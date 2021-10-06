package vfro.regression;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import vfro.framework.ApplitoolsEyes;
import vfro.framework.CommonFunctions;
import vfro.framework.DatabaseOperations;
import vfro.framework.Driver;
import vfro.framework.Reporting;
import vfro.framework.WebEmail;
import vfro.framework.Driver.HashMapNew;
import vfro.framework.EncryDecry;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import com.codoid.products.fillo.Fillo;

public class MainDriver {
	HashMap <String, String> Environment = new HashMap<String, String>();
	HashMapNew Dictionary = new HashMapNew();
	Reporting Reporter;

	//Instances
	Driver d;
	WebDriver driver;
	Eyes appEyes;
	ApplitoolsEyes applitoolsEyes;
	String driverType;
	CommonFunctions objCommon = new CommonFunctions(driver, driverType, Environment, Reporter);;
	Object testId;
	boolean bSkip;
	int TestCounter =0;
	static int TestNumber =0;
	ITestResult result;
	String env;
	String runOnEnv;
	String browser;
	Connection Conn;
	int counterA = 0;
	int counterB = 0;
	Reporting reporting;
	static String release;
	static String testPhase;
	static String packageName;
	static String Status_Table;
	SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/YYYY HH:mm:ss");
	SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
	Date date = new Date();
	String testProfile;
	static String executionBrowser;
	String progressionCRName;
	static String applicationName;
	String reportsPath="";
	static int totalTestCount,totalPassedCount,totalFailedCount;
	int testStatus;
	static String user;
	Date startDate=null;
	Date endDate=null;
	Date today=new Date();
	static String calendarName;
	static int Count_EnvironmentError=0;
	static int Count_ElementNotFound=0;
	static int Count_DataNotAvailable=0;
	static int Count_ScriptNotFound=0;
	static int Count_Other=0;
	String assignTableContent=null;
	static String Test_Suite_Name,Environment_Name,auth_emailId,toEmailId,Count_Table,ccEmailId,auth_password,internet_add;
	int failedTCCount=1; 
	String assignFileContent=null;
	List<String> developers = new ArrayList<>();
	boolean developerExist=false;
	Fillo fillo = new Fillo();
	com.codoid.products.fillo.Connection filloCon=null;
	

