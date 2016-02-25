package spud;

public class Configuration {
	private String _classPath;
	private String _descPath;
	private boolean _nameRequired;
	private boolean _descRequired;
	private boolean _singleRequired;
	private boolean _autorunRequired;
	private boolean _proxyRequired;
	private String _nameDefault;
	private String _descDefault;
	private boolean _singleDefault;
	private boolean _autorunDefault;
	private boolean _proxyDefault;
	
	public String getClassPath() {
		return _classPath;
	}
	public void setClassPath(String classPath) {
		_classPath = classPath;
	}
	public String getDescPath() {
		return _descPath;
	}
	public void setDescPath(String descPath) {
		_descPath = descPath;
	}
	public boolean isNameRequired() {
		return _nameRequired;
	}
	public void setNameRequired(boolean nameRequired) {
		_nameRequired = nameRequired;
	}
	public boolean isDescRequired() {
		return _descRequired;
	}
	public void setDescRequired(boolean descRequired) {
		_descRequired = descRequired;
	}
	public boolean isSingleRequired() {
		return _singleRequired;
	}
	public void setSingleRequired(boolean singleRequired) {
		_singleRequired = singleRequired;
	}
	public boolean isAutorunRequired() {
		return _autorunRequired;
	}
	public void setAutorunRequired(boolean autorunRequired) {
		_autorunRequired = autorunRequired;
	}
	public boolean isProxyRequired() {
		return _proxyRequired;
	}
	public void setProxyRequired(boolean proxyRequired) {
		_proxyRequired = proxyRequired;
	}
	public String getNameDefault() {
		return _nameDefault;
	}
	public void setNameDefault(String nameDefault) {
		_nameDefault = nameDefault;
	}
	public String getDescDefault() {
		return _descDefault;
	}
	public void setDescDefault(String descDefault) {
		_descDefault = descDefault;
	}
	public boolean getSingleDefault() {
		return _singleDefault;
	}
	public void setSingleDefault(boolean singleDefault) {
		_singleDefault = singleDefault;
	}
	public boolean getAutorunDefault() {
		return _autorunDefault;
	}
	public void setAutorunDefault(boolean autorunDefault) {
		_autorunDefault = autorunDefault;
	}
	public boolean getProxyDefault() {
		return _proxyDefault;
	}
	public void setProxyDefault(boolean proxyDefault) {
		_proxyDefault = proxyDefault;
	}
	
	
}
