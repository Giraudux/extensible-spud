package fr.univ.nantes.extensiblespud;

import fr.univ.nantes.extensiblespud.bean.Configuration;
import fr.univ.nantes.extensiblespud.bean.Description;
import fr.univ.nantes.extensiblespud.parser.Parser;
import fr.univ.nantes.extensiblespud.parser.PropertiesParser;
import fr.univ.nantes.extensiblespud.proxy.LazyLoaderHandler;

import java.io.*;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Description> getDescriptions() throws IOException, ClassNotFoundException {
        return (Map<String, Description>) copy(descriptions__);
    }

    public void updateDescriptions() {
        descriptions__ = new HashMap<String, Description>();
        Parser<Description> parser = new PropertiesParser<Description>();
        Description description;

        for (File file : listFiles(configuration.getDescPath(), configuration.getRecursive())) {
            try {
                description = parser.parse(new FileInputStream(file), Description.class);
                descriptions__.put(description.getName(), description);
            } catch (Exception ignored) {
                ;
            }
        }
    }

    private static Collection<File> listFiles(String path, boolean recursive) {
        Collection<File> files = new ArrayList<File>();
        listFiles(path, files, recursive);
        return files;
    }

    private static void listFiles(String path, Collection<File> files, boolean recursive) {
        File base = new File(path);
        if (base.isDirectory()) {
            File listFiles[] = base.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.isFile()) {
                        files.add(file);
                    } else if (file.isDirectory() && recursive) {
                        listFiles(file.getPath(), files, recursive);
                    }
                }
            }
        }
    }

    public static Platform getInstance() throws MalformedURLException {
        if (instance == null) {
            if (configuration == null) {
                configuration = new Configuration();
            }

            instance = new Platform();
            instance.singletons__ = new HashMap<String, Object>();
            URL url = new URL(configuration.getClassPath());
            URL urls[] = {url};
            instance.classLoader__ = new URLClassLoader(urls);
            instance.updateDescriptions();
        }

        return instance;
    }

    public static Configuration getConfiguration() throws IOException, ClassNotFoundException {
        if (configuration == null) {
            configuration = new Configuration();
        }

        return (Configuration) copy(Platform.configuration);
    }

    public static void setConfiguration(Configuration configuration) throws IOException, ClassNotFoundException {
        if (configuration == null) {
            configuration = new Configuration();
        }

        Platform.configuration = (Configuration) copy(configuration);
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
