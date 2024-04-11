/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.InventoryRequisition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface InventoryRequisitionRepository extends JpaRepository<InventoryRequisition, Long> {

	@Query("FROM InventoryRequisition AS inv Where inv.INVRID =?1 AND inv.COMPANY_ID =?2 AND inv.markForDelete=0")
	public InventoryRequisition GET_Inventory_Requisition_ID(Long iNVRID, Integer Company_id);

	@Query("SELECT inv.INVRID From InventoryRequisition AS inv where inv.REQUISITION_STATUS_ID =?1 AND inv.COMPANY_ID =?2 AND inv.markForDelete=0")
	public Page<InventoryRequisition> getDeployment_Page_InventoryRequisition(short Status, Integer Company_id, Pageable pageable);

	@Modifying
	@Query("UPDATE InventoryRequisition AS inv SET inv.REQUISITION_STATUS_ID=?1, inv.REQUITED_REMARK=?2, inv.LASTMODIFIEDBYID=?3, inv.LASTUPDATED_DATE=?4  where inv.INVRID=?5 AND inv.COMPANY_ID =?6 ")
	public void Update_InventoryRequisition_SENT(short Status, String remarks, Long updateBY, Timestamp update_Date,
			Long iNVRID, Integer Company_id);

	@Modifying
	@Query("UPDATE InventoryRequisition AS inv SET inv.markForDelete=1, LASTMODIFIEDBYID=?1, LASTUPDATED_DATE=?2 Where INVRID =?3 AND inv.COMPANY_ID =?4 ")
	public void DELETE_InventoryRequisition_MARK(Long deleted_byEmail, Timestamp update_Date, Long iNVRID, Integer Company_id);
	
	@Modifying
	@Query("UPDATE InventoryRequisition AS inv SET inv.REQUISITION_STATUS_ID=?1, inv.REQUITED_REMARK=?2, inv.LASTMODIFIEDBYID=?3, inv.LASTUPDATED_DATE=?4  where inv.INVRID=?5 AND inv.COMPANY_ID =?6 ")
	public void Update_InventoryRequisition_REJECT(short Status, String remarks, Long updateBY, Timestamp update_Date,
			Long iNVRID, Integer Company_id);

}
