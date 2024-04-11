/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.util.List;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.EmployeeDetailsRepository;
import org.fleetopgroup.persistence.model.EmployeeDetails;
import org.fleetopgroup.persistence.serviceImpl.IEmployeeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service
@Transactional
public class EmployeeDetailsService implements IEmployeeDetailsService {

	 @Autowired
	 private EmployeeDetailsRepository DetailsRepository;
	    
	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IEmployeeDetailsService#add_EmployeeDetails(org.fleetop.persistence.model.EmployeeDetails)
	 */
	@Transactional
	public void add_EmployeeDetails(EmployeeDetails empDetails) throws Exception {
		
		DetailsRepository.save(empDetails);
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IEmployeeDetailsService#validate_EmployeeDetails(java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<EmployeeDetails> validate_EmployeeDetails(String employeeID, String EmailID) throws Exception {
		
		return DetailsRepository.validate_EmployeeDetails(employeeID, EmailID);
	}

}
