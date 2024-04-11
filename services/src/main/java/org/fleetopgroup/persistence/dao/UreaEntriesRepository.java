package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.UreaEntries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UreaEntriesRepository extends JpaRepository<UreaEntries, Long>{

	@Query("SELECT BI.ureaEntriesId From UreaEntries as BI where BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<UreaEntries> getDeployment_Page_UreaEntries(Integer companyId, Pageable pageable);
	
	@Query("SELECT BI.ureaEntriesId From UreaEntries as BI where BI.ureaEntriesNumber = ?1 AND BI.companyId = ?2 AND BI.markForDelete = 0")
	public Long searchUreaEntriesByNumber(Long clothInvoiceId, Integer companyId);
	
	@Modifying
	@Query("UPDATE UreaEntries SET markForDelete = 1 where  ureaEntriesId = ?1 AND companyId =?2 ")
	public void deleteUreaEntriesById(Long ureaEntriesId, Integer companyId) throws Exception;
	
	@Query("FROM UreaEntries where ureaEntriesId = ?1 AND companyId =?2 ")
	public UreaEntries getUreaEntryDetailsById(Long ureaEntriesId, Integer companyId) throws Exception;
	
	@Query("From UreaEntries where tripSheetId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<UreaEntries> getUreaEntryDeatilsByTripsheetId(Long Tripsheet_Id, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE UreaEntries SET ureaOdometerOld = ?2 where  ureaEntriesId = ?1 AND companyId =?3 ")
	public void updateNextOldOdometer(Long ureaEntriesId, Double oldOdometer, Integer companyId);
	
	@Query("SELECT BI.ureaEntriesId From UreaEntries as BI where BI.ureaDate between ?2 AND ?3  AND BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<UreaEntries> getDeployment_Page_SerachUreaEntries(Integer companyId,Timestamp startDate, Timestamp endDate, Pageable pageable);
	
	@Query(nativeQuery = true, value = "SELECT * FROM UreaEntries where ureaInvoiceToDetailsId = ?1 AND companyId = ?2 AND markForDelete = 0 ORDER BY ureaEntriesId ASC LIMIT 1")
	public UreaEntries getUreaEntryByUreaInvoiceToDetailId(Long ureaInvoiceToDetailId , Integer companyId) throws Exception;
	
	
	@Query(nativeQuery = true, value = "SELECT * FROM UreaEntries AS UE "
			+ " INNER JOIN UreaInvoiceToDetails AS UID ON UID.ureaInvoiceToDetailsId = UE.ureaInvoiceToDetailsId"
			+ " INNER JOIN UreaInvoice AS UI ON UI.ureaInvoiceId = UID.ureaInvoiceId"
			+ " where UI.ureaInvoiceId = ?1 AND UE.companyId = ?2 AND UE.markForDelete = 0 ORDER BY UE.ureaEntriesId ASC LIMIT 1")
	public UreaEntries getUreaEntryByUreaInvoiceId(Long ureaInvoiceId , Integer companyId) throws Exception;
	
	@Query(" SELECT F.ureaEntriesId FROM UreaEntries as F  "
			+ " Where F.ureaDate between ?1 AND ?2 AND F.companyId =?3 AND F.markForDelete = 0 ORDER BY F.ureaEntriesId desc ")
	public Page<UreaEntries> getPageUreaConsumptionByTransactionDate(Timestamp startDate, Timestamp endDate, int companyId, Pageable pageable);
	
	@Query(" SELECT F.ureaEntriesId FROM UreaEntries as F  "
			+ " Where F.created between ?1 AND ?2 AND F.companyId =?3 AND F.markForDelete = 0 ORDER BY F.ureaEntriesId desc ")
	public Page<UreaEntries> getPageUreaConsumptionByCreatedDate(Timestamp startDate, Timestamp endDate, int companyId, Pageable pageable);

}
