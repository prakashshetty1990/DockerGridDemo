package com.sun.utils;


import io.appium.java_client.AppiumDriver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


/**
 * Base class for all Sun Auto Test
 *
 */
public class SunAutoTest extends TestListenerAdapter{
	private long startTime;
	private long endTime;
	private static String watchedLog;		
	protected DataTable dataTable;
	//protected SeleniumReport report;
	protected WebDriver browser;
	protected TestSettings settings;
	protected ExtentReports extent;	
	protected ExtentTest report;
	protected ExtentTest child;

	@Override
	public void onTestStart(ITestResult tr) {

	}



	@Override
	public void onTestSuccess(ITestResult tr) {


	}

	@Override
	public void onTestFailure(ITestResult tr) {


	}

	@Override
	public void onTestSkipped(ITestResult tr) {

	}



	public void setupData(String method) {
		settings=new TestSettings();		
		String testCaseName=null;
		if(method.contains("[")){
			testCaseName = method.substring(0,method.indexOf('['));
		}else{
			testCaseName = method;
		}		
		startTime = System.currentTimeMillis();
		try{
			dataTable = new DataTable(testCaseName);	
		}catch(Exception e){				
			browser.quit();			
			System.out.println("Test Data Row not present. Please check-> "+e.getMessage());
		}				
	}


	/**
	 * Initialize Test Report
	 * @param testCaseName
	 */
	protected void initializeReport(String browserName) {

		//String reportPath=ReportPath.getInstance().getReportPath(browserName);
		//extent = new ExtentReports(reportPath+"/Results_"+browserName+".html", true);

		/*ReportSettings reportSettings = new ReportSettings(reportPath, testCaseName);
		reportSettings.generateExcelReports=false;
		reportSettings.generateHtmlReports=true;
		reportSettings.includeTestDataInReport=false;
		reportSettings.takeScreenshotFailedStep=true;
		reportSettings.takeScreenshotPassedStep=false;*/
		/*report = new SeleniumReport(reportSettings, ReportThemeFactory.getReportsTheme(Theme.CLASSIC));
		report.setDriver(browser);
		report.initializeReportTypes();
		report.initializeTestLog();
		report.addTestLogHeading(testCaseName);
		report.addTestLogSubHeading("Browser", settings.getBrowser(), "URL", settings.getApplicationURL());
		report.addTestLogTableHeadings();
		report.addTestLogSection("Detailed Steps");*/
	}


	@AfterMethod
	public void tearDown(){
		endTime=System.currentTimeMillis();
		Long timeTaken=endTime-startTime-20;//-20 is to compensate for things other than test script execution
		int h = (int) ((timeTaken / 1000) / 3600);
		int m = (int) (((timeTaken / 1000) / 60) % 60);
		int s = (int) ((timeTaken / 1000) % 60);
		String time=""+h+":hh "+m+":mm"+s+"ss";
		//report.addTestLogFooter(time);
	}

	/**
	 * Get the browser object specified in the property
	 * @param browserName
	 * @return
	 */
	protected WebDriver getDriver(String browserName) {
		WebDriver driver = null;
		if (browserName.equalsIgnoreCase("firefox")) {

			ProfilesIni profilesIni = new ProfilesIni();

			FirefoxProfile profile = profilesIni.getProfile("default");

			profile.setEnableNativeEvents(true);

			driver = new FirefoxDriver(profile);

		}
		if(browserName.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", 
					TestUtils.getRelativePath()+"/ExternalLibraries/BrowserSpecificDrivers/chromedriver.exe");

			driver=new ChromeDriver();
		}
		if(browserName.equalsIgnoreCase("iexplore") || browserName.equalsIgnoreCase("ie")){
			System.setProperty("webdriver.ie.driver",
					TestUtils.getRelativePath()+"/ExternalLibraries/BrowserSpecificDrivers/IEDriverServer.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			//capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);

			capabilities.setCapability("ACCEPT_SSL_CERTS", true);
			driver=new InternetExplorerDriver(capabilities);

		}
		if(browserName.equalsIgnoreCase("safari")){			
			driver=new SafariDriver();
		}

		return driver;
	}

