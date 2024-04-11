package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MasterPartsExtraDetails")
public class MasterPartsExtraDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "partsExtraDetailsId")
	private Long	partsExtraDetailsId;
	
	@Column(name = "partid")
	private Long partid;
	
	@Column(name = "originalBrand", nullable = false)
	private Long originalBrand;
	
	@Column(name = "vehicleManufacturerId")
	private Long vehicleManufacturerId;
	
	@Column(name = "vehicleModelId")
	private Long vehicleModelId;
	
	@Column(name = "mainPacking")
	private String mainPacking;
	
	@Column(name = "uomPacking")
	private String uomPacking;
	
	@Column(name = "looseItem")
	private String looseItem;
	
	@Column(name = "looseUom")
	private String looseUom;
	
	@Column(name = "barCodeNumber")
	private String barCodeNumber;
	
	@Column(name = "itemType")
	private short itemType;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "dimention")
	private String dimention;
	
	@Column(name = "markForDelete")
	private boolean markForDelete;

	public Long getPartsExtraDetailsId() {
		return partsExtraDetailsId;
	}

	public void setPartsExtraDetailsId(Long partsExtraDetailsId) {
		this.partsExtraDetailsId = partsExtraDetailsId;
	}

	public Long getPartid() {
		return partid;
	}

	public void setPartid(Long partid) {
		this.partid = partid;
	}

	public Long getOriginalBrand() {
		return originalBrand;
	}

	public void setOriginalBrand(Long originalBrand) {
		this.originalBrand = originalBrand;
	}

	public Long getVehicleManufacturerId() {
		return vehicleManufacturerId;
	}

	public void setVehicleManufacturerId(Long vehicleManufacturerId) {
		this.vehicleManufacturerId = vehicleManufacturerId;
	}

	public Long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(Long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public String getMainPacking() {
		return mainPacking;
	}

	public void setMainPacking(String mainPacking) {
		this.mainPacking = mainPacking;
	}

	public String getUomPacking() {
		return uomPacking;
	}

	public void setUomPacking(String uomPacking) {
		this.uomPacking = uomPacking;
	}

	public String getLooseItem() {
		return looseItem;
	}

	public void setLooseItem(String looseItem) {
		this.looseItem = looseItem;
	}

	public String getLooseUom() {
		return looseUom;
	}

	public void setLooseUom(String looseUom) {
		this.looseUom = looseUom;
	}

	public String getBarCodeNumber() {
		return barCodeNumber;
	}

	public void setBarCodeNumber(String barCodeNumber) {
		this.barCodeNumber = barCodeNumber;
	}

	public short getItemType() {
		return itemType;
	}

	public void setItemType(short itemType) {
		this.itemType = itemType;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getDimention() {
		return dimention;
	}

	public void setDimention(String dimention) {
		this.dimention = dimention;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	
	
	
}
