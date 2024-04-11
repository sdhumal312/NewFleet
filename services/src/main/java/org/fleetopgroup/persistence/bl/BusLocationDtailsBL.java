package org.fleetopgroup.persistence.bl;


import org.fleetopgroup.persistence.model.BusLocation;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Component;

@Component
public class BusLocationDtailsBL {
	
	public BusLocation busLocationDto(ValueObject vo) throws Exception {
		BusLocation		busLocation		= null;
		
		try {
			busLocation = new BusLocation();
			
			busLocation.setBusBookingDate(DateTimeUtility.getTimeStamp(vo.getString("busBookingDate")));
			busLocation.setVehicleNumberMasterId(vo.getLong("vehicleId"));
			busLocation.setDriverId(vo.getLong("driveId"));
			busLocation.setSourceLocationId(vo.getLong("sourceLocationId"));
			busLocation.setDestinationLocationId(vo.getLong("destinationLocationId"));
			
			return busLocation;
		} catch (Exception e) {
			throw e;
		}
	}
}
