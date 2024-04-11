package com.fleetop.gpsservice.serviceImpl;

import java.util.Map;

public interface ICompanyConfigurationService {

	
	public Map<String, Object> getCompanyConfiguration(Integer companyId, Integer filter) throws Exception;
}
