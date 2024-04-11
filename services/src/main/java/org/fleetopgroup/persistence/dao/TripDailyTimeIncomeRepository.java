/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TripDailyTimeIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TripDailyTimeIncomeRepository extends JpaRepository<TripDailyTimeIncome, Long> {

	@Query("FROM TripDailyTimeIncome WHERE TRIPDAILYID =?1 AND markForDelete=0 ")
	public List<TripDailyTimeIncome> findAll_TripDailySheet_ID_TIME_Income(Long tripdailyid);

	@Query("FROM TripDailyTimeIncome WHERE TRIPDAILYID =?1 AND companyId = ?2 AND markForDelete=0  ")
	public List<TripDailyTimeIncome> findAll_TripDaily_TIME_Income(Long tripdailyid, Integer companyId);

	@Query("FROM TripDailyTimeIncome WHERE markForDelete=0 AND incomeId=?1 AND TRIPDAILYID =?2 AND companyId = ?3")
	public List<TripDailyTimeIncome> Validate_TripDaily_TIME_Income(Integer string, Long tripdailyid, Integer companyId);

	@Query("FROM TripDailyTimeIncome WHERE markForDelete=0 AND TDTIMEID =?1 AND companyId = ?2")
	public TripDailyTimeIncome Get_TripDaily_TIME_Income(Long tripIncomeID, Integer companyId);

	@Modifying
	@Query("UPDATE TripDailyTimeIncome SET markForDelete= 1 WHERE TDTIMEID =?1  AND companyId = ?2")
	public void delete_TripDaily_TIME_Income(Long tripincomeID, Integer companyId);

	@Modifying
	@Query("UPDATE TripDailyTimeIncome SET markForDelete=1 WHERE markForDelete=0 AND TRIPDAILYID =?1 ")
	public void delete_TripDailyTimeIncome_TRIPDAILYID(Long tripCollectionID);

	@Modifying
	@Query("UPDATE TripDailyTimeIncome SET incomeAmount = ?1, incomeCollectedById =?2  WHERE  TDTIMEID =?3 ")
	public void Update_TripDailyTimeIncome_Amount_By_TDTIMEID(Double incomeAmount, Long incomeCollectedBy,
			Long tripincomeID);
}
