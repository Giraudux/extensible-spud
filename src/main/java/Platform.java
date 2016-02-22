import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class Platform {

    private static Platform instance;
    private static Map<String, Object> extensions;
    private static Set<Description> descriptions;

    private Platform() {}

    public Object loadExtension(Description description) throws Exception {
        if (extensions == null)
            extensions = new HashMap<String, Object>();
        else if (extensions.containsKey(description.getName())) {
            return extensions.get(description.getName());
        }

        Class<?> afficheurClass = Class.forName(description.getName());
        Object extension = afficheurClass.newInstance();

        return extension;
    }

    public static Set<Description> getDescriptions() throws Exception {
        if(descriptions == null) {
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
