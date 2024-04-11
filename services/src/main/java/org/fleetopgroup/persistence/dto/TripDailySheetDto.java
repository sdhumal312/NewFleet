/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

import ch.qos.logback.classic.pattern.Util;

/**
 * @author fleetop
 *
 */
public class TripDailySheetDto {

	private Long 	TRIPDAILYID;
	private Long 	TRIPDAILYNUMBER;
	private Integer VEHICLEID;
	private String 	VEHICLE_REGISTRATION;
	private String 	VEHICLE_GROUP;
	private long 	VEHICLE_GROUP_ID;
	private int 	TRIP_DRIVER_ID;
	private String 	TRIP_DRIVER_NAME;
	private int 	TRIP_CONDUCTOR_ID;
	private String 	TRIP_CONDUCTOR_NAME;
	private int 	TRIP_CLEANER_ID;
	private String 	TRIP_CLEANER_NAME;
	private String 	TRIP_OPEN_DATE;
	private Date  	TRIP_OPEN_DATE_D;
	private Integer TRIP_ROUTE_ID;
	private String 	TRIP_ROUTE_NAME;
	private Double 	TRIP_DIESEL;
	private Double 	TRIP_DIESEL_AMOUNT;
	private Double 	TRIP_DIESELKMPL;
	private Integer TRIP_ROUTE_APPKM;
	private Integer TRIP_TOTALPASSNGER;
	private Integer TRIP_PASS_PASSNGER;
	private Integer TRIP_RFIDPASS;
	private Double 	TRIP_RFID_AMOUNT;
	private Integer TRIP_OPEN_KM;
	private Integer TRIP_CLOSE_KM;
	private Integer TRIP_USAGE_KM;
	private Double 	TOTAL_EXPENSE;
	private Double 	TRIP_OVERTIME;
	private Integer TRIP_SUBROUTE_ID;
	private Double 	TOTAL_INCOME_COLLECTION;
	private Double 	TOTAL_INCOME;
	private Double 	TOTAL_NET_INCOME;
	private Double 	TOTAL_WT;
	private Double 	TOTAL_EPK;
	private Double 	TOTAL_BALANCE;
	private String 	TRIP_CLOSE_STATUS;
	private short 	TRIP_STATUS_ID;
	private String 	TRIP_REMARKS;
	private String 	CREATEDBY;
	private String 	LASTMODIFIEDBY;
	private boolean markForDelete;
	private String 	CREATED;
	private Date 	CREATEDON;
	private Date 	LASTUPDATEDON;
	private String 	LASTUPDATED;
	private Integer COMPANY_ID;
	private Long 	CREATED_BY_ID;
	private Long 	LASTMODIFIED_BY_ID;	
	private float  	noOfRoundTrip;
	private String DAY_OF_MONTH;
	private short vStatusId;
	private String routeNo;
	private boolean multipleTrip;
	private int 	TRIP_SEC_DRIVER_ID;
	private String 	TRIP_SEC_DRIVER_NAME;
	private Integer CHALO_KM;
	private Double CHALO_AMOUNT;
	
	public  String getDAY_OF_MONTH() {
		return DAY_OF_MONTH;
	}
	public void setDAY_OF_MONTH(String dAY_OF_MONTH) {
		DAY_OF_MONTH = dAY_OF_MONTH;
	}
	
	public Long getTRIPSHEETID() {
		return TRIPSHEETID;
	}

	public void setTRIPSHEETID(Long tRIPSHEETID) {
		TRIPSHEETID = tRIPSHEETID;
	}

	private Double	vehicle_ExpectedMileage;
	private String	tripDate;
	private Double  fuel_amount;
    private Double  TOTAL_NET_BALANCE;
    private Long TRIPSHEETID;
	

	
	public Double getTOTAL_NET_BALANCE() {
		return TOTAL_NET_BALANCE;
	}

	public void setTOTAL_NET_BALANCE(Double tOTAL_NET_BALANCE) {
		TOTAL_NET_BALANCE =Utility.round(tOTAL_NET_BALANCE, 2);
	}

