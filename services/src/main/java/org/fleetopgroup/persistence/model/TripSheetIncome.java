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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tripsheetincome")
public class TripSheetIncome implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripincomeID")
	private Long tripincomeID;

	@Column(name = "incomeId", nullable = false)
	private Integer incomeId;

	@Column(name = "incomeAmount")
	private Double incomeAmount;

	@Column(name = "incomePlaceId", nullable = false)
	private Integer incomePlaceId;

	@Column(name = "incomeRefence", length = 150)
	private String incomeRefence;

	@Column(name = "incomeCollectedById", nullable = false)
	private Long incomeCollectedById;
	
	@Column(name = "incomeFixedId", nullable = false)
	private short incomeFixedId;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", nullable = false)
	private Long createdById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@ManyToOne
	@JoinColumn(name = "tripSheetID")
	@JsonBackReference
	private TripSheet tripsheet;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "lHPVDetailsId")
	private Long lHPVDetailsId;
	
	@Column(name = "dispatchLedgerId")
	private Long	dispatchLedgerId;

	@Column(name = "tcktIncmSeatCount")
	private Long	tcktIncmSeatCount;
	
	@Column(name = "ticketIncomeApiId")
	private Long	ticketIncomeApiId;
	
	@Column(name = "netIncomeAmount")
	private Double netIncomeAmount;

	@Column(name = "remark")
	private String remark;
	
	@Column(name = "tripsheetIncomeDate")
	private Date tripsheetIncomeDate;
	
	@Column(name = "routeID")
	private Integer routeID;
	
	@Column(name = "commission")
	private Double commission;
	
	@Column(name = "gst")
	private Double gst;
	
	@Column(name = "isDriverExcluded", nullable = false)
	private boolean	isDriverExcluded;
	
	@Column(name="paymentTypeId")
	private Short paymentTypeId;
	
	@Column(name="billSelectionId")
	private Short billSelectionId;
	
	//routeID
	public Integer getRouteID() {
		return routeID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public Date getTripsheetIncomeDate() {
		return tripsheetIncomeDate;
	}

	public void setTripsheetIncomeDate(Date tripsheetIncomeDate) {
		this.tripsheetIncomeDate = tripsheetIncomeDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public TripSheetIncome() {
		super();
	}

	public TripSheetIncome( Double incomeAmount,  String incomeRefence,
			Integer companyId) {
		super();
		this.incomeAmount = incomeAmount;
		this.incomeRefence = incomeRefence;
		this.companyId = companyId;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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
	 * @return the incomePlaceId
	 */
	public Integer getIncomePlaceId() {
		return incomePlaceId;
	}

	/**
	 * @param incomePlaceId the incomePlaceId to set
	 */
	public void setIncomePlaceId(Integer incomePlaceId) {
		this.incomePlaceId = incomePlaceId;
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

	/**
	 * @return the tripsheet
	 */
	public TripSheet getTripsheet() {
		return tripsheet;
	}

	/**
	 * @param tripsheet
	 *            the tripsheet to set
	 */
	public void setTripsheet(TripSheet tripsheet) {
		this.tripsheet = tripsheet;
	}

	public Long getlHPVDetailsId() {
		return lHPVDetailsId;
	}

	public void setlHPVDetailsId(Long lHPVDetailsId) {
		this.lHPVDetailsId = lHPVDetailsId;
	}
	
	public short getIncomeFixedId() {
		return incomeFixedId;
	}

	public void setIncomeFixedId(short incomeFixedId) {
		this.incomeFixedId = incomeFixedId;
	}

	public Long getDispatchLedgerId() {
		return dispatchLedgerId;
	}

	public void setDispatchLedgerId(Long dispatchLedgerId) {
		this.dispatchLedgerId = dispatchLedgerId;
	}

	public Long getTcktIncmSeatCount() {
		return tcktIncmSeatCount;
	}

	public void setTcktIncmSeatCount(Long tcktIncmSeatCount) {
		this.tcktIncmSeatCount = tcktIncmSeatCount;
	}

	public Long getTicketIncomeApiId() {
		return ticketIncomeApiId;
	}

	public void setTicketIncomeApiId(Long ticketIncomeApiId) {
		this.ticketIncomeApiId = ticketIncomeApiId;
	}
	
	public Double getNetIncomeAmount() {
		return netIncomeAmount;
	}

	public void setNetIncomeAmount(Double netIncomeAmount) {
		this.netIncomeAmount = netIncomeAmount;
	}


	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	public boolean isDriverExcluded() {
		return isDriverExcluded;
	}

	public void setDriverExcluded(boolean isDriverExcluded) {
		this.isDriverExcluded = isDriverExcluded;
	}

	public Short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	
	public Short getBillSelectionId() {
		return billSelectionId;
	}

	public void setBillSelectionId(Short billSelectionId) {
		this.billSelectionId = billSelectionId;
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
		result = prime * result + ((incomeRefence == null) ? 0 : incomeRefence.hashCode());
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
		TripSheetIncome other = (TripSheetIncome) obj;
		if (incomeRefence == null) {
			if (other.incomeRefence != null)
				return false;
		} else if (!incomeRefence.equals(other.incomeRefence))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TripSheetIncome [tripincomeID=" + tripincomeID + ", incomeId=" + incomeId + ", incomeAmount="
				+ incomeAmount + ", incomePlaceId=" + incomePlaceId + ", incomeRefence=" + incomeRefence
				+ ", incomeCollectedById=" + incomeCollectedById + ", incomeFixedId=" + incomeFixedId + ", companyId="
				+ companyId + ", createdById=" + createdById + ", created=" + created + ", tripsheet=" + tripsheet
				+ ", markForDelete=" + markForDelete + ", lHPVDetailsId=" + lHPVDetailsId + ", dispatchLedgerId="
				+ dispatchLedgerId + ", tcktIncmSeatCount=" + tcktIncmSeatCount + ", ticketIncomeApiId="
				+ ticketIncomeApiId + ", netIncomeAmount=" + netIncomeAmount + ", remark=" + remark
				+ ", tripsheetIncomeDate=" + tripsheetIncomeDate + ", routeID=" + routeID + ", commission=" + commission
				+ ", gst=" + gst + ", isDriverExcluded=" + isDriverExcluded + ", paymentTypeId=" + paymentTypeId
				+ ", billSelectionId=" + billSelectionId + "]";
	}

}