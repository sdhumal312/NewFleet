package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.DriverSalaryInsertionDetailsRepository;
import org.fleetopgroup.persistence.model.DriverSalaryInsertionDetails;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryInsertionDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverSalaryInsertionDetailsService implements IDriverSalaryInsertionDetailsService {

	private @Autowired	DriverSalaryInsertionDetailsRepository		insertionDetailsRepository;

	@Override
	public void saveDriverSalaryInsertionDetails(DriverSalaryInsertionDetails insertionDetails) throws Exception {
		
		insertionDetailsRepository.save(insertionDetails);
	}
}
