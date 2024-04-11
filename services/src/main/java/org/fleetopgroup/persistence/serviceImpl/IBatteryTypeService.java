package org.fleetopgroup.persistence.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.BatteryTypeDto;
import org.fleetopgroup.persistence.model.BatteryType;
import org.springframework.stereotype.Service;

@Service
public interface IBatteryTypeService {

	public List<BatteryType>	findAll();
	
	public void save(BatteryType	batteryType) throws Exception;
	
	public List<BatteryType> validateType(String type, Long manufactureId, String partNumber, Integer warranty) throws Exception;
	
	public List<BatteryTypeDto>	findAllBatteryType(Integer companyId) throws Exception;
	
	public List<BatteryType> getBatteryType(Long manufactureId) throws Exception;
	
	
	public List<BatteryType> validateBatteryType(String batteryType) throws Exception;
	
	public BatteryType getBatteryTypeByID(Long batteryTypeId);
	
	public BatteryTypeDto getBatteryTypeDto(Long batteryTypeId) throws Exception;

	/*public HashMap<String, Object> getBatteryTypeConfiguration(Integer company_id, int driverConfigurationConfig);*/

	public HashMap<String, Object> getBatteryTypeConfiguration(Integer companyId, Integer filter) throws Exception;
}
