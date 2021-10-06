package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class Felicitari {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public Felicitari(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String popupFelicitari = "xpath:=//div[@class='congrats-text']";
	private String btnAgentDashboard = "xpath:=//button[@data-automation-id='Agent-Dashboard-btn']";
	private String btnCustomerDashboard = "xpath:=//button[@data-automation-id='Customer-Dashboard-btn']";
	
	//Methods
	public boolean isPageDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(popupFelicitari, "Felicitari", 5) == false) return false;
		CommonFunctions.fCommonSpinnerSync(300);
		return true;
	}
	
	public boolean clickAgentDashboard(){
		if(CommonFunctions.fCommonClick(btnAgentDashboard, "Agent Dashboard") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean clickCustomerDashboard(){
		if(CommonFunctions.fCommonClick(btnCustomerDashboard, "Customer Dashboard") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}


	

	
	
}


