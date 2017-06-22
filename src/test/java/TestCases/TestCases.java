package TestCases;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.sun.data.IllinoisDetails;
import com.sun.pages.IllinoisApplication;
import com.sun.pages.IllinoisLifeInsurePage;
import com.sun.utils.ReportPath;
import com.sun.utils.SunAutoTest;

public class TestCases extends SunAutoTest {

	private IllinoisApplication illinoisApplication;
	private IllinoisDetails illinoisDetails;	
	private IllinoisLifeInsurePage illinoisLifeInsurePage;	
	
	@BeforeClass
	@Parameters({"selenium.browser"})
	public void setUp(String browserName){
		ReportPath reportPath = new ReportPath();
		String strReportPath=reportPath.getReportPath(browserName);			
		extent = new ExtentReports(strReportPath+"/Results.html", true);				
	}
	
	@BeforeMethod
	@Parameters({"selenium.machinename","selenium.host", "selenium.port", "selenium.browser", "selenium.os", "selenium.browserVersion", "selenium.osVersion"})
	public void openApplication(String machineName,String host,String port,String browserName,String os,String browserVersion,String osVersion,Method method){				
		browser=openBrowser(machineName, host, port, browserName, os, browserVersion, osVersion);		
		String testCaseName = method.getName();
		report = extent.startTest(testCaseName, "<font size=4 color=black>" +testCaseName+ "</font><br/>");
		setupData(testCaseName);		
		illinoisApplication = new IllinoisApplication(browser, report);
		illinoisLifeInsurePage = illinoisApplication.openRelevantApplication();
	}
	
	
	@Test
	public void testcase01(){		
		illinoisDetails = IllinoisDetails.loadFromDatatable(dataTable);
		illinoisLifeInsurePage.getMyQuote(illinoisDetails);
		illinoisLifeInsurePage.verifyPathProtectorreturnOfPremiumTerms(illinoisDetails);		
	}
	
	
	@Test
	public void testcase02(){		
		illinoisDetails = IllinoisDetails.loadFromDatatable(dataTable);
		illinoisLifeInsurePage.getMyQuote(illinoisDetails);
		illinoisLifeInsurePage.verifyPathProtectorreturnOfPremiumTerms(illinoisDetails);		
	}
	
	
	@Test
	public void testcase03(){
		illinoisDetails = IllinoisDetails.loadFromDatatable(dataTable);
		illinoisLifeInsurePage.getMyQuote(illinoisDetails);
		illinoisLifeInsurePage.verifyPathProtectorreturnOfPremiumTerms(illinoisDetails);		
	}
	
	
	@Test
	public void testcase04(){
		illinoisDetails = IllinoisDetails.loadFromDatatable(dataTable);
		illinoisLifeInsurePage.getMyQuote(illinoisDetails);
		illinoisLifeInsurePage.verifyPathProtectorreturnOfPremiumTerms(illinoisDetails);
	}
	
	
	@Test
	public void testcase05(){
		illinoisDetails = IllinoisDetails.loadFromDatatable(dataTable);
		illinoisLifeInsurePage.getMyQuote(illinoisDetails);
		illinoisLifeInsurePage.verifyPathProtectorreturnOfPremiumTerms(illinoisDetails);
	}
	
	
		
	@AfterMethod
	public void closeAppication() throws Exception{		
		extent.endTest(report);
		extent.flush();
		illinoisApplication.close();		
	}
	

}
