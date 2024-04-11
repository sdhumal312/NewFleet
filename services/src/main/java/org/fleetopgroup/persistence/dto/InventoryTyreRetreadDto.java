
/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class InventoryTyreRetreadDto {
	
	
	public static final short TR_VENDOR_PAYMODE_STATUS_PAID				= 1;
	public static final short TR_VENDOR_PAYMODE_STATUS_NOTPAID			= 2;
	public static final short TR_VENDOR_PAYMODE_STATUS_APPROVED			= 3;
	public static final short TR_VENDOR_PAYMODE_STATUS_PARTIALLY_PAID	= 4;
	public static final short TR_VENDOR_PAYMODE_STATUS_NEGOTIABLE_PAID	= 5;
	public static final short TR_VENDOR_PAYMODE_STATUS_CREATE_APPROVAL	= 6;
	
	public static final String  TR_VENDOR_PAYMODE_STATUS_PAID_NAME				= "PAID";
	public static final String  TR_VENDOR_PAYMODE_STATUS_NOTPAID_NAME			= "NOTPAID";
	public static final String  TR_VENDOR_PAYMODE_STATUS_APPROVED_NAME			= "APPROVED";
	public static final String  TR_VENDOR_PAYMODE_STATUS_PARTIALLY_PAID_NAME	= "PARTIALLY"; 
	public static final String  TR_VENDOR_PAYMODE_STATUS_NEGOTIABLE_PAID_NAME	= "NEGOTIABLE";
	public static final String  TR_VENDOR_PAYMODE_STATUS_CREATE_APPROVAL_NAME	= "CREATE_APPOVAL";
	
	
	public static final short	INVENTORY_TYRE_TR_STATUS_OPEN			= 1;
	public static final short	INVENTORY_TYRE_TR_STATUS_SENT_RETREAD	= 2;
	public static final short	INVENTORY_TYRE_TR_STATUS_COMPLETED		= 3;
	public static final short	INVENTORY_TYRE_TR_STATUS_RECEIVE		= 4;
	public static final short	INVENTORY_TYRE_TR_STATUS_REJECT			= 5;
	
	public static final String INVENTORY_TYRE_TR_STATUS_OPEN_NAME				= "OPEN";
	public static final String INVENTORY_TYRE_TR_STATUS_SENT_RETREAD_NAME		= "SENT-RETREAD";
	public static final String INVENTORY_TYRE_TR_STATUS_COMPLETED_NAME			= "COMPLETED";
	public static final String INVENTORY_TYRE_TR_STATUS_RECEIVE_NAME			= "RECEIVE";
	public static final String INVENTORY_TYRE_TR_STATUS_REJECT_NAME				= "REJECT";
	

	/** The value for the ITYRE_ID Layout Id field */
	private Long TRID;
	
	private Long TRNUMBER;

	/** The value for the TR_OPEN_DATE field */
	private String TR_OPEN_DATE;
	
	private Date	TR_OPEN_DATE_ON;
	
	private Date 	TR_REQUIRED_DATE_ON;

	/** The value for the TR_REQUIRED_DATE field */
	private String TR_REQUIRED_DATE;

	/** The value for the VENDOR_ID field */
	private Integer TR_VENDOR_ID;

	/** The value for the VENDOR_NAME field */
	private String TR_VENDOR_NAME;
	
	private short TR_PAYMENT_TYPE_ID;
	
	private short TR_STATUS_ID;

	/** The value for the VENDOR_LOCATION field */
	private String TR_VENDOR_LOCATION;

	/** The value for the PAYMENT_TYPE field */
	private String TR_PAYMENT_TYPE;

	/** The value for the PAYMENT_NUMBER field */
	private String TR_PAYMENT_NUMBER;

	/** The value for the VENDOR_PAYMODE_STATUS field */
	private String TR_VENDOR_PAYMODE_STATUS;
	
	private short TR_VENDOR_PAYMODE_STATUS_ID;

	/** The value for the VENDOR_PAYMODE_DATE DATE field */
	private Date TR_VENDOR_PAYMODE_DATE;

	/** The value for the TR_QUOTE_NO field */
	private String TR_QUOTE_NO;

	/** The value for the TR_MANUAL_NO field */
	private String TR_MANUAL_NO;

	/** The value for the TYRE_AMOUNT field */
	private Double TR_AMOUNT;

	/** The value for the TR_ROUNT_AMOUNT field */
	private Double TR_ROUNT_AMOUNT;

	/** The value for the TR_SEND_TYRE field */
	private Integer TR_SEND_TYRE;

	/** The value for the TR_RECEIVE_TYRE field */
	private Integer TR_RECEIVE_TYRE;

	/** The value for the TR_DESCRIPTION field */
	private String TR_DESCRIPTION;

	/** The value for the TR_RE_DESCRIPTION field */
	private String TR_RE_DESCRIPTION;

	/** The value for the TR_INVOICE_NUMBER field */
	private String TR_INVOICE_NUMBER;

	/** The value for the TR_INVOICE_ DATE field */
	private String TR_INVOICE_DATE;
	
	private Date TR_INVOICE_DATE_ON;

	/** The value for the TR_STATUS field */
	private String TR_STATUS;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;
	
	private short typeOfPaymentId;
	
	private String Tyre_Number;
	
	private String tyre_Manufacturer;
	
	private Long approvalId;
	
	private String approvalNumber;
	
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
		this.paidAmount = Utility.round(paidAmount, 2) ;
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2);
	}

	private double paidAmount;
	
	private double balanceAmount;

	boolean markForDelete;

	/** The value for the CREATED_DATE DATE field */
	private String CREATED_DATE;

	/** The value for the LASTUPDATED_DATE DATE field */
	private String LASTUPDATED_DATE;

	public InventoryTyreRetreadDto() {
		super();
	}

	public InventoryTyreRetreadDto(Long tRID, String tR_OPEN_DATE, String tR_REQUIRED_DATE, Integer tR_VENDOR_ID,
			String tR_VENDOR_NAME, String tR_VENDOR_LOCATION, String tR_QUOTE_NO, String tR_MANUAL_NO, Double tR_AMOUNT,
			Integer tR_SEND_TYRE, Integer tR_RECEIVE_TYRE, String tR_STATUS, String cREATEDBY, boolean MarkForDelete,
			String cREATED_DATE) {
		super();
		TRID = tRID;
		TR_OPEN_DATE = tR_OPEN_DATE;
		TR_REQUIRED_DATE = tR_REQUIRED_DATE;
		TR_VENDOR_ID = tR_VENDOR_ID;
		TR_VENDOR_NAME = tR_VENDOR_NAME;
		TR_VENDOR_LOCATION = tR_VENDOR_LOCATION;
		TR_QUOTE_NO = tR_QUOTE_NO;
		TR_MANUAL_NO = tR_MANUAL_NO;
		TR_AMOUNT = tR_AMOUNT;
		TR_SEND_TYRE = tR_SEND_TYRE;
		TR_RECEIVE_TYRE = tR_RECEIVE_TYRE;
		TR_STATUS = tR_STATUS;
		CREATEDBY = cREATEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
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
	public String getTR_OPEN_DATE() {
		return TR_OPEN_DATE;
	}

	/**
	 * @param tR_OPEN_DATE
	 *            the tR_OPEN_DATE to set
	 */
	public void setTR_OPEN_DATE(String tR_OPEN_DATE) {
		TR_OPEN_DATE = tR_OPEN_DATE;
	}

	/**
	 * @return the tR_REQUIRED_DATE
	 */
	public String getTR_REQUIRED_DATE() {
		return TR_REQUIRED_DATE;
	}

	/**
	 * @param tR_REQUIRED_DATE
	 *            the tR_REQUIRED_DATE to set
	 */
	public void setTR_REQUIRED_DATE(String tR_REQUIRED_DATE) {
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
	 * @return the tR_VENDOR_NAME
	 */
	public String getTR_VENDOR_NAME() {
		return TR_VENDOR_NAME;
	}

	/**
	 * @param tR_VENDOR_NAME
	 *            the tR_VENDOR_NAME to set
	 */
	public void setTR_VENDOR_NAME(String tR_VENDOR_NAME) {
		TR_VENDOR_NAME = tR_VENDOR_NAME;
	}

	/**
	 * @return the tR_VENDOR_LOCATION
	 */
	public String getTR_VENDOR_LOCATION() {
		return TR_VENDOR_LOCATION;
	}

	/**
	 * @param tR_VENDOR_LOCATION
	 *            the tR_VENDOR_LOCATION to set
	 */
	public void setTR_VENDOR_LOCATION(String tR_VENDOR_LOCATION) {
		TR_VENDOR_LOCATION = tR_VENDOR_LOCATION;
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
		TR_AMOUNT = Utility.round(tR_AMOUNT,2);
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
	 * @return the tR_STATUS
	 */
	public String getTR_STATUS() {
		return TR_STATUS;
	}

	/**
	 * @param tR_STATUS
	 *            the tR_STATUS to set
	 */
	public void setTR_STATUS(String tR_STATUS) {
		TR_STATUS = tR_STATUS;
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
		TR_ROUNT_AMOUNT = Utility.round(tR_ROUNT_AMOUNT,2);
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
	 * @return the tR_PAYMENT_TYPE
	 */
	public String getTR_PAYMENT_TYPE() {
		return TR_PAYMENT_TYPE;
	}

	/**
	 * @param tR_PAYMENT_TYPE
	 *            the tR_PAYMENT_TYPE to set
	 */
	public void setTR_PAYMENT_TYPE(String tR_PAYMENT_TYPE) {
		TR_PAYMENT_TYPE = tR_PAYMENT_TYPE;
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
	 * @return the tR_VENDOR_PAYMODE_STATUS
	 */
	public String getTR_VENDOR_PAYMODE_STATUS() {
		return TR_VENDOR_PAYMODE_STATUS;
	}

	/**
	 * @param tR_VENDOR_PAYMODE_STATUS
	 *            the tR_VENDOR_PAYMODE_STATUS to set
	 */
	public void setTR_VENDOR_PAYMODE_STATUS(String tR_VENDOR_PAYMODE_STATUS) {
		TR_VENDOR_PAYMODE_STATUS = tR_VENDOR_PAYMODE_STATUS;
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
	public String getTR_INVOICE_DATE() {
		return TR_INVOICE_DATE;
	}

	/**
	 * @param tR_INVOICE_DATE
	 *            the tR_INVOICE_DATE to set
	 */
	public void setTR_INVOICE_DATE(String tR_INVOICE_DATE) {
		TR_INVOICE_DATE = tR_INVOICE_DATE;
	}

	/**
	 * @return the tR_OPEN_DATE_ON
	 */
	public Date getTR_OPEN_DATE_ON() {
		return TR_OPEN_DATE_ON;
	}

	/**
	 * @param tR_OPEN_DATE_ON the tR_OPEN_DATE_ON to set
	 */
	public void setTR_OPEN_DATE_ON(Date tR_OPEN_DATE_ON) {
		TR_OPEN_DATE_ON = tR_OPEN_DATE_ON;
	}

	/**
	 * @return the tR_REQUIRED_DATE_ON
	 */
	public Date getTR_REQUIRED_DATE_ON() {
		return TR_REQUIRED_DATE_ON;
	}

	/**
	 * @param tR_REQUIRED_DATE_ON the tR_REQUIRED_DATE_ON to set
	 */
	public void setTR_REQUIRED_DATE_ON(Date tR_REQUIRED_DATE_ON) {
		TR_REQUIRED_DATE_ON = tR_REQUIRED_DATE_ON;
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
	 * @return the tR_INVOICE_DATE_ON
	 */
	public Date getTR_INVOICE_DATE_ON() {
		return TR_INVOICE_DATE_ON;
	}

	/**
	 * @param tR_INVOICE_DATE_ON the tR_INVOICE_DATE_ON to set
	 */
	public void setTR_INVOICE_DATE_ON(Date tR_INVOICE_DATE_ON) {
		TR_INVOICE_DATE_ON = tR_INVOICE_DATE_ON;
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
		InventoryTyreRetreadDto other = (InventoryTyreRetreadDto) obj;
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
	

	public static String getVendorPaymentModeName(short status) {

		String statusName		=	null;
		switch (status) {
		  case TR_VENDOR_PAYMODE_STATUS_PAID:
			  statusName	= TR_VENDOR_PAYMODE_STATUS_PAID_NAME;
		        break;
		  case TR_VENDOR_PAYMODE_STATUS_NOTPAID: 
			  statusName	= TR_VENDOR_PAYMODE_STATUS_NOTPAID_NAME;
		        break;
		  case TR_VENDOR_PAYMODE_STATUS_APPROVED: 
			  statusName	= TR_VENDOR_PAYMODE_STATUS_APPROVED_NAME;
		        break;
		  case TR_VENDOR_PAYMODE_STATUS_PARTIALLY_PAID: 
			  statusName	= TR_VENDOR_PAYMODE_STATUS_PARTIALLY_PAID_NAME;
			  break;
		  case TR_VENDOR_PAYMODE_STATUS_NEGOTIABLE_PAID: 
			  statusName	= TR_VENDOR_PAYMODE_STATUS_NEGOTIABLE_PAID_NAME;
			  break;
		  case TR_VENDOR_PAYMODE_STATUS_CREATE_APPROVAL: 
			  statusName	= TR_VENDOR_PAYMODE_STATUS_CREATE_APPROVAL_NAME;
			  break;
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}
	
	@Override
	public String toString() {
		return "InventoryTyreRetreadDto [TRID=" + TRID + ", TRNUMBER=" + TRNUMBER + ", TR_OPEN_DATE=" + TR_OPEN_DATE
				+ ", TR_OPEN_DATE_ON=" + TR_OPEN_DATE_ON + ", TR_REQUIRED_DATE_ON=" + TR_REQUIRED_DATE_ON
				+ ", TR_REQUIRED_DATE=" + TR_REQUIRED_DATE + ", TR_VENDOR_ID=" + TR_VENDOR_ID + ", TR_VENDOR_NAME="
				+ TR_VENDOR_NAME + ", TR_PAYMENT_TYPE_ID=" + TR_PAYMENT_TYPE_ID + ", TR_STATUS_ID=" + TR_STATUS_ID
				+ ", TR_VENDOR_LOCATION=" + TR_VENDOR_LOCATION + ", TR_PAYMENT_TYPE=" + TR_PAYMENT_TYPE
				+ ", TR_PAYMENT_NUMBER=" + TR_PAYMENT_NUMBER + ", TR_VENDOR_PAYMODE_STATUS=" + TR_VENDOR_PAYMODE_STATUS
				+ ", TR_VENDOR_PAYMODE_STATUS_ID=" + TR_VENDOR_PAYMODE_STATUS_ID + ", TR_VENDOR_PAYMODE_DATE="
				+ TR_VENDOR_PAYMODE_DATE + ", TR_QUOTE_NO=" + TR_QUOTE_NO + ", TR_MANUAL_NO=" + TR_MANUAL_NO
				+ ", TR_AMOUNT=" + TR_AMOUNT + ", TR_ROUNT_AMOUNT=" + TR_ROUNT_AMOUNT + ", TR_SEND_TYRE=" + TR_SEND_TYRE
				+ ", TR_RECEIVE_TYRE=" + TR_RECEIVE_TYRE + ", TR_DESCRIPTION=" + TR_DESCRIPTION + ", TR_RE_DESCRIPTION="
				+ TR_RE_DESCRIPTION + ", TR_INVOICE_NUMBER=" + TR_INVOICE_NUMBER + ", TR_INVOICE_DATE="
				+ TR_INVOICE_DATE + ", TR_INVOICE_DATE_ON=" + TR_INVOICE_DATE_ON + ", TR_STATUS=" + TR_STATUS
				+ ", CREATEDBY=" + CREATEDBY + ", LASTMODIFIEDBY=" + LASTMODIFIEDBY + ", typeOfPaymentId="
				+ typeOfPaymentId + ", Tyre_Number=" + Tyre_Number + ", tyre_Manufacturer=" + tyre_Manufacturer
				+ ", paidAmount=" + paidAmount + ", balanceAmount=" + balanceAmount + ", markForDelete=" + markForDelete
				+ ", CREATED_DATE=" + CREATED_DATE + ", LASTUPDATED_DATE=" + LASTUPDATED_DATE + "]";
	}

	public static String getTRStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case INVENTORY_TYRE_TR_STATUS_OPEN:
			  statusName	= INVENTORY_TYRE_TR_STATUS_OPEN_NAME;
		        break;
		  case INVENTORY_TYRE_TR_STATUS_SENT_RETREAD: 
			  statusName	= INVENTORY_TYRE_TR_STATUS_SENT_RETREAD_NAME;
		        break;
		  case INVENTORY_TYRE_TR_STATUS_COMPLETED: 
			  statusName	= INVENTORY_TYRE_TR_STATUS_COMPLETED_NAME;
		        break;
		  case INVENTORY_TYRE_TR_STATUS_RECEIVE: 
			  statusName	= INVENTORY_TYRE_TR_STATUS_RECEIVE_NAME;
		        break;
		  case INVENTORY_TYRE_TR_STATUS_REJECT: 
			  statusName	= INVENTORY_TYRE_TR_STATUS_REJECT_NAME;
		        break;
		
		
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}

	public String getTyre_Number() {
		return Tyre_Number;
	}

	public void setTyre_Number(String tyre_Number) {
		Tyre_Number = tyre_Number;
	}

	public String getTyre_Manufacturer() {
		return tyre_Manufacturer;
	}

	public void setTyre_Manufacturer(String tyre_Manufacturer) {
		this.tyre_Manufacturer = tyre_Manufacturer;
	}
}
