package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.BatteryHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.RefreshmentEntryDto;
import org.fleetopgroup.persistence.model.RefreshmentEntry;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface IRefreshmentEntryService {

	List<InventoryDto>  getRefreshmentEntryList(String term, Integer companyId) throws Exception;
	
	ValueObject		saveRefreshmentEntriesDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject getRefreshmentEntryListByTripSheetId(Long tripsheetId) throws Exception;
	
	RefreshmentEntryDto getRefreshmentEntryListById(Long refreshmentEntryId, Integer companyId) throws Exception;
	
	ValueObject		saveReturnRefreshment(ValueObject	valueObject) throws Exception;
	
	public void updateReturnRefreshmentDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject		getRefreshmentListDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject		removeRefreshment(ValueObject	valueObject) throws Exception;
	
	ValueObject		getRefreshmentConsumptionReport(ValueObject	valueObject) throws Exception;
	
	ValueObject		getTripSheetForDate(ValueObject	valueObject) throws Exception;
	
	ValueObject		getPageWiseRefreshmentsDetails(ValueObject	valueObject) throws Exception;
	
	public Page<RefreshmentEntry> getDeployment_Page_Refreshment(Integer pageNumber, Integer companyId, Long userId) throws Exception;
	
	ValueObject		deleteRefreshmentEntry(ValueObject	valueObject) throws Exception;
	
	ValueObject		getRefreshmentStockReport(ValueObject	valueObject) throws Exception;
	
	ValueObject		searchRefreshmentEntriesByNumber(ValueObject	valueObject) throws Exception;

	List<RefreshmentEntryDto> getRefreshmentConsumptionList(ValueObject object)throws Exception;
	
}
