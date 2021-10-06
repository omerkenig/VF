package vfro.framework;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
//import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;

import vfro.regression.MainDriver;

//import gherkin.formatter.Reporter;

//import metro.regression.POW;

public class Reporting {

	private String g_strTestCaseReport;
	private String g_strSnapshotFolderName;
	private String g_strScriptName;       
	private String g_ScreenShotRelative;
	private String g_strTestFolderName;

	// CATO Specific
	private String strCatoFolder;
	private String strPageNameCATOReport;
	private String CatoIDReportPath;
	private static String executionDuration;
	private int g_iTotalCATOEvaluated;
	private int g_iCATOTestCase;
	private int g_iPassCATOCount;
	private int g_iFailCATOCount;
	private int g_iTotalPageCount;
	private int g_iTotalCATOCount;
	private static Date g_PageStartTimeForCATO;

	//commit
	//Counters and Integers
	private int g_iSnapshotCount;
	private int g_OperationCount;
	private int g_iPassCount;
	private static int g_iFailCount;
	private static int g_iTCPassed;
	private int g_iTestCaseNo;
	private int g_iWTTestCaseNo;
	private int g_iWTTestStepNo;
	private int g_iWTFailCount;
	private int g_iVisualTC;
	private int g_iTCNameLimit=90;
	
	private Date g_StartTime;
	private Date g_EndTime;
	private Date g_SummaryStartTime;
	private Date g_SummaryEndTime;
	
	public WebDriver driver;
	private String driverType;
	private HashMap <String, String> Dictionary = new HashMap<String, String>();
	private HashMap <String, String> Environment = new HashMap<String, String>();

	private static Date testSuiteStartTime = new Date(); 

	private static ExtentReports extent;
	private static ExtentTest test;
		
	public Reporting(WebDriver webDriver, String DT,HashMap <String, String> Dict,HashMap <String, String> Env){
		driver = webDriver;
		Dictionary = Dict;
		Environment = Env;
		driverType = DT;
	}
	
	private FileOutputStream foutStrm = null;

