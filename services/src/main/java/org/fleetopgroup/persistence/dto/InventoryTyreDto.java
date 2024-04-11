/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.util.Date;

import javax.persistence.Column;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class InventoryTyreDto {
	
	public static final short TYRE_ASSIGN_STATUS_AVAILABLE			= 1;
	public static final short TYRE_ASSIGN_STATUS_IN_SERVICE			= 2;
	public static final short TYRE_ASSIGN_STATUS_SCRAPED			= 3;
	public static final short TYRE_ASSIGN_STATUS_SENT_RETREAD		= 4;
	public static final short TYRE_ASSIGN_STATUS_IN_PROCESS			= 5;
	public static final short TYRE_ASSIGN_STATUS_SOLD				= 6;
	public static final short TYRE_ASSIGN_STATUS_UNAVAILABLE		= 7;
	public static final short TYRE_ASSIGN_STATUS_INTRANSIT			= 8;
	
	public static final String TYRE_ASSIGN_STATUS_AVAILABLE_NAME		= "AVAILABLE";
	public static final String TYRE_ASSIGN_STATUS_IN_SERVICE_NAME 		= "IN SERVICE";
	public static final String TYRE_ASSIGN_STATUS_SCRAPED_NAME 			= "SCRAPED";
	public static final String TYRE_ASSIGN_STATUS_SENT_RETREAD_NAME 	= "SENT-RETREAD";
	public static final String TYRE_ASSIGN_STATUS_IN_PROCESS_NAME 		= "IN PROCESS";
	public static final String TYRE_ASSIGN_STATUS_SOLD_NAME 			= "SOLD";
	public static final String TYRE_ASSIGN_STATUS_UNAVAILABLE_NAME 		= "UNAVAILABLE";
	public static final String TYRE_ASSIGN_STATUS_INTRANSIT_NAME		= "IN TRANSFER";
	

	/** The value for the ITYRE_ID Layout Id field */
	private Long TYRE_ID;
	
	private Long TYRE_IN_NUMBER;

	/** The value for the Vehicle Registration Number field */
	private String TYRE_NUMBER;

	/** The value for the TYRE_AMOUNT field */
	private Double TYRE_AMOUNT;

	/** The value for the TYRE_MANUFACTURER field */
	private String TYRE_MANUFACTURER;

	/** The value for the TYRE_MODEL field */
	private String TYRE_MODEL;

	/** The value for the TYRE_SIZE_ID field */
	private Integer TYRE_SIZE_ID;

	/** The value for the TYRE_SIZE field */
	private String TYRE_SIZE;

	/** The value for the TYRE_TREAD field */
	private String TYRE_TREAD;

	/** The value for the WAREHOUSE_LOCATION field */
	private String WAREHOUSE_LOCATION;
	
	private Integer WAREHOUSE_LOCATION_ID;
	
	private Integer TYRE_MANUFACTURER_ID;
	
	private Integer TYRE_MODEL_ID;

	/** The value for the ITYRE_AMOUNT_ID field */
	private Long ITYRE_AMOUNT_ID;

	/** The value for the ITYRE_INVOICE_ID field */
	private Long ITYRE_INVOICE_ID;

	/** The value for the Vehicle ID field */
	private Integer VEHICLE_ID;

	/** The value for the Vehicle Registration Number field */
	private String VEHICLE_REGISTRATION;
	
	private String vehicleNumberStr;
	
	private Integer VEHICLE_ODOMETER;
	
	private  Double COST_PER_KM;
	
	private String POSITION;
	
	private Integer AXLE;
	
	private String previousTyreManufacturer;
	
	private String previousSerialNumber;
	
	private int previousTyreOpenOdo;
	
	private String previousAssignedDate;
	
	private int previousTyreUsage;
	
	private  Double discount;
	
	private  Double gst;
	
	private Double TyreNetAmount;
	
	private Integer subLocationId;
	
	private String subLocation;
	
	private Integer tyreGuage;
	
	private short dismountedTyreStatusId;
	
	private String dismountedTyreStatus;
	
	private Long statusWiseTyreCount;
	
	private String tyreAssignLayoutPosition;

	private String vehicleGroup;
	
	private String positionStr;
	
	private String positionT;
	private String remark;
//	private Long allTyreCount;
	

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = Utility.round(discount, 2);
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = Utility.round(gst,2);
	}

	public Double getTyreNetAmount() {
		return TyreNetAmount;
	}

	public void setTyreNetAmount(Double tyreNetAmount) {
		TyreNetAmount = Utility.round(tyreNetAmount,2) ;
	}

	public Double getCOST_PER_KM() {
		return COST_PER_KM;
	}

	public void setCOST_PER_KM(Double cOST_PER_KM) {
		COST_PER_KM = Utility.round(cOST_PER_KM,2);
	}

	public Integer getVEHICLE_ODOMETER() {
		return VEHICLE_ODOMETER;
	}

	public void setVEHICLE_ODOMETER(Integer vEHICLE_ODOMETER) {
		VEHICLE_ODOMETER = vEHICLE_ODOMETER;
	}

	/** The value for the OPEN_ODOMETER ID field */
	@Column(name = "OPEN_ODOMETER")
	private Integer OPEN_ODOMETER;

	/** The value for the CLOSE_ODOMETER ID field */
	private Integer CLOSE_ODOMETER;

	/** The value for the TYRE_USEAGE ID field */
	private Integer TYRE_USEAGE;

	/** The value for the RETREAD_COUND field Count */
	private Integer TYRE_RETREAD_COUNT;

	/** The value for the TYRE_ASSIGN_DATE field */
	private String TYRE_PURCHASE_DATE;

	/** The value for the TYRE_ASSIGN_DATE field */
	private String TYRE_ASSIGN_DATE;

	/** The value for the TYRE_ASSIGN_Status field */
	private String TYRE_ASSIGN_STATUS;

	private short TYRE_ASSIGN_STATUS_ID;
	
	/** The value for the TYRE_SCRAPED_BY field */
	private String TYRE_SCRAPED_BY;

	/** The value for the TYRE_SCRAPED_NOTE field */
	private String TYRE_SCRAPED_NOTE;

	/** The value for the TYRE_SCRAPED_DATE field */
	private String TYRE_SCRAPED_DATE;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;
	
	private Long TYRE_RETREAD_ID;

	boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	private String CREATED_DATE;
	private String LASTUPDATED_DATE;
	
	private Date TYRE_PURCHASE_DATE_ON;
	private Date TYRE_ASSIGN_DATE_ON;
	private Date TYRE_SCRAPED_DATE_ON;
	private Date CREATED_DATE_ON;
	private Date LASTUPDATED_DATE_ON;
	

	public InventoryTyreDto() {
		super();
	}

	public InventoryTyreDto(Long tYRE_ID, String tYRE_NUMBER, Double tYRE_AMOUNT, String tYRE_MANUFACTURER,
			String tYRE_MODEL, Integer tYRE_SIZE_ID, String tYRE_SIZE, String tYRE_TREAD, String wAREHOUSE_LOCATION,
			Long iTYRE_AMOUNT_ID, Long iTYRE_INVOICE_ID, Integer vEHICLE_ID, String vEHICLE_REGISTRATION,
			Integer oPEN_ODOMETER, Integer cLOSE_ODOMETER, Integer tYRE_USEAGE, String tYRE_ASSIGN_DATE) {
		super();
		TYRE_ID = tYRE_ID;
		TYRE_NUMBER = tYRE_NUMBER;
		TYRE_AMOUNT = tYRE_AMOUNT;
		TYRE_MANUFACTURER = tYRE_MANUFACTURER;
		TYRE_MODEL = tYRE_MODEL;
		TYRE_SIZE_ID = tYRE_SIZE_ID;
		TYRE_SIZE = tYRE_SIZE;
		TYRE_TREAD = tYRE_TREAD;
		WAREHOUSE_LOCATION = wAREHOUSE_LOCATION;
		ITYRE_AMOUNT_ID = iTYRE_AMOUNT_ID;
		ITYRE_INVOICE_ID = iTYRE_INVOICE_ID;
		VEHICLE_ID = vEHICLE_ID;
		VEHICLE_REGISTRATION = vEHICLE_REGISTRATION;
		OPEN_ODOMETER = oPEN_ODOMETER;
		CLOSE_ODOMETER = cLOSE_ODOMETER;
		TYRE_USEAGE = tYRE_USEAGE;
		TYRE_ASSIGN_DATE = tYRE_ASSIGN_DATE;
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

	public Long getTYRE_IN_NUMBER() {
		return TYRE_IN_NUMBER;
	}

	public void setTYRE_IN_NUMBER(Long tYRE_IN_NUMBER) {
		TYRE_IN_NUMBER = tYRE_IN_NUMBER;
	}

	/**
	 * @param tYRE_NUMBER
	 *            the tYRE_NUMBER to set
	 */
	public void setTYRE_NUMBER(String tYRE_NUMBER) {
		TYRE_NUMBER = tYRE_NUMBER;
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
		TYRE_AMOUNT = Utility.round(tYRE_AMOUNT, 2);
	}

	/**
	 * @return the tYRE_MANUFACTURER
	 */
	public String getTYRE_MANUFACTURER() {
		return TYRE_MANUFACTURER;
	}

	/**
	 * @param tYRE_MANUFACTURER
	 *            the tYRE_MANUFACTURER to set
	 */
	public void setTYRE_MANUFACTURER(String tYRE_MANUFACTURER) {
		TYRE_MANUFACTURER = tYRE_MANUFACTURER;
	}

	/**
	 * @return the tYRE_MODEL
	 */
	public String getTYRE_MODEL() {
		return TYRE_MODEL;
	}

	/**
	 * @param tYRE_MODEL
	 *            the tYRE_MODEL to set
	 */
	public void setTYRE_MODEL(String tYRE_MODEL) {
		TYRE_MODEL = tYRE_MODEL;
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
	 * @return the tYRE_SIZE
	 */
	public String getTYRE_SIZE() {
		return TYRE_SIZE;
	}

	/**
	 * @param tYRE_SIZE
	 *            the tYRE_SIZE to set
	 */
	public void setTYRE_SIZE(String tYRE_SIZE) {
		TYRE_SIZE = tYRE_SIZE;
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
	 * @return the wAREHOUSE_LOCATION
	 */
	public String getWAREHOUSE_LOCATION() {
		return WAREHOUSE_LOCATION;
	}

	/**
	 * @param wAREHOUSE_LOCATION
	 *            the wAREHOUSE_LOCATION to set
	 */
	public void setWAREHOUSE_LOCATION(String wAREHOUSE_LOCATION) {
		WAREHOUSE_LOCATION = wAREHOUSE_LOCATION;
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
	 * @return the vEHICLE_REGISTRATION
	 */
	public String getVEHICLE_REGISTRATION() {
		return VEHICLE_REGISTRATION;
	}

	/**
	 * @param vEHICLE_REGISTRATION
	 *            the vEHICLE_REGISTRATION to set
	 */
	public void setVEHICLE_REGISTRATION(String vEHICLE_REGISTRATION) {
		VEHICLE_REGISTRATION = vEHICLE_REGISTRATION;
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
	public String getTYRE_ASSIGN_DATE() {
		return TYRE_ASSIGN_DATE;
	}

	/**
	 * @param tYRE_ASSIGN_DATE
	 *            the tYRE_ASSIGN_DATE to set
	 */
	public void setTYRE_ASSIGN_DATE(String tYRE_ASSIGN_DATE) {
		TYRE_ASSIGN_DATE = tYRE_ASSIGN_DATE;
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
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	/**
	 * @param cREATED_DATE
	 *            the cREATED_DATE to set
	 */
	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the tYRE_ASSIGN_STATUS
	 */
	public String getTYRE_ASSIGN_STATUS() {
		return TYRE_ASSIGN_STATUS;
	}

	/**
	 * @param tYRE_ASSIGN_STATUS
	 *            the tYRE_ASSIGN_STATUS to set
	 */
	public void setTYRE_ASSIGN_STATUS(String tYRE_ASSIGN_STATUS) {
		TYRE_ASSIGN_STATUS = tYRE_ASSIGN_STATUS;
	}

	/**
	 * @return the tYRE_PURCHASE_DATE
	 */
	public String getTYRE_PURCHASE_DATE() {
		return TYRE_PURCHASE_DATE;
	}

	/**
	 * @param tYRE_PURCHASE_DATE
	 *            the tYRE_PURCHASE_DATE to set
	 */
	public void setTYRE_PURCHASE_DATE(String tYRE_PURCHASE_DATE) {
		TYRE_PURCHASE_DATE = tYRE_PURCHASE_DATE;
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
	 * @return the tYRE_SCRAPED_BY
	 */
	public String getTYRE_SCRAPED_BY() {
		return TYRE_SCRAPED_BY;
	}

	/**
	 * @param tYRE_SCRAPED_BY
	 *            the tYRE_SCRAPED_BY to set
	 */
	public void setTYRE_SCRAPED_BY(String tYRE_SCRAPED_BY) {
		TYRE_SCRAPED_BY = tYRE_SCRAPED_BY;
	}

	/**
	 * @return the tYRE_SCRAPED_NOTE
	 */
	public String getTYRE_SCRAPED_NOTE() {
		return TYRE_SCRAPED_NOTE;
	}

	/**
	 * @param tYRE_SCRAPED_NOTE
	 *            the tYRE_SCRAPED_NOTE to set
	 */
	public void setTYRE_SCRAPED_NOTE(String tYRE_SCRAPED_NOTE) {
		TYRE_SCRAPED_NOTE = tYRE_SCRAPED_NOTE;
	}

	/**
	 * @return the tYRE_SCRAPED_DATE
	 */
	public String getTYRE_SCRAPED_DATE() {
		return TYRE_SCRAPED_DATE;
	}

	/**
	 * @param tYRE_SCRAPED_DATE
	 *            the tYRE_SCRAPED_DATE to set
	 */
	public void setTYRE_SCRAPED_DATE(String tYRE_SCRAPED_DATE) {
		TYRE_SCRAPED_DATE = tYRE_SCRAPED_DATE;
	}

	/**
	 * @return the lASTMODIFIEDBY
	 */
	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	/**
	 * @param lASTMODIFIEDBY
	 *            the lASTMODIFIEDBY to set
	 */
	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}

	/**
	 * @return the lASTUPDATED_DATE
	 */
	public String getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	/**
	 * @param lASTUPDATED_DATE
	 *            the lASTUPDATED_DATE to set
	 */
	public void setLASTUPDATED_DATE(String lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	/**
	 * @return the tYRE_RETREAD_ID
	 */
	public Long getTYRE_RETREAD_ID() {
		return TYRE_RETREAD_ID;
	}

	/**
	 * @param tYRE_RETREAD_ID the tYRE_RETREAD_ID to set
	 */
	public void setTYRE_RETREAD_ID(Long tYRE_RETREAD_ID) {
		TYRE_RETREAD_ID = tYRE_RETREAD_ID;
	}

	/**
	 * @return the tYRE_PURCHASE_DATE_ON
	 */
	public Date getTYRE_PURCHASE_DATE_ON() {
		return TYRE_PURCHASE_DATE_ON;
	}

	/**
	 * @param tYRE_PURCHASE_DATE_ON the tYRE_PURCHASE_DATE_ON to set
	 */
	public void setTYRE_PURCHASE_DATE_ON(Date tYRE_PURCHASE_DATE_ON) {
		TYRE_PURCHASE_DATE_ON = tYRE_PURCHASE_DATE_ON;
	}

	/**
	 * @return the tYRE_ASSIGN_DATE_ON
	 */
	public Date getTYRE_ASSIGN_DATE_ON() {
		return TYRE_ASSIGN_DATE_ON;
	}

	/**
	 * @param tYRE_ASSIGN_DATE_ON the tYRE_ASSIGN_DATE_ON to set
	 */
	public void setTYRE_ASSIGN_DATE_ON(Date tYRE_ASSIGN_DATE_ON) {
		TYRE_ASSIGN_DATE_ON = tYRE_ASSIGN_DATE_ON;
	}

	/**
	 * @return the tYRE_SCRAPED_DATE_ON
	 */
	public Date getTYRE_SCRAPED_DATE_ON() {
		return TYRE_SCRAPED_DATE_ON;
	}

	/**
	 * @param tYRE_SCRAPED_DATE_ON the tYRE_SCRAPED_DATE_ON to set
	 */
	public void setTYRE_SCRAPED_DATE_ON(Date tYRE_SCRAPED_DATE_ON) {
		TYRE_SCRAPED_DATE_ON = tYRE_SCRAPED_DATE_ON;
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

	public Integer getAXLE() {
		return AXLE;
	}

	public void setAXLE(Integer aXLE) {
		AXLE = aXLE;
	}

	public String getPreviousTyreManufacturer() {
		return previousTyreManufacturer;
	}

	public void setPreviousTyreManufacturer(String previousTyreManufacturer) {
		this.previousTyreManufacturer = previousTyreManufacturer;
	}

	public String getPreviousSerialNumber() {
		return previousSerialNumber;
	}

	public void setPreviousSerialNumber(String previousSerialNumber) {
		this.previousSerialNumber = previousSerialNumber;
	}

	public int getPreviousTyreOpenOdo() {
		return previousTyreOpenOdo;
	}

	public void setPreviousTyreOpenOdo(int previousTyreOpenOdo) {
		this.previousTyreOpenOdo = previousTyreOpenOdo;
	}

	public String getPreviousAssignedDate() {
		return previousAssignedDate;
	}

	public void setPreviousAssignedDate(String previousAssignedDate) {
		this.previousAssignedDate = previousAssignedDate;
	}
	
	public int getPreviousTyreUsage() {
		return previousTyreUsage;
	}

	public void setPreviousTyreUsage(int previousTyreUsage) {
		this.previousTyreUsage = previousTyreUsage;
	}
	
	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}

	public String getSubLocation() {
		return subLocation;
	}

	public void setSubLocation(String subLocation) {
		this.subLocation = subLocation;
	}
	
	public Integer getTyreGuage() {
		return tyreGuage;
	}

	public void setTyreGuage(Integer tyreGuage) {
		this.tyreGuage = tyreGuage;
	}
	
	public short getDismountedTyreStatusId() {
		return dismountedTyreStatusId;
	}

	public void setDismountedTyreStatusId(short dismountedTyreStatusId) {
		this.dismountedTyreStatusId = dismountedTyreStatusId;
	}

	public String getDismountedTyreStatus() {
		return dismountedTyreStatus;
	}

	public void setDismountedTyreStatus(String dismountedTyreStatus) {
		this.dismountedTyreStatus = dismountedTyreStatus;
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
		InventoryTyreDto other = (InventoryTyreDto) obj;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryTyreDto [TYRE_ID=");
		builder.append(TYRE_ID);
		builder.append(", TYRE_IN_NUMBER=");
		builder.append(TYRE_IN_NUMBER);
		builder.append(", TYRE_NUMBER=");
		builder.append(TYRE_NUMBER);
		builder.append(", TYRE_AMOUNT=");
		builder.append(TYRE_AMOUNT);
		builder.append(", TYRE_MANUFACTURER=");
		builder.append(TYRE_MANUFACTURER);
		builder.append(", TYRE_MODEL=");
		builder.append(TYRE_MODEL);
		builder.append(", TYRE_SIZE_ID=");
		builder.append(TYRE_SIZE_ID);
		builder.append(", TYRE_SIZE=");
		builder.append(TYRE_SIZE);
		builder.append(", TYRE_TREAD=");
		builder.append(TYRE_TREAD);
		builder.append(", WAREHOUSE_LOCATION=");
		builder.append(WAREHOUSE_LOCATION);
		builder.append(", ITYRE_AMOUNT_ID=");
		builder.append(ITYRE_AMOUNT_ID);
		builder.append(", ITYRE_INVOICE_ID=");
		builder.append(ITYRE_INVOICE_ID);
		builder.append(", VEHICLE_ID=");
		builder.append(VEHICLE_ID);
		builder.append(", VEHICLE_REGISTRATION=");
		builder.append(VEHICLE_REGISTRATION);
		builder.append(", OPEN_ODOMETER=");
		builder.append(OPEN_ODOMETER);
		builder.append(", CLOSE_ODOMETER=");
		builder.append(CLOSE_ODOMETER);
		builder.append(", TYRE_USEAGE=");
		builder.append(TYRE_USEAGE);
		builder.append(", TYRE_RETREAD_COUNT=");
		builder.append(TYRE_RETREAD_COUNT);
		builder.append(", TYRE_PURCHASE_DATE=");
		builder.append(TYRE_PURCHASE_DATE);
		builder.append(", TYRE_ASSIGN_DATE=");
		builder.append(TYRE_ASSIGN_DATE);
		builder.append(", TYRE_ASSIGN_STATUS=");
		builder.append(TYRE_ASSIGN_STATUS);
		builder.append(", TYRE_SCRAPED_BY=");
		builder.append(TYRE_SCRAPED_BY);
		builder.append(", TYRE_SCRAPED_NOTE=");
		builder.append(TYRE_SCRAPED_NOTE);
		builder.append(", TYRE_SCRAPED_DATE=");
		builder.append(TYRE_SCRAPED_DATE);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", previousTyreManufacturer=");
		builder.append(previousTyreManufacturer);
		builder.append(", previousSerialNumber=");
		builder.append(previousSerialNumber);
		builder.append(", subLocationId=");
		builder.append(subLocationId);
		builder.append(", subLocation=");
		builder.append(subLocation);
		builder.append("]");
		return builder.toString();
	}
	

	public static String getPaymentModeName(short status) {

		String statusName		=	null;
		switch (status) {
		  case TYRE_ASSIGN_STATUS_AVAILABLE:
			  statusName	= TYRE_ASSIGN_STATUS_AVAILABLE_NAME;
		        break;
		  case TYRE_ASSIGN_STATUS_IN_SERVICE: 
			  statusName	= TYRE_ASSIGN_STATUS_IN_SERVICE_NAME;
		        break;
		  case TYRE_ASSIGN_STATUS_SCRAPED: 
			  statusName	= TYRE_ASSIGN_STATUS_SCRAPED_NAME;
		        break;
		  case TYRE_ASSIGN_STATUS_SENT_RETREAD: 
			  statusName	= TYRE_ASSIGN_STATUS_SENT_RETREAD_NAME;
		        break;
		  case TYRE_ASSIGN_STATUS_IN_PROCESS: 
			  statusName	= TYRE_ASSIGN_STATUS_IN_PROCESS_NAME;
		        break;  
		  case TYRE_ASSIGN_STATUS_SOLD: 
			  statusName	= TYRE_ASSIGN_STATUS_SOLD_NAME;
		        break; 
		  case TYRE_ASSIGN_STATUS_UNAVAILABLE: 
			  statusName	= TYRE_ASSIGN_STATUS_UNAVAILABLE_NAME;
			  break; 
		  case TYRE_ASSIGN_STATUS_INTRANSIT: 
			  statusName	= TYRE_ASSIGN_STATUS_INTRANSIT_NAME;
			  break; 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}

	public String getPOSITION() {
		return POSITION;
	}

	public void setPOSITION(String pOSITION) {
		POSITION = pOSITION;
	}

	public Long getStatusWiseTyreCount() {
		return statusWiseTyreCount;
	}

	public void setStatusWiseTyreCount(Long statusWiseTyreCount) {
		this.statusWiseTyreCount = statusWiseTyreCount;
	}

	public String getTyreAssignLayoutPosition() {
		return tyreAssignLayoutPosition;
	}
	public String getVehicleGroup() {
		return vehicleGroup;
	}

	public void setTyreAssignLayoutPosition(String tyreAssignLayoutPosition) {
		this.tyreAssignLayoutPosition = tyreAssignLayoutPosition;
	}
	public void setVehicleGroup(String vehicleGroup) {
		this.vehicleGroup = vehicleGroup;
	}
	

	public String getPositionStr() {
		return positionStr;
}

	public void setPositionStr(String positionStr) {
		this.positionStr = positionStr;
	}

	public String getVehicleNumberStr() {
		return vehicleNumberStr;
	}

	public void setVehicleNumberStr(String vehicleNumberStr) {
		this.vehicleNumberStr = vehicleNumberStr;
	}

	public String getPositionT() {
		return positionT;
	}

	public void setPositionT(String positionT) {
		this.positionT = positionT;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
