package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 *
 *
 */
public class DriverDto {

	private int driver_id;
	private String driver_firstname;
	private String driver_Lastname;
	private String driver_fathername;
	private String driver_dateofbirth;
	private String driver_Qualification;
	private String driver_bloodgroup;
	private String driver_languages;
	private String driver_group;
	private short driverSalaryTypeId;
	private String driverSalaryType;
	private Double driver_perdaySalary;
	private Double driver_esiamount;
	private Double driver_pfamount;
	private String driver_email;
	private String driver_mobnumber;
	private String driver_homenumber;
	private String driver_address;
	private String driver_address2;
	private String driver_city;
	private String driver_state;
	private String driver_pincode;
	private String driver_country;
	private String driver_empnumber;
	private String driver_insuranceno;
	private String driver_esino;
	private String driver_pfno;
	private String driver_jobtitle;
	private String driver_trainings;
	private String driver_startdate;
	private String driver_leavedate;
	private String driver_dlnumber;
	private String driver_badgenumber;
	private String driver_dlclass;
	private String driver_dlprovince;
	private String driver_active;
	private String driver_authorised;
	private String driver_dlOriginal;
	private String driver_reffristname;
	private String driver_reflastname;
	private String driver_refcontect;
	private Integer driver_photoid;
	private Integer vid;
	private String driver_aadharnumber;
	private String driver_pannumber;
	private String driver_banknumber;
	private String driver_bankname;
	private String driver_bankifsc;
	private Long tripSheetID;
	private Long tripSheetNumber;
	private String createdBy;
	private String created;
	private String lastModifiedBy;
	private String lastupdated;
	private Integer companyId;
	private long	vehicleGroupId;
	private short driverStatusId;
	private String 	vehicle_registration;
	private Integer driTraningTypeId;
	private Integer driJobId;
	private Date	createdOn;
	private	Date	lastupdatedOn;
	private short	salariedId;
	private String	salariedStr;
	private String	driverFullName;
	private String	dlStatus;
	private String	dlImageLink;
	private String	remark;
	private String	consumptionFuel;
	private String	mileageFuel;
	private String	breakDownTotal;
	private String	issueTotal;
	private String	accidentTotal;
	private String	commentTotal;
	private String  driverJoinDate;
	private String 	dlexpiryDate;
	private String driverStatusStr;
	
	public DriverDto() {
		super();
	}

