package org.fleetopgroup.persistence.dto;

import java.util.Comparator;
import java.util.Date;

public class IssuesTasksDto  implements Comparator<IssuesTasksDto> {

	/** This value for the ISSUES Task_Id name field */
	private Long ISSUES_TASK_ID;

	/** This value for the ISSUES TASK_STATUS name field */
	private String ISSUES_TASK_STATUS;
	
	private short ISSUES_TASK_STATUS_ID;
	
	/** This value for the ISSUES TASK_STATUS name field */
    private String ISSUES_CHANGE_STATUS;
    
    private short ISSUES_CHANGE_STATUS_ID;

	/** This value for the ISSUES SubJob Type Task name field */
	private Long ISSUES_CREATEBY_ID;

	/** This value for the ISSUES SubJob Type_ Task name field */
	private String ISSUES_CREATEBY_NAME;

	/** This value for close by name */
	private String ISSUES_REASON;
	
	private Date ISSUES_CREATED_DATE_ON;


	/** The value for the ISSUES_Due DATE field */
	private String ISSUES_CREATED_DATE;


	public IssuesTasksDto() {
		super();
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

	/**
	 * @return the iSSUES_TASK_STATUS
	 */
	public String getISSUES_TASK_STATUS() {
		return ISSUES_TASK_STATUS;
	}

	/**
	 * @param iSSUES_TASK_STATUS
	 *            the iSSUES_TASK_STATUS to set
	 */
	public void setISSUES_TASK_STATUS(String iSSUES_TASK_STATUS) {
		ISSUES_TASK_STATUS = iSSUES_TASK_STATUS;
	}

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
	 */
	public String getISSUES_CREATEBY_NAME() {
		return ISSUES_CREATEBY_NAME;
	}

	/**
	 * @param iSSUES_CREATEBY_NAME
	 *            the iSSUES_CREATEBY_NAME to set
	 */
	public void setISSUES_CREATEBY_NAME(String iSSUES_CREATEBY_NAME) {
		ISSUES_CREATEBY_NAME = iSSUES_CREATEBY_NAME;
	}

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
	 * @return the iSSUES_CREATED_DATE
	 */
	public String getISSUES_CREATED_DATE() {
		return ISSUES_CREATED_DATE;
	}

	/**
	 * @param iSSUES_CREATED_DATE the iSSUES_CREATED_DATE to set
	 */
	public void setISSUES_CREATED_DATE(String iSSUES_CREATED_DATE) {
		ISSUES_CREATED_DATE = iSSUES_CREATED_DATE;
	}

	
	/**
	 * @return the iSSUES_CHANGE_STATUS
	 */
	public String getISSUES_CHANGE_STATUS() {
		return ISSUES_CHANGE_STATUS;
	}

	/**
	 * @param iSSUES_CHANGE_STATUS the iSSUES_CHANGE_STATUS to set
	 */
	public void setISSUES_CHANGE_STATUS(String iSSUES_CHANGE_STATUS) {
		ISSUES_CHANGE_STATUS = iSSUES_CHANGE_STATUS;
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
	 * @return the iSSUES_CREATED_DATE_ON
	 */
	public Date getISSUES_CREATED_DATE_ON() {
		return ISSUES_CREATED_DATE_ON;
	}



	/**
	 * @param iSSUES_CREATED_DATE_ON the iSSUES_CREATED_DATE_ON to set
	 */
	public void setISSUES_CREATED_DATE_ON(Date iSSUES_CREATED_DATE_ON) {
		ISSUES_CREATED_DATE_ON = iSSUES_CREATED_DATE_ON;
	}



	/**
	 * @param iSSUES_CHANGE_STATUS_ID the iSSUES_CHANGE_STATUS_ID to set
	 */
	public void setISSUES_CHANGE_STATUS_ID(short iSSUES_CHANGE_STATUS_ID) {
		ISSUES_CHANGE_STATUS_ID = iSSUES_CHANGE_STATUS_ID;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IssuesTasksDto [ISSUES_TASK_ID=");
		builder.append(ISSUES_TASK_ID);
		builder.append(", ISSUES_TASK_STATUS=");
		builder.append(ISSUES_TASK_STATUS);
		builder.append(", ISSUES_CHANGE_STATUS=");
		builder.append(ISSUES_CHANGE_STATUS);
		builder.append(", ISSUES_CREATEBY_ID=");
		builder.append(ISSUES_CREATEBY_ID);
		builder.append(", ISSUES_CREATEBY_NAME=");
		builder.append(ISSUES_CREATEBY_NAME);
		builder.append(", ISSUES_REASON=");
		builder.append(ISSUES_REASON);
		builder.append(", ISSUES_CREATED_DATE=");
		builder.append(ISSUES_CREATED_DATE);
		builder.append("]");
		return builder.toString();
	}



	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(IssuesTasksDto o1, IssuesTasksDto o2) {
		
		return  o1.ISSUES_TASK_ID.compareTo(o2.ISSUES_TASK_ID);
	}

	

}