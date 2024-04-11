/**
 * 
 */
package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author fleetop
 *
 */
@Entity
@Table(name = "TripDailySheet")
public class TripDailySheet implements Serializable {
	// this Only For Sri Durgamma Daily Sheet in Expense Get TripDailyExpense
	// Income Get TripDailyIncome to Create Details
	private static final long serialVersionUID = 1L;

	/** The value for the TRIPCOLLID field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRIPDAILYID")
	private Long TRIPDAILYID;
	
	@Column(name = "TRIPDAILYNUMBER", nullable = false)
	private Long TRIPDAILYNUMBER;

	/** The value for the VEHICLEID field */
	@Column(name = "VEHICLEID")
	private Integer VEHICLEID;

	/** The value for the TRIP DRIVER ID field */
	@Column(name = "TRIP_DRIVER_ID")
	private int TRIP_DRIVER_ID;

	/** The value for the TRIP CONDUCTOR ID field */
	@Column(name = "TRIP_CONDUCTOR_ID")
	private int TRIP_CONDUCTOR_ID;

	/** The value for the TRIP_CLEANER_ID field */
	@Column(name = "TRIP_CLEANER_ID")
	private int TRIP_CLEANER_ID;

	/** The value for the TRIP_OPEN DATE field */
	@Column(name = "TRIP_OPEN_DATE")
	private Date TRIP_OPEN_DATE;

	/** The value for the TRIP_ROUTE_ID DATE field */
	@Column(name = "TRIP_ROUTE_ID")
	private Integer TRIP_ROUTE_ID;

	/** The value for the TRIP_DIESEL_LITER field */
	@Column(name = "TRIP_DIESEL")
	private Double TRIP_DIESEL;

	/** The value for the TRIP_DIESELKMPL field */
	@Column(name = "TRIP_DIESELKMPL")
	private Double TRIP_DIESELKMPL;

	/** The value for the TRIP_ROUTE_APPKM DATE field */
	@Column(name = "TRIP_ROUTE_APPKM")
	private Integer TRIP_ROUTE_APPKM;

	/** The value for the TRIP_TOTALPASSNGER TOTAL field */
	@Column(name = "TRIP_TOTALPASSNGER")
	private Integer TRIP_TOTALPASSNGER;
	
	/** The value for the TRIP_PASS  field */
	@Column(name = "TRIP_PASS_PASSNGER")
	private Integer TRIP_PASS_PASSNGER;

	/** The value for the TRIP_RFIDPASS AMOUNT field */
	@Column(name = "TRIP_RFIDPASS")
	private Integer TRIP_RFIDPASS;
	
	/** The value for the TRIP_RFID_AMOUNT field */
	@Column(name = "TRIP_RFID_AMOUNT")
	private Double TRIP_RFID_AMOUNT;

	/** The value for the TRIP_OPEN_KM DATE field */
	@Column(name = "TRIP_OPEN_KM", length = 10)
	private Integer TRIP_OPEN_KM;

	/** The value for the TRIP_CLOSE_KM DATE field */
	@Column(name = "TRIP_CLOSE_KM", length = 10)
	private Integer TRIP_CLOSE_KM;

	/** The value for the TRIP_USAGE_KM DATE field */
	@Column(name = "TRIP_USAGE_KM", length = 10)
	private Integer TRIP_USAGE_KM;

	/** The value for the TRIP_TOTAL_EXPENSE AMOUNT field */
	@Column(name = "TOTAL_EXPENSE")
	private Double TOTAL_EXPENSE;

	/** The value for the TRIP_TOTAL_OVERTIME AMOUNT field */
	@Column(name = "TRIP_OVERTIME")
	private Double TRIP_OVERTIME;
	
	/** The value for the TRIP_SUBROUTE_ID,  DATE field */
	@Column(name = "TRIP_SUBROUTE_ID")
	private Integer TRIP_SUBROUTE_ID;

	/** The value for the TRIP_TOTAL_INCOME AMOUNT is Time Collection  Amount field */
	@Column(name = "TOTAL_INCOME_COLLECTION")
	private Double TOTAL_INCOME_COLLECTION;
	
