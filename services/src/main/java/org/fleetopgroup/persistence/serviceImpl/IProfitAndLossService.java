package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IProfitAndLossService {

	ValueObject		getVehicleWiseUsageDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject		getGroupWiseUsageDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject		getRouteWiseUsageDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject		getVehicleWiseProfitAndLossReport(ValueObject	valueObject) throws Exception;
	
	ValueObject		getGroupWiseProfitAndLossReport(ValueObject	valueObject) throws Exception;
	
	ValueObject		getVehicleIncomeDetailsOfMonthByIncomeId(ValueObject	valueObject) throws Exception; 
	
	ValueObject		getVehicleExpenseDetailsOfMonthByExpenseId(ValueObject	valueObject) throws Exception; 
	
	ValueObject		getDateWiseVehicleProfitAndLoss(ValueObject 	valueObject) throws Exception;
	
	ValueObject		getVehicleProfitAndLossWithinRange(ValueObject 	valueObject) throws Exception;
	
	public ValueObject getVehicleExpenseDetailsDateRangeExpenseId(ValueObject valueObject) throws Exception;

	ValueObject getRouteWiseProfitAndLoss(ValueObject object)throws Exception;
	
	ValueObject		getIncomDetailsWithinRangeByIncomeId(ValueObject	valueObject) throws Exception;
	
	ValueObject		getVehicleComparisionDetails(ValueObject	valueObject) throws Exception;
}
