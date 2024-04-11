package org.fleetopgroup.persistence.serviceImpl;

import java.util.HashMap;

import org.fleetopgroup.web.util.ValueObject;

public interface IVehicleAgentIncomeExpenseDetailsService {

	public void processVehicleAgentIncomeExpense(ValueObject	valueObject) throws Exception;
	
	public void startThreadForVehicleAgentIncomeExpense(ValueObject valueObject)throws Exception;
	
	public void deleteVehicleAgentIncomeExpense(ValueObject	valueObject) throws Exception;
	
	public void processVehicleAgentIncomeExpense(HashMap<Long, ValueObject>	valueObject) throws Exception;
	
	public ValueObject	getVehicleAgentPaymentDetails(ValueObject	valueObject) throws Exception;
	
	public Double	getVehicleAgentClosingBalanceByDate(Integer vid, String txnDate) throws Exception;
	
	public void	updatePaymentDone(Integer	vid, String date)throws Exception;
}
