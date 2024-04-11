/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleManufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface VehicleManufacturerRepository extends JpaRepository<VehicleManufacturer, Integer> {

	@Query("From VehicleManufacturer VM where VM.companyId = ?1 and VM.markForDelete = 0 ")
	List<VehicleManufacturer> findBycompanyId(Integer companyId);

	@Query("From VehicleManufacturer VM where VM.vehicleManufacturerName =?1 AND VM.companyId = ?2 AND VM.markForDelete = 0 ")
	public VehicleManufacturer findByName(String vehicleManufacturerName, Integer companyId);
	
	@Query("From VehicleManufacturer VM where VM.vehicleManufacturerId = ?1 AND VM.companyId = ?2 AND VM.markForDelete = 0 ")
	public VehicleManufacturer findByVehicleManufacturerId(Long vehicleManufacturerId , Integer companyId);

	@Modifying
	@Query("UPDATE VehicleManufacturer VM SET VM.vehicleManufacturerName =?2, VM.description =?3 where VM.vehicleManufacturerId = ?1 AND VM.companyId = ?4 AND VM.markForDelete = 0 ")
	public void updateVehicleManufacturer(Long vehicleManufacturerId, String vehicleManufacturerName, String description, Integer company_id);
	
	@Modifying
	@Query("UPDATE VehicleManufacturer VM SET VM.markForDelete = 1  where VM.vehicleManufacturerId = ?1 AND VM.companyId = ?2 ")
	public void deleteVehicleManufacturer(Long vehicleManufacturerId,  Integer company_id);
}
