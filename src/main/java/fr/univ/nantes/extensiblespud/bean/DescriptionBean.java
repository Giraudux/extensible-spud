package fr.univ.nantes.extensiblespud.bean;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */
public class DescriptionBean implements Bean {
    private String name;
    private String description;
    private Boolean singleton;
    private Boolean autorun;
    private Boolean proxy;
    private Collection<String> contributeTo;
    private Collection<String> dependencies;

    public DescriptionBean() {
        name = "";
        description = "";
        singleton = false;
        autorun = false;
        proxy = false;
        contributeTo = new ArrayList<String>();
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

    public Boolean getSingleton() {
        return singleton;
    }

    public void setSingleton(Boolean singleton) {
        this.singleton = singleton;
    }

    public Boolean getAutorun() {
        return autorun;
    }

    public void setAutorun(Boolean autorun) {
        this.autorun = autorun;
    }

    public Boolean getProxy() {
        return proxy;
    }

    public void setProxy(Boolean proxy) {
        this.proxy = proxy;
    }

    public Collection<String> getContributeTo() {
        return contributeTo;
    }

    public void setContributeTo(Collection<String> contributeTo) {
        this.contributeTo = contributeTo;
    }

    public Collection<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Collection<String> dependencies) {
        this.dependencies = dependencies;
    }
}
