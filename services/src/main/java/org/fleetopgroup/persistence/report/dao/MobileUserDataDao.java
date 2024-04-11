package org.fleetopgroup.persistence.report.dao;

import org.fleetopgroup.persistence.dto.MobileUserDto;

public interface MobileUserDataDao {
	
	public MobileUserDto getUserDataForMobileApp(String companyCode,String email) throws Exception;

	public boolean getValidateUserRegistraion(MobileUserDto mobileUser) throws Exception;

}
