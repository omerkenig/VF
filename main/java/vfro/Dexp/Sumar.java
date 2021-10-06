package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class Sumar {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public Sumar(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String tabSumar = "xpath:=//div[@class='summary']";
	private String btnvodafoneLogo="xpath:=//img[@class='logo-vodafone-header' and @alt='logo-vodafone']";
	private String intrerupereComanda="xpath:=//h3[text()='Întrerupere Comanda']";
	private String btnsalveazaȘiIesi="xpath:=//*[contains(text(),'ȘTERGE ȘI IEȘI')]";
	private String btnpersonalDetails="xpath:=//button[@data-automation-id='apply-personal-details-to-contact']";
	private String btntrimitePeEmail="xpath:=//button[@data-automation-id='apply-personal-details-to-contact']/../div[contains(@class,'agent-dropdown')]/div/span[text()='Trimite pe email']";
	private String popupTrimitePeEmail="xpath:=//label[text()='Trimite pe email']";
	private String btnTrimite="xpath:=//button[@class='confirmationButton primary bold' and @data-automation-id='save-popup-save-button']";
	private String btnProfilareVodafoneDaToateOff = "xpath:=//input[@data-automation-id='automation-vf-advanceProfiling' and @value='false']/../span";
	private String btnProfilarePartnerDaToateOff = "xpath:=//input[@data-automation-id='automation-partner-advanceProfiling' and @value='false']/../span";
	private String btnContinuă = "xpath:=//button[@data-automation-id='summary-configuration-submit-btn']";
	private String btnTrimite2="xpath:=//*[contains(text(),'TRIMITE')] ";
	private String txtreason="xpath:=//*[@data-automation-id='select-reason-code']";
	private String linkTooltip="xpath:=//*[@class='info_tooltip']";
	

	//Methods
	public boolean isPageDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(tabSumar, "SUMAR", 25) == false) return false;
		CommonFunctions.fCommonSpinnerSync(300);
		return true;
	}
	public boolean isTrimitePopUpDispaly(){
		if(CommonFunctions.fCommonIsDisplayed(popupTrimitePeEmail, "Trimite pe email", 15) == false) return false;
		return true;
	}
	public boolean vodafoneLogo(){
		if (CommonFunctions.fCommonClick(btnvodafoneLogo, "Click on Vodafone Logo") == false) return false;
		return true;
	}
	public boolean ClicklinkTooltip(){
		if (CommonFunctions.fCommonClick(linkTooltip, "Click on Tool tip") == false) return false;
		return true;
	}
	public boolean clickTrimitebtn(){
		if (CommonFunctions.fCommonClick(btnTrimite2, "Click on Trimite button") == false) return false;
		return true;
	}
	public boolean isIntrerupereComandaDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(intrerupereComanda, "intrerupereComanda", 10) == false) return false;
		return true;
	}
	public boolean handlePopUp(){
		if (CommonFunctions.fCommonClick(btnsalveazaȘiIesi, "Perform cancel order") == false) return false;
		CommonFunctions.fCommonSpinnerSync(300);
		return true;
	}
	public boolean selectReasonCode(String rcode){
		if (CommonFunctions.fCommonSelectionOptionFromList(txtreason, "REASON CODE", rcode, "ByValue") == false) return false; 
		return true;
	}
	public boolean clickPersonalDetails(){
		if (CommonFunctions.fCommonClick(btnpersonalDetails, "Click on Personal details") == false) return false;
		return true;
	}
	public boolean clickTrimitePeEmail(){
		JavascriptExecutor js=(JavascriptExecutor) driver;
		WebElement btn=driver.findElement(By.xpath("//button[@data-automation-id='apply-personal-details-to-contact']/../div[contains(@class,'agent-dropdown')]/div/span[text()='Trimite pe email']"));
		js.executeScript("arguments[0].click();", btn);
		return true;
	}
	public boolean clickTrimite(){
		if (CommonFunctions.fCommonClick(btnTrimite, "Click on Trimite") == false) return false;
		return true;
	}
	public boolean toggleProfilareVodafoneDaToateOn(){
		if (CommonFunctions.fCommonClick(btnProfilareVodafoneDaToateOff, "Toggle on PROFILARE - Vodafone") == false) return false;
		return true;
	}
	public boolean toggleProfilarePartnerDaToateOn(){
		if (CommonFunctions.fCommonClick(btnProfilarePartnerDaToateOff, "Toggle on PROFILARE - Partner") == false) return false;
		return true;
	}

	public boolean clickContinuă(){
		if(CommonFunctions.fCommonClick(btnContinuă, "Continuă") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}


	

	
	
}


