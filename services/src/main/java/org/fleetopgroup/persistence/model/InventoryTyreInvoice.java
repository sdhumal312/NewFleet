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
@Table(name = "InventoryTyreInvoice")
public class InventoryTyreInvoice implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value for the Vehicle Tyre Layout Id field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ITYRE_ID")
	private Long ITYRE_ID;
	
	@Column(name = "ITYRE_NUMBER")
	private Long ITYRE_NUMBER;

	
	@Column(name = "WAREHOUSE_LOCATION_ID")
	private Integer WAREHOUSE_LOCATION_ID;
	
	/** The value for the PO_NUMBER field */
	@Column(name = "PO_NUMBER")
	private String PO_NUMBER;

	/** The value for the INVOICE_NUMBER field */
	@Column(name = "INVOICE_NUMBER", length = 25)
	private String INVOICE_NUMBER;

	/** The value for the INVOICE_ DATE field */
	@Basic(optional = false)
	@Column(name = "INVOICE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date INVOICE_DATE;

	/** The value for the INVOICE_AMOUNT field */
	@Column(name = "INVOICE_AMOUNT")
	private Double INVOICE_AMOUNT;

	/** The value for the VENDOR_ID field */
	@Column(name = "VENDOR_ID")
	private Integer VENDOR_ID;

	@Column(name = "PAYMENT_TYPE_ID")
	private short PAYMENT_TYPE_ID;

	/** The value for the PAYMENT_NUMBER field */
	@Column(name = "PAYMENT_NUMBER", length = 25)
	private String PAYMENT_NUMBER;

	
	@Column(name = "VENDOR_PAYMODE_STATUS_ID")
	private short VENDOR_PAYMODE_STATUS_ID;


	/** The value for the TYRE_APPROVAL_ID field */
	@Column(name = "TYRE_APPROVAL_ID")
	private Long TYRE_APPROVAL_ID;

	/** The value for the VENDOR_PAYMODE_DATE DATE field */
	@Basic(optional = true)
	@Column(name = "VENDOR_PAYMODE_DATE", updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date VENDOR_PAYMODE_DATE;
	
	@Column(name = "paidAmount")
	private Double paidAmount;
	
	@Column(name = "balanceAmount")
	private Double balanceAmount;
	
	@Column(name = "discountAmount")
	private Double discountAmount;
	
	/** The value for the TOTALTYRE_AMOUNT field */
	@Column(name = "TOTALTYRE_AMOUNT")
	private Double TOTALTYRE_AMOUNT;
	
	@Column(name = "COMPANY_ID")
	private Integer	COMPANY_ID;

	/** The value for the InventoryTyreInvoice field */
	@OneToMany(mappedBy = "InventoryTyreInvoice")
	private Set<InventoryTyreAmount> InventoryTyreAmount;

	/** The value for the DESCRIPTION field */
	@Column(name = "DESCRIPTION", length = 250)
	private String DESCRIPTION;

	
	@Column(name = "CREATEDBYID", updatable = false)
	private Long CREATEDBYID;

	
	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the CREATED_DATE DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;

	/** The value for the LASTUPDATED_DATE DATE field */
	@Basic(optional = false)
	@Column(name = "LASTUPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date LASTUPDATED_DATE;
	
	@Column(name = "anyTyreNumberAsign", nullable=false, columnDefinition = "bit not null default 1 ")
	private boolean	anyTyreNumberAsign;
	
	@Column(name = "STATUS_OF_TYRE", nullable = false)
	private short STATUS_OF_TYRE;
	
	@Basic(optional = false)
	@Column(name = "tyre_document", nullable = false)
	private boolean tyre_document = false;

	@Column(name = "tyre_document_id")
	private Long tyre_document_id;
	
	@Column(name = "tallyCompanyId")
	private Long tallyCompanyId;
	
	@Column(name = "expectedPaymentDate")
	private Timestamp	expectedPaymentDate;
	
	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;
	
	@Column(name = "subLocationId")
	private Integer	subLocationId;
	
	public Long getTyre_document_id() {
		return tyre_document_id;
	}

	public void setTyre_document_id(Long tyre_document_id) {
		this.tyre_document_id = tyre_document_id;
	}

	public InventoryTyreInvoice() {
		super();
	}

	public InventoryTyreInvoice(Long iTYRE_ID, Long iTYRE_NUMBER, String pO_NUMBER, String iNVOICE_NUMBER,
			Date iNVOICE_DATE, Double iNVOICE_AMOUNT, Integer vENDOR_ID,
			String pAYMENT_NUMBER, String vENDOR_PAYMODE_STATUS, Long tYRE_APPROVAL_ID, Date vENDOR_PAYMODE_DATE,
			Double tOTALTYRE_AMOUNT, Integer cOMPANY_ID,
			Set<org.fleetopgroup.persistence.model.InventoryTyreAmount> inventoryTyreAmount, String dESCRIPTION,
			boolean MarkForDelete, Date cREATED_DATE, Date lASTUPDATED_DATE,short typeOfPaymentId, Double paidAmoount,Double balanceAmount, Double discountAmount,
			boolean tyre_document, Long tyre_document_id) {
		super();
		ITYRE_ID = iTYRE_ID;
		ITYRE_NUMBER = iTYRE_NUMBER;
		PO_NUMBER = pO_NUMBER;
		INVOICE_NUMBER = iNVOICE_NUMBER;
		INVOICE_DATE = iNVOICE_DATE;
		INVOICE_AMOUNT = iNVOICE_AMOUNT;
		VENDOR_ID = vENDOR_ID;
		PAYMENT_NUMBER = pAYMENT_NUMBER;
		TYRE_APPROVAL_ID = tYRE_APPROVAL_ID;
		VENDOR_PAYMODE_DATE = vENDOR_PAYMODE_DATE;
		TOTALTYRE_AMOUNT = tOTALTYRE_AMOUNT;
		COMPANY_ID = cOMPANY_ID;
		InventoryTyreAmount = inventoryTyreAmount;
		DESCRIPTION = dESCRIPTION;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
		paidAmount = paidAmoount;
		this.balanceAmount = balanceAmount;
		this.discountAmount = discountAmount;
		this.tyre_document=tyre_document;
		this.tyre_document_id=tyre_document_id;
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
	 * @return the iTYRE_ID
	 */
	public Long getITYRE_ID() {
		return ITYRE_ID;
	}

	/**
	 * @param iTYRE_ID
	 *            the iTYRE_ID to set
	 */
	public void setITYRE_ID(Long iTYRE_ID) {
		ITYRE_ID = iTYRE_ID;
	}

	public Long getITYRE_NUMBER() {
		return ITYRE_NUMBER;
	}


	public void setITYRE_NUMBER(Long iTYRE_NUMBER) {
		ITYRE_NUMBER = iTYRE_NUMBER;
	}

	/**
	 * @return the pO_NUMBER
	 */
	public String getPO_NUMBER() {
		return PO_NUMBER;
	}

	/**
	 * @param pO_NUMBER
	 *            the pO_NUMBER to set
	 */
	public void setPO_NUMBER(String pO_NUMBER) {
		PO_NUMBER = pO_NUMBER;
	}

	/**
	 * @return the iNVOICE_NUMBER
	 */
	public String getINVOICE_NUMBER() {
		return INVOICE_NUMBER;
	}

	/**
	 * @param iNVOICE_NUMBER
	 *            the iNVOICE_NUMBER to set
	 */
	public void setINVOICE_NUMBER(String iNVOICE_NUMBER) {
		INVOICE_NUMBER = iNVOICE_NUMBER;
	}

	/**
	 * @return the iNVOICE_DATE
	 */
	public Date getINVOICE_DATE() {
		return INVOICE_DATE;
	}

	/**
	 * @param iNVOICE_DATE
	 *            the iNVOICE_DATE to set
	 */
	public void setINVOICE_DATE(Date iNVOICE_DATE) {
		INVOICE_DATE = iNVOICE_DATE;
	}

	/**
	 * @return the iNVOICE_AMOUNT
	 */
	public Double getINVOICE_AMOUNT() {
		return INVOICE_AMOUNT;
	}

	/**
	 * @param iNVOICE_AMOUNT
	 *            the iNVOICE_AMOUNT to set
	 */
	public void setINVOICE_AMOUNT(Double iNVOICE_AMOUNT) {
		INVOICE_AMOUNT = iNVOICE_AMOUNT;
	}

	/**
	 * @return the vENDOR_ID
	 */
	public Integer getVENDOR_ID() {
		return VENDOR_ID;
	}

	/**
	 * @param vENDOR_ID
	 *            the vENDOR_ID to set
	 */
	public void setVENDOR_ID(Integer vENDOR_ID) {
		VENDOR_ID = vENDOR_ID;
	}


	/**
	 * @return the tOTALTYRE_AMOUNT
	 */
	public Double getTOTALTYRE_AMOUNT() {
		return TOTALTYRE_AMOUNT;
	}

	/**
	 * @param tOTALTYRE_AMOUNT
	 *            the tOTALTYRE_AMOUNT to set
	 */
	public void setTOTALTYRE_AMOUNT(Double tOTALTYRE_AMOUNT) {
		TOTALTYRE_AMOUNT = tOTALTYRE_AMOUNT;
	}


	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}


	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}


	/**
	 * @return the inventoryTyreAmount
	 */
	public Set<InventoryTyreAmount> getInventoryTyreAmount() {
		return InventoryTyreAmount;
	}

	/**
	 * @param inventoryTyreAmount
	 *            the inventoryTyreAmount to set
	 */
	public void setInventoryTyreAmount(Set<InventoryTyreAmount> inventoryTyreAmount) {
		InventoryTyreAmount = inventoryTyreAmount;
	}

	/**
	 * @return the dESCRIPTION
	 */
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	/**
	 * @param dESCRIPTION
	 *            the dESCRIPTION to set
	 */
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
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
	 * @return the pAYMENT_NUMBER
	 */
	public String getPAYMENT_NUMBER() {
		return PAYMENT_NUMBER;
	}

	/**
	 * @param pAYMENT_NUMBER
	 *            the pAYMENT_NUMBER to set
	 */
	public void setPAYMENT_NUMBER(String pAYMENT_NUMBER) {
		PAYMENT_NUMBER = pAYMENT_NUMBER;
	}


	/**
	 * @return the vENDOR_PAYMODE_DATE
	 */
	public Date getVENDOR_PAYMODE_DATE() {
		return VENDOR_PAYMODE_DATE;
	}

	/**
	 * @param vENDOR_PAYMODE_DATE
	 *            the vENDOR_PAYMODE_DATE to set
	 */
	public void setVENDOR_PAYMODE_DATE(Date vENDOR_PAYMODE_DATE) {
		VENDOR_PAYMODE_DATE = vENDOR_PAYMODE_DATE;
	}

	/**
	 * @return the tYRE_APPROVAL_ID
	 */
	public Long getTYRE_APPROVAL_ID() {
		return TYRE_APPROVAL_ID;
	}

	/**
	 * @param tYRE_APPROVAL_ID
	 *            the tYRE_APPROVAL_ID to set
	 */
	public void setTYRE_APPROVAL_ID(Long tYRE_APPROVAL_ID) {
		TYRE_APPROVAL_ID = tYRE_APPROVAL_ID;
	}

	/**
	 * @return the wAREHOUSE_LOCATION_ID
	 */
	public Integer getWAREHOUSE_LOCATION_ID() {
		return WAREHOUSE_LOCATION_ID;
	}


	/**
	 * @param wAREHOUSE_LOCATION_ID the wAREHOUSE_LOCATION_ID to set
	 */
	public void setWAREHOUSE_LOCATION_ID(Integer wAREHOUSE_LOCATION_ID) {
		WAREHOUSE_LOCATION_ID = wAREHOUSE_LOCATION_ID;
	}


	/**
	 * @return the pAYMENT_TYPE_ID
	 */
	public short getPAYMENT_TYPE_ID() {
		return PAYMENT_TYPE_ID;
	}


	/**
	 * @param pAYMENT_TYPE_ID the pAYMENT_TYPE_ID to set
	 */
	public void setPAYMENT_TYPE_ID(short pAYMENT_TYPE_ID) {
		PAYMENT_TYPE_ID = pAYMENT_TYPE_ID;
	}


	/**
	 * @return the vENDOR_PAYMODE_STATUS_ID
	 */
	public short getVENDOR_PAYMODE_STATUS_ID() {
		return VENDOR_PAYMODE_STATUS_ID;
	}


	/**
	 * @param vENDOR_PAYMODE_STATUS_ID the vENDOR_PAYMODE_STATUS_ID to set
	 */
	public void setVENDOR_PAYMODE_STATUS_ID(short vENDOR_PAYMODE_STATUS_ID) {
		VENDOR_PAYMODE_STATUS_ID = vENDOR_PAYMODE_STATUS_ID;
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


	public boolean isAnyTyreNumberAsign() {
		return anyTyreNumberAsign;
	}


	public void setAnyTyreNumberAsign(boolean anyTyreNumberAsign) {
		this.anyTyreNumberAsign = anyTyreNumberAsign;
	}


	public short getSTATUS_OF_TYRE() {
		return STATUS_OF_TYRE;
	}

	public void setSTATUS_OF_TYRE(short sTATUS_OF_TYRE) {
		STATUS_OF_TYRE = sTATUS_OF_TYRE;
	}

	public boolean isTyre_document() {
		return tyre_document;
	}

	public void setTyre_document(boolean tyre_document) {
		this.tyre_document = tyre_document;
	}

	public Timestamp getExpectedPaymentDate() {
		return expectedPaymentDate;
	}

	public void setExpectedPaymentDate(Timestamp expectedPaymentDate) {
		this.expectedPaymentDate = expectedPaymentDate;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((INVOICE_AMOUNT == null) ? 0 : INVOICE_AMOUNT.hashCode());
		result = prime * result + ((INVOICE_DATE == null) ? 0 : INVOICE_DATE.hashCode());
		result = prime * result + PAYMENT_TYPE_ID;
		result = prime * result + ((VENDOR_ID == null) ? 0 : VENDOR_ID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryTyreInvoice other = (InventoryTyreInvoice) obj;
		if (INVOICE_AMOUNT == null) {
			if (other.INVOICE_AMOUNT != null)
				return false;
		} else if (!INVOICE_AMOUNT.equals(other.INVOICE_AMOUNT))
			return false;
		if (INVOICE_DATE == null) {
			if (other.INVOICE_DATE != null)
				return false;
		} else if (!INVOICE_DATE.equals(other.INVOICE_DATE))
			return false;
		if (PAYMENT_TYPE_ID != other.PAYMENT_TYPE_ID)
			return false;
		if (VENDOR_ID == null) {
			if (other.VENDOR_ID != null)
				return false;
		} else if (!VENDOR_ID.equals(other.VENDOR_ID))
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
		builder.append("InventoryTyreInvoice [ITYRE_ID=");
		builder.append(ITYRE_ID);
		builder.append(", ITYRE_NUMBER=");
		builder.append(ITYRE_NUMBER);
		builder.append(", PO_NUMBER=");
		builder.append(PO_NUMBER);
		builder.append(", INVOICE_NUMBER=");
		builder.append(INVOICE_NUMBER);
		builder.append(", INVOICE_DATE=");
		builder.append(INVOICE_DATE);
		builder.append(", INVOICE_AMOUNT=");
		builder.append(INVOICE_AMOUNT);
		builder.append(", VENDOR_ID=");
		builder.append(VENDOR_ID);
		builder.append(", PAYMENT_NUMBER=");
		builder.append(PAYMENT_NUMBER);
		builder.append(", TYRE_APPROVAL_ID=");
		builder.append(TYRE_APPROVAL_ID);
		builder.append(", VENDOR_PAYMODE_DATE=");
		builder.append(VENDOR_PAYMODE_DATE);
		builder.append(", TOTALTYRE_AMOUNT=");
		builder.append(TOTALTYRE_AMOUNT);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", InventoryTyreAmount=");
		builder.append(InventoryTyreAmount != null ? toString(InventoryTyreAmount, maxLen) : null);
		builder.append(", DESCRIPTION=");
		builder.append(DESCRIPTION);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append(", paidAmount=");
		builder.append(paidAmount);
		builder.append(", balanceAmount=");
		builder.append(balanceAmount);
		builder.append(", tyre_document=");
		builder.append(tyre_document);
		builder.append(", tyre_document_id=");
		builder.append(tyre_document_id);
		builder.append(", subLocationId=");
		builder.append(subLocationId);
		
		builder.append("]");
		return builder.toString();
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

}
