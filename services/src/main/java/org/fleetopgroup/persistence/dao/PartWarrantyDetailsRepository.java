package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.PartWarrantyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PartWarrantyDetailsRepository extends JpaRepository<PartWarrantyDetails, Long>{

	@Query("FROM PartWarrantyDetails where inventoryId =?1 and markForDelete = 0")
	List<PartWarrantyDetails>  getPartWarrantyDetailsList(Long inventoryId) throws Exception;
	
	@Query("FROM PartWarrantyDetails where partSerialNumber =?1 AND companyId = ?2 and markForDelete = 0")
	List<PartWarrantyDetails>  getPartWarrantyDetailsList(String partSerialNumber, Integer	companyId) throws Exception;
	
	@Query("FROM PartWarrantyDetails where partId =?1 and companyId = ?2 AND locationId = ?3 AND status = 1 and markForDelete = 0")
	List<PartWarrantyDetails>  getPartWarrantyDetailsListByPartId(Long partId, Integer companyId, Integer locationId ) throws Exception;

	@Modifying
	@Query(nativeQuery = true, value = "UPDATE PartWarrantyDetails SET status =?1 where partWarrantyDetailsId IN( '?1' ) AND companyId = ?2 AND markForDelete = 0")
	void updatePartWarrantyStatus(short status, String partWarrantyDetailsIds, int companyId);
	
	@Query("FROM PartWarrantyDetails where partWarrantyDetailsId =?1 AND companyId = ?2 AND markForDelete = 0")
	PartWarrantyDetails getPartWarrantyDetailsById(Long inventoryId , Integer companyId) throws Exception;
	
	@Query("FROM PartWarrantyDetails where serviceId =?1 AND servicePartId =?2 AND companyId = ?3 AND markForDelete = 0")
	List<PartWarrantyDetails> getPartWarrantyDetailsByServicePartId(long serviceId,long servicePartId, int int1);
	
	@Query("FROM PartWarrantyDetails where serviceId =?1 AND assignedFrom = ?2 AND markForDelete = 0")
	List<PartWarrantyDetails> getPartWarrantyDetailsByServiceId(long serviceId , short type);
	
	@Query("FROM PartWarrantyDetails where servicePartId =?1 AND companyId = ?2 AND assignedFrom = ?3 AND markForDelete = 0")
	List<PartWarrantyDetails> getPartWarrantyDetailsByServicePartId(long servicePartId, int int1, short type);
	
	@Query("FROM PartWarrantyDetails where serviceId =?1 AND companyId = ?2 AND markForDelete = 0")
	List<PartWarrantyDetails> getPartWarrantyDetailsByServiceId(long serviceId, int int1);

	@Query("FROM PartWarrantyDetails where serviceId =?1 AND partWarrantyStatusId = ?2 AND companyId = ?3 AND markForDelete = 0")
	List<PartWarrantyDetails> getPartWarrantyDetailsByServiceIdAndStatus(long serviceId, short unasigned, int companyId);

	@Query("FROM PartWarrantyDetails where serviceId =?1 AND servicePartId =?2 AND companyId = ?3 AND partWarrantyStatusId = 2 AND markForDelete = 0")
	List<PartWarrantyDetails> getAssignPart(long long1, long long2, int int1);

	@Modifying
	@Query("UPDATE PartWarrantyDetails SET markForDelete = 1 WHERE servicePartId =?1 AND partWarrantyStatusId = ?2 AND companyId = ?3 AND markForDelete = 0")
	void deleteAllUnAssignPart(long long1, short unasigned, int int1);
	
	@Modifying
	@Query("UPDATE PartWarrantyDetails SET markForDelete = 1 WHERE inventoryId =?1 ")
	public void deleteWarrantyPartByInventoryId(Long inventoryId) throws Exception;
	
	@Modifying
	@Query("UPDATE PartWarrantyDetails SET repairStatusId = ?2, repairToStockDetailsId = ?3  WHERE partWarrantyDetailsId =?1 ")
	void updateRepairStatus(Long assetId, short statusId, Long repairToStockDetailsId);

	@Modifying
	@Query("UPDATE PartWarrantyDetails SET locationId = ?2 WHERE repairToStockDetailsId =?1 ")
	void transferAssetNumber(long repairStockId, int locationId);
	
	@Modifying
	@Query("UPDATE PartWarrantyDetails SET repairStatusId = ?1 , repairToStockDetailsId = 0 WHERE repairToStockDetailsId =?2 ")
	void updateRepairStatusByrepairToStockDetailsId(short statusId, Long repairToStockDetailsId);
	
}
