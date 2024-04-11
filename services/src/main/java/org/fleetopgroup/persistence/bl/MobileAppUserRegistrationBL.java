package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.model.MobileAppUserRegistration;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;

public class MobileAppUserRegistrationBL {

	public MobileAppUserRegistration getMobileAppUserRegistrationDto(ValueObject valueInObject)  throws Exception {
		
		MobileAppUserRegistration		mobileAppUserRegistration			= null;
		
		try {
			mobileAppUserRegistration = new MobileAppUserRegistration();
			
			mobileAppUserRegistration.setUserId(valueInObject.getLong("userId",0));
			mobileAppUserRegistration.setCompanyId(valueInObject.getLong("companyId",0));
			mobileAppUserRegistration.setMacAddress(valueInObject.getString("macAddress",null));
			mobileAppUserRegistration.setIpAddress(valueInObject.getString("ipAddress",null));
			mobileAppUserRegistration.setMobileNumber(valueInObject.getString("mobileNumber",null));
			mobileAppUserRegistration.setMobileUniqueId(valueInObject.getString("mobileUniqueId",null));
			mobileAppUserRegistration.setDeviceName(valueInObject.getString("deviceName",null));
			mobileAppUserRegistration.setName(valueInObject.getString("name",null));
			mobileAppUserRegistration.setEmailId(valueInObject.getString("emailId",null));
			mobileAppUserRegistration.setUserAddress(valueInObject.getString("userAddress",null));
			mobileAppUserRegistration.setActiveOrDeactive(valueInObject.getBoolean(true));
			mobileAppUserRegistration.setTokenForNotification(valueInObject.getString("tokenForNotification",null));
			try {
				mobileAppUserRegistration.setRegistrationDateTime(DateTimeUtility.getCurrentTimeStamp());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mobileAppUserRegistration;
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			throw e;
		} finally {
			
		}
	}

}
