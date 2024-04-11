package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.PartCategoriesHistoryRepository;
import org.fleetopgroup.persistence.model.PartCategoriesHistory;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("PartCategoriesHistoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PartCategoriesHistoryService implements IPartCategoriesHistoryService {

	@Autowired
	private PartCategoriesHistoryRepository partCategoriesHistoryRepository;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPartCategoriesHistory(PartCategoriesHistory categoriesHistory) throws Exception {
		partCategoriesHistoryRepository.save(categoriesHistory);
	}
}