package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "BatteryInvoiceDocument")
public class BatteryInvoiceDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batteryInvoiceDocId")
	private Long batteryInvoiceDocId;

	@Column(name = "batteryInvoiceId")
	private Long batteryInvoiceId;

	@Lob
	@Column(name = "batteryInvoiceContent", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] batteryInvoiceContent;

	@Column(name = "batteryInvoiceContentType")
	private String batteryInvoiceContentType;

	@Column(name = "batteryInvoiceFileName")
	private String batteryInvoiceFileName;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "createdById")
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Basic(optional = false)
	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;

	public BatteryInvoiceDocument() {
		super();
	}


	public BatteryInvoiceDocument(Long batteryInvoiceDocId, Long batteryInvoiceId, byte[] batteryInvoiceContent,
			String batteryInvoiceContentType, String batteryInvoiceFileName, Integer companyId, Long createdById,
			Long lastModifiedById, boolean markForDelete, Date created, Date lastupdated) {
		super();
		this.batteryInvoiceDocId = batteryInvoiceDocId;
		this.batteryInvoiceId = batteryInvoiceId;
		this.batteryInvoiceContent = batteryInvoiceContent;
		this.batteryInvoiceContentType = batteryInvoiceContentType;
		this.batteryInvoiceFileName = batteryInvoiceFileName;
		this.companyId = companyId;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.markForDelete = markForDelete;
		this.created = created;
		this.lastupdated = lastupdated;
	}



	public Long getBatteryInvoiceDocId() {
		return batteryInvoiceDocId;
	}


	public void setBatteryInvoiceDocId(Long batteryInvoiceDocId) {
		this.batteryInvoiceDocId = batteryInvoiceDocId;
	}


	public Long getBatteryInvoiceId() {
		return batteryInvoiceId;
	}


	public void setBatteryInvoiceId(Long batteryInvoiceId) {
		this.batteryInvoiceId = batteryInvoiceId;
	}


	public byte[] getBatteryInvoiceContent() {
		return batteryInvoiceContent;
	}


	public void setBatteryInvoiceContent(byte[] batteryInvoiceContent) {
		this.batteryInvoiceContent = batteryInvoiceContent;
	}


	public String getBatteryInvoiceContentType() {
		return batteryInvoiceContentType;
	}


	public void setBatteryInvoiceContentType(String batteryInvoiceContentType) {
		this.batteryInvoiceContentType = batteryInvoiceContentType;
	}


	public String getBatteryInvoiceFileName() {
		return batteryInvoiceFileName;
	}


	public void setBatteryInvoiceFileName(String batteryInvoiceFileName) {
		this.batteryInvoiceFileName = batteryInvoiceFileName;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(batteryInvoiceContent);
		result = prime * result + ((batteryInvoiceContentType == null) ? 0 : batteryInvoiceContentType.hashCode());
		result = prime * result + ((batteryInvoiceDocId == null) ? 0 : batteryInvoiceDocId.hashCode());
		result = prime * result + ((batteryInvoiceFileName == null) ? 0 : batteryInvoiceFileName.hashCode());
		result = prime * result + ((batteryInvoiceId == null) ? 0 : batteryInvoiceId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((lastModifiedById == null) ? 0 : lastModifiedById.hashCode());
		result = prime * result + ((lastupdated == null) ? 0 : lastupdated.hashCode());
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
		BatteryInvoiceDocument other = (BatteryInvoiceDocument) obj;
		if (!Arrays.equals(batteryInvoiceContent, other.batteryInvoiceContent))
			return false;
		if (batteryInvoiceContentType == null) {
			if (other.batteryInvoiceContentType != null)
				return false;
		} else if (!batteryInvoiceContentType.equals(other.batteryInvoiceContentType))
			return false;
		if (batteryInvoiceDocId == null) {
			if (other.batteryInvoiceDocId != null)
				return false;
		} else if (!batteryInvoiceDocId.equals(other.batteryInvoiceDocId))
			return false;
		if (batteryInvoiceFileName == null) {
			if (other.batteryInvoiceFileName != null)
				return false;
		} else if (!batteryInvoiceFileName.equals(other.batteryInvoiceFileName))
			return false;
		if (batteryInvoiceId == null) {
			if (other.batteryInvoiceId != null)
				return false;
		} else if (!batteryInvoiceId.equals(other.batteryInvoiceId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (createdById == null) {
			if (other.createdById != null)
				return false;
		} else if (!createdById.equals(other.createdById))
			return false;
		if (lastModifiedById == null) {
			if (other.lastModifiedById != null)
				return false;
		} else if (!lastModifiedById.equals(other.lastModifiedById))
			return false;
		if (lastupdated == null) {
			if (other.lastupdated != null)
				return false;
		} else if (!lastupdated.equals(other.lastupdated))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "BatteryInvoiceDocument [batteryInvoiceDocId=" + batteryInvoiceDocId + ", batteryInvoiceId="
				+ batteryInvoiceId + ", batteryInvoiceContent=" + Arrays.toString(batteryInvoiceContent)
				+ ", batteryInvoiceContentType=" + batteryInvoiceContentType + ", batteryInvoiceFileName="
				+ batteryInvoiceFileName + ", companyId=" + companyId + ", createdById=" + createdById
				+ ", lastModifiedById=" + lastModifiedById + ", markForDelete=" + markForDelete + ", created=" + created
				+ ", lastupdated=" + lastupdated + "]";
	}


	

}
