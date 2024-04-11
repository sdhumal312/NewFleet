package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.UserProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserProfilePhotoRepository extends JpaRepository<UserProfilePhoto, Long> {
    
	//public UserProfilePhoto addUserProfilePhoto(UserProfilePhoto diverPhoto);

	//public UserProfilePhoto updateUserProfilePhoto(UserProfilePhoto diverPhoto);

	@Query(" From UserProfilePhoto u Where u.photoid = ?1 ")
	public UserProfilePhoto getUserProfilePhoto(long UserProfile_photoid);
	
	@Query(" From UserProfilePhoto u Where u.userprofile_id = ?1 ")
	public Long getPhotoCount(long photoid);
	
	@Query(" From UserProfilePhoto u Where u.userprofile_id = ?1 ")
	UserProfilePhoto findUserProfilePhotoByUserProfile_id(Long profile_id);
	
	@Query("SELECT MAX(photoid) FROM UserProfilePhoto")
	public long getUserProfilePhotoMaxId() throws Exception;
	
	@Query("FROM UserProfilePhoto where photoid > ?1 AND photoid <= ?2")
	public List<UserProfilePhoto> getUserProfilePhotoList(long startLimit, long endLimit) throws Exception;

}
