/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.Battery;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface InventoryTyreRepository extends JpaRepository<InventoryTyre, Long> {

	// public void add_Inventory_TYRE(InventoryTyre Tyre) throws Exception;

	@Query("From InventoryTyre WHERE ITYRE_INVOICE_ID=?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public List<InventoryTyre> Get__List_InventoryTyre(Long ITYRE_ID, Integer companyId) throws Exception;

	/*@Query(value = "select iu from InventoryTyre iu where iu.TYRE_NUMBER like %:text% or iu.TYRE_SIZE like %:text%")
	Page<InventoryTyre> fullText_InventoryTyre_Search(@Param("text") String text, Pageable pageable);
*/
	@Query("From InventoryTyre WHERE TYRE_ID=?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public InventoryTyre Get_TYRE_ID_InventoryTyre(Long TYRE_ID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE InventoryTyre SET markForDelete = 1 WHERE TYRE_ID=?1 AND COMPANY_ID = ?2")
	public void delete_Tyre_Inventory(Long Tyre_id, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE InventoryTyre SET TYRE_NUMBER=?1, LASTMODIFIEDBYID=?2, LASTUPDATED_DATE=?3  WHERE TYRE_ID=?4 AND COMPANY_ID = ?5")
	public void update_Tyre_Inventory(String TyreSerialNO, Long LASTUPDATEBY, Date LASTUPDATEDATE, Long Tyre_id, Integer companyid)
			throws Exception;

	@Query("From InventoryTyre WHERE ITYRE_AMOUNT_ID=?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public List<InventoryTyre> Validate_Amount_InventoryTyre(Long ITYRE_Amount_ID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE From InventoryTyre SET VEHICLE_ID=?1, OPEN_ODOMETER=?2, CLOSE_ODOMETER=0, TYRE_ASSIGN_STATUS_ID=?3, TYRE_ASSIGN_DATE=?4, dismountedTyreStatusId=?7  WHERE TYRE_ID=?5 AND COMPANY_ID = ?6 ")
	public void update_Assign_TYRE_To_Vehicle_Mount(Integer VEHICLE_ID, 
			Integer OPEN_ODOMETER, short TYRE_ASSIGN_STATUS, Date TYRE_ASSIGN_DATE, Long TYRE_ID, Integer companyId, short dismountStatusId) throws Exception;

	@Modifying
	@Query("UPDATE From InventoryTyre SET VEHICLE_ID=?1, CLOSE_ODOMETER=?2, TYRE_USEAGE=?3, TYRE_ASSIGN_STATUS_ID=?4  WHERE TYRE_ID=?5 ")
	public void update_Assign_TYRE_To_Vehicle_DisMount(Integer VEHICLE_ID,
			Integer ClOSE_ODOMETER, Integer TYRE_USEAGE, short TYRE_ASSIGN_STATUS, Long TYRE_ID) throws Exception;

	@Modifying
	@Query("UPDATE From InventoryTyre SET TYRE_USEAGE=?1 WHERE TYRE_ID=?2 ")
	public void update_Assign_TYRE_To_Vehicle_Rotation(Integer TYRE_USEAGE, Long TYRE_ID) throws Exception;

	@Modifying
	@Query("UPDATE From InventoryTyre SET TYRE_ASSIGN_STATUS_ID=?1 WHERE TYRE_ID=?2 AND COMPANY_ID = ?3")
	public void Update_Inventory_Tyre_AVALABLE_Status(short Status, Long Tyre_ID, Integer companyId) throws Exception;
	
	
	@Modifying
	@Query("UPDATE From InventoryTyre SET TYRE_USEAGE=?1, TYRE_AMOUNT=?2, TYRE_RETREAD_COUNT=?3, TYRE_ASSIGN_STATUS_ID=?4  WHERE TYRE_ID=?5 AND COMPANY_ID = ?6")
	public void Update_REtread_Amount_Usage_RetreadPeriod_Status_InventoryTYRE(Integer RetreadUsage,
			Double RetreadAmount, Integer RetreadCount, short Status, Long Tyre_id, Integer companyId) throws Exception;

	@Query("SELECT P.TYRE_ID FROM InventoryTyre AS P WHERE P.WAREHOUSE_LOCATION_ID = ?1 AND P.COMPANY_ID = ?2 AND P.markForDelete = 0")
	public Page<InventoryTyre> getDeployment_Page_InventoryLocation(Integer location, Integer companyId, Pageable pageable);

	@Modifying
	@Query("UPDATE From InventoryTyre SET WAREHOUSE_LOCATION_ID=?1 WHERE TYRE_ID=?2 AND COMPANY_ID = ?3")
	public void Update_Transfer_Location_InventoryTyre(Integer tra_TO_LOCATION, Long tyre_ID, Integer companyId);
	
	@Query("SELECT P.TYRE_ID FROM InventoryTyre AS P WHERE P.COMPANY_ID = ?1 AND P.markForDelete = 0")
	public Page<InventoryTyre> getDeploymentLog(Integer companyId, Pageable pageable);
	
	@Query("SELECT COUNT(TYRE_ID) FROM InventoryTyre AS IT "
			+ " INNER JOIN PartLocations P ON P.partlocation_id = IT.WAREHOUSE_LOCATION_ID "
			+ " INNER JOIN PartLocationPermission PLP ON PLP.partLocationId = P.partlocation_id AND PLP.user_Id = ?3 "
			+ " WHERE IT.COMPANY_ID = ?1 AND TYRE_ASSIGN_STATUS_ID = ?2 AND IT.markForDelete = 0")
	public long getInventoryTyreCountByStatus(Integer companyId, short tyreStatusId, long id);
	
	@Query("SELECT COUNT(TYRE_ID) FROM InventoryTyre AS IT WHERE IT.COMPANY_ID = ?1 AND TYRE_ASSIGN_STATUS_ID = ?2 AND IT.markForDelete = 0")
	public long getInventoryTyreCountByStatus(Integer companyId, short tyreStatusId) throws Exception;
	
	@Query("SELECT COUNT(TYRE_ID) FROM InventoryTyre AS IT WHERE IT.COMPANY_ID = ?1 AND TYRE_ASSIGN_STATUS_ID = ?2 AND WAREHOUSE_LOCATION_ID = ?3 AND IT.markForDelete = 0")
	public long getLocationWiseInventoryTyreCount(Integer companyId, short tyreStatusAvailableId, Integer locationId) throws Exception;
	
	/*location Wise Tyre List*/
	@Query("SELECT IT.TYRE_ID FROM InventoryTyre AS IT WHERE IT.COMPANY_ID = ?1 AND IT.TYRE_ASSIGN_STATUS_ID = ?2 AND IT.WAREHOUSE_LOCATION_ID = ?3 AND IT.markForDelete = 0")
	public Page<InventoryTyre> getDeployment_Page_ShowInLocationWiseInventoryTyreList(Integer companyId, short status, Integer locationId, Pageable pageable);
	
	/*All Location Wise List*/
	@Query("SELECT IT.TYRE_ID FROM InventoryTyre AS IT WHERE IT.COMPANY_ID = ?1 AND IT.TYRE_ASSIGN_STATUS_ID = ?2 AND IT.markForDelete = 0")
	public Page<InventoryTyre> getDeployment_Page_ShowInInventoryTyreList(Integer companyId, short status, Pageable pageable);
	
	@Modifying
	@Query("UPDATE InventoryTyre SET markForDelete = 1 WHERE ITYRE_INVOICE_ID=?1 AND COMPANY_ID = ?2")
	public void deleteTyreInventoryByInvoiceId(Long invoiceId, Integer companyId) throws Exception;

	@Modifying
	@Query("Update InventoryTyre set subLocationId= ?2 where ITYRE_INVOICE_ID= ?1 AND COMPANY_ID = ?3")
	public void updateSublocationInInventoryTyre(Long iTYRE_ID, Integer subLocationId, Integer companyId);
	
	@Query("SELECT COUNT(TYRE_ID) FROM InventoryTyre AS IT WHERE IT.COMPANY_ID = ?1 AND TYRE_ASSIGN_STATUS_ID = ?2 AND WAREHOUSE_LOCATION_ID = ?3 AND TYRE_MANUFACTURER_ID =?4 AND TYRE_MODEL_ID =?5 AND TYRE_SIZE_ID =?6 AND IT.markForDelete = 0")
	public long getLocationWiseTyreCount(Integer companyId, short tyreStatusAvailableId, Integer locationId ,int TYRE_MANUFACTURER_ID ,int TYRE_MODEL_ID,int TYRE_SIZE_ID  ) throws Exception;
	
	@Query("SELECT COUNT(TYRE_ID) FROM InventoryTyre AS IT WHERE IT.COMPANY_ID = ?1 AND TYRE_ASSIGN_STATUS_ID = ?2 AND WAREHOUSE_LOCATION_ID NOT IN(?3) AND TYRE_MANUFACTURER_ID =?4 AND TYRE_MODEL_ID =?5 AND TYRE_SIZE_ID =?6 AND IT.markForDelete = 0")
	public long getOtherLocationWiseTyreCount(Integer companyId, short tyreStatusAvailableId, Integer locationId ,int TYRE_MANUFACTURER_ID ,int TYRE_MODEL_ID,int TYRE_SIZE_ID  ) throws Exception;
	
	@Modifying
	@Query("UPDATE From InventoryTyre SET VEHICLE_ID=?1, CLOSE_ODOMETER=?2, TYRE_USEAGE=?3, TYRE_ASSIGN_STATUS_ID=?4, dismountedTyreStatusId=?5  WHERE TYRE_ID=?6 ")
	public void updateTyreStatusToAvailable(Integer VEHICLE_ID, Integer ClOSE_ODOMETER, Integer TYRE_USEAGE, short TYRE_ASSIGN_STATUS, short dismountedTyreStatusId, Long TYRE_ID) throws Exception;

	@Query(nativeQuery = true, value = "SELECT TYRE_ID FROM InventoryTyre WHERE TYRE_SIZE_ID=?1 AND COMPANY_ID = ?2 AND markForDelete = 0 ORDER BY TYRE_ID DESC LIMIT 1 ")
	public Long validateTyreByTyreSize(Integer tyreSizeId, Integer companyId);
	
	@Query(nativeQuery = true, value = "SELECT TYRE_ID FROM InventoryTyre WHERE TYRE_MODEL_ID=?1 AND COMPANY_ID = ?2 AND markForDelete = 0 ORDER BY TYRE_ID DESC LIMIT 1 ")
	public Long validateTyreByTyreModel(Integer tyreModelId, Integer companyId);

	@Query(" FROM InventoryTyre AS IT WHERE IT.COMPANY_ID = ?1 AND TYRE_ASSIGN_STATUS_ID = ?2 AND WAREHOUSE_LOCATION_ID = ?3 AND TYRE_MANUFACTURER_ID =?4 AND TYRE_MODEL_ID =?5 AND TYRE_SIZE_ID =?6 AND IT.markForDelete = 0")
	public List<InventoryTyre> getLocationWiseTyreList(Integer companyId, short tyreStatusAvailableId, Integer locationId ,int TYRE_MANUFACTURER_ID ,int TYRE_MODEL_ID,int TYRE_SIZE_ID  ) throws Exception;
	
	@Query(" FROM InventoryTyre AS IT WHERE IT.TYRE_ID IN (?1) AND  IT.COMPANY_ID = ?2 AND TYRE_ASSIGN_STATUS_ID = ?3 AND WAREHOUSE_LOCATION_ID = ?4  AND IT.markForDelete = 0")
	public List<InventoryTyre> getTyreListByIds(List<Long> ids,Integer companyId, short tyreStatusAvailableId, Integer locationId) throws Exception;
	
	@Query("select B from InventoryTyre as B " + 
			" Inner join TransferRequisitionDetails as T on T.transactionId = B.TYRE_ID " + 
			" where T.approvedRequisitionId = ?1  AND T.transferStatusId NOT IN(3,4,8) AND T.companyId =?2 AND B.markForDelete =0 AND T.markForDelete=0")
	public List<InventoryTyre> getTyreListToReceive(long approvedRequisitionId,Integer companyId)throws Exception;
	
	@Query("SELECT COUNT(TYRE_ID) FROM InventoryTyre AS IT WHERE IT.COMPANY_ID = ?1 AND TYRE_ASSIGN_STATUS_ID = ?2 AND WAREHOUSE_LOCATION_ID = ?3 AND TYRE_MODEL_ID =?4 AND IT.markForDelete = 0")
	public long getLocationWiseTyreCount(Integer companyId, short tyreStatusAvailableId, Integer locationId ,int TYRE_MODEL_ID) throws Exception;

	@Modifying
	@Query("UPDATE From InventoryTyre SET OPEN_ODOMETER =?2 WHERE TYRE_ID =?1 AND companyId =?3 ")
	public void updateOpenOdometer(Long tyre_ID, int rotationOdometer, Integer companyId);
	
	@Modifying
	@Query(nativeQuery = true, value = " UPDATE InventoryTyre SET dismountedTyreStatusId =?1  WHERE TYRE_ID IN(?2) ")
	public void updateTyreDismountStatus(short repairInProcess, String builder);
	
	@Modifying
	@Query(nativeQuery = true, value = " UPDATE InventoryTyre SET TYRE_ASSIGN_STATUS_ID = ?1, dismountedTyreStatusId =?2  WHERE TYRE_ID IN(?3) ")
	public void changeTyreRepairStatus(short available,short repairCompleted, String builder);
	
	@Query("SELECT IT.TYRE_NUMBER FROM InventoryTyre AS IT WHERE IT.COMPANY_ID = ?1 AND IT.TYRE_ID = ?2 AND IT.markForDelete = 0")
	public String getTyre_Number(Integer COMPANY_ID, Long TYRE_ID ) throws Exception;
	

}
