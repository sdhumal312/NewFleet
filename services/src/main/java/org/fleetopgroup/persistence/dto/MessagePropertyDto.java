/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.io.Serializable;

/**
 * @author fleetop
 *
 */
public class MessagePropertyDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private long id;
    
   
    /** Value of this property */
     private String PROPERTY_MESSAGE;
    
    /** Order within the list of properties */
     private String PROPERTY_DATE;
    
    private long MAILBOX_SENT_ID;
    
    /** The value for the uidValidity field */
	private long MAILBOX_FROM_USER_ID;

	private String MAILBOX_FROM_USER_NAME;

	/** The value foe the user email id in here MAILBOX_NAME */
	private String MAILBOX_FROM_EMAIL;
    
    
    /** The value for the mailboxId field */
    
	public MessagePropertyDto() {
		super();
	}

	

	public MessagePropertyDto(String pROPERTY_MESSAGE) {
		super();
		
		PROPERTY_MESSAGE = pROPERTY_MESSAGE;
	}


	public MessagePropertyDto(String pROPERTY_MESSAGE, String pROPERTY_DATE, long mAILBOX_SENT_ID, long mAILBOX_FROM_USER_ID,
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
	public String getPROPERTY_DATE() {
		return PROPERTY_DATE;
	}



	/**
	 * @param pROPERTY_DATE the pROPERTY_DATE to set
	 */
	public void setPROPERTY_DATE(String pROPERTY_DATE) {
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



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((MAILBOX_FROM_EMAIL == null) ? 0 : MAILBOX_FROM_EMAIL.hashCode());
		result = prime * result + (int) (MAILBOX_FROM_USER_ID ^ (MAILBOX_FROM_USER_ID >>> 32));
		result = prime * result + ((MAILBOX_FROM_USER_NAME == null) ? 0 : MAILBOX_FROM_USER_NAME.hashCode());
		result = prime * result + (int) (MAILBOX_SENT_ID ^ (MAILBOX_SENT_ID >>> 32));
		result = prime * result + ((PROPERTY_DATE == null) ? 0 : PROPERTY_DATE.hashCode());
		result = prime * result + ((PROPERTY_MESSAGE == null) ? 0 : PROPERTY_MESSAGE.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		MessagePropertyDto other = (MessagePropertyDto) obj;
		if (MAILBOX_FROM_EMAIL == null) {
			if (other.MAILBOX_FROM_EMAIL != null)
				return false;
		} else if (!MAILBOX_FROM_EMAIL.equals(other.MAILBOX_FROM_EMAIL))
			return false;
		if (MAILBOX_FROM_USER_ID != other.MAILBOX_FROM_USER_ID)
			return false;
		if (MAILBOX_FROM_USER_NAME == null) {
			if (other.MAILBOX_FROM_USER_NAME != null)
				return false;
		} else if (!MAILBOX_FROM_USER_NAME.equals(other.MAILBOX_FROM_USER_NAME))
			return false;
		if (MAILBOX_SENT_ID != other.MAILBOX_SENT_ID)
			return false;
		if (PROPERTY_DATE == null) {
			if (other.PROPERTY_DATE != null)
				return false;
		} else if (!PROPERTY_DATE.equals(other.PROPERTY_DATE))
			return false;
		if (PROPERTY_MESSAGE == null) {
			if (other.PROPERTY_MESSAGE != null)
				return false;
		} else if (!PROPERTY_MESSAGE.equals(other.PROPERTY_MESSAGE))
			return false;
		if (id != other.id)
			return false;
		return true;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessagePropertyDto [id=");
		builder.append(id);
		builder.append(", PROPERTY_MESSAGE=");
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
		builder.append("]");
		return builder.toString();
	}

}
