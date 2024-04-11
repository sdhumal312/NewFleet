package org.fleetopgroup.persistence.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PurchaseOrdersToUrea")
public class PurchaseOrdersToUrea implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchaseOrdersToUreaId")
	private Long purchaseOrdersToUreaId;

	@Column(name = "ureaManufacturerId")
	private Long ureaManufacturerId;

	@Column(name = "quantity")
	private Double quantity;

	@Column(name = "notRecivedQuantity", length = 10)
	private Double notRecivedQuantity;
	
	@Column(name = "recivedQuantity", length = 10)
	private Double recivedQuantity;

	@Column(name = "unitCost", length = 10)
	private Double unitCost;
	
	@Column(name = "discount", length = 10)
	private Double discount;

	@Column(name = "tax", length = 10)
	private Double tax;

	@Column(name = "totalcost", length = 10)
	private Double totalcost;
	
	@Column(name = "received_quantity_remark", length = 200)
	private String received_quantity_remark;

	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@Column(name = "purchaseOrderId", nullable = false)
	private Long purchaseOrderId;
	
	@Column(name = "requestedQuantity", length = 10)
	private Double requestedQuantity;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "vendorId")
	private Integer vendorId;
	
	@Column(name = "approvalPartStatusId", nullable = false)
	private short approvalPartStatusId;
	
	public PurchaseOrdersToUrea() {
		super();
	}

	public PurchaseOrdersToUrea(Long purchaseOrdersToUreaId, Long ureaManufacturerId, Double quantity,
			Double notRecivedQuantity, Double recivedQuantity, Double unitCost, Double discount, Double tax,
			Double totalcost, String received_quantity_remark, Integer companyId, boolean markForDelete,
			Long purchaseOrderId,Long ureaInvoiceToDetailsId) {
		super();
		this.purchaseOrdersToUreaId = purchaseOrdersToUreaId;
		this.ureaManufacturerId = ureaManufacturerId;
		this.quantity = quantity;
		this.notRecivedQuantity = notRecivedQuantity;
		this.recivedQuantity = recivedQuantity;
		this.unitCost = unitCost;
		this.discount = discount;
		this.tax = tax;
		this.totalcost = totalcost;
		this.received_quantity_remark = received_quantity_remark;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
		this.purchaseOrderId = purchaseOrderId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((notRecivedQuantity == null) ? 0 : notRecivedQuantity.hashCode());
		result = prime * result + ((purchaseOrderId == null) ? 0 : purchaseOrderId.hashCode());
		result = prime * result + ((purchaseOrdersToUreaId == null) ? 0 : purchaseOrdersToUreaId.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((received_quantity_remark == null) ? 0 : received_quantity_remark.hashCode());
		result = prime * result + ((recivedQuantity == null) ? 0 : recivedQuantity.hashCode());
		result = prime * result + ((tax == null) ? 0 : tax.hashCode());
		result = prime * result + ((totalcost == null) ? 0 : totalcost.hashCode());
		result = prime * result + ((unitCost == null) ? 0 : unitCost.hashCode());
		result = prime * result + ((ureaManufacturerId == null) ? 0 : ureaManufacturerId.hashCode());
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
		PurchaseOrdersToUrea other = (PurchaseOrdersToUrea) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (notRecivedQuantity == null) {
			if (other.notRecivedQuantity != null)
				return false;
		} else if (!notRecivedQuantity.equals(other.notRecivedQuantity))
			return false;
		if (purchaseOrderId == null) {
			if (other.purchaseOrderId != null)
				return false;
		} else if (!purchaseOrderId.equals(other.purchaseOrderId))
			return false;
		if (purchaseOrdersToUreaId == null) {
			if (other.purchaseOrdersToUreaId != null)
				return false;
		} else if (!purchaseOrdersToUreaId.equals(other.purchaseOrdersToUreaId))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (received_quantity_remark == null) {
			if (other.received_quantity_remark != null)
				return false;
		} else if (!received_quantity_remark.equals(other.received_quantity_remark))
			return false;
		if (recivedQuantity == null) {
			if (other.recivedQuantity != null)
				return false;
		} else if (!recivedQuantity.equals(other.recivedQuantity))
			return false;
		if (tax == null) {
			if (other.tax != null)
				return false;
		} else if (!tax.equals(other.tax))
			return false;
		if (totalcost == null) {
			if (other.totalcost != null)
				return false;
		} else if (!totalcost.equals(other.totalcost))
			return false;
		if (unitCost == null) {
			if (other.unitCost != null)
				return false;
		} else if (!unitCost.equals(other.unitCost))
			return false;
		if (ureaManufacturerId == null) {
			if (other.ureaManufacturerId != null)
				return false;
		} else if (!ureaManufacturerId.equals(other.ureaManufacturerId))
			return false;
		return true;
	}

	public Long getPurchaseOrdersToUreaId() {
		return purchaseOrdersToUreaId;
	}

	public void setPurchaseOrdersToUreaId(Long purchaseOrdersToUreaId) {
		this.purchaseOrdersToUreaId = purchaseOrdersToUreaId;
	}

	public Long getUreaManufacturerId() {
		return ureaManufacturerId;
	}

	public void setUreaManufacturerId(Long ureaManufacturerId) {
		this.ureaManufacturerId = ureaManufacturerId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getNotRecivedQuantity() {
		return notRecivedQuantity;
	}

	public void setNotRecivedQuantity(Double notRecivedQuantity) {
		this.notRecivedQuantity = notRecivedQuantity;
	}

	public Double getRecivedQuantity() {
		return recivedQuantity;
	}

	public void setRecivedQuantity(Double recivedQuantity) {
		this.recivedQuantity = recivedQuantity;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
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

	public Double getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(Double totalcost) {
		this.totalcost = totalcost;
	}

	public String getReceived_quantity_remark() {
		return received_quantity_remark;
	}

	public void setReceived_quantity_remark(String received_quantity_remark) {
		this.received_quantity_remark = received_quantity_remark;
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

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(Double requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	

	public short getApprovalPartStatusId() {
		return approvalPartStatusId;
	}

	public void setApprovalPartStatusId(short approvalPartStatusId) {
		this.approvalPartStatusId = approvalPartStatusId;
	}

	@Override
	public String toString() {
		return "PurchaseOrdersToUrea [purchaseOrdersToUreaId=" + purchaseOrdersToUreaId + ", ureaManufacturerId="
				+ ureaManufacturerId + ", quantity=" + quantity + ", notRecivedQuantity=" + notRecivedQuantity
				+ ", recivedQuantity=" + recivedQuantity + ", unitCost=" + unitCost + ", discount=" + discount
				+ ", tax=" + tax + ", totalcost=" + totalcost + ", received_quantity_remark=" + received_quantity_remark
				+ ", companyId=" + companyId + ", markForDelete=" + markForDelete + ", purchaseOrderId="
				+ purchaseOrderId + "]";
	}

	


	

	}