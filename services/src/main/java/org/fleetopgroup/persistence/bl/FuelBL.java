package org.fleetopgroup.persistence.bl;
/**
 * @author fleetop
 *
 *
 *
 */

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.constant.CompanyConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.VehicleFuelType;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.IntangleFuelEntryDetails;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

@Controller
public class FuelBL {
	

	public FuelBL() {
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat2 = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY_HH_MM_AA);
	SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat timeFormat	 = new SimpleDateFormat("hh:mm");
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	

	/* list of Fuel get and Display */
	public List<FuelDto> prepareListofFuelBean(List<FuelDto> fuelList) {
		List<FuelDto> beans = null;
		if (fuelList != null && !fuelList.isEmpty()) {
			beans = new ArrayList<FuelDto>();
			FuelDto fuel = null;

			for (FuelDto fuelBean : fuelList) {

				fuel = new FuelDto();
				fuel.setFuel_id(fuelBean.getFuel_id());
				fuel.setFuel_Number(fuelBean.getFuel_Number());
				fuel.setFuel_TripsheetID(fuelBean.getFuel_TripsheetID());

				fuel.setVid(fuelBean.getVid());
				// get Vehicle id to Vehicle name
				fuel.setVehicle_registration(fuelBean.getVehicle_registration());
				fuel.setVehicle_Ownership(fuelBean.getVehicle_Ownership());

				if (fuelBean.getFuel_date() != null) {
					fuel.setFuel_date(dateFormat_Name.format(fuelBean.getFuel_date()));
				}
				fuel.setFuel_meter(fuelBean.getFuel_meter());
				fuel.setFuel_meter_old(fuelBean.getFuel_meter_old());
				fuel.setFuel_meter_attributes(fuelBean.getFuel_meter_attributes());
				fuel.setFuel_type(fuelBean.getFuel_type());
				fuel.setFuel_liters(fuelBean.getFuel_liters());
				fuel.setFuel_price(fuelBean.getFuel_price());

				fuel.setVehicle_group(fuelBean.getVehicle_group());
				fuel.setDriver_id(fuelBean.getDriver_id());
				fuel.setDriver_name(fuelBean.getDriver_name());
				fuel.setDriver_empnumber(fuelBean.getDriver_empnumber());

				fuel.setVendor_id(fuelBean.getVendor_id());
				fuel.setVendor_name(fuelBean.getVendor_name());
				fuel.setVendor_location(fuelBean.getVendor_location());
				fuel.setFuel_payment(fuelBean.getFuel_payment());

				fuel.setFuel_reference(fuelBean.getFuel_reference());
				fuel.setFuel_personal(fuelBean.getFuel_personal());
				fuel.setFuel_tank(fuelBean.getFuel_tank());
				fuel.setFuel_tank_partial(fuelBean.getFuel_tank_partial());

				fuel.setFuel_comments(fuelBean.getFuel_comments());
				fuel.setFuel_usage(fuelBean.getFuel_usage());
				fuel.setFuel_amount(fuelBean.getFuel_amount());
				fuel.setFuel_kml(fuelBean.getFuel_kml());
				fuel.setFuel_cost(fuelBean.getFuel_cost());

				fuel.setFuel_vendor_paymode(fuelBean.getFuel_vendor_paymode());

				fuel.setFuel_document(fuelBean.isFuel_document());
				fuel.setFuel_document_id(fuelBean.getFuel_document_id());

				beans.add(fuel);
			}
		}
		return beans;
	}

	public List<FuelDto> prepareListofFuel(List<FuelDto> fuelList) throws Exception {
		List<FuelDto> beans = null;
		if (fuelList != null && !fuelList.isEmpty()) {
			beans = new ArrayList<FuelDto>();
			FuelDto fuel = null;

			for (FuelDto fuelBean : fuelList) {

				fuel = new FuelDto();
				fuel.setFuel_id(fuelBean.getFuel_id());
				fuel.setFuel_Number(fuelBean.getFuel_Number());
				fuel.setFuel_TripsheetID(fuelBean.getFuel_TripsheetID());
				fuel.setFuel_TripsheetNumber(fuelBean.getFuel_TripsheetNumber());

				fuel.setVid(fuelBean.getVid());
				// get Vehicle id to Vehicle name
				fuel.setVehicle_registration(fuelBean.getVehicle_registration());
				fuel.setVehicle_Ownership(fuelBean.getVehicle_Ownership());

				if (fuelBean.getFuel_D_date() != null) {
					fuel.setFuel_date(dateFormat_Name.format(fuelBean.getFuel_D_date()));
					fuel.setFuelDateTimeStr(dateFormat2.format(fuelBean.getFuel_D_date()));
				}
				fuel.setFuel_meter(fuelBean.getFuel_meter());
				fuel.setFuel_meter_old(fuelBean.getFuel_meter_old());
				fuel.setFuel_meter_attributes(fuelBean.getFuel_meter_attributes());
				fuel.setFuel_type(fuelBean.getFuel_type());
				fuel.setFuel_liters(fuelBean.getFuel_liters());
				fuel.setFuel_price(fuelBean.getFuel_price());

				fuel.setVehicle_group(fuelBean.getVehicle_group());
				fuel.setDriver_id(fuelBean.getDriver_id());
				fuel.setDriver_name(fuelBean.getDriver_name());
				fuel.setDriver_empnumber(fuelBean.getDriver_empnumber());

				fuel.setVendor_id(fuelBean.getVendor_id());
				fuel.setVendor_name(fuelBean.getVendor_name());
				fuel.setVendor_location(fuelBean.getVendor_location());
				fuel.setFuel_payment(fuelBean.getFuel_payment());

				fuel.setFuel_reference(fuelBean.getFuel_reference());
				fuel.setFuel_personal(fuelBean.getFuel_personal());
				fuel.setFuel_tank(fuelBean.getFuel_tank());
				fuel.setFuel_tank_partial(fuelBean.getFuel_tank_partial());

				fuel.setFuel_comments(fuelBean.getFuel_comments());
				fuel.setFuel_usage(fuelBean.getFuel_usage());
				fuel.setFuel_amount(Double.parseDouble(toFixedTwo.format(fuelBean.getFuel_amount())));
				fuel.setPaidAmount(Double.parseDouble(toFixedTwo.format(fuelBean.getPaidAmount())));
				fuel.setBalanceAmount(Double.parseDouble(toFixedTwo.format(fuelBean.getBalanceAmount())));
				fuel.setFuel_kml(fuelBean.getFuel_kml());
				if(fuelBean.getFuel_cost() != null) {
				fuel.setFuel_cost(Double.parseDouble(toFixedTwo.format(fuelBean.getFuel_cost())));
					}else {
							fuel.setFuel_cost(0.0);
				}
				fuel.setFuel_vendor_paymode(fuelBean.getFuel_vendor_paymode());
				fuel.setFuel_vendor_paymentdate(fuelBean.getFuel_vendor_paymentdate());
				fuel.setFuel_document(fuelBean.isFuel_document());
				fuel.setFuel_document_id(fuelBean.getFuel_document_id());
				fuel.setFuel_reference(fuelBean.getFuel_reference());   //latest
				fuel.setPaidAmount(Double.parseDouble(toFixedTwo.format(fuelBean.getPaidAmount())) );
				fuel.setGpsUsageKM(fuelBean.getGpsUsageKM());
				
				if(fuel.getGpsUsageKM() != null && fuel.getGpsUsageKM() > 0) {
					fuel.setGpKMPL(fuel.getGpsUsageKM()/ fuel.getFuel_liters());
				}
				
			     if(fuelBean.getFuelPriceDiff() != null) {
			    	 fuel.setFuelPriceDiff(Double.parseDouble(toFixedTwo.format(fuelBean.getFuelPriceDiff())));
			     }
			     fuel.setVendorApprovedAmount(Double.parseDouble(toFixedTwo.format(fuelBean.getVendorApprovedAmount())));
			     fuel.setFuelPriceDiff(fuelBean.getFuelPriceDiff());
			     fuel.setFirstDriverFatherName(fuelBean.getFirstDriverFatherName());
			     fuel.setFirstDriverLastName(fuelBean.getFirstDriverLastName());
			     fuel.setCreated(fuelBean.getCreated());
				beans.add(fuel);
			}
		}
		return beans;
	}

	
	
