package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.SubCompany;
import org.fleetopgroup.web.util.ValueObject;

//import com.fleetop.dto.UploadFile;

public interface ISubCompanyService {
	
	public ValueObject getAllSubCompanies()throws Exception;

	public SubCompany findBySubcompanyName(String subCompanuName , Integer companyId) throws Exception;
	
	public ValueObject saveSubCompany(ValueObject valueObject) throws Exception;
	
	public ValueObject getSubCompanyById(ValueObject valueObject)throws Exception;
	
	public ValueObject updateSubCompany(ValueObject valueObject)throws Exception;
	
	public ValueObject deleteSubCompany(ValueObject valueObject)throws Exception;

	public SubCompany getSubCompanyByVehicle(Integer subCompanyId) throws Exception;
	
}