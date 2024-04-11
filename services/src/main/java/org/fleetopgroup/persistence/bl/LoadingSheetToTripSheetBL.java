package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.TripRouteFixedType;
import org.fleetopgroup.constant.TripSheetConfigurationConstant;
import org.fleetopgroup.constant.WayBillTypeConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.LoadingSheetToTripSheetDto;
import org.fleetopgroup.persistence.model.LoadingSheetToTripSheet;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;

public class LoadingSheetToTripSheetBL {

	@SuppressWarnings("unchecked")
	public ValueObject	getLoadingSheetToTripSheetDto(JSONArray	jsonArray, ValueObject	valueObject, HashMap<String, LoadingSheetToTripSheet> validate) throws Exception {
		LoadingSheetToTripSheet					loadingSheetToTripSheetDto		= null;
		List<LoadingSheetToTripSheet>			loadingSheetToTripSheetList		= null;
		HashMap<String, LoadingSheetToTripSheet> hashMap						= null;
		LoadingSheetToTripSheet					tempDetails						= null;
		ValueObject								valueOutObject					= null;
		CustomUserDetails						userDetails						= null;
		HashMap<String, Object> 				configuration					= null;
		boolean									includeOnlyToPayLrs				= false;
		
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			configuration		= (HashMap<String, Object>) valueObject.get("configuration");
			includeOnlyToPayLrs = (boolean) configuration.get("includeOnlyToPayLrs");
			
			
			loadingSheetToTripSheetList	= new ArrayList<>();
			if(jsonArray.length() > 0) {
				valueOutObject	= new ValueObject();
				hashMap	= new HashMap<>();
				
				
				for(int i = 0; i<jsonArray.length(); i++) {
					
					if(includeOnlyToPayLrs && (short) jsonArray.getJSONObject(i).getLong("wayBillTypeId") != WayBillTypeConstant.WAYBILL_TYPE_TOPAY) {
						continue;
					}
					loadingSheetToTripSheetDto	= new LoadingSheetToTripSheet();
					
					if(jsonArray.getJSONObject(i).has("wayBillTypeId")) {
						loadingSheetToTripSheetDto.setWaybillTypeId((short) jsonArray.getJSONObject(i).getLong("wayBillTypeId"));
					}else {
						loadingSheetToTripSheetDto.setWaybillTypeId((short) 0);
					}
				
					loadingSheetToTripSheetDto.setDispatchLedgerId(jsonArray.getJSONObject(i).getLong("dispatchLedgerId"));
					loadingSheetToTripSheetDto.setWayBillId(jsonArray.getJSONObject(i).getLong("wayBillId"));
					loadingSheetToTripSheetDto.setWayBillNumber(jsonArray.getJSONObject(i).getString("wayBillNumber"));
					loadingSheetToTripSheetDto.setBookingTotal(jsonArray.getJSONObject(i).getDouble("bookingTotal"));
					loadingSheetToTripSheetDto.setFreight(jsonArray.getJSONObject(i).getDouble("freight"));
					
					if(jsonArray.getJSONObject(i).has("totalActualWeight")) 
						loadingSheetToTripSheetDto.setTotalActualWeight(jsonArray.getJSONObject(i).getDouble("totalActualWeight"));
					
					if(jsonArray.getJSONObject(i).has("dispatchedWeight")) 
						loadingSheetToTripSheetDto.setDispatchedWeight(jsonArray.getJSONObject(i).getDouble("dispatchedWeight"));
					
					if(jsonArray.getJSONObject(i).has("lsNumber")) {
						loadingSheetToTripSheetDto.setLsNumber(jsonArray.getJSONObject(i).getString("lsNumber"));
					}else {
						loadingSheetToTripSheetDto.setLsNumber(jsonArray.getJSONObject(i).getLong("dispatchLedgerId")+"");
					}
					
					loadingSheetToTripSheetDto.setTotalNoOfPackages(jsonArray.getJSONObject(i).getInt("totalNoOfPackages"));
					loadingSheetToTripSheetDto.setWayBillCrossingId(jsonArray.getJSONObject(i).getLong("wayBillCrossingId"));
					loadingSheetToTripSheetDto.setTripDateTime(new Timestamp((long)jsonArray.getJSONObject(i).get("tripDateTime")));
					loadingSheetToTripSheetDto.setTripSheetId(valueObject.getLong("tripSheetId"));
					loadingSheetToTripSheetDto.setCompanyId(userDetails.getCompany_id());
			    	loadingSheetToTripSheetDto.setLrDestinationBranchId(jsonArray.getJSONObject(i).getLong("lrDestinationBranchId"));
			    	loadingSheetToTripSheetDto.setLsDestinationBranchId(jsonArray.getJSONObject(i).getLong("lsDestinationBranchId"));
					loadingSheetToTripSheetDto.setLrSourceBranchId(jsonArray.getJSONObject(i).getLong("lrSourceBranchId"));
					loadingSheetToTripSheetDto.setLsSourceBranchId(jsonArray.getJSONObject(i).getLong("lsSourceBranchId"));
					loadingSheetToTripSheetDto.setLrSourceBranch(jsonArray.getJSONObject(i).getString("lrSourceBranch"));
					loadingSheetToTripSheetDto.setLsSourceBranch(jsonArray.getJSONObject(i).getString("lsSourceBranch"));
					loadingSheetToTripSheetDto.setLsDestinationBranch(jsonArray.getJSONObject(i).getString("lsDestinationBranch"));
					loadingSheetToTripSheetDto.setLrDestinationBranch(jsonArray.getJSONObject(i).getString("lrDestinationBranch"));
					tempDetails	= hashMap.get(loadingSheetToTripSheetDto.getDispatchLedgerId()+"_"+loadingSheetToTripSheetDto.getWayBillId());
					
					if(jsonArray.getJSONObject(i).has("billSelectionId"))
						loadingSheetToTripSheetDto.setBillSelectionId((short)jsonArray.getJSONObject(i).getLong("billSelectionId"));
					if(tempDetails == null) {
						tempDetails	= loadingSheetToTripSheetDto;
						hashMap.put(loadingSheetToTripSheetDto.getDispatchLedgerId()+"_"+loadingSheetToTripSheetDto.getWayBillId(), tempDetails);
						
						if(validate == null || validate.get(loadingSheetToTripSheetDto.getDispatchLedgerId()+"_"+loadingSheetToTripSheetDto.getWayBillId()) == null ) {
							if((boolean) configuration.get("checkLSAndLRDestinationBranchId"))
							{
								if(tempDetails.getLsDestinationBranchId() == tempDetails.getLrDestinationBranchId())
									loadingSheetToTripSheetList.add(tempDetails);
							}
							else
								loadingSheetToTripSheetList.add(tempDetails);
						}
					} 
					
				}
				
				valueOutObject.put("loadingSheetToTripSheetList", loadingSheetToTripSheetList);
			}
			
			return valueOutObject;
		} catch (Exception e) {
			throw	e;
		}finally {
			loadingSheetToTripSheetDto		= null;
			loadingSheetToTripSheetList		= null;
			hashMap							= null;
			tempDetails						= null;
			valueOutObject					= null;
			userDetails						= null;
			configuration					= null;
			includeOnlyToPayLrs				= false;
		}
	}
	
		
	@SuppressWarnings("unchecked")
	public List<TripSheetIncome>  getLoadingSheetToTripSheetIncome(List<LoadingSheetToTripSheet>		loadingSheetToTripSheetList, ValueObject	valueObject, List<Long> validate) throws Exception{
		
		List<TripSheetIncome>			tripSheetIncomeList			= null;
		TripSheetIncome					tripSheetIncome				= null;
		HashMap<Long, TripSheetIncome>	tempIncomeHM				= null;
		TripSheetIncome					tempIncome					= null;
		CustomUserDetails				userDetails						= null;
		HashMap<String, Object> 		configuration					= null;
		short							loadingSheetChargesType			= 0;
		boolean							includeOnlyToPayLrs				= false;
		boolean							allowedToAdd					= true;
		try {
			userDetails			= (CustomUserDetails) valueObject.get("userDetails");
			configuration		= (HashMap<String, Object>) valueObject.get("configuration");
			includeOnlyToPayLrs = (boolean) configuration.get("includeOnlyToPayLrs");
			loadingSheetChargesType	= Short.parseShort(configuration.get("loadingSheetChargesType")+"");
				if(loadingSheetToTripSheetList != null && !loadingSheetToTripSheetList.isEmpty()) {
					tempIncomeHM	= new HashMap<>();
					tripSheetIncomeList	= new ArrayList<>();
					for(LoadingSheetToTripSheet		sheetToTripSheet : loadingSheetToTripSheetList) {
						if(includeOnlyToPayLrs && sheetToTripSheet.getWaybillTypeId() != WayBillTypeConstant.WAYBILL_TYPE_TOPAY) {
							allowedToAdd	= false;
						}
						if(sheetToTripSheet.getBookingTotal() > 0 && allowedToAdd) {
							tripSheetIncome	= new TripSheetIncome();
							TripSheet tripSheet = new TripSheet();
							tripSheet.setTripSheetID(sheetToTripSheet.getTripSheetId());
							
							tripSheetIncome.setTripsheet(tripSheet);
							tripSheetIncome.setIncomeId(valueObject.getInt("loadingSheetIncomeId"));
							tripSheetIncome.setIncomeFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
							tripSheetIncome.setIncomeRefence("LS Number: "+sheetToTripSheet.getLsNumber());
							tripSheetIncome.setCreatedById(userDetails.getId());
							tripSheetIncome.setIncomePlaceId(0);
							tripSheetIncome.setDispatchLedgerId(sheetToTripSheet.getDispatchLedgerId());
							tripSheetIncome.setIncomeCollectedById(userDetails.getId());
							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

							tripSheetIncome.setCreated(toDate);
							tripSheetIncome.setCompanyId(userDetails.getCompany_id());
				
							tripSheetIncome.setBillSelectionId(sheetToTripSheet.getBillSelectionId());
							
							if(sheetToTripSheet.getWayBillCrossingId() == 0 || (boolean)configuration.getOrDefault(TripSheetConfigurationConstant.DO_NOT_CONSIDER_CROSS_LR, false)) {
								if(loadingSheetChargesType == LoadingSheetToTripSheetDto.LOADING_SHEET_CHARGES_TYPE_FREIGHT) {
									tripSheetIncome.setIncomeAmount(sheetToTripSheet.getFreight());
								}else {
									tripSheetIncome.setIncomeAmount(sheetToTripSheet.getBookingTotal());
								}
							}else {
								if(loadingSheetChargesType == LoadingSheetToTripSheetDto.LOADING_SHEET_CHARGES_TYPE_FREIGHT) {
									tripSheetIncome.setIncomeAmount(sheetToTripSheet.getFreight()/2);
								}else {
									tripSheetIncome.setIncomeAmount(sheetToTripSheet.getBookingTotal()/2);
								}
							}
							
							tempIncome	=	tempIncomeHM.get(sheetToTripSheet.getDispatchLedgerId());
							
							if(tempIncome == null) {
								tempIncome	= tripSheetIncome;
							}else {
								tempIncome.setIncomeAmount(tempIncome.getIncomeAmount() + tripSheetIncome.getIncomeAmount());
							}
							if(!validate.contains(sheetToTripSheet.getDispatchLedgerId()))
								tempIncomeHM.put(sheetToTripSheet.getDispatchLedgerId(), tempIncome );
						}
					}
					tripSheetIncomeList.addAll(tempIncomeHM.values());
				}
				
			return tripSheetIncomeList;
		} catch (Exception e) {
			throw e;
		}finally {
			tripSheetIncomeList			= null;
			tripSheetIncome				= null;
			tempIncomeHM				= null;
			tempIncome					= null;
			userDetails					= null;
			configuration				= null;
			loadingSheetChargesType		= 0;
		}
	}
}
