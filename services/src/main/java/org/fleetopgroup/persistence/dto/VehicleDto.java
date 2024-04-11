package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */

public class VehicleDto {

	private Integer vid;
	private String vehicle_registration;
	private String vehicle_chasis;
	private String vehicle_engine;
	private Integer vehicle_TypeID;
	private String vehicle_Type;
	private Integer vehicle_year;
	private String vehicle_maker;
	private String vehicle_Model;
	private String vehicle_registrationState;
	private String vehicle_RegisterDate;
	private String vehicle_Registeredupto;
	private Integer vehicle_statusID;
	private String vehicle_Status;
	private Integer vehicle_groupID;
	private String vehicle_Group;
	private String vehicle_RouteName;
	private String vehicle_Ownership;
	private String vehicle_Location;
	private String vehicle_Color;
	private String vehicle_Class;
	private String vehicle_body;
	private String vehicle_actype;
	private String vehicle_Cylinders;
	private String vehicle_CubicCapacity;
	private String vehicle_Power;
	private String vehicle_wheelBase;
	private String vehicle_SeatCapacity;
	private String vehicle_UnladenWeight;
	private String vehicle_LadenWeight;
	private String vehicle_Fuel;
	private Integer vehicle_FuelId;
	private Integer vehicle_FuelTank1;
	private Integer vehicle_Oil;
	private String vehicle_Meter;
	private String vehicle_MeterUnit;
	private Integer vehicle_Odometer;
	private Integer vehicle_odometerchange;
	private Double vehicle_ExpectedMileage;
	private Double vehicle_ExpectedMileage_to;
	private String vehicle_FuelUnit;
	private Long tripSheetNumber;
	private String createdBy;
	private String created;
	private Date   createdOn;
	private String lastModifiedBy;
	private Date   lastupdatedOn;
	private String lastupdated;
	private boolean markForDelete;
	private boolean mfd;
	private Integer vehicle_photoid;
	private Long tripSheetID;
	private short	vStatusId;
	private long	vehicleGroupId;
	private String  vehicleFuelId;
	private short	vehicleFuelUnitId;
	private short	vehicleMeterUnitId;
	private long	vehicleTypeId;
	private short	acTypeId;
	private short	vehicleOwnerShipId;
	private Integer routeID;
	private Long 	workOrder_Number;
	private Integer vehicle_ExpectedOdameter;
	private Integer vehicleExpectedKm;
	private boolean serviceOverDue;
	private String  serviceNumbers;
	private Integer routeApproximateKM;
	private String vendorName;
	private String vehicleGPSId;
	private double	gpsOdameter;
	private Integer	companyId;
	private String companyName;
	private Long	vehicleActiveCount;
	private Long	vehicleInActiveCount;
	private Long	vehicleSoldCount;
	private Integer vehicleCompanyId;

	private boolean gpsWorking;
	private String	gpsLocation;
	private Double	lastUreaOdometer;
	private Long serviceEntries_id;
	private Double totalserviceROUND_cost;
	private Long workorders_id;
	private Double totalworkorder_cost;
	private long vehicleServiceEntriesCount;
	private int vehicleWorkOrdersCount;
	private double vehicleTotalCost;
	private Integer partlocation_id;
	private String partlocation_name;
	private Long workorders_Number;
	private String woStartDate;
	private String woDueDate;
	private String wocompletedDate;
	private Long serviceEntriesNumber;
	private String seInvoiceDate;
	
	private Integer fuelMeter;
	private Integer lastFuelOdometer;
	private Double	driverMonthlySalary;
	//private String	tallyCompanyName;
	private String	ledgerName;
	private Integer	gpsVendorId;
	private String	gpsVendorName;
	private String	driverFirstName;
	private String	driverLastName;
	private String	driverEmpName;
	private String	mobileNumber;
	private Date 	fuelDateTime;
	private Date 	date;
	private Long 	id;
	private Long 	number;
	private String 	fuelDateStr;
	private Long 	fuel_id;
	private Long 	fuel_Number;
	private Double  fuel_liters;
	private Integer  moduleOdometer;
	private boolean isOdometerReading;
	private Double	tripStartDiesel;
	private Double	tripEndDiesel;
	private long 	busId;
	private long 	deviceId;
	private Date 	ureaDate;
	private String 	ureaDateStr;
	private Long 	ureaEntriesNumber;
	private Double	ureaOdometer;
	private Double	ureaLiters;
	private long 	ureaEntriesId;
    private Long	serviceProgramId;
	private String	serviceProgramName;
	private String	driverFatherName;
	private long createdById;
	private long modifiedById;
	private Integer branchId;
	private Integer vehicleBodyMakerId;
	private String bodyMakerName;
	private Long	vehicleModelId;
	private Long	vehicleMakerId;
	
    
	private String penaltyCount;
	private String accCount;				
	private String dseCount;				
	private String woCount;				
	private String vehicleIssueCount; 	
	private String breakDownIssueCount;	
	private String milageFuel;			
	private String consumptionFuel;
	private String vehicleTollId;
	private Integer driverId;
	private String subCompanyName;
	private Long subCompanyId;
	private String driverMonthlyBhatta;
	
	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(long createdById) {
		this.createdById = createdById;
	}

