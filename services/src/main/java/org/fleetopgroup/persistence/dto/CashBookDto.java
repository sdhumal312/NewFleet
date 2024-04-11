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
public class CashBookDto {

	/** The value for the CASHID field */
	private long CASHID;

	private long CASH_NUMBER;
	
	/** The value for the CASH_BOOK_NO field */
	private String CASH_BOOK_NO;
	
	private Integer CASH_BOOK_ID;

	/** The value for the CASH_PAYMENT_TYPE field */
	private String CASH_PAYMENT_TYPE;

	/** The value for the CASH_DATE DATE field */
	private String CASH_DATE;
	
	private Date CASH_DATE_ON;

	/** The value for the CASH_VOUCHER_NO field */
	private String CASH_VOUCHER_NO;

	/** The value for the CASH_AMOUNT field */
	private Double CASH_AMOUNT;

	/** The value for the CASH_NATURE_OF_PAYMENT field */
	private String CASH_NATURE_PAYMENT;
	
	private Integer CASH_NATURE_PAYMENT_ID;

	/** The value for the CASH_PAID_TO AND CASH RECEIVED_TO field */
	private String CASH_PAID_RECEIVED;

	/** The value for the CASH_PAID_BY field */
	private String CASH_PAID_BY;

	/** The value for the CASH_STATUS one user name field */
	private String CASH_STATUS;

	/** The value for the CASH_APPROVAL_STATUS one user name field */
	private String CASH_APPROVAL_STATUS;

	/** The value for the CASH_APPROVALBY one user name field */
	private String CASH_APPROVALBY;

	/** The value for the CASH_APPROVALCOMMENT one user name field */
	private String CASH_APPROVALCOMMENT;

	/** The value for the CASH_APPROVALDATE DATE field */
	private String CASH_APPROVALDATE;
	
	private Date CASH_APPROVALDATE_ON;

	/** The value for the CASH_REFENCENO field */
	private String CASH_REFERENCENO;

	/** The value for the CASH_GSTNO field */
	private String CASH_GSTNO;

	/** The value for the CASH_DOCUMENT field */
	private boolean CASH_DOCUMENT;

	/** The value for the CASH_DOCUMENT_ID field */
	private Long CASH_DOCUMENT_ID;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;

	boolean markForDelete;

	/** The value for the CASH_AMOUNT World field */
	private String CASH_AMOUNT_WORLD;

	/** The value for the CREATED_DATE DATE field */
	private String CREATED_DATE;

	/** The value for the LASTUPDATED_DATE DATE field */
	private String LASTUPDATED_DATE;
	
	private Date CREATED;

	/** The value for the LASTUPDATED_DATE DATE field */
	private Date LASTUPDATED;
	
	private short PAYMENT_TYPE_ID;
	
	private short CASH_APPROVAL_STATUS_ID;
	
	private short CASH_STATUS_ID;
	
	private Integer DRIVER_ID;
	
	private String CASH_AMT_STR;
	
	private boolean multiplePayment;
	
	private String incomeName;
	
	private String expenseName;
	
	private Integer companyId;
	
	private boolean isVoucherAuto;
	
	private boolean	voucherType;
	
	private String INCOME_TYPE;
	
	public CashBookDto() {
		super();
	}

