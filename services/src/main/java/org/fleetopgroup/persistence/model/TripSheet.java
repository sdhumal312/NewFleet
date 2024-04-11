package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "tripsheet")
public class TripSheet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Column(name = "routeName", length = 200)
	 private String routeName;
	
	@Column(name = "tripOpeningKM", length = 10)
	private Integer tripOpeningKM;
	
	@Column(name = "tripGpsOpeningKM")
	private Double tripGpsOpeningKM;

	@Column(name = "tripClosingKM", length = 10)
	private Integer tripClosingKM;
	
	@Column(name = "tripGpsClosingKM")
	private Double tripGpsClosingKM;
	
	@Column(name = "tripClosingKMStatusId", nullable = false)
	private short tripClosingKMStatusId;

	@Column(name = "tripUsageKM", length = 10)
	private Integer tripUsageKM;

	@Column(name = "tripBookref", length = 200)
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
	
	@Column(name = "closeTripReference", length = 200)
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

	@OneToMany(mappedBy = "tripsheet", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<TripSheetAdvance> tripsheetadvance;

	@OneToMany(mappedBy = "tripsheet", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<TripSheetExpense> tripsheetexpense;

	@OneToMany(mappedBy = "tripsheet", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<TripSheetIncome> tripsheetincome;

	/** The value for the created by email field */
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long createdById;

	/** The value for the lastUpdated By email field */
	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;
	
	@Column(name = "ipAddress")
	private String ipAddress;
	
	@Column(name = "subRoute")
	private String subRoute;
	
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
	
	@Column(name = "lhpvId")
	private Integer lhpvId;
	
	
	public Double getDriverBalance() {
		return driverBalance;
	}

	public void setDriverBalance(Double driverBalance) {
		this.driverBalance = driverBalance;
	}

	public String getSubRoute() {
		return subRoute;
	}

	public void setSubRoute(String subRoute) {
		this.subRoute = subRoute;
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
	 * @return the tripFristDriverID
	 */
	public int getTripFristDriverID() {
		return tripFristDriverID;
	}

	/**
	 * @param tripFristDriverID
	 *            the tripFristDriverID to set
	 */
	public void setTripFristDriverID(int tripFristDriverID) {
		this.tripFristDriverID = tripFristDriverID;
	}

	
	/**
	 * @return the tripSecDriverID
	 */
	public int getTripSecDriverID() {
		return tripSecDriverID;
	}

	/**
	 * @param tripSecDriverID
	 *            the tripSecDriverID to set
	 */
	public void setTripSecDriverID(int tripSecDriverID) {
		this.tripSecDriverID = tripSecDriverID;
	}


	/**
	 * @return the tripCleanerID
	 */
	public int getTripCleanerID() {
		return tripCleanerID;
	}

	/**
	 * @param tripCleanerID
	 *            the tripCleanerID to set
	 */
	public void setTripCleanerID(int tripCleanerID) {
		this.tripCleanerID = tripCleanerID;
	}

	

	/**
	 * @return the tripOpenDate
	 */
	public Date getTripOpenDate() {
		return tripOpenDate;
	}

	/**
	 * @param tripOpenDate
	 *            the tripOpenDate to set
	 */
	public void setTripOpenDate(Date tripOpenDate) {
		this.tripOpenDate = tripOpenDate;
	}

	/**
	 * @return the tripOpeningKM
	 */
	public Integer getTripOpeningKM() {
		return tripOpeningKM;
	}

	/**
	 * @param tripOpeningKM
	 *            the tripOpeningKM to set
	 */
	public void setTripOpeningKM(Integer tripOpeningKM) {
		this.tripOpeningKM = tripOpeningKM;
	}

	/**
	 * @return the tripClosingKM
	 */
	public Integer getTripClosingKM() {
		return tripClosingKM;
	}

	/**
	 * @param tripClosingKM
	 *            the tripClosingKM to set
	 */
	public void setTripClosingKM(Integer tripClosingKM) {
		this.tripClosingKM = tripClosingKM;
	}

	/**
	 * @return the tripUsageKM
	 */
	public Integer getTripUsageKM() {
		return tripUsageKM;
	}

	/**
	 * @param tripUsageKM
	 *            the tripUsageKM to set
	 */
	public void setTripUsageKM(Integer tripUsageKM) {
		this.tripUsageKM = tripUsageKM;
	}

	/**
	 * @return the tripBookref
	 */
	public String getTripBookref() {
		return tripBookref;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	/**
	 * @param tripBookref
	 *            the tripBookref to set
	 */
	public void setTripBookref(String tripBookref) {
		this.tripBookref = tripBookref;
	}

	/**
	 * @return the tripTotalAdvance
	 */
	public Double getTripTotalAdvance() {
		return tripTotalAdvance;
	}

	/**
	 * @param tripTotalAdvance
	 *            the tripTotalAdvance to set
	 */
	public void setTripTotalAdvance(Double tripTotalAdvance) {
		this.tripTotalAdvance = tripTotalAdvance;
	}

	/**
	 * @return the tripTotalexpense
	 */
	public Double getTripTotalexpense() {
		return tripTotalexpense;
	}

	/**
	 * @param tripTotalexpense
	 *            the tripTotalexpense to set
	 */
	public void setTripTotalexpense(Double tripTotalexpense) {
		this.tripTotalexpense = tripTotalexpense;
	}

	

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}


	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the tripsheetadvance
	 */
	public Set<TripSheetAdvance> getTripsheetadvance() {
		return tripsheetadvance;
	}

	/**
	 * @param tripsheetadvance
	 *            the tripsheetadvance to set
	 */
	public void setTripsheetadvance(Set<TripSheetAdvance> tripsheetadvance) {
		this.tripsheetadvance = tripsheetadvance;
	}

	/**
	 * @return the routeID
	 */
	public Integer getRouteID() {
		return routeID;
	}

	/**
	 * @param routeID
	 *            the routeID to set
	 */
	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	/**
	 * @return the closeTripAmount
	 */
	public Double getCloseTripAmount() {
		return closeTripAmount;
	}

	/**
	 * @param closeTripAmount
	 *            the closeTripAmount to set
	 */
	public void setCloseTripAmount(Double closeTripAmount) {
		this.closeTripAmount = closeTripAmount;
	}

	

	/**
	 * @return the closetripDate
	 */
	public Date getClosetripDate() {
		return closetripDate;
	}

	/**
	 * @param closetripDate
	 *            the closetripDate to set
	 */
	public void setClosetripDate(Date closetripDate) {
		this.closetripDate = closetripDate;
	}

	/**
	 * @return the tripsheetexpense
	 */
	public Set<TripSheetExpense> getTripsheetexpense() {
		return tripsheetexpense;
	}

	/**
	 * @param tripsheetexpense
	 *            the tripsheetexpense to set
	 */
	public void setTripsheetexpense(Set<TripSheetExpense> tripsheetexpense) {
		this.tripsheetexpense = tripsheetexpense;
	}

	

	/**
	 * @return the tripsheetincome
	 */
	public Set<TripSheetIncome> getTripsheetincome() {
		return tripsheetincome;
	}

	/**
	 * @param tripsheetincome
	 *            the tripsheetincome to set
	 */
	public void setTripsheetincome(Set<TripSheetIncome> tripsheetincome) {
		this.tripsheetincome = tripsheetincome;
	}

	/**
	 * @return the tripTotalincome
	 */
	public Double getTripTotalincome() {
		return tripTotalincome;
	}

	/**
	 * @param tripTotalincome
	 *            the tripTotalincome to set
	 */
	public void setTripTotalincome(Double tripTotalincome) {
		this.tripTotalincome = tripTotalincome;
	}

	

	/**
	 * @return the closeACCtripDate
	 */
	public Date getCloseACCtripDate() {
		return closeACCtripDate;
	}

	/**
	 * @param closeACCtripDate
	 *            the closeACCtripDate to set
	 */
	public void setCloseACCtripDate(Date closeACCtripDate) {
		this.closeACCtripDate = closeACCtripDate;
	}

	/**
	 * @return the closeTripReference
	 */
	public String getCloseTripReference() {
		return closeTripReference;
	}

	/**
	 * @param closeTripReference
	 *            the closeTripReference to set
	 */
	public void setCloseTripReference(String closeTripReference) {
		this.closeTripReference = closeTripReference;
	}

	/**
	 * @return the closeACCTripReference
	 */
	public String getCloseACCTripReference() {
		return closeACCTripReference;
	}

	/**
	 * @param closeACCTripReference
	 *            the closeACCTripReference to set
	 */
	public void setCloseACCTripReference(String closeACCTripReference) {
		this.closeACCTripReference = closeACCTripReference;
	}

	/**
	 * @return the closeACCTripAmount
	 */
	public Double getCloseACCTripAmount() {
		return closeACCTripAmount;
	}

	/**
	 * @param closeACCTripAmount
	 *            the closeACCTripAmount to set
	 */
	public void setCloseACCTripAmount(Double closeACCTripAmount) {
		this.closeACCTripAmount = closeACCTripAmount;
	}

	/**
	 * @return the tripCommentTotal
	 */
	public Integer getTripCommentTotal() {
		return tripCommentTotal;
	}

	/**
	 * @return the tripFristDriverBata
	 */
	public Double getTripFristDriverBata() {
		return tripFristDriverBata;
	}

	/**
	 * @param tripFristDriverBata
	 *            the tripFristDriverBata to set
	 */
	public void setTripFristDriverBata(Double tripFristDriverBata) {
		this.tripFristDriverBata = tripFristDriverBata;
	}

	/**
	 * @return the tripSecDriverBata
	 */
	public Double getTripSecDriverBata() {
		return tripSecDriverBata;
	}

	/**
	 * @param tripSecDriverBata
	 *            the tripSecDriverBata to set
	 */
	public void setTripSecDriverBata(Double tripSecDriverBata) {
		this.tripSecDriverBata = tripSecDriverBata;
	}

	/**
	 * @return the tripCleanerBata
	 */
	public Double getTripCleanerBata() {
		return tripCleanerBata;
	}

	/**
	 * @param tripCleanerBata
	 *            the tripCleanerBata to set
	 */
	public void setTripCleanerBata(Double tripCleanerBata) {
		this.tripCleanerBata = tripCleanerBata;
	}

	/**
	 * @param tripCommentTotal
	 *            the tripCommentTotal to set
	 */
	public void setTripCommentTotal(Integer tripCommentTotal) {
		this.tripCommentTotal = tripCommentTotal;
	}

	

	public Date getDispatchedByTime() {
		return dispatchedByTime;
	}

	public void setDispatchedByTime(Date dispatchedByTime) {
		this.dispatchedByTime = dispatchedByTime;
	}

	public Date getClosedByTime() {
		return closedByTime;
	}

	public void setClosedByTime(Date closedByTime) {
		this.closedByTime = closedByTime;
	}

	public Date getAcclosedByTime() {
		return acclosedByTime;
	}

	public void setAcclosedByTime(Date acclosedByTime) {
		this.acclosedByTime = acclosedByTime;
	}

	/**
	 * @return the tripClosingKMStatusId
	 */
	public short getTripClosingKMStatusId() {
		return tripClosingKMStatusId;
	}

	/**
	 * @return the closeTripStatusId
	 */
	public short getCloseTripStatusId() {
		return closeTripStatusId;
	}

	/**
	 * @return the closeTripNameById
	 */
	public Long getCloseTripNameById() {
		return closeTripNameById;
	}

	/**
	 * @return the dispatchedById
	 */
	public Long getDispatchedById() {
		return dispatchedById;
	}

	/**
	 * @return the dispatchedLocationId
	 */
	public Integer getDispatchedLocationId() {
		return dispatchedLocationId;
	}

	/**
	 * @return the closedById
	 */
	public Long getClosedById() {
		return closedById;
	}

	/**
	 * @return the tripStutesId
	 */
	public short getTripStutesId() {
		return tripStutesId;
	}

	/**
	 * @return the closeACCTripNameById
	 */
	public Long getCloseACCTripNameById() {
		return closeACCTripNameById;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param tripClosingKMStatusId the tripClosingKMStatusId to set
	 */
	public void setTripClosingKMStatusId(short tripClosingKMStatusId) {
		this.tripClosingKMStatusId = tripClosingKMStatusId;
	}

	/**
	 * @param closeTripStatusId the closeTripStatusId to set
	 */
	public void setCloseTripStatusId(short closeTripStatusId) {
		this.closeTripStatusId = closeTripStatusId;
	}

	/**
	 * @param closeTripNameById the closeTripNameById to set
	 */
	public void setCloseTripNameById(Long closeTripNameById) {
		this.closeTripNameById = closeTripNameById;
	}

	/**
	 * @param dispatchedById the dispatchedById to set
	 */
	public void setDispatchedById(Long dispatchedById) {
		this.dispatchedById = dispatchedById;
	}

	/**
	 * @param dispatchedLocationId the dispatchedLocationId to set
	 */
	public void setDispatchedLocationId(Integer dispatchedLocationId) {
		this.dispatchedLocationId = dispatchedLocationId;
	}

	/**
	 * @param closedById the closedById to set
	 */
	public void setClosedById(Long closedById) {
		this.closedById = closedById;
	}

	/**
	 * @param tripStutesId the tripStutesId to set
	 */
	public void setTripStutesId(short tripStutesId) {
		this.tripStutesId = tripStutesId;
	}

	/**
	 * @param closeACCTripNameById the closeACCTripNameById to set
	 */
	public void setCloseACCTripNameById(Long closeACCTripNameById) {
		this.closeACCTripNameById = closeACCTripNameById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	/**
	 * @return the closedLocationId
	 */
	public Integer getClosedLocationId() {
		return closedLocationId;
	}

	/**
	 * @return the acclosedLocationId
	 */
	public Integer getAcclosedLocationId() {
		return acclosedLocationId;
	}

	/**
	 * @return the acclosedById
	 */
	public Long getAcclosedById() {
		return acclosedById;
	}

	/**
	 * @param closedLocationId the closedLocationId to set
	 */
	public void setClosedLocationId(Integer closedLocationId) {
		this.closedLocationId = closedLocationId;
	}

	/**
	 * @param acclosedLocationId the acclosedLocationId to set
	 */
	public void setAcclosedLocationId(Integer acclosedLocationId) {
		this.acclosedLocationId = acclosedLocationId;
	}

	/**
	 * @param acclosedById the acclosedById to set
	 */
	public void setAcclosedById(Long acclosedById) {
		this.acclosedById = acclosedById;
	}

	/**
	 * @return the routeName
	 */
	public String getRouteName() {
		return routeName;
	}

	/**
	 * @param routeName the routeName to set
	 */
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

/*	public Long getlHPVDetailsId() {
		return lHPVDetailsId;
	}

	public void setlHPVDetailsId(Long lHPVDetailsId) {
		this.lHPVDetailsId = lHPVDetailsId;
	}*/

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Double getTripGpsOpeningKM() {
		return tripGpsOpeningKM;
	}

	public Double getTripGpsClosingKM() {
		return tripGpsClosingKM;
	}

	public void setTripGpsOpeningKM(Double tripGpsOpeningKM) {
		this.tripGpsOpeningKM = tripGpsOpeningKM;
	}

	public void setTripGpsClosingKM(Double tripGpsClosingKM) {
		this.tripGpsClosingKM = tripGpsClosingKM;
	}

	

	public String getGpsOpeningLocation() {
		return gpsOpeningLocation;
	}

	public String getGpsCloseLocation() {
		return gpsCloseLocation;
	}

	public void setGpsOpeningLocation(String gpsOpeningLocation) {
		this.gpsOpeningLocation = gpsOpeningLocation;
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
	
	//cleanerRoutePoint
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

	public Long getTripSheetDocumentId() {
		return tripSheetDocumentId;
	}

	public void setTripSheetDocumentId(Long tripSheetDocumentId) {
		this.tripSheetDocumentId = tripSheetDocumentId;
	}

	public boolean isMeterNotWorking() {
		return meterNotWorking;
	}

	public void setMeterNotWorking(boolean meterNotWorking) {
		this.meterNotWorking = meterNotWorking;
	}

	public TripSheet() {
		super();
	}
	
	

	public Integer getLhpvId() {
		return lhpvId;
	}

	public void setLhpvId(Integer lhpvId) {
		this.lhpvId = lhpvId;
	}

	public TripSheet(Long tripSheetID) {
		super();
		this.tripSheetID = tripSheetID;
	}

	public TripSheet(Long tripSheetID, Integer vid, 
			int tripFristDriverID,  int tripSecDriverID,
			 int tripCleanerID,  Date tripOpenDate, Integer routeID,  
			Integer tripOpeningKM, Integer tripClosingKM,  Integer tripUsageKM,
			String tripBookref, Double tripTotalAdvance, Double tripTotalexpense, Double tripTotalincome,
			Double closeTripAmount,  String closeTripReference, 
			Date closetripDate,  String closeACCTripReference,
			Double closeACCTripAmount, Date closeACCtripDate,
			Date created, Date lastupdated, Set<TripSheetAdvance> tripsheetadvance,
			Set<TripSheetExpense> tripsheetexpense, Set<TripSheetIncome> tripsheetincome, String subRoute,
			Integer noOfPOD, Integer receivedPOD, Double tripFristDriverRoutePoint, Double tripSecDriverRoutePoint,
			Double tripCleanerRoutePoint) {
		super();
		this.tripSheetID = tripSheetID;
		this.vid = vid;
		this.tripFristDriverID = tripFristDriverID;
		this.tripSecDriverID = tripSecDriverID;
		this.tripCleanerID = tripCleanerID;
		this.tripOpenDate = tripOpenDate;
		this.routeID = routeID;
		this.tripOpeningKM = tripOpeningKM;
		this.tripClosingKM = tripClosingKM;
		this.tripUsageKM = tripUsageKM;
		this.tripBookref = tripBookref;
		this.tripTotalAdvance = tripTotalAdvance;
		this.tripTotalexpense = tripTotalexpense;
		this.tripTotalincome = tripTotalincome;
		this.closeTripAmount = closeTripAmount;
		this.closeTripReference = closeTripReference;
		this.closetripDate = closetripDate;
		this.closeACCTripReference = closeACCTripReference;
		this.closeACCTripAmount = closeACCTripAmount;
		this.closeACCtripDate = closeACCtripDate;
		this.created = created;
		this.lastupdated = lastupdated;
		this.tripsheetadvance = tripsheetadvance;
		this.tripsheetexpense = tripsheetexpense;
		this.tripsheetincome = tripsheetincome;
		this.subRoute = subRoute;
		this.noOfPOD = noOfPOD;
		this.receivedPOD = receivedPOD;
		this.tripFristDriverRoutePoint = tripFristDriverRoutePoint;
		this.tripSecDriverRoutePoint = tripSecDriverRoutePoint;
		this.tripCleanerRoutePoint = tripCleanerRoutePoint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((routeID == null) ? 0 : routeID.hashCode());
		result = prime * result + ((tripSheetID == null) ? 0 : tripSheetID.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TripSheet other = (TripSheet) obj;
		if (routeID == null) {
			if (other.routeID != null)
				return false;
		} else if (!routeID.equals(other.routeID))
			return false;
		if (tripSheetID == null) {
			if (other.tripSheetID != null)
				return false;
		} else if (!tripSheetID.equals(other.tripSheetID))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TripSheet [tripSheetID=" + tripSheetID + ", tripSheetNumber=" + tripSheetNumber + ", vid=" + vid
				+ ", tripFristDriverID=" + tripFristDriverID + ", tripFristDriverBata=" + tripFristDriverBata
				+ ", tripSecDriverID=" + tripSecDriverID + ", tripSecDriverBata=" + tripSecDriverBata
				+ ", tripCleanerID=" + tripCleanerID + ", tripCleanerBata=" + tripCleanerBata + ", tripOpenDate="
				+ tripOpenDate + ", routeID=" + routeID + ", routeName=" + routeName + ", tripOpeningKM="
				+ tripOpeningKM + ", tripGpsOpeningKM=" + tripGpsOpeningKM + ", tripClosingKM=" + tripClosingKM
				+ ", tripGpsClosingKM=" + tripGpsClosingKM + ", tripClosingKMStatusId=" + tripClosingKMStatusId
				+ ", tripUsageKM=" + tripUsageKM + ", tripBookref=" + tripBookref + ", tripTotalAdvance="
				+ tripTotalAdvance + ", tripTotalexpense=" + tripTotalexpense + ", tripTotalincome=" + tripTotalincome
				+ ", closeTripAmount=" + closeTripAmount + ", closeTripStatusId=" + closeTripStatusId
				+ ", closeTripReference=" + closeTripReference + ", closeTripNameById=" + closeTripNameById
				+ ", closetripDate=" + closetripDate + ", dispatchedById=" + dispatchedById + ", dispatchedLocationId="
				+ dispatchedLocationId + ", dispatchedByTime=" + dispatchedByTime + ", closedById=" + closedById
				+ ", closedLocationId=" + closedLocationId + ", closedByTime=" + closedByTime + ", acclosedById="
				+ acclosedById + ", acclosedLocationId=" + acclosedLocationId + ", acclosedByTime=" + acclosedByTime
				+ ", tripStutesId=" + tripStutesId + ", closeACCTripNameById=" + closeACCTripNameById
				+ ", closeACCTripReference=" + closeACCTripReference + ", closeACCTripAmount=" + closeACCTripAmount
				+ ", closeACCtripDate=" + closeACCtripDate + ", tripCommentTotal=" + tripCommentTotal + ", companyId="
				+ companyId + ", tripsheetadvance=" + tripsheetadvance + ", tripsheetexpense=" + tripsheetexpense
				+ ", tripsheetincome=" + tripsheetincome + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", markForDelete=" + markForDelete + ", created=" + created + ", lastupdated="
				+ lastupdated + ", ipAddress=" + ipAddress + ", subRoute=" + subRoute + ", gpsOpeningLocation="
				+ gpsOpeningLocation + ", gpsCloseLocation=" + gpsCloseLocation + ", gpsTripUsageKM=" + gpsTripUsageKM
				+ ", loadTypeId=" + loadTypeId + ", noOfPOD=" + noOfPOD + ", receivedPOD=" + receivedPOD
				+ ", tripFristDriverRoutePoint=" + tripFristDriverRoutePoint + ", tripSecDriverRoutePoint="
				+ tripSecDriverRoutePoint + ", tripCleanerRoutePoint=" + tripCleanerRoutePoint + ", tripStartDiesel="
				+ tripStartDiesel + ", tripEndDiesel=" + tripEndDiesel + ", voucherDate=" + voucherDate
				+ ", deleteRemark=" + deleteRemark + ", tallyCompanyId=" + tallyCompanyId + ", tripSheetDocument="
				+ tripSheetDocument + ", meterNotWorking=" + meterNotWorking + ", tripSheetDocumentId="
				+ tripSheetDocumentId + ", driverBalance=" + driverBalance + ", isPendingForTally=" + isPendingForTally
				+ ", lhpvId=" + lhpvId + "]";
	}

	

}
