package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.PartPurchaseVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PartPurchaseVendorRepository extends JpaRepository<PartPurchaseVendor, Long>{

	@Modifying
	@Query("UPDATE FROM PartPurchaseVendor set markForDelete = 1 where partId = ?1 ")
	public void deleteByPartId(Long partId) throws Exception;
}
