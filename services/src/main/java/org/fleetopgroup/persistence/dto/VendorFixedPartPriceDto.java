package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

import ch.qos.logback.classic.pattern.Util;

public class VendorFixedPartPriceDto {

	/** The value for the VPPID by email field */
	private Long VPPID;

	/** The value for the VENDORID by email field */
	private Integer VENDORID;

	/** The value for the VENDORNAME by email field */
	private String VENDORNAME;

	/** The value for the PARTID by email field */
	private Long PARTID;

	/** The value for the PARTID by email field */
	private String PARTNUMBER;

	/** The value for the PARTNAME by email field */
	private String PARTNAME;

	/** The value for the PARTQUANTITY by email field */
	private Double PARTQUANTITY;

	/** The value for the PARTEACHCOST by email field */
	private Double PARTEACHCOST;

	/** The value for the PARTDISCOUNT by email field */
	private Double PARTDISCOUNT;

	/** The value for the PARTGST by email field */
	private Double PARTGST;

	/** The value for the PARTTOTAL by email field */
	private Double PARTTOTAL;

	/** The value for the created by email field */
	private String CREATEDBY;

	/** The value for the lastUpdated By email field */
	private String LASTMODIFIEDBY;

	boolean markForDelete;

	/** The value for the created DATE field */
	private Date CREATEDDATE;

	/** The value for the lastUpdated DATE field */
	private Date LASTUPDATED;

	public VendorFixedPartPriceDto() {
		super();

	}

	public VendorFixedPartPriceDto(Long vPPID, Integer vENDORID, String vENDORNAME, Long pARTID, String pARTNUMBER,
			String pARTNAME, String cREATEDBY, String lASTMODIFIEDBY, boolean MarkForDelete, Date cREATEDDATE,
			Date lASTUPDATED) {
		super();
		VPPID = vPPID;
		VENDORID = vENDORID;
		VENDORNAME = vENDORNAME;
		PARTID = pARTID;
		PARTNUMBER = pARTNUMBER;
		PARTNAME = pARTNAME;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATEDDATE = cREATEDDATE;
		LASTUPDATED = lASTUPDATED;
	}

	/**
	 * @return the vPPID
	 */
	public Long getVPPID() {
		return VPPID;
	}

	/**
	 * @param vPPID
	 *            the vPPID to set
	 */
	public void setVPPID(Long vPPID) {
		VPPID = vPPID;
	}

	/**
	 * @return the vENDORID
	 */
	public Integer getVENDORID() {
		return VENDORID;
	}

	/**
	 * @param vENDORID
	 *            the vENDORID to set
	 */
	public void setVENDORID(Integer vENDORID) {
		VENDORID = vENDORID;
	}

	/**
	 * @return the vENDORNAME
	 */
	public String getVENDORNAME() {
		return VENDORNAME;
	}

	/**
	 * @param vENDORNAME
	 *            the vENDORNAME to set
	 */
	public void setVENDORNAME(String vENDORNAME) {
		VENDORNAME = vENDORNAME;
	}

	/**
	 * @return the pARTID
	 */
	public Long getPARTID() {
		return PARTID;
	}

	/**
	 * @param pARTID
	 *            the pARTID to set
	 */
	public void setPARTID(Long pARTID) {
		PARTID = pARTID;
	}

	/**
	 * @return the pARTNUMBER
	 */
	public String getPARTNUMBER() {
		return PARTNUMBER;
	}

	/**
	 * @param pARTNUMBER
	 *            the pARTNUMBER to set
	 */
	public void setPARTNUMBER(String pARTNUMBER) {
		PARTNUMBER = pARTNUMBER;
	}

	/**
	 * @return the pARTNAME
	 */
	public String getPARTNAME() {
		return PARTNAME;
	}

	/**
	 * @param pARTNAME
	 *            the pARTNAME to set
	 */
	public void setPARTNAME(String pARTNAME) {
		PARTNAME = pARTNAME;
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
	 * @return the cREATEDDATE
	 */
	public Date getCREATEDDATE() {
		return CREATEDDATE;
	}

	/**
	 * @param cREATEDDATE
	 *            the cREATEDDATE to set
	 */
	public void setCREATEDDATE(Date cREATEDDATE) {
		CREATEDDATE = cREATEDDATE;
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
	 * @return the pARTQUANTITY
	 */
	public Double getPARTQUANTITY() {
		return PARTQUANTITY;
	}

	/**
	 * @param pARTQUANTITY
	 *            the pARTQUANTITY to set
	 */
	public void setPARTQUANTITY(Double pARTQUANTITY) {
		PARTQUANTITY = Utility.round(pARTQUANTITY, 2);
	}

	/**
	 * @return the pARTEACHCOST
	 */
	public Double getPARTEACHCOST() {
		return PARTEACHCOST;
	}

	/**
	 * @param pARTEACHCOST
	 *            the pARTEACHCOST to set
	 */
	public void setPARTEACHCOST(Double pARTEACHCOST) {
		PARTEACHCOST = Utility.round(pARTEACHCOST, 2);
	}

	/**
	 * @return the pARTDISCOUNT
	 */
	public Double getPARTDISCOUNT() {
		return PARTDISCOUNT;
	}

	/**
	 * @param pARTDISCOUNT
	 *            the pARTDISCOUNT to set
	 */
	public void setPARTDISCOUNT(Double pARTDISCOUNT) {
		PARTDISCOUNT = Utility.round(pARTDISCOUNT, 2);
	}

	/**
	 * @return the pARTGST
	 */
	public Double getPARTGST() {
		return PARTGST;
	}

	/**
	 * @param pARTGST
	 *            the pARTGST to set
	 */
	public void setPARTGST(Double pARTGST) {
		PARTGST = Utility.round(pARTGST, 2);
	}

	/**
	 * @return the pARTTOTAL
	 */
	public Double getPARTTOTAL() {
		return PARTTOTAL;
	}

	/**
	 * @param pARTTOTAL
	 *            the pARTTOTAL to set
	 */
	public void setPARTTOTAL(Double pARTTOTAL) {
		PARTTOTAL = Utility.round(pARTTOTAL, 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VendorFixedPartPriceDto [VPPID=");
		builder.append(VPPID);
		builder.append(", VENDORID=");
		builder.append(VENDORID);
		builder.append(", VENDORNAME=");
		builder.append(VENDORNAME);
		builder.append(", PARTID=");
		builder.append(PARTID);
		builder.append(", PARTNUMBER=");
		builder.append(PARTNUMBER);
		builder.append(", PARTNAME=");
		builder.append(PARTNAME);
		builder.append(", PARTQUANTITY=");
		builder.append(PARTQUANTITY);
		builder.append(", PARTEACHCOST=");
		builder.append(PARTEACHCOST);
		builder.append(", PARTDISCOUNT=");
		builder.append(PARTDISCOUNT);
		builder.append(", PARTGST=");
		builder.append(PARTGST);
		builder.append(", PARTTOTAL=");
		builder.append(PARTTOTAL);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATEDDATE=");
		builder.append(CREATEDDATE);
		builder.append(", LASTUPDATED=");
		builder.append(LASTUPDATED);
		builder.append("]");
		return builder.toString();
	}

}
