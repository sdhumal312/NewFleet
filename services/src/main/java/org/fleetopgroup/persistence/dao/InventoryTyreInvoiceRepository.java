/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.persistence.model.InventoryTyreInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface InventoryTyreInvoiceRepository extends JpaRepository<InventoryTyreInvoice, Long> {

	// public void add_Tyre_Inventory_Invoice(InventoryTyreInvoice TyreInvoice)
	// throws Exception;

	// This Find ITYRE_ID in InventoryTyreInvoice details
	@Query("From InventoryTyreInvoice as t WHERE t.ITYRE_ID=?1 AND t.COMPANY_ID = ?2 AND t.markForDelete = 0 ")
	public InventoryTyreInvoice Get_list_InventoryTyreInvoice(Long ITYRE_ID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE InventoryTyreInvoice SET WAREHOUSE_LOCATION_ID =?1, PO_NUMBER=?2, INVOICE_NUMBER=?3, "
		+ "INVOICE_DATE=?4, INVOICE_AMOUNT=?5, balanceAmount=?6, VENDOR_ID=?7, DESCRIPTION=?8,"
		+ " LASTMODIFIEDBYID=?9, LASTUPDATED_DATE=?10, tallyCompanyId = ?13, PAYMENT_TYPE_ID = ?14, subLocationId = ?15, VENDOR_PAYMODE_STATUS_ID = ?16   WHERE ITYRE_ID=?11 AND COMPANY_ID = ?12")
	public void update_Tyre_Inventory_Invoice(Integer warehose, String ponumber, String invoiceNumber,
			Date invoiceDate, Double Amount, double balAmount, Integer vendorID, String Description,
			Long LASTMODIFIEDBY, Date LASTUPDATED_DATE, Long InvoiceID, Integer companyId, Long tallyCompaId, short paymentTypeId, Integer sublocationId, short VENDOR_PAYMODE_STATUS_ID ) throws Exception;
	
	@Modifying
	@Query("UPDATE InventoryTyreInvoice as t SET t.markForDelete = 1 WHERE t.ITYRE_ID=?1 AND t.COMPANY_ID = ?2")
	public void delete_Tyre_Inventory_Invoice(Long ITYRE_Invoice_ID, Integer companyId) throws Exception;



	@Query("From InventoryTyreInvoice as t WHERE t.TYRE_APPROVAL_ID=?1 AND markForDelete = 0")
	public List<InventoryTyreInvoice> getVendorApproval_IN_InventoryTyreInvoice_List(Long VendorApproval_Id)
			throws Exception;
	
	@Query("SELECT t.ITYRE_ID From InventoryTyreInvoice as t WHERE t.COMPANY_ID=?1 AND t.markForDelete = 0")
	public Page<InventoryTyreInvoice> getDeployment_Page_TyreInvoice(Integer companyId, Pageable	pageable)throws Exception;
	
	
	@Modifying
	@Query("UPDATE InventoryTyreInvoice as t SET t.anyTyreNumberAsign = ?2 WHERE t.ITYRE_ID=?1")
	public void update_Inventory_Invoice(Long ITYRE_Invoice_ID, boolean asigned) throws Exception;

	
	@Modifying
	@Query("UPDATE InventoryTyreRetread as t SET t.TR_VENDOR_ID = ?2, t.TR_PAYMENT_TYPE_ID = ?3, t.TR_PAYMENT_NUMBER = ?4, t.TR_OPEN_DATE = ?5, t.TR_REQUIRED_DATE = ?6, t.TR_QUOTE_NO = ?7, t.TR_MANUAL_NO = ?8, t.LASTUPDATED_DATE = ?9, t.LASTMODIFIEDBYID = ?10, t.COMPANY_ID = ?11 WHERE t.TRID=?1")
	public void update_RetreadTyre_Invoice(long tRID, int tR_VENDOR_ID, short tR_PAYMENT_TYPE_ID,
			String tR_PAYMENT_NUMBER, Date tR_OPEN_DATE, Timestamp tR_REQUIRED_DATE, String tR_QUOTE_NO,
			String tR_MANUAL_NO, Timestamp currentDate, long userId, Integer companyId);

	
	@Modifying
	@Query("Update InventoryTyreInvoice set tyre_document_id= ?1, tyre_document= ?2 where ITYRE_ID= ?3 AND COMPANY_ID = ?4")
	public void Update_TyreDocument_ID_to_Tyre(Long fueldocid, boolean b, Long fuel_id, Integer companyId);
	
	@Query("From InventoryTyreInvoice where PO_NUMBER = ?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public InventoryTyreInvoice getTyreInvoiceFromPurchaseOrder(String poNumber, Integer companyId);

}
