package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class Detalii {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public Detalii(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String tabDetails = "xpath:=//span[text()='DETALII CLIENT']";
	private String inputFirstName = "xpath:=//input[@data-automation-id='PRENUME']";
	private String inputLastName = "xpath:=//input[@data-automation-id='NUME']";
	private String inputEmail = "xpath:=//input[@data-automation-id='E-MAIL CONTACT']";
	private String inputTelefonContact = "xpath:=//input[@data-automation-id='TELEFON CONTACT']";
	private String checkDisAddPhone = "xpath:=//input[@name='disableAdditionalPhone']";
	private String selectPostalCodeArrow = "xpath:=//*[@data-automation-id='postalCode']//*[@class='Select-arrow']";
	private String txtPostalCodeByIndex = "xpath:=//*[@data-automation-id='postalCode']//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String inputCodClientDonor = "xpath:=//input[@name='customerId']";
	private String btnContinuă = "xpath:=//button[@data-automation-id='summary-configuration-submit-btn']";
	private String btnLivrare="xpath:=//*[@data-automation-id='split-device-service' and @value='true']/../span";
	private String radiobtnAșteaptăportarea="xpath:=//*[@value='pendingSuccessfulPortIn']";
	private String modificarepopup="xpath:=//*[contains(text(),'Modificare tip comandă')]";
	private String btnModifica="xpath:=//*[contains(text(),'MODIFICĂ COMANDĂ')]";
	//private String msisdn="xpath:=//*[@class='simple-text block']";
	private String msisdn="xpath:=//label[@class='acquisition-flow-title']";
	//Methods
	
	
	public boolean setPrenume(String name){
		if(CommonFunctions.fCommonSetValueEditBox(inputFirstName, "PRENUME", name, "Y", "Y") == false) return false;
		return true;
	}
	public boolean isPageDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(tabDetails, "DETALII", 40) == false) return false;
		CommonFunctions.fCommonSpinnerSync(300);
		return true;
	}
	public boolean ismodificarepopupDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(modificarepopup, "Modificare tip comandă", 40) == false) return false;
		return true;
	}
	public boolean clickAșteaptăportarea(){
		if (CommonFunctions.fCommonClick(radiobtnAșteaptăportarea, "Click on Așteaptă portarea") == false) return false;
		return true;
	}
	public boolean clickModifica(){
		if (CommonFunctions.fCommonClick(btnModifica, "Click on button Modifica Comanda") == false) return false;
		return true;
	}
	
	public boolean setNume(String name){
		if(CommonFunctions.fCommonSetValueEditBox(inputLastName, "NUME", name, "Y", "Y") == false) return false;
		return true;
	}

	public boolean setEmail(String mail){
		if (CommonFunctions.fCommonSetValueEditBox(inputEmail, "E-MAIL CONTACT", mail, "Y", "Y") == false) return false;
		return true;
	}

	public boolean setTelefonContact(String number){
		if (CommonFunctions.fCommonSetValueEditBox(inputTelefonContact, "TELEFON CONTACT", number, "Y", "Y") == false) return false;
		return true;
	}
	
	public boolean clickLivrare(){
		if (CommonFunctions.fCommonClick(btnLivrare, "Click on Livrare dispozitiv în tranșe") == false) return false;
		return true;
	}
	
	public boolean clickNuTelefonAlternativ(){
		if (CommonFunctions.fCommonClick(checkDisAddPhone, "Clientul nu are un număr de telefon alternativ") == false) return false;
		return true;
	}
	public boolean openCodPoȘtalList(){
		if (CommonFunctions.fCommonClick(selectPostalCodeArrow, "COD POȘTAL list") == false) return false;
		return true;
	}
	public String getmsisdn(){
		return CommonFunctions.fCommonGetText(msisdn, "fetching Msisdn", 10).replace(" ", "").trim();
		
	}
	public boolean selectCodPoȘtalByIndex(String index){
		if (CommonFunctions.fCommonClick(selectPostalCodeArrow, "COD POȘTAL list") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		if (CommonFunctions.fCommonClick(txtPostalCodeByIndex.replace("<INDEX>", index), "COD POȘTAL item") == false) return false;
		return true;
	}

	public boolean setCodClientDonor(String value){
		if (CommonFunctions.fCommonSetValueEditBox(inputCodClientDonor, "COD CLIENT DONOR", value, "Y", "Y") == false) return false;
		return true;
	}

	public boolean clickContinuă(){
		if (CommonFunctions.fCommonClick(btnContinuă, "Continuă") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}
	
}


