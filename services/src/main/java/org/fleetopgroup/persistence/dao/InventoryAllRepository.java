package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.InventoryAll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InventoryAllRepository extends JpaRepository<InventoryAll, Long> {

	// get inventory Validate in Database
	@Query("FROM InventoryAll WHERE partid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<InventoryAll> Validate_InventoryAll(Long part_id, Integer companyId) throws Exception;

	@Query("FROM InventoryAll WHERE partid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public InventoryAll Purchase_Order_Validate_InventoryAll(Long part_id, Integer companyId) throws Exception;

	// get InventoryAll Details
	@Query("FROM InventoryAll WHERE inventory_all_id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public InventoryAll getInventoryAll(Long sid, Integer companyId) throws Exception;

//	@Query("FROM InventoryAll WHERE partid = ?1 AND companyId = ?2 AND markForDelete = 0 ")
//	public InventoryAll Get_InventoryAll_USEING_PART_ID(Long Part_ID, Integer companyId) throws Exception;
//	
	// delete InventoryAll All quantity
	@Modifying
	@Query("UPDATE InventoryAll SET markForDelete = 1 WHERE inventory_all_id= ?1 AND companyId =?2")
	public void deleteInventoryAll(Long Inventory_All_id, Integer companyId) throws Exception;
	
	@Query("SELECT IA.inventory_all_id FROM InventoryAll IA WHERE IA.companyId = ?1 AND IA.markForDelete = 0 and IA.all_quantity > 0")
	public Page<InventoryAll> getDeployment_Page_InventoryAll(Integer companyId, Pageable pageable) throws Exception;

	@Query("SELECT IA.inventory_all_id FROM InventoryAll IA "
			+ " INNER JOIN MasterParts MP ON MP.partid = IA.partid"
			+ " WHERE IA.companyId = ?1 AND IA.markForDelete = 0 and IA.all_quantity > 0 group by MP.partname")
	public Page<InventoryAll> getDeployment_Page_InventoryAllOnName(Integer companyId, Pageable pageable) throws Exception;

	
}