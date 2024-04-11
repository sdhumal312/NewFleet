package org.fleetopgroup.persistence.dao;

import java.util.Date;

import org.fleetopgroup.persistence.model.BusBookingDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BusBookingDetailsRepository extends JpaRepository<BusBookingDetails, Long>{

	@Query("SELECT vp.busBookingDetailsId From BusBookingDetails as vp where vp.companyId = ?1 AND vp.markForDelete = 0")
	public Page<BusBookingDetails> getDeployment_Page_BusBookingDetails(Integer companyId, Pageable pageable);
	
	@Modifying
	@Query("UPDATE BusBookingDetails SET markForDelete = 1, lastModifiedById = ?3, lastModifiedOn = ?4 WHERE busBookingDetailsId = ?1 AND companyId = ?2")
	public void deleteBusBookingDetails(Long vendorPaymentId, Integer companyId, Long userId, Date date);
	
	@Modifying
	@Query("UPDATE BusBookingDetails SET tripSheetId = ?1  WHERE busBookingDetailsId = ?2")
	public void updateTripSheetNumber(Long vendorPaymentId, Long companyId);
	
}
