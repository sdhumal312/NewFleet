package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class CashBookNameDto {

	private Integer NAMEID;

	private String CASHBOOK_NAME;

	private String CASHBOOK_REMARKS;

	private String CREATEDBY;

	private Date CREATED;
	
	private Integer COMPANY_ID;
	
	private String LASTMODIFIEDBY;

	private Date LASTMODIFIEDON;
	
	private long VEHICLE_GROUP_ID;
	
	private String VEHICLE_GROUP;
	
	private String	CASHBOOK_CODE;
	
	private boolean	markForDelete;



	/**
	 * @return the nAMEID
	 */
	public Integer getNAMEID() {
		return NAMEID;
	}

	/**
	 * @param nAMEID
	 *            the nAMEID to set
	 */
	public void setNAMEID(Integer nAMEID) {
		NAMEID = nAMEID;
	}

	/**
	 * @return the cASHBOOK_NAME
	 */
	public String getCASHBOOK_NAME() {
		return CASHBOOK_NAME;
	}

	/**
	 * @param cASHBOOK_NAME
	 *            the cASHBOOK_NAME to set
	 */
	public void setCASHBOOK_NAME(String cASHBOOK_NAME) {
		CASHBOOK_NAME = cASHBOOK_NAME;
	}

	/**
	 * @return the cASHBOOK_REMARKS
	 */
	public String getCASHBOOK_REMARKS() {
		return CASHBOOK_REMARKS;
	}

	/**
	 * @param cASHBOOK_REMARKS
	 *            the cASHBOOK_REMARKS to set
	 */
	public void setCASHBOOK_REMARKS(String cASHBOOK_REMARKS) {
		CASHBOOK_REMARKS = cASHBOOK_REMARKS;
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
	 * @return the cREATED
	 */
	public Date getCREATED() {
		return CREATED;
	}

	/**
	 * @param cREATED
	 *            the cREATED to set
	 */
	public void setCREATED(Date cREATED) {
		CREATED = cREATED;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}

	public Date getLASTMODIFIEDON() {
		return LASTMODIFIEDON;
	}

	public void setLASTMODIFIEDON(Date lASTMODIFIEDON) {
		LASTMODIFIEDON = lASTMODIFIEDON;
	}

	

	/**
	 * @return the vEHICLE_GROUP_ID
	 */
	public long getVEHICLE_GROUP_ID() {
		return VEHICLE_GROUP_ID;
	}

	/**
	 * @param vEHICLE_GROUP_ID the vEHICLE_GROUP_ID to set
	 */
	public void setVEHICLE_GROUP_ID(long vEHICLE_GROUP_ID) {
		VEHICLE_GROUP_ID = vEHICLE_GROUP_ID;
	}

	/**
	 * @return the vEHICLE_GROUP
	 */
	public String getVEHICLE_GROUP() {
		return VEHICLE_GROUP;
	}

	/**
	 * @param vEHICLE_GROUP the vEHICLE_GROUP to set
	 */
	public void setVEHICLE_GROUP(String vEHICLE_GROUP) {
		VEHICLE_GROUP = vEHICLE_GROUP;
	}

	public String getCASHBOOK_CODE() {
		return CASHBOOK_CODE;
	}

	public void setCASHBOOK_CODE(String cASHBOOK_CODE) {
		CASHBOOK_CODE = cASHBOOK_CODE;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CashBookNameDto [NAMEID=");
		builder.append(NAMEID);
		builder.append(", CASHBOOK_NAME=");
		builder.append(CASHBOOK_NAME);
		builder.append(", CASHBOOK_REMARKS=");
		builder.append(CASHBOOK_REMARKS);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(", LASTMODIFIEDON=");
		builder.append(LASTMODIFIEDON);
		builder.append(", VEHICLE_GROUP_ID=");
		builder.append(VEHICLE_GROUP_ID);
		builder.append(", VEHICLE_GROUP=");
		builder.append(VEHICLE_GROUP);
		builder.append(", CASHBOOK_CODE=");
		builder.append(CASHBOOK_CODE);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

	
}
