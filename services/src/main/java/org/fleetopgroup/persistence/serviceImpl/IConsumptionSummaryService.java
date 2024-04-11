package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;


public interface IConsumptionSummaryService {
	
	public ValueObject getFuelConsumptionCount(ValueObject object)throws Exception;

	public ValueObject getUreaConsumptionCount(ValueObject object)throws Exception;
	
	public ValueObject getUpholsteryConsumptionCount(ValueObject object)throws Exception;
	
	public ValueObject getPartConsumptionCount(ValueObject object)throws Exception;
	
	public Long getTyreConsumptionCount(ValueObject object)throws Exception;
	
	public Long getBatteryConsumptionCount(ValueObject object)throws Exception;
	
	public ValueObject getRefreshmentConsumptionCount(ValueObject object)throws Exception;

	public ValueObject getConsumptionSummaryData(ValueObject object)throws Exception;
	
}