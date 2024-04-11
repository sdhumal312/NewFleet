package org.fleetopgroup.persistence.dao;

import java.util.Date;

import org.fleetopgroup.persistence.model.VehicleDailyInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleDailyInspectionRepository extends JpaRepository<VehicleDailyInspection, Long>{
	
	@Query("select count(VDI.vehicleDailyInspectionId) from VehicleDailyInspection VDI "
			+ " INNER JOIN VehicleInspectionSheet VIS ON VIS.vehicleInspectionSheetId = VDI.inspectionSheetId AND VIS.markForDelete = 0 "
			+ " where VDI.companyId = ?1 AND VDI.inspectionDate < ?2 AND VDI.inspectionStatusId <> ?3 AND VDI.isSkiped = 0 AND VDI.markForDelete = 0")
	public Long totalCountOfMissedInspection(Integer companyId, Date todaysDate, short inspectionStatus) throws Exception;
	
	@Query("SELECT COUNT(VDI.vehicleDailyInspectionId) FROM VehicleDailyInspection VDI where VDI.vid = ?1 AND VDI.inspectionDate = ?2 AND VDI.companyId = ?3  AND VDI.markForDelete = 0 ")
	public long validateDailyInspection(Integer vid, Date todaysDate, Integer companyId) throws Exception;
	
	@Query("FROM VehicleDailyInspection VDI where VDI.vid = ?1 AND VDI.inspectionDate = ?2 AND VDI.companyId = ?3 AND VDI.isSkiped = 0 AND VDI.markForDelete = 0 ")
	public VehicleDailyInspection findByDate(Integer vid, Date todaysDate, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleDailyInspection VDI SET VDI.inspectionStatusId = ?1 where VDI.vid = ?2 AND VDI.inspectionDate = ?3 AND VDI.companyId = ?4 AND VDI.markForDelete = 0")
	public void updateInspectionStatus(short status, Integer vid, Date todaysDate, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleDailyInspection VDI SET VDI.markForDelete = 1 where VDI.vid = ?1 AND VDI.inspectionDate = ?2 AND VDI.companyId = ?3 ")
	public void deleteInspectionByVehicle(Integer vid, Date todaysDate, Integer companyId) throws Exception;
	
	@Query("select count(VDI.vehicleDailyInspectionId) from VehicleDailyInspection VDI "
			+ " INNER JOIN Vehicle V ON V.vid = VDI.vid "
			+ " INNER JOIN UserProfile AS UP ON UP.branch_id = V.branchId AND UP.user_id = ?4 "
			+ " INNER JOIN VehicleInspectionSheet VIS ON VIS.vehicleInspectionSheetId = VDI.inspectionSheetId AND VIS.markForDelete = 0 "
			+ " where VDI.companyId = ?1 AND VDI.inspectionDate < ?2 AND VDI.inspectionStatusId <> ?3 AND VDI.isSkiped = 0 AND VDI.markForDelete = 0")
	public long totalCountOfMissedInspection(Integer companyId, Date todaysDate, short inspectionStatus, long userId) throws Exception;
	
	
	@Modifying
	@Query("UPDATE VehicleDailyInspection SET markForDelete = 1 where inspectionSheetId =?1 AND vehicleTypeId = ?2 AND ( branchId IS NULL OR branchId = 0)  AND inspectionStatusId = 0 AND companyId = ?3 AND markForDelete =0")
	public Integer deleteVehicleAssignmentByVehicleType(Long inspectionSheetId, Long vehicleTypeId,Integer companyId) throws Exception;
	
	
	@Modifying
	@Query("UPDATE VehicleDailyInspection SET markForDelete = 1 where inspectionSheetId =?1 AND vehicleTypeId = ?2 AND branchId = ?4 AND inspectionStatusId = 0 AND companyId = ?3 AND markForDelete =0")
	public Integer deleteVehicleAssignmentByVehicleTypeAndBranch(Long inspectionSheetId, Long vehicleTypeId,Integer companyId,Integer branchId) throws Exception;

	@Modifying
	@Query("UPDATE VehicleDailyInspection SET markForDelete = 1 where vid =?1 AND vehicleTypeId = ?2  AND companyId = ?3 AND markForDelete =0")
	public Integer deleteVehicleAssignmentByVid(Integer vid, Long vehicleTypeId,Integer companyId) throws Exception;
	
	
	@Modifying
	@Query("UPDATE VehicleDailyInspection VDI SET VDI.isSkiped = 1 ,VDI.skipedRemark = ?2 where VDI.vehicleDailyInspectionId =?1  AND VDI.companyId = ?3 ")
	public Integer updateSkipedStatus(Long vid,String skipedRemark, Integer companyId) throws Exception;

}