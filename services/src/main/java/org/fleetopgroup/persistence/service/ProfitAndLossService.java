package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.DealerServiceEntriesDto;
import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.GroupWiseVehicleProfitAndLossReportDto;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.TollExpensesDetailsDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.UreaEntriesDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleEmiDetailDto;
import org.fleetopgroup.persistence.dto.VehicleEmiPaymentDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionCompletionDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossReportDto;
import org.fleetopgroup.persistence.dto.VehicleTyreLayoutPositionDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.model.InventoryTyreHistory;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleEmiDetails;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.report.dao.ProfitAndLossDao;
import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDealerServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.ITollExpensesDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUreaEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleEmiDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionCompletionDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreLayoutService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProfitAndLossService implements IProfitAndLossService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired private ProfitAndLossDao					profitAndLossDao;
	@Autowired private ICompanyConfigurationService		companyConfigurationService;
	@Autowired private IVehicleProfitAndLossService		vehicleProfitAndLossService;
	@Autowired private IRenewalReminderService			renewalReminderService;
	@Autowired private IVehicleTyreLayoutService		vehicleTyreLayoutService;
	@Autowired private IBatteryService					batteryService;
	@Autowired private IVehicleEmiDetailsService		vehicleEmiDetailsService;
	@Autowired private ITripSheetService				tripSheetService;
	@Autowired private IVehicleService					vehicleService;
	@Autowired private ITripDailySheetService			tripDailySheetService;
	@Autowired private IVehicleGroupService				vehicleGroupService;
	@Autowired private IFuelService						fuelService;
	@Autowired private IServiceEntriesService			serviceEntriesService;
	@Autowired private IWorkOrdersService				workOrdersService;
	@Autowired private ITollExpensesDetailsService		tollExpensesDetailsService;
	@Autowired private IUreaEntriesService				ureaEntriesService;
	@Autowired private IUserProfileService				userProfileService;
	@Autowired private IDealerServiceEntriesService			dealerServiceEntriesService;
	@Autowired private IVehicleInspectionCompletionDetailsService inspectionCompletionDetailsService;
	@Autowired IInventoryTyreService  inventoryTyreService;
	
	SimpleDateFormat 						AttendanceMonth 	= new SimpleDateFormat("MM");
	SimpleDateFormat 						form 				= new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
	SimpleDateFormat 						dateFormat 			= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DecimalFormat							toFixedTwo			= new DecimalFormat("#.##");
	
	RenewalReminderBL RRBL = new RenewalReminderBL();
	
	@Override
	public ValueObject getVehicleWiseUsageDetails(ValueObject valueObject) throws Exception {
		String								fromDate						= null;
		String								toDate							= null;
		ValueObject							dateRangeObj					= null;
		CustomUserDetails					userDetails						= null;
		List<TripSheetDto>					tripSheetDtoList				= null;
		HashMap<Long,TripSheetDto>			tripSheetDtoMap					= null;
		TripSheetDto						tripSheetDto					= null;
		List<FuelDto>						fueltDtoList					= null;
		List<TollExpensesDetailsDto>		tolltDtoList					= null;
		int									tripFlavor						= 0;
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRangeObj			= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
			fromDate				= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate					= dateRangeObj.getString(DateTimeUtility.TO_DATE);
			tripFlavor				= companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			fromDate = DateTimeUtility.getSqlStrDateFromStrDate(fromDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			toDate   = DateTimeUtility.getSqlStrDateFromStrDate(toDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			
			if (tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				tripSheetDtoList	= profitAndLossDao.getVehicleUsageTripSheet(valueObject.getInt("vid"), fromDate, toDate, userDetails);
			}else {
				tripSheetDtoList	= profitAndLossDao.getVehicleUsageTripDailySheet(valueObject.getInt("vid"), fromDate, toDate, userDetails);
			}
			fueltDtoList			= fuelService.getTripsheetfuelAmount(valueObject.getInt("vid"), fromDate, toDate, userDetails,tripFlavor);
			tolltDtoList			= tollExpensesDetailsService.getTripsheetTollAmount(valueObject.getInt("vid"), fromDate, toDate, userDetails,tripFlavor);
			
			tripSheetDtoMap			= new HashMap<>();
			
			if(tripSheetDtoList != null) {
				for(TripSheetDto dto : tripSheetDtoList) {
					if(tripSheetDtoMap.containsKey(dto.getTripSheetID())) {
						tripSheetDto	= tripSheetDtoMap.get(dto.getTripSheetID());
						tripSheetDto.setExpenseAmount(Double.parseDouble(toFixedTwo.format((tripSheetDto.getExpenseAmount()+dto.getExpenseAmount()))));
					} else {
						tripSheetDtoMap.put(dto.getTripSheetID(), dto);
					}
				}
			}
			
			if(fueltDtoList != null) {
				for(FuelDto dto : fueltDtoList ) {
					if(tripSheetDtoMap.containsKey(dto.getFuel_TripsheetID())) {
						tripSheetDtoMap.get(dto.getFuel_TripsheetID()).setFuelExpenseAmount(Double.parseDouble(toFixedTwo.format((tripSheetDtoMap.get(dto.getFuel_TripsheetID()).getFuelExpenseAmount() + dto.getFuel_amount()))));
					} 
				}
			}
			
			if(tolltDtoList != null) {
				for(TollExpensesDetailsDto dto : tolltDtoList ) {
					if(tripSheetDtoMap.containsKey(dto.getTripSheetId())) {
						tripSheetDtoMap.get(dto.getTripSheetId()).setTollExpenseAmount(Double.parseDouble(toFixedTwo.format(tripSheetDtoMap.get(dto.getTripSheetId()).getTollExpenseAmount() + dto.getTransactionAmount())));
					} 
				}
			}
			
			valueObject.put("tripSheetDtoList", tripSheetDtoMap.values());
			valueObject.put("tripFlavor", tripFlavor);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			fromDate						= null;
			toDate							= null;
			dateRangeObj					= null;
			userDetails						= null;
			tripSheetDtoList				= null;
		}
	}
	
	@Override
	public ValueObject getGroupWiseUsageDetails(ValueObject valueObject) throws Exception {
		String								fromDate						= null;
		String								toDate							= null;
		ValueObject							dateRangeObj					= null;
		CustomUserDetails					userDetails						= null;
		List<TripSheetDto>					tripSheetDtoList				= null;
		LinkedHashMap<Long,TripSheetDto>	tripSheetDtoMap					= null;
		TripSheetDto						tripSheetDto					= null;
		List<FuelDto>						fueltDtoList					= null;
		List<TollExpensesDetailsDto>		tolltDtoList					= null;
		int									tripFlavor						= 0;
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRangeObj			= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
			fromDate				= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate					= dateRangeObj.getString(DateTimeUtility.TO_DATE);
			tripFlavor				= companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			fromDate = DateTimeUtility.getSqlStrDateFromStrDate(fromDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			toDate   = DateTimeUtility.getSqlStrDateFromStrDate(toDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			
			if (tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				tripSheetDtoList	= profitAndLossDao.getGroupUsageTripSheet(valueObject.getLong("vehicleGroupId"), fromDate, toDate, userDetails);
			}else {
				tripSheetDtoList	= profitAndLossDao.getGroupUsageTripDailySheet(valueObject.getLong("vehicleGroupId"), fromDate, toDate, userDetails);
			}
			fueltDtoList			= fuelService.getTripsheetfuelAmountByGroupId(valueObject.getLong("vehicleGroupId"), fromDate, toDate, userDetails,tripFlavor);
			tolltDtoList			= tollExpensesDetailsService.getTripsheetTollAmountByGid(valueObject.getLong("vehicleGroupId"), fromDate, toDate, userDetails,tripFlavor);
			
			tripSheetDtoMap			= new LinkedHashMap<>();
			
			if(tripSheetDtoList != null) {
				for(TripSheetDto dto : tripSheetDtoList) {
					if(tripSheetDtoMap.containsKey(dto.getTripSheetID())) {
						tripSheetDto	= tripSheetDtoMap.get(dto.getTripSheetID());
						tripSheetDto.setExpenseAmount(tripSheetDto.getExpenseAmount()+dto.getExpenseAmount());
					} else {
						tripSheetDtoMap.put(dto.getTripSheetID(), dto);
					}
				}
			}
			
			if(fueltDtoList != null) {
				for(FuelDto dto : fueltDtoList ) {
					if(tripSheetDtoMap.containsKey(dto.getFuel_TripsheetID())) {
						tripSheetDtoMap.get(dto.getFuel_TripsheetID()).setFuelExpenseAmount(tripSheetDtoMap.get(dto.getFuel_TripsheetID()).getFuelExpenseAmount() + dto.getFuel_amount());
					} 
				}
			}
			
			if(tolltDtoList != null) {
				for(TollExpensesDetailsDto dto : tolltDtoList ) {
					if(tripSheetDtoMap.containsKey(dto.getTripSheetId())) {
						tripSheetDtoMap.get(dto.getTripSheetId()).setTollExpenseAmount(tripSheetDtoMap.get(dto.getTripSheetId()).getTollExpenseAmount() + dto.getTransactionAmount());
					} 
				}
			}
			
			valueObject.put("tripSheetDtoList", tripSheetDtoMap.values());
			valueObject.put("tripFlavor", tripFlavor);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			fromDate						= null;
			toDate							= null;
			dateRangeObj					= null;
			userDetails						= null;
			tripSheetDtoList				= null;
		}
	}
	
	@Override
	public ValueObject getRouteWiseUsageDetails(ValueObject valueObject) throws Exception {
		String								fromDate						= null;
		String								toDate							= null;
		ValueObject							dateRangeObj					= null;
		CustomUserDetails					userDetails						= null;
		List<TripSheetDto>					tripSheetDtoList				= null;
		int									tripFlavor						= 0;
		List<FuelDto>				        fueltDtoList					= null;
		List<TollExpensesDetailsDto>        tolltDtoList					= null;
		HashMap<Long,TripSheetDto>			tripSheetDtoMap					= null;
		TripSheetDto						tripSheetDto					= null;
		int									vid								= 0;
		String								vehicleTrip						= "";
		String								vehicleTipDaily					= "";
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRangeObj			= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
			fromDate				= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate					= dateRangeObj.getString(DateTimeUtility.TO_DATE);
			
			fromDate = DateTimeUtility.getSqlStrDateFromStrDate(fromDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			toDate   = DateTimeUtility.getSqlStrDateFromStrDate(toDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			
			vid						= valueObject.getInt("vid",0);
			if(vid > 0) {
				vehicleTrip 		= " AND T.vid = "+vid+" ";
			}
			if(vid > 0) {
				vehicleTipDaily 	= " AND T.VEHICLEID = "+vid+" ";
			}
			
			
			tripFlavor				= companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			if (tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				tripSheetDtoList	= profitAndLossDao.getRouteUsageTripSheet(valueObject.getInt("routeId"),vehicleTrip, fromDate, toDate, userDetails);
			}else {
				tripSheetDtoList	= profitAndLossDao.getRouteUsageTripDailySheet(valueObject.getInt("routeId"),vehicleTipDaily, fromDate, toDate, userDetails);
			}
			fueltDtoList			= fuelService.getRouteWiseTripsheetfuelAmount(valueObject.getInt("routeId"), fromDate, toDate, userDetails,tripFlavor);
			tolltDtoList			= tollExpensesDetailsService.getRouteWiseTripsheetTollAmount(fromDate, toDate, userDetails,tripFlavor);
			
			tripSheetDtoMap			= new HashMap<>();
			if(tripSheetDtoList != null) {
				for(TripSheetDto dto : tripSheetDtoList) {
					if(tripSheetDtoMap.containsKey(dto.getTripSheetID())) {
						tripSheetDto	= tripSheetDtoMap.get(dto.getTripSheetID());
						tripSheetDto.setExpenseAmount(Double.parseDouble(toFixedTwo.format(tripSheetDto.getExpenseAmount()+dto.getExpenseAmount())));
						
					} else {
						tripSheetDtoMap.put(dto.getTripSheetID(), dto);
					}
				}
			}
			
			if(fueltDtoList != null) {
				for(FuelDto dto : fueltDtoList ) {
					if(tripSheetDtoMap.containsKey(dto.getFuel_TripsheetID())) {
						tripSheetDtoMap.get(dto.getFuel_TripsheetID()).setFuelExpenseAmount(Double.parseDouble(toFixedTwo.format(tripSheetDtoMap.get(dto.getFuel_TripsheetID()).getFuelExpenseAmount() + dto.getFuel_amount())));
					} 
				}
			}
			
			if(tolltDtoList != null) {
				for(TollExpensesDetailsDto dto : tolltDtoList ) {
					if(tripSheetDtoMap.containsKey(dto.getTripSheetId())) {
						tripSheetDtoMap.get(dto.getTripSheetId()).setTollExpenseAmount(Double.parseDouble(toFixedTwo.format(tripSheetDtoMap.get(dto.getTripSheetId()).getTollExpenseAmount() + dto.getTransactionAmount())));
					} 
				}
			}
			
			valueObject.put("tripSheetDtoList", tripSheetDtoMap.values());
			valueObject.put("tripFlavor", tripFlavor);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject getVehicleWiseProfitAndLossReport(ValueObject valueObject) throws Exception {
		List<MonthWiseVehicleExpenseDto>				expenseDtoList				= null;
		CustomUserDetails								userDetails					= null;
		Timestamp										startDateOfMonth			= null;
		String											month						= null;
		List<RenewalReminderDto>						renewalList					= null;
		ValueObject										valueOutObject				= null;
		List<VehicleProfitAndLossReportDto> 			incomeList					= null;
		List<VehicleTyreLayoutPositionDto>				vehicleTyreList				= null;
		VehicleOdometerHistory							vehicleOdometerHistory		= null;
		List<BatteryDto>								batteryList					= null;
		List<TripSheetDto> 								tripSheetList				= null;
		Timestamp										fromDate					= null;
		Timestamp										toDate						= null;
		int												tripFlavor					= 0;
		String											vid							= "";
		ValueObject										outObject					= null;
		boolean											showDriverMonthlySalary		= false;	
		HashMap<String, Object>							configDriverSalary			= null;
		double											totalFuelLiters				= 0.0;
		List<VehicleEmiPaymentDetailsDto>				vehicleEMIList				= null;
		HashMap<String, Object> 						configuration	        	= null;
		boolean											vehicleMonthlyEMIPayment 	= false;
		boolean											showDriverMonthlyBhatta		= false;	
		
		try {
			valueOutObject	= new ValueObject();
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			configuration				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			vehicleMonthlyEMIPayment 	= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VEHICLE_MONTHLY_EMI_PAYMENT, false);
			
			
			month			= valueObject.getString("dateOfMonth", null);
			tripFlavor		= companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			startDateOfMonth		= DateTimeUtility.getTimeStamp(month, DateTimeUtility.DD_MM_YY);
			fromDate				= DateTimeUtility.getFirstDayOfMonth(startDateOfMonth); 
			toDate					= DateTimeUtility.getLastDayOfMonth(startDateOfMonth);
			
			vid							= valueObject.getString("vid");
			configDriverSalary			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG); 
			showDriverMonthlySalary		= (boolean) configDriverSalary.getOrDefault(VehicleConfigurationContant.SHOW_DRIVER_MONTHLY_SALARY, false); 
			showDriverMonthlyBhatta		= (boolean) configDriverSalary.getOrDefault(VehicleConfigurationContant.SHOW_DRIVER_MONTHLY_BHATTA, false); 
			VehicleDto vehicleDetails	= vehicleService.Get_Vehicle_Header_Details(valueObject.getInt("vid", 0));
			
			
			valueObject.put("userDetails", userDetails);
			valueObject.put("month", month);
			valueObject.put("tripFlavor", tripFlavor);
			valueObject.put("startDateOfMonth", startDateOfMonth);
			valueObject.put("fromDate", fromDate);
			valueObject.put("toDate", toDate);
			valueObject.put("vehicleTyreMountCostInPLReport", configDriverSalary.getOrDefault("vehicleTyreMountCostInPLReport", false));
			
			outObject	=	runThreadForVehicleProfitAndLossReport(valueObject);
			
			expenseDtoList			=	(List<MonthWiseVehicleExpenseDto>) outObject.get("expenseDtoList");
			
			List<FuelDto> fuelList	= fuelService.Vehicle_wise_Fuel_Mileage_Report(fromDate.toString(),toDate.toString(),vid,userDetails.getCompany_id());
			
			if(fuelList != null) {
			for(FuelDto dto : fuelList) {
				if( dto.getFuel_liters() > 0) {
				totalFuelLiters +=(Double)dto.getFuel_liters();
				}
			}
			}
			
			
			incomeList				= 	(List<VehicleProfitAndLossReportDto>) outObject.get("incomeList");
			renewalList				= 	(List<RenewalReminderDto>) outObject.get("renewalList");
			vehicleTyreList			=	(List<VehicleTyreLayoutPositionDto>) outObject.get("vehicleTyreList");
			batteryList				=	(List<BatteryDto>) outObject.get("batteryList");
			
			vehicleEMIList			= 	(List<VehicleEmiPaymentDetailsDto>) outObject.get("vehicleEMIList");
			tripSheetList		= 	(List<TripSheetDto>) outObject.get("tripSheetList");	
			valueOutObject.put("driverSalaryFromVehicle", vehicleService.Get_Vehicle_Header_Details(valueObject.getInt("vid", 0)));
			valueOutObject.put("expenseDtoList", expenseDtoList);
			valueOutObject.put("renewalList", renewalList);
			valueOutObject.put("startDateOfMonth", startDateOfMonth);
			valueOutObject.put("vehicleTyreList", vehicleTyreList);
			valueOutObject.put("vehicleOdometerHistory", vehicleOdometerHistory);
			valueOutObject.put("batteryList", batteryList);
			valueOutObject.put("vehicleEMIList", vehicleEMIList);
			valueOutObject.put("tripFlavor", tripFlavor);
			valueOutObject.put("fromDate", fromDate);
			valueOutObject.put("toDate", toDate);
			valueOutObject.put("showDriverMonthlySalary", showDriverMonthlySalary);
			valueOutObject.put("showDriverMonthlyBhatta", showDriverMonthlyBhatta);
			valueOutObject.put("vehicleMonthlyEMIPayment", vehicleMonthlyEMIPayment);
			valueOutObject.put("vehicleEMIList", vehicleEMIList);
			valueOutObject.put("totalRunningKM", tripSheetService.getVehicleRunKMByVid(valueObject.getInt("vid", 0), fromDate, toDate));	
			valueOutObject.put("vid", valueObject.getInt("vid", 0));
			
			valueOutObject.put("tyreMountList", outObject.get("tyreMountList"));
			valueOutObject.put("vehicleTyreMountCostInPLReport", configDriverSalary.getOrDefault("vehicleTyreMountCostInPLReport", false));
			valueObject.put("expenseDtoList", getFinalListOfExpenseForUI(valueOutObject));
			valueObject.put("incomeList", incomeList);
			if(vehicleDetails != null) {
				valueObject.put("vehicle", vehicleDetails);
			}
			valueObject.put("showDriverMonthlySalary", showDriverMonthlySalary);
			valueObject.put("showDriverMonthlyBhatta", showDriverMonthlyBhatta);

			valueObject.put("tripFlavor", tripFlavor);
			getTripSheetDetailsOfMonth(tripSheetList, valueObject);
			
			valueObject.put("totalFuelLiters", Utility.round(totalFuelLiters, 2));
			
			System.err.println("valueObject -- "+ valueObject);

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
		expenseDtoList			= null;
		userDetails				= null;
		startDateOfMonth		= null;
		month					= null;
		renewalList				= null;
		valueOutObject			= null;
		incomeList				= null;
		vehicleTyreList			= null;
		vehicleOdometerHistory	= null;
		batteryList				= null;
		tripSheetList			= null;}
	}
	
	private void	getTripSheetDetailsOfMonth(List<TripSheetDto> 	tripSheetList, ValueObject	valueObject)throws Exception {
			List<Date> 								dateList				= null;
			long									noOftripDays			= 0;
			int										currentMonth			= 0;
			int										daysInMonth				= 0;
			int										totalRunKM				= 0;
			Double									totalIncome				= 0.0;
			int										tripFlavor				= 0;
		try {
				dateList		= new ArrayList<>();
				tripFlavor		= valueObject.getInt("tripFlavor", 0);
				currentMonth	= Integer.parseInt(valueObject.getString("dateOfMonth").substring(3, 5));
				Calendar cal = Calendar.getInstance();
				cal.setTime(DateTimeUtility.getTimeStamp(valueObject.getString("startDateOfMonth"), "yyyy-MM-dd HH:mm:ss"));
				
				daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				if(tripSheetList != null && !tripSheetList.isEmpty()) {
					for(TripSheetDto tripSheet : tripSheetList) {
						if(tripFlavor != TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
							dateList 	= DateTimeUtility.getDaysBetweenDates(tripSheet.getTripOpenDateOn(), tripSheet.getClosetripDateOn(), dateList);
						}
						if(tripSheet.getTripUsageKM() != null)
							totalRunKM 	+= tripSheet.getTripUsageKM();
						if(tripSheet.getTripTotalincome() != null)
							totalIncome += tripSheet.getTripTotalincome();
						
					}
					valueObject.put("noOftrip", tripSheetList.size());
					
					if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
						noOftripDays	= tripSheetList.size();
					}else {
						
						for(Date date : dateList) {
							if(Integer.parseInt(AttendanceMonth.format(date)) == currentMonth) {
								noOftripDays++;
							}
						}
					}
				}
				
				valueObject.put("totalRunKM", totalRunKM);
				valueObject.put("noOftripDays", noOftripDays);
				valueObject.put("noOfDaysIdeal", daysInMonth - noOftripDays);
				valueObject.put("daysInMonth", daysInMonth);
				if(totalRunKM != 0) {
					valueObject.put("EPK", totalIncome / totalRunKM);
				}else {
					valueObject.put("EPK", 0);
				}
					
				valueObject.put("totalIncome", totalIncome);
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
	private void	getTripSheetDetailsDateWise(List<TripSheetDto> 	tripSheetList, ValueObject	valueObject)throws Exception {
		List<Date> 								dateList				= null;
		long									noOftripDays			= 0;
		long									daysInMonth				= 0;
		int										totalRunKM				= 0;
		Double									totalIncome				= 0.0;
		int										tripFlavor				= 0;
		String									fromDate				= null;
		String									toDate					= null;
		int										currentMonth			= 0;
	try {
			dateList		= new ArrayList<>();
			tripFlavor		= valueObject.getInt("tripFlavor", 0);
			fromDate		= valueObject.getString("fromDate");
			toDate			= valueObject.getString("toDate");
			currentMonth	= Integer.parseInt(toDate.substring(5, 7));
			
			daysInMonth = DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			if(tripSheetList != null && !tripSheetList.isEmpty()) {
				for(TripSheetDto tripSheet : tripSheetList) {
					
					dateList 	= DateTimeUtility.getDaysBetweenDates(tripSheet.getTripOpenDateOn(), tripSheet.getClosetripDateOn(), dateList);
					
					if(tripSheet.getTripUsageKM() != null)
						totalRunKM 	+= tripSheet.getTripUsageKM();
					if(tripSheet.getTripTotalincome() != null)
						totalIncome += tripSheet.getTripTotalincome();
					
				}
				valueObject.put("noOftrip", tripSheetList.size());
				
				if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
					noOftripDays	= tripSheetList.size();
				}else {
					for(Date date : dateList) {
						if(valueObject.getBoolean("fromVehicleProfitLoss", false)) {
							if(Integer.parseInt(AttendanceMonth.format(date)) == currentMonth) {
								noOftripDays++;
							}
						}else {
							noOftripDays = dateList.size();
						}
						
					}
				}
				
			}
			
			valueObject.put("totalRunKM", totalRunKM);
			valueObject.put("noOftripDays", noOftripDays);
			valueObject.put("noOfDaysIdeal", daysInMonth - noOftripDays);
			valueObject.put("daysInMonth", daysInMonth);
			if(totalRunKM != 0) {
				valueObject.put("EPK", totalIncome / totalRunKM);
			}else {
				valueObject.put("EPK", 0);
			}
				
			valueObject.put("totalIncome", totalIncome);
		
	} catch (Exception e) {
		throw e;
	}
	
}
	
	private ValueObject	getTripSheetDetailsOfMonthByVid(List<TripSheetDto> 	tripSheetList, ValueObject	valueObject)throws Exception {
		List<Date> 								dateList				= null;
		long									noOftripDays			= 0;
		int										currentMonth			= 0;
		int										daysInMonth				= 0;
		int										totalRunKM				= 0;
		Double									totalIncome				= 0.0;
		int										tripFlavor				= 0;
	try {
			dateList		= new ArrayList<>();
			tripFlavor		= valueObject.getInt("tripFlavor", 0);
			currentMonth	= Integer.parseInt(valueObject.getString("dateOfMonth").substring(3, 5));
		
			Calendar cal = Calendar.getInstance();
			cal.setTime(DateTimeUtility.getTimeStamp(valueObject.getString("dateOfMonth")));
			
			daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
			if(tripSheetList != null && !tripSheetList.isEmpty()) {
				for(TripSheetDto tripSheet : tripSheetList) {
					if(tripFlavor != TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
						dateList 	= DateTimeUtility.getDaysBetweenDates(tripSheet.getTripOpenDateOn(), tripSheet.getClosetripDateOn(), dateList);
					}
					if(tripSheet.getTripUsageKM() != null)
						totalRunKM 	+= tripSheet.getTripUsageKM();
					if(tripSheet.getTripTotalincome() != null)
						totalIncome += tripSheet.getTripTotalincome();
					
				}
				valueObject.put("noOftrip", tripSheetList.size());
				
				if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
					noOftripDays	= tripSheetList.size();
				}else {
					
					for(Date date : dateList) {
						if(Integer.parseInt(AttendanceMonth.format(date)) == currentMonth) {
							noOftripDays++;
						}
					}
				}
				
			}else {
				valueObject.put("noOftrip",0);
			}
			
			valueObject.put("totalRunKM", totalRunKM);
			valueObject.put("noOftripDays", noOftripDays);
			valueObject.put("noOfDaysIdeal", daysInMonth - noOftripDays);
			valueObject.put("daysInMonth", daysInMonth);
			if(totalRunKM != 0) {
				valueObject.put("EPK", totalIncome / totalRunKM);
			}else {
				valueObject.put("EPK", 0);
			}
				
			valueObject.put("totalIncome", totalIncome);
			
			return valueObject;
		
	} catch (Exception e) {
		throw e;
	}
	
}

	
	@SuppressWarnings({ "unchecked" })
	public List<DateWiseVehicleExpenseDto>  getFinalListOfExpenseForDateWiseUI(ValueObject	valueObject) throws Exception{
		
		List<DateWiseVehicleExpenseDto> 				finalList				= null;
		List<DateWiseVehicleExpenseDto>					expenseDtoList			= null;
		List<DateWiseVehicleExpenseDto>					tripExpenseList			= null;
		HashMap<String, DateWiseVehicleExpenseDto>		expenseHM				= null;	
		DateWiseVehicleExpenseDto						expenseDto				= null;
		List<RenewalReminderDto> 						renewalList				= null;
		List<VehicleTyreLayoutPositionDto>				vehicleTyreList			= null;
		List<BatteryDto>								batteryList				= null;
		Integer											totalRunningKM			= 0;
		String											fromDateStr				= null;
		String											toDateStr				= null;
		boolean 										showDriverMonthlySalary = false;
		VehicleDto										driverSalaryFromVehicle	= null;
		List<VehicleEmiPaymentDetailsDto> 				vehicleEMIList			= null;
		HashMap<Long, VehicleEmiPaymentDetailsDto>  	EMIHM					= null;
		List<InventoryTyreDto> 						tyreMountHistory			= null;

		try {
				//finalList	= new ArrayList<>();
				expenseHM	= new HashMap<>();
				EMIHM		= new HashMap<>();
				/**
				 * Adding Expense Head
				 */
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES));
			
			expenseDtoList				= (List<DateWiseVehicleExpenseDto>) valueObject.get("expenseDtoList");
			tripExpenseList				= (List<DateWiseVehicleExpenseDto>) valueObject.get("tripExpenseList");
			renewalList					= (List<RenewalReminderDto>) valueObject.get("renewalList");
			vehicleTyreList				= (List<VehicleTyreLayoutPositionDto>) valueObject.get("vehicleTyreList");
			batteryList					= (List<BatteryDto>) valueObject.get("batteryList");
			totalRunningKM				= valueObject.getInt("totalRunningKM", 0);
			fromDateStr					= valueObject.getString("fromDate");
			toDateStr						= valueObject.getString("toDate");
			showDriverMonthlySalary		= valueObject.getBoolean("showDriverMonthlySalary"); 
			driverSalaryFromVehicle		= (VehicleDto) valueObject.get("driverSalaryFromVehicle");
			vehicleEMIList				= (List<VehicleEmiPaymentDetailsDto>) valueObject.get("vehicleEMIList");
			tyreMountHistory				= (List<InventoryTyreDto>) valueObject.get("tyreMountList");
			
			long daysInMonth 			= DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getTimeStamp(fromDateStr, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDateStr, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			
			
			DateWiseVehicleExpenseDto	vehicleExpenseDto = null;
			if(renewalList != null && !renewalList.isEmpty()) {
				for(RenewalReminderDto  reminderDto : renewalList) {
					vehicleExpenseDto = new DateWiseVehicleExpenseDto();
					vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_RENEWAL);
					vehicleExpenseDto.setExpenseTypeStr(reminderDto.getRenewal_type());
					vehicleExpenseDto.setExpenseAmount((double) 0);
					vehicleExpenseDto.setExpenseId(Integer.parseInt(reminderDto.getRenewal_id()+""));
					vehicleExpenseDto.setTripExpenseName(reminderDto.getRenewal_type());
					vehicleExpenseDto.setExpenseTypeName(reminderDto.getRenewal_type());
					
					Double renewalAmountForMonth = (double) 0;
					
					Timestamp	countStartDate = null;
					Timestamp	countEndDate  = null;
					Timestamp	fromDate	= DateTimeUtility.getTimeStamp(fromDateStr, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					Timestamp	toDate		= DateTimeUtility.getTimeStamp(toDateStr, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					if(reminderDto.getRenewal_from_Date().after(fromDate)) {
						countStartDate = new Timestamp(reminderDto.getRenewal_from_Date().getTime());
					}else {
						countStartDate = fromDate;
					}
					
					if(reminderDto.getRenewal_To_Date().before(toDate)) {
						countEndDate = new Timestamp(reminderDto.getRenewal_To_Date().getTime());
					}else {
						countEndDate = toDate;
					}
					long dayDiff	 	 = DateTimeUtility.getDayDiffBetweenTwoDates(new Timestamp(reminderDto.getRenewal_from_Date().getTime()), new Timestamp(reminderDto.getRenewal_To_Date().getTime()));
					long renewalDays	 = DateTimeUtility.getDayDiffBetweenTwoDates(countStartDate, countEndDate);
					 
					
					if(reminderDto.getRenewal_Amount() != null && reminderDto.getRenewal_Amount() > 0) {
						renewalAmountForMonth = (reminderDto.getRenewal_Amount() / dayDiff) * renewalDays;
					}
					
					vehicleExpenseDto.setExpenseAmount(renewalAmountForMonth);
					
					vehicleExpenseDto.setVid(reminderDto.getVid());
					
					if(expenseDtoList == null) {
						expenseDtoList	= new ArrayList<>();
						
					}
					expenseDtoList.add(vehicleExpenseDto);
				}
				
				
			}
			
			if(!valueObject.getBoolean("vehicleTyreMountCostInPLReport",false)) {
			if(vehicleTyreList != null && !vehicleTyreList.isEmpty()) {
				vehicleExpenseDto = new DateWiseVehicleExpenseDto();
				vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE);
				vehicleExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME);
				vehicleExpenseDto.setExpenseAmount((double) 0);
				vehicleExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME);
				for(VehicleTyreLayoutPositionDto  positionDto : vehicleTyreList) {
					Double tyreAmountForMonth = (double) 0;
					Integer runKm	=	0 ;
					if(totalRunningKM != null) {
						runKm	= totalRunningKM;
					}
					
					tyreAmountForMonth	= positionDto.getCostPerKM() * runKm;
					vehicleExpenseDto.setExpenseAmount(tyreAmountForMonth + vehicleExpenseDto.getExpenseAmount());
					vehicleExpenseDto.setVid(positionDto.getVEHICLE_ID());
				}
				
				if(expenseDtoList == null) {
					expenseDtoList	= new ArrayList<>();
				}
				expenseDtoList.add(vehicleExpenseDto);
				
			}
			}else {
				if(tyreMountHistory != null && !tyreMountHistory.isEmpty()) {
					DateWiseVehicleExpenseDto vehicleExpenseDto2= new DateWiseVehicleExpenseDto();
					vehicleExpenseDto2.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE);
					vehicleExpenseDto2.setExpenseTypeStr(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME);
					vehicleExpenseDto2.setExpenseAmount((double) 0);
					vehicleExpenseDto2.setExpenseTypeName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME);
					vehicleExpenseDto2.setVid(tyreMountHistory.get(0).getVEHICLE_ID());
					tyreMountHistory.forEach(e->vehicleExpenseDto2.setExpenseAmount(e.getTYRE_AMOUNT()+vehicleExpenseDto2.getExpenseAmount()));
					if(expenseDtoList == null) {
						expenseDtoList	= new ArrayList<>();
					}
					expenseDtoList.add(vehicleExpenseDto2);
				}
			}
			
			if(batteryList != null && !batteryList.isEmpty()) {
				
				vehicleExpenseDto = new DateWiseVehicleExpenseDto();
				vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY);
				vehicleExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY_NAME);
				vehicleExpenseDto.setTripExpenseName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY_NAME);
				vehicleExpenseDto.setExpenseAmount((double) 0);
				for(BatteryDto  batteryDto : batteryList) {
					Double batteryAmount = (double) 0;
					
					batteryAmount	= batteryDto.getBatteryAmount() * daysInMonth;
						
					vehicleExpenseDto.setExpenseAmount(batteryAmount + vehicleExpenseDto.getExpenseAmount());
					vehicleExpenseDto.setVid(batteryDto.getVid());
				}
				
				if(expenseDtoList == null) {
					expenseDtoList	= new ArrayList<>();
					
				}
				expenseDtoList.add(vehicleExpenseDto);
				
			}
			
			
			if(tripExpenseList != null && !tripExpenseList.isEmpty()) {
				
				for(DateWiseVehicleExpenseDto  tripSheet : tripExpenseList) {
					vehicleExpenseDto = new DateWiseVehicleExpenseDto();
					vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
					vehicleExpenseDto.setExpenseTypeName(tripSheet.getExpenseTypeName());
					vehicleExpenseDto.setExpenseAmount(tripSheet.getExpenseAmount());
					vehicleExpenseDto.setExpenseId(tripSheet.getExpenseId());
					vehicleExpenseDto.setTripExpenseName(tripSheet.getExpenseTypeName());
					
					if(expenseDtoList == null) {
						expenseDtoList	= new ArrayList<>();
						
					}
					expenseDtoList.add(vehicleExpenseDto);
				}	
			}
			
			
			if(vehicleEMIList != null && !vehicleEMIList.isEmpty()) {
				
				vehicleExpenseDto = new DateWiseVehicleExpenseDto();
				vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI);
				vehicleExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI_NAME);
				vehicleExpenseDto.setTripExpenseName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI_NAME);
				vehicleExpenseDto.setExpenseAmount((double) 0);
				for(VehicleEmiPaymentDetailsDto  veEmiDetails : vehicleEMIList) {
					if(valueObject.getBoolean("vehicleMonthlyEMIPayment")== true) {
						if(EMIHM.containsKey(veEmiDetails.getVehicleEmiDetailsId())) {
							vehicleExpenseDto.setExpenseAmount(veEmiDetails.getEmiPaidAmount()+vehicleExpenseDto.getExpenseAmount());
						}else {
							vehicleExpenseDto.setExpenseAmount(veEmiDetails.getDownPaymentAmount()+ veEmiDetails.getEmiPaidAmount()+vehicleExpenseDto.getExpenseAmount());
							EMIHM.put(veEmiDetails.getVehicleEmiDetailsId(), veEmiDetails);
						}
						
					}else {
						if(EMIHM.containsKey(veEmiDetails.getVehicleEmiDetailsId())) {
							vehicleExpenseDto.setExpenseAmount(veEmiDetails.getMonthlyEmiAmount()+vehicleExpenseDto.getExpenseAmount());
						}else {
							EMIHM.put(veEmiDetails.getVehicleEmiDetailsId(), veEmiDetails);
							vehicleExpenseDto.setExpenseAmount(veEmiDetails.getMonthlyEmiAmount()+vehicleExpenseDto.getExpenseAmount()+veEmiDetails.getDownPaymentAmount());
						}
					}
					vehicleExpenseDto.setVid(veEmiDetails.getVid());
				}
				
				if(expenseDtoList == null) {
					expenseDtoList	= new ArrayList<>();
					
				}
				expenseDtoList.add(vehicleExpenseDto);
				
			}
			
			if(expenseDtoList != null && !expenseDtoList.isEmpty()) {
				
				finalList	= new ArrayList<>();
				for(DateWiseVehicleExpenseDto dto : expenseDtoList) {
					
					if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						expenseDto	= expenseHM.get(dto.getExpenseId()+"_"+dto.getExpenseTypeName());
						
					}else if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_RENEWAL) {
						expenseDto	= expenseHM.get(dto.getExpenseId()+"_"+dto.getExpenseTypeName());
						
					}else {
						expenseDto	= expenseHM.get(dto.getExpenseType()+"_"+dto.getExpenseTypeName());
					}
					
					
					if(expenseDto == null) {
						expenseDto	= dto;
					}else {
						if(expenseDto.getExpenseAmount()  == null) {
							expenseDto.setExpenseAmount(0.0);
						}	
						if(dto.getExpenseAmount()  == null) {
							dto.setExpenseAmount(0.0);
						}	
						expenseDto.setExpenseAmount(expenseDto.getExpenseAmount() + dto.getExpenseAmount()); 
							
						 expenseDto.setVid(dto.getVid());
							//expenseDto.setExpenseType(dto.getExpenseType());
							//expenseDto.setTripExpenseName(dto.getTripExpenseName());
							//expenseDto.setExpenseTypeStr(dto.getTripExpenseName());
							
					}
					
					
					if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						expenseHM.put(dto.getExpenseId()+"_"+dto.getExpenseTypeName(), expenseDto);
						
					}else if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_RENEWAL) {
						expenseHM.put(dto.getExpenseId()+"_"+dto.getExpenseTypeName(), expenseDto);
						
					}else {
						expenseHM.put(dto.getExpenseType()+"_"+dto.getExpenseTypeName(), expenseDto);
					}
				}
				
			}
			
			if(showDriverMonthlySalary && driverSalaryFromVehicle.getDriverMonthlySalary() != null) {
				Timestamp startTimeStampDate 			= DateTimeUtility.geTimeStampFromDate(form.parse(fromDateStr));
				
				Timestamp endTimeStampDate 				= DateTimeUtility.geTimeStampFromDate(form.parse(toDateStr));
				
				int getNoOfMonthBetweenTwoDates 		= DateTimeUtility.getNoOfMonthBetweenTwoDates(startTimeStampDate,endTimeStampDate);
				
				double driverMonthlySalary         	    = driverSalaryFromVehicle.getDriverMonthlySalary();
				
				double driverSalaryBasedOnRangeMonths   = driverMonthlySalary * getNoOfMonthBetweenTwoDates;
				
				Timestamp 		firstDayOfMonth			= DateTimeUtility.getFirstDayOfMonth(startTimeStampDate);
				
				Timestamp       lastDayOfMonth			= DateTimeUtility.getLastDayOfMonth(endTimeStampDate);
				
				long   dayDiffBetweenTwoDates     		= DateTimeUtility.getDayDiffBetweenTwoDates(firstDayOfMonth,lastDayOfMonth);
				
				double  driverSalaryPerDay	        	= (driverSalaryBasedOnRangeMonths/dayDiffBetweenTwoDates);
				
				long   dayDiffBetweenWorkingDays   	    = DateTimeUtility.getDayDiffBetweenTwoDates(startTimeStampDate,endTimeStampDate);
				
				double driverTotalSalary				= driverSalaryPerDay * dayDiffBetweenWorkingDays ;
				
				//expenseDto.setExpenseAmount(driverTotalSalary);
				
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY_NAME, new DateWiseVehicleExpenseDto(driverTotalSalary, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY));
				
			}
			
			finalList	= 	new ArrayList<DateWiseVehicleExpenseDto>(expenseHM.values());
			
			
			
			return	finalList.stream()
			.sorted(Comparator.comparing(DateWiseVehicleExpenseDto::getExpenseType).thenComparing(DateWiseVehicleExpenseDto::getExpenseTypeName))
					.collect(Collectors.toList());
			
			//return finalList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			finalList				= null;
			expenseDtoList			= null;
			expenseHM				= null;	
			expenseDto				= null;
			renewalList				= null;
			vehicleTyreList			= null;
			batteryList				= null;
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<MonthWiseVehicleExpenseDto>  getFinalListOfExpenseForUI(ValueObject	valueObject) throws Exception{
		
		List<MonthWiseVehicleExpenseDto> 				finalList				= null;
		List<MonthWiseVehicleExpenseDto>				expenseDtoList			= null;
		HashMap<String, MonthWiseVehicleExpenseDto>		expenseHM				= null;	
		MonthWiseVehicleExpenseDto						expenseDto				= null;
		List<RenewalReminderDto> 						renewalList				= null;
		List<VehicleTyreLayoutPositionDto>				vehicleTyreList			= null;
		List<BatteryDto>								batteryList				= null;
		Integer											totalRunningKM			= 0;
		List<VehicleEmiPaymentDetailsDto> 				vehicleEMIList			= null;
		VehicleDto										driverSalaryFromVehicle	= null;
		boolean											showDriverMonthlySalary	= false;
		HashMap<Long, VehicleEmiPaymentDetailsDto>  	EMIHM					= null;
		List<InventoryTyreDto> 							tyreMountHistory           =null;
		boolean											showDriverMonthlyBhatta	= false;
		try {
				
				expenseHM	= new HashMap<>();
				EMIHM		= new HashMap<>();
				/**
				 * Adding Expense Head
				 */
				showDriverMonthlyBhatta = valueObject.getBoolean("showDriverMonthlyBhatta",false);

				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL_NAME, new MonthWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES_NAME, new MonthWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER_NAME, new MonthWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME, new MonthWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY_NAME, new MonthWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI_NAME, new MonthWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY_NAME, new MonthWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES_NAME, new MonthWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES));
				if(showDriverMonthlyBhatta) 
					expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_BHATTA+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_BHATTA_NAME, new MonthWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_BHATTA_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_BHATTA));
				
			expenseDtoList			= (List<MonthWiseVehicleExpenseDto>) valueObject.get("expenseDtoList");
			renewalList				= (List<RenewalReminderDto>) valueObject.get("renewalList");
			vehicleTyreList			= (List<VehicleTyreLayoutPositionDto>) valueObject.get("vehicleTyreList");
			batteryList				= (List<BatteryDto>) valueObject.get("batteryList");
			vehicleEMIList			= (List<VehicleEmiPaymentDetailsDto>) valueObject.get("vehicleEMIList");
			tyreMountHistory		= (List<InventoryTyreDto>) valueObject.get("tyreMountList");
			totalRunningKM			= valueObject.getInt("totalRunningKM", 0);
			driverSalaryFromVehicle	= (VehicleDto) valueObject.get("driverSalaryFromVehicle");
			showDriverMonthlySalary	= valueObject.getBoolean("showDriverMonthlySalary",false);
			
			MonthWiseVehicleExpenseDto	vehicleExpenseDto = null;
			if(renewalList != null && !renewalList.isEmpty()) {
				
				for(RenewalReminderDto  reminderDto : renewalList) {
					vehicleExpenseDto = new MonthWiseVehicleExpenseDto();
					vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_RENEWAL);
					vehicleExpenseDto.setExpenseTypeStr(reminderDto.getRenewal_type());
					vehicleExpenseDto.setExpenseId(Integer.parseInt(reminderDto.getRenewal_id()+""));
					vehicleExpenseDto.setTripExpenseName(reminderDto.getRenewal_type());
					
					
					
					
					Timestamp	countStartDate = null;
					Timestamp	countEndDate  = null;
					Timestamp	fromDate	= (Timestamp) valueObject.get("fromDate");
					Timestamp	toDate		= (Timestamp) valueObject.get("toDate");
					
					
					if(reminderDto.getRenewal_from_Date().after(fromDate)) {
						countStartDate = new Timestamp(reminderDto.getRenewal_from_Date().getTime());
					}else {
						countStartDate = fromDate;
					}
					
					if(reminderDto.getRenewal_To_Date().before(toDate)) {
						countEndDate = new Timestamp(reminderDto.getRenewal_To_Date().getTime());
					}else {
						countEndDate = toDate;
					}
					
					long dayDiff	 	 = DateTimeUtility.getDayDiffBetweenTwoDates(new Timestamp(reminderDto.getRenewal_from_Date().getTime()), new Timestamp(reminderDto.getRenewal_To_Date().getTime()));
					long daysInMonth	 = DateTimeUtility.getDayDiffBetweenTwoDates(countStartDate, countEndDate);
				
					double renewalAmountForMonth = 0;
					if(reminderDto.getRenewal_Amount() != null && reminderDto.getRenewal_Amount() > 0) 
						renewalAmountForMonth = (reminderDto.getRenewal_Amount() / dayDiff) * daysInMonth;
					
					vehicleExpenseDto.setExpenseAmount(renewalAmountForMonth);
					
					
					vehicleExpenseDto.setVid(reminderDto.getVid());
					
					if(expenseDtoList == null) {
						expenseDtoList	= new ArrayList<>();
						
					}
					expenseDtoList.add(vehicleExpenseDto);
				}
				
				
			}
			if(!valueObject.getBoolean("vehicleTyreMountCostInPLReport",false) ) {
				
		
			if(vehicleTyreList != null && !vehicleTyreList.isEmpty()) {
				
				vehicleExpenseDto = new MonthWiseVehicleExpenseDto();
				vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE);
				vehicleExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME);
				vehicleExpenseDto.setExpenseAmount((double) 0);
				vehicleExpenseDto.setTripExpenseName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME);
				for(VehicleTyreLayoutPositionDto  positionDto : vehicleTyreList) {
					Double tyreAmountForMonth = (double) 0;
					Integer runKm	=	0 ;
					if(totalRunningKM != null) {
						runKm	= totalRunningKM;
					}
					
					tyreAmountForMonth	= positionDto.getCostPerKM() * runKm;
					vehicleExpenseDto.setExpenseAmount(tyreAmountForMonth + vehicleExpenseDto.getExpenseAmount());
					vehicleExpenseDto.setVid(positionDto.getVEHICLE_ID());
				}
				
				if(expenseDtoList == null) {
					expenseDtoList	= new ArrayList<>();
				}
				expenseDtoList.add(vehicleExpenseDto);
				
			}
			}else {
				
			if(tyreMountHistory != null && !tyreMountHistory.isEmpty()) {
				MonthWiseVehicleExpenseDto vehicleExpenseDto2 = new MonthWiseVehicleExpenseDto();
				vehicleExpenseDto2.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE);
				vehicleExpenseDto2.setExpenseTypeStr(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME);
				vehicleExpenseDto2.setExpenseAmount((double) 0);
				vehicleExpenseDto2.setTripExpenseName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME);
				vehicleExpenseDto2.setVid(tyreMountHistory.get(0).getVEHICLE_ID());
				tyreMountHistory.forEach(e->vehicleExpenseDto2.setExpenseAmount(e.getTYRE_AMOUNT()+vehicleExpenseDto2.getExpenseAmount()));
				if(expenseDtoList == null) {
					expenseDtoList	= new ArrayList<>();
				}
				expenseDtoList.add(vehicleExpenseDto2);
			}
			}
			
			if(batteryList != null && !batteryList.isEmpty()) {
				
				vehicleExpenseDto = new MonthWiseVehicleExpenseDto();
				vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY);
				vehicleExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY_NAME);
				vehicleExpenseDto.setTripExpenseName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY_NAME);
				vehicleExpenseDto.setExpenseAmount((double) 0);
				for(BatteryDto  batteryDto : batteryList) {
					Double batteryAmount = (double) 0;
					
					batteryAmount	= batteryDto.getBatteryAmount() * DateTimeUtility.getNoOfDaysInMonth((Timestamp) valueObject.get("startDateOfMonth"));
						
					vehicleExpenseDto.setExpenseAmount(batteryAmount + vehicleExpenseDto.getExpenseAmount());
					vehicleExpenseDto.setVid(batteryDto.getVid());
				}
				
				if(expenseDtoList == null) {
					expenseDtoList	= new ArrayList<>();
					
				}
				expenseDtoList.add(vehicleExpenseDto);
				
			}
			
			if(vehicleEMIList != null && !vehicleEMIList.isEmpty()) {
				
				vehicleExpenseDto = new MonthWiseVehicleExpenseDto();
				vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI);
				vehicleExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI_NAME);
				vehicleExpenseDto.setTripExpenseName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI_NAME);
				vehicleExpenseDto.setExpenseAmount((double) 0);
				for(VehicleEmiPaymentDetailsDto  veEmiDetails : vehicleEMIList) {
					if(valueObject.getBoolean("vehicleMonthlyEMIPayment")== true) {
						if(EMIHM.containsKey(veEmiDetails.getVehicleEmiDetailsId())) {
							vehicleExpenseDto.setExpenseAmount(veEmiDetails.getEmiPaidAmount()+vehicleExpenseDto.getExpenseAmount());
						}else {
							vehicleExpenseDto.setExpenseAmount(veEmiDetails.getDownPaymentAmount()+ veEmiDetails.getEmiPaidAmount()+vehicleExpenseDto.getExpenseAmount());
							EMIHM.put(veEmiDetails.getVehicleEmiDetailsId(), veEmiDetails);
						}
						
					}else {
						if(EMIHM.containsKey(veEmiDetails.getVehicleEmiDetailsId())) {
							vehicleExpenseDto.setExpenseAmount(veEmiDetails.getMonthlyEmiAmount()+vehicleExpenseDto.getExpenseAmount());
						}else {
							EMIHM.put(veEmiDetails.getVehicleEmiDetailsId(), veEmiDetails);
							vehicleExpenseDto.setExpenseAmount(veEmiDetails.getMonthlyEmiAmount()+vehicleExpenseDto.getExpenseAmount()+veEmiDetails.getDownPaymentAmount());
						}
					}
					vehicleExpenseDto.setVid(veEmiDetails.getVid());
				}
				
				if(expenseDtoList == null) {
					expenseDtoList	= new ArrayList<>();
					
				}
				expenseDtoList.add(vehicleExpenseDto);
				
			}
				
			if(showDriverMonthlySalary && driverSalaryFromVehicle.getDriverMonthlySalary() != null) {
				vehicleExpenseDto = new MonthWiseVehicleExpenseDto();
				vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY);
				vehicleExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY_NAME);
				vehicleExpenseDto.setTripExpenseName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY_NAME);
				vehicleExpenseDto.setExpenseAmount((double) 0);
				
				double driverSalaryBasedOnRangeMonths   = driverSalaryFromVehicle.getDriverMonthlySalary() ;
				
				vehicleExpenseDto.setExpenseAmount(driverSalaryBasedOnRangeMonths);
				if(expenseDtoList == null) {
					expenseDtoList	= new ArrayList<>();
					
				}
				expenseDtoList.add(vehicleExpenseDto);
			}
			
			System.err.println("driverSalaryFromVehicle.getDriverMonthlyBhatta() -- "+ driverSalaryFromVehicle.getDriverMonthlyBhatta());
			if(showDriverMonthlyBhatta && driverSalaryFromVehicle.getDriverMonthlyBhatta() != null) {
				
				System.err.println("inside expenseDtoList ");
				vehicleExpenseDto = new MonthWiseVehicleExpenseDto();
				vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_BHATTA);
				vehicleExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_BHATTA_NAME);
				vehicleExpenseDto.setTripExpenseName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_BHATTA_NAME);
				vehicleExpenseDto.setExpenseAmount((double) 0);
				
				double driverSalaryBasedOnRangeMonths   =  Double.parseDouble(driverSalaryFromVehicle.getDriverMonthlyBhatta());
				
				vehicleExpenseDto.setExpenseAmount(driverSalaryBasedOnRangeMonths);
				if(expenseDtoList == null) {
					expenseDtoList	= new ArrayList<>();
					
				}
				expenseDtoList.add(vehicleExpenseDto);
			}
			
			System.err.println("expenseDtoList -- "+ expenseDtoList);
			if(expenseDtoList != null && !expenseDtoList.isEmpty()) {
				
				finalList	= new ArrayList<>();
				for(MonthWiseVehicleExpenseDto dto : expenseDtoList) {
					
					
					if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						expenseDto	= expenseHM.get(dto.getExpenseId()+"_"+dto.getExpenseTypeStr());
						
					}else if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_RENEWAL) {
						expenseDto	= expenseHM.get(dto.getExpenseId()+"_"+dto.getExpenseTypeStr());
					}else {
						expenseDto	= expenseHM.get(dto.getExpenseType()+"_"+dto.getExpenseTypeStr());
					}
					
					if(expenseDto == null) {
						expenseDto	= dto;
					}else {
							if(dto.getExpenseAmount() == null)
								dto.setExpenseAmount(0.0);
							expenseDto.setExpenseAmount(expenseDto.getExpenseAmount() + dto.getExpenseAmount());
							expenseDto.setVid(dto.getVid());
					}
					
					
					if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						expenseHM.put(dto.getExpenseId()+"_"+dto.getExpenseTypeStr(), expenseDto);
					}else if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_RENEWAL) {
						expenseHM.put(dto.getExpenseId()+"_"+dto.getExpenseTypeStr(), expenseDto);
					}else {
						expenseHM.put(dto.getExpenseType()+"_"+dto.getExpenseTypeStr(), expenseDto);
					}
				}
				
			}
			
			
			
			finalList	= 	new ArrayList<MonthWiseVehicleExpenseDto>(expenseHM.values());
			
			return	finalList.stream()
							.sorted(Comparator.comparing(MonthWiseVehicleExpenseDto::getExpenseType)
									.thenComparing(MonthWiseVehicleExpenseDto::getTripExpenseName))
									.collect(Collectors.toList());
			
		} catch (Exception e) {
			throw e;
		}finally {
			finalList				= null;
			expenseDtoList			= null;
			expenseHM				= null;	
			expenseDto				= null;
			renewalList				= null;
			vehicleTyreList			= null;
			batteryList				= null;
			vehicleEMIList			= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject getGroupWiseProfitAndLossReport(ValueObject valueObject) throws Exception {
		List<MonthWiseVehicleExpenseDto>				expenseDtoList			= null;
		CustomUserDetails								userDetails				= null;
		Timestamp										startDateOfMonth		= null;
		String											month					= null;
		List<RenewalReminderDto>						renewalList				= null;
		ValueObject										valueOutObject			= null;
		List<VehicleProfitAndLossReportDto> 			incomeList				= null;
		List<VehicleTyreLayoutPositionDto>				vehicleTyreList			= null;
		VehicleOdometerHistory							vehicleOdometerHistory	= null;
		List<BatteryDto>								batteryList				= null;
		List<TripSheetDto> 								tripSheetList			= null;
		Timestamp										fromDate				= null;
		Timestamp										toDate					= null;
		int												tripFlavor				= 0;
		List<Vehicle> 									vehicleList				= null;
		HashMap<Integer, Double> 						expenseHM				= null;	
		Map<Integer, Double> 						inspectionPenaltyHM		= null;	
		List<GroupWiseVehicleProfitAndLossReportDto>	reportDtoList			= null;
		HashMap<Integer, VehicleProfitAndLossReportDto> incomeHM				= null;
		ValueObject										outObject				= null;
		boolean											showDriverMonthlySalary	= false; 
		HashMap<String, Object>							configDriverSalary		= null;  
		List<VehicleEmiPaymentDetailsDto>				vehicleEMIList			= null;
		boolean											vehicleMonthlyEMIPayment	= false;
		List<VehicleDto> 								vehicleDtoList				= null;
		try {
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueOutObject	= new ValueObject();
			
			
			month	= valueObject.getString("dateOfMonth", null);
			configDriverSalary			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG); 
			showDriverMonthlySalary		= (boolean) configDriverSalary.getOrDefault(VehicleConfigurationContant.SHOW_DRIVER_MONTHLY_SALARY, false); 
			vehicleMonthlyEMIPayment 	= (boolean) configDriverSalary.getOrDefault(VehicleConfigurationContant.VEHICLE_MONTHLY_EMI_PAYMENT, false);
			vehicleList	=	vehicleService.vehicle_wise_GroupFuelRange_Group(valueObject.getLong("vehicleGroupId", 0), userDetails.getCompany_id());
			startDateOfMonth		= DateTimeUtility.getTimeStamp(month, DateTimeUtility.DD_MM_YY);
			tripFlavor				= companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			fromDate				=	DateTimeUtility.getFirstDayOfMonth(startDateOfMonth); 
			toDate					=	DateTimeUtility.getLastDayOfMonth(startDateOfMonth);
			inspectionPenaltyHM 	= inspectionCompletionDetailsService.getMonthWiseInspectionPenaltyByVGroup(valueObject.getLong("vehicleGroupId", 0), fromDate, toDate, userDetails.getCompany_id());
		
			valueObject.put("userDetails", userDetails);
			valueObject.put("month", month);
			valueObject.put("tripFlavor", tripFlavor);
			valueObject.put("startDateOfMonth", startDateOfMonth);
			valueObject.put("fromDate", fromDate);
			valueObject.put("toDate", toDate);
		
			
			outObject	=	runThreadForVehicleGroupWiseProfitAndLossReport(valueObject);
			
			expenseDtoList			=	(List<MonthWiseVehicleExpenseDto>) outObject.get("expenseDtoList");
			incomeList				= 	(List<VehicleProfitAndLossReportDto>) outObject.get("incomeList");
			renewalList				= 	(List<RenewalReminderDto>) outObject.get("renewalList");
			vehicleTyreList			=	(List<VehicleTyreLayoutPositionDto>) outObject.get("vehicleTyreList");
			batteryList				=	(List<BatteryDto>) outObject.get("batteryList");
			vehicleEMIList			= 	(List<VehicleEmiPaymentDetailsDto>) outObject.get("vehicleEMIList");
			
			tripSheetList			= 	(List<TripSheetDto>) outObject.get("tripSheetList");	
			vehicleDtoList			= vehicleService.Get_Vehicle_Header_DetailsByGid(valueObject.getLong("vehicleGroupId", 0));
			valueOutObject.put("driverSalaryFromVehicle", vehicleDtoList);
			valueOutObject.put("expenseDtoList", expenseDtoList);
			valueOutObject.put("renewalList", renewalList);
			valueOutObject.put("startDateOfMonth", startDateOfMonth);
			valueOutObject.put("vehicleTyreList", vehicleTyreList);
			valueOutObject.put("vehicleOdometerHistory", vehicleOdometerHistory);
			valueOutObject.put("batteryList", batteryList);
			valueOutObject.put("fromDate", fromDate);
			valueOutObject.put("toDate", toDate);
			valueOutObject.put("vehicleGroupId", valueObject.getLong("vehicleGroupId", 0));
			valueOutObject.put("tripFlavor", tripFlavor);
			valueOutObject.put("showDriverMonthlySalary", showDriverMonthlySalary);
			valueOutObject.put("vehicleEMIList", vehicleEMIList);
			valueOutObject.put("vehicleMonthlyEMIPayment", vehicleMonthlyEMIPayment);
			
			
			ValueObject	object	=	getTripSheetDetailsHM(tripSheetList, valueObject);
			
			Map<Integer, List<TripSheetDto>> groupByVidMap	= (Map<Integer, List<TripSheetDto>>) object.get("groupByVidMap");
			
			expenseHM	= getVehicleWiseExpenseHM(valueOutObject, groupByVidMap);
			incomeHM	=  getTotalIncomeHMfromList(incomeList);
			
			if(vehicleList != null && !vehicleList.isEmpty()) {
				
				reportDtoList	= new ArrayList<>();
				GroupWiseVehicleProfitAndLossReportDto	lossReportDto	= null;
				
				for(Vehicle vehicle : vehicleList) {
					ValueObject	object2	=	null;
					if(groupByVidMap != null)
						object2	=	getTripSheetDetailsOfMonthByVid(groupByVidMap.get(vehicle.getVid()), valueObject);
					
					lossReportDto	= new GroupWiseVehicleProfitAndLossReportDto();
					
					Double	incomeAmount	= 0.0;
					Double	expenseAmount	= 0.0;
					
					lossReportDto.setVid(vehicle.getVid());
					lossReportDto.setVehicle_registration(vehicle.getVehicle_registration());
					if(expenseHM.get(vehicle.getVid()) != null) {
						if(expenseHM.get(vehicle.getVid()) != null)
							expenseAmount	= expenseHM.get(vehicle.getVid());
					}
					if(inspectionPenaltyHM != null && inspectionPenaltyHM.get(vehicle.getVid()) != null) {
						expenseAmount += inspectionPenaltyHM.get(vehicle.getVid());
					}
						lossReportDto.setTotalExpense(expenseAmount);   
						
					if(incomeHM.get(vehicle.getVid()) != null) {
						if(incomeHM.get(vehicle.getVid()).getIncomeAmount() != null)
							incomeAmount	= incomeHM.get(vehicle.getVid()).getIncomeAmount();
					}
					lossReportDto.setTotalIncome(incomeAmount);
					
					lossReportDto.setTotalBalance(lossReportDto.getTotalIncome() - lossReportDto.getTotalExpense());
					if(object2 != null) {
						lossReportDto.setDaysInTrip(object2.getInt("noOftripDays", 0));
						lossReportDto.setNoOfTrips(object2.getInt("noOftrip", 0));
						lossReportDto.setNoOfDaysIDeal(object2.getInt("noOfDaysIdeal", 0));
						lossReportDto.setTotalKMRun(object2.getInt("totalRunKM", 0));
					}else {
						lossReportDto.setDaysInTrip(0);
						lossReportDto.setNoOfTrips(0);
						lossReportDto.setNoOfDaysIDeal(0);
						lossReportDto.setTotalKMRun(0);
					}
					
					reportDtoList.add(lossReportDto);
				}
			}
			
			VehicleGroup	vehicleGroup	= vehicleGroupService.getVehicleGroupByID(valueObject.getLong("vehicleGroupId", 0));
			
			valueObject.clear();
			valueObject.put("reportDtoList", reportDtoList);
			valueObject.put("vehicleGroup", vehicleGroup);
			valueObject.put("noOfVehicle", vehicleList.size());
			valueObject.put("daysInMonth", DateTimeUtility.getNoOfDaysInMonth(startDateOfMonth));
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
		expenseDtoList			= null;
		userDetails				= null;
		startDateOfMonth		= null;
		month					= null;
		renewalList				= null;
		valueOutObject			= null;
		incomeList				= null;
		vehicleTyreList			= null;
		vehicleOdometerHistory	= null;
		batteryList				= null;
		tripSheetList			= null;}
	}
	
	/*private HashMap<Integer, MonthWiseVehicleExpenseDto> getExpenseHMfromList(List<MonthWiseVehicleExpenseDto>		finalList) throws Exception{
		HashMap<Integer, MonthWiseVehicleExpenseDto> 	expenseHM				= null;	
		MonthWiseVehicleExpenseDto						vehicleExpenseDto		= null;
		try {
			expenseHM	= new HashMap<>();
			
			if(finalList != null && !finalList.isEmpty()) {
				for(MonthWiseVehicleExpenseDto	expenseDto	: finalList) {
					vehicleExpenseDto	= 	expenseHM.get(expenseDto.getVid());
					if(vehicleExpenseDto == null) {
						vehicleExpenseDto	= expenseDto;
					}else {
						vehicleExpenseDto.setExpenseAmount(vehicleExpenseDto.getExpenseAmount() + expenseDto.getExpenseAmount());
						vehicleExpenseDto.setVid(expenseDto.getVid());
					}
					
					expenseHM.put(expenseDto.getVid(), vehicleExpenseDto);
					
				}
			}
			
			return expenseHM;
		} catch (Exception e) {
			throw e;
		}
	}*/
	
	private HashMap<Integer, VehicleProfitAndLossReportDto> getTotalIncomeHMfromList(List<VehicleProfitAndLossReportDto>		finalList) throws Exception{
		HashMap<Integer, VehicleProfitAndLossReportDto> 	incomeHM				= null;	
		VehicleProfitAndLossReportDto						vehicleIncomeDto		= null;
		try {
			incomeHM	= new HashMap<>();
			
			if(finalList != null && !finalList.isEmpty()) {
				for(VehicleProfitAndLossReportDto	incomeDto	: finalList) {
					vehicleIncomeDto	= 	incomeHM.get(incomeDto.getVid());
					if(vehicleIncomeDto == null) {
						vehicleIncomeDto	= incomeDto;
					}else {
						vehicleIncomeDto.setIncomeAmount(vehicleIncomeDto.getIncomeAmount() + incomeDto.getIncomeAmount());
					}
					
					incomeHM.put(incomeDto.getVid(), vehicleIncomeDto);
					
				}
			}
			
			return incomeHM;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private ValueObject  getTripSheetDetailsHM(List<TripSheetDto> 	tripSheetList, ValueObject	valueObject) throws Exception {
		int																daysInMonth				= 0;
		ValueObject														object					= null;
		try {
			object				= new ValueObject();
		
			Calendar cal = Calendar.getInstance();
			cal.setTime(DateTimeUtility.getTimeStamp(valueObject.getString("dateOfMonth")));
			
			daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			object.put("daysInMonth", daysInMonth);
		
			if(tripSheetList != null && !tripSheetList.isEmpty()) {
				
				Map<Integer, List<TripSheetDto>> groupByVidMap = 
						tripSheetList.stream().collect(Collectors.groupingBy(TripSheetDto::getVid));

				object.put("groupByVidMap", groupByVidMap);
				
			}
		
			return object;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	private HashMap<Integer, Double> getVehicleWiseExpenseHM(ValueObject		valueObject, Map<Integer, List<TripSheetDto>> groupByVidMap)throws Exception{
			HashMap<Integer, Double>						vehicleWiseExpenseHM	= null;
			List<MonthWiseVehicleExpenseDto>				expenseDtoList			= null;
			List<RenewalReminderDto> 						renewalList				= null;
			List<VehicleTyreLayoutPositionDto>				vehicleTyreList			= null;
			List<BatteryDto>								batteryList				= null;
			Double											amount					= 0.0;
			Timestamp										fromDate				= null;
			Timestamp										toDate					= null;
			long											vehicleGroupId			= 0;
			int												tripFlavor				= 0;
			boolean											showDriverMonthlySalary	= false;
			Timestamp										countStartDate			= null;
			Timestamp										countEndDate			= null;
			List<VehicleEmiPaymentDetailsDto>				vehicleEMIList			= null;
			List<VehicleDto>								driverSalaryFromVehicle	= null;
	//		HashMap<Long, VehicleEmiPaymentDetailsDto>  	EMIHM					= null;
			MonthWiseVehicleExpenseDto						vehicleExpenseDto		= null;
			Double											emiAmt					= 0.0;
			HashMap<Long, Double>							EMIHM					= null;
			
		try {
			
			expenseDtoList			= (List<MonthWiseVehicleExpenseDto>) valueObject.get("expenseDtoList");
			renewalList				= (List<RenewalReminderDto>) valueObject.get("renewalList");
			vehicleTyreList			= (List<VehicleTyreLayoutPositionDto>) valueObject.get("vehicleTyreList");
			batteryList				= (List<BatteryDto>) valueObject.get("batteryList");
			fromDate				= (Timestamp) valueObject.get("fromDate");
			toDate					= (Timestamp) valueObject.get("toDate");
			vehicleGroupId			= valueObject.getLong("vehicleGroupId", 0);
			tripFlavor				= valueObject.getInt("tripFlavor", 0);
			showDriverMonthlySalary = valueObject.getBoolean("showDriverMonthlySalary");
			vehicleEMIList			= (List<VehicleEmiPaymentDetailsDto>) valueObject.get("vehicleEMIList");
			driverSalaryFromVehicle	= (List<VehicleDto>) valueObject.get("driverSalaryFromVehicle");
			
			vehicleWiseExpenseHM	= new HashMap<>();
			EMIHM					= new HashMap<>();
			
			if(renewalList != null && !renewalList.isEmpty()) {
				
				for(RenewalReminderDto  reminderDto : renewalList) {
					
					if(reminderDto.getRenewal_from_Date().after(fromDate)) {
						countStartDate = new Timestamp(reminderDto.getRenewal_from_Date().getTime());
					}else {
						countStartDate = fromDate;
					}
					
					if(reminderDto.getRenewal_To_Date().before(toDate)) {
						countEndDate = new Timestamp(reminderDto.getRenewal_To_Date().getTime());
					}else {
						countEndDate = toDate;
					}
					
					long dayDiff	 	 = DateTimeUtility.getDayDiffBetweenTwoDates(new Timestamp(reminderDto.getRenewal_from_Date().getTime()), new Timestamp(reminderDto.getRenewal_To_Date().getTime()));
					long daysInMonth	 = DateTimeUtility.getDayDiffBetweenTwoDates(countStartDate, countEndDate);
				
					double renewalAmountForMonth = 0;
					if(reminderDto.getRenewal_Amount() != null && reminderDto.getRenewal_Amount() > 0) 
						renewalAmountForMonth = (reminderDto.getRenewal_Amount() / dayDiff) * daysInMonth;
					
					amount	= vehicleWiseExpenseHM.get(reminderDto.getVid());
					if(amount == null || amount == 0.0) {
						amount	= renewalAmountForMonth;
					}else {
						amount += renewalAmountForMonth;
					}
					
					
					vehicleWiseExpenseHM.put(reminderDto.getVid(), amount);
					
				}
				
				
			}
			
			
			
			if(vehicleTyreList != null && !vehicleTyreList.isEmpty()) 	{
				HashMap<Integer, Long> runKMHM	=	null;
				if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					runKMHM	=	tripSheetService.getVehicleRunKMByGroupId(vehicleGroupId, fromDate, toDate);
				}
				if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
					runKMHM	=	tripDailySheetService.getVehicleRunKMByGroupId(vehicleGroupId, fromDate, toDate);
				}
				for(VehicleTyreLayoutPositionDto  positionDto : vehicleTyreList) {
					
					
					Double tyreAmountForMonth = (double) 0;
					Long runKm	=	runKMHM.get(positionDto.getVEHICLE_ID());
					if(runKm != null) 
						tyreAmountForMonth	= positionDto.getCostPerKM() * runKm;
				
					
					amount	= vehicleWiseExpenseHM.get(positionDto.getVEHICLE_ID());
					if(amount == null || amount == 0.0) {
						amount	= tyreAmountForMonth;
					}else {
						amount += tyreAmountForMonth;
					}
					
					vehicleWiseExpenseHM.put(positionDto.getVEHICLE_ID(), amount);
				}
			}
			
			if(batteryList != null && !batteryList.isEmpty()) {
				
				for(BatteryDto  batteryDto : batteryList) {
					Double batteryAmount = (double) 0;
					
					batteryAmount	= batteryDto.getBatteryAmount() * DateTimeUtility.getNoOfDaysInMonth((Timestamp) valueObject.get("startDateOfMonth"));
					
					amount	= vehicleWiseExpenseHM.get(batteryDto.getVid());
					if(amount == null || amount == 0.0) {
						amount	= batteryAmount;
					}else {
						amount += batteryAmount;
					}
					
					vehicleWiseExpenseHM.put(batteryDto.getVid(), amount);
						
				}
				
			}
			
			if(vehicleEMIList != null && !vehicleEMIList.isEmpty()) {
				vehicleExpenseDto = new MonthWiseVehicleExpenseDto();
				vehicleExpenseDto.setExpenseAmount((double)0.0);
				for(VehicleEmiPaymentDetailsDto  veEmiDetails : vehicleEMIList) {
					if(valueObject.getBoolean("vehicleMonthlyEMIPayment")== true) {
						amount	= vehicleWiseExpenseHM.get(veEmiDetails.getVid());
						emiAmt	= EMIHM.get(veEmiDetails.getVehicleEmiDetailsId());
						if(amount == null || amount == 0.0) {
							amount = veEmiDetails.getDownPaymentAmount()+ veEmiDetails.getEmiPaidAmount()+vehicleExpenseDto.getExpenseAmount();
						}else {
							if(emiAmt == null || emiAmt == 0.0) {
								amount += veEmiDetails.getDownPaymentAmount()+ veEmiDetails.getEmiPaidAmount()+vehicleExpenseDto.getExpenseAmount();
							}else {
								amount += veEmiDetails.getEmiPaidAmount()+vehicleExpenseDto.getExpenseAmount();
							}
						}
					vehicleWiseExpenseHM.put(veEmiDetails.getVid(), amount);
					
					}else {
						amount	= vehicleWiseExpenseHM.get(veEmiDetails.getVid());
						emiAmt	= EMIHM.get(veEmiDetails.getVehicleEmiDetailsId());
						if(amount == null || amount == 0.0) {
							amount = veEmiDetails.getDownPaymentAmount()+ veEmiDetails.getMonthlyEmiAmount()+vehicleExpenseDto.getExpenseAmount();
						}else {
							
							if(emiAmt == null || emiAmt == 0.0) {
								amount += veEmiDetails.getDownPaymentAmount()+ veEmiDetails.getMonthlyEmiAmount()+vehicleExpenseDto.getExpenseAmount();
							}else {
								amount += veEmiDetails.getMonthlyEmiAmount()+vehicleExpenseDto.getExpenseAmount();
							}
						}
						vehicleWiseExpenseHM.put(veEmiDetails.getVid(), amount);
					}
				}
				
			}
			
			
			if(showDriverMonthlySalary && driverSalaryFromVehicle != null) {
				for(VehicleDto  veEmiDetails : driverSalaryFromVehicle) {
					amount	= vehicleWiseExpenseHM.get(veEmiDetails.getVid());
					if(amount == null || amount == 0.0) {
						if(veEmiDetails.getDriverMonthlySalary() != null) {
							amount	= veEmiDetails.getDriverMonthlySalary();
						}else {
							amount = 0.0;
						}
					}else {
						if(veEmiDetails.getDriverMonthlySalary() != null) {
							amount += veEmiDetails.getDriverMonthlySalary();
						}else {
							amount += 0.0;
						}
					}
					vehicleWiseExpenseHM.put(veEmiDetails.getVid(), amount);
				}
			}
			
				if(expenseDtoList != null && !expenseDtoList.isEmpty()) {
					for(MonthWiseVehicleExpenseDto dto : expenseDtoList) {
						amount	= vehicleWiseExpenseHM.get(dto.getVid());
						
						if(amount == null || amount == 0.0) {
							if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY && showDriverMonthlySalary == false) {
								amount	= dto.getExpenseAmount();
							}else if(dto.getExpenseType() != VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY ) {
								amount	= dto.getExpenseAmount();
							}
						}else {
							if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY && showDriverMonthlySalary == false) {
								amount += dto.getExpenseAmount();
							}else if(dto.getExpenseType() != VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY ) {
								amount	+= dto.getExpenseAmount();
							}
						}
						
						vehicleWiseExpenseHM.put(dto.getVid(), amount);
					}
					
				}
			
			return vehicleWiseExpenseHM;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	private ValueObject	runThreadForVehicleProfitAndLossReport(ValueObject	valueObject) throws Exception {
		Thread											expensesThread			= null;
		Thread											incomeThread			= null;
		Thread											reminderThread			= null;
		Thread											tyreThread				= null;
		Thread											batteryThread			= null;
		Thread											tripSheetThread			= null;
		Thread											EMIThread				= null;
		Thread											fuelThread				= null;
		Thread											seThread				= null;
		Thread											dseThread				= null;
		Thread											woThread				= null;
		Thread											ureaThread				= null;
		Thread											dSalaryThread			= null;
		Thread											tripExpenseThread		= null;
		ValueObject										valueOutObject;
		List<MonthWiseVehicleExpenseDto>				expenseDtoList			= null;
		Thread											tollExpenseThread		= null;
		Thread											inspectionPenaltyThread	= null;
		try {
			valueOutObject		= new ValueObject();
			expenseDtoList		= new ArrayList<MonthWiseVehicleExpenseDto>();
			
			final	Timestamp			startDateOfMonth	= (Timestamp) valueObject.get("startDateOfMonth");
			final	CustomUserDetails	userDetails			= (CustomUserDetails) valueObject.get("userDetails");
			final	int					tripFlavor  		= valueObject.getInt("tripFlavor");
			final	Timestamp			fromDate			= (Timestamp) valueObject.get("fromDate");
			final	Timestamp			toDate				= (Timestamp) valueObject.get("toDate");
			
			String			toEndDate	=	DateTimeUtility.getEndOfDayDateStr(DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD), DateTimeUtility.YYYY_MM_DD);
			fuelThread	= new Thread() {
				public void run() {
					List<MonthWiseVehicleExpenseDto>				fuelList			= null;
					try {
						fuelList			=	fuelService.getMonthWiseFuelExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate+"", toDate+"", userDetails.getCompany_id());
						valueOutObject.put("fuelList", fuelList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			fuelThread.start();
			
			seThread	= new Thread() {
				public void run() {
					List<MonthWiseVehicleExpenseDto>				serviceList			= null;
					try {
						serviceList			=	serviceEntriesService.getMonthWiseServiceEntriesExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate+"", toDate+"", userDetails.getCompany_id());
						valueOutObject.put("serviceList", serviceList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			seThread.start();
			
			woThread	= new Thread() {
				public void run() {
					List<MonthWiseVehicleExpenseDto>			workOrderList			= null;
					try {
						workOrderList			=	workOrdersService.getMonthWiseWorkOrderExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate+"", toDate+"", userDetails.getCompany_id());
						valueOutObject.put("workOrderList", workOrderList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			woThread.start();
			
			ureaThread	= new Thread() {
				public void run() {
					List<MonthWiseVehicleExpenseDto>			ureaEntriesList			= null;
					try {
						ureaEntriesList			=	ureaEntriesService.getMonthWiseUreaExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate+"", toDate+"", userDetails.getCompany_id());
						valueOutObject.put("ureaEntriesList", ureaEntriesList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			ureaThread.start();
			
			
			incomeThread	= new Thread() {
				public void run() {
					List<VehicleProfitAndLossReportDto> 			incomeList			= null;
					try {
						incomeList				= 	vehicleProfitAndLossService.getIncomeDetailsForDateRange(valueObject.getInt("vid", 0), fromDate+"", toDate+"", userDetails.getCompany_id());
						valueOutObject.put("incomeList", incomeList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			incomeThread.start();
			
			reminderThread	= new Thread() {
				public void run() {
					List<RenewalReminderDto>						renewalList				= null;
					try {
						renewalList				= 	renewalReminderService.getVehicleRenewalExpenses(valueObject.getInt("vid", 0), startDateOfMonth, userDetails.getCompany_id(), toEndDate);
						valueOutObject.put("renewalList", renewalList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			reminderThread.start();
			
			tyreThread	= new Thread() {
				public void run() {
					List<VehicleTyreLayoutPositionDto>						vehicleTyreList				= null;
					try {
						vehicleTyreList			=	vehicleTyreLayoutService.getVehicleTyreExpenseDetailsList(valueObject.getInt("vid", 0), userDetails.getCompany_id());
						valueOutObject.put("vehicleTyreList", vehicleTyreList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tyreThread.start();
			
			batteryThread	= new Thread() {
				public void run() {
					List<BatteryDto>								batteryList				= null;
					try {
						batteryList				=	batteryService.getVehicleBatteryListForCost(valueObject.getInt("vid", 0));
						valueOutObject.put("batteryList", batteryList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			batteryThread.start();
			
			tripExpenseThread = new Thread() {
				public void run() {
					List<MonthWiseVehicleExpenseDto>				tripExpenseList			= null;
					try {
						tripExpenseList			=	vehicleProfitAndLossService.getMonthWiseVehicleTripExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate+"", toDate+"");
						
						valueOutObject.put("tripExpenseList", tripExpenseList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tripExpenseThread.start();
			
			dseThread	= new Thread() {
				public void run() {
					List<MonthWiseVehicleExpenseDto>				dealerServiceList			= null;
					try {
						dealerServiceList			=	dealerServiceEntriesService.getMonthWiseDealerServiceEntriesExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate+"", toDate+"", userDetails.getCompany_id());
						valueOutObject.put("dealerServiceList", dealerServiceList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			dseThread.start();
			tripSheetThread	= new Thread() {
				public void run() {
					List<TripSheetDto> 								tripSheetList			= null;
					try {
						if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
							tripSheetList		= 	tripSheetService.getTripSheetDetailsInMonthByVid(valueObject.getInt("vid", 0), fromDate, toDate);	
						}else if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
							tripSheetList		= 	tripDailySheetService.getTripSheetDetailsInMonthByVId(valueObject.getInt("vid", 0), fromDate, toDate);	
						}
						valueOutObject.put("tripSheetList", tripSheetList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tripSheetThread.start();
			
			EMIThread	= new Thread() {
				public void run() {
					List<VehicleEmiPaymentDetailsDto>		vehicleEMIList			= null;
					try {
						vehicleEMIList			= 	vehicleEmiDetailsService.getVehicleEMIForDateRange(valueObject.getString("fromDate"), valueObject.getString("toDate") , valueObject.getInt("vid", 0));
						valueOutObject.put("vehicleEMIList", vehicleEMIList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			
			EMIThread.start();
			
		
			
			tollExpenseThread = new Thread() {
				public void run() {
					List<MonthWiseVehicleExpenseDto>				tollExpenseList			= null;
					try {
							tollExpenseList			=	tollExpensesDetailsService.getMonthWiseFastTagTollExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate+"", toDate+"");
						
						valueOutObject.put("tollExpenseList", tollExpenseList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tollExpenseThread.start();
			
			dSalaryThread	= new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				driverSalaryList			= null;
					try {
						driverSalaryList			=	vehicleProfitAndLossService.getDateWiseDriverSalaryByVid(valueObject.getInt("vid", 0), valueObject.getString("fromDate"), valueObject.getString("toDate"), userDetails.getCompany_id());
						
						valueOutObject.put("driverSalaryList", driverSalaryList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			dSalaryThread.start();
			
			inspectionPenaltyThread = new Thread() {
				public void run() {
					List<MonthWiseVehicleExpenseDto>				inspectionPenaltyList			= null;
					 try {
						 inspectionPenaltyList=inspectionCompletionDetailsService.getMonthWiseInspectionPenaltyByVid(valueObject.getInt("vid", 0), valueObject.getString("fromDate"), valueObject.getString("toDate"), userDetails.getCompany_id());	
						 valueOutObject.put("inspectionPenaltyList", inspectionPenaltyList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
					
				}
			};
			inspectionPenaltyThread.start();
			
			if(valueObject.getBoolean("vehicleTyreMountCostInPLReport", false)) {
				Runnable tyreMountRun=()->{
					try {
						valueOutObject.put("tyreMountList", getTyreDetailsForPL(fromDate+"", toDate+"", valueObject.getInt("vid", 0)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
				Thread tyreMountThread = new Thread(tyreMountRun);
				tyreMountThread.start();
				tyreMountThread.join();
				}
			
			fuelThread.join();
			seThread.join();
			dseThread.join();
			woThread.join();
			tripExpenseThread.join();
			tollExpenseThread.join();
			ureaThread.join();
			incomeThread.join();
			reminderThread.join();
			tyreThread.join();
			batteryThread.join();
			tripSheetThread.join();
			inspectionPenaltyThread.join();
			if(!valueObject.getBoolean("vehicleMonthlyEMIPayment", false)) {
				EMIThread.join();
			}
			dSalaryThread.join();
			
			List<MonthWiseVehicleExpenseDto> tempList =  null;
			
			tempList = (List<MonthWiseVehicleExpenseDto>) valueOutObject.get("fuelList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
				
			tempList = (List<MonthWiseVehicleExpenseDto>) valueOutObject.get("serviceList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			
			tempList = (List<MonthWiseVehicleExpenseDto>) valueOutObject.get("workOrderList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			
			tempList = (List<MonthWiseVehicleExpenseDto>) valueOutObject.get("ureaEntriesList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			
			tempList = (List<MonthWiseVehicleExpenseDto>) valueOutObject.get("tollExpenseList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			
			tempList = (List<MonthWiseVehicleExpenseDto>) valueOutObject.get("tripExpenseList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			
			tempList = (List<MonthWiseVehicleExpenseDto>) valueOutObject.get("dealerServiceList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			
			tempList = (List<MonthWiseVehicleExpenseDto>) valueOutObject.get("inspectionPenaltyList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			
			valueOutObject.put("expenseDtoList", expenseDtoList);

			
			return valueOutObject;
		} catch (Exception e) {
			LOGGER.error("Error : ", e);
			throw e;
		}
	}
	
	
	private ValueObject	runThreadForVehicleGroupWiseProfitAndLossReport(ValueObject	valueObject) throws Exception {
		Thread											expensesThread			= null;
		Thread											incomeThread			= null;
		Thread											reminderThread			= null;
		Thread											tyreThread				= null;
		Thread											batteryThread			= null;
		Thread											tripSheetThread			= null;
		Thread											EMIThread				= null;
		ValueObject										valueOutObject;
		try {
			valueOutObject		= new ValueObject();
			
			final	Timestamp			startDateOfMonth	= (Timestamp) valueObject.get("startDateOfMonth");
			final	CustomUserDetails	userDetails			= (CustomUserDetails) valueObject.get("userDetails");
			final	int					tripFlavor  		= valueObject.getInt("tripFlavor");
			final	Timestamp			fromDate			= (Timestamp) valueObject.get("fromDate");
			final	Timestamp			toDate				= (Timestamp) valueObject.get("toDate");
			
			String			toEndDate	=	DateTimeUtility.getEndOfDayDateStr(DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD), DateTimeUtility.YYYY_MM_DD);
			
			
			expensesThread	= new Thread() {
				public void run() {
					List<MonthWiseVehicleExpenseDto>				expenseDtoList			= null;
					try {
						expenseDtoList			=	vehicleProfitAndLossService.getMonthWiseGroupExpenseByVid(valueObject.getLong("vehicleGroupId", 0), startDateOfMonth, userDetails.getCompany_id());
						valueOutObject.put("expenseDtoList", expenseDtoList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			expensesThread.start();
			
			incomeThread	= new Thread() {
				public void run() {
					List<VehicleProfitAndLossReportDto> 			incomeList				= null;
					try {
						incomeList				= 	vehicleProfitAndLossService.getMonthIncomeDetailsByVehicleGroupId(valueObject.getLong("vehicleGroupId", 0), startDateOfMonth, userDetails.getCompany_id());
						valueOutObject.put("incomeList", incomeList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			incomeThread.start();
			
			reminderThread	= new Thread() {
				public void run() {
					List<RenewalReminderDto>						renewalList				= null;
					try {
						renewalList				= 	renewalReminderService.getGroupRenewalExpenses(valueObject.getLong("vehicleGroupId", 0), startDateOfMonth, userDetails.getCompany_id(),toEndDate);
						valueOutObject.put("renewalList", renewalList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			reminderThread.start();
			
			tyreThread	= new Thread() {
				public void run() {
					List<VehicleTyreLayoutPositionDto>						vehicleTyreList				= null;
					try {
						vehicleTyreList			=	vehicleTyreLayoutService.getGroupTyreExpenseDetailsList(valueObject.getLong("vehicleGroupId", 0), userDetails.getCompany_id());
						valueOutObject.put("vehicleTyreList", vehicleTyreList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tyreThread.start();
			
			batteryThread	= new Thread() {
				public void run() {
					List<BatteryDto>								batteryList				= null;
					try {
						batteryList				=	batteryService.getGroupVehicleBatteryListForCost(valueObject.getLong("vehicleGroupId", 0));
						valueOutObject.put("batteryList", batteryList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			batteryThread.start();
			
			EMIThread	= new Thread() {
				public void run() {
					List<VehicleEmiPaymentDetailsDto>		vehicleEMIList			= null;
					try {
						vehicleEMIList			= 	vehicleEmiDetailsService.getGroupVehicleEMIForMonth(startDateOfMonth,toDate, valueObject.getLong("vehicleGroupId", 0));
						valueOutObject.put("vehicleEMIList", vehicleEMIList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			
			EMIThread.start();
			
			
			tripSheetThread	= new Thread() {
				public void run() {
					List<TripSheetDto> 								tripSheetList			= null;
					try {
						if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
							tripSheetList			= 	tripSheetService.getTripSheetDetailsInMonthByVehicleGroupId(valueObject.getLong("vehicleGroupId", 0), fromDate, toDate);	
						}else if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
							tripSheetList			= 	tripDailySheetService.getTripSheetDetailsInMonthByVehicleGroupId(valueObject.getLong("vehicleGroupId", 0), fromDate, toDate);	
						}
						valueOutObject.put("tripSheetList", tripSheetList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tripSheetThread.start();
			
			
			expensesThread.join();
			incomeThread.join();
			reminderThread.join();
			tyreThread.join();
			batteryThread.join();
			if(valueObject.getBoolean("vehicleMonthlyEMIPayment", false) == false) {
			EMIThread.join();
			}
			tripSheetThread.join();
			
			return valueOutObject;
		} catch (Exception e) {
			LOGGER.error("Error : ", e);
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleIncomeDetailsOfMonthByIncomeId(ValueObject valueObject) throws Exception {
		List<TripSheetIncomeDto>		incomeList			= null;
		TripSheetIncomeDto				incomeDto			= null;
		Timestamp						startDateOfMonth	= null;
		CustomUserDetails				userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			incomeDto	= new TripSheetIncomeDto();
			
			startDateOfMonth		= DateTimeUtility.getTimeStamp(valueObject.getString("dateRange"), DateTimeUtility.DD_MM_YY);
			
			incomeDto.setVid(valueObject.getInt("vid", 0));
			incomeDto.setCompanyId(userDetails.getCompany_id());
			incomeDto.setIncomeId(valueObject.getInt("incomeId", 0));
			incomeDto.setFromDate(DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getFirstDayOfMonth(startDateOfMonth), DateTimeUtility.YYYY_MM_DD));
			incomeDto.setToDate(DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getLastDayOfMonth(startDateOfMonth), DateTimeUtility.YYYY_MM_DD));
			
			
			incomeList	= vehicleProfitAndLossService.getVehicleIncomeDetailsOfMonthByIncomeId(incomeDto);
			
			valueObject.put("incomeList", incomeList);
			
			return valueObject;
		} catch (Exception e) {
			throw	e;
		}finally {
			incomeList			= null;
			incomeDto			= null;
			startDateOfMonth	= null;
			userDetails			= null;
		}
	}
	
	@Override
	public ValueObject getVehicleExpenseDetailsOfMonthByExpenseId(ValueObject valueObject) throws Exception {
		List<TripSheetExpenseDto>				expenseList			= null;
		TripSheetIncomeDto						incomeDto			= null;
		Timestamp								startDateOfMonth	= null;
		Timestamp								fromDateTimestamp	= null;
		Timestamp								toDateTimeStamp		= null;
		CustomUserDetails						userDetails			= null;
		short									expenseType			= 0;
		List<FuelDto>							fuelList			= null;
		List<ServiceEntriesDto> 				serviceList			= null;
		List<DealerServiceEntriesDto> 			dealerServiceList			= null;
		List<WorkOrdersDto> 					workOrdersList		= null;
		List<VehicleTyreLayoutPositionDto>		tyreList			= null;
		Timestamp								countStartDate  	= null;
		Timestamp								countEndDate  		= null;
		
		try {
			
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String,Object> vehicleConfig =companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			incomeDto	= new TripSheetIncomeDto();
			
			startDateOfMonth		= DateTimeUtility.getTimeStamp(valueObject.getString("dateRange"), DateTimeUtility.DD_MM_YY);
			expenseType				= valueObject.getShort("expenseType", (short) 0);
			fromDateTimestamp		= DateTimeUtility.getFirstDayOfMonth(startDateOfMonth); 
			toDateTimeStamp			= DateTimeUtility.getLastDayOfMonth(startDateOfMonth);
			
			incomeDto.setVid(valueObject.getInt("vid", 0));
			incomeDto.setCompanyId(userDetails.getCompany_id());
			incomeDto.setIncomeId(valueObject.getInt("expenseId", 0));
			incomeDto.setFromDate(DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getFirstDayOfMonth(startDateOfMonth), DateTimeUtility.YYYY_MM_DD));
			String toDate = DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getLastDayOfMonth(startDateOfMonth), DateTimeUtility.YYYY_MM_DD);
			incomeDto.setToDate(toDate + DateTimeUtility.DAY_END_TIME);
			
			if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL) {
				fuelList	= fuelService.getFuelListByVidAndDateRange(incomeDto);
				valueObject.put("fuelList", fuelList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
				serviceList	= serviceEntriesService.getServiceEntriesServiceListOfMonth(incomeDto);
				valueObject.put("serviceList", serviceList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER) {
				workOrdersList	= workOrdersService.getWorkOrdereListOfMonthByVid(incomeDto);
				valueObject.put("workOrdersList", workOrdersList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
				expenseList	= vehicleProfitAndLossService.getVehicleExpenseDetailsOfMonthByExpennseId(incomeDto);
				valueObject.put("expenseList", expenseList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_RENEWAL) {
				
				RenewalReminderDto	reminderDto	= RRBL.GetRenewalReminder(renewalReminderService.getRenewalReminderById(valueObject.getLong("expenseId", 0), userDetails));
				long	noOfRenewalDays = DateTimeUtility.getDayDiffBetweenTwoDates(new Timestamp(reminderDto.getRenewal_from_Date().getTime()), new Timestamp(reminderDto.getRenewal_To_Date().getTime()));
				
				if(reminderDto.getRenewal_from_Date().after(fromDateTimestamp)) {
					countStartDate = new Timestamp(reminderDto.getRenewal_from_Date().getTime());
				}else {
					countStartDate = fromDateTimestamp;
				}
				
				if(reminderDto.getRenewal_To_Date().before(toDateTimeStamp)) {
					countEndDate = new Timestamp(reminderDto.getRenewal_To_Date().getTime());
				}else {
					countEndDate = toDateTimeStamp;
				}
				
				long daysInMonth	 = DateTimeUtility.getDayDiffBetweenTwoDates(countStartDate, countEndDate);
				
			//	long daysInMonth	 = DateTimeUtility.getNoOfDaysInMonth(startDateOfMonth);
				
				
				double	amountForMonth = 0;
					if(reminderDto.getRenewal_Amount() != null)
						amountForMonth = (reminderDto.getRenewal_Amount() / noOfRenewalDays)*daysInMonth;
				
				valueObject.put("renewal", reminderDto);
				valueObject.put("amountForMonth", amountForMonth);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE) {
				if((boolean) vehicleConfig.getOrDefault(vehicleConfig, false)) {
					
				int	tripFlavor		= companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				tyreList	=	vehicleTyreLayoutService.getVehicleTyreExpenseDetailsList(valueObject.getInt("vid", 0), userDetails.getCompany_id());
				Integer	totalRunKM	= 0;
				if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					totalRunKM	=	tripSheetService.getVehicleRunKMByVid(valueObject.getInt("vid", 0), startDateOfMonth, DateTimeUtility.getLastDayOfMonth(startDateOfMonth));	
				}else if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
					totalRunKM	=	tripSheetService.getVehicleRunKMByVid(valueObject.getInt("vid", 0), startDateOfMonth, DateTimeUtility.getLastDayOfMonth(startDateOfMonth));	
				}
				valueObject.put("tyreList", tyreList);
				valueObject.put("totalRunKM", totalRunKM);
				
				}else {
					valueObject.put("vehicleTyreMountCostInPLReport",  vehicleConfig.getOrDefault("vehicleTyreMountCostInPLReport", false));
					valueObject.put("tyreList", getTyreDetailsForPL(startDateOfMonth+"", DateTimeUtility.getLastDayOfMonth(startDateOfMonth)+"", valueObject.getInt("vid", 0)));
				}
				
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY) {
				List<BatteryDto>	batteryList				=	batteryService.getVehicleBatteryListForCost(valueObject.getInt("vid", 0));
				valueObject.put("noOfDaysInMOnth", DateTimeUtility.getNoOfDaysInMonth(startDateOfMonth));
				valueObject.put("batteryList", batteryList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI) {
				
				List<VehicleEmiPaymentDetailsDto> vehicleEMIList = vehicleEmiDetailsService.getVehicleEMIPaymentDetailsForMonth(fromDateTimestamp, toDateTimeStamp, valueObject.getInt("vid", 0));
				valueObject.put("vehicleEMIList", vehicleEMIList);
				
				List<VehicleEmiDetailDto> downPaymentList = vehicleEmiDetailsService.getVehicleEMIForMonth(fromDateTimestamp, toDateTimeStamp, valueObject.getInt("vid", 0));
				valueObject.put("downPaymentList", downPaymentList);		
				
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY) {
				List<DriverAttendanceDto>	attList	= vehicleProfitAndLossService.getDriverAttandanceOfMonth(incomeDto);
				
				valueObject.put("attList", attList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA) {
				List<UreaEntriesDto>	ureaList	= ureaEntriesService.getUreaEntriesDetailsByDateRange(valueObject.getInt("vid", 0), incomeDto.getFromDate(), incomeDto.getToDate());
				valueObject.put("ureaList", ureaList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES) {
				dealerServiceList	= dealerServiceEntriesService.getDealerServiceEntriesServiceListOfMonth(incomeDto);
				valueObject.put("serviceList", serviceList);
			}
			else if(expenseType == VehicleExpenseTypeConstant.INSPECTION_PENALTY){
				List<VehicleInspectionCompletionDetailsDto> inspectionPenalty=inspectionCompletionDetailsService.getDateWiseInspectedList(userDetails.getCompany_id(), valueObject.getInt("vid", 0), fromDateTimestamp, toDateTimeStamp);
				valueObject.put("inspectionPenalty", inspectionPenalty);
			}
			
			//expenseList	= vehicleProfitAndLossService.getVehicleIncomeDetailsOfMonthByIncomeId(incomeDto);
			
			
			
			return valueObject;
		} catch (Exception e) {
			throw	e;
		}finally {
			expenseList			= null;
			incomeDto			= null;
			startDateOfMonth	= null;
			userDetails			= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject getDateWiseVehicleProfitAndLoss(ValueObject valueObject) throws Exception {
		List<DateWiseVehicleExpenseDto>					expenseDtoList			= null;
		List<DateWiseVehicleExpenseDto>					tripExpenseList			= null;
		CustomUserDetails								userDetails				= null;
		Timestamp										startDateOfMonth		= null;
		List<RenewalReminderDto>						renewalList				= null;
		ValueObject										valueOutObject			= null;
		List<VehicleProfitAndLossReportDto> 			incomeList				= null;
		List<VehicleTyreLayoutPositionDto>				vehicleTyreList			= null;
		VehicleOdometerHistory							vehicleOdometerHistory	= null;
		List<BatteryDto>								batteryList				= null;
		List<TripSheetDto> 								tripSheetList			= null;
		String											fromDate				= null;
		String											toDate					= null;
		int												tripFlavor				= 0;
		ValueObject										outObject				= null;
		ValueObject										dateRangeObj			= null;
		boolean											showDriverMonthlySalary	= false;	
		HashMap<String, Object>							configDriverSalary		= null;
		boolean											vehicleMonthlyEMIPayment	= false;	
		List<VehicleEmiDetails> 						vehicleEMIList			= null;
		try {
			valueOutObject	= new ValueObject();
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configDriverSalary			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			showDriverMonthlySalary		= (boolean) configDriverSalary.getOrDefault(VehicleConfigurationContant.SHOW_DRIVER_MONTHLY_SALARY, false);
			vehicleMonthlyEMIPayment 	= (boolean) configDriverSalary.getOrDefault(VehicleConfigurationContant.VEHICLE_MONTHLY_EMI_PAYMENT, false);
			dateRangeObj	= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
			fromDate		= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate			= dateRangeObj.getString(DateTimeUtility.TO_DATE);
			tripFlavor		= companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			
			valueObject.put("userDetails", userDetails);
			valueObject.put("tripFlavor", tripFlavor);
			valueObject.put("fromDate", fromDate);
			valueObject.put("toDate", toDate);
			valueObject.put("vehicleMonthlyEMIPayment", vehicleMonthlyEMIPayment);
			
			outObject	=	runThreadForDateWiseVehicleProfitAndLossReport(valueObject);
			
			
			expenseDtoList			=	(List<DateWiseVehicleExpenseDto>) outObject.get("expenseDtoList");
			tripExpenseList			=	(List<DateWiseVehicleExpenseDto>) outObject.get("tripExpenseList");
			renewalList				= 	(List<RenewalReminderDto>) outObject.get("renewalList");
			vehicleTyreList			=	(List<VehicleTyreLayoutPositionDto>) outObject.get("vehicleTyreList");
			batteryList				=	(List<BatteryDto>) outObject.get("batteryList");
			tripSheetList			= 	(List<TripSheetDto>) outObject.get("tripSheetList");
			incomeList				= 	(List<VehicleProfitAndLossReportDto>) outObject.get("incomeList");
			vehicleEMIList			= 	(List<VehicleEmiDetails>) outObject.get("vehicleEMIList");
			
			
			valueOutObject.put("expenseDtoList", expenseDtoList);
			valueOutObject.put("tripExpenseList", tripExpenseList);
			valueOutObject.put("startDateOfMonth", startDateOfMonth);
			valueOutObject.put("renewalList", renewalList);
			valueOutObject.put("vehicleTyreList", vehicleTyreList);
			valueOutObject.put("vehicleOdometerHistory", vehicleOdometerHistory);
			valueOutObject.put("batteryList", batteryList);
			valueOutObject.put("tripFlavor", tripFlavor);
			valueOutObject.put("fromDate", fromDate);
			valueOutObject.put("toDate", toDate);
			valueOutObject.put("showDriverMonthlySalary", showDriverMonthlySalary); 
			valueOutObject.put("driverSalaryFromVehicle", vehicleService.Get_Vehicle_Header_Details(valueObject.getInt("vid", 0))); 
			valueOutObject.put("vehicleEMIList", vehicleEMIList);
			valueOutObject.put("totalRunningKM", tripSheetService.getVehicleRunKMByVid(valueObject.getInt("vid", 0), DateTimeUtility.getTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)));	
			
			valueObject.put("expenseDtoList", getFinalListOfExpenseForDateWiseUI(valueOutObject));
			valueObject.put("incomeList", incomeList);
			valueObject.put("vehicle", vehicleService.Get_Vehicle_Header_Details(valueObject.getInt("vid", 0)));
			valueObject.put("tripFlavor", tripFlavor);
			valueObject.put("showDriverMonthlySalary", showDriverMonthlySalary);
			valueObject.put("fromVehicleProfitLoss", true);
			getTripSheetDetailsDateWise(tripSheetList, valueObject);
			
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
		expenseDtoList			= null;
		userDetails				= null;
		startDateOfMonth		= null;
		renewalList				= null;
		valueOutObject			= null;
		incomeList				= null;
		vehicleTyreList			= null;
		vehicleOdometerHistory	= null;
		batteryList				= null;
		tripSheetList			= null;}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject getVehicleProfitAndLossWithinRange(ValueObject valueObject) throws Exception {
		List<DateWiseVehicleExpenseDto>					expenseDtoList			= null;
		List<DateWiseVehicleExpenseDto>					tripExpenseList			= null;
		CustomUserDetails								userDetails				= null;
		Timestamp										startDateOfMonth		= null;
		List<RenewalReminderDto>						renewalList				= null;
		ValueObject										valueOutObject			= null;
		List<VehicleProfitAndLossReportDto> 			incomeList				= null;
		List<VehicleTyreLayoutPositionDto>				vehicleTyreList			= null;
		VehicleOdometerHistory							vehicleOdometerHistory	= null;
		List<BatteryDto>								batteryList				= null;
		List<TripSheetDto> 								tripSheetList			= null;
		String											fromDate				= null;
		String											toDate					= null;
		int												tripFlavor				= 0;
		ValueObject										outObject				= null;
		ValueObject										dateRangeObj			= null;
		boolean											showDriverMonthlySalary	= false;	
		HashMap<String, Object>							configDriverSalary		= null;
		List<VehicleEmiDetails>							vehicleEMIList			= null;
		boolean											vehicleMonthlyEMIPayment	= false;	
		try {
			
			valueOutObject	= new ValueObject();
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configDriverSalary			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			showDriverMonthlySalary		= (boolean) configDriverSalary.getOrDefault(VehicleConfigurationContant.SHOW_DRIVER_MONTHLY_SALARY, false);
			vehicleMonthlyEMIPayment 	= (boolean) configDriverSalary.getOrDefault(VehicleConfigurationContant.VEHICLE_MONTHLY_EMI_PAYMENT, false);
			dateRangeObj	= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
			fromDate		= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate			= dateRangeObj.getString(DateTimeUtility.TO_DATE);
			tripFlavor		= companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			fromDate = DateTimeUtility.getSqlStrDateFromStrDate(fromDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			toDate   = DateTimeUtility.getSqlStrDateFromStrDate(toDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			
			valueObject.put("userDetails", userDetails);
			valueObject.put("tripFlavor", tripFlavor);
			valueObject.put("fromDate", fromDate);
			valueObject.put("toDate", toDate);
			valueObject.put("vehicleMonthlyEMIPayment", vehicleMonthlyEMIPayment);
			valueObject.put("vehicleTyreMountCostInPLReport", configDriverSalary.getOrDefault("vehicleTyreMountCostInPLReport",false));
			
			outObject	=	runThreadForDateRangeWiseVehicleProfitAndLossReport(valueObject);
			
			
			expenseDtoList			=	(List<DateWiseVehicleExpenseDto>) outObject.get("expenseDtoList");
			tripExpenseList			=	(List<DateWiseVehicleExpenseDto>) outObject.get("tripExpenseList");
			renewalList				= 	(List<RenewalReminderDto>) outObject.get("renewalList");
			vehicleTyreList			=	(List<VehicleTyreLayoutPositionDto>) outObject.get("vehicleTyreList");
			batteryList				=	(List<BatteryDto>) outObject.get("batteryList");
			tripSheetList			= 	(List<TripSheetDto>) outObject.get("tripSheetList");
			vehicleEMIList			= 	(List<VehicleEmiDetails>) outObject.get("vehicleEMIList");
			
			incomeList				= 	(List<VehicleProfitAndLossReportDto>) outObject.get("incomeList");
			valueOutObject.put("expenseDtoList", expenseDtoList);
			valueOutObject.put("vehicleEMIList", vehicleEMIList);
			valueOutObject.put("tripExpenseList", tripExpenseList);
			valueOutObject.put("startDateOfMonth", startDateOfMonth);
			valueOutObject.put("renewalList", renewalList);
			valueOutObject.put("vehicleTyreList", vehicleTyreList);
			valueOutObject.put("vehicleOdometerHistory", vehicleOdometerHistory);
			valueOutObject.put("batteryList", batteryList);
			valueOutObject.put("tripFlavor", tripFlavor);
			valueOutObject.put("fromDate", fromDate);
			valueOutObject.put("toDate", toDate);
			valueOutObject.put("showDriverMonthlySalary", showDriverMonthlySalary); 
			valueOutObject.put("driverSalaryFromVehicle", vehicleService.Get_Vehicle_Header_Details(valueObject.getInt("vid", 0))); 
			valueOutObject.put("vehicleMonthlyEMIPayment", vehicleMonthlyEMIPayment);
			valueOutObject.put("totalRunningKM", tripSheetService.getVehicleRunKMByVid(valueObject.getInt("vid", 0), DateTimeUtility.getTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)));	
			valueOutObject.put("vehicleTyreMountCostInPLReport", configDriverSalary.getOrDefault("vehicleTyreMountCostInPLReport",false));
			valueOutObject.put("tyreMountList",outObject.get("tyreMountList"));
			valueObject.put("expenseDtoList", getFinalListOfExpenseForDateWiseUI(valueOutObject));
			valueObject.put("incomeList", incomeList);
			valueObject.put("vehicle", vehicleService.Get_Vehicle_Header_Details(valueObject.getInt("vid", 0)));
			valueObject.put("tripFlavor", tripFlavor);
			valueObject.put("showDriverMonthlySalary", showDriverMonthlySalary);
			getTripSheetDetailsDateWise(tripSheetList, valueObject);
			
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
		expenseDtoList			= null;
		userDetails				= null;
		startDateOfMonth		= null;
		renewalList				= null;
		valueOutObject			= null;
		incomeList				= null;
		vehicleTyreList			= null;
		vehicleOdometerHistory	= null;
		batteryList				= null;
		tripSheetList			= null;}
	}
	
	private ValueObject	runThreadForDateWiseVehicleProfitAndLossReport(ValueObject	valueObject) throws Exception {
		Thread											expensesThread			= null;
		Thread											incomeThread			= null;
		Thread											reminderThread			= null;
		Thread											tyreThread				= null;
		Thread											batteryThread			= null;
		Thread											tripSheetThread			= null;
		Thread											tripExpenseThread		= null;
		Thread											EMIThread				= null;
		ValueObject										valueOutObject;
		try {
			valueOutObject		= new ValueObject();
			final	CustomUserDetails	userDetails   = (CustomUserDetails) valueObject.get("userDetails");
			final	int					tripFlavor    = valueObject.getInt("tripFlavor");
			final	String				fromDate	  = (String) valueObject.get("fromDate");
			final	String				toDate		  = (String) valueObject.get("toDate");
			expensesThread	= new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				expenseDtoList			= null;
					try {
						expenseDtoList			=	vehicleProfitAndLossService.getDateWiseVehicleExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate, toDate, userDetails.getCompany_id());
						valueOutObject.put("expenseDtoList", expenseDtoList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			expensesThread.start();
			
			tripExpenseThread = new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				tripExpenseList			= null;
					try {
						tripExpenseList			=	vehicleProfitAndLossService.getDateWiseVehicleTripExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate, toDate);
						
						valueOutObject.put("tripExpenseList", tripExpenseList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tripExpenseThread.start();
			
			incomeThread	= new Thread() {
				public void run() {
					List<VehicleProfitAndLossReportDto> 			incomeList				= null;
					try {
						incomeList				= 	vehicleProfitAndLossService.getIncomeDetailsForDateRange(valueObject.getInt("vid", 0), fromDate, toDate, userDetails.getCompany_id());
						valueOutObject.put("incomeList", incomeList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			incomeThread.start();
			
			reminderThread	= new Thread() {
				public void run() {
					List<RenewalReminderDto>						renewalList				= null;
					try {
						renewalList				= 	renewalReminderService.getVehicleRenewalExpensesDateRange(valueObject.getInt("vid", 0), fromDate, toDate);
						valueOutObject.put("renewalList", renewalList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			reminderThread.start();
			tyreThread	= new Thread() {
				public void run() {
					List<VehicleTyreLayoutPositionDto>						vehicleTyreList				= null;
					try {
						vehicleTyreList			=	vehicleTyreLayoutService.getVehicleTyreExpenseDetailsList(valueObject.getInt("vid", 0), userDetails.getCompany_id());
						
						valueOutObject.put("vehicleTyreList", vehicleTyreList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tyreThread.start();
			
			batteryThread	= new Thread() {
				public void run() {
					List<BatteryDto>								batteryList				= null;
					try {
						batteryList				=	batteryService.getVehicleBatteryListForCost(valueObject.getInt("vid", 0));
						valueOutObject.put("batteryList", batteryList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			batteryThread.start();
			
			tripSheetThread	= new Thread() {
				public void run() {
					List<TripSheetDto> 								tripSheetList			= null;
					try {
						if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
							tripSheetList		= 	tripSheetService.getTripSheetDetailsInMonthByVid(valueObject.getInt("vid", 0), DateTimeUtility.getTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));	
						}else if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
							tripSheetList		= 	tripDailySheetService.getTripSheetDetailsInMonthByVId(valueObject.getInt("vid", 0), DateTimeUtility.getTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));	
						}
						valueOutObject.put("tripSheetList", tripSheetList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tripSheetThread.start();
			
			if(valueObject.getBoolean("vehicleMonthlyEMIPayment", false) == false) {
			
			EMIThread	= new Thread() {
				public void run() {
					List<VehicleEmiPaymentDetailsDto>		vehicleEMIList			= null;
					try {
						vehicleEMIList			= 	vehicleEmiDetailsService.getVehicleEMIForDateRange(fromDate,toDate,valueObject.getInt("vid", 0));
						
						
						valueOutObject.put("vehicleEMIList", vehicleEMIList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			
			EMIThread.start();
			
			}
			expensesThread.join();
			tripExpenseThread.join();
			incomeThread.join();
			reminderThread.join();
			tyreThread.join();
			batteryThread.join();
			tripSheetThread.join();
			if(valueObject.getBoolean("vehicleMonthlyEMIPayment", false) == false) {
				EMIThread.join();	
			}
			
			return valueOutObject;
		} catch (Exception e) {
			LOGGER.error("Error : ", e);
			throw e;
		}
	}
	
	
	@Override
	public ValueObject getVehicleExpenseDetailsDateRangeExpenseId(ValueObject valueObject) throws Exception {
		List<TripSheetExpenseDto>				expenseList			= null;
		TripSheetIncomeDto						incomeDto			= null;
		CustomUserDetails						userDetails			= null;
		short									expenseType			= 0;
		List<FuelDto>							fuelList			= null;
		List<ServiceEntriesDto> 				serviceList			= null;
		List<DealerServiceEntriesDto> 			dealerServiceList			= null;
		List<WorkOrdersDto> 					workOrdersList		= null;
		List<VehicleTyreLayoutPositionDto>		tyreList			= null;
		Timestamp								countStartDate		= null;
		Timestamp								countEndDate		= null;
		double									amountForMonth 		= 0;
		try {
			
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String,Object> vehicleConfig =companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			incomeDto	= new TripSheetIncomeDto();
			expenseType				= valueObject.getShort("expenseType", (short) 0);
			
			incomeDto.setVid(valueObject.getInt("vid", 0));
			incomeDto.setCompanyId(userDetails.getCompany_id());
			incomeDto.setIncomeId(valueObject.getInt("expenseId", 0));
			incomeDto.setFromDate(valueObject.getString("fromDate") + DateTimeUtility.DAY_START_TIME);
			String toDate = valueObject.getString("toDate");
			incomeDto.setToDate(toDate + DateTimeUtility.DAY_END_TIME);
			
			Date parsedFromDate = dateFormat.parse(valueObject.getString("fromDate"));
		    Timestamp fromTimestamp = new java.sql.Timestamp(parsedFromDate.getTime());
		    
		    Date parsedToDate = dateFormat.parse(valueObject.getString("toDate"));
		    Timestamp toTimestamp = new java.sql.Timestamp(parsedToDate.getTime());
			
			long daysInMonth = DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getTimeStamp(incomeDto.getFromDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(incomeDto.getToDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			
			if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL) {
				fuelList	= fuelService.getFuelListByVidAndDateRange(incomeDto);
				valueObject.put("fuelList", fuelList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
				serviceList	= serviceEntriesService.getServiceEntriesServiceListOfMonth(incomeDto);
				valueObject.put("serviceList", serviceList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER) {
				workOrdersList	= workOrdersService.getWorkOrdereListOfMonthByVid(incomeDto);
				valueObject.put("workOrdersList", workOrdersList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
				expenseList	= vehicleProfitAndLossService.getVehicleExpenseDetailsOfMonthByExpennseId(incomeDto);
				valueObject.put("expenseList", expenseList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_RENEWAL) {
				
				RenewalReminderDto	reminderDto	= RRBL.GetRenewalReminder(renewalReminderService.getRenewalReminderById(valueObject.getLong("expenseId", 0), userDetails));
				
				if(reminderDto != null) {
					if(reminderDto.getRenewal_from_Date().after(fromTimestamp)) {
						countStartDate = new Timestamp(reminderDto.getRenewal_from_Date().getTime());
					}else {
						countStartDate = fromTimestamp;
					}
				
					if(reminderDto.getRenewal_To_Date().before(toTimestamp)) {
						countEndDate = new Timestamp(reminderDto.getRenewal_To_Date().getTime());
					}else {
						countEndDate = toTimestamp;
					}
				
					long	noOfRenewalDays 		= DateTimeUtility.getDayDiffBetweenTwoDates(new Timestamp(reminderDto.getRenewal_from_Date().getTime()), new Timestamp(reminderDto.getRenewal_To_Date().getTime()));
					long 	daysInMonthForRR	 	= DateTimeUtility.getDayDiffBetweenTwoDates(countStartDate, countEndDate);
				
				
					if(reminderDto.getRenewal_Amount() != null) {
						amountForMonth = (reminderDto.getRenewal_Amount() / noOfRenewalDays)*daysInMonthForRR;
					}
				}
				valueObject.put("renewal", reminderDto);
				valueObject.put("amountForMonth", amountForMonth);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE) {
				if(!(boolean) vehicleConfig.getOrDefault("vehicleTyreMountCostInPLReport", false)) {
			int	tripFlavor		= companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				
				tyreList	=	vehicleTyreLayoutService.getVehicleTyreExpenseDetailsList(valueObject.getInt("vid", 0), userDetails.getCompany_id());
				Integer	totalRunKM	= 0;
				if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					totalRunKM	=	tripSheetService.getVehicleRunKMByVid(valueObject.getInt("vid", 0), DateTimeUtility.getTimeStamp(incomeDto.getFromDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(incomeDto.getToDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS));	
				}else if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
					totalRunKM	=	tripSheetService.getVehicleRunKMByVid(valueObject.getInt("vid", 0), DateTimeUtility.getTimeStamp(incomeDto.getFromDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(incomeDto.getToDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS));	
				}
				valueObject.put("tyreList", tyreList);
				valueObject.put("totalRunKM", totalRunKM);
				}else {
					valueObject.put("vehicleTyreMountCostInPLReport",  vehicleConfig.getOrDefault("vehicleTyreMountCostInPLReport", false));
					valueObject.put("tyreList", getTyreDetailsForPL(valueObject.getString("fromDate"), toDate, valueObject.getInt("vid", 0)));
				}
				
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY) {
				List<BatteryDto>	batteryList				=	batteryService.getVehicleBatteryListForCost(valueObject.getInt("vid", 0));
				valueObject.put("noOfDaysInMOnth", daysInMonth);
				valueObject.put("batteryList", batteryList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI) {
				
				List<VehicleEmiPaymentDetailsDto> vehicleEMIList = 	vehicleEmiDetailsService.getVehicleEMIPaymentDetailsForMonth(fromTimestamp, toTimestamp, valueObject.getInt("vid", 0));
				valueObject.put("vehicleEMIList", vehicleEMIList);
				
				List<VehicleEmiDetailDto> downPaymentList = vehicleEmiDetailsService.getVehicleEMIForMonth(fromTimestamp, toTimestamp, valueObject.getInt("vid", 0));
				valueObject.put("downPaymentList", downPaymentList);
				
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY) {
				List<DriverAttendanceDto>	attList	= vehicleProfitAndLossService.getDriverAttandanceOfMonth(incomeDto);
				
				valueObject.put("attList", attList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA) {
				List<UreaEntriesDto> 	ureaList	= ureaEntriesService.getUreaEntriesDetailsByDateRange(valueObject.getInt("vid", 0), incomeDto.getFromDate(), incomeDto.getToDate());
				valueObject.put("ureaList", ureaList);
			}else if(expenseType == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES) {
				dealerServiceList	= dealerServiceEntriesService.getDealerServiceEntriesServiceListOfMonth(incomeDto);
				valueObject.put("dealerServiceList", dealerServiceList);
			}
			
			else if(expenseType == VehicleExpenseTypeConstant.INSPECTION_PENALTY){
				List<VehicleInspectionCompletionDetailsDto> inspectionPenalty=inspectionCompletionDetailsService.getDateWiseInspectedList(userDetails.getCompany_id(), valueObject.getInt("vid", 0), fromTimestamp, toTimestamp);
				valueObject.put("inspectionPenalty", inspectionPenalty);
			}
			//expenseList	= vehicleProfitAndLossService.getVehicleIncomeDetailsOfMonthByIncomeId(incomeDto);
			
			
			
			return valueObject;
		} catch (Exception e) {
			throw	e;
		}finally {
			expenseList			= null;
			incomeDto			= null;
			userDetails			= null;
		}
	}
	
	
	
	@Override
	public ValueObject getRouteWiseProfitAndLoss(ValueObject valueObject) throws Exception {		
		ValueObject							valueOutObject					= null;
		CustomUserDetails 					userDetails						= null;
		ValueObject						    tableConfig						= null;
		ValueObject							dateRangeObj					= null;
		String								fromDate						= null;
		String								toDate							= null;
		Integer								routeId							= 0;
		int									tripFlavor						= 0;
		List<TripSheetDto>					tripSheetDtoList				= null;
		List<FuelDto>						fueltDtoList					= null;
		List<TollExpensesDetailsDto>		tolltDtoList					= null;
		HashMap<Integer,TripSheetDto>		tripSheetDtoMap					= null;
		TripSheetDto						tripSheetDto					= null;
		String 								routeID							= "";	
		String 								routeIDD						= "";	
		String 								routeIDF						= "";	
		String 								routeIDT						= "";
		
		try {
			valueOutObject 		= new ValueObject();
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			routeId				= valueObject.getInt("routeId", 0);
			
			if(routeId > 0) {
				routeID		= " AND T.routeID = "+routeId+" ";
				routeIDD	= " AND T.TRIP_ROUTE_ID = "+routeId+" ";
				routeIDF	= " AND T.routeID = "+routeId+" ";
				routeIDT	= " AND TR.routeID = "+routeId+" ";
			}
			
			dateRangeObj		= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
			fromDate			= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate				= dateRangeObj.getString(DateTimeUtility.TO_DATE);
			
			fromDate = DateTimeUtility.getSqlStrDateFromStrDate(fromDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			toDate   = DateTimeUtility.getSqlStrDateFromStrDate(toDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			
			tripFlavor			= companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);			
				
			if (tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				tripSheetDtoList	= profitAndLossDao.getRouteWiseTripsheetExpense(routeID, fromDate, toDate, userDetails);
			}else {
				tripSheetDtoList	= profitAndLossDao.getRouteWiseTripsDailyExpense(routeIDD, fromDate, toDate, userDetails);
			}
			fueltDtoList			= fuelService.getRouteWiseTripsheetfuelExpense(routeIDF, fromDate, toDate, userDetails,tripFlavor);
			tolltDtoList			= tollExpensesDetailsService.getRouteWiseTripsheetTollExpenseAmount(routeIDT,fromDate, toDate, userDetails,tripFlavor);
			
			
			tripSheetDtoMap			= new HashMap<>();
			if(tripSheetDtoList != null) {
				
				for(TripSheetDto dto : tripSheetDtoList) {
					if(tripSheetDtoMap.containsKey(dto.getRouteID())) {
						
						tripSheetDto	= tripSheetDtoMap.get(dto.getRouteID());
						tripSheetDto.setExpenseAmount(tripSheetDto.getExpenseAmount()+dto.getExpenseAmount());
						tripSheetDto.setTripTotalincome(tripSheetDto.getTripTotalincome()+dto.getTripTotalincome());
						tripSheetDto.setTripTotalUsageKM(tripSheetDto.getTripTotalUsageKM()+dto.getTripTotalUsageKM());
						tripSheetDto.setTripSheetCount(tripSheetDto.getTripSheetCount() + 1);
						
					} else {
						dto.setTripSheetCount((long)1);
						tripSheetDtoMap.put(dto.getRouteID(), dto);
					}
				}
			}
			
			if(fueltDtoList != null) {
				for(FuelDto dto : fueltDtoList ) {
					if(tripSheetDtoMap.containsKey(dto.getRouteID())) {
						tripSheetDtoMap.get(dto.getRouteID()).setFuelExpenseAmount(Double.parseDouble(toFixedTwo.format(tripSheetDtoMap.get(dto.getRouteID()).getFuelExpenseAmount() + dto.getFuel_amount())) );
						tripSheetDtoMap.get(dto.getRouteID()).setFuel_liters(tripSheetDtoMap.get(dto.getRouteID()).getFuel_liters() + dto.getFuel_liters());
					} 
				}
			}
			if(tolltDtoList != null) {
				for(TollExpensesDetailsDto dto : tolltDtoList ) {
					if(tripSheetDtoMap.containsKey(dto.getRouteID())) {
						tripSheetDtoMap.get(dto.getRouteID()).setRouteID(dto.getRouteID());
						tripSheetDtoMap.get(dto.getRouteID()).setTollExpenseAmount(Double.parseDouble(toFixedTwo.format(tripSheetDtoMap.get(dto.getRouteID()).getTollExpenseAmount() + dto.getTransactionAmount())));
						
					} 
				}
			}
			if(tripSheetDtoMap != null && tripSheetDtoMap.size() > 0 ) {
				for(Integer key : tripSheetDtoMap.keySet()) {
					tripSheetDtoMap.get(key).setTotalBalance((tripSheetDtoMap.get(key).getTripTotalincome())-(tripSheetDtoMap.get(key).getExpenseAmount()+tripSheetDtoMap.get(key).getFuelExpenseAmount()+tripSheetDtoMap.get(key).getTollExpenseAmount()));
				}
			}
			
			valueOutObject.put("tripSheetDtoMap",tripSheetDtoMap.values());
			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ROUTE_WISE_PROFIT_AND_LOSS_DATA_FILE_PATH);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);	
			
			valueOutObject.put("tableConfig", tableConfig.getHtData());
			valueOutObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			
			return valueOutObject;
		} catch (Exception e) {
			LOGGER.error("err" + e);
			throw e;
		}finally {
			valueOutObject					= null;
			userDetails						= null;
			tableConfig						= null;
			dateRangeObj					= null;
			fromDate						= null;
			toDate							= null;
			routeId							= 0;   
			tripFlavor						= 0;   
			tripSheetDtoList				= null;
			fueltDtoList					= null;
			tolltDtoList					= null;
			tripSheetDtoMap					= null;
			tripSheetDto					= null;
			routeID							= "";	
			routeIDD						= "";	
			routeIDF						= "";	
			routeIDT						= "";  
		}
	}
	
	@Override
	public ValueObject getIncomDetailsWithinRangeByIncomeId(ValueObject valueObject) throws Exception {
		List<TripSheetIncomeDto>		incomeList			= null;
		TripSheetIncomeDto				incomeDto			= null;
		CustomUserDetails				userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			incomeDto	= new TripSheetIncomeDto();
			
			incomeDto.setVid(valueObject.getInt("vid", 0));
			incomeDto.setCompanyId(userDetails.getCompany_id());
			incomeDto.setIncomeId(valueObject.getInt("incomeId", 0));
			incomeDto.setFromDate(valueObject.getString("fromDate") + DateTimeUtility.DAY_START_TIME);
			String toDate = valueObject.getString("toDate");
			incomeDto.setToDate(toDate + DateTimeUtility.DAY_END_TIME);
			
			incomeList	= vehicleProfitAndLossService.getVehicleIncomeDetailsOfMonthByIncomeId(incomeDto);
			
			valueObject.put("incomeList", incomeList);
			
			return valueObject;
		} catch (Exception e) {
			throw	e;
		}finally {
			incomeList			= null;
			incomeDto			= null;
			userDetails			= null;
		}
	}
	
	@Override
	public ValueObject getVehicleComparisionDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails	userDetails		= null;
		String											fromDate				= null;
		String											toDate					= null;
		int												tripFlavor				= 0;
		ValueObject										dateRangeObj		= null;
		boolean											showDriverMonthlySalary	= false;	
		HashMap<String, Object>							configDriverSalary		= null;
		ValueObject										valueOutObject			= null;
		
		try {
			userDetails		= new CustomUserDetails(valueObject.getInt("companyId"), valueObject.getLong("userId",0));
			tripFlavor		= companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		
			configDriverSalary			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			showDriverMonthlySalary		= (boolean) configDriverSalary.getOrDefault(VehicleConfigurationContant.SHOW_DRIVER_MONTHLY_SALARY, false);
			
			
			dateRangeObj	= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"), DateTimeUtility.DD_MM_YYYY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			fromDate		= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate			= dateRangeObj.getString(DateTimeUtility.TO_DATE);
			
			valueObject.put("userDetails", userDetails);
			valueObject.put("tripFlavor", tripFlavor);
			valueObject.put("fromDate", fromDate);
			valueObject.put("toDate", toDate);
			valueObject.put("configDriverSalary", configDriverSalary);
			valueObject.put("showDriverMonthlySalary", showDriverMonthlySalary);
			
			valueOutObject	= new ValueObject();
			
			valueOutObject.put("noOfDays", DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)));
			
			if(valueObject.getBoolean("calledFromVehicle", false)) {
				if(valueObject.getInt("vid",0) > 0) {
					valueOutObject.put("vehicle",  getVehicleExpenseDetailsWithinDateRange(valueObject, valueObject.getInt("vid",0)).getHtData());
				}
			}else {
				if(valueObject.getInt("vehicle1",0) > 0) {
					valueOutObject.put("vehicle1",  getVehicleExpenseDetailsWithinDateRange(valueObject, valueObject.getInt("vehicle1",0)).getHtData());
				}
				if(valueObject.getInt("vehicle2",0) > 0) {
					valueOutObject.put("vehicle2",  getVehicleExpenseDetailsWithinDateRange(valueObject, valueObject.getInt("vehicle2",0)).getHtData());
				}
				if(valueObject.getInt("vehicle3",0) > 0) {
					valueOutObject.put("vehicle3",  getVehicleExpenseDetailsWithinDateRange(valueObject, valueObject.getInt("vehicle3",0)).getHtData());
				}
				if(valueObject.getInt("vehicle4",0) > 0) {
					valueOutObject.put("vehicle4",  getVehicleExpenseDetailsWithinDateRange(valueObject, valueObject.getInt("vehicle4",0)).getHtData());
				}
				if(valueObject.getInt("vehicle5",0) > 0) {
					valueOutObject.put("vehicle5",  getVehicleExpenseDetailsWithinDateRange(valueObject, valueObject.getInt("vehicle5",0)).getHtData());
				}
			}
			
			
			
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	private ValueObject	getVehicleExpenseDetailsWithinDateRange(ValueObject	valueInObject, Integer vid)throws Exception{
		ValueObject										outObject				= null;
		List<DateWiseVehicleExpenseDto>					expenseDtoList			= null;
		List<DateWiseVehicleExpenseDto>					tripExpenseList			= null;
		List<RenewalReminderDto>						renewalList				= null;
		ValueObject										valueOutObject			= null;
		List<VehicleTyreLayoutPositionDto>				vehicleTyreList			= null;
		List<BatteryDto>								batteryList				= null;
		int												tripFlavor				= 0;
		List<TripSheetDto>  							tripSheetList			= null;
		try {
			valueOutObject	= new ValueObject();
			
			outObject	=	runThreadForVehicleExpenses(valueInObject, vid);
			
			expenseDtoList			=	(List<DateWiseVehicleExpenseDto>) outObject.get("expenseDtoList");
			tripExpenseList			=	(List<DateWiseVehicleExpenseDto>) outObject.get("tripExpenseList");
			renewalList				= 	(List<RenewalReminderDto>) outObject.get("renewalList");
			vehicleTyreList			=	(List<VehicleTyreLayoutPositionDto>) outObject.get("vehicleTyreList");
			batteryList				=	(List<BatteryDto>) outObject.get("batteryList");
			tripFlavor				= 	valueInObject.getInt("tripFlavor");
			tripSheetList			= 	(List<TripSheetDto>) outObject.get("tripSheetList");
			valueOutObject.put("driverSalaryFromVehicle", vehicleService.Get_Vehicle_Header_Details(vid)); 
			valueOutObject.put("tripFlavor", tripFlavor);
			valueOutObject.put("expenseDtoList", expenseDtoList);
			valueOutObject.put("tripExpenseList", tripExpenseList);
			valueOutObject.put("renewalList", renewalList);
			valueOutObject.put("vehicleTyreList", vehicleTyreList);
			valueOutObject.put("batteryList", batteryList);
			valueOutObject.put("tripFlavor", tripFlavor);
			valueOutObject.put("fromDate", valueInObject.getString("fromDate"));
			valueOutObject.put("toDate", valueInObject.getString("toDate"));
			valueOutObject.put("showDriverMonthlySalary", valueInObject.getBoolean("showDriverMonthlySalary")); 
		/*	if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				valueOutObject.put("totalRunningKM", tripSheetService.getVehicleRunKMByVid(vid, DateTimeUtility.getTimeStamp(valueInObject.getString("fromDate"), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(valueInObject.getString("toDate"), DateTimeUtility.YYYY_MM_DD_HH_MM_SS)));	
			}else if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				valueOutObject.put("totalRunningKM", tripSheetService.getVehicleRunKMByVid(vid, DateTimeUtility.getTimeStamp(valueInObject.getString("fromDate"), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(valueInObject.getString("toDate"), DateTimeUtility.YYYY_MM_DD_HH_MM_SS)));	
			}
		*/	
			outObject.clear();
			if(valueInObject.getInt("routeId",0) > 0) {
				valueOutObject.put("routeCount",tripSheetService.getAllRouteCount(valueInObject.getString("fromDate"), valueInObject.getString("toDate"), vid));
			}
			getTripSheetDetailsDateWise(tripSheetList, valueOutObject);
			valueOutObject.put("expenseDtoList", getFinalListOfExpenseForComparisionUI(valueOutObject));
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	private ValueObject	runThreadForVehicleExpenses(ValueObject	valueObject, Integer vid) throws Exception {
		Thread											fuelThread				= null;
		Thread											seThread				= null;
		Thread											woThread				= null;
		Thread											ureaThread				= null;
		Thread											dSalaryThread			= null;
		Thread											reminderThread			= null;
		Thread											tyreThread				= null;
		Thread											batteryThread			= null;
		Thread											tripSheetThread			= null;
		Thread											tripExpenseThread		= null;
		Thread											tollExpenseThread		= null;
		List<DateWiseVehicleExpenseDto>					expenseDtoList			= null;
		
		ValueObject										valueOutObject;
		try {
			valueOutObject		= new ValueObject();
			expenseDtoList	= new ArrayList<DateWiseVehicleExpenseDto>();
			
			final	CustomUserDetails	userDetails   = (CustomUserDetails) valueObject.get("userDetails");
			final	int					tripFlavor    = valueObject.getInt("tripFlavor");
			final	String				fromDate	  = (String) valueObject.get("fromDate");
			final	String				toDate		  = (String) valueObject.get("toDate");
			
			
			fuelThread	= new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				fuelList			= null;
					try {
						if(valueObject.getInt("routeId",0) == 0 && valueObject.getLong("vehicleTypeId",0) == 0) {
							fuelList			=	fuelService.getDateWiseFuelExpenseDtoByVid(vid, fromDate, toDate, userDetails.getCompany_id());
						}else if(valueObject.getInt("routeId",0) > 0 && valueObject.getLong("vehicleTypeId",0) == 0) {
							fuelList			=	fuelService.getDateWiseFuelExpenseDtoByRouteId(vid, fromDate, toDate, userDetails.getCompany_id(), valueObject.getInt("routeId",0));
						}else if(valueObject.getInt("routeId",0) == 0 && valueObject.getLong("vehicleTypeId",0) > 0) {
							fuelList			=	fuelService.getDateWiseFuelExpenseDtoByVehicleTypeId(vid, fromDate, toDate, userDetails.getCompany_id(), valueObject.getLong("vehicleTypeId",0));
						}else if(valueObject.getInt("routeId",0) > 0 && valueObject.getLong("vehicleTypeId",0) > 0) {
							fuelList			=	fuelService.getDateWiseFuelExpenseDtoByVTRouteId(vid, fromDate, toDate, userDetails.getCompany_id(), valueObject.getLong("vehicleTypeId",0), valueObject.getInt("routeId",0));
						}
						valueOutObject.put("fuelList", fuelList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			fuelThread.start();
			
			seThread	= new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				serviceList			= null;
					try {
						if(valueObject.getInt("routeId",0) == 0 && valueObject.getLong("vehicleTypeId",0) == 0) {
							serviceList			=	serviceEntriesService.getDateWiseServiceEntriesExpenseDtoByVid(vid, fromDate, toDate, userDetails.getCompany_id());
						}else if(valueObject.getInt("routeId",0) > 0 && valueObject.getLong("vehicleTypeId",0) == 0) {
							serviceList			=	serviceEntriesService.getDateWiseServiceEntriesExpenseDtoByRouteId(vid, fromDate, toDate, userDetails.getCompany_id(), valueObject.getInt("routeId",0));
						}else if(valueObject.getInt("routeId",0) == 0 && valueObject.getLong("vehicleTypeId",0) > 0) {
							serviceList			=	serviceEntriesService.getDateWiseServiceEntriesExpenseDtoByVTId(vid, fromDate, toDate, userDetails.getCompany_id(), valueObject.getLong("vehicleTypeId",0));
						}else if(valueObject.getInt("routeId",0) > 0 && valueObject.getLong("vehicleTypeId",0) > 0) {
							serviceList			=	serviceEntriesService.getDateWiseServiceEntriesExpenseDtoByVTRouteId(vid, fromDate, toDate, userDetails.getCompany_id(), valueObject.getLong("vehicleTypeId",0), valueObject.getInt("routeId",0));
						}
						valueOutObject.put("serviceList", serviceList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			seThread.start();
			
			woThread	= new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>			workOrderList			= null;
					try {
						if(valueObject.getInt("routeId",0) == 0 && valueObject.getLong("vehicleTypeId",0) == 0) {
							workOrderList			=	workOrdersService.getDateWiseWorkOrderExpenseDtoByVid(vid, fromDate, toDate, userDetails.getCompany_id());
						}else if(valueObject.getInt("routeId",0) > 0 && valueObject.getLong("vehicleTypeId",0) == 0) {
						
						}else if(valueObject.getInt("routeId",0) == 0 && valueObject.getLong("vehicleTypeId",0) > 0) {
							workOrderList			=	workOrdersService.getDateWiseWorkOrderExpenseDtoVehicleTypeId(vid, fromDate, toDate, userDetails.getCompany_id(), valueObject.getLong("vehicleTypeId",0));
						}else if(valueObject.getInt("routeId",0) > 0 && valueObject.getLong("vehicleTypeId",0) > 0) {
						
						}
						valueOutObject.put("workOrderList", workOrderList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			woThread.start();
			
			ureaThread	= new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>			ureaEntriesList			= null;
					try {
						if(valueObject.getInt("routeId",0) == 0 && valueObject.getLong("vehicleTypeId",0) == 0) {
							ureaEntriesList			=	ureaEntriesService.getDateWiseUreaExpenseDtoByVid(vid, fromDate, toDate, userDetails.getCompany_id());
						}else if(valueObject.getInt("routeId",0) > 0 && valueObject.getLong("vehicleTypeId",0) == 0) {
							ureaEntriesList			=	ureaEntriesService.getDateWiseUreaExpenseDtoByRouteId(vid, fromDate, toDate, userDetails.getCompany_id(), valueObject.getInt("routeId",0));
						}else if(valueObject.getInt("routeId",0) == 0 && valueObject.getLong("vehicleTypeId",0) > 0) {
							ureaEntriesList			=	ureaEntriesService.getDateWiseUreaExpenseDtoByVTId(vid, fromDate, toDate, userDetails.getCompany_id(), valueObject.getLong("vehicleTypeId",0));
						}else if(valueObject.getInt("routeId",0) > 0 && valueObject.getLong("vehicleTypeId",0) > 0) {
							ureaEntriesList			=	ureaEntriesService.getDateWiseUreaExpenseDtoByVTRouteId(vid, fromDate, toDate, userDetails.getCompany_id(), valueObject.getLong("vehicleTypeId",0), valueObject.getInt("routeId",0));
						}
						valueOutObject.put("ureaEntriesList", ureaEntriesList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			ureaThread.start();
			
			dSalaryThread	= new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				driverSalaryList			= null;
					try {
						if(valueObject.getInt("routeId",0) == 0 && valueObject.getLong("vehicleTypeId",0) == 0) {
							driverSalaryList			=	vehicleProfitAndLossService.getDateWiseDriverSalaryByVid(vid, fromDate, toDate, userDetails.getCompany_id());
						}else if(valueObject.getInt("routeId",0) > 0 && valueObject.getLong("vehicleTypeId",0) == 0) {
							driverSalaryList			=	vehicleProfitAndLossService.getDateWiseDriverSalaryByRouteId(vid, fromDate, toDate, userDetails.getCompany_id(), valueObject.getInt("routeId",0));
						}else if(valueObject.getInt("routeId",0) == 0 && valueObject.getLong("vehicleTypeId",0) > 0) {
							driverSalaryList			=	vehicleProfitAndLossService.getDateWiseDriverSalaryByVehicleTypeId(vid, fromDate, toDate, userDetails.getCompany_id(), valueObject.getLong("vehicleTypeId",0));
						}else if(valueObject.getInt("routeId",0) > 0 && valueObject.getLong("vehicleTypeId",0) > 0) {
							driverSalaryList			=	vehicleProfitAndLossService.getDateWiseDriverSalaryByVTRouteId(vid, fromDate, toDate, userDetails.getCompany_id(), valueObject.getInt("routeId",0), valueObject.getLong("vehicleTypeId",0));
						}
						valueOutObject.put("driverSalaryList", driverSalaryList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			dSalaryThread.start();
			
			
			tripExpenseThread = new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				tripExpenseList			= null;
					try {
						if(valueObject.getInt("routeId",0) == 0 && valueObject.getLong("vehicleTypeId",0) == 0) {
							tripExpenseList			=	vehicleProfitAndLossService.getDateWiseVehicleTripExpenseDtoByVid(vid, fromDate, toDate);
						}else if(valueObject.getInt("routeId",0) > 0 && valueObject.getLong("vehicleTypeId",0) == 0) {
							tripExpenseList			=	vehicleProfitAndLossService.getDateWiseVehicleTripExpenseDtoByRouteId(vid, fromDate, toDate,valueObject.getInt("routeId",0));
						}else if(valueObject.getInt("routeId",0) == 0 && valueObject.getLong("vehicleTypeId",0) > 0) {
							tripExpenseList			=	vehicleProfitAndLossService.getDateWiseVehicleTripExpenseDtoByVehicleTypeId(vid, fromDate, toDate,valueObject.getLong("vehicleTypeId",0));
						}else if(valueObject.getInt("routeId",0) > 0 && valueObject.getLong("vehicleTypeId",0) > 0) {
							tripExpenseList			=	vehicleProfitAndLossService.getDateWiseVehicleTripExpenseDtoByVTRouteId(vid, fromDate, toDate, valueObject.getLong("vehicleTypeId",0), valueObject.getInt("routeId",0));
						}
						
						valueOutObject.put("tripExpenseList", tripExpenseList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tripExpenseThread.start();
			
			
			reminderThread	= new Thread() {
				public void run() {
					List<RenewalReminderDto>						renewalList				= null;
					try {
						renewalList				= 	renewalReminderService.getVehicleRenewalExpensesDateRange(vid, fromDate, toDate);
						valueOutObject.put("renewalList", renewalList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			reminderThread.start();
			
			tyreThread	= new Thread() {
				public void run() {
					List<VehicleTyreLayoutPositionDto>						vehicleTyreList				= null;
					try {
						vehicleTyreList			=	vehicleTyreLayoutService.getVehicleTyreExpenseDetailsList(vid, userDetails.getCompany_id());
						
						valueOutObject.put("vehicleTyreList", vehicleTyreList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tyreThread.start();
			
			batteryThread	= new Thread() {
				public void run() {
					List<BatteryDto>								batteryList				= null;
					try {
						batteryList				=	batteryService.getVehicleBatteryListForCost(vid);
						valueOutObject.put("batteryList", batteryList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			batteryThread.start();
			
			tripSheetThread	= new Thread() {
				public void run() {
					List<TripSheetDto> 								tripSheetList			= null;
					try {
						if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
							if(valueObject.getInt("routeId",0) > 0) {
								tripSheetList		= 	tripSheetService.getTripSheetDetailsInMonthByRouteId(vid, DateTimeUtility.getTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), valueObject.getInt("routeId",0));
							}else {
								tripSheetList		= 	tripSheetService.getTripSheetDetailsInMonthByVid(vid, DateTimeUtility.getTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
							}
								
						}else if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
							tripSheetList		= 	tripDailySheetService.getTripSheetDetailsInMonthByVId(vid, DateTimeUtility.getTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));	
						}
						valueOutObject.put("tripSheetList", tripSheetList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tripSheetThread.start();
			
			tollExpenseThread = new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				tollExpenseList			= null;
					try {
						if(valueObject.getInt("routeId",0) == 0 ) {
							tollExpenseList			=	tollExpensesDetailsService.getDateWiseFastTagTollExpenseDtoByVid(vid, fromDate, toDate);
						}else if(valueObject.getInt("routeId",0) > 0) {
							tollExpenseList			=	tollExpensesDetailsService.getDateWiseFastTagTollExpenseDtoByRouteId(vid, fromDate, toDate,valueObject.getInt("routeId",0));
						}
						
						valueOutObject.put("tollExpenseList", tollExpenseList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tollExpenseThread.start();
			
			
			fuelThread.join();
			seThread.join();
			woThread.join();
			ureaThread.join();
			dSalaryThread.join();
			tripExpenseThread.join();
			reminderThread.join();
			tyreThread.join();
			batteryThread.join();
			tripSheetThread.join();
			tollExpenseThread.join();
			
			List<DateWiseVehicleExpenseDto> tempList =  null;
			
			tempList = (List<DateWiseVehicleExpenseDto>) valueOutObject.get("fuelList");
			if(tempList != null && tempList.size() >  0){
				expenseDtoList.addAll(tempList);
			}
				
			tempList = (List<DateWiseVehicleExpenseDto>) valueOutObject.get("serviceList");
			if(tempList != null && tempList.size() >  0){
				expenseDtoList.addAll(tempList);
			}
			
			tempList = (List<DateWiseVehicleExpenseDto>) valueOutObject.get("workOrderList");
			if(tempList != null && tempList.size() >  0){
				expenseDtoList.addAll(tempList);
			}
			
			tempList = (List<DateWiseVehicleExpenseDto>) valueOutObject.get("ureaEntriesList");
			if(tempList != null && tempList.size() >  0){
				expenseDtoList.addAll(tempList);
			}
			
			tempList = (List<DateWiseVehicleExpenseDto>) valueOutObject.get("driverSalaryList");
			if(tempList != null && tempList.size() >  0){
				expenseDtoList.addAll(tempList);
			}
			
			
			valueOutObject.put("expenseDtoList", expenseDtoList);
			
			return valueOutObject;
		} catch (Exception e) {
			LOGGER.error("Error : ", e);
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, DateWiseVehicleExpenseDto>  getFinalListOfExpenseForComparisionUI(ValueObject	valueObject) throws Exception{

		
		List<DateWiseVehicleExpenseDto>					expenseDtoList			= null;
		List<DateWiseVehicleExpenseDto>					tripExpenseList			= null;
		LinkedHashMap<String, DateWiseVehicleExpenseDto>		expenseHM				= null;	
		DateWiseVehicleExpenseDto						expenseDto				= null;
		List<RenewalReminderDto> 						renewalList				= null;
		List<VehicleTyreLayoutPositionDto>				vehicleTyreList			= null;
		List<BatteryDto>								batteryList				= null;
		Integer											totalRunningKM			= 0;
		String											fromDateStr				= null;
		String											toDateStr				= null;
		boolean 										showDriverMonthlySalary = false;
		VehicleDto										driverSalaryFromVehicle	= null;
		try {
				//finalList	= new ArrayList<>();
				expenseHM	= new LinkedHashMap<>();
				/**
				 * Adding Expense Head
				 */
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY));
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA_NAME, new DateWiseVehicleExpenseDto((double) 0, VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA));
			
			expenseDtoList				= (List<DateWiseVehicleExpenseDto>) valueObject.get("expenseDtoList");
			tripExpenseList				= (List<DateWiseVehicleExpenseDto>) valueObject.get("tripExpenseList");
			renewalList					= (List<RenewalReminderDto>) valueObject.get("renewalList");
			vehicleTyreList				= (List<VehicleTyreLayoutPositionDto>) valueObject.get("vehicleTyreList");
			batteryList					= (List<BatteryDto>) valueObject.get("batteryList");
			totalRunningKM				= valueObject.getInt("totalRunningKM", 0);
			fromDateStr					= valueObject.getString("fromDate");
			toDateStr						= valueObject.getString("toDate");
			showDriverMonthlySalary		= valueObject.getBoolean("showDriverMonthlySalary"); 
			driverSalaryFromVehicle		= (VehicleDto) valueObject.get("driverSalaryFromVehicle");
			long daysInMonth 			= DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getTimeStamp(fromDateStr, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDateStr, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			
			
			DateWiseVehicleExpenseDto	vehicleExpenseDto = null;
			if(renewalList != null && !renewalList.isEmpty()) {
				for(RenewalReminderDto  reminderDto : renewalList) {
					vehicleExpenseDto = new DateWiseVehicleExpenseDto();
					vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_RENEWAL);
					vehicleExpenseDto.setExpenseTypeStr(reminderDto.getRenewal_type());
					vehicleExpenseDto.setExpenseAmount((double) 0);
					vehicleExpenseDto.setExpenseId(reminderDto.getRenewalTypeId());
					vehicleExpenseDto.setTripExpenseName(reminderDto.getRenewal_type());
					vehicleExpenseDto.setExpenseTypeName(reminderDto.getRenewal_type());
					
					Double renewalAmountForMonth = (double) 0;
					
					Timestamp	countStartDate = null;
					Timestamp	countEndDate  = null;
					Timestamp	fromDate	= DateTimeUtility.getTimeStamp(fromDateStr, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					Timestamp	toDate		= DateTimeUtility.getTimeStamp(toDateStr, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					if(reminderDto.getRenewal_from_Date().after(fromDate)) {
						countStartDate = new Timestamp(reminderDto.getRenewal_from_Date().getTime());
					}else {
						countStartDate = fromDate;
					}
					
					if(reminderDto.getRenewal_To_Date().before(toDate)) {
						countEndDate = new Timestamp(reminderDto.getRenewal_To_Date().getTime());
					}else {
						countEndDate = toDate;
					}
					long renewalDays	 = 0;
					
					long dayDiff	 	 = DateTimeUtility.getDayDiffBetweenTwoDates(new Timestamp(reminderDto.getRenewal_from_Date().getTime()), new Timestamp(reminderDto.getRenewal_To_Date().getTime()));
					if(valueObject.getLong("routeCount",0) > 0) {
						renewalDays = valueObject.getLong("noOftripDays",0);
					}else {
						renewalDays	 = DateTimeUtility.getDayDiffBetweenTwoDates(countStartDate, countEndDate);
					}	
					
					if(reminderDto.getRenewal_Amount() != null && reminderDto.getRenewal_Amount() > 0) {
						renewalAmountForMonth = (reminderDto.getRenewal_Amount() / dayDiff) * renewalDays;
					}
					vehicleExpenseDto.setExpenseAmount(renewalAmountForMonth);
					
					vehicleExpenseDto.setVid(reminderDto.getVid());
					vehicleExpenseDto.setExpenseCategory((short) 2);
					
					if(expenseDtoList == null) {
						expenseDtoList	= new ArrayList<>();
						
					}
					expenseDtoList.add(vehicleExpenseDto);
				}
				
				
			}
			
			if(vehicleTyreList != null && !vehicleTyreList.isEmpty()) {
				
				vehicleExpenseDto = new DateWiseVehicleExpenseDto();
				vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE);
				vehicleExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME);
				vehicleExpenseDto.setExpenseAmount((double) 0);
				vehicleExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TYRE_NAME);
				vehicleExpenseDto.setExpenseCategory((short) 1);
				for(VehicleTyreLayoutPositionDto  positionDto : vehicleTyreList) {
					Double tyreAmountForMonth = (double) 0;
					Integer runKm	=	0 ;
					if(totalRunningKM != null) {
						runKm	= totalRunningKM;
					}
					
					tyreAmountForMonth	= positionDto.getCostPerKM() * runKm;
					vehicleExpenseDto.setExpenseAmount(tyreAmountForMonth + vehicleExpenseDto.getExpenseAmount());
					vehicleExpenseDto.setVid(positionDto.getVEHICLE_ID());
				}
				
				if(expenseDtoList == null) {
					expenseDtoList	= new ArrayList<>();
				}
				expenseDtoList.add(vehicleExpenseDto);
				
			}
			
			if(batteryList != null && !batteryList.isEmpty()) {
				
				vehicleExpenseDto = new DateWiseVehicleExpenseDto();
				vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY);
				vehicleExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY_NAME);
				vehicleExpenseDto.setTripExpenseName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_BATTERY_NAME);
				vehicleExpenseDto.setExpenseAmount((double) 0);
				vehicleExpenseDto.setExpenseCategory((short) 1);
				for(BatteryDto  batteryDto : batteryList) {
					Double batteryAmount = (double) 0;
					
					batteryAmount	= batteryDto.getBatteryAmount() * daysInMonth;
						
					vehicleExpenseDto.setExpenseAmount(batteryAmount + vehicleExpenseDto.getExpenseAmount());
					vehicleExpenseDto.setVid(batteryDto.getVid());
				}
				
				if(expenseDtoList == null) {
					expenseDtoList	= new ArrayList<>();
					
				}
				expenseDtoList.add(vehicleExpenseDto);
				
			}
			
			if(tripExpenseList != null && !tripExpenseList.isEmpty()) {
				
				for(DateWiseVehicleExpenseDto  tripSheet : tripExpenseList) {
					vehicleExpenseDto = new DateWiseVehicleExpenseDto();
					vehicleExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
					vehicleExpenseDto.setExpenseTypeName(tripSheet.getExpenseTypeName());
					vehicleExpenseDto.setExpenseAmount(tripSheet.getExpenseAmount());
					vehicleExpenseDto.setExpenseId(tripSheet.getExpenseId());
					vehicleExpenseDto.setTripExpenseName(tripSheet.getExpenseTypeName());
					vehicleExpenseDto.setExpenseCategory((short) 3);
					
					if(expenseDtoList == null) {
						expenseDtoList	= new ArrayList<>();
						
					}
					expenseDtoList.add(vehicleExpenseDto);
				}	
			}
			
			if(expenseDtoList != null && !expenseDtoList.isEmpty()) {
				
				for(DateWiseVehicleExpenseDto dto : expenseDtoList) {
					
					if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						expenseDto	= expenseHM.get(dto.getExpenseId()+"_"+dto.getExpenseTypeName());
						
					}else if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_RENEWAL) {
						expenseDto	= expenseHM.get(dto.getExpenseId()+"_"+dto.getExpenseTypeName());
						
					}else {
						expenseDto	= expenseHM.get(dto.getExpenseType()+"_"+dto.getExpenseTypeName());
					}
					
					
					if(expenseDto == null) {
						expenseDto	= dto;
					}else {
						if(expenseDto.getExpenseAmount()  == null) {
							expenseDto.setExpenseAmount(0.0);
						}	
						if(dto.getExpenseAmount()  == null) {
							dto.setExpenseAmount(0.0);
						}	
						expenseDto.setExpenseAmount(expenseDto.getExpenseAmount() + dto.getExpenseAmount()); 
							
						 expenseDto.setVid(dto.getVid());
							//expenseDto.setExpenseType(dto.getExpenseType());
							//expenseDto.setTripExpenseName(dto.getTripExpenseName());
							//expenseDto.setExpenseTypeStr(dto.getTripExpenseName());
							
							
					}
					
					
					if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						expenseDto.setExpenseCategory((short) 3);
						expenseHM.put(dto.getExpenseId()+"_"+dto.getExpenseTypeName(), expenseDto);
						
					}else if(dto.getExpenseType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_RENEWAL) {
						expenseDto.setExpenseCategory((short) 2);
						expenseHM.put(dto.getExpenseId()+"_"+dto.getExpenseTypeName(), expenseDto);
						
					}else {
						expenseDto.setExpenseCategory((short) 1);
						expenseHM.put(dto.getExpenseType()+"_"+dto.getExpenseTypeName(), expenseDto);
					}
				}
				
			}
			
			if(showDriverMonthlySalary && driverSalaryFromVehicle.getDriverMonthlySalary() != null) {
				Timestamp startTimeStampDate 			= DateTimeUtility.geTimeStampFromDate(form.parse(fromDateStr));
				
				Timestamp endTimeStampDate 				= DateTimeUtility.geTimeStampFromDate(form.parse(toDateStr));
				
				int getNoOfMonthBetweenTwoDates 		= DateTimeUtility.getNoOfMonthBetweenTwoDates(startTimeStampDate,endTimeStampDate);
				
				double driverMonthlySalary         	    = driverSalaryFromVehicle.getDriverMonthlySalary();
				
				double driverSalaryBasedOnRangeMonths   = driverMonthlySalary * getNoOfMonthBetweenTwoDates;
				
				Timestamp 		firstDayOfMonth			= DateTimeUtility.getFirstDayOfMonth(startTimeStampDate);
				
				Timestamp       lastDayOfMonth			= DateTimeUtility.getLastDayOfMonth(endTimeStampDate);
				
				long   dayDiffBetweenTwoDates     		= DateTimeUtility.getDayDiffBetweenTwoDates(firstDayOfMonth,lastDayOfMonth);
				
				double  driverSalaryPerDay	        	= (driverSalaryBasedOnRangeMonths/dayDiffBetweenTwoDates);
				
				long   dayDiffBetweenWorkingDays   	    = DateTimeUtility.getDayDiffBetweenTwoDates(startTimeStampDate,endTimeStampDate);
				
				double driverTotalSalary				= driverSalaryPerDay * dayDiffBetweenWorkingDays ;
				
				
				expenseHM.put(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY+"_"+VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY_NAME, new DateWiseVehicleExpenseDto(driverTotalSalary, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY_NAME, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY));
				
			}
			
			
			
			return	expenseHM;
			
		} catch (Exception e) {
			throw e;
		}finally {
			expenseDtoList			= null;
			expenseHM				= null;	
			expenseDto				= null;
			renewalList				= null;
			vehicleTyreList			= null;
			batteryList				= null;
		}
	
	}
	
	@SuppressWarnings("unchecked")
	private ValueObject	runThreadForDateRangeWiseVehicleProfitAndLossReport(ValueObject	valueObject) throws Exception {
		Thread											fuelThread				= null;
		Thread											seThread				= null;
		Thread											dseThread				= null;
		Thread											woThread				= null;
		Thread											ureaThread				= null;
		Thread											dSalaryThread			= null;
		Thread											incomeThread			= null;
		Thread											reminderThread			= null;
		Thread											tyreThread				= null;
		Thread											batteryThread			= null;
		Thread											tripSheetThread			= null;
		Thread											tripExpenseThread		= null;
		Thread											tollExpenseThread		= null;
		List<DateWiseVehicleExpenseDto>					expenseDtoList			= null;
		Thread											EMIThread				= null;
		Thread     									inspectionPenaltyThread		= null;
		
		ValueObject										valueOutObject;
		try {
			valueOutObject		= new ValueObject();
			expenseDtoList	= new ArrayList<DateWiseVehicleExpenseDto>();
			
			final	CustomUserDetails	userDetails   = (CustomUserDetails) valueObject.get("userDetails");
			final	int					tripFlavor    = valueObject.getInt("tripFlavor");
			final	String				fromDate	  = (String) valueObject.get("fromDate");
			final	String				toDate		  = (String) valueObject.get("toDate");
			
			
			fuelThread	= new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				fuelList			= null;
					try {
						fuelList			=	fuelService.getDateWiseFuelExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate, toDate, userDetails.getCompany_id());
						valueOutObject.put("fuelList", fuelList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			fuelThread.start();
			
			seThread	= new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				serviceList			= null;
					try {
						serviceList			=	serviceEntriesService.getDateWiseServiceEntriesExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate, toDate, userDetails.getCompany_id());
						valueOutObject.put("serviceList", serviceList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			seThread.start();
			
			dseThread	= new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				dealerServiceList			= null;
					try {
						dealerServiceList			=	dealerServiceEntriesService.getDateWiseDealerServiceEntriesExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate, toDate, userDetails.getCompany_id());
						valueOutObject.put("dealerServiceList", dealerServiceList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			dseThread.start();
			
			woThread	= new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>			workOrderList			= null;
					try {
						workOrderList			=	workOrdersService.getDateWiseWorkOrderExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate, toDate, userDetails.getCompany_id());
						valueOutObject.put("workOrderList", workOrderList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			woThread.start();
			
			ureaThread	= new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>			ureaEntriesList			= null;
					try {
						ureaEntriesList			=	ureaEntriesService.getDateWiseUreaExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate, toDate, userDetails.getCompany_id());
						valueOutObject.put("ureaEntriesList", ureaEntriesList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			ureaThread.start();
			
			dSalaryThread	= new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				driverSalaryList			= null;
					try {
						driverSalaryList			=	vehicleProfitAndLossService.getDateWiseDriverSalaryByVid(valueObject.getInt("vid", 0), fromDate, toDate, userDetails.getCompany_id());
						valueOutObject.put("driverSalaryList", driverSalaryList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			dSalaryThread.start();
			
			
			tripExpenseThread = new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				tripExpenseList			= null;
					try {
						tripExpenseList			=	vehicleProfitAndLossService.getDateWiseVehicleTripExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate, toDate);
						
						valueOutObject.put("tripExpenseList", tripExpenseList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tripExpenseThread.start();
			
			incomeThread	= new Thread() {
				public void run() {
					List<VehicleProfitAndLossReportDto> 			incomeList				= null;
					try {
						incomeList				= 	vehicleProfitAndLossService.getIncomeDetailsForDateRange(valueObject.getInt("vid", 0), fromDate, toDate, userDetails.getCompany_id());
						valueOutObject.put("incomeList", incomeList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			incomeThread.start();
			
			reminderThread	= new Thread() {
				public void run() {
					List<RenewalReminderDto>						renewalList				= null;
					try {
						renewalList				= 	renewalReminderService.getVehicleRenewalExpensesDateRange(valueObject.getInt("vid", 0), fromDate, toDate);
						valueOutObject.put("renewalList", renewalList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			reminderThread.start();
			
			tyreThread	= new Thread() {
				public void run() {
					List<VehicleTyreLayoutPositionDto>						vehicleTyreList				= null;
					try {
						vehicleTyreList			=	vehicleTyreLayoutService.getVehicleTyreExpenseDetailsList(valueObject.getInt("vid", 0), userDetails.getCompany_id());
						
						valueOutObject.put("vehicleTyreList", vehicleTyreList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tyreThread.start();
			
			batteryThread	= new Thread() {
				public void run() {
					List<BatteryDto>								batteryList				= null;
					try {
						batteryList				=	batteryService.getVehicleBatteryListForCost(valueObject.getInt("vid", 0));
						valueOutObject.put("batteryList", batteryList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			batteryThread.start();
			
			tripSheetThread	= new Thread() {
				public void run() {
					List<TripSheetDto> 								tripSheetList			= null;
					try {
						if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
							tripSheetList		= 	tripSheetService.getTripSheetDetailsInMonthByVid(valueObject.getInt("vid", 0), DateTimeUtility.getTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));	
						}else if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
							tripSheetList		= 	tripDailySheetService.getTripSheetDetailsInMonthByVId(valueObject.getInt("vid", 0), DateTimeUtility.getTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));	
						}
						valueOutObject.put("tripSheetList", tripSheetList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tripSheetThread.start();
			
			tollExpenseThread = new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				tollExpenseList			= null;
					try {
							tollExpenseList			=	tollExpensesDetailsService.getDateWiseFastTagTollExpenseDtoByVid(valueObject.getInt("vid", 0), fromDate, toDate);
						
						valueOutObject.put("tollExpenseList", tollExpenseList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			tollExpenseThread.start();
			EMIThread	= new Thread() {
				public void run() {
					List<VehicleEmiPaymentDetailsDto>		vehicleEMIList			= null;
					try {
						vehicleEMIList			= 	vehicleEmiDetailsService.getVehicleEMIForDateRange(fromDate,toDate,valueObject.getInt("vid", 0));
						valueOutObject.put("vehicleEMIList", vehicleEMIList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			inspectionPenaltyThread = new Thread() {
				public void run() {
					List<DateWiseVehicleExpenseDto>				inspectionPenaltyList			= null;
					 try {
						 inspectionPenaltyList=inspectionCompletionDetailsService.getDateWiseInspectionPenaltyByVid(valueObject.getInt("vid", 0),fromDate, toDate, userDetails.getCompany_id());	
						 valueOutObject.put("inspectionPenaltyList", inspectionPenaltyList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
			
				}
			};
			inspectionPenaltyThread.start();
			
			if(valueObject.getBoolean("vehicleTyreMountCostInPLReport", false)) {
				Runnable tyreMountRun=()->{
					try {
						valueOutObject.put("tyreMountList", getTyreDetailsForPL(fromDate, toDate, valueObject.getInt("vid", 0)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
				Thread tyreMountThread = new Thread(tyreMountRun);
				tyreMountThread.start();
				tyreMountThread.join();
				}
			
			EMIThread.start();
			
			fuelThread.join();
			seThread.join();
			dseThread.join();
			woThread.join();
			ureaThread.join();
			dSalaryThread.join();
			tripExpenseThread.join();
			incomeThread.join();
			reminderThread.join();
			tyreThread.join();
			batteryThread.join();
			tripSheetThread.join();
			tollExpenseThread.join();
			inspectionPenaltyThread.join();
			if(valueObject.getBoolean("vehicleMonthlyEMIPayment", false) == false) {
			EMIThread.join();
			}
			
			List<DateWiseVehicleExpenseDto> tempList =  null;
			
			tempList = (List<DateWiseVehicleExpenseDto>) valueOutObject.get("fuelList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
				
			tempList = (List<DateWiseVehicleExpenseDto>) valueOutObject.get("serviceList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			
			tempList = (List<DateWiseVehicleExpenseDto>) valueOutObject.get("workOrderList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			
			tempList = (List<DateWiseVehicleExpenseDto>) valueOutObject.get("ureaEntriesList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			
			tempList = (List<DateWiseVehicleExpenseDto>) valueOutObject.get("driverSalaryList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			tempList = (List<DateWiseVehicleExpenseDto>) valueOutObject.get("tollExpenseList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			tempList = (List<DateWiseVehicleExpenseDto>) valueOutObject.get("inspectionPenaltyList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			
			tempList = (List<DateWiseVehicleExpenseDto>) valueOutObject.get("dealerServiceList");
			if(tempList != null && !tempList.isEmpty()){
				expenseDtoList.addAll(tempList);
			}
			
			
			valueOutObject.put("expenseDtoList", expenseDtoList);
			
			return valueOutObject;
		} catch (Exception e) {
			LOGGER.error("Error : ", e);
			throw e;
		}
	}
	
	public List<InventoryTyreDto>  getTyreDetailsForPL(String fromDate,String toDate,int vid) throws Exception{
		String query = " AND TH.TYRE_ASSIGN_DATE BETWEEN '"+fromDate+"' AND '"+toDate+"' AND TH.VEHICLE_ID ="+vid+" AND TH.TYRE_STATUS_ID="+InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT+" ";
		List<InventoryTyreDto>  tyreMountList  =inventoryTyreService.getTyreHistoryDetails(query);
		if(tyreMountList != null) {
			Map<Long,InventoryTyreDto> inventoryMap=tyreMountList.parallelStream().collect(Collectors.toMap(InventoryTyreDto::getTYRE_ID, Function.identity(),(e,e1)->e.getTYRE_ASSIGN_DATE_ON().before(e1.getTYRE_ASSIGN_DATE_ON())?e:e1));
			Set<Long> tyreIds=inventoryMap.keySet();
			StringJoiner strJoiner = new StringJoiner(",");
			tyreIds.forEach(e->strJoiner.add(""+e));
			query = " AND TH.TYRE_ASSIGN_DATE < '"+fromDate+"' AND TH.TYRE_ID IN ("+strJoiner.toString()+") AND TH.TYRE_STATUS_ID="+InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT+ " ";
			List<InventoryTyreDto>  tyreMountRemoveList= inventoryTyreService.getTyreHistoryDetails(query);
			if(tyreMountRemoveList != null && !tyreMountRemoveList.isEmpty()) {
				tyreMountRemoveList.forEach(e->inventoryMap.remove(e.getTYRE_ID()));
			}
			if(!inventoryMap.isEmpty())
				return  new ArrayList<>(inventoryMap.values());
		}
		return null;
		
	}
}
