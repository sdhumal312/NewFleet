package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Arrays;

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
@Table(name = "companylogo")
public class Companylogo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id")
	private Integer log_id;

	@Column(name = "filename")
	private String filename;

	@Column(name = "company_id")
	private Integer company_id;

	@Lob
	@Column(name = "log_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] log_content;

	@Column(name = "log_content_type")
	private String log_contentType;

	public Companylogo() {
		super();
	}

	public Companylogo(String filename, Integer company_id, byte[] log_content, String log_contentType) {
		super();
		this.filename = filename;
		this.company_id = company_id;
		this.log_content = log_content;
		this.log_contentType = log_contentType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company_id == null) ? 0 : company_id.hashCode());
		result = prime * result + Arrays.hashCode(log_content);
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
		Companylogo other = (Companylogo) obj;
		if (company_id == null) {
			if (other.company_id != null)
				return false;
		} else if (!company_id.equals(other.company_id))
			return false;
		if (!Arrays.equals(log_content, other.log_content))
			return false;
		return true;
	}

	public Integer getLog_id() {
		return log_id;
	}

	public void setLog_id(Integer log_id) {
		this.log_id = log_id;
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

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("Companylogo [filename=");
		builder.append(filename);
		builder.append(", company_id=");
		builder.append(company_id);
		builder.append(", log_content=");
		builder.append(log_content != null
				? Arrays.toString(Arrays.copyOf(log_content, Math.min(log_content.length, maxLen))) : null);
		builder.append(", log_contentType=");
		builder.append(log_contentType);
		builder.append("]");
		return builder.toString();
	}

	

}