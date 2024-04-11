package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.fleetopgroup.constant.CompanyConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.bl.ServiceProgramBL;
import org.fleetopgroup.persistence.bl.ServiceReminderBL;
import org.fleetopgroup.persistence.dao.ServiceProgramAsignmentDetailsRepository;
import org.fleetopgroup.persistence.dao.ServiceProgramSchedulesRepository;
import org.fleetopgroup.persistence.dao.ServiceReminderRepository;
import org.fleetopgroup.persistence.dao.VehicleServiceProgramRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.ServiceProgramSchedulesDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.VehicleServiceProgramDto;
import org.fleetopgroup.persistence.dto.VehicleTypeDto;
import org.fleetopgroup.persistence.model.ServiceProgramAsignmentDetails;
import org.fleetopgroup.persistence.model.ServiceProgramSchedules;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleServiceProgram;
import org.fleetopgroup.persistence.report.dao.ServiceProgramDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IServiceProgramAsignmentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IServiceProgramService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceProgramService implements IServiceProgramService {
	
	@PersistenceContext EntityManager entityManager;

	@Autowired private	VehicleServiceProgramRepository		vehicleServiceProgramRepository;
	@Autowired private	ServiceProgramDao					serviceProgramDao;
	@Autowired private	ServiceProgramSchedulesRepository	serviceProgramSchedulesRepository;
	@Autowired private  IVehicleService						vehicleService;
	@Autowired private	IServiceReminderService				serviceReminderService;
	@Autowired private	ServiceProgramAsignmentDetailsRepository	asignmentDetailsRepository;
	@Autowired private	IServiceProgramAsignmentDetailsService		asignmentDetailsService;
	@Autowired private ICompanyConfigurationService companyConfigurationService;
	
	@Autowired private ServiceReminderRepository serviceReminderRepository;
	
	private static final int PAGE_SIZE = 10;
	
	ServiceReminderBL 	SRBL					= new ServiceReminderBL();

	@Override
	public ValueObject getServiceProgramList(ValueObject valueObject) throws Exception {
		CustomUserDetails 					userDetails 					= null;
		long 								totalCount				 		= 0;
		long 								totalSRCount				 	= 0;
		HashMap<String, Object> 			configuration					= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			Page<VehicleServiceProgram> page = getDeployment_Page_ServiceProgram(valueObject.getInt("pagenumber"), userDetails);
			
			if (page != null) {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
				valueObject.put("configuration", configuration);
				totalCount = page.getTotalElements();
			}
			
			List<VehicleServiceProgramDto> serviceProgramList = getServiceProgramList(valueObject.getInt("pagenumber"), userDetails);
			if(serviceProgramList != null && !serviceProgramList.isEmpty()) {
				for(VehicleServiceProgramDto dto:serviceProgramList) {
					dto.setServiceReminderCount(serviceReminderRepository.getServiceReminderByserviceProgram(userDetails.getCompany_id(), dto.getVehicleServiceProgramId())); 
				}
			}
			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());	
			Page<ServiceReminder> pageSr =serviceReminderService.getDeployment_Page_ServiceReminder(1, userDetails);
			
			if (pageSr != null) {
				totalSRCount=pageSr.getTotalElements();
			}
			valueObject.put("TodayDueServiceRemindercount",serviceReminderService.countTodayDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));
			valueObject.put("TodayOverDueServiceRemindercount",serviceReminderService.countTodayOverDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));
			valueObject.put("serviceProgram", serviceProgramList);
			valueObject.put("SelectPage", valueObject.getInt("pagenumber"));
			valueObject.put("totalCount", totalCount);
			valueObject.put("totalSRCount", totalSRCount);
			valueObject.put("scheduleCountHM", serviceProgramDao.getServiceProgramWiseCountHM(valueObject.getInt("companyId")));
			return valueObject;
			
		} catch (Exception e) {
			System.err.println("ERR"+e);
			throw e;
		}finally {
			
		}
	}
	
	public Page<VehicleServiceProgram> getDeployment_Page_ServiceProgram(Integer pageNumber,
			CustomUserDetails userDetails) throws Exception {

		try {
				@SuppressWarnings("deprecation")
				Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
				return vehicleServiceProgramRepository.getDeployment_Page_ServiceProgram(userDetails.getCompany_id(), pageable);
			
		} catch (Exception e) {
			throw e;
		}
	}

	public List<VehicleServiceProgramDto>  getServiceProgramList(Integer pageNumber, CustomUserDetails userDetails) throws Exception{
		try {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					"SELECT SE.vehicleServiceProgramId, SE.programName, SE.description, SE.companyId, SE.isVendorProgram"
							+ " FROM VehicleServiceProgram  AS SE "
							+ " WHERE (SE.companyId = " + userDetails.getCompany_id()+" OR SE.isVendorProgram = 1 )"
							+ " AND SE.markForDelete = 0  ORDER BY SE.vehicleServiceProgramId DESC ",
					Object[].class);
			
			query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			query.setMaxResults(PAGE_SIZE);
			List<Object[]> results = query.getResultList();

			List<VehicleServiceProgramDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleServiceProgramDto>();
				VehicleServiceProgramDto list = null;
				for (Object[] result : results) {
					list = new VehicleServiceProgramDto();

					list.setVehicleServiceProgramId((Long) result[0]);
					list.setProgramName((String) result[1]);
					list.setDescription((String) result[2]);
					list.setCompanyId((Integer) result[3]);
					list.setVendorProgram((boolean) result[4]);

					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject saveServiceProgram(ValueObject valueObject) throws Exception {
		VehicleServiceProgram		vehicleServiceProgram			= null;
		try {
			 if(!valueObject.containsKey("programName") || valueObject.getString("programName", null) == null ) {
				 valueObject.put("noProgramName", true);
				 return valueObject;
			 }
			 vehicleServiceProgram	= ServiceProgramBL.getVehicleServiceProgramDTO(valueObject);
			 vehicleServiceProgramRepository.save(vehicleServiceProgram);
			 
			 valueObject.put("saved", true);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getServiceProgramDetailsById(ValueObject valueObject) throws Exception {
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("serviceProgram", serviceProgramDao.getServiceProgramDetailsById(valueObject.getLong("vehicleServiceProgramId")));
			valueObject.put("serviceSchedules", serviceProgramDao.getServiceProgramSchedulesBtId(valueObject.getLong("vehicleServiceProgramId")));
			valueObject.put("companyId", userDetails.getCompany_id());
			valueObject.put("userId", userDetails.getId());
			valueObject.put("asignmentList", asignmentDetailsService
					.getAsignmentListByServiceProgramId(valueObject.getLong("vehicleServiceProgramId"), userDetails.getCompany_id()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveServiceProramSchedule(ValueObject valueObject) throws Exception {
		ServiceProgramSchedules					serviceProgramSchedules		= null;
		List<ServiceProgramSchedules>			validate					= null;
		List<ServiceProgramAsignmentDetails>	assignmentList				= null;
		HashMap<String, Object> 				configuration	= null;
		String									branchQuery					= "";
		try {
			configuration		= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId",0), PropertyFileConstants.SERVICE_PROGRAM_CONFIGURATION_CONFIG);
			validate = serviceProgramSchedulesRepository.validateServiceSchedule(valueObject.getLong("vehicleServiceProgramId", 0), valueObject.getInt("jobSubTypeId",0));
			if(validate == null || validate.isEmpty()) {
				serviceProgramSchedules	= ServiceProgramBL.getServiceProgramSchedules(valueObject);
				serviceProgramSchedulesRepository.save(serviceProgramSchedules);
				assignmentList	=	asignmentDetailsRepository.validateAsignmentByProgramId(serviceProgramSchedules.getVehicleServiceProgramId() , serviceProgramSchedules.getCompanyId());
				if(assignmentList != null && !assignmentList.isEmpty()) {
					List<Vehicle> vehicleList	= null;
					for (ServiceProgramAsignmentDetails asignment : assignmentList) {
						branchQuery	= "AND VGP.branchId = "+asignment.getBranchId()+"";
						if((boolean) configuration.getOrDefault("vehicleBranchWiseProgram", false) && asignment.getBranchId() > 0) {
							vehicleList	= vehicleService.getVehicleByTypeModalAndBranch( asignment.getVehicleTypeId(), asignment.getVehicleModalId(), asignment.getBranchId(), asignment.getCompanyId());
						}else {
							vehicleList	= vehicleService.getVehicleByTypeAndModal( asignment.getVehicleTypeId(), asignment.getVehicleModalId(), asignment.getCompanyId());
						}
						
						if(vehicleList != null && !vehicleList.isEmpty()) {
							Map<String, ServiceReminder>	reminderHM	=		serviceReminderService.getMapOfServiceReminderByCompanyId( asignment.getVehicleTypeId(), asignment.getVehicleModalId(), asignment.getCompanyId(),branchQuery);
							
							for (Vehicle vehicle : vehicleList) {
								if(vehicle.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_SOLD && 
										reminderHM == null || reminderHM.get(vehicle.getVid()+"_"+serviceProgramSchedules.getJobTypeId()+"_"+serviceProgramSchedules.getJobSubTypeId()) == null) {
									vehicleService.getServiceReminderListToSave(serviceProgramSchedules, vehicle);
								}
							}
						}
					}
				}
			}else {
				valueObject.put("alreadyExist", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			serviceProgramSchedules		= null;
		}
	}

	@Override
	@Transactional
	public ValueObject deleteServiceSchedule(ValueObject valueObject) throws Exception {
		try {
			serviceReminderService.deleteServiceReminderForSchedule(valueObject.getLong("serviceScheduleId",0)
					, valueObject.getInt("companyId",0), valueObject.getLong("userId",0)
					, valueObject.getLong("serviceProgramId",0));
			serviceProgramSchedulesRepository.deleteServiceSchedule(valueObject.getLong("serviceScheduleId",0));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	@Transactional
	public ValueObject deleteServiceProgram(ValueObject valueObject) throws Exception {
		 List<ServiceProgramSchedules>		validate		= null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			validate	=	serviceProgramSchedulesRepository.getServiceProgramSchedulesListByProgramId(valueObject.getLong("vehicleServiceProgramId",0));
			
			if(validate == null || validate.isEmpty()) {
				serviceProgramSchedulesRepository.deleteServiceScheduleByProgramId(valueObject.getLong("vehicleServiceProgramId",0));
				vehicleServiceProgramRepository.deleteServiceProgram(valueObject.getLong("vehicleServiceProgramId",0));
				vehicleService.updateVehicleByServiceProgramId(valueObject.getLong("vehicleServiceProgramId",0), (long) 0, userDetails.getCompany_id());
				asignmentDetailsRepository.deleteProgramAsignment(valueObject.getLong("vehicleServiceProgramId",0), 
						 					userDetails.getCompany_id());
				
				valueObject.put("success", true);
			}else {
				valueObject.put("serviceScheduleExit", true);
			}
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleServiceProgramDetailsById(ValueObject valueObject) throws Exception {
		try {
			valueObject.put("serviceProgram", serviceProgramDao.getServiceProgramDetailsById(valueObject.getLong("vehicleServiceProgramId")));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject updateServiceProgram(ValueObject valueObject) throws Exception {
		VehicleServiceProgram		vehicleServiceProgram			= null;
		try {
			 if(!valueObject.containsKey("programName") || valueObject.getString("programName", null) == null ) {
				 valueObject.put("noProgramName", true);
				 return valueObject;
			 }
			 vehicleServiceProgram	= ServiceProgramBL.getVehicleServiceProgramDTO(valueObject);
			 vehicleServiceProgram.setVehicleServiceProgramId(valueObject.getLong("vehicleServiceProgramId"));
			 vehicleServiceProgramRepository.save(vehicleServiceProgram);
			 
			 valueObject.put("saved", true);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleServiceProgram> getVehicleServiceProgram(String term, Integer companyId) throws Exception {
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<VehicleServiceProgram> query = entityManager
				.createQuery("from VehicleServiceProgram  where (companyId = "+companyId+" OR isVendorProgram = 1)  AND markForDelete = 0 AND  lower(programName) Like ('%" + term + "%')", VehicleServiceProgram.class);
		return query.getResultList();
		}else {
			return null;
		}
	}
	
	@Override
	public List<VehicleTypeDto> getVehicleServiceProgramByTypeId(Long typeId, Integer companyId)
			throws Exception {
				TypedQuery<Object[]> query = entityManager
						.createQuery("SELECT v.tid, v.vtype, v.maxAllowedOdometer, v.company_Id,"
								+ " SP.vehicleServiceProgramId, SP.programName, SP2.vehicleServiceProgramId, SP2.programName"
								+ " FROM VehicleType as v "
								+ " LEFT JOIN VehicleServiceProgram SP ON SP.vehicleServiceProgramId = v.serviceProgramId"
								+ " LEFT JOIN VehicleServiceProgram SP2 ON SP2.vehicleServiceProgramId = v.superProgramId"
								+ " where v.tid ="+typeId+" AND (v.company_Id = " + companyId+" OR v.company_Id = 0)"
								+ " and v.markForDelete = 0 ", Object[].class);
				query.setMaxResults(1);
				List<Object[]> results = query.getResultList();
		
				List<VehicleTypeDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<VehicleTypeDto>();
					VehicleTypeDto list = null;
					for (Object[] result : results) {
						list = new VehicleTypeDto();
						
						list.setTid((Long) result[0]);
						list.setVtype((String) result[1]);
						list.setMaxAllowedOdometer((Integer) result[2]);
						list.setCompanyId((Integer) result[3]);
						list.setServiceProgramId((Long) result[4]);
						list.setProgramName((String) result[5]);
						
						if(list.getServiceProgramId() == null || list.getServiceProgramId() <= 0 || companyId.equals(0) || companyId.equals(CompanyConstant.COMPANY_CODE_FLEETOP)) {
							
							list.setServiceProgramId((Long) result[6]);
							list.setProgramName((String) result[7]);
						}
		
						Dtos.add(list);
					}
				}
				return Dtos;
	}
	
	@Override
	public List<VehicleServiceProgram> getServiceProgramListByCompanyId(Integer companyId) throws Exception {
		
		return	vehicleServiceProgramRepository.getServiceProgramListByCompanyId(companyId);
	}
	
	
	
	@Override
	public List<ServiceProgramSchedulesDto> getServiceProgramSchedulesBtId(Long id) throws Exception {
		return serviceProgramDao.getServiceProgramSchedulesBtId(id);
	}
	
	@Override
	public VehicleServiceProgram getVehicleServiceProgramDetailsByName(String programName, Integer companyId) throws Exception {
		try {
			return vehicleServiceProgramRepository.getServiceProgramDetailsByName(programName,companyId);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void saveServiceProgramList(List<VehicleServiceProgram> VehicleServiceProgramList) throws Exception {
		try {
			 vehicleServiceProgramRepository.saveAll(VehicleServiceProgramList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public VehicleServiceProgram saveServiceProgram(VehicleServiceProgram vehicleServiceProgram) throws Exception {
		try {
			return vehicleServiceProgramRepository.save(vehicleServiceProgram);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public void saveServiceProramSchedule(ServiceProgramSchedules serviceProgramSchedules) throws Exception {
		List<ServiceProgramSchedules>	validate					= null;  
		
		try {
			validate = serviceProgramSchedulesRepository.validateServiceSchedule(serviceProgramSchedules.getVehicleServiceProgramId(), serviceProgramSchedules.getJobSubTypeId());
			if((validate == null || validate.isEmpty()) ) {
				serviceProgramSchedulesRepository.save(serviceProgramSchedules);
			}
			
		} catch (Exception e) {
			throw e;
		}finally {
			serviceProgramSchedules		= null;
		}
	}
	
	@Override
	public  XSSFSheet getHssfSheetOfServiceProgram(DataValidationHelper validationHelper, XSSFSheet hssfSheet) throws Exception {
		/*CustomUserDetails		userDetails				= null;
		List<JobType>  	 		jobTypeList				= null;
		List<JobSubType>  	 	jobSubTypeList			= null;*/
		try {
		//	userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			/*jobTypeList 	= jobTypeService.listJobTypeByCompanyId(userDetails.getCompany_id());
			jobSubTypeList	= jobSubTypeService.listJobSubTypeListByCompanyId(userDetails.getCompany_id());*/
			
			/* this line will add Tyre manufacturer dropdown in excel from 2 to 50 row first column  */
		//	hssfSheet.addValidationData(getDropDownList(2,50,3,3, getJobypeArr(jobTypeList), validationHelper));
		    
		    /* this line will add Tyre Model dropdown in excel from 2 to 50 row second column  */
		//	hssfSheet.addValidationData(getDropDownList(2,50,2,2, getSubJobypeArr(jobSubTypeList), validationHelper));
		    
		    hssfSheet.addValidationData(getDropDownList(1,50,6,6, timeTypeArr(), validationHelper));
		   
		    hssfSheet.addValidationData(getDropDownList(1,50,9,9, timeTypeArr(), validationHelper));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return hssfSheet;
	}

	private DataValidation  getDropDownList(int firstRow, int lastRow, int firstCol, int lastCol, String[] myarray, DataValidationHelper validationHelper) throws Exception{
		DataValidation 				dataValidation 					= null;
		CellRangeAddressList		addressList 					= null;
		DataValidationConstraint 	constraint 						= null;
		try {
			
			addressList 	= new  CellRangeAddressList(firstRow,lastRow,firstCol,lastCol);
		    constraint 		= validationHelper.createExplicitListConstraint(myarray);
		    dataValidation  = validationHelper.createValidation(constraint, addressList);
		    dataValidation.setSuppressDropDownArrow(true);   
			
			return dataValidation;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/*private String []  getJobypeArr(List<JobType> list) {
		List<String>  				maStrings 			= null;	
		try {
				maStrings = new ArrayList<>();
				for(JobType 	jobType : list){
					  maStrings.add(jobType.getJob_Type()+"-"+jobType.getJob_id());
				}
				return  Arrays.copyOf(maStrings.toArray(), maStrings.size(), String[].class);
			} catch (Exception e) {
				throw e;
			}finally {
				maStrings 			= null;
			}
 }
	private String []  getSubJobypeArr(List<JobSubType> list) {
		List<String>  				maStrings 			= null;	
		try {
				maStrings = new ArrayList<>();
				for(JobSubType 	jobSubType : list){
					  maStrings.add(jobSubType.getJob_ROT()+"-"+jobSubType.getJob_Subid());
				}
				return  Arrays.copyOf(maStrings.toArray(), maStrings.size(), String[].class);
			} catch (Exception e) {
				throw e;
			}finally {
				maStrings 			= null;
			}
 }*/
	private String []  timeTypeArr() {
		List<String>  				maStrings 			= null;	
		try {
			maStrings = new ArrayList<>();
			maStrings.add("Day");
			maStrings.add("Month");
			maStrings.add("Year");
			return  Arrays.copyOf(maStrings.toArray(), maStrings.size(), String[].class);
		} catch (Exception e) {
			throw e;
		}finally {
			maStrings 			= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveServiceProramAsign(ValueObject valueObject) throws Exception {
		List<Vehicle>  							vehicleList		= null;
		Map<String, ServiceReminder>			reminderHM		= null;
		List<ServiceProgramAsignmentDetails>    validate		= null;
		HashMap<String, Object> 				configuration	= null;
		String 									branchQuery		= "";
		try {
			configuration		= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId",0), PropertyFileConstants.SERVICE_PROGRAM_CONFIGURATION_CONFIG);

			if((boolean) configuration.getOrDefault("vehicleBranchWiseProgram", false) && valueObject.getInt("branchId",0) > 0) {
				branchQuery	= "AND VGP.branchId = "+valueObject.getInt("branchId",0)+"";
				validate	=	asignmentDetailsRepository.validateAsignmentByVehicelTypeModalAndBranch( valueObject.getLong("vehicleType",0), valueObject.getLong("vehicleModal",0),valueObject.getInt("branchId",0), valueObject.getInt("companyId",0), valueObject.getLong("serviceProgramId",0));
			}else {
				validate	=	asignmentDetailsRepository.validateAsignment( valueObject.getLong("vehicleType",0), valueObject.getLong("vehicleModal",0), valueObject.getInt("companyId",0), valueObject.getLong("serviceProgramId",0));
			}

			if(validate == null || validate.isEmpty()) {
				/*if((boolean) configuration.getOrDefault("vehicleBranchWiseProgram", false) && valueObject.getInt("branchId",0) > 0) {
					vehicleList	= vehicleService.getVehicleByTypeModalAndBranch(valueObject.getLong("vehicleType",0),valueObject.getLong("vehicleModal",0), valueObject.getInt("branchId",0),valueObject.getInt("companyId",0));
				}else {
					vehicleList	= vehicleService.getVehicleByTypeAndModal(valueObject.getLong("vehicleType",0), valueObject.getLong("vehicleModal",0), valueObject.getInt("companyId",0));
				}*/
				if(valueObject.getString("vehicleIds",null) != null && !valueObject.getString("vehicleIds").trim().equals("")) {
					vehicleList	= vehicleService.getVehicleListForSR(valueObject.getString("vehicleIds"),valueObject.getInt("companyId",0));
				}

				asignmentDetailsRepository.save(ServiceProgramBL.getServiceProgramAsignmentDetailsDTO(valueObject));

				if(vehicleList != null && !vehicleList.isEmpty() && valueObject.getLong("serviceProgramId",0) > 0) {
					List<ServiceProgramSchedulesDto> 	serviceScheduleList		= getServiceProgramSchedulesBtId(valueObject.getLong("serviceProgramId",0));
					
					reminderHM	= serviceReminderService.getMapOfServiceReminderByCompanyId(valueObject.getLong("vehicleType",0), valueObject.getLong("vehicleModal",0), valueObject.getInt("companyId",0),branchQuery);
				
					for (Vehicle vehicle : vehicleList) {
						if(vehicle.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_SOLD && 
								serviceScheduleList != null && !serviceScheduleList.isEmpty()) {
							for(ServiceProgramSchedulesDto schedulesDto : serviceScheduleList) {
								if(reminderHM == null || reminderHM.get(vehicle.getVid()+"_"+schedulesDto.getJobTypeId()+"_"+schedulesDto.getJobSubTypeId()) == null) {
									vehicleService.getServiceReminderListToSave(schedulesDto, vehicle);
								}
								else {
									vehicleService.updateServiceReminder(schedulesDto, vehicle, reminderHM.get(vehicle.getVid()+"_"+schedulesDto.getJobTypeId()+"_"+schedulesDto.getJobSubTypeId()));
								}
							}
						}

						vehicleService.updateServiceProgramId(valueObject.getLong("serviceProgramId",0), vehicle.getVid());
					}
				}

			}else {
				valueObject.put("alreadyAsigned", true);
			}

			return valueObject;
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteServiceProgramAssignment(ValueObject valueObject) throws Exception {
		String									serviceIds		= "";
		
		try {
			if(valueObject.getBoolean("vehicleBranchWiseProgramConfig")) {
				asignmentDetailsService.deleteBranchWiseServiceProgramAssignment(valueObject.getLong("vehicleTypeId",0), 
						valueObject.getLong("vehicleModalId",0),valueObject.getInt("branchId",0), valueObject.getLong("serviceProgramId",0),
						valueObject.getInt("companyId",0));
				List<ServiceReminderDto> ServiceReminderList = serviceReminderService.getSRByVehicleTypeModalAndBranach(valueObject.getLong("vehicleTypeId",0), 
						valueObject.getLong("vehicleModalId",0),valueObject.getInt("branchId",0), valueObject.getLong("serviceProgramId",0),
						valueObject.getInt("companyId",0));
				if(ServiceReminderList != null && !ServiceReminderList.isEmpty()) {
					for(ServiceReminderDto dto : ServiceReminderList) {
						serviceIds += dto.getService_id()+",";
					}
					serviceReminderService.deleteReminderByServiceIds(org.fleetopgroup.web.util.Utility.removeLastComma(serviceIds));
				}
				
				vehicleService.updateBranchWiseVehicleServiceProgramStatus(valueObject.getLong("vehicleTypeId",0), 
						valueObject.getLong("vehicleModalId",0),valueObject.getInt("branchId",0), valueObject.getLong("serviceProgramId",0),
						valueObject.getInt("companyId",0));
			}else {
				asignmentDetailsService.deleteServiceProgramAssignment(valueObject.getLong("vehicleTypeId",0), 
						valueObject.getLong("vehicleModalId",0), valueObject.getLong("serviceProgramId",0),
						valueObject.getInt("companyId",0));
				List<ServiceReminderDto> ServiceReminderList = serviceReminderService.getSRByVehicleTypeAndModal(valueObject.getLong("vehicleTypeId",0),
						valueObject.getLong("vehicleModalId",0), valueObject.getLong("serviceProgramId",0),
						valueObject.getInt("companyId",0));
				if(ServiceReminderList != null && !ServiceReminderList.isEmpty()) {
					for(ServiceReminderDto dto : ServiceReminderList) {
						serviceIds += dto.getService_id()+",";
					}
					serviceReminderService.deleteReminderByServiceIds(org.fleetopgroup.web.util.Utility.removeLastComma(serviceIds));
				}
				
			/*	serviceReminderService.deleteReminderServiceProgramAssignment(valueObject.getLong("vehicleTypeId",0), 
						valueObject.getLong("vehicleModalId",0), valueObject.getLong("serviceProgramId",0),
						valueObject.getInt("companyId",0));*/
				vehicleService.updateVehicleServiceProgramStatus(valueObject.getLong("vehicleTypeId",0), 
						valueObject.getLong("vehicleModalId",0), valueObject.getLong("serviceProgramId",0),
						valueObject.getInt("companyId",0));
			}
			
			valueObject.put("deleted", true);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleTypeDto> getVehicleServiceProgramByTypeId(Long typeId, Long modalId, Integer companyId)
			throws Exception {
		TypedQuery<Object[]> query = entityManager
				.createQuery("SELECT SP.vehicleServiceProgramId, SP.programName"
						+ " FROM ServiceProgramAsignmentDetails as v "
						+ " INNER JOIN VehicleServiceProgram SP ON SP.vehicleServiceProgramId = v.serviceProgramId"
						+ " where v.vehicleTypeId ="+typeId+" AND v.vehicleModalId = "+modalId+"   AND v.companyId = " + companyId+" "
						+ " and v.markForDelete = 0 ", Object[].class);
		query.setMaxResults(1);
		List<Object[]> results = query.getResultList();

		List<VehicleTypeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleTypeDto>();
			VehicleTypeDto list = null;
			for (Object[] result : results) {
				list = new VehicleTypeDto();
				
				list.setServiceProgramId((Long) result[0]);
				list.setProgramName((String) result[1]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
}
	
	@Override
	public ValueObject getVehicleSheduleProgramList(ValueObject valueObject) throws Exception {
		List<ServiceProgramSchedulesDto> serviceProgramSchedulesDto = null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			serviceProgramSchedulesDto=serviceProgramDao.getServiceProgramSchedulesBtId(valueObject.getLong("vehicleServiceProgramId"));
			if(!serviceProgramSchedulesDto.isEmpty()) {
					for(ServiceProgramSchedulesDto dto:serviceProgramSchedulesDto) {
						if(dto.getVehicleServiceProgramId() != null && dto.getServiceScheduleId() != null )
							dto.setServiceReminderCount(serviceReminderRepository.getServiceReminderByserviceProgram (userDetails.getCompany_id(),dto.getVehicleServiceProgramId(),dto.getServiceScheduleId()));
					}
			}
			valueObject.put("serviceSchedules",serviceProgramSchedulesDto );
			valueObject.put("companyId", userDetails.getCompany_id());
			valueObject.put("userId", userDetails.getId());
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleReminderList(ValueObject valueObject) throws Exception {
		List<ServiceReminderDto> serviceReminderDto = null;
		
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			serviceReminderDto =	serviceReminderService.listServiceReminderByServiceProgram(valueObject.getLong("vehicleServiceProgramId",0),valueObject.getLong("serviceScheduleId",0), userDetails);

		valueObject.put("serviceReminderDto", SRBL.prepareListofServiceReminderAjax(serviceReminderDto) );
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Map<Long, VehicleServiceProgram> getServiceProgramHM(Integer companyId) throws Exception {
		 List<VehicleServiceProgram>		servicePrograms		= null;
		 Map<Long, VehicleServiceProgram> programHM	= null;	
		try {
			servicePrograms	= vehicleServiceProgramRepository.getServiceProgramListByCompanyId(companyId);
			
			if(servicePrograms != null && !servicePrograms.isEmpty()) {
				programHM	=	servicePrograms.stream().collect(Collectors.toMap(VehicleServiceProgram :: getVehicleServiceProgramId, Function.identity()));
			}
			
		} catch (Exception e) {
		}
		return programHM;
	}
	
	@Override
	public Map<Long, ServiceProgramSchedulesDto> getServiceProgramSchedulesHM(Integer companyId) throws Exception {
		try {
			Map<Long, ServiceProgramSchedulesDto>	scheduleHM = null;
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					"SELECT SE.serviceScheduleId, JT.Job_Type, JST.Job_ROT"
							+ " FROM ServiceProgramSchedules  AS SE "
							+ " INNER JOIN JobType JT ON JT.Job_id = SE.jobTypeId "
							+ " INNER JOIN JobSubType JST ON JST.Job_Subid = SE.jobSubTypeId"
							+ " WHERE SE.companyId = " + companyId+" "
							+ " AND SE.markForDelete = 0 ",
					Object[].class);
			
			List<Object[]> results = query.getResultList();

			List<ServiceProgramSchedulesDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceProgramSchedulesDto>();
				ServiceProgramSchedulesDto list = null;
				for (Object[] result : results) {
					list = new ServiceProgramSchedulesDto();

					list.setServiceScheduleId((Long) result[0]);
					list.setJobType((String) result[1]);
					list.setJobSubType((String) result[2]);

					Dtos.add(list);
				}
			}
				if(Dtos != null && !Dtos.isEmpty()) {
					scheduleHM =	Dtos.stream().collect(Collectors.
									toMap(ServiceProgramSchedulesDto :: getServiceScheduleId, Function.identity()));
				}
			
			return scheduleHM;
		} catch (Exception e) {
			throw e;
		}
	}
}
