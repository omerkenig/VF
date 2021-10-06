package vfro.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeDriverService.Builder;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
/*import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;*/
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.mercury.qualitycenter.otaclient.*;
import com.codoid.products.fillo.Fillo;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import com4j.Com4jObject;
import vfro.regression.MainDriver;


public class Driver{

	private static final String DEFAULT_PROXY_SERVER = "one.proxy.att.com:8080";

	//Variables
	String rootPath;
	String executionPath;
	String datasheetPath;
	String unixlogPath;
	String curExecutionFolder;
	String htmlReportsPath;
	String snapShotsPath;
	String unixlogsReportsPath;
	String dataSheetsPath;
	String dataSheet;
	String User;	
	String driverType;
	String enviromentsPath;
	Thread QCThread = null;
	String testFolder;
	String username = System.getProperty("user.name")+"_";
	String oceapiresponsePath;
	String oceapixmlPath;
	String wtReportingPath;

	HashMap <String, String> orgDictionary = new HashMap<String, String>();
	HashMapNew Dictionary = new HashMapNew();
	HashMap <String, String> Environment = new HashMap<String, String>();
	public static HashMap<String, String> QCHashMap = new HashMap<String, String>();
	public static HashMap<Object, Object> QCHashMapTestId = new HashMap<Object, Object>();
	public static ArrayList<Object> arlst = new ArrayList<Object>();
	public static ArrayList<Object> catoList = new ArrayList<Object>();

	//For MAC appium
	private JSch jsch;
	private Session session;
	private ChannelExec channelandroid;
	private ChannelExec channelios;

	//Launch Appium
	private static Process nodeProcess;
	private static Runtime runTime;

	//DataSheet 
	Sheet Sheet = null;

	Driver d;

	//Constructor
	public Driver(String DT, HashMapNew GDictionary, HashMap <String, String> GEnvironment, String reportsPath)
	{
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;

		//Get Root Path
		try{
			System.getProperty("Jenkins_Build_Number").trim();
			User="Jenkins";
		}catch(NullPointerException e){
			User = System.getProperty("user.name");
		}
		
		String rootPath = System.getProperty("user.dir");

		//Set paths
		if(reportsPath.isEmpty())
			executionPath = rootPath + "/Execution/"+MainDriver.getApplicationName();
//			executionPath = rootPath + "/Execution"+"/"+MainDriver.getTestPhase()+"/"+MainDriver.getApplicationName();
		else
			executionPath = reportsPath;
		dataSheetsPath = rootPath + "/data";
		enviromentsPath = rootPath + "/environments";
		unixlogPath = rootPath + "/UnixLogValidations";
		oceapiresponsePath = rootPath + "/APIResponse";

		//Add to Env Variables
		Environment.put("ROOTPATH", rootPath);
		Environment.put("EXECUTIONFOLDERPATH", executionPath);
		Environment.put("ENVIRONMENTXLSPATH", enviromentsPath);


		//Get Sheet from data calendar
		Sheet = fGetDataSheet(Environment.get("CLASS_NAME"));
	}
	
	public static class HashMapNew extends HashMap<String,String>{
		static final long serialVersionUID = 1L;

		@Override
		public String get(Object key){
			String value = super.get(key);
			if (value==null){return "";}
			return value;
		}
	}

	public static class HashMapQC extends HashMap<String,String>{
		static final long serialVersionUID = 1L;

		@Override
		public String get(Object key){
			String value = super.get(key);
			if (value==null){return "";}
			return value;
		}
	}
	///
	//	public static class Util {
	//	    public static HashMap<String,String> hMap = new HashMap<>();
	//	}
	///

	//Function to Create Execution Folders
	public boolean createExecutionFolders(String Classname) throws IOException
	{		
		//******************* Fetch Current TimeStamp ************************
		java.util.Date today = new java.util.Date();
		Timestamp now = new java.sql.Timestamp(today.getTime());
		String tempNow[] = now.toString().split("/.");
		String timeStamp = tempNow[0].replaceAll(":", ".").replaceAll(" ", "T");

		//Set execution paths
		//curExecutionFolder = executionPath + "/" + Classname + "/" + Environment.get("QC_TESTSET") + "/" + User; -- commented as the there is no QC
		curExecutionFolder = executionPath + "/" + Classname + "/" + Environment.get("ENV_CODE") + "/" + User;
		try{
			//htmlReportsPath = curExecutionFolder+ "/" + driverType + "/HTML_Reports_Build_" +System.getProperty("Jenkins_Build_Number").trim();
			htmlReportsPath = "\\\\INPNQVZAUTO03/Reports"+ "/" + driverType + "/HTML_Reports_Build_" +System.getProperty("Jenkins_Build_Number").trim();
		}catch(NullPointerException e){
			//htmlReportsPath = curExecutionFolder+ "/" + driverType + "/HTML_Reports" + timeStamp;
			htmlReportsPath="\\\\INPNQVZAUTO03/Reports"+ "/" + driverType + "/HTML_Reports" + timeStamp;
		}
		
		snapShotsPath = htmlReportsPath + "/Snapshots";	
		unixlogsReportsPath = unixlogPath +"/" + Classname +"_"+ Environment.get("ENVIRONMENT") +"_LOGS"; //added by Naveena for unix log validation
//		oceapixmlPath = oceapiresponsePath + "/" + Environment.get("ENVIRONMENT")+"_"+timeStamp;
		try{
			wtReportingPath = System.getProperty("user.dir")+ "/AdobeReports/AdobeReport_Build_"+System.getProperty("Jenkins_Build_Number").trim();
		}catch(NullPointerException e){
			wtReportingPath = System.getProperty("user.dir")+ "/AdobeReports/AdobeReport_"  + timeStamp;
		}		

		//Put in Environments
		Environment.put("CURRENTEXECUTIONFOLDER", curExecutionFolder);
		Environment.put("HTMLREPORTSPATH", htmlReportsPath);
		Environment.put("SNAPSHOTSFOLDER", snapShotsPath);		
		Environment.put("UNIXLOGSFOLDERPATH", unixlogsReportsPath);	
//		Environment.put("OCEAPIXMLPATH", oceapixmlPath);
		Environment.put("WTREPORTINGPATH", wtReportingPath);
//		new File(Environment.get("OCEAPIXMLPATH")).mkdirs();
		new File(Environment.get("WTREPORTINGPATH")).mkdirs();

		//Code to Save Report in text file for EM Smoke Test - Added by Shraddha
		try {
			FileOutputStream reportPath = new FileOutputStream(System.getProperty("user.dir") + "/ReportPath.txt");
			new PrintStream(reportPath).println(htmlReportsPath);
			reportPath.close();
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}
		return (new File(snapShotsPath)).mkdirs();

	}

