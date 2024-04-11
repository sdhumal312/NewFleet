package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.web.util.Utility;



public class VehicleHistoryDto {

	
private Integer vHistoryId;
	
	private Integer vid;

	private String vehicle_registration;

	private String vehicle_chasis;

	private String vehicle_engine;

	private long	vehicleTypeId;

	private Integer vehicle_year;

	private String vehicle_maker;

	private String vehicle_Model;

	private String vehicle_registrationState;

	private String vehicle_RegisterDate;

	private String vehicle_Registeredupto;
	
	private short	vStatusId;
	
	private long 	vehicleGroupId;

	private Integer oldRouteId;
	
	private String oldRouteName;

	private Integer newRouteId;
	
	private String newRouteName;
	
	private short	vehicleOwnerShipId;

	private String Vehicle_Location;

	private String vehicle_Color;

	private String vehicle_Class;

	private String vehicle_body;

	private short	acTypeId;

	private String vehicle_Cylinders;

	private String vehicle_CubicCapacity;

	private String vehicle_Power;

	private String vehicle_wheelBase;

	private String vehicle_SeatCapacity;

	private String vehicle_UnladenWeight;

	private String vehicle_LadenWeight;

	private String  vehicleFuelId;

	private Integer vehicle_FuelTank1;

	private Integer vehicle_Oil;

	private short	vehicleMeterUnitId;

	private Integer vehicle_Odometer;

	private Integer vehicle_odometerchange = 1;

	private Double vehicle_ExpectedMileage;

	private Double vehicle_ExpectedMileage_to;
	
	private Integer vehicle_ExpectedOdameter;
	
	private short	vehicleFuelUnitId;
	
	private Integer vehicle_photoid;

	private Long tripSheetID;
	
	private Timestamp	lastOdameterUpdated;
	
	private Timestamp	lastLhpvSyncDateTime;
	
	private String vehicleGPSId;
	
	private Double driverMonthlySalary;
	
	private Integer	gpsVendorId;
	
	private String	ledgerName;
	
	private String mobileNumber;
	
	private Long createdById;

	private Long lastModifiedById;

	private Date created;

	private Date lastupdated;
	
	private String lastupdatedStr;
	
	private Integer	company_Id;
	
	private boolean	markForDelete;
	
	private Long serviceProgramId;
	
	private boolean isRouteChanged;
	
	private String userName;

	public Integer getvHistoryId() {
		return vHistoryId;
	}

