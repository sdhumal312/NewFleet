package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.DayWiseInventoryStockDto;
import org.fleetopgroup.persistence.model.DayWiseInventoryStock;

public interface DayWiseInventoryStockServiceDao {

	public DayWiseInventoryStock getOpeningClosingQuantityForPreviousDate(short txnTypeId, String txnDate, Integer locationId, Long partId)throws Exception;
	
	public DayWiseInventoryStock getOpeningClosingQuantityForDate(short txnTypeId, String txnDate, Integer locationId, Long partId)throws Exception ;
	
	public void updateClosingQuantityForNextDates(Double quantity , String txnDate, Integer locationId, Long partId) throws Exception;
	
	public void updateClosingQuantity(Double quantity , String txnDate, Integer locationId, Long partId) throws Exception;
	
	public List<DayWiseInventoryStockDto> getDayWiseInventoryStockList(String query, Integer companyId) throws Exception;
}
