package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
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

@Entity
@Table(name = "TripCollectionSheet")
public class TripCollectionSheet implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value for the TRIPCOLLID field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRIPCOLLID")
	private Long TRIPCOLLID;
	
	@Column(name = "TRIPCOLLNUMBER", nullable = false)
	private Long TRIPCOLLNUMBER;

	/** The value for the VID field */
	@Column(name = "VID")
	private Integer VID;


	/** The value for the TRIP DRIVER ID field */
	@Column(name = "TRIP_DRIVER_ID")
	private int TRIP_DRIVER_ID;


	/** The value for the TRIP DRIVER JAMA field */
	@Column(name = "TRIP_DRIVER_JAMA", length = 150)
	private Double TRIP_DRIVER_JAMA;

	
	@Column(name = "DRIVER_ADVANCE_STATUS_ID")
	private short DRIVER_ADVANCE_STATUS_ID;

	/** The value for the TRIP CONDUCTOR ID field */
	@Column(name = "TRIP_CONDUCTOR_ID")
	private int TRIP_CONDUCTOR_ID;


	/** The value for the TRIP CONDUCTOR NAME field */
	@Column(name = "TRIP_CONDUCTOR_JAMA")
	private Double TRIP_CONDUCTOR_JAMA;

	
	@Column(name = "CONDUCTOR_ADVANCE_STATUS_ID")
	private short CONDUCTOR_ADVANCE_STATUS_ID;

	/** The value for the TRIP_CLEANER_ID field */
	@Column(name = "TRIP_CLEANER_ID")
	private int TRIP_CLEANER_ID;

	/** The value for the TRIP_OPEN DATE field */
	@Column(name = "TRIP_OPEN_DATE")
	private Date TRIP_OPEN_DATE;

	/** The value for the TRIP_ROUTE_ID DATE field */
	@Column(name = "TRIP_ROUTE_ID")
	private Integer TRIP_ROUTE_ID;

	
	/** The value for the TRIP_SINGL field */
	@Column(name = "TRIP_SINGL")
	private Integer TRIP_SINGL;

	/** The value for the TRIP_DIESEL_LITER field */
	@Column(name = "TRIP_DIESEL_LITER")
	private Integer TRIP_DIESEL_LITER;

	/** The value for the TRIP_ROUTE_APPKM DATE field */
	@Column(name = "TRIP_ROUTE_APPKM")
	private Integer TRIP_ROUTE_APPKM;

	/** The value for the TRIP_OPEN_KM DATE field */
	@Column(name = "TRIP_OPEN_KM", length = 10)
	private Integer TRIP_OPEN_KM;

	/** The value for the TRIP_CLOSE_KM DATE field */
	@Column(name = "TRIP_CLOSE_KM", length = 10)
	private Integer TRIP_CLOSE_KM;

	/** The value for the TRIP_USAGE_KM DATE field */
	@Column(name = "TRIP_USAGE_KM", length = 10)
	private Integer TRIP_USAGE_KM;

	/** The value for the TRIP_TOTAL_EXPENSE DATE field */
	@Column(name = "TOTAL_EXPENSE")
	private Double TOTAL_EXPENSE;

	/** The value for the TRIP_TOTAL_INCOME DATE field */
	@Column(name = "TOTAL_INCOME")
	private Double TOTAL_INCOME;

	/** The value for the TRIP_TOTAL_BALANCE DATE field */
	@Column(name = "TOTAL_BALANCE")
	private Double TOTAL_BALANCE;

	
	@Column(name = "TRIP_CLOSE_STATUS_ID")
	private short TRIP_CLOSE_STATUS_ID;

	/** The value for the REMARKS DATE field */
	@Column(name = "TRIP_REMARKS", length = 250)
	private String TRIP_REMARKS;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;
	

	/** The value for the TRIP_EXPENSE DATE field */
	@OneToMany(mappedBy = "tripCollectionsheet", fetch = FetchType.EAGER)
	private Set<TripCollectionExpense> tripCollectionexpense;

	/** The value for the TRIP_INCOME DATE field */
	@OneToMany(mappedBy = "tripCollectionsheet", fetch = FetchType.EAGER)
	private Set<TripCollectionIncome> tripCollectionincome;

	@Column(name = "CREATEDBYID", nullable = false, updatable = false)
	private Long CREATEDBYID;

	
	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;

	/** The value for the STATUS DATE field */
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "lASTUPDATED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lASTUPDATED;


	public TripCollectionSheet() {
		super();
	}

	public TripCollectionSheet(Long tRIPCOLLID, Long tRIPCOLLNUMBER,  Integer vID, String vEHICLE_REGISTRATION, String vEHICLE_GROUP,
			int tRIP_DRIVER_ID, String tRIP_DRIVER_NAME, Double tRIP_DRIVER_JAMA, String dRIVER_ADVANCE_STATUS,
			int tRIP_CONDUCTOR_ID, String tRIP_CONDUCTOR_NAME, Double tRIP_CONDUCTOR_JAMA,
			String cONDUCTOR_ADVANCE_STATUS, int tRIP_CLEANER_ID, String tRIP_CLEANER_NAME, Date tRIP_OPEN_DATE,
			Integer tRIP_ROUTE_ID, String tRIP_ROUTE_NAME, Integer tRIP_SINGL, Integer tRIP_DIESEL_LITER,
			Integer tRIP_ROUTE_APPKM, Integer tRIP_OPEN_KM, Integer tRIP_CLOSE_KM, Integer tRIP_USAGE_KM,
			Double tOTAL_EXPENSE, Double tOTAL_INCOME, Double tOTAL_BALANCE, String tRIP_CLOSE_STATUS, Integer cOMPANY_ID, long VEHICLE_GROUP_ID ,String cREATEDBY,
			String lASTMODIFIEDBY, boolean MarkForDelete, Date cREATED, Date lASTUPDATED) {
		super();
		this.TRIPCOLLID = tRIPCOLLID;
		this.TRIPCOLLNUMBER = tRIPCOLLNUMBER;
		this.VID = vID;
	//	this.VEHICLE_REGISTRATION = vEHICLE_REGISTRATION;
	//	this.VEHICLE_GROUP = vEHICLE_GROUP;
		this.TRIP_DRIVER_ID = tRIP_DRIVER_ID;
		//this.TRIP_DRIVER_NAME = tRIP_DRIVER_NAME;
		this.TRIP_DRIVER_JAMA = tRIP_DRIVER_JAMA;
	//	this.DRIVER_ADVANCE_STATUS = dRIVER_ADVANCE_STATUS;
		this.TRIP_CONDUCTOR_ID = tRIP_CONDUCTOR_ID;
		//this.TRIP_CONDUCTOR_NAME = tRIP_CONDUCTOR_NAME;
		this.TRIP_CONDUCTOR_JAMA = tRIP_CONDUCTOR_JAMA;
		//this.CONDUCTOR_ADVANCE_STATUS = cONDUCTOR_ADVANCE_STATUS;
		this.TRIP_CLEANER_ID = tRIP_CLEANER_ID;
		//this.TRIP_CLEANER_NAME = tRIP_CLEANER_NAME;
		this.TRIP_OPEN_DATE = tRIP_OPEN_DATE;
		this.TRIP_ROUTE_ID = tRIP_ROUTE_ID;
		//this.TRIP_ROUTE_NAME = tRIP_ROUTE_NAME;
		this.TRIP_SINGL = tRIP_SINGL;
		this.TRIP_DIESEL_LITER = tRIP_DIESEL_LITER;
		this.TRIP_ROUTE_APPKM = tRIP_ROUTE_APPKM;
		this.TRIP_OPEN_KM = tRIP_OPEN_KM;
		this.TRIP_CLOSE_KM = tRIP_CLOSE_KM;
		this.TRIP_USAGE_KM = tRIP_USAGE_KM;
		this.TOTAL_EXPENSE = tOTAL_EXPENSE;
		this.TOTAL_INCOME = tOTAL_INCOME;
		this.TOTAL_BALANCE = tOTAL_BALANCE;
		//this.TRIP_CLOSE_STATUS = tRIP_CLOSE_STATUS;
		this.COMPANY_ID = cOMPANY_ID;
		//this.VEHICLE_GROUP_ID = VEHICLE_GROUP_ID;
		//this.CREATEDBY = cREATEDBY;
		//this.LASTMODIFIEDBY = lASTMODIFIEDBY;
		this.markForDelete = MarkForDelete;
		this.CREATED = cREATED;
		this.lASTUPDATED = lASTUPDATED;
	}

	/**
	 * @return the tRIPCOLLID
	 */
	public Long getTRIPCOLLID() {
		return TRIPCOLLID;
	}

	/**
	 * @param tRIPCOLLID
	 *            the tRIPCOLLID to set
	 */
	public void setTRIPCOLLID(Long tRIPCOLLID) {
		TRIPCOLLID = tRIPCOLLID;
	}

	public Long getTRIPCOLLNUMBER() {
		return TRIPCOLLNUMBER;
	}

	public void setTRIPCOLLNUMBER(Long tRIPCOLLNUMBER) {
		TRIPCOLLNUMBER = tRIPCOLLNUMBER;
	}

	/**
	 * @return the vID
	 */
	public Integer getVID() {
		return VID;
	}

	/**
	 * @param vID
	 *            the vID to set
	 */
	public void setVID(Integer vID) {
		VID = vID;
	}

	/**
	 * @return the vEHICLE_REGISTRATION
	 *//*
	public String getVEHICLE_REGISTRATION() {
		return VEHICLE_REGISTRATION;
	}

	*//**
	 * @param vEHICLE_REGISTRATION
	 *            the vEHICLE_REGISTRATION to set
	 *//*
	public void setVEHICLE_REGISTRATION(String vEHICLE_REGISTRATION) {
		VEHICLE_REGISTRATION = vEHICLE_REGISTRATION;
	}

	*//**
	 * @return the vEHICLE_GROUP
	 *//*
	public String getVEHICLE_GROUP() {
		return VEHICLE_GROUP;
	}

	*//**
	 * @param vEHICLE_GROUP
	 *            the vEHICLE_GROUP to set
	 *//*
	public void setVEHICLE_GROUP(String vEHICLE_GROUP) {
		VEHICLE_GROUP = vEHICLE_GROUP;
	}*/

	/**
	 * @return the tRIP_DRIVER_ID
	 */
	public int getTRIP_DRIVER_ID() {
		return TRIP_DRIVER_ID;
	}

	/**
	 * @param tRIP_DRIVER_ID
	 *            the tRIP_DRIVER_ID to set
	 */
	public void setTRIP_DRIVER_ID(int tRIP_DRIVER_ID) {
		TRIP_DRIVER_ID = tRIP_DRIVER_ID;
	}

	/**
	 * @return the tRIP_DRIVER_NAME
	 *//*
	public String getTRIP_DRIVER_NAME() {
		return TRIP_DRIVER_NAME;
	}

	*//**
	 * @param tRIP_DRIVER_NAME
	 *            the tRIP_DRIVER_NAME to set
	 *//*
	public void setTRIP_DRIVER_NAME(String tRIP_DRIVER_NAME) {
		TRIP_DRIVER_NAME = tRIP_DRIVER_NAME;
	}
*/
	/**
	 * @return the tRIP_DRIVER_JAMA
	 */
	public Double getTRIP_DRIVER_JAMA() {
		return TRIP_DRIVER_JAMA;
	}

	/**
	 * @param tRIP_DRIVER_JAMA
	 *            the tRIP_DRIVER_JAMA to set
	 */
	public void setTRIP_DRIVER_JAMA(Double tRIP_DRIVER_JAMA) {
		TRIP_DRIVER_JAMA = tRIP_DRIVER_JAMA;
	}

	/**
	 * @return the tRIP_CONDUCTOR_ID
	 */
	public int getTRIP_CONDUCTOR_ID() {
		return TRIP_CONDUCTOR_ID;
	}

	/**
	 * @param tRIP_CONDUCTOR_ID
	 *            the tRIP_CONDUCTOR_ID to set
	 */
	public void setTRIP_CONDUCTOR_ID(int tRIP_CONDUCTOR_ID) {
		TRIP_CONDUCTOR_ID = tRIP_CONDUCTOR_ID;
	}

	/**
	 * @return the tRIP_CONDUCTOR_NAME
	 *//*
	public String getTRIP_CONDUCTOR_NAME() {
		return TRIP_CONDUCTOR_NAME;
	}

	*//**
	 * @param tRIP_CONDUCTOR_NAME
	 *            the tRIP_CONDUCTOR_NAME to set
	 *//*
	public void setTRIP_CONDUCTOR_NAME(String tRIP_CONDUCTOR_NAME) {
		TRIP_CONDUCTOR_NAME = tRIP_CONDUCTOR_NAME;
	}*/

	/**
	 * @return the tRIP_CONDUCTOR_JAMA
	 */
	public Double getTRIP_CONDUCTOR_JAMA() {
		return TRIP_CONDUCTOR_JAMA;
	}

	/**
	 * @param tRIP_CONDUCTOR_JAMA
	 *            the tRIP_CONDUCTOR_JAMA to set
	 */
	public void setTRIP_CONDUCTOR_JAMA(Double tRIP_CONDUCTOR_JAMA) {
		TRIP_CONDUCTOR_JAMA = tRIP_CONDUCTOR_JAMA;
	}

