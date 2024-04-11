package org.fleetopgroup.persistence.dto;

import java.util.Arrays;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.Date;
import java.util.Set;

import org.fleetopgroup.web.util.Utility;

import ch.qos.logback.classic.pattern.Util;

public class TripSheetDto {
	
	public static final short TRIP_PAY_TO_OFFICE	= 1;
	public static final short TRIP_PAY_TO_DRIVER	= 2;
	
	
	public static final String TRIP_PAY_TO_OFFICE_NAME	= "OFFICE";
	public static final String TRIP_PAY_TO_DRIVER_NAME	= "DRIVER";

	private Long 	tripSheetID;
	private Long 	tripSheetNumber;
	private Integer vid;
	private String 	vehicle_registration;
	private String 	vehicle_Group;
	private long   	vehicleGroupId;
	private Integer	companyId;
	private int 	tripFristDriverID;
	private String 	tripFristDriverName;
	private String 	tripFristDriverFatherName;
	private String 	tripFristDriverLastName;
	private String 	tripFristDriverMobile;
	private Double 	tripFristDriverBata;
	private int 	tripSecDriverID;
	private String 	tripSecDriverMobile;
	private String 	tripSecDriverName;
	private String 	tripSecDriverFatherName;
	private String 	tripSecDriverLastName;
	private String 	driverEmpNumber;
	private String 	driverCity;
	private Double 	tripSecDriverBata;
	private int 	tripCleanerID;
	private String 	tripCleanerName;
	private String 	tripCleanerMidleName;
	private String 	tripCleanerLastName;
	private String 	tripCleanerMobile;
	private Double 	tripCleanerBata;
	private String 	tripOpenDate;
	private Date   	tripOpenDateOn;
	private Integer	routeID;
	private String 	routeName;
	private Double 	routeAttendancePoint;
	private Integer tripUsageKM;
	private Integer routeApproximateKM;
	private Integer tripDiffernceKM;
	private Double 	tripUsageLiter;
	private Double 	routeTotalLiter;
	private Double 	tripDiffernceLiter;
	private Integer tripOpeningKM;
	private Integer tripClosingKM;
	private String 	tripClosingKMStatus;
	private String 	tripBookref;
	private Double 	tripTotalAdvance;
	private Double 	tripTotalexpense;
	private Double 	tripTotalincome;
	private Double 	closeTripAmount;
	private String 	closeTripStatus;
	private String 	closeTripReference;
	private String 	closeTripNameBy;
	private String 	closetripDate;
	private Date 	closetripDateOn;
	private String 	closeACCTripNameBy;
	private Date 	closeACCtripDate;
	private String 	closeACCtripDateStr;
	private String 	closeACCTripReference;
	private Double 	closeACCTripAmount;
	private String 	tripStutes;
	private String 	dispatchedBy;
	private String 	dispatchedLocation;
	private String 	dispatchedByTime;
	private String 	dispatchedTime;
	private Date 	dispatchedByTimeOn;
	private String 	dispatchedOn;
	private String 	closedByTime;
	private Date 	closedByTimeOn;
	private String 	closedOn;
	private String 	acclosedByTime;
	private Date 	acclosedByTimeOn;
	private String 	closedBy;
	private String 	cloesdLocation;
	private String 	acclosedBy;
	private String 	accloesdLocation;
	private Integer tripCommentTotal;
	private Date 	collectionTripOpenDate;
	private Double 	collectionTollCharge;
	private Double 	collectionFristDriverBata;
	private Double 	collectionSecondDriverBata;
	private Double 	collectionCleanerBata;
	private Double 	collectionWelfareExpense;
	private Double 	collectionHSD;
	private Double 	collectionLuggage;
	private Double 	collectionWashpark;
	private Double 	collectionHaltingBata;
	private Double 	collectionOtherExpense;
	private String 	createdBy;
	private String 	lastModifiedBy;
	private String 	created;
	private String 	lastupdated;
	private Date 	createdOn;
	private Date 	lastupdatedOn;
	private Long 	createdById;
	private short 	tripStutesId;
	private Long 	closedById;
	private Integer dispatchedLocationId;
	private Long 	dispatchedById;
	private Long 	closeTripNameById;
	private short 	closeTripStatusId;
	private short 	isNewRoute;
	private String 	ipAddress;
	private short 	driverSalaryTypeId;
	private Long 	tripSheetCount;
	private Long 	totalKMUsage;
	private String	routeRemark;
	private Double	totalNetIncome;
	private Double	totalIncomeCollection;
	private Double	totalBalance;
    private String[] tripsheetextraname;
    private String[] TripSheetExtraQuantityReceived;
    private String[] TripSheetExtraDescription;
    private Integer subRouteID;
    private String 	subRouteName;
    private String 	TRIP_ROUTE_NAME;
    private Double	tripDiesel;
    private Double  vehicle_ExpectedMileage;
    private Double 	driverSalary;
    private Integer advanceDriverId;
    private Double tripGpsOpeningKM;
    private Double tripGpsClosingKM;
    private Double	tripGpsUsageKM;
    private String	vehicleGPSId;
    private String 	tripEndTime;
    private String 	dispatchTime;
    private short vStatusId;
    private Date tripEndDateTime;
    private String	tripEndDateTimeStr;
	private String gpsOpeningLocation;
	private String gpsCloseLocation;
	private Double gpsTripUsageKM;
	private Integer routeNumber;
	private short	loadTypeId;
	private String	loadTypeStr;
	private String	hexLhpvIds;
	private Double expenseAmount; 
	private String expenseName;
	private String tripSheetNumberStr;
	private Integer noOfPOD;
	private Integer receivedPOD;
	private Double tripFristDriverRoutePoint;
	private Double tripSecDriverRoutePoint;
	private Double tripCleanerRoutePoint;
	private Long	loadTypesId;
	private String	loadTypeName;	
	private double	fuelExpenseAmount;	
	private double	tollExpenseAmount;
	private String	tripSheetCurrentStatus;
	private Long	vehicleCount;
	private double	fuel_liters;
	private Integer	expenseId;
	private Long 	tripTotalUsageKM;
	private Long 	busBookingDetailsId;
	private Double	tripStartDiesel;
	private Double	tripEndDiesel;
	private double	balance;
	private double	totalDebit;
	private double	totalCredit;
    private String	voucherDateStr;
	private long	busId;
	private Long	tallyCompanyId;
	private String	tallyCompanyName;
	private String 	tripCreatedBy;
	private Integer vehicle_ExpectedOdameter;
	private String 	tripSheetCloseDateStr;
	private String 	closedTripTime;
	private String 	tripOpenDateStr;
	private double  differenceKm;
	private double  differenceKmPercentage;
	private boolean	diffKMPercentDanger;
	private boolean tripSheetDocument;
	private Long tripSheetDocumentId;
	private String	accident;
	private String	issue;
	private Long userId;
	private String userName;
	private boolean markForDelete;
	private boolean meterNotWorking=false;
	private boolean isTallyPushNeeded;
	private Double	driverBalance;
	private String  FDriverEmpNo;
	private String  SDriverEmpNo;
	private String  FDdetails;
	private String  SDdetails;
	