	/** The value for the TOTAL_INCOME_COLLECTION AMOUNT is Net Cash Amount field */
	@Column(name = "TOTAL_INCOME")
	private Double TOTAL_INCOME;
	
	/** The value for the TRIP_TOTAL_INCOME AMOUNT field */
	@Column(name = "TOTAL_NET_INCOME")
	private Double TOTAL_NET_INCOME;
	
	/** The value for the TRIP_WT AMOUNT field */
	@Column(name = "TOTAL_WT")
	private Double TOTAL_WT;

	/** The value for the TRIP_TOTAL_BALANCE AMOUNT field */
	@Column(name = "TOTAL_BALANCE")
	private Double TOTAL_BALANCE;
	
	@Column(name = "TRIP_STATUS_ID", nullable = false)
	private short TRIP_STATUS_ID;

	/** The value for the REMARKS DATE field */
	@Column(name = "TRIP_REMARKS", length = 250)
	private String TRIP_REMARKS;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	/** The value for the TRIP_EXPENSE DATE field */
	@OneToMany(mappedBy = "tripDailysheet", fetch = FetchType.EAGER)
	private Set<TripDailyExpense> tripDailyexpense;

	/** The value for the TRIP_INCOME DATE field */
	@OneToMany(mappedBy = "tripDailysheet", fetch = FetchType.EAGER)
	private Set<TripDailyIncome> tripDailyincome;
	
	/** The value for the TRIP_INCOME DATE field */
	@OneToMany(mappedBy = "tripDailysheet", fetch = FetchType.EAGER)
	private Set<TripDailyTimeIncome> tripDailytimeincome;
	
	@Column(name = "CREATED_BY_ID", nullable = false)
	private Long CREATED_BY_ID;
	
	@Column(name = "LASTMODIFIED_BY_ID", nullable = false)
	private Long LASTMODIFIED_BY_ID;

	/** The value for the STATUS DATE field */
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "LASTUPDATED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date LASTUPDATED;
	
	@Column(name = "noOfRoundTrip",columnDefinition = "float default 0")
	private float	noOfRoundTrip;
	
	@Column(name = "CHALO_KM")
	private Integer CHALO_KM;
		
	
	@Column(name = "CHALO_AMOUNT")
	private Double CHALO_AMOUNT;
	

	public TripDailySheet() {
		super();

	}

	public TripDailySheet(Long tRIPDAILYID, Long tRIPDAILYNUMBER,Integer vEHICLEID,
			int tRIP_DRIVER_ID, int tRIP_CONDUCTOR_ID, 
			int tRIP_CLEANER_ID, Date tRIP_OPEN_DATE, Integer tRIP_ROUTE_ID,
			 Double tRIP_DIESEL, Double tRIP_DIESELKMPL, Integer tRIP_ROUTE_APPKM,
			Integer tRIP_TOTALPASSNGER, Integer tRIP_RFIDPASS, Integer tRIP_OPEN_KM, Integer tRIP_CLOSE_KM,
			Integer tRIP_USAGE_KM, Double tOTAL_EXPENSE, Double tOTAL_OVERTIME, Double tOTAL_INCOME,
			Double tOTAL_BALANCE, String tRIP_REMARKS, Set<TripDailyExpense> tripDailyexpense,
			Set<TripDailyIncome> tripDailyincome, Date cREATED,
			Date lASTUPDATED, Integer COMPANY_ID, long vEHICLE_GROUP_ID,Integer CHALO_KM,Double CHALO_AMOUNT) {
		super();
		this.TRIPDAILYID = tRIPDAILYID;
		this.TRIPDAILYNUMBER	= tRIPDAILYNUMBER;
		this.VEHICLEID = vEHICLEID;
		this.TRIP_DRIVER_ID = tRIP_DRIVER_ID;
		this.TRIP_CONDUCTOR_ID = tRIP_CONDUCTOR_ID;
		this.TRIP_CLEANER_ID = tRIP_CLEANER_ID;
		this.TRIP_OPEN_DATE = tRIP_OPEN_DATE;
		this.TRIP_ROUTE_ID = tRIP_ROUTE_ID;
		this.TRIP_DIESEL = tRIP_DIESEL;
		this.TRIP_DIESELKMPL = tRIP_DIESELKMPL;
		this.TRIP_ROUTE_APPKM = tRIP_ROUTE_APPKM;
		this.TRIP_TOTALPASSNGER = tRIP_TOTALPASSNGER;
		this.TRIP_RFIDPASS = tRIP_RFIDPASS;
		this.TRIP_OPEN_KM = tRIP_OPEN_KM;
		this.TRIP_CLOSE_KM = tRIP_CLOSE_KM;
		this.TRIP_USAGE_KM = tRIP_USAGE_KM;
		this.TOTAL_EXPENSE = tOTAL_EXPENSE;
		this.TRIP_OVERTIME = tOTAL_OVERTIME;
		this.TOTAL_INCOME = tOTAL_INCOME;
		this.TOTAL_BALANCE = tOTAL_BALANCE;
		this.TRIP_REMARKS = tRIP_REMARKS;
		this.tripDailyexpense = tripDailyexpense;
		this.tripDailyincome = tripDailyincome;
		this.CREATED = cREATED;
		this.LASTUPDATED = lASTUPDATED;
		this.COMPANY_ID = COMPANY_ID;
		this.CHALO_KM = CHALO_KM;
		this.CHALO_AMOUNT = CHALO_AMOUNT;
	}

