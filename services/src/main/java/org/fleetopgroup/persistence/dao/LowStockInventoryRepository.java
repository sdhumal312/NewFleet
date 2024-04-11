package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.LowStockInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LowStockInventoryRepository  extends JpaRepository<LowStockInventory, Long>{
	
	@Modifying
	@Query("UPDATE LowStockInventory SET markForDelete = 1 WHERE partid= ?1 AND unitCost= ?2 AND discount= ?3 AND tax= ?4")
	void updatePriceInLowStockInventory(Long partid, Double unitCost, Double discount, Double tax);

	
	@Query("FROM  LowStockInventory WHERE partid= ?1 AND companyId= ?2 AND locationId = ?3 AND markForDelete = 0")
	LowStockInventory validateLowStockInventory(Long partid, Integer companyId, Integer locationId);
	
	@Modifying
	@Query("update  LowStockInventory SET markForDelete = 1  WHERE lowStockInventoryId= ?1 ")
	public void deleteLowStockInventory(Long lowStockInventoryId) throws Exception;

}
