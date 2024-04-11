/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.InventoryTyreHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface InventoryTyreHistoryRepository  extends JpaRepository<InventoryTyreHistory, Long> {

	//public void add_Inventory_TYRE_History(InventoryTyreHistory TyreHistory) throws Exception;
	
	@Query("From InventoryTyreHistory AS H WHERE H.TYRE_ID =?1 AND H.COMPANY_ID = ?2 AND H.markForDelete = 0 ORDER BY H.TYRE_HIS_ID DESC")
	public List<InventoryTyreHistory> find_List_InventoryTyreHistory(Long ITYRE_ID, Integer companyId) throws Exception;

	@Query(" SELECT ITH.TYRE_HIS_ID FROM InventoryTyreHistory as ITH  "
			+ " Where ITH.TYRE_ASSIGN_DATE between ?1 AND ?2 AND ITH.COMPANY_ID =?3 AND ITH.markForDelete = 0 ORDER BY ITH.TYRE_HIS_ID desc ")
	public Page<InventoryTyreHistory> getPageTyreConsumptionByTransactionDate(Timestamp startDate, Timestamp endDate, int companyId, Pageable pageable);
	
	@Query(" SELECT ITH.TYRE_HIS_ID FROM InventoryTyreHistory as ITH  "
			+ " Where ITH.createdOn between ?1 AND ?2 AND ITH.COMPANY_ID =?3 AND ITH.markForDelete = 0 ORDER BY ITH.TYRE_HIS_ID desc ")
	public Page<InventoryTyreHistory> getPageTyreConsumptionByCreatedDate(Timestamp startDate, Timestamp endDate, int companyId, Pageable pageable);
	
	@Query(nativeQuery = true, value = "SELECT * FROM InventoryTyreHistory AS H WHERE H.TYRE_ID =?1 AND H.COMPANY_ID = ?2 AND H.markForDelete = 0 AND H.TYRE_STATUS_ID = 7 ORDER BY H.TYRE_HIS_ID DESC LIMT 1")
	public InventoryTyreHistory getDismountTyreDetails(Long ITYRE_ID, Integer companyId) throws Exception;

}
