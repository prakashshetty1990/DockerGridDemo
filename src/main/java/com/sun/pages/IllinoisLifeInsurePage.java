package com.sun.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.data.IllinoisDetails;

/**
 * Represents default page of the application
 * 
 */
public class IllinoisLifeInsurePage extends Page {



	@FindBy(xpath="//*[@id='LICustomerHeader1_btnQuote']")
	private WebElement btnGetQuote;
		
	@FindBy(id="rptPNT20YDiv")
	private WebElement btnPreferredNonTobacco20;
	
	@FindBy(id="rptPNT30YDiv")
	private WebElement btnPreferredNonTobacco30;
	
	@FindBy(id="rptSNT20YDiv")
	private WebElement btnStandardNonTobacco20;
	
	@FindBy(id="rptSNT30YDiv")
	private WebElement btnStandardNonTobacco30;
	
	@FindBy(id="rptST20YDiv")
	private WebElement btnStandardTobacco20;
	
	@FindBy(id="rptST30YDiv")
	private WebElement btnStandardTobacco30;
	
	@FindBy(xpath="//*[@id='LICustomerHeader1_stateDrpDwn']")
	private WebElement weState;

	@FindBy(xpath="//*[@id='LICustomerHeader1_txtAge']")
	private WebElement weAge;
	
	@FindBy(xpath="//*[@id='LICustomerHeader1_protectionSlnDrpDwn']")
	private WebElement weProtectionSoln;
	
	@FindBy(xpath="//*[@id='LICustomerHeader1_txtProtectAmt']")
	private WebElement weProtectionAmount;

	@FindBy(xpath="//*[@id='LICustomerHeader1_rdoBtnGender_0']")
	private WebElement rbMale;
	
	@FindBy(xpath="//*[@id='LICustomerHeader1_rdoBtnGender_1']")
	private WebElement rbFemale;
	
	@FindBy(xpath="//*[@id='LICustomerHeader1_rdoBtnNicotineYesNo_0']")
	private WebElement rbNicotineNo;
	
	@FindBy(xpath="//*[@id='LICustomerHeader1_rdoBtnNicotineYesNo_1']")
	private WebElement rbNicotineYes;
	
	@FindBy(xpath="//*[@id='LICustomerHeader1_btnQuote']")
	private WebElement btnGetMyQoute;
	
	@FindBy(id="issueAgeInfoDiv")
	private WebElement weAgeIssue;
	
	@FindBy(xpath="//*[@id='rptTable']")
	private WebElement tblROPterm; 
	
	
	protected static String HOME_PAGE_TITLE = "Your 5G QUOTE Information";

