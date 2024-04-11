package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.JobType;


public interface IJobTypeService {
	public void addJobType(JobType DocType) throws Exception;

	public List<JobType> listJobType() throws Exception;

	public JobType getJobType(int dtid, Integer companyId) throws Exception;

	public void deleteJobType(Integer DocType, Integer companyid) throws Exception;

	public List<JobType> SearchOnlyJobType(String term, Integer companyId) throws Exception;
	
	public JobType validateJobType(String Job_Type) throws Exception;
	
	public List<JobType> listJobTypeByCompanyId(Integer companyId) throws Exception;
	
	public JobType validateJobType(String Job_Type, Integer companyId) throws Exception;
	
	public void updateJobType(JobType DocType) throws Exception;
}