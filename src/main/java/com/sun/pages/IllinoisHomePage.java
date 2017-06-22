package com.sun.pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Represents default page of the application
 * 
 */
public class IllinoisHomePage extends Page {



	@FindBy(xpath="//*[@id='picker']/ul/li/center/span")
	private WebElement lnkSelectOne;
	
	@FindBy(xpath="//*[@id='picker']//a[contains(text(),'Life Insurance')]")
	private WebElement lnkLifeInsurance;
	
	
	protected static String HOME_PAGE_TITLE = "Illinois Mutual Life Insurance Company";

	public IllinoisHomePage(WebDriver browser, ExtentTest report) {
		super(browser, report);		
	}

	@Override
	protected boolean isValidPage() {
		if (browser.getTitle().trim().contains(HOME_PAGE_TITLE)) {
			return true;
		}
		return false;
	}

	@Override
	protected void waitForPageLoad() {
		try{
			new WebDriverWait(browser,30).
			until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='picker']/ul/li/center/span")));				
		}catch(Exception e){
			System.out.println(e.getMessage());
			report.log(LogStatus.FAIL, "Home Page is not displayed");			
		}


	}
	
	
	public IllinoisLifeInsurePage navigateToLifeInsure(){
		waitForElement(lnkSelectOne);				
			Actions action = new Actions(browser);
			action.moveToElement(lnkSelectOne).click().perform();
			waitForElement(lnkLifeInsurance);
			lnkLifeInsurance.click();
			sleep(2000);
			Set<String> handles = browser.getWindowHandles();
			if(handles.size()>1){
				report.log(LogStatus.INFO, "Clicked on Link Life Insurance successfully");	
				//report.reportDoneEvent("ClickonLink", "Clicked on Link Life Insurance successfully");				
			}else{
				jsClickLink(lnkLifeInsurance, "Life Insurance");
			}
				
		navigatoToWindow("Your 5G QUOTE Information");
		return new IllinoisLifeInsurePage(browser, report);		
	}

	

}



