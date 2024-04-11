/**
 * 
 */
package org.fleetopgroup.registration.listener;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author fleetop
 *
 */
@PropertySource("classpath:email.properties")
public class SendHTMLEmail {

	@Autowired
	private Environment env;
	
	public Session SendHTMLEmailTO() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host",  env.getProperty("smtp.host"));
		props.put("mail.smtp.port", env.getProperty("smtp.port", Integer.class));

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(env.getProperty("smtp.username"), env.getProperty("smtp.password"));
			}
		});
		return session;
	}
}
