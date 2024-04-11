package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TripsheetPickAndDropPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TripsheetPickAndDropPaymentRepository extends JpaRepository<TripsheetPickAndDropPayment, Long> {
	
	@Query("FROM TripsheetPickAndDropPayment T WHERE T.tripsheetPickAndDropInvoiceSummaryId = ?1 AND T.companyId = ?2 AND T.markForDelete = 0")
	public List<TripsheetPickAndDropPayment> getInvoicePaymentBySummaryId(Long tripsheetPickAndDropInvoiceSummaryId, Integer companyId) throws Exception;
	
}