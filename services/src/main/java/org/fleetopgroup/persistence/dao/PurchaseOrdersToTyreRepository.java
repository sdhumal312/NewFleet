package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.PurchaseOrdersToTyre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrdersToTyreRepository extends JpaRepository<PurchaseOrdersToTyre, Long> {

	
	@Query("FROM PurchaseOrdersToTyre p WHERE p.PO_TO_TYREID= ?1 AND p.markForDelete = 0")
	public PurchaseOrdersToTyre getPurchaseOrdersToTyre(long PO_TO_TYREID);

	
	// Update Task TotalCost
	@Query("from PurchaseOrdersToTyre where PO_ID = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<PurchaseOrdersToTyre> getPurchaseOrdersTasksIDToTyreList(long PO_ID, Integer companyId);

	// Delete PurchaseOrder
	@Modifying
	@Query("UPDATE PurchaseOrdersToTyre SET markForDelete = 1 where PO_TO_TYREID= ?1")
	public void deletePurchaseOrdersToTyre(long purchaseOrders_id);
	
	@Modifying
	@Query("UPDATE PurchaseOrdersToTyre SET markForDelete = 1 where PO_ID= ?1 AND companyId = ?2 AND markForDelete = 0")
	public void deletePurchaseOrdersToTyreByPOId(long poId, Integer companyId);

}
