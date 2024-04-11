package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;

public interface IUserDetailsCacheService {
	
	public void setOTPUserDetails(String email, String password, String companyCode, Long id) throws Exception;
	
	public void clearUserDetails(Long key);
	
	public ValueObject getUserDetailsByKey(Long key) throws Exception;
}
