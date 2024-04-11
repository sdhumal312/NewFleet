package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceEntriesDao {
	
	List<ServiceEntriesDto>  getServiceEntriesList(ServiceEntriesDto serviceEntriesDto)throws Exception;
	
	List<ServiceEntriesDto> getServiceEntriesPendingList(Integer companyId, Integer vid) throws Exception;
	
	public void updatePendingStatus(Long serviceId, short status) throws Exception;
}
