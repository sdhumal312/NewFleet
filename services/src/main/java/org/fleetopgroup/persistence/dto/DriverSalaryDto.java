package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class DriverSalaryDto {

	private Long 	DSID;
	private int 	DRIVER_ID;
	private String 	DRIVER_FIRSTNAME;
	private String 	DRIVER_LASTNAME;
	private String 	DRIVER_GROUP;
	private String 	DRIVER_EMPNUMBER;
	private String 	DRIVER_JOBTITLE;
	private Double 	ESIAMOUNT_NO;
	private Double 	PFAMOUNT_NO;
	private Long 	TOTAL_WORKINGDAY;
	private Long 	TOTAL_EXTRA_WORKINGDAY;
	private Long 	TOTAL_ALL_WORKINGDAY;
	private Double 	MONTH_PERDAYSALARY;
	private Double 	MONTH_SALARY;
	private Double 	MONTH_EXTRA_SALARY;
	private Double 	TOTAL_ESIAMOUNT;
	private Double 	TOTAL_PFAMOUNT;
	private Double 	TOTAL_PREVIOUS_ADVANCE;
	private Double 	TOTAL_PENALTY;
	private Double 	TOTAL_ADVANCE;
	private Double 	TOTAL_ADVANCE_DEDUCTION;
	private Double 	TOTAL_PENALTY_DEDUCTION;
	private Double 	TOTAL_ADVANCE_PENALTY;
	private Double 	TOTAL_ADVANCE_BALANCE;
	private Double 	TOTAL_NETSALARY;
	private Double 	TOTAL_ALLOWANCE;
	private Double 	TOTAL_OTHEREXTRA;
	private Double 	TOTAL_EXTRA_NETSALARY;
	private Double 	TOTAL_HANDSALARY;
	private String 	SALARY_FROM_DATE;
	private String 	SALARY_TO_DATE;
	private String 	SALARY_STATUS;
	private short 	SALARY_STATUS_ID;
	private Long 	CREATED_BY_ID;
	private Long 	LASTMODIFIED_BY_ID;
	private String 	CREATEDBY;
	private String 	LASTMODIFIEDBY;
	private boolean markForDelete;
	private String 	CREATED_DATE;
	private String 	LASTUPDATED_DATE;
	private Date 	SALARY_FROM_DATE_ON;
	private Date 	SALARY_TO_DATE_ON;
	private Date 	LASTUPDATED_DATE_ON;
	private Date 	CREATED_DATE_ON;
	private String 	DRIVER_ESINO;
	private String 	DRIVER_PFNO;
	private String 	DRIVER_BANKNUMBER;
	private Short 	DRIVER_SALARY_TYPE_ID;
	private String 	TRIP_SHEET_COUNT;
	private String 	TOTAL_KM_USAGE;
	private String 	driverFatherName;
	
	public DriverSalaryDto() {
		super();
	}

	public DriverSalaryDto(Long dSID, int dRIVER_ID, String dRIVER_FIRSTNAME, String dRIVER_LASTNAME,
			String dRIVER_GROUP, String dRIVER_EMPNUMBER, Double eSIAMOUNT_NO, Double pFAMOUNT_NO,
			Long tOTAL_WORKINGDAY, Double mONTH_PERDAYALARY, Double mONTH_SALARY, Double tOTAL_ESIAMOUNT,
			Double tOTAL_PFAMOUNT, Double tOTAL_ADVANCE, Double tOTAL_SALARY, String sALARY_FROM_DATE,
			String sALARY_TO_DATE, String sALARY_STATUS, String cREATEDBY, String lASTMODIFIEDBY, String cREATED_DATE,
			String lASTUPDATED_DATE) {
		super();
		DSID = dSID;
		DRIVER_ID = dRIVER_ID;
		DRIVER_FIRSTNAME = dRIVER_FIRSTNAME;
		DRIVER_LASTNAME = dRIVER_LASTNAME;
		DRIVER_GROUP = dRIVER_GROUP;
		DRIVER_EMPNUMBER = dRIVER_EMPNUMBER;
		ESIAMOUNT_NO = eSIAMOUNT_NO;
		PFAMOUNT_NO = pFAMOUNT_NO;
		TOTAL_WORKINGDAY = tOTAL_WORKINGDAY;
		MONTH_PERDAYSALARY = mONTH_PERDAYALARY;
		MONTH_SALARY = mONTH_SALARY;
		TOTAL_ESIAMOUNT = tOTAL_ESIAMOUNT;
		TOTAL_PFAMOUNT = tOTAL_PFAMOUNT;
		TOTAL_ADVANCE = tOTAL_ADVANCE;
		TOTAL_NETSALARY = tOTAL_SALARY;
		SALARY_FROM_DATE = sALARY_FROM_DATE;
		SALARY_TO_DATE = sALARY_TO_DATE;
		SALARY_STATUS = sALARY_STATUS;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	/**
	 * @return the tOTAL_ALLOWANCE
	 */
	public Double getTOTAL_ALLOWANCE() {
		return TOTAL_ALLOWANCE;
	}

	/**
	 * @param tOTAL_ALLOWANCE
	 *            the tOTAL_ALLOWANCE to set
	 */
	public void setTOTAL_ALLOWANCE(Double tOTAL_ALLOWANCE) {
		TOTAL_ALLOWANCE = Utility.round(tOTAL_ALLOWANCE, 2);
	}

	/**
	 * @return the tOTAL_OTHEREXTRA
	 */
	public Double getTOTAL_OTHEREXTRA() {
		return TOTAL_OTHEREXTRA;
	}

	/**
	 * @param tOTAL_OTHEREXTRA
	 *            the tOTAL_OTHEREXTRA to set
	 */
	public void setTOTAL_OTHEREXTRA(Double tOTAL_OTHEREXTRA) {
		TOTAL_OTHEREXTRA = Utility.round(tOTAL_OTHEREXTRA, 2);
	}

	/**
	 * @return the dRIVER_JOBTITLE
	 */
	public String getDRIVER_JOBTITLE() {
		return DRIVER_JOBTITLE;
	}

	/**
	 * @param dRIVER_JOBTITLE
	 *            the dRIVER_JOBTITLE to set
	 */
	public void setDRIVER_JOBTITLE(String dRIVER_JOBTITLE) {
		DRIVER_JOBTITLE = dRIVER_JOBTITLE;
	}

	/**
	 * @return the tOTAL_ADVANCE_DEDUCTION
	 */
	public Double getTOTAL_ADVANCE_DEDUCTION() {
		return TOTAL_ADVANCE_DEDUCTION;
	}

	/**
	 * @param tOTAL_ADVANCE_DEDUCTION
	 *            the tOTAL_ADVANCE_DEDUCTION to set
	 */
	public void setTOTAL_ADVANCE_DEDUCTION(Double tOTAL_ADVANCE_DEDUCTION) {
		TOTAL_ADVANCE_DEDUCTION = Utility.round(tOTAL_ADVANCE_DEDUCTION, 2);
	}

	/**
	 * @return the tOTAL_PENALTY_DEDUCTION
	 */
	public Double getTOTAL_PENALTY_DEDUCTION() {
		return TOTAL_PENALTY_DEDUCTION;
	}

	/**
	 * @param tOTAL_PENALTY_DEDUCTION
	 *            the tOTAL_PENALTY_DEDUCTION to set
	 */
	public void setTOTAL_PENALTY_DEDUCTION(Double tOTAL_PENALTY_DEDUCTION) {
		TOTAL_PENALTY_DEDUCTION = Utility.round(tOTAL_PENALTY_DEDUCTION, 2);
	}

	public Long getDSID() {
		return DSID;
	}

	public void setDSID(Long dSID) {
		DSID = dSID;
	}

	public int getDRIVER_ID() {
		return DRIVER_ID;
	}

	public void setDRIVER_ID(int dRIVER_ID) {
		DRIVER_ID = dRIVER_ID;
	}

	public String getDRIVER_FIRSTNAME() {
		return DRIVER_FIRSTNAME;
	}

	public void setDRIVER_FIRSTNAME(String dRIVER_FIRSTNAME) {
		DRIVER_FIRSTNAME = dRIVER_FIRSTNAME;
	}

	public String getDRIVER_LASTNAME() {
		return DRIVER_LASTNAME;
	}

	public void setDRIVER_LASTNAME(String dRIVER_LASTNAME) {
		DRIVER_LASTNAME = dRIVER_LASTNAME;
	}

	public String getDRIVER_GROUP() {
		return DRIVER_GROUP;
	}

	public void setDRIVER_GROUP(String dRIVER_GROUP) {
		DRIVER_GROUP = dRIVER_GROUP;
	}

	public String getDRIVER_EMPNUMBER() {
		return DRIVER_EMPNUMBER;
	}

	public void setDRIVER_EMPNUMBER(String dRIVER_EMPNUMBER) {
		DRIVER_EMPNUMBER = dRIVER_EMPNUMBER;
	}

	public Double getESIAMOUNT_NO() {
		return ESIAMOUNT_NO;
	}

	public void setESIAMOUNT_NO(Double eSIAMOUNT_NO) {
		ESIAMOUNT_NO = Utility.round(eSIAMOUNT_NO, 2);
	}

	public Double getPFAMOUNT_NO() {
		return PFAMOUNT_NO;
	}

	public void setPFAMOUNT_NO(Double pFAMOUNT_NO) {
		PFAMOUNT_NO = Utility.round(pFAMOUNT_NO, 2);
	}

	public Long getTOTAL_WORKINGDAY() {
		return TOTAL_WORKINGDAY;
	}

	public void setTOTAL_WORKINGDAY(Long tOTAL_WORKINGDAY) {
		TOTAL_WORKINGDAY = tOTAL_WORKINGDAY;
	}

	public Double getMONTH_PERDAYSALARY() {
		return MONTH_PERDAYSALARY;
	}

	public void setMONTH_PERDAYSALARY(Double mONTH_PERDAYALARY) {
		MONTH_PERDAYSALARY = Utility.round(mONTH_PERDAYALARY,2);
	}

	public Double getMONTH_SALARY() {
		return MONTH_SALARY;
	}

	public void setMONTH_SALARY(Double mONTH_SALARY) {
		MONTH_SALARY =Utility.round(mONTH_SALARY, 2) ;
	}

	public Double getTOTAL_ESIAMOUNT() {
		return TOTAL_ESIAMOUNT;
	}

	public void setTOTAL_ESIAMOUNT(Double tOTAL_ESIAMOUNT) {
		TOTAL_ESIAMOUNT = Utility.round(tOTAL_ESIAMOUNT,2);
	}

	public Double getTOTAL_PFAMOUNT() {
		return TOTAL_PFAMOUNT;
	}

	public void setTOTAL_PFAMOUNT(Double tOTAL_PFAMOUNT) {
		TOTAL_PFAMOUNT = Utility.round(tOTAL_PFAMOUNT,2) ;
	}

	public Double getTOTAL_ADVANCE() {
		return TOTAL_ADVANCE;
	}

	public void setTOTAL_ADVANCE(Double tOTAL_ADVANCE) {
		TOTAL_ADVANCE = Utility.round(tOTAL_ADVANCE, 2);
	}

	public Double getTOTAL_NETSALARY() {
		return TOTAL_NETSALARY;
	}

	public void setTOTAL_NETSALARY(Double tOTAL_NETSALARY) {
		TOTAL_NETSALARY = Utility.round(tOTAL_NETSALARY, 2);
	}

	public Double getTOTAL_HANDSALARY() {
		return TOTAL_HANDSALARY;
	}

	public void setTOTAL_HANDSALARY(Double tOTAL_HANDSALARY) {
		TOTAL_HANDSALARY = Utility.round(tOTAL_HANDSALARY,2);
	}

	public String getSALARY_FROM_DATE() {
		return SALARY_FROM_DATE;
	}

	public void setSALARY_FROM_DATE(String sALARY_FROM_DATE) {
		SALARY_FROM_DATE = sALARY_FROM_DATE;
	}

	public String getSALARY_TO_DATE() {
		return SALARY_TO_DATE;
	}

	public void setSALARY_TO_DATE(String sALARY_TO_DATE) {
		SALARY_TO_DATE = sALARY_TO_DATE;
	}

	public String getSALARY_STATUS() {
		return SALARY_STATUS;
	}

	public void setSALARY_STATUS(String sALARY_STATUS) {
		SALARY_STATUS = sALARY_STATUS;
	}

	public String getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean ismarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete
	 *            the markForDelete to set
	 */
	public void setmarkForDelete(boolean mARKFORDELETE) {
		markForDelete = mARKFORDELETE;
	}

	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public String getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	public void setLASTUPDATED_DATE(String lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	/**
	 * @return the tOTAL_EXTRA_WORKINGDAY
	 */
	public Long getTOTAL_EXTRA_WORKINGDAY() {
		return TOTAL_EXTRA_WORKINGDAY;
	}

	/**
	 * @param tOTAL_EXTRA_WORKINGDAY
	 *            the tOTAL_EXTRA_WORKINGDAY to set
	 */
	public void setTOTAL_EXTRA_WORKINGDAY(Long tOTAL_EXTRA_WORKINGDAY) {
		TOTAL_EXTRA_WORKINGDAY = tOTAL_EXTRA_WORKINGDAY;
	}

	/**
	 * @return the mONTH_EXTRA_SALARY
	 */
	public Double getMONTH_EXTRA_SALARY() {
		return MONTH_EXTRA_SALARY;
	}

	/**
	 * @param mONTH_EXTRA_SALARY
	 *            the mONTH_EXTRA_SALARY to set
	 */
	public void setMONTH_EXTRA_SALARY(Double mONTH_EXTRA_SALARY) {
		MONTH_EXTRA_SALARY = Utility.round(mONTH_EXTRA_SALARY, 2);
	}

	/**
	 * @return the tOTAL_EXTRA_NETSALARY
	 */
	public Double getTOTAL_EXTRA_NETSALARY() {
		return TOTAL_EXTRA_NETSALARY;
	}

	/**
	 * @param tOTAL_EXTRA_NETSALARY
	 *            the tOTAL_EXTRA_NETSALARY to set
	 */
	public void setTOTAL_EXTRA_NETSALARY(Double tOTAL_EXTRA_NETSALARY) {
		TOTAL_EXTRA_NETSALARY = Utility.round(tOTAL_EXTRA_NETSALARY, 2);
	}

	/**
	 * @return the tOTAL_ALL_WORKINGDAY
	 */
	public Long getTOTAL_ALL_WORKINGDAY() {
		return TOTAL_ALL_WORKINGDAY;
	}

	/**
	 * @param tOTAL_ALL_WORKINGDAY
	 *            the tOTAL_ALL_WORKINGDAY to set
	 */
	public void setTOTAL_ALL_WORKINGDAY(Long tOTAL_ALL_WORKINGDAY) {
		TOTAL_ALL_WORKINGDAY = tOTAL_ALL_WORKINGDAY;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete
	 *            the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the tOTAL_PREVIOUS_ADVANCE
	 */
	public Double getTOTAL_PREVIOUS_ADVANCE() {
		return TOTAL_PREVIOUS_ADVANCE;
	}

	/**
	 * @param tOTAL_PREVIOUS_ADVANCE
	 *            the tOTAL_PREVIOUS_ADVANCE to set
	 */
	public void setTOTAL_PREVIOUS_ADVANCE(Double tOTAL_PREVIOUS_ADVANCE) {
		TOTAL_PREVIOUS_ADVANCE = Utility.round(tOTAL_PREVIOUS_ADVANCE, 2) ;
	}

	/**
	 * @return the tOTAL_PENALTY
	 */
	public Double getTOTAL_PENALTY() {
		return TOTAL_PENALTY;
	}

	/**
	 * @param tOTAL_PENALTY
	 *            the tOTAL_PENALTY to set
	 */
	public void setTOTAL_PENALTY(Double tOTAL_PENALTY) {
		TOTAL_PENALTY = Utility.round(tOTAL_PENALTY, 2);
	}

	/**
	 * @return the tOTAL_ADVANCE_BALANCE
	 */
	public Double getTOTAL_ADVANCE_BALANCE() {
		return TOTAL_ADVANCE_BALANCE;
	}

	/**
	 * @param tOTAL_ADVANCE_BALANCE
	 *            the tOTAL_ADVANCE_BALANCE to set
	 */
	public void setTOTAL_ADVANCE_BALANCE(Double tOTAL_ADVANCE_BALANCE) {
		TOTAL_ADVANCE_BALANCE = Utility.round(tOTAL_ADVANCE_BALANCE, 2);
	}

	/**
	 * @return the tOTAL_ADVANCE_PENALTY
	 */
	public Double getTOTAL_ADVANCE_PENALTY() {
		return TOTAL_ADVANCE_PENALTY;
	}

	/**
	 * @param tOTAL_ADVANCE_PENALTY
	 *            the tOTAL_ADVANCE_PENALTY to set
	 */
	public void setTOTAL_ADVANCE_PENALTY(Double tOTAL_ADVANCE_PENALTY) {
		TOTAL_ADVANCE_PENALTY = Utility.round(tOTAL_ADVANCE_PENALTY, 2);
	}

	public short getSALARY_STATUS_ID() {
		return SALARY_STATUS_ID;
	}

	public void setSALARY_STATUS_ID(short sALARY_STATUS_ID) {
		SALARY_STATUS_ID = sALARY_STATUS_ID;
	}

	public Long getCREATED_BY_ID() {
		return CREATED_BY_ID;
	}

	public void setCREATED_BY_ID(Long cREATED_BY_ID) {
		CREATED_BY_ID = cREATED_BY_ID;
	}

	public Long getLASTMODIFIED_BY_ID() {
		return LASTMODIFIED_BY_ID;
	}

	public void setLASTMODIFIED_BY_ID(Long lASTMODIFIED_BY_ID) {
		LASTMODIFIED_BY_ID = lASTMODIFIED_BY_ID;
	}

	/**
	 * @return the sALARY_FROM_DATE_ON
	 */
	public Date getSALARY_FROM_DATE_ON() {
		return SALARY_FROM_DATE_ON;
	}

	/**
	 * @param sALARY_FROM_DATE_ON the sALARY_FROM_DATE_ON to set
	 */
	public void setSALARY_FROM_DATE_ON(Date sALARY_FROM_DATE_ON) {
		SALARY_FROM_DATE_ON = sALARY_FROM_DATE_ON;
	}

	/**
	 * @return the sALARY_TO_DATE_ON
	 */
	public Date getSALARY_TO_DATE_ON() {
		return SALARY_TO_DATE_ON;
	}

	/**
	 * @param sALARY_TO_DATE_ON the sALARY_TO_DATE_ON to set
	 */
	public void setSALARY_TO_DATE_ON(Date sALARY_TO_DATE_ON) {
		SALARY_TO_DATE_ON = sALARY_TO_DATE_ON;
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
	 * @return the cREATED_DATE_ON
	 */
	public Date getCREATED_DATE_ON() {
		return CREATED_DATE_ON;
	}

	/**
	 * @param cREATED_DATE_ON the cREATED_DATE_ON to set
	 */
	public void setCREATED_DATE_ON(Date cREATED_DATE_ON) {
		CREATED_DATE_ON = cREATED_DATE_ON;
	}

	/**
	 * @return the dRIVER_ESINO
	 */
	public String getDRIVER_ESINO() {
		return DRIVER_ESINO;
	}

	/**
	 * @param dRIVER_ESINO the dRIVER_ESINO to set
	 */
	public void setDRIVER_ESINO(String dRIVER_ESINO) {
		DRIVER_ESINO = dRIVER_ESINO;
	}

	/**
	 * @return the dRIVER_PFNO
	 */
	public String getDRIVER_PFNO() {
		return DRIVER_PFNO;
	}

	/**
	 * @param dRIVER_PFNO the dRIVER_PFNO to set
	 */
	public void setDRIVER_PFNO(String dRIVER_PFNO) {
		DRIVER_PFNO = dRIVER_PFNO;
	}

	/**
	 * @return the dRIVER_BANKNUMBER
	 */
	public String getDRIVER_BANKNUMBER() {
		return DRIVER_BANKNUMBER;
	}

	/**
	 * @param dRIVER_BANKNUMBER the dRIVER_BANKNUMBER to set
	 */
	public void setDRIVER_BANKNUMBER(String dRIVER_BANKNUMBER) {
		DRIVER_BANKNUMBER = dRIVER_BANKNUMBER;
	}

	public Short getDRIVER_SALARY_TYPE_ID() {
		return DRIVER_SALARY_TYPE_ID;
	}

	public void setDRIVER_SALARY_TYPE_ID(Short dRIVER_SALARY_TYPE_ID) {
		DRIVER_SALARY_TYPE_ID = dRIVER_SALARY_TYPE_ID;
	}

	public String getTRIP_SHEET_COUNT() {
		return TRIP_SHEET_COUNT;
	}

	public void setTRIP_SHEET_COUNT(String tRIP_SHEET_COUNT) {
		TRIP_SHEET_COUNT = tRIP_SHEET_COUNT;
	}

	public String getTOTAL_KM_USAGE() {
		return TOTAL_KM_USAGE;
	}

	public void setTOTAL_KM_USAGE(String tOTAL_KM_USAGE) {
		TOTAL_KM_USAGE = tOTAL_KM_USAGE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DRIVER_GROUP == null) ? 0 : DRIVER_GROUP.hashCode());
		result = prime * result + DRIVER_ID;
		result = prime * result + ((DRIVER_LASTNAME == null) ? 0 : DRIVER_LASTNAME.hashCode());
		result = prime * result + ((DSID == null) ? 0 : DSID.hashCode());
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
		DriverSalaryDto other = (DriverSalaryDto) obj;
		if (DRIVER_GROUP == null) {
			if (other.DRIVER_GROUP != null)
				return false;
		} else if (!DRIVER_GROUP.equals(other.DRIVER_GROUP))
			return false;
		if (DRIVER_ID != other.DRIVER_ID)
			return false;
		if (DRIVER_LASTNAME == null) {
			if (other.DRIVER_LASTNAME != null)
				return false;
		} else if (!DRIVER_LASTNAME.equals(other.DRIVER_LASTNAME))
			return false;
		if (DSID == null) {
			if (other.DSID != null)
				return false;
		} else if (!DSID.equals(other.DSID))
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
		builder.append("DriverSalaryDto [DSID=");
		builder.append(DSID);
		builder.append(", DRIVER_ID=");
		builder.append(DRIVER_ID);
		builder.append(", DRIVER_FIRSTNAME=");
		builder.append(DRIVER_FIRSTNAME);
		builder.append(", DRIVER_LASTNAME=");
		builder.append(DRIVER_LASTNAME);
		builder.append(", DRIVER_GROUP=");
		builder.append(DRIVER_GROUP);
		builder.append(", DRIVER_EMPNUMBER=");
		builder.append(DRIVER_EMPNUMBER);
		builder.append(", ESIAMOUNT_NO=");
		builder.append(ESIAMOUNT_NO);
		builder.append(", PFAMOUNT_NO=");
		builder.append(PFAMOUNT_NO);
		builder.append(", TOTAL_WORKINGDAY=");
		builder.append(TOTAL_WORKINGDAY);
		builder.append(", TOTAL_EXTRA_WORKINGDAY=");
		builder.append(TOTAL_EXTRA_WORKINGDAY);
		builder.append(", MONTH_PERDAYSALARY=");
		builder.append(MONTH_PERDAYSALARY);
		builder.append(", MONTH_SALARY=");
		builder.append(MONTH_SALARY);
		builder.append(", MONTH_EXTRA_SALARY=");
		builder.append(MONTH_EXTRA_SALARY);
		builder.append(", TOTAL_ESIAMOUNT=");
		builder.append(TOTAL_ESIAMOUNT);
		builder.append(", TOTAL_PFAMOUNT=");
		builder.append(TOTAL_PFAMOUNT);
		builder.append(", TOTAL_PREVIOUS_ADVANCE=");
		builder.append(TOTAL_PREVIOUS_ADVANCE);
		builder.append(", TOTAL_PENALTY=");
		builder.append(TOTAL_PENALTY);
		builder.append(", TOTAL_ADVANCE=");
		builder.append(TOTAL_ADVANCE);
		builder.append(", TOTAL_ADVANCE_DEDUCTION=");
		builder.append(TOTAL_ADVANCE_DEDUCTION);
		builder.append(", TOTAL_PENALTY_DEDUCTION=");
		builder.append(TOTAL_PENALTY_DEDUCTION);
		builder.append(", TOTAL_ADVANCE_PENALTY=");
		builder.append(TOTAL_ADVANCE_PENALTY);
		builder.append(", TOTAL_ADVANCE_BALANCE=");
		builder.append(TOTAL_ADVANCE_BALANCE);
		builder.append(", TOTAL_NETSALARY=");
		builder.append(TOTAL_NETSALARY);
		builder.append(", TOTAL_EXTRA_NETSALARY=");
		builder.append(TOTAL_EXTRA_NETSALARY);
		builder.append(", TOTAL_HANDSALARY=");
		builder.append(TOTAL_HANDSALARY);
		builder.append(", SALARY_FROM_DATE=");
		builder.append(SALARY_FROM_DATE);
		builder.append(", SALARY_TO_DATE=");
		builder.append(SALARY_TO_DATE);
		builder.append(", SALARY_STATUS=");
		builder.append(SALARY_STATUS);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append("]");
		return builder.toString();
	}

	public String getDriverFatherName() {
		return driverFatherName;
	}

	public void setDriverFatherName(String driverFatherName) {
		this.driverFatherName = driverFatherName;
	}

}
