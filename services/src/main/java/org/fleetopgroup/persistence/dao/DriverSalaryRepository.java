package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.DriverSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DriverSalaryRepository extends JpaRepository<DriverSalary, Long> {

	/**
	 * @param fromDate
	 * @param toDate
	 * @param driver_id
	 * @param driverGroup
	 * @return
	 */
	@Query("From DriverSalary As p where p.SALARY_FROM_DATE= ?1 AND p.SALARY_TO_DATE=?2 AND p.DRIVER_ID=?3 AND p.DRIVER_GROUP_ID =?4 AND p.COMPANY_ID = ?5 AND p.markForDelete = 0")
	public List<DriverSalary> ValidateDriverSalary(Date fromDate, Date toDate, Integer Driver_id, long DriverGroup, Integer companyId);

	@Query("From DriverSalary As p  where p.DRIVER_ID=?1 AND p.COMPANY_ID = ?2 AND p.markForDelete = 0 ORDER BY p.DSID ASC")
	public List<DriverSalary> List_Total_OF_DriverSalary_details(Integer dRIVER_ID, Integer companyId);

	@Query("From DriverSalary As p where p.DSID=?1 AND p.COMPANY_ID = ?2 AND p.markForDelete = 0")
	public DriverSalary Get_DriverSalary_details_SALARY_ID(Long sALARY_ADVANCE_ID, Integer companyId);

	@Modifying
	@Query("UPDATE DriverSalary AS P SET P.TOTAL_ADVANCE=?1, P.TOTAL_ADVANCE_DEDUCTION=?2, P.TOTAL_PENALTY_DEDUCTION=?3, P.TOTAL_HANDSALARY=?4,"
			+ " P.SALARY_STATUS_ID =?5, P.TOTAL_PREVIOUS_ADVANCE=?6, P.TOTAL_PENALTY=?7, P.TOTAL_ADVANCE_BALANCE=?8 WHERE P.DSID=?9 AND P.COMPANY_ID = ?10")
	public void UPDATE_DriverSalary_AdvacneAmount_HandAmount_Status(Double netPayAdvance,
			Double TOTAL_ADVANCE_DEDUCTION, Double TOTAL_PENALTY_DEDUCTION, Double netInHandSalary, short status,
			Double previousAdvance, Double totalPenaly, Double advanceBalance, Long sALARY_ADVANCE_ID, Integer companyId);
}
