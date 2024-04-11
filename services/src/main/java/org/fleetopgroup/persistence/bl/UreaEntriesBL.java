package org.fleetopgroup.persistence.bl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.UreaEntriesDto;
import org.fleetopgroup.persistence.model.UreaEntries;
import org.fleetopgroup.persistence.model.UreaEntriesHistory;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Controller;

@Controller
public class UreaEntriesBL {
	
	SimpleDateFormat		format	= new SimpleDateFormat("dd-MM-yyyy");
	
	SimpleDateFormat		dateFormat	= null;
	
	DecimalFormat 			toFixedTwo	= new DecimalFormat("#.##");
	
	public UreaEntries getUreaEntriesDTO(ValueObject	valueObject) throws Exception{
		UreaEntries			ureaEntries			= null;
		CustomUserDetails	userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			ureaEntries	= new UreaEntries();
			
			ureaEntries.setUreaEntriesNumber(valueObject.getLong("ureaEntriesNumber"));
			ureaEntries.setVid(valueObject.getInt("vid"));
			
			String ureaDate = valueObject.getString("ureaDate");
			java.util.Date date = format.parse(ureaDate);
			java.sql.Date sqlUreaDate = new java.sql.Date(date.getTime());
			ureaEntries.setUreaDate(sqlUreaDate);
			
			ureaEntries.setUreaInvoiceToDetailsId(valueObject.getLong("ureaLocation", 0));
			ureaEntries.setUreaOdometer(valueObject.getDouble("ureaOdometer", 0));
			ureaEntries.setUreaOdometerOld(valueObject.getDouble("fuel_meter_old", 0));
			ureaEntries.setUreaLiters(valueObject.getDouble("ureaLiters", 0));
			ureaEntries.setUreaRate(valueObject.getDouble("ureaPrice", 0));
			ureaEntries.setDiscount(valueObject.getDouble("discount", 0));
			ureaEntries.setGst(valueObject.getDouble("gst", 0));
			ureaEntries.setUreaManufacturerId(valueObject.getLong("manufacturerId",0));
			ureaEntries.setReference(valueObject.getString("reference"));
			ureaEntries.setDriver_id(valueObject.getInt("DriverFuel", 0));
			ureaEntries.setSecDriverID(valueObject.getInt("Driver2Fuel", 0));
			ureaEntries.setCleanerID(valueObject.getInt("CleanerFuel",0));
			ureaEntries.setRouteID(valueObject.getInt("routeId", 0));
			ureaEntries.setComments(valueObject.getString("ureaComments"));
			ureaEntries.setCreated(new Date());
			ureaEntries.setLastupdated(new Date());
			ureaEntries.setCreatedById(userDetails.getId());
			ureaEntries.setLastModifiedById(userDetails.getId());
			ureaEntries.setCompanyId(userDetails.getCompany_id());
			ureaEntries.setLocationId(valueObject.getInt("wareHouseLocation", 0));
			ureaEntries.setUreaAmount(valueObject.getDouble("ureaAmount", 0));
			ureaEntries.setMeterWorkingStatus(valueObject.getBoolean("meterWorkingStatus", false));
			ureaEntries.setFilledBy(valueObject.getString("filledBy"));
			ureaEntries.setFilledLocationId(valueObject.getInt("subLocationId", 0));
			
			return ureaEntries;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public UreaEntries updateUreaEntriesDTO(ValueObject	valueObject) throws Exception{
		UreaEntries			ureaEntries			= null;
		CustomUserDetails	userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			ureaEntries	= new UreaEntries();
			
			ureaEntries.setUreaEntriesId(valueObject.getLong("ureaEntriesInvoiceId"));
			ureaEntries.setUreaEntriesNumber(valueObject.getLong("ureaEntriesNumber"));
			ureaEntries.setVid(valueObject.getInt("vid"));
			
			if(valueObject.getLong("ureaInvoiceToDetailsId") == valueObject.getLong("newUreaInvoiceToDetailsId")) {
				ureaEntries.setUreaInvoiceToDetailsId(valueObject.getLong("ureaInvoiceToDetailsId"));
			} else {
				ureaEntries.setUreaInvoiceToDetailsId(valueObject.getLong("newUreaInvoiceToDetailsId"));
			}
			
			ureaEntries.setUreaDate(format.parse(valueObject.getString("ureaDate")));
			ureaEntries.setUreaOdometer(valueObject.getDouble("ureaOdometer", 0));
			ureaEntries.setUreaOdometerOld(valueObject.getDouble("ureaOdometerOld", 0));
			ureaEntries.setUreaLiters(valueObject.getDouble("ureaLiters", 0));
			ureaEntries.setUreaRate(valueObject.getDouble("ureaPrice", 0));
			ureaEntries.setDiscount(valueObject.getDouble("discount", 0));
			ureaEntries.setGst(valueObject.getDouble("gst", 0));
			ureaEntries.setUreaManufacturerId(valueObject.getLong("manufacturerId"));
			ureaEntries.setReference(valueObject.getString("reference"));
			ureaEntries.setDriver_id(valueObject.getInt("DriverFuel", 0));
			ureaEntries.setSecDriverID(valueObject.getInt("Driver2Fuel", 0));
			ureaEntries.setCleanerID(valueObject.getInt("CleanerFuel",0));
			ureaEntries.setRouteID(valueObject.getInt("routeId", 0));
			ureaEntries.setComments(valueObject.getString("ureaComments"));
			//ureaEntries.setCreated(new Date());
			ureaEntries.setLastupdated(new Date());
			ureaEntries.setCreatedById(userDetails.getId());
			ureaEntries.setLastModifiedById(userDetails.getId());
			ureaEntries.setCompanyId(userDetails.getCompany_id());
			ureaEntries.setLocationId(valueObject.getInt("wareHouseLocation", 0));
			ureaEntries.setUreaAmount(valueObject.getDouble("ureaAmount", 0));
			ureaEntries.setMeterWorkingStatus(valueObject.getBoolean("meterWorkingStatus", false));			
			return ureaEntries;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public UreaEntriesHistory updateUreaEntriesHistory(UreaEntries ureaEntries) throws Exception {
		UreaEntriesHistory      history     = null;
		try {
			
			history  = new UreaEntriesHistory();
			
			history.setUreaEntriesId(ureaEntries.getUreaEntriesId());
			history.setUreaEntriesNumber(ureaEntries.getUreaEntriesNumber());
			history.setVid(ureaEntries.getVid());
			history.setTripSheetId(ureaEntries.getTripSheetId());
			history.setUreaInvoiceToDetailsId(ureaEntries.getUreaInvoiceToDetailsId());
			history.setLocationId(ureaEntries.getLocationId());
			history.setUreaDate(ureaEntries.getUreaDate());
			history.setUreaOdometer(ureaEntries.getUreaOdometer());
			history.setUreaOdometerOld(ureaEntries.getUreaOdometerOld());
			history.setUreaLiters(ureaEntries.getUreaLiters());
			history.setUreaRate(ureaEntries.getUreaRate());
			history.setDiscount(ureaEntries.getDiscount());
			history.setGst(ureaEntries.getGst());
			history.setUreaAmount(ureaEntries.getUreaAmount());
			history.setUreaManufacturerId(ureaEntries.getUreaManufacturerId());
			history.setReference(ureaEntries.getReference());
			history.setDriver_id(ureaEntries.getDriver_id());
			history.setSecDriverID(ureaEntries.getSecDriverID());
			history.setCleanerID(ureaEntries.getCleanerID());
			history.setRouteID(ureaEntries.getRouteID());
			history.setComments(ureaEntries.getComments());
			history.setCompanyId(ureaEntries.getCompanyId());
			history.setCreatedById(ureaEntries.getCreatedById());
			history.setMarkForDelete(false);
			history.setCreated(ureaEntries.getCreated());
			
			return history;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Double prepareTotal_Tripsheet_Urea_details(List<UreaEntriesDto> ureaList) {

		Double totalUrea = 0.0;
		if (ureaList != null && !ureaList.isEmpty()) {
			for (UreaEntriesDto ureaBean : ureaList) {
				if (ureaBean.getUreaLiters() != null) {
					totalUrea += ureaBean.getUreaLiters();
				}
			}
		}
		return Double.parseDouble(toFixedTwo.format(totalUrea));
	}
	
	public Double prepareTotal_Tripsheet_Urea_Amount(List<UreaEntriesDto> ureaList) {
		
		Double totalUreaAmnt = 0.0;
		if (ureaList != null && !ureaList.isEmpty()) {
			for (UreaEntriesDto ureaBean : ureaList) {
				if (ureaBean.getUreaAmount() != null) {
					totalUreaAmnt += ureaBean.getUreaAmount();
				}
			}
		}
		return Double.parseDouble(toFixedTwo.format(totalUreaAmnt));
	}
}
