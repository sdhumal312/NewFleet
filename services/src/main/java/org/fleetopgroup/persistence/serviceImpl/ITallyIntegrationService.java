package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.TallyCompany;
import org.fleetopgroup.web.util.ValueObject;

public interface ITallyIntegrationService {

	ValueObject	getTallyIntegrationData(ValueObject	valueObject) throws Exception;
	
	public ValueObject getTallyIntegrationDataAtc(ValueObject valueObject) throws Exception ;
	
	ValueObject	updateTallyIntegrationStatus(ValueObject	valueObject) throws Exception;
	
	public ValueObject saveTallyCompany(ValueObject valueObject) throws Exception;
	
	public List<TallyCompany> validateTallyCompanyName(String companyName, Integer companyId) throws Exception;
	
	public ValueObject getTallyCompanyList(ValueObject valueObject) throws Exception;
	
	public ValueObject getTallyCompanyListById(ValueObject valueObject) throws Exception;
	
	public ValueObject editTallyCompany(ValueObject valueObject) throws Exception;
	
	public ValueObject deleteTallyCompany(ValueObject valueObject) throws Exception;
	
	public List<TallyCompany> searchByTallyCompany(String term, Integer companyId, Long userId) throws Exception;
	
	ValueObject	getTallyCompanyListForSwingApp(ValueObject	valueObject) throws Exception;
	
	List<TallyCompany>	getTallyCompanyListForCompany(Integer companyId) throws Exception;
	
	public ValueObject saveTallyCompanyPermission(ValueObject valueObject) throws Exception;
	
	List<TallyCompany> getTallyCompanyListByCompanyId(Integer companyId, String ids) throws Exception;
	
	public ValueObject getTallyComapnyListForMobile(ValueObject object) throws Exception;
}
