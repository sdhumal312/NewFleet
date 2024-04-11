package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.UserProfile;
import org.fleetopgroup.persistence.model.UserProfileDocument;
import org.fleetopgroup.persistence.model.UserProfilePhoto;
import org.fleetopgroup.validation.EmailExistsException;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface IUserProfileService {

	UserProfile registerNewUserProfile(UserProfile accountDto) throws EmailExistsException;

	UserProfile UpdateUserProfile(UserProfile accountDto) throws EmailExistsException;

	UserProfileDto findUserProfileByUser_email(String email);

	UserProfile getUserProfileByID(long id, Integer companyId);
	
	boolean isTwoFactorAuthEnabled (Long id) throws Exception;
	
	boolean isOTPValidationRequired(Long id) throws Exception;

	UserProfileDto getUserProfileByUser_id(long id);
	
	UserProfileDto get_editUserProfileByUser_id(long id);
	
	UserProfileDto get_ShowUserProfileByUser_id(long id);


	List<UserProfile> findAllUserProfile();

	/**
	 * This List get UserProfile table to get pagination last 10 entries values
	 */
	public List<UserProfileDto> list_UserProfile(Integer pageNumber) throws Exception;

	public List<UserProfileDto> list_UserProfile_Master_User(Integer Company_ID) throws Exception;

	/** This Page get UserProfile table to get pagination values */
	public Page<UserProfile> getDeployment_Page_UserProfile(Integer pageNumber);

	long count();

	void changeUserActive(long id, boolean status, Integer company_id);

	void changeUserInActive(long id, boolean status, Integer company_id);

	void UpdateUserProfile_photo_id(long id, Long photo_id, Integer companyId);

	List<UserProfileDto> SearchUserEmail_id_and_Name(String term, Integer companyId);
	
	List<UserProfileDto> SearchUserEmail_id_and_Name(Integer branchId, Integer companyId);

	/** Get user Email id to search user only subscribe value */
	UserProfileDto getUser_email_to_subscribe_Detils(String email);

	/** Get user Email id to search user only Issues Entries */
	UserProfileDto get_UserProfile_Issues_Details(String email);

	/** Get user Email id to get_UserProfile_Show_All_Details */
	UserProfileDto get_UserProfile_Show_All_Details(String email);

	/**
	 * Get user Email id to search user only Branch Name and Location Entries
	 */
	UserProfileDto get_UserProfile_WORKORDER_LOCATION_Details(String email);

	/* UserProfile Document */

	public void saveUserProfileDocument(org.fleetopgroup.persistence.document.UserProfileDocument UserProfileDocument);

	public void updateUserProfileDocument(org.fleetopgroup.persistence.document.UserProfileDocument UserProfileDocument);

	public List<org.fleetopgroup.persistence.document.UserProfileDocument> listUserProfileDocument(Long UserProfile_id, Integer companyId);

	public org.fleetopgroup.persistence.document.UserProfileDocument getUserProfileDocuemnt(Long UserProfile_docid, Integer companyId);

	public void deleteUserProfileDocument(Long UserProfile_documentid, Integer companyId) throws Exception;

	public Long getUserProfileDocumentCount(Long UserProfile_docid);

	/* UserProfile Photo */

	public void addUserProfilePhoto(org.fleetopgroup.persistence.document.UserProfilePhoto userProfilePhoto);

	public void updateUserProfilePhoto(org.fleetopgroup.persistence.document.UserProfilePhoto userProfilePhoto);

	public org.fleetopgroup.persistence.document.UserProfilePhoto getUserProfilePhoto(Long UserProfile_photoid);

	public org.fleetopgroup.persistence.document.UserProfilePhoto findUserProfilePhotoByUserProfile_id(Long profile_id);

	public Long getPhotoCount(Long UserProfile_id);

	/** Get User email id to show user profile picture */
	public UserProfilePhoto show_user_profile_picture(String User_emailId);

	/**
	 * This List get UserProfile table to get Search List last 10 entries values
	 */
	public List<UserProfileDto> Search_list_UserProfile(String SearchQuery,Integer company_Id) throws Exception;

	/**
	 * @param name
	 * @return
	 */
	UserProfileDto GET_UserProfile_Name_PlaceinBranchname_ByUseremail(String name);
	
	public UserProfileDto getUserDeatilsByEmail(String email, int compId) throws Exception;
	
	UserProfileDto GET_UserProfile_Name_PlaceinBranchname_ByUseremail(Long user_id);

	/**
	 * @param vendorId
	 * @param vendorName
	 * @param user_id
	 */
	public void Update_UserID_To_VendorLink_Details(Integer vendorId, String vendorName, Long user_id, Integer companyId);
	
	
	public UserProfileDto findUserProfileByUser_email_Company_ESI_PF_DIABLE(Long user_id);

	public void transferUserProfileDocument(List<UserProfileDocument> userProfileDocumentList) throws Exception;

	public void transferUserProfilePhoto(List<UserProfilePhoto> userProfilePhotoList) throws Exception;
	
	public long getUserProfileDocumentMaxId() throws Exception;
	
	public long getUserProfilePhotoMaxId() throws Exception;
	
	public HashMap<Long ,UserProfileDto> getUserEmailIdFromUserId(String userId) throws Exception;
	
	public ValueObject	saveTwoFactorEnableDisableState(ValueObject	valueObject) throws Exception;
	
	public void updateLoginDetails() throws Exception;
	
	public void UpdateLAstPasswordReset(Date date, Long userId) throws Exception;
	
	public ValueObject searchUserListInMobile(ValueObject object) throws Exception;

	public ValueObject checkIfPasswordResetRequired(Long id, HashMap<String, Object> configuration, Integer companyId) throws Exception;
	
	/** Get user Email id to search user only Issues Entries */
	UserProfileDto get_UserProfile_Issues_Details(String email,Integer companyId);
	
	public List<UserProfileDto> getAllUserListByCompanyId(String term,Integer companyId);
	
	public List<UserProfileDto> getBranchWiseUserList(String term,Integer companyId,Long branchId);
	
	public UserProfile getUserProfileByUser_id(long id, Integer companyId);
}
