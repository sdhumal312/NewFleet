package org.fleetopgroup.persistence.dao;

import java.sql.Date;
import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.VehicleInspectionCompletionDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleInspectionCompletionDetailsRepository extends JpaRepository<VehicleInspectionCompletionDetails, Long>{

	@Query("SELECT count(completionDetailsId) FROM VehicleInspectionCompletionDetails where inspectionDate = ?2 AND companyId =?1 AND inspectionStatusId = 2 AND markForDelete = 0")
	public long getTodaysVehicleInspectionCount(Integer companyId, Timestamp	inspectionStartDate) throws Exception; 
	
	@Query("SELECT F.completionDetailsId From VehicleInspectionCompletionDetails as F where F.vid=?1 AND F.companyId =?2 AND F.inspectionStatusId = 2 AND F.markForDelete = 0 ORDER BY F.completionDetailsId DESC ")
	public Page<VehicleInspectionCompletionDetails> findByVid(Integer vid, Integer companyId, Pageable pageable);
	
	
	@Query("FROM VehicleInspectionCompletionDetails WHERE vehicleInspctionSheetAsingmentId = ?1 AND companyId = ?2 AND markForDelete = 0  ")
	public VehicleInspectionCompletionDetails getVehicleInspectionCompletionDetails(Long vehicleInspctionSheetAsingmentId ,Integer companyId);
	
	@Query(nativeQuery= true,value= " select * FROM VehicleInspectionCompletionDetails WHERE vehicleInspctionSheetAsingmentId = ?1 AND companyId = ?2 AND inspectionDate = ?3 AND  markForDelete = 0   ")
	public VehicleInspectionCompletionDetails getTodaysInspectionCompletionDetails(Long vehicleInspctionSheetAsingmentId ,Integer companyId,Date startDate);
	
	@Query("FROM VehicleInspectionCompletionDetails WHERE completionDetailsId = ?1 AND companyId = ?2 AND markForDelete = 0  ")
	public VehicleInspectionCompletionDetails getInspectionCompletionDetailsByComplitionId(Long complitionId ,Integer companyId);
}
