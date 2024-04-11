package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.DriverSalaryAdvance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DriverSalaryAdvanceRepository extends JpaRepository<DriverSalaryAdvance, Long> {

	/*@Modifying
	@Query("update DriverSalaryAdvance DDT set DDT.ADVANCE_STATUS = ?1 where DDT.DRIVER_ID = ?2")
	public void update_DriverSalaryAdvance_Status(String advance_Status, Integer driver_ID) throws Exception;
*/
	@Query("FROM DriverSalaryAdvance AS DDT where DDT.DRIVER_ID = ?1 AND COMPANY_ID = ?2 AND DDT.markForDelete=0 ")
	public List<DriverSalaryAdvance> List_Total_OF_DriverSalaryAdvance_details(Integer driver_ID, Integer companyId);

	/*@Query("FROM DriverSalaryAdvance AS DDT where DDT.DRIVER_ID = ?1 AND DDT.ADVANCE_STATUS = ?2 AND DDT.COMPANY_ID = ?3  AND DDT.markForDelete=0 ")
	public List<DriverSalaryAdvance> GET_DriverSalaryAdvance_details_DRIVER_ID(Integer dRIVER_ID, String ADVANCE_STATUS, Integer companyId);
*/
	@Modifying
	@Query("update DriverSalaryAdvance DDT set DDT.ADVANCE_BALANCE = ?1 where DDT.DSAID = ?2 AND DDT.COMPANY_ID = ?3")
	public void update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK(Double paidAmountAdvacne, Long dsaid, Integer companyId);

	@Modifying
	@Query("update DriverSalaryAdvance DDT set DDT.ADVANCE_BALANCE = ?1 , DDT.ADVANCE_STATUS_ID = ?2 where DDT.DSAID = ?3 AND DDT.COMPANY_ID = ?4")
	public void update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK_UPDATE_STATUS(Double paidAmountAdvacne,
			short aDVANCE_STATUS, Long dsaid, Integer companyId);

	@Query("FROM DriverSalaryAdvance AS DDT where DDT.DSAID = ?1 AND DDT.COMPANY_ID = ?2 AND DDT.markForDelete = 0")
	public DriverSalaryAdvance GET_DRIVER_SALARY_ADVANCE_ID(Long dRIVER_SALARYID, Integer companyId);

	@Modifying
	@Query("UPDATE DriverSalaryAdvance AS DDT set DDT.markForDelete=1 WHERE DDT.DSAID = ?1 AND COMPANY_ID = ?2")
	public void DELETE_DRIVER_SALARY_ADVANCE_ID(Long dRIVER_SALARYID, Integer companyId);

	@Modifying
	@Query("UPDATE DriverSalaryAdvance AS DDT set DDT.markForDelete=1  WHERE DDT.DSAID = ?1")
	public void DELETE_PENALTY_IN_TRIPSHEET_AID(Long advance_ID);

	@Modifying
	@Query("UPDATE DriverSalaryAdvance set markForDelete =1  WHERE TRIPDAILYID = ?1")
	public void DELETE_PENALTY_IN_TRIPDailySheet_Delete_ID(Long tripCollectionID);
	
	@Query("FROM DriverSalaryAdvance AS DDT where DDT.TRIPDAILYID = ?1 AND COMPANY_ID = ?2 AND DDT.markForDelete=0 ")
	public List<DriverSalaryAdvance> getDriverSalaryAdvanceByTripSheetId(Long tripSheetId, Integer companyId);
	
	@Modifying
	@Query("UPDATE DriverSalaryAdvance set markForDelete = 1  WHERE TRIPDAILYID = ?1")
	public void removeDriverPenalty(Long tripsheetId)throws Exception;


}
