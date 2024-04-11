/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.InventoryTyreRetreadAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface InventoryTyreRetreadAmountRepository extends JpaRepository<InventoryTyreRetreadAmount, Long> {

	// public void add_Tyre_Inventory_Amount(InventoryTyreRetreadAmount TyreAmount)
	// throws Exception;

	@Query("From InventoryTyreRetreadAmount WHERE TRID=?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public List<InventoryTyreRetreadAmount> Get_InventoryTyreRetread_Amount(Long TRID, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE From InventoryTyreRetreadAmount SET RETREAD_COST=?1, RETREAD_DISCOUNT=?2, RETREAD_TAX=?3, RETREAD_AMOUNT=?4  WHERE TR_AMOUNT_ID=?5 AND COMPANY_ID = ?6")
	public void update_Inventory_Retread_Tyre_cost(Double RETREAD_COST, Double RETREAD_DISCOUNT, Double RETREAD_TAX, Double RETREAD_AMOUNT, Long TR_AMOUNT_ID, Integer companyId) throws Exception;
	
	
	@Modifying
	@Query("UPDATE InventoryTyreRetreadAmount SET markForDelete = 1 WHERE TR_AMOUNT_ID=?1 AND COMPANY_Id = ?2")
	public void delete_Inventory_Retread_Amount_Tyre_cost(Long IR_Amount_ID, Integer companyId) throws Exception;
	
	
	@Modifying
	@Query("UPDATE From InventoryTyreRetreadAmount SET TRA_STATUS_ID=?1 WHERE TR_AMOUNT_ID=?2 AND COMPANY_Id = ?3 ")
	public void Update_RECEVIED_RetreadAmount_Status(short Status, Long TyreRetread_id, Integer companyId) throws Exception;
	
	@Query("From InventoryTyreRetreadAmount WHERE TR_AMOUNT_ID=?1")
	public InventoryTyreRetreadAmount getRetreadTyreAmountDetailsByTR_AMOUNT_ID(Long tr_AMOUNT_ID);
}
