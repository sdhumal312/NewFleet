/**
 * 
 */
package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author fleetop
 *
 */
@Entity
@Table(name = "InventoryTyreRetread")
public class InventoryTyreRetread implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value for the ITYRE_ID Layout Id field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRID")
	private Long TRID;
	
	@Column(name = "TRNUMBER",nullable = false)
	private Long TRNUMBER;

	/** The value for the TR_OPEN_DATE field */
	@Basic(optional = true)
	@Column(name = "TR_OPEN_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date TR_OPEN_DATE;

	/** The value for the TR_REQUIRED_DATE field */
	@Basic(optional = true)
	@Column(name = "TR_REQUIRED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date TR_REQUIRED_DATE;

	/** The value for the VENDOR_ID field */
	@Column(name = "TR_VENDOR_ID")
	private Integer TR_VENDOR_ID;

	@Column(name = "TR_PAYMENT_TYPE_ID")
	private short TR_PAYMENT_TYPE_ID;

	/** The value for the PAYMENT_NUMBER field */
	@Column(name = "TR_PAYMENT_NUMBER", length = 25)
	private String TR_PAYMENT_NUMBER;

	
	@Column(name = "TR_VENDOR_PAYMODE_STATUS_ID")
	private short TR_VENDOR_PAYMODE_STATUS_ID;

	/** The value for the TR_APPROVAL_ID field */
	@Column(name = "TR_APPROVAL_ID")
	private Long TR_APPROVAL_ID;
	
	@Column(name = "paidAmount")
	private Double paidAmount;
	
	@Column(name = "balanceAmount")
	private Double balanceAmount;
	
	@Column(name = "discountAmount")
	private Double discountAmount;

	/** The value for the VENDOR_PAYMODE_DATE DATE field */
	@Basic(optional = true)
	@Column(name = "TR_VENDOR_PAYMODE_DATE", updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date TR_VENDOR_PAYMODE_DATE;

	/** The value for the TR_QUOTE_NO field */
	@Column(name = "TR_QUOTE_NO", length = 25)
	private String TR_QUOTE_NO;

	/** The value for the TR_MANUAL_NO field */
	@Column(name = "TR_MANUAL_NO", length = 25)
	private String TR_MANUAL_NO;

	/** The value for the TYRE_AMOUNT field */
	@Column(name = "TR_AMOUNT")
	private Double TR_AMOUNT;

	/** The value for the TYRE_ROUNT_AMOUNT field */
	@Column(name = "TR_ROUNT_AMOUNT")
	private Double TR_ROUNT_AMOUNT;

	/** The value for the TR_SEND_TYRE field */
	@Column(name = "TR_SEND_TYRE")
	private Integer TR_SEND_TYRE;

	/** The value for the TR_RECEIVE_TYRE field */
	@Column(name = "TR_RECEIVE_TYRE")
	private Integer TR_RECEIVE_TYRE;

	/** The value for the TYRE RETREAD TO main Tyre ID field */
	@OneToMany(mappedBy = "inventoryTyreRetread", fetch = FetchType.EAGER)
	private Set<InventoryTyreRetreadAmount> inventoryTyreRetreadAmount;

	/** The value for the TR_DESCRIPTION field */
	@Column(name = "TR_DESCRIPTION", length = 1000)
	private String TR_DESCRIPTION;

	/** The value for the TR_INVOICE_NUMBER field */
	@Column(name = "TR_INVOICE_NUMBER", length = 25)
	private String TR_INVOICE_NUMBER;

	/** The value for the TR_INVOICE_ DATE field */
	@Basic(optional = true)
	@Column(name = "TR_INVOICE_DATE", updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date TR_INVOICE_DATE;

	/** The value for the TR_RE_DESCRIPTION field */
	@Column(name = "TR_RE_DESCRIPTION", length = 1000)
	private String TR_RE_DESCRIPTION;

	
	@Column(name = "TR_STATUS_ID")
	private short TR_STATUS_ID;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer	COMPANY_ID;

	
	@Column(name = "CREATEDBYID", updatable = false)
	private Long CREATEDBYID;

	
	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "LASTUPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date LASTUPDATED_DATE;
	
	
	@Column(name = "expectedPaymentDate")
	private Timestamp	expectedPaymentDate;

	public InventoryTyreRetread() {
		super();
	}

	public InventoryTyreRetread(Long tRID, Long tRNUMBER, Date tR_OPEN_DATE, Date tR_REQUIRED_DATE, Integer tR_VENDOR_ID,
			String tR_QUOTE_NO, String tR_MANUAL_NO, Double tR_AMOUNT,
			Integer tR_SEND_TYRE, Integer tR_RECEIVE_TYRE,  boolean MarkForDelete,
			Date cREATED_DATE, Integer cOMPANY_ID, short typeOfPaymentId, Double paidAmoount,Double balanceAmount,Double discountAmount) {
		super();
		TRID = tRID;
		TRNUMBER = tRNUMBER;
		TR_OPEN_DATE = tR_OPEN_DATE;
		TR_REQUIRED_DATE = tR_REQUIRED_DATE;
		TR_VENDOR_ID = tR_VENDOR_ID;
		TR_QUOTE_NO = tR_QUOTE_NO;
		TR_MANUAL_NO = tR_MANUAL_NO;
		TR_AMOUNT = tR_AMOUNT;
		TR_SEND_TYRE = tR_SEND_TYRE;
		TR_RECEIVE_TYRE = tR_RECEIVE_TYRE;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		COMPANY_ID	= cOMPANY_ID;
		this.paidAmount = paidAmoount;
		this.balanceAmount = balanceAmount;
		this.discountAmount = discountAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	/**
	 * @return the inventoryTyreRetreadAmount
	 */
	public Set<InventoryTyreRetreadAmount> getInventoryTyreRetreadAmount() {
		return inventoryTyreRetreadAmount;
	}

	/**
	 * @param inventoryTyreRetreadAmount
	 *            the inventoryTyreRetreadAmount to set
	 */
	public void setInventoryTyreRetreadAmount(Set<InventoryTyreRetreadAmount> inventoryTyreRetreadAmount) {
		this.inventoryTyreRetreadAmount = inventoryTyreRetreadAmount;
	}

	/**
	 * @return the tRID
	 */
	public Long getTRID() {
		return TRID;
	}

	/**
	 * @param tRID
	 *            the tRID to set
	 */
	public void setTRID(Long tRID) {
		TRID = tRID;
	}

	public Long getTRNUMBER() {
		return TRNUMBER;
	}

	public void setTRNUMBER(Long tRNUMBER) {
		TRNUMBER = tRNUMBER;
	}

	/**
	 * @return the tR_OPEN_DATE
	 */
	public Date getTR_OPEN_DATE() {
		return TR_OPEN_DATE;
	}

	/**
	 * @param tR_OPEN_DATE
	 *            the tR_OPEN_DATE to set
	 */
	public void setTR_OPEN_DATE(Date tR_OPEN_DATE) {
		TR_OPEN_DATE = tR_OPEN_DATE;
	}

	/**
	 * @return the tR_REQUIRED_DATE
	 */
	public Date getTR_REQUIRED_DATE() {
		return TR_REQUIRED_DATE;
	}

	/**
	 * @param tR_REQUIRED_DATE
	 *            the tR_REQUIRED_DATE to set
	 */
	public void setTR_REQUIRED_DATE(Date tR_REQUIRED_DATE) {
		TR_REQUIRED_DATE = tR_REQUIRED_DATE;
	}

	/**
	 * @return the tR_VENDOR_ID
	 */
	public Integer getTR_VENDOR_ID() {
		return TR_VENDOR_ID;
	}

	/**
	 * @param tR_VENDOR_ID
	 *            the tR_VENDOR_ID to set
	 */
	public void setTR_VENDOR_ID(Integer tR_VENDOR_ID) {
		TR_VENDOR_ID = tR_VENDOR_ID;
	}

	
	/**
	 * @return the tR_QUOTE_NO
	 */
	public String getTR_QUOTE_NO() {
		return TR_QUOTE_NO;
	}

	/**
	 * @param tR_QUOTE_NO
	 *            the tR_QUOTE_NO to set
	 */
	public void setTR_QUOTE_NO(String tR_QUOTE_NO) {
		TR_QUOTE_NO = tR_QUOTE_NO;
	}

	/**
	 * @return the tR_MANUAL_NO
	 */
	public String getTR_MANUAL_NO() {
		return TR_MANUAL_NO;
	}

	/**
	 * @param tR_MANUAL_NO
	 *            the tR_MANUAL_NO to set
	 */
	public void setTR_MANUAL_NO(String tR_MANUAL_NO) {
		TR_MANUAL_NO = tR_MANUAL_NO;
	}

	/**
	 * @return the tR_AMOUNT
	 */
	public Double getTR_AMOUNT() {
		return TR_AMOUNT;
	}

	/**
	 * @param tR_AMOUNT
	 *            the tR_AMOUNT to set
	 */
	public void setTR_AMOUNT(Double tR_AMOUNT) {
		TR_AMOUNT = tR_AMOUNT;
	}

	/**
	 * @return the tR_SEND_TYRE
	 */
	public Integer getTR_SEND_TYRE() {
		return TR_SEND_TYRE;
	}

	/**
	 * @param tR_SEND_TYRE
	 *            the tR_SEND_TYRE to set
	 */
	public void setTR_SEND_TYRE(Integer tR_SEND_TYRE) {
		TR_SEND_TYRE = tR_SEND_TYRE;
	}

	/**
	 * @return the tR_RECEIVE_TYRE
	 */
	public Integer getTR_RECEIVE_TYRE() {
		return TR_RECEIVE_TYRE;
	}

	/**
	 * @param tR_RECEIVE_TYRE
	 *            the tR_RECEIVE_TYRE to set
	 */
	public void setTR_RECEIVE_TYRE(Integer tR_RECEIVE_TYRE) {
		TR_RECEIVE_TYRE = tR_RECEIVE_TYRE;
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
	 * @return the cREATED_DATE
	 */
	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}

	/**
	 * @param cREATED_DATE
	 *            the cREATED_DATE to set
	 */
	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the tR_ROUNT_AMOUNT
	 */
	public Double getTR_ROUNT_AMOUNT() {
		return TR_ROUNT_AMOUNT;
	}

	/**
	 * @param tR_ROUNT_AMOUNT
	 *            the tR_ROUNT_AMOUNT to set
	 */
	public void setTR_ROUNT_AMOUNT(Double tR_ROUNT_AMOUNT) {
		TR_ROUNT_AMOUNT = tR_ROUNT_AMOUNT;
	}

	/**
	 * @return the tR_RE_DESCRIPTION
	 */
	public String getTR_RE_DESCRIPTION() {
		return TR_RE_DESCRIPTION;
	}

	/**
	 * @param tR_RE_DESCRIPTION
	 *            the tR_RE_DESCRIPTION to set
	 */
	public void setTR_RE_DESCRIPTION(String tR_RE_DESCRIPTION) {
		TR_RE_DESCRIPTION = tR_RE_DESCRIPTION;
	}

	
	/**
	 * @return the tR_PAYMENT_NUMBER
	 */
	public String getTR_PAYMENT_NUMBER() {
		return TR_PAYMENT_NUMBER;
	}

	/**
	 * @param tR_PAYMENT_NUMBER
	 *            the tR_PAYMENT_NUMBER to set
	 */
	public void setTR_PAYMENT_NUMBER(String tR_PAYMENT_NUMBER) {
		TR_PAYMENT_NUMBER = tR_PAYMENT_NUMBER;
	}

	

	/**
	 * @return the tR_VENDOR_PAYMODE_DATE
	 */
	public Date getTR_VENDOR_PAYMODE_DATE() {
		return TR_VENDOR_PAYMODE_DATE;
	}

	/**
	 * @param tR_VENDOR_PAYMODE_DATE
	 *            the tR_VENDOR_PAYMODE_DATE to set
	 */
	public void setTR_VENDOR_PAYMODE_DATE(Date tR_VENDOR_PAYMODE_DATE) {
		TR_VENDOR_PAYMODE_DATE = tR_VENDOR_PAYMODE_DATE;
	}

	/**
	 * @return the tR_INVOICE_NUMBER
	 */
	public String getTR_INVOICE_NUMBER() {
		return TR_INVOICE_NUMBER;
	}

	/**
	 * @param tR_INVOICE_NUMBER
	 *            the tR_INVOICE_NUMBER to set
	 */
	public void setTR_INVOICE_NUMBER(String tR_INVOICE_NUMBER) {
		TR_INVOICE_NUMBER = tR_INVOICE_NUMBER;
	}

	/**
	 * @return the tR_INVOICE_DATE
	 */
	public Date getTR_INVOICE_DATE() {
		return TR_INVOICE_DATE;
	}

	/**
	 * @param tR_INVOICE_DATE
	 *            the tR_INVOICE_DATE to set
	 */
	public void setTR_INVOICE_DATE(Date tR_INVOICE_DATE) {
		TR_INVOICE_DATE = tR_INVOICE_DATE;
	}

	/**
	 * @return the tR_APPROVAL_ID
	 */
	public Long getTR_APPROVAL_ID() {
		return TR_APPROVAL_ID;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}


	/**
	 * @return the lASTUPDATED_DATE
	 */
	public Date getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	/**
	 * @param lASTUPDATED_DATE
	 *            the lASTUPDATED_DATE to set
	 */
	public void setLASTUPDATED_DATE(Date lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	/**
	 * @param tR_APPROVAL_ID
	 *            the tR_APPROVAL_ID to set
	 */
	public void setTR_APPROVAL_ID(Long tR_APPROVAL_ID) {
		TR_APPROVAL_ID = tR_APPROVAL_ID;
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0) {
				builder.append(", ");
			}
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the tR_DESCRIPTION
	 */
	public String getTR_DESCRIPTION() {
		return TR_DESCRIPTION;
	}

	/**
	 * @param tR_DESCRIPTION
	 *            the tR_DESCRIPTION to set
	 */
	public void setTR_DESCRIPTION(String tR_DESCRIPTION) {
		TR_DESCRIPTION = tR_DESCRIPTION;
	}

	/**
	 * @return the tR_PAYMENT_TYPE_ID
	 */
	public short getTR_PAYMENT_TYPE_ID() {
		return TR_PAYMENT_TYPE_ID;
	}

	/**
	 * @param tR_PAYMENT_TYPE_ID the tR_PAYMENT_TYPE_ID to set
	 */
	public void setTR_PAYMENT_TYPE_ID(short tR_PAYMENT_TYPE_ID) {
		TR_PAYMENT_TYPE_ID = tR_PAYMENT_TYPE_ID;
	}

	/**
	 * @return the tR_VENDOR_PAYMODE_STATUS_ID
	 */
	public short getTR_VENDOR_PAYMODE_STATUS_ID() {
		return TR_VENDOR_PAYMODE_STATUS_ID;
	}

	/**
	 * @param tR_VENDOR_PAYMODE_STATUS_ID the tR_VENDOR_PAYMODE_STATUS_ID to set
	 */
	public void setTR_VENDOR_PAYMODE_STATUS_ID(short tR_VENDOR_PAYMODE_STATUS_ID) {
		TR_VENDOR_PAYMODE_STATUS_ID = tR_VENDOR_PAYMODE_STATUS_ID;
	}

	/**
	 * @return the tR_STATUS_ID
	 */
	public short getTR_STATUS_ID() {
		return TR_STATUS_ID;
	}

	/**
	 * @param tR_STATUS_ID the tR_STATUS_ID to set
	 */
	public void setTR_STATUS_ID(short tR_STATUS_ID) {
		TR_STATUS_ID = tR_STATUS_ID;
	}

	/**
	 * @return the cREATEDBYID
	 */
	public Long getCREATEDBYID() {
		return CREATEDBYID;
	}

	/**
	 * @param cREATEDBYID the cREATEDBYID to set
	 */
	public void setCREATEDBYID(Long cREATEDBYID) {
		CREATEDBYID = cREATEDBYID;
	}

	/**
	 * @return the lASTMODIFIEDBYID
	 */
	public Long getLASTMODIFIEDBYID() {
		return LASTMODIFIEDBYID;
	}

	/**
	 * @param lASTMODIFIEDBYID the lASTMODIFIEDBYID to set
	 */
	public void setLASTMODIFIEDBYID(Long lASTMODIFIEDBYID) {
		LASTMODIFIEDBYID = lASTMODIFIEDBYID;
	}

	public Timestamp getExpectedPaymentDate() {
		return expectedPaymentDate;
	}

	public void setExpectedPaymentDate(Timestamp expectedPaymentDate) {
		this.expectedPaymentDate = expectedPaymentDate;
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
		result = prime * result + ((TRID == null) ? 0 : TRID.hashCode());
		result = prime * result + ((TR_VENDOR_ID == null) ? 0 : TR_VENDOR_ID.hashCode());
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
		InventoryTyreRetread other = (InventoryTyreRetread) obj;
		if (TRID == null) {
			if (other.TRID != null)
				return false;
		} else if (!TRID.equals(other.TRID))
			return false;
		if (TR_VENDOR_ID == null) {
			if (other.TR_VENDOR_ID != null)
				return false;
		} else if (!TR_VENDOR_ID.equals(other.TR_VENDOR_ID))
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
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryTyreRetread [TRID=");
		builder.append(TRID);
		builder.append(", TRNUMBER=");
		builder.append(TRNUMBER);
		builder.append(", TR_OPEN_DATE=");
		builder.append(TR_OPEN_DATE);
		builder.append(", TR_REQUIRED_DATE=");
		builder.append(TR_REQUIRED_DATE);
		builder.append(", TR_VENDOR_ID=");
		builder.append(TR_VENDOR_ID);
		builder.append(", TR_PAYMENT_NUMBER=");
		builder.append(TR_PAYMENT_NUMBER);
		builder.append(", TR_APPROVAL_ID=");
		builder.append(TR_APPROVAL_ID);
		builder.append(", TR_VENDOR_PAYMODE_DATE=");
		builder.append(TR_VENDOR_PAYMODE_DATE);
		builder.append(", TR_QUOTE_NO=");
		builder.append(TR_QUOTE_NO);
		builder.append(", TR_MANUAL_NO=");
		builder.append(TR_MANUAL_NO);
		builder.append(", TR_AMOUNT=");
		builder.append(TR_AMOUNT);
		builder.append(", TR_ROUNT_AMOUNT=");
		builder.append(TR_ROUNT_AMOUNT);
		builder.append(", TR_SEND_TYRE=");
		builder.append(TR_SEND_TYRE);
		builder.append(", TR_RECEIVE_TYRE=");
		builder.append(TR_RECEIVE_TYRE);
		builder.append(", inventoryTyreRetreadAmount=");
		builder.append(inventoryTyreRetreadAmount != null ? toString(inventoryTyreRetreadAmount, maxLen) : null);
		builder.append(", TR_DESCRIPTION=");
		builder.append(TR_DESCRIPTION);
		builder.append(", TR_INVOICE_NUMBER=");
		builder.append(TR_INVOICE_NUMBER);
		builder.append(", TR_INVOICE_DATE=");
		builder.append(TR_INVOICE_DATE);
		builder.append(", TR_RE_DESCRIPTION=");
		builder.append(TR_RE_DESCRIPTION);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", paidAmount=");
		builder.append(paidAmount);
		builder.append(", balanceAmount=");
		builder.append(balanceAmount);
		builder.append("]");
		return builder.toString();
	}

}
