package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class Page360 {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public Page360(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String page360 = "xpath:=//div[@class='header-360']";
	private String btnOpenMenu = "xpath:=//button[@data-automation-id='menu-icon-open']";
	private String txtMenuItem = "xpath:=//*[@class='menu-wrap customer-menu']//span[text()='<ITEM>']";
	private String linkMaiMult = "xpath:=//div[contains(@class, 'link') and contains(text(), 'Mai mult')]";
	private String linkLivrare="xpath:=//*[@data-automation-id='edit-shipping-detail-link']";
	private String pageSim="xpath:=//*[contains(text(),'Expediere SIM')]";
	private String trimiteSim="xpath:=//*[contains(text(),'TRIMITE SIM')]";
	private String btnDaContinua="xpath:=//*[contains(text(),'DA, CONTINUĂ')]";
	private String btnNuAnuleaza="xpath:=//*[contains(text(),'NU, ANULEAZĂ')]";
	private String popupAtentie="xpath:=//*[text()='ATENȚIE!']";
	private String btnDa="xpath:=//*[text()='DA']";
	private String selectAccessType="xpath:=//*[@name='currentAccessTypePromoName']";
	private String selectRoamingSetting="xpath:=//*[@name='currentRoamingSetting']";
	private String btnContinue="xpath:=//*[text()='Continue']";
	private String btnÎnregistreazăComanda="xpath:=//*[@class='primary bold submit-button button-margin-left button-yesClick ']";
	private String lnkVizualizare="xpath:=//*[@data-automation-id='generate-contract']//*[contains(text(), 'VIZUALIZARE')]";
	private String btngenerateDoc="xpath:=//button[@class='generate-document generate-doc-position']";
	private String lblRegenerareDocumente = "xpath:=//*[contains(text(), 'Regenerare documente')]";	
	private String electronicSign="xpath:=//*[@data-automation-id='electronic-signing-toggle' and @value='true']/../span";
	
	//Methods
	public boolean isPageDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(page360, "Page 360", 25) == false) return false;
		CommonFunctions.fCommonSpinnerSync(300);
		return true;
	}
	public boolean isAtentieDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(popupAtentie, "ATENȚIE!", 25) == false) return false;
		return true;
	}
	public boolean clickDa(){
		if(CommonFunctions.fCommonClick(btnDa, "Click on Da button") == false) return false;
		CommonFunctions.fCommonSpinnerSync(500);
		return true;
	}
	public boolean clickContinua(){
		if(CommonFunctions.fCommonClick(btnContinue, "Click on continua button") == false) return false;
		CommonFunctions.fCommonSpinnerSync(500);
		return true;
	}
	public boolean clickElectronicSign(){
		if(CommonFunctions.fCommonClick(electronicSign, "Click on electronic sign") == false) return false;
		CommonFunctions.fCommonSpinnerSync(500);
		return true;
	}
	public boolean clickGenerateDocument(){
		if(CommonFunctions.fCommonClick(btngenerateDoc, "Click on generate document") == false) return false;
		CommonFunctions.fCommonSpinnerSync(500);
		return true;
	}
	public boolean isRegenerareDocumenteDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(	lblRegenerareDocumente, "Regenerare Documente", 2) == false) return false;
		return true;
	}

	public boolean clickÎnregistreazăComanda(){
		if(CommonFunctions.fCommonClick(btnÎnregistreazăComanda, "Click on ÎnregistreazăComanda") == false) return false;
		return true;
	}
	public int countVizualizareLink(){
		return CommonFunctions.fCommonCountObjects(lnkVizualizare, "Vizualizare Link");
	}
	public boolean selectRoamingSettings(String Setting){
		if (CommonFunctions.fCommonSelectionOptionFromList(selectRoamingSetting, "Roaming Setting", Setting, "ByValue") == false) return false; 
		return true;
	}
	public boolean selectAccessType(String AT){
		if (CommonFunctions.fCommonSelectionOptionFromList(selectAccessType, "Access Type", AT, "ByValue") == false) return false; 
		return true;
	}
	public boolean clickDaContinua(){
		if(CommonFunctions.fCommonClick(btnDaContinua, "Click on Da continua button") == false) return false;
		return true;
	}
	public boolean clickNuAnuleaza(){
		if(CommonFunctions.fCommonClick(btnNuAnuleaza, "Click on NuAnuleaza button") == false) return false;
		return true;
	}
	public boolean ispageSimDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(pageSim, "Expediere SIM", 25) == false) return false;
		return true;
	}
	public boolean clickTrimiteSim(){
		if(CommonFunctions.fCommonClick(trimiteSim, "Click on trimite Sim") == false) return false;
		return true;
	}
	public boolean clickLivrare(){
		if(CommonFunctions.fCommonClick(linkLivrare, "Menu") == false) return false;
		return true;
	}
	public boolean openMenu(){
		if(CommonFunctions.fCommonClick(btnOpenMenu, "Menu") == false) return false;
		return true;
	}

	public boolean hoverselectMenuItem(String item){
		//if(CommonFunctions.fCommonClick(txtMenuItem.replace("<ITEM>", item), "Select menu item: " + item) == false) return false;
		if (CommonFunctions.moveToWebElement(txtMenuItem.replace("<ITEM>", item), "Select menu item: " + item)== false) return false;
		//CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}
	public boolean selectMenuItem(String item){
		if(CommonFunctions.fCommonClick(txtMenuItem.replace("<ITEM>", item), "Select menu item: " + item) == false) return false;
		//if (CommonFunctions.moveToWebElement(txtMenuItem.replace("<ITEM>", item), "Select menu item: " + item)== false) return false;
		//CommonFunctions.fCommonSpinnerSync(120);
		
		return true;
	}

	public boolean clickMaiMult(){
		if(CommonFunctions.fCommonClick(linkMaiMult, "Mai mult") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}


	
	

	
	
}


