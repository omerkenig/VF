package vfro.Dexp;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;
public class Comenzi {
	//Instance Variables
		private Reporting Reporter;
		private WebDriver driver;
		private String driverType;
		private HashMap<String, String> Dictionary;
		private HashMap<String, String> Environment;
		private CommonFunctions CommonFunctions;

		//Constructor
		public Comenzi(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
		{
			Reporter = GReporter;
			driver = GDriver;
			driverType = DT;
			Dictionary = GDictionary;
			Environment = GEnvironment;
			CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
		}
		//objects
		private String cautadupa="xpath:=//*[@id='react-select-2--value']";
		private String searchval="xpath:=//*[@class='vdf-search-value-input ']";
		private String btnCautaComanda="xpath:=//*[contains(text(),'Caută comanda')]";
		private String linkAnuleaza="xpath:=//*[contains(text(),'Anulează comandă')]";
		private String popupRenunta="xpath:=//*[contains(text(),'RENUNȚĂ')]";
		private String txtreason="xpath:=//*[@data-automation-id='select-reason-code']";
		private String btnCancel="xpath:=//*[@data-automation-id='cancel-popup-submit-button']";
		private String removefilter2="xpath:=//*[text()='Error']/../img[@class='vdf-remove-filter-button']";
		private String removefilter1="xpath:=//*[text()='In Progress']/../img[@class='vdf-remove-filter-button']";
		private String txtCautaDupa="xpath:=//*[text()='<ITEM>']";
		private String btnPrimary="xpath:=//*[@class='tooltip-button']";
		private String orderdetails="xpath:=//*[contains(text(),'Comandă')]";
		private String viewdetails="xpath:=//*[contains(text(),'View Details')]";
		private String btnAnuelaza="xpath:=//*[contains(text(),'ANULEAZĂ COMANDĂ')]";
		private String btnGenerezaDocumente="xpath:=//*[contains(text(),'GENEREAZĂ DOCUMENTE')]";
		
		
		public boolean isPageDisplay(){
			if(CommonFunctions.fCommonIsDisplayed(popupRenunta, "Pop Up Renunta", 5) == false) return false;
			CommonFunctions.fCommonSpinnerSync(300);
			return true;
		}
		public boolean isPrimaryDisplay(){
			if(CommonFunctions.fCommonIsDisplayed(btnPrimary, "Checking Primary link is visible", 5) == false) return false;
			return true;
		}
		public boolean clickCnp(){
			if (CommonFunctions.fCommonClick(btnCautaComanda, " Clicking on Cauta Comanda") == false) return false; 
			CommonFunctions.fCommonSpinnerSync(400);
			return true;
		}
		public boolean clickGenerezaDocumente(){
			if (CommonFunctions.fCommonClick(btnGenerezaDocumente, " Clicking on Genereza Documente") == false) return false; 
			CommonFunctions.fCommonSpinnerSync(400);
			return true;
		}
		public boolean clickView(){
			if (CommonFunctions.fCommonClick(viewdetails, " Clicking on View details") == false) return false; 
			return true;
		}
		public boolean clicklinkANULEAZĂ(){
			if (CommonFunctions.fCommonClick(btnAnuelaza, " Clicking Anuelaza Commanda") == false) return false; 
			CommonFunctions.fCommonSpinnerSync(400);
			return true;
		}
		public String gettextorder(){
			return CommonFunctions.fCommonGetText(orderdetails, "getting offer value which is selected", 10);
		}
		public boolean clickPrimary(){
			if (CommonFunctions.fCommonClick(btnPrimary, "Clicking on Primary button") == false) return false; 
			return true;
		}
		public boolean removefilter1(){
			if (CommonFunctions.fCommonClick(removefilter1, "remove In Progress filter") == false) return false; 
			CommonFunctions.fCommonSpinnerSync(120);
			return true;
		}
		public boolean removefilter2(){
			if (CommonFunctions.fCommonJavascriptClick(removefilter2, "remove Error filter") == false) return false; 
			CommonFunctions.fCommonSpinnerSync(120);
			return true;
		}
		public boolean clickCancel(){
			if (CommonFunctions.fCommonClick(btnCancel, " Clicking on Cancel option") == false) return false; 
			CommonFunctions.fCommonSpinnerSync(400);
			return true;
		}
		public boolean clickAnuleaza(){
			if (CommonFunctions.fCommonJavascriptClick(linkAnuleaza, "link Anuleaza click successfully") == false) return false; 
			CommonFunctions.fCommonSpinnerSync(400);
			return true;
		}
		public boolean searchVal(String searchVal){
			if (CommonFunctions.fCommonSetValueEditBox(searchval, "search Order ID", searchVal, "Y", "Y") == false) return false;
			return true;
			
		}
		public boolean selectReasonCode(String rcode){
			if (CommonFunctions.fCommonSelectionOptionFromList(txtreason, "REASON CODE", rcode, "ByValue") == false) return false; 
			return true;
		}
		public boolean selectCautaDupa(String item){
			//if (CommonFunctions.fCommonSelectionOptionFromList(cautadupa, "CAUTA DUPA", cdupa, "ByValue") == false) return false; 
			if (CommonFunctions.fCommonClick(cautadupa, "Cauta dupa click successfully") == false) return false; 
			if (CommonFunctions.fCommonClick(txtCautaDupa.replace("<ITEM>", item), "Select menu item: " + item) == false) return false; 
			CommonFunctions.fCommonSpinnerSync(400);
			return true;
		}
}
