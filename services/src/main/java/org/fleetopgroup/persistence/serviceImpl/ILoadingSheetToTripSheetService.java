package org.fleetopgroup.persistence.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.LoadingSheetToTripSheetDto;
import org.fleetopgroup.persistence.model.LoadingSheetToTripSheet;
import org.springframework.stereotype.Service;

@Service
public interface ILoadingSheetToTripSheetService {

	public void saveLoadingSheetToTripSheet(LoadingSheetToTripSheet		loadingSheetToTripSheet) throws Exception;
	
	public void saveAllLoadingSheetToTripSheet(List<LoadingSheetToTripSheet>		loadingSheetToTripSheet) throws Exception;
	
	HashMap<String, LoadingSheetToTripSheet>  validateLoadingSheetAdded(Long tripSheetId, Integer companyId) throws Exception;
	
	List<LoadingSheetToTripSheet>  getLoadingSheetToTripSheetByDispatchLedgerId(Long dispatchLedgerId, Long tripSheetId) throws Exception;
	
	List<LoadingSheetToTripSheet>  getLoadingSheetToTripSheetByTripSheetId(Long dispatchLedgerId) throws Exception;

	public List<LoadingSheetToTripSheetDto> getLoadingSheetTotripSheetDetailsByTripSheetId(Integer company_id, Long id) throws Exception;
}
