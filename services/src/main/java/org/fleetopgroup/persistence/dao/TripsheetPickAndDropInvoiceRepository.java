package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TripsheetPickAndDropInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TripsheetPickAndDropInvoiceRepository extends JpaRepository<TripsheetPickAndDropInvoice, Long> {
	
	@Query("FROM TripsheetPickAndDropInvoice T WHERE T.tripsheetPickAndDropInvoiceSummaryId = ?1 AND T.companyId = ?2 AND T.markForDelete = 0")
	public List<TripsheetPickAndDropInvoice> getInvoiceById(Long tripsheetPickAndDropInvoiceSummaryId, Integer companyId) throws Exception;
	
}