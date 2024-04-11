package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.InspectionCompletionToParameterDto;
import org.fleetopgroup.persistence.model.InspectionCompletionToParameter;
import org.fleetopgroup.persistence.model.InspectionParameter;

public interface IInspectionCompletionToParameterService {

	public void saveInspectionCompletionToParameter(InspectionCompletionToParameter	completionToParameter) throws Exception;
	
	public void updateDocumentIdToParameter(Long parameterId, Long documentId) throws Exception;
	
	public List<InspectionCompletionToParameter>   	  getInspectionCompletionToParameterByDateAndVid(Integer vid, Timestamp	date) throws Exception;
	
	public List<InspectionCompletionToParameterDto>   getInspectionCompletionToParameterDetails(Integer vid, Long completionDetailsId) throws Exception;
	
	List<InspectionCompletionToParameterDto> getVehicleInspectionReportDetails(InspectionCompletionToParameterDto	parameterDto, String query, Map<Long, InspectionParameter> parameterHM) throws Exception;
	
	List<InspectionCompletionToParameterDto> getGroupInspectionReportDetails(InspectionCompletionToParameterDto	parameterDto, String query, Map<Long, InspectionParameter> parameterHM) throws Exception;

	public void savePenalty(Long inspectionSheetId,Long inspectionParameterId ,int vid,Long vehicleInspctionSheetAsingmentId ,Double amount) throws Exception ;
	
	public void updateDocumentToParameter(Long parameterId) throws Exception ;
}
