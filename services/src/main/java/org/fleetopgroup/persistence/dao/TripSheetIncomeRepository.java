package org.fleetopgroup.persistence.dao;


import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TripSheetIncomeRepository extends JpaRepository<TripSheetIncome, Long> {

	//public void addTripSheetIncome(TripSheetIncome TripSheetIncome) throws Exception;

	@Query("FROM TripSheetIncome T WHERE T.tripincomeID = ?1 AND T.companyId = ?2 AND  T.markForDelete = 0")
	public TripSheetIncome getTripSheetIncome(Long TripSheet_Incomeid, Integer companyId) throws Exception;

	//public List<Double> ReportTripIncomeTotal(String ReportQuery);

	@Modifying
	@Query("UPDATE TripSheetIncome SET markForDelete = 1 WHERE tripincomeID = ?1 AND companyId = ?2")
	public void deleteTripSheetIncome(Long TripSheet_Incomeid, Integer companyId) throws Exception;

	@Modifying
	@Transactional
	@Query("UPDATE TripSheetIncome SET markForDelete = 1 WHERE tripsheet.tripSheetID = ?1 AND companyId = ?2")
	public void deleteTripSheetIncomeById(Long TripSheet_Incomeid, Integer companyId) throws Exception;

	
	@Query("from TripSheetIncome where tripSheetID= ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<TripSheetIncome> findAllTripSheetIncome(Long tripSheetID, Integer	companyId) throws Exception;

	@Query("from TripSheetIncome where tripSheetID= ?2 AND incomeId= ?1 AND companyId = ?3 AND markForDelete = 0")
	public List<TripSheetIncome> ValidateAllTripSheetIncome(Integer IncomeName, Long tripSheetID, Integer companyId) throws Exception;

	@Modifying
	@Query("update TripSheet Set tripTotalincome= ?1 WHERE tripSheetID = ?2")
	public void updateTotalIncome(Double tripTotalIncome, Long tripSheetID) throws Exception;

	
	/**
	 * @param tripSheetID
	 */
	@Modifying
	@Query("UPDATE TripSheetIncome SET markForDelete = 1 WHERE tripSheetID = ?1 AND companyId = ?2")
	public void Delete_TSID_to_TripSheetIncome(Long tripSheetID, Integer companyId);

	@Query("FROM TripSheetIncome  WHERE tripSheetID = ?1 AND incomeId = ?2 AND markForDelete = 0")
	public TripSheetIncome getTripSheetIncomeByTripIdAndIncomeId(Long tripSheetId, Integer incomeId) throws Exception;
	
	@Query("SELECT dispatchLedgerId from TripSheetIncome where tripSheetID= ?1 AND companyId = ?2 AND markForDelete = 0 AND dispatchLedgerId IS NOT NULL")
	public List<Long> findAllTripSheetIncomeForLS(Long tripSheetID, Integer	companyId) throws Exception;

	@Query("FROM TripSheetIncome WHERE incomeId = ?1 AND tripSheetID = ?2 AND created = ?3 AND companyId = ?4 AND markForDelete = 0")
	public TripSheetIncome validateBusIncomeApi(Integer incomeId, Long tripSheetID, Date created, Integer companyId) throws Exception;
}
