package org.fleetopgroup.persistence.dao;

import java.util.List;

/**
 * @author fleetop
 *
 *
 *
 */

import org.fleetopgroup.persistence.model.PurchaseOrdersDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrdersDocumentRepository extends JpaRepository<PurchaseOrdersDocument, Long> {

	// upload Document save
	// public void uploadPurchaseOrdersDocument(PurchaseOrdersDocument
	// purchaseOrdersdocument) throws Exception;

	// get Document
	@Query("from PurchaseOrdersDocument where Purchaseorder_id = ?1 AND markForDelete = 0")
	public PurchaseOrdersDocument getPurchaseOrdersDocument(Long purchaseOrders_id) throws Exception;

	// get Document
	@Query("from PurchaseOrdersDocument where Purchaseorder_id = ?1 and  purchaseorder_document =?2 AND companyId = ?3 AND markForDelete = 0")
	public PurchaseOrdersDocument ValidatePurchaseOrdersDocument(Long purchaseOrders_id, String purchaseorder_document, Integer companyid)
			throws Exception;
	
	@Query("from PurchaseOrdersDocument where purchaseorder_documentid = ?1 AND markForDelete = 0")
	public PurchaseOrdersDocument getPurchaseOrdersDocumentDetails(Long purchaseOrders_id) throws Exception;

	@Query("SELECT MAX(purchaseorder_documentid) FROM PurchaseOrdersDocument")
	public long getPurchaseOrdersDocumentMaxId() throws Exception;
	
	@Query("FROM PurchaseOrdersDocument where purchaseorder_documentid > ?1 AND purchaseorder_documentid <= ?2")
	public List<PurchaseOrdersDocument> getPurchaseOrdersDocumentList(Long startLimit, Long endLimit) throws Exception;

}
