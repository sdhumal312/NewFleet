/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class InventoryTyreInvoiceDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final short PAYMENT_TYPE_PO							= 0;
	public static final short VENDOR_PAYMODE_STATUS_PAID				= 1;
	public static final short VENDOR_PAYMODE_STATUS_NOTPAID				= 2;
	public static final short VENDOR_PAYMODE_STATUS_APPROVED			= 3;
	public static final short VENDOR_PAYMODE_STATUS_PARTIALLY_PAID		= 4;
	public static final short VENDOR_PAYMODE_STATUS_NEGOTIABLE_PAID		= 5;
	public static final short VENDOR_PAYMODE_STATUS_CREATE_APPROVAL		= 6;
	
	public static final String PAYMENT_TYPE_PO_NAME								= "PO";
	public static final String VENDOR_PAYMODE_STATUS_PAID_NAME					= "PAID";
	public static final String VENDOR_PAYMODE_STATUS_NOTPAID_NAME				= "NOTPAID";
	public static final String VENDOR_PAYMODE_STATUS_APPROVED_NAME				= "APPROVED";
	public static final String VENDOR_PAYMODE_STATUS_PARTIALLY_PAID_NAME		= "PARTIALLY";
	public static final String VENDOR_PAYMODE_STATUS_NEGOTIABLE_PAID_NAME		= "NEGOTIABLE";
	public static final String VENDOR_PAYMODE_STATUS_CREATE_APPROVAL_NAME		= "CREATE_APPROVAL";
	
	public static final short STATUS_OF_TYRE_NEW			= 0;
	public static final short STATUS_OF_TYRE_RETREAD		= 1;

	/** The value for the Vehicle Tyre Layout Id field */
	private Long ITYRE_ID;
	
	private Long ITYRE_NUMBER;

	/** The value for the WAREHOUSE_LOCATION field */
	private String WAREHOUSE_LOCATION;
	
	private Integer WAREHOUSE_LOCATION_ID;
	
	private short VENDOR_PAYMODE_STATUS_ID;
	
	private Long CREATEDBYID;
	
	private Long LASTMODIFIEDBYID;

	/** The value for the PO_NUMBER field */
	private String PO_NUMBER;

	/** The value for the INVOICE_NUMBER field */
	private String INVOICE_NUMBER;

	/** The value for the INVOICE_ DATE field */
	private String INVOICE_DATE;
	
	private Date INVOICE_DATE_ON;

	/** The value for the INVOICE_AMOUNT field */
	private Double INVOICE_AMOUNT;

	/** The value for the VENDOR_ID field */
	private Integer VENDOR_ID;

	/** The value for the VENDOR_NAME field */
	private String VENDOR_NAME;

	/** The value for the VENDOR_LOCATION field */
	private String VENDOR_LOCATION;

	/** The value for the PAYMENT_TYPE field */
	private String PAYMENT_TYPE;
	
	private short PAYMENT_TYPE_ID;

	/** The value for the PAYMENT_NUMBER field */
	private String PAYMENT_NUMBER;

	/** The value for the VENDOR_PAYMODE_STATUS field */
	private String VENDOR_PAYMODE_STATUS;

	/** The value for the VENDOR_PAYMODE_DATE DATE field */
	private String VENDOR_PAYMODE_DATE;

	/** The value for the TOTALTYRE_AMOUNT field */
	private Double TOTALTYRE_AMOUNT;

	/** The value for the InventoryTyreInvoice field */
	private Set<InventoryTyreAmountDto> INVENTORY_TYRE_AMOUNT;

	/** The value for the DESCRIPTION field */
	private String DESCRIPTION;

	private String CREATEDBY;

	@Column(name = "LASTMODIFIEDBY", length = 150)
	private String LASTMODIFIEDBY;

	boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	private String CREATED_DATE;
	
	private Date CREATED_DATE_ON;

	/** The value for the ISSUES_Due DATE field */
	private String LASTUPDATED_DATE;
	
	private Date LASTUPDATED_DATE_ON;
	
	private boolean	anyTyreNumberAsign;
	
	private short STATUS_OF_TYRE;
	
	private String firstName;

	private String lastName;
	private short typeOfPaymentId;
	private String typeOfPaymentStr;
	private double paidAmount;
	private double balanceAmount; 
	
	private boolean tyre_document = false;
	
	private Long tyre_document_id;
	
	private Long tallyCompanyId;
	
	private String tallyCompanyName;
	
	private Integer	subLocationId;
	
	private String	subLocation;
	
	private Long purchaseOrderId;
	
	private Long approvalId;
	
	private String approvalNumber;
	

	public InventoryTyreInvoiceDto() {
		super();
	}

	public InventoryTyreInvoiceDto(Long iTYRE_ID, String wAREHOUSE_LOCATION, String pO_NUMBER, String iNVOICE_NUMBER,
			String iNVOICE_DATE, Double iNVOICE_AMOUNT, Integer vENDOR_ID, String vENDOR_NAME, String vENDOR_LOCATION,
			Double tOTALTYRE_AMOUNT, String dESCRIPTION, String cREATEDBY, String lASTMODIFIEDBY, boolean MarkForDelete,
			String cREATED_DATE, String lASTUPDATED_DATE, String firstName, String lastName, short typeOfPaymentId,long paidAmount,long balanceAmount,
			boolean tyre_document,boolean tyre_document_id) {
		super();
		ITYRE_ID = iTYRE_ID;
		WAREHOUSE_LOCATION = wAREHOUSE_LOCATION;
		PO_NUMBER = pO_NUMBER;
		INVOICE_NUMBER = iNVOICE_NUMBER;
		INVOICE_DATE = iNVOICE_DATE;
		INVOICE_AMOUNT = iNVOICE_AMOUNT;
		VENDOR_ID = vENDOR_ID;
		VENDOR_NAME = vENDOR_NAME;
		VENDOR_LOCATION = vENDOR_LOCATION;
		TOTALTYRE_AMOUNT = tOTALTYRE_AMOUNT;
		DESCRIPTION = dESCRIPTION;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
		this.firstName = firstName;
		this.lastName = lastName;
		this.typeOfPaymentId = typeOfPaymentId;
		this.paidAmount = paidAmount;
		this.balanceAmount = balanceAmount;
		this.tyre_document=tyre_document;
	}

	public short getTypeOfPaymentId() {
		return typeOfPaymentId;
	}

	public void setTypeOfPaymentId(short typeOfPaymentId) {
		this.typeOfPaymentId = typeOfPaymentId;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = Utility.round(paidAmount,2);
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2);
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
	 * @return the wAREHOUSE_LOCATION
	 */
	public String getWAREHOUSE_LOCATION() {
		return WAREHOUSE_LOCATION;
	}

	/**
	 * @param wAREHOUSE_LOCATION
	 *            the wAREHOUSE_LOCATION to set
	 */
	public void setWAREHOUSE_LOCATION(String wAREHOUSE_LOCATION) {
		WAREHOUSE_LOCATION = wAREHOUSE_LOCATION;
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
	public String getINVOICE_DATE() {
		return INVOICE_DATE;
	}

	/**
	 * @param iNVOICE_DATE
	 *            the iNVOICE_DATE to set
	 */
	public void setINVOICE_DATE(String iNVOICE_DATE) {
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
		INVOICE_AMOUNT = Utility.round(iNVOICE_AMOUNT,2);
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
	 * @return the vENDOR_NAME
	 */
	public String getVENDOR_NAME() {
		return VENDOR_NAME;
	}

	/**
	 * @param vENDOR_NAME
	 *            the vENDOR_NAME to set
	 */
	public void setVENDOR_NAME(String vENDOR_NAME) {
		VENDOR_NAME = vENDOR_NAME;
	}

	/**
	 * @return the vENDOR_LOCATION
	 */
	public String getVENDOR_LOCATION() {
		return VENDOR_LOCATION;
	}

	/**
	 * @param vENDOR_LOCATION
	 *            the vENDOR_LOCATION to set
	 */
	public void setVENDOR_LOCATION(String vENDOR_LOCATION) {
		VENDOR_LOCATION = vENDOR_LOCATION;
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
		TOTALTYRE_AMOUNT = Utility.round(tOTALTYRE_AMOUNT,2);
	}

	/**
	 * @return the iNVENTORY_TYRE_INVOCE
	 */
	public Set<InventoryTyreAmountDto> getINVENTORY_TYRE_AMOUNT() {
		return INVENTORY_TYRE_AMOUNT;
	}

	/**
	 * @param iNVENTORY_TYRE_INVOCE
	 *            the iNVENTORY_TYRE_INVOCE to set
	 */
	public void setINVENTORY_TYRE_AMOUNT(Set<InventoryTyreAmountDto> iNVENTORY_TYRE_AMOUNT) {
		INVENTORY_TYRE_AMOUNT = iNVENTORY_TYRE_AMOUNT;
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
	 * @return the cREATEDBY
	 */
	public String getCREATEDBY() {
		return CREATEDBY;
	}

	/**
	 * @param cREATEDBY
	 *            the cREATEDBY to set
	 */
	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	/**
	 * @return the lASTMODIFIEDBY
	 */
	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	/**
	 * @param lASTMODIFIEDBY
	 *            the lASTMODIFIEDBY to set
	 */
	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
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
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	/**
	 * @param cREATED_DATE
	 *            the cREATED_DATE to set
	 */
	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the lASTUPDATED_DATE
	 */
	public String getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	/**
	 * @param lASTUPDATED_DATE
	 *            the lASTUPDATED_DATE to set
	 */
	public void setLASTUPDATED_DATE(String lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	/**
	 * @return the pAYMENT_TYPE
	 */
	public String getPAYMENT_TYPE() {
		return PAYMENT_TYPE;
	}

	/**
	 * @param pAYMENT_TYPE
	 *            the pAYMENT_TYPE to set
	 */
	public void setPAYMENT_TYPE(String pAYMENT_TYPE) {
		PAYMENT_TYPE = pAYMENT_TYPE;
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
	 * @return the vENDOR_PAYMODE_STATUS
	 */
	public String getVENDOR_PAYMODE_STATUS() {
		return VENDOR_PAYMODE_STATUS;
	}

	/**
	 * @param vENDOR_PAYMODE_STATUS
	 *            the vENDOR_PAYMODE_STATUS to set
	 */
	public void setVENDOR_PAYMODE_STATUS(String vENDOR_PAYMODE_STATUS) {
		VENDOR_PAYMODE_STATUS = vENDOR_PAYMODE_STATUS;
	}

	/**
	 * @return the vENDOR_PAYMODE_DATE
	 */
	public String getVENDOR_PAYMODE_DATE() {
		return VENDOR_PAYMODE_DATE;
	}

	/**
	 * @param vENDOR_PAYMODE_DATE
	 *            the vENDOR_PAYMODE_DATE to set
	 */
	public void setVENDOR_PAYMODE_DATE(String vENDOR_PAYMODE_DATE) {
		VENDOR_PAYMODE_DATE = vENDOR_PAYMODE_DATE;
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
	 * @return the iNVOICE_DATE_ON
	 */
	public Date getINVOICE_DATE_ON() {
		return INVOICE_DATE_ON;
	}

	/**
	 * @param iNVOICE_DATE_ON the iNVOICE_DATE_ON to set
	 */
	public void setINVOICE_DATE_ON(Date iNVOICE_DATE_ON) {
		INVOICE_DATE_ON = iNVOICE_DATE_ON;
	}

	/**
	 * @return the cREATED_DATE_ID
	 */
	public Date getCREATED_DATE_ON() {
		return CREATED_DATE_ON;
	}

	/**
	 * @param cREATED_DATE_ID the cREATED_DATE_ID to set
	 */
	public void setCREATED_DATE_ON(Date cREATED_DATE_ID) {
		CREATED_DATE_ON = cREATED_DATE_ID;
	}

	/**
	 * @return the lASTUPDATED_DATE_ON
	 */
	public Date getLASTUPDATED_DATE_ON() {
		return LASTUPDATED_DATE_ON;
	}

	/**
	 * @param lASTUPDATED_DATE_ON the lASTUPDATED_DATE_ON to set
	 */
	public void setLASTUPDATED_DATE_ON(Date lASTUPDATED_DATE_ON) {
		LASTUPDATED_DATE_ON = lASTUPDATED_DATE_ON;
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

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}

	public String getSubLocation() {
		return subLocation;
	}

	public void setSubLocation(String subLocation) {
		this.subLocation = subLocation;
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
		result = prime * result + ((INVOICE_DATE == null) ? 0 : INVOICE_DATE.hashCode());
		result = prime * result + ((INVOICE_NUMBER == null) ? 0 : INVOICE_NUMBER.hashCode());
		result = prime * result + ((ITYRE_ID == null) ? 0 : ITYRE_ID.hashCode());
		result = prime * result + ((VENDOR_ID == null) ? 0 : VENDOR_ID.hashCode());
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
		InventoryTyreInvoiceDto other = (InventoryTyreInvoiceDto) obj;
		if (INVOICE_DATE == null) {
			if (other.INVOICE_DATE != null)
				return false;
		} else if (!INVOICE_DATE.equals(other.INVOICE_DATE))
			return false;
		if (INVOICE_NUMBER == null) {
			if (other.INVOICE_NUMBER != null)
				return false;
		} else if (!INVOICE_NUMBER.equals(other.INVOICE_NUMBER))
			return false;
		if (ITYRE_ID == null) {
			if (other.ITYRE_ID != null)
				return false;
		} else if (!ITYRE_ID.equals(other.ITYRE_ID))
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
		builder.append("InventoryTyreInvoiceDto [ITYRE_ID=");
		builder.append(ITYRE_ID);
		builder.append(", ITYRE_NUMBER=");
		builder.append(ITYRE_NUMBER);
		builder.append(", WAREHOUSE_LOCATION=");
		builder.append(WAREHOUSE_LOCATION);
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
		builder.append(", VENDOR_NAME=");
		builder.append(VENDOR_NAME);
		builder.append(", VENDOR_LOCATION=");
		builder.append(VENDOR_LOCATION);
		builder.append(", PAYMENT_TYPE=");
		builder.append(PAYMENT_TYPE);
		builder.append(", PAYMENT_NUMBER=");
		builder.append(PAYMENT_NUMBER);
		builder.append(", VENDOR_PAYMODE_STATUS=");
		builder.append(VENDOR_PAYMODE_STATUS);
		builder.append(", VENDOR_PAYMODE_DATE=");
		builder.append(VENDOR_PAYMODE_DATE);
		builder.append(", TOTALTYRE_AMOUNT=");
		builder.append(TOTALTYRE_AMOUNT);
		builder.append(", INVENTORY_TYRE_AMOUNT=");
		builder.append(INVENTORY_TYRE_AMOUNT != null ? toString(INVENTORY_TYRE_AMOUNT, maxLen) : null);
		builder.append(", DESCRIPTION=");
		builder.append(DESCRIPTION);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);		
		builder.append(", typeOfPaymentId=");
		builder.append(typeOfPaymentId);		
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
		builder.append(", subLocation=");
		builder.append(subLocation);		
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
	
	public static String getPaymentModeName(short status) {

		String statusName		=	null;
		switch (status) {
		case PAYMENT_TYPE_PO:
			statusName	= PAYMENT_TYPE_PO_NAME;
			break;
		  case VENDOR_PAYMODE_STATUS_PAID:
			  statusName	= VENDOR_PAYMODE_STATUS_PAID_NAME;
		        break;
		  case VENDOR_PAYMODE_STATUS_NOTPAID: 
			  statusName	= VENDOR_PAYMODE_STATUS_NOTPAID_NAME;
		        break;
		  case VENDOR_PAYMODE_STATUS_APPROVED: 
			  statusName	= VENDOR_PAYMODE_STATUS_APPROVED_NAME;
		        break;
		  case VENDOR_PAYMODE_STATUS_PARTIALLY_PAID: 
			  statusName	= VENDOR_PAYMODE_STATUS_PARTIALLY_PAID_NAME;
			  break;
		  case VENDOR_PAYMODE_STATUS_NEGOTIABLE_PAID: 
			  statusName	= VENDOR_PAYMODE_STATUS_NEGOTIABLE_PAID_NAME;
			  break;
		  case VENDOR_PAYMODE_STATUS_CREATE_APPROVAL: 
			  statusName	= VENDOR_PAYMODE_STATUS_CREATE_APPROVAL_NAME;
			  break;	  
			  
		
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}

	public String getTypeOfPaymentStr() {
		return typeOfPaymentStr;
	}

	public void setTypeOfPaymentStr(String typeOfPaymentStr) {
		this.typeOfPaymentStr = typeOfPaymentStr;
	}

	public boolean isTyre_document() {
		return tyre_document;
	}

	public void setTyre_document(boolean tyre_document) {
		this.tyre_document = tyre_document;
	}

	public Long getTyre_document_id() {
		return tyre_document_id;
	}

	public void setTyre_document_id(Long tyre_document_id) {
		this.tyre_document_id = tyre_document_id;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public String getTallyCompanyName() {
		return tallyCompanyName;
	}

	public void setTallyCompanyName(String tallyCompanyName) {
		this.tallyCompanyName = tallyCompanyName;
	}

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public Long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}
	
}
