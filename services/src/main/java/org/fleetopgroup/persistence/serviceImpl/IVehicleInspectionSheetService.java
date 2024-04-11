package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleInspctionSheetAsingmentDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionCompletionDetailsDto;
import org.fleetopgroup.persistence.model.VehicleInspectionCompletionDetails;
import org.fleetopgroup.persistence.model.VehicleInspectionSheet;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface IVehicleInspectionSheetService {

	public VehicleInspectionSheet saveVehicleInspectionSheet(VehicleInspectionSheet	vehicleInspectionSheet) throws Exception;
	
	public List<VehicleInspectionSheet>  validateVehicleInspectionSheetName(String	name, Integer	companyId) throws Exception;
	
	public List<VehicleInspectionSheet>  getListVehicleInspectionSheet(Integer	companyId) throws Exception;
	
	public List<VehicleInspectionSheet>  getListVehicleInspectionSheet(String term , Integer	companyId) throws Exception;
	
	public	VehicleInspectionSheet getVehicleInspectionSheetById(Long vehicleInspectionSheetId) throws Exception;
	
	public void updateNoOfVehicleAssignment(Long vehicleInspectionSheetId,int count) throws Exception;
	
	public void substractNoOfVehicleAssignment(Long vehicleInspectionSheetId,int count) throws Exception;
	
	public void deleteVehicleInspectionSheet(Long vehicleInspectionSheetId, Integer companyId) throws Exception;

	public	ValueObject getVehicleInspectionParameter(Integer companyId, Long inspectionSheetId) throws Exception;

	public ValueObject getVehicleInspectionSheetFindById(ValueObject valueObject) throws Exception;

	public ValueObject updateInspectionSheetDetails(ValueObject valueOutObject)throws Exception;
	
	public Page<VehicleInspectionCompletionDetails> getVehicleInspectionPage(Integer vid, Integer pageNumber, Integer companyId);
	
	public List<VehicleInspectionCompletionDetailsDto> getVehicleInspectionPageList(Integer vid, Integer pageNumber, Integer companyId);
	
	public void updateVehicleType(Long vehicleTYpeId,Long vehicleInspectionSheetId) throws Exception;
	
	public void updateMultiVehicleType(String vehicleTYpeId,Long vehicleInspectionSheetId) throws Exception;
	
	public void addInspectionSheetToVehicle(VehicleInspctionSheetAsingmentDto vehicleInspectionSheetAssignment) throws Exception ;
}
