package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 */
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "ReasonTypeHistory")
public class ReasonTypeHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Reason_Type_History_Id")
	private Integer Reason_Type_History_Id;

	@Column(name = "Reason_id")
	private Integer Reason_id;

	@Column(name = "Reason_Type", unique = false, nullable = false, length = 150)
	private String Reason_Type;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	public Integer getReason_Type_History_Id() {
		return Reason_Type_History_Id;
	}

	public void setReason_Type_History_Id(Integer reason_Type_History_Id) {
		Reason_Type_History_Id = reason_Type_History_Id;
	}

	public Integer getReason_id() {
		return Reason_id;
	}

	public void setReason_id(Integer reason_id) {
		Reason_id = reason_id;
	}

	public String getReason_Type() {
		return Reason_Type;
	}

	public void setReason_Type(String reason_Type) {
		Reason_Type = reason_Type;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}