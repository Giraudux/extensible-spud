package fr.univ.nantes.extensiblespud.bean;

/**
 *
 */
public class Description {
    private String name;
    private String description;
    private boolean singleton;
    private boolean autorun;
    private boolean proxy;
    private Iterable<String> contributeTo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public boolean isAutorun() {
        return autorun;
    }

    public void setAutorun(boolean autorun) {
        this.autorun = autorun;
    }

    public boolean isProxy() {
        return proxy;
    }

    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }

    public Iterable<String> getContributeTo() {
        return contributeTo;
    }

    public void setContributeTo(Iterable<String> contributeTo) {
        this.contributeTo = contributeTo;
    }
}
