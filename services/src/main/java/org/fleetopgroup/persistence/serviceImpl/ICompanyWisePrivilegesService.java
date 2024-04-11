package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.CompanyWisePrivileges;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface ICompanyWisePrivilegesService {

	ValueObject  getCompanyWisePrivilegesList(ValueObject	valueObject) throws Exception;
	
	ValueObject  getCompanyWiseModulePrivilegesList(ValueObject	valueObject) throws Exception;
	
	ValueObject  saveCompanyWiseModulePrivilegesList(ValueObject	valueObject) throws Exception;
	
	List<CompanyWisePrivileges>  getCompanyWiseModulePrivilegesList(Integer companyId) throws Exception;
	List<CompanyWisePrivileges>  getCompanyWiseFeildPrivilegesList(Integer companyId) throws Exception;
	
	public void saveCompanyWisePrivileges(CompanyWisePrivileges		companyWisePrivileges) throws Exception;
	
}
