package org.fleetopgroup.persistence.document;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "RenewalReminderDocument")
public class RenewalReminderDocument {

	@Id
	private Long _id;

	@Indexed
	private Long renewal_id;
	

	private Long renewal_R_Number;


	private Long renewalHis_id;


	private String renewal_filename;

	
	private byte[] renewal_content;


	private String renewal_contentType;
	

	private Integer companyId;

	
	
	private Long createdById;
	
	
	private Long lastModifiedById;

	
	private Date created;

	
	private Date lastupdated;


	private boolean markForDelete;



	public Long get_id() {
		return _id;
	}


	public void set_id(Long _id) {
		this._id = _id;
	}


	/**
	 * @return the renewal_id
	 */
	public Long getRenewal_id() {
		return renewal_id;
	}


	/**
	 * @param renewal_id the renewal_id to set
	 */
	public void setRenewal_id(Long renewal_id) {
		this.renewal_id = renewal_id;
	}


	/**
	 * @return the renewal_R_Number
	 */
	public Long getRenewal_R_Number() {
		return renewal_R_Number;
	}


	/**
	 * @param renewal_R_Number the renewal_R_Number to set
	 */
	public void setRenewal_R_Number(Long renewal_R_Number) {
		this.renewal_R_Number = renewal_R_Number;
	}


	/**
	 * @return the renewalHis_id
	 */
	public Long getRenewalHis_id() {
		return renewalHis_id;
	}


	/**
	 * @param renewalHis_id the renewalHis_id to set
	 */
	public void setRenewalHis_id(Long renewalHis_id) {
		this.renewalHis_id = renewalHis_id;
	}


	/**
	 * @return the renewal_filename
	 */
	public String getRenewal_filename() {
		return renewal_filename;
	}


	/**
	 * @param renewal_filename the renewal_filename to set
	 */
	public void setRenewal_filename(String renewal_filename) {
		this.renewal_filename = renewal_filename;
	}


	/**
	 * @return the renewal_content
	 */
	public byte[] getRenewal_content() {
		return renewal_content;
	}


	/**
	 * @param renewal_content the renewal_content to set
	 */
	public void setRenewal_content(byte[] renewal_content) {
		this.renewal_content = renewal_content;
	}


	/**
	 * @return the renewal_contentType
	 */
	public String getRenewal_contentType() {
		return renewal_contentType;
	}


	/**
	 * @param renewal_contentType the renewal_contentType to set
	 */
	public void setRenewal_contentType(String renewal_contentType) {
		this.renewal_contentType = renewal_contentType;
	}


	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}


	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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


	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}


	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}


	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}


	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}


	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}


	/**
	 * @param lastupdated the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}


	/**
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}


	/**
	 * @param markForDelete the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	
}
