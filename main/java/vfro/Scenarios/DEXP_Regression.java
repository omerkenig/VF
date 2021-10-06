package vfro.Scenarios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;
import com.mysql.jdbc.ResultSetMetaData;

import vfro.Dexp.*;
import vfro.framework.*;


public class DEXP_Regression {
	
	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon;
	
	BufferedReader BufferedReader = null;
	OutputStream OutputStream = null;
	InputStream InputStream = null;
	Channel Channel = null;
	Session Session = null;
	BufferedReader fromServer;
	OutputStream toServer;
	Session session;
	Channel channel;
	HashMap<String, String> params = new HashMap<>();
	public String ORDER_ID="",CUSTID="",msisdn="",newMsisdn="";
	UnixCommonFunction cm = new UnixCommonFunction(Session, Channel, BufferedReader, OutputStream);
	

	
	
	public DEXP_Regression(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)//, Eyes GEyes)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		objCommon = new CommonFunctions(driver,driverType,Environment,Reporter);
		
		
	}
	
	// Created by Zachi
	public boolean MobileProvide(String browser) throws IOException{
		
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		Login login = new Login(driver, driverType, Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		cnpGenerator cnpGenerator = new cnpGenerator(driver, driverType, Dictionary, Environment, Reporter);
		AbonamentNou newSubscriber = new AbonamentNou(driver, driverType, Dictionary, Environment, Reporter);
		Configurează configuration = new Configurează(driver, driverType, Dictionary, Environment, Reporter);
		Configureaddon config = new Configureaddon(driver, driverType, Dictionary, Environment, Reporter);
		Detalii details = new Detalii(driver, driverType, Dictionary, Environment, Reporter);
		Curier equipment = new Curier(driver, driverType, Dictionary, Environment, Reporter);
		Sumar summary = new Sumar(driver, driverType, Dictionary, Environment, Reporter);
		Finalizare completion = new Finalizare(driver, driverType, Dictionary, Environment, Reporter);
		Felicitari congrats = new Felicitari(driver, driverType, Dictionary, Environment, Reporter);
		DocumenteÎncărcatePopup uploadedDoc = new DocumenteÎncărcatePopup(driver, driverType, Dictionary, Environment, Reporter);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		String cnp = "";
		String prenume = "Auto";
		String nume = RandomStringUtils.randomAlphabetic(5);
		String streetPrefix = "str";
		String mail = RandomStringUtils.randomAlphabetic(5) + "@google.com";
		String sim = "";
		String contact="";
		String validatepopup="";
		int i=1;
		
		//Login DEXP
		if(Dictionary.get("NPREV").isEmpty()){
			Assert.assertTrue(login.loginDexp(Environment.get("DEX_USER_ID"), Environment.get("DEX_PASSWORD")));
		}
		else{
			Assert.assertTrue(login.loginDexp(Environment.get("DEX_NPRV_USER"), Environment.get("DEX_NPREV_PASSWORD")));
		}
		Assert.assertTrue(login.selectMagazin(Dictionary.get("MAGAZIN")));
		Assert.assertTrue(login.clickContinuă());

		cnp = cnpGenerator.generateCnp();
		
		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickCnp());
		Assert.assertTrue(agentDashboard.setSearchValue(cnp));
		Assert.assertTrue(agentDashboard.clickSearch());
	
		if(agentDashboard.isInvalidCNPDisplay()==false)
		{
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouMobil());
		}
		else{
			Assert.assertTrue(agentDashboard.handleInvalidCNP());
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouMobil());	
		}
		
		
		if (Dictionary.get("FLAG").equals("Y") || Dictionary.get("PretoPost").equals("Y") ){
			Assert.assertTrue(newSubscriber.isPageDisplay());
			Assert.assertTrue(newSubscriber.clickMigrareDeLaPrepaidtab());
			Assert.assertTrue(newSubscriber.clickDa());
			try{
				ResultSet rsData = dbAction.getDataFromDB(Dictionary.get("PK4"), "ABP");
				contact = rsData.getString("RESOURCE_VALUE");
			}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get sim from DB");
			}
			Assert.assertTrue(newSubscriber.setNumărContact(contact));
			}
		else{
			Assert.assertTrue(newSubscriber.isPageDisplay());
			Assert.assertTrue(newSubscriber.clickDa());
			Assert.assertTrue(newSubscriber.setNumărContact());
			}
		Assert.assertTrue(newSubscriber.clickUrmătoarele());
		Assert.assertTrue(newSubscriber.setPrenume(prenume));
		Assert.assertTrue(newSubscriber.setNume(nume));
		Assert.assertTrue(newSubscriber.selectJudetByIndex("1"));
		Assert.assertTrue(newSubscriber.selectLocalitateByIndex("1"));
		Assert.assertTrue(newSubscriber.selectTipStradăByIndex("1"));
		Assert.assertTrue(newSubscriber.setNumeStradă(streetPrefix));
		Assert.assertTrue(newSubscriber.selectNumeStradăByIndex("2"));
		Assert.assertTrue(newSubscriber.clickContinuă());
		
		Assert.assertTrue(configuration.isPageDisplay());
		if(Dictionary.get("NVFD").equals("Y")){
			Assert.assertTrue(configuration.clickConfigureazăOferte());
			Assert.assertTrue(configuration.clickFilter());
			Assert.assertTrue(configuration.clickDealerLink());
			Assert.assertTrue(configuration.setTip());
			Assert.assertTrue(configuration.setMarca());
			Assert.assertTrue(configuration.setNume());
			Assert.assertTrue(configuration.setPREȚÎNTREG());
			Assert.assertTrue(configuration.setReducere());
			Assert.assertTrue(configuration.clickGata());
			Assert.assertTrue(configuration.selectServiciiMobileByIndex("1"));
			//Assert.assertTrue(configuration.clickContinuă());
		}
		else if(Dictionary.get("BUNDLE").equals("Y"))
		{
			Assert.assertTrue(configuration.isPageDisplay());
			Assert.assertTrue(configuration.selectPackageByIndex("2"));
			Assert.assertTrue(configuration.clickMiniCart());
			configuration.getCartDetails_Service();
			String str=configuration.getOrderno();
			String[] arr = str.split("#", 0);
			System.out.println(arr[1]);
			ORDER_ID=arr[1];
		}
		else if(Dictionary.get("CombineOrder").isEmpty()){
		Assert.assertTrue(configuration.clickConfigureazăOferte());
		Assert.assertTrue(configuration.openMenu());
		Assert.assertTrue(configuration.selectMenuItem("Doar servicii"));
		Assert.assertTrue(configuration.selectServiciiMobileByIndex("1"));
		Assert.assertTrue(configuration.clickMiniCart());
		configuration.getCartDetails_Service();
		String str=configuration.getOrderno();
		String[] arr = str.split("#", 0);
		System.out.println(arr[1]);
		ORDER_ID=arr[1];
		}
		
		else{
			Assert.assertTrue(configuration.clickConfigureazăOferte());
			Assert.assertTrue(configuration.clickHuwaeiOffer(Dictionary.get("Device")));
			Assert.assertTrue(configuration.clickDiscountOffer(Dictionary.get("Offer")));
			Assert.assertTrue(configuration.clickMiniCart());
			configuration.getCartDetails_StartDevice();
			String str=configuration.getOrderno();
			String[] arr = str.split("#", 0);
			System.out.println(arr[1]);
			ORDER_ID=arr[1];
			//Assert.assertTrue(configuration.selectPackageByIndex("2"));
		}
		
		Assert.assertTrue(configuration.isOpȚiuniPromoȚionaleDisplay());
		Assert.assertTrue(configuration.selectMandatoriesAddon());
		Assert.assertTrue(configuration.clickContinuă());

		Assert.assertTrue(details.isPageDisplay());
		Assert.assertTrue(details.setEmail(mail));
		Assert.assertTrue(details.clickNuTelefonAlternativ());
		Assert.assertTrue(details.selectCodPoȘtalByIndex("1"));
		msisdn=details.getmsisdn();
		if(msisdn.endsWith("#1)")){
			msisdn=msisdn.replace("(NUMĂRNOU#1)","");
			//System.out.println("getting msisdn " +msisdn);
		}
		System.out.println("getting msisdn " +msisdn);
		if(Dictionary.get("CombineOrder").equals("Y")){
			Assert.assertTrue(details.clickLivrare());
		}
		Assert.assertTrue(details.clickContinuă());
		if( Dictionary.get("MAGAZIN").startsWith("TC")){
			Assert.assertTrue(equipment.clickPhoneNumber()); // needed to enable the 'continue' button
			Assert.assertTrue(equipment.clickContinuă());
		}
		
		else if (Dictionary.get("FLAG").isEmpty()||Dictionary.get("EXISTANTSIM").equals("Y")) {
			try{
				ResultSet rsData = dbAction.getDataFromDB(Dictionary.get("PK"), "ABP");
				sim = rsData.getString("PACKAGE_VALUE");
			}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get sim from DB");
			}
			
			Assert.assertTrue(equipment.isPageDisplay());
			//msisdn = equipment.getPhoneNumber();
			if(Dictionary.get("EXISTANTSIM").equals("Y")){
			Assert.assertTrue(equipment.existientSimToggleoff());}
			Assert.assertTrue(equipment.setSim(sim));
			Assert.assertTrue(equipment.clickPhoneNumber());
			if(Dictionary.get("NVFD").equals("Y")|| Dictionary.get("Device").startsWith("Huawei")){
				Assert.assertTrue(equipment.setImei(Dictionary.get("IMEI")));
				Assert.assertTrue(equipment.clickPhoneNumber());
			}
			else if(Dictionary.get("CombineOrder").equals("N")){
			Assert.assertTrue(equipment.ClickInStore());
			}
			Assert.assertTrue(equipment.clickContinuă());
		 }
		if(Dictionary.get("FLAG").equals("Y")){
			Assert.assertTrue(summary.vodafoneLogo());
			Assert.assertTrue(summary.isIntrerupereComandaDisplay());
			Assert.assertTrue(summary.selectReasonCode("FCATEX"));
			Assert.assertTrue(summary.handlePopUp());
			Assert.assertTrue(agentDashboard.isPageDisplay());
			objCommon.fCommonSync(29000);
			
			params.put("<ORDER_ID>", ORDER_ID);
			
			for(int counter=0 ; counter<60 ; counter++)
			{
					try{
				ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ORDER_STATUS", "OMS",params);
				System.out.println("updated status of order " +rsData.getString("STATUS"));
				if(!rsData.getString("STATUS").equals("CA"))
					objCommon.fCommonSync(3000);
				else
					break;
				
						}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get Order status from DB");
						}
					
			}
			
			objCommon.fCommonSync(18000);
			params.put("<MSISDN>",msisdn);
			params.put("<SIM>",sim);
			for(int counter=0 ; counter<60 ; counter++)
			{
					try{
						ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_RESOURCE_VALUE", "ABP",params);
						System.out.println("RESOURCE STATUS: " +rsData.getString("RESOURCE_STATUS"));
				if(!rsData.getString("RESOURCE_STATUS").equals("ASSIGNED"))
					objCommon.fCommonSync(3000);
				else
					break;
				
						}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get RESOURCE_STATUS from DB");
						}
					
			}
			
			Reporter.fnWriteToHtmlOutput("Pre to post migration with service cancel with dex", "Successful", "Successful", "Pass");
		}
		else{
			//condition to send email
			if (Dictionary.get("Email").equals("Y")){
				Assert.assertTrue(summary.isPageDisplay());
				Assert.assertTrue(summary.clickPersonalDetails());
				Assert.assertTrue(summary.clickTrimitePeEmail());
				//Assert.assertTrue(summary.clickTrimite());
				Assert.assertTrue(summary.isTrimitePopUpDispaly());
				Assert.assertTrue(summary.clickTrimite());
			}
			Assert.assertTrue(summary.isPageDisplay());
			Assert.assertTrue(summary.toggleProfilareVodafoneDaToateOn());
			Assert.assertTrue(summary.toggleProfilarePartnerDaToateOn());
			Assert.assertTrue(summary.clickContinuă());
			
			Assert.assertTrue(completion.isPageDisplay());
			Assert.assertTrue(completion.clickClientulEste());
			Assert.assertTrue(completion.clickGenereazăDocumente());
			for(i=1 ; i<8 ; i++){
				if(completion.countVizualizareLink()==2)
					break;
				else if (completion.isRegenerareDocumenteDisplay())
					Assert.assertTrue(completion.clickGenereazăDocumente());
			}
			if(Dictionary.get("CombineOrder").equals("Y") || Dictionary.get("NVFD").equals("Y")||Dictionary.get("PretoPost").equals("Y")||Dictionary.get("FLAG1").equals("V")||Dictionary.get("MAGAZIN").equalsIgnoreCase("PO1BB100"))
			{
				Assert.assertTrue(completion.clickCauta());
				Assert.assertTrue(completion.attachDoc());
				Assert.assertTrue(completion.clickÎncarcăDocumente());
				Assert.assertTrue(completion.clickÎnregistreazăComanda());
				Assert.assertTrue(completion.isEsignDisplay());
				Assert.assertTrue(completion.clickFinalizare());
			}
			else{
				Assert.assertTrue(completion.clickÎnregistreazăComanda());
				Assert.assertTrue(completion.isEsignDisplay());
				Assert.assertTrue(completion.clickFinalizare());
			}
			if(uploadedDoc.isPageDisplay())
				Assert.assertTrue(uploadedDoc.clickContinuă());
			
			Assert.assertTrue(congrats.isPageDisplay());
			String str=completion.getOrder();
			String[] arr = str.split("#", 0);
			System.out.println(arr[1]);
			ORDER_ID=arr[1];
			Assert.assertTrue(congrats.clickAgentDashboard());
			
			Assert.assertTrue(agentDashboard.isPageDisplay());
			
			objCommon.fCommonSync(29000);
			
			params.put("<ORDER_ID>", ORDER_ID);
			for(int counter=0 ; counter<60 ; counter++)
			{
					try{
				ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ORDER_STATUS", "OMS",params);
				System.out.println("updated status of order " +rsData.getString("STATUS"));
				if(rsData.getString("STATUS").equalsIgnoreCase("SB"))
					objCommon.fCommonSync(3000);
				else
					break;
				
						}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get Order status from DB");
						}
					
			}
			
			params.put("<MSISDN>", msisdn);
			objCommon.fCommonSync(18000);
			for(int counter=0 ; counter<60 ; counter++)
			{
					try{
						ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_SUBSTATUS", "ABP",params);
						System.out.println("Sub Status of order:" +rsData.getString("SUB_STATUS"));
				if(!rsData.getString("SUB_STATUS").equals("A"))
					objCommon.fCommonSync(3000);
				else
					break;
				
						}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get SUB_STATUS from DB");
						}
					
			}	
			Environment.put("<MSISDN>", msisdn);
			Environment.put("<ORDER_ID>", ORDER_ID);
			Reporter.fnWriteToHtmlOutput("Create Mobile provide", "Successful", "Successful", "Pass");
		}
		return true;
	}

	// Created by Zachi
	public boolean FixedProvide(String browser){
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		Login login = new Login(driver, driverType, Dictionary, Environment, Reporter);
		AdresăDeInstalarePopup address = new AdresăDeInstalarePopup(driver, driverType, Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		cnpGenerator cnpGenerator = new cnpGenerator(driver, driverType, Dictionary, Environment, Reporter);
		Configurează configuration = new Configurează(driver, driverType, Dictionary, Environment, Reporter);
		Detalii details = new Detalii(driver, driverType, Dictionary, Environment, Reporter);
		Curier equipment = new Curier(driver, driverType, Dictionary, Environment, Reporter);
		Sumar summary = new Sumar(driver, driverType, Dictionary, Environment, Reporter);
		Finalizare completion = new Finalizare(driver, driverType, Dictionary, Environment, Reporter);
		Felicitari congrats = new Felicitari(driver, driverType, Dictionary, Environment, Reporter);
		Configureaddon config=new Configureaddon(driver, driverType, Dictionary, Environment, Reporter);
		DocumenteÎncărcatePopup uploadedDoc = new DocumenteÎncărcatePopup(driver, driverType, Dictionary, Environment, Reporter);
		String cnp = "";
		String prenume = "Auto";
		String nume = RandomStringUtils.randomAlphabetic(5);
		String LocalitatePrefix = "cit";
		String streetPrefix = "str";
		String mail = RandomStringUtils.randomAlphabetic(5) + "@google.com";
		String number = Environment.get("CONTACT_NUM_PREFIX") + RandomStringUtils.randomNumeric(8);
		int i=1;
		int counter = 0;
		
		
		
		//Login DEXP
		Assert.assertTrue(login.loginDexp(Environment.get("DEX_USER_ID"), Environment.get("DEX_PASSWORD")));
		Assert.assertTrue(login.selectMagazin(Dictionary.get("MAGAZIN")));
		Assert.assertTrue(login.clickContinuă());

		cnp = cnpGenerator.generateCnp();

		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickCnp());
		Assert.assertTrue(agentDashboard.setSearchValue(cnp));
		Assert.assertTrue(agentDashboard.clickSearch());
		if(agentDashboard.isInvalidCNPDisplay()==false)
		{
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouFix());
		}
		else{
			Assert.assertTrue(agentDashboard.handleInvalidCNP());
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouFix());	
		}
		
		
		
		Assert.assertTrue(address.isPageDisplay());
		Assert.assertTrue(address.selectJudetByIndex("1"));
		Assert.assertTrue(address.setLocalitate(LocalitatePrefix));
		Assert.assertTrue(address.selectLocalitateByIndex("1"));
		Assert.assertTrue(address.selectTipStradăByIndex("1"));
		Assert.assertTrue(address.setNumeStradă(streetPrefix));
		Assert.assertTrue(address.selectNumeStradăByIndex("1"));
		Assert.assertTrue(address.selectNumărStradăByIndex("1"));
		Assert.assertTrue(address.selectBlocByIndex("1"));
		Assert.assertTrue(address.selectScarăByIndex("1"));
		Assert.assertTrue(address.selectApartamentByIndex("1"));
		Assert.assertTrue(address.selectEtajByIndex("1"));
		Assert.assertTrue(address.clickContinuă());
		
		Assert.assertTrue(configuration.isPageDisplay());
		Assert.assertTrue(configuration.selectPackageByIndex("1"));
		Assert.assertTrue(configuration.selectAddonItem("Optiune Smart Protect 1 Licenta"));
		Assert.assertTrue(config.isConfigureAddonDispalyed());
		Assert.assertTrue(config.setNumarTelefon());
		Assert.assertTrue(config.setEmail());
		Assert.assertTrue(config.ClickSelecteaza());
		Assert.assertTrue(configuration.selectAddonItem("Optiune HBO"));
		Assert.assertTrue(configuration.clickMiniCart());
		configuration.getCartDetails_Service();
		String str=configuration.getOrderno();
		String[] arr = str.split("#", 0);
		System.out.println(arr[1]);
		ORDER_ID=arr[1];
		Assert.assertTrue(configuration.selectMandatoriesAddon());
		Assert.assertTrue(configuration.clickContinuă());

		Assert.assertTrue(details.isPageDisplay());
		Assert.assertTrue(details.setPrenume(prenume));
		Assert.assertTrue(details.setNume(nume));
		Assert.assertTrue(details.setEmail(mail));
		Assert.assertTrue(details.setTelefonContact(number));
		Assert.assertTrue(details.clickNuTelefonAlternativ());
		Assert.assertTrue(details.clickContinuă());
		
		Assert.assertTrue(equipment.clickContinuă());
		
		Assert.assertTrue(summary.isPageDisplay());
		Assert.assertTrue(summary.toggleProfilareVodafoneDaToateOn());
		Assert.assertTrue(summary.toggleProfilarePartnerDaToateOn());
		Assert.assertTrue(summary.clickContinuă());
		
		Assert.assertTrue(completion.isPageDisplay());
		Assert.assertTrue(completion.clickClientulEste());
		Assert.assertTrue(completion.clickGenereazăDocumente());
		for(i=1 ; i<8 ; i++){
			if(completion.countVizualizareLink()==2)
				break;
			else if (completion.isRegenerareDocumenteDisplay())
				Assert.assertTrue(completion.clickGenereazăDocumente());
		}
		Assert.assertTrue(completion.isPageDisplay());
		
		Assert.assertTrue(completion.clickÎnregistreazăComanda());
		
		Assert.assertTrue(completion.clickFinalizare());
		
		Assert.assertTrue(uploadedDoc.clickContinuă());
		
		Assert.assertTrue(congrats.isPageDisplay());
			String str1=completion.getOrder();
			String[] arr1 = str1.split("#", 0);
			System.out.println(arr1[1]);
			ORDER_ID=arr1[1];
		Assert.assertTrue(congrats.clickAgentDashboard());
		
		Assert.assertTrue(agentDashboard.isPageDisplay());
		
		objCommon.fCommonSync(29000);
		
		params.put("<ORDER_ID>", ORDER_ID);
		for(counter=0 ; counter<60 ; counter++)
		{
				try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ORDER_STATUS", "OMS",params);
			System.out.println("updated status of order " +rsData.getString("STATUS"));
			if(rsData.getString("STATUS").equalsIgnoreCase("SB"))
				objCommon.fCommonSync(3000);
			else
				break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get Order status from DB");
					}
				
		}
		Environment.put("<CNP>",cnp);
		Reporter.fnWriteToHtmlOutput("Create Fix provide", "Successful", "Successful", "Pass");
		return true;
	}

	// Created by Zachi
	public boolean MobilePortIn(String browser) throws IOException{
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		Login login = new Login(driver, driverType, Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		cnpGenerator cnpGenerator = new cnpGenerator(driver, driverType, Dictionary, Environment, Reporter);
		AbonamentNou newSubscriber = new AbonamentNou(driver, driverType, Dictionary, Environment, Reporter);
		Configurează configuration = new Configurează(driver, driverType, Dictionary, Environment, Reporter);
		Detalii details = new Detalii(driver, driverType, Dictionary, Environment, Reporter);
		Curier equipment = new Curier(driver, driverType, Dictionary, Environment, Reporter);
		Sumar summary = new Sumar(driver, driverType, Dictionary, Environment, Reporter);
		Finalizare completion = new Finalizare(driver, driverType, Dictionary, Environment, Reporter);
		Felicitari congrats = new Felicitari(driver, driverType, Dictionary, Environment, Reporter);
		DocumenteÎncărcatePopup uploadedDoc = new DocumenteÎncărcatePopup(driver, driverType, Dictionary, Environment, Reporter);
		Comenzi comenzi=new Comenzi(driver, driverType, Dictionary, Environment, Reporter);
		
		String cnp = "";
		String prenume = "Auto";
		String nume = RandomStringUtils.randomAlphabetic(5);
		String custId = RandomStringUtils.randomAlphanumeric(5);
		String streetPrefix = "str";
		String mail = RandomStringUtils.randomAlphabetic(5) + "@google.com";
		String sim = "";
		String newOrder="";
		
		params.put("<MSISDN>", msisdn);
		int i=1;
		
		//Login DEXP
		if(Dictionary.get("NPREV").isEmpty()){
			Assert.assertTrue(login.loginDexp(Environment.get("DEX_USER_ID"), Environment.get("DEX_PASSWORD")));
		}
		else{
			Assert.assertTrue(login.loginDexp(Environment.get("DEX_NPRV_USER"), Environment.get("DEX_NPREV_PASSWORD")));
		}
		//Assert.assertTrue(login.loginDexp(Environment.get("DEX_USER_ID"), Environment.get("DEX_PASSWORD")));
		Assert.assertTrue(login.selectMagazin(Dictionary.get("MAGAZIN")));
		Assert.assertTrue(login.clickContinuă());

		cnp = cnpGenerator.generateCnp();

		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickCnp());
		Assert.assertTrue(agentDashboard.setSearchValue(cnp));
		Assert.assertTrue(agentDashboard.clickSearch());
		if(agentDashboard.isInvalidCNPDisplay()==false)
		{
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouMobil());
		}
		else{
			Assert.assertTrue(agentDashboard.handleInvalidCNP());
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouMobil());	
		}
		
	
		
		Assert.assertTrue(newSubscriber.isPageDisplay());
		Assert.assertTrue(newSubscriber.clickPortareTab());
		Assert.assertTrue(newSubscriber.clickDa());
		Assert.assertTrue(newSubscriber.setNumărContact());
		Assert.assertTrue(newSubscriber.selectProviderByIndex("1"));
		Assert.assertTrue(newSubscriber.clickUrmătoarele());
		Assert.assertTrue(newSubscriber.setPrenume(prenume));
		Assert.assertTrue(newSubscriber.setNume(nume));
		Assert.assertTrue(newSubscriber.selectJudetByIndex("1"));
		Assert.assertTrue(newSubscriber.selectLocalitateByIndex("1"));
		Assert.assertTrue(newSubscriber.selectTipStradăByIndex("1"));
		Assert.assertTrue(newSubscriber.setNumeStradă(streetPrefix));
		Assert.assertTrue(newSubscriber.selectNumeStradăByIndex("2"));
		Assert.assertTrue(newSubscriber.clickContinuă());
		Assert.assertTrue(configuration.isPageDisplay());
		if(Dictionary.get("CIB").isEmpty())
			{
			Assert.assertTrue(configuration.clickConfigureazăOferte());
			Assert.assertTrue(configuration.openMenu());
			Assert.assertTrue(configuration.selectMenuItem("Doar servicii"));
			Assert.assertTrue(configuration.selectServiciiMobileByIndex("1"));
			Assert.assertTrue(configuration.clickMiniCart());
			configuration.getCartDetails_Service();
			String str=configuration.getOrderno();
			String[] arr = str.split("#", 0);
			System.out.println(arr[1]);
			ORDER_ID=arr[1];
		}
		else if(Dictionary.get("BUNDLE").equals("Y"))
		{
			Assert.assertTrue(configuration.isPageDisplay());
			Assert.assertTrue(configuration.selectPackageByIndex("1"));
			Assert.assertTrue(configuration.clickMiniCart());
			configuration.getCartDetails_Service();
			String str=configuration.getOrderno();
			String[] arr = str.split("#", 0);
			System.out.println(arr[1]);
			ORDER_ID=arr[1];
		}
		else  {
			Assert.assertTrue(configuration.clickConfigureazăOferte());
			Assert.assertTrue(configuration.clickHuwaeiOffer(Dictionary.get("Device")));
			Assert.assertTrue(configuration.clickDiscountOffer(Dictionary.get("Offer")));
			Assert.assertTrue(configuration.clickMiniCart());
			configuration.getCartDetails_StartDevice();
			String str=configuration.getOrderno();
			String[] arr = str.split("#", 0);
			System.out.println(arr[1]);
			ORDER_ID=arr[1];
		}
		
		
		Assert.assertTrue(configuration.isOpȚiuniPromoȚionaleDisplay());
		Assert.assertTrue(configuration.selectMandatoriesAddon());
		Assert.assertTrue(configuration.clickContinuă());

		Assert.assertTrue(details.isPageDisplay());
		Assert.assertTrue(details.setEmail(mail));
		Assert.assertTrue(details.clickNuTelefonAlternativ());
		Assert.assertTrue(details.selectCodPoȘtalByIndex("1"));
		Assert.assertTrue(details.setCodClientDonor(custId));
		msisdn=details.getmsisdn();
		if(msisdn.endsWith("#1)")){
			msisdn=msisdn.replace("(NUMĂRNOU#1)","");
			//System.out.println("getting msisdn " +msisdn);
		}
		System.out.println("Fetching msisdn" +msisdn);
		/*if (Dictionary.get("CBU").equals("Y"))
		{
			Assert.assertTrue(details.clickAșteaptăportarea());
			//Assert.assertTrue(details.clickModifica());
		}*/
		Assert.assertTrue(details.clickContinuă());
		if(Dictionary.get("CIB").isEmpty()||Dictionary.get("FISCAL").equals("Y") ||Dictionary.get("CBU").equals("Y"))
		{
			try{
				ResultSet rsData = dbAction.getDataFromDB(Dictionary.get("PK"), "ABP");
				sim = rsData.getString("PACKAGE_VALUE");
			}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get sim from DB");
			}
	
			Assert.assertTrue(equipment.isPageDisplay());
			//msisdn = equipment.getPhoneNumber();
				Assert.assertTrue(equipment.setSim(sim));
				Assert.assertTrue(equipment.clickPhoneNumber());
				if (Dictionary.get("FISCAL").equals("Y")||Dictionary.get("Device").startsWith("Huawei")){
					Assert.assertTrue(equipment.setImei(Dictionary.get("IMEI")));
					Assert.assertTrue(equipment.clickPhoneNumber());
				}
				Assert.assertTrue(equipment.clickContinuă());// needed to enable the 'continue' button
		}
		else if (Dictionary.get("MAGAZIN").startsWith("TC"))
		{
			Assert.assertTrue(equipment.clickPhoneNumber());
			Assert.assertTrue(equipment.clickContinuă());
		}
		else
		{
			Assert.assertTrue(equipment.clickEditare());
			Assert.assertTrue(equipment.isComentariiDisplay());
			Assert.assertTrue(equipment.addComentrii());
			Assert.assertTrue(equipment.clickSalveaza());
			Assert.assertTrue(equipment.clickContinuă());
		}
		Assert.assertTrue(summary.isPageDisplay());
		Assert.assertTrue(summary.toggleProfilareVodafoneDaToateOn());
		Assert.assertTrue(summary.toggleProfilarePartnerDaToateOn());
		Assert.assertTrue(summary.clickContinuă());
		
		Assert.assertTrue(completion.isPageDisplay());
		Assert.assertTrue(completion.clickClientulEste());
		Assert.assertTrue(completion.clickGenereazăDocumente());
		for(i=1 ; i<12 ; i++){
			if(completion.countVizualizareLink()==3)
				break;
			else if (completion.isRegenerareDocumenteDisplay())
				Assert.assertTrue(completion.clickGenereazăDocumente());
		}
		if (Dictionary.get("FISCAL").equals("Y")){
			Assert.assertTrue(completion.clickCauta());
			Assert.assertTrue(completion.attachDoc());
			Assert.assertTrue(completion.clickÎncarcăDocumente());
			Assert.assertTrue(completion.clickÎnregistreazăComanda());
			Assert.assertTrue(completion.isEsignDisplay());
			Assert.assertTrue(completion.clickFinalizare());
		}
		else{
			Assert.assertTrue(completion.clickÎnregistreazăComanda());
			Assert.assertTrue(completion.isEsignDisplay());
			Assert.assertTrue(completion.clickFinalizare());
		 }
		if(uploadedDoc.isPageDisplay())
			Assert.assertTrue(uploadedDoc.clickContinuă());
			
		String str=completion.getOrder();
		String[] arr = str.split("#", 0);
		System.out.println(arr[1]);
		ORDER_ID=arr[1];
		
		Assert.assertTrue(congrats.isPageDisplay());
		Assert.assertTrue(congrats.clickAgentDashboard());
		Assert.assertTrue(agentDashboard.isPageDisplay());
		if (Dictionary.get("CBU").equals("Y")||Dictionary.get("Cancel_Order").equals("Y"))
		{
				objCommon.fCommonSync(29000);
				
					params.put("<ORDER_ID>", ORDER_ID);
				for(int counter=0 ; counter<60 ; counter++)
				{
						try{
					ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ORDER_STATUS", "OMS",params);
					System.out.println("updated status of order " +rsData.getString("STATUS"));
					if(rsData.getString("STATUS").equalsIgnoreCase("SB"))
						objCommon.fCommonSync(3000);
					else
						break;
					
							}catch(Exception e){
					e.printStackTrace();
					Assert.fail("Failed to get Order status from DB");
							}
						
				}
				Assert.assertTrue(agentDashboard.clickCautacomenzi());
				Assert.assertTrue(comenzi.selectCautaDupa("Order ID"));
				Assert.assertTrue(comenzi.searchVal(ORDER_ID));
				Assert.assertTrue(comenzi.removefilter1());
				Assert.assertTrue(comenzi.removefilter2());
				Assert.assertTrue(comenzi.clickCnp());
				objCommon.fCommonSync(40000);
				if(Dictionary.get("Cancel_Order").equals("Y"))
				{
					for(i=0 ; i<90; i++){
						if(comenzi.isPrimaryDisplay()==false){
							objCommon.fCommonSync(3000);
						driver.navigate().to(driver.getCurrentUrl());
						}
						else 
							break;
					}
					objCommon.fCommonSync(18000);
					Assert.assertTrue(comenzi.clickPrimary());
					Assert.assertTrue(comenzi.clickView());
					newOrder=comenzi.gettextorder();
					 String[] arr1 = newOrder.split("#", 0);
						System.out.println(arr1[1]);
						newOrder=arr1[1];
					 System.out.println("new order id is:"+newOrder);
					Assert.assertTrue(comenzi.clicklinkANULEAZĂ());
					Assert.assertTrue(comenzi.selectReasonCode("CAPCR"));
					Assert.assertTrue(comenzi.clickGenerezaDocumente());
					objCommon.fCommonSync(4000);
					Assert.assertTrue(comenzi.clickCancel());
			}
			else if(Dictionary.get("ChangeMsisdn").equals("Y"))
			{
				Assert.assertTrue(Selectnewmsisdn(browser));
			}
			else{
			Assert.assertTrue(comenzi.clickAnuleaza());
			Assert.assertTrue(comenzi.selectReasonCode("CAPTH"));
			objCommon.fCommonSync(4000);
			Assert.assertTrue(comenzi.clickCancel());
			}
			objCommon.fCommonSync(29000);
			System.out.println("Printing Order Id: "+ORDER_ID);
			params.put("<ORDER_ID>", newOrder);
			for(int counter=0 ; counter<60 ; counter++)
			{
					try{
				ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ORDER_STATUS", "OMS",params);
				System.out.println("updated status of order " +rsData.getString("STATUS"));
				if(!rsData.getString("STATUS").equalsIgnoreCase("TC"))
					objCommon.fCommonSync(3000);
				else
					break;
				
						}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get Order status from DB");
						}
					
			}	
			Environment.put("<ORDER_ID>",ORDER_ID);
			Environment.put("<NEW_ORDER>",newOrder);
			Reporter.fnWriteToHtmlOutput("CBU Port in", "Successful", "Successful", "Pass");
			return true;								
		}
		objCommon.fCommonSync(29000);
		params.put("<ORDER_ID>", ORDER_ID);
		for(int counter=0 ; counter<60 ; counter++)
		{
				try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ORDER_STATUS", "OMS",params);
			System.out.println("updated status of order " +rsData.getString("STATUS"));
			if(rsData.getString("STATUS").equalsIgnoreCase("SB"))
				objCommon.fCommonSync(3000);
			else
				break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get Order status from DB");
					}
				
		}	
		
		objCommon.fCommonSync(18000);
		
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_NEW_MSISDN", "OMS",params);
			msisdn=rsData.getString("L9_SERVICE_ID");
			System.out.println("L9_SERVICE_ID: " +msisdn);
	
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get substatus from DB");
		}

		params.put("<MSISDN>", msisdn);
		objCommon.fCommonSync(18000);
		for(int counter=0 ; counter<60 ; counter++)
		{
				try{
					ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_SUBSTATUS", "ABP",params);
					System.out.println("Sub Status of order:" +rsData.getString("SUB_STATUS"));
			if(!rsData.getString("SUB_STATUS").equals("A"))
				objCommon.fCommonSync(3000);
			else
				break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get SUB_STATUS from DB");
					}
				
		}	
		params.put("<ORDER_ID>", ORDER_ID);
		String Url="";
		if (Dictionary.get("FISCAL").equals("Y")){
			for(int counter=0 ; counter<60 ; counter++)
			{
				
					try{
						ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_URL","OMS",params);
						 Url=rsData.getString("CONTRACT_URL");
						System.out.println("CONTRACT URL:" +rsData.getString("CONTRACT_URL"));
					
				if(rsData.getString("CONTRACT_URL").equals(" "))
					objCommon.fCommonSync(3000);
				else
					break;
				
						}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get SUB_STATUS from DB");
						}
					
			}
			completion.OpenUrl(Url);
		}
		Environment.put("<ORDER_ID>",ORDER_ID);
		Reporter.fnWriteToHtmlOutput("Create Mobile PortIn", "Successful", "Successful", "Pass");
		
		return true;
}


	// Created by Zachi
	public boolean ChangeSim(String browser){
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		Login login = new Login(driver, driverType, Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		cnpGenerator cnpGenerator = new cnpGenerator(driver, driverType, Dictionary, Environment, Reporter);
		AbonamentNou newSubscriber = new AbonamentNou(driver, driverType, Dictionary, Environment, Reporter);
		Configurează configuration = new Configurează(driver, driverType, Dictionary, Environment, Reporter);
		Detalii details = new Detalii(driver, driverType, Dictionary, Environment, Reporter);
		Curier equipment = new Curier(driver, driverType, Dictionary, Environment, Reporter);
		Sumar summary = new Sumar(driver, driverType, Dictionary, Environment, Reporter);
		Finalizare completion = new Finalizare(driver, driverType, Dictionary, Environment, Reporter);
		Felicitari congrats = new Felicitari(driver, driverType, Dictionary, Environment, Reporter);
		DocumenteÎncărcatePopup uploadedDoc = new DocumenteÎncărcatePopup(driver, driverType, Dictionary, Environment, Reporter);
		Page360 page360 = new Page360(driver, driverType, Dictionary, Environment, Reporter);
		SchimbăSimPopup changeSim = new SchimbăSimPopup(driver, driverType, Dictionary, Environment, Reporter);
		SetaliiAbonamentȘiServicii detailsAndServices = new SetaliiAbonamentȘiServicii(driver, driverType, Dictionary, Environment, Reporter);
	
		String cnp = "";
		String prenume = "Auto";
		String nume = RandomStringUtils.randomAlphabetic(5);
		String streetPrefix = "str";
		String mail = RandomStringUtils.randomAlphabetic(5) + "@google.com";
		String sim = "";
		int i=1;
		
		
		String actualSim = "";
		
		String count = "";
		int counter = 0;
		
		//Login DEXP
		Assert.assertTrue(login.loginDexp(Environment.get("DEX_USER_ID"), Environment.get("DEX_PASSWORD")));
		Assert.assertTrue(login.selectMagazin(Dictionary.get("MAGAZIN")));
		Assert.assertTrue(login.clickContinuă());

		cnp = cnpGenerator.generateCnp();

		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickCnp());
		Assert.assertTrue(agentDashboard.setSearchValue(cnp));
		Assert.assertTrue(agentDashboard.clickSearch());
		if(agentDashboard.isInvalidCNPDisplay()==false)
		{
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouMobil());
		}
		else{
			Assert.assertTrue(agentDashboard.handleInvalidCNP());
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouMobil());	
		}
		
		
		
		Assert.assertTrue(newSubscriber.isPageDisplay());
		Assert.assertTrue(newSubscriber.clickDa());
		Assert.assertTrue(newSubscriber.setNumărContact());
		Assert.assertTrue(newSubscriber.clickUrmătoarele());
		Assert.assertTrue(newSubscriber.setPrenume(prenume));
		Assert.assertTrue(newSubscriber.setNume(nume));
		Assert.assertTrue(newSubscriber.selectJudetByIndex("1"));
		Assert.assertTrue(newSubscriber.selectLocalitateByIndex("1"));
		Assert.assertTrue(newSubscriber.selectTipStradăByIndex("1"));
		Assert.assertTrue(newSubscriber.setNumeStradă(streetPrefix));
		Assert.assertTrue(newSubscriber.selectNumeStradăByIndex("2"));
		Assert.assertTrue(newSubscriber.clickContinuă());
		
		Assert.assertTrue(configuration.isPageDisplay());
		Assert.assertTrue(configuration.clickConfigureazăOferte());
		Assert.assertTrue(configuration.openMenu());
		Assert.assertTrue(configuration.selectMenuItem("Doar servicii"));
		Assert.assertTrue(configuration.selectServiciiMobileByIndex("1"));
		
		Assert.assertTrue(configuration.isOpȚiuniPromoȚionaleDisplay());
		Assert.assertTrue(configuration.clickMiniCart());
		configuration.getCartDetails_Service();
		String str=configuration.getOrderno();
		String[] arr = str.split("#", 0);
		System.out.println(arr[1]);
		ORDER_ID=arr[1];
		Assert.assertTrue(configuration.selectMandatoriesAddon());
		Assert.assertTrue(configuration.clickContinuă());

		Assert.assertTrue(details.isPageDisplay());
		Assert.assertTrue(details.setEmail(mail));
		Assert.assertTrue(details.clickNuTelefonAlternativ());
		Assert.assertTrue(details.selectCodPoȘtalByIndex("1"));
		Assert.assertTrue(details.clickContinuă());
		
		try{
			ResultSet rsData = dbAction.getDataFromDB(Dictionary.get("PK"), "ABP");
			sim = rsData.getString("PACKAGE_VALUE");
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get sim from DB");
		}

		Assert.assertTrue(equipment.isPageDisplay());
		msisdn = equipment.getPhoneNumber();
		Assert.assertTrue(equipment.setSim(sim));
		Assert.assertTrue(equipment.clickPhoneNumber()); // needed to enable the 'continue' button
		Assert.assertTrue(equipment.clickContinuă());
		
		Assert.assertTrue(summary.isPageDisplay());
		Assert.assertTrue(summary.toggleProfilareVodafoneDaToateOn());
		Assert.assertTrue(summary.toggleProfilarePartnerDaToateOn());
		Assert.assertTrue(summary.clickContinuă());
		
		Assert.assertTrue(completion.isPageDisplay());
		Assert.assertTrue(completion.clickClientulEste());
		Assert.assertTrue(completion.clickGenereazăDocumente());
		for(i=1 ; i<8 ; i++){
			if(completion.countVizualizareLink()==2)
				break;
			else if (completion.isRegenerareDocumenteDisplay())
				Assert.assertTrue(completion.clickGenereazăDocumente());
		}
		
		Assert.assertTrue(completion.clickÎnregistreazăComanda());
		Assert.assertTrue(completion.isEsignDisplay());
		Assert.assertTrue(completion.clickFinalizare());
		
		if(uploadedDoc.isPageDisplay())
			Assert.assertTrue(uploadedDoc.clickContinuă());
		
		
		Assert.assertTrue(congrats.clickAgentDashboard());
		
		Assert.assertTrue(agentDashboard.isPageDisplay());
			
		for(counter=0 ; counter<60 ; counter++){
			params.put("<MSISDN>", msisdn);
			try{
				ResultSet rsData = dbAction.getDataFromDBWithParameters("COUNT_SUBSCRIBER_BY_MSISDN", "ABP", params);
				if(rsData.getString("COUNT").equals("0"))
					objCommon.fCommonSync(3000);
				else
					break;
			}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get info from DB");
			}
		}
		objCommon.fCommonSync(40000);
		Assert.assertTrue(agentDashboard.clickMsisdn());
		for(counter=0 ; counter<60 ; counter++){
			Assert.assertTrue(agentDashboard.setSearchValue(msisdn));
			Assert.assertTrue(agentDashboard.clickSearch());
			if(agentDashboard.isNoResultDisplay())
				objCommon.fCommonSync(3000);
			else 
				break;
		}

		Assert.assertTrue(page360.isPageDisplay());
		Assert.assertTrue(page360.openMenu());
		Assert.assertTrue(page360.hoverselectMenuItem("Settings"));
		Assert.assertTrue(page360.selectMenuItem("Schimbă SIM"));

		try{
			ResultSet rsData = dbAction.getDataFromDB(Dictionary.get("PK"), "ABP");
			sim = rsData.getString("PACKAGE_VALUE");
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get sim from DB");
		}
		
		Assert.assertTrue(changeSim.isPageDisplay());
		Assert.assertTrue(changeSim.setSim(sim));
		Assert.assertTrue(changeSim.clickÎnregistreazăComanda());

		Assert.assertTrue(page360.isPageDisplay());
		Assert.assertTrue(page360.clickMaiMult());
		
		Assert.assertTrue(detailsAndServices.isPageDisplay());
		actualSim = detailsAndServices.getSim();
		if(actualSim.equals(sim))
			Reporter.fnWriteToHtmlOutput("Check SIM value in application after the change", sim, actualSim, "Pass");
		else
			Reporter.fnWriteToHtmlOutput("Check SIM value in application after the change", sim, actualSim, "Fail");
		
		params.put("<ITEM>", sim);
		params.put("<SRV_ID>", msisdn);
		params.put("<STATE>", "AS");
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("COUNT_TBAP_ITEM", "OMS", params);
			count = rsData.getString("COUNT");
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get info from DB");
		}
		
		for(counter=0 ; counter<60 ; counter++){
			try{
				ResultSet rsData = dbAction.getDataFromDBWithParameters("COUNT_TBAP_ITEM", "OMS", params);
				if(rsData.getString("COUNT").equals("0"))
					objCommon.fCommonSync(3000);
				else{ 
					count = rsData.getString("COUNT");
					break;
				}
			}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get info from DB");
			}
		}

		if(count.equals("1"))
			Reporter.fnWriteToHtmlOutput("Check SIM value in DB after the change", "record numbers: 1", "record numbers: " + count, "Pass");
		//else
			//Reporter.fnWriteToHtmlOutput("Check SIM value in DB after the change", "record numbers: 1", "record numbers: " + count, "Fail");

				
		objCommon.fCommonSync(29000);
		
		params.put("<ORDER_ID>", ORDER_ID);
		for(counter=0 ; counter<60 ; counter++)
		{
				try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ORDER_STATUS", "OMS",params);
			System.out.println("updated status of order " +rsData.getString("STATUS"));
			if(rsData.getString("STATUS").equalsIgnoreCase("SB"))
				objCommon.fCommonSync(3000);
			else
				break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get Order status from DB");
					}
				
		}

	params.put("<MSISDN>", msisdn);
	objCommon.fCommonSync(18000);
	for(counter=0 ; counter<60 ; counter++)
	{
			try{
				ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_SUBSTATUS", "ABP",params);
				System.out.println("Sub Status of order:" +rsData.getString("SUB_STATUS"));
		if(!rsData.getString("SUB_STATUS").equals("A"))
			objCommon.fCommonSync(3000);
		else
			break;
		
				}catch(Exception e){
		e.printStackTrace();
		Assert.fail("Failed to get SUB_STATUS from DB");
				}
			
	}
	return true;
	}

	// Created by Zachi
	public boolean ChangeMsisdn(String browser){
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		Login login = new Login(driver, driverType, Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		cnpGenerator cnpGenerator = new cnpGenerator(driver, driverType, Dictionary, Environment, Reporter);
		AbonamentNou newSubscriber = new AbonamentNou(driver, driverType, Dictionary, Environment, Reporter);
		Configurează configuration = new Configurează(driver, driverType, Dictionary, Environment, Reporter);
		Detalii details = new Detalii(driver, driverType, Dictionary, Environment, Reporter);
		Curier equipment = new Curier(driver, driverType, Dictionary, Environment, Reporter);
		Sumar summary = new Sumar(driver, driverType, Dictionary, Environment, Reporter);
		Finalizare completion = new Finalizare(driver, driverType, Dictionary, Environment, Reporter);
		Felicitari congrats = new Felicitari(driver, driverType, Dictionary, Environment, Reporter);
		DocumenteÎncărcatePopup uploadedDoc = new DocumenteÎncărcatePopup(driver, driverType, Dictionary, Environment, Reporter);
		Page360 page360 = new Page360(driver, driverType, Dictionary, Environment, Reporter);
		SchimbareNumărPopup changeMsisdn = new SchimbareNumărPopup(driver, driverType, Dictionary, Environment, Reporter);
		Header header = new Header(driver, driverType, Dictionary, Environment, Reporter);
		
		String cnp = "";
		String prenume = "Auto";
		String nume = RandomStringUtils.randomAlphabetic(5);
		String streetPrefix = "str";
		String mail = RandomStringUtils.randomAlphabetic(5) + "@google.com";
		String sim = "";
		int i=1;
		
		
		int counter=0 ;
		
		
		
		//Login DEXP
		Assert.assertTrue(login.loginDexp(Environment.get("DEX_USER_ID"), Environment.get("DEX_PASSWORD")));
		Assert.assertTrue(login.selectMagazin(Dictionary.get("MAGAZIN")));
		Assert.assertTrue(login.clickContinuă());

		cnp = cnpGenerator.generateCnp();

		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickCnp());
		Assert.assertTrue(agentDashboard.setSearchValue(cnp));
		Assert.assertTrue(agentDashboard.clickSearch());
		if(agentDashboard.isInvalidCNPDisplay()==false)
		{
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouMobil());
		}
		else{
			Assert.assertTrue(agentDashboard.handleInvalidCNP());
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouMobil());	
		}
		
		
		
		Assert.assertTrue(newSubscriber.isPageDisplay());
		Assert.assertTrue(newSubscriber.clickDa());
		Assert.assertTrue(newSubscriber.setNumărContact());
		Assert.assertTrue(newSubscriber.clickUrmătoarele());
		Assert.assertTrue(newSubscriber.setPrenume(prenume));
		Assert.assertTrue(newSubscriber.setNume(nume));
		Assert.assertTrue(newSubscriber.selectJudetByIndex("1"));
		Assert.assertTrue(newSubscriber.selectLocalitateByIndex("1"));
		Assert.assertTrue(newSubscriber.selectTipStradăByIndex("1"));
		Assert.assertTrue(newSubscriber.setNumeStradă(streetPrefix));
		Assert.assertTrue(newSubscriber.selectNumeStradăByIndex("2"));
		Assert.assertTrue(newSubscriber.clickContinuă());
		
		Assert.assertTrue(configuration.isPageDisplay());
		Assert.assertTrue(configuration.clickConfigureazăOferte());
		Assert.assertTrue(configuration.openMenu());
		Assert.assertTrue(configuration.selectMenuItem("Doar servicii"));
		Assert.assertTrue(configuration.selectServiciiMobileByIndex("1"));
		
		Assert.assertTrue(configuration.isOpȚiuniPromoȚionaleDisplay());
		Assert.assertTrue(configuration.clickMiniCart());
		configuration.getCartDetails_Service();
		String str=configuration.getOrderno();
		String[] arr = str.split("#", 0);
		System.out.println(arr[1]);
		ORDER_ID=arr[1];
		Assert.assertTrue(configuration.selectMandatoriesAddon());
		Assert.assertTrue(configuration.clickContinuă());

		Assert.assertTrue(details.isPageDisplay());
		Assert.assertTrue(details.setEmail(mail));
		Assert.assertTrue(details.clickNuTelefonAlternativ());
		Assert.assertTrue(details.selectCodPoȘtalByIndex("1"));
		Assert.assertTrue(details.clickContinuă());
		
		try{
			ResultSet rsData = dbAction.getDataFromDB(Dictionary.get("PK"), "ABP");
			sim = rsData.getString("PACKAGE_VALUE");
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get sim from DB");
		}

		Assert.assertTrue(equipment.isPageDisplay());
		msisdn = equipment.getPhoneNumber();
		Assert.assertTrue(equipment.setSim(sim));
		Assert.assertTrue(equipment.clickPhoneNumber()); // needed to enable the 'continue' button
		Assert.assertTrue(equipment.clickContinuă());
		
		Assert.assertTrue(summary.isPageDisplay());
		Assert.assertTrue(summary.toggleProfilareVodafoneDaToateOn());
		Assert.assertTrue(summary.toggleProfilarePartnerDaToateOn());
		Assert.assertTrue(summary.clickContinuă());
		
		Assert.assertTrue(completion.isPageDisplay());
		Assert.assertTrue(completion.clickClientulEste());
		Assert.assertTrue(completion.clickGenereazăDocumente());
		for(i=1 ; i<8 ; i++){
			if(completion.countVizualizareLink()==2)
				break;
			else if (completion.isRegenerareDocumenteDisplay())
				Assert.assertTrue(completion.clickGenereazăDocumente());
		}
		
		Assert.assertTrue(completion.clickÎnregistreazăComanda());
		Assert.assertTrue(completion.isEsignDisplay());
		Assert.assertTrue(completion.clickFinalizare());
		
		if(uploadedDoc.isPageDisplay())
			Assert.assertTrue(uploadedDoc.clickContinuă());
		
		Assert.assertTrue(congrats.isPageDisplay());
		
		Assert.assertTrue(congrats.clickAgentDashboard());
		
		for(counter=0 ; counter<60 ; counter++){
			params.put("<MSISDN>", msisdn);
			try{
				ResultSet rsData = dbAction.getDataFromDBWithParameters("COUNT_SUBSCRIBER_BY_MSISDN", "ABP", params);
				if(rsData.getString("COUNT").equals("0"))
					objCommon.fCommonSync(3000);
				else
					break;
			}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get info from DB");
			}
		}

		Assert.assertTrue(agentDashboard.isPageDisplay());
		
		objCommon.fCommonSync(40000);
		Assert.assertTrue(agentDashboard.clickMsisdn());
		for(counter=0 ; counter<60 ; counter++){
			Assert.assertTrue(agentDashboard.setSearchValue(msisdn));
			Assert.assertTrue(agentDashboard.clickSearch());
			if(agentDashboard.isNoResultDisplay())
				objCommon.fCommonSync(3000);
			else 
				break;
		}
		Assert.assertTrue(page360.isPageDisplay());
		Assert.assertTrue(page360.openMenu());
		Assert.assertTrue(page360.hoverselectMenuItem("Settings"));
		Assert.assertTrue(page360.hoverselectMenuItem("Change Phone Number"));
		Assert.assertTrue(page360.selectMenuItem("Standard/Special Number"));

		Assert.assertTrue(changeMsisdn.isPageDisplay());
		newMsisdn = changeMsisdn.getNewNumber();
		Assert.assertTrue(changeMsisdn.clickRezervăNumăr());
		Assert.assertTrue(changeMsisdn.clickTrimite());
		
		Assert.assertTrue(page360.isPageDisplay());
		
		Assert.assertTrue(header.clickVodafoneLogo());
		
		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickMsisdn());
		objCommon.fCommonSync(40000);
		for(counter=0 ; counter<60 ; counter++){
			Assert.assertTrue(agentDashboard.setSearchValue(newMsisdn));
			Assert.assertTrue(agentDashboard.clickSearch());
			if(agentDashboard.isNoResultDisplay())
				objCommon.fCommonSync(3000);
			else 
				break;
		}

		
		objCommon.fCommonSync(29000);
		
		params.put("<ORDER_ID>", ORDER_ID);
		for(counter=0 ; counter<60 ; counter++)
		{
				try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ORDER_STATUS", "OMS",params);
			System.out.println("updated status of order " +rsData.getString("STATUS"));
			if(rsData.getString("STATUS").equalsIgnoreCase("SB"))
				objCommon.fCommonSync(3000);
			else
				break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get Order status from DB");
					}
				
		}
		objCommon.fCommonSync(18000);
		
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_NEW_MSISDN", "OMS",params);
			msisdn=rsData.getString("L9_SERVICE_ID");
			System.out.println("L9_SERVICE_ID: " +msisdn);
	
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get substatus from DB");
		}

	params.put("<MSISDN>", newMsisdn);
	objCommon.fCommonSync(18000);
	for(counter=0 ; counter<60 ; counter++)
	{
			try{
				ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_SUBSTATUS", "ABP",params);
				System.out.println("Sub Status of order:" +rsData.getString("SUB_STATUS"));
		if(!rsData.getString("SUB_STATUS").equals("A"))
			objCommon.fCommonSync(3000);
		else
			break;
		
				}catch(Exception e){
		e.printStackTrace();
		Assert.fail("Failed to get SUB_STATUS from DB");
				}
			
	}	
	
	Environment.put("<MSISDN>",msisdn);
	Environment.put("<ORDER_ID>",ORDER_ID);
	Environment.put("<NEWMSISDN>",newMsisdn);
	if(page360.isPageDisplay())
		Reporter.fnWriteToHtmlOutput("Check MSISDN had been changed successfully", "Successful", "Successful", "Pass");
	else
		Reporter.fnWriteToHtmlOutput("Check MSISDN had been changed successfully", "Successful", "Failed", "Fail");
	return true;
	}

	//Created by Sayali
	public boolean ProvideGA(String browser) throws IOException{
		Assert.assertTrue(MobileProvide(browser));
		return true;
	}
	//Created by Sayali
	public boolean PretoPostWithService(String browser) throws IOException{
		Assert.assertTrue(MobileProvide(browser));
		return true;
	}
	//Created by Sayali
	public boolean VodafonePartnerPretoPost(String browser) throws IOException{
		Assert.assertTrue(MobileProvide(browser));
		return true;
	}
	//Created by Sayali
	public boolean CIBPortIn(String browser) throws IOException{
		Assert.assertTrue(MobilePortIn(browser));
		return true;
	}
	//Created by Sayali
	public boolean ProvideNonPrev_Service(String browser) throws IOException{
		Assert.assertTrue(MobileProvide(browser));
		return true;
	}
	
	public boolean PreorderCombineDelivery(String browser) throws IOException{
		Assert.assertTrue(MobileProvide(browser));
		return true;
	}
	public boolean SplitDeliveryMigPost(String browser) throws IOException{
		Assert.assertTrue(VodafonePartnerPretoPost(browser));
		return true;
	}
	public boolean FiscalInvoice(String browser) throws IOException
	{
		Assert.assertTrue(MobilePortIn(browser));
		return true;
	}
	public boolean NVFD(String browser) throws IOException{
		Assert.assertTrue(MobileProvide(browser));
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		
		
		String PARENT_ORDER="";
		msisdn=Environment.get("<MSISDN>");
		System.out.println("msisdn: "+msisdn);
		params.put("<MSISDN>", msisdn);
		try{
		ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_PARENT_UNITID", "OMS",params);
		PARENT_ORDER=rsData.getString("PARENT_ORDER_UNIT");
		System.out.println("Parent order unit id: " +rsData.getString("PARENT_ORDER_UNIT"));
		}catch(Exception e){
		e.printStackTrace();
		Assert.fail("Failed to get Order status from DB");
				}
		
			params.put("<PARENT_ORDER>", PARENT_ORDER);
		for(int counter=0 ; counter<60 ; counter++)
		{
				try{
					ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_SERVICE_TYPE", "OMS",params);
					System.out.println("SERVICE_TYPE: " +rsData.getString("SERVICE_TYPE"));
					if(rsData.getString("SERVICE_TYPE").equals(" "))
						objCommon.fCommonSync(3000);
					else
						break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get Order status from DB");
					}
				
		}
		
		return true;
	}
	public boolean MTV331(String browser) throws IOException{
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		Login login = new Login(driver, driverType, Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		Detalii details = new Detalii(driver, driverType, Dictionary, Environment, Reporter);
		Curier equipment = new Curier(driver, driverType, Dictionary, Environment, Reporter);
		Page360 page360 = new Page360(driver, driverType, Dictionary, Environment, Reporter);
		AbonamentNou newSubscriber = new AbonamentNou(driver, driverType, Dictionary, Environment, Reporter);
		String streetPrefix = "str";
		
		//msisdn=Environment.get("<MSISDN>");
		//sim=Environment.get("<SIM>");
		Assert.assertTrue(MobileProvide(browser));
		 msisdn=Environment.get("<MSISDN>");
		System.out.println("msisdn: "+msisdn);
		
		Assert.assertTrue(login.loginDexp(Environment.get("DEX_USER_ID"), Environment.get("DEX_PASSWORD")));
		Assert.assertTrue(login.selectMagazin("TC5CT100"));
		Assert.assertTrue(login.clickContinuă());

		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickMsisdn());
		Assert.assertTrue(agentDashboard.setSearchValue(msisdn));
		Assert.assertTrue(agentDashboard.clickSearch());
		
		Assert.assertTrue(page360.isPageDisplay());
		Assert.assertTrue(page360.openMenu());
		Assert.assertTrue(page360.hoverselectMenuItem("Settings"));
		Assert.assertTrue(page360.selectMenuItem("Expediere SIM"));
		Assert.assertTrue(page360.ispageSimDisplay());
		Assert.assertTrue(page360.clickLivrare());
		//change address here
		
		Assert.assertTrue(newSubscriber.selectJudetByIndex("2"));
		Assert.assertTrue(newSubscriber.selectLocalitateByIndex("1"));
		Assert.assertTrue(newSubscriber.selectTipStradăByIndex("1"));
		Assert.assertTrue(newSubscriber.setNumeStradă(streetPrefix));
		Assert.assertTrue(newSubscriber.selectNumeStradăByIndex("1"));
		Assert.assertTrue(details.selectCodPoȘtalByIndex("1"));
		Assert.assertTrue(equipment.clickSalveaza());
		//click 
		Assert.assertTrue(page360.clickTrimiteSim());
		params.put("<MSISDN>", msisdn);
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_SUBSTATUS", "ABP",params);
			
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get substatus from DB");
		}
		
		Reporter.fnWriteToHtmlOutput("MTV331", "Successful", "Successful", "Pass");
		return true;
	}
	
	public boolean BarSim(String browser) throws IOException{
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		Login login = new Login(driver, driverType, Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		Page360 page360 = new Page360(driver, driverType, Dictionary, Environment, Reporter);
		
		
		int counter=0;
		
		
		if(Dictionary.get("Unbar").equals(""))
		{
			Assert.assertTrue(MobileProvide(browser));
		}
		
			Assert.assertTrue(login.loginDexp(Environment.get("DEX_USER_ID"), Environment.get("DEX_PASSWORD")));
			Assert.assertTrue(login.selectMagazin("PO1BB100"));
			Assert.assertTrue(login.clickContinuă());
		
		
		msisdn=Environment.get("<MSISDN>");
		ORDER_ID=Environment.get("<ORDER_ID>");
		System.out.println("msisdn: "+msisdn);
		System.out.println("ORDER_ID: "+ORDER_ID);
		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickMsisdn());
		Assert.assertTrue(agentDashboard.setSearchValue(msisdn));
		Assert.assertTrue(agentDashboard.clickSearch());
		
		Assert.assertTrue(page360.isPageDisplay());
		Assert.assertTrue(page360.openMenu());
		Assert.assertTrue(page360.hoverselectMenuItem("Settings"));
		Assert.assertTrue(page360.selectMenuItem("Blocare/Deblocare"));
		//Assert.assertTrue(page360.clickDa());
		Assert.assertTrue(page360.clickDaContinua());
		Environment.put("<MSISDN>", msisdn);
		
		
		params.put("<ORDER_ID>", ORDER_ID);
					try{
					ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_CUSTID", "OMS",params);
					CUSTID=rsData.getString("customer_id");
					System.out.println("Customer id: " +rsData.getString("customer_id"));
					}catch(Exception e){
					e.printStackTrace();
					Assert.fail("Customer id from DB");
							}
						
				
			params.put("<CUSTID>", CUSTID);
				for(counter=0 ; counter<60 ; counter++)
				{
						try{
					ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_REASON", "OMS",params);
					System.out.println("REASON_ID: " +rsData.getString("REASON_ID"));
					if(!rsData.getString("REASON_ID").equals("BARN"))
						objCommon.fCommonSync(3000);
					else
						break;
					
							}catch(Exception e){
					e.printStackTrace();
					Assert.fail("Failed to get Order status from DB");
							}
						
				}
				params.put("<CUSTID>", CUSTID);
				for(counter=0 ; counter<60 ; counter++)
				{
						try{
					ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ACTION_TYPE", "OMS",params);
					System.out.println("ACTION_TYPE: " +rsData.getString("ACTION_TYPE"));
					if(!rsData.getString("ACTION_TYPE").equals("CH"))
						objCommon.fCommonSync(3000);
					else
						break;
					
							}catch(Exception e){
					e.printStackTrace();
					Assert.fail("Failed to get Order status from DB");
							}
						
				}
				params.put("<CUSTID>", CUSTID);
				for(counter=0 ; counter<60 ; counter++)
				{
						try{
					ResultSet rsData = dbAction.getDataFromDBWithParameters("CHECK_ORDER", "OMS",params);
					System.out.println("updated status of order " +rsData.getString("STATUS"));
					if(!rsData.getString("STATUS").equals("DO"))
						objCommon.fCommonSync(3000);
					else
						break;
					
							}catch(Exception e){
					e.printStackTrace();
					Assert.fail("Failed to get Order status from DB");
							}
						
				}
				if(Dictionary.get("Unbar").equals(""))
				{
				
						params.put("<MSISDN>", msisdn);
						
						for(counter=0 ; counter<60 ; counter++)
						{
								try{
							ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_PROMATION_NAME", "OMS",params);
							System.out.println("PROMOTION_NAME: " +rsData.getString("PROMOTION_NAME"));
							if(!rsData.getString("PROMOTION_NAME").equals("Activare VoluntaryBAR"))
								objCommon.fCommonSync(3000);
							else
								break;
							
									}catch(Exception e){
							e.printStackTrace();
							Assert.fail("Failed to get Order status from DB");
									}
								
						}
				 }
				else
				{
						params.put("<MSISDN>", msisdn);
						for(counter=0 ; counter<60 ; counter++)
						{
								try{
							ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_PROMATION_NAME_UNBAR", "OMS",params);
							System.out.println("PROMOTION_NAME: " +rsData.getString("PROMOTION_NAME"));
							if(rsData.getString("PROMOTION_NAME").equals("Activare VoluntaryUNBAR"))
								objCommon.fCommonSync(3000);
							else
								break;
							
									}catch(Exception e){
							e.printStackTrace();
							Assert.fail("Failed to get Order status from DB");
									}
								
						}
						
						
						params.put("<MSISDN>", msisdn);
						for(counter=0 ; counter<60 ; counter++)
						{
								try{
							ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ORDER_COUNT", "OMS",params);
							System.out.println("COUNT " +rsData.getString("COUNT"));
							if(rsData.getString("COUNT").equals(" "))
								objCommon.fCommonSync(3000);
							else
								break;
							
									}catch(Exception e){
							e.printStackTrace();
							Assert.fail("Failed to get Order status from DB");
									}
								
						}
				}
				
		Reporter.fnWriteToHtmlOutput("Bar Sim", "Successful", "Successful", "Pass");
		return true;
	}
	public boolean Selectnewmsisdn(String browser)
	{
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		Page360 page360 = new Page360(driver, driverType, Dictionary, Environment, Reporter);
		SchimbareNumărPopup changeMsisdn = new SchimbareNumărPopup(driver, driverType, Dictionary, Environment, Reporter);
		Header header = new Header(driver, driverType, Dictionary, Environment, Reporter);
		
		
		int counter=0;
		for(counter=0 ; counter<60 ; counter++){
			params.put("<MSISDN>", msisdn);
			try{
				ResultSet rsData = dbAction.getDataFromDBWithParameters("COUNT_SUBSCRIBER_BY_MSISDN", "ABP", params);
				if(rsData.getString("COUNT").equals("0"))
					objCommon.fCommonSync(3000);
				else
					break;
			}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get info from DB");
			}
		}

		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickMsisdn());
		Assert.assertTrue(agentDashboard.setSearchValue(msisdn));
		Assert.assertTrue(agentDashboard.clickSearch());
		
		Assert.assertTrue(page360.isPageDisplay());
		Assert.assertTrue(page360.openMenu());
		Assert.assertTrue(page360.hoverselectMenuItem("Settings"));
		Assert.assertTrue(page360.hoverselectMenuItem("Change Phone Number"));
		Assert.assertTrue(page360.selectMenuItem("Standard/Special Number"));

		Assert.assertTrue(changeMsisdn.isPageDisplay());
		newMsisdn = changeMsisdn.getNewNumber();
		Assert.assertTrue(changeMsisdn.clickRezervăNumăr());
		Assert.assertTrue(changeMsisdn.clickTrimite());
		
		Assert.assertTrue(page360.isPageDisplay());
		
		Assert.assertTrue(header.clickVodafoneLogo());
		
		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickMsisdn());
		objCommon.fCommonSync(40000);
		for(counter=0 ; counter<60 ; counter++){
			Assert.assertTrue(agentDashboard.setSearchValue(newMsisdn));
			Assert.assertTrue(agentDashboard.clickSearch());
			if(agentDashboard.isNoResultDisplay())
				objCommon.fCommonSync(3000);
			else 
				break;
		}

		return true;
	}
	public boolean UnbarSim(String browser) throws IOException{
		
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		msisdn=Environment.get("<MSISDN>");
			System.out.println("msisdn: "+msisdn);
		Assert.assertTrue(BarSim(browser));
		params.put("<CUSTID>", CUSTID);
		for(int counter=0 ; counter<60 ; counter++)
		{
				try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ACTION_TYPE", "OMS",params);
			System.out.println("updated status of order " +rsData.getString("ACTION_TYPE"));
			if(!rsData.getString("ACTION_TYPE").equals("CH"))
				objCommon.fCommonSync(3000);
			else
				break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get Order status from DB");
					}
				
		}
		Reporter.fnWriteToHtmlOutput("UnBar Sim", "Successful", "Successful", "Pass");
		return true;
	}
	
	public boolean ConnectUnix(String browser) throws IOException{				
		
		cm.fConnectUnixBox();	
		
		fromServer = cm.getFromServer();
		toServer = cm.getToServer();
		session = cm.getSession();
		channel = cm.getChannel();				
		

		String command1="ssh dexpwrk1@illnqw4130";
		String command2="ls -ltr";
		String command3="cd Automation";
		String command4="./ChangePortDate -d "+Dictionary.get("PORT_DATE");
		System.out.println(command4);

//		cm.fFireFreeUnixCommand(fromServer, toServer ,command4);run kar wait
//		
		cm.fFireFreeUnixCommand(fromServer, toServer ,command1);
//		
		cm.fFireFreeUnixCommand(fromServer, toServer ,command2);
		cm.fFireFreeUnixCommand(fromServer, toServer ,command3);
		cm.fFireFreeUnixCommand(fromServer, toServer ,command4);
		String c = cm.fFireFreeUnixCommand(fromServer, toServer ,command1);
		System.out.println(c);
		String b = cm.fFireFreeUnixCommand(fromServer, toServer ,command2);
		System.out.println(b);
		String a = cm.fFireFreeUnixCommand(fromServer, toServer ,command3);
		System.out.println(a);
		String d = cm.fFireFreeUnixCommand(fromServer, toServer ,command4);
		System.out.println(d);
		
		
		cm.closeUnixConnection(session, channel);	
		Assert.assertTrue(MobilePortIn(browser));
		Reporter.fnWriteToHtmlOutput("ConnectUnix", "Successful", "Successful", "Pass");
		return true;
	}
	public boolean dbtest(String browser){
		Finalizare completion = new Finalizare(driver, driverType, Dictionary, Environment, Reporter);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		String msisdn = "757001493";
		ORDER_ID="4410";
		String sim="8940100100000000670";
		HashMap<String, String> params = new HashMap<>();
		String PARENT_ORDER="";
		String CUSTID="850357222";
		
		
		
		
params.put("<CUSTID>",CUSTID);
		
		for(int counter=0 ; counter<60 ; counter++)
		{
			try{
				ResultSet rsData = dbAction.getDataFromDBWithParameters("CHECK_TBAP_CHARGE_INFO", "OMS",params);
				 String Cnt = rsData.getString("COUNT(*)");
				System.out.println("Printing Count: "+Cnt);
				if(rsData.getString("COUNT(*)").equals("0"))
					objCommon.fCommonSync(3000);
				else
					break;
				
			}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get count");
			}
			
		
		
		params.put("<ORDER_ID>",ORDER_ID);
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_CUSTID", "OMS",params);
			CUSTID=rsData.getString("customer_id");
			System.out.println("Printing Customer Id: "+CUSTID);

		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get count");
		}
		params.put("<CUSTID>",CUSTID);
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_QA_STATUS", "OMS",params);
			System.out.println("Printing Customer Id: "+rsData.getString("OA_STATUS"));
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get count");
		}
		
		
		params.put("<ORDER_ID>",ORDER_ID);
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_CUSTID", "OMS",params);
			CUSTID=rsData.getString("customer_id");
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get count");
		}
		params.put("<CUSTID>",CUSTID);
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_COUNT", "OMS",params);
			
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get count");
		}
		params.put("<CUSTID>",CUSTID);
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_RESOURCE_COUNT", "OMS",params);
			
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get count");
		}
		params.put("<CUSTID>",CUSTID);
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("CHECK_TBAP_CHARGE_INFO", "OMS",params);
			
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get count");
		}
		
		
		
		
		
		
		
		params.put("<CUSTID>", CUSTID);
				try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ACTION_TYPE", "OMS",params);
			System.out.println("ACTION_TYPE: " +rsData.getString("ACTION_TYPE"));
			}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get Action type from DB");
					}
				
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		params.put("<MSISDN>", msisdn);
		try{
		ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_PARENT_UNITID", "OMS",params);
		PARENT_ORDER=rsData.getString("PARENT_ORDER_UNIT");
		System.out.println("Parent order unit id: " +rsData.getString("PARENT_ORDER_UNIT"));
		}catch(Exception e){
		e.printStackTrace();
		Assert.fail("Failed to get Order status from DB");
				}
		
			params.put("<PARENT_ORDER>", PARENT_ORDER);
	
		}
		
		
		
		
		
		params.put("<ORDER_ID>", ORDER_ID);
		String Url="";
		
			for(int counter=0 ; counter<60 ; counter++)
			{
				
					try{
						ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_URL","OMS",params);
						 Url=rsData.getString("CONTRACT_URL");
						System.out.println("CONTRACT URL:" +rsData.getString("CONTRACT_URL"));
					
				if(rsData.getString("CONTRACT_URL").equals(" "))
					objCommon.fCommonSync(3000);
				else
					break;
				
						}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get SUB_STATUS from DB");
						}
					
			}
			completion.OpenUrl(Url);
		
		
		
		
		
		
		
		//msisdn=Dictionary.get("MSISDN");
		params.put("<MSISDN>", msisdn);
		objCommon.fCommonSync(18000);
		for(int counter=0 ; counter<60 ; counter++)
		{
				try{
					ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_SUBSTATUS", "ABP",params);
					System.out.println("Sub Status of order:" +rsData.getString("SUB_STATUS"));
			if(!rsData.getString("SUB_STATUS").equals("A"))
				objCommon.fCommonSync(3000);
			else
				break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get SUB_STATUS from DB");
					}
				
		}	
		params.put("<MSISDN>", msisdn);
		params.put("<SIM>", sim);
		for(int counter=0 ; counter<60 ; counter++)
		{
				try{
					ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_RESOURCE_VALUE", "ABP",params);
					System.out.println("RESOURCE STATUS"+rsData.getString("RESOURCE_STATUS"));
			if(!rsData.getString("RESOURCE_STATUS").equals("ASSIGNED"))
				objCommon.fCommonSync(3000);
			else
				break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get RESOURCE_STATUS from DB");
					}
				
		}
		
		params.put("<ORDER_ID>", ORDER_ID);
		String st="";
		objCommon.fCommonSync(3000);
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ORDER_STATUS", "OMS",params);
			st=rsData.getString("STATUS");
			System.out.println("Status of order"+st);
		
		
			}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get Order status from DB");
		   }
		
		try{
			ResultSet rsData = dbAction.getDataFromDB(Dictionary.get("PK4"), "CRM");
			msisdn = rsData.getString("MSISDN");
			ResultSet abc=rsData;
			java.sql.ResultSetMetaData rsmd = rsData.getMetaData();
			   //System.out.println("querying SELECT * FROM XXX");
			   int columnsNumber = rsmd.getColumnCount();
			   while (rsData.next()) {
			       for (int i = 1; i <= columnsNumber; i++) {
			           if (i > 1) System.out.print(",  ");
			           String columnValue = rsData.getString(i);
			           System.out.print(columnValue + " " + rsmd.getColumnName(i));
			       }
			       System.out.println("");
			   }
			
			
			System.out.println("print the query output"+abc);
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get sim from DB");
		}
		params.put("<MSISDN>", msisdn);
		
		
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_SUBSTATUS", "ABP",params);
			
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get sim from DB");
		}
		
		
		Reporter.fnWriteToHtmlOutput("MTV331", "Successful", "Successful", "Pass");
		return true;
	}

	public boolean MTV333(String browser) throws IOException{
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		Configurează configuration = new Configurează(driver, driverType, Dictionary, Environment, Reporter);
		Sumar summary = new Sumar(driver, driverType, Dictionary, Environment, Reporter);
		Finalizare completion = new Finalizare(driver, driverType, Dictionary, Environment, Reporter);
		Felicitari congrats = new Felicitari(driver, driverType, Dictionary, Environment, Reporter);
		DocumenteÎncărcatePopup uploadedDoc = new DocumenteÎncărcatePopup(driver, driverType, Dictionary, Environment, Reporter);
		Page360 page360 = new Page360(driver, driverType, Dictionary, Environment, Reporter);
		
		
		
		 Assert.assertTrue(MobileProvide(browser));
		 msisdn=Environment.get("<MSISDN>");
		 ORDER_ID=Environment.get("<ORDER_ID>");
		  System.out.println("msisdn: "+msisdn);
		  System.out.println("ORDER_ID: "+ORDER_ID);
		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickMsisdn());
		Assert.assertTrue(agentDashboard.setSearchValue(msisdn));
		Assert.assertTrue(agentDashboard.clickSearch());
		
		Assert.assertTrue(page360.isPageDisplay());
		Assert.assertTrue(page360.openMenu());
		Assert.assertTrue(page360.hoverselectMenuItem("Gestionare servicii"));
		Assert.assertTrue(page360.selectMenuItem("Editare Opțiuni"));
		
		Assert.assertTrue(configuration.clickCBU());
		Assert.assertTrue(configuration.clickSelectaza());
		Assert.assertTrue(configuration.clickContinuă());
		
		
		
		Assert.assertTrue(summary.isPageDisplay());
		Assert.assertTrue(summary.clickContinuă());
		Assert.assertTrue(completion.isPageDisplay());
		Assert.assertTrue(completion.clickGenereazăDocumente());
		for(int i=1 ; i<12 ; i++){
			if(completion.countVizualizareLink()==3)
				break;
			else if (completion.isRegenerareDocumenteDisplay())
				Assert.assertTrue(completion.clickGenereazăDocumente());
		}
		
			Assert.assertTrue(completion.clickÎnregistreazăComanda());
			Assert.assertTrue(completion.isEsignDisplay());
			Assert.assertTrue(completion.clickFinalizare());
		 
		if(uploadedDoc.isPageDisplay())
			Assert.assertTrue(uploadedDoc.clickContinuă());
			
		String str=completion.getOrder();
		String[] arr = str.split("#", 0);
		System.out.println(arr[1]);
		ORDER_ID=arr[1];
		
		Assert.assertTrue(congrats.clickAgentDashboard());
		
		Assert.assertTrue(agentDashboard.isPageDisplay());
		params.put("<ORDER_ID>", ORDER_ID);
		
		try{
		ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_CUSTID", "OMS",params);
		CUSTID=rsData.getString("customer_id");
		System.out.println("Customer id: " +rsData.getString("customer_id"));
		}catch(Exception e){
		e.printStackTrace();
		Assert.fail("Customer id from DB");
				}
			
	
		params.put("<CUSTID>", CUSTID);
	for(int counter=0 ; counter<60 ; counter++)
	{
			try{
		ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_REASON", "OMS",params);
		System.out.println("REASON_ID: " +rsData.getString("REASON_ID"));
		if(!rsData.getString("REASON_ID").equals("COMADO"))
			objCommon.fCommonSync(3000);
		else
			break;
		
				}catch(Exception e){
		e.printStackTrace();
		Assert.fail("Failed to get Order status from DB");
				}
			
	}
	params.put("<CUSTID>", CUSTID);
	for(int counter=0 ; counter<60 ; counter++)
	{
			try{
		ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ACTION_TYPE", "OMS",params);
		System.out.println("ACTION_TYPE: " +rsData.getString("ACTION_TYPE"));
		if(!rsData.getString("ACTION_TYPE").equals("CH"))
			objCommon.fCommonSync(3000);
		else
			break;
		
				}catch(Exception e){
		e.printStackTrace();
		Assert.fail("Failed to get Order status from DB");
				}
			
	}
		Reporter.fnWriteToHtmlOutput("MTV333", "Successful", "Successful", "Pass");

		return true;
	}
	
	public boolean CBU_Port_in(String browser) throws IOException{
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		String newOrder="";
		Assert.assertTrue(MobilePortIn(browser));
		ORDER_ID=Environment.get("<ORDER_ID>");
		newOrder=Environment.get("<NEW_ORDER>");
		System.out.println("ORDER_ID: "+ORDER_ID);
		System.out.println("ORDER_ID: "+newOrder);
		objCommon.fCommonSync(29000);
		params.put("<ORDER_ID>", ORDER_ID);
		for(int counter=0 ; counter<60 ; counter++)
		{
				try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ORDER_STATUS", "OMS",params);
			System.out.println("updated status of order " +rsData.getString("STATUS"));
			if(rsData.getString("STATUS").equalsIgnoreCase("SB"))
				objCommon.fCommonSync(3000);
			else
				break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get Order status from DB");
					}
				
		}	
		params.put("<ORDER_ID>", ORDER_ID);	
		for(int counter=0 ; counter<60 ; counter++)
		{
				try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_CONTRACT_URL_CBU", "OMS",params);
			System.out.println("CONTRACT_URL: " +rsData.getString("CONTRACT_URL"));
			if(rsData.getString("CONTRACT_URL").equals(" "))
				objCommon.fCommonSync(3000);
			else
				break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get Order status from DB");
					}
				
		}
		
		params.put("<ORDER_ID>", newOrder);	
		for(int counter=0 ; counter<60 ; counter++)
		{
				try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_CONTRACT_URL_CANCELCBU", "OMS",params);
			System.out.println("CONTRACT_URL: " +rsData.getString("CONTRACT_URL"));
			if(rsData.getString("CONTRACT_URL").equals(""))
				objCommon.fCommonSync(3000);
			else
				break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get Order status from DB");
					}
				
		}
		Reporter.fnWriteToHtmlOutput("CBU Port In", "Successful", "Successful", "Pass");
		return true;
	}
	public boolean MTV335(String browser) {
	//Digital_Admin, Digital_Supervisor roles not available
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		Assert.assertTrue(ChangeMsisdn(browser));
		msisdn=Environment.get("<MSISDN>");
		newMsisdn=Environment.get("<NEWMSISDN>");
		ORDER_ID=Environment.get("<ORDER_ID>");
		System.out.println("msisdn: "+msisdn);
		System.out.println("newMsisdn: "+newMsisdn);
		System.out.println("ORDER_ID: "+ORDER_ID);
		
		params.put("<ORDER_ID>", ORDER_ID);
		
		try{
		ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_CUSTID", "OMS",params);
		CUSTID=rsData.getString("customer_id");
		System.out.println("Customer id: " +rsData.getString("customer_id"));
		}catch(Exception e){
		e.printStackTrace();
		Assert.fail("Customer id from DB");
				}
			
		params.put("<CUSTID>", CUSTID);	
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_OLDMSISDN", "OMS",params);
			System.out.println("L9_OLD_SERVICE_ID: " +rsData.getString("L9_OLD_SERVICE_ID"));
			
			}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get L9_OLD_SERVICE_ID  from DB");
					}
		
		params.put("<MSISDN>", newMsisdn);	
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_AMOUNT", "ABP",params);
			System.out.println("L9_OLD_SERVICE_ID: " +rsData.getString("AMOUNT"));
			
			}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get GET AMOUNT from DB");
					}
		params.put("<MSISDN>", msisdn);	
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_RESOURCE_STATUS", "ABP",params);
			System.out.println("L9_OLD_SERVICE_ID: " +rsData.getString("RESOURCE_STATUS"));
			
			}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get GET RESOURCE STATUS from DB");
					}
		
		
		Reporter.fnWriteToHtmlOutput("MTV335", "Successful", "Successful", "Pass");
		return true;
	}
	public boolean UpdateSubscriberRoaming(String browser) throws IOException{
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		Login login = new Login(driver, driverType, Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		Finalizare completion = new Finalizare(driver, driverType, Dictionary, Environment, Reporter);
		Page360 page360 = new Page360(driver, driverType, Dictionary, Environment, Reporter);
		SchimbareNumărPopup changeMsisdn = new SchimbareNumărPopup(driver, driverType, Dictionary, Environment, Reporter);
		String msisdn = "";
		String CUSTID = null,ORDER_ID;
		
				Assert.assertTrue(MobileProvide(browser));
				 msisdn=Environment.get("<MSISDN>");
				 ORDER_ID=Environment.get("<ORDER_ID>");
				System.out.println("msisdn: "+msisdn);
				System.out.println("ORDER_ID: "+ORDER_ID);
				if(Dictionary.get("MTV330").equals("Y"))
				{
					Assert.assertTrue(login.loginDexp(Environment.get("DEX_USER_ID"), Environment.get("DEX_PASSWORD")));
					Assert.assertTrue(login.selectMagazin("TC5CT100"));
					Assert.assertTrue(login.clickContinuă());
				}
				Assert.assertTrue(agentDashboard.isPageDisplay());
				Assert.assertTrue(agentDashboard.clickMsisdn());
				Assert.assertTrue(agentDashboard.setSearchValue(msisdn));
				Assert.assertTrue(agentDashboard.clickSearch());
				
				
				Assert.assertTrue(page360.isPageDisplay());
				Assert.assertTrue(page360.openMenu());
				Assert.assertTrue(page360.hoverselectMenuItem("Settings"));
				Assert.assertTrue(page360.selectMenuItem("Configurare setări"));
				//Assert.assertTrue(page360.isAtentieDisplay());
				//Assert.assertTrue(page360.clickDa());
				Assert.assertTrue(page360.selectAccessType("Acces International"));
				Assert.assertTrue(page360.selectRoamingSettings("Roaming Date Fara Notificari"));
				Assert.assertTrue(page360.clickContinua());
				if(Dictionary.get("MTV330").equals("Y"))
				{
					Assert.assertTrue(page360.clickElectronicSign());
				}
				Assert.assertTrue(page360.clickGenerateDocument());
				for(int i=1 ; i<8 ; i++){
					if(completion.countVizualizareLink()==1)
						break;
					else if (page360.isRegenerareDocumenteDisplay())
						Assert.assertTrue(page360.clickGenerateDocument());
				}
				if(Dictionary.get("MTV330").equals("Y"))
				{
					Assert.assertTrue(changeMsisdn.clickbtnCauta());
					Assert.assertTrue(completion.attachDoc());
					Assert.assertTrue(changeMsisdn.clickÎncarcăDocumente());
					Assert.assertTrue(page360.clickÎnregistreazăComanda());
				}
				else{
					Assert.assertTrue(page360.clickÎnregistreazăComanda());
					
					params.put("<ORDER_ID>", ORDER_ID);
					try{
					ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_CUSTID", "OMS",params);
					CUSTID=rsData.getString("customer_id");
					System.out.println("Customer id: " +rsData.getString("customer_id"));
					}catch(Exception e){
					e.printStackTrace();
					Assert.fail("Customer id from DB");
							}
					
					params.put("<CUSTID>", CUSTID);
							try{
						ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ACTION_TYPE", "OMS",params);
						System.out.println("ACTION_TYPE: " +rsData.getString("ACTION_TYPE"));
						}catch(Exception e){
						e.printStackTrace();
						Assert.fail("Failed to get Action type from DB");
								}
							
							params.put("<MSISDN>", msisdn);
							for(int counter=0 ; counter<60 ; counter++)
							{
									try{
								ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_PROMOTION_NAME_SUB","OMS",params);
								System.out.println("PROMOTION_NAME: " +rsData.getString("PROMOTION_NAME"));
								if(rsData.getString("PROMOTION_NAME").equals(" "))
									objCommon.fCommonSync(3000);
								else
									break;
								
										}catch(Exception e){
								e.printStackTrace();
								Assert.fail("Failed to get promotion name from DB");
										}
									
							}	
							
							params.put("<CUSTID>", CUSTID);
							try{
								ResultSet rsData = dbAction.getDataFromDBWithParameters("FETCH_REASON_SUBSET","OMS",params);
								System.out.println(" REASON_ID: " +rsData.getString("REASON_ID"));
								
										}catch(Exception e){
								e.printStackTrace();
								Assert.fail("Failed to get promotion name from DB");
										}
							params.put("<CUSTID>", CUSTID);	
						for(int counter=0 ; counter<60 ; counter++)
							{
									try{
								ResultSet rsData = dbAction.getDataFromDBWithParameters("FETCH_STATUS_SUBSET","OMS",params);
								System.out.println("status: " +rsData.getString("status"));
								if(rsData.getString("status").equals("DE"))
									objCommon.fCommonSync(3000);
								else
									break;
								
										}catch(Exception e){
								e.printStackTrace();
								Assert.fail("Failed to get promotion name from DB");
										}
									
							}	
				}
				Reporter.fnWriteToHtmlOutput("UpdateSubscriberRoaming", "Successful", "Successful", "Pass");			
		return true;
	}
	public boolean UpdateSubscriberCustDashboard(String browser) throws IOException{
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		Login login = new Login(driver, driverType, Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		Page360 page360 = new Page360(driver, driverType, Dictionary, Environment, Reporter);
		String msisdn = "";
		String streetPrefix = "str";
		String CUSTID=null,ORDER_ID = null;
				 Assert.assertTrue(MobileProvide(browser));
				 msisdn=Environment.get("<MSISDN>");
				 ORDER_ID=Environment.get("<ORDER_ID>");
				 System.out.println("msisdn: "+msisdn);
				 System.out.println("ORDER_ID: "+ORDER_ID);
				Assert.assertTrue(agentDashboard.isPageDisplay());
				Assert.assertTrue(agentDashboard.clickMsisdn());
				Assert.assertTrue(agentDashboard.setSearchValue(msisdn));
				Assert.assertTrue(agentDashboard.clickSearch());
				
				Assert.assertTrue(page360.isPageDisplay());
				Assert.assertTrue(page360.openMenu());
				Assert.assertTrue(page360.hoverselectMenuItem("Settings"));
				Assert.assertTrue(page360.selectMenuItem("Configurare setări"));
				//Assert.assertTrue(page360.isAtentieDisplay());
				//Assert.assertTrue(page360.clickDa());
				Assert.assertTrue(page360.selectAccessType("Acces International"));
				Assert.assertTrue(page360.selectRoamingSettings("Roaming Date Limita 50Eur"));
				Assert.assertTrue(page360.clickÎnregistreazăComanda());
				params.put("<ORDER_ID>", ORDER_ID);
				try{
				ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_CUSTID", "OMS",params);
				CUSTID=rsData.getString("customer_id");
				System.out.println("Customer id: " +rsData.getString("customer_id"));
				}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Customer id from DB");
						}
				
				params.put("<CUSTID>", CUSTID);
						try{
					ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ACTION_TYPE", "OMS",params);
					System.out.println("ACTION_TYPE: " +rsData.getString("ACTION_TYPE"));
					}catch(Exception e){
					e.printStackTrace();
					Assert.fail("Failed to get Action type from DB");
							}
						params.put("<MSISDN>", msisdn);
						for(int counter=0 ; counter<60 ; counter++)
						{
								try{
							ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_PROMOTION_NAME_SUB","OMS",params);
							System.out.println("PROMOTION_NAME: " +rsData.getString("PROMOTION_NAME"));
							if(rsData.getString("PROMOTION_NAME").equals(" "))
								objCommon.fCommonSync(3000);
							else
								break;
							
									}catch(Exception e){
							e.printStackTrace();
							Assert.fail("Failed to get promotion name from DB");
									}
								
						}	
						
						params.put("<CUSTID>", CUSTID);
						try{
							ResultSet rsData = dbAction.getDataFromDBWithParameters("FETCH_REASON_SUBSET","OMS",params);
							System.out.println(" REASON_ID: " +rsData.getString("REASON_ID"));
							
									}catch(Exception e){
							e.printStackTrace();
							Assert.fail("Failed to get promotion name from DB");
									}
						params.put("<CUSTID>", CUSTID);	
					for(int counter=0 ; counter<60 ; counter++)
						{
								try{
							ResultSet rsData = dbAction.getDataFromDBWithParameters("FETCH_STATUS_SUBSET","OMS",params);
							System.out.println("status: " +rsData.getString("status"));
							if(rsData.getString("status").equals("DE")||rsData.getString("status").equals("NO"))
								objCommon.fCommonSync(3000);
							else
								break;
							
									}catch(Exception e){
							e.printStackTrace();
							Assert.fail("Failed to get promotion name from DB");
									}
								
						}	
				Reporter.fnWriteToHtmlOutput("UpdateSubscriberCustDashboard", "Successful", "Successful", "Pass");
		return true;
	}
	
	public boolean CancelSplitDelivery(String browser) throws IOException
	{
		Assert.assertTrue(MobilePortIn(browser));
		Reporter.fnWriteToHtmlOutput("CancelSplitDelivery", "Successful", "Successful", "Pass");
		return true;
	}
	public boolean MTV321(String browser) throws IOException
	{
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		Assert.assertTrue(MobileProvide(browser));
		 ORDER_ID=Environment.get("<ORDER_ID>");
		 System.out.println("msisdn: "+ORDER_ID);
		params.put("<ORDER_ID>",ORDER_ID);
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_CUSTID", "OMS",params);
			CUSTID=rsData.getString("customer_id");
			System.out.println("Printing Customer Id: "+CUSTID);

		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get count");
		}
		params.put("<CUSTID>",CUSTID);
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_QA_STATUS", "OMS",params);
			System.out.println("Printing Customer Id: "+rsData.getString("OA_STATUS"));
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get count");
		}
		Reporter.fnWriteToHtmlOutput("MTV321", "Successful", "Successful", "Pass");
		return true;
	}
	public boolean MTV330(String browser) throws IOException{
		Assert.assertTrue(UpdateSubscriberRoaming(browser));
		Reporter.fnWriteToHtmlOutput("MTV330", "Successful", "Successful", "Pass");
		return true;
	}
	public boolean ChangeMsisdnGoldno(String browser) throws IOException{
		
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		Login login = new Login(driver, driverType, Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		Page360 page360 = new Page360(driver, driverType, Dictionary, Environment, Reporter);
		SchimbareNumărPopup changeMsisdn = new SchimbareNumărPopup(driver, driverType, Dictionary, Environment, Reporter);
		Header header = new Header(driver, driverType, Dictionary, Environment, Reporter);
		String Count="";
		String newMsisdn="";
		String msisdn="";
		String validatetext="";
		
		 Assert.assertTrue(MobileProvide(browser));
		 msisdn=Environment.get("<MSISDN>");
		 ORDER_ID=Environment.get("<ORDER_ID>");
		 System.out.println("msisdn: "+msisdn);
		 System.out.println("msisdn: "+ORDER_ID);
		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickMsisdn());
		Assert.assertTrue(agentDashboard.setSearchValue(msisdn));
		Assert.assertTrue(agentDashboard.clickSearch());
		
		Assert.assertTrue(page360.isPageDisplay());
		Assert.assertTrue(page360.openMenu());
		Assert.assertTrue(page360.hoverselectMenuItem("Settings"));
		Assert.assertTrue(page360.hoverselectMenuItem("Change Phone Number"));
		Assert.assertTrue(page360.selectMenuItem("Standard/Special Number"));
		
		Assert.assertTrue(changeMsisdn.isPageDisplay());
		Assert.assertTrue(changeMsisdn.clicktabNumberSpecial());
		Assert.assertTrue(changeMsisdn.selectȘtergeByIndex(2));
		Assert.assertTrue(changeMsisdn.clickbtnCauta());
		validatetext=changeMsisdn.getTextNiciun();
		/*if (validatetext.startsWith("Niciun număr"))
		{
			Assert.assertTrue(changeMsisdn.selectȘtergeByIndex("3"));
			Assert.assertTrue(changeMsisdn.clickbtnCauta());
		}*/
		/*
		for(int i=3;i<5;i++){
			if (validatetext.equals("Niciun număr disponibil. Încearcă alte variante")||validatetext.equals(""))
			{
				Assert.assertTrue(changeMsisdn.selectȘtergeByIndex(i));
				Assert.assertTrue(changeMsisdn.clickbtnCauta());
			}	
				else
					break;
				
		}*/
		newMsisdn = changeMsisdn.getNewNumber();
		Assert.assertTrue(changeMsisdn.clickRezervăNumăr());
		Assert.assertTrue(changeMsisdn.clickContinue());
				
		Assert.assertTrue(page360.isPageDisplay());
		Assert.assertTrue(header.clickVodafoneLogo());
		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickMsisdn());
		/*objCommon.fCommonSync(40000);
		for(int counter=0 ; counter<60 ; counter++){
			Assert.assertTrue(agentDashboard.setSearchValue(newMsisdn));
			Assert.assertTrue(agentDashboard.clickSearch());
			if(agentDashboard.isNoResultDisplay())
				objCommon.fCommonSync(3000);
			else 
				break;
		}
		*/
		params.put("<ORDER_ID>",ORDER_ID);
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_CUSTID", "OMS",params);
			CUSTID=rsData.getString("customer_id");
			System.out.println("Printing Customer Id: "+CUSTID);
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get count");
		}
		
		params.put("<CUSTID>",CUSTID);
		try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_RESOURCE_COUNT", "OMS",params);
			Count=rsData.getString("COUNT(*)");
			System.out.println("Printing Count: "+Count);
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get count");
		}
		params.put("<CUSTID>",CUSTID);
		
		for(int counter=0 ; counter<60 ; counter++)
		{
			try{
				ResultSet rsData = dbAction.getDataFromDBWithParameters("CHECK_TBAP_CHARGE_INFO", "OMS",params);
				Count=rsData.getString("COUNT(*)");
				System.out.println("Printing Count: "+Count);
				if(rsData.getString("COUNT(*)").equals("0"))
					objCommon.fCommonSync(3000);
				else
					break;
				
			}catch(Exception e){
				e.printStackTrace();
				Assert.fail("Failed to get count");
			}
			
			
			
		}	
		
		Reporter.fnWriteToHtmlOutput("ChangeMsisdnGoldno", "Successful", "Successful", "Pass");
		return true;
	}
	
	public boolean DispatchSim(String browser) throws IOException
	{
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		String sim=null,PARENT_ORDER="";
		Assert.assertTrue(MTV331(browser));
		msisdn=Environment.get("<MSISDN>");
		sim=Environment.get("<SIM>");
		System.out.println("msisdn: "+msisdn);
		System.out.println("newMsisdn: "+sim);
		
		params.put("<MSISDN>", msisdn);
		try{
		ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_PARENT_UNITID", "OMS",params);
		PARENT_ORDER=rsData.getString("PARENT_ORDER_UNIT");
		System.out.println("Parent order unit id: " +rsData.getString("PARENT_ORDER_UNIT"));
		}catch(Exception e){
		e.printStackTrace();
		Assert.fail("Failed to get Order status from DB");
				}
		
		params.put("<SIM>", sim);
		params.put("<PARENT_ORDER>", PARENT_ORDER);
		params.put("<MSISDN>", msisdn);
	for(int counter=0 ; counter<60 ; counter++)
	{
		try{
	ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_STATUS_331", "OMS",params);
	System.out.println("STATUS: " +rsData.getString("status"));
	if(rsData.getString("STATUS").equals(""))
		objCommon.fCommonSync(3000);
	else
		break;
	
			}catch(Exception e){
	e.printStackTrace();
	Assert.fail("Failed to get Order status from DB");
			}
		
}
		return true;
	}
	public boolean ChangeMsisdnPortIn(String browser) throws IOException
	{
		Assert.assertTrue(MobilePortIn(browser));
		return true;
	}
	public boolean NewCBUCust(String browser) throws IOException
	{
		Assert.assertTrue(MobileProvide(browser));
		return true;
	}
	public boolean EditConfigurationAdditionalPromotion(String browser)
	{
		
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		Login login = new Login(driver, driverType, Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		Configurează configuration = new Configurează(driver, driverType, Dictionary, Environment, Reporter);
		Detalii details = new Detalii(driver, driverType, Dictionary, Environment, Reporter);
		Curier equipment = new Curier(driver, driverType, Dictionary, Environment, Reporter);
		Sumar summary = new Sumar(driver, driverType, Dictionary, Environment, Reporter);
		Finalizare completion = new Finalizare(driver, driverType, Dictionary, Environment, Reporter);
		Felicitari congrats = new Felicitari(driver, driverType, Dictionary, Environment, Reporter);
		Page360 page360 = new Page360(driver, driverType, Dictionary, Environment, Reporter);
		Configureaddon config=new Configureaddon(driver, driverType, Dictionary, Environment, Reporter);
		DocumenteÎncărcatePopup uploadedDoc = new DocumenteÎncărcatePopup(driver, driverType, Dictionary, Environment, Reporter);
		String cnp="";
		Assert.assertTrue(FixedProvide(browser));
		cnp=Environment.get("<CNP>");
		Assert.assertTrue(agentDashboard.clickCnp());
		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickCnp());
		Assert.assertTrue(agentDashboard.setSearchValue(cnp));
		Assert.assertTrue(agentDashboard.clickSearch());
		
		Assert.assertTrue(page360.isPageDisplay());
		Assert.assertTrue(page360.openMenu());
		Assert.assertTrue(page360.hoverselectMenuItem("Gestionare servicii"));
		Assert.assertTrue(page360.selectMenuItem("Editare Opțiuni și Echipamente"));
		configuration.getPromotions();
		Assert.assertTrue(configuration.selectAddonItem("Optiune IP FIX"));
		Assert.assertTrue(configuration.clickReturneaza());
		Assert.assertTrue(configuration.selectList("MOTIV RETUR","Returned"));
		Assert.assertTrue(configuration.selectList("TIP RETUR","Missing"));
		Assert.assertTrue(configuration.selectList("METODĂ RETUR","Manual in Store"));
		Assert.assertTrue(configuration.clicktogglebtn());
		Assert.assertTrue(configuration.selectAddonItem("Optiune Smart Protect 1 Licenta"));
		Assert.assertTrue(configuration.clickStrge());
		Assert.assertTrue(configuration.clickElimina());
		Assert.assertTrue(configuration.clickMiniCart());
		Assert.assertTrue(configuration.clickContinuă());
		Assert.assertTrue(details.clickContinuă());
		Assert.assertTrue(summary.isPageDisplay());
		Assert.assertTrue(summary.clickPersonalDetails());
		Assert.assertTrue(summary.ClicklinkTooltip());
		Assert.assertTrue(summary.clickTrimitebtn());
		
		
		return true;
	}
	
	public boolean TV_And_Internet_Offer_Bundle(String browser){
		System.out.println("'" + Dictionary.get("TEST_NAME") + "' test started on " + browser);
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		Login login = new Login(driver, driverType, Dictionary, Environment, Reporter);
		AdresăDeInstalarePopup address = new AdresăDeInstalarePopup(driver, driverType, Dictionary, Environment, Reporter);
		AgentDashboard agentDashboard = new AgentDashboard(driver, driverType, Dictionary, Environment, Reporter);
		cnpGenerator cnpGenerator = new cnpGenerator(driver, driverType, Dictionary, Environment, Reporter);
		Configurează configuration = new Configurează(driver, driverType, Dictionary, Environment, Reporter);
		Detalii details = new Detalii(driver, driverType, Dictionary, Environment, Reporter);
		Curier equipment = new Curier(driver, driverType, Dictionary, Environment, Reporter);
		Sumar summary = new Sumar(driver, driverType, Dictionary, Environment, Reporter);
		Finalizare completion = new Finalizare(driver, driverType, Dictionary, Environment, Reporter);
		Felicitari congrats = new Felicitari(driver, driverType, Dictionary, Environment, Reporter);
		Configureaddon config=new Configureaddon(driver, driverType, Dictionary, Environment, Reporter);
		DocumenteÎncărcatePopup uploadedDoc = new DocumenteÎncărcatePopup(driver, driverType, Dictionary, Environment, Reporter);
		Page360 page360 = new Page360(driver, driverType, Dictionary, Environment, Reporter);

		
		String cnp = "";
		String prenume = "Auto";
		String nume = RandomStringUtils.randomAlphabetic(5);
		String LocalitatePrefix = "cit";
		String streetPrefix = "str";
		String mail = RandomStringUtils.randomAlphabetic(5) + "@google.com";
		String number = Environment.get("CONTACT_NUM_PREFIX") + RandomStringUtils.randomNumeric(8);
		int i=1;
		int counter = 0;
		
		
		
		//1. Login to Dex 
		
		Assert.assertTrue(login.loginDexp(Environment.get("DEX_USER_ID"), Environment.get("DEX_PASSWORD")));
		Assert.assertTrue(login.selectMagazin(Dictionary.get("MAGAZIN")));
		Assert.assertTrue(login.clickContinuă());

		// 2. Search invalid CNP and Initiate a Derby Fixed provide
		
		cnp = cnpGenerator.generateCnp();

		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickCnp());
		Assert.assertTrue(agentDashboard.setSearchValue(cnp));
		Assert.assertTrue(agentDashboard.clickSearch());
		if(agentDashboard.isInvalidCNPDisplay()==false)
		{
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouFix());
		}
		else{
			Assert.assertTrue(agentDashboard.handleInvalidCNP());
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouFix());	
		}
				
		Assert.assertTrue(address.isPageDisplay());
		Assert.assertTrue(address.selectJudetByIndex("1"));
		Assert.assertTrue(address.setLocalitate(LocalitatePrefix));
		Assert.assertTrue(address.selectLocalitateByIndex("1"));
		Assert.assertTrue(address.selectTipStradăByIndex("1"));
		Assert.assertTrue(address.setNumeStradă(streetPrefix));
		Assert.assertTrue(address.selectNumeStradăByIndex("1"));
		Assert.assertTrue(address.selectNumărStradăByIndex("1"));
		Assert.assertTrue(address.selectBlocByIndex("1"));
		Assert.assertTrue(address.selectScarăByIndex("1"));
		Assert.assertTrue(address.selectApartamentByIndex("1"));
		Assert.assertTrue(address.selectEtajByIndex("1"));
		Assert.assertTrue(address.clickContinuă());

		
		// Reach configurati+H5on page

		Assert.assertTrue(configuration.isPageDisplay());
		
		// Select a TV + Internet Bundle/Offer and open shopping cart

		Assert.assertTrue(configuration.selectPackageByIndex("1"));
		
		// Validate order is created and validate that Add On 
		// in same group are displayed in groups in shopping cart also.

		
		Assert.assertTrue(configuration.validateBunbleNameAndCartEqual());
				
		
		// Add Addons
		
		Assert.assertTrue(configuration.clickOptiuneHBOGO());
		Assert.assertTrue(configuration.clickOptiuneIP());
		Assert.assertTrue(configuration.clickOptiuneIPFIX());
		
		Assert.assertTrue(configuration.clickOptiuneTarifVerde());
	//	Assert.assertTrue(configuration.clickOptiuneTelecomanda ());
	//	Assert.assertTrue(configuration.clickVoyoTV());
		
		
		// Check if Addons shown in packages 
		Assert.assertTrue(configuration.validateAddOnInPackage());
		
		// Check if Addons shown in cart
		Assert.assertTrue(configuration.validateAddOnCart());
		
		
		//Validate on DEX configuration screen below 4 accordion should be displayed - 
		// 1. Add On - 1 Accordian for TV and 1 Accordian for Internet
		
		Assert.assertTrue(configuration.validateAccordionAddon());

		//2. Equipment - 1 Accordian for TV and 1 Accordian for Internet	

		Assert.assertTrue(configuration.validateAccordionEquipment());
		
		//Click on - sign to collapse the TV Addon Section
		
		Assert.assertTrue(configuration.clickCollapseTvAddon());

		//Validate that complete TV Addon Section collapses
		
	//	Assert.assertTrue(configuration.validateTvAddonCollapses());

		//Validate the collapsed bar has summary of the selected add ons.

		Assert.assertTrue(configuration.validateAddOnInPackage());
		
		//Validate + Sign is displayed by the side of collapsed accordion

		Assert.assertTrue(configuration.validatePlusTV());
		
		//Click on + Sign and validate TV Addon Section expands again
		
		Assert.assertTrue(configuration.clickExpandsTvAddon());
		Assert.assertTrue(configuration.validateTVAddonShow());

		//Click on + sign to add items in TV Equipment section
		
		Assert.assertTrue(configuration.clickToAddTVEquipment());
		
		//Click on  sign to add items in TV Equipment section
		
		Assert.assertTrue(configuration.clickToSubTVEquipment());

		//**************************************************************************
		
		//Click on - sign to collapse the Internet Addon Section
		
				Assert.assertTrue(configuration.clickCollapseInternetAddon());

				//Validate that complete Internet Addon Section collapses
				
				Assert.assertTrue(configuration.validateInternetAddonCollapses());

				//Validate the collapsed bar of Internet has summary of the selected add ons.

				Assert.assertTrue(configuration.validateAddOnInPackage());
				
				//Validate + Sign is displayed Internet by the side of collapsed accordion

				Assert.assertTrue(configuration.validatePlusInternet());
				
				//Click on + Sign and validate Internet Addon Section expands again
				
				Assert.assertTrue(configuration.clickExpandsInternetAddon());
				Assert.assertTrue(configuration.validateInternetAddonShow());

				//Click on - sign to add items in Internet Equipment section
				
				Assert.assertTrue(configuration.clickToSubInternetEquipment());	
				
				//Click on + sign to add items in Internet Equipment section
				
				Assert.assertTrue(configuration.clickToAddInternetEquipment());
				
				//Submit Order

				Assert.assertTrue(configuration.clickContinuă());




