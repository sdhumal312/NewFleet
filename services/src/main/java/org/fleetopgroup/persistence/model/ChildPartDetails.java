package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ChildPartDetails")
public class ChildPartDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "childPartDetailsId")
	private Long childPartDetailsId;
	
	@Column(name = "mainPartId")
	private Long	mainPartId;

	@Column(name = "partId")
	private Long	partId;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public Long getChildPartDetailsId() {
		return childPartDetailsId;
	}

	public void setChildPartDetailsId(Long childPartDetailsId) {
		this.childPartDetailsId = childPartDetailsId;
	}

	public Long getMainPartId() {
		return mainPartId;
	}

	public void setMainPartId(Long mainPartId) {
		this.mainPartId = mainPartId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	
}
