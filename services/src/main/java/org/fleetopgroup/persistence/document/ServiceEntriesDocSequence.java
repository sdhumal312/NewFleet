package org.fleetopgroup.persistence.document;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ServiceEntriesDocSequence")
public class ServiceEntriesDocSequence {
	
	@Id
	private String id;
	
	private Long 	nextVal;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the nextVal
	 */
	public Long getNextVal() {
		return nextVal;
	}

	/**
	 * @param nextVal the nextVal to set
	 */
	public void setNextVal(Long nextVal) {
		this.nextVal = nextVal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServiceEntriesDocSequence [id=" + id + ", nextVal=" + nextVal + "]";
	}
	
	
}
