package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.JobSubTypeDto;
import org.fleetopgroup.persistence.model.JobSubType;
import org.springframework.data.domain.Page;

public interface IJobSubTypeService {
	/* Sub Type */
	public void addJobSubType(JobSubType DocType) throws Exception;
	
	public void updateJobSubType(JobSubType DocType) throws Exception;

	/** This Page get WorkOrders table to get pagination values */
	public Page<JobSubType> getDeployment_Page_JobSubType(Integer pageNumber) throws Exception;

	public List<JobSubType> listJobSubType(Integer pageNumber) throws Exception;
	
	public List<JobSubTypeDto> listJobSubType(Integer pageNumber, Integer companyId) throws Exception;

	public List<JobSubType> listJobSubType() throws Exception;

	public JobSubType getJobSubType(int dtid) throws Exception;
	
	public JobSubTypeDto getJobSubType(int dtid, Integer companyId) throws Exception;

	public void deleteJobSubType(Integer DocType, Integer companyId) throws Exception;

	public List<JobSubTypeDto> SearchOnlyJobSubType(String term, Integer companyId) throws Exception;
	
	public List<JobSubTypeDto> SearchOnlyJobSubType(Integer term, Integer companyId) throws Exception;
	
	public JobSubType validateJobSubType(String Job_SubType) throws Exception;
	
	public List<JobSubType> ValidateJobRotnumber(String Rotnumber, Integer companyId) throws Exception;
	
	public List<JobSubType> ValidateJobSubType(String Type, String Rotnumber, Integer companyId) throws Exception;
	
	/**Search Job Sub Type Value in ROT and Type and SubType  */
	public List<JobSubType> SearchJobSubType_ROT(String term, Integer companyId) throws Exception;
	
	/**Search Job Sub Type ID to in only Type  */
	public JobSubTypeDto getJobSub_ID_Only_TypeName(int dtid, Integer companyId) throws Exception;
	
	public JobSubType validateJobSubType(String Job_SubType, Integer companyId) throws Exception;
	
	public List<JobSubTypeDto> listJobSubTypeByCompanyId(Integer companyId) throws Exception;

	public JobSubType getJobSubTypeByJobROTAndJobType(String job_Rot, String jobType, Integer companyId)throws Exception;
	public JobSubType getJobSubTypeForMob(int dtid,int companyId) throws Exception;
	public List<JobSubType> listJobSubTypeListByCompanyId(Integer companyId) throws Exception;

	
	
}