	//*****************************************************************************************
	//*    Name        : fnCreateSummaryReport
	//*    Description    : The function creates the summary HTML file
	//*    Author        :  Anil Agarwal
	//*    Input Params    :     None
	//*    Return Values    :     None
	//*****************************************************************************************
	public void fnCreateSummaryReport(){		
		try{
			if(Environment.get("RUN_ACCESSIBILITY").equals(null)){
				Environment.put("RUN_ACCESSIBILITY","");
			}
		}catch(Exception e){
			Environment.put("RUN_ACCESSIBILITY", "");
		}
		if(Environment.get("RUN_ACCESSIBILITY").equalsIgnoreCase("Y")){
			//Setting counter value
			g_iTCPassed = 0;
			g_iTestCaseNo = 0;
			g_iTotalPageCount = 0;
			g_SummaryStartTime = new Date();

			try 
			{ 
				//Open the test case report for writing                   
				foutStrm = new FileOutputStream(Environment.get("HTMLREPORTSPATH")+ "/SummaryReport.html", true);
				extent = new ExtentReports(Environment.get("HTMLREPORTSPATH")+ "/ExtentSummaryReport.html");			
				//Close the html file
				new PrintStream(foutStrm).println("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100% BGCOLOR=BLACK>");
				new PrintStream(foutStrm).println("<TR><TD WIDTH=90% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=ORANGE SIZE=3><B>AMDOCS</B></FONT></TD></TR><TR><TD ALIGN=CENTER BGCOLOR=ORANGE><FONT FACE=VERDANA COLOR=WHITE SIZE=3><B>Selenium Framework Reporting</B></FONT></TD></TR></TABLE><TABLE CELLPADDING=3 WIDTH=100%><TR height=30><TD WIDTH=100% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>&nbsp; Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + " on Browser " + driverType.replaceAll("[0-9]", "").trim() +" in Environment "+Environment.get("ENV_CODE")+"</B></FONT></TD></TR><TR HEIGHT=5></TR></TABLE>");  
				new PrintStream(foutStrm).println("<TABLE  CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");           
				new PrintStream(foutStrm).println("<TR COLS=6 BGCOLOR=ORANGE><TD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Sr. No.</B></FONT></TD><TD  WIDTH=45%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Page Name</B></FONT></TD><TD BGCOLOR=ORANGE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Test Duration</B></FONT></TD><TD  WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Total CATO Evaluated</B></FONT></TD><TD  WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>CATO Passed Count</B></FONT></TD><TD  WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>CATO Failed Count</B></FONT></TD></TR>");

				//Close the object
				foutStrm.close();
			} catch (IOException io) 
			{
				io.printStackTrace();
			} 

			foutStrm = null;

			// initialize the CatoCount
			g_iCATOTestCase = 0;

		}else{
			//Setting counter value
			g_iTCPassed = 0;
			g_iTestCaseNo = 0;
			g_SummaryStartTime = new Date();

			try 
			{ 
				//Open the test case report for writing                   
				foutStrm = new FileOutputStream(Environment.get("HTMLREPORTSPATH")+ "/SummaryReport.html", true);
				extent = new ExtentReports(Environment.get("HTMLREPORTSPATH")+ "/ExtentSummaryReport.html");
				//Close the html file
				new PrintStream(foutStrm).println("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100% BGCOLOR=BLACK>");
				new PrintStream(foutStrm).println("<TR><TD WIDTH=90% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=ORANGE SIZE=3><B>AMDOCS</B></FONT></TD></TR><TR><TD ALIGN=CENTER BGCOLOR=ORANGE><FONT FACE=VERDANA COLOR=WHITE SIZE=3><B>Selenium Framework Reporting</B></FONT></TD></TR></TABLE><TABLE CELLPADDING=3 WIDTH=100%><TR height=30><TD WIDTH=100% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>&nbsp; Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + " on Browser " + driverType.replaceAll("[0-9]", "").trim() +" in Environment "+Environment.get("ENV_CODE")+"</B></FONT></TD></TR><TR HEIGHT=5></TR></TABLE>");  
				new PrintStream(foutStrm).println("<TABLE  CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");           
				new PrintStream(foutStrm).println("<TR COLS=6 BGCOLOR=ORANGE><TD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>TC No.</B></FONT></TD><TD  WIDTH=50%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Test Name</B></FONT></TD><TD BGCOLOR=ORANGE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Status</B></FONT></TD><TD BGCOLOR=ORANGE WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Failure Analysis</B></FONT></TD><TD  WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Test Duration</B></FONT></TD></TR>");

				//Close the object
				foutStrm.close();
			} catch (IOException io) 
			{
				io.printStackTrace();
			} 

			foutStrm = null;

		}
	}	

	//*****************************************************************************************
	//*    Name            : fnCreateHtmlReport
	//*    Description        : The function creates the result HTML file
	//*                      In Case the file already exists, it will overwrite it and also delete the existing folders.
	//*    Author            : Anil Agarwal
	//*    Input Params    : None
	//*    Return Values    : None
	//*****************************************************************************************
	public void fnCreateHtmlReport(String strTestName) {
		if(!Dictionary.get("PAGE_ACCESSIBILITY").equalsIgnoreCase("Y") && Dictionary.get("PAGE_ACCESSIBILITY").equalsIgnoreCase("")){
			//Set the default Operation count as 0
			g_OperationCount = 0;

			//Number of default Pass and Fail cases to 0
			g_iPassCount = 0;
			g_iFailCount = 0;

			//Snapshot count to start from 0
			g_iSnapshotCount = 0;

			
			g_iTestCaseNo++;
			//script name
			g_strScriptName = g_iTestCaseNo+"_"+strTestName;
			int testCaseNumberlength = String.valueOf(g_iTestCaseNo+"_").length();
			//Set the name for the Test Case Report File
			try{if(g_strScriptName.length()>(g_iTCNameLimit))				
					g_strScriptName=g_strScriptName.substring(0,(g_iTCNameLimit-1+testCaseNumberlength)).trim();
			}catch(StringIndexOutOfBoundsException e){
//				e.printStackTrace();
//				fnWriteToHtmlOutput("Create HTMl Report", "Report Created", "Error in creating report: "+e.getLocalizedMessage(),"Fail");
			}
			
			g_strTestCaseReport = Environment.get("HTMLREPORTSPATH") + "/Report_"+ g_strScriptName + ".html";
			
			//Snap Shot folder
			g_strSnapshotFolderName = Environment.get("SNAPSHOTSFOLDER") + "/" +  g_strScriptName;
			g_ScreenShotRelative = "/Snapshots/" + g_strScriptName;

			test = extent.startTest(g_strScriptName, g_strScriptName);
			//Delete the Summary Folder if present
			File file = new File(g_strSnapshotFolderName);

			if (file.exists()) {
				file.delete();
			}

			//Make a new snapshot folder
			file.mkdir();

			//Open the report file to write the report

			try {
				foutStrm = new FileOutputStream(g_strTestCaseReport);
			} catch (FileNotFoundException fe) { //FileNotFoundException
				fe.printStackTrace();
			}

			//Close the html file
			try 
			{		
					new PrintStream(foutStrm).println("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100% BGCOLOR=ORANGE>");
					new PrintStream(foutStrm).println("<TR><TD WIDTH=90% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=ORANGE SIZE=3><B>AMDOCS</B></FONT></TD></TR><TR><TD ALIGN=CENTER BGCOLOR=ORANGE><FONT FACE=VERDANA COLOR=WHITE SIZE=3><B>Selenium Framework Reporting</B></FONT></TD></TR></TABLE><TABLE CELLPADDING=3 WIDTH=100%><TR height=30><TD WIDTH=130% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>&nbsp; Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + " on Browser " + driverType +" in Environment "+Environment.get("ENV_CODE")+"</B></FONT></TD></TR><TR HEIGHT=5></TR></TABLE>");
					new PrintStream(foutStrm).println("<TABLE BORDER=0 BORDERCOLOR=WHITE CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
					new PrintStream(foutStrm).println("<TR><TD BGCOLOR=BLACK WIDTH=20%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Test     Name:</B></FONT></TD><TD COLSPAN=6 BGCOLOR=BLACK><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>" + strTestName + "</B></FONT></TD></TR>");
				new PrintStream(foutStrm).println("</TABLE><BR/><TABLE WIDTH=100% CELLPADDING=3>");
				//		new PrintStream(foutStrm).println("<TR WIDTH=100%><TH BGCOLOR=ORANGE WIDTH=5%><FONT FACE=VERDANA SIZE=2>Step No.</FONT></TH><TH BGCOLOR=ORANGE WIDTH=28%><FONT FACE=VERDANA SIZE=2>Step Description</FONT></TH><TH BGCOLOR=ORANGE WIDTH=25%><FONT FACE=VERDANA SIZE=2>Expected Value</FONT></TH><TH BGCOLOR=ORANGE WIDTH=25%><FONT FACE=VERDANA SIZE=2>Obtained Value</FONT></TH><TH BGCOLOR=ORANGE WIDTH=7%><FONT FACE=VERDANA SIZE=2>Result</FONT></TH></TR>");
				new PrintStream(foutStrm).println("<TR WIDTH=100%><TH BGCOLOR=ORANGE WIDTH=5%><FONT FACE=VERDANA SIZE=2>Step No.</FONT></TH><TH BGCOLOR=ORANGE WIDTH=10%><FONT FACE=VERDANA SIZE=2> Timestamp   </FONT></TH><TH BGCOLOR=ORANGE WIDTH=15%><FONT FACE=VERDANA SIZE=2>Step Description</FONT></TH><TH BGCOLOR=ORANGE WIDTH=15%><FONT FACE=VERDANA SIZE=2>Expected Value</FONT></TH><TH BGCOLOR=ORANGE WIDTH=15%><FONT FACE=VERDANA SIZE=2>Obtained Value</FONT></TH><TH BGCOLOR=ORANGE WIDTH=10%><FONT FACE=VERDANA SIZE=2>Screen Shot</FONT></TH></TR>");

				foutStrm.close();
			} catch (IOException io) 
			{
				io.printStackTrace();
			}
			//Deference the file pointer
			foutStrm = null;

			//Get the start time of the execution
			g_StartTime = new Date();
		}else{
			if(Dictionary.get("PAGE_ACCESSIBILITY").equalsIgnoreCase("Y")){
				//Setting counter value
				//Set the default Operation count as 0
				g_OperationCount = 0;
				g_iTotalCATOCount = 0;
				//Number of default Pass and Fail cases to 0
				g_iPassCount = 0;
				g_iFailCount = 0;

				//Snapshot count to start from 0
				g_iSnapshotCount = 0;

				//script name
				g_strScriptName = strTestName;		

				//Set the name for the Test Case Report File
				g_strTestCaseReport = Environment.get("HTMLREPORTSPATH") + "/Report_" + Dictionary.get("CURRENT_RUNNING_PAGE") + ".html";
				Dictionary.put("CATO_SUMMARY_REPORT_PATH", g_strTestCaseReport);
				//Snap Shot folder
				//g_strSnapshotFolderName = Environment.get("SNAPSHOTSFOLDER") + "/" +  g_strScriptName;
				g_strTestFolderName = Environment.get("TESTFOLDER");
				//g_ScreenShotRelative = "/Snapshots/" + g_strScriptName;
				//Delete the Summary Folder if present

				test = extent.startTest(g_strScriptName, g_strScriptName);
				File file = new File(g_strTestFolderName);

				if (file.exists()) {
					file.delete();
				}

				//Make a new snapshot folder
				file.mkdir();

				//Open the report file to write the report
				try {
					foutStrm = new FileOutputStream(g_strTestCaseReport);

					//Close the html file
					new PrintStream(foutStrm).println("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100% BGCOLOR=BLACK>");
					new PrintStream(foutStrm).println("<TR><TD WIDTH=90% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=ORANGE SIZE=3><B>AMDOCS</B></FONT></TD></TR><TR><TD ALIGN=CENTER BGCOLOR=ORANGE><FONT FACE=VERDANA COLOR=WHITE SIZE=3><B>Selenium Framework Reporting</B></FONT></TD></TR></TABLE><TABLE CELLPADDING=3 WIDTH=100%><TR height=30><TD WIDTH=100% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>&nbsp; Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + " on Browser " + driverType +"</B></FONT></TD></TR><TR HEIGHT=5></TR></TABLE>");  
					new PrintStream(foutStrm).println("<TABLE BORDER=0 BORDERCOLOR=WHITE CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
					new PrintStream(foutStrm).println("<TR><TD BGCOLOR=BLACK WIDTH=20%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Page     Name:</B></FONT></TD><TD COLSPAN=6 BGCOLOR=BLACK><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>" + g_strScriptName + "</B></FONT></TD></TR><TABLE>");
					new PrintStream(foutStrm).println("<TABLE><TR><TD WIDTH=100% ALIGN=CENTER BGCOLOR=WHITE><TD></TR><TABLE>");           
					new PrintStream(foutStrm).println("<TABLE  CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");           
					new PrintStream(foutStrm).println("<TR COLS=6 BGCOLOR=ORANGE><TD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Sr No.</B></FONT></TD><TD  WIDTH=20%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>CATO Standards</B></FONT></TD><TD  WIDTH=30%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>CATO Description</B></FONT></TD><TD BGCOLOR=ORANGE WIDTH=20%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Duration</B></FONT></TD><TD BGCOLOR=ORANGE WIDTH=20%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Status</B></FONT></TD></TR>");

					//Close the object
					foutStrm.close();
					foutStrm = null;

					g_StartTime = new Date();
					g_PageStartTimeForCATO = new Date();
					//System.out.println(g_PageStartTimeForCATO);
				} catch (IOException io) 
				{
					io.printStackTrace();
				} 

				//Number of default Pass and Fail For CATO cases to 0
				g_iPassCATOCount = 0;
				g_iFailCATOCount = 0;
			}
		}
	}

	//*****************************************************************************************
	//*    Name        : fnWriteTestSummary
	//*    Description    : The function Writes the final outcome of a test case to a summary file.
	//*    Author        :  Aniket Gadre
	//*    Input Params    :     
	//*            strTestCaseName(String) - the name of the test case
	//*            strResult(String) - the result (Pass/Fail)
	//*    Return Values    :     
	//*            (Boolean) TRUE - Succeessful write
	//*                 FALSE - Report file not created
	//*****************************************************************************************
	public void fnWriteTestSummary(String strTestCaseName, String strResult, String strDuration){
		String sColor,sRowColor;

		//Close the file
		try{        
			//Open the test case report for writing                   
			foutStrm = new FileOutputStream(Environment.get("HTMLREPORTSPATH")+ "/SummaryReport.html", true);

			//Check color result
			if (strResult.toUpperCase().equals("PASSED") || strResult.toUpperCase().equals("PASS")){
				sColor = "GREEN";
				g_iTCPassed ++;
			}
			else if (strResult.toUpperCase().equals("FAILED") || strResult.toUpperCase().equals("FAIL")){
				sColor = "RED";
			}
			else{
				sColor = "ORANGE";
				
			}

//			g_iTestCaseNo++;

			if (g_iTestCaseNo % 2 == 0){sRowColor = "#EEEEEE";}
			else{sRowColor = "#D3D3D3";}

			//Write the result of Individual Test Case
//			if(g_iFailCount==0){
//				try{ new PrintStream(foutStrm).println ("<TR COLS=3 BGCOLOR=" + sRowColor + "><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iTestCaseNo + "</FONT></TD><TD  WIDTH=60%><FONT FACE=VERDANA SIZE=2>" + strTestCaseName + "</FONT></TD><TD  WIDTH=15%><A HREF='" + strTestCaseName.substring(0,(g_iTCNameLimit-1+8+String.valueOf(g_iTestCaseNo).length())).trim() + ".html'><FONT FACE=VERDANA SIZE=2 COLOR=" + sColor + "><B>" + strResult + "</B></FONT></A></TD><TD  WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + strDuration+ "</FONT></TD></TR>");
//				}catch(StringIndexOutOfBoundsException e){
//					new PrintStream(foutStrm).println ("<TR COLS=3 BGCOLOR=" + sRowColor + "><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iTestCaseNo + "</FONT></TD><TD  WIDTH=60%><FONT FACE=VERDANA SIZE=2>" + strTestCaseName + "</FONT></TD><TD  WIDTH=15%><A HREF='" + strTestCaseName + ".html'><FONT FACE=VERDANA SIZE=2 COLOR=" + sColor + "><B>" + strResult + "</B></FONT></A></TD><TD  WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + strDuration+ "</FONT></TD></TR>");
//				}
//			}else{
				try{ new PrintStream(foutStrm).println ("<TR COLS=3 BGCOLOR=" + sRowColor + "><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iTestCaseNo + "</FONT></TD><TD  WIDTH=50%><FONT FACE=VERDANA SIZE=2>" + strTestCaseName + "</FONT></TD><TD  WIDTH=15%><A HREF='" + strTestCaseName.substring(0,(g_iTCNameLimit-1+8+String.valueOf(g_iTestCaseNo).length())).trim() + ".html'><FONT FACE=VERDANA SIZE=2 COLOR=" + sColor + "><B>" + strResult + "</B></FONT></A></TD><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + Environment.get("ROOT_CAUSE")+ "</FONT></TD><TD  WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + strDuration+ "</FONT></TD></TR>");
				}catch(StringIndexOutOfBoundsException e){
					 new PrintStream(foutStrm).println ("<TR COLS=3 BGCOLOR=" + sRowColor + "><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iTestCaseNo + "</FONT></TD><TD  WIDTH=50%><FONT FACE=VERDANA SIZE=2>" + strTestCaseName + "</FONT></TD><TD  WIDTH=15%><A HREF='" + strTestCaseName + ".html'><FONT FACE=VERDANA SIZE=2 COLOR=" + sColor + "><B>" + strResult + "</B></FONT></A></TD><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + Environment.get("ROOT_CAUSE")+ "</FONT></TD><TD  WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + strDuration+ "</FONT></TD></TR>");
				}catch(Exception e){
					e.printStackTrace();
				}
//			}
			
			
			foutStrm.close();
			
			
			
//			ZipCompress zipComp = new ZipCompress();
//			zipComp.zip(sourcepath, destFile);
		}
		catch (IOException io) 
		{
			io.printStackTrace();
		}
		foutStrm = null;

	}

	//*****************************************************************************************
	//*    Name        : fnCloseHtmlReport
	//*    Description    : The function Closes the HTML file
	//*    Author        : Anil Agarwal
	//*    Input Params    :     None
	//*    Return Values    :     None
	//*****************************************************************************************
	public void fnCloseHtmlReport() {

		//Declaring variables

		String strTestCaseResult = null;

		//Open the report file to write the report
		try {
			
			foutStrm = new FileOutputStream(g_strTestCaseReport, true);

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}

		//Get the current time
		g_EndTime = new Date();

		//Fetch the time difference
		String strTimeDifference = fnTimeDiffference(g_StartTime.getTime(),g_EndTime.getTime());
		executionDuration=strTimeDifference;

		System.out.println("\nTHREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- Time taken by last method: " + strTimeDifference + "\n");

		//Close the html file
		try {		
			//Write the number of test steps passed/failed and the time which the test case took to run
			new PrintStream(foutStrm).println("<TR></TR><TR><TD BGCOLOR=BLACK WIDTH=5%></TD><TD BGCOLOR=BLACK WIDTH=28%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Time Taken : "+ strTimeDifference + "</B></FONT></TD><TD BGCOLOR=BLACK WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Pass Count : " + g_iPassCount + "</B></FONT></TD><TD BGCOLOR=BLACK WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Fail Count : " + g_iFailCount + "</b></FONT></TD><TD BGCOLOR=Black WIDTH=7%></TD><TD BGCOLOR=Black WIDTH=7%></TD></TR>");
			//Close the HTML tags
			new PrintStream(foutStrm).println("</TABLE><TABLE WIDTH=100%><TR><TD ALIGN=RIGHT><FONT FACE=VERDANA COLOR=ORANGE SIZE=1>&copy; amdocs - Integrated Customer Management</FONT></TD></TR></TABLE></BODY></HTML>");
			//Close File stream
			foutStrm.close();

		} catch (IOException io) {
			io.printStackTrace();
		}

		//Deference the file pointer
		foutStrm = null;

		//Check if test case passed or failed

		if (g_iFailCount != 0) {
			strTestCaseResult = "Fail";
		} else 
		{
			strTestCaseResult = "Pass";
		}
		extent.endTest(test);
		extent.flush();
		//Write into the Summary Report
		fnWriteTestSummary ("Report_"+ g_iTestCaseNo+"_"+Dictionary.get("TEST_NAME").replaceAll("\\r|\\n", "").trim(),strTestCaseResult,strTimeDifference);

	}


	//*****************************************************************************************
	//*    Name        : fnCloseTestSummary
	//*    Description    : The function Closes the summary file
	//*    Author        :  Aniket Gadre
	//*    Input Params    :     None
	//*    Return Values    :     None
	//*****************************************************************************************
	public void fnCloseTestSummary()
	{
		g_SummaryEndTime = new Date();
		//Fetch the time difference
		String strTimeDifference = fnTimeDiffference(g_SummaryStartTime.getTime(),g_SummaryEndTime.getTime());

		System.out.println("\nTHREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- Time taken by last test: " + strTimeDifference);
		System.out.println("\nTHREAD:" + Thread.currentThread().getId() + " -- " + driverType + 
				" -- Total time taken by test suite until now: " + fnTimeDiffference(testSuiteStartTime.getTime(), System.currentTimeMillis()) + "\n");

		//Open the Test Summary Report File
		try {    
			
			
			foutStrm = new FileOutputStream(Environment.get("HTMLREPORTSPATH")+ "/SummaryReport.html", true);
			

			new PrintStream(foutStrm).println("</TABLE><TABLE WIDTH=100%><TR>");
			new PrintStream(foutStrm).println("<TD BGCOLOR=BLACK WIDTH=10%></TD><TD BGCOLOR=BLACK WIDTH=50%><FONT FACE=VERDANA SIZE=2 COLOR=WHITE><B></B></FONT></TD><TD BGCOLOR=BLACK WIDTH=15%><FONT FACE=WINGDINGS SIZE=4>2</FONT><FONT FACE=VERDANA SIZE=2 COLOR=WHITE><B>Total Passed: " + g_iTCPassed + "</B></FONT></TD><TD BGCOLOR=BLACK WIDTH=10%><FONT FACE=VERDANA SIZE=2 COLOR=WHITE><B></B></FONT></TD><TD BGCOLOR=BLACK WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=WHITE><B>" + strTimeDifference + "</B></FONT></TD>");
			new PrintStream(foutStrm).println("</TR></TABLE>");
			new PrintStream(foutStrm).println("<TABLE WIDTH=100%><TR><TD ALIGN=RIGHT><FONT FACE=VERDANA COLOR=ORANGE SIZE=1>&copy; amdocs - Integrated Customer Management</FONT></TD></TR></TABLE></BODY></HTML>");

			
			//Close File stream
			foutStrm.close();
			if("Y".equalsIgnoreCase(Environment.get("EMAIL_NOTIFICATION"))){
				   String filePath = Environment.get("HTMLREPORTSPATH")+ "/SummaryReport.html";
				   String environment = Environment.get("ENV_CODE");
				   String className = Environment.get("CLASS_NAME");
				   SendMail.sendEmail(filePath, environment, className,"Metro by T-Mobile_"+environment+"_Automation Execution Report_"+className+".html");
			}

		} catch (Exception io) {
			io.printStackTrace();
		}
		extent.flush();
		//Deference the file pointer
		foutStrm = null;
		try{
			System.getProperty("WorkSpace").trim();
			File file=new File(System.getProperty("WorkSpace").trim()+"/ExecutionSummary.html");
			FileWriter fw=new java.io.FileWriter(file);
			
			String htmlCode="<table border='1' cellpadding='10' cellspacing='4' width='100%'>"
					+ "<tbody>"
					+"<tr >"
                    +"      <td style='width: 100px;' align=CENTER><strong>Total Test Cases</strong></td>"
                    +"      <td style='width: 100px;' align=CENTER><strong>Passed</strong></td>"
                    +"      <td style='width: 100px;' align=CENTER><strong>Failed</strong></td>"
                    +"      <td style='width: 100px;' align=CENTER><strong>Execution Duration</strong></td>"
                    +"    </tr>"
                    +"<tr >"
                    +"      <td style='width: 100px;' align=CENTER>"+vfro.regression.MainDriver.getTestNumber()+"</td>"
                    +"      <td style='width: 100px;' align=CENTER><strong><FONT color=GREEN>"+g_iTCPassed+"</FONT></strong></td>"
                    +"      <td style='width: 100px;' align=CENTER><strong><FONT color=RED>"+String.valueOf(vfro.regression.MainDriver.getTestNumber()-g_iTCPassed)+"</FONT></strong></td>"
                    +"      <td style='width: 100px;' align=CENTER>"+strTimeDifference+"</td>"
                    +"    </tr>"
                    +"</tbody>"
                    +"</table>";
                    
                    if(vfro.regression.MainDriver.getTestNumber()!=g_iTCPassed){
                    	htmlCode=htmlCode+"<br>"
                    			 + "<table border='1' cellpadding='10' cellspacing='4' width='100%'>"
            					 + "<tbody>"
                    			 +"<tr >"
                                 +"      <td style='width: 100px;' align=CENTER><strong>Total Failed Count</strong></td>"
                                 +"      <td style='width: 100px;' align=CENTER><strong>Environment Error</strong></td>"
                                 +"      <td style='width: 100px;' align=CENTER><strong>Element Not Found</strong></td>"
                                 +"      <td style='width: 100px;' align=CENTER><strong>Data Not Available</strong></td>"
                                 +"      <td style='width: 100px;' align=CENTER><strong>Script Not Found</strong></td>"
                                 +"      <td style='width: 100px;' align=CENTER><strong>Other</strong></td>"
                                 +"    </tr>"
                                 +"<tr >"
                                 +"      <td style='width: 100px;' align=CENTER><strong><FONT color=RED>"+String.valueOf(vfro.regression.MainDriver.getTestNumber()-g_iTCPassed)+"</FONT></strong></td>"
                                 +"      <td style='width: 100px;' align=CENTER><FONT color=RED>"+String.valueOf(vfro.regression.MainDriver.getEnvironmentErrorCount())+"</FONT></td>"
                                 +"      <td style='width: 100px;' align=CENTER><FONT color=RED>"+String.valueOf(vfro.regression.MainDriver.getElementNotFoundCount())+"</FONT></td>"
                                 +"      <td style='width: 100px;' align=CENTER><FONT color=RED>"+String.valueOf(vfro.regression.MainDriver.getDataNotAvailableCount())+"</FONT></td>"
                                 +"      <td style='width: 100px;' align=CENTER><FONT color=RED>"+String.valueOf(vfro.regression.MainDriver.getScriptNotFound())+"</FONT></td>"
                                 +"      <td style='width: 100px;' align=CENTER><FONT color=RED>"+String.valueOf(vfro.regression.MainDriver.getOtherCount())+"</FONT></td>"
                                 +"    </tr>"
                                 +"</tbody>"
                                 +"</table>";
                    }
                    htmlCode=htmlCode
                    +"</tbody>"
                    +"</table>";
			fw.write(htmlCode);
			fw.close();
		}catch(NullPointerException e){
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	//*****************************************************************************************
	//*    Name            : fnWriteToHtmlOutput
	//*    Description        : The function Writes output to the HTML file
	//*    Author            : Aniket Gadre
	//*    Input Params    :     
	//*                        strDescription(String) - the description of the object
	//*                        strExpectedValue(String) - the expected value
	//*                        strObtainedValue(String) - the actual/obtained value
	//*                        strResult(String) - the result (Pass/Fail)
	//*    Return Values    :     
	//*                        (Boolean) TRUE - Successful write
	//*                                  FALSE - Report file not created
	//*****************************************************************************************
	public void fnWriteToHtmlOutput(String strDescription, String strExpectedValue, String strObtainedValue, String strResult) {
        
        boolean imageSame=true;
        boolean imageCompared=false;
        String expectedMismatchCount=null;
        
        if(!Environment.get("RUN_ACCESSIBILITY").equalsIgnoreCase("Y")){
               String sStep;
               if (Dictionary.containsKey("STEP")){
                     sStep = Dictionary.get("STEP") + "<NS>" + strDescription + "<ND>" + strExpectedValue + "<ND>" + strObtainedValue + "<ND>" + strResult;
                     Dictionary.remove("STEP");
               }else{
                     sStep = strDescription + "<ND>" + strExpectedValue + "<ND>" + strObtainedValue + "<ND>" + strResult;
               }

               Dictionary.put("STEP", sStep);
               Driver.QCHashMap.put("STEP", sStep);
               //Declaring Variables
               String snapshotFilePath,snapshotRelativePath,sRowColor;

               //Open the test case report for writing
               //Open the HTML file
               //Open the report file to write the report
               try {
                     foutStrm = new FileOutputStream(g_strTestCaseReport, true);

               } catch (FileNotFoundException fe) {
                     fe.printStackTrace();
               }

               //Increment the Operation Count
               g_OperationCount = g_OperationCount + 1;

               //Row Color
               if (g_OperationCount % 2 == 0) {
                     sRowColor = "#EEEEEE";
               } else {
                     sRowColor = "#D3D3D3";
               }

               String elapsedtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()).toString() ;

               //Check if the result is Pass or Fail
               if (strResult.toUpperCase().equals("PASS")){               
                     //Increment the Pass Count
                     g_iPassCount++;
                     //Increment the snapshot count
                     g_iSnapshotCount+=1;
                     //Get the Full path of the snapshot
                     snapshotFilePath = g_strSnapshotFolderName + "/SS_" + g_iSnapshotCount + ".png";
                     snapshotRelativePath = "." + g_ScreenShotRelative + "/SS_" + g_iSnapshotCount + ".png";
                     //Capture the Snapshot
//                     fTakeScreenshot(snapshotFilePath,"P");
                     File file = new File(snapshotFilePath);
                     File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                     try {
                    	 FileUtils.copyFile(scrFile, file);
                     } catch (IOException e1) {
                    	 e1.printStackTrace();
                     }
                     int mismatchCount=0;
                     double mismatchPercentage=00.00;
                     imageCompared=false;
                     
                     if(g_iSnapshotCount>1){
                    	 try {
                    		 FileInputStream file1 = new FileInputStream(snapshotFilePath);
                    		 FileInputStream file2 = new FileInputStream(g_strSnapshotFolderName + "/SS_" + (g_iSnapshotCount-1) + ".png"); 
                    		 
                    		 BufferedImage currentImage = ImageIO.read(file1);
                    		 DataBuffer bufferFileInput1 = currentImage.getData().getDataBuffer();
                    		 BufferedImage previousImage = ImageIO.read(file2);
                    		 DataBuffer bufferFileInput2=previousImage.getData().getDataBuffer();
                    		 
                    		 if(currentImage.getWidth()==previousImage.getWidth() && currentImage.getHeight()==previousImage.getHeight()){
                    			 for(int i=0; i<bufferFileInput1.getSize();i++){
                    				 if(bufferFileInput1.getElem(i)!=bufferFileInput2.getElem(i)){
                    					 imageSame=false;
                    					 mismatchCount++;
                    				 }
                    			 }
                    			 imageCompared=true;
                    			 mismatchPercentage=(mismatchCount*100)/bufferFileInput1.getSize();
                    		}else{
                    			 imageSame=true;
                        		 mismatchCount=10;
                    		}
                    	} catch (Exception e) {
//                    		 e.printStackTrace();
                    		 imageSame=false;
                    		 mismatchCount=Integer.valueOf(Environment.get("REPORTING_MISMATCH_COOUNT"))+10;
                    	 }
                    }
//                     System.out.println("Image same: "+imageSame);
//                     System.out.println("Mismatch count: "+mismatchCount);
                     if(Environment.containsKey(MainDriver.getApplicationName()+"_REPORTING_MISMATCH_COOUNT"))
                    	 expectedMismatchCount=Environment.get(MainDriver.getApplicationName()+"_REPORTING_MISMATCH_COOUNT");
                     else
                    	 expectedMismatchCount=Environment.get("REPORTING_MISMATCH_COOUNT");
                     if(((imageSame==false && mismatchCount>Integer.valueOf(expectedMismatchCount)) || (imageCompared==false))==true){   //(((imageSame==false && mismatchCount>Integer.valueOf(Environment.get("REPORTING_MISMATCH_COOUNT"))) || (imageCompared==false))==true)
                     new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=green><B>" + g_OperationCount + "</B>" +
                                   "<TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +  elapsedtime +" </FONT></TD>" +
                                   "</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +
                                   strDescription+"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" + strExpectedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" 
                                   + strObtainedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><A HREF='" + snapshotRelativePath + "'><IMG SRC='" + snapshotRelativePath + "' alt='Screen Shot' style='width:250px;height:200px'></A></TD></TR>");
                     }else {
                    	 if(Dictionary.get("LINK_XML_FILES").toLowerCase().equals("y")){
                             new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=green><B>" + g_OperationCount +"</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +  elapsedtime +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strDescription +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" + strExpectedValue + "</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strObtainedValue +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><A HREF='file://" + Dictionary.get("XML_PATH") + "'><FONT FACE=VERDANA SIZE=2 COLOR=Green><B>" + strResult + "</B></FONT></A></TD></TR>");}
                      else   {
                             new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=green><B>" + g_OperationCount +"</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +  elapsedtime +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strDescription +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" + strExpectedValue + "</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strObtainedValue +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=Green><B>"+ strResult +"</B></FONT></TD></TR>");}
                    	 if(g_iSnapshotCount>1){
                    		 file.delete();
                    		 g_iSnapshotCount-=1; 
                    	 }
                    }
                                         
                     System.out.println("#######");
                     System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+vfro.regression.MainDriver.getTestNumber());
                     System.out.println("Step: "+g_OperationCount +" -- TimeStamp: "+elapsedtime +" -- Description: " + strDescription + " -- Expected Value: "+strExpectedValue+ " -- Actual: "+strObtainedValue);
                     System.out.println("#######");
                     test.log(LogStatus.PASS, strDescription + " | Expected - " + strExpectedValue + " | Actual - " + strObtainedValue);
                     test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(snapshotRelativePath));
               }
               else
               {
                     if (strResult.toUpperCase().equals("FAIL")){
                             //Increment the SnapShot count
                            g_iSnapshotCount++ ;

                            //Increment the Fail Count
                            g_iFailCount++;
                            //Get the Full path of the snapshot
                            snapshotFilePath = g_strSnapshotFolderName + "/SS_" + g_iSnapshotCount + ".png";
                            snapshotRelativePath = "." + g_ScreenShotRelative + "/SS_" + g_iSnapshotCount + ".png";
                            //Capture the Snapshot
                            try{
                            	 test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(snapshotRelativePath));
                                  fTakeScreenshot(snapshotRelativePath,"F");
                            }catch(Exception e){
                            	
                            }

                            new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=red><B>" + g_OperationCount + "</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +  elapsedtime +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>" + strDescription + " </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>" + strExpectedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>" + strObtainedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><A HREF='" + snapshotRelativePath + "'><IMG SRC='" + snapshotRelativePath + "' alt='Screen Shot' style='width:250px;height:200px'></A></TD></TR>");
                            System.out.println("#######");
                            System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+vfro.regression.MainDriver.getTestNumber());
                            System.out.println("Step: "+g_OperationCount +" -- TimeStamp: "+elapsedtime +" -- Description: " + strDescription + " -- Expected: "+strExpectedValue+ " -- Actual: "+strObtainedValue);
                            System.out.println("#######");
                            if(Environment.get("FAILURE_ERROR").isEmpty()==true)
                            	Environment.put("FAILURE_ERROR", strObtainedValue);
                            test.log(LogStatus.FAIL, strDescription + " | Expected - " + strExpectedValue + " | Actual - " + strObtainedValue);
                            test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(snapshotRelativePath));
                     }else if (strResult.toUpperCase().equals("DONE")){ 
                            strResult = "Pass";
                            //Write Results into the file
                            if(Dictionary.get("LINK_XML_FILES").toLowerCase().equals("y")){
                                   new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=green><B>" + g_OperationCount +"</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +  elapsedtime +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strDescription +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" + strExpectedValue + "</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strObtainedValue +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><A HREF='file://" + Dictionary.get("XML_PATH") + "'><FONT FACE=VERDANA SIZE=2 COLOR=Green><B>" + strResult + "</B></FONT></A></TD></TR>");}
                            else   {
                                   new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=green><B>" + g_OperationCount +"</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +  elapsedtime +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strDescription +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" + strExpectedValue + "</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strObtainedValue +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=Green><B>"+ strResult +"</B></FONT></TD></TR>");}
                            System.out.println("#######");
                            System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+vfro.regression.MainDriver.getTestNumber());
                            System.out.println("Step: "+g_OperationCount +" -- TimeStamp: "+elapsedtime +" -- Description: " + strDescription + " -- Expected Value: "+strExpectedValue+ " -- Actual Value: "+strObtainedValue);
                            System.out.println("#######");
                            Dictionary.put("LINK_XML_FILES","N");
                            test.log(LogStatus.PASS, strDescription + " | Expected - " + strExpectedValue + " | Actual - " + strObtainedValue);
                            ////
                     }else if(strResult.toUpperCase().equals("VALIDATION PASS")){
                            strResult = "Validation Pass";
                            if(Dictionary.get("LINK_XML_FILES").toLowerCase().equals("y")){
                                   new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=green><B>" + g_OperationCount +"</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +  elapsedtime +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strDescription +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" + strExpectedValue + "</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strObtainedValue +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><A HREF='file://" + Dictionary.get("XML_PATH") + "'><FONT FACE=VERDANA SIZE=2 COLOR=Green><B>" + strResult + "</B></FONT></A></TD></TR>");}
                            else   {
                                   new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=green><B>" + g_OperationCount +"</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +  elapsedtime +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strDescription +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" + strExpectedValue + "</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strObtainedValue +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=Green><B>"+ strResult +"</B></FONT></TD></TR>");}
                            System.out.println("#######");
                            System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+vfro.regression.MainDriver.getTestNumber());
                            System.out.println("Step: "+g_OperationCount +" -- TimeStamp: "+elapsedtime +" -- Description: " + strDescription + " -- Expected Value: "+strExpectedValue+ " -- Actual Value: "+strObtainedValue);
                            System.out.println("#######");
                            Dictionary.put("LINK_XML_FILES","N");
                            test.log(LogStatus.PASS, strDescription + " | Expected - " + strExpectedValue + " | Actual - " + strObtainedValue);

                     }else if(strResult.toUpperCase().equals("VALIDATION FAIL")){
                            strResult = "Validation Fail";
                            
                            //Increment the Fail Count
                            g_iFailCount++;
                           // g_iSnapshotCount++ ;
                            //snapshotRelativePath = "." + g_ScreenShotRelative + "/SS_" + g_iSnapshotCount + ".png";
                            if(Dictionary.get("LINK_XML_FILES").toLowerCase().equals("y")){
                                   new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=red><B>" + g_OperationCount +"</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>" +  elapsedtime +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>"+ strDescription +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>" + strExpectedValue + "</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>"+ strObtainedValue +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><A HREF='file://" + Dictionary.get("XML_PATH") + "'><FONT FACE=VERDANA SIZE=2 COLOR=red><B>" + strResult + "</B></FONT></A></TD></TR>");}
                            else   {
                                   new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=red><B>" + g_OperationCount +"</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>" +  elapsedtime +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>"+ strDescription +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>" + strExpectedValue + "</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>"+ strObtainedValue +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=red><B>"+ strResult +"</B></FONT></TD></TR>");}
                            System.out.println("#######");
                            System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+vfro.regression.MainDriver.getTestNumber());
                            System.out.println("Step: "+g_OperationCount +" -- TimeStamp: "+elapsedtime +" -- Description: " + strDescription + " -- Expected Value: "+strExpectedValue+ " -- Actual Value: "+strObtainedValue);
                            System.out.println("#######");
                            if(Environment.get("FAILURE_ERROR").isEmpty()==true)
                            	Environment.put("FAILURE_ERROR", strObtainedValue);
                            Dictionary.put("LINK_XML_FILES","N");
                            test.log(LogStatus.FAIL, strDescription + " | Expected - " + strExpectedValue + " | Actual - " + strObtainedValue);
                            //test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(snapshotRelativePath));
                     }
               }
               try
               {                    
                     //Close File stream
                     foutStrm.close();

               } catch (IOException io) {
                     io.printStackTrace();
               }
        }else{
               if(Dictionary.get("RUNNING_CATO").equals("")){
                     System.err.println("There are no CATO_ID to be run");
               }else{
                     System.err.println(Dictionary.get("RUNNING_CATO")+ " running....");
                     WriteToCatoIDReport(Dictionary.get("RUNNING_CATO"), strDescription, strExpectedValue, strObtainedValue, strResult);
               }
        }
}


	//*****************************************************************************************
	//*    Name        : fTakeScreenshot
	//*    Description    : The function takes the screenshot
	//*    Author        :  Anup Agarwal
	//*    Input Params    :     SSPath - Screenshot path
	//*    Return Values    :     None
	//*****************************************************************************************
	public void fTakeScreenshot(String SSPath,String sStatus){

		String sTakeScreenShot = Environment.get("SCREENSHOTS").toUpperCase();

		//Dina - added option to Take/Not Take screenshots, or Take screenshots per specific status "P", "F"
		if (sStatus.equals(sTakeScreenShot) || sTakeScreenShot.equals("Y") || sTakeScreenShot.equals("")) {

			try{   		
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(SSPath));
//				final Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
//				final Rectangle screenRectangle = new Rectangle(screenDimension);
//				final Robot robot = new Robot();
//				final BufferedImage screenImage = robot.createScreenCapture(screenRectangle);
//				ImageIO.write(screenImage, "PNG", new File(SSPath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//*****************************************************************************************
	//*    Name        : fnTimeDiffference
	//*    Description    : calculate Time of execution
	//*    Author        :  Anil Agarwal
	//*    Input Params    :     SSPath - Screenshot path
	//*    Return Values    :     None
	//*****************************************************************************************
	public static String fnTimeDiffference(long startTime, long endTime) {

		//Finding the difference in milliseconds
		long delta = endTime - startTime;

		//Finding number of days
		int days = (int) delta / (24 * 3600 * 1000);

		//Finding the remainder
		delta = (int) delta % (24 * 3600 * 1000);

		//Finding number of hrs
		int hrs = (int) delta / (3600 * 1000);

		//Finding the remainder
		delta = (int) delta % (3600 * 1000);

		//Finding number of minutes
		int min = (int) delta / (60 * 1000);

		//Finding the remainder
		delta = (int) delta % (60 * 1000);

		//Finding number of seconds
		int sec = (int) delta / 1000;

		//Concatenting to get time difference in the form day:hr:min:sec 
		//String strTimeDifference = days + ":" + hrs + ":" + min + ":" + sec;
		String strTimeDifference = days + "d " + hrs + "h " + min + "m " + sec + "s";
		return strTimeDifference;
	}

	//*****************************************************************************************
	//*    Name        : fnWriteThreadReport
	//*    Description    : The function Writes each Thread details.
	//*    Author        :  Anup Agarwal
	//*    Input Params    :     
	//*            int iThreadCount
	//*            String sReportFile(String)
	//*			   String sCalendar
	//*			   String sSummaryFile
	//*    Return Values    :     
	//*            Void
	//*****************************************************************************************
	public void fnWriteThreadReport(int iThreadCount, String sReportFile, String sCalendar, String sSummaryFile){
		String sRowColor;

		//Close the file
		try{        
			//Open the test case report for writing                   
			foutStrm = new FileOutputStream(sReportFile, true);

			//Set Row Color
			if (iThreadCount % 2 == 0){
				sRowColor = "#EEEEEE";
			}else{
				sRowColor = "#D3D3D3";
			}

			//Write the result of Individual Test Case
			new PrintStream(foutStrm).println ("<TR COLS=3 BGCOLOR=" + sRowColor + "><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + iThreadCount + "</FONT></TD><TD  WIDTH=35%><FONT FACE=VERDANA SIZE=2>" + driverType + "</FONT></TD><TD  WIDTH=35%><FONT FACE=VERDANA SIZE=2>" + sCalendar + "</FONT></TD><TD  WIDTH=20%><A HREF='" + sSummaryFile + "'><FONT FACE=VERDANA SIZE=2 COLOR=GREEN><B>Report</B></FONT></A></TD></TR>");

			foutStrm.close();
		}
		catch (IOException io){
			io.printStackTrace();
		}
		foutStrm = null;
	}

	//*****************************************************************************************
	//*    Name        		: fnCloseCATOHtmlReport
	//*    Description    	: The function Closes the HTML file for CATO Specific
	//*    Author        	: Kapish Kumar
	//*    Input Params    	: None
	//*    Return Values    : None
	//*****************************************************************************************
	public void fnCloseCATOHtmlReport() {

		//Declaring variables

		String strTestCaseResult = null;

		//Open the report file to write the report
		try {
			foutStrm = new FileOutputStream(strPageNameCATOReport, true);

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}

		//Get the current time
		g_EndTime = new Date();

		//Fetch the time difference
		String strTimeDifference = fnTimeDiffference(g_StartTime.getTime(),g_EndTime.getTime());

		System.out.println("\nTHREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- Time taken by last method: " + strTimeDifference + "\n");

		//Close the html file
		try {		
			//Write the number of test steps passed/failed and the time which the test case took to run
			new PrintStream(foutStrm).println("<TR></TR><TR><TD BGCOLOR=BLACK WIDTH=5%></TD><TD BGCOLOR=BLACK WIDTH=28%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Time Taken : "+ strTimeDifference + "</B></FONT></TD><TD BGCOLOR=BLACK WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Pass Count : " + g_iPassCount + "</B></FONT></TD><TD BGCOLOR=BLACK WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Fail Count : " + g_iFailCount + "</b></FONT></TD><TD BGCOLOR=Black WIDTH=7%></TD><TD BGCOLOR=Black WIDTH=7%></TD></TR>");
			//Close the HTML tags
			new PrintStream(foutStrm).println("</TABLE><TABLE WIDTH=100%><TR><TD ALIGN=RIGHT><FONT FACE=VERDANA COLOR=ORANGE SIZE=1>&copy; amdocs - Integrated Customer Management</FONT></TD></TR></TABLE></BODY></HTML>");
			//Close File stream
			foutStrm.close();
			Dictionary.put(Dictionary.get("RUNNING_CATO")+"_TIME_DURATION", strTimeDifference);
		} catch (IOException io) {
			io.printStackTrace();
		}

		//Deference the file pointer
		foutStrm = null;

		//Check if test case passed or failed

		if (g_iFailCount != 0) {
			strTestCaseResult = "Fail";
		} else 
		{
			strTestCaseResult = "Pass";
		}

		//Write into the Summary Report
		fnWriteToCATOSummary (Dictionary.get("RUNNING_CATO"),strTestCaseResult,strTimeDifference);

	}

	//*****************************************************************************************
	//*    Name        		: fnWriteToCATOSummary
	//*    Description    	: The function Writes the final outcome of Page Accessibility to a summary file.
	//*    Author        	: Kapish Kumar
	//*    Input Params    	: strTestCaseName(String) - the name of the test case     
	//*            			  strResult(String) - the result (Pass/Fail)
	//*						
	//*    Return Values    : (Boolean) TRUE - Succeessful write     
	//*              		  FALSE - Report file not created
	//*****************************************************************************************
	public void fnWriteToCATOSummary(String strCATOId, String strResult, String strDuration){
		String sColor,sRowColor;

		//Close the file
		try{        
			//Open the test case report for writing
			g_strTestCaseReport = Dictionary.get("CATO_SUMMARY_REPORT_PATH");
			foutStrm = new FileOutputStream(g_strTestCaseReport, true);

			//Check color result
			if (strResult.toUpperCase().equals("PASSED") || strResult.toUpperCase().equals("PASS")){
				sColor = "GREEN";
				g_iTCPassed ++;
				g_iPassCATOCount ++;
			}
			else if (strResult.toUpperCase().equals("FAILED") || strResult.toUpperCase().equals("FAIL")){
				sColor = "RED";
				g_iFailCATOCount ++;
			}
			else{
				sColor = "ORANGE";
			}

			g_iTotalCATOCount++;

			if (g_iTotalCATOCount % 2 == 0){sRowColor = "#EEEEEE";}
			else{sRowColor = "#D3D3D3";}
			String strRelativeCatoIDReportPath = "." + "/" + Dictionary.get("CURRENT_RUNNING_PAGE") + "/" + "Report_"+ Dictionary.get("CURRENT_RUNNING_PAGE") + "_" + Dictionary.get("RUNNING_CATO") + ".html";
			//Write the result of Individual Test Case
			new PrintStream(foutStrm).println ("<TR COLS=3 BGCOLOR=" + sRowColor + "><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iTotalCATOCount + "</FONT></TD><TD  WIDTH=20%><FONT FACE=VERDANA SIZE=2>" + strCATOId + "</FONT></TD><TD  WIDTH=30%><A HREF='" + Dictionary.get(strCATOId+"_DESCRIPTION")+"' TARGET='_BLANK'><FONT FACE=VERDANA SIZE=2>" + "Click on this link for "+ strCATOId + " description" + "</FONT></A></TD><TD  WIDTH=20%><FONT FACE=VERDANA SIZE=2>" + Dictionary.get(Dictionary.get("RUNNING_CATO")+"_TIME_DURATION")+ "</FONT></TD><TD  WIDTH=20%><A HREF='" + strRelativeCatoIDReportPath + "'><FONT FACE=VERDANA SIZE=2 COLOR=" + sColor + "><B>" + strResult + "</B></FONT></A></TD></TR>");

			foutStrm.close();
		}
		catch (IOException io) 
		{
			io.printStackTrace();
		}
		foutStrm = null;

		// empty CATO ID from Dictionary to allow other CATO ID's Execution & it avoids reporting if the page is not being evaluated for any specific CATO
		Dictionary.put("RUNNING_CATO", "");

	}

	//*****************************************************************************************
	//*    Name        		: fnCloseCATOSummary
	//*    Description    	: The function Writes the final outcome of Page Accessibility to a summary file.
	//*    Author        	: Kapish Kumar
	//*    Input Params    	: strTestCaseName(String) - the name of the test case     
	//*            			  strResult(String) - the result (Pass/Fail)
	//*						
	//*    Return Values    : (Boolean) TRUE - Succeessful write     
	//*              		  FALSE - Report file not created
	//*****************************************************************************************
	public void fnCloseCATOSummary(){
		if(Environment.get("RUN_ACCESSIBILITY").equalsIgnoreCase("Y") && !Dictionary.get("PAGE_ACCESSIBILITY").equalsIgnoreCase("")){
			try{        
				//Open the test case report for writing                   
				foutStrm = new FileOutputStream(g_strTestCaseReport, true);

				//Get the current time
				g_EndTime = new Date();

				//Fetch the time difference
				String strCATOTimeDifference = fnTimeDiffference(g_PageStartTimeForCATO.getTime(),g_EndTime.getTime());
				Dictionary.put("CATO_TIME_DIFFERENCE", strCATOTimeDifference);

				//Write the number of test steps passed/failed and the time which the test case took to run
				new PrintStream(foutStrm).println("<TR></TR><TR><TD BGCOLOR=BLACK WIDTH=10%></TD><TD BGCOLOR=BLACK WIDTH=20%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Pass Count : " + g_iPassCATOCount + "</B></FONT></TD><TD BGCOLOR=BLACK WIDTH=30%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Fail Count : " + g_iFailCATOCount + "</b></FONT></TD><TD BGCOLOR=BLACK WIDTH=20%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Total Duration : " + strCATOTimeDifference + "</b></FONT></TD><TD BGCOLOR=BLACK WIDTH=20%></TD></TR>");
				//Close the HTML tags
				new PrintStream(foutStrm).println("</TABLE><TABLE WIDTH=100%><TR><TD ALIGN=RIGHT><FONT FACE=VERDANA COLOR=ORANGE SIZE=1>&copy; amdocs - Integrated Customer Management</FONT></TD></TR></TABLE></BODY></HTML>");

				//Close File stream
				foutStrm.close();

				int intTotalCatoEvaluated = g_iPassCATOCount + g_iFailCATOCount;

				Dictionary.put("PASSED_CATO_COUNT", String.valueOf(g_iPassCATOCount).trim());
				Dictionary.put("FAILED_CATO_COUNT", String.valueOf(g_iFailCATOCount).trim());
				Dictionary.put("TOTAL_CATO_COUNT", String.valueOf(intTotalCatoEvaluated));

				// Reset all evaluated count
				//g_iPassCATOCount = 0;
				//g_iFailCATOCount = 0;
				//g_iTotalCATOCount = 0;
			}
			catch (IOException io) 
			{
				io.printStackTrace();
			}
		}
		foutStrm = null;

		//fnUpdateCATOTestCaseReport();
	}

	//*****************************************************************************************
	//*    Name        		: fnUpdateCATOTestCaseReport
	//*    Description    	: Consolidate the Tests run in the Test Suite.
	//*    Author        	: Kapish Kumar
	//*    Input Params    	: 
	//*    Return Values    : 
	//*****************************************************************************************
	public void fnUpdateCATOTestCaseReport(){
		if(Environment.get("RUN_ACCESSIBILITY").equalsIgnoreCase("Y") && !Dictionary.get("PAGE_ACCESSIBILITY").equalsIgnoreCase("")){
			String sRowColor;

			//Close the file
			try{        
				//Open the test case report for writing                   
				foutStrm = new FileOutputStream(Environment.get("HTMLREPORTSPATH")+ "/SummaryReport.html", true);

				// Total CATO Evaluated
				g_iTotalCATOEvaluated = g_iPassCATOCount+g_iFailCATOCount;

				g_iCATOTestCase++;

				if (g_iCATOTestCase % 2 == 0){sRowColor = "#EEEEEE";}
				else{sRowColor = "#D3D3D3";}
				//C:\Selenium\workspace\trunk_NewReporting\Execution\BuyFlowAccessibilityTesting\Star_Smoke_August\kapishk\CHROME1\HTML_Reports2015-10-21T12.13.04\Report_AvailabilitySuccessPage.html
				String strRelativePageReportPath = "." + "/" + "Report_"+ Dictionary.get("CURRENT_RUNNING_PAGE") + ".html";

				//Write the result of Individual Test Case
				//new PrintStream(foutStrm).println ("<TR COLS=3 BGCOLOR=" + sRowColor + "><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iCATOTestCase + "</FONT></TD><TD  WIDTH=45%><A HREF='" + g_strTestCaseReport + "'><FONT FACE=VERDANA SIZE=2><B>" + Dictionary.get("CURRENT_RUNNING_PAGE") + "</B></FONT></A></TD><TD  WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + Dictionary.get("CATO_TIME_DIFFERENCE") + "</FONT></TD><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iTotalCATOEvaluated + "</FONT></TD><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iPassCATOCount + "</FONT></TD><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iFailCATOCount + "</FONT></TD></TR>");
				new PrintStream(foutStrm).println ("<TR COLS=3 BGCOLOR=" + sRowColor + "><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iCATOTestCase + "</FONT></TD><TD  WIDTH=45%><A HREF='" + strRelativePageReportPath + "'><FONT FACE=VERDANA SIZE=2><B>" + Dictionary.get("CURRENT_RUNNING_PAGE") + "</B></FONT></A></TD><TD  WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + Dictionary.get("CATO_TIME_DIFFERENCE") + "</FONT></TD><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iTotalCATOEvaluated + "</FONT></TD><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iPassCATOCount + "</FONT></TD><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iFailCATOCount + "</FONT></TD></TR>");

				foutStrm.close();
			} catch (IOException io) {
				io.printStackTrace();
			}

			//Deference the file pointer
			foutStrm = null;

			// Reset all evaluated count
			g_iPassCATOCount = 0;
			g_iFailCATOCount = 0;
			g_iTotalCATOCount = 0;
		}
	}

	//*****************************************************************************************
	//*    Name        		: fnUpdateCATOTestCaseReport
	//*    Description    	: Consolidate the Tests run in a Test Suite.
	//*    Author        	: Kapish Kumar
	//*    Input Params    	: 
	//*    Return Values    : 
	//*****************************************************************************************
	public void fnCloseCATOTestCaseReport(){
		//Close the file
		try{
			//Open the test case report for writing                   
			foutStrm = new FileOutputStream(Environment.get("HTMLREPORTSPATH")+ "/SummaryReport.html", true);

			new PrintStream(foutStrm).println("<TR></TR><TR><TD BGCOLOR=BLACK WIDTH=10%></TD><TD BGCOLOR=BLACK WIDTH=45%><FONT FACE=VERDANA COLOR=BLACK SIZE=1>.</font></TD><TD BGCOLOR=BLACK WIDTH=15%></TD><TD BGCOLOR=BLACK WIDTH=10%></TD><TD BGCOLOR=BLACK WIDTH=10%></TD><TD BGCOLOR=BLACK WIDTH=10%></TD></TR>");
			//Close the HTML tags
			new PrintStream(foutStrm).println("</TABLE><TABLE WIDTH=100%><TR><TD ALIGN=RIGHT><FONT FACE=VERDANA COLOR=ORANGE SIZE=1>&copy; amdocs - Integrated Customer Management</FONT></TD></TR></TABLE></BODY></HTML>");

			foutStrm.close();
		} catch (IOException io) {
			io.printStackTrace();
		}

		//De-reference the file pointer
		foutStrm = null;
	}

	//*****************************************************************************************
	//*    Name        		: CreateCatoIDReports
	//*    Description    	: The function Creates CATO ID Reports
	//*    Author        	: Kapish Kumar
	//*    Input Params    	: None
	//*    Return Values    : None
	//*****************************************************************************************
	public void CreateCatoIDReports(String CATOID){
		if(Dictionary.get("PAGE_ACCESSIBILITY").equalsIgnoreCase("Y")){
			//Set the default Operation count as 0
			g_OperationCount = 0;

			//Number of default Pass and Fail cases to 0
			g_iPassCount = 0;
			g_iFailCount = 0;

			//Snapshot count to start from 0
			g_iSnapshotCount = 0;

			//script name
			g_strScriptName = Dictionary.get("TEST_NAME") + "_" + Dictionary.get("CURRENT_RUNNING_PAGE") + "_" + CATOID;		

			//Set the name for the Test Case Report File
			//g_strTestCaseReport = Environment.get("HTMLREPORTSPATH") + "/Report_" + g_strScriptName + ".html";
			//CatoIDReportPath = Environment.get("TESTFOLDER")+ "/" + "Report_" + Dictionary.get("TEST_NAME") + "_" + CATOID + ".html";
			CatoIDReportPath = Environment.get("TESTFOLDER")+ "/" + "Report_" + Dictionary.get("CURRENT_RUNNING_PAGE") + "_" + CATOID + ".html";

			//Snap Shot folder
			g_strSnapshotFolderName = Environment.get("TESTFOLDER") + "/" +  CATOID;
			g_ScreenShotRelative = "/"+CATOID;

			// to be put for getting currently running CATO Standard
			Dictionary.put("RUNNING_CATO", CATOID);
			//Delete the Summary Folder if present
			File file = new File(g_strSnapshotFolderName);

			if (file.exists()) {
				file.delete();
			}

			//Make a new snapshot folder
			file.mkdir();

			//Open the report file to write the report

			try {
				foutStrm = new FileOutputStream(CatoIDReportPath, true);
			} catch (FileNotFoundException fe) {
				fe.printStackTrace();
			}

			//Close the html file
			try 
			{		
				new PrintStream(foutStrm).println("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100% BGCOLOR=ORANGE>");
				new PrintStream(foutStrm).println("<TR><TD WIDTH=90% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=ORANGE SIZE=3><B>AMDOCS</B></FONT></TD></TR><TR><TD ALIGN=CENTER BGCOLOR=ORANGE><FONT FACE=VERDANA COLOR=WHITE SIZE=3><B>Selenium Framework Reporting</B></FONT></TD></TR></TABLE><TABLE CELLPADDING=3 WIDTH=100%><TR height=30><TD WIDTH=130% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>&nbsp; Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + " on Browser " + driverType +"</B></FONT></TD></TR><TR HEIGHT=5></TR></TABLE>");
				new PrintStream(foutStrm).println("<TABLE BORDER=0 BORDERCOLOR=WHITE CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
				new PrintStream(foutStrm).println("<TR><TD BGCOLOR=BLACK WIDTH=20%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Test     Name:</B></FONT></TD><TD COLSPAN=6 BGCOLOR=BLACK><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>" + g_strScriptName + "</B></FONT></TD></TR>");
				new PrintStream(foutStrm).println("</TABLE><BR/><TABLE WIDTH=100% CELLPADDING=3>");
				//		new PrintStream(foutStrm).println("<TR WIDTH=100%><TH BGCOLOR=ORANGE WIDTH=5%><FONT FACE=VERDANA SIZE=2>Step No.</FONT></TH><TH BGCOLOR=ORANGE WIDTH=28%><FONT FACE=VERDANA SIZE=2>Step Description</FONT></TH><TH BGCOLOR=ORANGE WIDTH=25%><FONT FACE=VERDANA SIZE=2>Expected Value</FONT></TH><TH BGCOLOR=ORANGE WIDTH=25%><FONT FACE=VERDANA SIZE=2>Obtained Value</FONT></TH><TH BGCOLOR=ORANGE WIDTH=7%><FONT FACE=VERDANA SIZE=2>Result</FONT></TH></TR>");
				new PrintStream(foutStrm).println("<TR WIDTH=100%><TH BGCOLOR=ORANGE WIDTH=5%><FONT FACE=VERDANA SIZE=2>Step No.</FONT></TH><TH BGCOLOR=ORANGE WIDTH=10%><FONT FACE=VERDANA SIZE=2> Timestamp   </FONT></TH><TH BGCOLOR=ORANGE WIDTH=15%><FONT FACE=VERDANA SIZE=2>Step Description</FONT></TH><TH BGCOLOR=ORANGE WIDTH=15%><FONT FACE=VERDANA SIZE=2>Expected Value</FONT></TH><TH BGCOLOR=ORANGE WIDTH=15%><FONT FACE=VERDANA SIZE=2>Obtained Value</FONT></TH><TH BGCOLOR=ORANGE WIDTH=10%><FONT FACE=VERDANA SIZE=2>Screen Shot</FONT></TH></TR>");

				foutStrm.close();
			} catch (IOException io) 
			{
				io.printStackTrace();
			}
			//Deference the file pointer
			foutStrm = null;

			//Get the start time of the execution
			g_StartTime = new Date();
		}
	}

	//*****************************************************************************************
	//*    Name        		: WriteToCatoIDReport
	//*    Description    	: The function Write To Cato ID Report
	//*    Author        	: Kapish Kumar
	//*    Input Params    	: None
	//*    Return Values    : None
	//*****************************************************************************************
	public void WriteToCatoIDReport(String strCATOID, String strDescription, String strExpectedValue, String strObtainedValue, String strResult){
		try{
			String sStep;
			if (Dictionary.containsKey("STEP")){
				sStep = Dictionary.get("STEP") + "<NS>" + strDescription + "<ND>" + strExpectedValue + "<ND>" + strObtainedValue + "<ND>" + strResult;
				Dictionary.remove("STEP");
			}else{
				sStep = strDescription + "<ND>" + strExpectedValue + "<ND>" + strObtainedValue + "<ND>" + strResult;
			}

			Dictionary.put("STEP", sStep);
			Driver.QCHashMap.put("STEP", sStep);
			//Declaring Variables
			String snapshotFilePath,snapshotRelativePath,sRowColor;

			strCatoFolder = Environment.get("TESTFOLDER") + "/" + strCATOID;
			strPageNameCATOReport = Environment.get("TESTFOLDER") + "/" + "Report_" + Dictionary.get("CURRENT_RUNNING_PAGE") + "_" + strCATOID + ".html";

			try {
				//new File(strCatoFolder).mkdirs();
				foutStrm = new FileOutputStream(strPageNameCATOReport, true);
			} catch (FileNotFoundException fe) {
				fe.printStackTrace();
			}

			//Increment the Operation Count
			g_OperationCount = g_OperationCount + 1;

			//Row Color
			if (g_OperationCount % 2 == 0) {
				sRowColor = "#EEEEEE";
			} else {
				sRowColor = "#D3D3D3";
			}

			String elapsedtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()).toString() ;

			//Check if the result is Pass or Fail
			if (strResult.toUpperCase().equals("PASS")){               
				//Increment the Pass Count
				g_iPassCount++;
				//Increment the snapshot count
				g_iSnapshotCount++;
				//Get the Full path of the snapshot
				snapshotFilePath = strCatoFolder + "/SS_" + g_iSnapshotCount + ".png";
				snapshotRelativePath = "." + g_ScreenShotRelative + "/SS_" + g_iSnapshotCount + ".png";
				//Capture the Snapshot
				fTakeScreenshot(snapshotFilePath,"P");

				new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=green><B>" + g_OperationCount + "</B>" +
						"<TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +  elapsedtime +" </FONT></TD>" +
						"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +
						strDescription + " </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" + strExpectedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" 
						+ strObtainedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><A HREF='" + snapshotRelativePath + "'><IMG SRC='" + snapshotRelativePath + "' alt='Screen Shot' style='width:250px;height:200px'></A></TD></TR>");
				System.out.println("#######");
//				switch(Environment.get("CLASS_NAME").split("[_]")[0]){
//                case "Cycle0":System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+metro.regression.Cycle0_MainDriver.getTestNumber());
//                break;
//                case "EDGE": System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+metro.regression.EDGE_MainDriver.getTestNumber());
//                break;
//                case "Progression": System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+metro.regression.Progression_MainDriver.getTestNumber());
//                break;
//                case "ProfileScenarios": System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+metro.regression.ProfileScenarios_MainDriver.getTestNumber());
//                break;
//                default: System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+metro.regression.EDGE_MainDriver.getTestNumber());
//                break;
//                }
				System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+vfro.regression.MainDriver.getTestNumber());
//				System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME"));
				System.out.println("Step: "+g_OperationCount +" -- TimeStamp: "+elapsedtime +" -- Description: " + strDescription + " -- Expected Value: "+strExpectedValue+ " -- Actual: "+strObtainedValue);
				System.out.println("#######");
			}
			else
			{
				if (strResult.toUpperCase().equals("FAIL")){
					//Increment the SnapShot count
					g_iSnapshotCount++ ;

					//Increment the Fail Count
					g_iFailCount++;

					//Get the Full path of the snapshot
					snapshotFilePath = g_strSnapshotFolderName + "/SS_" + g_iSnapshotCount + ".png";
					snapshotRelativePath = "." + g_ScreenShotRelative + "/SS_" + g_iSnapshotCount + ".png";
					//Capture the Snapshot
					fTakeScreenshot(snapshotFilePath,"F");

					new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=red><B>" + g_OperationCount + "</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +  elapsedtime +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>" + strDescription + " </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>" + strExpectedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=red>" + strObtainedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><A HREF='" + snapshotRelativePath + "'><IMG SRC='" + snapshotRelativePath + "' alt='Screen Shot' style='width:250px;height:200px'></A></TD></TR>");
					System.out.println("#######");
					System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME"));
					System.out.println("Step: "+g_OperationCount +" -- TimeStamp: "+elapsedtime +" -- Description: " + strDescription + " -- Expected: "+strExpectedValue+ " -- Actual: "+strObtainedValue);
					System.out.println("#######");
				}else if (strResult.toUpperCase().equals("DONE")){ 
					//Increment the Pass Count
					g_iPassCount++;

					strResult = "Pass";
					//Write Results into the file
					new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=green><B>" + g_OperationCount +"</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +  elapsedtime +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strDescription +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" + strExpectedValue + "</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>"+ strObtainedValue +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=Green><B>"+ strResult +"</B></FONT></TD></TR>");
					System.out.println("#######");
					System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME"));
					System.out.println("Step: "+g_OperationCount +" -- TimeStamp: "+elapsedtime +" -- Description: " + strDescription + " -- Expected Value: "+strExpectedValue+ " -- Actual Value: "+strObtainedValue);
					System.out.println("#######");
				}
			}
			try
			{			
				//Close File stream
				foutStrm.close();

			} catch (IOException io) {
				io.printStackTrace();
			}
		}catch(Exception e1){
			System.err.println();
		}
	}


	//***********************************************************************************************************************************
	//*    Name        		: CreateUnifiedReport
	//*    Description    	: Execution results of all drivers will be stored in a unified location- SummaryReport_TimeStamp.html
	//*    Author        	: Naveena Basetty
	//*    Input Params    	: None
	//*    Return Values    : None
	//***********************************************************************************************************************************
	public void CreateUnifiedReport(String TS){
		try{ //get the list of drivers run on

			Set<String> DriverSet = new HashSet<String>();
			File xmlFile = new File(Dictionary.get("TESTNG_XMLFILE"));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc  = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("parameter");
			//System.out.println(nList.getLength());

			for (int i=0;i<nList.getLength() ;i++){
				if (nList.item(i).getAttributes().item(0).getNodeValue().toLowerCase().equals("browser")){
					System.out.println(nList.item(i).getAttributes().item(1).getNodeValue());
					DriverSet.add(nList.item(i).getAttributes().item(1).getNodeValue());
				}
			}       


			boolean flag = true;
			int TestCounter = 1;
			String browser = "<TABLE  CELLPADDING=3 CELLSPACING=1 WIDTH=100%>";
			for(String btype : DriverSet)
			{
				String p = Environment.get("CURRENTEXECUTIONFOLDER") + "/" + btype ;
				File dir = new File(p);
				File[] files = dir.listFiles();
				String htmlname = "";
				for(File name : files){
					//System.out.println("HTML_Reports"+TS + "  " + name.getName()  + "  " +  name.getName().compareToIgnoreCase("HTML_Reports"+TS));
					if( name.getName().compareToIgnoreCase("HTML_Reports"+TS) >= 1)
						htmlname = name.getName();
				}

				String exePath = Environment.get("CURRENTEXECUTIONFOLDER") + "/" + btype + "/" + htmlname + "/"+ "SummaryReport.html";
				File SRFile = new File(exePath);
				String htmlfilepath = "file:///"+Environment.get("CURRENTEXECUTIONFOLDER") + "/" + btype + "/" + htmlname + "/";
				htmlfilepath = htmlfilepath.replaceAll("//", "/");


				String thisLine;
				BufferedReader br = new BufferedReader(new FileReader(SRFile));
				while ((thisLine = br.readLine()) != null) {
					//System.out.println(thisLine.toString());
					if (flag && thisLine.contains("Status") ) {
						browser = browser + thisLine.toString().replaceAll("TC No.</B></FONT>", "Index</B></FONT></TD><TD><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Browser</B></FONT></TD>");
						flag = false;
					} else if (thisLine.contains("HREF"))
					{
						browser = browser + thisLine.toString().replaceAll("BGCOLOR=#D3D3D3><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>"," BGCOLOR=#D3D3D3><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2> " + TestCounter  + "</FONT></TD>" +
								"<TD><FONT FACE=VERDANA SIZE=2>" + btype + "</FONT></TD>").replaceAll("BGCOLOR=#EEEEEE><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>"," BGCOLOR=#D3D3D3><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2> " + TestCounter  + "</FONT></TD>" +
										"<TD><FONT FACE=VERDANA SIZE=2>"+ btype + "</FONT></TD>").replaceAll("HREF='", "HREF='"+ htmlfilepath);
						TestCounter = TestCounter + 1;
					}
				}
				br.close();
				//System.out.println(browser);
			}
			browser = browser + "</TABLE> " ;

			FileOutputStream OutputStream = new FileOutputStream(Environment.get("CURRENTEXECUTIONFOLDER")+ "/SummaryReport_" + TimeStamp() + ".html", true);
			new PrintStream(OutputStream).print("<h3> <font color=green> <center> " + new Date()+ " -- " + Environment.get("CLASS_NAME")+  "--    Consolidated Report </center> </font> </h3>");
			new PrintStream(OutputStream).println(browser);
			new PrintStream(OutputStream).print("<TABLE WIDTH=100%><TR><TD ALIGN=RIGHT><FONT FACE=VERDANA COLOR=ORANGE SIZE=1>&copy; amdocs - Integrated Customer Management</FONT></TD></TR></TABLE></BODY></HTML>");
			OutputStream.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private String TimeStamp()
	{
		java.util.Date today = new java.util.Date();
		Timestamp now = new java.sql.Timestamp(today.getTime());
		String tempNow[] = now.toString().split("/.");
		String timestamp = tempNow[0].replaceAll(":", ".").replaceAll(" ", "T");
		return timestamp;
	}


	/*//*****************************************************************************************
	//*    Name        : fnCreateWebTrendTestCaseReport
	//*    Description    : The function creates the summary HTML file
	//*    Author        : Suraj Bankar
	//*    Input Params    :     None
	//*    Return Values    :     None
	//*****************************************************************************************
	public void fnCreateWebTrendTestCaseReport(String strTestName){
		//Setting counter value
		g_iTCPassed = 0;
		g_iWTTestStepNo = 0;
		g_SummaryStartTime = new Date();
		g_iWTFailCount = 0;
		
		String strTestCaseFile = Environment.get("WTREPORTINGPATH") + "/" + g_strScriptName + ".html";
		Environment.put("WTTESTCASEFILE", strTestCaseFile);
		try{
			//Open the test case report for writing
			foutStrm = new FileOutputStream(strTestCaseFile, true);
			new PrintStream(foutStrm).println("<html ><head><style>h1{font-size:30px;color:#00008B;font-weight: 300;  text-align: center;  margin-bottom: 15px;}table{  width:100%;}.tbl-header{  background-color: rgba(255,255,255,0.3); border: 1px solid rgba(255,255,255,255);}.tbl-content{overflow-x:auto; margin-top: 0px; border: 1px solid rgba(255,255,255,255);}th{ padding: 20px 15px; text-align: left;  font-weight: 1000;  font-size: 15px; color: #00008B; text-transform: uppercase; background-color: rgba(255,255,255,0.3);  border-bottom: 1px solid rgba(255,255,255,255);}td{ padding: 15px; text-align: left;  vertical-align:middle; font-weight: 300;font-size: 15px;color:");
			new PrintStream(foutStrm).println("#00008B; border-bottom: solid 1px rgba(255,255,255,0.3);}body{ background: -webkit-linear-gradient(left, #25c481, #25b7c4); background: linear-gradient(to right, #B0C4DE, #B0C4DE);font-family: 'Roboto', sans-serif;}section{margin: 50px;}.template { margin-top: 40px;  padding: 10px;  clear: left; text-align: center; font-size: 10px; font-family: arial; color: #fff;}.template i { font-style: normal; color: #F50057;");
			new PrintStream(foutStrm).println("font-size: 14px;position: relative; top: 2px;}.template a { color: #fff;  text-decoration: none;}.template a:hover {text-decoration: underline;}::-webkit-scrollbar {  width: 6px;} ::-webkit-scrollbar-track {-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);} ::-webkit-scrollbar-thumb ");
			new PrintStream(foutStrm).println("{-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);}</style><script> window.console = window.console || function(t) {};</script><script>if (document.location.search.match(/type=embed/gi)){window.parent.postMessage(");
			new PrintStream(foutStrm).println("</script></head><body translate=NO ><section><!--for demo wrap--><h1>ATT.com Automation Web Trend Reporting</h1>");


			new PrintStream(foutStrm).println("<TABLE CELLPADDING=3 WIDTH=100%><TR height=30><TD WIDTH=130% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>&nbsp; Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + " on Browser " + driverType +"</B></FONT></TD></TR><TR HEIGHT=5></TR></TABLE>");
			new PrintStream(foutStrm).println("<TABLE BORDER=0 BORDERCOLOR=WHITE CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
			new PrintStream(foutStrm).println("<TR><TD BGCOLOR=BLACK WIDTH=20%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Test     Name:</B></FONT></TD><TD COLSPAN=6 BGCOLOR=BLACK><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>" + g_strScriptName + "</B></FONT></TD></TR>");
			
			new PrintStream(foutStrm).println("<div class=tbl-content><table cellpadding=0 cellspacing=0 border=0><tbody><tr><th>#</th><th>Page Name</th><th>Event Code - Event Action </th><th>Event Parameter</th><th>Expected Parameter Value</th><th>Actual Parameter Value</th><th>Status</th></tr>");
			//Close the object
			foutStrm.close();
		} catch (IOException io){
			io.printStackTrace();
		}
		foutStrm = null;
	}*/

	//*****************************************************************************************
	//*    Name         : fnWriteToWebTrendTestCaseReport
	//*    Description  : 
	//*    Author       : Shraddha Girme
	//*    Input Params : String strTestCaseName, String strPageName,String strEventCodeAction, String[] strEventParameter,String[] strExpectedParameterValue ,String[] strConsoleParameterValue,String[] strNetworkParameterValue, String[] strStatus
	//*    Return Values: None
	//*****************************************************************************************
	public void fnWriteToWebTrendTestCaseReport(String strTestCaseName, String strPageName,String strEventCodeAction, String[] strConsoleEventParameter,String[] strConsoleParameterValue,String[] strNetworkEventParameter ,String[] strNetworkParameterValue, String[] strExpectedParameterValue, String[] strStatus){
		//Setting counter value
		g_iTCPassed = 0;
		g_SummaryStartTime = new Date();
		g_iWTTestStepNo = g_iWTTestStepNo + 1;
		String strConsoleEventParameterString = "";
		String strConsoleParameterValueString = "";
		String strNetworkEventParameterString = "";
		String strNetworkParameterValueString = "";
		String strExpectedParameterValueString = "";

		//String strNetworkParameterValueString = "";
		String strColorString = "";
		String color="";

		try{
			//Write the step
			foutStrm = new FileOutputStream(Environment.get("WTTESTCASEFILE"), true);

			//Add Single Step
			new PrintStream(foutStrm).println(" <tr><td>"+ g_iWTTestStepNo +"</td><td>"+ strPageName +"</td> <td>"+ strEventCodeAction +"</td>");

			for(int iEvent=0; iEvent < strConsoleEventParameter.length; iEvent++){
				strConsoleEventParameterString = strConsoleEventParameterString + strConsoleEventParameter[iEvent] +"</br></br>";
				strConsoleParameterValueString = strConsoleParameterValueString + strConsoleParameterValue[iEvent] +"</br></br>";
				strNetworkEventParameterString = strNetworkEventParameterString + strNetworkEventParameter[iEvent] + "</br></br>";
				strNetworkParameterValueString = strNetworkParameterValueString + strNetworkParameterValue[iEvent] + "</br></br>";
				//strExpectedParameterValueString = strExpectedParameterValueString + strExpectedParameterValue[iEvent] + "</br></br>";
				if(strStatus[iEvent].equals("Pass")){
					color="green;>&#10004;";
				}else{
					color="red;>&#10008";
					g_iWTFailCount ++;
				}
				strColorString = strColorString + "<span style=font-size:100%;font-weight:bold;color:"+ color + "</br> </br>";
			}
			new PrintStream(foutStrm).println("<td>"+strConsoleEventParameterString+"</td> <td>"+strConsoleParameterValueString+"</td> <td>"+strNetworkEventParameterString+"</td><td>" + strNetworkParameterValueString + "</td></tr>");

			//Close the object
			foutStrm.close();
		} catch (IOException io){
			io.printStackTrace();
		}
		foutStrm = null;
	}
	
	//*****************************************************************************************
	//*    Name        : fnCreateWebTrendTestCaseReport
	//*    Description    : The function creates the summary HTML file
	//*    Author        : Suraj Bankar
	//*    Input Params    :     None
	//*    Return Values    :     None
	//*****************************************************************************************
	public void fnCreateWebTrendTestCaseReport(String strTestName){
		//Setting counter value
		g_iTCPassed = 0;
		g_iWTTestStepNo = 0;
		g_SummaryStartTime = new Date();
		g_iWTFailCount = 0;
		
		String strTestCaseFile = Environment.get("WTREPORTINGPATH") + "/" + g_strScriptName + ".html";
		Environment.put("WTTESTCASEFILE", strTestCaseFile);
		try{
			//Open the test case report for writing
			foutStrm = new FileOutputStream(strTestCaseFile, true);
			new PrintStream(foutStrm).println("<html ><head><style>table{  width:100%;}.tbl-header{  background-color: rgba(255,255,255,255); border: 1px solid rgba(255,255,255,255);}.tbl-content{overflow-x:auto; margin-top: 0px; border: 1px solid rgba(255,255,255,255);}th{ padding: 10px 10px; text-align: left;  font-weight: 1000;  font-size: 13px; color: #00008B; text-transform: uppercase; background-color: #F39C12;  border-bottom: 1px solid rgba(255,255,255,255);}td{ padding: 10px; text-align: left;  vertical-align:middle; font-weight: 300;font-size: 13px;color:");
			new PrintStream(foutStrm).println("#00008B; background-color: #EAEDED ;border-bottom: solid 1px rgba(255,255,255,0.3);}body{ background: -webkit-linear-gradient(left, #FFFFFF, #FFFFFF); background: linear-gradient(to right, #FFFFFF, #FFFFFF);font-family: 'VERDANA', sans-serif;}section{margin: 10px;}.template { margin-top: 40px;  padding: 10px;  clear: left; text-align: center; font-size: 5px; font-family: VERDANA; color: #fff;}.template i { font-style: normal; color: #F50057;");
			new PrintStream(foutStrm).println("font-size: 10px;position: relative; top: 2px;}.template a { color: #fff;  text-decoration: none;}.template a:hover {text-decoration: underline;}::-webkit-scrollbar {  width: 6px;} ::-webkit-scrollbar-track {-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);} ::-webkit-scrollbar-thumb");
			new PrintStream(foutStrm).println("{-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);}</style><script> window.console = window.console || function(t) {};</script><script>if (document.location.search.match(/type=embed/gi)){window.parent.postMessage(");
			new PrintStream(foutStrm).println("</script></head><body translate=NO ><section><h1 ALIGN=CENTER><FONT FACE=VERDANA COLOR=ORANGE SIZE=4><B>AMDOCS</B></FONT></BR><FONT FACE=VERDANA COLOR=ORANGE SIZE=4>ATT.com Automation Web Trend Reporting</FONT></BR><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>");


			new PrintStream(foutStrm).println("Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + " on Browser " + driverType +"</B></FONT></h1>");
			new PrintStream(foutStrm).println("<TABLE BORDER=0 BORDERCOLOR=WHITE CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
			new PrintStream(foutStrm).println("<TR><TH BGCOLOR=BLACK WIDTH=20%><FONT FACE=VERDANA COLOR=#00008B SIZE=2><B>Test     Name:</B></FONT></TH><TH COLSPAN=6 BGCOLOR=BLACK><FONT FACE=VERDANA COLOR=#00008B SIZE=2><B>" + g_strScriptName + "</B></FONT></TH></TR></TABLE>");
			
			new PrintStream(foutStrm).println("<div class=tbl-content><table cellpadding=0 cellspacing=2 border=0><tbody><tr><th>#</th><th>Page Name</th><th>Event Code-Event Action </th><th>Data Layer Parameter Name</th><th>Data Layer Parameter Value</th><th>Adobe Beacon Parameter Name</th><th>Adobe Beacon Parameter Value</th></tr>");
			//Close the object
			foutStrm.close();
		} catch (IOException io){
			io.printStackTrace();
		}
		foutStrm = null;
	}

	//*****************************************************************************************
	//*    Name        : fnCreateWebTrendSummaryReport
	//*    Description    : The function creates the summary HTML file
	//*    Author        : Shraddha Girme
	//*    Input Params    :     None
	//*    Return Values    :     None
	//*****************************************************************************************
	public void fnCreateWebTrendSummaryReport(){
		//Setting counter value
		g_iTCPassed = 0;
		g_iWTTestCaseNo = 0;
		g_SummaryStartTime = new Date();

		String strSummaryFile = Environment.get("WTREPORTINGPATH") + "/WTSummaryReport.html";
		Environment.put("WTSUMMARYFILE", strSummaryFile);
		try {		
			foutStrm = new FileOutputStream(strSummaryFile, true);
			new PrintStream(foutStrm).println("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100% BGCOLOR=BLACK>");
			new PrintStream(foutStrm).println("<TR><TD WIDTH=90% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=ORANGE SIZE=3><B>AMDOCS</B></FONT></TD></TR><TR><TD ALIGN=CENTER BGCOLOR=ORANGE><FONT FACE=VERDANA COLOR=WHITE SIZE=3><B>ATT.com Web Trend Reporting</B></FONT></TD></TR></TABLE><TABLE CELLPADDING=3 WIDTH=100%><TR height=30><TD WIDTH=100% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>&nbsp; Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + " on Browser " + driverType +"</B></FONT></TD></TR><TR HEIGHT=5></TR></TABLE>");
			new PrintStream(foutStrm).println("<TABLE  CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");           
			new PrintStream(foutStrm).println("<TR COLS=6 BGCOLOR=ORANGE><TD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>#</B></FONT></TD><TD  WIDTH=60%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Test Name</B></FONT></TD><TD BGCOLOR=ORANGE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Status</B></FONT></TD><TD  WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Test Duration</B></FONT></TD></TR>");

			//Close the object
			foutStrm.close();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//*****************************************************************************************
	//*    Name         : fnWriteToWebTrendSummaryReport
	//*    Description  : 
	//*    Author       : Shraddha Girme
	//*    Input Params : String strTestCaseName, String strPageName,String strEventCodeAction, String[] strEventParameter,String[] strExpectedParameterValue ,String[] strConsoleParameterValue,String[] strNetworkParameterValue, String[] strStatus
	//*    Return Values: None
	//*****************************************************************************************
	public void fnWriteToWebTrendSummaryReport(String strTestCaseName, String strResult, String strDuration){
		String sColor,sRowColor;

		//Close the file
		try{        
			//Open the test case report for writing                   
			foutStrm = new FileOutputStream(Environment.get("WTSUMMARYFILE"), true);

			//Check color result
			if (strResult.toUpperCase().equals("PASSED") || strResult.toUpperCase().equals("PASS")){
				sColor = "GREEN";
				g_iTCPassed ++;
			}
			else if (strResult.toUpperCase().equals("FAILED") || strResult.toUpperCase().equals("FAIL")){
				sColor = "RED";
			}
			else{
				sColor = "ORANGE";
			}

			g_iWTTestCaseNo++;

			if (g_iWTTestCaseNo % 2 == 0){sRowColor = "#EEEEEE";}
			else{sRowColor = "#D3D3D3";}

			//Write the result of Individual Test Case
			new PrintStream(foutStrm).println ("<TR COLS=3 BGCOLOR=" + sRowColor + "><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iWTTestCaseNo + "</FONT></TD><TD  WIDTH=60%><FONT FACE=VERDANA SIZE=2>" + strTestCaseName + "</FONT></TD><TD  WIDTH=15%><A HREF='" + strTestCaseName + ".html'><FONT FACE=VERDANA SIZE=2 COLOR=" + sColor + "><B>" + strResult + "</B></FONT></A></TD><TD  WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + strDuration+ "</FONT></TD></TR>");

			foutStrm.close();
		}
		catch (IOException io) 
		{
			io.printStackTrace();
		}
		foutStrm = null;

	}

	//*****************************************************************************************
	//*    Name        : fnCloseWTTestCaseReport
	//*    Description    : 
	//*    Author        : Shraddha Girme
	//*    Input Params    :     None
	//*    Return Values    :     None
	//*****************************************************************************************
	public void fnCloseWTTestCaseReport(){
		try{
			//Fetch the time difference
			String strTimeDifference = fnTimeDiffference(g_StartTime.getTime(),g_EndTime.getTime());
			String strTestCaseResult;
			//Write the step
			foutStrm = new FileOutputStream(Environment.get("WTTESTCASEFILE"), true);
			//Closing Webtrend Report
			new PrintStream(foutStrm).println("<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr><TABLE  CELLPADDING=3 CELLSPACING=1 WIDTH=100%></TABLE>");

			//Close the object
			foutStrm.close();

			if (g_iWTFailCount != 0) {
				strTestCaseResult = "Fail";
			} else 
			{
				strTestCaseResult = "Pass";
			}

			//Write into the Summary Report
			fnWriteToWebTrendSummaryReport(Dictionary.get("TEST_NAME"),strTestCaseResult,strTimeDifference);
		} catch (IOException io){
			io.printStackTrace();
		}
		foutStrm = null;
	}

	//*****************************************************************************************
	//*    Name        : fnCloseWTSummary
	//*    Description    : The function Closes the summary file
	//*    Author        :  Shraddha
	//*    Input Params    :     None
	//*    Return Values    :     None
	//*****************************************************************************************
	public void fnCloseWTSummary()
	{
		g_SummaryEndTime = new Date();
		//Fetch the time difference
		String strTimeDifference = fnTimeDiffference(g_SummaryStartTime.getTime(),g_SummaryEndTime.getTime());

		System.out.println("\nTHREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- Time taken by last test: " + strTimeDifference);
		System.out.println("\nTHREAD:" + Thread.currentThread().getId() + " -- " + driverType + 
				" -- Total time taken by test suite until now: " + fnTimeDiffference(testSuiteStartTime.getTime(), System.currentTimeMillis()) + "\n");

		//Open the Test Summary Report File
		try {         
			foutStrm = new FileOutputStream(Environment.get("WTREPORTINGPATH") + "/WTSummaryReport.html", true);

			new PrintStream(foutStrm).println("</TABLE><TABLE WIDTH=100%><TR>");
			new PrintStream(foutStrm).println("<TD BGCOLOR=BLACK WIDTH=10%></TD><TD BGCOLOR=BLACK WIDTH=60%><FONT FACE=VERDANA SIZE=2 COLOR=WHITE><B></B></FONT></TD><TD BGCOLOR=BLACK WIDTH=15%><FONT FACE=WINGDINGS SIZE=4>2</FONT><FONT FACE=VERDANA SIZE=2 COLOR=WHITE><B>Total Passed: " + g_iTCPassed + "</B></FONT></TD><TD BGCOLOR=BLACK WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=WHITE><B>" + strTimeDifference + "</B></FONT></TD>");
			new PrintStream(foutStrm).println("</TR></TABLE>");
			new PrintStream(foutStrm).println("<TABLE WIDTH=100%><TR><TD ALIGN=RIGHT><FONT FACE=VERDANA COLOR=ORANGE SIZE=1>&copy; amdocs - Integrated Customer Management</FONT></TD></TR></TABLE></BODY></HTML>");

			//Close File stream
			foutStrm.close();

		} catch (IOException io) {
			io.printStackTrace();
		}
		//Deference the file pointer
		foutStrm = null;
	}

    //*****************************************************************************************
    //*    Name            : fnWriteVisualResultsToHtmlOutput
    //*    Description     : The function Writes output of the visual tests to the HTML file
    //*    Author          : Zachi Gahari
    //*    Input Params    : stepName
    //*                        strExpectedValue(String) - the expected value
    //*                        strObtainedValue(String) - the actual/obtained value
    //*                        strResult(String) - the result (Pass/Fail)
    //*    Return Values    :     
    //*                        (Boolean) TRUE - Successful write
    //*                                  FALSE - Report file not created
    //*****************************************************************************************
    public void fnWriteVisualResultsToHtmlOutput(String stepName, String imageFilePath, String statusResult) {
           if(!Environment.get("RUN_ACCESSIBILITY").equalsIgnoreCase("Y")){
//                String sStep;
//                if (Dictionary.containsKey("STEP")){
//                      sStep = Dictionary.get("STEP") + "<NS>" + strDescription + "<ND>" + strExpectedValue + "<ND>" + strObtainedValue + "<ND>" + strResult;
//                      Dictionary.remove("STEP");
//                }else{
//                      sStep = strDescription + "<ND>" + strExpectedValue + "<ND>" + strObtainedValue + "<ND>" + strResult;
//                }
//
//                Dictionary.put("STEP", sStep);
//                Driver.QCHashMap.put("STEP", sStep);
                  //Declaring Variables
                  String currentFile, baseLineFile, diffFile, AnimatedGif, sRowColor, status, statusColor;
                  LogStatus logStatus;
                  //Open the test case report for writing
                  //Open the HTML file
                  //Open the report file to write the report
                  try {
                        foutStrm = new FileOutputStream(g_strTestCaseReport, true);

                  } catch (FileNotFoundException fe) {
                        fe.printStackTrace();
                  }

                  //Increment the Operation Count
                  g_OperationCount = g_OperationCount + 1;

                  //Row Color
                  if (g_OperationCount % 2 == 0) {
                        sRowColor = "#EEEEEE";
                  } else {
                        sRowColor = "#D3D3D3";
                  }

                  String elapsedtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()).toString() ;
                  g_iVisualTC++;
                  currentFile = imageFilePath + "step " + g_iVisualTC + " " + stepName + "-Current.png";
                  baseLineFile = imageFilePath + "step " + g_iVisualTC + " " + stepName + "-Baseline.png";
                  diffFile = imageFilePath + "step " + g_iVisualTC + " " + stepName + "-Diff.png";
                        
                  //Check if the result is Pass or Fail
                  if (statusResult.toUpperCase().equals("PASSED")){
                        status = "Passed";
                        statusColor = "green";
                        AnimatedGif = null;
                        logStatus = LogStatus.PASS;
                  }else{
                        status = "Failed";
                        statusColor = "red";
                        AnimatedGif = imageFilePath + g_iVisualTC + " - AnimatedGif.gif";
                        logStatus = LogStatus.FAIL;
                  }
                        
                  if (statusResult.toUpperCase().equals("PASSED")){               

                        new PrintStream(foutStrm).println("<TR WIDTH=100%>" +
                                      "<TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=green><B>" + g_OperationCount + "</B>" +
                                      "<TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +  elapsedtime +" </FONT></TD>" + //"</FONT></TD>" +
                                      "<TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=" + statusColor + ">" + stepName + "<br>" + "<br>" + status +" </FONT></TD>" +
                                      "<TD BGCOLOR=" + sRowColor + " WIDTH=15% ALIGN=CENTER><A HREF='" + baseLineFile + "'><IMG SRC='" + baseLineFile + "' alt='Screen Shot' style='width:250px;height:200px'></A></TD>" +
                                      "<TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><A HREF='" + currentFile + "'><IMG SRC='" + currentFile + "' alt='Screen Shot' style='width:250px;height:200px'></A></TD>" +
                                      "</TR>");

                        System.out.println("#######");
                        
//                        switch(Environment.get("CLASS_NAME")){
//                        case "EDGE": System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+metro.regression.EDGE_MainDriver.getTestNumber());
//                        break;
//                        case "Cycle0": System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+metro.regression.Cycle0_MainDriver.getTestNumber());
//                        break;
//                        default: System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+metro.regression.EDGE_MainDriver.getTestNumber());
//                        break;
//                        }
                        
                        System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+vfro.regression.MainDriver.getTestNumber());
                        System.out.println("Step: "+g_OperationCount +" -- TimeStamp: "+elapsedtime +" -- Description: " + stepName + " -- Results: "+ statusResult.toUpperCase());
                        System.out.println("#######");
                        
                        test.log(logStatus, stepName + " | Expected - PASSED  | Actual -  " + statusResult.toUpperCase());
                        test.log(LogStatus.INFO, "Expected snapshot below: " + test.addScreenCapture(baseLineFile));
                        test.log(LogStatus.INFO, "Actual snapshot below: " + test.addScreenCapture(currentFile));
                  }else{
                        new PrintStream(foutStrm).println("<TR WIDTH=100%>" +
                                      "<TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=green><B>" + g_OperationCount + "</B>" +
                                      "<TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=green>" +  elapsedtime +" </FONT></TD>" + //"</FONT></TD>" +
                                      "<TD BGCOLOR=" + sRowColor + " WIDTH=15%><FONT FACE=VERDANA SIZE=2 COLOR=" + statusColor + ">" + stepName + "<br>" + "<br>" + status +" </FONT></TD>" +
                                      "<TD BGCOLOR=" + sRowColor + " WIDTH=15% ALIGN=CENTER><A HREF='" + baseLineFile + "'><IMG SRC='" + baseLineFile + "' alt='Screen Shot' style='width:250px;height:200px'></A></TD>" +
                                      "<TD BGCOLOR=" + sRowColor + " WIDTH=10% ALIGN=CENTER><A HREF='" + currentFile + "'><IMG SRC='" + currentFile + "' alt='Screen Shot' style='width:250px;height:200px'></A></TD>" +
                                      "<TD BGCOLOR=" + sRowColor + " WIDTH=15% ALIGN=CENTER><A HREF='" + AnimatedGif + "'><IMG SRC='" + AnimatedGif + "'alt='Animation' style='width:250px;height:200px'></A></TD>" +
                                      "</TR>");

                        System.out.println("#######");
                        
//                        switch(Environment.get("CLASS_NAME")){
//                        case "EDGE": System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+metro.regression.EDGE_MainDriver.getTestNumber());
//                        break;
//                        case "Cycle0": System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+metro.regression.Cycle0_MainDriver.getTestNumber());
//                        break;
//                        default: System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+metro.regression.EDGE_MainDriver.getTestNumber());
//                        break;
//                        }

                        System.out.println("THREAD:" + Thread.currentThread().getId() + " -- " + driverType + " -- TEST CASE NAME: " + Dictionary.get("TEST_NAME")+". Test number executing: "+vfro.regression.MainDriver.getTestNumber());
                        System.out.println("Step: "+g_OperationCount +" -- TimeStamp: "+elapsedtime +" -- Description: " + stepName + " -- Results: "+ statusResult.toUpperCase());
                        System.out.println("#######");
                        test.log(logStatus, stepName + " | Expected - PASSED  | Actual -  " + statusResult.toUpperCase());
                        test.log(LogStatus.INFO, "Expected snapshot below: " + test.addScreenCapture(baseLineFile));
                        test.log(LogStatus.INFO, "Actual snapshot below: " + test.addScreenCapture(currentFile));
                        test.log(LogStatus.INFO, "Differrences highlighted below: " + test.addScreenCapture(diffFile));

                  }
                  
                  try
                  {                    
                        //Close File stream
                        foutStrm.close();

                  } catch (IOException io) {
                        io.printStackTrace();
                  }

           }
    }
    
    public static int getFailureCount(){
		return g_iFailCount;
	}
    
    public String getExecutionDuration(){
    	return executionDuration;
    }
    
    public static int getPassCount(){
		return g_iTCPassed;
	}

}
