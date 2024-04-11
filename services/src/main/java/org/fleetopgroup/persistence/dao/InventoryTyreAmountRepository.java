/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.InventoryTyreAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface InventoryTyreAmountRepository extends JpaRepository<InventoryTyreAmount, Long> {

	// public void add_Tyre_Inventory_Amount(InventoryTyreAmount TyreAmount)
	// throws Exception;

	// This Find ITYRE_ID Entries in InventoryTyreAmount details
	@Query("From InventoryTyreAmount WHERE ITYRE_ID=?1 AND COMPANY_ID = ?2  AND markForDelete = 0")
	public List<InventoryTyreAmount> Get_List_InventoryTyreAmount(Long ITYRE_ID, Integer companyId) throws Exception;

	@Query("From InventoryTyreAmount WHERE ITYRE_AMD_ID=?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public InventoryTyreAmount Get_AMount_ID_InventoryTyreAmount(Long ITYRE_Aomunt_ID, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE InventoryTyreAmount SET TYRE_ASSIGN_NO=?1  WHERE ITYRE_AMD_ID=?2 ")
	public void Update_Inventory_Amount_Complete_Tyre_Number(Integer done_TyreSerialNo, Long Amount_ID) throws Exception;
	
	
	@Modifying
	@Query("UPDATE InventoryTyreAmount SET markForDelete = 1 WHERE ITYRE_AMD_ID=?1 AND COMPANY_ID = ?2")
	public void delete_Tyre_Inventory_Amount(Long ITYRE_Amount_ID, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE InventoryTyreAmount SET markForDelete = 1 WHERE ITYRE_ID=?1 AND COMPANY_ID = ?2")
	public void deleteTyreInventoryAmountByInvoiceId(Long invoiceId, Integer companyId) throws Exception;


}
