package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.DriverAdvanceStatus;
import org.fleetopgroup.constant.DriverAdvanceType;
import org.fleetopgroup.constant.DriverAttandancePointType;
import org.fleetopgroup.constant.DriverAttandanceStatus;
import org.fleetopgroup.constant.DriverFamilyConstant;
import org.fleetopgroup.constant.DriverLedgerTypeConstant;
import org.fleetopgroup.constant.DriverStatus;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverAdvanceDto;
import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.DriverCommentDto;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.DriverFamilyDto;
import org.fleetopgroup.persistence.dto.DriverHaltDto;
import org.fleetopgroup.persistence.dto.DriverPaidAdvanceDto;
import org.fleetopgroup.persistence.dto.DriverSalaryAdvanceDto;
import org.fleetopgroup.persistence.dto.DriverSalaryDto;
import org.fleetopgroup.persistence.dto.TripCollectionSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.model.CashBook;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverAdvance;
import org.fleetopgroup.persistence.model.DriverAttendance;
import org.fleetopgroup.persistence.model.DriverHistory;
import org.fleetopgroup.persistence.model.DriverLedger;
import org.fleetopgroup.persistence.model.DriverPaidAdvance;
import org.fleetopgroup.persistence.model.DriverSalary;
import org.fleetopgroup.persistence.model.DriverSalaryAdvance;
import org.fleetopgroup.persistence.model.DriverTripSheetBalance;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class DriverBL {

	/*
	 * @Autowired private DriverService driverService;
	 */
	@Autowired
	CompanyConfigurationService	companyConfigurationService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	/**
	 * @param esiAmountPer
	 * @param salary
	 * @return
	 */
	private Double PerCentage_Calculation(Double esiAmountPer, Double salary) {
		// Note: Calculation in Total Percentage
		Double percentage = 0.0;

		percentage = ((esiAmountPer * salary) / 100.0);

		return round(percentage, 2);
	}

	public List<VehicleGroup> prepareListofDto(List<VehicleGroup> vehicleGroup) {
		List<VehicleGroup> Dtos = null;
		if (vehicleGroup != null && !vehicleGroup.isEmpty()) {
			Dtos = new ArrayList<VehicleGroup>();
			VehicleGroup Dto = null;
			for (VehicleGroup vehicelGroup : vehicleGroup) {
				Dto = new VehicleGroup();
				Dto.setvGroup(vehicelGroup.getvGroup());
				Dto.setGid(vehicelGroup.getGid());
				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// get logic in driver Information
	public Driver prepareModel(DriverDto driverDto) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Driver driver = new Driver();

		driver.setDriver_id(driverDto.getDriver_id());
		driver.setDriver_firstname(driverDto.getDriver_firstname());
		driver.setDriver_Lastname(driverDto.getDriver_Lastname());
		driver.setDriver_fathername(driverDto.getDriver_fathername());
		driver.setDriver_dateofbirth(driverDto.getDriver_dateofbirth());
		driver.setDriver_Qualification(driverDto.getDriver_Qualification());
		driver.setDriver_bloodgroup(driverDto.getDriver_bloodgroup());
		driver.setDriver_languages(driverDto.getDriver_languages());

		driver.setDriverSalaryTypeId(driverDto.getDriverSalaryTypeId());
		driver.setDriver_perdaySalary(driverDto.getDriver_perdaySalary());
		driver.setDriver_esiamount(driverDto.getDriver_esiamount());
		driver.setDriver_pfamount(driverDto.getDriver_pfamount());

		driver.setDriver_email(driverDto.getDriver_email());
		driver.setDriver_mobnumber(driverDto.getDriver_mobnumber());
		driver.setDriver_homenumber(driverDto.getDriver_homenumber());
		driver.setDriver_insuranceno(driverDto.getDriver_insuranceno());
		driver.setDriver_esino(driverDto.getDriver_esino());
		driver.setDriver_pfno(driverDto.getDriver_pfno());
		driver.setDriver_address(driverDto.getDriver_address());
		driver.setDriver_address2(driverDto.getDriver_address2());
		driver.setDriver_city(driverDto.getDriver_city());
		driver.setDriver_state(driverDto.getDriver_state());
		driver.setDriver_pincode(driverDto.getDriver_pincode());
		driver.setDriver_country(driverDto.getDriver_country());
		driver.setDriver_empnumber(driverDto.getDriver_empnumber());
		// driver.setDriver_jobtitle(driverDto.getDriver_jobtitle());
		// driver.setDriver_trainings(driverDto.getDriver_trainings());
		driver.setDriver_startdate(driverDto.getDriver_startdate());
		driver.setDriver_leavedate(driverDto.getDriver_leavedate());
		driver.setDriver_dlnumber(driverDto.getDriver_dlnumber());

		driver.setDriver_badgenumber(driverDto.getDriver_badgenumber());
		driver.setDriver_dlclass(driverDto.getDriver_dlclass());
		driver.setDriver_dlprovince(driverDto.getDriver_dlprovince());

		// driver.setDriver_active(driverDto.getDriver_active());

		driver.setDriver_authorised(driverDto.getDriver_authorised());
		driver.setDriver_reffristname(driverDto.getDriver_reffristname());
		driver.setDriver_reflastname(driverDto.getDriver_reflastname());
		driver.setDriver_refcontect(driverDto.getDriver_refcontect());

		driver.setDriver_photoid(driverDto.getDriver_photoid());

		driver.setDriver_aadharnumber(driverDto.getDriver_aadharnumber());
		driver.setDriver_bankname(driverDto.getDriver_bankname());
		driver.setDriver_banknumber(driverDto.getDriver_banknumber());
		driver.setDriver_bankifsc(driverDto.getDriver_bankifsc());
		driver.setDriver_pannumber(driverDto.getDriver_pannumber());

		driver.setTripSheetID(driverDto.getTripSheetID());
		driver.setVehicleGroupId(driverDto.getVehicleGroupId());

		/** who Create this vehicle get email id to user profile details */
		driver.setCreatedById(userDetails.getId());
		driver.setLastModifiedById(userDetails.getId());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		driver.setCreated(toDate);
		driver.setLastupdated(toDate);
		driver.setCompanyId(userDetails.getCompany_id());
		driver.setMarkForDelete(false);
		driver.setDriverStatusId(driverDto.getDriverStatusId());
		driver.setDriver_trainings(driverDto.getDriver_trainings());
		driver.setDriJobId(driverDto.getDriJobId());
		driver.setVid(driverDto.getVid());
		return driver;
	}

	// get logic in driver Information
	public Driver prepareModelUpdate(Driver driverDto, DriverDto Olddriver) {
		Driver driver = new Driver();

		driver.setDriver_id(Olddriver.getDriver_id());
		driver.setCompanyId(Olddriver.getCompanyId());
		// Old Driver Trip ID and Photo Values
		driver.setDriver_photoid(Olddriver.getDriver_photoid());
		driver.setTripSheetID(Olddriver.getTripSheetID());
		driver.setVehicleGroupId(driverDto.getVehicleGroupId());
		driver.setVid(driverDto.getVid());

		driver.setDriver_firstname(driverDto.getDriver_firstname());
		driver.setDriver_Lastname(driverDto.getDriver_Lastname());
		driver.setDriver_fathername(driverDto.getDriver_fathername());
		driver.setDriver_dateofbirth(driverDto.getDriver_dateofbirth());
		driver.setDriver_Qualification(driverDto.getDriver_Qualification());
		driver.setDriver_bloodgroup(driverDto.getDriver_bloodgroup());
		driver.setDriver_languages(driverDto.getDriver_languages());

		// driver.setDriver_group(driverDto.getDriver_group());
		driver.setVehicleGroupId(driverDto.getVehicleGroupId());

		driver.setDriverSalaryTypeId(driverDto.getDriverSalaryTypeId());
		driver.setDriver_perdaySalary(driverDto.getDriver_perdaySalary());
		driver.setDriver_esiamount(driverDto.getDriver_esiamount());
		driver.setDriver_pfamount(driverDto.getDriver_pfamount());

		driver.setDriver_email(driverDto.getDriver_email());
		driver.setDriver_mobnumber(driverDto.getDriver_mobnumber());
		driver.setDriver_homenumber(driverDto.getDriver_homenumber());
		driver.setDriver_insuranceno(driverDto.getDriver_insuranceno());
		driver.setDriver_esino(driverDto.getDriver_esino());
		driver.setDriver_pfno(driverDto.getDriver_pfno());
		driver.setDriver_address(driverDto.getDriver_address());
		driver.setDriver_address2(driverDto.getDriver_address2());
		driver.setDriver_city(driverDto.getDriver_city());
		driver.setDriver_state(driverDto.getDriver_state());
		driver.setDriver_pincode(driverDto.getDriver_pincode());
		driver.setDriver_country(driverDto.getDriver_country());
		driver.setDriver_empnumber(driverDto.getDriver_empnumber());
		// driver.setDriver_jobtitle(driverDto.getDriver_jobtitle());
		driver.setDriJobId(driverDto.getDriJobId());

		driver.setDriver_trainings(driverDto.getDriver_trainings());
		driver.setDriver_startdate(driverDto.getDriver_startdate());
		driver.setDriver_leavedate(driverDto.getDriver_leavedate());
		driver.setDriver_dlnumber(driverDto.getDriver_dlnumber());

		driver.setDriver_dlOriginal(driverDto.getDriver_dlOriginal());
		driver.setDriver_badgenumber(driverDto.getDriver_badgenumber());
		driver.setDriver_dlclass(driverDto.getDriver_dlclass());
		driver.setDriver_dlprovince(driverDto.getDriver_dlprovince());

		// driver.setDriver_active(driverDto.getDriver_active());
		driver.setDriverStatusId(driverDto.getDriverStatusId());
		driver.setDriver_authorised(driverDto.getDriver_authorised());
		driver.setDriver_reffristname(driverDto.getDriver_reffristname());
		driver.setDriver_reflastname(driverDto.getDriver_reflastname());
		driver.setDriver_refcontect(driverDto.getDriver_refcontect());

		driver.setDriver_aadharnumber(driverDto.getDriver_aadharnumber());
		driver.setDriver_bankname(driverDto.getDriver_bankname());
		driver.setDriver_banknumber(driverDto.getDriver_banknumber());
		driver.setDriver_bankifsc(driverDto.getDriver_bankifsc());
		driver.setDriver_pannumber(driverDto.getDriver_pannumber());

		/** who Create this vehicle get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		driver.setCreatedById(userDetails.getId());
		driver.setLastModifiedById(userDetails.getId());
		driver.setCompanyId(userDetails.getCompany_id());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		driver.setCreated(Olddriver.getCreatedOn());
		driver.setLastupdated(toDate);

		driver.setMarkForDelete(false);
		driver.setDriJobId(driverDto.getDriJobId());

		return driver;
	}

	/* List of showing driver details */

	public List<DriverDto> prepareListShowDriverDto(List<Driver> drivers) {
		List<DriverDto> Dtos = null;
		if (drivers != null && !drivers.isEmpty()) {
			Dtos = new ArrayList<DriverDto>();
			DriverDto driver = null;
			for (Driver driverDto : drivers) {
				driver = new DriverDto();

				driver.setDriver_id(driverDto.getDriver_id());
				driver.setDriver_empnumber(driverDto.getDriver_empnumber());
				driver.setDriver_firstname(driverDto.getDriver_firstname());
				driver.setDriver_Lastname(driverDto.getDriver_Lastname());
				driver.setDriver_dlnumber(driverDto.getDriver_dlnumber());
				driver.setDriver_badgenumber(driverDto.getDriver_badgenumber());
				// driver.setDriver_group(driverDto.getDriver_group());
				driver.setDriver_email(driverDto.getDriver_email());
				driver.setDriver_mobnumber(driverDto.getDriver_mobnumber());
				driver.setDriver_insuranceno(driverDto.getDriver_insuranceno());
				driver.setDriver_esino(driverDto.getDriver_esino());
				driver.setDriver_pfno(driverDto.getDriver_pfno());
				// driver.setDriver_jobtitle(driverDto.getDriver_jobtitle());
				driver.setDriver_trainings(driverDto.getDriver_trainings());
				// driver.setDriver_active(driverDto.getDriver_active());

				Dtos.add(driver);
			}
		}
		return Dtos;
	}

	/* List of showing driver details */

	public List<DriverDto> prepareList_Driver_mail_show(List<DriverDto> drivers) throws Exception {
		List<DriverDto> Dtos = null;
		if (drivers != null && !drivers.isEmpty()) {
			Dtos = new ArrayList<DriverDto>();
			DriverDto driver = null;
			for (DriverDto driverDto : drivers) {
				driver = new DriverDto();

				driver.setDriver_id(driverDto.getDriver_id());
				driver.setDriver_empnumber(driverDto.getDriver_empnumber());
				driver.setDriver_firstname(driverDto.getDriver_firstname());
				driver.setDriver_Lastname(driverDto.getDriver_Lastname());
				driver.setDriver_dlnumber(driverDto.getDriver_dlnumber());
				driver.setDriver_badgenumber(driverDto.getDriver_badgenumber());
				driver.setDriver_group(driverDto.getDriver_group());
				driver.setDriver_mobnumber(driverDto.getDriver_mobnumber());
				driver.setDriver_jobtitle(driverDto.getDriver_jobtitle());
				driver.setDriver_active(DriverStatus.getDriverStatus(driverDto.getDriverStatusId()));
				driver.setTripSheetID(driverDto.getTripSheetID());
				driver.setTripSheetNumber(driverDto.getTripSheetNumber());
				driver.setDriver_fathername(driverDto.getDriverFullName());

				Dtos.add(driver);
			}
		}
		return Dtos;
	}

	// public List<Driver> prepareListDriverJOB_LC(List<Driver> drivers) {
	// List<Driver> Dtos = null;
	// if (drivers != null && !drivers.isEmpty()) {
	// Dtos = new ArrayList<Driver>();
	// Driver driver = null;
	// for (Driver driverDto : drivers) {
	// driver = new Driver();
	//
	// driver.setDriver_jobtitle(driverDto.getDriver_jobtitle());
	//
	// driver.setDriver_dlclass(driverDto.getDriver_dlclass());
	//
	// Dtos.add(driver);
	// }
	// }
	// return Dtos;
	// }

	/* Driver Show starting page */
	public List<DriverDto> prepareListofDriverDto(List<Driver> drivers) {
		List<DriverDto> Dtos = null;
		if (drivers != null && !drivers.isEmpty()) {
			Dtos = new ArrayList<DriverDto>();
			DriverDto driver = null;
			for (Driver driverDto : drivers) {
				driver = new DriverDto();

				driver.setDriver_id(driverDto.getDriver_id());

				driver.setDriver_firstname(driverDto.getDriver_firstname());
				driver.setDriver_Lastname(driverDto.getDriver_Lastname());
				driver.setDriver_fathername(driverDto.getDriver_fathername());
				driver.setDriver_dateofbirth(driverDto.getDriver_dateofbirth());
				driver.setDriver_Qualification(driverDto.getDriver_Qualification());
				driver.setDriver_bloodgroup(driverDto.getDriver_bloodgroup());
				driver.setDriver_languages(driverDto.getDriver_languages());
				// driver.setDriver_group(driverDto.getDriver_group());
				driver.setDriver_email(driverDto.getDriver_email());
				driver.setDriver_mobnumber(driverDto.getDriver_mobnumber());
				driver.setDriver_homenumber(driverDto.getDriver_homenumber());
				driver.setDriver_insuranceno(driverDto.getDriver_insuranceno());
				driver.setDriver_esino(driverDto.getDriver_esino());
				driver.setDriver_pfno(driverDto.getDriver_pfno());
				driver.setDriver_address(driverDto.getDriver_address());
				driver.setDriver_address2(driverDto.getDriver_address2());
				driver.setDriver_city(driverDto.getDriver_city());
				driver.setDriver_state(driverDto.getDriver_state());
				driver.setDriver_pincode(driverDto.getDriver_pincode());
				driver.setDriver_country(driverDto.getDriver_country());
				driver.setDriver_empnumber(driverDto.getDriver_empnumber());
				// driver.setDriver_jobtitle(driverDto.getDriver_jobtitle());
				driver.setDriver_trainings(driverDto.getDriver_trainings());
				driver.setDriver_startdate(driverDto.getDriver_startdate());
				driver.setDriver_leavedate(driverDto.getDriver_leavedate());
				driver.setDriver_dlnumber(driverDto.getDriver_dlnumber());

				driver.setDriver_badgenumber(driverDto.getDriver_badgenumber());
				driver.setDriver_dlclass(driverDto.getDriver_dlclass());
				driver.setDriver_dlprovince(driverDto.getDriver_dlprovince());

				// driver.setDriver_active(driverDto.getDriver_active());
				driver.setDriver_authorised(driverDto.getDriver_authorised());
				driver.setDriver_dlOriginal(driverDto.getDriver_dlOriginal());

				driver.setDriver_reffristname(driverDto.getDriver_reffristname());
				driver.setDriver_reflastname(driverDto.getDriver_reflastname());
				driver.setDriver_refcontect(driverDto.getDriver_refcontect());

				driver.setDriver_photoid(driverDto.getDriver_photoid());

				driver.setTripSheetID(driverDto.getTripSheetID());

				Dtos.add(driver);
			}
		}
		return Dtos;
	}

	/* Get id from Driver Information 
	public DriverDto GetDriverID(Driver driverDto) {
		DriverDto driver = new DriverDto();

		driver.setDriver_id(driverDto.getDriver_id());

		driver.setDriver_firstname(driverDto.getDriver_firstname());
		driver.setDriver_Lastname(driverDto.getDriver_Lastname());
		driver.setDriver_fathername(driverDto.getDriver_fathername());
		driver.setDriver_dateofbirth(driverDto.getDriver_dateofbirth());
		driver.setDriver_Qualification(driverDto.getDriver_Qualification());
		driver.setDriver_bloodgroup(driverDto.getDriver_bloodgroup());
		driver.setDriver_languages(driverDto.getDriver_languages());

		// driver.setDriver_group(driverDto.getDriver_group());
		driver.setVehicleGroupId(driverDto.getVehicleGroupId());

		driver.setDriver_perdaySalary(driverDto.getDriver_perdaySalary());
		driver.setDriver_esiamount(driverDto.getDriver_esiamount());
		driver.setDriver_pfamount(driverDto.getDriver_pfamount());

		driver.setDriver_email(driverDto.getDriver_email());
		driver.setDriver_mobnumber(driverDto.getDriver_mobnumber());
		driver.setDriver_homenumber(driverDto.getDriver_homenumber());
		driver.setDriver_insuranceno(driverDto.getDriver_insuranceno());
		driver.setDriver_esino(driverDto.getDriver_esino());
		driver.setDriver_pfno(driverDto.getDriver_pfno());
		driver.setDriver_address(driverDto.getDriver_address());
		driver.setDriver_address2(driverDto.getDriver_address2());
		driver.setDriver_city(driverDto.getDriver_city());
		driver.setDriver_state(driverDto.getDriver_state());
		driver.setDriver_pincode(driverDto.getDriver_pincode());
		driver.setDriver_country(driverDto.getDriver_country());
		driver.setDriver_empnumber(driverDto.getDriver_empnumber());
		// driver.setDriver_jobtitle(driverDto.getDriver_jobtitle());
		driver.setDriver_trainings(driverDto.getDriver_trainings());
		driver.setDriver_startdate(driverDto.getDriver_startdate());
		driver.setDriver_leavedate(driverDto.getDriver_leavedate());
		driver.setDriver_dlnumber(driverDto.getDriver_dlnumber());

		driver.setDriver_badgenumber(driverDto.getDriver_badgenumber());
		driver.setDriver_dlclass(driverDto.getDriver_dlclass());
		driver.setDriver_dlprovince(driverDto.getDriver_dlprovince());

		// driver.setDriver_active(driverDto.getDriver_active());
		driver.setDriver_authorised(driverDto.getDriver_authorised());
		driver.setDriver_dlOriginal(driverDto.getDriver_dlOriginal());

		driver.setDriver_reffristname(driverDto.getDriver_reffristname());
		driver.setDriver_reflastname(driverDto.getDriver_reflastname());
		driver.setDriver_refcontect(driverDto.getDriver_refcontect());

		driver.setDriver_photoid(driverDto.getDriver_photoid());

		driver.setDriver_aadharnumber(driverDto.getDriver_aadharnumber());
		driver.setDriver_bankname(driverDto.getDriver_bankname());
		driver.setDriver_banknumber(driverDto.getDriver_banknumber());
		driver.setDriver_bankifsc(driverDto.getDriver_bankifsc());
		driver.setDriver_pannumber(driverDto.getDriver_pannumber());

		driver.setTripSheetID(driverDto.getTripSheetID());

		// Create and Last updated Display
		driver.setCreatedById(driverDto.getCreatedById());
		if (driverDto.getCreated() != null) {
			driver.setCreated(CreatedDateTime.format(driverDto.getCreated()));
		}
		driver.setLastModifiedById(driverDto.getLastModifiedById());
		if (driverDto.getLastupdated() != null) {
			driver.setLastupdated(CreatedDateTime.format(driverDto.getLastupdated()));
		}

		return driver;

	}
*/
	public DriverDto GetDriverID(DriverDto driverDto) throws Exception {
		DriverDto driver = new DriverDto();

		driver.setDriver_id(driverDto.getDriver_id());

		driver.setDriver_firstname(driverDto.getDriver_firstname());
		driver.setDriver_Lastname(driverDto.getDriver_Lastname());
		driver.setDriver_fathername(driverDto.getDriver_fathername());
		driver.setDriver_dateofbirth(driverDto.getDriver_dateofbirth());
		driver.setDriver_Qualification(driverDto.getDriver_Qualification());
		driver.setDriver_bloodgroup(driverDto.getDriver_bloodgroup());
		driver.setDriver_languages(driverDto.getDriver_languages());

		driver.setDriver_group(driverDto.getDriver_group());
		driver.setVehicleGroupId(driverDto.getVehicleGroupId());

		driver.setDriverSalaryType(DriverStatus.getDriverSalaryType(driverDto.getDriverSalaryTypeId()));
		driver.setDriver_perdaySalary(driverDto.getDriver_perdaySalary());
		driver.setDriver_esiamount(driverDto.getDriver_esiamount());
		driver.setDriver_pfamount(driverDto.getDriver_pfamount());

		driver.setDriver_email(driverDto.getDriver_email());
		driver.setDriver_mobnumber(driverDto.getDriver_mobnumber());
		driver.setDriver_homenumber(driverDto.getDriver_homenumber());
		driver.setDriver_insuranceno(driverDto.getDriver_insuranceno());
		driver.setDriver_esino(driverDto.getDriver_esino());
		driver.setDriver_pfno(driverDto.getDriver_pfno());
		driver.setDriver_address(driverDto.getDriver_address());
		driver.setDriver_address2(driverDto.getDriver_address2());
		driver.setDriver_city(driverDto.getDriver_city());
		driver.setDriver_state(driverDto.getDriver_state());
		driver.setDriver_pincode(driverDto.getDriver_pincode());
		driver.setDriver_country(driverDto.getDriver_country());
		driver.setDriver_empnumber(driverDto.getDriver_empnumber());
		driver.setDriver_jobtitle(driverDto.getDriver_jobtitle());
		driver.setDriver_trainings(driverDto.getDriver_trainings());
		driver.setDriver_startdate(driverDto.getDriver_startdate());
		driver.setDriver_leavedate(driverDto.getDriver_leavedate());
		driver.setDriver_dlnumber(driverDto.getDriver_dlnumber());

		driver.setDriver_badgenumber(driverDto.getDriver_badgenumber());
		driver.setDriver_dlclass(driverDto.getDriver_dlclass());
		driver.setDriver_dlprovince(driverDto.getDriver_dlprovince());

		driver.setDriver_active(DriverStatus.getDriverStatus(driverDto.getDriverStatusId()));
		driver.setDriver_authorised(driverDto.getDriver_authorised());
		driver.setDriver_dlOriginal(driverDto.getDriver_dlOriginal());

		driver.setDriver_reffristname(driverDto.getDriver_reffristname());
		driver.setDriver_reflastname(driverDto.getDriver_reflastname());
		driver.setDriver_refcontect(driverDto.getDriver_refcontect());

		driver.setDriver_photoid(driverDto.getDriver_photoid());

		driver.setDriver_aadharnumber(driverDto.getDriver_aadharnumber());
		driver.setDriver_bankname(driverDto.getDriver_bankname());
		driver.setDriver_banknumber(driverDto.getDriver_banknumber());
		driver.setDriver_bankifsc(driverDto.getDriver_bankifsc());
		driver.setDriver_pannumber(driverDto.getDriver_pannumber());

		driver.setTripSheetID(driverDto.getTripSheetID());

		// Create and Last updated Display
		driver.setCreatedBy(driverDto.getCreatedBy());
		if (driverDto.getCreatedOn() != null) {
			driver.setCreated(CreatedDateTime.format(driverDto.getCreatedOn()));
		}
		driver.setLastModifiedBy(driverDto.getLastModifiedBy());
		if (driverDto.getLastupdatedOn() != null) {
			driver.setLastupdated(CreatedDateTime.format(driverDto.getLastupdatedOn()));
		}
		driver.setDriJobId(driverDto.getDriJobId());
		driver.setDriverStatusId(driverDto.getDriverStatusId());
		driver.setVehicle_registration(driverDto.getVehicle_registration());
		driver.setVid(driverDto.getVid());
		driver.setSalariedId(driverDto.getSalariedId());
		driver.setSalariedStr(driverDto.getSalariedStr());
		driver.setRemark(driverDto.getRemark());
		return driver;

	}

	/* Get id from Driver Information */
	public DriverDto Show_Driver_Header_Details(DriverDto driverDto) {
		DriverDto driver = new DriverDto();

		driver.setDriver_id(driverDto.getDriver_id());
		driver.setDriver_firstname(driverDto.getDriver_firstname());
		driver.setDriver_Lastname(driverDto.getDriver_Lastname());
		driver.setDriver_photoid(driverDto.getDriver_photoid());
		driver.setDriver_jobtitle(driverDto.getDriver_jobtitle());
		driver.setDriver_group(driverDto.getDriver_group());
		driver.setDriver_empnumber(driverDto.getDriver_empnumber());
		driver.setDriver_dlnumber(driverDto.getDriver_dlnumber());

		driver.setDriver_esino(driverDto.getDriver_esino());
		driver.setDriver_pfno(driverDto.getDriver_pfno());
		driver.setSalariedId(driverDto.getSalariedId());
		driver.setSalariedStr(driverDto.getSalariedStr());
		driver.setDriverStatusId(driverDto.getDriverStatusId());
		driver.setDriver_badgenumber(driverDto.getDriver_badgenumber());
		return driver;
	}

	// Driver Attendance Collection Details in to Tripsheet
	public DriverAttendance Driver_Attendance_to_tripSheet(int Driver_Id, String Driver_Name, 
			TripSheetDto alldetails, CustomUserDetails userDetails) {
		DriverAttendance attendance = new DriverAttendance();

		attendance.setDRIVERID(Driver_Id);
		// attendance.setDRIVER_NAME(Driver_Name);
		attendance.setTRIPSHEETID(alldetails.getTripSheetID());

		// DRIVER HALT ID
		attendance.setDHID((long) 0);
		// attendance.setTRIP_ROUTE_NAME(alldetails.getRouteName());
		attendance.setTRIP_ROUTE_ID(alldetails.getRouteID());
		attendance.setATTENDANCE_STATUS_ID(DriverAttandanceStatus.DRIVER_ATTANDANCE_STATUS_PASS);
		// attendance.setATTENDANCE_STATUS(DriverAttandanceStatus.DRIVER_ATTANDANCE_STATUS_PASS_NAME);
		attendance.setMarkForDelete(false);

		/** who Create this Issues get email id to user profile details */

		// attendance.setCREATEDBY(userDetails.getEmail());
		// attendance.setLASTUPDATEDBY(userDetails.getEmail());
		attendance.setCREATED_BY_ID(userDetails.getId());
		attendance.setLASTUPDATED_BY_ID(userDetails.getId());

		attendance.setCOMPANY_ID(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		attendance.setLASTUPDATED(toDate);
		attendance.setCREATED(toDate);

		return attendance;
	}

	// Driver Attendance Collection Details in to Tripsheet
	public DriverAttendance Driver_HALT_Attendance_to_tripSheet(int Driver_Id, String Driver_Name,
			TripSheetDto alldetails, CustomUserDetails userDetails) {
		DriverAttendance attendance = new DriverAttendance();

		attendance.setDRIVERID(Driver_Id);
		// attendance.setDRIVER_NAME(Driver_Name);
		attendance.setTRIPSHEETID(alldetails.getTripSheetID());

		// DRIVER HALT ID
		attendance.setDHID((long) 0);
		// attendance.setTRIP_ROUTE_NAME(alldetails.getRouteName());
		attendance.setTRIP_ROUTE_ID(alldetails.getRouteID());
		// attendance.setATTENDANCE_STATUS(DriverAttandanceStatus.DRIVER_ATTANDANCE_STATUS_HPASS_NAME);
		attendance.setATTENDANCE_STATUS_ID(DriverAttandanceStatus.DRIVER_ATTANDANCE_STATUS_HPASS);
		attendance.setMarkForDelete(false);

		// attendance.setCREATEDBY(userDetails.getEmail());
		// attendance.setLASTUPDATEDBY(userDetails.getEmail());
		attendance.setCREATED_BY_ID(userDetails.getId());
		attendance.setLASTUPDATED_BY_ID(userDetails.getId());
		attendance.setCOMPANY_ID(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		attendance.setLASTUPDATED(toDate);
		attendance.setCREATED(toDate);

		return attendance;
	}

	public DriverAttendance Driver_HALT_Attendance_to_DriverHaltTable(int Driver_Id, Long dHID) {
		DriverAttendance attendance = new DriverAttendance();

		attendance.setDRIVERID(Driver_Id);
		// attendance.setDRIVER_NAME(Driver_Name);
		attendance.setTRIPSHEETID((long) 0);

		attendance.setDHID(dHID);
		// attendance.setTRIP_ROUTE_NAME(null);
		attendance.setATTENDANCE_STATUS_ID(DriverAttandanceStatus.DRIVER_ATTANDANCE_STATUS_HPASS);
		// attendance.setATTENDANCE_STATUS(DriverAttandanceStatus.DRIVER_ATTANDANCE_STATUS_HPASS_NAME);
		attendance.setMarkForDelete(false);

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		// attendance.setCREATEDBY(userDetails.getEmail());
		// attendance.setLASTUPDATEDBY(userDetails.getEmail());
		attendance.setCREATED_BY_ID(userDetails.getId());
		attendance.setLASTUPDATED_BY_ID(userDetails.getId());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		attendance.setLASTUPDATED(toDate);
		attendance.setCREATED(toDate);

		return attendance;
	}

	// Driver Attendance Collection Details in to Tripsheet
	public DriverAttendance Driver_Attendance_to_tripCollection(int Driver_Id, String Driver_Name,
			TripCollectionSheetDto alldetails) {
		DriverAttendance attendance = new DriverAttendance();

		attendance.setDRIVERID(Driver_Id);
		// attendance.setDRIVER_NAME(Driver_Name);
		attendance.setTRIPSHEETID(alldetails.getTRIPCOLLID());

		// DRIVER HALT ID
		attendance.setDHID((long) 0);

		// attendance.setTRIP_ROUTE_NAME(alldetails.getTRIP_ROUTE_NAME());
		attendance.setATTENDANCE_STATUS_ID(DriverAttandanceStatus.DRIVER_ATTANDANCE_STATUS_PASS);
		// attendance.setATTENDANCE_STATUS(DriverAttandanceStatus.DRIVER_ATTANDANCE_STATUS_PASS_NAME);
		attendance.setMarkForDelete(false);
		attendance.setTRIP_ROUTE_ID(alldetails.getTRIP_ROUTE_ID());

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		// attendance.setCREATEDBY(userDetails.getEmail());
		// attendance.setLASTUPDATEDBY(userDetails.getEmail());
		attendance.setCREATED_BY_ID(userDetails.getId());
		attendance.setLASTUPDATED_BY_ID(userDetails.getId());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		attendance.setLASTUPDATED(toDate);
		attendance.setCREATED(toDate);

		return attendance;
	}

	public HashMap<String, TripSheet> getTripSheetDriverMap(List<TripSheet> tripSheetList, Integer driverId)
			throws Exception {
		HashMap<String, TripSheet> hashMap = new HashMap<>();
		TripSheet sheet = null;
		try {
			for (TripSheet tripSheet : tripSheetList) {
				sheet = hashMap.get(driverId + "_" + tripSheet.getTripSheetID());
				if (sheet == null) {
					sheet = tripSheet;
				}
				hashMap.put(driverId + "_" + tripSheet.getTripSheetID(), sheet);
			}
			return hashMap;
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	/**
	 * @param advanceDto
	 * @return
	 */
	public DriverAdvance prepare_Driver_Advance(DriverAdvanceDto advanceDto) {

		DriverAdvance advance = new DriverAdvance();

		advance.setTRIP_DRIVER_ID(advanceDto.getTRIP_DRIVER_ID());
		//advance.setTRIP_DRIVER_NAME(advanceDto.getTRIP_DRIVER_NAME());

		advance.setDRIVER_JAMA_BALANCE(advanceDto.getDRIVER_JAMA_BALANCE());
		advance.setDRIVER_ADVANCE_AMOUNT(advanceDto.getDRIVER_ADVANCE_AMOUNT());

	//	advance.setADVANCE_STATUS(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN_NAME);
		advance.setADVANCE_STATUS_ID(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN);
		//advance.setADVANCE_PAID_BY(advanceDto.getADVANCE_PAID_BY());
		advance.setADVANCE_PAID_NUMBER(advanceDto.getADVANCE_PAID_NUMBER());
		advance.setADVANCE_PAID_TYPE_ID(advanceDto.getADVANCE_PAID_TYPE_ID());
		//advance.setADVANCE_PAID_TYPE(PaymentTypeConstant.getPaymentTypeName(advanceDto.getADVANCE_PAID_TYPE_ID()));

		advance.setDRIVER_ADVANCE_REMARK(advanceDto.getDRIVER_ADVANCE_REMARK());

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		//advance.setCREATEDBY(userDetails.getEmail());
		//advance.setLASTMODIFIEDBY(userDetails.getEmail());
		advance.setCOMPANY_ID(userDetails.getCompany_id());
		advance.setCREATED_BY_ID(userDetails.getId());
		advance.setLASTUPDATED_ID(userDetails.getId());
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		advance.setLASTUPDATED(toDate);
		advance.setCREATED(toDate);
		advance.setMarkForDelete(false);
		return advance;
	}

	public List<DriverSalaryAdvanceDto> prepare_DriverASalaryAdvance(List<DriverSalaryAdvance> salaryAdvances) {
		List<DriverSalaryAdvanceDto> Dtos = new ArrayList<DriverSalaryAdvanceDto>();
		DriverSalaryAdvanceDto DA = null;
		if (salaryAdvances != null && !salaryAdvances.isEmpty()) {
			for (DriverSalaryAdvance ATTENDANCE : salaryAdvances) {
				DA = new DriverSalaryAdvanceDto();
				DA.setDSAID(ATTENDANCE.getDSAID());
				DA.setDSANUMBER(ATTENDANCE.getDSANUMBER());
				DA.setADVANCE_NAME_ID(ATTENDANCE.getADVANCE_NAME_ID());
				DA.setADVANCE_NAME(DriverAdvanceType.getAdvanceTypeName(ATTENDANCE.getADVANCE_NAME_ID()));

				if (ATTENDANCE.getADVANCE_DATE() != null) {
					DA.setADVANCE_DATE(dateFormat_Name.format(ATTENDANCE.getADVANCE_DATE()));
				}
				DA.setADVANCE_AMOUNT(ATTENDANCE.getADVANCE_AMOUNT());
				DA.setADVANCE_BALANCE(ATTENDANCE.getADVANCE_BALANCE());
				DA.setADVANCE_STATUS_ID(ATTENDANCE.getADVANCE_STATUS_ID());
				DA.setADVANCE_STATUS(DriverAdvanceStatus.getStausName(ATTENDANCE.getADVANCE_STATUS_ID()));
				DA.setADVANCE_REMARK(ATTENDANCE.getADVANCE_REMARK());
				DA.setTRIPDAILYID(ATTENDANCE.getTRIPDAILYID());
				Dtos.add(DA);
			}
		}
		return Dtos;

	}

	public DriverSalaryAdvanceDto getDriverASalaryAdvance(DriverSalaryAdvance salaryAdvances) {
		DriverSalaryAdvanceDto DA = null;
		if (salaryAdvances != null) {
			DA = new DriverSalaryAdvanceDto();
			DA.setDSAID(salaryAdvances.getDSAID());
			DA.setDSANUMBER(salaryAdvances.getDSANUMBER());
			DA.setADVANCE_NAME_ID(salaryAdvances.getADVANCE_NAME_ID());
			DA.setADVANCE_NAME(DriverAdvanceType.getAdvanceTypeName(salaryAdvances.getADVANCE_NAME_ID()));

			if (salaryAdvances.getADVANCE_DATE() != null) {
				DA.setADVANCE_DATE(dateFormat_Name.format(salaryAdvances.getADVANCE_DATE()));
			}
			DA.setADVANCE_AMOUNT(salaryAdvances.getADVANCE_AMOUNT());
			DA.setADVANCE_BALANCE(salaryAdvances.getADVANCE_BALANCE());
			DA.setADVANCE_STATUS_ID(salaryAdvances.getADVANCE_STATUS_ID());
			DA.setADVANCE_STATUS(DriverAdvanceStatus.getStausName(salaryAdvances.getADVANCE_STATUS_ID()));
			DA.setADVANCE_REMARK(salaryAdvances.getADVANCE_REMARK());
			DA.setTRIPDAILYID(salaryAdvances.getTRIPDAILYID());
		}
		return DA;

	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public List<DriverAttendanceDto> prepare_DriverAttendance_Date_to_Report(List<DriverAttendanceDto> TRIPSHEETPOINT,
			List<DriverAttendanceDto> LocalHALT) {

		List<DriverAttendanceDto> Dtos = new ArrayList<DriverAttendanceDto>();
		DriverAttendanceDto DA = null;
		if (TRIPSHEETPOINT != null && !TRIPSHEETPOINT.isEmpty()) {
			for (DriverAttendanceDto ATTENDANCE : TRIPSHEETPOINT) {
				DA = new DriverAttendanceDto();

				DA.setDRIVERID(ATTENDANCE.getDRIVERID());
				DA.setDRIVER_NAME(ATTENDANCE.getDRIVER_NAME());
				if (ATTENDANCE.getD_ATTENDANCE_DATE() != null) {
					DA.setATTENDANCE_DATE(dateFormat_Name.format(ATTENDANCE.getD_ATTENDANCE_DATE()));
				}
				// driver Group Service
				DA.setDRIVER_GROUP(ATTENDANCE.getCREATEDBY());
				DA.setTRIPSHEETID(ATTENDANCE.getTRIPSHEETID());
				DA.setTRIPSHEETNUMBER(ATTENDANCE.getTRIPSHEETNUMBER());
				if (ATTENDANCE.getCREATED() != null) {
					// tripOpenDate is only display propose show CREATED Halt
					DA.setTRIP_OPEN_DATE(dateFormat_Name.format(ATTENDANCE.getCREATED()));
				}
				if (ATTENDANCE.getLASTUPDATED() != null) {
					// tripCloseDate is only display propose show LASTUPDATED
					// Halt
					DA.setTRIP_CLOSE_DATE(dateFormat_Name.format(ATTENDANCE.getLASTUPDATED()));
				}

				DA.setTRIP_ROUTE_NAME(ATTENDANCE.getTRIP_ROUTE_NAME());
				// vehicle_registration is only display propose POINT_STATUS
				// Halt
				DA.setTRIP_VEHICLE_NAME(ATTENDANCE.getPOINT_STATUS());

				DA.setDRIVER_POINT(ATTENDANCE.getDRIVER_POINT());
				DA.setPOINT_TYPE_ID(ATTENDANCE.getPOINT_TYPE_ID());
				DA.setPOINT_TYPE(DriverAttandancePointType.getPointTypeName(ATTENDANCE.getPOINT_TYPE_ID()));

				Dtos.add(DA);
			}
		}

		if (LocalHALT != null && !LocalHALT.isEmpty()) {
			for (DriverAttendanceDto LocalHalt : LocalHALT) {
				DA = new DriverAttendanceDto();

				DA.setDRIVERID(LocalHalt.getDRIVERID());
				DA.setDRIVER_NAME(LocalHalt.getDRIVER_NAME());
				if (LocalHalt.getATTENDANCE_DATE() != null) {
					DA.setATTENDANCE_DATE(dateFormat_Name.format(LocalHalt.getATTENDANCE_DATE()));
				}
				// driver Group Service
				DA.setDRIVER_GROUP(LocalHalt.getCREATEDBY());
				DA.setTRIPSHEETID(LocalHalt.getTRIPSHEETID());
				DA.setDRIVER_POINT(LocalHalt.getDRIVER_POINT());
				DA.setPOINT_TYPE(LocalHalt.getPOINT_TYPE());
				DA.setPOINT_TYPE_ID(LocalHalt.getPOINT_TYPE_ID());
				DA.setPOINT_TYPE(DriverAttandancePointType.getPointTypeName(LocalHalt.getPOINT_TYPE_ID()));

				Dtos.add(DA);
			}
		}
		return Dtos;
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public Double perpare_Total_DriverAttendance_Point(List<DriverAttendanceDto> TRIPSHEETPOINT,
			List<DriverAttendanceDto> LocalHALT) {

		Double TotalPoint = 0.0;
		if (TRIPSHEETPOINT != null && !TRIPSHEETPOINT.isEmpty()) {
			for (DriverAttendanceDto ATTENDANCE : TRIPSHEETPOINT) {
				if (ATTENDANCE.getDRIVER_POINT() != null) {
					TotalPoint += ATTENDANCE.getDRIVER_POINT();
				}
			}
		}
		if (LocalHALT != null && !LocalHALT.isEmpty()) {
			for (DriverAttendanceDto Local : LocalHALT) {
				if (Local.getDRIVER_POINT() != null) {
					TotalPoint += Local.getDRIVER_POINT();
				}
			}
		}
		return TotalPoint;
	}

	/**
	 * @param advanceCollection
	 * @param driverWiseTripSheetHM 
	 * @return
	 */
	public List<DriverAttendanceDto> prepare_Driver_Group_DriverAttendance_Report(List<DriverAttendanceDto> driverAttendanceList, 
			HashMap<Integer,TripSheetDto> driverWiseTripSheetHM) throws Exception {

		List<DriverAttendanceDto> 		attendanceList 		= null;
		DriverAttendanceDto 			attendance 			= null;
		TripSheetDto					tripSheet			= null;
		Double 							driverTotalSalary 	= 0.0;
		
		try {
			if (driverAttendanceList != null && !driverAttendanceList.isEmpty()) {
				attendanceList = new ArrayList<DriverAttendanceDto>();
				for (DriverAttendanceDto driverAttendance : driverAttendanceList) {
					attendance = new DriverAttendanceDto();
					
					attendance.setDRIVERID(driverAttendance.getDRIVERID());
					attendance.setDRIVER_NAME(driverAttendance.getDRIVER_NAME());
					attendance.setPOINT_TYPE(driverAttendance.getPOINT_TYPE());
					attendance.setPerDaySalary(driverAttendance.getDRIVER_POINT());
					// Here TripSheetId Is Driver Working Total Days
					
					if(driverWiseTripSheetHM != null && driverWiseTripSheetHM.get(driverAttendance.getDRIVERID()) != null) {
						tripSheet	= driverWiseTripSheetHM.get(driverAttendance.getDRIVERID());
						
						attendance.setDriverSalaryTypeId(tripSheet.getDriverSalaryTypeId());
						attendance.setTripSheetCount(tripSheet.getTripSheetCount());
						attendance.setTotalKMUsage(tripSheet.getTotalKMUsage());
					}
					
					if (driverAttendance.getDRIVER_POINT() != null && driverAttendance.getTRIPSHEETID() != 0) {
						// Here Calucation is Total Working Days and Per day Salary
						// Amount To Multiple
						// Driver Total Salary
						if(attendance.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_DAY) {
							
							if(driverAttendance.isIncldriverbalance())
								driverTotalSalary = (driverAttendance.getDRIVER_POINT() * driverAttendance.getTRIPSHEETID()) + driverAttendance.getExpenseAmount();
							else
								driverTotalSalary = driverAttendance.getDRIVER_POINT() * driverAttendance.getTRIPSHEETID();

						} else if(attendance.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_TRIP) {
							driverTotalSalary = driverAttendance.getDRIVER_POINT() * attendance.getTripSheetCount();							
						} else if(attendance.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_KM) {
							driverTotalSalary = driverAttendance.getDRIVER_POINT() * attendance.getTotalKMUsage();														
						}else {
							driverTotalSalary = driverAttendance.getDRIVER_POINT() * driverAttendance.getTRIPSHEETID();
						}
						
					}
					
					// Here Driver Point IN Driver Per day Salary
					attendance.setExpenseAmount(driverAttendance.getExpenseAmount());
					attendance.setDRIVER_POINT(round(driverTotalSalary, 2));
					attendance.setTRIPSHEETID(driverAttendance.getTRIPSHEETID());
					attendanceList.add(attendance);
				}
			}
			return attendanceList;	
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public List<DriverAttendanceDto> prepare_Driver_Depot_DriverAttendance_Report(
			List<DriverAttendanceDto> advanceCollection) {

		List<DriverAttendanceDto> Dtos = null;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto DA = null;
			for (DriverAttendanceDto ATTENDANCE : advanceCollection) {
				DA = new DriverAttendanceDto();

				DA.setDRIVERID(ATTENDANCE.getDRIVERID());
				DA.setDRIVER_NAME(ATTENDANCE.getDRIVER_NAME());

				DA.setPOINT_TYPE(ATTENDANCE.getPOINT_TYPE());

				// Here TripSheetId Is Driver Working Total Days
				DA.setTRIPSHEETID(ATTENDANCE.getTRIPSHEETID());

				Double DriverTotalSalary = 0.0;
				if (ATTENDANCE.getDRIVER_POINT() != null && ATTENDANCE.getTRIPSHEETID() != 0) {

					// Here Calucation is Total Working Days and Per day Salary
					// Amount To Multiple
					// Driver Total Salary
					DriverTotalSalary = ATTENDANCE.getDRIVER_POINT() * ATTENDANCE.getTRIPSHEETID();
				}

				// Here Driver Point IN Driver Per day Salary
				DA.setDRIVER_POINT(round(DriverTotalSalary, 2));

				Dtos.add(DA);
			}
		}
		return Dtos;
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public List<DriverSalary> prepare_Driver_Group_Driver_Report(List<Driver> advanceCollection) {

		List<DriverSalary> Dtos = null;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			Dtos = new ArrayList<DriverSalary>();
			DriverSalary DA = null;
			for (Driver ATTENDANCE : advanceCollection) {
				DA = new DriverSalary();

				DA.setDRIVER_ID(ATTENDANCE.getDriver_id());
				//DA.setDRIVER_FIRSTNAME(ATTENDANCE.getDriver_firstname());
				//DA.setDRIVER_LASTNAME(ATTENDANCE.getDriver_Lastname());
				// DA.setDRIVER_GROUP(ATTENDANCE.getDriver_group());
				//DA.setDRIVER_EMPNUMBER(ATTENDANCE.getDriver_empnumber());

				// Driver ESI Percentage point
				DA.setESIAMOUNT_NO(ATTENDANCE.getDriver_esiamount());

				// Driver PF Percentage point
				DA.setPFAMOUNT_NO(ATTENDANCE.getDriver_pfamount());

				// Here TripSheetId Is Driver Working Total Days

				Double DriverPerDay = 0.0;
				Double EsiAmount = 0.0;
				Double PFAmount = 0.0;
				Double Salary = 0.0;
				Double total = 0.0;
				Double total_Advance = 0.0;

				if (ATTENDANCE.getDriver_perdaySalary() != null && ATTENDANCE.getTripSheetID() != 0) {

					if (ATTENDANCE.getDriver_perdaySalary() != null) {
						DriverPerDay = ATTENDANCE.getDriver_perdaySalary();
					}

					Salary = ATTENDANCE.getTripSheetID() * DriverPerDay;
					if (Salary != 0) {

						if (ATTENDANCE.getDriver_esiamount() != null) {
							Double EsiAmountPer = ATTENDANCE.getDriver_esiamount();

							EsiAmount = PerCentage_Calculation(EsiAmountPer, Salary);
						}

						if (ATTENDANCE.getDriver_pfamount() != null) {
							Double PFAmountPer = ATTENDANCE.getDriver_pfamount();

							PFAmount = PerCentage_Calculation(PFAmountPer, Salary);
						}

						total = Salary - EsiAmount - PFAmount;
					}
				}

				// ATTENDANCE_DATE to showing in TRIPSHEETID
				DA.setTOTAL_WORKINGDAY(ATTENDANCE.getTripSheetID());

				DA.setMONTH_PERDAYSALARY(DriverPerDay);
				// Here Driver Point IN Driver Per day Salary
				DA.setTOTAL_NETSALARY(round(Salary, 2));

				DA.setTOTAL_ESIAMOUNT(EsiAmount);
				DA.setTOTAL_PFAMOUNT(PFAmount);
				DA.setTOTAL_ADVANCE(total_Advance);

				DA.setMONTH_SALARY(total);

				Dtos.add(DA);
			}
		}
		return Dtos;
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public Double perpare_Total_Driver_Group_DriverAttendance_Point(List<DriverAttendanceDto> advanceCollection) {

		Double TotalPoint = 0.0;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			for (DriverAttendanceDto ATTENDANCE : advanceCollection) {
				if (ATTENDANCE.getDRIVER_POINT() != null) {
					TotalPoint += ATTENDANCE.getDRIVER_POINT();
				}
			}
		}
		return Utility.round(TotalPoint, 2);
	}

	/**
	 * @param haltCollection
	 * @return
	 */
	public List<DriverHaltDto> prepare_DriverHalt_DATE_Halt_Report(List<DriverHaltDto> haltCollection) {

		List<DriverHaltDto> Dtos = null;
		if (haltCollection != null && !haltCollection.isEmpty()) {
			Dtos = new ArrayList<DriverHaltDto>();
			DriverHaltDto DriH = null;
			for (DriverHaltDto Halt : haltCollection) {
				DriH = new DriverHaltDto();

				DriH.setDHID(Halt.getDHID());
				DriH.setDRIVERID(Halt.getDRIVERID());
				DriH.setDRIVER_NAME(Halt.getDRIVER_NAME());
				DriH.setHALT_AMOUNT(Halt.getHALT_AMOUNT());

				if (Halt.getHALT_DATE_FROM() != null) {
					DriH.setHALT_DATE_FROM(dateFormat_Name.format(Halt.getHALT_DATE_FROM()));
				}
				if (Halt.getHALT_DATE_TO() != null) {
					DriH.setHALT_DATE_TO(dateFormat_Name.format(Halt.getHALT_DATE_TO()));
				}
				DriH.setVEHICLE_NAME(Halt.getVEHICLE_NAME());
				DriH.setREFERENCE_NO(Halt.getREFERENCE_NO());
				DriH.setHALT_PLACE(Halt.getHALT_PLACE());
				DriH.setHALT_REASON(Halt.getHALT_REASON());
				DriH.setHALT_PAIDBY(Halt.getHALT_PAIDBY());
				/*
				 * This Trip Route is Driver Group to Showing Only Route name In
				 */
				DriH.setTRIP_ROUTE_NAME(Halt.getTRIP_ROUTE_NAME());
				/* This Driver driver_jobtitle To Show in only CreatedBy In */
				DriH.setCREATEDBY(Halt.getCREATEDBY());

				Dtos.add(DriH);
			}
		}
		return Dtos;
	}

	/**
	 * @param haltCollection
	 * @return
	 */
	public Double perpare_Total_DriverHalt_DATE_Halt(List<DriverHaltDto> haltCollection) {

		Double HALT_AMOUNT = 0.0;
		if (haltCollection != null && !haltCollection.isEmpty()) {
			for (DriverHaltDto Halt : haltCollection) {
				if (Halt.getHALT_AMOUNT() != null) {
					HALT_AMOUNT += Halt.getHALT_AMOUNT();
				}
			}
		}
		return Utility.round(HALT_AMOUNT, 2);
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public Object prepare_DriverAttendance_to_Report(List<DriverAttendanceDto> TRIPSHEETPOINT) {

		List<DriverAttendanceDto> Dtos = null;

		if (TRIPSHEETPOINT != null && !TRIPSHEETPOINT.isEmpty()) {
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto DA = null;
			for (DriverAttendanceDto ATTENDANCE : TRIPSHEETPOINT) {
				DA = new DriverAttendanceDto();

				DA.setDRIVERID(ATTENDANCE.getDRIVERID());
				DA.setDRIVER_NAME(ATTENDANCE.getDRIVER_NAME());
				if (ATTENDANCE.getATTENDANCE_DATE() != null) {
					DA.setATTENDANCE_DATE(dateFormat_Name.format(ATTENDANCE.getATTENDANCE_DATE()));
				}
				// driver Group Service
				DA.setDRIVER_GROUP(ATTENDANCE.getCREATEDBY());
				DA.setTRIPSHEETID(ATTENDANCE.getTRIPSHEETID());
				if (ATTENDANCE.getCREATED() != null) {
					// tripOpenDate is only display propose show CREATED Halt
					DA.setTRIP_OPEN_DATE(dateFormat_Name.format(ATTENDANCE.getCREATED()));
				}
				if (ATTENDANCE.getLASTUPDATED() != null) {
					// tripCloseDate is only display propose show LASTUPDATED
					// Halt
					DA.setTRIP_CLOSE_DATE(dateFormat_Name.format(ATTENDANCE.getLASTUPDATED()));
				}

				DA.setTRIP_ROUTE_NAME(ATTENDANCE.getTRIP_ROUTE_NAME());
				// vehicle_registration is only display propose TRIP_ROUTE_NAME
				// Halt
				DA.setTRIP_VEHICLE_NAME(ATTENDANCE.getPOINT_STATUS());

				DA.setDRIVER_POINT(ATTENDANCE.getDRIVER_POINT());
				DA.setPOINT_TYPE(ATTENDANCE.getPOINT_TYPE());

				Dtos.add(DA);
			}
		}

		return Dtos;
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public List<DriverAttendanceDto> prepare_Driver_Group_DriverAttendance_POINT_Report(
			List<DriverAttendanceDto> advanceCollection) {

		List<DriverAttendanceDto> Dtos = null;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto DA = null;
			for (DriverAttendanceDto ATTENDANCE : advanceCollection) {
				DA = new DriverAttendanceDto();

				DA.setDRIVERID(ATTENDANCE.getDRIVERID());
				DA.setDRIVER_NAME(ATTENDANCE.getDRIVER_NAME());

				DA.setPOINT_TYPE(ATTENDANCE.getPOINT_TYPE());
				DA.setDRIVER_POINT(ATTENDANCE.getDRIVER_POINT());

				DA.setTRIPSHEETID(ATTENDANCE.getTRIPSHEETID());

				Dtos.add(DA);
			}
		}
		return Dtos;
	}

	/**
	 * @param list_Of_DriverID_to_DriverFamily
	 * @return
	 */
	public List<DriverFamilyDto> Show_Driver_Family_list(List<DriverFamilyDto> DriverFamilyList) {

		List<DriverFamilyDto> Dtos = null;
		if (DriverFamilyList != null && !DriverFamilyList.isEmpty()) {
			Dtos = new ArrayList<DriverFamilyDto>();
			DriverFamilyDto DA = null;
			for (DriverFamilyDto FAMILY : DriverFamilyList) {
				DA = new DriverFamilyDto();

				DA.setDFID(FAMILY.getDFID());
				DA.setDRIVERID(FAMILY.getDRIVERID());
				DA.setDF_NAME(FAMILY.getDF_NAME());

				DA.setDF_SEX(DriverFamilyConstant.getDriverFamilySexName(FAMILY.getDF_SEX_ID()));
				DA.setDF_AGE(FAMILY.getDF_AGE());

				DA.setDF_RELATIONSHIP(DriverFamilyConstant.getDFRELATIONName(FAMILY.getDF_RELATIONSHIP_ID()));

				Dtos.add(DA);
			}
		}
		return Dtos;
	}

	/**
	 * @param list_Of_DriverID_to_Driver_BATA_Details
	 * @return
	 */
	public List<TripSheetDto> Show_Driver_BataDetails_ToTripsheet(List<TripSheetDto> TSDto,
			List<DriverHaltDto> DriHalt) {

		List<TripSheetDto> Dtos = new ArrayList<TripSheetDto>();
		TripSheetDto Bata = null;
		if (TSDto != null && !TSDto.isEmpty()) {

			for (TripSheetDto TripBata : TSDto) {
				Bata = new TripSheetDto();

				Bata.setTripSheetID(TripBata.getTripSheetID());
				Bata.setTripSheetNumber(TripBata.getTripSheetNumber());
				Bata.setVehicle_registration(TripBata.getVehicle_registration());

				Bata.setRouteName(TripBata.getRouteName());

				if (TripBata.getTripOpenDate() != null) {
					Bata.setTripOpenDate(TripBata.getTripOpenDate());
				}
				if (TripBata.getClosetripDate() != null) {
					Bata.setClosetripDate(TripBata.getClosetripDate());
				}

				Double FristDriverBata = 0.0;
				if (TripBata.getTripFristDriverBata() != null && !(TripBata.getTripFristDriverBata().equals(0.0))) {

					FristDriverBata = TripBata.getTripFristDriverBata();

				} else if (TripBata.getTripSecDriverBata() != null && !(TripBata.getTripSecDriverBata().equals(0.0))) {

					FristDriverBata = TripBata.getTripSecDriverBata();

				} else if (TripBata.getTripCleanerBata() != null && !(TripBata.getTripCleanerBata().equals(0.0))) {

					FristDriverBata = TripBata.getTripCleanerBata();
				}

				Bata.setTripFristDriverBata(FristDriverBata);

				Dtos.add(Bata);
			}
		}

		if (DriHalt != null && !DriHalt.isEmpty()) {
			for (DriverHaltDto DriverHaltDto : DriHalt) {

				Bata = new TripSheetDto();

				Bata.setTripSheetID(DriverHaltDto.getTRIPSHEETID());
				Bata.setRouteName(DriverHaltDto.getHALT_REASON());
				Bata.setVehicle_registration(DriverHaltDto.getVEHICLE_NAME());

				Bata.setRouteName(DriverHaltDto.getHALT_REASON());

				if (DriverHaltDto.getHALT_DATE_FROM() != null) {
					Bata.setTripOpenDate(DriverHaltDto.getHALT_DATE_FROM());
				}
				if (DriverHaltDto.getHALT_DATE_TO() != null) {
					Bata.setClosetripDate(DriverHaltDto.getHALT_DATE_TO());
				}

				Double FristDriverBata = 0.0;
				if (DriverHaltDto.getHALT_AMOUNT() != null && !(DriverHaltDto.getHALT_AMOUNT().equals(0.0))) {

					FristDriverBata = DriverHaltDto.getHALT_AMOUNT();

				}

				Bata.setTripFristDriverBata(FristDriverBata);
				
				Bata.setCloseTripStatusId(DriverHaltDto.getHALT_PLACE_TYPE_ID());

				Dtos.add(Bata);
			}
		}

		return Dtos;
	}

	/**
	 * @param tripSheet
	 * @param driverHaltVali
	 * @return
	 */
	public Double Total_BataDetails_ToTripsheet(List<TripSheetDto> tripSheet, List<DriverHaltDto> driverHaltVali) {
		Double TotalBata = 0.0;
		if (tripSheet != null && !tripSheet.isEmpty()) {
			for (TripSheetDto TripBata : tripSheet) {

				Double FristDriverBata = 0.0;
				if (TripBata.getTripFristDriverBata() != null && !(TripBata.getTripFristDriverBata().equals(0.0))) {

					FristDriverBata = TripBata.getTripFristDriverBata();

				} else if (TripBata.getTripSecDriverBata() != null && !(TripBata.getTripSecDriverBata().equals(0.0))) {

					FristDriverBata = TripBata.getTripSecDriverBata();

				} else if (TripBata.getTripCleanerBata() != null && !(TripBata.getTripCleanerBata().equals(0.0))) {

					FristDriverBata = TripBata.getTripCleanerBata();
				}

				TotalBata += FristDriverBata;

			}
		}

		if (driverHaltVali != null && !driverHaltVali.isEmpty()) {
			for (DriverHaltDto DriverHaltDto : driverHaltVali) {

				Double FristDriverBataHalt = 0.0;
				if (DriverHaltDto.getHALT_AMOUNT() != null && !(DriverHaltDto.getHALT_AMOUNT().equals(0.0))) {

					FristDriverBataHalt = DriverHaltDto.getHALT_AMOUNT();

				}
				TotalBata += FristDriverBataHalt;

			}
		}

		return TotalBata;
	}

	/*******************************************************************/
	/*********** SRI DURGAMBA CASHBOOK LOGIC TRIP DAILY ***************/
	/*****************************************************************/

	// Driver Attendance Collection Details in to Tripsheet
	public DriverAttendance Driver_Attendance_to_tripDaily(int Driver_Id, String Driver_Name, Long TRIPDAILYID,
			Integer TRIP_ROUTE_ID) {
		DriverAttendance attendance = new DriverAttendance();

		attendance.setDRIVERID(Driver_Id);
		// attendance.setDRIVER_NAME(Driver_Name);
		attendance.setTRIPSHEETID(TRIPDAILYID);
		
		attendance.setDHID((long) 0);
		attendance.setTRIP_ROUTE_ID(TRIP_ROUTE_ID);
		attendance.setATTENDANCE_STATUS_ID(DriverAttandanceStatus.DRIVER_ATTANDANCE_STATUS_PASS);
		// attendance.setATTENDANCE_STATUS(DriverAttandanceStatus.DRIVER_ATTANDANCE_STATUS_PASS_NAME);
		attendance.setMarkForDelete(false);

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		// attendance.setCREATEDBY(userDetails.getEmail());
		// attendance.setLASTUPDATEDBY(userDetails.getEmail());
		attendance.setCREATED_BY_ID(userDetails.getId());
		attendance.setLASTUPDATED_BY_ID(userDetails.getId());

		attendance.setCOMPANY_ID(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		attendance.setLASTUPDATED(toDate);
		attendance.setCREATED(toDate);

		return attendance;
	}

	/**
	 * @param salary
	 * @return
	 */
	public List<DriverSalaryDto> Driver_Salary_ESI_PF_Report(List<DriverSalaryDto> salary) {

		List<DriverSalaryDto> Dtos = new ArrayList<DriverSalaryDto>();
		DriverSalaryDto Bata = null;
		if (salary != null && !salary.isEmpty()) {

			for (DriverSalaryDto DSalary : salary) {
				Bata = new DriverSalaryDto();

				Bata.setDSID(DSalary.getDSID());
				Bata.setDRIVER_ID(DSalary.getDRIVER_ID());
				Bata.setDRIVER_FIRSTNAME(DSalary.getDRIVER_FIRSTNAME());
				Bata.setDRIVER_LASTNAME(DSalary.getDRIVER_LASTNAME());
				Bata.setDRIVER_EMPNUMBER(DSalary.getDRIVER_EMPNUMBER());
				Bata.setDRIVER_GROUP(DSalary.getDRIVER_GROUP());
				Bata.setESIAMOUNT_NO(round(DSalary.getESIAMOUNT_NO(), 2));
				Bata.setMONTH_PERDAYSALARY(round(DSalary.getMONTH_PERDAYSALARY(), 2));

				Bata.setMONTH_SALARY(round(DSalary.getMONTH_SALARY(), 2));
				Bata.setMONTH_EXTRA_SALARY(round(DSalary.getMONTH_EXTRA_SALARY(), 2));
				
				Bata.setDRIVER_ESINO(DSalary.getDRIVER_ESINO());
				Bata.setDRIVER_PFNO(DSalary.getDRIVER_PFNO());
				Bata.setDRIVER_BANKNUMBER(DSalary.getDRIVER_BANKNUMBER());

				Bata.setPFAMOUNT_NO(round(DSalary.getPFAMOUNT_NO(), 2));
				if (DSalary.getSALARY_FROM_DATE_ON() != null) {
					Bata.setSALARY_FROM_DATE(dateFormat_Name.format(DSalary.getSALARY_FROM_DATE_ON()));
				}
				if (DSalary.getSALARY_TO_DATE_ON() != null) {
					Bata.setSALARY_TO_DATE(dateFormat_Name.format(DSalary.getSALARY_TO_DATE_ON()));
				}

				Double TOTAL_PREVIOUS_ADVANCE = 0.0;
				Double TOTAL_ADVANCE = 0.0;
				Double TOTAL_PENALTY = 0.0;
				Double TOTAL_ADVANCE_PENALTY = 0.0;
				Double TOTAL_ADVANCE_BALANCE = 0.0;

				Double TOTAL_ADVANCE_DEDUCTION = 0.0;
				Double TOTAL_PENALTY_DEDUCTION = 0.0;

				Double TOTAL_ALLOWONCE = 0.0;
				Double TOTAL_OTHEREXTRA = 0.0;

				if (DSalary.getTOTAL_PREVIOUS_ADVANCE() != null) {
					TOTAL_PREVIOUS_ADVANCE = DSalary.getTOTAL_PREVIOUS_ADVANCE();
				}

				if (DSalary.getTOTAL_ADVANCE() != null) {
					TOTAL_ADVANCE = DSalary.getTOTAL_ADVANCE();
				}

				if (DSalary.getTOTAL_PENALTY() != null) {
					TOTAL_PENALTY = DSalary.getTOTAL_PENALTY();
				}

				if (DSalary.getTOTAL_ADVANCE_PENALTY() != null) {
					TOTAL_ADVANCE_PENALTY = DSalary.getTOTAL_ADVANCE_PENALTY();
				}

				if (DSalary.getTOTAL_ADVANCE_BALANCE() != null) {
					TOTAL_ADVANCE_BALANCE = DSalary.getTOTAL_ADVANCE_BALANCE();
				}

				if (DSalary.getTOTAL_ADVANCE_DEDUCTION() != null) {
					TOTAL_ADVANCE_DEDUCTION = DSalary.getTOTAL_ADVANCE_DEDUCTION();
				}

				if (DSalary.getTOTAL_PENALTY_DEDUCTION() != null) {
					TOTAL_PENALTY_DEDUCTION = DSalary.getTOTAL_PENALTY_DEDUCTION();
				}

				if (DSalary.getTOTAL_ALLOWANCE() != null) {
					TOTAL_ALLOWONCE = DSalary.getTOTAL_ALLOWANCE();
				}

				if (DSalary.getTOTAL_OTHEREXTRA() != null) {
					TOTAL_OTHEREXTRA = DSalary.getTOTAL_OTHEREXTRA();
				}

				Bata.setTOTAL_PREVIOUS_ADVANCE(round(TOTAL_PREVIOUS_ADVANCE, 2));
				Bata.setTOTAL_ADVANCE(round(TOTAL_ADVANCE, 2));
				Bata.setTOTAL_PENALTY(round(TOTAL_PENALTY, 2));

				Bata.setTOTAL_ADVANCE_DEDUCTION(round(TOTAL_ADVANCE_DEDUCTION, 2));
				Bata.setTOTAL_PENALTY_DEDUCTION(round(TOTAL_PENALTY_DEDUCTION, 2));

				Bata.setTOTAL_ADVANCE_PENALTY(round(TOTAL_ADVANCE_PENALTY, 2));

				Bata.setTOTAL_ADVANCE_BALANCE(round(TOTAL_ADVANCE_BALANCE, 2));

				Bata.setTOTAL_ESIAMOUNT(round(DSalary.getTOTAL_ESIAMOUNT(), 2));
				Bata.setTOTAL_PFAMOUNT(round(DSalary.getTOTAL_PFAMOUNT(), 2));

				Bata.setTOTAL_NETSALARY(round(DSalary.getTOTAL_NETSALARY(), 2));
				Bata.setTOTAL_EXTRA_NETSALARY(round(DSalary.getTOTAL_EXTRA_NETSALARY(), 2));

				Bata.setTOTAL_ALLOWANCE(round(TOTAL_ALLOWONCE, 2));
				Bata.setTOTAL_OTHEREXTRA(round(TOTAL_OTHEREXTRA, 2));

				Bata.setTOTAL_WORKINGDAY(DSalary.getTOTAL_WORKINGDAY());
				Bata.setTOTAL_EXTRA_WORKINGDAY(DSalary.getTOTAL_EXTRA_WORKINGDAY());

				Bata.setTOTAL_HANDSALARY(DSalary.getTOTAL_HANDSALARY());

				Bata.setSALARY_STATUS_ID(DSalary.getSALARY_STATUS_ID());
				Bata.setSALARY_STATUS(DriverAdvanceStatus.getStausName(DSalary.getSALARY_STATUS_ID()));

				if (DSalary.getLASTUPDATED_DATE_ON() != null) {
					Bata.setLASTUPDATED_DATE(dateFormat_Name.format(DSalary.getLASTUPDATED_DATE_ON()));
				}
				Bata.setLASTMODIFIEDBY(DSalary.getLASTMODIFIEDBY());
				if (DSalary.getCREATED_DATE_ON() != null) {
					Bata.setCREATED_DATE(dateFormat_Name.format(DSalary.getCREATED_DATE_ON()));
				}
				Bata.setCREATEDBY(DSalary.getCREATEDBY());
				Bata.setmarkForDelete(DSalary.ismarkForDelete());

				Dtos.add(Bata);
			}
		}
		return Dtos;
	}

	/**
	 * @param salary
	 * @return
	 */
	public Double[] Tota_TripSalary_Sheet_total(List<DriverSalaryDto> salary) {

		Double[] totalCollection = new Double[15];

		Double TotalWorkingDate = 0.0, TotalMONTH_SALARY = 0.0, TotalESI = 0.0, TotalPF = 0.0,
				TotalPreviousAdvance = 0.0, TotalPenalty = 0.0, TotalExtraDuty = 0.0, TotalExtraMONTH_SALARY = 0.0,
				TotalAdvanceDeduction = 0.0, TotalPenaltyDeduction = 0.0, TOTAL_ALLOWANCE = 0.0, TOTAL_OTHEREXTRA = 0.0,
				TotalNetPay = 0.0, TotalExtraNetPay = 0.0, TotalAdvanceBalance = 0.0;
		if (salary != null && !salary.isEmpty()) {
			for (DriverSalaryDto tripGroupCollection : salary) {

				if (tripGroupCollection.getTOTAL_WORKINGDAY() != null) {
					TotalWorkingDate += tripGroupCollection.getTOTAL_WORKINGDAY();
				}
				if (tripGroupCollection.getMONTH_SALARY() != null) {
					TotalMONTH_SALARY += tripGroupCollection.getMONTH_SALARY();
				}

				if (tripGroupCollection.getTOTAL_ESIAMOUNT() != null) {
					TotalESI += tripGroupCollection.getTOTAL_ESIAMOUNT();
				}
				if (tripGroupCollection.getTOTAL_PFAMOUNT() != null) {
					TotalPF += tripGroupCollection.getTOTAL_PFAMOUNT();
				}
				if (tripGroupCollection.getTOTAL_PREVIOUS_ADVANCE() != null) {
					TotalPreviousAdvance += tripGroupCollection.getTOTAL_PREVIOUS_ADVANCE();
				}
				if (tripGroupCollection.getTOTAL_PENALTY() != null) {
					TotalPenalty += tripGroupCollection.getTOTAL_PENALTY();
				}
				if (tripGroupCollection.getTOTAL_EXTRA_WORKINGDAY() != null) {
					TotalExtraDuty += tripGroupCollection.getTOTAL_EXTRA_WORKINGDAY();
				}
				if (tripGroupCollection.getMONTH_EXTRA_SALARY() != null) {
					TotalExtraMONTH_SALARY += tripGroupCollection.getMONTH_EXTRA_SALARY();
				}
				if (tripGroupCollection.getTOTAL_ADVANCE_DEDUCTION() != null) {
					TotalAdvanceDeduction += tripGroupCollection.getTOTAL_ADVANCE_DEDUCTION();
				}

				if (tripGroupCollection.getTOTAL_PENALTY_DEDUCTION() != null) {
					TotalPenaltyDeduction += tripGroupCollection.getTOTAL_PENALTY_DEDUCTION();
				}

				if (tripGroupCollection.getTOTAL_ALLOWANCE() != null) {
					TOTAL_ALLOWANCE += tripGroupCollection.getTOTAL_ALLOWANCE();
				}

				if (tripGroupCollection.getTOTAL_OTHEREXTRA() != null) {
					TOTAL_OTHEREXTRA += tripGroupCollection.getTOTAL_OTHEREXTRA();
				}

				if (tripGroupCollection.getTOTAL_NETSALARY() != null) {
					TotalNetPay += tripGroupCollection.getTOTAL_NETSALARY();
				}

				if (tripGroupCollection.getTOTAL_EXTRA_NETSALARY() != null) {
					TotalExtraNetPay += tripGroupCollection.getTOTAL_EXTRA_NETSALARY();
				}

				if (tripGroupCollection.getTOTAL_ADVANCE_BALANCE() != null) {
					TotalAdvanceBalance += tripGroupCollection.getTOTAL_ADVANCE_BALANCE();
				}

			}
		}

		totalCollection[0] = round(TotalWorkingDate, 2);
		totalCollection[1] = round(TotalMONTH_SALARY, 2);
		totalCollection[2] = round(TotalPF, 2);
		totalCollection[3] = round(TotalESI, 2);

		totalCollection[4] = round(TotalExtraDuty, 2);
		totalCollection[5] = round(TotalExtraMONTH_SALARY, 2);

		totalCollection[6] = round(TotalPreviousAdvance, 2);

		totalCollection[7] = round(TotalPenalty, 2);
		totalCollection[8] = round(TotalAdvanceDeduction, 2);

		totalCollection[9] = round(TotalPenaltyDeduction, 2);

		totalCollection[10] = round(TOTAL_ALLOWANCE, 2);
		totalCollection[11] = round(TOTAL_OTHEREXTRA, 2);

		totalCollection[12] = round(TotalNetPay, 2);
		totalCollection[13] = round(TotalExtraNetPay, 2);
		totalCollection[14] = round(TotalAdvanceBalance, 2);

		return totalCollection;
	}

	public DriverSalaryAdvance prepare_DriverSalary_Advance(DriverSalaryAdvanceDto advanceDto) {

		DriverSalaryAdvance advance = new DriverSalaryAdvance();

		advance.setDRIVER_ID(advanceDto.getDRIVER_ID());

		advance.setADVANCE_BALANCE(advanceDto.getADVANCE_AMOUNT());
		advance.setADVANCE_AMOUNT(advanceDto.getADVANCE_AMOUNT());

		advance.setTRIPDAILYID((long) 0);

		advance.setADVANCE_NAME_ID(DriverAdvanceType.DRIVER_ADVANCE_TYPE_ADVANCE);
		//advance.setADVANCE_NAME(DriverAdvanceType.DRIVER_ADVANCE_TYPE_ADVANCE_NAME);

		advance.setADVANCE_STATUS_ID(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN);
		//advance.setADVANCE_STATUS(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN_NAME);
	//	advance.setADVANCE_PAID_BY(advanceDto.getADVANCE_PAID_BY());
		advance.setADVANCE_PAID_NUMBER(advanceDto.getADVANCE_PAID_NUMBER());

		advance.setADVANCE_PAID_TYPE_ID(advanceDto.getADVANCE_PAID_TYPE_ID());
	//	advance.setADVANCE_PAID_TYPE(PaymentTypeConstant.getPaymentTypeName(advanceDto.getADVANCE_PAID_TYPE_ID()));

		advance.setADVANCE_REMARK(advanceDto.getADVANCE_REMARK());

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		//advance.setCREATEDBY(userDetails.getEmail());
		advance.setCREATED_BY_ID(userDetails.getId());
	//	advance.setLASTMODIFIEDBY(userDetails.getEmail());
		advance.setLASTMODIFIED_BY_ID(userDetails.getId());
		advance.setCOMPANY_ID(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		advance.setLASTUPDATED(toDate);
		advance.setCREATED(toDate);
		advance.setmarkForDelete(false);
		advance.setDRIVER_ADVANCE_TYPE_ID(advanceDto.getDRIVER_ADVANCE_TYPE_ID());		
		return advance;
	}

	public DriverSalaryAdvance prepare_DriverSalary_Advance_TRIPDAILY_SHEET(DriverSalaryAdvanceDto advanceDto) {
		DriverSalaryAdvance advance = new DriverSalaryAdvance();

		advance.setDRIVER_ID(advanceDto.getDRIVER_ID());

		advance.setADVANCE_BALANCE(advanceDto.getADVANCE_AMOUNT());
		advance.setADVANCE_AMOUNT(advanceDto.getADVANCE_AMOUNT());

		advance.setTRIPDAILYID(advanceDto.getTRIPDAILYID());
		advance.setADVANCE_NAME_ID(DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY);
		//advance.setADVANCE_NAME(DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY_NAME);

		//advance.setADVANCE_STATUS(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN_NAME);
		advance.setADVANCE_STATUS_ID(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN);

		//advance.setADVANCE_PAID_BY(advanceDto.getADVANCE_PAID_BY());
		advance.setADVANCE_PAID_NUMBER(advanceDto.getADVANCE_PAID_NUMBER());
		advance.setADVANCE_PAID_TYPE_ID(advanceDto.getADVANCE_PAID_TYPE_ID());
	//	advance.setADVANCE_PAID_TYPE(PaymentTypeConstant.getPaymentTypeName(advanceDto.getADVANCE_PAID_TYPE_ID()));

		advance.setADVANCE_REMARK(advanceDto.getADVANCE_REMARK());

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		//advance.setCREATEDBY(userDetails.getEmail());
		//advance.setLASTMODIFIEDBY(userDetails.getEmail());
		advance.setCOMPANY_ID(userDetails.getCompany_id());

		advance.setCREATED_BY_ID(userDetails.getId());
		advance.setLASTMODIFIED_BY_ID(userDetails.getId());
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		advance.setLASTUPDATED(toDate);
		advance.setCREATED(toDate);
		advance.setmarkForDelete(false);

		return advance;
	}

	public DriverSalaryDto Driver_Salary_DETAILS_BY_ID(DriverSalaryDto DSalary) {

		DriverSalaryDto Bata = null;
		if (DSalary != null) {

			Bata = new DriverSalaryDto();

			Bata.setDSID(DSalary.getDSID());
			Bata.setDRIVER_ID(DSalary.getDRIVER_ID());
			Bata.setDRIVER_FIRSTNAME(DSalary.getDRIVER_FIRSTNAME());
			Bata.setDRIVER_LASTNAME(DSalary.getDRIVER_LASTNAME());
			Bata.setDRIVER_EMPNUMBER(DSalary.getDRIVER_EMPNUMBER());
			Bata.setDRIVER_GROUP(DSalary.getDRIVER_GROUP());
			if (DSalary.getESIAMOUNT_NO() != null)
				Bata.setESIAMOUNT_NO(round(DSalary.getESIAMOUNT_NO(), 2));
			Bata.setMONTH_PERDAYSALARY(round(DSalary.getMONTH_PERDAYSALARY(), 2));

			Bata.setMONTH_SALARY(round(DSalary.getMONTH_SALARY(), 2));
			Bata.setMONTH_EXTRA_SALARY(round(DSalary.getMONTH_EXTRA_SALARY(), 2));

			Bata.setPFAMOUNT_NO(round(DSalary.getPFAMOUNT_NO(), 2));
			if (DSalary.getSALARY_FROM_DATE_ON() != null) {
				Bata.setSALARY_FROM_DATE(dateFormat_Name.format(DSalary.getSALARY_FROM_DATE_ON()));
			}
			if (DSalary.getSALARY_TO_DATE_ON() != null) {
				Bata.setSALARY_TO_DATE(dateFormat_Name.format(DSalary.getSALARY_TO_DATE_ON()));
			}

			Double TOTAL_PREVIOUS_ADVANCE = 0.0;
			Double TOTAL_ADVANCE = 0.0;
			Double TOTAL_PENALTY = 0.0;
			Double TOTAL_ADVANCE_PENALTY = 0.0;
			Double TOTAL_ADVANCE_BALANCE = 0.0;

			if (DSalary.getTOTAL_PREVIOUS_ADVANCE() != null) {
				TOTAL_PREVIOUS_ADVANCE = DSalary.getTOTAL_PREVIOUS_ADVANCE();
			}

			if (DSalary.getTOTAL_ADVANCE() != null) {
				TOTAL_ADVANCE = DSalary.getTOTAL_ADVANCE();
			}

			if (DSalary.getTOTAL_PENALTY() != null) {
				TOTAL_PENALTY = DSalary.getTOTAL_PENALTY();
			}

			if (DSalary.getTOTAL_ADVANCE_PENALTY() != null) {
				TOTAL_ADVANCE_PENALTY = DSalary.getTOTAL_ADVANCE_PENALTY();
			}

			if (DSalary.getTOTAL_ADVANCE_BALANCE() != null) {
				TOTAL_ADVANCE_BALANCE = DSalary.getTOTAL_ADVANCE_BALANCE();
			}

			Bata.setTOTAL_PREVIOUS_ADVANCE(round(TOTAL_PREVIOUS_ADVANCE, 2));
			Bata.setTOTAL_ADVANCE(round(TOTAL_ADVANCE, 2));
			Bata.setTOTAL_PENALTY(round(TOTAL_PENALTY, 2));

			Bata.setTOTAL_ADVANCE_PENALTY(round(TOTAL_ADVANCE_PENALTY, 2));
			Bata.setTOTAL_ADVANCE_BALANCE(round(TOTAL_ADVANCE_BALANCE, 2));

			Bata.setTOTAL_HANDSALARY(DSalary.getTOTAL_HANDSALARY());

			Bata.setTOTAL_ESIAMOUNT(round(DSalary.getTOTAL_ESIAMOUNT(), 2));
			Bata.setTOTAL_PFAMOUNT(round(DSalary.getTOTAL_PFAMOUNT(), 2));

			Bata.setTOTAL_NETSALARY(round(DSalary.getTOTAL_NETSALARY(), 2));
			Bata.setTOTAL_EXTRA_NETSALARY(round(DSalary.getTOTAL_EXTRA_NETSALARY(), 2));

			Bata.setTOTAL_WORKINGDAY(DSalary.getTOTAL_WORKINGDAY());
			Bata.setTOTAL_EXTRA_WORKINGDAY(DSalary.getTOTAL_EXTRA_WORKINGDAY());

			Bata.setSALARY_STATUS_ID(DSalary.getSALARY_STATUS_ID());
			Bata.setSALARY_STATUS(DriverAdvanceStatus.getStausName(DSalary.getSALARY_STATUS_ID()));
			if (DSalary.getLASTUPDATED_DATE_ON() != null) {
				Bata.setLASTUPDATED_DATE(dateFormat_Name.format(DSalary.getLASTUPDATED_DATE_ON()));
			}
			Bata.setLASTMODIFIEDBY(DSalary.getLASTMODIFIEDBY());
			if (DSalary.getCREATED_DATE_ON() != null) {
				Bata.setCREATED_DATE(dateFormat_Name.format(DSalary.getCREATED_DATE_ON()));
			}
			Bata.setCREATEDBY(DSalary.getCREATEDBY());
			Bata.setMarkForDelete(DSalary.ismarkForDelete());

		}

		return Bata;
	}

	public DriverPaidAdvance perpare_DriverPaidAdvanceDto(DriverPaidAdvanceDto paidAdvance) {

		DriverPaidAdvance advance = new DriverPaidAdvance();

		if (paidAdvance != null) {

			advance.setADVANCE_PAID_NUMBER(paidAdvance.getADVANCE_PAID_NUMBER());
			advance.setADVANCE_RECEIVED_BY_ID(paidAdvance.getADVANCE_RECEIVED_BY_ID());

			advance.setDRIVER_ID(paidAdvance.getDRIVER_ID());

			try {
				java.util.Date date = dateFormat.parse(paidAdvance.getPAID_DATE());
				Date FuelDate = new Date(date.getTime());
				advance.setPAID_DATE(FuelDate);
			} catch (Exception e) {
				e.printStackTrace();
			}

			advance.setPAID_REMARK(paidAdvance.getPAID_REMARK());
			advance.setPAID_STATUS_ID(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_PAID);
			advance.setmarkForDelete(false);

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			advance.setCREATEDBYID(userDetails.getId());
			advance.setLASTMODIFIEDBYID(userDetails.getId());
			advance.setCOMPANY_ID(userDetails.getCompany_id());

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp CREATED = new java.sql.Timestamp(currentDateUpdate.getTime());

			advance.setCREATED(CREATED);
			advance.setLASTUPDATED(CREATED);
		}

		return advance;
	}

	public Double Total_advanceBalance_total(List<DriverSalaryAdvance> driverAdvanvce) {
		Double advacneBalance = 0.0;
		if (driverAdvanvce != null && !driverAdvanvce.isEmpty()) {
			for (DriverSalaryAdvance driverSalaryAdvance : driverAdvanvce) {

				if (driverSalaryAdvance.getADVANCE_BALANCE() != null) {

					advacneBalance += driverSalaryAdvance.getADVANCE_BALANCE();
				}
			}
		}
		return advacneBalance;
	}

	public Double Total_Only_advanceBalance_total(List<DriverSalaryAdvance> driverAdvanvce) {
		Double advacneBalance = 0.0;
		if (driverAdvanvce != null && !driverAdvanvce.isEmpty()) {
			for (DriverSalaryAdvance driverSalaryAdvance : driverAdvanvce) {

				if (driverSalaryAdvance.getADVANCE_BALANCE() != null) {

					if (driverSalaryAdvance.getADVANCE_NAME_ID() == DriverAdvanceType.DRIVER_ADVANCE_TYPE_ADVANCE) {
						advacneBalance += driverSalaryAdvance.getADVANCE_BALANCE();
					}
				}
			}
		}
		return advacneBalance;
	}

	public Double Total_Only_PenaltyBalance_total(List<DriverSalaryAdvance> driverAdvanvce) {
		Double advacneBalance = 0.0;
		if (driverAdvanvce != null && !driverAdvanvce.isEmpty()) {
			for (DriverSalaryAdvance driverSalaryAdvance : driverAdvanvce) {

				if (driverSalaryAdvance.getADVANCE_BALANCE() != null) {

					if (driverSalaryAdvance.getADVANCE_NAME_ID() == DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY) {
						advacneBalance += driverSalaryAdvance.getADVANCE_BALANCE();
					}
				}
			}
		}
		return advacneBalance;
	}

	public Double Total_DriverSalary_Advance_Report(List<DriverSalaryAdvanceDto> driverTotal) {

		Double PenaltyTotal = 0.0;
		if (driverTotal != null && !driverTotal.isEmpty()) {
			for (DriverSalaryAdvanceDto driverSalaryAdvance : driverTotal) {

				if (driverSalaryAdvance.getADVANCE_AMOUNT() != null) {

					PenaltyTotal += driverSalaryAdvance.getADVANCE_AMOUNT();
				}
			}
		}
		return PenaltyTotal;
	}

	public DriverSalaryAdvance prepare_DriverSalary_Advance_CASHBOOK(CashBook cash) {

		DriverSalaryAdvance advance = new DriverSalaryAdvance();

		advance.setDRIVER_ID(cash.getDRIVER_ID());

		advance.setADVANCE_BALANCE(cash.getCASH_AMOUNT());
		advance.setADVANCE_AMOUNT(cash.getCASH_AMOUNT());
		advance.setADVANCE_DATE(cash.getCASH_DATE());
		advance.setmarkForDelete(false);

		advance.setTRIPDAILYID((long) 0);
		advance.setADVANCE_NAME_ID(DriverAdvanceType.DRIVER_ADVANCE_TYPE_ADVANCE);
	//	advance.setADVANCE_NAME(DriverAdvanceType.DRIVER_ADVANCE_TYPE_ADVANCE_NAME);

		advance.setADVANCE_STATUS_ID(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN);
		//advance.setADVANCE_STATUS(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN_NAME);
		//advance.setADVANCE_PAID_BY(cash.getCASH_PAID_BY());
		advance.setADVANCE_PAID_NUMBER(cash.getCASH_REFERENCENO());
		advance.setADVANCE_PAID_TYPE_ID(cash.getPAYMENT_TYPE_ID());
		//advance.setADVANCE_PAID_TYPE(PaymentTypeConstant.getPaymentTypeName(cash.getPAYMENT_TYPE_ID()));

		advance.setADVANCE_REMARK("Cash Book To Given Advance :" + cash.getCASH_REFERENCENO());

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		//advance.setCREATEDBY(userDetails.getEmail());
		//advance.setLASTMODIFIEDBY(userDetails.getEmail());
		advance.setCREATED_BY_ID(userDetails.getId());
		advance.setLASTMODIFIED_BY_ID(userDetails.getId());
		advance.setCOMPANY_ID(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		advance.setLASTUPDATED(toDate);
		advance.setCREATED(toDate);
		advance.setmarkForDelete(false);
		return advance;
	}
	
	public List<DriverCommentDto> getDriverCommentList(List<DriverCommentDto>  list){
		List<DriverCommentDto>	commentList	= null;
		try {
			if(list != null) {
				commentList	= new ArrayList<>();
				for(DriverCommentDto driverCommentDto : list) {
					if(driverCommentDto.getCreated() != null) {
						driverCommentDto.setCreationDate(CreatedDateTime.format(driverCommentDto.getCreated()));
					}else {
						driverCommentDto.setCreationDate("--");
					}
					if(driverCommentDto.getCreatedBy() == null) 
						driverCommentDto.setCreatedBy("--");
					
					if(driverCommentDto.getDriver_title() == null)
						driverCommentDto.setDriver_title("--");
					
					commentList.add(driverCommentDto);
				}
			}
			return commentList;
		} catch (Exception e) {
			throw e;
		}finally {
			commentList	= null;
		}
	}
	
	public DriverSalaryAdvance prepareDriverPenalty(ValueObject valueObject) throws Exception {
		DriverSalaryAdvance advance = new DriverSalaryAdvance();

		advance.setDRIVER_ID(valueObject.getInt("driverId"));

		advance.setADVANCE_BALANCE(valueObject.getDouble("advanceAmount"));
		advance.setADVANCE_AMOUNT(valueObject.getDouble("advanceAmount"));

		advance.setTRIPDAILYID(valueObject.getLong("tripsheetId"));
		advance.setDSANUMBER(valueObject.getLong("tripNumber"));
		advance.setADVANCE_NAME_ID(DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY);
		//advance.setADVANCE_NAME(DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY_NAME);
		
		
		if (valueObject.getString("penaltyDate") != null) {
			/** Reported_Date converted String to date Format */
			java.util.Date date = dateFormat.parse(valueObject.getString("penaltyDate"));
			Date Reported_Date = new Date(date.getTime());
			advance.setADVANCE_DATE(Reported_Date);
		} else {
			String currentDate = dateFormat.format(new java.util.Date());
			java.util.Date currentDateUpdate = dateFormat.parse(currentDate);
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			advance.setADVANCE_DATE(toDate);
		}

		//advance.setADVANCE_STATUS(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN_NAME);
		advance.setADVANCE_STATUS_ID(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN);

		//advance.setADVANCE_PAID_BY(advanceDto.getADVANCE_PAID_BY());
	//2222222222	advance.setADVANCE_PAID_NUMBER(advanceDto.getADVANCE_PAID_NUMBER());
	//	22222222222advance.setADVANCE_PAID_TYPE_ID(advanceDto.getADVANCE_PAID_TYPE_ID());
	//	advance.setADVANCE_PAID_TYPE(PaymentTypeConstant.getPaymentTypeName(advanceDto.getADVANCE_PAID_TYPE_ID()));

		advance.setADVANCE_REMARK(valueObject.getString("remark"));

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		//advance.setCREATEDBY(userDetails.getEmail());
		//advance.setLASTMODIFIEDBY(userDetails.getEmail());
		advance.setCOMPANY_ID(userDetails.getCompany_id());

		advance.setCREATED_BY_ID(userDetails.getId());
		advance.setLASTMODIFIED_BY_ID(userDetails.getId());
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		advance.setLASTUPDATED(toDate);
		advance.setCREATED(toDate);
		advance.setmarkForDelete(false);

		return advance;
	}
	
	public Driver prepareDriverDetails(ValueObject valueObject , CustomUserDetails userDetails) throws Exception {
		Driver 		driver 					= null;
		Date 		currentDateUpdate 		= null;
		Timestamp 	toDate 					= null;
		try {
			
			driver 				= new Driver();
			currentDateUpdate 	= new Date();
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());

			driver.setDriver_id(valueObject.getInt("driverId",0));
			driver.setDriver_firstname(valueObject.getString("driverFirstName"));
			driver.setDriver_Lastname(valueObject.getString("driverLastName"));
			driver.setDriver_fathername(valueObject.getString("driverFatherName"));
			driver.setDriver_dateofbirth(valueObject.getString("driverDateOfBirth"));
			driver.setDriver_Qualification(valueObject.getString("driverQualification"));
			driver.setDriver_bloodgroup(valueObject.getString("driverBloodGroup"));
			driver.setDriver_languages(valueObject.getString("driverLanguage"));
			driver.setVid(valueObject.getInt("vid",0));
			driver.setVehicleGroupId(valueObject.getLong("vGroup",0));
			driver.setDriverStatusId(valueObject.getShort("driverStatusId",(short)0));
			driver.setDriverSalaryTypeId(valueObject.getShort("driverSalaryTypeId",(short)0));
			driver.setDriver_perdaySalary(valueObject.getDouble("driverPerDaySalary",0));
			driver.setDriver_esiamount(valueObject.getDouble("driverESIAmount",0));
			driver.setDriver_pfamount(valueObject.getDouble("driverPFAmount",0));
			driver.setDriver_email(valueObject.getString("driverEmail"));
			driver.setDriver_mobnumber(valueObject.getString("mobileNo"));
			driver.setDriver_homenumber(valueObject.getString("driverHomePhoneNo"));
			driver.setDriver_address(valueObject.getString("driverAddress"));
			driver.setDriver_address2(valueObject.getString("driverAddress2"));
			driver.setDriver_country(valueObject.getString("countryId"));
			driver.setDriver_state(valueObject.getString("stateId"));
			driver.setDriver_city(valueObject.getString("cities"));
			driver.setDriver_pincode(valueObject.getString("pinCode"));
			driver.setDriver_empnumber(valueObject.getString("empNumber"));
			driver.setDriver_insuranceno(valueObject.getString("insuranceNo"));
			driver.setDriver_esino(valueObject.getString("ESINumber"));
			driver.setDriver_pfno(valueObject.getString("PFNumber"));
			driver.setDriver_trainings(valueObject.getString("driverTraining"));
			driver.setDriJobId(valueObject.getInt("driJobId",0));
			driver.setDriver_startdate(valueObject.getString("joinDate"));
			driver.setDriverJoinDate(dateFormat.parse(valueObject.getString("joinDate")));
			driver.setDriver_leavedate(valueObject.getString("LeaveDate"));
			driver.setDriver_dlnumber(valueObject.getString("dlNumber"));
			driver.setDriver_badgenumber(valueObject.getString("badgeNumber"));
			driver.setDriver_dlclass(valueObject.getString("DL_Class"));
			driver.setDriver_dlprovince(valueObject.getString("DL_State"));
			driver.setDriver_authorised(valueObject.getString("driverAuthorized"));
			driver.setDriver_dlOriginal(valueObject.getString("DL_Original"));
			driver.setDriver_bankname(valueObject.getString("bankName"));
			driver.setDriver_banknumber(valueObject.getString("bankACNumber"));
			driver.setDriver_bankifsc(valueObject.getString("bankIFSC"));
			driver.setDriver_aadharnumber(valueObject.getString("aadharNumber"));
			driver.setDriver_pannumber(valueObject.getString("panNumber"));
			driver.setDriver_reffristname(valueObject.getString("referenceFirstName"));
			driver.setDriver_reflastname(valueObject.getString("referenceLastName"));
			driver.setDriver_refcontect(valueObject.getString("referenceContactNo"));
			driver.setDriver_photoid(valueObject.getInt("driverPhotoId",0));
			driver.setTripSheetID(valueObject.getLong("tripsheetId",0));
			driver.setCreatedById(userDetails.getId());
			driver.setLastModifiedById(userDetails.getId());
			driver.setCreated(toDate);
			driver.setLastupdated(toDate);
			driver.setCompanyId(userDetails.getCompany_id());
			driver.setMarkForDelete(false);
			driver.setSalariedId(valueObject.getShort("salariedId"));
			if(driver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_SUSPEND) {
				driver.setRemark(valueObject.getString("remark",""));
			}
			return driver;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
		
		
	}

	public DriverHistory prepareDriverHistoryDetails(Driver savedDriver, ValueObject valueObject) throws Exception {
			DriverHistory 		driver 					= null;
			try {
				
				driver 				= new DriverHistory();

				driver.setDriver_id(savedDriver.getDriver_id());
				driver.setDriver_firstname(savedDriver.getDriver_firstname());
				driver.setDriver_Lastname(savedDriver.getDriver_Lastname());
				driver.setDriver_fathername(savedDriver.getDriver_fathername());
				driver.setDriver_dateofbirth(savedDriver.getDriver_dateofbirth());
				driver.setDriver_Qualification(savedDriver.getDriver_Qualification());
				driver.setDriver_bloodgroup(savedDriver.getDriver_bloodgroup());
				driver.setDriver_languages(savedDriver.getDriver_languages());
				driver.setVid(savedDriver.getVid());
				driver.setVehicleGroupId(savedDriver.getVehicleGroupId());
				driver.setDriverStatusId(savedDriver.getDriverStatusId());
				driver.setDriverSalaryTypeId(savedDriver.getDriverSalaryTypeId());
				driver.setDriver_perdaySalary(savedDriver.getDriver_perdaySalary());
				driver.setDriver_esiamount(savedDriver.getDriver_esiamount());
				driver.setDriver_pfamount(savedDriver.getDriver_pfamount());
				driver.setDriver_email(savedDriver.getDriver_email());
				driver.setDriver_mobnumber(savedDriver.getDriver_mobnumber());
				driver.setDriver_homenumber(savedDriver.getDriver_homenumber());
				driver.setDriver_address(savedDriver.getDriver_address());
				driver.setDriver_address2(savedDriver.getDriver_address2());
				driver.setDriver_country(savedDriver.getDriver_country());
				driver.setDriver_state(savedDriver.getDriver_state());
				driver.setDriver_city(savedDriver.getDriver_city());
				driver.setDriver_pincode(savedDriver.getDriver_pincode());
				driver.setDriver_empnumber(savedDriver.getDriver_empnumber());
				driver.setDriver_insuranceno(savedDriver.getDriver_insuranceno());
				driver.setDriver_esino(savedDriver.getDriver_esino());
				driver.setDriver_pfno(savedDriver.getDriver_pfno());
				driver.setDriver_trainings(savedDriver.getDriver_trainings());
				driver.setDriJobId(savedDriver.getDriJobId());
				driver.setDriver_startdate(savedDriver.getDriver_startdate());
				driver.setDriver_leavedate(savedDriver.getDriver_leavedate());
				driver.setDriver_dlnumber(savedDriver.getDriver_dlnumber());
				driver.setDriver_badgenumber(savedDriver.getDriver_badgenumber());
				driver.setDriver_dlclass(savedDriver.getDriver_dlclass());
				driver.setDriver_dlprovince(savedDriver.getDriver_dlprovince());
				driver.setDriver_authorised(savedDriver.getDriver_authorised());
				driver.setDriver_dlOriginal(savedDriver.getDriver_dlOriginal());
				driver.setDriver_bankname(savedDriver.getDriver_bankname());
				driver.setDriver_banknumber(savedDriver.getDriver_banknumber());
				driver.setDriver_bankifsc(savedDriver.getDriver_bankifsc());
				driver.setDriver_aadharnumber(savedDriver.getDriver_aadharnumber());
				driver.setDriver_pannumber(savedDriver.getDriver_pannumber());
				driver.setDriver_reffristname(savedDriver.getDriver_reffristname());
				driver.setDriver_reflastname(savedDriver.getDriver_reflastname());
				driver.setDriver_refcontect(savedDriver.getDriver_refcontect());
				driver.setDriver_photoid(savedDriver.getDriver_photoid());
				driver.setTripSheetID(savedDriver.getTripSheetID());
				driver.setCreatedById(savedDriver.getCreatedById());
				driver.setLastModifiedById(savedDriver.getLastModifiedById());
				driver.setCreated(savedDriver.getCreated());
				driver.setLastupdated(savedDriver.getLastupdated());
				driver.setCompanyId(savedDriver.getCompanyId());
				driver.setMarkForDelete(savedDriver.isMarkForDelete());
				driver.setSalariedId(savedDriver.getSalariedId());
				driver.setRemark(savedDriver.getRemark());
				driver.setOperationId(valueObject.getShort("operationId"));
				
				return driver;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
				
			}
	}
	
	public static  DriverTripSheetBalance getDriverTripSheetBalanceDto(DriverTripSheetBalance	prBalance, ValueObject	valueObject) {
		DriverTripSheetBalance	balance	= new DriverTripSheetBalance();
		double amount = 0.0;
		
		if(valueObject.getShort("txnType",(short) 0) == DriverLedgerTypeConstant.TRIPSHEET_EXPENSE || valueObject.getShort("txnType",(short) 0) == DriverLedgerTypeConstant.TRIPSHEET_ADVANCE_REMOVED || valueObject.getShort("txnType",(short) 0) == DriverLedgerTypeConstant.TRIPSHEET_CLOSE_PAID_BY_DRIVER)
			amount -= valueObject.getDouble("amount",0);
		else	
			amount = valueObject.getDouble("amount",0);
		
		if(prBalance != null) {
			balance.setdTripSheetBalanceId(prBalance.getdTripSheetBalanceId());
			balance.setBalanceAmount(prBalance.getBalanceAmount() + amount);
		}else
			balance.setBalanceAmount(amount);
		
		balance.setDriverId(valueObject.getLong("driverId",0));
		balance.setCompanyId(valueObject.getInt("companyId",0));
		balance.setMarkForDelete(false);
		
		return balance;
	}
	
	public static DriverLedger	getDriverLedgerDto(DriverLedger  preLedger, ValueObject	valueObject) throws Exception{
		
		DriverLedger	driverLedger	= new DriverLedger();
		
		double amount = 0.0;
		
		if(valueObject.getShort("txnType",(short) 0) == DriverLedgerTypeConstant.TRIPSHEET_EXPENSE || valueObject.getShort("txnType",(short) 0) == DriverLedgerTypeConstant.TRIPSHEET_ADVANCE_REMOVED || valueObject.getShort("txnType",(short) 0) == DriverLedgerTypeConstant.TRIPSHEET_CLOSE_PAID_BY_DRIVER)
			amount -= valueObject.getDouble("amount",0);
		else
			amount = valueObject.getDouble("amount",0);
		
			valueObject.put("amountAdvanceOrExpense", amount);
		
			if(preLedger != null) {
				driverLedger.setOpeningBalance(preLedger.getClosingBalance());
				if(!valueObject.getBoolean("FromBalanceTransfer", false))
					driverLedger.setClosingBalance(preLedger.getClosingBalance() + amount);
				else
					driverLedger.setClosingBalance(preLedger.getClosingBalance());
			}else {
				driverLedger.setOpeningBalance(0.00);
				driverLedger.setClosingBalance(amount);
			}
			driverLedger.setDriverId(valueObject.getLong("driverId"));
			driverLedger.setCompanyId(valueObject.getInt("companyId"));
			driverLedger.setTransactionAmount(valueObject.getDouble("amount",0));
			driverLedger.setTransactionId(valueObject.getLong("transactionId",0));
			driverLedger.setTxnTypeId(valueObject.getShort("txnType",(short) 0));
			driverLedger.setSubTransactionId(valueObject.getLong("subTransactionId",0));
			driverLedger.setDescription(valueObject.getString("description"));
			driverLedger.setCreatedById(valueObject.getLong("userId"));
			driverLedger.setUpdatedById(valueObject.getLong("userId"));
			driverLedger.setCreatedOn((Date) valueObject.get("txnTime"));
			driverLedger.setModifiedOn((Date) valueObject.get("txnTime"));
			driverLedger.setMarkForDelete(false);
		
			System.err.println("DriverLedger Dto "+ driverLedger);
		return driverLedger;
	}

}
