package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.RenewalMailConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RenewalMailConfigurationRepository extends JpaRepository<RenewalMailConfiguration, Long> {
	
	@Query("FROM RenewalMailConfiguration E where E.markForDelete = 0")
	public List<RenewalMailConfiguration> getAllReminderEmailAlertQueueDetails() throws Exception;
	
	@Query("FROM RenewalMailConfiguration E where E.companyId=?1  AND  E.markForDelete = 0")
	public List<RenewalMailConfiguration> getAllReminderEmail_Ids(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE RenewalMailConfiguration SET emailIds=?1, lastModifiedById=?2 WHERE configurationId =?3 AND companyId=?4")
	public void updateReminderEmail_Ids(String email, long userId, Long id, Integer companyId);
	
	@Query("FROM RenewalMailConfiguration E where E.companyId=?1  AND  E.markForDelete = 0 AND E.emailType = 2 ")
	public RenewalMailConfiguration getRenewalMailByCompanyIdAndEmailType(Integer companyId) throws Exception;
	
	
	
	@Query("FROM RenewalMailConfiguration E where E.companyId=?1  AND  E.markForDelete = 0")
	List<RenewalMailConfiguration> getAllCompanyInfEmail_Ids(Integer companyId) throws Exception;
		
	@Modifying
	@Query("UPDATE RenewalMailConfiguration SET emailIds=?1, lastModifiedById=?2, lastModifiedOn=?3 WHERE configurationId =?4 AND companyId =?5 AND emailType =?6")	
	public void updateCompanyUsersEmail_Ids(String email,long userId,Timestamp toDate,Long configurationId, Integer companyId,Short emailType);
	
	@Query("FROM RenewalMailConfiguration E where E.markForDelete = 0 AND E.emailType=?1")
	public List<RenewalMailConfiguration> getAllEmailTripSheetDailyWorkStatusDetails(short emailType) throws Exception;
	
	
}


