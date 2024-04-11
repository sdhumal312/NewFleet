/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TripDailyIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TripDailyIncomeRepository extends JpaRepository<TripDailyIncome, Long> {

	@Query("FROM TripDailyIncome WHERE markForDelete=0 AND TRIPDAILYID =?1 ")
	public List<TripDailyIncome> findAll_TripDailySheet_ID_Income(Long tripdailyid);

	@Query("FROM TripDailyIncome WHERE markForDelete=0 AND TRIPDAILYID =?1 AND companyId = ?2")
	public List<TripDailyIncome> findAll_TripDaily_Income(Long tripdailyid, Integer companyId);

	@Query("FROM TripDailyIncome WHERE markForDelete=0 AND incomeId=?1 AND TRIPDAILYID =?2 AND companyId = ?3")
	public List<TripDailyIncome> Validate_TripDaily_Income(Integer string, Long tripdailyid, Integer companyId);

	@Query("FROM TripDailyIncome WHERE markForDelete=0 AND tripincomeID =?1 AND companyId = ?2")
	public TripDailyIncome Get_TripDaily_Income(Long tripIncomeID, Integer companyId);

	@Modifying
	@Query("UPDATE TripDailyIncome SET markForDelete=1 WHERE tripincomeID =?1 AND companyId = ?2")
	public void delete_TripDaily_Income(Long tripincomeID, Integer companyId);

	@Modifying
	@Query("UPDATE TripDailyIncome SET markForDelete=1 WHERE markForDelete=0 AND TRIPDAILYID =?1 AND companyId = ?2")
	public void delete_TripDailyIncome_TRIPDAILYID(Long tripCollectionID, Integer companyId);

	@Modifying
	@Query("UPDATE TripDailyIncome SET incomeAmount = ?1, incomeCollectedById =?2  WHERE  tripincomeID =?3 AND companyId = ?4")
	public void Update_TripDailyIncome_Amount_By_tripincomeID(Double incomeAmount, Long incomeCollectedBy,
			Long tripincomeID, Integer companyId);
	
	
	@Query("FROM TripDailyIncome WHERE markForDelete=0 AND TRIPDAILYID =?1 AND incomeId = ?2")
	public TripDailyIncome Get_TripDaily_IncomeByIncomeId(Long tripId, Integer incomeId);

}
