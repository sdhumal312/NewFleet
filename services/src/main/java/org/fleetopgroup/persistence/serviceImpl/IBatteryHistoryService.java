package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.BatteryHistoryDto;
import org.fleetopgroup.persistence.model.BatteryHistory;
import org.fleetopgroup.persistence.model.VehicleBatteryLayout;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IBatteryHistoryService {

	public void save(BatteryHistory		batteryHistory) throws Exception;
	
	List<BatteryHistoryDto>  getBatteryHistoryList(Long batteryId) throws Exception;

	public Map<Integer, List<BatteryHistoryDto>> getAllBatteryAssignmentDetails(String startDate, String endDate)throws Exception;

	public BatteryHistoryDto getPreBatteryHistory(VehicleBatteryLayout vehicleBatteryLayout, long layoutId, short batteryStatusID, Integer companyId)throws Exception;

	public BatteryHistory getBatteryMountCreatedBetweenDates(String startDate, String endDate, Integer companyId) throws Exception;
	
	public List<BatteryHistoryDto> getBatteryConsumptionList(ValueObject object)throws Exception;
}
