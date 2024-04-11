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
@Table(name = "InventoryTyreTransfer")
public class InventoryTyreTransfer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ITTID")
	private Long ITTID;
	
	@Column(name = "TYRE_ID")
	private Long TYRE_ID;

	/** The value for the Vehicle Registration Number field */
	@Column(name = "TYRE_NUMBER", nullable = false, length = 150)
	private String TYRE_NUMBER;

	
	@Column(name = "TRA_FROM_LOCATION_ID")
	private Integer TRA_FROM_LOCATION_ID;

	@Column(name = "TRA_TO_LOCATION_ID")
	private Integer TRA_TO_LOCATION_ID;

	@Column(name = "TRA_QUANTITY", length = 10)
	private Double TRA_QUANTITY;

	@Column(name = "TRANSFER_DATE")
	private Date TRANSFER_DATE;

	@Column(name = "TRANSFER_BY_ID")
	private Long TRANSFER_BY_ID;

	@Column(name = "TRANSFER_RECEIVEDBYID")
	private Long TRANSFER_RECEIVEDBYID;

	@Column(name = "TRANSFER_RECEIVEDDATE")
	private Date TRANSFER_RECEIVEDDATE;

	@Column(name = "TRANSFER_RECEIVEDREASON", length = 250)
	private String TRANSFER_RECEIVEDREASON;

	@Column(name = "TRANSFER_VIA_ID")
	private short TRANSFER_VIA_ID;

	@Column(name = "TRANSFER_REASON", length = 250)
	private String TRANSFER_REASON;

	@Column(name = "TRA_INVENTORY_INVOCEID")
	private Long TRA_INVENTORY_INVOCEID;


	@Column(name = "TRANSFER_STATUS_ID")
	private short TRANSFER_STATUS_ID;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer	COMPANY_ID;

	
	@Column(name = "CREATEDBYID", nullable = false)
	private Long CREATEDBYID;

	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;

	@Basic(optional = false)
	@Column(name = "LASTUPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date LASTUPDATED_DATE;
	
	@Column(name = "requisitionTransferId")
	private Long requisitionTransferId;

	public InventoryTyreTransfer() {
		super();
	}

	public InventoryTyreTransfer(Long iTTID, Long tYRE_ID, String tYRE_NUMBER, Double tRA_QUANTITY,
			Date tRANSFER_DATE,
			Date tRANSFER_RECEIVEDDATE, String tRANSFER_RECEIVEDREASON, String tRANSFER_REASON,
			Long tRA_INVENTORY_INVOCEID, String tRANSFER_STATUS, Long cREATEDBY, Long lASTMODIFIEDBY,
			boolean MarkForDelete, Date cREATED_DATE, Date lASTUPDATED_DATE, Integer cOMPANY_ID) {
		super();
		ITTID = iTTID;
		TYRE_ID = tYRE_ID;
		TYRE_NUMBER = tYRE_NUMBER;
		TRA_QUANTITY = tRA_QUANTITY;
		TRANSFER_DATE = tRANSFER_DATE;
		TRANSFER_RECEIVEDDATE = tRANSFER_RECEIVEDDATE;
		TRANSFER_RECEIVEDREASON = tRANSFER_RECEIVEDREASON;
		TRANSFER_REASON = tRANSFER_REASON;
		TRA_INVENTORY_INVOCEID = tRA_INVENTORY_INVOCEID;
		COMPANY_ID	= cOMPANY_ID;
		CREATEDBYID = cREATEDBY;
		LASTMODIFIEDBYID = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}


	public Long getITTID() {
		return ITTID;
	}

	public void setITTID(Long iTTID) {
		ITTID = iTTID;
	}

	
	public Long getTYRE_ID() {
		return TYRE_ID;
	}

	public void setTYRE_ID(Long tYRE_ID) {
		TYRE_ID = tYRE_ID;
	}

	public String getTYRE_NUMBER() {
		return TYRE_NUMBER;
	}

	public void setTYRE_NUMBER(String tYRE_NUMBER) {
		TYRE_NUMBER = tYRE_NUMBER;
	}


	public Double getTRA_QUANTITY() {
		return TRA_QUANTITY;
	}

	public void setTRA_QUANTITY(Double tRA_QUANTITY) {
		TRA_QUANTITY = tRA_QUANTITY;
	}

	public Date getTRANSFER_DATE() {
		return TRANSFER_DATE;
	}

	public void setTRANSFER_DATE(Date tRANSFER_DATE) {
		TRANSFER_DATE = tRANSFER_DATE;
	}


	public Date getTRANSFER_RECEIVEDDATE() {
		return TRANSFER_RECEIVEDDATE;
	}

	public void setTRANSFER_RECEIVEDDATE(Date tRANSFER_RECEIVEDDATE) {
		TRANSFER_RECEIVEDDATE = tRANSFER_RECEIVEDDATE;
	}

	public String getTRANSFER_RECEIVEDREASON() {
		return TRANSFER_RECEIVEDREASON;
	}

	public void setTRANSFER_RECEIVEDREASON(String tRANSFER_RECEIVEDREASON) {
		TRANSFER_RECEIVEDREASON = tRANSFER_RECEIVEDREASON;
	}

	public String getTRANSFER_REASON() {
		return TRANSFER_REASON;
	}

	public void setTRANSFER_REASON(String tRANSFER_REASON) {
		TRANSFER_REASON = tRANSFER_REASON;
	}


	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}


	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean MarkForDelete) {
		markForDelete = MarkForDelete;
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

	public Long getTRA_INVENTORY_INVOCEID() {
		return TRA_INVENTORY_INVOCEID;
	}

	public void setTRA_INVENTORY_INVOCEID(Long tRA_INVENTORY_INVOCEID) {
		TRA_INVENTORY_INVOCEID = tRA_INVENTORY_INVOCEID;
	}

	/**
	 * @return the tRA_FROM_LOCATION_ID
	 */
	public Integer getTRA_FROM_LOCATION_ID() {
		return TRA_FROM_LOCATION_ID;
	}

	/**
	 * @param tRA_FROM_LOCATION_ID the tRA_FROM_LOCATION_ID to set
	 */
	public void setTRA_FROM_LOCATION_ID(Integer tRA_FROM_LOCATION_ID) {
		TRA_FROM_LOCATION_ID = tRA_FROM_LOCATION_ID;
	}

	/**
	 * @return the tRA_TO_LOCATION_ID
	 */
	public Integer getTRA_TO_LOCATION_ID() {
		return TRA_TO_LOCATION_ID;
	}

	/**
	 * @param tRA_TO_LOCATION_ID the tRA_TO_LOCATION_ID to set
	 */
	public void setTRA_TO_LOCATION_ID(Integer tRA_TO_LOCATION_ID) {
		TRA_TO_LOCATION_ID = tRA_TO_LOCATION_ID;
	}

	/**
	 * @return the tRANSFER_BY_ID
	 */
	public Long getTRANSFER_BY_ID() {
		return TRANSFER_BY_ID;
	}

	/**
	 * @param tRANSFER_BY_ID the tRANSFER_BY_ID to set
	 */
	public void setTRANSFER_BY_ID(Long tRANSFER_BY_ID) {
		TRANSFER_BY_ID = tRANSFER_BY_ID;
	}

	/**
	 * @return the tRANSFER_RECEIVEDBYID
	 */
	public Long getTRANSFER_RECEIVEDBYID() {
		return TRANSFER_RECEIVEDBYID;
	}

	/**
	 * @param tRANSFER_RECEIVEDBYID the tRANSFER_RECEIVEDBYID to set
	 */
	public void setTRANSFER_RECEIVEDBYID(Long tRANSFER_RECEIVEDBYID) {
		TRANSFER_RECEIVEDBYID = tRANSFER_RECEIVEDBYID;
	}

	/**
	 * @return the tRANSFER_STATUS_ID
	 */
	public short getTRANSFER_STATUS_ID() {
		return TRANSFER_STATUS_ID;
	}

	/**
	 * @param tRANSFER_STATUS_ID the tRANSFER_STATUS_ID to set
	 */
	public void setTRANSFER_STATUS_ID(short tRANSFER_STATUS_ID) {
		TRANSFER_STATUS_ID = tRANSFER_STATUS_ID;
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
	 * @return the tRANSFER_VIA_ID
	 */
	public short getTRANSFER_VIA_ID() {
		return TRANSFER_VIA_ID;
	}

	/**
	 * @param tRANSFER_VIA_ID the tRANSFER_VIA_ID to set
	 */
	public void setTRANSFER_VIA_ID(short tRANSFER_VIA_ID) {
		TRANSFER_VIA_ID = tRANSFER_VIA_ID;
	}

	public Long getRequisitionTransferId() {
		return requisitionTransferId;
	}

	public void setRequisitionTransferId(Long requisitionTransferId) {
		this.requisitionTransferId = requisitionTransferId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ITTID == null) ? 0 : ITTID.hashCode());
		result = prime * result + ((TRANSFER_BY_ID == null) ? 0 : TRANSFER_BY_ID.hashCode());
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
		InventoryTyreTransfer other = (InventoryTyreTransfer) obj;
		if (ITTID == null) {
			if (other.ITTID != null)
				return false;
		} else if (!ITTID.equals(other.ITTID))
			return false;
		if (TRANSFER_BY_ID == null) {
			if (other.TRANSFER_BY_ID != null)
				return false;
		} else if (!TRANSFER_BY_ID.equals(other.TRANSFER_BY_ID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryTyreTransfer [ITTID=");
		builder.append(ITTID);
		builder.append(", TYRE_ID=");
		builder.append(TYRE_ID);
		builder.append(", TYRE_NUMBER=");
		builder.append(TYRE_NUMBER);
		builder.append(", TRA_QUANTITY=");
		builder.append(TRA_QUANTITY);
		builder.append(", TRANSFER_DATE=");
		builder.append(TRANSFER_DATE);
		builder.append(", TRANSFER_RECEIVEDDATE=");
		builder.append(TRANSFER_RECEIVEDDATE);
		builder.append(", TRANSFER_RECEIVEDREASON=");
		builder.append(TRANSFER_RECEIVEDREASON);
		builder.append(", TRANSFER_REASON=");
		builder.append(TRANSFER_REASON);
		builder.append(", TRA_INVENTORY_INVOCEID=");
		builder.append(TRA_INVENTORY_INVOCEID);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
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