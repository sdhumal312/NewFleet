package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripsheetDueAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripsheetDueAmountRepository extends JpaRepository<TripsheetDueAmount, Long>{
	
	@Modifying
	@Query("UPDATE TripsheetDueAmount SET markForDelete = 1 where tripsheetDueAmountId = ?1")
	public void deleteremoveDueAmountById(Long id) throws Exception;
	
	@Query("FROM TripsheetDueAmount T WHERE T.tripsheetDueAmountId = ?1 AND T.companyId = ?2 AND T.markForDelete = 0")
	public TripsheetDueAmount getTripSheetDueAmountById(Long tripsheetDueAmountId, Integer companyId) throws Exception;
	
	
	@Modifying
	@Query("UPDATE TripsheetDueAmount SET markForDelete = 1 where tripSheetID = ?1")
	public void deleteDueAmountByTripSheetId(Long id) throws Exception;
	
}