package org.fleetopgroup.persistence.dto;

import java.util.Date;

import javax.persistence.Column;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 *
 *
 */ 
public class FuelDto {

	private Long 		fuel_id;
	private Long 		fuel_Number;
	private String 		fuelNumber;
	private Integer 	vid;
	private String 		vehicle_registration;
	private String 		vehicle_Ownership;
	private Integer 	vehicle_odometerchange;
	private String 		fuel_date;
	private Date 		fuel_D_date;
	private Integer 	fuel_meter;
	private Integer 	fuel_meter_old;
	private Integer 	fuel_meter_attributes;
	private String		meter_attributes_str;
	private String 		fuel_type;
	private String 		vehicle_group;
	private Double 		fuel_liters;
	private Double 		fuel_price;
	private Integer 	vendor_id;
	private String 		vendor_name;
	private boolean		isAutoVendor;
	private String 		vendor_location;
	private String 		fuel_payment;
	private String 		fuel_reference;
	private Integer 	driver_id;
	private String 		driver_name;
	private String 		fuelSecDriverName;
	private String 		fuelCleanerName;
	private String 		fuelRouteName;
	private String 		driver_empnumber;
	private Integer 	fuel_personal;
	private Integer 	fuel_tank;
	private Integer 	fuel_tank_partial;
	private String 		fuel_comments;
	private Integer 	fuel_usage;
	private Double 		fuel_amount;
	private Double 		fuel_kml;
	private Double 		fuel_cost;
	private String 		fuelCost;//String
	private Long 		fuel_TripsheetID;
	private Long 		fuel_TripsheetNumber;
	private boolean 	fuel_document;
	private Long 		fuel_document_id;
	private String 		fuel_from;
	private String 		fuel_to;
	private Date 		fuel_vendor_paymentdate;
	private String 		fuel_vendor_paymode;
	private String 		createdBy;
	private String 		created;
	private String 		lastModifiedBy;
	private String 		lastupdated;
	private long		vehicleGroupId;
	private short		paymentTypeId;
	private short 		vehicle_OwnershipId;
	private short 		fuelTypeId;
	private short 		fuel_vendor_paymodeId;
	private Long 		createdById;
	private Long 		lastModifiedById;
	private Integer 	companyId;
	private Date 		createdOn;
	private Date 		lastupdatedOn;
	private Long 		fuel_vendor_approvalID;
	private Double		previousFuelAmount;
	private String		previousFuelDate;
	private Double		gpsOdometer;
	private Double		gpsUsageKM;
	private short 		typeOfPaymentId;
	private Integer 	secDriverID;
	private Integer 	cleanerID;
	private Integer 	routeID;
	private double 		paidAmount;
	private double 		balanceAmount;
	private String		tripSheetNumber;
	private Double 		vehicle_ExpectedMileage;
	private Double 		vehicle_ExpectedMileage_to;
	private Date		fuelDateTime;
	private String		fuelDateTimeStr;
	private String		fuelDateTimeStr2;
	private String		fuelTime;
	private String		fuelBase64Document;
	private String		vehicleGroupIdStr;
	private Double		fuelPriceDiff;
	private String		tallyCompanyName;
	private Long		tallyCompanyId;
	private	boolean		creatingBackDateFuel;
	private	boolean		nextFuelEntryFound;
	private long		nextFuelEntryFuelId;
	private Integer		vehicle_ExpectedOdameter;
	private String		latitude;
	private String		longitude;
	private long 		countOfFEOnEachVehicle;
	private double 		vendorApprovedAmount;
	private String		fileExtension;
	private short		ownPetrolPump;
	private String 		firstDriverFatherName;
	private String 		firstDriverLastName;
	private String 		secDriverFatherName;
	private String 		secDriverLastName;
	private Long		fuelInvoiceId;
	private boolean     markForDelete;
	private long		userId;
	private String		userName;
	private String      fuelVendorPaymentDate;
	private Double		gpKMPL;
	private Short 		paidById;
	private Long 		paidByBranchId;
	private String 		paidByBranchStr;
	public String getPaidByBranchStr() {
		return paidByBranchStr;
	}


	public void setPaidByBranchStr(String paidByBranchStr) {
		this.paidByBranchStr = paidByBranchStr;
	}


	private short       moduleIdentifire;
	public short getModuleIdentifire() {
		return moduleIdentifire;
	}


