package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="InventoryDamageAndLostHistory")
public class InventoryDamageAndLostHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "InventoryDamageAndLostHistoryId")
	private Long	InventoryDamageAndLostHistoryId;
	
	@Column(name = "clothTypesId")
	private Long	clothTypesId;

	@Column(name = "wareHouseLocation")
	private Integer	wareHouseLocation;

	@Column(name = "damagedQuantity")
	private Double	damagedQuantity;
	
	@Column(name = "laundryInvoiceId")
	private Long laundryInvoiceId;

	@Column(name = "losedQuantity")
	private Double	losedQuantity;

	@Column(name = "vendor_id")
	private Integer vendor_id;

	@Column(name = "receiveById")
	private Long	receiveById;

	@Column(name = "companyId")
	private Integer	companyId;

	@Column(name = "markForDelete")
	private boolean	markForDelete;

	@Column(name = "createdDate")
	private Date	createdDate;
	
	@Column(name = "stockTypeId")
	private short	stockTypeId;
	
	@Column(name = "remark")
	private String	remark;
	
	@Column(name = "lastModifiedById")
	private Long	lastModifiedById;
	
	@Column(name = "lastModifiedOn")
	private Date	lastModifiedOn;
	
	@Column(name = "vid")
	private Integer	vid;

	@Column(name = "driverId")
	private Integer driverId;
	
	
	public InventoryDamageAndLostHistory() {
		super();
	}

	public InventoryDamageAndLostHistory(Long InventoryDamageAndLostHistoryId, Long clothTypesId, Integer wareHouseLocation,
			Double damagedQuantity, Long laundryInvoiceId, Double losedQuantity, Integer vendor_id, Long receiveById,
			Integer	companyId, boolean	markForDelete, Date	createdDate, short	stockTypeId, String	remark, 
			Long lastModifiedById, Date	lastModifiedOn, Integer vid,Integer driverId) {
		super();
		this.InventoryDamageAndLostHistoryId = InventoryDamageAndLostHistoryId;
		this.clothTypesId = clothTypesId;
		this.wareHouseLocation = wareHouseLocation;
		this.damagedQuantity = damagedQuantity;
		this.laundryInvoiceId = laundryInvoiceId;
		this.losedQuantity = losedQuantity;
		this.vendor_id = vendor_id;
		this.receiveById = receiveById;
		this.companyId = companyId;
		this.receiveById = receiveById;
		this.markForDelete = markForDelete;
		this.createdDate = createdDate;
		this.stockTypeId = stockTypeId;
		this.remark = remark;
		this.lastModifiedById = lastModifiedById;
		this.lastModifiedOn = lastModifiedOn;
		this.vid = vid;
		this.driverId = driverId;
		
	}

	public Long getInventoryDamageAndLostHistoryId() {
		return InventoryDamageAndLostHistoryId;
	}

	public void setInventoryDamageAndLostHistoryId(Long inventoryDamageAndLostHistoryId) {
		InventoryDamageAndLostHistoryId = inventoryDamageAndLostHistoryId;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
	}

	public Double getDamagedQuantity() {
		return damagedQuantity;
	}

	public void setDamagedQuantity(Double damagedQuantity) {
		this.damagedQuantity = damagedQuantity;
	}

	public Long getLaundryInvoiceId() {
		return laundryInvoiceId;
	}

	public void setLaundryInvoiceId(Long laundryInvoiceId) {
		this.laundryInvoiceId = laundryInvoiceId;
	}

	public Double getLosedQuantity() {
		return losedQuantity;
	}

	public void setLosedQuantity(Double losedQuantity) {
		this.losedQuantity = losedQuantity;
	}

	public Integer getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	public Long getReceiveById() {
		return receiveById;
	}

	public void setReceiveById(Long receiveById) {
		this.receiveById = receiveById;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public short getStockTypeId() {
		return stockTypeId;
	}

	public void setStockTypeId(short stockTypeId) {
		this.stockTypeId = stockTypeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	
	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryDamageAndLostHistory [InventoryDamageAndLostHistoryId=");
		builder.append(InventoryDamageAndLostHistoryId);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", wareHouseLocation=");
		builder.append(wareHouseLocation);
		builder.append(", damagedQuantity=");
		builder.append(damagedQuantity);
		builder.append(", laundryInvoiceId=");
		builder.append(laundryInvoiceId);
		builder.append(", losedQuantity=");
		builder.append(losedQuantity);
		builder.append(", vendor_id=");
		builder.append(vendor_id);
		builder.append(", receiveById=");
		builder.append(receiveById);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", stockTypeId=");
		builder.append(stockTypeId);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", driverId=");
		builder.append(driverId);
		builder.append("]");
		return builder.toString();
	}

	
}	