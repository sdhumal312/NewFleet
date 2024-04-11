package org.fleetopgroup.persistence.dao;

import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	// save Inventory location All Details and Quantity
	// public void add_Inventory(Inventory Quantity) throws Exception;

	// public void updateInventory(Inventory Inventory) throws Exception;

	public List<Inventory> findAll();
	
	@Query("FROM Inventory WHERE companyId= ?1 AND markForDelete = 0")
	public List<Inventory> findAllInventoryList(Integer companyId);

	// public List<Inventory> SearchlistInventory(String Search) throws
	// Exception;

	@Query("FROM Inventory I"
			+ " INNER JOIN InventoryAll AL ON AL.inventory_all_id = I.inventory_all_id"
			+ " WHERE I.inventory_id = ?1 AND AL.companyId = ?2 and I.markForDelete = 0")
	public Inventory getInventory(Long inventory_id, Integer companyId) throws Exception;
	
	@Query("FROM Inventory WHERE inventory_id= ?1 AND companyId = ?2 AND markForDelete = 0")
	public Inventory getInventoryDetails(Long inventory_id, Integer companyId) throws Exception;

	// delete Inventory quantity
	@Modifying
	@Query("UPDATE Inventory SET markForDelete = 1 WHERE inventory_id = ?1 AND companyId = ?2")
	public void deleteInventory(Long Inventory_id, Integer companyId) throws Exception;

	// public List<Inventory> listOnlyStatus() throws Exception;

	// search Inventory SearchlistInventoryINVoice
	// public List<Inventory> SearchlistInventoryINVoice(String Search) throws
	// Exception;

	// Inventory PartQuentity and location
	@Query("FROM Inventory WHERE location= ?1 AND markForDelete = 0")
	public List<Inventory> getInventoryPartQuantity_Location(String location) throws Exception;

	@Query("select count(*) from InventoryAll ")
	public Long countInventory() throws Exception;

	@Query("select sum(all_quantity) from InventoryAll where markForDelete = 0")
	public List<Long> countLocationTotalQty() throws Exception;

	@Query("select sum(location_quantity) from InventoryLocation where location= ?1 and markForDelete = 0")
	public List<Long> countLocationTotalQty(String Location) throws Exception;

	@Modifying
	@Query("UPDATE Inventory SET quantity= ?1 where inventory_id= ?2 ")
	public void updateInventoryQuantity(Double Quantiy, Long Inventoryid) throws Exception;

	// Subtract in Inventory Quantity From WorkOrder to Assign
	// workOrdersQuantity
	// public void Subtract_update_Inventory_from_Workorder(Double Quantiy, Long
	// Inventoryid) throws Exception;

	// get InventoryAll ID To Inventory Part Location Quantiy
	@Query("FROM Inventory  AS I WHERE I.inventory_all_id = ?1 AND I.companyId = ?2 and I.markForDelete = 0")
	public List<Inventory> Get_InventoryAll_id_To_Inventory(Long inventory_all_id, Integer companyId) throws Exception;

	// get InventoryAll ID To Inventory Part Location Quantiy
	@Query("SELECT I FROM Inventory AS I "
			+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id"
			+ " WHERE I.inventory_all_id = ?1 AND I.quantity > 0.0 AND IAL.companyId = ?2 AND I.markForDelete = 0 ")
	public List<Inventory> Show_AvailableQYT_InventoryAll_id_To_Inventory(Long inventory_all_id, Integer companyId) throws Exception;

	// get WorkOrder InventoryAll ID To Inventory Part Location Quantiy
	@Query("FROM Inventory WHERE inventory_location_id = ?1 AND quantity > 0.0 AND companyId = ?2 and markForDelete = 0 ")
	public List<Inventory> Get_WorkOrder_InventoryLocation_id_To_Inventory(Long inventory_location_id, Integer companyId) throws Exception;

	// Add in Inventory Quantity From WorkOrder to Assign
	// workOrdersQuantity
	// public void Add_update_Inventory_from_Workorder(Double Quantiy, Long
	// Inventoryid) throws Exception;

	// get Inventory_Location ID To Inventory Part Location Quantiy
	@Query("FROM Inventory WHERE inventory_location_id =?1 and markForDelete = 0")
	public List<Inventory> validate_Inventory_Location_id_To_Inventory(Long inventory_location_id) throws Exception;

	// Inventory Report
	// public List<Inventory> Report_listInventory(Inventory inventoryReport)
	// throws Exception;
	@Query("FROM Inventory WHERE ponumber =?1 AND companyId = ?2 and markForDelete = 0")
	public List<Inventory> Get_Validate_InventoryPurchaseOrderId_Details(String purchaseorder_id, Integer companyid);

	@Query("From Inventory as p WHERE p.quantity > 0.0 and markForDelete = 0 GROUP BY p.partid ")
	public Page<Inventory> getDeployment_Page_Inventory_GROUPBY_PARTID_PARTNAME(Pageable pageable);
	
	@Modifying
	@Query("UPDATE Inventory SET locationId =?1, invoice_number=?2, "
		+ "invoice_date=?3, invoice_amount=?4, vendor_id=?5, description=?6,"
		+ " lastModifiedById=?7, lastupdated=?8  WHERE inventory_id=?9 AND companyId = ?10")
	public void update_Part_Inventory(Integer locationId, String invoice_number,
			Date invoice_date, String invoice_amount, Integer vendor_id, String description,
			Long lastModifiedById, Date lastupdated, Long inventory_id, Integer companyId) throws Exception;
	
	
	@Query("FROM Inventory WHERE partInvoiceId =?1 AND companyId = ?2 and markForDelete = 0")
	public List<Inventory> Get_InventoryDetails_From_PartInvoiceId(long partInvoiceId, Integer companyId);
	
	@Query("FROM Inventory WHERE purchaseorder_id =?1 AND companyId = ?2 and markForDelete = 0")
	public List<Inventory> getPartInventoryDetailsFromPartPurchaseOrder(Long purchaseorder_id, Integer companyid);

	@Modifying
	@Query("Update Inventory set subLocationId= ?2 where partInvoiceId= ?1 AND COMPANY_ID = ?3")
	public void updateSublocationInInventory(Long partInvoiceId, Integer subLocationId, Integer company_id);
	
	@Query("FROM Inventory WHERE partid = ?1 AND locationId= ?2 AND companyId =?3 AND quantity > 0.0 AND markForDelete = 0")
	public List<Inventory> getInventoryPartQuantityLocation(Long partId,int location,int companyId) throws Exception;
	
	@Query("FROM Inventory WHERE inventory_id IN(?1) AND companyId =?2  AND markForDelete = 0")
	public List<Inventory> getInventoryPartList(List<Long> inventoryIds,int companyId) throws Exception;

	@Query("FROM Inventory WHERE partid =?1 AND repairStockId =?2 AND markForDelete = 0")
	public Inventory getInventoryByRepairStockId(long partId,long repairStockId);

	@Modifying
	@Query("Update Inventory set markForDelete = 1 where inventory_id= ?1 ")
	public void deleteTransferdInventoryIdFromInventory(long inventoryId);
	

	@Query("FROM Inventory WHERE purchaseorderto_partid= ?1 AND companyId = ?2 AND markForDelete = 0")
	public Inventory getInventoryDetailsByPartId(Long inventory_id, Integer companyId) throws Exception;

}