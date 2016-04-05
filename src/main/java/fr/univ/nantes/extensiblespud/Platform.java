package fr.univ.nantes.extensiblespud;

import fr.univ.nantes.extensiblespud.bean.ConfigurationBean;
import fr.univ.nantes.extensiblespud.bean.DescriptionBean;
import fr.univ.nantes.extensiblespud.bean.HandlerBean;
import fr.univ.nantes.extensiblespud.bean.StatusBean;
import fr.univ.nantes.extensiblespud.handler.Handler;
import fr.univ.nantes.extensiblespud.handler.HandlerManager;
import fr.univ.nantes.extensiblespud.handler.LazyLoaderHandler;
import fr.univ.nantes.extensiblespud.parser.DescriptionParser;
import fr.univ.nantes.extensiblespud.parser.DescriptionPropertiesParser;

import java.io.*;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Nina Exposito
 * @author Alexis Giraudet
 * @author Jean-Christophe Guérin
 * @author Jasone Lenormand
 */
public class Platform {
    private static Logger logger = Logger.getLogger(Platform.class.getName());
    private static Platform instance;
    private static ConfigurationBean configuration;
    private Map<String, Object> singletons__;
    private Map<String, DescriptionBean> descriptions__;
    private Map<String, StatusBean> status__;
    private ClassLoader classLoader__;

    /**
     * @throws MalformedURLException
     */
    private Platform() throws MalformedURLException {
        URL url = new URL(configuration.getClassPath());
        URL urls[] = {url};
        classLoader__ = new URLClassLoader(urls);
        descriptions__ = new HashMap<String, DescriptionBean>();
        singletons__ = new HashMap<String, Object>();
        status__ = new HashMap<String, StatusBean>();
        addBaseDescriptions();
        updateDescriptions();
    }

    /**
     * Load all extension description where autorun is true.
     */
    public void autorun() {
        for (DescriptionBean description : descriptions__.values()) {
            if (description.getAutorun()) {
                loadExtension(description);
            }
        }
    }

    /**
     * Load extension by name.
     *
     * @param name the name of the extension to load
     * @return the extension or null on failure
     */
    public Object loadExtension(String name) {
        return loadExtension(descriptions__.get(name));
    }

    /**
     * Load extension by description.
     *
     * @param description the descrirption of the extension to load
     * @return the extension or null on failure
     */
    private Object loadExtension(DescriptionBean description) {
        Object o = null;
        StatusBean status = null;
        Class<?> extensionClass = null;

        try {
            extensionClass = Class.forName(description.getName(), true, classLoader__);
            status = status__.get(description.getName());

            if (!description.getSingleton() || (o = singletons__.get(description.getName())) == null) {
                if (description.getAutorun() || description.getProxies().isEmpty()) {
                    o = extensionClass.newInstance();
                }

                if (!description.getProxies().isEmpty()) {
                    HandlerBean handlerBean = new HandlerBean(description, status, o, classLoader__);
                    HandlerManager handlerManager = new HandlerManager(handlerBean);
                    Collection<Class<?>> interfaces;
                    Handler handler;

                    for (String proxy : description.getProxies()) {
                        handler = (Handler) loadExtension(proxy);
                        handlerManager.add(handler);
                    }

                    interfaces = handlerManager.getInterfaces();
                    o = Proxy.newProxyInstance(classLoader__, interfaces.toArray(new Class<?>[interfaces.size()]), handlerManager);
                }

                if (description.getSingleton()) {
                    singletons__.put(description.getName(), o);
                }

                status.setSuccessfullyLoaded(true);

                logger.log(Level.INFO, "load \"" + description.getName() + "\" extension");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "can't load \"" + description.getName() + "\" extension: " + e.toString());
            if (status != null) {
                status.setLoadingFailed(true);
                status.setLastException(e);
            }
        }

        return o;
    }

