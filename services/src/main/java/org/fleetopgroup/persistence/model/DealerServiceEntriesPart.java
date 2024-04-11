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
@Table(name = "DealerServiceEntriesPart")
public class DealerServiceEntriesPart implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DealerServiceEntriesPartId")
	private Long DealerServiceEntriesPartId;
	
	@Column(name = "dealerServiceEntriesId")
	private Long dealerServiceEntriesId;
	
	@Column(name = "part_HSN_SAC_Code")
	private String part_HSN_SAC_Code;

	@Column(name = "partId")
	private Long partId;
	
	@Column(name = "partName")
	private String partName;
	
	@Column(name = "partNumber")
	private String partNumber;

	@Column(name = "quantity")
	private Double quantity;

	@Column(name = "partEchCost")
	private Double partEchCost;

	@Column(name = "partDiscount")
	private Double partDiscount;

	@Column(name = "partTax")
	private Double partTax;
	
	@Column(name = "partDiscountCost")
	private Double partDiscountCost;

	@Column(name = "partTaxCost")
	private Double partTaxCost;

	@Column(name = "partTotalCost")
	private Double partTotalCost;
	
	@Column(name = "createdById", nullable = false)
	private Long createdById;

	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "creationOn")
	private Date creationOn;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "lastOccurredDastealerServiceEntriesId")
	private Long lastOccurredDealerServiceEntriesId;
	
	@Column(name = "partDiscountTaxTypeId", nullable = false)
	private short partDiscountTaxTypeId;

	public DealerServiceEntriesPart() {
		super();
	}

	public DealerServiceEntriesPart(Long dealerServiceEntriesPartId, Long dealerServiceEntriesId,
			String part_HSN_SAC_Code, Long partId, String partName, String partNumber, Double quantity,
			Double partEchCost, Double partDiscount, Double partTax, Double partDiscountCost, Double partTaxCost,
			Double partTotalCost, Long createdById, Long lastModifiedById, Date creationOn, Date lastUpdatedOn,
			Integer companyId, boolean markForDelete, Long lastOccurredDealerServiceEntriesId, Date lastOccurredOn) {
		super();
		DealerServiceEntriesPartId = dealerServiceEntriesPartId;
		this.dealerServiceEntriesId = dealerServiceEntriesId;
		this.part_HSN_SAC_Code = part_HSN_SAC_Code;
		this.partId = partId;
		this.partName = partName;
		this.partNumber = partNumber;
		this.quantity = quantity;
		this.partEchCost = partEchCost;
		this.partDiscount = partDiscount;
		this.partTax = partTax;
		this.partDiscountCost = partDiscountCost;
		this.partTaxCost = partTaxCost;
		this.partTotalCost = partTotalCost;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.creationOn = creationOn;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
		this.lastOccurredDealerServiceEntriesId = lastOccurredDealerServiceEntriesId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DealerServiceEntriesPartId == null) ? 0 : DealerServiceEntriesPartId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationOn == null) ? 0 : creationOn.hashCode());
		result = prime * result + ((dealerServiceEntriesId == null) ? 0 : dealerServiceEntriesId.hashCode());
		result = prime * result + ((lastModifiedById == null) ? 0 : lastModifiedById.hashCode());
		result = prime * result
				+ ((lastOccurredDealerServiceEntriesId == null) ? 0 : lastOccurredDealerServiceEntriesId.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((partDiscount == null) ? 0 : partDiscount.hashCode());
		result = prime * result + ((partDiscountCost == null) ? 0 : partDiscountCost.hashCode());
		result = prime * result + ((partEchCost == null) ? 0 : partEchCost.hashCode());
		result = prime * result + ((partId == null) ? 0 : partId.hashCode());
		result = prime * result + ((partName == null) ? 0 : partName.hashCode());
		result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
		result = prime * result + ((partTax == null) ? 0 : partTax.hashCode());
		result = prime * result + ((partTaxCost == null) ? 0 : partTaxCost.hashCode());
		result = prime * result + ((partTotalCost == null) ? 0 : partTotalCost.hashCode());
		result = prime * result + ((part_HSN_SAC_Code == null) ? 0 : part_HSN_SAC_Code.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
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
		DealerServiceEntriesPart other = (DealerServiceEntriesPart) obj;
		if (DealerServiceEntriesPartId == null) {
			if (other.DealerServiceEntriesPartId != null)
				return false;
		} else if (!DealerServiceEntriesPartId.equals(other.DealerServiceEntriesPartId))
			return false;
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
		if (creationOn == null) {
			if (other.creationOn != null)
				return false;
		} else if (!creationOn.equals(other.creationOn))
			return false;
		if (dealerServiceEntriesId == null) {
			if (other.dealerServiceEntriesId != null)
				return false;
		} else if (!dealerServiceEntriesId.equals(other.dealerServiceEntriesId))
			return false;
		if (lastModifiedById == null) {
			if (other.lastModifiedById != null)
				return false;
		} else if (!lastModifiedById.equals(other.lastModifiedById))
			return false;
		if (lastOccurredDealerServiceEntriesId == null) {
			if (other.lastOccurredDealerServiceEntriesId != null)
				return false;
		} else if (!lastOccurredDealerServiceEntriesId.equals(other.lastOccurredDealerServiceEntriesId))
			return false;
		if (lastUpdatedOn == null) {
			if (other.lastUpdatedOn != null)
				return false;
		} else if (!lastUpdatedOn.equals(other.lastUpdatedOn))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (partDiscount == null) {
			if (other.partDiscount != null)
				return false;
		} else if (!partDiscount.equals(other.partDiscount))
			return false;
		if (partDiscountCost == null) {
			if (other.partDiscountCost != null)
				return false;
		} else if (!partDiscountCost.equals(other.partDiscountCost))
			return false;
		if (partEchCost == null) {
			if (other.partEchCost != null)
				return false;
		} else if (!partEchCost.equals(other.partEchCost))
			return false;
		if (partId == null) {
			if (other.partId != null)
				return false;
		} else if (!partId.equals(other.partId))
			return false;
		if (partName == null) {
			if (other.partName != null)
				return false;
		} else if (!partName.equals(other.partName))
			return false;
		if (partNumber == null) {
			if (other.partNumber != null)
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		if (partTax == null) {
			if (other.partTax != null)
				return false;
		} else if (!partTax.equals(other.partTax))
			return false;
		if (partTaxCost == null) {
			if (other.partTaxCost != null)
				return false;
		} else if (!partTaxCost.equals(other.partTaxCost))
			return false;
		if (partTotalCost == null) {
			if (other.partTotalCost != null)
				return false;
		} else if (!partTotalCost.equals(other.partTotalCost))
			return false;
		if (part_HSN_SAC_Code == null) {
			if (other.part_HSN_SAC_Code != null)
				return false;
		} else if (!part_HSN_SAC_Code.equals(other.part_HSN_SAC_Code))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}

	public Long getDealerServiceEntriesPartId() {
		return DealerServiceEntriesPartId;
	}

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public String getPart_HSN_SAC_Code() {
		return part_HSN_SAC_Code;
	}

	public Long getPartId() {
		return partId;
	}

	public String getPartName() {
		return partName;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public Double getQuantity() {
		return quantity;
	}

	public Double getPartEchCost() {
		return partEchCost;
	}

	public Double getPartDiscount() {
		return partDiscount;
	}

	public Double getPartTax() {
		return partTax;
	}

	public Double getPartDiscountCost() {
		return partDiscountCost;
	}

	public Double getPartTaxCost() {
		return partTaxCost;
	}

	public Double getPartTotalCost() {
		return partTotalCost;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public Long getLastOccurredDealerServiceEntriesId() {
		return lastOccurredDealerServiceEntriesId;
	}


	public void setDealerServiceEntriesPartId(Long dealerServiceEntriesPartId) {
		DealerServiceEntriesPartId = dealerServiceEntriesPartId;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}

	public void setPart_HSN_SAC_Code(String part_HSN_SAC_Code) {
		this.part_HSN_SAC_Code = part_HSN_SAC_Code;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public void setPartEchCost(Double partEchCost) {
		this.partEchCost = partEchCost;
	}

	public void setPartDiscount(Double partDiscount) {
		this.partDiscount = partDiscount;
	}

	public void setPartTax(Double partTax) {
		this.partTax = partTax;
	}

	public void setPartDiscountCost(Double partDiscountCost) {
		this.partDiscountCost = partDiscountCost;
	}

	public void setPartTaxCost(Double partTaxCost) {
		this.partTaxCost = partTaxCost;
	}

	public void setPartTotalCost(Double partTotalCost) {
		this.partTotalCost = partTotalCost;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public void setLastOccurredDealerServiceEntriesId(Long lastOccurredDealerServiceEntriesId) {
		this.lastOccurredDealerServiceEntriesId = lastOccurredDealerServiceEntriesId;
	}

	public short getPartDiscountTaxTypeId() {
		return partDiscountTaxTypeId;
	}

	public void setPartDiscountTaxTypeId(short partDiscountTaxTypeId) {
		this.partDiscountTaxTypeId = partDiscountTaxTypeId;
	}

	@Override
	public String toString() {
		return "DealerServiceEntriesPart [DealerServiceEntriesPartId=" + DealerServiceEntriesPartId
				+ ", dealerServiceEntriesId=" + dealerServiceEntriesId + ", part_HSN_SAC_Code=" + part_HSN_SAC_Code
				+ ", partId=" + partId + ", partName=" + partName + ", partNumber=" + partNumber + ", quantity="
				+ quantity + ", partEchCost=" + partEchCost + ", partDiscount=" + partDiscount + ", partTax=" + partTax
				+ ", partDiscountCost=" + partDiscountCost + ", partTaxCost=" + partTaxCost + ", partTotalCost="
				+ partTotalCost + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", creationOn=" + creationOn + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + ", lastOccurredDealerServiceEntriesId="
				+ lastOccurredDealerServiceEntriesId + "]";
	}

	
	


}
