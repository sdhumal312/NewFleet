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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DriverSalary")
public class DriverSalary implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DSID")
	private Long DSID;

	@Column(name = "DRIVER_ID")
	private int DRIVER_ID;

	@Column(name = "DRIVER_GROUP_ID")
	private Long DRIVER_GROUP_ID;

	@Column(name = "ESIAMOUNT_NO")
	private Double ESIAMOUNT_NO;

	@Column(name = "PFAMOUNT_NO")
	private Double PFAMOUNT_NO;

	@Column(name = "TOTAL_WORKINGDAY")
	private Long TOTAL_WORKINGDAY;

	@Column(name = "TOTAL_EXTRA_WORKINGDAY")
	private Long TOTAL_EXTRA_WORKINGDAY;

	@Column(name = "MONTH_PERDAYSALARY")
	private Double MONTH_PERDAYSALARY;

	@Column(name = "MONTH_SALARY")
	private Double MONTH_SALARY;

	@Column(name = "MONTH_EXTRA_SALARY")
	private Double MONTH_EXTRA_SALARY;

	@Column(name = "TOTAL_ESIAMOUNT")
	private Double TOTAL_ESIAMOUNT;

	@Column(name = "TOTAL_PFAMOUNT")
	private Double TOTAL_PFAMOUNT;

	@Column(name = "TOTAL_PREVIOUS_ADVANCE")
	private Double TOTAL_PREVIOUS_ADVANCE;

	@Column(name = "TOTAL_PENALTY")
	private Double TOTAL_PENALTY;

	@Column(name = "TOTAL_ADVANCE")
	private Double TOTAL_ADVANCE;
	
	@Column(name = "TOTAL_ADVANCE_DEDUCTION")
	private Double TOTAL_ADVANCE_DEDUCTION;
	
	@Column(name = "TOTAL_PENALTY_DEDUCTION")
	private Double TOTAL_PENALTY_DEDUCTION;
	
	@Column(name = "COMFIXID")
	private Long COMFIXID;
	
	@Column(name = "TOTAL_ALLOWANCE")
	private Double TOTAL_ALLOWANCE;
	
	@Column(name = "TOTAL_OTHEREXTRA")
	private Double TOTAL_OTHEREXTRA;
	
	@Column(name = "TOTAL_ADVANCE_PENALTY")
	private Double TOTAL_ADVANCE_PENALTY;

	@Column(name = "TOTAL_ADVANCE_BALANCE")
	private Double TOTAL_ADVANCE_BALANCE;

	@Column(name = "TOTAL_NETSALARY")
	private Double TOTAL_NETSALARY;

	@Column(name = "TOTAL_EXTRA_NETSALARY")
	private Double TOTAL_EXTRA_NETSALARY;

	@Column(name = "TOTAL_HANDSALARY")
	private Double TOTAL_HANDSALARY;

	/** The value for the SALARY_FROM_DATE DATE field */
	@Basic(optional = false)
	@Column(name = "SALARY_FROM_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date SALARY_FROM_DATE;

	/** The value for the SALARY_TO_DATE DATE field */
	@Basic(optional = false)
	@Column(name = "SALARY_TO_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date SALARY_TO_DATE;

	
	@Column(name = "SALARY_STATUS_ID", nullable = false)
	private short SALARY_STATUS_ID;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	@Column(name = "CREATED_BY_ID", nullable = false)
	private Long CREATED_BY_ID;
	
	@Column(name = "LASTMODIFIED_BY_ID", nullable = false)
	private Long LASTMODIFIED_BY_ID;

	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

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

	@Column(name ="DRIVER_SALARY_TYPE_ID")
	private Short DRIVER_SALARY_TYPE_ID;

	@Column(name = "TRIP_SHEET_COUNT")
	private Long TRIP_SHEET_COUNT;

	@Column(name = "TOTAL_KM_USAGE")
	private Long TOTAL_KM_USAGE;
	
	public DriverSalary() {
		super();
	}

	public DriverSalary(Long dSID, int dRIVER_ID,
			String dRIVER_EMPNUMBER, Double eSIAMOUNT_NO, Double pFAMOUNT_NO, Long tOTAL_WORKINGDAY,
			Double mONTH_PERDAYALARY, Double mONTH_SALARY, Double tOTAL_ESIAMOUNT, Double tOTAL_PFAMOUNT,
			Double tOTAL_ADVANCE, Double tOTAL_SALARY, Date sALARY_FROM_DATE, Date sALARY_TO_DATE, 
			Date cREATED_DATE, Date lASTUPDATED_DATE, Short driver_SALARY_TYPE_ID, Long trip_SHEET_COUNT, Long total_KM_USAGE) {
		super();
		DSID = dSID;
		DRIVER_ID = dRIVER_ID;
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
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
		DRIVER_SALARY_TYPE_ID = driver_SALARY_TYPE_ID;
		TRIP_SHEET_COUNT = trip_SHEET_COUNT;
		TOTAL_KM_USAGE = total_KM_USAGE;
	}

	
	public Long getDRIVER_GROUP_ID() {
		return DRIVER_GROUP_ID;
	}

	public void setDRIVER_GROUP_ID(Long dRIVER_GROUP_ID) {
		DRIVER_GROUP_ID = dRIVER_GROUP_ID;
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

	
	/*public String getDRIVER_EMPNUMBER() {
		return DRIVER_EMPNUMBER;
	}

	public void setDRIVER_EMPNUMBER(String dRIVER_EMPNUMBER) {
		DRIVER_EMPNUMBER = dRIVER_EMPNUMBER;
	}
*/
	public Double getESIAMOUNT_NO() {
		return ESIAMOUNT_NO;
	}

	public void setESIAMOUNT_NO(Double eSIAMOUNT_NO) {
		ESIAMOUNT_NO = eSIAMOUNT_NO;
	}

	public Double getPFAMOUNT_NO() {
		return PFAMOUNT_NO;
	}

	public void setPFAMOUNT_NO(Double pFAMOUNT_NO) {
		PFAMOUNT_NO = pFAMOUNT_NO;
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
		MONTH_PERDAYSALARY = mONTH_PERDAYALARY;
	}

	public Double getMONTH_SALARY() {
		return MONTH_SALARY;
	}

	public void setMONTH_SALARY(Double mONTH_SALARY) {
		MONTH_SALARY = mONTH_SALARY;
	}

	public Double getTOTAL_ESIAMOUNT() {
		return TOTAL_ESIAMOUNT;
	}

	public void setTOTAL_ESIAMOUNT(Double tOTAL_ESIAMOUNT) {
		TOTAL_ESIAMOUNT = tOTAL_ESIAMOUNT;
	}

	public Double getTOTAL_PFAMOUNT() {
		return TOTAL_PFAMOUNT;
	}

	public void setTOTAL_PFAMOUNT(Double tOTAL_PFAMOUNT) {
		TOTAL_PFAMOUNT = tOTAL_PFAMOUNT;
	}

	public Double getTOTAL_ADVANCE() {
		return TOTAL_ADVANCE;
	}

	public void setTOTAL_ADVANCE(Double tOTAL_ADVANCE) {
		TOTAL_ADVANCE = tOTAL_ADVANCE;
	}

	public Double getTOTAL_NETSALARY() {
		return TOTAL_NETSALARY;
	}

	public void setTOTAL_NETSALARY(Double tOTAL_SALARY) {
		TOTAL_NETSALARY = tOTAL_SALARY;
	}

	public Date getSALARY_FROM_DATE() {
		return SALARY_FROM_DATE;
	}

	public void setSALARY_FROM_DATE(Date sALARY_FROM_DATE) {
		SALARY_FROM_DATE = sALARY_FROM_DATE;
	}

	public Date getSALARY_TO_DATE() {
		return SALARY_TO_DATE;
	}

	public void setSALARY_TO_DATE(Date sALARY_TO_DATE) {
		SALARY_TO_DATE = sALARY_TO_DATE;
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

	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public Date getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	public void setLASTUPDATED_DATE(Date lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	public Double getTOTAL_HANDSALARY() {
		return TOTAL_HANDSALARY;
	}

	public void setTOTAL_HANDSALARY(Double tOTAL_HANDSALARY) {
		TOTAL_HANDSALARY = tOTAL_HANDSALARY;
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
		MONTH_EXTRA_SALARY = mONTH_EXTRA_SALARY;
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
		TOTAL_EXTRA_NETSALARY = tOTAL_EXTRA_NETSALARY;
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
		TOTAL_PREVIOUS_ADVANCE = tOTAL_PREVIOUS_ADVANCE;
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
		TOTAL_PENALTY = tOTAL_PENALTY;
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
		TOTAL_ADVANCE_BALANCE = tOTAL_ADVANCE_BALANCE;
	}
	

	/**
	 * @return the tOTAL_ADVANCE_PENALTY
	 */
	public Double getTOTAL_ADVANCE_PENALTY() {
		return TOTAL_ADVANCE_PENALTY;
	}

	/**
	 * @param tOTAL_ADVANCE_PENALTY the tOTAL_ADVANCE_PENALTY to set
	 */
	public void setTOTAL_ADVANCE_PENALTY(Double tOTAL_ADVANCE_PENALTY) {
		TOTAL_ADVANCE_PENALTY = tOTAL_ADVANCE_PENALTY;
	}

	
	/**
	 * @return the tOTAL_ADVANCE_DEDUCTION
	 */
	public Double getTOTAL_ADVANCE_DEDUCTION() {
		return TOTAL_ADVANCE_DEDUCTION;
	}

	/**
	 * @param tOTAL_ADVANCE_DEDUCTION the tOTAL_ADVANCE_DEDUCTION to set
	 */
	public void setTOTAL_ADVANCE_DEDUCTION(Double tOTAL_ADVANCE_DEDUCTION) {
		TOTAL_ADVANCE_DEDUCTION = tOTAL_ADVANCE_DEDUCTION;
	}

	/**
	 * @return the tOTAL_PENALTY_DEDUCTION
	 */
	public Double getTOTAL_PENALTY_DEDUCTION() {
		return TOTAL_PENALTY_DEDUCTION;
	}

	/**
	 * @param tOTAL_PENALTY_DEDUCTION the tOTAL_PENALTY_DEDUCTION to set
	 */
	public void setTOTAL_PENALTY_DEDUCTION(Double tOTAL_PENALTY_DEDUCTION) {
		TOTAL_PENALTY_DEDUCTION = tOTAL_PENALTY_DEDUCTION;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}
	
	

	/**
	 * @return the cOMFIXID
	 */
	public Long getCOMFIXID() {
		return COMFIXID;
	}

	/**
	 * @param cOMFIXID the cOMFIXID to set
	 */
	public void setCOMFIXID(Long cOMFIXID) {
		COMFIXID = cOMFIXID;
	}

	/**
	 * @return the tOTAL_ALLOWANCE
	 */
	public Double getTOTAL_ALLOWANCE() {
		return TOTAL_ALLOWANCE;
	}

	/**
	 * @param tOTAL_ALLOWANCE the tOTAL_ALLOWANCE to set
	 */
	public void setTOTAL_ALLOWANCE(Double tOTAL_ALLOWANCE) {
		TOTAL_ALLOWANCE = tOTAL_ALLOWANCE;
	}

	/**
	 * @return the tOTAL_OTHEREXTRA
	 */
	public Double getTOTAL_OTHEREXTRA() {
		return TOTAL_OTHEREXTRA;
	}

	/**
	 * @param tOTAL_OTHEREXTRA the tOTAL_OTHEREXTRA to set
	 */
	public void setTOTAL_OTHEREXTRA(Double tOTAL_OTHEREXTRA) {
		TOTAL_OTHEREXTRA = tOTAL_OTHEREXTRA;
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

	public Short getDRIVER_SALARY_TYPE_ID() {
		return DRIVER_SALARY_TYPE_ID;
	}

	public void setDRIVER_SALARY_TYPE_ID(Short dRIVER_SALARY_TYPE_ID) {
		DRIVER_SALARY_TYPE_ID = dRIVER_SALARY_TYPE_ID;
	}

	public Long getTRIP_SHEET_COUNT() {
		return TRIP_SHEET_COUNT;
	}

	public void setTRIP_SHEET_COUNT(Long tRIP_SHEET_COUNT) {
		TRIP_SHEET_COUNT = tRIP_SHEET_COUNT;
	}

	public Long getTOTAL_KM_USAGE() {
		return TOTAL_KM_USAGE;
	}

	public void setTOTAL_KM_USAGE(Long tOTAL_KM_USAGE) {
		TOTAL_KM_USAGE = tOTAL_KM_USAGE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + DRIVER_ID;
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
		DriverSalary other = (DriverSalary) obj;
		
		if (DRIVER_ID != other.DRIVER_ID)
			return false;
		if (DSID == null) {
			if (other.DSID != null)
				return false;
		} else if (!DSID.equals(other.DSID))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverSalary [DSID=");
		builder.append(DSID);
		builder.append(", DRIVER_ID=");
		builder.append(DRIVER_ID);
		/*builder.append(", DRIVER_EMPNUMBER=");
		builder.append(DRIVER_EMPNUMBER);*/
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
		builder.append(", COMFIXID=");
		builder.append(COMFIXID);
		builder.append(", TOTAL_ALLOWANCE=");
		builder.append(TOTAL_ALLOWANCE);
		builder.append(", TOTAL_OTHEREXTRA=");
		builder.append(TOTAL_OTHEREXTRA);
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
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append("]");
		return builder.toString();
	}

}
