/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.InventoryTyreLifeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface InventoryTyreLifeHistoryRepository extends JpaRepository<InventoryTyreLifeHistory, Long> {

	// public void add_Tyre_Inventory_Amount(InventoryTyreRetreadAmount TyreAmount)
	// throws Exception;

	@Query("From InventoryTyreLifeHistory WHERE TYRE_ID=?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public List<InventoryTyreLifeHistory> find_list_InventoryTyreLifeHistory(Long Tyre_ID, Integer companyId) throws Exception;

	@Query("From InventoryTyreLifeHistory WHERE TYRE_ID=?1 AND LIFE_PERIOD =?2 AND COMPANY_ID = ?3 AND markForDelete = 0")
	public InventoryTyreLifeHistory getInventoryTyreLifeHistoryByLifePeriodAndTyreNumber(Long Tyre_ID,String lifePeriod, Integer companyId) throws Exception;
	
	
	
}
