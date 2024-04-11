package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleAgentIncomeExpenseDetailsDto;

public interface VehicleAgentIncomeExpenseDetailsDao {

	public void updateClosingBalanceAmountForNextDates(Double amount , String txnDate, Integer vid) throws Exception;
	
	public void updateClosingBalanceAmount(Double amount , String txnDate, Integer vid) throws Exception;
	
	public List<VehicleAgentIncomeExpenseDetailsDto> getVehicleAgentPendingPayment(Integer vid, String fromDate, String toDate)throws Exception;
	
	public Double	getVehicleAgentOpeningClosingBalanceByDate(Integer vid, String txnDate) throws Exception;
	
	public void updatePaymentDone(Integer vid, String date) throws Exception;
}
