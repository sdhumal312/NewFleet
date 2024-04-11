/**
 * 
 */
package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author fleetop
 *
 */
@Entity
@Table(name = "CashBook")
public class CashBook implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value for the CASHID field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CASHID")
	private long CASHID;
	
	@Column(name = "CASH_NUMBER", nullable = false)
	private long CASH_NUMBER;

	/** The value for the CASH_BOOK_NO field */
	@Column(name = "CASH_BOOK_NO", nullable = false, length = 250)
	private String CASH_BOOK_NO;
	
	@Column(name = "CASH_BOOK_ID", nullable = false)
	private Integer	CASH_BOOK_ID;

	@Column(name = "PAYMENT_TYPE_ID", nullable = false)
	private short PAYMENT_TYPE_ID;

	/** The value for the CASH_DATE DATE field */
	@Basic(optional = false)
	@Column(name = "CASH_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CASH_DATE;

	/** The value for the CASH_VOUCHER_NO field */
	@Column(name = "CASH_VOUCHER_NO", nullable = false, length = 50)
	private String CASH_VOUCHER_NO;

	/** The value for the CASH_AMOUNT field */
	@Column(name = "CASH_AMOUNT")
	private Double CASH_AMOUNT;

	/** The value for the CASH_NATURE_OF_PAYMENT field */
	@Column(name = "CASH_NATURE_PAYMENT", length = 250)
	private String CASH_NATURE_PAYMENT;
	
	@Column(name = "CASH_NATURE_PAYMENT_ID")
	private Integer CASH_NATURE_PAYMENT_ID;

	/** The value for the CASH_PAID_TO AND CASH RECEIVED_TO field */
	@Column(name = "CASH_PAID_RECEIVED", length = 250)
	private String CASH_PAID_RECEIVED;

	/** The value for the CASH_PAID_BY field */
	@Column(name = "CASH_PAID_BY", length = 250)
	private String CASH_PAID_BY;

	@Column(name = "CASH_STATUS_ID", nullable = false)
	private short CASH_STATUS_ID;

	@Column(name = "CASH_APPROVAL_STATUS_ID", nullable = false)
	private short CASH_APPROVAL_STATUS_ID;

	/** The value for the CASH_APPROVALBY one user name field */
	@Column(name = "CASH_APPROVALBY", length = 150)
	private String CASH_APPROVALBY;

	/** The value for the CASH_APPROVALCOMMENT one user name field */
	@Column(name = "CASH_APPROVALCOMMENT", length = 200)
	private String CASH_APPROVALCOMMENT;

	/** The value for the CASH_APPROVALDATE DATE field */
	@Column(name = "CASH_APPROVALDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date CASH_APPROVALDATE;

	/** The value for the CASH_REFENCENO field */
	@Column(name = "CASH_REFERENCENO", length = 50)
	private String CASH_REFERENCENO;

	/** The value for the CASH_GSTNO field */
	@Column(name = "CASH_GSTNO", length = 50)
	private String CASH_GSTNO;

	/** The value for the CASHBOOK DOCUMENT available field */
	@Basic(optional = false)
	@Column(name = "CASH_DOCUMENT", nullable = false)
	private boolean CASH_DOCUMENT = false;

	/** The value for the CASHBOOK DOCUMENT ID available field */
	@Column(name = "CASH_DOCUMENT_ID")
	private Long CASH_DOCUMENT_ID;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;
	
	/** The value for the DRIVER_ID available field */
	@Column(name = "DRIVER_ID")
	private Integer DRIVER_ID;

	@Column(name = "CREATEDBY", updatable = false, length = 150)
	private String CREATEDBY;

	@Column(name = "LASTMODIFIEDBY", length = 150)
	private String LASTMODIFIEDBY;

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

	@Column(name = "INCOME_TYPE")
	private String INCOME_TYPE;
	
	public CashBook() {
		super();
	}

	public CashBook(long cASHID, long CASH_NUMBER, String cASH_BOOK_NO, Integer CASH_BOOK_ID, Date cASH_DATE, String cASH_VOUCHER_NO,
			Double cASH_AMOUNT, String cASH_NATURE_PAYMENT, String cASH_PAID_RECEIVED, String cASH_PAID_BY,
			 String cREATEDBY, boolean MarkForDelete, Date cREATED_DATE, Integer companyId) {
		super();
		this.CASHID = cASHID;
		this.CASH_NUMBER = CASH_NUMBER;
		this.CASH_BOOK_NO = cASH_BOOK_NO;
		this.CASH_BOOK_ID	= CASH_BOOK_ID;
		this.CASH_DATE = cASH_DATE;
		this.CASH_VOUCHER_NO = cASH_VOUCHER_NO;
		this.CASH_AMOUNT = cASH_AMOUNT;
		this.CASH_NATURE_PAYMENT = cASH_NATURE_PAYMENT;
		this.CASH_PAID_RECEIVED = cASH_PAID_RECEIVED;
		this.CASH_PAID_BY = cASH_PAID_BY;
		this.CREATEDBY = cREATEDBY;
		this.markForDelete = MarkForDelete;
		this.CREATED_DATE = cREATED_DATE;
		this.COMPANY_ID = companyId;
	}

	
	public String getCASH_BOOK_NO() {
		return CASH_BOOK_NO;
	}

	public void setCASH_BOOK_NO(String cASH_BOOK_NO) {
		CASH_BOOK_NO = cASH_BOOK_NO;
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


	public Integer getCASH_BOOK_ID() {
		return CASH_BOOK_ID;
	}

	public void setCASH_BOOK_ID(Integer cASH_BOOK_ID) {
		CASH_BOOK_ID = cASH_BOOK_ID;
	}


	/**
	 * @return the cASH_DATE
	 */
	public Date getCASH_DATE() {
		return CASH_DATE;
	}

	/**
	 * @param cASH_DATE
	 *            the cASH_DATE to set
	 */
	public void setCASH_DATE(Date cASH_DATE) {
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
		CASH_AMOUNT = cASH_AMOUNT;
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


	public short getPAYMENT_TYPE_ID() {
		return PAYMENT_TYPE_ID;
	}

	public void setPAYMENT_TYPE_ID(short pAYMENT_TYPE_ID) {
		PAYMENT_TYPE_ID = pAYMENT_TYPE_ID;
	}

	public short getCASH_STATUS_ID() {
		return CASH_STATUS_ID;
	}

	public void setCASH_STATUS_ID(short cASH_STATUS_ID) {
		CASH_STATUS_ID = cASH_STATUS_ID;
	}

	public short getCASH_APPROVAL_STATUS_ID() {
		return CASH_APPROVAL_STATUS_ID;
	}

	public void setCASH_APPROVAL_STATUS_ID(short cASH_APPROVAL_STATUS_ID) {
		CASH_APPROVAL_STATUS_ID = cASH_APPROVAL_STATUS_ID;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer COMPANY_ID) {
		this.COMPANY_ID = COMPANY_ID;
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
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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
	 * @return the cASH_REFENCENO
	 */
	public String getCASH_REFERENCENO() {
		return CASH_REFERENCENO;
	}

	/**
	 * @param cASH_REFENCENO
	 *            the cASH_REFENCENO to set
	 */
	public void setCASH_REFERENCENO(String cASH_REFENCENO) {
		CASH_REFERENCENO = cASH_REFENCENO;
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
	public Date getCASH_APPROVALDATE() {
		return CASH_APPROVALDATE;
	}

	/**
	 * @param cASH_APPROVALDATE
	 *            the cASH_APPROVALDATE to set
	 */
	public void setCASH_APPROVALDATE(Date cASH_APPROVALDATE) {
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

	public Integer getDRIVER_ID() {
		return DRIVER_ID;
	}

	public void setDRIVER_ID(Integer dRIVER_ID) {
		DRIVER_ID = dRIVER_ID;
	}

	/**
	 * @return the cASH_NATURE_PAYMENT_ID
	 */
	public Integer getCASH_NATURE_PAYMENT_ID() {
		return CASH_NATURE_PAYMENT_ID;
	}

	/**
	 * @param cASH_NATURE_PAYMENT_ID the cASH_NATURE_PAYMENT_ID to set
	 */
	public void setCASH_NATURE_PAYMENT_ID(Integer cASH_NATURE_PAYMENT_ID) {
		CASH_NATURE_PAYMENT_ID = cASH_NATURE_PAYMENT_ID;
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
		CashBook other = (CashBook) obj;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CashBook [CASHID=");
		builder.append(CASHID);
		builder.append(", CASH_NUMBER=");
		builder.append(CASH_NUMBER);
		builder.append(", CASH_BOOK_NO=");
		builder.append(CASH_BOOK_NO);
		builder.append(", CASH_BOOK_ID=");
		builder.append(CASH_BOOK_ID);
		builder.append(", CASH_DATE=");
		builder.append(CASH_DATE);
		builder.append(", CASH_VOUCHER_NO=");
		builder.append(CASH_VOUCHER_NO);
		builder.append(", CASH_AMOUNT=");
		builder.append(CASH_AMOUNT);
		builder.append(", CASH_NATURE_PAYMENT=");
		builder.append(CASH_NATURE_PAYMENT);
		builder.append(", CASH_PAID_RECEIVED=");
		builder.append(CASH_PAID_RECEIVED);
		builder.append(", CASH_PAID_BY=");
		builder.append(CASH_PAID_BY);
		builder.append(", CASH_APPROVALBY=");
		builder.append(CASH_APPROVALBY);
		builder.append(", CASH_APPROVALCOMMENT=");
		builder.append(CASH_APPROVALCOMMENT);
		builder.append(", CASH_APPROVALDATE=");
		builder.append(CASH_APPROVALDATE);
		builder.append(", CASH_REFERENCENO=");
		builder.append(CASH_REFERENCENO);
		builder.append(", CASH_GSTNO=");
		builder.append(CASH_GSTNO);
		builder.append(", CASH_DOCUMENT=");
		builder.append(CASH_DOCUMENT);
		builder.append(", CASH_DOCUMENT_ID=");
		builder.append(CASH_DOCUMENT_ID);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", PAYMENT_TYPE_ID=");
		builder.append(PAYMENT_TYPE_ID);
		builder.append(", CASH_STATUS_ID=");
		builder.append(CASH_STATUS_ID);
		builder.append(", CASH_APPROVAL_STATUS_ID=");
		builder.append(CASH_APPROVAL_STATUS_ID);
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
		builder.append(", INCOME_TYPE=");
		builder.append("INCOME_TYPE");
		builder.append("]");
		return builder.toString();
	}

}
