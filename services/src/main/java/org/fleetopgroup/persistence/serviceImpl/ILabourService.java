package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.Labour;
import org.fleetopgroup.web.util.ValueObject;

public interface ILabourService {

	public ValueObject getAllLabourMaster(ValueObject valueObject)  throws Exception;

	public ValueObject saveLabour(ValueObject valueObject)throws Exception;

	public ValueObject getLabourMaster(ValueObject valueObject)throws Exception;

	public ValueObject updateLabour(ValueObject valueObject) throws Exception;

	public ValueObject deleteLabour(ValueObject valueObject) throws Exception;

	public List<Labour> getAllLabourMasterByTerm(String term ,Integer companyId)throws Exception;

}
