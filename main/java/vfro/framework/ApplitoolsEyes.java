package vfro.framework;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.FileLogger;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.ProxySettings;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;

import vfro.regression.MainDriver;

//import metro.framework.ApplitoolsTestResultsHandler;

//import java.awt.image.BufferedImage;

public class ApplitoolsEyes {

	public static final int EYES_DEFAULT_WIDTH = 1476;
	public static final int EYES_DEFAULT_HEIGHT = 763;

	private static Eyes eyes;
	private static Reporting Reporter;
	private static String driverType;
	private static Boolean imageTaken;
	private static HashMap<String, String> Dictionary;
	private static HashMap<String, String> Environment;
	public static BatchInfo myBatch;
	public static String eyesLink;
	public static WebDriver eyesDriver;

	//Constructor
	public ApplitoolsEyes(String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment ,Reporting GReporter)
	{
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		Reporter = GReporter;
	}
	
	//*****************************************************************************************
	//* Name            : getEyes
	//* Description     : method to get Applitools Eyes 
	//* Author          : Zachi Gahari
	//* Creation Date   : 11-Mar-2018
	//* Input Params    : None
	//* Return Values   : 
	//* Updated by      : 
	//* Modified date   : 
	//*****************************************************************************************
	public static Eyes getEyes(){
		if (eyes == null) {
			eyes = new Eyes();
//			eyes.setLogHandler(new StdoutLogHandler(true));
//			eyes.setLogHandler(new FileLogger("C:\\SVN\\MetroPCS-EDGE_21.01\\file.log", true,true));

			try {
				eyes.setServerUrl(new URI(Environment.get("APPLITOOLS_URL")));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			eyes.setApiKey(getApiKey());
			eyes.setProxy(new ProxySettings("http://genproxy:8080"));
			eyes.setMatchLevel(MatchLevel.STRICT);
			eyes.setForceFullPageScreenshot(true);
			eyes.setStitchMode(StitchMode.CSS);
			eyes.setSaveDebugScreenshots(false);
			
			setBatch();
			imageTaken = false;
//			eyes.setMatchTimeout(4);
//			eyes.setHostApp("IE");
			eyes.setHostOS("Windows 10");
		}
		return eyes;
	}

	//*****************************************************************************************
	//*	Name		    : getApiKey
	//* Description     : method to get API key 
	//* Author          : Zachi Gahari
	//* Creation Date   : 11-Mar-2018
	//* Input Params    : None
	//* Return Values   : 
	//* Updated by      : 
	//* Modified date   : 
	//*	Description	    : return ApiKey according to the team.
	//***********************************************************************
	public static String getApiKey()
	{
		switch (Environment.get("TEAM")) {
		case "Amdocs Visual Test Team":
			return "97SuCOcL0D0gZHPoAx74KcdTzCZEspQKmRyXeFSwH7UM110";
		}

		return "97SuCOcL0D0gZHPoAx74KcdTzCZEspQKmRyXeFSwH7UM110";
	}


	public void checkWindow (String screenhotName) {
		if (Environment.get("VISUAL_TEST").equals("Y")){
			eyes.checkWindow(screenhotName);
			imageTaken = true;
			Reporter.fnWriteToHtmlOutput ("Taking Applitools image - check Window", "Applitools Image should be created", "Applitools Image was created", "DONE");
		}
	}


	public static boolean closeEyes () {
		boolean success = true;
		String imagesRootFolder = Environment.get("HTMLREPORTSPATH");
		String imagesFolder = null;
		String testName=null;
		String qcStatus=null;
		
		if (eyes != null && eyes.getIsOpen()) {
			try {
				TestResults results = getEyes().close(false);
				eyesLink = results.getUrl();
				System.out.println("Result: " + results.toString());
				System.out.println("Matches: " + results.getMatches());
				System.out.println("Mismatches: " + results.getMismatches());
				System.out.println("Missing: " + results.getMissing());
				System.out.println("URL: " + eyesLink);
				System.out.println("Steps: " + results.getSteps());
				System.out.println("Passed: " + results.isPassed());

				if (eyesLink!=null) {
					if (results.isNew()) {
						Reporter.fnWriteToHtmlOutput("Applitool Eyes", "New Eyes test(baseline) was created.", "New Eyes test was created. See details in  <a href=" + eyesLink + ">" + eyesLink + "</a>", "Pass");

					} else if (results.isPassed()) {
						Reporter.fnWriteToHtmlOutput("Applitool Eyes ", "Actual screenshot should be same as baseline", "Actual screenshot is same as baseline. See details in  <a href=" + eyesLink + ">" + eyesLink + "</a>", "Pass");
					} else {
						//since calling from afterMethod, avoid throwing assertion error
						Reporter.fnWriteToHtmlOutput("Applitool Eyes ", "Actual screenshot should be same as baseline", "Actual screenshot is different from baseline. See details in  <a href=" + eyesLink + ">" + eyesLink + "</a>", "Done");
						success = false;
					}
				}
				if(imageTaken == true){
					ApplitoolsTestResultsHandler testResultHandler;
					try {
						testResultHandler = new ApplitoolsTestResultsHandler(results, "JPxZcucVP3hYWDlUJPgNqYkDwl2wvKbP2KRv3CiyOIw110", "genproxy", "8080");
						System.out.println("batchID: " + testResultHandler.batchID + "   sessionID:" + testResultHandler.sessionID);
						imagesFolder = imagesRootFolder + "\\" + testResultHandler.getAppName().toString() + "\\" + testResultHandler.getTestName().toString() + "\\"+ testResultHandler.batchID + "\\" + testResultHandler.sessionID + "\\";
						testResultHandler.SetPathPrefixStructure("AppName/TestName");
						testResultHandler.downloadImages(imagesRootFolder);
						testResultHandler.downloadDiffs(imagesRootFolder);
						try{testResultHandler.downloadAnimatedGif(imagesRootFolder, 1000);
						}catch(NullPointerException e){
							
						}
						ResultStatus[] stepsResultsArray = testResultHandler.calculateStepResults();
						String[] stepsNames = testResultHandler.getStepsNames();
						testName=Dictionary.get("TEST_NAME");
						qcStatus=Dictionary.get("QC_STATUS");
						for(int i=0;i<stepsNames.length;++i){
							Reporter.fnWriteVisualResultsToHtmlOutput(stepsNames[i].toString(), imagesFolder, stepsResultsArray[i].toString());
							System.out.println(stepsNames[i].toString());
							System.out.println(testResultHandler.getLinkToStep(i));
							System.out.println(stepsResultsArray[i].toString());
							Dictionary.put("TEST_NAME", stepsNames[i].toString());
							
							if(stepsResultsArray[i].toString().toUpperCase().equals("PASSED"))
								Dictionary.put("QC_STATUS", "Passed");
							else
								Dictionary.put("QC_STATUS", "In Progress");
							
							new DatabaseOperations(Dictionary, Environment).updateGrafana(MainDriver.getRelease(), MainDriver.getTestPhase(), MainDriver.getApplicationName(), MainDriver.getPackageName(), "", MainDriver.getExecutionBrowser(), "", Environment.get("ENV_CODE"), MainDriver.getUser());
						}
						Dictionary.put("TEST_NAME", testName);
						Dictionary.put("QC_STATUS", qcStatus);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else
					Reporter.fnWriteToHtmlOutput("Applitool Eyes ", "Visual testing", "There are no visual tests for this scenario or test fail before visual test", "Done");
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				getEyes().abortIfNotClosed();
			}
		}

		return success;
	}

	public static void abortEyesIfNotClosed () {
		eyes.abortIfNotClosed();
	}

	public static void setForceFullPageScreenshot(Boolean tag) {
		eyes.setForceFullPageScreenshot(tag);
	}

	public void checkRegion (String objDesc, String screenhotName, Boolean tag) {
		if (Environment.get("VISUAL_TEST").equals("Y")){
			eyes.checkRegion(By.xpath(objDesc), screenhotName, tag);
			imageTaken = true;
			Reporter.fnWriteToHtmlOutput ("Taking Applitools image - check Region", "Applitools Image should be created", "Applitools Image was created", "DONE");
		}
	}

	public void checkRegion (String objDesc,  Boolean tag) {
		if (Environment.get("VISUAL_TEST").equals("Y")){
			eyes.checkRegion(By.xpath(objDesc), tag);
			imageTaken = true;
			Reporter.fnWriteToHtmlOutput ("Taking Applitools image - check Region", "Applitools Image should be created", "Applitools Image was created", "DONE");
		}
	}

//	public static void checkRegionInFrame(WebElement frameElement, WebElement element, String tag) {
//		eyes.checkRegionInFrame(frameElement, (By)frameElement, tag);
//		Reporter.fnWriteToHtmlOutput ("Taking Applitools image - check Region In Frame", "Applitools Image should be created", "Applitools Image was created", "DONE");
//	}

	public static void setBatch() {
		String applicationName=null;
		
		if(MainDriver.getApplicationName().equalsIgnoreCase("rise"))
			applicationName="POW";
		else
			applicationName=MainDriver.getApplicationName();
		System.out.println("Batch: "+System.getProperty("user.name").toLowerCase() +"_"+applicationName+"_"+driverType.replace("1", "") + "_"+Environment.get("ENV_CODE"));
		myBatch = new BatchInfo(System.getProperty("user.name").toLowerCase() +"_"+applicationName+"_"+driverType.replace("1", "") + "_"+Environment.get("ENV_CODE"));
		eyes.setBatch(myBatch);
	}


	public static String getEyesLink() {
		return eyesLink;
	}

}

