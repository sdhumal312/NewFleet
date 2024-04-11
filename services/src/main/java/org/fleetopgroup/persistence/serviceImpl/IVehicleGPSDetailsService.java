package org.fleetopgroup.persistence.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleGPSData;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IVehicleGPSDetailsService {

	ValueObject		getVehicleGPSDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject		getVehicleGPSDetailsByVehicleId(String gpsId, HashMap<String, Object>  configuration) throws Exception;
	
	ValueObject		getVehicleGPSDetailsAtSpecifiedTime(ValueObject	valueObject) throws Exception;
	
	ValueObject		getTotalDistanceTraveledBetweenTwoDateTime(ValueObject	valueObject) throws Exception;
	
	Double			getVehicleGpsUsageKM(HashMap<String, Object> configuration, ValueObject valueObject) throws Exception;
	
	public void		saveVehicleGPSDataIntangles(ValueObject 	valueObject) throws Exception;
	
	ValueObject		saveVehicleGPSDataIdeagami(ValueObject 	valueObject) throws Exception;
	
	ValueObject		getVehicleGPSData(ValueObject	valueObject) throws Exception;
	
	public ValueObject getVehicleGPSDataAtSpecifiedTime(ValueObject valueObject) throws Exception;
	
	public VehicleGPSData getVehicleGPSDataByNumber(String vehicleNumber) throws Exception;
	
	public Double	getGPSRunKMBetweenTwoDates(Integer vid, String fromDate, String toDate, Integer companyId) throws Exception;
	
	public List<VehicleGPSData>  getVehicleGPSDataList(String vehicleNumber, String fromDate, String toDate) throws Exception;
	
	public ValueObject	getVehicleGPSByVendorProviderAtSpecifiedTime(ValueObject	valueObject, Vehicle vehicle) throws Exception;
}