	public long getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(long modifiedById) {
		this.modifiedById = modifiedById;
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

	public String getVehicleTollId() {
		return vehicleTollId;
	}

	public void setVehicleTollId(String vehicleTollId) {
		this.vehicleTollId = vehicleTollId;
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
	 * @return the vehicle_chasis
	 */
	public String getVehicle_chasis() {
		return vehicle_chasis;
	}

	/**
	 * @param vehicle_chasis
	 *            the vehicle_chasis to set
	 */
	public void setVehicle_chasis(String vehicle_chasis) {
		this.vehicle_chasis = vehicle_chasis;
	}

	/**
	 * @return the vehicle_engine
	 */
	public String getVehicle_engine() {
		return vehicle_engine;
	}

	/**
	 * @param vehicle_engine
	 *            the vehicle_engine to set
	 */
	public void setVehicle_engine(String vehicle_engine) {
		this.vehicle_engine = vehicle_engine;
	}

	/**
	 * @return the vehicle_TypeID
	 */
	public Integer getVehicle_TypeID() {
		return vehicle_TypeID;
	}

	/**
	 * @param vehicle_TypeID
	 *            the vehicle_TypeID to set
	 */
	public void setVehicle_TypeID(Integer vehicle_TypeID) {
		this.vehicle_TypeID = vehicle_TypeID;
	}

	/**
	 * @return the vehicle_year
	 */
	public Integer getVehicle_year() {
		return vehicle_year;
	}

	/**
	 * @param vehicle_year
	 *            the vehicle_year to set
	 */
	public void setVehicle_year(Integer vehicle_year) {
		this.vehicle_year = vehicle_year;
	}

	/**
	 * @return the vehicle_maker
	 */
	public String getVehicle_maker() {
		return vehicle_maker;
	}

	/**
	 * @param vehicle_maker
	 *            the vehicle_maker to set
	 */
	public void setVehicle_maker(String vehicle_maker) {
		this.vehicle_maker = vehicle_maker;
	}

	/**
	 * @return the vehicle_registrationState
	 */
	public String getVehicle_registrationState() {
		return vehicle_registrationState;
	}

	/**
	 * @param vehicle_registrationState
	 *            the vehicle_registrationState to set
	 */
	public void setVehicle_registrationState(String vehicle_registrationState) {
		this.vehicle_registrationState = vehicle_registrationState;
	}

	/**
	 * @return the vehicle_RegisterDate
	 */
	public String getVehicle_RegisterDate() {
		return vehicle_RegisterDate;
	}

	/**
	 * @param vehicle_RegisterDate
	 *            the vehicle_RegisterDate to set
	 */
	public void setVehicle_RegisterDate(String vehicle_RegisterDate) {
		this.vehicle_RegisterDate = vehicle_RegisterDate;
	}

	/**
	 * @return the vehicle_Registeredupto
	 */
	public String getVehicle_Registeredupto() {
		return vehicle_Registeredupto;
	}

	/**
	 * @param vehicle_Registeredupto
	 *            the vehicle_Registeredupto to set
	 */
	public void setVehicle_Registeredupto(String vehicle_Registeredupto) {
		this.vehicle_Registeredupto = vehicle_Registeredupto;
	}

	/**
	 * @return the vehicle_statusID
	 */
	public Integer getVehicle_statusID() {
		return vehicle_statusID;
	}

	/**
	 * @param vehicle_statusID
	 *            the vehicle_statusID to set
	 */
	public void setVehicle_statusID(Integer vehicle_statusID) {
		this.vehicle_statusID = vehicle_statusID;
	}

	/**
	 * @return the vehicle_groupID
	 */
	public Integer getVehicle_groupID() {
		return vehicle_groupID;
	}

	/**
	 * @param vehicle_groupID
	 *            the vehicle_groupID to set
	 */
	public void setVehicle_groupID(Integer vehicle_groupID) {
		this.vehicle_groupID = vehicle_groupID;
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
	 * @return the vehicle_Color
	 */
	public String getVehicle_Color() {
		return vehicle_Color;
	}

	/**
	 * @param vehicle_Color
	 *            the vehicle_Color to set
	 */
	public void setVehicle_Color(String vehicle_Color) {
		this.vehicle_Color = vehicle_Color;
	}

	/**
	 * @return the vehicle_Class
	 */
	public String getVehicle_Class() {
		return vehicle_Class;
	}

	/**
	 * @param vehicle_Class
	 *            the vehicle_Class to set
	 */
	public void setVehicle_Class(String vehicle_Class) {
		this.vehicle_Class = vehicle_Class;
	}

	/**
	 * @return the vehicle_body
	 */
	public String getVehicle_body() {
		return vehicle_body;
	}

	/**
	 * @param vehicle_body
	 *            the vehicle_body to set
	 */
	public void setVehicle_body(String vehicle_body) {
		this.vehicle_body = vehicle_body;
	}

	/**
	 * @return the vehicle_Cylinders
	 */
	public String getVehicle_Cylinders() {
		return vehicle_Cylinders;
	}

	/**
	 * @param vehicle_Cylinders
	 *            the vehicle_Cylinders to set
	 */
	public void setVehicle_Cylinders(String vehicle_Cylinders) {
		this.vehicle_Cylinders = vehicle_Cylinders;
	}

	/**
	 * @return the vehicle_CubicCapacity
	 */
	public String getVehicle_CubicCapacity() {
		return vehicle_CubicCapacity;
	}

	/**
	 * @param vehicle_CubicCapacity
	 *            the vehicle_CubicCapacity to set
	 */
	public void setVehicle_CubicCapacity(String vehicle_CubicCapacity) {
		this.vehicle_CubicCapacity = vehicle_CubicCapacity;
	}

	/**
	 * @return the vehicle_Power
	 */
	public String getVehicle_Power() {
		return vehicle_Power;
	}

	/**
	 * @param vehicle_Power
	 *            the vehicle_Power to set
	 */
	public void setVehicle_Power(String vehicle_Power) {
		this.vehicle_Power = vehicle_Power;
	}

	/**
	 * @return the vehicle_wheelBase
	 */
	public String getVehicle_wheelBase() {
		return vehicle_wheelBase;
	}

	/**
	 * @param vehicle_wheelBase
	 *            the vehicle_wheelBase to set
	 */
	public void setVehicle_wheelBase(String vehicle_wheelBase) {
		this.vehicle_wheelBase = vehicle_wheelBase;
	}

	/**
	 * @return the vehicle_SeatCapacity
	 */
	public String getVehicle_SeatCapacity() {
		return vehicle_SeatCapacity;
	}

	/**
	 * @param vehicle_SeatCapacity
	 *            the vehicle_SeatCapacity to set
	 */
	public void setVehicle_SeatCapacity(String vehicle_SeatCapacity) {
		this.vehicle_SeatCapacity = vehicle_SeatCapacity;
	}

	/**
	 * @return the vehicle_UnladenWeight
	 */
	public String getVehicle_UnladenWeight() {
		return vehicle_UnladenWeight;
	}

	/**
	 * @param vehicle_UnladenWeight
	 *            the vehicle_UnladenWeight to set
	 */
	public void setVehicle_UnladenWeight(String vehicle_UnladenWeight) {
		this.vehicle_UnladenWeight = vehicle_UnladenWeight;
	}

	/**
	 * @return the vehicle_LadenWeight
	 */
	public String getVehicle_LadenWeight() {
		return vehicle_LadenWeight;
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
	 * @param vehicle_LadenWeight
	 *            the vehicle_LadenWeight to set
	 */
	public void setVehicle_LadenWeight(String vehicle_LadenWeight) {
		this.vehicle_LadenWeight = vehicle_LadenWeight;
	}

	/**
	 * @return the vehicle_FuelId
	 */
	public Integer getVehicle_FuelId() {
		return vehicle_FuelId;
	}

	/**
	 * @param vehicle_FuelId
	 *            the vehicle_FuelId to set
	 */
	public void setVehicle_FuelId(Integer vehicle_FuelId) {
		this.vehicle_FuelId = vehicle_FuelId;
	}

	/**
	 * @return the vehicle_FuelTank1
	 */
	public Integer getVehicle_FuelTank1() {
		return vehicle_FuelTank1;
	}

	/**
	 * @param vehicle_FuelTank1
	 *            the vehicle_FuelTank1 to set
	 */
	public void setVehicle_FuelTank1(Integer vehicle_FuelTank1) {
		this.vehicle_FuelTank1 = vehicle_FuelTank1;
	}

	/**
	 * @return the vehicle_Oil
	 */
	public Integer getVehicle_Oil() {
		return vehicle_Oil;
	}

	/**
	 * @param vehicle_Oil
	 *            the vehicle_Oil to set
	 */
	public void setVehicle_Oil(Integer vehicle_Oil) {
		this.vehicle_Oil = vehicle_Oil;
	}

	/**
	 * @return the vehicle_Meter
	 */
	public String getVehicle_Meter() {
		return vehicle_Meter;
	}

	/**
	 * @param vehicle_Meter
	 *            the vehicle_Meter to set
	 */
	public void setVehicle_Meter(String vehicle_Meter) {
		this.vehicle_Meter = vehicle_Meter;
	}

	/**
	 * @return the vehicle_Odometer
	 */
	public Integer getVehicle_Odometer() {
		return vehicle_Odometer;
	}

	/**
	 * @param vehicle_Odometer
	 *            the vehicle_Odometer to set
	 */
	public void setVehicle_Odometer(Integer vehicle_Odometer) {
		this.vehicle_Odometer = vehicle_Odometer;
	}

	/**
	 * @return the vehicle_FuelUnit
	 */
	public String getVehicle_FuelUnit() {
		return vehicle_FuelUnit;
	}

	/**
	 * @param vehicle_FuelUnit
	 *            the vehicle_FuelUnit to set
	 */
	public void setVehicle_FuelUnit(String vehicle_FuelUnit) {
		this.vehicle_FuelUnit = vehicle_FuelUnit;
	}

	/**
	 * @return the vehicle_photoid
	 */
	public Integer getVehicle_photoid() {
		return vehicle_photoid;
	}

	/**
	 * @param vehicle_photoid
	 *            the vehicle_photoid to set
	 */
	public void setVehicle_photoid(Integer vehicle_photoid) {
		this.vehicle_photoid = vehicle_photoid;
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
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the mfd
	 */
	public boolean isMfd() {
		return mfd;
	}

	/**
	 * @param mfd
	 *            the mfd to set
	 */
	public void setMfd(boolean mfd) {
		this.mfd = mfd;
	}

	/**
	 * @return the vehicle_actype
	 */
	public String getVehicle_actype() {
		return vehicle_actype;
	}

	/**
	 * @param vehicle_actype
	 *            the vehicle_actype to set
	 */
	public void setVehicle_actype(String vehicle_actype) {
		this.vehicle_actype = vehicle_actype;
	}

	/**
	 * @return the vehicle_Fuel
	 */
	public String getVehicle_Fuel() {
		return vehicle_Fuel;
	}

	/**
	 * @param vehicle_Fuel
	 *            the vehicle_Fuel to set
	 */
	public void setVehicle_Fuel(String vehicle_Fuel) {
		this.vehicle_Fuel = vehicle_Fuel;
	}

	/**
	 * @return the vehicle_RouteName
	 */
	public String getVehicle_RouteName() {
		return vehicle_RouteName;
	}

	/**
	 * @param vehicle_RouteName
	 *            the vehicle_RouteName to set
	 */
	public void setVehicle_RouteName(String vehicle_RouteName) {
		this.vehicle_RouteName = vehicle_RouteName;
	}

	/**
	 * @return the vehicle_Type
	 */
	public String getVehicle_Type() {
		return vehicle_Type;
	}

	/**
	 * @param vehicle_Type
	 *            the vehicle_Type to set
	 */
	public void setVehicle_Type(String vehicle_Type) {
		this.vehicle_Type = vehicle_Type;
	}

	/**
	 * @return the vehicle_Model
	 */
	public String getVehicle_Model() {
		return vehicle_Model;
	}

	/**
	 * @param vehicle_Model
	 *            the vehicle_Model to set
	 */
	public void setVehicle_Model(String vehicle_Model) {
		this.vehicle_Model = vehicle_Model;
	}

	/**
	 * @return the vehicle_Status
	 */
	public String getVehicle_Status() {
		return vehicle_Status;
	}

	/**
	 * @param vehicle_Status
	 *            the vehicle_Status to set
	 */
	public void setVehicle_Status(String vehicle_Status) {
		this.vehicle_Status = vehicle_Status;
	}

	/**
	 * @return the vehicle_Group
	 */
	public String getVehicle_Group() {
		return vehicle_Group;
	}

	/**
	 * @param vehicle_Group
	 *            the vehicle_Group to set
	 */
	public void setVehicle_Group(String vehicle_Group) {
		this.vehicle_Group = vehicle_Group;
	}

	/**
	 * @return the vehicle_Location
	 */
	public String getVehicle_Location() {
		return vehicle_Location;
	}

	/**
	 * @param vehicle_Location
	 *            the vehicle_Location to set
	 */
	public void setVehicle_Location(String vehicle_Location) {
		this.vehicle_Location = vehicle_Location;
	}

	/**
	 * @return the vehicle_MeterUnit
	 */
	public String getVehicle_MeterUnit() {
		return vehicle_MeterUnit;
	}

	/**
	 * @param vehicle_MeterUnit
	 *            the vehicle_MeterUnit to set
	 */
	public void setVehicle_MeterUnit(String vehicle_MeterUnit) {
		this.vehicle_MeterUnit = vehicle_MeterUnit;
	}

	/**
	 * @return the vehicle_ExpectedMileage
	 */
	public Double getVehicle_ExpectedMileage() {
		return vehicle_ExpectedMileage;
	}

	/**
	 * @param vehicle_ExpectedMileage
	 *            the vehicle_ExpectedMileage to set
	 */
	public void setVehicle_ExpectedMileage(Double vehicle_ExpectedMileage) {
		this.vehicle_ExpectedMileage = Utility.round(vehicle_ExpectedMileage, 2);
	}

	public Double getVehicle_ExpectedMileage_to() {
		return vehicle_ExpectedMileage_to;
	}

	public void setVehicle_ExpectedMileage_to(Double vehicle_ExpectedMileage_to) {
		this.vehicle_ExpectedMileage_to = Utility.round(vehicle_ExpectedMileage_to, 2);
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
	 * @return the tripSheetID
	 */
	public Long getTripSheetID() {
		return tripSheetID;
	}

	/**
	 * @param tripSheetID
	 *            the tripSheetID to set
	 */
	public void setTripSheetID(Long tripSheetID) {
		this.tripSheetID = tripSheetID;
	}

	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public short getvStatusId() {
		return vStatusId;
	}

	public void setvStatusId(short vStatusId) {
		this.vStatusId = vStatusId;
	}

	public long getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	public String getVehicleFuelId() {
		return vehicleFuelId;
	}

	public void setVehicleFuelId(String vehicleFuelId) {
		this.vehicleFuelId = vehicleFuelId;
	}

	public short getVehicleFuelUnitId() {
		return vehicleFuelUnitId;
	}

	public void setVehicleFuelUnitId(short vehicleFuelUnitId) {
		this.vehicleFuelUnitId = vehicleFuelUnitId;
	}

	public short getVehicleMeterUnitId() {
		return vehicleMeterUnitId;
	}

	public void setVehicleMeterUnitId(short vehicleMeterUnitId) {
		this.vehicleMeterUnitId = vehicleMeterUnitId;
	}

	public long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public short getAcTypeId() {
		return acTypeId;
	}

	public void setAcTypeId(short acTypeId) {
		this.acTypeId = acTypeId;
	}

	public short getVehicleOwnerShipId() {
		return vehicleOwnerShipId;
	}

	public void setVehicleOwnerShipId(short vehicleOwnerShipId) {
		this.vehicleOwnerShipId = vehicleOwnerShipId;
	}

	public Integer getRouteID() {
		return routeID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastupdatedOn() {
		return lastupdatedOn;
	}

	public void setLastupdatedOn(Date lastupdatedOn) {
		this.lastupdatedOn = lastupdatedOn;
	}

	public Long getWorkOrder_Number() {
		return workOrder_Number;
	}

	public void setWorkOrder_Number(Long workOrder_Number) {
		this.workOrder_Number = workOrder_Number;
	}

	public Integer getVehicle_ExpectedOdameter() {
		return vehicle_ExpectedOdameter;
	}

	public void setVehicle_ExpectedOdameter(Integer vehicle_ExpectedOdameter) {
		this.vehicle_ExpectedOdameter = vehicle_ExpectedOdameter;
	}

	public Integer getVehicleExpectedKm() {
		return vehicleExpectedKm;
	}

	public void setVehicleExpectedKm(Integer vehicleExpectedKm) {
		this.vehicleExpectedKm = vehicleExpectedKm;
	}

	public boolean isServiceOverDue() {
		return serviceOverDue;
	}

	public void setServiceOverDue(boolean serviceOverDue) {
		this.serviceOverDue = serviceOverDue;
	}

	public String getServiceNumbers() {
		return serviceNumbers;
	}

	public void setServiceNumbers(String serviceNumbers) {
		this.serviceNumbers = serviceNumbers;
	}
	
	

	public Integer getRouteApproximateKM() {
		return routeApproximateKM;
	}

	public void setRouteApproximateKM(Integer routeApproximateKM) {
		this.routeApproximateKM = routeApproximateKM;
	}


	
	public String getGpsLocation() {
		return gpsLocation;
	}

	public void setGpsLocation(String gpsLocation) {
		this.gpsLocation = gpsLocation;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVehicleGPSId() {
		return vehicleGPSId;
	}

	public void setVehicleGPSId(String vehicleGPSId) {
		this.vehicleGPSId = vehicleGPSId;
	}

	public double getGpsOdameter() {
		return gpsOdameter;
	}

	public void setGpsOdameter(double gpsOdameter) {
		this.gpsOdameter = Utility.round(gpsOdameter, 2);
	}

	public Long getVehicleActiveCount() {
		return vehicleActiveCount;
	}

	public void setVehicleActiveCount(Long vehicleActiveCount) {
		this.vehicleActiveCount = vehicleActiveCount;
	}

	public Long getVehicleInActiveCount() {
		return vehicleInActiveCount;
	}

	public void setVehicleInActiveCount(Long vehicleInActiveCount) {
		this.vehicleInActiveCount = vehicleInActiveCount;
	}

	public Long getVehicleSoldCount() {
		return vehicleSoldCount;
	}

	public void setVehicleSoldCount(Long vehicleSoldCount) {
		this.vehicleSoldCount = vehicleSoldCount;
	}
	
	public Integer getVehicleCompanyId() {
		return vehicleCompanyId;
	}

	public void setVehicleCompanyId(Integer vehicleCompanyId) {
		this.vehicleCompanyId = vehicleCompanyId;
	}

	public boolean isGpsWorking() {
		return gpsWorking;
	}

	public void setGpsWorking(boolean gpsWorking) {
		this.gpsWorking = gpsWorking;
	}
	
	public Long getServiceEntries_id() {
		return serviceEntries_id;
	}

	public void setServiceEntries_id(Long serviceEntries_id) {
		this.serviceEntries_id = serviceEntries_id;
	}

	public Double getTotalserviceROUND_cost() {
		return totalserviceROUND_cost;
	}

	public void setTotalserviceROUND_cost(Double totalserviceROUND_cost) {
		this.totalserviceROUND_cost = Utility.round(totalserviceROUND_cost, 2);
	}

	public Long getWorkorders_id() {
		return workorders_id;
	}

	public void setWorkorders_id(Long workorders_id) {
		this.workorders_id = workorders_id;
	}

	public Double getTotalworkorder_cost() {
		return totalworkorder_cost;
	}

	public void setTotalworkorder_cost(Double totalworkorder_cost) {
		this.totalworkorder_cost = Utility.round(totalworkorder_cost, 2);
	}

	public long getVehicleServiceEntriesCount() {
		return vehicleServiceEntriesCount;
	}

	public void setVehicleServiceEntriesCount(long vehicleServiceEntriesCount) {
		this.vehicleServiceEntriesCount = vehicleServiceEntriesCount;
	}

	public int getVehicleWorkOrdersCount() {
		return vehicleWorkOrdersCount;
	}

	public void setVehicleWorkOrdersCount(int vehicleWorkOrdersCount) {
		this.vehicleWorkOrdersCount = vehicleWorkOrdersCount;
	}
	 
	 public double getVehicleTotalCost() {
		return vehicleTotalCost;
	}

	public void setVehicleTotalCost(double vehicleTotalCost) {
		this.vehicleTotalCost = Utility.round(vehicleTotalCost, 2);
	}

	public Integer getPartlocation_id() {
		return partlocation_id;
	}

	public void setPartlocation_id(Integer partlocation_id) {
		this.partlocation_id = partlocation_id;
	}

	public String getPartlocation_name() {
		return partlocation_name;
	}

	public void setPartlocation_name(String partlocation_name) {
		this.partlocation_name = partlocation_name;
	}

	public Long getWorkorders_Number() {
		return workorders_Number;
	}

	public void setWorkorders_Number(Long workorders_Number) {
		this.workorders_Number = workorders_Number;
	}

	public String getWoStartDate() {
		return woStartDate;
	}

	public void setWoStartDate(String woStartDate) {
		this.woStartDate = woStartDate;
	}

	public String getWoDueDate() {
		return woDueDate;
	}

	public void setWoDueDate(String woDueDate) {
		this.woDueDate = woDueDate;
	}

	public Long getServiceEntriesNumber() {
		return serviceEntriesNumber;
	}

	public void setServiceEntriesNumber(Long serviceEntriesNumber) {
		this.serviceEntriesNumber = serviceEntriesNumber;
	}

	public String getSeInvoiceDate() {
		return seInvoiceDate;
	}

	public void setSeInvoiceDate(String seInvoiceDate) {
		this.seInvoiceDate = seInvoiceDate;
	}

	public String getWocompletedDate() {
		return wocompletedDate;
	}

	public void setWocompletedDate(String wocompletedDate) {
		this.wocompletedDate = wocompletedDate;
	}

	public Integer getFuelMeter() {
		return fuelMeter;
	}

	public void setFuelMeter(Integer fuelMeter) {
		this.fuelMeter = fuelMeter;
	}

	public Integer getLastFuelOdometer() {
		return lastFuelOdometer;
	}

	public void setLastFuelOdometer(Integer lastFuelOdometer) {
		this.lastFuelOdometer = lastFuelOdometer;
	}

	public Double getDriverMonthlySalary() {
		return driverMonthlySalary;
	}

	public void setDriverMonthlySalary(Double driverMonthlySalary) {
		this.driverMonthlySalary = Utility.round(driverMonthlySalary,2);
	}

	
	public Double getLastUreaOdometer() {
		return lastUreaOdometer;
	}

	public void setLastUreaOdometer(Double lastUreaOdometer) {
		this.lastUreaOdometer = Utility.round(lastUreaOdometer, 2);
	}


	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	public Integer getGpsVendorId() {
		return gpsVendorId;
	}

	public void setGpsVendorId(Integer gpsVendorId) {
		this.gpsVendorId = gpsVendorId;
	}

	public String getGpsVendorName() {
		return gpsVendorName;
	}

	public void setGpsVendorName(String gpsVendorName) {
		this.gpsVendorName = gpsVendorName;
	}

	public String getDriverFirstName() {
		return driverFirstName;
	}

	public void setDriverFirstName(String driverFirstName) {
		this.driverFirstName = driverFirstName;
	}

	public String getDriverLastName() {
		return driverLastName;
	}

	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}

	public String getDriverEmpName() {
		return driverEmpName;
	}

	public void setDriverEmpName(String driverEmpName) {
		this.driverEmpName = driverEmpName;
	}

	public Date getFuelDateTime() {
		return fuelDateTime;
	}

	public void setFuelDateTime(Date fuelDateTime) {
		this.fuelDateTime = fuelDateTime;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public Long getFuel_id() {
		return fuel_id;
	}

	public void setFuel_id(Long fuel_id) {
		this.fuel_id = fuel_id;
	}

	public Long getFuel_Number() {
		return fuel_Number;
	}

	public void setFuel_Number(Long fuel_Number) {
		this.fuel_Number = fuel_Number;
	}

	public Double getFuel_liters() {
		return fuel_liters;
	}

	public void setFuel_liters(Double fuel_liters) {
		this.fuel_liters =Utility.round(fuel_liters, 2);
	}

	public boolean isOdometerReading() {
		return isOdometerReading;
	}

	public void setOdometerReading(boolean isOdometerReading) {
		this.isOdometerReading = isOdometerReading;
	}

	public Double getTripStartDiesel() {
		return tripStartDiesel;
	}

	public void setTripStartDiesel(Double tripStartDiesel) {
		this.tripStartDiesel =Utility.round(tripStartDiesel, 2);
	}

	public Double getTripEndDiesel() {
		return tripEndDiesel;
	}

	public void setTripEndDiesel(Double tripEndDiesel) {
		this.tripEndDiesel = Utility.round( tripEndDiesel, 2);
	}

	public long getBusId() {
		return busId;
	}

	public void setBusId(long busId) {
		this.busId = busId;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public Date getUreaDate() {
		return ureaDate;
	}

	public void setUreaDate(Date ureaDate) {
		this.ureaDate = ureaDate;
	}

	public Long getUreaEntriesNumber() {
		return ureaEntriesNumber;
	}

	public void setUreaEntriesNumber(Long ureaEntriesNumber) {
		this.ureaEntriesNumber = ureaEntriesNumber;
	}

	public Double getUreaOdometer() {
		return ureaOdometer;
	}

	public void setUreaOdometer(Double ureaOdometer) {
		this.ureaOdometer = Utility.round(ureaOdometer, 2);
	}

	public Double getUreaLiters() {
		return ureaLiters;
	}

	public void setUreaLiters(Double ureaLiters) {
		this.ureaLiters = Utility.round(ureaLiters,2);
	}

	public long getUreaEntriesId() {
		return ureaEntriesId;
	}

	public void setUreaEntriesId(long ureaEntriesId) {
		this.ureaEntriesId = ureaEntriesId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getServiceProgramId() {
		return serviceProgramId;
	}

	public void setServiceProgramId(Long serviceProgramId) {
		this.serviceProgramId = serviceProgramId;
	}

	public String getServiceProgramName() {
		return serviceProgramName;
	}

	public void setServiceProgramName(String serviceProgramName) {
		this.serviceProgramName = serviceProgramName;
	}

	@Override
	public String toString() {
		return "VehicleDto [vid=" + vid + ", vehicle_registration=" + vehicle_registration + ", vehicle_chasis="
				+ vehicle_chasis + ", vehicle_engine=" + vehicle_engine + ", vehicle_TypeID=" + vehicle_TypeID
				+ ", vehicle_Type=" + vehicle_Type + ", vehicle_year=" + vehicle_year + ", vehicle_maker="
				+ vehicle_maker + ", vehicle_Model=" + vehicle_Model + ", vehicle_registrationState="
				+ vehicle_registrationState + ", vehicle_RegisterDate=" + vehicle_RegisterDate
				+ ", vehicle_Registeredupto=" + vehicle_Registeredupto + ", vehicle_statusID=" + vehicle_statusID
				+ ", vehicle_Status=" + vehicle_Status + ", vehicle_groupID=" + vehicle_groupID + ", vehicle_Group="
				+ vehicle_Group + ", vehicle_RouteName=" + vehicle_RouteName + ", vehicle_Ownership="
				+ vehicle_Ownership + ", vehicle_Location=" + vehicle_Location + ", vehicle_Color=" + vehicle_Color
				+ ", vehicle_Class=" + vehicle_Class + ", vehicle_body=" + vehicle_body + ", vehicle_actype="
				+ vehicle_actype + ", vehicle_Cylinders=" + vehicle_Cylinders + ", vehicle_CubicCapacity="
				+ vehicle_CubicCapacity + ", vehicle_Power=" + vehicle_Power + ", vehicle_wheelBase="
				+ vehicle_wheelBase + ", vehicle_SeatCapacity=" + vehicle_SeatCapacity + ", vehicle_UnladenWeight="
				+ vehicle_UnladenWeight + ", vehicle_LadenWeight=" + vehicle_LadenWeight + ", vehicle_Fuel="
				+ vehicle_Fuel + ", vehicle_FuelId=" + vehicle_FuelId + ", vehicle_FuelTank1=" + vehicle_FuelTank1
				+ ", vehicle_Oil=" + vehicle_Oil + ", vehicle_Meter=" + vehicle_Meter + ", vehicle_MeterUnit="
				+ vehicle_MeterUnit + ", vehicle_Odometer=" + vehicle_Odometer + ", vehicle_odometerchange="
				+ vehicle_odometerchange + ", vehicle_ExpectedMileage=" + vehicle_ExpectedMileage
				+ ", vehicle_ExpectedMileage_to=" + vehicle_ExpectedMileage_to + ", vehicle_FuelUnit="
				+ vehicle_FuelUnit + ", tripSheetNumber=" + tripSheetNumber + ", createdBy=" + createdBy + ", created="
				+ created + ", createdOn=" + createdOn + ", lastModifiedBy=" + lastModifiedBy + ", lastupdatedOn="
				+ lastupdatedOn + ", lastupdated=" + lastupdated + ", markForDelete=" + markForDelete + ", mfd=" + mfd
				+ ", vehicle_photoid=" + vehicle_photoid + ", tripSheetID=" + tripSheetID + ", vStatusId=" + vStatusId
				+ ", vehicleGroupId=" + vehicleGroupId + ", vehicleFuelId=" + vehicleFuelId + ", vehicleFuelUnitId="
				+ vehicleFuelUnitId + ", vehicleMeterUnitId=" + vehicleMeterUnitId + ", vehicleTypeId=" + vehicleTypeId
				+ ", acTypeId=" + acTypeId + ", vehicleOwnerShipId=" + vehicleOwnerShipId + ", routeID=" + routeID
				+ ", workOrder_Number=" + workOrder_Number + ", vehicle_ExpectedOdameter=" + vehicle_ExpectedOdameter
				+ ", vehicleExpectedKm=" + vehicleExpectedKm + ", serviceOverDue=" + serviceOverDue
				+ ", serviceNumbers=" + serviceNumbers + ", routeApproximateKM=" + routeApproximateKM + ", vendorName="
				+ vendorName + ", vehicleGPSId=" + vehicleGPSId + ", gpsOdameter=" + gpsOdameter + ", companyId="
				+ companyId + ", companyName=" + companyName + ", vehicleActiveCount=" + vehicleActiveCount
				+ ", vehicleInActiveCount=" + vehicleInActiveCount + ", vehicleSoldCount=" + vehicleSoldCount
				+ ", vehicleCompanyId=" + vehicleCompanyId + ", gpsWorking=" + gpsWorking + ", gpsLocation="
				+ gpsLocation + ", lastUreaOdometer=" + lastUreaOdometer + ", serviceEntries_id=" + serviceEntries_id
				+ ", totalserviceROUND_cost=" + totalserviceROUND_cost + ", workorders_id=" + workorders_id
				+ ", totalworkorder_cost=" + totalworkorder_cost + ", vehicleServiceEntriesCount="
				+ vehicleServiceEntriesCount + ", vehicleWorkOrdersCount=" + vehicleWorkOrdersCount
				+ ", vehicleTotalCost=" + vehicleTotalCost + ", partlocation_id=" + partlocation_id
				+ ", partlocation_name=" + partlocation_name + ", workorders_Number=" + workorders_Number
				+ ", woStartDate=" + woStartDate + ", woDueDate=" + woDueDate + ", wocompletedDate=" + wocompletedDate
				+ ", serviceEntriesNumber=" + serviceEntriesNumber + ", seInvoiceDate=" + seInvoiceDate + ", fuelMeter="
				+ fuelMeter + ", lastFuelOdometer=" + lastFuelOdometer + ", driverMonthlySalary=" + driverMonthlySalary
				+ ", ledgerName=" + ledgerName + ", gpsVendorId=" + gpsVendorId + ", gpsVendorName=" + gpsVendorName
				+ ", driverFirstName=" + driverFirstName + ", driverLastName=" + driverLastName + ", driverEmpName="
				+ driverEmpName + ", mobileNumber=" + mobileNumber + ", fuelDateTime=" + fuelDateTime + ", date=" + date
				+ ", id=" + id + ", number=" + number + ", fuelDateStr=" + fuelDateStr + ", fuel_id=" + fuel_id
				+ ", fuel_Number=" + fuel_Number + ", fuel_liters=" + fuel_liters + ", moduleOdometer=" + moduleOdometer
				+ ", isOdometerReading=" + isOdometerReading + ", tripStartDiesel=" + tripStartDiesel
				+ ", tripEndDiesel=" + tripEndDiesel + ", busId=" + busId + ", deviceId=" + deviceId + ", ureaDate="
				+ ureaDate + ", ureaDateStr=" + ureaDateStr + ", ureaEntriesNumber=" + ureaEntriesNumber
				+ ", ureaOdometer=" + ureaOdometer + ", ureaLiters=" + ureaLiters + ", ureaEntriesId=" + ureaEntriesId
				+ ", serviceProgramId=" + serviceProgramId + ", serviceProgramName=" + serviceProgramName
				+ ", driverFatherName=" + driverFatherName + ", createdById=" + createdById + ", modifiedById="
				+ modifiedById + ", branchId=" + branchId + ", vehicleBodyMakerId=" + vehicleBodyMakerId
				+ ", bodyMakerName=" + bodyMakerName + ", vehicleModelId=" + vehicleModelId + ", vehicleMakerId="
				+ vehicleMakerId + ", penaltyCount=" + penaltyCount + ", accCount=" + accCount + ", dseCount="
				+ dseCount + ", woCount=" + woCount + ", vehicleIssueCount=" + vehicleIssueCount
				+ ", breakDownIssueCount=" + breakDownIssueCount + ", milageFuel=" + milageFuel + ", consumptionFuel="
				+ consumptionFuel + "]";
	}

	public String getUreaDateStr() {
		return ureaDateStr;
	}

	public void setUreaDateStr(String ureaDateStr) {
		this.ureaDateStr = ureaDateStr;
	}

	public String getFuelDateStr() {
		return fuelDateStr;
	}

	public void setFuelDateStr(String fuelDateStr) {
		this.fuelDateStr = fuelDateStr;
	}

	public String getDriverFatherName() {
		return driverFatherName;
	}

	public void setDriverFatherName(String driverFatherName) {
		this.driverFatherName = driverFatherName;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(Long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public Long getVehicleMakerId() {
		return vehicleMakerId;
	}

	public void setVehicleMakerId(Long vehicleMakerId) {
		this.vehicleMakerId = vehicleMakerId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Integer getModuleOdometer() {
		return moduleOdometer;
	}

	public void setModuleOdometer(Integer moduleOdometer) {
		this.moduleOdometer = moduleOdometer;
	}

	public Integer getVehicleBodyMakerId() {
		return vehicleBodyMakerId;
	}

	public void setVehicleBodyMakerId(Integer vehicleBodyMakerId) {
		this.vehicleBodyMakerId = vehicleBodyMakerId;
	}

	public String getBodyMakerName() {
		return bodyMakerName;
	}

	public void setBodyMakerName(String bodyMakerName) {
		this.bodyMakerName = bodyMakerName;
	}



	public String getAccCount() {
		return accCount;
	}

	public void setAccCount(String accCount) {
		this.accCount = accCount;
	}

	public String getDseCount() {
		return dseCount;
	}

	public void setDseCount(String dseCount) {
		this.dseCount = dseCount;
	}

	public String getWoCount() {
		return woCount;
	}

	public void setWoCount(String woCount) {
		this.woCount = woCount;
	}

	public String getVehicleIssueCount() {
		return vehicleIssueCount;
	}

	public void setVehicleIssueCount(String vehicleIssueCount) {
		this.vehicleIssueCount = vehicleIssueCount;
	}

	public String getBreakDownIssueCount() {
		return breakDownIssueCount;
	}

	public void setBreakDownIssueCount(String breakDownIssueCount) {
		this.breakDownIssueCount = breakDownIssueCount;
	}

	public String getMilageFuel() {
		return milageFuel;
	}

	public void setMilageFuel(String milageFuel) {
		this.milageFuel = milageFuel;
	}

	public String getConsumptionFuel() {
		return consumptionFuel;
	}

	public void setConsumptionFuel(String consumptionFuel) {
		this.consumptionFuel = consumptionFuel;
	}

	public String getPenaltyCount() {
		return penaltyCount;
	}

	public void setPenaltyCount(String penaltyCount) {
		this.penaltyCount = penaltyCount;
	}

	public String getSubCompanyName() {
		return subCompanyName;
	}

	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}

	public Long getSubCompanyId() {
		return subCompanyId;
	}

	public void setSubCompanyId(Long subCompanyId) {
		this.subCompanyId = subCompanyId;
	}

	public String getDriverMonthlyBhatta() {
		return driverMonthlyBhatta;
	}

	public void setDriverMonthlyBhatta(String driverMonthlyBhatta) {
		this.driverMonthlyBhatta = driverMonthlyBhatta;
	}

	
}