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
@Table(name = "TripCollectionIncome")
public class TripCollectionIncome implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripincomeID")
	private Long tripincomeID;

	/*@Column(name = "incomeName", length = 150)
	private String incomeName;*/
	
	@Column(name = "incomeId")
	private Integer incomeId;

	@Column(name = "incomeAmount")
	private Double incomeAmount;

	@Column(name = "incomeRefence", length = 20)
	private String incomeRefence;

/*	@Column(name = "incomeCollectedBy", length = 20)
	private String incomeCollectedBy;*/
	
	@Column(name = "incomeCollectedById")
	private Long incomeCollectedById;
	
	@Column(name = "companyId", nullable = false)
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;


	/*@Column(name = "createdBy", length = 50)
	private String createdBy;*/
	
	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@ManyToOne
	@JoinColumn(name = "TRIPCOLLID")
	private TripCollectionSheet tripCollectionsheet;

	public TripCollectionIncome() {
		super();
	}

	public TripCollectionIncome(Long tripincomeID, String incomeName, Double incomeAmount, String incomeRefence,
			String incomeCollectedBy, String createdBy, Date created, TripCollectionSheet tripCollectionsheet, Integer companyId) {
		super();
		this.tripincomeID = tripincomeID;
		//this.incomeName = incomeName;
		this.incomeAmount = incomeAmount;
		this.incomeRefence = incomeRefence;
		//this.incomeCollectedBy = incomeCollectedBy;
		//this.createdBy = createdBy;
		this.created = created;
		this.tripCollectionsheet = tripCollectionsheet;
		this.companyId = companyId;
	}

	public TripCollectionIncome(String incomeName, Double incomeAmount, String incomeRefence,
			String incomeCollectedBy) {
		super();
		//this.incomeName = incomeName;
		this.incomeAmount = incomeAmount;
		this.incomeRefence = incomeRefence;
		//this.incomeCollectedBy = incomeCollectedBy;
	}

	/**
	 * @return the tripincomeID
	 */
	public Long getTripincomeID() {
		return tripincomeID;
	}

	/**
	 * @param tripincomeID
	 *            the tripincomeID to set
	 */
	public void setTripincomeID(Long tripincomeID) {
		this.tripincomeID = tripincomeID;
	}

	/**
	 * @return the incomeName
	 *//*
	public String getIncomeName() {
		return incomeName;
	}

	*//**
	 * @param incomeName
	 *            the incomeName to set
	 *//*
	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}
*/
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

	/**
	 * @return the incomeCollectedBy
	 *//*
	public String getIncomeCollectedBy() {
		return incomeCollectedBy;
	}

	*//**
	 * @param incomeCollectedBy
	 *            the incomeCollectedBy to set
	 *//*
	public void setIncomeCollectedBy(String incomeCollectedBy) {
		this.incomeCollectedBy = incomeCollectedBy;
	}

	*//**
	 * @return the createdBy
	 *//*
	public String getCreatedBy() {
		return createdBy;
	}

	*//**
	 * @param createdBy
	 *            the createdBy to set
	 *//*
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

*/	/**
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the incomeId
	 */
	public Integer getIncomeId() {
		return incomeId;
	}

	/**
	 * @param incomeId the incomeId to set
	 */
	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
	}

	/**
	 * @return the incomeCollectedById
	 */
	public Long getIncomeCollectedById() {
		return incomeCollectedById;
	}

	/**
	 * @param incomeCollectedById the incomeCollectedById to set
	 */
	public void setIncomeCollectedById(Long incomeCollectedById) {
		this.incomeCollectedById = incomeCollectedById;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the tripCollectionsheet
	 */
	public TripCollectionSheet getTripCollectionsheet() {
		return tripCollectionsheet;
	}

	/**
	 * @param tripCollectionsheet
	 *            the tripCollectionsheet to set
	 */
	public void setTripCollectionsheet(TripCollectionSheet tripCollectionsheet) {
		this.tripCollectionsheet = tripCollectionsheet;
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
		result = prime * result + ((incomeAmount == null) ? 0 : incomeAmount.hashCode());
		//result = prime * result + ((incomeName == null) ? 0 : incomeName.hashCode());
		result = prime * result + ((tripincomeID == null) ? 0 : tripincomeID.hashCode());
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
		TripCollectionIncome other = (TripCollectionIncome) obj;
		if (incomeAmount == null) {
			if (other.incomeAmount != null)
				return false;
		} else if (!incomeAmount.equals(other.incomeAmount))
			return false;
		if (tripincomeID == null) {
			if (other.tripincomeID != null)
				return false;
		} else if (!tripincomeID.equals(other.tripincomeID))
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
		builder.append("TripCollectionIncome [tripincomeID=");
		builder.append(tripincomeID);
	/*	builder.append(", incomeName=");
		builder.append(incomeName);*/
		builder.append(", incomeAmount=");
		builder.append(incomeAmount);
		builder.append(", incomeRefence=");
		builder.append(incomeRefence);
		/*builder.append(", incomeCollectedBy=");
		builder.append(incomeCollectedBy);*/
		builder.append(", companyId=");
		builder.append(companyId);
		/*builder.append(", createdBy=");
		builder.append(createdBy);*/
		builder.append(", created=");
		builder.append(created);
		builder.append(", tripCollectionsheet=");
		builder.append(tripCollectionsheet);
		builder.append("]");
		return builder.toString();
	}

}