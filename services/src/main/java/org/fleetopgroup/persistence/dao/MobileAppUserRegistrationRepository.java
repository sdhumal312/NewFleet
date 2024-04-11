package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.MobileAppUserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MobileAppUserRegistrationRepository extends JpaRepository<MobileAppUserRegistration, Long>{
	@Transactional
	@Modifying
	@Query("update MobileAppUserRegistration  set tokenForNotification = ?1 where userId = ?2 and companyId = ?3")
	void updateUserTokenForNotification(String tokenForNotification, Long userId, Long companyId)throws Exception;
	
	@Query("select tokenForNotification from  MobileAppUserRegistration   where userId = ?1 and companyId = ?2")
	String getTokenForNotification(Long userId,Long companyId)throws Exception;
}
