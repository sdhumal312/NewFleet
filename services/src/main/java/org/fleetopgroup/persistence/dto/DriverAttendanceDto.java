package org.fleetopgroup.persistence.dto;

import java.io.Serializable;
import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class DriverAttendanceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The value for the DRIVERATTENDANCE ID field */
	private Long DAID;

	/** The value for the DRIVER ID field */
	private int DRIVERID;

	/** The value for the DRIVER FRIST NAME field */
	private String DRIVER_NAME;

	/** The value for the DRIVER_GROUP field */
	private String DRIVER_GROUP;
	
	private long DRIVER_GROUP_ID;

	private Integer DRIVER_JOBTITLE;
	/**
	 * The value for the DRIVER to create ATTENDANCE name one user name field
	 */
	private String ATTENDANCE_DATE;
	
	private Date D_ATTENDANCE_DATE;

	/** The value for the DRIVER ATTENDANCE DAY ONLY name */
	private String DADATE;

	/** The value for the DRIVER ATTENDANCE MONTH ONLY name */
	private String DAMONTH;

	/** The value for the DRIVER ATTENDANCE YEAR ONLY name */
	private String DAYEAR;

	/** The value for the DRIVER TRIP ID field */
	private Long TRIPSHEETID;
	
	/** The value for the DRIVER TRIP ID field */
	private Long TRIPSHEETNUMBER;


	/** The value for the DRIVER TRIP NAME field */
	private String TRIP_ROUTE_NAME;

	/** The value for the TRIP_OPEN_DATE NAME field */
	private String TRIP_OPEN_DATE;

	/** The value for the TRIP_CLOSE_DATE NAME field */
	private String TRIP_CLOSE_DATE;

	/** The value for the TRIP_VEHICLE_ NAME field */
	private String TRIP_VEHICLE_NAME;

	/** The value for the STATUS NAME field */
	private String ATTENDANCE_STATUS;
	
	private short ATTENDANCE_STATUS_ID;

	/** The value for the DRIVER_POINT eg: 2.0 1.0 */
	private Double DRIVER_POINT;

	/** The value for the POINT_TYPE eg: TS, Halt field */
	private String POINT_TYPE;

	/** The value for the POINT_STATUS NAME field */
	private String POINT_STATUS;
	
	private short POINT_STATUS_ID;
	
	private short POINT_TYPE_ID;

	/** The value for the CREATEDBY NAME field */
	private String CREATEDBY;

	/** The value for the LASTUPDATEDBY NAME field */
	private String LASTUPDATEDBY;

	/** The value for the STATUS NAME field */
	boolean markForDelete;

	/** The value for the CREATED NAME field */
	private Date CREATED;

	/** The value for the LASTUPDATED NAME field */
	private Date LASTUPDATED;
	
	private Integer TRIP_ROUTE_ID;
	
	private String driver_empnumber;
	
	private String driverJobType;
	
	private long totalWorkingDays;
	
	private long extraWorkingDays;
	
	private long allWorkingDays;
	
	private Double perDaySalary;
	
	private short 	driverSalaryTypeId;
	private Long 	tripSheetCount;
	private Long 	totalKMUsage;
	
	private int	tripSheetFlavor;
	
	private Integer	tripUsageKM;
	
	private Long	tripSheetId;
	
	private Long	tripSheetNumber;
	
	private Double driverSalary;
	
	private Long	driverSalaryInsertionDetailsId;
	
	private Integer	driverFirstId;
	
	private Integer driverSecondId;
	
	private Integer cleanerId;

	private Long  tripSheetExpenseId;
	
	private Double expenseAmount;
	
	private Integer expenseId;
	
	private boolean incldriverbalance;
	
	public Integer getDriverFirstId() {
		return driverFirstId;
	}

	public void setDriverFirstId(Integer driverFirstId) {
		this.driverFirstId = driverFirstId;
	}

	public Integer getDriverSecondId() {
		return driverSecondId;
	}

	public void setDriverSecondId(Integer driverSecondId) {
		this.driverSecondId = driverSecondId;
	}

	public Integer getCleanerId() {
		return cleanerId;
	}

	public void setCleanerId(Integer cleanerId) {
		this.cleanerId = cleanerId;
	}

	public DriverAttendanceDto() {
		super();
	}

	public DriverAttendanceDto(Long dAID, int dRIVERID, String dRIVER_NAME, String aTTENDANCE_DATE, Long tRIPSHEETID,
			String tRIP_ROUTE_NAME, String aTTENDANCE_STATUS, String cREATEDBY, String lASTUPDATEDBY, boolean MarkForDelete,
			Date cREATED, Date lASTUPDATED) {
		super();
		DAID = dAID;
		DRIVERID = dRIVERID;
		DRIVER_NAME = dRIVER_NAME;
		ATTENDANCE_DATE = aTTENDANCE_DATE;
		TRIPSHEETID = tRIPSHEETID;
		TRIP_ROUTE_NAME = tRIP_ROUTE_NAME;
		ATTENDANCE_STATUS = aTTENDANCE_STATUS;
		CREATEDBY = cREATEDBY;
		LASTUPDATEDBY = lASTUPDATEDBY;
		markForDelete = MarkForDelete;
		CREATED = cREATED;
		LASTUPDATED = lASTUPDATED;
	}

	/**
	 * @return the dAID
	 */
	public Long getDAID() {
		return DAID;
	}

	/**
	 * @param dAID
	 *            the dAID to set
	 */
	public void setDAID(Long dAID) {
		DAID = dAID;
	}

	/**
	 * @return the dRIVERID
	 */
	public int getDRIVERID() {
		return DRIVERID;
	}

	/**
	 * @param dRIVERID
	 *            the dRIVERID to set
	 */
	public void setDRIVERID(int dRIVERID) {
		DRIVERID = dRIVERID;
	}

	/**
	 * @return the dRIVER_NAME
	 */
	public String getDRIVER_NAME() {
		return DRIVER_NAME;
	}

	/**
	 * @param dRIVER_NAME
	 *            the dRIVER_NAME to set
	 */
	public void setDRIVER_NAME(String dRIVER_NAME) {
		DRIVER_NAME = dRIVER_NAME;
	}

	/**
	 * @return the aTTENDANCE_DATE
	 */
	public String getATTENDANCE_DATE() {
		return ATTENDANCE_DATE;
	}

	/**
	 * @param aTTENDANCE_DATE
	 *            the aTTENDANCE_DATE to set
	 */
	public void setATTENDANCE_DATE(String aTTENDANCE_DATE) {
		ATTENDANCE_DATE = aTTENDANCE_DATE;
	}

	public Date getD_ATTENDANCE_DATE() {
		return D_ATTENDANCE_DATE;
	}

	public void setD_ATTENDANCE_DATE(Date d_ATTENDANCE_DATE) {
		D_ATTENDANCE_DATE = d_ATTENDANCE_DATE;
	}

	/**
	 * @return the dADATE
	 */
	public String getDADATE() {
		return DADATE;
	}

	/**
	 * @param dADATE
	 *            the dADATE to set
	 */
	public void setDADATE(String dADATE) {
		DADATE = dADATE;
	}

	public long getDRIVER_GROUP_ID() {
		return DRIVER_GROUP_ID;
	}

	public void setDRIVER_GROUP_ID(long dRIVER_GROUP_ID) {
		DRIVER_GROUP_ID = dRIVER_GROUP_ID;
	}

	public Integer getDRIVER_JOBTITLE() {
		return DRIVER_JOBTITLE;
	}

	public void setDRIVER_JOBTITLE(Integer dRIVER_JOBTITLE) {
		DRIVER_JOBTITLE = dRIVER_JOBTITLE;
	}

	/**
	 * @return the dAMONTH
	 */
	public String getDAMONTH() {
		return DAMONTH;
	}

	/**
	 * @param dAMONTH
	 *            the dAMONTH to set
	 */
	public void setDAMONTH(String dAMONTH) {
		DAMONTH = dAMONTH;
	}

	/**
	 * @return the dAYEAR
	 */
	public String getDAYEAR() {
		return DAYEAR;
	}

	/**
	 * @param dAYEAR
	 *            the dAYEAR to set
	 */
	public void setDAYEAR(String dAYEAR) {
		DAYEAR = dAYEAR;
	}

	/**
	 * @return the tRIPSHEETID
	 */
	public Long getTRIPSHEETID() {
		return TRIPSHEETID;
	}

	/**
	 * @param tRIPSHEETID
	 *            the tRIPSHEETID to set
	 */
	public void setTRIPSHEETID(Long tRIPSHEETID) {
		TRIPSHEETID = tRIPSHEETID;
	}

	public Long getTRIPSHEETNUMBER() {
		return TRIPSHEETNUMBER;
	}

	public void setTRIPSHEETNUMBER(Long tRIPSHEETNUMBER) {
		TRIPSHEETNUMBER = tRIPSHEETNUMBER;
	}

	public int getTripSheetFlavor() {
		return tripSheetFlavor;
	}

	public void setTripSheetFlavor(int tripSheetFlavor) {
		this.tripSheetFlavor = tripSheetFlavor;
	}

	/**
	 * @return the tRIP_ROUTE_NAME
	 */
	public String getTRIP_ROUTE_NAME() {
		return TRIP_ROUTE_NAME;
	}

	public Integer getTripUsageKM() {
		return tripUsageKM;
	}

	public void setTripUsageKM(Integer tripUsageKM) {
		this.tripUsageKM = tripUsageKM;
	}

	/**
	 * @param tRIP_ROUTE_NAME
	 *            the tRIP_ROUTE_NAME to set
	 */
	public void setTRIP_ROUTE_NAME(String tRIP_ROUTE_NAME) {
		TRIP_ROUTE_NAME = tRIP_ROUTE_NAME;
	}

	/**
	 * @return the aTTENDANCE_STATUS
	 */
	public String getATTENDANCE_STATUS() {
		return ATTENDANCE_STATUS;
	}

	/**
	 * @param aTTENDANCE_STATUS
	 *            the aTTENDANCE_STATUS to set
	 */
	public void setATTENDANCE_STATUS(String aTTENDANCE_STATUS) {
		ATTENDANCE_STATUS = aTTENDANCE_STATUS;
	}

	/**
	 * @return the cREATEDBY
	 */
	public String getCREATEDBY() {
		return CREATEDBY;
	}

	/**
	 * @param cREATEDBY
	 *            the cREATEDBY to set
	 */
	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	/**
	 * @return the lASTUPDATEDBY
	 */
	public String getLASTUPDATEDBY() {
		return LASTUPDATEDBY;
	}

	/**
	 * @param lASTUPDATEDBY
	 *            the lASTUPDATEDBY to set
	 */
	public void setLASTUPDATEDBY(String lASTUPDATEDBY) {
		LASTUPDATEDBY = lASTUPDATEDBY;
	}

	/**
	 * @return the sTATUS
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param sTATUS
	 *            the sTATUS to set
	 */
	public void setMarkForDelete(boolean MarkForDelete) {
		markForDelete = MarkForDelete;
	}

	/**
	 * @return the cREATED
	 */
	public Date getCREATED() {
		return CREATED;
	}

	/**
	 * @param cREATED
	 *            the cREATED to set
	 */
	public void setCREATED(Date cREATED) {
		CREATED = cREATED;
	}

	/**
	 * @return the lASTUPDATED
	 */
	public Date getLASTUPDATED() {
		return LASTUPDATED;
	}

	/**
	 * @param lASTUPDATED
	 *            the lASTUPDATED to set
	 */
	public void setLASTUPDATED(Date lASTUPDATED) {
		LASTUPDATED = lASTUPDATED;
	}

	/**
	 * @return the dRIVER_POINT
	 */
	public Double getDRIVER_POINT() {
		return DRIVER_POINT;
	}

	/**
	 * @param dRIVER_POINT
	 *            the dRIVER_POINT to set
	 */
	public void setDRIVER_POINT(Double dRIVER_POINT) {
		DRIVER_POINT = Utility.round(dRIVER_POINT,2);
	}

	/**
	 * @return the pOINT_TYPE
	 */
	public String getPOINT_TYPE() {
		return POINT_TYPE;
	}

	/**
	 * @param pOINT_TYPE
	 *            the pOINT_TYPE to set
	 */
	public void setPOINT_TYPE(String pOINT_TYPE) {
		POINT_TYPE = pOINT_TYPE;
	}

	/**
	 * @return the pOINT_STATUS
	 */
	public String getPOINT_STATUS() {
		return POINT_STATUS;
	}

	/**
	 * @param pOINT_STATUS
	 *            the pOINT_STATUS to set
	 */
	public void setPOINT_STATUS(String pOINT_STATUS) {
		POINT_STATUS = pOINT_STATUS;
	}

	public short getPOINT_STATUS_ID() {
		return POINT_STATUS_ID;
	}

	public void setPOINT_STATUS_ID(short pOINT_STATUS_ID) {
		POINT_STATUS_ID = pOINT_STATUS_ID;
	}

	public short getPOINT_TYPE_ID() {
		return POINT_TYPE_ID;
	}

	public void setPOINT_TYPE_ID(short pOINT_TYPE_ID) {
		POINT_TYPE_ID = pOINT_TYPE_ID;
	}

	/**
	 * @return the dRIVER_GROUP
	 */
	public String getDRIVER_GROUP() {
		return DRIVER_GROUP;
	}

	/**
	 * @param dRIVER_GROUP
	 *            the dRIVER_GROUP to set
	 */
	public void setDRIVER_GROUP(String dRIVER_GROUP) {
		DRIVER_GROUP = dRIVER_GROUP;
	}

	/**
	 * @return the tRIP_OPEN_DATE
	 */
	public String getTRIP_OPEN_DATE() {
		return TRIP_OPEN_DATE;
	}

	/**
	 * @param tRIP_OPEN_DATE
	 *            the tRIP_OPEN_DATE to set
	 */
	public void setTRIP_OPEN_DATE(String tRIP_OPEN_DATE) {
		TRIP_OPEN_DATE = tRIP_OPEN_DATE;
	}

	/**
	 * @return the tRIP_CLOSE_DATE
	 */
	public String getTRIP_CLOSE_DATE() {
		return TRIP_CLOSE_DATE;
	}

	/**
	 * @param tRIP_CLOSE_DATE
	 *            the tRIP_CLOSE_DATE to set
	 */
	public void setTRIP_CLOSE_DATE(String tRIP_CLOSE_DATE) {
		TRIP_CLOSE_DATE = tRIP_CLOSE_DATE;
	}

	/**
	 * @return the tRIP_VEHICLE_NAME
	 */
	public String getTRIP_VEHICLE_NAME() {
		return TRIP_VEHICLE_NAME;
	}

	/**
	 * @param tRIP_VEHICLE_NAME
	 *            the tRIP_VEHICLE_NAME to set
	 */
	public void setTRIP_VEHICLE_NAME(String tRIP_VEHICLE_NAME) {
		TRIP_VEHICLE_NAME = tRIP_VEHICLE_NAME;
	}

	public short getATTENDANCE_STATUS_ID() {
		return ATTENDANCE_STATUS_ID;
	}

	public void setATTENDANCE_STATUS_ID(short aTTENDANCE_STATUS_ID) {
		ATTENDANCE_STATUS_ID = aTTENDANCE_STATUS_ID;
	}

	/**
	 * @return the tRIP_ROUTE_ID
	 */
	public Integer getTRIP_ROUTE_ID() {
		return TRIP_ROUTE_ID;
	}

	/**
	 * @param tRIP_ROUTE_ID the tRIP_ROUTE_ID to set
	 */
	public void setTRIP_ROUTE_ID(Integer tRIP_ROUTE_ID) {
		TRIP_ROUTE_ID = tRIP_ROUTE_ID;
	}

	public String getDriver_empnumber() {
		return driver_empnumber;
	}

	public void setDriver_empnumber(String driver_empnumber) {
		this.driver_empnumber = driver_empnumber;
	}

	public String getDriverJobType() {
		return driverJobType;
	}

	public void setDriverJobType(String driverJobType) {
		this.driverJobType = driverJobType;
	}

	public long getTotalWorkingDays() {
		return totalWorkingDays;
	}

	public void setTotalWorkingDays(long totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}

	public long getExtraWorkingDays() {
		return extraWorkingDays;
	}

	public void setExtraWorkingDays(long extraWorkingDays) {
		this.extraWorkingDays = extraWorkingDays;
	}

	public Double getPerDaySalary() {
		return perDaySalary;
	}

	public void setPerDaySalary(Double perDaySalary) {
		this.perDaySalary =Utility.round(perDaySalary, 2) ;
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

	public long getAllWorkingDays() {
		return allWorkingDays;
	}

	public void setAllWorkingDays(long allWorkingDays) {
		this.allWorkingDays = allWorkingDays;
	}
	

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public Double getDriverSalary() {
		return driverSalary;
	}

	public void setDriverSalary(Double driverSalary) {
		this.driverSalary = Utility.round(driverSalary,2);
	}

	public Long getDriverSalaryInsertionDetailsId() {
		return driverSalaryInsertionDetailsId;
	}

	public void setDriverSalaryInsertionDetailsId(Long driverSalaryInsertionDetailsId) {
		this.driverSalaryInsertionDetailsId = driverSalaryInsertionDetailsId;
	}

	
	public Long getTripSheetExpenseId() {
		return tripSheetExpenseId;
	}

	public void setTripSheetExpenseId(Long tripSheetExpenseId) {
		this.tripSheetExpenseId = tripSheetExpenseId;
	}

	public Double getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}
	
	public boolean isIncldriverbalance() {
		return incldriverbalance;
	}

	public void setIncldriverbalance(boolean incldriverbalance) {
		this.incldriverbalance = incldriverbalance;
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
		result = prime * result + ((DAID == null) ? 0 : DAID.hashCode());
		result = prime * result + DRIVERID;
		result = prime * result + ((DRIVER_NAME == null) ? 0 : DRIVER_NAME.hashCode());
		result = prime * result + ((TRIPSHEETID == null) ? 0 : TRIPSHEETID.hashCode());
		result = prime * result + ((TRIP_ROUTE_NAME == null) ? 0 : TRIP_ROUTE_NAME.hashCode());
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
		DriverAttendanceDto other = (DriverAttendanceDto) obj;
		if (DAID == null) {
			if (other.DAID != null)
				return false;
		} else if (!DAID.equals(other.DAID))
			return false;
		if (DRIVERID != other.DRIVERID)
			return false;
		if (DRIVER_NAME == null) {
			if (other.DRIVER_NAME != null)
				return false;
		} else if (!DRIVER_NAME.equals(other.DRIVER_NAME))
			return false;
		if (TRIPSHEETID == null) {
			if (other.TRIPSHEETID != null)
				return false;
		} else if (!TRIPSHEETID.equals(other.TRIPSHEETID))
			return false;
		if (TRIP_ROUTE_NAME == null) {
			if (other.TRIP_ROUTE_NAME != null)
				return false;
		} else if (!TRIP_ROUTE_NAME.equals(other.TRIP_ROUTE_NAME))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverAttendanceDto [DAID=");
		builder.append(DAID);
		builder.append(", DRIVERID=");
		builder.append(DRIVERID);
		builder.append(", DRIVER_NAME=");
		builder.append(DRIVER_NAME);
		builder.append(", DRIVER_GROUP=");
		builder.append(DRIVER_GROUP);
		builder.append(", DRIVER_GROUP_ID=");
		builder.append(DRIVER_GROUP_ID);
		builder.append(", DRIVER_JOBTITLE=");
		builder.append(DRIVER_JOBTITLE);
		builder.append(", ATTENDANCE_DATE=");
		builder.append(ATTENDANCE_DATE);
		builder.append(", D_ATTENDANCE_DATE=");
		builder.append(D_ATTENDANCE_DATE);
		builder.append(", DADATE=");
		builder.append(DADATE);
		builder.append(", DAMONTH=");
		builder.append(DAMONTH);
		builder.append(", DAYEAR=");
		builder.append(DAYEAR);
		builder.append(", TRIPSHEETID=");
		builder.append(TRIPSHEETID);
		builder.append(", TRIPSHEETNUMBER=");
		builder.append(TRIPSHEETNUMBER);
		builder.append(", TRIP_ROUTE_NAME=");
		builder.append(TRIP_ROUTE_NAME);
		builder.append(", TRIP_OPEN_DATE=");
		builder.append(TRIP_OPEN_DATE);
		builder.append(", TRIP_CLOSE_DATE=");
		builder.append(TRIP_CLOSE_DATE);
		builder.append(", TRIP_VEHICLE_NAME=");
		builder.append(TRIP_VEHICLE_NAME);
		builder.append(", ATTENDANCE_STATUS=");
		builder.append(ATTENDANCE_STATUS);
		builder.append(", ATTENDANCE_STATUS_ID=");
		builder.append(ATTENDANCE_STATUS_ID);
		builder.append(", DRIVER_POINT=");
		builder.append(DRIVER_POINT);
		builder.append(", POINT_TYPE=");
		builder.append(POINT_TYPE);
		builder.append(", POINT_STATUS=");
		builder.append(POINT_STATUS);
		builder.append(", POINT_STATUS_ID=");
		builder.append(POINT_STATUS_ID);
		builder.append(", POINT_TYPE_ID=");
		builder.append(POINT_TYPE_ID);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTUPDATEDBY=");
		builder.append(LASTUPDATEDBY);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", LASTUPDATED=");
		builder.append(LASTUPDATED);
		builder.append(", TRIP_ROUTE_ID=");
		builder.append(TRIP_ROUTE_ID);
		builder.append(", driver_empnumber=");
		builder.append(driver_empnumber);
		builder.append(", driverJobType=");
		builder.append(driverJobType);
		builder.append(", totalWorkingDays=");
		builder.append(totalWorkingDays);
		builder.append(", extraWorkingDays=");
		builder.append(extraWorkingDays);
		builder.append(", allWorkingDays=");
		builder.append(allWorkingDays);
		builder.append(", perDaySalary=");
		builder.append(perDaySalary);
		builder.append(", driverSalaryTypeId=");
		builder.append(driverSalaryTypeId);
		builder.append(", tripSheetCount=");
		builder.append(tripSheetCount);
		builder.append(", totalKMUsage=");
		builder.append(totalKMUsage);
		builder.append(", tripSheetFlavor=");
		builder.append(tripSheetFlavor);
		builder.append(", tripUsageKM=");
		builder.append(tripUsageKM);
		builder.append(", tripSheetId=");
		builder.append(tripSheetId);
		builder.append(", tripSheetNumber=");
		builder.append(tripSheetNumber);
		builder.append(", tripSheetExpenseId=");
		builder.append(tripSheetExpenseId);
		builder.append(", expenseAmount=");
		builder.append(expenseAmount);
		builder.append(", expenseId");
		builder.append(expenseId);
		builder.append("]");
		builder.append(", Incldriverbalance=");
		builder.append(incldriverbalance);
		return builder.toString();
	}

	
}
