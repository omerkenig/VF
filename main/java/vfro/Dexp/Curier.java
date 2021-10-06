package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class Curier {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public Curier(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String tabCurier = "xpath:=//div[@class='equipment-product-title']";
	private String txtPhoneNumber = "xpath:=//div[@class='equipment-number border-bottom-line']/span[1]";
	private String txtSim = "xpath:=//input[@data-automation-id='new-sim-number-input']";
	private String btnContinuă = "xpath:=//button[@data-automation-id='summary-configuration-submit-btn']";
	private String linkEditare="xpath:=//a[text()='EDITARE']";
	private String comentarii="xpath:=//h1/span[text()='Editează detalii de livrare']";
	private String typeHere="xpath:=//*[@class='special-instruction-text-area' and @placeholder='Type Here']";
	private String btnSalveaza="xpath:=//button[@type='submit']//span[text()='SALVEAZĂ']";
	private String clickInStore="xpath:=//select[not(@disabled)]/option[@value='Shipping']/..";
	private String txtmethod="xpath:=//select[not(@disabled)]/option[@value='Shipping']";
	private String txtImei="xpath:=//*[@data-automation-id='imei-input']";
	private String existensimtoggle="xpath:=//*[@data-automation-id='use-existing-sim-card' and @value='true']/../span";
	//Methods
	public boolean isPageDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(tabCurier, "CURIER", 5) == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}
	public boolean isComentariiDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(comentarii, "Comentrii", 5) == false) return false;
		return true;
	}
	public boolean existientSimToggleoff(){
		if(CommonFunctions.fCommonClick(existensimtoggle, "making existent sim toggle off") == false) return false;
		return true;
	}
	public boolean clickEditare(){
		if(CommonFunctions.fCommonClick(linkEditare, "Editare") == false) return false;
		return true;
	}
	public boolean ClickInStore(){
		if (CommonFunctions.fCommonClick(clickInStore, "Clicking on In Store list") == false) return false;
		if (CommonFunctions.fCommonClick(txtmethod, "Shipping Method") == false) return false;
		return true;
	}
	public boolean addComentrii(){
		//CommonFunctions.sendKeys(typeHere, "Mobile Ship!!");
		WebElement textarea=driver.findElement(By.xpath("//*[@class='special-instruction-text-area' and @placeholder='Type Here']"));
		textarea.sendKeys("Mobile shipping!!!");
		return true;
	}
	public boolean clickSalveaza(){
		if(CommonFunctions.fCommonClick(btnSalveaza, "SALVEAZA") == false) return false;
//		
		return true;
	}
	public String getPhoneNumber(){
		return CommonFunctions.fCommonGetText(txtPhoneNumber, "Număr nou", 2).replace(" ", "").trim();
	}

	public boolean setSim(String sim){
		if (CommonFunctions.fCommonSetValueEditBox(txtSim, "SIM Card", sim, "Y", "Y") == false) return false;
		return true;
	}
	public boolean setImei(String Imei){
		if (CommonFunctions.fCommonSetValueEditBox(txtImei, "IMEI", Imei, "Y", "Y") == false) return false;
		return true;
	}
	public boolean clickContinuă(){
		CommonFunctions.fCommonSpinnerSync(10);
		if(CommonFunctions.fCommonClick(btnContinuă, "Continuă") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean clickPhoneNumber(){
		if(CommonFunctions.fCommonClick(txtPhoneNumber, "Phone Number") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	
	

	
	
}


