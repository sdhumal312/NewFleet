package org.fleetopgroup.persistence.document;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MasterDocuments")

public class MasterDocuments implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long 	_id;
	private String 	name;
	private byte[] 	content;
	private String 	contentType;
	private short   documentTypeId;
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public short getDocumentTypeId() {
		return documentTypeId;
	}
	public void setDocumentTypeId(short documentTypeId) {
		this.documentTypeId = documentTypeId;
	}
	@Override
	public String toString() {
		return "MasterDocuments [_id=" + _id + ", name=" + name + ", content=" + Arrays.toString(content)
				+ ", contentType=" + contentType + ", documentTypeId=" + documentTypeId + "]";
	}
}