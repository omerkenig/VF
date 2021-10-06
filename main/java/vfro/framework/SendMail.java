package vfro.framework;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import org.openqa.selenium.WebDriver;

import com.codoid.products.fillo.Recordset;
import com.gargoylesoftware.htmlunit.html.impl.SimpleSelectionDelegate;

import javax.activation.*;

public class SendMail {

	private static HashMap<String, String> Dictionary;
	private static HashMap<String, String> Environment;
	private static Reporting Reporter;
	private WebDriver driver;
	private String driverType;
	private CommonFunctions objCommon; 

	public SendMail(WebDriver GDriver,String DT,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)//, Eyes GEyes)
	{
		Reporter = GReporter;
		driver = GDriver;
		driverType = DT;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		objCommon = new CommonFunctions(driver,driverType,Environment,Reporter);
	}
	
	public static void main(String[] args) {
		
//		   String environment = Environment.get("ENV_CODE");
//		   String className = Environment.get("CLASS_NAME");
//		 String filePath = Environment.get("HTMLREPORTSPATH")+ "/SummaryReport.html"+ className;
		String filePath = "C:\\MetroPCS-EDGE_12.4.3\\Execution\\Regression_CustomerActivities\\null\\madhurda\\CHROME1\\HTML_Reports2020-03-06T13.04.33.609\\SummaryReport.html";
		sendEmail(filePath,"TEST","TEST","Report.html");

	}
	
	public static void sendEmail(String filePath, String environment2, String className, String fileName){

		// TODO Auto-generated method stub
			/*private HashMap<String, String> Environment;
			Environment = GEnvironment;*/
			/*String uat = Environment.get("EMAIL_NOTIFICATION");*/
//				String url =(Environment.get("EDGE_URL"));
//		Recordset rsData = dbAction.getDataFromExcel(calendarFileName, "Market",Environment.get("MARKET"));
				// Recipient's email ID needs to be mentioned.
			      String to = "madhuri.dalvi@amdocs.com";

			      // Sender's email ID needs to be mentioned
			      String from = "madhuri.dalvi@amdocs.com";

			      // Assuming you are sending email from localhost
			      String host = "int.corp.amdocs.com";

			      // Get system properties
			      Properties properties = System.getProperties();

			      // Setup mail server
			      properties.setProperty("mail.smtp.host", host);

			      // Get the default Session object.
			      Session session = Session.getDefaultInstance(properties);

			      try {
			    	  Date date= new Date();
				         SimpleDateFormat format =new SimpleDateFormat("dd-MM-yyyy hh:mm");
				        
				         String date1 = format.format(date);
			         // Create a default MimeMessage object.
			         MimeMessage message = new MimeMessage(session);

			         // Set From: header field of the header.
			         message.setFrom(new InternetAddress(from));

			         // Set To: header field of the header.
			         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			         // Set Subject: header field
			         message.setSubject("Metro by T-Mobile | "+environment2+" | Automation Execution Report | "+className+" | "+date1);

			         // Now set the actual message
			         message.setText("This is actual message");
			         
			         //html
			         // Create the message part 
			         BodyPart messageBodyPart = new MimeBodyPart();

			         // Fill the message
			         messageBodyPart.setText("Hi Team,"+System.lineSeparator()+"Please find automation execution report attached with this email.");
			         
			         // Create a multipar message
			         Multipart multipart = new MimeMultipart();

			         // Set text message part
			         multipart.addBodyPart(messageBodyPart);

			         // Part two is attachment
			         messageBodyPart = new MimeBodyPart();
//			         String filename = "C:/SVN/MetroPCS-EDGE_12.4.3/Execution/MarketOneAuto/null/madhurda/CHROME1/SummaryReport.html";
			        
//			         date1 = date.toLocaleString();
			
			         DataSource source = new FileDataSource(filePath);
			         messageBodyPart.setDataHandler(new DataHandler(source));
//			         messageBodyPart.setFileName("Metro by T-Mobile_"+environment2+"_Automation Execution Report_"+className+"_" + date1+".html");
			         messageBodyPart.setFileName(fileName);
			         
			         multipart.addBodyPart(messageBodyPart);

			         // Send the complete message parts
			         message.setContent(multipart );

			         // Send message
			         Transport.send(message);
			         System.out.println("Sent message successfully....");
			      } catch (MessagingException mex) {
			         mex.printStackTrace();
			      }
	}

}
