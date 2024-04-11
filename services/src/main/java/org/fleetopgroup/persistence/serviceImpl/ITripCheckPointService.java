package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;
import org.springframework.web.multipart.MultipartFile;

public interface ITripCheckPointService {
	
	public ValueObject getAllTripCheckPointParameter(ValueObject object)throws Exception;

	public ValueObject saveTripCheckPointParameter(ValueObject valueInObject)throws Exception;

	public ValueObject deleteTripCheckPointParameter(ValueObject valueObject)throws Exception;
	
	public ValueObject getRouteWiseTripCheckPoint(ValueObject object) throws Exception;
	
	public ValueObject saveTripCheckPoint(ValueObject valueInObject)throws Exception;

	public ValueObject inspectTripCheckPointParameter(ValueObject valueObject,MultipartFile[] file)throws Exception;

	public ValueObject deleteTripCheckPoint(ValueObject valueObject)throws Exception;

	public ValueObject getInspectedTripCheckPointParameter(ValueObject valueObject)throws Exception;

	public ValueObject getAvailabilityOfTripCheckPointParamterInInspection(ValueObject valueObject)throws Exception;

	public ValueObject getAvailabilityOfTripCheckPointInInspection(ValueObject valueObject)throws Exception;

	public ValueObject removeInspectedParameter(ValueObject valueObject)throws Exception;

	

	
}
