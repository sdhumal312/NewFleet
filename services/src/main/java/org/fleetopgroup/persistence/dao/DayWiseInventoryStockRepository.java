package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.DayWiseInventoryStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DayWiseInventoryStockRepository extends JpaRepository<DayWiseInventoryStock, Long>{

	@Query("FROM DayWiseInventoryStock where transactionId = ?1 and transactionType = ?2 and markForDelete = 0")
	public DayWiseInventoryStock getDayWiseInventoryStockDetails(Long txnId, short txnTypeId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE DayWiseInventoryStock SET markForDelete =  1 where transactionId = ?1 and transactionType = ?2 ")
	public void deleteDayWiseInventoryStock(Long txnId, short txnTypeId) throws Exception;
	
}
