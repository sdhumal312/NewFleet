package org.fleetopgroup.persistence.serviceImpl;

public interface IExceptionEmailAlertService {

	public void sendExceptionEmail(Exception e, String className) throws Exception;
	
}
