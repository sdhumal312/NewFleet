package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class DriverHaltDto {

	/** The value for the DRIVERATTENDANCE ID field */
	private Long DHID;

	/** The value for the DRIVER ID field */
	private int DRIVERID;

	/** The value for the DRIVER FRIST NAME field */
	private String DRIVER_NAME;
	
	private String driverLastName;

	private String driverFatherName;
	
	private String HALT_DATE_FROM;
	
	private Date HALT_DATE_FROM_ON;
	
	/**
	 * The value for the DRIVER to create ATTENDANCE name one user name field
	 */
	private String HALT_DATE_TO;
	
	private Date HALT_DATE_TO_ON;

	/** The value for the DRIVER TRIP ID field */
	private Long TRIPSHEETID;
	
	/** The value for the tripSheetNumber ID field */
	private Long tripSheetNumber;

	/** The value for the DRIVER TRIP NAME field */
	private String TRIP_ROUTE_NAME;

	/** The value for the HALT_AMOUNT NAME field */
	private Double HALT_AMOUNT;

	private Integer VID;

	/** The value for the VID field */
	private String VEHICLE_NAME;

	/** The value for the REFERENCE_NO field */
	private String REFERENCE_NO;

	/** The value for the HALT_POINT field */
	private Double HALT_POINT;

	/** The value for the HALT_REASON NAME field */
	private String HALT_REASON;

	/** The value for the HALT_PLACE NAME field */
	private String HALT_PLACE;
	
	/** The value for the HALT_PLACE NAME field */
	private Integer HALT_PLACE_ID;

	/** The value for the HALT_PAIDBY NAME field */
	private String HALT_PAIDBY;
	
	/** The value for the HALT PLACE TYPE NAME field */
	private short HALT_PLACE_TYPE_ID;
	private String HALT_PLACE_TYPE_NAME;

	/** The value for the CREATEDBY NAME field */
	private String CREATEDBY;

	/** The value for the LASTUPDATEDBY NAME field */
	private String LASTUPDATEDBY;

	/** The value for the STATUS NAME field */
	boolean markForDelete;

	/** The value for the CREATED NAME field */
	private Date CREATED;

	/** The value for the LASTUPDATED NAME field */
	private Date LASTUPDATED;

	public DriverHaltDto() {
		super();
	}

	public DriverHaltDto(Long dHID, int dRIVERID, String dRIVER_NAME, String hALT_DATE_FROM, String hALT_DATE_TO,
			Long tRIPSHEETID, String tRIP_ROUTE_NAME, Double hALT_AMOUNT, Double hALT_POINT, String hALT_REASON,
			String hALT_PLACE, String hALT_PAIDBY, String cREATEDBY, String lASTUPDATEDBY, boolean MarkForDelete, Date cREATED,
			Date lASTUPDATED) {
		super();
		DHID = dHID;
		DRIVERID = dRIVERID;
		DRIVER_NAME = dRIVER_NAME;
		HALT_DATE_FROM = hALT_DATE_FROM;
		HALT_DATE_TO = hALT_DATE_TO;
		TRIPSHEETID = tRIPSHEETID;
		TRIP_ROUTE_NAME = tRIP_ROUTE_NAME;
		HALT_AMOUNT = hALT_AMOUNT;
		HALT_POINT = hALT_POINT;
		HALT_REASON = hALT_REASON;
		HALT_PLACE = hALT_PLACE;
		HALT_PAIDBY = hALT_PAIDBY;
		CREATEDBY = cREATEDBY;
		LASTUPDATEDBY = lASTUPDATEDBY;
		markForDelete = MarkForDelete;
		CREATED = cREATED;
		LASTUPDATED = lASTUPDATED;
	}

	
	public Date getHALT_DATE_FROM_ON() {
		return HALT_DATE_FROM_ON;
	}

	public void setHALT_DATE_FROM_ON(Date hALT_DATE_FROM_ON) {
		HALT_DATE_FROM_ON = hALT_DATE_FROM_ON;
	}

	public Date getHALT_DATE_TO_ON() {
		return HALT_DATE_TO_ON;
	}

	public void setHALT_DATE_TO_ON(Date hALT_DATE_TO_ON) {
		HALT_DATE_TO_ON = hALT_DATE_TO_ON;
	}

	public Integer getHALT_PLACE_ID() {
		return HALT_PLACE_ID;
	}

	public void setHALT_PLACE_ID(Integer hALT_PLACE_ID) {
		HALT_PLACE_ID = hALT_PLACE_ID;
	}

	/**
	 * @return the dHID
	 */
	public Long getDHID() {
		return DHID;
	}

	/**
	 * @param dHID
	 *            the dHID to set
	 */
	public void setDHID(Long dHID) {
		DHID = dHID;
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
	 * @return the dRIVER_NAME
	 */
	public String getDRIVER_NAME() {
		return DRIVER_NAME;
	}

	/**
	 * @param dRIVER_NAME
	 *            the dRIVER_NAME to set
	 */
	public void setDRIVER_NAME(String dRIVER_NAME) {
		DRIVER_NAME = dRIVER_NAME;
	}

	/**
	 * @return the hALT_DATE_FROM
	 */
	public String getHALT_DATE_FROM() {
		return HALT_DATE_FROM;
	}

	/**
	 * @param hALT_DATE_FROM
	 *            the hALT_DATE_FROM to set
	 */
	public void setHALT_DATE_FROM(String hALT_DATE_FROM) {
		HALT_DATE_FROM = hALT_DATE_FROM;
	}

	/**
	 * @return the hALT_DATE_TO
	 */
	public String getHALT_DATE_TO() {
		return HALT_DATE_TO;
	}

	/**
	 * @param hALT_DATE_TO
	 *            the hALT_DATE_TO to set
	 */
	public void setHALT_DATE_TO(String hALT_DATE_TO) {
		HALT_DATE_TO = hALT_DATE_TO;
	}

	/**
	 * @return the tRIPSHEETID
	 */
	public Long getTRIPSHEETID() {
		return TRIPSHEETID;
	}

	/**
	 * @param tRIPSHEETID
	 *            the tRIPSHEETID to set
	 */
	public void setTRIPSHEETID(Long tRIPSHEETID) {
		TRIPSHEETID = tRIPSHEETID;
	}

	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	/**
	 * @return the tRIP_ROUTE_NAME
	 */
	public String getTRIP_ROUTE_NAME() {
		return TRIP_ROUTE_NAME;
	}

	/**
	 * @param tRIP_ROUTE_NAME
	 *            the tRIP_ROUTE_NAME to set
	 */
	public void setTRIP_ROUTE_NAME(String tRIP_ROUTE_NAME) {
		TRIP_ROUTE_NAME = tRIP_ROUTE_NAME;
	}

	/**
	 * @return the hALT_AMOUNT
	 */
	public Double getHALT_AMOUNT() {
		return HALT_AMOUNT;
	}

	/**
	 * @param hALT_AMOUNT
	 *            the hALT_AMOUNT to set
	 */
	public void setHALT_AMOUNT(Double hALT_AMOUNT) {
		HALT_AMOUNT = Utility.round(hALT_AMOUNT, 2);
	}

	/**
	 * @return the hALT_POINT
	 */
	public Double getHALT_POINT() {
		return HALT_POINT;
	}

	/**
	 * @param hALT_POINT
	 *            the hALT_POINT to set
	 */
	public void setHALT_POINT(Double hALT_POINT) {
		HALT_POINT =Utility.round(hALT_POINT,2);
	}

	/**
	 * @return the hALT_REASON
	 */
	public String getHALT_REASON() {
		return HALT_REASON;
	}

	/**
	 * @param hALT_REASON
	 *            the hALT_REASON to set
	 */
	public void setHALT_REASON(String hALT_REASON) {
		HALT_REASON = hALT_REASON;
	}

	/**
	 * @return the hALT_PLACE
	 */
	public String getHALT_PLACE() {
		return HALT_PLACE;
	}

	/**
	 * @param hALT_PLACE
	 *            the hALT_PLACE to set
	 */
	public void setHALT_PLACE(String hALT_PLACE) {
		HALT_PLACE = hALT_PLACE;
	}

	/**
	 * @return the hALT_PAIDBY
	 */
	public String getHALT_PAIDBY() {
		return HALT_PAIDBY;
	}

	/**
	 * @param hALT_PAIDBY
	 *            the hALT_PAIDBY to set
	 */
	public void setHALT_PAIDBY(String hALT_PAIDBY) {
		HALT_PAIDBY = hALT_PAIDBY;
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
	 * @return the lASTUPDATEDBY
	 */
	public String getLASTUPDATEDBY() {
		return LASTUPDATEDBY;
	}

	/**
	 * @param lASTUPDATEDBY
	 *            the lASTUPDATEDBY to set
	 */
	public void setLASTUPDATEDBY(String lASTUPDATEDBY) {
		LASTUPDATEDBY = lASTUPDATEDBY;
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
	public Date getLASTUPDATED() {
		return LASTUPDATED;
	}

	/**
	 * @param lASTUPDATED
	 *            the lASTUPDATED to set
	 */
	public void setLASTUPDATED(Date lASTUPDATED) {
		LASTUPDATED = lASTUPDATED;
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
	 * @return the vEHICLE_NAME
	 */
	public String getVEHICLE_NAME() {
		return VEHICLE_NAME;
	}

	/**
	 * @param vEHICLE_NAME
	 *            the vEHICLE_NAME to set
	 */
	public void setVEHICLE_NAME(String vEHICLE_NAME) {
		VEHICLE_NAME = vEHICLE_NAME;
	}

	/**
	 * @return the rEFERENCE_NO
	 */
	public String getREFERENCE_NO() {
		return REFERENCE_NO;
	}

	/**
	 * @param rEFERENCE_NO
	 *            the rEFERENCE_NO to set
	 */
	public void setREFERENCE_NO(String rEFERENCE_NO) {
		REFERENCE_NO = rEFERENCE_NO;
	}
	

	public short getHALT_PLACE_TYPE_ID() {
		return HALT_PLACE_TYPE_ID;
	}

	public void setHALT_PLACE_TYPE_ID(short hALT_PLACE_TYPE_ID) {
		HALT_PLACE_TYPE_ID = hALT_PLACE_TYPE_ID;
	}

	public String getHALT_PLACE_TYPE_NAME() {
		return HALT_PLACE_TYPE_NAME;
	}

	public void setHALT_PLACE_TYPE_NAME(String hALT_PLACE_TYPE_NAME) {
		HALT_PLACE_TYPE_NAME = hALT_PLACE_TYPE_NAME;
	}

	public String getDriverLastName() {
		return driverLastName;
	}

	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}

	public String getDriverFatherName() {
		return driverFatherName;
	}

	public void setDriverFatherName(String driverFatherName) {
		this.driverFatherName = driverFatherName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverHalt [DHID=");
		builder.append(DHID);
		builder.append(", DRIVERID=");
		builder.append(DRIVERID);
		builder.append(", DRIVER_NAME=");
		builder.append(DRIVER_NAME);
		builder.append(", HALT_DATE_FROM=");
		builder.append(HALT_DATE_FROM);
		builder.append(", HALT_DATE_TO=");
		builder.append(HALT_DATE_TO);
		builder.append(", TRIPSHEETID=");
		builder.append(TRIPSHEETID);
		builder.append(", TRIP_ROUTE_NAME=");
		builder.append(TRIP_ROUTE_NAME);
		builder.append(", HALT_AMOUNT=");
		builder.append(HALT_AMOUNT);
		builder.append(", HALT_POINT=");
		builder.append(HALT_POINT);
		builder.append(", HALT_REASON=");
		builder.append(HALT_REASON);
		builder.append(", HALT_PLACE=");
		builder.append(HALT_PLACE);
		builder.append(", HALT_PAIDBY=");
		builder.append(HALT_PAIDBY);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTUPDATEDBY=");
		builder.append(LASTUPDATEDBY);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", LASTUPDATED=");
		builder.append(LASTUPDATED);
		builder.append("]");
		return builder.toString();
	}
}
