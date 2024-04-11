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
@Table(name = "TripGroupCollection")
public class TripGroupCollection implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value for the TRIPGROUPID field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRIPGROUPID")
	private Long TRIPGROUPID;

	/** The value for the VEHICLE GROUP COMPANY NAME field *//*
	@Column(name = "VEHICLE_GROUP", length = 150)
	private String VEHICLE_GROUP;*/

	/** The value for the TRIP_OPEN DATE field */
	@Column(name = "TRIP_OPEN_DATE")
	private Date TRIP_OPEN_DATE;

	/** The value for the TRIP_TOTAL_COLLECTION DATE field */
	@Column(name = "TOTAL_COLLECTION")
	private Double TOTAL_COLLECTION;

	/** The value for the TRIP_TOTAL_EXPENSE DATE field */
	@Column(name = "TOTAL_EXPENSE")
	private Double TOTAL_EXPENSE;

	/** The value for the TOTAL_DIESEL DATE field */
	@Column(name = "TOTAL_DIESEL")
	private Integer TOTAL_DIESEL;

	/** The value for the TRIP_TOTAL_BALANCE DATE field */
	@Column(name = "TOTAL_BALANCE")
	private Double TOTAL_BALANCE;

	/** The value for the TRIP_TOTAL_SINGL field */
	@Column(name = "TOTAL_SINGL")
	private Integer TOTAL_SINGL;

	/** The value for the TOTAL_BUS field */
	@Column(name = "TOTAL_BUS")
	private Integer TOTAL_BUS;

	/** The value for the TOTAL_GROUP_COLLECTION DATE field */
	@Column(name = "TOTAL_GROUP_COLLECTION")
	private Double TOTAL_GROUP_COLLECTION;

	/** The value for the TRIP_CLOSE_STATUS DATE field *//*
	@Column(name = "TRIP_CLOSE_STATUS", length = 25)
	private String TRIP_CLOSE_STATUS;*/
	
	@Column(name = "TRIP_CLOSE_STATUS_ID")
	private short TRIP_CLOSE_STATUS_ID;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;
	
	@Column(name = "VEHICLE_GROUP_ID", nullable = false)
	private long VEHICLE_GROUP_ID;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public TripGroupCollection() {
		super();
	}

	public TripGroupCollection(Long tRIPGROUPID, Date tRIP_OPEN_DATE, Double tOTAL_COLLECTION,
			Double tOTAL_EXPENSE, Integer tOTAL_DIESEL, Double tOTAL_BALANCE, Integer tOTAL_SINGL, Integer tOTAL_BUS,
			Double tOTAL_GROUP_COLLECTION,  Integer companyId) {
		super();
		TRIPGROUPID = tRIPGROUPID;
		//VEHICLE_GROUP = vEHICLE_GROUP;
		TRIP_OPEN_DATE = tRIP_OPEN_DATE;
		TOTAL_COLLECTION = tOTAL_COLLECTION;
		TOTAL_EXPENSE = tOTAL_EXPENSE;
		TOTAL_DIESEL = tOTAL_DIESEL;
		TOTAL_BALANCE = tOTAL_BALANCE;
		TOTAL_SINGL = tOTAL_SINGL;
		TOTAL_BUS = tOTAL_BUS;
		TOTAL_GROUP_COLLECTION = tOTAL_GROUP_COLLECTION;
		//TRIP_CLOSE_STATUS = tRIP_CLOSE_STATUS;
		COMPANY_ID = companyId;
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
	 *//*
	public String getVEHICLE_GROUP() {
		return VEHICLE_GROUP;
	}

	*//**
	 * @param vEHICLE_GROUP
	 *            the vEHICLE_GROUP to set
	 *//*
	public void setVEHICLE_GROUP(String vEHICLE_GROUP) {
		VEHICLE_GROUP = vEHICLE_GROUP;
	}*/

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
	}*/

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
		result = prime * result + ((TOTAL_BALANCE == null) ? 0 : TOTAL_BALANCE.hashCode());
		result = prime * result + ((TOTAL_COLLECTION == null) ? 0 : TOTAL_COLLECTION.hashCode());
		result = prime * result + ((TOTAL_EXPENSE == null) ? 0 : TOTAL_EXPENSE.hashCode());
		result = prime * result + ((TOTAL_SINGL == null) ? 0 : TOTAL_SINGL.hashCode());
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
		TripGroupCollection other = (TripGroupCollection) obj;
		if (TOTAL_BALANCE == null) {
			if (other.TOTAL_BALANCE != null)
				return false;
		} else if (!TOTAL_BALANCE.equals(other.TOTAL_BALANCE))
			return false;
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
		if (TOTAL_SINGL == null) {
			if (other.TOTAL_SINGL != null)
				return false;
		} else if (!TOTAL_SINGL.equals(other.TOTAL_SINGL))
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
		builder.append("TripGroupCollection [TRIPGROUPID=");
		builder.append(TRIPGROUPID);
		/*builder.append(", VEHICLE_GROUP=");
		builder.append(VEHICLE_GROUP);*/
		builder.append(", TRIP_OPEN_DATE=");
		builder.append(TRIP_OPEN_DATE);
		builder.append(", TOTAL_COLLECTION=");
		builder.append(TOTAL_COLLECTION);
		builder.append(", TOTAL_EXPENSE=");
		builder.append(TOTAL_EXPENSE);
		builder.append(", TOTAL_DIESEL=");
		builder.append(TOTAL_DIESEL);
		builder.append(", TOTAL_BALANCE=");
		builder.append(TOTAL_BALANCE);
		builder.append(", TOTAL_SINGL=");
		builder.append(TOTAL_SINGL);
		builder.append(", TOTAL_BUS=");
		builder.append(TOTAL_BUS);
		builder.append(", TOTAL_GROUP_COLLECTION=");
		builder.append(TOTAL_GROUP_COLLECTION);
		/*builder.append(", TRIP_CLOSE_STATUS=");
		builder.append(TRIP_CLOSE_STATUS);*/
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", VEHICLE_GROUP_ID=");
		builder.append(VEHICLE_GROUP_ID);
		
		builder.append("]");
		return builder.toString();
	}

}
