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
@Table(name = "CommentType")
public class CommentType implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "commentTypeId")
		private Long commentTypeId;

		@Column(name = "commentTypeName", nullable = false, length = 50)
		private String commentTypeName;

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
		
		public Long getCommentTypeId() {
			return commentTypeId;
		}

		public void setCommentTypeId(Long commentTypeId) {
			this.commentTypeId = commentTypeId;
		}

		public String getCommentTypeName() {
			return commentTypeName;
		}

		public void setCommentTypeName(String commentTypeName) {
			this.commentTypeName = commentTypeName;
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
}
