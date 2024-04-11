package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.DealerServiceEntries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DealerServiceEntriesRepository  extends JpaRepository<DealerServiceEntries, Long>{

	@Query("select DSE.dealerServiceEntriesId FROM DealerServiceEntries as DSE Where  DSE.statusId =?1 AND  DSE.companyId = ?2 AND DSE.markForDelete = 0 ORDER BY DSE.dealerServiceEntriesId DESC ")
	public Page<DealerServiceEntries> getDeploymentPageDealerServiceEntries(short statusId, Integer comapanyId, Pageable pageable);

	//For All Status
	@Query("select DSE.dealerServiceEntriesId FROM DealerServiceEntries as DSE Where DSE.companyId = ?1 AND DSE.markForDelete = 0 ORDER BY DSE.dealerServiceEntriesId DESC ")
	public Page<DealerServiceEntries> getDeploymentPageDealerServiceEntries(Integer comapanyId, Pageable pageable);
	
	@Query("FROM DealerServiceEntries Where dealerServiceEntriesNumber =?1 AND companyId = ?2 AND markForDelete = 0 ")
	public DealerServiceEntries searchDealerServiceEntriesByNumber(long dealerServiceEntriesNumber, int companyId);

	@Transactional
	@Modifying
	@Query("UPDATE DealerServiceEntries SET remark =?1, statusId = ?2, lastModifiedById = ?3 ,lastUpdatedOn = ?4, completedDate = ?5, isPartApplicable =?6 ,isLabourApplicable = ?9 where dealerServiceEntriesId =?7 AND companyId = ?8")
	public void updateDealerServiceEntriesStatus(String remark ,short status, long lastModifiedById ,Timestamp lastModifiedOn, Timestamp completedDate, boolean isPartApplicable,  long dealerServiceEntriesId, int companyId, boolean isLabourApplicable);

	@Transactional
	@Modifying
	@Query("UPDATE DealerServiceEntries SET markForDelete = 1, lastModifiedById = ?1 ,lastUpdatedOn = ?2  where dealerServiceEntriesId =?3 AND companyId = ?4")
	public void deleteDealerServiceEntries( long lastModifiedById ,Timestamp lastModifiedOn,  long dealerServiceEntriesId, int companyId);

	@Query("FROM DealerServiceEntries Where vid =?1 AND vendorId = ?2 AND invoiceNumber = ?3 AND companyId = ?4  AND markForDelete = 0 ")
	public List<DealerServiceEntries> validateDealerServiceEntries(int vid, int vendorId, String invoiceNumber, int companyId);

	@Query("FROM DealerServiceEntries Where dealerServiceEntriesId <> ?1 AND vid =?2 AND vendorId = ?3 AND invoiceNumber = ?4 AND companyId = ?5  AND markForDelete = 0 ")
	public List<DealerServiceEntries> validateDealerServiceEntriesOnUpdate(long dealerServiceEntriesId, int vid,
			int vendorId, String invoiceNumber, int companyId);

	@Modifying
	@Query("UPDATE DealerServiceEntries SET dealerServiceDocumentId =?1, dealerServiceDocument =?2 where dealerServiceEntriesId= ?3 AND companyId = ?4 ")
	public void UpdateDealerServiceEntriesDocuemntAvailableValue(Long service_documentid, boolean service_document,
			Long dealerServiceEntriesId, Integer companyId);
	
	@Query("FROM DealerServiceEntries Where dealerServiceEntriesId =?1 AND companyId = ?2 AND markForDelete = 0 ")
	public DealerServiceEntries getDealerServiceEntriesById(Long dealerServiceEntriesId, Integer companyId);

	@Modifying
	@Query("UPDATE DealerServiceEntries SET remark =?1, lastModifiedById = ?2 ,lastUpdatedOn = ?3 where  dealerServiceEntriesId =?4 AND companyId = ?5 ")
	public void updateRemark(String string, long long1, Timestamp currentTimeStamp, long dealerServiceEntriesId, int companyId);

	@Modifying
	@Query("UPDATE DealerServiceEntries SET remark =?1, statusId =?2, lastModifiedById = ?3 ,lastUpdatedOn = ?4 where  dealerServiceEntriesId =?5 AND companyId = ?6 ")
	public void changeDseStatus(String remark, short statusId, long lastModifiedById, Timestamp lastUpdatedOn, long dealerServiceEntriesId, int companyId);

	@Modifying
	@Query("UPDATE DealerServiceEntries SET driverId =?1, assignToId =?2 where  dealerServiceEntriesId =?3 AND companyId = ?4 ")
	public void updateDriverAndAssignTo(int driverId, long assignToId, long dealerServiceEntriesId, int companyId);

	@Query("SELECT D.dealerServiceEntriesNumber FROM DealerServiceEntries AS D WHERE D.companyId = ?1 AND D.dealerServiceEntriesId = ?2 AND D.markForDelete = 0")
	public Long getDSE_Number(Integer companyId, Long dealerServiceEntriesId ) throws Exception;
	
}
