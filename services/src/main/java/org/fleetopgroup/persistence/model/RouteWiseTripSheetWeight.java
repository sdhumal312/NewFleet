package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RouteWiseTripSheetweight")
public class RouteWiseTripSheetWeight implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long routeWiseTripSheetWeightId;
	
	@Column(name = "routeName")
	private String routeName;
	
	@Column(name = "actualWeight")
	private Double actualWeight;
	
	@Column(name = "scaleWeight")
	private Double scaleWeight;
	
	@Column(name = "tripSheetId")
	private Long tripSheetId;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	
	
	public RouteWiseTripSheetWeight() {
		super();
		// TODO Auto-generated constructor stub
	}


	public RouteWiseTripSheetWeight(Long routeWiseTripSheetWeightId, String routeName, Double actualWeight,
			Double scaleWeight, Long tripSheetId, Integer companyId, Boolean markForDelete) {
		super();
		this.routeWiseTripSheetWeightId = routeWiseTripSheetWeightId;
		this.routeName = routeName;
		this.actualWeight = actualWeight;
		this.scaleWeight = scaleWeight;
		this.tripSheetId = tripSheetId;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
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


	public Boolean getMarkForDelete() {
		return markForDelete;
	}


	public void setMarkForDelete(Boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "RouteWiseTripSheetWeight [routeWiseTripSheetWeightId=" + routeWiseTripSheetWeightId + ", routeName="
				+ routeName + ", actualWeight=" + actualWeight + ", scaleWeight=" + scaleWeight + ", tripSheetId="
				+ tripSheetId + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}
	
}