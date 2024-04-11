/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface VehicleModelRepository extends JpaRepository<VehicleModel, Integer> {

	@Query("From VehicleModel VM where VM.companyId = ?1 and VM.markForDelete = 0 ")
	List<VehicleModel> findBycompanyId(Integer companyId);

	@Query("From VehicleModel VM where VM.vehicleModelName =?1 AND VM.companyId = ?2 AND VM.markForDelete = 0 ")
	public VehicleModel findByName(String vehicleModelName, Integer companyId);
	
	@Query("From VehicleModel VM where VM.vehicleModelId = ?1 AND VM.companyId = ?2 AND VM.markForDelete = 0 ")
	public VehicleModel findByVehicleModelId(Long vehicleModelId , Integer companyId);

	@Modifying
	@Query("UPDATE VehicleModel VM SET VM.vehicleModelName =?2, VM.description =?3, VM.vehicleManufacturerId = ?5 "
			+ " where VM.vehicleModelId = ?1 AND VM.companyId = ?4 AND VM.markForDelete = 0 ")
	public void updateVehicleModel(Long vehicleModelId, String vehicleModelName, String description, 
				Integer company_id, Long vehicleManufacturerId );
	
	@Modifying
	@Query("UPDATE VehicleModel VM SET VM.markForDelete = 1  where VM.vehicleModelId = ?1 AND VM.companyId = ?2 ")
	public void deleteVehicleModel(Long vehicleModelId,  Integer company_id);
}
