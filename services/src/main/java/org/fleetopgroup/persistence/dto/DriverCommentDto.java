package org.fleetopgroup.persistence.dto;

import java.util.Date;

/**
 * @author fleetop
 *
 *
 *
 */
public class DriverCommentDto {

	private Long driver_commentid;

	private String driver_title;

	private String driver_uploaddate;

	private String driver_editdate;

	private Integer driver_id;

	private String driver_firstname;

	private String driver_Lastname;
	
	private String driverFatherName;

	private String driver_empnumber;

	private String driver_comment;

	private String createdBy;
	
	boolean markForDelete;

	private Date created;
	
	private String creationDate;

	private Long createdById;
	
	private Integer	companyId;
	
	private long	vehicleGroupId;
	
	private String fromDate;
	
	private String toDate;
	
	private String driverFullName;
	
	public Long getDriver_commentid() {
		return driver_commentid;
	}

	public void setDriver_commentid(Long driver_commentid) {
		this.driver_commentid = driver_commentid;
	}

	public String getDriver_title() {
		return driver_title;
	}

	public void setDriver_title(String driver_title) {
		this.driver_title = driver_title;
	}

	public String getDriver_uploaddate() {
		return driver_uploaddate;
	}

	public void setDriver_uploaddate(String driver_uploaddate) {
		this.driver_uploaddate = driver_uploaddate;
	}

	public String getDriver_editdate() {
		return driver_editdate;
	}

	public void setDriver_editdate(String driver_editdate) {
		this.driver_editdate = driver_editdate;
	}

	public Integer getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}

	public String getDriver_comment() {
		return driver_comment;
	}

	public void setDriver_comment(String driver_comment) {
		this.driver_comment = driver_comment;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDriver_firstname() {
		return driver_firstname;
	}

	public void setDriver_firstname(String driver_firstname) {
		this.driver_firstname = driver_firstname;
	}

	public String getDriver_Lastname() {
		return driver_Lastname;
	}

	public void setDriver_Lastname(String driver_Lastname) {
		this.driver_Lastname = driver_Lastname;
	}

	public String getDriver_empnumber() {
		return driver_empnumber;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setDriver_empnumber(String driver_empnumber) {
		this.driver_empnumber = driver_empnumber;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public long getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getDriverFatherName() {
		return driverFatherName;
	}

	public void setDriverFatherName(String driverFatherName) {
		this.driverFatherName = driverFatherName;
	}

	public String getDriverFullName() {
		return driverFullName;
	}

	public void setDriverFullName(String driverFullName) {
		this.driverFullName = driverFullName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverCommentDto [driver_commentid=");
		builder.append(driver_commentid);
		builder.append(", driver_title=");
		builder.append(driver_title);
		builder.append(", driver_uploaddate=");
		builder.append(driver_uploaddate);
		builder.append(", driver_editdate=");
		builder.append(driver_editdate);
		builder.append(", driver_id=");
		builder.append(driver_id);
		builder.append(", driver_comment=");
		builder.append(driver_comment);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append("]");
		return builder.toString();
	}

}
