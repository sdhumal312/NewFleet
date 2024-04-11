package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
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
@Table(name = "DriverTrainingTypeHistory")
public class DriverTrainingTypeHistory implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DriverTrainingTypeHistoryId")
	private Integer DriverTrainingTypeHistoryId;

	@Column(name = "dri_id")
	private Integer dri_id;

	@Column(name = "dri_TrainingType", unique = false, nullable = false)
	private String dri_TrainingType;
		
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public Integer getDriverTrainingTypeHistoryId() {
		return DriverTrainingTypeHistoryId;
	}

	public void setDriverTrainingTypeHistoryId(Integer driverTrainingTypeHistoryId) {
		DriverTrainingTypeHistoryId = driverTrainingTypeHistoryId;
	}

	public Integer getDri_id() {
		return dri_id;
	}

	public void setDri_id(Integer dri_id) {
		this.dri_id = dri_id;
	}

	public String getDri_TrainingType() {
		return dri_TrainingType;
	}

	public void setDri_TrainingType(String dri_TrainingType) {
		this.dri_TrainingType = dri_TrainingType;
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