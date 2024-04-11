/**
 * 
 */
package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 */
public class MailboxDto {

	private String MAILBOX_ID_Encode;

	private long MAILBOX_ID;

	/** The value for the uidValidity field */
	private long MAILBOX_FROM_USER_ID;

	private String MAILBOX_FROM_USER_NAME;

	/** The value foe the user email id in here MAILBOX_NAME */
	private String MAILBOX_FROM_EMAIL;

	/** The value for the name field subject */
	private String MAILBOX_NAMESPACE;

	/** The value for the internalDate field */
	private String MAIL_DATE;

	/** The value for the answered field */
	private boolean MAIL_IS_ANSWERED;

	/** The value for the deleted field */
	private boolean MAIL_IS_DELETED;

	/** The value for the draft field */
	private boolean MAIL_IS_DRAFT;

	/** The value for the flagged field */
	private boolean MAIL_IS_FLAGGED;

	/** The value for the recent field */
	private boolean MAIL_IS_RECENT;

	/** The value for the seen field */
	private boolean MAIL_IS_SEEN;

	/** Number of octets in the full document content */
	private long MAIL_CONTENT_OCTETS_COUNT;

	/** THE CRFL count when this document is textual, null otherwise */
	private Integer MAIL_TEXTUAL_COUNT = 0;

	private long MAILBOX_SENTER_MAILBOX_ID;

	/** MIME type */
	private String MAIL_MIME_TYPE;

	/** The value for the uidValidity field */

	/** The value foe the user email id in here MAILBOX_NAME */
	private String MAILBOX_TO_EMAIL;

	/** The value foe the user to id in here MAILBOX_NAME */
	private String MAILBOX_TO_USER_NAME;

	/** The value foe the user to id in here MAIL_DOCUMENT */
	private boolean MAIL_DOCUMENT;

	/** The value for the MAIL DOCUMENT_ID available field */
	private Long MAIL_DOCUMENT_ID;

	public MailboxDto() {
		super();
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
	 * @return the mAILBOX_ID_Encode
	 */
	public String getMAILBOX_ID_Encode() {
		return MAILBOX_ID_Encode;
	}

	/**
	 * @param mAILBOX_ID_Encode
	 *            the mAILBOX_ID_Encode to set
	 */
	public void setMAILBOX_ID_Encode(String mAILBOX_ID_Encode) {
		MAILBOX_ID_Encode = mAILBOX_ID_Encode;
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
	public String getMAIL_DATE() {
		return MAIL_DATE;
	}

	/**
	 * @param mAIL_DATE
	 *            the mAIL_DATE to set
	 */
	public void setMAIL_DATE(String mAIL_DATE) {
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
	public long getMAIL_CONTENT_OCTETS_COUNT() {
		return MAIL_CONTENT_OCTETS_COUNT;
	}

	/**
	 * @param mAIL_CONTENT_OCTETS_COUNT
	 *            the mAIL_CONTENT_OCTETS_COUNT to set
	 */
	public void setMAIL_CONTENT_OCTETS_COUNT(long mAIL_CONTENT_OCTETS_COUNT) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MailboxDto [MAILBOX_ID_Encode=");
		builder.append(MAILBOX_ID_Encode);
		builder.append(", MAILBOX_ID=");
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
		builder.append(", MAILBOX_SENTER_MAILBOX_ID=");
		builder.append(MAILBOX_SENTER_MAILBOX_ID);
		builder.append(", MAIL_MIME_TYPE=");
		builder.append(MAIL_MIME_TYPE);
		builder.append(", MAILBOX_TO_EMAIL=");
		builder.append(MAILBOX_TO_EMAIL);
		builder.append(", MAILBOX_TO_USER_NAME=");
		builder.append(MAILBOX_TO_USER_NAME);
		builder.append(", MAIL_DOCUMENT=");
		builder.append(MAIL_DOCUMENT);
		builder.append(", MAIL_DOCUMENT_ID=");
		builder.append(MAIL_DOCUMENT_ID);
		builder.append("]");
		return builder.toString();
	}

}