Assert.assertTrue(details.isPageDisplay());
		Assert.assertTrue(details.setPrenume(prenume));
		Assert.assertTrue(details.setNume(nume));
		Assert.assertTrue(details.setEmail(mail));
		Assert.assertTrue(details.setTelefonContact(number));
		Assert.assertTrue(details.clickNuTelefonAlternativ());
		Assert.assertTrue(details.clickContinuă());
		
		Assert.assertTrue(equipment.clickContinuă());
		
		Assert.assertTrue(summary.isPageDisplay());
		Assert.assertTrue(summary.toggleProfilareVodafoneDaToateOn());
		Assert.assertTrue(summary.toggleProfilarePartnerDaToateOn());
		Assert.assertTrue(summary.clickContinuă());
		
		Assert.assertTrue(completion.isPageDisplay());
		Assert.assertTrue(completion.clickClientulEste());
		Assert.assertTrue(completion.clickGenereazăDocumente());
		for(i=1 ; i<8 ; i++){
			if(completion.countVizualizareLink()==2)
				break;
			else if (completion.isRegenerareDocumenteDisplay())
				Assert.assertTrue(completion.clickGenereazăDocumente());
		}
		Assert.assertTrue(completion.isPageDisplay());
		
		Assert.assertTrue(completion.clickÎnregistreazăComanda());
		
		Assert.assertTrue(completion.clickFinalizare());
		
		Assert.assertTrue(uploadedDoc.clickContinuă());
		
		Assert.assertTrue(congrats.isPageDisplay());
			String str1=completion.getOrder();
			String[] arr1 = str1.split("#", 0);
			System.out.println(arr1[1]);
			ORDER_ID=arr1[1];
		Assert.assertTrue(congrats.clickAgentDashboard());
		
		Assert.assertTrue(agentDashboard.isPageDisplay());
		
		objCommon.fCommonSync(29000);
		
		params.put("<ORDER_ID>", ORDER_ID);
		for(counter=0 ; counter<60 ; counter++)
		{
				try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ORDER_STATUS", "OMS",params);
			System.out.println("updated status of order " +rsData.getString("STATUS"));
			if(rsData.getString("STATUS").equalsIgnoreCase("SB"))
				objCommon.fCommonSync(3000);
			else
				break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get Order status from DB");
					}
				
		}
		Environment.put("<CNP>",cnp);
		Reporter.fnWriteToHtmlOutput("Create Fix provide", "Successful", "Successful", "Pass");
		return true;
				
				
				
				
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
				
		
		
		
		
		
/*		Assert.assertTrue(configuration.Internetfixspecial());
		Assert.assertTrue(configuration.clickContinuă());

		Assert.assertTrue(details.isPageDisplay());
		Assert.assertTrue(details.setPrenume(prenume));
		Assert.assertTrue(details.setNume(nume));
		Assert.assertTrue(details.setEmail(mail));
		Assert.assertTrue(details.setTelefonContact(number));
		Assert.assertTrue(details.clickNuTelefonAlternativ());
		Assert.assertTrue(details.clickContinuă());
		
		Assert.assertTrue(equipment.isPageDisplay());
		Assert.assertTrue(equipment.clickContinuă());
		
		
		Assert.assertTrue(summary.isPageDisplay());
		Assert.assertTrue(summary.toggleProfilareVodafoneDaToateOn());
		Assert.assertTrue(summary.toggleProfilarePartnerDaToateOn());
		Assert.assertTrue(summary.clickContinuă());
		
		Assert.assertTrue(completion.isPageDisplay());
		Assert.assertTrue(completion.clickClientulEste());
		Assert.assertTrue(completion.clickGenereazăDocumente());
		for(i=1 ; i<8 ; i++){
			if(completion.countVizualizareLink()==2)
				break;
			else if (completion.isRegenerareDocumenteDisplay())
				Assert.assertTrue(completion.clickGenereazăDocumente());
		}
		
		Assert.assertTrue(completion.clickÎnregistreazăComanda());
		Assert.assertTrue(completion.clickFinalizare());
		
		if(uploadedDoc.isPageDisplay())
			Assert.assertTrue(uploadedDoc.clickContinuă());
		

		Assert.assertTrue(congrats.isPageDisplay());
		String str1=completion.getOrder();
		String[] arr1 = str1.split("#", 0);
		System.out.println(arr1[1]);
		ORDER_ID=arr1[1];
		Assert.assertTrue(congrats.clickAgentDashboard());		


	// 3. Start  INT FIX flow with address B and Submit the order
		
		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickCnp());
		Assert.assertTrue(agentDashboard.setSearchValue(cnp));
		Assert.assertTrue(agentDashboard.clickSearch());
		if(agentDashboard.isInvalidCNPDisplay()==false)
		{
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouFix());
		}
		else{
			Assert.assertTrue(agentDashboard.handleInvalidCNP());
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouFix());	
		}
		
		Assert.assertTrue(page360.isPageDisplay());
		Assert.assertTrue(page360.clickServiceIFixe());
		
		Assert.assertTrue(address.isPageDisplay());
		Assert.assertTrue(address.selectJudetByIndex("2"));
		Assert.assertTrue(address.setLocalitate(LocalitatePrefix));
		Assert.assertTrue(address.selectLocalitateByIndex("2"));
		Assert.assertTrue(address.selectTipStradăByIndex("2"));
		Assert.assertTrue(address.setNumeStradă(streetPrefix));
		Assert.assertTrue(address.selectNumeStradăByIndex("2"));
		Assert.assertTrue(address.selectNumărStradăByIndex("2"));
		Assert.assertTrue(address.selectBlocByIndex("2"));
		Assert.assertTrue(address.selectScarăByIndex("2"));
		Assert.assertTrue(address.selectApartamentByIndex("2"));
		Assert.assertTrue(address.selectEtajByIndex("2"));
		Assert.assertTrue(address.clickContinuă());
		
		Assert.assertTrue(configuration.selectTvAndNetBudle());

		Assert.assertTrue(configuration.clickContinuă());
		
		Assert.assertTrue(equipment.isPageDisplay());
		Assert.assertTrue(equipment.clickContinuă());
		
		Assert.assertTrue(details.clickContinuă());

		Assert.assertTrue(equipment.isPageDisplay());
		Assert.assertTrue(equipment.clickContinuă());
		
		Assert.assertTrue(summary.isPageDisplay());
		Assert.assertTrue(summary.toggleProfilareVodafoneDaToateOn());
		Assert.assertTrue(summary.toggleProfilarePartnerDaToateOn());
		Assert.assertTrue(summary.clickContinuă());
		
		Assert.assertTrue(completion.isPageDisplay());
		Assert.assertTrue(completion.clickGenereazăDocumente());
		for(i=1 ; i<8 ; i++){
			if(completion.countVizualizareLink()==2)
				break;
			else if (completion.isRegenerareDocumenteDisplay())
				Assert.assertTrue(completion.clickGenereazăDocumente());
		}
		
		Assert.assertTrue(completion.clickÎnregistreazăComanda());
		Assert.assertTrue(completion.clickFinalizare());
		
		if(uploadedDoc.isPageDisplay())
			Assert.assertTrue(uploadedDoc.clickContinuă());
		
		Assert.assertTrue(congrats.isPageDisplay());
		Assert.assertTrue(congrats.clickAgentDashboard());	
		
		// 4. Search for this customer and open 360 dashboard for it 
		
		Assert.assertTrue(agentDashboard.isPageDisplay());
		Assert.assertTrue(agentDashboard.clickCnp());
		Assert.assertTrue(agentDashboard.setSearchValue(cnp));
		Assert.assertTrue(agentDashboard.clickSearch());
		if(agentDashboard.isInvalidCNPDisplay()==false)
		{
			Assert.assertTrue(agentDashboard.clickSearch());
			Assert.assertTrue(agentDashboard.clickClientNouFix());
		}
		else{
			Assert.assertTrue(agentDashboard.handleInvalidCNP());
			Assert.assertTrue(agentDashboard.clickSearch());
			
		// 5. Verify one tab for TV will be presented
			
		Assert.assertTrue(page360.countSumBundle());
		
		// 6. Verify address A will be presented for first opened tab (TV)
		
		Assert.assertTrue(page360.checkAddress());
		
		// 7. Verify Balance widget UI will be enhanced according to the new layout (according SB) - verify link working
		
		Assert.assertTrue(page360.clickPLĂTESTE());
		
		
		Assert.assertTrue(configuration.selectPackageByIndex("1"));
		Assert.assertTrue(configuration.selectAddonItem("Optiune Smart Protect 1 Licenta"));
		Assert.assertTrue(config.isConfigureAddonDispalyed());
		Assert.assertTrue(config.setNumarTelefon());
		Assert.assertTrue(config.setEmail());
		Assert.assertTrue(config.ClickSelecteaza());
		Assert.assertTrue(configuration.selectAddonItem("Optiune HBO"));
		Assert.assertTrue(configuration.clickMiniCart());
		configuration.getCartDetails_Service();
		String str=configuration.getOrderno();
		String[] arr = str.split("#", 0);
		System.out.println(arr[1]);
		ORDER_ID=arr[1];
		Assert.assertTrue(configuration.selectMandatoriesAddon());
		Assert.assertTrue(configuration.clickContinuă());

		Assert.assertTrue(details.isPageDisplay());
		Assert.assertTrue(details.setPrenume(prenume));
		Assert.assertTrue(details.setNume(nume));
		Assert.assertTrue(details.setEmail(mail));
		Assert.assertTrue(details.setTelefonContact(number));
		Assert.assertTrue(details.clickNuTelefonAlternativ());
		Assert.assertTrue(details.clickContinuă());
		
		Assert.assertTrue(equipment.clickContinuă());
		
		Assert.assertTrue(summary.isPageDisplay());
		Assert.assertTrue(summary.toggleProfilareVodafoneDaToateOn());
		Assert.assertTrue(summary.toggleProfilarePartnerDaToateOn());
		Assert.assertTrue(summary.clickContinuă());
		
		Assert.assertTrue(completion.isPageDisplay());
		Assert.assertTrue(completion.clickClientulEste());
		Assert.assertTrue(completion.clickGenereazăDocumente());
		for(i=1 ; i<8 ; i++){
			if(completion.countVizualizareLink()==2)
				break;
			else if (completion.isRegenerareDocumenteDisplay())
				Assert.assertTrue(completion.clickGenereazăDocumente());
		}
		Assert.assertTrue(completion.isPageDisplay());
		
		Assert.assertTrue(completion.clickÎnregistreazăComanda());
		
		Assert.assertTrue(completion.clickFinalizare());
		
		Assert.assertTrue(uploadedDoc.clickContinuă());
		
		Assert.assertTrue(congrats.isPageDisplay());
			String str1=completion.getOrder();
			String[] arr1 = str1.split("#", 0);
			System.out.println(arr1[1]);
			ORDER_ID=arr1[1];
		Assert.assertTrue(congrats.clickAgentDashboard());
		
		Assert.assertTrue(agentDashboard.isPageDisplay());
		
		objCommon.fCommonSync(29000);
		
		params.put("<ORDER_ID>", ORDER_ID);
		for(counter=0 ; counter<60 ; counter++)
		{
				try{
			ResultSet rsData = dbAction.getDataFromDBWithParameters("GET_ORDER_STATUS", "OMS",params);
			System.out.println("updated status of order " +rsData.getString("STATUS"));
			if(rsData.getString("STATUS").equalsIgnoreCase("SB"))
				objCommon.fCommonSync(3000);
			else
				break;
			
					}catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed to get Order status from DB");
					}
				
		}*/
/*		Environment.put("<CNP>",cnp);
		Reporter.fnWriteToHtmlOutput("Create Fix provide", "Successful", "Successful", "Pass");
		return true;*/
//	}
	//	return false;
}

}


