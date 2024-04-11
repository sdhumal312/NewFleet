/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class BookingTripSheetDto {

	private Long BOOKID;

	/** The value party name */
	private String BTS_PARTYNAME;

	/** The value cost no */
	private String BTS_BB_NO;

	/** The value employees no */
	private String BTS_EMPLOYEE_NO;

	/** The value telephone no */
	private String BTS_TELEPHONE;

	/** The value ORDERED BY no */
	private String BTS_ORDEREDBY;

	/** The value REPORTTO no */
	private String BTS_REPORTTO;

	/** The value PARTY ADDRESS no */
	private String BTS_PARTY_ADDRESS;

	/** The value PARTY COUNTRY no */
	private String BTS_PARTY_COUNTRY;

	/** The value PARTY STATE no */
	private String BTS_PARTY_STATE;

	/** The value PARTY CITY no */
	private String BTS_PARTY_CITY;

	/** The value PIN CODE no */
	private Integer BTS_PARTY_PIN;

	/** The value BTS_VEHICLETYPE no */
	private String BTS_VEHICLETYPE;

	private String BTS_TRIP_FROM;

	private String BTS_TRIP_TO;

	/**
	 * The value for the BTS_FROM_DATE to create workOrder name one user name
	 * field
	 */
	private String BTS_FROM_DATE;

	/**
	 * The value for the BTS_FROM_DATE to create workOrder name one user name
	 * field
	 */
	private String BTS_TO_DATE;

	/** The value BTS_VEHICLETYPE no */
	private String BTS_VISIT_PLACES;

	/** The value PICKUP ADDRESS no */
	private String BTS_PICKUP_ADDRESS;

	/** The value PICKUP COUNTRY no */
	private String BTS_PICKUP_COUNTRY;

	/** The value PICKUP STATE no */
	private String BTS_PICKUP_STATE;

	/** The value PICKUP CITY no */
	private String BTS_PICKUP_CITY;

	/** The value PIN CODE no */
	private Integer BTS_PICKUP_PIN;

	/** The value BTS_TOTAL no */
	private Double BTS_TOTAL;

	/** The value BTS_AMOUNT_TOTAL no */
	private Double BTS_AMOUNT_TOTAL;

	/** The value BTS_TOTAL WoRlD no */
	private String BTS_TOTAL_WORLD;

	/** The value BTS_Service Tax Percent no */
	private Double BTS_SERVICETAX_PERCENT;

	/** The value BTS_service Amount no */
	private Double BTS_SERVICETAX_AMOUNT;

	/** The value for the ISSUES Assign to Status one user name field */
	private String BTS_STATUS;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;

	boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	private String CREATED_DATE;

	/** The value for the ISSUES_Due DATE field */
	private String LASTUPDATED_DATE;

	public BookingTripSheetDto() {
		super();
	}

	public BookingTripSheetDto(Long bOOKID, String bTS_PARTYNAME, String bTS_COST_NO, String bTS_EMPLOYEE_NO,
			String bTS_TELEPHONE, String bTS_ORDEREDBY, String bTS_REPORTTO, String bTS_PARTY_ADDRESS,
			String bTS_PARTY_COUNTRY, String bTS_PARTY_STATE, String bTS_PARTY_CITY, Integer bTS_PARTY_PIN,
			String bTS_VEHICLETYPE, String bTS_FROM_DATE, String bTS_TO_DATE, String bTS_VISIT_PLACES,
			String bTS_PICKUP_ADDRESS, String bTS_PICKUP_COUNTRY, String bTS_PICKUP_STATE, String bTS_PICKUP_CITY,
			Integer bTS_PICKUP_PIN, Double bTS_TOTAL, String bTS_STATUS, String cREATEDBY, String lASTMODIFIEDBY,
			boolean MarkForDelete, String cREATED_DATE, String lASTUPDATED_DATE) {
		super();
		BOOKID = bOOKID;
		BTS_PARTYNAME = bTS_PARTYNAME;
		BTS_BB_NO = bTS_COST_NO;
		BTS_EMPLOYEE_NO = bTS_EMPLOYEE_NO;
		BTS_TELEPHONE = bTS_TELEPHONE;
		BTS_ORDEREDBY = bTS_ORDEREDBY;
		BTS_REPORTTO = bTS_REPORTTO;
		BTS_PARTY_ADDRESS = bTS_PARTY_ADDRESS;
		BTS_PARTY_COUNTRY = bTS_PARTY_COUNTRY;
		BTS_PARTY_STATE = bTS_PARTY_STATE;
		BTS_PARTY_CITY = bTS_PARTY_CITY;
		BTS_PARTY_PIN = bTS_PARTY_PIN;
		BTS_VEHICLETYPE = bTS_VEHICLETYPE;
		BTS_FROM_DATE = bTS_FROM_DATE;
		BTS_TO_DATE = bTS_TO_DATE;
		BTS_VISIT_PLACES = bTS_VISIT_PLACES;
		BTS_PICKUP_ADDRESS = bTS_PICKUP_ADDRESS;
		BTS_PICKUP_COUNTRY = bTS_PICKUP_COUNTRY;
		BTS_PICKUP_STATE = bTS_PICKUP_STATE;
		BTS_PICKUP_CITY = bTS_PICKUP_CITY;
		BTS_PICKUP_PIN = bTS_PICKUP_PIN;
		BTS_TOTAL = bTS_TOTAL;
		BTS_STATUS = bTS_STATUS;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	/**
	 * @return the bOOKID
	 */
	public Long getBOOKID() {
		return BOOKID;
	}

	/**
	 * @param bOOKID
	 *            the bOOKID to set
	 */
	public void setBOOKID(Long bOOKID) {
		BOOKID = bOOKID;
	}

	/**
	 * @return the bTS_PARTYNAME
	 */
	public String getBTS_PARTYNAME() {
		return BTS_PARTYNAME;
	}

	/**
	 * @param bTS_PARTYNAME
	 *            the bTS_PARTYNAME to set
	 */
	public void setBTS_PARTYNAME(String bTS_PARTYNAME) {
		BTS_PARTYNAME = bTS_PARTYNAME;
	}

	/**
	 * @return the bTS_COST_NO
	 */
	public String getBTS_BB_NO() {
		return BTS_BB_NO;
	}

	/**
	 * @param bTS_COST_NO
	 *            the bTS_COST_NO to set
	 */
	public void setBTS_BB_NO(String bTS_BB_NO) {
		BTS_BB_NO = bTS_BB_NO;
	}

	/**
	 * @return the bTS_EMPLOYEE_NO
	 */
	public String getBTS_EMPLOYEE_NO() {
		return BTS_EMPLOYEE_NO;
	}

	/**
	 * @param bTS_EMPLOYEE_NO
	 *            the bTS_EMPLOYEE_NO to set
	 */
	public void setBTS_EMPLOYEE_NO(String bTS_EMPLOYEE_NO) {
		BTS_EMPLOYEE_NO = bTS_EMPLOYEE_NO;
	}

	/**
	 * @return the bTS_TELEPHONE
	 */
	public String getBTS_TELEPHONE() {
		return BTS_TELEPHONE;
	}

	/**
	 * @param bTS_TELEPHONE
	 *            the bTS_TELEPHONE to set
	 */
	public void setBTS_TELEPHONE(String bTS_TELEPHONE) {
		BTS_TELEPHONE = bTS_TELEPHONE;
	}

	/**
	 * @return the bTS_ORDEREDBY
	 */
	public String getBTS_ORDEREDBY() {
		return BTS_ORDEREDBY;
	}

	/**
	 * @param bTS_ORDEREDBY
	 *            the bTS_ORDEREDBY to set
	 */
	public void setBTS_ORDEREDBY(String bTS_ORDEREDBY) {
		BTS_ORDEREDBY = bTS_ORDEREDBY;
	}

	/**
	 * @return the bTS_REPORTTO
	 */
	public String getBTS_REPORTTO() {
		return BTS_REPORTTO;
	}

	/**
	 * @param bTS_REPORTTO
	 *            the bTS_REPORTTO to set
	 */
	public void setBTS_REPORTTO(String bTS_REPORTTO) {
		BTS_REPORTTO = bTS_REPORTTO;
	}

	/**
	 * @return the bTS_PARTY_ADDRESS
	 */
	public String getBTS_PARTY_ADDRESS() {
		return BTS_PARTY_ADDRESS;
	}

	/**
	 * @param bTS_PARTY_ADDRESS
	 *            the bTS_PARTY_ADDRESS to set
	 */
	public void setBTS_PARTY_ADDRESS(String bTS_PARTY_ADDRESS) {
		BTS_PARTY_ADDRESS = bTS_PARTY_ADDRESS;
	}

	/**
	 * @return the bTS_PARTY_COUNTRY
	 */
	public String getBTS_PARTY_COUNTRY() {
		return BTS_PARTY_COUNTRY;
	}

	/**
	 * @param bTS_PARTY_COUNTRY
	 *            the bTS_PARTY_COUNTRY to set
	 */
	public void setBTS_PARTY_COUNTRY(String bTS_PARTY_COUNTRY) {
		BTS_PARTY_COUNTRY = bTS_PARTY_COUNTRY;
	}

	/**
	 * @return the bTS_PARTY_STATE
	 */
	public String getBTS_PARTY_STATE() {
		return BTS_PARTY_STATE;
	}

	/**
	 * @param bTS_PARTY_STATE
	 *            the bTS_PARTY_STATE to set
	 */
	public void setBTS_PARTY_STATE(String bTS_PARTY_STATE) {
		BTS_PARTY_STATE = bTS_PARTY_STATE;
	}

	/**
	 * @return the bTS_PARTY_CITY
	 */
	public String getBTS_PARTY_CITY() {
		return BTS_PARTY_CITY;
	}

	/**
	 * @param bTS_PARTY_CITY
	 *            the bTS_PARTY_CITY to set
	 */
	public void setBTS_PARTY_CITY(String bTS_PARTY_CITY) {
		BTS_PARTY_CITY = bTS_PARTY_CITY;
	}

	/**
	 * @return the bTS_PARTY_PIN
	 */
	public Integer getBTS_PARTY_PIN() {
		return BTS_PARTY_PIN;
	}

	/**
	 * @param bTS_PARTY_PIN
	 *            the bTS_PARTY_PIN to set
	 */
	public void setBTS_PARTY_PIN(Integer bTS_PARTY_PIN) {
		BTS_PARTY_PIN = bTS_PARTY_PIN;
	}

	/**
	 * @return the bTS_VEHICLETYPE
	 */
	public String getBTS_VEHICLETYPE() {
		return BTS_VEHICLETYPE;
	}

	/**
	 * @param bTS_VEHICLETYPE
	 *            the bTS_VEHICLETYPE to set
	 */
	public void setBTS_VEHICLETYPE(String bTS_VEHICLETYPE) {
		BTS_VEHICLETYPE = bTS_VEHICLETYPE;
	}

	/**
	 * @return the bTS_FROM_DATE
	 */
	public String getBTS_FROM_DATE() {
		return BTS_FROM_DATE;
	}

	/**
	 * @param bTS_FROM_DATE
	 *            the bTS_FROM_DATE to set
	 */
	public void setBTS_FROM_DATE(String bTS_FROM_DATE) {
		BTS_FROM_DATE = bTS_FROM_DATE;
	}

	/**
	 * @return the bTS_TO_DATE
	 */
	public String getBTS_TO_DATE() {
		return BTS_TO_DATE;
	}

	/**
	 * @param bTS_TO_DATE
	 *            the bTS_TO_DATE to set
	 */
	public void setBTS_TO_DATE(String bTS_TO_DATE) {
		BTS_TO_DATE = bTS_TO_DATE;
	}

	/**
	 * @return the bTS_VISIT_PLACES
	 */
	public String getBTS_VISIT_PLACES() {
		return BTS_VISIT_PLACES;
	}

	/**
	 * @param bTS_VISIT_PLACES
	 *            the bTS_VISIT_PLACES to set
	 */
	public void setBTS_VISIT_PLACES(String bTS_VISIT_PLACES) {
		BTS_VISIT_PLACES = bTS_VISIT_PLACES;
	}

	/**
	 * @return the bTS_PICKUP_ADDRESS
	 */
	public String getBTS_PICKUP_ADDRESS() {
		return BTS_PICKUP_ADDRESS;
	}

	/**
	 * @param bTS_PICKUP_ADDRESS
	 *            the bTS_PICKUP_ADDRESS to set
	 */
	public void setBTS_PICKUP_ADDRESS(String bTS_PICKUP_ADDRESS) {
		BTS_PICKUP_ADDRESS = bTS_PICKUP_ADDRESS;
	}

	/**
	 * @return the bTS_PICKUP_COUNTRY
	 */
	public String getBTS_PICKUP_COUNTRY() {
		return BTS_PICKUP_COUNTRY;
	}

	/**
	 * @param bTS_PICKUP_COUNTRY
	 *            the bTS_PICKUP_COUNTRY to set
	 */
	public void setBTS_PICKUP_COUNTRY(String bTS_PICKUP_COUNTRY) {
		BTS_PICKUP_COUNTRY = bTS_PICKUP_COUNTRY;
	}

	/**
	 * @return the bTS_PICKUP_STATE
	 */
	public String getBTS_PICKUP_STATE() {
		return BTS_PICKUP_STATE;
	}

	/**
	 * @param bTS_PICKUP_STATE
	 *            the bTS_PICKUP_STATE to set
	 */
	public void setBTS_PICKUP_STATE(String bTS_PICKUP_STATE) {
		BTS_PICKUP_STATE = bTS_PICKUP_STATE;
	}

	/**
	 * @return the bTS_PICKUP_CITY
	 */
	public String getBTS_PICKUP_CITY() {
		return BTS_PICKUP_CITY;
	}

	/**
	 * @param bTS_PICKUP_CITY
	 *            the bTS_PICKUP_CITY to set
	 */
	public void setBTS_PICKUP_CITY(String bTS_PICKUP_CITY) {
		BTS_PICKUP_CITY = bTS_PICKUP_CITY;
	}

	/**
	 * @return the bTS_PICKUP_PIN
	 */
	public Integer getBTS_PICKUP_PIN() {
		return BTS_PICKUP_PIN;
	}

	/**
	 * @param bTS_PICKUP_PIN
	 *            the bTS_PICKUP_PIN to set
	 */
	public void setBTS_PICKUP_PIN(Integer bTS_PICKUP_PIN) {
		BTS_PICKUP_PIN = bTS_PICKUP_PIN;
	}

	/**
	 * @return the bTS_TOTAL
	 */
	public Double getBTS_TOTAL() {
		return BTS_TOTAL;
	}

	/**
	 * @param bTS_TOTAL
	 *            the bTS_TOTAL to set
	 */
	public void setBTS_TOTAL(Double bTS_TOTAL) {
		BTS_TOTAL = Utility.round(bTS_TOTAL, 2);
	}

	/**
	 * @return the bTS_STATUS
	 */
	public String getBTS_STATUS() {
		return BTS_STATUS;
	}

	/**
	 * @param bTS_STATUS
	 *            the bTS_STATUS to set
	 */
	public void setBTS_STATUS(String bTS_STATUS) {
		BTS_STATUS = bTS_STATUS;
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
	 * @return the sTATUS
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param sTATUS
	 *            the sTATUS to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the cREATED_DATE
	 */
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	/**
	 * @param cREATED_DATE
	 *            the cREATED_DATE to set
	 */
	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the lASTUPDATED_DATE
	 */
	public String getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	/**
	 * @param lASTUPDATED_DATE
	 *            the lASTUPDATED_DATE to set
	 */
	public void setLASTUPDATED_DATE(String lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	/**
	 * @return the bTS_TRIP_FROM
	 */
	public String getBTS_TRIP_FROM() {
		return BTS_TRIP_FROM;
	}

	/**
	 * @param bTS_TRIP_FROM
	 *            the bTS_TRIP_FROM to set
	 */
	public void setBTS_TRIP_FROM(String bTS_TRIP_FROM) {
		BTS_TRIP_FROM = bTS_TRIP_FROM;
	}

	/**
	 * @return the bTS_TRIP_TO
	 */
	public String getBTS_TRIP_TO() {
		return BTS_TRIP_TO;
	}

	/**
	 * @param bTS_TRIP_TO
	 *            the bTS_TRIP_TO to set
	 */
	public void setBTS_TRIP_TO(String bTS_TRIP_TO) {
		BTS_TRIP_TO = bTS_TRIP_TO;
	}

	/**
	 * @return the bTS_SERVICETAX_PERCENT
	 */
	public Double getBTS_SERVICETAX_PERCENT() {
		return BTS_SERVICETAX_PERCENT;
	}

	/**
	 * @param bTS_SERVICETAX_PERCENT
	 *            the bTS_SERVICETAX_PERCENT to set
	 */
	public void setBTS_SERVICETAX_PERCENT(Double bTS_SERVICETAX_PERCENT) {
		BTS_SERVICETAX_PERCENT = Utility.round(bTS_SERVICETAX_PERCENT, 2);
	}

	/**
	 * @return the bTS_SERVICETAX_AMOUNT
	 */
	public Double getBTS_SERVICETAX_AMOUNT() {
		return BTS_SERVICETAX_AMOUNT;
	}

	/**
	 * @param bTS_SERVICETAX_AMOUNT
	 *            the bTS_SERVICETAX_AMOUNT to set
	 */
	public void setBTS_SERVICETAX_AMOUNT(Double bTS_SERVICETAX_AMOUNT) {
		BTS_SERVICETAX_AMOUNT = Utility.round(bTS_SERVICETAX_AMOUNT, 2);
	}

	/**
	 * @return the bTS_TOTAL_WORLD
	 */
	public String getBTS_TOTAL_WORLD() {
		return BTS_TOTAL_WORLD;
	}

	/**
	 * @param bTS_TOTAL_WORLD
	 *            the bTS_TOTAL_WORLD to set
	 */
	public void setBTS_TOTAL_WORLD(String bTS_TOTAL_WORLD) {
		BTS_TOTAL_WORLD = bTS_TOTAL_WORLD;
	}

	/**
	 * @return the bTS_AMOUNT_TOTAL
	 */
	public Double getBTS_AMOUNT_TOTAL() {
		return BTS_AMOUNT_TOTAL;
	}

	/**
	 * @param bTS_AMOUNT_TOTAL
	 *            the bTS_AMOUNT_TOTAL to set
	 */
	public void setBTS_AMOUNT_TOTAL(Double bTS_AMOUNT_TOTAL) {
		BTS_AMOUNT_TOTAL = Utility.round(bTS_AMOUNT_TOTAL, 2);
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
		result = prime * result + ((BOOKID == null) ? 0 : BOOKID.hashCode());
		result = prime * result + ((BTS_FROM_DATE == null) ? 0 : BTS_FROM_DATE.hashCode());
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
		BookingTripSheetDto other = (BookingTripSheetDto) obj;
		if (BOOKID == null) {
			if (other.BOOKID != null)
				return false;
		} else if (!BOOKID.equals(other.BOOKID))
			return false;
		if (BTS_FROM_DATE == null) {
			if (other.BTS_FROM_DATE != null)
				return false;
		} else if (!BTS_FROM_DATE.equals(other.BTS_FROM_DATE))
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
		builder.append("BookingTripSheetDto [BOOKID=");
		builder.append(BOOKID);
		builder.append(", BTS_PARTYNAME=");
		builder.append(BTS_PARTYNAME);
		builder.append(", BTS_BB_NO=");
		builder.append(BTS_BB_NO);
		builder.append(", BTS_EMPLOYEE_NO=");
		builder.append(BTS_EMPLOYEE_NO);
		builder.append(", BTS_TELEPHONE=");
		builder.append(BTS_TELEPHONE);
		builder.append(", BTS_ORDEREDBY=");
		builder.append(BTS_ORDEREDBY);
		builder.append(", BTS_REPORTTO=");
		builder.append(BTS_REPORTTO);
		builder.append(", BTS_PARTY_ADDRESS=");
		builder.append(BTS_PARTY_ADDRESS);
		builder.append(", BTS_PARTY_COUNTRY=");
		builder.append(BTS_PARTY_COUNTRY);
		builder.append(", BTS_PARTY_STATE=");
		builder.append(BTS_PARTY_STATE);
		builder.append(", BTS_PARTY_CITY=");
		builder.append(BTS_PARTY_CITY);
		builder.append(", BTS_PARTY_PIN=");
		builder.append(BTS_PARTY_PIN);
		builder.append(", BTS_VEHICLETYPE=");
		builder.append(BTS_VEHICLETYPE);
		builder.append(", BTS_TRIP_FROM=");
		builder.append(BTS_TRIP_FROM);
		builder.append(", BTS_TRIP_TO=");
		builder.append(BTS_TRIP_TO);
		builder.append(", BTS_FROM_DATE=");
		builder.append(BTS_FROM_DATE);
		builder.append(", BTS_TO_DATE=");
		builder.append(BTS_TO_DATE);
		builder.append(", BTS_VISIT_PLACES=");
		builder.append(BTS_VISIT_PLACES);
		builder.append(", BTS_PICKUP_ADDRESS=");
		builder.append(BTS_PICKUP_ADDRESS);
		builder.append(", BTS_PICKUP_COUNTRY=");
		builder.append(BTS_PICKUP_COUNTRY);
		builder.append(", BTS_PICKUP_STATE=");
		builder.append(BTS_PICKUP_STATE);
		builder.append(", BTS_PICKUP_CITY=");
		builder.append(BTS_PICKUP_CITY);
		builder.append(", BTS_PICKUP_PIN=");
		builder.append(BTS_PICKUP_PIN);
		builder.append(", BTS_TOTAL=");
		builder.append(BTS_TOTAL);
		builder.append(", BTS_AMOUNT_TOTAL=");
		builder.append(BTS_AMOUNT_TOTAL);
		builder.append(", BTS_TOTAL_WORLD=");
		builder.append(BTS_TOTAL_WORLD);
		builder.append(", BTS_SERVICETAX_PERCENT=");
		builder.append(BTS_SERVICETAX_PERCENT);
		builder.append(", BTS_SERVICETAX_AMOUNT=");
		builder.append(BTS_SERVICETAX_AMOUNT);
		builder.append(", BTS_STATUS=");
		builder.append(BTS_STATUS);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append("]");
		return builder.toString();
	}

}
