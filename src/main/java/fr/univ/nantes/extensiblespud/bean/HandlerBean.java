package fr.univ.nantes.extensiblespud.bean;

/**
 * @author Nina Exposito
 * @author Alexis Giraudet
 * @author Jean-Christophe Guérin
 * @author Jasone Lenormand
 */
public class HandlerBean implements Bean {
    private DescriptionBean description;
    private StatusBean status;
    private Object instance;
    private ClassLoader classLoader;

    public HandlerBean(DescriptionBean description, StatusBean status, Object instance, ClassLoader classLoader) {
        this.description = description;
        this.status = status;
        this.instance = instance;
        this.classLoader = classLoader;
    }

    public DescriptionBean getDescription() {
        return description;
    }

    public void setDescription(DescriptionBean description) {
        this.description = description;
    }

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
