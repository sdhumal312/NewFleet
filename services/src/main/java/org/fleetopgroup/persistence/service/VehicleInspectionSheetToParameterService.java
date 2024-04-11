package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehicleInspectionSheetToParameterRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleInspectionSheetToParameterDto;
import org.fleetopgroup.persistence.model.VehicleInspectionSheetToParameter;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionSheetToParameterService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class VehicleInspectionSheetToParameterService implements IVehicleInspectionSheetToParameterService {
	
	@PersistenceContext	public EntityManager entityManager;


	private @Autowired	VehicleInspectionSheetToParameterRepository		vehicleInspectionSheetToParameterRepository;
	
	SimpleDateFormat 		dateFormat 			= new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void saveVehicleInspectionSheetToParameter(VehicleInspectionSheetToParameter inspectionSheetToParameter)
			throws Exception {
		
			vehicleInspectionSheetToParameterRepository.save(inspectionSheetToParameter);
	}
	
	@Override
	public List<VehicleInspectionSheetToParameterDto> getVehicleInspectionSheetToParameterList(
			Long inspectionParameterId, Integer companyId) throws Exception {
		
		try {
				TypedQuery<Object[]> query = entityManager.createQuery(
						"SELECT VIS.inspectionSheetToParameterId, VIS.inspectionSheetId, VIS.inspectionParameterId, VIS.frequency, VIS.isMandatory, VIS.isPhotoRequired,"
						+ " VIS.isTextRequired, IP.parameterName,VG.gid, VG.vGroup  "
						+ " FROM VehicleInspectionSheetToParameter AS VIS "
						+ " LEFT JOIN VehicleInspectionSheet VTS ON VTS.vehicleInspectionSheetId = VIS.inspectionSheetId " 
						+ " LEFT JOIN VehicleGroup VG ON VG.gid = VTS.vehicleGroupId "
						+ " INNER JOIN InspectionParameter IP ON IP.inspectionParameterId = VIS.inspectionParameterId"
						+ " where VIS.inspectionSheetId ="+inspectionParameterId+" AND VIS.companyId = "+companyId+" AND VIS.markForDelete = 0",
						Object[].class);
				
				List<Object[]> results = query.getResultList();
				
				List<VehicleInspectionSheetToParameterDto> dtos = null;
				if (results != null && !results.isEmpty()) {
					dtos = new ArrayList<VehicleInspectionSheetToParameterDto>();
					VehicleInspectionSheetToParameterDto parameterDto = null;
					for (Object[] result : results) {
						parameterDto = new VehicleInspectionSheetToParameterDto();
						parameterDto.setInspectionSheetToParameterId((Long) result[0]);
						parameterDto.setInspectionSheetId((Long) result[1]);
						if(result[2] != null) {
						parameterDto.setInspectionParameterId((Long) result[2]);
						}
						parameterDto.setFrequency((int) result[3]);
						parameterDto.setMandatory((boolean) result[4]);
						parameterDto.setPhotoRequired((boolean) result[5]);
						parameterDto.setTextRequired((boolean) result[6]);
						parameterDto.setParameterName((String) result[7]);
						if(result[8]!= null) {
							parameterDto.setGid((long) result[8]);
						}
						if(result[9]!= null) {
						parameterDto.setvGroup((String) result[9]);
						}
						
						if(parameterDto.isMandatory()) {
							parameterDto.setMandatoryText("YES");
						}else {
							parameterDto.setMandatoryText("NO");
						}
						
						if(parameterDto.isPhotoRequired()) {
							parameterDto.setPhotoRequiredText("YES");
						}else {
							parameterDto.setPhotoRequiredText("NO");
						}
						
						if(parameterDto.isTextRequired()) {
							parameterDto.setTextRequiredText("YES");
						}else {
							parameterDto.setTextRequiredText("NO");
						}
						
						dtos.add(parameterDto);
					}
				}
				return dtos;
					
				
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleInspectionSheetToParameterDto> getVehicleInspectionSheetToParameterListToINspect(
			Long assignmentId, Integer vid, Integer companyId, String date) throws Exception {
		
		try {
				TypedQuery<Object[]> query = entityManager.createQuery(
						"SELECT VIS.inspectionSheetToParameterId, VIS.inspectionSheetId, VIS.inspectionParameterId, VIS.frequency, VIS.isMandatory, VIS.isPhotoRequired,"
						+ " VIS.isTextRequired, IP.parameterName, VIA.inspectionStartDate, IP.parameterPhotoId,VIA.assignDate  "
						+ " FROM VehicleInspectionSheetToParameter AS VIS "
						+ " INNER JOIN VehicleInspectionSheetAssignment VIA ON VIA.inspectionSheetId = VIS.inspectionSheetId "
						+ " INNER JOIN InspectionParameter IP ON IP.inspectionParameterId = VIS.inspectionParameterId"
						+ " where VIA.vehicleInspctionSheetAsingmentId ="+assignmentId+" AND VIA.vid = "+vid+" AND VIS.companyId = "+companyId+" AND VIS.markForDelete = 0",
						Object[].class);
				
				List<Object[]> results = query.getResultList();
				
				List<VehicleInspectionSheetToParameterDto> dtos = null;
				if (results != null && !results.isEmpty()) {
					dtos = new ArrayList<>();
					VehicleInspectionSheetToParameterDto parameterDto = null;
					for (Object[] result : results) {
						parameterDto = new VehicleInspectionSheetToParameterDto();
						parameterDto.setInspectionSheetToParameterId((Long) result[0]);
						parameterDto.setInspectionSheetId((Long) result[1]);
						parameterDto.setInspectionParameterId((Long) result[2]);
						parameterDto.setFrequency((int) result[3]);
						parameterDto.setMandatory((boolean) result[4]);
						parameterDto.setPhotoRequired((boolean) result[5]);
						parameterDto.setTextRequired((boolean) result[6]);
						parameterDto.setParameterName((String) result[7]);
						parameterDto.setInspectionStartDate((Timestamp) result[8]);
						if(result[9] != null) {
						parameterDto.setParameterPhotoId((long) result[9]);
						} else {
						parameterDto.setParameterPhotoId(0);	
						}
						parameterDto.setInspectionAssignDate((Timestamp)result[10]);
						if(parameterDto.isMandatory()) {
							parameterDto.setMandatoryText("YES");
						}else {
							parameterDto.setMandatoryText("NO");
						}
						
						if(parameterDto.isPhotoRequired()) {
							parameterDto.setPhotoRequiredText("YES");
						}else {
							parameterDto.setPhotoRequiredText("NO");
						}
						
						if(parameterDto.isTextRequired()) {
							parameterDto.setTextRequiredText("YES");
						}else {
							parameterDto.setTextRequiredText("NO");
						}
						
						long diff =  DateTimeUtility.getExactDayDiffBetweenTwoDates(parameterDto.getInspectionStartDate(), DateTimeUtility.getTimeStamp(date, DateTimeUtility.YYYY_MM_DD));
						if(diff != 0) {
							if(diff == parameterDto.getFrequency() || parameterDto.getFrequency() == 1 || dateFormat.format(parameterDto.getInspectionAssignDate()).equals(date.trim()) || (diff > parameterDto.getFrequency() &&  (diff % parameterDto.getFrequency()) == 0)) {
								dtos.add(parameterDto);
							}
						}else {
							dtos.add(parameterDto);
						}
					}
				}
				return dtos;
					
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void updateInspectionSheet(Object frequency, Object inspectionParameter, Object requiredTypeId, 
			Object inputPhotoTypeId, Object textGroupTypeId, Object inspectionSheetParameterId)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		try {
			
			entityManager.createNativeQuery(
					"UPDATE VehicleInspectionSheetToParameter SET frequency ="+frequency+" , "
					+ " inspectionParameterId = "+inspectionParameter+",isMandatory = "+requiredTypeId+", "
					+ " isPhotoRequired = "+inputPhotoTypeId+",isTextRequired = "+textGroupTypeId+" "
					+ " where inspectionSheetToParameterId = "+inspectionSheetParameterId+"  "
					+ " AND markForDelete =0 AND companyId = "+userDetails.getCompany_id()+" ")
			.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}

	}
	
	@Transactional
	public void updateLastModifiedFromVehicleInspectionSheet(Object parameterSheetId,Timestamp currentDate,long userId)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		try {
			
			entityManager.createNativeQuery(
					"UPDATE VehicleInspectionSheet SET lastModifiedOn = '"+currentDate+"' , "
					+ " lastModifiedById = "+userId+" "
					+ " where vehicleInspectionSheetId = "+parameterSheetId+"  "
					+ " AND markForDelete =0 AND companyId = "+userDetails.getCompany_id()+" ")
			.executeUpdate();
			
			
			
			/*entityManager.createNativeQuery(
					" UPDATE VehicleInspectionSheet SET lastModifiedOn = " + currentDate + ", lastModifiedById = " + userId + " "
					+ " where vehicleInspectionSheetId = " + parameterSheetId + " AND companyId = "+userDetails.getCompany_id()+" ")
			.executeUpdate();*/
			
		} catch (Exception e) {
			throw e;
		}

	}
	
	@Transactional
	public void RemoveInspectionParameter(long inspectionSheetParameterId,Integer companyId) throws Exception {
		
		 vehicleInspectionSheetToParameterRepository.deleteInspectionParameter(inspectionSheetParameterId,companyId);
	}
	@Override
	public List<VehicleInspectionSheetToParameter> getVehicleInspectionSheetsToParameterListByParameterId(Long inspectionSheetParameterId,Integer company_id) throws Exception {
		return vehicleInspectionSheetToParameterRepository.getInspectionParametersheetByInspectionParameterId(inspectionSheetParameterId, company_id);
	}
	
	@Override
	public List<VehicleInspectionSheetToParameter> checkParametersInVehicleInspectionSheets(long inspectionSheetParameterId,Integer company_id) throws Exception {
		return vehicleInspectionSheetToParameterRepository.getAllParametersInVehicleInspectionSheets(inspectionSheetParameterId, company_id);
	}
	
}
