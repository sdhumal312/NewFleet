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
@Table(name = "driverreminderhistory")
public class DriverReminderHistory implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driver_rhid")
	private int driver_rhid;

	@Column(name = "driver_id")
	private Integer driver_id;

	@Column(name = "driver_rhtypeId")
	private Long driver_rhtypeId;

	@Column(name = "driver_rhnumber")
	private String driver_rhnumber;

	/*@Column(name = "driver_rhfrom")
	private String driver_rhfrom;*/
	
	@Column(name = "driver_rhfromDate")
	private Date driver_rhfromDate;

	/*@Column(name = "driver_rhto")
	private String driver_rhto;*/
	
	@Column(name = "driver_rhtoDate")
	private Date driver_rhtoDate;

	@Column(name = "driver_timethreshold")
	private Integer driver_timethreshold;

	@Column(name = "driver_periedthreshold")
	private Integer driver_periedthreshold;

	@Column(name = "driver_renewaldate")
	private String driver_renewaldate;

	@Column(name = "driver_revdate")
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
	
	@Column(name = "createdById")
	private Long createdById;
	
	@Column(name = "created")
	private Date created;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	

	public DriverReminderHistory() {
		super();
	}
	
	

	public DriverReminderHistory(Date driver_rhfrom, Date driver_rhto, String driver_filename, Integer companyId) {
		super();
		this.driver_rhfromDate = driver_rhfrom;
		this.driver_rhtoDate = driver_rhto;
		this.driver_filename = driver_filename;
		this.companyId = companyId;
	}



	public int getDriver_rhid() {
		return driver_rhid;
	}

	public void setDriver_rhid(int driver_rhid) {
		this.driver_rhid = driver_rhid;
	}

	public Integer getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}

	/*public String getDriver_rhtype() {
		return driver_rhtype;
	}

	public void setDriver_rhtype(String driver_rhtype) {
		this.driver_rhtype = driver_rhtype;
	}*/

	public String getDriver_rhnumber() {
		return driver_rhnumber;
	}

	public void setDriver_rhnumber(String driver_rhnumber) {
		this.driver_rhnumber = driver_rhnumber;
	}

	/*public String getDriver_rhfrom() {
		return driver_rhfrom;
	}

	public void setDriver_rhfrom(String driver_rhfrom) {
		this.driver_rhfrom = driver_rhfrom;
	}

	public String getDriver_rhto() {
		return driver_rhto;
	}

	public void setDriver_rhto(String driver_rhto) {
		this.driver_rhto = driver_rhto;
	}*/

	public Integer getDriver_timethreshold() {
		return driver_timethreshold;
	}

	public void setDriver_timethreshold(Integer driver_timethreshold) {
		this.driver_timethreshold = driver_timethreshold;
	}

	public Integer getDriver_periedthreshold() {
		return driver_periedthreshold;
	}

	public void setDriver_periedthreshold(Integer driver_periedthreshold) {
		this.driver_periedthreshold = driver_periedthreshold;
	}

	public String getDriver_renewaldate() {
		return driver_renewaldate;
	}

	public void setDriver_renewaldate(String driver_renewaldate) {
		this.driver_renewaldate = driver_renewaldate;
	}

	public String getDriver_revdate() {
		return driver_revdate;
	}

	public void setDriver_revdate(String driver_revdate) {
		this.driver_revdate = driver_revdate;
	}

	public String getDriver_filename() {
		return driver_filename;
	}

	public void setDriver_filename(String driver_filename) {
		this.driver_filename = driver_filename;
	}

	

	public byte[] getDriver_content() {
		return driver_content;
	}



	public void setDriver_content(byte[] driver_content) {
		this.driver_content = driver_content;
	}



	public String getDriver_contentType() {
		return driver_contentType;
	}

	public void setDriver_contentType(String driver_contentType) {
		this.driver_contentType = driver_contentType;
	}



	/**
	 * @return the driver_rhtypeId
	 */
	public Long getDriver_rhtypeId() {
		return driver_rhtypeId;
	}



	/**
	 * @param driver_rhtypeId the driver_rhtypeId to set
	 */
	public void setDriver_rhtypeId(Long driver_rhtypeId) {
		this.driver_rhtypeId = driver_rhtypeId;
	}



	public Integer getCompanyId() {
		return companyId;
	}



	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}



	/**
	 * @return the driver_rhfromDate
	 */
	public Date getDriver_rhfromDate() {
		return driver_rhfromDate;
	}



	/**
	 * @param driver_rhfromDate the driver_rhfromDate to set
	 */
	public void setDriver_rhfromDate(Date driver_rhfromDate) {
		this.driver_rhfromDate = driver_rhfromDate;
	}



	/**
	 * @return the driver_rhtoDate
	 */
	public Date getDriver_rhtoDate() {
		return driver_rhtoDate;
	}



	/**
	 * @param driver_rhtoDate the driver_rhtoDate to set
	 */
	public void setDriver_rhtoDate(Date driver_rhtoDate) {
		this.driver_rhtoDate = driver_rhtoDate;
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



	public boolean isMarkForDelete() {
		return markForDelete;
	}



	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver_filename == null) ? 0 : driver_filename.hashCode());
		result = prime * result + ((driver_rhfromDate == null) ? 0 : driver_rhfromDate.hashCode());
		result = prime * result + ((driver_rhtoDate == null) ? 0 : driver_rhtoDate.hashCode());
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
		DriverReminderHistory other = (DriverReminderHistory) obj;
		if (driver_filename == null) {
			if (other.driver_filename != null)
				return false;
		} else if (!driver_filename.equals(other.driver_filename))
			return false;
		if (driver_rhfromDate == null) {
			if (other.driver_rhfromDate != null)
				return false;
		} else if (!driver_rhfromDate.equals(other.driver_rhfromDate))
			return false;
		if (driver_rhtoDate == null) {
			if (other.driver_rhtoDate != null)
				return false;
		} else if (!driver_rhtoDate.equals(other.driver_rhtoDate))
			return false;
		return true;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverReminderHistory [driver_rhid=");
		builder.append(driver_rhid);
		builder.append(", driver_id=");
		builder.append(driver_id);
	/*	builder.append(", driver_rhtype=");
		builder.append(driver_rhtype);*/
		builder.append(", driver_rhnumber=");
		builder.append(driver_rhnumber);
		builder.append(", driver_rhfromDate=");
		builder.append(driver_rhfromDate);
		builder.append(", driver_rhtoDate=");
		builder.append(driver_rhtoDate);
		builder.append(", driver_timethreshold=");
		builder.append(driver_timethreshold);
		builder.append(", driver_periedthreshold=");
		builder.append(driver_periedthreshold);
		builder.append(", driver_renewaldate=");
		builder.append(driver_renewaldate);
		builder.append(", driver_revdate=");
		builder.append(driver_revdate);
		builder.append(", driver_filename=");
		builder.append(driver_filename);
		builder.append(", driver_content=");
		builder.append(driver_content);
		builder.append(", driver_contentType=");
		builder.append(driver_contentType);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}
	
	

}
