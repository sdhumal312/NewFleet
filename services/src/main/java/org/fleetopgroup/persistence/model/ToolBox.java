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
@Table(name ="ToolBox")
public class ToolBox implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "toolBoxId")
	private Long	toolBoxId;
	
	@Column(name = "toolBoxName")
	private String	toolBoxName;
	
	@Column(name = "description")
	private String	description;
	
	@Column(name = "creationDate")
	private Date	creationDate;
	
	@Column(name = "lastUpdatedOn")
	private Date	lastUpdatedOn;
	
	@Column(name = "createdById")
	private Long	createdById;
	
	@Column(name = "lastUpdatedBy")
	private Long	lastUpdatedBy;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	public ToolBox() {
		super();
	}

	public ToolBox(Long toolBoxId, String toolBoxName, String description, Date creationDate, Date lastUpdatedOn,
			Long createdById, Long lastUpdatedBy, Integer companyId, boolean markForDelete) {
		super();
		this.toolBoxId = toolBoxId;
		this.toolBoxName = toolBoxName;
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
		result = prime * result + ((toolBoxId == null) ? 0 : toolBoxId.hashCode());
		result = prime * result + ((toolBoxName == null) ? 0 : toolBoxName.hashCode());
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
		ToolBox other = (ToolBox) obj;
		if (toolBoxId == null) {
			if (other.toolBoxId != null)
				return false;
		} else if (!toolBoxId.equals(other.toolBoxId))
			return false;
		if (toolBoxName == null) {
			if (other.toolBoxName != null)
				return false;
		} else if (!toolBoxName.equals(other.toolBoxName))
			return false;
		return true;
	}

	public Long getToolBoxId() {
		return toolBoxId;
	}

	public void setToolBoxId(Long toolBoxId) {
		this.toolBoxId = toolBoxId;
	}

	public String getToolBoxName() {
		return toolBoxName;
	}

	public void setToolBoxName(String toolBoxName) {
		this.toolBoxName = toolBoxName;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "ToolBox [toolBoxId=" + toolBoxId + ", toolBoxName=" + toolBoxName + ", description=" + description
				+ ", creationDate=" + creationDate + ", lastUpdatedOn=" + lastUpdatedOn + ", createdById=" + createdById
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}
	
	
	
}
