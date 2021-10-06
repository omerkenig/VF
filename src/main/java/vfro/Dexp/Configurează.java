package vfro.Dexp;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import vfro.framework.CommonFunctions;
import vfro.framework.Reporting;
import vfro.regression.MainDriver;

public class Configurează extends MainDriver{

	// Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;
	//WebDriverWait wait = new WebDriverWait(driver, 10);



	// Constructor
	public Configurează(WebDriver GDriver, String DT, HashMap<String, String> GDictionary,
			HashMap<String, String> GEnvironment, Reporting GReporter) {
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		CommonFunctions = new CommonFunctions(driver, driverType, Environment, Reporter);
	}

	// Objects
	private String tabConfiguration = "xpath:=//div[@class='configuration-page']";
	private String linkConfigureazăOferte = "xpath:=//div[@data-automation-id='custom-configuration']";
	private String linkÎncepeCuServicii = "xpath:=//div[@data-automation-id='header-link']";
	private String btnSelectServiceByIndex = "xpath:=//div[@class='card mobile-card'][<INDEX>]//button/span[text()='Selecteaza']";
	private String btnSelectPackageByIndex = "xpath:=//div[starts-with(@class, 'card ')][<INDEX>]//button/span[text()='Selecteaza']";
	private String txtOpȚiuniPromoȚionale = "xpath:=//span[contains(text(), 'OPȚIUNI PROMOȚIONALE')]";
	private String addonMandatorySections = "xpath:=//*[starts-with(text(), 'Selectează min 1')]/../..//div[@class='addons-section']";
	private String addonMandatorySelectedIndex = "xpath:=(//*[starts-with(text(), 'Selectează min 1')]/../..//div[@class='addons-section'])[<INDEX>]//div[contains(@class, 'selected')]";
	private String addonMandatoryAvailableIndex = "xpath:=(//*[starts-with(text(), 'Selectează min 1')]/../..//div[@class='addons-section'])[<INDEX_1>]//div[starts-with(@class, 'addon')][<INDEX_2>]";
	private String btnContinuă = "xpath:=//button[@data-automation-id='summary-configuration-submit-btn']";
	private String btnDoarServiciiOff = "xpath:=//input[@data-automation-id='mobileDeviceOffers.mobileOfferLabel' and @value='false']/../span";
	// private String
	// selectHuwaeiDevice="xpath:=//*[contains(@title,'"+offerName+"')]/../../../..//*[text()='SELECTEAZĂ']";
	private String filter = "xpath:=//*[@data-automation-id='open-filter']";
	private String dealerLink = "xpath:=//*[@data-automation-id='dealers-portfolio-link']";
	private String selectTipArrow = "xpath:=//select[@type='select']";
	// private String
	// txtTipArrowByIndex="xpath:=//select[@type='select']/option[<INDEX>]";
	private String txtMARCĂ = "xpath:=//*[@data-automation-id='brand-name-input']";
	private String txtNUME = "xpath:=//input[@name='deviceName' and @data-automation-id='device-name-input']";
	private String txtPREȚÎNTREG = "xpath:=//input[@name='fullPrice' and @data-automation-id='full-price-input']";
	private String txtREDUCERE = "xpath:=//input[@name='discount' and @data-automation-id='discount-input']";
	private String btnGata = "xpath:=//button[@data-automation-id='device-view-done-button']";
	private String doarOneSelectedIndex = "xpath:=//*[starts-with(text(), 'Selectează doar 1')]/../..//*[@class='addon-div '][INDEX]//div[contains(@class, 'selected')]";
	private String doarOne = "xpath:=//*[starts-with(text(), 'Selectează doar 1')]/../..//*[@class='addon-div ']";
	private String doarOneAvailableIndex = "//*[starts-with(text(), 'Selectează doar 1')]/../..//*[@class='addon-div '][<INDEX_1>]//div[starts-with(@class, 'addon')][<INDEX_2>]";
	private String selectCBU = "xpath:=//*[text()='CBU_Vodafone PC Protection']/../../../div[@class='addon_item   ']";
	private String txtMenuItem = "xpath:=//*[@class='services-nav-menu']//span[text()='<ITEM>']";
	private String btnOpenMenu = "xpath:=//*[@class='services-nav-menu']";
	private String miniCart = "xpath:=//*[@data-automation-id='open-cart-menu-button']";
	private String cartDetails = "xpath:=//*[@class='cart-menu-wrap']";
	private String deviceVal = "xpath:=//*[@class='cart-item-title']";
	private String offerVal = "xpath:=//*[@class='cart-item-title-nonCapitalize']";
	private String getOrder = "xpath:=//*[contains(text(),'COMANDA')]";
	private String btnSelectaza = "xpath:=//*[text()='SELECTEAZĂ']";
	private String addonitem = "xpath:=//*[@class='addon_item   ']/../../..//*[contains(text(),'<ITEM>')]";
	private String txtaddon = "xpath:=//*[@class='selected-fixed-addons-name']";
	private String linkReturneaza = "xapth:=//*[text()='RETURNEAZĂ']";
	private String selectList = "xpath:=//*[text()='<ITEM>']/../select['data-automation-id']";
	private String togglebtn = "xpath:=//*[@type='checkbox' and @value='false']";
	private String btnStrge = "xpath:=//*[text()='ȘTERGE']";
	private String btnElimina = "xpath:=//*[text()='ELIMINĂ']";
	private String Internetfixspecial = "xpath:=//button[@data-automation-id='256165793']";
	private String TvAndNetBudle = "xpath:=//button[@data-automation-id='256414791']//span[1]";
	private String bundleName = "//span[text()='PACHETE SERVICII FIXE']/following-sibling::label";
	//private String cartName = "//div[contains(text(),'TV Start + Net 500 testFMCVOIP')]";
	private String addTVEquipment = "//button[text()='+'])[2]";
	private String subTVEquipment = "xpath:=//button[text()='-'])[2]";
	private String addInternetEquipment = "//button[text()='-']";
	private String subInternetEquipment = "xpath:=//button[text()='+']";

	

