package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.Battery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BatteryRepository extends JpaRepository<Battery, Long>{

	@Query("FROM Battery where batterySerialNumber = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<Battery>  validateBatterySerialNumber(String number, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE Battery SET markForDelete = 1, lastModifiedOn = ?1, lastModifiedById = ?2  where batteryId = ?3")
	public void deleteBattery(Timestamp lastUpdated, Long lastModifiedById, Long batteryId)throws Exception;
	
	@Query("SELECT P.batteryId FROM Battery AS P WHERE P.wareHouseLocationId = ?1 AND P.companyId = ?2 AND P.markForDelete = 0")
	public Page<Battery> getDeployment_Page_InventoryLocation(Integer location, Integer companyId, Pageable pageable);

	@Query("SELECT P.batteryId FROM Battery AS P "
			+ " INNER JOIN BatteryCapacity BC ON BC.batteryCapacityId = P.batteryCapacityId"
			+ " WHERE ( lower(P.batterySerialNumber) like CONCAT('%',?1,'%') OR lower(BC.batteryCapacity) like CONCAT('%',?1,'%') ) AND P.companyId = ?2 AND P.markForDelete = 0")
	public Page<Battery> getDeployment_Page_InventoryBattery(String term, Integer companyId, Pageable pageable);

	@Query("SELECT P.batteryId FROM Battery AS P "
			+ " INNER JOIN BatteryCapacity BC ON BC.batteryCapacityId = P.batteryCapacityId"
			+ " INNER JOIN PartLocationPermission PL ON PL.partLocationId = P.wareHouseLocationId AND PL.user_Id = ?3"
			+ " WHERE ( lower(P.batterySerialNumber) like CONCAT('%',?1,'%') OR lower(BC.batteryCapacity) like CONCAT('%',?1,'%') ) AND P.companyId = ?2 AND P.markForDelete = 0")
	public Page<Battery> getDeployment_Page_InventoryBattery(String term, Integer companyId, Long id, Pageable pageable);
	
	@Modifying
	@Query("UPDATE From Battery SET vid=?1, openOdometer=?2, closedOdometer=0, batteryStatusId=?3, batteryAsignedDate=?4, batteryFirstAsignedDate = ?7 WHERE batteryId=?5 AND companyId = ?6 ")
	public void update_Assign_TYRE_To_Vehicle_Mount(Integer VEHICLE_ID, 
			Integer OPEN_ODOMETER, short TYRE_ASSIGN_STATUS, Date TYRE_ASSIGN_DATE, Long TYRE_ID, Integer companyId, Timestamp batteryFirstAsignedDate) throws Exception;
	
	@Modifying
	@Query("UPDATE From Battery SET vid=?1, openOdometer=?2, closedOdometer=0, batteryStatusId=?3, batteryAsignedDate=?4  WHERE batteryId=?5 AND companyId = ?6 ")
	public void update_Assign_TYRE_To_Vehicle_Mount(Integer VEHICLE_ID, 
			Integer OPEN_ODOMETER, short TYRE_ASSIGN_STATUS, Date TYRE_ASSIGN_DATE, Long TYRE_ID, Integer companyId) throws Exception;

	
	@Modifying
	@Query("UPDATE From Battery SET vid=?1, closedOdometer=?2, batteryUsesOdometer=?3, batteryStatusId=?4, usesNoOfTime =?6, batteryUsesStatusId = 2  WHERE batteryId=?5 ")
	public void update_Assign_Battery_To_Vehicle_DisMount(Integer VEHICLE_ID,
			Integer ClOSE_ODOMETER, Integer TYRE_USEAGE, short TYRE_ASSIGN_STATUS, Long batteryId, Long usesNoOfTime) throws Exception;
	
	
	@Modifying
	@Query("UPDATE From Battery SET wareHouseLocationId=?1 WHERE batteryId=?2 AND companyId = ?3")
	public void Update_Transfer_Location_InventoryBattery(Integer tra_TO_LOCATION, Long batteryId, Integer companyId);
	
	@Query("FROM Battery where batteryCapacityId = ?1 AND companyId = ?2 AND markForDelete = 0")
	Long getBatteryCount(Long capacityId, Integer companyId) throws Exception;

	@Query("SELECT COUNT(batteryId) FROM Battery where companyId = ?1 AND batteryStatusId = ?2 AND markForDelete = 0")
	public long getbatteryCountByStatus(Integer companyId, short status) throws Exception;

	@Query("SELECT COUNT(batteryId) FROM Battery where companyId = ?1 AND batteryStatusId = ?2 AND wareHouseLocationId = ?3 AND markForDelete = 0")
	public long getlocationWisebatteryCountByStatus(Integer companyId, short status, Integer location)throws Exception;

	/*location Wise Tyre List*/
	@Query("SELECT IT.batteryId FROM Battery AS IT WHERE IT.companyId = ?1 AND IT.batteryStatusId = ?2 AND IT.wareHouseLocationId = ?3 AND IT.markForDelete = 0")
	public Page<Battery> getDeployment_Page_ShowInLocationWiseBatteryList(Integer companyId, short status, Integer locationId, Pageable pageable);
	
	/*All Location Wise List*/
	@Query("SELECT IT.batteryId FROM Battery AS IT WHERE IT.companyId = ?1 AND IT.batteryStatusId = ?2 AND IT.markForDelete = 0")
	public Page<Battery> getDeployment_Page_ShowInBatteryList(Integer companyId, short status, Pageable pageable);
	
	@Query("FROM Battery where batteryId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public Battery  getBatteryDetailsByBatteryId(Long batteryId, Integer companyId) throws Exception;
	
	@Query("FROM Battery where batteryInvoiceId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<Battery>  getBatteryDetailsByInvoiceId(Long batteryInvoiceId, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE Battery SET markForDelete = 1 WHERE batteryInvoiceId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public void deleteBatteryByInvoiceId(Long batteryInvoiceId, Integer companyId)throws Exception;

	@Modifying
	@Query("Update Battery set subLocationId= ?2 where batteryInvoiceId= ?1 AND companyId = ?3")
	public void updateSublocationInBattery(Long batteryInvoiceId, Integer subLocationId, Integer companyId)throws Exception;
	
	@Query("SELECT COUNT(batteryId) FROM Battery where companyId = ?1 AND batteryStatusId = ?2 AND wareHouseLocationId = ?3 AND batteryManufacturerId=?4 AND batteryTypeId=?5 AND batteryCapacityId =?6 AND markForDelete = 0")
	public long getlocationWisebatteryCount(Integer companyId, short status, Integer location ,long batteryManufacturerId ,long batteryModel,long batteryCapacity)throws Exception;
	
	@Query("SELECT COUNT(batteryId) FROM Battery where companyId = ?1 AND batteryStatusId = ?2 AND wareHouseLocationId = ?3 AND batteryCapacityId =?4 AND markForDelete = 0")
	public long getlocationAndCapacityWiseCount(Integer companyId, short status, Integer location,long batteryCapacity)throws Exception;
	
	@Query(" FROM Battery where companyId = ?1 AND batteryStatusId = ?2 AND wareHouseLocationId = ?3 AND batteryManufacturerId=?4 AND batteryTypeId=?5 AND batteryCapacityId =?6 AND markForDelete = 0 order by batteryId desc")
	public List<Battery> getlocationWisebatteryList(Integer companyId, short status, Integer location ,long batteryManufacturerId ,long batteryModel,long batteryCapacity)throws Exception;
	
	@Query("SELECT COUNT(batteryId) FROM Battery where companyId = ?1 AND batteryStatusId = ?2 AND wareHouseLocationId NOT IN (?3)  AND batteryManufacturerId=?4 AND batteryTypeId=?5 AND batteryCapacityId =?6 AND markForDelete = 0")
	public long getAllLocationWisebatteryCount(Integer companyId, short status, Integer location ,long batteryManufacturerId ,long batteryModel,long batteryCapacity)throws Exception;
	
	@Query(" FROM Battery where companyId = ?1 AND batteryStatusId = ?2 AND wareHouseLocationId = ?3 AND batteryId IN (?4) AND markForDelete = 0 ")
	public List<Battery> getbatteryListByIds(Integer companyId, short status, Integer location,List<Long> ids )throws Exception;
	
	@Query("select B from Battery as B " + 
			" Inner join TransferRequisitionDetails as T on T.transactionId = B.batteryId " + 
			" where T.approvedRequisitionId = ?1  AND T.transferStatusId NOT IN(3,4,8) AND T.companyId =?2 AND B.markForDelete =0 AND T.markForDelete=0")
	public List<Battery> getbatteryListToReceive(long approvedRequisitionId,Integer companyId)throws Exception;
	@Modifying
	@Query("UPDATE From Battery SET vid=?1, closedOdometer=?2, batteryUsesOdometer=?3, batteryStatusId=?4, dismountedBatteryStatusId =?5, usesNoOfTime =?7, batteryUsesStatusId = 2  WHERE batteryId=?6 ")
	public void update_Assign_Battery_To_Vehicle_DisMount(Integer VEHICLE_ID,
			Integer ClOSE_ODOMETER, Integer TYRE_USEAGE, short TYRE_ASSIGN_STATUS, short dismountStatusId, Long batteryId, Long usesNoOfTime) throws Exception;

	@Modifying
	@Query(nativeQuery = true, value = "UPDATE Battery SET dismountedBatteryStatusId=?1  WHERE batteryId IN (?2) ")
	public void updateBatteryDismountStatus(short repairInProcess, String builder);

	@Modifying
	@Query(nativeQuery = true, value = " UPDATE Battery SET batteryStatusId = ?1, dismountedBatteryStatusId =?2  WHERE batteryId IN(?3) ")
	public void changeBatteryRepairStatus(short available,short repairCompleted, String builder);

	
}
