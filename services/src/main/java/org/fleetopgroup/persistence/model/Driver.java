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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Driver")
public class Driver implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driver_id")
	private int driver_id;

	@Column(name = "driver_firstname", nullable = false, length = 25)
	private String driver_firstname;

	@Column(name = "driver_Lastname", length = 25)
	private String driver_Lastname;

	@Column(name = "driver_fathername", length = 25)
	private String driver_fathername;

	@Column(name = "driver_dateofbirth", length = 10)
	private String driver_dateofbirth;

	@Column(name = "driver_Qualification", length = 25)
	private String driver_Qualification;

	@Column(name = "driver_bloodgroup", length = 5)
	private String driver_bloodgroup;

	@Column(name = "driver_languages", length = 50)
	private String driver_languages;

	@Column(name = "vehicleGroupId", nullable = false)
	private long vehicleGroupId;

	@Column(name ="driverSalaryTypeId")
	private short driverSalaryTypeId;
	
	@Column(name = "driver_perdaySalary")
	private Double driver_perdaySalary;

	@Column(name = "driver_esiamount")
	private Double driver_esiamount;

	@Column(name = "driver_pfamount")
	private Double driver_pfamount;

	@Column(name = "driver_email", length = 50)
	private String driver_email;

	@Column(name = "driver_mobnumber", length = 15)
	private String driver_mobnumber;

	@Column(name = "driver_homenumber", length = 15)
	private String driver_homenumber;

	@Column(name = "driver_address", length = 100)
	private String driver_address;

	@Column(name = "driver_address2", length = 100)
	private String driver_address2;

	@Column(name = "driver_city", length = 100)
	private String driver_city;

	@Column(name = "driver_state", length = 50)
	private String driver_state;

	@Column(name = "driver_pincode", length = 6)
	private String driver_pincode;

	@Column(name = "driver_country", length = 25)
	private String driver_country;

	@Column(name = "driver_empnumber", length = 25)
	private String driver_empnumber;

	@Column(name = "driver_insuranceno", length = 25)
	private String driver_insuranceno;
	@Column(name = "driver_esino", length = 25)
	private String driver_esino;
	@Column(name = "driver_pfno", length = 25)
	private String driver_pfno;

	@Column(name ="driJobId", nullable = false)
	private Integer driJobId;

	@Column(name = "driver_trainings", length = 75)
	private String driver_trainings;

	@Column(name = "driver_startdate", length = 25)
	private String driver_startdate;
	
	@Column(name = "driverJoinDate")
	private Date driverJoinDate;

	@Column(name = "driver_leavedate", length = 25)
	private String driver_leavedate;

	@Column(name = "driver_dlnumber", length = 25, unique = true, nullable = true)
	private String driver_dlnumber;

	@Column(name = "driver_badgenumber", length = 25)
	private String driver_badgenumber;

	@Column(name = "driver_dlclass", length = 25)
	private String driver_dlclass;

	@Column(name = "driver_dlprovince", length = 25)
	private String driver_dlprovince;

	@Column(name ="driverStatusId", nullable = false)
	private short driverStatusId;
	
	@Column(name = "driver_authorised", length = 25)
	private String driver_authorised;

	@Column(name = "driver_dlOriginal", length = 150)
	private String driver_dlOriginal;

	@Column(name = "driver_reffristname", length = 25)
	private String driver_reffristname;

	@Column(name = "driver_reflastname", length = 25)
	private String driver_reflastname;

	@Column(name = "driver_refcontect", length = 15)
	private String driver_refcontect;

	@Column(name = "driver_photoid")
	private Integer driver_photoid;

	@Column(name = "driver_aadharnumber", length = 50)
	private String driver_aadharnumber;

	@Column(name = "driver_pannumber", length = 50)
	private String driver_pannumber;

	@Column(name = "driver_banknumber", length = 25)
	private String driver_banknumber;

	@Column(name = "driver_bankname", length = 250)
	private String driver_bankname;

	@Column(name = "driver_bankifsc", length = 25)
	private String driver_bankifsc;

	@Column(name = "tripSheetID")
	private Long tripSheetID;
	
	/** The value for the created_ID DATE field */
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	/** The value for the lastUpdated_ID DATE field */
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;
	
	/** The value for the which company this record belongs*/
	@Column(name ="companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "vid")
	private Integer	vid;
	
	/** The value for the mark to delete*/
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "salariedId")
	private short	salariedId;
	
	@Column(name = "remark")
	private String	remark;
	
	

	public Driver() {
		super();
	}

	public Driver(String driver_firstname, String driver_Lastname, String driver_fathername, String driver_dateofbirth,
			String driver_Qualification, String driver_bloodgroup, String driver_languages, 
			String driver_email, String driver_mobnumber, String driver_homenumber, String driver_address,
			String driver_address2, String driver_city, String driver_state, String driver_pincode,
			String driver_country, String driver_empnumber, String driver_insuranceno, String driver_esino,
			String driver_pfno,  String driver_trainings, String driver_startdate,
			String driver_leavedate, String driver_dlnumber, String driver_badgenumber, String driver_dlclass,
			String driver_dlprovince, String driver_authorised, String driver_dlOriginal,
			String driver_reffristname, String driver_reflastname, String driver_refcontect, Integer driver_photoid,
			Long createdById, Long lastModifiedById, boolean MarkForDelete, Date created, Date lastupdated, Integer companyId,
			long vehicleGroupId) {
		super();
		this.driver_firstname = driver_firstname;
		this.driver_Lastname = driver_Lastname;
		this.driver_fathername = driver_fathername;
		this.driver_dateofbirth = driver_dateofbirth;
		this.driver_Qualification = driver_Qualification;
		this.driver_bloodgroup = driver_bloodgroup;
		this.driver_languages = driver_languages;
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
		this.driver_trainings = driver_trainings;
		this.driver_startdate = driver_startdate;
		this.driver_leavedate = driver_leavedate;
		this.driver_dlnumber = driver_dlnumber;
		this.driver_badgenumber = driver_badgenumber;
		this.driver_dlclass = driver_dlclass;
		this.driver_dlprovince = driver_dlprovince;
		this.driver_authorised = driver_authorised;
		this.driver_dlOriginal = driver_dlOriginal;
		this.driver_reffristname = driver_reffristname;
		this.driver_reflastname = driver_reflastname;
		this.driver_refcontect = driver_refcontect;
		this.driver_photoid = driver_photoid;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.markForDelete = MarkForDelete;
		this.created = created;
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


	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
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
		this.driver_perdaySalary = driver_perdaySalary;
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
		this.driver_esiamount = driver_esiamount;
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

	public Double getDriver_pfamount() {
		return driver_pfamount;
	}

	public void setDriver_pfamount(Double driver_pfamount) {
		this.driver_pfamount = driver_pfamount;
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


	public Integer getDriJobId() {
		return driJobId;
	}

	public void setDriJobId(Integer driJobId) {
		this.driJobId = driJobId;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public short getSalariedId() {
		return salariedId;
	}

	public void setSalariedId(short salariedId) {
		this.salariedId = salariedId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}
	
	public Date getDriverJoinDate() {
		return driverJoinDate;
	}

	public void setDriverJoinDate(Date driverJoinDate) {
		this.driverJoinDate = driverJoinDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver_dlnumber == null) ? 0 : driver_dlnumber.hashCode());
		result = prime * result + ((driver_empnumber == null) ? 0 : driver_empnumber.hashCode());
		result = prime * result + ((driver_firstname == null) ? 0 : driver_firstname.hashCode());
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
		Driver other = (Driver) obj;
		if (driver_dlnumber == null) {
			if (other.driver_dlnumber != null)
				return false;
		} else if (!driver_dlnumber.equals(other.driver_dlnumber))
			return false;
		if (driver_empnumber == null) {
			if (other.driver_empnumber != null)
				return false;
		} else if (!driver_empnumber.equals(other.driver_empnumber))
			return false;
		if (driver_firstname == null) {
			if (other.driver_firstname != null)
				return false;
		} else if (!driver_firstname.equals(other.driver_firstname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Driver [driver_id=");
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
		builder.append(", vehicleGroupId=");
		builder.append(vehicleGroupId);
		builder.append(", driverSalaryTypeId=");
		builder.append(driverSalaryTypeId);
		builder.append(", driver_perdaySalary=");
		builder.append(driver_perdaySalary);
		builder.append(", driver_esiamount=");
		builder.append(driver_esiamount);
		builder.append(", driver_pfamount=");
		builder.append(driver_pfamount);
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
		builder.append(", driJobId=");
		builder.append(driJobId);
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
		builder.append(", driverStatusId=");
		builder.append(driverStatusId);
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
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}


}