	public FuelDto getFuelId(Fuel fuelBean) {

		FuelDto fuel = new FuelDto();
		fuel.setFuel_id(fuelBean.getFuel_id());
		fuel.setFuel_Number(fuelBean.getFuel_Number());

		fuel.setFuel_TripsheetID(fuelBean.getFuel_TripsheetID());

		fuel.setVid(fuelBean.getVid());
		// get Vehicle id to Vehicle name
		//fuel.setVehicle_registration(fuelBean.getVehicle_registration());
		if (fuelBean.getFuel_date() != null) {
			fuel.setFuel_date(dateFormat.format(fuelBean.getFuel_date()));
		}
		//fuel.setVehicle_Ownership(fuelBean.getVehicle_Ownership());
		fuel.setFuel_meter(fuelBean.getFuel_meter());
		fuel.setFuel_meter_old(fuelBean.getFuel_meter_old());
		fuel.setFuel_meter_attributes(fuelBean.getFuel_meter_attributes());
		//fuel.setFuel_type(fuelBean.getFuel_type());
		fuel.setFuel_liters(fuelBean.getFuel_liters());
		fuel.setFuel_price(fuelBean.getFuel_price());

		//fuel.setVehicle_group(fuelBean.getVehicle_group());
		//fuel.setVehicleGroupId(fuelBean.getVehicleGroupId());
		fuel.setDriver_id(fuelBean.getDriver_id());
		//fuel.setDriver_name(fuelBean.getDriver_name());
		//fuel.setDriver_empnumber(fuelBean.getDriver_empnumber());

		fuel.setVendor_id(fuelBean.getVendor_id());
		//fuel.setVendor_name(fuelBean.getVendor_name());
		//fuel.setVendor_location(fuelBean.getVendor_location());
		//fuel.setFuel_payment(fuelBean.getFuel_payment());
		//fuel.setFuel_vendor_paymode(fuelBean.getFuel_vendor_paymode());
		fuel.setFuel_vendor_paymentdate(fuelBean.getFuel_vendor_paymentdate());

		fuel.setFuel_reference(fuelBean.getFuel_reference());
		fuel.setFuel_personal(fuelBean.getFuel_personal());
		fuel.setFuel_tank(fuelBean.getFuel_tank());
		fuel.setFuel_tank_partial(fuelBean.getFuel_tank_partial());

		fuel.setFuel_comments(fuelBean.getFuel_comments());
		fuel.setFuel_usage(fuelBean.getFuel_usage());
		fuel.setFuel_amount(fuelBean.getFuel_amount());
		fuel.setFuel_kml(fuelBean.getFuel_kml());
		fuel.setFuel_cost(fuelBean.getFuel_cost());

		fuel.setFuel_reference(fuelBean.getFuel_reference());
		fuel.setFuel_personal(fuelBean.getFuel_personal());

		fuel.setFuel_document(fuelBean.isFuel_document());
		fuel.setFuel_document_id(fuelBean.getFuel_document_id());

		// Create and Last updated Display
		//fuel.setCreatedBy(fuelBean.getCreatedBy());
		if (fuelBean.getCreated() != null) {
			fuel.setCreated(CreatedDateTime.format(fuelBean.getCreated()));
		}
		//fuel.setLastModifiedBy(fuelBean.getLastModifiedBy());
		if (fuelBean.getLastupdated() != null) {
			fuel.setLastupdated(CreatedDateTime.format(fuelBean.getLastupdated()));
		}

		return fuel;
	}
	
