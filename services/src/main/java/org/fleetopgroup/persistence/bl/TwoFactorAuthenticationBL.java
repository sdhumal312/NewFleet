package org.fleetopgroup.persistence.bl;

import java.util.Date;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TwoFactorAuthenticationDetails;
import org.fleetopgroup.web.util.ValueObject;

public class TwoFactorAuthenticationBL {

	
	public static TwoFactorAuthenticationDetails getTwoFactorAuthenticationBLDTO(ValueObject	valueObject) throws Exception{
		
		TwoFactorAuthenticationDetails		authenticationDetails		= null;
		CustomUserDetails					userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			
			authenticationDetails	= new TwoFactorAuthenticationDetails();
			if(valueObject.getLong("authenticationDetailsId", 0) > 0)
				authenticationDetails.setAuthenticationDetailsId(valueObject.getLong("authenticationDetailsId", 0));
			authenticationDetails.setUserId(valueObject.getLong("userId"));
			authenticationDetails.setOtpRequiredType(valueObject.getShort("otpRequiredType"));
			if(valueObject.containsKey("mobileNumber"))
				authenticationDetails.setMobileNumber(valueObject.getString("mobileNumber"));
			if(valueObject.containsKey("email"))
				authenticationDetails.setEmail(valueObject.getString("email"));
			authenticationDetails.setEnabled(true);
			//authenticationDetails.setOtpValidatedOn(DateTimeUtility.getDateFromString(valueObject.getString("otpValidatedOn"), "yyyy-MM-dd hh:mm:ss"));
			authenticationDetails.setCreated(new Date());
			authenticationDetails.setLastModified(new Date());
			authenticationDetails.setCreatedById(userDetails.getId());
			authenticationDetails.setLastModifiedBy(userDetails.getId());
			authenticationDetails.setCompanyId(userDetails.getCompany_id());
			
			
			
			return authenticationDetails;
		} catch (Exception e) {
			throw e;
		}finally {
			authenticationDetails		= null;
			userDetails					= null;
		}
	}
}
