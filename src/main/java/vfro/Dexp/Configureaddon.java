package vfro.Dexp;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;
public class Configureaddon {
	//Instance Variables
			private Reporting Reporter;
			private WebDriver driver;
			private String driverType;
			private HashMap<String, String> Dictionary;
			private HashMap<String, String> Environment;
			private CommonFunctions CommonFunctions;

			//Constructor
			public Configureaddon(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
			{
				Reporter = GReporter;
				driver = GDriver;
				driverType = DT;
				Dictionary = GDictionary;
				Environment = GEnvironment;
				CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
			}
			//objects
			private String configureAddOn="xpath:=//*[text()='Configure Add-ons']";
			private String numarTelefon="xpath:=//*[@name='field_251519207']";
			private String txtEmail="xpath:=//*[@name='field_256275873']";
			private String btnselect="xpath:=//*[text()='SELECTEAZĂ']";
			
			
			public boolean isConfigureAddonDispalyed(){
				if(CommonFunctions.fCommonIsDisplayed(configureAddOn, "Configure Add-ons", 5) == false) return false;
				CommonFunctions.fCommonSpinnerSync(300);
				return true;
			}
			public boolean ClickSelecteaza(){
				if(CommonFunctions.fCommonClick(btnselect, "SELECTEAZĂ") == false) return false;
				return true;
			}
			public boolean setNumarTelefon(){
			
				if(CommonFunctions.fCommonSetValueEditBox(numarTelefon, "numarTelefon", "7757897544", "Y", "Y") == false) return false;
				return true;
			}
			public boolean setEmail(){
			
				if(CommonFunctions.fCommonSetValueEditBox(txtEmail, "ADRESĂ EMAIL", "sa@gmail.com", "Y", "Y") == false) return false;
				return true;
			}
}
