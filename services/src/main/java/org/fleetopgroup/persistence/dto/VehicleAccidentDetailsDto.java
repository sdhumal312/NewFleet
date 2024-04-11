package org.fleetopgroup.persistence.dto;

import java.util.Date;

import javax.persistence.Column;

import org.fleetopgroup.web.util.Utility;

public class VehicleAccidentDetailsDto {

	private Long	accidentDetailsId;
	
	private Long	accidentDetailsNumber;
	
	private Integer vid;
	
	private String	vehicleNumber;
	
	private Integer companyId;
	
	private Long	createdById;
	
	private Date	createdOn;
	
	private Long	lastUpdateById;
	
	private Date 	lastUpdatedOn;
	
	private String	description;
	
	private Integer	driverId;
	
	private String	location;
	
	private Date	accidentDateTime;
	
	private String	accidentDate;
	
	private String	accidentTime;
	
	private String	accidentWithVehicle;
	
	private String	vehicleType;
	
	private String	accidentWithOwner;
	
	private String	accidentWithOwnerMobile;
	
	private String	accidentWithDriver;
	
	private String	accidentWithDriverrMobile;
	
	private	String	accidentWithOtherDetails;
	
	private String firNumber;
	
	private String firPoliceStation;
	
	private String firBy;
	
	private String firRemark;
	
	private Long	tripSheetId;
	
	private Long	tripSheetNumber;
	
	private String	accidentDateTimeStr;
	
	private String	driverName;
	
	private String	createdBy;
	
	private String	lastUpdatedBy;
	
	private String	createdOnStr;
	
	private String	lastUpdatedOnStr;
	
	private String	encriptedId;
	
	private	Integer	vehicleOdometer; 
	
	private String	serviceNumber;
	
	private Long	serviceId;
	
	private short	serviceType;
	
	private short	status;
	
	private String	statuStr;
	
	private Date	paymentDate;
	
	private String	paymentDateStr;
	
	private String	paymentRemark;
	
	private Double	paymentAmount;
	
	private Double	approxDamageAmount;
	
	private short	damageAmountStatusId;
	
	private String	damageAmountStatus;
	
	private Double	damageAmount;
	
	private boolean	isClaim;
	
	private String	claimStatus;
	
	private String	claimRemark;

	private Integer	routeId;
	
	private String	route;
	
	private short	serviceStatusId;
	
	private String queryRemark;

	public Date getAccidentDateTime() {
		return accidentDateTime;
	}

	public void setAccidentDateTime(Date accidentDateTime) {
		this.accidentDateTime = accidentDateTime;
	}

	public String getAccidentWithVehicle() {
		return accidentWithVehicle;
	}

