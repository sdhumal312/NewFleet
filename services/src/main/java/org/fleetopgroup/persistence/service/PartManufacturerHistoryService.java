package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.PartManufacturerHistoryRepository;
import org.fleetopgroup.persistence.model.PartManufacturerHistory;
import org.fleetopgroup.persistence.serviceImpl.IPartManufacturerHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("PartManufacturerHistoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PartManufacturerHistoryService implements IPartManufacturerHistoryService {

	@Autowired
	private PartManufacturerHistoryRepository partManufacturerHistoryRepository;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPartManufacturerHistory(PartManufacturerHistory partManufacturerHistory) throws Exception {
		partManufacturerHistoryRepository.save(partManufacturerHistory);
	}
}