/**
 * 
 */
package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author fleetop
 *
 */
@Entity
@Table(name = "MessageProperty")
public class MessageProperty implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** The system unique key */
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROPERTY_ID", nullable = true)
    private long id;
    
   
    /** Value of this property */
    @Basic(optional = false)
    @Column(name = "PROPERTY_MESSAGE", nullable = false, length = 1000)
    private String PROPERTY_MESSAGE;
    
    /** Order within the list of properties */
    @Column(name = "PROPERTY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date PROPERTY_DATE = new Date();
    
    @Column(name = "MAILBOX_SENT_ID", nullable = false)
	private long MAILBOX_SENT_ID;
    
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
    
    
    /** The value for the mailboxId field */
    @ManyToOne
    @JoinColumn(name = "MAILBOX_ID")
    private Mailbox mailbox;
    
    @Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
    

	public MessageProperty() {
		super();
	}

	

	public MessageProperty(String pROPERTY_MESSAGE) {
		super();
		
		PROPERTY_MESSAGE = pROPERTY_MESSAGE;
	}


	public MessageProperty(String pROPERTY_MESSAGE, Date pROPERTY_DATE, long mAILBOX_SENT_ID, long mAILBOX_FROM_USER_ID,
			String mAILBOX_FROM_USER_NAME, String mAILBOX_FROM_EMAIL) {
		super();
		PROPERTY_MESSAGE = pROPERTY_MESSAGE;
		PROPERTY_DATE = pROPERTY_DATE;
		MAILBOX_SENT_ID = mAILBOX_SENT_ID;
		MAILBOX_FROM_USER_ID = mAILBOX_FROM_USER_ID;
		MAILBOX_FROM_USER_NAME = mAILBOX_FROM_USER_NAME;
		MAILBOX_FROM_EMAIL = mAILBOX_FROM_EMAIL;
	}



	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}



	public boolean isMarkForDelete() {
		return markForDelete;
	}



	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}



	/**
	 * @return the mAILBOX_FROM_USER_ID
	 */
	public long getMAILBOX_FROM_USER_ID() {
		return MAILBOX_FROM_USER_ID;
	}



	/**
	 * @param mAILBOX_FROM_USER_ID the mAILBOX_FROM_USER_ID to set
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
	 * @param mAILBOX_FROM_USER_NAME the mAILBOX_FROM_USER_NAME to set
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
	 * @param mAILBOX_FROM_EMAIL the mAILBOX_FROM_EMAIL to set
	 */
	public void setMAILBOX_FROM_EMAIL(String mAILBOX_FROM_EMAIL) {
		MAILBOX_FROM_EMAIL = mAILBOX_FROM_EMAIL;
	}



	/**
	 * @return the pROPERTY_MESSAGE
	 */
	public String getPROPERTY_MESSAGE() {
		return PROPERTY_MESSAGE;
	}



	/**
	 * @param pROPERTY_MESSAGE the pROPERTY_MESSAGE to set
	 */
	public void setPROPERTY_MESSAGE(String pROPERTY_MESSAGE) {
		PROPERTY_MESSAGE = pROPERTY_MESSAGE;
	}



	/**
	 * @return the pROPERTY_DATE
	 */
	public Date getPROPERTY_DATE() {
		return PROPERTY_DATE;
	}



	/**
	 * @param pROPERTY_DATE the pROPERTY_DATE to set
	 */
	public void setPROPERTY_DATE(Date pROPERTY_DATE) {
		PROPERTY_DATE = pROPERTY_DATE;
	}



	/**
	 * @return the mAILBOX_SENT_ID
	 */
	public long getMAILBOX_SENT_ID() {
		return MAILBOX_SENT_ID;
	}



	/**
	 * @param mAILBOX_SENT_ID the mAILBOX_SENT_ID to set
	 */
	public void setMAILBOX_SENT_ID(long mAILBOX_SENT_ID) {
		MAILBOX_SENT_ID = mAILBOX_SENT_ID;
	}



	/**
	 * @return the mailbox
	 */
	public Mailbox getMailbox() {
		return mailbox;
	}



	/**
	 * @param mailbox the mailbox to set
	 */
	public void setMailbox(Mailbox mailbox) {
		this.mailbox = mailbox;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (MAILBOX_SENT_ID ^ (MAILBOX_SENT_ID >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((mailbox == null) ? 0 : mailbox.hashCode());
		return result;
	}



	/* (non-Javadoc)
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
		MessageProperty other = (MessageProperty) obj;
		if (MAILBOX_SENT_ID != other.MAILBOX_SENT_ID)
			return false;
		if (id != other.id)
			return false;
		if (mailbox == null) {
			if (other.mailbox != null)
				return false;
		} else if (!mailbox.equals(other.mailbox))
			return false;
		return true;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageProperty [PROPERTY_MESSAGE=");
		builder.append(PROPERTY_MESSAGE);
		builder.append(", PROPERTY_DATE=");
		builder.append(PROPERTY_DATE);
		builder.append(", MAILBOX_SENT_ID=");
		builder.append(MAILBOX_SENT_ID);
		builder.append(", MAILBOX_FROM_USER_ID=");
		builder.append(MAILBOX_FROM_USER_ID);
		builder.append(", MAILBOX_FROM_USER_NAME=");
		builder.append(MAILBOX_FROM_USER_NAME);
		builder.append(", MAILBOX_FROM_EMAIL=");
		builder.append(MAILBOX_FROM_EMAIL);
		builder.append(", mailbox=");
		builder.append(mailbox);
		builder.append("]");
		return builder.toString();
	}

}
