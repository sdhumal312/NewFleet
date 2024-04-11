/**
 * 
 */
package org.fleetopgroup.persistence.model;

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

/**
 * @author fleetop
 *
 */
@Entity
@Table(name = "MessageAttachFile")
public class MessageAttachFile {

	/** The system unique key */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ATTACH_ID", nullable = true)
	private long id;

	@Basic(optional = false)
	@Column(name = "ATTACH_FILENAME", nullable = false, length = 200)
	private String attachfilename;

	@Lob
	@Column(name = "ATTACH_CONTENT", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] attach_content;

	@Column(name = "ATTACH_CONTENT_TYPE", nullable = false, length = 200)
	private String attach_contentType;

	@Basic(optional = false)
	@Column(name = "PROPERTY_ID", nullable = true)
	private long propertyId;

	@Basic(optional = false)
	@Column(name = "MAILBOX_ID", nullable = true)
	private long mailboxId;

	public MessageAttachFile() {
		super();
	}

	public MessageAttachFile(long id, String attachfilename, byte[] attach_content, String attach_contentType,
			long propertyId, long mailboxId) {
		super();
		this.id = id;
		this.attachfilename = attachfilename;
		this.attach_content = attach_content;
		this.attach_contentType = attach_contentType;
		this.propertyId = propertyId;
		this.mailboxId = mailboxId;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the attachfilename
	 */
	public String getAttachfilename() {
		return attachfilename;
	}

	/**
	 * @param attachfilename
	 *            the attachfilename to set
	 */
	public void setAttachfilename(String attachfilename) {
		this.attachfilename = attachfilename;
	}

	/**
	 * @return the attach_content
	 */
	public byte[] getAttach_content() {
		return attach_content;
	}

	/**
	 * @param attach_content
	 *            the attach_content to set
	 */
	public void setAttach_content(byte[] attach_content) {
		this.attach_content = attach_content;
	}

	/**
	 * @return the attach_contentType
	 */
	public String getAttach_contentType() {
		return attach_contentType;
	}

	/**
	 * @param attach_contentType
	 *            the attach_contentType to set
	 */
	public void setAttach_contentType(String attach_contentType) {
		this.attach_contentType = attach_contentType;
	}

	/**
	 * @return the propertyId
	 */
	public long getPropertyId() {
		return propertyId;
	}

	/**
	 * @param propertyId
	 *            the propertyId to set
	 */
	public void setPropertyId(long propertyId) {
		this.propertyId = propertyId;
	}

	/**
	 * @return the mailboxId
	 */
	public long getMailboxId() {
		return mailboxId;
	}

	/**
	 * @param mailboxId
	 *            the mailboxId to set
	 */
	public void setMailboxId(long mailboxId) {
		this.mailboxId = mailboxId;
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
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (mailboxId ^ (mailboxId >>> 32));
		result = prime * result + (int) (propertyId ^ (propertyId >>> 32));
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
		MessageAttachFile other = (MessageAttachFile) obj;
		if (id != other.id)
			return false;
		if (mailboxId != other.mailboxId)
			return false;
		if (propertyId != other.propertyId)
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
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("MessageAttachFile [id=");
		builder.append(id);
		builder.append(", attachfilename=");
		builder.append(attachfilename);
		builder.append(", attach_content=");
		builder.append(attach_content != null
				? Arrays.toString(Arrays.copyOf(attach_content, Math.min(attach_content.length, maxLen))) : null);
		builder.append(", attach_contentType=");
		builder.append(attach_contentType);
		builder.append(", propertyId=");
		builder.append(propertyId);
		builder.append(", mailboxId=");
		builder.append(mailboxId);
		builder.append("]");
		return builder.toString();
	}

}
