package org.fleetopgroup.persistence.serviceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.fleetopgroup.persistence.service.IEmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
@PropertySource({ "classpath:email.properties" })
public class EmailSenderServiceImpl implements IEmailSenderService {

	@Autowired private Environment env;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@Override
	public void sendFleetopPlusMail(String[] sendTo, String subject, String htmlBody) throws Exception {

		MimeMessage msg =	setMailConfiguration();
		/*
		 * InternetAddress[] toAddresses = { new InternetAddress() };
		 * 
		 * String[] addresses = toAddress.split(",");
		 */
		InternetAddress[] toAddresses = new InternetAddress[sendTo.length];
	    for (int i = 0; i < sendTo.length; i++) {
	    	toAddresses[i] = new InternetAddress(sendTo[i]);
	    }
		
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject,"UTF-8");
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(htmlBody, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		
		  Map<String, String> mapInlineImages = new HashMap<String, String>();
		     
	      Resource resource = resourceLoader.getResource("classpath:plus.png");
	     
	      mapInlineImages.put("pluslogo", resource.getFile().getAbsolutePath());

		// adds inline image attachments
		if (mapInlineImages != null && mapInlineImages.size() > 0) {
			Set<String> setImageID = mapInlineImages.keySet();
			
			for (String contentId : setImageID) {
				MimeBodyPart imagePart = new MimeBodyPart();
				imagePart.setHeader("Content-ID", "<" + contentId + ">");
				imagePart.setDisposition(MimeBodyPart.INLINE);
				
				String imageFilePath = mapInlineImages.get(contentId);
				try {
					imagePart.attachFile(imageFilePath);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				multipart.addBodyPart(imagePart);
			}
		}

		msg.setContent(multipart);

		Transport.send(msg);
	
		
	}
	
	private MimeMessage setMailConfiguration() throws Exception {
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", env.getProperty("smtp.host"));
		properties.put("mail.smtp.port", env.getProperty("smtp.inlineport"));
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", env.getProperty("smtp.username"));
		properties.put("mail.password", env.getProperty("smtp.password"));

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(env.getProperty("smtp.username"), env.getProperty("smtp.password"));
			}
		};
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		MimeMessage msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(env.getProperty("smtp.username")));
		
		return msg;
	}
	public void sendFleetopOtpOnMail(String sendTo, String subject, String messageody,boolean isFromLocal) throws Exception{
		Session					session					= null;
		 MimeMessage            message                 = null;
		 try {
	      Properties props = System.getProperties();
	      if(isFromLocal) {
	    	    String                  host                    = "";
	    	    String from = sendTo;//this is only for local not for test and live
				host			= "smtp.gmail.com";
				props.setProperty("mail.smtp.starttls.enable","true");
				props.setProperty("mail.smtp.host", host);
				props.setProperty("mail.smtp.auth", "true");
				props.setProperty("mail.smtp.port", "587");
				session = Session.getDefaultInstance(props,  
						new javax.mail.Authenticator() {  
					protected PasswordAuthentication getPasswordAuthentication() {  
						/*
						 * Change in case of local testing...do not commit this file for test and live
						 */
						return new PasswordAuthentication("yourMailId", "yourPassword");  
					}  
				});  
				 message = new MimeMessage(session);
		         message.setFrom(new InternetAddress(from));
			} else{
				message =	setMailConfiguration();
			}
	         // Create a default MimeMessage object.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
	         message.setSubject(subject);
	         message.setText(messageody);
	         // Send message
	         Transport.send(message);
	         //System.out.println("Sent message successfully....");
	      } catch (MessagingException mex) {
	    	  System.out.println("Exception ...."+mex);
	          mex.printStackTrace();
	      }
	}
}
