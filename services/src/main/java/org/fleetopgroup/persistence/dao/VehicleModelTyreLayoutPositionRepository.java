/**
 * 
 */
package org.fleetopgroup.persistence.dao;

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
	void deleteVehicleModelTyreLayoutPosition(long vehicleModelTyreLayoutId, int companyId);}
