package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.PurchaseOrdersToParts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrdersToPartsRepository extends JpaRepository<PurchaseOrdersToParts, Long> {

	@Query("FROM PurchaseOrdersToParts p WHERE p.purchaseorderto_partid= ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public PurchaseOrdersToParts getPurchaseOrdersToParts(long purchaseorderto_partid, Integer companyId);

	@Query("FROM PurchaseOrdersToParts where purchaseorder_id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<PurchaseOrdersToParts> getPurchaseOrdersTasksToParts(long purchaseOrders_id, Integer companyId);

	@Query("FROM PurchaseOrdersToParts where purchaseorder_id = ?1 AND markForDelete = 0")
	public List<PurchaseOrdersToParts> getPurchaseOrdersTasksIDToPartsList(long purchaseOrders_id);

	@Modifying
	@Query("UPDATE PurchaseOrdersToParts SET markForDelete = 1 where Purchaseorderto_partid= ?1 AND companyId = ?2")
	public void deletePurchaseOrdersTOParts(Long purchaseOrdersTask_id, Integer companyId);
	
	@Modifying
	@Query("UPDATE PurchaseOrdersToParts SET received_quantity = 0, notreceived_quantity = quantity where purchaseorder_id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public void updateFromReceivedToOrderedStatus(Long purchaseorder_id, Integer companyId);

	@Modifying
	@Query("UPDATE PurchaseOrdersToParts SET quantity = ?1, notreceived_quantity =?1, totalcost = ?2, approvalPartStatusId =?3, remark =?4 where Purchaseorderto_partid = ?5 AND companyId = ?6 AND markForDelete = 0")
	public void updatePurchaseOrderPartQuantityAndStatus(double quantity, double totalCost, short status,String remark, long Purchaseorderto_partid, Integer companyId);

	@Query("FROM PurchaseOrdersToParts where purchaseorder_id = ?1 AND companyId = ?2 AND markForDelete = 0 AND approvalPartStatusId = 2")
	public List<PurchaseOrdersToParts> getPurchaseOrdersApprovedTasksToParts(long purchaseOrders_id, Integer companyId);
	
	@Modifying
	@Query("UPDATE PurchaseOrdersToParts SET markForDelete = 1 where purchaseorder_id= ?1 AND companyId = ?2")
	public void deletePurchaseOrdersParts(Long purchaseorder_id, Integer companyId);

}
