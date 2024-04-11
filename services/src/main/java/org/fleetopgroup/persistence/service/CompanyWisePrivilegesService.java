package org.fleetopgroup.persistence.service;

import java.util.List;

import org.fleetopgroup.persistence.bl.CompanyWisePrivilegesBL;
import org.fleetopgroup.persistence.dao.CompanyWisePrivilegesRepository;
import org.fleetopgroup.persistence.dao.ModulePrivilegesRepository;
import org.fleetopgroup.persistence.model.CompanyWisePrivileges;
import org.fleetopgroup.persistence.model.ModulePrivileges;
import org.fleetopgroup.persistence.serviceImpl.ICompanyWisePrivilegesService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyWisePrivilegesService implements ICompanyWisePrivilegesService {

	@Autowired	private CompanyWisePrivilegesRepository			companyWisePrivilegesRepository;
	@Autowired  private ModulePrivilegesRepository				modulePrivilegesRepository;
	
	CompanyWisePrivilegesBL		companyWisePrivilegesBL	= new CompanyWisePrivilegesBL();
	
	@Override
	public ValueObject getCompanyWisePrivilegesList(ValueObject	valueObject) throws Exception {
		List<CompanyWisePrivileges> 	companyWisePrivileges	= null;
		List<CompanyWisePrivileges> 	companyModulePrivileges	= null;
		try {
			
			companyWisePrivileges	= 	companyWisePrivilegesRepository.getCompanyWisePrivilegesList(valueObject.getInt("companyId"));
			companyModulePrivileges	=	companyWisePrivilegesRepository.getCompanyWiseModulePrivilegesList(valueObject.getInt("companyId"));
			
			valueObject.put("companyWisePrivileges", companyWisePrivileges);
			valueObject.put("companyModulePrivileges", companyModulePrivileges);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			companyWisePrivileges	= null;
			companyModulePrivileges	= null;
		}
	}
	
	@Override
	public ValueObject getCompanyWiseModulePrivilegesList(ValueObject valueObject) throws Exception {
		List<ModulePrivileges> 			moduleprivileges		= null;
		List<CompanyWisePrivileges> 	companymodulePrivileges	= null;
		try {
			
			moduleprivileges		= modulePrivilegesRepository.findAll();
			companymodulePrivileges	= companyWisePrivilegesRepository.getCompanyWiseModulePrivilegesList(valueObject.getInt("companyId"));
			
			valueObject.put("moduleprivileges", moduleprivileges);
			valueObject.put("companymodulePrivileges", companymodulePrivileges);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			moduleprivileges		= null;
			companymodulePrivileges	= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveCompanyWiseModulePrivilegesList(ValueObject valueObject) throws Exception {
		List<CompanyWisePrivileges> list	= null;
		try {
			list	= 	companyWisePrivilegesBL.getCompanyModulePrivilegesDto(valueObject);
			
			companyWisePrivilegesRepository.deleteModulePrivileges(valueObject.getInt("companyId"));
			
			companyWisePrivilegesRepository.saveAll(list);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			list	= null;
		}
	}
	
	@Override
	public List<CompanyWisePrivileges> getCompanyWiseModulePrivilegesList(Integer companyId) throws Exception {
		try {
			return companyWisePrivilegesRepository.getCompanyWiseModulePrivilegesList(companyId);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<CompanyWisePrivileges> getCompanyWiseFeildPrivilegesList(Integer companyId) throws Exception {
		try {
			return companyWisePrivilegesRepository.getCompanyWisePrivilegesList(companyId);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void saveCompanyWisePrivileges(CompanyWisePrivileges companyWisePrivileges) throws Exception {
		try {
			companyWisePrivilegesRepository.save(companyWisePrivileges);
		} catch (Exception e) {
			throw e;
		}
	}
}