	public Double getFuel_amount() {
		return fuel_amount;
	}

	public void setFuel_amount(Double fuel_amount) {
		this.fuel_amount =Utility.round(fuel_amount, 2);
	}

	public Long getTRIPDAILYID() {
		return TRIPDAILYID;
	}

	public void setTRIPDAILYID(Long tRIPDAILYID) {
		TRIPDAILYID = tRIPDAILYID;
	}

	public Long getTRIPDAILYNUMBER() {
		return TRIPDAILYNUMBER;
	}

	public void setTRIPDAILYNUMBER(Long tRIPDAILYNUMBER) {
		TRIPDAILYNUMBER = tRIPDAILYNUMBER;
	}

	public Integer getVEHICLEID() {
		return VEHICLEID;
	}

	public void setVEHICLEID(Integer vEHICLEID) {
		VEHICLEID = vEHICLEID;
	}

	public String getVEHICLE_REGISTRATION() {
		return VEHICLE_REGISTRATION;
	}

	public void setVEHICLE_REGISTRATION(String vEHICLE_REGISTRATION) {
		VEHICLE_REGISTRATION = vEHICLE_REGISTRATION;
	}

	public String getVEHICLE_GROUP() {
		return VEHICLE_GROUP;
	}

	public void setVEHICLE_GROUP(String vEHICLE_GROUP) {
		VEHICLE_GROUP = vEHICLE_GROUP;
	}

	public long getVEHICLE_GROUP_ID() {
		return VEHICLE_GROUP_ID;
	}

	public void setVEHICLE_GROUP_ID(long vEHICLE_GROUP_ID) {
		VEHICLE_GROUP_ID = vEHICLE_GROUP_ID;
	}

	public int getTRIP_DRIVER_ID() {
		return TRIP_DRIVER_ID;
	}

	public void setTRIP_DRIVER_ID(int tRIP_DRIVER_ID) {
		TRIP_DRIVER_ID = tRIP_DRIVER_ID;
	}

	public String getTRIP_DRIVER_NAME() {
		return TRIP_DRIVER_NAME;
	}

	public void setTRIP_DRIVER_NAME(String tRIP_DRIVER_NAME) {
		TRIP_DRIVER_NAME = tRIP_DRIVER_NAME;
	}

	public int getTRIP_CONDUCTOR_ID() {
		return TRIP_CONDUCTOR_ID;
	}

	public Double getTRIP_DIESEL_AMOUNT() {
		return TRIP_DIESEL_AMOUNT;
	}

	public void setTRIP_DIESEL_AMOUNT(Double tRIP_DIESEL_AMOUNT) {
		TRIP_DIESEL_AMOUNT = Utility.round(tRIP_DIESEL_AMOUNT, 2);
	}

	public void setTRIP_CONDUCTOR_ID(int tRIP_CONDUCTOR_ID) {
		TRIP_CONDUCTOR_ID = tRIP_CONDUCTOR_ID;
	}

	public String getTRIP_CONDUCTOR_NAME() {
		return TRIP_CONDUCTOR_NAME;
	}

	public void setTRIP_CONDUCTOR_NAME(String tRIP_CONDUCTOR_NAME) {
		TRIP_CONDUCTOR_NAME = tRIP_CONDUCTOR_NAME;
	}

	public int getTRIP_CLEANER_ID() {
		return TRIP_CLEANER_ID;
	}

	public void setTRIP_CLEANER_ID(int tRIP_CLEANER_ID) {
		TRIP_CLEANER_ID = tRIP_CLEANER_ID;
	}

	public String getTRIP_CLEANER_NAME() {
		return TRIP_CLEANER_NAME;
	}

	public void setTRIP_CLEANER_NAME(String tRIP_CLEANER_NAME) {
		TRIP_CLEANER_NAME = tRIP_CLEANER_NAME;
	}

	public String getTRIP_OPEN_DATE() {
		return TRIP_OPEN_DATE;
	}