	// private String addOnNet="xpath:=(//div[@class='accordion_head
	// undefined'])[2]"));

	private String iNTERNETEX = "xpath:=//div[text()='Optiune HBOGO, Optiune IP FIX Promo, Optiune IP FIX']";
	private String buttonOptiuneHBOGO = "xpath:=//h3[text()='Optiune HBOGO']";
	private String buttonOptiuneIP = "xpath:=//h3[text()='Optiune IP FIX Promo']";
	private String buttonOptiuneIPFIX = "xpath:=//h3[text()='Optiune IP FIX']";

	private String TVEXTRAOPTIUNI = "xpath:=//div[text()='Optiune HBOGO, Optiune IP FIX Promo, Optiune IP FIX']";
	private String buttonOptiuneTarifVerde = "xpath:=//h3[text()='Optiune Tarif Verde']";
	private String buttonOptiuneTelecomanda = "xpath:=//h3[text()='Optiune Telecomanda copil']";
	private String buttonVoyoTV = "xpath:=//h3[text()='Optiune Voyo TV']";
	private String textAddOnNet = "xpath:=//div[text()='Optiune Tarif Verde, Optiune Telecomanda copil, Optiune Voyo TV']";
	private String plusTVAddon = "xpath:=//img[@src='resources/assets/images/section_expand.svg']";
	private String plusInternetAddon = "xpath:=//img[@alt='custom-img'])[3]";

	private String MinusTvAddon = "xpath:=//span[text()='Other Group']";
	private String MinusInternetAddon = "//span[text()='Other']";
	
	// Cart

	private String BENEFICII = "xpath:=//div[text()='Reducere Optiune IP Fix']";
	private String CTVEXTRAOPTIUNI = "xpath:=//div[text()='Optiune Voyo TV']";
	private String INTERNETEXTRAOPTIUNI = "xpath:=//div[text()='Optiune HBOGO']";

