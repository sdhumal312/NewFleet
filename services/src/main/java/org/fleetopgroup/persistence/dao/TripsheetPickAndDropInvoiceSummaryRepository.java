package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripsheetPickAndDropInvoiceSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripsheetPickAndDropInvoiceSummaryRepository extends JpaRepository<TripsheetPickAndDropInvoiceSummary, Long> {
	
	@Query("FROM TripsheetPickAndDropInvoiceSummary T WHERE T.tripsheetPickAndDropInvoiceSummaryId = ?1 AND T.companyId = ?2 AND T.markForDelete = 0")
	public TripsheetPickAndDropInvoiceSummary getInvoiceById(Long tripsheetPickAndDropInvoiceSummaryId, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE TripsheetPickAndDropInvoiceSummary SET markForDelete = 1 WHERE tripsheetPickAndDropInvoiceSummaryId = ?1 AND companyId = ?2")
	public void deleteInvoiceById(Long tripsheetPickAndDropInvoiceSummaryId, Integer companyId) throws Exception;
	
}