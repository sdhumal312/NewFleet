package org.fleetopgroup.persistence.bl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.fleetopgroup.persistence.model.BusBookingDetails;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Component;

@Component
public class BusBookingDetailsBL {
		
	SimpleDateFormat	dateFormat	= new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
	
	public BusBookingDetails getBusBookingDetailsDto(ValueObject	valueObject) throws Exception{
		BusBookingDetails		busBookingDetails		= null;
		try {
			busBookingDetails	= new BusBookingDetails();
			
			busBookingDetails.setBusBookingNumber(valueObject.getLong("busBookingNumber", 0));
			busBookingDetails.setBookingRefNumber(valueObject.getString("bookingRefNumber"));
			if(valueObject.getString("busBookingDate", null) != null)
				busBookingDetails.setBusBookingDate(dateFormat.parse(valueObject.getString("busBookingDate", null)));
			busBookingDetails.setPartyGSTNo(valueObject.getString("partyGSTNo"));
			busBookingDetails.setPartyId(valueObject.getLong("partyId"));
			busBookingDetails.setPartyMobileNumber(valueObject.getString(""));
			busBookingDetails.setPartyMobileNumber(valueObject.getString("partyMobileNumber"));
			busBookingDetails.setPartyAddress(valueObject.getString("partyAddress"));
			busBookingDetails.setReportToName(valueObject.getString("reportToName"));
			busBookingDetails.setReportToMobileNumber(valueObject.getString("reportToMobileNumber"));
			busBookingDetails.setBillingAddress(valueObject.getString("billingAddress"));
			busBookingDetails.setVehicleTypeId(valueObject.getLong("vehicleTypeId",0));
			busBookingDetails.setPlaceOfVisit(valueObject.getString("placeOfVisit"));
			busBookingDetails.setPickUpAddress(valueObject.getString("pickUpAddress"));
			busBookingDetails.setDropAddress(valueObject.getString("dropAddress"));
			busBookingDetails.setRate(valueObject.getDouble("rate", 0));
			busBookingDetails.setHireAmount(valueObject.getDouble("hireAmount", 0));
			busBookingDetails.setRemark(valueObject.getString("remark"));
			
			busBookingDetails.setBookedBy(valueObject.getString("bookedBy"));
			busBookingDetails.setCompanyId(valueObject.getInt("companyId",0));
			busBookingDetails.setCreatedById(valueObject.getLong("userId", 0));
			busBookingDetails.setLastModifiedById(valueObject.getLong("userId", 0));
			busBookingDetails.setCreatedOn(new Date());
			busBookingDetails.setLastModifiedOn(new Date());
			String startTime	= "00:00";
			if(valueObject.getString("startTime", null) != null)
				startTime	= valueObject.getString("startTime");
			String endTime	= "00:00";
			if(valueObject.getString("endTime", null) != null)
				endTime	= valueObject.getString("endTime");
			
			
			Date	tripStartDate	=  DateTimeUtility.getDateTimeFromDateTimeString(valueObject.getString("tripStartDate"), startTime);
			Date	tripEndDate		=  DateTimeUtility.getDateTimeFromDateTimeString(valueObject.getString("tripEndDate"), endTime);
			
			busBookingDetails.setTripStartDateTime(tripStartDate);
			busBookingDetails.setTripEndDateTime(tripEndDate);
			
			return busBookingDetails;
		} catch (Exception e) {
			throw e;
		}finally {
			busBookingDetails	= null;
		}
	}
}
