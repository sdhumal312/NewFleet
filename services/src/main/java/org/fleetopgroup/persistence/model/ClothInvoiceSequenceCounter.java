package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ClothInvoiceSequenceCounter")
public class ClothInvoiceSequenceCounter implements Serializable {

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
	
	
	public ClothInvoiceSequenceCounter() {
		super();
	}
	
	

	public ClothInvoiceSequenceCounter(Integer companyId, long nextVal) {
		super();
		this.companyId = companyId;
		this.nextVal = nextVal;
	}



	public long getSequence_Id() {
		return sequence_Id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public long getNextVal() {
		return nextVal;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setSequence_Id(long sequence_Id) {
		this.sequence_Id = sequence_Id;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setNextVal(long nextVal) {
		this.nextVal = nextVal;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClothInvoiceSequenceCounter [sequence_Id=");
		builder.append(sequence_Id);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", nextVal=");
		builder.append(nextVal);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
