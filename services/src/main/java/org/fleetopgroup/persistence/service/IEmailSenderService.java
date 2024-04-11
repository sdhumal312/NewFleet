package org.fleetopgroup.persistence.service;

public interface IEmailSenderService {

	public void sendFleetopPlusMail(String[] sendTo, String subject, String htmlBody) throws Exception;
	
	public void sendFleetopOtpOnMail(String sendTo, String subject, String messageody,boolean isFromLocal) throws Exception;
}
