package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.DriverTrainingType;


public interface IDriverTrainingTypeService {

	public void addDriverTrainingType(DriverTrainingType TrainingType) throws Exception;
	
	public void updateDriverTrainingType(String dri_TrainingType, Long modifiedBy, Timestamp modifiedOn, Integer dri_id, Integer companyId) throws Exception;

	public List<DriverTrainingType> listDriverTrainingType() throws Exception;

	public DriverTrainingType getDriverTrainingType(int dtid, Integer companyid) throws Exception;
	
	public DriverTrainingType ValidateDriverTrainingType(String dri_TrainingType, Integer companyId) throws Exception;

	public void deleteDriverTrainingType(Integer dri_id, Integer companyId) throws Exception;
	
	public List<DriverTrainingType> listDriverTrainingTypeByCompanyId(Integer companyId) throws Exception;

}