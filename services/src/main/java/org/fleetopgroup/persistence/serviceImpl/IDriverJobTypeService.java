package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.DriverJobType;


public interface IDriverJobTypeService {

	public void addDriverJobType(DriverJobType JobType) throws Exception;
	
	public void updateDriverJobType(DriverJobType JobType) throws Exception;

	public List<DriverJobType> listDriverJobType() throws Exception;

	public DriverJobType getDriverJobType(int dtid, Integer companyId) throws Exception;

	public DriverJobType get_EditableDriverJobType(int dtid, Integer companyid) throws Exception;

	public void deleteDriverJobType(Integer JobType, Integer companyId) throws Exception;
	
	public DriverJobType validateDriverJobType(String driJobType, Integer companyId) throws Exception;
	
	public List<DriverJobType> listDriverJobTypeByCompanyId(Integer companyId) throws Exception;
	
	public DriverJobType getTopDriverJobType(Integer companyId) throws Exception;

}