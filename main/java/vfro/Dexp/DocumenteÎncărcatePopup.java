package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class DocumenteÎncărcatePopup {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public DocumenteÎncărcatePopup(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String popupDocumenteÎncărcatePopup = "xpath:=//*[contains(@class, 'popup')]//*[contains(text(), 'Documente încărcate')]";
	private String btnContinuă = "xpath:=//*[@data-automation-id='primary-button-in-popup']";
	
	//Methods
	public boolean isPageDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(popupDocumenteÎncărcatePopup, "Documente încărcate", 5) == false) return false;
		CommonFunctions.fCommonSpinnerSync(300);
		return true;
	}
	
	public boolean clickContinuă(){
		if(CommonFunctions.fCommonClick(btnContinuă, "CONTINUĂ") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	
}