	public DriverDto(int driver_id, String driver_firstname, String driver_Lastname, String driver_fathername,
			String driver_dateofbirth, String driver_Qualification, String driver_bloodgroup, String driver_languages,
			String driver_group, Double driver_perdaySalary, Double driver_esiamount, String driver_email,
			String driver_mobnumber, String driver_homenumber, String driver_address, String driver_address2,
			String driver_city, String driver_state, String driver_pincode, String driver_country,
			String driver_empnumber, String driver_insuranceno, String driver_esino, String driver_pfno,
			String driver_jobtitle, String driver_trainings, String driver_startdate, String driver_leavedate,
			String driver_dlnumber, String driver_badgenumber, String driver_dlclass, String driver_dlprovince,
			String driver_active, String driver_authorised, String driver_dlOriginal, String driver_reffristname,
			String driver_reflastname, String driver_refcontect, Integer driver_photoid, String driver_aadharnumber,
			String driver_pannumber, String driver_banknumber, String driver_bankname, String driver_bankifsc,
			String createdBy, String created, String lastModifiedBy, String lastupdated, Integer companyId, long vehicleGroupId) {
		super();
		this.driver_id = driver_id;
		this.driver_firstname = driver_firstname;
		this.driver_Lastname = driver_Lastname;
		this.driver_fathername = driver_fathername;
		this.driver_dateofbirth = driver_dateofbirth;
		this.driver_Qualification = driver_Qualification;
		this.driver_bloodgroup = driver_bloodgroup;
		this.driver_languages = driver_languages;
		this.driver_group = driver_group;
		this.driver_perdaySalary = driver_perdaySalary;
		this.driver_esiamount = driver_esiamount;
		this.driver_email = driver_email;
		this.driver_mobnumber = driver_mobnumber;
		this.driver_homenumber = driver_homenumber;
		this.driver_address = driver_address;
		this.driver_address2 = driver_address2;
		this.driver_city = driver_city;
		this.driver_state = driver_state;
		this.driver_pincode = driver_pincode;
		this.driver_country = driver_country;
		this.driver_empnumber = driver_empnumber;
		this.driver_insuranceno = driver_insuranceno;
		this.driver_esino = driver_esino;
		this.driver_pfno = driver_pfno;
		this.driver_jobtitle = driver_jobtitle;
		this.driver_trainings = driver_trainings;
		this.driver_startdate = driver_startdate;
		this.driver_leavedate = driver_leavedate;
		this.driver_dlnumber = driver_dlnumber;
		this.driver_badgenumber = driver_badgenumber;
		this.driver_dlclass = driver_dlclass;
		this.driver_dlprovince = driver_dlprovince;
		this.driver_active = driver_active;
		this.driver_authorised = driver_authorised;
		this.driver_dlOriginal = driver_dlOriginal;
		this.driver_reffristname = driver_reffristname;
		this.driver_reflastname = driver_reflastname;
		this.driver_refcontect = driver_refcontect;
		this.driver_photoid = driver_photoid;
		this.driver_aadharnumber = driver_aadharnumber;
		this.driver_pannumber = driver_pannumber;
		this.driver_banknumber = driver_banknumber;
		this.driver_bankname = driver_bankname;
		this.driver_bankifsc = driver_bankifsc;
		this.createdBy = createdBy;
		this.created = created;
		this.lastModifiedBy = lastModifiedBy;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
		this.vehicleGroupId = vehicleGroupId;
	}

	public int getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
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

	public String getDriver_fathername() {
		return driver_fathername;
	}

	public void setDriver_fathername(String driver_fathername) {
		this.driver_fathername = driver_fathername;
	}

	public String getDriver_dateofbirth() {
		return driver_dateofbirth;
	}

	public void setDriver_dateofbirth(String driver_dateofbirth) {
		this.driver_dateofbirth = driver_dateofbirth;
	}

	public String getDriver_Qualification() {
		return driver_Qualification;
	}

	public void setDriver_Qualification(String driver_Qualification) {
		this.driver_Qualification = driver_Qualification;
	}

	public String getDriver_bloodgroup() {
		return driver_bloodgroup;
	}

	public void setDriver_bloodgroup(String driver_bloodgroup) {
		this.driver_bloodgroup = driver_bloodgroup;
	}

	public String getDriver_languages() {
		return driver_languages;
	}

	public void setDriver_languages(String driver_languages) {
		this.driver_languages = driver_languages;
	}

	public String getDriver_group() {
		return driver_group;
	}

	public void setDriver_group(String driver_group) {
		this.driver_group = driver_group;
	}

	public String getDriver_email() {
		return driver_email;
	}

	public void setDriver_email(String driver_email) {
		this.driver_email = driver_email;
	}

	public String getDriver_mobnumber() {
		return driver_mobnumber;
	}

	public void setDriver_mobnumber(String driver_mobnumber) {
		this.driver_mobnumber = driver_mobnumber;
	}

	public String getDriver_homenumber() {
		return driver_homenumber;
	}

	public void setDriver_homenumber(String driver_homenumber) {
		this.driver_homenumber = driver_homenumber;
	}

	/**
	 * @return the driver_insuranceno
	 */
	public String getDriver_insuranceno() {
		return driver_insuranceno;
	}

	/**
	 * @param driver_insuranceno
	 *            the driver_insuranceno to set
	 */
	public void setDriver_insuranceno(String driver_insuranceno) {
		this.driver_insuranceno = driver_insuranceno;
	}