	public Long getTRIPDAILYID() {
		return TRIPDAILYID;
	}

	public void setTRIPDAILYID(Long tRIPDAILYID) {
		TRIPDAILYID = tRIPDAILYID;
	}

	public Long getTRIPDAILYNUMBER() {
		return TRIPDAILYNUMBER;
	}

	public void setTRIPDAILYNUMBER(Long tRIPDAILYNUMBER) {
		TRIPDAILYNUMBER = tRIPDAILYNUMBER;
	}

	public Integer getVEHICLEID() {
		return VEHICLEID;
	}

	public void setVEHICLEID(Integer vEHICLEID) {
		VEHICLEID = vEHICLEID;
	}

	public int getTRIP_DRIVER_ID() {
		return TRIP_DRIVER_ID;
	}

	public void setTRIP_DRIVER_ID(int tRIP_DRIVER_ID) {
		TRIP_DRIVER_ID = tRIP_DRIVER_ID;
	}

	public int getTRIP_CONDUCTOR_ID() {
		return TRIP_CONDUCTOR_ID;
	}

	public void setTRIP_CONDUCTOR_ID(int tRIP_CONDUCTOR_ID) {
		TRIP_CONDUCTOR_ID = tRIP_CONDUCTOR_ID;
	}

	public int getTRIP_CLEANER_ID() {
		return TRIP_CLEANER_ID;
	}

	public void setTRIP_CLEANER_ID(int tRIP_CLEANER_ID) {
		TRIP_CLEANER_ID = tRIP_CLEANER_ID;
	}

	public Date getTRIP_OPEN_DATE() {
		return TRIP_OPEN_DATE;
	}

	public void setTRIP_OPEN_DATE(Date tRIP_OPEN_DATE) {
		TRIP_OPEN_DATE = tRIP_OPEN_DATE;
	}

	public Integer getTRIP_ROUTE_ID() {
		return TRIP_ROUTE_ID;
	}

	public void setTRIP_ROUTE_ID(Integer tRIP_ROUTE_ID) {
		TRIP_ROUTE_ID = tRIP_ROUTE_ID;
	}

	public Double getTRIP_DIESEL() {
		return TRIP_DIESEL;
	}

	public void setTRIP_DIESEL(Double tRIP_DIESEL) {
		TRIP_DIESEL = tRIP_DIESEL;
	}

	public Double getTRIP_DIESELKMPL() {
		return TRIP_DIESELKMPL;
	}

	public void setTRIP_DIESELKMPL(Double tRIP_DIESELKMPL) {
		TRIP_DIESELKMPL = tRIP_DIESELKMPL;
	}

	public Integer getTRIP_ROUTE_APPKM() {
		return TRIP_ROUTE_APPKM;
	}

	public void setTRIP_ROUTE_APPKM(Integer tRIP_ROUTE_APPKM) {
		TRIP_ROUTE_APPKM = tRIP_ROUTE_APPKM;
	}