	public void setTRIP_OPEN_DATE(String tRIP_OPEN_DATE) {
		TRIP_OPEN_DATE = tRIP_OPEN_DATE;
	}

	public Date getTRIP_OPEN_DATE_D() {
		return TRIP_OPEN_DATE_D;
	}

	public void setTRIP_OPEN_DATE_D(Date tRIP_OPEN_DATE_D) {
		TRIP_OPEN_DATE_D = tRIP_OPEN_DATE_D;
	}

	public Integer getTRIP_ROUTE_ID() {
		return TRIP_ROUTE_ID;
	}

	public void setTRIP_ROUTE_ID(Integer tRIP_ROUTE_ID) {
		TRIP_ROUTE_ID = tRIP_ROUTE_ID;
	}

	public String getTRIP_ROUTE_NAME() {
		return TRIP_ROUTE_NAME;
	}

	public void setTRIP_ROUTE_NAME(String tRIP_ROUTE_NAME) {
		TRIP_ROUTE_NAME = tRIP_ROUTE_NAME;
	}

	public Double getTRIP_DIESEL() {
		return TRIP_DIESEL;
	}

	public void setTRIP_DIESEL(Double tRIP_DIESEL) {
		TRIP_DIESEL = Utility.round(tRIP_DIESEL, 2);
	}

	public Double getTRIP_DIESELKMPL() {
		return TRIP_DIESELKMPL;
	}

	public void setTRIP_DIESELKMPL(Double tRIP_DIESELKMPL) {
		TRIP_DIESELKMPL = Utility.round(tRIP_DIESELKMPL, 2);
	}

	public Integer getTRIP_ROUTE_APPKM() {
		return TRIP_ROUTE_APPKM;
	}

	public void setTRIP_ROUTE_APPKM(Integer tRIP_ROUTE_APPKM) {
		TRIP_ROUTE_APPKM = tRIP_ROUTE_APPKM;
	}

	public Integer getTRIP_TOTALPASSNGER() {
		return TRIP_TOTALPASSNGER;
	}

	public void setTRIP_TOTALPASSNGER(Integer tRIP_TOTALPASSNGER) {
		TRIP_TOTALPASSNGER = tRIP_TOTALPASSNGER;
	}

	public Integer getTRIP_PASS_PASSNGER() {
		return TRIP_PASS_PASSNGER;
	}

	public void setTRIP_PASS_PASSNGER(Integer tRIP_PASS_PASSNGER) {
		TRIP_PASS_PASSNGER = tRIP_PASS_PASSNGER;
	}

	public Integer getTRIP_RFIDPASS() {
		return TRIP_RFIDPASS;
	}

	public void setTRIP_RFIDPASS(Integer tRIP_RFIDPASS) {
		TRIP_RFIDPASS = tRIP_RFIDPASS;
	}

	public Double getTRIP_RFID_AMOUNT() {
		return TRIP_RFID_AMOUNT;
	}

	public void setTRIP_RFID_AMOUNT(Double tRIP_RFID_AMOUNT) {
		TRIP_RFID_AMOUNT = Utility.round(tRIP_RFID_AMOUNT, 2);
	}

	public Integer getTRIP_OPEN_KM() {
		return TRIP_OPEN_KM;
	}

	public void setTRIP_OPEN_KM(Integer tRIP_OPEN_KM) {
		TRIP_OPEN_KM = tRIP_OPEN_KM;
	}

	public Integer getTRIP_CLOSE_KM() {
		return TRIP_CLOSE_KM;
	}

	public void setTRIP_CLOSE_KM(Integer tRIP_CLOSE_KM) {
		TRIP_CLOSE_KM = tRIP_CLOSE_KM;
	}

	public Integer getTRIP_USAGE_KM() {
		return TRIP_USAGE_KM;
	}

	public void setTRIP_USAGE_KM(Integer tRIP_USAGE_KM) {
		TRIP_USAGE_KM = tRIP_USAGE_KM;
	}

