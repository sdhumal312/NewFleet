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
@Table(name = "DealerServiceEntriesLabour")
public class DealerServiceEntriesLabour implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dealerServiceEntriesLabourId")
	private Long dealerServiceEntriesLabourId;
	
	@Column(name = "dealerServiceEntriesId")
	private Long dealerServiceEntriesId;

	@Column(name = "labourId")
	private Integer labourId;
	
	@Column(name = "labourName")
	private String labourName;
	
	@Column(name = "labour_HSN_SAC_Code")
	private String labour_HSN_SAC_Code;
	
	@Column(name = "labourWorkingHours")
	private Double labourWorkingHours;

	@Column(name = "labourPerHourCost")
	private Double labourPerHourCost;
	
	@Column(name = "labourDiscount")
	private Double labourDiscount;

	@Column(name = "labourTax")
	private Double labourTax;
	
	@Column(name = "labourDiscountCost")
	private Double labourDiscountCost;

	@Column(name = "labourTaxCost")
	private Double labourTaxCost;

	@Column(name = "totalCost")
	private Double totalCost;

	@Column(name = "description")
	private String	description;
	
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
	
	@Column(name = "labourDiscountTaxTypeId", nullable = false)
	private short labourDiscountTaxTypeId;

	public DealerServiceEntriesLabour() {
		super();
	}

	public DealerServiceEntriesLabour(Long dealerServiceEntriesLabourId, Long dealerServiceEntriesId, String labourName,
			String labour_HSN_SAC_Code, Double labourWorkingHours, Double labourPerHourCost, Double labourDiscount,
			Double labourTax, Double totalCost, String description, Long createdById, Long lastModifiedById,
			Date creationOn, Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		this.dealerServiceEntriesLabourId = dealerServiceEntriesLabourId;
		this.dealerServiceEntriesId = dealerServiceEntriesId;
		this.labourName = labourName;
		this.labour_HSN_SAC_Code = labour_HSN_SAC_Code;
		this.labourWorkingHours = labourWorkingHours;
		this.labourPerHourCost = labourPerHourCost;
		this.labourDiscount = labourDiscount;
		this.labourTax = labourTax;
		this.totalCost = totalCost;
		this.description = description;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.creationOn = creationOn;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationOn == null) ? 0 : creationOn.hashCode());
		result = prime * result + ((dealerServiceEntriesId == null) ? 0 : dealerServiceEntriesId.hashCode());
		result = prime * result
				+ ((dealerServiceEntriesLabourId == null) ? 0 : dealerServiceEntriesLabourId.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((labourDiscount == null) ? 0 : labourDiscount.hashCode());
		result = prime * result + ((labourName == null) ? 0 : labourName.hashCode());
		result = prime * result + ((labourPerHourCost == null) ? 0 : labourPerHourCost.hashCode());
		result = prime * result + ((labourTax == null) ? 0 : labourTax.hashCode());
		result = prime * result + ((labourWorkingHours == null) ? 0 : labourWorkingHours.hashCode());
		result = prime * result + ((labour_HSN_SAC_Code == null) ? 0 : labour_HSN_SAC_Code.hashCode());
		result = prime * result + ((lastModifiedById == null) ? 0 : lastModifiedById.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((totalCost == null) ? 0 : totalCost.hashCode());
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
		DealerServiceEntriesLabour other = (DealerServiceEntriesLabour) obj;
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
		if (dealerServiceEntriesLabourId == null) {
			if (other.dealerServiceEntriesLabourId != null)
				return false;
		} else if (!dealerServiceEntriesLabourId.equals(other.dealerServiceEntriesLabourId))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (labourDiscount == null) {
			if (other.labourDiscount != null)
				return false;
		} else if (!labourDiscount.equals(other.labourDiscount))
			return false;
		if (labourName == null) {
			if (other.labourName != null)
				return false;
		} else if (!labourName.equals(other.labourName))
			return false;
		if (labourPerHourCost == null) {
			if (other.labourPerHourCost != null)
				return false;
		} else if (!labourPerHourCost.equals(other.labourPerHourCost))
			return false;
		if (labourTax == null) {
			if (other.labourTax != null)
				return false;
		} else if (!labourTax.equals(other.labourTax))
			return false;
		if (labourWorkingHours == null) {
			if (other.labourWorkingHours != null)
				return false;
		} else if (!labourWorkingHours.equals(other.labourWorkingHours))
			return false;
		if (labour_HSN_SAC_Code == null) {
			if (other.labour_HSN_SAC_Code != null)
				return false;
		} else if (!labour_HSN_SAC_Code.equals(other.labour_HSN_SAC_Code))
			return false;
		if (lastModifiedById == null) {
			if (other.lastModifiedById != null)
				return false;
		} else if (!lastModifiedById.equals(other.lastModifiedById))
			return false;
		if (lastUpdatedOn == null) {
			if (other.lastUpdatedOn != null)
				return false;
		} else if (!lastUpdatedOn.equals(other.lastUpdatedOn))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (totalCost == null) {
			if (other.totalCost != null)
				return false;
		} else if (!totalCost.equals(other.totalCost))
			return false;
		return true;
	}

	public Long getDealerServiceEntriesLabourId() {
		return dealerServiceEntriesLabourId;
	}

	public void setDealerServiceEntriesLabourId(Long dealerServiceEntriesLabourId) {
		this.dealerServiceEntriesLabourId = dealerServiceEntriesLabourId;
	}

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}
	
	public Integer getLabourId() {
		return labourId;
	}

	public void setLabourId(Integer labourId) {
		this.labourId = labourId;
	}

	public String getLabourName() {
		return labourName;
	}

	public void setLabourName(String labourName) {
		this.labourName = labourName;
	}

	public String getLabour_HSN_SAC_Code() {
		return labour_HSN_SAC_Code;
	}

	public void setLabour_HSN_SAC_Code(String labour_HSN_SAC_Code) {
		this.labour_HSN_SAC_Code = labour_HSN_SAC_Code;
	}

	public Double getLabourWorkingHours() {
		return labourWorkingHours;
	}

	public void setLabourWorkingHours(Double labourWorkingHours) {
		this.labourWorkingHours = labourWorkingHours;
	}

	public Double getLabourPerHourCost() {
		return labourPerHourCost;
	}

	public void setLabourPerHourCost(Double labourPerHourCost) {
		this.labourPerHourCost = labourPerHourCost;
	}

	public Double getLabourDiscount() {
		return labourDiscount;
	}

	public void setLabourDiscount(Double labourDiscount) {
		this.labourDiscount = labourDiscount;
	}

	public Double getLabourTax() {
		return labourTax;
	}

	public void setLabourTax(Double labourTax) {
		this.labourTax = labourTax;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
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
	

	public Double getLabourDiscountCost() {
		return labourDiscountCost;
	}

	public Double getLabourTaxCost() {
		return labourTaxCost;
	}

	public void setLabourDiscountCost(Double labourDiscountCost) {
		this.labourDiscountCost = labourDiscountCost;
	}

	public void setLabourTaxCost(Double labourTaxCost) {
		this.labourTaxCost = labourTaxCost;
	}

	
	public short getLabourDiscountTaxTypeId() {
		return labourDiscountTaxTypeId;
	}

	public void setLabourDiscountTaxTypeId(short labourDiscountTaxTypeId) {
		this.labourDiscountTaxTypeId = labourDiscountTaxTypeId;
	}

	@Override
	public String toString() {
		return "DealerServiceEntriesLabour [dealerServiceEntriesLabourId=" + dealerServiceEntriesLabourId
				+ ", dealerServiceEntriesId=" + dealerServiceEntriesId + ", labourName=" + labourName
				+ ", labour_HSN_SAC_Code=" + labour_HSN_SAC_Code + ", labourWorkingHours=" + labourWorkingHours
				+ ", labourPerHourCost=" + labourPerHourCost + ", labourDiscount=" + labourDiscount + ", labourTax="
				+ labourTax + ", totalCost=" + totalCost + ", description=" + description + ", createdById="
				+ createdById + ", lastModifiedById=" + lastModifiedById + ", creationOn=" + creationOn
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}

	

}
