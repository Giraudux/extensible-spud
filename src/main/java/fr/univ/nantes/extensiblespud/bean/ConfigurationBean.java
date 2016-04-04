package fr.univ.nantes.extensiblespud.bean;

/**
 *
 */

public class ConfigurationBean implements Bean {
    private String classPath;
    private String descriptionPath;
    private Boolean recursive;

    /**
     *
     */
    public ConfigurationBean() {
        classPath = "file://extensions";
        descriptionPath = "descriptions";
        recursive = true;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getDescriptionPath() {
        return descriptionPath;
    }

    public void setDescriptionPath(String descriptionPath) {
        this.descriptionPath = descriptionPath;
    }

    public Boolean getRecursive() {
        return recursive;
    }

    public void setRecursive(Boolean recursive) {
        this.recursive = recursive;
    }
}
