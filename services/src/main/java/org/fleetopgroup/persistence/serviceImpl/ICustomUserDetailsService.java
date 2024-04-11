package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.springframework.stereotype.Service;

@Service
public interface ICustomUserDetailsService {

	CustomUserDetails		getCustomUserDetails(Integer companyId) throws Exception;
}
