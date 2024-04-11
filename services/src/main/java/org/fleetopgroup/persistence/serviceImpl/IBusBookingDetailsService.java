package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.dto.BusBookingDetailsDto;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IBusBookingDetailsService {
	
	ValueObject	getBusBookingDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject	saveBusBookingDetails(ValueObject	valueObject) throws Exception;
	
	public ValueObject deleteBusBookingDetails(ValueObject valueObject) throws Exception ;
	
	public BusBookingDetailsDto getBusBookingDetails(Long busBookingDetailsId)throws Exception;
	
	public void updateTripSheetNumber(Long tripSheetId, Long busBookingDetailsId) throws Exception;
	
	public BusBookingDetailsDto getBusBookingDetailsByTripSheetId(Long tripSheetId)throws Exception;
	
	public ValueObject getShowBusBookingDetails(ValueObject valueObject) throws Exception ;
	
	public ValueObject getBusBookingCalendarDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getBusBookingListOftheDay(ValueObject valueObject) throws Exception;
	
	public ValueObject getVehicleTypeWiseBusBookingList(ValueObject valueObject) throws Exception;
	
	public ValueObject getMonthlyVehicleWiseCount(ValueObject valueObject) throws Exception;
	
}
