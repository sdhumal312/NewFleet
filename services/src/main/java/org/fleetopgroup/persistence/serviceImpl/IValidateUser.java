package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;
@Service
public interface IValidateUser {
	
	public ValueObject validateUser(ValueObject valueObjectIn) throws Exception;

	public ValueObject mobileRegistrationOTP(ValueObject object) throws Exception;

	public ValueObject mobileAppUserRegistration(ValueObject valueInObject) throws Exception;
	
	public void updateUserTokenForNotification(ValueObject valueInObject) throws Exception;
	

}
