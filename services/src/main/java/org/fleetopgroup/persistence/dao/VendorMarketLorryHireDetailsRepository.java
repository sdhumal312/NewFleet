package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VendorMarketLorryHireDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VendorMarketLorryHireDetailsRepository extends JpaRepository<VendorMarketLorryHireDetails, Long>{

	@Query("SELECT vp.lorryHireDetailsId From VendorMarketLorryHireDetails as vp where vp.companyId = ?1 AND vp.markForDelete = 0")
	public Page<VendorMarketLorryHireDetails> getDeployment_Page_VendorLorryHire(Integer companyId, Pageable pageable);


	@Query("FROM VendorMarketLorryHireDetails V WHERE V.companyId = ?1 AND V.lorryHireDetailsId = ?2 AND V.markForDelete = 0")
	public VendorMarketLorryHireDetails getVendorMarketLorryHireDetailsInformation(Integer companyId,Long lorryHireDetailsId);
	

	
	
}
