package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleInspectionSheetToParameterDto;
import org.fleetopgroup.persistence.model.VehicleInspectionSheetToParameter;
import org.springframework.stereotype.Service;

@Service
public interface IVehicleInspectionSheetToParameterService {

	public void saveVehicleInspectionSheetToParameter(VehicleInspectionSheetToParameter	inspectionSheetToParameter) throws Exception;
	
	List<VehicleInspectionSheetToParameterDto>   getVehicleInspectionSheetToParameterList(Long inspectionParameterId, Integer companyId)throws Exception;
	
	List<VehicleInspectionSheetToParameterDto>   getVehicleInspectionSheetToParameterListToINspect(Long assignmentId, Integer vid, Integer companyId, String date)throws Exception;

	public void updateInspectionSheet(Object frequency, Object inspectionParameter, Object requiredTypeId, Object inputPhotoTypeId, Object textGroupTypeId, Object inspectionSheetParameterId)throws Exception;

	void RemoveInspectionParameter(long inspectionSheetParameterId, Integer companyId) throws Exception;

	public void updateLastModifiedFromVehicleInspectionSheet(Object object, Timestamp currentDate, long id)throws Exception;

	public List<VehicleInspectionSheetToParameter> getVehicleInspectionSheetsToParameterListByParameterId(
			Long inspectionParameterId, Integer company_id)throws Exception;

	public List<VehicleInspectionSheetToParameter> checkParametersInVehicleInspectionSheets(long asingmentId,Integer companyId)throws Exception;


}
