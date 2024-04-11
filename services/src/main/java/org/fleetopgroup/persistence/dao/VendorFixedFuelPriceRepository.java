package org.fleetopgroup.persistence.dao;

import java.util.Date;

import org.fleetopgroup.persistence.model.VendorFixedFuelPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendorFixedFuelPriceRepository extends JpaRepository<VendorFixedFuelPrice, Long> {

	@Query("From VendorFixedFuelPrice as p where p.VENDORID =?1 AND p.COMPANY_ID = ?2 AND p.markForDelete = 0  ")
	public Page<VendorFixedFuelPrice> Get_Deployment_Page_VendorFixedFuelPrice(Integer vehicleID, Integer companyId, Pageable pageable);
	
	
	@Query("From VendorFixedFuelPrice as p where  p.FID=?1 AND p.FUEL_FIXED_DATE=?2 AND p.VENDORID =?3 AND p.COMPANY_ID = ?4 AND p.markForDelete = 0 ")
	public VendorFixedFuelPrice Validate_Vendor_Fixed_Fuel_value(Long fuel_ID, Date fuel_FIXED_DATE,
			Integer vendorid, Integer companyId);
	
	@Modifying
	@Query("UPDATE VendorFixedFuelPrice as p SET p.markForDelete = 1 where p.VFFID =?1 ")
	public void Delete_VendorFixed_FuelPrice_ID(Long vPID);

	
	@Query("From VendorFixedFuelPrice as p where p.VENDORID=?1 AND p.FID=?2 AND p.FUEL_FIXED_DATE =?3 AND p.COMPANY_ID = ?4 AND p.markForDelete = 0 ")
	public VendorFixedFuelPrice GET_VENDOR_DROPDOWN_FUEL_ADD_FIXED_DETAILS(Integer vendor_id, Long fuel_ID,
			java.sql.Date fuelDate, Integer companyId);

}
