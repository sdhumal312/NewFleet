package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleInspectionSheetAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleInspctionSheetAsingmentRepository extends JpaRepository<VehicleInspectionSheetAssignment, Long>{

	@Modifying
	@Query("UPDATE VehicleInspectionSheetAssignment SET markForDelete = 1 where vehicleInspctionSheetAsingmentId =?1 AND vid = ?2")
	public void deleteVehicleAssignment(Long vehicleInspctionSheetAsingmentId, Integer vid) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleInspectionSheetAssignment SET inspectionStatusId = ?1 where vehicleInspctionSheetAsingmentId =?2  AND companyId =?3 AND markForDelete = 0")
	public void updateVehicleAssignmentStatus(short statusId,Long vehicleInspctionSheetAsingmentId ,Integer companyId) throws Exception;
	
	@Query("SELECT count(vehicleInspctionSheetAsingmentId) FROM VehicleInspectionSheetAssignment where inspectionStartDate <= ?2 AND companyId =?1 AND markForDelete = 0")
	public long getTodaysVehicleInspectionCount(Integer companyId, Timestamp	inspectionStartDate) throws Exception; 
	
	@Query("FROM VehicleInspectionSheetAssignment where inspectionStartDate >= ?2 AND companyId =?1 AND markForDelete = 0")
	public List<VehicleInspectionSheetAssignment> getTodaysVehicleInspectionList(Integer companyId, Timestamp	inspectionStartDate) throws Exception;
	
	@Query("FROM VehicleInspectionSheetAssignment where inspectionSheetId = ?1 AND companyId =?2 AND markForDelete = 0")
	public List<VehicleInspectionSheetAssignment> getVehiclesAsPerSheetId(Long inspectionSheetId, Integer companyId) throws Exception;
	
//	@Modifying
//	@Query("UPDATE VehicleInspectionSheetAssignment SET penaltyAmount =?1 WHERE vehicleInspctionSheetAsingmentId=?2 ")
//	public void updatedPenalty(Double penalty , Long vehicleInspctionSheetAsingmentId)throws Exception;
	
	
	@Query("SELECT count(vehicleInspctionSheetAsingmentId) FROM VehicleInspectionSheetAssignment where inspectionStartDate <= ?2 AND companyId =?1 AND markForDelete = 0")
	public long getVehicleInspectionCount(Integer companyId, Timestamp	inspectionStartDate) throws Exception; 
	
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE VehicleInspectionSheetAssignment VI  SET VI.markForDelete = 1 where"
			+ " VI.inspectionSheetId =?1 AND VI.vehicleTypeId = ?2 AND (VI.branchId IS NULL OR VI.branchId = 0)  AND VI.companyId = ?3   AND VI.markForDelete =0")
	public Integer deleteVehicleAssignmentByVehicleType(Long inspectionSheetId, Long vehicleTypeId,Integer companyId) throws Exception;
	
	
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE VehicleInspectionSheetAssignment VI  SET VI.markForDelete = 1 where"
			+ " VI.inspectionSheetId =?1 AND VI.vehicleTypeId = ?2 AND VI.branchId = ?4 AND VI.companyId = ?3   AND VI.markForDelete =0")
	public Integer deleteVehicleAssignmentByVehicleTypeAndBranch(Long inspectionSheetId, Long vehicleTypeId,Integer companyId,Integer branchId) throws Exception;
	
	
	@Modifying
	@Query("UPDATE VehicleInspectionSheetAssignment SET markForDelete = 1 where  vid = ?1 AND vehicleTypeId =?3 AND companyId =?2 AND markForDelete = 0")
	public Integer deleteVehicleAssignmentByVid( Integer vid ,Integer companyId ,Long vTypeId) throws Exception;
	
	@Query("FROM VehicleInspectionSheetAssignment where vid = ?1 AND vehicleTypeId = ?2 AND companyId =?3 AND markForDelete = 0 ")
	public VehicleInspectionSheetAssignment getVehicleAssignmentByVid(Integer vid ,Long vehicleType ,Integer companyId );

}
