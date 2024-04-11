package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class DriverFamilyDto {

	private long DFID;

	private int DRIVERID;

	private String DF_NAME;
	
	private String DF_RELATIONSHIP;
	
	private short DF_RELATIONSHIP_ID;

	private String DF_SEX;
	
	private short DF_SEX_ID;

	private Integer DF_AGE;
	
	Integer COMPANY_ID;

	private Long CREATEDBYID;

	private Long LASTMODIFIEDBYID;

	boolean markForDelete;

	private Date CREATED_DATE;

	private Date LASTUPDATED_DATE;



	/**
	 * @return the dFID
	 */
	public long getDFID() {
		return DFID;
	}

	/**
	 * @param dFID
	 *            the dFID to set
	 */
	public void setDFID(long dFID) {
		DFID = dFID;
	}

	/**
	 * @return the dRIVERID
	 */
	public int getDRIVERID() {
		return DRIVERID;
	}

	/**
	 * @param dRIVERID
	 *            the dRIVERID to set
	 */
	public void setDRIVERID(int dRIVERID) {
		DRIVERID = dRIVERID;
	}

	/**
	 * @return the dF_NAME
	 */
	public String getDF_NAME() {
		return DF_NAME;
	}

	/**
	 * @param dF_NAME
	 *            the dF_NAME to set
	 */
	public void setDF_NAME(String dF_NAME) {
		DF_NAME = dF_NAME;
	}

	/**
	 * @return the dF_RELATIONSHIP
	 *//*
	public String getDF_RELATIONSHIP() {
		return DF_RELATIONSHIP;
	}

	*//**
	 * @param dF_RELATIONSHIP
	 *            the dF_RELATIONSHIP to set
	 *//*
	public void setDF_RELATIONSHIP(String dF_RELATIONSHIP) {
		DF_RELATIONSHIP = dF_RELATIONSHIP;
	}

	*//**
	 * @return the dF_SEX
	 *//*
	public String getDF_SEX() {
		return DF_SEX;
	}

	*//**
	 * @param dF_SEX
	 *            the dF_SEX to set
	 *//*
	public void setDF_SEX(String dF_SEX) {
		DF_SEX = dF_SEX;
	}
*/
	/**
	 * @return the dF_AGE
	 */
	public Integer getDF_AGE() {
		return DF_AGE;
	}

	/**
	 * @param dF_AGE
	 *            the dF_AGE to set
	 */
	public void setDF_AGE(Integer dF_AGE) {
		DF_AGE = dF_AGE;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
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
	 * @return the dF_RELATIONSHIP_ID
	 */
	public short getDF_RELATIONSHIP_ID() {
		return DF_RELATIONSHIP_ID;
	}

	/**
	 * @param dF_RELATIONSHIP_ID the dF_RELATIONSHIP_ID to set
	 */
	public void setDF_RELATIONSHIP_ID(short dF_RELATIONSHIP_ID) {
		DF_RELATIONSHIP_ID = dF_RELATIONSHIP_ID;
	}

	/**
	 * @return the dF_SEX_ID
	 */
	public short getDF_SEX_ID() {
		return DF_SEX_ID;
	}

	/**
	 * @param dF_SEX_ID the dF_SEX_ID to set
	 */
	public void setDF_SEX_ID(short dF_SEX_ID) {
		DF_SEX_ID = dF_SEX_ID;
	}



	/**
	 * @return the dF_RELATIONSHIP
	 */
	public String getDF_RELATIONSHIP() {
		return DF_RELATIONSHIP;
	}

	/**
	 * @param dF_RELATIONSHIP the dF_RELATIONSHIP to set
	 */
	public void setDF_RELATIONSHIP(String dF_RELATIONSHIP) {
		DF_RELATIONSHIP = dF_RELATIONSHIP;
	}

	/**
	 * @return the dF_SEX
	 */
	public String getDF_SEX() {
		return DF_SEX;
	}

	/**
	 * @param dF_SEX the dF_SEX to set
	 */
	public void setDF_SEX(String dF_SEX) {
		DF_SEX = dF_SEX;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverFamily [DFID=");
		builder.append(DFID);
		builder.append(", DRIVERID=");
		builder.append(DRIVERID);
		builder.append(", DF_NAME=");
		builder.append(DF_NAME);
		builder.append(", DF_RELATIONSHIP_ID=");
		builder.append(DF_RELATIONSHIP_ID);
		builder.append(", DF_SEX_ID=");
		builder.append(DF_SEX_ID);
		builder.append(", DF_AGE=");
		builder.append(DF_AGE);
		builder.append(", CREATEDBYID=");
		builder.append(CREATEDBYID);
		builder.append(", LASTMODIFIEDBYID=");
		builder.append(LASTMODIFIEDBYID);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append("]");
		return builder.toString();
	}


}
