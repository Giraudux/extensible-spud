import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class Platform {

    private static Platform instance;
    private static Map<Description, Object> extensions;
    private static Set<Description> descriptions;
    private static ClassLoader classLoader;

    private Platform() {
    }

    public Object loadExtension(Description description) throws Exception {
        if (extensions == null)
            extensions = new HashMap<Description, Object>();
        else if (extensions.containsKey(description)) {
            return extensions.get(description);
        }

        if(classLoader == null) {
            URL url = new URL(".");
            URL urls[] = {url};
            classLoader = new URLClassLoader(urls);
        }

        Class<?> extensionClass = Class.forName(description.getName(), true, classLoader);

        Object extension;
        if (description.isProxy()) {
            Class<?>[] interfaces = {extensionClass};

            extension = Proxy.newProxyInstance(classLoader, interfaces, null /*new ProxyHandler(target)*/);
        } else {
            extension = extensionClass.newInstance();
        }

        return extension;
    }

    public static Set<Description> getDescriptions() throws Exception {
        if (descriptions == null) {
            descriptions = new HashSet<Description>();

            BufferedReader br = new BufferedReader(new FileReader("afficheur.txt"));

            String line;
            Description description = new Description();
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    if (description.getName() != null) {
                        descriptions.add(description);
                    }

                    description = new Description();
                }

                String split[] = line.split(" = ");
                String key = split[0];
                String value = split[1];

                for (Method m : Description.class.getMethods()) {
                    if (m.getName().equals("set" + key)) {
                        Class<?> parameterType = m.getParameterTypes()[0];
                        if (parameterType.equals(String.class)) {
                            m.invoke(description, value);
                        } else if (parameterType.equals(Boolean.class)) {
                            m.invoke(description, Boolean.parseBoolean(value));
                        } else {
                            throw new Exception("unknown parameter type: " + parameterType.getName());
                        }

                        break;
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
}
