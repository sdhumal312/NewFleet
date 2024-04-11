package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.VehicleInspectionStatus;
import org.fleetopgroup.persistence.dao.VehicleDailyInspectionRepository;
import org.fleetopgroup.persistence.dao.VehicleInspctionSheetAsingmentRepository;
import org.fleetopgroup.persistence.dao.VehicleInspectionCompletionDetailsRepository;
import org.fleetopgroup.persistence.dao.VehicleInspectionSheetRepository;
import org.fleetopgroup.persistence.dao.VehicleInspectionSheetToParameterRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleInspctionSheetAsingmentDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionCompletionDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionSheetAssignmentDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionSheetToParameterDto;
import org.fleetopgroup.persistence.model.VehicleDailyInspection;
import org.fleetopgroup.persistence.model.VehicleInspectionCompletionDetails;
import org.fleetopgroup.persistence.model.VehicleInspectionSheet;
import org.fleetopgroup.persistence.model.VehicleInspectionSheetAssignment;
import org.fleetopgroup.persistence.model.VehicleInspectionSheetToParameter;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDailyInspectionService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspctionSheetAsingmentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionSheetService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionSheetToParameterService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleInspectionSheetService implements IVehicleInspectionSheetService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private @Autowired	IVehicleInspectionSheetToParameterService		sheetToParameterService;
	private @Autowired	IVehicleDailyInspectionService					VehicleDailyInspectionService;
	private @Autowired	VehicleInspectionSheetRepository				vehicleInspectionSheetRepository;
	private @Autowired	VehicleInspectionSheetToParameterRepository		VehicleInspectionSheetToParameterRepository;
	private @Autowired	VehicleInspctionSheetAsingmentRepository		VehicleInspctionSheetAsingmentRepository;
	private @Autowired	VehicleDailyInspectionRepository				VehicleDailyInspectionRepository;
	private @Autowired	VehicleInspectionCompletionDetailsRepository	vehicleInspectionCompletionDetailsDao;
	private	@Autowired 	IVehicleInspctionSheetAsingmentService			asingmentService;
	
	@PersistenceContext	public EntityManager entityManager;
	
	SimpleDateFormat 		dateFormat 			= new SimpleDateFormat("yyyy-MM-dd");
	
	SimpleDateFormat 		dateFormat2 		= new SimpleDateFormat("dd-MM-yyyy");
	
	SimpleDateFormat 		dateFormat3 		= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	private static final int PAGE_SIZE = 10;

	@Override
	public VehicleInspectionSheet saveVehicleInspectionSheet(VehicleInspectionSheet vehicleInspectionSheet) throws Exception {

		return vehicleInspectionSheetRepository.save(vehicleInspectionSheet);
	}
	
	@Override
	public List<VehicleInspectionSheet> validateVehicleInspectionSheetName(String name, Integer companyId)
			throws Exception {
		
		return vehicleInspectionSheetRepository.validateVehicleInspectionSheetName(name, companyId);
	}
	
	@Override
	public List<VehicleInspectionSheet> getListVehicleInspectionSheet(Integer companyId) throws Exception {
		
		return vehicleInspectionSheetRepository.getListVehicleInspectionSheet(companyId);
	}
	
	@Override
	public VehicleInspectionSheet getVehicleInspectionSheetById(Long vehicleInspectionSheetId) throws Exception {
		VehicleInspectionSheet vehicleInspectionSheet =null;
		try {
			vehicleInspectionSheet =  vehicleInspectionSheetRepository.getVehicleInspectionSheetById(vehicleInspectionSheetId);
		
			
			return vehicleInspectionSheet;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleInspectionSheetFindById(ValueObject valueObject) throws Exception {
		List<VehicleInspectionSheetToParameterDto>		sheetToParameterDtoList	= null;
		VehicleInspectionSheet							inspectionSheetDetails	= null;
		CustomUserDetails								userDetails				= null;
		try {
			userDetails=(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			inspectionSheetDetails		=	vehicleInspectionSheetRepository.findById(valueObject.getLong("vehicleInspectionSheetId")).get();
			sheetToParameterDtoList		=	sheetToParameterService.getVehicleInspectionSheetToParameterList(valueObject.getLong("vehicleInspectionSheetId"), userDetails.getCompany_id());
			
			valueObject.put("inspectionSheetDetails", inspectionSheetDetails);
			valueObject.put("sheetToParameterDtoList", sheetToParameterDtoList);

			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleInspectionSheet> getListVehicleInspectionSheet(String term, Integer companyId) throws Exception {
		List<VehicleInspectionSheet>	Dtos		= null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT VI.vehicleInspectionSheetId, VI.inspectionSheetName, VI.vehicleGroupId "
				+ " FROM VehicleInspectionSheet AS VI "
				+ " where  lower(VI.inspectionSheetName) Like ('%" + term + "%') AND VI.companyId = " + companyId + " AND VI.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();
		
		
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleInspectionSheet>();
			VehicleInspectionSheet inspectionSheet = null;
			for (Object[] result : results) {
				inspectionSheet = new VehicleInspectionSheet();
				
				inspectionSheet.setVehicleInspectionSheetId((Long) result[0]);
				inspectionSheet.setInspectionSheetName((String) result[1]);
				inspectionSheet.setVehicleGroupId((String) result[2]);

				Dtos.add(inspectionSheet);
			}
		}
		}
		return Dtos;
	}
	
	@Override
	@Transactional
	public void updateNoOfVehicleAssignment(Long vehicleInspectionSheetId ,int count ) throws Exception {
		entityManager.createNativeQuery(
				"UPDATE VehicleInspectionSheet SET noOfVehicleAsigned = noOfVehicleAsigned + "+count+" "
						+ " where vehicleInspectionSheetId =" +vehicleInspectionSheetId+ " ")
		.executeUpdate();
	}
	
	@Override
	@Transactional
	public void substractNoOfVehicleAssignment(Long vehicleInspectionSheetId,int count) throws Exception {
		entityManager.createNativeQuery(
				"UPDATE VehicleInspectionSheet SET noOfVehicleAsigned = noOfVehicleAsigned - "+count+" "
						+ " where vehicleInspectionSheetId =" +vehicleInspectionSheetId+ " ")
		.executeUpdate();
	}
	
	@Override
	@Transactional
	public void deleteVehicleInspectionSheet(Long vehicleInspectionSheetId, Integer companyId) throws Exception {
		entityManager.createNativeQuery(
				"UPDATE VehicleInspectionSheet SET markForDelete =  1 "
						+ " where vehicleInspectionSheetId =" +vehicleInspectionSheetId+ " ")
		.executeUpdate();
	}

	@Override
	public ValueObject getVehicleInspectionParameter(Integer companyId,Long inspectionSheetId) throws Exception {
		ValueObject				valueOutObject				= null;
		try {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT VS.inspectionSheetId,VS.inspectionStatusId, V.vehicle_registration  FROM VehicleInspectionSheetAssignment AS VS "
				+ " LEFT JOIN Vehicle AS V ON V.vid = VS.vid WHERE  VS.companyId ="+companyId+" AND VS.inspectionSheetId= "+ inspectionSheetId +" AND VS.markForDelete = 0",				
				Object[].class);
		List<Object[]> results = queryt.getResultList();
		
		List<VehicleInspectionSheetAssignmentDto>	Dtos		= null;
		
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleInspectionSheetAssignmentDto>();
			VehicleInspectionSheetAssignmentDto inspectionSheet = null;
			for (Object[] result : results) {
				
				inspectionSheet = new VehicleInspectionSheetAssignmentDto();	
				
				inspectionSheet.setInspectionSheetId(((Long) result[0]));
				inspectionSheet.setInspectionStatusId((Short) result[1]);				
				inspectionSheet.setVehicle_registration((String) result[2]);
				Dtos.add(inspectionSheet);
			}
			
		}
		valueOutObject	= new ValueObject();
		valueOutObject.put("InspectionInformation", Dtos); 
		return valueOutObject;
		}catch(Exception e){
			
		}
		return valueOutObject;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public ValueObject updateInspectionSheetDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails				= null;
		ArrayList<ValueObject> 					dataArray 				= null;		
		ArrayList<ValueObject> 					newDataArray 			= null;		
		VehicleInspectionSheetToParameter		sheetToParameter		= null;
		int										frequency				= 1;
		List<VehicleInspectionSheetToParameter> frequencyAsOne			= null;
		List<VehicleInspectionSheetAssignment>	listOfVehicles			= null;
		VehicleDailyInspection 					dailyInspection 		= null;
		List<VehicleInspectionSheetToParameter> emptySheet				= null;
		try {
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dataArray		= (ArrayList<ValueObject>) valueObject.get("Parameter");
			if(dataArray != null && !dataArray.isEmpty()) {
				for (ValueObject object : dataArray) {
					frequencyAsOne = VehicleInspectionSheetToParameterRepository.getAllParametersWithFrequencyAsOne(object.getLong("parameterSheetId"), frequency, userDetails.getCompany_id());
					if(frequencyAsOne.isEmpty()) {
						if(object.getInt("frequency") == frequency) {
							listOfVehicles = VehicleInspctionSheetAsingmentRepository.getVehiclesAsPerSheetId(object.getLong("parameterSheetId"), userDetails.getCompany_id());
							if(listOfVehicles != null && !listOfVehicles.isEmpty()) {
								for(VehicleInspectionSheetAssignment vehicles : listOfVehicles) {
									VehicleDailyInspection	 validatedailyInsection = VehicleDailyInspectionRepository.findByDate(vehicles.getVid(), DateTimeUtility.getCurrentDate(), userDetails.getCompany_id());
									if(validatedailyInsection == null) {
									dailyInspection = new VehicleDailyInspection();
									dailyInspection.setInspectionSheetId(object.getLong("parameterSheetId"));
									dailyInspection.setVid(vehicles.getVid());
									String todaysDate = dateFormat.format(new Date());
									dailyInspection.setInspectionDate(dateFormat.parse(todaysDate));
									dailyInspection.setInspectionStatusId(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN);
									dailyInspection.setCompanyId(userDetails.getCompany_id());
									if(vehicles.getVehicleTypeId() != null)
									dailyInspection.setVehicleTypeId(vehicles.getVehicleTypeId());
									if(vehicles.getBranchId() != null)
									dailyInspection.setBranchId(vehicles.getBranchId());
									VehicleDailyInspectionService.saveVehicleDailyInspection(dailyInspection);
									}
								}
							}
						}
					}
					
					sheetToParameterService.updateInspectionSheet(object.get("frequency"),object.get("inspectionParameter"),
							object.get("requiredTypeId"),object.get("inputPhotoTypeId"),object.get("textGroupTypeId"),object.get("inspectionSheetParameterId"));
					
					sheetToParameterService.updateLastModifiedFromVehicleInspectionSheet(object.get("parameterSheetId"),DateTimeUtility.getCurrentTimeStamp(),userDetails.getId());
					
					emptySheet = VehicleInspectionSheetToParameterRepository.getAllParametersInVehicleInspectionSheets(object.getLong("parameterSheetId"), userDetails.getCompany_id());
					if(emptySheet.isEmpty()) {
						String todaysDate = dateFormat.format(new Date());
						
						listOfVehicles = VehicleInspctionSheetAsingmentRepository.getVehiclesAsPerSheetId(object.getLong("parameterSheetId"), userDetails.getCompany_id());
						if(listOfVehicles != null) {
							for(VehicleInspectionSheetAssignment vehicles : listOfVehicles) {
								VehicleDailyInspectionRepository.deleteInspectionByVehicle(vehicles.getVid(), dateFormat.parse(todaysDate), userDetails.getCompany_id());
							}
						}
					}
				}
			}
			newDataArray		= (ArrayList<ValueObject>) valueObject.get("newParameter");
			if(newDataArray != null && !newDataArray.isEmpty()) {
				for (ValueObject object : newDataArray) {
					if(object != null) {
					sheetToParameter	= new VehicleInspectionSheetToParameter();
					sheetToParameter.setInspectionParameterId(object.getLong("inspectionParameterID"));
					sheetToParameter.setFrequency(object.getInt("newFrequency"));
					if(object.get("newRequiredTypeId")!= null) {
					sheetToParameter.setMandatory(object.getBoolean("newRequiredTypeId"));
					}
					sheetToParameter.setPhotoRequired(object.getBoolean("newInputPhotoGroupID"));
					sheetToParameter.setTextRequired(object.getBoolean("newTextGroupID"));
					sheetToParameter.setInspectionSheetId(object.getLong("parameterSheetId"));
					sheetToParameter.setCompanyId(userDetails.getCompany_id());
					
					frequencyAsOne = VehicleInspectionSheetToParameterRepository.getAllParametersWithFrequencyAsOne(object.getLong("parameterSheetId"), frequency, userDetails.getCompany_id());
					if(frequencyAsOne.isEmpty()) {
						if(object.getInt("newFrequency") == frequency) {
							listOfVehicles = VehicleInspctionSheetAsingmentRepository.getVehiclesAsPerSheetId(object.getLong("parameterSheetId"), userDetails.getCompany_id());
							if(listOfVehicles != null && !listOfVehicles.isEmpty()) {
								for(VehicleInspectionSheetAssignment vehicles : listOfVehicles) {
									VehicleDailyInspection	 validatedailyInsection = VehicleDailyInspectionRepository.findByDate(vehicles.getVid(), DateTimeUtility.getCurrentDate(), userDetails.getCompany_id());
									if(validatedailyInsection == null) {
										dailyInspection = new VehicleDailyInspection();
										dailyInspection.setInspectionSheetId(object.getLong("parameterSheetId"));
										dailyInspection.setVid(vehicles.getVid());
										String todaysDate = dateFormat.format(new Date());
										dailyInspection.setInspectionDate(dateFormat.parse(todaysDate));
										dailyInspection.setInspectionStatusId(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN);
										dailyInspection.setCompanyId(userDetails.getCompany_id());
										if(vehicles.getVehicleTypeId() != null)
											dailyInspection.setVehicleTypeId(vehicles.getVehicleTypeId());
										if(vehicles.getBranchId() != null)
											dailyInspection.setBranchId(vehicles.getBranchId());
										VehicleDailyInspectionService.saveVehicleDailyInspection(dailyInspection);
									}
								}
							}
						}
					}
					
					sheetToParameterService.saveVehicleInspectionSheetToParameter(sheetToParameter);
					
					}
				}
			}

			return valueObject;
		} 
		catch (Exception e) {
			LOGGER.error("Error", e);
			throw e;
		}finally {
			userDetails			= null;
		}
	}
	
	@Override
	public Page<VehicleInspectionCompletionDetails> getVehicleInspectionPage(Integer vid, Integer pageNumber, Integer companyId) {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return vehicleInspectionCompletionDetailsDao.findByVid(vid, companyId, pageable);
	}
	
	@Override
	public List<VehicleInspectionCompletionDetailsDto> getVehicleInspectionPageList(Integer vid, Integer pageNumber,
			Integer companyId) {

		TypedQuery<Object[]> query = null;
			
		query = entityManager
					.createQuery("SELECT F.completionDetailsId, F.inspectionDate, F.completionDateTime, F.inspectedById, U.firstName, U.lastName "
							+ " FROM VehicleInspectionCompletionDetails as F"
							+ " INNER JOIN User U ON U.id = F.inspectedById "
							+ " where F.vid=" + vid + " AND F.companyId = " + companyId
							+ " AND F.markForDelete = 0 ORDER BY F.completionDetailsId DESC", Object[].class);
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		List<Object[]> results = query.getResultList();

		List<VehicleInspectionCompletionDetailsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleInspectionCompletionDetailsDto>();
			VehicleInspectionCompletionDetailsDto fuel = null;
			for (Object[] result : results) {
				fuel = new VehicleInspectionCompletionDetailsDto();
				
				fuel.setCompletionDetailsId((Long) result[0]);
				fuel.setInspectionDateStr(dateFormat2.format(result[1]));
				fuel.setCompletionDateTimeStr(dateFormat3.format(result[2]));
				fuel.setInspectedById((Long) result[3]);
				fuel.setInspectedBy((String) result[4]);
				fuel.setVid(vid);
				
				Dtos.add(fuel);
			}
		}
		return Dtos;

	}
	
	@Override
	@Transactional
	public void updateVehicleType(Long vehicleTYpeId,Long vehicleInspectionSheetId) throws Exception {
		entityManager.createNativeQuery("UPDATE VehicleInspectionSheet SET vehicleTypeId = CONCAT(IFNUll(vehicleTypeId,''),',"+vehicleTYpeId+"')   where vehicleInspectionSheetId ="+vehicleInspectionSheetId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateMultiVehicleType(String vehicleTYpeId,Long vehicleInspectionSheetId) throws Exception {
		entityManager.createNativeQuery("UPDATE VehicleInspectionSheet SET vehicleTypeId = '"+vehicleTYpeId+"'  where vehicleInspectionSheetId ="+vehicleInspectionSheetId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void addInspectionSheetToVehicle(VehicleInspctionSheetAsingmentDto vehicleInspectionSheetAssignment) throws Exception {
		
		VehicleInspectionSheetAssignment		assignment			=	null;
		VehicleDailyInspection 					dailyInspection 	= 	null;
		
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			assignment	= new VehicleInspectionSheetAssignment();
			
			assignment.setInspectionSheetId(vehicleInspectionSheetAssignment.getInspectionSheetId());
			assignment.setVid(vehicleInspectionSheetAssignment.getVid());
			assignment.setInspectionStartDate(DateTimeUtility.getTimeStamp(vehicleInspectionSheetAssignment.getInspectionStartDateStr(), DateTimeUtility.DD_MM_YYYY));
			assignment.setCompanyId(userDetails.getCompany_id());
			assignment.setAssignDate(DateTimeUtility.getCurrentTimeStamp());
			assignment.setAssignById(userDetails.getId());
			assignment.setVehicleTypeId(vehicleInspectionSheetAssignment.getVehicleTypeId());
			assignment.setBranchId(vehicleInspectionSheetAssignment.getBranchId());
			
			asingmentService.saveVehicleAssignment(assignment);
			
			dailyInspection = new VehicleDailyInspection();
			dailyInspection.setInspectionSheetId(vehicleInspectionSheetAssignment.getInspectionSheetId());
			dailyInspection.setVid(vehicleInspectionSheetAssignment.getVid());
			String todaysDate = dateFormat.format(new Date());
			dailyInspection.setInspectionDate(dateFormat.parse(todaysDate));
			dailyInspection.setInspectionStatusId(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN);
			dailyInspection.setCompanyId(userDetails.getCompany_id());
			dailyInspection.setVehicleTypeId(vehicleInspectionSheetAssignment.getVehicleTypeId());
			dailyInspection.setBranchId(vehicleInspectionSheetAssignment.getBranchId());
			VehicleDailyInspectionService.saveVehicleDailyInspection(dailyInspection);
			
			updateNoOfVehicleAssignment(assignment.getInspectionSheetId(),1);
		} catch (Exception e) {
			throw e;
		}
		
	}
	


}
