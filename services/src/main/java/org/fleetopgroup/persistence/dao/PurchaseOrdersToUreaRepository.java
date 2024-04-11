package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.PurchaseOrdersToUrea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrdersToUreaRepository extends JpaRepository<PurchaseOrdersToUrea, Long>{
	
	@Modifying
	@Query("UPDATE PurchaseOrdersToUrea POU SET POU.markForDelete = 1  where POU.purchaseOrdersToUreaId = ?1 AND POU.companyId = ?2 ")
	public void deletePurchaseOrdersById(Long purchaseOrdersToUreaId,  Integer company_id);
	
	@Query("From PurchaseOrdersToUrea POU where POU.purchaseOrdersToUreaId = ?1 AND POU.companyId = ?2 AND POU.markForDelete = 0 ")
	public PurchaseOrdersToUrea findByPurchaseOrdersToUreaId(Long findByPurchaseOrdersToUreaId , Integer companyId);

	@Modifying
	@Query("UPDATE PurchaseOrdersToUrea POU SET POU.recivedQuantity = ?1, POU.notRecivedQuantity = ?2, POU.received_quantity_remark = ?3 where POU.purchaseOrdersToUreaId = ?4 AND POU.companyId = ?5 ")
	public void updateReceivedQuantityOfUrea(double receivedQuantity, double noReceivedQuantity, String receivedQuantityRemark, long partId, Integer company_id);

	@Query("From PurchaseOrdersToUrea POU where POU.purchaseOrderId = ?1 AND POU.companyId = ?2 AND POU.markForDelete = 0 ")
	public List<PurchaseOrdersToUrea> getPurchaseOrdersToUreaByPurchaseOrderId(long purchaseOrderId, Integer company_id);

	@Modifying
	@Query("UPDATE PurchaseOrdersToUrea POU SET POU.recivedQuantity = 0 where POU.purchaseOrderId = ?1 AND POU.companyId = ?2 AND POU.markForDelete = 0 ")
	public void updateReceiveQuantityOfPurchaseOrdersToUrea(Long purchaseOrderId,  Integer company_id);

	@Modifying
	@Query("UPDATE PurchaseOrdersToUrea SET quantity = ?1, notRecivedQuantity =?1, totalcost = ?2, approvalPartStatusId =?3, remark =?4 where purchaseOrdersToUreaId = ?5 AND companyId = ?6 AND markForDelete = 0")
	public void updatePurchaseOrderUreaQuantityAndStatus(double quantity, double totalCost, short status,String remark, long purchaseOrdersToUreaId, Integer companyId);
	

}
