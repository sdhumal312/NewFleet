package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.BusBookingDetailsDto;
import org.fleetopgroup.persistence.model.BusBookingDetails;
import org.springframework.data.domain.Page;

public interface IBusBookingDetailsDao {

	Page<BusBookingDetails> getDeployment_Page_BusBookingDetails(Integer pageNumber, Integer companyId) throws Exception;
	
	public List<BusBookingDetailsDto> getPageWiseBusBookingDetails(Integer pageNumber, Integer companyId) throws Exception;
	
	public BusBookingDetailsDto getBusBookingDetails(Long busBookingDetailsId) throws Exception ;
	
	public BusBookingDetailsDto getBusBookingDetailsByTripSheetId(Long tripSheetId) throws Exception;
	
	public List<BusBookingDetailsDto> getBusBookingCalendarDetails(BusBookingDetailsDto busBookingDetailsDto) throws Exception;
	
	public List<BusBookingDetailsDto> getDateWiseCount(BusBookingDetailsDto busBookingDetailsDto) throws Exception;
	
	public List<BusBookingDetailsDto> getVehicleTypeWiseCount(BusBookingDetailsDto busBookingDetailsDto) throws Exception;
	
	public List<BusBookingDetailsDto> getVehicleTypeWiseList (BusBookingDetailsDto busBookingDetailsDto) throws Exception;
}
