package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleGPSData;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONObject;

public interface IVehicleGPSDataService {

	public List<VehicleGPSData>  getVehicleGPSDataInDateRange(Timestamp fromDate, Timestamp toDate, String gpsDeviceId) throws Exception;
	
	public JSONObject  getVehicleGPSDataInDateRange(Timestamp fromDate, Timestamp toDate, Vehicle vehicle, HashMap<String, Object> configuration) throws Exception;
	
	public JSONObject  getVehicleGPSDataInDateRange(Timestamp fromDate, Timestamp toDate, VehicleDto vehicle, HashMap<String, Object> configuration) throws Exception;

	public ValueObject getITSGatewayUsageKM(ValueObject valueObject) throws Exception;

	public ValueObject getTrackNowRunKM(ValueObject valueObject) throws Exception;
	
	public ValueObject getFleetexRunKM(ValueObject valueObject) throws Exception;

	public ValueObject getEicherRunKM(ValueObject valueObject) throws Exception;
	
}
