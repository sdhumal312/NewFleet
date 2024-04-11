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
@Table(name = "TripDayCollection")
public class TripDayCollection implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value for the TRIPDAYID field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRIPDAYID")
	private Long TRIPDAYID;

	/** The value for the TRIP_OPEN DATE field */
	@Column(name = "TRIP_OPEN_DATE")
	private Date TRIP_OPEN_DATE;

	/** The value for the TRIP_TOTAL_COLLECTION DATE field */
	@Column(name = "TOTAL_COLLECTION")
	private Double TOTAL_COLLECTION;

	/** The value for the TRIP_TOTAL_EXPENSE DATE field */
	@Column(name = "TOTAL_EXPENSE")
	private Double TOTAL_EXPENSE;

	/** The value for the TRIP_TOTAL_BALANCE DATE field */
	@Column(name = "TOTAL_BALANCE")
	private Double TOTAL_BALANCE;

	/** The value for the TRIP_TOTAL_EXPENSE DATE field */
	@Column(name = "TOTAL_DIESEL")
	private Integer TOTAL_DIESEL;

	/** The value for the TRIP_TOTAL_SINGL field */
	@Column(name = "TOTAL_SINGL")
	private Integer TOTAL_SINGL;

	/** The value for the TOTAL_BUS field */
	@Column(name = "TOTAL_BUS")
	private Integer TOTAL_BUS;

	/** The value for the TOTAL_GROUP_COLLECTION DATE field */
	@Column(name = "TOTAL_GROUP_COLLECTION")
	private Double TOTAL_GROUP_COLLECTION;

	/** The value for the STAFF_SALARY DATE field */
	@Column(name = "STAFF_SALARY")
	private Double STAFF_SALARY;

	/** The value for the TICKET_ROLL DATE field */
	@Column(name = "TICKET_ROLL")
	private Double TICKET_ROLL;

	/** The value for the ROLL_NUMBER DATE field */
	@Column(name = "ROLL_NUMBER")
	private Double ROLL_NUMBER;

	/** The value for the ROLL_PRICE DATE field */
	@Column(name = "ROLL_PRICE")
	private Double ROLL_PRICE;

	/** The value for the MECHANIC_MAINTANCE DATE field */
	@Column(name = "MECHANIC_MAINTANCE")
	private Double MECHANIC_MAINTANCE;

	/** The value for the INSURENCE_MAINTANCE DATE field */
	@Column(name = "INSURENCE_MAINTANCE")
	private Double INSURENCE_MAINTANCE;

	/** The value for the DC_BONUS DATE field */
	@Column(name = "DC_BONUS")
	private Double DC_BONUS;

	/** The value for the GRAND_TOTAL DATE field */
	@Column(name = "GRAND_TOTAL")
	private Double GRAND_TOTAL;

	/** The value for the NET_TOTAL DATE field */
	@Column(name = "NET_TOTAL")
	private Double NET_TOTAL;

	/** The value for the EACH_BUS_COLLECTION DATE field */
	@Column(name = "EACH_BUS_COLLECTION")
	private Double EACH_BUS_COLLECTION;

	/** The value for the PER_DAY_SINGL DATE field */
	@Column(name = "PER_DAY_SINGL")
	private Integer PER_DAY_SINGL;

	/** The value for the PER_SINGL_COLLECTION DATE field */
	@Column(name = "PER_SINGL_COLLECTION")
	private Double PER_SINGL_COLLECTION;

	/** The value for the TRIP_TOTAL RUNNING SINGL field */
	@Column(name = "TOTAL_RUN_SINGL")
	private Integer TOTAL_RUN_SINGL;

	/** The value for the TRIP_TOTAL CUT SINGL field */
	@Column(name = "TOTAL_CUT_SINGL")
	private Integer TOTAL_CUT_SINGL;

	/** The value for the TRIP_CLOSE_STATUS DATE field *//*
	@Column(name = "TRIP_CLOSE_STATUS", length = 25)
	private String TRIP_CLOSE_STATUS;*/
	
	@Column(name = "TRIP_CLOSE_STATUS_ID")
	private short TRIP_CLOSE_STATUS_ID;

	/** The value for the CREATEDBY DATE field *//*
	@Column(name = "TRIP_CLOSED_BY", length = 150, updatable = false)
	private String TRIP_CLOSED_BY;*/
	
	private Long TRIP_CLOSED_BY_ID;

	/** The value for the STATUS DATE field */
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the TRIP_CLOSED_DATE field */
	@Column(name = "TRIP_CLOSED_DATE", nullable = false, updatable = false)
	private Date TRIP_CLOSED_DATE;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	public TripDayCollection() {
		super();
	}

	public TripDayCollection(Long tRIPDAYID, Date tRIP_OPEN_DATE, Double tOTAL_COLLECTION, Double tOTAL_EXPENSE,
			Double tOTAL_BALANCE, Integer tOTAL_DIESEL, Integer tOTAL_SINGL, Integer tOTAL_BUS,
			Double tOTAL_GROUP_COLLECTION, Double sTAFF_SALARY, Double tICKET_ROLL, Double rOLL_NUMBER,
			Double rOLL_PRICE, Double mECHANIC_MAINTANCE, Double iNSURENCE_MAINTANCE, Double dC_BONUS,
			Double gRAND_TOTAL, Double nET_TOTAL, Double eACH_BUS_COLLECTION, Double pER_SINGL_COLLECTION,
			Integer tOTAL_RUN_SINGL, Integer tOTAL_CUT_SINGL, String tRIP_CLOSE_STATUS, String tRIP_CLOSED_BY,
			boolean MarkForDelete, Date tRIP_CLOSED_DATE, Integer cOMPANY_ID) {
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
		//TRIP_CLOSE_STATUS = tRIP_CLOSE_STATUS;
		//TRIP_CLOSED_BY = tRIP_CLOSED_BY;
		markForDelete = MarkForDelete;
		TRIP_CLOSED_DATE = tRIP_CLOSED_DATE;
		COMPANY_ID = cOMPANY_ID;
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
		TOTAL_GROUP_COLLECTION = tOTAL_GROUP_COLLECTION;
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
		STAFF_SALARY = sTAFF_SALARY;
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
		TICKET_ROLL = tICKET_ROLL;
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
		ROLL_NUMBER = rOLL_NUMBER;
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
		ROLL_PRICE = rOLL_PRICE;
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
		MECHANIC_MAINTANCE = mECHANIC_MAINTANCE;
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
		INSURENCE_MAINTANCE = iNSURENCE_MAINTANCE;
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
		DC_BONUS = dC_BONUS;
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
		GRAND_TOTAL = gRAND_TOTAL;
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
		NET_TOTAL = nET_TOTAL;
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
		EACH_BUS_COLLECTION = eACH_BUS_COLLECTION;
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
		PER_SINGL_COLLECTION = pER_SINGL_COLLECTION;
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
	 *//*
	public String getTRIP_CLOSE_STATUS() {
		return TRIP_CLOSE_STATUS;
	}

	*//**
	 * @param tRIP_CLOSE_STATUS
	 *            the tRIP_CLOSE_STATUS to set
	 *//*
	public void setTRIP_CLOSE_STATUS(String tRIP_CLOSE_STATUS) {
		TRIP_CLOSE_STATUS = tRIP_CLOSE_STATUS;
	}

	*//**
	 * @return the tRIP_CLOSED_BY
	 *//*
	public String getTRIP_CLOSED_BY() {
		return TRIP_CLOSED_BY;
	}

	*//**
	 * @param tRIP_CLOSED_BY
	 *            the tRIP_CLOSED_BY to set
	 *//*
	public void setTRIP_CLOSED_BY(String tRIP_CLOSED_BY) {
		TRIP_CLOSED_BY = tRIP_CLOSED_BY;
	}
*/
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
	 * @return the pER_DAY_SINGL
	 */
	public Integer getPER_DAY_SINGL() {
		return PER_DAY_SINGL;
	}

	/**
	 * @param pER_DAY_SINGL
	 *            the pER_DAY_SINGL to set
	 */
	public void setPER_DAY_SINGL(Integer pER_DAY_SINGL) {
		PER_DAY_SINGL = pER_DAY_SINGL;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
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

	/**
	 * @return the tRIP_CLOSED_BY_ID
	 */
	public Long getTRIP_CLOSED_BY_ID() {
		return TRIP_CLOSED_BY_ID;
	}

	/**
	 * @param tRIP_CLOSED_BY_ID the tRIP_CLOSED_BY_ID to set
	 */
	public void setTRIP_CLOSED_BY_ID(Long tRIP_CLOSED_BY_ID) {
		TRIP_CLOSED_BY_ID = tRIP_CLOSED_BY_ID;
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
		TripDayCollection other = (TripDayCollection) obj;
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
		builder.append("TripDayCollection [TRIPDAYID=");
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
		builder.append(", PER_DAY_SINGL=");
		builder.append(PER_DAY_SINGL);
		builder.append(", PER_SINGL_COLLECTION=");
		builder.append(PER_SINGL_COLLECTION);
		builder.append(", TOTAL_RUN_SINGL=");
		builder.append(TOTAL_RUN_SINGL);
		builder.append(", TOTAL_CUT_SINGL=");
		builder.append(TOTAL_CUT_SINGL);
	/*	builder.append(", TRIP_CLOSE_STATUS=");
		builder.append(TRIP_CLOSE_STATUS);
		builder.append(", TRIP_CLOSED_BY=");
		builder.append(TRIP_CLOSED_BY);*/
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", TRIP_CLOSED_DATE=");
		builder.append(TRIP_CLOSED_DATE);
		builder.append("]");
		return builder.toString();
	}

}
