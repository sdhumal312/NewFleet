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
@Table(name="ClothInventoryDetails")
public class ClothInventoryDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clothInventoryDetailsId")
	private Long	clothInventoryDetailsId;
	
	@Column(name = "clothInvoiceId")
	private Long	clothInvoiceId;
	
	@Column(name = "clothTypesId")
	private Long	clothTypesId;
	
	@Column(name = "quantity")
	private Double 	quantity;
	
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

	public Long getClothInventoryDetailsId() {
		return clothInventoryDetailsId;
	}

	public Long getClothInvoiceId() {
		return clothInvoiceId;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public Double getDiscount() {
		return discount;
	}

	public Double getTax() {
		return tax;
	}

	public Double getTotal() {
		return total;
	}

	public Long getPurchaseorder_id() {
		return purchaseorder_id;
	}

	public Integer getVendor_id() {
		return vendor_id;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public Date getCreated() {
		return created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setClothInventoryDetailsId(Long clothInventoryDetailsId) {
		this.clothInventoryDetailsId = clothInventoryDetailsId;
	}

	public void setClothInvoiceId(Long clothInvoiceId) {
		this.clothInvoiceId = clothInvoiceId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public void setPurchaseorder_id(Long purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}

	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public void setCreated(Date created) {
		this.created = created;
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

	public short getDiscountTaxTypeId() {
		return discountTaxTypeId;
	}

	public void setDiscountTaxTypeId(short discountTaxTypeId) {
		this.discountTaxTypeId = discountTaxTypeId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clothInventoryDetailsId == null) ? 0 : clothInventoryDetailsId.hashCode());
		result = prime * result + ((clothInvoiceId == null) ? 0 : clothInvoiceId.hashCode());
		result = prime * result + ((clothTypesId == null) ? 0 : clothTypesId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClothInventoryDetails other = (ClothInventoryDetails) obj;
		if (clothInventoryDetailsId == null) {
			if (other.clothInventoryDetailsId != null)
				return false;
		} else if (!clothInventoryDetailsId.equals(other.clothInventoryDetailsId))
			return false;
		if (clothInvoiceId == null) {
			if (other.clothInvoiceId != null)
				return false;
		} else if (!clothInvoiceId.equals(other.clothInvoiceId))
			return false;
		if (clothTypesId == null) {
			if (other.clothTypesId != null)
				return false;
		} else if (!clothTypesId.equals(other.clothTypesId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClothInventoryDetails [clothInventoryDetailsId=");
		builder.append(clothInventoryDetailsId);
		builder.append(", clothInvoiceId=");
		builder.append(clothInvoiceId);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", quantity=");
		builder.append(quantity);
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
		builder.append("]");
		return builder.toString();
	}
	
	
}
