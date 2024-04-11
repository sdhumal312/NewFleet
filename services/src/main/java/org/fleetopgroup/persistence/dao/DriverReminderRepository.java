package org.fleetopgroup.persistence.dao;

import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.DriverReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DriverReminderRepository extends JpaRepository<DriverReminder, Integer> {

	// public void addDriverReminder(DriverReminder diverreminder);

	// public void updateDriverReminder(DriverReminder diverreminder);

	@Modifying
	@Query("From DriverReminder d where d.driver_id= ?1  AND d.markForDelete = 0")
	public void getDriverReminderList(Integer diverreminder);

	@Query("From DriverReminder")
	public List<DriverReminder> listDriverReminder();

	@Query("From DriverReminder where driver_id= ?1  AND markForDelete = 0")
	public List<DriverReminder> listDriverReminder(int diverreminder);

	@Query("From DriverReminder where driver_remid= ?1 AND companyId = ?2 AND markForDelete = 0")
	public DriverReminder getDriverReminder(int driver_remid, Integer companyId);

	@Modifying
	@Query("UPDATE DriverReminder DR SET markForDelete = 1 WHERE driver_remid = ?1 AND companyId = ?2")
	public void deleteDriverReminder(Integer diverreminder, Integer companyId);

	@Modifying
	@Query("select count(d.driver_id) from DriverReminder d where d.driver_id= ?1  AND d.markForDelete = 0")
	public Integer getReminderCount(int driver_remid);

	@Query("select count(d.driver_id) from DriverReminder d where d.driver_dlto = ?1  AND d.markForDelete = 0")
	public Long countToDay_DL_Renewal(Date currentdate) throws Exception;

	@Query("from DriverReminder d where d.driver_dlto = ?1 AND d.markForDelete = 0")
	public List<DriverReminder> listof_ToDay_DL_Renewal(Date currentdate);
	
	@Modifying
	@Query("UPDATE DriverReminder DR SET renewalRecieptId = ?1 , newDriverReminder = 1 WHERE driver_remid = ?2 AND companyId = ?3")
	public void updateRenewalReceiptId(Long renewalRecieptId,int driver_remid, Integer companyId);
	
	
	@Modifying
	@Query("UPDATE DriverReminder DR SET newDriverReminder = 0 WHERE driver_remid = ?1 AND companyId = ?2 ")
	public void updateDeletedRenewalReceipt(int driver_remid, Integer companyId);
	
	
	@Modifying
	@Query("UPDATE DriverReminder DR SET newDriverReminder = 1 WHERE driver_remid = ?1 AND companyId = ?2")
	public void updateOldDriverReminder(Integer diverreminder, Integer companyId);
	
	

}
