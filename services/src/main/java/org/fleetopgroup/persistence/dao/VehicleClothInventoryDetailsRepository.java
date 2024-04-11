package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleClothInventoryDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleClothInventoryDetailsRepository extends JpaRepository<VehicleClothInventoryDetails, Long> {

	@Query("FROM VehicleClothInventoryDetails where clothTypesId = ?1 AND vid = ?2 AND locationId =?3 AND markForDelete = 0")
	public VehicleClothInventoryDetails validateVehicleClothInventoryDetails(Long clothTypesId, Integer vehicleId, Integer locationId) throws Exception;
	
	@Query("FROM VehicleClothInventoryDetails where clothTypesId = ?1 AND vid = ?2 AND markForDelete = 0")
	public List<VehicleClothInventoryDetails> checkVehicleClothInventoryDetails(Long clothTypesId, Integer vehicleId) throws Exception;
	
	@Query("SELECT BI.vehicleClothInventoryDetailsId From VehicleClothInventoryDetails as BI where BI.companyId = ?1 "
			+ " AND BI.clothTypesId = ?2 AND BI.locationId = ?3 AND BI.markForDelete = 0")
	public Page<VehicleClothInventoryDetails> getDeployment_Page_ShowInServiceList(Integer companyId, long clothTypeId, int locationId, Pageable pageable);
	
	
	
}