    /**
     * List all available extension descriptions.
     *
     * @return the list of descriptions
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Map<String, DescriptionBean> getDescriptions() throws IOException, ClassNotFoundException {
        return (Map<String, DescriptionBean>) copy(descriptions__);
    }

    /**
     * List all extension contribution to the given extension.
     *
     * @param contributeTo the interface to contribute to
     * @return the list of contributors
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Map<String, DescriptionBean> getContributors(String contributeTo) throws IOException, ClassNotFoundException {
        Map<String, DescriptionBean> contributors = getDescriptions();

        for (Map.Entry<String, DescriptionBean> entry : descriptions__.entrySet()) {
            if (!entry.getValue().getContributeTo().contains(contributeTo)) {
                contributors.remove(entry.getKey());
            }
        }

        return contributors;
    }

    /**
     * List the status of
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Map<String, StatusBean> getStatus() throws IOException, ClassNotFoundException {
        return (Map<String, StatusBean>) copy(status__);
    }

    /**
     * Reload descriptions.
     */
    public void updateDescriptions() {
        /*descriptions__ = new HashMap<String, DescriptionBean>();
        status__ = new HashMap<String, StatusBean>();*/
        String fileExtension;
        DescriptionParser parser;
        DescriptionBean description;
        StatusBean status;
        boolean update;

        logger.log(Level.INFO, "update descriptions");

        update = false;
        for (File file : listFiles(configuration.getDescriptionPath(), configuration.getRecursive())) {
            fileExtension = getFileExtension(file);
            try {
                parser = null;
                for (Map.Entry<String, DescriptionBean> entry : getContributors(DescriptionParser.class.getName()).entrySet()) {
                    if ((parser = (DescriptionParser) loadExtension(entry.getKey())) != null && parser.fileExtension().equals(fileExtension)) {
                        break;
                    }
                }

                if (parser == null) {
                    continue;
                }

                description = parser.parse(new FileInputStream(file));
                descriptions__.put(description.getName(), description);
                if (!status__.containsKey(description.getName())) {
                    status = new StatusBean();
                    status.setUnresolvedDependencies(new ArrayList<String>(description.getDependencies()));
                    status__.put(description.getName(), status);
                    if (description.getContributeTo().contains(DescriptionParser.class.getName())) {
                        update = true;
                    }
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "can't parse \"" + file.getPath() + "\" description file: " + e.toString());
            }
        }

        if (update) {
            updateDescriptions();
        }
    }

    /**
     * @return the platform instance
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
     * @return the current platform configuration
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
     * @param configuration the new platform configuration
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void setConfiguration(ConfigurationBean configuration) throws IOException, ClassNotFoundException {
        if (configuration == null) {
            configuration = new ConfigurationBean();
        }

        logger.log(Level.INFO, "update platform configuration");

        Platform.configuration = (ConfigurationBean) copy(configuration);
    }

    /**
     *
     */
    private void addBaseDescriptions() {
        DescriptionBean description;
        Collection<String> contributeTo;
        StatusBean status;

        contributeTo = new ArrayList<String>();
        contributeTo.add(DescriptionParser.class.getName());
        description = new DescriptionBean();
        description.setName(DescriptionPropertiesParser.class.getName());
        description.setContributeTo(contributeTo);
        description.setSingleton(true);
        descriptions__.put(description.getName(), description);
        status = new StatusBean();
        status__.put(description.getName(), status);

        contributeTo = new ArrayList<String>();
        contributeTo.add(Handler.class.getName());
        description = new DescriptionBean();
        description.setName(LazyLoaderHandler.class.getName());
        description.setContributeTo(contributeTo);
        descriptions__.put(description.getName(), description);
        status = new StatusBean();
        status__.put(description.getName(), status);
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

    /**
     * @param file
     * @return
     */
    private static String getFileExtension(File file) {
        String fileExtension = "";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            fileExtension = fileName.substring(dotIndex + 1);
        }
        return fileExtension;
    }
}
