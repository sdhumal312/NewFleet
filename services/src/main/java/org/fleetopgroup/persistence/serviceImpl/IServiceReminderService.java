package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.model.DealerServiceReminderHistory;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.ServiceReminderWorkOrderHistory;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface IServiceReminderService {

	public void addServiceReminder(ServiceReminder ServiceReminder);
	
	public void addMultipleServiceReminder(List<ServiceReminder>  reminderList) throws Exception;

	public ServiceReminder updateServiceReminder(ServiceReminder ServiceReminder);

	public void updateWORKOrderToServiceReminder(Long Service_id, short Status, Integer companyId, Long workOrders_id);

	/**
	 * This List get ServiceReminder table to get pagination last 10 entries
	 * values
	 */
	public List<ServiceReminderDto> listServiceReminder(Integer pageNumber, CustomUserDetails userDetails) throws Exception;
	
	public List<ServiceReminderDto> listServiceReminderGroupByVid(Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	/** This Page get ServiceReminder table to get pagination values */
	public Page<ServiceReminder> getDeployment_Page_ServiceReminder(Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	public Page<ServiceReminder> getDeployment_Page_ServiceReminderByVid(Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	
	public List<ServiceReminder> AllServiceReminderList() throws Exception;

	public List<ServiceReminderDto> OverDueServiceRemnder(Date toDate, CustomUserDetails userDetails, Integer pageNumber) throws Exception;

	public List<ServiceReminderDto> DueSoonServiceRemnder(Date toDate, CustomUserDetails userDetails) throws Exception;

	public List<ServiceReminder> listVehicleServiceReminder(String Service_vehiclename);

	public List<ServiceReminder> listSearchWorkorderToServiceReminder(Integer vid, Integer JobTask, Integer SubJobTask, Integer companyId)
			throws Exception;

	public ServiceReminderDto getServiceReminder(Long Service_id, Integer companyId) throws Exception;

	public void deleteServiceReminder(Long ServiceReminder, Timestamp toDate, long userId, Integer companyId);

	public List<ServiceReminder> listServiceReminder(String query);

	public Long countServiceReminder() throws Exception;

	public Long countTodayDueServiceReminder(Date toDate, Integer companyId, Long id) throws Exception;

	public Long countTodayOverDueServiceReminder(Date toDate, Integer companyId, Long id) throws Exception;

	public List<ServiceReminder> SearchServiceReminder(String Search) throws Exception;

	public List<ServiceReminderDto> OnlyVehicleServiceReminderList(Integer vehicle_ID, Integer companyId, Long id) throws Exception;
	
	public List<ServiceReminderDto> OnlyVehicleServiceReminderListByDate(Integer vehicle_ID, String overDue, String dueSoon,Integer companyId) throws Exception;

	public void updateCurrentOdometerToServiceReminder(Integer vehicle_id, Integer oddmeter_Current, Integer companyId) throws Exception;

	public List<ServiceReminderDto> ReportServiceReminder(String query, Integer companyId) throws Exception;

	public List<ServiceReminderDto> ReportOverDueServiceReminder(String query, String Currentdate, Integer companyId) throws Exception;

	public List<ServiceReminderDto> ReportDueSoonServiceReminder(String query, String Currentdate, Integer companyId) throws Exception;

	/**
	 * Subscribe display value of the OverDue & Due Soon ServiceReminder Select
	 */
	public List<ServiceReminderDto> OverDue_DueSoon_Subcribe_ServiceReminder(Date toDate) throws Exception;

	/**
	 * Update Vehicle Edit to Vehicle Group using vehicle Id
	 **/
	public void update_Vehicle_Group_USING_Vehicle_Id(String Vehicle_Group, Integer vehicle_id, long vehicleGroupId, Integer companyId) throws Exception;
	
	//public List<ServiceReminder> listServiceReminder(CustomUserDetails	userDetails) throws Exception;
	
	public List<ServiceReminderDto> SearchServiceReminderByNumber(String Search, CustomUserDetails	userDetails) throws Exception;
	
	public ServiceReminderDto getServiceReminderByNumber(Long Service_id, CustomUserDetails userDetails) throws Exception;
	
	public ValueObject	getServiceReminderCalendarDetails(ValueObject valueObject) throws Exception;
	
	public ServiceReminderWorkOrderHistory getServiceWorkHistory(Long workOrderId)throws Exception;
	
	public ValueObject getServiceReminderListOftheDay(ValueObject valueObject) throws Exception;
	
	public ValueObject getReminderStatusForOverdue(Integer vid) throws Exception;
	
	public Page<ServiceReminder> geServiceReminderOverDuePage(Timestamp toDate, Integer pageNumber, CustomUserDetails userDetails) throws Exception;
	
	public Page<ServiceReminder> geServiceReminderOverDuePageGByVid(Timestamp toDate, Integer pageNumber, CustomUserDetails userDetails) throws Exception;
	
	public List<ServiceReminderDto> getServiceReminderByThresholdDate(String currentDate, String companyId) throws Exception ;
	
	public Long getTotalServiceReminderCount(String startDate, String endDate,Integer companyId) throws Exception;

	public HashMap<Integer, Long> getALLEmailServiceReminderDailyWorkStatus(String startDate, String endDate) throws Exception;

	public List<CompanyDto> getALLEmailServiceReminderDailyWorkForManagers(String startDate, String endDate) throws Exception;
	
	public Long getTotalServiceReminderOverDueCount(String date, Integer companyId) throws Exception;
	
	public Long getDistinctTotalServiceReminderOverDueCount(String date, String serviceIds, Integer companyId) throws Exception;
	
	public HashMap<Integer, Long> getALLServiceReminderOverDueCOunt(String startDate) throws Exception;
	
	public Long getDistinctTotalServiceReminderOverDueCountBetweenDates(String startDate, String endDate, String serviceId, Integer companyId) throws Exception;
	
	public List<ServiceReminder> getDistinctTotalServiceReminderOverDueListBetweenDates(String startDate, String endDate, String serviceId, Integer companyId) throws Exception;
	
	public HashMap<Integer, Long> getALLServiceReminderOverDueMore15COunt(String startDate) throws Exception;
	
	public Long getTotalServiceReminderOverDueCountBetweenDates(String startDate, String endDate,Integer companyId) throws Exception;
	
	public List<ServiceReminder> getTotalServiceReminderOverDueListBetweenDates(String startDate, String endDate, Integer companyId) throws Exception;
	
	public HashMap<Integer, Long> getALLServiceReminderOverDueCOunt(String startDate, String endDate) throws Exception;
	
	public void setServiceOdometerUpdatedDate(long service_id, int companyId) throws Exception;
	
	public long totalDueSoonCount (String date, int companyId) throws Exception;
	
	public long todaysServiceCount (String startDate, String endDate, int companyId) throws Exception;
	
	public List<ServiceReminderDto> getServiceReminderTableListBetweenDates(String query, Integer companyID) throws Exception;

	public List<ServiceReminderDto> getListServiceReminderMonthWiseDueSoon(String startDateOfMonth,String endDateOfMonth, Integer companyId) throws Exception;

	public ValueObject configureReminderOnStartDayOfMonthEmailBody(ValueObject valueInObject) throws Exception;
	
	public List<ServiceReminderDto> getServiceReminderOverDueListServiceDateWise(String date, int compId) throws Exception;
	
	public List<ServiceReminderDto> getServiceReminderOverDueListOdometerWise(String date, int compId) throws Exception; 
	
	public HashMap<Long ,ServiceReminderDto> getSROverDue15PlusCount(List<ServiceReminderDto> Dtos) throws Exception;
	
	public List<ServiceReminderDto> getListOfNumberOfSRCreatedOnVehicles(Integer companyID) throws Exception;
	
	public ValueObject	getServiceReminderList(ValueObject valueObject) throws Exception;
	
	public ValueObject	getOverDueServiceList(ValueObject valueObject) throws Exception;
	
	public ValueObject	getDueSoonServiceList(ValueObject valueObject) throws Exception;
	
	public ValueObject	saveServiceReminderDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject	getServiceReminderData(ValueObject valueObject) throws Exception;
	
	public ValueObject	updateServiceReminderDetails(ValueObject valueObject) throws Exception;
	
	public List<ServiceReminder>  validateServiceReminder(Integer vid, Integer serviceType, Integer serviceSubType) throws Exception;
	
	public List<ServiceReminder> validateServiceReminderEdit(Integer vid, Integer serviceType, Integer serviceSubType, Long serviceId) throws Exception;
	
	public ValueObject	searchServiceReminderByNumber(ValueObject valueObject) throws Exception;
	
	public Long getServiceIdByNumber(Long number, CustomUserDetails userDetails) throws Exception;
	
	public ValueObject	searchServiceReminderByMultiple(ValueObject valueObject) throws Exception;
	
	public ValueObject	deleteServiceReminderById(ValueObject valueObject) throws Exception;

	public ValueObject getVehicleWiseActiveSR(ValueObject object)throws Exception;
	
	public ServiceReminderDto	getServiceReminderByVidAndServiceId(Integer vid, long serviceId, Integer companyId) throws Exception;

	public void updateServiceIdInServiceReminder(ValueObject valueObject) throws Exception;
	
	public Map<String, ServiceReminder> getMapOfServiceReminderByCompanyId(Long vehicleType, Long vehiclModal, Integer companyId,String query) throws Exception;
	
	public void deleteServiceReminderForSchedule(Long serviceScheduleId, Integer companyId, Long userId, Long vehicleServiceProgramId) throws Exception;
	
	public void deleteReminderServiceProgramAssignment(Long vehicleTypeId, Long modalId, Long serviceProgramId,
			Integer companyId) throws Exception;


	public List<ServiceReminder> getServiceReminderListByServiceReminderIds(String string, Integer companyId)throws Exception;

	public DealerServiceReminderHistory getDealerServiceReminderHistory(Long dealerServiceEntriesId)throws Exception;
	
	public List<ServiceReminderDto> listServiceReminderByServiceProgram(Long serviceProgram,Long sheduledId, CustomUserDetails userDetails) throws Exception;
	
	public ValueObject	getDueSoonServiceListGroupBySProgram(ValueObject valueObject) throws Exception;
	
	public ValueObject	getOverDueServiceListGroupBySProgram(ValueObject valueObject) throws Exception;
	
	public List<ServiceReminderDto> OverDueServiceRemnder(Date toDate, CustomUserDetails userDetails) throws Exception ;
	
	public List<ServiceReminderDto> getServiceProgramListByVid(Integer vehicle_ID, String overDue, String dueSoon,Integer companyId) throws Exception;
	
	public List<ServiceReminderDto> getServiceProgramListByVidProgramId(Integer vid, String serviceProgramId, Integer companyId) throws Exception;

	public ValueObject getServiceReminderByProgramIdVid(ValueObject object)throws Exception;
	
	public List<ServiceReminderDto> getProgramListByreminderIds(String serviceIds, Integer companyId) throws Exception;

	public HashMap<Integer, ServiceReminderDto> getJobtypeAndSubtypeFromServiceReminderId(String serviceIds, Integer company_id) throws Exception;

	public ValueObject getServiceReminderByServiceId(ValueObject object) throws Exception;

	public List<ServiceReminderDto> getServiceReminderByServiceId(String serviceProgramId, Integer companyId) throws Exception;

	public void deleteReminderByServiceIds(String ids) throws Exception;

	public List<ServiceReminderDto> getSRByVehicleTypeModalAndBranach(Long vehicleTypeId, Long modalId, Integer branchId, Long serviceProgramId,
			Integer companyId)throws Exception;

	List<ServiceReminderDto> getSRByVehicleTypeAndModal(Long vehicleTypeId, Long modalId, Long serviceProgramId,
			Integer companyId) throws Exception;
	
	public void deleteServiceReminderByVid(Integer vid, Integer companyId, Long serviceProgramId) throws Exception;
	
	public ValueObject getServiceReminderOfVehicle(ValueObject object) throws Exception;
	
	public List<ServiceReminderDto> listServiceReminderByVid(Integer vid, CustomUserDetails userDetails) throws Exception;
	
	public List<ServiceReminderDto> OverDueServiceRemnderGByVid(Date toDate, CustomUserDetails userDetails, Integer pageNumber) throws Exception;
	
	public ValueObject getOverDueReminderOfVehicle(ValueObject object) throws Exception;
	
	public List<ServiceReminderDto> listOverDueReminderByVid(Integer vid, CustomUserDetails userDetails)
			throws Exception ;
	
	public List<ServiceReminderDto> DueSoonServiceRemnderGByVid(Date toDate, CustomUserDetails userDetails) throws Exception;
	
	public ValueObject getDueSoonReminderOfVehicle(ValueObject object) throws Exception;
	
	public List<ServiceReminderDto> listDueSoonReminderByVid(Integer vid, CustomUserDetails userDetails)
			throws Exception ;
	
	public ValueObject skipSrWithRemark(ValueObject object) throws Exception;
	
	public List<ServiceReminderDto> getSRByreminderIds(String serviceIds, Integer companyId) throws Exception;

	Map<String, Object> getMultiPrintDataForServiceReminder(Integer vehicleId, String serviceProgramIds,
			Integer companyId);
	
	public ValueObject getServiceReminderForDueAndOverDue(ValueObject object) throws Exception;
		
	public ValueObject checkServiceReminderForJobTypeAndSubType(ValueObject object) throws Exception;
	
	public Long countTodaysDueServiceReminder(Date toDate, Integer companyId, Long id) throws Exception;

	public Page<ServiceReminder> geServiceReminderTodaysOverDuePageGByVid(Timestamp toDate, Integer pageNumber, CustomUserDetails userDetails) throws Exception;
	
	public ValueObject getServiceReminderHistory(ValueObject object) throws Exception;
	
	public ValueObject getTodaysOverDueServiceList(ValueObject valueObject) throws Exception;
	
	public ValueObject getUpcomingOverDueServiceList(ValueObject valueObject) throws Exception;
	
	public List<ServiceReminderDto> TodaysOverDueServiceRemnder(Date toDate, CustomUserDetails userDetails, Integer pageNumber) throws Exception;
		
	public Page<ServiceReminder> geServiceReminderTodaysOverDuePage(Timestamp toDate, Integer pageNumber, CustomUserDetails userDetails) throws Exception;
	
	public List<ServiceReminderDto> TodaysOverDueServiceRemnderGByVid(Date toDate, CustomUserDetails userDetails, Integer pageNumber) throws Exception;
	
	public Page<ServiceReminder> geServiceReminderUpcomingOverDuePage(Timestamp fromDate, Timestamp toDate, Integer pageNumber, CustomUserDetails userDetails) throws Exception;
		
	public Page<ServiceReminder> geServiceReminderUpcomingOverDuePageGByVid(Timestamp fromDate, Timestamp toDate, Integer pageNumber, CustomUserDetails userDetails) throws Exception;
		
	public List<ServiceReminderDto> UpcomingOverDueServiceRemnderGByVid(Date fromDate, Date toDate, CustomUserDetails userDetails, Integer pageNumber) throws Exception;

}
