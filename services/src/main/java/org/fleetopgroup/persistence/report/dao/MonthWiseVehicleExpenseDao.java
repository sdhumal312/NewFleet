package org.fleetopgroup.persistence.report.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.model.MonthWiseVehicleExpense;

public interface MonthWiseVehicleExpenseDao {

	public void subtractPreviousMonthAmount(MonthWiseVehicleExpense		monthWiseVehicleExpense, Timestamp	timestamp, Double amount) throws Exception;
	
	public void subtractPreviousMonthAmount(MonthWiseVehicleExpense		monthWiseVehicleExpense) throws Exception;
	
	public List<MonthWiseVehicleExpenseDto>  getMonthWiseVehicleExpenseForReport(Integer vid, Timestamp startDateOfMonth, Integer companyId) throws Exception;
	
	public List<MonthWiseVehicleExpenseDto>  getMonthWiseGroupExpenseForReport(long vehicleGroupId, Timestamp startDateOfMonth, Integer companyId) throws Exception;
	
	public MonthWiseVehicleExpense validateMonthWiseVehicleExpense(Integer vid, String	startDate, Integer companyId, short expenseType) throws Exception;
	
	public MonthWiseVehicleExpense validateMonthWiseVehicleExpense(Integer vid, String	startDate, Integer companyId, Integer expenseId, short expenseType) throws Exception;
	
	public MonthWiseVehicleExpense getMonthWiseVehicleExpense(Integer vid, String	startDate, Integer companyId, short expenseType) throws Exception;
	
	public MonthWiseVehicleExpense getMonthWiseVehicleExpense(Integer vid, String	startDate, Integer companyId, Integer expenseId, short expenseType) throws Exception;
	
}
