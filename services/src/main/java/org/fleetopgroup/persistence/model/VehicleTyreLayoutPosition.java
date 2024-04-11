/**
 * 
 */
package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author fleetop
 *
 */
@Entity
@Table(name = "VehicleTyreLayoutPosition")
public class VehicleTyreLayoutPosition implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value for the Vehicle Tyre Layout Id field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LP_ID")
	private Long LP_ID;
	
	/** The value for the Vehicle ID field */
	@Column(name = "VEHICLE_ID", nullable = false)
	private Integer VEHICLE_ID;

	/** The value for the Vehicle Axle field */
	@Column(name = "AXLE", nullable = false)
	private Integer AXLE;

	/** The value for the Vehicle Axle Tyre position field */
	@Column(name = "POSITION", nullable = false, length = 25)
	private String POSITION;
	
	
	/** The value for the Vehicle Tyre  size Number position field *//*
	@Column(name = "TYRE_SIZE", length = 20)
	private String TYRE_SIZE;*/
	
	@Column(name = "TYRE_SIZE_ID")
	private Integer TYRE_SIZE_ID;

	/**
	 * The value for the Vehicle Tyre TYRE_PRESSURE Number position field
	 */
	@Column(name = "TYRE_PRESSURE", length = 50)
	private String TYRE_PRESSURE;

	/** The value for the Vehicle Tyre ID position field */
	@Column(name = "TYRE_ID")
	private Long TYRE_ID;

	/** The value for the Vehicle Tyre Serial Number position field */
	@Column(name = "TYRE_SERIAL_NO", length = 150)
	private String TYRE_SERIAL_NO;

	/** The value for the TYRE_ASSIGNED field */
	@Basic(optional = false)
	@Column(name = "TYRE_ASSIGNED", nullable = false)
	private boolean TYRE_ASSIGNED = false;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "tyreTypeId")
	private short tyreTypeId;
	
	@Column(name = "oldTyreId")
	private Long oldTyreId;
	
	@Column(name = "oldTyreMoveId")
	private short oldTyreMoveId;
	
	@Column(name = "alignment")
	private short alignment;
	
	@Column(name = "kinpin")
	private short kinpin;
	
	@Column(name = "assignFromId")
	private short assignFromId;
	
	@Column(name = "tyreModelId")
	private Integer tyreModelId;
	
	@Column(name = "tyreGauge")
	private Integer tyreGauge;

	@Column(name = "remark")
	private String remark;
	
	@Column(name = "transactionTypeId")
	private short transactionTypeId;
	
	@Column(name = "transactionId")
	private Long transactionId;
	
	@Column(name = "transactionSubId")
	private Long transactionSubId;

	public VehicleTyreLayoutPosition() {
		super();
	}

	public VehicleTyreLayoutPosition(Long lP_ID, Long tYRE_ID) {
		super();
		LP_ID = lP_ID;
		TYRE_ID = tYRE_ID;
	}
	
	

	/**
	 * @return the vEHICLE_ID
	 */
	public Integer getVEHICLE_ID() {
		return VEHICLE_ID;
	}

	/**
	 * @param vEHICLE_ID the vEHICLE_ID to set
	 */
	public void setVEHICLE_ID(Integer vEHICLE_ID) {
		VEHICLE_ID = vEHICLE_ID;
	}

	/**
	 * @return the lP_ID
	 */
	public Long getLP_ID() {
		return LP_ID;
	}

	/**
	 * @param lP_ID
	 *            the lP_ID to set
	 */
	public void setLP_ID(Long lP_ID) {
		LP_ID = lP_ID;
	}

	/**
	 * @return the aXLE
	 */
	public Integer getAXLE() {
		return AXLE;
	}

	/**
	 * @param aXLE
	 *            the aXLE to set
	 */
	public void setAXLE(Integer aXLE) {
		AXLE = aXLE;
	}

	/**
	 * @return the pOSITION
	 */
	public String getPOSITION() {
		return POSITION;
	}

	/**
	 * @param pOSITION
	 *            the pOSITION to set
	 */
	public void setPOSITION(String pOSITION) {
		POSITION = pOSITION;
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
	 * @return the tYRE_SERIAL_NO
	 */
	public String getTYRE_SERIAL_NO() {
		return TYRE_SERIAL_NO;
	}

	/**
	 * @param tYRE_SERIAL_NO
	 *            the tYRE_SERIAL_NO to set
	 */
	public void setTYRE_SERIAL_NO(String tYRE_SERIAL_NO) {
		TYRE_SERIAL_NO = tYRE_SERIAL_NO;
	}

	/**
	 * @return the tYRE_ASSIGNED
	 */
	public boolean isTYRE_ASSIGNED() {
		return TYRE_ASSIGNED;
	}

	/**
	 * @param tYRE_ASSIGNED
	 *            the tYRE_ASSIGNED to set
	 */
	public void setTYRE_ASSIGNED(boolean tYRE_ASSIGNED) {
		TYRE_ASSIGNED = tYRE_ASSIGNED;
	}

	
	/**
	 * @return the tYRE_SIZE
	 *//*
	public String getTYRE_SIZE() {
		return TYRE_SIZE;
	}

	*//**
	 * @param tYRE_SIZE the tYRE_SIZE to set
	 *//*
	public void setTYRE_SIZE(String tYRE_SIZE) {
		TYRE_SIZE = tYRE_SIZE;
	}*/

	/**
	 * @return the tYRE_PRESSURE
	 */
	public String getTYRE_PRESSURE() {
		return TYRE_PRESSURE;
	}

	/**
	 * @param tYRE_PRESSURE the tYRE_PRESSURE to set
	 */
	public void setTYRE_PRESSURE(String tYRE_PRESSURE) {
		TYRE_PRESSURE = tYRE_PRESSURE;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}


	/**
	 * @return the tYRE_SIZE_ID
	 */
	public Integer getTYRE_SIZE_ID() {
		return TYRE_SIZE_ID;
	}

	/**
	 * @param tYRE_SIZE_ID the tYRE_SIZE_ID to set
	 */
	public void setTYRE_SIZE_ID(Integer tYRE_SIZE_ID) {
		TYRE_SIZE_ID = tYRE_SIZE_ID;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	public short getTyreTypeId() {
		return tyreTypeId;
	}

	public void setTyreTypeId(short tyreTypeId) {
		this.tyreTypeId = tyreTypeId;
	}

	public Long getOldTyreId() {
		return oldTyreId;
	}

	public void setOldTyreId(Long oldTyreId) {
		this.oldTyreId = oldTyreId;
	}

	public short getOldTyreMoveId() {
		return oldTyreMoveId;
	}

	public void setOldTyreMoveId(short oldTyreMoveId) {
		this.oldTyreMoveId = oldTyreMoveId;
	}

	public short getAlignment() {
		return alignment;
	}

	public void setAlignment(short alignment) {
		this.alignment = alignment;
	}

	public short getKinpin() {
		return kinpin;
	}

	public void setKinpin(short kinpin) {
		this.kinpin = kinpin;
	}
	

	public short getAssignFromId() {
		return assignFromId;
	}

	public void setAssignFromId(short assignFromId) {
		this.assignFromId = assignFromId;
	}

	public Integer getTyreModelId() {
		return tyreModelId;
	}

	public void setTyreModelId(Integer tyreModelId) {
		this.tyreModelId = tyreModelId;
	}

	public Integer getTyreGauge() {
		return tyreGauge;
	}

	public void setTyreGauge(Integer tyreGauge) {
		this.tyreGauge = tyreGauge;
	}
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public short getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(short transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	

	public Long getTransactionSubId() {
		return transactionSubId;
	}

	public void setTransactionSubId(Long transactionSubId) {
		this.transactionSubId = transactionSubId;
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
		result = prime * result + ((LP_ID == null) ? 0 : LP_ID.hashCode());
		result = prime * result + ((TYRE_ID == null) ? 0 : TYRE_ID.hashCode());
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
		VehicleTyreLayoutPosition other = (VehicleTyreLayoutPosition) obj;
		if (LP_ID == null) {
			if (other.LP_ID != null)
				return false;
		} else if (!LP_ID.equals(other.LP_ID))
			return false;
		if (TYRE_ID == null) {
			if (other.TYRE_ID != null)
				return false;
		} else if (!TYRE_ID.equals(other.TYRE_ID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VehicleTyreLayoutPosition [LP_ID=" + LP_ID + ", VEHICLE_ID=" + VEHICLE_ID + ", AXLE=" + AXLE
				+ ", POSITION=" + POSITION + ", TYRE_SIZE_ID=" + TYRE_SIZE_ID + ", TYRE_PRESSURE=" + TYRE_PRESSURE
				+ ", TYRE_ID=" + TYRE_ID + ", TYRE_SERIAL_NO=" + TYRE_SERIAL_NO + ", TYRE_ASSIGNED=" + TYRE_ASSIGNED
				+ ", COMPANY_ID=" + COMPANY_ID + ", markForDelete=" + markForDelete + ", tyreTypeId=" + tyreTypeId
				+ ", oldTyreId=" + oldTyreId + ", oldTyreMoveId=" + oldTyreMoveId + ", alignment=" + alignment
				+ ", kinpin=" + kinpin + ", assignFromId=" + assignFromId + ", tyreModelId=" + tyreModelId
				+ ", tyreGauge=" + tyreGauge + ", remark=" + remark + ", transactionTypeId=" + transactionTypeId
				+ ", transactionId=" + transactionId + "]";
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	

}
