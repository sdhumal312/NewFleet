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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "IssuesTasks")
public class IssuesTasks implements Serializable   {

	private static final long serialVersionUID = 1L;

	/** This value for the ISSUES Task_Id name field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ISSUES_TASK_ID")
	private Long ISSUES_TASK_ID;
	
	@Column(name = "ISSUES_TASK_STATUS_ID")
	private short ISSUES_TASK_STATUS_ID;
	
	@Column(name = "ISSUES_CHANGE_STATUS_ID")
	private short ISSUES_CHANGE_STATUS_ID;

	/** This value for the ISSUES SubJob Type Task name field */
	@Column(name = "ISSUES_CREATEBY_ID")
	private Long ISSUES_CREATEBY_ID;

	/** This value for close by name */
	@Column(name = "ISSUES_REASON", length = 1000)
	private String ISSUES_REASON;

	@ManyToOne
	@JoinColumn(name = "ISSUES_ID")
	private Issues ISSUES;
	
	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "ISSUES_CREATED_DATE", updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date ISSUES_CREATED_DATE;
	
	@Column(name = "COMPANY_ID" , nullable = false)
	private Integer COMPANY_ID;

	@Column(name = "markForDelete" , nullable = false)
	private boolean markForDelete;
	
	public IssuesTasks() {
		super();
	}

	public IssuesTasks(Long iSSUES_TASK_ID, short iSSUES_TASK_STATUS, Long iSSUES_CREATEBY_ID,
			String iSSUES_CREATEBY_NAME, String iSSUES_REASON,  Issues iSSUES, Integer companyId) {
		super();
		ISSUES_TASK_ID = iSSUES_TASK_ID;
		ISSUES_TASK_STATUS_ID = iSSUES_TASK_STATUS;
		ISSUES_CREATEBY_ID = iSSUES_CREATEBY_ID;
		ISSUES_REASON = iSSUES_REASON;
		ISSUES = iSSUES;
		COMPANY_ID	= companyId;
	}
	
	

	public IssuesTasks(Long iSSUES_TASK_ID, short iSSUES_TASK_STATUS, Long iSSUES_CREATEBY_ID,
			String iSSUES_CREATEBY_NAME, String iSSUES_REASON,Issues iSSUES,
			Date iSSUES_CREATED_DATE, Integer companyId) {
		super();
		ISSUES_TASK_ID = iSSUES_TASK_ID;
		ISSUES_TASK_STATUS_ID = iSSUES_TASK_STATUS;
		ISSUES_CREATEBY_ID = iSSUES_CREATEBY_ID;
	//	ISSUES_CREATEBY_NAME = iSSUES_CREATEBY_NAME;
		ISSUES_REASON = iSSUES_REASON;
		ISSUES = iSSUES;
		ISSUES_CREATED_DATE = iSSUES_CREATED_DATE;
		COMPANY_ID	= companyId;
	}

	/**
	 * @return the iSSUES_TASK_ID
	 */
	public Long getISSUES_TASK_ID() {
		return ISSUES_TASK_ID;
	}

	/**
	 * @param iSSUES_TASK_ID
	 *            the iSSUES_TASK_ID to set
	 */
	public void setISSUES_TASK_ID(Long iSSUES_TASK_ID) {
		ISSUES_TASK_ID = iSSUES_TASK_ID;
	}

/*	*//**
	 * @return the iSSUES_TASK_STATUS
	 *//*
	public String getISSUES_TASK_STATUS() {
		return ISSUES_TASK_STATUS;
	}

	*//**
	 * @param iSSUES_TASK_STATUS
	 *            the iSSUES_TASK_STATUS to set
	 *//*
	public void setISSUES_TASK_STATUS(String iSSUES_TASK_STATUS) {
		ISSUES_TASK_STATUS = iSSUES_TASK_STATUS;
	}*/

	/**
	 * @return the iSSUES_CREATEBY_ID
	 */
	public Long getISSUES_CREATEBY_ID() {
		return ISSUES_CREATEBY_ID;
	}

	/**
	 * @param iSSUES_CREATEBY_ID
	 *            the iSSUES_CREATEBY_ID to set
	 */
	public void setISSUES_CREATEBY_ID(Long iSSUES_CREATEBY_ID) {
		ISSUES_CREATEBY_ID = iSSUES_CREATEBY_ID;
	}

