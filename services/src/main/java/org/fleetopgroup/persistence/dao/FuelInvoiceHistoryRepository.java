package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.FuelInvoiceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelInvoiceHistoryRepository extends JpaRepository<FuelInvoiceHistory, Long>{

	@Query(nativeQuery = true, value = "select * From FuelInvoiceHistory F  where F.stockTypeId IN(1,2) AND F.fuelInvoiceId = ?1 AND F.companyId = ?2 AND F.markForDelete = 0 limit 1")
	public FuelInvoiceHistory getStatusWiseInvoiceHistory(long long1, int int1)throws Exception;
}
