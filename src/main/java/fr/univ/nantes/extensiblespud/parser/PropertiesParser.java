package fr.univ.nantes.extensiblespud.parser;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

/**
 * @param <T>
 * @author Nina Exposito
 * @author Alexis Giraudet
 * @author Jean-Christophe Guérin
 * @author Jasone Lenormand
 */
public abstract class PropertiesParser<T> implements Parser<T> {
    private Class<T> tClass;

    /**
     * @param tClass
     */
    protected PropertiesParser(Class<T> tClass) {
        this.tClass = tClass;
    }

    /**
     * @param inputStream
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IOException
     * @throws InvocationTargetException
     */
    public T parse(InputStream inputStream) throws IllegalAccessException, InstantiationException, IOException, InvocationTargetException {
        T t = tClass.newInstance();
        Properties properties = new Properties();

        properties.load(inputStream);
        for (String key : properties.stringPropertyNames()) {
            for (Method method : tClass.getMethods()) {
                StringBuilder methodName = new StringBuilder("set");
                if (key.length() > 0) {
                    methodName.append(Character.toUpperCase(key.charAt(0)));
                    if (key.length() > 1) {
                        methodName.append(key.substring(1));
                    }
                }
                if (method.getName().equals(methodName.toString())) {
                    Class<?> parameterTypes[] = method.getParameterTypes();
                    if (parameterTypes.length == 1) {
                        Class<?> parameterType = parameterTypes[0];

                        if (parameterType.equals(Byte.class)) {
                            method.invoke(t, Byte.parseByte(properties.getProperty(key)));
                        } else if (parameterType.equals(Short.class)) {
                            method.invoke(t, Short.parseShort(properties.getProperty(key)));
                        } else if (parameterType.equals(Integer.class)) {
                            method.invoke(t, Integer.parseInt(properties.getProperty(key)));
                        } else if (parameterType.equals(Long.class)) {
                            method.invoke(t, Long.parseLong(properties.getProperty(key)));
                        } else if (parameterType.equals(Float.class)) {
                            method.invoke(t, Float.parseFloat(properties.getProperty(key)));
                        } else if (parameterType.equals(Double.class)) {
                            method.invoke(t, Double.parseDouble(properties.getProperty(key)));
                        } else if (parameterType.equals(Boolean.class)) {
                            method.invoke(t, Boolean.parseBoolean(properties.getProperty(key)));
                        } else if (parameterType.equals(Character.class)) {
                            method.invoke(t, properties.getProperty(key).charAt(0));
                        } else if (parameterType.equals(String.class)) {
                            method.invoke(t, properties.getProperty(key));
                        } else if (parameterType.equals(Collection.class)) {
                            Collection<String> c0 = Arrays.asList(properties.getProperty(key).replace("[", "").replace("]", "").replace(" ", "").split(","));
                            Collection<String> c1 = new ArrayList<String>();
                            for (String s : c0) {
                                if (!s.isEmpty()) {
                                    c1.add(s);
                                }
                            }
                            method.invoke(t, c1);
                        }

                        break;
                    }
                }
            }
        }

        return t;
    }

    /**
     * @return
     */
    public String fileExtension() {
        return "properties";
    }
}