	private String shopCart = "xpath:=//img[@alt='shopping']";
	
	// 4 accordion
	private String internterAddon = "xpath:=//span[text()='INTERNET- EXTRAOPTIUNI']";
	private String tvAddon = "xpath:=//span[text()='TV- EXTRAOPTIUNI']";
	private String internterEquipment = "xpath:=//span[text()='Internet Fix Echipament']";
	private String tvEquipment = "xpath:=//span[text()='Internet Fix Echipament']";
	
	private String tvSum = "xpath:=(//input[@data-automation-id='input-number-increment'])[2]";
	private String internetSum = "xpath:=//input[@data-automation-id='input-number-increment']";

	
	int tvEquipmentsum = 1;
	
	
	// Methods
	public boolean isPageDisplay() {
		if (CommonFunctions.fCommonIsDisplayed(tabConfiguration, "CONFIGUREAZĂ", 5) == false)
			return false;
		return true;
	}

	public boolean openMenu() {
		if (CommonFunctions.fCommonClick(btnOpenMenu, "Menu") == false)
			return false;
		return true;
	}

	public boolean clickSelectaza() {
		if (CommonFunctions.fCommonClick(btnSelectaza, "btn Selectaza") == false)
			return false;
		return true;
	}

	public boolean selectMenuItem(String item) {
		if (CommonFunctions.fCommonClick(txtMenuItem.replace("<ITEM>", item), "Select menu item: " + item) == false)
			return false;
		return true;
	}

	public boolean selectAddonItem(String item) {
		if (CommonFunctions.fCommonClick(addonitem.replace("<ITEM>", item), "Select addon item: " + item) == false)
			return false;
		return true;
	}

	public boolean clickReturneaza() {
		if (CommonFunctions.fCommonClick(linkReturneaza, "Click on link Returneaza") == false)
			return false;
		return true;
	}

	public boolean selectList(String item, String item2) {
		if (CommonFunctions.fCommonClick(selectList.replace("<ITEM>", item), "Select menu item: " + item) == false)
			return false;
		if (CommonFunctions.fCommonSelectionOptionFromList(selectList, "Select options from list", item2,
				"ByValue") == false)
			return false;
		return true;
	}

	public boolean clicktogglebtn() {
		if (CommonFunctions.fCommonClick(togglebtn, "Click on link Returneaza") == false)
			return false;
		return true;
	}

	public boolean clickStrge() {
		if (CommonFunctions.fCommonClick(btnStrge, "Click on link Returneaza") == false)
			return false;
		return true;
	}

	public boolean clickElimina() {
		if (CommonFunctions.fCommonClick(btnElimina, "Click on link Returneaza") == false)
			return false;
		return true;
	}

	public String getOrderno() {
		return CommonFunctions.fCommonGetText(getOrder, "Fetching order no", 10).replace(" ", "").trim();

	}

	public String getPromotions() {
		return CommonFunctions.fCommonGetText(txtaddon, "Fetching Promotions", 10).replace(" ", "").trim();

	}

	public boolean clickMiniCart() {
		if (CommonFunctions.fCommonClick(miniCart, "Click on Mini Cart logo") == false)
			return false;
		return true;
	}

	public String getCartDetails_StartDevice() {

		String s1 = CommonFunctions.fCommonGetText(offerVal, "getting offer value which is selected", 10);
		String s2 = CommonFunctions.fCommonGetText(deviceVal, "getting device value which is selected", 10);
		if (s1.startsWith(Dictionary.get("Offer")) || s2.equalsIgnoreCase(Dictionary.get("Device"))) {
			System.out.println("OFFER and DEVICE Validated Successfully!!!!");
		} else {
			Assert.fail();
		}
		return CommonFunctions.fCommonGetText(cartDetails, "Fetching cart details", 10).replace(" ", "").trim();

	}

