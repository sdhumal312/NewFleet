package org.fleetopgroup.persistence.dao;

import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.TripIncome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripIncomeRepository extends JpaRepository<TripIncome, Integer> {

	@Modifying
	@Query("update TripIncome T SET T.incomeName = ?1, T.incomeRemarks = ?2, T.lastModifiedBy = ?3,  T.lastupdated = ?4  where T.incomeID = ?5 and T.companyId = ?6")
	public void updateTripIncome(String incomeName, String incomeRemarks, String lastModifiedBy, Date lastupdated, Integer incomeID, Integer companyId) throws Exception;

	@Query("From TripIncome p where p.companyId = ?1 and p.incomeType=1 AND p.markForDelete=0")
	public List<TripIncome> findAll(Integer companyId);

	@Query("From TripIncome p where p.incomeID = ?1 AND p.companyId = ?2")
	public TripIncome getTripIncome(int IncomeID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE TripIncome T SET T.markForDelete = 1 where T.incomeID = ?1 and T.companyId = ?2")
	public void deleteTripIncome(Integer status, Integer companyId) throws Exception;
	
	@Query("From TripIncome p where p.incomeName = ?1 and p.companyId = ?2 and p.markForDelete = 0")
	public TripIncome  validateTripIncome(String IncomeNAME, Integer companyId) throws Exception;
	
	@Query("From TripIncome TI where TI.companyId = ?1 and TI.markForDelete = 0")
	public List<TripIncome> listTripIncome(Integer companyId) throws Exception;
	
	
	@Query("From TripIncome p where p.companyId = ?1 AND p.incomeType=2 AND p.markForDelete=0")
	public List<TripIncome> listOnlyIncomeName_FixedIncome(Integer companyId);
	
	
	@Query("From TripIncome p where p.companyId = ?1 AND  p.incomeType=3 AND p.markForDelete=0")
	public List<TripIncome> listTrip_CASHBOOK_Income(Integer companyId);

	@Query("From TripIncome p where p.companyId = ?1 AND  p.incomeType=2 AND p.markForDelete=0")
	public List<TripIncome> listTrip_ROUTE_Income(Integer companyId);
	
	@Query("FROM TripIncome TR  where TR.companyId= ?1 AND TR.incomeType=?2 AND TR.markForDelete = 0")
	public Page<TripIncome> getDeployment_Page_TripIncome(Integer company_id, Integer incomeType,  Pageable pageable);

	@Modifying
	@Query("update TripIncome T SET T.incomeName = ?1, T.incomeRemarks = ?2, T.commission =?3 ,T.gst =?4 ,T.lastModifiedById = ?5 where T.incomeID = ?6 and T.companyId = ?7")
	public void updateTripIncomeWithRate(String incomeName, String discription, double commision, double gst,
			long userId ,int incomeId,Integer company_id);

}