	public Integer getTRIP_TOTALPASSNGER() {
		return TRIP_TOTALPASSNGER;
	}

	public void setTRIP_TOTALPASSNGER(Integer tRIP_TOTALPASSNGER) {
		TRIP_TOTALPASSNGER = tRIP_TOTALPASSNGER;
	}

	public Integer getTRIP_PASS_PASSNGER() {
		return TRIP_PASS_PASSNGER;
	}

	public void setTRIP_PASS_PASSNGER(Integer tRIP_PASS_PASSNGER) {
		TRIP_PASS_PASSNGER = tRIP_PASS_PASSNGER;
	}

	public Integer getTRIP_RFIDPASS() {
		return TRIP_RFIDPASS;
	}

	public void setTRIP_RFIDPASS(Integer tRIP_RFIDPASS) {
		TRIP_RFIDPASS = tRIP_RFIDPASS;
	}

	public Double getTRIP_RFID_AMOUNT() {
		return TRIP_RFID_AMOUNT;
	}

	public void setTRIP_RFID_AMOUNT(Double tRIP_RFID_AMOUNT) {
		TRIP_RFID_AMOUNT = tRIP_RFID_AMOUNT;
	}

	public Integer getTRIP_OPEN_KM() {
		return TRIP_OPEN_KM;
	}

	public void setTRIP_OPEN_KM(Integer tRIP_OPEN_KM) {
		TRIP_OPEN_KM = tRIP_OPEN_KM;
	}

	public Integer getTRIP_CLOSE_KM() {
		return TRIP_CLOSE_KM;
	}

	public void setTRIP_CLOSE_KM(Integer tRIP_CLOSE_KM) {
		TRIP_CLOSE_KM = tRIP_CLOSE_KM;
	}

	public Integer getTRIP_USAGE_KM() {
		return TRIP_USAGE_KM;
	}

	public void setTRIP_USAGE_KM(Integer tRIP_USAGE_KM) {
		TRIP_USAGE_KM = tRIP_USAGE_KM;
	}

	public Double getTOTAL_EXPENSE() {
		return TOTAL_EXPENSE;
	}

	public void setTOTAL_EXPENSE(Double tOTAL_EXPENSE) {
		TOTAL_EXPENSE = tOTAL_EXPENSE;
	}

	public Double getTRIP_OVERTIME() {
		return TRIP_OVERTIME;
	}

	public void setTRIP_OVERTIME(Double tRIP_OVERTIME) {
		TRIP_OVERTIME = tRIP_OVERTIME;
	}

	public Integer getTRIP_SUBROUTE_ID() {
		return TRIP_SUBROUTE_ID;
	}

	public void setTRIP_SUBROUTE_ID(Integer tRIP_SUBROUTE_ID) {
		TRIP_SUBROUTE_ID = tRIP_SUBROUTE_ID;
	}

	public Double getTOTAL_INCOME_COLLECTION() {
		return TOTAL_INCOME_COLLECTION;
	}

	public void setTOTAL_INCOME_COLLECTION(Double tOTAL_INCOME_COLLECTION) {
		TOTAL_INCOME_COLLECTION = tOTAL_INCOME_COLLECTION;
	}

	public Double getTOTAL_INCOME() {
		return TOTAL_INCOME;
	}

	public void setTOTAL_INCOME(Double tOTAL_INCOME) {
		TOTAL_INCOME = tOTAL_INCOME;
	}

	public Double getTOTAL_NET_INCOME() {
		return TOTAL_NET_INCOME;
	}

	public void setTOTAL_NET_INCOME(Double tOTAL_NET_INCOME) {
		TOTAL_NET_INCOME = tOTAL_NET_INCOME;
	}

	public Double getTOTAL_WT() {
		return TOTAL_WT;
	}

	public void setTOTAL_WT(Double tOTAL_WT) {
		TOTAL_WT = tOTAL_WT;
	}

	public Double getTOTAL_BALANCE() {
		return TOTAL_BALANCE;
	}

	public void setTOTAL_BALANCE(Double tOTAL_BALANCE) {
		TOTAL_BALANCE = tOTAL_BALANCE;
	}

	public short getTRIP_STATUS_ID() {
		return TRIP_STATUS_ID;
	}

