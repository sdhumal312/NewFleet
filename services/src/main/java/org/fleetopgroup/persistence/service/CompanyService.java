package org.fleetopgroup.persistence.service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.IVServerIdentifierConstant;
import org.fleetopgroup.constant.RenewalReminderEmailConfiguration;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.bl.CompanyConfigurationBL;
import org.fleetopgroup.persistence.dao.CompanyConfigurationRepository;
import org.fleetopgroup.persistence.dao.CompanyFixedAllowanceRepository;
import org.fleetopgroup.persistence.dao.CompanyMapperRepository;
import org.fleetopgroup.persistence.dao.CompanyRepository;
import org.fleetopgroup.persistence.dao.CompanybankRepository;
import org.fleetopgroup.persistence.dao.CompanydirectorRepository;
import org.fleetopgroup.persistence.dao.CompanylogoRepository;
import org.fleetopgroup.persistence.dao.RenewalMailConfigurationRepository;
import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CompanyFixedAllowanceDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.BatteryHistory;
import org.fleetopgroup.persistence.model.Company;
import org.fleetopgroup.persistence.model.CompanyConfiguration;
import org.fleetopgroup.persistence.model.CompanyFixedAllowance;
import org.fleetopgroup.persistence.model.CompanyMapper;
import org.fleetopgroup.persistence.model.Companybank;
import org.fleetopgroup.persistence.model.Companydirector;
import org.fleetopgroup.persistence.model.Companylogo;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.InventoryTyreHistory;
import org.fleetopgroup.persistence.model.RenewalMailConfiguration;
import org.fleetopgroup.persistence.model.ServiceEntries;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.persistence.serviceImpl.IBatteryHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUreaEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;




@Service
@Transactional
public class CompanyService implements ICompanyService {
	
	@Autowired CompanyFixedAllowanceRepository 		companyFixedAllowanceRepository;
	@Autowired CompanydirectorRepository 			companydirectorRepository;
	@Autowired ISequenceCounterService				sequenceCounterService;
	@Autowired CompanybankRepository 				companybankRepository;
	@Autowired CompanylogoRepository				companylogoRepository;
	@Autowired CompanyRepository 					companyRepository;
	@Autowired MongoTemplate						mongoTemplate;
	@Autowired IVehicleService						vehicleService;
	@Autowired IInventoryTyreService				tyreService;
	@Autowired IBatteryHistoryService				batteryService;
	@Autowired IUreaEntriesService					ureaService;
	@Autowired IClothInventoryService				clothInventoryService;
	@Autowired IDriverService 			    		driverService;
	@Autowired IEmailSenderService					emailSenderService;
	@Autowired IUreaEntriesService					ureaEntriesService;
	@Autowired IFuelService							fuelService;
	@Autowired ITripSheetService 					tripSheetService;
	@Autowired IServiceEntriesService				serviceEntriesService;
	@Autowired IWorkOrdersService			   		workOrdersService;
	@Autowired IServiceReminderService 				serviceReminderService;
	@Autowired IRenewalReminderService				renewalReminderService;
	@Autowired CompanyMapperRepository				companyMapperRepo;
	
	@Autowired
	private RenewalMailConfigurationRepository renewalReminderMail;

	@Autowired CompanyConfigurationRepository   companyConfigurationRepository;
	CompanyConfigurationBL   companyConfigBL  = new CompanyConfigurationBL();
	
	@PersistenceContext
	EntityManager entityManager;

	private static final int PAGE_SIZE = 10;

	@Transactional
	public Company registerNewCompany(Company CompanyDto) throws Exception {

		return companyRepository.save(CompanyDto);
	}

	@Transactional
	public Companydirector registerNewCompanydirector(Companydirector CompanydirectorDto) throws Exception {

		return companydirectorRepository.save(CompanydirectorDto);
	}

	@Transactional
	public Companybank registerNewCompanybank(Companybank CompanybankDto) throws Exception {

		return companybankRepository.save(CompanybankDto);
	}

