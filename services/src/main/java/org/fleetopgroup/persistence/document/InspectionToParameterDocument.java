package org.fleetopgroup.persistence.document;

import java.util.Arrays;

import javax.persistence.Id;

public class InspectionToParameterDocument {

	@Id
	private Long 	_id;
	private String 	name;
	private byte[] 	content;
	private String 	contentType;
	private Long	completionToParameterId;
	private Integer	companyId;
	private boolean markForDelete;
	
	
	
	public Long get_id() {
		return _id;
	}
	public String getName() {
		return name;
	}
	public byte[] getContent() {
		return content;
	}
	public String getContentType() {
		return contentType;
	}
	public Long getCompletionToParameterId() {
		return completionToParameterId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public boolean isMarkForDelete() {
		return markForDelete;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public void setCompletionToParameterId(Long completionToParameterId) {
		this.completionToParameterId = completionToParameterId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	@Override
	public String toString() {
		return "InspectionToParameterDocument [_id=" + _id + ", name=" + name + ", content=" + Arrays.toString(content)
				+ ", contentType=" + contentType + ", completionToParameterId=" + completionToParameterId
				+ ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	
	
}
