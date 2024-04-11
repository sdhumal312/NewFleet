package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.UreaInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UreaInvoiceRepository extends JpaRepository<UreaInvoice, Long>{

	@Query("SELECT BI.ureaInvoiceId From UreaInvoice as BI where BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<UreaInvoice> getDeployment_Page_UreaInvoice(Integer companyId, Pageable pageable);
	
	@Query("SELECT BI.ureaInvoiceId From UreaInvoice as BI where BI.ureaInvoiceNumber = ?1 AND BI.companyId = ?2 AND BI.markForDelete = 0")
	public Long searchUreaInvoiceByNumber(Long clothInvoiceId, Integer companyId);
	
	
	@Query("SELECT BI.ureaInvoiceId From UreaInvoice as BI where lower(BI.invoiceNumber) like CONCAT('%',?1,'%')  AND BI.companyId = ?2 AND BI.markForDelete = 0")
	public Page<UreaInvoice> getDeployment_Page_UreaInvoice(String term, Integer companyId, Pageable pageable);
	
	@Query("FROM UreaInvoice where ureaInvoiceId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public UreaInvoice getUreaInvoiceByInvoiceId(Long ureaInvoiceId, Integer companyId);

	@Modifying
	@Query("Update UreaInvoice set urea_document_id= ?1, urea_document= ?2 where ureaInvoiceId= ?3 AND companyId = ?4")
	public void updateUreaInvoiceDocumentId(Long ureaInvoiceDocId, boolean ureaDocument, Long ureaInvoiceId,
			Integer companyId);
	
	@Query("FROM UreaInvoice where purchaseOrderId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public UreaInvoice getUreaInvoiceByPurchaseOrderId(Long ureaInvoiceId, Integer companyId);
	
}