	public Double getTOTAL_EXPENSE() {
		return TOTAL_EXPENSE;
	}

	public void setTOTAL_EXPENSE(Double tOTAL_EXPENSE) {
		TOTAL_EXPENSE =  Utility.round(tOTAL_EXPENSE, 2);
	}

	public Double getTRIP_OVERTIME() {
		return TRIP_OVERTIME;
	}

	public void setTRIP_OVERTIME(Double tRIP_OVERTIME) {
		TRIP_OVERTIME = Utility.round(tRIP_OVERTIME,2);
	}

	public Integer getTRIP_SUBROUTE_ID() {
		return TRIP_SUBROUTE_ID;
	}

	public void setTRIP_SUBROUTE_ID(Integer tRIP_SUBROUTE_ID) {
		TRIP_SUBROUTE_ID = tRIP_SUBROUTE_ID;
	}

	public Double getTOTAL_INCOME_COLLECTION() {
		return TOTAL_INCOME_COLLECTION;
	}

	public void setTOTAL_INCOME_COLLECTION(Double tOTAL_INCOME_COLLECTION) {
		TOTAL_INCOME_COLLECTION = Utility.round(tOTAL_INCOME_COLLECTION, 2);
	}

	public Double getTOTAL_INCOME() {
		return TOTAL_INCOME;
	}

	public void setTOTAL_INCOME(Double tOTAL_INCOME) {
		TOTAL_INCOME = Utility.round(tOTAL_INCOME, 2);
	}

	public Double getTOTAL_NET_INCOME() {
		return TOTAL_NET_INCOME;
	}

	public void setTOTAL_NET_INCOME(Double tOTAL_NET_INCOME) {
		TOTAL_NET_INCOME = Utility.round(tOTAL_NET_INCOME, 2);
	}

	public Double getTOTAL_WT() {
		return TOTAL_WT;
	}

	public void setTOTAL_WT(Double tOTAL_WT) {
		TOTAL_WT =Utility.round(tOTAL_WT, 2);
	}

	public Double getTOTAL_EPK() {
		return TOTAL_EPK;
	}

	public void setTOTAL_EPK(Double tOTAL_EPK) {
		TOTAL_EPK = Utility.round(tOTAL_EPK, 2);
	}

	public Double getTOTAL_BALANCE() {
		return TOTAL_BALANCE;
	}

	public void setTOTAL_BALANCE(Double tOTAL_BALANCE) {
		TOTAL_BALANCE = Utility.round(tOTAL_BALANCE, 2);
	}

	public String getTRIP_CLOSE_STATUS() {
		return TRIP_CLOSE_STATUS;
	}

	public void setTRIP_CLOSE_STATUS(String tRIP_CLOSE_STATUS) {
		TRIP_CLOSE_STATUS = tRIP_CLOSE_STATUS;
	}

	public short getTRIP_STATUS_ID() {
		return TRIP_STATUS_ID;
	}

	public void setTRIP_STATUS_ID(short tRIP_STATUS_ID) {
		TRIP_STATUS_ID = tRIP_STATUS_ID;
	}

	public String getTRIP_REMARKS() {
		return TRIP_REMARKS;
	}

	public void setTRIP_REMARKS(String tRIP_REMARKS) {
		TRIP_REMARKS = tRIP_REMARKS;
	}

	public String getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getCREATED() {
		return CREATED;
	}