	public CashBookDto(long cASHID, String cASH_BOOK_NO, String cASH_PAYMENT_TYPE, String cASH_DATE,
			String cASH_VOUCHER_NO, Double cASH_AMOUNT, String cASH_NATURE_PAYMENT, String cASH_PAID_RECEIVED,
			String cASH_PAID_BY, String cASH_STATUS, String cREATEDBY, boolean MarkForDelete, String cREATED_DATE) {
		super();
		CASHID = cASHID;
		CASH_BOOK_NO = cASH_BOOK_NO;
		CASH_PAYMENT_TYPE = cASH_PAYMENT_TYPE;
		CASH_DATE = cASH_DATE;
		CASH_VOUCHER_NO = cASH_VOUCHER_NO;
		CASH_AMOUNT = cASH_AMOUNT;
		CASH_NATURE_PAYMENT = cASH_NATURE_PAYMENT;
		CASH_PAID_RECEIVED = cASH_PAID_RECEIVED;
		CASH_PAID_BY = cASH_PAID_BY;
		CASH_STATUS = cASH_STATUS;
		CREATEDBY = cREATEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the cASHID
	 */
	public long getCASHID() {
		return CASHID;
	}

	/**
	 * @param cASHID
	 *            the cASHID to set
	 */
	public void setCASHID(long cASHID) {
		CASHID = cASHID;
	}

	public long getCASH_NUMBER() {
		return CASH_NUMBER;
	}

	public void setCASH_NUMBER(long cASH_NUMBER) {
		CASH_NUMBER = cASH_NUMBER;
	}

	/**
	 * @return the cASH_BOOK_NO
	 */
	public String getCASH_BOOK_NO() {
		return CASH_BOOK_NO;
	}

	/**
	 * @param cASH_BOOK_NO
	 *            the cASH_BOOK_NO to set
	 */
	public void setCASH_BOOK_NO(String cASH_BOOK_NO) {
		CASH_BOOK_NO = cASH_BOOK_NO;
	}

	public Integer getCASH_BOOK_ID() {
		return CASH_BOOK_ID;
	}

	public void setCASH_BOOK_ID(Integer cASH_BOOK_ID) {
		CASH_BOOK_ID = cASH_BOOK_ID;
	}

	/**
	 * @return the cASH_PAYMENT_TYPE
	 */
	public String getCASH_PAYMENT_TYPE() {
		return CASH_PAYMENT_TYPE;
	}

	/**
	 * @param cASH_PAYMENT_TYPE
	 *            the cASH_PAYMENT_TYPE to set
	 */
	public void setCASH_PAYMENT_TYPE(String cASH_PAYMENT_TYPE) {
		CASH_PAYMENT_TYPE = cASH_PAYMENT_TYPE;
	}

	/**
	 * @return the cASH_DATE
	 */
	public String getCASH_DATE() {
		return CASH_DATE;
	}

	/**
	 * @param cASH_DATE
	 *            the cASH_DATE to set
	 */
	public void setCASH_DATE(String cASH_DATE) {
		CASH_DATE = cASH_DATE;
	}

	/**
	 * @return the cASH_VOUCHER_NO
	 */
	public String getCASH_VOUCHER_NO() {
		return CASH_VOUCHER_NO;
	}

	/**
	 * @param cASH_VOUCHER_NO
	 *            the cASH_VOUCHER_NO to set
	 */
	public void setCASH_VOUCHER_NO(String cASH_VOUCHER_NO) {
		CASH_VOUCHER_NO = cASH_VOUCHER_NO;
	}

	/**
	 * @return the cASH_AMOUNT
	 */
	public Double getCASH_AMOUNT() {
		return CASH_AMOUNT;
	}

	/**
	 * @param cASH_AMOUNT
	 *            the cASH_AMOUNT to set
	 */
	public void setCASH_AMOUNT(Double cASH_AMOUNT) {
		CASH_AMOUNT = Utility.round(cASH_AMOUNT, 2);
	}

	/**
	 * @return the cASH_NATURE_PAYMENT
	 */
	public String getCASH_NATURE_PAYMENT() {
		return CASH_NATURE_PAYMENT;
	}

	/**
	 * @param cASH_NATURE_PAYMENT
	 *            the cASH_NATURE_PAYMENT to set
	 */
	public void setCASH_NATURE_PAYMENT(String cASH_NATURE_PAYMENT) {
		CASH_NATURE_PAYMENT = cASH_NATURE_PAYMENT;
	}

	/**
	 * @return the cASH_PAID_RECEIVED
	 */
	public String getCASH_PAID_RECEIVED() {
		return CASH_PAID_RECEIVED;
	}

	/**
	 * @param cASH_PAID_RECEIVED
	 *            the cASH_PAID_RECEIVED to set
	 */
	public void setCASH_PAID_RECEIVED(String cASH_PAID_RECEIVED) {
		CASH_PAID_RECEIVED = cASH_PAID_RECEIVED;
	}

	public Date getCREATED() {
		return CREATED;
	}

	public void setCREATED(Date cREATED) {
		CREATED = cREATED;
	}

	public Date getLASTUPDATED() {
		return LASTUPDATED;
	}

	public void setLASTUPDATED(Date lASTUPDATED) {
		LASTUPDATED = lASTUPDATED;
	}

	/**
	 * @return the cASH_PAID_BY
	 */
	public String getCASH_PAID_BY() {
		return CASH_PAID_BY;
	}

	/**
	 * @param cASH_PAID_BY
	 *            the cASH_PAID_BY to set
	 */
	public void setCASH_PAID_BY(String cASH_PAID_BY) {
		CASH_PAID_BY = cASH_PAID_BY;
	}

	/**
	 * @return the cASH_STATUS
	 */
	public String getCASH_STATUS() {
		return CASH_STATUS;
	}

	/**
	 * @param cASH_STATUS
	 *            the cASH_STATUS to set
	 */
	public void setCASH_STATUS(String cASH_STATUS) {
		CASH_STATUS = cASH_STATUS;
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
	 * @return the cASH_AMOUNT_WORLD
	 */
	public String getCASH_AMOUNT_WORLD() {
		return CASH_AMOUNT_WORLD;
	}

	/**
	 * @param cASH_AMOUNT_WORLD
	 *            the cASH_AMOUNT_WORLD to set
	 */
	public void setCASH_AMOUNT_WORLD(String cASH_AMOUNT_WORLD) {
		CASH_AMOUNT_WORLD = cASH_AMOUNT_WORLD;
	}

	/**
	 * @return the cASH_REFERENCENO
	 */
	public String getCASH_REFERENCENO() {
		return CASH_REFERENCENO;
	}

	/**
	 * @param cASH_REFERENCENO
	 *            the cASH_REFERENCENO to set
	 */
	public void setCASH_REFERENCENO(String cASH_REFERENCENO) {
		CASH_REFERENCENO = cASH_REFERENCENO;
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

	public boolean isVoucherType() {
		return voucherType;
	}

	public void setVoucherType(boolean voucherType) {
		this.voucherType = voucherType;
	}

	/**
	 * @return the cASH_APPROVAL_STATUS
	 */
	public String getCASH_APPROVAL_STATUS() {
		return CASH_APPROVAL_STATUS;
	}

	/**
	 * @param cASH_APPROVAL_STATUS
	 *            the cASH_APPROVAL_STATUS to set
	 */
	public void setCASH_APPROVAL_STATUS(String cASH_APPROVAL_STATUS) {
		CASH_APPROVAL_STATUS = cASH_APPROVAL_STATUS;
	}

	/**
	 * @return the cASH_APPROVALBY
	 */
	public String getCASH_APPROVALBY() {
		return CASH_APPROVALBY;
	}

	/**
	 * @param cASH_APPROVALBY
	 *            the cASH_APPROVALBY to set
	 */
	public void setCASH_APPROVALBY(String cASH_APPROVALBY) {
		CASH_APPROVALBY = cASH_APPROVALBY;
	}

	/**
	 * @return the cASH_APPROVALCOMMENT
	 */
	public String getCASH_APPROVALCOMMENT() {
		return CASH_APPROVALCOMMENT;
	}

	/**
	 * @param cASH_APPROVALCOMMENT
	 *            the cASH_APPROVALCOMMENT to set
	 */
	public void setCASH_APPROVALCOMMENT(String cASH_APPROVALCOMMENT) {
		CASH_APPROVALCOMMENT = cASH_APPROVALCOMMENT;
	}

	/**
	 * @return the cASH_APPROVALDATE
	 */
	public String getCASH_APPROVALDATE() {
		return CASH_APPROVALDATE;
	}

	/**
	 * @param cASH_APPROVALDATE
	 *            the cASH_APPROVALDATE to set
	 */
	public void setCASH_APPROVALDATE(String cASH_APPROVALDATE) {
		CASH_APPROVALDATE = cASH_APPROVALDATE;
	}

	/**
	 * @return the cASH_DOCUMENT
	 */
	public boolean isCASH_DOCUMENT() {
		return CASH_DOCUMENT;
	}

	/**
	 * @param cASH_DOCUMENT
	 *            the cASH_DOCUMENT to set
	 */
	public void setCASH_DOCUMENT(boolean cASH_DOCUMENT) {
		CASH_DOCUMENT = cASH_DOCUMENT;
	}

	/**
	 * @return the cASH_DOCUMENT_ID
	 */
	public Long getCASH_DOCUMENT_ID() {
		return CASH_DOCUMENT_ID;
	}

	/**
	 * @param cASH_DOCUMENT_ID
	 *            the cASH_DOCUMENT_ID to set
	 */
	public void setCASH_DOCUMENT_ID(Long cASH_DOCUMENT_ID) {
		CASH_DOCUMENT_ID = cASH_DOCUMENT_ID;
	}

	/**
	 * @return the cASH_GSTNO
	 */
	public String getCASH_GSTNO() {
		return CASH_GSTNO;
	}

	/**
	 * @param cASH_GSTNO
	 *            the cASH_GSTNO to set
	 */
	public void setCASH_GSTNO(String cASH_GSTNO) {
		CASH_GSTNO = cASH_GSTNO;
	}

	public short getPAYMENT_TYPE_ID() {
		return PAYMENT_TYPE_ID;
	}

	public void setPAYMENT_TYPE_ID(short pAYMENT_TYPE_ID) {
		PAYMENT_TYPE_ID = pAYMENT_TYPE_ID;
	}

	public short getCASH_APPROVAL_STATUS_ID() {
		return CASH_APPROVAL_STATUS_ID;
	}

	public void setCASH_APPROVAL_STATUS_ID(short cASH_APPROVAL_STATUS_ID) {
		CASH_APPROVAL_STATUS_ID = cASH_APPROVAL_STATUS_ID;
	}

	public short getCASH_STATUS_ID() {
		return CASH_STATUS_ID;
	}

	public void setCASH_STATUS_ID(short cASH_STATUS_ID) {
		CASH_STATUS_ID = cASH_STATUS_ID;
	}

	/**
	 * @return the cASH_AMT_STR
	 */
	public String getCASH_AMT_STR() {
		return CASH_AMT_STR;
	}

	/**
	 * @param cASH_AMT_STR the cASH_AMT_STR to set
	 */
	public void setCASH_AMT_STR(String cASH_AMT_STR) {
		CASH_AMT_STR = cASH_AMT_STR;
	}

	public Integer getDRIVER_ID() {
		return DRIVER_ID;
	}

	public void setDRIVER_ID(Integer dRIVER_ID) {
		DRIVER_ID = dRIVER_ID;
	}

	/**
	 * @return the multiplePayment
	 */
	public boolean isMultiplePayment() {
		return multiplePayment;
	}

	/**
	 * @return the cASH_DATE_ON
	 */
	public Date getCASH_DATE_ON() {
		return CASH_DATE_ON;
	}

	/**
	 * @param cASH_DATE_ON the cASH_DATE_ON to set
	 */
	public void setCASH_DATE_ON(Date cASH_DATE_ON) {
		CASH_DATE_ON = cASH_DATE_ON;
	}

	/**
	 * @param multiplePayment the multiplePayment to set
	 */
	public void setMultiplePayment(boolean multiplePayment) {
		this.multiplePayment = multiplePayment;
	}

	public Integer getCASH_NATURE_PAYMENT_ID() {
		return CASH_NATURE_PAYMENT_ID;
	}

	public void setCASH_NATURE_PAYMENT_ID(Integer cASH_NATURE_PAYMENT_ID) {
		CASH_NATURE_PAYMENT_ID = cASH_NATURE_PAYMENT_ID;
	}

	public String getIncomeName() {
		return incomeName;
	}

	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public Date getCASH_APPROVALDATE_ON() {
		return CASH_APPROVALDATE_ON;
	}

	public void setCASH_APPROVALDATE_ON(Date cASH_APPROVALDATE_ON) {
		CASH_APPROVALDATE_ON = cASH_APPROVALDATE_ON;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public boolean isVoucherAuto() {
		return isVoucherAuto;
	}

	public void setVoucherAuto(boolean isVoucherAuto) {
		this.isVoucherAuto = isVoucherAuto;
	}

	
	public String getINCOME_TYPE() {
		return INCOME_TYPE;
	}

	public void setINCOME_TYPE(String iNCOME_TYPE) {
		INCOME_TYPE = iNCOME_TYPE;
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
		result = prime * result + ((CASH_BOOK_NO == null) ? 0 : CASH_BOOK_NO.hashCode());
		result = prime * result + ((CASH_VOUCHER_NO == null) ? 0 : CASH_VOUCHER_NO.hashCode());
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
		CashBookDto other = (CashBookDto) obj;
		if (CASH_BOOK_NO == null) {
			if (other.CASH_BOOK_NO != null)
				return false;
		} else if (!CASH_BOOK_NO.equals(other.CASH_BOOK_NO))
			return false;
		if (CASH_VOUCHER_NO == null) {
			if (other.CASH_VOUCHER_NO != null)
				return false;
		} else if (!CASH_VOUCHER_NO.equals(other.CASH_VOUCHER_NO))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CashBookDto [CASHID=");
		builder.append(CASHID);
		builder.append(", CASH_NUMBER=");
		builder.append(CASH_NUMBER);
		builder.append(", CASH_BOOK_NO=");
		builder.append(CASH_BOOK_NO);
		builder.append(", CASH_BOOK_ID=");
		builder.append(CASH_BOOK_ID);
		builder.append(", CASH_PAYMENT_TYPE=");
		builder.append(CASH_PAYMENT_TYPE);
		builder.append(", CASH_DATE=");
		builder.append(CASH_DATE);
		builder.append(", CASH_DATE_ON=");
		builder.append(CASH_DATE_ON);
		builder.append(", CASH_VOUCHER_NO=");
		builder.append(CASH_VOUCHER_NO);
		builder.append(", CASH_AMOUNT=");
		builder.append(CASH_AMOUNT);
		builder.append(", CASH_NATURE_PAYMENT=");
		builder.append(CASH_NATURE_PAYMENT);
		builder.append(", CASH_NATURE_PAYMENT_ID=");
		builder.append(CASH_NATURE_PAYMENT_ID);
		builder.append(", CASH_PAID_RECEIVED=");
		builder.append(CASH_PAID_RECEIVED);
		builder.append(", CASH_PAID_BY=");
		builder.append(CASH_PAID_BY);
		builder.append(", CASH_STATUS=");
		builder.append(CASH_STATUS);
		builder.append(", CASH_APPROVAL_STATUS=");
		builder.append(CASH_APPROVAL_STATUS);
		builder.append(", CASH_APPROVALBY=");
		builder.append(CASH_APPROVALBY);
		builder.append(", CASH_APPROVALCOMMENT=");
		builder.append(CASH_APPROVALCOMMENT);
		builder.append(", CASH_APPROVALDATE=");
		builder.append(CASH_APPROVALDATE);
		builder.append(", CASH_APPROVALDATE_ON=");
		builder.append(CASH_APPROVALDATE_ON);
		builder.append(", CASH_REFERENCENO=");
		builder.append(CASH_REFERENCENO);
		builder.append(", CASH_GSTNO=");
		builder.append(CASH_GSTNO);
		builder.append(", CASH_DOCUMENT=");
		builder.append(CASH_DOCUMENT);
		builder.append(", CASH_DOCUMENT_ID=");
		builder.append(CASH_DOCUMENT_ID);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CASH_AMOUNT_WORLD=");
		builder.append(CASH_AMOUNT_WORLD);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", LASTUPDATED=");
		builder.append(LASTUPDATED);
		builder.append(", PAYMENT_TYPE_ID=");
		builder.append(PAYMENT_TYPE_ID);
		builder.append(", CASH_APPROVAL_STATUS_ID=");
		builder.append(CASH_APPROVAL_STATUS_ID);
		builder.append(", CASH_STATUS_ID=");
		builder.append(CASH_STATUS_ID);
		builder.append(", DRIVER_ID=");
		builder.append(DRIVER_ID);
		builder.append(", CASH_AMT_STR=");
		builder.append(CASH_AMT_STR);
		builder.append(", multiplePayment=");
		builder.append(multiplePayment);
		builder.append(", incomeName=");
		builder.append(incomeName);
		builder.append(", expenseName=");
		builder.append(expenseName);
		builder.append(", INCOME_TYPE=");
		builder.append("INCOME_TYPE");
		builder.append("]");
		return builder.toString();
	}

	
}