	private String  clenerDetails;
	
	private String 	tripFristDriverDL;
	private String 	tripSecDriverDL;
	
	private int  driverId;

	private Double fuelAmount;
	private Double tollAmount;
	
	public Double getFuelAmount() {
		return fuelAmount;
	}

	public void setFuelAmount(Double fuelAmount) {
		this.fuelAmount = fuelAmount;
	}

	public Double getTollAmount() {
		return tollAmount;
	}

	public void setTollAmount(Double tollAmount) {
		this.tollAmount = tollAmount;
	}

	public Double getDriverBalance() {
		return driverBalance;
	}

	public void setDriverBalance(Double driverBalance) {
		this.driverBalance = driverBalance;
	}

	public boolean isTallyPushNeeded() {
		return isTallyPushNeeded;
	}

	public void setTallyPushNeeded(boolean isTallyPushNeeded) {
		this.isTallyPushNeeded = isTallyPushNeeded;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getLoadTypesId() {
		return loadTypesId;
	}

	public String getLoadTypeName() {
		return loadTypeName;
	}

	public void setLoadTypesId(Long loadTypesId) {
		this.loadTypesId = loadTypesId;
	}

	public void setLoadTypeName(String loadTypeName) {
		this.loadTypeName = loadTypeName;
	}

	public String getTripSheetNumberStr() {
		return tripSheetNumberStr;
	}

	public void setTripSheetNumberStr(String tripSheetNumberStr) {
		this.tripSheetNumberStr = tripSheetNumberStr;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public Double getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = Utility.round( expenseAmount, 2);
	}

	public Double getDriverSalary() {
		return driverSalary;
	}

	public void setDriverSalary(Double driverSalary) {
		this.driverSalary = Utility.round( driverSalary, 2);
	}

	public Double getVehicle_ExpectedMileage() {
		return vehicle_ExpectedMileage;
	}

	public void setVehicle_ExpectedMileage(Double vehicle_ExpectedMileage) {
		this.vehicle_ExpectedMileage = Utility.round( vehicle_ExpectedMileage, 2);
	}

	public String getDriverCity() {
		return driverCity;
	}

	public void setDriverCity(String driverCity) {
		this.driverCity = driverCity;
	}

	public String getDriverEmpNumber() {
		return driverEmpNumber;
	}

	public void setDriverEmpNumber(String driverEmpNumber) {
		this.driverEmpNumber = driverEmpNumber;
	}

	public String getTripFristDriverLastName() {
		return tripFristDriverLastName;
	}

	public void setTripFristDriverLastName(String tripFristDriverLastName) {
		this.tripFristDriverLastName = tripFristDriverLastName;
	}

	public String getTRIP_ROUTE_NAME() {
		return TRIP_ROUTE_NAME;
	}

	public void setTRIP_ROUTE_NAME(String tRIP_ROUTE_NAME) {
		TRIP_ROUTE_NAME = tRIP_ROUTE_NAME;
	}

	public String getSubRouteName() {
		return subRouteName;
	}

	public void setSubRouteName(String subRouteName) {
		this.subRouteName = subRouteName;
	}

	public Integer getSubRouteID() {
		return subRouteID;
	}

	public void setSubRouteID(Integer subRouteID) {
		this.subRouteID = subRouteID;
	}

	public String[] getTripsheetextraname() {
		return tripsheetextraname;
	}

	public void setTripsheetextraname(String[] tripsheetextraname) {
		this.tripsheetextraname = tripsheetextraname;
	}

	public String[] getTripSheetExtraQuantityReceived() {
		return TripSheetExtraQuantityReceived;
	}

	public void setTripSheetExtraQuantityReceived(String[] tripSheetExtraQuantityReceived) {
		TripSheetExtraQuantityReceived = tripSheetExtraQuantityReceived;
	}

	public String[] getTripSheetExtraDescription() {
		return TripSheetExtraDescription;
	}

	public void setTripSheetExtraDescription(String[] tripSheetExtraDescription) {
		TripSheetExtraDescription = tripSheetExtraDescription;
	}

	/**
	 * @return the dispatchedByTimeOn
	 */
	public Date getDispatchedByTimeOn() {
		return dispatchedByTimeOn;
	}

	/**
	 * @param dispatchedByTimeOn the dispatchedByTimeOn to set
	 */
	public void setDispatchedByTimeOn(Date dispatchedByTimeOn) {
		this.dispatchedByTimeOn = dispatchedByTimeOn;
	}

	private short tripClosingKMStatusId;
	private Long lastModifiedById;
	private Integer	closedLocationId;
	private Integer	acclosedLocationId;
	private Long acclosedById;
	private Long closeACCTripNameById;

	/**
	 * @return the closetripDateOn
	 */
	public Date getClosetripDateOn() {
		return closetripDateOn;
	}

	/**
	 * @param closetripDateOn the closetripDateOn to set
	 */
	public void setClosetripDateOn(Date closetripDateOn) {
		this.closetripDateOn = closetripDateOn;
	}

	private Set<TripSheetAdvanceDto> tripsheetadvance;

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
	 * @return the tripFristDriverName
	 */
	public String getTripFristDriverName() {
		return tripFristDriverName;
	}

	/**
	 * @param tripFristDriverName
	 *            the tripFristDriverName to set
	 */
	public void setTripFristDriverName(String tripFristDriverName) {
		this.tripFristDriverName = tripFristDriverName;
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
	 * @return the tripSecDriverName
	 */
	public String getTripSecDriverName() {
		return tripSecDriverName;
	}

	/**
	 * @param tripSecDriverName
	 *            the tripSecDriverName to set
	 */
	public void setTripSecDriverName(String tripSecDriverName) {
		this.tripSecDriverName = tripSecDriverName;
	}

	/**
	 * @return the tripStutes
	 */
	public String getTripStutes() {
		return tripStutes;
	}

	/**
	 * @param tripStutes
	 *            the tripStutes to set
	 */
	public void setTripStutes(String tripStutes) {
		this.tripStutes = tripStutes;
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
	 * @return the tripCleanerName
	 */
	public String getTripCleanerName() {
		return tripCleanerName;
	}

	/**
	 * @param tripCleanerName
	 *            the tripCleanerName to set
	 */
	public void setTripCleanerName(String tripCleanerName) {
		this.tripCleanerName = tripCleanerName;
	}

	/**
	 * @return the tripOpenDate
	 */
	public String getTripOpenDate() {
		return tripOpenDate;
	}

	/**
	 * @param tripOpenDate
	 *            the tripOpenDate to set
	 */
	public void setTripOpenDate(String tripOpenDate) {
		this.tripOpenDate = tripOpenDate;
	}

	/**
	 * @return the routeName
	 */
	public String getRouteName() {
		return routeName;
	}

	/**
	 * @param routeName
	 *            the routeName to set
	 */
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	/**
	 * @return the routeApproximateKM
	 */
	public Integer getRouteApproximateKM() {
		return routeApproximateKM;
	}

	/**
	 * @param routeApproximateKM
	 *            the routeApproximateKM to set
	 */
	public void setRouteApproximateKM(Integer routeApproximateKM) {
		this.routeApproximateKM = routeApproximateKM;
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
		this.tripTotalAdvance = Utility.round( tripTotalAdvance, 2);
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
		this.tripTotalexpense = Utility.round(tripTotalexpense, 2);
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
	 * @return the tripOpenDateOn
	 */
	public Date getTripOpenDateOn() {
		return tripOpenDateOn;
	}

	/**
	 * @param tripOpenDateOn the tripOpenDateOn to set
	 */
	public void setTripOpenDateOn(Date tripOpenDateOn) {
		this.tripOpenDateOn = tripOpenDateOn;
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
	 * @return the tripsheetadvance
	 */
	public Set<TripSheetAdvanceDto> getTripsheetadvance() {
		return tripsheetadvance;
	}

	/**
	 * @param tripsheetadvance
	 *            the tripsheetadvance to set
	 */
	public void setTripsheetadvance(Set<TripSheetAdvanceDto> tripsheetadvance) {
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
		this.closeTripAmount = Utility.round( closeTripAmount, 2);
	}

	/**
	 * @return the closeTripStatus
	 */
	public String getCloseTripStatus() {
		return closeTripStatus;
	}

	/**
	 * @param closeTripStatus
	 *            the closeTripStatus to set
	 */
	public void setCloseTripStatus(String closeTripStatus) {
		this.closeTripStatus = closeTripStatus;
	}

	/**
	 * @return the closeTripNameBy
	 */
	public String getCloseTripNameBy() {
		return closeTripNameBy;
	}

	/**
	 * @param closeTripNameBy
	 *            the closeTripNameBy to set
	 */
	public void setCloseTripNameBy(String closeTripNameBy) {
		this.closeTripNameBy = closeTripNameBy;
	}

	/**
	 * @return the closetripDate
	 */
	public String getClosetripDate() {
		return closetripDate;
	}

	/**
	 * @param closetripDate
	 *            the closetripDate to set
	 */
	public void setClosetripDate(String closetripDate) {
		this.closetripDate = closetripDate;
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
		this.tripTotalincome = Utility.round(tripTotalincome, 2) ;
	}

	/**
	 * @return the tripClosingKMStatus
	 */
	public String getTripClosingKMStatus() {
		return tripClosingKMStatus;
	}

	/**
	 * @param tripClosingKMStatus
	 *            the tripClosingKMStatus to set
	 */
	public void setTripClosingKMStatus(String tripClosingKMStatus) {
		this.tripClosingKMStatus = tripClosingKMStatus;
	}

	/**
	 * @return the closeACCTripNameBy
	 */
	public String getCloseACCTripNameBy() {
		return closeACCTripNameBy;
	}

	/**
	 * @param closeACCTripNameBy
	 *            the closeACCTripNameBy to set
	 */
	public void setCloseACCTripNameBy(String closeACCTripNameBy) {
		this.closeACCTripNameBy = closeACCTripNameBy;
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
		this.closeACCTripAmount = Utility.round( closeACCTripAmount, 2);
	}

	/**
	 * @return the tripFristDriverMobile
	 */
	public String getTripFristDriverMobile() {
		return tripFristDriverMobile;
	}

	/**
	 * @param tripFristDriverMobile
	 *            the tripFristDriverMobile to set
	 */
	public void setTripFristDriverMobile(String tripFristDriverMobile) {
		this.tripFristDriverMobile = tripFristDriverMobile;
	}

	/**
	 * @return the tripSecDriverMobile
	 */
	public String getTripSecDriverMobile() {
		return tripSecDriverMobile;
	}

	/**
	 * @param tripSecDriverMobile
	 *            the tripSecDriverMobile to set
	 */
	public void setTripSecDriverMobile(String tripSecDriverMobile) {
		this.tripSecDriverMobile = tripSecDriverMobile;
	}

	/**
	 * @return the tripCleanerMobile
	 */
	public String getTripCleanerMobile() {
		return tripCleanerMobile;
	}

	/**
	 * @param tripCleanerMobile
	 *            the tripCleanerMobile to set
	 */
	public void setTripCleanerMobile(String tripCleanerMobile) {
		this.tripCleanerMobile = tripCleanerMobile;
	}

	/**
	 * @return the routeAttendancePoint
	 */
	public Double getRouteAttendancePoint() {
		return routeAttendancePoint;
	}

	/**
	 * @param routeAttendancePoint
	 *            the routeAttendancePoint to set
	 */
	public void setRouteAttendancePoint(Double routeAttendancePoint) {
		this.routeAttendancePoint = Utility.round( routeAttendancePoint, 2);
	}

	/**
	 * @return the routeTotalLiter
	 */
	public Double getRouteTotalLiter() {
		return routeTotalLiter;
	}

	/**
	 * @param routeTotalLiter
	 *            the routeTotalLiter to set
	 */
	public void setRouteTotalLiter(Double routeTotalLiter) {
		this.routeTotalLiter = Utility.round(routeTotalLiter, 2);
	}

	/**
	 * @return the isNewRoute
	 */
	public short getIsNewRoute() {
		return isNewRoute;
	}

	/**
	 * @param isNewRoute the isNewRoute to set
	 */
	public void setIsNewRoute(short isNewRoute) {
		this.isNewRoute = isNewRoute;
	}

	/**
	 * @return the tripCommentTotal
	 */
	public Integer getTripCommentTotal() {
		return tripCommentTotal;
	}

	/**
	 * @param tripCommentTotal
	 *            the tripCommentTotal to set
	 */
	public void setTripCommentTotal(Integer tripCommentTotal) {
		this.tripCommentTotal = tripCommentTotal;
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
		this.tripFristDriverBata = Utility.round(tripFristDriverBata, 2);
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
		this.tripSecDriverBata = Utility.round(tripSecDriverBata, 2);
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
		this.tripCleanerBata = Utility.round(tripCleanerBata, 2) ;
	}

	/**
	 * @return the collectionTripOpenDate
	 */
	public Date getCollectionTripOpenDate() {
		return collectionTripOpenDate;
	}

	/**
	 * @param collectionTripOpenDate
	 *            the collectionTripOpenDate to set
	 */
	public void setCollectionTripOpenDate(Date collectionTripOpenDate) {
		this.collectionTripOpenDate = collectionTripOpenDate;
	}

	/**
	 * @return the collectionTollCharge
	 */
	public Double getCollectionTollCharge() {
		return collectionTollCharge;
	}

	/**
	 * @param collectionTollCharge
	 *            the collectionTollCharge to set
	 */
	public void setCollectionTollCharge(Double collectionTollCharge) {
		this.collectionTollCharge = Utility.round(collectionTollCharge, 2);
	}

	/**
	 * @return the collectionFristDriverBata
	 */
	public Double getCollectionFristDriverBata() {
		return collectionFristDriverBata;
	}

	/**
	 * @param collectionFristDriverBata
	 *            the collectionFristDriverBata to set
	 */
	public void setCollectionFristDriverBata(Double collectionFristDriverBata) {
		this.collectionFristDriverBata = Utility.round( collectionFristDriverBata, 2);
	}

	/**
	 * @return the collectionSecondDriverBata
	 */
	public Double getCollectionSecondDriverBata() {
		return collectionSecondDriverBata;
	}

	/**
	 * @param collectionSecondDriverBata
	 *            the collectionSecondDriverBata to set
	 */
	public void setCollectionSecondDriverBata(Double collectionSecondDriverBata) {
		this.collectionSecondDriverBata = Utility.round( collectionSecondDriverBata, 2);
	}

	/**
	 * @return the collectionCleanerBata
	 */
	public Double getCollectionCleanerBata() {
		return collectionCleanerBata;
	}

	/**
	 * @param collectionCleanerBata
	 *            the collectionCleanerBata to set
	 */
	public void setCollectionCleanerBata(Double collectionCleanerBata) {
		this.collectionCleanerBata = Utility.round( collectionCleanerBata, 2);
	}

	/**
	 * @return the collectionWelfareExpense
	 */
	public Double getCollectionWelfareExpense() {
		return collectionWelfareExpense;
	}

	/**
	 * @param collectionWelfareExpense
	 *            the collectionWelfareExpense to set
	 */
	public void setCollectionWelfareExpense(Double collectionWelfareExpense) {
		this.collectionWelfareExpense = Utility.round( collectionWelfareExpense, 2);
	}

	/**
	 * @return the collectionHSD
	 */
	public Double getCollectionHSD() {
		return collectionHSD;
	}

	/**
	 * @param collectionHSD
	 *            the collectionHSD to set
	 */
	public void setCollectionHSD(Double collectionHSD) {
		this.collectionHSD = Utility.round( collectionHSD, 2);
	}

	/**
	 * @return the collectionLuggage
	 */
	public Double getCollectionLuggage() {
		return collectionLuggage;
	}

	/**
	 * @param collectionLuggage
	 *            the collectionLuggage to set
	 */
	public void setCollectionLuggage(Double collectionLuggage) {
		this.collectionLuggage = Utility.round( collectionLuggage, 2);
	}

	/**
	 * @return the collectionWashpark
	 */
	public Double getCollectionWashpark() {
		return collectionWashpark;
	}

	/**
	 * @param collectionWashpark
	 *            the collectionWashpark to set
	 */
	public void setCollectionWashpark(Double collectionWashpark) {
		this.collectionWashpark = Utility.round( collectionWashpark, 2);
	}

	/**
	 * @return the collectionHaltingBata
	 */
	public Double getCollectionHaltingBata() {
		return collectionHaltingBata;
	}

	/**
	 * @param collectionHaltingBata
	 *            the collectionHaltingBata to set
	 */
	public void setCollectionHaltingBata(Double collectionHaltingBata) {
		this.collectionHaltingBata = Utility.round( collectionHaltingBata, 2);
	}

	/**
	 * @return the collectionOtherExpense
	 */
	public Double getCollectionOtherExpense() {
		return collectionOtherExpense;
	}

	/**
	 * @param collectionOtherExpense
	 *            the collectionOtherExpense to set
	 */
	public void setCollectionOtherExpense(Double collectionOtherExpense) {
		this.collectionOtherExpense = Utility.round( collectionOtherExpense, 2);
	}

	public String getDispatchedBy() {
		return dispatchedBy;
	}

	public void setDispatchedBy(String dispatchedBy) {
		this.dispatchedBy = dispatchedBy;
	}

	public String getDispatchedLocation() {
		return dispatchedLocation;
	}

	public void setDispatchedLocation(String dispatchedLocation) {
		this.dispatchedLocation = dispatchedLocation;
	}

	public String getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}

	public String getCloesdLocation() {
		return cloesdLocation;
	}

	public void setCloesdLocation(String cloesdLocation) {
		this.cloesdLocation = cloesdLocation;
	}

	public String getAcclosedBy() {
		return acclosedBy;
	}

	public void setAcclosedBy(String acclosedBy) {
		this.acclosedBy = acclosedBy;
	}

	public String getAccloesdLocation() {
		return accloesdLocation;
	}

	public void setAccloesdLocation(String accloesdLocation) {
		this.accloesdLocation = accloesdLocation;
	}

	public String getDispatchedByTime() {
		return dispatchedByTime;
	}

	public void setDispatchedByTime(String dispatchedByTime) {
		this.dispatchedByTime = dispatchedByTime;
	}

	public long getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getClosedByTime() {
		return closedByTime;
	}

	public void setClosedByTime(String closedByTime) {
		this.closedByTime = closedByTime;
	}

	public String getAcclosedByTime() {
		return acclosedByTime;
	}

	public void setAcclosedByTime(String acclosedByTime) {
		this.acclosedByTime = acclosedByTime;
	}
	
	
	

	/**
	 * @return the tripDiffernceKM
	 */
	public Integer getTripDiffernceKM() {
		return tripDiffernceKM;
	}

	/**
	 * @param tripDiffernceKM the tripDiffernceKM to set
	 */
	public void setTripDiffernceKM(Integer tripDiffernceKM) {
		this.tripDiffernceKM = tripDiffernceKM;
	}

	/**
	 * @return the tripUsageLiter
	 */
	public Double getTripUsageLiter() {
		return tripUsageLiter;
	}

	/**
	 * @param tripUsageLiter the tripUsageLiter to set
	 */
	public void setTripUsageLiter(Double tripUsageLiter) {
		this.tripUsageLiter = Utility.round( tripUsageLiter, 2);
	}

	/**
	 * @return the tripDiffernceLiter
	 */
	public Double getTripDiffernceLiter() {
		return tripDiffernceLiter;
	}

	/**
	 * @param tripDiffernceLiter the tripDiffernceLiter to set
	 */
	public void setTripDiffernceLiter(Double tripDiffernceLiter) {
		this.tripDiffernceLiter = Utility.round( tripDiffernceLiter, 2);
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @return the tripStutesId
	 */
	public short getTripStutesId() {
		return tripStutesId;
	}

	/**
	 * @return the closedById
	 */
	public Long getClosedById() {
		return closedById;
	}

	/**
	 * @return the dispatchedLocationId
	 */
	public Integer getDispatchedLocationId() {
		return dispatchedLocationId;
	}

	/**
	 * @return the dispatchedById
	 */
	public Long getDispatchedById() {
		return dispatchedById;
	}

	/**
	 * @return the closeTripNameById
	 */
	public Long getCloseTripNameById() {
		return closeTripNameById;
	}

	/**
	 * @return the closeTripStatusId
	 */
	public short getCloseTripStatusId() {
		return closeTripStatusId;
	}

	/**
	 * @return the tripClosingKMStatusId
	 */
	public short getTripClosingKMStatusId() {
		return tripClosingKMStatusId;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
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
	 * @return the closeACCTripNameById
	 */
	public Long getCloseACCTripNameById() {
		return closeACCTripNameById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @param tripStutesId the tripStutesId to set
	 */
	public void setTripStutesId(short tripStutesId) {
		this.tripStutesId = tripStutesId;
	}

	/**
	 * @param closedById the closedById to set
	 */
	public void setClosedById(Long closedById) {
		this.closedById = closedById;
	}

	/**
	 * @param dispatchedLocationId the dispatchedLocationId to set
	 */
	public void setDispatchedLocationId(Integer dispatchedLocationId) {
		this.dispatchedLocationId = dispatchedLocationId;
	}

	/**
	 * @param dispatchedById the dispatchedById to set
	 */
	public void setDispatchedById(Long dispatchedById) {
		this.dispatchedById = dispatchedById;
	}

	/**
	 * @param closeTripNameById the closeTripNameById to set
	 */
	public void setCloseTripNameById(Long closeTripNameById) {
		this.closeTripNameById = closeTripNameById;
	}

	/**
	 * @param closeTripStatusId the closeTripStatusId to set
	 */
	public void setCloseTripStatusId(short closeTripStatusId) {
		this.closeTripStatusId = closeTripStatusId;
	}

	/**
	 * @param tripClosingKMStatusId the tripClosingKMStatusId to set
	 */
	public void setTripClosingKMStatusId(short tripClosingKMStatusId) {
		this.tripClosingKMStatusId = tripClosingKMStatusId;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
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
	 * @param closeACCTripNameById the closeACCTripNameById to set
	 */
	public void setCloseACCTripNameById(Long closeACCTripNameById) {
		this.closeACCTripNameById = closeACCTripNameById;
	}

	/**
	 * @return the closedByTimeOn
	 */
	public Date getClosedByTimeOn() {
		return closedByTimeOn;
	}

	/**
	 * @param closedByTimeOn the closedByTimeOn to set
	 */
	public void setClosedByTimeOn(Date closedByTimeOn) {
		this.closedByTimeOn = closedByTimeOn;
	}

	/**
	 * @return the acclosedByTimeOn
	 */
	public Date getAcclosedByTimeOn() {
		return acclosedByTimeOn;
	}

	/**
	 * @param acclosedByTimeOn the acclosedByTimeOn to set
	 */
	public void setAcclosedByTimeOn(Date acclosedByTimeOn) {
		this.acclosedByTimeOn = acclosedByTimeOn;
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

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public short getDriverSalaryTypeId() {
		return driverSalaryTypeId;
	}

	public void setDriverSalaryTypeId(short driverSalaryTypeId) {
		this.driverSalaryTypeId = driverSalaryTypeId;
	}

	public Long getTripSheetCount() {
		return tripSheetCount;
	}

	public void setTripSheetCount(Long tripSheetCount) {
		this.tripSheetCount = tripSheetCount;
	}

	public Long getTotalKMUsage() {
		return totalKMUsage;
	}

	public void setTotalKMUsage(Long totalKMUsage) {
		this.totalKMUsage = totalKMUsage;
	}

	public String getRouteRemark() {
		return routeRemark;
	}

	public void setRouteRemark(String routeRemark) {
		this.routeRemark = routeRemark;
	}

	public Double getTotalNetIncome() {
		return totalNetIncome;
	}

	public void setTotalNetIncome(Double totalNetIncome) {
		this.totalNetIncome =  Utility.round(totalNetIncome, 2);
	}

	public Double getTotalIncomeCollection() {
		return totalIncomeCollection;
	}

	public void setTotalIncomeCollection(Double totalIncomeCollection) {
		this.totalIncomeCollection = Utility.round( totalIncomeCollection, 2);
	}

	public Double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = Utility.round( totalBalance, 2);
	}

	public Double getTripDiesel() {
		return tripDiesel;
	}

	public void setTripDiesel(Double tripDiesel) {
		this.tripDiesel = Utility.round(tripDiesel, 2);
	}

	/*public Long getlHPVDetailsId() {
		return lHPVDetailsId;
	}

	public void setlHPVDetailsId(Long lHPVDetailsId) {
		this.lHPVDetailsId = lHPVDetailsId;
	}*/

	public Double getTripGpsOpeningKM() {
		return tripGpsOpeningKM;
	}

	public Double getTripGpsClosingKM() {
		return tripGpsClosingKM;
	}

	public void setTripGpsOpeningKM(Double tripGpsOpeningKM) {
		this.tripGpsOpeningKM = Utility.round( tripGpsOpeningKM, 2);
	}

	public void setTripGpsClosingKM(Double tripGpsClosingKM) {
		this.tripGpsClosingKM = Utility.round( tripGpsClosingKM, 2);
	}
	

	public Integer getAdvanceDriverId() {
		return advanceDriverId;
	}

	public void setAdvanceDriverId(Integer advanceDriverId) {
		this.advanceDriverId = advanceDriverId;
	}

	
	public static String getTripPayToName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case TRIP_PAY_TO_OFFICE:
			  paymentTypeName	= TRIP_PAY_TO_OFFICE_NAME;
		        break;
		  case TRIP_PAY_TO_DRIVER: 
			  paymentTypeName	= TRIP_PAY_TO_DRIVER_NAME;
		        break;
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}

	public Double getTripGpsUsageKM() {
		return tripGpsUsageKM;
	}

	public void setTripGpsUsageKM(Double tripGpsUsageKM) {
		this.tripGpsUsageKM =  Utility.round(tripGpsUsageKM, 2);
	}

	public String getVehicleGPSId() {
		return vehicleGPSId;
	}

	public void setVehicleGPSId(String vehicleGPSId) {
		this.vehicleGPSId = vehicleGPSId;
	}

	public String getTripEndTime() {
		return tripEndTime;
	}

	public void setTripEndTime(String tripEndTime) {
		this.tripEndTime = tripEndTime;
	}

	public Date getTripEndDateTime() {
		return tripEndDateTime;
	}

	public String getTripEndDateTimeStr() {
		return tripEndDateTimeStr;
	}

	public void setTripEndDateTime(Date tripEndDateTime) {
		this.tripEndDateTime = tripEndDateTime;
	}

	public void setTripEndDateTimeStr(String tripEndDateTimeStr) {
		this.tripEndDateTimeStr = tripEndDateTimeStr;
	}

	public short getvStatusId() {
		return vStatusId;
	}

	public void setvStatusId(short vStatusId) {
		this.vStatusId = vStatusId;
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
		this.gpsTripUsageKM = Utility.round( gpsTripUsageKM,2);
	}

	public Integer getRouteNumber() {
		return routeNumber;
	}

	public void setRouteNumber(Integer routeNumber) {
		this.routeNumber = routeNumber;
	}
	

	public short getLoadTypeId() {
		return loadTypeId;
	}

	public void setLoadTypeId(short loadTypeId) {
		this.loadTypeId = loadTypeId;
	}

	public String getLoadTypeStr() {
		return loadTypeStr;
	}

	public void setLoadTypeStr(String loadTypeStr) {
		this.loadTypeStr = loadTypeStr;
	}

	public String getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public String getHexLhpvIds() {
		return hexLhpvIds;
	}

	public void setHexLhpvIds(String hexLhpvIds) {
		this.hexLhpvIds = hexLhpvIds;
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
		this.tripFristDriverRoutePoint = Utility.round(tripFristDriverRoutePoint, 2);
	}

	public Double getTripSecDriverRoutePoint() {
		return tripSecDriverRoutePoint;
	}

	public void setTripSecDriverRoutePoint(Double tripSecDriverRoutePoint) {
		this.tripSecDriverRoutePoint = Utility.round( tripSecDriverRoutePoint, 2);
	}

	public Double getTripCleanerRoutePoint() {
		return tripCleanerRoutePoint;
	}

	public void setTripCleanerRoutePoint(Double tripCleanerRoutePoint) {
		this.tripCleanerRoutePoint = Utility.round( tripCleanerRoutePoint, 2);
	}
	

	public String getTripSheetCurrentStatus() {
		return tripSheetCurrentStatus;
	}

	public void setTripSheetCurrentStatus(String tripSheetCurrentStatus) {
		this.tripSheetCurrentStatus = tripSheetCurrentStatus;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = Utility.round(balance, 2);
	}

	public double getTotalDebit() {
		return totalDebit;
	}

	public void setTotalDebit(double totalDebit) {
		this.totalDebit = Utility.round(totalDebit, 2);
	}

	public double getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(double totalCredit) {
		this.totalCredit = Utility.round( totalCredit, 2);
	}

	public long getBusId() {
		return busId;
	}

	public void setBusId(long busId) {
		this.busId = busId;
	}

	public double getFuelExpenseAmount() {
		return fuelExpenseAmount;
	}

	public void setFuelExpenseAmount(double fuelExpenseAmount) {
		this.fuelExpenseAmount = Utility.round(fuelExpenseAmount, 2);
	}

	public double getTollExpenseAmount() {
		return tollExpenseAmount;
	}

	public void setTollExpenseAmount(double tollExpenseAmount) {
		this.tollExpenseAmount =  Utility.round(tollExpenseAmount, 2);
	}

	public Long getVehicleCount() {
		return vehicleCount;
	}

	public void setVehicleCount(Long vehicleCount) {
		this.vehicleCount = vehicleCount;
	}

	public double getFuel_liters() {
		return fuel_liters;
	}

	public void setFuel_liters(double fuel_liters) {
		this.fuel_liters = Utility.round( fuel_liters, 2);
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public Long getTripTotalUsageKM() {
		return tripTotalUsageKM;
	}

	public void setTripTotalUsageKM(Long tripTotalUsageKM) {
		this.tripTotalUsageKM = tripTotalUsageKM;
	}

	public Long getBusBookingDetailsId() {
		return busBookingDetailsId;
	}

	public void setBusBookingDetailsId(Long busBookingDetailsId) {
		this.busBookingDetailsId = busBookingDetailsId;
	}

	public Double getTripStartDiesel() {
		return tripStartDiesel;
	}

	public void setTripStartDiesel(Double tripStartDiesel) {
		this.tripStartDiesel = Utility.round(tripStartDiesel, 2);
	}

	public Double getTripEndDiesel() {
		return tripEndDiesel;
	}

	public void setTripEndDiesel(Double tripEndDiesel) {
		this.tripEndDiesel =  Utility.round(tripEndDiesel,2);
	}
	
	public String getTripCreatedBy() {
		return tripCreatedBy;
	}

	public void setTripCreatedBy(String tripCreatedBy) {
		this.tripCreatedBy = tripCreatedBy;
	}

	public String getTripSheetCloseDateStr() {
		return tripSheetCloseDateStr;
	}

	public void setTripSheetCloseDateStr(String tripSheetCloseDateStr) {
		this.tripSheetCloseDateStr = tripSheetCloseDateStr;
	}

	public String getClosedTripTime() {
		return closedTripTime;
	}

	public void setClosedTripTime(String closedTripTime) {
		this.closedTripTime = closedTripTime;
	}
	
	public String getVoucherDateStr() {
		return voucherDateStr;
	}

	public void setVoucherDateStr(String voucherDateStr) {
		this.voucherDateStr = voucherDateStr;
	}

	public String getDispatchedTime() {
		return dispatchedTime;
	}

	public void setDispatchedTime(String dispatchedTime) {
		this.dispatchedTime = dispatchedTime;
	}

	public String getCloseACCtripDateStr() {
		return closeACCtripDateStr;
	}

	public void setCloseACCtripDateStr(String closeACCtripDateStr) {
		this.closeACCtripDateStr = closeACCtripDateStr;
	}

	public String getDispatchedOn() {
		return dispatchedOn;
	}

	public void setDispatchedOn(String dispatchedOn) {
		this.dispatchedOn = dispatchedOn;
	}

	public String getClosedOn() {
		return closedOn;
	}

	public void setClosedOn(String closedOn) {
		this.closedOn = closedOn;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public String getTallyCompanyName() {
		return tallyCompanyName;
	}

	public void setTallyCompanyName(String tallyCompanyName) {
		this.tallyCompanyName = tallyCompanyName;
	}

	public String getTripOpenDateStr() {
		return tripOpenDateStr;
	}

	public void setTripOpenDateStr(String tripOpenDateStr) {
		this.tripOpenDateStr = tripOpenDateStr;
	}

	public double getDifferenceKmPercentage() {
		return differenceKmPercentage;
	}

	public void setDifferenceKmPercentage(double differenceKmPercentage) {
		this.differenceKmPercentage =Utility.round( differenceKmPercentage, 2);
	}

	public boolean isDiffKMPercentDanger() {
		return diffKMPercentDanger;
	}

	public void setDiffKMPercentDanger(boolean diffKMPercentDanger) {
		this.diffKMPercentDanger = diffKMPercentDanger;
	}

	public double getDifferenceKm() {
		return differenceKm;
	}

	public void setDifferenceKm(double differenceKm) {
		this.differenceKm = Utility.round(differenceKm, 2);
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
	
	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripSheetDto [tripSheetID=");
		builder.append(tripSheetID);
		builder.append(", tripSheetNumber=");
		builder.append(tripSheetNumber);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", vehicle_registration=");
		builder.append(vehicle_registration);
		builder.append(", vehicle_Group=");
		builder.append(vehicle_Group);
		builder.append(", vehicleGroupId=");
		builder.append(vehicleGroupId);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", tripFristDriverID=");
		builder.append(tripFristDriverID);
		builder.append(", tripFristDriverName=");
		builder.append(tripFristDriverName);
		builder.append(", tripFristDriverLastName=");
		builder.append(tripFristDriverLastName);
		builder.append(", tripFristDriverMobile=");
		builder.append(tripFristDriverMobile);
		builder.append(", tripFristDriverBata=");
		builder.append(tripFristDriverBata);
		builder.append(", tripSecDriverID=");
		builder.append(tripSecDriverID);
		builder.append(", tripSecDriverMobile=");
		builder.append(tripSecDriverMobile);
		builder.append(", tripSecDriverName=");
		builder.append(tripSecDriverName);
		builder.append(", driverEmpNumber=");
		builder.append(driverEmpNumber);
		builder.append(", driverCity=");
		builder.append(driverCity);
		builder.append(", tripSecDriverBata=");
		builder.append(tripSecDriverBata);
		builder.append(", tripCleanerID=");
		builder.append(tripCleanerID);
		builder.append(", tripCleanerName=");
		builder.append(tripCleanerName);
		builder.append(", tripCleanerMobile=");
		builder.append(tripCleanerMobile);
		builder.append(", tripCleanerBata=");
		builder.append(tripCleanerBata);
		builder.append(", tripOpenDate=");
		builder.append(tripOpenDate);
		builder.append(", tripOpenDateOn=");
		builder.append(tripOpenDateOn);
		builder.append(", routeID=");
		builder.append(routeID);
		builder.append(", routeName=");
		builder.append(routeName);
		builder.append(", routeAttendancePoint=");
		builder.append(routeAttendancePoint);
		builder.append(", tripUsageKM=");
		builder.append(tripUsageKM);
		builder.append(", routeApproximateKM=");
		builder.append(routeApproximateKM);
		builder.append(", tripDiffernceKM=");
		builder.append(tripDiffernceKM);
		builder.append(", tripUsageLiter=");
		builder.append(tripUsageLiter);
		builder.append(", routeTotalLiter=");
		builder.append(routeTotalLiter);
		builder.append(", tripDiffernceLiter=");
		builder.append(tripDiffernceLiter);
		builder.append(", tripOpeningKM=");
		builder.append(tripOpeningKM);
		builder.append(", tripClosingKM=");
		builder.append(tripClosingKM);
		builder.append(", tripClosingKMStatus=");
		builder.append(tripClosingKMStatus);
		builder.append(", tripBookref=");
		builder.append(tripBookref);
		builder.append(", tripTotalAdvance=");
		builder.append(tripTotalAdvance);
		builder.append(", tripTotalexpense=");
		builder.append(tripTotalexpense);
		builder.append(", tripTotalincome=");
		builder.append(tripTotalincome);
		builder.append(", closeTripAmount=");
		builder.append(closeTripAmount);
		builder.append(", closeTripStatus=");
		builder.append(closeTripStatus);
		builder.append(", closeTripReference=");
		builder.append(closeTripReference);
		builder.append(", closeTripNameBy=");
		builder.append(closeTripNameBy);
		builder.append(", closetripDate=");
		builder.append(closetripDate);
		builder.append(", closetripDateOn=");
		builder.append(closetripDateOn);
		builder.append(", closeACCTripNameBy=");
		builder.append(closeACCTripNameBy);
		builder.append(", closeACCtripDate=");
		builder.append(closeACCtripDate);
		builder.append(", closeACCTripReference=");
		builder.append(closeACCTripReference);
		builder.append(", closeACCTripAmount=");
		builder.append(closeACCTripAmount);
		builder.append(", tripStutes=");
		builder.append(tripStutes);
		builder.append(", dispatchedBy=");
		builder.append(dispatchedBy);
		builder.append(", dispatchedLocation=");
		builder.append(dispatchedLocation);
		builder.append(", dispatchedByTime=");
		builder.append(dispatchedByTime);
		builder.append(", dispatchedByTimeOn=");
		builder.append(dispatchedByTimeOn);
		builder.append(", closedByTime=");
		builder.append(closedByTime);
		builder.append(", closedByTimeOn=");
		builder.append(closedByTimeOn);
		builder.append(", acclosedByTime=");
		builder.append(acclosedByTime);
		builder.append(", acclosedByTimeOn=");
		builder.append(acclosedByTimeOn);
		builder.append(", closedBy=");
		builder.append(closedBy);
		builder.append(", cloesdLocation=");
		builder.append(cloesdLocation);
		builder.append(", acclosedBy=");
		builder.append(acclosedBy);
		builder.append(", accloesdLocation=");
		builder.append(accloesdLocation);
		builder.append(", tripCommentTotal=");
		builder.append(tripCommentTotal);
		builder.append(", collectionTripOpenDate=");
		builder.append(collectionTripOpenDate);
		builder.append(", collectionTollCharge=");
		builder.append(collectionTollCharge);
		builder.append(", collectionFristDriverBata=");
		builder.append(collectionFristDriverBata);
		builder.append(", collectionSecondDriverBata=");
		builder.append(collectionSecondDriverBata);
		builder.append(", collectionCleanerBata=");
		builder.append(collectionCleanerBata);
		builder.append(", collectionWelfareExpense=");
		builder.append(collectionWelfareExpense);
		builder.append(", collectionHSD=");
		builder.append(collectionHSD);
		builder.append(", collectionLuggage=");
		builder.append(collectionLuggage);
		builder.append(", collectionWashpark=");
		builder.append(collectionWashpark);
		builder.append(", collectionHaltingBata=");
		builder.append(collectionHaltingBata);
		builder.append(", collectionOtherExpense=");
		builder.append(collectionOtherExpense);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastupdatedOn=");
		builder.append(lastupdatedOn);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", tripStutesId=");
		builder.append(tripStutesId);
		builder.append(", closedById=");
		builder.append(closedById);
		builder.append(", dispatchedLocationId=");
		builder.append(dispatchedLocationId);
		builder.append(", dispatchedById=");
		builder.append(dispatchedById);
		builder.append(", closeTripNameById=");
		builder.append(closeTripNameById);
		builder.append(", closeTripStatusId=");
		builder.append(closeTripStatusId);
		builder.append(", isNewRoute=");
		builder.append(isNewRoute);
		builder.append(", ipAddress=");
		builder.append(ipAddress);
		builder.append(", driverSalaryTypeId=");
		builder.append(driverSalaryTypeId);
		builder.append(", tripSheetCount=");
		builder.append(tripSheetCount);
		builder.append(", totalKMUsage=");
		builder.append(totalKMUsage);
		builder.append(", routeRemark=");
		builder.append(routeRemark);
		builder.append(", totalNetIncome=");
		builder.append(totalNetIncome);
		builder.append(", totalIncomeCollection=");
		builder.append(totalIncomeCollection);
		builder.append(", totalBalance=");
		builder.append(totalBalance);
		builder.append(", tripsheetextraname=");
		builder.append(Arrays.toString(tripsheetextraname));
		builder.append(", TripSheetExtraQuantityReceived=");
		builder.append(Arrays.toString(TripSheetExtraQuantityReceived));
		builder.append(", TripSheetExtraDescription=");
		builder.append(Arrays.toString(TripSheetExtraDescription));
		builder.append(", subRouteID=");
		builder.append(subRouteID);
		builder.append(", subRouteName=");
		builder.append(subRouteName);
		builder.append(", TRIP_ROUTE_NAME=");
		builder.append(TRIP_ROUTE_NAME);
		builder.append(", tripDiesel=");
		builder.append(tripDiesel);
		builder.append(", vehicle_ExpectedMileage=");
		builder.append(vehicle_ExpectedMileage);
		builder.append(", driverSalary=");
		builder.append(driverSalary);
		builder.append(", advanceDriverId=");
		builder.append(advanceDriverId);
		builder.append(", tripGpsOpeningKM=");
		builder.append(tripGpsOpeningKM);
		builder.append(", tripGpsClosingKM=");
		builder.append(tripGpsClosingKM);
		builder.append(", tripGpsUsageKM=");
		builder.append(tripGpsUsageKM);
		/*builder.append(", lHPVDetailsId=");
		builder.append(lHPVDetailsId);*/
		builder.append(", vehicleGPSId=");
		builder.append(vehicleGPSId);
		builder.append(", tripEndTime=");
		builder.append(tripEndTime);
		builder.append(", vStatusId=");
		builder.append(vStatusId);
		builder.append(", tripEndDateTime=");
		builder.append(tripEndDateTime);
		builder.append(", tripEndDateTimeStr=");
		builder.append(tripEndDateTimeStr);
		builder.append(", tripClosingKMStatusId=");
		builder.append(tripClosingKMStatusId);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", closedLocationId=");
		builder.append(closedLocationId);
		builder.append(", acclosedLocationId=");
		builder.append(acclosedLocationId);
		builder.append(", acclosedById=");
		builder.append(acclosedById);
		builder.append(", closeACCTripNameById="); 
		builder.append(closeACCTripNameById);
		builder.append(", tripsheetadvance=");
		builder.append(tripsheetadvance);
		builder.append(", routeNumber=");
		builder.append(routeNumber);
		builder.append(", hexLhpvIds=");
		builder.append(hexLhpvIds);
		builder.append(", expenseAmount=");
		builder.append(expenseAmount);
		builder.append(", expenseName=");
		builder.append(expenseName);
		builder.append(", noOfPOD=");
		builder.append(noOfPOD);
		builder.append(", receivedPOD=");
		builder.append(receivedPOD);
		builder.append(", tripFristDriverRoutePoint=");
		builder.append(tripFristDriverRoutePoint);
		builder.append(", tripSecDriverRoutePoint=");
		builder.append(tripSecDriverRoutePoint);
		builder.append(", tripCleanerRoutePoint=");
		builder.append(tripCleanerRoutePoint);
		builder.append(", loadTypeId=");
		builder.append(loadTypeId);
		builder.append(", loadTypesId=");
		builder.append(loadTypesId);
		builder.append(", loadTypeName=");
		builder.append(loadTypeName);
		builder.append(", fuelExpenseAmount=");
		builder.append(fuelExpenseAmount);
		builder.append(", tollExpenseAmount=");
		builder.append(tollExpenseAmount);
		builder.append(", tripSheetCurrentStatus=");
		builder.append(tripSheetCurrentStatus);
		builder.append(", balance=");
		builder.append(balance);
		builder.append(", totalCredit=");
		builder.append(totalCredit);
		builder.append(", totalDebit=");
		builder.append(totalDebit);
		builder.append(", busId=");
		builder.append(busId);
		builder.append(", closedTripTime=");
		builder.append(closedTripTime);
		builder.append(", tripFristDriverFatherName=");
		builder.append(tripFristDriverFatherName);
		builder.append(", tripSecDriverFatherName=");
		builder.append(tripSecDriverFatherName);
		builder.append(", tripSecDriverLastName=");
		builder.append(tripFristDriverLastName);
		builder.append(", driverId=");
		builder.append(driverId);
		builder.append(", driverBalance=");
		builder.append(driverBalance);
		builder.append(", fuelAmount=");
		builder.append(fuelAmount);
		builder.append(", tollAmount=");
		builder.append(tollAmount);
		builder.append("]");
		return builder.toString();
	}

	public Integer getVehicle_ExpectedOdameter() {
		return vehicle_ExpectedOdameter;
	}

	public void setVehicle_ExpectedOdameter(Integer vehicle_ExpectedOdameter) {
		this.vehicle_ExpectedOdameter = vehicle_ExpectedOdameter;
	}

	public String getTripFristDriverFatherName() {
		return tripFristDriverFatherName;
	}

	public void setTripFristDriverFatherName(String tripFristDriverFatherName) {
		this.tripFristDriverFatherName = tripFristDriverFatherName;
	}

	public String getTripSecDriverFatherName() {
		return tripSecDriverFatherName;
	}

	public void setTripSecDriverFatherName(String tripSecDriverFatherName) {
		this.tripSecDriverFatherName = tripSecDriverFatherName;
	}

	public String getTripSecDriverLastName() {
		return tripSecDriverLastName;
	}

	public void setTripSecDriverLastName(String tripSecDriverLastName) {
		this.tripSecDriverLastName = tripSecDriverLastName;
	}

	public String getAccident() {
		return accident;
	}

	public void setAccident(String accident) {
		this.accident = accident;
	}

	public String getIssue() {
		return issue;
	}

	public String getTripCleanerMidleName() {
		return tripCleanerMidleName;
	}

	public void setTripCleanerMidleName(String tripCleanerMidleName) {
		this.tripCleanerMidleName = tripCleanerMidleName;
	}

	public String getTripCleanerLastName() {
		return tripCleanerLastName;
	}

	public void setTripCleanerLastName(String tripCleanerLastName) {
		this.tripCleanerLastName = tripCleanerLastName;
	}

	public boolean isMeterNotWorking() {
		return meterNotWorking;
	}

	public void setMeterNotWorking(boolean meterNotWorking) {
		this.meterNotWorking = meterNotWorking;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getFDriverEmpNo() {
		return FDriverEmpNo;
	}

	public void setFDriverEmpNo(String fDriverEmpNo) {
		FDriverEmpNo = fDriverEmpNo;
	}

	public String getSDriverEmpNo() {
		return SDriverEmpNo;
	}

	public void setSDriverEmpNo(String sDriverEmpNo) {
		SDriverEmpNo = sDriverEmpNo;
	}
	
	public String getClenerDetails() {
		return clenerDetails;
	}

	public void setClenerDetails(String clenerDetails) {
		this.clenerDetails = clenerDetails;
	}

	public String getFDdetails() {
		return FDdetails;
	}

	public void setFDdetails(String fDdetails) {
		FDdetails = fDdetails;
	}

	public String getSDdetails() {
		return SDdetails;
	}

	public void setSDdetails(String sDdetails) {
		SDdetails = sDdetails;
	}

	public String getTripFristDriverDL() {
		return tripFristDriverDL;
	}

	public void setTripFristDriverDL(String tripFristDriverDL) {
		this.tripFristDriverDL = tripFristDriverDL;
	}

	public String getTripSecDriverDL() {
		return tripSecDriverDL;
	}

	public void setTripSecDriverDL(String tripSecDriverDL) {
		this.tripSecDriverDL = tripSecDriverDL;
	}
	
	
}
