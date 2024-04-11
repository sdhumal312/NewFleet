package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CompanyFixedAllowanceDto;
import org.fleetopgroup.persistence.model.Company;
import org.fleetopgroup.persistence.model.CompanyFixedAllowance;
import org.fleetopgroup.persistence.model.Companybank;
import org.fleetopgroup.persistence.model.Companydirector;
import org.fleetopgroup.persistence.model.Companylogo;
import org.fleetopgroup.persistence.model.RenewalMailConfiguration;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface ICompanyService {

    Company registerNewCompany(Company CompanyDto) throws Exception;
    
    Companydirector registerNewCompanydirector(Companydirector CompanydirectorDto) throws Exception;
    
    Companybank registerNewCompanybank(Companybank CompanybankDto) throws Exception;
    
    org.fleetopgroup.persistence.document.Companylogo registerNewCompanylogo(org.fleetopgroup.persistence.document.Companylogo CompanylogoDto) throws Exception;
    
    void updateDriverCompany(String dri_DocType, long dri_id) throws Exception;
    
    void deleteCompanyDetails(Integer company_id) throws Exception;
    
    List<Company> findAll();
    
    Company getCompany(String verificationToken);

    Company getCompanyByID(Integer dri_id);
    
    List<Company> searchCompanyList(String term);
    
    
    
    List<Company> getSubCompanyList(Integer dri_id);
    
    List<Companydirector> getCompanydirectorList(Integer company_id);
    
    List<Companybank> getCompanybankList(Integer company_id);
    
    Company validateCompanyName(String companyName);
    
    public org.fleetopgroup.persistence.document.Companylogo getCompanyLogo(int logo_id) throws Exception;
    
    Companydirector validateCompanydirectorName(String director_name, Integer company_id);
    
    Companybank validateCompanybankAccount(String account_name, Integer companyId);
    
    
    Companydirector getCompanydirectorByID(Integer com_directors_id, Integer companyId);
    
    void deleteCompanydirector(Integer com_directors_id, Integer companyId) throws Exception;
   
    Companybank getCompanybankByID(Integer com_bank_id, Integer companyId) throws Exception;
    
    void deleteCompanybank(Integer com_bank_id, Integer companyId) throws Exception;
    
    Company getCompanyAdress(Integer companyId) throws Exception;
    
    
    public Company GET_USER_ID_TO_COMPANY_DETAILS_ESI_PF_WORKING_DAYS(Integer companyId);
    
    
    public CompanyFixedAllowance validate_CompanyFixedAllowance(Long vehiclegroup_ID, Integer driver_JOBTYPE_ID);

	void registerNewCompanyFixedAllowance(CompanyFixedAllowance gET_Type);

	public List<CompanyFixedAllowanceDto> get_CompanyFixedAllowance_List(int i);

	public void delete_CompanyFixedAllowance(Long FixedAllowance_ID);

	public CompanyFixedAllowance GET_COMPANY_ID_TO_COMPANYFIXEDALLOWANCE_EXTRA(Integer company_id, Long DRIVER_GROUP_ID , Integer DRIVER_JOBTITLE);

	Page<Company> getDeployment_Find_Page_MasterCompany_Details(Integer pageNumber);

	public List<CompanyDto> Find_MasterCompany_Details(Integer pageNumber)  throws Exception;

	public void transferCompanyLogo(List<Companylogo> companylogoList) throws Exception;

	public long getCompanylogoMaxId() throws Exception;
	
	List<RenewalMailConfiguration> getAllCompanyEmail_ById(Integer companyId) throws Exception;
	
	Object saveCompanyEmailRenewalReminder(ValueObject valueOutObject) throws Exception; 
	
	void updateCompany_Email_ById(String emailId, Long configurationId, long userId, Integer companyId,Short emailType,Timestamp toDate) throws Exception;

	public ValueObject configureDailyWorkStatusEmailBody(ValueObject valueInObject) throws Exception;

	public ValueObject configureActiveVehicleDetailsOfTheMonthEmailBody(ValueObject valueInObject) throws Exception;

	public ValueObject configureMissingFuelEntryAlertEmailBody(ValueObject valueOutObject)throws Exception;

	public ValueObject configureMissingUreaEntryAlertEmailBody(ValueObject valueOutObject)throws Exception;

	public ArrayList<CompanyDto> getActiveGroupList()throws Exception;
	
	public Map<Object,Object> getIvLoginUrlByCompanyId();

	public void updateCompanyConfigurationValue(ValueObject object) throws Exception;

	
	
}
