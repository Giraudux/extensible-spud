package fr.univ.nantes.extensiblespud.bean;

/**
 * @author Nina Exposito
 * @author Alexis Giraudet
 * @author Jean-Christophe Guérin
 * @author Jasone Lenormand
 */

public class ConfigurationBean implements Bean {
    private String classPath;
    private String descriptionPath;
    private Boolean recursive;

    /**
     * Defaults values are: classPath = "file://extensions", descriptionPath = "descriptions" and recursive = true.
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
