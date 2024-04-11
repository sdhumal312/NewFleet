package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TripRoutefixedIncome")
public class TripRoutefixedIncome implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "routefixedID")
	private Integer routefixedID;

	@Column(name = "incomeId", nullable = false)
	private Integer	incomeId;

	@Column(name = "incomeAmount")
	private Double incomeAmount;

	@Column(name = "incomePlaceId", nullable = false)
	private Integer incomePlaceId;
	
	@Column(name = "incomeRefence", length = 20)
	private String incomeRefence;
	
	@Column(name = "incomeFixedId", nullable = false)
	private short incomeFixedId;

	@ManyToOne
	@JoinColumn(name = "routeID")
	private TripRoute triproute;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public TripRoutefixedIncome() {
		super();
	}

	public TripRoutefixedIncome(Integer routefixedID,  Double incomeAmount, String incomeRefence,
			TripRoute triproute, boolean markForDelete) {
		super();
		this.routefixedID = routefixedID;
		this.incomeAmount = incomeAmount;
		this.incomeRefence = incomeRefence;
		this.triproute = triproute;
		this.markForDelete = markForDelete;
	}

	public TripRoutefixedIncome(Double incomeAmount, String incomeRefence,
			boolean markForDelete) {
		super();
		this.incomeAmount = incomeAmount;
		this.incomeRefence = incomeRefence;
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the routefixedID
	 */
	public Integer getRoutefixedID() {
		return routefixedID;
	}

	/**
	 * @param routefixedID
	 *            the routefixedID to set
	 */
	public void setRoutefixedID(Integer routefixedID) {
		this.routefixedID = routefixedID;
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


	public short getIncomeFixedId() {
		return incomeFixedId;
	}

	public void setIncomeFixedId(short incomeFixedId) {
		this.incomeFixedId = incomeFixedId;
	}

	/**
	 * @return the triproute
	 */
	public TripRoute getTriproute() {
		return triproute;
	}


	/**
	 * @param triproute
	 *            the triproute to set
	 */
	public void setTriproute(TripRoute triproute) {
		this.triproute = triproute;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
	}

	public Integer getIncomePlaceId() {
		return incomePlaceId;
	}

	public void setIncomePlaceId(Integer incomePlaceId) {
		this.incomePlaceId = incomePlaceId;
	}

	@Override
	public String toString() {
		return "TripRoutefixedIncome [routefixedID=" + routefixedID + ", incomeId=" + incomeId + ", incomeAmount="
				+ incomeAmount + ", incomePlaceId=" + incomePlaceId + ", incomeRefence=" + incomeRefence
				+ ", incomeFixedId=" + incomeFixedId + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}

}