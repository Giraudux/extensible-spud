package fr.univ.nantes.extensiblespud.bean;

/**
 *
 */
public class StatusBean implements Bean {
    private Boolean successfullyLoaded;
    private Boolean loadingFailed;
    private Exception lastException;

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
}
