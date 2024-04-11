/**
 * 
 */
package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author fleetop
 *
 */
@Entity
@Table(name = "Mailbox")
public class Mailbox implements Serializable {
	private static final long serialVersionUID = 1L;

	// private static final String TAB = " ";

	/** The value for the mailboxId field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MAILBOX_ID")
	private long MAILBOX_ID;

	/** The value for the uidValidity field */
	@Basic(optional = false)
	@Column(name = "MAILBOX_FROM_USER_ID", nullable = false)
	private long MAILBOX_FROM_USER_ID;

	@Basic(optional = false)
	@Column(name = "MAILBOX_FROM_USER_NAME", nullable = false, length = 200)
	private String MAILBOX_FROM_USER_NAME;

	/** The value foe the user email id in here MAILBOX_NAME */
	@Basic(optional = false)
	@Column(name = "MAILBOX_FROM_EMAIL", nullable = false, length = 200)
	private String MAILBOX_FROM_EMAIL;

	/** The value for the name field subject */
	@Basic(optional = false)
	@Column(name = "MAILBOX_NAMESPACE", nullable = true, length = 200)
	private String MAILBOX_NAMESPACE;

	/** The value for the internalDate field */
	@Basic(optional = false)
	@Column(name = "MAIL_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date MAIL_DATE = new Date();

	/** The value for the answered field */
	@Basic(optional = false)
	@Column(name = "MAIL_IS_ANSWERED", nullable = false)
	private boolean MAIL_IS_ANSWERED = false;

	/** The value for the deleted field */
	@Basic(optional = false)
	@Column(name = "MAIL_IS_DELETED", nullable = false)
	private boolean MAIL_IS_DELETED = false;

	/** The value for the draft field */
	@Basic(optional = false)
	@Column(name = "MAIL_IS_DRAFT", nullable = false)
	private boolean MAIL_IS_DRAFT = false;

	/** The value for the flagged field */
	@Basic(optional = false)
	@Column(name = "MAIL_IS_FLAGGED", nullable = false)
	private boolean MAIL_IS_FLAGGED = false;

	/** The value for the recent field */
	@Basic(optional = false)
	@Column(name = "MAIL_IS_RECENT", nullable = false)
	private boolean MAIL_IS_RECENT = false;

	/** The value for the seen field */
	@Basic(optional = false)
	@Column(name = "MAIL_IS_SEEN", nullable = false)
	private boolean MAIL_IS_SEEN = false;

	/** Number of octets in the full document content */
	@Basic(optional = false)
	@Column(name = "MAIL_CONTENT_OCTETS_COUNT", nullable = false)
	private Integer MAIL_CONTENT_OCTETS_COUNT = 0;

	/** THE CRFL count when this document is textual, null otherwise */
	@Basic(optional = true)
	@Column(name = "MAIL_TEXTUAL_COUNT", nullable = true)
	private Integer MAIL_TEXTUAL_COUNT = 0;

	/** Meta data for this message */
	@OneToMany(mappedBy = "mailbox", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@OrderBy("PROPERTY_ID DESC")
	private List<MessageProperty> properties;

	/** MIME type */
	@Basic(optional = true)
	@Column(name = "MAIL_MIME_TYPE", nullable = true, length = 200)
	private String MAIL_MIME_TYPE;

	/** The value for the uidValidity field */

	@Column(name = "MAILBOX_SENTER_MAILBOX_ID", nullable = false)
	private long MAILBOX_SENTER_MAILBOX_ID;

	/** The value foe the user email id in here MAILBOX_NAME */
	@Basic(optional = false)
	@Column(name = "MAILBOX_TO_EMAIL", nullable = false, length = 200)
	private String MAILBOX_TO_EMAIL;

	@Basic(optional = false)
	@Column(name = "MAILBOX_TO_USER_NAME", nullable = true, length = 500)
	private String MAILBOX_TO_USER_NAME;

	/** The value for the MAIL DOCUMENT available field */
	@Basic(optional = false)
	@Column(name = "MAIL_DOCUMENT", nullable = false)
	private boolean MAIL_DOCUMENT = false;

	/** The value for the MAIL DOCUMENT_ID available field */
	@Column(name = "MAIL_DOCUMENT_ID")
	private Long MAIL_DOCUMENT_ID;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	public Mailbox() {
		super();
	}

	public Mailbox(long mAILBOX_ID) {
		super();
		MAILBOX_ID = mAILBOX_ID;
	}

	public Mailbox(long mAILBOX_ID, long mAILBOX_FROM_USER_ID, String mAILBOX_FROM_USER_NAME, String mAILBOX_FROM_EMAIL,
			String mAILBOX_NAMESPACE, Date mAIL_DATE, boolean mAIL_IS_ANSWERED, boolean mAIL_IS_DELETED,
			boolean mAIL_IS_DRAFT, boolean mAIL_IS_FLAGGED, boolean mAIL_IS_RECENT, boolean mAIL_IS_SEEN,
			Integer mAIL_CONTENT_OCTETS_COUNT, Integer mAIL_TEXTUAL_COUNT, List<MessageProperty> properties,
			String mAIL_MIME_TYPE, long mAILBOX_TO_USER_ID, String mAILBOX_TO_USER_NAME, String mAILBOX_TO_EMAIL, Integer companyId) {
		super();
		MAILBOX_ID = mAILBOX_ID;
		MAILBOX_FROM_USER_ID = mAILBOX_FROM_USER_ID;
		MAILBOX_FROM_USER_NAME = mAILBOX_FROM_USER_NAME;
		MAILBOX_FROM_EMAIL = mAILBOX_FROM_EMAIL;
		MAILBOX_NAMESPACE = mAILBOX_NAMESPACE;
		MAIL_DATE = mAIL_DATE;
		MAIL_IS_ANSWERED = mAIL_IS_ANSWERED;
		MAIL_IS_DELETED = mAIL_IS_DELETED;
		MAIL_IS_DRAFT = mAIL_IS_DRAFT;
		MAIL_IS_FLAGGED = mAIL_IS_FLAGGED;
		MAIL_IS_RECENT = mAIL_IS_RECENT;
		MAIL_IS_SEEN = mAIL_IS_SEEN;
		MAIL_CONTENT_OCTETS_COUNT = mAIL_CONTENT_OCTETS_COUNT;
		MAIL_TEXTUAL_COUNT = mAIL_TEXTUAL_COUNT;
		this.properties = properties;
		MAIL_MIME_TYPE = mAIL_MIME_TYPE;

		MAILBOX_TO_EMAIL = mAILBOX_TO_EMAIL;
		this.COMPANY_ID = companyId;
	}

	/**
	 * @return the mAILBOX_ID
	 */
	public long getMAILBOX_ID() {
		return MAILBOX_ID;
	}

	/**
	 * @param mAILBOX_ID
	 *            the mAILBOX_ID to set
	 */
	public void setMAILBOX_ID(long mAILBOX_ID) {
		MAILBOX_ID = mAILBOX_ID;
	}

	/**
	 * @return the mAILBOX_FROM_USER_ID
	 */
	public long getMAILBOX_FROM_USER_ID() {
		return MAILBOX_FROM_USER_ID;
	}

	/**
	 * @param mAILBOX_FROM_USER_ID
	 *            the mAILBOX_FROM_USER_ID to set
	 */
	public void setMAILBOX_FROM_USER_ID(long mAILBOX_FROM_USER_ID) {
		MAILBOX_FROM_USER_ID = mAILBOX_FROM_USER_ID;
	}

	/**
	 * @return the mAILBOX_FROM_USER_NAME
	 */
	public String getMAILBOX_FROM_USER_NAME() {
		return MAILBOX_FROM_USER_NAME;
	}

	/**
	 * @param mAILBOX_FROM_USER_NAME
	 *            the mAILBOX_FROM_USER_NAME to set
	 */
	public void setMAILBOX_FROM_USER_NAME(String mAILBOX_FROM_USER_NAME) {
		MAILBOX_FROM_USER_NAME = mAILBOX_FROM_USER_NAME;
	}

	/**
	 * @return the mAILBOX_FROM_EMAIL
	 */
	public String getMAILBOX_FROM_EMAIL() {
		return MAILBOX_FROM_EMAIL;
	}

	/**
	 * @param mAILBOX_FROM_EMAIL
	 *            the mAILBOX_FROM_EMAIL to set
	 */
	public void setMAILBOX_FROM_EMAIL(String mAILBOX_FROM_EMAIL) {
		MAILBOX_FROM_EMAIL = mAILBOX_FROM_EMAIL;
	}

	/**
	 * @return the mAILBOX_NAMESPACE
	 */
	public String getMAILBOX_NAMESPACE() {
		return MAILBOX_NAMESPACE;
	}

	/**
	 * @param mAILBOX_NAMESPACE
	 *            the mAILBOX_NAMESPACE to set
	 */
	public void setMAILBOX_NAMESPACE(String mAILBOX_NAMESPACE) {
		MAILBOX_NAMESPACE = mAILBOX_NAMESPACE;
	}

	/**
	 * @return the mAIL_DATE
	 */
	public Date getMAIL_DATE() {
		return MAIL_DATE;
	}

	/**
	 * @param mAIL_DATE
	 *            the mAIL_DATE to set
	 */
	public void setMAIL_DATE(Date mAIL_DATE) {
		MAIL_DATE = mAIL_DATE;
	}

	/**
	 * @return the mAIL_IS_ANSWERED
	 */
	public boolean isMAIL_IS_ANSWERED() {
		return MAIL_IS_ANSWERED;
	}

	/**
	 * @param mAIL_IS_ANSWERED
	 *            the mAIL_IS_ANSWERED to set
	 */
	public void setMAIL_IS_ANSWERED(boolean mAIL_IS_ANSWERED) {
		MAIL_IS_ANSWERED = mAIL_IS_ANSWERED;
	}

	/**
	 * @return the mAIL_IS_DELETED
	 */
	public boolean isMAIL_IS_DELETED() {
		return MAIL_IS_DELETED;
	}

	/**
	 * @param mAIL_IS_DELETED
	 *            the mAIL_IS_DELETED to set
	 */
	public void setMAIL_IS_DELETED(boolean mAIL_IS_DELETED) {
		MAIL_IS_DELETED = mAIL_IS_DELETED;
	}

	/**
	 * @return the mAIL_IS_DRAFT
	 */
	public boolean isMAIL_IS_DRAFT() {
		return MAIL_IS_DRAFT;
	}

	/**
	 * @param mAIL_IS_DRAFT
	 *            the mAIL_IS_DRAFT to set
	 */
	public void setMAIL_IS_DRAFT(boolean mAIL_IS_DRAFT) {
		MAIL_IS_DRAFT = mAIL_IS_DRAFT;
	}

	/**
	 * @return the mAIL_IS_FLAGGED
	 */
	public boolean isMAIL_IS_FLAGGED() {
		return MAIL_IS_FLAGGED;
	}

	/**
	 * @param mAIL_IS_FLAGGED
	 *            the mAIL_IS_FLAGGED to set
	 */
	public void setMAIL_IS_FLAGGED(boolean mAIL_IS_FLAGGED) {
		MAIL_IS_FLAGGED = mAIL_IS_FLAGGED;
	}

	/**
	 * @return the mAIL_IS_RECENT
	 */
	public boolean isMAIL_IS_RECENT() {
		return MAIL_IS_RECENT;
	}

	/**
	 * @param mAIL_IS_RECENT
	 *            the mAIL_IS_RECENT to set
	 */
	public void setMAIL_IS_RECENT(boolean mAIL_IS_RECENT) {
		MAIL_IS_RECENT = mAIL_IS_RECENT;
	}

	/**
	 * @return the mAIL_IS_SEEN
	 */
	public boolean isMAIL_IS_SEEN() {
		return MAIL_IS_SEEN;
	}

	/**
	 * @param mAIL_IS_SEEN
	 *            the mAIL_IS_SEEN to set
	 */
	public void setMAIL_IS_SEEN(boolean mAIL_IS_SEEN) {
		MAIL_IS_SEEN = mAIL_IS_SEEN;
	}

	/**
	 * @return the mAIL_CONTENT_OCTETS_COUNT
	 */
	public Integer getMAIL_CONTENT_OCTETS_COUNT() {
		return MAIL_CONTENT_OCTETS_COUNT;
	}

	/**
	 * @param mAIL_CONTENT_OCTETS_COUNT
	 *            the mAIL_CONTENT_OCTETS_COUNT to set
	 */
	public void setMAIL_CONTENT_OCTETS_COUNT(Integer mAIL_CONTENT_OCTETS_COUNT) {
		MAIL_CONTENT_OCTETS_COUNT = mAIL_CONTENT_OCTETS_COUNT;
	}

	/**
	 * @return the mAIL_TEXTUAL_COUNT
	 */
	public Integer getMAIL_TEXTUAL_COUNT() {
		return MAIL_TEXTUAL_COUNT;
	}

	/**
	 * @param mAIL_TEXTUAL_COUNT
	 *            the mAIL_TEXTUAL_COUNT to set
	 */
	public void setMAIL_TEXTUAL_COUNT(Integer mAIL_TEXTUAL_COUNT) {
		MAIL_TEXTUAL_COUNT = mAIL_TEXTUAL_COUNT;
	}

	/**
	 * @return the properties
	 */
	public List<MessageProperty> getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(List<MessageProperty> properties) {
		this.properties = properties;
	}

	/**
	 * @return the mAIL_MIME_TYPE
	 */
	public String getMAIL_MIME_TYPE() {
		return MAIL_MIME_TYPE;
	}

	/**
	 * @param mAIL_MIME_TYPE
	 *            the mAIL_MIME_TYPE to set
	 */
	public void setMAIL_MIME_TYPE(String mAIL_MIME_TYPE) {
		MAIL_MIME_TYPE = mAIL_MIME_TYPE;
	}

	/**
	 * @return the mAILBOX_TO_EMAIL
	 */
	public String getMAILBOX_TO_EMAIL() {
		return MAILBOX_TO_EMAIL;
	}

	/**
	 * @param mAILBOX_TO_EMAIL
	 *            the mAILBOX_TO_EMAIL to set
	 */
	public void setMAILBOX_TO_EMAIL(String mAILBOX_TO_EMAIL) {
		MAILBOX_TO_EMAIL = mAILBOX_TO_EMAIL;
	}

	/**
	 * @return the mAILBOX_SENTER_MAILBOX_ID
	 */
	public long getMAILBOX_SENTER_MAILBOX_ID() {
		return MAILBOX_SENTER_MAILBOX_ID;
	}

	/**
	 * @param mAILBOX_SENTER_MAILBOX_ID
	 *            the mAILBOX_SENTER_MAILBOX_ID to set
	 */
	public void setMAILBOX_SENTER_MAILBOX_ID(long mAILBOX_SENTER_MAILBOX_ID) {
		MAILBOX_SENTER_MAILBOX_ID = mAILBOX_SENTER_MAILBOX_ID;
	}

	/**
	 * @return the mAILBOX_TO_USER_NAME
	 */
	public String getMAILBOX_TO_USER_NAME() {
		return MAILBOX_TO_USER_NAME;
	}

	/**
	 * @param mAILBOX_TO_USER_NAME
	 *            the mAILBOX_TO_USER_NAME to set
	 */
	public void setMAILBOX_TO_USER_NAME(String mAILBOX_TO_USER_NAME) {
		MAILBOX_TO_USER_NAME = mAILBOX_TO_USER_NAME;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the mAIL_DOCUMENT
	 */
	public boolean isMAIL_DOCUMENT() {
		return MAIL_DOCUMENT;
	}

	/**
	 * @param mAIL_DOCUMENT
	 *            the mAIL_DOCUMENT to set
	 */
	public void setMAIL_DOCUMENT(boolean mAIL_DOCUMENT) {
		MAIL_DOCUMENT = mAIL_DOCUMENT;
	}

	/**
	 * @return the mAIL_DOCUMENT_ID
	 */
	public Long getMAIL_DOCUMENT_ID() {
		return MAIL_DOCUMENT_ID;
	}

	/**
	 * @param mAIL_DOCUMENT_ID
	 *            the mAIL_DOCUMENT_ID to set
	 */
	public void setMAIL_DOCUMENT_ID(Long mAIL_DOCUMENT_ID) {
		MAIL_DOCUMENT_ID = mAIL_DOCUMENT_ID;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
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
		result = prime * result + (int) (MAILBOX_ID ^ (MAILBOX_ID >>> 32));
		result = prime * result + ((MAILBOX_TO_EMAIL == null) ? 0 : MAILBOX_TO_EMAIL.hashCode());
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
		Mailbox other = (Mailbox) obj;
		if (MAILBOX_ID != other.MAILBOX_ID)
			return false;
		if (MAILBOX_TO_EMAIL == null) {
			if (other.MAILBOX_TO_EMAIL != null)
				return false;
		} else if (!MAILBOX_TO_EMAIL.equals(other.MAILBOX_TO_EMAIL))
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
		builder.append("Mailbox [MAILBOX_ID=");
		builder.append(MAILBOX_ID);
		builder.append(", MAILBOX_FROM_USER_ID=");
		builder.append(MAILBOX_FROM_USER_ID);
		builder.append(", MAILBOX_FROM_USER_NAME=");
		builder.append(MAILBOX_FROM_USER_NAME);
		builder.append(", MAILBOX_FROM_EMAIL=");
		builder.append(MAILBOX_FROM_EMAIL);
		builder.append(", MAILBOX_NAMESPACE=");
		builder.append(MAILBOX_NAMESPACE);
		builder.append(", MAIL_DATE=");
		builder.append(MAIL_DATE);
		builder.append(", MAIL_IS_ANSWERED=");
		builder.append(MAIL_IS_ANSWERED);
		builder.append(", MAIL_IS_DELETED=");
		builder.append(MAIL_IS_DELETED);
		builder.append(", MAIL_IS_DRAFT=");
		builder.append(MAIL_IS_DRAFT);
		builder.append(", MAIL_IS_FLAGGED=");
		builder.append(MAIL_IS_FLAGGED);
		builder.append(", MAIL_IS_RECENT=");
		builder.append(MAIL_IS_RECENT);
		builder.append(", MAIL_IS_SEEN=");
		builder.append(MAIL_IS_SEEN);
		builder.append(", MAIL_CONTENT_OCTETS_COUNT=");
		builder.append(MAIL_CONTENT_OCTETS_COUNT);
		builder.append(", MAIL_TEXTUAL_COUNT=");
		builder.append(MAIL_TEXTUAL_COUNT);
		builder.append(", properties=");
		builder.append(properties != null ? properties.subList(0, Math.min(properties.size(), maxLen)) : null);
		builder.append(", MAIL_MIME_TYPE=");
		builder.append(MAIL_MIME_TYPE);
		builder.append(", MAILBOX_SENTER_MAILBOX_ID=");
		builder.append(MAILBOX_SENTER_MAILBOX_ID);
		builder.append(", MAILBOX_TO_EMAIL=");
		builder.append(MAILBOX_TO_EMAIL);
		builder.append(", MAILBOX_TO_USER_NAME=");
		builder.append(MAILBOX_TO_USER_NAME);
		builder.append(", MAIL_DOCUMENT=");
		builder.append(MAIL_DOCUMENT);
		builder.append(", MAIL_DOCUMENT_ID=");
		builder.append(MAIL_DOCUMENT_ID);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append("]");
		return builder.toString();
	}

}
