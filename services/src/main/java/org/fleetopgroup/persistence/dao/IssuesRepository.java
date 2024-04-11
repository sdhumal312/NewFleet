/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.persistence.model.Issues;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface IssuesRepository extends JpaRepository<Issues, Long> {

	// public Issues registerNew_Issues(Issues IssuesDto) throws Exception;

	// public Issues update_Issues(Issues IssuesDto) throws Exception;

	@Modifying
	@Query("UPDATE FROM Issues AS T SET ISSUES_ODOMETER=?1, ISSUES_DRIVER_ID=?2,  ISSUES_REPORTED_DATE=?3, "
			+ "ISSUES_SUMMARY=?4, ISSUES_DESCRIPTION=?5, ISSUES_LABELS_ID=?6 ,"
			+ " ISSUES_ASSIGN_TO=?7, ISSUES_ASSIGN_TO_NAME=?8, LASTMODIFIEDBYID=?9, LASTUPDATED_DATE=?10   where T.ISSUES_ID = ?11 AND T.COMPANY_ID = ?12")
	public void update_Vehicle_Issues(Integer ISSUES_ODOMETER, Integer ISSUES_DRIVER_ID, 
			Date ISSUES_REPORTED_DATE, String ISSUES_SUMMARY, String ISSUES_DESCRIPTION, short ISSUES_LABELS,
			String ISSUES_ASSIGN_TO, String ISSUES_ASSIGN_TO_NAME, Long LASTMODIFIEDBY, Date LASTUPDATED_DATE,
			Long ISSUES_ID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE FROM Issues AS T SET ISSUES_REPORTED_DATE=?1, ISSUES_SUMMARY=?2, ISSUES_DESCRIPTION=?3, ISSUES_LABELS_ID=?4 ,"
			+ " ISSUES_ASSIGN_TO=?5, ISSUES_ASSIGN_TO_NAME=?6, LASTMODIFIEDBYID=?7, LASTUPDATED_DATE=?8   where T.ISSUES_ID = ?9 AND T.COMPANY_ID = ?10")
	public void update_Driver_Issues(Date ISSUES_REPORTED_DATE, String ISSUES_SUMMARY, String ISSUES_DESCRIPTION,
			short ISSUES_LABELS, String ISSUES_ASSIGN_TO, String ISSUES_ASSIGN_TO_NAME, Long LASTMODIFIEDBY,
			Date LASTUPDATED_DATE, Long ISSUES_ID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE FROM Issues AS T SET ISSUES_BRANCH_ID=?1,  ISSUES_DEPARTNMENT_ID=?2, "
			+ " ISSUES_REPORTED_DATE=?3, ISSUES_SUMMARY=?4, ISSUES_DESCRIPTION=?5, ISSUES_LABELS_ID=?6 ,"
			+ " ISSUES_ASSIGN_TO=?7, ISSUES_ASSIGN_TO_NAME=?8, LASTMODIFIEDBYID=?9, LASTUPDATED_DATE=?10  where T.ISSUES_ID = ?11 AND T.COMPANY_ID = ?12")
	public void update_Other_Issues(Integer ISSUES_BRANCH_ID,  Integer ISSUES_DEPARTNMENT_ID,
			 Date ISSUES_REPORTED_DATE, String ISSUES_SUMMARY, String ISSUES_DESCRIPTION,
			short ISSUES_LABELS, String ISSUES_ASSIGN_TO, String ISSUES_ASSIGN_TO_NAME, Long LASTMODIFIEDBY,
			Date LASTUPDATED_DATE, Long ISSUES_ID, Integer companyId) throws Exception;

	@Query("select count(p) from Issues as p ")
	public Long countTotalIssues() throws Exception;

	/*@Query("select count(p) from Issues as p where p.ISSUES_STATUS =?1 ")
	public Long countStatus(String Status) throws Exception;
*/
	@Query("from Issues as p where p.ISSUES_ID =?1 ")
	public Issues get_IssuesDetails(Long Issues_ID) throws Exception;

	@Modifying
	@Query("UPDATE Issues T SET T.markForDelete = 1,LASTMODIFIEDBYID = ?3, LASTUPDATED_DATE = ?4 where T.ISSUES_ID = ?1 AND T.COMPANY_ID = ?2")
	public void delete_Issues(Long Issues_ID, Integer companyId ,Long userID,Timestamp todate);
	@Modifying
	@Query("UPDATE From Issues AS T SET T.ISSUES_STATUS_ID=?1 , T.LASTMODIFIEDBYID=?2, T.LASTUPDATED_DATE=?3  where T.ISSUES_ID = ?4 AND T.COMPANY_ID = ?5")
	public void update_Close_Issues_Status(short Status, Long CloseBy, Date close_date, Long Issues_ID, Integer companyId);

	@Modifying
	@Query("UPDATE From Issues AS T SET T.ISSUES_STATUS_ID=?1 , T.LASTMODIFIEDBYID=?2, T.LASTUPDATED_DATE=?3  where T.ISSUES_ID = ?4 AND T.COMPANY_ID = ?5")
	public void update_Reject_Issues_Status(short Status, Long CloseBy, Date close_date, Long Issues_ID, Integer companyId);

	@Modifying
	@Query("UPDATE From Issues AS T SET T.ISSUES_STATUS_ID=?1 , T.LASTMODIFIEDBYID=?2, T.LASTUPDATED_DATE=?3  where T.ISSUES_ID = ?4 AND T.COMPANY_ID = ?5")
	public void update_Reopen_Issues_Status(short Status, Long CloseBy, Date close_date, Long Issues_ID, Integer companyId);

	@Modifying
	@Query("UPDATE From Issues AS T SET T.ISSUES_STATUS_ID=?1 , T.LASTMODIFIEDBYID=?2, T.LASTUPDATED_DATE=?3  where T.ISSUES_ID = ?4 AND T.COMPANY_ID = ?5")
	public void update_ReSolved_Issues_Status(short Status, Long CloseBy, Date close_date, Long Issues_ID, Integer companyId);

	@Modifying
	@Query("UPDATE From Issues AS T SET T.ISSUES_STATUS_ID=?1 , T.LASTMODIFIEDBYID=?2, T.LASTUPDATED_DATE=?3, ISSUES_WORKORDER_ID=?4, ISSUES_WORKORDER_DATE=?3  where T.ISSUES_ID = ?5 AND T.COMPANY_ID = ?6")
	public void update_Create_WorkOrder_Issues_Status(short Status, Long CloseBy, Date close_date, Long WorkOrder_ID,
			Long Issues_ID, Integer companyId);

	@Query("SELECT R.ISSUES_ID from Issues as R "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = ?3 "
			+ " Where lower(R.ISSUES_ASSIGN_TO) like CONCAT('%',?1,'%') AND R.ISSUES_STATUS_ID <> 2 AND R.COMPANY_ID = ?2 AND R.markForDelete = 0")
	public Page<Issues> get_Issues_Your_pagination(String user_email, Integer companyId, Long id , Pageable pageable);

	@Query("SELECT R.ISSUES_ID from Issues as R Where lower(R.ISSUES_ASSIGN_TO) like CONCAT('%',?1,'%') AND R.ISSUES_STATUS_ID <> 2 AND R.COMPANY_ID = ?2 AND R.markForDelete = 0")
	public Page<Issues> get_Issues_Your_pagination(String user_email, Integer companyId, Pageable pageable);

	/**
	 * Update Vehicle Edit to Vehicle Group using vehicle Id
	 **/
	/*@Modifying
	@Query("UPDATE From Issues AS T SET T.ISSUES_VEHICLE_GROUP=?1, T.VEHICLE_GROUP_ID = ?3 where T.ISSUES_VID = ?2")
	public void update_Vehicle_Group_USING_Vehicle_Id(String Vehicle_Group, Integer vehicle_id, long vehicleGroupId);
	*/
	@Query("SELECT R.ISSUES_ID from Issues as R "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = ?2 "
			+ " Where  R.COMPANY_ID = ?1 AND R.markForDelete = 0")
	public Page<Issues> get_Issues_All_pagination(Integer companyId, Long id , Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R  Where  R.COMPANY_ID = ?1 AND R.markForDelete = 0")
	public Page<Issues> get_Issues_All_pagination(Integer companyId,Pageable pageable);
	
	
	@Query("from Issues as I "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = I.VEHICLE_GROUP_ID AND VGP.user_id = ?2 "
			+ " where I.ISSUES_NUMBER =?1 AND I.COMPANY_ID = ?3 AND I.markForDelete = 0 ")
	public Issues get_IssuesDetailsByNumber(Long Issues_ID, long id, Integer companyId) throws Exception;
	
	@Query("from Issues as I  where I.ISSUES_NUMBER =?1 AND I.COMPANY_ID = ?2 AND I.markForDelete = 0 ")
	public Issues get_IssuesDetailsByNumber(Long Issues_ID,Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE FROM Issues AS T SET ISSUES_REPORTED_DATE=?1, ISSUES_SUMMARY=?2, ISSUES_DESCRIPTION=?3, ISSUES_LABELS_ID=?4 ,"
			+ " ISSUES_ASSIGN_TO=?5, ISSUES_ASSIGN_TO_NAME=?6,CUSTOMER_NAME=?7, LASTMODIFIEDBYID=?8, LASTUPDATED_DATE=?9   where T.ISSUES_ID = ?10 AND T.COMPANY_ID = ?11")
	public void update_Customer_Issues(Date issues_REPORTED_DATE, String issues_SUMMARY, String issues_DESCRIPTION,
			short issues_LABELS_ID, String issues_ASSIGN_TO, String issues_ASSIGN_TO_NAME, String customer_NAME,
			Long lastmodifiedbyid, Date lastupdated_DATE, long issues_ID, Integer company_ID);
	
	@Modifying
	@Query("UPDATE From Issues AS T SET T.ISSUES_STATUS_ID=?1 , T.LASTMODIFIEDBYID=?2, T.LASTUPDATED_DATE=?3, T.ISSUES_SE_ID=?4, T.ISSUES_SE_DATE=?3  where T.ISSUES_ID = ?5 AND T.COMPANY_ID = ?6")
	public void update_Create_SE_Issues_Status(short Status, Long CloseBy, Date close_date, Long WorkOrder_ID,
			Long Issues_ID, Integer companyId);

	@Modifying
	@Query("UPDATE From Issues AS T SET T.ISSUES_STATUS_ID =?2  where T.ISSUES_SE_ID = ?1 AND T.ISSUES_ID =?3 AND T.COMPANY_ID = ?4")
	public void changeIssueStatusFromSE_createdToOpen(Long serviceEntriesId, short issueStatus,Long issuesId, Integer company_id);
	
	@Query("FROM Issues as I  where I.ISSUES_ID =?1 AND I.COMPANY_ID = ?2 AND I.markForDelete = 0 ")
	public Issues getIssueDetailsByIssueId(Long Issues_ID,Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE From Issues AS T SET T.ISSUES_SE_ID = 0 ,T.ISSUES_SE_DATE = NULL where T.ISSUES_ID = ?1 AND T.COMPANY_ID = ?2")
	public void update_SE_Issue_Details( Long Issues_ID, Integer companyId);
	
	@Modifying
	@Query("UPDATE From Issues AS T SET T.ISSUES_WORKORDER_ID = 0 where T.ISSUES_ID = ?1 AND T.COMPANY_ID = ?2")
	public void update_WO_Issue_Details( Long Issues_ID, Integer companyId);
	
	@Modifying
	@Query("UPDATE From Issues AS T SET T.ISSUES_STATUS_ID=?1 , T.LASTMODIFIEDBYID=?2, T.LASTUPDATED_DATE=?3, dealerServiceEntriesId=?4, dealerServiceEntriesCreatedOn=?3  where T.ISSUES_ID = ?5 AND T.COMPANY_ID = ?6")
	public void updateCreateDSE_IssueStatus(short Status, Long CloseBy, Date close_date, Long dealerServiceEntriesId, Long Issues_ID, Integer companyId);

	@Modifying
	@Query("UPDATE From Issues AS T SET T.dealerServiceEntriesId = 0 where T.ISSUES_ID = ?1 AND T.COMPANY_ID = ?2")
	public void update_DSE_Issue_Details( Long Issues_ID, Integer companyId);
	
	@Modifying
	@Query("UPDATE From Issues AS T SET T.ISSUES_STATUS_ID=?1 , T.LASTMODIFIEDBYID=?2, T.LASTUPDATED_DATE=?3, issueLP_ID=?4 where T.ISSUES_ID = ?5 AND T.COMPANY_ID = ?6")
	public void updateTyreAssignmentIssueStatus(short Status, Long CloseBy, Date close_date, Long issueLP_ID, Long Issues_ID, Integer companyId);
	
	@Modifying
	@Query("UPDATE From Issues AS T SET T.ISSUES_STATUS_ID=?1  where T.ISSUES_ID = ?2 AND T.COMPANY_ID = ?3")
	public void updateTyreAssignmentIssueStatus(short Status,  Long Issues_ID, Integer companyId);
	
	@Query("SELECT T.ISSUES_NUMBER FROM Issues AS T WHERE T.COMPANY_ID = ?1 AND T.ISSUES_ID = ?2 AND T.markForDelete = 0")
	public Long getIssues_Number(Integer COMPANY_ID, Long ISSUES_ID ) throws Exception;
	
	@Query("SELECT R.ISSUES_ID from Issues as R "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = ?2 "
			+ " Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?3 AND R.markForDelete = 0")
	public Page<Issues> get_OpenIssues_All_pagination(Integer companyId, Long id ,short issueStatus, Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R  Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?2 AND R.markForDelete = 0")
	public Page<Issues> get_OpenIssues_All_pagination(Integer companyId,short issueStatus,Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = ?2 "
			+ " Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?3 AND R.markForDelete = 0")
	public Page<Issues> get_Resolved_All_pagination(Integer companyId, Long id ,short issueStatus, Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R  Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?2 AND R.markForDelete = 0")
	public Page<Issues> get_Resolved_All_pagination(Integer companyId,short issueStatus, Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = ?2 "
			+ " Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?3 AND R.ISSUES_REPORTED_DATE <= ?4 AND R.markForDelete = 0")
	public Page<Issues> get_OverdueIssues_All_pagination(Integer companyId, Long id,short issueStatus ,Timestamp date, Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R  Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?2 AND R.ISSUES_REPORTED_DATE <= ?3 AND R.markForDelete = 0")
	public Page<Issues> get_OverdueIssues_All_pagination(Integer companyId,short issueStatus,Timestamp date, Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = ?2 "
			+ " Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?3 AND R.markForDelete = 0")
	public Page<Issues> get_WOCreatedIssues_All_pagination(Integer companyId, Long id , short issueStatus, Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R  Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?2 AND R.markForDelete = 0")
	public Page<Issues> get_WOCreatedIssues_All_pagination(Integer companyId,short issueStatus, Pageable pageable);
		
	@Query("SELECT R.ISSUES_ID from Issues as R "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = ?2 "
			+ " Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?3 AND R.markForDelete = 0")
	public Page<Issues> get_SECreatedIssues_All_pagination(Integer companyId, Long id ,short issueStatus, Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R  Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?2 AND R.markForDelete = 0")
	public Page<Issues> get_SECreatedIssues_All_pagination(Integer companyId,short issueStatus,Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = ?2 "
			+ " Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?3  AND R.markForDelete = 0")
	public Page<Issues> get_DSE_Created_Issues_All_pagination(Integer companyId, Long id , short issueStatus,  Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R  Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?2 AND R.markForDelete = 0")
	public Page<Issues> get_DSE_Created_Issues_All_pagination(Integer companyId,short issueStatus, Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = ?2 "
			+ " Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?3 AND R.markForDelete = 0")
	public Page<Issues> get_VehicleBreakDownIssues_All_pagination(Integer companyId, Long id, short issueStatus , Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R  Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?2 AND R.markForDelete = 0")
	public Page<Issues> get_VehicleBreakDownIssues_All_pagination(Integer companyId,short issueStatus, Pageable pageable);
		
	@Query("SELECT R.ISSUES_ID from Issues as R "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = ?2 "
			+ " Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?3 AND R.markForDelete = 0")
	public Page<Issues> get_CloseIssues_All_pagination(Integer companyId, Long id ,short issueStatus, Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R  Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?2 AND R.markForDelete = 0")
	public Page<Issues> get_CloseIssues_All_pagination(Integer companyId,short issueStatus, Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = ?2 "
			+ " Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?3 AND R.markForDelete = 0")
	public Page<Issues> get_RejectIssues_All_pagination(Integer companyId, Long id ,short issueStatus, Pageable pageable);
	
	@Query("SELECT R.ISSUES_ID from Issues as R  Where  R.COMPANY_ID = ?1 AND R.ISSUES_STATUS_ID = ?2 AND R.markForDelete = 0")
	public Page<Issues> get_RejectIssues_All_pagination(Integer companyId,short issueStatus, Pageable pageable);
	

}
