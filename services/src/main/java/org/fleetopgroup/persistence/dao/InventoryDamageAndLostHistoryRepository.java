package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.InventoryDamageAndLostHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryDamageAndLostHistoryRepository extends JpaRepository<InventoryDamageAndLostHistory, Long> {
	
	@Query("SELECT BI.InventoryDamageAndLostHistoryId From InventoryDamageAndLostHistory as BI where BI.companyId = ?1 "
			+ " AND BI.clothTypesId = ?2 AND BI.wareHouseLocation = ?3 AND BI.markForDelete = 0")
	public Page<InventoryDamageAndLostHistory> getDeployment_Page_ShowInDamageAndLost(Integer companyId, long clothTypeId, int locationId, Pageable pageable);
	
}