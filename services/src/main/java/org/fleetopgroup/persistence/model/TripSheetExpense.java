package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
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
@Table(name = "tripsheetexpense")
public class TripSheetExpense implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripExpenseID")
	private Long tripExpenseID;

	@Column(name = "expenseId", nullable = false)
	private Integer expenseId;

	@Column(name = "expenseAmount")
	private Double expenseAmount;

	@Column(name = "expensePlaceId", nullable = false)
	private Integer expensePlaceId;

	@Column(name = "expenseRefence", length = 250)
	private String expenseRefence;

	@Column(name = "expenseFixedId", nullable = false)
	private short expenseFixedId;

	@Column(name = "fuel_id")
	private Long fuel_id;

	@Column(name = "DHID")
	private Long DHID;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", nullable = false, updatable = false)
	private Long createdById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "isCredit", nullable = false)
	private boolean isCredit;
	
	@Column(name = "vendorId")
	private Integer vendorId;
	
	@Column(name = "tallyCompanyId")
	private Long tallyCompanyId;
	
	@Column(name = "remark", length = 250)
	private String remark;

	@ManyToOne
	@JoinColumn(name = "tripSheetID")
	@JsonBackReference
	private TripSheet tripsheet;
	
	@Column(name = "voucherDate")
	private Date voucherDate;
	
	@Column(name = "transactionId")
	private Long	transactionId;
	
	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;
	
	@Basic(optional = false)
	@Column(name = "tripSheetExpense_document", nullable = false)
	private boolean tripSheetExpense_document = false;

	@Column(name = "tripSheetExpense_document_id")
	private Long tripSheetExpense_document_id;
	
	@Column(name = "lHPVDetailsId")
	private Long lHPVDetailsId;
	
	@Column(name = "pfAmount")
	private Double	pfAmount;
	
	@Column(name = "esiAmount")
	private	Double	esiAmount;
	
	@Column(name = "balanceAmount")
	private Double	balanceAmount;
	
	@Column(name = "driverId")
	private Integer driverId;
	
	@Column(name = "paymentTypeId")
	private Short paymentTypeId;
	
	@Column(name = "paidDate")
	private Date paidDate;
	
	public TripSheetExpense() {
		super();
	}

	
	public TripSheetExpense(Integer expenseId, Double expenseAmount, Integer expensePlaceId, String expenseRefence,  short expenseFixedId
			) {
		super();
		this.expenseId = expenseId;
		this.expenseAmount = expenseAmount;
		this.expensePlaceId = expensePlaceId;
		this.expenseRefence = expenseRefence;
		this.expenseFixedId = expenseFixedId;
	}


	public TripSheetExpense( Double expenseAmount,  String expenseRefence, Integer companyId) {
		super();
		this.expenseAmount = expenseAmount;
		this.expenseRefence = expenseRefence;
		this.companyId = companyId;
	}

	public TripSheetExpense( Double expenseAmount,  String expenseRefence
			) {
		super();
		this.expenseAmount = expenseAmount;
		this.expenseRefence = expenseRefence;
	}

	

	

	/**
	 * @return the tripExpenseID
	 */
	public Long getTripExpenseID() {
		return tripExpenseID;
	}

	

	/**
	 * @param tripExpenseID
	 *            the tripExpenseID to set
	 */
	public void setTripExpenseID(Long tripExpenseID) {
		this.tripExpenseID = tripExpenseID;
	}

	/**
	 * @return the expenseAmount
	 */
	public Double getExpenseAmount() {
		return expenseAmount;
	}

	/**
	 * @param expenseAmount
	 *            the expenseAmount to set
	 */
	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	

	/**
	 * @return the expenseRefence
	 */
	public String getExpenseRefence() {
		return expenseRefence;
	}

	/**
	 * @param expenseRefence
	 *            the expenseRefence to set
	 */
	public void setExpenseRefence(String expenseRefence) {
		this.expenseRefence = expenseRefence;
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

	public Long getFuel_id() {
		return fuel_id;
	}

	public void setFuel_id(Long fuel_id) {
		this.fuel_id = fuel_id;
	}

	public Long getDHID() {
		return DHID;
	}

	public void setDHID(Long dHID) {
		DHID = dHID;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the expenseId
	 */
	public Integer getExpenseId() {
		return expenseId;
	}

	/**
	 * @param expenseId the expenseId to set
	 */
	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	/**
	 * @return the expensePlaceId
	 */
	public Integer getExpensePlaceId() {
		return expensePlaceId;
	}

	/**
	 * @param expensePlaceId the expensePlaceId to set
	 */
	public void setExpensePlaceId(Integer expensePlaceId) {
		this.expensePlaceId = expensePlaceId;
	}

	/**
	 * @return the expenseFixedId
	 */
	public short getExpenseFixedId() {
		return expenseFixedId;
	}

	/**
	 * @param expenseFixedId the expenseFixedId to set
	 */
	public void setExpenseFixedId(short expenseFixedId) {
		this.expenseFixedId = expenseFixedId;
	}

	/*public Long getlHPVDetailsId() {
		return lHPVDetailsId;
	}


	public void setlHPVDetailsId(Long lHPVDetailsId) {
		this.lHPVDetailsId = lHPVDetailsId;
	}*/


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

	

	public boolean isCredit() {
		return isCredit;
	}


	public void setCredit(boolean isCredit) {
		this.isCredit = isCredit;
	}


	public Integer getVendorId() {
		return vendorId;
	}


	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}


	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Date getVoucherDate() {
		return voucherDate;
	}


	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}


	public Long getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}


	public boolean isPendingForTally() {
		return isPendingForTally;
	}


	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	public boolean isTripSheetExpense_document() {
		return tripSheetExpense_document;
	}


	public void setTripSheetExpense_document(boolean tripSheetExpense_document) {
		this.tripSheetExpense_document = tripSheetExpense_document;
	}


	public Long getTripSheetExpense_document_id() {
		return tripSheetExpense_document_id;
	}


	public void setTripSheetExpense_document_id(Long tripSheetExpense_document_id) {
		this.tripSheetExpense_document_id = tripSheetExpense_document_id;
	}


	public Long getlHPVDetailsId() {
		return lHPVDetailsId;
	}


	public void setlHPVDetailsId(Long lHPVDetailsId) {
		this.lHPVDetailsId = lHPVDetailsId;
	}


	public Double getPfAmount() {
		return pfAmount;
	}


	public void setPfAmount(Double pfAmount) {
		this.pfAmount = pfAmount;
	}


	public Double getEsiAmount() {
		return esiAmount;
	}


	public void setEsiAmount(Double esiAmount) {
		this.esiAmount = esiAmount;
	}


	public Double getBalanceAmount() {
		return balanceAmount;
	}


	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}


	public Integer getDriverId() {
		return driverId;
	}


	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}


	public Short getPaymentTypeId() {
		return paymentTypeId;
	}


	public void setPaymentTypeId(Short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}


	public Date getPaidDate() {
		return paidDate;
	}


	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
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
		result = prime * result + ((expenseRefence == null) ? 0 : expenseRefence.hashCode());
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
		TripSheetExpense other = (TripSheetExpense) obj;
		if (expenseRefence == null) {
			if (other.expenseRefence != null)
				return false;
		} else if (!expenseRefence.equals(other.expenseRefence))
			return false;
		return true;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripSheetExpense [tripExpenseID=");
		builder.append(tripExpenseID);
		builder.append(", expenseId=");
		builder.append(expenseId);
		builder.append(", expenseAmount=");
		builder.append(expenseAmount);
		builder.append(", expensePlaceId=");
		builder.append(expensePlaceId);
		builder.append(", expenseRefence=");
		builder.append(expenseRefence);
		builder.append(", expenseFixedId=");
		builder.append(expenseFixedId);
		builder.append(", fuel_id=");
		builder.append(fuel_id);
		builder.append(", DHID=");
		builder.append(DHID);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", created=");
		builder.append(created);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", tallyCompanyId=");
		builder.append(tallyCompanyId);
		builder.append(", paymentTypeId=");
		builder.append(paymentTypeId);
		builder.append("]");
		return builder.toString();
	}

}