package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.PartCategoriesHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PartCategoriesHistoryRepository  extends JpaRepository<PartCategoriesHistory, Integer>{
	
}