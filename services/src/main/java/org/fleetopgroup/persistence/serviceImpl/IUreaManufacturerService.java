package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.UreaManufacturer;
import org.fleetopgroup.web.util.ValueObject;

public interface IUreaManufacturerService {

	List<UreaManufacturer>  getUreaManufacturerList(String	term, Integer companyId)throws Exception;

	public ValueObject saveUreaTypes(ValueObject object) throws Exception;	
	
	List<UreaManufacturer> validateUreaTypes(String ureaType, Integer companyId) throws Exception;	

	public ValueObject getUreaTypesList(ValueObject object) throws Exception;

	public ValueObject getUreaTypesById(ValueObject object) throws Exception;   

	public ValueObject editUreaTypes(ValueObject object) throws Exception;	

	public ValueObject deleteUreaType(ValueObject object)  throws Exception;
	
	
}
