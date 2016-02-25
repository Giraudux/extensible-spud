package spud;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class ConfigLoader {
	public static Configuration Loader(String filePath) throws IOException{
		
		Configuration config = new Configuration();
	
		try {
			
			FileInputStream fileInput = new FileInputStream(filePath);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String)enuKeys.nextElement();
				String value = (String)properties.getProperty(key);
				
				if(key.equals("classPath"))
					config.setClassPath(value);
				
				else if(key.equals("descPath"))
					config.setDescPath(value);
						
				else if(key.equals("nameRequired"))
					config.setNameRequired(Boolean.valueOf(value));
				
				else if(key.equals("descRequired"))
					config.setDescRequired(Boolean.valueOf(value));
				
				else if(key.equals("singleRequired"))
					config.setSingleRequired(Boolean.valueOf(value));

				else if(key.equals("autorunRequired"))
					config.setAutorunRequired(Boolean.valueOf(value));

				else if(key.equals("proxyRequired"))
					config.setProxyRequired(Boolean.valueOf(value));

				else if(key.equals("nameDefault")){
					if(!config.isNameRequired()){
						config.setNameDefault(value);
					}
				}
				else if(key.equals("descDefault")){
					if(!config.isDescRequired()){
						config.setDescDefault(value);
					}
				}

				else if(key.equals("singleDefault")){
					if(!config.isSingleRequired()){
						config.setSingleDefault(Boolean.valueOf(value));
					}
				}

				else if(key.equals("autorunDefault")){
					if(!config.isAutorunRequired()){
						config.setAutorunDefault(Boolean.valueOf(value));
					}
				}

				else if(key.equals("proxyDefault")){
					if(!config.isProxyRequired()){
						config.setProxyDefault(Boolean.valueOf(value));
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();	
	}
		
		return config;
	}
}
