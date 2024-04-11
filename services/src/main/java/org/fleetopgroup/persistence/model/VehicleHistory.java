package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vehicleHistory")
public class VehicleHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vHistoryId")
	private Integer vHistoryId;
	
	@Column(name = "vid")
	private Integer vid;

	@Column(name = "vehicle_registration", length = 25)
	private String vehicle_registration;

	@Column(name = "vehicle_chasis",  length = 25)
	private String vehicle_chasis;

	@Column(name = "vehicle_engine", length = 25)
	private String vehicle_engine;

	@Column(name = "vehicleTypeId")
	private long	vehicleTypeId;

	@Column(name = "vehicle_year", length = 4)
	private Integer vehicle_year;

	@Column(name = "vehicle_maker", length = 150)
	private String vehicle_maker;

	@Column(name = "vehicle_Model", length = 150)
	private String vehicle_Model;

	@Column(name = "vehicle_registrationState", length = 25)
	private String vehicle_registrationState;

	@Column(name = "vehicle_RegisterDate", length = 25)
	private String vehicle_RegisterDate;

	@Column(name = "vehicle_Registeredupto", length = 25)
	private String vehicle_Registeredupto;
	
	@Column(name = "vStatusId")
	private short	vStatusId;
	
	@Column(name = "vehicleGroupId",nullable = false)
	private long 	vehicleGroupId;

	@Column(name = "oldRouteId")
	private Integer oldRouteId;

	@Column(name = "newRouteId")
	private Integer newRouteId;
	
	@Column(name = "vehicleOwnerShipId")
	private short	vehicleOwnerShipId;

	@Column(name = "Vehicle_Location", length = 150)
	private String Vehicle_Location;

	@Column(name = "vehicle_Color", length = 25)
	private String vehicle_Color;

	@Column(name = "vehicle_Class", length = 25)
	private String vehicle_Class;

	@Column(name = "vehicle_body", length = 25)
	private String vehicle_body;

	@Column(name = "acTypeId")
	private short	acTypeId;

	@Column(name = "vehicle_Cylinders", length = 25)
	private String vehicle_Cylinders;

	@Column(name = "vehicle_CubicCapacity", length = 25)
	private String vehicle_CubicCapacity;

	@Column(name = "vehicle_Power", length = 25)
	private String vehicle_Power;

	@Column(name = "vehicle_wheelBase", length = 25)
	private String vehicle_wheelBase;

	@Column(name = "vehicle_SeatCapacity", length = 25)
	private String vehicle_SeatCapacity;

	@Column(name = "vehicle_UnladenWeight", length = 25)
	private String vehicle_UnladenWeight;

	@Column(name = "vehicle_LadenWeight", length = 25)
	private String vehicle_LadenWeight;

	@Column(name = "vehicleFuelId")
	private String  vehicleFuelId;

	@Column(name = "vehicle_FuelTank1", length = 7)
	private Integer vehicle_FuelTank1;

	@Column(name = "vehicle_Oil", length = 7)
	private Integer vehicle_Oil;

	@Column(name = "vehicleMeterUnitId")
	private short	vehicleMeterUnitId;

	@Column(name = "vehicle_Odometer", length = 10)
	private Integer vehicle_Odometer;

	@Column(name = "vehicle_odometerchange")
	private Integer vehicle_odometerchange = 1;

	@Column(name = "vehicle_ExpectedMileage")
	private Double vehicle_ExpectedMileage;

	@Column(name = "vehicle_ExpectedMileage_to")
	private Double vehicle_ExpectedMileage_to;
	
	@Column(name = "vehicle_ExpectedOdameter")
	private Integer vehicle_ExpectedOdameter;
	
	@Column(name = "vehicleFuelUnitId")
	private short	vehicleFuelUnitId;
	
	@Column(name = "vehicle_photoid", length = 7)
	private Integer vehicle_photoid;

	@Column(name = "tripSheetID")
	private Long tripSheetID;
	
	@Column(name = "lastOdameterUpdated")
	private Timestamp	lastOdameterUpdated;
	
	@Column(name = "lastLhpvSyncDateTime")
	private Timestamp	lastLhpvSyncDateTime;
	
	@Column(name = "vehicleGPSId")
	private String vehicleGPSId;
	
	@Column(name = "driverMonthlySalary")
	private Double driverMonthlySalary;
	
	@Column(name = "gpsVendorId")
	private Integer	gpsVendorId;
	
	@Column(name = "ledgerName")
	private String	ledgerName;
	
	@Column(name = "mobileNumber")
	private String mobileNumber;
	
	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;
	
	@Column(name = "company_Id", nullable = false)
	private Integer	company_Id;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;
	
	@Column(name = "serviceProgramId")
	private Long serviceProgramId;
	
	@Column(name = "isRouteChanged")
	private boolean isRouteChanged;
	
	@Column(name = "vHistorycreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date vHistorycreated;
	
	@Column(name = "vHistorycreatedById")
	private Long vHistorycreatedById;
	
	@Column(name = "vehicleBodyMakerId")
	private Integer vehicleBodyMakerId;


	public Date getvHistorycreated() {
		return vHistorycreated;
	}



	public void setvHistorycreated(Date vHistorycreated) {
		this.vHistorycreated = vHistorycreated;
	}



	public Long getvHistorycreatedById() {
		return vHistorycreatedById;
	}



	public void setvHistorycreatedById(Long vHistorycreatedById) {
		this.vHistorycreatedById = vHistorycreatedById;
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
		this.vehicle_ExpectedMileage = vehicle_ExpectedMileage;
	}



	public Double getVehicle_ExpectedMileage_to() {
		return vehicle_ExpectedMileage_to;
	}



	public void setVehicle_ExpectedMileage_to(Double vehicle_ExpectedMileage_to) {
		this.vehicle_ExpectedMileage_to = vehicle_ExpectedMileage_to;
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
		this.driverMonthlySalary = driverMonthlySalary;
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



	public Long getServiceProgramId() {
		return serviceProgramId;
	}



	public void setServiceProgramId(Long serviceProgramId) {
		this.serviceProgramId = serviceProgramId;
	}



	public Integer getvHistoryId() {
		return vHistoryId;
	}



	public void setvHistoryId(Integer vHistoryId) {
		this.vHistoryId = vHistoryId;
	}



	public Integer getOldRouteId() {
		return oldRouteId;
	}



	public void setOldRouteId(Integer oldRouteId) {
		this.oldRouteId = oldRouteId;
	}



	public Integer getNewRouteId() {
		return newRouteId;
	}



	public void setNewRouteId(Integer newRouteId) {
		this.newRouteId = newRouteId;
	}



	public boolean isRouteChanged() {
		return isRouteChanged;
	}



	public void setRouteChanged(boolean isRouteChanged) {
		this.isRouteChanged = isRouteChanged;
	}



	public Integer getVehicleBodyMakerId() {
		return vehicleBodyMakerId;
	}



	public void setVehicleBodyMakerId(Integer vehicleBodyMakerId) {
		this.vehicleBodyMakerId = vehicleBodyMakerId;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + acTypeId;
		result = prime * result + ((company_Id == null) ? 0 : company_Id.hashCode());
		result = prime * result + ((oldRouteId == null) ? 0 : oldRouteId.hashCode());
		result = prime * result + ((newRouteId == null) ? 0 : newRouteId.hashCode());
		result = prime * result + ((tripSheetID == null) ? 0 : tripSheetID.hashCode());
		result = prime * result + vStatusId;
		result = prime * result + ((vehicleFuelId == null) ? 0 : vehicleFuelId.hashCode());
		result = prime * result + vehicleFuelUnitId;
		result = prime * result + (int) (vehicleGroupId ^ (vehicleGroupId >>> 32));
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
		return result;
	}



	@Override
	public String toString() {
		return "VehicleHistory [vHistoryId=" + vHistoryId + ", vid=" + vid + ", vehicle_registration="
				+ vehicle_registration + ", vehicle_chasis=" + vehicle_chasis + ", vehicle_engine=" + vehicle_engine
				+ ", vehicleTypeId=" + vehicleTypeId + ", vehicle_year=" + vehicle_year + ", vehicle_maker="
				+ vehicle_maker + ", vehicle_Model=" + vehicle_Model + ", vehicle_registrationState="
				+ vehicle_registrationState + ", vehicle_RegisterDate=" + vehicle_RegisterDate
				+ ", vehicle_Registeredupto=" + vehicle_Registeredupto + ", vStatusId=" + vStatusId
				+ ", vehicleGroupId=" + vehicleGroupId + ", oldrouteID=" + oldRouteId + ",newrouteID=" + newRouteId + ", vehicleOwnerShipId="
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
				+ lastModifiedById + ", created=" + created + ", lastupdated=" + lastupdated + ", company_Id="
				+ company_Id + ", markForDelete=" + markForDelete + ", serviceProgramId=" + serviceProgramId
				+ ", isRouteChanged=" + isRouteChanged + "]";
	}



	}