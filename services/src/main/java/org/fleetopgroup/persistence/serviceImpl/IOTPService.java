package org.fleetopgroup.persistence.serviceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.web.util.ValueObject;

public interface IOTPService {

	public int generateOTP(Long key) throws Exception;
	
	public int getOtp(Long key);
	
	public void clearOTP(Long key);
	
	public void validateIdOTPValidated(HttpServletRequest	request, HttpServletResponse response) throws Exception;
	public ValueObject	saveTwoFactorAuthDetails(ValueObject	valueObject) throws Exception;
	public ValueObject	getTwoFactorAuthDetails(ValueObject	valueObject) throws Exception;
	
	public ValueObject resendOTPValidationCode(ValueObject	valueObject) throws Exception;
}
