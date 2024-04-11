package org.fleetopgroup.persistence.service;

import java.net.InetAddress;

import javax.mail.internet.MimeMessage;

import org.fleetopgroup.persistence.serviceImpl.IExceptionEmailAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class ExceptionEmailAlertService implements IExceptionEmailAlertService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired	private JavaMailSender mailSender;
	
	@Autowired private Environment env;
	
	@Override
	public void sendExceptionEmail(Exception e, String className) throws Exception {
		mailSender.send(new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws MailParseException  {
				String		errorMsg	= null;
		        InetAddress ip;
				try {
					 ip = InetAddress.getLocalHost();
					String hostName = ip.getHostName();
					errorMsg	= "We have found Exception on Server : "+hostName+" \n in class "+className+" \n on line number : "+e.getStackTrace()[0].getLineNumber()+" \n Exception is : "+e;
					
					MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					messageHelper.setTo(env.getProperty("developer.email"));										
					messageHelper.setSubject("Fleetop Exception Alert");
					messageHelper.setText(errorMsg);
					

				}catch (Exception e) {
					LOGGER.error("Exception " + e);
					e.printStackTrace();
				}

			}

	  });

	}
}
