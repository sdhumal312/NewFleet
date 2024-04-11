package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FuelStockDetails")
public class FuelStockDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "fuelStockDetailsId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	fuelStockDetailsId;
	
	@Column(name = "petrolPumpId")
	private Integer	petrolPumpId;
	
	@Column(name = "stockQuantity")
	private Double	stockQuantity;
	
	@Column(name = "avgFuelRate")
	private Double	avgFuelRate;
	
	@Column(name = "totalFuelCost")
	private Double	totalFuelCost;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;
	

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
		this.stockQuantity = stockQuantity;
	}

	public Double getAvgFuelRate() {
		return avgFuelRate;
	}

	public void setAvgFuelRate(Double avgFuelRate) {
		this.avgFuelRate = avgFuelRate;
	}

	public Double getTotalFuelCost() {
		return totalFuelCost;
	}

	public void setTotalFuelCost(Double totalFuelCost) {
		this.totalFuelCost = totalFuelCost;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	
	
}
