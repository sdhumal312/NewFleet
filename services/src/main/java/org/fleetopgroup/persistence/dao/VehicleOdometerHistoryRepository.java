package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface VehicleOdometerHistoryRepository extends JpaRepository<VehicleOdometerHistory, Long> {

	@Modifying
	@Query("Update VehicleOdometerHistory set vehicle_Odometer= ?1, voh_date= ?2 where voh_updateId= ?3 ")
	public void updateVehicleOdometerHistory(Integer odometer, Date current, Long voh_updateId) throws Exception;

	@Query("From VehicleOdometerHistory")
	public List<VehicleOdometerHistory> listVehicleOdometerHistory() throws Exception;

	@Query("from VehicleOdometerHistory where vid= ?1 ORDER BY voh_id desc")
	public List<VehicleOdometerHistory> listVehicleOdometerGraph(Integer vid) throws Exception;
	
	@Query(nativeQuery = true, value = "SELECT * FROM vehicleodometerhistory where vid = ?1 AND voh_date >= ?2 AND markForDelete = 0 ORDER BY voh_id ASC LIMIT 1")
	public VehicleOdometerHistory getMonthStartOdoMeterOfVehicle(Integer vid, Timestamp date) throws Exception;

	@Modifying
	@Query("Update VehicleOdometerHistory set markForDelete = 1 where voh_updateId= ?1 AND companyId= ?2 ")
	public void deleteVehicleOdometerHistory(Long voh_updateId, Integer companyId);

	@Modifying
	@Query("Update VehicleOdometerHistory set vohUpdateLocationStatusId= ?1 where voh_id= ?2 AND companyId= ?3 ")
	public void updateVohUpdateLocationStatusIdByVoh_id(short vohUpdateLocationStatusId, Long voh_Id, Integer companyId);

}