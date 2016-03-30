package fr.univ.nantes.extensiblespud.bean;

/**
 *
 */

public class ConfigurationBean implements Bean {
    private String classPath;
    private String descPath;
    private Boolean recursive;
    private Boolean nameRequired;
    private Boolean descRequired;
    private Boolean singleRequired;
    private Boolean autorunRequired;
    private Boolean proxyRequired;
    private String nameDefault;
    private String descDefault;
    private Boolean singleDefault;
    private Boolean autorunDefault;
    private Boolean proxyDefault;
    private Boolean contributeRequired;

    public ConfigurationBean() {
        classPath = "file://extensions";
        descPath = "descriptions";
        recursive = true;
        nameRequired = true;
        descRequired = true;
        singleRequired = true;
        autorunRequired = true;
        proxyRequired = true;
        contributeRequired = true;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getDescPath() {
        return descPath;
    }

    public void setDescPath(String descPath) {
        this.descPath = descPath;
    }

    public Boolean getRecursive() {
        return recursive;
    }

    public void setRecursive(Boolean recursive) {
        this.recursive = recursive;
    }

    public Boolean getNameRequired() {
        return nameRequired;
    }

    public void setNameRequired(Boolean nameRequired) {
        this.nameRequired = nameRequired;
    }

    public Boolean getDescRequired() {
        return descRequired;
    }

    public void setDescRequired(Boolean descRequired) {
        this.descRequired = descRequired;
    }

    public Boolean getSingleRequired() {
        return singleRequired;
    }

    public void setSingleRequired(Boolean singleRequired) {
        this.singleRequired = singleRequired;
    }

    public Boolean getAutorunRequired() {
        return autorunRequired;
    }

    public void setAutorunRequired(Boolean autorunRequired) {
        this.autorunRequired = autorunRequired;
    }

    public Boolean getProxyRequired() {
        return proxyRequired;
    }

    public void setProxyRequired(Boolean proxyRequired) {
        this.proxyRequired = proxyRequired;
    }

    public String getNameDefault() {
        return nameDefault;
    }

    public void setNameDefault(String nameDefault) {
        this.nameDefault = nameDefault;
    }

    public String getDescDefault() {
        return descDefault;
    }

    public void setDescDefault(String descDefault) {
        this.descDefault = descDefault;
    }

    public Boolean getSingleDefault() {
        return singleDefault;
    }

    public void setSingleDefault(Boolean singleDefault) {
        this.singleDefault = singleDefault;
    }

    public Boolean getAutorunDefault() {
        return autorunDefault;
    }

    public void setAutorunDefault(Boolean autorunDefault) {
        this.autorunDefault = autorunDefault;
    }

    public Boolean getProxyDefault() {
        return proxyDefault;
    }

    public void setProxyDefault(Boolean proxyDefault) {
        this.proxyDefault = proxyDefault;
    }

    public Boolean getContributeRequired() {
        return contributeRequired;
    }

    public void setContributeRequired(Boolean contributeRequired) {
        this.contributeRequired = contributeRequired;
    }
}