	/**
	 * @return the driver_esino
	 */
	public String getDriver_esino() {
		return driver_esino;
	}

	/**
	 * @param driver_esino
	 *            the driver_esino to set
	 */
	public void setDriver_esino(String driver_esino) {
		this.driver_esino = driver_esino;
	}

	/**
	 * @return the driver_pfno
	 */
	public String getDriver_pfno() {
		return driver_pfno;
	}

	/**
	 * @param driver_pfno
	 *            the driver_pfno to set
	 */
	public void setDriver_pfno(String driver_pfno) {
		this.driver_pfno = driver_pfno;
	}

	public String getDriver_dlclass() {
		return driver_dlclass;
	}

	public void setDriver_dlclass(String driver_dlclass) {
		this.driver_dlclass = driver_dlclass;
	}

	public String getDriver_dlprovince() {
		return driver_dlprovince;
	}

	public void setDriver_dlprovince(String driver_dlprovince) {
		this.driver_dlprovince = driver_dlprovince;
	}

	public String getDriver_active() {
		return driver_active;
	}

	public void setDriver_active(String driver_active) {
		this.driver_active = driver_active;
	}

	public String getDriver_address() {
		return driver_address;
	}

	public void setDriver_address(String driver_address) {
		this.driver_address = driver_address;
	}

	public String getDriver_address2() {
		return driver_address2;
	}

	public void setDriver_address2(String driver_address2) {
		this.driver_address2 = driver_address2;
	}

	public String getDriver_city() {
		return driver_city;
	}

	public void setDriver_city(String driver_city) {
		this.driver_city = driver_city;
	}

	public String getDriver_state() {
		return driver_state;
	}

	public void setDriver_state(String driver_state) {
		this.driver_state = driver_state;
	}

	/**
	 * @return the driver_pincode
	 */
	public String getDriver_pincode() {
		return driver_pincode;
	}

	/**
	 * @param driver_pincode
	 *            the driver_pincode to set
	 */
	public void setDriver_pincode(String driver_pincode) {
		this.driver_pincode = driver_pincode;
	}

	public String getDriver_country() {
		return driver_country;
	}

	public void setDriver_country(String driver_country) {
		this.driver_country = driver_country;
	}

	public String getDriver_empnumber() {
		return driver_empnumber;
	}

	public void setDriver_empnumber(String driver_empnumber) {
		this.driver_empnumber = driver_empnumber;
	}

	public String getDriver_jobtitle() {
		return driver_jobtitle;
	}

	public void setDriver_jobtitle(String driver_jobtitle) {
		this.driver_jobtitle = driver_jobtitle;
	}

	public String getDriver_startdate() {
		return driver_startdate;
	}

	public void setDriver_startdate(String driver_startdate) {
		this.driver_startdate = driver_startdate;
	}

	public String getDriver_leavedate() {
		return driver_leavedate;
	}

	public void setDriver_leavedate(String driver_leavedate) {
		this.driver_leavedate = driver_leavedate;
	}

	public String getDriver_dlnumber() {
		return driver_dlnumber;
	}

	public void setDriver_dlnumber(String driver_dlnumber) {
		this.driver_dlnumber = driver_dlnumber;
	}

	public String getDriver_badgenumber() {
		return driver_badgenumber;
	}

	public void setDriver_badgenumber(String driver_badgenumber) {
		this.driver_badgenumber = driver_badgenumber;
	}

	public String getDriver_authorised() {
		return driver_authorised;
	}

	public void setDriver_authorised(String driver_authorised) {
		this.driver_authorised = driver_authorised;
	}

	public String getDriver_reffristname() {
		return driver_reffristname;
	}

	public void setDriver_reffristname(String driver_reffristname) {
		this.driver_reffristname = driver_reffristname;
	}

	public String getDriver_reflastname() {
		return driver_reflastname;
	}

	public void setDriver_reflastname(String driver_reflastname) {
		this.driver_reflastname = driver_reflastname;
	}