	public void setModuleIdentifire(short moduleIdentifire) {
		this.moduleIdentifire = moduleIdentifire;
	}


	private Integer     tripClosingKM;
	private short       tripStutesId;
	
	public Integer getTripClosingKM() {
		return tripClosingKM;
	}


	public void setTripClosingKM(Integer tripClosingKM) {
		this.tripClosingKM = tripClosingKM;
	}


	public short getTripStutesId() {
		return tripStutesId;
	}


	public void setTripStutesId(short tripStutesId) {
		this.tripStutesId = tripStutesId;
	}


	public String getFuelVendorPaymentDate() {
		return fuelVendorPaymentDate;
	}


	public void setFuelVendorPaymentDate(String fuelVendorPaymentDate) {
		this.fuelVendorPaymentDate = fuelVendorPaymentDate;
	}


	public boolean isMarkForDelete() {
		return markForDelete;
	}


	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getFuelDateTimeStr2() {
		return fuelDateTimeStr2;
	}


	public void setFuelDateTimeStr2(String fuelDateTimeStr2) {
		this.fuelDateTimeStr2 = fuelDateTimeStr2;
	}


	public short getTypeOfPaymentId() {
		return typeOfPaymentId;
	}


	public void setTypeOfPaymentId(short typeOfPaymentId) {
		this.typeOfPaymentId = typeOfPaymentId;
	}


	public double getPaidAmount() {
		return paidAmount;
	}


	public void setPaidAmount(double paidAmount) {
		this.paidAmount = Utility.round( paidAmount, 2);
	}


