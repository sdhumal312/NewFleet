package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.RepairStockToPartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RepairStockToPartDetailsRepository  extends JpaRepository<RepairStockToPartDetails, Long>{

	@Modifying
	@Query("UPDATE RepairStockToPartDetails SET markForDelete = 1 WHERE repairStockToPartDetailsId =?1 AND companyId =?2 ")
	public void removeAdditionalPart(long repairStockToPartDetailsId, int companyId);

}
