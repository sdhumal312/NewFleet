package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.RenewalReminderApproval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RenewalReminderApprovalRepository extends JpaRepository<RenewalReminderApproval, Long> {

	@Query("FROM RenewalReminderApproval AS a WHERE a.renewalApproval_id=?1 and a.companyId = ?2 and a.markForDelete = 0")
	public RenewalReminderApproval Get_RenewalReminderApproval(Long approvalID, Integer companyId);

	@Query("From RenewalReminderApproval as p where p.approvalStatusId= ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public Page<RenewalReminderApproval> getDeployment_Page_RenewalReminderApproval(short status, Integer companyId , Pageable pageable);

	@Query("From RenewalReminderApproval as p "
			+ " INNER JOIN RenewalReminder RR ON RR.renewal_approvedID = p.renewalApproval_id"
			+ " INNER JOIN Vehicle V ON V.vid = RR.vid"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3"
			+ " where p.approvalStatusId= ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public Page<RenewalReminderApproval> getDeployment_Page_RenewalReminderApproval(short status, Integer companyId , long id, Pageable pageable);

	
	@Modifying
	@Query("Update FROM RenewalReminderApproval AS a SET a.approvalPayment_Amount=?1 WHERE a.renewalApproval_id=?2")
	public void Update_ApproavalPayment_Amount_Get_Apprival_RR(Double approvalPayment_Amount, Long renewalApproval_id);

	@Modifying
	@Query("UPDATE RenewalReminderApproval  RRA SET RRA.markForDelete = 1  WHERE RRA.renewalApproval_id = ?1 AND RRA.companyId = ?2 ")
	public void delete_RenewalReminderApproval_ID(Long renewalApproval_id, Integer companyId);

	@Modifying
	@Query("Update FROM RenewalReminderApproval AS a SET a.approvalCreated_ById=?1, a.approvalCreated_Date=?2,  a.approvalStatusId = ?5 WHERE a.renewalApproval_id=?3 AND a.companyId = ?4")
	public void update_RenewalReminderApproval_ApprovedBY_andDate(Long approvalCreated_By, Timestamp toDate,
			 Long renewalApproval_id, Integer companyId, short statusid);

	@Modifying
	@Query("Update FROM RenewalReminderApproval AS a SET a.approvalPending_Amount=?1, a.approvalPay_Number=?2, a.approvalPayment_Date=?3,"
			+ " a.approvalPayment_ById=?4, a.lastModifiedById=?5, a.lastupdated=?6, a.approvalStatusId = ?9, a.paymentTypeId = ?10 WHERE a.renewalApproval_id=?7 AND a.companyId = ?8")
	public void Update_RenewalReminder_ApprovedPayment_Details(Double approvalPending_Amount,
			String approvalPay_Number,
			Timestamp toDate, Long approvalPayment_By, Long lastUpdatedBy,
			Timestamp toDate2, Long renewalApproval_id, Integer companyId, short staus, short paymentTypeId);

	@Modifying
	@Query("Update FROM RenewalReminderApproval AS a SET a.approval_document_id=?1, a.approval_document=?2 WHERE a.renewalApproval_id=?3 AND a.companyId = ?4")
	public void Update_RenewalReminderApprovalDocument_ID_to_RenewalReminderApproval(Long rendoc_id, boolean b,
			Long renewalApproval_id, Integer companyId);
	
	@Query("SELECT a FROM RenewalReminderApproval AS a INNER JOIN RenewalReminder RR ON RR.renewal_approvedID = a.renewalApproval_id "
			+ " INNER JOIN Vehicle V ON RR.vid = V.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3 "
			+ " WHERE a.renewalApproval_Number=?1 and a.companyId = ?2 and a.markForDelete = 0")
	public List<RenewalReminderApproval> Get_RenewalReminderApprovalByApprovalNumber(Long renewalApproval_Number, Integer companyId, Long userId);

	@Query("SELECT a FROM RenewalReminderApproval AS a INNER JOIN RenewalReminder RR ON RR.renewal_approvedID = a.renewalApproval_id "
			+ " WHERE a.renewalApproval_Number=?1 and a.companyId = ?2 and a.markForDelete = 0")
	public List<RenewalReminderApproval> Get_RenewalReminderApprovalByApprovalNumber(Long renewalApproval_Number, Integer companyId);

}
