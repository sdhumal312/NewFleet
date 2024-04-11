package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossReportDto;
import org.fleetopgroup.persistence.model.MonthWiseVehicleExpense;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IVehicleProfitAndLossService {
	
	public void runThreadForVehicleExpenseDetails(ValueObject	valueObject) throws Exception;
	
	public void runThreadForDateWiseVehicleExpenseDetails(ValueObject	valueObject) throws Exception;
	
	public void runThreadForMonthWiseVehicleExpenseDetails(ValueObject	valueObject) throws Exception;
	
	public void runThreadForDateWiseVehicleBalanceDetails(ValueObject	valueObject) throws Exception;
	
	public void runThreadForMonthWiseVehicleBalanceDetails(ValueObject	valueObject) throws Exception;
	
	public void runThreadForDeleteVehicleExpenseDetails(ValueObject	valueObject) throws Exception;
	
	public void runThreadForDeleteDateWiseVehicleExpenseDetails(ValueObject	valueObject) throws Exception;
	
	public void runThreadForDeleteMonthWiseVehicleExpenseDetails(ValueObject	valueObject) throws Exception;
	
	public void runThreadForDeleteDateWiseVehicleExpenseBalance(ValueObject	valueObject) throws Exception;
	
	public void runThreadForDeleteMonthWiseVehicleExpenseBalance(ValueObject	valueObject) throws Exception;
	
	public void runThreadForTripSheetExpenses(ValueObject	valueObject) throws Exception;
	
	public void runThreadForTripSheetIncome(ValueObject	valueObject) throws Exception;
	
	public void runThreadForVehicleIncomeDetails(ValueObject	valueObject) throws Exception;

	public void runThreadForMonthWiseIncomeDetails(ValueObject	valueObject) throws Exception;
	
	public void runThreadForMonthWiseIncomeBalance(ValueObject	valueObject) throws Exception;
	
	public void runThreadForDeleteIncome(ValueObject	valueObject) throws Exception;
	
	public List<MonthWiseVehicleExpenseDto>	getMonthWiseVehicleExpenseByVid(Integer vid, Timestamp startDateOfMonth, Integer companyId) throws Exception;
	
	public List<VehicleProfitAndLossReportDto> getMonthIncomeDetails(Integer vid, Timestamp startDateOfMonth, Integer companyId) throws Exception;
	
	public void runThreadForDeleteExpenseDetailsOFTripSheet(ValueObject	valueObject) throws Exception;
	
	public List<MonthWiseVehicleExpenseDto>	getMonthWiseGroupExpenseByVid(long vehicleGroupId, Timestamp startDateOfMonth, Integer companyId) throws Exception;
	
	public List<VehicleProfitAndLossReportDto> getMonthIncomeDetailsByVehicleGroupId(long vehicleGroupId, Timestamp startDateOfMonth, Integer companyId) throws Exception;
	
	public List<TripSheetIncomeDto> getVehicleIncomeDetailsOfMonthByIncomeId(TripSheetIncomeDto	incomeDto) throws Exception;
	
	public List<TripSheetExpenseDto> getVehicleExpenseDetailsOfMonthByExpennseId(TripSheetIncomeDto	incomeDto) throws Exception;
	
	public List<DriverAttendanceDto>  getDriverAttandanceOfMonth(TripSheetIncomeDto	incomeDto) throws Exception;
	
	public void updateIsFinallyEntered() throws Exception;
	
	public void deleteVehicleExpenseTxnChecker() throws Exception;
	
	public List<VehicleProfitAndLossReportDto> getIncomeDetailsForDateRange(Integer vid, String fromDate, String toDate, Integer companyId) throws Exception;
	
	public void updateIsFinallyEnteredForIncome() throws Exception;
	
	public void deleteVehicleExpenseIncomeTxnChecker() throws Exception;
	
	public List<DateWiseVehicleExpenseDto>	getDateWiseVehicleExpenseDtoByVid(Integer vid, String fromDate, String toDate, Integer companyId) throws Exception;
	
	public List<DateWiseVehicleExpenseDto>	getDateWiseDriverSalaryByVid(Integer vid, String fromDate, String toDate, Integer companyId) throws Exception;
	
	public List<MonthWiseVehicleExpenseDto>	getMonthWiseDriverSalaryByVid(Integer vid, String fromDate, String toDate, Integer companyId) throws Exception;
	
	public List<DateWiseVehicleExpenseDto>	getDateWiseDriverSalaryByRouteId(Integer vid, String fromDate, String toDate, Integer companyId, Integer routeId) throws Exception;
	
	public List<DateWiseVehicleExpenseDto>	getDateWiseDriverSalaryByVehicleTypeId(Integer vid, String fromDate, String toDate, Integer companyId, Long vehicleTypeId) throws Exception;
	
	public List<DateWiseVehicleExpenseDto>	getDateWiseDriverSalaryByVTRouteId(Integer vid, String fromDate, String toDate, Integer companyId, Integer routeId, Long vehicleTypeId) throws Exception;
	
	public List<DateWiseVehicleExpenseDto>	getDateWiseVehicleTripExpenseDtoByVid(Integer vid, String fromDate, String toDate) throws Exception;
	
	
	public List<MonthWiseVehicleExpenseDto>	getMonthWiseVehicleTripExpenseDtoByVid(Integer vid, String fromDate, String toDate) throws Exception;
	
	public List<DateWiseVehicleExpenseDto>	getDateWiseVehicleTripExpenseDtoByRouteId(Integer vid, String fromDate, String toDate, Integer routeId) throws Exception;

	public List<DateWiseVehicleExpenseDto>	getDateWiseVehicleTripExpenseDtoByVehicleTypeId(Integer vid, String fromDate, String toDate, Long vehicleTypeId) throws Exception;

	public List<DateWiseVehicleExpenseDto>	getDateWiseVehicleTripExpenseDtoByVTRouteId(Integer vid, String fromDate, String toDate, Long vehicleTypeId, Integer routeId) throws Exception;

	
	public void runThreadForEMIPaymentDetails(ValueObject valueObject) throws Exception;

	public MonthWiseVehicleExpense getMonthWiseVehicleExpenseByExpenseIdAndVid(ValueObject valueObject) throws Exception;

	public void updateMonthWiseVehicleExpenseAmount(ValueObject valueObject) throws Exception;

	public ValueObject getMonthAndDateWiseVehicleExpenseOfTripExpense(ValueObject valueObject) throws Exception;

	public void updateExpenseAmountOfTripExpense(ValueObject valueObject) throws Exception;

}
