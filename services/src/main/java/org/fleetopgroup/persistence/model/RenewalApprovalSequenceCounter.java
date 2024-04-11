package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "renewalapprovalsequencecounter")
public class RenewalApprovalSequenceCounter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sequence_Id")
	private long sequence_Id;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "nextVal", nullable = false)
	private long	nextVal;
	
	@Column(name = "markForDelete")
	private boolean markForDelete;
	
	public RenewalApprovalSequenceCounter() {
		super();
	}
	
	

	public RenewalApprovalSequenceCounter(Integer companyId, long nextVal) {
		super();
		this.companyId = companyId;
		this.nextVal = nextVal;
	}



	public long getSequence_Id() {
		return sequence_Id;
	}

	public void setSequence_Id(long sequence_Id) {
		this.sequence_Id = sequence_Id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public long getNextVal() {
		return nextVal;
	}

	public void setNextVal(long nextVal) {
		this.nextVal = nextVal;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	

}
