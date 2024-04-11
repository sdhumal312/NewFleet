package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.InspectionParameter;
import org.fleetopgroup.persistence.model.TripSheetOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleInspectionParameterRepository extends JpaRepository<InspectionParameter, Long>{

	@Override
	@Query("From InspectionParameter where markForDelete = 0")
	public List<InspectionParameter>  findAll();
	
	@Query("From InspectionParameter where markForDelete = 0 AND companyId = ?1 ")
	public List<InspectionParameter> getInspectionParameterList (Integer companyId);
	
	@Query("FROM InspectionParameter IP Where IP.parameterName = ?1 and IP.markForDelete = 0 AND companyId = ?2 ")
	public InspectionParameter findByParamterName(String parameterName, Integer companyId);
	
	@Query("FROM InspectionParameter IP Where IP.inspectionParameterId = ?1 and IP.markForDelete = 0 AND companyId = ?2 ")
	public InspectionParameter findByParamterId(long parameterId, Integer companyId);
	
	@Modifying
	@Query("UPDATE InspectionParameter SET markForDelete = 1 WHERE inspectionParameterId = ?1 AND companyId = ?2 ")
	public void deleteParameter(long photoid,Integer companyId);
	
	@Modifying
	@Query("UPDATE InspectionParameter SET lastModifiedOn = ?2, parameterPhotoId = ?3  WHERE inspectionParameterId = ?1")
	public void updateParameter(Long id,Timestamp lastupdated, long photoId);
	
	@Modifying
	@Query("UPDATE InspectionParameter SET parameterName = ?2, lastModifiedOn = ?3 WHERE inspectionParameterId = ?1")
	public void updateParameterPhotoIdNull(Long id, String name, Timestamp lastupdated);
	
	@Modifying
	@Query("UPDATE InspectionParameter SET parameterName = ?2, lastModifiedOn = ?3, parameterPhotoId = ?4 WHERE inspectionParameterId = ?1")
	public void updateParameterParameterName(Long id, String name, Timestamp lastupdated,long photoId);
	
	
}
