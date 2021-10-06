package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class SchimbareNumărPopup {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public SchimbareNumărPopup(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String popupSchimbăSimPopup = "xpath:=//*[contains(@class, 'popup')]//*[contains(text(), 'Schimbare număr')]";
	private String btnRezervăNumăr = "xpath:=//button//span[contains(text(), 'REZERVĂ NUMĂR')]";
	private String btnSelectedNumber = "xpath:=//*[starts-with(@class, 'number-box selected')]";
	private String btnTrimite = "xpath:=//button[@data-automation-id='number-allocation-popup-continue-button']";
	private String tabNumberSpecial="xpath:=//*[contains(text(),'NUMĂR SPECIAL')]";
	private String selectȘterge="xpath:=//*[@type='select']";
	private String selectȘtergeIndex="xpath:=//*[@type='select']//option[<INDEX>]";
	private String btnCauta="xpath:=//*[contains(text(),'CAUTĂ')]";
	private String txtNiciun="xpath:=//*[contains(text(),'Niciun număr disponibil. Încearcă alte variante')]";
	private String btnContinue="xpath:=//*[@data-automation-id='number-allocation-popup-continue-button']";
	private String btnÎncarcăDocumente="xpath:=//*[@class='generate-doc-position']";
	
	
	//Methods
	public boolean isPageDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(popupSchimbăSimPopup, "Schimbă SIM", 5) == false) return false;
		CommonFunctions.fCommonSpinnerSync(300);
		return true;
	}
	public boolean clickÎncarcăDocumente(){
		if(CommonFunctions.fCommonClick(btnÎncarcăDocumente, "Încarcă Documente") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public String getNewNumber(){
		return CommonFunctions.fCommonGetText(btnSelectedNumber, "New selected number", 2).split("\\(")[0].replace(" ", "").trim();
	}
	public boolean clicktabNumberSpecial(){
		if(CommonFunctions.fCommonClick(tabNumberSpecial, "Number Special") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}
	public boolean clickbtnCauta(){
		if(CommonFunctions.fCommonClick(btnCauta, "Cauta click successfully") == false) return false;
		return true;
	}
	
	public boolean clickContinue(){
		if(CommonFunctions.fCommonClick(btnContinue, "Continue button successfully click") == false) return false;
		
		return true;
	}
	public boolean selectȘtergeByIndex(Integer index){
		if (CommonFunctions.fCommonClick(selectȘterge, "Șterge list") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		String index1=Integer.toString(index);
		if (CommonFunctions.fCommonClick(selectȘtergeIndex.replace("<INDEX>", index1), "Șterge item") == false) return false;
		return true;
	}
	public String getTextNiciun(){
		return CommonFunctions.fCommonGetText(txtNiciun, "Niciun număr disponibil. Încearcă alte variante", 10);
	}
	public boolean clickRezervăNumăr(){
		if(CommonFunctions.fCommonClick(btnRezervăNumăr, "REZERVĂ NUMĂR") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean clickTrimite(){
		if(CommonFunctions.fCommonClick(btnTrimite, "TRIMITE") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	
}


