package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleBatteryLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleBatteryLayoutRepository extends JpaRepository<VehicleBatteryLayout, Long>{

	@Query("from VehicleBatteryLayout where vid = ?1 and companyId = ?2 AND markForDelete = 0")
	public List<VehicleBatteryLayout> getVehicleBatteryLayout(Integer vid, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE From VehicleBatteryLayout SET batteryId=?1, batteryAsigned=?2  WHERE layoutId =?3")
	public void update_Position_Assign_Battery_To_Vehicle(Long batteryId , boolean batteryAsigned, Long layoutId) throws Exception;
	
	@Modifying
	@Query("UPDATE From VehicleBatteryLayout SET markForDelete = 1 WHERE vid =?1")
	public void deleteBatteryLayout(Integer vid) throws Exception;
}
