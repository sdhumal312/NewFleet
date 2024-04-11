package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 *
 *
 */


public class DriverReminderHistoryDto {

	private int driver_rhid;

	private Integer driver_id;

	private String driver_rhtype;
	
	private Long driver_rhtypeId;

	private String driver_rhnumber;

	private String driver_rhfrom;

	private String driver_rhto;

	private Integer driver_timethreshold;

	private Integer driver_periedthreshold;

	private String driver_renewaldate;

	private String driver_revdate;

	private String driver_filename;

	private byte[] driver_content;

	private String driver_contentType;

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

	public String getDriver_rhtype() {
		return driver_rhtype;
	}

	public void setDriver_rhtype(String driver_rhtype) {
		this.driver_rhtype = driver_rhtype;
	}

	public String getDriver_rhnumber() {
		return driver_rhnumber;
	}

	public void setDriver_rhnumber(String driver_rhnumber) {
		this.driver_rhnumber = driver_rhnumber;
	}

	public String getDriver_rhfrom() {
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
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverReminderHistoryDto [driver_rhid=");
		builder.append(driver_rhid);
		builder.append(", driver_id=");
		builder.append(driver_id);
		builder.append(", driver_rhtype=");
		builder.append(driver_rhtype);
		builder.append(", driver_rhnumber=");
		builder.append(driver_rhnumber);
		builder.append(", driver_rhfrom=");
		builder.append(driver_rhfrom);
		builder.append(", driver_rhto=");
		builder.append(driver_rhto);
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
		builder.append("]");
		return builder.toString();
	}

	
}
