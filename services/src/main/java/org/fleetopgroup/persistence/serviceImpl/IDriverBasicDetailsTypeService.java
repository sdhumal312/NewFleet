package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.DriverBasicDetailsType;
import org.fleetopgroup.web.util.ValueObject;

public interface IDriverBasicDetailsTypeService {
	
	public ValueObject getDriverAllBasicDetailsTypeList()throws Exception;

	public DriverBasicDetailsType validateDriverBasicDetailsType(String detailsType, Integer companyId) throws Exception;
	
	public ValueObject saveDriverBasicDetailsType(ValueObject valueObject) throws Exception;

	public ValueObject getDriverBasicDetailsType(ValueObject valueInObject)throws Exception;

	public ValueObject updateDriverBasicDetailsType(ValueObject valueInObject)throws Exception;

	public ValueObject deleteDriverBasicDetailsType(ValueObject valueInObject)throws Exception;


	

	
}