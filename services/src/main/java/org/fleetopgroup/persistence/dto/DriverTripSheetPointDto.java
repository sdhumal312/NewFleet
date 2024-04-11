package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class DriverTripSheetPointDto {

	/** The value for the DRIVER ID field */
	private int DRIVERID;

	/** The value for the DRIVER FRIST NAME field */
	private String DRIVER_NAME;

	/**
	 * The value for the TRIP_ROUTE_POINT TOTAL
	 */
	private Double TRIP_ROUTE_POINT;

	/**
	 * The value for the DRIVER HALT_POINT
	 */
	private Double HALT_POINT;

	/** The value for the DRIVER TRIP ID field */
	private Double POINT_TOTAL;

	public DriverTripSheetPointDto() {
		super();
	}

	public DriverTripSheetPointDto(int dRIVERID, String dRIVER_NAME, Double tRIP_ROUTE_POINT, Double hALT_POINT,
			Double pOINT_TOTAL) {
		super();
		DRIVERID = dRIVERID;
		DRIVER_NAME = dRIVER_NAME;
		TRIP_ROUTE_POINT = tRIP_ROUTE_POINT;
		HALT_POINT = hALT_POINT;
		POINT_TOTAL = pOINT_TOTAL;
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
	 * @return the tRIP_ROUTE_POINT
	 */
	public Double getTRIP_ROUTE_POINT() {
		return TRIP_ROUTE_POINT;
	}

	/**
	 * @param tRIP_ROUTE_POINT
	 *            the tRIP_ROUTE_POINT to set
	 */
	public void setTRIP_ROUTE_POINT(Double tRIP_ROUTE_POINT) {
		TRIP_ROUTE_POINT = Utility.round(tRIP_ROUTE_POINT, 2);
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
		HALT_POINT = Utility.round(hALT_POINT, 2);
	}

	/**
	 * @return the pOINT_TOTAL
	 */
	public Double getPOINT_TOTAL() {
		return POINT_TOTAL;
	}

	/**
	 * @param pOINT_TOTAL
	 *            the pOINT_TOTAL to set
	 */
	public void setPOINT_TOTAL(Double pOINT_TOTAL) {
		POINT_TOTAL = Utility.round(pOINT_TOTAL, 2) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverTripSheetPointDto [DRIVERID=");
		builder.append(DRIVERID);
		builder.append(", DRIVER_NAME=");
		builder.append(DRIVER_NAME);
		builder.append(", TRIP_ROUTE_POINT=");
		builder.append(TRIP_ROUTE_POINT);
		builder.append(", HALT_POINT=");
		builder.append(HALT_POINT);
		builder.append(", POINT_TOTAL=");
		builder.append(POINT_TOTAL);
		builder.append("]");
		return builder.toString();
	}

}
