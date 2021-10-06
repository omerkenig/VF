package vfro.Dexp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.codoid.products.fillo.Recordset;

import vfro.framework.CommonFunctions;
import vfro.framework.DatabaseOperations;
import vfro.framework.Reporting;

public class cnpGenerator {

	//Instance Variables
	private Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private HashMap<String, String> Environment;
	private CommonFunctions CommonFunctions;
	private HashMap<String, String> Dictionary;

	public cnpGenerator(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Environment = GEnvironment;
		Dictionary = GDictionary;
		CommonFunctions = new CommonFunctions(driver,driverType,Environment,Reporter);
		
	}
	
	//Objects
	private String btnGenerateCnp= "xpath:=//input[@type='submit' and @value='Generează']";
	private String txtBody = "xpath:=//body";
	
	//Methods
	public boolean isPagsDisplay()	{
		if(CommonFunctions.fCommonIsDisplayed(btnGenerateCnp, "Generate CNP", 5) == false) return false;
		return true;
	}

	public boolean clickGenerateCnp()	{
		if (CommonFunctions.fCommonClick(btnGenerateCnp, "Generate CNP") == false) return false;
		return true;
	}
	
	public String getCnp(){
		return CommonFunctions.fCommonGetText(txtBody, "Body", 1).split("CNP:")[1].split("\\n")[0].trim();
	}
	
	public String generateCnp(){
	    String originalHandle = driver.getWindowHandle();  // Save the original tab

		if(CommonFunctions.fCommonOpenLinkInNewtab(Environment.get("CNP_GENERATOR_URL")) == false) return null;
		CommonFunctions.fCommonSwitchToWindow(1);
		if(isPagsDisplay()== false) return null;
		if(clickGenerateCnp() == false) return null;
		String cnp = getCnp();
		driver.close();	// Close the CNP generator tab
		driver.switchTo().window(originalHandle);  // Switch back to the original tab

		return cnp;
	}

}
