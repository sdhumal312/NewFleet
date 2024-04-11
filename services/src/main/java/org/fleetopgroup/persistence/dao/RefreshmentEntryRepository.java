package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.RefreshmentEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RefreshmentEntryRepository extends JpaRepository<RefreshmentEntry, Long> {

	@Modifying
	@Query("UPDATE RefreshmentEntry set markForDelete = 1 where refreshmentEntryId = ?1")
	public void deleteRefreshmentEntryById(Long refreshmentEntryId) throws Exception;
	
	@Query("SELECT BI.refreshmentEntryId From RefreshmentEntry as BI where BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<RefreshmentEntry> getDeployment_Page_Refreshment(Integer companyId, Pageable pageable);
	
	@Query("SELECT BI.refreshmentEntryId From RefreshmentEntry as BI "
			+ " INNER JOIN Vehicle V ON V.vid = BI.vid"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?2"
			+ " where BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<RefreshmentEntry> getDeployment_Page_Refreshment(Integer companyId, Long id, Pageable pageable);
	

	@Query("SELECT BI.refreshmentEntryId From RefreshmentEntry as BI where BI.refreshmentEntryNumber = ?1 AND BI.companyId = ?2 AND BI.markForDelete = 0")
	public Long searchRefreshEntriesByNumber(Long clothInvoiceId, Integer companyId);

	@Query(" SELECT R.refreshmentEntryId FROM RefreshmentEntry as R  "
			+ " Where R.asignmentDate between ?1 AND ?2 AND R.companyId =?3 AND R.markForDelete = 0 ORDER BY R.refreshmentEntryId desc ")
	public Page<RefreshmentEntry> getPageRefreshmentConsumptionByTransactionDate(Timestamp fromDate, Timestamp toDate,
			int companyId, Pageable pageable);

	@Query(" SELECT R.refreshmentEntryId FROM RefreshmentEntry as R  "
			+ " Where R.created between ?1 AND ?2 AND R.companyId =?3 AND R.markForDelete = 0 ORDER BY R.refreshmentEntryId desc ")
	public Page<RefreshmentEntry> getPageRefreshmentConsumptionByCreatedDate(Timestamp fromDate, Timestamp toDate,
			int companyId, Pageable pageable);
	
	
}
