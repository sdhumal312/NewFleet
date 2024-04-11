package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TripSheetHistory")
public class TripSheetHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TripSheetHistoryId")
	private Long TripSheetHistoryId;
	
	@Column(name = "tripSheetID")
	private Long tripSheetID;
	
	@Column(name = "tripSheetNumber", nullable = false)
	private Long tripSheetNumber;

	@Column(name = "vid")
	private Integer vid;

	@Column(name = "tripFristDriverID")
	private int tripFristDriverID;

	/** The value for the TRIP tripFristDriverBata field */
	@Column(name = "tripFristDriverBata")
	private Double tripFristDriverBata;

	@Column(name = "tripSecDriverID")
	private int tripSecDriverID;

	/** The value for the TRIP tripSecDriverBata field */
	@Column(name = "tripSecDriverBata")
	private Double tripSecDriverBata;

	@Column(name = "tripCleanerID")
	private int tripCleanerID;

	/** The value for the TRIP tripCleanerBata field */
	@Column(name = "tripCleanerBata")
	private Double tripCleanerBata;

	@Column(name = "tripOpenDate")
	private Date tripOpenDate;

	@Column(name = "routeID")
	private Integer routeID;

	@Column(name = "tripOpeningKM", length = 10)
	private Integer tripOpeningKM;

	@Column(name = "tripClosingKM", length = 10)
	private Integer tripClosingKM;
	
	@Column(name = "tripClosingKMStatusId", nullable = false)
	private short tripClosingKMStatusId;

	@Column(name = "tripUsageKM", length = 10)
	private Integer tripUsageKM;

	@Column(name = "tripBookref", length = 250)
	private String tripBookref;

	@Column(name = "tripTotalAdvance")
	private Double tripTotalAdvance;

	@Column(name = "tripTotalexpense")
	private Double tripTotalexpense;

	@Column(name = "tripTotalincome")
	private Double tripTotalincome;

	@Column(name = "closeTripAmount")
	private Double closeTripAmount;

	@Column(name = "closeTripStatusId", nullable = false)
	private short closeTripStatusId;
	
	@Column(name = "closeTripReference", length = 15)
	private String closeTripReference;
	
	@Column(name = "closeTripNameById")
	private Long closeTripNameById;

	@Column(name = "closetripDate")
	private Date closetripDate;
	
	@Column(name = "dispatchedById")
	private Long dispatchedById;
	
	@Column(name = "dispatchedLocationId")
	private Integer dispatchedLocationId;

	@Column(name = "dispatchedByTime")
	private Date dispatchedByTime;
	
	@Column(name = "closedById")
	private Long closedById;
	
	@Column(name = "closedLocationId")
	private Integer	closedLocationId;

	@Column(name = "closedByTime")
	private Date closedByTime;
	
	@Column(name = "acclosedById")
	private Long acclosedById;
	
	@Column(name = "acclosedLocationId")
	private Integer	acclosedLocationId;

	@Column(name = "acclosedByTime")
	private Date acclosedByTime;
	
	@Column(name = "tripStutesId", nullable = false)
	private short tripStutesId;
	
	@Column(name = "closeACCTripNameById")
	private Long closeACCTripNameById;

	@Column(name = "closeACCTripReference", length = 15)
	private String closeACCTripReference;

	@Column(name = "closeACCTripAmount")
	private Double closeACCTripAmount;

	@Column(name = "closeACCtripDate")
	private Date closeACCtripDate;

	@Column(name = "tripCommentTotal", length = 3)
	private Integer tripCommentTotal;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	/** The value for the lastUpdated By email field */
	@Column(name = "modifiedById", nullable = false)
	private Long modifiedById;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@Column(name = "lastupdated")
	private Date lastupdated;
	
	@Column(name = "ipAddress")
	private String ipAddress;
	
	@Column(name = "subRoute")
	private String subRoute;
	
	@Column(name = "routeName", length = 200)
	 private String routeName;
	
	@Column(name = "tripGpsOpeningKM")
	private Double tripGpsOpeningKM;
	
	@Column(name = "tripGpsClosingKM")
	private Double tripGpsClosingKM;
	
	/** The value for the created by email field */
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long createdById;
	
	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(name = "gpsOpeningLocation")
	private String gpsOpeningLocation;
	
	@Column(name = "gpsCloseLocation")
	private String gpsCloseLocation;
	
	@Column(name = "gpsTripUsageKM")
	private Double  gpsTripUsageKM;
	
	@Column(name = "loadTypeId", columnDefinition = "int default 0")
	private short	loadTypeId;
	
	@Column(name = "noOfPOD")
	private Integer noOfPOD;
	
	@Column(name = "receivedPOD")
	private Integer receivedPOD;
	
	@Column(name = "tripFristDriverRoutePoint")
	private Double tripFristDriverRoutePoint;
	
	@Column(name = "tripSecDriverRoutePoint")
	private Double tripSecDriverRoutePoint;
	
	@Column(name = "tripCleanerRoutePoint")
	private Double tripCleanerRoutePoint;
	
	@Column(name = "tripStartDiesel")
	private Double	tripStartDiesel;
	
	@Column(name = "tripEndDiesel")
	private Double	tripEndDiesel;
	
	@Column(name = "voucherDate")
	private Date voucherDate;
	
	@Column(name = "deleteRemark")
	private String deleteRemark;
	
	@Column(name = "tallyCompanyId")
	private Long	tallyCompanyId;
	
	@Basic(optional = false)
	@Column(name = "tripSheetDocument", nullable = false)
	private boolean tripSheetDocument = false;
	
	@Basic(optional = false)
	@Column(name = "meterNotWorking", nullable = false)
	private boolean meterNotWorking = false;

	@Column(name = "tripSheetDocumentId")
	private Long tripSheetDocumentId;
	
	@Column(name = "driverBalance")
	private Double driverBalance;
	
	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;
	
	

	public String getSubRoute() {
		return subRoute;
	}

	public void setSubRoute(String subRoute) {
		this.subRoute = subRoute;
	}

	public Long getTripSheetHistoryId() {
		return TripSheetHistoryId;
	}

	public void setTripSheetHistoryId(Long tripSheetHistoryId) {
		TripSheetHistoryId = tripSheetHistoryId;
	}

	public Long getTripSheetID() {
		return tripSheetID;
	}

	public void setTripSheetID(Long tripSheetID) {
		this.tripSheetID = tripSheetID;
	}

	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public int getTripFristDriverID() {
		return tripFristDriverID;
	}

	public void setTripFristDriverID(int tripFristDriverID) {
		this.tripFristDriverID = tripFristDriverID;
	}

	public Double getTripFristDriverBata() {
		return tripFristDriverBata;
	}

	public void setTripFristDriverBata(Double tripFristDriverBata) {
		this.tripFristDriverBata = tripFristDriverBata;
	}

	public int getTripSecDriverID() {
		return tripSecDriverID;
	}

	public void setTripSecDriverID(int tripSecDriverID) {
		this.tripSecDriverID = tripSecDriverID;
	}

	public Double getTripSecDriverBata() {
		return tripSecDriverBata;
	}

	public void setTripSecDriverBata(Double tripSecDriverBata) {
		this.tripSecDriverBata = tripSecDriverBata;
	}

	public int getTripCleanerID() {
		return tripCleanerID;
	}

	public void setTripCleanerID(int tripCleanerID) {
		this.tripCleanerID = tripCleanerID;
	}

	public Double getTripCleanerBata() {
		return tripCleanerBata;
	}

	public void setTripCleanerBata(Double tripCleanerBata) {
		this.tripCleanerBata = tripCleanerBata;
	}

	public Date getTripOpenDate() {
		return tripOpenDate;
	}

	public void setTripOpenDate(Date tripOpenDate) {
		this.tripOpenDate = tripOpenDate;
	}

	public Integer getRouteID() {
		return routeID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public Integer getTripOpeningKM() {
		return tripOpeningKM;
	}

	public void setTripOpeningKM(Integer tripOpeningKM) {
		this.tripOpeningKM = tripOpeningKM;
	}

	public Integer getTripClosingKM() {
		return tripClosingKM;
	}

	public void setTripClosingKM(Integer tripClosingKM) {
		this.tripClosingKM = tripClosingKM;
	}

	public short getTripClosingKMStatusId() {
		return tripClosingKMStatusId;
	}

	public void setTripClosingKMStatusId(short tripClosingKMStatusId) {
		this.tripClosingKMStatusId = tripClosingKMStatusId;
	}

	public Integer getTripUsageKM() {
		return tripUsageKM;
	}

	public void setTripUsageKM(Integer tripUsageKM) {
		this.tripUsageKM = tripUsageKM;
	}

	public String getTripBookref() {
		return tripBookref;
	}

	public void setTripBookref(String tripBookref) {
		this.tripBookref = tripBookref;
	}

	public Double getTripTotalAdvance() {
		return tripTotalAdvance;
	}

	public void setTripTotalAdvance(Double tripTotalAdvance) {
		this.tripTotalAdvance = tripTotalAdvance;
	}

	public Double getTripTotalexpense() {
		return tripTotalexpense;
	}

	public void setTripTotalexpense(Double tripTotalexpense) {
		this.tripTotalexpense = tripTotalexpense;
	}

	public Double getTripTotalincome() {
		return tripTotalincome;
	}

	public void setTripTotalincome(Double tripTotalincome) {
		this.tripTotalincome = tripTotalincome;
	}

	public Double getCloseTripAmount() {
		return closeTripAmount;
	}

	public void setCloseTripAmount(Double closeTripAmount) {
		this.closeTripAmount = closeTripAmount;
	}

	public short getCloseTripStatusId() {
		return closeTripStatusId;
	}

	public void setCloseTripStatusId(short closeTripStatusId) {
		this.closeTripStatusId = closeTripStatusId;
	}

	public String getCloseTripReference() {
		return closeTripReference;
	}

	public void setCloseTripReference(String closeTripReference) {
		this.closeTripReference = closeTripReference;
	}

	public Long getCloseTripNameById() {
		return closeTripNameById;
	}

	public void setCloseTripNameById(Long closeTripNameById) {
		this.closeTripNameById = closeTripNameById;
	}

	public Date getClosetripDate() {
		return closetripDate;
	}

	public void setClosetripDate(Date closetripDate) {
		this.closetripDate = closetripDate;
	}

	public Long getDispatchedById() {
		return dispatchedById;
	}

	public void setDispatchedById(Long dispatchedById) {
		this.dispatchedById = dispatchedById;
	}

	public Integer getDispatchedLocationId() {
		return dispatchedLocationId;
	}

	public void setDispatchedLocationId(Integer dispatchedLocationId) {
		this.dispatchedLocationId = dispatchedLocationId;
	}

	public Date getDispatchedByTime() {
		return dispatchedByTime;
	}

	public void setDispatchedByTime(Date dispatchedByTime) {
		this.dispatchedByTime = dispatchedByTime;
	}

	public Long getClosedById() {
		return closedById;
	}

	public void setClosedById(Long closedById) {
		this.closedById = closedById;
	}

	public Integer getClosedLocationId() {
		return closedLocationId;
	}

	public void setClosedLocationId(Integer closedLocationId) {
		this.closedLocationId = closedLocationId;
	}

	public Date getClosedByTime() {
		return closedByTime;
	}

	public void setClosedByTime(Date closedByTime) {
		this.closedByTime = closedByTime;
	}

	public Long getAcclosedById() {
		return acclosedById;
	}

	public void setAcclosedById(Long acclosedById) {
		this.acclosedById = acclosedById;
	}

	public Integer getAcclosedLocationId() {
		return acclosedLocationId;
	}

	public void setAcclosedLocationId(Integer acclosedLocationId) {
		this.acclosedLocationId = acclosedLocationId;
	}

	public Date getAcclosedByTime() {
		return acclosedByTime;
	}

	public void setAcclosedByTime(Date acclosedByTime) {
		this.acclosedByTime = acclosedByTime;
	}

	public short getTripStutesId() {
		return tripStutesId;
	}

	public void setTripStutesId(short tripStutesId) {
		this.tripStutesId = tripStutesId;
	}

	public Long getCloseACCTripNameById() {
		return closeACCTripNameById;
	}

	public void setCloseACCTripNameById(Long closeACCTripNameById) {
		this.closeACCTripNameById = closeACCTripNameById;
	}

	public String getCloseACCTripReference() {
		return closeACCTripReference;
	}

	public void setCloseACCTripReference(String closeACCTripReference) {
		this.closeACCTripReference = closeACCTripReference;
	}

	public Double getCloseACCTripAmount() {
		return closeACCTripAmount;
	}

	public void setCloseACCTripAmount(Double closeACCTripAmount) {
		this.closeACCTripAmount = closeACCTripAmount;
	}

	public Date getCloseACCtripDate() {
		return closeACCtripDate;
	}

	public void setCloseACCtripDate(Date closeACCtripDate) {
		this.closeACCtripDate = closeACCtripDate;
	}

	public Integer getTripCommentTotal() {
		return tripCommentTotal;
	}

	public void setTripCommentTotal(Integer tripCommentTotal) {
		this.tripCommentTotal = tripCommentTotal;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(Long modifiedById) {
		this.modifiedById = modifiedById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Double getTripGpsOpeningKM() {
		return tripGpsOpeningKM;
	}

	public void setTripGpsOpeningKM(Double tripGpsOpeningKM) {
		this.tripGpsOpeningKM = tripGpsOpeningKM;
	}

	public Double getTripGpsClosingKM() {
		return tripGpsClosingKM;
	}

	public void setTripGpsClosingKM(Double tripGpsClosingKM) {
		this.tripGpsClosingKM = tripGpsClosingKM;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getGpsOpeningLocation() {
		return gpsOpeningLocation;
	}

	public void setGpsOpeningLocation(String gpsOpeningLocation) {
		this.gpsOpeningLocation = gpsOpeningLocation;
	}

	public String getGpsCloseLocation() {
		return gpsCloseLocation;
	}

	public void setGpsCloseLocation(String gpsCloseLocation) {
		this.gpsCloseLocation = gpsCloseLocation;
	}

	public Double getGpsTripUsageKM() {
		return gpsTripUsageKM;
	}

	public void setGpsTripUsageKM(Double gpsTripUsageKM) {
		this.gpsTripUsageKM = gpsTripUsageKM;
	}

	public short getLoadTypeId() {
		return loadTypeId;
	}

	public void setLoadTypeId(short loadTypeId) {
		this.loadTypeId = loadTypeId;
	}

	public Integer getNoOfPOD() {
		return noOfPOD;
	}

	public void setNoOfPOD(Integer noOfPOD) {
		this.noOfPOD = noOfPOD;
	}

	public Integer getReceivedPOD() {
		return receivedPOD;
	}

	public void setReceivedPOD(Integer receivedPOD) {
		this.receivedPOD = receivedPOD;
	}

	public Double getTripFristDriverRoutePoint() {
		return tripFristDriverRoutePoint;
	}

	public void setTripFristDriverRoutePoint(Double tripFristDriverRoutePoint) {
		this.tripFristDriverRoutePoint = tripFristDriverRoutePoint;
	}

	public Double getTripSecDriverRoutePoint() {
		return tripSecDriverRoutePoint;
	}

	public void setTripSecDriverRoutePoint(Double tripSecDriverRoutePoint) {
		this.tripSecDriverRoutePoint = tripSecDriverRoutePoint;
	}

	public Double getTripCleanerRoutePoint() {
		return tripCleanerRoutePoint;
	}

	public void setTripCleanerRoutePoint(Double tripCleanerRoutePoint) {
		this.tripCleanerRoutePoint = tripCleanerRoutePoint;
	}

	public Double getTripStartDiesel() {
		return tripStartDiesel;
	}

	public void setTripStartDiesel(Double tripStartDiesel) {
		this.tripStartDiesel = tripStartDiesel;
	}

	public Double getTripEndDiesel() {
		return tripEndDiesel;
	}

	public void setTripEndDiesel(Double tripEndDiesel) {
		this.tripEndDiesel = tripEndDiesel;
	}

	public Date getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}

	public String getDeleteRemark() {
		return deleteRemark;
	}

	public void setDeleteRemark(String deleteRemark) {
		this.deleteRemark = deleteRemark;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public boolean isTripSheetDocument() {
		return tripSheetDocument;
	}

	public void setTripSheetDocument(boolean tripSheetDocument) {
		this.tripSheetDocument = tripSheetDocument;
	}

	public boolean isMeterNotWorking() {
		return meterNotWorking;
	}

	public void setMeterNotWorking(boolean meterNotWorking) {
		this.meterNotWorking = meterNotWorking;
	}

	public Long getTripSheetDocumentId() {
		return tripSheetDocumentId;
	}

	public void setTripSheetDocumentId(Long tripSheetDocumentId) {
		this.tripSheetDocumentId = tripSheetDocumentId;
	}

	public Double getDriverBalance() {
		return driverBalance;
	}

	public void setDriverBalance(Double driverBalance) {
		this.driverBalance = driverBalance;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}