package vfro.framework;

import java.awt.AWTException;
//import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
//import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//import java.util.Dictionary;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import org.xml.sax.SAXException;

import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
//import com.gargoylesoftware.htmlunit.javascript.host.dom.Node;
import com.google.common.base.Function;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;


import vfro.framework.DatabaseOperations;
import vfro.regression.MainDriver;




/* *** List of Common Function ***

 * fCommonLaunchEnvironemnt
 * fCommonIsSelected
 * fCommonIsEnabled
 * fCommonValidateDynamicPageDisplayed
 * fCommonClick - using regular click()
 * fCommonClick (OverLoaded) - using regular click()
 * fCommonJavascriptClick - using Java Script click
 * fCommonJavascriptClick (Overloaded) - using Java Script click
 * fCommonSelectionOptionFromList
 * fCommonValidateSelectedOptionFromList
 * fCommonSetValueEditBox
 * fCommonSetValueEditBox (Overloaded)
 * fCommonValidateEditBoxValue
 * fCommonVerifyApplicationURL
 * fCommonGetWebElement
 * fCommonGetWebElementsList
 * fCommonDeleteFolder
 * fCommonStringCompare
 * fCommonGetParentElement
 * fCommonGetParentElement (Overloaded)
 * fCommonGetSiblingElements
 * fCommonGetChildWebElementsList
 * fCommonSync
 * fCommonGetKeyByValue
 * fCommonVerifyObjectNotExist
 * fCommonBrowserBackButton
 * fCommonGetObject
 * fCommonGetObject(Overloaded)
 * fCommonGetMultipleObjects
 * fCommonGetMultipleChildObjects
 * fCommonCheckObjectExistance
 * fCommonExplicitwaitForWebElement
 * fCommonSwitchToWindow
 * fCommonGetXMLNodes
 * fCommonSetCookieDomain
 * fCommonChangeProxyFF
 * fCommonSwitchFrameDefaultContent
 * fCommonIsDisplayed
 * OpenNewTABinBrowser
 * validateEmail
 * COAMandBARKFirstNameLastNameGenerator
 * fCommonCloseBrowser
 * fCommonLaunchEnvironemntAfterCCC
 * fCommonValidateURLContent
 */
public class CommonFunctions {

	public WebDriver driver;
	private String driverType;
	private Reporting Reporter;
	private HashMap <String, String> Environment;
	
	
	public static LinkedHashMap<String, String> hashTestFlowStatus = new LinkedHashMap<String, String>();

	private static boolean firstTime = true;

	//* Default explicit wait time for an element: 5  seconds 
	public int WAIT_FOR_ELEMENT = 5;

	//* Default explicit wait time for multiple elements: 5  seconds 
	public int WAIT_FOR_MULTIPLE_ELEMENTS = 5;

	//* Default explicit wait time for multiple child elements: 5  seconds 
	public int WAIT_FOR_MULTIPLE_CHILD_ELEMENTS = 5;

	public int WAIT_FOR_POPUP_ELEMENTS = 3;

	// Default explicit wait time for a page to be displayed:  60 seconds.
	public int WAIT_FOR_PAGE = 60;

	// Default implicit wait time :  30 seconds.
	public int IMPLICIT_WAIT=30;

	// Polling time to check if the UI element state changed
	public long WAIT_FOR_ELEMENT_POLLING_TIME = 100L;

	// Polling time to check if the page state changed
	public long WAIT_FOR_PAGE_POLLING_TIME = 100L;
	//Added by amit
	public int CART_COUNT; 

	//static String prefix = "Pass";

	static String xmlPath;

	public CommonFunctions(WebDriver webDriver,String DT, HashMap <String, String> Env, Reporting Report){
		driver = webDriver;
		driverType = DT;
		Environment = Env;
		Reporter = Report;
		initializeTimeouts();
	}
	public String flowNameStatus(String sharedReportPath, int totalTestCount, int totalPassedCount, int totalFailedCount, String flowName,  String stat) throws IOException, SAXException, TikaException
    {
		
           hashTestFlowStatus.put(flowName, stat);

           String statusTable = "<h>Hi All,</h>" + "<br><br>" + "<h>Please check todays execution report.</h>" + "<br><br>" + "<h>Please click </h>"+ "<a href="+sharedReportPath+">Here</a>"+"<h> to view the full execution report.</h>" + "<br><br>"
                        +"<h3 align='center'><b><u>Automation Execution Summary Report</b></u></h3>"+ "</table>" + "<table border-collapse: separate  width='60%' border='1' bordercolor=\"#000000\" align='center'>"
                        + "<link rel=\"stylesheet\" type=\"text/css\" href=\"/Project/stylesheet.css\">"
                        + "<tr align='center' class='w3-red'>" + "<th bgcolor=\"#bfbfbf\"><b>Total SCEANRIOS <b></th>"
                        + "<td bgcolor=\"#00ff00\"><b>Total PASSED<b></td>" + "<td  bgcolor=\"#ff0000\"><b>Total FAILED<b></td>"
                        + "</tr>"+"<tr align='center'>" + "<td>" + totalTestCount + "</td>" + "<td>"
                        + totalPassedCount + "</td>" + "<td>" + totalFailedCount + "</td>" + "</tr>" + "</table>"+ "<br><br><br>"+"<table border-collapse: separate width='60%' border='1' bordercolor=\"#000000\" align='center'>"
                        + "<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\">";
                        //+ "<tr align='center' class='w3-red'>" + "<th bgcolor=\"#bfbfbf\"><b>SCENARIO NAME <b></th>" + "<th bgcolor=\"#bfbfbf\" ><b>STATUS<b></th>" + "</tr>";

          /* for (Map.Entry entry : hashTestFlowStatus.entrySet()) {
                  String value = (String) entry.getValue();
                  if (value.equalsIgnoreCase("PASS")) {
                        statusTable = statusTable + "<tr align='center'>" + "<td>" + entry.getKey() + "</td>"
                                      + "<td style=\"color:green\">" + entry.getValue() + "</td>" + "</tr>";

                  } else if (value.equalsIgnoreCase("FAIL")) {
                        statusTable = statusTable + "<tr align='center'>" + "<td>" + entry.getKey() + "</td>"
                                      + "<td style=\"color:red\">" + entry.getValue() + "</td>" + "</tr>";
                  }

           }
*/
           Reporter.fnCloseHtmlReport();
           String HtmlSummaryReport="";
  		 StringBuilder contentBuilder = new StringBuilder();
           try {
               BufferedReader in = new BufferedReader(new FileReader(sharedReportPath));
               String str;
               while ((str = in.readLine()) !=null) {
                   contentBuilder.append(str);
               }
               in.close();
           } catch (IOException e) {
           }
         
           HtmlSummaryReport = contentBuilder.toString();
           HtmlSummaryReport=HtmlSummaryReport.replace("href=", "pid=");
           statusTable=statusTable.concat(HtmlSummaryReport);
           return statusTable;
    }

	public String createEmailTable() {
        String count_Table = "</table>" + "<br><br><br>" + "<p></p>" + "<br><br><br>" + "<font align='Left' color=\"blue\">*Note : This is auto generated status report.</font>";
        return(count_Table);
 }

	private void initializeTimeouts() {

		try {
			/*WAIT_FOR_ELEMENT = Integer.parseInt(Environment.get("WAIT_FOR_ELEMENT"));
			WAIT_FOR_MULTIPLE_ELEMENTS = Integer.parseInt(Environment.get("WAIT_FOR_MULTIPLE_ELEMENTS"));
			WAIT_FOR_MULTIPLE_CHILD_ELEMENTS = Integer.parseInt(Environment.get("WAIT_FOR_MULTIPLE_CHILD_ELEMENTS"));
			WAIT_FOR_POPUP_ELEMENTS = Integer.parseInt(Environment.get("WAIT_FOR_POPUP_ELEMENTS"));
			WAIT_FOR_PAGE = Integer.parseInt(Environment.get("WAIT_FOR_PAGE"));
			IMPLICIT_WAIT = Integer.parseInt(Environment.get("IMPLICIT_WAIT"));*/

			// print only the first time, it might load different values later on but it's too verbose to print every time
			// also, multiple threads might get inside this if but it's not that vital to synchronize
			if(firstTime) {
				firstTime = false;
				System.out.println(fGetThreadClassNameAndMethod() + " -- WAIT_FOR_ELEMENT picked from Environments.xlsx: " + WAIT_FOR_ELEMENT);
				System.out.println(fGetThreadClassNameAndMethod() + " -- WAIT_FOR_MULTIPLE_ELEMENTS picked from Environments.xlsx: " + WAIT_FOR_MULTIPLE_ELEMENTS);
				System.out.println(fGetThreadClassNameAndMethod() + " -- WAIT_FOR_MULTIPLE_CHILD_ELEMENTS picked from Environments.xlsx: " + WAIT_FOR_MULTIPLE_CHILD_ELEMENTS);
				System.out.println(fGetThreadClassNameAndMethod() + " -- WAIT_FOR_POPUP_ELEMENTS picked from Environments.xlsx: " + WAIT_FOR_POPUP_ELEMENTS);
				System.out.println(fGetThreadClassNameAndMethod() + " -- WAIT_FOR_PAGE picked from Environments.xlsx: " + WAIT_FOR_PAGE);
				System.out.println(fGetThreadClassNameAndMethod() + " -- IMPLICIT_WAIT picked from Environments.xlsx: " + IMPLICIT_WAIT);
			}

		} catch (Exception e) {
			System.out.println(fGetThreadClassNameAndMethod() + " -- Exception while getting the values for explicit waits :  "+e);
			e.printStackTrace();
		}
	}

	//*****************************************************************************************
	//* Name            : fCommonLaunchEnvironemnt
	//* Description     : Launch env for any URL
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :
	//* Author          : Dina dodin
	//* Input Params    : strUrl - url need to open 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonLaunchEnvironemnt(String strUrl) {

		long startTime=System.currentTimeMillis();

		System.out.println("fCommonLaunchEnvironment");
		try {
			driver.manage().deleteAllCookies();
			if(driverType.contains("IOS")){
				try{
					((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");
				}catch(Exception e){
					System.out.println(e);
				}                           
			}
			driver.navigate().refresh();

			// To set up Bothell/Allen coockies in gtm link 
			fGuiSetUpCookiesRegion();

			//open env according to given URL
			driver.get(strUrl);
			
			Reporter.fnWriteToHtmlOutput("Launch URL: "+strUrl+" in "+driverType.replaceAll("[0-9]", "").trim()+" browser", strUrl+" should be launched", strUrl+ " is launched successfully", "Done");
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonLaunchEnvironemntname - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			
			//added by Naveena for log validation
		/*	if(!Environment.get("UNIX_LOG_VALIDATION").toLowerCase().equals("no") && Environment.get("UNIX_LOG_VALIDATION") != null)
			{
				if(Environment.get("SESSION_NAME")!=null)
				{
					String sessionid = fCommonGetCookieValue(Environment.get("SESSION_NAME").toUpperCase()+"SESSIONID");
					if(sessionid != null )
						Environment.put("SESSION_ID",fCommonGetCookieValue(Environment.get("SESSION_NAME").toUpperCase()+"SESSIONID"));
					else
						Environment.put("SESSION_ID",null);
				}
				else
					Environment.put("SESSION_NAME", null);
			}*/
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonLaunchEnvironemntname - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput("Launch URL: "+strUrl+" in "+driverType.replaceAll("[0-9]", "").trim()+" browser", "Exception occurred","Exception: " + e, "Fail");
			Environment.put("ROOT_CAUSE", "Environment Error");
			Environment.put("FAILURE_ERROR","Error: "+e.getLocalizedMessage());
			MainDriver.incrementEnvironmentErrorCount();
			return false;
		}       
	}   

	//*****************************************************************************************
	//* Name            : fCommonGetSessionId
	//* Description     : Get session Id
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :
	//* Author          : Surajs Bankar
	//* Input Params    : strUrl - url need to open 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public String fCommonGetSessionId() {

		System.out.println("fCommonGetSessionId");

		String[] cookie = ((JavascriptExecutor) driver).executeScript("return document.cookie;").toString().split("SHOPSESSIONID=");
		String[] sessionid = cookie[1].split(";");
		System.out.println(sessionid[0]);
		return sessionid[0];


	}   



	//*****************************************************************************************
	//* Name            : fCommonIsSelected
	//* Description     : Check if the object is selected or not
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :1504
	//* Author          : Moin khan
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : WebElement - The webelement for which we need to check
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonIsSelected(String webElmtProp, String objName,boolean takeScreenPrint){

		long startTime=System.currentTimeMillis();
		//      String sTakeScreenPrint="Done";
		//      if(takeScreenPrint)
		//          sTakeScreenPrint="Pass";

		//Get WebElement
		WebElement webElement = fCommonGetObject(webElmtProp,objName);  
		if (webElement==null){
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsSelected", "Check Object Existence","Object is null", "Done");
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsSelected -false- has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return false;
		}

		//Check if the WebElement is selected           
		boolean bIsSelected = false;                
		try {                               
			bIsSelected = webElement.isSelected();
		}catch(Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsSelected - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsSelected", "Exception occurred for when getting isSelected: " + webElmtProp,"Exception: " + e, "Fail");          
			return false;           
		}

		//return the results
		if (!(bIsSelected)){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsSelected - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsSelected", objName+ " should be Selected", objName+ " is not Selected", "Done");
			return false;
		}   
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsSelected - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsSelected", objName+ " should be Selected", objName+ " is Selected ", sTakeScreenPrint);
		return true;            

	}   

