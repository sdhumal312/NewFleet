package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.DriverTrainingTypeHistoryRepository;
import org.fleetopgroup.persistence.model.DriverTrainingTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IDriverTrainingTypeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("DriverTrainingTypeHistoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DriverTrainingTypeHistoryService implements IDriverTrainingTypeHistoryService {
	@Autowired
	private DriverTrainingTypeHistoryRepository driverTrainingTypeHistoryRepository;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addDriverTrainingTypeHistory(DriverTrainingTypeHistory driverTrainingTypeHistory) throws Exception {
		driverTrainingTypeHistoryRepository.save(driverTrainingTypeHistory);
	}
}