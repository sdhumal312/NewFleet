package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.LoadingSheetToTripSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoadingSheetToTripSheetRepository extends JpaRepository<LoadingSheetToTripSheet, Long>{

	@Query("FROM LoadingSheetToTripSheet where dispatchLedgerId = ?1 AND tripSheetId = ?2 AND markForDelete = 0 ")
	public List<LoadingSheetToTripSheet> getLoadingSheetToTripSheetByDispatchLedgerId(Long dispatchLedgerId, Long tripSheetId) throws Exception ;
	
	
	@Query("FROM LoadingSheetToTripSheet where tripSheetId = ?1 AND markForDelete = 0")
	public List<LoadingSheetToTripSheet> getLoadingSheetToTripSheetByTripSheetId(Long dispatchLedgerId) throws Exception ;
}
