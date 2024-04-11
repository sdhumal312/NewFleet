class URI {
  static const DEVELOPEMENT_URI = "https://fleetop.in/restservices";
  static const LIVE_URI = "https://fleetop.in/restservices";
  static const SRS_LIVE_URI = "https://srstravels.xyz/restservices";
  static const VERSION_CHECK = "/mobileVersionCheck";
  static const VALIDATE_USER_URI = "/mobileUserValidate";
  static const SEND_OTP_URI = "/mobileRegistrationOTP";
  static const MOBILE_APP_USER_REGISTRATION = "/mobileAppUserRegistration";
  static const GET_VEHICLE_DETAILS = "/mobileAppGetVehicleList";
  static const GET_ODO_METER_DETAILS = "/mobileAppFuelVehicleOdoMerete";
  static const SAVE_FUEL_ENTRY_DETAILS = "/mobileAppSaveFuelEntryDetails";
  static const INITIALIZE_FUEL_ENTRY_DATA = "/mobileAppInitializeFuelEntry";
  static const TRIP_LIST_BY_VEHICLE_ID = "/mobileAppRecentTripListByVehicleId";
  static const FUEL_VENDOR_LIST = "/mobileAppGetFuelVendorList";
  static const FUEL_TYPE_LIST = "/getFuelTypeNameWiseList";
  static const DRIVER_NAME_LIST = "/mobileDriverNameWiseList";
  static const CLEANER_NAME_LIST = "/mobileCleanerNameWiseList";
  static const TRIP_ROUTE_NAME_LIST = "/mobileTripRouteNameWiseList";
  static const GET_FUEL_DETAILS = "/mobileAppGetFuelDetails";
  static const VEHICLE_GPS_DATA_AT_TIME = "/getVehicleGPSDataAtTimeOnMob";
  static const TRIP_SHEET_DATA = "/getTripSheetCountDataOnMob";
  static const FUEL_COUNT_DATA = "/getFuelCountDataOnMob";
  static const WORK_ORDER_DATA = "/getWorkOrderCountDataOnMob";
  static const SERVICE_REM_DATA = "/getServiceReminderCountDataOnMob";
  static const RENEWAL_REM_DATA = "/getRRCountDataOnMob";
  static const ISSUE_COUNT_DATA = "/getIssueCountDataOnMob";
  static const SERVICE_ENTRY_DATA = "/getServiceEntryCountDataOnMob";
  static const TRIP_SHEET_DATA_COUNT = "/getTripSheetDataCountOnMob";
  static const TRIP_SHEET_TABLE_DATA = "/getTripSheetTableDataOnMob";
  static const FUEL_ENTRY_DATA_COUNT = "/getFuelDataCountOnMob";
  static const FUEL_ENTRY_TABLE_DATA = "/getFuelTableDataOnMob";
  static const WORK_ORDER_DATA_COUNT = "/getWorkOrderDataCountOnMob";
  static const WORK_ORDER_TABLE_DATA = "/getWorkOrderTableDataOnMob";
  static const WORK_ORDER_LOCATION_DATA =
      "/getLocationWiseWorkOrderDataCountOnMob";
  static const SERVICE_REMINDER_DATA_COUNT =
      "/getServiceReminderDataCountOnMob";
  static const SERVICE_REMINDER_TABLE_DATA =
      "/getServiceReminderTableDataOnMob";
  static const RENEWAL_REMINDER_DATA_COUNT =
      "/getRenewalReminderDataCountOnMob";
  static const RENEWAL_REMINDER_TABLE_DATA =
      "/getRenewalReminderTableDataOnMob";
  static const ISSUES_DATA_COUNT = "/getIssueDataCountOnMob";
  static const ISSUES_TABLE_DATA = "/getIssueTableDataOnMob";
  static const SERVICE_ENTRY_DATA_COUNT = "/getServiceEntryDataCountOnMob";
  static const SERVICE_ENTRY_TABLE_DATA = "/getServiceEntryTableDataOnMob";
  static const CHECK_VEHICLE_STATUS_ON_TRIPSHEET =
      "/vehicleStatusCheckWhileCreatingTripSheet";
  static const CREATE_TRIPSHEET = "/mobileCreateTripsheet";
  static const DISPATCH_OR_SAVE_TRIPSHEET = "/mobileSaveOrDispatchTripsheet";
  static const LOAD_TYPE_LIST = "/mobileLoadTypeNameWiseList";
  static const SHOW_TRIPSHEET = "/showTripSheetDetails";
  static const ADD_ADVANCE_TRIPSHEET = "/addTripSheetAdvanceDetails";
  static const SAVE_ADVANCE_TRIPSHEET = "/saveTripSheetAdvanceDetails";
  static const REMOVE_ADVANCE_TRIPSHEET = "/removeTripSheetAdvanceDetails";
  static const ADD_EXPENSE_TRIPSHEET = "/addTripSheetExpenseDetails";
  static const SAVE_EXPENSE_TRIPSHEET = "/saveTripSheetExpenseDetails";
  static const REMOVE_EXPENSE_TRIPSHEET = "/removeTripSheetExpenseDetails";
  static const ADD_INCOME_TRIPSHEET = "/addTripSheetIncomeDetails";
  static const SAVE_INCOME_TRIPSHEET = "/saveTripSheetIncomeDetails";
  static const REMOVE_INCOME_TRIPSHEET = "/removeTripSheetIncomeDetails";
  static const ADD_DRIVER_HALT_TRIPSHEET = "/addTripSheetDriverHaltDetails";
  static const SAVE_DRIVER_HALT_TRIPSHEET = "/saveTripSheetDriverHaltDetails";
  static const REMOVE_DRIVER_HALT_TRIPSHEET =
      "/removeTripSheetDriverHaltDetails";
  static const ADD_FUEL_TRIPSHEET = "/addTripSheetFuelDetails";
  static const REMOVE_FUEL_TRIPSHEET = "/removeTripSheetFuelDetails";
  static const EDIT_TRIPSHEET = "/editTripSheetDetails";
  static const UPDATE_TRIPSHEET = "/updateTripSheetDetails";
  static const ADD_CLOSE_TRIPSHEET = "/addTripSheetCloseDetails";
  static const SAVE_CLOSE_TRIPSHEET = "/saveTripSheetCloseDetails";
  static const INCOME_LIST_TRIPSHEET = "/mobileTripIncomeList";
  static const EXPENSE_LIST_TRIPSHEET = "/mobileTripExpenseList";
  static const SAVE_COMMENT_TRIPSHEET = "/saveTripComment";
  static const ADD_POD_TRIPSHEET = "/addTripSheetPODDetails";
  static const SAVE_POD_TRIPSHEET = "/saveTripSheetPODDetails";
  static const DELETE_TRIPSHEET = "/deleteTripSheetDetails";
  static const ADD_ACCOUNT_CLOSE_TRIPSHEET = "/addcloseAccountTripSheet";
  static const SAVE_ACCOUNT_CLOSE_TRIPSHEET = "/savecloseAccountTripSheet";
  static const SHOW_ACCOUNT_CLOSE_TRIPSHEET = "/showCloseAccountTripSheet";
  static const SEARCH_TRIPSHEET = "/searchTripsheet";
  static const GET_PARTY_NAME_DATA = "/mobilePartyNameWiseList";
  static const GET_PICK_DROP_NAME_DATA =
      "/mobilePickOrDropLocationNameWiseList";
  static const SAVE_PICK_OR_DROP_DATA = "/savePickOrDropDispatchMobileData";
  static const SHOW_PICK_OR_DROP_DATA = "/getPickOrDropDispatchMobileData";
  static const EDIT_PICK_OR_DROP_DATA =
      "/editTripsheetPickDropDispatchDetailsFromMobile";
  static const UPDATE_PICK_OR_DROP_DATA =
      "/updateTripsheetPickDropDispatchDetailsFromMobile";
  static const SEARCH_PICK_OR_DROP_DATA =
      "/searchPickOrDropTripsheetFromMobile";
  static const GET_RR_CONFIG_DATA =
      "/getRenewalReminderInitialConfigDataForMobile";
  static const GET_RR_SUBTYPE_LIST = "/getRenewalReminderSubTypeList";
  static const OTHER_VENDOR_LIST = "/getOtherVendorSearchListForMobile";
  static const SAVE_RENEWAL_REMINDER = "/saveRenewalReminderFromMobile";
  static const SHOW_RR_DATA = "/getRenewalReminderShowDataForMobile";
  static const SEARCH_RR_BY_NUMBER = "/searchRenewalByNumber";
  static const GET_DRIVER_RENEWAL_LIST = "/getDriverRenewalTypeList";
  static const SAVE_DRIVER_RENEWAL = "/saveDriverRenewalFromMobile";
  static const GET_DRIVER_RENEWAL_DATA = "/showDriverRenewalFromMobile";
  static const SEARCH_DRIVER_BY_EMPNUMBER =
      "/searchDriverRenewalByDriverNumber";
  static const SEARCH_RENEWAL_BY_VEHICLE = "/searchRenewalByVehicle";
  static const SEARCH_TRIPSHEET_BY_VEHICLE = "/searchTripSheetByVehicle";
  static const SEARCH_PICK_OR_DROP_BY_VEHICLE =
      "/searchPickAndDropTripByVehicle";
  static const UPDATE_VEHICLE_KMPL_DETAILS =
      "/updateVehicleKMPLDetailsFromMobile";
  static const GET_ACTIVE_TS_AND_BACKDATE =
      "/getActiveTripSheetAndBackDateData";
  static const SEARCH_FUEL_ENTRY_BY_VEHICLE = "/searchFuelEntriesByVehicle";
  static const TALLY_COMPANY_LIST = "/mobileTallyCompanyWiseList";
  static const GET_LAST_TSDETAILS = "/getLastTripSheetDetailsToCopy";
  static const GET_LAST_NEXT_TSDETAILS =
      "/getLastNextTripSheetDetailsFromMobile";
  static const GET_MANDATORY_RENEWAL_LIST = "/getMandatoryListOfRenewal";
  static const GET_LAST_NEXT_TSDETAILS_EDIT =
      "/getLastNextTripSheetDetailsForEditMobile";
  static const CREATE_ISSUE = "/createIssueFromMobile";
  static const GET_BRANCH_NAME_LIST = "/getBranchNameList";
  static const GET_DEPARTMENT_NAME_LIST = "/getDpartmentNameList";
  static const GET_USER_NAME_LIST = "/getUserNameList";
  static const SAVE_ISSUES_DETAILS = "/saveIssueFromMobile";
  static const SHOW_ISSUES_DETAILS = "/showIssueFromMobile";
  static const EDIT_ISSUES_DETAILS = "/editIssueFromMobile";
  static const UPDATE_ISSUES_DETAILS = "/updateIssueFromMobile";
  static const SEARCH_ISSUES_DETAILS = "/searchIssueFromMobile";
  static const RESOLVE_ISSUES_DETAILS = "/resolveIssueFromMobile";
  static const REJECT_ISSUES_DETAILS = "/rejectIssueFromMobile";
  static const REOPEN_ISSUES_DETAILS = "/reOpenIssueFromMobile";
  static const CLOSE_ISSUES_DETAILS = "/closeIssueFromMobile";
  static const SAVE_ISSUE_IMAGE = "/saveIssueImageFromMobile";
  static const SAVE_TRIPSHEET_VOUCHER_DATE = "/saveVoucherDateFromMobile";
  static const SAVE_TRIPSHEET_TALLY_COMPANY =
      "/saveTripsheetTallyCompanyFromMobile";
  static const GET_FUEL_STOCK_DETAILS = "/getFuelStockDetailsFromMob";
  static const GET_REN_DET_BY_VEHI_RENE_TYPE =
      "/getRenewalReminderDetailsByVehicleNoAndReneType";
  static const SEARCH_FUEL_ENTRY_BY_VEHICLE_OR_DATE_RANGE =
      "/searchFuelEntriesByVehicleNumberAndDateRangeFromMob";
  static const SEARCH_WORK_ORDER = "/searchWoByDifferentParameters";
  static const GET_AALL_WORK_ORDER_DETAILS = "/getWorkOrderList";
  static const GET_WORK_ORDER_INITIAL_DETAILS = "/getWorkOrderInitialDetails";
  static const GET_WORK_ORDER_DRIVERLIST = "/getDriverSearchListWorkOrder";
  static const GET_WORK_ORDER_USER_DETAILS = "/getUserEmailId_Subscrible";
  static const SAVE_WORK_ORDER = "/saveWorkOrderDetailsFromMob";
  static const GET_ODO_DETAILS_FOR_WO = "/getOdometerDetailsForWO";
  static const GET_JOB_TYPE_WO = "/getJobTypeWorkOrder";
  static const GET_SUB_JOB_TYPE_WO = "/getJobSubTypeWorkOrder";
  static const GET_JOB_DET_FROM_SUB_JOB_ID = "/getJobDetailsFromSubJobId";
  static const GET_PART_LOCATION_BY_MAIN_LOCATION_ID =
      "/searchPartLocationListByMainLocatinId";
  static const SHOW_WORK_ORDER_DETAILS_FOR_PROCESS =
      "/showWorkOrderDetailsFromMob";
  static const GET_WORK_ORDER_DETAILS_BY_VEHICLE_ID =
      "/vehicleWiseWorkorderServiceDetailsFromMob";
  static const GET_WORK_ORDER_ALL_DETAILS_ = "/VehicleWorkOrderDetailsFromMob";
  static const SAVE_NEW_TASK_DETAILS = "/saveWorkOderTasksFromMob";
  static const APPROVE_WO = "/approveWorkOrderDetailsFromMob";
  static const SAVE_PARTS = "/savePartForWorkOderTaskFromMob";
  static const SAVE_LABOUR = "/saveLabourForWorkOderTaskFromMob";
  static const MARK_COMPLETE = "/saveMarkAsCompleteFromMob";
  static const CHANGE_TO_HOLD = "/changeWorkorderStatusToHoldFromMob";
  static const CHANGE_TO_IN_PROG = "/changeWorkorderStatusToInProgressFromMob";
  static const CHANGE_TO_COMPLETED = "/changeWorkorderStatusToCompleteFromMob";
  static const UPLOAD_WO_DOC = "/uploadWorkOrderDocument";
  static const GET_PART_DETAILS = "/getInvoiceWisePartList";
  static const GET_TEC_DETAILS = "/getTechinicianWorkOrderFromMob";
  static const DELETE_PART_OF_WO_TASK = "/deletePartOfWorkOrdertaskFromMob";
  static const DELETE_LABOUR_OF_WO_TASK = "/deleteLabourOfWorkOrdertaskFromMob";
  static const DELETE_WO_TASK = "/deleteWorkOrdertaskFromMob";
  static const UPLOAD_WO_DCOUMENT = "/uploadWorkOrderDocumentFromMob";
  static const DELETE_WO_FROM_MOB = "/deleteWorkOrderDetailsFromMob";
  static const EDIT_WO_FROM_MOB = "/updateWorkOrderDetailsFromMob";
  static const GET_INITIAL_DETAILS_OF_WO = "/getworkOrderEditInitialDetails";
  static const UPDATE_TASK_REMARK = "/updateTaskRemarkByIdFromMob";
  static const UPDATE_USER_TOKEN = "/updateUserTokenForNotification";
  static const GET_MOB_NOTIFICATION_DETAILS = "/getUserNotificationDetails";
  static const GET_UNREAD_NOTI = "/getUnreadNotificationList";
  static const GET_READ_NOTI = "/getReadNotificationList";
  static const GET_SENT_NOTI = "/getSentNotificationList";
  static const MARK_AS_READ = "/markNotificationAsRead";
  static const GET_SERVICE_REMIDENDER = "/getVehicleServiceReminderList";
}
