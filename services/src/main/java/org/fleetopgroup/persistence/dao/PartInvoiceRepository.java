package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.persistence.model.PartInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PartInvoiceRepository extends JpaRepository<PartInvoice, Long>{
	
	@Modifying
	@Query("UPDATE PartInvoice SET wareHouseLocation =?1, invoiceNumber=?2, "
		+ "invoiceDate=?3, invoiceAmount=?4, vendorId=?5, description=?6,"
		+ " lastModifiedById=?7, lastUpdated_Date=?8, quantity=?9, balanceAmount=?10, paymentTypeId = ?13, labourCharge = ?14, tallyCompanyId = ?15, subLocationId = ?16, vendorPaymentStatus =?17 WHERE partInvoiceId=?11 AND companyId = ?12")
	public void update_Part_Inventory_Invoice(Integer wareHouseLocation, String invoiceNumber,
			Timestamp invoiceDate, String invoiceAmount, Integer vendorId, String description,
			Long lastModifiedById, Date lastUpdated_Date, Double quantity, double balanceAmount, Long partInvoiceId, Integer companyId, short paymentType, Double labour, Long tallyId,Integer subLocationId,short vendorPaymentStatus) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE PartInvoice SET anyPartNumberAsign = 1 WHERE partInvoiceId = ?1 AND companyId = ?2 AND markForDelete = 0 ")
	public void update_anyPartNumberAsign_InPartInvoice(Long partInvoiceId, Integer companyId) throws Exception;
	
	@Query("FROM PartInvoice WHERE partInvoiceId= ?1 AND companyId = ?2 AND markForDelete = 0")
	public PartInvoice getPartInvoiceDetails(Long partInvoiceId, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE PartInvoice SET markForDelete = 1 WHERE partInvoiceId=?1 AND companyId = ?2")
	public void delete_PartInvoice(Long partInvoiceId, Integer companyId) throws Exception;
	
	@Modifying
	@Query("Update PartInvoice set part_document_id= ?1, part_document= ?2 where partInvoiceId= ?3 AND companyId = ?4")
	public void Update_PartDocument_ID_to_Part(Long fueldocid, boolean b, Long fuel_id, Integer companyId)throws Exception;
	
	@Modifying
	@Query("UPDATE PartInvoice SET anyPartNumberAsign = 0 WHERE partInvoiceId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public void update_anyPartNumberNotAsign_InPartInvoice(Long partInvoiceId, Integer companyId) throws Exception;
	
	@Query("FROM PartInvoice WHERE purchaseorder_id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public PartInvoice getPartInvoiceDetailsFromPurchaseOrder(Long purchaseorder_id, Integer companyId) throws Exception;
	
	@Query("SELECT IA.partInvoiceId FROM PartInvoice IA WHERE IA.companyId = ?1 AND IA.markForDelete = 0 ")
	Page<PartInvoice> getDeployment_Page_PartInvoice(Integer companyId, Pageable pageable) throws Exception;
	
}