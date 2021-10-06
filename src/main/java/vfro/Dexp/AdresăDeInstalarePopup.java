package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class AdresăDeInstalarePopup {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public AdresăDeInstalarePopup(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String popupAdresăDeInstalarePopup = "xpath:=//*[contains(@class, 'title-popup undefined title-height')]//*[contains(text(), 'Adresă instalare')]";
	private String selectJudeȚArrow = "xpath:=//*[text()='JUDEȚ']/..//*[@class='Select-arrow']";
	private String txtJudeȚByIndex = "xpath:=//*[text()='JUDEȚ']/..//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String inputLocalitate = "xpath:=//*[text()='LOCALITATE']/..//input";
	private String txtLocalitateByIndex = "xpath:=//*[text()='LOCALITATE']/..//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String selectTipStradăArrow = "xpath:=//*[text()='TIP STRADĂ']/..//*[@class='Select-arrow']";
	private String txtTipStradăByIndex = "xpath:=//*[text()='TIP STRADĂ']/..//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String inputNumeStradă = "xpath:=//*[text()='NUME STRADĂ']/..//*[@class='Select-input']/input";
	private String txtNumeStradăByIndex = "xpath:=//*[text()='NUME STRADĂ']/..//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String selectNumărStradăArrow = "xpath:=//*[text()='NUMĂR STRADĂ']/..//*[@class='Select-arrow']";
	private String txtNumărStradăByIndex = "xpath:=//*[text()='NUMĂR STRADĂ']/..//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String selectBlocArrow = "xpath:=//*[text()='BLOC']/..//*[@class='Select-arrow']";
	private String txtBlocByIndex = "xpath:=//*[text()='BLOC']/..//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String selectScarăArrow = "xpath:=//*[text()='SCARĂ']/..//*[@class='Select-arrow']";
	private String txtScarăByIndex = "xpath:=//*[text()='SCARĂ']/..//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String selectEtajArrow = "xpath:=//*[text()='ETAJ']/..//*[@class='Select-arrow']";
	private String txtEtajByIndex = "xpath:=//*[text()='ETAJ']/..//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String selectApartamentArrow = "xpath:=//*[text()='APARTAMENT']/..//*[@class='Select-arrow']";
	private String txtApartamentByIndex = "xpath:=//*[text()='APARTAMENT']/..//*[@class='Select-menu-outer']//*[@class='Select-option'][<INDEX>]";
	private String btnContinuă = "xpath:=//button[@data-automation-id='address-popup-continue-button']";
	
	
	//Methods
	public boolean isPageDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(popupAdresăDeInstalarePopup, "Adresă de instalare", 15) == false) return false;
		// CommonFunctions.fCommonSpinnerSync(300);
		return true;
	}
	
	public boolean openJudetList(){
		if (CommonFunctions.fCommonClick(selectJudeȚArrow, "JUDEȚ list") == false) return false;
		
		return true;
	}

	public boolean selectJudetByIndex(String index){
		if (openJudetList() == false) return false;
		if (CommonFunctions.fCommonClick(txtJudeȚByIndex.replace("<INDEX>", index), "JUDEȚ item") == false) return false;
		return true;
	}

	public boolean setLocalitate(String name){
		if(CommonFunctions.fCommonSetValueEditBox(inputLocalitate, "LOCALITATE", name, "Y", "Y") == false) return false;
		return true;
	}
	
	public boolean selectLocalitateByIndex(String index){
		if (CommonFunctions.fCommonClick(txtLocalitateByIndex.replace("<INDEX>", index), "LOCALITATE item") == false) return false;
		return true;
	}


	public boolean openTipStradăList(){
		if (CommonFunctions.fCommonClick(selectTipStradăArrow, "TIP STRADĂ list") == false) return false;
		return true;
	}

	public boolean selectTipStradăByIndex(String index){
		if (openTipStradăList() == false) return false;
		if (CommonFunctions.fCommonClick(txtTipStradăByIndex.replace("<INDEX>", index), "TIP STRADĂ item") == false) return false;
		return true;
	}

	public boolean setNumeStradă(String name){
		if(CommonFunctions.fCommonSetValueEditBox(inputNumeStradă, "NUME STRADĂ", name, "Y", "Y") == false) return false;
		return true;
	}
	
	public boolean selectNumeStradăByIndex(String index){
		if (CommonFunctions.fCommonClick(txtNumeStradăByIndex.replace("<INDEX>", index), "NUME STRADĂ item") == false) return false;
		return true;
	}

	public boolean openNumărStradăList(){
		if (CommonFunctions.fCommonClick(selectNumărStradăArrow, "NUMĂR STRADĂ list") == false) return false;
		return true;
	}

	public boolean selectNumărStradăByIndex(String index){
		if (openNumărStradăList() == false) return false;
		if (CommonFunctions.fCommonClick(txtNumărStradăByIndex.replace("<INDEX>", index), "NUMĂR STRADĂ item") == false) return false;
		return true;
	}

	public boolean openBlocList(){
		if (CommonFunctions.fCommonClick(selectBlocArrow, "BLOC list") == false) return false;
		return true;
	}

	public boolean selectBlocByIndex(String index){
		if (openBlocList() == false) return false;
		if (CommonFunctions.fCommonClick(txtBlocByIndex.replace("<INDEX>", index), "BLOC item") == false) return false;
		return true;
	}

	public boolean openScarăList(){
		if (CommonFunctions.fCommonClick(selectScarăArrow, "SCARĂ list") == false) return false;
		return true;
	}

	public boolean selectScarăByIndex(String index){
		if (openScarăList() == false) return false;
		if (CommonFunctions.fCommonClick(txtScarăByIndex.replace("<INDEX>", index), "SCARĂ item") == false) return false;
		return true;
	}

	public boolean openEtajList(){
		if (CommonFunctions.fCommonClick(selectEtajArrow, "ETAJ list") == false) return false;
		return true;
	}

	public boolean selectEtajByIndex(String index){
		if (openEtajList() == false) return false;
		if (CommonFunctions.fCommonClick(txtEtajByIndex.replace("<INDEX>", index), "ETAJ item") == false) return false;
		return true;
	}

	public boolean openApartamentList(){
		if (CommonFunctions.fCommonClick(selectApartamentArrow, "APARTAMENT list") == false) return false;
		return true;
	}

	public boolean selectApartamentByIndex(String index){
		if (openApartamentList() == false) return false;
		if (CommonFunctions.fCommonClick(txtApartamentByIndex.replace("<INDEX>", index), "APARTAMENT item") == false) return false;
		return true;
	}

	public boolean clickContinuă(){
		if (CommonFunctions.fCommonClick(btnContinuă, "Continuă") == false) return false;
		// CommonFunctions.fCommonSpinnerSync(10);
		return true;
	}

	
}


