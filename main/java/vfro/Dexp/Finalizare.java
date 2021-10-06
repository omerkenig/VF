package vfro.Dexp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;

public class Finalizare {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;

	//Constructor
	public Finalizare(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	//Objects
	private String tabFinalizare = "xpath:=//div[@class='content-data upload-document']";
	private String radioClientulEsteAcordReținăCopie = "xpath:=//*[@name='idStoragePreferencesOption' and @value='Y']";
	private String btnContracteCaută = "xpath:=//*[@class='drop-zone-title' and contains(text(), 'CONTRACTE')]/..//*[@class='browse-link']";
	private String btnPaȘaportCaută = "xpath:=//*[@class='drop-zone-title' and contains(text(), 'PAȘAPORT')]/..//*[@class='browse-link']";
	private String textContractType = "xpath:=//div[text()='Contract Type']";
	private String chkCbu = "xpath:=//*[@value='CONT_CBU_MOB']/../span";
	private String btnÎncarcăDocumente = "xpath:=//button[@class='upload-document']";
	private String btnGenereazăDocumente = "xpath:=//button[contains(@class, 'generate-document')]";
	private String lblRegenerareDocumente = "xpath:=//*[@class='error-wrap-div']//*[contains(text(), 'Regenerare documente')]";
	private String rowContract = "xpath:=//div[@class='contract-row']";
	private String lnkVizualizare = "xpath:=//*[@data-automation-id='generate-contract']//*[contains(text(), 'VIZUALIZARE')]";
	private String btnÎnregistreazăComanda = "xpath:=//button[contains(@class, 'submit-order')]";
	private String btnFinalizare = "xpath:=//button[@data-automation-id='summary-configuration-submit-btn']";
	private String linkEsign = "xpath:=//a[@class='e-sign-documents-link']";
	private String linkCauta="xpath:=//*[text()='DOCUMENTE']/..//span[contains(text(),'CAUT')]";
	private String linkOrder="xpath:=//*[@class='order-success']";
	private String pdf="xpath:=//*[contains(text(),'.pdf')]";
	
	//Methods
	
	public boolean isPageDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(tabFinalizare, "FINALIZARE", 5) == false) return false;
		CommonFunctions.fCommonSpinnerSync(300);

		return true;
	}
	public boolean isPdfDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(pdf, "CBU_FISCAL_INV PDF displayed successfully", 15) == false) return false;

		return true;
	}
	public boolean isClientulEsteDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(radioClientulEsteAcordReținăCopie, "Clientul este de acord ca Vodafone să rețină o copie ...", 15) == false) return false;

		return true;
	}
	public boolean clickClientulEste(){
		if (CommonFunctions.fCommonClick(radioClientulEsteAcordReținăCopie, "Clientul este de acord ca Vodafone să rețină o copie ...") == false) return false;
		return true;
	}

	public boolean clickCautăForContracte(){
		if (CommonFunctions.fCommonClick(btnContracteCaută, "Caută For Contracte") == false) return false;
		return true;
	}
	public boolean attachDoc() throws IOException{
		CommonFunctions.fCommonSpinnerSync(120);
		Runtime.getRuntime().exec("C:\\VFRO\\AUTOIT\\FileUpload.exe");
		return true;
	}
	public String getOrder(){
		return CommonFunctions.fCommonGetText(linkOrder, "Order no", 5).replace("# ", "").trim();
	}
	public boolean clickOrder(){
		if (CommonFunctions.fCommonClick(linkOrder, "Click on Order Success") == false) return false;
		return true;
	}
	public boolean clickCauta(){
		if (CommonFunctions.fCommonClick(linkCauta, "Click on upload document") == false) return false;
		return true;
	}
	public boolean clickContractType(){
		if (CommonFunctions.fCommonClick(textContractType, "Contract Type") == false) return false;
		return true;
	}

	public boolean clickMobileContractCbu(){
		if (CommonFunctions.fCommonClick(chkCbu, "Mobile Contract CBU") == false) return false;
		return true;
	}

	public boolean clickCautăForPaȘaport(){
		if (CommonFunctions.fCommonClick(btnPaȘaportCaută, "Caută For PaȘaport") == false) return false;
		return true;
	}

	public boolean uploadDocument(){
		if(CommonFunctions.fCommonClick(btnFinalizare, "FINALIZARE") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}
	
	public boolean clickGenereazăDocumente(){
		if(CommonFunctions.fCommonClick(btnGenereazăDocumente, "Generează Documente") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public int countContractRows(){
		CommonFunctions.fCommonSpinnerSync(120);
		return CommonFunctions.fCommonCountObjects(rowContract, "Contract row");
	}
	
	public int countVizualizareLink(){
		return CommonFunctions.fCommonCountObjects(lnkVizualizare, "Vizualizare Link");
	}
	
	public boolean isRegenerareDocumenteDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(	lblRegenerareDocumente, "Regenerare Documente", 2) == false) return false;
		return true;
	}

	public boolean clickÎncarcăDocumente(){
		if(CommonFunctions.fCommonClick(btnÎncarcăDocumente, "Încarcă Documente") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean clickÎnregistreazăComanda(){
		if(CommonFunctions.fCommonClick(btnÎnregistreazăComanda, "Înregistrează Comanda") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean isEsignDisplay(){
		if(CommonFunctions.fCommonIsDisplayed(linkEsign, "Proces semnătură electronică inițiată - Pentru vizualizare in aplicație accesează acest link:E-SIGN", 30) == false) return false;
		return true;
	}
	
	public boolean clickFinalizare(){
		if(CommonFunctions.fCommonClick(btnFinalizare, "FINALIZARE") == false) return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}


	public String OpenUrl(String Url){
	    String originalHandle = driver.getWindowHandle();  // Save the original tab

		if(CommonFunctions.fCommonOpenLinkInNewtab(Url) == false) return null;
		if(isPdfDisplay()== false) return null;
		return null;
	}
	
}