	/**
	 * @return the iSSUES_CREATEBY_NAME
	 *//*
	public String getISSUES_CREATEBY_NAME() {
		return ISSUES_CREATEBY_NAME;
	}

	*//**
	 * @param iSSUES_CREATEBY_NAME
	 *            the iSSUES_CREATEBY_NAME to set
	 *//*
	public void setISSUES_CREATEBY_NAME(String iSSUES_CREATEBY_NAME) {
		ISSUES_CREATEBY_NAME = iSSUES_CREATEBY_NAME;
	}
*/
	/**
	 * @return the iSSUES_REASON
	 */
	public String getISSUES_REASON() {
		return ISSUES_REASON;
	}

	/**
	 * @param iSSUES_REASON
	 *            the iSSUES_REASON to set
	 */
	public void setISSUES_REASON(String iSSUES_REASON) {
		ISSUES_REASON = iSSUES_REASON;
	}

	
	/**
	 * @return the iSSUES
	 */
	public Issues getISSUES() {
		return ISSUES;
	}

	/**
	 * @param iSSUES
	 *            the iSSUES to set
	 */
	public void setISSUES(Issues iSSUES) {
		ISSUES = iSSUES;
	}
	
	

	/**
	 * @return the iSSUES_CREATED_DATE
	 */
	public Date getISSUES_CREATED_DATE() {
		return ISSUES_CREATED_DATE;
	}

	/**
	 * @param iSSUES_CREATED_DATE the iSSUES_CREATED_DATE to set
	 */
	public void setISSUES_CREATED_DATE(Date iSSUES_CREATED_DATE) {
		ISSUES_CREATED_DATE = iSSUES_CREATED_DATE;
	}

	
/*	*//**
	 * @return the iSSUES_CHANGE_STATUS
	 *//*
	public String getISSUES_CHANGE_STATUS() {
		return ISSUES_CHANGE_STATUS;
	}

	*//**
	 * @param iSSUES_CHANGE_STATUS the iSSUES_CHANGE_STATUS to set
	 *//*
	public void setISSUES_CHANGE_STATUS(String iSSUES_CHANGE_STATUS) {
		ISSUES_CHANGE_STATUS = iSSUES_CHANGE_STATUS;
	}*/

   
	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/**
	 * @return the iSSUES_TASK_STATUS_ID
	 */
	public short getISSUES_TASK_STATUS_ID() {
		return ISSUES_TASK_STATUS_ID;
	}

	/**
	 * @param iSSUES_TASK_STATUS_ID the iSSUES_TASK_STATUS_ID to set
	 */
	public void setISSUES_TASK_STATUS_ID(short iSSUES_TASK_STATUS_ID) {
		ISSUES_TASK_STATUS_ID = iSSUES_TASK_STATUS_ID;
	}

	/**
	 * @return the iSSUES_CHANGE_STATUS_ID
	 */
	public short getISSUES_CHANGE_STATUS_ID() {
		return ISSUES_CHANGE_STATUS_ID;
	}

	/**
	 * @param iSSUES_CHANGE_STATUS_ID the iSSUES_CHANGE_STATUS_ID to set
	 */
	public void setISSUES_CHANGE_STATUS_ID(short iSSUES_CHANGE_STATUS_ID) {
		ISSUES_CHANGE_STATUS_ID = iSSUES_CHANGE_STATUS_ID;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IssuesTasks [ISSUES_TASK_ID=");
		builder.append(ISSUES_TASK_ID);
		/*builder.append(", ISSUES_TASK_STATUS=");
		builder.append(ISSUES_TASK_STATUS);
		builder.append(", ISSUES_CHANGE_STATUS=");
		builder.append(ISSUES_CHANGE_STATUS);*/
		builder.append(", ISSUES_CREATEBY_ID=");
		builder.append(ISSUES_CREATEBY_ID);
	/*	builder.append(", ISSUES_CREATEBY_NAME=");
		builder.append(ISSUES_CREATEBY_NAME);*/
		builder.append(", ISSUES_REASON=");
		builder.append(ISSUES_REASON);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		//builder.append(", ISSUES=");
		//builder.append(ISSUES);
		builder.append(", ISSUES_CREATED_DATE=");
		builder.append(ISSUES_CREATED_DATE);
		builder.append("]");
		return builder.toString();
	}

	

	

}