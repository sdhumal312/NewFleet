package org.fleetopgroup.persistence.dto;

import javax.persistence.Column;

import org.fleetopgroup.web.util.Utility;

public class FuelStockDetailsDto {

	private Long	fuelStockDetailsId;
	
	private Integer	petrolPumpId;
	
	private Double	stockQuantity;
	
	private Double	avgFuelRate;
	
	private Double	totalFuelCost;
	
	private String vendorName;

	public Long getFuelStockDetailsId() {
		return fuelStockDetailsId;
	}

	public void setFuelStockDetailsId(Long fuelStockDetailsId) {
		this.fuelStockDetailsId = fuelStockDetailsId;
	}

	public Integer getPetrolPumpId() {
		return petrolPumpId;
	}

	public void setPetrolPumpId(Integer petrolPumpId) {
		this.petrolPumpId = petrolPumpId;
	}

	public Double getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Double stockQuantity) {
		this.stockQuantity = Utility.round(stockQuantity, 2) ;
	}

	public Double getAvgFuelRate() {
		return avgFuelRate;
	}

	public void setAvgFuelRate(Double avgFuelRate) {
		this.avgFuelRate = Utility.round(avgFuelRate, 2) ;
	}

	public Double getTotalFuelCost() {
		return totalFuelCost;
	}

	public void setTotalFuelCost(Double totalFuelCost) {
		this.totalFuelCost = Utility.round(totalFuelCost, 2) ;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Override
	public String toString() {
		return "FuelStockDetailsDto [fuelStockDetailsId=" + fuelStockDetailsId + ", petrolPumpId=" + petrolPumpId
				+ ", stockQuantity=" + stockQuantity + ", avgFuelRate=" + avgFuelRate + ", totalFuelCost="
				+ totalFuelCost + ", vendorName=" + vendorName + "]";
	}
	
}
