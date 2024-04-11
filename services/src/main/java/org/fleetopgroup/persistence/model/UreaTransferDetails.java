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
@Table(name = "UreaTransferDetails")
public class UreaTransferDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ureaTransferDetailsId")
	private Long ureaTransferDetailsId;
	
	@Column(name = "ureaTransferId")
	private Long ureaTransferId;
	
	@Column(name = "ureaTransferFromLocationId")
	private Integer ureaTransferFromLocationId;
	
	@Column(name = "ureaInvoiceToDetailsId")
	private Long ureaInvoiceToDetailsId;
	
	@Column(name = "ureaStockQuantity")
	private Double ureaStockQuantity;
	
	@Column(name = "ureaInventoryTransferQuantity")
	private Double ureaInventoryTransferQuantity;

	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastUpdatedById")
	private Long lastUpdatedById;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "lastUpdatedDate")
	private Date lastUpdatedDate;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	//for centrelised transfer
	@Column(name = "approvalId")
	private Long approvalId;

	public UreaTransferDetails() {
		super();
		
	}

	public UreaTransferDetails(Long ureaTransferDetailsId, Integer ureaTransferFromLocationId,
			Long ureaTransferId, Long ureaInvoiceToDetailsId, Double ureaStockQuantity,
			Double ureaInventoryTransferQuantity, Long createdById, Long lastUpdatedById, Date creationDate,
			Date lastUpdatedDate, Integer companyId, boolean markForDelete) {
		super();
		this.ureaTransferDetailsId = ureaTransferDetailsId;
		this.ureaTransferFromLocationId = ureaTransferFromLocationId;
		this.ureaTransferId = ureaTransferId;
		this.ureaInvoiceToDetailsId = ureaInvoiceToDetailsId;
		this.ureaStockQuantity = ureaStockQuantity;
		this.ureaInventoryTransferQuantity = ureaInventoryTransferQuantity;
		this.createdById = createdById;
		this.lastUpdatedById = lastUpdatedById;
		this.creationDate = creationDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((lastUpdatedById == null) ? 0 : lastUpdatedById.hashCode());
		result = prime * result + ((lastUpdatedDate == null) ? 0 : lastUpdatedDate.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result
				+ ((ureaInventoryTransferQuantity == null) ? 0 : ureaInventoryTransferQuantity.hashCode());
		result = prime * result + ((ureaInvoiceToDetailsId == null) ? 0 : ureaInvoiceToDetailsId.hashCode());
		result = prime * result + ((ureaStockQuantity == null) ? 0 : ureaStockQuantity.hashCode());
		result = prime * result + ((ureaTransferDetailsId == null) ? 0 : ureaTransferDetailsId.hashCode());
		result = prime * result + ((ureaTransferFromLocationId == null) ? 0 : ureaTransferFromLocationId.hashCode());
		result = prime * result + ((ureaTransferId == null) ? 0 : ureaTransferId.hashCode());
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
		UreaTransferDetails other = (UreaTransferDetails) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (createdById == null) {
			if (other.createdById != null)
				return false;
		} else if (!createdById.equals(other.createdById))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (lastUpdatedById == null) {
			if (other.lastUpdatedById != null)
				return false;
		} else if (!lastUpdatedById.equals(other.lastUpdatedById))
			return false;
		if (lastUpdatedDate == null) {
			if (other.lastUpdatedDate != null)
				return false;
		} else if (!lastUpdatedDate.equals(other.lastUpdatedDate))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (ureaInventoryTransferQuantity == null) {
			if (other.ureaInventoryTransferQuantity != null)
				return false;
		} else if (!ureaInventoryTransferQuantity.equals(other.ureaInventoryTransferQuantity))
			return false;
		if (ureaInvoiceToDetailsId == null) {
			if (other.ureaInvoiceToDetailsId != null)
				return false;
		} else if (!ureaInvoiceToDetailsId.equals(other.ureaInvoiceToDetailsId))
			return false;
		if (ureaStockQuantity == null) {
			if (other.ureaStockQuantity != null)
				return false;
		} else if (!ureaStockQuantity.equals(other.ureaStockQuantity))
			return false;
		if (ureaTransferDetailsId == null) {
			if (other.ureaTransferDetailsId != null)
				return false;
		} else if (!ureaTransferDetailsId.equals(other.ureaTransferDetailsId))
			return false;
		if (ureaTransferFromLocationId == null) {
			if (other.ureaTransferFromLocationId != null)
				return false;
		} else if (!ureaTransferFromLocationId.equals(other.ureaTransferFromLocationId))
			return false;
		if (ureaTransferId == null) {
			if (other.ureaTransferId != null)
				return false;
		} else if (!ureaTransferId.equals(other.ureaTransferId))
			return false;
		return true;
	}

	public Long getUreaTransferDetailsId() {
		return ureaTransferDetailsId;
	}

	public void setUreaTransferDetailsId(Long ureaTranferDetailsId) {
		this.ureaTransferDetailsId = ureaTranferDetailsId;
	}

	public Integer getUreaTransferFromLocationId() {
		return ureaTransferFromLocationId;
	}

	public void setUreaTransferFromLocationId(Integer ureaTransferFromLocationId) {
		this.ureaTransferFromLocationId = ureaTransferFromLocationId;
	}

	public Long getUreaTransferId() {
		return ureaTransferId;
	}

	public void setUreaTransferId(Long ureaTransferId) {
		this.ureaTransferId = ureaTransferId;
	}

	public Long getUreaInvoiceToDetailsId() {
		return ureaInvoiceToDetailsId;
	}

	public void setUreaInvoiceToDetailsId(Long ureaInvoiceToDetailsId) {
		this.ureaInvoiceToDetailsId = ureaInvoiceToDetailsId;
	}

	public Double getUreaStockQuantity() {
		return ureaStockQuantity;
	}

	public void setUreaStockQuantity(Double ureaStockQuantity) {
		this.ureaStockQuantity = ureaStockQuantity;
	}

	public Double getUreaInventoryTransferQuantity() {
		return ureaInventoryTransferQuantity;
	}

	public void setUreaInventoryTransferQuantity(Double ureaInventoryTransferQuantity) {
		this.ureaInventoryTransferQuantity = ureaInventoryTransferQuantity;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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

	public Long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	@Override
	public String toString() {
		return "UreaTransferDetails [ureaTransferDetailsId=" + ureaTransferDetailsId + ", ureaTransferFromLocationId=" + ureaTransferFromLocationId
				+ ", ureaTransferId=" + ureaTransferId + ", ureaInvoiceToDetailsId=" + ureaInvoiceToDetailsId
				+ ", ureaStockQuantity=" + ureaStockQuantity + ", ureaInventoryTransferQuantity="
				+ ureaInventoryTransferQuantity + ", createdById=" + createdById + ", lastUpdatedById="
				+ lastUpdatedById + ", creationDate=" + creationDate + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}

	


}