package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleGroupPermision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleGroupPermissionRepository extends JpaRepository<VehicleGroupPermision, Long> {

	@Modifying
	@Query("DELETE VehicleGroupPermision VG  where VG.user_id = ?1 AND VG.companyId= ?2")
	void deleteVehicleGroupPrmissionByUserId(long gid, Integer companyId);

	  @Query("SELECT VG.vg_per_id FROM  VehicleGroupPermision VG  where VG.user_id = ?1 AND VG.companyId= ?2")
	  List<Long> getVehicleGroupByUserId(Long id, Integer companyId) throws Exception;
}
