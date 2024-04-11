package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ICustomUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements ICustomUserDetailsService {

	@Override
	public CustomUserDetails getCustomUserDetails(Integer companyId) throws Exception {
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= new CustomUserDetails();
			
			userDetails.setCompany_id(companyId);
			userDetails.setId(0);
			
			return userDetails;
		} catch (Exception e) {
			throw e;
		}
	}

}
