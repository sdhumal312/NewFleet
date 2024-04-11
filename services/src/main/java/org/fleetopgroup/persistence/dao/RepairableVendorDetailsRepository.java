package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.RepairableVendorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RepairableVendorDetailsRepository extends JpaRepository<RepairableVendorDetails, Long>{

	@Modifying
	@Query("UPDATE FROM RepairableVendorDetails set markForDelete = 1 where partId = ?1 ")
	public void deleteBypartId(Long partId) throws Exception;
}
