package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.InventoryLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InventoryLocationRepository extends JpaRepository<InventoryLocation, Long> {

	@Query("FROM InventoryLocation WHERE partid = ?1 AND locationId = ?2 AND companyId = ?3 AND markForDelete = 0 ")
	public List<InventoryLocation> Validate_Inventory_Location(Long part_id, Integer locationId, Integer companyId) throws Exception;

	@Query("SELECT P.inventory_location_id FROM InventoryLocation AS P WHERE P.locationId = ?1 AND P.location_quantity > 0.0 AND P.companyId = ?2 AND P.markForDelete = 0")
	public Page<InventoryLocation> getDeployment_Page_InventoryLocation(Integer locationId, Integer companyId, Pageable pageable);

	@Query("SELECT P.inventory_location_id FROM InventoryLocation AS P "
			+ " INNER JOIN MasterParts MP ON MP.partid = P.partid"
			+ " WHERE P.locationId = ?1 AND P.location_quantity > 0.0 AND P.companyId = ?2 AND P.markForDelete = 0 group by MP.partname")
	public Page<InventoryLocation> getDeployment_Page_InventoryLocationOnName(Integer locationId, Integer companyId, Pageable pageable);

	
	@Query("FROM InventoryLocation WHERE location = ?1 AND markForDelete = 0")
	public List<InventoryLocation> list_Of_Location_Inventory_Quantity(String location) throws Exception;

	@Query("FROM InventoryLocation WHERE inventory_location_id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public InventoryLocation getInventoryLocation(Long Location_id, Integer companyId) throws Exception;

	@Query("FROM InventoryLocation WHERE inventory_all_id = ?1 AND companyId =?2 AND markForDelete = 0")
	public List<InventoryLocation> Get_InventoryAll_id_To_Inventory_Location(Long inventory_all_id, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE InventoryLocation SET markForDelete = 1 WHERE inventory_location_id = ?1 AND companyId = ?2")
	public void deleteLocationInventory(Long Inventory_Location_id, Integer companyId) throws Exception;
	
	@Query("FROM InventoryLocation WHERE inventory_location_id = ?1 AND companyId =?2 AND markForDelete = 0")
	public List<InventoryLocation> getInventoryLocationById(Long Location_id, Integer companyId) throws Exception;
	
	@Query("SELECT P.inventory_location_id FROM InventoryLocation AS P "
			+ " INNER JOIN LowStockInventory LO ON LO.partid = P.partid AND LO.locationId = ?2"
			+ " WHERE P.locationId = ?2 AND P.companyId = ?1 AND (P.location_quantity <= LO.lowstocklevel) AND P.location_quantity > 0.00 AND P.markForDelete = 0")
	public Page<InventoryLocation> getDeployment_Page_LowStockInventoryLocation(Integer companyId, Integer location, Pageable pageable);

	@Query("SELECT P.inventory_location_id FROM InventoryLocation AS P "
			+ " INNER JOIN PartLocationPermission PLP ON PLP.partLocationId = P.locationId AND PLP.user_Id = ?2"
			+ " INNER JOIN LowStockInventory LO ON LO.partid = P.partid AND LO.locationId = ?3"
			+ " WHERE P.locationId = ?3 AND P.companyId = ?1 AND (P.location_quantity <= LO.lowstocklevel) AND P.location_quantity > 0.00 AND P.markForDelete = 0")
	public Page<InventoryLocation> getDeployment_Page_LowStockInventoryLocation(Integer companyId, Long id, Integer location, Pageable pageable);


}