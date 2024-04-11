package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripSheetConfigurationConstant;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleExtraDetailsConstant;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.dao.TicketIncomeApiRepository;
import org.fleetopgroup.persistence.dao.TripSheetIncomeRepository;
import org.fleetopgroup.persistence.dao.VehicleExtraDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TicketIncomeApiDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.TicketIncomeApi;
import org.fleetopgroup.persistence.model.TripDailyIncome;
import org.fleetopgroup.persistence.model.TripDailySheet;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.fleetopgroup.persistence.model.VehicleExtraDetails;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossIncomeTxnChecker;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ITicketIncomeApiService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossIncomeTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

@Service
public class TicketIncomeApiService implements ITicketIncomeApiService {

	@Autowired private ICompanyConfigurationService 					companyConfigurationService;
	@Autowired private IUserProfileService 								userProfileService;
	@Autowired private ITripSheetService 								tripSheetService;
	@Autowired private TicketIncomeApiRepository 						ticketIncomeApiRepository;
	@Autowired private ITripDailySheetService 							TripDailySheetService;
	@Autowired private IVehicleProfitAndLossService						vehicleProfitAndLossService;
	@Autowired private IVehicleProfitAndLossIncomeTxnCheckerService		vehicleProfitAndLossIncomeTxnCheckerService;
	@Autowired private VehicleExtraDetailsRepository					vehicleExtraDetailsRepository;
	@Autowired private TripSheetIncomeRepository						tripSheetIncomeRepository;

	VehicleProfitAndLossTxnCheckerBL txnCheckerBL = new VehicleProfitAndLossTxnCheckerBL();

	SimpleDateFormat format 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat form 				= new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat sf1 				= new SimpleDateFormat("h:mm aa");
	SimpleDateFormat df 				= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat dateFormat 		= new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
	SimpleDateFormat dateTimeFormat 	= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void saveticketIncomeApi(TicketIncomeApi ticketIncomeApi) throws Exception {

		ticketIncomeApiRepository.save(ticketIncomeApi);
	}

