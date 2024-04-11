package org.fleetopgroup.spring;


import org.fleetopgroup.persistence.dao.PrivilegeRepository;
import org.fleetopgroup.persistence.model.Privilege;
import org.fleetopgroup.persistence.serviceImpl.ILiveReleaseUpdateQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired	private PrivilegeRepository	privilegeRepository;
	@Autowired	private ILiveReleaseUpdateQueryService	liveReleaseUpdateQueryService;
	//@Autowired private IVehicleDailyInspectionService	vehicleDailyInspectionService;
	private boolean alreadySetup = false;
	

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		
		if (alreadySetup) {
			return;
		}
		final Privilege NUM_OF_BACK_DATE_FOR_ADMIN						= createPrivilegeIfNotFound("NUM_OF_BACK_DATE_FOR_ADMIN");
		final Privilege BUS_BOOKING_LOCATION						        = createPrivilegeIfNotFound("BUS_BOOKING_LOCATION");
		final Privilege APPROVE_ABOVE_FIFTY					        		= createPrivilegeIfNotFound("APPROVE_ABOVE_FIFTY");
		final Privilege APPROVE_BELOW_FIFTY					        		= createPrivilegeIfNotFound("APPROVE_BELOW_FIFTY");
		final Privilege PART_REQUISITION_STATUS_REPORT					    = createPrivilegeIfNotFound("PART_REQUISITION_STATUS_REPORT");
		final Privilege VIEW_PURCHASE_ORDER_STATUS_REPORT				    = createPrivilegeIfNotFound("VIEW_PURCHASE_ORDER_STATUS_REPORT");
		final Privilege CHANGE_STATUS				    					= createPrivilegeIfNotFound("CHANGE_STATUS");
		final Privilege DRIVER_LEDGER_ACCOUNT_REPORT					= createPrivilegeIfNotFound("DRIVER_LEDGER_ACCOUNT_REPORT");
		final Privilege JOB_CARD_SERVICE_REMINDER_PRINT						= createPrivilegeIfNotFound("JOB_CARD_SERVICE_REMINDER_PRINT");

		final Privilege  DELETE_PURCHASE_AFTER_APPROVAL 						= createPrivilegeIfNotFound("DELETE_PURCHASE_AFTER_APPROVAL");
		final Privilege VIEW_VEHICLE_WISE_WORK_ORDER_REPORT 				= createPrivilegeIfNotFound("VIEW_VEHICLE_WISE_WORK_ORDER_REPORT");
		final Privilege ADD_ROUTE_WISE_TRIPSHEET_WEIGHT						= createPrivilegeIfNotFound("ADD_ROUTE_WISE_TRIPSHEET_WEIGHT");
		final Privilege DRIVERS_LEDGER_REPORT								= createPrivilegeIfNotFound("DRIVERS_LEDGER_REPORT");
		final Privilege BATTERY_WISE_HISTORY_REPORT								= createPrivilegeIfNotFound("BATTERY_WISE_HISTORY_REPORT");
		final Privilege ADD_REASON_REPAIR_TYPES                             = createPrivilegeIfNotFound("ADD_REASON_REPAIR_TYPES");

		final Privilege VIEW_MAIN_CASH_BOOK                                 = createPrivilegeIfNotFound("VIEW_MAIN_CASH_BOOK");
		final Privilege VIEW_SSTS_CASH_BOOK                                 = createPrivilegeIfNotFound("VIEW_SSTS_CASH_BOOK");
		final Privilege VIEW_JBL_CASH_BOOK                                  = createPrivilegeIfNotFound("VIEW_JBL_CASH_BOOK");
		final Privilege VIEW_COMBINED_CASH_BOOK								= createPrivilegeIfNotFound("VIEW_COMBINED_CASH_BOOK");

		final Privilege B_INCOME                                            = createPrivilegeIfNotFound("B_INCOME");
		final Privilege E_INCOME                                            = createPrivilegeIfNotFound("E_INCOME");
		final Privilege VIEW_B_E_INCOME_TRIPBALANCE                         = createPrivilegeIfNotFound("VIEW_B_E_INCOME_TRIPBALANCE");
		final Privilege DRIVER_SALARY_REPORT                                = createPrivilegeIfNotFound("DRIVER_SALARY_REPORT");
		
		liveReleaseUpdateQueryService.processUpdateLiveQuery();
//			try {
//				vehicleDailyInspectionService.getDailyInspectionList();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

		alreadySetup = true;
	}

	@Transactional
	private final Privilege createPrivilegeIfNotFound(final String name) {
		Privilege privilege = privilegeRepository.findByName(name);
		if (privilege == null) {
			privilege = new Privilege(name);
			privilegeRepository.save(privilege);
		}
		return privilege;
	}
	
	
}
