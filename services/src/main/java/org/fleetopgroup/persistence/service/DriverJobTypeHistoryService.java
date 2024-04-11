package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.DriverJobTypeHistoryRepository;
import org.fleetopgroup.persistence.model.DriverJobTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IDriverJobTypeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("DriverJobTypeHistoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DriverJobTypeHistoryService implements IDriverJobTypeHistoryService {
	@Autowired
	private DriverJobTypeHistoryRepository driverJobTypeHistoryRepository;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addDriverJobTypeHistory(DriverJobTypeHistory driverJobTypeHistory) throws Exception {
		driverJobTypeHistoryRepository.save(driverJobTypeHistory);
	}
}