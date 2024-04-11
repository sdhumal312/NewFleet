package org.fleetopgroup.persistence.document;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "FuelInvoiceDocument")
public class FuelInvoiceDocument implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		private Long	 _id;
		
		@Indexed
		private Long 	fuelInvoiceId;
		
		private byte[]  content;
		private String  contentType;
		private String  filename;
		private Integer companyId;
		private Long 	createdById;
		private Long 	lastModifiedById;
		private boolean markForDelete;
		private Date 	created;
		private Date 	lastupdated;
		
		
		public Long get_id() {
			return _id;
		}
		public void set_id(Long _id) {
			this._id = _id;
		}
		public Long getFuelInvoiceId() {
			return fuelInvoiceId;
		}
		public void setFuelInvoiceId(Long fuelInvoiceId) {
			this.fuelInvoiceId = fuelInvoiceId;
		}
		public byte[] getContent() {
			return content;
		}
		public void setContent(byte[] content) {
			this.content = content;
		}
		public String getContentType() {
			return contentType;
		}
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		public String getFilename() {
			return filename;
		}
		public void setFilename(String filename) {
			this.filename = filename;
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
		public Long getLastModifiedById() {
			return lastModifiedById;
		}
		public void setLastModifiedById(Long lastModifiedById) {
			this.lastModifiedById = lastModifiedById;
		}
		public boolean isMarkForDelete() {
			return markForDelete;
		}
		public void setMarkForDelete(boolean markForDelete) {
			this.markForDelete = markForDelete;
		}
		public Date getCreated() {
			return created;
		}
		public void setCreated(Date created) {
			this.created = created;
		}
		public Date getLastupdated() {
			return lastupdated;
		}
		public void setLastupdated(Date lastupdated) {
			this.lastupdated = lastupdated;
		}
		
		
}
