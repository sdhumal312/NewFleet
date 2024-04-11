package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.RepairToLabourDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RepairToLabourDetailsRepository extends JpaRepository<RepairToLabourDetails, Long>{

	@Modifying
	@Query("UPDATE RepairToLabourDetails SET markForDelete = 1 WHERE repairToLabourDetailsId =?1 AND companyId =?2 ")
	public void removeLabour(long repairToLabourDetailsId, int companyId);

}
