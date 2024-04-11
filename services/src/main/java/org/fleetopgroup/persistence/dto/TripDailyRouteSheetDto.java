package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class TripDailyRouteSheetDto {

	/** The value for the TRIPGROUPID field */
	private Long TRIPROUTEID;

	/** The value for the VEHICLE GROUP COMPANY NAME field */
	private String VEHICLE_GROUP;
	
	private Integer TRIP_ROUTE_ID;

	/** The value for the TRIP DRIVER NAME field */
	private String TRIP_DRIVER_NAME;

	/** The value for the TRIP CONDUCTOR NAME field */
	private String TRIP_CONDUCTOR_NAME;

	/** The value for the TRIP_ROUTE_ID DATE field */
	private String TRIP_ROUTE_NAME;

	/** The value for the TRIP_OPEN DATE field */
	private String TRIP_OPEN_DATE;

	/** The value for the TRIP_OPEN_KM DATE field */
	private Integer TRIP_OPEN_KM;

	/** The value for the TRIP_CLOSE_KM DATE field */
	private Integer TRIP_CLOSE_KM;

	/** The value for the TOTAL_USAGE_KM field */
	private Integer TOTAL_USAGE_KM;

	/** The value for the TOTAL_DIESEL DATE field */
	private Double TOTAL_DIESEL;
	
	private Double TOTAL_DIESEL_AMOUNT;

	/** The value for the TOTAL_DIESEL DATE field */
	private Double TOTAL_DIESELKML;

	/** The value for the TOTAL_TOTALPASSNGER TOTAL field */
	private Integer TOTAL_TOTALPASSNGER;
	
	/** The value for the TRIP_PASS  field */
	private Integer TOTAL_PASS_PASSNGER;

	/** The value for the TOTAL_RFIDPASS AMOUNT field */
	private Integer TOTAL_RFIDPASS;
	
	/** The value for the TRIP_RFID_AMOUNT field */
	private Double TOTAL_RFID_AMOUNT;

	/** The value for the TRIP_TOTAL_COLLECTION DATE field */
	private Double TOTAL_COLLECTION;
	
	/** The value for the TRIP_TOTAL_NET_COLLECTION DATE field */
	private Double TOTAL_NET_COLLECTION;
	
	/** The value for the TRIP_TOTAL_COLLECTION DATE field */
	private Double TOTAL_WT;


	/** The value for the TRIP_TOTAL_EPK DATE field */
	private Double TOTAL_EPK;

	/** The value for the TRIP_TOTAL_EXPENSE DATE field */
	private Double TOTAL_EXPENSE;

	/** The value for the TRIP_TOTAL_OVERTIME AMOUNT field */
	private Double TOTAL_OVERTIME;

	/** The value for the TRIP_TOTAL_BALANCE DATE field */
	private Double TOTAL_BALANCE;

	/** The value for the TOTAL_BUS field */
	private Integer TOTAL_BUS;

	/** The value for the TRIP_CLOSE_STATUS DATE field */
	private String TRIP_CLOSE_STATUS;
	
	private short TRIP_STATUS_ID;
	
	private Integer COMPANY_ID;
	
	private long VEHICLE_GROUP_ID;
	
	private Date TRIP_OPEN_DATE_ON;
	
	private double DIESEL_Amount;
	
	private Integer CHALO_KM;
	
	private Double CHALO_AMOUNT;
	

	public TripDailyRouteSheetDto() {
		super();
	}

	public TripDailyRouteSheetDto(Long tRIPROUTEID, String vEHICLE_GROUP, String tRIP_ROUTE_NAME, String tRIP_OPEN_DATE,
			Integer tOTAL_USAGE_KM, Double tOTAL_DIESEL, Integer tOTAL_TOTALPASSNGER, Integer tOTAL_RFIDPASS,
			Double tOTAL_COLLECTION, Double tOTAL_EXPENSE, Double tOTAL_OVERTIME, Double tOTAL_BALANCE,
			Integer tOTAL_BUS, String tRIP_CLOSE_STATUS) {
		super();
		TRIPROUTEID = tRIPROUTEID;
		VEHICLE_GROUP = vEHICLE_GROUP;
		TRIP_ROUTE_NAME = tRIP_ROUTE_NAME;
		TRIP_OPEN_DATE = tRIP_OPEN_DATE;
		TOTAL_USAGE_KM = tOTAL_USAGE_KM;
		TOTAL_DIESEL = tOTAL_DIESEL;
		TOTAL_TOTALPASSNGER = tOTAL_TOTALPASSNGER;
		TOTAL_RFIDPASS = tOTAL_RFIDPASS;
		TOTAL_COLLECTION = tOTAL_COLLECTION;
		TOTAL_EXPENSE = tOTAL_EXPENSE;
		TOTAL_OVERTIME = tOTAL_OVERTIME;
		TOTAL_BALANCE = tOTAL_BALANCE;
		TOTAL_BUS = tOTAL_BUS;
		TRIP_CLOSE_STATUS = tRIP_CLOSE_STATUS;
	}

	/**
	 * @return the tRIPROUTEID
	 */
	public Long getTRIPROUTEID() {
		return TRIPROUTEID;
	}

	/**
	 * @param tRIPROUTEID
	 *            the tRIPROUTEID to set
	 */
	public void setTRIPROUTEID(Long tRIPROUTEID) {
		TRIPROUTEID = tRIPROUTEID;
	}

	/**
	 * @return the vEHICLE_GROUP
	 */
	public String getVEHICLE_GROUP() {
		return VEHICLE_GROUP;
	}

	/**
	 * @param vEHICLE_GROUP
	 *            the vEHICLE_GROUP to set
	 */
	public void setVEHICLE_GROUP(String vEHICLE_GROUP) {
		VEHICLE_GROUP = vEHICLE_GROUP;
	}

	/**
	 * @return the tRIP_ROUTE_NAME
	 */
	public String getTRIP_ROUTE_NAME() {
		return TRIP_ROUTE_NAME;
	}

	/**
	 * @param tRIP_ROUTE_NAME
	 *            the tRIP_ROUTE_NAME to set
	 */
	public void setTRIP_ROUTE_NAME(String tRIP_ROUTE_NAME) {
		TRIP_ROUTE_NAME = tRIP_ROUTE_NAME;
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
	 * @return the tOTAL_USAGE_KM
	 */
	public Integer getTOTAL_USAGE_KM() {
		return TOTAL_USAGE_KM;
	}

	/**
	 * @param tOTAL_USAGE_KM
	 *            the tOTAL_USAGE_KM to set
	 */
	public void setTOTAL_USAGE_KM(Integer tOTAL_USAGE_KM) {
		TOTAL_USAGE_KM = tOTAL_USAGE_KM;
	}

	/**
	 * @return the tOTAL_DIESEL
	 */
	public Double getTOTAL_DIESEL() {
		return TOTAL_DIESEL;
	}

	/**
	 * @param tOTAL_DIESEL
	 *            the tOTAL_DIESEL to set
	 */
	public void setTOTAL_DIESEL(Double tOTAL_DIESEL) {
		TOTAL_DIESEL =Utility.round(tOTAL_DIESEL, 2);
	}

	/**
	 * @return the tOTAL_TOTALPASSNGER
	 */
	public Integer getTOTAL_TOTALPASSNGER() {
		return TOTAL_TOTALPASSNGER;
	}

	/**
	 * @param tOTAL_TOTALPASSNGER
	 *            the tOTAL_TOTALPASSNGER to set
	 */
	public void setTOTAL_TOTALPASSNGER(Integer tOTAL_TOTALPASSNGER) {
		TOTAL_TOTALPASSNGER = tOTAL_TOTALPASSNGER;
	}

	/**
	 * @return the tOTAL_RFIDPASS
	 */
	public Integer getTOTAL_RFIDPASS() {
		return TOTAL_RFIDPASS;
	}

	public Double getTOTAL_DIESEL_AMOUNT() {
		return TOTAL_DIESEL_AMOUNT;
	}

	public void setTOTAL_DIESEL_AMOUNT(Double tOTAL_DIESEL_AMOUNT) {
		TOTAL_DIESEL_AMOUNT = Utility.round(tOTAL_DIESEL_AMOUNT, 2) ;
	}

	/**
	 * @param tOTAL_RFIDPASS
	 *            the tOTAL_RFIDPASS to set
	 */
	public void setTOTAL_RFIDPASS(Integer tOTAL_RFIDPASS) {
		TOTAL_RFIDPASS = tOTAL_RFIDPASS;
	}

	/**
	 * @return the tOTAL_COLLECTION
	 */
	public Double getTOTAL_COLLECTION() {
		return TOTAL_COLLECTION;
	}

	/**
	 * @param tOTAL_COLLECTION
	 *            the tOTAL_COLLECTION to set
	 */
	public void setTOTAL_COLLECTION(Double tOTAL_COLLECTION) {
		TOTAL_COLLECTION = Utility.round(tOTAL_COLLECTION, 2);
	}

	/**
	 * @return the tOTAL_EXPENSE
	 */
	public Double getTOTAL_EXPENSE() {
		return TOTAL_EXPENSE;
	}

	/**
	 * @param tOTAL_EXPENSE
	 *            the tOTAL_EXPENSE to set
	 */
	public void setTOTAL_EXPENSE(Double tOTAL_EXPENSE) {
		TOTAL_EXPENSE =Utility.round(tOTAL_EXPENSE, 2) ;
	}

	/**
	 * @return the tOTAL_OVERTIME
	 */
	public Double getTOTAL_OVERTIME() {
		return TOTAL_OVERTIME;
	}

	/**
	 * @param tOTAL_OVERTIME
	 *            the tOTAL_OVERTIME to set
	 */
	public void setTOTAL_OVERTIME(Double tOTAL_OVERTIME) {
		TOTAL_OVERTIME = Utility.round(tOTAL_OVERTIME, 2);
	}

	/**
	 * @return the tOTAL_BALANCE
	 */
	public Double getTOTAL_BALANCE() {
		return TOTAL_BALANCE;
	}

	/**
	 * @param tOTAL_BALANCE
	 *            the tOTAL_BALANCE to set
	 */
	public void setTOTAL_BALANCE(Double tOTAL_BALANCE) {
		TOTAL_BALANCE =Utility.round(tOTAL_BALANCE, 2);
	}

	/**
	 * @return the tOTAL_BUS
	 */
	public Integer getTOTAL_BUS() {
		return TOTAL_BUS;
	}

	/**
	 * @param tOTAL_BUS
	 *            the tOTAL_BUS to set
	 */
	public void setTOTAL_BUS(Integer tOTAL_BUS) {
		TOTAL_BUS = tOTAL_BUS;
	}

	/**
	 * @return the tRIP_CLOSE_STATUS
	 */
	public String getTRIP_CLOSE_STATUS() {
		return TRIP_CLOSE_STATUS;
	}

	/**
	 * @param tRIP_CLOSE_STATUS
	 *            the tRIP_CLOSE_STATUS to set
	 */
	public void setTRIP_CLOSE_STATUS(String tRIP_CLOSE_STATUS) {
		TRIP_CLOSE_STATUS = tRIP_CLOSE_STATUS;
	}

	public String getTRIP_DRIVER_NAME() {
		return TRIP_DRIVER_NAME;
	}

	public void setTRIP_DRIVER_NAME(String tRIP_DRIVER_NAME) {
		TRIP_DRIVER_NAME = tRIP_DRIVER_NAME;
	}

	public String getTRIP_CONDUCTOR_NAME() {
		return TRIP_CONDUCTOR_NAME;
	}

	public void setTRIP_CONDUCTOR_NAME(String tRIP_CONDUCTOR_NAME) {
		TRIP_CONDUCTOR_NAME = tRIP_CONDUCTOR_NAME;
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


	public Double getTOTAL_DIESELKML() {
		return TOTAL_DIESELKML;
	}

	public void setTOTAL_DIESELKML(Double tOTAL_DIESELKML) {
		TOTAL_DIESELKML = Utility.round(tOTAL_DIESELKML, 2);
	}

	public Double getTOTAL_EPK() {
		return TOTAL_EPK;
	}

	public void setTOTAL_EPK(Double tOTAL_EPK) {
		TOTAL_EPK = Utility.round(tOTAL_EPK, 2);
	}

	
	
	/**
	 * @return the tOTAL_PASS_PASSNGER
	 */
	public Integer getTOTAL_PASS_PASSNGER() {
		return TOTAL_PASS_PASSNGER;
	}

	/**
	 * @param tOTAL_PASS_PASSNGER the tOTAL_PASS_PASSNGER to set
	 */
	public void setTOTAL_PASS_PASSNGER(Integer tOTAL_PASS_PASSNGER) {
		TOTAL_PASS_PASSNGER = tOTAL_PASS_PASSNGER;
	}

	/**
	 * @return the tOTAL_RFID_AMOUNT
	 */
	public Double getTOTAL_RFID_AMOUNT() {
		return TOTAL_RFID_AMOUNT;
	}

	/**
	 * @param tOTAL_RFID_AMOUNT the tOTAL_RFID_AMOUNT to set
	 */
	public void setTOTAL_RFID_AMOUNT(Double tOTAL_RFID_AMOUNT) {
		TOTAL_RFID_AMOUNT = Utility.round(tOTAL_RFID_AMOUNT, 2);
	}

	/**
	 * @return the tOTAL_NET_COLLECTION
	 */
	public Double getTOTAL_NET_COLLECTION() {
		return TOTAL_NET_COLLECTION;
	}

	/**
	 * @param tOTAL_NET_COLLECTION the tOTAL_NET_COLLECTION to set
	 */
	public void setTOTAL_NET_COLLECTION(Double tOTAL_NET_COLLECTION) {
		TOTAL_NET_COLLECTION = Utility.round(tOTAL_NET_COLLECTION, 2) ;
	}

	/**
	 * @return the tOTAL_WT
	 */
	public Double getTOTAL_WT() {
		return TOTAL_WT;
	}

	/**
	 * @param tOTAL_WT the tOTAL_WT to set
	 */
	public void setTOTAL_WT(Double tOTAL_WT) {
		TOTAL_WT = Utility.round(tOTAL_WT, 2);
	}

	/**
	 * @return the tRIP_STATUS_ID
	 */
	public short getTRIP_STATUS_ID() {
		return TRIP_STATUS_ID;
	}

	/**
	 * @param tRIP_STATUS_ID the tRIP_STATUS_ID to set
	 */
	public void setTRIP_STATUS_ID(short tRIP_STATUS_ID) {
		TRIP_STATUS_ID = tRIP_STATUS_ID;
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

	/**
	 * @return the cOMPANY_ID
	 */
	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	/**
	 * @param cOMPANY_ID the cOMPANY_ID to set
	 */
	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/**
	 * @return the vEHICLE_GROUP_ID
	 */
	public long getVEHICLE_GROUP_ID() {
		return VEHICLE_GROUP_ID;
	}

	/**
	 * @param vEHICLE_GROUP_ID the vEHICLE_GROUP_ID to set
	 */
	public void setVEHICLE_GROUP_ID(long vEHICLE_GROUP_ID) {
		VEHICLE_GROUP_ID = vEHICLE_GROUP_ID;
	}

	/**
	 * @return the tRIP_OPEN_DATE_ON
	 */
	public Date getTRIP_OPEN_DATE_ON() {
		return TRIP_OPEN_DATE_ON;
	}

	/**
	 * @param tRIP_OPEN_DATE_ON the tRIP_OPEN_DATE_ON to set
	 */
	public void setTRIP_OPEN_DATE_ON(Date tRIP_OPEN_DATE_ON) {
		TRIP_OPEN_DATE_ON = tRIP_OPEN_DATE_ON;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((TOTAL_COLLECTION == null) ? 0 : TOTAL_COLLECTION.hashCode());
		result = prime * result + ((TOTAL_EXPENSE == null) ? 0 : TOTAL_EXPENSE.hashCode());
		result = prime * result + ((TRIPROUTEID == null) ? 0 : TRIPROUTEID.hashCode());
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
		TripDailyRouteSheetDto other = (TripDailyRouteSheetDto) obj;
		if (TOTAL_COLLECTION == null) {
			if (other.TOTAL_COLLECTION != null)
				return false;
		} else if (!TOTAL_COLLECTION.equals(other.TOTAL_COLLECTION))
			return false;
		if (TOTAL_EXPENSE == null) {
			if (other.TOTAL_EXPENSE != null)
				return false;
		} else if (!TOTAL_EXPENSE.equals(other.TOTAL_EXPENSE))
			return false;
		if (TRIPROUTEID == null) {
			if (other.TRIPROUTEID != null)
				return false;
		} else if (!TRIPROUTEID.equals(other.TRIPROUTEID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripDailyRouteSheetDto [TRIPROUTEID=");
		builder.append(TRIPROUTEID);
		builder.append(", VEHICLE_GROUP=");
		builder.append(VEHICLE_GROUP);
		builder.append(", TRIP_DRIVER_NAME=");
		builder.append(TRIP_DRIVER_NAME);
		builder.append(", TRIP_CONDUCTOR_NAME=");
		builder.append(TRIP_CONDUCTOR_NAME);
		builder.append(", TRIP_ROUTE_NAME=");
		builder.append(TRIP_ROUTE_NAME);
		builder.append(", TRIP_OPEN_DATE=");
		builder.append(TRIP_OPEN_DATE);
		builder.append(", TRIP_OPEN_KM=");
		builder.append(TRIP_OPEN_KM);
		builder.append(", TRIP_CLOSE_KM=");
		builder.append(TRIP_CLOSE_KM);
		builder.append(", TOTAL_USAGE_KM=");
		builder.append(TOTAL_USAGE_KM);
		builder.append(", TOTAL_DIESEL=");
		builder.append(TOTAL_DIESEL);
		builder.append(", TOTAL_TOTALPASSNGER=");
		builder.append(TOTAL_TOTALPASSNGER);
		builder.append(", TOTAL_RFIDPASS=");
		builder.append(TOTAL_RFIDPASS);
		builder.append(", TOTAL_COLLECTION=");
		builder.append(TOTAL_COLLECTION);
		builder.append(", TOTAL_EXPENSE=");
		builder.append(TOTAL_EXPENSE);
		builder.append(", TOTAL_OVERTIME=");
		builder.append(TOTAL_OVERTIME);
		builder.append(", TOTAL_BALANCE=");
		builder.append(TOTAL_BALANCE);
		builder.append(", TOTAL_BUS=");
		builder.append(TOTAL_BUS);
		builder.append(", TRIP_CLOSE_STATUS=");
		builder.append(TRIP_CLOSE_STATUS);
		builder.append(", DIESEL_Amount=");
		builder.append(DIESEL_Amount); 
		builder.append(", TOTAL_DIESEL_AMOUNT=");
		builder.append(TOTAL_DIESEL_AMOUNT);
		builder.append(", CHALO_KM=");
		builder.append(CHALO_KM);
		builder.append(", CHALO_AMOUNT=");
		builder.append(CHALO_AMOUNT);
		builder.append("]");
		return builder.toString();
	}

	public double getDIESEL_Amount() {
		return DIESEL_Amount;
	}

	public void setDIESEL_Amount(double dIESEL_Amount) {
		DIESEL_Amount = Utility.round(dIESEL_Amount, 2);
	}

}