	//*****************************************************************************************
	//*	Name		    : fGetDataSheet
	//*	Description	    : Function to get the DataSheet
	//*	Author		    : kaiqi Tang
	//* Input Params	: String, ClassName
	//*	Return Values	: None
	//***********************************************************************	
	public Sheet fGetDataSheet(String className){
		Sheet datasheet = null;
		//DataSheet
		final String dataSheet = dataSheetsPath + "/" + className + ".xlsx";
		final String mainSheet = "MAIN"; 

		try{

			//Create the FileInputStream object			
			FileInputStream file = new FileInputStream(new File(dataSheet));		     
			//Get the workbook instance for XLS file 
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			//Get MAIN sheet from the workbook
			datasheet = workbook.getSheet(mainSheet);
			file.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		return datasheet;
	}

	//*****************************************************************************************
	//*	Name		    : fGetColumnIndex
	//*	Description	    : Function to get the Column Index
	//*	Author		    : Anil Agarwal
	//* Input Params	: int row - Row number to skip
	//*	Return Values	: None
	//***********************************************************************
	public int fGetColumnIndex (String strXLSX, String strSheetName, String strColumnName)
	{
		try
		{
			//Create the FileInputStream object			
			FileInputStream file = new FileInputStream(new File(strXLSX));		     
			//Get the workbook instance for XLS file 
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first sheet from the workbook
			Sheet sheet = workbook.getSheet(strSheetName);

			//Iterate through each rows from first sheet
			Row row = sheet.getRow(0);

			//Get the Column count
			int iColCount = row.getLastCellNum();
			int iCell = 0;
			int iIndex = -1;
			String strTemp = "";

			//Loop through all the columns
			for (iCell = 0; iCell < iColCount; iCell ++)
			{
				//Get the index for Version and Enviornment
				strTemp = sheet.getRow(0).getCell(iCell).getStringCellValue().trim().toUpperCase();

				//if the strColumnName contains Header then check for HEADER or HEADER_IND
				if (strColumnName.equals("HEADER_IND") || strColumnName.equals("HEADER"))
				{
					if (strTemp.equals("HEADER") || strTemp.equals("HEADER_IND"))
					{
						iIndex = iCell;
						//Exit the Loop
						break;
					}

				}else{ 
					if (strTemp.equals(strColumnName.trim().toUpperCase()))
					{
						iIndex = iCell;
						//Exit the Loop
						break;
					}
				}
			}
			//Close the file
			file.close();

			//Validate if index is returned properly or not
			if (iIndex != -1)
			{
				return iIndex;

			}else{
				System.out.println("Failed to find the Column Id for Column " + strColumnName);
				return -1;
			} 
		}catch (Exception e){
			System.out.println("Got exception while finding the Index column. Exception is " + e);
			return -1;
		}
	}

	//*****************************************************************************************
	//*	Name		    : fGetColumnIndex(overloaded)
	//*	Description	    : Function to get the Column Index
	//*	Author		    : Anil Agarwal
	//* Input Params	: int row - Row number to skip
	//*	Return Values	: None
	//***********************************************************************
	public int fGetColumnIndex (Sheet sheet, String strSheetName, String strColumnName)
	{
		try
		{

			Row row = sheet.getRow(0);

			//Get the Column count
			int iColCount = row.getLastCellNum();
			int iCell = 0;
			int iIndex = -1;
			String strTemp = "";

			//Loop through all the columns
			for (iCell = 0; iCell < iColCount; iCell ++)
			{
				//Get the index for Version and Enviornment
				strTemp = sheet.getRow(0).getCell(iCell).getStringCellValue().trim().toUpperCase();

				//if the strColumnName contains Header then check for HEADER or HEADER_IND
				if (strColumnName.equals("HEADER_IND") || strColumnName.equals("HEADER"))
				{
					if (strTemp.equals("HEADER") || strTemp.equals("HEADER_IND"))
					{
						iIndex = iCell;
						//Exit the Loop
						break;
					}

				}else{ 
					if (strTemp.equals(strColumnName.trim().toUpperCase()))
					{
						iIndex = iCell;
						//Exit the Loop
						break;
					}
				}
			}

			//Validate if index is returned properly or not
			if (iIndex != -1)
			{
				return iIndex;

			}else{
				System.out.println("Failed to find the Column Id for Column " + strColumnName);
				return -1;
			}
		}catch (Exception e){
			System.out.println("Got exception while finding the Index column. Exception is " + e);
			return -1;
		}
	}


	//*****************************************************************************************
	//*	Name		    : fGetDataForTest
	//*	Description	    : Function to get Data corresponding to Test
	//*	Author		    : Aniket Gadre
	//* Input Params	: int row - Row number to skip
	//*	Return Values	: None
	//***********************************************************************
	public boolean fGetDataForTest(String testName,String className)
	{
		//DataSheet
		final String dataSheet = dataSheetsPath + "/" + className + ".xlsx";
		final String mainSheet = "MAIN"; 
		final String testNameColumn = "ACTION";

		//Clear Dictionary
		Dictionary.clear();
		orgDictionary.clear();



		try{

			//Create the FileInputStream object			
			//FileInputStream file = new FileInputStream(new File(dataSheet));	//comment out by Kaiqi Tang 05/04/2015	     
			//Get the workbook instance for XLS file 
			//XSSFWorkbook workbook = new XSSFWorkbook(file); //comment out by Kaiqi Tang 05/04/2015	 

			//Get MAIN sheet from the workbook
			Sheet sheet = Sheet;

			//Get column index of test name column
			int iColTestName = fGetColumnIndex(sheet, mainSheet, testNameColumn);

			//Iterate through each rows from first sheet
			int iRowCnt = sheet.getLastRowNum();

			//Loop
			int iRow;
			for(iRow=0;iRow<iRowCnt;iRow++)
			{
				//Get row with test name and exist
				if(sheet.getRow(iRow).getCell(iColTestName).getStringCellValue().equalsIgnoreCase(testName)) break;
			}

			//Check if test found
			/*if(iRow == iRowCnt){
				System.out.println("Test with name: " + testName + " not found in datasheet: " +  dataSheet);
				return false;
			}*/
			//////////////////////
			if(iRow == iRowCnt){
				Dictionary.put("SKIP_"+driverType,"null");
				System.out.println("Test Case: " + testName + " not found in datasheet: " +  dataSheet);
			}
			//////////////////////
			if(iRow < iRowCnt){ // it will execute only if the test case exists in the excel
				//Retrieve Skip value
				Dictionary.put("SKIP_"+driverType, sheet.getRow(iRow-1).getCell(fGetColumnIndex(sheet, mainSheet, "SKIP_" + driverType)).getStringCellValue());
				//if (Dictionary.get("SKIP_"+driverType).contains(""))
				if (Dictionary.get("SKIP_"+driverType).isEmpty())//updated by Dina
				{
					//Dina - save the row number to update with status
					Dictionary.put("SKIPROW_" + driverType, Integer.toString(iRow));
					System.out.println("GetData() - " + driverType + " " + iRow);
					System.out.println();
					//Set Header & DataRow
					Row headerRow = sheet.getRow(iRow-1);
					Row dataRow = sheet.getRow(iRow);

					//Get Column count for test-1 row
					int iParamCnt = headerRow.getLastCellNum();		 

					//
					String key="";
					String value="";

					//Loop through params
					int iCol;
					for(iCol=0;iCol<iParamCnt;iCol++){

						//Fetch the value for the Header Row
						if (headerRow.getCell(iCol, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK) == null)
						{
							key = "";
						}else{
							key = headerRow.getCell(iCol).getStringCellValue();
						}

						//Fetch the value for the Header Row
						if (dataRow.getCell(iCol, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK) == null)
						{
							value = "";
						}else{
							value = dataRow.getCell(iCol).getStringCellValue();
						}

						//Check key value
						Dictionary.put(key,value);
						orgDictionary.put(key,value);

					} 	
				}
			}
			//Retrieve Skip value
			// Dictionary.put("SKIP", sheet.getRow(iRow-1).getCell(fGetColumnIndex(dataSheet, mainSheet, "SKIP_" + driverType)).getStringCellValue());
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exception " + e + " occured while fetching data from datasheet " + dataSheet + " for test " + testName);
			return false;
		}		
	}

	//*****************************************************************************************
	//*	Name		    : fGetDataForTest(overload)
	//*	Description	    : Function to get Data corresponding to Test
	//*	Author		    : Aniket Gadre
	//* Input Params	: int row - Row number to skip
	//*	Return Values	: None
	//***********************************************************************
	public boolean fGetDataForTest(String testName)
	{
		//DataSheet
		final String mainSheet = "MAIN"; 
		final String testNameColumn = "ACTION";

		//Clear Dictionary
		Dictionary.clear();
		orgDictionary.clear();

		try{

			//Get column index of test name column
			int iColTestName = fGetColumnIndex(Sheet, mainSheet, testNameColumn);

			//Iterate through each rows from first sheet
			int iRowCnt = Sheet.getLastRowNum();

			//Loop
			int iRow;
			for(iRow=0;iRow<iRowCnt;iRow++)
			{
				//Get row with test name and exist
				if(Sheet.getRow(iRow).getCell(iColTestName).getStringCellValue().equalsIgnoreCase(testName)) break;
			}

			//Check if test found
			/*if(iRow == iRowCnt){
				System.out.println("Test with name: " + testName + " not found in datasheet: " +  dataSheet);
				return false;
			}*/
			//////////////////////
			if(iRow == iRowCnt){
				Dictionary.put("SKIP_"+driverType,"null");
				System.out.println("Test Case: " + testName + " not found in datasheet: " +  dataSheet);
			}
			//////////////////////
			if(iRow < iRowCnt){ // it will execute only if the test case exists in the excel
				//Retrieve Skip value
				Dictionary.put("SKIP_"+driverType, Sheet.getRow(iRow-1).getCell(fGetColumnIndex(Sheet, mainSheet, "SKIP_" + driverType)).getStringCellValue());
				//if (Dictionary.get("SKIP_"+driverType).contains(""))
				if (Dictionary.get("SKIP_"+driverType).isEmpty())//updated by Dina
				{
					//Dina - save the row number to update with status
					Dictionary.put("SKIPROW_" + driverType, Integer.toString(iRow));
					System.out.println("GetData() - " + driverType + " " + iRow);
					System.out.println();
					//Set Header & DataRow
					Row headerRow = Sheet.getRow(iRow-1);
					Row dataRow = Sheet.getRow(iRow);

					//Get Column count for test-1 row
					int iParamCnt = headerRow.getLastCellNum();		 

					//
					String key="";
					String value="";

					//Loop through params
					int iCol;
					for(iCol=0;iCol<iParamCnt;iCol++){

						//Fetch the value for the Header Row
						if (headerRow.getCell(iCol, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK) == null)
						{
							key = "";
						}else{
							key = headerRow.getCell(iCol).getStringCellValue();
						}

						//Fetch the value for the Header Row
						if (dataRow.getCell(iCol, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK) == null)
						{
							value = "";
						}else{
							value = dataRow.getCell(iCol).getStringCellValue();
						}

						//Check key value
						Dictionary.put(key,value);
						orgDictionary.put(key,value);

					} 	
				}
			}
			//Retrieve Skip value
			// Dictionary.put("SKIP", sheet.getRow(iRow-1).getCell(fGetColumnIndex(dataSheet, mainSheet, "SKIP_" + driverType)).getStringCellValue());
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exception " + e + " occured while fetching data from datasheet " + dataSheet + " for test " + testName);
			return false;
		}		
	}	

	
	//*****************************************************************************************
	//*	Name		    : fGetNextRecordData
	//*	Description	    : Function to get next record data for execution
	//*	Author		    : Zachi Gahari
	//* Input Params	: None
	//*	Return Values	: None
	//***********************************************************************
	public boolean fGetNextRecordData()
	{
		//DataSheet
		final String mainSheet = "MAIN"; 
//		final String testNameColumn = "ACTION";

		//Clear Dictionary
		Dictionary.clear();
		orgDictionary.clear();

		try{
			int index = getNextRecordIndex();
			if(index == 0){
				Dictionary.put("SKIP_"+driverType,"null");
				System.out.println("No Test Case to execute was found in Excel datasheet" );
			}

			// Iterate through each rows from first sheet
			int iRowCnt = Sheet.getLastRowNum();

			if(index < iRowCnt){ // it will execute only if the test case exists in the excel
				//Retrieve Skip value
				try{Dictionary.put("SKIP_"+driverType, Sheet.getRow(index).getCell(fGetColumnIndex(Sheet, mainSheet, "SKIP_" + driverType)).getStringCellValue());
				}catch(NullPointerException e){
					Dictionary.put("SKIP_"+driverType, "");
				}
				if (Dictionary.get("SKIP_"+driverType).isEmpty())
				{
					//Save the row number to update with status
					Dictionary.put("SKIPROW_" + driverType, Integer.toString(index));
					System.out.println("GetData() - " + driverType + " " + index);
					System.out.println();
					//Set Header & DataRow
					Row headerRow = Sheet.getRow(index-1);
					Row dataRow = Sheet.getRow(index);

					//Get Column count for test-1 row
					int iParamCnt = headerRow.getLastCellNum();		 

					
					String key="";
					String value="";

					//Loop through params
					int iCol;
					for(iCol=0;iCol<iParamCnt;iCol++){

						//Fetch the value for the Header Row
						if (headerRow.getCell(iCol, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK) == null)
						{
							key = "";
						}else{
							key = headerRow.getCell(iCol).getStringCellValue();
						}

						//Fetch the value for the Header Row
						if (dataRow.getCell(iCol, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK) == null)
						{
							value = "";
						}else{
							value = dataRow.getCell(iCol).getStringCellValue();
						}

						//Check key value
						Dictionary.put(key,value);
						orgDictionary.put(key,value);

					} 	
				}
			}
			return true;
		}
		catch(Exception e)
		{  e.printStackTrace();
			System.out.println("Exception " + e + " occured while fetching data to execute from Excel datasheet");
			return false;
		}		
	}	

	
	//*****************************************************************************************
	//*	Name		    : fRetrieveExecutionParameters
	//*	Description	    : Retrieve the Execution Parameters from Parameters.xml
	//*	Author		    : Shraddha Girme
	//*	Input Params	: 
	//*	Return Values	: Test Object
	//*****************************************************************************************
	public void fRetrieveExecutionParameters(String driverType){

		try{      
			String xmlPath = Environment.get("ROOTPATH")+"/src/Parameters.xml";
			File fXmlFile = new File(xmlPath);

			DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbFac.newDocumentBuilder();
			Document xmldoc = docBuilder.parse(fXmlFile);

			XPathFactory xPathfac = XPathFactory.newInstance();
			XPath xpath = xPathfac.newXPath();

			//Get QC details
			XPathExpression expr = xpath.compile("//common");		
			NodeList nl = ((NodeList)expr.evaluate(xmldoc, XPathConstants.NODESET)).item(0).getChildNodes();

			for(int child=0; child<nl.getLength(); child++){
				Environment.put(nl.item(child).getNodeName(), nl.item(child).getTextContent());
			}

			//Get Browser/Thread details
			expr = xpath.compile("//"+ driverType.toLowerCase());
			nl = ((NodeList)expr.evaluate(xmldoc, XPathConstants.NODESET)).item(0).getChildNodes();

			for(int child=0; child<nl.getLength(); child++){
				Environment.put(nl.item(child).getNodeName(), nl.item(child).getTextContent());
			}

		}catch(Exception excep){
			System.out.println("Exception occurred while reading XML file for Browser "+ System.getProperty("browserName"));
			System.out.println("Exception :"+excep);
			while(Thread.currentThread().isAlive()){
				Thread.currentThread().interrupt();
			}
		}
	}

	//*****************************************************************************************
	//*	Name		    : fAddTest
	//*	Description	    : Add Test in Test Plan of Quality Center
	//*	Author		    : Shraddha Girme
	//*	Input Params	: 
	//*	Return Values	: Test Object
	//*****************************************************************************************
	public Object fAddTest(String sTestName){
		try{			
			ITDConnection2 QCconn = ClassFactory.createTDConnection();
			QCconn.initConnectionEx(Environment.get("QC_URL"));
			QCconn.login(Environment.get("QC_USER"), Environment.get("QC_PASS"));
			QCconn.connect(Environment.get("QC_DOMAIN"), Environment.get("QC_PROJECT"));
			Object[] sTestParam = new Object[4];
			if (QCconn.projectConnected() == true){

				ITestFactory sTestFactory = (QCconn.testFactory()).queryInterface(ITestFactory.class);
				ITreeManager sTreeManager = (QCconn.treeManager()).queryInterface(ITreeManager.class);
				ISysTreeNode sSysTreeNode = (sTreeManager.nodeByPath(Environment.get("QC_TEST_PLAN")+"/"+ Environment.get("QC_PACKAGE") + "/Automation_Coverage")).queryInterface(ISysTreeNode.class);
				String sTestFactoryFilter = "select TS_TEST_ID from TEST where TS_NAME = '" + sTestName + "' and TS_SUBJECT = " + sSysTreeNode.nodeID();
				IList iListTestCase = sTestFactory.newList(sTestFactoryFilter);

				if (iListTestCase.count() == 0)
				{
					sTestParam[0] = sTestName;
					sTestParam[1] = "QUICKTEST_TEST";
					sTestParam[2] = Environment.get("QC_USER");
					sTestParam[3] = sSysTreeNode.nodeID();
					IBaseField sBaseField = (sTestFactory.addItem(sTestParam)).queryInterface(IBaseField.class);
					sBaseField.post();
					return sBaseField.id();	
				}else{
					Com4jObject comObj = (Com4jObject) iListTestCase.item(1);
					ITest sTest = comObj.queryInterface(ITest.class);
					return sTest.id();
				}
			}else{
				System.out.println("QC Connection Failed");
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	//*****************************************************************************************
	//*	Name		    : fUpdateTestStatQC
	//*	Description	    : Update Test Case Status in Test Set of Quality Center
	//*	Author		    : Shraddha Girme
	//*	Input Params	: 
	//*	Return Values	: Boolean
	//*****************************************************************************************
	public boolean fUpdateTestStatQC(String sTestName, Object sTestid, String sStatus, String sTestSteps, String sOsBrowserVer){
		try{
			ITDConnection2 QCconn= ClassFactory.createTDConnection();
			QCconn.initConnectionEx(Environment.get("QC_URL"));
			QCconn.login(Environment.get("QC_USER"), Environment.get("QC_PASS"));
			QCconn.connect(Environment.get("QC_DOMAIN"), Environment.get("QC_PROJECT"));
			if (QCconn.projectConnected() == true)
			{
				ITestSetFactory sTestSetFactory = (QCconn.testSetFactory()).queryInterface(ITestSetFactory.class);
				ITestSetTreeManager sTestSetTreeManager = (QCconn.testSetTreeManager()).queryInterface(ITestSetTreeManager.class);
				ISysTreeNode sSysTreeNodeTestSet = (sTestSetTreeManager.nodeByPath(Environment.get("QC_TESTSET_FOLDER"))).queryInterface(ISysTreeNode.class);

				String sTestSetFactoryFilter = "select CY_CYCLE_ID from CYCLE where CY_CYCLE = '" + Environment.get("QC_TESTSET") + "' and CY_FOLDER_ID = " + sSysTreeNodeTestSet.nodeID();
				IList  iListTestSets = sTestSetFactory.newList(sTestSetFactoryFilter);

				if (iListTestSets.count() == 0)
				{
					System.out.println("Test Set does not exist");
					return false;
				}else{
					Com4jObject comObjTestSet = (Com4jObject) iListTestSets.item(1);
					ITestSet sTestSet = comObjTestSet.queryInterface(ITestSet.class);
					Com4jObject comObjTSTestSet = (Com4jObject) sTestSet.tsTestFactory();
					IBaseFactory sBaseFactory = (IBaseFactory)comObjTSTestSet.queryInterface(IBaseFactory.class);

					String strTestSetTestFactoryFilter = "SELECT *  FROM TESTCYCL WHERE tc_test_id = " + sTestid + " and TC_cycle_id = " + sTestSet.id();
					IList iListTsTest  = sBaseFactory.newList(strTestSetTestFactoryFilter);
					ITSTest sTSTest;
					if (iListTsTest.count() == 0)
					{
						sTSTest = (sBaseFactory.addItem(sTestid)).queryInterface(ITSTest.class);
						sTSTest.status("No Run");
						sTSTest.post();
					}else{
						Com4jObject comObjTSTest = (Com4jObject) iListTsTest.item(1);
						sTSTest = (ITSTest)comObjTSTest.queryInterface(ITSTest.class);
					}
					IRunFactory sRunFactory = (sTSTest.runFactory()).queryInterface(IRunFactory.class);
					IRun sRun = (sRunFactory.addItem(sTSTest.id())).queryInterface(IRun.class);

					//24-Feb-2014 - Dina - update fields: RN_USER_01=Desktop/Mobile, RN_USER_02=Browser Version <MSIE 10.x>, RN_OS_NAME = OS name <Windows 7>, <iOS 6.1.4>
					String OsBrowserVer[] = sOsBrowserVer.split(";");
					sRun.field("RN_USER_01", OsBrowserVer[0]);
					sRun.field("RN_USER_02", OsBrowserVer[1]);
					sRun.field("RN_OS_NAME", OsBrowserVer[2]);
					sRun.status(sStatus);
					//sRunid = sRun.id();
					sRun.post();

					String[] arrTestSteps = sTestSteps.split("\\<NS\\>");

					for (int i = 0; i < arrTestSteps.length; i++){
						String[] arrStep = arrTestSteps[i].split("\\<ND\\>");
						if(arrStep.length==4){
							IBaseFactory sTestStepFactory = (sRun.stepFactory()).queryInterface(IBaseFactory.class);
							IStep sTestStep = (sTestStepFactory.addItem(sTestName)).queryInterface(IStep.class);
							sTestStep.field("ST_STEP_NAME", arrStep[0]);
							sTestStep.field("ST_DESCRIPTION", arrStep[0]);
							sTestStep.field("ST_EXPECTED", arrStep[1]);
							sTestStep.field("ST_ACTUAL", arrStep[2]);
							if(arrStep[3].equals("Pass")){
								sTestStep.status("Passed");
							}else if(arrStep[3].equals("Fail")){
								sTestStep.status("Failed");
							}else{
								sTestStep.status("Passed");
							}
							sTestStep.post();
						}
						else{
							System.out.println("fUpdateTestStatQC: Step data should have 4 parameters. " + arrTestSteps[i]);
						}
					}
				}			
			}else{
				System.out.println("QC Connection Failed");
				return false;
			}
			return true;
		}catch(Exception e){
			System.out.println("Exception occured in fUpdateTestStatQC");
			e.printStackTrace();
			return false;
		}
	}

	//*****************************************************************************************
	//*	Name		    : fGetWebDriver
	//*	Description	    : Returns the required webdriver
	//*	Author		    : Aniket Gadre
	//*	Input Params	: None
	//*	Return Values	: WebDriver 
	//*****************************************************************************************
	public WebDriver fGetWebDriver(String driverType, String strMethod) throws MalformedURLException
	{
		//Define webdriver
		if (driverType.contains("CHROME"))
		{	
			// to start chrome in incognito mode and close the IETab.net - Amit Kumar (Automation)
			WebDriver driver = null;
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();     
			options.addArguments("start-maximized");
			//options.addArguments("--disable-extensions");
//			if(!Dictionary.get("BROWSER_MODE").equals("PLANE_BROWSER")){
//				options.addArguments("-incognito");
//			}
			options.addArguments("ignore-certificate-errors");
//			options.setCapability("download.prompt_for_download", false);
//			options.setCapability("download.directory_upgrade", true);
//			options.setCapability("plugins.always_open_pdf_externally", true);
//			options.setCapability("download.default_directory", "C:\\Users\\LVARDHAN\\Downloads\\");
			capabilities.setCapability(ChromeOptions.CAPABILITY,options);
			if(Dictionary.get("APP_DATA_SAVE").equals("YES")){
				options.addArguments("user-data-dir="+System.getProperty("user.home")+"/AppData/Local/Temp/");
			}
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/exe/chromedriver.exe");  

			driver = new ChromeDriver(options);
//			driver.get("chrome://extensions-frame");
//			if(!driver.findElement(By.xpath("//input[@type='checkbox']/ancestor::label[@class='incognito-control']")).isSelected()){
//				driver.findElement(By.xpath("//input[@type='checkbox']/ancestor::label[@class='incognito-control']")).click(); 
//			}
			return driver;

		}
		else if(driverType.contains("ANDROID_EMULATION")){
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", Environment.get("EMULATION_DEVICE_NAME"));
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			capabilities.setAcceptInsecureCerts(true);
//			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/exe/chromedriver.exe");
			return new ChromeDriver(capabilities);
//			ChromeOptions chromeOptions=new ChromeOptions();
//			chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
//			return new ChromeDriver(chromeOptions);
		}
		else if(driverType.contains("IOS_EMULATION")){
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", Environment.get("EMULATION_DEVICE_NAME"));
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			capabilities.setAcceptInsecureCerts(true);
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/exe/chromedriver.exe");
			return new ChromeDriver(capabilities);
		}
		else if (driverType.contains("FIREFOX")){
			try {
				Runtime.getRuntime().exec("taskkill /F /IM authentication_32bit.exe");
				Runtime.getRuntime().exec("taskkill /F /IM authentication_64bit.exe");

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			FirefoxBinary binary = new FirefoxBinary();
			FirefoxProfile profile = new FirefoxProfile(); 
			LoggingPreferences logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/exe/geckodriver.exe");
			//added by sagar Ruikar 
			capabilities.setCapability("marionette", true);
			capabilities.setCapability("acceptInsecureCerts",true);
			
			WebDriver driver = null;
			try{
				org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
				proxy.setProxyType(ProxyType.AUTODETECT);
				capabilities.setCapability(CapabilityType.PROXY, proxy);
				driver = new FirefoxDriver(capabilities);
			}catch(Exception e){
				System.out.println("Error in launching firefox: "+e);
			}
			return driver;
		}

		else if(driverType.contains("EDGE")){
			try {
				Runtime.getRuntime().exec("taskkill /F /IM MicrosoftWebDriver.exe");
				//Runtime.getRuntime().exec("taskkill /F /IM authenticationIE32.exe");
				//Runtime.getRuntime().exec("taskkill /F /IM authenticationIE64.exe");
				Process p = Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 2");
				p.waitFor();
			} catch (Exception e) {
				//catch (IOException e) {
				e.printStackTrace();
			}
			WebDriver driver = null;
//			EdgeOptions options = new EdgeOptions();  
			DesiredCapabilities capabilities = DesiredCapabilities.edge();
			capabilities.setCapability("acceptInsecureCerts",true);
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir")+"/exe/msedgedriver.exe");
/*			EdgeDriverService service = new EdgeDriverService.Builder()
			.usingDriverExecutable(new File(System.getProperty("user.dir")+"/exe/MicrosoftWebDriver.exe"))
			.usingAnyFreePort()
			.build();
			try {
				service.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			
			driver = new EdgeDriver(capabilities);
			return driver;
		}
		
		else if (driverType.contains("IE")){
			try {
				Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
				Runtime.getRuntime().exec("taskkill /F /IM authenticationIE32.exe");
				Runtime.getRuntime().exec("taskkill /F /IM authenticationIE64.exe");
				Process p = Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 2");
				//Process p = Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
				p.waitFor();
			} catch (IOException | InterruptedException e) {
				//catch (IOException e) {
				e.printStackTrace();
			}

			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"/exe/IEDriverServer.exe");
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			caps.setCapability("ignoreZoomSetting", true);
//			caps.setCapability("nativeEvents",false);
			//caps.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);  
			//caps.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
			return new InternetExplorerDriver(caps);
		}
		else if(driverType.contains("ANDROID")){
/*			try {
				fGuiLaunchAppium();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception occured " + e);
			}*/
			//DesiredCapabilities dc;
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("browserName", "chrome"); 
			caps.setCapability("deviceConnectIgnoreSession", true);
			caps.setCapability("deviceConnectUserName", Dictionary.get("MOBILELABSUSER"));
			caps.setCapability("deviceConnectApiKey", Dictionary.get("MOBILELABSTOKENID"));
			caps.setCapability("udid", Dictionary.get("MOBILELABSUDID"));
			return new RemoteWebDriver( new URL("http://" + Dictionary.get("MOBILELABSIPADDR") + "/Appium"),caps);	

			//			dc=new DesiredCapabilities();
			//			dc.setCapability("app","Chrome");
			//			dc.setCapability(CapabilityType.BROWSER_NAME,"");
			//			dc.setCapability("device", "Android");
			//			dc.setCapability(CapabilityType.VERSION,Environment.get("ANDROID_VERSION"));
			//			dc.setCapability("device ID", Environment.get("ANDROID_ID"));
			//			dc.setCapability(CapabilityType.PLATFORM,"WINDOWS");
			//			// amit added
			//			dc.setCapability("platformName","Android");
			//			dc.setCapability("deviceName","Samsung Galaxy");			
			//			dc.setCapability("newCommandTimeout",60000);


			//return new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub/"), caps);	 			
		}
		else if(driverType.contains("IOS")){

			//			try {
			//				Start_appiumAndriod_ioswebkit_appiumiOSProcessonMac();
			//			} catch (Exception e) {
			//				e.printStackTrace();
			//				System.out.println("Exception occured " + e);
			//			}
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("browserName", "Safari");
			caps.setCapability("deviceConnectIgnoreSession", true);
			caps.setCapability("deviceConnectUserName", Environment.get("MOBILELABSUSER"));
			caps.setCapability("deviceConnectApiKey", Environment.get("MOBILELABSTOKENID"));
			caps.setCapability("udid", Environment.get("MOBILELABSUDID"));
			return new RemoteWebDriver( new URL("http://" + Environment.get("MOBILELABSIPADDR") + "/Appium"),caps);	
/*			caps.setCapability("deviceConnectUserName", "ms920m@att.com");
			caps.setCapability("deviceConnectApiKey", "95538adf-6db3-4a11-9a67-48c7e8d02f10");
			caps.setCapability("udid", "7162effe054ccca65f7ec6e8b9334650611a2fa9");
			return new RemoteWebDriver( new URL("http://135.165.46.72/Appium"),caps);	*/		

			//			DesiredCapabilities caps = new DesiredCapabilities();
			//			caps.setCapability("app", "safari");
			//			caps.setCapability("newCommandTimeout", 100);
			//
			//			return new RemoteWebDriver(new URL("http://"+ Environment.get("MAC_IP") +":"+Environment.get("APPIUM_PORT")+"/wd/hub"),caps);
		} 
//		else if (driverType.contains("PHANTOM")){	
//			String strOS = System.getProperty("os.name");
//			if(!strOS.contains("Windows")){
//				DesiredCapabilities caps = new DesiredCapabilities();
//
//				caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, System.getProperty("user.dir")+"/exe/phantomjs");
//				caps.setCapability("takesScreenshot", true);
//
//				//TODO pick up the proxy config from Environments.xlsx
//				ArrayList<String> cliArgsCap = new ArrayList<String>();
//				cliArgsCap.add("--proxy=" + getProxyServer());
//				cliArgsCap.add("--proxy-auth=");
//				cliArgsCap.add("--proxy-type=http");
//				caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
//
//				return new PhantomJSDriver(caps);
//			}else{
//				DesiredCapabilities caps = new DesiredCapabilities();
//				caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, System.getProperty("user.dir")+"/exe/phantomjs.exe");
//				caps.setCapability("takesScreenshot", true);
//
//				//TODO pick up the proxy config from Environments.xlsx
//				ArrayList<String> cliArgsCap = new ArrayList<String>();
//				cliArgsCap.add("--proxy=" + getProxyServer());
//				cliArgsCap.add("--proxy-auth=");
//				cliArgsCap.add("--proxy-type=http");
//				caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
//
//				return new PhantomJSDriver(caps);
//			}
//		}
		else{
			System.out.println("Driver type " + driverType + " is invalid");
			return null;
		}
	}

	private String getProxyServer() {

		String proxy = Environment.get("PROXY_SERVER");
		System.out.println("Driver.getProxyServer() --> Getting proxy settings from Environments.xslx for PhantomJS as: " + proxy);
		if (proxy != null) {
			return proxy;
		} else {
			return DEFAULT_PROXY_SERVER;
		}
	}
	//*****************************************************************************************
	//*	Name		    : fUpdateTestCaseRowSkip
	//*	Description	    : update skip row with status
	//*	Author		    : Dina Dodin
	//*	Input Params	: row - number of the current row executed 
	//*	Return Values	: WebDriver 
	//*****************************************************************************************
	public void fUpdateTestCaseRowSkip(int row)//for second time calling in TC pass the SKIPROW_drierType value
	{   
		final String dataSheet = dataSheetsPath + "/" + Environment.get("CLASS_NAME") + ".xlsx";
		final String mainSheet = "MAIN";

		try{  
			//Create the FileInputStream object             
//			FileInputStream file = new FileInputStream(new File(dataSheet));             
			//Get the workbook instance for XLS file 
//			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get MAIN sheet from the workbook
//			Sheet sheet = workbook.getSheet(mainSheet);

			//update skip row according to status
//			sheet.getRow(row-1).getCell(fGetColumnIndex(dataSheet, mainSheet, "SKIP_" + driverType)).setCellValue("X_"+driverType);
//			System.out.println("Cell value to be set: "+Dictionary.get("RESULT_"+driverType));
//			System.out.println("Row: "+row);
//			sheet.getRow(4).getCell(fGetColumnIndex(dataSheet, mainSheet, "SKIP_" + driverType)).setCellValue(Dictionary.get("RESULT_"+driverType)); //Dictionary.get("RESULT_"+driverType)
//
//			System.out.println("UpdateSkip() - " + driverType + " " + row);
//			System.out.println();
//
//			file.close();
//
//			FileOutputStream outFile =new FileOutputStream(new File(dataSheet));
//			workbook.write(outFile);
//			outFile.close();    
			
			Fillo fillo = new Fillo();
			com.codoid.products.fillo.Connection conn=fillo.getConnection(dataSheet);
			
			conn.executeUpdate("Update "+mainSheet+" set SKIP_"+driverType+"='"+"X_"+driverType+"' where ID="+(row-1));
			conn.executeUpdate("Update "+mainSheet+" set SKIP_"+driverType+"='"+Dictionary.get("RESULT_"+driverType)+"' where ID="+row);
			
			conn.close();

		}catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
			Thread.currentThread().interrupt();
		}
	}
	public String  FindOSBrowserVer(WebDriver driver)
	{
		String sUserAgentString = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent");
		String sAutoType = "Unknown"; //RN_USER_01
		String sBrowserVer = "0.0"; //RN_USER_02
		String sOSName = "Unknown"; // RN_OS_NAME

		if (driverType.contains("IE"))
		{	
			sAutoType = "Desktop";
			sOSName = System.getProperty("os.name");
			if (sUserAgentString.contains("Trident"))
			{
				sBrowserVer = "IE11";
			}
			else
			{
				sBrowserVer = sUserAgentString.substring(sUserAgentString.indexOf("MSIE"),sUserAgentString.indexOf("MSIE")+8);
			}
		}
		else if (driverType.contains("FIREFOX"))
		{
			sAutoType = "Desktop";
			sOSName = System.getProperty("os.name");
			sBrowserVer = sUserAgentString.substring(sUserAgentString.indexOf("Firefox"),sUserAgentString.indexOf("Firefox")+12).replace("/", " ");
		}
		else if (driverType.contains("CHROME"))
		{
			sAutoType = "Desktop";
			sOSName = System.getProperty("os.name");
			sBrowserVer = sUserAgentString.substring(sUserAgentString.indexOf("Chrome"),sUserAgentString.indexOf("Chrome")+11).replace("/", " ");
		}
		else if (driverType.contains("ANDROID"))
		{
			sAutoType = "Mobile";
			sOSName = Environment.get("OS_NAME");
			sBrowserVer = Environment.get("DEVICE_NAME");
		}
		else if (driverType.contains("IOS"))
		{	
			sAutoType = "Mobile";
			sOSName = Environment.get("OS_NAME");
			sBrowserVer = Environment.get("DEVICE_NAME");
		}

		return sAutoType + ";" + sBrowserVer + ";" + sOSName;

	}



	//*****************************************************************************************
	//*    Name        	  : fetchEnvironmentDetails
	//*    Description    : Fetches EnvDetails from Environments.xls and loads it into hashmap 
	//*    Author         : Aniket Gadre / Updates By Dina
	//*    Input Params   : None
	//*    Return Values  : None
	//*****************************************************************************************
	public boolean fetchEnvironmentDetails() {

		try {
			int iEnvironment = -1;
			boolean bFlag = false;

			String enviromentsExcel = Environment.get("ENVIRONMENTXLSPATH") + "/Environments.xlsx";
			//Get the Column Index for the ENVIRONMENT Column
//			iEnvironment = fGetColumnIndex(enviromentsExcel, "ENVIRONMENTS", "ENVIRONMENT");
//
//			//Check if the index value is proper
//			if (iEnvironment == -1 ){
//				System.out.println("Failed to find the Environment Column in the file " + enviromentsExcel);
//				return false;
//			}

			//Create the FileInputStream obhect			
			FileInputStream file = new FileInputStream(new File(enviromentsExcel));		     
			//Get the workbook instance for XLS file 
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			//Get first sheet from the workbook
			Sheet sheet = workbook.getSheet("ENVIRONMENTS");

			//Get the Number of Rows
			int iRowNum = sheet.getLastRowNum();

			//Get the Column count
			int iColCount = sheet.getRow(0).getLastCellNum();

			/***** Get Environment Parameters From ENVIRONMENTS sheet ***************/
			
			for(int i=0;i<iColCount;i++){
				if(sheet.getRow(0).getCell(i).getStringCellValue().trim().toUpperCase().equals(Environment.get("ENV_CODE"))){
					bFlag = true;
					String strKey = "";
					String strValue = "";
					for(int j=1; j<=iRowNum;j++){
						strKey=sheet.getRow(j).getCell(0).getStringCellValue().trim();
						if(sheet.getRow(j).getCell(i, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK)==null)
							strValue="";
						else
							strValue=sheet.getRow(j).getCell(i).getStringCellValue();
						
						Environment.put(strKey.trim(), strValue.trim());
					}
					break;
				} else
					continue;
			}
			
			/***** Get Common Parameters From COMMON PARAMETERS sheet ***************/
			
			sheet=workbook.getSheet("COMMON_PARAMETERS");
			iRowNum=sheet.getLastRowNum();
			iColCount=sheet.getRow(0).getLastCellNum();
			
			String strKey = "";
			String strValue = "";
			for(int j=1; j<=iRowNum;j++){
				strKey=sheet.getRow(j).getCell(0).getStringCellValue().trim().toUpperCase();
				if(sheet.getRow(j).getCell(1, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK)==null)
					strValue="";
				else
					strValue=sheet.getRow(j).getCell(1).getStringCellValue();

				Environment.put(strKey.trim(), strValue.trim());
			}
						
			
//			/***** Get Credentials From CREDENTIALS sheet For the brand configured for execution ***************/
//			
//			sheet=workbook.getSheet("CREDENTIALS_"+Environment.get("LOGIN_BRAND"));
//			iRowNum=sheet.getLastRowNum();
//			iColCount=sheet.getRow(0).getLastCellNum();
//			
//			GetCredentials:
//				for(int k=0;k<iColCount;k++){
//					if(sheet.getRow(1).getCell(k).getStringCellValue().trim().toUpperCase().equals(MainDriver.getApplicationName())){
//						if(sheet.getRow(0).getCell(k).getStringCellValue().trim().toUpperCase().equals(Environment.get("ENV_CODE"))){
//							bFlag = true;
//							strKey = "";
//							strValue = "";
//							for(int j=2; j<=iRowNum;j++){
//								strKey=sheet.getRow(j).getCell(0).getStringCellValue().trim().toUpperCase();
//								if(sheet.getRow(j).getCell(k, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK)==null)
//									strValue="";
//								else
//									strValue=sheet.getRow(j).getCell(k).getStringCellValue();
//								
//								Environment.put(strKey.trim(), strValue.trim());
//							}
//							break GetCredentials;
//						} else
//							continue;
//					}
//				}
//				
//			/************Read the URLS sheet**************************/ //Added by Kaiqi Tang 05/04/2015
//			try{
//				sheet = workbook.getSheet("URLs");
//				//Get the Number of Rows
//				iRowNum = sheet.getLastRowNum();
//				//Get the Column count
//				iColCount = sheet.getRow(0).getLastCellNum();
//
//				for (int iRow = 1; iRow <= iRowNum; iRow++){
//					String key = sheet.getRow(iRow).getCell(0).getStringCellValue();
//					String value = sheet.getRow(iRow).getCell(1).getStringCellValue();
//					Environment.put(key, value);
//				}
//
//			}
//			catch(Exception e){
//				System.out.println("URLs sheet can't be read");
//				e.printStackTrace();
//			}

			//Close the file
			file.close();

			//If bFlag is true
			if (bFlag == false)
			{
				System.out.println("Environment Code " + Environment.get("ENV_CODE") + " not found in the Environment xls");
				return false;
			}

			return true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}



	//*****************************************************************************************
	//*	Name		    : StartTest
	//*	Description	    : Start Appium through Windows
	//*	Author		    : Abhijeet Gorecha
	//*	Date Created	    : July 28, 2014
	//*	Input Params	    : 
	//*	Return Values	    : Test Object
	//*****************************************************************************************
	public void fGuiLaunchAppium() throws Exception{
		fGuiKillAppiumProcess();	
		runTime = Runtime.getRuntime();
		//System.setProperty("user.dir", "C:/Appium/Appium");

		/*CodeSource codesource = Driver.class.getProtectionDomain().getCodeSource();
		File jarFile;
		jarFile = new File(codesource.getLocation().toURI().getPath());
		String jarDir = jarFile.getParentFile().getPath();*/

		nodeProcess = runTime.exec("node "+"C:/Appium/Appium/node_modules/appium/lib/server/main.js --address 127.0.0.1");
		Thread.sleep(15000);
	}

	//*****************************************************************************************
	//*	Name		    : StartTest
	//*	Description	    : Kill Appium through Windows
	//*	Author		    : Abhijeet Gorecha
	//*	Date Created	    : July 28, 2014
	//*	Input Params	    : 
	//*	Return Values	    : Test Object
	//*****************************************************************************************	
	public boolean fGuiKillAppiumProcess() throws Exception{
		if(nodeProcess== null){
			return true;
		}
		else{
			nodeProcess.destroy();
			return true;
		}
	}

	//*********************************************************************************************************
	//*	//Name		    : Start_appiumAndriod_ioswebkit_appiumiOSProcessonMac
	//*	Description	    : Starts Appium server and ios webkit on MAC through windows based on driver type
	//*	Author		    : Naveena Basetty
	//*	Input Params	: None
	//*	Return Values	: void
	//*******************************************************************************************************	
	public void Start_appiumAndriod_ioswebkit_appiumiOSProcessonMac() throws JSchException, IOException
	{
		jsch=new JSch();	
		session=jsch.getSession(Environment.get("USER_NAME"), Environment.get("MAC_IP"), 22);
		session.setPassword(Environment.get("MAC_PWD"));

		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();


		if (driverType.contains("ANDROID"))		{		   
			channelandroid=(ChannelExec) session.openChannel("exec");
			//Parameters to pass :  --devicename -p -bp --chromeport -a
			//channelandroid.setCommand("source ~/.bash_profile;./startappiumioswebkit.sh android " + Environment.get("deviceid") + " "+
			channelandroid.setCommand("source ~/.profile;./startappiumioswebkit.sh android " + Environment.get("deviceid") + " "+
					Environment.get("appiumPort") + " " +
					Environment.get("bootstrap") +  " " +
					Environment.get("chromeport") + " " +
					Environment.get("address") + " " + "> /dev/null 2>&1 &");
			channelandroid.connect();
			channelandroid.disconnect();

		}else if (driverType.contains("IOS")){
			//ios webkit
			channelios=(ChannelExec) session.openChannel("exec");
			channelios.setCommand("./startappiumioswebkit.sh ios "+ Environment.get("IOS_ID") + "  > /dev/null 2>&1 &");
			channelios.connect();
			channelios.disconnect();
		}

		session.disconnect();


	}
	//*****************************************************************************************
	//*	//Name		    : DisconnectioswebkitProcess
	//*	Description	    : Disconnects the appium processes running on MAC machine
	//*	Author		    : Naveena Basetty
	//*	Input Params	: None
	//*	Return Values	: void
	//****************************************************************************************
	public void DisconnectioswebkitProcess( ) throws JSchException
	{

		jsch=new JSch();	
		session=jsch.getSession(Environment.get("MAC_NAME"), Environment.get("MAC_IP"), 22);
		session.setPassword(Environment.get("MAC_PWD"));

		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();

		channelios=(ChannelExec) session.openChannel("exec");
		channelios.setCommand("ps aux | grep webkit  | awk '{print $2}'| xargs kill -9;");
		channelios.connect();
		channelios.disconnect();
		session.disconnect();

	}

	//*****************************************************************************************
	//*	Name		    : getSkipedCells
	//*	Description	    : Function to get non-Skiped Cells
	//*	Author		    : Kapish Kumar
	//* Input Params	: 
	//*	Return Values	: int
	//***********************************************************************
	public int getSkipedCells() {

		int cellskipped = 0;
		Sheet sh = Sheet;
		int totalRowCount = sh.getLastRowNum();
		int totalCellCount = sh.getRow(0).getLastCellNum();

		int x = 0;
		int driverTypeCellNumber = 0;
		int ACTIONCellNumber = 0;

		for(Row r : sh) {
			for(x=0 ; x < totalCellCount+1; x++){
				Cell c = r.getCell(x);
				driverTypeCellNumber++;
				if (c.getStringCellValue().contains(driverType)) {
					for(Row rAction : sh){
						for(x = 0; x < totalCellCount+1; x++){
							Cell cAction = rAction.getCell(x);
							ACTIONCellNumber++;
							if(cAction.getStringCellValue().contains("ACTION")){

								break;
							}
						}
						break;
					}
					break;
				}
			}
			break;
		}
		/////////////////////////////
		Iterator<Row> rowIter = sh.rowIterator();
		Row row = sh.getRow(0);
		while (rowIter.hasNext()) {
			for (int i = 0; i < totalRowCount; i++) {
				row = sh.getRow(i);
				String headerACTION =null;
				try{ headerACTION=row.getCell(ACTIONCellNumber-1).getStringCellValue();
				}catch(NullPointerException e){
					
				}
//				System.out.println("Value: "+row.getCell(driverTypeCellNumber-1).getStringCellValue());
//				System.out.println("Rich Value: "+row.getCell(driverTypeCellNumber-1).getRichStringCellValue());
				String headerDriverType = null; 
				try{	headerDriverType=row.getCell(driverTypeCellNumber-1).getStringCellValue();
				}catch(NullPointerException e){
					headerDriverType="";
				}
				try{
					if (headerACTION.equals("ACTION") && headerDriverType.equals("")) {
						QCHashMap.put("COUNT_SKIP", ""+i);
						//arlst.add(QCHashMap.get("COUNT_SKIP"));
						cellskipped++;
						arlst.add(cellskipped);
						//					System.out.println(i);
					}
				}catch(NullPointerException e){
					
				}
			}
			break;
		}
		/////////////////////////////
//		System.out.println("Number of @Test to be executed for "+ driverType.replace("1", "")+" driver type"+" : "+cellskipped);
		//fis.close();

		// deletes summary report.text file
		try {
			DeleteSummaryReportTxtFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//killChromeDriver();
		//killAuthPopUp();
		return cellskipped;
	}

	
	//*****************************************************************************************
	//*	Name		    : getNextRecordIndex
	//*	Description	    : Function to next Record Index to be executed
	//*	Author		    : Zachi Gahari
	//* Input Params	: 
	//*	Return Values	: Int
	//***********************************************************************
	public int getNextRecordIndex() {

		int index = 0;

		//Get Sheet from data calendar
		Sheet sh = fGetDataSheet(Environment.get("CLASS_NAME"));
		int totalRowCount = sh.getLastRowNum();
		int totalCellCount = sh.getRow(0).getLastCellNum();

		int x = 0;
		int driverTypeCellNumber = 0;
		int ACTIONCellNumber = 0;
		
		// Get SKIP and ACTION column index
		for(Row r : sh) {
			for(x=0 ; x < totalCellCount+1; x++){
				Cell c = r.getCell(x);
				driverTypeCellNumber++;
				if (c.getStringCellValue().contains(driverType)) {
					for(Row rAction : sh){
						for(x = 0; x < totalCellCount+1; x++){
							Cell cAction = rAction.getCell(x);
							ACTIONCellNumber++;
							if(cAction.getStringCellValue().contains("ACTION")){
								break;
							}
						}
						break;
					}
					break;
				}
			}
			break;
		}
		
		/////////////////////////////
		Iterator<Row> rowIter = sh.rowIterator();
		Row row = sh.getRow(0);
		while (rowIter.hasNext()) {
			for (int i = 0; i < totalRowCount; i++) {
				row = sh.getRow(i);
				String headerACTION = null; 
				try{ headerACTION=row.getCell(ACTIONCellNumber-1).getStringCellValue();
				}catch(NullPointerException e){
					
				}
				String headerDriverType=null;
				try{ headerDriverType = row.getCell(driverTypeCellNumber-1).getStringCellValue();
				}catch(NullPointerException e){
					headerDriverType="";
				}
				try{
					if (headerACTION.equals("ACTION") && headerDriverType.equals("")) {
						row = sh.getRow(i+1);
						try{
							if (row.getCell(driverTypeCellNumber-1).getStringCellValue().equals("")) {
								index = i+1;
								break;
							}
						} catch(NullPointerException e){
							index = i+1;
							break;
						}

					}
				}catch(NullPointerException e){
			 }
			}
			break;
		}

		return index;
	}


	//Dina
	public void fGuiHandleAuth(String borwser)
	{

		String sAuthFilePath = System.getProperty("user.dir") + "/exe/";
		try{
			String sFile1 = "";
			String sFile2 = "";
			String sFile3 = "";
			File file;

			if (System.getProperty("os.arch").contains("86"))
			{
				sFile1 = sAuthFilePath + "authentication_32bit.exe";
				sFile2 = sAuthFilePath + "authenticationChrome32.exe";
				sFile3 = sAuthFilePath + "authenticationIE32.exe";

			}
			else if (System.getProperty("os.arch").contains("64"))
			{
				sFile1 = sAuthFilePath + "authentication_64bit.exe";
				sFile2 = sAuthFilePath + "authenticationChrome64.exe";
				sFile3 = sAuthFilePath + "authenticationIE64.exe";
			}


			if (borwser.contains("FIREFOX"))
			{
				file = new File(sFile1);

				if (file.exists())
				{
					Runtime.getRuntime().exec(sFile1);
				}
			}
			else if (borwser.contains("CHROME"))
			{
				file = new File(sFile2);
				if (file.exists())
				{
					
					Runtime.getRuntime().exec(sFile2);
				}
			}

			else if (borwser.contains("IE"))
			{
				file = new File(sFile3);
				if (file.exists())
				{
					Runtime.getRuntime().exec(sFile3);
				}
			}

		}
		catch (IOException e)
		{
			//do nothing
		}
	}

	/*@SuppressWarnings("null")
	public boolean g()

	{
		Ldtp ff = null;
		if (ff.closeWindow("Authentication Required")==1)
		{
			return false;
		}

		return true;
	}*/

	//*****************************************************************************************
	//*	Name		    : copyDriverToTrash
	//*	Description	    : copy Driver To Trash
	//*	Author		    : Kapish Kumar
	//* Input Params	: strTestName
	//*	Return Values	: newName
	//*	Date Created	: Mar 27, 2015
	//***********************************************************************
	public File copyDriverToTrash(String strTestName) throws IOException{
		File file = new File("c:/chromedriver.exe");
		File destinationDir = new File(System.getProperty("user.dir")+"/trash/");

		FileUtils.copyFileToDirectory(file, destinationDir, true);

		File oldName = new File(destinationDir+"/"+"chromedriver.exe");
		File newName = new File(destinationDir+"/"+username+"chromedriver_"+strTestName+".exe");
		if(newName.exists()){
			Runtime.getRuntime().exec("taskkill /F /IM "+newName.getName()+" /t");
			newName.delete();
			if(oldName.renameTo(newName)) {
				System.out.println("");
			} else {
				System.out.println("Error in renaming chromedriver");
			}
		}else{
			if(oldName.renameTo(newName)) {
				System.out.println("");
			} else {
				System.out.println("Error in renaming chromedriver");
			}
		}
		return newName;
	}

	//*****************************************************************************************
	//*	Name		    : killDriver
	//*	Description	    : kill Driver
	//*	Author		    : Kapish Kumar
	//* Input Params	: strDriverNameForTest
	//*	Return Values	: 
	//*	Date Created	: Mar 27, 2015
	//***********************************************************************
	public void killDriver(String strDriverNameForTest) {
		List<Object> map = new ArrayList<Object>();
		try {
			String line="";
			Process p=Runtime.getRuntime().exec("tasklist");
			BufferedReader buf=new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = buf.readLine()) != null)
			{
				map.add(line+"\n");
				if(line.contains(strDriverNameForTest.split("driver")[0])){
					//Runtime.getRuntime().exec("taskkill /F /IM "+strDriverNameForTest +" /t");
					Runtime.getRuntime().exec("wmic process where name='"+strDriverNameForTest+"' delete");
					System.out.println("Rogue chromedriver.... killed");
				} 
			} 				
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}

	//*****************************************************************************************
	//*	Name		    : DeleteItemsInTrashFolder
	//*	Description	    : Delete Items In Trash Folder
	//*	Author		    : Kapish Kumar
	//* Input Params	: 
	//*	Return Values	: 
	//*	Date Created	: Mar 31, 2015
	//***********************************************************************
	public void DeleteItemsInTrashFolder(){
		try {
			File file = new File(System.getProperty("user.dir")+"/trash");
			String processWMIC = "wmic path win32_process Where "+"\"Caption Like '%_chromedriver_%'\""+" Call Terminate"; 
			String processAuthentication = "wmic path win32_process Where "+"\"Caption Like '%authentication%'\""+" Call Terminate";
			if(file.exists()){
				for(File f: file.listFiles()) {
					// wmic path win32_process Where "Caption Like '%java.exe%' AND CommandLine Like '%selenium.jar%'" Call Terminate
					f.setWritable(true);
					try{Thread.sleep(500);}catch(Exception e){e.printStackTrace();}
					Runtime.getRuntime().exec(processWMIC);
					try{Thread.sleep(750);}catch(Exception e){e.printStackTrace();}
					FileDeleteStrategy.FORCE.delete(f);
					try{Thread.sleep(750);}catch(Exception e){e.printStackTrace();}
				}
			}
			try{Thread.sleep(500);}catch(Exception e){e.printStackTrace();}
			Runtime.getRuntime().exec(processAuthentication);
			try{Thread.sleep(500);}catch(Exception e){e.printStackTrace();}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//*****************************************************************************************
	//*	Name		    : killChromeDriver
	//*	Description	    : kill Chrome Driver
	//*	Author		    : Kapish Kumar
	//* Input Params	: 
	//*	Return Values	: 
	//*	Date Created	: Mar 31, 2015
	//***********************************************************************
	public void killChromeDriver(){
		try{
			Runtime.getRuntime().exec("wmic path win32_process Where "+"\"Caption Like '%authentication%'\""+" Call Terminate");

			Runtime.getRuntime().exec("wmic path win32_process Where "+"\"Caption Like '%chromedriver%'\""+" Call Terminate");

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void killAuthPopUp(){
		try{

			Runtime.getRuntime().exec("wmic path win32_process Where "+"\"Caption Like '%authentication%'\""+" Call Terminate");

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//*****************************************************************************************
	//*	Name		    : DeleteSummaryReportTxtFile
	//*	Description	    : Delete Summary Report Txt File
	//*	Author		    : Kapish Kumar
	//* Input Params	: 
	//*	Return Values	: 
	//*	Date Created	: April 10, 2015
	//***********************************************************************
	public void DeleteSummaryReportTxtFile() throws IOException{
		File file = new File(System.getProperty("user.dir") + "/summary_report.txt");
		if(file.exists()){
			FileDeleteStrategy.FORCE.delete(file);
		}
	}

	//*****************************************************************************************
	//*    Name            : fnCreateUnixlogValidationReport
	//*    Description        : The function creates the log validation report
	//*    Author            : Naveena Basetty
	//*    Input Params    : None
	//*    Return Values    : Classname
	//*****************************************************************************************
	public void fnCreateUnixlogValidationReport(String Classname) {

		try{
			//copy the xls to unixlogvalidation folder
			String source = dataSheetsPath + "/" + Classname + ".xlsx"; 
			String destination = Environment.get("UNIXLOGSFOLDERPATH")  + "/" + Classname + ".xlsx"; 
			Environment.put("UNIXERRORLOGFOLDERPATH", destination);

			System.out.println(source + " " + destination);

			File f = new File(destination);
			if(!f.exists()){
				f.mkdirs();
				Path FROM = Paths.get(source);
				Path TO = Paths.get(destination);


				Files.copy(FROM, TO, StandardCopyOption.REPLACE_EXISTING);
				//Add a datasheet and 3 columns to the data file - test_name,session_name,session_ID,Errors
				FileInputStream input= new FileInputStream(destination);
				XSSFWorkbook workbook = new XSSFWorkbook(input);
				XSSFSheet sh = workbook.createSheet("UNIX_LOG_VALIDATIONS");
				sh.createRow(0).createCell(0).setCellValue("TEST_NAME");
				sh.getRow(0).createCell(1).setCellValue("SESSION_NAME");
				sh.getRow(0).createCell(2).setCellValue("SESSION_ID");
				sh.getRow(0).createCell(3).setCellValue("ERRORS");
				sh.getRow(0).createCell(4).setCellValue("DATE");

				FileOutputStream savefile = new FileOutputStream(destination);
				workbook.write(savefile);
				savefile.close(); 

			}  
			else
				System.out.println("File exists");


		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	//*****************************************************************************************
	//* Name            : UpdateTestFlowErrorsToUNIXLogFile
	//* Description     : Function to update unix logs errors if the test passes in chrome
	//*					logs aren't validated for other browser types - IE and Firefox
	//* CREATED RELEASE : 
	//* LAST MODIFIED IN :
	//* Author          : Naveena Basetty
	//* Input Params    : 
	//* Return Values   : 
	//*****************************************************************************************
	public void UpdateTestFlowErrorsToUNIXLogFile(){
		try{
			String TestCase = Driver.QCHashMap.get("TEST_NAME_");
			String SessionName = Environment.get("SESSION_NAME");
			String SessionID = Environment.get("SESSION_ID");
			String Errors = Environment.get("LOG_ERRORS");

			String destination = Environment.get("UNIXERRORLOGFOLDERPATH");
			FileInputStream input= new FileInputStream(destination);
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			XSSFSheet sh = workbook.getSheet("UNIX_LOG_VALIDATIONS");
			int IndexOfRowNumber = sh.getLastRowNum();
			IndexOfRowNumber++;
			sh.createRow(IndexOfRowNumber).createCell(0).setCellValue(TestCase);
			sh.getRow(IndexOfRowNumber).createCell(1).setCellValue(SessionName);
			sh.getRow(IndexOfRowNumber).createCell(2).setCellValue(SessionID);
			sh.getRow(IndexOfRowNumber).createCell(3).setCellValue(Errors);
			sh.getRow(IndexOfRowNumber).createCell(4).setCellValue(new Date().toString());

			FileOutputStream savefile = new FileOutputStream(destination);
			workbook.write(savefile);
			savefile.close(); 


		}
		catch(Exception e)
		{
			e.printStackTrace();

		}
	}	

	//*****************************************************************************************
	//*	Name		    : createTestFolders
	//*	Description	    : Function to Create Execution Folders
	//*	Author		    : Kapish Kumar
	//* Input Params	: strTestName, ClassName
	//*	Return Values	: boolean
	//***********************************************************************	
	public boolean createTestFolders(String strTestName, String Classname)
	{	
		boolean validateSnapShotPath = false;
		if(!Environment.get("RUN_ACCESSIBILITY").equalsIgnoreCase("y")){
			System.out.println("Execution is not related to accessibility, so this methods does not executes");
		}else{
			try {
				testFolder = Environment.get("HTMLREPORTSPATH") + "/" + strTestName;
				//catoSummary = testFolder + "/" + strTestName+"_CATO_SummaryReport.html";
				//Put in Environments
				//Environment.put("CURRENTEXECUTIONFOLDER", curExecutionFolder);
				//Environment.put("HTMLREPORTSPATH", htmlReportsPath);
				Environment.put("TESTFOLDER", testFolder);
				//Environment.put("SNAPSHOTSFOLDER", snapShotsPath);		
				//Environment.put("CATOSUMMARYREPORT", catoSummary);

				new File(testFolder).mkdirs();

				validateSnapShotPath = true;
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		return validateSnapShotPath;
	}

	//*****************************************************************************************
	//*	Name		    : PutAccessibilityDataToDictionary
	//*	Description	    : Put Accessibility Data To Dictionary
	//*	Author		    : Kapish Kumar
	//* Input Params	: 
	//*	Return Values	: 
	//*	Date Created	: October 10, 2015
	//***********************************************************************
	public void PutAccessibilityDataToDictionary(String strPageName) {
		try{
			if(Environment.get("RUN_ACCESSIBILITY").equalsIgnoreCase("Y")){
				// Clear cato list
				catoList.clear();
				// Clear Page acessibility flag
				Dictionary.put("PAGE_ACCESSIBILITY","");

				final String dataSheet = dataSheetsPath + "/" + Environment.get("CLASS_NAME") + ".xlsx";
				final String AccessibilitySheet = "ACCESSIBILITY";

				FileInputStream file = new FileInputStream(new File(dataSheet));
				XSSFWorkbook workbook = new XSSFWorkbook(file);
				XSSFSheet sheet = workbook.getSheet(AccessibilitySheet);

				Row firstrow = sheet.getRow(0);
				//loop each row
				for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++){
					Row row = sheet.getRow(i);

					Cell cellPage = row.getCell(0);
					String strPageNameVal = getCellValueAsString(cellPage);

					if(strPageNameVal.equalsIgnoreCase(strPageName)){
						System.out.println("Got Page: "+strPageName);
						/**
						 * Find the row to be executed, execute the script
						 * Loop on the @cellAccessibility for running accessibility
						 */
						Cell cellAccessibility = row.getCell(getRequiredColumnIndex(dataSheet, "ACCESSIBILITY", "PAGE_ACCESSIBILITY"));
						String strcellAccessibility = getCellValueAsString(cellAccessibility);
						if (strcellAccessibility.equalsIgnoreCase("Y")) {
							try{
								createTestFolders(strPageName,Environment.get("CLASS_NAME"));	
							}catch(Exception e1){
								System.err.println(e1);
							}
							int intLastCellNum = firstrow.getPhysicalNumberOfCells();

							for(int j=0; j < intLastCellNum ; j++){
								Cell cell = row.getCell(j);
								String strCATOFlag = getCellValueAsString(cell);

								Cell cellInFirstRowForCATO = firstrow.getCell(j);
								String strCellInFirstRowForCATO = getCellValueAsString(cellInFirstRowForCATO);

								if(strCellInFirstRowForCATO.contains("CATO_ID")){
									if(strCATOFlag.equalsIgnoreCase("y")){
										System.out.println("Accsssibility to be run for: "+ row.getCell(0));
										System.out.println("CATO ID to be run for "+ row.getCell(0) + " is: " + firstrow.getCell(j, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).toString());
										Dictionary.put("CATO_ID", firstrow.getCell(j, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK).toString());
										Dictionary.put(strCellInFirstRowForCATO, strCATOFlag);
										catoList.add(strCellInFirstRowForCATO);

										// Fetch Cell Comments
										try {
											org.apache.poi.ss.usermodel.Comment comment = sheet.getRow(0).getCell(j).getCellComment();
											if (comment != null) {
												String strComment = comment.getString().toString();
												Dictionary.put(strCellInFirstRowForCATO+"_DESCRIPTION", strComment);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}


									}
								}
							}
							// to fetch data from sheet 2 for accessibility beside main
							Dictionary.put("PAGE_ACCESSIBILITY",strcellAccessibility);
							Dictionary.put("CURRENT_RUNNING_PAGE", strPageName);
							break;
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		try{
			if(Environment.get("RUN_ACCESSIBILITY").equalsIgnoreCase("Y")){
				Reporting Reporter = new Reporting(null, null, Dictionary, Environment);
				Reporter.fnCreateHtmlReport(Dictionary.get("CURRENT_RUNNING_PAGE"));
			}
		}catch(Exception e1){
			System.err.println(e1);
		}

	}

	/**
	 * @Description Get Required Heading(Column) Name position
	 * @param strXLSX
	 * @param strSheetName
	 * @param strColumnName
	 * @return index of Heading
	 */
	//*****************************************************************************************
	//*	Name		    : getRequiredColumnIndex
	//*	Description	    : Get Required Heading(Column) Name position
	//*	Author		    : Kapish Kumar
	//* Input Params	: 
	//*	Return Values	: 
	//*	Date Created	: October 10, 2015
	//***********************************************************************
	public int getRequiredColumnIndex(String strXLSX, String strSheetName, String strColumnName) {
		int ACTIONCellNumber = 0;
		try {
			//System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
			//Create the FileInputStream object			   		   
			FileInputStream file = new FileInputStream(new File(strXLSX));
			//Get the workbook instance for XLS file 
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first sheet from the workbook and first row of sheet
			XSSFSheet sh = workbook.getSheet(strSheetName);
			int totalCellCount = sh.getRow(0).getLastCellNum();

			int x = 0;
			for(Row r : sh) {
				for(x=0 ; x < totalCellCount+1; x++){
					//Cell c = r.getCell(x);
					XSSFCell c = (XSSFCell) r.getCell(x);
					String cellValue = getCellValueAsString(c);
					if (cellValue.contains(strColumnName)) {
						ACTIONCellNumber++;
						break;
					}
				}
				break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ACTIONCellNumber;
	}

	/**
	 * @Description This method for the type of data in the cell, extracts the data and
	 * returns it as a string.
	 * @return Cell Value
	 */
	//*****************************************************************************************
	//*	Name		    : getCellValueAsString
	//*	Description	    : This method for the type of data in the cell, extracts the data and returns it as a string.
	//*	Author		    : Kapish Kumar
	//* Input Params	: cell
	//*	Return Values	: strCellValue - cell value
	//*	Date Created	: October 10, 2015
	//***********************************************************************
	public String getCellValueAsString(Cell cell) {
		String strCellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				strCellValue = cell.toString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					strCellValue = dateFormat.format(cell.getDateCellValue());
				} else {
					Double value = cell.getNumericCellValue();
					Long longValue = value.longValue();
					strCellValue = new String(longValue.toString());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				strCellValue = new String(new Boolean(
						cell.getBooleanCellValue()).toString());
				break;
			case Cell.CELL_TYPE_BLANK:
				strCellValue = "";
				break;
			}
		}else{
			strCellValue = "";
		}
		return strCellValue;
	}

	/**
	 * @Description UpdateStatusToAccessibilitySheet is intentionally left blank,
	 * will be updating on demand
	 */
	//*****************************************************************************************
	//*	Name		    : UpdateStatusToAccessibilitySheet
	//*	Description	    : Update Status To Accessibility Sheet
	//*	Author		    : Kapish Kumar
	//* Input Params	: 
	//*	Return Values	: 
	//*	Date Created	: October 10, 2015
	//***********************************************************************
	public void UpdateStatusToAccessibilitySheet() {
		// intentionally left blank
	}


	//*****************************************************************************************
	//*	Name		    : UpdateOrderIDintoDataSheet
	//*	Description	    : Update order id  To data Sheet
	//*	Author		    : Amit Kumar
	//* Input Params	: 
	//*	Return Values	: 
	//*	Date Created	: June 28, 2016
	//***********************************************************************
	public void UpdateOrderIDintoDataSheet(int row) {
		final String dataSheet = dataSheetsPath + "/" + Environment.get("CLASS_NAME") + ".xlsx";
		final String mainSheet = "MAIN";

		try{  
			//Create the FileInputStream object             
			FileInputStream file = new FileInputStream(new File(dataSheet));             
			//Get the workbook instance for XLS file 
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get MAIN sheet from the workbook
			Sheet sheet = workbook.getSheet(mainSheet);

			//update skip row according to status
			sheet.getRow(row-1).getCell(fGetColumnIndex(dataSheet, mainSheet, "SKIP_" + driverType)).setCellValue("X_"+driverType);
			sheet.getRow(row).getCell(fGetColumnIndex(dataSheet, mainSheet, "SKIP_" + driverType)).setCellValue(Dictionary.get("RESULT_"+driverType));


			System.out.println("UpdateSkip() - " + driverType + " " + row);
			System.out.println();

			file.close();

			FileOutputStream outFile =new FileOutputStream(new File(dataSheet));
			workbook.write(outFile);
			outFile.close();    

		}catch (Exception e) {
			System.err.println(e);
			Thread.currentThread().interrupt();
		}
	}



	//*****************************************************************************************
	//*	Name		    : fGetReportingWebDriver
	//*	Description	    : Returns the required webdriver for Report
	//*	Author		    : Hemant Talavdekar
	//*	Input Params	: Driver
	//*	Return Values	: WebDriver 
	//* Date			: 10/11/2016
	//*****************************************************************************************
	public WebDriver fGetReportingWebDriver()  
	{
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/exe/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=/root/Downloads/aaa"); 
		options.addArguments("start-maximized");
		DesiredCapabilities d = DesiredCapabilities.chrome();
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
		logPrefs.enable(LogType.BROWSER, Level.FINE);
		if(driverType.contains("ANDROID_EMULATION")){
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", "Google Nexus 5");
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			d.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		}else if(driverType.contains("IOS_EMULATION")){
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", "Apple iPhone 6 Plus");
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			d.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		}
		d.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		return new ChromeDriver(d);

	} 
}





///




////