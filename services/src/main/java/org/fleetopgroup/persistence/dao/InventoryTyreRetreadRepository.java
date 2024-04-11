/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.InventoryTyreRetread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface InventoryTyreRetreadRepository extends JpaRepository<InventoryTyreRetread, Long> {

	// public void add_Tyre_Inventory_Amount(InventoryTyreRetread TyreAmount)
	// throws Exception;

	@Query("From InventoryTyreRetread WHERE TRID=?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public InventoryTyreRetread Get_InventoryTyreRetread(Long TRID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE InventoryTyreRetread SET markForDelete = 1 WHERE TRID=?1 AND COMPANY_ID = ?2 ")
	public void delete_Tyre_InventoryRetread(Long IRID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE From InventoryTyreRetread SET TR_STATUS_ID=?1, TR_DESCRIPTION=?2 , LASTMODIFIEDBYID=?3, LASTUPDATED_DATE=?4  WHERE TRID=?5 AND COMPANY_ID = ?6")
	public void Update_Inventory_ReTread_Status_and_Description(short Status, String Description, Long LastupdateBy,
			Date Lastupdated, Long TyreRetread_id, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE From InventoryTyreRetread SET TR_STATUS_ID=?1, TR_INVOICE_NUMBER=?2, TR_INVOICE_DATE=?3, TR_RE_DESCRIPTION=?4,  LASTMODIFIEDBYID=?5, LASTUPDATED_DATE=?6  WHERE TRID=?7 AND COMPANY_ID = ?8")
	public void Update_Completed_Inventory_ReTread_Status_and_Description(short Status, String invoiceNumber,
			Date invoiceDate, String Description, Long LastupdateBy, Date Lastupdated, Long TyreRetread_id, Integer companyid)
			throws Exception;

	@Modifying
	@Query("UPDATE From InventoryTyreRetread SET TR_STATUS_ID=?1, TR_RE_DESCRIPTION=?2  WHERE TRID=?3 AND COMPANY_ID = ?4")
	public void Update_Completed_Inventory_ReTread_Status_and_Description(short Status, String Description,
			Long TyreRetread_id, Integer companyId) throws Exception;

	@Query("From InventoryTyreRetread WHERE TR_APPROVAL_ID=?1 AND markForDelete = 0")
	public List<InventoryTyreRetread> getVendorApproval_IN_InventoryTyreRetread_List(Long VendorApproval_Id)
			throws Exception;
	
	@Query("SELECT ITR.TRID From InventoryTyreRetread ITR WHERE ITR.COMPANY_ID=?1 AND ITR.markForDelete = 0")
	public Page<InventoryTyreRetread> getDeploymentInventoryTyreRetreadLog(Integer companyId, Pageable	pageable)throws Exception;
}
