package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.InspectionCompletionToParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InspectionCompletionToParameterRepository extends JpaRepository<InspectionCompletionToParameter, Long>{

	@Modifying
	@Query("UPDATE InspectionCompletionToParameter SET documentId = ?2,isDocumentUploaded = 1 WHERE  completionToParameterId = ?1")
	public void updateDocumentIdToParameter(Long parameterId, Long documentId);
	
	@Modifying
	@Query("UPDATE InspectionCompletionToParameter SET isDocumentUploaded = 1 WHERE  completionToParameterId = ?1")
	public void updateDocumentToParameter(Long parameterId);
	
	@Query("SELECT IP FROM InspectionCompletionToParameter IP "
			+ " INNER JOIN VehicleInspectionCompletionDetails VIC ON VIC.completionDetailsId = IP.completionDetailsId "
			+ " WHERE  IP.vid = ?1 AND VIC.inspectionDate =?2 AND VIC.markForDelete = 0 AND IP.markForDelete = 0")
	public List<InspectionCompletionToParameter> getInspectionCompletionToParameterByDateAndVid(Integer vid, Timestamp date) throws Exception;
	
	@Query("FROM InspectionCompletionToParameter WHERE completionToParameterId=?1  AND vid =?2 AND markForDelete = 0 ")
	public InspectionCompletionToParameter getFailedParameterIsuuesId(Long completionToParameterId , int vid) throws Exception;

}
