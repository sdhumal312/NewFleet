package org.fleetopgroup.persistence.dao;


import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

	

	@Query(" From UserProfile u Where u.user_email = ?1 AND u.company_id = ?2 AND u.markForDelete = 0")
	UserProfile findUserProfileByUser_email(String email, Integer companyId);

	@Query(" From UserProfile u Where u.id = ?1 AND u.company_id = ?2")
	UserProfile getUserProfileByID(long id, Integer companyId);
	
	@Query(" SELECT u.isTwoFactorLogin From UserProfile u Where u.user_id = ?1 ")
	public boolean isTwoFactorAuthEnabled(Long id) throws Exception ;

	@Query(" From UserProfile u Where u.user_id = ?1 AND u.company_id = ?2 AND u.markForDelete = 0")
	UserProfile getUserProfileByUser_id(long id, Integer companyId);

	long count();

	@Modifying
	@Query("UPDATE UserProfile u Set u.markForDelete= ?2 Where u.user_id= ?1 AND u.company_id = ?3")
	void changeUserActive(long id, boolean status, Integer company_id);

	@Modifying
	@Query("UPDATE UserProfile u Set u.markForDelete= ?2 Where u.user_id= ?1 AND u.company_id = ?3")
	void changeUserINActive(long id, boolean status, Integer company_id);

	@Modifying
	@Query("UPDATE UserProfile u Set u.photo_id= ?2 Where u.id= ?1 AND u.company_id = ?3")
	void UpdateUserProfile_photo_id(long id, Long photo_id, Integer companyId);

	@Query("UPDATE UserProfile u Set u.markForDelete= 0 Where u.user_id= ?1 ")
	List<UserProfile> SearchUserEmail_id_and_Name(String term);

	@Modifying
	@Query("UPDATE UserProfile u Set u.vendorId= ?1, u.vendorName= ?2 Where u.user_id= ?3 AND u.company_id = ?4")
	public void Update_UserID_To_VendorLink_Details(Integer vendorId, String vendorName, Long user_id, Integer companyId);
	
	@Query(" From UserProfile u Where u.company_id = ?1")
	Page<UserProfile> findAllByCompanyId(Integer company_Id, Pageable pageable);
	
	@Modifying
	@Query("UPDATE UserProfile u Set u.isTwoFactorLogin= ?1 Where u.userprofile_id= ?2")
	public void updateTwoFactorEnableDisableState(boolean status, Long userprofile_id);
	
	@Modifying
	@Query("UPDATE UserProfile u Set u.lastLoginDate= ?1, u.lastLoginIP = ?2 Where u.user_id= ?3")
	public void updateLoginDetails(Date data, String ip, Long userId);
	
	@Modifying
	@Query("UPDATE UserProfile u Set u.lastPasswordResetOn= ?1 Where u.user_id= ?2")
	public void UpdateLAstPasswordReset(Date data,  Long userId);
	
	@Query("SELECT V.vendorName from UserProfile U "
			+ " INNER JOIN Vendor V ON V.vendorId = U.vendorId "
			+ " WHERE U.user_id = ?1 AND U.company_id = ?2 AND U.markForDelete = 0")
	public String selectVendor(long id, Integer companyId) throws Exception;
	
}
