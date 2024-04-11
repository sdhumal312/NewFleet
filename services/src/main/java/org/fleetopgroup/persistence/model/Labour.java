package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Labour")
public class Labour implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "labourId")
		private Integer labourId;

		@Column(name = "labourName", nullable = false, length = 50)
		private String labourName;
		
		@Column(name = "description", length = 150)
		private String description;
		
		@Column(name = "companyId", nullable = false)
		private Integer companyId;
		
		@Column(name = "createdById", nullable = false)
		private Long createdById;
		
		@Column(name = "createdOn", nullable = true)
		private Timestamp createdOn;
		
		@Column(name = "lastModifiedById", nullable = true)
		private Long lastModifiedById;

		@Column(name = "lastModifiedOn", nullable = true)
		private Timestamp lastModifiedOn;
		
		@Column(name = "markForDelete", nullable = false)
		private boolean markForDelete;

		public Labour() {
			super();
		}

		public Labour(Integer labourId, String labourName, String description, Integer companyId, Long createdById,
				Timestamp createdOn, Long lastModifiedById, Timestamp lastModifiedOn, boolean markForDelete) {
			super();
			this.labourId = labourId;
			this.labourName = labourName;
			this.description = description;
			this.companyId = companyId;
			this.createdById = createdById;
			this.createdOn = createdOn;
			this.lastModifiedById = lastModifiedById;
			this.lastModifiedOn = lastModifiedOn;
			this.markForDelete = markForDelete;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
			result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
			result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
			result = prime * result + ((description == null) ? 0 : description.hashCode());
			result = prime * result + ((labourId == null) ? 0 : labourId.hashCode());
			result = prime * result + ((labourName == null) ? 0 : labourName.hashCode());
			result = prime * result + ((lastModifiedById == null) ? 0 : lastModifiedById.hashCode());
			result = prime * result + ((lastModifiedOn == null) ? 0 : lastModifiedOn.hashCode());
			result = prime * result + (markForDelete ? 1231 : 1237);
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
			Labour other = (Labour) obj;
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
			if (createdOn == null) {
				if (other.createdOn != null)
					return false;
			} else if (!createdOn.equals(other.createdOn))
				return false;
			if (description == null) {
				if (other.description != null)
					return false;
			} else if (!description.equals(other.description))
				return false;
			if (labourId == null) {
				if (other.labourId != null)
					return false;
			} else if (!labourId.equals(other.labourId))
				return false;
			if (labourName == null) {
				if (other.labourName != null)
					return false;
			} else if (!labourName.equals(other.labourName))
				return false;
			if (lastModifiedById == null) {
				if (other.lastModifiedById != null)
					return false;
			} else if (!lastModifiedById.equals(other.lastModifiedById))
				return false;
			if (lastModifiedOn == null) {
				if (other.lastModifiedOn != null)
					return false;
			} else if (!lastModifiedOn.equals(other.lastModifiedOn))
				return false;
			if (markForDelete != other.markForDelete)
				return false;
			return true;
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

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Integer getCompanyId() {
			return companyId;
		}

		public void setCompanyId(Integer companyId) {
			this.companyId = companyId;
		}

		public Long getCreatedById() {
			return createdById;
		}

		public void setCreatedById(Long createdById) {
			this.createdById = createdById;
		}

		public Timestamp getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(Timestamp createdOn) {
			this.createdOn = createdOn;
		}

		public Long getLastModifiedById() {
			return lastModifiedById;
		}

		public void setLastModifiedById(Long lastModifiedById) {
			this.lastModifiedById = lastModifiedById;
		}

		public Timestamp getLastModifiedOn() {
			return lastModifiedOn;
		}

		public void setLastModifiedOn(Timestamp lastModifiedOn) {
			this.lastModifiedOn = lastModifiedOn;
		}

		public boolean isMarkForDelete() {
			return markForDelete;
		}

		public void setMarkForDelete(boolean markForDelete) {
			this.markForDelete = markForDelete;
		}

		@Override
		public String toString() {
			return "Labour [labourId=" + labourId + ", labourName=" + labourName + ", description=" + description
					+ ", companyId=" + companyId + ", createdById=" + createdById + ", createdOn=" + createdOn
					+ ", lastModifiedById=" + lastModifiedById + ", lastModifiedOn=" + lastModifiedOn
					+ ", markForDelete=" + markForDelete + "]";
		}
		
		
		
		
		
		
}
