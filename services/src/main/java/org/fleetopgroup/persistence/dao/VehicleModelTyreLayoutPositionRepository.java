/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleModelTyreLayoutPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fleetop
 *
 */
public interface VehicleModelTyreLayoutPositionRepository extends JpaRepository<VehicleModelTyreLayoutPosition, Integer> {

	@Transactional
	@Modifying
	@Query("UPDATE VehicleModelTyreLayoutPosition SET markForDelete = 1 where vehicleModelTyreLayoutId =?1 AND companyId = ?2")
	void deleteVehicleModelTyreLayoutPosition(long vehicleModelTyreLayoutId, int companyId);

	
    @Query("FROM VehicleModelTyreLayoutPosition where vehicleModelTyreLayoutId = ?1 and markForDelete = 0")
	public List<VehicleModelTyreLayoutPosition>  getBYvehicleModelTyreLayoutId(Long vehicleModelTyreLayoutId) throws Exception;



}