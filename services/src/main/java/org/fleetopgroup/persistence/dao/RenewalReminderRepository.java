package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.Date;
/**
 * @author fleetop
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.model.RenewalReminder;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RenewalReminderRepository extends JpaRepository<RenewalReminder, Long> {

	@Modifying
	@Query("UPDATE RenewalReminder SET renewal_staus_id = ?2, renewal_approvedbyId = ?3, renewal_approvedComment = ?4, renewal_approveddate = ?5, lastupdated = ?5 WHERE  renewal_id = ?1 AND companyId = ?6")
	public void updateApproedBy(Long renewal_id, short renewal_status, Long renewal_approvedby,
			String renewal_approvedComment, Date renewal_approveddate, Integer companyId);

	public List<RenewalReminder> findAll();

	@Query("From RenewalReminder where vehicle_registration = ?1 AND markForDelete = 0")
	public List<RenewalReminder> listVehicleRenewalReminder(String renewal_vehiclename);

	@Query("From RenewalReminder RR INNER JOIN Vehicle V ON RR.vid = V.vid INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3 where RR.renewal_R_Number= ?1 AND RR.companyId = ?2 AND  RR.markForDelete = 0")
	public RenewalReminder getRenewalReminder(Long renewal_id, Integer companyId, Long userId);

	@Query("From RenewalReminder RR  where RR.renewal_R_Number= ?1 AND RR.companyId = ?2 AND  RR.markForDelete = 0")
	public RenewalReminder getRenewalReminder(Long renewal_id, Integer companyId);

	@Modifying
	@Query("UPDATE FROM RenewalReminder RR SET RR.markForDelete = 1, RR.lastModifiedById=?3 ,RR.lastupdated=?4 WHERE RR.renewal_id = ?1 AND RR.companyId = ?2 ")
	public void deleteRenewalReminder(Long renewalReminder, Integer companyId , Long userId , Timestamp todate);

	@Query("select count(*) from RenewalReminder")
	public Long countRenewalReminder() throws Exception;

	@Modifying
	@Query("UPDATE RenewalReminder SET renewal_document_id = ?1, renewal_document = ?2 WHERE  renewal_id = ?3 AND companyId = ?4")
	public void Update_RenewalReminderDocument_ID_to_RenewalReminder(Long rendoc_id, boolean b, Long renewal_id, Integer companyId);

	@Query("From RenewalReminder RR where RR.renewal_approvedID = ?1 and RR.companyId = ?2 and RR.markForDelete = 0")
	public List<RenewalReminder> Find_Approval_RenewalReminder(Long approvalID, Integer companyId);

	@Query("SELECT p.renewal_id From RenewalReminder as p where p.renewal_staus_id= ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public Page<RenewalReminder> getDeployment_Page_RenewalReminder(short status, Integer companyId, Pageable pageable);
	
	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3"
			+ " where p.renewal_staus_id= ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public Page<RenewalReminder> getDeployment_Page_RenewalReminder(short status, Integer companyId, long id, Pageable pageable);


	@Modifying
	@Query("UPDATE RenewalReminder RR SET RR.markForDelete = 1 WHERE RR.renewal_approvedID = ?1 AND RR.companyId = ?2 ")
	public void delete_RenewalReminder_To_Approval_ID(Long renewalApproval_id, Integer companyId);

	@Modifying
	@Query("UPDATE RenewalReminder SET renewal_PayNumber = ?1, renewal_paidbyId = ?2, renewal_dateofpayment = ?3, "
			+ "renewal_staus_id = ?6, paymentTypeId = ?7 WHERE  renewal_approvedID = ?4 AND companyId = ?5")
	public void Update_RenewalReminderApproval_ID_To_Payment_Status_Change(
			String approvalPay_Number, Long approvalPayment_By, Timestamp toDate,
			Long renewalApproval_id, Integer companyId, short status, short paymentTypeId);

	@Modifying
	@Query("UPDATE RenewalReminder SET renewal_receipt =?1, renewal_document = ?2, renewal_authorization = ?3, renewal_number = ?4, "
			+ "renewal_staus_id = ?5 WHERE  renewal_id = ?6 AND companyId = ?7")
	public void Update_RenewalReminder_Upload_File(String renewal_receipt, boolean renewal_document,
			String renewal_authorization, String renewal_number, short renewal_status, Long renewal_id, Integer companyId);

	@Modifying
	@Query("UPDATE RenewalReminder SET renewal_Amount =?1 WHERE  renewal_id = ?2 AND companyId = ?3 ")
	public void Update_RenewalReminder_Approval_Amount(Double renewalAmount, Long renewalID, Integer companyId);

	@Modifying
	@Query("UPDATE RenewalReminder SET renewal_PayNumber=?1, renewal_paidbyId=?2, renewal_dateofpayment=?3, paymentTypeId = ?6 WHERE renewal_id = ?4 AND companyId = ?5")
	public void update_RenewalReminder_DD_Number(String renewal_PayNumber,
			Long renewal_paidby, java.util.Date renewal_dateofpayment, Long renewal_id, Integer companyId, short paymentTypeId);

	@Modifying
	@Query("UPDATE RenewalReminder SET renewal_staus_id = ?1, renewal_approvedbyId = ?2, renewal_approvedComment = ?3,"
			+ " renewal_approveddate = ?4, lastupdated = ?4, renewal_from=?4, renewal_to=?5 WHERE  renewal_id = ?6 ")
	public void update_Cancel_ApprovalID_to_Status_changeDate_From_To(short renewal_status, Long firstName,
			String renewal_approvedComment, java.util.Date From, java.util.Date To, Long renewal_id, Integer companyId);
	
	@Query("From RenewalReminder RR INNER JOIN Vehicle V ON RR.vid = V.vid INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3 where RR.renewal_id= ?1 AND RR.companyId = ?2 AND  RR.markForDelete = 0")
	public RenewalReminder getRenewalReminderById(Long renewal_id, Integer companyId, Long userId);

	@Query("From RenewalReminder RR  where RR.renewal_id= ?1 AND RR.companyId = ?2 AND  RR.markForDelete = 0")
	public RenewalReminder getRenewalReminderById(Long renewal_id, Integer companyId);
	
	@Modifying
	@Query("UPDATE RenewalReminder SET renewal_from = ?1, renewal_to =?2, renewal_timethreshold = ?3, dateofRenewal = ?4, lastupdated = ?7, lastModifiedById = ?8, renewal_periedthreshold = ?9 where renewal_id = ?5 AND companyId = ?6")
	public void updateRenewalPeriod(Date renewal_from, Date renewal_to, Integer renewal_timethreshold, Date dateofRenewal, Long renewal_Id, Integer companyId, Date lastUpdate, Long updatedBy, Integer threashHoldPeriod) throws Exception;
	
	@Modifying
	@Query("UPDATE RenewalReminder SET newRRCreated=true WHERE vid =?1  AND renewalTypeId= ?2 AND renewal_Subid = ?3")
	public void updateNewRRCreated(Integer vid, Integer renewalType, Integer renewalSubType) throws Exception;
	
	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid AND V.vStatusId <> 4"
			+ " where p.renewal_to < ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.newRRCreated = 0")
	public Page<RenewalReminder> getDeployment_Page_RenewalOverDue(Date startDate, Integer companyId, Pageable pageable);
	
	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid AND V.vStatusId <> 4"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3"
			+ " where p.renewal_to < ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.newRRCreated = 0")
	public Page<RenewalReminder> getDeployment_Page_RenewalOverDue(Date startDate, Integer companyId, long id, Pageable pageable);
	
	@Query("SELECT COUNT(R.renewal_id) FROM RenewalReminder R "
			+ " INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> 4"
			+ " where R.renewal_to < ?1 AND  R.companyId = ?2 AND R.markForDelete = 0 AND R.newRRCreated = 0")
	public Long getRenewalOverDueCount(Date startDate, Integer companyId) throws Exception;
	
	@Query("SELECT COUNT(R.renewal_id) FROM RenewalReminder R "
			+ " INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> 4"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3"
			+ " where R.renewal_to < ?1 AND  R.companyId = ?2 AND R.markForDelete = 0 AND R.newRRCreated = 0")
	public Long getRenewalOverDueCount(Date startDate, Integer companyId, long id) throws Exception;

	
	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid AND V.vStatusId <> 4"
			+ " where p.dateofRenewal <= ?1 AND p.renewal_to > ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.newRRCreated = 0")
	public Page<RenewalReminder> getDeployment_Page_RenewalDueSoon(Date startDate, Integer companyId, Pageable pageable);
	
	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid AND V.vStatusId <> 4"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3"
			+ " where p.dateofRenewal <= ?1 AND p.renewal_to > ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.newRRCreated = 0")
	public Page<RenewalReminder> getDeployment_Page_RenewalDueSoon(Date startDate, Integer companyId, long id, Pageable pageable);
	
	@Query("select COUNT(DISTINCT RR.vid) from RenewalReminder RR "
			+ " INNER JOIN Vehicle V ON V.vid = RR.vid "
			+ " WHERE RR.companyId = ?1 AND RR.markForDelete = 0 AND V.vStatusId <> 4 ")
	public Long distinctRenewalRemndCreatedOnVehicles(Integer companyId) throws Exception;
	
	@Query("select DISTINCT RR.vid from RenewalReminder RR "
			+ " INNER JOIN Vehicle V ON V.vid = RR.vid "
			+ " WHERE RR.companyId = ?1 AND RR.markForDelete = 0 AND V.vStatusId <> 4 ")
	public List<RenewalReminder> listRenewalRemndCreatedOnVehicles(Integer companyId) throws Exception;
	
	@Query("SELECT p.renewal_id From RenewalReminder as p where  p.companyId = ?1 AND p.markForDelete = 0")
	public Page<RenewalReminder> getDeploymentPageRenewalReminderForAll(Integer companyId, Pageable pageable);
	
	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?2"
			+ " where p.companyId = ?1 AND p.markForDelete = 0")
	public Page<RenewalReminder> getDeploymentPageRenewalReminderForAll(Integer companyId, long id, Pageable pageable);
	
	
	@Query("SELECT p.renewal_id From RenewalReminder as p where p.renewal_to between ?1 AND ?2 AND p.companyId = ?3 AND p.newRRCreated = 0 AND p.markForDelete = 0")
	public Page<RenewalReminder> getDeploymentPageRenewalReminderByDate(Timestamp startdate,Timestamp endDate, Integer companyId, Pageable pageable);
	
	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?4"
			+ " where p.renewal_to between ?1 AND ?2 AND p.companyId = ?3 AND p.newRRCreated = 0 AND p.markForDelete = 0")
	public Page<RenewalReminder> getDeploymentPageRenewalReminderByDate(Timestamp startdate,Timestamp endDate, Integer companyId, long id, Pageable pageable);

	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid AND V.vStatusId <> 4 AND V.vStatusId <> 2 AND V.vStatusId <> 3"
			+ " where p.renewal_to < ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.newRRCreated = 0")
	public Page<RenewalReminder> getDeploymentPageRenewalOverDueExcludingSurrenderAndInactive(Date startDate, Integer companyId, Pageable pageable);
	
	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid AND V.vStatusId <> 4 AND V.vStatusId <> 2 AND V.vStatusId <> 3"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3"
			+ " where p.renewal_to < ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.newRRCreated = 0")
	public Page<RenewalReminder> getDeploymentPageRenewalOverDueExcludingSurrenderAndInactive(Date startDate, Integer companyId, long id, Pageable pageable);

	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid AND V.vStatusId = 2"
			+ " where p.renewal_to < ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.newRRCreated = 0")
	public Page<RenewalReminder> getDeploymentPageInactiveVehicleRenewalOverDue(Date startDate, Integer companyId, Pageable pageable);
	
	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid AND V.vStatusId = 2 "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3"
			+ " where p.renewal_to < ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.newRRCreated = 0")
	public Page<RenewalReminder> getDeploymentPageInactiveVehicleRenewalOverDue(Date startDate, Integer companyId, long id, Pageable pageable);
	
	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid AND V.vStatusId <> 4 AND V.vStatusId <> 2 AND V.vStatusId <> 3"
			+ " where p.dateofRenewal <= ?1 AND p.renewal_to > ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.newRRCreated = 0")
	public Page<RenewalReminder> getDeploymentPageRenewalDueSoonExcludingSurrenderAndInactive(Date startDate, Integer companyId, Pageable pageable);
	
	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid AND V.vStatusId <> 4 AND V.vStatusId <> 2 AND V.vStatusId <> 3"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3"
			+ " where p.dateofRenewal <= ?1 AND p.renewal_to > ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.newRRCreated = 0")
	public Page<RenewalReminder> getDeploymentPageRenewalDueSoonExcludingSurrenderAndInactive(Date startDate, Integer companyId, long id, Pageable pageable);
	
	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid AND V.vStatusId = 2"
			+ " where p.dateofRenewal <= ?1 AND p.renewal_to > ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.newRRCreated = 0")
	public Page<RenewalReminder> getDeploymentPageInactiveVehicleRenewalDueSoon(Date startDate, Integer companyId, Pageable pageable);
	
	@Query("SELECT p.renewal_id From RenewalReminder as p "
			+ " INNER JOIN Vehicle V ON V.vid = p.vid AND V.vStatusId = 2"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3"
			+ " where p.dateofRenewal <= ?1 AND p.renewal_to > ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.newRRCreated = 0")
	public Page<RenewalReminder> getDeploymentPageInactiveVehicleRenewalDueSoon(Date startDate, Integer companyId, long id, Pageable pageable);
	
	
	@Modifying
	@Query("UPDATE FROM RenewalReminder RR SET RR.ignored = 1, RR.lastModifiedById=?3 ,RR.lastupdated=?4 ,RR.igRemark = ?5 WHERE RR.renewal_id = ?1 AND RR.companyId = ?2 ")
	public void ignoreRenewalReminder(Long renewalReminder, Integer companyId , Long userId , Timestamp todate ,String remark);
	
}
