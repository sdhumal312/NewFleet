package org.fleetopgroup.persistence.report.dao;

import org.fleetopgroup.persistence.model.DateWiseVehicleExpense;
import org.fleetopgroup.web.util.ValueObject;

public interface DateWiseVehicleExpenseDetailsDao {

	public void subtractVehicleExpenseFromPreviousDate(DateWiseVehicleExpense	dateWiseVehicleExpense, Double amount) throws Exception;
	
	public void deleteDateWiseVehicleExpense(ValueObject	valueObject) throws Exception;
	
	public DateWiseVehicleExpense validateDateWiseVehicleExpense(short expenseType, Integer companyId, Integer vid, String	expenseDate) throws Exception;
	
	public DateWiseVehicleExpense validateDateWiseVehicleExpense(Integer expenseId, Integer companyId, Integer vid, String	expenseDate, short expenseType) throws Exception;
}
