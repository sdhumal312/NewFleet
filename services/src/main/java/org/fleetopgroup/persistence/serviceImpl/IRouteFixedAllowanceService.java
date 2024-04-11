package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.RouteFixedAllowanceDto;
import org.fleetopgroup.persistence.model.RouteFixedAllowance;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IRouteFixedAllowanceService {

	public ValueObject saveAllowanceDetails(ValueObject valueObject) throws Exception;
	
	List<RouteFixedAllowanceDto>  getRouteFixedAllowanceList(Integer	routeId) throws Exception;
	
	public ValueObject removeAllowanceDetails(ValueObject valueObject) throws Exception;
	
	List<RouteFixedAllowance>  getRouteFixedAllowanceList(Integer	routeId, Integer companyId) throws Exception;
}