	public String getCartDetails_Service() {

		return CommonFunctions.fCommonGetText(cartDetails, "Fetching cart details", 10).replace(" ", "").trim();
	}

	public boolean clickHuwaeiOffer(String offerName) {
		String selectHuwaeiDevice1 = "xpath:=//*[contains(@title,'" + offerName
				+ "')]/../../..//*[text()='SELECTEAZĂ']";
		if (CommonFunctions.fCommonClick(selectHuwaeiDevice1, "Huwaei offer") == false)
			return false;
		// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean clickDiscountOffer(String servicename) {
		String discountoffer = "xpath:=//*[contains(@title,'" + servicename
				+ "')]/../../../../../.././/*[text()='Selecteaza']";
		if (CommonFunctions.fCommonClick(discountoffer, "Discount offer selection") == false)
			return false;
		// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean clickDealerLink() {
		if (CommonFunctions.fCommonClick(dealerLink, "Click on Dealer Link") == false)
			return false;
		return true;
	}

	public boolean clickFilter() {
		if (CommonFunctions.fCommonClick(filter, "Click on filter option") == false)
			return false;
		// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean setMarca() {
		String Marca = Dictionary.get("MARCA");
		if (CommonFunctions.fCommonSetValueEditBox(txtMARCĂ, "MARCĂ", Marca, "Y", "Y") == false)
			return false;
		return true;
	}

	public boolean setTip() {
		String Tip = Dictionary.get("TIP");
		if (CommonFunctions.fCommonSelectionOptionFromList(selectTipArrow, "TIP", Tip, "ByVisibleText") == false)
			return false;
		return true;
	}

	public boolean setNume() {
		String Nume = Dictionary.get("NUME");
		if (CommonFunctions.fCommonSetValueEditBox(txtNUME, "NUME", Nume, "Y", "Y") == false)
			return false;
		return true;
	}

	public boolean setPREȚÎNTREG() {
		String Pretintreg = Dictionary.get("PREȚÎNTREG");
		if (CommonFunctions.fCommonSetValueEditBox(txtPREȚÎNTREG, "PREȚÎNTREG", Pretintreg, "Y", "Y") == false)
			return false;
		return true;
	}

	public boolean setReducere() {
		String Reducere = Dictionary.get("REDUCERE");
		if (CommonFunctions.fCommonSetValueEditBox(txtREDUCERE, "REDUCERE", Reducere, "Y", "Y") == false)
			return false;
		return true;
	}

	public boolean clickGata() {
		if (CommonFunctions.fCommonClick(btnGata, "Click on GATA") == false)
			return false;
		// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean clickConfigureazăOferte() {
		if (CommonFunctions.fCommonClick(linkConfigureazăOferte, "Configurează Oferte") == false)
			return false;
		return true;
	}

	public boolean clickÎncepeCuServicii() {
		if (CommonFunctions.fCommonClick(linkÎncepeCuServicii, "ÎNCEPE CU SERVICII") == false)
			return false;
		// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean toggleDoarServiciiOn() {
		if (CommonFunctions.fCommonClick(btnDoarServiciiOff, "Toggle on DOAR SERVICII") == false)
			return false;
		return true;
	}

