package vfro.framework;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.PreemptiveAuthSpec;
import io.restassured.specification.RequestSpecification;
import vfro.regression.MainDriver;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.relevantcodes.extentreports.LogStatus;


public class RestAssuredAPI {

	//Instance Variables
	private static Reporting Reporter;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private WebDriver driver;
	private String driverType;
	static int responseStatusCode;
	static String responseBody;
	static String reqBody;
	static String response;

	//Constructor
	public RestAssuredAPI(HashMap<String, String> GDictionary, HashMap<String,String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		Dictionary = GDictionary;
		Environment = GEnvironment;


	}

	//*****************************************************************************************
	//* Name            : getCookie
	//* Description     : Get Cookie
	//* Author          : Soma Dash
	//* Updated Date    : August, 2019
	//* Input Params    : 
	//* Return Values   : Cookie
	//*****************************************************************************************
	public String getCookie()
	{

		RequestSpecification httpRequest = RestAssured.given();
		RestAssured.baseURI = Environment.get("URI");
		//httpRequest.auth().basic(Environment.get("USER_ID"), Environment.get("PASSWORD"));
		httpRequest.contentType("application/json");
		httpRequest.header("Authorization","Basic NTE1MDQ6dW5peDEx");
		Response response = httpRequest.request(Method.GET, "/api/user");

		responseStatusCode = response.getStatusCode();
		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Pass");
			System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
			String AuthToken = response.getCookie("TOKEN");
			return AuthToken;
		}
		else
		{
			String AuthToken = response.getCookie("TOKEN");
			if(AuthToken==null)
				Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
			System.out.println("Response Code is "+responseStatusCode);
			return null;
		}

	}

	//*****************************************************************************************
	//* Name            : generateOTP
	//* Description     : Generate OTP in Secure ID mode
	//* Author          : Soma Dash
	//* Updated Date    : August, 2019
	//* Input Params    : 
	//* Return Values   : OTP
	//*****************************************************************************************
	public String generateOTPusingAPI(String Ban, String ptn, String IsBackmode,String user, String Password)
	{
		String generateOTP_URI=Environment.get("GENERATE_OTP_URI");
		Dictionary.put("ACCOUNT_NO",Ban);
		Dictionary.put("MDN",ptn);
		Dictionary.put("IS_BACK_MODE",IsBackmode);
		String templatePath =  System.getProperty("user.dir") + "/Templates" + "/GenerateOTP.json";
		reqBody = replaceTemplateWithValues(templatePath);

		String authString = user + ":" + Password;
		byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
		String encodedString = new String(base64Encoded);
		RestAssured.baseURI = Environment.get("URI");
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.contentType("application/json");
		httpRequest.header("Authorization", "Basic " + encodedString);
		Response response = httpRequest.request(Method.GET, "/api/user");

		responseStatusCode = response.getStatusCode();
		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
			System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
		}
		else
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
			return null;
		}
		httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

		httpRequest.body(reqBody);
		Reporter.fnWriteToHtmlOutput("generateOTPusingAPI", "URI", "URI: "+generateOTP_URI, "Done");
		Reporter.fnWriteToHtmlOutput("generateOTPusingAPI", "Request sent", "Request sent: "+reqBody, "Done");
		Response response1 = httpRequest.request(Method.POST, generateOTP_URI);

		responseStatusCode = response1.getStatusCode();
		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("generateOTPusingAPI","Execute generateOTP API", "API executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			JsonPath jp = new JsonPath(response1.getBody().asString());
			String otp = jp.getString("otp");
			String mode = jp.getString("mode");
			if(mode.contains("B")){
				Reporter.fnWriteToHtmlOutput("generateOTPusingAPI","Verify Mode", "mode is=" + mode + " is Backupmode","Fail");
				System.out.println("OTP is "+ otp);
				return otp;
			}
			Reporter.fnWriteToHtmlOutput("generateOTPusingAPI","generate OTP", "OTP generated successfully - " + "otp is "+otp + "and mode=" + mode + " is securemode","Done");
			return otp;

		}
		else
		{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("generateOTPusingAPI","Execute generateOTP API", "API failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done"); // Failure will not impact TC being executed
			return null;
		}

	}


	//*****************************************************************************************
	//* Name            : replaceTemplateWithValues
	//* Description     : Replace template with values
	//* Author          : Soma Dash
	//* Updated Date    : August, 2019
	//* Input Params    : 
	//* Return Values   : 
	//*****************************************************************************************    
	public String replaceTemplateWithValues(String templatePath) {


		StringBuffer stBuff;
		String res = "";
		try {
			File f = new File(templatePath);
			if (!f.exists()) {
				throw new Exception();
			} else {
				BufferedReader br = new BufferedReader(new FileReader(f));

				String st;
				stBuff = new StringBuffer();
				while ((st = br.readLine()) != null) {
					stBuff = stBuff.append(st);
				}

				res = stBuff.toString();
				String[] keyArr = Dictionary.keySet().toArray(new String[Dictionary.size()]);

				for (String key : keyArr) {
					if (res.contains(key)) {
						res = res.replace("{" + key + "}", Dictionary.get(key));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return res;
		}
	}   


	public String createSingleLineBANUsingMS(String firstName, String lastName, String accountSubType ,String pin, String securityQuestion, String securityAnswer, String itemID, String deviceInfoSource, String sim, String imei, String market, String pricePlan, String user, String Password)
	{
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		CommonFunctions objCommon = new CommonFunctions(driver, driverType, Environment, Reporter);
		HashMap<String, String> params = new HashMap<>();
		Fillo fillo = new Fillo();
		com.codoid.products.fillo.Connection conn;
		Recordset rds;
		ResultSet rs=null;
		List<String> npa=new ArrayList<String>();
		List<String> nxx=new ArrayList<String>();
		int i=0;
		Response response1=null;
		boolean npaFoundInMarket=false;
		boolean npaFoundInAlternateMarket=false;
		boolean tryAltyernatePriceSheetValues=false;
		String priceSheetCode=null;
		String offerCode=null;
		String pkNpaNxx = "NPA_NXX_MARKET";
		String pkNpaNxxCount = "COUNT_MARKET_NPA_NXX";
		String accountType=null;
		String tempMarket=null;
		boolean blackListNumberMatches=false;
		
		if(accountSubType.isEmpty())
			accountSubType="1";
		
		rs=dbAction.getDataFromDB("BRAND_INFO");
		
		try {
			accountType=rs.getString("ACCOUNT_TYPE").trim();
		} catch (SQLException e2) {
			e2.printStackTrace();
			accountType="I";
		}
		
		if(accountType.equals("I")==false){
			rs=dbAction.getDataFromDB("SECURITY_QUESTION");		
			try {
				do{
					if(rs.getString("ACCOUNT_TYPE").trim().equals(accountType)){
						securityQuestion=rs.getString("QUESTION_CODE").trim();
						break;
					}
				}while(rs.next());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		try{
			conn=fillo.getConnection(System.getProperty("user.dir")+"/data/SOC_Details.xlsx");
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving offer details: "+e1.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}

		params.put("<MARKET>", market);
		if(!Dictionary.get("STATE_CODE").isEmpty()){
			params.put("<STATE_CODE>", Dictionary.get("STATE_CODE"));
			pkNpaNxx = "NPA_NXX_MARKET_STATE_CODE";
			pkNpaNxxCount = "COUNT_MARKET_NPA_NXX_STATE_CODE";
		}
		
		try { 
			params.put("<BLACKLIST>", Environment.get("BLACKLIST_PHONE_NUMBERS"));
			rs=dbAction.getDataFromDBWithParameters(pkNpaNxxCount, params);
			if(!rs.getString("numberOfRecords").trim().equals("0")){
				rs=dbAction.getDataFromDBWithParameters(pkNpaNxx, params);
//				rs.getString("NPA");
				npaFoundInMarket=true;
			}else{
				Reporter.fnWriteToHtmlOutput("Get NP/NXX from primary market", "NPA/NXX available", "NPA/NXX not available", "Done");
				throw new Exception();
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		} 

		try {
			do{
				blackListNumberMatches=false;
				if(Environment.get("BLACKLIST_PHONE_NUMBERS").isEmpty()==false){
					verifyBlackList:
						for(int j=0;j<Environment.get("BLACKLIST_PHONE_NUMBERS").split(",").length;j++){
//							System.out.println("Inside black list loop");
							if(rs.getString("NPA").trim().concat(rs.getString("NXX").trim()).contains(Environment.get("BLACKLIST_PHONE_NUMBERS").split(",")[j])){
								System.out.println("Phone number "+rs.getString("NPA").trim().concat(rs.getString("NXX").trim())+" present in black list");
								blackListNumberMatches=true;
								break verifyBlackList;
							}
						}
				}
				
				if(blackListNumberMatches==false){
					npa.add(rs.getString("NPA").trim());
					nxx.add(rs.getString("NXX").trim());
				}
			}while(rs.next());
			
			if(npa.isEmpty() || nxx.isEmpty())
				throw new Exception();
		} catch (Exception e1) {
				e1.printStackTrace();
		}

		String createBAN_URI=Environment.get("CREATE_BAN_URI");
		int j=0;

		Dictionary.put("FIRST_NAME",firstName);
		Dictionary.put("LAST_NAME",lastName);
		Dictionary.put("SID",String.valueOf(accountSubType));
		Dictionary.put("SECURITY_QUESTION",securityQuestion);
		Dictionary.put("SECURITY_ANSWER",securityAnswer);
		Dictionary.put("ITEM_ID",itemID);
		Dictionary.put("DEVICE_INFO_SOURCE",deviceInfoSource);
		Dictionary.put("SIM_NUMBER",sim);
		Dictionary.put("DEVICE_NUMBER",imei);
		Dictionary.put("MARKET",market);
		Dictionary.put("ACCOUNT_TYPE", accountType);
//		Dictionary.put("APPLICATION_ID", applicationName);
		
		if(pricePlan.toUpperCase().equals("ANY") || pricePlan.isEmpty())
			pricePlan=getDefaultPlan(accountSubType);	
		else if(pricePlan.toUpperCase().length()==2 || pricePlan.length()==3)
			pricePlan=getSpecificCategoryPricePlanFromDataBase(pricePlan);				
		
		try {
			System.out.println("Query for retrieving offercode/pricesheet: "+"select OfferCode, PriceSheet from PricePlans where PP_Code='"+pricePlan+"'"+" and OfferCode is not null and PriceSheet is not null");
			rds=conn.executeQuery("select OfferCode, PriceSheet from PricePlans where PP_Code='"+pricePlan+"'"+" and OfferCode is not null and PriceSheet is not null");
			rds.next();
		} catch (FilloException e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving OfferCode/PriceSheet details: "+e.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}

		try {
			offerCode=rds.getField("OfferCode");
		} catch (FilloException e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving offer code details: "+e.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}
		Dictionary.put("PRICE_PLAN",pricePlan);
		try {
			priceSheetCode=rds.getField("PriceSheet");
		} catch (FilloException e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving price sheet details: "+e.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}

		createNewRequest:
			do{
				Dictionary.put("NPA",npa.get(i));
				Dictionary.put("NXX", nxx.get(i));
				Dictionary.put("PIN_NUMBER",pin);
				Dictionary.put("MARKET",market);
				Dictionary.put("OFFER_CODE",offerCode);
				Dictionary.put("PRICE_SHEET_CODE",priceSheetCode);

				String templatePath =  System.getProperty("user.dir") + "/Templates" + "/CreateSingleBAN.json";
				reqBody = replaceTemplateWithValues(templatePath);
				if(Dictionary.get("DEVICE_INFO_SOURCE").equalsIgnoreCase("B")){
						reqBody = reqBody.replace("itemID", "itemId");
				}
				
				String authString = user + ":" + Password;
				byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
				String encodedString = new String(base64Encoded);
				RestAssured.baseURI = Environment.get("URI");
				RequestSpecification httpRequest = RestAssured.given();
				httpRequest.contentType("application/json");
				httpRequest.header("Authorization", "Basic " + encodedString);
				Response response = httpRequest.request(Method.GET, "/api/user");

				responseStatusCode = response.getStatusCode();
				if(responseStatusCode == 200 || responseStatusCode == 201)
				{
					Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
					System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
				}
				else
				{
					Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
					Reporter.fnWriteToHtmlOutput("createSingleBANUsingMS","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Fail");
					Environment.put("ROOT_CAUSE", "Environment Error");
					Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("error"));
					MainDriver.incrementEnvironmentErrorCount();
					return null;
				}
				httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

				httpRequest.body(reqBody);
				Reporter.fnWriteToHtmlOutput("CreateBANUsingMS", "URI", "URI: "+Environment.get("URI")+createBAN_URI, "Done");
				Reporter.fnWriteToHtmlOutput("CreateBANUsingMS", "Request sent", "Request sent: "+reqBody, "Done");
				response1 = httpRequest.request(Method.POST, createBAN_URI);

				responseStatusCode = response1.getStatusCode();
				System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
				Reporter.fnWriteToHtmlOutput("CreateBANUsingMS","Execute CreateBAN MS", "MS executed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");

				if((responseStatusCode == 400 || responseStatusCode==500000055) && response1.getBody().jsonPath().getString("errorMessage").equals("PIN numbering cannot be consecutive or repeated and should not contain any dates formatted with month, day and year. Please ask the customer to select a unique PIN that they will remember and that others cannot guess.")){
					j++;
					pin=objCommon.generateRandomNumber(8);

					if(j>=2){
						Reporter.fnWriteToHtmlOutput("createSingleBANUsingMS","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
						return null;
					}
					continue createNewRequest;
				}

				if((responseStatusCode == 503 && (response1.getBody().jsonPath().getString("errorMessage").startsWith("Error in CSM service poSvOrd"))) || (responseStatusCode==400 && (response1.getBody().jsonPath().getString("errorMessage").startsWith("There are not enough available PTN resources for")))){
					i++;
					if(i==npa.size()){
						if(npaFoundInAlternateMarket==false){
							try{market="0"+Environment.get("ALTERNATE_MARKET");
							params.put("<MARKET>", market);
							rs=dbAction.getDataFromDBWithParameters("NPA_NXX_MARKET", params);
							rs.getString("NPA");
							npaFoundInAlternateMarket=true;
							}catch (Exception e1){
								e1.printStackTrace();
								return null;
							}
							npa=new ArrayList<>();
							nxx=new ArrayList<>();

							try {
								do{
									blackListNumberMatches=false;
									if(Environment.get("BLACKLIST_PHONE_NUMBERS").isEmpty()==false){
										verifyBlackList:
											for(j=0;j<Environment.get("BLACKLIST_PHONE_NUMBERS").split(",").length;j++){
												System.out.println("Inside black list loop");
												if(rs.getString("NPA").trim().concat(rs.getString("NXX").trim()).contains(Environment.get("BLACKLIST_PHONE_NUMBERS").split(",")[j])){
													System.out.println("Blacklist validated");
													blackListNumberMatches=true;
													break verifyBlackList;
												}
											}
									}
									
									if(blackListNumberMatches==false){
										npa.add(rs.getString("NPA").trim());
										nxx.add(rs.getString("NXX").trim());
									}
								}while(rs.next());
							} catch (Exception e1) {
								e1.printStackTrace();
								Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving NPA/NXX details: "+e1.getLocalizedMessage(), "Fail");
								Environment.put("ROOT_CAUSE", "No data return for query");
								Environment.put("FAILURE_ERROR","No data return for query");
								MainDriver.incrementDataNotAvailableCount();
								return null;
							}
							i=0;
							continue createNewRequest;
						}
					}
				}else if(responseStatusCode==400 && response1.getBody().jsonPath().getString("errorMessage").endsWith("is already in use")){
					if(npaFoundInAlternateMarket==false){
						try{market="0"+Environment.get("ALTERNATE_MARKET");
						params.put("<MARKET>", market);
						rs=dbAction.getDataFromDBWithParameters("NPA_NXX_MARKET", params);
						rs.getString("NPA");
						npaFoundInAlternateMarket=true;
						}catch (Exception e1){
							e1.printStackTrace();
							return null;
						}
						npa=new ArrayList<>();
						nxx=new ArrayList<>();

						try {
							do{
								npa.add(rs.getString("NPA").trim());
								nxx.add(rs.getString("NXX").trim());
							}while(rs.next());
						} catch (Exception e1) {
							e1.printStackTrace();
							Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving NPA/NXX details: "+e1.getLocalizedMessage(), "Fail");
							Environment.put("ROOT_CAUSE", "No data return for query");
							Environment.put("FAILURE_ERROR","No data return for query");
							MainDriver.incrementDataNotAvailableCount();
							return null;
						}
						continue createNewRequest;
					}else
						return null;
				}
				else if(responseStatusCode == 400 && response1.getBody().jsonPath().getString("errorMessage").equals("There is no network coverage in the Selected Area for the target network.")){
					if(npaFoundInAlternateMarket==false){
						try{market="0"+Environment.get("ALTERNATE_MARKET");
						params.put("<MARKET>", market);
						rs=dbAction.getDataFromDBWithParameters("NPA_NXX_MARKET", params);
						rs.getString("NPA");
						npaFoundInAlternateMarket=true;
						}catch (Exception e1){
							e1.printStackTrace();
							return null;
						}
						npa=new ArrayList<>();
						nxx=new ArrayList<>();

						try {
							do{
								npa.add(rs.getString("NPA").trim());
								nxx.add(rs.getString("NXX").trim());
							}while(rs.next());
						} catch (Exception e1) {
							e1.printStackTrace();
							Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving NPA/NXX details: "+e1.getLocalizedMessage(), "Fail");
							Environment.put("ROOT_CAUSE", "No data return for query");
							Environment.put("FAILURE_ERROR","No data return for query");
							MainDriver.incrementDataNotAvailableCount();
							return null;
						}
						continue createNewRequest;
					}else
						return null;
				} else if(responseStatusCode == 503 && response1.getBody().jsonPath().getString("errorMessage").equals("Failed to retrieve price plan from EPC") && tryAltyernatePriceSheetValues==false){
					tryAltyernatePriceSheetValues=true;
					try {
						System.out.println("Query for retrieving alternate offercode/pricesheet: "+"select AlternateOfferCode, AlternatePriceSheet from PricePlans where PP_Code='"+pricePlan+"'"+" and OfferCode is not null");
						rds=conn.executeQuery("select AlternateOfferCode, AlternatePriceSheet from PricePlans where PP_Code='"+pricePlan+"'"+" and OfferCode is not null");
						rds.next();
					} catch (FilloException e) {
						e.printStackTrace();
						Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving OfferCode/PriceSheet details: "+e.getLocalizedMessage(), "Fail");
						Environment.put("ROOT_CAUSE", "No data return for query");
						Environment.put("FAILURE_ERROR","No data return for query");
						MainDriver.incrementDataNotAvailableCount();
						return null;
					}

					try {
						offerCode=rds.getField("AlternateOfferCode");
					} catch (FilloException e) {
						e.printStackTrace();
						Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving offer code details: "+e.getLocalizedMessage(), "Fail");
						Environment.put("ROOT_CAUSE", "No data return for query");
						Environment.put("FAILURE_ERROR","No data return for query");
						MainDriver.incrementDataNotAvailableCount();
						return null;
					}
					Dictionary.put("PRICE_PLAN",pricePlan);
					try {
						priceSheetCode=rds.getField("AlternatePriceSheet");
					} catch (FilloException e) {
						e.printStackTrace();
						Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving price sheet details: "+e.getLocalizedMessage(), "Fail");
						Environment.put("ROOT_CAUSE", "No data return for query");
						Environment.put("FAILURE_ERROR","No data return for query");
						MainDriver.incrementDataNotAvailableCount();
						return null;
					}
				}else
					break;
			}while(i<npa.size());

		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("CreateBANUsingMS","Execute CreateBAN MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			JsonPath jp = new JsonPath(response1.getBody().asString());
			String ban = jp.getString("newCreatedAccountNumber");
			System.out.println("BAN: "+ban);
			
			params.put("<BAN>", ban);
			
			rs=dbAction.getDataFromDBWithParameters("BILLING_ACCOUNT_BY_BAN", params);
			
			try {
				if(rs.getString("BAN_STATUS").trim().equals("O")==false){
					Reporter.fnWriteToHtmlOutput("createSingleBANUsingMS","BAN is created successfully", "BAN status: "+rs.getString("BAN_STATUS").trim() ,"Fail");	
					Environment.put("ROOT_CAUSE", "Environment Error");
					Environment.put("FAILURE_ERROR"," ");
					MainDriver.incrementEnvironmentErrorCount();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(!Dictionary.get("COMPLETE_PROFILE").equals("N")) setPinNumberInDB(imei, user, Password);
			return ban;

		}
		else
		{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			String jsonResponseString=response1.getBody().asString();
			JSONObject jsonObject = new JSONObject(jsonResponseString);
			Iterator<String> keys=jsonObject.keys();
			String errorMessage=null;
			while(keys.hasNext()){
				String keyName=keys.next();
				System.out.println("Field name: "+jsonObject.get(keyName).toString());
				if(keyName.contains("essage")){
					errorMessage=jsonObject.getString(keyName).toString();
					break;
				}
			}
			
			Reporter.fnWriteToHtmlOutput("createSingleBANUsingMS","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
			
			if(errorMessage.contains("Failed to ") || errorMessage.contains("Error") || errorMessage.contains("error") || errorMessage.contains("is not authorized to perform the activity") || errorMessage.contains("There was a problem logging in") || errorMessage.contains("No route to host") || responseStatusCode==504){
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR",errorMessage);
				MainDriver.incrementEnvironmentErrorCount();
			}else if(errorMessage.contains("mandatory field is missing")){
				Environment.put("ROOT_CAUSE", "No data return for query");
				Environment.put("FAILURE_ERROR","No data return for query");
				MainDriver.incrementDataNotAvailableCount();
			}
				
			return null;
		}

	}

	//*****************************************************************************************
	//* Name            : addSingleLineUsingMS
	//* Description     : Add a line to existing account
	//* Author          : Zachi Gahari
	//* Updated Date    : 8 Jul 2020
	//* Input Params    : 
	//* Return Values   : 
	//*****************************************************************************************    
	public String addSingleLineUsingMS(String accountNumber, String sim, String imei, String market, String pricePlan, String user, String Password)
	{
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		HashMap<String, String> params = new HashMap<>();
		Fillo fillo = new Fillo();
		com.codoid.products.fillo.Connection conn;
		Recordset rds;
		ResultSet rs=null;
		List<String> npa=new ArrayList<String>();
		List<String> nxx=new ArrayList<String>();
		int i=0;
		Response response1=null;
		boolean npaFoundInMarket=false;
		boolean npaFoundInAlternateMarket=false;
		boolean tryAltyernatePriceSheetValues=false;
		String priceSheetCode=null;
		String offerCode=null;
		String pkNpaNxx = "NPA_NXX_MARKET";
		String pkNpaNxxCount = "COUNT_MARKET_NPA_NXX";
		String accountSubType=null;
		String tempMarket=null;
		boolean blackListNumberMatches=false;
		
		try {
			params.put("<BAN>", accountNumber);
			rs=dbAction.getDataFromDBWithParameters("BILLING_ACCOUNT", params);
			accountSubType=rs.getString("ACCOUNT_SUB_TYPE").trim();
		} catch (SQLException e2) {
			e2.printStackTrace();
			accountSubType="1";
		}
		
		try{
			conn=fillo.getConnection(System.getProperty("user.dir")+"/data/SOC_Details.xlsx");
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Add a line", "Line added successfully", "Error in retrieving offer details: "+e1.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}

		params.put("<MARKET>", market);
		if(!Dictionary.get("STATE_CODE").isEmpty()){
			params.put("<STATE_CODE>", Dictionary.get("STATE_CODE"));
			pkNpaNxx = "NPA_NXX_MARKET_STATE_CODE";
			pkNpaNxxCount = "COUNT_MARKET_NPA_NXX_STATE_CODE";
		}

		try { 
			params.put("<BLACKLIST>", Environment.get("BLACKLIST_PHONE_NUMBERS"));
			rs=dbAction.getDataFromDBWithParameters(pkNpaNxxCount, params);
			if(!rs.getString("numberOfRecords").trim().equals("0")){
				rs=dbAction.getDataFromDBWithParameters(pkNpaNxx, params);
				npaFoundInMarket=true;
			}else{
				Reporter.fnWriteToHtmlOutput("Get NP/NXX from primary market", "NPA/NXX available", "NPA/NXX not available", "Done");
				throw new Exception();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} 

		try {
			do{
				blackListNumberMatches=false;
				if(Environment.get("BLACKLIST_PHONE_NUMBERS").isEmpty()==false){
					verifyBlackList:
						for(int j=0;j<Environment.get("BLACKLIST_PHONE_NUMBERS").split(",").length;j++){
//							System.out.println("Inside black list loop");
							if(rs.getString("NPA").trim().concat(rs.getString("NXX").trim()).contains(Environment.get("BLACKLIST_PHONE_NUMBERS").split(",")[j])){
								System.out.println("Phone number "+rs.getString("NPA").trim().concat(rs.getString("NXX").trim())+" present in black list");
								blackListNumberMatches=true;
								break verifyBlackList;
							}
						}
				}
				
				if(blackListNumberMatches==false){
					npa.add(rs.getString("NPA").trim());
					nxx.add(rs.getString("NXX").trim());
				}
			}while(rs.next());
			
			if(npa.isEmpty() || nxx.isEmpty())
				throw new Exception();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String addLine_URI=Environment.get("ADD_LINE_URI");

		if(pricePlan.toUpperCase().equals("ANY") || pricePlan.isEmpty())
			pricePlan=getDefaultPlan(accountSubType);
				
		try {
			System.out.println("Query for retrieving offercode/pricesheet: "+"select OfferCode, PriceSheet from PricePlans where PP_Code='"+pricePlan+"'"+" and OfferCode is not null");
			rds=conn.executeQuery("select OfferCode, PriceSheet from PricePlans where PP_Code='"+pricePlan+"'"+" and OfferCode is not null");
			rds.next();
		} catch (FilloException e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Add a line", "Line added successfully", "Error in retrieving OfferCode/PriceSheet details for price plan "+pricePlan+": "+e.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}

		try {
			offerCode=rds.getField("OfferCode");
		} catch (FilloException e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Add a line", "Line added successfully", "Error in retrieving offer code details: "+e.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}
		try {
			priceSheetCode=rds.getField("PriceSheet");
		} catch (FilloException e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Add a line", "Line added successfully", "Error in retrieving price sheet details: "+e.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}

		createNewRequest:
			do{
				Dictionary.put("ACCOUNT_NO", accountNumber);
				Dictionary.put("SIM_NUMBER",sim);
				Dictionary.put("DEVICE_NUMBER",imei);
				Dictionary.put("NPA",npa.get(i));
				Dictionary.put("NXX", nxx.get(i));
				Dictionary.put("MARKET",market);
				Dictionary.put("OFFER_CODE",offerCode);
				Dictionary.put("PRICE_PLAN_CODE",pricePlan);
				Dictionary.put("PRICE_SHEET_CODE",priceSheetCode);

				String templatePath =  System.getProperty("user.dir") + "/Templates" + "/AddALine.json";
				reqBody = replaceTemplateWithValues(templatePath);

				String authString = user + ":" + Password;
				byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
				String encodedString = new String(base64Encoded);
				RestAssured.baseURI = Environment.get("URI");
				RequestSpecification httpRequest = RestAssured.given();
				httpRequest.contentType("application/json");
				httpRequest.header("Authorization", "Basic " + encodedString);
				Response response = httpRequest.request(Method.GET, "/api/user");

				responseStatusCode = response.getStatusCode();
				if(responseStatusCode == 200 || responseStatusCode == 201)
				{
					Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
					System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
				}
				else
				{
					Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
					Reporter.fnWriteToHtmlOutput("addSingleLineUsingMS","Execute Add a line MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Fail");
					Environment.put("ROOT_CAUSE", "Environment Error");
					Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("error"));
					MainDriver.incrementEnvironmentErrorCount();
					return null;
				}
				httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

				httpRequest.body(reqBody);
				Reporter.fnWriteToHtmlOutput("AddSingleLineUsingMS", "URI", "URI: "+Environment.get("URI")+addLine_URI, "Done");
				Reporter.fnWriteToHtmlOutput("AddSingleLineUsingMS", "Request sent", "Request sent: "+reqBody, "Done");
				response1 = httpRequest.request(Method.POST, addLine_URI);

				responseStatusCode = response1.getStatusCode();
				System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
				Reporter.fnWriteToHtmlOutput("AddSingleLineUsingMS","Execute add a line MS", "MS executed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");

				if((responseStatusCode == 503 && (response1.getBody().jsonPath().getString("errorMessage").startsWith("Error in CSM service poSvOrd"))) || (responseStatusCode==400 && (response1.getBody().jsonPath().getString("errorMessage").startsWith("There are not enough available PTN resources for")))){
					i++;
					if(i==npa.size()){
						if(npaFoundInAlternateMarket==false){
							try{market="0"+Environment.get("ALTERNATE_MARKET");
							params.put("<MARKET>", market);
							rs=dbAction.getDataFromDBWithParameters("NPA_NXX_MARKET", params);
							rs.getString("NPA");
							npaFoundInAlternateMarket=true;
							}catch (Exception e1){
								e1.printStackTrace();
								return null;
							}
							npa=new ArrayList<>();
							nxx=new ArrayList<>();

							try {
								do{
									npa.add(rs.getString("NPA").trim());
									nxx.add(rs.getString("NXX").trim());
								}while(rs.next());
							} catch (Exception e1) {
								e1.printStackTrace();
								Reporter.fnWriteToHtmlOutput("Add a line", "Line added successfully", "Error in retrieving NPA/NXX details: "+e1.getLocalizedMessage(), "Fail");
								Environment.put("ROOT_CAUSE", "No data return for query");
								Environment.put("FAILURE_ERROR","No data return for query");
								MainDriver.incrementDataNotAvailableCount();
								return null;
							}
							i=0;
							continue createNewRequest;
						}
					}
				}else if(responseStatusCode == 400 && response1.getBody().jsonPath().getString("errorMessage").equals("There is no network coverage in the Selected Area for the target network.")){
					if(npaFoundInAlternateMarket==false){
						try{market="0"+Environment.get("ALTERNATE_MARKET");
						params.put("<MARKET>", market);
						rs=dbAction.getDataFromDBWithParameters("NPA_NXX_MARKET", params);
						rs.getString("NPA");
						npaFoundInAlternateMarket=true;
						}catch (Exception e1){
							e1.printStackTrace();
							return null;
						}
						npa=new ArrayList<>();
						nxx=new ArrayList<>();

						try {
							do{
								npa.add(rs.getString("NPA").trim());
								nxx.add(rs.getString("NXX").trim());
							}while(rs.next());
						} catch (Exception e1) {
							e1.printStackTrace();
							Reporter.fnWriteToHtmlOutput("Add a line", "Line added successfully", "Error in retrieving NPA/NXX details: "+e1.getLocalizedMessage(), "Fail");
							Environment.put("ROOT_CAUSE", "No data return for query");
							Environment.put("FAILURE_ERROR","No data return for query");
							MainDriver.incrementDataNotAvailableCount();
							return null;
						}
						continue createNewRequest;
					}else
						return null;
				} else if(responseStatusCode == 503 && response1.getBody().jsonPath().getString("errorMessage").equals("Failed to retrieve price plan from EPC") && tryAltyernatePriceSheetValues==false){
					tryAltyernatePriceSheetValues=true;
					try {
						System.out.println("Query for retrieving alternate offercode/pricesheet: "+"select AlternateOfferCode, AlternatePriceSheet from PricePlans where PP_Code='"+pricePlan+"'"+" and OfferCode is not null");
						rds=conn.executeQuery("select AlternateOfferCode, AlternatePriceSheet from PricePlans where PP_Code='"+pricePlan+"'"+" and OfferCode is not null");
						rds.next();
					} catch (FilloException e) {
						e.printStackTrace();
						Reporter.fnWriteToHtmlOutput("Add a line", "Line added successfully", "Error in retrieving OfferCode/PriceSheet details: "+e.getLocalizedMessage(), "Fail");
						Environment.put("ROOT_CAUSE", "No data return for query");
						Environment.put("FAILURE_ERROR","No data return for query");
						MainDriver.incrementDataNotAvailableCount();
						return null;
					}

					try {
						offerCode=rds.getField("AlternateOfferCode");
					} catch (FilloException e) {
						e.printStackTrace();
						Reporter.fnWriteToHtmlOutput("Add a line", "Line added successfully", "Error in retrieving offer code details: "+e.getLocalizedMessage(), "Fail");
						Environment.put("ROOT_CAUSE", "No data return for query");
						Environment.put("FAILURE_ERROR","No data return for query");
						MainDriver.incrementDataNotAvailableCount();
						return null;
					}
					Dictionary.put("PRICE_PLAN",pricePlan);
					try {
						priceSheetCode=rds.getField("AlternatePriceSheet");
					} catch (FilloException e) {
						e.printStackTrace();
						Reporter.fnWriteToHtmlOutput("Add a line", "Line added successfully", "Error in retrieving price sheet details: "+e.getLocalizedMessage(), "Fail");
						Environment.put("ROOT_CAUSE", "No data return for query");
						Environment.put("FAILURE_ERROR","No data return for query");
						MainDriver.incrementDataNotAvailableCount();
						return null;
					}
				}else
					break;
			}while(i<npa.size());

		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("addSingleLineUsingMS","Execute Add a line MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			JsonPath jp = new JsonPath(response1.getBody().asString());
			String ptn = jp.getString("newActivatedMdnList");
			System.out.println("PTN: "+ ptn);
			return ptn;

		}
		else
		{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("addSingleLineUsingMS","Execute Add a line MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
			
			String jsonResponseString=response1.getBody().asString();
			JSONObject jsonObject = new JSONObject(jsonResponseString);
			Iterator<String> keys=jsonObject.keys();
			String errorMessage=null;
			while(keys.hasNext()){
				String keyName=keys.next();
				System.out.println("Field name: "+jsonObject.get(keyName).toString());
				if(keyName.contains("essage")){
					errorMessage=jsonObject.getString(keyName).toString();
					break;
				}
			}
			
			if(errorMessage.contains("Failed to ") || errorMessage.contains("Error") || errorMessage.contains("error") || errorMessage.contains("is not authorized to perform the activity") || errorMessage.contains("There was a problem logging in") || errorMessage.contains("No route to host") || responseStatusCode==504){
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR",errorMessage);
				MainDriver.incrementEnvironmentErrorCount();
			}else if(errorMessage.contains("mandatory field is missing")){
				Environment.put("ROOT_CAUSE", "No data return for query");
				Environment.put("FAILURE_ERROR","No data return for query");
				MainDriver.incrementDataNotAvailableCount();
			}
			return null;
		}

	}


	//*****************************************************************************************
	//* Name            : setPinNumberInDB
	//* Description     : Set PIN number in DB for account
	//* Author          : Zachi Gahari
	//* Updated Date    : November, 2019
	//* Input Params    : Device number and  PIN 
	//* Return Values   : None
	//*****************************************************************************************
	//	commenting out whole code since SRP functionality is on hold for undefined period and this MS is not required to be run
	public boolean setPinNumberInDB(String deviceNumber, String user, String Password)
	{
		//		Random r = new Random();
		//		String randomNum = "";
		//		int counter = 0;
		//		while (counter++ < 8)
		//			randomNum += r.nextInt(9);
		//
		//		String updatePIN=Environment.get("UPDATE_PIN");
		//		Dictionary.put("Device_Number",deviceNumber);
		//		Dictionary.put("PIN_Number",String.valueOf(randomNum)); //
		//		String templatePath =  System.getProperty("user.dir") + "/Templates" + "/UpdatePIN.json";
		//		reqBody = replaceTemplateWithValues(templatePath);
		//		
		//		String authString = user + ":" + Password;
		//		byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
		//		String encodedString = new String(base64Encoded);
		//		RestAssured.baseURI = Environment.get("URI");
		//		RequestSpecification httpRequest = RestAssured.given();
		//		httpRequest.contentType("application/json");
		//		httpRequest.header("Authorization", "Basic " + encodedString);
		//		Response response = null;
		//		try {
		//			response = httpRequest.request(Method.GET, "/api/user");
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//
		//		responseStatusCode = response.getStatusCode();
		//		System.out.println(responseStatusCode);
		//		if(responseStatusCode == 200 || responseStatusCode == 201)
		//		{
		//			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Pass");
		//			System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
		//		}
		//		else
		//		{	
		//			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
		//			Reporter.fnWriteToHtmlOutput("setPinNumberInDB","Execute updatePIN API", "API executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Done");
		//			return false;
		//		}
		//		httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));
		//
		//		httpRequest.body(reqBody);
		//		Reporter.fnWriteToHtmlOutput("setPinNumberInDB", "URI", "URI: "+ updatePIN, "Done");
		//		Reporter.fnWriteToHtmlOutput("setPinNumberInDB", "Request sent", "Request sent: "+reqBody, "Done");
		//		Response response1 = httpRequest.request(Method.POST, updatePIN);
		//
		//		responseStatusCode = response1.getStatusCode();
		//		if(responseStatusCode == 200 || responseStatusCode == 201)
		//		{
		//			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
		//			Reporter.fnWriteToHtmlOutput("setPinNumberInDB","Execute updatePIN API", "API executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
		//			Reporter.fnWriteToHtmlOutput("setPinNumberInDB","Update PIN in DB", "Pin number updated successfully","Done");
		//			return true;
		//			
		//		}
		//		else
		//		{
		//			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
		//			Reporter.fnWriteToHtmlOutput("setPinNumberInDB","Execute updatePIN API", "API executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
		//			Reporter.fnWriteToHtmlOutput("setPinNumberInDB","Update PIN in DB", "Failed to update PIN number in DB", "Fail");
		//			return false;
		//		}

		return true;

	}

	public String createTwoLineBANUsingMS(String firstName, String lastName, String accountSubType ,String pin, String securityQuestion, String securityAnswer, String firstLineItemID, String firstLineDeviceInfoSource, String firstLineSim, String firstLineImei, String market, String firstLinePricePlan,String secondLineItemID, String secondLineDeviceInfoSource, String secondLineSim, String secondLineImei, String secondLinePricePlan, String user, String Password)
	{
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		CommonFunctions objCommon = new CommonFunctions(driver, driverType, Environment, Reporter);
		HashMap<String, String> params = new HashMap<>();
		Fillo fillo = new Fillo();
		com.codoid.products.fillo.Connection conn;
		Recordset rds;
		ResultSet rs=null;
		List<String> npa=new ArrayList<String>();
		List<String> nxx=new ArrayList<String>();
		int i=0;
		Response response1=null;
		int j=0;
		boolean npaFoundInMarket=false;
		boolean npaFoundInAlternateMarket=false;
		String offerCode1=null;
		String priceSheetCode1=null;
		String offerCode2=null;
		String priceSheetCode2=null;
		boolean tryAltyernatePriceSheetValues=false;
		String pkNpaNxx = "NPA_NXX_MARKET";
		String pkNpaNxxCount = "COUNT_MARKET_NPA_NXX";
		String accountType=null;
		String tempMarket=null;
		boolean blackListNumberMatches=false;
		
		if(accountSubType.isEmpty())
			accountSubType="1";
		
		rs=dbAction.getDataFromDB("BRAND_INFO");
		
		try {
			accountType=rs.getString("ACCOUNT_TYPE").trim();
		} catch (SQLException e2) {
			e2.printStackTrace();
			accountType="I";
		}
		
		if(accountType.equals("I")==false){
			rs=dbAction.getDataFromDB("SECURITY_QUESTION");		
			try {
				do{
					if(rs.getString("ACCOUNT_TYPE").trim().equals(accountType)){
						securityQuestion=rs.getString("QUESTION_CODE").trim();
						break;
					}
				}while(rs.next());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		try {
			conn=fillo.getConnection(System.getProperty("user.dir")+"/data/SOC_Details.xlsx");
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving offer details: "+e1.getLocalizedMessage(), "Fail");
			return null;
		}

		params.put("<MARKET>", market);
		if(!Dictionary.get("STATE_CODE").isEmpty()){
			params.put("<STATE_CODE>", Dictionary.get("STATE_CODE"));
			pkNpaNxx = "NPA_NXX_MARKET_STATE_CODE";
			pkNpaNxxCount = "COUNT_MARKET_NPA_NXX_STATE_CODE";
		}

		try { 
			params.put("<BLACKLIST>", Environment.get("BLACKLIST_PHONE_NUMBERS"));
			rs=dbAction.getDataFromDBWithParameters(pkNpaNxxCount, params);
		if(!rs.getString("numberOfRecords").trim().equals("0")){
			rs=dbAction.getDataFromDBWithParameters(pkNpaNxx, params);
			rs.getString("NPA");
			npaFoundInMarket=true;
		} else{
			Reporter.fnWriteToHtmlOutput("Get NP/NXX from primary market", "NPA/NXX available", "NPA/NXX not available", "Done");
			throw new Exception();
		}

		} catch (Exception e) {
			e.printStackTrace();
			if(!Dictionary.get("STATE_CODE").isEmpty()){ // Zachi - In case of using specific market, no need to try alternative market
				return null;
			}
			try{
				market="0"+Environment.get("ALTERNATE_MARKET");
				params.put("<MARKET>", market);
				rs=dbAction.getDataFromDBWithParameters("COUNT_MARKET_NPA_NXX", params);
				if(!rs.getString("numberOfRecords").trim().equals("0")){
					rs=dbAction.getDataFromDBWithParameters("NPA_NXX_MARKET", params);
					rs.getString("NPA");
					npaFoundInAlternateMarket=true;
				}else{
					Reporter.fnWriteToHtmlOutput("Get phone numbers", "Phone numbers available in DB", "Phone numbers not available in DB", "VALIDATION FAIL");
					return null;
				}
			}catch (Exception e1){
				e1.printStackTrace();
				return null;
			}
		} 

		try {
			do{
				blackListNumberMatches=false;
				if(Environment.get("BLACKLIST_PHONE_NUMBERS").isEmpty()==false){
					verifyBlackList:
						for(j=0;j<Environment.get("BLACKLIST_PHONE_NUMBERS").split(",").length;j++){
//							System.out.println("Inside black list loop");
							if(rs.getString("NPA").trim().concat(rs.getString("NXX").trim()).contains(Environment.get("BLACKLIST_PHONE_NUMBERS").split(",")[j])){
								System.out.println("Phone number "+rs.getString("NPA").trim().concat(rs.getString("NXX").trim())+" present in black list");
								blackListNumberMatches=true;
								break verifyBlackList;
							}
						}
				}
				
				if(blackListNumberMatches==false){
					npa.add(rs.getString("NPA").trim());
					nxx.add(rs.getString("NXX").trim());
				}
			}while(rs.next());
			
			if(npa.isEmpty() || nxx.isEmpty())
				throw new Exception();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String createBAN_URI=Environment.get("CREATE_BAN_URI");

		Dictionary.put("FIRST_NAME",firstName);
		Dictionary.put("LAST_NAME",lastName);
		Dictionary.put("SID",String.valueOf(accountSubType));
		Dictionary.put("SECURITY_QUESTION",securityQuestion);
		Dictionary.put("SECURITY_ANSWER",securityAnswer);
		Dictionary.put("ITEM_ID_1",firstLineItemID);
		Dictionary.put("DEVICE_INFO_SOURCE_1",firstLineDeviceInfoSource);
		Dictionary.put("SIM_NUMBER_1",firstLineSim);
		Dictionary.put("DEVICE_NUMBER_1",firstLineImei);
		Dictionary.put("MARKET",market);
		Dictionary.put("ITEM_ID_2",secondLineItemID);
		Dictionary.put("DEVICE_INFO_SOURCE_2",secondLineDeviceInfoSource);
		Dictionary.put("SIM_NUMBER_2",secondLineSim);
		Dictionary.put("DEVICE_NUMBER_2",secondLineImei);
		Dictionary.put("ACCOUNT_TYPE", accountType);

		if(firstLinePricePlan.toUpperCase().equals("ANY") || firstLinePricePlan.isEmpty())
			firstLinePricePlan=getDefaultPlan(accountSubType);
		else if(firstLinePricePlan.toUpperCase().length()==2 || firstLinePricePlan.length()==3)
			firstLinePricePlan=getSpecificCategoryPricePlanFromDataBase(firstLinePricePlan);	
		

		if(secondLinePricePlan.toUpperCase().equals("ANY") || secondLinePricePlan.isEmpty())
			secondLinePricePlan=getDefaultPlan(accountSubType);
		else if(secondLinePricePlan.toUpperCase().length()==2 || secondLinePricePlan.length()==3)
			secondLinePricePlan=getSpecificCategoryPricePlanFromDataBase(secondLinePricePlan);
		
		try {
			System.out.println("Query for retrieving first line offercode/pricesheet: "+"select OfferCode, PriceSheet from PricePlans where PP_Code='"+firstLinePricePlan+"'"+" and OfferCode is not null and PriceSheet is not null");
			rds=conn.executeQuery("select OfferCode, PriceSheet from PricePlans where PP_Code='"+firstLinePricePlan+"'"+" and OfferCode is not null and PriceSheet is not null");
			rds.next();
		} catch (FilloException e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving OfferCode/PriceSheet details: "+e.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}

		try {
			offerCode1=rds.getField("OfferCode");
		} catch (FilloException e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving offer code details: "+e.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}
		Dictionary.put("PRICE_PLAN_1",firstLinePricePlan);
		try {
			priceSheetCode1=rds.getField("PriceSheet");
		} catch (FilloException e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving price sheet details: "+e.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}

		try {
			System.out.println("Query for retrieving second line offercode/pricesheet: "+"select OfferCode, PriceSheet from PricePlans where PP_Code='"+secondLinePricePlan+"'"+" and OfferCode is not null and PriceSheet is not null");
			rds=conn.executeQuery("select OfferCode, PriceSheet from PricePlans where PP_Code='"+secondLinePricePlan+"'"+" and OfferCode is not null and PriceSheet is not null");
			rds.next();
		} catch (FilloException e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving OfferCode/PriceSheet details: "+e.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}

		try {
			offerCode2=rds.getField("OfferCode");
		} catch (FilloException e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving offer code details: "+e.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}
		Dictionary.put("PRICE_PLAN_2",secondLinePricePlan);
		try {
			priceSheetCode2=rds.getField("PriceSheet");
		} catch (FilloException e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving price sheet details: "+e.getLocalizedMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}

		createNewRequest:
			do{	
				Dictionary.put("NPA",npa.get(i));
				Dictionary.put("NXX", nxx.get(i));
				Dictionary.put("PIN_NUMBER",pin);
				Dictionary.put("MARKET",market);
				Dictionary.put("OFFER_CODE_1",offerCode1);
				Dictionary.put("PRICE_SHEET_CODE_1",priceSheetCode1);
				Dictionary.put("OFFER_CODE_2",offerCode2);
				Dictionary.put("PRICE_SHEET_CODE_2",priceSheetCode2);

				String templatePath =  System.getProperty("user.dir") + "/Templates" + "/CreateTwoLineBAN.json";
				reqBody = replaceTemplateWithValues(templatePath);
				if(Dictionary.get("DEVICE_INFO_SOURCE_1").equalsIgnoreCase("B"))
					reqBody = reqBody.replace("itemID\":\"" + firstLineItemID, "itemId\":\"" + firstLineItemID);
				if(Dictionary.get("DEVICE_INFO_SOURCE_2").equalsIgnoreCase("B"))
					reqBody = reqBody.replace("itemID\":\"" + secondLineItemID, "itemId\":\"" + secondLineItemID);

				String authString = user + ":" + Password;
				byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
				String encodedString = new String(base64Encoded);
				RestAssured.baseURI = Environment.get("URI");
				RequestSpecification httpRequest = RestAssured.given();
				httpRequest.contentType("application/json");
				httpRequest.header("Authorization", "Basic " + encodedString);
				Response response = httpRequest.request(Method.GET, "/api/user");

				responseStatusCode = response.getStatusCode();

				if(responseStatusCode == 200 || responseStatusCode == 201)
				{
					Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
					System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
				}
				else
				{
					Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
					Reporter.fnWriteToHtmlOutput("createTwoLineBANUsingMS","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Fail");
					Environment.put("ROOT_CAUSE", "Environment Error");
					Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("error"));
					MainDriver.incrementEnvironmentErrorCount();
					return null;
				}
				httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

				httpRequest.body(reqBody);
				Reporter.fnWriteToHtmlOutput("CreateBANUsingMS", "URI", "URI: "+Environment.get("URI")+createBAN_URI, "Done");
				Reporter.fnWriteToHtmlOutput("CreateBANUsingMS", "Request sent", "Request sent: "+reqBody, "Done");
				response1 = httpRequest.request(Method.POST, createBAN_URI);

				responseStatusCode = response1.getStatusCode();

				System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
				Reporter.fnWriteToHtmlOutput("CreateBANUsingMS","Execute CreateBAN MS", "MS executed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");

				if((responseStatusCode == 400 || responseStatusCode==500000055) && response1.getBody().jsonPath().getString("errorMessage").equals("PIN numbering cannot be consecutive or repeated and should not contain any dates formatted with month, day and year. Please ask the customer to select a unique PIN that they will remember and that others cannot guess.")){
					j++;
					pin=objCommon.generateRandomNumber(8);

					if(j>=2){
						Reporter.fnWriteToHtmlOutput("createTwoLineBANUsingMS","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
						return null;
					}
					continue createNewRequest;
				}

				if((responseStatusCode == 503 && (response1.getBody().jsonPath().getString("errorMessage").startsWith("Error in CSM service poSvOrd"))) || (responseStatusCode==400 && (response1.getBody().jsonPath().getString("errorMessage").startsWith("There are not enough available PTN resources for")))){
					i++;
					if(i==npa.size()){
						if(npaFoundInAlternateMarket==false){
							try{market="0"+Environment.get("ALTERNATE_MARKET");
							params.put("<MARKET>", market);
							rs=dbAction.getDataFromDBWithParameters("NPA_NXX_MARKET", params);
							rs.getString("NPA");
							npaFoundInAlternateMarket=true;
							}catch (Exception e1){
								e1.printStackTrace();
								return null;
							}
							npa=new ArrayList<>();
							nxx=new ArrayList<>();

							try {
								do{
									blackListNumberMatches=false;
									if(Environment.get("BLACKLIST_PHONE_NUMBERS").isEmpty()==false){
										verifyBlackList:
											for(j=0;j<Environment.get("BLACKLIST_PHONE_NUMBERS").split(",").length;j++){
												System.out.println("Inside black list loop");
												if(rs.getString("NPA").trim().concat(rs.getString("NXX").trim()).contains(Environment.get("BLACKLIST_PHONE_NUMBERS").split(",")[j])){
													System.out.println("Blacklist validated");
													blackListNumberMatches=true;
													break verifyBlackList;
												}
											}
									}
									
									if(blackListNumberMatches==false){
										npa.add(rs.getString("NPA").trim());
										nxx.add(rs.getString("NXX").trim());
									}
								}while(rs.next());
							} catch (Exception e1) {
								e1.printStackTrace();
								Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving NPA/NXX details: "+e1.getLocalizedMessage(), "Fail");
								Environment.put("ROOT_CAUSE", "No data return for query");
								Environment.put("FAILURE_ERROR","No data return for query");
								MainDriver.incrementDataNotAvailableCount();
								return null;
							}
							i=0;
							continue createNewRequest;
						}
					}
				}else if(responseStatusCode == 400 && response1.getBody().jsonPath().getString("errorMessage").equals("There is no network coverage in the Selected Area for the target network.")){
					if(npaFoundInAlternateMarket==false){
						try{market="0"+Environment.get("ALTERNATE_MARKET");
						params.put("<MARKET>", market);
						rs=dbAction.getDataFromDBWithParameters("NPA_NXX_MARKET", params);
						rs.getString("NPA");
						npaFoundInAlternateMarket=true;
						}catch (Exception e1){
							e1.printStackTrace();
							return null;
						}
						npa=new ArrayList<>();
						nxx=new ArrayList<>();

						try {
							do{
								npa.add(rs.getString("NPA").trim());
								nxx.add(rs.getString("NXX").trim());
							}while(rs.next());
						} catch (Exception e1) {
							e1.printStackTrace();
							Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving NPA/NXX details: "+e1.getLocalizedMessage(), "Fail");
							Environment.put("ROOT_CAUSE", "No data return for query");
							Environment.put("FAILURE_ERROR","No data return for query");
							MainDriver.incrementDataNotAvailableCount();
							return null;
						}
						continue createNewRequest;
					}else
						return null;
				} else if(responseStatusCode == 503 && response1.getBody().jsonPath().getString("errorMessage").equals("Failed to retrieve price plan from EPC") && tryAltyernatePriceSheetValues==false){
					tryAltyernatePriceSheetValues=true;
					try {
						System.out.println("Query for retrieving first line alternate offercode/pricesheet: "+"select AlternateOfferCode, AlternatePriceSheet from PricePlans where PP_Code='"+firstLinePricePlan+"'"+" and OfferCode is not null");
						rds=conn.executeQuery("select AlternateOfferCode, AlternatePriceSheet from PricePlans where PP_Code='"+firstLinePricePlan+"'"+" and OfferCode is not null");
						rds.next();
					} catch (FilloException e) {
						e.printStackTrace();
						Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving OfferCode/PriceSheet details: "+e.getLocalizedMessage(), "Fail");
						Environment.put("ROOT_CAUSE", "No data return for query");
						Environment.put("FAILURE_ERROR","No data return for query");
						MainDriver.incrementDataNotAvailableCount();
						return null;
					}

					try {
						if(!rds.getField("AlternateOfferCode").isEmpty())
							offerCode1=rds.getField("AlternateOfferCode");
					} catch (FilloException e) {
						e.printStackTrace();
						Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving offer code details: "+e.getLocalizedMessage(), "Fail");
						Environment.put("ROOT_CAUSE", "No data return for query");
						Environment.put("FAILURE_ERROR","No data return for query");
						MainDriver.incrementDataNotAvailableCount();
						return null;
					}
					Dictionary.put("PRICE_PLAN_1",firstLinePricePlan);
					try {if(!rds.getField("AlternatePriceSheet").isEmpty())
						priceSheetCode1=rds.getField("AlternatePriceSheet");
					} catch (FilloException e) {
						e.printStackTrace();
						Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving price sheet details: "+e.getLocalizedMessage(), "Fail");
						Environment.put("ROOT_CAUSE", "No data return for query");
						Environment.put("FAILURE_ERROR","No data return for query");
						MainDriver.incrementDataNotAvailableCount();
						return null;
					}

					try {
						System.out.println("Query for retrieving second line alternate offercode/pricesheet: "+"select AlternateOfferCode, AlternatePriceSheet from PricePlans where PP_Code='"+secondLinePricePlan+"'"+" and OfferCode is not null");
						rds=conn.executeQuery("select AlternateOfferCode, AlternatePriceSheet from PricePlans where PP_Code='"+secondLinePricePlan+"'"+" and OfferCode is not null");
						rds.next();
					} catch (FilloException e) {
						e.printStackTrace();
						Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving OfferCode/PriceSheet details: "+e.getLocalizedMessage(), "Fail");
						Environment.put("ROOT_CAUSE", "No data return for query");
						Environment.put("FAILURE_ERROR","No data return for query");
						MainDriver.incrementDataNotAvailableCount();
						return null;
					}

					try {
						if(!rds.getField("AlternateOfferCode").isEmpty())
							offerCode2=rds.getField("AlternateOfferCode");
					} catch (FilloException e) {
						e.printStackTrace();
						Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving offer code details: "+e.getLocalizedMessage(), "Fail");
						Environment.put("ROOT_CAUSE", "No data return for query");
						Environment.put("FAILURE_ERROR","No data return for query");
						MainDriver.incrementDataNotAvailableCount();
						return null;
					}
					Dictionary.put("PRICE_PLAN_2",secondLinePricePlan);
					try {if(!rds.getField("AlternatePriceSheet").isEmpty())
						priceSheetCode2=rds.getField("AlternatePriceSheet");
					} catch (FilloException e) {
						e.printStackTrace();
						Reporter.fnWriteToHtmlOutput("Create BAN", "BAN is created successfully", "Error in retrieving price sheet details: "+e.getLocalizedMessage(), "Fail");
						Environment.put("ROOT_CAUSE", "No data return for query");
						Environment.put("FAILURE_ERROR","No data return for query");
						MainDriver.incrementDataNotAvailableCount();
						return null;
					}
				}else
					break;
			}while(i<npa.size());

		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("CreateBANUsingMS","Execute CreateBAN MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			JsonPath jp = new JsonPath(response1.getBody().asString());
			String ban = jp.getString("newCreatedAccountNumber");
			System.out.println("BAN: "+ban);
			
			params.put("<BAN>", ban);
			
			rs=dbAction.getDataFromDBWithParameters("BILLING_ACCOUNT_BY_BAN", params);
			
			try {
				if(rs.getString("BAN_STATUS").trim().equals("O")==false){
					Reporter.fnWriteToHtmlOutput("createSingleBANUsingMS","BAN is created successfully", "BAN status: "+rs.getString("BAN_STATUS").trim() ,"Fail");	
					Environment.put("ROOT_CAUSE", "Environment Error");
					Environment.put("FAILURE_ERROR"," ");
					MainDriver.incrementEnvironmentErrorCount();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(!Dictionary.get("COMPLETE_PROFILE").equals("N")) setPinNumberInDB(firstLineImei, user, Password);
			return ban;

		}
		else
		{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("createTwoLineBANUsingMS","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
			
			String jsonResponseString=response1.getBody().asString();
			JSONObject jsonObject = new JSONObject(jsonResponseString);
			Iterator<String> keys=jsonObject.keys();
			String errorMessage=null;
			while(keys.hasNext()){
				String keyName=keys.next();
				System.out.println("Field name: "+jsonObject.get(keyName).toString());
				if(keyName.contains("essage")){
					errorMessage=jsonObject.getString(keyName).toString();
					break;
				}
			}
			
			if(errorMessage.contains("Failed to ") || errorMessage.contains("Error") || errorMessage.contains("error") || errorMessage.contains("is not authorized to perform the activity") || errorMessage.contains("There was a problem logging in") || errorMessage.contains("No route to host") || responseStatusCode==504){
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR",errorMessage);
				MainDriver.incrementEnvironmentErrorCount();
			}else if(errorMessage.contains("mandatory field is missing")){
				Environment.put("ROOT_CAUSE", "No data return for query");
				Environment.put("FAILURE_ERROR","No data return for query");
				MainDriver.incrementDataNotAvailableCount();
			}
			
			return null;
		}

	}

	public boolean cancelBAN(String ban, String user, String Password){
		String cancelBAN_URI=Environment.get("CANCEL_BAN_URI");
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		HashMap<String, String> params = new HashMap<>();

		Dictionary.put("BAN", ban);
		Dictionary.put("USER", user);		
		Dictionary.put("ACTIVITY_CODE", "CAN");
		
		params.put("<BAN>", ban);
		ResultSet rs=dbAction.getDataFromDBWithParameters("BILLING_ACCOUNT_BY_BAN",params);
		
		try {
			if(rs.getString("ACCOUNT_TYPE").trim().equals("I"))
				Dictionary.put("CODE","CR");
			else	
				Dictionary.put("CODE","VC");
		} catch (SQLException e) {
			e.printStackTrace();
			Dictionary.put("CODE","CR");
		}
		
		String templatePath =  System.getProperty("user.dir") + "/Templates" + "/ChangeBanStatus.json";
		reqBody = replaceTemplateWithValues(templatePath);

		String authString = user + ":" + Password;
		byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
		String encodedString = new String(base64Encoded);
		RestAssured.baseURI = Environment.get("URI");
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.contentType("application/json");
		httpRequest.header("Authorization", "Basic " + encodedString);
		Response response = httpRequest.request(Method.GET, "/api/user");

		responseStatusCode = response.getStatusCode();
		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
			System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
		}
		else
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
			Reporter.fnWriteToHtmlOutput("ChangeBANStatusUsingMS","Execute change BAN status MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Fail");
			Environment.put("ROOT_CAUSE", "Environment Error");
			Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("error"));
			MainDriver.incrementEnvironmentErrorCount();
			return false;
		}
		httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

		httpRequest.body(reqBody);
		Reporter.fnWriteToHtmlOutput("ChangeBANStatusUsingMS", "URI", "URI: "+Environment.get("URI")+cancelBAN_URI, "Done");
		Reporter.fnWriteToHtmlOutput("ChangeBANStatusUsingMS", "Request sent", "Request sent: "+reqBody, "Done");
		Response response1 = httpRequest.request(Method.POST, cancelBAN_URI);

		responseStatusCode = response1.getStatusCode();


		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("ChangeBANStatusUsingMS","Execute change BAN status MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			return true;

		}
		else
		{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("ChangeBANStatusUsingMS","Execute change BAN status MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
			
			String jsonResponseString=response1.getBody().asString();
			JSONObject jsonObject = new JSONObject(jsonResponseString);
			Iterator<String> keys=jsonObject.keys();
			String errorMessage=null;
			while(keys.hasNext()){
				String keyName=keys.next();
				System.out.println("Field name: "+jsonObject.get(keyName).toString());
				if(keyName.contains("essage")){
					errorMessage=jsonObject.getString(keyName).toString();
					break;
				}
			}
			
			if(errorMessage.contains("Failed to ") || errorMessage.contains("Error") || errorMessage.contains("error") || errorMessage.contains("is not authorized to perform the activity") || errorMessage.contains("There was a problem logging in") || errorMessage.contains("No route to host") || responseStatusCode==504){
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR",errorMessage);
				MainDriver.incrementEnvironmentErrorCount();
			}else if(errorMessage.contains("mandatory field is missing")){
				Environment.put("ROOT_CAUSE", "No data return for query");
				Environment.put("FAILURE_ERROR","No data return for query");
				MainDriver.incrementDataNotAvailableCount();
			}
			
			return false;
		}
	}

	public boolean suspendBAN(String ban, String user, String Password){
		String cancelBAN_URI=Environment.get("CANCEL_BAN_URI");
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		HashMap<String, String> params = new HashMap<>();

		Dictionary.put("BAN", ban);
		Dictionary.put("USER", user);
		
		params.put("<BAN>", ban);
		ResultSet rs=dbAction.getDataFromDBWithParameters("BILLING_ACCOUNT_BY_BAN",params);
		
		try {
			if(rs.getString("ACCOUNT_TYPE").trim().equals("I"))
				Dictionary.put("CODE","CR");
			else	
				Dictionary.put("CODE","LSTS");
		} catch (SQLException e) {
			e.printStackTrace();
			Dictionary.put("CODE","CR");
		}
		
		Dictionary.put("ACTIVITY_CODE", "SUS");

		String templatePath =  System.getProperty("user.dir") + "/Templates" + "/ChangeBanStatus.json";
		reqBody = replaceTemplateWithValues(templatePath);

		String authString = user + ":" + Password;
		byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
		String encodedString = new String(base64Encoded);
		RestAssured.baseURI = Environment.get("URI");
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.contentType("application/json");
		httpRequest.header("Authorization", "Basic " + encodedString);
		Response response = httpRequest.request(Method.GET, "/api/user");

		responseStatusCode = response.getStatusCode();
		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
			System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
		}
		else
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
			Reporter.fnWriteToHtmlOutput("ChangeBANStatusUsingMS","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Fail");
			Environment.put("ROOT_CAUSE", "Environment Error");
			Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("error"));
			MainDriver.incrementEnvironmentErrorCount();
			return false;
		}
		httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

		httpRequest.body(reqBody);
		Reporter.fnWriteToHtmlOutput("ChangeBANStatusUsingMS", "URI", "URI: "+Environment.get("URI")+cancelBAN_URI, "Done");
		Reporter.fnWriteToHtmlOutput("ChangeBANStatusUsingMS", "Request sent", "Request sent: "+reqBody, "Done");
		Response response1 = httpRequest.request(Method.POST, cancelBAN_URI);

		responseStatusCode = response1.getStatusCode();


		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("ChangeBANStatusUsingMS","Execute change BAN status MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			return true;

		}
		else
		{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("ChangeBANStatusUsingMS","Execute change BAN status MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
			
			String jsonResponseString=response1.getBody().asString();
			JSONObject jsonObject = new JSONObject(jsonResponseString);
			Iterator<String> keys=jsonObject.keys();
			String errorMessage=null;
			while(keys.hasNext()){
				String keyName=keys.next();
				System.out.println("Field name: "+jsonObject.get(keyName).toString());
				if(keyName.contains("essage")){
					errorMessage=jsonObject.getString(keyName).toString();
					break;
				}
			}
			
			if(errorMessage.contains("Failed to ") || errorMessage.contains("Error") || errorMessage.contains("error") || errorMessage.contains("is not authorized to perform the activity") || errorMessage.contains("There was a problem logging in") || errorMessage.contains("No route to host") || responseStatusCode==504){
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR",errorMessage);
				MainDriver.incrementEnvironmentErrorCount();
			}else if(errorMessage.contains("mandatory field is missing")){
				Environment.put("ROOT_CAUSE", "No data return for query");
				Environment.put("FAILURE_ERROR","No data return for query");
				MainDriver.incrementDataNotAvailableCount();
			}
			
			return false;
		}
	}

	public boolean updateSMSMode(String mode){
		String cancelBAN_URI=Environment.get("UPDATE_SMS_MODE");

		Dictionary.put("SMS_MODE", mode.toUpperCase().trim());
		
		String templatePath =  System.getProperty("user.dir") + "/Templates" + "/UpdateSMSMode.json";
		reqBody = replaceTemplateWithValues(templatePath);

		String authString = Environment.get("MS_USER_ID") + ":" + Environment.get("MS_PASSWORD");
		byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
		String encodedString = new String(base64Encoded);
		RestAssured.baseURI = Environment.get("URI");
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.contentType("application/json");
		httpRequest.header("Authorization", "Basic " + encodedString);
		Response response = httpRequest.request(Method.GET, "/api/user");

		responseStatusCode = response.getStatusCode();
		
		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
			System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
		}
		else
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Done");
			Reporter.fnWriteToHtmlOutput("Update SMS mode","Execute update sms mode MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Done");
			return false;
		}
		httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

		httpRequest.body(reqBody);

		Reporter.fnWriteToHtmlOutput("Update SMS mode", "URI", "URI: "+Environment.get("URI")+cancelBAN_URI, "Done");
		Reporter.fnWriteToHtmlOutput("Update SMS mode", "Request sent", "Request sent: "+reqBody, "Done");

		Response response1 = httpRequest.request(Method.POST, cancelBAN_URI);

		responseStatusCode = response1.getStatusCode();


		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("Update SMS mode","Execute CreateBAN MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			return true;

		}
		else
		{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("Update SMS mode","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			if(response1.getBody().asString().contains("Application is not available")){
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR","Application is not available");
				MainDriver.incrementEnvironmentErrorCount();
			}
			return false;
		}
	}
	
	public String GetWalletDetails(String mdn, String user, String Password){
		String walletURI=Environment.get("WALLET");

		Dictionary.put("MDN", mdn);

		String templatePath =  System.getProperty("user.dir") + "/Templates" + "/WalletDetails.json";
		reqBody = replaceTemplateWithValues(templatePath);

		String authString = user + ":" + Password;
		byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
		String encodedString = new String(base64Encoded);
		RestAssured.baseURI = Environment.get("URI");
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.contentType("application/json");
		httpRequest.header("Authorization", "Basic " + encodedString);
		Response response = httpRequest.request(Method.GET, "/api/user");

		responseStatusCode = response.getStatusCode();
		if(responseStatusCode == 200 || responseStatusCode == 201){
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
			System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
		}else{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
			Reporter.fnWriteToHtmlOutput("GetWalletDetails","Execute GetWalletDetails MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Fail");
			Environment.put("ROOT_CAUSE", "Environment Error");
			Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("errorMessage"));
			MainDriver.incrementEnvironmentErrorCount();
			return null;
		}
		httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

		httpRequest.body(reqBody);
		Reporter.fnWriteToHtmlOutput("GetWalletDetails", "URI", "URI: "+Environment.get("URI")+walletURI, "Done");
		Reporter.fnWriteToHtmlOutput("GetWalletDetails", "Request sent", "Request sent: "+reqBody, "Done");
		Response response1 = httpRequest.request(Method.POST, walletURI);

		responseStatusCode = response1.getStatusCode();

		if(responseStatusCode == 200 || responseStatusCode == 201){
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("GetWalletDetails","Execute GetWalletDetails status MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			return response1.getBody().asString();

		}else{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("GetWalletDetails","Execute GetWalletDetails MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
			return null;
		}
	}

	public String getDefaultPlan(String accSubType){
		String pricePlan=null;
		
		switch(String.valueOf(accSubType)){
		case "1": pricePlan= Environment.get("RETAIL_DEFAULT_PLAN");
		break;
		case "2": pricePlan= Environment.get("EMPLOYEE_DEFAULT_PLAN");
		break;
		case "3": pricePlan= Environment.get("DEMO_DEFAULT_PLAN");
		break;
		case "4": pricePlan= Environment.get("TEST_DEFAULT_PLAN");
		break;
		case "6": pricePlan= Environment.get("DEALER_DEFAULT_PLAN");
		break;
		case "7": pricePlan= Environment.get("VIP_DEFAULT_PLAN");
		break;
		case "Y": pricePlan= Environment.get("VIP_DEFAULT_PLAN");
		break;
		default: pricePlan= Environment.get("MILITARY_DEFAULT_PLAN");
		break;
		}
		
		return pricePlan;
	}
	
	public String getSpecificCategoryPricePlanFromDataBase(String pricePlanCategory) {
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		HashMap<String, String> params = new HashMap<>();
		
		String pricePlan=null;
		
		params.put("<SOC_CATEGORY>", pricePlanCategory);
		
		ResultSet rs=dbAction.getDataFromDBWithParameters("GET_SPECIFIC_CATEGORY_PLAN", params);
			
		try {
			pricePlan=rs.getString("SOC").trim();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in retrieving "+pricePlanCategory+" category pan from DB. Error: "+e.getLocalizedMessage());
			return null;
		}
		
		return pricePlan;
	}
	
	public String createAWLBAN(String firstName, String lastName, String emailAddress, String itemID, String user, String Password)
	{
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		CommonFunctions objCommon = new CommonFunctions(driver, driverType, Environment, Reporter);
		HashMap<String, String> params = new HashMap<>();
		
		ResultSet rs=null;
		Response response1=null;
		String accountType=null;
		String securityQuestion=null;
		String sim=null;
		String imei=null;
		String llKey=null;
		int retryCount=0;
		String expDate="20300829";
		String mdn=null;
		String ban=null;
		String orderNumber=null;
		String templatePath = "";	
		String pin=null;
		boolean pinValidated=false;
		String pin1[]=new String[6];
		boolean pinValid=true;
		String createBAN_URI=null;
		String market=Environment.get("MARKET");
		String ptn=null;
						
		params.put("<BRAND_NAME>", "'AWL'");						
		rs=dbAction.getDataFromDBWithParameters("BRAND_INFO", params);
		
		try {
			accountType=rs.getString("ACCOUNT_TYPE").trim();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		rs=dbAction.getDataFromDBWithParameters("SECURITY_QUESTION", params);		
		try {
			do{
				if(rs.getString("ACCOUNT_TYPE").trim().equals(accountType)){
					securityQuestion=rs.getString("QUESTION_CODE").trim();
					break;
				}
			}while(rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		retryCount=0;
				
		while(pinValidated==false){
			pin=objCommon.generateRandomNumber(6);
			
			for(int i=0;i<pin.length();i++)
				pin1[i]=String.valueOf(pin.charAt(i));
			
			pinValid=true;
			
			validatePIN:
			for(int i=0;i<pin.length();i++){
				for(int j=0;j<pin.length();j++){
					if(i!=j){
						if(String.valueOf(pin.charAt(i)).equals(pin1[j])){
							pinValid=false;
							break validatePIN;
						}
						
					}
				}
			}
			
			if(pinValid==true)
				pinValidated=true;
		}
		
		Dictionary.put("FIRST_NAME",firstName);
		Dictionary.put("LAST_NAME",lastName);
		Dictionary.put("SECURITY_QUESTION",securityQuestion);
		Dictionary.put("SECURITY_ANSWER",RandomStringUtils.randomAlphabetic(6).trim());
		Dictionary.put("EMAIL_ADDRESS", emailAddress);
		Dictionary.put("PIN_NUMBER", pin);
		Dictionary.put("EXPIRY_DATE", expDate);
		Dictionary.put("GEO_CODE", objCommon.generateRandomNumber(8));
		
		createAndReserveSubscriber:
		do{	
			try {	
				Recordset rsData = dbAction.getDataFromExcel(System.getProperty("user.dir") + "\\environments\\Environments.xlsx", "Market", "Market='"+market+"'");
				Dictionary.put("STREET", rsData.getField("Street").toUpperCase().trim());
				Dictionary.put("CITY", rsData.getField("City").toUpperCase().trim());
				Dictionary.put("STATE_CODE", rsData.getField("State_code").toUpperCase().trim());
				Dictionary.put("ZIP", rsData.getField("ZipCode").trim());
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("Failed to get market information from Excel");
			}
			
			pin=objCommon.generateRandomNumber(6);
			createBAN_URI=Environment.get("AWL_CREATE_ACCOUNT_AND_RESERVE_SUBSCRIBER_URI");
			
			while(retryCount<10){
				llKey="JOUKO"+objCommon.generateRandomNumber(5)+RandomStringUtils.randomAlphabetic(2).toLowerCase();
				
				params.put("<LL_KEY>", llKey);
				rs=dbAction.getDataFromDBWithParameters("GET_LIFELINE_KEY_ENTRIES", params);
				try {
					if(rs.getString("numberOfRecords").trim().equals("0"))
						break;
					else
						retryCount++;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			Dictionary.put("LIFE_LINE_KEY", llKey);
			
			if(Dictionary.get("TRIBAL_IND").equalsIgnoreCase("YES"))
				Dictionary.put("TRIBAL_PLACE_HOLDER", "\"specialOfferType\": \"T\",");
			else
				Dictionary.put("TRIBAL_PLACE_HOLDER", "");
			
			templatePath =  System.getProperty("user.dir") + "/Templates" + "/CreateAccountAndReserveSubscriber.json";
			reqBody = replaceTemplateWithValues(templatePath);

			String authString = user + ":" + Password;
			byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
			String encodedString = new String(base64Encoded);
			RestAssured.baseURI = Environment.get("URI");
			RequestSpecification httpRequest = RestAssured.given();
			httpRequest.contentType("application/json");
			httpRequest.header("Authorization", "Basic " + encodedString);
			Response response = httpRequest.request(Method.GET, "/api/user");

			responseStatusCode = response.getStatusCode();
			if(responseStatusCode == 200 || responseStatusCode == 201)
			{
				Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
				System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
			}else
			{
				Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
				Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Fail");
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("error"));
				MainDriver.incrementEnvironmentErrorCount();
				return null;
			}
			httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

			httpRequest.body(reqBody);
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "URI", "URI: "+Environment.get("URI")+createBAN_URI, "Done");
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "Request sent", "Request sent: "+reqBody, "Done");
			response1 = httpRequest.request(Method.POST, createBAN_URI);

			responseStatusCode = response1.getStatusCode();
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute CreateBAN MS", "MS executed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");

			if(responseStatusCode == 200 || responseStatusCode == 201)
			{
				System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
				Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute CreateBAN MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
				JsonPath jp = new JsonPath(response1.getBody().asString());
				ban=jp.getString("accountNumber");
				mdn=jp.getString("mdn");
				orderNumber=jp.getString("orderNumber");
				System.out.println("BAN: "+ban);
				System.out.println("MDN: "+mdn);
				System.out.println("Order Number: "+orderNumber);
				System.out.println("Subscriber Number: "+jp.getString("subscriberId"));
				if(Dictionary.get("RESERVE_ACCOUNT_ONLY").equalsIgnoreCase("YES"))
					return ban;
			}else if(response1.getBody().jsonPath().getString("errorMessage").contains("There are not enough available PTN resources")){
				market=Environment.get("ALTERNATE_MARKET");
				retryCount++;
				continue createAndReserveSubscriber;
			}else if(response1.getBody().jsonPath().getString("errorMessage").contains("is already in use")){
				ptn=response1.getBody().jsonPath().getString("errorMessage").replaceAll("[^0-9]","").trim();
				params.put("<PTN_STATUS>", "AI");
				params.put("<NPA>", String.valueOf(ptn.charAt(0))+String.valueOf(ptn.charAt(1))+String.valueOf(ptn.charAt(2)));
				params.put("<NXX>", String.valueOf(ptn.charAt(3))+String.valueOf(ptn.charAt(4))+String.valueOf(ptn.charAt(5)));
				params.put("<LINE_NUMBER>", String.valueOf(ptn.charAt(6))+String.valueOf(ptn.charAt(7))+String.valueOf(ptn.charAt(8))+String.valueOf(ptn.charAt(9)));
				dbAction.executeUpdateQueryAndCommitWithParameters("UPDATE_PTN_STATUS",params);
				retryCount++;
				continue createAndReserveSubscriber;
			}else
			{
				System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
				String jsonResponseString=response1.getBody().asString();
				JSONObject jsonObject = new JSONObject(jsonResponseString);
				Iterator<String> keys=jsonObject.keys();
				String errorMessage=null;
				while(keys.hasNext()){
					String keyName=keys.next();
					System.out.println("Field name: "+jsonObject.get(keyName).toString());
					if(keyName.contains("essage")){
						errorMessage=jsonObject.getString(keyName).toString();
						break;
					}
				}
			
				if(errorMessage.contains("PIN cannot have more than 2 consecutive")==true){
					retryCount++;
					Dictionary.put("PIN_NUMBER", objCommon.generateRandomNumber(6));
					continue createAndReserveSubscriber;
				}
				
				Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
				
				if(errorMessage.contains("Failed to") || errorMessage.contains("Error") || errorMessage.contains("error") || errorMessage.contains("is not authorized to perform the activity") || errorMessage.contains("There was a problem logging in") || errorMessage.contains("No route to host") || responseStatusCode==504 || errorMessage.contains("There are not enough available PTN resources") || errorMessage.contains("is already in use")){
					Environment.put("ROOT_CAUSE", "Environment Error");
					Environment.put("FAILURE_ERROR",errorMessage);
					MainDriver.incrementEnvironmentErrorCount();
				}
				return null;
			}
//		}while(retryCount<5);
		
		params.put("<BRAND_NAME>", "'METRO' or brand_name = 'AWL' or brand_name is null");
		
		if(itemID.isEmpty() || itemID.equalsIgnoreCase("any"))
			itemID=Environment.get("AWL_ITEM_ID");
		
		params.put("{ITEM_ID}", itemID);
		rs=dbAction.getDataFromDBWithParameters("DEVICE_NUMBER", params);
	    try {
			imei=rs.getString("SERIAL_NUMBER").trim();
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "AWL BAN is created", "Error while retrieving IMEI: "+e1.getMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}
	    
	    rs=dbAction.getDataFromDBWithParameters("SIM_NUMBER", params);
	    try {
			sim=rs.getString("SERIAL_NUMBER").trim();
		} catch (SQLException e1) {
			e1.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "AWL BAN is created", "Error while retrieving SIM: "+e1.getMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}
	    
	    params.put("<BRAND_NAME>", "'AWL'");
		params.put("<SIM>", sim);
		params.put("<IMEI>", imei);
		
		if(dbAction.executeUpdateQueryAndCommitWithParameters("SET_DEVICE_FOR_AWL", params)==false){
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "AWL BAN is created", "Error while setting up device for AWL brand", "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}
		
		params.put("{ITEM_ID}", Environment.get("AWL_SIM_ITEM_ID"));
		if(dbAction.executeUpdateQueryAndCommitWithParameters("SET_SIM_FOR_AWL", params)==false){
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "AWL BAN is created", "Error while setting up sim for AWL brand", "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}
		
		createBAN_URI=Environment.get("ACTIVATE_RESERVED_SUBSCRIBER_URI");
		
		Dictionary.put("MDN_NO", mdn);
		Dictionary.put("ORDER_ID", orderNumber);
		Dictionary.put("DEVICE_NUMBER", imei);
		
		if(Dictionary.get("PLAN_TYPE").equalsIgnoreCase("HOLDING"))
			Dictionary.put("DEVICE_ORDER_TYPE", "shipped");
		else
			Dictionary.put("DEVICE_ORDER_TYPE", "immediate");
		
		templatePath =  System.getProperty("user.dir") + "/Templates" + "/ActivateReservedSubscriber.json";
		reqBody = replaceTemplateWithValues(templatePath);
				
		authString = user + ":" + Password;
		base64Encoded = Base64.getEncoder().encode(authString.getBytes());
		encodedString = new String(base64Encoded);
		httpRequest = RestAssured.given();
		httpRequest.contentType("application/json");
		httpRequest.header("Authorization", "Basic " + encodedString);
		response = httpRequest.request(Method.GET, "/api/user");

		responseStatusCode = response.getStatusCode();
		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Pass");
			System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
		}
		else
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
			Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Fail");
			Environment.put("ROOT_CAUSE", "Environment Error");
			Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("error"));
			MainDriver.incrementEnvironmentErrorCount();
			return null;
		}
		httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

		httpRequest.body(reqBody);
		Reporter.fnWriteToHtmlOutput("Create AWL BAN", "URI", "URI: "+Environment.get("URI")+createBAN_URI, "Done");
		Reporter.fnWriteToHtmlOutput("Create AWL BAN", "Request sent", "Request sent: "+reqBody, "Done");
		response1 = httpRequest.request(Method.POST, createBAN_URI);

		responseStatusCode = response1.getStatusCode();
		System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
		Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute CreateBAN MS", "MS executed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");

		if(responseStatusCode==400 && response1.body().jsonPath().getString("errorMessage").contains("Subscriber activation has failed")==true){
			retryCount++;
		}
		else
			break createAndReserveSubscriber;
		}while(retryCount<5);
		
		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute CreateBAN MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			JsonPath jp = new JsonPath(response1.getBody().asString());
			ban=jp.getString("accountNumber");
			System.out.println("BAN: "+ban);
			System.out.println("Price plan: "+jp.getString("pricePlanCode"));
			return ban;
		}
		else
		{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("createSingleBANUsingMS","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");

			String jsonResponseString=response1.getBody().asString();
			JSONObject jsonObject = new JSONObject(jsonResponseString);
			Iterator<String> keys=jsonObject.keys();
			String errorMessage=null;
			while(keys.hasNext()){
				String keyName=keys.next();
				System.out.println("Field name: "+jsonObject.get(keyName).toString());
				if(keyName.contains("essage")){
					errorMessage=jsonObject.getString(keyName).toString();
					break;
				}
			}
			
			if(errorMessage.contains("Failed to") || errorMessage.contains("Error") || errorMessage.contains("error") || errorMessage.contains("is not authorized to perform the activity") || errorMessage.contains("There was a problem logging in") || errorMessage.contains("Subscriber activation has failed") || errorMessage.contains("No route to host") || responseStatusCode==504){
				Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR",errorMessage);
				MainDriver.incrementEnvironmentErrorCount();
			}
			return null;
		}
	}
	
	public String GetDeviceHistory(String imei){
		String deviceHistoryURI=Environment.get("GET_DEVICE_ACTIVITY_DAYS_URI");

		Dictionary.put("IMEI", imei);

		String templatePath =  System.getProperty("user.dir") + "/Templates" + "/DeviceHistory.json";
		reqBody = replaceTemplateWithValues(templatePath);

		String authString = Environment.get("MS_USER_ID") + ":" + Environment.get("MS_PASSWORD");
		byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
		String encodedString = new String(base64Encoded);
		RestAssured.baseURI = Environment.get("URI");
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.contentType("application/json");
		httpRequest.header("Authorization", "Basic " + encodedString);
		Response response = httpRequest.request(Method.GET, "/api/user");

		responseStatusCode = response.getStatusCode();
		if(responseStatusCode == 200 || responseStatusCode == 201){
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
			System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
		}else{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
			Reporter.fnWriteToHtmlOutput("GetDeviceHistory","Execute GetDeviceHistory MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Fail");
			Environment.put("ROOT_CAUSE", "Environment Error");
			Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("errorMessage"));
			MainDriver.incrementEnvironmentErrorCount();
			return null;
		}
		httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

		httpRequest.body(reqBody);
		Reporter.fnWriteToHtmlOutput("GetDeviceHistory", "URI", "URI: "+Environment.get("URI")+deviceHistoryURI, "Done");
		Reporter.fnWriteToHtmlOutput("GetDeviceHistory", "Request sent", "Request sent: "+reqBody, "Done");
		Response response1 = httpRequest.request(Method.POST, deviceHistoryURI);

		responseStatusCode = response1.getStatusCode();

		if(responseStatusCode == 200 || responseStatusCode == 201){
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("GetDeviceHistory","Execute GetDeviceHistory status MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			return response1.getBody().asString();

		}else{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("GetDeviceHistory","Execute GetDeviceHistory MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
			
			String jsonResponseString=response1.getBody().asString();
			JSONObject jsonObject = new JSONObject(jsonResponseString);
			Iterator<String> keys=jsonObject.keys();
			String errorMessage=null;
			while(keys.hasNext()){
				String keyName=keys.next();
				System.out.println("Field name: "+jsonObject.get(keyName).toString());
				if(keyName.contains("essage")){
					errorMessage=jsonObject.getString(keyName).toString();
					break;
				}
			}
			
			if(errorMessage.contains("Failed to ") || errorMessage.contains("Error") || errorMessage.contains("error") || errorMessage.contains("is not authorized to perform the activity") || errorMessage.contains("There was a problem logging in")){
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR",errorMessage);
				MainDriver.incrementEnvironmentErrorCount();
			}else if(errorMessage.contains("mandatory field is missing")){
				Environment.put("ROOT_CAUSE", "No data return for query");
				Environment.put("FAILURE_ERROR","No data return for query");
				MainDriver.incrementDataNotAvailableCount();
			}
			
			return null;
		}
	}
	
	public String GetDeviceStatus(String imei){
		String deviceStatusURI=Environment.get("GET_DEVICE_STATUS_URI");

		Dictionary.put("IMEI", imei);

		String templatePath =  System.getProperty("user.dir") + "/Templates" + "/DeviceHistory.json";
		reqBody = replaceTemplateWithValues(templatePath);

		String authString = Environment.get("MS_USER_ID") + ":" + Environment.get("MS_PASSWORD");
		byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
		String encodedString = new String(base64Encoded);
		RestAssured.baseURI = Environment.get("URI");
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.contentType("application/json");
		httpRequest.header("Authorization", "Basic " + encodedString);
		Response response = httpRequest.request(Method.GET, "/api/user");

		responseStatusCode = response.getStatusCode();
		if(responseStatusCode == 200 || responseStatusCode == 201){
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
			System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
		}else{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
			Reporter.fnWriteToHtmlOutput("GetDeviceHistory","Execute GetDeviceHistory MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Fail");
			Environment.put("ROOT_CAUSE", "Environment Error");
			Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("errorMessage"));
			MainDriver.incrementEnvironmentErrorCount();
			return null;
		}
		httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

		httpRequest.body(reqBody);
		Reporter.fnWriteToHtmlOutput("GetDeviceHistory", "URI", "URI: "+Environment.get("URI")+deviceStatusURI, "Done");
		Reporter.fnWriteToHtmlOutput("GetDeviceHistory", "Request sent", "Request sent: "+reqBody, "Done");
		Response response1 = httpRequest.request(Method.POST, deviceStatusURI);

		responseStatusCode = response1.getStatusCode();

		if(responseStatusCode == 200 || responseStatusCode == 201){
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("GetDeviceHistory","Execute GetDeviceHistory status MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			return response1.getBody().asString();

		}else{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("GetDeviceHistory","Execute GetDeviceHistory MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
			
			String jsonResponseString=response1.getBody().asString();
			JSONObject jsonObject = new JSONObject(jsonResponseString);
			Iterator<String> keys=jsonObject.keys();
			String errorMessage=null;
			while(keys.hasNext()){
				String keyName=keys.next();
				System.out.println("Field name: "+jsonObject.get(keyName).toString());
				if(keyName.contains("essage")){
					errorMessage=jsonObject.getString(keyName).toString();
					break;
				}
			}
			
			if(errorMessage.contains("Failed to ") || errorMessage.contains("Error") || errorMessage.contains("error") || errorMessage.contains("is not authorized to perform the activity") || errorMessage.contains("There was a problem logging in")){
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR",errorMessage);
				MainDriver.incrementEnvironmentErrorCount();
			}else if(errorMessage.contains("mandatory field is missing")){
				Environment.put("ROOT_CAUSE", "No data return for query");
				Environment.put("FAILURE_ERROR","No data return for query");
				MainDriver.incrementDataNotAvailableCount();
			}
			
			return null;
		}
	}

	public boolean updatePrepaidBalance(String mdn, String balance, String user, String Password){
		String updatePrepaidBalance_URI=Environment.get("UPDATE_PREPAID_BALANCE");

		Dictionary.put("MDN_NO", mdn);
		Dictionary.put("NEW_BALANCE", balance);
		Dictionary.put("MSG_ID", RandomStringUtils.randomAlphabetic(6).toLowerCase());

		String templatePath =  System.getProperty("user.dir") + "/Templates" + "/UpdatePrepaidBalance.json";
		reqBody = replaceTemplateWithValues(templatePath);
		
		String authString = user + ":" + Password;
		byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
		String encodedString = new String(base64Encoded);
		RestAssured.baseURI = Environment.get("URI");
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.contentType("application/json");
		httpRequest.header("Authorization", "Basic " + encodedString);
		Response response = httpRequest.request(Method.GET, "/api/user");

		responseStatusCode = response.getStatusCode();
		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
			System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
		}
		else
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
			Reporter.fnWriteToHtmlOutput("UpdatePrepaidBalanceMS","Execute update prepaid balance MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Fail");
			Environment.put("ROOT_CAUSE", "Environment Error");
			Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("errorMessage"));
			MainDriver.incrementEnvironmentErrorCount();
			return false;
		}
		httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

		httpRequest.body(reqBody);
		Reporter.fnWriteToHtmlOutput("UpdatePrepaidBalanceMS", "URI", "URI: "+Environment.get("URI")+updatePrepaidBalance_URI, "Done");
		Reporter.fnWriteToHtmlOutput("UpdatePrepaidBalanceMS", "Request sent", "Request sent: "+reqBody, "Done");
		Response response1 = httpRequest.request(Method.POST, updatePrepaidBalance_URI);

		responseStatusCode = response1.getStatusCode();

		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("UpdatePrepaidBalanceMS","Execute update prepaid balance MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			return true;
		}
		else
		{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("UpdatePrepaidBalanceMS","Execute update prepaid balance MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
			
			String jsonResponseString=response1.getBody().asString();
			JSONObject jsonObject = new JSONObject(jsonResponseString);
			Iterator<String> keys=jsonObject.keys();
			String errorMessage=null;
			while(keys.hasNext()){
				String keyName=keys.next();
				System.out.println("Field name: "+jsonObject.get(keyName).toString());
				if(keyName.contains("essage")){
					errorMessage=jsonObject.getString(keyName).toString();
					break;
				}
			}
			
			if(errorMessage.contains("Failed to") || errorMessage.contains("Error") || errorMessage.contains("error") || errorMessage.contains("is not authorized to perform the activity") || errorMessage.contains("There was a problem logging in") || errorMessage.contains("Subscriber activation has failed") || errorMessage.contains("No route to host") || responseStatusCode==504){
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR",errorMessage);
				MainDriver.incrementEnvironmentErrorCount();
			}else if(errorMessage.contains("mandatory field is missing")){
				Environment.put("ROOT_CAUSE", "No data return for query");
				Environment.put("FAILURE_ERROR","No data return for query");
				MainDriver.incrementDataNotAvailableCount();
			}
			
			return false;
		}
	}

	public boolean createAWLBANWithBlackListedDevice(String firstName, String lastName, String emailAddress, String itemID, String user, String Password)
	{
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		CommonFunctions objCommon = new CommonFunctions(driver, driverType, Environment, Reporter);
		HashMap<String, String> params = new HashMap<>();
		
		ResultSet rs=null;
		Response response1=null;
		String accountType=null;
		String securityQuestion=null;
		String sim=null;
		String imei=null;
		String llKey=null;
		int retryCount=0;
		String expDate="20200829";
		String mdn=null;
		String ban=null;
		String orderNumber=null;
		String templatePath = "";
		String errormsg=null;
		String errorcode=null;
				
		params.put("<BRAND_NAME>", "'AWL'");						
		rs=dbAction.getDataFromDBWithParameters("BRAND_INFO", params);
		
		try {
			accountType=rs.getString("ACCOUNT_TYPE").trim();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		rs=dbAction.getDataFromDBWithParameters("SECURITY_QUESTION", params);		
		try {
			do{
				if(rs.getString("ACCOUNT_TYPE").trim().equals(accountType)){
					securityQuestion=rs.getString("QUESTION_CODE").trim();
					break;
				}
			}while(rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		while(retryCount<10){
			llKey="JOUKO"+objCommon.generateRandomNumber(5)+RandomStringUtils.randomAlphabetic(2).toLowerCase();
			
			params.put("<LL_KEY>", llKey);
			rs=dbAction.getDataFromDBWithParameters("GET_LIFELINE_KEY_ENTRIES", params);
			try {
				if(rs.getString("numberOfRecords").trim().equals("0"))
					break;
				else
					retryCount++;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		retryCount=0;
		String createBAN_URI=Environment.get("AWL_CREATE_ACCOUNT_AND_RESERVE_SUBSCRIBER_URI");
		
		Dictionary.put("FIRST_NAME",firstName);
		Dictionary.put("LAST_NAME",lastName);
		Dictionary.put("SECURITY_QUESTION",securityQuestion);
		Dictionary.put("SECURITY_ANSWER",RandomStringUtils.randomAlphabetic(6).trim());
		Dictionary.put("EMAIL_ADDRESS", emailAddress);
		Dictionary.put("PIN_NUMBER", objCommon.generateRandomNumber(6));
		Dictionary.put("LIFE_LINE_KEY", llKey);
		Dictionary.put("EXPIRY_DATE", expDate);
		Dictionary.put("GEO_CODE", objCommon.generateRandomNumber(8));
		
		createAndReserveSubscriber:
		do{	
			if(Dictionary.get("TRIBAL_IND").equalsIgnoreCase("YES"))
				Dictionary.put("TRIBAL_PLACE_HOLDER", "\"specialOfferType\": \"T\",");
			else
				Dictionary.put("TRIBAL_PLACE_HOLDER", "");
			
			templatePath =  System.getProperty("user.dir") + "/Templates" + "/CreateAccountAndReserveSubscriber.json";
			reqBody = replaceTemplateWithValues(templatePath);

			String authString = user + ":" + Password;
			byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
			String encodedString = new String(base64Encoded);
			RestAssured.baseURI = Environment.get("URI");
			RequestSpecification httpRequest = RestAssured.given();
			httpRequest.contentType("application/json");
			httpRequest.header("Authorization", "Basic " + encodedString);
			Response response = httpRequest.request(Method.GET, "/api/user");

			responseStatusCode = response.getStatusCode();
			if(responseStatusCode == 200 || responseStatusCode == 201)
			{
				Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
				System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
			}
			else
			{
				Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
				Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Fail");
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("errorMessage"));
				MainDriver.incrementEnvironmentErrorCount();
				return false;
			}
			httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

			httpRequest.body(reqBody);
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "URI", "URI: "+Environment.get("URI")+createBAN_URI, "Done");
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "Request sent", "Request sent: "+reqBody, "Done");
			response1 = httpRequest.request(Method.POST, createBAN_URI);

			responseStatusCode = response1.getStatusCode();
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute CreateBAN MS", "MS executed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");

			if(responseStatusCode == 200 || responseStatusCode == 201)
			{
				System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
				Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute CreateBAN MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
				JsonPath jp = new JsonPath(response1.getBody().asString());
				ban=jp.getString("accountNumber");
				mdn=jp.getString("mdn");
				orderNumber=jp.getString("orderNumber");
				System.out.println("BAN: "+ban);
				System.out.println("MDN: "+mdn);
				System.out.println("Order Number: "+orderNumber);
				System.out.println("Subscriber Number: "+jp.getString("subscriberId"));
				if(Dictionary.get("RESERVE_ACCOUNT_ONLY").equalsIgnoreCase("YES"))
					return true;
				break createAndReserveSubscriber;
			}
			else
			{
				System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
				Reporter.fnWriteToHtmlOutput("createSingleBANUsingMS","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
				if(response1.getBody().jsonPath().getString("errorMessage").contains("PIN cannot have more than 2 consecutive")==true){
					retryCount++;
					Dictionary.put("PIN_NUMBER", objCommon.generateRandomNumber(6));
				}else if(response1.getBody().jsonPath().getString("errorMessage").contains("Failed to create transaction to MICRO SERVICE") || response1.getBody().jsonPath().getString("errorMessage").contains("Error") || response1.getBody().jsonPath().getString("errorMessage").contains("error")){
					
					String jsonResponseString=response1.getBody().asString();
					JSONObject jsonObject = new JSONObject(jsonResponseString);
					Iterator<String> keys=jsonObject.keys();
					String errorMessage=null;
					while(keys.hasNext()){
						String keyName=keys.next();
						System.out.println("Field name: "+jsonObject.get(keyName).toString());
						if(keyName.contains("essage")){
							errorMessage=jsonObject.getString(keyName).toString();
							break;
						}
					}
					
					if(errorMessage.contains("Failed to ") || errorMessage.contains("Error") || errorMessage.contains("error") || errorMessage.contains("is not authorized to perform the activity") || errorMessage.contains("There was a problem logging in")){
						Environment.put("ROOT_CAUSE", "Environment Error");
						Environment.put("FAILURE_ERROR",errorMessage);
						MainDriver.incrementEnvironmentErrorCount();
					}else if(errorMessage.contains("mandatory field is missing")){
						Environment.put("ROOT_CAUSE", "No data return for query");
						Environment.put("FAILURE_ERROR","No data return for query");
						MainDriver.incrementDataNotAvailableCount();
					}
					
					return false;
				}else
					return false;
			}
		}while(retryCount<5);
		
		params.put("<BRAND_NAME>", "'METRO' or brand_name='AWL'");
		
		if(itemID.isEmpty() || itemID.equalsIgnoreCase("any"))
			itemID=Environment.get("AWL_ITEM_ID");
		
		
		rs=dbAction.getDataFromDB("GET_BLOCKED_DEVICE_FOR_AWL");
	    try {
			imei=rs.getString("SERIAL_NUMBER").trim();
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "AWL BAN is created", "Error while retrieving IMEI: "+e1.getMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return false;
		}
	    
	    rs=dbAction.getDataFromDBWithParameters("SIM_NUMBER", params);
	    try {
			sim=rs.getString("SERIAL_NUMBER").trim();
		} catch (SQLException e1) {
			e1.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "AWL BAN is created", "Error while retrieving SIM: "+e1.getMessage(), "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return false;
		}
	    
	    params.put("<BRAND_NAME>", "'AWL'");
		params.put("<SIM>", sim);
		params.put("<IMEI>", imei);
		
		if(dbAction.executeUpdateQueryAndCommitWithParameters("SET_DEVICE_FOR_AWL", params)==false){
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "AWL BAN is created", "Error while setting up device for AWL brand", "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return false;
		}
		
		if(dbAction.executeUpdateQueryAndCommitWithParameters("SET_SIM_FOR_AWL", params)==false){
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "AWL BAN is created", "Error while setting up sim for AWL brand", "Fail");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return false;
		}
		
		createBAN_URI=Environment.get("ACTIVATE_RESERVED_SUBSCRIBER_URI");
		
		Dictionary.put("MDN_NO", mdn);
		Dictionary.put("ORDER_ID", orderNumber);
		Dictionary.put("DEVICE_NUMBER", imei);
		
		if(Dictionary.get("PLAN_TYPE").equalsIgnoreCase("HOLDING"))
			Dictionary.put("DEVICE_ORDER_TYPE", "shipped");
		else
			Dictionary.put("DEVICE_ORDER_TYPE", "immediate");
		
		templatePath =  System.getProperty("user.dir") + "/Templates" + "/ActivateReservedSubscriber.json";
		reqBody = replaceTemplateWithValues(templatePath);
		
		
		String authString = user + ":" + Password;
		byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
		String encodedString = new String(base64Encoded);
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.contentType("application/json");
		httpRequest.header("Authorization", "Basic " + encodedString);
		Response response = httpRequest.request(Method.GET, "/api/user");

		responseStatusCode = response.getStatusCode();
		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Pass");
			System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
		}
		else
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Fail");
			Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Fail");
			return false;
		}
		httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

		httpRequest.body(reqBody);
		Reporter.fnWriteToHtmlOutput("Create AWL BAN", "URI", "URI: "+Environment.get("URI")+createBAN_URI, "Done");
		Reporter.fnWriteToHtmlOutput("Create AWL BAN", "Request sent", "Request sent: "+reqBody, "Done");
		response1 = httpRequest.request(Method.POST, createBAN_URI);

		responseStatusCode = response1.getStatusCode();
		System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
		Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute CreateBAN MS", "MS executed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");

		if(responseStatusCode == 400)
		{
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute CreateBAN MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			JsonPath jp = new JsonPath(response1.getBody().asString());
			errorcode=jp.getString("errorCode");
			errormsg=jp.getString("errorMessage");
			if(errormsg.equals("imei is not valid.")==true)
				Reporter.fnWriteToHtmlOutput("Verify Error Message is displayed ", "Verify Error Message is displayed", "Verify Error Message is displayed "+":"+errormsg,"Pass");
			else
				Reporter.fnWriteToHtmlOutput("Verify Error Message is displayed ", "Verify Error Message is displayed", "Verify Error Message is not  displayed "+":"+errormsg, "Fail");
			if(errorcode.equals("100000025")==true)
				Reporter.fnWriteToHtmlOutput("Verify Error Code is displayed ", "Verify  Error Code is displayed", "Verify  Error Code is displayed "+":"+errorcode,"Pass");
			else
				Reporter.fnWriteToHtmlOutput("Verify  Error Code is displayed ", "Verify Error Code is displayed", "Verify  Error Code is not  displayed "+":"+errorcode, "Fail");

				
		}
		else
		{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("createSingleBANUsingMS","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Fail");
			
			String jsonResponseString=response1.getBody().asString();
			JSONObject jsonObject = new JSONObject(jsonResponseString);
			Iterator<String> keys=jsonObject.keys();
			String errorMessage=null;
			while(keys.hasNext()){
				String keyName=keys.next();
				System.out.println("Field name: "+jsonObject.get(keyName).toString());
				if(keyName.contains("essage")){
					errorMessage=jsonObject.getString(keyName).toString();
					break;
				}
			}
			
			
			
			return false;
		}
		return true;
	}
	
	
	//Developed by Varam(copied from CreateAWLBAN and modified as per test case requirement.
	public String createAWLBANValidateResponse(String firstName, String lastName, String emailAddress, String itemID, String user, String Password)
	{
		DatabaseOperations dbAction = new DatabaseOperations(Dictionary, Environment, Reporter);
		CommonFunctions objCommon = new CommonFunctions(driver, driverType, Environment, Reporter);
		HashMap<String, String> params = new HashMap<>();
		
		ResultSet rs=null;
		Response response1=null;
		String accountType=null;
		String securityQuestion=null;
		String sim=null;
		String imei=null;
		String llKey=null;
		int retryCount=0;
		String expDate="20300829";
		String mdn=null;
		String ban=null;
		String orderNumber=null;
		String templatePath = "";	
		String pin=null;
		boolean pinValidated=false;
		String pin1[]=new String[6];
		boolean pinValid=true;
				
		params.put("<BRAND_NAME>", "'AWL'");						
		rs=dbAction.getDataFromDBWithParameters("BRAND_INFO", params);
		
		try {
			accountType=rs.getString("ACCOUNT_TYPE").trim();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		rs=dbAction.getDataFromDBWithParameters("SECURITY_QUESTION", params);		
		try {
			do{
				if(rs.getString("ACCOUNT_TYPE").trim().equals(accountType)){
					securityQuestion=rs.getString("QUESTION_CODE").trim();
					break;
				}
			}while(rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		while(retryCount<10){
			llKey="JOUKO"+objCommon.generateRandomNumber(5)+RandomStringUtils.randomAlphabetic(2).toLowerCase();
			
			params.put("<LL_KEY>", llKey);
			rs=dbAction.getDataFromDBWithParameters("GET_LIFELINE_KEY_ENTRIES", params);
			try {
				if(rs.getString("numberOfRecords").trim().equals("0"))
					break;
				else
					retryCount++;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		retryCount=0;
		String createBAN_URI=Environment.get("AWL_CREATE_ACCOUNT_AND_RESERVE_SUBSCRIBER_URI");
		
		while(pinValidated==false){
			pin=objCommon.generateRandomNumber(6);
			
			for(int i=0;i<pin.length();i++)
				pin1[i]=String.valueOf(pin.charAt(i));
			
			pinValid=true;
			
			validatePIN:
			for(int i=0;i<pin.length();i++){
				for(int j=0;j<pin.length();j++){
					if(i!=j){
						if(String.valueOf(pin.charAt(i)).equals(pin1[j])){
							pinValid=false;
							break validatePIN;
						}
						
					}
				}
			}
			
			if(pinValid==true)
				pinValidated=true;
		}
		
		Dictionary.put("FIRST_NAME",firstName);
		Dictionary.put("LAST_NAME",lastName);
		Dictionary.put("SECURITY_QUESTION",securityQuestion);
		Dictionary.put("SECURITY_ANSWER",RandomStringUtils.randomAlphabetic(6).trim());
		Dictionary.put("EMAIL_ADDRESS", emailAddress);
		Dictionary.put("PIN_NUMBER", pin);
		Dictionary.put("LIFE_LINE_KEY", llKey);
		Dictionary.put("EXPIRY_DATE", expDate);
		Dictionary.put("GEO_CODE", objCommon.generateRandomNumber(8));
		
		createAndReserveSubscriber:
		do{	
			pin=objCommon.generateRandomNumber(6);
			
			if(Dictionary.get("TRIBAL_IND").equalsIgnoreCase("YES"))
				Dictionary.put("TRIBAL_PLACE_HOLDER", "\"specialOfferType\": \"T\",");
			else
				Dictionary.put("TRIBAL_PLACE_HOLDER", "");
			
			templatePath =  System.getProperty("user.dir") + "/Templates" + "/CreateAccountAndReserveSubscriber.json";
			reqBody = replaceTemplateWithValues(templatePath);
			String authString = user + ":" + Password;
			
			byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
			String encodedString = new String(base64Encoded);
			RestAssured.baseURI = Environment.get("URI");
			RequestSpecification httpRequest = RestAssured.given();
			httpRequest.contentType("application/json");
			httpRequest.header("Authorization", "Basic " + encodedString);
			Response response = httpRequest.request(Method.GET, "/api/user");

			responseStatusCode = response.getStatusCode();
			if(responseStatusCode == 200 || responseStatusCode == 201)
			{
				Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Done");
				System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
			}
			else
			{
				Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Done");
				Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Done");
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("error"));
				MainDriver.incrementEnvironmentErrorCount();
				//Added by Varam to validate response	
				Dictionary.put("RESPONSE_VALUE", response.getBody().asString());
				return  response.getBody().asString();
			}
			httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

			httpRequest.body(reqBody);
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "URI", "URI: "+Environment.get("URI")+createBAN_URI, "Done");
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "Request sent", "Request sent: "+reqBody, "Done");
			response1 = httpRequest.request(Method.POST, createBAN_URI);

			responseStatusCode = response1.getStatusCode();
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute CreateBAN MS", "MS executed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");

			if(responseStatusCode == 200 || responseStatusCode == 201)
			{
				System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
				Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute CreateBAN MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
				JsonPath jp = new JsonPath(response1.getBody().asString());
				ban=jp.getString("accountNumber");
				mdn=jp.getString("mdn");
				orderNumber=jp.getString("orderNumber");
				System.out.println("BAN: "+ban);
				System.out.println("MDN: "+mdn);
				System.out.println("Order Number: "+orderNumber);
				System.out.println("Subscriber Number: "+jp.getString("subscriberId"));
				if(Dictionary.get("RESERVE_ACCOUNT_ONLY").equalsIgnoreCase("YES"))
					//Added by Varam to validate response	
					
					 //return response.getBody().asString();
				Dictionary.put("RESPONSE_VALUE", response1.getBody().asString());
				break createAndReserveSubscriber;
			}
			else
			{
				System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
				Reporter.fnWriteToHtmlOutput("createSingleBANUsingMS","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
				//if(response1.getBody().jsonPath().getString("errorMessage").contains("PIN cannot have more than 2 consecutive")==true){
					//retryCount++;
					//Dictionary.put("PIN_NUMBER", objCommon.generateRandomNumber(6));
				//}else
					//onment.put("ROOT_CAUSE", "Environment Error");
					//Environment.put("FAILURE_ERROR",response1.getBody().jsonPath().getString("errorMessage"));if(response1.getBody().jsonPath().getString("errorMessage").contains("Failed to create transaction to MICRO SERVICE") || response1.getBody().jsonPath().getString("errorMessage").contains("Error") || response1.getBody().jsonPath().getString("errorMessage").contains("error")){
					//Envir
					//MainDriver.incrementEnvironmentErrorCount();
					//Added by Varam to validate response			
					//return  response.getBody().asString();
				//}else
					//Added by Varam to validate response
				Dictionary.put("RESPONSE_VALUE", response1.getBody().asString());
				System.out.println(Dictionary.get("RESPONSE_VALUE"));
				System.out.println(response1.getBody().asString());
					return  response1.getBody().asString();
			}
		}while(retryCount<5);
		
		params.put("<BRAND_NAME>", "'METRO' or brand_name is null");
		
		if(itemID.isEmpty() || itemID.equalsIgnoreCase("any"))
			itemID=Environment.get("AWL_ITEM_ID");
		
		params.put("{ITEM_ID}", itemID);
		rs=dbAction.getDataFromDBWithParameters("DEVICE_NUMBER", params);
	    try {
			imei=rs.getString("SERIAL_NUMBER").trim();
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "AWL BAN is created", "Error while retrieving IMEI: "+e1.getMessage(), "Done");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}
	    
	    rs=dbAction.getDataFromDBWithParameters("SIM_NUMBER", params);
	    try {
			sim=rs.getString("SERIAL_NUMBER").trim();
		} catch (SQLException e1) {
			e1.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "AWL BAN is created", "Error while retrieving SIM: "+e1.getMessage(), "Done");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}
	    
	    params.put("<BRAND_NAME>", "'AWL'");
		params.put("<SIM>", sim);
		params.put("<IMEI>", imei);
		
		if(dbAction.executeUpdateQueryAndCommitWithParameters("SET_DEVICE_FOR_AWL", params)==false){
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "AWL BAN is created", "Error while setting up device for AWL brand", "Done");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}
		
		params.put("{ITEM_ID}", Environment.get("AWL_SIM_ITEM_ID"));
		if(dbAction.executeUpdateQueryAndCommitWithParameters("SET_SIM_FOR_AWL", params)==false){
			Reporter.fnWriteToHtmlOutput("Create AWL BAN", "AWL BAN is created", "Error while setting up sim for AWL brand", "Done");
			Environment.put("ROOT_CAUSE", "No data return for query");
			Environment.put("FAILURE_ERROR","No data return for query");
			MainDriver.incrementDataNotAvailableCount();
			return null;
		}
		
		createBAN_URI=Environment.get("ACTIVATE_RESERVED_SUBSCRIBER_URI");
		
		Dictionary.put("MDN_NO", mdn);
		Dictionary.put("ORDER_ID", orderNumber);
		Dictionary.put("DEVICE_NUMBER", imei);
		
		if(Dictionary.get("PLAN_TYPE").equalsIgnoreCase("HOLDING"))
			Dictionary.put("DEVICE_ORDER_TYPE", "shipped");
		else
			Dictionary.put("DEVICE_ORDER_TYPE", "immediate");
		
		templatePath =  System.getProperty("user.dir") + "/Templates" + "/ActivateReservedSubscriber.json";
		reqBody = replaceTemplateWithValues(templatePath);
				
		String authString = user + ":" + Password;
		byte[] base64Encoded = Base64.getEncoder().encode(authString.getBytes());
		String encodedString = new String(base64Encoded);
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.contentType("application/json");
		httpRequest.header("Authorization", "Basic " + encodedString);
		Response response = httpRequest.request(Method.GET, "/api/user");

		responseStatusCode = response.getStatusCode();
		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Execute API", "Pass");
			System.out.println("Response Code is "+responseStatusCode + "Response Body is "+response.getCookie("TOKEN"));
		}
		else
		{
			Reporter.fnWriteToHtmlOutput("AuthToken", "Get Authorization Token","Failed to execute API", "Done");
			Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response.getBody().asString() ,"Done");
			Environment.put("ROOT_CAUSE", "Environment Error");
			Environment.put("FAILURE_ERROR",response.getBody().jsonPath().getString("error"));
			MainDriver.incrementEnvironmentErrorCount();
			//Added by Varam to validate response	
			Dictionary.put("RESPONSE_VALUE", response.getBody().asString());
			return  response.getBody().asString();
		}
		httpRequest.header("Cookie","TOKEN=" + response.getCookie("TOKEN"));

		httpRequest.body(reqBody);
		Reporter.fnWriteToHtmlOutput("Create AWL BAN", "URI", "URI: "+Environment.get("URI")+createBAN_URI, "Done");
		Reporter.fnWriteToHtmlOutput("Create AWL BAN", "Request sent", "Request sent: "+reqBody, "Done");
		response1 = httpRequest.request(Method.POST, createBAN_URI);

		responseStatusCode = response1.getStatusCode();
		System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
		Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute CreateBAN MS", "MS executed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");

		if(responseStatusCode == 200 || responseStatusCode == 201)
		{
			System.out.println("Response code is "+responseStatusCode  + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("Create AWL BAN","Execute CreateBAN MS", "MS executed successfully - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			JsonPath jp = new JsonPath(response1.getBody().asString());
			ban=jp.getString("accountNumber");
			System.out.println("BAN: "+ban);
			System.out.println("Price plan: "+jp.getString("pricePlanCode"));
			//Added by Varam to validate response	
			Dictionary.put("RESPONSE_VALUE", response1.getBody().asString());
			return  response.getBody().asString();
		}
		else
		{
			System.out.println("Response code is "+responseStatusCode + "\n" + "Response Body is "+response1.getBody().asString());
			Reporter.fnWriteToHtmlOutput("createSingleBANUsingMS","Execute create BAN MS", "MS failed - " + "Response code is "+responseStatusCode + " -Response Body is "+response1.getBody().asString() ,"Done");
			if(response1.getBody().jsonPath().getString("errorMessage").contains("Failed to create transaction to MICRO SERVICE") || response1.getBody().jsonPath().getString("errorMessage").contains("Error") || response1.getBody().jsonPath().getString("errorMessage").contains("error") || response1.getBody().jsonPath().getString("message").contains("is not authorized to perform the activity")){
				Environment.put("ROOT_CAUSE", "Environment Error");
				Environment.put("FAILURE_ERROR",response1.getBody().jsonPath().getString("errorMessage").concat(response1.getBody().jsonPath().getString("message")));
				MainDriver.incrementEnvironmentErrorCount();
			}
			//Added by Varam to validate response	
			Dictionary.put("RESPONSE_VALUE", response1.getBody().asString());
			return  response.getBody().asString();

		}
		
		
		
		
	}

		
		
	
	
	
	
	
	
	
}
