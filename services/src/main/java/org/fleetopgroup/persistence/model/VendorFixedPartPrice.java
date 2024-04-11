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
@Table(name = "VendorFixedPartPrice")
public class VendorFixedPartPrice implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value for the VPPID by email field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VPPID")
	private Long VPPID;

	/** The value for the VENDORID by email field */
	@Column(name = "VENDORID")
	private Integer VENDORID;

	/** The value for the PARTID by email field */
	@Column(name = "PARTID")
	private Long PARTID;

	/** The value for the PARTQUANTITY by email field */
	@Column(name = "PARTQUANTITY")
	private Double PARTQUANTITY;

	/** The value for the PARTEACHCOST by email field */
	@Column(name = "PARTEACHCOST")
	private Double PARTEACHCOST;

	/** The value for the PARTDISCOUNT by email field */
	@Column(name = "PARTDISCOUNT")
	private Double PARTDISCOUNT;

	/** The value for the PARTGST by email field */
	@Column(name = "PARTGST")
	private Double PARTGST;

	/** The value for the PARTTOTAL by email field */
	@Column(name = "PARTTOTAL")
	private Double PARTTOTAL;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	@Column(name = "CREATEDBYID", updatable = false)
	private Long CREATEDBYID;
	
	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "CREATEDDATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATEDDATE;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "LASTUPDATED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date LASTUPDATED;

	public VendorFixedPartPrice() {
		super();

	}

	public VendorFixedPartPrice(Long vPPID, Integer vENDORID, Long pARTID, Double pARTQUANTITY, Double pARTEACHCOST,
			Double pARTDISCOUNT, Double pARTGST, Long cREATEDBY, Long lASTMODIFIEDBY, boolean MarkForDelete,
			Date cREATEDDATE, Date lASTUPDATED, Integer cOMPANY_ID) {
		super();
		VPPID = vPPID;
		VENDORID = vENDORID;
		PARTID = pARTID;
		PARTQUANTITY = pARTQUANTITY;
		PARTEACHCOST = pARTEACHCOST;
		PARTDISCOUNT = pARTDISCOUNT;
		PARTGST = pARTGST;
		CREATEDBYID = cREATEDBY;
		LASTMODIFIEDBYID = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATEDDATE = cREATEDDATE;
		LASTUPDATED = lASTUPDATED;
		COMPANY_ID = cOMPANY_ID;
	}

	public VendorFixedPartPrice(Long pARTID, Double pARTQUANTITY, Double pARTEACHCOST, Double pARTDISCOUNT,
			Double pARTGST, Integer cOMPANY_ID) {
		super();
		PARTID = pARTID;
		PARTQUANTITY = pARTQUANTITY;
		PARTEACHCOST = pARTEACHCOST;
		PARTDISCOUNT = pARTDISCOUNT;
		PARTGST = pARTGST;
		COMPANY_ID = cOMPANY_ID;
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
		PARTQUANTITY = pARTQUANTITY;
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
		PARTEACHCOST = pARTEACHCOST;
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
		PARTDISCOUNT = pARTDISCOUNT;
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
		PARTGST = pARTGST;
	}

/*	*//**
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
	}*/

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
		PARTTOTAL = pARTTOTAL;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/**
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((PARTID == null) ? 0 : PARTID.hashCode());
		result = prime * result + ((VENDORID == null) ? 0 : VENDORID.hashCode());
		result = prime * result + ((VPPID == null) ? 0 : VPPID.hashCode());
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
		VendorFixedPartPrice other = (VendorFixedPartPrice) obj;
		if (PARTID == null) {
			if (other.PARTID != null)
				return false;
		} else if (!PARTID.equals(other.PARTID))
			return false;
		if (VENDORID == null) {
			if (other.VENDORID != null)
				return false;
		} else if (!VENDORID.equals(other.VENDORID))
			return false;
		if (VPPID == null) {
			if (other.VPPID != null)
				return false;
		} else if (!VPPID.equals(other.VPPID))
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
		builder.append("VendorFixedPartPrice [VPPID=");
		builder.append(VPPID);
		builder.append(", VENDORID=");
		builder.append(VENDORID);
		builder.append(", PARTID=");
		builder.append(PARTID);
		builder.append(", PARTQUANTITY=");
		builder.append(PARTQUANTITY);
		builder.append(", PARTEACHCOST=");
		builder.append(PARTEACHCOST);
		builder.append(", PARTDISCOUNT=");
		builder.append(PARTDISCOUNT);
		builder.append(", PARTGST=");
		builder.append(PARTGST);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", CREATEDBYID=");
		builder.append(CREATEDBYID);
		builder.append(", LASTMODIFIEDBYID=");
		builder.append(LASTMODIFIEDBYID);
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
