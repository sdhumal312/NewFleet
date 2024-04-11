/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TyreSoldInvoiceDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TyreSoldInvoiceDetailsRepository extends JpaRepository<TyreSoldInvoiceDetails, Integer> {

	@Query("From TyreSoldInvoiceDetails TE where TE.tyreSoldInvoiceId = ?1 AND TE.companyId = ?2 AND TE.markForDelete = 0 ")
	public TyreSoldInvoiceDetails findByTyreSoldInvoiceId(Long invoiceId , Integer companyId);

	@Modifying
	@Query("UPDATE TyreSoldInvoiceDetails TE SET TE.tyreSoldInvoiceAmount =?1, TE.tyreStatus =?2, TE.description =?3  where TE.tyreSoldInvoiceId = ?4 AND TE.companyId = ?5 AND TE.markForDelete = 0 ")
	public void updateTyreSoldInvoiceDetils(double tyreSoldInvoiceAmount, short status, String description, long tyreSoldInvoiceId, Integer company_id);

	@Query("SELECT BI.tyreSoldInvoiceId From TyreSoldInvoiceDetails as BI where BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<TyreSoldInvoiceDetails> getDeployment_Page_TyreSoldInvoiceDetails(Integer companyId,
			Pageable pageable);

	@Modifying
	@Query("UPDATE TyreSoldInvoiceDetails TE SET TE.markForDelete = 1  where TE.tyreSoldInvoiceId = ?1 AND TE.companyId = ?2 ")
	public void deleteTyreSoldInvoiceDetails(long invoiceId,  Integer company_id);
}

	


