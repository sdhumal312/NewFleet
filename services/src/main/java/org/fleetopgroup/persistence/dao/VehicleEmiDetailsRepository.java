package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleEmiDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleEmiDetailsRepository extends JpaRepository<VehicleEmiDetails, Long>{

	@Query("FROM VehicleEmiDetails where vid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<VehicleEmiDetails> validateEmiDetails(Integer vid, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleEmiDetails SET markForDelete = 1 where vehicleEmiDetailsId = ?1")
	public void deleteVehicleEmiDetailsById(Long vehicleEmiDetailsId) throws Exception;
	
	@Query("FROM VehicleEmiDetails where vehicleEmiDetailsId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public VehicleEmiDetails getVehicleEmiDetailsById(Long vehicleEmiDetailsId, int companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleEmiDetails SET emiStatus = ?1 WHERE vehicleEmiDetailsId = ?2 AND companyId = ?3 AND markForDelete = 0")
	public void updateEmiStatus(short status, Long vehicleEmiDetailsId, int companyId) throws Exception;
}
