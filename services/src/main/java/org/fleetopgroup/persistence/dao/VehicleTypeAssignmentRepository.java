package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.VehicleTypeAssignmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleTypeAssignmentRepository extends JpaRepository<VehicleTypeAssignmentDetails, Long> {
	
	@Modifying
	@Query(" UPDATE VehicleTypeAssignmentDetails SET markForDelete =1 , lastUpdatedBy =?4 ,lastUpdatedDate =?5 WHERE inspectionSheetId =?1 AND vehicleTypeId =?2  AND (branchId IS NULL OR branchId = 0) AND companyId = ?3 AND markForDelete = 0")
	public void deletedVehicleTypeAssignmentByType(Long inspectionSheetId, Long vehicleTypeID, Integer companyId ,Long lastUpdatedBy , Timestamp lastUpdatedDate);
	
	@Modifying
	@Query(" UPDATE VehicleTypeAssignmentDetails SET markForDelete =1 , lastUpdatedBy =?4 ,lastUpdatedDate =?5 WHERE inspectionSheetId =?1 AND vehicleTypeId =?2 AND branchId = ?6 AND companyId = ?3 AND markForDelete = 0")
	public void deletedVehicleTypeAssignmentByTypeAndBranch(Long inspectionSheetId, Long vehicleTypeID, Integer companyId ,Long lastUpdatedBy , Timestamp lastUpdatedDate,Integer branchId);
	
	@Query(nativeQuery=true, value=" SELECT *  FROM VehicleTypeAssignmentDetails  WHERE vehicleTypeId =?1 AND companyId = ?2 AND markForDelete = 0 ORDER BY vehicleTypeAssignmentDetailsId DESC LIMIT 1")
	public VehicleTypeAssignmentDetails getVehicleTypeAssignmentByType( Long vehicleTypeId, Integer companyId);
	
	@Query(nativeQuery=true, value= "SELECT * FROM VehicleTypeAssignmentDetails  WHERE branchId =?1 AND vehicleTypeId =?2 AND companyId = ?3 AND markForDelete = 0 ORDER BY vehicleTypeAssignmentDetailsId DESC LIMIT 1 ")
	public VehicleTypeAssignmentDetails getVehicleTypeAssignmentByTypeAndBranch(Integer branchId, Long vehicleTypeId,Integer companyId);

}
