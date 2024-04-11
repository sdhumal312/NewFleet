/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VehicleModelTyreLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fleetop
 *
 */
public interface VehicleModelTyreLayoutRepository extends JpaRepository<VehicleModelTyreLayout, Integer> {

	
	@Query("FROM VehicleModelTyreLayout where vehicleModelId =?1 AND companyId = ?2 AND markForDelete = 0")
	public VehicleModelTyreLayout getvehicleTyreModelLayoutByModelId(long vehicleModelId, int companyId);
	
	@Transactional
	@Modifying
	@Query("UPDATE VehicleModelTyreLayout SET markForDelete = 1 where vehicleModelTyreLayoutId =?1 AND companyId = ?2")
	public void deleteVehicleModelTyreLayout(long vehicleModelTyreLayoutId, int companyId);
	

}
