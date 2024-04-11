package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.UreaInvoiceToDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UreaInvoiceToDetailsRepository extends JpaRepository<UreaInvoiceToDetails, Long> {

	@Query("FROM UreaInvoiceToDetails where ureaInvoiceId = ?1 AND markForDelete = 0")
	public List<UreaInvoiceToDetails> getUreaInvoiceToDetailsList(Long	ureaInvoiceId) throws Exception;
	
	@Query("FROM UreaInvoiceToDetails where ureaInvoiceToDetailsId IN(?1) AND companyId=?2 AND markForDelete = 0 ")
	public List<UreaInvoiceToDetails> getUreaInvoiceToDetailsList(List<Long> ureaInvoiceId,int companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE UreaInvoiceToDetails SET markForDelete = 1 where  ureaInvoiceToDetailsId = ?1")
	public void deleteUreaInventoryDetails(Long ureaInvoiceToDetailsId) throws Exception;
	
	@Query("FROM UreaInvoiceToDetails where ureaInvoiceToDetailsId = ?1 AND markForDelete = 0")
	public UreaInvoiceToDetails getUreaInvoiceToDetailsById(Long ureaInvoiceId) throws Exception;
	
	@Modifying
	@Query("UPDATE UreaInvoiceToDetails SET markForDelete = 1 where  ureaInvoiceId = ?1")
	public void deleteUreaInventoryDetailsByUreaInvoiceId(Long ureaInvoiceId) throws Exception;
	
	@Modifying
	@Query("UPDATE UreaInvoiceToDetails SET subLocationId =?1 where  ureaInvoiceId = ?2 AND markForDelete = 0 AND companyId = ?3")
	public void updateSubLocationInUreaInvoiceToDetails(Integer subLocationId, Long ureaInvoiceId, Integer company_id) throws Exception;
	
}