	@Parameters({"browser", "envcode","visual","Calendar","Emulation_Device_Name","Test_Phase"})
	@BeforeClass
	public void beforeClass(@Optional("") String browser, @Optional("") String runOnEnv, @Optional("") String visual, String calendarName, @Optional("") String deviceName, @Optional("") String testPhase, ITestContext context) {
		try{
			release=System.getProperty("Release").toUpperCase().trim();
		}catch(NullPointerException e){
			release=Paths.get(System.getProperty("user.dir")).getFileName().toString().replaceAll("[^0-9.]", "");
		}

		try{ 
			System.getProperty("Calendar").trim();
			Environment.put("CLASS_NAME", System.getProperty("Calendar"));
		}catch(NullPointerException e){
			Environment.put("CLASS_NAME", calendarName);
		}

		packageName = Environment.get("CLASS_NAME").split("_")[1].replaceAll("[0-9]", "");
		
		try{
			driverType=System.getProperty("Browser").toUpperCase().trim()+"1";
		}catch(NullPointerException e){
			driverType = browser;
		}

		executionBrowser=driverType.replaceAll("[0-9]", "");

		try{
			user = System.getProperty("BUILD_USER").trim();
		}catch(NullPointerException e){
			user = System.getProperty("user.name");
		}

		applicationName = Environment.get("CLASS_NAME").split("_")[0];

		try{ 
			File file = new File(System.getProperty("WorkSpace").trim()+"/ExecutionSummary.html");
			if(file.exists()) file.delete();
		}catch(NullPointerException e){}

		try{
			reportsPath = System.getProperty("Reports_Path").trim()+"\\"+System.getProperty("Release").trim()+"\\"+testPhase+"\\"+applicationName;
			//reportsPath = "//INPNQVZAUTO03\\Reports\\"+System.getProperty("Release").trim()+"\\"+testPhase+"\\"+applicationName;
			
		}catch(NullPointerException e){}

		d = new Driver(driverType, Dictionary, Environment, reportsPath);
		env = System.getProperty("envName");	
		if (env==null){	
			try{
				env=System.getProperty("Environment").toUpperCase().trim();
			}catch(NullPointerException e){
				env = runOnEnv;
			}
		}

		Assert.assertNotNull(env);
		//Add env global environments
		Environment.put("ENV_CODE", env);

		d.fetchEnvironmentDetails(); //from Environment.xlsx
		try {
			d.createExecutionFolders(Environment.get("CLASS_NAME"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}	

		Reporter = new Reporting(driver, driverType, Dictionary, Environment);
		Reporter.fnCreateSummaryReport();
		
		try{
			Environment.put("VISUAL_TEST", System.getProperty("VISUAL_TEST").toUpperCase().trim());
		}catch(NullPointerException e){
			Environment.put("VISUAL_TEST", visual);
		}

		if (Environment.get("VISUAL_TEST").equals("Y")){
			applitoolsEyes = new ApplitoolsEyes(driverType, Dictionary, Environment, Reporter);
			appEyes = ApplitoolsEyes.getEyes();
		}

		// Set invocation count for Test
		ITestNGMethod currentTestNGMethod = null;
		for (ITestNGMethod testNGMethod : context.getAllTestMethods()) {
			if (testNGMethod.getInstance() == this) {
				currentTestNGMethod = testNGMethod;
				break;
			}
		}

		// Get number of TCs to be executed
		TestCounter=0;
		TestNumber=0;

		TestCounter= d.getSkipedCells();
		if (TestCounter==0){
			System.out.println("\n" + StringUtils.repeat('*', 70));
			System.out.println("* No Test Case to execute was found in Excel datasheet for " + driverType);
			System.out.println(StringUtils.repeat('*', 70) );
			currentTestNGMethod.setInvocationCount(0);
		}else{
			System.out.println("Number of @Test to be executed for "+ driverType.replace("1", "")+" driver type"+" : "+ TestCounter);
			currentTestNGMethod.setInvocationCount(TestCounter);
		}
	}

	@Parameters({"browser", "Emulation_Device_Name"})
	@BeforeMethod
	public void beforeMethod(@Optional("") String browser, @Optional("") String deviceName, Method method){
		try{
			d.fGetNextRecordData();

			if (Dictionary.get("SKIP_"+driverType).equalsIgnoreCase("")){	
				driver = d.fGetWebDriver(driverType, Dictionary.get("ACTION"));
				Environment.put("FAILURE_ERROR", "");
				Environment.put("ROOT_CAUSE", "");
				date=new Date();
				Environment.put("EXECUTION_DATE", dateFormat.format(date));

				if (Environment.get("VISUAL_TEST").equals("Y"))
					appEyes.open(driver, Environment.get("CLASS_NAME"), Dictionary.get("TEST_NAME"), new RectangleSize(1280, 800));
				else
					driver.manage().window().maximize();

				System.out.println("Resolution: "+driver.manage().window().getSize());
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				Reporter.driver = driver;
				Reporter.fnCreateHtmlReport(Dictionary.get("TEST_NAME").replaceAll("\\r|\\n", "").trim());
				objCommon = new CommonFunctions(driver, driverType, Environment,Reporter);
				Dictionary.put("TEST_NAME_"+ driverType, Dictionary.get("TEST_NAME").replaceAll("\\r|\\n", "").trim());

				if(Environment.get("CLASS_NAME").equals("Profiles"))
					testProfile=Dictionary.get("TESTING_PROFILE");
				else
					testProfile="";

				if(Dictionary.get("RUNSET_NAME").isEmpty()==false)
					progressionCRName=Dictionary.get("RUNSET_NAME").toUpperCase().trim();
				else
					progressionCRName="";
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getLocalizedMessage().contains("This version of MSEdgeDriver only supports MSEdge version")){
				System.out.println("Looks like your EDGE browser got upgraded to a new version.");
				System.out.println("\nCurrent edge driver is not compatible. Please upgrade edge driver and try again");
				System.exit(0);
			}
		}

	}

	@Parameters({"browser","Calendar"})
	@Test
	public void Test(@Optional("") String browser, String calendarName) throws Exception 
	{
		String testName = Dictionary.get("ACTION");
		boolean testFound=false;
		try{
			browser=System.getProperty("Browser").toUpperCase().trim()+"1";
		}catch(NullPointerException e){}
		
		try{
			calendarName=System.getProperty("Calendar").trim();
		}catch(NullPointerException e){}
		
//		String className = calendarName;
		String className ="DEXP_Regression";
		
		TestNumber=TestNumber+1;

		System.out.println("\n" + StringUtils.repeat('*', 70));
		System.out.println("Executing test: " + MainDriver.TestNumber + "/"+TestCounter+"");
		System.out.println(StringUtils.repeat('*', 70));

		try {
			Class<?> c=null;
			try{
				c= Class.forName("vfro.Scenarios." + className.replace("_Test", "").replaceAll("[0-9]", "").trim());
				Constructor<?> cons = c.getDeclaredConstructor( new Class[] {WebDriver.class ,String.class ,HashMap.class, HashMap.class ,Reporting.class});//, Eyes.class});
				Object o = cons.newInstance(driver, driverType, Dictionary, Environment, Reporter);//, appEyes);
				Method method = c.getMethod(testName, String.class);
				method.invoke(o,browser);
				testFound=true;
			}catch (NoSuchMethodException e) {
				e.printStackTrace();
				System.out.println("Method " + testName + " doesn't exist");
			}

			if(testFound==false){
				Environment.put("FAILURE_ERROR", "Script not found");
				Reporter.fnWriteToHtmlOutput(Dictionary.get("TEST_NAME"), "Functional test passed", "Script not found", "Fail");
				Assert.fail();
			}
		}catch(InvocationTargetException e) {
			e.printStackTrace();
			for(int i=0;i<e.getTargetException().getStackTrace().length;i++){
				if(e.getTargetException().getStackTrace()[i].toString().startsWith("vfro.Scenarios")){
					if(i!=0)
						Reporter.fnWriteToHtmlOutput(Dictionary.get("TEST_NAME"), "Functional test passed", "Error '"+e.getCause().toString().split("[.]")[e.getCause().toString().split("[.]").length-1]+"' occured in step: '"+e.getTargetException().getStackTrace()[i-1].getMethodName()+"'. For reference, please refer line number: '"+e.getTargetException().getStackTrace()[i].getLineNumber()+"' in class '"+e.getTargetException().getStackTrace()[i].getClassName().split("[.]")[e.getTargetException().getStackTrace()[i].getClassName().split("[.]").length-1]+"'", "Fail");
					else
						Reporter.fnWriteToHtmlOutput(Dictionary.get("TEST_NAME"), "Functional test passed", "Error '"+e.getCause().toString().split("[.]")[e.getCause().toString().split("[.]").length-1]+"' occured. For reference, please refer line number: '"+e.getTargetException().getStackTrace()[i].getLineNumber()+"' in class '"+e.getTargetException().getStackTrace()[i].getClassName().split("[.]")[e.getTargetException().getStackTrace()[i].getClassName().split("[.]").length-1]+"'", "Fail");
					
					//Assuming error is stale element exception. Hence classifying this error into element not found category
					if(e.getCause().toString().split("[.]")[e.getCause().toString().split("[.]").length-1].contains("Session ID")==true){
						Environment.put("FAILURE_ERROR","Element was not found");
						Environment.put("ROOT_CAUSE", "Element not found in page");
						MainDriver.incrementElementNotFoundCount();
					}
					break;
				}
			}
		}finally{
			if(vfro.framework.Reporting.getFailureCount()>0)
				Assert.fail();
		}
	}
	//@Parameters({"browser", "envcode","visual","Calendar","Emulation_Device_Name","Test_Phase"})@Parameters({"Suite","envcode"})
	@Parameters({"browser","envcode"})
	@AfterMethod
	public void afterMethod(Method method, ITestResult result,@Optional("") String browser, @Optional("") String envcode) throws Exception{
		
		driverType = browser;
		Environment_Name = envcode;
        totalTestCount = totalTestCount + 1;
        String action = method.getName();
        testStatus = result.getStatus();
        //String flowName = method.getMethodName();
        String flowName = action;
        String stat = null;
        if (testStatus == 1) {
               stat = "PASS";
               totalPassedCount++;
        } else if (testStatus == 2) {
               stat = "FAIL";
               totalFailedCount++;
        }

        Status_Table = objCommon.flowNameStatus(Environment.get("HTMLREPORTSPATH")+"//SummaryReport.html", totalTestCount, totalPassedCount, totalFailedCount,flowName,stat);
        
		try{
			browser=System.getProperty("Browser").toUpperCase().trim()+"1";
		}catch(NullPointerException e){
			
		}
		driverType = browser;
		Boolean applitoolsResults = null;
		if (Environment.get("VISUAL_TEST").equals("Y")){
			try {
				applitoolsResults = ApplitoolsEyes.closeEyes();
			} catch (Exception e1) {
				e1.printStackTrace();
				appEyes.abortIfNotClosed();
			}
		}

		Conn=DatabaseOperations.getConnection();

		if(Conn!=null)
			DatabaseOperations.closeConnection(Conn);
		try{
			if (Dictionary.get("SKIP_"+ driverType).equals("") && !Dictionary.get("SKIP_"+driverType).equals("null")){ 
				if (Environment.get("VISUAL_TEST").equals("Y")){
					if (applitoolsResults == true){
						Dictionary.put("VISUAL_RESULT_" + driverType, "P");
						Driver.QCHashMap.put("VISUAL_RESULT_" + driverType, "P");

						Dictionary.put("QC_VISUAL_STATUS", "Passed");
						Driver.QCHashMap.put("QC_VISUAL_STATUS", "Passed");
					}else{
						Dictionary.put("VISUAL_RESULT_" + driverType, "F");
						Driver.QCHashMap.put("VISUAL_RESULT_" + driverType, "F");

						Dictionary.put("QC_VISUAL_STATUS", "Failed");
						Driver.QCHashMap.put("QC_VISUAL_STATUS", "Failed");
					}
				}

				if (result.getStatus() == ITestResult.SUCCESS){
					Reporter.fnWriteToHtmlOutput(Dictionary.get("TEST_NAME"), "Functional test passed", "Functional test passed", "Done");
					if (Environment.get("VISUAL_TEST").equals("Y")){
						Dictionary.put("RESULT_" + driverType, "P_" + applitoolsResults);
						Driver.QCHashMap.put("RESULT_" + driverType, "P_" + applitoolsResults);
					}else{
						Dictionary.put("RESULT_" + driverType, "P");
						Driver.QCHashMap.put("RESULT_" + driverType, "P");
					}

					Dictionary.put("QC_STATUS", "Passed");
					Driver.QCHashMap.put("QC_STATUS", "Passed");
				}
				else if(result.getStatus() == ITestResult.FAILURE){
					Reporter.fnWriteToHtmlOutput(Dictionary.get("TEST_NAME"), "Functional test failed", "Functional test failed", "Done");
					if (Environment.get("VISUAL_TEST").equals("Y")){
						Dictionary.put("RESULT_" + driverType, "F_" + applitoolsResults);
						Driver.QCHashMap.put("RESULT_" + driverType, "F_" + applitoolsResults);
					}else{
						Reporter.fnWriteToHtmlOutput(Dictionary.get("TEST_NAME"), "Functional test failed", "Functional test failed", "Done");
						Dictionary.put("RESULT_" + driverType, "F");
						Driver.QCHashMap.put("RESULT_" + driverType, "F");
					}
					
					Dictionary.put("QC_STATUS", "Failed");
					Driver.QCHashMap.put("QC_STATUS", "Failed");
				}else {
					if (Environment.get("VISUAL_TEST").equals("Y")){
						Dictionary.put("RESULT_" + driverType, "N_" + applitoolsResults);
						Driver.QCHashMap.put("RESULT_" + driverType, "N_" + applitoolsResults);
					}else{
						Dictionary.put("RESULT_" + driverType, "N");
						Driver.QCHashMap.put("RESULT_" + driverType, "N");
					}

					Dictionary.put("QC_STATUS", "Passed");
					Driver.QCHashMap.put("QC_STATUS", "Passed");
					System.out.println("Invalid Status");
				}
				System.out.println("Result - " + driverType + " - " + result.toString());
				d.fUpdateTestCaseRowSkip(Integer.parseInt(Dictionary.get("SKIPROW_" + driverType)));

				//Close Summary Report
				//Reporter.fnCloseHtmlReport();
				
				
			//Get OS and Browser Version Information
			Dictionary.put("OS_BROWSER_VER", d.FindOSBrowserVer(driver));

			Driver.QCHashMap.put("TEST_NAME_", Dictionary.get("TEST_NAME_"+ driverType) + "_" + driverType);
			Driver.QCHashMap.put("OS_BROWSER_VER", d.FindOSBrowserVer(driver));
			Driver.QCHashMap.put("STEP", Dictionary.get("STEP"));
			Driver.QCHashMap.put("QC_UPDATE", Environment.get("QC_UPDATE"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("exception " + Dictionary.get("TEST_NAME_"+ driverType) + driverType + ": " + e.toString());
		}

		//Quit Driver
		if(driver!=null){
			try{
				driver.quit();
			}catch(Exception e){}
		}
	}
	@Parameters({"Suite","envcode"})
	@AfterClass
	public void afterClass(@Optional("") String Suite,@Optional("") String envcode){
		try{
			Test_Suite_Name = Suite;
            Environment_Name = envcode;
            auth_emailId = Environment.get("FROM_EMAIL_ID");
            toEmailId = Environment.get("TO_EMAIL_ID");
            ccEmailId = Environment.get("CC_EMAIL_ID");
            auth_password = EncryDecry.Decrypted(Environment.get("FROM_EMAIL_ID_PASSWORD"));
            internet_add = Environment.get("FROM_EMAIL_ID");


			Reporter.fnCloseTestSummary();
			Count_Table = objCommon.createEmailTable();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("exception " + Dictionary.get("TEST_NAME_"+ driverType) + driverType + ": " + e.toString());
		}
	}
	@AfterSuite
	public void after_suite()  
    {  
		/* String HtmlSummaryReport="";
		 StringBuilder contentBuilder = new StringBuilder();
         try {
             BufferedReader in = new BufferedReader(new FileReader(Environment.get("HTMLREPORTSPATH")+"//SummaryReport.html"));
             String str;
             while ((str = in.readLine()) !=null) {
                 contentBuilder.append(str);
             }
             in.close();
         } catch (IOException e) {
         }
         HtmlSummaryReport = contentBuilder.toString();
         Status_Table=HtmlSummaryReport;*/
           WebEmail.fEmail(auth_emailId, auth_password, internet_add, Status_Table, Count_Table,Test_Suite_Name,Environment_Name, toEmailId, ccEmailId);
          
    }

	public static int getTestNumber(){
		return TestNumber;
	}
	
	public static String getApplicationName(){
		return applicationName;
	}
	
	public static String getTestPhase(){
		return testPhase;
	}
	
	public static String getCalendarName(){
		return calendarName;
	}
	
	public static int getEnvironmentErrorCount(){
		return Count_EnvironmentError;
	}
	
	public static int getElementNotFoundCount(){
		return Count_ElementNotFound;
	}
	
	public static int getDataNotAvailableCount(){
		return Count_DataNotAvailable;
	}
	
	public static int getScriptNotFound(){
		return Count_ScriptNotFound;
	}
	
	public static int getOtherCount(){
		return Count_Other;
	}
	
	public static int incrementEnvironmentErrorCount(){
		return Count_EnvironmentError++;
	}
	
	public static String getRelease(){
		return release;
	}
	
	public static String getPackageName(){
		return packageName;
	}
	
	public static String getExecutionBrowser(){
		return executionBrowser;
	}
	
	public static String getUser(){
		return user;
	}
	
	public static int incrementDataNotAvailableCount(){
		return Count_DataNotAvailable++;
	}
	
	public static int incrementElementNotFoundCount(){
		return Count_ElementNotFound++;
	}
}
