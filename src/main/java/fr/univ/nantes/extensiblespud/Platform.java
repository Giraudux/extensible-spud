package fr.univ.nantes.extensiblespud;

import fr.univ.nantes.extensiblespud.bean.ConfigurationBean;
import fr.univ.nantes.extensiblespud.bean.DescriptionBean;
import fr.univ.nantes.extensiblespud.bean.StatusBean;
import fr.univ.nantes.extensiblespud.parser.DescriptionParser;
import fr.univ.nantes.extensiblespud.parser.DescriptionPropertiesParser;
import fr.univ.nantes.extensiblespud.parser.Parser;
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
import java.util.logging.Level;
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
    private Map<String, DescriptionParser> parsers__;
    private Map<String, StatusBean> status__;
    private ClassLoader classLoader__;

    /**
     *
     */
    private Platform() throws MalformedURLException {
        URL url = new URL(configuration.getClassPath());
        URL urls[] = {url};
        classLoader__ = new URLClassLoader(urls/*, this.getClass().getClassLoader()*/);
        descriptions__ = new HashMap<String, DescriptionBean>();
        parsers__ = new HashMap<String, DescriptionParser>();
        singletons__ = new HashMap<String, Object>();
        status__ = new HashMap<String, StatusBean>();
        DescriptionParser parser = new DescriptionPropertiesParser();
        parsers__.put(parser.fileExtension(), parser);
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
            logger.log(Level.WARNING, "can't load \""+description.getName()+"\" extension: "+e.toString());
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
        String fileExtension;
        Parser<DescriptionBean> parser;
        DescriptionBean description;
        StatusBean status;
        boolean update;

        logger.log(Level.INFO, "update descriptions");

        update = false;
        for (File file : listFiles(configuration.getDescriptionPath(), configuration.getRecursive())) {
            fileExtension = getFileExtension(file);
            if((parser = parsers__.get(fileExtension)) == null) {
                logger.log(Level.WARNING, "no description parser mapped for the \""+fileExtension+"\" extension");
            } else {
                try {
                    description = parser.parse(new FileInputStream(file));
                    descriptions__.put(description.getName(), description);
                    if(!status__.containsKey(description.getName())) {
                        status = new StatusBean();
                        status.setUnresolvedDependencies(new ArrayList<String>(description.getDependencies()));
                        status__.put(description.getName(), status);
                    }
                    if(description.getContributeTo().contains(DescriptionParser.class.getName())) {
                        DescriptionParser newParser = (DescriptionParser) Platform.getInstance().loadExtension(description.getName());
                        if(!parsers__.containsKey(newParser.fileExtension())) {
                            parsers__.put(newParser.fileExtension(), newParser);
                            update = true;
                        }
                    }
                } catch (Exception e) {
                    logger.log(Level.WARNING, "can't parse \""+file.getPath()+"\" description file: "+e.toString());
                }
            }
        }

        if(update) {
            updateDescriptions();
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

        logger.log(Level.INFO, "update platform configuration");

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

    private static String getFileExtension(File file) {
        String fileExtension = "";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if(dotIndex >= 0) {
            fileExtension = fileName.substring(dotIndex+1);
        }
        return fileExtension;
    }

    /**
     *
     * @param fileExtension
     * @param descriptionParser
     * @return
     */
    public Parser<DescriptionBean> putDescriptionParser(String fileExtension, DescriptionParser descriptionParser) {
        return parsers__.put(fileExtension, descriptionParser);
    }

    /**
     *
     * @param fileExtension
     * @return
     */
    public Parser<DescriptionBean> removeDescriptionParser(String fileExtension) {
        return parsers__.remove(fileExtension);
    }
}
