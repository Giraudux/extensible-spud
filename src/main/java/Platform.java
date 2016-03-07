import java.io.*;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 *
 */
public class Platform {
    private static Platform instance;

    private Configuration configuration;
    private Map<Description, Object> singletons;
    private Set<Description> descriptions;
    private ClassLoader classLoader;

    private Platform() {
    }

    public void autorun() throws Exception {
        for (Description description : getDescriptions()) {
            if (description.isAutorun()) {
                loadExtension(description);
            }
        }
    }

    public Object loadExtension(Description description) throws Exception {
        if (singletons == null) {
            singletons = new HashMap<Description, Object>();
        }

        if (classLoader == null) {
            URL url = new URL("file://extensions"/*configuration.getDescPath()*/);
            URL urls[] = {url};
            classLoader = new URLClassLoader(urls);
        }

        Class<?> extensionClass = Class.forName(description.getName(), true, classLoader);

        Object extensionInstance = null;
        if (description.isProxy()) {
            Class<?>[] interfaces = {extensionClass};
            extensionInstance = Proxy.newProxyInstance(classLoader, interfaces, new ProxyHandler(extensionClass, null));
        } else {
            if (description.isSingleton()) {
                if (singletons.containsKey(description)) {
                    extensionInstance = singletons.get(description);
                }
            } else {
                extensionInstance = extensionClass.newInstance();
                singletons.put(description, extensionInstance);
            }
        }

        return extensionInstance;
    }

    public Set<Description> getDescriptions() throws Exception {
        if (descriptions == null) {
            descriptions = new HashSet<Description>();

            File extensionsDirectory = new File("descriptions");
            if (extensionsDirectory.isDirectory()) {
                File files[] = extensionsDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            Description description = Loader(file.getPath());
                            if (description != null) {
                                descriptions.add(description);
                            }
                        }
                    }
                }
            }
        }

        return descriptions;
    }

    public static Platform getInstance() {
        if (instance == null)
            instance = new Platform();

        return instance;
    }

    private static Description Loader(String filePath) throws IOException {
        Description descList = new Description();

        try {
            FileInputStream fileInput = new FileInputStream(filePath);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();

            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();

                if (key.equals("name")) {
                    descList.setName(value);
                } else if (key.equals("description")) {
                    descList.setDescription(value);
                } else if (key.equals("singleton")) {
                    descList.setSingleton(Boolean.parseBoolean(value));
                } else if (key.equals("autorun")) {
                    descList.setAutorun(Boolean.parseBoolean(value));
                } else if (key.equals("proxy")) {
                    descList.setProxy(Boolean.parseBoolean(value));
                }
            }
        } catch (Exception e) {
            return null;
        }

        return descList;
    }
}
