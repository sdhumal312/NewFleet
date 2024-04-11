package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TripDailyRouteSheet")
public class TripDailyRouteSheet implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value for the TRIPGROUPID field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRIPROUTEID")
	private Long TRIPROUTEID;

	
	/** The value for the TRIP_ROUTE_ID DATE field */
	@Column(name = "TRIP_ROUTE_ID")
	private Integer TRIP_ROUTE_ID;
	

	/** The value for the TRIP_OPEN DATE field */
	@Column(name = "TRIP_OPEN_DATE")
	private Date TRIP_OPEN_DATE;

	/** The value for the TOTAL_USAGE_KM field */
	@Column(name = "TOTAL_USAGE_KM")
	private Integer TOTAL_USAGE_KM;

	/** The value for the TOTAL_DIESEL DATE field */
	@Column(name = "TOTAL_DIESEL")
	private Double TOTAL_DIESEL;

	/** The value for the TOTAL_TOTALPASSNGER TOTAL field */
	@Column(name = "TOTAL_TOTALPASSNGER")
	private Integer TOTAL_TOTALPASSNGER;
	
	/** The value for the TRIP_PASS  field */
	@Column(name = "TOTAL_PASS_PASSNGER")
	private Integer TOTAL_PASS_PASSNGER;

	/** The value for the TOTAL_RFIDPASS AMOUNT field */
	@Column(name = "TOTAL_RFIDPASS")
	private Integer TOTAL_RFIDPASS;
	
	/** The value for the TRIP_RFID_AMOUNT field */
	@Column(name = "TOTAL_RFID_AMOUNT")
	private Double TOTAL_RFID_AMOUNT;

	/** The value for the TRIP_TOTAL_COLLECTION DATE field */
	@Column(name = "TOTAL_COLLECTION")
	private Double TOTAL_COLLECTION;
	
	/** The value for the TRIP_TOTAL_NET_COLLECTION DATE field */
	@Column(name = "TOTAL_NET_COLLECTION")
	private Double TOTAL_NET_COLLECTION;
	
	/** The value for the TRIP_TOTAL_COLLECTION DATE field */
	@Column(name = "TOTAL_WT")
	private Double TOTAL_WT;

	/** The value for the TRIP_TOTAL_EXPENSE DATE field */
	@Column(name = "TOTAL_EXPENSE")
	private Double TOTAL_EXPENSE;

	/** The value for the TRIP_TOTAL_OVERTIME AMOUNT field */
	@Column(name = "TOTAL_OVERTIME")
	private Double TOTAL_OVERTIME;

	/** The value for the TRIP_TOTAL_BALANCE DATE field */
	@Column(name = "TOTAL_BALANCE")
	private Double TOTAL_BALANCE;

	/** The value for the TOTAL_BUS field */
	@Column(name = "TOTAL_BUS")
	private Integer TOTAL_BUS;

	@Column(name = "TRIP_STATUS_ID", nullable = false)
	private short TRIP_STATUS_ID;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;
	
	@Column(name = "VEHICLE_GROUP_ID", nullable = false)
	private long VEHICLE_GROUP_ID;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public TripDailyRouteSheet() {
		super();
	}

	public TripDailyRouteSheet(Long tRIPROUTEID,  Date tRIP_OPEN_DATE,
			Integer tOTAL_USAGE_KM, Double tOTAL_DIESEL, Integer tOTAL_TOTALPASSNGER, Integer tOTAL_RFIDPASS,
			Double tOTAL_COLLECTION, Double tOTAL_EXPENSE, Double tOTAL_OVERTIME, Double tOTAL_BALANCE,
			Integer tOTAL_BUS) {
		super();
		TRIPROUTEID = tRIPROUTEID;
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
		TOTAL_RFID_AMOUNT = tOTAL_RFID_AMOUNT;
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
	 * @return the tRIP_OPEN_DATE
	 */
	public Date getTRIP_OPEN_DATE() {
		return TRIP_OPEN_DATE;
	}

	/**
	 * @param tRIP_OPEN_DATE
	 *            the tRIP_OPEN_DATE to set
	 */
	public void setTRIP_OPEN_DATE(Date tRIP_OPEN_DATE) {
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
		TOTAL_DIESEL = tOTAL_DIESEL;
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
		TOTAL_COLLECTION = tOTAL_COLLECTION;
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
		TOTAL_EXPENSE = tOTAL_EXPENSE;
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
		TOTAL_OVERTIME = tOTAL_OVERTIME;
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
		TOTAL_BALANCE = tOTAL_BALANCE;
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

	

	public Integer getTRIP_ROUTE_ID() {
		return TRIP_ROUTE_ID;
	}

	public void setTRIP_ROUTE_ID(Integer tRIP_ROUTE_ID) {
		TRIP_ROUTE_ID = tRIP_ROUTE_ID;
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
		TOTAL_NET_COLLECTION = tOTAL_NET_COLLECTION;
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
		TOTAL_WT = tOTAL_WT;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	public long getVEHICLE_GROUP_ID() {
		return VEHICLE_GROUP_ID;
	}

	public void setVEHICLE_GROUP_ID(long vEHICLE_GROUP_ID) {
		VEHICLE_GROUP_ID = vEHICLE_GROUP_ID;
	}

	public short getTRIP_STATUS_ID() {
		return TRIP_STATUS_ID;
	}

	public void setTRIP_STATUS_ID(short tRIP_STATUS_ID) {
		TRIP_STATUS_ID = tRIP_STATUS_ID;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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
		TripDailyRouteSheet other = (TripDailyRouteSheet) obj;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripDailyRouteSheet [TRIPROUTEID=");
		builder.append(TRIPROUTEID);
		builder.append(", TRIP_ROUTE_ID=");
		builder.append(TRIP_ROUTE_ID);
		builder.append(", TRIP_OPEN_DATE=");
		builder.append(TRIP_OPEN_DATE);
		builder.append(", TOTAL_USAGE_KM=");
		builder.append(TOTAL_USAGE_KM);
		builder.append(", TOTAL_DIESEL=");
		builder.append(TOTAL_DIESEL);
		builder.append(", TOTAL_TOTALPASSNGER=");
		builder.append(TOTAL_TOTALPASSNGER);
		builder.append(", TOTAL_PASS_PASSNGER=");
		builder.append(TOTAL_PASS_PASSNGER);
		builder.append(", TOTAL_RFIDPASS=");
		builder.append(TOTAL_RFIDPASS);
		builder.append(", TOTAL_RFID_AMOUNT=");
		builder.append(TOTAL_RFID_AMOUNT);
		builder.append(", TOTAL_COLLECTION=");
		builder.append(TOTAL_COLLECTION);
		builder.append(", TOTAL_NET_COLLECTION=");
		builder.append(TOTAL_NET_COLLECTION);
		builder.append(", TOTAL_WT=");
		builder.append(TOTAL_WT);
		builder.append(", TOTAL_EXPENSE=");
		builder.append(TOTAL_EXPENSE);
		builder.append(", TOTAL_OVERTIME=");
		builder.append(TOTAL_OVERTIME);
		builder.append(", TOTAL_BALANCE=");
		builder.append(TOTAL_BALANCE);
		builder.append(", TOTAL_BUS=");
		builder.append(TOTAL_BUS);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", VEHICLE_GROUP_ID=");
		builder.append(VEHICLE_GROUP_ID);
		builder.append("]");
		return builder.toString();
	}


}
