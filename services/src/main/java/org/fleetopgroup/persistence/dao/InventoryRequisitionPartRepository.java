/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.InventoryRequisitionPartsDto;
import org.fleetopgroup.persistence.model.InventoryRequisitionParts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface InventoryRequisitionPartRepository extends JpaRepository<InventoryRequisitionParts, Long>{

	@Query("FROM InventoryRequisitionParts Where markForDelete=0 AND INVRID =?1 AND COMPANY_ID =?2  ")
	public InventoryRequisitionParts GET_Inventory_Requisition_Part_ID(Long iNVRID, Integer Company_id);

	@Modifying
	@Query("UPDATE InventoryRequisitionParts SET markForDelete=1 Where INVRPARTID =?1 AND COMPANY_ID =?2 ")
	public void DELETE_InventoryRequisitionParts_MARK(Long iNVRID, Integer Company_id);
	
	@Modifying
	@Query("UPDATE InventoryRequisitionParts SET PART_TRANSFER_QTY=?1 Where INVRPARTID =?2 AND COMPANY_ID =?3  ")
	public void UPDATE_TRANSFER_QTY_IN_PART(Double updateTransferQuantity, Long invrpartid, Integer Company_id);
	
	@Query("FROM InventoryRequisitionParts Where markForDelete=0 AND INVRID =?1 AND COMPANY_ID =?2  ")
	public List<InventoryRequisitionPartsDto> list_Of_All_InventoryParts_Requisition(Long invrid, Integer company_ID);

	
	@Query("SELECT IR.INVRPARTID From InventoryRequisitionParts AS IR where IR.COMPANY_ID = ?1 AND IR.INVRID = ?2 AND IR.markForDelete = 0")
	public Page<InventoryRequisitionParts> getDeployment_Page_ShowInPartRequisitionDetailsByINVRID(Integer companyId,
			 Long INVRID, Pageable pageable);

}