	//*****************************************************************************************
	//* Name            : fCommonIsEnabled
	//* Description     : Check if the object is enabled or not
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :1504
	//* Author          : Anil Agarwal
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : WebElement - The webelement for which we need to check
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonIsEnabled(String webElmtProp, String strObjName){

		long startTime=System.currentTimeMillis();

		//Get WebElement
		WebElement webElement = fCommonGetObject(webElmtProp,strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""));
		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled", "Check Object Existance","Object is null", "Done");
			return false;
		}
		
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		
		//Added By Zachi for BIS - Date 23-07-2020
		if(!webElement.isDisplayed()){
			try {
				js.executeScript("arguments[0].scrollIntoView(true);",webElement);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		//Check if the WebElement is enabled
		boolean bIsEnabled = false;                     
		try {                               
			bIsEnabled = webElement.isEnabled();
		}catch(Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled", "Exception occurred for when getting isEnabled: " + webElmtProp,"Exception: " + e, "Fail");
			Reporter.fnWriteToHtmlOutput("Verify element: "+strObjName+" is enabled"+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "Verify element: "+strObjName+" is enabled"+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "Exception occurred for when getting enabled status for element: "+ strObjName+". Exception: " + e.getLocalizedMessage(), "Fail");
			return false;           
		}

		//Validate
		if (!(bIsEnabled)){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " should be enabled", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is not enabled", "Done");
			Reporter.fnWriteToHtmlOutput("Verify element: "+strObjName+" is enabled"+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "Verify element: "+strObjName+" is enabled"+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is not enabled", "Done");
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		Reporter.fnWriteToHtmlOutput("Verify element: "+strObjName+" is enabled"+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "Element: "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " should be enabled", "Element: "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is enabled", "Done");
		return true;        

	}   
	
	//*****************************************************************************************
	//* Name            : fCommonIsEnabled
	//* Description     : Check if the object is enabled or not by give timeout 
	//* CREATED RELEASE : 
	//* LAST MODIFIED IN :
	//* Author          : Zachi Gahari
	//* Input Params    : Object description, time out (sec)
	//* Modified date   : 25-Mar-2018
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonIsEnabled(String webElmtProp, String elmntName, int timeOut){
		int tmpWait = WAIT_FOR_ELEMENT;
		WAIT_FOR_ELEMENT = timeOut;
		WebElement webElement = null;
		long startTime = System.currentTimeMillis();
		int i; 

		webElement = fCommonGetObject(webElmtProp, elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""));
//		WAIT_FOR_ELEMENT = tmpWait;  // Restore default value
		long waitForObjectTime = Math.round(((System.currentTimeMillis()-startTime))/1000);
		System.out.println("Wait for " + waitForObjectTime + " seconds");
		WAIT_FOR_ELEMENT = tmpWait;  // Restore default value

		if(webElement!=null){
			for(i=(int) waitForObjectTime; i<timeOut; i++){
				if(webElement.isEnabled()==true){
					try {
						JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
						jsExecutor.executeScript("arguments[0].scrollIntoView(false);", webElement);
						if(webElement.getLocation().getY()>500){
							jsExecutor.executeScript("window.scrollBy(0, 100)");
						}
					} catch (Exception e) {}
					System.out.println(">>>>>>>  " + elmntName + " enabled within " + ((System.currentTimeMillis()-startTime)/1000) + " seconds");
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled", "Check if object enabled", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " enabled within " + ((System.currentTimeMillis()-startTime)/1000) + " seconds", "Pass");
					return true;
				}

				fCommonSync(1000);
			}
			System.out.println(">>>>>>>  " + elmntName + " hasn't enabled after " + ((System.currentTimeMillis()-startTime)/1000)+" seconds");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled", "Check if object enabled", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " hasn't enabled after " + ((System.currentTimeMillis()-startTime)/1000) + " seconds", "Pass"); // Zachi - Report 'Pass' for getting the screenshot, can't report 'Fail' as sometimes it is expected
		}else{
			System.out.println(">>>>>>>  " + elmntName + " doesn't exist (returned null) after " + ((System.currentTimeMillis()-startTime)/1000) + " seconds");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled", "Check if object enabled", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " doesn't exist (returned null) after " + ((System.currentTimeMillis()-startTime)/1000) + " seconds", "Pass"); // Zachi - Report 'Pass' for getting the screenshot, can't report 'Fail' as sometimes it is expected
		}

		return false;
	}

	//*****************************************************************************************
	//* Name            : fCommonIsEnabled
	//* Description     : Check if the object is enabled or not
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :1504
	//* Author          : Anil Agarwal
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : WebElement - The webelement for which we need to check
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonIsEnabled(WebElement webElement, String strObjName){

		long startTime=System.currentTimeMillis();

		//Validate WebElement parameters
		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled", "Check Object Existance","Object is null", "Done");
			return false;
		}

		//Check if the WebElement is enabled
		boolean bIsEnabled = false;                     
		try {                               
			bIsEnabled = webElement.isEnabled();
		}catch(Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled", "Exception occurred for when getting isEnabled: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""),"Exception: " + e, "Fail");         
			return false;           
		}

		//Validate
		if (!(bIsEnabled)){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strObjName, strObjName+ " should be enabled", strObjName+ " is not enabled", "Done");
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsEnabled - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strObjName, strObjName+ " should be enabled", strObjName+ " is enable", "Done");
		return true;        

	}   

	//*****************************************************************************************
	//* Name            : fCommonClick
	//* Description     : Click on the webelement
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN : 1504
	//* Author          : Anil Agarwal
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : WebElement Properties - The webelement for which we need to click
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonClick(String webElmtProp, String strObjName) throws ElementNotVisibleException{   

		long startTime=System.currentTimeMillis();

		//Get WebElement
//		WebElement webElement = fcommonRetrieveVisibleElementOnPage(webElmtProp, strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""));
		WebElement webElement = fCommonGetObject(webElmtProp, strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""));
		if(webElement == null){
			System.out.println(fGetThreadClassNameAndMethod() + " -- " + "fCommonClick - return false - has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + " -- " + "fCommonClick: " + strObjName, "Object should be found", "Object was not found. Null value returned", "Fail");
			Reporter.fnWriteToHtmlOutput("Click on element: "+strObjName+" in page :"+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "Element "+strObjName+" is clicked", "Element was not found. Null value returned", "Fail");
			return false;
		}
		
		//updated By Holey for BIS Date-23-07-2020
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		if(!webElement.isDisplayed()){
			try {
				js.executeScript("arguments[0].scrollIntoView();",webElement);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		if(fCommonClick(webElement, strObjName)==false){
			if(fCommonJavascriptClick(webElmtProp, strObjName)==false)
				return false;
		}

		return true;
	} 




	//*****************************************************************************************
	//* Name            : fCommonClick (Overloaded)
	//* Description     : Click on the webelement
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN : 1504
	//* Author          : Anup Agarwal
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : WebElement - The webelement for which we need to click
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonClick(WebElement webElement, String strObjName){              

		long startTime=System.currentTimeMillis();
		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
		try
		{
			///////////---Authentication Popup-------////////////////////
			//JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
			jsExecutor.executeScript("window.close");
			///////////---Authentication Popup-------////////////////////

			//Check WebElement
			if(webElement == null){
				System.out.println(fGetThreadClassNameAndMethod() + " -- " + "fCommonClick - return false - has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
//				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + " -- " + "fCommonClick: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "Object should be found", "Object was not found. Null value returned", "Fail");
				Reporter.fnWriteToHtmlOutput("Click on element: "+strObjName+" in page: "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "Element is clicked", "Element was not found. Null value returned", "Fail");
				return false;
			}

		}
		catch(Exception e)
		{
			fCommonHandleAlert("dismiss");
			//System.out.println("Dina - exception fCommonClick: " + e.toString());
		}

		//Check if the WebElement is clicked
		boolean bIsClicked = false;	
		
		try 
		{
			// just waiting for now until timeout, if still not clickable it will fail downstream
			waitForWebElementIsEnabled(webElement,strObjName);

			try {
//				jsExecutor.executeScript("arguments[0].scrollIntoView(false);", webElement);
				int y = webElement.getLocation().getY()-400;
				String script = "window.scrollBy(0, " + y + ")";
				jsExecutor.executeScript("window.scrollTo(0, 0)");
				jsExecutor.executeScript(script);
//				if(webElement.getLocation().getY()>500){
//					fCommonSync(2000);
//					jsExecutor.executeScript("window.scrollBy(0, 200)");
//				}
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			
			webElement.getLocation();
			webElement.click();
			bIsClicked=true;
		}
		catch(Exception e)
		{	e.printStackTrace();
//			fCommonHandleAlert("dismiss");
			if(e.getLocalizedMessage().contains("element not visible")==true){       
				System.out.println("Inside scroll block");
					try{
					jsExecutor.executeScript("arguments[0].scrollIntoView();",webElement);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			//System.out.println("Dina - exception fCommonClick: " + e.toString());
			//check if are pop-ups that is making the click failing
//			int i=handlePopUps(1);
			int i=0;
			//i will become <=0 only if at least one pop-up was found and handled; if not then the click failed from other reasons
			if(i<=0)
			{
				try {
//					fCommonHandleAlert("dismiss");
					webElement.getLocation();
					webElement.click();
					bIsClicked=true;
				} catch (Exception e1) {
					if(e1.getLocalizedMessage().contains("element not visible")==true){       //element not visible
						System.out.println("Inside second scroll block");
						try{
						jsExecutor.executeScript("arguments[0].scrollIntoView();",webElement);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					}
					if(!e1.getLocalizedMessage().contains("stale element reference: element is not attached to the page document")){
//						fCommonHandleAlert("dismiss");
						e.printStackTrace();
						System.out.println(fGetThreadClassNameAndMethod() + " -- " + "fCommonClick - return false - has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
//						Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + " -- " + "fCommonClick", "Exception occurred  when clicking webelement: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "Exception: " + e1.getMessage(), "DONE");		// Set the status as DONE so that it won't consider as failure if second try will work
						Reporter.fnWriteToHtmlOutput("Click on element :"+strObjName, "Element is clicked", "Exception occurred  when clicking on element: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+", Exception: " + e1.getLocalizedMessage(), "Done");		// Set the status as DONE so that it won't consider as failure if second try will work
					}
					return false;
				}  
			}
			else // 7-May-2015-Dina - adding to provide reporting of exception in situation that click failing due to another exception and not because of popup
			{
				try { // Dina - give it one more try to click - for IE browser
//					fCommonHandleAlert("dismiss");
					webElement.getLocation();
					webElement.click();
					bIsClicked=true;
				} catch (Exception e2) {
					if(!e2.getLocalizedMessage().contains("stale element reference: element is not attached to the page document")){
						fCommonHandleAlert("dismiss");
						System.out.println(fGetThreadClassNameAndMethod() + " -- " + "Exception occured : " + e2.toString());
						System.out.println(fGetThreadClassNameAndMethod() + " -- " + "fCommonClick - return false - has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
//						Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + " -- " + "fCommonClick", "Exception occurred  when clicking webelement: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "Exception: " + e2.getMessage(), "DONE");		// Set the status as DONE so that it won't consider as failure if second try will work
						Reporter.fnWriteToHtmlOutput("Click on element :"+strObjName, "Element is clicked","Exception occurred  when clicking webelement: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+". Exception: " + e2.getLocalizedMessage(), "Done");		// Set the status as DONE so that it won't consider as failure if second try will work
					}
					return false;
				}  

			}
		}

		//Validate
		if (!(bIsClicked)){
			System.out.println(fGetThreadClassNameAndMethod() + " -- " + "fCommonClick - return false - has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
			if(Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "").equals("CommonFunctions")==false){
//				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + " -- " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is not clicked", "Fail");
				Reporter.fnWriteToHtmlOutput("Click on element: "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is not clicked", "Fail");
			}else{
//				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + " -- " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "") + " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "")+ " is not clicked", "Fail");
				Reporter.fnWriteToHtmlOutput("Click on element: "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "") + " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "")+ " is not clicked", "Fail");
			}
				
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonClick - return true - has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
		if(Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "").equals("CommonFunctions")==false){
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + " -- " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is clicked", "Pass");
			Reporter.fnWriteToHtmlOutput("Click on element: "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is clicked", "Pass");
		}else{
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + " -- " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "") + " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "")+ " is clicked", "Pass");
			Reporter.fnWriteToHtmlOutput("Click on element: "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "") + " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "")+ " is clicked", "Pass");
		}
			
		return true;    
	}



	//*****************************************************************************************
	//* Name            : fCommonJavascriptClick
	//* Description     : Click on Element using Java script command
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN : 1504
	//* Author          : Shraddha
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : None
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonJavascriptClick(String webElmtProp, String strObjName){

		long startTime=System.currentTimeMillis();

		///////////---Authentication Popup-------////////////////////
		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
		jsExecutor.executeScript("window.close");
		///////////---Authentication Popup-------////////////////////

		//Get WebElement
		WebElement webElement = fCommonGetObject(webElmtProp,strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""));
		if(webElement == null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonJavascriptClick - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonJavascriptClick", "Object not Found","Object: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " doesn't exist", "Fail");
			Reporter.fnWriteToHtmlOutput("Click on element: "+strObjName+" using java script", "Element is clicked","Element: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " is not found", "Fail");
			return false;  
		}

		//Click on the WebElement       
		boolean bIsClicked = false;                     
		try {                               
			webElement.getLocation();
			((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElement);
			bIsClicked=true;
		}catch(Exception e){
			if(e.getLocalizedMessage().contains("element not visible")==true){       //element not visible
				try{
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);",webElement);
			} catch (Exception e2) {
				e2.printStackTrace();
				try{
					webElement.getLocation();
					((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElement);
					bIsClicked=true;
				}catch(Exception e3){
					e3.printStackTrace();
				}
			}
			}
			
			if (e.toString().contains("UnhandledAlertException"))
			{
				fCommonHandleAlert("dismiss");
				//System.out.println("Dina - exception fCommonJavascriptClick: " + e.toString());
			}
			else
			{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonJavascriptClick - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonJavascriptClick", "Exception occurred  when clickig webelement: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""),"Exception: " + e, "Fail");
				Reporter.fnWriteToHtmlOutput("Click on element: "+strObjName+" using java script", "Exception occurred  when clickig webelement: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""),"Exception: " + e.getLocalizedMessage(), "Fail");
				return false;  
			}
		}

		//Validate
		if (!(bIsClicked)){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonJavascriptClick - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			if(Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "").equals("CommonFunctions")==false){
//				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is not clicked", "Fail");
				Reporter.fnWriteToHtmlOutput("Click on element: "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" using java script", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is not clicked", "Fail");
			}else{
//				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "")+ " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "")+ " is not clicked", "Fail");
				Reporter.fnWriteToHtmlOutput("Click on element: "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "")+ " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "")+ " is not clicked", "Fail");
			}
				
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonJavascriptClick - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		if(Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "").equals("CommonFunctions")==false){
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is clicked", "Pass");
			Reporter.fnWriteToHtmlOutput("Click on element: "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is clicked", "Pass");
		}else{
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "")+ " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "")+ " is clicked", "Pass");
			Reporter.fnWriteToHtmlOutput("Click on element: "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "")+ " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "")+ " is clicked", "Pass");
		}
			
		return true;    

	}

	//*****************************************************************************************
	//* Name            : fCommonJavascriptClick (Overloaded)
	//* Description     : Click on the webelement
	//* CREATED RELEASE : 1310
	//* LAST MODIFIED IN : 1504
	//* Author          : Dina Dodin
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : WebElement - The webelement for which we need to click
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonJavascriptClick(WebElement webElement, String strObjName){            

		long startTime=System.currentTimeMillis();

		///////////---Authentication Popup-------////////////////////
		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
		jsExecutor.executeScript("window.close");
		///////////---Authentication Popup-------////////////////////
		handlePopUps(1);
		//Check WebElement
		if(webElement == null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonJavascriptClick - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonJavascriptClick", "Object not Found","Object: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " doesn't exist", "Fail");
			return false;  
		}

		//Click on the WebElement       
		boolean bIsClicked = false;                     
		try {                               
			webElement.getLocation();
			((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElement);
			bIsClicked=true;
		}catch(Exception e){
			if (e.toString().contains("UnhandledAlertException"))
			{
				fCommonHandleAlert("dismiss");
				//System.out.println("Dina - exception fCommonJavascriptClick: " + e.toString());
			}
			else
			{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonJavascriptClick - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonJavascriptClick", "Exception occurred  when clickig webelement: " + strObjName,"Exception: " + e, "Fail");         
				return false;
			}
		}

		//Validate
		if (!(bIsClicked)){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonJavascriptClick - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is not clicked", "Fail");
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonJavascriptClick - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " should be clicked", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is clicked", "Pass");
		return true;    

	}


	//*****************************************************************************************
	//* Name            : fCommonSelectionOptionFromList
	//* Description     : select option from list
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN : 1506
	//* Author          : Abhishek Pandey
	//* Updated by      : Jaishree M
	//* Updated Date    : April 24, 2015
	//* Parameters      : sSelectionMethod (ByValue,ByVisibleText). If a value other than these, ByVisibleText is used
	//* Dictionary      : 
	//* Date Modified   : 30-Aug-12;06-Aug-2014
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonSelectionOptionFromList(String webElmtProp, String strObjName, String strText, String sSelectionMethod){

		long startTime=System.currentTimeMillis();
		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;

		//Get WebElement
		WebElement objList = fCommonGetObject(webElmtProp,strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""));
		if (objList == null){   
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput("Select value: "+strText+" from drop down: "+strObjName, "Value: "+strText+" in drop down: "+ strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" is selected", "Element was not found. "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " does not exist", "Fail");
			Environment.put("FAILURE_ERROR","Element was not found");
			Environment.put("ROOT_CAUSE", "Element not found in page");
			MainDriver.incrementElementNotFoundCount();			
			return false;
		}
		
		if(!objList.isDisplayed()){
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			try {
				js.executeScript("arguments[0].scrollIntoView(true);",webElmtProp);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
				
		//Select from list      
		boolean bIsSelected = false;    

		try {
			if(!sSelectionMethod.contains("ByList")){
				Select select = new Select(objList);
				List<WebElement> element = select.getOptions(); 
				int retryCount=0;
				while(element.size()==1 && retryCount<10){ 
					fCommonSync(1000);
					element = select.getOptions();
					retryCount++;
				}
		
				if(sSelectionMethod.equals("ByValue") || sSelectionMethod.equals("")){
					select.selectByValue(strText);
					bIsSelected=true;
				}else if(sSelectionMethod.equals("ByVisibleText")){
					select.selectByVisibleText(strText);
					bIsSelected=true;
				}else if(sSelectionMethod.equals("ByClick")){
					List <WebElement> listOptions = objList.findElements(By.tagName("option"));                         
					for(int i = 0; i< listOptions.size();i++){
						if(listOptions.get(i).getText().equalsIgnoreCase(strText)){
							listOptions.get(i).click();
							bIsSelected=true;
							break;
						}
					}
				}else if(sSelectionMethod.equals("sendKeys")){
					objList.sendKeys(strText);
					bIsSelected=true;
				}else if(sSelectionMethod.equals("ByIndex")){
					select.selectByIndex(Integer.valueOf(strText));
					bIsSelected=true;
				}
				else if(sSelectionMethod.equals("clickAny")){
					List <WebElement> listOptions = objList.findElements(By.tagName("option"));                         
					for(int i = 0; i< listOptions.size();i++){
						if(listOptions.get(i).getText().contains(strText)){
							listOptions.get(i).click();
							bIsSelected=true;
							break;
						}
					}
				}
				// just waiting for now until timeout, if still not visible it will fail downstream
				// waitForWebElementIsClickable(select.getFirstSelectedOption(),strObjName);
			}else{
				// Select By List
				// Added By AMIT KUMAR : To select the value from Drop down by List
//				Commenting below if block to merge framework with BIS application
//				if(fCommonClick(objList, strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""))==false){
//					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList", "Unable to locate object: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is null", "Fail");
//					return false;
//				}
				String lstOfDropDown = "xpath:=//li[contains(text(),'XXX')]";
				String strValueToBeSelected = strText;
				WebElement webListOfDropDown = fcommonRetrieveVisibleElementOnPage(lstOfDropDown.replace("XXX",strValueToBeSelected),strValueToBeSelected);
				if (webListOfDropDown == null){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList", "Unable to locate object: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is null", "Fail");
					Reporter.fnWriteToHtmlOutput("Select value: "+strText+" from drop down: "+strObjName, "Value: "+strText+" in drop down: "+ strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" is selected", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is not found", "Fail");
					return false;
				}
				if(fCommonClick(webListOfDropDown, strValueToBeSelected)==false){
					return false;
				}
				bIsSelected = true;
				// either value is selected or not 				
			}
		}/*catch (WebDriverException we){
	            try {// Kapish added to select if there is any exception by Select Class
	                Actions cursor = new Actions(driver);
	                cursor.moveToElement(objList).perform();
	                cursor.click();    
	                WebElement selElmt = objList;
	                selElmt.click();
	                cursor.sendKeys(strText);
	                cursor.moveToElement(selElmt).perform();
	                bIsSelected=true;
	            } catch (Exception e1) {
	                System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
	                Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList", "Selection from list", "WebDriverException exception occured = " +e1, "Fail");
	                return false;
	            }

	        }*/catch (Exception e) {

	        	System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//	        	Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList", "Selection from list", "Exception occured = " +e, "Fail");
	        	Reporter.fnWriteToHtmlOutput("Select value: "+strText+" from drop down: "+strObjName, "Value: "+strText+" in drop down: "+ strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" is selected", "Exception occured: " +e.getLocalizedMessage(), "Fail");
	        	Environment.put("FAILURE_ERROR","Element was not found");
				Environment.put("ROOT_CAUSE", "Element not found in page");
				MainDriver.incrementElementNotFoundCount();		
	        	return false;
	        }

		//Validate
		if (!(bIsSelected)){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " should be Selected", strText+ " is not selected", "Fail");
			Reporter.fnWriteToHtmlOutput("Select value: "+strText+" from drop down: "+strObjName, "Value: "+strText+" in drop down: "+ strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" is selected", "Value: "+strText+" in drop down: "+ strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" is not selected", "Fail");
			Environment.put("FAILURE_ERROR","Element was not found");
			Environment.put("ROOT_CAUSE", "Element not found in page");
			MainDriver.incrementElementNotFoundCount();		
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " should be selected", strText+ " is selected in page "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "Pass");
		Reporter.fnWriteToHtmlOutput("Select value: "+strText+" from drop down: "+strObjName, "Value: "+strText+" in drop down: "+ strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" is selected", "Value: "+strText+" in drop down: "+ strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" is selected", "Pass");
		return true;    

	} 



	//*****************************************************************************************
	//* Name            : fCommonSelectionOptionFromList (Overloaded method which get WebElement as parameter - added by Dina 28-Apr-2015)
	//* Description     : select option from list
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN : 1406
	//* Author          : Abhishek Pandey
	//* Parameters      : sSelectionMethod (ByValue,ByVisibleText). If a value other than these, ByVisibleText is used
	//* Dictionary      : 
	//* Date Modified   : 30-Aug-12;06-Aug-2014
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonSelectionOptionFromList(WebElement webElmtProp, String strObjName, String strText, String sSelectionMethod){

		long startTime=System.currentTimeMillis();

		//Check WebElement
		if (webElmtProp == null){ //30-March-2015 - Added by Dina
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList", "Unable to locate object: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is null", "Fail");
			return false;
		}

		//Select from list      
		boolean bIsSelected = false;    

		try {

			if(!sSelectionMethod.contains("ByList")){
				Select select;
				select = new Select(webElmtProp);
				if(sSelectionMethod.equals("ByValue") || sSelectionMethod.equals("")){
					select.selectByValue(strText);
					bIsSelected=true;
				}else if(sSelectionMethod.equals("ByVisibleText")){
					select.selectByVisibleText(strText);
					bIsSelected=true;
				}else if(sSelectionMethod.equals("ByClick")){
					List <WebElement> listOptions = webElmtProp.findElements(By.tagName("option"));                         
					for(int i = 0; i< listOptions.size();i++){
						if(listOptions.get(i).getText().equalsIgnoreCase(strText)){
							listOptions.get(i).click();
							bIsSelected=true;
							break;
						}
					}
				}else if(sSelectionMethod.equals("sendKeys")){
					webElmtProp.sendKeys(strText);
					bIsSelected=true;
				}
			}
			else{
				// Select By List
				// Added By VIVEK VISWANATHAN : To select the value from Drop down by List
				if(fCommonClick(webElmtProp, strObjName)==false){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList", "Unable to locate object: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is null", "Fail");
					return false;
				}
				String lstOfDropDown = "xpath:=//li[contains(text(),'XXX')]";
				String strValueToBeSelected = strText;
				WebElement webListOfDropDown = fcommonRetrieveVisibleElementOnPage(lstOfDropDown.replace("XXX",strValueToBeSelected),strValueToBeSelected);
				if (webListOfDropDown == null){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList", "Unable to locate object: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is null", "Fail");
					return false;
				}
				if(fCommonClick(webListOfDropDown, strValueToBeSelected)==false){
					return false;
				}
				bIsSelected = true;
				// either value is selected or not 				
			}
		}/*catch (WebDriverException we){
			try {// Kapish added to select if there is any exception by Select Class
				Actions cursor = new Actions(driver);
				cursor.moveToElement(webElmtProp).perform();
				cursor.click();    
				WebElement selElmt = webElmtProp;
				selElmt.click();
				cursor.sendKeys(strText);
				cursor.moveToElement(selElmt).perform();

			} catch (Exception e1) {
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList", "Selection from list", "WebDriverException exception occured = " +e1, "Fail");
				return false;
			}

		}*/catch (Exception e) {
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList", "Selection from list", "Exception occured = " +e, "Fail");
			return false;
		}

		//Validate
		if (!(bIsSelected)){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " should be Selected", strText+ " is not selected", "Fail");
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSelectionOptionFromList - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " should be selected", strText+ " is selected", "Pass");
		return true;    

	} 


	//*****************************************************************************************
	//* Name            : fCommonValidateSelectedOptionFromList
	//* Description     : Function to validate the selected option in the list
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :
	//* Author          : Anup Agarwal
	//* Input Params    : None
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonValidateSelectedOptionFromList(String webElmtProp, String strObjName, String expectedValue){

		long startTime=System.currentTimeMillis();

		//Get WebElement
		WebElement objList = fCommonGetObject(webElmtProp,strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""));
		if (objList == null){   
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList", "Unable to locate object: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+ " is null", "Fail");
			Reporter.fnWriteToHtmlOutput("Validate selected option in drop down", "Option "+expectedValue+" is selected in drop down "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "Element was not found.", "Fail");
			return false;
		}

		try{
			//Set Select Element
			Select select = new Select(objList);
			//Get the selected value from the drop down
			String actualValue = select.getFirstSelectedOption().getText();
			
			//Check if actual selected value is equal to expected value
			if(actualValue.equals(expectedValue)){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList","Selected value: " + actualValue, "Expected value: " + expectedValue , "Done");
				Reporter.fnWriteToHtmlOutput("Validate selected option in drop down", "Option "+expectedValue+" is selected in drop down "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "Option "+expectedValue+" is selected in drop down "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") , "Done");
				return true;                
			}else{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList","Selected value: " + actualValue, "Expected value: " + expectedValue , "Done");
				return false;
			}           

		} catch (Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList", "Exception occurred for object: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""),"Exception: " + e, "Fail");
			Reporter.fnWriteToHtmlOutput("Validate selected option in drop down", "Option "+expectedValue+" is selected in drop down "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""),"Exception: " + e.getLocalizedMessage(), "Fail");
			return false;
		}
	} 

	//*****************************************************************************************
	//* Name            : fCommonValidateSelectedOptionFromList
	//* Description     : Function to validate the selected option in the list
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :
	//* Author          : Anup Agarwal
	//* Input Params    : None
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonValidateSelectedOptionFromList(WebElement objList, String strObjName, String expectedValue){
		long startTime=System.currentTimeMillis();

		try{
			//Set Select Element
			Select select = new Select(objList);
			//Get the selected value from the drop down
			String actualValue = select.getFirstSelectedOption().getText();
			//Check if actual selected value is equal to expected value
			if(actualValue.equals(expectedValue)){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList","Selected value: " + actualValue, "Expected value: " + expectedValue , "Done");
				return true;                
			}else{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList","Selected value: " + actualValue, "Expected value: " + expectedValue , "Done");
				return false;
			}           

		} catch (Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateSelectedOptionFromList", "Exception occurred for object: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""),"Exception: " + e, "Fail");
			return false;
		}
	} 

	//*****************************************************************************************
	//* Name            : fCommonSetValueEditBox
	//* Description     : Set value in Edit box
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN : 1504
	//* Author          : Dina Dodin
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : strClear  - if no need to clear edit box, put: "N" otherwise can leave empty: ""
	//*                   strSkipVerify - if no need to do verification after set, put: "Y" otherwise leave empty: ""
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonSetValueEditBox(String webElmtProp, String strObjName, String strValue, String strClear,String strSkipVerify){

		long startTime=System.currentTimeMillis();

		//Get WebElement
//		WebElement objWebEdit = fcommonRetrieveVisibleElementOnPage(webElmtProp,strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""));
		WebElement objWebEdit =fCommonGetObject(webElmtProp,strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""));
		
		if(objWebEdit == null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox", "Object not Found","Object: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " doesn't exist", "Fail");
			Reporter.fnWriteToHtmlOutput("Set value: "+strValue+" in text field: "+strObjName, "Value: "+strValue+" is set in "+strObjName,"Element was not found. Element: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " doesn't exist", "Fail");
			return false;  
		}
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		
		//Added By Zachi for BIS - Date 23-07-2020
		if(!objWebEdit.isDisplayed()){
			try {
				js.executeScript("arguments[0].scrollIntoView(true);",objWebEdit);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		//Checks if input parameter is Null
		if (strValue == null){
			strValue = "";
		}

		// just waiting for now until timeout, if still not enabled or not visible it will fail downstream
		if (waitForWebElementIsEnabled(objWebEdit,strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")) == false || waitForWebElementIsDisplayed(objWebEdit,strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")) == false){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox",strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " should be Enabled and Displayed", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " is not Enabled or Displayed" , "Fail");
			Reporter.fnWriteToHtmlOutput("Verify text field: "+strObjName+" is enabled and displayed",strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " should be Enabled and Displayed", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " is not Enabled or Displayed" , "Fail");
			return false;
		}

		//Set value to the Edit box  
		try{
			if(strClear.equalsIgnoreCase("Y")){ 
				objWebEdit.clear();
			}
			if(Environment.get("HIDE_TEST_DATA")!=null 
					&& (Environment.get("HIDE_TEST_DATA").toLowerCase().equals("y") || Environment.get("HIDE_TEST_DATA").toLowerCase().equals("yes"))){
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);",objWebEdit, "color: white; background-color: white; border: 4px solid red;");
			}
			objWebEdit.sendKeys(strValue);          
		}catch (Exception e){
			e.printStackTrace();
			try {
				if(Environment.get("HIDE_TEST_DATA")!=null 
						&& (Environment.get("HIDE_TEST_DATA").toLowerCase().equals("y") || Environment.get("HIDE_TEST_DATA").toLowerCase().equals("yes"))){
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);",objWebEdit, "color: white; background-color: white; border: 4px solid red;");
				}
				js.executeScript("arguments[0].setAttribute('value', '"+strValue+"');", objWebEdit);
			} catch (Exception e1) {
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				if(Environment.get("HIDE_TEST_DATA")!=null 
						&& (Environment.get("HIDE_TEST_DATA").toLowerCase().equals("y") || Environment.get("HIDE_TEST_DATA").toLowerCase().equals("yes"))){
//					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox","Set: HIDDEN_VALUE", "HIDDEN_VALUE is not set" , "Fail");
					Reporter.fnWriteToHtmlOutput("Set value: "+strValue+" as hidden in text field: "+strObjName,"Set: HIDDEN_VALUE", "HIDDEN_VALUE is not set" , "Fail");
				}else{
//					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox","Set: " + strValue, strValue +" is not set" , "Fail");
					Reporter.fnWriteToHtmlOutput("Set value: "+strValue+" in text field: "+strObjName,"Set: " + strValue, strValue +" is not set" , "Fail");
				}
				return false;
			}
		}

		//Validate if the value is selected successfully
		if (strSkipVerify.equalsIgnoreCase("N")){
			if(objWebEdit.getAttribute("value").equals(strValue)){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				if(Environment.get("HIDE_TEST_DATA")!=null 
						&& (Environment.get("HIDE_TEST_DATA").toLowerCase().equals("y") || Environment.get("HIDE_TEST_DATA").toLowerCase().equals("yes"))){
//					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox","HIDDEN_VALUE should be set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "HIDDEN_VALUE is set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" successfully", "Pass");
					Reporter.fnWriteToHtmlOutput("Set value: "+strValue+" as hidden in text field: "+strObjName,"HIDDEN_VALUE should be set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "HIDDEN_VALUE is set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" successfully", "Pass");
				}else{
//					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox", strValue+" should be set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strValue+ " is set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" successfully", "Pass");       
					Reporter.fnWriteToHtmlOutput("Set value: "+strValue+" in text field: "+strObjName, strValue+" should be set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strValue+ " is set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" successfully", "Pass");
				}
				return true;
			}else{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				if(Environment.get("HIDE_TEST_DATA")!=null 
						&& (Environment.get("HIDE_TEST_DATA").toLowerCase().equals("y") || Environment.get("HIDE_TEST_DATA").toLowerCase().equals("yes"))){
//					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox","Set: HIDDEN_VALUE", "HIDDEN_VALUE is not set" , "Fail");
					Reporter.fnWriteToHtmlOutput("Set value: "+strValue+" as hidden in text field: "+strObjName,"Set: HIDDEN_VALUE", "HIDDEN_VALUE is not set" , "Fail");
				}else{
//					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox","Set: " + strValue, strValue +" is not set" , "Fail");
					Reporter.fnWriteToHtmlOutput("Set value: "+strValue+" in text field: "+strObjName,"Set: " + strValue, strValue +" is not set" , "Fail");
				}
				return false;
			}
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		if(Environment.get("HIDE_TEST_DATA")!=null 
				&& (Environment.get("HIDE_TEST_DATA").toLowerCase().equals("y") || Environment.get("HIDE_TEST_DATA").toLowerCase().equals("yes"))){
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox", "HIDDEN_VALUE should be set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "HIDDEN_VALUE is set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" successfully", "Pass");       
			Reporter.fnWriteToHtmlOutput("Set value: "+strValue+" as hidden in text field: "+strObjName, "HIDDEN_VALUE should be set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "HIDDEN_VALUE is set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" successfully", "Pass");
		}else{
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox", strValue+" should be set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strValue+ " is set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" successfully", "Pass");       
			Reporter.fnWriteToHtmlOutput("Set value: "+strValue+" in text field: "+strObjName, strValue+" should be set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strValue+ " is set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" successfully", "Pass");
		}
		return true;
	}


	//*****************************************************************************************
	//* Name            : fCommonSetValueEditBox (OverRidden)
	//* Description     : Set value in Edit box (Function overloaded if entered value should not be verified)
	//* CREATED RELEASE : 1310
	//* LAST MODIFIED IN :1504
	//* Author          : Anup Agarwal
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : String webElmtProp, String strObjName, String strValue, String DontVerify
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonSetValueEditBox(WebElement objWebEdit, String strObjName, String strValue, String strClear, String strSkipVerify){

		long startTime=System.currentTimeMillis();

		//Check WebElement
		if(objWebEdit == null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox", "Object not Found","Object: " + strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " doesn't exist", "Fail");
			return false;  
		}
		JavascriptExecutor js = ((JavascriptExecutor) driver);

		//Checks if input parameter is Null
		if (strValue == null){
			strValue = "";
		}

		try{
			//Checks if object is enabled
			if (objWebEdit.isEnabled() == false){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox",strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " should be Enabled", strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " is not Enabled" , "Fail");
				return false;
			}
		}catch (Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox",strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " should be Enabled", "Exception while checing if is enabled" , "Fail");
			return false;
		}

		//Set value to the Editbox  
		try{
			if(strClear.equalsIgnoreCase("Y")){ 
				objWebEdit.clear();
			}
			if(Environment.get("HIDE_TEST_DATA")!=null 
					&& (Environment.get("HIDE_TEST_DATA").toLowerCase().equals("y") || Environment.get("HIDE_TEST_DATA").toLowerCase().equals("yes"))){
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);",objWebEdit, "color: white; background-color: white; border: 4px solid red;");
			}
			objWebEdit.sendKeys(strValue);
		}catch (Exception e){   
			//driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
			try {
				if(Environment.get("HIDE_TEST_DATA")!=null 
						&& (Environment.get("HIDE_TEST_DATA").toLowerCase().equals("y") || Environment.get("HIDE_TEST_DATA").toLowerCase().equals("yes"))){
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);",objWebEdit, "color: white; background-color: white; border: 4px solid red;");
				}
				js.executeScript("arguments[0].setAttribute('value', '"+strValue+"');", objWebEdit);
			} catch (Exception e1) {
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				if(Environment.get("HIDE_TEST_DATA")!=null 
						&& (Environment.get("HIDE_TEST_DATA").toLowerCase().equals("y") || Environment.get("HIDE_TEST_DATA").toLowerCase().equals("yes"))){
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox","Set: HIDDEN_VALUE", "HIDDEN_VALUE is not set" , "Fail");
				}else{
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox","Set: " + strValue, strValue +" is not set" , "Fail");
				}
				return false;
			}
		}

		//Validate if the value is selected successfully
		if (strSkipVerify.equalsIgnoreCase("N")){
			if(objWebEdit.getAttribute("value").equals(strValue)){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				if(Environment.get("HIDE_TEST_DATA")!=null 
						&& (Environment.get("HIDE_TEST_DATA").toLowerCase().equals("y") || Environment.get("HIDE_TEST_DATA").toLowerCase().equals("yes"))){
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox","HIDDEN_VALUE should be set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "HIDDEN_VALUE is set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" successfully", "Pass");       
				}else{
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox", strValue+" should be set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strValue+ " is set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" successfully", "Pass");       
				}                
				return true;
			}else{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				if(Environment.get("HIDE_TEST_DATA")!=null 
						&& (Environment.get("HIDE_TEST_DATA").toLowerCase().equals("y") || Environment.get("HIDE_TEST_DATA").toLowerCase().equals("yes"))){
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox","Set: HIDDEN_VALUE", "HIDDEN_VALUE is not set" , "Fail");
				}else{
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox","Set: " + strValue, strValue +" is not set" , "Fail");
				}
				return false;
			}
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		if(Environment.get("HIDE_TEST_DATA")!=null 
				&& (Environment.get("HIDE_TEST_DATA").toLowerCase().equals("y") || Environment.get("HIDE_TEST_DATA").toLowerCase().equals("yes"))){
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox", "HIDDEN_VALUE should be set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), "HIDDEN_VALUE is set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" successfully", "Pass");       
		}else{
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetValueEditBox", strValue+" should be set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""), strValue+ " is set in "+strObjName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "")+" successfully", "Pass");       
		}
		return true;

	}



	// *****************************************************************************************
	// * Name : fCommonVerifyApplicationURL
	// * Description : verify that the current URL is the same as the original
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :1504
	// * Author : Dina Dodin
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	// * Input Params : 
	// * Return Values : No return value
	// *********************************************************************************

	public Boolean fCommonVerifyApplicationURL(String strURL, String bFlag){    
		String strCurrentURL = "";
		long startTime=System.currentTimeMillis();

		try{
			strCurrentURL = driver.getCurrentUrl();
			if(strCurrentURL.equals("")){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL","Curent URL parameter strExistingURL should have a value", "Existing URL parameter is empty", "Fail");
				return false;
			}           
			System.out.println(strCurrentURL);
		}
		catch (Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL","Problem occured", "There is no option to fetch the current URL from the Browser", "Fail");
			System.out.println(e);
			return false;
		}

		//Validates the existing URL
		if(!strURL.equals("")){
			strURL = strURL.replace("http://","").replace("https://","");
			String arrURL[] = strURL.split("/");
			if(bFlag.equalsIgnoreCase("true")) {    
				if(strCurrentURL.contains(arrURL[0])== false){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL","Current URL should be the same as expected URL", "Current URL ie " + 
							strCurrentURL + " is different from Expected URL ie " + strURL, "Fail");
					return false;                   
				}
			}
			else if(bFlag.equalsIgnoreCase("false")){   
				if(strCurrentURL.contains(arrURL[0])== true){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL","Current URL should be different from the origional URL", "Current URL ie " + 
							strCurrentURL + " contains original URL ie " + strURL + " which is not expected", "Fail");
					return false;                   
				}
			}
			else{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL","Valid value should be passed for bFlag parameter", "Invalid value " + bFlag + " passed for the bFLag parameter", "Fail");
				return false;              
			}
		}
		else{   
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL","Search URL parameter should not be null", "Search URL parameter is null", "Fail");
			return false;
		}   
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyApplicationURL","Existing URL should be verified successfully", "Existing URL was verified successfully", "Done");   
		return true;
	}

	//*****************************************************************************************
	//* Name            : fCommonGetWebElement
	//* Description     : Check if the webelement is properly identified
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :1504
	//* Author          : Anup Agarwal
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : WebElement properties, WebElement name
	//* Return Values   : WebElement Object
	//*****************************************************************************************
	public WebElement fCommonGetWebElement(String webElmtProp, String objName) {

		long startTime=System.currentTimeMillis();
		long endTime = startTime + WAIT_FOR_ELEMENT * 1000;

		//Get WebElement
		fCommonHandleAlert("dismiss");
		WebElement webElement = fCommonGetObject(webElmtProp,objName);
		fCommonHandleAlert("dismiss");
		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetWebElement - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return null;
		}

		//Check if the WebElement is enabled or displayed until web element configured timeout           
		boolean bIsDisplayed = false;
		boolean bIsEnabled = false;
		while (!(bIsDisplayed || bIsEnabled) && System.currentTimeMillis() < endTime) {
			try {
				bIsDisplayed = webElement.isDisplayed();                    
				bIsEnabled = webElement.isEnabled();
			}catch(StaleElementReferenceException e){
				if (System.currentTimeMillis() >= endTime) {
					e.printStackTrace();
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetWebElement - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetWebElement", "Exception occurred for getting isDisplayed or isEnabled: " + objName,"Exception: " + e, "Fail");
					return null;
				} else {
					webElement = fCommonGetObject(webElmtProp,objName);
					bIsDisplayed = bIsEnabled = false;
					sleep(WAIT_FOR_ELEMENT_POLLING_TIME);
				}
			}
		}

		//check if enabled or displayed
		if (!(bIsDisplayed || bIsEnabled)) {
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetWebElement - return null -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return null;
		} else {
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetWebElement - return webElement -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return webElement;
		}
	}


	//*****************************************************************************************
	//* Name            : fCommonGetWebElementsList
	//* Description     : Check if the webelements are identified properly 
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :1504
	//* Author          : Anup Agarwal
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : WebElement properties, WebElement name
	//* Return Values   : WebElement List
	//*****************************************************************************************
	public List<WebElement> fCommonGetWebElementsList(String webElmtProp, String objName){

		long startTime=System.currentTimeMillis();

		//Get WebElement
		List<WebElement> webElements = fCommonGetMultipleObjects(webElmtProp,objName);

		if (webElements==null || webElements.size()==0){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetWebElementsList - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetWebElementsList", "Check WebElements","WebElements are null", "Done");
			return null;
		}

		//Check if the WebElements are enabled or displayed         
		boolean bIsDisplayed = false;
		boolean bIsEnabled = false;

		try {                               
			bIsDisplayed = webElements.get(0).isDisplayed();
			bIsEnabled = webElements.get(0).isEnabled();
		}catch(Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetWebElementsList - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetWebElementsList", "Exception occurred for when getting isDisplayed or isEnabled: " + objName,"Exception: " + e, "Fail");            
			return null;            
		}

		//Validate if the elements are displayed
		if (!(bIsDisplayed || bIsEnabled)){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetWebElementsList - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetWebElementsList", objName+ " should be displayed and enabled", objName+ " is not displayed or enabled", "Done");
			return null;
		}        

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetWebElementsList - return webElements -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return webElements;


	}   




	//*****************************************************************************************
	//* Name            : fCommonSync
	//* Description     : waiting the specified time
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :
	//* Author          : Dina Dodin
	//* Input Params    : sTime - time to wait
	//* Return Values   : Void
	//*****************************************************************************************  
	public void fCommonSync(long sTime)
	{
		System.err.println(fGetThreadClassNameAndMethod() + " -- " + " calling CommonFunctions.fCommonSync: sleep for " + sTime + "ms");
		try {
			Thread.sleep(sTime);
		} catch (InterruptedException e) {          
			e.printStackTrace();
		}
	}
	//*****************************************************************************************
	//* Name            : fCommonSyncDependsOnNW
	//* Description     : waiting the specified time as per network/environment response speed 
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :
	//* Author          : Amit Kumar
	//* Input Params    : sTimeNW - To update sync time as per environment response
	//* Return Values   : Void
	//*****************************************************************************************  
	public void fCommonSyncDependsOnNW(long sTimeNW)
	{
		if (Environment.get("SYNC").equals("Y"))
			try
		{
				System.err.println(fGetThreadClassNameAndMethod() + " -- " + " calling CommonFunctions.fCommonSyncDependsOnNW: sleep for " + sTimeNW + "ms");
				Thread.sleep(sTimeNW);
		} 
		catch (InterruptedException e)
		{           
			e.printStackTrace();
		}
	}
	//*****************************************************************************************
	//* Name            : fCommonDeleteFolder
	//* Description     : Deletes a folder after deleting all its sub-folders and files
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :
	//* Author          : Abhishek Pandey
	//* Parameters      : FolderPath
	//* Dictionary      : 
	//* Date Modified   : 2-Sep-12
	//* Return Values   : None
	//*****************************************************************************************
	public boolean fCommonDeleteFolder(File FolderPath) {
		long startTime=System.currentTimeMillis();
		try{        
			if (FolderPath.isDirectory()) {
				String[] arrChildNodes = FolderPath.list();
				for (int i=0; i<arrChildNodes.length; i++) {
					fCommonDeleteFolder(new File(FolderPath, arrChildNodes[i]));
				}
			}
			FolderPath.delete();
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonDeleteFolder - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return true;

		}catch(Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonDeleteFolder - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonDeleteFolder", "Exception occurred" ,"Exception :" + e, "Fail");
			return false;
		}
	}

	//*****************************************************************************************
	//* Name : fCommonStringCompare
	//* Description : Checks for spelling
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :
	//* Author : Naveena Basetty
	//* Input Params : webElmtProp,objName, objName
	//* Return Values : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonStringCompare(String webElmtProp, String objName, String Exptval)
	{
		long startTime=System.currentTimeMillis();

		try {
			WebElement webElement = fCommonGetObject(webElmtProp, objName);
			if (webElement == null)
			{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare", "Object " + objName + " should be found" ,"Unable to find object " + objName, "Fail");
				return false; 
			}
			String TextwebElement = webElement.getText().replace("\n", " ").replace("\r", " ").replace(",", "");//22-June-2015-Dina - added to remove comma from text
			if (TextwebElement.toLowerCase().equalsIgnoreCase(Exptval.toLowerCase()))
			{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare", "Spell check performed on " +Exptval ,"Spell check done successfully", "Done");
				return true;
			}
			else if (TextwebElement.contains(Exptval)){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare", "Spell check performed on " +Exptval ,"Spell check done successfully", "Done");
				return true;
			}
			else
			{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare", "Spell check performed on " +Exptval ,"Spell check wasn't done successfully.Actual text is "+TextwebElement, "Fail");
				return false;
			}


		} catch (Exception e) {
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare", "Incorrect spelling" ,objName, "Fail");
			System.out.print("Exception :" + e);
			return false;
		}

	}



	//*****************************************************************************************
	//* Name : fCommonStringCompare (overloading)
	//* Description : Checks for spelling
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :
	//* Author : Naveena Basetty
	//* Input Params : webElmtProp,objName, objName
	//* Return Values : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonStringCompare(WebElement webElement, String objName, String Exptval)
	{
		long startTime=System.currentTimeMillis();
		try {
			//WebElement webElement = fCommonGetObject(webElmtProp, objName);
			if (webElement == null)
			{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare", "Object " + objName + " should be found" ,"Unable to find object " + objName, "Fail");
				return false; 
			}
			String TextwebElement = webElement.getText().replace("\n", " ").replace("\r", " ").replace(",", "");//22-June-2015-Dina - added to remove comma from text
			if (TextwebElement.toUpperCase().equals(Exptval.toUpperCase()))
			{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare", "Spell check performed on " +Exptval ,"Spell check done successfully", "Done");
				return true;
			}
			else
			{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare", "Spell check performed on " +Exptval ,"Spell check wasn't done successfully.Actual text is "+TextwebElement, "Fail");
				return false;
			}


		} catch (Exception e) {
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonStringCompare", "Incorrect spelling" ,objName, "Fail");
			System.out.print("Exception :" + e);
			return false;
		}

	}


	//*****************************************************************************************
	//* Name            : fCommonGetParentElement
	//* Description     : Function to get Parent Web Element
	//* CREATED RELEASE : 1310
	//* LAST MODIFIED IN :
	//* Author          : Anup Agarwal
	//* Input Params    : 
	//* Return Values   : Web Element - Depending on the success
	//*****************************************************************************************
	public WebElement fCommonGetParentElement(WebElement childElement, String objName, int ParentLevel){

		long startTime=System.currentTimeMillis();
		try{
			/////////////////////////////////////
			String str="class=\"fsrC\"";
			if(driver.getPageSource().contains(str)==true)
			{
				if (driver.findElement(By.linkText("No, thanks")) !=null)
				{
					driver.findElement(By.linkText("No, thanks")).click();
				}
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetParentElement", "Survey popup Modal should be handled","Survey popup Modal handled ", "Pass");        
			}
			/////////////////////////////////////

			//Define Parent xpath
			String strParentXpath = "..";
			if(ParentLevel > 1){
				for(int count=2; count<=ParentLevel; count++){
					strParentXpath = strParentXpath+"/..";
				}   
			}
			//Get Parent WebElement
			WebElement parentElement = childElement.findElement(By.xpath(strParentXpath));   
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetParentElement - return parentElement -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return parentElement;           
		}catch (Exception e) {      
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetParentElement - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetParentElement", "Exception occurred for object: " + objName,"Exception :" + e, "Fail");
			return null;
		} 
	}

	//*****************************************************************************************
	//* Name            : fCommonGetParentElement
	//* Description     : Function to get Parent Web Element - Overloaded function
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :
	//* Author          : Dina
	//* Input Params    : 
	//* Return Values   : Web Element - Depending on the success
	//*****************************************************************************************
	public WebElement fCommonGetParentElement(String childElement, String objName, int ParentLevel){
		long startTime=System.currentTimeMillis();
		try{
			//get the object 
			WebElement childObject = fCommonGetWebElement(childElement, objName);
			if(childObject==null)
			{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetParentElement - return null -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				return null;
			}
			//Define Parent xpath
			String strParentXpath = "..";
			if(ParentLevel > 1){
				for(int count=2; count<=ParentLevel; count++){
					strParentXpath = strParentXpath+"/..";
				}   
			}
			//Get Parent WebElement
			WebElement parentElement = childObject.findElement(By.xpath(strParentXpath));   
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetParentElement - return parentElement -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return parentElement;           
		}catch (Exception e) {          
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetParentElement - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetParentElement", "Exception occurred for object: " + objName ,"Exception :" + e, "Fail");
			return null;
		} 
	}

	//*****************************************************************************************
	//* Name            : fCommonGetSiblingElements
	//* Description     : Function to get Sibling Web Element 
	//* CREATED RELEASE : 1310
	//* LAST MODIFIED IN :
	//* Author          : Moin
	//* Input Params    : String strChildElement,String siblingDesc
	//* Return Values   : Web Element - Depending on the success
	//*****************************************************************************************
	public List<WebElement> fCommonGetSiblingElements(String strChildElement ,String siblingDesc){
		long startTime=System.currentTimeMillis();
		try{

			WebElement child = fCommonGetObject(strChildElement,strChildElement);
			WebElement parent =child.findElement(By.xpath(".."));
			List<WebElement> list= fCommonGetMultipleChildObjects(parent,siblingDesc,siblingDesc);
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetSiblingElements - return list -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return list;
		}
		catch (Exception e) {   
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetSiblingElements - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetSiblingElements", "Exception occurred for object: " + siblingDesc,"Exception :" + e, "Fail");
			return null;
		} 

	}



	//*****************************************************************************************
	//* Name            : fCommonGetSiblingElements
	//* Description     : Function to get Sibling Web Element 
	//* CREATED RELEASE : 1310
	//* LAST MODIFIED IN :
	//* Author          : Moin
	//* Input Params    : String strChildElement,String siblingDesc
	//* Return Values   : Web Element - Depending on the success
	//*****************************************************************************************
	public List<WebElement> fCommonGetSiblingElements(WebElement child ,String siblingDesc){
		long startTime=System.currentTimeMillis();
		try{

			//WebElement child = fCommonGetObject(strChildElement,strChildElement);
			WebElement parent =child.findElement(By.xpath(".."));
			List<WebElement> list= fCommonGetMultipleChildObjects(parent,siblingDesc,siblingDesc);
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetSiblingElements - return list -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return list;
		}
		catch (Exception e) {
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetSiblingElements - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetSiblingElements", "Exception occurred for object: " + siblingDesc,"Exception :" + e, "Fail");
			return null;
		} 

	}


	//*****************************************************************************************
	//* Name            : fCommonGetChildWebElementsList
	//* Description     : Get child webelements under parent webelement
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :1504
	//* Author          : Anup Agarwal
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : Parent WebElement, WebElement properties, WebElement name
	//* Return Values   : ChildWebElements List
	//*****************************************************************************************
	public List<WebElement> fCommonGetChildWebElementsList(WebElement Parent, String childElmtProp, String childName){

		long startTime=System.currentTimeMillis();

		//Get WebElements    		
		List<WebElement> childWebElements = fCommonGetMultipleChildObjects(Parent, childElmtProp, childName);
		if(childWebElements==null || childWebElements.size()==0){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetChildWebElementsList - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetChildWebElementsList", "Checking object","Object is null or empty", "Done");
			return null;
		}

		//Check if the WebElements are enabled and displayed            
		boolean bIsDisplayed = false;
		boolean bIsEnabled = false;
		try {                               
			bIsDisplayed = childWebElements.get(0).isDisplayed();
			bIsEnabled = childWebElements.get(0).isEnabled();
		}catch(Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetChildWebElementsList - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetChildWebElementsList", "Exception occurred for when getting isEnabled or isDisplayed","Exception: " + e, "Fail");           
			return null;            
		}

		//Validate if the element is displayed
		if (!(bIsDisplayed || bIsEnabled)){  
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetChildWebElementsList - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetChildWebElementsList", "Checking if child elements are displayed and enabled","Child elements are not displayed or enabled. ", "Done");   
			return null;
		}   

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetChildWebElementsList - return childWebElements -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetChildWebElementsList", "Checking if child elements are displayed and enabled","Child elements are displayed and enabled", "Done");    
		return childWebElements;

	}


	//*****************************************************************************************
	//* Name            : fCommonValidateEditBoxValue
	//* Description     : Validates value in Editbox
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :
	//* Author          : Naveena Basetty
	//* Input Params    : None
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonValidateEditBoxValue(String webElmtProp, String strObjName, String strValue){ 

		long startTime=System.currentTimeMillis();

		//Get WebElement
		WebElement Editbox = fCommonGetObject(webElmtProp,strObjName);
		if(Editbox==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateEditBoxValue - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateEditBoxValue","Get Object", "Object is null" , "Done");
			return false;
		}

		//Validation
		try{
			String actualValue = Editbox.getAttribute("value").toLowerCase();

			//validate webedit value expected value
			if(actualValue.equals(strValue.toLowerCase())){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateEditBoxValue - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateEditBoxValue: "+strObjName,"value: " + actualValue, "Expected value: " + strValue , "Done");
				return true;                
			}else{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateEditBoxValue - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "actualValue is :"+actualValue);
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "ExpectedVal is :"+strValue);
				//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateEditBoxValue: "+strObjName,"value: " + actualValue, "Expected value: " + strValue , "Done");
				return false;
			}           

		} catch (Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateEditBoxValue - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateEditBoxValue: "+strObjName, "Exception occurred","Exception: " + e, "Fail");
			return false;
		}

	}   



	//*****************************************************************************************
	//* Name            : fCommonGetKeyByValue
	//* Description     : get key of given value
	//* CREATED RELEASE : 1310
	//* LAST MODIFIED IN :
	//* Author          : Dina dodin
	//* Input Params    : map - hash map name to search in
	//*                   value - value in hash map to find its key
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public <T, E> T fCommonGetKeyByValue(Map<T, E> map, E value) {
		long startTime=System.currentTimeMillis();
		for (Entry<T, E> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetKeyByValue - return null -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return null;
	}



	//*****************************************************************************************
	//* Name            : fCommonVerifyObjectNotExist
	//* Description     : return true if object not exist, and false if object exist
	//* CREATED RELEASE : 1310
	//* Author          : Dina dodin - Surbhi added Reporter statements for Pass Scenarios
	//* LAST MODIFIED IN :1504
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : WebElement properties, WebElement name
	//* Return Values   : WebElement Object
	//*****************************************************************************************
	public boolean fCommonVerifyObjectNotExist(String webElmtProp, String objName){

		long startTime=System.currentTimeMillis();

		//Get WebElement
		WebElement webElement = fCommonGetObject(webElmtProp,objName);  

		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyObjectNotExist - return true - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyObjectNotExist", "Check Object Non Existance","Object is null", "Done");
			return true;
		}

		//Check if the WebElement is enabled            
		boolean bIsEnabled = false;
		boolean bIsDisplayed = false;

		try {                               
			bIsEnabled = webElement.isEnabled();
			bIsDisplayed = webElement.isDisplayed();
		}catch(Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyObjectNotExist - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyObjectNotExist", "Exception occurred for when getting isEnabled or isDisplayed: " + webElmtProp,"Exception: " + e, "Fail");          
			return false;           
		}

		//Validate if the element is displayed and enabled
		if (!(bIsDisplayed || bIsEnabled)) {
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyObjectNotExist - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyObjectNotExist", objName+ " should NOT exist", objName+ " is not Displayed/existing", "Done");
			return true;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonVerifyObjectNotExist - return false -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return false;


	}
	//*****************************************************************************************
	//* Name            : fCommonBrowserBackButton
	//* Description     :navigate back to previous page
	//* CREATED RELEASE : 1310
	//* LAST MODIFIED IN :05-Aug-2015 Gavril Grigorean
	//* Author          : Naveena Basetty 
	//* Input Params    : 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonBrowserBackButton() {            
		try {
			driver.navigate().back();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonBrowserBackButton", "Navigate back to previous page using back button","Navigate to previous page failed" , "Failed");
			return false;
		}

		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonBrowserBackButton", "Navigate back to previous page using back button","Navigate to previous page was successful" , "Pass");
		return true;


	} 

	//*****************************************************************************************
	//* Name            : fCommonGetObject(String objDesc, String objName)
	//* Description     : Method to get object
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN : 1504
	//* Author          : Anup Agarwal
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : String
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************         
	public WebElement fCommonGetObject(String objDesc, String objName){

		long startTime=System.currentTimeMillis();

		//Delimiters
		String[] delimiters = new String[] {":="};
		String[] arrFindByValues = objDesc.split(delimiters[0]);
		//Get Findby and Value
		String FindBy, val;
		if(arrFindByValues.length==2){
			FindBy = arrFindByValues[0];
			val = arrFindByValues[1];           
		}
		else{
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetObject for object " + objDesc + "  - return null -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");  
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetObject", "objDesc should be valid","objDesc is not valid: " + objDesc, "Fail");
			Reporter.fnWriteToHtmlOutput("Locate element: "+objName, "Element is located","Element description is not valid: " + objDesc, "Fail");
			return null;
		}

		WebDriverWait Wait=new WebDriverWait(driver,WAIT_FOR_ELEMENT);

		boolean invalidFindBy=false;
		boolean elementNotFound=false;
		String strElement = FindBy.toLowerCase();
		int intcount = 1;               
		while (intcount <= 2) {                 
			try{
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
				//Handle all FindBy cases                
				if (strElement.equalsIgnoreCase("linktext")){
					WebElement Element= Wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(val)));
					return Element;
				}
				else if (strElement.equalsIgnoreCase("xpath")){
					WebElement Element= Wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(val)));
					return Element;
				}
				else if (strElement.equalsIgnoreCase("name")){
					WebElement Element= Wait.until(ExpectedConditions.presenceOfElementLocated(By.name(val)));
					return Element;
				}
				else if (strElement.equalsIgnoreCase("id")){
					WebElement Element= Wait.until(ExpectedConditions.presenceOfElementLocated(By.id(val)));
					return Element;
				}
				else if (strElement.equalsIgnoreCase("classname")){
					WebElement Element= Wait.until(ExpectedConditions.presenceOfElementLocated(By.className(val)));
					return Element;
				}
				else if (strElement.equalsIgnoreCase("cssselector")){
					WebElement Element= Wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(val)));
					return Element;
				}   
				else if (strElement.equalsIgnoreCase("partialLinkText")){
					WebElement Element= Wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(val)));
					return Element;                     
				}
				else{
					invalidFindBy=true;
					Reporter.fnWriteToHtmlOutput("Locate element: "+objName, "Element is located using property name :" + FindBy,"Property name specified for element " + objName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " is invalid","Fail");
					return null;
				}                       
			}
			catch(Exception e){
				e.printStackTrace();
				//Added by Kaiqi Tang to handle mobile popup, 02/23/2016
				if(driverType.contains("ANDROID") || driverType.contains("IOS")){
					fGuiHandleNoThanksModalPgSrc();
				}
//				fCommonHandleAlert("dismiss");
				System.out.println("Exception while getting object " + objDesc + "  ... Checking for PopUps... ");
				intcount = handlePopUps(intcount);
				if (intcount == 1){
					elementNotFound=true;
					return null;
				}                       
				intcount = intcount + 1;
				//Select browser in focus
				try{
					((JavascriptExecutor) driver).executeScript("focus()");
				}
				catch(Exception e1)
				{
					//System.out.println("Dina - exception fCommonGetObject: " + e1.toString());
					fCommonHandleAlert("dismiss");
				}

			}
			finally{                             

				if (invalidFindBy==true){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetObject for object " + objDesc + "   - return null -- invalid FindBy locator -- has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms"); 
				}
				if (elementNotFound==true){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetObject for object " + objDesc + "   - return null --element not found --  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				}else{
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetObject "+strElement+" for object " + objDesc + "- return success -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");    
				}

				driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS);  //reset implicitlyWait()

			}
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetObject for object " + objDesc + "  - return null -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return null;               
	}


	//*****************************************************************************************
	//*	Name		    : handlePopUps(int intCount)
	//*	Description	    : Method to handle the PopUps
	//*	CREATED RELEASE : 1506
	//*	LAST MODIFIED IN : 1504
	//*	Author		    : Gavril Grigorean
	//*	Updated by	    : Gavril Grigorean; Dina Dodin
	//*	Updated Date	: April 24, 2015; 30-Apr-2015; May-01-2015
	//*	Input Params	: Int
	//*	Return Values	: int 
	//*****************************************************************************************	    

	public int handlePopUps(int intcount) {

		long startTime=System.currentTimeMillis();

		int ret =  handlePopUps(intcount, WAIT_FOR_POPUP_ELEMENTS);

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "handlePopUps - return intcount -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");

		return ret;
	}


	/**
	 * Handle popups method with timeout parameter 
	 * 
	 * @param intcount
	 * @param timeout
	 * @return
	 */
	private int handlePopUps(int intcount, int timeout) {

		WebDriverWait Wait=new WebDriverWait(driver, timeout);

		WebElement element=null;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
		try{
			if(driverType.contains("ANDROID")){
				element= Wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='fsrDeclineButton']")));
				String elementClassAttr = element.getAttribute("class");
				if(element.isEnabled()){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "handlePopUps - Survey or Chat Pop Up Exist - Closing it...");
					intcount--;        
					element.click();
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "handlePopUps", "Survey or Chat Pop Up Exist", 
							"Survey or Chat Pop up Handled by clicking element with class: " + elementClassAttr, "Done");
					return intcount;
				}

			}
			element= Wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='fsrCloseBtn' or @id='tcChat_btnCloseChat_img' or @id='tcXF18000443_xf-10·1']")));
			String elementClassAttr = element.getAttribute("class");
			if(element.isEnabled()){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "handlePopUps - Survey or Chat Pop Up Exist - Closing it...");
				intcount--;        
				element.click();
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "handlePopUps", "Survey or Chat Pop Up Exist", 
						"Survey or Chat Pop up Handled by clicking element with class: " + elementClassAttr, "Done");
				return intcount;
			}

		}catch(Exception e){
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS); //reset implicitlyWait()
		}

		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS); //reset implicitlyWait()    }

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "handlePopUps - No Pop Up displayed...");

		return intcount;
	}

	//*****************************************************************************************
	//* Name            : fCommonGetObject
	//* Description     : Method to get object (OverRidden Function to get object using passed WebDriver)
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :
	//* Author          : Anup Agarwal
	//* Input Params    : WebDriver newDriver, String objDesc, String objName
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************         
	public WebElement fCommonGetObject(WebDriver newDriver, String objDesc, String objName){

		//Check WebDriver
		if(newDriver == null){
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetObject", "WebDriver should not be null","WebDriver is null", "Fail");
			return null;
		}

		//Delimiters
		String[] delimiters = new String[] {":="};
		String[] arrFindByValues = objDesc.split(delimiters[0]);
		//Get Findby and Value
		String FindBy, val;
		if(arrFindByValues.length==2){
			FindBy = arrFindByValues[0];
			val = arrFindByValues[1];           
		}else{
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetObject", "objDesc should be valid","objDesc is not valid: " + objDesc, "Fail");
			return null;
		}
		String strElement = FindBy.toLowerCase();
		WebElement objElement=null;
		boolean bIsDisplayed = false;
		boolean bIsEnabled = false;
		int intCount = 1;

		while (intCount<=3){
			try{
				//Get WebElement
				if (strElement.equalsIgnoreCase("linktext")){
					objElement = newDriver.findElement(By.linkText(val));
				}
				else if (strElement.equalsIgnoreCase("xpath")){
					objElement = newDriver.findElement(By.xpath(val));
				}
				else if (strElement.equalsIgnoreCase("name")){
					objElement = newDriver.findElement(By.name(val));
				}
				else if (strElement.equalsIgnoreCase("id")){
					objElement = newDriver.findElement(By.id(val));
				}
				else if (strElement.equalsIgnoreCase("classname")){
					objElement = newDriver.findElement(By.className(val));
				}
				else if (strElement.equalsIgnoreCase("cssselector")){
					objElement = newDriver.findElement(By.cssSelector(val));
				}   
				else if (strElement.equalsIgnoreCase("partialLinkText")){
					objElement = newDriver.findElement(By.partialLinkText(val));                            
				}
				else{
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetObject", "Property name :" + FindBy,"Property name specified for object " + objName + " is invalid", 

							"Fail");
					return null;
				}       

				//Check if the Webelement is enabled or displayed           
				if(objElement != null){
					bIsDisplayed = objElement.isDisplayed();                    
					bIsEnabled = objElement.isEnabled();

					//Validate if the element is displayed/enabled
					if (bIsDisplayed || bIsEnabled){                
						break;
					}
				}                   
				intCount++;         
			}catch (Exception e){
				newDriver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
				if(newDriver.findElement(By.linkText("No, thanks")) != null){
					newDriver.findElement(By.linkText("No, thanks")).click();
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "Survey Popup", "Survey Pop Up Exist", "Survey Pop up Handled", "Done");
				}
				newDriver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS);
				intCount++;
			}
		}
		return objElement;                     
	}  


	//*****************************************************************************************
	//* Name            : fCommonGetMultipleObjects(String objDesc, String objName)
	//* Description     : Method to get multiple objects having same property
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :1504
	//* Author          : Anup Agarwal
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : String
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************         
	public List<WebElement> fCommonGetMultipleObjects(String objDesc, String objName){

		long startTime=System.currentTimeMillis();

		//Delimiters
		int noOfElements=0;
		String[] delimiters = new String[] {":="};
		String[] arrFindByValues = objDesc.split(delimiters[0]);
		//Get Findby and Value
		String FindBy, val;
		if(arrFindByValues.length==2){
			FindBy = arrFindByValues[0];
			val = arrFindByValues[1];           
		}
		else{
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleObjects for object " + objDesc + "  - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleObjects", "objDesc should be valid","objDesc is not valid: " + objDesc, "Fail");
			Reporter.fnWriteToHtmlOutput("Get all elements matching: "+objName, "All elements matching element condition are fetched","Element description is not valid: " + objDesc, "Fail");
			return null;
		}

		WebDriverWait Wait=new WebDriverWait(driver, WAIT_FOR_MULTIPLE_ELEMENTS);
		int intcount = 1;   
		boolean invalidFindBy=false;
		boolean elementNotFound=false;
		String strElement = FindBy.toLowerCase();

		while (intcount <= 2){                  
			try{
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
				//Handle all FindBy cases              
				if (strElement.equalsIgnoreCase("linktext")){
					List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(val)));
					noOfElements=elements.size();
					return elements;
				}
				else if (strElement.equalsIgnoreCase("xpath")){
					List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(val)));
					noOfElements=elements.size();
					return elements;
				}
				else if (strElement.equalsIgnoreCase("name")){
					List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(val)));
					noOfElements=elements.size();
					return elements;
				}
				else if (strElement.equalsIgnoreCase("id")){
					List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(val)));
					noOfElements=elements.size();
					return elements;
				}
				else if (strElement.equalsIgnoreCase("classname")){
					List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(val)));
					noOfElements=elements.size();
					return elements;
				}
				else if (strElement.equalsIgnoreCase("cssselector")){
					List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(val)));
					noOfElements=elements.size();
					return elements;
				}   
				else if (strElement.equalsIgnoreCase("partialLinkText")){
					List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.partialLinkText(val)));
					noOfElements=elements.size();
					return elements;                        
				}
				else{
					invalidFindBy=true;
//					Reporter.fnWriteToHtmlOutput("fCommonGetObject", "Property name :" + FindBy,"Property name specified for object " + objName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " is invalid","Fail");
					Reporter.fnWriteToHtmlOutput("Locate elements matching: "+objName, "Elements located using property name :" + FindBy,"Property name specified for element " + objName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " is invalid","Fail");
					return null;
				}                       
			}catch(Exception e){
//				intcount = handlePopUps(intcount);
				if (intcount == 1){
					elementNotFound=true;
					e.printStackTrace();
					//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleObjects", objName+" should be present", objName+" is not present", "Fail");
					//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleObjects", "Exception occurred for object: " + objName,"Exception :" + e.toString(), "Fail");                            
					return null;
				}                       
				intcount = intcount + 1;
				//Select browser in focus
				((JavascriptExecutor) driver).executeScript("focus()");
			}
			finally{
				if(invalidFindBy==true){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleObjects for object " + objDesc + "   - return null -- invalid FindBy locator -- has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");  
				}
				if(elementNotFound){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleObjects for object " + objDesc + "  - return null --element not found-- has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms. Exception occurred ");
				}else{
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleObjects  "+strElement+" for object " + objDesc + "and number of objects "+noOfElements+"-return success -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				}

				driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS); //reset implicitlyWait
			}
		}
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleObjects for object " + objDesc + "  - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return null;               
	}



	//*****************************************************************************************
	//* Name            : fCommonGetMultipleChildObjects
	//* Description     : Method to get multiple child objects under a parent object
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :1504
	//* Author          : Anup Agarwal
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : WebElement parent, String objDesc, String objName
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************         
	public List<WebElement> fCommonGetMultipleChildObjects(final WebElement parent, final String objDesc, String objName){

		final long startTime=System.currentTimeMillis();

		//Verify parent element
		if(parent == null){ 
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonLaunchEnvironemntname for object " + objDesc + "  - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput("fCommonGetMultipleChildObjects", "Parent webelement should be present", "Parent webelement is not present", "Done");
			return null;
		}   

		//Delimiters
		String[] delimiters = new String[] {":="};
		String[] arrFindByValues = objDesc.split(delimiters[0]);
		//Get Findby and Value
		String FindBy;
		final String val;
		if(arrFindByValues.length==2){
			FindBy = arrFindByValues[0];
			// add . when for the xpath to look for childs
			if(arrFindByValues[1].startsWith("//")){
				val = "."+arrFindByValues[1];
			}else {
				val = arrFindByValues[1];
			}
			//val = val.replaceAll("//", "./");
			//val = val.replace("//", ".//");
		}
		else{
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonLaunchEnvironemntname for object " + objDesc + "   - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleChildObjects", "objDesc should be valid","objDesc is not valid: " + objDesc, "Fail");
			return null;
		}

		WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_MULTIPLE_CHILD_ELEMENTS);
		List<WebElement> webElements=null;
		int intcount = 1;	            
		boolean invalidFindBy=false;
		boolean elementNotFound=false;
		String strElement = FindBy.toLowerCase();
		while (intcount <= 2){	            	
			try{
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS); //nullify implicitlyWait()
				//Handle all FindBy cases               
				if (strElement.equalsIgnoreCase("linktext")){                  		  		
					webElements= wait.until(new ExpectedCondition<java.util.List<WebElement>>() {
						@Override
						public List<WebElement> apply(WebDriver d) {
							return parent.findElements(By.linkText(val));
						}
					});			
					return webElements;
				}
				else if (strElement.equalsIgnoreCase("xpath")){
					webElements= wait.until(new ExpectedCondition<java.util.List<WebElement>>() {
						@Override
						public List<WebElement> apply(WebDriver d) {
							return parent.findElements(By.xpath(val));
						}
					});	
					return webElements;
				}
				else if (strElement.equalsIgnoreCase("name")){
					webElements= wait.until(new ExpectedCondition<java.util.List<WebElement>>() {
						@Override
						public List<WebElement> apply(WebDriver d) {
							return parent.findElements(By.name(val));
						}
					});
					return webElements;
				}
				else if (strElement.equalsIgnoreCase("id")){
					webElements= wait.until(new ExpectedCondition<java.util.List<WebElement>>() {
						@Override
						public List<WebElement> apply(WebDriver d) {
							return parent.findElements(By.id(val));
						}
					});	
					return webElements;
				}
				else if (strElement.equalsIgnoreCase("classname")){
					webElements= wait.until(new ExpectedCondition<java.util.List<WebElement>>() {
						@Override
						public List<WebElement> apply(WebDriver d) {
							return parent.findElements(By.className(val));
						}
					});	
					return webElements;
				}
				else if (strElement.equalsIgnoreCase("cssselector")){
					webElements= wait.until(new ExpectedCondition<java.util.List<WebElement>>() {
						@Override
						public List<WebElement> apply(WebDriver d) {
							return parent.findElements(By.cssSelector(val));
						}
					});	
					return webElements;
				}
				else if (strElement.equalsIgnoreCase("tagname")){
					webElements= wait.until(new ExpectedCondition<java.util.List<WebElement>>() {
						@Override
						public List<WebElement> apply(WebDriver d) {
							return parent.findElements(By.tagName(val));
						}
					});		
					return webElements;
				}
				else{
					invalidFindBy=true;
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleChildObjects", "Property name :" + FindBy,"Property name specified for object " + objName + " is invalid", "Fail");
					return null;
				}                       
			}
			catch(Exception e){     
				//System.out.println("fCommonGetMultipleChildObjects exception caught : " +e);
				intcount = handlePopUps(intcount);
				if (intcount == 1){
					elementNotFound=true;
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleChildObjects", objName+" is not identified", "Exception :" + e.toString(), "Fail");
					return null;
				}                       
				intcount = intcount + 1;
				//Select browser in focus
				((JavascriptExecutor) driver).executeScript("focus()");
			}
			finally{
				if(invalidFindBy==true){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleChildObjects for object " + objDesc + "   - return null --invalid findBy value-- was delayed with :  "+(System.currentTimeMillis() - startTime) + "ms");
				}
				if(elementNotFound==true){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleChildObjects for object " + objDesc + "  - return null --element not found -- has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");  
				}else{
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleChildObjects "+strElement+" for object " + objDesc + " -- return success --  was delayed with :  "+(System.currentTimeMillis() - startTime) + "ms"); 
				}                       
				driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS); //reset implicitlyWait
			}
		}
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetMultipleChildObjects for object " + objDesc + "  - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return null;               
	}

	//*****************************************************************************************
	//* Name            : fCommonCheckObjectExistance
	//* Description     : Set value in Edit box
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :1504
	//* Author          : Shraddha Girme
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : None
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonCheckObjectExistance(String objDesc){

		long startTime=System.currentTimeMillis();
		WebElement webElement = null;
		//Get WebElement
		try{
			webElement = fCommonGetObject(objDesc,objDesc);
		}
		catch(Exception e){

		}

		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance", "Check Object Existance","Object is null", "Done");
			return false;
		}

		//Check if the WebElement is enabled and displayed          
		boolean bIsEnabled = false;
		boolean bIsDisplayed = false;

		try {                               
			bIsEnabled = webElement.isEnabled();
			bIsDisplayed = webElement.isDisplayed();
		}catch(Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance", "Exception occurred for when getting isEnabled or isDisplayed " + objDesc,"Exception: " + e, "Fail");           
			Reporter.fnWriteToHtmlOutput("Verify element is enabled and displayed", "Element is enabled and displayed ","Exception while checking element is enabled or displayed: " + e.getLocalizedMessage(), "Fail");
			return false;           
		}

		//Validate if the element is enabled
		if (!(bIsEnabled|| bIsDisplayed)) {
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput("Verify element is enabled and displayed", "Element is enabled and displayed", "Element is not enabled or displayed", "Done");
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		Reporter.fnWriteToHtmlOutput("Verify element is enabled and displayed", "Element is enabled and displayed", "Element is enabled and displayed", "Done");
		return true;

	}

	//*****************************************************************************************
	//* Name            : fCommonCheckObjectExistance
	//* Description     : Set value in Edit box
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :1504
	//* Author          : Shraddha Girme
	//* Overloaded by   : Pushkar Singh Rawat
	//* Updated Date    : July 25, 2017
	//* Input Params    : None
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonCheckObjectExistance(WebElement objDesc){

		long startTime=System.currentTimeMillis();
		WebElement webElement = null;
		//Get WebElement
		try{
			webElement = objDesc;
		}
		catch(Exception e){

		}

		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance", "Check Object Existance","Object is null", "Done");
			return false;
		}

		//Check if the WebElement is enabled and displayed          
		boolean bIsEnabled = false;
		boolean bIsDisplayed = false;

		try {                               
			bIsEnabled = webElement.isEnabled();
			bIsDisplayed = webElement.isDisplayed();
		}catch(Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance", "Exception occurred for when getting isEnabled or isDisplayed " + objDesc,"Exception: " + e, "Fail");           
			return false;           
		}

		//Validate if the element is enabled
		if (!(bIsEnabled|| bIsDisplayed)) {
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance", "Check if object is enabled and is displayed", objDesc+ " is not enabled or enabled", "Done");
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance", "Check if object is enabled and displayed", objDesc+ " is enabled and displayed", "Done");
		return true;

	}



	//*****************************************************************************************
	//* Name            : fCommonSwitchToWindow
	//* Description     : switch to window based on index
	//* CREATED RELEASE : 1310
	//* LAST MODIFIED IN :
	//* Author          : Abhishek Pandey
	//* Input Params    : iIndex
	//* Date Created    : 25-Jun-13
	//*****************************************************************************************
	public void fCommonSwitchToWindow(int iIndex){
		System.out.println("WindowHandle before: " + driver.getWindowHandle());
		long startTime=System.currentTimeMillis();
		Set<String> collWindowHandles = driver.getWindowHandles();
		if(collWindowHandles.size() < iIndex+1){
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSwitchToWindow", "Specified index out of range.", "Available Windows: " + collWindowHandles.size() + "Specified Index: " + iIndex , "Fail");
		}
		else{
			Iterator<String> iter = collWindowHandles.iterator();
			for(int i=0;i<collWindowHandles.size();i++){                
				String sWindowHandle = iter.next();
				if(i==iIndex){driver.switchTo().window(sWindowHandle);}
			}
		}
		System.out.println("WindowHandle after: " + driver.getWindowHandle());
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSwitchToWindow - return void -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
	}



	//*****************************************************************************************
	//* Name            : fCommonGetXMLNodes
	//* Description     : Retrives all xml nodes for a xml path
	//* CREATED RELEASE : 1310
	//* LAST MODIFIED IN :
	//* Author          : Anup Agarwal
	//* Input Params    : File xmlFile, String xPath
	//* Return Values   : NodeList Object
	//*****************************************************************************************
	public NodeList fCommonGetXMLNodes(File xmlFile, String strXPath){
		xmlFile = new File("C:/Selenium/workspace/att.com_automation_trunk/CSI_Xmls/TST16/sales_se2XWabZINmN6-iT7EivBbVIOr9RqcYd!1617912662/Fail_13Jan2017_022323_key135.163.193.222-59bd1b5d15990338f85-7681_sidse2XWabZINmN6-iT7EivBbVIOr9RqcYd!1617912662!1484302952153_InquireTransportProductAvailabilityServiceResponse.xml");
		strXPath = "//userName";
		long startTime=System.currentTimeMillis();
		try{
			//Create Document Object
			DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbFac.newDocumentBuilder();
			Document xmldoc = docBuilder.parse( xmlFile );
			xmldoc.getDocumentElement().normalize();

			//Create xPath object
			XPathFactory xPathfac = XPathFactory.newInstance();
			XPath objXpath = xPathfac.newXPath();           
			XPathExpression xpathExpr = objXpath.compile( strXPath );

			//Get List of nodes
			NodeList objNodeList = (NodeList)xpathExpr.evaluate(xmldoc, XPathConstants.NODESET);

			//NodeList objNodeList = xmldoc.getElementsByTagName("description");
			//System.out.println(objNodeList.item(0).getTextContent());


			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetXMLNodes - return objNodeList -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return objNodeList;         
		}catch(Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetXMLNodes - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetXMLNodes", "Exception occurred","Exception :" + e, "Fail");         
			return null;        
		}   
	}





	//*****************************************************************************************
	//* Name            : fCommonSetCookieDomain
	//* Description     : Sets cookie domain
	//* CREATED RELEASE : 1402
	//* LAST MODIFIED IN :
	//* Author          : Naveena Basetty
	//* Input Params    : CookieKey
	//* Return Values   : boolean --> this function works on chrome and firefox ONLY
	//*****************************************************************************************

	public  boolean fCommonSetCookieDomain(String CookieKey)
	{
		long startTime=System.currentTimeMillis();
		try{
			Cookie ck = driver.manage().getCookieNamed(CookieKey);
			String value =ck.getValue();
			driver.manage().deleteCookieNamed(CookieKey);
			Calendar c1 = new GregorianCalendar();
			c1.set(2017, 12, 12, 12, 00, 00);
			Date expiry = c1.getTime();

			Cookie cookie = new Cookie(CookieKey,value , ".att.com", "/",expiry);
			driver.manage().addCookie(cookie);
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetCookieDomain", "Set cookie domain for key:"+ CookieKey,".att.com" , "Pass");
			if(CookieKey == "tutG"){
				System.out.println("tutG: "+driver.manage().getCookieNamed("tutG"));
			}else if(CookieKey == "TG-PD-ID"){
				System.out.println("TG-PD-ID: "+driver.manage().getCookieNamed("TG-PD-ID"));
			}
			fCommonSync(1000);
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetCookieDomain - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return (true);
		}
		catch (Exception e)
		{
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSetCookieDomain - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetCookieDomain", "Unable to set cookie domain for key:"+ CookieKey,"Exception :" + e, "Fail");
			return(false);
		}
	}


	//*****************************************************************************************
	//* Name            : fCommonValidateCookieValue
	//* Description     : Validate cookie value
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :
	//* Author          : Gavril Grigorean
	//* Input Params    : CookieKey, CookieValue
	//* Return Values   : boolean --> this function works on chrome and firefox ONLY
	//*****************************************************************************************

	public  boolean fCommonValidateCookieValue(String cookieKey, String cookieValue)
	{
		long startTime=System.currentTimeMillis();

		String ckValue =fCommonGetCookieValue(cookieKey);

		if (!ckValue.equals(cookieValue)){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateCookieValue - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateCookieValue", "Validate the cookie value", "Cookie value - "+ckValue+"- is not matching expected value : "+cookieValue, "Fail");
			return(false);
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateCookieValue - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateCookieValue", "Validate the cookie value", "Cookie value - "+ckValue+"- is matching expected value : "+cookieValue, "Pass");
		return(true);

	}


	//*****************************************************************************************
	//* Name            : fCommonGetCookieValue
	//* Description     : Get Cookie Value
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :
	//* Author          : Gavril Grigorean
	//* Input Params    : CookieKey
	//* Return Values   : String --> this function works on chrome and firefox ONLY
	//*****************************************************************************************

	public  String fCommonGetCookieValue(String cookieKey)
	{
		long startTime=System.currentTimeMillis();

		if(cookieKey==null || cookieKey.equals("")){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetCookieValue - return null - cookieKey is empty or null -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return null;
		}

		try{
			Cookie ck = driver.manage().getCookieNamed(cookieKey);
			if(ck==null){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetCookieValue - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateCookieValue", "Get cookie "+cookieKey+" value", "Cookie "+cookieKey+" is not present", "Done");
				return null;
			}

			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetCookieValue - return ck.getValue() -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateCookieValue", "Get cookie "+cookieKey+" value", "Cookie value retrieve successful. "+cookieKey+"="+ck.getValue(), "Done");
			return ck.getValue();
		}
		catch (Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetCookieValue - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetCookieDomain", "Get cookie "+cookieKey+" value","Exception :" + e, "Fail");
			return null;
		}
	}


	//*****************************************************************************************
	//* Name            : fCommonIsCookiePresent
	//* Description     : Check if cookie is present
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :
	//* Author          : Gavril Grigorean
	//* Input Params    : CookieKey
	//* Return Values   : boolean --> this function works on chrome and firefox ONLY
	//*****************************************************************************************

	public  boolean fCommonIsCookiePresent(String cookieKey)
	{
		long startTime=System.currentTimeMillis();
		try{
			Cookie ck = driver.manage().getCookieNamed(cookieKey);
			if(ck==null){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsCookiePresent - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateCookieValue", "Check if cookie is present", "Cookie is not present in browser session", "Fail");
				return false;
			}
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsCookiePresent - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateCookieValue", "Check if cookie is present", "Cookie is present in browser session", "Pass");
			return true;
		}
		catch (Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsCookiePresent - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonSetCookieDomain", "Check if cookie is present","Exception :" + e, "Fail");
			return false;
		}
	}


	//*****************************************************************************************
	//* Name            : fCommonChangeProxyFF
	//* Description     : change proxy in FF
	//* CREATED RELEASE : 1402
	//* LAST MODIFIED IN :
	//* Author          :Dina dodin
	//* Input Params    : 
	//* Return Values   : boolean --> this function works firefox ONLY
	//*****************************************************************************************
	public void fCommonChangeProxyFF()
	{
		long startTime=System.currentTimeMillis();

		String sAuthFilePath = System.getProperty("user.dir") + "\\src\\pckgSeleniumCommon\\";
		try{

			String sFile = "";
			if (System.getProperty("os.arch").contains("x86"))
			{
				sFile = sAuthFilePath + "ChangeProxyFF_32bit.exe";
			}
			else if (System.getProperty("os.arch").contains("x64"))
			{
				sFile = sAuthFilePath + "ChangeProxyFF_64bit.exe";
			}
			File f1 = new File(sFile);
			if (f1.exists())
			{
				Runtime.getRuntime().exec(sFile);
			}

		}catch (IOException e)
		{
			//do nothing
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonChangeProxyFF - return void -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
	}

	//*****************************************************************************************
	//* Name            : fValidateDynamicPageDisplayed
	//* Description     : Function to validate dynamic web page title
	//* CREATED RELEASE : 1402
	//* LAST MODIFIED IN :1504
	//* Author          : Dina
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 26, 2015
	//* Input Params    : 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public String fCommonValidateDynamicPageDisplayed(String webElmtProp,String DiffDynamicPageTitles){

		long startTime=System.currentTimeMillis(); //startime for the function
		long endTime=startTime+(WAIT_FOR_PAGE*1000);  //end time for page to load
		String checkPage = "";

		//get the POM class name from currentThread; this value will come from Environment.xls from URLs sheet
		//String classNamePOM=Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", ""); 
		String classNamePOM=Thread.currentThread().getStackTrace()[2].getClassName();//using full path to distinguish URLs between diff packages with similar class names

		//check if current url is matching the expected url; if is set
		if(Environment.get(classNamePOM)!=null && Environment.get(classNamePOM)!="" && DiffDynamicPageTitles!=""){
			if(waitForUrlSync(driver, Environment.get(classNamePOM),startTime,endTime)==false){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed", "Check url sync","URL doesn't contain: " + Environment.get(classNamePOM), "Fail");
				Environment.put("FAILURE_ERROR","Element was not found");
				Environment.put("ROOT_CAUSE", "Element not found in page");
				MainDriver.incrementElementNotFoundCount();
				return null;
			}
		}

		//check if current host is matching the expected host
		if(Environment.get("ENV_HOST")!=null && Environment.get("ENV_HOST")!=""){
			if(waitForHostSync(driver, Environment.get("ENV_HOST"),startTime,endTime)==false){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed", "Check host sync","Host sync failed", "Fail");
				Environment.put("FAILURE_ERROR","Element was not found");
				Environment.put("ROOT_CAUSE", "Element not found in page");
				MainDriver.incrementElementNotFoundCount();
				return null;
			}
		}

		//check node sync remains contanst for 2 consecutive seconds
		if(waitForNodesSyncByPageSource(driver, startTime,endTime)==false){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed", "Check node sync","Node sync failed", "Fail");
			Environment.put("FAILURE_ERROR","Element was not found");
			Environment.put("ROOT_CAUSE", "Element not found in page");
			MainDriver.incrementElementNotFoundCount();
			return null;
		}
		//check wait for page js.executeScript("return document.readyState").equals("complete")
		if(waitForPageFullyLoaded(driver,startTime,endTime)==false){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed", "Check WaitPageFullyLoaded","WaitPageFullyLoaded failed", "Fail");
			Environment.put("FAILURE_ERROR","Element was not found");
			Environment.put("ROOT_CAUSE", "Element not found in page");
			MainDriver.incrementElementNotFoundCount();
			return null;
		}
		/*        //check ajax calls to be completed
	        if(waitForAjaxCallsToComplete(driver,startTime,endTime)==false && pendingAjaxCalls==""){
	            System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
	            Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed", "Check waitForAjaxCallsToComplete","waitForAjaxCallsToComplete failed", "Fail");
	            return null;
	        }else if(pendingAjaxCalls!=""){
	            Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed", "Check waitForAjaxCallsToComplete","waitForAjaxCallsToComplete Done : "+pendingAjaxCalls+" Ajax calls hung up", "Done");
	        }*/

		//check for page is not still showing the loading progress circle
		if(waitForPageIsLoadingProgress(driver,startTime,endTime)==false){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForPageIsLoadingProgress", "Check for page is still showing is loading progress circle","WaitForPageIsLoadingProgress failed", "Fail");
			Environment.put("FAILURE_ERROR","Element was not found");
			Environment.put("ROOT_CAUSE", "Element not found in page");
			MainDriver.incrementElementNotFoundCount();
			return null;		
		}
//		WaitForSpinnerToLoaded();

		checkPage = checkPageContainsText(webElmtProp, DiffDynamicPageTitles, startTime,endTime);

		if (checkPage==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - Check for page for: " + DiffDynamicPageTitles + ". Value of '" + webElmtProp + "' was different than expected");
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//comented reporter line below because in some flows this method is called for a modal that may or may not appear all the times
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText", "Check for page for: " + DiffDynamicPageTitles, "Value of '" + webElmtProp + "' was different than expected", "Fail");
			Environment.put("FAILURE_ERROR","Element was not found");
			Environment.put("ROOT_CAUSE", "Element not found in page");
			MainDriver.incrementElementNotFoundCount();
			return null;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonValidateDynamicPageDisplayed - return string - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText", "Check for page for: " + DiffDynamicPageTitles, "CheckPageContainsText passed", "Pass");
		Reporter.fnWriteToHtmlOutput("Verify page is loaded", "Page is loaded succesfully", "Page is loaded sucessfully", "Pass");

		return checkPage;	



	}

	//*****************************************************************************************
	//* Name            : fCommonGuiIsDisplayed
	//* Description     : Function to validate dynamic web page title
	//* CREATED RELEASE : 1406
	//* LAST MODIFIED IN :1504
	//* Author          : Sourabh
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Input Params    : 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonGuiIsDisplayed(String webElmtProp, String objName){

		long startTime=System.currentTimeMillis();

		//Get WebElement 
		WebElement webElement = fCommonGetObject(webElmtProp,objName); 
		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed", "Check Object Non Existance","Object is null", "Fail");
			return false;
		}

		boolean ret = fCommonGuiIsDisplayed(webElement,objName);
		System.out.println(webElement.getText());
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed - return "+ret+" -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return ret;

	}

	//*****************************************************************************************
	//* Name            : fCommonGuiIsDisplayed
	//* Description     : Function to validate dynamic web page title
	//* CREATED RELEASE : 1510
	//* LAST MODIFIED IN :1510
	//* Author          : Sourabh
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : Oct 12, 2015
	//* Input Params    : 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonGuiIsDisplayed(WebElement webElement, String objName){

		long startTime=System.currentTimeMillis();

		//Check if the Webelement is displayed                         
		boolean bIsDisplayed = false;             

		try {                               
			bIsDisplayed = webElement.isDisplayed();

		}catch(Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed " + objName, "Exception occurred for when getting isDisplayed: ","Exception: " + e, "Fail");           
			return false;           
		}

		//Validate if the element is enabled
		if (!(bIsDisplayed)) {
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed "+ objName+" - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed", "Check if object is displayed", objName+ " is not displayed", "Done");
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed "+ objName+" - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed", "Check if object is displayed", objName+ " is displayed", "Done");
		return true;

	}

	//*****************************************************************************************
	//* Name            : fCommonCloseAWindow
	//* Description     : To close any window opened
	//* CREATED RELEASE : 1406
	//* LAST MODIFIED IN :
	//* Author          : Sourabh
	//* Input Params    : 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonCloseAWindow()
	{
		long startTime=System.currentTimeMillis();
		fCommonSync(2000);
		Set<String> NoOfWindowsBefore = driver.getWindowHandles();
		int countBefore = NoOfWindowsBefore.size();
		driver.close();
		Set<String> NoOfWindowsAfter = driver.getWindowHandles();
		int countAfter = NoOfWindowsAfter.size();
		if (countBefore-countAfter ==1){ 
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonCloseAWindow", "To close an extra window","Closed one window" , "Done");
			fCommonSync(2000);
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCloseAWindow - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return true;
		}
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCloseAWindow - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return false;
	}


	//*****************************************************************************************
	//* Name            : fCommonHandleAlert
	//* Description     : To close any window opened
	//* CREATED RELEASE : 1406
	//* LAST MODIFIED IN : 10/27/2015 By Kaiqi
	//* Author          : Sourabh
	//* Input Params    : String - accept or dismiss as per the intent of the flow
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonHandleAlert(String intent)
	{
		if(driverType.contains("IE")){
			long startTime=System.currentTimeMillis();
			int i = 0;
			while(i < 2)
			{
				try{
					if(intent.equalsIgnoreCase("accept"))
					{

						driver.switchTo().alert().getText();
						driver.switchTo().alert().accept();
						Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonHandleAlert", "To accept the alert","Accepted the alert" , "Done");
						System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonHandleAlert - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
						return true;
					}
					else if (intent.equalsIgnoreCase("dismiss"))
					{
						driver.switchTo().alert().getText();
						driver.switchTo().alert().dismiss();
						Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonHandleAlert", "To dismiss the alert","Dismissed the alert" , "Done");
						System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonHandleAlert - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");

						return true;
					}
				}
				catch(Exception e)
				{
					//System.out.println("Dina - exception fCommonHandleAlert: " + e.toString());
					/*	System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonHandleAlert - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
						Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonHandleAlert", "To handle the alert, either accept or dismiss","No alert found" , "Fail");
						return false;*/
				}

				i++;
			}
		}


		return true;
	}



	//*****************************************************************************************
	//* Name            : isNumeric
	//* Description     : check if string is a number
	//* CREATED RELEASE : 1410
	//* LAST MODIFIED IN :
	//* Author          : Dina
	//* Input Params    : String - for verify if number
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}


	//*****************************************************************************************
	//* Name            : ClearItemsOfPersistantCart
	//* Description     : empty cart summary
	//* CREATED RELEASE : 1410
	//* LAST MODIFIED IN : Feb-17-2015 - Kapish Added For Mobile
	//* LAST MODIFIED IN : Dec-06-2016 - Hemant T(removed unwanted code(Optimize))
	//* Author          : Dina & Abhigyan
	//* Input Params    : 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean ClearItemsOfPersistantCart()
	{
		long startTime=System.currentTimeMillis();
		// Desktops
		String numberOfItemsInCart="xpath:=//*[contains(@id,'minicart-count')]";
		String lnkMyWirelessCart = "xpath:=//*[contains(@id,'minicart-link')]";
		String btnContinueShopping = "xpath:=//*[contains(text(),'Continue shopping')]";
		String webElmtPageHeader = "id:=cart";
		String lnkEmptyCart = "xpath:=//button[contains(.,'Empty cart')]";//a[contains(.,'Empty cart')]";//"Linktext:=Empty cart";
		String webElmtEmtyCartModal = "cssselector:=*[title='emptyCart'] h1";
		String btnContinue = "xpath:=//*[@id='empty-cart-interstitial']//*[@id='continue-btn']";
		String chatPopUp    = "xpath:=//a[contains(.,'No Thanks')]";
		boolean flag		=false;		                                            
		WebElement weWirelessCart = fCommonGetWebElement(numberOfItemsInCart, "No of persisted items in cart");
		String webElmtForCartSummaryHeader 	= "xpath:=//h1[contains(@class,'heading-page commonTitle')]";
		String webElmtForOldCartSummaryHeader 	= "xpath:=//*[contains(@class,'page-heading')]";
		if (weWirelessCart == null)
		{
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "CommonFunctions - ClearItemsOfPersistantCart", "My Wireless cart should exist", "My wireless cart doesn't exist", "Done");
			return false;
		}
		// chat popup click on 'No thanks'
		/*		if(fCommonCheckObjectExistance(chatPopUp) == true){
			fCommonClick(chatPopUp, "chatPopUp ");
		}*/

		if (!weWirelessCart.getText().equals(""))
		{
			if(driver.getCurrentUrl().contains("cellphones") || driver.getCurrentUrl().contains("devices"))
			{
				flag=true;
			}
			if(fCommonJavascriptClick(lnkMyWirelessCart, "My Wireless Cart")==false){
				return false;
			}
			fCommonValidateDynamicPageDisplayed("","");
			String strDynamicTitles = "Order Summary ## Resumen de pedidos ## Shopping Cart ## Wireless ## M�vil ## Cart Summary ## My Cart ## My cart ## Order summary";
			//added cartsummary condition by suraj for old and new checkout
			if(driver.getCurrentUrl().contains("cartsummary")){
				if (fCommonValidateDynamicPageDisplayed(webElmtForOldCartSummaryHeader,strDynamicTitles) == null){
					Reporter.fnWriteToHtmlOutput("CommonFunctions - ClearItemsOfPersistantCart", "Page Title should be display", "Page Title is not displayed", "Fail");
					return false;
				}
				if (fCommonJavascriptClick("xpath:=//a[contains(text(),'Empty cart')]","lnkEmptyCart") == false){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					return false;
				}
				fCommonValidateDynamicPageDisplayed("","");
				if (fCommonClick(fcommonRetrieveVisibleElementOnPage("xpath:=//a[@id='continue-btn']//*[contains(@class,'blueButton')]", "Continue Empty Cart"),"Continue Empty Cart") == false){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					return false;
				}
			}else{
				if (fCommonValidateDynamicPageDisplayed(webElmtForCartSummaryHeader,strDynamicTitles) == null){
					Reporter.fnWriteToHtmlOutput("CommonFunctions - ClearItemsOfPersistantCart", "Page Title should be display", "Page Title is not displayed", "Fail");
					return false;
				}
				if (fCommonJavascriptClick(lnkEmptyCart,"lnkEmptyCart") == false){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					return false;
				}
				fCommonValidateDynamicPageDisplayed("","");
				//            if (fCommonValidateDynamicPageDisplayed(webElmtEmtyCartModal,"Empty Cart") == null){
				//                // Try clicking once more if the page does not advance to the Empty Cart page (it seems to require two clicks now)...
				//                if (fCommonClick(lnkEmptyCart,"lnkEmptyCart") == false){
				//                    System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				//                    return false;
				//                }
				//                if (fCommonValidateDynamicPageDisplayed(webElmtEmtyCartModal,"Empty Cart") == null)
				//                {
				//                    System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				//                    Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "CommonFunctions - ClearItemsOfPersistantCart", "Empty Cart modal should be displayed", "Empty Cart modal is not displayed", "Fail");
				//                    return false;
				//                }
				//            }
				if (fCommonClick("xpath:=//div[contains(@class,'empty-cart')]//button[text()='Empty cart']","Empty Cart") == false){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					return false;
				}
			}			
			if(flag){
				fCommonSync(10000);
				fCommonValidateDynamicPageDisplayed("","");
				driver.navigate().back();
				/*if (fCommonCheckObjectExistance(webElmtPageHeader) == false){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "CommonFunctions - ClearItemsOfPersistantCart", "Page Title should be display", "Page Title is not displayed", "Fail");
					return false;
				}
				if (fCommonClick(btnContinueShopping,"btnContinueShopping") == false){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					return false;
				}*/
			}


		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return true;
	}

	//*****************************************************************************************
	//* Name            : ClearItemsOfPersistantCartMobile
	//* Description     : empty cart summary
	//* CREATED RELEASE : 1410
	//* LAST MODIFIED IN : Feb-17-2015 - Kapish Added For Mobile
	//* Author          : Dina & Abhigyan
	//* Input Params    : 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean ClearItemsOfPersistantCartMobile()
	{
		long startTime=System.currentTimeMillis();

		// Mobile
		String mobileCartCount      = "cssselector:=.cartCount";
		String mobileCartIcon       = "xpath:=//*[@class='iconCart ui-link']";//*[@id='mlcConfig']//a[contains(.,'Cart')]";
		String removeLine1          = "xpath:=//a[contains(.,'remove line 1')]";
		String okMobileModal        = "xpath:=//span[contains(.,'OK')]/*";
		String startShoppingMobile  = "xpath:=//a[contains(.,'Start shopping')]/*";
		String phonesTabletsDevicesMobile   = "xpath:=//a[contains(.,'Add a new device to my account')]";

		WebElement webMobileCartCount = fCommonGetObject(mobileCartCount, "Cart Count");
		if(webMobileCartCount == null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return false;
		}

		if(!webMobileCartCount.getText().equals("")){
			if(fCommonClick(mobileCartIcon, "Cart Icon") == false){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				return false;
			}

			fCommonValidateDynamicPageDisplayed(mobileCartIcon,"");

			while(fCommonGuiIsDisplayed(removeLine1,"removeLine1") == true){
				if(fCommonJavascriptClick(removeLine1, "Remove Line 1") == false){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					return false;
				}

				if(fCommonJavascriptClick(okMobileModal, "OK on Modal") == false){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					return false;
				}
			}

			fCommonValidateDynamicPageDisplayed(startShoppingMobile, "");

			if(fCommonJavascriptClick(startShoppingMobile, "Start Shopping") == false){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				return false;
			}

			fCommonValidateDynamicPageDisplayed(phonesTabletsDevicesMobile, "");

			if(fCommonJavascriptClick(phonesTabletsDevicesMobile, "Phones Tablets Devices Mobile") == false){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				return false;
			}
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "ClearItemsOfPersistantCart - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return true;
	}

	public void fGuiSetUpCookiesRegion()
	{
		long startTime=System.currentTimeMillis();
		String sCookiesRegion = Environment.get("SET_COOKIES_REGION");
		String sCookiesRegionECMS = Environment.get("SET_ECMS_COOKIES_REGION");
		String sCookiesRegioneCareeSupport = Environment.get("SET_ECARE_ESUPPORT_COOKIES_REGION");
		String sCookiesRegionStandalone= Environment.get("SET_STANDALONE_COOKIES_REGION");
		if (sCookiesRegion != null)
			//if (!sCookiesRegion.contains(""))
		{
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fGuiSetUpCookiesRegion - Setting cookie region to  - "+Environment.get("SET_COOKIES_REGION"));
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "CommonFunctions - fGuiSetUpCookiesRegion", "Setting cookie region","Setting cookie region to -  "+Environment.get("SET_COOKIES_REGION"), "Done");

			if (!sCookiesRegion.equals(""))
			{
				if (!driverType.contains("IE"))
				{
					driver.get("http://iphone5s:iphone5s@www.att.com/att/gtm/");
				}
				else
				{
					driver.get("www.att.com/att/gtm/");
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {          
					e.printStackTrace();
				}

				try//added By Dina for IE - authentication popup is comming and causing failure in flow
				{
					driver.findElement(By.id("remove-all")).click();
				}
				catch(UnhandledAlertException e)
				{
					fCommonHandleAlert("dismiss");
					driver.findElement(By.id("remove-all")).click();
				}

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {          
					e.printStackTrace();
				}

				WebElement webElement = driver.findElement(By.id("wireless"));
				try
				{
					if(driverType.contains("ANDROID") || driverType.contains("IOS")){
						((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElement);
						//driver.findElement(By.id("wireless")).click();
					}
					else if (!driverType.contains("IE"))
					{
						((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElement);
					}
					else
					{
						driver.findElement(By.id("wireless")).click();
					}
				}
				catch(UnhandledAlertException e)
				{
					fCommonHandleAlert("dismiss");
					webElement = driver.findElement(By.id("wireless"));
					driver.findElement(By.id("wireless")).click();
				}

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {          
					e.printStackTrace();
				}


				try
				{
					if(driverType.contains("ANDROID"))
					{
						driver.findElement(By.xpath("//*[@id='menu']/a[contains(text(),'" + sCookiesRegion + "')]")).click();
					}else{
						driver.findElement(By.xpath("//*[@id='menu']/a[contains(text(),'" + sCookiesRegion + "')]")).click();
					}
				}
				catch(UnhandledAlertException e)
				{
					fCommonHandleAlert("dismiss");
					driver.findElement(By.xpath("//*[@id='menu']/a[contains(text(),'" + sCookiesRegion + "')]")).click();

				}


				///////////////////////////////////////////////
				////////////---On request of ENV team----------/////////////
				///////////////////////////////////////////////
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				try
				{

					WebElement webElementUpgrade = driver.findElement(By.id("wireless"));
					if(driverType.contains("ANDROID") || driverType.contains("IOS")){
						((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElementUpgrade);
						//driver.findElement(By.id("wireless")).click();
					}
					else if (!driverType.contains("IE"))
					{
						((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElementUpgrade);
					}
					else
					{
						driver.findElement(By.id("wireless")).click();
					}
				}
				catch(UnhandledAlertException e)
				{
					fCommonHandleAlert("dismiss");
					driver.findElement(By.id("wireless")).click();
				}


				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {          
					e.printStackTrace();
				}

				try
				{
					if(driverType.contains("ANDROID")){
						driver.findElement(By.xpath("//*[@id='menu']/a[contains(text(),'" + sCookiesRegion + "')]")).click();
					}else{
						driver.findElement(By.xpath("//*[@id='menu']/a[contains(text(),'" + sCookiesRegion + "')]")).click();
					}
				}
				catch(UnhandledAlertException e)
				{
					fCommonHandleAlert("dismiss");
					driver.findElement(By.xpath("//*[@id='menu']/a[contains(text(),'" + sCookiesRegion + "')]")).click();
				}


				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				if(!sCookiesRegioneCareeSupport.contains("")){
					try
					{
						WebElement webElementSupport = driver.findElement(By.id("esupport"));
						if(driverType.contains("ANDROID") || driverType.contains("IOS")){
							//driver.findElement(By.id("wireless")).click();
							((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElementSupport);
						}
						else if (!driverType.contains("IE"))
						{
							((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElementSupport);
						}
						else
						{
							driver.findElement(By.id("esupport")).click();
						}
					}	
					catch(UnhandledAlertException e)
					{
						fCommonHandleAlert("dismiss");
						driver.findElement(By.id("esupport")).click();
					}
				}

				if(!sCookiesRegionStandalone.contains("")){
					try
					{
						WebElement webElementStandalone = driver.findElement(By.id("standalone"));
						if(driverType.contains("ANDROID") || driverType.contains("IOS")){
							//driver.findElement(By.id("wireless")).click();
							((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElementStandalone);
						}
						else if (!driverType.contains("IE"))
						{
							((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElementStandalone);
						}
						else
						{
							driver.findElement(By.id("standalone")).click();
						}
					}
					catch(UnhandledAlertException e)
					{
						fCommonHandleAlert("dismiss");
						driver.findElement(By.id("standalone")).click();
					}
				}

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {          
					e.printStackTrace();
				}

				try
				{
					if(driverType.contains("ANDROID")){
						driver.findElement(By.xpath("//*[@id='menu']/a[contains(text(),'" + sCookiesRegion + "')]")).click();
					}
//					else{
//						driver.findElement(By.xpath("//*[@id='menu']/a[contains(text(),'" + sCookiesRegion + "')]")).click();
					//}
				}
				catch(UnhandledAlertException e)
				{
					fCommonHandleAlert("dismiss");
					driver.findElement(By.xpath("//*[@id='menu']/a[contains(text(),'" + sCookiesRegion + "')]")).click();
				}

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {          
					e.printStackTrace();
				}					

				// for ECMS
				// Added by AMIT KUMAR // to set the ECMS data center 
				if(!sCookiesRegionECMS.contains("")){
					try
					{
						WebElement webElementecms = driver.findElement(By.id("ecms"));
						if(driverType.contains("ANDROID") || driverType.contains("IOS")){
							((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElementecms);
						}
						else if (!driverType.contains("IE"))
						{
							((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElementecms);
						}
						else
						{
							driver.findElement(By.id("ecms")).click();
						}
					}
					catch(UnhandledAlertException e)
					{
						fCommonHandleAlert("dismiss");
						driver.findElement(By.id("ecms")).click();
					}

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {          
						e.printStackTrace();
					}

					try
					{
						if(driverType.contains("ANDROID")){
							driver.findElement(By.xpath("//*[@id='menu']/a[contains(text(),'" + sCookiesRegionECMS + "')]")).click();
						}else{
							driver.findElement(By.xpath("//*[@id='menu']/a[contains(text(),'" + sCookiesRegionECMS + "')]")).click();
						}
					}
					catch(UnhandledAlertException e)
					{
						fCommonHandleAlert("dismiss");
						driver.findElement(By.xpath("//*[@id='menu']/a[contains(text(),'" + sCookiesRegionECMS + "')]")).click();
					}
				}else{
					System.out.println("ECMS no need to set");
				}

				///////////////////////////////////////////////
				////////////---On request of ENV team----------/////////////
				///////////////////////////////////////////////


				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {          
					e.printStackTrace();
				}

				//driver.findElement(By.xpath("//a[@id='allow-qa']")).click();
				WebElement weTmp=null;
				try
				{
					if(Environment.get("ENABLE_QA_ACCESS") != null){
						if(Environment.get("ENABLE_QA_ACCESS").toUpperCase().contains("Y")){
							weTmp = driver.findElement(By.id("allow-qa"));
							///////////////////////////////////////////////
							////////////---The below line not required for 1_28 release---------/////////////
							///////////////////////////////////////////////
							weTmp.click();
						}
					}
				}
				catch(UnhandledAlertException e)
				{
					fCommonHandleAlert("dismiss");
					if(Environment.get("ENABLE_QA_ACCESS") != null){
						if(Environment.get("ENABLE_QA_ACCESS").toUpperCase().contains("Y")){
							weTmp.click();
						}
					}
				}



				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {           
					e.printStackTrace();
				}
			}
		}
		

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fGuiSetUpCookiesRegion - return void -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
	}


	//*****************************************************************************************
	//* Name            : fCommonSwitchToFrame
	//* Description     : switch to given frame
	//* CREATED RELEASE : 1410
	//* LAST MODIFIED IN :
	//* Author          : Dina
	//* Input Params    :
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonSwitchToFrame(String objDesc, String objName)
	{
		long startTime=System.currentTimeMillis();

		WebElement weFrame = fCommonGetWebElement(objDesc, objName);
		if (weFrame == null)
		{
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSwitchToFrame - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "CommonFunctions - fCommonSwitchToFrame", "Frame should be found", "Frame " + objName + " is missing", "Fail");
			return false;
		}

		driver.switchTo().frame(weFrame);

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonSwitchToFrame - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return true;
	}


	//*****************************************************************************************
	//* Name            : HandlePrintPopup
	//* Description     : check print popup exist and close it
	//* CREATED RELEASE : 1410
	//* LAST MODIFIED IN :
	//* Author          : Dina
	//* Input Params    :
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public synchronized boolean HandlePrintPopup()
	{
		long startTime=System.currentTimeMillis();
		String sAuthFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\att\\framework\\";

		try{
			String sFile1 = "";
			File file;

			if (System.getProperty("os.arch").contains("86"))
			{
				sFile1 = sAuthFilePath + "PrintPopupHandle_32.exe";

			}
			else if (System.getProperty("os.arch").contains("64"))
			{
				sFile1 = sAuthFilePath + "PrintPopupHandle_64.exe";
			}

			file = new File(sFile1);


			if (file.exists())
			{
				Runtime.getRuntime().exec(sFile1);
			}

		}
		catch(Exception e ){}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "HandlePrintPopup - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "CommonFunctions - HandlePrintPopup", "Print Popup Handle","Print popup was handled successfully", "Pass");
		return true;
	}

	//*****************************************************************************************
	//* Name            : fCommonIsDisplayed
	//* Description     : Javascript method to verify Element Present on the webpage 
	//* CREATED RELEASE : 1410
	//* LAST MODIFIED IN :
	//* Author          : Kapish Kumar
	//* Input Params    : cssSelector
	//* Modified date   : 16-Feb-2015 - By Dina
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonIsDisplayed(String strCSSSelector)
	{
		long startTime=System.currentTimeMillis();

		//Delimiters
		String[] delimiters = new String[] {":="};
		String[] arrFindByValues = strCSSSelector.split(delimiters[0]);
		//Get Findby and Value
		String FindBy, strVal;
		if(arrFindByValues.length==2){
			FindBy = arrFindByValues[0];
			strVal = arrFindByValues[1];            
		}
		else{
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed", "objDesc should be valid: cssselector:= <some selector>","objDesc is not valid: " + strCSSSelector, "Fail");
			return false;
		}

		if (!FindBy.equalsIgnoreCase("cssselector"))
		{
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed", "This method is working only with cssselector","please provide cssselector", "Fail");
			return false;
		}

		//      String strJs_OLD1 =     "Element.prototype.isVisible = function() {" +
		//              "'use strict';" +
		//              "function _isVisible(el, t, r, b, l, w, h) {" +
		//              "var p = el.parentNode,VISIBLE_PADDING = 2;" +
		//              "if ( !_elementInDocument(el) ) {" +
		//              "return false;}/**-- Return true for document node*/" +
		//              "if ( 9 === p.nodeType ) {" +
		//              "return true;}/**-- Return false if our element is invisible*/" +
		//              "if ('0' === _getStyle(el, 'opacity') ||'none' === _getStyle(el, 'display') ||'hidden' === _getStyle(el, 'visibility') ||'collapse' === _getStyle(el, 'visibility')) {" +
		//              "return false;}" +
		//              "if ('undefined' === typeof(t) ||'undefined' === typeof(r) ||'undefined' === typeof(b) ||'undefined' === typeof(l) ||'undefined' === typeof(w) ||'undefined' === typeof(h)) {" +
		//              "t = el.offsetTop;" +
		//              "l = el.offsetLeft;" +
		//              "b = t + el.offsetHeight;" +
		//              "r = l + el.offsetWidth;" +
		//              "w = el.offsetWidth;" +
		//              "h = el.offsetHeight;}/**-- If we have a parent, let's continue:*/" +
		//              "if ( p ) {/**-- Check if the parent can hide its children.*/" +
		//              "if ( ('hidden' === _getStyle(p, 'overflow') || 'scroll' === _getStyle(p, 'overflow')) ) {/**-- Only check if the offset is different for the parent*/" +
		//              "if (/**-- If the target element is to the right of the parent elm*/l + VISIBLE_PADDING > p.offsetWidth + p.scrollLeft ||/**-- If the target element is to the left of the parent elm*/l + w - VISIBLE_PADDING < p.scrollLeft ||/**-- If the target element is under the parent elm*/t + VISIBLE_PADDING > p.offsetHeight + p.scrollTop ||/**-- If the target element is above the parent elm*/t + h - VISIBLE_PADDING < p.scrollTop) {/**-- Our target element is out of bounds:*/" +
		//              "return false;}}/**-- Add the offset parent's left/top coords to our element's offset:*/" +
		//              "if ( el.offsetParent === p ) {" +
		//              "l += p.offsetLeft;" +
		//              "t += p.offsetTop;}/**-- Let's recursively check upwards:*/" +
		//              "return _isVisible(p, t, r, b, l, w, h);}" +
		//              "return true;}" +
		//              "/**-- Cross browser method to get style properties:*/" +
		//              "function _getStyle(el, property) {" +
		//              "if ( window.getComputedStyle ) {" +
		//              "return document.defaultView.getComputedStyle(el,null)[property];}" +
		//              "if ( el.currentStyle ) {" +
		//              "return el.currentStyle[property];}}" +
		//              "function _elementInDocument(element) {" +
		//              "while (element = element.parentNode) {" +
		//              "if (element == document) {" +
		//              "return true;}}" +
		//              "return false;}" +
		//              "return _isVisible(this);};"; 
		//
		//      String strJs_OLD2 = "Element.prototype.isVisible = function isScrolledIntoView(elem) {var elemTop = elem.getBoundingClientRect().top;var elemBottom = elem.getBoundingClientRect().bottom;var isVisible = (elemTop >= 0) && (elemBottom <= window.innerHeight);return isVisible;};";
		//      String strJs = "Element.prototype.isVisible = function isScrolledIntoView(elem) {var elemTop = elem.getBoundingClientRect().top;var elemBottom = elem.getBoundingClientRect().bottom;var isVisible = (elemTop >= 0) && (elemBottom <= window.innerHeight);return isVisible;};";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String strJSResult = null;
		String strJSResult2 = "";
		//      int a = 0;
		try{
			//strJSResult = js.executeScript(strJs+" "+"var elem = document.querySelector(\""+ strVal +"\");"+"elem.focus();"+"return elem.isVisible(elem);").toString();
			strJSResult2 = js.executeScript("var elem = document.querySelector(\""+ strVal +"\").clientHeight;return elem;").toString();
			strJSResult = js.executeScript("return $(\""+ strVal +"\").is(':visible');").toString();
		}catch(Exception e1){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return false;
		}
		if(strJSResult == "true" && Integer.parseInt(strJSResult2) > 0){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return true;
		}
		else
		{
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return false;
		}

	}

	//*****************************************************************************************
	//* Name            : fCommonIsDisplayed
	//* Description     : Method to verify object is display by guven timeout 
	//* CREATED RELEASE : 
	//* LAST MODIFIED IN :
	//* Author          : Zachi Gahari
	//* Input Params    : Object description, time out (sec)
	//* Modified date   : 26-Now-2017
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonIsDisplayed(String webElmtProp, String elmntName, int timeOut){
		int tmpWait = WAIT_FOR_ELEMENT;
		int i = 0;
		WAIT_FOR_ELEMENT = timeOut;
		WebElement webElement = null;
		
		long startTime = System.currentTimeMillis();
		webElement = fCommonGetObject(webElmtProp, elmntName);
		long waitForObjectTime = Math.round(((System.currentTimeMillis()-startTime))/1000);
		System.out.println("Wait for " + waitForObjectTime + " seconds");
		WAIT_FOR_ELEMENT = tmpWait;  // Restore default value
		
		
		try {
			if(webElement!=null){
				// add by holey 23-072020
				JavascriptExecutor js = ((JavascriptExecutor) driver);
				if(!webElement.isDisplayed()){
					try {
						js.executeScript("arguments[0].scrollIntoView(true);",webElement);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				i=(int) waitForObjectTime;
				do{
					if(webElement.isDisplayed()){
						try {
							JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
							jsExecutor.executeScript("arguments[0].scrollIntoView(false);", webElement);
							if(webElement.getLocation().getY()>500){
								jsExecutor.executeScript("window.scrollBy(0, 100)");
							}
						} catch (Exception e) {}
						System.out.println(">>>>>>>  " + elmntName + " displayed within " + timeOut + " seconds");
						Reporter.fnWriteToHtmlOutput("Check if element: "+elmntName+" displayed", "Element: "+elmntName+" is displayed", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " displayed within " + timeOut + " seconds", "Pass");
						return true;
					}else{
						fCommonSync(1000);
						i++;
					}
				}while (i<=timeOut);
				System.out.println(">>>>>>>  " + elmntName + " doesn't display after " + timeOut + " seconds");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed", "Check if object displayed", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " doesn't display after " + timeOut + " seconds", "Pass"); // Zachi - Report 'Pass' for getting the screenshot, can't report 'Fail' as sometimes it is expected
				Reporter.fnWriteToHtmlOutput("Check if element: "+elmntName +" displayed", "Element: "+elmntName+" is displayed", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " doesn't display after " + timeOut + " seconds", "Done"); // Zachi - Report 'Pass' for getting the screenshot, can't report 'Fail' as sometimes it is expected
			}else{
				System.out.println(">>>>>>>  " + elmntName + " doesn't exist (returned null) after " + timeOut + " seconds");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed", "Check if object displayed", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " doesn't exist (returned null) after " + timeOut + " seconds", "Pass"); // Zachi - Report 'Pass' for getting the screenshot, can't report 'Fail' as sometimes it is expected
				Reporter.fnWriteToHtmlOutput("Check if element: "+elmntName+" displayed", "Element: "+elmntName+" is displayed", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " doesn't exist (returned null) after " + timeOut + " seconds", "Pass"); // Zachi - Report 'Pass' for getting the screenshot, can't report 'Fail' as sometimes it is expected
			}
		} catch (StaleElementReferenceException e) {
		}

		return false;
	}
	
	//*****************************************************************************************
	//* Name            : fCommonIsDisplayed (overloaded)
	//* Description     : Method to verify object is display by guven timeout 
	//* CREATED RELEASE : 
	//* LAST MODIFIED IN :
	//* Author          : Zachi Gahari
	//* Input Params    : Object description, Object name, time out (sec), expected Y/N/M (Yes/No/Maybe)
	//* Modified date   : 26-Now-2017
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonIsDisplayed(String webElmtProp, String elmntName, int timeOut, String expected){
		int tmpWait = WAIT_FOR_ELEMENT;
		WAIT_FOR_ELEMENT = timeOut;
		WebElement webElement = null;
		
		fCommonSync(2000);
		long startTime = System.currentTimeMillis();
		webElement = fCommonGetObject(webElmtProp, elmntName);
		System.out.println("Wait for " + (System.currentTimeMillis()-startTime)+"ms");
		WAIT_FOR_ELEMENT = tmpWait;  // Restore default value

		if(webElement!=null){
			if(webElement.isDisplayed()==true){
				if(expected.toUpperCase().equals("Y")){
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed", "Check if object displayed", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " displayed within " + timeOut + " seconds", "Pass"); 
					return true;
				}else if(expected.toUpperCase().equals("N")){
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed", "Check if object displayed", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " displayed within " + timeOut + " seconds", "Fail"); 
					return false;
				}else{
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed", "Check if object displayed", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " displayed within " + timeOut + " seconds", "Done"); 
					return true;
				}
			}
		}
		
		if(expected.toUpperCase().equals("N")){
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed", "Check if object displayed", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " hasn't displayed", "Pass"); 
			return true;
		}else if(expected.toUpperCase().equals("Y")){
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed", "Check if object displayed", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " displayed", "Fail"); 
			return false;
		}else{
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonIsDisplayed", "Check if object displayed", elmntName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " hasn't displayed", "Done"); 
			return false;
		}
	}
	
	//*****************************************************************************************
	//* Name            : waitForPageFullyLoaded
	//* Description     : Javascript method to Wait for the Page to be fully loaded 
	//* CREATED RELEASE : 1410
	//* LAST MODIFIED IN :
	//* Author          : Kaiqi Tang
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 26, 2015
	//* Input Params    : driver
	//* Modified date   : 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean waitForPageFullyLoaded(WebDriver driver, long startTime, long endTime){

		long internalStartTime=System.currentTimeMillis();

		try{
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			boolean valReady = js.executeScript("return document.readyState").equals("complete");

			while(valReady != true){

				//check if it passed the gloabl timeout for page load
				if(System.currentTimeMillis() > endTime){
					//Reporter.fnWriteToHtmlOutput("WaitPageFullyLoaded", "Check page loaded","Page load still not completed", "Done");
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForPageFullyLoaded timeout reached : global : " +(endTime - startTime)+"ms");
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForPageFullyLoaded - return false - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForPageFullyLoaded", "Check if page document state is complete","Global timeout "+WAIT_FOR_PAGE+" seconds reached for page document state check", "Fail");
					return false;
				}

				sleep(WAIT_FOR_PAGE_POLLING_TIME);

				valReady = js.executeScript("return document.readyState").equals("complete");   
			} 
		}
		catch(Exception e)
		{
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForPageFullyLoaded exception occured : " + e.toString()); //Dina - exception is getting somtimes while using IE, wnat to catch it and not fail the flow
			fCommonHandleAlert("dismiss");
		}

		//Reporter.fnWriteToHtmlOutput("WaitPageFullyLoaded", "Check page loaded","Page load completed", "Done");
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForPageFullyLoaded - return true -  has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
		return true;
	}


	//*****************************************************************************************
	//* Name            : waitForPageFullyLoaded
	//* Description     : Overloaded - Javascript method to Wait for the Page to be fully loaded 
	//* CREATED RELEASE : 1410
	//* LAST MODIFIED IN :
	//* Author          : Kaiqi Tang
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 26, 2015
	//* Input Params    : driver
	//* Modified date   : 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean waitForPageFullyLoaded(WebDriver driver){

		long startTime=System.currentTimeMillis();
		long waitTime=60000; //60s for internal timeout

		try{
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			boolean valReady = js.executeScript("return document.readyState").equals("complete");

			while(valReady != true){

				//check if it passed the internal timeout
				if(System.currentTimeMillis() > waitTime){
					//Reporter.fnWriteToHtmlOutput("WaitPageFullyLoaded", "Check page loaded","Page load still not completed", "Done");
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForPageFullyLoaded timeout reached : internal : " +waitTime+"ms");
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForPageFullyLoaded - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					return false;
				}

				sleep(WAIT_FOR_PAGE_POLLING_TIME);

				valReady = js.executeScript("return document.readyState").equals("complete");   
			}  
		}
		catch(Exception e)
		{
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForPageFullyLoaded exception occured : " + e.toString()); //Dina - exception is getting somtimes while using IE, wnat to catch it and not fail the flow
		}

		//Reporter.fnWriteToHtmlOutput("WaitPageFullyLoaded", "Check page loaded","Page load completed", "Done");
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForPageFullyLoaded - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return true;

	}


	//*****************************************************************************************
	//* Name            : waitForAjaxCallsToComplete
	//* Description     : Javascript method to Wait for all ajax calls to be completed 
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1504
	//* Author          : Gavril Grigorean
	//* Input Params    : driver
	//* Modified date   : April 26, 2015; 4-May-2015 - By Dina
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************

	public boolean waitForAjaxCallsToComplete(WebDriver driver, long startTime, long endTime) {

		long internalStartTime=System.currentTimeMillis();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		try{ // Dina - adding Try Catch - to not fail on page where JQuery doesn't exist
			Object numberOfAjaxConnections = js.executeScript("return jQuery.active");
			Long n = (Long) numberOfAjaxConnections;

			while(n.longValue() != 0L){

				//check if it passed the gloabl timeout for page load
				if(System.currentTimeMillis() > endTime){

					System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForAjaxCallsToComplete timeout reached : global : " +(endTime - startTime)+"ms");
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForAjaxCallsToComplete - return n: false - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForAjaxCallsToComplete", "Check if there are still ajax calls active","Global timeout "+WAIT_FOR_PAGE+" seconds reached for ajax calls check", "Done");
					n.toString();
					return true;
				}

				sleep(WAIT_FOR_PAGE_POLLING_TIME);

				numberOfAjaxConnections = js.executeScript("return jQuery.active");

				n = (Long) numberOfAjaxConnections;  
			}
		}
		catch(Exception e)
		{
			if (e.getMessage().contains("jQuery is not defined") || e.getMessage().contains("JavaScript error") ||e.toString().contains("UnhandledAlertException"))//Dina - added additional exception for IE browser
			{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForAjaxCallsToComplete - return true -  No JQuery defined in the Page -  has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
				fCommonHandleAlert("dismiss");
				//return true;
			}
			else{
				e.printStackTrace();
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForAjaxCallsToComplete - return false -  has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
				return false;
			}
		}

		//Reporter.fnWriteToHtmlOutput("waitForAjaxCallsToComplete", "Check ajax calls","No ajax calls active", "Done");
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForAjaxCallsToComplete - return true -  has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
		return true;
	}


	//*****************************************************************************************
	//* Name            : timer
	//* Description     :
	//* CREATED RELEASE : 
	//* LAST MODIFIED IN :
	//* Author          : 
	//* Input Params    : 
	//* Return Values   : 
	//***************************************************************************************** 
	public long timer(String method, long t1){
		Date date = new Date();
		long t2 = date.getTime();
		System.out.println(method + "   :" + (t2 - t1) + "ms");
		return t2;
	}



	//*****************************************************************************************
	//* Name            : fCommonHighlightElement
	//* Description     : Method to highlight an object
	//* CREATED RELEASE : 1210
	//* LAST MODIFIED IN :
	//* Author          : Naveena Basetty
	//* Input Params    : WebElement 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************         
	public boolean fCommonHighlightElement(WebElement element){
		long startTime=System.currentTimeMillis();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String color =  "Aquamarine";
		js.executeScript("arguments[0].style.backgroundColor = '"+color+"'",  element);
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonHighlightElement - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return true;
	}



	//*****************************************************************************************
	//* Name            : fCommonCreateIRUurl
	//* Description     : Method to create IRU url according to given account id and Fan number
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :
	//* Author          : Dina Dodin 
	//* Return Values   : Boolean - Depending on the success
	//***************************************************************************************** 
	public boolean fCommonCreateIRUurl()
	{
		long startTime=System.currentTimeMillis();

		String strUrl = "";

		String webElmtTable = "xpath:=//table";
		String webElmtEncryptAccount = "xpath:=//li[contains(text(),'Encrypted Account')]";
		String webElmtTitle = "xpath:=//h1";
		//open All FANs url
		if (Environment.get("ENV_CODE").contains("FS"))
		{
			driver.manage().deleteAllCookies();
			fGuiSetUpCookiesRegion();
			driver.get("http://finalstage.att.com/shoptest/iru/AllFans.jsp");
		}
		else
		{
			String temp = "http://"+ Environment.get("ENV_CODE").toLowerCase() + ".stage.att.com/shoptest/iru/AllFans.jsp";
			driver.get(temp);
		}

		try{

			//search for the Account Group id Link
			if (fCommonValidateDynamicPageDisplayed(webElmtTitle,"Retrieve ALL FANs") == null){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCreateIRUurl - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");

				if (Environment.get("ENV_CODE").contains("FS")) //20-05-2015- Dina - sometimes the AllFans is not available 
				{
					strUrl = "http://finalstage.att.com/shopservlets/irulanding?account_group="+ Environment.get("IRU_TOKEN") +"&src=PREMIER";
				}
				else
				{
					strUrl = "http://" + Environment.get("ENV_CODE").toLowerCase() + ".stage.att.com/shopservlets/irulanding?account_group="+ Environment.get("IRU_TOKEN") +"&src=PREMIER";
				}
				Environment.put("NC_IRU", strUrl);
				return true;
			}
			WebElement weParent = fCommonGetWebElement(webElmtTable,"webElmtTable");
			List<WebElement> weRows = fCommonGetChildWebElementsList(weParent, "xpath:=//tr", "rows");

			int i;

			for (i=1; i<weRows.size(); i++)
			{
				List<WebElement> weColumns = fCommonGetChildWebElementsList(weRows.get(i), "xpath:=//td", "Columns");

				if(weColumns.get(0).getText().trim().equals(Environment.get("FAN_NUMBER")) && weColumns.get(2).getText().trim().equals(Environment.get("ACCOUNT_ID")))
				{
					int iCount = i+1;
					if (fCommonClick("xpath:=//tr[" + iCount + "]/td/a[contains(text(),'"+ Environment.get("ACCOUNT_ID") +"')]","") == false){
						System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCreateIRUurl - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
						return false;
					}
					break;
				}

			}

			// String strUrl = "";
			if (weRows.size() == 1 && Environment.get("ENV_CODE").contains("FS")) //20-05-2015- Dina - sometimes the AllFans is not available for FS
			{
				strUrl = "http://finalstage.att.com/shopservlets/irulanding?account_group="+ Environment.get("IRU_TOKEN") +"&src=PREMIER";
			}
			else
			{
				fCommonSwitchToWindow(1);

				String strEncryptedAccount = fCommonGetWebElement(webElmtEncryptAccount,"webElmtEncryptAccount").getText().split(":")[1].trim();

				if (Environment.get("ENV_CODE").contains("FS"))
				{
					strUrl = "http://finalstage.att.com/shopservlets/irulanding?account_group="+ strEncryptedAccount +"&src=PREMIER";
				}
				else
				{
					strUrl = "http://" + Environment.get("ENV_CODE").toLowerCase() + ".stage.att.com/shopservlets/irulanding?account_group="+ strEncryptedAccount +"&src=PREMIER";
				}

				driver.close();
				fCommonSwitchToWindow(0);
			}

		}
		catch(Exception e)
		{

			if (Environment.get("ENV_CODE").contains("FS")) //20-05-2015- Dina - sometimes the AllFans is not available for FS
			{
				strUrl = "http://finalstage.att.com/shopservlets/irulanding?account_group="+ Environment.get("IRU_TOKEN") +"&src=PREMIER";
			}
			else
			{
				strUrl = "http://" + Environment.get("ENV_CODE").toLowerCase() + ".stage.att.com/shopservlets/irulanding?account_group="+ Environment.get("IRU_TOKEN") +"&src=PREMIER";
			}
		}

		Environment.put("NC_IRU", strUrl);


		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCreateIRUurl - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return true;
	}


	//*****************************************************************************************
	//* Name            : waitForUrlSync
	//* Description     : Method to check if url has been changed
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1504
	//* Author          : Kaiqi tang
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Return Values   : Boolean - Depending on the success
	//***************************************************************************************** 
	public boolean waitForUrlSync(WebDriver driver, String URL_token, long startTime, long endTime)
	{

		long internalStartTime = System.currentTimeMillis();

		//loop for veriying URL 
		while(true){

			//check if it passed the gloabl timeout for page load
			if((System.currentTimeMillis() > endTime)) {
				System.err.println(fGetThreadClassNameAndMethod() + " -- " + "waitForUrlSync timeout reached : global : " + (endTime - startTime) + "ms");
				System.err.println(fGetThreadClassNameAndMethod() + " -- " + "waitForUrlSync - expected url: "+ URL_token +" - actual url: " + driver.getCurrentUrl());
				System.err.println(fGetThreadClassNameAndMethod() + " -- " + "waitForUrlSync - return false - has delayed with --> " + (System.currentTimeMillis() - internalStartTime) + "ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForUrlSync", "Check if Actual URL: "+driver.getCurrentUrl()+"  contains Expected URL: " +URL_token,"Global timeout "+WAIT_FOR_PAGE+" seconds reached for URL check", "Fail");
				return false;
			}

			// check if url is reached
			if (driver.getCurrentUrl().matches(".*(" + URL_token + ").*")) {
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForUrlSync - return true -  has delayed with --> " + (System.currentTimeMillis() - internalStartTime) + "ms");
				return true;
			}

			// check for popups, just in case but with 0 timeout since they should be already displayed
			handlePopUps(0, 0);

			// no popups, still waiting for the url to be redirected 
			sleep(WAIT_FOR_PAGE_POLLING_TIME);
		}
	}


	//*****************************************************************************************
	//* Name            : waitForNodesSyncByCssSelector
	//* Description     : Method to check if all nodes has been loaded 
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1504
	//* Author          : Kaiqi tang 
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : April 24, 2015
	//* Return Values   : Boolean - Depending on the success
	//***************************************************************************************** 
	public boolean waitForNodesSyncByCssSelector(WebDriver driver, long startTime, long endTime) {

		long internalStartTime = System.currentTimeMillis();

		int previous = 0;
		int current = driver.findElements(By.cssSelector("*")).size(); //initialize the number of nodes
		System.out.println(fGetThreadClassNameAndMethod() + " -- driver.findElements(By.cssSelector(*)).size()="+current+" - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");

		long timeSliceMs = 500L; // check every 1/2 second
		do {

			//check if it passed the gloabl timeout for page load
			if(System.currentTimeMillis() > endTime){
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForNodesSyncByCssSelector", "Check node sync","Global timeout for page load reached", "Done");
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForNodesSyncByCssSelector timeout reached : global : " +(endTime - startTime)+"ms");
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForNodesSyncByCssSelector - return false - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
				return false;
			}

			previous = current; //pass the number of nodes from last iteration

			sleep(timeSliceMs);

			long internalStartTime2 = System.currentTimeMillis();
			current = driver.findElements(By.cssSelector("*")).size();
			System.out.println(fGetThreadClassNameAndMethod() + " -- driver.findElements(By.cssSelector(*)).size()="+current+" - has delayed with --> "+(System.currentTimeMillis()-internalStartTime2)+"ms");

		} while(current != previous); // as long as the number of nodes changed 

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForNodesSyncByCssSelector - return true -  has delayed with --> " +(System.currentTimeMillis() - internalStartTime) + "ms");
		return true;
	}

	//*****************************************************************************************
	//* Name            : waitForNodesSyncByPageSource
	//* Description     : Method to check if all nodes has been loaded - using page source instead of xpath or cssselector, getting all nodes as WebElements is too slow 
	//* CREATED RELEASE : 1508
	//* LAST MODIFIED IN:
	//* Author          : Stefan Andrei 
	//* Updated by      : 
	//* Updated Date    : 
	//* Return Values   : Boolean - Depending on the success
	//* 
	//* Replacing CommonFunctions.waitForNodesSyncByCssSelector with waitForNodesSyncByPageSource which checks 
	//* if the number of nodes is stable by comparing the page source size between 2 points in time
	//* 
	//* The reason is that taking all nodes by cssselector or xpath as webelement array is very slow, 
	//* taking only page source as string is much faster, still slow though because it's a remote call to webdriver
	//* 
	//* Best would be to be able to get only the page source size or the number of nodes from the webdriver 
	//* (to avoid the heavy computation and remote big data remote transfer) but I didn't find any way to do it  like this
	//* 
	//* NOTE: waitForNodesSyncByPageSource is approximatively  3 times faster than 
	//*       waitForNodesSyncByCssSelector and waitForNodesSyncByXpath which translates in at least 30s less per test
	//***************************************************************************************** 
	public boolean waitForNodesSyncByPageSource(WebDriver driver, long startTime, long endTime) {

		long internalStartTime = System.currentTimeMillis(), internalStartTime2;

		int previousPageSourceSize = 0;
		try
		{
			int currentPageSourceSize = driver.getPageSource().length(); //initialize the page source size
			System.out.println(fGetThreadClassNameAndMethod() + " -- driver.getPageSource().length() = " + currentPageSourceSize + " - has delayed with --> " + (System.currentTimeMillis() - internalStartTime) + "ms");

			int timeSliceMs = 500; // check every 1/2 second
			do {
				//check if it passed the gloabl timeout for page load
				if(System.currentTimeMillis() > endTime){               
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForNodesSyncByPageSource timeout reached : global : " + (endTime - startTime) + "ms");
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForNodesSyncByPageSource - return false - has delayed with --> "+(System.currentTimeMillis() - internalStartTime) + "ms");
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForNodesSyncByPageSource", "Check node sync", "Global timeout "+WAIT_FOR_PAGE+" seconds reached for nodes sync", "Fail");
					return false;
				}

				previousPageSourceSize = currentPageSourceSize; //pass the page source size from last iteration

				// wait until next check
				sleep(timeSliceMs);

				internalStartTime2 = System.currentTimeMillis();
				currentPageSourceSize = driver.getPageSource().length();
				System.out.println(fGetThreadClassNameAndMethod() + " -- driver.getPageSource().length() = " + currentPageSourceSize + " - has delayed with --> " + (System.currentTimeMillis() - internalStartTime2) + "ms");

			} while (currentPageSourceSize != previousPageSourceSize); // as long as the number page source changed 

		}
		catch(Exception e)
		{
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "exception occured " + e.toString());//Dina - exception happens in IE want to continue and not fail TC
			fCommonHandleAlert("dismiss");
		}
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForNodesSyncByPageSource - return true -  has delayed with --> " +(System.currentTimeMillis() - internalStartTime) + "ms");
		return true;
	}


	//*****************************************************************************************
	//* Name            : waitForPageIsLoadingProgress
	//* Description     : Method to check if the page is still loading (the loading progress)
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1504
	//* Author          : Gavril Grigorean 
	//* Updated by      : 
	//* Updated Date    : April 28, 2015
	//* Return Values   : Boolean - Depending on the success
	//***************************************************************************************** 
	public boolean waitForPageIsLoadingProgress(WebDriver driver, long startTime, long endTime) {

		long internalStartTime = System.currentTimeMillis();

		String str="<div class=\"cover\" ng-show=\"isLoading\"></div>";
		String strDarkScreen = "<div id=\"darkenScreenObject\"";
		String strSpinnerShade ="<div ng-show=\"activeRequests\" class=\"spinner-shade\">";
		String strSpinnerLoading = "<div class=\"spinner\" ng-show=\"isLoading\">";
		//String strSpinnerIsLoading = "//pageload-indicator[not(@class ='ng-hide')]//i[@class='icon-spinner']";

		fCommonHandleAlert("dismiss");
		while(driver.getPageSource().contains(str) || driver.getPageSource().contains(strDarkScreen) 
				|| driver.getPageSource().contains(strSpinnerShade) || driver.getPageSource().contains(strSpinnerLoading)) {

			//check if it passed the gloabl timeout for page load
			if(System.currentTimeMillis() > endTime){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForPageIsLoadingProgress timeout reached : global : " +(endTime - startTime)+"ms");
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForPageIsLoadingProgress - return false - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForPageIsLoadingProgress", "Check if Loading in progress circles still displayed","Global timeout "+WAIT_FOR_PAGE+" seconds reached for loading in progress check", "Fail");
				return false;
			}

			try{
				// check for popups, just in case but with 0 timeout since they should be already displayed
				handlePopUps(0, 0);

				// no popups, page is still actively loading
				sleep(WAIT_FOR_PAGE_POLLING_TIME);
			}catch(Exception e){
				fCommonHandleAlert("dismiss");
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForPageIsLoadingProgress exception occured : " + e.toString());
			}
		} 

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForPageIsLoadingProgress - return true -  has delayed with --> " +(System.currentTimeMillis() - internalStartTime) + "ms");
		//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForPageIsLoadingProgress", "Check page is not still loading","waitForPageIsNotLoading completed", "Done");
		return true;
	}

	//*****************************************************************************************
	//* Name            : fGetThreadClassNameAndMethod
	//* Description     : get the class name and method
	//* CREATED RELEASE : 1505
	//* LAST MODIFIED IN :
	//* Author          : Gavril Grigorean
	//* Input Params    : none
	//* Return Values   : String
	//*****************************************************************************************  
	public String fGetThreadClassNameAndMethod()
	{
		return getThreadId() + " -- " + driverType + " -- " + 
				Thread.currentThread().getStackTrace()[3].getClassName().replaceAll("^.*\\.", "") + "." + 
				Thread.currentThread().getStackTrace()[3].getMethodName();
	}


	//*****************************************************************************************
	//* Name            : checkPageContainsText
	//* Description     : check if the page element check is correct
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :
	//* Author          : Gavril Grigorean
	//* Input Params    : none
	//* Return Values   : String
	//*****************************************************************************************  

	public String checkPageContainsText(String webElmtLocator,String webElmtTextOrUrl,long startTime, long endTime){

		//-    if (webElmtProp.isEmpty()) – will be checked if the url passed in webElmtTextOrUrl is contained in the current url
		//            o      e.g. fCommonValidateDynamicPageDisplayed("","internet.html")
		//-    if (DiffDynamicPageTitles.isEmpty()) - it will look for the web element  webElmtLocator  and check if exist
		//            o      e.g. fCommonValidateDynamicPageDisplayed("id:=clearFilterTokens","")
		//else – will be checked if the text of web element webElmtLocator contains the text of webElmtTextOrUrl 
		//            o  e.g. fCommonValidateDynamicPageDisplayed("xpath:=//h1","The page title")

		long internalStartTime = System.currentTimeMillis();
		WebElement objPageTitle = null;     
		String sPageTitle = null;

		try {   

			//validate if driver.getCurrentUrl() contains url sent in webElmtTextOrUrl
			//e.g. fCommonValidateDynamicPageDisplayed("",internet.html) 
			if (webElmtLocator.isEmpty()) {

				sPageTitle = driver.getCurrentUrl().toLowerCase().trim(); //otherwise check the url text

			}

			//validate if the webElement is displayed and enabled and re-try until is displayed or until threshold timeout reached
			//e.g. fCommonValidateDynamicPageDisplayed("id:=clearFilterTokens,"")
			//e.g. fCommonValidateDynamicPageDisplayed("path:=//h1","The page title")
			else
			{   
				boolean isElementFound=false;
				while(!isElementFound && System.currentTimeMillis() < endTime) {

					objPageTitle = fCommonGetWebElement(webElmtLocator, webElmtLocator);

					// Added code to wait loading of Icon.jpg// Amit // 17/08/2017



					//					try {
					//						for(int i=0;i<=2000;i++){
					//							WebElement webSpinner = fCommonGetObject("xpath:=//pageload-indicator[not(@class ='ng-hide')]//i[@class='icon-spinner']","Spinner");
					//							if(!(webSpinner==null)){
					//								if(!webSpinner.isEnabled()){
					//									//System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - WebElement found successful - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
					//									break;
					//								}
					//								else{
					//									i++;
					//								}
					//							}	
					//							else{
					//								//System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - WebElement found successful - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
					//								break;
					//							}
					//						}
					//					} catch (Exception e) {
					//						// TODO Auto-generated catch block
					//						e.printStackTrace();
					//						break;
					//					}

					/*					try {
						for(int i=0;i<=2000;i++){
							WebElement webSpinner = fCommonGetObject("xpath:=//pageload-indicator[not(@class ='ng-hide')]//i[@class='icon-spinner']","Spinner");
							if(!(webSpinner==null)){
								if(!webSpinner.isEnabled()){
									//System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - WebElement found successful - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
									break;
								}
								else{
									i++;
								}
							}	
							else{
								//System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - WebElement found successful - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
								break;
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}*/

					if(objPageTitle != null){             
						System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - WebElement found successful - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
						isElementFound=true;
						break;
					}                    

					// check for popups, just in case but with 0 timeout since they should be already displayed
					handlePopUps(0, 0);

					System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText : WebElement not found on page. Retrying in " + WAIT_FOR_ELEMENT_POLLING_TIME + "ms.");
					sleep(WAIT_FOR_ELEMENT_POLLING_TIME);                  

				}

				//check if it passed the gloabl timeout for page load
				if(System.currentTimeMillis() > endTime ){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText timeout reached : global = " +(WAIT_FOR_PAGE*1000)+"ms");
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - return null - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText", "Check if page contains expected web emenent","Global timeout "+WAIT_FOR_PAGE+" seconds reached for page containing element "+webElmtLocator, "Fail");
					return null;
				}

				if(!isElementFound){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - WebElement "+webElmtLocator+" not found within global timeout threshold - return null - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
					return null;
				}else{
					sPageTitle  = objPageTitle.getText().toLowerCase().trim(); 
				}

			}

			boolean isExpectedTextOrUrl = false;

			//for scenario where looking ONLY for element presence without expected text check
			if (webElmtTextOrUrl.isEmpty()) {
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - return sPageTitle - WebElement only found successful - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
				return sPageTitle; 
			}           

			//validate expected:webElmtTextOrUrl  VS  actual:sPageTitle
			//validate if sPageTitle is contained in one of the values of webElmtTextOrUrl separated by (##)
			else{
				// sometimes the element is loaded but its text didn't load yet - giving time until timeout to load
				outerloop:
					while(!isExpectedTextOrUrl && System.currentTimeMillis() < endTime) {
						for (String expectedTitle : webElmtTextOrUrl.toLowerCase().trim().split("##")) {
							if (sPageTitle.contains(expectedTitle.trim())) {
								isExpectedTextOrUrl = true;
								break outerloop;
							}
						}

						// check for popups, just in case but with 0 timeout since they should be already displayed
						handlePopUps(0, 0);

						if (webElmtLocator.isEmpty()) {
							System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText : Current URL - "+sPageTitle+" - does not contain expected url - "+webElmtTextOrUrl+". Retrying in " + WAIT_FOR_ELEMENT_POLLING_TIME + "ms.");
						}else{
							System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText : WebElement text -  "+sPageTitle+" - does not contains expected text - "+webElmtTextOrUrl+". Retrying in " + WAIT_FOR_ELEMENT_POLLING_TIME + "ms.");
						}                                          

						sleep(WAIT_FOR_ELEMENT_POLLING_TIME);
						if (!webElmtLocator.isEmpty())//06-Aug-2015-Dina
						{
							sPageTitle = fCommonGetWebElement(webElmtLocator, webElmtTextOrUrl).getText().toLowerCase().trim();
						}
						else
						{
							sPageTitle = driver.getCurrentUrl().toLowerCase().trim();
						}
					}

			//check if it passed the gloabl timeout for page load
			if(System.currentTimeMillis() > endTime ){
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText timeout reached : global = " +(WAIT_FOR_PAGE*1000)+"ms");
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - return null - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText", "Check if page contains expected web emenent","Global timeout "+WAIT_FOR_PAGE+" seconds reached for page containing element "+webElmtLocator, "Fail");
				return null;
			}
			}

			if (isExpectedTextOrUrl == false) {
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - return null - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
				return null;
			}

		}catch (Exception e) {
			if(e.toString().contains("UnhandledAlertException"))
			{
				fCommonHandleAlert("dismiss");
				//System.out.println("Dina - exception checkPageContainsText: " + e.toString());
			}
			else
			{
				e.printStackTrace();
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText exception caught:  " + e);
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - return null - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
				return null;
			}
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - return sPageTitle - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
		return sPageTitle;

	}


	//*****************************************************************************************
	//* Name            : waitForHostSync
	//* Description     : Method to check if host has been changed
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :June 02, 2015
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : 
	//* Return Values   : boolean - Depending on the success
	//***************************************************************************************** 
	public boolean waitForHostSync(WebDriver driver, String hostExpected, long startTime, long endTime){

		long internalStartTime = System.currentTimeMillis();

		//loop for veriying Host 
		while(true){

			//check if it passed the gloabl timeout for page load
			if(System.currentTimeMillis() > endTime){              
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForHostSync timeout reached : global : " +(endTime - startTime)+"ms");
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForHostSync - return false - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "hostSync", "Check if host contains "+hostExpected,"Global timeout "+WAIT_FOR_PAGE+" seconds reached for host check", "Fail");
				return false;
			}

			//check if url is reached
			if(driver.getCurrentUrl().contains(hostExpected)){
				//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "hostSync", "Check host sync","Url check successful", "Done");
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForHostSync - return true -  has delayed with --> " + (System.currentTimeMillis() - internalStartTime) + "ms");
				return true;            }

			try{
				// check for popups, just in case but with 0 timeout since they should be already displayed
				handlePopUps(0, 0);

				// no popups, still waiting for the url to be redirected 
				sleep(WAIT_FOR_PAGE_POLLING_TIME);
			}catch(Exception e){System.out.println(e);}

		}


	}

	//*****************************************************************************************
	//* Name            : getHostName
	//* Description     : method to get the hostname from input url
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :June 02, 2015
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : 
	//* Return Values   : String - hostname
	//***************************************************************************************** 
	public String getHostName(String urlInput) {

		if(urlInput==null || urlInput.equals("")){
			return null;
		}else{
			String hostName=urlInput.toLowerCase();
			if(urlInput.startsWith("http") || urlInput.startsWith("https")){
				try{
					URL netUrl = new URL(urlInput);
					String host= netUrl.getHost();
					if(host.startsWith("www")){
						hostName = host.substring("www".length()+1);
					}else{
						hostName=host;
					}
				}catch (MalformedURLException e){
					System.out.println("Exception MalformedURLException : "+e);
					hostName=urlInput;
				}
			}else if(urlInput.startsWith("www")){
				hostName=urlInput.substring("www".length()+1);
				if(hostName.contains("/")){
					hostName=hostName.substring(0,hostName.indexOf("/"));
				}
			}else{
				return null;
			}
			return  hostName;
		}
	}

	//*****************************************************************************************
	//* Name            : setHostName
	//* Description     : method to set the hostname in the Environment HashMap
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :June 02, 2015
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : 
	//* Return Values   : boolean
	//***************************************************************************************** 
	public boolean setHostName(String host) {

		try {
			Environment.put("ENV_HOST",host);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public String getThreadId() {
		return "THREAD:" + Thread.currentThread().getId();
	}


	//*****************************************************************************************
	//* Name            : validateHostName
	//* Description     : validate if the hostname is mathching expected hostname
	//* Created release : 1512
	//* Last modified in: 03 Dec 2015
	//* Author          : Gavril Grigorean
	//* Input Params    : expected hostname
	//* Return Values   : Boolean - Depending on the success
	//***************************************************************************************** 
	public boolean validateHostName(String hostExpected){

		long internalStartTime = System.currentTimeMillis();

		try{

			if(!getHostName(driver.getCurrentUrl()).equals(hostExpected)){
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "validateHostName", "Validate the hostname",
						"Current url host - "+driver.getCurrentUrl() + " - is not matching expected host : "+hostExpected, "Fail");
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "validateHostName - return false - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
				return false;            }

		}catch(Exception e){
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "validateHostName", "Validate the hostname",
					"Exception occured " + e.toString(), "Fail");
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "validateHostName - return true - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
			return false;
		}

		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "validateHostName", "Validate the hostname",
				"Current url host - "+driver.getCurrentUrl() + " - is matching expected host : "+hostExpected, "Pass");
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "validateHostName - return true - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
		return true;

	}



	//*****************************************************************************************
	//* Name            : waitForElementToContainsText
	//* Description     : method to wait for WAIT_FOR_ELEMENT seconds until an element has the expected text
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :June 08, 2015
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : 
	//* Return Values   : boolean
	//***************************************************************************************** 
	public boolean waitForElementToContainsText(String webElmtProp, final String expectedText) {

		long startTime = System.currentTimeMillis();

		//Get WebElement
		final WebElement webElement = fCommonGetObject(webElmtProp,webElmtProp);
		if(webElement == null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForElementToContainText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForElementToContainText: " + webElmtProp ,"Object should be found", "Object was not found. Null value returned", "Fail");
			return false;
		}

		try {          
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

			WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT, WAIT_FOR_ELEMENT_POLLING_TIME);
			wait.until(new Function<WebDriver, WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {                  
					if (webElement.getText().trim().contains(expectedText)) {
						return webElement;
					}else{
						return null;
					}     

				}
			});
		} catch (Exception e) {
			System.out.println("Exception : "+e.toString());
			e.printStackTrace();

			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS);

			System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForElementToContainText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return false;
		}      

		System.out.println(fGetThreadClassNameAndMethod() + " -- waitForElementToContainText - return true - for object '" + 
				webElmtProp + "' has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");

		return true;
	}



	//*****************************************************************************************
	//* Name            : waitForElementToContainsAnyText
	//* Description     : method to wait for WAIT_FOR_ELEMENT seconds until an element has any text
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :June 11, 2015
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : 
	//* Return Values   : boolean
	//***************************************************************************************** 
	public boolean waitForElementToContainsAnyText(String webElmtLocator, String webElmtName) {

		long startTime = System.currentTimeMillis();

		//Get WebElement
		final WebElement webElement = fCommonGetObject(webElmtLocator,webElmtName);
		if(webElement == null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForElementToContainAnyText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForElementToContainAnyText: " + webElmtName ,"Object should be found", "Object was not found. Null value returned", "Fail");
			return false;
		}

		try {          
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

			WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT, WAIT_FOR_ELEMENT_POLLING_TIME);
			wait.until(new Function<WebDriver, WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {                  
					if (webElement.getText().length()>0) {
						return webElement;
					}else{
						return null;
					}     

				}
			});
		} catch (Exception e) {
			System.out.println("Exception : "+e.toString());
			e.printStackTrace();

			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS);

			System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForElementToContainAnyText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			return false;
		}      

		System.out.println(fGetThreadClassNameAndMethod() + " -- waitForElementToContainAnyText - return true - for object '" + 
				webElmtName + "' has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");

		return true;
	}


	//*****************************************************************************************
	//* Name            : checkWebElementContainsText
	//* Description     : check if the web element text contains expected value
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1506
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : June 08 - 2015
	//* Input Params    : object locator and expected text
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean checkWebElementContainsText(String objectLocator, String expectedText){

		long startTime=System.currentTimeMillis();

		//Get WebElement
		WebElement webElement = fCommonGetObject(objectLocator,objectLocator);  

		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText", "Check Object Existance","Object is null", "Done");
			return false;
		}

		//Check if the WebElement contains expectedText
		try {
			if(!webElement.getText().toLowerCase().contains(expectedText.toLowerCase())){

				if (webElement.getAttribute("value")!=null){
					if(!webElement.getAttribute("value").trim().contains(expectedText.trim())){
						System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
						return false;
					}
				}else{
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					return false;
				}
			}
		} catch (Exception e1) {
			System.out.println("Exception while webElement.getText() : " + e1.toString());
			e1.printStackTrace();
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText", "Exception occurred for when getting getText()" + objectLocator,"Exception: " + e1, "Fail");
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText", expectedText+" should be displayed", expectedText+" is displayed",  "Pass");

		return true;

	}

	//*****************************************************************************************
	//* Name            : checkWebElementContainsText
	//* Description     : check if the web element text contains expected value
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1506
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : June 25 - 2015
	//* Input Params    : web element and expected text
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean checkWebElementContainsText(WebElement webElement, String expectedText){

		long startTime=System.currentTimeMillis();

		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText", "Check Object Existance","Object is null", "Done");
			return false;
		}

		//Check if the WebElement contains expectedText
		try {
			if(!webElement.getText().contains(expectedText)){

				if (webElement.getAttribute("value")!=null){
					if(!webElement.getAttribute("value").trim().contains(expectedText.trim())){
						System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
						return false;
					}
				}else{
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					return false;
				}
			}
		} catch (Exception e1) {
			System.out.println("Exception while webElement.getText() : " + e1.toString());
			e1.printStackTrace();
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText", "Exception occurred for when getting getText()","Exception: " + e1, "Fail");
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsText - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return true;

	}

	//*****************************************************************************************
	//* Name            : checkWebElementContainsAnyText
	//* Description     : check if the web element text contains any text
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1506
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : June 08 - 2015
	//* Input Params    : object locator
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean checkWebElementContainsAnyText(String objectLocator){

		long startTime=System.currentTimeMillis();

		//Get WebElement
		WebElement webElement = fCommonGetObject(objectLocator,objectLocator);  

		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsAnyText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsAnyText", "Check Object Existance","Object is null", "Done");
			return false;
		}

		//Check if the WebElement contains any text
		try {
			if(!(webElement.getText().length()>0)){

				if (webElement.getAttribute("value")!=null){
					if(!(webElement.getAttribute("value").trim().length()>0)){
						System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsAnyText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
						return false;
					}
				}else{
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsAnyText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					return false;
				}
			}
		} catch (Exception e1) {
			System.out.println("Exception while webElement.getText() : " + e1.toString());
			e1.printStackTrace();
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsAnyText - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsAnyText", "Exception occurred for when getting getText()" + objectLocator,"Exception: " + e1, "Fail");
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkWebElementContainsAnyText - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		return true;

	}

	//*****************************************************************************************
	//* Name            : waitForWebElementIsEnabled
	//* Description     : wait for WAIT_FOR_ELEMENT seconds until the web element is enabled
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1506
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : June 09 - 2015
	//* Input Params    : object locator and object name
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean waitForWebElementIsEnabled(String webElmtLocator, String webElmtName){

		long startTime=System.currentTimeMillis();

		//Get WebElement
		WebElement webElement = fCommonGetObject(webElmtLocator,webElmtName);  

		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForWebElementIsEnabled - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForWebElementIsEnabled", "Check object existance for "+webElmtName,"Object is null", "Done");
			return false;
		}

		return waitForWebElementIsEnabled(webElement,webElmtName);

	}


	//*****************************************************************************************
	//* Name            : waitForWebElementIsEnabled
	//* Description     : wait for WAIT_FOR_ELEMENT seconds until the web element is enabled
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1506, 1511
	//* Author          : Stefan Andrei
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : 
	//* Input Params    : web element; web element name
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean waitForWebElementIsEnabled(final WebElement element, String webElmtName) {

		long startTime = System.currentTimeMillis();

		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
			fCommonHandleAlert("dismiss");
			WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT, WAIT_FOR_ELEMENT_POLLING_TIME);
			wait.until(new Function<WebDriver, WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {
					fCommonHandleAlert("dismiss");
					if (element == null) {
						return null;
					} else {
						if (element.isEnabled()) {
							return element;
						} else {
							return null;
						}
					}
				}
			});

		} catch (NoSuchElementException noSuchElementException){           
			noSuchElementException.printStackTrace();           
			System.out.println(fGetThreadClassNameAndMethod() + " -- waitForWebElementIsEnabled - return false - for object '" + 
					webElmtName + "' has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForWebElementIsEnabled", "Wait up to "+WAIT_FOR_ELEMENT_POLLING_TIME+" seconds for element -- "+webElmtName+" -- to be enabled","NoSuchElementException exception: " + noSuchElementException, "Done");
			Reporter.fnWriteToHtmlOutput("Wait for element :"+webElmtName+" to be enabled", "Wait up to "+WAIT_FOR_ELEMENT_POLLING_TIME+" seconds for element -- "+webElmtName+" -- to be enabled","NoSuchElementException exception: " + noSuchElementException, "Done");
			return false;
		}catch (TimeoutException  timeoutException) {
			timeoutException.printStackTrace();           
			System.out.println(fGetThreadClassNameAndMethod() + " -- waitForWebElementIsEnabled - return false - for object '" + 
					webElmtName + "' has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForWebElementIsEnabled", "Wait up to "+WAIT_FOR_ELEMENT_POLLING_TIME+" seconds for element -- "+webElmtName+" -- to be enabled","TimeoutException exception: " + timeoutException, "Done");
			Reporter.fnWriteToHtmlOutput("Wait for element :"+webElmtName+" to be enabled", "Wait up to "+WAIT_FOR_ELEMENT_POLLING_TIME+" seconds for element -- "+webElmtName+" -- to be enabled","TimeoutException exception: " + timeoutException, "Done");
			return false;
		}catch (Exception  exception) {
			exception.printStackTrace();    
			if(!exception.getLocalizedMessage().contains("stale element reference: element is not attached to the page document")){
				System.out.println(fGetThreadClassNameAndMethod() + " -- waitForWebElementIsEnabled - return false - for object '" + 
						webElmtName + "' has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
//				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForWebElementIsEnabled", "Wait up to "+WAIT_FOR_ELEMENT_POLLING_TIME+" seconds for element -- "+webElmtName+" -- to be enabled","Exception: " + exception, "Done");
				Reporter.fnWriteToHtmlOutput("Wait for element :"+webElmtName+" to be enabled", "Wait up to "+WAIT_FOR_ELEMENT_POLLING_TIME+" seconds for element -- "+webElmtName+" -- to be enabled","Exception: " + exception, "Done");
			}
			return false;
			
		}finally{
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS);
		}

		System.out.println(fGetThreadClassNameAndMethod() + " -- waitForWebElementIsEnabled - return true - for object -- " + 
				webElmtName + " -- has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
//		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForWebElementIsEnabled", "Wait up to "+WAIT_FOR_ELEMENT_POLLING_TIME+" seconds for element -- "+webElmtName+" -- to be enabled", "Element -- "+webElmtName+" -- is enabled", "Done");
		Reporter.fnWriteToHtmlOutput("Wait for element :"+webElmtName+" to be enabled", "Wait up to "+WAIT_FOR_ELEMENT_POLLING_TIME+" seconds for element -- "+webElmtName+" -- to be enabled", "Element -- "+webElmtName+" -- is enabled", "Done");
		return true;
	}


	//*****************************************************************************************
	//* Name            : waitForWebElementIsClickable
	//* Description     : wait for WAIT_FOR_ELEMENT seconds until the web element is clickable
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1506
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : June 09 - 2015
	//* Input Params    : object locator and object name
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean waitForWebElementIsClickable(String webElmtLocator, String webElmtName){

		long startTime=System.currentTimeMillis();

		//Get WebElement
		WebElement webElement = fCommonGetObject(webElmtLocator,webElmtName);  

		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForWebElementIsClickable - return false - WebElemetn is null -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance", "Check Object Existance","Object is null", "Done");
			return false;
		}

		return waitForWebElementIsClickable(webElement,webElmtName);

	}


	//*****************************************************************************************
	//* Name            : waitForWebElementIsClickable
	//* Description     : wait for WAIT_FOR_ELEMENT seconds until the web element is clickable
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1506; 1510       
	//* Author          : Stefan Andrei
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : 
	//* Input Params    : web element, web element name
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean waitForWebElementIsClickable(WebElement element, String webElmtName) {

		long startTime = System.currentTimeMillis();

		try {          
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
			WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT, WAIT_FOR_ELEMENT_POLLING_TIME);
			element = wait.until(ExpectedConditions.elementToBeClickable(element));
		}catch (NoSuchElementException noSuchElementException){           
			noSuchElementException.printStackTrace();           
			System.out.println(fGetThreadClassNameAndMethod() + " -- waitForWebElementIsClickable - return false - for object '" + 
					webElmtName + "' has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForWebElementIsClickable", 
					"Wait up to "+WAIT_FOR_ELEMENT_POLLING_TIME+" seconds for element -- "+webElmtName+" -- to be clickable",
					"NoSuchElementException exception: " + noSuchElementException, "Done");
			return false;
		}catch (TimeoutException  timeoutException) {
			timeoutException.printStackTrace();           
			System.out.println(fGetThreadClassNameAndMethod() + " -- waitForWebElementIsClickable - return false - for object '" + 
					webElmtName + "' has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForWebElementIsClickable", 
					"Wait up to "+WAIT_FOR_ELEMENT_POLLING_TIME+" seconds for element -- "+webElmtName+" -- to be clickable",
					"TimeoutException exception: " + timeoutException, "Done");
			return false;
		}catch (Exception  exception) {
			exception.printStackTrace();           
			System.out.println(fGetThreadClassNameAndMethod() + " -- waitForWebElementIsClickable - return false - for object '" + 
					webElmtName + "' has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForWebElementIsClickable", 
					"Wait up to "+WAIT_FOR_ELEMENT_POLLING_TIME+" seconds for element -- "+webElmtName+" -- to be clickable",
					"Exception: " + exception, "Done");
			return false;
		}finally{
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS);
		}

		System.out.println(fGetThreadClassNameAndMethod() + " -- waitForWebElementIsClickable - return true - for object -- " + 
				webElmtName + " -- has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForWebElementIsClickable", 
				"Wait up to "+WAIT_FOR_ELEMENT_POLLING_TIME+" seconds for element -- "+webElmtName+" -- to be clickable",
				"Element -- "+webElmtName+" -- is clickable", "Done");
		return true;
	}

	//*****************************************************************************************
	//* Name            : validateEmail
	//* Description     : this method runs an executable which in turn runs a search on _Auto Test attuser 's inbox folder for order confirmation messsages. 
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1506
	//* Author          : Abhigyan Dwivedi
	//* Updated by      : 
	//* Updated Date    : 
	//* Input Params    : params a string array of variable arguements so at runtime any number of values to be looked up in a specific email could be passed 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	/**
	 * Method checking if an element is clickable and waiting until the timeout
	 * @param objList
	 */

	public boolean validateEmail(String ... params) {

		fCommonSync(5000);

		String path=System.getProperty("user.dir");
		path=path+"\\Debug\\";
		String cmd=path+"EmailValidator.exe";//+userName+" "+orderNumber;
		ArrayList<String> result=new ArrayList<String>();
		String str;
		String param=" ";
		for(String s:params){

			param=param+" \""+s+"\""+" ";

		}
		cmd=cmd+param;
		//System.out.println(param);
		//System.out.println("final command:"+cmd);
		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "validate Email: for " + param.trim().replaceAll("\"  \"","\"&\"") ,"Email should be against the specified parameters", "Email is against the specified parameters", "DONE");
		try {
			//Setting up command line parameters to be passed in vararg params.
			//Process p=Runtime.getRuntime().exec(cmd,params);
			Process p=Runtime.getRuntime().exec(cmd);
			InputStream stdin = p.getInputStream();
			InputStreamReader isr = new InputStreamReader(stdin);
			BufferedReader br = new BufferedReader(isr);
			while ((str = br.readLine()) != null) {
				result.add(str);
			}
			p.waitFor();
			String output=result.get(0);

			if(output.equals("message found")) {
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "validate Email: for " + param.trim().replaceAll("\"  \"","\"&\"") ,"Email should be against the specified parameters", "Email was SUCCESSFULLY validated against specified parameters", "DONE");              
				System.out.println(output+" In success"); 
			}
			else{
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "validate Email: for " + param.trim().replaceAll("\"  \"","\"&\"") ,"Email should be against the specified parameters", "Email  validation FAILED against specified parameters", "FAIL");
				// System.out.println(output+false);
				return false;
			}

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			//e.printStackTrace();
		}
		catch(Exception e){
			System.out.println(e);
		}
		// System.out.println(true);
		return true;
	}
	//*****************************************************************************************
	//* Name            : COAMandBARKFirstNameLastNameGenerator
	//* Description     : This method reutrns the following string sample: "vvtra##riset" at the recieving end extract out these values 
	//*					  and put the splitted array's string elements into first name and last name respectively.
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1506
	//* Author          : Abhigyan Dwivedi
	//* Updated by      : 
	//* Updated Date    : 
	//* Input Params    : params a string array of variable arguements so at runtime any number of values to be looked up in a specific email could be passed 
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************

	public String COAMandBARKFirstNameLastNameGenerator() {

		java.util.Random random=new Random();

		StringBuilder tmp = new StringBuilder();
		for (char ch = 'a'; ch <= 'z'; ++ch)
			tmp.append(ch);
		char[]  symbols = tmp.toString().toCharArray();

		char buf[]=new char[5];

		for (int idx = 0; idx < buf.length; ++idx) 
			buf[idx] = symbols[random.nextInt(symbols.length)];
		// return     
		String FirstName=new String(buf);    

		for (int idx = 0; idx < buf.length; ++idx) 
			buf[idx] = symbols[random.nextInt(symbols.length)];
		String LastName=new String(buf);    
		return FirstName+"##"+LastName;
	}

	//*****************************************************************************************
	//* Name            : waitForWebElementIsDisplayed
	//* Description     : wait for WAIT_FOR_ELEMENT seconds until the web element is displayed
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1506
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : June 25 - 2015
	//* Input Params    : object locator and object name
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean waitForWebElementIsDisplayed(String webElmtLocator, String webElmtName){

		long startTime=System.currentTimeMillis();

		//Get WebElement
		WebElement webElement = fCommonGetObject(webElmtLocator,webElmtName);  

		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForWebElementIsDisplayed - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonCheckObjectExistance", "Check Object Existance","Object is null", "Done");
			return false;
		}

		return waitForWebElementIsDisplayed(webElement,webElmtName);

	}


	//*****************************************************************************************
	//* Name            : waitForWebElementIsDisplayed
	//* Description     : wait for WAIT_FOR_ELEMENT seconds until the web element is displayed
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1506
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : June 25 - 2015
	//* Input Params    : web element
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean waitForWebElementIsDisplayed(final WebElement element,String webElmtName) {
		fCommonHandleAlert("dismiss");
		long startTime = System.currentTimeMillis();

		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

			WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT, WAIT_FOR_ELEMENT_POLLING_TIME);
			fCommonHandleAlert("dismiss");
			wait.until(new Function<WebDriver, WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {
					fCommonHandleAlert("dismiss");
					if (element == null) {
						return null;
					} else {
						if (element.isDisplayed()) {
							return element;
						} else {
							return null;
						}
					}
				}
			});


		} catch (Exception e) {
			System.out.println("Exception : "+e.toString());
			e.printStackTrace();
			System.out.println(fGetThreadClassNameAndMethod() + " -- waitForWebElementIsDisplayed - return false - for object '" + 
					webElmtName + "' has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
			return false;
		}finally{
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS);
		}

		System.out.println(fGetThreadClassNameAndMethod() + " -- waitForWebElementIsDisplayed - return true - for object '" + 
				webElmtName + "' has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
		return true;
	}

	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {          
			e.printStackTrace();
		}
	}
	//*****************************************************************************************
	//* Name            : getXPathCount
	//* Description     : Method to get the node count by xpath
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1506
	//* Author          : Bruce Ritter 
	//* Updated by      : Gavril Grigorean
	//* Updated Date    : July 31, 2015
	//* Return Values   : Boolean - Depending on the success
	//***************************************************************************************** 

	public int getXPathCount()
	{
		final String notHiddenElementxPathlet = "not(self::*[contains(@visibility,'hidden')]) and not(ancestor::div[contains(@style,'display: none')])";
		return getXPathCount("//*["+notHiddenElementxPathlet+"]");
	}
	public int getXPathCount(String xPath)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String jsScriptTemplate = "var nodeCount = document.evaluate(\"count(%s)\", document, null, XPathResult.ANY_TYPE, null); return nodeCount.numberValue;";
		String jsScript = String.format(jsScriptTemplate, xPath);
		Object result = js.executeScript(jsScript);
		return Integer.parseInt(result.toString()); 
	}

	//*****************************************************************************************
	//* Name            : waitForNodeSyncByXpath
	//* Description     : Method to check if all nodes has been loaded bu checking the xount from xpath
	//* CREATED RELEASE : 1506
	//* LAST MODIFIED IN :1506
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : July 31, 2015
	//* Return Values   : Boolean - Depending on the success
	//***************************************************************************************** 
	public boolean waitForNodesSyncByXpath(WebDriver driver, long startTime, long endTime) {

		long internalStartTime = System.currentTimeMillis();
		int previous = 0;
		int current = getXPathCount(); //initialize the number of nodes
		System.out.println(fGetThreadClassNameAndMethod() + " -- getXPathCount()="+current+" - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");

		long timeSliceMs = 500L; // check every 1/2 second
		do {

			//check if it passed the gloabl timeout for page load
			if(System.currentTimeMillis() > endTime){
				//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "nodes_Sync", "Check node sync","Global timeout for page load reached", "Done");
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForNodesSyncByXpath timeout reached : global : " +(endTime - startTime)+"ms");
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForNodesSyncByXpath - return false - has delayed with --> "+(System.currentTimeMillis()-internalStartTime)+"ms");
				return false;
			}

			previous = current; //pass the number of nodes from last iteration

			sleep(timeSliceMs);

			long internalStartTime2 = System.currentTimeMillis();
			current = getXPathCount();
			System.out.println(fGetThreadClassNameAndMethod() + " -- getXPathCount()="+current+" - has delayed with --> "+(System.currentTimeMillis()-internalStartTime2)+"ms");

		} while(current != previous); // as long as the number of nodes changed 


		System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForNodesSyncByXpath - return true -  has delayed with --> " +(System.currentTimeMillis() - internalStartTime) + "ms");
		//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForNodesSyncByXpath", "Check node sync","Node sync completed", "Done");
		return true;

	}

	/////////////////////////////////
	///////---Fraud Check ---////////
	//*****************************************************************************************
	//* Name            	: RunFraudCheck
	//* Description     	: Run Fraud Check
	//* CREATED RELEASE 	: 1506
	//* LAST MODIFIED IN 	: 1506
	//* Author          	: Kapish Kumar
	//* Updated by      	: 
	//* Updated Date    	: July 31, 2015
	//* Return Values   	: Boolean - Depending on the success
	//***************************************************************************************** 
