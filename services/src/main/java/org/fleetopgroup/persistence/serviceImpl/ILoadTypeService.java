package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.LoadTypeMaster;
import org.fleetopgroup.web.util.ValueObject;

public interface ILoadTypeService {

	public ValueObject saveLoadTypes(ValueObject object) throws Exception;

	List<LoadTypeMaster> validateLoadTypes(String loadType, Integer companyId) throws Exception;

	public ValueObject getLoadTypesList(ValueObject object) throws Exception;

	public ValueObject getLoadTypesById(ValueObject object) throws Exception;

	public ValueObject editLoadTypes(ValueObject object) throws Exception;

	public ValueObject deleteLoadType(ValueObject object) throws Exception;

	public List<LoadTypeMaster> getLoadTypesListDropDown(String term, Integer company_id) throws Exception;
	
	public ValueObject getLoadTypeNameWiseList(ValueObject object) throws Exception;

}
