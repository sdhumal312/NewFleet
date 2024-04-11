package org.fleetopgroup.persistence.report.dao;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.ServiceProgramSchedulesDto;
import org.fleetopgroup.persistence.dto.VehicleServiceProgramDto;

public interface ServiceProgramDao {

	public VehicleServiceProgramDto getServiceProgramDetailsById(Long id)throws Exception;
	
	public List<ServiceProgramSchedulesDto> getServiceProgramSchedulesBtId(Long id) throws Exception;
	
	HashMap<Long, Long>  getServiceProgramWiseCountHM(Integer	companyId) throws Exception;
}