//	public void RunFraudCheck() {
//		WebDriver webdriver;
//		if(Environment.get("FRAUD_CHECK").equalsIgnoreCase("Y")){
//
//			ChromeOptions options = new ChromeOptions();        
//			options.addArguments("--disable-extensions");
//			options.addArguments("-incognito");
//
//			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/exe/chromedriver.exe");  
//
//			webdriver = new ChromeDriver(options);
//			try {
//				webdriver.navigate().to(Environment.get("FRAUD_CHECK_URL"));
//			} catch (Exception e2) {
//				System.err.println("Environment.get(\"FRAUD_CHECK_URL\") might have null value");
//				System.exit(0);
//			}
//
//			try{
//				try {
//					webdriver.findElement(By.cssSelector("input[name='userid']")).sendKeys("JA559K");
//					webdriver.findElement(By.cssSelector("input[name='password']")).sendKeys("summer_15");
//					webdriver.findElement(By.cssSelector("input[name='login']")).click();
//				} catch (Exception e2) {
//					System.err.println("Objects Might have changed on the Webpage\n"+ e2);
//					System.exit(0);
//				}
//
//				try {Thread.sleep(3000);} catch (InterruptedException e1) {e1.printStackTrace();}
//				webdriver.navigate().to(Environment.get("FRAUD_CHECK_URL"));
//
//				try {Thread.sleep(6000);} catch (InterruptedException e1) {e1.printStackTrace();}
//
//				webdriver.switchTo().defaultContent();
//				try{
//					// driver.switchTo().frame(0);
//					webdriver.switchTo().frame(webdriver.findElement(By.id("faAdminIFrame")));
//					if(webdriver.findElement(By.xpath("//*[contains(text(),'Enable All Fraud Checks')]")).isEnabled()){
//						try{
//							webdriver.findElement(By.xpath("//*[contains(text(),'Enable All Fraud Checks')]")).click();
//						}catch(Exception e){
//							System.err.println("Tests can not be run ,as Fraud Check button is enabled");
//							System.exit(0);
//						}
//					}else{
//						System.out.println("Tests can be run, as Fraud Check button is disabled");
//					}
//
//					webdriver.switchTo().defaultContent();
//
//					webdriver.quit();
//				}catch(Exception e){
//					System.out.println(e);
//				}
//			}catch(Exception e){
//				System.out.println(e);
//			}
//		}
//	}
	/////--- Fraud Check ---/////
	/////////////////////////////


	/**
	 * @param e
	 * @return given exception to string, including the stack trace
	 */
	public static String exceptionToString(Throwable e) {

		// also return null for AssertionError since we get that for all handled tests errors
		if (e == null || e instanceof AssertionError) {
			return null;
		}

		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));

		return sw.toString();
	}

	//*****************************************************************************************
	//* Name            	: HandlePopUpsByESC
	//* Description     	: Use of escape Key to handle authentication popup on Chrome browser
	//* CREATED RELEASE 	: 1506
	//* LAST MODIFIED IN 	: 1506
	//* Author          	: Amit Kumar
	//* Updated by      	: 
	//* Updated Date    	: October 09, 2015
	//* Return Values   	: void - Depending on the success
	//***************************************************************************************** 
	public void HandlePopUpsByESC() {

		try{
			if (driverType.contains("CHROME")){
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_ESCAPE);
			}
		}catch(Exception e){
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS); 
		}
	}


	//*****************************************************************************************
	//* Name            : fGetSessionID
	//* Description     : Function to get session id based on the application type 
	//* CREATED RELEASE : 
	//* LAST MODIFIED IN :
	//* Author          : Naveena Basetty
	//* Input Params    : String shop/sales - SESSION_NAME
	//* Return Values   : 
	//*****************************************************************************************
	public void fGetSessionID(String SESSION_NAME){

		try{
			if(driverType.contains("CHROME")){
				String co = ((JavascriptExecutor) driver).executeScript("return document.cookie;").toString();
				System.out.println(co);
				String[] cookie = ((JavascriptExecutor) driver).executeScript("return document.cookie;").toString().split(SESSION_NAME.toUpperCase()+"SESSIONID=");
				String[] sessionid = cookie[1].split(";");
				Environment.put("SESSION_ID",sessionid[0]);
			}

		}
		catch (Exception e) {
			Reporter.fnWriteToHtmlOutput("fCommonGetSessionID", "Exception occurred for object: " + SESSION_NAME,"Exception :" + e, "Fail");
			Environment.put("SESSION_ID","Unable to capture session-id");
		}

	}

	//*****************************************************************************************
	//* Name            : fGetErrorFromUnixLogs
	//* Description     : Function to get unix logs based on session id 
	//* CREATED RELEASE : 
	//* LAST MODIFIED IN :
	//* Author          : Naveena Basetty
	//* Input Params    : String SessionID
	//* Return Values   : String - Depending on the success
	//*****************************************************************************************
	public String fGetErrorFromUnixLogs(String SessionID){

		if(Environment.get("SESSION_NAME")== null)
			return "Session name cannot be null";
		else if(SessionID == null)	
			return "SessionID cannot be null - unable to capture session ID";

		try{
			System.out.println("Validating UNIX logs for session id :" + SessionID);	
			JSch jsch = new JSch();
			Session session = jsch.getSession(Environment.get("UNIX_LOGIN"), Environment.get("UNIX_SERVER"), 22);
			session.setProxy(new ProxyHTTP("135.28.13.11", 8080));
			System.out.println("Connected to unix server : " + Environment.get("UNIX_SERVER"));	

			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(Environment.get("UNIX_PASSWORD"));
			session.connect();

			Channel channel = session.openChannel("shell");
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(channel.getInputStream()));  
			OutputStream  toServer = channel.getOutputStream();
			channel.connect();  
			toServer.write(("cd /sites/servers/"+ Environment.get("ENVIRONMENT").toLowerCase()+ "-"+Environment.get("SESSION_NAME").toLowerCase()+"-main1/logs \r\n").getBytes());
			toServer.flush();
			String command = "grep '" + SessionID +"' console.log | grep 'Error' ";
			toServer.write((command + "\r\n").getBytes());
			toServer.flush();
			Thread.sleep(4000);

			toServer.write(("$$$$" + "\r\n").getBytes());
			toServer.flush();


			StringBuilder builder = new StringBuilder();  
			String line = "";  

			while(true) {  
				line = fromServer.readLine();
				if(line.contains("$$$$"))break;

				//System.out.println(count++ + " " + line);
				builder.append(line).append("\n");
			}  

			String[] result = builder.toString().split(" grep 'Error'");  
			int index=0;
			for(int i=0;i<result.length;i++)
			{
				if (result[i].contains("**** Error")) 
				{ index = i;break;}
				else
					result[index] = "";
			}

			if(result[index].length() == 0)
				result[index] = "NO ERRORS FOUND";
			System.out.println(result[index]);

			session.disconnect(); 
			System.out.println("Disconnected from Unix server");  
			return result[index];
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("Error validating logs-verify manually");
			return "UNABLE TO VALIDATE LOGS DUE TO AN EXCEPTION-PLEASE CHECK MANUALLY FOR THIS TEST CASE";
		}
	}	



	//*****************************************************************************************
	//* Name            : moveToWebElement
	//* Description     : move to an element on the page using Actions class
	//* Created Date    : 19 Nov 2015 
	//* Created Release : 1512  
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : 
	//* Input Params    : object locator and object name
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean moveToWebElement(String webElmtLocator, String webElmtName){

		long startTime=System.currentTimeMillis();

		//Get WebElement
		WebElement webElement = fCommonGetObject(webElmtLocator,webElmtName);  

		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "moveToWebElement - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "moveToWebElement", "Check Object Existance","Object is null", "Fail");
			return false;
		}

		return  moveToWebElement(webElement,webElmtName);

	}


	//*****************************************************************************************
	//* Name            : moveToWebElement
	//* Description     : move to an element on the page using Actions class
	//* Created Date    : 19 Nov 2015 
	//* Created Release : 1512  
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : 
	//* Input Params    : web element
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean moveToWebElement(final WebElement element, String webElmtName) {

		long startTime = System.currentTimeMillis();      

		try {
			if(!driverType.contains("FIREFOX")){
				Actions actions = new Actions(driver);
				actions.moveToElement(element).build().perform();
			}
		} catch (Exception exception) {
			System.out.println(fGetThreadClassNameAndMethod() + " -- moveToWebElement - return false - for object '" + 
					webElmtName + "' has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
			exception.printStackTrace();
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "moveToWebElement", "Move to web element -- " +webElmtName,"Move to web element was not successful", "Fail");
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + " -- moveToWebElement - return true - for object '" + 
				webElmtName + "' has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "moveToWebElement", "Move to web element -- " +webElmtName,"Move to web element was successful", "Pass");
		return true;
	}


	//*****************************************************************************************
	//* Name            : waitForElementToContainAnyValue
	//* Description     : method to wait for WAIT_FOR_ELEMENT seconds until an element has any value in it
	//* Created Date    : 20 Nov 2015 
	//* Created Release : 1512  
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : 
	//* Input Params    : web element locator and web element name
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean waitForElementToContainAnyValue(String webElmtLocator, String webElmtName){

		long startTime=System.currentTimeMillis();

		//Get WebElement
		WebElement webElement = fCommonGetObject(webElmtLocator,webElmtName);  

		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForElementToContainAnyValue - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForElementToContainAnyValue", "Check Object Existance","Object is null", "Done");
			return false;
		}

		return  waitForElementToContainAnyValue(webElement,webElmtName);

	}


	//*****************************************************************************************
	//* Name            : waitForElementToContainAnyValue
	//* Description     : method to wait for WAIT_FOR_ELEMENT seconds until an element has any value in it
	//* Created Date    : 20 Nov 2015 
	//* Created Release : 1512  
	//* Author          : Gavril Grigorean
	//* Updated by      : 
	//* Updated Date    : 
	//* Input Params    : web element and web element name
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean waitForElementToContainAnyValue(final WebElement webElement, String webElmtName) {

		long startTime = System.currentTimeMillis();

		try {          
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

			WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT, WAIT_FOR_ELEMENT_POLLING_TIME);
			wait.until(new Function<WebDriver, WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {                  
					if (webElement.getAttribute("value").length()>0) {
						return webElement;
					}else{
						return null;
					}     

				}
			});
		} catch (Exception e) {	
			e.printStackTrace();
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "waitForElementToContainAnyValue - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "waitForElementToContainAnyValue", "Check if webelemnt contains any value","Exception occured: "+e.toString(), "Fail");		
			return false;
		}finally{
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS);
		}

		System.out.println(fGetThreadClassNameAndMethod() + " -- waitForElementToContainAnyValue - return true - for object '" + 
				webElmtName + "' has delayed with --> " + (System.currentTimeMillis() - startTime) + "ms");
		return true;
	}

	//*****************************************************************************************
	//*	Name		    : fGuiHandleNoThanksModalPgSrc
	//*	Description	    : Checks and Handle the Survey Popup
	//*	CREATED RELEASE : 1210
	//*	LAST MODIFIED IN :
	//*	Author		    : Abhigyan Dwivedi
	//*	Input Params	: None
	//*	Return Values	: Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fGuiHandleNoThanksModalPgSrc(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str="class=\"fsrC\"";
		if(driver.getPageSource().contains(str)==true){
			driver.findElement(By.linkText("No, thanks")).click();
		}
		return true;
	}

	//*****************************************************************************************
	//*	Name		    : generateRandomNumber
	//*	Description	    : Generates a random number of given length
	//*	CREATED RELEASE : 1604
	//*	LAST MODIFIED IN :
	//*	Author		    : Prakash Bastola
	//*	Input Params	: int charLength -> the length of the random number to be generated
	//*	Return Values	: Boolean - Depending on the success
	//*****************************************************************************************
	public String generateRandomNumber(int charLength) {

		Random r = new Random();

		String randomNum = "";

		int counter = 0;

		while (counter++ < charLength)
			randomNum += r.nextInt(9);
		return String.valueOf(randomNum);
	}

	//*****************************************************************************************
	//*	Name		    : createSOAPRequest
	//*	Description	    : Creates a soap request
	//*	CREATED RELEASE : 1604
	//*	LAST MODIFIED IN :
	//*	Author		    : Prakash Bastola
	//*	Input Params	: filepath for the request xml file
	//*	Return Values	: SOAPMEssage
	//*****************************************************************************************
	public SOAPMessage createSOAPRequest(String fileName) throws Exception {

		try{
			// Create a SOAP message from the XML file located at the given path
			FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir")+"/Requestxmls/wireless/OCE/" + fileName + ".xml"));
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage message = factory.createMessage(new MimeHeaders(), fis);
			return message;
		}
		catch(Exception e)
		{
			System.out.println("Exception occured while creating soap request");
			e.printStackTrace();
		}
		return null;
	}

	//*****************************************************************************************
	//*	Name		    : getSOAPResponse
	//*	Description	    : Gets soapresponse and saves it to a xml file
	//*	CREATED RELEASE : 1604
	//*	LAST MODIFIED IN :
	//*	Author		    : Prakash Bastola
	//*	Input Params	: SOAPrequest and the endpoint to be hit for response
	//*	Return Values	: SOAPMEssage
	//*****************************************************************************************
	public void getSOAPResponse(SOAPMessage soapRequest,
			String strEndpoint, String fileName) throws Exception, SOAPException {

		try{
			// Send the SOAP request to the given endpoint and return the
			// corresponding response
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory
					.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory
					.createConnection();
			SOAPMessage soapResponse = soapConnection
					.call(soapRequest, strEndpoint);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			soapResponse.writeTo(out);

			File newXmlFile = new File(System.getProperty("user.dir")+"/Requestxmla/OCE/" + fileName + " _Response.xml");
			FileWriter fw = new FileWriter(newXmlFile);
			fw.write(out.toString());
			fw.close();
		}
		catch (Exception e)
		{
			System.out.println("Exception occured while getting soap response");
			e.printStackTrace();
		}
	}

	//*****************************************************************************************
	//*	Name		    : getXMLElementText
	//*	Description	    : Returns text for the xml element 
	//*	CREATED RELEASE : 1604
	//*	LAST MODIFIED IN :
	//*	Author		    : Prakash Bastola
	//*	Input Params	: filename-> file path of the xml file, xpath-> Xpth of the element
	//*	Return Values	: String -> text of the given element
	//*****************************************************************************************
	public void updateXml(String xmlFile, String Xpath, String textUpdate) {

		String filePath = System.getProperty("user.dir")+"/Requestxmls/wireless/OCE/"+ xmlFile+".xml";
		try{
			DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbFac.newDocumentBuilder();
			Document doc = docBuilder.parse( filePath );

			//Create xPath object
			XPathFactory xPathfac = XPathFactory.newInstance();
			XPath objXpath = xPathfac.newXPath();           
			XPathExpression xpathExpr = objXpath.compile( Xpath);

			//Get List of nodes
			NodeList objNodeList = (NodeList)xpathExpr.evaluate(doc, XPathConstants.NODESET);

			System.out.println("Nodelist size is " + objNodeList.getLength());
			System.out.println(objNodeList.item(0).getFirstChild().getNodeValue());
			objNodeList.item(0).setTextContent(textUpdate);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);

			System.out.println("Done");
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
		catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//*****************************************************************************************
	//*	Name		    : getXMLElementText
	//*	Description	    : Returns text for the xml element 
	//*	CREATED RELEASE : 1604
	//*	LAST MODIFIED IN :
	//*	Author		    : Prakash Bastola
	//*	Input Params	: filename-> file path of the xml file, xpath-> Xpth of the element
	//*	Return Values	: String -> text of the given element
	//*****************************************************************************************
	public String getXMLElementText(String fileName, String Xpath) {

		String text ="";
		try{
			File newXmlFile = new File(fileName);
			text= fCommonGetXMLNodes(newXmlFile, Xpath).item(0).getFirstChild().getNodeValue();
		}
		catch(Exception e)
		{
			text = null;
		}
		return text;
	}

	//*****************************************************************************************
	//*	Name		    : isSuccessResponse
	//*	Description	    : Verifies for a given xml file if there is a success response
	//*	CREATED RELEASE : 1604
	//*	LAST MODIFIED IN :
	//*	Author		    : Prakash Bastola
	//*	Input Params	: SOAPrequest and the endpoint to be hit for response
	//*	Return Values	: SOAPMEssage
	//*****************************************************************************************
	public boolean isSuccessResponse(String fileName) {

		File newXmlFile = new File(System.getProperty("user.dir")+"/Requestxmla/OCE/" + fileName + " _Response.xml");
		return fCommonGetXMLNodes(newXmlFile, "/Envelope/Body/ProcessOrderResponse/ResponseDescription").item(0).getFirstChild().getNodeValue().equalsIgnoreCase("Success");
	}


	//*****************************************************************************************
	//* Name            : pressEscape
	//* Description     : simulate Escape button key press event
	//* CREATED RELEASE : 1604
	//* LAST MODIFIED IN :
	//* Author          : Gavril Grigorean
	//* Input Params    : none
	//* Return Values   : boolean
	//*****************************************************************************************
	public boolean pressEscape() {

		try{

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ESCAPE);

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}
	////////////////////////////////////////////////////// AMIT //////////////////////////////////////////////////////////////
	//*****************************************************************************************
	//* Name            : fCommonLaunchEnvironemntAfterCCC
	//* Description     : To open new browser with the 
	//* CREATED RELEASE : 1703
	//* LAST MODIFIED IN: 15th Dec 2016
	//* Author          : Amit kumar 
	//* Input Params    : none
	//* Return Values   : boolean
	//*****************************************************************************************
	//////////////////////////////  to open new Browser without deleting cookies////////////////////////////////////////////////////
	public boolean fCommonLaunchEnvironemntAfterCCC(String strURLCCC){

		long startTime=System.currentTimeMillis();

		System.out.println("fCommonLaunchEnvironment");
		try {

//			((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");
			if(driverType.contains("IOS")){
				try{
					((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");
				}catch(Exception e){
					System.out.println(e);
				}                           
			}
//			driver.navigate().refresh();

			// To set up Bothell/Allen coockies in gtm link 
			fGuiSetUpCookiesRegion();

			//open env according to given URL
			driver.get(strURLCCC);
			driver.manage().window().maximize();

//			Reporter.fnWriteToHtmlOutput("fCommonLaunchEnvironemnt", strURLCCC+" should be launched", strURLCCC+ " is launched successfully", "Pass");
			Reporter.fnWriteToHtmlOutput("Launch URL: "+strURLCCC+" in "+driverType.replaceAll("[0-9]", "").trim()+" browser", strURLCCC+" should be launched", strURLCCC+ " is launched successfully", "Pass");
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonLaunchEnvironemntname - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");

			//added by Naveena for log validation
//			if(!Environment.get("UNIX_LOG_VALIDATION").toLowerCase().equals("no") && Environment.get("UNIX_LOG_VALIDATION") != null)
//			{
//				if(Environment.get("SESSION_NAME")!=null)
//				{
//					String sessionid = fCommonGetCookieValue(Environment.get("SESSION_NAME").toUpperCase()+"SESSIONID");
//					if(sessionid != null )
//						Environment.put("SESSION_ID",fCommonGetCookieValue(Environment.get("SESSION_NAME").toUpperCase()+"SESSIONID"));
//					else
//						Environment.put("SESSION_ID",null);
//				}
//				else
//					Environment.put("SESSION_NAME", null);
//			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonLaunchEnvironemntname - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//			Reporter.fnWriteToHtmlOutput("fCommonLaunchEnvironemnt", "Exception occurred","Exception: " + e, "Fail");
			Reporter.fnWriteToHtmlOutput("Launch URL: "+strURLCCC+" in "+driverType.replaceAll("[0-9]", "").trim()+" browser", "URL is launched successfully","Exception occured while launching URL: " + e.getLocalizedMessage(), "Fail");
			return false;
		}       
	}   
	//*****************************************************************************************
	//* Name            : fCommonLaunchEnvironemntBoforeCCC
	//* Description     : To open new browser with the 
	//* CREATED RELEASE : 1703
	//* LAST MODIFIED IN: 15th Dec 2016
	//* Author          : Amit kumar 
	//* Input Params    : none
	//* Return Values   : boolean
	//*****************************************************************************************
	//////////////////////////////  to open new Browser without deleting cookies////////////////////////////////////////////////////
	public boolean fCommonLaunchEnvironemntBoforeCCC(String strURLCCC){

		long startTime=System.currentTimeMillis();

		System.out.println("fCommonLaunchEnvironment");
		try {
			driver.manage().deleteAllCookies();
			//			((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");
			//			if(driverType.contains("IOS")){
			//				try{
			//					((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");
			//				}catch(Exception e){
			//					System.out.println(e);
			//				}                           
			//			}
			driver.navigate().refresh();

			// To set up Bothell/Allen coockies in gtm link 
			fGuiSetUpCookiesRegion();

			//open env according to given URL
			driver.get(strURLCCC);
			driver.manage().window().maximize();

			Reporter.fnWriteToHtmlOutput("Launch URL: "+strURLCCC+" in "+driverType.replaceAll("[0-9]", "").trim()+" browser", strURLCCC+" should be launched", strURLCCC+ " is launched successfully", "Pass");
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonLaunchEnvironemntname - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");

			//added by Naveena for log validation
			if(!Environment.get("UNIX_LOG_VALIDATION").toLowerCase().equals("no") && Environment.get("UNIX_LOG_VALIDATION") != null)
			{
				if(Environment.get("SESSION_NAME")!=null)
				{
					String sessionid = fCommonGetCookieValue(Environment.get("SESSION_NAME").toUpperCase()+"SESSIONID");
					if(sessionid != null )
						Environment.put("SESSION_ID",fCommonGetCookieValue(Environment.get("SESSION_NAME").toUpperCase()+"SESSIONID"));
					else
						Environment.put("SESSION_ID",null);
				}
				else
					Environment.put("SESSION_NAME", null);
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonLaunchEnvironemntname - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput("Launch URL: "+strURLCCC+" in "+driverType.replaceAll("[0-9]", "").trim()+" browser", "Exception occurred","Exception: " + e, "Fail");
			return false;
		}       
	}   

	//*****************************************************************************************
	//* Name            : fCommonCloseBrowser
	//* Description     : Get the URL of the current browser and close  the browser.
	//* CREATED RELEASE : 1703
	//* LAST MODIFIED IN: 15th Dec 2016
	//* Author          : Amit kumar 
	//* Input Params    : none
	//* Return Values   : boolean
	//*****************************************************************************************
	//////////////////////////////  to Close the Browser////////////////////////////////////////////////////
	public boolean fCommonCloseBrowser(){

		String strCurrentURL = driver.getCurrentUrl();
		Reporter.fnWriteToHtmlOutput("fCommonCloseBrowser", "The current URL that driver are going to close is: " +strCurrentURL+" ", " ", "Done");
		driver.close();
		Reporter.fnWriteToHtmlOutput("fCommonCloseBrowser", "Browser should close","Browser is Closed with URL :"+strCurrentURL +" ", "Done");
		fCommonSync(5000);
		return true;
	}
	
	
	//*****************************************************************************************
	//* Name            : OpenNewTABinBrowser
	//* Description     : To open New TAB through Selenium 
	//* CREATED RELEASE : 1703
	//* LAST MODIFIED IN: 15th Dec 2016
	//* Author          : Amit kumar 
	//* Input Params    : none
	//* Return Values   : boolean
	//*****************************************************************************************
	//////////////////////////////To open New TAB in Browser////////////////////////////////////////////////////

	public boolean OpenNewTABinBrowser(){
		try {
			//			robot = new Robot();
			//			robot.keyPress(KeyEvent.VK_CONTROL);
			//			robot.keyPress(KeyEvent.VK_T);
			//			fCommonSync(2000);
			//			robot.keyRelease(KeyEvent.VK_CONTROL); 
			//			robot.keyRelease(KeyEvent.VK_T);

			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("function createDoc(){var w = window.open(); w.document.open();w.document.close();}; createDoc();");
			for(String winHandle : driver.getWindowHandles()){
				driver.switchTo().window(winHandle);
			}
			fCommonSync(2000);
			Reporter.fnWriteToHtmlOutput("OpenNewTABinBrowser", "User should able to open new TAB in same browser","New TAB open", "Done");
		} catch (Exception e) {
			Reporter.fnWriteToHtmlOutput("OpenNewTABinBrowser", "User should able to open new TAB in same browser","Unable to open New TAB ", "Fail");
			e.printStackTrace();
		}

		return true;
	}
	//*****************************************************************************************
	//* Name            : fCommonAddIntoDictionary
	//* Description     : To add the items in dectionary
	//* CREATED RELEASE : 1703
	//* LAST MODIFIED IN: 15th Dec 2016
	//* Author          : Amit kumar 
	//* Input Params    : Need toadd the String value
	//* Return Values   : boolean
	//*****************************************************************************************
	public boolean fCommonAddParamForCCC(String key, String Value){
		Reporter.fnWriteToHtmlOutput("AddIntoDictionary", "User are adding key as " +key+"  " + "and value as " +Value+"  ","Key and value are  added", "Done");
		Util.hMap.put(key,Value);	
		return true;
	}
	//	public boolean fCommonAddIntoDictionary(String key, String Value){
	//		
	//		HashMap<String, String> COSCParamFromWebPage = new HashMap<String, String>();
	//		Reporter.fnWriteToHtmlOutput("AddIntoDictionary", "User are adding key as " +key+"  " + "and value as " +Value+"  ","Key and value are  added", "Done");
	//		COSCParamFromWebPage.put(key,Value);	
	//		return true;
	//	}


	//*****************************************************************************************
	//* Name            : fCommonValidateURLContent
	//* Description     : To validate the URL Content
	//* CREATED RELEASE : 1703
	//* LAST MODIFIED IN: 30th Dec 2016
	//* Author          : Amit kumar 
	//* Input Params    : URL that need to be validated
	//* Return Values   : boolean
	//*****************************************************************************************
	public boolean fCommonValidateURLContent(String strURL){
		if(strURL==null){
			return false;
		}
		String strGetCurrentURL = driver.getCurrentUrl();
		if(!strGetCurrentURL.toLowerCase().equalsIgnoreCase(strGetCurrentURL.toLowerCase())==true){
			return false;
		}
		Reporter.fnWriteToHtmlOutput("fCommonValidateURLContent", "The current URL content" +strURL + "should match with " +strGetCurrentURL+"  ","URL content are matching", "Done");
		return true;
	}

	//*****************************************************************************************
	//* Name            : fCommonCloseCurrentBrowserAndSwitchToMainBrowser
	//* Description     : To  close the currrent working browser and switch to main browser
	//* CREATED RELEASE : 1703
	//* LAST MODIFIED IN: 30th Dec 2016
	//* Author          : Amit kumar 
	//* Input Params    : 
	//* Return Values   : boolean
	//*****************************************************************************************

	public boolean fCommonCloseCurrentBrowser(){

		try {
			driver.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//*****************************************************************************************
	//* Name            : fCommonDeleteCookies
	//* Description     : To  delete the cookies
	//* CREATED RELEASE : 1703
	//* LAST MODIFIED IN: 30th Dec 2016
	//* Author          : Amit kumar 
	//* Input Params    : 
	//* Return Values   : boolean
	//*****************************************************************************************
	public boolean fCommonDeleteCookies(){
		driver.manage().deleteAllCookies();
		fCommonSync(2000);
		Reporter.fnWriteToHtmlOutput("fCommonDeleteCookies", "cookies should be deleted" ,"Cookies deleted", "Done");
		return true;
	}

	//*****************************************************************************************
	//* Name            : RetrieveCSIXML
	//* Description     : To  Retrieve CSI Xml
	//* Author          : Shraddha Girme
	//* Input Params    : 
	//* Return Values   : boolean
	//*Updated on 		: 30/08/2017
	//*Updated By 		: Sagar Ruikar //generated xml file is not in valid format, End tag was missing. updated
	//*****************************************************************************************
	public HashMap<Integer,String> RetrieveCSIXML(String strxmlFile){
		fCommonSync(5000);
		HashMap<Integer,String> FileNames = new HashMap<Integer,String>();
		String SessionID = driver.manage().getCookieNamed("SALESSESSIONID").getValue();
		xmlPath = System.getProperty("user.dir")+"\\CSI_Xmls\\"+Environment.get("ENVIRONMENT") +"\\"+Environment.get("SESSION_NAME")+"_" + SessionID + "\\";
		Environment.put("CSIXMLPATH", xmlPath);
		try {
			JSch jsch = new JSch();
			Session session;
			session = jsch.getSession(Environment.get("UNIX_LOGIN"), Environment.get("UNIX_SERVER"), 22);
			session.setProxy(new ProxyHTTP("135.28.13.11", 8080));
			System.out.println("Connected to unix server : " + Environment.get("UNIX_SERVER"));	

			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(Environment.get("UNIX_PASSWORD"));
			session.connect();

			Channel channel = session.openChannel("shell");
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(channel.getInputStream()));  
			OutputStream  toServer = channel.getOutputStream();
			channel.connect();  

			toServer.write(("cd /sites/servers/"+ Environment.get("ENVIRONMENT").toLowerCase()+ "-"+Environment.get("SESSION_NAME").toLowerCase()+"-main1/logs/csixmls \r\n").getBytes());
			toServer.flush();
			Thread.sleep(7000);
			String command = "ls -l | grep '"+ SessionID + "'";
			toServer.write((command + "\r\n").getBytes());
			toServer.flush();
			Thread.sleep(4000);

			toServer.write(("$$$$" + "\r\n").getBytes());
			toServer.flush();
			String line = "";  

			int i =1;

			while(line != null) {  
				line = fromServer.readLine();
				System.out.println(line);
				if(line.contains("$$$$")) break;


				if(line.contains("-rw-r")){
					String[] fileA = line.split(" ");
					FileNames.put(i++,fileA[fileA.length-1]);
				}
			}  
			for(Integer key : FileNames.keySet()){
				if(FileNames.get(key).contains(strxmlFile)){
					command = "clear";
					line = "";
					toServer.write((command + "\r\n").getBytes());
					toServer.flush();
					Thread.sleep(1000);

					//System.out.println(FileNames.get(key));
					command = "cat '" + FileNames.get(key)+ "'";
					toServer.write((command + "\r\n").getBytes());
					toServer.flush();
					Thread.sleep(1000);
					toServer.write(("$$$$" + "\r\n").getBytes());
					toServer.flush();
					Thread.sleep(1000);

					while(line != null) {  

						line = fromServer.readLine();
						System.out.println(line);
						String content = "";

						if(line.toLowerCase().contains("soap-env"))
						{
							if(line.contains("[atg")){
								String[] xmlContent = line.trim().split("\\[atg");
								content = xmlContent[0];
							}else{
								StringBuilder builder = new StringBuilder();  
								while (!line.contains("\\[atg")){
									builder.append(line).append("\n");
									line = fromServer.readLine();
									System.out.println(line);
									if(line.contains("[atgview")) break; 
								}
								content = builder.toString() + line.trim().split("\\[atg")[0];
							}  
							System.out.println(content);

							File f = new File(xmlPath);
							if(!f.exists()){
								f.mkdirs();
							}

							java.io.FileWriter filewriter = new java.io.FileWriter(xmlPath + FileNames.get(key) );
							filewriter.write(content);
							filewriter.close();
							break;
						}
					}
				}
			}
			session.disconnect(); 
			System.out.println("Disconnected from Unix server");  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return result[index];
		fCommonSync(5000);
		return FileNames;
	}

	//**************************************************************************************************************************
	//*    NAME                 : fCommonGetWirelessCartCount
	//*    DESCRIPTION          : Get Wireless cart count to store in static variable, Compare Wireless Cart count 
	//*    CREATED RELEASE      : 9th jan 2017
	//*    LAST MODIFIED IN     : 
	//*    AUTHOR               : Vivek Viswanathan
	//*    INPUT PARAMS         : compare=false && equals==false, compare==true && equals==false, compare==false && equals==true
	//*    RETURN VALUE         : Boolean
	//*    UPDATED ON           : 
	//**************************************************************************************************************************
	public boolean fCommonGetWirelessCartCount(Boolean compare,Boolean equals){
		int cartCountNumber=0;
		String numberOfItemsInCart="id:=minicart-count";

		if(fCommonCheckObjectExistance(numberOfItemsInCart)==false){
			Reporter.fnWriteToHtmlOutput("GetWirelessCartCount", "Wireless Cart Count should be displayed", "Wireless Cart Count is missing", "Fail");
			return false;
		}

		WebElement webGetWirelessCartCount = fCommonGetObject(numberOfItemsInCart,"Cart Count");

		String strGetWirelessCartCount = webGetWirelessCartCount.getText();

		if(!(strGetWirelessCartCount.isEmpty())){
			cartCountNumber = Integer.parseInt(strGetWirelessCartCount.replaceAll("[^0-9]", ""));
			Reporter.fnWriteToHtmlOutput("GetWirelessCartCount", "Cart Count value should be returned", "Cart count is returned with value ="+cartCountNumber, "Done");
		}
		else{
			cartCountNumber = 0;
			Reporter.fnWriteToHtmlOutput("Device List Page - GetWirelessCartCount", "Wireless Cart should be empty", "Wirelss Cart is empty and value ="+cartCountNumber, "Done");		
		}
		if(compare==true && equals==false )
		{
			if(cartCountNumber>CART_COUNT)
			{
				Reporter.fnWriteToHtmlOutput("CompareCount", "First Value should be less than Second Value ", ""+CART_COUNT+" is less than "+cartCountNumber+"", "Pass");		
				CART_COUNT=cartCountNumber;
				return true;
			}
			else
			{
				Reporter.fnWriteToHtmlOutput("CompareCount", "First Value should be less than Second Value", ""+CART_COUNT+" is greater than "+cartCountNumber+"", "Fail");
				return false;
			}
		}

		if(compare==false && equals==true)
		{
			if(cartCountNumber==CART_COUNT)
			{
				Reporter.fnWriteToHtmlOutput("CompareCount", "First Value should be equal to Second Value ", ""+CART_COUNT+" is equal to "+cartCountNumber+"", "Pass");		
				CART_COUNT=cartCountNumber;
				return true;
			}
			else
			{
				Reporter.fnWriteToHtmlOutput("CompareCount", "First Value should be equal to Second Value ", ""+CART_COUNT+" is not equal to "+cartCountNumber+"", "Fail");
				return false;
			}
		}

		CART_COUNT=cartCountNumber;
		return true;
	}

	public String fCommonAdjustDecimalInString(String strNumericContent, int iDecimalPositions){
		boolean bAppend = false;
		if(strNumericContent.contains("$")){
			strNumericContent = strNumericContent.replaceAll("[a-zA-Z]", "");
			strNumericContent = strNumericContent.replaceAll("\n", "");
			strNumericContent = strNumericContent.replace("$", "");
			strNumericContent = strNumericContent.replace(",", "");
			bAppend = true;
		}
		//strNumericContent = "10.0000";
		try{
			Float flNumeric=Float.parseFloat(strNumericContent);
			DecimalFormat df = new DecimalFormat("0.00");
			df.setMaximumFractionDigits(iDecimalPositions);
			strNumericContent = df.format(flNumeric);
			if(bAppend){
				strNumericContent = "$" + strNumericContent;
			}
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
		return strNumericContent;
	}


	//**************************************************************************************************************************
	//*    NAME                 : fcommonCompareHaspMap
	//*    DESCRIPTION          : Get the two hashmap value and compare 
	//*    CREATED RELEASE      : 13th feb  2017
	//*    LAST MODIFIED IN     : 
	//*    AUTHOR               : Amit kumar
	//*    INPUT PARAMS         : hamshmap 1, hashmap 2
	//*    RETURN VALUE         : Boolean
	//*    UPDATED ON           : 
	//**************************************************************************************************************************
	public boolean fcommonCompareHaspMap(HashMap<String,String>hMap1,HashMap<String,String>hMap2){
		for(String k : hMap1.keySet()){	
			if(!hMap2.containsKey(k)){
				//System.out.println("PlanNPriceAtConfigPage1 Key  -"+ k + " not present in cart 2"+" with value   "+hMap1.get(k));  
				Reporter.fnWriteToHtmlOutput("fcommonCompareHaspMap", "PlanNPriceAtConfigPage1 Key  -"+ k + " not present in cart 2"+" with value   "+hMap1.get(k) , "" ,"VALIDATION FAIL");
			}
		}
		for(String k : hMap2.keySet()){	
			if(!hMap1.containsKey(k)){
				//System.out.println("PlanNPriceAtConfigPage2 Key  -"+ k + " not present in PlanNPriceAtConfigPage1"+" with value   "+hMap2.get(k));  
				Reporter.fnWriteToHtmlOutput("fcommonCompareHaspMap", "PlanNPriceAtConfigPage2 Key  -"+ k + " not present in PlanNPriceAtConfigPage1"+" with value   "+hMap2.get(k) , "" ,"VALIDATION FAIL");
			}
		}
		for(String k : hMap2.keySet()){
			if(!hMap2.get(k).equals(hMap1.get(k))){
				//System.out.println("VALUE : "+hMap2.get(k)+" of HASHMAP : PlanNPriceAtConfigPage2 with KEY : "+k+" is not equal to VALUE : "+hMap1.get(k)+" of HASHMAP : PlanNPriceAtConfigPage1 with KEY : "+k);                    
				Reporter.fnWriteToHtmlOutput("fcommonCompareHaspMap", "PlanNPriceAtConfigPage1 Key  -"+ k + " not present in cart 2"+" with value   "+hMap1.get(k) , "" ,"VALIDATION FAIL");
			}
		}
		if (hMap1.entrySet().containsAll(hMap2.entrySet())) {
			Reporter.fnWriteToHtmlOutput("fcommonCompareHaspMap", "hashMap one  should equals to hashmap2", "both are matched", "Pass");
		}
		else { 
			Reporter.fnWriteToHtmlOutput("fcommonCompareHaspMap", "hashMap one  should equals to hashmap2", "both are not matched", "Fail");
			return false;
		}
		return true;

	}
	//**************************************************************************************************************************
	//*    NAME                 : fcommonReplaceAttributetInXML
	//*    DESCRIPTION          : To Repace 
	//*    CREATED RELEASE      : 13th march  2017
	//*    LAST MODIFIED IN     : 
	//*    AUTHOR               : Amit kumar
	//*    INPUT PARAMS         : 
	//*    RETURN VALUE         : Boolean
	//*    UPDATED ON           : 
	//**************************************************************************************************************************	

	public boolean fcommonReplaceAttributetInXMLAndPOSTXML(String strADDRESS_LINE_1 , String strADDRESS_LINE_2 , String strZIP_CODE , String strCOUNTRY) throws SAXException, IOException, ParserConfigurationException, SOAPException{

		File xmlFile = new File(System.getProperty("user.dir") + "\\Requestxmls\\" +"\\wireline\\" + "InquireTransportProductAvailabilityServiceRequest"+".xml");
		Reader fileReader = new FileReader(xmlFile); BufferedReader bufReader = new BufferedReader(fileReader);
		StringBuilder sb = new StringBuilder(); 
		String line = bufReader.readLine();
		String  strStrinfXML = "";
		while( line != null){ sb.append(line).append("\n");
		line = bufReader.readLine();
		}
		strStrinfXML = sb.toString(); 
		System.out.println("XML to String using BufferedReader : "); 
		System.out.println(strStrinfXML);		

		strStrinfXML = strStrinfXML.replace("ADDRESS_LINE_1", strADDRESS_LINE_1);
		strStrinfXML = strStrinfXML.replace("ADDRESS_LINE_2", strADDRESS_LINE_2);
		strStrinfXML = strStrinfXML.replace("ZIP_CODE", strZIP_CODE);
		strStrinfXML = strStrinfXML.replace("COUNTRY", strCOUNTRY);	

		BufferedWriter out = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\Requestxmls\\" +"\\wireline\\" + "InquireTransportProductAvailabilityServiceRequest1"+".xml"));
		try { 
			out.write(strStrinfXML);
			System.out.println(strStrinfXML);
		} catch (IOException e) {
			e.printStackTrace();
		} 

		String api = System.getProperty("user.dir") + "\\Requestxmls\\" +"\\wireline\\" + "InquireTransportProductAvailabilityServiceRequest1"+".xml";
		SOAPMessage request = MessageFactory.newInstance().createMessage();  
		javax.xml.soap.SOAPPart soapPart = request.getSOAPPart();  
		soapPart.setContent(new StreamSource(new FileInputStream(api)));
		SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConn = soapConnFactory.createConnection();
		String url = Environment.get("REQUEST_URL").trim();
		SOAPMessage apiresponse = soapConn.call(request, url);
		System.out.println(apiresponse);	

		return true;
	}
	//**************************************************************************************************************************
	//*    NAME                 : fcommonRetrieveVisibleElementOnPage
	//*    DESCRIPTION          : To Repace 
	//*    CREATED RELEASE      : 18 April 2017
	//*    LAST MODIFIED IN     : 
	//*    AUTHOR               : Suraj Bankar
	//*    INPUT PARAMS         : strWebElement='It is the xpath of partcular object',strElementName='It is an element name,it can be blank '
	//*    RETURN VALUE         : WebElement
	//*    UPDATED ON           : 
	//**************************************************************************************************************************
	public WebElement fcommonRetrieveVisibleElementOnPage(String strWebElement,String strElementName){

		List<WebElement> arrVisbleElement = fCommonGetMultipleObjects(strWebElement, strElementName);
		WebElement webElmtVisible = null;
		if (arrVisbleElement != null){
			for(int i=0; i<arrVisbleElement.size(); i++){
				if(arrVisbleElement.get(i).getSize().height != 0){
					webElmtVisible = arrVisbleElement.get(i);
					break;
				}
				if(i==arrVisbleElement.size()-1){					
					System.out.println("Visible element not found");
					return null;
				}
			}
		}else{			
			return null;
		}	
//		Reporter.fnWriteToHtmlOutput("fcommonRetrieveVisibleElementOnPage", "Element on page should be displayed and found","Element found", "Pass");
		Reporter.fnWriteToHtmlOutput("Retrieve element: "+strElementName+" in page", "Element in page should be displayed and found","Element found", "Pass");
		return webElmtVisible;		
	}	
	//	//**************************************************************************************************************************
	//	//*    NAME                 : fcommonSendRequestXMLnValidateResponce
	//	//*    DESCRIPTION          : To send the requst XML  to URL 
	//	//*    CREATED RELEASE      : 10th march  2017
	//	//*    LAST MODIFIED IN     : 
	//	//*    AUTHOR               : Amit kumar
	//	//*    INPUT PARAMS         : 
	//	//*    RETURN VALUE         : Boolean
	//	//*    UPDATED ON           : 
	//	//**************************************************************************************************************************
	//	public boolean fcommonSendRequestXML() throws SOAPException, FileNotFoundException{
	//		String api = System.getProperty("user.dir") + "\\Requestxmls\\" +"\\wireline\\" + "InquireTransportProductAvailabilityServiceRequest"+".xml";
	//		SOAPMessage request = MessageFactory.newInstance().createMessage();  
	//		javax.xml.soap.SOAPPart soapPart = request.getSOAPPart();  
	//		soapPart.setContent(new StreamSource(new FileInputStream(api)));
	//		SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
	//		SOAPConnection soapConn = soapConnFactory.createConnection();
	//		String url = Environment.get("REQUEST_URL");
	//		SOAPMessage apiresponse = soapConn.call(request, url);
	//		System.out.println(apiresponse);
	//		return true;
	//	}

	//**************************************************************************************************************************
	//*    NAME                 : fcommonRetrieveListVisibleElementOnPage
	//*    DESCRIPTION          : Used to get Visible List Element
	//*    CREATED RELEASE      : 22/05/2017
	//*    LAST MODIFIED IN     : 
	//*    AUTHOR               : Hemant Talavdekar
	//*    INPUT PARAMS         : strWebElement = Object Xpath, strElementName = Element Name or can be blank
	//*    RETURN VALUE         : List WebElement
	//*    UPDATED ON           : 
	//**************************************************************************************************************************	

	public List<WebElement> fcommonRetrieveListVisibleElementOnPage(String strWebElement,String strElementName){

		List<WebElement> arrVisbleElement = fCommonGetMultipleObjects(strWebElement, strElementName);
		List<WebElement> lstWebElement = new ArrayList<>();

		if(arrVisbleElement!= null)
		{
			for(int i=0;i<arrVisbleElement.size();i++)
			{
				if(arrVisbleElement.get(i).isDisplayed())
				{
					lstWebElement.add(arrVisbleElement.get(i));
				}
			}
		}else{
			Reporter.fnWriteToHtmlOutput("fcommonRetrieveVisibleElementOnPage", "Element on page should be displayed and found","Element not found", "Fail");
			return null;
		}	
		Reporter.fnWriteToHtmlOutput("fcommonRetrieveVisibleElementOnPage", "Element on page should be displayed and found","Element found", "Pass");
		return lstWebElement;		
	}	



	public boolean fCommonGuiIsDisplayed(WebElement parent,String child, String objName){

		long startTime=System.currentTimeMillis();

		//Check if the Webelement is displayed                         
		boolean bIsDisplayed = false;          

		WebElement webElement = fCommonGetChildWebElementsList(parent, child, objName).get(0);
		System.out.println(webElement.getText());

		try {                               
			bIsDisplayed = webElement.isDisplayed();

		}catch(Exception e){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed " + objName, "Exception occurred for when getting isDisplayed: ","Exception: " + e, "Fail");           
			return false;           
		}

		//Validate if the element is enabled
		if (!(bIsDisplayed)) {
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed "+ objName+" - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed", "Check if object is displayed", objName+ " is not displayed", "Done");
			return false;
		}

		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed "+ objName+" - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		//Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed", "Check if object is displayed", objName+ " is displayed", "Done");
		return true;

	}

	//**************************************************************************************************************************
	//*    NAME                 : WaitForSpinnerToLoaded
	//*    DESCRIPTION          : To wait for spinner to get loaded
	//*    CREATED RELEASE      : 22/08/2017
	//*    LAST MODIFIED IN     : 
	//*    AUTHOR               : Amit Kumar
	//*    INPUT PARAMS         : 
	//*    RETURN VALUE         : 
	//*    UPDATED ON           : 
	//**************************************************************************************************************************	
	public boolean WaitForSpinnerToLoaded(){
		try {
			for(int i=0;i<=4000;i++){
				WebElement webSpinner = driver.findElement(By.xpath("//pageload-indicator[not(@class ='ng-hide')]//i[@class='icon-spinner']"));
				//				WebElement webSpinner = fCommonGetObject("xpath:=//pageload-indicator[not(@class ='ng-hide')]//i[@class='icon-spinner']","Spinner");
				if(!(webSpinner==null)){
					if(!webSpinner.isEnabled()){						
						System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - WebElement found successful - has delayed with --> "+(System.currentTimeMillis())+"ms");
						break;
					}
					else{
						System.out.println("Holds because of spinner");
						i++;
					}
				}	
				else{
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "checkPageContainsText - WebElement found successful - has delayed with --> "+(System.currentTimeMillis())+"ms");
					break;
				}
			}
		} catch (Exception e) {

		}
		return true;
	}


	//*****************************************************************************************
	//* Name            : fCommonGuiIsDisplayed
	//* Description     : Function to validate static content
	//* CREATED RELEASE : 1707
	//* LAST MODIFIED IN :1707
	//* Author          : Meena Panchanathesewaran
	//* Return Values   : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonGuiIsDisplayed(String webElmtProp, String objName, boolean isreport){

		long startTime=System.currentTimeMillis();

		//Get WebElement 
		WebElement webElement = fCommonGetObject(webElmtProp,objName); 
		if (webElement==null){
			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed", "Check Object Non Existance","Object is null", "Fail");
			return false;
		}

		boolean ret = fCommonGuiIsDisplayed(webElement,objName);
		System.out.println(webElement.getText());
		System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed - return "+ret+" -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
		Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGuiIsDisplayed", "Check Object Non Existance of" +objName ,objName+"is displayed", "Pass");

		return ret;

	}
		
		
		//*****************************************************************************************
		//* Name            : fCommonGetXMLNodes
		//* Description     : Retrives all xml nodes for a xml path
		//* CREATED RELEASE : 1708
		//* LAST MODIFIED IN :
		//* Author          : Sagar Ruikar
		//* Input Params    : Map<Integer, String>
		//* Return Values   : NodeList Object
		//*****************************************************************************************
//		public NodeList fCommonGetXMLNodes(Map<Integer, String> hashmap , String xmlfileName , String tagNAme, String textContent){
//			fCommonSync(5000);
//			String SessionID = driver.manage().getCookieNamed("SALESSESSIONID").getValue();
//			xmlPath = System.getProperty("user.dir")+"\\CSI_Xmls\\"+Environment.get("ENVIRONMENT") +"\\"+Environment.get("SESSION_NAME")+"_" + SessionID + "\\";
//			File xmlFile= null; 
//			String filename ="";
//			
//			for(Integer key : hashmap.keySet()){
//				if(hashmap.get(key).contains(xmlfileName)){
//					filename = hashmap.get(key);
//					break;
//				}
//			}
//			filename = xmlPath + filename;
//			xmlFile = new File(filename);
//			long startTime=System.currentTimeMillis();
//			try{
//				
//				//Create Document Object
//				DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
//				DocumentBuilder docBuilder = dbFac.newDocumentBuilder();
//				Document xmldoc = docBuilder.parse(xmlFile);
//				xmldoc.getDocumentElement().normalize();
//
//
//				NodeList objNodeList = xmldoc.getElementsByTagName(tagNAme);
//
//			for (int i = 0; i < objNodeList.getLength(); i++) {
//				if (objNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
//					if (!textContent.isEmpty()) {
//						if (objNodeList.item(i).getTextContent().equalsIgnoreCase(textContent)) {
//							return objNodeList;
//						}
//
//						if (i == objNodeList.getLength() - 1) {
//							return null;
//						}
//					} else {
//						System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetXMLNodes - return objNodeList -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//						return objNodeList;
//					}
//
//				}
//			}
//			return null;         
//			}catch(Exception e){
//				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetXMLNodes - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetXMLNodes", "Exception occurred","Exception :" + e, "Fail");         
//				return null;        
//			}   
//		}

		//*****************************************************************************************
		//* Name            : fCommonGetXMLNodes
		//* Description     : Retrives all xml nodes for a xml path
		//* CREATED RELEASE : 1708
		//* LAST MODIFIED IN :
		//* Author          : Sagar Ruikar
		//* Input Params    : Map<Integer, String>
		//* Return Values   : NodeList Object
		//*****************************************************************************************		

//		public boolean fCommonGVerifyXMLNodesAvailibity(NodeList nodeList , String tagname, String tagValue ,String nxtTag, String expectedValue)
//		{
//			long startTime=System.currentTimeMillis();
//			try{
//				for (int i = 0; i < nodeList.getLength(); i++) {
//					if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
//						org.w3c.dom.Node currentNode = (org.w3c.dom.Node) nodeList.item(i) ;
//						Element eElement = (Element) currentNode;
//						if(eElement.getElementsByTagName(tagname).item(0).getTextContent().equalsIgnoreCase(tagValue) && eElement.getElementsByTagName(nxtTag).item(0).getTextContent().equalsIgnoreCase(expectedValue))
//						{
//							System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGVerifyXMLNodesAvailibity - return "+"fail"+" -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//							Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGVerifyXMLNodesAvailibity", "Check tagName " +tagname , "having expected value " + tagname, "Pass");
//							return true;
//						}
//					}
//				}
//			}catch(Exception e){
//				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGVerifyXMLNodesAvailibity - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
//				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGVerifyXMLNodesAvailibity", "Exception occurred","Exception :" + e, "Fail");         
//				return false;        
//			}
//			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGVerifyXMLNodesAvailibity","Check tagName " +tagname , "not found expected value " + tagname, "Fail");         
//			return false; 
//		}


		//*****************************************************************************************
		//* Name            : fCommonGetText
		//* Description     : Get object text 
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN :
		//* Author          : Zachi Gahari
		//* Input Params    : Object description
		//* Modified date   : 8-Apr-2018
		//* Return Values   : String - object's text
		//*****************************************************************************************
		public String fCommonGetText(String webElmtProp, String elmntName, int timeOut){
			int tmpWait = WAIT_FOR_ELEMENT;
			WAIT_FOR_ELEMENT = timeOut;
			WebElement webElement = null;
			String text = null;
			
			long startTime = System.currentTimeMillis();
			webElement = fCommonGetObject(webElmtProp, elmntName);
			WAIT_FOR_ELEMENT = tmpWait;  // Restore default value

			if(webElement!=null){
				text = webElement.getText();
				System.out.println(">>>>>>>  " + elmntName + " Text - " + text);
//				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetText", "Get object text ", elmntName + ": Text - " + text, "Pass");
				Reporter.fnWriteToHtmlOutput("Get element: "+elmntName+" content", "Element: "+elmntName+" content is fetched", elmntName + ": Text - " + text, "Pass");
			}else{
				System.out.println(">>>>>>>  " + elmntName + " doesn't exist (returned null) after " + ((System.currentTimeMillis()-startTime)/1000) + " seconds");
//				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetText", "Get object text ", elmntName + " doesn't exist (returned null) after " + ((System.currentTimeMillis()-startTime)/1000) + " seconds", "Fail"); 
				Reporter.fnWriteToHtmlOutput("Get element: "+elmntName+" content", "Element: "+elmntName+" content is fetched", elmntName + " doesn't exist (returned null) after " + ((System.currentTimeMillis()-startTime)/1000) + " seconds", "Fail");
			}

			return text;
		}		

		
		//*****************************************************************************************
				//* Name            : fCommonGetText
				//* Description     : Get object text 
				//* CREATED RELEASE : 
				//* LAST MODIFIED IN :
				//* Author          : 
				//* Input Params    : Object description
				//* Modified date   : 8-Apr-2018
				//* Return Values   : String - object's text
				//*****************************************************************************************
				public String fCommonGetText2(String webElmtProp, String elmntName, int timeOut){
					int tmpWait = WAIT_FOR_ELEMENT;
					WAIT_FOR_ELEMENT = timeOut;
					WebElement webElement = null;
					String text = null;
					
					long startTime = System.currentTimeMillis();
					webElement = fCommonGetObject(webElmtProp, elmntName);
					WAIT_FOR_ELEMENT = tmpWait;  // Restore default value

					if(webElement!=null){
						text = webElement.getText();
						System.out.println(">>>>>>>  " + elmntName + " Text - " + text);
//						Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetText", "Get object text ", elmntName + ": Text - " + text, "Pass");
						Reporter.fnWriteToHtmlOutput("Get element: "+elmntName+" content", "Element: "+elmntName+" content is fetched", elmntName + ": Text - " + text, "Pass");
					}/*else{
						System.out.println(">>>>>>>  " + elmntName + " doesn't exist (returned null) after " + ((System.currentTimeMillis()-startTime)/1000) + " seconds");
//						Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetText", "Get object text ", elmntName + " doesn't exist (returned null) after " + ((System.currentTimeMillis()-startTime)/1000) + " seconds", "Fail"); 
						Reporter.fnWriteToHtmlOutput("Get element: "+elmntName+" content", "Element: "+elmntName+" content is fetched", elmntName + " doesn't exist (returned null) after " + ((System.currentTimeMillis()-startTime)/1000) + " seconds", "Fail");
					}
*/
					return text;
				}	
		//*****************************************************************************************
		//* Name            : fCommonEpandRootElement
		//* Description     : Get shadow root object  
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN :
		//* Author          : Zachi Gahari
		//* Input Params    : Object description
		//* Modified date   : 15-Aug-2019
		//* Return Values   : Shadow root object
		//*****************************************************************************************
		public WebElement fCommonEpandRootElement(WebElement webElement) {
			WebElement ele =null;
			WebElement ele1 =null;

			try {
//				WebElement ele1 = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot",webElement);
				JavascriptExecutor jsExecuter = (JavascriptExecutor)driver;
				ele = (WebElement)(jsExecuter.executeScript("return arguments[0].shadowRoot", webElement));  
				ele1 = (WebElement)(jsExecuter.executeScript("return arguments[0].shadowRoot", webElement));  
				System.out.println(jsExecuter.executeScript("return arguments[0].shadowRoot", webElement).toString());  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ele;
		}

		public WebElement getShadowRoot(WebElement element)
		{
			WebElement shadowElement = (WebElement) ((JavascriptExecutor)driver).executeScript("return arguments[0].shadowRoot", element);
			return shadowElement;
		}

		//*****************************************************************************************
		//* Name            : fCommonGetOptionFromList
		//* Description     : Get option from list
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : Feb 3, 2020
		//* Parameters      : sSelectionMethod (Element property, Element name)
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : String
		//*****************************************************************************************
		public String fCommonGetOptionFromList(String webElmtProp, String strElmtName){

			String options = "";
			int startFrom = 0; 
			int retryCount=0;
			long startTime=System.currentTimeMillis();
			JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;

			//Get WebElement
			WebElement objList = fCommonGetObject(webElmtProp,strElmtName);
			if (objList == null){   
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetOptionFromList", "Unable to locate object: " + strElmtName, strElmtName+ " is null", "Fail");
				return null;
			}

			int y = objList.getLocation().getY()-400;
			String script = "window.scrollBy(0, " + y + ")";
			jsExecutor.executeScript("window.scrollTo(0, 0)");
			jsExecutor.executeScript(script);

			try {
				Select select = new Select(objList);
				List<WebElement> element = select.getOptions();
				if(element.get(0).getText().trim().equalsIgnoreCase("Please Select"))
					startFrom = 1; 
				
				while(element.size()==startFrom && retryCount<10){ 
					fCommonSync(1000);
					element = select.getOptions();
					retryCount++;
				}
				
				if(element.size()==startFrom && retryCount==10){
					System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetOptionFromList", "Get option from list", "Failed to get the options from the list " + strElmtName, "Fail");
					return null;
				}

				System.out.println("Number of options in the list: " + element.size());

				for (int j = startFrom; j < element.size(); j++) {
					options = options + "; " + element.get(j).getText().trim();
				}
				
				options = options.substring(1); // Remove the first ';'

			}catch (Exception e) {
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetOptionFromList - return false - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "fCommonGetOptionFromList", "Get option from list", "Exception occured = " +e, "Fail");
				return null;
			}


			System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonGetOptionFromList - return true -  has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + strElmtName, strElmtName+ " contains those options: ", options, "Pass");
			return options;

		} 


		//*****************************************************************************************
		//* Name            : sendKeys
		//* Description     : Send keyboard kes to screen
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : Feb 16, 2020
		//* Parameters      : sendKeys (String/Key, value)
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : String
		//*****************************************************************************************
		public void sendKeys(String type, String value){
			try {
//				activateScreen();
				Robot robot = new Robot();
				switch(type.toUpperCase()){
				case "KEY":
					switch(value.toUpperCase()){
					case "ENTER":
						robot.keyPress(KeyEvent.VK_ENTER);
						fCommonSync(1000);
						robot.keyRelease(KeyEvent.VK_ENTER);
						System.out.println("Clicking bottun >> " + value);
						break;
					}
					break;
				case "STRING":
					Clipboard clipboard; 
					StringSelection stringSelection;
					stringSelection = new StringSelection(value);
					clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, stringSelection);
					System.out.println("Typing to screen >> " + value);
					fCommonSync(2000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_V);		
					fCommonSync(1000);
					robot.keyRelease(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//*****************************************************************************************
		//* Name            : connectToServer
		//* Description     : Connect to ssh2 server 
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : Jul 21, 2020
		//* Parameters      : connectToServer (server, user, password)
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : session
		//*****************************************************************************************
		public Session connectToServer(String server, String user, String password){
	        Session session = null;
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
	        JSch jsch = new JSch();

	        try {
				session = jsch.getSession(user, server);
				session.setConfig(config);
				session.setPassword(password);
		        session.connect();
		        
			} catch (Exception e) {
				e.printStackTrace();
				Reporter.fnWriteToHtmlOutput("Connect To Server", "Connection should establish to: " + user + "@" + server, "Exception :" + e, "Fail");
				return session;
			}
	        
	        Reporter.fnWriteToHtmlOutput("Connect To Server", "Connection establish to: " + user + "@" + server, "Successful", "Pass");
			return session;
		}		
		
		//*****************************************************************************************
		//* Name            : ftpFile
		//* Description     : File transfer between server and local machine 
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : Jul 21, 2020
		//* Parameters      : ftpFile( session,  source,  target,  type)
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : true/false
		//*****************************************************************************************
		public boolean ftpFile(Session conn, String source, String target, String type){
			int mode = ChannelSftp.OVERWRITE;
			try{     
				ChannelSftp sftpChannel = (ChannelSftp) conn.openChannel("sftp");
				sftpChannel.connect();

				switch (type.toUpperCase()){
				case "PUT":
					sftpChannel.put(source, target, mode); 
					break;
				case "GET":
					sftpChannel.get(source, target, null, mode); 
					break;
				default:
					Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "connectToServer", "Copy file: " + source + " to " + target, "Transfer type (put/get) hasn't provided", "Fail");
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "connectToServer", "Copy file: " + source + " to " + target, "Exception :" + e, "Fail");
				return false;
			}

			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "connectToServer", "Copy file: " + source + " to " + target, "Successful", "Pass");
			return true;
		}		
			
		//*****************************************************************************************
		//* Name            : runUnixCommand
		//* Description     : Run command on Unix server 
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : Jul 22, 2020
		//* Parameters      : runUnixCommand(session, command)
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : true/false
		//*****************************************************************************************
		public String runUnixCommand(Session session, String command){
			ChannelExec channel = null;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			try {
				channel = (ChannelExec)session.openChannel("exec");

				channel.setCommand(command.concat("\n"));
				System.out.println("Executing: " + command.concat("\n"));
				channel.connect();
				channel.setOutputStream(baos);	
				fCommonSync(1000);
				System.out.println(baos.toString());
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "runUnixCommand", "Executed command: " + command, "Successful", "Done");
				return baos.toString();
			} catch (Exception e) {
				e.printStackTrace();
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "runUnixCommand", "Failed to execute command: " + command, "Exception :" + e, "Fail");
				return null;
			}finally{
				channel.disconnect();
			}
		}		
		
		
		//*****************************************************************************************
		//* Name            : runUnixCommand_shell
		//* Description     : Run command on Unix server using 'shell' option
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : Dec 12, 2020
		//* Parameters      : runUnixCommand_shell(session, command)
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : true/false
		//*****************************************************************************************
		public String runUnixCommand_shell(Session session, String command){
			String line = ""; 
			String output = ""; 

			try {

				Channel channel = session.openChannel("shell");
				BufferedReader fromServer = new BufferedReader(new InputStreamReader(channel.getInputStream()));  
				OutputStream  toServer = channel.getOutputStream();
				channel.connect();  

				toServer.write((command + "\n").getBytes());
				toServer.flush();
				Thread.sleep(4000);

				toServer.write(("Done \n").getBytes());
				toServer.flush();

				while(line != null) {  
					line = fromServer.readLine();
					System.out.println(line);
					if (line.contains("==>"))
						break;
				}  

				while(!line.contains("Done")) {  
					line = fromServer.readLine();
					System.out.println(line);
					output = output + line + "\r\n" ;
				}  
			} catch (Exception e) {
				e.printStackTrace();
			}

			return output;
		}
		
		//*****************************************************************************************
		//* Name            : startDemon
		//* Description     : Run command on Unix server using 'shell' option
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : Dec 12, 2020
		//* Parameters      : startDemon(session, demonName, by)
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : true/false
		//*****************************************************************************************
		public String startDemon(Session session, String demonName, String by){
			String line = "";

			try{
				Channel channel = session.openChannel("shell");
				BufferedReader outTerminal = new BufferedReader(new InputStreamReader(channel.getInputStream()));  
				OutputStream  inTerminal = channel.getOutputStream();
				channel.connect();  

				// Clear the terminal output
				inTerminal.write(("echo\n").getBytes());
				inTerminal.flush();

				while (true){
					line = outTerminal.readLine();
					System.out.println(line);
					if(line.contains("==>"))
						break;
				}  

				// Run job 
				inTerminal.write(("runjobs " + demonName + " " + by + " \n").getBytes());
				inTerminal.flush();

				while(true) {  
					line = outTerminal.readLine();
					if(line.contains("Hit RETURN key")){
						break;
					}
					System.out.println(line);
				}  

			}catch(Exception e){
				e.printStackTrace();
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "runUnixCommand", "Failed to run demon: " + demonName + " " + by , "Exception :" + e, "Fail");
				return "Failed to run job";
			}

			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "runJob", "Run demon: " + demonName + " " + by , "Successful", "Done");
			return line;
		}	

		public boolean verifyDeamoIsRunning(Session session, String demon, String expString){   //  Hasn't tested!!!)
			String line = "";

			try{
				Channel channel = session.openChannel("shell");
				BufferedReader outTerminal = new BufferedReader(new InputStreamReader(channel.getInputStream()));  
				OutputStream  inTerminal = channel.getOutputStream();
				channel.connect();  

				// Clear the terminal output
				inTerminal.write(("echo\n").getBytes());
				inTerminal.flush();

				while (true){
					line = outTerminal.readLine();
					System.out.println(line);
					if(line.contains("==>"))
						break;
				}  

				// Run job 
				inTerminal.write(("psu -f | grep " + demon + " ; echo \"Done\" \n").getBytes());
				inTerminal.flush();

				while(true) {  
					line = outTerminal.readLine();
					if(line.contains(expString)){
						Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "verifyDeamoIsRunning", demon +" is runnig: " , "True", "True");
						return true;
					}else if (line.equalsIgnoreCase("Done")){
						Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "verifyDeamoIsRunning", demon +" is runnig: " , "True", "false");
						return false;
					}
					System.out.println(line);
				}  
			}catch(Exception e){
				e.printStackTrace();
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "verifyDeamoIsRunning", "Failed to check status for: " + demon , "Exception :" + e, "Fail");
				return false;
			}

		}	

		
		//*****************************************************************************************
		//* Name            : runJob
		//* Description     : Run JOB on Unix server 
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : Jul 26, 2020
		//* Parameters      : runUnixCommand(session, jobName,  by)
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : true/false
		//*****************************************************************************************
		public String runJob(Session session, String jobName, String by){
			String line = "";

			try{
				Channel channel = session.openChannel("shell");
				BufferedReader outTerminal = new BufferedReader(new InputStreamReader(channel.getInputStream()));  
				OutputStream  inTerminal = channel.getOutputStream();
				channel.connect();  

				// Clear the terminal output
				inTerminal.write(("echo\n").getBytes());
				inTerminal.flush();

				while (true){
					line = outTerminal.readLine();
					System.out.println(line);
					if(line.contains("==>"))
						break;
				}  

				// Run job 
				inTerminal.write(("runjobs " + jobName + " " + by + " \n").getBytes());
				inTerminal.flush();

				while(true) {  
					line = outTerminal.readLine();
					if(line.contains("Hit RETURN key")){
						inTerminal.write(("\n").getBytes());
						inTerminal.flush();
						while(true) {  
							line = outTerminal.readLine();
							System.out.println(line);
							if(line.contains("Operational Job ended"))
								break;
						}  
						break;
					}
					System.out.println(line);
				}  

			}catch(Exception e){
				e.printStackTrace();
				Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "runUnixCommand", "Failed to run job: " + jobName + " " + by , "Exception :" + e, "Fail");
				return "Failed to run job";
			}

			Reporter.fnWriteToHtmlOutput(fGetThreadClassNameAndMethod() + "--" + "runJob", "Run job: " + jobName + " " + by , "Successful", "Done");
			return line;
		}	

		public boolean fCommonOpenLinkInNewtab(String link){
			try {	
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("(window.open('"+link+"' , '_blank'));");
				Reporter.fnWriteToHtmlOutput("Open new link in new tab", link, "Successfull", "Pass");
			} catch (Exception e) {
				Reporter.fnWriteToHtmlOutput("Open new link in new tab", link, "Failed", "Fail");
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		//*****************************************************************************************
		//* Name            : fCommonSpinnerSync
		//* Description     : Wait for spinner to disappear
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : Apr 4, 2021
		//* Parameters      : fCommonSpinnerSync(int timeOut)
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : true/false
		//*****************************************************************************************
		public boolean fCommonSpinnerSync(int timeOut){
			String imgSpinner="xpath:=//*[@class='loading-dots']";
			try {
				for(int i=1 ; i<timeOut ; i++){
					if(fCommonIsDisplayed(imgSpinner, "Spinner object", 1) == false) 
						return true;
					fCommonSync(1000);
				}
			} catch (Exception  e) {
				e.printStackTrace();
			}
			
			Reporter.fnWriteToHtmlOutput("Wait for spinner to disappear", "Application hanged for more than " + timeOut + " seconds", "", "Done");
			return false;
		}

		//*****************************************************************************************
		//* Name            : generateCreditCardToken
		//* Description     : Generate Credit Card Token on Unix server using 'shell' option
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : Apr 8, 2021
		//* Parameters      : generateCreditCardToken(cardNumber)
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : true/false
		//*****************************************************************************************
		public String generateCreditCardToken(String cardNumber){
			String server = Environment.get("ENS_SERVER");
			String user = Environment.get("ENS_USER");
			String pass = Environment.get("ENS_PWD");
			String path = Environment.get("TOKENIZER_PATH");; 
			String command = Environment.get("TOKENIZER_NAME");; 
			Session session;
			String line = "";

			session = connectToServer(server, user, pass);

			try{
				Channel channel = session.openChannel("shell");
				BufferedReader outTerminal = new BufferedReader(new InputStreamReader(channel.getInputStream()));  
				OutputStream  inTerminal = channel.getOutputStream();
				channel.connect();  

				// Clear the terminal output
				inTerminal.write(("cd " + path + "\n").getBytes());
				inTerminal.flush();

				while (true){
					line = outTerminal.readLine();
					System.out.println(line);
					if(line.contains("==>"))
						break;
				}  

				// Run the command 
				inTerminal.write((command + " \n").getBytes());
				inTerminal.flush();

				while(true) {  
					line = outTerminal.readLine();
					System.out.println(line);
					if(line.contains("Enter CC number you want to tokenize")){
						break;
					}
				}  

				// Provide the credit card number 
				inTerminal.write((cardNumber + " \n").getBytes());
				inTerminal.flush();

				while(true) {  
					line = outTerminal.readLine();
					System.out.println(line);
					if(line.contains("\"token\""))
						break;
					else if(line.contains("Thanks for using this Script")){
						Reporter.fnWriteToHtmlOutput("Generate Credit Card Token", "Token for CC #: " + cardNumber+" is generated successfully", "Could not generate token. Check console logs", "VALIDATION FAIL");
						return null;
					}
				}  

			}catch(Exception e){
				e.printStackTrace();
				Reporter.fnWriteToHtmlOutput("Generate Credit Card Token", "Token for CC #: " + cardNumber+" is generated successfully", "Exception :" + e.getLocalizedMessage(), "VALIDATION FAIL");
				return null;
			}

			Reporter.fnWriteToHtmlOutput("Generate Credit Card Token", "Token for CC #: " + cardNumber+" is generated successfully", "Token: "+line.split("\"")[3] , "Done");
			return line.split("\"")[3];
		}	

		//*****************************************************************************************
		//* Name            : getRelease
		//* Description     : Get current release 
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : Feb 21, 2021
		//* Parameters      : 
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : String
		//*****************************************************************************************
		public String getRelease(){
			String release;

			try{
				release=System.getProperty("Release").toUpperCase().trim();
			}catch(NullPointerException e){
				release=Paths.get(System.getProperty("user.dir")).getFileName().toString().replaceAll("[^0-9.]", "");
			}

			return release;
		}

		//*****************************************************************************************
		//* Name            : readCommonParameters
		//* Description     : Get Common Parameters into environment object 
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : Feb 21, 2021
		//* Parameters      : 
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : String
		//*****************************************************************************************
		public boolean readCommonParameters(String fileName){
			Fillo fillo = new Fillo();
			com.codoid.products.fillo.Connection filloCon = null;

			try{
				filloCon = fillo.getConnection(fileName);
				Recordset rsd = filloCon.executeQuery("select * from COMMON_PARAMETERS");
				while(rsd.next())
					Environment.put(rsd.getField("PARAMETER").toUpperCase().trim(), rsd.getField("VALUE").trim());
			}catch(Exception filloE){
				filloE.printStackTrace();
				return false;
			}finally{
				try{ 
					filloCon.close();
				}catch(Exception closeCon){
					closeCon.printStackTrace();
				}
			}

			return true;
		}

		//*****************************************************************************************
		//* Name            : setCalendarForExecution
		//* Description     : Set calendar before execution with requested TC 
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : Feb 21, 2021
		//* Parameters      : 
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : True/false
		//*****************************************************************************************
		public boolean setCalendarForExecution(String calendarName, String testPhase, String packageName, String release, String applicationName, String executionBrowser, HashMap<String, String> GDictionary){
			Fillo fillo = new Fillo();
			com.codoid.products.fillo.Connection filloCon=null;
			HashMap<String, String> Dictionary = null;
			Date startDate=null;
			Date endDate=null;
			Date today=new Date();
			Date date = new Date();
			SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/YYYY HH:mm:ss");
			SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
			Recordset rs=null;
			ResultSet rst=null;
			String testPhaseTemp=testPhase;
			String packageNameTemp=packageName;
			Connection Conn=new DatabaseOperations(GDictionary, Environment).connectToGrafana();
			
			try {	// Check if Jenkins execution
				System.getProperty("ExecuteSuite").trim().toUpperCase();
			} catch (Exception e2) {
				return true;
			}
			
			try {
				Statement stmt = Conn.createStatement();

				filloCon = fillo.getConnection(System.getProperty("user.dir") + "\\data" + "\\" + calendarName +".xlsx");
				filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"='x'" + " where SKIP_"+driverType+"!='END'");

				switch(System.getProperty("ExecuteSuite").trim().toUpperCase()) {
				case "FULL SUITE": 
					try{
						if(System.getProperty("Runset_Names").toString().equals("")==false){
							String runSets[]=System.getProperty("Runset_Names").toString().trim().split(",");
							for(int i=0;i<runSets.length;i++){
								try{
									rs=filloCon.executeQuery("Select ID from MAIN where RUNSET_NAME='"+runSets[i].trim()+"' and SKIP_"+driverType+"!='END'");
									while(rs.next()){
										filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+String.valueOf(Integer.valueOf(rs.getField("ID"))-1)+"'");
										filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+rs.getField("ID")+"'");
									}
								}catch(Exception e){
									if(e.getLocalizedMessage().contains("No records found"))
										System.out.println("No records found for Select ID from MAIN where RUNSET_NAME='"+runSets[i].trim()+"' and SKIP_"+driverType+"!='END'");
								}
							}
						}else
							throw new NullPointerException();
					}catch (NullPointerException e) {
						filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where " + "SKIP_"+driverType+"!='END'");
					}
					break;
				case "FAILED TEST CASES ONLY": 
					try{ 
						if(System.getProperty("Runset_Names").toString().equals("")==false){
							String runSets[]=System.getProperty("Runset_Names").toString().trim().split(",");
							try{
								for(int j=0;j<runSets.length;j++){
									rst=stmt.executeQuery("select distinct(test_case_name) from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where phase_name='"+testPhase+"' and release_name='"+release+"' and application_name='"+applicationName+"' and package_name='"+packageName+"' and progression_cr_name='"+runSets[j]+"' and browser_name='"+executionBrowser+"' and test_profile='' and execution_status='Failed' and test_case_name not in (select test_case_name from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where phase_name='"+testPhase+"' and release_name='"+release+"'and application_name='"+applicationName+"' and package_name='"+packageName+"' and progression_cr_name='"+runSets[j]+"' and browser_name='"+executionBrowser+"' and test_profile='' and execution_status='Passed')");
									while(rst.next()){
										try{
											rs=filloCon.executeQuery("Select ID from MAIN where TEST_NAME='"+rst.getString("test_case_name")+"' and RUNSET_NAME='"+runSets[j]+"'and SKIP_"+driverType+"!='END'");
											while(rs.next()){
												stmt.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+String.valueOf(Integer.valueOf(rs.getField("ID"))-1)+"'");
												stmt.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+rs.getField("ID")+"'");
											}
										}catch(Exception updateException){
											if(updateException.getLocalizedMessage().contains("No records found"))
												System.out.println("No records found for test case "+rst.getString("test_case_name")+" and runset name "+runSets[j]);
										}
									}
								}
							}catch(Exception grafanaException){}
						}else
							throw new NullPointerException();
					}catch (NullPointerException e){
						if((testPhase.toUpperCase().equals("CIT")==false && testPhase.toUpperCase().equals("UAT")==false) || testPhase.isEmpty()){
							startDate=format.parse(Environment.get("CIT_START_DATE"));
							endDate=format.parse(Environment.get("CIT_END_DATE"));
							today=format.parse(dateFormat.format(date));

							if((today.equals(startDate) || today.after(startDate)) && (today.equals(endDate) || today.before(endDate)))
								testPhaseTemp="CIT";
							else
								testPhaseTemp="UAT";

							if(testPhaseTemp.equals("CIT") && packageName.startsWith("Progression"))
								testPhaseTemp="DEV";

							packageNameTemp=packageName+"_"+testPhase;
						}
						try{
							System.out.println("Query to fetch failed TC's: select distinct(test_case_name) from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and application_name='"+applicationName+"' and package_name='"+packageNameTemp+"' and browser_name='"+executionBrowser+"' and execution_status in ('In Progress','Failed') and test_case_name not in (select test_case_name from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and application_name='"+applicationName+"' and package_name='"+packageNameTemp+"' and browser_name='"+executionBrowser+"' and execution_status='Passed') and test_case_name not in (select test_case_name from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and application_name='"+applicationName+"' and package_name='"+packageNameTemp+"' and browser_name='"+executionBrowser+"' and execution_status='Skipped')");
							rst=stmt.executeQuery("select distinct(test_case_name) from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and application_name='"+applicationName+"' and package_name='"+packageNameTemp+"' and browser_name='"+executionBrowser+"' and execution_status in ('In Progress','Failed') and test_case_name not in (select test_case_name from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and application_name='"+applicationName+"' and package_name='"+packageNameTemp+"' and browser_name='"+executionBrowser+"' and execution_status='Passed') and test_case_name not in (select test_case_name from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and application_name='"+applicationName+"' and package_name='"+packageNameTemp+"' and browser_name='"+executionBrowser+"' and execution_status='Skipped')");
							while(rst.next()){
								try{
									System.out.println("Query for selecting test case from calendar: Select ID from MAIN where TEST_NAME='"+rst.getString("test_case_name")+"' and SKIP_"+driverType+"!='END'");
									rs=filloCon.executeQuery("Select ID from MAIN where TEST_NAME='"+rst.getString("test_case_name")+"' and SKIP_"+driverType+"!='END'");
									while(rs.next()){
										filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+String.valueOf(Integer.valueOf(rs.getField("ID"))-1)+"'");
										filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+rs.getField("ID")+"'");
									}
								}catch(Exception updateException){
									if(updateException.getLocalizedMessage().contains("No records found"))
										System.out.println("No records found for test case "+rst.getString("test_case_name"));
								}
							}
						}catch(Exception grafanaException){}
					}
					break;
				case "PENDING TEST CASES ONLY": 
					if((testPhase.toUpperCase().equals("CIT")==false && testPhase.toUpperCase().equals("UAT")==false) || testPhase.isEmpty()){
						startDate=format.parse(Environment.get("CIT_START_DATE"));
						endDate=format.parse(Environment.get("CIT_END_DATE"));
						today=format.parse(dateFormat.format(date));

						if((today.equals(startDate) || today.after(startDate)) && (today.equals(endDate) || today.before(endDate)))
							testPhaseTemp="CIT";
						else
							testPhaseTemp="UAT";

						if(testPhaseTemp.equals("CIT") && packageName.startsWith("Progression"))
							testPhaseTemp="DEV";

						packageNameTemp=packageName+"_"+testPhase;
					}

					try{
						if(System.getProperty("Runset_Names").toString().equals("")==false){
							String runSets[]=System.getProperty("Runset_Names").toString().trim().split(",");
							try{
								for(int j=0;j<runSets.length;j++){
									Recordset testNames=filloCon.executeQuery("select ID,TEST_NAME from MAIN where TEST_NAME!='TEST_NAME' and RUNSET_NAME='"+runSets[j]+"' and TEST_NAME!='END'");
									while(testNames.next()){
										System.out.println("Query for verifying test case status: select count(*) as numberOfrecords from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testNames.getField("TEST_NAME")+"' and package_name='"+packageNameTemp+"' and phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and progression_cr_name='' and browser_name='"+executionBrowser+"' and application_name='"+applicationName+"'");
										rst=stmt.executeQuery("select count(*) as numberOfrecords from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testNames.getField("TEST_NAME")+"' and package_name='"+packageNameTemp+"' and phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and progression_cr_name='' and browser_name='"+executionBrowser+"' and application_name='"+applicationName+"'");
										rst.next();
										if(rst.getString("numberOfrecords").equals("0")){
											System.out.println("Updatig calendar for test case: "+testNames.getField("TEST_NAME"));
											filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+String.valueOf(Integer.valueOf(testNames.getField("ID"))-1)+"'");
											filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+testNames.getField("ID")+"'");
										}
									}
								}

							}catch(Exception grafanaException){}
						}else
							throw new NullPointerException();
					}catch(NullPointerException ne){
						try{
							Recordset testNames=filloCon.executeQuery("select ID,TEST_NAME from MAIN where TEST_NAME!='TEST_NAME' and TEST_NAME!='END'");
							while(testNames.next()){
								System.out.println("Query for verifying test case status: select count(*) as numberOfrecords from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testNames.getField("TEST_NAME")+"' and package_name='"+packageNameTemp+"' and phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and browser_name='"+executionBrowser+"' and application_name='"+applicationName+"'");
								rst=stmt.executeQuery("select count(*) as numberOfrecords from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testNames.getField("TEST_NAME")+"' and package_name='"+packageNameTemp+"' and phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and browser_name='"+executionBrowser+"' and application_name='"+applicationName+"'");
								rst.next();
								if(rst.getString("numberOfrecords").equals("0")){
									System.out.println("Updatig calendar for test case: "+testNames.getField("TEST_NAME"));
									filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+String.valueOf(Integer.valueOf(testNames.getField("ID"))-1)+"'");
									filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+testNames.getField("ID")+"'");
								}
							}
						}catch(Exception e){}
					}
					break;
				case "PENDING AND FAILED TEST CASES ONLY": 
					List<String> testCases=new ArrayList<>();

					if((testPhase.toUpperCase().equals("CIT")==false && testPhase.toUpperCase().equals("UAT")==false) || testPhase.isEmpty()){
						startDate=format.parse(Environment.get("CIT_START_DATE"));
						endDate=format.parse(Environment.get("CIT_END_DATE"));
						today=format.parse(dateFormat.format(date));

						if((today.equals(startDate) || today.after(startDate)) && (today.equals(endDate) || today.before(endDate)))
							testPhaseTemp="CIT";
						else
							testPhaseTemp="UAT";

						if(testPhaseTemp.equals("CIT") && packageName.startsWith("Progression"))
							testPhaseTemp="DEV";

						packageNameTemp=packageName+"_"+testPhase;
					}

					try{
						if(System.getProperty("Runset_Names").toString().equals("")==false){
							String runSets[]=System.getProperty("Runset_Names").toString().trim().split(",");

							try{
								for(int j=0;j<runSets.length;j++){
									Recordset testNames=filloCon.executeQuery("select ID,TEST_NAME from MAIN where TEST_NAME!='TEST_NAME' and RUNSET_NAME='"+runSets[j]+"' and TEST_NAME!='END'");
									while(testNames.next()){
										System.out.println("Query for verifying test case status: select count(*) as numberOfrecords from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testNames.getField("TEST_NAME")+"' and package_name='"+packageNameTemp+"' and phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and progression_cr_name='' and browser_name='"+executionBrowser+"' and application_name='"+applicationName+"'");
										rst=stmt.executeQuery("select count(*) as numberOfrecords from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testNames.getField("TEST_NAME")+"' and package_name='"+packageNameTemp+"' and phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and progression_cr_name='' and browser_name='"+executionBrowser+"' and application_name='"+applicationName+"'");
										rst.next();
										if(rst.getString("numberOfrecords").equals("0")){
											System.out.println("Updatig calendar for test case: "+testNames.getField("TEST_NAME"));
											filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+String.valueOf(Integer.valueOf(testNames.getField("ID"))-1)+"'");
											filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+testNames.getField("ID")+"'");
										}
									}
								}

							}catch(Exception grafanaException){}
						}else
							throw new NullPointerException();
					}catch(NullPointerException ne){
						try{
							Recordset testNames=filloCon.executeQuery("select ID,TEST_NAME from MAIN where TEST_NAME!='TEST_NAME' and TEST_NAME!='END'");
							while(testNames.next()){
								System.out.println("\nQuery for verifying test case status: select count(*) as numberOfrecords from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testNames.getField("TEST_NAME")+"' and package_name='"+packageNameTemp+"' and phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and browser_name='"+executionBrowser+"' and application_name='"+applicationName+"'");
								rst=stmt.executeQuery("select count(*) as numberOfrecords from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testNames.getField("TEST_NAME")+"' and package_name='"+packageNameTemp+"' and phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and browser_name='"+executionBrowser+"' and application_name='"+applicationName+"'");
								rst.next();
								if(rst.getString("numberOfrecords").equals("0")){
									System.out.println("\nUpdating calendar for pending test case: "+testNames.getField("TEST_NAME"));
									testCases.add(testNames.getField("TEST_NAME"));
									//							System.out.println("Added pending test case: "+testNames.getField("TEST_NAME")+" to the list");
									filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+String.valueOf(Integer.valueOf(testNames.getField("ID"))-1)+"'");
									filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+testNames.getField("ID")+"'");
								}
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					} 
					try { 
						if(System.getProperty("Runset_Names").toString().equals("")==false){
							String runSets[]=System.getProperty("Runset_Names").toString().trim().split(",");
							try{
								for(int j=0;j<runSets.length;j++){
									rst=stmt.executeQuery("select distinct(test_case_name) from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where phase_name='"+testPhase+"' and release_name='"+release+"' and application_name='"+applicationName+"' and package_name='"+packageName+"' and progression_cr_name='"+runSets[j]+"' and browser_name='"+executionBrowser+"' and test_profile='' and execution_status='Failed' and test_case_name not in (select test_case_name from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where phase_name='"+testPhase+"' and release_name='"+release+"'and application_name='"+applicationName+"' and package_name='"+packageName+"' and progression_cr_name='"+runSets[j]+"' and browser_name='"+executionBrowser+"' and test_profile='' and execution_status='Passed')");
									while(rst.next()){
										try{
											rs=filloCon.executeQuery("Select ID from MAIN where TEST_NAME='"+rst.getString("test_case_name")+"' and RUNSET_NAME='"+runSets[j]+"'and SKIP_"+driverType+"!='END'");
											while(rs.next()){
												filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+String.valueOf(Integer.valueOf(rs.getField("ID"))-1)+"'");
												filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+rs.getField("ID")+"'");
											}
										}catch(Exception updateException){
											if(updateException.getLocalizedMessage().contains("No records found"))
												System.out.println("No records found for test case "+rst.getString("test_case_name")+" and runset name "+runSets[j]);
										}
									}
								}

							}catch(Exception grafanaException){}
						}else
							throw new NullPointerException();
					} catch (NullPointerException e) {
						if((testPhase.toUpperCase().equals("CIT")==false && testPhase.toUpperCase().equals("UAT")==false) || testPhase.isEmpty()){
							startDate=format.parse(Environment.get("CIT_START_DATE"));
							endDate=format.parse(Environment.get("CIT_END_DATE"));
							today=format.parse(dateFormat.format(date));

							if((today.equals(startDate) || today.after(startDate)) && (today.equals(endDate) || today.before(endDate)))
								testPhaseTemp="CIT";
							else
								testPhaseTemp="UAT";

							if(testPhaseTemp.equals("CIT") && packageName.startsWith("Progression"))
								testPhaseTemp="DEV";

							packageNameTemp=packageName+"_"+testPhase;
						}

						try{
							Conn=new DatabaseOperations(Dictionary, Environment).connectToGrafana();
							rst=stmt.executeQuery("select distinct(test_case_name) from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and application_name='"+applicationName+"' and package_name='"+packageNameTemp+"' and browser_name='"+executionBrowser+"' and execution_status in ('In Progress','Failed') and test_case_name not in (select test_case_name from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and application_name='"+applicationName+"' and package_name='"+packageNameTemp+"' and browser_name='"+executionBrowser+"' and execution_status='Passed') and test_case_name not in (select test_case_name from grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" where phase_name='"+testPhaseTemp+"' and release_name='"+release+"' and application_name='"+applicationName+"' and package_name='"+packageNameTemp+"' and browser_name='"+executionBrowser+"' and execution_status='Skipped')");
							while(rst.next()){
								try{System.out.println("\nQuery for selecting test case from calendar: Select ID from MAIN where TEST_NAME='"+rst.getString("test_case_name")+"' and SKIP_"+driverType+"!='END'");
								rs=filloCon.executeQuery("Select ID from MAIN where TEST_NAME='"+rst.getString("test_case_name")+"' and SKIP_"+driverType+"!='END'");
								while(rs.next()){
									testCases.add(rst.getString("test_case_name"));
									System.out.println("\nUpdating calendar for failed test case: "+rst.getString("test_case_name"));
									filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+String.valueOf(Integer.valueOf(rs.getField("ID"))-1)+"'");
									filloCon.executeUpdate("Update MAIN Set " + "SKIP_"+driverType+"=''" + " where ID='"+rs.getField("ID")+"'");
								}
								}catch(Exception updateException){
									if(updateException.getLocalizedMessage().contains("No records found"))
										System.out.println("No records found for test case "+rst.getString("test_case_name"));
								}
							}
						}catch(Exception grafanaException){}
					}
					break;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				return false;
			}finally{
				filloCon.close();
				try {
					Conn.close();
				} catch (Exception e) {}
			}
			return true;
		}

		//*****************************************************************************************
		//* Name            : selectDocumentInDialog
		//* Description     : This method will select document in dialog box which opened by DEXP application 
		//* CREATED RELEASE : 
		//* LAST MODIFIED IN : 
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : May 9, 2021
		//* Parameters      : 
		//* Dictionary      : 
		//* Date Modified   : 
		//* Return Values   : True/false
		//*****************************************************************************************
		public boolean selectDocumentInDialog(String file){
			Clipboard clipboard; 
			StringSelection stringSelection;
			try {
				Robot robot = new Robot();
				System.out.println(file);
				stringSelection = new StringSelection(file);
				clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, stringSelection);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.delay(1000);
				robot.keyPress(KeyEvent.VK_V);			
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.delay(1000);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				robot.delay(1000);

				//simulate pressing enter            
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			}catch (Exception e){
				e.printStackTrace();
			}

			return true;
		}

		//*****************************************************************************************
		//* Name            : fCommonCountObjects(String objDesc, String objName)
		//* Description     : Method to get objects count
		//* CREATED RELEASE : 1210
		//* LAST MODIFIED IN :1504
		//* Author          : Zachi Gahari
		//* Updated by      : 
		//* Updated Date    : May 10, 2021
		//* Input Params    : String
		//* Return Values   : int
		//*****************************************************************************************         
		public int fCommonCountObjects(String objDesc, String objName){

			long startTime=System.currentTimeMillis();

			//Delimiters
			int noOfElements=0;
			String[] delimiters = new String[] {":="};
			String[] arrFindByValues = objDesc.split(delimiters[0]);
			//Get Findby and Value
			String FindBy, val;
			if(arrFindByValues.length==2){
				FindBy = arrFindByValues[0];
				val = arrFindByValues[1];           
			}else{
				System.out.println(fGetThreadClassNameAndMethod() + "--" + "fCommonCountObjects for object " + objDesc + "  - return null - has delayed with --> "+(System.currentTimeMillis()-startTime)+"ms");
				Reporter.fnWriteToHtmlOutput("fCommonCountObjects", "Count element on screen","Element description is not valid: " + objDesc, "Fail");
				return -1;
			}

			WebDriverWait Wait=new WebDriverWait(driver, WAIT_FOR_MULTIPLE_ELEMENTS);
			int intcount = 1;   
			String strElement = FindBy.toLowerCase();

			while (intcount <= 2){                  
				try{
					driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
					//Handle all FindBy cases              
					if (strElement.equalsIgnoreCase("linktext")){
						List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(val)));
						noOfElements=elements.size();
						return noOfElements;
					}
					else if (strElement.equalsIgnoreCase("xpath")){
						List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(val)));
						noOfElements=elements.size();
						return noOfElements;
					}
					else if (strElement.equalsIgnoreCase("name")){
						List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(val)));
						noOfElements=elements.size();
						return noOfElements;
					}
					else if (strElement.equalsIgnoreCase("id")){
						List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(val)));
						noOfElements=elements.size();
						return noOfElements;
					}
					else if (strElement.equalsIgnoreCase("classname")){
						List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(val)));
						noOfElements=elements.size();
						return noOfElements;
					}
					else if (strElement.equalsIgnoreCase("cssselector")){
						List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(val)));
						noOfElements=elements.size();
						return noOfElements;
					}   
					else if (strElement.equalsIgnoreCase("partialLinkText")){
						List<WebElement> elements= Wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.partialLinkText(val)));
						noOfElements=elements.size();
						return noOfElements;                        
					}
					else{
						Reporter.fnWriteToHtmlOutput("fCommonCountObjects", "Elements located using property name :" + FindBy,"Property name specified for element " + objName+" in page : "+Thread.currentThread().getStackTrace()[2].getClassName().replaceAll("^.*\\.", "") + " is invalid","Fail");
						return -1;
					}                       
				}catch(Exception e){
					if (intcount == 2){
						e.printStackTrace();
						return -1;
					}                       
					intcount = intcount + 1;
				}
				finally{
					driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS); //reset implicitlyWait
				}
			}
			
			return -1;
		}




}