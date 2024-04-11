package org.fleetopgroup.persistence.dao;

import java.util.Date;

import org.fleetopgroup.persistence.model.TwoFactorAuthenticationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TwoFactorAuthenticationDetailsRepository extends JpaRepository<TwoFactorAuthenticationDetails, Long>{
	
	@Query("FROM TwoFactorAuthenticationDetails where userId = ?1 AND markForDelete = 0")
	public TwoFactorAuthenticationDetails getTwoFactorAuthenticationDetails(Long userId) throws Exception;
	
	@Modifying
	@Query("UPDATE TwoFactorAuthenticationDetails SET OtpValidatedOn = ?1 where userId = ?2 AND markForDelete = 0")
	public void updateLastOTPValidated(Date	date, Long userId) throws Exception;
}
