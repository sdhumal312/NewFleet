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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TripDailyTimeIncome")
public class TripDailyTimeIncome implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TDTIMEID")
	private Long TDTIMEID;

	@Column(name = "incomeId", nullable = false)
	private Integer incomeId;

	@Column(name = "incomeAmount")
	private Double incomeAmount;

	@Column(name = "incomeRefence", length = 20)
	private String incomeRefence;

	@Column(name = "incomeFixedId", nullable = false)
	private short incomeFixedId;

	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", nullable = false)
	private Long createdById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "incomeCollectedById", nullable = false)
	private Long incomeCollectedById;

	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	

	@ManyToOne
	@JoinColumn(name = "TRIPDAILYID")
	private TripDailySheet tripDailysheet;

	public TripDailyTimeIncome() {
		super();
	}

	public TripDailyTimeIncome(Long tripincomeID, Double incomeAmount, String incomeRefence, Date created,
			TripDailySheet tripCollectionsheet) {
		super();
		this.TDTIMEID = tripincomeID;
		this.incomeAmount = incomeAmount;
		this.incomeRefence = incomeRefence;
		this.created = created;
		this.tripDailysheet = tripCollectionsheet;
	}

	public TripDailyTimeIncome(Double incomeAmount, String incomeRefence) {
		super();
		this.incomeAmount = incomeAmount;
		this.incomeRefence = incomeRefence;
	}

	/**
	 * @return the tDTIMEID
	 */
	public Long getTDTIMEID() {
		return TDTIMEID;
	}

	/**
	 * @param tDTIMEID
	 *            the tDTIMEID to set
	 */
	public void setTDTIMEID(Long tDTIMEID) {
		TDTIMEID = tDTIMEID;
	}

	/**
	 * @return the incomeAmount
	 */
	public Double getIncomeAmount() {
		return incomeAmount;
	}

	/**
	 * @param incomeAmount
	 *            the incomeAmount to set
	 */
	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	/**
	 * @return the incomeRefence
	 */
	public String getIncomeRefence() {
		return incomeRefence;
	}

	/**
	 * @param incomeRefence
	 *            the incomeRefence to set
	 */
	public void setIncomeRefence(String incomeRefence) {
		this.incomeRefence = incomeRefence;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the tripDailysheet
	 */
	public TripDailySheet getTripDailysheet() {
		return tripDailysheet;
	}

	/**
	 * @param tripDailysheet
	 *            the tripDailysheet to set
	 */
	public void setTripDailysheet(TripDailySheet tripDailysheet) {
		this.tripDailysheet = tripDailysheet;
	}

	/**
	 * @return the incomeId
	 */
	public Integer getIncomeId() {
		return incomeId;
	}

	/**
	 * @param incomeId
	 *            the incomeId to set
	 */
	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
	}

	/**
	 * @return the incomeFixedId
	 */
	public short getIncomeFixedId() {
		return incomeFixedId;
	}

	/**
	 * @param incomeFixedId
	 *            the incomeFixedId to set
	 */
	public void setIncomeFixedId(short incomeFixedId) {
		this.incomeFixedId = incomeFixedId;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById
	 *            the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the incomeCollectedById
	 */
	public Long getIncomeCollectedById() {
		return incomeCollectedById;
	}

	/**
	 * @param incomeCollectedById
	 *            the incomeCollectedById to set
	 */
	public void setIncomeCollectedById(Long incomeCollectedById) {
		this.incomeCollectedById = incomeCollectedById;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete
	 *            the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((incomeAmount == null) ? 0 : incomeAmount.hashCode());
		result = prime * result + ((incomeId == null) ? 0 : incomeId.hashCode());
		result = prime * result + ((TDTIMEID == null) ? 0 : TDTIMEID.hashCode());
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
		TripDailyTimeIncome other = (TripDailyTimeIncome) obj;
		if (incomeAmount == null) {
			if (other.incomeAmount != null)
				return false;
		} else if (!incomeAmount.equals(other.incomeAmount))
			return false;
		if (incomeId == null) {
			if (other.incomeId != null)
				return false;
		} else if (!incomeId.equals(other.incomeId))
			return false;
		if (TDTIMEID == null) {
			if (other.TDTIMEID != null)
				return false;
		} else if (!TDTIMEID.equals(other.TDTIMEID))
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
		builder.append("TripDailyTimeIncome [TDTIMEID=");
		builder.append(TDTIMEID);
		builder.append(", incomeAmount=");
		builder.append(incomeAmount);
		builder.append(", incomeRefence=");
		builder.append(incomeRefence);
		builder.append(", incomeCollectedBy=");
		builder.append(incomeCollectedById);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdBy=");
		builder.append(createdById);
		builder.append(", created=");
		builder.append(created);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", tripDailysheet=");
		builder.append(tripDailysheet);
		builder.append("]");
		return builder.toString();
	}

}