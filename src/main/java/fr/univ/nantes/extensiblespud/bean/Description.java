package fr.univ.nantes.extensiblespud.bean;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */
public class Description {
    private String name;
    private String description;
    private boolean singleton;
    private boolean autorun;
    private boolean proxy;
    private Collection<String> contributeTo;

    public Description() {
        name = "";
        description = "";
        singleton = false;
        autorun = false;
        proxy = false;
        contributeTo = new ArrayList<String>();
    }

    public Description(Description description) {
        this.name = description.name;
        this.description = description.description;
        this.singleton = description.singleton;
        this.autorun = description.autorun;
        this.proxy = description.proxy;
        this.contributeTo = new ArrayList<String>(description.contributeTo);
    }

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

    public Collection<String> getContributeTo() {
        return contributeTo;
    }

    public void setContributeTo(Collection<String> contributeTo) {
        this.contributeTo = contributeTo;
    }
}
