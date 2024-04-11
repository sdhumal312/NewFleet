package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleAccidentDetails")
public class VehicleAccidentDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accidentDetailsId")
	private Long	accidentDetailsId;
	
	@Column(name = "accidentDetailsNumber")
	private Long	accidentDetailsNumber;
	
	@Column(name = "vid")
	private Integer vid;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "createdById", updatable = false, nullable = false)
	private Long	createdById;
	
	@Column(name = "createdOn", updatable = false, nullable = false)
	private Date	createdOn;
	
	@Column(name = "lastUpdateById")
	private Long	lastUpdateById;
	
	@Column(name = "lastUpdatedOn")
	private Date 	lastUpdatedOn;
	
	@Column(name = "description", length = 560)
	private String	description;
	
	@Column(name = "driverId")
	private Integer	driverId;
	
	@Column(name = "location")
	private String	location;
	
	@Column(name = "serviceId")
	private Long	serviceId;
	
	@Column(name = "serviceType")
	private short	serviceType;
	
	@Column(name = "status")
	private short	status;
	
	@Column(name = "accidentDateTime")
	private Date	accidentDateTime;
	
	@Column(name = "accidentWithVehicle")
	private String	accidentWithVehicle;
	
	@Column(name = "vehicleType")
	private String	vehicleType;
	
	@Column(name = "accidentWithOwner")
	private String	accidentWithOwner;
	
	@Column(name = "accidentWithOwnerMobile")
	private String	accidentWithOwnerMobile;
	
	@Column(name = "accidentWithDriver")
	private String	accidentWithDriver;
	
	@Column(name = "accidentWithDriverrMobile")
	private String	accidentWithDriverrMobile;
	
	@Column(name = "accidentWithOtherDetails")
	private	String	accidentWithOtherDetails;
	
	@Column(name = "firNumber")
	private String firNumber;
	
	@Column(name = "firPoliceStation")
	private String firPoliceStation;
	
	@Column(name = "firBy")
	private String firBy;
	
	@Column(name = "firRemark")
	private String firRemark;
	
	@Column(name = "tripSheetId")
	private Long	tripSheetId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;
	
	@Column(name = "paymentDate")
	private Date	paymentDate;
	
	@Column(name = "paymentRemark", length = 1000)
	private String	paymentRemark;
	
	@Column(name = "paymentAmount")
	private Double	paymentAmount;

	@Column(name = "approxDamageAmount")
	private Double	approxDamageAmount;
	
	@Column(name = "damageAmountStatusId", nullable = false)
	private short	damageAmountStatusId;
	
	@Column(name = "damageAmount")
	private Double	damageAmount;
	
	@Column(name = "isClaim", nullable = false)
	private boolean	isClaim;
	
	@Column(name = "claimRemark")
	private String	claimRemark;
	
	@Column(name = "queryRemark", length = 1000)
	private String	queryRemark;
	
	@Column(name = "routeId")
	private Integer	routeId;

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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
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
		this.paymentAmount = paymentAmount;
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

	public String getQueryRemark() {
		return queryRemark;
	}

	public void setQueryRemark(String queryRemark) {
		this.queryRemark = queryRemark;
	}
	
	
	
	
}
