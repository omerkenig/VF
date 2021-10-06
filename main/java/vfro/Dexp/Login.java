package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class Login {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public Login(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String txtAtenție = "xpath:=//span[text()='Atenție!']";
	private String btnOk = "xpath:=//button/span[contains(text(), 'OK')]";
	private String txtWelcome = "xpath:=//span[contains(text(), 'Bine ai venit!')]";
	private String inputUserName = "xpath:=//input[@data-automation-id='input-login-user-name']";
	private String inputPassword = "xpath:=//input[@data-automation-id='input-login-user-password']";	
	private String btnLogin = "xpath:=//button[@data-automation-id='login-button']";
	private String listMagazin = "xpath:=//select[@data-automation-id='select-dealer']";
	private String btnDealerLogin = "xpath:=//button[@data-automation-id='dealer-login-button']";
	
	//Methods
	public boolean loginDexp(String userID, String password){
		if(CommonFunctions.fCommonLaunchEnvironemnt(Environment.get("DEX_URL")) == false) return false;
		if(isAtențieDisplay() == false) return false;
		if(clickOk() == false) return false;
		if(isBineAiVenitDisplay() == false) return false;
		if(enterLoginDetails(userID, password) == false) return false;
		if(clickLogin() == false) return false;
		//CommonFunctions.fCommonSpinnerSync(150);
		return true;
	}
	
	public boolean isAtențieDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(txtAtenție, "Atenție!", 25) == false) return false;
		//CommonFunctions.fCommonSpinnerSync(300);
		return true;
	}
	
	public boolean clickOk(){
		if (CommonFunctions.fCommonClick(btnOk, "Ok") == false) return false; 
		return true;
	}

	public boolean isBineAiVenitDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(txtWelcome, "Bine ai venit!", 5) == false) return false;
		return true;
	}
	

	public boolean enterLoginDetails(String userID, String password){
		if (CommonFunctions.fCommonSetValueEditBox(inputUserName, "User Name", userID, "Y", "Y") == false) return false;
		if (CommonFunctions.fCommonSetValueEditBox(inputPassword, "Password",password , "N", "Y") == false) return false;
		return true;
	}
	
	public boolean clickLogin(){
		if (CommonFunctions.fCommonClick(btnLogin, "Login") == false) return false;
		return true;
	}

	public boolean selectMagazin(String magazin){
		if (CommonFunctions.fCommonSelectionOptionFromList(listMagazin, "Magazine", magazin, "ByValue") == false) return false; 
		return true;
	}
	
	public boolean clickContinuă(){
		if (CommonFunctions.fCommonClick(btnDealerLogin, "CONTINUĂ") == false) return false;
		//CommonFunctions.fCommonSpinnerSync(10);
		return true;
	}


	
	
}