	public void setCREATED(String cREATED) {
		CREATED = cREATED;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public Date getLASTUPDATEDON() {
		return LASTUPDATEDON;
	}

	public void setLASTUPDATEDON(Date lASTUPDATEDON) {
		LASTUPDATEDON = lASTUPDATEDON;
	}

	public String getLASTUPDATED() {
		return LASTUPDATED;
	}

	public void setLASTUPDATED(String lASTUPDATED) {
		LASTUPDATED = lASTUPDATED;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	public Long getCREATED_BY_ID() {
		return CREATED_BY_ID;
	}

	public void setCREATED_BY_ID(Long cREATED_BY_ID) {
		CREATED_BY_ID = cREATED_BY_ID;
	}

	public Long getLASTMODIFIED_BY_ID() {
		return LASTMODIFIED_BY_ID;
	}

	public void setLASTMODIFIED_BY_ID(Long lASTMODIFIED_BY_ID) {
		LASTMODIFIED_BY_ID = lASTMODIFIED_BY_ID;
	}

	public float getNoOfRoundTrip() {
		return noOfRoundTrip;
	}

	public void setNoOfRoundTrip(float noOfRoundTrip) {
		this.noOfRoundTrip = noOfRoundTrip;
	}

	public Double getVehicle_ExpectedMileage() {
		return vehicle_ExpectedMileage;
	}

	public void setVehicle_ExpectedMileage(Double vehicle_ExpectedMileage) {
		this.vehicle_ExpectedMileage = Utility.round(vehicle_ExpectedMileage, 2);
	}

	public String getTripDate() {
		return tripDate;
	}

	public void setTripDate(String tripDate) {
		this.tripDate = tripDate;
	}

	
	public boolean isMultipleTrip() {
		return multipleTrip;
	}
	public void setMultipleTrip(boolean multipleTrip) {
		this.multipleTrip = multipleTrip;
	}
	
	public short getvStatusId() {
		return vStatusId;
	}
	public void setvStatusId(short vStatusId) {
		this.vStatusId = vStatusId;
	}
	
	
	public int getTRIP_SEC_DRIVER_ID() {
		return TRIP_SEC_DRIVER_ID;
	}
	public void setTRIP_SEC_DRIVER_ID(int tRIP_SEC_DRIVER_ID) {
		TRIP_SEC_DRIVER_ID = tRIP_SEC_DRIVER_ID;
	}
	public String getTRIP_SEC_DRIVER_NAME() {
		return TRIP_SEC_DRIVER_NAME;
	}
	public void setTRIP_SEC_DRIVER_NAME(String tRIP_SEC_DRIVER_NAME) {
		TRIP_SEC_DRIVER_NAME = tRIP_SEC_DRIVER_NAME;
	}
	
	public String getRouteNo() {
		return routeNo;
	}
	public void setRouteNo(String routeNo) {
		this.routeNo = routeNo;
	}
	public Integer getCHALO_KM() {
		return CHALO_KM;
	}
	public void setCHALO_KM(Integer cHALO_KM) {
		CHALO_KM = cHALO_KM;
	}
	public Double getCHALO_AMOUNT() {
		return CHALO_AMOUNT;
	}
	public void setCHALO_AMOUNT(Double cHALO_AMOUNT) {
		CHALO_AMOUNT = Utility.round(cHALO_AMOUNT, 2);
	}	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((TOTAL_BALANCE == null) ? 0 : TOTAL_BALANCE.hashCode());
		result = prime * result + ((TOTAL_EXPENSE == null) ? 0 : TOTAL_EXPENSE.hashCode());
		result = prime * result + ((TOTAL_INCOME == null) ? 0 : TOTAL_INCOME.hashCode());
		result = prime * result + ((TRIP_OVERTIME == null) ? 0 : TRIP_OVERTIME.hashCode());
		result = prime * result + ((TRIPDAILYID == null) ? 0 : TRIPDAILYID.hashCode());
		result = prime * result + ((TRIP_DIESEL == null) ? 0 : TRIP_DIESEL.hashCode());
		result = prime * result + ((TRIP_OPEN_DATE == null) ? 0 : TRIP_OPEN_DATE.hashCode());
		result = prime * result + ((TRIP_ROUTE_NAME == null) ? 0 : TRIP_ROUTE_NAME.hashCode());
		result = prime * result + ((TRIP_TOTALPASSNGER == null) ? 0 : TRIP_TOTALPASSNGER.hashCode());
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
		TripDailySheetDto other = (TripDailySheetDto) obj;
		if (TOTAL_BALANCE == null) {
			if (other.TOTAL_BALANCE != null)
				return false;
		} else if (!TOTAL_BALANCE.equals(other.TOTAL_BALANCE))
			return false;
		if (TOTAL_EXPENSE == null) {
			if (other.TOTAL_EXPENSE != null)
				return false;
		} else if (!TOTAL_EXPENSE.equals(other.TOTAL_EXPENSE))
			return false;
		if (TOTAL_INCOME == null) {
			if (other.TOTAL_INCOME != null)
				return false;
		} else if (!TOTAL_INCOME.equals(other.TOTAL_INCOME))
			return false;
		if (TRIP_OVERTIME == null) {
			if (other.TRIP_OVERTIME != null)
				return false;
		} else if (!TRIP_OVERTIME.equals(other.TRIP_OVERTIME))
			return false;
		if (TRIPDAILYID == null) {
			if (other.TRIPDAILYID != null)
				return false;
		} else if (!TRIPDAILYID.equals(other.TRIPDAILYID))
			return false;
		if (TRIP_DIESEL == null) {
			if (other.TRIP_DIESEL != null)
				return false;
		} else if (!TRIP_DIESEL.equals(other.TRIP_DIESEL))
			return false;
		if (TRIP_OPEN_DATE == null) {
			if (other.TRIP_OPEN_DATE != null)
				return false;
		} else if (!TRIP_OPEN_DATE.equals(other.TRIP_OPEN_DATE))
			return false;
		if (TRIP_ROUTE_NAME == null) {
			if (other.TRIP_ROUTE_NAME != null)
				return false;
		} else if (!TRIP_ROUTE_NAME.equals(other.TRIP_ROUTE_NAME))
			return false;
		if (TRIP_TOTALPASSNGER == null) {
			if (other.TRIP_TOTALPASSNGER != null)
				return false;
		} else if (!TRIP_TOTALPASSNGER.equals(other.TRIP_TOTALPASSNGER))
			return false;
		return true;
	}
	
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripDailySheetDto [TRIPDAILYID=");
		builder.append(TRIPDAILYID);
		builder.append(", TRIPDAILYNUMBER=");
		builder.append(TRIPDAILYNUMBER);
		builder.append(", VEHICLEID=");
		builder.append(VEHICLEID);
		builder.append(", VEHICLE_REGISTRATION=");
		builder.append(VEHICLE_REGISTRATION);
		builder.append(", VEHICLE_GROUP=");
		builder.append(VEHICLE_GROUP);
		builder.append(", VEHICLE_GROUP_ID=");
		builder.append(VEHICLE_GROUP_ID);
		builder.append(", TRIP_DRIVER_ID=");
		builder.append(TRIP_DRIVER_ID);
		builder.append(", TRIP_DRIVER_NAME=");
		builder.append(TRIP_DRIVER_NAME);
		builder.append(", TRIP_CONDUCTOR_ID=");
		builder.append(TRIP_CONDUCTOR_ID);
		builder.append(", TRIP_CONDUCTOR_NAME=");
		builder.append(TRIP_CONDUCTOR_NAME);
		builder.append(", TRIP_CLEANER_ID=");
		builder.append(TRIP_CLEANER_ID);
		builder.append(", TRIP_CLEANER_NAME=");
		builder.append(TRIP_CLEANER_NAME);
		builder.append(", TRIP_OPEN_DATE=");
		builder.append(TRIP_OPEN_DATE);
		builder.append(", TRIP_OPEN_DATE_D=");
		builder.append(TRIP_OPEN_DATE_D);
		builder.append(", TRIP_ROUTE_ID=");
		builder.append(TRIP_ROUTE_ID);
		builder.append(", TRIP_ROUTE_NAME=");
		builder.append(TRIP_ROUTE_NAME);
		builder.append(", TRIP_DIESEL=");
		builder.append(TRIP_DIESEL);
		builder.append(", TRIP_DIESEL_AMOUNT=");
		builder.append(TRIP_DIESEL_AMOUNT);
		builder.append(", TRIP_DIESELKMPL=");
		builder.append(TRIP_DIESELKMPL);
		builder.append(", TRIP_ROUTE_APPKM=");
		builder.append(TRIP_ROUTE_APPKM);
		builder.append(", TRIP_TOTALPASSNGER=");
		builder.append(TRIP_TOTALPASSNGER);
		builder.append(", TRIP_PASS_PASSNGER=");
		builder.append(TRIP_PASS_PASSNGER);
		builder.append(", TRIP_RFIDPASS=");
		builder.append(TRIP_RFIDPASS);
		builder.append(", TRIP_RFID_AMOUNT=");
		builder.append(TRIP_RFID_AMOUNT);
		builder.append(", TRIP_OPEN_KM=");
		builder.append(TRIP_OPEN_KM);
		builder.append(", TRIP_CLOSE_KM=");
		builder.append(TRIP_CLOSE_KM);
		builder.append(", TRIP_USAGE_KM=");
		builder.append(TRIP_USAGE_KM);
		builder.append(", TOTAL_EXPENSE=");
		builder.append(TOTAL_EXPENSE);
		builder.append(", TRIP_OVERTIME=");
		builder.append(TRIP_OVERTIME);
		builder.append(", TRIP_SUBROUTE_ID=");
		builder.append(TRIP_SUBROUTE_ID);
		builder.append(", TOTAL_INCOME_COLLECTION=");
		builder.append(TOTAL_INCOME_COLLECTION);
		builder.append(", TOTAL_INCOME=");
		builder.append(TOTAL_INCOME);
		builder.append(", TOTAL_NET_INCOME=");
		builder.append(TOTAL_NET_INCOME);
		builder.append(", TOTAL_WT=");
		builder.append(TOTAL_WT);
		builder.append(", TOTAL_EPK=");
		builder.append(TOTAL_EPK);
		builder.append(", TOTAL_BALANCE=");
		builder.append(TOTAL_BALANCE);
		builder.append(", TRIP_CLOSE_STATUS=");
		builder.append(TRIP_CLOSE_STATUS);
		builder.append(", TRIP_STATUS_ID=");
		builder.append(TRIP_STATUS_ID);
		builder.append(", TRIP_REMARKS=");
		builder.append(TRIP_REMARKS);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", CREATEDON=");
		builder.append(CREATEDON);
		builder.append(", LASTUPDATEDON=");
		builder.append(LASTUPDATEDON);
		builder.append(", LASTUPDATED=");
		builder.append(LASTUPDATED);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", CREATED_BY_ID=");
		builder.append(CREATED_BY_ID);
		builder.append(", LASTMODIFIED_BY_ID=");
		builder.append(LASTMODIFIED_BY_ID);
		builder.append(", noOfRoundTrip=");
		builder.append(noOfRoundTrip);
		builder.append(", DAY_OF_MONTH=");
		builder.append(DAY_OF_MONTH);
		builder.append(", vStatusId=");
		builder.append(vStatusId);
		builder.append(", routeNo=");
		builder.append(routeNo);
		builder.append(", multipleTrip=");
		builder.append(multipleTrip);
		builder.append(", TRIP_SEC_DRIVER_ID=");
		builder.append(TRIP_SEC_DRIVER_ID);
		builder.append(", TRIP_SEC_DRIVER_NAME=");
		builder.append(TRIP_SEC_DRIVER_NAME);
		builder.append(", CHALO_KM=");
		builder.append(CHALO_KM);
		builder.append(", CHALO_AMOUNT=");
		builder.append(CHALO_AMOUNT);
		builder.append(", vehicle_ExpectedMileage=");
		builder.append(vehicle_ExpectedMileage);
		builder.append(", tripDate=");
		builder.append(tripDate);
		builder.append(", fuel_amount=");
		builder.append(fuel_amount);
		builder.append(", TOTAL_NET_BALANCE=");
		builder.append(TOTAL_NET_BALANCE);
		builder.append(", TRIPSHEETID=");
		builder.append(TRIPSHEETID);
		builder.append("]");
		return builder.toString();
	}	
		
}
