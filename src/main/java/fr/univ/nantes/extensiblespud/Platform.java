package fr.univ.nantes.extensiblespud;

import fr.univ.nantes.extensiblespud.bean.Configuration;
import fr.univ.nantes.extensiblespud.bean.Description;
import fr.univ.nantes.extensiblespud.proxy.LazyLoaderHandler;

import java.io.*;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 *
 */
public class Platform {
    private static Platform instance;
    private static Configuration configuration;
    private Map<String, Object> singletons__;
    private Map<String, Description> descriptions__;
    private ClassLoader classLoader__;

    private Platform() {
    }

    public void autorun() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        for (Description description : descriptions__.values()) {
            if (description.getAutorun()) {
                loadExtension(description);
            }
        }
    }

    public Object loadExtension(String name) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return loadExtension(descriptions__.get(name));
    }

    public Object loadExtension(Description description) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> extensionClass = Class.forName(description.getName(), true, classLoader__);
        Object o = null;
        Object extension = null;
        Object proxy = null;

        if (!description.getSingleton() || (o = singletons__.get(description.getName())) == null) {
            if (description.getAutorun() || !description.getProxy()) {
                extension = extensionClass.newInstance();
                o = extension;
            }

            if (description.getProxy()) {
                Class<?>[] interfaces = {extensionClass};
                proxy = Proxy.newProxyInstance(classLoader__, interfaces, new LazyLoaderHandler(extensionClass, extension));
                o = proxy;
            }

            if (description.getSingleton()) {
                singletons__.put(description.getName(), o);
            }
        }

        return o;
    }

    public Map<String, Description> getDescriptions() throws Exception {
        if (descriptions__ == null) {
            descriptions__ = new HashMap<String, Description>();

            File extensionsDirectory = new File(getConfiguration().getDescPath());
            if (extensionsDirectory.isDirectory()) {
                File files[] = extensionsDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            Description description = Loader(file.getPath());
                            if (description != null) {
                                descriptions__.put(description.getName(), description);
                            }
                        }
                    }
                }
            }
        }

        return descriptions__;
    }

    public static Platform getInstance() throws MalformedURLException {
        if (instance == null) {
            if (configuration == null) {
                configuration = new Configuration();
            }

            instance = new Platform();
            instance.singletons__ = new HashMap<String, Object>();
            instance.descriptions__ = new HashMap<String, Description>();
            URL url = new URL(configuration.getClassPath());
            URL urls[] = {url};
            instance.classLoader__ = new URLClassLoader(urls);
        }

        return instance;
    }

    public static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
        }

        return Platform.configuration;
    }

    public static void setConfiguration(Configuration configuration) {
        if (configuration == null) {
            configuration = new Configuration();
        }

        Platform.configuration = configuration;
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
                } else if (key.equals("contributors")) {
                    Collection<String> col = new ArrayList<String>();
                    String[] arrayS = value.split(", ", 0);
                    for (int i = 0; i <= arrayS.length; i++) {
                        col.add(arrayS[i]);
                        descList.setContributeTo(col);
                    }

                }
            }
        } catch (Exception e) {
            return null;
        }

        return descList;
    }

    private static Object copy(Object object) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.flush();
        oos.close();
        ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        return oin.readObject();
    }
}
