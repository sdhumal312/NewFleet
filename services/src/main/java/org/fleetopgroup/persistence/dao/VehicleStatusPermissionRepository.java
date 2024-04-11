package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleStatusPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface VehicleStatusPermissionRepository extends JpaRepository<VehicleStatusPermission, Long>{

	@Query("FROM VehicleStatusPermission VS where VS.sid = ?1 AND VS.companyId = ?2 AND VS.markForDelete = 0 ")
	VehicleStatusPermission getVehicleStatusByID(long sid, Integer companyId);
	
	
	@Modifying
	@Query("UPDATE VehicleStatusPermission  VS SET VS.markForDelete = 1 where VS.sid = ?1 AND VS.companyId = ?2")
	void deleteVehicleStatus(long sid, Integer companyId);
    
	
	@Query("FROM VehicleStatusPermission VS where  VS.companyId = ?2 AND VS.markForDelete = 0 ")
	List<VehicleStatusPermission> getVehicleStatusList(Integer companyId);
}
