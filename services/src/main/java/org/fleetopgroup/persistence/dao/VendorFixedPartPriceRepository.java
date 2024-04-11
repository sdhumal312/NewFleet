package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VendorFixedPartPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendorFixedPartPriceRepository extends JpaRepository<VendorFixedPartPrice, Long> {

	@Query("FROM VendorFixedPartPrice AS f WHERE f.PARTID =?1 AND f.VENDORID=?2 AND f.COMPANY_ID = ?3 AND f.markForDelete = 0")
	public VendorFixedPartPrice Validate_Vendor_Fixed_Part_value(long parseLong, Integer vENDOR_ID, Integer companyId);

	@Query("From VendorFixedPartPrice as p where p.VENDORID =?1 AND p.COMPANY_ID = ?2 AND p.markForDelete = 0")
	public Page<VendorFixedPartPrice> Get_Deployment_Page_VendorFixedPartPrice(Integer vehicleID, Integer companyId, Pageable pageable);

	@Modifying
	@Query("UPDATE VendorFixedPartPrice as p SET p.markForDelete = 1 where p.VPPID =?1 AND p.COMPANY_ID = ?2")
	public void Delete_VendorFixedPartPrice_ID(Long vPID, Integer companyId);

	@Query("From VendorFixedPartPrice as p where p.PARTID=?1 AND p.VENDORID =?2 AND p.COMPANY_ID = ?3 AND p.markForDelete = 0")
	public VendorFixedPartPrice Get_VendorFixedPrice_Validate_VendorID_PartId(Long pART_ID, Integer vENDOTID, Integer companyId);

}