/*	*//**
	 * @return the dRIVER_ADVANCE_STATUS
	 *//*
	public String getDRIVER_ADVANCE_STATUS() {
		return DRIVER_ADVANCE_STATUS;
	}

	*//**
	 * @param dRIVER_ADVANCE_STATUS
	 *            the dRIVER_ADVANCE_STATUS to set
	 *//*
	public void setDRIVER_ADVANCE_STATUS(String dRIVER_ADVANCE_STATUS) {
		DRIVER_ADVANCE_STATUS = dRIVER_ADVANCE_STATUS;
	}

	*//**
	 * @return the cONDUCTOR_ADVANCE_STATUS
	 *//*
	public String getCONDUCTOR_ADVANCE_STATUS() {
		return CONDUCTOR_ADVANCE_STATUS;
	}

	*//**
	 * @param cONDUCTOR_ADVANCE_STATUS
	 *            the cONDUCTOR_ADVANCE_STATUS to set
	 *//*
	public void setCONDUCTOR_ADVANCE_STATUS(String cONDUCTOR_ADVANCE_STATUS) {
		CONDUCTOR_ADVANCE_STATUS = cONDUCTOR_ADVANCE_STATUS;
	}*/

	/**
	 * @return the tRIP_CLEANER_ID
	 */
	public int getTRIP_CLEANER_ID() {
		return TRIP_CLEANER_ID;
	}

	/**
	 * @param tRIP_CLEANER_ID
	 *            the tRIP_CLEANER_ID to set
	 */
	public void setTRIP_CLEANER_ID(int tRIP_CLEANER_ID) {
		TRIP_CLEANER_ID = tRIP_CLEANER_ID;
	}

	/**
	 * @return the tRIP_CLEANER_NAME
	 *//*
	public String getTRIP_CLEANER_NAME() {
		return TRIP_CLEANER_NAME;
	}

	*//**
	 * @param tRIP_CLEANER_NAME
	 *            the tRIP_CLEANER_NAME to set
	 *//*
	public void setTRIP_CLEANER_NAME(String tRIP_CLEANER_NAME) {
		TRIP_CLEANER_NAME = tRIP_CLEANER_NAME;
	}*/

	/**
	 * @return the tRIP_OPEN_DATE
	 */
	public Date getTRIP_OPEN_DATE() {
		return TRIP_OPEN_DATE;
	}

	/**
	 * @param tRIP_OPEN_DATE
	 *            the tRIP_OPEN_DATE to set
	 */
	public void setTRIP_OPEN_DATE(Date tRIP_OPEN_DATE) {
		TRIP_OPEN_DATE = tRIP_OPEN_DATE;
	}

	/**
	 * @return the tRIP_ROUTE_ID
	 */
	public Integer getTRIP_ROUTE_ID() {
		return TRIP_ROUTE_ID;
	}

	/**
	 * @param tRIP_ROUTE_ID
	 *            the tRIP_ROUTE_ID to set
	 */
	public void setTRIP_ROUTE_ID(Integer tRIP_ROUTE_ID) {
		TRIP_ROUTE_ID = tRIP_ROUTE_ID;
	}

	/**
	 * @return the tRIP_ROUTE_NAME
	 *//*
	public String getTRIP_ROUTE_NAME() {
		return TRIP_ROUTE_NAME;
	}

	*//**
	 * @param tRIP_ROUTE_NAME
	 *            the tRIP_ROUTE_NAME to set
	 *//*
	public void setTRIP_ROUTE_NAME(String tRIP_ROUTE_NAME) {
		TRIP_ROUTE_NAME = tRIP_ROUTE_NAME;
	}
*/
	/**
	 * @return the tRIP_SINGL
	 */
	public Integer getTRIP_SINGL() {
		return TRIP_SINGL;
	}

	/**
	 * @param tRIP_SINGL
	 *            the tRIP_SINGL to set
	 */
	public void setTRIP_SINGL(Integer tRIP_SINGL) {
		TRIP_SINGL = tRIP_SINGL;
	}

	/**
	 * @return the tRIP_DIESEL_LITER
	 */
	public Integer getTRIP_DIESEL_LITER() {
		return TRIP_DIESEL_LITER;
	}

	/**
	 * @param tRIP_DIESEL_LITER
	 *            the tRIP_DIESEL_LITER to set
	 */
	public void setTRIP_DIESEL_LITER(Integer tRIP_DIESEL_LITER) {
		TRIP_DIESEL_LITER = tRIP_DIESEL_LITER;
	}

	/**
	 * @return the tRIP_ROUTE_APPKM
	 */
	public Integer getTRIP_ROUTE_APPKM() {
		return TRIP_ROUTE_APPKM;
	}

	/**
	 * @param tRIP_ROUTE_APPKM
	 *            the tRIP_ROUTE_APPKM to set
	 */
	public void setTRIP_ROUTE_APPKM(Integer tRIP_ROUTE_APPKM) {
		TRIP_ROUTE_APPKM = tRIP_ROUTE_APPKM;
	}

	/**
	 * @return the tRIP_OPEN_KM
	 */
	public Integer getTRIP_OPEN_KM() {
		return TRIP_OPEN_KM;
	}

	/**
	 * @param tRIP_OPEN_KM
	 *            the tRIP_OPEN_KM to set
	 */
	public void setTRIP_OPEN_KM(Integer tRIP_OPEN_KM) {
		TRIP_OPEN_KM = tRIP_OPEN_KM;
	}

	/**
	 * @return the tRIP_CLOSE_KM
	 */
	public Integer getTRIP_CLOSE_KM() {
		return TRIP_CLOSE_KM;
	}

	/**
	 * @param tRIP_CLOSE_KM
	 *            the tRIP_CLOSE_KM to set
	 */
	public void setTRIP_CLOSE_KM(Integer tRIP_CLOSE_KM) {
		TRIP_CLOSE_KM = tRIP_CLOSE_KM;
	}

	/**
	 * @return the tRIP_USAGE_KM
	 */
	public Integer getTRIP_USAGE_KM() {
		return TRIP_USAGE_KM;
	}

	/**
	 * @param tRIP_USAGE_KM
	 *            the tRIP_USAGE_KM to set
	 */
	public void setTRIP_USAGE_KM(Integer tRIP_USAGE_KM) {
		TRIP_USAGE_KM = tRIP_USAGE_KM;
	}

	/**
	 * @return the tOTAL_EXPENSE
	 */
	public Double getTOTAL_EXPENSE() {
		return TOTAL_EXPENSE;
	}

	/**
	 * @param tOTAL_EXPENSE
	 *            the tOTAL_EXPENSE to set
	 */
	public void setTOTAL_EXPENSE(Double tOTAL_EXPENSE) {
		TOTAL_EXPENSE = tOTAL_EXPENSE;
	}

	/**
	 * @return the tOTAL_INCOME
	 */
	public Double getTOTAL_INCOME() {
		return TOTAL_INCOME;
	}

	/**
	 * @param tOTAL_INCOME
	 *            the tOTAL_INCOME to set
	 */
	public void setTOTAL_INCOME(Double tOTAL_INCOME) {
		TOTAL_INCOME = tOTAL_INCOME;
	}

	/**
	 * @return the tOTAL_BALANCE
	 */
	public Double getTOTAL_BALANCE() {
		return TOTAL_BALANCE;
	}

	/**
	 * @param tOTAL_BALANCE
	 *            the tOTAL_BALANCE to set
	 */
	public void setTOTAL_BALANCE(Double tOTAL_BALANCE) {
		TOTAL_BALANCE = tOTAL_BALANCE;
	}

	/**
	 * @return the tRIP_CLOSE_STATUS
	 *//*
	public String getTRIP_CLOSE_STATUS() {
		return TRIP_CLOSE_STATUS;
	}

	*//**
	 * @param tRIP_CLOSE_STATUS
	 *            the tRIP_CLOSE_STATUS to set
	 *//*
	public void setTRIP_CLOSE_STATUS(String tRIP_CLOSE_STATUS) {
		TRIP_CLOSE_STATUS = tRIP_CLOSE_STATUS;
	}*/

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

