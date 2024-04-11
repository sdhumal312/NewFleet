package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.BatteryCapacity;
import org.springframework.stereotype.Service;

@Service
public interface IBatteryCapacityService {
	
	List<BatteryCapacity>  getBatteryCapacityList() throws Exception;
	
	void save(BatteryCapacity batteryCapacity) throws Exception;
	
	List<BatteryCapacity>  validateBatteryCapacity(String capacity) throws Exception;
	
	List<BatteryCapacity>  searchBatteryCapacity(String term) throws Exception;
}
