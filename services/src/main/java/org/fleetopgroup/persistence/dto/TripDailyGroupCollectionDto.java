package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TripDailyGroupCollectionDto {

	/** The value for the TRIPGROUPID field */
	private Long TRIPGROUPID;
	
	private Long TRIPGROUPNUMBER;

	/** The value for the VEHICLE GROUP COMPANY NAME field */
	private String VEHICLE_GROUP;

	private long VEHICLE_GROUP_ID;
	/** The value for the TRIP_OPEN DATE field */
	private String TRIP_OPEN_DATE;
	
	private Date TRIP_OPEN_DATE_ON;

	/** The value for the TOTAL_USAGE_KM field */
	private Integer TOTAL_USAGE_KM;

	/** The value for the TOTAL_DIESEL DATE field */
	private Double TOTAL_DIESEL;

	/** The value for the TOTAL_DIESEL_MILAGE field */
	private Double TOTAL_DIESEL_MILAGE;

	/** The value for the TOTAL_TOTALPASSNGER TOTAL field */
	private Integer TOTAL_TOTALPASSNGER;

	/** The value for the TRIP_PASS field */
	private Integer TOTAL_PASS_PASSNGER;

	/** The value for the TOTAL_RFIDPASS AMOUNT field */
	private Integer TOTAL_RFIDPASS;

	/** The value for the TRIP_RFID_AMOUNT field */
	private Double TOTAL_RFID_AMOUNT;

	/** The value for the TRIP_TOTAL_COLLECTION DATE field */
	private Double TOTAL_COLLECTION;

	/** The value for the TRIP_TOTAL_COLLECTION DATE field */
	private Double TOTAL_NET_COLLECTION;

	/** The value for the TRIP_WT DATE field */
	private Double TOTAL_WT;

	/** The value for the TRIP_TOTAL_COLLECTION DATE field */
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

	/** The value for the REMARKS DATE field */
	private String TRIP_CLOSE_REMARKS;

	/** The value for the CREATEDBY DATE field */
	private String CREATEDBY;

	@JsonIgnore
	/** The value for the STATUS DATE field */
	private boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	private String CREATED;
	
	private Date CREATED_ON;

	private double Diesel_Amount;
	
	private Integer TRIP_ROUTE_ID;
	
	private double Total_Diesel_Amount;
	
	private Integer CHALO_KM;
	
	private double CHALO_AMOUNT;
	
	private Integer totalCHALO_KM;	
	
	private double totalCHALO_AMOUNT;	
	
	
	public TripDailyGroupCollectionDto() {
		super();
	}

	public TripDailyGroupCollectionDto(Long tRIPGROUPID, String vEHICLE_GROUP, String tRIP_OPEN_DATE,
			Integer tOTAL_USAGE_KM, Double tOTAL_DIESEL, Double tOTAL_DIESEL_MILAGE, Integer tOTAL_TOTALPASSNGER,
			Integer tOTAL_RFIDPASS, Double tOTAL_COLLECTION, Double tOTAL_EXPENSE, Double tOTAL_OVERTIME,
			Double tOTAL_BALANCE, Integer tOTAL_BUS, String tRIP_CLOSE_STATUS, String tRIP_CLOSE_REMARKS,
			String cREATEDBY, String cREATED,Integer CHALO_KM ,double CHALO_AMOUNT, Integer totalCHALO_KM ,double totalCHALO_AMOUNT) {
		super();
		TRIPGROUPID = tRIPGROUPID;
		VEHICLE_GROUP = vEHICLE_GROUP;
		TRIP_OPEN_DATE = tRIP_OPEN_DATE;
		TOTAL_USAGE_KM = tOTAL_USAGE_KM;
		TOTAL_DIESEL = tOTAL_DIESEL;
		TOTAL_DIESEL_MILAGE = tOTAL_DIESEL_MILAGE;
		TOTAL_TOTALPASSNGER = tOTAL_TOTALPASSNGER;
		TOTAL_RFIDPASS = tOTAL_RFIDPASS;
		TOTAL_COLLECTION = tOTAL_COLLECTION;
		TOTAL_EXPENSE = tOTAL_EXPENSE;
		TOTAL_OVERTIME = tOTAL_OVERTIME;
		TOTAL_BALANCE = tOTAL_BALANCE;
		TOTAL_BUS = tOTAL_BUS;
		TRIP_CLOSE_STATUS = tRIP_CLOSE_STATUS;
		TRIP_CLOSE_REMARKS = tRIP_CLOSE_REMARKS;
		CREATEDBY = cREATEDBY;
		CREATED = cREATED;
		CHALO_KM = CHALO_KM;
		CHALO_AMOUNT = CHALO_AMOUNT;
		totalCHALO_KM=totalCHALO_KM;
		totalCHALO_AMOUNT=totalCHALO_AMOUNT;
		
	}
	
	
	public Integer getTotalCHALO_KM() {
		return totalCHALO_KM;
	}

	public void setTotalCHALO_KM(Integer totalCHALO_KM) {
		this.totalCHALO_KM = totalCHALO_KM;
	}

	public double getTotalCHALO_AMOUNT() {
		return totalCHALO_AMOUNT;
	}

	public void setTotalCHALO_AMOUNT(double totalCHALO_AMOUNT) {
		this.totalCHALO_AMOUNT =Utility.round(totalCHALO_AMOUNT, 2);
	}


	/**
	 * @return the tOTAL_PASS_PASSNGER
	 */
	public Integer getTOTAL_PASS_PASSNGER() {
		return TOTAL_PASS_PASSNGER;
	}

	/**
	 * @param tOTAL_PASS_PASSNGER
	 *            the tOTAL_PASS_PASSNGER to set
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
	 * @param tOTAL_RFID_AMOUNT
	 *            the tOTAL_RFID_AMOUNT to set
	 */
	public void setTOTAL_RFID_AMOUNT(Double tOTAL_RFID_AMOUNT) {
		TOTAL_RFID_AMOUNT =Utility.round(tOTAL_RFID_AMOUNT, 2);
	}

	/**
	 * @return the tRIPGROUPNUMBER
	 */
	public Long getTRIPGROUPNUMBER() {
		return TRIPGROUPNUMBER;
	}

	/**
	 * @param tRIPGROUPNUMBER the tRIPGROUPNUMBER to set
	 */
	public void setTRIPGROUPNUMBER(Long tRIPGROUPNUMBER) {
		TRIPGROUPNUMBER = tRIPGROUPNUMBER;
	}

	/**
	 * @return the tOTAL_NET_COLLECTION
	 */
	public Double getTOTAL_NET_COLLECTION() {
		return TOTAL_NET_COLLECTION;
	}

	/**
	 * @param tOTAL_NET_COLLECTION
	 *            the tOTAL_NET_COLLECTION to set
	 */
	public void setTOTAL_NET_COLLECTION(Double tOTAL_NET_COLLECTION) {
		TOTAL_NET_COLLECTION = Utility.round(tOTAL_NET_COLLECTION, 2);
	}

	/**
	 * @return the tOTAL_WT
	 */
	public Double getTOTAL_WT() {
		return TOTAL_WT;
	}

	/**
	 * @param tOTAL_WT
	 *            the tOTAL_WT to set
	 */
	public void setTOTAL_WT(Double tOTAL_WT) {
		TOTAL_WT = Utility.round(tOTAL_WT, 2);
	}

	/**
	 * @return the tRIPGROUPID
	 */
	public Long getTRIPGROUPID() {
		return TRIPGROUPID;
	}

	/**
	 * @param tRIPGROUPID
	 *            the tRIPGROUPID to set
	 */
	public void setTRIPGROUPID(Long tRIPGROUPID) {
		TRIPGROUPID = tRIPGROUPID;
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
		TOTAL_DIESEL =Utility.round(tOTAL_DIESEL,2);
	}

	/**
	 * @return the tOTAL_DIESEL_MILAGE
	 */
	public Double getTOTAL_DIESEL_MILAGE() {
		return TOTAL_DIESEL_MILAGE;
	}

	/**
	 * @param tOTAL_DIESEL_MILAGE
	 *            the tOTAL_DIESEL_MILAGE to set
	 */
	public void setTOTAL_DIESEL_MILAGE(Double tOTAL_DIESEL_MILAGE) {
		TOTAL_DIESEL_MILAGE =Utility.round(tOTAL_DIESEL_MILAGE, 2);
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
		TOTAL_COLLECTION =Utility.round(tOTAL_COLLECTION,2);
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
		TOTAL_EXPENSE = Utility.round(tOTAL_EXPENSE, 2);
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
		TOTAL_OVERTIME =Utility.round(tOTAL_OVERTIME, 2);
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
		TOTAL_BALANCE = Utility.round(tOTAL_BALANCE, 2);
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

	/**
	 * @return the tRIP_CLOSE_REMARKS
	 */
	public String getTRIP_CLOSE_REMARKS() {
		return TRIP_CLOSE_REMARKS;
	}

	/**
	 * @param tRIP_CLOSE_REMARKS
	 *            the tRIP_CLOSE_REMARKS to set
	 */
	public void setTRIP_CLOSE_REMARKS(String tRIP_CLOSE_REMARKS) {
		TRIP_CLOSE_REMARKS = tRIP_CLOSE_REMARKS;
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
	 * @return the cREATED_ON
	 */
	public Date getCREATED_ON() {
		return CREATED_ON;
	}

	/**
	 * @param cREATED_ON the cREATED_ON to set
	 */
	public void setCREATED_ON(Date cREATED_ON) {
		CREATED_ON = cREATED_ON;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean ismarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete
	 *            the markForDelete to set
	 */
	public void setmarkForDelete(boolean mARKFORDELETE) {
		markForDelete = mARKFORDELETE;
	}

	/**
	 * @return the cREATED
	 */
	public String getCREATED() {
		return CREATED;
	}

	/**
	 * @param cREATED
	 *            the cREATED to set
	 */
	public void setCREATED(String cREATED) {
		CREATED = cREATED;
	}

	public Double getTOTAL_EPK() {
		return TOTAL_EPK;
	}

	public void setTOTAL_EPK(Double tOTAL_EPK) {
		TOTAL_EPK = Utility.round(tOTAL_EPK, 2);
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
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public double getDiesel_Amount() {
		return Diesel_Amount;
	}

	public void setDiesel_Amount(double diesel_Amount) {
		Diesel_Amount = Utility.round(diesel_Amount, 2);
	}

	public Integer getTRIP_ROUTE_ID() {
		return TRIP_ROUTE_ID;
	}

	public void setTRIP_ROUTE_ID(Integer tRIP_ROUTE_ID) {
		TRIP_ROUTE_ID = tRIP_ROUTE_ID;
	}

	public double getTotal_Diesel_Amount() {
		return Total_Diesel_Amount;
	}

	public void setTotal_Diesel_Amount(double total_Diesel_Amount) {
		Total_Diesel_Amount =Utility.round(total_Diesel_Amount, 2);
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
		result = prime * result + ((TOTAL_DIESEL == null) ? 0 : TOTAL_DIESEL.hashCode());
		result = prime * result + ((TOTAL_DIESEL_MILAGE == null) ? 0 : TOTAL_DIESEL_MILAGE.hashCode());
		result = prime * result + ((TOTAL_EXPENSE == null) ? 0 : TOTAL_EXPENSE.hashCode());
		result = prime * result + ((TOTAL_OVERTIME == null) ? 0 : TOTAL_OVERTIME.hashCode());
		result = prime * result + ((TOTAL_RFIDPASS == null) ? 0 : TOTAL_RFIDPASS.hashCode());
		result = prime * result + ((TOTAL_TOTALPASSNGER == null) ? 0 : TOTAL_TOTALPASSNGER.hashCode());
		result = prime * result + ((TOTAL_USAGE_KM == null) ? 0 : TOTAL_USAGE_KM.hashCode());
		result = prime * result + ((TRIPGROUPID == null) ? 0 : TRIPGROUPID.hashCode());
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
		TripDailyGroupCollectionDto other = (TripDailyGroupCollectionDto) obj;
		if (TOTAL_COLLECTION == null) {
			if (other.TOTAL_COLLECTION != null)
				return false;
		} else if (!TOTAL_COLLECTION.equals(other.TOTAL_COLLECTION))
			return false;
		if (TOTAL_DIESEL == null) {
			if (other.TOTAL_DIESEL != null)
				return false;
		} else if (!TOTAL_DIESEL.equals(other.TOTAL_DIESEL))
			return false;
		if (TOTAL_DIESEL_MILAGE == null) {
			if (other.TOTAL_DIESEL_MILAGE != null)
				return false;
		} else if (!TOTAL_DIESEL_MILAGE.equals(other.TOTAL_DIESEL_MILAGE))
			return false;
		if (TOTAL_EXPENSE == null) {
			if (other.TOTAL_EXPENSE != null)
				return false;
		} else if (!TOTAL_EXPENSE.equals(other.TOTAL_EXPENSE))
			return false;
		if (TOTAL_OVERTIME == null) {
			if (other.TOTAL_OVERTIME != null)
				return false;
		} else if (!TOTAL_OVERTIME.equals(other.TOTAL_OVERTIME))
			return false;
		if (TOTAL_RFIDPASS == null) {
			if (other.TOTAL_RFIDPASS != null)
				return false;
		} else if (!TOTAL_RFIDPASS.equals(other.TOTAL_RFIDPASS))
			return false;
		if (TOTAL_TOTALPASSNGER == null) {
			if (other.TOTAL_TOTALPASSNGER != null)
				return false;
		} else if (!TOTAL_TOTALPASSNGER.equals(other.TOTAL_TOTALPASSNGER))
			return false;
		if (TOTAL_USAGE_KM == null) {
			if (other.TOTAL_USAGE_KM != null)
				return false;
		} else if (!TOTAL_USAGE_KM.equals(other.TOTAL_USAGE_KM))
			return false;
		if (TRIPGROUPID == null) {
			if (other.TRIPGROUPID != null)
				return false;
		} else if (!TRIPGROUPID.equals(other.TRIPGROUPID))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripDailyGroupCollectionDto [TRIPGROUPID=");
		builder.append(TRIPGROUPID);
		builder.append(", VEHICLE_GROUP=");
		builder.append(VEHICLE_GROUP);
		builder.append(", TRIP_OPEN_DATE=");
		builder.append(TRIP_OPEN_DATE);
		builder.append(", TOTAL_USAGE_KM=");
		builder.append(TOTAL_USAGE_KM);
		builder.append(", TOTAL_DIESEL=");
		builder.append(TOTAL_DIESEL);
		builder.append(", TOTAL_DIESEL_MILAGE=");
		builder.append(TOTAL_DIESEL_MILAGE);
		builder.append(", TOTAL_TOTALPASSNGER=");
		builder.append(TOTAL_TOTALPASSNGER);
		builder.append(", TOTAL_RFIDPASS=");
		builder.append(TOTAL_RFIDPASS);
		builder.append(", TOTAL_COLLECTION=");
		builder.append(TOTAL_COLLECTION);
		builder.append(", TOTAL_EPK=");
		builder.append(TOTAL_EPK);
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
		builder.append(", TRIP_CLOSE_REMARKS=");
		builder.append(TRIP_CLOSE_REMARKS);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", CHALO_KM=");
		builder.append(CHALO_KM);
		builder.append(", CHALO_AMOUNT=");
		builder.append(CHALO_AMOUNT);		
		builder.append(", totalCHALO_KM=");
		builder.append(totalCHALO_KM);
		builder.append(", totalCHALO_AMOUNT=");
		builder.append(totalCHALO_AMOUNT);		
		builder.append("]");
		return builder.toString();
	}

	public Integer getCHALO_KM() {
		return CHALO_KM;
	}

	public void setCHALO_KM(Integer cHALO_KM) {
		CHALO_KM = cHALO_KM;
	}

	public double getCHALO_AMOUNT() {
		return CHALO_AMOUNT;
	}

	public void setCHALO_AMOUNT(double cHALO_AMOUNT) {
		CHALO_AMOUNT = Utility.round(cHALO_AMOUNT, 2);
	}

	

}
