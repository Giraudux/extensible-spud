package fr.univ.nantes.extensiblespud.bean;

import java.io.Serializable;

/**
 *
 */

public class Configuration implements Serializable {
    private String classPath;
    private String descPath;
    private boolean nameRequired;
    private boolean descRequired;
    private boolean singleRequired;
    private boolean autorunRequired;
    private boolean proxyRequired;
    private String nameDefault;
    private String descDefault;
    private boolean singleDefault;
    private boolean autorunDefault;
    private boolean proxyDefault;
    private boolean contributeRequired;

    public Configuration() {
        classPath = "file://extensions";
        descPath = "descriptions";
        nameRequired = true;
        descRequired = true;
        singleRequired = true;
        autorunRequired = true;
        proxyRequired = true;
        contributeRequired = true;
    }

    /*public Configuration(Configuration configuration) {
        this.classPath = configuration.classPath;
        this.descPath = configuration.descPath;
        this.nameRequired = configuration.nameRequired;
        this.descRequired = configuration.descRequired;
        this.singleRequired = configuration.singleRequired;
        this.autorunRequired = configuration.autorunRequired;
        this.proxyRequired = configuration.proxyRequired;
        this.nameDefault = configuration.nameDefault;
        this.descDefault = configuration.descDefault;
        this.singleDefault = configuration.singleDefault;
        this.autorunDefault = configuration.autorunDefault;
        this.proxyDefault = configuration.proxyDefault;
        this.contributeRequired = configuration.contributeRequired;
    }*/

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

    public boolean isNameRequired() {
        return nameRequired;
    }

    public void setNameRequired(boolean nameRequired) {
        this.nameRequired = nameRequired;
    }

    public boolean isDescRequired() {
        return descRequired;
    }

    public void setDescRequired(boolean descRequired) {
        this.descRequired = descRequired;
    }

    public boolean isSingleRequired() {
        return singleRequired;
    }

    public void setSingleRequired(boolean singleRequired) {
        this.singleRequired = singleRequired;
    }

    public boolean isAutorunRequired() {
        return autorunRequired;
    }

    public void setAutorunRequired(boolean autorunRequired) {
        this.autorunRequired = autorunRequired;
    }

    public boolean isProxyRequired() {
        return proxyRequired;
    }

    public void setProxyRequired(boolean proxyRequired) {
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

    public boolean isSingleDefault() {
        return singleDefault;
    }

    public void setSingleDefault(boolean singleDefault) {
        this.singleDefault = singleDefault;
    }

    public boolean isAutorunDefault() {
        return autorunDefault;
    }

    public void setAutorunDefault(boolean autorunDefault) {
        this.autorunDefault = autorunDefault;
    }

    public boolean isProxyDefault() {
        return proxyDefault;
    }

    public void setProxyDefault(boolean proxyDefault) {
        this.proxyDefault = proxyDefault;
    }

    public boolean isContributeRequired() {
        return contributeRequired;
    }

    public void setContributeRequired(boolean contributeRequired) {
        this.contributeRequired = contributeRequired;
    }
}
