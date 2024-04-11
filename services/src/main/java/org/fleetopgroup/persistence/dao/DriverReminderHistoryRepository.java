package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.DriverReminderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;



public interface DriverReminderHistoryRepository extends JpaRepository<DriverReminderHistory, Integer>  {
	

	//public void saveDriverReminderHistory(DriverReminderHistory driverReminderHistory);

	//public void updateDriverReminderHistory(DriverReminderHistory driverReminderHistory);

	@Query("From DriverReminderHistory where driver_id= ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<DriverReminderHistory> listDriverReminderHis(int diverRemHis_id, Integer companyId);

	@Query("From DriverReminderHistory where driver_rhid= ?1 AND companyId = ?2 AND markForDelete = 0")
	public DriverReminderHistory getDriverReminderHis(int driver_RemHisid, Integer companyId);

	@Modifying
	@Query("UPDATE DriverReminderHistory SET markForDelete = 1 WHERE driver_rhid = ?1 AND companyId = ?2")
	public void deleteDriverReminderHis(int driver_RemHistid, Integer companyId);

}