	public IllinoisLifeInsurePage(WebDriver browser, ExtentTest report) {
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
			until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='LICustomerHeader1_btnQuote']")));				
		}catch(Exception e){
			System.out.println(e.getMessage());
			report.log(LogStatus.FAIL, "Home Page is not displayed");
			//report.reportFailEvent("Check Illinois Life Insurance Home Page","Home Page is not displayed");
		}
	}


	public void getMyQuote(IllinoisDetails illinoisDetails){	
		Select sel = new Select( weState);
		sel.selectByVisibleText(illinoisDetails.State);
		        
		enterText(weAge,illinoisDetails.Age);
				
		Select sel1 = new Select( weProtectionSoln);		
		sel1.selectByVisibleText(illinoisDetails.ProtectionSol);
				
		enterText(weProtectionAmount,illinoisDetails.ProtectionAmount);  
		
		if(illinoisDetails.Gender.equals("Male")){			
			clickOnLink(rbMale);			
		}else{			
			clickOnLink(rbFemale);			
		}               
		
		if(illinoisDetails.TobaccoUse.contains("No")){			
			clickOnLink(rbNicotineNo);			
		}else{			
			clickOnLink(rbNicotineYes);			
		}		
		//report.updateTestLog("EnterValues", "Entered all the values", Status.SCREENSHOT);
		clickOnLink(btnGetMyQoute);
		report.log(LogStatus.INFO, "Clicked on Get My Qoute Button");
		//report.reportDoneEvent("ClickOnGetMyQoute", "Clicked on Get My Qoute Button");				
	}
	
	
	public void verifyPathProtectorreturnOfPremiumTerms(IllinoisDetails illinoisDetails){
		if(Integer.parseInt(illinoisDetails.Age)<18||Integer.parseInt(illinoisDetails.Age)>75){
			waitForElement(weAgeIssue);
			if(isElementPresent(weAgeIssue)){
				report.log(LogStatus.PASS, "Expected error message is displayed "+ weAgeIssue.getText());
				//report.reportPassEvent("VerifyErrorMessage", "Expected error message is displayed "+ weAgeIssue.getText());
			}else{
				report.log(LogStatus.FAIL, "Expected error message is not displayed");
				//report.reportFailEvent("VerifyErrorMessage", "Expected error message is not displayed");
			}
			takeScreenshot("Pass");
		}else{
			waitForElement(tblROPterm);
			if(isElementPresent(tblROPterm)){
				
				//report.updateTestLog("VerifyPremiumTable", "Premium Table is displayed as expected",Status.SCREENSHOT);
				String strPreferredNonTobacco20 = btnPreferredNonTobacco20.getText();
				if(strPreferredNonTobacco20.trim().equals(illinoisDetails.PreferredNonTobacco20)){
					report.log(LogStatus.PASS, "Premium displayed is as expected->"+strPreferredNonTobacco20);
					//report.reportPassEvent("PreferredNonTobacco 20 TermPremium", "Premium displayed is as expected->"+strPreferredNonTobacco20);
				}else{
					report.log(LogStatus.FAIL, "expected->"+illinoisDetails.PreferredNonTobacco20+" but actual is->"+strPreferredNonTobacco20);
					//report.reportFailEvent("PreferredNonTobacco 20 TermPremium", "expected->"+illinoisDetails.PreferredNonTobacco20+" but actual is->"+strPreferredNonTobacco20);
				}
				
				String strPreferredNonTobacco30 = btnPreferredNonTobacco30.getText();
				if(strPreferredNonTobacco30.trim().equals(illinoisDetails.PreferredNonTobacco30)){
					report.log(LogStatus.PASS, "Premium displayed is as expected->"+strPreferredNonTobacco30);
					//report.reportPassEvent("PreferredNonTobacco 30 TermPremium", "Premium displayed is as expected->"+strPreferredNonTobacco30);
				}else{
					report.log(LogStatus.FAIL, "expected->"+illinoisDetails.PreferredNonTobacco30+" but actual is->"+strPreferredNonTobacco30);
					//report.reportFailEvent("PreferredNonTobacco 30 TermPremium", "expected->"+illinoisDetails.PreferredNonTobacco30+" but actual is->"+strPreferredNonTobacco30);
				}
				
				
				String strStandardNonTobacco20 = btnStandardNonTobacco20.getText();
				if(strStandardNonTobacco20.trim().equals(illinoisDetails.StandardNonTobacco20)){
					report.log(LogStatus.PASS, "Premium displayed is as expected->"+strStandardNonTobacco20);
					//report.reportPassEvent("StandardNonTobacco 20 TermPremium", "Premium displayed is as expected->"+strStandardNonTobacco20);
				}else{
					report.log(LogStatus.FAIL, "expected->"+illinoisDetails.StandardNonTobacco20+" but actual is->"+strStandardNonTobacco20);
					//report.reportFailEvent("StandardNonTobacco 20 TermPremium", "expected->"+illinoisDetails.StandardNonTobacco20+" but actual is->"+strStandardNonTobacco20);
				}
				
				String strStandardNonTobacco30 = btnStandardNonTobacco30.getText();
				if(strStandardNonTobacco30.trim().equals(illinoisDetails.StandardNonTobacco30)){
					report.log(LogStatus.PASS, "Premium displayed is as expected->"+strStandardNonTobacco30);
					//report.reportPassEvent("StandardNonTobacco 20 TermPremium", "Premium displayed is as expected->"+strStandardNonTobacco30);
				}else{
					report.log(LogStatus.FAIL, "expected->"+illinoisDetails.StandardNonTobacco30+" but actual is->"+strStandardNonTobacco30);
					//report.reportFailEvent("StandardNonTobacco 20 TermPremium", "expected->"+illinoisDetails.StandardNonTobacco30+" but actual is->"+strStandardNonTobacco30);
				}
				
				String strStandardTobacco20 = btnStandardTobacco20.getText();
				if(strStandardTobacco20.trim().equals(illinoisDetails.StandardTobacco20)){
					report.log(LogStatus.PASS, "Premium displayed is as expected->"+strStandardTobacco20);
					//report.reportPassEvent("StandardNonTobacco 20 TermPremium", "Premium displayed is as expected->"+strStandardTobacco20);
				}else{
					report.log(LogStatus.FAIL, "expected->"+illinoisDetails.StandardTobacco20+" but actual is->"+strStandardTobacco20);
					//report.reportFailEvent("StandardNonTobacco 20 TermPremium", "expected->"+illinoisDetails.StandardTobacco20+" but actual is->"+strStandardTobacco20);
				}
				
				String strStandardTobacco30 = btnStandardTobacco30.getText();
				if(strStandardTobacco30.trim().equals(illinoisDetails.StandardTobacco30)){
					report.log(LogStatus.PASS, "Premium displayed is as expected->"+strStandardTobacco30);
					//report.reportPassEvent("StandardNonTobacco 20 TermPremium", "Premium displayed is as expected->"+strStandardTobacco30);
				}else{
					report.log(LogStatus.FAIL, "expected->"+illinoisDetails.StandardTobacco30+" but actual is->"+strStandardTobacco30);
					//report.reportFailEvent("StandardNonTobacco 20 TermPremium", "expected->"+illinoisDetails.StandardTobacco30+" but actual is->"+strStandardTobacco30);
				}
				takeScreenshot("Pass");
			}else{
				report.log(LogStatus.FAIL, "Premium Table is not displayed as expected");
				//report.reportFailEvent("VerifyPremiumTable", "Premium Table is not displayed as expected");
			}
			
		}
	}

}







