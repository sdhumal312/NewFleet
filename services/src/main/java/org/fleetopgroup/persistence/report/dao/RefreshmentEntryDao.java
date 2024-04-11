package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.RefreshmentEntryDto;

public interface RefreshmentEntryDao {

	List<RefreshmentEntryDto>  getRefreshmentConsumptionData(String	query, Integer companyId, Long userId) throws Exception;
	
	public List<RefreshmentEntryDto> getRefreshmentEntryDtoList(Integer pageNumber, Integer companyId, Long userId) throws Exception;
}
