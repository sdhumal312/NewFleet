package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class ToolBoxDetailsDto 
{

	private Long	ToolBoxDetailsId;
	
	private Integer vid;
	
	private Long	toolBoxId;
	
	private Double	quantity;
	
	private String	description;
	
	private Date	creationDate;
	
	private Date	lastUpdatedOn;
	
	private Long	createdById;
	
	private Long	lastUpdatedBy;
	
	private Integer	companyId;
	
	private boolean markForDelete;
	
	private String toolBoxName;
	

	public Long getToolBoxDetailsId() {
		return ToolBoxDetailsId;
	}

	public void setToolBoxDetailsId(Long toolBoxDetailsId) {
		ToolBoxDetailsId = toolBoxDetailsId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getToolBoxId() {
		return toolBoxId;
	}

	public void setToolBoxId(Long toolBoxId) {
		this.toolBoxId = toolBoxId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = Utility.round( quantity, 2);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getToolBoxName() {
		return toolBoxName;
	}

	public void setToolBoxName(String toolBoxName) {
		this.toolBoxName = toolBoxName;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public ToolBoxDetailsDto() 
	{
		super();
		
	}

	public ToolBoxDetailsDto(Long toolBoxDetailsId, Integer vid, Long toolBoxId, Double quantity, String description,
			Date creationDate, Date lastUpdatedOn, Long createdById, Long lastUpdatedBy, Integer companyId,
			boolean markForDelete) {
		super();
		ToolBoxDetailsId = toolBoxDetailsId;
		this.vid = vid;
		this.toolBoxId = toolBoxId;
		this.quantity = quantity;
		this.description = description;
		this.creationDate = creationDate;
		this.lastUpdatedOn = lastUpdatedOn;
		this.createdById = createdById;
		this.lastUpdatedBy = lastUpdatedBy;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ToolBoxDetailsId == null) ? 0 : ToolBoxDetailsId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((toolBoxId == null) ? 0 : toolBoxId.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
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
		ToolBoxDetailsDto other = (ToolBoxDetailsDto) obj;
		if (ToolBoxDetailsId == null) {
			if (other.ToolBoxDetailsId != null)
				return false;
		} else if (!ToolBoxDetailsId.equals(other.ToolBoxDetailsId))
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
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (lastUpdatedBy == null) {
			if (other.lastUpdatedBy != null)
				return false;
		} else if (!lastUpdatedBy.equals(other.lastUpdatedBy))
			return false;
		if (lastUpdatedOn == null) {
			if (other.lastUpdatedOn != null)
				return false;
		} else if (!lastUpdatedOn.equals(other.lastUpdatedOn))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (toolBoxId == null) {
			if (other.toolBoxId != null)
				return false;
		} else if (!toolBoxId.equals(other.toolBoxId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ToolBoxDetailsDto [ToolBoxDetailsId=" + ToolBoxDetailsId + ", vid=" + vid + ", toolBoxId=" + toolBoxId
				+ ", quantity=" + quantity + ", description=" + description + ", creationDate=" + creationDate
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", createdById=" + createdById + ", lastUpdatedBy="
				+ lastUpdatedBy + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	
	
	
	
	
	
}
