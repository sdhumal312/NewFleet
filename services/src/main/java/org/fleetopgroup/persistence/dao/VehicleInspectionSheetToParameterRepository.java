package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleInspectionSheetToParameterDto;
import org.fleetopgroup.persistence.model.VehicleInspectionSheetToParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleInspectionSheetToParameterRepository extends JpaRepository<VehicleInspectionSheetToParameter, Long>{
	
	@Modifying
	@Query("Update  FROM VehicleInspectionSheetToParameter v set v.markForDelete = 1 where v.inspectionSheetToParameterId = ?1 and v.companyId = ?2")
	public void deleteInspectionParameter(Object object, Integer companyId) throws Exception;
	
	
	@Query("FROM VehicleInspectionSheetToParameter where inspectionParameterId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<VehicleInspectionSheetToParameter> getInspectionParametersheetByInspectionParameterId(Long inspectionSheetParameterId, Integer companyId);
	
	@Query("FROM VehicleInspectionSheetToParameter where inspectionSheetId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<VehicleInspectionSheetToParameter> getAllParametersInVehicleInspectionSheets(Long inspectionSheetParameterId, Integer companyId);
	
	@Query("FROM VehicleInspectionSheetToParameter where inspectionSheetId = ?1 AND frequency = ?2 AND companyId = ?3 AND markForDelete = 0")
	public List<VehicleInspectionSheetToParameter> getAllParametersWithFrequencyAsOne(Long inspectionSheetId, int frequency, Integer companyId);

}