	public void setAccidentWithVehicle(String accidentWithVehicle) {
		this.accidentWithVehicle = accidentWithVehicle;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getAccidentWithOwner() {
		return accidentWithOwner;
	}

	public void setAccidentWithOwner(String accidentWithOwner) {
		this.accidentWithOwner = accidentWithOwner;
	}

	public String getAccidentWithOwnerMobile() {
		return accidentWithOwnerMobile;
	}

	public void setAccidentWithOwnerMobile(String accidentWithOwnerMobile) {
		this.accidentWithOwnerMobile = accidentWithOwnerMobile;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public Long getAccidentDetailsId() {
		return accidentDetailsId;
	}

	public void setAccidentDetailsId(Long accidentDetailsId) {
		this.accidentDetailsId = accidentDetailsId;
	}

	public Long getAccidentDetailsNumber() {
		return accidentDetailsNumber;
	}

	public void setAccidentDetailsNumber(Long accidentDetailsNumber) {
		this.accidentDetailsNumber = accidentDetailsNumber;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getLastUpdateById() {
		return lastUpdateById;
	}

	public void setLastUpdateById(Long lastUpdateById) {
		this.lastUpdateById = lastUpdateById;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAccidentWithDriver() {
		return accidentWithDriver;
	}

	public void setAccidentWithDriver(String accidentWithDriver) {
		this.accidentWithDriver = accidentWithDriver;
	}

	public String getAccidentWithDriverrMobile() {
		return accidentWithDriverrMobile;
	}

	public void setAccidentWithDriverrMobile(String accidentWithDriverrMobile) {
		this.accidentWithDriverrMobile = accidentWithDriverrMobile;
	}

	public String getAccidentWithOtherDetails() {
		return accidentWithOtherDetails;
	}

	public void setAccidentWithOtherDetails(String accidentWithOtherDetails) {
		this.accidentWithOtherDetails = accidentWithOtherDetails;
	}

	public String getFirNumber() {
		return firNumber;
	}

	public void setFirNumber(String firNumber) {
		this.firNumber = firNumber;
	}

	public String getFirPoliceStation() {
		return firPoliceStation;
	}

	public void setFirPoliceStation(String firPoliceStation) {
		this.firPoliceStation = firPoliceStation;
	}

	public String getFirBy() {
		return firBy;
	}

	public void setFirBy(String firBy) {
		this.firBy = firBy;
	}

	public String getFirRemark() {
		return firRemark;
	}

	public void setFirRemark(String firRemark) {
		this.firRemark = firRemark;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public String getAccidentDateTimeStr() {
		return accidentDateTimeStr;
	}

	public void setAccidentDateTimeStr(String accidentDateTimeStr) {
		this.accidentDateTimeStr = accidentDateTimeStr;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getCreatedOnStr() {
		return createdOnStr;
	}

	public void setCreatedOnStr(String createdOnStr) {
		this.createdOnStr = createdOnStr;
	}

	public String getLastUpdatedOnStr() {
		return lastUpdatedOnStr;
	}

	public void setLastUpdatedOnStr(String lastUpdatedOnStr) {
		this.lastUpdatedOnStr = lastUpdatedOnStr;
	}

	public String getEncriptedId() {
		return encriptedId;
	}

	public void setEncriptedId(String encriptedId) {
		this.encriptedId = encriptedId;
	}

	public Integer getVehicleOdometer() {
		return vehicleOdometer;
	}

	public void setVehicleOdometer(Integer vehicleOdometer) {
		this.vehicleOdometer = vehicleOdometer;
	}

	public String getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public short getServiceType() {
		return serviceType;
	}

	public void setServiceType(short serviceType) {
		this.serviceType = serviceType;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getStatuStr() {
		return statuStr;
	}

	public void setStatuStr(String statuStr) {
		this.statuStr = statuStr;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentDateStr() {
		return paymentDateStr;
	}

	public void setPaymentDateStr(String paymentDateStr) {
		this.paymentDateStr = paymentDateStr;
	}

	public String getPaymentRemark() {
		return paymentRemark;
	}

	public void setPaymentRemark(String paymentRemark) {
		this.paymentRemark = paymentRemark;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = Utility.round(paymentAmount, 2);
	}

	public String getAccidentDate() {
		return accidentDate;
	}

	public void setAccidentDate(String accidentDate) {
		this.accidentDate = accidentDate;
	}

	public String getAccidentTime() {
		return accidentTime;
	}

	public void setAccidentTime(String accidentTime) {
		this.accidentTime = accidentTime;
	}

	public Double getApproxDamageAmount() {
		return approxDamageAmount;
	}

	public void setApproxDamageAmount(Double approxDamageAmount) {
		this.approxDamageAmount = approxDamageAmount;
	}

	public short getDamageAmountStatusId() {
		return damageAmountStatusId;
	}

	public void setDamageAmountStatusId(short damageAmountStatusId) {
		this.damageAmountStatusId = damageAmountStatusId;
	}

	public String getDamageAmountStatus() {
		return damageAmountStatus;
	}

	public void setDamageAmountStatus(String damageAmountStatus) {
		this.damageAmountStatus = damageAmountStatus;
	}

	public Double getDamageAmount() {
		return damageAmount;
	}

	public void setDamageAmount(Double damageAmount) {
		this.damageAmount = damageAmount;
	}

	public boolean isClaim() {
		return isClaim;
	}

	public void setClaim(boolean isClaim) {
		this.isClaim = isClaim;
	}

	public String getClaimRemark() {
		return claimRemark;
	}

	public void setClaimRemark(String claimRemark) {
		this.claimRemark = claimRemark;
	}

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	public short getServiceStatusId() {
		return serviceStatusId;
	}

	public void setServiceStatusId(short serviceStatusId) {
		this.serviceStatusId = serviceStatusId;
	}

	public String getQueryRemark() {
		return queryRemark;
	}

	public void setQueryRemark(String queryRemark) {
		this.queryRemark = queryRemark;
	}

	
	
	
	
	
	
}
