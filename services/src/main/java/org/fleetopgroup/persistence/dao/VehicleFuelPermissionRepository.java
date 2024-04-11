package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VehicleFuelPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleFuelPermissionRepository extends JpaRepository<VehicleFuelPermission, Long>{

	@Query("FROM VehicleFuelPermission VS where VS.fid = ?1 AND VS.companyId = ?2 AND VS.markForDelete = 0 ")
	VehicleFuelPermission getVehicleFuelByID(long fid, Integer companyId);
	
	
	@Modifying
	@Query("UPDATE VehicleFuelPermission  VS SET VS.markForDelete = 1 where VS.fid = ?1 AND VS.companyId = ?2")
	void deleteVehicleFuel(long sid, Integer companyId);
}
