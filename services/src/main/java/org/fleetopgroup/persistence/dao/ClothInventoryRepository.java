package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.ClothInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClothInventoryRepository extends JpaRepository<ClothInvoice, Long>{

	@Query("SELECT BI.clothInvoiceId From ClothInvoice as BI where BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<ClothInvoice> getDeployment_Page_ClothInvoice(Integer companyId, Pageable pageable);
	
	@Query("SELECT BI.clothInvoiceId From ClothInvoice as BI where lower(BI.invoiceNumber) like CONCAT('%',?1,'%')  AND BI.companyId = ?2 AND BI.markForDelete = 0")
	public Page<ClothInvoice> getDeployment_Page_ClothInvoice(String term, Integer companyId, Pageable pageable);
	
	@Query("SELECT BI.clothInvoiceId From ClothInvoice as BI where BI.clothInvoiceNumber = ?1 AND BI.companyId = ?2 AND BI.markForDelete = 0")
	public Long searchClothInvoiceByNumber(Long clothInvoiceId, Integer companyId);
	
	@Query("From ClothInvoice as BI where BI.clothInvoiceId = ?1 AND BI.companyId = ?2 AND BI.markForDelete = 0")
	public ClothInvoice getClothInvoice(Long clothInvoiceId, Integer companyId);

	@Modifying
	@Query("Update ClothInvoice set cloth_document_id= ?1, cloth_document= ?2 where clothInvoiceId= ?3 AND companyId = ?4")
	public void updateClothInvoiceDocumentId(Long clothInvoiceDocId, boolean clothDocument, Long clothInvoiceId,
			Integer companyId);
	
	@Query("From ClothInvoice as BI where BI.poNumber = ?1 AND BI.companyId = ?2 AND BI.markForDelete = 0")
	public ClothInvoice getClothInvoiceByPurchaseOrder(String poNumber, Integer companyId);
	
	@Query("Update ClothInvoice set markForDelete = 1 where clothInvoiceId = ?1 AND companyId = ?2")
	public void deleteClothInvoiceById(Long clothInvoiceId, Integer companyId);
	

}
