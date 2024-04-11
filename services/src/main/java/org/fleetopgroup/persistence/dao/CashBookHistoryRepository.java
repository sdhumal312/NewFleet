package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.CashBookHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashBookHistoryRepository extends CrudRepository<CashBookHistory,Long > {

	
}
