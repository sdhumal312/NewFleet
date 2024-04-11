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
@Table(name = "InventoryTyre")
public class InventoryTyre implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value for the ITYRE_ID Layout Id field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TYRE_ID")
	private Long TYRE_ID;
	
	@Column(name = "TYRE_IN_NUMBER")
	private Long TYRE_IN_NUMBER;

	/** The value for the Vehicle Registration Number field */
	@Column(name = "TYRE_NUMBER", nullable = false, unique = false, length = 25)
	private String TYRE_NUMBER;

	/** The value for the TYRE_AMOUNT field */
	@Column(name = "TYRE_AMOUNT")
	private Double TYRE_AMOUNT;

	
	@Column(name = "TYRE_MANUFACTURER_ID")
	private Integer TYRE_MANUFACTURER_ID;

	
	@Column(name = "TYRE_MODEL_ID")
	private Integer TYRE_MODEL_ID;

	/** The value for the TYRE_SIZE_ID field */
	@Column(name = "TYRE_SIZE_ID")
	private Integer TYRE_SIZE_ID;

	
	/** The value for the TYRE_TREAD field */
	@Column(name = "TYRE_TREAD", length = 50)
	private String TYRE_TREAD;
	
	
	@Column(name = "WAREHOUSE_LOCATION_ID")
	private Integer WAREHOUSE_LOCATION_ID;

	/** The value for the ITYRE_AMOUNT_ID field */
	@Column(name = "ITYRE_AMOUNT_ID")
	private Long ITYRE_AMOUNT_ID;

	/** The value for the ITYRE_INVOICE_ID field */
	@Column(name = "ITYRE_INVOICE_ID")
	private Long ITYRE_INVOICE_ID;

	/** The value for the Vehicle ID field */
	@Column(name = "VEHICLE_ID")
	private Integer VEHICLE_ID;

	
	/** The value for the OPEN_ODOMETER ID field */
	@Column(name = "OPEN_ODOMETER")
	private Integer OPEN_ODOMETER;

	/** The value for the CLOSE_ODOMETER ID field */
	@Column(name = "CLOSE_ODOMETER")
	private Integer CLOSE_ODOMETER;

	/** The value for the TYRE_USEAGE ID field */
	@Column(name = "TYRE_USEAGE")
	private Integer TYRE_USEAGE;

	/** The value for the TYRE_ASSIGN_DATE field */
	@Basic(optional = false)
	@Column(name = "TYRE_PURCHASE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date TYRE_PURCHASE_DATE;

	/** The value for the TYRE_ASSIGN_DATE field */
	@Basic(optional = true)
	@Column(name = "TYRE_ASSIGN_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date TYRE_ASSIGN_DATE;

	
	@Column(name = "TYRE_ASSIGN_STATUS_ID")
	private short TYRE_ASSIGN_STATUS_ID;

	/** The value for the TYRE_RETREAD_ID field */
	@Column(name = "TYRE_RETREAD_ID")
	private Long TYRE_RETREAD_ID;

	/** The value for the RETREAD_COUND field Count */
	@Column(name = "TYRE_RETREAD_COUNT")
	private Integer TYRE_RETREAD_COUNT;
	
	@Column(name = "TYRE_SCRAPED_BY_ID")
	private Long TYRE_SCRAPED_BY_ID;

	/** The value for the TYRE_SCRAPED_DATE field */
	@Basic(optional = true)
	@Column(name = "TYRE_SCRAPED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date TYRE_SCRAPED_DATE;
	
	@Column(name = "CREATEDBYID", updatable = false)
	private Long CREATEDBYID;

	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;


	/** The value for the CREATED_DATE DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;

	/** The value for the LASTUPDATED_DATE DATE field */
	@Basic(optional = false)
	@Column(name = "LASTUPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date LASTUPDATED_DATE;
	
	/** The value for which company this record relate */
	@Column(name ="COMPANY_ID" , nullable = false)
	private Integer COMPANY_ID;
	
	/** The value for Marking that this record is required or not */
	@Column(name="markForDelete", nullable = false)
	boolean	markForDelete;
	
	@Column(name = "subLocationId")
	private Integer	subLocationId;
	
	@Column(name = "dismountedTyreStatusId",nullable=false)
	private short dismountedTyreStatusId;

	private Integer VEHICLE_NO;
	
	public InventoryTyre() {
		super();
	}

	public InventoryTyre(Long tYRE_ID, Long tYRE_IN_NUMBER, String tYRE_NUMBER, Double tYRE_AMOUNT, Integer tYRE_SIZE_ID, 
			 String tYRE_TREAD, String wAREHOUSE_LOCATION,
			Long iTYRE_AMOUNT_ID, Long iTYRE_INVOICE_ID, Integer vEHICLE_ID, 
			Integer oPEN_ODOMETER, Integer cLOSE_ODOMETER, Integer tYRE_USEAGE, Date tYRE_ASSIGN_DATE, Integer COMPANY_ID) {
		super();
		TYRE_ID = tYRE_ID;
		TYRE_IN_NUMBER = tYRE_IN_NUMBER;
		TYRE_NUMBER = tYRE_NUMBER;
		TYRE_AMOUNT = tYRE_AMOUNT;
		TYRE_SIZE_ID = tYRE_SIZE_ID;
		TYRE_TREAD = tYRE_TREAD;
		ITYRE_AMOUNT_ID = iTYRE_AMOUNT_ID;
		ITYRE_INVOICE_ID = iTYRE_INVOICE_ID;
		VEHICLE_ID = vEHICLE_ID;
		OPEN_ODOMETER = oPEN_ODOMETER;
		CLOSE_ODOMETER = cLOSE_ODOMETER;
		TYRE_USEAGE = tYRE_USEAGE;
		TYRE_ASSIGN_DATE = tYRE_ASSIGN_DATE;
		this.COMPANY_ID = COMPANY_ID;
	}

	/**
	 * @return the tYRE_ID
	 */
	public Long getTYRE_ID() {
		return TYRE_ID;
	}

	/**
	 * @param tYRE_ID
	 *            the tYRE_ID to set
	 */
	public void setTYRE_ID(Long tYRE_ID) {
		TYRE_ID = tYRE_ID;
	}

	/**
	 * @return the tYRE_NUMBER
	 */
	public String getTYRE_NUMBER() {
		return TYRE_NUMBER;
	}

	/**
	 * @param tYRE_NUMBER
	 *            the tYRE_NUMBER to set
	 */
	public void setTYRE_NUMBER(String tYRE_NUMBER) {
		TYRE_NUMBER = tYRE_NUMBER;
	}

	public Long getTYRE_IN_NUMBER() {
		return TYRE_IN_NUMBER;
	}

	public void setTYRE_IN_NUMBER(Long tYRE_IN_NUMBER) {
		TYRE_IN_NUMBER = tYRE_IN_NUMBER;
	}

	/**
	 * @return the tYRE_AMOUNT
	 */
	public Double getTYRE_AMOUNT() {
		return TYRE_AMOUNT;
	}

	/**
	 * @param tYRE_AMOUNT
	 *            the tYRE_AMOUNT to set
	 */
	public void setTYRE_AMOUNT(Double tYRE_AMOUNT) {
		TYRE_AMOUNT = tYRE_AMOUNT;
	}

	/**
	 * @return the tYRE_SIZE_ID
	 */
	public Integer getTYRE_SIZE_ID() {
		return TYRE_SIZE_ID;
	}

	/**
	 * @param tYRE_SIZE_ID
	 *            the tYRE_SIZE_ID to set
	 */
	public void setTYRE_SIZE_ID(Integer tYRE_SIZE_ID) {
		TYRE_SIZE_ID = tYRE_SIZE_ID;
	}

	
	/**
	 * @return the tYRE_TREAD
	 */
	public String getTYRE_TREAD() {
		return TYRE_TREAD;
	}

	/**
	 * @param tYRE_TREAD
	 *            the tYRE_TREAD to set
	 */
	public void setTYRE_TREAD(String tYRE_TREAD) {
		TYRE_TREAD = tYRE_TREAD;
	}

	
	/**
	 * @return the iTYRE_AMOUNT_ID
	 */
	public Long getITYRE_AMOUNT_ID() {
		return ITYRE_AMOUNT_ID;
	}

	/**
	 * @param iTYRE_AMOUNT_ID
	 *            the iTYRE_AMOUNT_ID to set
	 */
	public void setITYRE_AMOUNT_ID(Long iTYRE_AMOUNT_ID) {
		ITYRE_AMOUNT_ID = iTYRE_AMOUNT_ID;
	}

	/**
	 * @return the iTYRE_INVOICE_ID
	 */
	public Long getITYRE_INVOICE_ID() {
		return ITYRE_INVOICE_ID;
	}

	/**
	 * @param iTYRE_INVOICE_ID
	 *            the iTYRE_INVOICE_ID to set
	 */
	public void setITYRE_INVOICE_ID(Long iTYRE_INVOICE_ID) {
		ITYRE_INVOICE_ID = iTYRE_INVOICE_ID;
	}

	/**
	 * @return the vEHICLE_ID
	 */
	public Integer getVEHICLE_ID() {
		return VEHICLE_ID;
	}

	/**
	 * @param vEHICLE_ID
	 *            the vEHICLE_ID to set
	 */
	public void setVEHICLE_ID(Integer vEHICLE_ID) {
		VEHICLE_ID = vEHICLE_ID;
	}


	/**
	 * @return the oPEN_ODOMETER
	 */
	public Integer getOPEN_ODOMETER() {
		return OPEN_ODOMETER;
	}

	/**
	 * @param oPEN_ODOMETER
	 *            the oPEN_ODOMETER to set
	 */
	public void setOPEN_ODOMETER(Integer oPEN_ODOMETER) {
		OPEN_ODOMETER = oPEN_ODOMETER;
	}

	/**
	 * @return the cLOSE_ODOMETER
	 */
	public Integer getCLOSE_ODOMETER() {
		return CLOSE_ODOMETER;
	}

	/**
	 * @param cLOSE_ODOMETER
	 *            the cLOSE_ODOMETER to set
	 */
	public void setCLOSE_ODOMETER(Integer cLOSE_ODOMETER) {
		CLOSE_ODOMETER = cLOSE_ODOMETER;
	}

	/**
	 * @return the tYRE_USEAGE
	 */
	public Integer getTYRE_USEAGE() {
		return TYRE_USEAGE;
	}

	/**
	 * @param tYRE_USEAGE
	 *            the tYRE_USEAGE to set
	 */
	public void setTYRE_USEAGE(Integer tYRE_USEAGE) {
		TYRE_USEAGE = tYRE_USEAGE;
	}

	/**
	 * @return the tYRE_ASSIGN_DATE
	 */
	public Date getTYRE_ASSIGN_DATE() {
		return TYRE_ASSIGN_DATE;
	}

	/**
	 * @param tYRE_ASSIGN_DATE
	 *            the tYRE_ASSIGN_DATE to set
	 */
	public void setTYRE_ASSIGN_DATE(Date tYRE_ASSIGN_DATE) {
		TYRE_ASSIGN_DATE = tYRE_ASSIGN_DATE;
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
	 * @return the tYRE_PURCHASE_DATE
	 */
	public Date getTYRE_PURCHASE_DATE() {
		return TYRE_PURCHASE_DATE;
	}

	/**
	 * @param tYRE_PURCHASE_DATE
	 *            the tYRE_PURCHASE_DATE to set
	 */
	public void setTYRE_PURCHASE_DATE(Date tYRE_PURCHASE_DATE) {
		TYRE_PURCHASE_DATE = tYRE_PURCHASE_DATE;
	}

	/**
	 * @return the tYRE_RETREAD_ID
	 */
	public Long getTYRE_RETREAD_ID() {
		return TYRE_RETREAD_ID;
	}

	/**
	 * @param tYRE_RETREAD_ID
	 *            the tYRE_RETREAD_ID to set
	 */
	public void setTYRE_RETREAD_ID(Long tYRE_RETREAD_ID) {
		TYRE_RETREAD_ID = tYRE_RETREAD_ID;
	}

	/**
	 * @return the tYRE_RETREAD_COUNT
	 */
	public Integer getTYRE_RETREAD_COUNT() {
		return TYRE_RETREAD_COUNT;
	}

	/**
	 * @param tYRE_RETREAD_COUNT
	 *            the tYRE_RETREAD_COUNT to set
	 */
	public void setTYRE_RETREAD_COUNT(Integer tYRE_RETREAD_COUNT) {
		TYRE_RETREAD_COUNT = tYRE_RETREAD_COUNT;
	}

	
	/**
	 * @return the tYRE_SCRAPED_DATE
	 */
	public Date getTYRE_SCRAPED_DATE() {
		return TYRE_SCRAPED_DATE;
	}

	/**
	 * @param tYRE_SCRAPED_DATE
	 *            the tYRE_SCRAPED_DATE to set
	 */
	public void setTYRE_SCRAPED_DATE(Date tYRE_SCRAPED_DATE) {
		TYRE_SCRAPED_DATE = tYRE_SCRAPED_DATE;
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

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/**
	 * @return the tYRE_MANUFACTURER_ID
	 */
	public Integer getTYRE_MANUFACTURER_ID() {
		return TYRE_MANUFACTURER_ID;
	}

	/**
	 * @param tYRE_MANUFACTURER_ID the tYRE_MANUFACTURER_ID to set
	 */
	public void setTYRE_MANUFACTURER_ID(Integer tYRE_MANUFACTURER_ID) {
		TYRE_MANUFACTURER_ID = tYRE_MANUFACTURER_ID;
	}

	/**
	 * @return the tYRE_MODEL_ID
	 */
	public Integer getTYRE_MODEL_ID() {
		return TYRE_MODEL_ID;
	}

	/**
	 * @param tYRE_MODEL_ID the tYRE_MODEL_ID to set
	 */
	public void setTYRE_MODEL_ID(Integer tYRE_MODEL_ID) {
		TYRE_MODEL_ID = tYRE_MODEL_ID;
	}

	/**
	 * @return the wAREHOUSE_LOCATION_ID
	 */
	public Integer getWAREHOUSE_LOCATION_ID() {
		return WAREHOUSE_LOCATION_ID;
	}

	/**
	 * @param wAREHOUSE_LOCATION_ID the wAREHOUSE_LOCATION_ID to set
	 */
	public void setWAREHOUSE_LOCATION_ID(Integer wAREHOUSE_LOCATION_ID) {
		WAREHOUSE_LOCATION_ID = wAREHOUSE_LOCATION_ID;
	}

	/**
	 * @return the tYRE_ASSIGN_STATUS_ID
	 */
	public short getTYRE_ASSIGN_STATUS_ID() {
		return TYRE_ASSIGN_STATUS_ID;
	}

	/**
	 * @param tYRE_ASSIGN_STATUS_ID the tYRE_ASSIGN_STATUS_ID to set
	 */
	public void setTYRE_ASSIGN_STATUS_ID(short tYRE_ASSIGN_STATUS_ID) {
		TYRE_ASSIGN_STATUS_ID = tYRE_ASSIGN_STATUS_ID;
	}

	/**
	 * @return the tYRE_SCRAPED_BY_ID
	 */
	public Long getTYRE_SCRAPED_BY_ID() {
		return TYRE_SCRAPED_BY_ID;
	}

	/**
	 * @param tYRE_SCRAPED_BY_ID the tYRE_SCRAPED_BY_ID to set
	 */
	public void setTYRE_SCRAPED_BY_ID(Long tYRE_SCRAPED_BY_ID) {
		TYRE_SCRAPED_BY_ID = tYRE_SCRAPED_BY_ID;
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
	
	
	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}
	
	public short getDismountedTyreStatusId() {
		return dismountedTyreStatusId;
	}

	public void setDismountedTyreStatusId(short dismountedTyreStatusId) {
		this.dismountedTyreStatusId = dismountedTyreStatusId;
	}

	
	public Integer getVEHICLE_NO() {
		return VEHICLE_NO;
	}

	public void setVEHICLE_NO(Integer vEHICLE_NO) {
		VEHICLE_NO = vEHICLE_NO;
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
		result = prime * result + ((ITYRE_AMOUNT_ID == null) ? 0 : ITYRE_AMOUNT_ID.hashCode());
		result = prime * result + ((ITYRE_INVOICE_ID == null) ? 0 : ITYRE_INVOICE_ID.hashCode());
		result = prime * result + ((TYRE_ID == null) ? 0 : TYRE_ID.hashCode());
		result = prime * result + ((TYRE_SIZE_ID == null) ? 0 : TYRE_SIZE_ID.hashCode());
		result = prime * result + ((VEHICLE_ID == null) ? 0 : VEHICLE_ID.hashCode());
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
		InventoryTyre other = (InventoryTyre) obj;
		if (ITYRE_AMOUNT_ID == null) {
			if (other.ITYRE_AMOUNT_ID != null)
				return false;
		} else if (!ITYRE_AMOUNT_ID.equals(other.ITYRE_AMOUNT_ID))
			return false;
		if (ITYRE_INVOICE_ID == null) {
			if (other.ITYRE_INVOICE_ID != null)
				return false;
		} else if (!ITYRE_INVOICE_ID.equals(other.ITYRE_INVOICE_ID))
			return false;
		if (TYRE_ID == null) {
			if (other.TYRE_ID != null)
				return false;
		} else if (!TYRE_ID.equals(other.TYRE_ID))
			return false;
		if (TYRE_SIZE_ID == null) {
			if (other.TYRE_SIZE_ID != null)
				return false;
		} else if (!TYRE_SIZE_ID.equals(other.TYRE_SIZE_ID))
			return false;
		if (VEHICLE_ID == null) {
			if (other.VEHICLE_ID != null)
				return false;
		} else if (!VEHICLE_ID.equals(other.VEHICLE_ID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InventoryTyre [TYRE_ID=" + TYRE_ID + ", TYRE_IN_NUMBER=" + TYRE_IN_NUMBER + ", TYRE_NUMBER="
				+ TYRE_NUMBER + ", TYRE_AMOUNT=" + TYRE_AMOUNT + ", TYRE_MANUFACTURER_ID=" + TYRE_MANUFACTURER_ID
				+ ", TYRE_MODEL_ID=" + TYRE_MODEL_ID + ", TYRE_SIZE_ID=" + TYRE_SIZE_ID + ", TYRE_TREAD=" + TYRE_TREAD
				+ ", WAREHOUSE_LOCATION_ID=" + WAREHOUSE_LOCATION_ID + ", ITYRE_AMOUNT_ID=" + ITYRE_AMOUNT_ID
				+ ", ITYRE_INVOICE_ID=" + ITYRE_INVOICE_ID + ", VEHICLE_ID=" + VEHICLE_ID + ", OPEN_ODOMETER="
				+ OPEN_ODOMETER + ", CLOSE_ODOMETER=" + CLOSE_ODOMETER + ", TYRE_USEAGE=" + TYRE_USEAGE
				+ ", TYRE_PURCHASE_DATE=" + TYRE_PURCHASE_DATE + ", TYRE_ASSIGN_DATE=" + TYRE_ASSIGN_DATE
				+ ", TYRE_ASSIGN_STATUS_ID=" + TYRE_ASSIGN_STATUS_ID + ", TYRE_RETREAD_ID=" + TYRE_RETREAD_ID
				+ ", TYRE_RETREAD_COUNT=" + TYRE_RETREAD_COUNT + ", TYRE_SCRAPED_BY_ID=" + TYRE_SCRAPED_BY_ID
				+ ", TYRE_SCRAPED_DATE=" + TYRE_SCRAPED_DATE + ", CREATEDBYID=" + CREATEDBYID + ", LASTMODIFIEDBYID="
				+ LASTMODIFIEDBYID + ", CREATED_DATE=" + CREATED_DATE + ", LASTUPDATED_DATE=" + LASTUPDATED_DATE
				+ ", COMPANY_ID=" + COMPANY_ID + ", markForDelete=" + markForDelete +", subLocationId="+subLocationId+ "]";
	}

	
}
