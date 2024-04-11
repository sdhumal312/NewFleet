package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.PurchaseOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrdersRepository extends JpaRepository<PurchaseOrders, Long> {

	// public void addPurchaseOrders(PurchaseOrders purchaseOrders) throws
	// Exception;

	/* purchase order show page to add purchase orders */
	@Query("FROM PurchaseOrders p WHERE p.purchaseorder_id= ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public PurchaseOrders getPurchaseOrders(long purchaseOrders, Integer companyId);

	@Query("SELECT p.purchaseorder_id From PurchaseOrders as p where p.purchaseorder_statusId= ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public Page<PurchaseOrders> getDeployment_Page_PurchaseOrders(short status, Integer companyId, Pageable pageable);

	// show REQUISITION List in main page
	@Query("from PurchaseOrders where purchaseorder_status= ?1")
	public List<PurchaseOrders> listOpenPurchaseOrders(String purchaseorder_status);

	// count purchaseOrder
	@Query(" select count(*) from PurchaseOrders where companyId = ?1 AND markForDelete = 0")
	public Long countPurchaseOrder(Integer companyId) throws Exception;

//	@Modifying
//	@Query(" UPDATE PurchaseOrders SET purchaseorder_subtotal_cost= ?1, purchaseorder_totalcost = ?2 , purchaseorder_balancecost = ?3  where Purchaseorder_id= ?4 AND companyId = ?5")
//	public void updatePurchaseOrderMainTotalCost(Double TotalpurchaseOrderSubcost, Double TotalpurchaseOrdercost,
//			Double TotalpurchaseOrderBalance, Long purchaseOrder_ID, Integer companyId) throws Exception;

	// Update Orderd By details
	// public void updatePurchaseOrderedBYAdavanceCost(PurchaseOrders
	// Update_OrderedBy) throws Exception;

	// Update PurchaseOrderd Parts and Received_Quantity & NOT_Received_Quantity
	@Modifying
	@Query(" UPDATE PurchaseOrdersToParts SET received_quantity = ?1 , received_quantity_remark = ?2, notreceived_quantity = ?3  where purchaseorderto_partid= ?4 AND companyId = ?5")
	public void updatePurchaseOrderPart_ReceivedQuantity(Double TotalPurchaseOrderPart_ReceivedQty,
			String Received_Qty_Remark, Double TotalPurchaseOrderPart_NOT_ReceivedQty, Long TotalPurchaseOrderPart_Id, Integer companyid)
			throws Exception;

	// Update Received By details
	// public void updatePurchaseOrderedToReceived_Quantity(PurchaseOrders
	// Update_OrderedBy) throws Exception;

	// Update Freight
	// public void updatePurchaseOrder_Freight(PurchaseOrders Update_OrderedBy)
	// throws Exception;

	// Update TaxCost
	// public void updatePurchaseOrder_TaxCost(PurchaseOrders Update_OrderedBy)
	// throws Exception;

	// update debit_note_cost to PurchaseOrder Table
	@Modifying
	@Query(" UPDATE PurchaseOrders SET Purchaseorder_total_debitnote_cost = ?1  where Purchaseorder_id= ?2  AND companyId = ?3")
	public void updatePurchaseOrder_Total_DebitCost(Double TotalpurchaseOrder_Debitcost, Long purchaseOrder_ID, Integer companyId)
			throws Exception;

	// Update Complete
	// public void updatePurchaseOrder_Complete(PurchaseOrders Update_Complete)
	// throws Exception;

	// Search Puchchase Orders
	// public List<PurchaseOrders> SearchPurchaseOrders(String
	// purchaseOrders_Search) throws Exception;

	// count Statues
	@Query(" select count(*) from PurchaseOrders Where purchaseorder_status= ?1 ")
	public Long countPurchaseOrderStatues(String Statues) throws Exception;

	// Delete PurchaseOrder
	@Modifying
	@Query("UPDATE PurchaseOrders SET markForDelete = 1,lastModifiedById =?3,lastupdated=?4 WHERE Purchaseorder_id = ?1 AND companyId = ?2 ")
	public void deletePurchaseOrders(Long purchaseOrders_id, Integer companyId,Long userId,Timestamp date);

	// show Report search List
	// public List<PurchaseOrders> Report_PurchaseOrders(PurchaseOrdersDto
	// purchaseOrders);

	@Query("From PurchaseOrders Where purchaseorder_vendor_id =?1 AND purchaseorder_invoiceno= ?2 AND companyId = ?3 AND markForDelete = 0 ")
	public List<PurchaseOrders> Validate_PurchaseOrder_Received_Invoice(Integer VendorID,
			String purchaseorder_invoiceno, Integer companyId);

	@Modifying
	@Query(" UPDATE PurchaseOrders SET purchaseorder_created_on = ?1, purchaseorder_requied_on =?2, purchaseorder_termsId=?3, purchaseorder_shipviaId =?4, purchaseorder_quotenumber=?5, "
			+ "purchaseorder_indentno =?6,  purchaseorder_workordernumber =?7,  purchaseorder_notes =?8, lastModifiedById=?9, Lastupdated = ?10,purchaseorder_vendor_paymodeId = ?12 , subCompanyId =?14, purchaseorder_vendor_id = ?15, purchaseorder_shiplocation_id = ?16 where purchaseorder_id= ?11 AND companyId = ?13")
	public void UpdateEdit_PurchaseOrders_details(java.util.Date purchaseorder_created_on,
			java.util.Date purchaseorder_requied_on, short purchaseorder_terms,
			short purchaseorder_shipvia, String purchaseorder_quotenumber, String purchaseorder_indentno,
			String purchaseorder_workordernumber, String purchaseorder_notes, Long lastModifiedBy,
			Date Lastupdated_date, Long purchaseorder_id,short vendorPaymodId, Integer companyId, Long subCompanyId, Integer purchaseorder_vendor_id, Integer purchaseorder_shiplocation_id);

	@Query("From PurchaseOrders Where purchaseorder_vendor_approvalID =?1 AND companyId = ?2 AND markForDelete = 0")
	public List<PurchaseOrders> getVendorApproval_IN_PurchaseOrders(Long VendorApproval_Id, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE PurchaseOrders SET purchaseorder_document_id=?1, purchaseorder_document=?2 Where purchaseorder_id =?3 AND companyId = ?4")
	public void Update_PurchaseOrders_To_Document_True(Long purchaseorder_documentid, boolean purchaseOrder_document,
			Long purchaseorder_id, Integer companyid);
	
	@Query("FROM PurchaseOrders p WHERE p.purchaseorder_Number= ?1 AND companyId = ?2 AND p.markForDelete = 0")
	public PurchaseOrders getPurchaseOrdersByNumber(long purchaseOrders, Integer companyId);

	@Modifying
	@Query(" UPDATE PurchaseOrders SET balanceAmount = ?1,purchaseorder_totalcost = ?2, purchaseorder_subtotal_cost = ?3, purchaseorder_balancecost = ?4, lastupdated = ?5, lastModifiedById = ?6  where Purchaseorder_id= ?7  AND companyId = ?8")
	public void UpdatePurchaseOrderCost(double balanceAmount, double purchaseOrderTotalCost, double purchaseOrderSubTotalCost, double purchaseOrderBalanceCost,java.sql.Timestamp currentDate, Long userId ,long purchaseorder_id, Integer company_id);

	@Modifying
	@Query(" UPDATE PurchaseOrders SET balanceAmount = ?1,purchaseorder_advancecost = ?2, purchaseorder_balancecost = ?3, purchaseorder_orderd_remark = ?4, purchaseorder_orderddate = ?5, purchaseorder_statusId = ?6, lastupdated = ?7, lastModifiedById = ?8, purchaseorder_orderdbyId =?9   where Purchaseorder_id= ?10  AND companyId = ?11")
	public void sentToPurchaseOrder(double balanceAmount, double purchaseOrderAdvanceCost, double purchaseOrderBalanceCost, String purchaseOrderSentRemark, java.sql.Timestamp purchaseOrder_OrderDate, short purchaseOrderStatusId, java.sql.Timestamp lastUpdatedDate, long id,long orderById, Long purchaseorder_id, Integer company_id);

	
	@Modifying
	@Query(" UPDATE PurchaseOrders SET purchaseorder_received_remark = ?1,purchaseorder_receivedbyId = ?2, purchaseorder_received_date = ?3, purchaseorder_invoice_date = ?4, purchaseorder_invoiceno = ?5, purchaseorder_statusId = ?6, lastModifiedById = ?7, lastupdated = ?8, purchaseorder_total_debitnote_cost = ?9  where Purchaseorder_id= ?10  AND companyId = ?11")
	public void updateReceivedPartPurchaseOrder(String purchaseorder_received_remark, long purchaseorder_receivedbyId,
			Date purchaseorder_received_date, Date purchaseorder_invoice_date, String purchaseorder_invoiceno,
			short purchaseorder_statusId, long lastModifiedById, Date lastupdated,double purchaseorder_total_debitnote_cost, long Purchaseorder_id,Integer company_id);

	@Modifying
	@Query(" UPDATE PurchaseOrders SET purchaseorder_vendor_id = ?1 where Purchaseorder_id= ?2  AND companyId = ?3 AND markForDelete = 0 ")
	public void updateVendorDetailsInPurchaseOrder(int vendorId, long purchaseOrderId, Integer company_id);
	
	@Modifying
	@Query(" UPDATE PurchaseOrders SET purchaseorder_invoiceno = ?1, purchaseorder_statusId= ?2, lastModifiedById = ?3, lastupdated = ?4 WHERE  Purchaseorder_id= ?5 AND companyId = ?6 AND markForDelete = 0 ")
	public void updatePurchaseOrderInvoiceNumberAndStatus(String invoiceNo, short poStatus, long lastModifiedById, Date lastupdated, long purchaseOrderId, Integer company_id);
	
	@Modifying
	@Query(" UPDATE PurchaseOrders SET purchaseorder_invoiceno = ?1, purchaseorder_statusId= ?2, lastModifiedById = ?3, lastupdated = ?4, purchaseorder_total_debitnote_cost = 0 WHERE  Purchaseorder_id= ?5 AND companyId = ?6 AND markForDelete = 0 ")
	public void updatePurchaseOrderDetailsFromDebitNote(String invoiceNo, short poStatus, long lastModifiedById, Date lastupdated, long purchaseOrderId, Integer company_id);
	
	@Modifying
	@Query(" UPDATE PurchaseOrders SET tallyCompanyId = ?1 where Purchaseorder_id= ?2  AND companyId = ?3 AND markForDelete = 0 ")
	public void updateTallyCompanyDetailsInPurchaseOrder(long tallyCompanyId, long purchaseOrderId, Integer company_id);
	
	@Query("SELECT p.purchaseorder_id From PurchaseOrders as p where p.approvalStatus= ?1 AND p.companyId = ?2 AND purchaseorder_statusId=?3 AND p.markForDelete = 0")
	public Page<PurchaseOrders> getDeployment_Page_PurchaseOrdersByApprovalStatus(short status, Integer companyId,short poStatusId, Pageable pageable);

	@Query("SELECT p.purchaseorder_id From PurchaseOrders as p where p.purchaseorder_statusId= ?1 AND p.companyId = ?2 AND p.approvalStatus = 0 AND p.markForDelete = 0")
	public Page<PurchaseOrders> getDeployment_Page_PurchaseOrdersByApprovalStatus(short status, Integer companyId, Pageable pageable);

	@Query("FROM PurchaseOrders p WHERE p.purchaseorder_Number= ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public PurchaseOrders getPurchaseOrdersID(Long purchaseorder_Number, Integer companyId);
}
