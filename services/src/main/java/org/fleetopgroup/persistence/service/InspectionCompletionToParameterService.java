package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.InspectionCompletionToParameterRepository;
import org.fleetopgroup.persistence.dao.InspectionFailedPenaltyRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InspectionCompletionToParameterDto;
import org.fleetopgroup.persistence.model.InspectionCompletionToParameter;
import org.fleetopgroup.persistence.model.InspectionFailedParameterPenalty;
import org.fleetopgroup.persistence.model.InspectionParameter;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInspectionCompletionToParameterService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InspectionCompletionToParameterService implements IInspectionCompletionToParameterService {
	
	@PersistenceContext	public EntityManager entityManager;

	
	@Autowired private InspectionCompletionToParameterRepository		inspectionCompletionToParameterRepository;
	
	@Autowired private InspectionFailedPenaltyRepository                   inspectionFailedPenaltyRepository; 
	
	@Autowired private ICompanyConfigurationService 							companyConfigurationService;

	@Override
	public void saveInspectionCompletionToParameter(InspectionCompletionToParameter completionToParameter)
			throws Exception {
		
		inspectionCompletionToParameterRepository.save(completionToParameter);
	}

	@Override
	@Transactional
	public void updateDocumentIdToParameter(Long parameterId, Long documentId) throws Exception {
		
		inspectionCompletionToParameterRepository.updateDocumentIdToParameter(parameterId, documentId);
	}
	
	@Override
	@Transactional
	public void updateDocumentToParameter(Long parameterId) throws Exception {
		
		inspectionCompletionToParameterRepository.updateDocumentToParameter(parameterId);
	}
	
	
	@Override
	public List<InspectionCompletionToParameter> getInspectionCompletionToParameterByDateAndVid(Integer vid,
			Timestamp date) throws Exception {
		
		return inspectionCompletionToParameterRepository.getInspectionCompletionToParameterByDateAndVid(vid, date);
	}
	
	@Override
	public List<InspectionCompletionToParameterDto> getInspectionCompletionToParameterDetails(Integer vid,
			Long completionDetailsId) throws Exception {
		CustomUserDetails		                        userDetails						= null;
		HashMap<String, Object> 						configuration	 				= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
		
				TypedQuery<Object[]> queryt = null;
					queryt = entityManager.createQuery(" SELECT ICP.completionToParameterId, ICP.completionDetailsId, ICP.inspectionSheetId, ICP.inspectionParameterId,"
							+ " ICP.isInspectionSuccess, ICP.isInspectionDone, ICP.completionDateTime, ICP.isDocumentUploaded, ICP.documentId, ICP.description, ICP.inspectedById,"
							+ " IP.parameterName "
							+ " FROM InspectionCompletionToParameter ICP "
							+ " INNER JOIN VehicleInspectionSheetToParameter VISP on VISP.inspectionSheetToParameterId = ICP.inspectionParameterId"
							+ " INNER JOIN InspectionParameter IP ON IP.inspectionParameterId = VISP.inspectionParameterId"
							+ " WHERE ICP.vid = "+vid+" AND ICP.completionDetailsId = "+completionDetailsId+" AND ICP.markForDelete = 0",
							Object[].class);
				List<Object[]> results = queryt.getResultList();
		
				List<InspectionCompletionToParameterDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					
					Dtos = new ArrayList<InspectionCompletionToParameterDto>();
					InspectionCompletionToParameterDto parameterDto = null;
					for (Object[] result : results) {
						parameterDto = new InspectionCompletionToParameterDto();
						
						parameterDto.setCompletionToParameterId((Long) result[0]);
						parameterDto.setCompletionDetailsId((Long) result[1]);
						parameterDto.setInspectionSheetId((Long) result[2]);
						parameterDto.setInspectionParameterId((Long) result[3]);
						parameterDto.setIsInspectionSuccess((short) result[4]);
						parameterDto.setInspectionDone((boolean) result[5]);
						parameterDto.setCompletionDateTime((Timestamp) result[6]);
						parameterDto.setDocumentUploaded((boolean) result[7]);
						parameterDto.setDocumentId((Long) result[8]);
						parameterDto.setDescription((String) result[9]);
						parameterDto.setInspectedById((Long) result[10]);
						parameterDto.setParameterName((String) result[11]);
						
						if(parameterDto.getIsInspectionSuccess() == 1) {
							parameterDto.setInspectionSucessStr("PASS");
						}else if(parameterDto.getIsInspectionSuccess() == 2) {
							parameterDto.setInspectionSucessStr("FAILED");
							if((boolean)configuration.getOrDefault("failedParameterPenalty", false)) {
								parameterDto.setPenaltyAmount((Double)(configuration.getOrDefault("penaltyAmount", 0)));
							}
						}else {
							parameterDto.setInspectionSucessStr("NOT INSPECTED");
						}
		
						Dtos.add(parameterDto);
					}
				}
				return Dtos;
				
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<InspectionCompletionToParameterDto> getVehicleInspectionReportDetails(InspectionCompletionToParameterDto	completionToParameterDto, String query, Map<Long, InspectionParameter> parameterHM) throws Exception {
				TypedQuery<Object[]> queryt = null;
				queryt = entityManager.createQuery(" SELECT ICP.completionToParameterId, ICP.completionDetailsId, ICP.inspectionSheetId, ICP.inspectionParameterId,"
						+ " ICP.isInspectionSuccess, ICP.isInspectionDone, ICP.completionDateTime, ICP.isDocumentUploaded, ICP.documentId, ICP.description, ICP.inspectedById,"
						+ " VCD.inspectionDate, VP.frequency, U.firstName, U.lastName, VP.inspectionParameterId "
						+ " FROM InspectionCompletionToParameter ICP "
						+ " INNER JOIN VehicleInspectionCompletionDetails VCD ON VCD.completionDetailsId = ICP.completionDetailsId"
						+ " INNER JOIN VehicleInspectionSheetToParameter VP ON VP.inspectionSheetToParameterId = ICP.inspectionParameterId"
						+ " LEFT JOIN User U ON U.id = ICP.inspectedById"
						+ " WHERE ICP.vid = "+completionToParameterDto.getVid()+" AND VCD.inspectionDate between '"+completionToParameterDto.getFromDate()+"' "
						+ " AND '"+completionToParameterDto.getToDate()+"' "+query+""
						+ " AND ICP.markForDelete = 0 ORDER BY VCD.inspectionDate ASC",
						Object[].class);
			List<Object[]> results = queryt.getResultList();
		
			List<InspectionCompletionToParameterDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				
				Dtos = new ArrayList<InspectionCompletionToParameterDto>();
				InspectionCompletionToParameterDto parameterDto = null;
				for (Object[] result : results) {
					parameterDto = new InspectionCompletionToParameterDto();
					
					parameterDto.setCompletionToParameterId((Long) result[0]);
					parameterDto.setCompletionDetailsId((Long) result[1]);
					parameterDto.setInspectionSheetId((Long) result[2]);
					parameterDto.setInspectionParameterId((Long) result[3]);
					parameterDto.setIsInspectionSuccess((short) result[4]);
					parameterDto.setInspectionDone((boolean) result[5]);
					parameterDto.setCompletionDateTime((Timestamp) result[6]);
					parameterDto.setDocumentUploaded((boolean) result[7]);
					parameterDto.setDocumentId((Long) result[8]);
					parameterDto.setDescription((String) result[9]);
					parameterDto.setInspectedById((Long) result[10]);
					parameterDto.setInspectionDate((Timestamp) result[11]);
					if(result[12] != null)
						parameterDto.setFrequency((int) result[12]);
					if(result[13] != null)
						parameterDto.setInspectedBy((String) result[13]+"_ "+(String) result[14]);
					
					parameterDto.setInspectionParameterId((Long) result[15]);
					
					if(parameterHM != null && !parameterHM.isEmpty()) {
						if(parameterHM.get(parameterDto.getInspectionParameterId()) != null) {
							parameterDto.setParameterName(parameterHM.get(parameterDto.getInspectionParameterId()).getParameterName());
						}
					}
					
					if(parameterDto.getIsInspectionSuccess() == 1) {
						parameterDto.setInspectionSucessStr("PASS");
					}else if(parameterDto.getIsInspectionSuccess() == 2) {
						parameterDto.setInspectionSucessStr("FAILED");
					}else {
						parameterDto.setInspectionSucessStr("NOT INSPECTED");
					}
					
					if(parameterDto.getInspectionDate() != null) {
						parameterDto.setInspectionDateStr(DateTimeUtility.getDateFromTimeStamp(parameterDto.getInspectionDate(), DateTimeUtility.DD_MM_YYYY));
					}
		
					Dtos.add(parameterDto);
				}
			}
			return Dtos;
	}
	
	@Override
	public List<InspectionCompletionToParameterDto> getGroupInspectionReportDetails(
			InspectionCompletionToParameterDto completionToParameterDto, String query, Map<Long, InspectionParameter> parameterHM) throws Exception {
						TypedQuery<Object[]> queryt = null;
						queryt = entityManager.createQuery(" SELECT ICP.completionToParameterId, ICP.completionDetailsId, ICP.inspectionSheetId, ICP.inspectionParameterId,"
								+ " ICP.isInspectionSuccess, ICP.isInspectionDone, ICP.completionDateTime, ICP.isDocumentUploaded, ICP.documentId, ICP.description, ICP.inspectedById,"
								+ " VCD.inspectionDate, VP.frequency, U.firstName, U.lastName, VP.inspectionParameterId, V.vehicle_registration "
								+ " FROM InspectionCompletionToParameter ICP "
								+ " INNER JOIN VehicleInspectionCompletionDetails VCD ON VCD.completionDetailsId = ICP.completionDetailsId"
								+ " INNER JOIN Vehicle V ON V.vid = ICP.vid"
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN VehicleInspectionSheetToParameter VP ON VP.inspectionSheetToParameterId = ICP.inspectionParameterId"
								+ " LEFT JOIN User U ON U.id = ICP.inspectedById"
								+ " WHERE VG.gid = "+completionToParameterDto.getVehicleGroupId()+" AND VCD.inspectionDate between '"+completionToParameterDto.getFromDate()+"' "
								+ " AND '"+completionToParameterDto.getToDate()+"' "+query+""
								+ " AND ICP.markForDelete = 0 ORDER BY ICP.vid ASC",
								Object[].class);
					List<Object[]> results = queryt.getResultList();
				
					List<InspectionCompletionToParameterDto> Dtos = null;
					if (results != null && !results.isEmpty()) {
						
						Dtos = new ArrayList<InspectionCompletionToParameterDto>();
						InspectionCompletionToParameterDto parameterDto = null;
						for (Object[] result : results) {
							parameterDto = new InspectionCompletionToParameterDto();
							
							parameterDto.setCompletionToParameterId((Long) result[0]);
							parameterDto.setCompletionDetailsId((Long) result[1]);
							parameterDto.setInspectionSheetId((Long) result[2]);
							parameterDto.setInspectionParameterId((Long) result[3]);
							parameterDto.setIsInspectionSuccess((short) result[4]);
							parameterDto.setInspectionDone((boolean) result[5]);
							parameterDto.setCompletionDateTime((Timestamp) result[6]);
							parameterDto.setDocumentUploaded((boolean) result[7]);
							parameterDto.setDocumentId((Long) result[8]);
							parameterDto.setDescription((String) result[9]);
							parameterDto.setInspectedById((Long) result[10]);
							parameterDto.setInspectionDate((Timestamp) result[11]);
							if(result[12] != null)
								parameterDto.setFrequency((int) result[12]);
							if(result[14] != null)
								parameterDto.setInspectedBy((String) result[13]+"_ "+(String) result[14]);
							
							parameterDto.setInspectionParameterId((Long) result[15]);
							parameterDto.setVehicle_registration((String) result[16]);
							
							if(parameterHM != null && !parameterHM.isEmpty()) {
								if(parameterHM.get(parameterDto.getInspectionParameterId()) != null) {
									parameterDto.setParameterName(parameterHM.get(parameterDto.getInspectionParameterId()).getParameterName());
								}
							}
							
							if(parameterDto.getIsInspectionSuccess() == 1) {
								parameterDto.setInspectionSucessStr("PASS");
							}else if(parameterDto.getIsInspectionSuccess() == 2) {
								parameterDto.setInspectionSucessStr("FAILED");
							}else {
								parameterDto.setInspectionSucessStr("NOT INSPECTED");
							}
							
							if(parameterDto.getInspectionDate() != null) {
								parameterDto.setInspectionDateStr(DateTimeUtility.getDateFromTimeStamp(parameterDto.getInspectionDate(), DateTimeUtility.DD_MM_YYYY));
							}
							
							if(parameterDto.getDocumentId() != null && parameterDto.getDocumentId() > 0) {
								String documentUrl	="<a style='color: blue;' class='btn btn-success btn-sm' href='downloadParameterDocument/"+parameterDto.getDocumentId()+".in'><span class='fa fa-download'> Document</span></a>";
								parameterDto.setDocumentUrl(documentUrl);
							}
				
							Dtos.add(parameterDto);
						}
					}
					return Dtos;
}
	@Override
	public void savePenalty(Long inspectionSheetId,Long inspectionParameterId ,int vid,Long vehicleInspctionSheetAsingmentId ,Double amount) throws Exception {
		
		InspectionFailedParameterPenalty penalty = null;
		
		try {
			penalty = new InspectionFailedParameterPenalty();
			penalty.setInspectionSheetId(inspectionSheetId);
			penalty.setVehicleInspctionSheetAsingmentId(vehicleInspctionSheetAsingmentId);
			penalty.setParameterPenaltyId(inspectionParameterId);
			penalty.setVid(vid);
			penalty.setPenaltyAmount(amount);
			penalty.setMarkForDelete(false);
			
			inspectionFailedPenaltyRepository.save(penalty);
		} catch (Exception e) {
			throw e;
		}
		
		
	}
	
}
