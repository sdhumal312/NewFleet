package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.DayWiseInventoryStockDto;
import org.fleetopgroup.web.util.ValueObject;

public interface IDayWiseInventoryStockService {
	
	public void processLocationWiseInventoryStockDetails(ValueObject	valueObject)throws Exception;

	public void procressOpeningClosingLocationWiseInventoryStock(ValueObject	valueObject) throws Exception;
	
	public List<DayWiseInventoryStockDto> getDayWiseInventoryStockList(String query, Integer companyId) throws Exception;
}
