package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripSheetExpenseRepository extends JpaRepository<TripSheetExpense, Long> {

	// public void addTripSheetExpense(TripSheetExpense TripSheetExpense) throws
	// Exception;

	@Query("FROM TripSheetExpense T WHERE T.tripExpenseID = ?1 AND T.companyId = ?2")
	public TripSheetExpense getTripSheetExpense(Long TripSheet_Expenseid, Integer companyId) throws Exception;

	@Query("FROM TripSheetExpense T WHERE T.fuel_id = ?1")
	public TripSheetExpense getTripSheetExpenseByFuelId(Long fuelId) throws Exception;


	@Modifying
	@Query("UPDATE TripSheetExpense SET markForDelete = 1 WHERE tripExpenseID = ?1 AND companyId = ?2")
	public void deleteTripSheetExpense(Long TripSheet_Expenseid, Integer companyId) throws Exception;

	@Query("from TripSheetExpense where tripSheetID= ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<TripSheetExpense> findAllTripSheetExpense(Long tripSheetID, Integer companyId) throws Exception;

	@Query("from TripSheetExpense where expenseId= ?1 AND tripSheetID= ?2 AND companyId = ?3 AND markForDelete = 0 ")
	public List<TripSheetExpense> ValidateAllTripSheetExpense(Integer ExpenseName, Long tripSheetID, Integer companyId) throws Exception;

	@Modifying
	@Query("update TripSheet Set tripTotalexpense= ?1 WHERE tripSheetID = ?2")
	public void updateTotalExpense(Double tripTotalExpense, Long tripSheetID) throws Exception;

	/**
	 * @param tripSheetID
	 */
	@Modifying
	@Query("UPDATE TripSheetExpense SET markForDelete = 1 WHERE tripSheetID = ?1 AND companyId = ?2")
	public void Delete_TSID_to_TripSheetExpence(Long tripSheetID, Integer companyId);

	@Modifying
	@Query("UPDATE TripSheetExpense SET markForDelete = 1 WHERE DHID = ?1 AND companyId = ?2")
	public void DELETE_TRIPSHEETEXPENSE_DRIVERHALT_ID(Long dhid, Integer companyId);

	/**
	 * @param fuel_id
	 */
	@Modifying
	@Query("DELETE FROM TripSheetExpense WHERE fuel_id = ?1")
	public void DELETE_TRIPSHEETEXPENSE_FUEL_ID(Long fuel_id);
	
	
	@Query("FROM TripSheetExpense  WHERE expenseId = ?1 AND companyId = ?2 AND tripSheetID = ?3")
	public TripSheetExpense getTripSheetExpensebyExpenseId(Integer expenseid, Integer companyId, Long tripSheetID) throws Exception;

	@Modifying
	@Query("Update TripSheetExpense set tripSheetExpense_document_id= ?1, tripSheetExpense_document= ?2 where tripExpenseID= ?3 AND companyId = ?4")
	public void updateTripSheetExpenseDocumentId(Long tripSheetExpenseDocId, boolean tripSheetExpenseDocument,
			Long tripSheetExpenseId, Integer companyId);
	
	@Modifying
	@Query("Update TripSheetExpense set driverId= ?1 where tripSheetID= ?2 AND companyId = ?3 AND markForDelete = 0")
	public void updateDriverInTripSheetExpense(int newDriverId, Long tripSheetId,Integer companyId);
	
	@Modifying
	@Query("Update TripSheetExpense set markForDelete =?3  WHERE fuel_id = ?1 AND companyId = ?2")
	public void updateTripsheetExpenseByFuelId(Long fuelId, Integer companyId, boolean mfd);

	@Query("SELECT SUM(expenseAmount) from TripSheetExpense where tripSheetID = ?1 AND expenseId = ?2 AND companyId = ?3 AND markForDelete = 0")
	public Double getTripExpense_DA_FromTripSheet(Long tripSheetID,Integer expenseId, Integer companyId) throws Exception;
	
}
