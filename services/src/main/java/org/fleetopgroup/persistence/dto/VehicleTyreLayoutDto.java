/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.util.Date;

/**
 * @author fleetop
 *
 */
public class VehicleTyreLayoutDto {

	private Long L_ID;

	private Integer VEHICLE_ID;
	private String VEHICLE_REGISTRATION;
	private Integer AXLE;
	private String TYRE_FRONT_SIZE;
	private Integer TYRE_FRONT_SIZE_ID;
	private String TYRE_FRONT_PRESSURE;
	private String TYRE_REAR_SIZE;
	private Integer TYRE_REAR_SIZE_ID;
	private String TYRE_REAR_PRESSURE;
	private Integer COMPANY_ID;
	private String CREATEDBY;
	private Long CREATEDBYID;
	private Date CREATED_DATE;
	private boolean markForDelete;

	
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
	 * @return the tYRE_FRONT_SIZE
	 */
	public String getTYRE_FRONT_SIZE() {
		return TYRE_FRONT_SIZE;
	}



	/**
	 * @param tYRE_FRONT_SIZE the tYRE_FRONT_SIZE to set
	 */
	public void setTYRE_FRONT_SIZE(String tYRE_FRONT_SIZE) {
		TYRE_FRONT_SIZE = tYRE_FRONT_SIZE;
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
	 * @return the tYRE_REAR_SIZE
	 */
	public String getTYRE_REAR_SIZE() {
		return TYRE_REAR_SIZE;
	}



	/**
	 * @param tYRE_REAR_SIZE the tYRE_REAR_SIZE to set
	 */
	public void setTYRE_REAR_SIZE(String tYRE_REAR_SIZE) {
		TYRE_REAR_SIZE = tYRE_REAR_SIZE;
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
		builder.append(", VEHICLE_REGISTRATION=");
		builder.append(VEHICLE_REGISTRATION);
		builder.append(", AXLE=");
		builder.append(AXLE);
		builder.append(", TYRE_FRONT_SIZE=");
		builder.append(TYRE_FRONT_SIZE);
		builder.append(", TYRE_FRONT_PRESSURE=");
		builder.append(TYRE_FRONT_PRESSURE);
		builder.append(", TYRE_REAR_SIZE=");
		builder.append(TYRE_REAR_SIZE);
		builder.append(", TYRE_REAR_PRESSURE=");
		builder.append(TYRE_REAR_PRESSURE);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append("]");
		return builder.toString();
	}


}