	public String getDriver_refcontect() {
		return driver_refcontect;
	}

	public void setDriver_refcontect(String driver_refcontect) {
		this.driver_refcontect = driver_refcontect;
	}

	public Integer getDriver_photoid() {
		return driver_photoid;
	}

	public void setDriver_photoid(Integer driver_photoid) {
		this.driver_photoid = driver_photoid;
	}

	public String getDriverSalaryType() {
		return driverSalaryType;
	}

	public void setDriverSalaryType(String driverSalaryType) {
		this.driverSalaryType = driverSalaryType;
	}

	public short getDriverSalaryTypeId() {
		return driverSalaryTypeId;
	}

	public void setDriverSalaryTypeId(short driverSalaryTypeId) {
		this.driverSalaryTypeId = driverSalaryTypeId;
	}

	/**
	 * @return the driver_perdaySalary
	 */
	public Double getDriver_perdaySalary() {
		return driver_perdaySalary;
	}

	/**
	 * @param driver_perdaySalary
	 *            the driver_perdaySalary to set
	 */
	public void setDriver_perdaySalary(Double driver_perdaySalary) {
		this.driver_perdaySalary =Utility.round(driver_perdaySalary,2);
	}

	/**
	 * @return the driver_esiamount
	 */
	public Double getDriver_esiamount() {
		return driver_esiamount;
	}

	/**
	 * @param driver_esiamount
	 *            the driver_esiamount to set
	 */
	public void setDriver_esiamount(Double driver_esiamount) {
		this.driver_esiamount = Utility.round(driver_esiamount, 2);
	}

	public Double getDriver_pfamount() {
		return driver_pfamount;
	}

	public void setDriver_pfamount(Double driver_pfamount) {
		this.driver_pfamount = Utility.round(driver_pfamount, 2);
	}

	/**
	 * @return the driver_trainings
	 */
	public String getDriver_trainings() {
		return driver_trainings;
	}

	/**
	 * @param driver_trainings
	 *            the driver_trainings to set
	 */
	public void setDriver_trainings(String driver_trainings) {
		this.driver_trainings = driver_trainings;
	}

	/**
	 * @return the driver_dlOriginal
	 */
	public String getDriver_dlOriginal() {
		return driver_dlOriginal;
	}

