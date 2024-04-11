package org.fleetopgroup.persistence.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DriverSalaryInsertionDetails")
public class DriverSalaryInsertionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driverSalaryInsertionDetailsId")
	private Long 	driverSalaryInsertionDetailsId;
	
	@Column(name = "driver_id")
	private int 	driver_id;
	
	@Column(name = "salaryDate")
	private Timestamp	salaryDate;
	
	@Column(name ="companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	
	public DriverSalaryInsertionDetails() {
		super();
	}
	
	
	
	public DriverSalaryInsertionDetails(int driver_id, Timestamp salaryDate, Integer companyId) {
		super();
		this.driver_id = driver_id;
		this.salaryDate = salaryDate;
		this.companyId = companyId;
	}



	public Long getDriverSalaryInsertionDetailsId() {
		return driverSalaryInsertionDetailsId;
	}
	public int getDriver_id() {
		return driver_id;
	}
	public Timestamp getSalaryDate() {
		return salaryDate;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public boolean isMarkForDelete() {
		return markForDelete;
	}
	public void setDriverSalaryInsertionDetailsId(Long driverSalaryInsertionDetailsId) {
		this.driverSalaryInsertionDetailsId = driverSalaryInsertionDetailsId;
	}
	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}
	public void setSalaryDate(Timestamp salaryDate) {
		this.salaryDate = salaryDate;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result
				+ ((driverSalaryInsertionDetailsId == null) ? 0 : driverSalaryInsertionDetailsId.hashCode());
		result = prime * result + driver_id;
		result = prime * result + ((salaryDate == null) ? 0 : salaryDate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DriverSalaryInsertionDetails other = (DriverSalaryInsertionDetails) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (driverSalaryInsertionDetailsId == null) {
			if (other.driverSalaryInsertionDetailsId != null)
				return false;
		} else if (!driverSalaryInsertionDetailsId.equals(other.driverSalaryInsertionDetailsId))
			return false;
		if (driver_id != other.driver_id)
			return false;
		if (salaryDate == null) {
			if (other.salaryDate != null)
				return false;
		} else if (!salaryDate.equals(other.salaryDate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverSalaryInsertionDetails [driverSalaryInsertionDetailsId=");
		builder.append(driverSalaryInsertionDetailsId);
		builder.append(", driver_id=");
		builder.append(driver_id);
		builder.append(", salaryDate=");
		builder.append(salaryDate);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
