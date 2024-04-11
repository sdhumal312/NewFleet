package org.fleetopgroup.persistence.bl;
/**
 * @author fleetop
 *
 *
 *
 */

import org.fleetopgroup.persistence.dto.BookingTripSheetDto;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TripCollectionSheetDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VendorDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.InventoryLocation;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.springframework.stereotype.Controller;

@Controller
public class ReportBL {

	public ReportBL() {
	}

	// get RenewalReminderDto
	public RenewalReminderDto prepareRenewalRemider(RenewalReminderDto renewalReminderDto) {

		RenewalReminderDto renewal = new RenewalReminderDto();

		/*
		 * renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
		 * renewal.setVid(renewalReminderDto.getVid());
		 */
		renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
		renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
		renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());
		renewal.setRenewal_receipt(renewalReminderDto.getRenewal_receipt());
		renewal.setRenewal_from(renewalReminderDto.getRenewal_from());
		renewal.setRenewal_to(renewalReminderDto.getRenewal_to());

		return renewal;
	}

	public VehicleDto prepareReportVehicle(VehicleDto vehicleDto) {

		VehicleDto Dto = new VehicleDto();

		Dto.setVehicle_Status(vehicleDto.getVehicle_Status());
		Dto.setVehicle_Group(vehicleDto.getVehicle_Group());
		Dto.setVehicle_Type(vehicleDto.getVehicle_Type());
		Dto.setVehicle_Location(vehicleDto.getVehicle_Location());
		Dto.setVehicle_Ownership(vehicleDto.getVehicle_Ownership());
		Dto.setVehicle_Fuel(vehicleDto.getVehicle_Fuel());
		Dto.setVehicle_year(vehicleDto.getVehicle_year());
		Dto.setVehicle_maker(vehicleDto.getVehicle_maker());
		Dto.setVehicle_Model(vehicleDto.getVehicle_Model());
		Dto.setVehicle_SeatCapacity(vehicleDto.getVehicle_SeatCapacity());
		Dto.setVehicle_actype(vehicleDto.getVehicle_actype());
		return Dto;
	}

	// get logic in driver Information
	public Driver prepareModelDriver(DriverDto driverDto) {
		Driver driver = new Driver();

		driver.setVehicleGroupId(driverDto.getVehicleGroupId());
		driver.setDriJobId(driverDto.getDriJobId());
		driver.setDriver_dlclass(driverDto.getDriver_dlclass());
		driver.setDriverStatusId(driverDto.getDriverStatusId());
		driver.setDriver_languages(driverDto.getDriver_languages());
		driver.setDriver_trainings(driverDto.getDriver_trainings());
		driver.setVehicleGroupId(driverDto.getVehicleGroupId());

		return driver;
	}

	// get RenewalReminderDto
	public FuelDto prepareFuel(FuelDto fuelDto) {

		FuelDto fuel = new FuelDto();

		fuel.setVid(fuelDto.getVid());
		fuel.setVehicle_Ownership(fuelDto.getVehicle_Ownership());
		fuel.setFuel_type(fuelDto.getFuel_type());
		fuel.setVehicle_group(fuelDto.getVehicle_group());
		fuel.setDriver_id(fuelDto.getDriver_id());
		fuel.setVendor_name(fuelDto.getVendor_name());
		fuel.setFuel_from(fuelDto.getFuel_from());
		fuel.setFuel_to(fuelDto.getFuel_to());
		fuel.setFuel_vendor_paymode(fuelDto.getFuel_vendor_paymode());
		fuel.setVendor_id(fuelDto.getVendor_id());
		fuel.setVehicleGroupId(fuelDto.getVehicleGroupId());
		fuel.setVehicle_OwnershipId(fuelDto.getVehicle_OwnershipId());
		fuel.setFuelTypeId(fuelDto.getFuelTypeId());
		fuel.setFuel_vendor_paymodeId(fuelDto.getFuel_vendor_paymodeId());
		return fuel;
	}

	// get RenewalReminderDto
	public VendorDto prepareVendor(VendorDto vendorDto) {

		VendorDto vendor = new VendorDto();
		
		if(vendorDto.getVendorId() != null) {
			vendor.setVendorId(vendorDto.getVendorId());
		}else {
			vendor.setVendorId(0);
		}
		if(vendorDto.getVendorTypeId() != null) {
			vendor.setVendorTypeId(vendorDto.getVendorTypeId());
		}else {
			vendor.setVendorTypeId((long) 0);
		}
			
		vendor.setVendorLocation(vendorDto.getVendorLocation());

		return vendor;
	}

	// get TripSheetDto
	public TripSheetDto prepareTripSheet(TripSheetDto tripSheetDto) {

		TripSheetDto trip = new TripSheetDto();

		trip.setVid(tripSheetDto.getVid());
		trip.setVehicle_Group(tripSheetDto.getVehicle_Group());
		trip.setVehicleGroupId(tripSheetDto.getVehicleGroupId());
		trip.setCloseTripStatusId(tripSheetDto.getCloseTripStatusId());
		trip.setTripOpenDate(tripSheetDto.getTripOpenDate());
		trip.setClosetripDate(tripSheetDto.getClosetripDate());

		return trip;
	}

	// get TripSheetDto
	public TripCollectionSheetDto prepareTripCollectionSheet(TripCollectionSheetDto tripSheetDto) {

		TripCollectionSheetDto trip = new TripCollectionSheetDto();

		trip.setVID(tripSheetDto.getVID());
		trip.setVEHICLE_GROUP_ID(tripSheetDto.getVEHICLE_GROUP_ID());
		trip.setTRIP_CLOSE_STATUS(tripSheetDto.getTRIP_CLOSE_STATUS());
		trip.setTRIP_CLOSE_STATUS_ID(tripSheetDto.getTRIP_CLOSE_STATUS_ID());
		trip.setTRIP_OPEN_DATE(tripSheetDto.getTRIP_OPEN_DATE());
		trip.setlASTUPDATED(tripSheetDto.getlASTUPDATED());

		return trip;
	}

	// get BookingTripSheetDto
	public BookingTripSheetDto prepareBookingTripSheet(BookingTripSheetDto tripSheetDto) {

		BookingTripSheetDto trip = new BookingTripSheetDto();

		trip.setBTS_VEHICLETYPE(tripSheetDto.getBTS_VEHICLETYPE());
		trip.setBTS_TRIP_FROM(tripSheetDto.getBTS_TRIP_FROM());
		trip.setBTS_TRIP_TO(tripSheetDto.getBTS_TRIP_TO());
		trip.setBTS_FROM_DATE(tripSheetDto.getBTS_FROM_DATE());
		trip.setBTS_TO_DATE(tripSheetDto.getBTS_TO_DATE());

		return trip;
	}

	// get RenewalReminderDto
	public ServiceReminderDto prepareServiceReminder(ServiceReminder serviceDto) {

		ServiceReminderDto service = new ServiceReminderDto();

		service.setVid(serviceDto.getVid());
		service.setServiceTypeId(serviceDto.getServiceTypeId());
		service.setServiceSubTypeId(serviceDto.getServiceSubTypeId());
		service.setServiceStatusId(serviceDto.getServiceStatusId());

		return service;
	}

	// get ServiceEntriesDto
	public ServiceEntriesDto prepareServiceEntries(ServiceEntriesDto serviceDto) {

		ServiceEntriesDto service = new ServiceEntriesDto();

		service.setVid(serviceDto.getVid());
		service.setDriver_id(serviceDto.getDriver_id());
		service.setDriver_name(serviceDto.getDriver_name());
		service.setVendor_id(serviceDto.getVendor_id());
		service.setVendor_name(serviceDto.getVendor_name());
		service.setInvoiceDate(serviceDto.getInvoiceDate());
		service.setService_paymentTypeId(serviceDto.getService_paymentTypeId());
		service.setService_paiddate(serviceDto.getService_paiddate());
		service.setService_paidby(serviceDto.getService_paidby());

		return service;
	}

	// get Masterpart
	public MasterParts prepareMasterParts(MasterParts UIPart) {

		MasterParts part = new MasterParts();

		part.setPartid(UIPart.getPartid());
		part.setMakerId(UIPart.getMakerId());
		part.setCategoryId(UIPart.getCategoryId());

		return part;
	}

	/*// get PurchaseOrder
	public PurchaseOrders preparePurchaseOrder(PurchaseOrdersDto PODto) {

		PurchaseOrders PO = new PurchaseOrders();

		PO.setPurchaseorder_quotenumber(PODto.getPurchaseorder_quotenumber());
		PO.setPurchaseorder_vendor_name(PODto.getPurchaseorder_vendor_name());
		PO.setPurchaseorder_buyer(PODto.getPurchaseorder_buyer());
		PO.setPurchaseorder_terms(PODto.getPurchaseorder_terms());
		PO.setPurchaseorder_shiplocation_id(PODto.getPurchaseorder_shiplocation_id());
		PO.setPurchaseorder_shipvia(PODto.getPurchaseorder_shipvia());
		
		 * PO.setPurchaseorder_created_on(PODto.getPurchaseorder_created_on());
		 PO.setPurchaseorder_receivedby(PODto.getPurchaseorder_receivedby());

		return PO;
	}*/

	// get prepareIssues
	public IssuesDto prepareIssues(IssuesDto issueDto) {

		IssuesDto issues = new IssuesDto();

		issues.setISSUES_TYPE_ID(issueDto.getISSUES_TYPE_ID());
		issues.setISSUES_STATUS_ID(issueDto.getISSUES_STATUS_ID());
		issues.setISSUES_REPORTED_DATE(issueDto.getISSUES_REPORTED_DATE());
		issues.setISSUES_REPORTED_BY(issueDto.getISSUES_REPORTED_BY());
		issues.setISSUES_REPORTED_BY_ID(issueDto.getISSUES_REPORTED_BY_ID());
		issues.setISSUES_ASSIGN_TO(issueDto.getISSUES_ASSIGN_TO());
		issues.setISSUES_LABELS_ID(issueDto.getISSUES_LABELS_ID());
		return issues;
	}

	public RenewalReminderDto prepareRenewalReminder(RenewalReminderDto ReminderDto) {
		RenewalReminderDto renewal = new RenewalReminderDto();

		renewal.setRenewalTypeId(ReminderDto.getRenewalTypeId());
		renewal.setRenewal_Subid(ReminderDto.getRenewal_Subid());
		renewal.setVid(ReminderDto.getVid());
		renewal.setVehicle_registration(ReminderDto.getVehicle_registration());

		return renewal;
	}

	public InventoryLocation prepareInventoryLocation(InventoryLocation LocationDto) {
		InventoryLocation location = new InventoryLocation();

		location.setPartid(LocationDto.getPartid());
//		location.setPartname(LocationDto.getPartname());
//		location.setLocation(LocationDto.getLocation());
//		location.setPartnumber(LocationDto.getPartnumber());

		return location;
	}

	public InventoryDto prepareInventory(InventoryDto part) {

		InventoryDto invenPart = new InventoryDto();

		invenPart.setPartid(part.getPartid());
		invenPart.setVendor_id(part.getVendor_id());
		invenPart.setVendor_name(part.getVendor_name());
		invenPart.setLocation(part.getLocation());

		return invenPart;
	}

	// get TripSheetDto
	public TripDailySheetDto prepareTripDailySheet(TripDailySheetDto tripSheetDto) {

		TripDailySheetDto trip = new TripDailySheetDto();

		trip.setVEHICLEID(tripSheetDto.getVEHICLEID());
		trip.setVEHICLE_GROUP_ID(tripSheetDto.getVEHICLE_GROUP_ID());
		trip.setTRIP_STATUS_ID(tripSheetDto.getTRIP_STATUS_ID());
		trip.setTRIP_OPEN_DATE(tripSheetDto.getTRIP_OPEN_DATE());
		trip.setLASTUPDATED(tripSheetDto.getLASTUPDATED());

		return trip;
	}
}