	public void setTRIP_STATUS_ID(short tRIP_STATUS_ID) {
		TRIP_STATUS_ID = tRIP_STATUS_ID;
	}

	public String getTRIP_REMARKS() {
		return TRIP_REMARKS;
	}

	public void setTRIP_REMARKS(String tRIP_REMARKS) {
		TRIP_REMARKS = tRIP_REMARKS;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	public Set<TripDailyExpense> getTripDailyexpense() {
		return tripDailyexpense;
	}

	public void setTripDailyexpense(Set<TripDailyExpense> tripDailyexpense) {
		this.tripDailyexpense = tripDailyexpense;
	}

	public Set<TripDailyIncome> getTripDailyincome() {
		return tripDailyincome;
	}

	public void setTripDailyincome(Set<TripDailyIncome> tripDailyincome) {
		this.tripDailyincome = tripDailyincome;
	}

	public Set<TripDailyTimeIncome> getTripDailytimeincome() {
		return tripDailytimeincome;
	}

	public void setTripDailytimeincome(Set<TripDailyTimeIncome> tripDailytimeincome) {
		this.tripDailytimeincome = tripDailytimeincome;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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

	public float getNoOfRoundTrip() {
		return noOfRoundTrip;
	}

	public void setNoOfRoundTrip(float noOfRoundTrip) {
		this.noOfRoundTrip = noOfRoundTrip;
	}
	
	
	public Integer getCHALO_KM() {
		return CHALO_KM;
	}

	public void setCHALO_KM(Integer cHALO_KM) {
		CHALO_KM = cHALO_KM;
	}

	public Double getCHALO_AMOUNT() {
		return CHALO_AMOUNT;
	}

	public void setCHALO_AMOUNT(Double cHALO_AMOUNT) {
		CHALO_AMOUNT = cHALO_AMOUNT;
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
		result = prime * result + ((TOTAL_BALANCE == null) ? 0 : TOTAL_BALANCE.hashCode());
		result = prime * result + ((TOTAL_EXPENSE == null) ? 0 : TOTAL_EXPENSE.hashCode());
		result = prime * result + ((TOTAL_INCOME == null) ? 0 : TOTAL_INCOME.hashCode());
		result = prime * result + ((TRIP_OVERTIME == null) ? 0 : TRIP_OVERTIME.hashCode());
		result = prime * result + ((TRIPDAILYID == null) ? 0 : TRIPDAILYID.hashCode());
		result = prime * result + ((TRIP_DIESEL == null) ? 0 : TRIP_DIESEL.hashCode());
		result = prime * result + ((TRIP_OPEN_DATE == null) ? 0 : TRIP_OPEN_DATE.hashCode());
		result = prime * result + ((TRIP_ROUTE_ID == null) ? 0 : TRIP_ROUTE_ID.hashCode());
		result = prime * result + ((TRIP_TOTALPASSNGER == null) ? 0 : TRIP_TOTALPASSNGER.hashCode());
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
		TripDailySheet other = (TripDailySheet) obj;
		if (TOTAL_BALANCE == null) {
			if (other.TOTAL_BALANCE != null)
				return false;
		} else if (!TOTAL_BALANCE.equals(other.TOTAL_BALANCE))
			return false;
		if (TOTAL_EXPENSE == null) {
			if (other.TOTAL_EXPENSE != null)
				return false;
		} else if (!TOTAL_EXPENSE.equals(other.TOTAL_EXPENSE))
			return false;
		if (TOTAL_INCOME == null) {
			if (other.TOTAL_INCOME != null)
				return false;
		} else if (!TOTAL_INCOME.equals(other.TOTAL_INCOME))
			return false;
		if (TRIP_OVERTIME == null) {
			if (other.TRIP_OVERTIME != null)
				return false;
		} else if (!TRIP_OVERTIME.equals(other.TRIP_OVERTIME))
			return false;
		if (TRIPDAILYID == null) {
			if (other.TRIPDAILYID != null)
				return false;
		} else if (!TRIPDAILYID.equals(other.TRIPDAILYID))
			return false;
		if (TRIP_DIESEL == null) {
			if (other.TRIP_DIESEL != null)
				return false;
		} else if (!TRIP_DIESEL.equals(other.TRIP_DIESEL))
			return false;
		if (TRIP_OPEN_DATE == null) {
			if (other.TRIP_OPEN_DATE != null)
				return false;
		} else if (!TRIP_OPEN_DATE.equals(other.TRIP_OPEN_DATE))
			return false;
		if (TRIP_ROUTE_ID == null) {
			if (other.TRIP_ROUTE_ID != null)
				return false;
		} else if (!TRIP_ROUTE_ID.equals(other.TRIP_ROUTE_ID))
			return false;
		if (TRIP_TOTALPASSNGER == null) {
			if (other.TRIP_TOTALPASSNGER != null)
				return false;
		} else if (!TRIP_TOTALPASSNGER.equals(other.TRIP_TOTALPASSNGER))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripDailySheet [TRIPDAILYID=");
		builder.append(TRIPDAILYID);
		builder.append(", TRIPDAILYNUMBER=");
		builder.append(TRIPDAILYNUMBER);
		builder.append(", VEHICLEID=");
		builder.append(VEHICLEID);
		builder.append(", TRIP_DRIVER_ID=");
		builder.append(TRIP_DRIVER_ID);
		
		builder.append(", TRIP_CONDUCTOR_ID=");
		builder.append(TRIP_CONDUCTOR_ID);
		
		builder.append(", TRIP_CLEANER_ID=");
		builder.append(TRIP_CLEANER_ID);
		
		builder.append(", TRIP_OPEN_DATE=");
		builder.append(TRIP_OPEN_DATE);
		builder.append(", TRIP_ROUTE_ID=");
		builder.append(TRIP_ROUTE_ID);
		
		builder.append(", TRIP_DIESEL=");
		builder.append(TRIP_DIESEL);
		builder.append(", TRIP_DIESELKMPL=");
		builder.append(TRIP_DIESELKMPL);
		builder.append(", TRIP_ROUTE_APPKM=");
		builder.append(TRIP_ROUTE_APPKM);
		builder.append(", TRIP_TOTALPASSNGER=");
		builder.append(TRIP_TOTALPASSNGER);
		builder.append(", TRIP_PASS_PASSNGER=");
		builder.append(TRIP_PASS_PASSNGER);
		builder.append(", TRIP_RFIDPASS=");
		builder.append(TRIP_RFIDPASS);
		builder.append(", TRIP_RFID_AMOUNT=");
		builder.append(TRIP_RFID_AMOUNT);
		builder.append(", TRIP_OPEN_KM=");
		builder.append(TRIP_OPEN_KM);
		builder.append(", TRIP_CLOSE_KM=");
		builder.append(TRIP_CLOSE_KM);
		builder.append(", TRIP_USAGE_KM=");
		builder.append(TRIP_USAGE_KM);
		builder.append(", TOTAL_EXPENSE=");
		builder.append(TOTAL_EXPENSE);
		builder.append(", TRIP_OVERTIME=");
		builder.append(TRIP_OVERTIME);
		builder.append(", TOTAL_INCOME=");
		builder.append(TOTAL_INCOME);
		builder.append(", TOTAL_NET_INCOME=");
		builder.append(TOTAL_NET_INCOME);
		builder.append(", TOTAL_WT=");
		builder.append(TOTAL_WT);
		builder.append(", TOTAL_BALANCE=");
		builder.append(TOTAL_BALANCE);
		
		builder.append(", TRIP_REMARKS=");
		builder.append(TRIP_REMARKS);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", LASTUPDATED=");
		builder.append(LASTUPDATED);
		builder.append(", TRIP_STATUS_ID=");
		builder.append(TRIP_STATUS_ID);
		builder.append(", CREATED_BY_ID=");
		builder.append(CREATED_BY_ID);
		builder.append(", LASTMODIFIED_BY_ID=");
		builder.append(LASTMODIFIED_BY_ID);		
		builder.append(", CHALO_KM=");
		builder.append(CHALO_KM);		
		builder.append(", CHALO_AMOUNT=");
		builder.append(CHALO_AMOUNT);		
		builder.append("]");
		return builder.toString();
	}

	
}
