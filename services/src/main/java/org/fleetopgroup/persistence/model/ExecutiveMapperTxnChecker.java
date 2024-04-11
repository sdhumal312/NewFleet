package org.fleetopgroup.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ExecutiveMapperTxnChecker {
	
	@Id
	@Column(name = "executiveMapperTxnCheckerId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long executiveMapperTxnCheckerId;
	
	@Column(name = "isIVCargoStatusUpdated")
	private boolean isIVCargoStatusUpdated;
	
	@Column(name = "executiveMapperId")
	private Long executiveMapperId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete ;

	public Long getExecutiveMapperTxnCheckerId() {
		return executiveMapperTxnCheckerId;
	}

	public void setExecutiveMapperTxnCheckerId(Long executiveMapperTxnCheckerId) {
		this.executiveMapperTxnCheckerId = executiveMapperTxnCheckerId;
	}

	public boolean isIVCargoStatusUpdated() {
		return isIVCargoStatusUpdated;
	}

	public void setIVCargoStatusUpdated(boolean isIVCargoStatusUpdated) {
		this.isIVCargoStatusUpdated = isIVCargoStatusUpdated;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public Long getExecutiveMapperId() {
		return executiveMapperId;
	}

	public void setExecutiveMapperId(Long executiveMapperId) {
		this.executiveMapperId = executiveMapperId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	

}
