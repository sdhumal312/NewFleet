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
@Table(name = "VehicleTyreLayout")
public class VehicleTyreLayout implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value for the Vehicle Tyre Layout Id field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "L_ID")
	private Long L_ID;

	/** The value for the Vehicle ID field */
	@Column(name = "VEHICLE_ID", nullable = false)
	private Integer VEHICLE_ID;

	
	/**
	 * The value for the Vehicle Axle field id to many position id axle position
	 */
	@Column(name = "AXLE", nullable = false)
	private Integer AXLE;

	
	
	@Column(name = "TYRE_FRONT_SIZE_ID")
	private Integer TYRE_FRONT_SIZE_ID;

	/**
	 * The value for the Vehicle Tyre TYRE_FRONT_PRESSURE Number position field
	 */
	@Column(name = "TYRE_FRONT_PRESSURE", length = 50)
	private String TYRE_FRONT_PRESSURE;

	
	
	@Column(name = "TYRE_REAR_SIZE_ID")
	private Integer TYRE_REAR_SIZE_ID;

	/**
	 * The value for the Vehicle Tyre TYRE_REAR_PRESSURE Number position field
	 */
	@Column(name = "TYRE_REAR_PRESSURE", length = 50)
	private String TYRE_REAR_PRESSURE;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	
	
	@Column(name = "CREATEDBYID", nullable = false, updatable = false)
	private Long CREATEDBYID;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public VehicleTyreLayout() {
		super();
	}

	

	public VehicleTyreLayout(Long l_ID, Integer vEHICLE_ID, String vEHICLE_REGISTRATION, Integer aXLE,
			String tYRE_FRONT_SIZE, String tYRE_FRONT_PRESSURE, String tYRE_REAR_SIZE, String tYRE_REAR_PRESSURE,
			String cREATEDBY, Date cREATED_DATE, Integer companyId) {
		super();
		L_ID = l_ID;
		VEHICLE_ID = vEHICLE_ID;
		AXLE = aXLE;
		TYRE_FRONT_PRESSURE = tYRE_FRONT_PRESSURE;
		TYRE_REAR_PRESSURE = tYRE_REAR_PRESSURE;
		CREATED_DATE = cREATED_DATE;
		COMPANY_ID	= companyId;
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
		result = prime * result + ((AXLE == null) ? 0 : AXLE.hashCode());
		result = prime * result + ((L_ID == null) ? 0 : L_ID.hashCode());
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
		VehicleTyreLayout other = (VehicleTyreLayout) obj;
		if (AXLE == null) {
			if (other.AXLE != null)
				return false;
		} else if (!AXLE.equals(other.AXLE))
			return false;
		if (L_ID == null) {
			if (other.L_ID != null)
				return false;
		} else if (!L_ID.equals(other.L_ID))
			return false;
		if (VEHICLE_ID == null) {
			if (other.VEHICLE_ID != null)
				return false;
		} else if (!VEHICLE_ID.equals(other.VEHICLE_ID))
			return false;
		return true;
	}

	/**
	 * @return the l_ID
	 */
	public Long getL_ID() {
		return L_ID;
	}

	/**
	 * @param l_ID
	 *            the l_ID to set
	 */
	public void setL_ID(Long l_ID) {
		L_ID = l_ID;
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
	 * @return the tYRE_FRONT_PRESSURE
	 */
	public String getTYRE_FRONT_PRESSURE() {
		return TYRE_FRONT_PRESSURE;
	}



	/**
	 * @param tYRE_FRONT_PRESSURE the tYRE_FRONT_PRESSURE to set
	 */
	public void setTYRE_FRONT_PRESSURE(String tYRE_FRONT_PRESSURE) {
		TYRE_FRONT_PRESSURE = tYRE_FRONT_PRESSURE;
	}


	/**
	 * @return the tYRE_REAR_PRESSURE
	 */
	public String getTYRE_REAR_PRESSURE() {
		return TYRE_REAR_PRESSURE;
	}



	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}



	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}



	/**
	 * @param tYRE_REAR_PRESSURE the tYRE_REAR_PRESSURE to set
	 */
	public void setTYRE_REAR_PRESSURE(String tYRE_REAR_PRESSURE) {
		TYRE_REAR_PRESSURE = tYRE_REAR_PRESSURE;
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
	 * @return the tYRE_FRONT_SIZE_ID
	 */
	public Integer getTYRE_FRONT_SIZE_ID() {
		return TYRE_FRONT_SIZE_ID;
	}



	/**
	 * @param tYRE_FRONT_SIZE_ID the tYRE_FRONT_SIZE_ID to set
	 */
	public void setTYRE_FRONT_SIZE_ID(Integer tYRE_FRONT_SIZE_ID) {
		TYRE_FRONT_SIZE_ID = tYRE_FRONT_SIZE_ID;
	}



	/**
	 * @return the tYRE_REAR_SIZE_ID
	 */
	public Integer getTYRE_REAR_SIZE_ID() {
		return TYRE_REAR_SIZE_ID;
	}



	/**
	 * @param tYRE_REAR_SIZE_ID the tYRE_REAR_SIZE_ID to set
	 */
	public void setTYRE_REAR_SIZE_ID(Integer tYRE_REAR_SIZE_ID) {
		TYRE_REAR_SIZE_ID = tYRE_REAR_SIZE_ID;
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



	public boolean isMarkForDelete() {
		return markForDelete;
	}



	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleTyreLayout [L_ID=");
		builder.append(L_ID);
		builder.append(", VEHICLE_ID=");
		builder.append(VEHICLE_ID);
		builder.append(", AXLE=");
		builder.append(AXLE);
		builder.append(", TYRE_FRONT_PRESSURE=");
		builder.append(TYRE_FRONT_PRESSURE);
		builder.append(", TYRE_REAR_PRESSURE=");
		builder.append(TYRE_REAR_PRESSURE);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append("]");
		return builder.toString();
	}

}
