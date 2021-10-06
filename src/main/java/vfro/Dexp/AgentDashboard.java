package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class AgentDashboard {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public AgentDashboard(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String btnMsisdn = "xpath:=//div[@data-automation-id='msisdn-button']";
	private String btnCnp = "xpath:=//div[@data-automation-id='cnp-button']";
	private String btnContClient = "xpath:=//div[@data-automation-id='billingID-button']";
	private String btnAdresaInstalare = "xpath:=//div[@data-automation-id='installationAddress-button']";
	private String inputSearch = "xpath:=//input[@data-automation-id='input-search-address-header']";
	private String btnSearch = "xpath:=//button[@data-automation-id='search-button']";
	private String btnWirelessService = "xpath:=//button[@data-automation-id='new-wireless-btn']";
	private String btnFixedService = "xpath:=//button[@data-automation-id='new-fixed-service-btn']";
	private String txtNoResult = "xpath:=//*[contains(text(), 'Nu au fost găsite rezultate pentru')]";
	private String cautăcomenzi="xpath:=//*[contains(text(),'Caută comenzi')]";
	private String invalidCNP="xpath:=//*[@class='title-popup undefined title-height']";
	private String cancelInvalidCNP="xpath:=//*[@data-automation-id='close-cancel-validate-contant-popup']";
	private String clickCancel="xpath:=//*[@class='cancel-icon']";
	String cnp="";
	//Methods
	public boolean isPageDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(inputSearch, "Search customer", 5) == false) return false;
		//CommonFunctions.fCommonSpinnerSync(300);
		return true;
	}
	public boolean isInvalidCNPDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(invalidCNP, "Invalid CNP Displayed", 5) == false) return false;
		return true;
	}
	public void isInvalidCNP(){
		
		if(CommonFunctions.fCommonIsDisplayed(invalidCNP, "Invalid CNP Displayed", 5)); 
		
	}
	public String getInvalidCNPTxt(){
		return CommonFunctions.fCommonGetText2(invalidCNP, "Invalid CNP text", 5);
		
	}
	public boolean clickMsisdn(){
		if (CommonFunctions.fCommonClick(btnMsisdn, "MSISDN") == false) return false; 
		return true;
	}
	public boolean clickcancelInvalidCNP(){
		if (CommonFunctions.fCommonClick(cancelInvalidCNP, "Cancel Invalid CNP") == false) return false; 
		return true;
	}
	public boolean clickCancel(){
		if (CommonFunctions.fCommonClick(clickCancel, "Make CNP filled Null") == false) return false; 
		return true;
	}
	public boolean clickCnp(){
		if (CommonFunctions.fCommonClick(btnCnp, "CNP") == false) return false; 
		return true;
	}
	public boolean clickCautacomenzi(){
		if (CommonFunctions.fCommonClick(cautăcomenzi, "Caută comenzi") == false) return false;
		return true;
	}
	public boolean setSearchValue(String value){
		if(CommonFunctions.fCommonSetValueEditBox(inputSearch, "Search value", value, "Y", "Y") == false) return false;
		return true;
	}
	
	public boolean clickSearch(){
		if (CommonFunctions.fCommonClick(btnSearch, "Search button") == false) return false;
		//CommonFunctions.fCommonSpinnerSync(10);
		return true;
	}

	public boolean clickClientNouMobil(){
		if (CommonFunctions.fCommonClick(btnWirelessService, "CLIENT NOU - MOBILE") == false) return false;
		return true;
	}

	public boolean clickClientNouFix(){
		if (CommonFunctions.fCommonClick(btnFixedService, "CLIENT NOU - FIX") == false) return false;
		return true;
	}
	
	public boolean isNoResultDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(txtNoResult, "Nu au fost găsite rezultate pentru", 5) == false) return false;
		return true;
	}

	public boolean handleInvalidCNP()
	{
		cnpGenerator cnpGenerator = new cnpGenerator(driver, driverType, Dictionary, Environment, Reporter);
		
		Assert.assertTrue(clickcancelInvalidCNP());
		Assert.assertTrue(clickCancel());
		cnp = cnpGenerator.generateCnp();
		Assert.assertTrue(isPageDisplay());
		Assert.assertTrue(setSearchValue(cnp));	
		return true;
	}
	
}


