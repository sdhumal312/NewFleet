package org.fleetopgroup.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UreaInvoiceToDetails")
public class UreaInvoiceToDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ureaInvoiceToDetailsId")
	private Long	ureaInvoiceToDetailsId;
	
	@Column(name = "ureaInvoiceId")
	private Long 		ureaInvoiceId;
	
	@Column(name = "manufacturerId")
	private Long	manufacturerId;
	
	@Column(name = "quantity")
	private Double 	quantity;
	
	@Column(name = "usedQuantity")
	private Double	usedQuantity;
	
	@Column(name = "stockQuantity")
	private Double	stockQuantity;
	
	@Column(name = "unitprice")
	private Double  unitprice;
	
	@Column(name = "discount")
	private Double  discount;
	
	@Column(name = "tax")
	private Double  tax;
	
	@Column(name = "total")
	private Double  total;
	
	@Column(name = "purchaseorder_id")
	private Long 	purchaseorder_id;
	
	@Column(name = "vendor_id")
	private Integer vendor_id;
	
	@Column(name = "wareHouseLocation")
	private Integer	wareHouseLocation;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "created", updatable = false)
	private Date created;
	
	@Column(name = "lastupdated")
	private Date lastupdated;
	
	@Column(name = "subLocationId")
	private Integer subLocationId;
	
	@Column(name = "discountTaxTypeId")
	private short discountTaxTypeId;
	
	@Column(name = "transferQuantity")
	private Double	transferQuantity;
	
	@Column(name = "ureaTransferDetailsId")// this used when we received urea 
	private Long	ureaTransferDetailsId;

	public Long getUreaInvoiceToDetailsId() {
		return ureaInvoiceToDetailsId;
	}

	public void setUreaInvoiceToDetailsId(Long ureaInvoiceToDetailsId) {
		this.ureaInvoiceToDetailsId = ureaInvoiceToDetailsId;
	}

	public Long getUreaInvoiceId() {
		return ureaInvoiceId;
	}

	public void setUreaInvoiceId(Long ureaInvoiceId) {
		this.ureaInvoiceId = ureaInvoiceId;
	}

	public Long getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(Long manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Double stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public Double getUsedQuantity() {
		return usedQuantity;
	}

	public void setUsedQuantity(Double usedQuantity) {
		this.usedQuantity = usedQuantity;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Long getPurchaseorder_id() {
		return purchaseorder_id;
	}

	public void setPurchaseorder_id(Long purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}

	public Integer getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
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

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}
	
	public Double getTransferQuantity() {
		return transferQuantity;
	}

	public short getDiscountTaxTypeId() {
		return discountTaxTypeId;
	}

	public void setDiscountTaxTypeId(short discountTaxTypeId) {
		this.discountTaxTypeId = discountTaxTypeId;
	}

	public void setTransferQuantity(Double transferQuantity) {
		this.transferQuantity = transferQuantity;
	}

	public Long getUreaTransferDetailsId() {
		return ureaTransferDetailsId;
	}

	public void setUreaTransferDetailsId(Long ureaTransferDetailsId) {
		this.ureaTransferDetailsId = ureaTransferDetailsId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UreaInvoiceToDetails [ureaInvoiceToDetailsId=");
		builder.append(ureaInvoiceToDetailsId);
		builder.append(", ureaInvoiceId=");
		builder.append(ureaInvoiceId);
		builder.append(", manufacturerId=");
		builder.append(manufacturerId);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", usedQuantity=");
		builder.append(usedQuantity);
		builder.append(", stockQuantity=");
		builder.append(stockQuantity);
		builder.append(", unitprice=");
		builder.append(unitprice);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", tax=");
		builder.append(tax);
		builder.append(", total=");
		builder.append(total);
		builder.append(", purchaseorder_id=");
		builder.append(purchaseorder_id);
		builder.append(", vendor_id=");
		builder.append(vendor_id);
		builder.append(", wareHouseLocation=");
		builder.append(wareHouseLocation);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", ureaInvoiceToDetailsId=");
		builder.append(ureaInvoiceToDetailsId);
		builder.append(", transferQuantity=");
		builder.append(transferQuantity);
		builder.append(", ureaTransferDetailsId=");
		builder.append(ureaTransferDetailsId);
		builder.append("]");
		return builder.toString();
	}

	

}
