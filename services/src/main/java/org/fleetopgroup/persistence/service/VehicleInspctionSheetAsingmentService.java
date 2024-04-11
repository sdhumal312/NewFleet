package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.VehicleInspectionStatus;
import org.fleetopgroup.persistence.bl.VehicleInspectionBL;
import org.fleetopgroup.persistence.dao.VehicleDailyInspectionRepository;
import org.fleetopgroup.persistence.dao.VehicleInspctionSheetAsingmentRepository;
import org.fleetopgroup.persistence.dao.VehicleInspectionSheetRepository;
import org.fleetopgroup.persistence.dao.VehicleInspectionSheetToParameterRepository;
import org.fleetopgroup.persistence.dao.VehicleTypeAssignmentRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleInspctionSheetAsingmentDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionSheetDto;
import org.fleetopgroup.persistence.dto.VehicleTypeAssignmebtDetailsDto;
import org.fleetopgroup.persistence.model.VehicleDailyInspection;
import org.fleetopgroup.persistence.model.VehicleInspectionSheet;
import org.fleetopgroup.persistence.model.VehicleInspectionSheetAssignment;
import org.fleetopgroup.persistence.model.VehicleInspectionSheetToParameter;
import org.fleetopgroup.persistence.model.VehicleType;
import org.fleetopgroup.persistence.model.VehicleTypeAssignmentDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInspectionCompletionToParameterService;
import org.fleetopgroup.persistence.serviceImpl.IInspectionToParameterDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDailyInspectionService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspctionSheetAsingmentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionCompletionDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionSheetService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionSheetToParameterService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTypeAssignmentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTypeService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleInspctionSheetAsingmentService implements IVehicleInspctionSheetAsingmentService {

	@PersistenceContext	public EntityManager entityManager;
	
	private @Autowired	VehicleInspctionSheetAsingmentRepository		vehicleInspctionSheetAsingmentRepository;
	
	private @Autowired	IVehicleGroupService							vehicleGroupService;
	
	private @Autowired	IVehicleService									vehicleService;
	
	private @Autowired	IVehicleInspectionSheetService					vehicleInspectionSheetService;
	
	private @Autowired	IVehicleInspectionSheetToParameterService		sheetToParameterService;
	
	private	@Autowired 	IVehicleInspctionSheetAsingmentService			asingmentService;
	
	private @Autowired 	IVehicleDocumentService							vehicleDocumentService;
	
	private	@Autowired IUserProfileService 								userProfileService;
	
	private @Autowired IVehicleInspectionCompletionDetailsService		vehicleInspectionCompletionDetailsService;
	
	private @Autowired IInspectionCompletionToParameterService			completionToParameterService;
	
	private @Autowired IInspectionToParameterDocumentService			documentService;
	
	private @Autowired IVehicleDailyInspectionService					VehicleDailyInspectionService;
	
	private @Autowired VehicleDailyInspectionRepository					VehicleDailyInspectionRepository;
	
	private @Autowired VehicleInspectionSheetToParameterRepository		VehicleInspectionSheetToParameterRepository;
	
	@Autowired private VehicleInspectionSheetRepository					vehicleInspectionSheetRepository;
	
	
	@Autowired private ICompanyConfigurationService 					companyConfigurationService;
	
	@Autowired  private IVehicleTypeService								vehicletypeService;
	
	@Autowired private VehicleTypeAssignmentRepository					vehicleTypeAssignmentRepository;
	
	@Autowired private IVehicleTypeAssignmentDetailsService        vehicleTypeAssignmentDetailsService;
	
	VehicleInspectionBL		vehicleInspectionBL					= new VehicleInspectionBL();
	
	SimpleDateFormat 	dateFormat_Name 	= new SimpleDateFormat("yyyy-MM-dd");
	
	SimpleDateFormat 		dateFormat 			= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat 		dateFormat2 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 		format 				= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Override
	public VehicleInspctionSheetAsingmentDto getVehicleInspctionSheetAsingmentbyVid(Integer vid, Integer companyId)
			throws Exception {
		
		try {
			Query query = entityManager.createQuery(
					"SELECT VA.vehicleInspctionSheetAsingmentId, VA.vid, VA.inspectionStartDate, VA.inspectionSheetId, VA.assignDate, VA.assignById , U.firstName ,"
					+ " VS.inspectionSheetName"
					+ " FROM VehicleInspectionSheetAssignment AS VA"
					+ " INNER JOIN VehicleInspectionSheet VS ON VS.vehicleInspectionSheetId = VA.inspectionSheetId "
					+ " INNER JOIN User U ON U.id = VA.assignById"
					+ " where VA.vid ="+vid+" AND VA.companyId = "+companyId+" AND VA.markForDelete = 0");
			
					Object[] result = null;
					try {
						result = (Object[]) query.getSingleResult();
					} catch (NoResultException nre) {
						// Ignore this because as per your logic this is ok!
					}
		
					VehicleInspctionSheetAsingmentDto select;
					if (result != null) {
						select = new VehicleInspctionSheetAsingmentDto();
						
						select.setVehicleInspctionSheetAsingmentId((Long) result[0]);
						select.setVid((Integer) result[1]);
						select.setInspectionStartDate((Timestamp) result[2]);
						select.setInspectionSheetId((Long) result[3]);
						select.setAssignDate((Timestamp) result[4]);
						select.setAssignById((Long) result[5]);
						select.setAssignByName((String) result[6]);
						select.setInspectionSheetName((String) result[7]);
						
						select.setInspectionStartDateStr(DateTimeUtility.getDateFromTimeStamp(select.getInspectionStartDate(), DateTimeUtility.DD_MM_YYYY));
						select.setAssignDateStr(DateTimeUtility.getDateFromTimeStamp(select.getAssignDate(), DateTimeUtility.DD_MM_YYYY));
						
		
					} else {
						return null;
					}
		
					return select;
				
					
		} catch (Exception e) {
			throw e;
		}
	}

	
	@Override
	public void saveVehicleAssignment(VehicleInspectionSheetAssignment assignment) throws Exception {
		try {
			
			vehicleInspctionSheetAsingmentRepository.save(assignment);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void deleteVehicleAssignment(Long vehicleInspctionSheetAsingmentId, Integer vid) throws Exception {
		
		vehicleInspctionSheetAsingmentRepository.deleteVehicleAssignment(vehicleInspctionSheetAsingmentId, vid);
	}
	
	@Override
	public long getTodaysVehicleInspectionCount(Integer companyId, Timestamp	inspectionStartDate) throws Exception {
		
		return vehicleInspctionSheetAsingmentRepository.getTodaysVehicleInspectionCount(companyId, inspectionStartDate);
	}
	
	//This is the method with data from old Table and not in use. New method name is getTodaysVehicleInspectionListNew in VehicleDailyInspectionService.
	@Override
	public List<VehicleInspctionSheetAsingmentDto> getTodaysVehicleInspectionList(Integer companyId,
			Timestamp inspectionStartDate) throws Exception {
		
		String todaysDate	= DateTimeUtility.getCurrentDate()+" 00:00:00";
		
		TypedQuery<Object[]> queryt = null;
			
			queryt = entityManager.createQuery("SELECT VS.vehicleInspctionSheetAsingmentId, VS.vid, VS.inspectionStartDate, VS.inspectionSheetId, VS.assignDate, VS.assignById,"
					+ " V.vehicle_registration, VIS.inspectionSheetName, VCD.inspectionStatusId, VCD.completionDetailsId"
					+ " FROM VehicleInspectionSheetAssignment AS VS "
					+ " INNER JOIN Vehicle V ON V.vid = VS.vid"
					+ " INNER JOIN VehicleInspectionSheet VIS ON VIS.vehicleInspectionSheetId = VS.inspectionSheetId"
					+ " LEFT JOIN VehicleInspectionCompletionDetails VCD ON VCD.inspectionSheetId = VS.inspectionSheetId AND VCD.vid = VS.vid AND VCD.markForDelete = 0 "
					+ " AND VCD.inspectionDate = '"+todaysDate+"' "
					+ " where VS.inspectionStartDate <= '"+inspectionStartDate+"' AND VS.companyId = "+companyId+"  and VS.markForDelete = 0 ",
					Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<VehicleInspctionSheetAsingmentDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			
			Dtos = new ArrayList<VehicleInspctionSheetAsingmentDto>();
			VehicleInspctionSheetAsingmentDto asingment = null;
			for (Object[] result : results) {
				asingment = new VehicleInspctionSheetAsingmentDto();
				
				asingment.setVehicleInspctionSheetAsingmentId((Long) result[0]);
				asingment.setVid((Integer) result[1]);
				asingment.setInspectionStartDate((Timestamp) result[2]);
				asingment.setInspectionSheetId((Long) result[3]);
				asingment.setAssignDate((Timestamp) result[4]);
				asingment.setAssignById((Long) result[5]);
				asingment.setVehicle_registration((String) result[6]);
				asingment.setInspectionSheetName((String) result[7]);
				if(result[8] != null) {
					asingment.setInspectionStatusId((short) result[8]);
					asingment.setInspectionStatusName(VehicleInspectionStatus.getVehicleInspectionStatusName((short) result[8]));
				}else {
					asingment.setInspectionStatusName(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN_NAME);
				}
				asingment.setCompletionDetailsId((Long) result[9]);
				
				asingment.setInspectionStartDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getInspectionStartDate(), DateTimeUtility.DD_MM_YYYY));
				asingment.setAssignDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getAssignDate(), DateTimeUtility.DD_MM_YYYY));

				Dtos.add(asingment);
			}
		}
		return Dtos;
	}
	
	@Override
	public VehicleInspctionSheetAsingmentDto	getVehicleInspctionSheetAsingmentbyAssignmentId(Long	assignmentId, Integer companyId) throws Exception {
		String todaysDate	= DateTimeUtility.getCurrentDate()+" 00:00:00";
		try {
			Query query = entityManager.createQuery(
					"SELECT VA.vehicleInspctionSheetAsingmentId, VA.vid, VA.inspectionStartDate, VA.inspectionSheetId, VA.assignDate, VA.assignById ,"
					+ " VS.inspectionSheetName, V.vehicle_registration, VG.vGroup, VD.completionDetailsId ,VA.vehicleTypeId "
					+ " FROM VehicleInspectionSheetAssignment AS VA"
					+ " INNER JOIN VehicleInspectionSheet VS ON VS.vehicleInspectionSheetId = VA.inspectionSheetId "
					+ " INNER JOIN Vehicle V ON V.vid = VA.vid"
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
					+ " LEFT JOIN VehicleInspectionCompletionDetails VD ON VD.inspectionSheetId = VA.inspectionSheetId "
					+ " AND VD.vid = VA.vid AND VD.inspectionDate = '"+todaysDate+"'"
					+ " where VA.vehicleInspctionSheetAsingmentId ="+assignmentId+" AND VA.companyId = "+companyId+" ");
			
					Object[] result = null;
					try {
						result = (Object[]) query.getSingleResult();
					} catch (NoResultException nre) {
						// Ignore this because as per your logic this is ok!
					}
		
					VehicleInspctionSheetAsingmentDto select;
					if (result != null) {
						select = new VehicleInspctionSheetAsingmentDto();
						
						select.setVehicleInspctionSheetAsingmentId((Long) result[0]);
						select.setVid((Integer) result[1]);
						select.setInspectionStartDate((Timestamp) result[2]);
						select.setInspectionSheetId((Long) result[3]);
						select.setAssignDate((Timestamp) result[4]);
						select.setAssignById((Long) result[5]);
						select.setInspectionSheetName((String) result[6]);
						select.setVehicle_registration((String) result[7]);
						select.setVehicleGroupName((String) result[8]);
						select.setCompletionDetailsId((Long) result[9]);
//						select.setPenaltyAmount((Double) result[10]);
						select.setVehicleTypeId((Long) result[10]);
						select.setInspectionStartDateStr(DateTimeUtility.getDateFromTimeStamp(select.getInspectionStartDate(), DateTimeUtility.DD_MM_YYYY));
						select.setAssignDateStr(DateTimeUtility.getDateFromTimeStamp(select.getAssignDate(), DateTimeUtility.DD_MM_YYYY));
		
					} else {
						return null;
					}
		
					return select;
				
					
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public VehicleInspctionSheetAsingmentDto getVehicleInspctionSheetAsingmentbyCompletionID(Long completionID,
			Integer companyId) throws Exception {
		
		try {
			Query query = entityManager.createQuery(
					"SELECT VA.vehicleInspctionSheetAsingmentId, VA.vid, VA.inspectionStartDate, VA.inspectionSheetId, VA.assignDate, VA.assignById ,"
					+ " VS.inspectionSheetName, V.vehicle_registration, VG.vGroup, VD.completionDetailsId, VD.inspectedById, U.firstName,VD.totalPenalty  "
					+ " FROM  VehicleInspectionSheet VS "
					+ " INNER JOIN VehicleInspectionSheetAssignment AS VA ON VA.inspectionSheetId = VS.vehicleInspectionSheetId "
					+ " INNER JOIN Vehicle V ON V.vid = VA.vid"
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
					+ " LEFT JOIN VehicleInspectionCompletionDetails VD ON VD.inspectionSheetId = VA.inspectionSheetId AND VD.vid = VA.vid AND VD.vehicleInspctionSheetAsingmentId = VA.vehicleInspctionSheetAsingmentId"
					+ " LEFT JOIN User U ON U.id = VD.inspectedById"
					+ " where VD.completionDetailsId ="+completionID+" AND VA.companyId = "+companyId+"");
			
					Object[] result = null;
					try {
						result = (Object[]) query.getSingleResult();
					} catch (NoResultException nre) {
						// Ignore this because as per your logic this is ok!
					}
		
					VehicleInspctionSheetAsingmentDto select;
					if (result != null) {
						select = new VehicleInspctionSheetAsingmentDto();
						
						select.setVehicleInspctionSheetAsingmentId((Long) result[0]);
						select.setVid((Integer) result[1]);
						select.setInspectionStartDate((Timestamp) result[2]);
						select.setInspectionSheetId((Long) result[3]);
						select.setAssignDate((Timestamp) result[4]);
						select.setAssignById((Long) result[5]);
						select.setInspectionSheetName((String) result[6]);
						select.setVehicle_registration((String) result[7]);
						select.setVehicleGroupName((String) result[8]);
						select.setCompletionDetailsId((Long) result[9]);
						select.setInspectedById((Long) result[10]);
						select.setInspectedBy((String) result[11]);
						select.setInspectionStartDateStr(DateTimeUtility.getDateFromTimeStamp(select.getInspectionStartDate(), DateTimeUtility.DD_MM_YYYY));
						select.setAssignDateStr(DateTimeUtility.getDateFromTimeStamp(select.getAssignDate(), DateTimeUtility.DD_MM_YYYY));
						select.setPenaltyAmount(Utility.round((Double)result[12], 2));
		
					} else {
						return null;
					}
		
					return select;
				
					
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public String AsignSheetByVehicleType(Long inspectionId,Long vehicleType,String ispectionDate,short multiType,Integer VehicleLocation) throws Exception{
		VehicleInspectionSheetAssignment			assignment					=	null;
		VehicleDailyInspection 						dailyInspection 			= 	null;
		List<VehicleInspectionSheetToParameter> 	emptySheet					=   null;
		List<Object[]>      						vehicleList	 				= 	null;
		List<VehicleInspectionSheetAssignment>    	saveList					=   null;
		List<VehicleDailyInspection>        		saveDailyInspectionList 	=   null;
		CustomUserDetails							userDetails					=  	null;
		String 										status						=	""; 
		try {
			saveList        		= new ArrayList<>();
			saveDailyInspectionList = new ArrayList<>();
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			emptySheet = VehicleInspectionSheetToParameterRepository.getAllParametersInVehicleInspectionSheets(inspectionId, userDetails.getCompany_id());
			if(emptySheet.isEmpty()) {
				status="emptySheetAssignment";
				return status;
			}
			
			vehicleList=vehicleService.getVehicleIdByVehicleType(vehicleType,userDetails.getCompany_id(),VehicleLocation);
			if(vehicleList != null && !vehicleList.isEmpty()) {
				for(Object v : vehicleList) {
					VehicleInspctionSheetAsingmentDto	asingmentDto	=	asingmentService.getVehicleInspctionSheetAsingmentbyVid((int)v, userDetails.getCompany_id());
					if(asingmentDto == null) {
						assignment	= new VehicleInspectionSheetAssignment();

						assignment.setInspectionSheetId(inspectionId);
						assignment.setVid((int)v);
						assignment.setVehicleTypeId(vehicleType);
						assignment.setInspectionStartDate(DateTimeUtility.getTimeStamp(ispectionDate, DateTimeUtility.DD_MM_YYYY));
						assignment.setCompanyId(userDetails.getCompany_id());
						assignment.setAssignDate(DateTimeUtility.getCurrentTimeStamp());
						assignment.setAssignById(userDetails.getId());
						assignment.setBranchId(VehicleLocation);
						saveList.add(assignment);
						
						
						
						VehicleDailyInspection	validate	=			 VehicleDailyInspectionRepository.findByDate((int)v, DateTimeUtility.getCurrentDate(), 
								 										userDetails.getCompany_id());
						if(validate == null) {
							
							dailyInspection = new VehicleDailyInspection();
							dailyInspection.setInspectionSheetId(inspectionId);
							dailyInspection.setVid((int)v);
							dailyInspection.setVehicleTypeId(vehicleType);
							dailyInspection.setBranchId(VehicleLocation);
							String todaysDate = dateFormat.format(new Date());
							dailyInspection.setInspectionDate(dateFormat.parse(todaysDate));
							dailyInspection.setInspectionStatusId(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN);
							dailyInspection.setCompanyId(userDetails.getCompany_id());
							
							saveDailyInspectionList.add(dailyInspection);
						}
					}

				}
				if(!saveList.isEmpty()) {
					vehicleInspctionSheetAsingmentRepository.saveAll(saveList);
					status= "success";
					if(multiType == 1) 
					vehicleInspectionSheetService.updateVehicleType(vehicleType,inspectionId);
					vehicleInspectionSheetService.updateNoOfVehicleAssignment(inspectionId,saveList.size());
					prepareAssignmentDetails(inspectionId,vehicleType, ispectionDate,VehicleLocation);
				}else {
					status="AlreadyAssigned";
				}
				if(!saveDailyInspectionList.isEmpty())
					VehicleDailyInspectionRepository.saveAll(saveDailyInspectionList); 
			}else {
				status="NoVehicleFound";
			}
			return status;
		} catch (Exception e) {
			throw e;		
		}
	}
	@Override
	public void prepareSheeetToAssignMultiVehicleType(String vehicleType, Long inspectionId ,Integer VehicleLocation) throws Exception {
		try {
			String todaysDate = dateFormat2.format(new Date());
			if(vehicleType != null &&!vehicleType.trim().equals("")) {
				StringBuilder strBuilder = new StringBuilder();
			//	StringBuilder alreadyAssigned = new StringBuilder();
				String [] vehicleTypeArr = vehicleType.split(","); 
				for(String vehicleTypeId :vehicleTypeArr) {
					if(!vehicleTypeId.trim().equals("")) {
						String status = AsignSheetByVehicleType(inspectionId,Long.parseLong(vehicleTypeId),todaysDate,(short)2,VehicleLocation);
						if(status.equalsIgnoreCase("success")) {
							strBuilder.append(vehicleTypeId+",");
						}
//						else if(status.equalsIgnoreCase("AlreadyAssigned")) {
//							alreadyAssigned.append(vehicleTypeId+",");
//						}
					}
				}
				if(strBuilder.length() != 0)
				vehicleInspectionSheetService.updateMultiVehicleType(strBuilder.toString(),inspectionId);
			}


		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	@Transactional
	@Override
	public ValueObject deleteVehicleInspectionSheetByVehicleType(ValueObject valueInObject) throws Exception{
		CustomUserDetails							userDetails					=  	null;
		ValueObject 		valueObject= null; 
		try {
			valueObject= new ValueObject();
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			int branchId = valueInObject.getInt("branchId",0);
			int count = 0;
			if(branchId >0) {
				count = vehicleInspctionSheetAsingmentRepository.deleteVehicleAssignmentByVehicleTypeAndBranch(valueInObject.getLong("inspectionSheetId",0), valueInObject.getLong("vehicleTypeId",0), userDetails.getCompany_id(),branchId);
				VehicleDailyInspectionRepository.deleteVehicleAssignmentByVehicleTypeAndBranch(valueInObject.getLong("inspectionSheetId",0), valueInObject.getLong("vehicleTypeId",0), userDetails.getCompany_id(),branchId);
				vehicleTypeAssignmentRepository.deletedVehicleTypeAssignmentByTypeAndBranch(valueInObject.getLong("inspectionSheetId",0), valueInObject.getLong("vehicleTypeId",0), userDetails.getCompany_id(),userDetails.getId(),DateTimeUtility.getCurrentTimeStamp(),branchId);
			}else {
				count = vehicleInspctionSheetAsingmentRepository.deleteVehicleAssignmentByVehicleType(valueInObject.getLong("inspectionSheetId",0), valueInObject.getLong("vehicleTypeId",0), userDetails.getCompany_id());
			VehicleDailyInspectionRepository.deleteVehicleAssignmentByVehicleType(valueInObject.getLong("inspectionSheetId",0), valueInObject.getLong("vehicleTypeId",0), userDetails.getCompany_id());
				vehicleTypeAssignmentRepository.deletedVehicleTypeAssignmentByType(valueInObject.getLong("inspectionSheetId",0), valueInObject.getLong("vehicleTypeId",0), userDetails.getCompany_id(),userDetails.getId(),DateTimeUtility.getCurrentTimeStamp());
			}
			
			vehicleInspectionSheetService.substractNoOfVehicleAssignment(valueInObject.getLong("inspectionSheetId",0), count);
			
			VehicleInspectionSheet   inspectionSheet = vehicleInspectionSheetService.getVehicleInspectionSheetById(valueInObject.getLong("inspectionSheetId",0));
			
			updatevehicleTypeInInspection(valueInObject.getLong("inspectionSheetId",0),inspectionSheet.getVehicleTypeId(),valueInObject.getLong("vehicleTypeId",0),userDetails.getCompany_id());
			
//			vehicleTypeAssignmentRepository.deletedVehicleTypeAssignmentByType(valueInObject.getLong("inspectionSheetId",0), valueInObject.getLong("vehicleTypeId",0), userDetails.getCompany_id(),userDetails.getId(),DateTimeUtility.getCurrentTimeStamp());
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	
	@Transactional
	public void updatevehicleTypeInInspection(Long inspectionId,String vehicleTypeStr ,long vehicleTypeId ,int companyId) throws Exception {

		try {
			if(vehicleTypeStr != null) {

				String [] vehicleTypeArr = vehicleTypeStr.split(",");
				StringBuilder strBuilder = new StringBuilder();
				boolean validateIdOnce = false;
				for(String vehicleType:vehicleTypeArr) {
					if(!vehicleType.trim().equals("")) {
						if((Long.parseLong(vehicleType) != vehicleTypeId || validateIdOnce )) {
						strBuilder.append(vehicleType+",");
					}
						if((Long.parseLong(vehicleType) == vehicleTypeId && !validateIdOnce))
							validateIdOnce=true;
				}

				
				}
				vehicleInspectionSheetRepository.updateVehicleTypeId(strBuilder.toString(),
						inspectionId, companyId);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public void prepareAssignmentDetails(Long inspectionId,Long vehicleType,String ispectionDate ,Integer branchId) throws Exception{
		CustomUserDetails							userDetails					=  	null;
		VehicleTypeAssignmentDetails    			vehicleTypeAssignmentDetails = null;

		try {
			vehicleTypeAssignmentDetails = new VehicleTypeAssignmentDetails();
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicleTypeAssignmentDetails.setInspectionSheetId(inspectionId);
			vehicleTypeAssignmentDetails.setVehicleTypeId(vehicleType);
			vehicleTypeAssignmentDetails.setInspectionStartDate(DateTimeUtility.getTimeStamp(ispectionDate, DateTimeUtility.DD_MM_YYYY));
			vehicleTypeAssignmentDetails.setCompanyId(userDetails.getCompany_id());
			vehicleTypeAssignmentDetails.setAssignById(userDetails.getId());
			vehicleTypeAssignmentDetails.setAssignedOn(DateTimeUtility.getCurrentTimeStamp());
			vehicleTypeAssignmentDetails.setBranchId(branchId);
			
			vehicleTypeAssignmentRepository.save(vehicleTypeAssignmentDetails);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	

	
@Override	
public ValueObject getVehicleTypeInspectionSheetDto(HashMap<Long, VehicleType> vehicleTypeHM ,VehicleInspectionSheet inspectionSheet) throws Exception {
		VehicleInspectionSheetDto vehicleInspectionSheetDto = null;
		ValueObject ValueOutObject = new ValueObject(); 
		try {
			if(inspectionSheet != null) {
				vehicleInspectionSheetDto = new VehicleInspectionSheetDto();
				vehicleInspectionSheetDto.setVehicleInspectionSheetId(inspectionSheet.getVehicleInspectionSheetId());
				vehicleInspectionSheetDto.setInspectionSheetName(inspectionSheet.getInspectionSheetName());	
				vehicleInspectionSheetDto.setNoOfVehicleAsigned(inspectionSheet.getNoOfVehicleAsigned());
				
				if(inspectionSheet.getVehicleTypeId() != null) {
					String [] vehicleTypeArr =inspectionSheet.getVehicleTypeId().split(",");
					StringBuilder vehicleTypeName = new StringBuilder() ;
					
					for(String vehicleType :vehicleTypeArr) {
						if(!vehicleType.trim().equals("")) {
						VehicleType vehiclTypeObj = vehicleTypeHM.get(Long.parseLong(vehicleType+""));
						if(vehiclTypeObj != null) {
							vehicleTypeName.append(vehiclTypeObj.getVtype()+" , ");
							}
						vehicleInspectionSheetDto.setVehicleGroup(vehicleTypeName.toString());
						}
					}
				}
				ValueOutObject.put("inspectionSheet", vehicleInspectionSheetDto);
			}
			
			return ValueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleInspectionSheetDto = null;
		}
	}	
	@Override
	public void editVehicleSheetAssignment(Long oldvTypeId,Long newvTypeId,int vid ,Integer branchId ) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			VehicleInspectionSheetAssignment vehicleInspectionSheetAssignment = vehicleInspctionSheetAsingmentRepository.getVehicleAssignmentByVid(vid, oldvTypeId, userDetails.getCompany_id());
			
			int count= vehicleInspctionSheetAsingmentRepository.deleteVehicleAssignmentByVid(vid, userDetails.getCompany_id() ,oldvTypeId);
			VehicleDailyInspectionRepository.deleteVehicleAssignmentByVid(vid, oldvTypeId, userDetails.getCompany_id());
			if(vehicleInspectionSheetAssignment != null)
			vehicleInspectionSheetService.substractNoOfVehicleAssignment(vehicleInspectionSheetAssignment.getInspectionSheetId(), count);
			vehicleService.prepareForInspectionSheetAssignForNewVid(vid, newvTypeId,branchId);
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
}	

