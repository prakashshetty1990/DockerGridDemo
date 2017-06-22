package com.sun.pages;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.sun.utils.TestSettings;



public class IllinoisApplication {

	private WebDriver browser;
	private ExtentTest report;
	private String url;
	private TestSettings testSettings;
	private String env;
	private Page page;

	public IllinoisApplication(WebDriver browser, ExtentTest report) {
		this.browser = browser;
		TestSettings testSettings = new TestSettings();
		this.url = testSettings.getApplicationURL();
		this.report = report;		
	}

	public IllinoisLifeInsurePage openRelevantApplication() {		
		IllinoisLifeInsurePage IllinoisLifeInsurePage = openIllinoisApplication();
		//IllinoisLifeInsurePage illinoisLifeInsurePage = illinoisHomePage.navigateToLifeInsure();
		return IllinoisLifeInsurePage;
	}


	public IllinoisLifeInsurePage openIllinoisApplication() {
		try {
			browser.get(url);
			browser.manage().window().maximize();
			//report.updateTestLog("OpenApplication", "Opened Application Successfully", Status.SCREENSHOT);			
		} catch (Exception e) {			
			//report.reportFailEvent("OpenApplication", "Not able to Open Application -> "+e.getMessage());
		}
		return new IllinoisLifeInsurePage(browser, report);

	}
	
	/**
	 * method to make a thread sleep for customized time in milliseconds
	 * @param milliseconds
	 */
	protected void sleep(int milliseconds){
		 try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteAllCookies() {
		try{
			browser.manage().deleteAllCookies();
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	public void close() throws Exception {
		try{
			deleteAllCookies();
			browser.quit();
		}catch(Exception Ex){
			System.out.println("Unable to Close Application");
		}finally{
			sleep(2500);
			if(isProcessRunging("iexplore.exe"))
			{
				killProcess("iexplore.exe");
				System.out.println("killing iexplore");
			}
		}
		
	}

		private static final String TASKLIST = "tasklist";
		private static final String KILL = "taskkill /IM ";

		public static boolean isProcessRunging(String serviceName) throws Exception {

			Process p = Runtime.getRuntime().exec(TASKLIST);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {

				//System.out.println(line);
				if (line.contains(serviceName)) {
					return true;
				}
			}

			return false;

		}

		public static void killProcess(String serviceName) throws Exception {

			Runtime.getRuntime().exec(KILL + serviceName);

		}
}