	@Override
	public ValueObject getVehicleTicketIncome(ValueObject valueObject) throws Exception {
		HashMap<String, Object> 	configuration				= null;
		ValueObject					itsGateway					= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			
			valueObject.put("startDate", DateTimeUtility.getTimeStamp(valueObject.getString("FromDate"), DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
		    valueObject.put("endDate",DateTimeUtility.getTimeStamp(valueObject.getString("ToDate"), DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
		    valueObject.put("isTripClosed", true);
		    valueObject.put("configuration", configuration);
		    
		    if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_ITS_GATEWAY_BUS_INCOME)) {
		    	itsGateway = new ValueObject();
		    	
		    	VehicleExtraDetails busDeatils = vehicleExtraDetailsRepository.getBusId(valueObject.getInt("vid"), valueObject.getInt("companyId"));
		    	
		    	java.util.Date fromDate = dateTimeFormat.parse(valueObject.getString("FromDate"));
				fromDate.setTime(fromDate.getTime() - 1800 * 1000);
		    	
		    	itsGateway.put("FromDate", dateTimeFormat.format(fromDate));
		    	itsGateway.put("ToDate", dateTimeFormat.format((Date)dateTimeFormat.parse(valueObject.getString("ToDate"))));
		    	itsGateway.put("tripSheetId", valueObject.getLong("tripSheetId"));
		    	itsGateway.put("vid", valueObject.getInt("vid"));
		    	itsGateway.put("BusID", busDeatils.getBusId());
		    	itsGateway.put("companyId", valueObject.getInt("companyId"));
		    	itsGateway.put("userId", valueObject.getLong("userId"));
		    	itsGateway.put("configuration", configuration);
		    	itsGateway.put("isTripClosed", true);
		    	
		    	return addITSGatewayBusIncome(itsGateway);
		    	
		    }else if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_BITLA_API_INCOME)) {
		    	return addBitlaTicketIncome(valueObject);
		    }else if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_MANTIS_DISPATCH_INCOME)) {
		    	return addMantisDispatchIncome(valueObject);
		    }else {
		    	return addVehicleTicketIncome(valueObject);
		    }
			
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			throw e;
		}
		  
	}

	@Override
	@Transactional
	public ValueObject addVehicleTicketIncome(ValueObject valueObject) throws Exception {
		HashMap<String, Object> configuration = null;
		CustomUserDetails userDetails = null;
		UserProfileDto userProfile = null;
		TripSheet sheet = null;
		ValueObject	valueOutObject	= null;

		try {
			valueOutObject	= new ValueObject();
			userDetails = new CustomUserDetails(valueObject.getInt("companyId"), valueObject.getLong("userId"));
			configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),
					PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			userProfile = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(valueObject.getLong("userId"));
			sheet = tripSheetService.getTripSheetData(valueObject.getLong("tripSheetId"), userDetails.getCompany_id());

			valueObject.put("userDetails", userDetails);
			valueObject.put("configuration", configuration);
			valueObject.put("userProfile", userProfile);
			valueObject.put("sheet", sheet);

			valueObject =	getTicketIncomeDetails(valueObject);
			
			valueOutObject.put("noRecordFound", valueObject.getBoolean("noRecordFound", false));
			valueOutObject.put("alreadyAdded", valueObject.getBoolean("alreadyAdded", false));
			valueOutObject.put("ticketIncomeAdded", valueObject.getBoolean("busTicketIncomeAdded", false));
			valueOutObject.put("flavor", valueObject.getInt("flavor"));
				
			return valueOutObject;
		} catch (Exception e) {
			System.err.println("Exception " + e);
			throw e;
		} finally {
			configuration = null;
			userProfile = null;
			userDetails = null;
			sheet = null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	private ValueObject getTicketIncomeDetails(ValueObject valueObject) throws Exception {

		HashMap<String, Object> configuration = null;
		JSONObject inputObj = null;
		HttpResponse<JsonNode> response = null;
		Integer companyId = null;
		List<TicketIncomeApi> ticketIncomeDetails = null;
		String apiURL = "";

		try {
			inputObj = new JSONObject();
			configuration = (HashMap<String, Object>) valueObject.get("configuration");

			/*
			 * companyId = this is intangles companyId not fleetop
			 */
			companyId = (Integer) configuration.get(TripSheetConfigurationConstant.COMPANYID_FOR_TCKT_INCM_API);
			apiURL = (String) configuration.get(TripSheetConfigurationConstant.URl_FOR_TCKT_INCM_API);

			inputObj.put(TripSheetConfigurationConstant.COMPANYID_FOR_TCKT_INCM_API, companyId);
			inputObj.put(TicketIncomeApiDto.FROM_DATE, valueObject.getString(TicketIncomeApiDto.FROM_DATE));
			inputObj.put(TicketIncomeApiDto.TO_DATE, valueObject.getString(TicketIncomeApiDto.TO_DATE));
			inputObj.put(TicketIncomeApiDto.BUS_NUMBER, valueObject.getString(TicketIncomeApiDto.BUS_NUMBER));

			response = Unirest.get(apiURL)
					.queryString(TripSheetConfigurationConstant.COMPANYID_FOR_TCKT_INCM_API, companyId + "")
					.queryString(TicketIncomeApiDto.BUS_NUMBER, valueObject.getString(TicketIncomeApiDto.BUS_NUMBER))
					.queryString(TicketIncomeApiDto.FROM_DATE, valueObject.getString(TicketIncomeApiDto.FROM_DATE))
					.queryString(TicketIncomeApiDto.TO_DATE, valueObject.getString(TicketIncomeApiDto.TO_DATE))
					.asJson();

			if (response.getStatus() == 200) {

				JSONArray jsonArray = response.getBody().getArray();
				ticketIncomeDetails = setTicketIncomeDetailsDto(jsonArray, valueObject);
				if (jsonArray != null && jsonArray.length() > 0
						&& jsonArray.getJSONObject(0).has("APIGujaratQuickViewResult")) {
					
					saveTicketIncomeDetails(ticketIncomeDetails, valueObject);
					valueObject.put("ticketIncomeAdded", true);
				}else {
					valueObject.put("noRecordFound", true);
				}


			} else {
				valueObject.put("noRecordFound", true);
			}
			return valueObject;
		} catch (Exception e) {
			System.err.println("Exception " + e);
			return valueObject;
		} finally {
			configuration 		= null;
			inputObj 			= null;
			response 			= null;
			companyId 			= null;
			ticketIncomeDetails = null;
			apiURL 				= null;
		}

	}

	public List<TicketIncomeApi> setTicketIncomeDetailsDto(JSONArray jsonArray, ValueObject valueObject)
			throws Exception {
		JSONArray details = null;
		CustomUserDetails userDetails = null;
		List<TicketIncomeApi> ticketIncomeApiList = null;
		TicketIncomeApi ticketIncomeApi = null;
		Timestamp endDate = null;
		Date startDate = null;
		try {

			ticketIncomeApiList = new ArrayList<>();
			userDetails = (CustomUserDetails) valueObject.get("userDetails");
			endDate = (Timestamp) valueObject.get("endDate");
			startDate = (Date) valueObject.get("startDate");

			Calendar cal = Calendar.getInstance();
			// remove next line if you're always using the current time.
			cal.setTime(startDate);
			cal.add(Calendar.MINUTE, -30);
			startDate = cal.getTime();

			if (jsonArray != null && jsonArray.length() > 0
					&& jsonArray.getJSONObject(0).has("APIGujaratQuickViewResult")) {

				details = (JSONArray) jsonArray.getJSONObject(0).get("APIGujaratQuickViewResult");

				for (int i = 0; i < details.length(); i++) {

					ticketIncomeApi = new TicketIncomeApi();

					ticketIncomeApi.setTripSheetID(valueObject.getLong("tripSheetId"));
					ticketIncomeApi.setVid(valueObject.getInt("vid"));
					ticketIncomeApi.setBranchAmt(details.getJSONObject(i).getDouble("BranchAmt"));
					ticketIncomeApi.setGuestAmt(details.getJSONObject(i).getDouble("GuestAmt"));
					ticketIncomeApi.setOfflineAgentAmt(details.getJSONObject(i).getDouble("OfflineAgentAmt"));
					ticketIncomeApi.setOnlineAgentsAmt(details.getJSONObject(i).getDouble("OnlineAgentsAmt"));
					ticketIncomeApi.setBranchSeatcount(details.getJSONObject(i).getLong("BranchSeatcount"));
					ticketIncomeApi.setGuestSeatcount(details.getJSONObject(i).getLong("GuestSeatcount"));
					ticketIncomeApi.setOffAgentSeatcount(details.getJSONObject(i).getLong("OffAgentSeatcount"));
					ticketIncomeApi.setOnlAgentsSeatcount(details.getJSONObject(i).getLong("OnlAgentsSeatcount"));
					ticketIncomeApi.setRouteName(details.getJSONObject(i).getString("RouteName"));

					String chartDate = format.format(form.parse(details.getJSONObject(i).getString("ChartDate")));
					String tripTime = df.format(sf1.parse(details.getJSONObject(i).getString("TripTime")))
							.substring(10);
					String chartDateWithTime = chartDate + "" + tripTime;
					ticketIncomeApi.setChartDate(df.parse(chartDateWithTime));

					ticketIncomeApi.setCompanyId(userDetails.getCompany_id());
					ticketIncomeApi.setCreatedById(userDetails.getId());
					ticketIncomeApi.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
					if (ticketIncomeApi.getChartDate().after(startDate)
							&& ticketIncomeApi.getChartDate().before(endDate))
						ticketIncomeApiList.add(ticketIncomeApi);
				}
			}

			return ticketIncomeApiList;

		} catch (Exception e) {
			throw e;
		} finally {
			details = null;
			userDetails = null;
			ticketIncomeApiList = null;
			ticketIncomeApi = null;
			endDate = null;
			startDate = null;
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	private synchronized void saveTicketIncomeDetails(List<TicketIncomeApi> ticketIncomeDetails,
			ValueObject valueObject) throws Exception {
		HashMap<String, Object> configuration = null;
		CustomUserDetails userDetails = null;
		UserProfileDto userProfile = null;
		int flavor = 0;
		// TripIncome incomeName = null;
		Integer ticketIncomeId = null;
		HashMap<Long, TripSheetIncome>  tripSheetIncomeDtoHM = null;
		HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>  incomeTxnCheckerHashMap = null;
		TripSheet sheet = null;
		try {

			userDetails = (CustomUserDetails) valueObject.get("userDetails");
			configuration = (HashMap<String, Object>) valueObject.get("configuration");
			userProfile = (UserProfileDto) valueObject.get("userProfile");
			ticketIncomeId = (Integer) configuration.get(TripSheetConfigurationConstant.TICKET_INCOME_ID);
			sheet		= (TripSheet) valueObject.get("sheet");

			// incomeName = TripIncomeService.validateTripIncome((String)
			// configuration.get(TripSheetConfigurationConstant.TCKT_INCM_NAME),
			// userDetails.getCompany_id());
			flavor = companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			valueObject.put("flavor", flavor);

			if (ticketIncomeDetails != null && !ticketIncomeDetails.isEmpty() && ticketIncomeId != null) {
				ticketIncomeApiRepository.deleteTicketIncome(valueObject.getLong("tripSheetId"), userDetails.getCompany_id());
				tripSheetService.deleteTripSheetIncomeById(valueObject.getLong("tripSheetId"), userDetails.getCompany_id());
					tripSheetIncomeDtoHM = new HashMap<Long, TripSheetIncome>();
					incomeTxnCheckerHashMap = new HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>();
					
					for (TicketIncomeApi tcktIncm : ticketIncomeDetails) {
						saveticketIncomeApi(tcktIncm);

						if (flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {

							TripSheet tsheet = new TripSheet();
							tsheet.setTripSheetID(valueObject.getLong("tripSheetId"));

							TripSheetIncome TripIncome = new TripSheetIncome();
							TripIncome.setIncomeId(ticketIncomeId);
							TripIncome.setIncomePlaceId(userProfile.getBranch_id());
							TripIncome.setIncomeRefence("Bus Ticket API");
							TripIncome.setIncomeCollectedById(userDetails.getId());
							TripIncome.setCompanyId(userDetails.getCompany_id());
							TripIncome.setCreatedById(userDetails.getId());
							TripIncome.setTripsheet(tsheet);
							TripIncome.setTicketIncomeApiId(tcktIncm.getTicketIncomeApiId());

							double totalBusIncome = 0;
							totalBusIncome = tcktIncm.getBranchAmt() + tcktIncm.getGuestAmt()
									+ tcktIncm.getOfflineAgentAmt() + tcktIncm.getOnlineAgentsAmt();
							TripIncome.setIncomeAmount(totalBusIncome);

							long totalSeatCount = 0;
							totalSeatCount = tcktIncm.getBranchSeatcount() + tcktIncm.getGuestSeatcount()
									+ tcktIncm.getOffAgentSeatcount() + tcktIncm.getOnlAgentsSeatcount();
							TripIncome.setTcktIncmSeatCount(totalSeatCount);

							TripIncome.setCreated(tcktIncm.getChartDate());

							tripSheetService.addTripSheetIncome(TripIncome);
							
								
								
								if(TripIncome.getIncomeAmount() > 0.0 && sheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
									
									ValueObject incomeObject = new ValueObject();
									
									incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, valueObject.getLong("tripSheetId"));
									incomeObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID,VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
									incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID,VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
									incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID,userDetails.getCompany_id());
									incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, sheet.getVid());
									incomeObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, TripIncome.getIncomeId());
									incomeObject.put(VehicleProfitAndLossDto.TRIP_INCOME_ID,TripIncome.getTripincomeID());
									
									VehicleProfitAndLossIncomeTxnChecker profitAndLossIncomeTxnChecker =
											txnCheckerBL.getVehicleProfitAndLossIncomeTxnChecker(incomeObject);
									vehicleProfitAndLossIncomeTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossIncomeTxnChecker);
									
									incomeTxnCheckerHashMap.put(profitAndLossIncomeTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossIncomeTxnChecker);
									tripSheetIncomeDtoHM.put(TripIncome.getTripincomeID(), TripIncome); 
									
								}
							 

						} else if (flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {

						} else if (flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {

							TripDailySheet tsheet = new TripDailySheet();
							tsheet.setTRIPDAILYID(valueObject.getLong("tripSheetId"));

							TripDailyIncome TripDailyIncome = new TripDailyIncome();
							TripDailyIncome.setIncomeId(ticketIncomeId);
							TripDailyIncome.setIncomeRefence("Bus Ticket");
							TripDailyIncome.setIncomeCollectedById(userDetails.getId());
							TripDailyIncome.setCompanyId(userDetails.getCompany_id());
							TripDailyIncome.setCreatedById(userDetails.getId());
							TripDailyIncome.setTripDailysheet(tsheet);

							double totalBusIncome = 0;
							totalBusIncome = tcktIncm.getBranchAmt() + tcktIncm.getGuestAmt()
									+ tcktIncm.getOfflineAgentAmt() + tcktIncm.getOnlineAgentsAmt();
							TripDailyIncome.setIncomeAmount(totalBusIncome);

							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
							TripDailyIncome.setCreated(toDate);

							TripDailySheetService.add_TripDailyIncome(TripDailyIncome);

							TripDailySheetService.update_TripDaily_TotalIncome(valueObject.getLong("tripSheetId"), userDetails.getCompany_id());

						}

						valueObject.put("busTicketIncomeAdded", true);

					}
					tripSheetService
							.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(valueObject.getLong("tripSheetId"), userDetails.getCompany_id());
					valueObject.put("alreadyAdded", false);
						
						if(tripSheetIncomeDtoHM != null && tripSheetIncomeDtoHM.size() > 0) {
							ValueObject valObj = new ValueObject(); 
							valObj.put("tripSheet", sheet);
							valObj.put("userDetails", userDetails); 
							valObj.put("tripSheetIncomeHM", tripSheetIncomeDtoHM); 
							valObj.put("incomeTxnCheckerHashMap", incomeTxnCheckerHashMap); 
							valObj.put("isTripSheetClosed", true);
							
							vehicleProfitAndLossService.runThreadForTripSheetIncome(valObj); 
						}
				
			}

		} catch (Exception e) {
			throw e;
		} finally {
			configuration = null;
			userDetails = null;
			userProfile = null;
			flavor = 0;
			ticketIncomeId = null;
		}

	}

	@Override
	public ValueObject getTicketIncomeApiDeatilsById(ValueObject valueObject) throws Exception {
		long ticketIncomeApiId = 0;
		CustomUserDetails userDetails = null;
		TicketIncomeApi ticketIncomeApi = null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ticketIncomeApiId = valueObject.getLong("ticketIncomeApiId");

			ticketIncomeApi = ticketIncomeApiRepository.geTticketIncomeApiById(ticketIncomeApiId,
					userDetails.getCompany_id());
			if (ticketIncomeApi != null) {
				valueObject.put("TicketIncomeApi", ticketIncomeApi);
			}

			return valueObject;

		} catch (Exception e) {
			throw e;
		} finally {
			ticketIncomeApiId = 0;
			userDetails = null;
			ticketIncomeApi = null;
		}
	}

	@Override
	public ValueObject addITSGatewayBusIncome(ValueObject valueObject) throws Exception {
		CustomUserDetails 				userDetails 				= null;
		UserProfileDto 					userProfile 				= null;
		TripSheet 						sheet 						= null;
		ValueObject						valueOutObject				= null;
		try {
			valueOutObject		= new ValueObject();
			userDetails 		= new CustomUserDetails(valueObject.getInt("companyId"), valueObject.getLong("userId"));
			userProfile 		= userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(valueObject.getLong("userId"));
			sheet 				= tripSheetService.getTripSheetData(valueObject.getLong("tripSheetId"), userDetails.getCompany_id());

			valueObject.put("userDetails", userDetails);
			valueObject.put("userProfile", userProfile);
			valueObject.put("sheet", sheet);

			valueObject =	getITSGatewayBusIncome(valueObject);
			
			valueOutObject.put("noRecordFound", valueObject.getBoolean("noRecordFound", false));
			valueOutObject.put("alreadyAdded", valueObject.getBoolean("alreadyAdded", false));
			valueOutObject.put("ticketIncomeAdded", valueObject.getBoolean("itsGatewayIncomeAdded", false));
			valueOutObject.put("flavor", valueObject.getInt("flavor"));
				
			return valueOutObject;
		} catch (Exception e) {
			System.err.println("Exception Add ITS " + e);
			throw e;
		} finally {
			userProfile 		= null;
			userDetails 		= null;
			sheet 				= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject addBitlaTicketIncome(ValueObject valueObject) throws Exception {
		String							apiKey				= null;
		HashMap<String, Object> 		configuration 		= null;
		try {
			configuration	= (HashMap<String, Object>) valueObject.get("configuration");
			apiKey	=	getBitlaAPIKey(configuration);
			if(apiKey != null) {
				insertBitlaIncomeDetails(valueObject, apiKey, configuration);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject addMantisDispatchIncome(ValueObject valueObject) throws Exception {
		String							apiKey				= null;
		HashMap<String, Object> 		configuration 		= null;
		try {
			configuration	= (HashMap<String, Object>) valueObject.get("configuration");
			apiKey			= (String) configuration.get("mantisDipatchIncomeAPIKey");
			
			if(apiKey != null) {
				insertMantisDipatchIncomeDetails(valueObject, apiKey, configuration);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private ValueObject	insertBitlaIncomeDetails(ValueObject	valueObject, String apiKey, HashMap<String, Object>  configuration) throws Exception{
		try {
			JSONObject	jsonObject = new JSONObject();
			jsonObject.put("vehicle_number", valueObject.getString("BusNumber").replace("-", ""));
			jsonObject.put("from_date", valueObject.getString("FromDate"));
			jsonObject.put("to_date", valueObject.getString("ToDate"));
			
			HttpResponse<JsonNode> response = Unirest.post((String)configuration.get("bitlaApiTransactionDetailsUrl"))
					  .header("Content-Type", "application/json")
					  .queryString("api_key", apiKey)
					  .body(jsonObject)
					  .asJson();
			
			if(response.getStatus() == HttpStatus.SC_OK) {
				JSONObject	jsonOutObject	=	response.getBody().getObject();
				if(jsonOutObject != null && jsonOutObject.has("net_amount")) {
					List<TicketIncomeApi> validate = ticketIncomeApiRepository
							.validateTicketIncomeApi(valueObject.getLong("tripSheetId"));
					if (validate == null || validate.isEmpty()) {
						
						UserProfileDto		userProfile = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(valueObject.getLong("userId"));
						
						TicketIncomeApi	incomeApi	= new TicketIncomeApi();
						
						incomeApi.setTripSheetID(valueObject.getLong("tripSheetId"));
						incomeApi.setVid(valueObject.getInt("vid",0));
						incomeApi.setCompanyId(valueObject.getInt("companyId",0));
						incomeApi.setCreatedById(valueObject.getLong("userId", 0));
						incomeApi.setCreatedOn(dateTimeFormat.parse(valueObject.getString("ToDate")));
						incomeApi.setBranchAmt(jsonOutObject.getDouble("net_amount"));
						incomeApi.setNoOfTrip(jsonOutObject.getInt("trip_count"));
						incomeApi.setChartDate(dateTimeFormat.parse(valueObject.getString("FromDate")));
						
						ticketIncomeApiRepository.save(incomeApi);
						
						TripSheet tsheet = new TripSheet();
						tsheet.setTripSheetID(valueObject.getLong("tripSheetId"));

						TripSheetIncome tripIncome = new TripSheetIncome();
						tripIncome.setIncomeId((Integer) configuration.get(TripSheetConfigurationConstant.TICKET_INCOME_ID));
						tripIncome.setIncomePlaceId(userProfile.getBranch_id());
						tripIncome.setIncomeRefence("Bitla API Income");
						tripIncome.setIncomeCollectedById(valueObject.getLong("userId", 0));
						tripIncome.setCompanyId(valueObject.getInt("companyId",0));
						tripIncome.setCreatedById(valueObject.getLong("userId", 0));
						tripIncome.setTripsheet(tsheet);
						tripIncome.setTicketIncomeApiId(incomeApi.getTicketIncomeApiId());
						tripIncome.setIncomeAmount(incomeApi.getBranchAmt());
						tripIncome.setCreated(incomeApi.getCreatedOn());

						tripSheetService.addTripSheetIncome(tripIncome);
						
						tripSheetService
						.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(valueObject.getLong("tripSheetId"), valueObject.getInt("companyId",0));
						
						valueObject.put("ticketIncomeAdded", true);
					}
				}
			}else if(response.getStatus() == HttpStatus.SC_NO_CONTENT) {
				valueObject.put("noRecordFound", true);
			}else {
				valueObject.put("error", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	private ValueObject	insertMantisDipatchIncomeDetails(ValueObject	valueObject, String apiKey, HashMap<String, Object>  configuration) throws Exception{
		try {
			JSONObject	jsonObject = new JSONObject();
			jsonObject.put("vehicle_number", valueObject.getString("BusNumber"));
			jsonObject.put("from_date", valueObject.getString("FromDate"));
			jsonObject.put("to_date", valueObject.getString("ToDate"));
			
			String startTime = valueObject.getString("FromDate").split("\\s")[1].split("\\.")[0];
    		String endTime = valueObject.getString("ToDate").split("\\s")[1].split("\\.")[0];
    		
    		String start = DateTimeUtility.getStrFromString(valueObject.getString("FromDate"), DateTimeUtility.YYYY_MM_DD)+"%20"+startTime;
    		String end = DateTimeUtility.getStrFromString(valueObject.getString("ToDate"), DateTimeUtility.YYYY_MM_DD)+"%20"+endTime;
    		
			
			Unirest.setTimeouts(0, 0);
			HttpResponse<JsonNode> response = Unirest.get("http://crsagentapi.bookbustickets.com/service.svc/APIGetDispatchSummary?API_key="+apiKey+"&VehicleNo="+valueObject.getString("BusNumber")+"&FromDateTime="+start+"&ToDateTime="+end+"")
			  .asJson();
			
			if(response.getStatus() == HttpStatus.SC_OK) {
				JSONObject	jsonOutObject	=	response.getBody().getObject();
				if(jsonOutObject != null && jsonOutObject.has("Details")) {
					try {
						JSONArray	jsonArray	=	jsonOutObject.getJSONArray("Details");
						List<TicketIncomeApi> validate = ticketIncomeApiRepository
								.validateTicketIncomeApi(valueObject.getLong("tripSheetId"));
						if (validate == null || validate.isEmpty()) {
							
							UserProfileDto		userProfile = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(valueObject.getLong("userId"));
							
							TicketIncomeApi	incomeApi	= new TicketIncomeApi();
							
							JSONObject	finalObj	= jsonArray.getJSONObject(0);
							incomeApi.setTripSheetID(valueObject.getLong("tripSheetId"));
							incomeApi.setVid(valueObject.getInt("vid",0));
							incomeApi.setCompanyId(valueObject.getInt("companyId",0));
							incomeApi.setCreatedById(valueObject.getLong("userId", 0));
							incomeApi.setCreatedOn(dateTimeFormat.parse(valueObject.getString("ToDate")));
							
							incomeApi.setNetAmount(finalObj.getDouble("NetAmount"));
							incomeApi.setActualweight(finalObj.getDouble("ActualWeight"));
							incomeApi.setChargedWeight(finalObj.getDouble("ChargedWeight"));
							incomeApi.setQuantity(finalObj.getDouble("Quantity"));
							incomeApi.setLessCommission(finalObj.getDouble("LessCommission"));
							
							ticketIncomeApiRepository.save(incomeApi);
							
							TripSheet tsheet = new TripSheet();
							tsheet.setTripSheetID(valueObject.getLong("tripSheetId"));

							TripSheetIncome tripIncome = new TripSheetIncome();
							tripIncome.setIncomeId((Integer) configuration.get(TripSheetConfigurationConstant.TICKET_INCOME_ID));
							tripIncome.setIncomePlaceId(userProfile.getBranch_id());
							tripIncome.setIncomeRefence("Mantis Dispatch Income");
							tripIncome.setIncomeCollectedById(valueObject.getLong("userId", 0));
							tripIncome.setCompanyId(valueObject.getInt("companyId",0));
							tripIncome.setCreatedById(valueObject.getLong("userId", 0));
							tripIncome.setTripsheet(tsheet);
							tripIncome.setTicketIncomeApiId(incomeApi.getTicketIncomeApiId());
							tripIncome.setIncomeAmount(incomeApi.getLessCommission());
							tripIncome.setCreated(incomeApi.getCreatedOn());

							tripSheetService.addTripSheetIncome(tripIncome);
							
							tripSheetService
							.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(valueObject.getLong("tripSheetId"), valueObject.getInt("companyId",0));
							
							valueObject.put("ticketIncomeAdded", true);
						}
					} catch (Exception e) {
							System.err.println("No Data Found !");
					}
				}
			}else if(response.getStatus() == HttpStatus.SC_NO_CONTENT) {
				valueObject.put("noRecordFound", true);
			}else {
				valueObject.put("error", true);
			}
			
			
		} catch (Exception e) {
			System.err.println("Exception while  getting mantis income details  "+e);
		}
		return valueObject;
	}
	
	private String getBitlaAPIKey(HashMap<String, Object> 	configuration) throws Exception{
		try {
			String		apiKey			= null;
			
			HttpResponse<JsonNode> response = Unirest.post((String)configuration.get("bitlaApiKeyUrl"))
				  .header("Content-Type", "application/json")
				  .queryString("login", (String)configuration.get("bitlaApiUserName"))
				  .queryString("password", (String)configuration.get("bitlaApiPassword"))
				  .body("")
				  .asJson();
			
			if(response.getStatus() == HttpStatus.SC_OK) {
				JSONObject	jsonObject	=	response.getBody().getObject();
				apiKey = jsonObject.getString("key");
			}
			
			return apiKey;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	private ValueObject getITSGatewayBusIncome(ValueObject valueObject) throws Exception {
		HashMap<String, Object> 					configuration 					= null;
		JSONObject 									inputObj 						= null;
		String 										verifyCall 						= null;
		String 										apiURL 							= "";
		double										incomeAmount					= 0.0;
		ValueObject 								incomeDetails					= null;
		LinkedHashMap<Object, Object> 				incomeValue						= null;
		ArrayList<LinkedHashMap<Object, Object>> 	income							= null;
		
		try {
			inputObj 		= new JSONObject();
			configuration   = (HashMap<String, Object>) valueObject.get("configuration");

			verifyCall 		= (String) configuration.get(TripSheetConfigurationConstant.VERIFY_CALL_FOR_ITS_GATEWAY);
			apiURL 			= (String) configuration.get(TripSheetConfigurationConstant.URl_FOR_TCKT_INCM_API);

			inputObj.put(VehicleExtraDetailsConstant.FROM_DATE,   (Object)valueObject.getString(VehicleExtraDetailsConstant.FROM_DATE));
			inputObj.put(VehicleExtraDetailsConstant.TO_DATE,     (Object)valueObject.get(VehicleExtraDetailsConstant.TO_DATE));
			inputObj.put(VehicleExtraDetailsConstant.VERIFY_CALL, (Object)verifyCall);
			inputObj.put(VehicleExtraDetailsConstant.BUS_ID,      (Object)valueObject.getLong(VehicleExtraDetailsConstant.BUS_ID));

			final HttpResponse<String> response = (HttpResponse<String>)Unirest.post(apiURL).header("Content-Type", "application/json").body(inputObj).asString();
			
			if (response.getStatus() == 200) {
				incomeDetails = JsonConvertor.toValueObjectFormSimpleJsonString((String)response.getBody());
				
				if(incomeDetails.get("data") != null) {
                	incomeValue = (LinkedHashMap<Object, Object>) incomeDetails.get("data");
                	
                	if(!incomeValue.isEmpty() && incomeValue.get("Data") != null) {
                	    income	= (ArrayList<LinkedHashMap<Object, Object>>) incomeValue.get("Data");
                		
                		for (Map<Object, Object> entry : income) {
                			incomeAmount = (double) entry.get("Amount");
       						
       						java.util.Date date = dateFormat.parse((String)entry.get("IncomeDateTime"));
            				Timestamp createdDate = new java.sql.Timestamp(date.getTime());
       						
       						saveITSGatewayBusIncome(incomeAmount, createdDate, valueObject);
                		}	 
                	} else {
        				valueObject.put("noRecordFound", true);
        				System.err.println("Income Data Is Empty");
                	}
                }
				
				
			} else {
				valueObject.put("noRecordFound", true);
				System.err.println("Response of Income is not 200");
			}
			
			return valueObject;
			
		} catch (Exception e) {
			System.err.println("Exception get ITS " + e);
			return valueObject;
		} finally {
			configuration 		= null;
			inputObj 			= null;
			verifyCall 			= null;
			apiURL 				= null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private synchronized void saveITSGatewayBusIncome(double incomeAmount, Timestamp createdDate, ValueObject valueObject) throws Exception {
		HashMap<String, Object> 								configuration 				= null;
		CustomUserDetails 										userDetails 				= null;
		UserProfileDto 											userProfile 				= null;
		int 													flavor 						= 0;
		int 													incomeId 					= 0;
		HashMap<Long, TripSheetIncome>  tripSheetIncomeDtoHM = null;
		HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>  incomeTxnCheckerHashMap = null;
		TripSheet sheet = null;
		try {
			userDetails 			= (CustomUserDetails) valueObject.get("userDetails");
			configuration 			= (HashMap<String, Object>) valueObject.get("configuration");
			userProfile 			= (UserProfileDto) valueObject.get("userProfile");
			incomeId 				= (Integer) configuration.get(TripSheetConfigurationConstant.TICKET_INCOME_ID);
			sheet					= (TripSheet) valueObject.get("sheet");

			flavor = companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(),
						PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			valueObject.put("flavor", flavor);
			
			TripSheetIncome validateBusApi = tripSheetIncomeRepository.validateBusIncomeApi(incomeId, 
					valueObject.getLong("tripSheetId"), createdDate, userDetails.getCompany_id());
			
			if(validateBusApi == null) {
				tripSheetIncomeDtoHM = new HashMap<Long, TripSheetIncome>();
				incomeTxnCheckerHashMap = new HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>();
				
				if (flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {

					TripSheet tsheet = new TripSheet();
					tsheet.setTripSheetID(valueObject.getLong("tripSheetId"));

					TripSheetIncome TripIncome = new TripSheetIncome();
					TripIncome.setIncomeId(incomeId);
					TripIncome.setIncomePlaceId(userProfile.getBranch_id());
					TripIncome.setIncomeRefence("ITS Gateway Bus Income");
					TripIncome.setIncomeCollectedById(userDetails.getId());
					TripIncome.setCompanyId(userDetails.getCompany_id());
					TripIncome.setCreatedById(userDetails.getId());
					TripIncome.setTripsheet(tsheet);
					TripIncome.setIncomeAmount(incomeAmount);
					TripIncome.setCreated(createdDate);

					tripSheetService.addTripSheetIncome(TripIncome);
					
					if(TripIncome.getIncomeAmount() > 0.0 && sheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
						
						ValueObject incomeObject = new ValueObject();
						
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, valueObject.getLong("tripSheetId"));
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID,VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID,VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID,userDetails.getCompany_id());
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, sheet.getVid());
						incomeObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, TripIncome.getIncomeId());
						incomeObject.put(VehicleProfitAndLossDto.TRIP_INCOME_ID,TripIncome.getTripincomeID());
						
						VehicleProfitAndLossIncomeTxnChecker profitAndLossIncomeTxnChecker =
								txnCheckerBL.getVehicleProfitAndLossIncomeTxnChecker(incomeObject);
						vehicleProfitAndLossIncomeTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossIncomeTxnChecker);
						
						incomeTxnCheckerHashMap.put(profitAndLossIncomeTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossIncomeTxnChecker);
						tripSheetIncomeDtoHM.put(TripIncome.getTripincomeID(), TripIncome); 
						
					}
					

				} else if (flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {

				} else if (flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {

					TripDailySheet tsheet = new TripDailySheet();
					tsheet.setTRIPDAILYID(valueObject.getLong("tripSheetId"));

					TripDailyIncome TripDailyIncome = new TripDailyIncome();
					TripDailyIncome.setIncomeId(incomeId);
					TripDailyIncome.setIncomeRefence("ITS Gateway Bus Income");
					TripDailyIncome.setIncomeCollectedById(userDetails.getId());
					TripDailyIncome.setCompanyId(userDetails.getCompany_id());
					TripDailyIncome.setCreatedById(userDetails.getId());
					TripDailyIncome.setTripDailysheet(tsheet);
					TripDailyIncome.setIncomeAmount(incomeAmount);
					TripDailyIncome.setCreated(createdDate);

					TripDailySheetService.add_TripDailyIncome(TripDailyIncome);

					TripDailySheetService.update_TripDaily_TotalIncome(valueObject.getLong("tripSheetId"), userDetails.getCompany_id());

				}

				valueObject.put("itsGatewayIncomeAdded", true);
						
				tripSheetService
						.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(valueObject.getLong("tripSheetId"), userDetails.getCompany_id());
				valueObject.put("alreadyAdded", false);
				
				if(tripSheetIncomeDtoHM != null && tripSheetIncomeDtoHM.size() > 0) {
					ValueObject valObj = new ValueObject(); 
					valObj.put("tripSheet", sheet);
					valObj.put("userDetails", userDetails); 
					valObj.put("tripSheetIncomeHM", tripSheetIncomeDtoHM); 
					valObj.put("incomeTxnCheckerHashMap", incomeTxnCheckerHashMap); 
					valObj.put("isTripSheetClosed", true);
					
					vehicleProfitAndLossService.runThreadForTripSheetIncome(valObj); 
				}
			}else {
				
				valueObject.put("alreadyAdded", true);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			configuration 	= null;
			userDetails 	= null;
			userProfile 	= null;
			flavor 			= 0;
			incomeId		= 0;
		}
	}

}