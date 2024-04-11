package org.fleetopgroup.persistence.dto;

import javax.persistence.Column;


public class RouteWiseTripSheetWeightDto {

	private Long routeWiseTripSheetWeightId;
	
	private String routeName;
	
	private Double actualWeight;
	
	private Double scaleWeight;
	
	private Long tripSheetId;
	
	private Integer companyId;

	
	public RouteWiseTripSheetWeightDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public RouteWiseTripSheetWeightDto(Long routeWiseTripSheetWeightId, String routeName, Double actualWeight,
			Double scaleWeight, Long tripSheetId, Integer companyId) {
		super();
		this.routeWiseTripSheetWeightId = routeWiseTripSheetWeightId;
		this.routeName = routeName;
		this.actualWeight = actualWeight;
		this.scaleWeight = scaleWeight;
		this.tripSheetId = tripSheetId;
		this.companyId = companyId;
	}


	public Long getRouteWiseTripSheetWeightId() {
		return routeWiseTripSheetWeightId;
	}


	public void setRouteWiseTripSheetWeightId(Long routeWiseTripSheetWeightId) {
		this.routeWiseTripSheetWeightId = routeWiseTripSheetWeightId;
	}


	public String getRouteName() {
		return routeName;
	}


	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}


	public Double getActualWeight() {
		return actualWeight;
	}


	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}


	public Double getScaleWeight() {
		return scaleWeight;
	}


	public void setScaleWeight(Double scaleWeight) {
		this.scaleWeight = scaleWeight;
	}


	public Long getTripSheetId() {
		return tripSheetId;
	}


	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}


	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	@Override
	public String toString() {
		return "RouteWiseTripSheetWeightDto [routeWiseTripSheetWeightId=" + routeWiseTripSheetWeightId + ", routeName="
				+ routeName + ", actualWeight=" + actualWeight + ", scaleWeight=" + scaleWeight + ", tripSheetId="
				+ tripSheetId + ", companyId=" + companyId + "]";
	}
	
	
	
}