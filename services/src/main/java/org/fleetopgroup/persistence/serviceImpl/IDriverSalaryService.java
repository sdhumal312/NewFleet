package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.DriverSalaryDto;
import org.fleetopgroup.persistence.model.DriverSalary;

public interface IDriverSalaryService {


	public void Add_Driver_Salary(DriverSalary driverSalary);

	public List<DriverSalary> ValidateDriverSalary(Date fromDate, Date toDate, Integer Driver_id, long DriverGroup, Integer companyId);

	/**
	 * @param query
	 * @return
	 */
	public List<DriverSalaryDto> Driver_MONTH_WISE_ESI_PF_ReportDate(String query, Integer companyId);

	public Object[] GET_TOTAL_MONTHBASED_SALARY_ESI_PF_AMOUNT(String lastMonthFROM, String lastMonthTo);

	public List<DriverSalaryDto> List_Total_OF_DriverSalary_details(Integer dRIVER_ID, Integer companyId);

	public DriverSalaryDto Get_DriverSalary_details_SALARY_ID(Long sALARY_ADVANCE_ID, Integer companyId) throws Exception;

	public void UPDATE_DriverSalary_AdvacneAmount_HandAmount_Status(Double netPayAdvance,  Double TOTAL_ADVANCE_DEDUCTION,  Double TOTAL_PENALTY_DEDUCTION,  Double netInHandSalary,
			short statusId, Double previousAdvance, Double totalPenaly, Double advanceBalance, Long sALARY_ADVANCE_ID, Integer companyId);

	public List<DriverSalaryDto> Driver_MONTH_WISE_ATTENDANCE_ReportDate(String dRIVER_GROUP, String dateRangeFrom,
			String dateRangeTo, Integer companyId);


}
