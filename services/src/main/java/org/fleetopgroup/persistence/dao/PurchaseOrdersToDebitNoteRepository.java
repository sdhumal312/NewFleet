package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.PurchaseOrdersToDebitNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrdersToDebitNoteRepository extends JpaRepository<PurchaseOrdersToDebitNote, Long> {

	/* save purchasestasktoparts To PurchaseOrdersToDebitNote */
	// public void addPurchaseOrdersToDebitNote(PurchaseOrdersToDebitNote
	// purchaseOrdersToDebitNote) throws Exception;

	// Get List to DebitNote
	@Query("from PurchaseOrdersToDebitNote where Purchaseorder_id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<PurchaseOrdersToDebitNote> getPurchaseOrdersTasksToDebitNote(long purchaseOrders_id, Integer companyId);

	@Query("from PurchaseOrdersToDebitNote where purchaseorderto_debitnoteid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public PurchaseOrdersToDebitNote Get_PurchaseOrdersTasks_ToDebitNoteID(Long purchaseorderto_debitnoteid, Integer companyid);

	@Modifying
	@Query("UPDATE PurchaseOrdersToDebitNote SET markForDelete = 1 where purchaseorderto_debitnoteid = ?1 AND companyId = ?2")
	public void Delete_PurchaseOrders_ToDebitNoteID(Long purchaseorderto_debitnoteid, Integer companyId);
	
	@Modifying
	@Query("UPDATE PurchaseOrdersToDebitNote SET markForDelete = 1 where purchaseorder_id = ?1 AND companyId = ?2")
	public void deleteDebitNoteByPOId(Long purchaseorder_id, Integer companyId);
	

	@Modifying
	@Query("UPDATE PurchaseOrdersToDebitNote SET markForDelete = 1 where purchaseorderto_partid = ?1 AND companyId = ?2")
	public void deleteDebitNoteByPOIdByPartId(Long purchaseorder_id, Integer companyId);
}
