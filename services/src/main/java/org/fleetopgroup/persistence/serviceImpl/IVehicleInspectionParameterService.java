package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.model.InspectionParameter;
import org.springframework.stereotype.Service;

@Service
public interface IVehicleInspectionParameterService {


	public InspectionParameter findByParamterName(String parameterName, Integer companyId) throws Exception;

	public  InspectionParameter registerNewInspectionParameter(InspectionParameter inspectionParameter) throws Exception;

	public InspectionParameter findByParamterId(long id, Integer companyId) throws Exception;
	
	public InspectionParameter findByInspectionParameterId(long id, Integer companyId) throws Exception;
	
	public void deleteParameter(long photoid, Integer companyId) throws Exception;
	
	public void updateParameter(Long id, Timestamp lastUpdated, long photoId) throws Exception;
	
	public List<InspectionParameter> getInspectionParameterListForDropDown(String term) throws Exception;

	void updateParameterPhotoIdNull(Long inspectionParameterId, String parameterName, Timestamp lastModifiedOn)throws Exception;

	public List<InspectionParameter> getInspectionParameterList(Integer companyId) throws Exception;

	public void updateParameterParameterName(Long inspectionParameterId, String parameterName, Timestamp toDate, long docId)throws Exception;
	
	public Map<Long, InspectionParameter>  getInspectionParameterHM(Integer companyId) throws Exception;
}
