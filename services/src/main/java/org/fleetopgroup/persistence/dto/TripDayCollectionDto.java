package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class TripDayCollectionDto {

	/** The value for the TRIPDAYID field */

	private Long TRIPDAYID;

	/** The value for the TRIP_OPEN DATE field */
	private String TRIP_OPEN_DATE;

	/** The value for the TRIP_TOTAL_COLLECTION DATE field */
	private Double TOTAL_COLLECTION;

	/** The value for the TRIP_TOTAL_EXPENSE DATE field */
	private Double TOTAL_EXPENSE;

	/** The value for the TRIP_TOTAL_BALANCE DATE field */
	private Double TOTAL_BALANCE;

	/** The value for the TRIP_TOTAL_EXPENSE DATE field */
	private Integer TOTAL_DIESEL;

	/** The value for the TRIP_TOTAL_SINGL field */
	private Integer TOTAL_SINGL;

	/** The value for the TOTAL_BUS field */
	private Integer TOTAL_BUS;

	/** The value for the TOTAL_GROUP_COLLECTION DATE field */
	private Double TOTAL_GROUP_COLLECTION;

	/** The value for the STAFF_SALARY DATE field */
	private Double STAFF_SALARY;

	/** The value for the TICKET_ROLL DATE field */
	private Double TICKET_ROLL;

	/** The value for the ROLL_NUMBER DATE field */
	private Double ROLL_NUMBER;

	/** The value for the ROLL_PRICE DATE field */
	private Double ROLL_PRICE;

	/** The value for the MECHANIC_MAINTANCE DATE field */
	private Double MECHANIC_MAINTANCE;

	/** The value for the INSURENCE_MAINTANCE DATE field */
	private Double INSURENCE_MAINTANCE;

	/** The value for the DC_BONUS DATE field */
	private Double DC_BONUS;

	/** The value for the GRAND_TOTAL DATE field */
	private Double GRAND_TOTAL;

	/** The value for the NET_TOTAL DATE field */
	private Double NET_TOTAL;

	/** The value for the EACH_BUS_COLLECTION DATE field */
	private Double EACH_BUS_COLLECTION;

	/** The value for the PER_SINGL_COLLECTION DATE field */
	private Double PER_SINGL_COLLECTION;

	/** The value for the TRIP_TOTAL RUNNING SINGL field */
	private Integer TOTAL_RUN_SINGL;

	/** The value for the TRIP_TOTAL CUT SINGL field */
	private Integer TOTAL_CUT_SINGL;

	/** The value for the TRIP_CLOSE_STATUS DATE field */
	private String TRIP_CLOSE_STATUS;

	/** The value for the CREATEDBY DATE field */
	private String TRIP_CLOSED_BY;

	/** The value for the STATUS DATE field */
	boolean markForDelete;

	/** The value for the TRIP_CLOSED_DATE field */
	private Date TRIP_CLOSED_DATE;
	
	private short TRIP_CLOSE_STATUS_ID;

	public TripDayCollectionDto() {
		super();
	}

	public TripDayCollectionDto(Long tRIPDAYID, String tRIP_OPEN_DATE, Double tOTAL_COLLECTION, Double tOTAL_EXPENSE,
			Double tOTAL_BALANCE, Integer tOTAL_DIESEL, Integer tOTAL_SINGL, Integer tOTAL_BUS,
			Double tOTAL_GROUP_COLLECTION, Double sTAFF_SALARY, Double tICKET_ROLL, Double rOLL_NUMBER,
			Double rOLL_PRICE, Double mECHANIC_MAINTANCE, Double iNSURENCE_MAINTANCE, Double dC_BONUS,
			Double gRAND_TOTAL, Double nET_TOTAL, Double eACH_BUS_COLLECTION, Double pER_SINGL_COLLECTION,
			Integer tOTAL_RUN_SINGL, Integer tOTAL_CUT_SINGL, String tRIP_CLOSE_STATUS, String tRIP_CLOSED_BY,
			boolean MarkForDelete, Date tRIP_CLOSED_DATE) {
		super();
		TRIPDAYID = tRIPDAYID;
		TRIP_OPEN_DATE = tRIP_OPEN_DATE;
		TOTAL_COLLECTION = tOTAL_COLLECTION;
		TOTAL_EXPENSE = tOTAL_EXPENSE;
		TOTAL_BALANCE = tOTAL_BALANCE;
		TOTAL_DIESEL = tOTAL_DIESEL;
		TOTAL_SINGL = tOTAL_SINGL;
		TOTAL_BUS = tOTAL_BUS;
		TOTAL_GROUP_COLLECTION = tOTAL_GROUP_COLLECTION;
		STAFF_SALARY = sTAFF_SALARY;
		TICKET_ROLL = tICKET_ROLL;
		ROLL_NUMBER = rOLL_NUMBER;
		ROLL_PRICE = rOLL_PRICE;
		MECHANIC_MAINTANCE = mECHANIC_MAINTANCE;
		INSURENCE_MAINTANCE = iNSURENCE_MAINTANCE;
		DC_BONUS = dC_BONUS;
		GRAND_TOTAL = gRAND_TOTAL;
		NET_TOTAL = nET_TOTAL;
		EACH_BUS_COLLECTION = eACH_BUS_COLLECTION;
		PER_SINGL_COLLECTION = pER_SINGL_COLLECTION;
		TOTAL_RUN_SINGL = tOTAL_RUN_SINGL;
		TOTAL_CUT_SINGL = tOTAL_CUT_SINGL;
		TRIP_CLOSE_STATUS = tRIP_CLOSE_STATUS;
		TRIP_CLOSED_BY = tRIP_CLOSED_BY;
		markForDelete = MarkForDelete;
		TRIP_CLOSED_DATE = tRIP_CLOSED_DATE;

	}

	/**
	 * @return the tRIPDAYID
	 */
	public Long getTRIPDAYID() {
		return TRIPDAYID;
	}

	/**
	 * @param tRIPDAYID
	 *            the tRIPDAYID to set
	 */
	public void setTRIPDAYID(Long tRIPDAYID) {
		TRIPDAYID = tRIPDAYID;
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
		TOTAL_EXPENSE =Utility.round(tOTAL_EXPENSE, 2);
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
	 * @return the tOTAL_DIESEL
	 */
	public Integer getTOTAL_DIESEL() {
		return TOTAL_DIESEL;
	}

	/**
	 * @param tOTAL_DIESEL
	 *            the tOTAL_DIESEL to set
	 */
	public void setTOTAL_DIESEL(Integer tOTAL_DIESEL) {
		TOTAL_DIESEL = tOTAL_DIESEL;
	}

	/**
	 * @return the tOTAL_SINGL
	 */
	public Integer getTOTAL_SINGL() {
		return TOTAL_SINGL;
	}

	/**
	 * @param tOTAL_SINGL
	 *            the tOTAL_SINGL to set
	 */
	public void setTOTAL_SINGL(Integer tOTAL_SINGL) {
		TOTAL_SINGL = tOTAL_SINGL;
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
	 * @return the tOTAL_GROUP_COLLECTION
	 */
	public Double getTOTAL_GROUP_COLLECTION() {
		return TOTAL_GROUP_COLLECTION;
	}

	/**
	 * @param tOTAL_GROUP_COLLECTION
	 *            the tOTAL_GROUP_COLLECTION to set
	 */
	public void setTOTAL_GROUP_COLLECTION(Double tOTAL_GROUP_COLLECTION) {
		TOTAL_GROUP_COLLECTION = Utility.round(tOTAL_GROUP_COLLECTION, 2);
	}

	/**
	 * @return the sTAFF_SALARY
	 */
	public Double getSTAFF_SALARY() {
		return STAFF_SALARY;
	}

	/**
	 * @param sTAFF_SALARY
	 *            the sTAFF_SALARY to set
	 */
	public void setSTAFF_SALARY(Double sTAFF_SALARY) {
		STAFF_SALARY = Utility.round(sTAFF_SALARY, 2);
	}

	/**
	 * @return the tICKET_ROLL
	 */
	public Double getTICKET_ROLL() {
		return TICKET_ROLL;
	}

	/**
	 * @param tICKET_ROLL
	 *            the tICKET_ROLL to set
	 */
	public void setTICKET_ROLL(Double tICKET_ROLL) {
		TICKET_ROLL = Utility.round(tICKET_ROLL, 2);
	}

	/**
	 * @return the rOLL_NUMBER
	 */
	public Double getROLL_NUMBER() {
		return ROLL_NUMBER;
	}

	/**
	 * @param rOLL_NUMBER
	 *            the rOLL_NUMBER to set
	 */
	public void setROLL_NUMBER(Double rOLL_NUMBER) {
		ROLL_NUMBER = Utility.round(rOLL_NUMBER, 2);
	}

	/**
	 * @return the rOLL_PRICE
	 */
	public Double getROLL_PRICE() {
		return ROLL_PRICE;
	}

	/**
	 * @param rOLL_PRICE
	 *            the rOLL_PRICE to set
	 */
	public void setROLL_PRICE(Double rOLL_PRICE) {
		ROLL_PRICE =Utility.round(rOLL_PRICE, 2);
	}

	/**
	 * @return the mECHANIC_MAINTANCE
	 */
	public Double getMECHANIC_MAINTANCE() {
		return MECHANIC_MAINTANCE;
	}

	/**
	 * @param mECHANIC_MAINTANCE
	 *            the mECHANIC_MAINTANCE to set
	 */
	public void setMECHANIC_MAINTANCE(Double mECHANIC_MAINTANCE) {
		MECHANIC_MAINTANCE = Utility.round(mECHANIC_MAINTANCE, 2);
	}

	/**
	 * @return the iNSURENCE_MAINTANCE
	 */
	public Double getINSURENCE_MAINTANCE() {
		return INSURENCE_MAINTANCE;
	}

	/**
	 * @param iNSURENCE_MAINTANCE
	 *            the iNSURENCE_MAINTANCE to set
	 */
	public void setINSURENCE_MAINTANCE(Double iNSURENCE_MAINTANCE) {
		INSURENCE_MAINTANCE =Utility.round(iNSURENCE_MAINTANCE, 2);
	}

	/**
	 * @return the dC_BONUS
	 */
	public Double getDC_BONUS() {
		return DC_BONUS;
	}

	/**
	 * @param dC_BONUS
	 *            the dC_BONUS to set
	 */
	public void setDC_BONUS(Double dC_BONUS) {
		DC_BONUS = Utility.round(dC_BONUS,2);
	}

	/**
	 * @return the gRAND_TOTAL
	 */
	public Double getGRAND_TOTAL() {
		return GRAND_TOTAL;
	}

	/**
	 * @param gRAND_TOTAL
	 *            the gRAND_TOTAL to set
	 */
	public void setGRAND_TOTAL(Double gRAND_TOTAL) {
		GRAND_TOTAL =Utility.round(gRAND_TOTAL, 2);
	}

	/**
	 * @return the nET_TOTAL
	 */
	public Double getNET_TOTAL() {
		return NET_TOTAL;
	}

	/**
	 * @param nET_TOTAL
	 *            the nET_TOTAL to set
	 */
	public void setNET_TOTAL(Double nET_TOTAL) {
		NET_TOTAL = Utility.round(nET_TOTAL, 2);
	}

	/**
	 * @return the eACH_BUS_COLLECTION
	 */
	public Double getEACH_BUS_COLLECTION() {
		return EACH_BUS_COLLECTION;
	}

	/**
	 * @param eACH_BUS_COLLECTION
	 *            the eACH_BUS_COLLECTION to set
	 */
	public void setEACH_BUS_COLLECTION(Double eACH_BUS_COLLECTION) {
		EACH_BUS_COLLECTION = Utility.round(eACH_BUS_COLLECTION, 2);
	}

	/**
	 * @return the pER_SINGL_COLLECTION
	 */
	public Double getPER_SINGL_COLLECTION() {
		return PER_SINGL_COLLECTION;
	}

	/**
	 * @param pER_SINGL_COLLECTION
	 *            the pER_SINGL_COLLECTION to set
	 */
	public void setPER_SINGL_COLLECTION(Double pER_SINGL_COLLECTION) {
		PER_SINGL_COLLECTION =Utility.round(pER_SINGL_COLLECTION, 2);
	}

	/**
	 * @return the tOTAL_RUN_SINGL
	 */
	public Integer getTOTAL_RUN_SINGL() {
		return TOTAL_RUN_SINGL;
	}

	/**
	 * @param tOTAL_RUN_SINGL
	 *            the tOTAL_RUN_SINGL to set
	 */
	public void setTOTAL_RUN_SINGL(Integer tOTAL_RUN_SINGL) {
		TOTAL_RUN_SINGL = tOTAL_RUN_SINGL;
	}

	/**
	 * @return the tOTAL_CUT_SINGL
	 */
	public Integer getTOTAL_CUT_SINGL() {
		return TOTAL_CUT_SINGL;
	}

	/**
	 * @param tOTAL_CUT_SINGL
	 *            the tOTAL_CUT_SINGL to set
	 */
	public void setTOTAL_CUT_SINGL(Integer tOTAL_CUT_SINGL) {
		TOTAL_CUT_SINGL = tOTAL_CUT_SINGL;
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
	 * @return the tRIP_CLOSED_BY
	 */
	public String getTRIP_CLOSED_BY() {
		return TRIP_CLOSED_BY;
	}

	/**
	 * @param tRIP_CLOSED_BY
	 *            the tRIP_CLOSED_BY to set
	 */
	public void setTRIP_CLOSED_BY(String tRIP_CLOSED_BY) {
		TRIP_CLOSED_BY = tRIP_CLOSED_BY;
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
	 * @return the tRIP_CLOSED_DATE
	 */
	public Date getTRIP_CLOSED_DATE() {
		return TRIP_CLOSED_DATE;
	}

	/**
	 * @param tRIP_CLOSED_DATE
	 *            the tRIP_CLOSED_DATE to set
	 */
	public void setTRIP_CLOSED_DATE(Date tRIP_CLOSED_DATE) {
		TRIP_CLOSED_DATE = tRIP_CLOSED_DATE;
	}

	/**
	 * @return the tRIP_CLOSE_STATUS_ID
	 */
	public short getTRIP_CLOSE_STATUS_ID() {
		return TRIP_CLOSE_STATUS_ID;
	}

	/**
	 * @param tRIP_CLOSE_STATUS_ID the tRIP_CLOSE_STATUS_ID to set
	 */
	public void setTRIP_CLOSE_STATUS_ID(short tRIP_CLOSE_STATUS_ID) {
		TRIP_CLOSE_STATUS_ID = tRIP_CLOSE_STATUS_ID;
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
		result = prime * result + ((EACH_BUS_COLLECTION == null) ? 0 : EACH_BUS_COLLECTION.hashCode());
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
		TripDayCollectionDto other = (TripDayCollectionDto) obj;
		if (EACH_BUS_COLLECTION == null) {
			if (other.EACH_BUS_COLLECTION != null)
				return false;
		} else if (!EACH_BUS_COLLECTION.equals(other.EACH_BUS_COLLECTION))
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
		builder.append("TripDayCollectionDto [TRIPDAYID=");
		builder.append(TRIPDAYID);
		builder.append(", TRIP_OPEN_DATE=");
		builder.append(TRIP_OPEN_DATE);
		builder.append(", TOTAL_COLLECTION=");
		builder.append(TOTAL_COLLECTION);
		builder.append(", TOTAL_EXPENSE=");
		builder.append(TOTAL_EXPENSE);
		builder.append(", TOTAL_BALANCE=");
		builder.append(TOTAL_BALANCE);
		builder.append(", TOTAL_DIESEL=");
		builder.append(TOTAL_DIESEL);
		builder.append(", TOTAL_SINGL=");
		builder.append(TOTAL_SINGL);
		builder.append(", TOTAL_BUS=");
		builder.append(TOTAL_BUS);
		builder.append(", TOTAL_GROUP_COLLECTION=");
		builder.append(TOTAL_GROUP_COLLECTION);
		builder.append(", STAFF_SALARY=");
		builder.append(STAFF_SALARY);
		builder.append(", TICKET_ROLL=");
		builder.append(TICKET_ROLL);
		builder.append(", ROLL_NUMBER=");
		builder.append(ROLL_NUMBER);
		builder.append(", ROLL_PRICE=");
		builder.append(ROLL_PRICE);
		builder.append(", MECHANIC_MAINTANCE=");
		builder.append(MECHANIC_MAINTANCE);
		builder.append(", INSURENCE_MAINTANCE=");
		builder.append(INSURENCE_MAINTANCE);
		builder.append(", DC_BONUS=");
		builder.append(DC_BONUS);
		builder.append(", GRAND_TOTAL=");
		builder.append(GRAND_TOTAL);
		builder.append(", NET_TOTAL=");
		builder.append(NET_TOTAL);
		builder.append(", EACH_BUS_COLLECTION=");
		builder.append(EACH_BUS_COLLECTION);
		builder.append(", PER_SINGL_COLLECTION=");
		builder.append(PER_SINGL_COLLECTION);
		builder.append(", TOTAL_RUN_SINGL=");
		builder.append(TOTAL_RUN_SINGL);
		builder.append(", TOTAL_CUT_SINGL=");
		builder.append(TOTAL_CUT_SINGL);
		builder.append(", TRIP_CLOSE_STATUS=");
		builder.append(TRIP_CLOSE_STATUS);
		builder.append(", TRIP_CLOSED_BY=");
		builder.append(TRIP_CLOSED_BY);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", TRIP_CLOSED_DATE=");
		builder.append(TRIP_CLOSED_DATE);
		builder.append("]");
		return builder.toString();
	}

}
