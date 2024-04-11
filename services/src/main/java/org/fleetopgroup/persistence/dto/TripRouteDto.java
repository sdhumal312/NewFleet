package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class TripRouteDto {
	
	public static final short ROUTE_TYPE_MAIN_ROUTE		= 1;
	public static final short ROUTE_TYPE_SUB_ROUTE		= 2;
	
	public static final String ROUTE_TYPE_MAIN_ROUTE_NAME	= "MAIN ROUTE";
	public static final String ROUTE_TYPE_SUB_ROUTE_NAME	= "SUB ROUTE";

	private Integer routeID;
	
	private Integer routeNumber;
	
	private Integer routeType;
	
	private Integer mainRouteId;
	
	private String mainRoute;

	private String routeName;

	private String routeNo;

	private String routeTimeFrom;

	private String routeTimeTo;

	private Double routrAttendance;

	private Double routeTotalHour;

	private Double routeTotalLiter;

	private Integer routeApproximateKM;

	private String routeRemarks;

	private String createdBy;

	private String lastModifiedBy;
	
	private long vehicleGroupId;
	
	private Date created;
	
	private Integer maxAllowedOdometer;
	
	private Integer minAllowedOdometer;
	
	

	/**
	 * @return the routeID
	 */
	public Integer getRouteID() {
		return routeID;
	}

	/**
	 * @param routeID
	 *            the routeID to set
	 */
	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public Integer getRouteNumber() {
		return routeNumber;
	}

	public void setRouteNumber(Integer routeNumber) {
		this.routeNumber = routeNumber;
	}

	/**
	 * @return the routeName
	 */
	public String getRouteName() {
		return routeName;
	}

	/**
	 * @param routeName
	 *            the routeName to set
	 */
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	/**
	 * @return the routeNo
	 */
	public String getRouteNo() {
		return routeNo;
	}

	/**
	 * @param routeNo
	 *            the routeNo to set
	 */
	public void setRouteNo(String routeNo) {
		this.routeNo = routeNo;
	}

	/**
	 * @return the routeApproximateKM
	 */
	public Integer getRouteApproximateKM() {
		return routeApproximateKM;
	}

	/**
	 * @param routeApproximateKM
	 *            the routeApproximateKM to set
	 */
	public void setRouteApproximateKM(Integer routeApproximateKM) {
		this.routeApproximateKM = routeApproximateKM;
	}

	/**
	 * @return the routeRemarks
	 */
	public String getRouteRemarks() {
		return routeRemarks;
	}

	/**
	 * @param routeRemarks
	 *            the routeRemarks to set
	 */
	public void setRouteRemarks(String routeRemarks) {
		this.routeRemarks = routeRemarks;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the mainRoute
	 */
	public String getMainRoute() {
		return mainRoute;
	}

	/**
	 * @param mainRoute the mainRoute to set
	 */
	public void setMainRoute(String mainRoute) {
		this.mainRoute = mainRoute;
	}

	/**
	 * @return the routeTimeFrom
	 */
	public String getRouteTimeFrom() {
		return routeTimeFrom;
	}

	/**
	 * @param routeTimeFrom
	 *            the routeTimeFrom to set
	 */
	public void setRouteTimeFrom(String routeTimeFrom) {
		this.routeTimeFrom = routeTimeFrom;
	}

	/**
	 * @return the routeTimeTo
	 */
	public String getRouteTimeTo() {
		return routeTimeTo;
	}

	/**
	 * @param routeTimeTo
	 *            the routeTimeTo to set
	 */
	public void setRouteTimeTo(String routeTimeTo) {
		this.routeTimeTo = routeTimeTo;
	}

	/**
	 * @return the routrAttendance
	 */
	public Double getRoutrAttendance() {
		return routrAttendance;
	}

	/**
	 * @param routrAttendance
	 *            the routrAttendance to set
	 */
	public void setRoutrAttendance(Double routrAttendance) {
		this.routrAttendance = Utility.round(routrAttendance, 2);
	}

	/**
	 * @return the routeTotalHour
	 */
	public Double getRouteTotalHour() {
		return routeTotalHour;
	}

	/**
	 * @param routeTotalHour
	 *            the routeTotalHour to set
	 */
	public void setRouteTotalHour(Double routeTotalHour) {
		this.routeTotalHour = Utility.round(routeTotalHour, 2);
	}

	/**
	 * @return the routeTotalLiter
	 */
	public Double getRouteTotalLiter() {
		return routeTotalLiter;
	}

	/**
	 * @param routeTotalLiter
	 *            the routeTotalLiter to set
	 */
	public void setRouteTotalLiter(Double routeTotalLiter) {
		this.routeTotalLiter = Utility.round(routeTotalLiter, 2);
	}

	public long getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}
	
	

	public Integer getRouteType() {
		return routeType;
	}

	public void setRouteType(Integer routeType) {
		this.routeType = routeType;
	}

	/**
	 * @return the mainRouteId
	 */
	public Integer getMainRouteId() {
		return mainRouteId;
	}

	/**
	 * @param mainRouteId the mainRouteId to set
	 */
	public void setMainRouteId(Integer mainRouteId) {
		this.mainRouteId = mainRouteId;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getMaxAllowedOdometer() {
		return maxAllowedOdometer;
	}

	public void setMaxAllowedOdometer(Integer maxAllowedOdometer) {
		this.maxAllowedOdometer = maxAllowedOdometer;
	}

	public Integer getMinAllowedOdometer() {
		return minAllowedOdometer;
	}

	public void setMinAllowedOdometer(Integer minAllowedOdometer) {
		this.minAllowedOdometer = minAllowedOdometer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripRouteDto [routeID=");
		builder.append(routeID);
		builder.append(", routeName=");
		builder.append(routeName);
		builder.append(", routeNo=");
		builder.append(routeNo);
		builder.append(", routeTimeFrom=");
		builder.append(routeTimeFrom);
		builder.append(", routeTimeTo=");
		builder.append(routeTimeTo);
		builder.append(", routrAttendance=");
		builder.append(routrAttendance);
		builder.append(", routeTotalHour=");
		builder.append(routeTotalHour);
		builder.append(", routeTotalLiter=");
		builder.append(routeTotalLiter);
		builder.append(", routeApproximateKM=");
		builder.append(routeApproximateKM);
		builder.append(", routeRemarks=");
		builder.append(routeRemarks);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", vehicleGroupId=");
		builder.append(vehicleGroupId);
		builder.append("]");
		return builder.toString();
	}

}