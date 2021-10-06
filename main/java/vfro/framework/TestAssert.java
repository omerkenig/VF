package vfro.framework;

import java.util.Dictionary;

import org.testng.Assert;

public class TestAssert {
	
	private Reporting Reporter;
	public boolean testStatus;
	
	public TestAssert(Reporting GReporter)
	{
		Reporter = GReporter;
		testStatus = true;
	}
	
	// *****************************************************************************************
    // * Name : assertTrue
    // * Description : This method verifies if the given condition is true
    // * Author : Prakash Bastola
    // * Input Params : condition -> boolean condition to be verified
    //*                 conditionInString -> condition expressed in string
    // * Return Values : boolean
    // *****************************************************************************************
    public void assertTrue(Boolean condition, String conditionInString) {
           
           try{
                 Assert.assertTrue(condition);     
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString+" is "+ condition , "Pass");
           }
           catch(AssertionError ex)
           {
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString +" is "+ condition , "Fail");
                 throw new AssertionError();
           }
    }
    
	// *****************************************************************************************
    // * Name : assertTrue
    // * Description : This method verifies if the given condition is true
    // * Author : Prakash Bastola
    // * Input Params : condition -> boolean condition to be verified
    //*                 conditionInString -> condition expressed in string
    // * Return Values : boolean
    // *****************************************************************************************
    public void isTrue(Boolean condition, String conditionInString) {
           
           try{
                 Assert.assertTrue(condition);     
                 Reporter.fnWriteToHtmlOutput("Calculating page load time", conditionInString ,
                               conditionInString, "Pass");
           }
           catch(AssertionError ex)
           {
                 Reporter.fnWriteToHtmlOutput("Calculating page load time", conditionInString ,
                               conditionInString , "Fail");
                 throw new AssertionError();
           }
    }

	// *****************************************************************************************
    // * Name : assertTrue
    // * Description : This method verifies if the given condition is true
    // * Author : Prakash Bastola
    // * Input Params : condition -> boolean condition to be verified
    //*                 conditionInString -> condition expressed in string
    // * Return Values : boolean
    // *****************************************************************************************
    public void verifyTrue(Boolean condition, String conditionInString) {
           
           try{
                 Assert.assertTrue(condition);     
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString + " is true", "Pass");
           }
           catch(AssertionError ex)
           {
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString + " is false", "Fail");
                 testStatus = false;
           }
    }
    
    public void verifyTrue(Boolean condition, String conditionInString, String actualCondition) {
        
        try{
              Assert.assertTrue(condition);     
              Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString,
            		  actualCondition , "Pass");
        }
        catch(AssertionError ex)
        {
              Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString ,
            		  actualCondition, "Fail");
              testStatus = false;

        }
 }
   public void assertTrue(Boolean condition, String conditionInString, String actualCondition) {
        
        try{
              Assert.assertTrue(condition);     
              Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString,
            		  actualCondition , "Pass");
        }
        catch(AssertionError ex)
        {
              Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString ,
            		  actualCondition, "Fail");
              throw new AssertionError();

        }
 }
	// *****************************************************************************************
    // * Name : assertTrue
    // * Description : This method verifies if the given condition is true
    // * Author : Prakash Bastola
    // * Input Params : condition -> boolean condition to be verified
    //*                 conditionInString -> condition expressed in string
    // * Return Values : boolean
    // *****************************************************************************************
    public void assertFalse(Boolean condition, String conditionInString) {
           
           try{
                 Assert.assertFalse(condition);     
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString , "Pass");
           }
           catch(AssertionError ex)
           {
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString , "Fail");
                 throw new AssertionError();
           }
    }
    
 // *****************************************************************************************
    // * Name : assertTrue
    // * Description : This method verifies if the given condition is true
    // * Author : Prakash Bastola
    // * Input Params : condition -> boolean condition to be verified
    //*                 conditionInString -> condition expressed in string
    // * Return Values : boolean
    // *****************************************************************************************
    public void verifyFalse(Boolean condition, String conditionInString) {
           
           try{
                 Assert.assertFalse(condition);     
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString + " is true", "Pass");
           }
           catch(AssertionError ex)
           {
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString + " is false", "Fail");
                 testStatus = false;
           }
    }
    
	// *****************************************************************************************
    // * Name : assertEquals
    // * Description : This method verifies if the given two objects are equal
    // * Author : Prakash Bastola
    // * Input Params : actual -> the actual value
    //*                 expected -> the expected value
    //*					conditionInString -> condition expressed in string
    // * Return Values : boolean
    // *****************************************************************************************
    public void assertEquals(Object actual, Object expected, String conditionInString) {
           
           try{
                 Assert.assertEquals(actual, expected);     
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString + " is true", "Pass");
           }
           catch(AssertionError ex)
           {
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString + " is false", "Fail");
                 throw new AssertionError();
           }
    }
    
 // *****************************************************************************************
    // * Name : assertEquals
    // * Description : This method verifies if the given two objects are equal
    // * Author : Prakash Bastola
    // * Input Params : actual -> the actual value
    //*                 expected -> the expected value
    //*					conditionInString -> condition expressed in string
    // * Return Values : boolean
    // *****************************************************************************************
    public void verifyEquals(Object actual, Object expected, String conditionInString) {
           
           try{
                 Assert.assertEquals(actual, expected);     
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString + " is true", "Pass");
           }
           catch(AssertionError ex)
           {
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString + " is false", "Fail");
                 testStatus = false;
           }
    }
    
	// *****************************************************************************************
    // * Name : assertEquals
    // * Description : This method verifies if the given two objects are equal
    // * Author : Prakash Bastola
    // * Input Params : actual -> the actual value
    //*                 expected -> the expected value
    //*					conditionInString -> condition expressed in string
    // * Return Values : boolean
    // *****************************************************************************************
    public void assertEquals(String actual, String expected, String conditionInString) {
           
           try{
                 Assert.assertEquals(actual, expected);     
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString + " is true", "Pass");
           }
           catch(AssertionError ex)
           {
                 Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
                               conditionInString + " is false", "Fail");
                 throw new AssertionError();
           }
    }
    


    
    // * Name : Shridhar Patil
    
    
    // *****************************************************************************************
	// * Name : assertNotNull
	// * Description : This method verifies if the given object is null
	// * Author : Prakash Bastola
	// * Input Params : object -> object to be verified
	//*                 conditionInString -> condition expressed in string
	// * Return Values : boolean
	// *****************************************************************************************
