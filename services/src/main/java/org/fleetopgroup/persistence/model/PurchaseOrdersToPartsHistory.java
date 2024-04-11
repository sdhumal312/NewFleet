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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PurchaseOrdersToPartsHistory")
public class PurchaseOrdersToPartsHistory implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchaseorderto_partid")
	private Long purchaseorderto_partid;

	@Column(name = "partid")
	private Long partid;

	@Column(name = "quantity", length = 10)
	private Double quantity;

	@Column(name = "received_quantity", length = 10)
	private Double received_quantity;

	@Column(name = "received_quantity_remark", length = 200)
	private String received_quantity_remark;

	@Column(name = "notreceived_quantity", length = 10)
	private Double notreceived_quantity;

	@Column(name = "companyId")
	private Integer companyId;

	@Column(name = "markForDelete")
	private boolean markForDelete;
	
	@Column(name = "approvalPartStatusId")
	private short approvalPartStatusId;

	@ManyToOne
	@JoinColumn(name = "purchaseorder_id")
	private PurchaseOrders purchaseorders;
	
	@Column(name = "requestedQuantity", length = 10)
	private Double requestedQuantity;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "vendorId")
	private Integer vendorId;

	public Long getPurchaseorderto_partid() {
		return purchaseorderto_partid;
	}

	public void setPurchaseorderto_partid(Long purchaseorderto_partid) {
		this.purchaseorderto_partid = purchaseorderto_partid;
	}

	public Long getPartid() {
		return partid;
	}

	public void setPartid(Long partid) {
		this.partid = partid;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getReceived_quantity() {
		return received_quantity;
	}

	public void setReceived_quantity(Double received_quantity) {
		this.received_quantity = received_quantity;
	}

	public String getReceived_quantity_remark() {
		return received_quantity_remark;
	}

	public void setReceived_quantity_remark(String received_quantity_remark) {
		this.received_quantity_remark = received_quantity_remark;
	}

	public Double getNotreceived_quantity() {
		return notreceived_quantity;
	}

	public void setNotreceived_quantity(Double notreceived_quantity) {
		this.notreceived_quantity = notreceived_quantity;
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

	public short getApprovalPartStatusId() {
		return approvalPartStatusId;
	}

	public void setApprovalPartStatusId(short approvalPartStatusId) {
		this.approvalPartStatusId = approvalPartStatusId;
	}

	public PurchaseOrders getPurchaseorders() {
		return purchaseorders;
	}

	public void setPurchaseorders(PurchaseOrders purchaseorders) {
		this.purchaseorders = purchaseorders;
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

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PurchaseOrdersToPartsHistory [purchaseorderto_partid=");
		builder.append(purchaseorderto_partid);
		builder.append(", partid=");
		builder.append(partid);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", received_quantity=");
		builder.append(received_quantity);
		builder.append(", received_quantity_remark=");
		builder.append(received_quantity_remark);
		builder.append(", notreceived_quantity=");
		builder.append(notreceived_quantity);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", approvalPartStatusId=");
		builder.append(approvalPartStatusId);
		builder.append(", purchaseorders=");
		builder.append(purchaseorders);
		builder.append(", requestedQuantity=");
		builder.append(requestedQuantity);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", vendorId=");
		builder.append(vendorId);
		builder.append("]");
		return builder.toString();
	}
	
}

	