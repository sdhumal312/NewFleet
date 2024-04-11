package org.fleetopgroup.persistence.report.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleProfitAndLossReportDto;
import org.fleetopgroup.persistence.model.MonthWiseVehicleIncome;

public interface MonthWiseVehicleIncomeDao {

	public void deleteMonthWiseVehicleIncome(Integer vid, Timestamp	startDate, Integer companyId, Integer incomeId, Double incomeAmount) throws Exception;
	
	public List<VehicleProfitAndLossReportDto>  getMonthIncomeDetailsByVid(Integer vid, Timestamp startDateOfMonth, Integer companyId) throws Exception;
	
	public List<VehicleProfitAndLossReportDto>  getMonthIncomeDetailsByVehicleGroupId(long vehicleGroupId, Timestamp startDateOfMonth, Integer companyId) throws Exception;
	
	public MonthWiseVehicleIncome validateMonthWiseVehicleIncome(Integer vid, String	startDate, Integer companyId, short incomeType) throws Exception;
	
	public MonthWiseVehicleIncome validateMonthWiseVehicleIncome(Integer vid, String	startDate, Integer companyId, Integer incomeId, short type) throws Exception;
	
}