	@Transactional
	public org.fleetopgroup.persistence.document.Companylogo registerNewCompanylogo(org.fleetopgroup.persistence.document.Companylogo CompanylogoDto) throws Exception {
		CompanylogoDto.set_id((int) sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_COMPANY_LOGO));
		mongoTemplate.save(CompanylogoDto);
		return CompanylogoDto;
	}

	@Transactional
	public void updateDriverCompany(String dri_DocType, long dri_id) throws Exception {

	}

	@Transactional
	public void deleteCompanyDetails(Integer company_id) throws Exception {

		companyRepository.deleteCompanyDetails(company_id);
	}

	@Transactional
	public List<Company> findAll() {

		return companyRepository.findAll();
	}

	@Override
	public List<Company> searchCompanyList(String term) {

		TypedQuery<Object[]> queryt = null;
		
		if(term != null && !term.trim().equalsIgnoreCase("")) {
			queryt = entityManager.createQuery(
					"SELECT C.company_id, C.company_name, C.companyCode "
							+ " FROM Company AS C where C.markForDelete = 0 AND  lower(C.company_name) Like ('%" + term+ "%') order by C.company_name desc  ",
					Object[].class);
		}else {

			queryt = entityManager.createQuery(
					"SELECT C.company_id, C.company_name, C.companyCode "
							+ " FROM Company AS C where  C.markForDelete = 0 order by C.company_name asc ",
					Object[].class);
		
		}
		
		
		List<Object[]> results = queryt.getResultList();

		List<Company> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Company>();
			Company list = null;
			for (Object[] result : results) {
				list = new Company();

				list.setCompany_id((Integer) result[0]);
				list.setCompany_name((String) result[1]);
				list.setCompanyCode((String) result[2]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	
	@Transactional
	public Company getCompany(String verificationToken) {

		return null;
	}

	@Transactional
	public Company getCompanyByID(Integer dri_id) {

		return companyRepository.getCompanyByID(dri_id);
	}

	@Transactional
	public Company validateCompanyName(String companyName) {

		return companyRepository.validateCompanyName(companyName);
	}

	@Transactional
	public org.fleetopgroup.persistence.document.Companylogo getCompanyLogo(int logo_id) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("company_id").is(logo_id));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.Companylogo.class);
	}

	@Transactional
	public List<Company> getSubCompanyList(Integer dri_id) {

		return companyRepository.getSubCompanyList(dri_id);
	}

	@Transactional
	public Companydirector validateCompanydirectorName(String director_name, Integer company_id) {

		return companydirectorRepository.validateCompanydirectorName(director_name, company_id);
	}

	@Transactional
	public List<Companydirector> getCompanydirectorList(Integer company_id) {

		return companydirectorRepository.getCompanydirectorList(company_id);
	}

	@Transactional
	public Companybank validateCompanybankAccount(String account_name, Integer companyId) {

		return companybankRepository.validateCompanybankAccount(account_name, companyId);
	}

	@Transactional
	public List<Companybank> getCompanybankList(Integer company_id) {
		
		return companybankRepository.getCompanybankList(company_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.ICompanyService#getCompanydirectorByID(
	 * java.lang.Integer)
	 */
	@Transactional
	public Companydirector getCompanydirectorByID(Integer com_directors_id, Integer companyId) {

		return companydirectorRepository.getCompanydirectorByID(com_directors_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.ICompanyService#deleteCompanydirector(
	 * java.lang.Integer)
	 */
	@Transactional
	public void deleteCompanydirector(Integer com_directors_id, Integer companyId) throws Exception {

		companydirectorRepository.deleteCompanydirector(com_directors_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.ICompanyService#getCompanybankByID(java.
	 * lang.Integer)
	 */
	@Transactional
	public Companybank getCompanybankByID(Integer com_bank_id, Integer companyId) throws Exception {

		return companybankRepository.getCompanybankByID(com_bank_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.ICompanyService#deleteCompanybank(java.
	 * lang.Integer)
	 */
	@Transactional
	public void deleteCompanybank(Integer com_bank_id, Integer companyId) throws Exception {

		companybankRepository.deleteCompanybank(com_bank_id, companyId);
	}

	@Override
	public Company getCompanyAdress(Integer companyId) throws Exception {
		try {
			return companyRepository.getCompanyAdress(companyId);
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public Company GET_USER_ID_TO_COMPANY_DETAILS_ESI_PF_WORKING_DAYS(Integer companyId) {

		Query query = entityManager
				.createQuery("SELECT f.company_id, f.company_esi_pf_days, f.company_esi_pf_disable from Company AS f "
						+ " WHERE  f.company_id = :companyId ");

		query.setParameter("companyId", companyId);
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		Company select = null;
		if (vehicle != null) {
			select = new Company();
			select.setCompany_id((Integer) vehicle[0]);
			select.setCompany_esi_pf_days((Long) vehicle[1]);
			select.setCompany_esi_pf_disable((Integer) vehicle[2]);

		} else {
			return null;
		}
		return select;
	}

	// New Fixed Allowance

	@Override
	@Transactional
	public CompanyFixedAllowance validate_CompanyFixedAllowance(Long vehiclegroup_ID, Integer driver_JOBTYPE_ID) {

		return companyFixedAllowanceRepository.validate_CompanyFixedAllowance(vehiclegroup_ID, driver_JOBTYPE_ID);
	}

	@Override
	@Transactional
	public void registerNewCompanyFixedAllowance(CompanyFixedAllowance gET_Type) {

		companyFixedAllowanceRepository.save(gET_Type);
	}

	@Override
	@Transactional
	public List<CompanyFixedAllowanceDto> get_CompanyFixedAllowance_List(int company_id) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.COMFIXID, R.COMPANY_ID, R.VEHICLEGROUP_ID, G.vGroup, R.DRIVER_JOBTYPE_ID, D.driJobType, R.FIX_PERDAY_ALLOWANCE, R.FIX_PERDAY_ALLOWANCE_AMOUNT, R.FIX_EXTRA_DAYS, R.FIX_EXTRA_DAYS_AMOUNT "
						+ " FROM CompanyFixedAllowance AS R LEFT JOIN VehicleGroup AS G ON R.VEHICLEGROUP_ID=G.gid LEFT JOIN DriverJobType AS D ON R.DRIVER_JOBTYPE_ID=D.driJobId  WHERE R.markForDelete=0 AND R.COMPANY_ID="
						+ company_id + " ORDER BY R.COMFIXID desc",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<CompanyFixedAllowanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<CompanyFixedAllowanceDto>();
			CompanyFixedAllowanceDto list = null;
			for (Object[] result : results) {
				list = new CompanyFixedAllowanceDto();

				list.setCOMFIXID((Long) result[0]);
				list.setCOMPANY_ID((Integer) result[1]);
				list.setVEHICLEGROUP_ID((Long) result[2]);
				list.setVEHICLEGROUP_NAME((String) result[3]);
				list.setDRIVER_JOBTYPE_ID((Integer) result[4]);
				list.setDRIVER_JOBTYPE_NAME((String) result[5]);
				list.setFIX_PERDAY_ALLOWANCE((Integer) result[6]);
				list.setFIX_PERDAY_ALLOWANCE_AMOUNT((Double) result[7]);
				list.setFIX_EXTRA_DAYS((Integer) result[8]);
				list.setFIX_EXTRA_DAYS_AMOUNT((Double) result[9]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public void delete_CompanyFixedAllowance(Long FixedAllowance_ID) {

		try {
			companyFixedAllowanceRepository.delete_CompanyFixedAllowance(FixedAllowance_ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public CompanyFixedAllowance GET_COMPANY_ID_TO_COMPANYFIXEDALLOWANCE_EXTRA(Integer company_id, Long DRIVER_GROUP_ID,
			Integer DRIVER_JOBTITLE) {

		Query query = entityManager.createQuery(
				"SELECT f.COMFIXID, f.FIX_PERDAY_ALLOWANCE, f.FIX_PERDAY_ALLOWANCE_AMOUNT, f.FIX_EXTRA_DAYS, f.FIX_EXTRA_DAYS_AMOUNT from CompanyFixedAllowance AS f where f.markForDelete=0 AND  f.COMPANY_ID = :id "
						+ "AND f.VEHICLEGROUP_ID = " + DRIVER_GROUP_ID + "" + "AND f.DRIVER_JOBTYPE_ID = "
						+ DRIVER_JOBTITLE + " ");

		query.setParameter("id", company_id);
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		CompanyFixedAllowance select;
		if (vehicle != null) {
			select = new CompanyFixedAllowance();
			select.setCOMFIXID((Long) vehicle[0]);

			select.setFIX_PERDAY_ALLOWANCE((Integer) vehicle[1]);
			select.setFIX_PERDAY_ALLOWANCE_AMOUNT((Double) vehicle[2]);
			select.setFIX_EXTRA_DAYS((Integer) vehicle[3]);
			select.setFIX_EXTRA_DAYS_AMOUNT((Double) vehicle[4]);

		} else {
			return null;
		}
		return select;
	}

	@Override
	@Transactional
	public List<CompanyDto> Find_MasterCompany_Details(Integer pageNumber) throws Exception {

		TypedQuery<Object[]> queryt = null;
		try {
			queryt = entityManager.createQuery(
					"SELECT R.company_id, R.company_name, R.company_city, R.company_state, R.company_website,  R.company_email, "
							+ "R.company_mobilenumber, R.company_status, R.companyCode From Company AS R "
							+ " WHERE R.markForDelete = 0 ORDER BY R.company_id desc",
					Object[].class);
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<CompanyDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<CompanyDto>();
				CompanyDto Dto = null;
				for (Object[] com_nonEncode : results) {
					Dto = new CompanyDto();

					Base64.Encoder encoder = Base64.getEncoder();
					String normalString = "" + com_nonEncode[0];
					String encodedString = encoder.encodeToString(normalString.getBytes(StandardCharsets.UTF_8));

					Dto.setCompany_id_encode(encodedString);

					Dto.setCompany_name((String) com_nonEncode[1]);
					Dto.setCompany_city((String) com_nonEncode[2]);
					Dto.setCompany_state((String) com_nonEncode[3]);
					Dto.setCompany_website((String) com_nonEncode[4]);
					Dto.setCompany_email((String) com_nonEncode[5]);
					Dto.setCompany_mobilenumber((String) com_nonEncode[6]);
					Dto.setCompany_status((String) com_nonEncode[7]);
					Dto.setCompanyCode((String) com_nonEncode[8]);
					
					Dtos.add(Dto);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		} finally {
			queryt = null;
		}
	}

	@Override
	public Page<Company> getDeployment_Find_Page_MasterCompany_Details(Integer pageNumber) {

		try {
			@SuppressWarnings("deprecation")
			PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);

			return companyRepository.getDeployment_Find_Page_MasterCompany_Details(request);

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void transferCompanyLogo(List<Companylogo> list) throws Exception {
		org.fleetopgroup.persistence.document.Companylogo				companylogo		= null;
		List<org.fleetopgroup.persistence.document.Companylogo> 		companylogoList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				companylogoList	= new ArrayList<>();
				for(org.fleetopgroup.persistence.model.Companylogo	document : list) {
					companylogo	= new org.fleetopgroup.persistence.document.Companylogo();
					
					companylogo.set_id(document.getLog_id());
					companylogo.setFilename(document.getFilename());
					companylogo.setLog_content(document.getLog_content());
					companylogo.setLog_contentType(document.getLog_contentType());
					companylogo.setCompany_id(document.getCompany_id());
					
					companylogoList.add(companylogo);
				}
				System.err.println("Saving Companylogo ....");
				mongoTemplate.insert(companylogoList, org.fleetopgroup.persistence.document.Companylogo.class);
				System.err.println("Saved Successfully....");
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}

	@Override
	public long getCompanylogoMaxId() throws Exception {
		try {
			return companylogoRepository.getCompanylogoMaxId();
		} catch (Exception e) {
			return 0;
		}
	}
	
		@Override
		public List<RenewalMailConfiguration> getAllCompanyEmail_ById(Integer companyId)throws Exception {			
			return renewalReminderMail.getAllCompanyInfEmail_Ids(companyId);
		}	
	
		@Transactional
		public ValueObject saveCompanyEmailRenewalReminder(ValueObject	valueObject) throws Exception {
			CustomUserDetails				userDetails						= null;
			Timestamp 						createdDateTime 				= null;
			RenewalMailConfiguration		RenewalMailConfiguration		= null;
			try {
				userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				createdDateTime 	= new java.sql.Timestamp(new Date().getTime());
				RenewalMailConfiguration = new RenewalMailConfiguration();

				RenewalMailConfiguration.setEmailIds(valueObject.getString("emailId"));
				RenewalMailConfiguration.setCompanyId(userDetails.getCompany_id());
				RenewalMailConfiguration.setCreatedOn(createdDateTime);
				RenewalMailConfiguration.setLastModifiedOn(createdDateTime);
				RenewalMailConfiguration.setLastModifiedById(userDetails.getId());
				RenewalMailConfiguration.setCreatedById(userDetails.getId());
				RenewalMailConfiguration.setMarkForDelete(false);		
				RenewalMailConfiguration.setEmailType(RenewalReminderEmailConfiguration.ADMIN_DAILY_WORK_STATUS_EMAIL);
				renewalReminderMail.save(RenewalMailConfiguration);
				return valueObject;
			} catch (Exception e) {
				throw e;
			} finally {
				userDetails						= null;
				createdDateTime 				= null;
			}
		}
		
		@Override
		public void updateCompany_Email_ById(String emailId, Long configurationId, long userId, Integer companyId,Short emailType,Timestamp toDate)
				throws Exception {				
			//renewalReminderMail.updateCompanyUsersEmail_Ids(emailId,configurationId,userId,companyId,emailType);
			renewalReminderMail.updateCompanyUsersEmail_Ids(emailId,userId,toDate,configurationId,companyId,emailType);
		}
		
		
		@SuppressWarnings("unchecked")
		@Override
		public ValueObject configureDailyWorkStatusEmailBody(ValueObject valueInObject) throws Exception {
			
			ValueObject					        valueOutObject 				= null;
			Map<Integer,CompanyDto> 			finalMapOfCount				= null;
			CompanyDto							companyDto					= null;
			String emailDate=null;
			RenewalMailConfiguration			renewalMailConfiguration	= null;
			String								status						= "";
			try {
				
				
				finalMapOfCount = (HashMap<Integer, CompanyDto>) valueInObject.get("finalMapOfCount");
				emailDate= valueInObject.getString("emailDate");
				String 	countsDetailsHTML 	= "";
				long 	rowWiseTotal 		= 0;
				long 	columnWiseTotal		= 0;
				companyDto					= null;
				companyDto 					= new CompanyDto();
				
				if(finalMapOfCount != null && !finalMapOfCount.isEmpty()) {
					
					int	i= 1;
					for(int key : finalMapOfCount.keySet()){
						rowWiseTotal = 0 ;
						rowWiseTotal = finalMapOfCount.get(key).getWorkOrderCount()+finalMapOfCount.get(key).getServiceEntriesCount()+finalMapOfCount.get(key).getFuelEntriesCount()+finalMapOfCount.get(key).getServiceReminderCount()+finalMapOfCount.get(key).getRenewalReminderCount()+finalMapOfCount.get(key).getTripSheetCount();					
						columnWiseTotal	= columnWiseTotal + rowWiseTotal;
						
						companyDto.setTotalWorkOrderCount(companyDto.getTotalWorkOrderCount() + finalMapOfCount.get(key).getWorkOrderCount());
						companyDto.setTotalServiceEntriesCount(companyDto.getTotalServiceEntriesCount() + finalMapOfCount.get(key).getServiceEntriesCount());
						companyDto.setTotalFuelEntriesCount(companyDto.getTotalFuelEntriesCount() + finalMapOfCount.get(key).getFuelEntriesCount() );
						companyDto.setTotalServiceReminderCount(companyDto.getTotalServiceReminderCount() + finalMapOfCount.get(key).getServiceReminderCount());
						companyDto.setTotalRenewalReminderCount(companyDto.getTotalRenewalReminderCount() + finalMapOfCount.get(key).getRenewalReminderCount() );
						companyDto.setTotalTripSheetCount(companyDto.getTotalTripSheetCount() + finalMapOfCount.get(key).getTripSheetCount());
						
						renewalMailConfiguration	= renewalReminderMail.getRenewalMailByCompanyIdAndEmailType(finalMapOfCount.get(key).getCompany_id());
						if(renewalMailConfiguration != null) {
							status = "Activated";
						}else {
							status = "--";
						}
						
						countsDetailsHTML 	+= "<tr>"
											+"<td align='center'>"+ i +"</td>"
											+"<td align='center'>"+ finalMapOfCount.get(key).getCompany_name() +"</td>"
											+"<td align='center'>"+ finalMapOfCount.get(key).getWorkOrderCount() +"</td>"
											+"<td align='center'>"+ finalMapOfCount.get(key).getServiceEntriesCount()+"</td>"
											+"<td align='center'>"+ finalMapOfCount.get(key).getFuelEntriesCount()+"</td>"
											+"<td align='center'>"+ finalMapOfCount.get(key).getServiceReminderCount()+"</td>"
											+"<td align='center'>"+ finalMapOfCount.get(key).getRenewalReminderCount()	+"</td>"
											+"<td align='center'>"+ finalMapOfCount.get(key).getTripSheetCount()	+"</td>"
											+"<td align='center'><b>"+rowWiseTotal+"</b></td>"
											+"<td align='center'>"+status+"</td>"
											+"<td align='center'>"+status+"</td>"
											+"</tr>";
						i++;
					}
				}

				countsDetailsHTML 	+= "<tr>"
									+"<td align='center'></td>"
									+"<td align='center'>Total</td>"
									+"<td align='center'><b>"+ companyDto.getTotalWorkOrderCount()+"</b></td>"
									+"<td align='center'><b>"+ companyDto.getTotalServiceEntriesCount()+"</b></td>"
									+"<td align='center'><b>"+ companyDto.getTotalFuelEntriesCount()+"</b></td>"
									+"<td align='center'><b>"+ companyDto.getTotalServiceReminderCount()+"</b></td>"
									+"<td align='center'><b>"+ companyDto.getTotalRenewalReminderCount()+"</b></td>"
									+"<td align='center'><b>"+ companyDto.getTotalTripSheetCount()+"</b></td>"
									+"<td align='center'><b>"+columnWiseTotal+"</b></td>"
									+"<td align='center'></td>"
									+"<td align='center'></td>"
									+"</tr>";
				
				
				
				final String DailyWorkStatusEmailBody_Manager = "<html>\r\n" + 
						"<head>\r\n" + 
						"</head>\r\n" + 
						"\r\n" + 
						"<body>\r\n" +
						"<table bgcolor=\"blue\" width=\"100%\">\r\n" + 
						"<tr>\r\n" + 
						"<td>\r\n" + 
						"<h2><font color=\"#ffffff\" ><b><center>Fleetop</center></b></font></h2>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n" + 
						"</table>\r\n" + 
						"<br>\r\n <font color='blue'>Work Status update for </font> <b>" +emailDate+
						"</b><br>\r\n" + 
						"<table style=\"width:100%;border-collapse: collapse;\" border=1>\r\n" +
						"	<thead>"+
						"  <tr>\r\n" + 
						"    <th><p style=\"color:black;\">SR NO</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">Company Name</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">WO</p></th> \r\n" + 
						"    <th><p style=\"color:black;\">SE</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">FE</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">SR</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">RR</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">TripSheet</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">Total</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">Dashboard</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">Consumption</p></th>\r\n" + 
						"  </tr>\r\n"+
						"	</thead>"+
						"	<tbody>" + countsDetailsHTML+
						"	</tbody>" + 								
						"</table>\r\n" + 
						"<br>\r\n" + 
						"\r\n" + 
						"<table bgcolor=\"blue\" width=\"100%\">\r\n" + 
						"<tr>\r\n" + 
						"<td>\r\n" + 
						"<h2><font color=\"#ffffff\" ><b> </b></font></h2>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n" + 
						"</table>\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"</body>\r\n" + 
						"</html>";
				
				valueOutObject	= new ValueObject();
				valueOutObject.put("DailyWorkStatusEmailBody_Manager", DailyWorkStatusEmailBody_Manager);
				
				return valueOutObject;
				
			}catch (Exception e) {
				throw e;
			}
			
		}	
		
		
		@SuppressWarnings("unchecked")
		@Override
		public ValueObject configureActiveVehicleDetailsOfTheMonthEmailBody(ValueObject valueInObject) throws Exception {
			ValueObject					        valueOutObject 				= null;
			Map<Integer,CompanyDto> 			finalMapOfCount				= null;
			CompanyDto							companyDto					= null;
			String 								emailDate  					= null;
			String 								countsDetailsHTML 			= "";
			try {
				companyDto 		= new CompanyDto();
				finalMapOfCount = (HashMap<Integer, CompanyDto>) valueInObject.get("vehicleCountHM");
				emailDate 		= valueInObject.getString("emailDate");
				
				if(finalMapOfCount != null && !finalMapOfCount.isEmpty()) {
					int	i= 1;
					for(int key : finalMapOfCount.keySet()){
						
						companyDto.setTotalActiveVehicleCount(companyDto.getTotalActiveVehicleCount() + finalMapOfCount.get(key).getTotalActiveVehicleCount());
						companyDto.setTotalInActiveVehicleCount(companyDto.getTotalInActiveVehicleCount() + finalMapOfCount.get(key).getTotalInActiveVehicleCount());
						companyDto.setTotalSoldVehicleCount(companyDto.getTotalSoldVehicleCount() + finalMapOfCount.get(key).getTotalSoldVehicleCount());
						
						countsDetailsHTML 	+= "<tr>"
								+"<td align='center'>"+ i +"</td>"
								+"<td align='center'>"+ finalMapOfCount.get(key).getCompany_name() +"</td>"
								+"<td align='center'>"+ finalMapOfCount.get(key).getTotalActiveVehicleCount() +"</td>"
								+"<td align='center'>"+ finalMapOfCount.get(key).getTotalInActiveVehicleCount() +"</td>"
								+"<td align='center'>"+ finalMapOfCount.get(key).getTotalSoldVehicleCount() +"</td>"
								+"</tr>";
							i++;
					}
				}
				
				countsDetailsHTML 	+= "<tr>"
						+"<td align='center'></td>"
						+"<td align='center'>Total</td>"
						+"<td align='center'><b>"+ companyDto.getTotalActiveVehicleCount()+"</b></td>"
						+"<td align='center'><b>"+ companyDto.getTotalInActiveVehicleCount()+"</b></td>"
						+"<td align='center'><b>"+ companyDto.getTotalSoldVehicleCount()+"</b></td>"
						+"</tr>";
				
				final String activeVehicleDetailsEmailBody = "<html>\r\n" + 
						"<head>\r\n" + 
						"</head>\r\n" + 
						"\r\n" + 
						"<body>\r\n" +
						"<table bgcolor=\"06adf0\" width=\"100%\">\r\n" + 
						"<tr>\r\n" + 
						"<td>\r\n" + 
						"<h2><font color=\"#ffffff\" ><b><center>Fleetop</center></b></font></h2>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n" + 
						"</table>\r\n" + 
						"<br>\r\n <font color='blue'>Active Client For Month  </font> <b>" +valueInObject.getString("finalMonthAndYear", "")+
						"</b><br>\r\n" + 
						"<table style=\"width:100%;border-collapse: collapse;\" border=1>\r\n" +
						"	<thead>"+
						"  <tr>\r\n" + 
						"    <th><p style=\"color:black;\">SR NO</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">Company Name</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">Active Vehicle Count</p></th> \r\n" + 
						"    <th><p style=\"color:black;\">In-Active Vehicle Count</p></th> \r\n" + 
						"    <th><p style=\"color:black;\">Sold Vehicle Count</p></th> \r\n" + 
						"  </tr>\r\n"+
						"	</thead>"+
						"	<tbody>" + countsDetailsHTML+
						"	</tbody>" + 								
						"</table>\r\n" + 
						"<br>\r\n" + 
						"\r\n" + 
						"<table bgcolor=\"06adf0\" width=\"100%\">\r\n" + 
						"<tr>\r\n" + 
						"<td>\r\n" + 
						"<h2><font color=\"#ffffff\" ><b> </b></font></h2>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n" + 
						"</table>\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"</body>\r\n" + 
						"</html>";
				
				valueOutObject	= new ValueObject();
				valueOutObject.put("activeVehicleDetailsEmailBody", activeVehicleDetailsEmailBody);
				
				return valueOutObject;
				
			}catch (Exception e) {
				throw e;
			}
			
		}
	 @SuppressWarnings("unchecked")
		@Override
		public ValueObject configureMissingFuelEntryAlertEmailBody(ValueObject valueInObject) throws Exception {
			ValueObject					        valueOutObject 				= null;
			List<VehicleDto> 				vehicleFinalList					= null;
			CompanyDto							companyDto					= null;
			String 								emailDate  					= null;
			String 								countsDetailsHTML 			= "";
			try {
				companyDto 		= new CompanyDto();
				companyDto = (CompanyDto) valueInObject.get("company");
				vehicleFinalList = (List<VehicleDto>) valueInObject.get("vehicleFinalList");
				
				if(vehicleFinalList == null || vehicleFinalList.isEmpty()) {
					valueInObject.put("noRecordFound", true);
					return valueInObject;
				}
				
				emailDate 		= valueInObject.getString("emailDate");
				
				if(vehicleFinalList != null && !vehicleFinalList.isEmpty()) {
					int i= 1;
					for(VehicleDto dto :vehicleFinalList){
						countsDetailsHTML 	+= "<tr>"
								+"<td align='center'>"+ i +"</td>"
								+"<td align='center'>"+ dto.getVehicle_registration() +"</td>"
								+"<td align='center'>F-"+ dto.getFuel_Number() +"</td>"
								+"<td align='center'>"+ dto.getFuelDateStr() +"</td>"
								+"<td align='center'>"+ dto.getFuelMeter() +"</td>"
								+"<td align='center'>"+ dto.getVehicle_Odometer()+"</td>"
								+"<td align='center'>"+ dto.getVehicle_ExpectedOdameter()+"</td>"
								+"</tr>";
							i++;
					}
				}

				
				final String activeVehicleDetailsEmailBody = "<html>\r\n" + 
						"<head>\r\n" + 
						"</head>\r\n" + 
						"\r\n" + 
						"<body>\r\n" +
						"<table bgcolor=\"06adf0\" width=\"100%\">\r\n" + 
						"<tr>\r\n" + 
						"<td>\r\n" + 
						"<h2><font color=\"#ffffff\" ><b><center>"+companyDto.getCompany_name()+"</center></b></font></h2>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n" + 
						"</table>\r\n" + 
						"<br>\r\n <font color='red'>Weekly Missing Fuel Entries Alert </font> <b>" +DateTimeUtility.getCurrentDateStr(DateTimeUtility.DD_MM_YYYY)+
						"</b><br>\r\n" + 
						"<table style=\"width:100%;border-collapse: collapse;\" border=1>\r\n" +
						"	<thead>"+
						"  <tr>\r\n" + 
						"    <th><p style=\"color:black;\">SR NO</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">Vehicle Registration</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">Last Fuel Number</p></th> \r\n" + 
						"    <th><p style=\"color:black;\">Last Fuel Entry Date</p></th> \r\n" + 
						"    <th><p style=\"color:black;\">Last Fuel Entry Odometer</p></th> \r\n" + 
						"    <th><p style=\"color:black;\">Current Odometer</p></th> \r\n" + 
						"    <th><p style=\"color:black;\">Max Odometer</p></th> \r\n" + 
						"  </tr>\r\n"+
						"	</thead>"+
						"	<tbody>" + countsDetailsHTML+
						"	</tbody>" + 								
						"</table>\r\n" + 
						"<br>\r\n" + 
						"\r\n" + 
						"<table bgcolor=\"06adf0\" width=\"100%\">\r\n" + 
						"<tr>\r\n" + 
						"<td>\r\n" + 
						"<h2><font color=\"#ffffff\" ><b> </b></font></h2>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n" + 
						"</table>\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"</body>\r\n" + 
						"</html>";
				
				valueOutObject	= new ValueObject();
				valueOutObject.put("activeVehicleDetailsEmailBody", activeVehicleDetailsEmailBody);
				
				return valueOutObject;
				
			}catch (Exception e) {
				throw e;
			}
			
		}
		
	 @SuppressWarnings("unchecked")
		@Override
		public ValueObject configureMissingUreaEntryAlertEmailBody(ValueObject valueInObject) throws Exception {
			ValueObject					        valueOutObject 				= null;
			List<VehicleDto> 				vehicleFinalList					= null;
			CompanyDto							companyDto					= null;
			String 								emailDate  					= null;
			String 								countsDetailsHTML 			= "";
			try {
				companyDto 		= new CompanyDto();
				companyDto = (CompanyDto) valueInObject.get("company");
				vehicleFinalList = new ArrayList<>();
				vehicleFinalList = (List<VehicleDto>) valueInObject.get("vehicleFinalList");
				if(vehicleFinalList == null || vehicleFinalList.isEmpty()) {
					valueInObject.put("noRecordFound", true);
					return valueInObject;
				}
				emailDate 		= valueInObject.getString("emailDate");
				
				if(vehicleFinalList != null && !vehicleFinalList.isEmpty()) {
					int i= 1;
					for(VehicleDto dto :vehicleFinalList){
						countsDetailsHTML 	+= "<tr>"
								+"<td align='center'>"+ i +"</td>"
								+"<td align='center'>"+ dto.getVehicle_registration() +"</td>"
								+"<td align='center'>UE-"+ dto.getUreaEntriesNumber() +"</td>"
								+"<td align='center'>"+ dto.getUreaDateStr() +"</td>"
								+"<td align='center'>"+ dto.getUreaOdometer() +"</td>"
								+"<td align='center'>"+ dto.getVehicle_Odometer()+"</td>"
								+"<td align='center'>"+ dto.getVehicle_ExpectedOdameter()+"</td>"
								+"</tr>";
							i++;
					}
				}

				
				final String activeVehicleDetailsEmailBody = "<html>\r\n" + 
						"<head>\r\n" + 
						"</head>\r\n" + 
						"\r\n" + 
						"<body>\r\n" +
						"<table bgcolor=\"06adf0\" width=\"100%\">\r\n" + 
						"<tr>\r\n" + 
						"<td>\r\n" + 
						"<h2><font color=\"#ffffff\" ><b><center>"+companyDto.getCompany_name()+"</center></b></font></h2>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n" + 
						"</table>\r\n" + 
						"<br>\r\n <font color='red'>Weekly Missing Urea Entries Alert </font> <b>" +DateTimeUtility.getCurrentDateStr(DateTimeUtility.DD_MM_YYYY)+
						"</b><br>\r\n" + 
						"<table style=\"width:100%;border-collapse: collapse;\" border=1>\r\n" +
						"	<thead>"+
						"  <tr>\r\n" + 
						"    <th><p style=\"color:black;\">SR NO</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">Vehicle Registration</p></th>\r\n" + 
						"    <th><p style=\"color:black;\">Last Urea Entry Number</p></th> \r\n" + 
						"    <th><p style=\"color:black;\">Last Urea Entry Date</p></th> \r\n" + 
						"    <th><p style=\"color:black;\">Last Urea Entry Odometer</p></th> \r\n" + 
						"    <th><p style=\"color:black;\">Current Odometer</p></th> \r\n" + 
						"    <th><p style=\"color:black;\">Max Odometer</p></th> \r\n" + 
						"  </tr>\r\n"+
						"	</thead>"+
						"	<tbody>" + countsDetailsHTML+
						"	</tbody>" + 								
						"</table>\r\n" + 
						"<br>\r\n" + 
						"\r\n" + 
						"<table bgcolor=\"06adf0\" width=\"100%\">\r\n" + 
						"<tr>\r\n" + 
						"<td>\r\n" + 
						"<h2><font color=\"#ffffff\" ><b> </b></font></h2>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n" + 
						"</table>\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"</body>\r\n" + 
						"</html>";
				
				valueOutObject	= new ValueObject();
				valueOutObject.put("activeVehicleDetailsEmailBody", activeVehicleDetailsEmailBody);
				
				return valueOutObject;
				
			}catch (Exception e) {
				throw e;
			}
			
		}
	 
		@Override
		public ArrayList<CompanyDto> getActiveGroupList() throws Exception {
			ValueObject						valueOutObject				= null;
			String 							startOfMonth				= "";
			String 							endOfMonth					= "";
			List<Company>					companyList					= null;
			ArrayList<CompanyDto> 			activeGroupList				= null;
			Fuel							fuelEntriesDetails			= null;
			TripSheet						tripsheetDetails			= null;
			ServiceEntries					serviceEntriesDetails		= null;
			WorkOrders						workOrderDetails			= null;
			long							renewalDetails				= 0;
			BatteryHistory					battery						= null;
			InventoryTyreHistory			tyre						= null;
			long							serviceReminderDetails		= 0;
			CompanyDto						companyDto					= null;
			
			try {
				activeGroupList			= new ArrayList<>();
				valueOutObject 			= DateTimeUtility.getPreviousMonthStartAndEndDate();
				startOfMonth 			= valueOutObject.getString("startOfMonth");
				endOfMonth 				= valueOutObject.getString("endOfMonth");
				
				companyList = findAll();
				activeGroupList = new ArrayList<>();
				
				String startDate[] 	= startOfMonth.split("-");
				String endDate[] 	= endOfMonth.split("-");
					
				String sDate 	= startDate[0];
				String sMonth 	= startDate[1];
				String sYear 	= startDate[2];
				
				
				String eDate 	= endDate[0];
				String eMonth 	= endDate[1];
				String eYear 	= endDate[2];
				
				String sStartDate = sYear+"-"+sMonth+"-"+sDate;
				String sEndDate	  = eYear+"-"+eMonth+"-"+eDate;
					
				String finalStartDate 		= sStartDate+" "+DateTimeUtility.DAY_END_TIME;
				String finalEndDate 		= sEndDate+" "+DateTimeUtility.DAY_END_TIME;
				
				for(Company company : companyList ) {
					companyDto = new CompanyDto();
					
					tripsheetDetails		= tripSheetService.getTripSheetCreatedBetweenDates(finalStartDate, finalEndDate,company.getCompany_id());
					if(tripsheetDetails != null) {
						companyDto.setCompany_id(company.getCompany_id());
						companyDto.setCompany_name(company.getCompany_name());
						activeGroupList.add(companyDto);
						continue;
					}
					
				}
				
				return activeGroupList;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Override
		public Map<Object,Object> getIvLoginUrlByCompanyId(){
			CustomUserDetails userDetails = Utility.getObject(null);
			Map<Object,Object> hashmap= new HashMap<>();
			String url = null;
			try {
				CompanyMapper companyMapper =companyMapperRepo.findGroupIdByFleetCompanyId(userDetails.getCompany_id());
				if(companyMapper != null && companyMapper.getIvServerIdentifier() > 0) {
					url =IVServerIdentifierConstant.getServerName(companyMapper.getIvServerIdentifier())+"Home.do?pageId=0&eventId=0";
					hashmap.put("url", url);
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return hashmap;
		}
		
		
		@Override
		public void updateCompanyConfigurationValue(ValueObject object) throws Exception {
			// TODO Auto-generated method stub
			CompanyConfiguration   validate       = null;
			CompanyConfiguration   companyConfig  = null;
			validate = companyConfigurationRepository.companyConfigurationAlreadyExits(object.getInt("moduleId"),object.getInt("companyId"), object.getString("propertyName"));
			
			if(validate == null) {
				companyConfig  =  companyConfigBL.prepareCompanyConfigObj(object);
				companyConfigurationRepository.save(companyConfig);
			}else {
				companyConfigurationRepository.updateCompanyConfigurationPropertyValue(object.getLong("configId"), object.getString("propertyValue"), object.getInt("moduleId"), object.getInt("companyId"));
			}
		}
		
}