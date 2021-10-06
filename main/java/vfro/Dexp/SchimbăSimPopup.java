package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class SchimbăSimPopup {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public SchimbăSimPopup(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String popupSchimbăSimPopup = "xpath:=//*[contains(@class, 'popup')]//*[contains(text(), 'Schimbă SIM')]";
	private String inputSim = "xpath:=//input[@name='newSimNumber']";
	private String btnÎnregistreazăComanda = "xpath:=//button[contains(@class, 'submit')]//*[contains(text(), 'ÎNREGISTREAZĂ COMANDA')]";
	
	//Methods
	public boolean isPageDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(popupSchimbăSimPopup, "Schimbă SIM", 5) == false) return false;
		CommonFunctions.fCommonSpinnerSync(300);
		return true;
	}
	
	public boolean setSim(String sim){
		if(CommonFunctions.fCommonSetValueEditBox(inputSim, "SIM Card", sim, "Y", "Y") == false) return false;
		if(CommonFunctions.fCommonClick(popupSchimbăSimPopup, "Schimbă SIM") == false) return false;  // Click the title to change the focus from the input field
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}
	public boolean clickÎnregistreazăComanda(){
		if(CommonFunctions.fCommonClick(btnÎnregistreazăComanda, "ÎNREGISTREAZĂ COMANDA") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	
}


