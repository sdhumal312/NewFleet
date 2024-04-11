package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VendorMarketHireToCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendorMarketHireToChargesRepository extends JpaRepository<VendorMarketHireToCharges, Long> {
	
	@Modifying
	@Query("UPDATE VendorMarketHireToCharges SET markForDelete = 1 where hireToChargesId = ?1 AND companyId = ?2")
	public void deleteMarketHireCharges(Long hireToChargesId, Integer companyId) throws Exception;
	
	@Query("SELECT SUM(amount) FROM  VendorMarketHireToCharges  where lorryHireDetailsId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public Double getMarketHireCharges(Long hireToChargesId, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE VendorMarketHireToCharges SET markForDelete = 1 where lorryHireDetailsId = ?1 AND companyId = ?2")
	public void deleteMarketHireChargesById(Long lorryHireDetailsId, Integer companyId) throws Exception;
	
	

	@Query("FROM VendorMarketHireToCharges VM WHERE VM.hireToChargesId = ?1 AND VM.companyId = ?2 ")
	public VendorMarketHireToCharges getAmountFromVendorMarketHireToCharges(long hireToChargesId, Integer company_id) throws Exception;
	
}
