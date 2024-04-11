package org.fleetopgroup.persistence.report.bll.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetReportDto;
import org.fleetopgroup.persistence.report.bll.TripSheetReportBll;
import org.fleetopgroup.persistence.report.dao.TripSheetReportDao;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TripSheetReportBllImpl implements TripSheetReportBll{
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	IUserProfileService		userProfileService;
	@Autowired	TripSheetReportDao		tripSheetReportDao;
	@Autowired	IVehicleGroupService	vehicleGroupService;
	
	SimpleDateFormat	format	= new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);

	@Override
	public ValueObject getTripSheetStatusCollectionDetails(ValueObject valueObject) throws Exception {
		Long							vehicleGroupId			= (long) 0;
		String							dateRange				= "";
		CustomUserDetails				userDetails				= null;
		ValueObject						tableConfig				= null;
		String 							query					= "";
		Long 							userId					= null ;
		short							tripStatusId			= 0;
		ValueObject						threadObject			= null;
		try {
			userDetails						= new CustomUserDetails(valueObject.getInt("companyId",0), valueObject.getLong("userId",0));			
			vehicleGroupId					= valueObject.getLong("vehicleGroupId", 0);
			dateRange						= valueObject.getString("date");
			userId							= valueObject.getLong("userName", 0);
			tripStatusId					= valueObject.getShort("tripStutesId",(short) 0);

			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				String userName = "" , vehicleGroup = "",date = "" ;
				
				if(tripStatusId == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
					date		=	" T.dispatchedByTime between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
					if(userId > 0 ){
						userName = " AND  T.dispatchedById = "+ userId +" ";
					}
				}else if(tripStatusId == TripSheetStatus.TRIP_STATUS_CLOSED) {
					date		=	" T.closedByTime between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
					if(userId > 0 ){
						userName = " AND  T.closedById = "+ userId +" ";
					}
				}else if(tripStatusId == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED) {
					date		=	" T.closeACCtripDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
					if(userId > 0 ){
						userName = " AND  T.closeACCTripNameById = "+ userId +" ";
					}
				}else {
					valueObject.put("unknowStatus", true);
					return  valueObject;
				}
				
				
				if(vehicleGroupId > 0 ){
					vehicleGroup = " AND  V.vehicleGroupId = "+ vehicleGroupId +" ";
					valueObject.put("selectedVehicleGroup", vehicleGroupService.getVehicleGroupByID(vehicleGroupId));
				}
				
				
				

				query       = " "+ date+" " + userName + " "+ vehicleGroup +" ";
			}	

			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.TRIPSHEET_COLLECTION_REPORT_DATA_FILE_PATH);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

			threadObject	=	runThreadForReportDetails(query, userDetails.getCompany_id(), userDetails.getId());
		
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			if(userId > 0 ){
				valueObject.put("selectedUser", userProfileService.getUserProfileByUser_id(userId));
			}
			valueObject	=	getFinalListForUI(threadObject, valueObject);

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails				= null;	
			vehicleGroupId			= null;
			dateRange				= "";
			tableConfig				= null;
			query					= "";
			userId					= null;
		}
	}

	ValueObject runThreadForReportDetails(String query, Integer companyId, Long userId) throws Exception{
		Thread											expensesThread			= null;
		Thread											fuelThread				= null;
		Thread											tripDetailsThread		= null;
		Thread											tollThread				= null;
		Thread											dueThread				= null;
		ValueObject										valueOutObject;
		try {
			valueOutObject		= new ValueObject();
			
			
			tripDetailsThread	= new Thread() {
				public void run() {
					List<TripSheetReportDto>				tripSheettList			= null;
					try {
						tripSheettList		= tripSheetReportDao.getTripSheetReportDtoList(query, companyId, userId);
						valueOutObject.put("tripSheetReportList", tripSheettList);
					} catch (Exception e) {
						LOGGER.error("err", e);
					}
				}
			};
			tripDetailsThread.start();
			
			fuelThread	= new Thread() {
				public void run() {
					HashMap<Long, TripSheetReportDto>				fuelDataHM			= null;
					try {
						fuelDataHM		= tripSheetReportDao.getTripSheetFuelData(query, companyId, userId);
						valueOutObject.put("fuelDataHM", fuelDataHM);
					} catch (Exception e) {
						LOGGER.error("err", e);
					}
				}
			};
			fuelThread.start();
			
			expensesThread	= new Thread() {
				public void run() {
					HashMap<Long, TripSheetReportDto>				expenseDataHM			= null;
					try {
						expenseDataHM		= tripSheetReportDao.getTripExpenseDataHM(query, companyId, userId);
						valueOutObject.put("expenseDataHM", expenseDataHM);
					} catch (Exception e) {
						LOGGER.error("err", e);
					}
				}
			};
			expensesThread.start();
			
			tollThread	= new Thread() {
				public void run() {
					HashMap<Long, TripSheetReportDto>				tollDataHM			= null;
					try {
						tollDataHM		= tripSheetReportDao.getTripTollDataHM(query, companyId, userId);
						valueOutObject.put("tollDataHM", tollDataHM);
					} catch (Exception e) {
						LOGGER.error("err", e);
					}
				}
			};
			tollThread.start();
			
			dueThread	= new Thread() {
				public void run() {
					HashMap<Long, TripSheetReportDto>				dueDataHM			= null;
					try {
						dueDataHM		= tripSheetReportDao.getTripDueAmount(query, companyId, userId);
						valueOutObject.put("dueDataHM", dueDataHM);
					} catch (Exception e) {
						LOGGER.error("err", e);
					}
				}
			};
			dueThread.start();
			

			tripDetailsThread.join();
			fuelThread.join();
			expensesThread.join();
			tollThread.join();
			dueThread.join();
			
			return valueOutObject;
		} catch (Exception e) {
			LOGGER.error("Error : ", e);
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	private ValueObject  getFinalListForUI(ValueObject  threadObject, ValueObject  valueObject) throws Exception{
		List<TripSheetReportDto>				tripSheetReportList		= null;
		HashMap<Long, TripSheetReportDto>		fuelDataHM				= null;
		HashMap<Long, TripSheetReportDto>		expenseDataHM			= null;
		HashMap<Long, TripSheetReportDto>		tollDataHM				= null;
		HashMap<Long, TripSheetReportDto>		dueDataHM				= null;
		try {
			if(threadObject != null) {
				tripSheetReportList	= (List<TripSheetReportDto>) threadObject.get("tripSheetReportList");
				fuelDataHM			= (HashMap<Long, TripSheetReportDto>) threadObject.get("fuelDataHM");
				expenseDataHM		= (HashMap<Long, TripSheetReportDto>) threadObject.get("expenseDataHM");
				tollDataHM			= (HashMap<Long, TripSheetReportDto>) threadObject.get("tollDataHM");
				dueDataHM			= (HashMap<Long, TripSheetReportDto>) threadObject.get("dueDataHM");
				
				if(tripSheetReportList != null && !tripSheetReportList.isEmpty()) {
					TripSheetReportDto fuelData = null;
					for (TripSheetReportDto reportDto : tripSheetReportList) {
						
						reportDto.setTripOpenDateStr(format.format(reportDto.getTripOpenDate()) +" To "+format.format(reportDto.getTripCloseDate()));
						
						if(reportDto.getTripExpenseAmount() == null) {
							reportDto.setTripExpenseAmount(0.0);
						}
						if(reportDto.getTripIncomeAmount() == null) {
							reportDto.setTripIncomeAmount(0.0);
						}
						
						if(expenseDataHM != null && !expenseDataHM.isEmpty()) {
							fuelData = expenseDataHM.get(reportDto.getTripSheetId());
							if(fuelData != null) {
								reportDto.setTripExpenseAmount(fuelData.getTripExpenseAmount());
							}
						}
						
						if(fuelDataHM != null && !fuelDataHM.isEmpty()) {
							fuelData = fuelDataHM.get(reportDto.getTripSheetId());
							if(fuelData != null) {
								reportDto.setTripExpenseAmount(fuelData.getTripFuelAmount() + reportDto.getTripExpenseAmount());
								reportDto.setTripFuelAmount(fuelData.getTripFuelAmount());
								reportDto.setTripFuelQuantity(fuelData.getTripFuelQuantity());
							}
						}
						
						if(tollDataHM != null && !tollDataHM.isEmpty()) {
							fuelData = tollDataHM.get(reportDto.getTripSheetId());
							if(fuelData != null) {
								reportDto.setTripExpenseAmount(fuelData.getTripFastTagAmt() + reportDto.getTripExpenseAmount());
								reportDto.setTripFastTagAmt(fuelData.getTripFastTagAmt());
							}
						}
						
						if(dueDataHM != null && !dueDataHM.isEmpty()) {
							fuelData = dueDataHM.get(reportDto.getTripSheetId());
							
							if(fuelData != null) {
								reportDto.setTripDueAmount(fuelData.getTripDueAmount());
							}else {
								reportDto.setTripDueAmount(0.0);
							}
						}else {
							reportDto.setTripDueAmount(0.0);
						}
						
						
						reportDto.setTripBalanceAmt(reportDto.getTripIncomeAmount() - reportDto.getTripExpenseAmount());
						
						reportDto.setDriverBalanceAmt((reportDto.getTripIncomeAmount()+reportDto.getTripAdvanceAmount())-(reportDto.getTripExpenseAmount()+reportDto.getTripDueAmount()));
					}
				}
				
			}
			valueObject.put("tripSheetReportList", tripSheetReportList);
			valueObject.put("fuelDataHM", fuelDataHM);
			valueObject.put("expenseDataHM", expenseDataHM);
			valueObject.put("tollDataHM", tollDataHM);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Error : ", e);
			throw e;
		}
	}
	@Override
	public ValueObject getTripIncomeReportByIncomeName(ValueObject valueObject) throws Exception {		
		ValueObject								valueOutObject							= null;
		CustomUserDetails 						userDetails								= null;
		ValueObject						   	 	tableConfig								= null;
		List<TripSheetReportDto>				tripsheetReportDtoList					= null;
		String 									startDate 								= "";
		String 									endDate 								= "";
		String									incomeStr								= "";
		long									incomeId								= 0;
		String									query									= "";
		String									dateRange								= "";
		int										vid										= 0;
		String 									vehicle									= "";
		try {
			valueOutObject 		= new ValueObject();
			tableConfig			= new ValueObject();
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			startDate 			= valueObject.getString("startDate");
			endDate 			= valueObject.getString("endDate");
			incomeId   			= valueObject.getLong("tripincomeID",0);
			vid   				= valueObject.getInt("vehicleId",0);
			
			startDate = DateTimeUtility.getStrDateFromStrDate(startDate, DateTimeUtility.DD_MM_YYYY);
			endDate   = DateTimeUtility.getStrDateFromStrDate(endDate , DateTimeUtility.DD_MM_YYYY, DateTimeUtility.YYYY_MM_DD);

			
			if(incomeId > 0) {
				incomeStr = "AND TI.incomeId = "+incomeId+" ";
			}
			if(vid > 0) {
				vehicle = "AND T.vid = "+vid+"";
			}
			dateRange ="AND T.tripOpenDate BETWEEN '"+startDate+"' AND '"+endDate+" 23:59:59'";
			query = " "+incomeStr+"  "+dateRange+" "+vehicle+" ";
			
			valueObject.put("query", query);
			valueObject.put("companyId", userDetails.getCompany_id());
			valueObject.put("usesrID", userDetails.getId());
			
			tripsheetReportDtoList = tripSheetReportDao.getTripincomeDetailsByIncomeId(valueObject);
			valueOutObject.put("tripsheetIncomeList", tripsheetReportDtoList);
			
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.TRIPINCOME_REPORT_BY_INCOME_NAME_DATA_FILE_PATH);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);	
			
			valueOutObject.put("tableConfig", tableConfig.getHtData());
			valueOutObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueOutObject					= null;
			userDetails						= null;
			tableConfig						= null;
		}
	}
	
}
