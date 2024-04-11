package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.RenewalReminderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RenewalReminderHistoryRepository extends JpaRepository<RenewalReminderHistory, Long> {

	@Modifying
	@Query("From RenewalReminderHistory where driver_id= ?1")
	public void getRenewalReminderHistoryList(Integer driver_id);

	public List<RenewalReminderHistory> findAll();

	@Query("From RenewalReminderHistory where vid= ?1 ")
	public List<RenewalReminderHistory> listRenewalReminderHistory(Integer vehicle_id) throws Exception;

	@Query("From RenewalReminderHistory where renewalhis_id= ?1 ")
	public RenewalReminderHistory getRenewalReminderHistory(Long renewalhis_id);

	@Modifying
	@Query("UPDATE RenewalReminderHistory RRH SET RRH.markForDelete = 1 WHERE RRH.renewalhis_id = ?1 AND companyId = ?2")
	public void deleteRenewalReminderHistory(Long renewalReminderHistory, Integer companyId);

}
