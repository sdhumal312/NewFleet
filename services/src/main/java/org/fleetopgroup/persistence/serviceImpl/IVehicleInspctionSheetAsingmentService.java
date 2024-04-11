package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.VehicleDailyInspectionDto;
import org.fleetopgroup.persistence.dto.VehicleInspctionSheetAsingmentDto;
import org.fleetopgroup.persistence.dto.VehicleTypeAssignmebtDetailsDto;
import org.fleetopgroup.persistence.model.VehicleDailyInspection;
import org.fleetopgroup.persistence.model.VehicleInspectionSheet;
import org.fleetopgroup.persistence.model.VehicleInspectionSheetAssignment;
import org.fleetopgroup.persistence.model.VehicleType;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IVehicleInspctionSheetAsingmentService {

	public VehicleInspctionSheetAsingmentDto	getVehicleInspctionSheetAsingmentbyVid(Integer	vid, Integer companyId) throws Exception;
	
	public void saveVehicleAssignment(VehicleInspectionSheetAssignment	assignment) throws Exception;
	
	public void deleteVehicleAssignment(Long vehicleInspctionSheetAsingmentId, Integer vid) throws Exception;
	
	public long getTodaysVehicleInspectionCount(Integer companyId, Timestamp	inspectionStartDate) throws Exception;
	
	public List<VehicleInspctionSheetAsingmentDto> getTodaysVehicleInspectionList(Integer companyId, Timestamp	inspectionStartDate) throws Exception;
	
	public VehicleInspctionSheetAsingmentDto	getVehicleInspctionSheetAsingmentbyAssignmentId(Long	assignmentId, Integer companyId) throws Exception;
	
	public VehicleInspctionSheetAsingmentDto	getVehicleInspctionSheetAsingmentbyCompletionID(Long	completionID, Integer companyId) throws Exception;
	
	public String AsignSheetByVehicleType(Long inspectionId,Long vehicleType,String ispectionDate,short multiType ,Integer VehicleLocation) throws Exception;
	
	public void prepareSheeetToAssignMultiVehicleType(String vehicleType, Long inspectionId,Integer VehicleLocation) throws Exception ;
	
	public ValueObject deleteVehicleInspectionSheetByVehicleType(ValueObject valueInObject) throws Exception;
	
	//public VehicleTypeAssignmebtDetailsDto  getvehicleTypeAssignmebtDetailsByTypeId(Long vehicleTypeId) throws Exception;
	
	public ValueObject getVehicleTypeInspectionSheetDto(HashMap<Long, VehicleType> vehicleTypeHM ,VehicleInspectionSheet inspectionSheet) throws Exception;

	public void editVehicleSheetAssignment(Long oldvTypeId,Long newvTypeId,int vid ,Integer branchId) throws Exception;
}
