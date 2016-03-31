package fr.univ.nantes.extensiblespud;

import fr.univ.nantes.extensiblespud.bean.ConfigurationBean;
import fr.univ.nantes.extensiblespud.bean.DescriptionBean;
import fr.univ.nantes.extensiblespud.bean.StatusBean;
import fr.univ.nantes.extensiblespud.parser.Parser;
import fr.univ.nantes.extensiblespud.parser.PropertiesParser;
import fr.univ.nantes.extensiblespud.handler.LazyLoaderHandler;

import java.io.*;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 */
public class Platform {
    private static Logger logger = Logger.getLogger(Platform.class.getName());
    private static Platform instance;
    private static ConfigurationBean configuration;
    private Map<String, Object> singletons__;
    private Map<String, DescriptionBean> descriptions__;
    private Map<String, Parser<DescriptionBean>> parsers__;
    private Map<String, StatusBean> status__;
    private ClassLoader classLoader__;

    /**
     *
     */
    private Platform() throws MalformedURLException {
        URL url = new URL(configuration.getClassPath());
        URL urls[] = {url};
        classLoader__ = new URLClassLoader(urls);
        descriptions__ = new HashMap<String, DescriptionBean>();
        parsers__ = new HashMap<String, Parser<DescriptionBean>>();
        singletons__ = new HashMap<String, Object>();
        status__ = new HashMap<String, StatusBean>();
        parsers__.put("properties", new PropertiesParser<DescriptionBean>());
    }

    /**
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public void autorun() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(descriptions__.isEmpty()) {
            updateDescriptions();
        }

        for (DescriptionBean description : descriptions__.values()) {
            if (description.getAutorun()) {
                loadExtension(description);
            }
        }
    }

    /**
     * @param name
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public Object loadExtension(String name) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return loadExtension(descriptions__.get(name));
    }

    /**
     * @param description
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object loadExtension(DescriptionBean description) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> extensionClass = Class.forName(description.getName(), true, classLoader__);
        Object o = null;
        Object extension = null;
        Object proxy = null;
        StatusBean status = status__.get(description.getName());

        try {
            if (!description.getSingleton() || (o = singletons__.get(description.getName())) == null) {
                if (description.getAutorun() || !description.getProxy()) {
                    extension = extensionClass.newInstance();
                    o = extension;
                }

                if (description.getProxy()) {
                    Class<?>[] interfaces = extensionClass.getInterfaces();
                    proxy = Proxy.newProxyInstance(classLoader__, interfaces, new LazyLoaderHandler(extensionClass, extension));
                    o = proxy;
                }

                if (description.getSingleton()) {
                    singletons__.put(description.getName(), o);
                }

                status.setSuccessfullyLoaded(true);
            }
        } catch (Exception e) {
            status.setLoadingFailed(true);
            status.setLastException(e);
            //throw e;
        }

        return o;
    }

    /**
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Map<String, DescriptionBean> getDescriptions() throws IOException, ClassNotFoundException {
        if(descriptions__.isEmpty()) {
            updateDescriptions();
        }

        return (Map<String, DescriptionBean>) copy(descriptions__);
    }

    /**
     *
     * @param contributeTo
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Map<String, DescriptionBean> getContributors(String contributeTo) throws IOException, ClassNotFoundException {
        Map<String,DescriptionBean> contributors = getDescriptions();

        for(Map.Entry<String,DescriptionBean> entry : descriptions__.entrySet()) {
            if(!entry.getValue().getContributeTo().contains(contributeTo)) {
                contributors.remove(entry.getKey());
            }
        }

        return contributors;
    }

    /**
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Map<String, StatusBean> getStatus() throws IOException, ClassNotFoundException {
        return (Map<String, StatusBean>) copy(status__);
    }

    /**
     *
     */
    public void updateDescriptions() {
        //descriptions__ = new HashMap<String, DescriptionBean>();
        //status__ = new HashMap<String, StatusBean>();
        Parser<DescriptionBean> parser = new PropertiesParser<DescriptionBean>();
        DescriptionBean description;

        for (File file : listFiles(configuration.getDescPath(), configuration.getRecursive())) {
            try {
                description = parser.parse(new FileInputStream(file), DescriptionBean.class);
                descriptions__.put(description.getName(), description);
                if(!status__.containsKey(description.getName())) {
                    status__.put(description.getName(), new StatusBean());
                }
            } catch (Exception ignored) {
                ;
            }
        }
    }

    /**
     * @param path
     * @param recursive
     * @return
     */
    private static Collection<File> listFiles(String path, boolean recursive) {
        Collection<File> files = new ArrayList<File>();
        listFiles(path, files, recursive);
        return files;
    }

    /**
     * @param path
     * @param files
     * @param recursive
     */
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

    /**
     * @return
     * @throws MalformedURLException
     */
    public static Platform getInstance() throws MalformedURLException {
        if (instance == null) {
            if (configuration == null) {
                configuration = new ConfigurationBean();
            }

            instance = new Platform();
        }

        return instance;
    }

    /**
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ConfigurationBean getConfiguration() throws IOException, ClassNotFoundException {
        if (configuration == null) {
            configuration = new ConfigurationBean();
        }

        return (ConfigurationBean) copy(Platform.configuration);
    }

    /**
     * @param configuration
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void setConfiguration(ConfigurationBean configuration) throws IOException, ClassNotFoundException {
        if (configuration == null) {
            configuration = new ConfigurationBean();
        }

        Platform.configuration = (ConfigurationBean) copy(configuration);
    }

    /**
     * @param object
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
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