	public FuelDto getFuelId(FuelDto fuelBean) {
		FuelDto fuel = new FuelDto();
		try {
			if(fuelBean != null)
				fuel.setFuel_id(fuelBean.getFuel_id());
				fuel.setFuel_Number(fuelBean.getFuel_Number());
				if(fuelBean.getFuel_TripsheetID() != null && fuelBean.getFuel_TripsheetID() > 0) {
					fuel.setFuel_TripsheetID(fuelBean.getFuel_TripsheetID());
					fuel.setFuel_TripsheetNumber(fuelBean.getFuel_TripsheetNumber());
				}else {
					fuel.setFuel_TripsheetID((long) 0);
					fuel.setFuel_TripsheetNumber((long) 0);
				}
	
				fuel.setVid(fuelBean.getVid());
				fuel.setVehicle_registration(fuelBean.getVehicle_registration());
				if (fuelBean.getFuel_D_date() != null) {
					fuel.setFuel_date(dateFormat.format(fuelBean.getFuel_D_date()));
				}
				fuel.setFuel_D_date(fuelBean.getFuel_D_date());
				fuel.setVehicle_Ownership(fuelBean.getVehicle_Ownership());
				fuel.setFuel_meter(fuelBean.getFuel_meter());
				fuel.setFuel_meter_old(fuelBean.getFuel_meter_old());
				fuel.setFuel_meter_attributes(fuelBean.getFuel_meter_attributes());
				if(fuelBean.getFuel_meter_attributes() == 1) {
					fuel.setMeter_attributes_str("Void Fuel Entry");
				}
				fuel.setFuel_type(fuelBean.getFuel_type());
				fuel.setFuel_liters(fuelBean.getFuel_liters());
				fuel.setFuel_price(fuelBean.getFuel_price());
				fuel.setVehicle_group(fuelBean.getVehicle_group());
				fuel.setVehicleGroupId(fuelBean.getVehicleGroupId());
				fuel.setDriver_id(fuelBean.getDriver_id());
				fuel.setDriver_name(fuelBean.getDriver_name());
				fuel.setDriver_empnumber(fuelBean.getDriver_empnumber());
				fuel.setSecDriverID(fuelBean.getSecDriverID());
				fuel.setFuelSecDriverName(fuelBean.getFuelSecDriverName());
				fuel.setCleanerID(fuelBean.getCleanerID());
				fuel.setFuelCleanerName(fuelBean.getFuelCleanerName());
				fuel.setRouteID(fuelBean.getRouteID());
				fuel.setFuelRouteName(fuelBean.getFuelRouteName());
				fuel.setVendor_id(fuelBean.getVendor_id());
				fuel.setVendor_name(fuelBean.getVendor_name());
				fuel.setVendor_location(fuelBean.getVendor_location());
				fuel.setFuel_payment(fuelBean.getFuel_payment());
				fuel.setFuel_vendor_paymode(fuelBean.getFuel_vendor_paymode());
				fuel.setFuel_vendor_paymentdate(fuelBean.getFuel_vendor_paymentdate());
				fuel.setFuelVendorPaymentDate(fuelBean.getFuelVendorPaymentDate());
				fuel.setFuel_reference(fuelBean.getFuel_reference());
				fuel.setFuel_personal(fuelBean.getFuel_personal());
				fuel.setFuel_tank(fuelBean.getFuel_tank());
				fuel.setFuel_tank_partial(fuelBean.getFuel_tank_partial());
	
				fuel.setFuel_comments(fuelBean.getFuel_comments());
				fuel.setFuel_usage(fuelBean.getFuel_usage());
				fuel.setFuel_amount(fuelBean.getFuel_amount());
				fuel.setFuel_kml(fuelBean.getFuel_kml());
				fuel.setFuel_cost(fuelBean.getFuel_cost());
				fuel.setFuel_reference(fuelBean.getFuel_reference());
				fuel.setFuel_personal(fuelBean.getFuel_personal());
				fuel.setFuel_document(fuelBean.isFuel_document());
				fuel.setFuel_document_id(fuelBean.getFuel_document_id());
				if(fuelBean.getFuelDateTime() != null) {
					fuel.setFuelDateTimeStr(dateFormat2.format(fuelBean.getFuelDateTime()));
					fuel.setFuelDateTimeStr2(dateFormat3.format(fuelBean.getFuelDateTime()));
					fuel.setFuel_date(dateFormat2.format(fuelBean.getFuelDateTime()));
					fuel.setFuelTime(DateTimeUtility.getTimeFromTimeStamp(new Timestamp(fuelBean.getFuelDateTime().getTime()),  DateTimeUtility.KK_MM));
				}
				// Create and Last updated Display
				fuel.setCreatedBy(fuelBean.getCreatedBy());
				if (fuelBean.getCreatedOn() != null) {
					fuel.setCreated(CreatedDateTime.format(fuelBean.getCreatedOn()));
				}
				fuel.setLastModifiedBy(fuelBean.getLastModifiedBy());
				if (fuelBean.getLastupdatedOn() != null) {
					fuel.setLastupdated(CreatedDateTime.format(fuelBean.getLastupdatedOn()));
				}
				fuel.setVehicle_OwnershipId(fuelBean.getVehicle_OwnershipId());
				fuel.setFuelTypeId(fuelBean.getFuelTypeId());
				fuel.setPaymentTypeId(fuelBean.getPaymentTypeId());
				fuel.setFuel_vendor_paymodeId(fuelBean.getFuel_vendor_paymodeId());
				fuel.setCreatedById(fuelBean.getCreatedById());
				fuel.setAutoVendor(fuelBean.isAutoVendor());
				fuel.setGpsOdometer(fuelBean.getGpsOdometer());
				fuel.setTallyCompanyId(fuelBean.getTallyCompanyId());
				fuel.setTallyCompanyName(fuelBean.getTallyCompanyName());
				fuel.setGpsUsageKM(fuelBean.getGpsUsageKM());
				fuel.setVehicle_ExpectedOdameter(fuelBean.getVehicle_ExpectedOdameter());
				fuel.setOwnPetrolPump(fuelBean.getOwnPetrolPump());
				fuel.setFirstDriverFatherName(fuelBean.getFirstDriverFatherName());
				fuel.setFirstDriverLastName(fuelBean.getFirstDriverLastName());
				fuel.setSecDriverFatherName(fuelBean.getSecDriverFatherName());
				fuel.setSecDriverLastName(fuelBean.getSecDriverLastName());
				fuel.setFuelInvoiceId(fuelBean.getFuelInvoiceId());
			
				fuel.setTripStutesId(fuelBean.getTripStutesId());
				fuel.setTripClosingKM(fuelBean.getTripClosingKM());
			return fuel;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}

	public List<FuelDto> Report_Vehicle_wise_Fuel_RangeFuelBean(List<Vehicle> VehicleReport, List<FuelDto> fuelReport,
			String fuelRange) {

		List<FuelDto> Fuelbeans = new ArrayList<FuelDto>();

		switch (fuelRange) {

		case "WITH_IN":

			// vehicle List checking to vid and expert mileage and
			// expert to
			// getying type
			if (VehicleReport != null && !VehicleReport.isEmpty()) {
				// vehicle for loop continue
				for (Vehicle vehicleBean : VehicleReport) {

					if (fuelReport != null && !fuelReport.isEmpty()) {

						FuelDto fuel = null;

						for (FuelDto fuelBean : fuelReport) {
							fuel = new FuelDto();

							// check vehicle for one vehicle If and Fuel
							// vid equel is true go inside
							// System.out.println(vehicleBean.getVid().equals(fuelBean.getVid()));
							if (vehicleBean.getVid().equals(fuelBean.getVid())) {

								// here check vehicle expectedMileage
								// from less then equel ( 4 <= 5) to
								// fuel kml true go inside
								if (vehicleBean.getVehicle_ExpectedMileage() <= fuelBean.getFuel_kml()) {

									// here check vehicle
									// expectedMileage_to to greater
									// then equel ( 3 >= 2) to fuel kml
									// true go inside
									// this checking with in Range
									if (vehicleBean.getVehicle_ExpectedMileage_to() >= fuelBean.getFuel_kml()) {

										fuel.setFuel_id(fuelBean.getFuel_id());
										fuel.setFuel_Number(fuelBean.getFuel_Number());

										fuel.setVid(fuelBean.getVid());
										// get Vehicle id to Vehicle
										// name
										fuel.setVehicle_registration(fuelBean.getVehicle_registration());
										fuel.setVehicle_Ownership(fuelBean.getVehicle_Ownership());
										if (fuelBean.getFuel_date() != null) {
											fuel.setFuel_date(dateFormat.format(fuelBean.getFuel_date()));
										}
										fuel.setFuel_meter(fuelBean.getFuel_meter());
										fuel.setFuel_meter_old(fuelBean.getFuel_meter_old());
										fuel.setFuel_meter_attributes(fuelBean.getFuel_meter_attributes());
										fuel.setFuel_type(fuelBean.getFuel_type());
										fuel.setFuel_liters(fuelBean.getFuel_liters());
										fuel.setFuel_price(fuelBean.getFuel_price());

										fuel.setVehicle_group(fuelBean.getVehicle_group());
										fuel.setDriver_id(fuelBean.getDriver_id());
										fuel.setDriver_name(fuelBean.getDriver_name());
										fuel.setDriver_empnumber(fuelBean.getDriver_empnumber());

										fuel.setVendor_id(fuelBean.getVendor_id());
										fuel.setVendor_name(fuelBean.getVendor_name());
										fuel.setVendor_location(fuelBean.getVendor_location());
										fuel.setFuel_payment(fuelBean.getFuel_payment());

										fuel.setFuel_reference(fuelBean.getFuel_reference());
										fuel.setFuel_personal(fuelBean.getFuel_personal());
										fuel.setFuel_tank(fuelBean.getFuel_tank());
										fuel.setFuel_tank_partial(fuelBean.getFuel_tank_partial());

										fuel.setFuel_comments(fuelBean.getFuel_comments());
										fuel.setFuel_usage(fuelBean.getFuel_usage());
										fuel.setFuel_amount(fuelBean.getFuel_amount());
										fuel.setFuel_kml(fuelBean.getFuel_kml());
										fuel.setFuel_cost(fuelBean.getFuel_cost());

										fuel.setFuel_vendor_paymode(fuelBean.getFuel_vendor_paymode());

										Fuelbeans.add(fuel);

									} // close less than If condition
								} // close greater then If condition

							} // close vehicle and fuel vid If condition

						} // close fuel for loop
					} // close fuelReport If condition
				} // close vehicle For loop
			} // close vehicle if condition

			break;

		case "OUT_OF":
			// vehicle List checking to vid and expert mileage and
			// expert to
			// getying type
			if (VehicleReport != null && !VehicleReport.isEmpty()) {
				// vehicle for loop continue
				for (Vehicle vehicleBean : VehicleReport) {

					if (fuelReport != null && !fuelReport.isEmpty()) {

						FuelDto fuel = null;

						for (FuelDto fuelBean : fuelReport) {
							fuel = new FuelDto();

							// check vehicle for one vehicle If and Fuel
							// vid equel is true go inside
							// System.out.println(vehicleBean.getVid().equals(fuelBean.getVid()));
							if (vehicleBean.getVid().equals(fuelBean.getVid())) {

								// here check vehicle expectedMileage
								// from less then equel ( 4 <= 5) to
								// fuel kml true go inside
								if (vehicleBean.getVehicle_ExpectedMileage() <= fuelBean.getFuel_kml()) {

									// here check vehicle
									// expectedMileage_to to greater
									// then equel ( 3 >= 2) to fuel kml
									// true go inside
									// this checking with in Range
									if (vehicleBean.getVehicle_ExpectedMileage_to() >= fuelBean.getFuel_kml()) {
										// vehicle with in Range
										// condition loop
									} else {

										// below vehicle BELOW Range
										// condition loop

										fuel.setFuel_id(fuelBean.getFuel_id());
										fuel.setFuel_Number(fuelBean.getFuel_Number());

										fuel.setVid(fuelBean.getVid());
										// get Vehicle id to Vehicle
										// name
										fuel.setVehicle_registration(fuelBean.getVehicle_registration());
										fuel.setVehicle_Ownership(fuelBean.getVehicle_Ownership());
										if (fuelBean.getFuel_date() != null) {
											fuel.setFuel_date(dateFormat.format(fuelBean.getFuel_date()));
										}
										fuel.setFuel_meter(fuelBean.getFuel_meter());
										fuel.setFuel_meter_old(fuelBean.getFuel_meter_old());
										fuel.setFuel_meter_attributes(fuelBean.getFuel_meter_attributes());
										fuel.setFuel_type(fuelBean.getFuel_type());
										fuel.setFuel_liters(fuelBean.getFuel_liters());
										fuel.setFuel_price(fuelBean.getFuel_price());

										fuel.setVehicle_group(fuelBean.getVehicle_group());
										fuel.setDriver_id(fuelBean.getDriver_id());
										fuel.setDriver_name(fuelBean.getDriver_name());
										fuel.setDriver_empnumber(fuelBean.getDriver_empnumber());

										fuel.setVendor_id(fuelBean.getVendor_id());
										fuel.setVendor_name(fuelBean.getVendor_name());
										fuel.setVendor_location(fuelBean.getVendor_location());
										fuel.setFuel_payment(fuelBean.getFuel_payment());

										fuel.setFuel_reference(fuelBean.getFuel_reference());
										fuel.setFuel_personal(fuelBean.getFuel_personal());
										fuel.setFuel_tank(fuelBean.getFuel_tank());
										fuel.setFuel_tank_partial(fuelBean.getFuel_tank_partial());

										fuel.setFuel_comments(fuelBean.getFuel_comments());
										fuel.setFuel_usage(fuelBean.getFuel_usage());
										fuel.setFuel_amount(fuelBean.getFuel_amount());
										fuel.setFuel_kml(fuelBean.getFuel_kml());
										fuel.setFuel_cost(fuelBean.getFuel_cost());

										fuel.setFuel_vendor_paymode(fuelBean.getFuel_vendor_paymode());

										Fuelbeans.add(fuel);

									} // close less than If condition
								} // close greater then If condition

							} // close vehicle and fuel vid If condition

						} // close fuel for loop
					} // close fuelReport If condition
				} // close vehicle For loop
			} // close vehicle if condition

			break;
		case "BELOW":

			// vehicle List checking to vid and expert mileage and
			// expert to
			// getying type
			if (VehicleReport != null && !VehicleReport.isEmpty()) {
				// vehicle for loop continue
				for (Vehicle vehicleBean : VehicleReport) {

					if (fuelReport != null && !fuelReport.isEmpty()) {

						FuelDto fuel = null;

						for (FuelDto fuelBean : fuelReport) {
							fuel = new FuelDto();

							// check vehicle for one vehicle If and Fuel
							// vid equel is true go inside
							// System.out.println(vehicleBean.getVid().equals(fuelBean.getVid()));
							if (vehicleBean.getVid().equals(fuelBean.getVid())) {

								// here check vehicle expectedMileage
								// from less then equel ( 4 <= 5) to
								// fuel kml true go inside
								if (vehicleBean.getVehicle_ExpectedMileage() <= fuelBean.getFuel_kml()) {
									// vehicle with in Range condition
									// loop
								} else {

									// below vehicle BELOW Range
									// condition loop

									fuel.setFuel_id(fuelBean.getFuel_id());
									fuel.setFuel_Number(fuelBean.getFuel_Number());

									fuel.setVid(fuelBean.getVid());
									// get Vehicle id to Vehicle name
									fuel.setVehicle_registration(fuelBean.getVehicle_registration());
									fuel.setVehicle_Ownership(fuelBean.getVehicle_Ownership());
									if (fuelBean.getFuel_date() != null) {
										fuel.setFuel_date(dateFormat.format(fuelBean.getFuel_date()));
									}
									fuel.setFuel_meter(fuelBean.getFuel_meter());
									fuel.setFuel_meter_old(fuelBean.getFuel_meter_old());
									fuel.setFuel_meter_attributes(fuelBean.getFuel_meter_attributes());
									fuel.setFuel_type(fuelBean.getFuel_type());
									fuel.setFuel_liters(fuelBean.getFuel_liters());
									fuel.setFuel_price(fuelBean.getFuel_price());

									fuel.setVehicle_group(fuelBean.getVehicle_group());
									fuel.setDriver_id(fuelBean.getDriver_id());
									fuel.setDriver_name(fuelBean.getDriver_name());
									fuel.setDriver_empnumber(fuelBean.getDriver_empnumber());

									fuel.setVendor_id(fuelBean.getVendor_id());
									fuel.setVendor_name(fuelBean.getVendor_name());
									fuel.setVendor_location(fuelBean.getVendor_location());
									fuel.setFuel_payment(fuelBean.getFuel_payment());

									fuel.setFuel_reference(fuelBean.getFuel_reference());
									fuel.setFuel_personal(fuelBean.getFuel_personal());
									fuel.setFuel_tank(fuelBean.getFuel_tank());
									fuel.setFuel_tank_partial(fuelBean.getFuel_tank_partial());

									fuel.setFuel_comments(fuelBean.getFuel_comments());
									fuel.setFuel_usage(fuelBean.getFuel_usage());
									fuel.setFuel_amount(fuelBean.getFuel_amount());
									fuel.setFuel_kml(fuelBean.getFuel_kml());
									fuel.setFuel_cost(fuelBean.getFuel_cost());

									fuel.setFuel_vendor_paymode(fuelBean.getFuel_vendor_paymode());

									Fuelbeans.add(fuel);

								} // close Below Range Else condition

							} // close vehicle and fuel vid If condition

						} // close fuel for loop
					} // close fuelReport If condition
				} // close vehicle For loop
			} // close vehicle if condition

			break;
		}

		return Fuelbeans;
	}

	
	public List<FuelDto> Report_Vehicle_wise_Fuel_RangeFuel(List<Vehicle> VehicleReport, List<FuelDto> fuelReport,
			short fuelRange) {

		List<FuelDto> Fuelbeans = new ArrayList<FuelDto>();
		

		switch (fuelRange) {

		case VehicleFuelType.FUEL_RANGE_WITHIN:

			// vehicle List checking to vid and expert mileage and
			// expert to
			// getying type
			if (VehicleReport != null && !VehicleReport.isEmpty()) {
				// vehicle for loop continue
				for (Vehicle vehicleBean : VehicleReport) {
					if (fuelReport != null && !fuelReport.isEmpty()) {

						FuelDto fuel = null;

						for (FuelDto fuelBean : fuelReport) {
							fuel = new FuelDto();
							// check vehicle for one vehicle If and Fuel
							// vid equel is true go inside
							// System.out.println(vehicleBean.getVid().equals(fuelBean.getVid()));
							if (vehicleBean.getVid().equals(fuelBean.getVid())) {

								// here check vehicle expectedMileage
								// from less then equel ( 4 <= 5) to
								// fuel kml true go inside
								if (vehicleBean.getVehicle_ExpectedMileage() <= fuelBean.getFuel_kml()) {

									// here check vehicle
									// expectedMileage_to to greater
									// then equel ( 3 >= 2) to fuel kml
									// true go inside
									// this checking with in Range
									if (vehicleBean.getVehicle_ExpectedMileage_to() >= fuelBean.getFuel_kml()) {

										fuel.setFuel_id(fuelBean.getFuel_id());
										fuel.setFuel_Number(fuelBean.getFuel_Number());

										fuel.setVid(fuelBean.getVid());
										// get Vehicle id to Vehicle
										// name
										fuel.setVehicle_registration(fuelBean.getVehicle_registration());
										fuel.setVehicle_Ownership(fuelBean.getVehicle_Ownership());
										if (fuelBean.getFuel_D_date() != null) {
											fuel.setFuel_date(dateFormat.format(fuelBean.getFuel_D_date()));
										}
										fuel.setFuel_meter(fuelBean.getFuel_meter());
										fuel.setFuel_meter_old(fuelBean.getFuel_meter_old());
										fuel.setFuel_meter_attributes(fuelBean.getFuel_meter_attributes());
										fuel.setFuel_type(fuelBean.getFuel_type());
										fuel.setFuel_liters(fuelBean.getFuel_liters());
										fuel.setFuel_price(fuelBean.getFuel_price());

										fuel.setVehicle_group(fuelBean.getVehicle_group());
										fuel.setDriver_id(fuelBean.getDriver_id());
										fuel.setDriver_name(fuelBean.getDriver_name());
										fuel.setDriver_empnumber(fuelBean.getDriver_empnumber());

										fuel.setVendor_id(fuelBean.getVendor_id());
										fuel.setVendor_name(fuelBean.getVendor_name());
										fuel.setVendor_location(fuelBean.getVendor_location());
										fuel.setFuel_payment(fuelBean.getFuel_payment());

										fuel.setFuel_reference(fuelBean.getFuel_reference());
										fuel.setFuel_personal(fuelBean.getFuel_personal());
										fuel.setFuel_tank(fuelBean.getFuel_tank());
										fuel.setFuel_tank_partial(fuelBean.getFuel_tank_partial());

										fuel.setFuel_comments(fuelBean.getFuel_comments());
										fuel.setFuel_usage(fuelBean.getFuel_usage());
										fuel.setFuel_amount(fuelBean.getFuel_amount());
										fuel.setFuel_kml(fuelBean.getFuel_kml());
										fuel.setFuel_cost(fuelBean.getFuel_cost());
										fuel.setFirstDriverFatherName(fuelBean.getFirstDriverFatherName());
										fuel.setFirstDriverLastName(fuelBean.getFirstDriverLastName());

										fuel.setFuel_vendor_paymode(fuelBean.getFuel_vendor_paymode());

										Fuelbeans.add(fuel);

									} // close less than If condition
								} // close greater then If condition

							} // close vehicle and fuel vid If condition

						} // close fuel for loop
					} // close fuelReport If condition
				} // close vehicle For loop
			} // close vehicle if condition

			break;

		case VehicleFuelType.FUEL_RANGE_OUT_OF_RANGE:
			// vehicle List checking to vid and expert mileage and
			// expert to
			// getying type
			if (VehicleReport != null && !VehicleReport.isEmpty()) {
				// vehicle for loop continue
				for (Vehicle vehicleBean : VehicleReport) {

					if (fuelReport != null && !fuelReport.isEmpty()) {

						FuelDto fuel = null;

						for (FuelDto fuelBean : fuelReport) {
							fuel = new FuelDto();

							// check vehicle for one vehicle If and Fuel
							// vid equel is true go inside
							// System.out.println(vehicleBean.getVid().equals(fuelBean.getVid()));
							if (vehicleBean.getVid().equals(fuelBean.getVid())) {

								// here check vehicle expectedMileage
								// from less then equel ( 4 <= 5) to
								// fuel kml true go inside
								if (vehicleBean.getVehicle_ExpectedMileage() <= fuelBean.getFuel_kml()) {

									// here check vehicle
									// expectedMileage_to to greater
									// then equel ( 3 >= 2) to fuel kml
									// true go inside
									// this checking with in Range
									if (vehicleBean.getVehicle_ExpectedMileage_to() >= fuelBean.getFuel_kml()) {
										// vehicle with in Range
										// condition loop
									} else {

										// below vehicle BELOW Range
										// condition loop

										fuel.setFuel_id(fuelBean.getFuel_id());
										fuel.setFuel_Number(fuelBean.getFuel_Number());

										fuel.setVid(fuelBean.getVid());
										// get Vehicle id to Vehicle
										// name
										fuel.setVehicle_registration(fuelBean.getVehicle_registration());
										fuel.setVehicle_Ownership(fuelBean.getVehicle_Ownership());
										if (fuelBean.getFuel_D_date() != null) {
											fuel.setFuel_date(dateFormat.format(fuelBean.getFuel_D_date()));
										}
										fuel.setFuel_meter(fuelBean.getFuel_meter());
										fuel.setFuel_meter_old(fuelBean.getFuel_meter_old());
										fuel.setFuel_meter_attributes(fuelBean.getFuel_meter_attributes());
										fuel.setFuel_type(fuelBean.getFuel_type());
										fuel.setFuel_liters(fuelBean.getFuel_liters());
										fuel.setFuel_price(fuelBean.getFuel_price());

										fuel.setVehicle_group(fuelBean.getVehicle_group());
										fuel.setDriver_id(fuelBean.getDriver_id());
										fuel.setDriver_name(fuelBean.getDriver_name());
										fuel.setDriver_empnumber(fuelBean.getDriver_empnumber());

										fuel.setVendor_id(fuelBean.getVendor_id());
										fuel.setVendor_name(fuelBean.getVendor_name());
										fuel.setVendor_location(fuelBean.getVendor_location());
										fuel.setFuel_payment(fuelBean.getFuel_payment());

										fuel.setFuel_reference(fuelBean.getFuel_reference());
										fuel.setFuel_personal(fuelBean.getFuel_personal());
										fuel.setFuel_tank(fuelBean.getFuel_tank());
										fuel.setFuel_tank_partial(fuelBean.getFuel_tank_partial());

										fuel.setFuel_comments(fuelBean.getFuel_comments());
										fuel.setFuel_usage(fuelBean.getFuel_usage());
										fuel.setFuel_amount(fuelBean.getFuel_amount());
										fuel.setFuel_kml(fuelBean.getFuel_kml());
										fuel.setFuel_cost(fuelBean.getFuel_cost());

										fuel.setFuel_vendor_paymode(fuelBean.getFuel_vendor_paymode());
										fuel.setFirstDriverFatherName(fuelBean.getFirstDriverFatherName());
										fuel.setFirstDriverLastName(fuelBean.getFirstDriverLastName());

										Fuelbeans.add(fuel);

									} // close less than If condition
								} // close greater then If condition

							} // close vehicle and fuel vid If condition

						} // close fuel for loop
					} // close fuelReport If condition
				} // close vehicle For loop
			} // close vehicle if condition

			break;
		case VehicleFuelType.FUEL_RANGE_BELOW:

			// vehicle List checking to vid and expert mileage and
			// expert to
			// getying type
			if (VehicleReport != null && !VehicleReport.isEmpty()) {
				// vehicle for loop continue
				for (Vehicle vehicleBean : VehicleReport) {

					if (fuelReport != null && !fuelReport.isEmpty()) {

						FuelDto fuel = null;

						for (FuelDto fuelBean : fuelReport) {
							fuel = new FuelDto();

							// check vehicle for one vehicle If and Fuel
							// vid equel is true go inside
							// System.out.println(vehicleBean.getVid().equals(fuelBean.getVid()));
							if (vehicleBean.getVid().equals(fuelBean.getVid())) {

								// here check vehicle expectedMileage
								// from less then equel ( 4 <= 5) to
								// fuel kml true go inside
								if(vehicleBean.getVehicle_ExpectedMileage() == null) {
									vehicleBean.setVehicle_ExpectedMileage(0.0);
								}
								if (vehicleBean.getVehicle_ExpectedMileage() <= fuelBean.getFuel_kml()) {
									// vehicle with in Range condition
									// loop
								} else {

									// below vehicle BELOW Range
									// condition loop

									fuel.setFuel_id(fuelBean.getFuel_id());
									fuel.setFuel_Number(fuelBean.getFuel_Number());

									fuel.setVid(fuelBean.getVid());
									// get Vehicle id to Vehicle name
									fuel.setVehicle_registration(fuelBean.getVehicle_registration());
									fuel.setVehicle_Ownership(fuelBean.getVehicle_Ownership());
									if (fuelBean.getFuel_D_date() != null) {
										fuel.setFuel_date(dateFormat.format(fuelBean.getFuel_D_date()));
									}
									fuel.setFuel_meter(fuelBean.getFuel_meter());
									fuel.setFuel_meter_old(fuelBean.getFuel_meter_old());
									fuel.setFuel_meter_attributes(fuelBean.getFuel_meter_attributes());
									fuel.setFuel_type(fuelBean.getFuel_type());
									fuel.setFuel_liters(fuelBean.getFuel_liters());
									fuel.setFuel_price(fuelBean.getFuel_price());

									fuel.setVehicle_group(fuelBean.getVehicle_group());
									fuel.setDriver_id(fuelBean.getDriver_id());
									fuel.setDriver_name(fuelBean.getDriver_name());
									fuel.setDriver_empnumber(fuelBean.getDriver_empnumber());

									fuel.setVendor_id(fuelBean.getVendor_id());
									fuel.setVendor_name(fuelBean.getVendor_name());
									fuel.setVendor_location(fuelBean.getVendor_location());
									fuel.setFuel_payment(fuelBean.getFuel_payment());

									fuel.setFuel_reference(fuelBean.getFuel_reference());
									fuel.setFuel_personal(fuelBean.getFuel_personal());
									fuel.setFuel_tank(fuelBean.getFuel_tank());
									fuel.setFuel_tank_partial(fuelBean.getFuel_tank_partial());

									fuel.setFuel_comments(fuelBean.getFuel_comments());
									fuel.setFuel_usage(fuelBean.getFuel_usage());
									fuel.setFuel_amount(fuelBean.getFuel_amount());
									fuel.setFuel_kml(fuelBean.getFuel_kml());
									fuel.setFuel_cost(fuelBean.getFuel_cost());

									fuel.setFuel_vendor_paymode(fuelBean.getFuel_vendor_paymode());
									fuel.setFirstDriverFatherName(fuelBean.getFirstDriverFatherName());
									fuel.setFirstDriverLastName(fuelBean.getFirstDriverLastName());

									Fuelbeans.add(fuel);

								} // close Below Range Else condition

							} // close vehicle and fuel vid If condition

						} // close fuel for loop
					} // close fuelReport If condition
				} // close vehicle For loop
			} // close vehicle if condition

			break;
		}

		return Fuelbeans;
	}

	
	/**
	 * @param showAmount
	 * @return
	 */
	public Double prepareTotal_Tripsheet_fuel_details(List<FuelDto> fuelList) {

		Double totalFuelDiesel = 0.0;
		if (fuelList != null && !fuelList.isEmpty()) {
			for (FuelDto fuelBean : fuelList) {
				if (fuelBean.getFuel_liters() != null) {
					totalFuelDiesel += fuelBean.getFuel_liters();
				}
			}
		}
		return Double.parseDouble(toFixedTwo.format(totalFuelDiesel));
	}

	/**
	 * @param showAmount
	 * @return
	 */
	public Integer prepareTotalUsage_Tripsheet_fuel_details(List<FuelDto> showAmount) {

		Integer totalFuelusage = 0;
		if (showAmount != null && !showAmount.isEmpty()) {
			for (FuelDto fuelBean : showAmount) {
				if(fuelBean.getFuel_tank() == 0) {
					if (fuelBean.getFuel_usage() != null) {
						totalFuelusage += fuelBean.getFuel_usage();
					}
				}
			}
		}
		return totalFuelusage;
	}

	/**
	 * @param showAmount
	 * @return
	 */
	public Double prepareTotalAmount_Tripsheet_fuel_details(List<FuelDto> showAmount) {

		Double totalFuelamount = 0.0;
		if (showAmount != null && !showAmount.isEmpty()) {
			for (FuelDto fuelBean : showAmount) {
				if (fuelBean.getFuel_amount() != null) {
					totalFuelamount += fuelBean.getFuel_amount();
				}
			}
		}
		return  Double.parseDouble(toFixedTwo.format(totalFuelamount));
	}
	
	public Double prepareTotalAmount_Tripsheet_Created_fuel_details(List<FuelDto> showAmount) {
		Double totalFuelamount = 0.0;
		if (showAmount != null && !showAmount.isEmpty()) {
			for (FuelDto fuelBean : showAmount) {
				if ((fuelBean.getFuel_amount() != null && fuelBean.getPaymentTypeId()==PaymentTypeConstant.PAYMENT_TYPE_CREDIT)
						|| (fuelBean.getFuel_amount() != null && fuelBean.getPaidByBranchId() != null && fuelBean.getPaidByBranchId() > 0)) {
					totalFuelamount += fuelBean.getFuel_amount();
				}
			}
		}
		return Double.parseDouble(toFixedTwo.format(totalFuelamount));
	}
	
	
	public  FuelDto getUpdateFuelDto(ValueObject object) throws Exception{
		FuelDto		fuel	 	= null;
		try {
			fuel = new FuelDto();
			fuel.setFuel_id(object.getLong("fuel_id"));
			fuel.setFuel_Number(object.getLong("fuelNumber"));
			fuel.setVid(object.getInt("FuelSelectVehicle",0));
			fuel.setGpsOdometer(object.getDouble("gpsOdometer",0));
			fuel.setTallyCompanyId(object.getLong("tallyCompanyId",0));
			fuel.setFuel_liters(object.getDouble("fuel_liters",0));
			fuel.setVehicle_group(object.getString("vehicle_group"));
			fuel.setFuel_TripsheetNumber(object.getLong("fuel_TripsheetNumber",0));
			fuel.setFuel_date(object.getString("fuelDate"));
			if(!object.getString("fuelTime","").equals("")) {
				fuel.setFuelTime(object.getString("fuelTime"));
			}else {
				fuel.setFuelTime("00:00");
			}
			fuel.setFuel_meter_old(object.getInt("fuel_meter_old",0));
			fuel.setFuel_meter(object.getInt("fuel_meter",0));
			fuel.setFuelTypeId(object.getShort("fuel_type"));
			fuel.setVendor_id(object.getInt("selectVendor",0));
			fuel.setFuel_price(object.getDouble("fuel_price",0));
			fuel.setFuel_reference(object.getString("fuel_reference"));
			fuel.setDriver_id(object.getInt("VehicleTODriverFuel",0));
			fuel.setSecDriverID(object.getInt("VehicleTODriver2Fuel",0));
			fuel.setCleanerID(object.getInt("VehicleTOCleanerFuel",0));
			fuel.setRouteID(object.getInt("TripRouteList",0));
			fuel.setFuel_comments(object.getString("fuel_comments"));
			fuel.setFuel_TripsheetID(object.getLong("tripSheetId",0));
			fuel.setPaymentTypeId(object.getShort("paymentTypeId"));
			fuel.setFuel_tank(object.getInt("fuel_tank",0));
			fuel.setFuel_meter_attributes(object.getInt("fuel_meter_attributes",0));
			fuel.setCreatingBackDateFuel(object.getBoolean("creatingBackDateFuel",false));
			fuel.setPreviousFuelAmount(object.getDouble("previousFuelAmount",0));
			fuel.setPreviousFuelDate(object.getString("previousFuelDate"));
			fuel.setFuelInvoiceId(object.getLong("fuelInvoiceId",0));
			fuel.setVendor_name(object.getString("vendorName"));
			fuel.setFuel_amount(object.getDouble("fuelAmount",0));
			
			if(object.getLong("nextFuelIdOfBackDate",0) >   0) {
				fuel.setNextFuelEntryFuelId(object.getLong("nextFuelIdOfBackDate",0));
				fuel.setNextFuelEntryFound(object.getBoolean("nextFuelEntryFound",false));
				
			}
			return fuel;
		} catch (Exception e) {
			throw  e;                                      
		}
	}
	
	public IntangleFuelEntryDetails prepareIntangleFuelEntryDetails(JSONObject	object)throws Exception {
		IntangleFuelEntryDetails 	intangleFuelEntryDetails = null;
		JSONObject	location = null;
		try {
			
			intangleFuelEntryDetails = new IntangleFuelEntryDetails();
			intangleFuelEntryDetails.setIntangleFuelApiId(object.getString("id"));
			location = object.getJSONObject("location");
			intangleFuelEntryDetails.setLatitude(location.getString("latitude"));
			intangleFuelEntryDetails.setLongitude(location.getString("longitude"));
			Date date = new Date(Long.parseLong(object.getString("timestamp")));
			intangleFuelEntryDetails.setFuelDate((Timestamp) object.get("fuelDate"));
			intangleFuelEntryDetails.setVid(object.getInt("vid"));
			intangleFuelEntryDetails.setAddress(object.getString("address"));
			intangleFuelEntryDetails.setType(object.getString("type"));
			intangleFuelEntryDetails.setAmount(object.getDouble("amount"));
			intangleFuelEntryDetails.setOdometer(object.getLong("odo"));
			intangleFuelEntryDetails.setVerified(object.getBoolean("verified"));
			intangleFuelEntryDetails.setCompanyId(object.getInt("companyId"));
			intangleFuelEntryDetails.setMarkForDelete(false);
			intangleFuelEntryDetails.setCreationDate(DateTimeUtility.getCurrentTimeStamp());
			intangleFuelEntryDetails.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			return intangleFuelEntryDetails;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
}