//     public void assertNotNull(Object object, String conditionInString) {
//		
//		try{
//			Assert.assertNotNull(object);	
//			Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
//					conditionInString + " is true", "Pass");
//		}
//		catch(AssertionError ex)
//		{
//			Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
//					conditionInString + " is false", "Fail");
//			throw new AssertionError();
//		}
//	}
//     
//     public void validate(Boolean condition, String conditionInString, String actualCondition, boolean takeScreenShot) {
//         
//         try{
//               Assert.assertTrue(condition);    
//               Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString,
//             		  actualCondition , "Pass", takeScreenShot);
//         }
//         catch(AssertionError ex)
//         {
//               Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString ,
//             		  actualCondition, "Fail", takeScreenShot);
//               testStatus = false;
//
//         }
//  }
     
//     public void validate(Boolean condition, String conditionInString, boolean takeScreenShot) {
//         
//         try{
//               Assert.assertTrue(condition);    
//               Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
//            		   conditionInString + " is true" , "Pass", takeScreenShot);
//         }
//         catch(AssertionError ex)
//         {
//               Reporter.fnWriteToHtmlOutput("Verifying the condition: " + conditionInString , conditionInString + " should be true",
//            		   conditionInString + " is false", "Fail", takeScreenShot);
//               testStatus = false;
//
//         }
//  }
}
