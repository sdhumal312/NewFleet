package org.fleetopgroup.persistence.service;

import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IBusLocationService {

	public ValueObject insertBusLocationDto(ValueObject vo) throws Exception;

	public ValueObject getBusLocationReport(ValueObject object) throws Exception;

	public ValueObject updateBuslocationOutTime(ValueObject object) throws Exception; 
}
