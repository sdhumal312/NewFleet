package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "driverreminder")
public class DriverReminder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driver_remid")
	private int driver_remid;

	@Column(name = "driver_id")
	private Integer driver_id;

	@Column(name = "driverRenewalTypeId", nullable = false)
	private Long driverRenewalTypeId;

	@Column(name = "driver_dlnumber", length = 25)
	private String driver_dlnumber;

	@Column(name = "driver_dlfrom")
	private Date driver_dlfrom;

	@Column(name = "driver_dlto")
	private Date driver_dlto;

	@Column(name = "driver_timethreshold", length = 5)
	private Integer driver_timethreshold;

	@Column(name = "driver_periedthreshold", length = 5)
	private Integer driver_periedthreshold;

	@Column(name = "driver_renewaldate", length = 25)
	private String driver_renewaldate;

	@Column(name = "driver_revdate", length = 10)
	private String driver_revdate;

	@Column(name = "driver_filename")
	private String driver_filename;

	@Lob
	@Column(name = "driver_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] driver_content;

	@Column(name = "driver_content_type")
	private String driver_contentType;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "created", updatable = false)
	private Date created;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "lastModified")
	private Date lastModified;
	
	@Column(name ="markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name ="renewalRecieptId")
	private Long renewalRecieptId;
	
	@Column(name ="newDriverReminder", nullable = false)
	private boolean newDriverReminder;

	/* Show Date field */

	private String driver_dueEndDate;
	private String driver_dueRemDate;
	private String driver_dueEndCalendar;
	private String driver_dueDifference;

	public DriverReminder() {
		super();
	}
	

	public DriverReminder(Date driver_dlfrom, Date driver_dlto, Integer driver_periedthreshold,
			String driver_renewaldate, Integer companyId) {
		super();
		this.driver_dlfrom = driver_dlfrom;
		this.driver_dlto = driver_dlto;
		this.driver_periedthreshold = driver_periedthreshold;
		this.driver_renewaldate = driver_renewaldate;
		this.companyId = companyId;
	}







	/**
	 * @return the driver_remid
	 */
	public int getDriver_remid() {
		return driver_remid;
	}

	/**
	 * @param driver_remid
	 *            the driver_remid to set
	 */
	public void setDriver_remid(int driver_remid) {
		this.driver_remid = driver_remid;
	}

	/**
	 * @return the driver_id
	 */
	public Integer getDriver_id() {
		return driver_id;
	}

	/**
	 * @param driver_id
	 *            the driver_id to set
	 */
	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}

	/**
	 * @return the driver_remindertype
	 *//*
	public String getDriver_remindertype() {
		return driver_remindertype;
	}

	*//**
	 * @param driver_remindertype
	 *            the driver_remindertype to set
	 *//*
	public void setDriver_remindertype(String driver_remindertype) {
		this.driver_remindertype = driver_remindertype;
	}*/

	public Long getDriverRenewalTypeId() {
		return driverRenewalTypeId;
	}







	public void setDriverRenewalTypeId(Long driverRenewalTypeId) {
		this.driverRenewalTypeId = driverRenewalTypeId;
	}







	/**
	 * @return the driver_dlnumber
	 */
	public String getDriver_dlnumber() {
		return driver_dlnumber;
	}

	/**
	 * @param driver_dlnumber
	 *            the driver_dlnumber to set
	 */
	public void setDriver_dlnumber(String driver_dlnumber) {
		this.driver_dlnumber = driver_dlnumber;
	}

	/**
	 * @return the driver_dlfrom
	 */
	public Date getDriver_dlfrom() {
		return driver_dlfrom;
	}

	/**
	 * @param driver_dlfrom
	 *            the driver_dlfrom to set
	 */
	public void setDriver_dlfrom(Date driver_dlfrom) {
		this.driver_dlfrom = driver_dlfrom;
	}

	/**
	 * @return the driver_dlto
	 */
	public Date getDriver_dlto() {
		return driver_dlto;
	}

	/**
	 * @param driver_dlto
	 *            the driver_dlto to set
	 */
	public void setDriver_dlto(Date driver_dlto) {
		this.driver_dlto = driver_dlto;
	}

	/**
	 * @return the driver_timethreshold
	 */
	public Integer getDriver_timethreshold() {
		return driver_timethreshold;
	}

	/**
	 * @param driver_timethreshold
	 *            the driver_timethreshold to set
	 */
	public void setDriver_timethreshold(Integer driver_timethreshold) {
		this.driver_timethreshold = driver_timethreshold;
	}

	/**
	 * @return the driver_periedthreshold
	 */
	public Integer getDriver_periedthreshold() {
		return driver_periedthreshold;
	}

	/**
	 * @param driver_periedthreshold
	 *            the driver_periedthreshold to set
	 */
	public void setDriver_periedthreshold(Integer driver_periedthreshold) {
		this.driver_periedthreshold = driver_periedthreshold;
	}

	/**
	 * @return the driver_renewaldate
	 */
	public String getDriver_renewaldate() {
		return driver_renewaldate;
	}

	/**
	 * @param driver_renewaldate
	 *            the driver_renewaldate to set
	 */
	public void setDriver_renewaldate(String driver_renewaldate) {
		this.driver_renewaldate = driver_renewaldate;
	}

	/**
	 * @return the driver_revdate
	 *//*
	public String getDriver_revdate() {
		return driver_revdate;
	}

	*//**
	 * @param driver_revdate
	 *            the driver_revdate to set
	 *//*
	public void setDriver_revdate(String driver_revdate) {
		this.driver_revdate = driver_revdate;
	}*/

	/**
	 * @return the driver_filename
	 */
	public String getDriver_filename() {
		return driver_filename;
	}

	/**
	 * @param driver_filename
	 *            the driver_filename to set
	 */
	public void setDriver_filename(String driver_filename) {
		this.driver_filename = driver_filename;
	}

	

	public byte[] getDriver_content() {
		return driver_content;
	}



	public void setDriver_content(byte[] driver_content) {
		this.driver_content = driver_content;
	}



	/**
	 * @return the driver_contentType
	 */
	public String getDriver_contentType() {
		return driver_contentType;
	}

	/**
	 * @param driver_contentType
	 *            the driver_contentType to set
	 */
	public void setDriver_contentType(String driver_contentType) {
		this.driver_contentType = driver_contentType;
	}

	/**
	 * @return the driver_dueEndDate
	 */
	public String getDriver_dueEndDate() {
		return driver_dueEndDate;
	}

	/**
	 * @param driver_dueEndDate
	 *            the driver_dueEndDate to set
	 */
	public void setDriver_dueEndDate(String driver_dueEndDate) {
		this.driver_dueEndDate = driver_dueEndDate;
	}

	/**
	 * @return the driver_dueRemDate
	 */
	public String getDriver_dueRemDate() {
		return driver_dueRemDate;
	}

	/**
	 * @param driver_dueRemDate
	 *            the driver_dueRemDate to set
	 */
	public void setDriver_dueRemDate(String driver_dueRemDate) {
		this.driver_dueRemDate = driver_dueRemDate;
	}

	/**
	 * @return the driver_dueEndCalendar
	 */
	public String getDriver_dueEndCalendar() {
		return driver_dueEndCalendar;
	}

	/**
	 * @param driver_dueEndCalendar
	 *            the driver_dueEndCalendar to set
	 */
	public void setDriver_dueEndCalendar(String driver_dueEndCalendar) {
		this.driver_dueEndCalendar = driver_dueEndCalendar;
	}

	/**
	 * @return the driver_dueDifference
	 */
	public String getDriver_dueDifference() {
		return driver_dueDifference;
	}

	/**
	 * @param driver_dueDifference
	 *            the driver_dueDifference to set
	 */
	public void setDriver_dueDifference(String driver_dueDifference) {
		this.driver_dueDifference = driver_dueDifference;
	}



	public Integer getCompanyId() {
		return companyId;
	}







	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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







	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}







	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}







	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}







	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}







	/**
	 * @return the lastModified
	 */
	public Date getLastModified() {
		return lastModified;
	}







	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}







	public boolean isMarkForDelete() {
		return markForDelete;
	}







	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public boolean isNewDriverReminder() {
		return newDriverReminder;
	}


	public void setNewDriverReminder(boolean newDriverReminder) {
		this.newDriverReminder = newDriverReminder;
	}


	public Long getRenewalRecieptId() {
		return renewalRecieptId;
	}


	public void setRenewalRecieptId(Long renewalRecieptId) {
		this.renewalRecieptId = renewalRecieptId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver_dlfrom == null) ? 0 : driver_dlfrom.hashCode());
		result = prime * result + ((driver_dlto == null) ? 0 : driver_dlto.hashCode());
		result = prime * result + ((driver_filename == null) ? 0 : driver_filename.hashCode());
		result = prime * result + ((driver_id == null) ? 0 : driver_id.hashCode());
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
		DriverReminder other = (DriverReminder) obj;
		if (driver_dlfrom == null) {
			if (other.driver_dlfrom != null)
				return false;
		} else if (!driver_dlfrom.equals(other.driver_dlfrom))
			return false;
		if (driver_dlto == null) {
			if (other.driver_dlto != null)
				return false;
		} else if (!driver_dlto.equals(other.driver_dlto))
			return false;
		if (driver_filename == null) {
			if (other.driver_filename != null)
				return false;
		} else if (!driver_filename.equals(other.driver_filename))
			return false;
		if (driver_id == null) {
			if (other.driver_id != null)
				return false;
		} else if (!driver_id.equals(other.driver_id))
			return false;
		return true;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverReminder [driver_remid=");
		builder.append(driver_remid);
		builder.append(", driver_id=");
		builder.append(driver_id);
		/*builder.append(", driver_remindertype=");
		builder.append(driver_remindertype);*/
		builder.append(", driver_dlnumber=");
		builder.append(driver_dlnumber);
		builder.append(", driver_dlfrom=");
		builder.append(driver_dlfrom);
		builder.append(", driver_dlto=");
		builder.append(driver_dlto);
		builder.append(", driver_timethreshold=");
		builder.append(driver_timethreshold);
		builder.append(", driver_periedthreshold=");
		builder.append(driver_periedthreshold);
		builder.append(", driver_renewaldate=");
		builder.append(driver_renewaldate);
		/*builder.append(", driver_revdate=");
		builder.append(driver_revdate);*/
		builder.append(", driver_filename=");
		builder.append(driver_filename);
		builder.append(", driver_content=");
		builder.append(driver_content);
		builder.append(", driver_contentType=");
		builder.append(driver_contentType);
		builder.append(", driver_dueEndDate=");
		builder.append(driver_dueEndDate);
		builder.append(", driver_dueRemDate=");
		builder.append(driver_dueRemDate);
		builder.append(", driver_dueEndCalendar=");
		builder.append(driver_dueEndCalendar);
		builder.append(", driver_dueDifference=");
		builder.append(driver_dueDifference);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}
	
	
}
