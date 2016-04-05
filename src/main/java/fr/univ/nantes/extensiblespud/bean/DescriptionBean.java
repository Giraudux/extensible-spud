package fr.univ.nantes.extensiblespud.bean;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Nina Exposito
 * @author Alexis Giraudet
 * @author Jean-Christophe Guérin
 * @author Jasone Lenormand
 */
public class DescriptionBean implements Bean {
    private String name;
    private String description;
    private Boolean singleton;
    private Boolean autorun;
    private Collection<String> proxies;
    private Collection<String> contributeTo;
    private Collection<String> dependencies;

    /**
     * Defaults values are: name = "", description = "", singleton = false, autorun = false, proxies = [], contributeTo = [] and dependencies = [].
     */
    public DescriptionBean() {
        name = "";
        description = "";
        singleton = false;
        autorun = false;
        proxies = new ArrayList<String>();
        contributeTo = new ArrayList<String>();
        dependencies = new ArrayList<String>();
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

    public Collection<String> getProxies() {
        return proxies;
    }

    public void setProxies(Collection<String> proxies) {
        this.proxies = proxies;
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
