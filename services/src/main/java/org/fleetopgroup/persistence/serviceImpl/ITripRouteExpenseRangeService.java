package org.fleetopgroup.persistence.serviceImpl;

import java.util.HashMap;

import org.fleetopgroup.web.util.ValueObject;


public interface ITripRouteExpenseRangeService {

	public ValueObject saveTripRouteExpenseRange(ValueObject valueObject) throws Exception;
	
	public ValueObject getTripExpenseListToSetRouteWiseExpenseRange(ValueObject valueInObject)throws Exception;

	public ValueObject getExpenseRangeByRouteIdAndExpenseId(ValueObject valueInObject)throws Exception;

	public HashMap<Integer, Double>  getExpenseWiseMaxLimit(Integer companyId) throws Exception;
}