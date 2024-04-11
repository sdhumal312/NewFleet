package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.InventoryTyreRetreadDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryTyreRetreadDocumentRepository extends JpaRepository<InventoryTyreRetreadDocument, Long> {

	// get Document
	@Query("From InventoryTyreRetreadDocument where TRID = ?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public InventoryTyreRetreadDocument Get_Validate_TYRE_Document(Long ITYRE_ID, Integer companyId) throws Exception;
	
	@Query("SELECT MAX(TRDOCID) FROM InventoryTyreRetreadDocument")
	public long getInventoryTyreRetreadDocumentMaxId() throws Exception;
	
	@Query("FROM InventoryTyreRetreadDocument where TRDOCID > ?1 AND TRDOCID <= ?2")
	public List<InventoryTyreRetreadDocument> getInventoryTyreRetreadDocumentList(Long startLimit, Long endLimit) throws Exception;

}
