package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
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

@Entity
@Table(name = "vehiclepurchase")

public class VehiclePurchase implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	/*@Column(name = "name")
	private String name;
	*/
	@Column(name = "vPurchaseTypeId")
	private Long vPurchaseTypeId;
	
	/*@Column(name = "uploaddate")
	private String uploaddate;
	
	@Column(name = "revdate")
	private String revdate;*/
	
	private Date uploaddateOn;
	
	@Column(name = "filename")
	private String filename;
	@Column(name = "vehid")
	private Integer vehid;
	
	@Lob
	@Column(name = "content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;
	
	@Column(name = "content_type")
	private String contentType;

	/*@Column(name = "startTime", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;*/
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	private Long  createdById;
	
	
	@Column(name = "markForDelete" , nullable = false)
	private boolean markForDelete;

	public VehiclePurchase() {
		super();
	}

	public VehiclePurchase(Long vPurchaseTypeId, Date uploaddate, String filename, Integer vehid, 
			String contentType, Integer companyId) {
		super();
		this.vPurchaseTypeId = vPurchaseTypeId;
		this.uploaddateOn = uploaddate;
		this.filename = filename;
		this.vehid = vehid;
		this.contentType = contentType;
		this.companyId = companyId;
	}

	public Integer getId() {
		return id;
	}

	/*public String getName() {
		return name;
	}*/

	public String getFilename() {
		return filename;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*public void setName(String name) {
		this.name = name;
	}*/

	public void setFilename(String filename) {
		this.filename = filename;
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

	/*public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getUploaddate() {
		return uploaddate;
	}

	public void setUploaddate(String uploaddate) {
		this.uploaddate = uploaddate;
	}

	public String getRevdate() {
		return revdate;
	}

	public void setRevdate(String revdate) {
		this.revdate = revdate;
	}
*/
	/**
	 * @return the vehid
	 */
	public Integer getVehid() {
		return vehid;
	}

	/**
	 * @param vehid
	 *            the vehid to set
	 */
	public void setVehid(Integer vehid) {
		this.vehid = vehid;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the vPurchaseTypeId
	 */
	public Long getvPurchaseTypeId() {
		return vPurchaseTypeId;
	}

	/**
	 * @param vPurchaseTypeId the vPurchaseTypeId to set
	 */
	public void setvPurchaseTypeId(Long vPurchaseTypeId) {
		this.vPurchaseTypeId = vPurchaseTypeId;
	}

	/**
	 * @return the uploaddateOn
	 */
	public Date getUploaddateOn() {
		return uploaddateOn;
	}

	/**
	 * @param uploaddateOn the uploaddateOn to set
	 */
	public void setUploaddateOn(Date uploaddateOn) {
		this.uploaddateOn = uploaddateOn;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vPurchaseTypeId == null) ? 0 : vPurchaseTypeId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehiclePurchase other = (VehiclePurchase) obj;
		if (vPurchaseTypeId == null) {
			if (other.vPurchaseTypeId != null)
				return false;
		} else if (!vPurchaseTypeId.equals(other.vPurchaseTypeId))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehiclePurchase [vPurchaseTypeId=");
		builder.append(vPurchaseTypeId);
		builder.append(", uploaddateOn=");
		builder.append(uploaddateOn);
		/*builder.append(", revdate=");
		builder.append(revdate);*/
		builder.append(", filename=");
		builder.append(filename);
		builder.append(", vehid=");
		builder.append(vehid);
		builder.append(", content=");
		builder.append(content);
		builder.append(", contentType=");
		builder.append(contentType);
		/*builder.append(", startTime=");
		builder.append(startTime);*/
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

}