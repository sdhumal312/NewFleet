package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.BatteryManufacturer;
import org.springframework.stereotype.Service;

@Service
public interface IBatteryManufacturerService {

	public List<BatteryManufacturer> findAll() throws Exception;
	
	public List<BatteryManufacturer> validateBatteryManufacturer(String name) throws Exception;
	
	BatteryManufacturer getBatteryManufactureByID(Long batteryManufacturerId);
	
	long	countBatteryManufactureByCompanyId(Integer company_Id) throws Exception;
	
	public void save(BatteryManufacturer 	batteryManufacturer) throws Exception;
	
	public List<BatteryManufacturer> getBatteryManufacturerList(String name) throws Exception;
}
