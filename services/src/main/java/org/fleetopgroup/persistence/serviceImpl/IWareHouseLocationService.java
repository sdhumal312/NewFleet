package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IWareHouseLocationService{
	
	public ValueObject getWareHouseLocationWiseCostReport(ValueObject valueObject) throws Exception;
	
	
}