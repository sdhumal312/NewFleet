package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.ServiceReminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ServiceReminderRepository extends JpaRepository<ServiceReminder, Long> {

	@Modifying
	@Query(" UPDATE ServiceReminder SET serviceStatusId= ?2, workorders_id = ?4 Where service_id= ?1 AND companyId = ?3")
	public void updateWORKOrderToServiceReminder(Long Service_id, short Status, Integer companyId, Long workOrders_id);

	public List<ServiceReminder> findAll();

	@Query(" From ServiceReminder SR "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?2 "
			+ " where ( SR.companyId = ?3 AND SR.markForDelete = 0 ) AND SR.time_servicedate <= ?1  OR SR.meter_serviceodometer <= SR.vehicle_currentOdometer")
	public List<ServiceReminder> OverDueServiceRemnder(Date toDate, Long userId, Integer companyId) throws Exception;

	
	@Query(" From ServiceReminder SR "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?2 "
			+ " where ( SR.companyId = ?3 AND SR.markForDelete = 0 ) AND SR.time_servicethreshold_date <= ?1 AND  SR.time_servicedate >= ?1  OR SR.meter_servicethreshold_odometer <= SR.vehicle_currentOdometer AND SR.meter_serviceodometer >= vehicle_currentOdometer ")
	public List<ServiceReminder> DueSoonServiceRemnder(Date toDate, Long userId, Integer companyId) throws Exception;

	@Query(" from ServiceReminder where vehicle_registration = ?1 ")
	public List<ServiceReminder> listVehicleServiceReminder(String Service_vehiclename);

	@Query("from ServiceReminder SR where SR.service_id= ?1 AND SR.companyId = ?2 AND SR.markForDelete = 0")
	public ServiceReminder getServiceReminder(Long Service_id, Integer companyId);

	@Modifying
	@Query("UPDATE ServiceReminder SR SET SR.lastupdated=?2,SR.lastModifiedById=?3, SR.markForDelete = 1 WHERE SR.service_id = ?1 AND SR.companyId = ?4 ")
	public void deleteServiceReminder(Long ServiceReminder,Timestamp toDate, long userId, Integer companyId);

	@Query("from ServiceReminder where vid = ?1 AND service_type = ?2 AND service_subtype = ?3 AND companyId = ?4")
	public List<ServiceReminder> listSearchWorkorderToServiceReminder(Integer vid, String JobTask, String SubJobTask, Integer companyId)
			throws Exception;

	@Query("select count(*) from ServiceReminder ")
	public Long countServiceReminder() throws Exception;

	@Query("select count(*) from ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?3 "
			+ " where ( (SR.time_servicethreshold_date <= ?1 AND  SR.time_servicedate >= ?1 AND SR.time_interval > 0)  OR "
			+ " (SR.meter_servicethreshold_odometer <= vehicle_currentOdometer AND SR.meter_serviceodometer >= SR.vehicle_currentOdometer AND SR.meter_interval > 0 ) ) "
			+ " AND SR.companyId = ?2 AND SR.markForDelete = 0 AND V.vStatusId IN(1,5,6) AND SR.time_servicedate > ?1 and SR.meter_serviceodometer > V.vehicle_Odometer")
	public Long countTodayDueServiceReminder(Date toDate, Integer companyId, Long id) throws Exception;
	
	@Query("select count(distinct SR.vid) from ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?3 "
			+ " where ( (SR.time_servicethreshold_date <= ?1 AND  SR.time_servicedate >= ?1 AND SR.time_interval > 0)  OR "
			+ " (SR.meter_servicethreshold_odometer <= vehicle_currentOdometer AND SR.meter_serviceodometer >= SR.vehicle_currentOdometer AND SR.meter_interval > 0 ) ) "
			+ " AND SR.companyId = ?2 AND SR.markForDelete = 0 AND V.vStatusId IN(1,5,6) AND SR.time_servicedate > ?1 and SR.meter_serviceodometer > V.vehicle_Odometer")
	public Long countTodayDueServiceReminderGByVid(Date toDate, Integer companyId, Long id) throws Exception;


	@Query("select count(*) from ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " where ( (SR.time_servicethreshold_date <= ?1 AND  SR.time_servicedate >= ?1 AND SR.time_interval > 0) OR "
			+ " (SR.meter_servicethreshold_odometer <= vehicle_currentOdometer AND SR.meter_serviceodometer >= SR.vehicle_currentOdometer AND SR.meter_interval > 0 ))"
			+ " AND SR.companyId = ?2 AND SR.markForDelete = 0 AND V.vStatusId IN(1,5,6) AND SR.time_servicedate > ?1 and SR.meter_serviceodometer > V.vehicle_Odometer")
	public Long countTodayDueServiceReminder(Date toDate, Integer companyId) throws Exception;
	
	@Query("select count(distinct SR.vid) from ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " where ( (SR.time_servicethreshold_date <= ?1 AND SR.time_servicedate >= ?1 AND SR.time_interval > 0) OR "
			+ " (SR.meter_servicethreshold_odometer <= vehicle_currentOdometer AND SR.meter_serviceodometer >= SR.vehicle_currentOdometer AND SR.meter_interval > 0 ))"
			+ " AND SR.companyId = ?2 AND SR.markForDelete = 0 AND V.vStatusId IN(1,5,6) AND SR.time_servicedate > ?1 and SR.meter_serviceodometer > V.vehicle_Odometer")
	public Long countTodayDueServiceReminderGByVid(Date toDate, Integer companyId) throws Exception;

	@Query("select count(*) from ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?3 "
			+ " where SR.companyId = ?2 AND SR.markForDelete = 0 AND "
			+ "( (SR.time_servicedate <= ?1 AND  SR.time_interval > 0)  OR "
			+ " ( SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0 )) AND V.vStatusId IN(1,5,6)")
	public Long countTodayOverDueServiceReminder(Date toDate, Integer companyId, Long id) throws Exception;
	
	@Query("select count(*) from ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " where SR.companyId = ?2 AND SR.markForDelete = 0 AND ( ( SR.time_servicedate = ?1 AND SR.time_interval > 0 ) "
			+ " AND SR.meter_interval > 0 ) AND V.vStatusId IN(1,5,6)"
			+ " ")
	public Long countTodaysOverDueServiceReminder(Date toDate, Integer companyId) throws Exception;
	
	@Query("select count(*) from ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?3 "
			+ " where ( (SR.time_servicedate = ?1 AND SR.time_interval > 0) "
			+ " AND SR.meter_interval > 0 ) "
			+ " AND SR.companyId = ?2 AND SR.markForDelete = 0 AND V.vStatusId IN(1,5,6) ")
	public Long countTodaysOverDueServiceReminder(Date toDate, Integer companyId, Long id) throws Exception;
	
	@Query("select count(distinct SR.vid) from ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " where ( (SR.time_servicedate = ?1 AND SR.time_interval > 0) "
			+ " AND SR.meter_interval > 0 )"
			+ " AND SR.companyId = ?2 AND SR.markForDelete = 0 AND V.vStatusId IN(1,5,6) ")
	public Long countTodaysOverDueServiceReminderGByVid(Date toDate, Integer companyId) throws Exception;
	
	@Query("select count(distinct SR.vid) from ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?3 "
			+ " where ( (SR.time_servicedate = ?1 AND SR.time_interval > 0) "
			+ " AND SR.meter_interval > 0 ) "
			+ " AND SR.companyId = ?2 AND SR.markForDelete = 0 AND V.vStatusId IN(1,5,6) ")
	public Long countTodaysOverDueServiceReminderGByVid(Date toDate, Integer companyId, Long id) throws Exception;

	@Query("SELECT COUNT(*) FROM ServiceReminder SR "
	        + " INNER JOIN Vehicle V ON V.vid = SR.vid "
	        + " WHERE SR.companyId = ?3 AND SR.markForDelete = 0 AND ( "
	        + " ((SR.time_servicedate BETWEEN ?1 AND ?2) AND SR.time_interval > 0) "
	        + " AND SR.meter_interval > 0 )"
	        + " AND V.vStatusId IN (1, 5, 6)")
	public Long countUpcomingDueServiceReminder(Date fromDate, Date toDate, Integer companyId) throws Exception;

	@Query("SELECT COUNT(*) FROM ServiceReminder SR "
	        + " INNER JOIN Vehicle V ON V.vid = SR.vid "
	        + " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?4 "
	        + " WHERE SR.companyId = ?3 AND SR.markForDelete = 0 AND ( "
	        + " ((SR.time_servicedate BETWEEN ?1 AND ?2) AND SR.time_interval > 0) "
	        + " AND SR.meter_interval > 0 )"
	        + " AND V.vStatusId IN (1, 5, 6)")
	public Long countUpcomingDueServiceReminder(Date fromDate, Date toDate, Integer companyId, Long id) throws Exception;

	@Query("SELECT COUNT(distinct SR.vid) FROM ServiceReminder SR "
	        + " INNER JOIN Vehicle V ON V.vid = SR.vid "
	        + " WHERE SR.companyId = ?3 AND SR.markForDelete = 0 AND ( "
	        + " ((SR.time_servicedate BETWEEN ?1 AND ?2) AND SR.time_interval > 0) "
	        + " AND SR.meter_interval > 0 )"
	        + " AND V.vStatusId IN (1, 5, 6)")
	public Long countUpcomingDueServiceReminderGByVid(Date fromDate, Date toDate, Integer companyId) throws Exception;

	@Query("SELECT COUNT(distinct SR.vid) FROM ServiceReminder SR "
	        + " INNER JOIN Vehicle V ON V.vid = SR.vid "
	        + " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?4 "
	        + " WHERE SR.companyId = ?3 AND SR.markForDelete = 0 AND ( "
	        + " ((SR.time_servicedate BETWEEN ?1 AND ?2) AND SR.time_interval > 0) "
	        + " AND SR.meter_interval > 0 )"
	        + " AND V.vStatusId IN (1, 5, 6)")
	public Long countUpcomingDueServiceReminderGByVid(Date fromDate, Date toDate, Integer companyId, Long id) throws Exception;
	
	@Query("select count(distinct SR.vid) from ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?3 "
			+ " where SR.companyId = ?2 AND SR.markForDelete = 0 AND "
			+ "( (SR.time_servicedate <= ?1 AND  SR.time_interval > 0)  OR "
			+ " ( SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0 )) AND V.vStatusId IN(1,5,6)")
	public Long countTodayOverDueServiceReminderGByVid(Date toDate, Integer companyId, Long id) throws Exception;


	@Query("select count(*) from ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " where SR.companyId = ?2 AND SR.markForDelete = 0 AND ( ( SR.time_servicedate <= ?1 AND SR.time_interval > 0 ) OR "
			+ " ( SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0 ) ) AND V.vStatusId IN(1,5,6)"
			+ " ")
	public Long countTodayOverDueServiceReminder(Date toDate, Integer companyId) throws Exception;
	
	@Query("select count(distinct SR.vid) from ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " where SR.companyId = ?2 AND SR.markForDelete = 0 AND ( ( SR.time_servicedate <= ?1 AND SR.time_interval > 0 ) OR "
			+ " ( SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0 ) ) AND V.vStatusId IN(1,5,6) "
			+ " ")
	public Long countTodayOverDueServiceReminderGByVid(Date toDate, Integer companyId) throws Exception;

	@Modifying
	@Query("update ServiceReminder set vehicle_currentOdometer= ?2 where vid= ?1 AND companyId = ?3 ")
	public void updateCurrentOdometerToServiceReminder(Integer vehicle_id, Integer oddmeter_Current, Integer companyId) throws Exception;

	
	@Modifying
	@Query("update ServiceReminder set thresholdUpdateOdometer= ?1, serviceOdometerUpdatedDate = ?3 where vid= ?2 AND meter_servicethreshold_odometer < ?1 AND thresholdUpdateOdometer <= meter_servicethreshold_odometer")
	public void updateOdometerThreshHoldToServiceReminder(Integer oddmeter_Current,  Integer vehicle_id, Date threshHoldDate) throws Exception;

	@Modifying
	@Query("update ServiceReminder set vehicle_Group= ?1, vehicleGroupId = ?3 where vid= ?2 and companyId = ?4 ")
	public void update_Vehicle_Group_USING_Vehicle_Id(String Vehicle_Group, Integer vehicle_id, long vehicleGroupId, Integer companyId);
	
	@Query("from ServiceReminder SR"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?2 "
			+ " where  SR.companyId = ?3 AND SR.markForDelete = 0 AND SR.service_Number= ?1 ")
	public ServiceReminder getServiceReminderByNumber(Long Service_id, Long userId, Integer companyId) throws Exception;

	@Query("from ServiceReminder SR where  SR.companyId = ?2 AND SR.markForDelete = 0 AND SR.service_Number= ?1 ")
	public ServiceReminder getServiceReminderByNumber(Long Service_id, Integer companyId) throws Exception;
	
	
	@Query("SELECT SR.service_id FROM ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?1 "
			+ " where SR.companyId = ?2 AND SR.markForDelete = 0 AND V.vStatusId IN(1,5,6) ")
	public Page<ServiceReminder> getDeployment_Page_ServiceReminder(long userId,Integer companyId, Pageable pageable);
	
	@Query("SELECT SR.service_id FROM ServiceReminder SR "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?1 "
			+ " where SR.companyId = ?2 AND SR.markForDelete = 0 AND V.vStatusId IN(1,5,6) group by V.vid ")
	public Page<ServiceReminder> getDeployment_Page_ServiceReminderByVid(long userId,Integer companyId, Pageable pageable);
	
	
	@Query("SELECT SR.service_id FROM ServiceReminder SR  "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " where SR.companyId = ?1 AND SR.markForDelete = 0 AND V.vStatusId IN(1,5,6) ")
	public Page<ServiceReminder> getDeployment_Page_ServiceReminder(Integer companyId, Pageable pageable);
	
	@Query("SELECT SR.service_id FROM ServiceReminder SR  "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " where SR.companyId = ?1 AND SR.markForDelete = 0 AND V.vStatusId IN(1,5,6) group by V.vid")
	public Page<ServiceReminder> getDeployment_Page_ServiceReminderByVehicle(Integer companyId, Pageable pageable);
	
	@Query("SELECT SR.service_id FROM ServiceReminder SR  "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " where ( SR.companyId = ?2 AND SR.markForDelete = 0 )"
			+ " AND ( (SR.time_servicedate <= ?1 AND SR.time_interval > 0)  OR (SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0 )) AND V.vStatusId IN(1,5,6) ")
	public Page<ServiceReminder> getServiceReminderOverduePage(Timestamp toDate, Integer companyId, Pageable pageable);
	
	@Query("SELECT SR.service_id FROM ServiceReminder SR  "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " where ( SR.companyId = ?2 AND SR.markForDelete = 0 )"
			+ " AND ( (SR.time_servicedate = ?1 AND SR.time_interval > 0)  OR (SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0 )) AND V.vStatusId IN(1,5,6) ")
	public Page<ServiceReminder> getServiceReminderTodaysOverduePage(Timestamp toDate, Integer companyId, Pageable pageable);
	
	@Query("SELECT SR.service_id FROM ServiceReminder SR  "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " where ( SR.companyId = ?2 AND SR.markForDelete = 0 )"
			+ " AND ( (SR.time_servicedate <= ?1 AND SR.time_interval > 0)  OR (SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0 )) AND V.vStatusId IN(1,5,6) group by SR.vid ")
	public Page<ServiceReminder> getServiceReminderOverduePageGByVid(Timestamp toDate, Integer companyId, Pageable pageable);
	
	@Query("SELECT SR.service_id FROM ServiceReminder SR  "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " where ( SR.companyId = ?2 AND SR.markForDelete = 0 )"
			+ " AND ( (SR.time_servicedate = ?1 AND SR.time_interval > 0)  OR (SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0 )) AND V.vStatusId IN(1,5,6) group by SR.vid ")
	public Page<ServiceReminder> getServiceReminderTodaysOverduePageGByVid(Timestamp toDate, Integer companyId, Pageable pageable);
	
	@Query("SELECT SR.service_id FROM ServiceReminder SR  "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?2 "
			+ " where ( SR.companyId = ?3 AND SR.markForDelete = 0 )"
			+ " AND ( (SR.time_servicedate <= ?1  AND SR.time_interval > 0) "
			+ " OR ( SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0) ) AND V.vStatusId IN(1,5,6) ")
	public Page<ServiceReminder> getServiceReminderOverduePage(Timestamp toDate, long userId, Integer companyId, Pageable pageable);
	
	@Query("SELECT SR.service_id FROM ServiceReminder SR  "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?2 "
			+ " where ( SR.companyId = ?3 AND SR.markForDelete = 0 )"
			+ " AND ( (SR.time_servicedate = ?1  AND SR.time_interval > 0) "
			+ " OR ( SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0) ) AND V.vStatusId IN(1,5,6) ")
	public Page<ServiceReminder> getServiceReminderTodaysOverduePage(Timestamp toDate, long userId, Integer companyId, Pageable pageable);
	
	@Query("SELECT SR.service_id FROM ServiceReminder SR  "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?2 "
			+ " where ( SR.companyId = ?3 AND SR.markForDelete = 0 )"
			+ " AND ( (SR.time_servicedate <= ?1  AND SR.time_interval > 0) "
			+ " OR ( SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0) ) AND V.vStatusId IN(1,5,6) group by SR.vid ")
	public Page<ServiceReminder> getServiceReminderOverduePageGByVid(Timestamp toDate, long userId, Integer companyId, Pageable pageable);

	@Query("SELECT SR.service_id FROM ServiceReminder SR  "
			+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?2 "
			+ " where ( SR.companyId = ?3 AND SR.markForDelete = 0 )"
			+ " AND ( (SR.time_servicedate = ?1  AND SR.time_interval > 0) "
			+ " OR ( SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0) ) AND V.vStatusId IN(1,5,6) group by SR.vid ")
	public Page<ServiceReminder> getServiceReminderTodaysOverduePageGByVid(Timestamp toDate, long userId, Integer companyId, Pageable pageable);
	
	@Query("select count(DISTINCT SR.vid) from ServiceReminder SR "
			+ "INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ "WHERE SR.companyId = ?1 AND SR.markForDelete = 0 AND V.vStatusId <> 4 ")
	public Long distinctServRemndCreatedOnVehicles(Integer companyId) throws Exception;
	
	@Query("select DISTINCT SR.vid from ServiceReminder SR "
			+ "INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ "WHERE SR.companyId = ?1 AND SR.markForDelete = 0 AND V.vStatusId <> 4 ")
	public List<ServiceReminder> listServRemndCreatedOnActVehicles(Integer companyId) throws Exception;
	
	@Query("from ServiceReminder SR where  SR.vid = ?1 AND SR.serviceTypeId =?2 AND SR.serviceSubTypeId =?3 AND SR.markForDelete = 0 ")
	public List<ServiceReminder> validateServiceReminder(Integer vid, Integer serviceType, Integer serviceSubType) throws Exception;
	
	@Query("from ServiceReminder SR where  SR.vid = ?1 AND SR.serviceTypeId =?2 AND SR.serviceSubTypeId =?3 AND SR.service_id <> ?4 AND SR.markForDelete = 0 ")
	public List<ServiceReminder> validateServiceReminderEdit(Integer vid, Integer serviceType, Integer serviceSubType, Long serviceId) throws Exception;

	@Query("SELECT SR.service_id FROM ServiceReminder SR  "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?3 "
			+ " where SR.service_Number = ?1 AND  SR.companyId = ?2 AND SR.markForDelete = 0 ")
	public Long getServiceIdByNumber(Long number, Integer companyId, Long userId) throws Exception ;
	
	@Query("SELECT SR.service_id FROM ServiceReminder SR  "
			+ " where SR.service_Number = ?1 AND  SR.companyId = ?2 AND SR.markForDelete = 0 ")
	public Long getServiceIdByNumber(Long number, Integer companyId) throws Exception ;
	
	@Query("SELECT SR FROM ServiceReminder SR  "
			+ " INNER JOIN Vehicle VGP ON VGP.vid = SR.vid AND VGP.markForDelete = 0 "
			+ " where VGP.vehicleTypeId = ?1 AND VGP.vehicleModalId = ?2 AND  SR.companyId = ?3 AND SR.markForDelete = 0 ")
	public List<ServiceReminder>  getSRLIstByVehicleTypeModalAndCompanyId(Long vehicleType, Long vehicleModal, Integer companyId);
	
	
	@Query("SELECT COUNT(*) FROM ServiceReminder SR "
			+ "INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ " where SR.companyId = ?1 AND SR.serviceProgramId=?2 AND SR.serviceScheduleId=?3 AND SR.markForDelete = 0 AND V.vStatusId IN(1,5,6)")
	public Long getServiceReminderByserviceProgram(Integer companyId,Long serviceProgramId ,Long serviceScheduleId );
	
	@Query("SELECT COUNT(*) FROM ServiceReminder SR  "
			+ "INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ "where SR.companyId = ?1 AND SR.serviceProgramId=?2  AND SR.markForDelete = 0 AND V.vStatusId IN(1,5,6)")
	public Long getServiceReminderByserviceProgram(Integer companyId,Long serviceProgramId);
	
	@Query("FROM ServiceReminder where service_id = ?1 ")
	public ServiceReminder getServiceReminderById(Long id) ;
	
	@Modifying
	@Query("UPDATE ServiceReminder SET markForDelete = 1 where vid = ?1 and companyId = ?2 and serviceProgramId = ?3")
	public void deleteServiceReminderByVid(Integer vid, Integer companyId, Long serviceProgramId) throws Exception;
	
	@Modifying
	@Query(" UPDATE ServiceReminder SET serviceStatusId= ?2, isSkip = 1, skipRemark = ?4 Where service_id= ?1 AND companyId = ?3")
	public void updateSkipServiceReminder(Long Service_id, short Status, Integer companyId, String remark);
	
	@Query("SELECT SR.serviceTypeId, SR.serviceSubTypeId FROM ServiceReminder SR  "
			+ "INNER JOIN Vehicle V ON V.vid = SR.vid "
			+ "where SR.serviceTypeId = ?1 AND SR.serviceSubTypeId=?2 AND SR.vid = ?3 AND SR.markForDelete = 0 ")
	public ServiceReminder getCheckDuplicateServiceReminder(Integer serviceTypeId,Integer serviceSubTypeId, Integer vid);
	
	@Modifying
	@Query("UPDATE ServiceReminder SET meter_interval = ?2 , meter_threshold = ?3 , meter_serviceodometer =?4 ,"
			+ " meter_servicethreshold_odometer = ?5 , time_interval =?6, time_interval_currentdate =?7 , "
			+ " time_servicedate = ?8 ,time_threshold = ?9 ,time_servicethreshold_date = ?10, service_subscribeduser_name =?11 "
			+ " WHERE service_id = ?1 ")
	public void updateServiceReminder(Long service_id, Integer meter_interval, Integer meter_threshold, 
			
			Integer meter_serviceodometer, Integer meter_servicethreshold_odometer, Integer time_interval,
			Date time_interval_currentdate, Date time_servicedate, Integer time_threshold, 
			Date time_servicethreshold_date, String service_subscribeduser_name);
	
		@Query("SELECT SR.service_id FROM ServiceReminder SR  "
				+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
				+ " where ( SR.companyId = ?3 AND SR.markForDelete = 0 )"
				+ " AND ( ((SR.time_servicedate BETWEEN ?1 AND ?2) AND SR.time_interval > 0)  OR (SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0 )) AND V.vStatusId IN(1,5,6) ")
		public Page<ServiceReminder> getServiceReminderUpcomingOverduePage(Timestamp fromDate, Timestamp toDate, Integer companyId, Pageable pageable);
	
		@Query("SELECT SR.service_id FROM ServiceReminder SR  "
				+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
				+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?2 "
				+ " where ( SR.companyId = ?3 AND SR.markForDelete = 0 )"
				+ " AND ( ((SR.time_servicedate BETWEEN ?1 AND ?2)  AND SR.time_interval > 0) "
				+ " OR ( SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0) ) AND V.vStatusId IN(1,5,6) ")
		public Page<ServiceReminder> getServiceReminderUpcomingOverduePage(Timestamp fromDate, Timestamp toDate, long userId, Integer companyId, Pageable pageable);

		@Query("SELECT SR.service_id FROM ServiceReminder SR  "
				+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
				+ " where ( SR.companyId = ?3 AND SR.markForDelete = 0 )"
				+ " AND ( ((SR.time_servicedate BETWEEN ?1 AND ?2) AND SR.time_interval > 0)  OR (SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0 )) AND V.vStatusId IN(1,5,6) group by SR.vid ")
		public Page<ServiceReminder> getServiceReminderUpcomingOverduePageGByVid(Timestamp fromDate,Timestamp toDate, Integer companyId, Pageable pageable);

		@Query("SELECT SR.service_id FROM ServiceReminder SR  "
				+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
				+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = ?2 "
				+ " where ( SR.companyId = ?3 AND SR.markForDelete = 0 )"
				+ " AND ( ((SR.time_servicedate BETWEEN ?1 AND ?2)  AND SR.time_interval > 0) "
				+ " OR ( SR.meter_serviceodometer <= SR.vehicle_currentOdometer AND SR.meter_interval > 0) ) AND V.vStatusId IN(1,5,6) group by SR.vid ")
		public Page<ServiceReminder> getServiceReminderUpcomingOverduePageGByVid(Timestamp fromDate, Timestamp toDate, long userId, Integer companyId, Pageable pageable);
}
