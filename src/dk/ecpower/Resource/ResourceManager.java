package dk.ecpower.Resource;

import java.io.FileInputStream;
import java.util.Properties;

public class ResourceManager {
	
	
	public Properties getPropertyFile(){
		Properties prop = new Properties();
		try{
			FileInputStream fis = new FileInputStream("props/dtf.properties");
			prop.load(fis);
		}catch(Exception ep){
			ep.printStackTrace();
		}
		
		return prop;
		
	}
	
}
