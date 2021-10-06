package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class AbonamentNou {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public AbonamentNou(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String popupNewsub = "xpath:=//*[@class='wrap-popup new-subscriber-popup']";
	private String tabNumărNou = "xpath:=//li[starts-with(@class, 'tab')]/span[text()='NUMĂR NOU']";
	private String tabPortare = "xpath:=//li[starts-with(@class, 'tab')]/span[text()='PORTARE']";
	private String tabMigrareDeLaPrepaid="xpath:=//li[starts-with(@class, 'tab')]/span[text()='MIGRARE DE LA PREPAID']";
	private String radioAgreeCustConsent = "xpath:=//input[@data-automation-id='agree-customer-consent']";
	private String inputNumber = "xpath:=//*[@name='contactNumber' or @name='portedNumber' or @name='numberToMigrate']";
	private String btnUrmătoarele = "xpath:=//button[@data-automation-id='subscribers-popup-continue-button']";
	private String inputFirstName = "xpath:=//input[@data-automation-id='PRENUME']";
	private String inputLastName = "xpath:=//input[@data-automation-id='NUME']";
	private String selectProviders = "xpath:=//select[@class='providers']";
	private String selectCountryArrow = "xpath:=//*[@data-automation-id='county']//*[@class='Select-arrow']";
	private String txtCountryByIndex = "xpath:=//*[@data-automation-id='county']//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String selectCityArrow = "xpath:=//*[@data-automation-id='cities']//*[@class='Select-arrow']";
	private String txtCityByIndex = "xpath:=//*[@data-automation-id='cities']//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String selectStreetTypeArrow = "xpath:=//*[@data-automation-id='streetType']//*[@class='Select-arrow']";
	private String txtStreetTypeByIndex = "xpath:=//*[@data-automation-id='streetType']//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String inputStreet = "xpath:=//*[@data-automation-id='streets']//*[@class='Select-input']/input";
	private String txtStreetByIndex = "xpath:=//*[@data-automation-id='streets']//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String btnContinuă = "xpath:=//button[@data-automation-id='subscribers-popup-continue-button']";
	
	//Methods
	public boolean isPageDisplay(){
		CommonFunctions.fCommonSpinnerSync(10);
		if(CommonFunctions.fCommonIsDisplayed(popupNewsub, "ABONAMENT NOU", 5) == false) return false;
		CommonFunctions.fCommonSpinnerSync(300);
		return true;
	}
	
	public boolean clickNumărNouTab(){
		if (CommonFunctions.fCommonClick(tabNumărNou, "NUMĂR NOU tab") == false) return false; 
		return true;
	}

	public boolean clickPortareTab(){
		if (CommonFunctions.fCommonClick(tabPortare, "PORTARE") == false) return false; 
		return true;
	}
	public boolean clickMigrareDeLaPrepaidtab(){
		if(CommonFunctions.fCommonClick(tabMigrareDeLaPrepaid, "MIGRARE DE LA PREPAID")==false)return false;
		return true;
	}
	public boolean clickDa(){
		if (CommonFunctions.fCommonClick(radioAgreeCustConsent, "CONSIMȚĂMÂNT CLIENT") == false) return false;
		return true;
	}
	public boolean setNumărContact(String contact){
		if(CommonFunctions.fCommonSetValueEditBox(inputNumber, "NUMĂR CONTACT", contact, "Y", "Y") == false) return false;
		return true;
	}
	public boolean setNumărContact(){
		String number = Environment.get("CONTACT_NUM_PREFIX") + RandomStringUtils.randomNumeric(8);
		if(CommonFunctions.fCommonSetValueEditBox(inputNumber, "NUMĂR CONTACT", number, "Y", "Y") == false) return false;
		return true;
	}
	
	public boolean clickUrmătoarele (){
		if (CommonFunctions.fCommonClick(btnUrmătoarele, "URMĂTOARELE") == false) return false;
		CommonFunctions.fCommonSpinnerSync(10);
		return true;
	}

	public boolean selectProviderByIndex(String index){
		if (CommonFunctions.fCommonSelectionOptionFromList(selectProviders, "DONOR", index, "ByIndex") == false) return false; 
		return true;
	}

	public boolean setPrenume(String name){
		if(CommonFunctions.fCommonSetValueEditBox(inputFirstName, "PRENUME", name, "Y", "Y") == false) return false;
		return true;
	}
	
	public boolean setNume(String name){
		if(CommonFunctions.fCommonSetValueEditBox(inputLastName, "NUME", name, "Y", "Y") == false) return false;
		return true;
	}
	
	public boolean openJudetList(){
		if (CommonFunctions.fCommonClick(selectCountryArrow, "JUDEȚ list") == false) return false;
		return true;
	}

	public boolean selectJudetByIndex(String index){
		if (CommonFunctions.fCommonClick(selectCountryArrow, "JUDEȚ list") == false) return false;
		if (CommonFunctions.fCommonClick(txtCountryByIndex.replace("<INDEX>", index), "JUDEȚ item") == false) return false;
		return true;
	}

	public boolean openLocalitateList(){
		if (CommonFunctions.fCommonClick(selectCityArrow, "LOCALITATE list") == false) return false;
		return true;
	}

	public boolean selectLocalitateByIndex(String index){
		if (CommonFunctions.fCommonClick(selectCityArrow, "LOCALITATE list") == false) return false;
		if (CommonFunctions.fCommonClick(txtCityByIndex.replace("<INDEX>", index), "LOCALITATE item") == false) return false;
		return true;
	}

	public boolean openTipStradăList(){
		if (CommonFunctions.fCommonClick(selectStreetTypeArrow, "LOCALITATE list") == false) return false;
		return true;
	}

	public boolean selectTipStradăByIndex(String index){
		if (CommonFunctions.fCommonClick(selectStreetTypeArrow, "LOCALITATE list") == false) return false;
		if (CommonFunctions.fCommonClick(txtStreetTypeByIndex.replace("<INDEX>", index), "TIP STRADĂ item") == false) return false;
		return true;
	}

	public boolean setNumeStradă(String name){
		if(CommonFunctions.fCommonSetValueEditBox(inputStreet, "NUME STRADĂ", name, "Y", "Y") == false) return false;
		return true;
	}
	
	public boolean selectNumeStradăByIndex(String index){
		if (CommonFunctions.fCommonClick(txtStreetByIndex.replace("<INDEX>", index), "NUME STRADĂ item") == false) return false;
		return true;
	}

	public boolean clickContinuă(){
		if (CommonFunctions.fCommonClick(btnContinuă, "Continuă") == false) return false;
		CommonFunctions.fCommonSpinnerSync(10);
		return true;
	}

	
	
}


