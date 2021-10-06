package vfro.framework;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class WebEmail {
	public static void fEmail(final String auth_emailid, final String auth_password, String internet_add, String body,
			String body2, String Test_Suite_Name,String Environment_Name, String ToEmailId, String CCEmailId) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date = new Date();
		
        
		Properties props = new Properties();

		props.put("mail.smtp.host", "ilmail.corp.amdocs.com");

		/*props.put("mail.smtp.socketFactory.port", "465");

		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");*/
		
		props.put("mail.smtp.user", "omerkenig");
        props.put("mail.smtp.password", auth_password );
        
        props.put("mail.smtp.ssl.trust", "ilmail.corp.amdocs.com");

		props.put("mail.smtp.auth", "true");

		props.put("mail.smtp.port", "587");
		
        props.put("mail.smtp.starttls.enable", "true");
		

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication("sayaliso", auth_password);
			}
		});
		try {

			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(internet_add));
			
			msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(ToEmailId));
			msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(CCEmailId));
			

			msg.setSubject("VFRO | " + Environment_Name+" | Automation Execution | "+Test_Suite_Name+" Status "+ dateFormat.format(date)) ;

			BodyPart part1 = new MimeBodyPart();

			part1.setText("Execution Report " + new Date().getDate() + "-" + (new Date().getMonth() + 1) + "-" + new Date().getYear());

			BodyPart part2 = new MimeBodyPart();

			//String filename = Test_ZipFolder.Output_ZIP_FILE;

			//DataSource src = new FileDataSource(filename);

			//part2.setDataHandler(new DataHandler(src));

			//part2.setFileName(filename);

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(part2);

			multipart.addBodyPart(part1);
			body = body + body2;
			msg.setContent(body, "text/html");
			
			Transport.send(msg);

			System.out.println("=======Email Sent======");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} 
	}
}
