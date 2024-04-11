package org.fleetopgroup.persistence.document;

/**
 * @author Ashish Tiwari
 *
 *
 *
 */
import java.io.Serializable;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Companylogo")

public class Companylogo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Integer _id;
	private String  filename;
	private Integer company_id;
	private byte[]  log_content;
	private String  log_contentType;
	
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public byte[] getLog_content() {
		return log_content;
	}
	public void setLog_content(byte[] log_content) {
		this.log_content = log_content;
	}
	public String getLog_contentType() {
		return log_contentType;
	}
	public void setLog_contentType(String log_contentType) {
		this.log_contentType = log_contentType;
	}
}