	public WebDriver openBrowser(String machineName, String host, String port, String browser, String os, String browserVersion, String osVersion){
		WebDriver driver = null;
		Process p; 
		if(machineName.toLowerCase().contains("windows")){
			if (browser.equalsIgnoreCase("firefox")){
				System.out.println(" Executing on FireFox");
				String Node = "http://" + host + ":" + port + "/wd/hub";
				DesiredCapabilities cap = DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");			
				try {
					driver = new RemoteWebDriver(new URL(Node), cap);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Puts an Implicit wait, Will wait for 10 seconds before throwing exception
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);					
			}else if (browser.equalsIgnoreCase("chrome")){
				System.out.println(" Executing on CHROME");
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
				String Node = "http://" + host + ":" + port + "/wd/hub";			

				try {
					driver = new RemoteWebDriver(new URL(Node), cap);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			}else if (browser.equalsIgnoreCase("ie")){
				System.out.println(" Executing on IE");
				DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
				cap.setBrowserName("internet explorer");
				String Node = "http://" + host + ":" + port + "/wd/hub";			
				try {
					driver = new RemoteWebDriver(new URL(Node), cap);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			}else{
				throw new IllegalArgumentException("The Browser Type is Undefined");
			}				
		}else if(machineName.toLowerCase().contains("android")){
			if (browser.equalsIgnoreCase("Androidchrome")){
				try {
					/*String[] commandStartServer = {"cmd.exe", "/C", "Start", System.getProperty("user.dir") + File.separator + "Config\\AppiumStart.bat"};  
						p =  Runtime.getRuntime().exec(commandStartServer);					
						Thread.sleep(20000);*/
					//DesiredCapabilities cap = DesiredCapabilities.android();
					String Node = "http://" + host + ":" + port + "/wd/hub";						
					DesiredCapabilities capabilities = new DesiredCapabilities();
					capabilities.setCapability("deviceName", "Moto G");
					//capabilities.setCapability("chromedriverExecutable", "C:\\New folder\\chromedriver.exe");
					capabilities.setCapability("browserName", "Chrome");
					capabilities.setCapability("platformName", "Android");						
					driver = new RemoteWebDriver(new URL(Node), capabilities);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else if(machineName.toLowerCase().contains("cloud")){
			if (browser.toLowerCase().contains("firefox")){
				System.out.println(" Executing on Cloud Firefox");			
				final String URL = "https://"+ host + ":" + port + "@hub.browserstack.com/wd/hub";			
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("os", os);
				capabilities.setCapability("browser", "firefox");
				capabilities.setCapability("project", "GridProject");
				capabilities.setCapability("os_version", osVersion);			
				try {
					driver = new RemoteWebDriver(new URL(URL), capabilities);
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}					
			}else if(browser.toLowerCase().contains("chrome")){
				System.out.println(" Executing on Cloud Chrome");			
				final String URL = "https://"+ host + ":" + port + "@hub.browserstack.com/wd/hub";			
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("os", os);
				capabilities.setCapability("browser", "chrome");
				capabilities.setCapability("project", "GridProject");
				capabilities.setCapability("os_version", osVersion);			
				try {
					driver = new RemoteWebDriver(new URL(URL), capabilities);
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}else if(browser.toLowerCase().contains("ie")){
				System.out.println(" Executing on Cloud IE");			
				final String URL = "https://"+ host + ":" + port + "@hub.browserstack.com/wd/hub";			
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("os", os);
				capabilities.setCapability("browser", "IE");
				capabilities.setCapability("project", "MyProject");
				capabilities.setCapability("os_version", osVersion);			
				try {
					driver = new RemoteWebDriver(new URL(URL), capabilities);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

		return driver;
	}

	public void reinitializeDriverForReport() {
		browser=getDriver(settings.getBrowser()); 
		//report.setDriver(browser);  
	}


	public void createZipFileOfReport(String reportPath,String testCaseQCName){
		System.out.println(reportPath);
		File dir = new File(reportPath);

		try {
			System.out.println("Getting all files in "
					+ dir.getCanonicalPath()
					+ " including those in subdirectories");
			List<File> files = (List<File>) FileUtils.listFiles(
					dir, TrueFileFilter.INSTANCE,
					TrueFileFilter.INSTANCE);
			byte[] b;

			FileOutputStream fout = new FileOutputStream(reportPath+"\\"
					+ testCaseQCName + ".zip");
			ZipOutputStream zout = new ZipOutputStream(
					new BufferedOutputStream(fout));

			for (int i = 0; i < files.size(); i++) {
				if(files.get(i).getName().contains(testCaseQCName)){
					b = new byte[1024];
					FileInputStream fin = new FileInputStream(
							files.get(i));
					zout.putNextEntry(new ZipEntry(files.get(i)
							.getName()));
					int length;
					while (((length = fin.read(b, 0, 1024))) > 0) {
						zout.write(b, 0, length);
					}
					zout.closeEntry();
					fin.close();	
				}

			}
			zout.close();

		} catch (Exception e) {
			System.out.println("Exception caught");
		}
	}



	public void createResultFile(String reportPath,String runStatus){
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter( new FileWriter(reportPath+"\\result.txt"));
			writer.write(runStatus);

		}
		catch ( IOException e)
		{
		}
		finally
		{
			try
			{
				if ( writer != null)
					writer.close( );
			}
			catch ( IOException e)
			{
			}
		}
	}

	public String getMemoryUsedByApplication(String strAppl) throws IOException{
		Process p = Runtime.getRuntime().exec("tasklist");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.contains(strAppl)) {
				break;
			}
		}
		return line;
	}


}