	public void setvHistoryId(Integer vHistoryId) {
		this.vHistoryId = vHistoryId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getVehicle_chasis() {
		return vehicle_chasis;
	}

	public void setVehicle_chasis(String vehicle_chasis) {
		this.vehicle_chasis = vehicle_chasis;
	}

	public String getVehicle_engine() {
		return vehicle_engine;
	}

	public void setVehicle_engine(String vehicle_engine) {
		this.vehicle_engine = vehicle_engine;
	}

	public long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public Integer getVehicle_year() {
		return vehicle_year;
	}

	public void setVehicle_year(Integer vehicle_year) {
		this.vehicle_year = vehicle_year;
	}

	public String getVehicle_maker() {
		return vehicle_maker;
	}

	public void setVehicle_maker(String vehicle_maker) {
		this.vehicle_maker = vehicle_maker;
	}

	public String getVehicle_Model() {
		return vehicle_Model;
	}

	public void setVehicle_Model(String vehicle_Model) {
		this.vehicle_Model = vehicle_Model;
	}

	public String getVehicle_registrationState() {
		return vehicle_registrationState;
	}

	public void setVehicle_registrationState(String vehicle_registrationState) {
		this.vehicle_registrationState = vehicle_registrationState;
	}

	public String getVehicle_RegisterDate() {
		return vehicle_RegisterDate;
	}

	public void setVehicle_RegisterDate(String vehicle_RegisterDate) {
		this.vehicle_RegisterDate = vehicle_RegisterDate;
	}

	public String getVehicle_Registeredupto() {
		return vehicle_Registeredupto;
	}

	public void setVehicle_Registeredupto(String vehicle_Registeredupto) {
		this.vehicle_Registeredupto = vehicle_Registeredupto;
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

	public Integer getOldRouteId() {
		return oldRouteId;
	}

	public void setOldRouteId(Integer oldRouteId) {
		this.oldRouteId = oldRouteId;
	}

	public String getOldRouteName() {
		return oldRouteName;
	}

	public void setOldRouteName(String oldRouteName) {
		this.oldRouteName = oldRouteName;
	}

	public Integer getNewRouteId() {
		return newRouteId;
	}

	public void setNewRouteId(Integer newRouteId) {
		this.newRouteId = newRouteId;
	}

	public String getNewRouteName() {
		return newRouteName;
	}

	public void setNewRouteName(String newRouteName) {
		this.newRouteName = newRouteName;
	}

	public short getVehicleOwnerShipId() {
		return vehicleOwnerShipId;
	}

	public void setVehicleOwnerShipId(short vehicleOwnerShipId) {
		this.vehicleOwnerShipId = vehicleOwnerShipId;
	}

	public String getVehicle_Location() {
		return Vehicle_Location;
	}

	public void setVehicle_Location(String vehicle_Location) {
		Vehicle_Location = vehicle_Location;
	}

	public String getVehicle_Color() {
		return vehicle_Color;
	}

	public void setVehicle_Color(String vehicle_Color) {
		this.vehicle_Color = vehicle_Color;
	}

	public String getVehicle_Class() {
		return vehicle_Class;
	}

	public void setVehicle_Class(String vehicle_Class) {
		this.vehicle_Class = vehicle_Class;
	}

	public String getVehicle_body() {
		return vehicle_body;
	}

	public void setVehicle_body(String vehicle_body) {
		this.vehicle_body = vehicle_body;
	}

	public short getAcTypeId() {
		return acTypeId;
	}

	public void setAcTypeId(short acTypeId) {
		this.acTypeId = acTypeId;
	}

	public String getVehicle_Cylinders() {
		return vehicle_Cylinders;
	}

	public void setVehicle_Cylinders(String vehicle_Cylinders) {
		this.vehicle_Cylinders = vehicle_Cylinders;
	}

	public String getVehicle_CubicCapacity() {
		return vehicle_CubicCapacity;
	}

	public void setVehicle_CubicCapacity(String vehicle_CubicCapacity) {
		this.vehicle_CubicCapacity = vehicle_CubicCapacity;
	}

	public String getVehicle_Power() {
		return vehicle_Power;
	}

	public void setVehicle_Power(String vehicle_Power) {
		this.vehicle_Power = vehicle_Power;
	}

	public String getVehicle_wheelBase() {
		return vehicle_wheelBase;
	}

	public void setVehicle_wheelBase(String vehicle_wheelBase) {
		this.vehicle_wheelBase = vehicle_wheelBase;
	}

	public String getVehicle_SeatCapacity() {
		return vehicle_SeatCapacity;
	}

	public void setVehicle_SeatCapacity(String vehicle_SeatCapacity) {
		this.vehicle_SeatCapacity = vehicle_SeatCapacity;
	}

	public String getVehicle_UnladenWeight() {
		return vehicle_UnladenWeight;
	}

	public void setVehicle_UnladenWeight(String vehicle_UnladenWeight) {
		this.vehicle_UnladenWeight = vehicle_UnladenWeight;
	}

	public String getVehicle_LadenWeight() {
		return vehicle_LadenWeight;
	}

	public void setVehicle_LadenWeight(String vehicle_LadenWeight) {
		this.vehicle_LadenWeight = vehicle_LadenWeight;
	}

	public String getVehicleFuelId() {
		return vehicleFuelId;
	}

	public void setVehicleFuelId(String vehicleFuelId) {
		this.vehicleFuelId = vehicleFuelId;
	}

	public Integer getVehicle_FuelTank1() {
		return vehicle_FuelTank1;
	}

	public void setVehicle_FuelTank1(Integer vehicle_FuelTank1) {
		this.vehicle_FuelTank1 = vehicle_FuelTank1;
	}

	public Integer getVehicle_Oil() {
		return vehicle_Oil;
	}

	public void setVehicle_Oil(Integer vehicle_Oil) {
		this.vehicle_Oil = vehicle_Oil;
	}

	public short getVehicleMeterUnitId() {
		return vehicleMeterUnitId;
	}

	public void setVehicleMeterUnitId(short vehicleMeterUnitId) {
		this.vehicleMeterUnitId = vehicleMeterUnitId;
	}

	public Integer getVehicle_Odometer() {
		return vehicle_Odometer;
	}

	public void setVehicle_Odometer(Integer vehicle_Odometer) {
		this.vehicle_Odometer = vehicle_Odometer;
	}

	public Integer getVehicle_odometerchange() {
		return vehicle_odometerchange;
	}

	public void setVehicle_odometerchange(Integer vehicle_odometerchange) {
		this.vehicle_odometerchange = vehicle_odometerchange;
	}

	public Double getVehicle_ExpectedMileage() {
		return vehicle_ExpectedMileage;
	}

	public void setVehicle_ExpectedMileage(Double vehicle_ExpectedMileage) {
		this.vehicle_ExpectedMileage = Utility.round(vehicle_ExpectedMileage,2);
	}

	public Double getVehicle_ExpectedMileage_to() {
		return vehicle_ExpectedMileage_to;
	}

	public void setVehicle_ExpectedMileage_to(Double vehicle_ExpectedMileage_to) {
		this.vehicle_ExpectedMileage_to = Utility.round(vehicle_ExpectedMileage_to, 2);
	}

	public Integer getVehicle_ExpectedOdameter() {
		return vehicle_ExpectedOdameter;
	}

	public void setVehicle_ExpectedOdameter(Integer vehicle_ExpectedOdameter) {
		this.vehicle_ExpectedOdameter = vehicle_ExpectedOdameter;
	}

	public short getVehicleFuelUnitId() {
		return vehicleFuelUnitId;
	}

	public void setVehicleFuelUnitId(short vehicleFuelUnitId) {
		this.vehicleFuelUnitId = vehicleFuelUnitId;
	}

	public Integer getVehicle_photoid() {
		return vehicle_photoid;
	}

	public void setVehicle_photoid(Integer vehicle_photoid) {
		this.vehicle_photoid = vehicle_photoid;
	}

	public Long getTripSheetID() {
		return tripSheetID;
	}

	public void setTripSheetID(Long tripSheetID) {
		this.tripSheetID = tripSheetID;
	}

	public Timestamp getLastOdameterUpdated() {
		return lastOdameterUpdated;
	}

	public void setLastOdameterUpdated(Timestamp lastOdameterUpdated) {
		this.lastOdameterUpdated = lastOdameterUpdated;
	}

	public Timestamp getLastLhpvSyncDateTime() {
		return lastLhpvSyncDateTime;
	}

	public void setLastLhpvSyncDateTime(Timestamp lastLhpvSyncDateTime) {
		this.lastLhpvSyncDateTime = lastLhpvSyncDateTime;
	}

	public String getVehicleGPSId() {
		return vehicleGPSId;
	}

	public void setVehicleGPSId(String vehicleGPSId) {
		this.vehicleGPSId = vehicleGPSId;
	}

	public Double getDriverMonthlySalary() {
		return driverMonthlySalary;
	}

	public void setDriverMonthlySalary(Double driverMonthlySalary) {
		this.driverMonthlySalary = Utility.round(driverMonthlySalary, 2);
	}

	public Integer getGpsVendorId() {
		return gpsVendorId;
	}

	public void setGpsVendorId(Integer gpsVendorId) {
		this.gpsVendorId = gpsVendorId;
	}

	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public String getLastupdatedStr() {
		return lastupdatedStr;
	}

	public void setLastupdatedStr(String lastupdatedStr) {
		this.lastupdatedStr = lastupdatedStr;
	}

	public Integer getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(Integer company_Id) {
		this.company_Id = company_Id;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getServiceProgramId() {
		return serviceProgramId;
	}

	public void setServiceProgramId(Long serviceProgramId) {
		this.serviceProgramId = serviceProgramId;
	}

	public boolean isRouteChanged() {
		return isRouteChanged;
	}

	public void setRouteChanged(boolean isRouteChanged) {
		this.isRouteChanged = isRouteChanged;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "VehicleHistoryDto [vHistoryId=" + vHistoryId + ", vid=" + vid + ", vehicle_registration="
				+ vehicle_registration + ", vehicle_chasis=" + vehicle_chasis + ", vehicle_engine=" + vehicle_engine
				+ ", vehicleTypeId=" + vehicleTypeId + ", vehicle_year=" + vehicle_year + ", vehicle_maker="
				+ vehicle_maker + ", vehicle_Model=" + vehicle_Model + ", vehicle_registrationState="
				+ vehicle_registrationState + ", vehicle_RegisterDate=" + vehicle_RegisterDate
				+ ", vehicle_Registeredupto=" + vehicle_Registeredupto + ", vStatusId=" + vStatusId
				+ ", vehicleGroupId=" + vehicleGroupId + ", oldRouteId=" + oldRouteId + ", oldRouteName=" + oldRouteName
				+ ", newRouteId=" + newRouteId + ", newRouteName=" + newRouteName + ", vehicleOwnerShipId="
				+ vehicleOwnerShipId + ", Vehicle_Location=" + Vehicle_Location + ", vehicle_Color=" + vehicle_Color
				+ ", vehicle_Class=" + vehicle_Class + ", vehicle_body=" + vehicle_body + ", acTypeId=" + acTypeId
				+ ", vehicle_Cylinders=" + vehicle_Cylinders + ", vehicle_CubicCapacity=" + vehicle_CubicCapacity
				+ ", vehicle_Power=" + vehicle_Power + ", vehicle_wheelBase=" + vehicle_wheelBase
				+ ", vehicle_SeatCapacity=" + vehicle_SeatCapacity + ", vehicle_UnladenWeight=" + vehicle_UnladenWeight
				+ ", vehicle_LadenWeight=" + vehicle_LadenWeight + ", vehicleFuelId=" + vehicleFuelId
				+ ", vehicle_FuelTank1=" + vehicle_FuelTank1 + ", vehicle_Oil=" + vehicle_Oil + ", vehicleMeterUnitId="
				+ vehicleMeterUnitId + ", vehicle_Odometer=" + vehicle_Odometer + ", vehicle_odometerchange="
				+ vehicle_odometerchange + ", vehicle_ExpectedMileage=" + vehicle_ExpectedMileage
				+ ", vehicle_ExpectedMileage_to=" + vehicle_ExpectedMileage_to + ", vehicle_ExpectedOdameter="
				+ vehicle_ExpectedOdameter + ", vehicleFuelUnitId=" + vehicleFuelUnitId + ", vehicle_photoid="
				+ vehicle_photoid + ", tripSheetID=" + tripSheetID + ", lastOdameterUpdated=" + lastOdameterUpdated
				+ ", lastLhpvSyncDateTime=" + lastLhpvSyncDateTime + ", vehicleGPSId=" + vehicleGPSId
				+ ", driverMonthlySalary=" + driverMonthlySalary + ", gpsVendorId=" + gpsVendorId + ", ledgerName="
				+ ledgerName + ", mobileNumber=" + mobileNumber + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", created=" + created + ", lastupdated=" + lastupdated + ", lastupdatedStr="
				+ lastupdatedStr + ", company_Id=" + company_Id + ", markForDelete=" + markForDelete
				+ ", serviceProgramId=" + serviceProgramId + ", isRouteChanged=" + isRouteChanged + ", userName="
				+ userName + "]";
	}
	
	
	
	
}
