package com.sun.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Read Test settings from framework.propeties files
 *
 */
public class TestSettings {
	Properties properties;
	String key;
	long value;
	public TestSettings(){
		loadProperties();
	}
	/**
	 * Get property value 
	 * @param key property key
	 * @return value of the property
	 */
	public String getSettings(String key){
		return properties.getProperty(key);
	}
	
	/**
	 * Get property value 
	 * @param key property key
	 * @return 
	 * @return value of the property
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public  void setSettings(String key,long value) throws FileNotFoundException, IOException{
		this.key=key;
		this.value=value;
		properties.store(new FileOutputStream("framework.properties"), null);

	}
	/**
	 * Get the browser name in which tests needs to be run
	 */
	public String getBrowser(){
		String browser= System.getenv("browser");
		if(browser==null || browser == ""){
			browser=properties.getProperty("browser","firefox");
		}
		return browser;
	}
	
	

	/**
	 *	Get the URL of the application under test 
	 */
	public String getApplicationURL(){		
		return getSettings("url");				
	}
	
	
	public String getEnvironment() {
		return getSettings("Environment");
	}
	
	
	/**
	 * Load properties from framework.properties file
	 */
	private void loadProperties() {
		properties= new Properties();
		try {
			String relativePath=TestUtils.getRelativePath();
			properties.load(new FileInputStream(relativePath + "//src//test//resources"+ File.separator +"framework.properties"));
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getSheetName() {		
		return getSettings("SheetName");		
	}
	

}
