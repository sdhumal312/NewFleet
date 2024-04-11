package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripsheetPickAndDrop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripsheetPickAndDropRepository extends JpaRepository<TripsheetPickAndDrop, Long> {
	
	@Query("SELECT BI.tripsheetPickAndDropId From TripsheetPickAndDrop as BI where BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<TripsheetPickAndDrop> getDeployment_Page_TripsheetPickAndDrop(Integer companyId, Pageable pageable);
	
	@Query("SELECT BI.tripsheetPickAndDropId From TripsheetPickAndDrop as BI where BI.companyId = ?1 AND BI.vid = ?2 AND BI.markForDelete = 0")
	public Page<TripsheetPickAndDrop> getVehicleWiseDeployment_Page_TripsheetPickAndDrop(Integer companyId, Integer vid, Pageable pageable);
	
	@Query("FROM TripsheetPickAndDrop  WHERE tripsheetPickAndDropId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public TripsheetPickAndDrop getTripsheetPickAndDropById(Long tripsheetPickAndDropId, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE TripsheetPickAndDrop SET markForDelete = 1 WHERE tripsheetPickAndDropId = ?1 AND companyId = ?2")
	public void deleteTripsheetPickAndDrop(Long tripsheetPickAndDropId, Integer companyId) throws Exception;
	
	@Query("FROM TripsheetPickAndDrop  WHERE tripSheetNumber = ?1 AND companyId = ?2 AND markForDelete = 0")
	public TripsheetPickAndDrop getTripsheetPickAndDropByNumber(Long tripSheetNumber, Integer companyId) throws Exception;
	
	@Query("select COUNT(TS.tripsheetPickAndDropId) from TripsheetPickAndDrop TS where TS.vid = ?1 AND TS.companyId = ?2 AND TS.markForDelete = 0")
	public Long countTripSheet(int vid, int companyId) throws Exception;
}