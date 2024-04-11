package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.LHPVConfigurationConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.LHPVBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.LHPVDetailsDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.LHPVDetails;
import org.fleetopgroup.persistence.model.LHPVSettlementCharges;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetAdvance;
import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.fleetopgroup.persistence.model.TripSheetToLhpvDetails;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ILHPVDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ILHPVSettlementChargesService;
import org.fleetopgroup.persistence.serviceImpl.ILHPVtoTripSheetLinkingService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetToLhpvDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

@Service
public class LHPVtoTripSheetLinkingService implements ILHPVtoTripSheetLinkingService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	private @Autowired ILHPVDetailsService				lhpvDetailsService;
	private @Autowired IVehicleService					vehicleService;
	private @Autowired ILHPVSettlementChargesService	settlementChargesService;
	private @Autowired ICompanyConfigurationService		companyConfigurationService;
	private @Autowired ITripSheetService				tripSheetService;
	private	@Autowired IUserProfileService 				userProfileService;
	private @Autowired ITripSheetToLhpvDetailsService	tripSheetToLhpvDetailsService;
	
	LHPVBL	lHPVBL	= new LHPVBL();
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject syncDataOfLhpvFromIvCargo(ValueObject valueObject) throws Exception {
		JSONArray						jsonArray				= null;
		HttpResponse<JsonNode> 			response 				= null;
		ValueObject						serObject				= null;
		CustomUserDetails				userDetails				= null;
		Vehicle							vehicle					= null;
		HashMap<String, Object>        	configuration			= null;
		Timestamp						lastLhpvSyncDateTime	= null;
		Timestamp						currentTimeStamp		= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicle			=	vehicleService.getVehicel(valueObject.getInt("vid"), userDetails.getCompany_id());
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.LHPV_INTEGRATION_CONFIG);
			
			
			if(vehicle != null) {
				currentTimeStamp	= DateTimeUtility.getCurrentTimeStamp();
				if(vehicle.getLastLhpvSyncDateTime() != null)
						lastLhpvSyncDateTime	= vehicle.getLastLhpvSyncDateTime();
				
				if(lastLhpvSyncDateTime == null) {
					response = Unirest.post((String)configuration.get(LHPVConfigurationConstant.LHPV_INTEGRATION_URL))
							.header("cache-control", "no-cache").field("vehicleNumber", vehicle.getVehicle_registration())
							.asJson();
				}else {
					response = Unirest.post((String)configuration.get(LHPVConfigurationConstant.LHPV_INTEGRATION_URL))
							.header("cache-control", "no-cache").field("vehicleNumber", vehicle.getVehicle_registration())
							.field("lastSyncDate", DateTimeUtility.getDateFromTimeStamp(lastLhpvSyncDateTime, DateTimeUtility.DD_MM_YYYY))
							.asJson();
				}
				
				
				if(response.getStatus() == 200) {
					
					if( response.getBody().getArray().getJSONObject(0).has("lhpvList")) {
						
						jsonArray	=	 (JSONArray) response.getBody().getArray().getJSONObject(0).get("lhpvList");
						
						List<LHPVDetailsDto>	list =  lHPVBL.getLHPVDetailsDTO(jsonArray, valueObject);
						
						serObject	= getLHPVDetailsModelToSave(list, userDetails);
						
						lhpvDetailsService.saveLHPVDetailsList((List<LHPVDetails>) serObject.get("lhpvDetailsList"));
						
						settlementChargesService.saveLHPVSettlementChargesList((List<LHPVSettlementCharges>) serObject.get("lhpvSettlementCharges"));
						
						vehicleService.updateLastLhpvSyncDateTime(vehicle.getVid(), currentTimeStamp);
					}
					
					
				}
				
				valueObject.put("lhpvDetailsList", getLHPVDetailsDtoList(vehicle.getVid()));
			}else {
				valueObject.put("noVehicleFound", true);
			}
			
			
			return valueObject;
		} catch (Exception e) {
			valueObject.put("lhpvDetailsList", getLHPVDetailsDtoList(vehicle.getVid()));
			LOGGER.error("Error : " , e);
			return valueObject;
		} finally {
			jsonArray		= null;
			response 		= null;
			serObject		= null;
			userDetails		= null;
			vehicle			= null;
		}
	}
	
	private ValueObject  getLHPVDetailsModelToSave(List<LHPVDetailsDto>	lhpvDetailsDtoList, CustomUserDetails	userDetails) throws Exception{
		List<LHPVDetails>					lhpvDetailsList				= null;
		LHPVDetails							lhpvDetails					= null;
		HashMap<Long, LHPVDetails> 			lHPVDetailsHM				= null;
		ValueObject							valueObject					= null;
		List<LHPVSettlementCharges> 		lhpvSettlementList			= null;
		LHPVSettlementCharges				lhpvSettlementCharges		= null;
		List<LHPVDetails>					validate					= null;
		try {
					lhpvDetailsList		= new ArrayList<>();
					lHPVDetailsHM		= new HashMap<>();
					valueObject			= new ValueObject();
					lhpvSettlementList	= new ArrayList<>();
			
					if(lhpvDetailsDtoList != null && !lhpvDetailsDtoList.isEmpty()) {
						
						for(LHPVDetailsDto		lhpvDetailsDto :	lhpvDetailsDtoList) {
							if(lhpvDetailsDto.getLhpvChargeTypeMasterId() == 4) {
								
								validate	=	lhpvDetailsService.validateLHPVDetails(lhpvDetailsDto.getLhpvId());
								if(validate == null || validate.isEmpty()) {
									
									lhpvDetails	= lHPVDetailsHM.get(lhpvDetailsDto.getLhpvId());
									if(lhpvDetails == null) {
										lhpvDetails	= new LHPVDetails();
										lhpvDetails.setLhpvId(lhpvDetailsDto.getLhpvId());
										lhpvDetails.setAccountGroupId(lhpvDetailsDto.getAccountGroupId());
										lhpvDetails.setLhpvDateTimeStamp(lhpvDetailsDto.getLhpvDateTimeStamp());
										lhpvDetails.setTotalAmount(lhpvDetailsDto.getTotalAmount());
										lhpvDetails.setAdvanceAmount(lhpvDetailsDto.getAdvanceAmount());
										lhpvDetails.setBalanceAmount(lhpvDetailsDto.getBalanceAmount());
										lhpvDetails.setVehicleNumberMasterId(lhpvDetailsDto.getVehicleNumberMasterId());
										lhpvDetails.setlHPVNumber(lhpvDetailsDto.getlHPVNumber());
										lhpvDetails.setRemark(lhpvDetailsDto.getRemark());
										lhpvDetails.setLorryHire(lhpvDetailsDto.getChargeAmount());
										lhpvDetails.setVid(lhpvDetailsDto.getVid());
										lhpvDetails.setCompanyId(userDetails.getCompany_id());
										lhpvDetails.setSyncDateTime(DateTimeUtility.getCurrentTimeStamp());
										lhpvDetails.setTotalActualWeight(lhpvDetailsDto.getTotalActualWeight());
										lhpvDetails.setBoliWeight(lhpvDetailsDto.getBoliWeight());
										
										lhpvDetailsList.add(lhpvDetails);
									}
									
									lHPVDetailsHM.put(lhpvDetails.getLhpvId(), lhpvDetails);
								}
							}
							if(validate == null || validate.isEmpty()) {
								lhpvSettlementCharges	= new LHPVSettlementCharges();
								
								lhpvSettlementCharges.setLhpvId(lhpvDetailsDto.getLhpvId());
								lhpvSettlementCharges.setLhpvDateTimeStamp(lhpvDetailsDto.getLhpvDateTimeStamp());
								lhpvSettlementCharges.setLhpvChargeMasterId(lhpvDetailsDto.getLhpvChargeTypeMasterId());
								lhpvSettlementCharges.setChargeAmount(lhpvDetailsDto.getChargeAmount());
								lhpvSettlementCharges.setChargeName(lhpvDetailsDto.getChargeName());
								lhpvSettlementCharges.setCompanyId(userDetails.getCompany_id());
								
								lhpvSettlementList.add(lhpvSettlementCharges);
							}	
							
						}
					}
					
					valueObject.put("lhpvDetailsList", lhpvDetailsList);
					valueObject.put("lhpvSettlementCharges", lhpvSettlementList);
					
			
			return valueObject;
		} catch (Exception e) {
			throw	e;
		}finally {
			lhpvDetailsList				= null;
			lhpvDetails					= null;
			lHPVDetailsHM				= null;
			valueObject					= null;
			lhpvSettlementList			= null;
			lhpvSettlementCharges		= null;
		}
	}
	
	
	public List<LHPVDetailsDto>  getLHPVDetailsDtoList(Integer vid) throws Exception{
		List<LHPVDetails> 		lHPVDetailsList				= null;
		List<LHPVDetailsDto>	lHPVDetailsDtoList			= null;
		try {
			
			lHPVDetailsDtoList	= new ArrayList<>();
			
			lHPVDetailsList	= lhpvDetailsService.getLHPVDetailsDtoList(vid);
			if(lHPVDetailsList != null && !lHPVDetailsList.isEmpty()) {
				LHPVDetailsDto		detailsDto		= null;
				for(LHPVDetails 	lhpvDetails : lHPVDetailsList) {
					detailsDto	= new LHPVDetailsDto();
					
					detailsDto.setAdvanceAmount(lhpvDetails.getAdvanceAmount());
					detailsDto.setLorryHire(lhpvDetails.getLorryHire());
					detailsDto.setBalanceAmount(lhpvDetails.getBalanceAmount());
					detailsDto.setlHPVDetailsId(lhpvDetails.getlHPVDetailsId());
					detailsDto.setlHPVNumber(lhpvDetails.getlHPVNumber());
					detailsDto.setLhpvDateTime(DateTimeUtility.getDateFromTimeStamp(lhpvDetails.getLhpvDateTimeStamp(), DateTimeUtility.DD_MM_YYYY));
					detailsDto.setLhpvId(lhpvDetails.getLhpvId());
					
					lHPVDetailsDtoList.add(detailsDto);
				}
			}
			return	lHPVDetailsDtoList;
		} catch (Exception e) {
			LOGGER.error("Error : ", e);
			throw e;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject syncLhpvDetailsToAddInTripSheet(ValueObject valueObject) throws Exception {
		JSONArray						jsonArray				= null;
		HttpResponse<JsonNode> 			response 				= null;
		ValueObject						serObject				= null;
		CustomUserDetails				userDetails				= null;
		Vehicle							vehicle					= null;
		HashMap<String, Object>        	configuration			= null;
		Timestamp						lastLhpvSyncDateTime	= null;
		Timestamp						currentTimeStamp		= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicle			= vehicleService.getVehicel(valueObject.getInt("vid"), userDetails.getCompany_id());
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.LHPV_INTEGRATION_CONFIG);
			
			
			if(vehicle != null) {
				currentTimeStamp	= DateTimeUtility.getCurrentTimeStamp();
				if(vehicle.getLastLhpvSyncDateTime() != null)
						lastLhpvSyncDateTime	= vehicle.getLastLhpvSyncDateTime();
				
				if(lastLhpvSyncDateTime == null) {
					response = Unirest.post((String)configuration.get(LHPVConfigurationConstant.LHPV_INTEGRATION_URL))
							.header("cache-control", "no-cache").field("vehicleNumber", vehicle.getVehicle_registration())
							.asJson();
				}else {
					response = Unirest.post((String)configuration.get(LHPVConfigurationConstant.LHPV_INTEGRATION_URL))
							.header("cache-control", "no-cache").field("vehicleNumber", vehicle.getVehicle_registration())
							.field("lastSyncDate", DateTimeUtility.getDateFromTimeStamp(lastLhpvSyncDateTime, DateTimeUtility.DD_MM_YYYY))
							.asJson();
				}
				
				
				if(response.getStatus() == 200) {
					
					if( response.getBody().getArray().getJSONObject(0).has("lhpvList")) {
						
						jsonArray	=	 (JSONArray) response.getBody().getArray().getJSONObject(0).get("lhpvList");
						
						List<LHPVDetailsDto>	list =  lHPVBL.getLHPVDetailsDTO(jsonArray, valueObject);
						
						serObject	= getLHPVDetailsModelToSave(list, userDetails);
						
						lhpvDetailsService.saveLHPVDetailsList((List<LHPVDetails>) serObject.get("lhpvDetailsList"));
						
						settlementChargesService.saveLHPVSettlementChargesList((List<LHPVSettlementCharges>) serObject.get("lhpvSettlementCharges"));
						
						vehicleService.updateLastLhpvSyncDateTime(vehicle.getVid(), currentTimeStamp);
					}
					
					
				}
				
				valueObject.put("lhpvDetailsList", getLHPVDetailsDtoList(vehicle.getVid()));
				valueObject.put("tripSheet", tripSheetService.getTripSheetDetails(valueObject.getLong("tripSheetId"), userDetails));
				
			}else {
				valueObject.put("noVehicleFound", true);
			}
			
			
			return valueObject;
		} catch (Exception e) {
			valueObject.put("lhpvDetailsList", getLHPVDetailsDtoList(vehicle.getVid()));
			LOGGER.error("Error : " , e);
			return valueObject;
		} finally {
			jsonArray		= null;
			response 		= null;
			serObject		= null;
			userDetails		= null;
			vehicle			= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveLhpvDetailsToTripSheet(ValueObject valueObject) throws Exception {
		
		String							lhpvIds					= null;
		List<LHPVDetails>				lhpvDetails				= null;
		CustomUserDetails				userDetails				= null;
		List<TripSheetAdvance>  		tripAdvanceList			= null;
		List<TripSheetIncome>  			tripSheetIncomeList		= null;
		List<TripSheetToLhpvDetails>	tripToLhpv				= null;
		String							lhpvBookRef				= "";
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			UserProfileDto Orderingname = userProfileService
					.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
			
			lhpvIds	= valueObject.getString("lhpvIds");
			
			lhpvDetails	= lhpvDetailsService.getLHPVDetailsList(lhpvIds, userDetails.getCompany_id());
			
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			if(lhpvDetails != null && !lhpvDetails.isEmpty()) {
				
				
				TripSheet	tripSheet	= new TripSheet();
				tripSheet.setTripSheetID(valueObject.getLong("tripSheetId"));
				
				tripAdvanceList		= new ArrayList<>();
				tripSheetIncomeList	= new ArrayList<>();
				tripToLhpv			= new ArrayList<>();
				lhpvBookRef 		+= valueObject.getString("tripBookref")+" , Lhpv: ";
				
				for(LHPVDetails  details : lhpvDetails) {
					
					lhpvBookRef += details.getlHPVNumber()+",";
					
					if(details != null && details.getLorryHire() > 0) {
						TripSheetIncome	tripIncome	= new TripSheetIncome();

						tripIncome.setIncomeId(Integer.parseInt("" + details.getlHPVDetailsId()));
						tripIncome.setIncomeAmount(details.getLorryHire());
						tripIncome.setIncomePlaceId(Orderingname.getBranch_id());
						tripIncome.setIncomeRefence("LHPV NO : "+details.getlHPVNumber());
						tripIncome.setIncomeCollectedById(userDetails.getId());
						tripIncome.setCompanyId(userDetails.getCompany_id());
						tripIncome.setCreatedById(userDetails.getId());
						tripIncome.setTripsheet(tripSheet);
						tripIncome.setlHPVDetailsId(details.getlHPVDetailsId());
						
						tripIncome.setCreated(toDate);
						
						tripSheetIncomeList.add(tripIncome);
						
					}
					
					if(details != null && details.getAdvanceAmount() > 0) {
						TripSheetAdvance	sheetAdvance = new TripSheetAdvance();
						sheetAdvance.setAdvanceAmount(details.getAdvanceAmount());

						sheetAdvance.setAdvancePlaceId(Orderingname.getBranch_id());
						sheetAdvance.setAdvancePaidbyId(userDetails.getId());
						sheetAdvance.setAdvanceRefence("LHPV NO : "+details.getlHPVNumber());
						sheetAdvance.setAdvanceRemarks("LHPV Advance For Lhpv NO "+details.getlHPVNumber());
						sheetAdvance.setTripsheet(tripSheet);
						sheetAdvance.setCompanyId(userDetails.getCompany_id());
						sheetAdvance.setCreatedById(userDetails.getId());

						sheetAdvance.setCreated(toDate);
						tripAdvanceList.add(sheetAdvance);
						//tripSheetService.addTripSheetAdvance(sheetAdvance);
					}
					
					TripSheetToLhpvDetails	sheetToLhpvDetails	= new TripSheetToLhpvDetails();
					
					sheetToLhpvDetails.setTripSheetId(tripSheet.getTripSheetID());
					sheetToLhpvDetails.setLhpvId(details.getLhpvId());
					sheetToLhpvDetails.setLhpvNumber(details.getlHPVNumber());
					sheetToLhpvDetails.setLhpvDate(details.getLhpvDateTimeStamp());
					sheetToLhpvDetails.setLhpvAdvance(details.getAdvanceAmount());
					sheetToLhpvDetails.setLhpvLorryHire(details.getLorryHire());
					sheetToLhpvDetails.setCompanyId(userDetails.getCompany_id());
					
					tripToLhpv.add(sheetToLhpvDetails);
					
				}
				
				tripSheetService.saveTripAdvanceList(tripAdvanceList);
				tripSheetService.saveTripIncomeList(tripSheetIncomeList);
				tripSheetToLhpvDetailsService.saveTripSheetToLhpvDetailsList(tripToLhpv);
				
				lhpvDetailsService.updateTripSheetCreated(lhpvIds, tripSheet.getTripSheetID(), userDetails.getCompany_id());
				
				tripSheetService.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(tripSheet.getTripSheetID(), userDetails.getCompany_id());
				tripSheetService.UPDATE_TOTALADVANCE_IN_TRIPSHEET_TRIPSHEETADVANCE_ID(tripSheet.getTripSheetID(), userDetails.getCompany_id());
				
				tripSheetService.updateBookRefToLhpvIntripSheet(lhpvBookRef, tripSheet.getTripSheetID());
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Error : " , e);
			throw e;
		}
	}
}
