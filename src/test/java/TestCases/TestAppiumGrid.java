package TestCases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class TestAppiumGrid {

	//public static void main(String[] args) {
	
	@Test
	public void test(){
		
		WebDriver driver;
		//AppiumDriver driver;
		/*DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");					
		capabilities.setCapability("deviceName", "Moto G");
		capabilities.setCapability("platformVersion", "5.1");
		capabilities.setCapability("platformName", "Android");*/
		String Node = "http://192.168.94.75:5555/wd/hub";	
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");		
		//capabilities.setCapability("udid","353217051493594");
		capabilities.setCapability("deviceName", "Moto G");
		//capabilities.setCapability("platformVersion", "5.1");
		capabilities.setCapability("platformName", "Android");
		//capabilities.setPlatform(org.openqa.selenium.Platform.ANDROID);
		
		try {
			
			//driver = new AppiumDriver(new URL(Node), (Capabilities)capabilities);
			driver = new RemoteWebDriver(new URL(Node), (Capabilities)capabilities);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