/*	public long getVEHICLE_GROUP_ID() {
		return VEHICLE_GROUP_ID;
	}

	public void setVEHICLE_GROUP_ID(long vEHICLE_GROUP_ID) {
		VEHICLE_GROUP_ID = vEHICLE_GROUP_ID;
	}
*/
	/**
	 * @return the tripCollectionexpense
	 */
	public Set<TripCollectionExpense> getTripCollectionexpense() {
		return tripCollectionexpense;
	}

	/**
	 * @param tripCollectionexpense
	 *            the tripCollectionexpense to set
	 */
	public void setTripCollectionexpense(Set<TripCollectionExpense> tripCollectionexpense) {
		this.tripCollectionexpense = tripCollectionexpense;
	}

	/**
	 * @return the tripCollectionincome
	 */
	public Set<TripCollectionIncome> getTripCollectionincome() {
		return tripCollectionincome;
	}

	/**
	 * @param tripCollectionincome
	 *            the tripCollectionincome to set
	 */
	public void setTripCollectionincome(Set<TripCollectionIncome> tripCollectionincome) {
		this.tripCollectionincome = tripCollectionincome;
	}

	/**
	 * @return the cREATEDBY
	 *//*
	public String getCREATEDBY() {
		return CREATEDBY;
	}

	*//**
	 * @param cREATEDBY
	 *            the cREATEDBY to set
	 *//*
	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	*//**
	 * @return the lASTMODIFIEDBY
	 *//*
	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	*//**
	 * @param lASTMODIFIEDBY
	 *            the lASTMODIFIEDBY to set
	 *//*
	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}*/

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

	/**
	 * @return the lASTUPDATED
	 */
	public Date getlASTUPDATED() {
		return lASTUPDATED;
	}

	/**
	 * @param lASTUPDATED
	 *            the lASTUPDATED to set
	 */
	public void setlASTUPDATED(Date lASTUPDATED) {
		this.lASTUPDATED = lASTUPDATED;
	}

	/**
	 * @return the dRIVER_ADVANCE_STATUS_ID
	 */
	public short getDRIVER_ADVANCE_STATUS_ID() {
		return DRIVER_ADVANCE_STATUS_ID;
	}

	/**
	 * @param dRIVER_ADVANCE_STATUS_ID the dRIVER_ADVANCE_STATUS_ID to set
	 */
	public void setDRIVER_ADVANCE_STATUS_ID(short dRIVER_ADVANCE_STATUS_ID) {
		DRIVER_ADVANCE_STATUS_ID = dRIVER_ADVANCE_STATUS_ID;
	}

	/**
	 * @return the cONDUCTOR_ADVANCE_STATUS_ID
	 */
	public short getCONDUCTOR_ADVANCE_STATUS_ID() {
		return CONDUCTOR_ADVANCE_STATUS_ID;
	}

	/**
	 * @param cONDUCTOR_ADVANCE_STATUS_ID the cONDUCTOR_ADVANCE_STATUS_ID to set
	 */
	public void setCONDUCTOR_ADVANCE_STATUS_ID(short cONDUCTOR_ADVANCE_STATUS_ID) {
		CONDUCTOR_ADVANCE_STATUS_ID = cONDUCTOR_ADVANCE_STATUS_ID;
	}

	/**
	 * @return the tRIP_CLOSE_STATUS_ID
	 */
	public short getTRIP_CLOSE_STATUS_ID() {
		return TRIP_CLOSE_STATUS_ID;
	}

	/**
	 * @param tRIP_CLOSE_STATUS_ID the tRIP_CLOSE_STATUS_ID to set
	 */
	public void setTRIP_CLOSE_STATUS_ID(short tRIP_CLOSE_STATUS_ID) {
		TRIP_CLOSE_STATUS_ID = tRIP_CLOSE_STATUS_ID;
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
	 * @return the tRIP_REMARKS
	 */
	public String getTRIP_REMARKS() {
		return TRIP_REMARKS;
	}

	/**
	 * @param tRIP_REMARKS
	 *            the tRIP_REMARKS to set
	 */
	public void setTRIP_REMARKS(String tRIP_REMARKS) {
		TRIP_REMARKS = tRIP_REMARKS;
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
		result = prime * result + ((TRIPCOLLID == null) ? 0 : TRIPCOLLID.hashCode());
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
		TripCollectionSheet other = (TripCollectionSheet) obj;
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
		if (TRIPCOLLID == null) {
			if (other.TRIPCOLLID != null)
				return false;
		} else if (!TRIPCOLLID.equals(other.TRIPCOLLID))
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
		builder.append("TripCollectionSheet [TRIPCOLLID=");
		builder.append(TRIPCOLLID);
		builder.append(", TRIPCOLLNUMBER=");
		builder.append(TRIPCOLLNUMBER);
		builder.append(", VID=");
		builder.append(VID);
		/*builder.append(", VEHICLE_REGISTRATION=");
		builder.append(VEHICLE_REGISTRATION);
		builder.append(", VEHICLE_GROUP=");
		builder.append(VEHICLE_GROUP);*/
		builder.append(", TRIP_DRIVER_ID=");
		builder.append(TRIP_DRIVER_ID);
		/*builder.append(", TRIP_DRIVER_NAME=");
		builder.append(TRIP_DRIVER_NAME);*/
		builder.append(", TRIP_DRIVER_JAMA=");
		builder.append(TRIP_DRIVER_JAMA);
		/*builder.append(", DRIVER_ADVANCE_STATUS=");
		builder.append(DRIVER_ADVANCE_STATUS);*/
		builder.append(", TRIP_CONDUCTOR_ID=");
		builder.append(TRIP_CONDUCTOR_ID);
		/*builder.append(", TRIP_CONDUCTOR_NAME=");
		builder.append(TRIP_CONDUCTOR_NAME);*/
		builder.append(", TRIP_CONDUCTOR_JAMA=");
		builder.append(TRIP_CONDUCTOR_JAMA);
		/*builder.append(", CONDUCTOR_ADVANCE_STATUS=");
		builder.append(CONDUCTOR_ADVANCE_STATUS);*/
		builder.append(", TRIP_CLEANER_ID=");
		builder.append(TRIP_CLEANER_ID);
		/*builder.append(", TRIP_CLEANER_NAME=");
		builder.append(TRIP_CLEANER_NAME);*/
		builder.append(", TRIP_OPEN_DATE=");
		builder.append(TRIP_OPEN_DATE);
		builder.append(", TRIP_ROUTE_ID=");
		builder.append(TRIP_ROUTE_ID);
		/*builder.append(", TRIP_ROUTE_NAME=");
		builder.append(TRIP_ROUTE_NAME);*/
		builder.append(", TRIP_SINGL=");
		builder.append(TRIP_SINGL);
		builder.append(", TRIP_DIESEL_LITER=");
		builder.append(TRIP_DIESEL_LITER);
		builder.append(", TRIP_ROUTE_APPKM=");
		builder.append(TRIP_ROUTE_APPKM);
		builder.append(", TRIP_OPEN_KM=");
		builder.append(TRIP_OPEN_KM);
		builder.append(", TRIP_CLOSE_KM=");
		builder.append(TRIP_CLOSE_KM);
		builder.append(", TRIP_USAGE_KM=");
		builder.append(TRIP_USAGE_KM);
		builder.append(", TOTAL_EXPENSE=");
		builder.append(TOTAL_EXPENSE);
		builder.append(", TOTAL_INCOME=");
		builder.append(TOTAL_INCOME);
		builder.append(", TOTAL_BALANCE=");
		builder.append(TOTAL_BALANCE);
		/*builder.append(", TRIP_CLOSE_STATUS=");
		builder.append(TRIP_CLOSE_STATUS);*/
		builder.append(", TRIP_REMARKS=");
		builder.append(TRIP_REMARKS);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		/*builder.append(", VEHICLE_GROUP_ID=");
		builder.append(VEHICLE_GROUP_ID);*/
		/*builder.append(", tripCollectionexpense=");
		builder.append(tripCollectionexpense != null ? toString(tripCollectionexpense, maxLen) : null);
		builder.append(", tripCollectionincome=");
		builder.append(tripCollectionincome != null ? toString(tripCollectionincome, maxLen) : null);*/
		/*builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);*/
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", lASTUPDATED=");
		builder.append(lASTUPDATED);
		builder.append("]");
		return builder.toString();
	}

	@SuppressWarnings("unused")
	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0) {
				builder.append(", ");
			}
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

}
