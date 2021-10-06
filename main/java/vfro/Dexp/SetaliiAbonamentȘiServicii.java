package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class SetaliiAbonamentȘiServicii {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public SetaliiAbonamentȘiServicii(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String txtTitle = "xpath:=//div[@id='plan-subscription-popup']";
	private String txtSim = "xpath:=//div[@class='general-info-section sim-wrap-flex']//*[@class='each-value']";
	private String btnÎnregistreazăComanda = "xpath:=//button[contains(@class, 'submit')]//*[contains(text(), 'ÎNREGISTREAZĂ COMANDA')]";
	
	//Methods
	public boolean isPageDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(txtTitle, "DETALII ABONAMENT ȘI SERVICII", 5) == false) return false;
		CommonFunctions.fCommonSpinnerSync(300);
		return true;
	}
	
	public String getSim(){
		return CommonFunctions.fCommonGetText(txtSim, "SIM", 2).replace(" ", "").trim();
	}
	public boolean clickÎnregistreazăComanda(){
		if(CommonFunctions.fCommonClick(btnÎnregistreazăComanda, "ÎNREGISTREAZĂ COMANDA") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	
}


