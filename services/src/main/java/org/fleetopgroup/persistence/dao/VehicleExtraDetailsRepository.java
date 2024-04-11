package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleExtraDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleExtraDetailsRepository extends JpaRepository<VehicleExtraDetails, Long>{
	
	@Query("FROM VehicleExtraDetails VED WHERE VED.vid = ?1 AND VED.companyId = ?2 AND VED.markForDelete = 0")
	public VehicleExtraDetails getBusId(Integer vid, Integer companyId) throws Exception;
	
	@Query("FROM VehicleExtraDetails WHERE vid = ?1 AND busId = ?2 AND deviceId = ?3  AND companyId = ?4 AND markForDelete = 0")
	public VehicleExtraDetails validateBusDetails(Integer vid, long busId, long deviceId, Integer companyId) throws Exception;
	
	@Query("FROM VehicleExtraDetails WHERE busId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public VehicleExtraDetails validateBusId(long busId, Integer companyId) throws Exception;
	
	@Query("FROM VehicleExtraDetails WHERE deviceId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public VehicleExtraDetails validateDeviceId(long deviceId, Integer companyId) throws Exception;
	
	@Query("FROM VehicleExtraDetails VED WHERE VED.vid = ?1 AND VED.markForDelete = 0")
	public List<VehicleExtraDetails> getVehicleExtraDetailsByVid(Integer vid) throws Exception;
	
	
}