package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VendorFixedLaundryRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendorFixedLaundryRateRepository extends JpaRepository<VendorFixedLaundryRate, Long>{

	@Query("From VendorFixedLaundryRate as p where p.vendorId =?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public Page<VendorFixedLaundryRate> Get_Deployment_Page_VendorFixedLaundryPrice(Integer vendorId, Integer companyId, Pageable pageable);

	@Modifying
	@Query("UPDATE VendorFixedLaundryRate SET markForDelete = 1 where vendorLaundryRateId = ?1 AND companyId = ?2")
	public void deleteLaundryRate(Long rateId, Integer companyId);
	
	@Query("From VendorFixedLaundryRate as p where p.clothTypesId = ?1 AND p.vendorId =?2 AND p.markForDelete = 0")
	public List<VendorFixedLaundryRate> validateVendorFixedLaundryRate(Long clothTypesId, Integer vendorId) throws Exception;

}
