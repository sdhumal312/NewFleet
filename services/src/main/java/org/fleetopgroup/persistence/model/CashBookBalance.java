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
@Table(name = "CashBookBalance")
public class CashBookBalance implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value for the CASHID field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CBBID")
	private long CBBID;

	/** The value for the CASH_BOOK_NO field */
	@Column(name = "CASH_BOOK_MAIN_ID", nullable = false)
	private short CASH_BOOK_MAIN_ID;

	/** The value for the CASH_BOOK_NO field */
	@Column(name = "CASH_BOOK_NAME", nullable = false, length = 250)
	private String CASH_BOOK_NAME;
	
	@Column(name = "CASH_BOOK_ID", nullable = false)
	private Integer	CASH_BOOK_ID;

	/** The value for the CASH_DATE DATE field */
	@Basic(optional = false)
	@Column(name = "CASH_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CASH_DATE;

	/** The value for the CASH_CREDIT field */
	@Column(name = "CASH_CREDIT", nullable = false)
	private Double CASH_CREDIT;

	/** The value for the CASH_DEBIT field */
	@Column(name = "CASH_DEBIT", nullable = false)
	private Double CASH_DEBIT;

	/** The value for the CASH_BALANCE field */
	@Column(name = "CASH_DAY_BALANCE", nullable = false)
	private Double CASH_DAY_BALANCE;

	/** The value for the CASH_BALANCE field */
	@Column(name = "CASH_ALL_BALANCE", nullable = false)
	private Double CASH_ALL_BALANCE;

	/** The value for the CASH_STATUS field */
	@Column(name = "CASH_STATUS_ID", nullable = false)
	private short CASH_STATUS_ID;

	/** The value for the CASH_CLOSE_BY field */
	@Column(name = "CASH_CLOSED_BY", nullable = true, length = 150)
	private String CASH_CLOSED_BY;

	/** The value for the CASH_CLOSE_BY field */
	@Column(name = "CASH_CLOSED_EMAIL", nullable = true, length = 150)
	private String CASH_CLOSED_EMAIL;

	/** The value for the CASH_CLOSE_REMARKS field */
	@Column(name = "CASH_REMARKS", nullable = true, length = 500)
	private String CASH_REMARKS;

	/** The value for the CASH_CLOSED_DATE DATE field */
	@Basic(optional = true)
	@Column(name = "CASH_CLOSED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date CASH_CLOSED_DATE;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer	COMPANY_ID;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public CashBookBalance() {
		super();
	}

	public CashBookBalance(long cBBID, String cASH_BOOK_NO, Date cASH_DATE, Double cASH_CREDIT, Double cASH_DEBIT,
			Double cASH_DAY_BALANCE, Double cASH_ALL_BALANCE,  String cASH_CLOSED_BY,
			String cASH_CLOSED_EMAIL, String cASH_REMARKS, Date cASH_CLOSED_DATE) {
		super();
		CBBID = cBBID;
		CASH_BOOK_NAME = cASH_BOOK_NO;
		CASH_DATE = cASH_DATE;
		CASH_CREDIT = cASH_CREDIT;
		CASH_DEBIT = cASH_DEBIT;
		CASH_DAY_BALANCE = cASH_DAY_BALANCE;
		CASH_ALL_BALANCE = cASH_ALL_BALANCE;
		CASH_CLOSED_BY = cASH_CLOSED_BY;
		CASH_CLOSED_EMAIL = cASH_CLOSED_EMAIL;
		CASH_REMARKS = cASH_REMARKS;
		CASH_CLOSED_DATE = cASH_CLOSED_DATE;
	}

	/**
	 * @return the cBBID
	 */
	public long getCBBID() {
		return CBBID;
	}

	/**
	 * @param cBBID
	 *            the cBBID to set
	 */
	public void setCBBID(long cBBID) {
		CBBID = cBBID;
	}


	
	/**
	 * @return the cASH_BOOK_NAME
	 */
	public String getCASH_BOOK_NAME() {
		return CASH_BOOK_NAME;
	}

	/**
	 * @param cASH_BOOK_NAME
	 *            the cASH_BOOK_NAME to set
	 */
	public void setCASH_BOOK_NAME(String cASH_BOOK_NAME) {
		CASH_BOOK_NAME = cASH_BOOK_NAME;
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
	 * @return the cASH_CREDIT
	 */
	public Double getCASH_CREDIT() {
		return CASH_CREDIT;
	}

	/**
	 * @param cASH_CREDIT
	 *            the cASH_CREDIT to set
	 */
	public void setCASH_CREDIT(Double cASH_CREDIT) {
		CASH_CREDIT = cASH_CREDIT;
	}

	/**
	 * @return the cASH_DEBIT
	 */
	public Double getCASH_DEBIT() {
		return CASH_DEBIT;
	}

	/**
	 * @param cASH_DEBIT
	 *            the cASH_DEBIT to set
	 */
	public void setCASH_DEBIT(Double cASH_DEBIT) {
		CASH_DEBIT = cASH_DEBIT;
	}

	/**
	 * @return the cASH_BALANCE
	 */
	public Double getCASH_DAY_BALANCE() {
		return CASH_DAY_BALANCE;
	}

	/**
	 * @param cASH_BALANCE
	 *            the cASH_BALANCE to set
	 */
	public void setCASH_DAY_BALANCE(Double cASH_BALANCE) {
		CASH_DAY_BALANCE = cASH_BALANCE;
	}

	/**
	 * @return the cASH_ALL_BALANCE
	 */
	public Double getCASH_ALL_BALANCE() {
		return CASH_ALL_BALANCE;
	}

	/**
	 * @param cASH_ALL_BALANCE
	 *            the cASH_ALL_BALANCE to set
	 */
	public void setCASH_ALL_BALANCE(Double cASH_ALL_BALANCE) {
		CASH_ALL_BALANCE = cASH_ALL_BALANCE;
	}


	public short getCASH_BOOK_MAIN_ID() {
		return CASH_BOOK_MAIN_ID;
	}

	public void setCASH_BOOK_MAIN_ID(short cASH_BOOK_MAIN_ID) {
		CASH_BOOK_MAIN_ID = cASH_BOOK_MAIN_ID;
	}

	public short getCASH_STATUS_ID() {
		return CASH_STATUS_ID;
	}

	public void setCASH_STATUS_ID(short cASH_STATUS_ID) {
		CASH_STATUS_ID = cASH_STATUS_ID;
	}

	/**
	 * @return the cASH_CLOSED_BY
	 */
	public String getCASH_CLOSED_BY() {
		return CASH_CLOSED_BY;
	}

	/**
	 * @param cASH_CLOSED_BY
	 *            the cASH_CLOSED_BY to set
	 */
	public void setCASH_CLOSED_BY(String cASH_CLOSED_BY) {
		CASH_CLOSED_BY = cASH_CLOSED_BY;
	}

	/**
	 * @return the cASH_CLOSED_EMAIL
	 */
	public String getCASH_CLOSED_EMAIL() {
		return CASH_CLOSED_EMAIL;
	}

	/**
	 * @param cASH_CLOSED_EMAIL
	 *            the cASH_CLOSED_EMAIL to set
	 */
	public void setCASH_CLOSED_EMAIL(String cASH_CLOSED_EMAIL) {
		CASH_CLOSED_EMAIL = cASH_CLOSED_EMAIL;
	}

	/**
	 * @return the cASH_CLOSED_DATE
	 */
	public Date getCASH_CLOSED_DATE() {
		return CASH_CLOSED_DATE;
	}

	/**
	 * @param cASH_CLOSED_DATE
	 *            the cASH_CLOSED_DATE to set
	 */
	public void setCASH_CLOSED_DATE(Date cASH_CLOSED_DATE) {
		CASH_CLOSED_DATE = cASH_CLOSED_DATE;
	}

	/**
	 * @return the cASH_REMARKS
	 */
	public String getCASH_REMARKS() {
		return CASH_REMARKS;
	}

	/**
	 * @param cASH_REMARKS
	 *            the cASH_REMARKS to set
	 */
	public void setCASH_REMARKS(String cASH_REMARKS) {
		CASH_REMARKS = cASH_REMARKS;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	public Integer getCASH_BOOK_ID() {
		return CASH_BOOK_ID;
	}

	public void setCASH_BOOK_ID(Integer cASH_BOOK_ID) {
		CASH_BOOK_ID = cASH_BOOK_ID;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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
		result = prime * result + ((CASH_DAY_BALANCE == null) ? 0 : CASH_DAY_BALANCE.hashCode());
		result = prime * result + ((CASH_BOOK_NAME == null) ? 0 : CASH_BOOK_NAME.hashCode());
		result = prime * result + ((CASH_CLOSED_DATE == null) ? 0 : CASH_CLOSED_DATE.hashCode());
		result = prime * result + ((CASH_CREDIT == null) ? 0 : CASH_CREDIT.hashCode());
		result = prime * result + ((CASH_DEBIT == null) ? 0 : CASH_DEBIT.hashCode());
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
		CashBookBalance other = (CashBookBalance) obj;
		if (CASH_DAY_BALANCE == null) {
			if (other.CASH_DAY_BALANCE != null)
				return false;
		} else if (!CASH_DAY_BALANCE.equals(other.CASH_DAY_BALANCE))
			return false;
		if (CASH_BOOK_NAME == null) {
			if (other.CASH_BOOK_NAME != null)
				return false;
		} else if (!CASH_BOOK_NAME.equals(other.CASH_BOOK_NAME))
			return false;
		if (CASH_CLOSED_DATE == null) {
			if (other.CASH_CLOSED_DATE != null)
				return false;
		} else if (!CASH_CLOSED_DATE.equals(other.CASH_CLOSED_DATE))
			return false;
		if (CASH_CREDIT == null) {
			if (other.CASH_CREDIT != null)
				return false;
		} else if (!CASH_CREDIT.equals(other.CASH_CREDIT))
			return false;
		if (CASH_DEBIT == null) {
			if (other.CASH_DEBIT != null)
				return false;
		} else if (!CASH_DEBIT.equals(other.CASH_DEBIT))
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
		builder.append("CashBookBalance [CBBID=");
		builder.append(CBBID);
		builder.append(", CASH_BOOK_MAIN_ID=");
		builder.append(CASH_BOOK_MAIN_ID);
		builder.append(", CASH_BOOK_NAME=");
		builder.append(CASH_BOOK_NAME);
		builder.append(", CASH_DATE=");
		builder.append(CASH_DATE);
		builder.append(", CASH_CREDIT=");
		builder.append(CASH_CREDIT);
		builder.append(", CASH_DEBIT=");
		builder.append(CASH_DEBIT);
		builder.append(", CASH_DAY_BALANCE=");
		builder.append(CASH_DAY_BALANCE);
		builder.append(", CASH_ALL_BALANCE=");
		builder.append(CASH_ALL_BALANCE);
		builder.append(", CASH_STATUS_ID=");
		builder.append(CASH_STATUS_ID);
		builder.append(", CASH_CLOSED_BY=");
		builder.append(CASH_CLOSED_BY);
		builder.append(", CASH_CLOSED_EMAIL=");
		builder.append(CASH_CLOSED_EMAIL);
		builder.append(", CASH_REMARKS=");
		builder.append(CASH_REMARKS);
		builder.append(", CASH_CLOSED_DATE=");
		builder.append(CASH_CLOSED_DATE);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append("]");
		return builder.toString();
	}
}
