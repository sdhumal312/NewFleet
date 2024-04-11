package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class TripDailyAllGroupDayDto {

	/** The value for the TRIPGROUPID field */
	private Long TRIPALLGROUPID;
	
	private Long TRIPALLGROUPNUMBER;

	/** The value for the TRIP_OPEN DATE field */
	private String TRIP_OPEN_DATE;

	/** The value for the TRIP_TOTAL_COLLECTION DATE field */
	private Double BUSCOLLECTION;

	/** The value for the TOTAL_RFIDRCG field */
	private Double RFIDRCG;

	/** The value for the TOTAL_RFIDCARD field */
	private Double RFIDCARD;

	/** The value for the TOTAL_RFIDUSAGE field */
	private Double RFIDUSAGE;

	/** The value for the TOTAL_RFIDUSAGE field */
	private Double BOOKING;

	/** The value for the TOTAL_DIESEL DATE field */
	private Double TOTAL_DIESEL;

	/** The value for the TOTAL_USAGE_KM field */
	private Integer TOTAL_USAGE_KM;

	/** The value for the TOTAL_DIESEL_MILAGE field */
	private Double TOTAL_DIESEL_MILAGE;

	/** The value for the TOTAL_TOTALPASSNGER TOTAL field */
	private Integer TOTAL_TOTALPASSNGER;

	/** The value for the TOTAL_DIESEL DATE field */
	private Double TOTAL_DIESELEXPENSE;

	/** The value for the TRIP_TOTAL_OVERTIME AMOUNT field */
	private Double TOTAL_OVERTIME;

	/** The value for the ADVANCE field */
	private Integer ADVANCE;

	/** The value for the TRIP_TOTAL_COLLECTION DATE field */
	private Double COLLECTION_BALANCE;

	/** The value for the TRIP_TOTAL_EXPENSE DATE field */
	private Double EXPENSE_DAY;

	/** The value for the TRIP_TOTAL_BALANCE DATE field */
	private Double TOTAL_BALANCE;

	/** The value for the TOTAL_WT field */
	private String TOTAL_WT;

	/** The value for the REMARKS DATE field */
	private String TRIP_REMARKS;

	/** The value for the TRIP_CLOSE_STATUS DATE field */
	private String TRIP_CLOSE_STATUS;
	
	private short TRIP_STATUS_ID;

	/** The value for the CREATEDBY DATE field */
	private String CREATEDBY;

	/** The value for the LASTMODIFIEDBY DATE field */
	private String LASTMODIFIEDBY;

	/** The value for the STATUS DATE field */
	private boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	private String CREATED;

	/** The value for the ISSUES_Due DATE field */
	private String LASTUPDATED;
	
	private Long CREATED_BY_ID;
	
	private Long LASTMODIFIED_BY_ID;
	
	private Date TRIP_OPEN_DATE_ON;
	
	private Date CREATED_ON;
	
	private Date LASTUPDATED_ON;
	
	private Integer TOTAL_PASS_PASSNGER;
	
	private Double TOTAL_RFID_AMOUNT;
	
	private Integer TOTAL_RFIDPASS;

	public TripDailyAllGroupDayDto() {
		super();
	}

	public TripDailyAllGroupDayDto(Long tRIPALLGROUPID, String tRIP_OPEN_DATE, Double bUSCOLLECTION,
			Double rFIDRCG, Double rFIDCARD, Double rFIDUSAGE, Double bOOKING, Double tOTAL_DIESEL,
			Integer tOTAL_USAGE_KM, Double tOTAL_DIESEL_MILAGE, Integer tOTAL_TOTALPASSNGER, Double tOTAL_DIESELEXPENSE,
			Double tOTAL_OVERTIME, Integer aDVANCE, Double cOLLECTION_BALANCE, Double eXPENSE_DAY, Double tOTAL_BALANCE,
			String tOTAL_WT, String tRIP_REMARKS, String tRIP_CLOSE_STATUS) {
		super();
		TRIPALLGROUPID = tRIPALLGROUPID;
		TRIP_OPEN_DATE = tRIP_OPEN_DATE;
		BUSCOLLECTION = bUSCOLLECTION;
		RFIDRCG = rFIDRCG;
		RFIDCARD = rFIDCARD;
		RFIDUSAGE = rFIDUSAGE;
		BOOKING = bOOKING;
		TOTAL_DIESEL = tOTAL_DIESEL;
		TOTAL_USAGE_KM = tOTAL_USAGE_KM;
		TOTAL_DIESEL_MILAGE = tOTAL_DIESEL_MILAGE;
		TOTAL_TOTALPASSNGER = tOTAL_TOTALPASSNGER;
		TOTAL_DIESELEXPENSE = tOTAL_DIESELEXPENSE;
		TOTAL_OVERTIME = tOTAL_OVERTIME;
		ADVANCE = aDVANCE;
		COLLECTION_BALANCE = cOLLECTION_BALANCE;
		EXPENSE_DAY = eXPENSE_DAY;
		TOTAL_BALANCE = tOTAL_BALANCE;
		TOTAL_WT = tOTAL_WT;
		TRIP_REMARKS = tRIP_REMARKS;
		TRIP_CLOSE_STATUS = tRIP_CLOSE_STATUS;
	}

	/**
	 * @return the tRIPALLGROUPID
	 */
	public Long getTRIPALLGROUPID() {
		return TRIPALLGROUPID;
	}

	/**
	 * @param tRIPALLGROUPID
	 *            the tRIPALLGROUPID to set
	 */
	public void setTRIPALLGROUPID(Long tRIPALLGROUPID) {
		TRIPALLGROUPID = tRIPALLGROUPID;
	}

	public Long getTRIPALLGROUPNUMBER() {
		return TRIPALLGROUPNUMBER;
	}

	public void setTRIPALLGROUPNUMBER(Long tRIPALLGROUPNUMBER) {
		TRIPALLGROUPNUMBER = tRIPALLGROUPNUMBER;
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
	 * @return the bUSCOLLECTION
	 */
	public Double getBUSCOLLECTION() {
		return BUSCOLLECTION;
	}

	/**
	 * @param bUSCOLLECTION
	 *            the bUSCOLLECTION to set
	 */
	public void setBUSCOLLECTION(Double bUSCOLLECTION) {
		BUSCOLLECTION = Utility.round(bUSCOLLECTION, 2);
	}

	/**
	 * @return the rFIDRCG
	 */
	public Double getRFIDRCG() {
		return RFIDRCG;
	}

	/**
	 * @param rFIDRCG
	 *            the rFIDRCG to set
	 */
	public void setRFIDRCG(Double rFIDRCG) {
		RFIDRCG = Utility.round(rFIDRCG, 2);
	}

	/**
	 * @return the rFIDCARD
	 */
	public Double getRFIDCARD() {
		return RFIDCARD;
	}

	/**
	 * @param rFIDCARD
	 *            the rFIDCARD to set
	 */
	public void setRFIDCARD(Double rFIDCARD) {
		RFIDCARD = Utility.round(rFIDCARD, 2);
	}

	/**
	 * @return the rFIDUSAGE
	 */
	public Double getRFIDUSAGE() {
		return RFIDUSAGE;
	}

	/**
	 * @param rFIDUSAGE
	 *            the rFIDUSAGE to set
	 */
	public void setRFIDUSAGE(Double rFIDUSAGE) {
		RFIDUSAGE =Utility.round(rFIDUSAGE, 2);
	}

	/**
	 * @return the bOOKING
	 */
	public Double getBOOKING() {
		return BOOKING;
	}

	/**
	 * @param bOOKING
	 *            the bOOKING to set
	 */
	public void setBOOKING(Double bOOKING) {
		BOOKING =Utility.round(bOOKING, 2);
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
		TOTAL_DIESEL = Utility.round(tOTAL_DIESEL, 2);
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
	 * @return the tOTAL_DIESELEXPENSE
	 */
	public Double getTOTAL_DIESELEXPENSE() {
		return TOTAL_DIESELEXPENSE;
	}

	/**
	 * @param tOTAL_DIESELEXPENSE
	 *            the tOTAL_DIESELEXPENSE to set
	 */
	public void setTOTAL_DIESELEXPENSE(Double tOTAL_DIESELEXPENSE) {
		TOTAL_DIESELEXPENSE = Utility.round(tOTAL_DIESELEXPENSE, 2) ;
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
	 * @return the aDVANCE
	 */
	public Integer getADVANCE() {
		return ADVANCE;
	}

	/**
	 * @param aDVANCE
	 *            the aDVANCE to set
	 */
	public void setADVANCE(Integer aDVANCE) {
		ADVANCE = aDVANCE;
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
	 * @return the cOLLECTION_BALANCE
	 */
	public Double getCOLLECTION_BALANCE() {
		return COLLECTION_BALANCE;
	}

	/**
	 * @param cOLLECTION_BALANCE
	 *            the cOLLECTION_BALANCE to set
	 */
	public void setCOLLECTION_BALANCE(Double cOLLECTION_BALANCE) {
		COLLECTION_BALANCE = Utility.round(cOLLECTION_BALANCE,2);
	}

	/**
	 * @return the eXPENSE_DAY
	 */
	public Double getEXPENSE_DAY() {
		return EXPENSE_DAY;
	}

	/**
	 * @param eXPENSE_DAY
	 *            the eXPENSE_DAY to set
	 */
	public void setEXPENSE_DAY(Double eXPENSE_DAY) {
		EXPENSE_DAY =Utility.round(eXPENSE_DAY, 2);
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
	 * @return the tOTAL_WT
	 */
	public String getTOTAL_WT() {
		return TOTAL_WT;
	}

	/**
	 * @param tOTAL_WT
	 *            the tOTAL_WT to set
	 */
	public void setTOTAL_WT(String tOTAL_WT) {
		TOTAL_WT = tOTAL_WT;
	}

	/**
	 * @return the tRIP_REMARKS
	 */
	public String getTRIP_REMARKS() {
		return TRIP_REMARKS;
	}

	/**
	 * @param tRIP_REMARKS
	 *            the tRIP_REMARKS to set
	 */
	public void setTRIP_REMARKS(String tRIP_REMARKS) {
		TRIP_REMARKS = tRIP_REMARKS;
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
	 * @return the lASTMODIFIEDBY
	 */
	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	/**
	 * @param lASTMODIFIEDBY
	 *            the lASTMODIFIEDBY to set
	 */
	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean ismarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete the markForDelete to set
	 */
	public void setmarkForDelete(boolean mARKFORDELETE) {
		markForDelete = mARKFORDELETE;
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
	 * @return the lASTUPDATED_ON
	 */
	public Date getLASTUPDATED_ON() {
		return LASTUPDATED_ON;
	}

	/**
	 * @param lASTUPDATED_ON the lASTUPDATED_ON to set
	 */
	public void setLASTUPDATED_ON(Date lASTUPDATED_ON) {
		LASTUPDATED_ON = lASTUPDATED_ON;
	}

	/**
	 * @return the cREATED
	 */
	public String getCREATED() {
		return CREATED;
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

	/**
	 * @return the cREATED_BY_ID
	 */
	public Long getCREATED_BY_ID() {
		return CREATED_BY_ID;
	}

	/**
	 * @param cREATED_BY_ID the cREATED_BY_ID to set
	 */
	public void setCREATED_BY_ID(Long cREATED_BY_ID) {
		CREATED_BY_ID = cREATED_BY_ID;
	}

	/**
	 * @return the lASTMODIFIED_BY_ID
	 */
	public Long getLASTMODIFIED_BY_ID() {
		return LASTMODIFIED_BY_ID;
	}

	/**
	 * @param lASTMODIFIED_BY_ID the lASTMODIFIED_BY_ID to set
	 */
	public void setLASTMODIFIED_BY_ID(Long lASTMODIFIED_BY_ID) {
		LASTMODIFIED_BY_ID = lASTMODIFIED_BY_ID;
	}

	/**
	 * @param cREATED
	 *            the cREATED to set
	 */
	public void setCREATED(String cREATED) {
		CREATED = cREATED;
	}

	/**
	 * @return the lASTUPDATED
	 */
	public String getLASTUPDATED() {
		return LASTUPDATED;
	}

	/**
	 * @param lASTUPDATED
	 *            the lASTUPDATED to set
	 */
	public void setLASTUPDATED(String lASTUPDATED) {
		LASTUPDATED = lASTUPDATED;
	}

	/**
	 * @return the tOTAL_RFIDPASS
	 */
	public Integer getTOTAL_RFIDPASS() {
		return TOTAL_RFIDPASS;
	}

	/**
	 * @param tOTAL_RFIDPASS the tOTAL_RFIDPASS to set
	 */
	public void setTOTAL_RFIDPASS(Integer tOTAL_RFIDPASS) {
		TOTAL_RFIDPASS = tOTAL_RFIDPASS;
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
		result = prime * result + ((TRIPALLGROUPID == null) ? 0 : TRIPALLGROUPID.hashCode());
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
		TripDailyAllGroupDayDto other = (TripDailyAllGroupDayDto) obj;
		if (TRIPALLGROUPID == null) {
			if (other.TRIPALLGROUPID != null)
				return false;
		} else if (!TRIPALLGROUPID.equals(other.TRIPALLGROUPID))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripDailyAllGroupDayDto [TRIPALLGROUPID=");
		builder.append(TRIPALLGROUPID);
		builder.append(", TRIPALLGROUPNUMBER=");
		builder.append(TRIPALLGROUPNUMBER);
		builder.append(", TRIP_OPEN_DATE=");
		builder.append(TRIP_OPEN_DATE);
		builder.append(", BUSCOLLECTION=");
		builder.append(BUSCOLLECTION);
		builder.append(", RFIDRCG=");
		builder.append(RFIDRCG);
		builder.append(", RFIDCARD=");
		builder.append(RFIDCARD);
		builder.append(", RFIDUSAGE=");
		builder.append(RFIDUSAGE);
		builder.append(", BOOKING=");
		builder.append(BOOKING);
		builder.append(", TOTAL_DIESEL=");
		builder.append(TOTAL_DIESEL);
		builder.append(", TOTAL_USAGE_KM=");
		builder.append(TOTAL_USAGE_KM);
		builder.append(", TOTAL_DIESEL_MILAGE=");
		builder.append(TOTAL_DIESEL_MILAGE);
		builder.append(", TOTAL_TOTALPASSNGER=");
		builder.append(TOTAL_TOTALPASSNGER);
		builder.append(", TOTAL_DIESELEXPENSE=");
		builder.append(TOTAL_DIESELEXPENSE);
		builder.append(", TOTAL_OVERTIME=");
		builder.append(TOTAL_OVERTIME);
		builder.append(", ADVANCE=");
		builder.append(ADVANCE);
		builder.append(", COLLECTION_BALANCE=");
		builder.append(COLLECTION_BALANCE);
		builder.append(", EXPENSE_DAY=");
		builder.append(EXPENSE_DAY);
		builder.append(", TOTAL_BALANCE=");
		builder.append(TOTAL_BALANCE);
		builder.append(", TOTAL_WT=");
		builder.append(TOTAL_WT);
		builder.append(", TRIP_REMARKS=");
		builder.append(TRIP_REMARKS);
		builder.append(", TRIP_CLOSE_STATUS=");
		builder.append(TRIP_CLOSE_STATUS);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", LASTUPDATED=");
		builder.append(LASTUPDATED);
		builder.append("]");
		return builder.toString();
	}

}
