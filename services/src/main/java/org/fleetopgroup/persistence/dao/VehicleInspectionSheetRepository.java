package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleInspectionSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleInspectionSheetRepository extends JpaRepository<VehicleInspectionSheet, Long>{

	@Query("FROM VehicleInspectionSheet where inspectionSheetName = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<VehicleInspectionSheet> validateVehicleInspectionSheetName(String name, Integer companyId) throws Exception;
	
	@Query("FROM VehicleInspectionSheet where companyId = ?1 AND markForDelete = 0")
	public List<VehicleInspectionSheet> getListVehicleInspectionSheet(Integer companyId) throws Exception;
	
	@Query("FROM VehicleInspectionSheet where vehicleInspectionSheetId = ?1 ")
	public VehicleInspectionSheet getVehicleInspectionSheetById(Long vehicleInspectionSheetId) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleInspectionSheet SET vehicleTypeId = ?1 WHERE vehicleInspectionSheetId =?2 AND companyId =?3 AND markForDelete = 0")
	public void updateVehicleTypeId(String vehicleTypeId , Long vehicleInspectionSheetId,int companyId )throws Exception;
	
	
}