	public boolean selectServiciiMobileByIndex(String index) {
		if (CommonFunctions.fCommonClick(btnSelectServiceByIndex.replace("<INDEX>", index), "SERVICII MOBILE") == false)
			return false;
		// // CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean selectPackageByIndex(String index) {
		if (CommonFunctions.fCommonClick(btnSelectPackageByIndex.replace("<INDEX>", index), "SERVICII MOBILE") == false)
			return false;
		CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean isOpȚiuniPromoȚionaleDisplay() {
		if (CommonFunctions.fCommonIsDisplayed(txtOpȚiuniPromoȚionale, "OPȚIUNI PROMOȚIONALE", 5) == false)
			return false;
		return true;
	}

	public boolean selectMandatoriesAddon() {
		List<WebElement> arrMandatorySections = CommonFunctions.fCommonGetMultipleObjects(addonMandatorySections,
				"Mandatory Sections");
		if (arrMandatorySections != null) {
			for (int i = 0; i < arrMandatorySections.size(); i++) {
				if (!CommonFunctions.fCommonIsDisplayed(
						addonMandatorySelectedIndex.replace("<INDEX>", Integer.toString(i + 1)),
						"Selected mandatory Addon", 1)) {
					if (CommonFunctions.fCommonClick(addonMandatoryAvailableIndex
							.replace("<INDEX_1>", Integer.toString(i + 1)).replace("<INDEX_2>", "1"), "Addon") == false)
						return false;
				}
			}
		} else {
			return true;
		}

		Reporter.fnWriteToHtmlOutput("Select Mandatories Addon", "Madatories addon selected", "Success", "Pass");
		return true;
	}

	public boolean selectDoar1() {
		List<WebElement> arrMandatorySections = CommonFunctions.fCommonGetMultipleObjects(doarOne,
				"Mandatory Sections");
		if (arrMandatorySections != null) {
			for (int i = 0; i < arrMandatorySections.size(); i++) {
				if (!CommonFunctions.fCommonIsDisplayed(
						doarOneSelectedIndex.replace("<INDEX>", Integer.toString(i + 1)), "Selected mandatory Addon",
						1)) {
					if (CommonFunctions.fCommonClick(doarOneAvailableIndex.replace("<INDEX_1>", Integer.toString(i + 1))
							.replace("<INDEX_2>", "1"), "Addon") == false)
						return false;
				}
			}
		} else {
			return true;
		}

		Reporter.fnWriteToHtmlOutput("Select Mandatories Addon", "Madatories addon selected", "Success", "Pass");
		return true;
	}

	public boolean clickCBU() {
		if (CommonFunctions.fCommonClick(selectCBU, "CBU") == false)
			return false;
		// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean clickContinuă() {
		if (CommonFunctions.fCommonClick(btnContinuă, "Continuă") == false)
			return false;
		// // CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean Internetfixspecial() {
		if (CommonFunctions.fCommonClick(Internetfixspecial, "click on Internet Fix") == false)
			return false;
		return true;
	}

	public boolean selectTvAndNetBudle() {
		if (CommonFunctions.fCommonClick(TvAndNetBudle, "click on Home 1000 Plus") == false)
			return false;
		return true;
	}

	public boolean validateBunbleNameAndCartEqual() {

		String getTextBunble = driver
				.findElement(By.xpath("//span[text()='PACHETE SERVICII FIXE']/following-sibling::label")).getText();

	//	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='shopping']")));
		Assert.assertTrue(clickShopCart());
		
	    List<WebElement> getTextBundlecart = driver.findElements(By.xpath("//div[@class='cart-item-title']"));
	    
		String textCart = getTextBundlecart.get(0).getText();

	//	Assert.assertEquals(getTextBunble,textCart);

	    if(textCart.equalsIgnoreCase(getTextBunble)) 
			return true;
		else
			return false;
	}

	public boolean clickShopCart() {

		if (CommonFunctions.fCommonClick(shopCart, "click on Shopping Cart") == false)
			return false;
		return true;

	}

	// public int countVizualizareLink(){
	// return CommonFunctions.fCommonCountObjects(lnkVizualizare, "Vizualizare
	// Link");
	// }

	public boolean clickOptiuneHBOGO() {
		if (CommonFunctions.fCommonClick(buttonOptiuneHBOGO, "click on Optiune HBOGO") == false)
			return false;
		// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean clickOptiuneIP() {
		if (CommonFunctions.fCommonClick(buttonOptiuneIP, "click on Optiune IP") == false)
			return false;
		// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean clickOptiuneIPFIX() {
		if (CommonFunctions.fCommonClick(buttonOptiuneIPFIX, "click on Optiune IP FIX") == false)
			return false;
		// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean clickOptiuneTarifVerde() {
		if (CommonFunctions.fCommonClick(buttonOptiuneTarifVerde, "click on Optiune Tarif") == false)
			return false;
		// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean clickOptiuneTelecomanda() {
		if (CommonFunctions.fCommonClick(buttonOptiuneTelecomanda, "click on Optiune Tarif") == false)
			return false;
		// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}

	public boolean clickVoyoTV() {
		if (CommonFunctions.fCommonClick(buttonVoyoTV, "click on Optiune Tarif") == false)
			return false;
		// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}
	
	public boolean validateAddOnInPackage() {

		

		String textOptiuneHBOGO = driver.findElement(By.xpath("//h3[text()='Optiune HBOGO']")).getText();

		String textOptiuneIP = driver.findElement(By.xpath("//h3[text()='Optiune IP FIX']")).getText();

		String textaddOnTV = driver.findElement(By.xpath("(//div[@class='accordion_head undefined'])[2]")).getText();

		String textOptiuneIPFixPromo = driver.findElement(By.xpath("//h3[text()='Optiune IP FIX Promo']")).getText();

		

	//	String textTarifVerde = driver.findElement(By.xpath("//h3[text()='Optiune IP FIX']")).getText();
	//	String textOptiuneTelecomanda = driver.findElement(By.xpath("xpath:=//h3[text()='Optiune IP FIX']")).getText();
	//  String textVoyoTV = driver.findElement(By.xpath("//h3[text()='Optiune IP FIX']")).getText();

		if ((textaddOnTV.contains(textOptiuneHBOGO)) || (textaddOnTV.contains(textOptiuneIP))
				|| (textaddOnTV.contains(textOptiuneIPFixPromo))
				/*|| (TVEXTRAOPTIUNI.contains(textTarifVerde)) && (TVEXTRAOPTIUNI.contains(textOptiuneTelecomanda))
				&& (TVEXTRAOPTIUNI.contains(textVoyoTV))*/

		)

			return true;
		else
			return false;
	}
	
	public boolean validateTvAddonCollapses() {

		
		   List<WebElement> getTextBundlecart = driver.findElements(By.xpath("//span[contains(text(),'Selectează min 1, maxim 3 opțiuni')]"));
		    
			String textCart = getTextBundlecart.get(1).getText();
			
		if (CommonFunctions.fCommonVerifyObjectNotExist(textCart, "Tv Addon are Collapses") == true)
			return true;
	// CommonFunctions.fCommonSpinnerSync(120);
		return false;
		
	}
	
	public boolean validateInternetAddonCollapses() {

		if (CommonFunctions.fCommonCheckObjectExistance(buttonOptiuneHBOGO) == true)
			return true;
	// CommonFunctions.fCommonSpinnerSync(120);
		return false;
		
	}

	public boolean validateAddOnCart() {
		
		Assert.assertTrue(clickShopCart());
		CommonFunctions.fCommonSpinnerSync(120);

	    List<WebElement> getTextBundlecart = driver.findElements(By.xpath("//div[@class='cart-item-title']"));
	    
		String textCart = getTextBundlecart.get(3).getText();

		if (textCart.contains("Optiune"))

			return true;
		else
			return false;
	}

	public boolean validateAccordionAddon() {
		if ( (CommonFunctions.fCommonCheckObjectExistance(internterAddon) ) && 
				(CommonFunctions.fCommonCheckObjectExistance(tvAddon)) == true)
			return true;
		return false;
	}
	
	public boolean validateAccordionEquipment() {
		if ( (CommonFunctions.fCommonCheckObjectExistance(internterEquipment) ) && 
				(CommonFunctions.fCommonCheckObjectExistance(tvEquipment)) == true)
			return true;
		return false;
	}
	
	
	
	public boolean clickCollapseTvAddon() {
		if (CommonFunctions.fCommonClick(MinusTvAddon, "sign to collapse the TV Addon Section ") == false)
			return false;
		//// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}
	
	public boolean clickCollapseInternetAddon() {
		
		List<WebElement> plusTotal = driver.findElements(By.xpath("//img[@alt='custom-img']"));
		
		WebElement plusInternet = plusTotal.get(0);		
		if (CommonFunctions.fCommonClick(plusInternet, "sign to collapse the TV Addon Section ") == false)
			return false;
		//// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}
	
	public boolean validatePlusTV() {
		if ( (CommonFunctions.fCommonCheckObjectExistance(plusTVAddon)) 	 == true)
			return true;
		return false;
	}
	
	public boolean validatePlusInternet() {
		
		List<WebElement> plusTotal = driver.findElements(By.xpath("//img[@alt='custom-img']"));
		
		WebElement plusInternet = plusTotal.get(0);	
		
		if ( (CommonFunctions.fCommonCheckObjectExistance(plusInternet)) 	 == true)
			return true;
		return false;
	}
	
	public boolean clickExpandsTvAddon() {
		if (CommonFunctions.fCommonClick(plusTVAddon, "sign to collapse the TV Addon Section ") == false)
			return false;
	// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}
	
	public boolean validateTVAddonShow() {
		if (CommonFunctions.fCommonCheckObjectExistance(buttonOptiuneTarifVerde) == true)
			return true;
	// CommonFunctions.fCommonSpinnerSync(120);
		return false;
	}
	
	public boolean clickExpandsInternetAddon() {
		
		List<WebElement> plusTotal = driver.findElements(By.xpath("//img[@alt='custom-img']"));
		
		WebElement plusInternet = plusTotal.get(0);	
		
		if (CommonFunctions.fCommonClick(plusInternet, "sign to collapse the TV Addon Section ") == false)
			return false;
	// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}
	
	public boolean validateInternetAddonShow() {
		if (CommonFunctions.fCommonCheckObjectExistance(buttonOptiuneHBOGO) == false)
			return false;
	// CommonFunctions.fCommonSpinnerSync(120);
		return true;
	}
	
	
	
	
	public boolean clickToAddTVEquipment() {
		
		List<WebElement> numberOfPlus = driver.findElements(By.xpath("//button[@data-automation-id='increment-button']"));
		
		WebElement TvPlus = numberOfPlus.get(1);		
					
		if ( ( CommonFunctions.fCommonClick(TvPlus, "Add item in TV Equipment ") )	 == true)
			return true;
	// CommonFunctions.fCommonSpinnerSync(120);
		return false;
	}
	
	public boolean clickToSubTVEquipment() {
		
List<WebElement> numberOfMinus = driver.findElements(By.xpath("(//button[@data-automation-id='decrement-button'])"));
	    
		WebElement TvPlus = numberOfMinus.get(1);
				
		if ( ( CommonFunctions.fCommonClick(TvPlus, "Add item in TV Equipment ") ) == true)
			return true;
	// CommonFunctions.fCommonSpinnerSync(120);
		return false;
	}
	
	public boolean clickToAddInternetEquipment() {
		
		List<WebElement> numberOfPluse = driver.findElements(By.xpath("//button[@data-automation-id='increment-button']"));
	    
		WebElement Internet = numberOfPluse.get(0);
			
		if ( ( CommonFunctions.fCommonClick(Internet, "Add item in TV Equipment ") ) == true)
			return true;
	// CommonFunctions.fCommonSpinnerSync(120);
		return false;
	}
	
	public boolean clickToSubInternetEquipment() {
		
		List<WebElement> numberOfMinus = driver.findElements(By.xpath("//button[@data-automation-id='decrement-button']"));
	    
		WebElement internetMinus = numberOfMinus.get(0);
		
		if ( ( CommonFunctions.fCommonClick(internetMinus, "Add item in TV Equipment ") )== true)
			return true;
	// CommonFunctions.fCommonSpinnerSync(120);
		return false;
	}
	
	
	
	
	

}
