package org.fleetopgroup.persistence.bl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.UserProfile;
import org.fleetopgroup.persistence.service.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

/**
 * @author fleetop
 *
 *
 *
 */

@Controller
public class UserProfileBL {

	public UserProfileBL() {
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	// Get The branch details to show Edit page Branch
	public UserProfileDto GetUserProfileDtoDetails(UserProfile userBean) {

		final UserProfileDto userProfile = new UserProfileDto();

		userProfile.setUserprofile_id(userBean.getUserprofile_id());
		//userProfile.setFirstName(userBean.getFirstName());
		//userProfile.setLastName(userBean.getLastName());

		userProfile.setUser_id(userBean.getUser_id());
		//userProfile.setUser_email(userBean.getUser_email());

		userProfile.setCompany_id(userBean.getCompany_id());
		//userProfile.setCompany_name(userBean.getCompany_name());
		userProfile.setDepartment_id(userBean.getDepartment_id());
		//userProfile.setDepartment_name(userBean.getDepartment_name());
		userProfile.setBranch_id(userBean.getBranch_id());
		//userProfile.setBranch_name(userBean.getBranch_name());

		userProfile.setDesignation(userBean.getDesignation());

		userProfile.setSex(userBean.getSex());
		userProfile.setPersonal_email(userBean.getPersonal_email());
		userProfile.setHome_number(userBean.getHome_number());
		userProfile.setMobile_number(userBean.getMobile_number());
		userProfile.setWork_number(userBean.getWork_number());
		userProfile.setAddress_line1(userBean.getAddress_line1());
		userProfile.setCountry(userBean.getCountry());
		userProfile.setState(userBean.getState());
		userProfile.setCity(userBean.getCity());
		userProfile.setPincode(userBean.getPincode());

		userProfile.setEmergency_person(userBean.getEmergency_person());
		userProfile.setEmergency_number(userBean.getEmergency_number());

		userProfile.setEmployes_id(userBean.getEmployes_id());
		userProfile.setWorking_time_from(userBean.getWorking_time_from());
		userProfile.setWorking_time_to(userBean.getWorking_time_to());
		userProfile.setEsi_number(userBean.getEsi_number());
		userProfile.setPf_number(userBean.getPf_number());
		userProfile.setInsurance_number(userBean.getInsurance_number());

		// This Vendor Show Vehicle Details
		userProfile.setVendorId(userBean.getVendorId());
		//userProfile.setVendorName(userBean.getVendorName());

		userProfile.setSubscribe(userBean.getSubscribe());

		userProfile.setPhoto_id(userBean.getPhoto_id());
		userProfile.setMarkForDelete(userBean.isMarkForDelete());

		if (userBean.getDateofbirth() != null) {
			try {
				userProfile.setDateofbirth(dateFormat.format(userBean.getDateofbirth()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Create and Last updated Display
		userProfile.setCreatedBy(userBean.getCreatedBy());
		if (userBean.getCreated() != null) {
			userProfile.setCreated(CreatedDateTime.format(userBean.getCreated()));
		}
		userProfile.setLastModifiedBy(userBean.getLastModifiedBy());
		if (userBean.getLastupdated() != null) {
			userProfile.setLastupdated(CreatedDateTime.format(userBean.getLastupdated()));
		}

		return userProfile;
	}

	public UserProfile prepareUpdateUserProfile_Master_User(UserProfileDto userBean, UserDto user) {

		UserProfile userProfile = new UserProfile();

		//userProfile.setFirstName(user.getFirstName());
		//userProfile.setLastName(user.getLastName());
		//userProfile.setUser_email(user.getEmail());


		if (userBean.getDepartment_id() != null) {
			userProfile.setDepartment_id(userBean.getDepartment_id());
		}

		if (userBean.getBranch_id() != null) {
			userProfile.setBranch_id(userBean.getBranch_id());
		}

		userProfile.setDesignation(userBean.getDesignation());

		userProfile.setSex(userBean.getSex());
		userProfile.setPersonal_email(userBean.getPersonal_email());
		userProfile.setHome_number(userBean.getHome_number());
		userProfile.setMobile_number(userBean.getMobile_number());
		userProfile.setWork_number(userBean.getWork_number());
		userProfile.setAddress_line1(userBean.getAddress_line1());
		userProfile.setCountry(userBean.getCountry());
		userProfile.setState(userBean.getState());
		userProfile.setCity(userBean.getCity());
		userProfile.setPincode(userBean.getPincode());

		userProfile.setEmergency_person(userBean.getEmergency_person());
		userProfile.setEmergency_number(userBean.getEmergency_number());

		userProfile.setEmployes_id(userBean.getEmployes_id());
		userProfile.setWorking_time_from(userBean.getWorking_time_from());
		userProfile.setWorking_time_to(userBean.getWorking_time_to());
		userProfile.setEsi_number(userBean.getEsi_number());
		userProfile.setPf_number(userBean.getPf_number());
		userProfile.setInsurance_number(userBean.getInsurance_number());
		userProfile.setSubscribe(userBean.getSubscribe());
		if (userBean.getDateofbirth() != null) {
			try {
				java.util.Date dateTo = dateFormat.parse(userBean.getDateofbirth());
				java.sql.Date dateofbirth = new Date(dateTo.getTime());
				userProfile.setDateofbirth(dateofbirth);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// This Vendor_Id Link To User Profile
		userProfile.setVendorId(0);
		//userProfile.setVendorName(null);

		/** who Create this vehicle get email id to user profile details */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null) {
			String CreatedBy = auth.getName();

			userProfile.setCreatedBy(CreatedBy);
			userProfile.setLastModifiedBy(CreatedBy);
		}

		java.util.Date currentDate = new java.util.Date();
		Timestamp toDateUpdte = new java.sql.Timestamp(currentDate.getTime());

		userProfile.setCreated(toDateUpdte);
		userProfile.setLastupdated(toDateUpdte);

		userProfile.setMarkForDelete(false);
		//userProfile.setVehicleGroupPermission(userBean.getGroup_Permissions());

		return userProfile;
	}

	public UserProfile prepareUpdateUserProfile(UserProfileDto userBean) {

		UserProfile userProfile = new UserProfile();

		userProfile.setUserprofile_id(userBean.getUserprofile_id());
		userProfile.setUser_id(userBean.getUser_id());
		userProfile.setRole_id(userBean.getRole_id());
		userProfile.setFirstName(userBean.getFirstName());
		userProfile.setLastName(userBean.getLastName());
		// userProfile.setUser_email(userBean.getUser_email());
		if (userBean.getCompany_id() != null) {
			userProfile.setCompany_id(userBean.getCompany_id());
		}

		if (userBean.getDepartment_id() != null) {
			userProfile.setDepartment_id(userBean.getDepartment_id());
		}

		if (userBean.getBranch_id() != null) {
			userProfile.setBranch_id(userBean.getBranch_id());
		}

		userProfile.setDesignation(userBean.getDesignation());

		userProfile.setSex(userBean.getSex());
		userProfile.setPersonal_email(userBean.getPersonal_email());
		userProfile.setHome_number(userBean.getHome_number());
		userProfile.setMobile_number(userBean.getMobile_number());
		userProfile.setWork_number(userBean.getWork_number());
		userProfile.setAddress_line1(userBean.getAddress_line1());
		userProfile.setCountry(userBean.getCountry());
		userProfile.setState(userBean.getState());
		userProfile.setCity(userBean.getCity());
		userProfile.setPincode(userBean.getPincode());

		userProfile.setEmergency_person(userBean.getEmergency_person());
		userProfile.setEmergency_number(userBean.getEmergency_number());

		userProfile.setEmployes_id(userBean.getEmployes_id());
		userProfile.setWorking_time_from(userBean.getWorking_time_from());
		userProfile.setWorking_time_to(userBean.getWorking_time_to());
		userProfile.setEsi_number(userBean.getEsi_number());
		userProfile.setPf_number(userBean.getPf_number());
		userProfile.setInsurance_number(userBean.getInsurance_number());

		if(userBean.getRfidCardNo() != null) {
			userProfile.setRfidCardNo(userBean.getRfidCardNo());
		}

		userProfile.setSubscribe(userBean.getSubscribe());

		if (userBean.getDateofbirth() != null) {
			try {
				java.util.Date dateTo = dateFormat.parse(userBean.getDateofbirth());
				java.sql.Date dateofbirth = new Date(dateTo.getTime());
				userProfile.setDateofbirth(dateofbirth);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// This Vendor_Id Link To User Profile
		userProfile.setVendorId(userBean.getVendorId());
		// userProfile.setVendorName(userBean.getVendorName());

		/** who Create this vehicle get email id to user profile details */
		//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//		String CreatedBy = auth.getName();

		userProfile.setCreatedBy(userBean.getCreatedBy());
		userProfile.setLastModifiedBy(userBean.getLastModifiedBy());

		java.util.Date currentDate = new java.util.Date();
		Timestamp toDateUpdte = new java.sql.Timestamp(currentDate.getTime());

		userProfile.setLastupdated(toDateUpdte);
		userProfile.setMarkForDelete(userBean.isMarkForDelete());
		userProfile.setPhoto_id(userBean.getPhoto_id());
		userProfile.setCreated(currentDate);
		userProfile.setRole_id(1L);
		// userProfile.setVehicleGroupPermission(userBean.getGroup_Permissions());

		return userProfile;
	}

	public UserProfile prepareUpdateUserProfile_Master_User(UserProfileDto userBean, UserDto user, UserProfile	userProfile) {

		if(userProfile == null)
			userProfile = new UserProfile();

		//userProfile.setFirstName(user.getFirstName());
		//userProfile.setLastName(user.getLastName());
		//userProfile.setUser_email(user.getEmail());


		if (userBean.getDepartment_id() != null) {
			userProfile.setDepartment_id(userBean.getDepartment_id());
		}

		if (userBean.getBranch_id() != null) {
			userProfile.setBranch_id(userBean.getBranch_id());
		}

		userProfile.setDesignation(userBean.getDesignation());

		userProfile.setSex(userBean.getSex());
		userProfile.setPersonal_email(userBean.getPersonal_email());
		userProfile.setHome_number(userBean.getHome_number());
		userProfile.setMobile_number(userBean.getMobile_number());
		userProfile.setWork_number(userBean.getWork_number());
		userProfile.setAddress_line1(userBean.getAddress_line1());
		userProfile.setCountry(userBean.getCountry());
		userProfile.setState(userBean.getState());
		userProfile.setCity(userBean.getCity());
		userProfile.setPincode(userBean.getPincode());

		userProfile.setEmergency_person(userBean.getEmergency_person());
		userProfile.setEmergency_number(userBean.getEmergency_number());

		userProfile.setEmployes_id(userBean.getEmployes_id());
		userProfile.setWorking_time_from(userBean.getWorking_time_from());
		userProfile.setWorking_time_to(userBean.getWorking_time_to());
		userProfile.setEsi_number(userBean.getEsi_number());
		userProfile.setPf_number(userBean.getPf_number());
		userProfile.setInsurance_number(userBean.getInsurance_number());
		userProfile.setSubscribe(userBean.getSubscribe());
		if (userBean.getDateofbirth() != null) {
			try {
				java.util.Date dateTo = dateFormat.parse(userBean.getDateofbirth());
				java.sql.Date dateofbirth = new Date(dateTo.getTime());
				userProfile.setDateofbirth(dateofbirth);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// This Vendor_Id Link To User Profile
		userProfile.setVendorId(0);
		//userProfile.setVendorName(null);

		/** who Create this vehicle get email id to user profile details */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null) {
			String CreatedBy = auth.getName();

			userProfile.setCreatedBy(CreatedBy);
			userProfile.setLastModifiedBy(CreatedBy);
		}

		java.util.Date currentDate = new java.util.Date();
		Timestamp toDateUpdte = new java.sql.Timestamp(currentDate.getTime());

		userProfile.setCreated(toDateUpdte);
		userProfile.setLastupdated(toDateUpdte);

		userProfile.setMarkForDelete(false);
		//userProfile.setVehicleGroupPermission(userBean.getGroup_Permissions());

		return userProfile;
	}

}