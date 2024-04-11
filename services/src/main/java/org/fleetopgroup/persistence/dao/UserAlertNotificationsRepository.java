package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.UserAlertNotifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserAlertNotificationsRepository extends JpaRepository<UserAlertNotifications, Long>{

	@Query("SELECT COUNT(userAlertNotificationsId) FROM UserAlertNotifications where userId = ?1 AND status = 1 AND markForDelete = 0")
	public Long getNotificationCountForUser(Long userId)throws Exception;
	
	
	@Query("SELECT COUNT(userAlertNotificationsId) FROM UserAlertNotifications where userId = ?1 AND txnTypeId = ?2 AND status = 1 AND markForDelete = 0")
	public Long getNotificationCountFortxnTypeId(Long userId,Short txnTypeId)throws Exception;
	
	@Query("SELECT BI.userAlertNotificationsId From UserAlertNotifications as BI where BI.userId = ?1 AND BI.status = 1 AND BI.markForDelete = 0")
	public Page<UserAlertNotifications> getUnReadPageObject(Long userId, Pageable pageable);
	
	@Modifying
	@Transactional
	@Query("UPDATE UserAlertNotifications SET status = 2 where userAlertNotificationsId = ?1 ")
	public void markNotificationAsRead(Long userAlertNotificationsId)throws Exception;
	
	@Query("SELECT BI.userAlertNotificationsId From UserAlertNotifications as BI where BI.userId = ?1 AND BI.status = 2 AND BI.markForDelete = 0")
	public Page<UserAlertNotifications> getReadPageObject(Long userId, Pageable pageable);
	
	@Query("SELECT BI.userAlertNotificationsId From UserAlertNotifications as BI where BI.createdById = ?1 AND BI.markForDelete = 0")
	public Page<UserAlertNotifications> getSentPageObject(Long userId, Pageable pageable);
	
	
}
