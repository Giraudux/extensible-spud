package fr.univ.nantes.extensiblespud.bean;

import java.util.Collection;

/**
 *
 */
public class StatusBean implements Bean {
    private Boolean successfullyLoaded;
    private Boolean loadingFailed;
    private Exception lastException;
    private Collection<String> unresolvedDependencies;

    /**
     *
     */
    public StatusBean() {
        successfullyLoaded = false;
        loadingFailed = false;
        lastException = null;
    }

    public Boolean getSuccessfullyLoaded() {
        return successfullyLoaded;
    }

    public void setSuccessfullyLoaded(Boolean successfullyLoaded) {
        this.successfullyLoaded = successfullyLoaded;
    }

    public Boolean getLoadingFailed() {
        return loadingFailed;
    }

    public void setLoadingFailed(Boolean loadingFailed) {
        this.loadingFailed = loadingFailed;
    }

    public Exception getLastException() {
        return lastException;
    }

    public void setLastException(Exception lastException) {
        this.lastException = lastException;
    }

    public Collection<String> getUnresolvedDependencies() {
        return unresolvedDependencies;
    }

    public void setUnresolvedDependencies(Collection<String> unresolvedDependencies) {
        this.unresolvedDependencies = unresolvedDependencies;
    }
}