	public double getBalanceAmount() {
		return balanceAmount;
	}


	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = Utility.round( balanceAmount, 2);
	}


	public static final int getParseVendorID_STRING_TO_ID(String value) {
		if (value == null) {
			return 0;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}
	
	
	/**
	 * @return the fuel_id
	 */
	public Long getFuel_id() {
		return fuel_id;
	}

	/**
	 * @param fuel_id
	 *            the fuel_id to set
	 */
	public void setFuel_id(Long fuel_id) {
		this.fuel_id = fuel_id;
	}

	public Long getFuel_Number() {
		return fuel_Number;
	}

	public void setFuel_Number(Long fuel_Number) {
		this.fuel_Number = fuel_Number;
	}

	/**
	 * @return the vid
	 */
	public Integer getVid() {
		return vid;
	}

	/**
	 * @param vid
	 *            the vid to set
	 */
	public void setVid(Integer vid) {
		this.vid = vid;
	}

	/**
	 * @return the vehicle_registration
	 */
	public String getVehicle_registration() {
		return vehicle_registration;
	}

	/**
	 * @param vehicle_registration
	 *            the vehicle_registration to set
	 */
	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	/**
	 * @return the fuel_date
	 */
	public String getFuel_date() {
		return fuel_date;
	}

	/**
	 * @param fuel_date
	 *            the fuel_date to set
	 */
	public void setFuel_date(String fuel_date) {
		this.fuel_date = fuel_date;
	}

	/**
	 * @return the fuel_meter
	 */
	public Integer getFuel_meter() {
		return fuel_meter;
	}

	/**
	 * @param fuel_meter
	 *            the fuel_meter to set
	 */
	public void setFuel_meter(Integer fuel_meter) {
		this.fuel_meter = fuel_meter;
	}

	/**
	 * @return the fuel_meter_old
	 */
	public Integer getFuel_meter_old() {
		return fuel_meter_old;
	}

	/**
	 * @param fuel_meter_old
	 *            the fuel_meter_old to set
	 */
	public void setFuel_meter_old(Integer fuel_meter_old) {
		this.fuel_meter_old = fuel_meter_old;
	}

	/**
	 * @return the fuel_meter_attributes
	 */
	public Integer getFuel_meter_attributes() {
		return fuel_meter_attributes;
	}

	/**
	 * @param fuel_meter_attributes
	 *            the fuel_meter_attributes to set
	 */
	public void setFuel_meter_attributes(Integer fuel_meter_attributes) {
		this.fuel_meter_attributes = fuel_meter_attributes;
	}

	/**
	 * @return the fuel_type
	 */
	public String getFuel_type() {
		return fuel_type;
	}

	/**
	 * @param fuel_type
	 *            the fuel_type to set
	 */
	public void setFuel_type(String fuel_type) {
		this.fuel_type = fuel_type;
	}

	/**
	 * @return the fuel_liters
	 */
	public Double getFuel_liters() {
		return fuel_liters;
	}

	/**
	 * @param fuel_liters
	 *            the fuel_liters to set
	 */
	public void setFuel_liters(Double fuel_liters) {
		this.fuel_liters = Utility.round( fuel_liters, 2);
	}

	/**
	 * @return the fuel_price
	 */
	public Double getFuel_price() {
		return fuel_price;
	}

	/**
	 * @param fuel_price
	 *            the fuel_price to set
	 */
	public void setFuel_price(Double fuel_price) {
		this.fuel_price = Utility.round( fuel_price, 2);
	}

	public String getFuelTime() {
		return fuelTime;
	}


	public void setFuelTime(String fuelTime) {
		this.fuelTime = fuelTime;
	}


	/**
	 * @return the vendor_id
	 */
	public Integer getVendor_id() {
		return vendor_id;
	}

	/**
	 * @param vendor_id
	 *            the vendor_id to set
	 */
	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	/**
	 * @return the vendor_name
	 */
	public String getVendor_name() {
		return vendor_name;
	}

	/**
	 * @param vendor_name
	 *            the vendor_name to set
	 */
	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	public boolean isAutoVendor() {
		return isAutoVendor;
	}


	public String getPreviousFuelDate() {
		return previousFuelDate;
	}


	public void setPreviousFuelDate(String previousFuelDate) {
		this.previousFuelDate = previousFuelDate;
	}


	public void setAutoVendor(boolean isAutoVendor) {
		this.isAutoVendor = isAutoVendor;
	}
	

	/**
	 * @return the fuel_reference
	 */
	public String getFuel_reference() {
		return fuel_reference;
	}

	/**
	 * @param fuel_reference
	 *            the fuel_reference to set
	 */
	public void setFuel_reference(String fuel_reference) {
		this.fuel_reference = fuel_reference;
	}

	/**
	 * @return the fuel_vendor_approvalID
	 */
	public Long getFuel_vendor_approvalID() {
		return fuel_vendor_approvalID;
	}

	/**
	 * @param fuel_vendor_approvalID the fuel_vendor_approvalID to set
	 */
	public void setFuel_vendor_approvalID(Long fuel_vendor_approvalID) {
		this.fuel_vendor_approvalID = fuel_vendor_approvalID;
	}

	/**
	 * @return the fuel_personal
	 */
	public Integer getFuel_personal() {
		return fuel_personal;
	}

	/**
	 * @param fuel_personal
	 *            the fuel_personal to set
	 */
	public void setFuel_personal(Integer fuel_personal) {
		this.fuel_personal = fuel_personal;
	}

	/**
	 * @return the fuel_tank
	 */
	public Integer getFuel_tank() {
		return fuel_tank;
	}

	/**
	 * @param fuel_tank
	 *            the fuel_tank to set
	 */
	public void setFuel_tank(Integer fuel_tank) {
		this.fuel_tank = fuel_tank;
	}

	/**
	 * @return the fuel_tank_partial
	 */
	public Integer getFuel_tank_partial() {
		return fuel_tank_partial;
	}

	/**
	 * @param fuel_tank_partial
	 *            the fuel_tank_partial to set
	 */
	public void setFuel_tank_partial(Integer fuel_tank_partial) {
		this.fuel_tank_partial = fuel_tank_partial;
	}

	/**
	 * @return the fuel_comments
	 */
	public String getFuel_comments() {
		return fuel_comments;
	}

	/**
	 * @param fuel_comments
	 *            the fuel_comments to set
	 */
	public void setFuel_comments(String fuel_comments) {
		this.fuel_comments = fuel_comments;
	}

	/**
	 * @return the fuel_usage
	 */
	public Integer getFuel_usage() {
		return fuel_usage;
	}

	/**
	 * @param fuel_usage
	 *            the fuel_usage to set
	 */
	public void setFuel_usage(Integer fuel_usage) {
		this.fuel_usage = fuel_usage;
	}

	/**
	 * @return the fuel_amount
	 */
	public Double getFuel_amount() {
		return fuel_amount;
	}

	/**
	 * @param fuel_amount
	 *            the fuel_amount to set
	 */
	public void setFuel_amount(Double fuel_amount) {
		this.fuel_amount = Utility.round( fuel_amount, 2);
	}

	/**
	 * @return the fuel_kml
	 */
	public Double getFuel_kml() {
		return fuel_kml;
	}

	public Double getPreviousFuelAmount() {
		return previousFuelAmount;
	}


	public void setPreviousFuelAmount(Double previousFuelAmount) {
		this.previousFuelAmount = Utility.round( previousFuelAmount, 2);
	}


	/**
	 * @param fuel_kml
	 *            the fuel_kml to set
	 */
	public void setFuel_kml(Double fuel_kml) {
		this.fuel_kml = Utility.round(fuel_kml, 2);
	}

	/**
	 * @return the fuel_cost
	 */
	public Double getFuel_cost() {
		return fuel_cost;
	}

	/**
	 * @param fuel_cost
	 *            the fuel_cost to set
	 */
	public void setFuel_cost(Double fuel_cost) {
		this.fuel_cost = Utility.round(fuel_cost, 2);
	}

	/**
	 * @return the vendor_location
	 */
	public String getVendor_location() {
		return vendor_location;
	}

	/**
	 * @param vendor_location
	 *            the vendor_location to set
	 */
	public void setVendor_location(String vendor_location) {
		this.vendor_location = vendor_location;
	}

	/**
	 * @return the fuel_from
	 */
	public String getFuel_from() {
		return fuel_from;
	}

	/**
	 * @param fuel_from
	 *            the fuel_from to set
	 */
	public void setFuel_from(String fuel_from) {
		this.fuel_from = fuel_from;
	}

	/**
	 * @return the fuel_to
	 */
	public String getFuel_to() {
		return fuel_to;
	}

	/**
	 * @param fuel_to
	 *            the fuel_to to set
	 */
	public void setFuel_to(String fuel_to) {
		this.fuel_to = fuel_to;
	}

	/**
	 * @return the vehicle_group
	 */
	public String getVehicle_group() {
		return vehicle_group;
	}

	/**
	 * @param vehicle_group
	 *            the vehicle_group to set
	 */
	public void setVehicle_group(String vehicle_group) {
		this.vehicle_group = vehicle_group;
	}

	/**
	 * @return the driver_id
	 */
	public Integer getDriver_id() {
		return driver_id;
	}

	/**
	 * @param driver_id
	 *            the driver_id to set
	 */
	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}

	/**
	 * @return the driver_name
	 */
	public String getDriver_name() {
		return driver_name;
	}

	/**
	 * @param driver_name
	 *            the driver_name to set
	 */
	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	/**
	 * @return the fuel_payment
	 */
	public String getFuel_payment() {
		return fuel_payment;
	}

	/**
	 * @param fuel_payment
	 *            the fuel_payment to set
	 */
	public void setFuel_payment(String fuel_payment) {
		this.fuel_payment = fuel_payment;
	}

	/**
	 * @return the fuel_vendor_paymentdate
	 */
	public Date getFuel_vendor_paymentdate() {
		return fuel_vendor_paymentdate;
	}

	/**
	 * @param fuel_vendor_paymentdate
	 *            the fuel_vendor_paymentdate to set
	 */
	public void setFuel_vendor_paymentdate(Date fuel_vendor_paymentdate) {
		this.fuel_vendor_paymentdate = fuel_vendor_paymentdate;
	}

	/**
	 * @return the fuel_vendor_paymode
	 */
	public String getFuel_vendor_paymode() {
		return fuel_vendor_paymode;
	}

	/**
	 * @param fuel_vendor_paymode
	 *            the fuel_vendor_paymode to set
	 */
	public void setFuel_vendor_paymode(String fuel_vendor_paymode) {
		this.fuel_vendor_paymode = fuel_vendor_paymode;
	}

	/**
	 * @return the vehicle_Ownership
	 */
	public String getVehicle_Ownership() {
		return vehicle_Ownership;
	}

	/**
	 * @param vehicle_Ownership
	 *            the vehicle_Ownership to set
	 */
	public void setVehicle_Ownership(String vehicle_Ownership) {
		this.vehicle_Ownership = vehicle_Ownership;
	}

	/**
	 * @return the driver_empnumber
	 */
	public String getDriver_empnumber() {
		return driver_empnumber;
	}

	/**
	 * @param driver_empnumber
	 *            the driver_empnumber to set
	 */
	public void setDriver_empnumber(String driver_empnumber) {
		this.driver_empnumber = driver_empnumber;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public String getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the vehicle_odometerchange
	 */
	public Integer getVehicle_odometerchange() {
		return vehicle_odometerchange;
	}

	/**
	 * @param vehicle_odometerchange
	 *            the vehicle_odometerchange to set
	 */
	public void setVehicle_odometerchange(Integer vehicle_odometerchange) {
		this.vehicle_odometerchange = vehicle_odometerchange;
	}

	/**
	 * @return the fuel_TripsheetID
	 */
	public Long getFuel_TripsheetID() {
		return fuel_TripsheetID;
	}

	/**
	 * @param fuel_TripsheetID
	 *            the fuel_TripsheetID to set
	 */
	public void setFuel_TripsheetID(Long fuel_TripsheetID) {
		this.fuel_TripsheetID = fuel_TripsheetID;
	}

	public Long getFuel_TripsheetNumber() {
		return fuel_TripsheetNumber;
	}

	public void setFuel_TripsheetNumber(Long fuel_TripsheetNumber) {
		this.fuel_TripsheetNumber = fuel_TripsheetNumber;
	}

	/**
	 * @return the fuel_document
	 */
	public boolean isFuel_document() {
		return fuel_document;
	}

	/**
	 * @param fuel_document
	 *            the fuel_document to set
	 */
	public void setFuel_document(boolean fuel_document) {
		this.fuel_document = fuel_document;
	}

	/**
	 * @return the fuel_document_id
	 */
	public Long getFuel_document_id() {
		return fuel_document_id;
	}

	/**
	 * @param fuel_document_id
	 *            the fuel_document_id to set
	 */
	public void setFuel_document_id(Long fuel_document_id) {
		this.fuel_document_id = fuel_document_id;
	}

	public long getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	/**
	 * @return the paymentTypeId
	 */
	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	/**
	 * @param paymentTypeId the paymentTypeId to set
	 */
	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	/**
	 * @return the vehicle_OwnershipId
	 */
	public short getVehicle_OwnershipId() {
		return vehicle_OwnershipId;
	}

	/**
	 * @param vehicle_OwnershipId the vehicle_OwnershipId to set
	 */
	public void setVehicle_OwnershipId(short vehicle_OwnershipId) {
		this.vehicle_OwnershipId = vehicle_OwnershipId;
	}

	/**
	 * @return the fuelTypeId
	 */
	public short getFuelTypeId() {
		return fuelTypeId;
	}

	/**
	 * @param fuelTypeId the fuelTypeId to set
	 */
	public void setFuelTypeId(short fuelTypeId) {
		this.fuelTypeId = fuelTypeId;
	}

	/**
	 * @return the fuel_vendor_paymodeId
	 */
	public short getFuel_vendor_paymodeId() {
		return fuel_vendor_paymodeId;
	}

	/**
	 * @param fuel_vendor_paymodeId the fuel_vendor_paymodeId to set
	 */
	public void setFuel_vendor_paymodeId(short fuel_vendor_paymodeId) {
		this.fuel_vendor_paymodeId = fuel_vendor_paymodeId;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Date getFuel_D_date() {
		return fuel_D_date;
	}

	public void setFuel_D_date(Date fuel_D_date) {
		this.fuel_D_date = fuel_D_date;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the lastupdatedOn
	 */
	public Date getLastupdatedOn() {
		return lastupdatedOn;
	}

	/**
	 * @param lastupdatedOn the lastupdatedOn to set
	 */
	public void setLastupdatedOn(Date lastupdatedOn) {
		this.lastupdatedOn = lastupdatedOn;
	}

	public Double getGpsOdometer() {
		return gpsOdometer;
	}


	public void setGpsOdometer(Double gpsOdometer) {
		this.gpsOdometer = Utility.round(gpsOdometer, 2);
	}
	

	public Integer getSecDriverID() {
		return secDriverID;
	}


	public void setSecDriverID(Integer secDriverID) {
		this.secDriverID = secDriverID;
	}


	public Integer getCleanerID() {
		return cleanerID;
	}


	public void setCleanerID(Integer cleanerID) {
		this.cleanerID = cleanerID;
	}


	public Integer getRouteID() {
		return routeID;
	}


	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}
	

	public String getFuelNumber() {
		return fuelNumber;
	}


	public void setFuelNumber(String fuelNumber) {
		this.fuelNumber = fuelNumber;
	}


	public String getFuelSecDriverName() {
		return fuelSecDriverName;
	}


	public void setFuelSecDriverName(String fuelSecDriverName) {
		this.fuelSecDriverName = fuelSecDriverName;
	}


	public String getFuelCleanerName() {
		return fuelCleanerName;
	}


	public void setFuelCleanerName(String fuelCleanerName) {
		this.fuelCleanerName = fuelCleanerName;
	}


	public String getFuelRouteName() {
		return fuelRouteName;
	}


	public void setFuelRouteName(String fuelRouteName) {
		this.fuelRouteName = fuelRouteName;
	}


	public String getFuelCost() {
		return fuelCost;
	}


	public void setFuelCost(String fuelCost) {
		this.fuelCost = fuelCost;
	}


	public String getTripSheetNumber() {
		return tripSheetNumber;
	}


	public void setTripSheetNumber(String tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public Double getVehicle_ExpectedMileage() {
		return vehicle_ExpectedMileage;
	}


	public void setVehicle_ExpectedMileage(Double vehicle_ExpectedMileage) {
		this.vehicle_ExpectedMileage = Utility.round(vehicle_ExpectedMileage, 2);
	}


	public Double getVehicle_ExpectedMileage_to() {
		return vehicle_ExpectedMileage_to;
	}


	public void setVehicle_ExpectedMileage_to(Double vehicle_ExpectedMileage_to) {
		this.vehicle_ExpectedMileage_to = Utility.round(vehicle_ExpectedMileage_to, 2);
	}


	public Date getFuelDateTime() {
		return fuelDateTime;
	}


	public void setFuelDateTime(Date fuelDateTime) {
		this.fuelDateTime = fuelDateTime;
	}


	public String getFuelDateTimeStr() {
		return fuelDateTimeStr;
	}


	public void setFuelDateTimeStr(String fuelDateTimeStr) {
		this.fuelDateTimeStr = fuelDateTimeStr;
	}

	
	public String getFuelBase64Document() {
		return fuelBase64Document;
	}


	public void setFuelBase64Document(String fuelBase64Document) {
		this.fuelBase64Document = fuelBase64Document;
	}


	public String getVehicleGroupIdStr() {
		return vehicleGroupIdStr;
	}


	public void setVehicleGroupIdStr(String vehicleGroupIdStr) {
		this.vehicleGroupIdStr = vehicleGroupIdStr;
	}


	public Double getFuelPriceDiff() {
		return fuelPriceDiff;
	}


	public void setFuelPriceDiff(Double fuelPriceDiff) {
		this.fuelPriceDiff = Utility.round(fuelPriceDiff, 2);
	}


	public String getTallyCompanyName() {
		return tallyCompanyName;
	}


	public void setTallyCompanyName(String tallyCompanyName) {
		this.tallyCompanyName = tallyCompanyName;
	}


	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}


	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}


	public Double getGpsUsageKM() {
		return gpsUsageKM;
	}


	public void setGpsUsageKM(Double gpsUsageKM) {
		this.gpsUsageKM = Utility.round(gpsUsageKM, 2);
	}
	

	public boolean isCreatingBackDateFuel() {
		return creatingBackDateFuel;
	}


	public void setCreatingBackDateFuel(boolean creatingBackDateFuel) {
		this.creatingBackDateFuel = creatingBackDateFuel;
	}


	public boolean isNextFuelEntryFound() {
		return nextFuelEntryFound;
	}


	public void setNextFuelEntryFound(boolean nextFuelEntryFound) {
		this.nextFuelEntryFound = nextFuelEntryFound;
	}
	

	public long getNextFuelEntryFuelId() {
		return nextFuelEntryFuelId;
	}


	public void setNextFuelEntryFuelId(long nextFuelEntryFuelId) {
		this.nextFuelEntryFuelId = nextFuelEntryFuelId;
	}


	public String getMeter_attributes_str() {
		return meter_attributes_str;
	}


	public void setMeter_attributes_str(String meter_attributes_str) {
		this.meter_attributes_str = meter_attributes_str;
	}


	public Integer getVehicle_ExpectedOdameter() {
		return vehicle_ExpectedOdameter;
	}


	public void setVehicle_ExpectedOdameter(Integer vehicle_ExpectedOdameter) {
		this.vehicle_ExpectedOdameter = vehicle_ExpectedOdameter;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public long getCountOfFEOnEachVehicle() {
		return countOfFEOnEachVehicle;
	}

	public void setCountOfFEOnEachVehicle(long countOfFEOnEachVehicle) {
		this.countOfFEOnEachVehicle = countOfFEOnEachVehicle;
	}


	public double getVendorApprovedAmount() {
		return vendorApprovedAmount;
	}


	public void setVendorApprovedAmount(double vendorApprovedAmount) {
		this.vendorApprovedAmount = Utility.round(vendorApprovedAmount, 2);
	}

	public String getFirstDriverFatherName() {
		return firstDriverFatherName;
	}

	public String getFileExtension() {
		return fileExtension;
	}


	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}


	public short getOwnPetrolPump() {
		return ownPetrolPump;
	}


	public void setOwnPetrolPump(short ownPetrolPump) {
		this.ownPetrolPump = ownPetrolPump;
	}



	public void setFirstDriverFatherName(String firstDriverFatherName) {
		this.firstDriverFatherName = firstDriverFatherName;
	}


	public String getFirstDriverLastName() {
		return firstDriverLastName;
	}


	public void setFirstDriverLastName(String firstDriverLastName) {
		this.firstDriverLastName = firstDriverLastName;
	}


	public String getSecDriverFatherName() {
		return secDriverFatherName;
	}


	public void setSecDriverFatherName(String secDriverFatherName) {
		this.secDriverFatherName = secDriverFatherName;
	}


	public String getSecDriverLastName() {
		return secDriverLastName;
	}


	public void setSecDriverLastName(String secDriverLastName) {
		this.secDriverLastName = secDriverLastName;
	}


	public Long getFuelInvoiceId() {
		return fuelInvoiceId;
	}


	public void setFuelInvoiceId(Long fuelInvoiceId) {
		this.fuelInvoiceId = fuelInvoiceId;
	}


	public Double getGpKMPL() {
		return gpKMPL;
	}


	public void setGpKMPL(Double gpKMPL) {
		this.gpKMPL = Utility.round(gpKMPL, 2);
	}
	

	public Short getPaidById() {
		return paidById;
	}


	public void setPaidById(Short paidById) {
		this.paidById = paidById;
	}


	public Long getPaidByBranchId() {
		return paidByBranchId;
	}


	public void setPaidByBranchId(Long paidByBranchId) {
		this.paidByBranchId = paidByBranchId;
	}


	@Override
	public String toString() {
		return "FuelDto [fuel_id=" + fuel_id + ", fuel_Number=" + fuel_Number + ", fuelNumber=" + fuelNumber + ", vid="
				+ vid + ", vehicle_registration=" + vehicle_registration + ", vehicle_Ownership=" + vehicle_Ownership
				+ ", vehicle_odometerchange=" + vehicle_odometerchange + ", fuel_date=" + fuel_date + ", fuel_D_date="
				+ fuel_D_date + ", fuel_meter=" + fuel_meter + ", fuel_meter_old=" + fuel_meter_old
				+ ", fuel_meter_attributes=" + fuel_meter_attributes + ", meter_attributes_str=" + meter_attributes_str
				+ ", fuel_type=" + fuel_type + ", vehicle_group=" + vehicle_group + ", fuel_liters=" + fuel_liters
				+ ", fuel_price=" + fuel_price + ", vendor_id=" + vendor_id + ", vendor_name=" + vendor_name
				+ ", isAutoVendor=" + isAutoVendor + ", vendor_location=" + vendor_location + ", fuel_payment="
				+ fuel_payment + ", fuel_reference=" + fuel_reference + ", driver_id=" + driver_id + ", driver_name="
				+ driver_name + ", fuelSecDriverName=" + fuelSecDriverName + ", fuelCleanerName=" + fuelCleanerName
				+ ", fuelRouteName=" + fuelRouteName + ", driver_empnumber=" + driver_empnumber + ", fuel_personal="
				+ fuel_personal + ", fuel_tank=" + fuel_tank + ", fuel_tank_partial=" + fuel_tank_partial
				+ ", fuel_comments=" + fuel_comments + ", fuel_usage=" + fuel_usage + ", fuel_amount=" + fuel_amount
				+ ", fuel_kml=" + fuel_kml + ", fuel_cost=" + fuel_cost + ", fuelCost=" + fuelCost
				+ ", fuel_TripsheetID=" + fuel_TripsheetID + ", fuel_TripsheetNumber=" + fuel_TripsheetNumber
				+ ", fuel_document=" + fuel_document + ", fuel_document_id=" + fuel_document_id + ", fuel_from="
				+ fuel_from + ", fuel_to=" + fuel_to + ", fuel_vendor_paymentdate=" + fuel_vendor_paymentdate
				+ ", fuel_vendor_paymode=" + fuel_vendor_paymode + ", createdBy=" + createdBy + ", created=" + created
				+ ", lastModifiedBy=" + lastModifiedBy + ", lastupdated=" + lastupdated + ", vehicleGroupId="
				+ vehicleGroupId + ", paymentTypeId=" + paymentTypeId + ", vehicle_OwnershipId=" + vehicle_OwnershipId
				+ ", fuelTypeId=" + fuelTypeId + ", fuel_vendor_paymodeId=" + fuel_vendor_paymodeId + ", createdById="
				+ createdById + ", lastModifiedById=" + lastModifiedById + ", companyId=" + companyId + ", createdOn="
				+ createdOn + ", lastupdatedOn=" + lastupdatedOn + ", fuel_vendor_approvalID=" + fuel_vendor_approvalID
				+ ", previousFuelAmount=" + previousFuelAmount + ", previousFuelDate=" + previousFuelDate
				+ ", gpsOdometer=" + gpsOdometer + ", gpsUsageKM=" + gpsUsageKM + ", typeOfPaymentId=" + typeOfPaymentId
				+ ", secDriverID=" + secDriverID + ", cleanerID=" + cleanerID + ", routeID=" + routeID + ", paidAmount="
				+ paidAmount + ", balanceAmount=" + balanceAmount + ", tripSheetNumber=" + tripSheetNumber
				+ ", vehicle_ExpectedMileage=" + vehicle_ExpectedMileage + ", vehicle_ExpectedMileage_to="
				+ vehicle_ExpectedMileage_to + ", fuelDateTime=" + fuelDateTime + ", fuelDateTimeStr=" + fuelDateTimeStr
				+ ", fuelDateTimeStr2=" + fuelDateTimeStr2 + ", fuelTime=" + fuelTime + ", fuelBase64Document="
				+ fuelBase64Document + ", vehicleGroupIdStr=" + vehicleGroupIdStr + ", fuelPriceDiff=" + fuelPriceDiff
				+ ", tallyCompanyName=" + tallyCompanyName + ", tallyCompanyId=" + tallyCompanyId
				+ ", creatingBackDateFuel=" + creatingBackDateFuel + ", nextFuelEntryFound=" + nextFuelEntryFound
				+ ", nextFuelEntryFuelId=" + nextFuelEntryFuelId + ", vehicle_ExpectedOdameter="
				+ vehicle_ExpectedOdameter + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", countOfFEOnEachVehicle=" + countOfFEOnEachVehicle + ", vendorApprovedAmount="
				+ vendorApprovedAmount + ", fileExtension=" + fileExtension + ", ownPetrolPump=" + ownPetrolPump
				+ ", firstDriverFatherName=" + firstDriverFatherName + ", firstDriverLastName=" + firstDriverLastName
				+ ", secDriverFatherName=" + secDriverFatherName + ", secDriverLastName=" + secDriverLastName
				+ ", fuelInvoiceId=" + fuelInvoiceId + ", markForDelete=" + markForDelete + ", userId=" + userId
				+ ", userName=" + userName + ", fuelVendorPaymentDate=" + fuelVendorPaymentDate + ", gpKMPL=" + gpKMPL
				+ ", paidById=" + paidById + ", paidByBranchId=" + paidByBranchId + ", paidByBranchStr="
				+ paidByBranchStr + ", moduleIdentifire=" + moduleIdentifire + ", tripClosingKM=" + tripClosingKM
				+ ", tripStutesId=" + tripStutesId + "]";
	}


	
	 


}
