package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.UserProfileDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserProfileDocumentRepository extends JpaRepository<UserProfileDocument, Long> {
    
	//public UserProfileDocument saveUserProfileDocument(UserProfileDocument UserProfileDocument);

	//public UserProfileDocument updateUserProfileDocument(UserProfileDocument UserProfileDocument);

	@Query(" From UserProfileDocument u Where u.userprofile_id = ?1 AND u.companyId = ?2 AND u.markForDelete = 0")
	public List<UserProfileDocument> listUserProfileDocument(Long UserProfile_id, Integer companyId);

	@Query(" From UserProfileDocument u Where u.documentid = ?1 AND u.companyId = ?2 AND u.markForDelete = 0")
	public UserProfileDocument getUserProfileDocuemnt(Long documentid, Integer companyId);
	
	@Modifying
	@Query("UPDATE UserProfileDocument u SET u.markForDelete = 1 Where u.documentid= ?1 AND u.companyId = ?2")
	public void deleteUserProfileDocument(Long documentid, Integer companyId)  throws Exception;
	
	@Query("select count(*) From UserProfileDocument u Where u.userprofile_id = ?1 ")
	public Long getUserProfileDocumentCount(Long UserProfile_docid);
	
	@Query("SELECT MAX(documentid) FROM UserProfileDocument")
	public long getUserProfileDocumentMaxId() throws Exception;
	
	@Query("FROM UserProfileDocument where documentid > ?1 AND documentid <= ?2")
	public List<UserProfileDocument> getUserProfileDocumentList(Long startLimit, Long endLimit) throws Exception;
}