	/**
	 * @param driver_dlOriginal
	 *            the driver_dlOriginal to set
	 */
	public void setDriver_dlOriginal(String driver_dlOriginal) {
		this.driver_dlOriginal = driver_dlOriginal;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the driver_aadharnumber
	 */
	public String getDriver_aadharnumber() {
		return driver_aadharnumber;
	}

	/**
	 * @param driver_aadharnumber
	 *            the driver_aadharnumber to set
	 */
	public void setDriver_aadharnumber(String driver_aadharnumber) {
		this.driver_aadharnumber = driver_aadharnumber;
	}

	/**
	 * @return the driver_banknumber
	 */
	public String getDriver_banknumber() {
		return driver_banknumber;
	}

	/**
	 * @param driver_banknumber
	 *            the driver_banknumber to set
	 */
	public void setDriver_banknumber(String driver_banknumber) {
		this.driver_banknumber = driver_banknumber;
	}

	/**
	 * @return the driver_bankname
	 */
	public String getDriver_bankname() {
		return driver_bankname;
	}

	/**
	 * @param driver_bankname
	 *            the driver_bankname to set
	 */
	public void setDriver_bankname(String driver_bankname) {
		this.driver_bankname = driver_bankname;
	}

	/**
	 * @return the driver_bankifsc
	 */
	public String getDriver_bankifsc() {
		return driver_bankifsc;
	}

	/**
	 * @param driver_bankifsc
	 *            the driver_bankifsc to set
	 */
	public void setDriver_bankifsc(String driver_bankifsc) {
		this.driver_bankifsc = driver_bankifsc;
	}

	/**
	 * @return the driver_pannumber
	 */
	public String getDriver_pannumber() {
		return driver_pannumber;
	}

	/**
	 * @param driver_pannumber
	 *            the driver_pannumber to set
	 */
	public void setDriver_pannumber(String driver_pannumber) {
		this.driver_pannumber = driver_pannumber;
	}

	/**
	 * @return the tripSheetID
	 */
	public Long getTripSheetID() {
		return tripSheetID;
	}

	/**
	 * @param tripSheetID
	 *            the tripSheetID to set
	 */
	public void setTripSheetID(Long tripSheetID) {
		this.tripSheetID = tripSheetID;
	}

	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public String getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
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

	public short getDriverStatusId() {
		return driverStatusId;
	}

	public void setDriverStatusId(short driverStatusId) {
		this.driverStatusId = driverStatusId;
	}

	public Integer getDriTraningTypeId() {
		return driTraningTypeId;
	}

	public void setDriTraningTypeId(Integer driTraningTypeId) {
		this.driTraningTypeId = driTraningTypeId;
	}

	public Integer getDriJobId() {
		return driJobId;
	}

	public void setDriJobId(Integer driJobId) {
		this.driJobId = driJobId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastupdatedOn() {
		return lastupdatedOn;
	}

	public void setLastupdatedOn(Date lastupdatedOn) {
		this.lastupdatedOn = lastupdatedOn;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public short getSalariedId() {
		return salariedId;
	}

	public void setSalariedId(short salariedId) {
		this.salariedId = salariedId;
	}

	public String getSalariedStr() {
		return salariedStr;
	}

	public void setSalariedStr(String salariedStr) {
		this.salariedStr = salariedStr;
	}

	public String getDriverFullName() {
		return driverFullName;
	}

	public void setDriverFullName(String driverFullName) {
		this.driverFullName = driverFullName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDlStatus() {
		return dlStatus;
	}

	public void setDlStatus(String dlStatus) {
		this.dlStatus = dlStatus;
	}

	public String getDlImageLink() {
		return dlImageLink;
	}

	public void setDlImageLink(String dlImageLink) {
		this.dlImageLink = dlImageLink;
	}

	public String getConsumptionFuel() {
		return consumptionFuel;
	}

	public void setConsumptionFuel(String consumptionFuel) {
		this.consumptionFuel = consumptionFuel;
	}

	public String getMileageFuel() {
		return mileageFuel;
	}

	public void setMileageFuel(String mileageFuel) {
		this.mileageFuel = mileageFuel;
	}

	public String getBreakDownTotal() {
		return breakDownTotal;
	}

	public void setBreakDownTotal(String breakDownTotal) {
		this.breakDownTotal = breakDownTotal;
	}

	public String getIssueTotal() {
		return issueTotal;
	}

	public void setIssueTotal(String issueTotal) {
		this.issueTotal = issueTotal;
	}

	public String getAccidentTotal() {
		return accidentTotal;
	}

	public void setAccidentTotal(String accidentTotal) {
		this.accidentTotal = accidentTotal;
	}

	public String getCommentTotal() {
		return commentTotal;
	}

	public void setCommentTotal(String commentTotal) {
		this.commentTotal = commentTotal;
	}

	
	public String getDriverJoinDate() {
		return driverJoinDate;
	}

	public void setDriverJoinDate(String driverJoinDate) {
		this.driverJoinDate = driverJoinDate;
	}

	
	public String getDlexpiryDate() {
		return dlexpiryDate;
	}

	public void setDlexpiryDate(String dlexpiryDate) {
		this.dlexpiryDate = dlexpiryDate;
	}

	
	public String getDriverStatusStr() {
		return driverStatusStr;
	}

	public void setDriverStatusStr(String driverStatusStr) {
		this.driverStatusStr = driverStatusStr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + driver_id;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DriverDto other = (DriverDto) obj;
		if (driver_id != other.driver_id)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverDto [driver_id=");
		builder.append(driver_id);
		builder.append(", driver_firstname=");
		builder.append(driver_firstname);
		builder.append(", driver_Lastname=");
		builder.append(driver_Lastname);
		builder.append(", driver_fathername=");
		builder.append(driver_fathername);
		builder.append(", driver_dateofbirth=");
		builder.append(driver_dateofbirth);
		builder.append(", driver_Qualification=");
		builder.append(driver_Qualification);
		builder.append(", driver_bloodgroup=");
		builder.append(driver_bloodgroup);
		builder.append(", driver_languages=");
		builder.append(driver_languages);
		builder.append(", driver_group=");
		builder.append(driver_group);
		builder.append(", driver_perdaySalary=");
		builder.append(driver_perdaySalary);
		builder.append(", driver_esiamount=");
		builder.append(driver_esiamount);
		builder.append(", driver_email=");
		builder.append(driver_email);
		builder.append(", driver_mobnumber=");
		builder.append(driver_mobnumber);
		builder.append(", driver_homenumber=");
		builder.append(driver_homenumber);
		builder.append(", driver_address=");
		builder.append(driver_address);
		builder.append(", driver_address2=");
		builder.append(driver_address2);
		builder.append(", driver_city=");
		builder.append(driver_city);
		builder.append(", driver_state=");
		builder.append(driver_state);
		builder.append(", driver_pincode=");
		builder.append(driver_pincode);
		builder.append(", driver_country=");
		builder.append(driver_country);
		builder.append(", driver_empnumber=");
		builder.append(driver_empnumber);
		builder.append(", driver_insuranceno=");
		builder.append(driver_insuranceno);
		builder.append(", driver_esino=");
		builder.append(driver_esino);
		builder.append(", driver_pfno=");
		builder.append(driver_pfno);
		builder.append(", driver_jobtitle=");
		builder.append(driver_jobtitle);
		builder.append(", driver_trainings=");
		builder.append(driver_trainings);
		builder.append(", driver_startdate=");
		builder.append(driver_startdate);
		builder.append(", driver_leavedate=");
		builder.append(driver_leavedate);
		builder.append(", driver_dlnumber=");
		builder.append(driver_dlnumber);
		builder.append(", driver_badgenumber=");
		builder.append(driver_badgenumber);
		builder.append(", driver_dlclass=");
		builder.append(driver_dlclass);
		builder.append(", driver_dlprovince=");
		builder.append(driver_dlprovince);
		builder.append(", driver_active=");
		builder.append(driver_active);
		builder.append(", driver_authorised=");
		builder.append(driver_authorised);
		builder.append(", driver_dlOriginal=");
		builder.append(driver_dlOriginal);
		builder.append(", driver_reffristname=");
		builder.append(driver_reffristname);
		builder.append(", driver_reflastname=");
		builder.append(driver_reflastname);
		builder.append(", driver_refcontect=");
		builder.append(driver_refcontect);
		builder.append(", driver_photoid=");
		builder.append(driver_photoid);
		builder.append(", driver_aadharnumber=");
		builder.append(driver_aadharnumber);
		builder.append(", driver_pannumber=");
		builder.append(driver_pannumber);
		builder.append(", driver_banknumber=");
		builder.append(driver_banknumber);
		builder.append(", driver_bankname=");
		builder.append(driver_bankname);
		builder.append(", driver_bankifsc=");
		builder.append(driver_bankifsc);
		builder.append(", tripSheetID=");
		builder.append(tripSheetID);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", vehicleGroupId=");
		builder.append(vehicleGroupId);
		builder.append(", driverStatusId=");
		builder.append(driverStatusId);
		builder.append(", driTraningTypeId=");
		builder.append(driTraningTypeId);
		builder.append(", driJobId=");
		builder.append(driJobId);
		builder.append("driverStatusStr=");
		builder.append(driverStatusStr);
		builder.append("]");
		return builder.toString();
	}

}
