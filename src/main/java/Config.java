package main.java;

public class Config {
	private boolean defaultSingleton;
    private boolean defaultAutorun;
    private boolean defaultProxy;
    
    private String fileDescription;
    
	public boolean isDefaultSingleton() {
		return defaultSingleton;
	}
	public void setDefaultSingleton(boolean defaultSingleton) {
		this.defaultSingleton = defaultSingleton;
	}
	public boolean isDefaultAutorun() {
		return defaultAutorun;
	}
	public void setDefaultAutorun(boolean defaultAutorun) {
		this.defaultAutorun = defaultAutorun;
	}
	public boolean isDefaultProxy() {
		return defaultProxy;
	}
	public void setDefaultProxy(boolean defaultProxy) {
		this.defaultProxy = defaultProxy;
	}
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
    
    
}