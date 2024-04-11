package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BatteryInvoiceRepository extends JpaRepository<BatteryInvoice, Long>{
	
	@Modifying
	@Query("UPDATE BatteryInvoice SET markForDelete = 1, lastModifiedById = ?1, lastModifiedBy = ?2 where batteryInvoiceId = ?3")
	public void delete(Long id, Timestamp lastupdate, Long batteryInvoiceID) throws Exception;
	
	@Query("SELECT BI.batteryInvoiceId From BatteryInvoice as BI where BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<BatteryInvoice> getDeployment_Page_BatteryInvoice(Integer companyId, Pageable pageable);
	
	@Query("SELECT BI.batteryInvoiceId From BatteryInvoice as BI where lower(BI.invoiceNumber) like CONCAT('%',?1,'%')  AND BI.companyId = ?2 AND BI.markForDelete = 0")
	public Page<BatteryInvoice> getDeployment_Page_BatteryInvoice(String term, Integer companyId, Pageable pageable);
	
	@Query("From BatteryInvoice as BI where BI.batteryInvoiceId= ?1 AND BI.companyId = ?2 AND BI.markForDelete = 0")
	public BatteryInvoice getBatteryInvoice(Long batteryInvoiceId, Integer companyId);

	@Modifying
	@Query("Update BatteryInvoice set battery_document_id= ?1, battery_document= ?2 where batteryInvoiceId= ?3 AND companyId = ?4")
	public void updateBatteryInvoiceDocumentId(Long batteryInvoiceDocId, boolean batteryDocument, Long batteryInvoiceId,
			Integer companyId);
	
	@Query("From BatteryInvoice as BI where BI.purchaseOrderId = ?1 AND BI.companyId = ?2 AND BI.markForDelete = 0")
	public BatteryInvoice getBatteryInvoiceFromPurchaseOrder(Long purchaseOrderId, Integer companyId);
	
	@Modifying
	@Query("UPDATE BatteryInvoice SET markForDelete = 1 where batteryInvoiceId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public void deleteByBatteyInvoiceId(Long batteryInvoiceID, Integer companyId) throws Exception;
	
}
