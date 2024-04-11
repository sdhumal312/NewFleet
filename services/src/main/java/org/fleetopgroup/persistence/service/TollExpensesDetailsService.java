package org.fleetopgroup.persistence.service;

import java.net.SocketTimeoutException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.FastTagBankConstant;
import org.fleetopgroup.constant.MyTrustManager;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.constant.TollApiConfiguration;
import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.constant.TripRouteFixedType;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.dao.TollExpenseDataRepository;
import org.fleetopgroup.persistence.dao.TollExpensesDetailsRepository;
import org.fleetopgroup.persistence.dto.AuthTokenInfo;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.TollExpensesDetailsDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.TollExpenseData;
import org.fleetopgroup.persistence.model.TollExpensesDetails;
import org.fleetopgroup.persistence.model.TripDailyExpense;
import org.fleetopgroup.persistence.model.TripDailySheet;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.model.VehicleTollDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ITollExpensesDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTollService;
import org.fleetopgroup.web.util.AESEncryptionDecryption;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class TollExpensesDetailsService implements ITollExpensesDetailsService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@PersistenceContext public EntityManager entityManager;
	
	@Autowired	private ICompanyConfigurationService			companyConfigurationService;
	@Autowired	private TollExpensesDetailsRepository			tollExpensesDetailsRepository;
	@Autowired	private ITripSheetService						tripSheetService;
	@Autowired	private IUserProfileService						userProfileService;
	@Autowired	private ITripDailySheetService					tripDailySheetService;
	@Autowired	private VehicleProfitAndLossTxnCheckerService	vehicleProfitAndLossTxnCheckerService;
	@Autowired	private IVehicleTollService						vehicleTollService;
	@Autowired	private	IVehicleProfitAndLossService			vehicleProfitAndLossService;
	@Autowired	private TollExpenseDataRepository				tollExpenseDataRepository;
	
	SimpleDateFormat dateFormatonlyDate = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormat 		= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat tallyFormat		= new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sqlFormat 			= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	private SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MMM/yy hh:mm:ss");
	
	VehicleProfitAndLossTxnCheckerBL	txnCheckerBL = new VehicleProfitAndLossTxnCheckerBL();	
		
	
	@Override
	public ValueObject addVehicleTripTollExpenses(ValueObject valueObject) throws Exception {
		HashMap<String, Object> 		configuration		= null;
		CustomUserDetails				userDetails			= null;
		UserProfileDto					userProfile			= null;
		TripSheet						sheet				= null;
		ValueObject						valueOutObject		= null;
		ValueObject						objectForPlICICI	= null;
		ValueObject						objectForPlKVB		= null;
		ValueObject						objectForPlHDFC		= null;
		ValueObject						objectForPlYesBank	= null;
		ValueObject						objectForPlKarins	= null;
		try {
			valueOutObject	= new ValueObject();
			userDetails		= (CustomUserDetails) valueObject.get("userDetails");
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TOLL_API_CONFIG);
			userProfile		= userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId());
			sheet			= tripSheetService.getTripSheetData(valueObject.getLong("tripSheetId"),userDetails.getCompany_id());

			valueObject.put("configuration", configuration);
			System.err.println("configuration "+configuration);
			valueObject.put("userProfile", userProfile);
			valueObject.put("sheet", sheet);
			
			if((boolean) configuration.get(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION))
				objectForPlICICI	= getICICIBankTollCharges(valueObject);
			if((boolean) configuration.get(TollApiConfiguration.ALLOW_KVB_BANK_TOLL_API))
				objectForPlKVB	=	getKVBBankFastTagTollCharges(valueObject);
			if((boolean) configuration.get(TollApiConfiguration.ALLOW_HDFC_BANK_FASTTAG))
				objectForPlHDFC	=	getHDFCBankFastTagTollCharges(valueObject);
			if((boolean) configuration.get(TollApiConfiguration.ALLOW_YES_BANK_FASTTAG))
				objectForPlYesBank	=	getYesBankFastTagTollCharges(valueObject);
			if((boolean) configuration.get(TollApiConfiguration.ALLOW_KARINS_FASTTAG))
				objectForPlKarins	=	getKarinsFastTagTollCharges(valueObject);
			
			
				
			valueOutObject.put("tollExpensesAdded", true);
			valueOutObject.put("objectForPlKVB", objectForPlKVB);
			valueOutObject.put("objectForPlICICI", objectForPlICICI);
			valueOutObject.put("objectForPlHDFC", objectForPlHDFC);
			valueOutObject.put("objectForPlYesBank", objectForPlYesBank);
			valueOutObject.put("objectForPlKarins", objectForPlKarins);
			valueOutObject.put("flavor", companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG));
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
			userProfile			= null;
			userDetails			= null;
			sheet				= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private ValueObject	getKVBBankFastTagTollCharges(ValueObject	valueObject) throws Exception{
		HashMap<String, Object> 	configuration		= null;
		try {
			configuration	= (HashMap<String, Object>) valueObject.get("configuration");
			AuthTokenInfo	authTokenInfo	=	sendTokenRequest(configuration);
			return getTollTransactionDetails(authTokenInfo, valueObject);
		} catch (Exception e) {
			logger.error("Toll Expense Page Page:", e);
			return null;
		}finally {
			configuration		= null;
		}
	}
	
	private ValueObject	getHDFCBankFastTagTollCharges(ValueObject	valueObject) throws Exception{
		try {
			return getHdfcBankTollCharges(valueObject);
		} catch (Exception e) {
			logger.error("Toll Expense Page Page:", e);
			return null;
		}
	}
	
	private ValueObject	getYesBankFastTagTollCharges(ValueObject	valueObject) throws  Exception{
		List<TollExpensesDetails>		tollExpensesDetails	= null;
		JSONArray						jsonArray			= null;
		ValueObject						objectForPl			= null;
		try {
			if(valueObject.containsKey("tollid") && valueObject.getString("tollid") != null) {
				int  startIndex	= 0;
				int  endIndex	= 10;
				int maxSize		= 10;
				Timestamp fromDate  = DateTimeUtility.getTimeStamp(valueObject.getString(TollExpensesDetailsDto.START_TRANSACTION_DATE), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				Timestamp toDate	= DateTimeUtility.getTimeStamp(valueObject.getString(TollExpensesDetailsDto.END_TRANSACTION_DATE), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
				HttpResponse<JsonNode> response =  null;
				do {
					response = Unirest.get("https://loconav.com/api/v3/expense_engine/yesbank/fastag/"+valueObject.getString("tollid")+"/txns")
							.header("Authorization", "Lyegz-fP_SURyKG4zTgM")
							.header("Content-Type", "application/json")
							.queryString("start_time", DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.DD_MM_YY_HH_MM_SS))
							.queryString("end_time", DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.DD_MM_YY_HH_MM_SS))
							.queryString("start_index", startIndex)
							.queryString("end_index", endIndex)
							.queryString("ignore_dup_txns", true)
							.asJson();  
					
					jsonArray	= response.getBody().getArray();
					
					startIndex	= endIndex;
					endIndex	= maxSize + endIndex;
					tollExpensesDetails	= getTollExpensesDetailsDtoForYesBank(jsonArray, valueObject, tollExpensesDetails);
					
				} while (jsonArray.length() == maxSize);	
				
				if(tollExpensesDetails != null && !tollExpensesDetails.isEmpty())
					objectForPl	= saveTollTransactionDetail(tollExpensesDetails, valueObject);
			}else {
				logger.error("Toll Id Not Found");
			}
			
			return objectForPl;
		} catch (Exception e) {
			logger.error("Toll Expense Page Page:", e);
			return null;
		}
	}
	
	private ValueObject	getKarinsFastTagTollCharges(ValueObject	valueObject) throws  Exception{
		List<TollExpensesDetails>		tollExpensesDetails		= null;
		ValueObject						objectForPl				= null;
		List<TollExpenseData> 			tollExpenseList			= null;
		try {
				Date	fromDate	= sqlFormat.parse(valueObject.getString("StartTransactionDate"));
				Date	toDate		= sqlFormat.parse(valueObject.getString("EndTransactionDate"));
				tollExpenseList		= tollExpenseDataRepository.getTollExpenseDataList(valueObject.getString("VehicleNumber"), fromDate, toDate);
				
				tollExpensesDetails	= getTollExpensesDetailsDtoForKarins(tollExpenseList, valueObject, tollExpensesDetails);
				
				if(tollExpensesDetails != null && !tollExpensesDetails.isEmpty())
					objectForPl	= saveTollTransactionDetail(tollExpensesDetails, valueObject);
			
			return objectForPl;
		} catch (Exception e) {
			logger.error("Toll Expense Page Page:", e);
			return null;
		}
	}
	
	private List<TollExpensesDetails> getTollExpensesDetailsDtoForYesBank(JSONArray	details, ValueObject valueObject
			,List<TollExpensesDetails> 	tollExpensesDetailsList) throws Exception{
		TollExpensesDetails			tollExpensesDetails			= null;
		CustomUserDetails			userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			if(tollExpensesDetailsList == null)
				tollExpensesDetailsList	= new ArrayList<>();
			
			for(int i = 0; i<details.length(); i++) {
				tollExpensesDetails	= new TollExpensesDetails();
				
				 tollExpensesDetails.setProcessingDateTime(DateTimeUtility.getTimeStamp(details.getJSONObject(i).getString("toll_reader_time"), "dd-MMM-yyyy hh:mm:ss"));
				 tollExpensesDetails.setTransactionDateTime(DateTimeUtility.getTimeStamp(details.getJSONObject(i).getString("transacted_at"), "dd-MMM-yyyy hh:mm:ss"));
				 tollExpensesDetails.setTransactionAmount(details.getJSONObject(i).getDouble("amount"));
				 tollExpensesDetails.setTransactionId(details.getJSONObject(i).getLong("id")+"");
				 tollExpensesDetails.setLaneCode(details.getJSONObject(i).getString("plaza_lane_id"));
				 tollExpensesDetails.setPlazaCode(details.getJSONObject(i).getString("plaza_code"));
				 tollExpensesDetails.setTransactionReferenceNumber(details.getJSONObject(i).getString("ext_txn_id"));
				 tollExpensesDetails.setPlazaName(details.getJSONObject(i).getString("plaza_name"));
				 tollExpensesDetails.setCompanyId(userDetails.getCompany_id());
				 tollExpensesDetails.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
				 tollExpensesDetails.setCreatedById(userDetails.getId());
				 tollExpensesDetails.setVid(valueObject.getInt("vid"));
				 tollExpensesDetails.setTripSheetId(valueObject.getLong("tripSheetId"));
				 tollExpensesDetails.setFastTagBankId(FastTagBankConstant.FASTTAG_BANK_YESBANK_ID);
				 
				 tollExpensesDetailsList.add(tollExpensesDetails);
			}
					
			return tollExpensesDetailsList;
		} catch (Exception e) {
			System.err.println("Exception "+e);
			throw e;
		}finally {
			tollExpensesDetails			= null;
			details						= null;
			userDetails					= null;
		}
	}
	
	private List<TollExpensesDetails> getTollExpensesDetailsDtoForKarins(List<TollExpenseData> tollList, ValueObject valueObject
			,List<TollExpensesDetails> 	tollExpensesDetailsList) throws Exception{
		TollExpensesDetails			tollExpensesDetails			= null;
		CustomUserDetails			userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			if(tollExpensesDetailsList == null)
				tollExpensesDetailsList	= new ArrayList<>();
			
			for (TollExpenseData tollExpenseData : tollList) {

				tollExpensesDetails	= new TollExpensesDetails();
				if(tollExpenseData.getReader_datetime() != null)
				 tollExpensesDetails.setProcessingDateTime(DateTimeUtility.getTimeStampFromDate(tollExpenseData.getReader_datetime()));
				 tollExpensesDetails.setTransactionDateTime(DateTimeUtility.getTimeStampFromDate(tollExpenseData.getTransaction_datetime()));
				 
				 tollExpensesDetails.setTransactionAmount(tollExpenseData.getTransaction_amount());
				 tollExpensesDetails.setTransactionId(tollExpenseData.getTransaction_id());
				 tollExpensesDetails.setLaneCode(tollExpenseData.getLane_code());
				 tollExpensesDetails.setPlazaCode(tollExpenseData.getPlaza_code());
				 tollExpensesDetails.setTransactionReferenceNumber(tollExpenseData.getTransaction_reference_number());
				 tollExpensesDetails.setPlazaName(tollExpenseData.getPlazaName());
				 tollExpensesDetails.setCompanyId(userDetails.getCompany_id());
				 tollExpensesDetails.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
				 tollExpensesDetails.setCreatedById(userDetails.getId());
				 tollExpensesDetails.setVid(valueObject.getInt("vid"));
				 tollExpensesDetails.setTripSheetId(valueObject.getLong("tripSheetId"));
				 tollExpensesDetails.setFastTagBankId(FastTagBankConstant.FASTTAG_BANK_YESBANK_ID);
				 
				 tollExpensesDetailsList.add(tollExpensesDetails);
			
			}
			
					
			return tollExpensesDetailsList;
		} catch (Exception e) {
			System.err.println("Exception "+e);
			throw e;
		}finally {
			tollExpensesDetails			= null;
			tollList					= null;
			userDetails					= null;
		}
	}

	
	 /*
     * Send a GET request to get a specific user.
     */
    @SuppressWarnings("unchecked")
	private  ValueObject getTollTransactionDetails(AuthTokenInfo tokenInfo, ValueObject valueObject) throws Exception{
    	HashMap<String, Object> 	configuration				= null;
    	String 						sharedKey					= null;
    	String 						sharedVector				= null;
    	CustomUserDetails			userDetails					= null;
    	List<TollExpensesDetails> 	tollExpensesDetailsList		= null;
    	ValueObject 				valueObjectPL				= null;
		try {
			
			configuration	= (HashMap<String, Object>) valueObject.get("configuration");
			userDetails		= (CustomUserDetails) valueObject.get("userDetails");
			
			sharedKey		= (String) configuration.get(TollApiConfiguration.KVB_BANK_SHARED_KEY);
			sharedVector	= (String) configuration.get(TollApiConfiguration.KVB_BANK_SHARED_VECTOR);
			
			String[] byteStrings = sharedVector.split(",");
			byte[] byteData = new byte[byteStrings.length];
			for (int i = 0; i < byteData.length; i++){
			    byteData[i] = Byte.parseByte(byteStrings[i]); 
			}
    		String jsonStr;
    		JSONObject json = new JSONObject();
    		json.put("FROM_DT", valueObject.getString(TollExpensesDetailsDto.START_TRANSACTION_DATE));
    		json.put("TO_DT", valueObject.getString(TollExpensesDetailsDto.END_TRANSACTION_DATE));
    		json.put("MOBILENUMBER", configuration.get(TollApiConfiguration.KVB_BANK_CLIENT_MOBILE_NUMBER));
    		json.put("VEHICLENUMBER", valueObject.getString(TollExpensesDetailsDto.VEHICLE_NUMBER));
    		json.put("PARAM1", "");
    		json.put("PARAM2", "");
    		json.put("PARAM3", "");
    		json.put("PARAM4", "");
    		json.put("PARAM5", "");
    		
    		jsonStr =  json.toString();
    		
    		String encryptedStr =	AESEncryptionDecryption.encryptText(jsonStr, sharedKey.getBytes(), byteData);
    		
            RestTemplate restTemplate = new RestTemplate();
          
            HttpEntity<String> entity = new HttpEntity<String>(encryptedStr,getHeadersWithClientCredentialsForToll(tokenInfo));
            
            ResponseEntity<String> loginResponse = restTemplate
            		  .exchange(TollExpensesDetailsDto.GET_KVB_BANK_TOLL_TRANSACTION_URL, HttpMethod.POST, entity, String.class);
            
            
            String responseStr	= AESEncryptionDecryption.decryptText(loginResponse.getBody(), sharedKey.getBytes(), byteData);
            
            JSONArray jsonArr = new JSONArray(responseStr);

            JSONObject	resultObject	=  (JSONObject) jsonArr.get(0);
            
            
            if(resultObject.getString("STATUSCODE").equalsIgnoreCase("000")) {
            	
            	tollExpensesDetailsList	= new ArrayList<>();
            	
            	  JSONObject	jsonObject	=  (JSONObject) jsonArr.get(1);
            	  
            	  TollExpensesDetails	tollExpensesDetails		= null;
            	  
            	  if(jsonObject != null) {
            		  for(int i = 0; i<jsonObject.getJSONArray("VEHICLETRANSACTIONDETAILS").length() ; i++) {
            			  tollExpensesDetails	= new TollExpensesDetails();
            			  tollExpensesDetails.setTransactionId(jsonObject.getJSONArray("VEHICLETRANSACTIONDETAILS").getJSONObject(i).getString("TRANSACTIONID"));
            			  
            			  String transactionDate = jsonObject.getJSONArray("VEHICLETRANSACTIONDETAILS").getJSONObject(i).getString("TRANSACTIONDATETIME");
            			  String tollReaderTime  = jsonObject.getJSONArray("VEHICLETRANSACTIONDETAILS").getJSONObject(i).getString("TOLLREADERDATETIME");
          					
          				 tollExpensesDetails.setProcessingDateTime(DateTimeUtility.geTimeStampFromDateWithTime(sdf2.parse(transactionDate)));
          				 tollExpensesDetails.setTransactionDateTime(DateTimeUtility.geTimeStampFromDateWithTime(sdf2.parse(tollReaderTime)));
          				 tollExpensesDetails.setTransactionAmount(jsonObject.getJSONArray("VEHICLETRANSACTIONDETAILS").getJSONObject(i).getDouble("AMOUNT"));
          				 tollExpensesDetails.setLaneCode(jsonObject.getJSONArray("VEHICLETRANSACTIONDETAILS").getJSONObject(i).getString("TAGID"));
          				 tollExpensesDetails.setPlazaCode(jsonObject.getJSONArray("VEHICLETRANSACTIONDETAILS").getJSONObject(i).getString("TOLLPLAZAID"));
          				 tollExpensesDetails.setTransactionReferenceNumber(jsonObject.getJSONArray("VEHICLETRANSACTIONDETAILS").getJSONObject(i).getString("TRANSACTIONID"));
          				 tollExpensesDetails.setPlazaName(jsonObject.getJSONArray("VEHICLETRANSACTIONDETAILS").getJSONObject(i).getString("TOLLPLAZANAME"));
          				 tollExpensesDetails.setCompanyId(userDetails.getCompany_id());
          				 tollExpensesDetails.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
          				 tollExpensesDetails.setCreatedById(userDetails.getId());
          				 tollExpensesDetails.setVid(valueObject.getInt("vid"));
          				 tollExpensesDetails.setTripSheetId(valueObject.getLong("tripSheetId"));
          				tollExpensesDetails.setFastTagBankId(FastTagBankConstant.FASTTAG_BANK_KVB_ID);
          				 
          				 tollExpensesDetailsList.add(tollExpensesDetails);
          			
            		  }
            		  
            		  valueObjectPL	=   saveTollTransactionDetail(tollExpensesDetailsList, valueObject);
            	  }
            	
            	
          
            }else {
            	logger.info("No Record Found !");
            }
          
            return valueObjectPL;
		} catch (Exception e) {
			logger.error("Toll Expense Page Page:", e);
			return null;
		}finally {
			configuration				= null;
	    	sharedKey					= null;
	    	sharedVector				= null;
	    	userDetails					= null;
	    	tollExpensesDetailsList		= null;
	    	valueObjectPL				= null;
		}
    }
    
    @SuppressWarnings("unchecked")
	private ValueObject getHdfcBankTollCharges(ValueObject	valueObject) throws Exception {

		HashMap<String, Object> 		configuration		= null;
		JSONObject						inputObj			= null;
		HttpResponse<String> 			response 			= null; 
		String							customerId			= null;
		String							walletId			= null;
		List<TollExpensesDetails>		tollExpensesDetails	= null;
		ValueObject						objectForPl			= null;
		VehicleTollDetails				vehicleTollDetails	= null;
		List<Date> 						dateList			= null;
		try {
			configuration	= (HashMap<String, Object>) valueObject.get("configuration");
			vehicleTollDetails	= vehicleTollService.getVehicleTollByVid(valueObject.getInt("vid"));
			
			if(vehicleTollDetails != null) {
					customerId	= vehicleTollDetails.getCustomerId();
					walletId	= vehicleTollDetails.getWalletId();
			}
			dateList = new ArrayList<Date>();
			dateList 	= DateTimeUtility.getDaysBetweenDates(sqlFormat.parse(valueObject.getString(TollExpensesDetailsDto.START_TRANSACTION_DATE)), 
							sqlFormat.parse(valueObject.getString(TollExpensesDetailsDto.END_TRANSACTION_DATE)), dateList);
			if(dateList != null && !dateList.isEmpty()) {
				for (Date date : dateList) {
					if(customerId != null) {
						try {
							inputObj		= new JSONObject();  
							inputObj.put(TollExpensesDetailsDto.WALLET_ID, walletId);
							inputObj.put(TollExpensesDetailsDto.FROM_DATE, tallyFormat.format(date));
							inputObj.put(TollExpensesDetailsDto.TO_DATE,tallyFormat.format(date));
							inputObj.put("vehicleNumber", valueObject.getString(TollExpensesDetailsDto.VEHICLE_NUMBER));
							inputObj.put("requestTime", tallyFormat.format(date));
							inputObj.put("merchantID", customerId);
							inputObj.put("requestID", "001");
							inputObj.put("contactNumber", "");
							inputObj.put("requestSource", "BD");
							
							String checkSum = Utility.HmacSHA256(inputObj.toString(), (String)configuration.get("userName")+""+(String)configuration.get("password"));
							System.err.println("customerId "+customerId+" (String)configuration.get(\"userName\")  "+(String)configuration.get("userName") +" (String)configuration.get(\"password\") "+(String)configuration.get("password"));
							
							System.err.println("(String)configuration.get(\"saltValue\") "+(String)configuration.get("saltValue") +" inputObj "+inputObj);
							try {
								response = Unirest.post((String)configuration.get("hdfcBankFastTagUrl"))
										.header("Authorization", ""+customerId+":"+checkSum)
										.header("Content-Type", "application/json")
										.header("salt", (String)configuration.get("saltValue"))
										.body(inputObj)
										.asString();
							} catch (Exception e) {
								e.printStackTrace();
							}
							

							System.err.println(response+"**** ###"+response.getStatus()+" "+response.getStatusText());
							if(response.getStatus() == 200) {
								//JSONArray	jsonArray	=	 response.getBody().getArray();
								
								//System.err.println("jsonArray hdfc" + jsonArray);
								
								//tollExpensesDetails		=	 getHDFCTollExpensesDetailsDto(jsonArray, valueObject);
								
								//objectForPl	= saveTollTransactionDetail(tollExpensesDetails, valueObject);
								
								
							}else if(response.getStatus() == 404){
								logger.info("404 status found");
							}else {
								valueObject.put("noRecordFound", true);
							}
						} catch (Exception e) {
						}
						
					}
				
				}
			}
			
			  
			  return objectForPl;
		}catch (UnirestException e) {
			logger.error("Unirest exception while getting HDFC toll ");
			return null;
		}catch (SocketTimeoutException e) {
			logger.error("SocketTimeoutException while getting HDFC toll ");
			return null;
		}catch (Exception e) {
			logger.error("Toll Expense Page Page:", e);
			return null;
		}finally {
			configuration		= null;
			inputObj			= null;
			response 			= null; 
			customerId			= null;
			tollExpensesDetails	= null;
			objectForPl			= null;
			vehicleTollDetails	= null;
		}
	
	}
	
	@SuppressWarnings("unchecked")
	private ValueObject getICICIBankTollCharges(ValueObject	valueObject) throws Exception {

		HashMap<String, Object> 		configuration		= null;
		JSONObject						inputObj			= null;
		HttpResponse<JsonNode> 			response 			= null; 
		String							customerId			= null;
		List<TollExpensesDetails>		tollExpensesDetails	= null;
		ValueObject						objectForPl			= null;
		VehicleTollDetails				vehicleTollDetails	= null;
		try {
			configuration	= (HashMap<String, Object>) valueObject.get("configuration");
			if(!(boolean) configuration.get("multipleAccountForSameVendor")) {
				customerId		= (int) configuration.get(TollExpensesDetailsDto.CUSTOMER_ID)+"";
			}else {
				vehicleTollDetails	= vehicleTollService.getVehicleTollByVid(valueObject.getInt("vid"));
				if(vehicleTollDetails != null) {
						customerId	= vehicleTollDetails.getCustomerId();
				}
			}
			
			
			if(customerId != null) {
				inputObj		= new JSONObject();  
				inputObj.put(TollExpensesDetailsDto.CUSTOMER_ID, Integer.parseInt(customerId));    
				inputObj.put(TollExpensesDetailsDto.START_TRANSACTION_DATE,valueObject.getString(TollExpensesDetailsDto.START_TRANSACTION_DATE).replace(".0", ""));    
				Timestamp	endDate = DateTimeUtility.getEndOfDayTimeStamp(valueObject.getString(TollExpensesDetailsDto.END_TRANSACTION_DATE), DateTimeUtility.YYYY_MM_DD);
				if(endDate.before(DateTimeUtility.getCurrentTimeStamp())) {
					inputObj.put(TollExpensesDetailsDto.END_TRANSACTION_DATE,valueObject.getString(TollExpensesDetailsDto.END_TRANSACTION_DATE));
				}else {
					TimeZone tz = TimeZone.getTimeZone("UTC");
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
					df.setTimeZone(tz);
					String nowAsISO = df.format(new Date());
					inputObj.put(TollExpensesDetailsDto.END_TRANSACTION_DATE,nowAsISO);
				}
				inputObj.put(TollExpensesDetailsDto.VEHICLE_NUMBER, valueObject.getString(TollExpensesDetailsDto.VEHICLE_NUMBER)); 
				
				
				response = Unirest.post(TollExpensesDetailsDto.GET_TRANSACTION_URL)
						.header(TollApiConfiguration.API_KEY, (String) configuration.get(TollApiConfiguration.API_KEY))
						.header("Content-Type", "application/json")
						.header(TollApiConfiguration.API_CLIENT_ID, (Integer) configuration.get(TollApiConfiguration.API_CLIENT_ID)+"")
						.body(inputObj)
						.asJson();
				
				System.err.println("Hereee ");
				if(response.getStatus() == 200) {
					JSONArray	jsonArray	=	 response.getBody().getArray();
					System.err.println("jsonArray "+ jsonArray);
					tollExpensesDetails		=	 getTollExpensesDetailsDto(jsonArray, valueObject);
					
					objectForPl	= saveTollTransactionDetail(tollExpensesDetails, valueObject);
					
					
				}else if(response.getStatus() == 404){
					logger.info("404 status found");
				}else {
					valueObject.put("noRecordFound", true);
				}
			}
			  
			  return objectForPl;
		}catch (NumberFormatException e) {
			System.err.println("inside number format exception tol Expense page");
			return null;
		}catch (UnirestException e) {
			logger.error("Unirest exception while getting icici toll ");
			return null;
		}catch (SocketTimeoutException e) {
			logger.error("SocketTimeoutException while getting icici toll ");
			return null;
		}catch (Exception e) {
			logger.error("Toll Expense Page Page:", e);
			return null;
		}finally {
			configuration		= null;
			inputObj			= null;
			response 			= null; 
			customerId			= null;
			tollExpensesDetails	= null;
			objectForPl			= null;
			vehicleTollDetails	= null;
		}
	
	}
	
	@SuppressWarnings("unchecked")
	private ValueObject saveTollTransactionDetail(List<TollExpensesDetails>		tollExpensesDetails, ValueObject	valueObject) throws Exception{
		HashMap<String, Object> 		configuration		= null;
		CustomUserDetails				userDetails			= null;
		UserProfileDto 					userProfile			= null;
		List<TollExpensesDetails>		validate			= null;
		TripSheet						sheet				= null;
		VehicleProfitAndLossTxnChecker		profitAndLossExpenseTxnChecker	= null;
		HashMap<Long, VehicleProfitAndLossTxnChecker>		expenseTxnCheckerHashMap		= null;
		HashMap<Long, TripSheetExpense> 					tripSheetExpenseHM				= null;
		ValueObject	objectForPl	= null;
		try {
			
			userDetails		= (CustomUserDetails) valueObject.get("userDetails");
			configuration	= (HashMap<String, Object>) valueObject.get("configuration");
			sheet			= (TripSheet) valueObject.get("sheet");
			userProfile 	= (UserProfileDto) valueObject.get("userProfile");
			
			if(tollExpensesDetails != null && !tollExpensesDetails.isEmpty()) {
				expenseTxnCheckerHashMap	= new HashMap<>();
				tripSheetExpenseHM			= new HashMap<>();
			int 	flavor	= companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG); 
			TripSheetExpense tripExpense = new TripSheetExpense();
			TripDailyExpense tripDailyExpense = new TripDailyExpense();
			
			for(TollExpensesDetails  details : tollExpensesDetails) {
					
					validate				=	 validateTollExpensesDetails(details.getTransactionId(), userDetails.getCompany_id());
					if(validate == null || validate.isEmpty()) {
						
						saveTollExpenses(details);
						
						valueObject.put("flavor", flavor);
						
						if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
							TripSheet tsheet = new TripSheet();
							tsheet.setTripSheetID(valueObject.getLong("tripSheetId"));
							
							tripExpense.setExpenseId((Integer) configuration.get("TollExpenseId"));
							if(tripExpense.getExpenseAmount() != null) {
								tripExpense.setExpenseAmount(tripExpense.getExpenseAmount() + details.getTransactionAmount());
							}else {
								tripExpense.setExpenseAmount(details.getTransactionAmount());
							}
							tripExpense.setExpensePlaceId(userProfile.getBranch_id());
							tripExpense.setExpenseRefence("FastTag Toll charges");
							tripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
							tripExpense.setCreatedById(userDetails.getId());
							tripExpense.setTripsheet(tsheet);
							tripExpense.setCompanyId(userDetails.getCompany_id());
							
							tripExpense.setCreated(sheet.getClosetripDate());
							
						}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
							
							TripDailySheet tsheet = new TripDailySheet();
							tsheet.setTRIPDAILYID(valueObject.getLong("tripSheetId"));

							
							tripDailyExpense.setExpenseId((Integer) configuration.get("TollExpenseId"));
							tripDailyExpense.setTripDailysheet(tsheet);

							tripDailyExpense.setCreatedById(userDetails.getId());
							if(tripDailyExpense.getExpenseAmount() == null) {
								tripDailyExpense.setExpenseAmount(details.getTransactionAmount());
							}else {
								tripDailyExpense.setExpenseAmount(tripDailyExpense.getExpenseAmount() + details.getTransactionAmount());
							}
							tripDailyExpense.setExpenseRefence("FastTag Toll charges");

							tripDailyExpense.setCreated(DateTimeUtility.getCurrentDate());
							tripDailyExpense.setCompanyId(userDetails.getCompany_id());
							tripDailyExpense.setFixedTypeId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
							
							tripDailySheetService.add_TripDailyExpense(tripDailyExpense);
							
							tripDailySheetService.update_TripDailySheet_TotalExpense(valueObject.getLong("tripSheetId"));
						}
						
						
					
						
						valueObject.put("tollExpensesAdded", true);
					}else {
						valueObject.put("tollExpensesAlreadyAdded", true);
					}
				}
			
			if(tripExpense != null &&tripExpense.getExpenseAmount() != null && tripExpense.getExpenseAmount() > 0.0 ) {

				ValueObject		object	= new ValueObject();

				object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION_ID, valueObject.getLong("tripSheetId"));
				object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
				object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION_VID, sheet.getVid());
				object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, tripExpense.getExpenseId());
				object.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, Long.parseLong(tripExpense.getExpenseId()+""));

				
				profitAndLossExpenseTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);

				vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossExpenseTxnChecker);

				expenseTxnCheckerHashMap.put(profitAndLossExpenseTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossExpenseTxnChecker);
				tripSheetExpenseHM.put(Long.parseLong(tripExpense.getExpenseId()+""), tripExpense);

			}
			
			if(profitAndLossExpenseTxnChecker != null) {
				objectForPl	= new ValueObject();
				objectForPl.put("tripSheet", sheet);
				objectForPl.put("userDetails", userDetails);
				objectForPl.put("tripSheetExpenseHM", tripSheetExpenseHM);
				objectForPl.put("expenseTxnCheckerHashMap", expenseTxnCheckerHashMap);
				objectForPl.put("isTripSheetClosed", true);
				if(valueObject.getBoolean("calledAfterClose",false)) {
					vehicleProfitAndLossService.runThreadForTripSheetExpenses(objectForPl);
				}
			}
				
			}
			return objectForPl;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public void saveTollExpenses(TollExpensesDetails tollExpensesDetails) throws Exception {

		tollExpensesDetailsRepository.save(tollExpensesDetails);
	}

	private List<TollExpensesDetails> getTollExpensesDetailsDto(JSONArray	jsonArray, ValueObject valueObject) throws Exception{
		TollExpensesDetails			tollExpensesDetails			= null;
		JSONArray					details						= null;
		CustomUserDetails			userDetails					= null;
		List<TollExpensesDetails> 	tollExpensesDetailsList		= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			
			tollExpensesDetailsList	= new ArrayList<>();
			
			details	=  (JSONArray) jsonArray.getJSONObject(0).get("TransactionDetails"); 
			
			for(int i = 0; i<details.length(); i++) {
				tollExpensesDetails	= new TollExpensesDetails();
				 Calendar	calendar	=	javax.xml.bind.DatatypeConverter.parseDateTime(details.getJSONObject(i).getString("ProcessingDateTime"));
					
				 tollExpensesDetails.setProcessingDateTime(new Timestamp(calendar.getTime().getTime()));
				 Calendar	calendar2	=	javax.xml.bind.DatatypeConverter.parseDateTime(details.getJSONObject(i).getString("TransactionDateTime"));
				 tollExpensesDetails.setTransactionDateTime(new Timestamp(calendar2.getTime().getTime()));
				 tollExpensesDetails.setTransactionAmount(details.getJSONObject(i).getDouble("TransactionAmount"));
				 tollExpensesDetails.setTransactionId(details.getJSONObject(i).getLong("TransactionId")+"");
				 tollExpensesDetails.setLaneCode(details.getJSONObject(i).getString("LaneCode"));
				 tollExpensesDetails.setPlazaCode(details.getJSONObject(i).getString("PlazaCode"));
				 tollExpensesDetails.setTransactionStatus(details.getJSONObject(i).getString("TransactionStatus"));
				 tollExpensesDetails.setTransactionReferenceNumber(details.getJSONObject(i).getString("TransactionReferenceNumber"));
				 tollExpensesDetails.setPlazaName(details.getJSONObject(i).getString("PlazaName"));
				 tollExpensesDetails.setCompanyId(userDetails.getCompany_id());
				 tollExpensesDetails.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
				 tollExpensesDetails.setCreatedById(userDetails.getId());
				 tollExpensesDetails.setVid(valueObject.getInt("vid"));
				 tollExpensesDetails.setTripSheetId(valueObject.getLong("tripSheetId"));
				 tollExpensesDetails.setFastTagBankId(FastTagBankConstant.FASTTAG_BANK_ICICI_ID);
				 
				 tollExpensesDetailsList.add(tollExpensesDetails);
			}
					
			return tollExpensesDetailsList;
		} catch (Exception e) {
			System.err.println("Exception "+e);
			throw e;
		}finally {
			tollExpensesDetails			= null;
			details						= null;
			userDetails					= null;
		}
	}
	
	private List<TollExpensesDetails> getHDFCTollExpensesDetailsDto(JSONArray	jsonArray, ValueObject valueObject) throws Exception{
		TollExpensesDetails			tollExpensesDetails			= null;
		JSONArray					details						= null;
		CustomUserDetails			userDetails					= null;
		List<TollExpensesDetails> 	tollExpensesDetailsList		= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			
			tollExpensesDetailsList	= new ArrayList<>();
			
			Timestamp startDateTime	= DateTimeUtility.getTimeStamp(valueObject.getString(TollExpensesDetailsDto.START_TRANSACTION_DATE), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			Timestamp endDateTime	= DateTimeUtility.getTimeStamp(valueObject.getString(TollExpensesDetailsDto.END_TRANSACTION_DATE), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			details	=  (JSONArray) jsonArray.getJSONObject(0).get("data"); 
			
			for(int i = 0; i<details.length(); i++) {
				tollExpensesDetails	= new TollExpensesDetails();
				 tollExpensesDetails.setTransactionDateTime(DateTimeUtility.getTimeStamp(details.getJSONObject(i).optString("reqTime"), DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
				 tollExpensesDetails.setTransactionAmount(details.getJSONObject(i).getDouble("txnAmt"));
				 tollExpensesDetails.setTransactionId(details.getJSONObject(i).optString("partnerRefId"));
				 tollExpensesDetails.setLaneCode(details.getJSONObject(i).optString("laneId"));
				 tollExpensesDetails.setPlazaCode(details.getJSONObject(i).optString("tollplazaid"));
				 tollExpensesDetails.setTransactionReferenceNumber(details.getJSONObject(i).getString("partnerRefId"));
				 tollExpensesDetails.setPlazaName(details.getJSONObject(i).optString("tollplazaname"));
				 tollExpensesDetails.setCompanyId(userDetails.getCompany_id());
				 tollExpensesDetails.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
				 tollExpensesDetails.setCreatedById(userDetails.getId());
				 tollExpensesDetails.setVid(valueObject.getInt("vid"));
				 tollExpensesDetails.setTripSheetId(valueObject.getLong("tripSheetId"));
				 tollExpensesDetails.setFastTagBankId(FastTagBankConstant.FASTTAG_BANK_HDFC_ID);
				 tollExpensesDetails.setProcessingDateTime(DateTimeUtility.getTimeStamp(details.getJSONObject(i).getString("reqTime"), DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
				
				 if(startDateTime.before(tollExpensesDetails.getTransactionDateTime()) && endDateTime.after(tollExpensesDetails.getTransactionDateTime()))
					 tollExpensesDetailsList.add(tollExpensesDetails);
			}
					
			return tollExpensesDetailsList;
		} catch (Exception e) {
			System.err.println("Exception "+e);
			throw e;
		}finally {
			tollExpensesDetails			= null;
			details						= null;
			userDetails					= null;
		}
	}
	
	@Override
	public  List<TollExpensesDetails> validateTollExpensesDetails(String transactionId, Integer companyId) throws Exception {
		try {
			return tollExpensesDetailsRepository.validateTollExpensesDetails(transactionId, companyId);
		} catch (Exception e) {
			System.err.println("Exception "+e);
			throw e;
		}
	}
	
	@Override
	public ValueObject getTollExpensesDetailsList(Long tripSheetId, Integer companyId) throws Exception {
		ValueObject		valueObject	= new ValueObject();
		TypedQuery<Object[]> query = null;
		 query = entityManager.createQuery(
				"SELECT T.tollExpensesDetailsId, T.plazaName, T.transactionId, T.transactionAmount, T.laneCode, T.plazaCode,"
						+ " T.transactionStatus, T.transactionReferenceNumber, T.createdOn, T.createdById, T.processingDateTime, T.transactionDateTime, T.fastTagBankId "
						+ " FROM TollExpensesDetails T "
						+ " WHERE T.tripSheetId =:id AND T.companyId =:companyId AND T.markForDelete = 0 ORDER BY T.transactionDateTime ASC",
						Object[].class);

		query.setParameter("id", tripSheetId);
		query.setParameter("companyId", companyId);
		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TollExpensesDetailsDto> Dtos = null;
		double	totalAmount	= 0;
		Timestamp date  = null;
		
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TollExpensesDetailsDto>();
			TollExpensesDetailsDto select;
			for (Object[] vehicle : results) {
				if (vehicle != null) {
					select = new TollExpensesDetailsDto();
					
					select.setTollExpensesDetailsId((Long) vehicle[0]);
					select.setPlazaName((String) vehicle[1]);
					select.setTransactionId((String) vehicle[2]);
					select.setTransactionAmount((Double) vehicle[3]);
					select.setLaneCode((String) vehicle[4]);
					select.setPlazaCode((String) vehicle[5]);
					select.setTransactionStatus((String) vehicle[6]);
					select.setTransactionReferenceNumber((String) vehicle[7]);
					select.setCreatedOn((Timestamp) vehicle[8]);
					date = (Timestamp) vehicle[8];
					select.setCreatedById((Long) vehicle[9]);
					select.setProcessingDateTime((Timestamp) vehicle[10]);
					select.setTransactionDateTime((Timestamp) vehicle[11]);
					select.setFastTagBankId((short) vehicle[12]);
					select.setFastTagBankName(FastTagBankConstant.getFastTagBankName((short) vehicle[12]));
					
					if(select.getTransactionAmount() != null)
						totalAmount += select.getTransactionAmount();
					
					if(select.getProcessingDateTime() != null) {
						select.setProcessingDateTimeStr(DateTimeUtility.getDateFromTimeStamp(select.getProcessingDateTime(), DateTimeUtility.DD_MM_YY_HH_MM_SS));
					} else {
							select.setProcessingDateTimeStr("--");
					}
					
					if(select.getTransactionDateTime() != null) {
						select.setTransactionDateTimeStr(DateTimeUtility.getDateFromTimeStamp(select.getTransactionDateTime(), DateTimeUtility.DD_MM_YY_HH_MM_SS));
					} else {
							select.setTransactionDateTimeStr("--");
					}
					
					Dtos.add(select);
				}
				valueObject.put("Date", DateTimeUtility.getDateFromTimeStamp(date));
				valueObject.put("ExpenseName", Dtos);
				valueObject.put("totalAmount", totalAmount);
			}
		}

		return valueObject;
	}
	
	 @SuppressWarnings({ "unchecked"})
		private static AuthTokenInfo sendTokenRequest(HashMap<String, Object> 	configuration){
		 	disableSSL();
	        RestTemplate restTemplate = new RestTemplate(); 
	        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials(configuration));
	        ResponseEntity<Object> response = restTemplate.exchange(TollExpensesDetailsDto.GET_KVB_BANK_TOKEN_URL, HttpMethod.POST, request, Object.class);
	        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();
	        AuthTokenInfo tokenInfo = null;
	        
	        if(map!=null){
	        	tokenInfo = new AuthTokenInfo();
	        	tokenInfo.setAccess_token((String)map.get("access_token"));
	        	tokenInfo.setToken_type((String)map.get("token_type"));
	        	tokenInfo.setRefresh_token((String)map.get("refresh_token"));
	        	tokenInfo.setExpires_in(Integer.parseInt(map.get("expires_in")+""));
	        	tokenInfo.setScope((String)map.get("scope"));
	        }
	        return tokenInfo;
	    }
	 
	/* private static ClientHttpRequestFactory getClientHttpRequestFactory() {
		    int timeout = 8000;
		    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
		      = new HttpComponentsClientHttpRequestFactory();
		    clientHttpRequestFactory.setConnectTimeout(timeout);
		    return clientHttpRequestFactory;
		}*/
	    
	 /*
	     * Prepare HTTP Headers.
	     */
	    private static HttpHeaders getHeaders(){
	    	HttpHeaders headers = new HttpHeaders();
	    	headers.add("Cookie","AspxAutoDetectCookie=1");
	    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    	return headers;
	    }
	    
	    /*
	     * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
	     */
	    private static HttpHeaders getHeadersWithClientCredentials(HashMap<String, Object> 	configuration){
	    	
	    	String base64ClientCredentials = (String) configuration.get(TollApiConfiguration.KVB_BANK_TOKEN_KEY);
	    	
	    	HttpHeaders headers = getHeaders();
	    	headers.add("Authorization", "Basic " + base64ClientCredentials);
	    	return headers;
	    }  
	    
	    private static HttpHeaders getHeadersWithClientCredentialsForToll(AuthTokenInfo	authTokenInfo){
	    	
	    	String base64ClientCredentials = authTokenInfo.getAccess_token();
	    	
	    	HttpHeaders headers = getHeaders();
	    	headers.add("Authorization", "Bearer " + base64ClientCredentials);
	    	return headers;
	    }  
	    
	    private static void disableSSL() {
	        try {
	           TrustManager[] trustAllCerts = new TrustManager[] { new   MyTrustManager() };

	      // Install the all-trusting trust manager
	      SSLContext sc = SSLContext.getInstance("SSL");
	      sc.init(null, trustAllCerts, new java.security.SecureRandom());
	      HostnameVerifier allHostsValid = new HostnameVerifier() {
	          public boolean verify(String hostname, SSLSession session) {
	              return true;
	          }
	      };
	      HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	  } catch (Exception e) {
	      e.printStackTrace();
	  }
}
	    
		@Override
		public List<TollExpensesDetailsDto> getTripsheetTollAmount(int vid, String fromDate, String toDate,
				CustomUserDetails userDetails,int flavor)throws Exception {
			try {
				TypedQuery<Object[]> query = null;
				
				if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE)	{
					query = entityManager.createQuery(
							" SELECT TED.tollExpensesDetailsId, TED.transactionAmount,TED.tripSheetId "
								+ " FROM TollExpensesDetails AS TED "
								+ " LEFT JOIN TripSheet T ON T.tripSheetID = TED.tripSheetId "
								+ " where  TED.companyId = "+ userDetails.getCompany_id() + " AND TED.vid = "+vid+" AND T.closetripDate between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.tripStutesId IN (3, 4) AND TED.markForDelete = 0 "
								+ " ORDER BY TED.tollExpensesDetailsId ASC",
							Object[].class);
				}else {
					query = entityManager.createQuery(
							" SELECT TED.tollExpensesDetailsId, TED.transactionAmount,TED.tripSheetId "
								+ " FROM TollExpensesDetails AS TED "
								+ " LEFT JOIN TripDailySheet T ON T.TRIPDAILYID = TED.tripSheetId "
								+ " where  TED.companyId = "+ userDetails.getCompany_id() + " AND TED.vid = "+vid+" AND T.TRIP_OPEN_DATE between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND TED.markForDelete = 0 "
								+ " ORDER BY TED.tollExpensesDetailsId ASC",
							Object[].class);
				}
				
				
				List<Object[]> results = query.getResultList();

				List<TollExpensesDetailsDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<TollExpensesDetailsDto>();
					TollExpensesDetailsDto select = null;
					for (Object[] result : results) {
						select = new TollExpensesDetailsDto();
						select.setTollExpensesDetailsId((Long) result[0]);
						select.setTransactionAmount((double) result[1]);
						select.setTripSheetId((Long)result[2]);
						Dtos.add(select);
					}
				}
				return Dtos;
				
			} catch (Exception e) {
				throw e;
			}
		}    
		
		@Override
		public List<TollExpensesDetailsDto> getTripsheetTollAmountByGid(Long vid, String fromDate, String toDate,
				CustomUserDetails userDetails,int flavor) throws Exception {
			try {
				TypedQuery<Object[]> query = null;
				
				if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE)	{
					query = entityManager.createQuery(
							" SELECT TED.tollExpensesDetailsId, TED.transactionAmount,TED.tripSheetId "
								+ " FROM TollExpensesDetails AS TED "
								+ " INNER JOIN Vehicle V ON V.vid = TED.vid "
								+ " LEFT JOIN TripSheet T ON T.tripSheetID = TED.tripSheetId "
								+ " where  TED.companyId = "+ userDetails.getCompany_id() + " AND V.vehicleGroupId = "+vid+" AND T.closetripDate between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.tripStutesId IN (3, 4) AND TED.markForDelete = 0 "
								+ " ORDER BY TED.tollExpensesDetailsId ASC",
							Object[].class);
				}else {
					query = entityManager.createQuery(
							" SELECT TED.tollExpensesDetailsId, TED.transactionAmount,TED.tripSheetId "
								+ " FROM TollExpensesDetails AS TED "
								+ " INNER JOIN Vehicle V ON V.vid = TED.vid "
								+ " LEFT JOIN TripDailySheet T ON T.TRIPDAILYID = TED.tripSheetId "
								+ " where  TED.companyId = "+ userDetails.getCompany_id() + " AND V.vehicleGroupId = "+vid+" AND T.TRIP_OPEN_DATE between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND TED.markForDelete = 0 "
								+ " ORDER BY TED.tollExpensesDetailsId ASC",
							Object[].class);
				}
				
				
				List<Object[]> results = query.getResultList();

				List<TollExpensesDetailsDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<TollExpensesDetailsDto>();
					TollExpensesDetailsDto select = null;
					for (Object[] result : results) {
						select = new TollExpensesDetailsDto();
						select.setTollExpensesDetailsId((Long) result[0]);
						select.setTransactionAmount((double) result[1]);
						select.setTripSheetId((Long)result[2]);
						Dtos.add(select);
					}
				}
				return Dtos;
				
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<TollExpensesDetailsDto> getRouteWiseTripsheetTollAmount(String fromDate, String toDate,
				CustomUserDetails userDetails, int tripFalvor)throws Exception {
			try {
				TypedQuery<Object[]> query = null;
				
				if(tripFalvor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE)	{
				query = entityManager.createQuery(
						" SELECT TED.tollExpensesDetailsId, TED.transactionAmount,TED.tripSheetId "
								+ " FROM TollExpensesDetails AS TED "
								+ " LEFT JOIN TripSheet T ON T.tripSheetID = TED.tripSheetId "
								+ " where  TED.companyId = "+ userDetails.getCompany_id() + " AND T.closetripDate between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.tripStutesId IN (3, 4) AND TED.markForDelete = 0 "
								+ " ORDER BY TED.tollExpensesDetailsId ASC",
								Object[].class);
				}else {
					query = entityManager.createQuery(
							" SELECT TED.tollExpensesDetailsId, TED.transactionAmount,TED.tripSheetId "
									+ " FROM TollExpensesDetails AS TED "
									+ " LEFT JOIN TripDailySheet T ON T.TRIPDAILYID = TED.tripSheetId "
									+ " where  TED.companyId = "+ userDetails.getCompany_id() + " AND T.TRIP_OPEN_DATE between "
									+ " '"+fromDate+"' AND '"+toDate+"' AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND T.markForDelete = 0 "
									+ " ORDER BY TED.tollExpensesDetailsId ASC",
									Object[].class);
				}
				
				
				List<Object[]> results = query.getResultList();
				
				List<TollExpensesDetailsDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<TollExpensesDetailsDto>();
					TollExpensesDetailsDto select = null;
					for (Object[] result : results) {
						select = new TollExpensesDetailsDto();
						select.setTollExpensesDetailsId((Long) result[0]);
						select.setTransactionAmount((double) result[1]);
						select.setTripSheetId((Long)result[2]);
						Dtos.add(select);
					}
				}
				return Dtos;
				
			} catch (Exception e) {
				throw e;
			}
		}    
	    
		
		@Override
		public TollExpensesDetailsDto getTripTotalTollExpenseAmount (String startDate, String endDate,Integer companyId) throws Exception {
			Query queryt = 	null;
			
				queryt = entityManager.createQuery(
						" SELECT COUNT(tollExpensesDetailsId), SUM(transactionAmount) "
								+ " From TollExpensesDetails "					
								+ " WHERE createdOn between '"+ startDate +"' AND '"+ endDate + "' AND companyId = " +companyId
								+ " AND markForDelete = 0 ");
			
			Object[] vehicle = null;
			try {
				vehicle = (Object[]) queryt.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			TollExpensesDetailsDto select;
			if (vehicle != null) {
				select = new TollExpensesDetailsDto();
				
				if(vehicle[0] != null)
					select.setTollExpensesDetailsId((Long) vehicle[0]);
				
				if(vehicle[1] != null)
					select.setTransactionAmount((double) vehicle[1]);
				
			} else {
				return null;
			}

			return select;
		}
		
		@Override
		public List<TollExpensesDetailsDto> getRouteWiseTripsheetTollExpenseAmount(String routeId, String fromDate, String toDate, CustomUserDetails userDetails, int tripFalvor)throws Exception {
			TypedQuery<Object[]> 			query 						= null;
			List<TollExpensesDetailsDto> 	tollExpensesDetailsDtoList 	= null;
			TollExpensesDetailsDto 			tollExpensesDetailsDto 		= null;
			try {
				
				if(tripFalvor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE)	{
				query = entityManager.createQuery(
						" SELECT TED.tollExpensesDetailsId, TED.transactionAmount,TED.tripSheetId,TR.routeID "
								+ " FROM TollExpensesDetails AS TED "
								+ " LEFT JOIN TripSheet TR ON TR.tripSheetID = TED.tripSheetId "
								+ " where  TED.companyId = "+ userDetails.getCompany_id() + " "+routeId+" AND TR.closetripDate between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND TR.tripStutesId IN ("+TripSheetStatus.TRIP_STATUS_CLOSED+", "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+") AND TED.markForDelete = 0 "
								+ " ORDER BY TED.tollExpensesDetailsId ASC",
								Object[].class);
				}else {
					query = entityManager.createQuery(
							" SELECT TED.tollExpensesDetailsId, TED.transactionAmount,TED.tripSheetId, TR.TRIP_ROUTE_ID "
									+ " FROM TollExpensesDetails AS TED "
									+ " LEFT JOIN TripDailySheet TR ON TR.TRIPDAILYID = TED.tripSheetId "
									+ " where TED.companyId = "+ userDetails.getCompany_id() + ""+routeId+" AND TR.TRIP_OPEN_DATE between "
									+ " '"+fromDate+"' AND '"+toDate+"' AND TR.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND TR.markForDelete = 0 "
									+ " ORDER BY TED.tollExpensesDetailsId ASC",
									Object[].class);
				}
				
				
				List<Object[]> results = query.getResultList();
				
				
				if (results != null && !results.isEmpty()) {
					tollExpensesDetailsDtoList = new ArrayList<TollExpensesDetailsDto>();
					
					for (Object[] result : results) {
						tollExpensesDetailsDto = new TollExpensesDetailsDto();
						tollExpensesDetailsDto.setTollExpensesDetailsId((Long) result[0]);
						if((double) result[1] >0) {
							tollExpensesDetailsDto.setTransactionAmount((double) result[1]);
						}else {
							tollExpensesDetailsDto.setTransactionAmount(0.0);
						}
						tollExpensesDetailsDto.setTripSheetId((Long)result[2]);
						tollExpensesDetailsDto.setRouteID((Integer)result[3]);
						tollExpensesDetailsDtoList.add(tollExpensesDetailsDto);
					}
				}
				return tollExpensesDetailsDtoList;
				
			} catch (Exception e) {
				logger.error("ERR"+e);
				throw e;
			}finally {
				query 						= null;   
				tollExpensesDetailsDtoList 	= null;   
				tollExpensesDetailsDto 		= null;   
			}
		}   
		
		@Override
		public Double getTripSheetTollAmount(Long tripSheetId) throws Exception {
			try {
				
				return tollExpensesDetailsRepository.getTripSheetTollAmount(tripSheetId);
				
			} catch (Exception e) {
				throw e;
			}
			
		}
		
		@Override
		public List<DateWiseVehicleExpenseDto> getDateWiseFastTagTollExpenseDtoByVid(Integer vid, String fromDate,
				String toDate) throws Exception {
			TypedQuery<Object[]>	query = entityManager.createQuery(
					" SELECT TED.transactionAmount,TED.tripSheetId "
							+ " FROM TollExpensesDetails AS TED "
							+ " INNER JOIN TripSheet TR ON TR.tripSheetID = TED.tripSheetId "
							+ " where TR.vid = "+vid+" AND TR.closetripDate between "
							+ " '"+fromDate+"' AND '"+toDate+"' AND TR.tripStutesId IN ("+TripSheetStatus.TRIP_STATUS_CLOSED+", "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+") AND TED.markForDelete = 0 ",
							Object[].class);
			
			List<Object[]>	results = query.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseId(2836);
					mExpenseDto.setExpenseType((short) 4);
					mExpenseDto.setExpenseTypeName("FASTTAG TOLL CHARGES");
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
			}
		
		@Override
		public List<MonthWiseVehicleExpenseDto> getMonthWiseFastTagTollExpenseDtoByVid(Integer vid, String fromDate,
				String toDate) throws Exception {
			TypedQuery<Object[]>	query = entityManager.createQuery(
					" SELECT TED.transactionAmount,TED.tripSheetId "
							+ " FROM TollExpensesDetails AS TED "
							+ " INNER JOIN TripSheet TR ON TR.tripSheetID = TED.tripSheetId "
							+ " where TR.vid = "+vid+" AND TR.closetripDate between "
							+ " '"+fromDate+"' AND '"+toDate+"' AND TR.tripStutesId IN ("+TripSheetStatus.TRIP_STATUS_CLOSED+", "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+") AND TED.markForDelete = 0 ",
							Object[].class);
			
			List<Object[]>	results = query.getResultList();
			
			List<MonthWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					MonthWiseVehicleExpenseDto	mExpenseDto = new MonthWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseId(2836);
					mExpenseDto.setExpenseType((short) 4);
					mExpenseDto.setExpenseTypeStr("FASTTAG TOLL CHARGES");
					mExpenseDto.setTripExpenseName("FASTTAG TOLL CHARGES");
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
			}
		
		@Override
		public List<DateWiseVehicleExpenseDto> getDateWiseFastTagTollExpenseDtoByRouteId(Integer vid, String fromDate,
				String toDate, Integer routeId) throws Exception {
			TypedQuery<Object[]>	query = entityManager.createQuery(
					" SELECT TED.transactionAmount,TED.tripSheetId"
							+ " FROM TollExpensesDetails AS TED "
							+ " INNER JOIN TripSheet TR ON TR.tripSheetID = TED.tripSheetId "
							+ " where TR.vid = "+vid+" AND TR.routeID = "+routeId+" AND TR.closetripDate between "
							+ " '"+fromDate+"' AND '"+toDate+"' AND TR.tripStutesId IN ("+TripSheetStatus.TRIP_STATUS_CLOSED+", "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+") AND TED.markForDelete = 0 ",
							Object[].class);
			
			List<Object[]>	results = query.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseId(2836);
					mExpenseDto.setExpenseType((short) 4);
					mExpenseDto.setExpenseTypeName("FASTTAG TOLL CHARGES");
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
			}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<TripSheetExpenseDto> getTollExpensesForTally(String fromDate, String toDate, Integer companyId,
				String tallyCompany) throws Exception {
			try {
				HashMap<Long, TripSheetExpenseDto>  tempHM  = new HashMap<Long, TripSheetExpenseDto>();
				TripSheetExpenseDto	expenseDto = null;
				Query query = entityManager.createQuery(
						"SELECT TS.tripSheetID, TS.tripSheetNumber, TS.voucherDate, VH.vehicle_registration,"
						+ " VH.ledgerName, TC.companyName, TS.created, SE.vid,  SE.transactionAmount, SE.isPendingForTally,"
						+ " SE.fastTagBankId "
						+ " FROM TollExpensesDetails SE "
						+ " INNER JOIN Vehicle VH ON VH.vid = SE.vid "
						+ " INNER JOIN TripSheet TS ON TS.tripSheetID = SE.tripSheetId AND TS.markForDelete = 0"
						+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = TS.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
						+ " WHERE TS.voucherDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
						+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND SE.transactionAmount > 0"
						+ " AND TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+" ");
				
				List<Object[]> results = null;
				try {
					results = query.getResultList();
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}
				if (results != null && !results.isEmpty()) {
					TripSheetExpenseDto select;
					for (Object[] vehicle : results) {
						if (vehicle != null) {
							select = new TripSheetExpenseDto();
							select.setTripExpenseID((Long) vehicle[0]);
							select.setTripSheetNumber((Long) vehicle[1]);
							select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[2]));
							select.setVehicle_registration((String) vehicle[3]);
							select.setLedgerName((String) vehicle[4]);
							select.setTallyCompanyName((String) vehicle[5]);
							select.setCreatedOn((Timestamp) vehicle[6]);
							select.setExpenseFixedId((short) 2);
							select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
							select.setVid((Integer) vehicle[7]);
							select.setExpenseAmount((Double) vehicle[8]);
							select.setPendingForTally((boolean) vehicle[9]);
							select.setExpenseName("Fasttag Toll Charges");
							select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_FASTTAG);
							select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
							
							
							select.setTripSheetId(select.getTripExpenseID());
							if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
								select.setCredit(true);
							}else {
								select.setCredit(false);
							}
							
							if(select.getVendorName() == null ) {
								select.setVendorName("--");
							}
							
							if(vehicle[2] != null ) {
								select.setCreatedStr(dateFormat.format((java.util.Date)vehicle[2]));
							}
							
							 select.setRemark("FastTag Toll Charges On Vehicle "+select.getVehicle_registration()+" Date: "+dateFormat.format((java.util.Date)vehicle[2]));
							
							if(select.getTallyCompanyName() == null) {
								select.setTallyCompanyName("--");
							}
							if(select.getLedgerName() == null) {
								select.setLedgerName("--");
							}
							
							select.setTripSheetNumberStr("TS-"+select.getTripSheetNumber()+"_"+select.getTripSheetId());
							
							expenseDto	= tempHM.get(select.getTripExpenseID());
							if(expenseDto == null) {
								expenseDto = select;
							}else {
								expenseDto.setExpenseAmount(select.getExpenseAmount() + expenseDto.getExpenseAmount());
							}
							
							if(vehicle[10] != null) {
								select.setVendorName(FastTagBankConstant.getFastTagBankName((short) vehicle[10])+" FastTag");
							}else {
								select.setVendorName("HDFC Bank FastTag");
							}
							
							
							tempHM.put(select.getTripExpenseID(), expenseDto);
						}
					}
					
				}
				return new ArrayList<TripSheetExpenseDto>(tempHM.values());
				
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<TripSheetExpenseDto> getTollExpensesForTallyATC(String fromDate, String toDate, Integer companyId,
				String tallyCompany) throws Exception {
			try {
				HashMap<Long, TripSheetExpenseDto>  tempHM  = new HashMap<Long, TripSheetExpenseDto>();
				TripSheetExpenseDto	expenseDto = null;
				Query query = entityManager.createQuery(
						"SELECT TS.tripSheetID, TS.tripSheetNumber, TS.voucherDate, VH.vehicle_registration,"
						+ " VH.ledgerName, TS.created, SE.vid,  SE.transactionAmount, SE.isPendingForTally,"
						+ " SE.fastTagBankId "
						+ " FROM TollExpensesDetails SE "
						+ " INNER JOIN Vehicle VH ON VH.vid = SE.vid "
						+ " INNER JOIN TripSheet TS ON TS.tripSheetID = SE.tripSheetId AND TS.markForDelete = 0"
						+ " WHERE TS.voucherDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
						+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND SE.transactionAmount > 0"
						+ " AND TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+" ");
				
				List<Object[]> results = null;
				try {
					results = query.getResultList();
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}
				if (results != null && !results.isEmpty()) {
					TripSheetExpenseDto select;
					for (Object[] vehicle : results) {
						if (vehicle != null) {
							select = new TripSheetExpenseDto();
							select.setTripExpenseID((Long) vehicle[0]);
							select.setTripSheetNumber((Long) vehicle[1]);
							select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[2]));
							select.setVehicle_registration((String) vehicle[3]);
							select.setLedgerName((String) vehicle[4]);
							select.setTallyCompanyName(tallyCompany);
							select.setCreatedOn((Timestamp) vehicle[6]);
							select.setExpenseFixedId((short) 2);
							select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
							select.setVid((Integer) vehicle[6]);
							select.setExpenseAmount((Double) vehicle[7]);
							select.setPendingForTally((boolean) vehicle[8]);
							select.setExpenseName("Fasttag Toll Charges");
							select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_FASTTAG);
							select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
							
							
							select.setTripSheetId(select.getTripExpenseID());
							if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
								select.setCredit(true);
							}else {
								select.setCredit(false);
							}
							
							if(select.getVendorName() == null ) {
								select.setVendorName("--");
							}
							
							if(vehicle[2] != null ) {
								select.setCreatedStr(dateFormat.format((java.util.Date)vehicle[2]));
							}
							
							 select.setRemark("FastTag Toll Charges On Vehicle "+select.getVehicle_registration()+" Date: "+dateFormat.format((java.util.Date)vehicle[2]));
							
							if(select.getTallyCompanyName() == null) {
								select.setTallyCompanyName("--");
							}
							if(select.getLedgerName() == null) {
								select.setLedgerName("--");
							}
							
							select.setTripSheetNumberStr("TS-"+select.getTripSheetNumber()+"_"+select.getTripSheetId());
							
							expenseDto	= tempHM.get(select.getTripExpenseID());
							if(expenseDto == null) {
								expenseDto = select;
							}else {
								expenseDto.setExpenseAmount(select.getExpenseAmount() + expenseDto.getExpenseAmount());
							}
							
							if(vehicle[10] != null) {
								select.setVendorName(FastTagBankConstant.getFastTagBankName((short) vehicle[9])+" FastTag");
							}else {
								select.setVendorName("HDFC Bank FastTag");
							}
							
							
							tempHM.put(select.getTripExpenseID(), expenseDto);
						}
					}
					
				}
				return new ArrayList<TripSheetExpenseDto>(tempHM.values());
				
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public ValueObject karinsFasttagExpenses(List<TollExpenseData> tollExpenseList) throws Exception {
			ValueObject			valueObject			= null;
			try {
				valueObject	= new ValueObject();
				if(tollExpenseList != null && !tollExpenseList.isEmpty()) {
					tollExpenseDataRepository.saveAll(tollExpenseList);
					valueObject.put("success", true);
					valueObject.put("successMsg", "Toll Data Saved Succeessfully !");
				}
			} catch (Exception e) {
				throw e;
			}
			
			return valueObject;
		}
		
		@Override
		public HashMap<Object, Object> karinsFasttagExpenses(HashMap<Object, Object> allRequestParams) throws Exception {
			TollExpenseData			tollExpenseData			= null;
			try {
				
				tollExpenseData	= new TollExpenseData();
				
				if(allRequestParams.containsKey("push_id"))
					tollExpenseData.setPush_id((String) allRequestParams.get("push_id"));
				if(allRequestParams.containsKey("customer_id"))
					tollExpenseData.setCustomer_id((String) allRequestParams.get("customer_id"));
				if(allRequestParams.containsKey("customer_name"))
					tollExpenseData.setCustomer_name((String) allRequestParams.get("customer_name"));
				if(allRequestParams.containsKey("customer_first_name"))
					tollExpenseData.setCustomer_first_name((String) allRequestParams.get("customer_first_name"));
				if(allRequestParams.containsKey("customer_last_name"))
					tollExpenseData.setCustomer_last_name((String) allRequestParams.get("customer_last_name"));
				if(allRequestParams.containsKey("mobile_number"))
					tollExpenseData.setMobile_number(allRequestParams.get("mobile_number")+"");
				if(allRequestParams.containsKey("email_id"))
					tollExpenseData.setEmail_id((String) allRequestParams.get("email_id"));
				if(allRequestParams.containsKey("tag_id"))
					tollExpenseData.setTag_id((String) allRequestParams.get("tag_id"));
				if(allRequestParams.containsKey("vehicle_id"))
					tollExpenseData.setVehicle_id((String) allRequestParams.get("vehicle_id"));
				if(allRequestParams.containsKey("vehicle_no"))
					tollExpenseData.setVehicle_no((String) allRequestParams.get("vehicle_no"));
				if(allRequestParams.containsKey("transaction_datetime"))
					tollExpenseData.setTransaction_datetime(sqlFormat.parse(allRequestParams.get("transaction_datetime")+""));
				if(allRequestParams.containsKey("reader_datetime"))
					tollExpenseData.setReader_datetime(sqlFormat.parse(allRequestParams.get("reader_datetime")+""));
				if(allRequestParams.containsKey("transaction_amount"))
					tollExpenseData.setTransaction_amount(Double.parseDouble(allRequestParams.get("transaction_amount")+""));
				if(allRequestParams.containsKey("balance_amount"))
					tollExpenseData.setBalance_amount(Double.parseDouble(allRequestParams.get("balance_amount")+""));
				if(allRequestParams.containsKey("transaction_reference_number"))
					tollExpenseData.setTransaction_reference_number(allRequestParams.get("transaction_reference_number")+"");
				if(allRequestParams.containsKey("transaction_id"))
					tollExpenseData.setTransaction_id(allRequestParams.get("transaction_id")+"");
				if(allRequestParams.containsKey("transaction_type"))
					tollExpenseData.setTransaction_type((String) allRequestParams.get("transaction_type"));
				if(allRequestParams.containsKey("transaction_status"))
					tollExpenseData.setTransaction_status((String) allRequestParams.get("transaction_status"));
				if(allRequestParams.containsKey("lane_code"))
					tollExpenseData.setLane_code((String) allRequestParams.get("lane_code"));
				if(allRequestParams.containsKey("plaza_code"))
					tollExpenseData.setPlaza_code((String) allRequestParams.get("plaza_code"));
				if(allRequestParams.containsKey("plaza_name"))
					tollExpenseData.setPlaza_name((String) allRequestParams.get("plaza_name"));
				if(allRequestParams.containsKey("plazaName"))
					tollExpenseData.setPlazaName((String) allRequestParams.get("plazaName"));
				if(allRequestParams.containsKey("direction"))
					tollExpenseData.setDirection((String) allRequestParams.get("direction"));
				if(allRequestParams.containsKey("lat"))
					tollExpenseData.setLat(allRequestParams.get("lat")+"");
				if(allRequestParams.containsKey("longitude"))
					tollExpenseData.setLongitude(allRequestParams.get("longitude")+"");
				if(allRequestParams.containsKey("rrn"))
					tollExpenseData.setRrn(allRequestParams.get("rrn")+"");
				if(allRequestParams.containsKey("remark"))
					tollExpenseData.setRemark((String) allRequestParams.get("remark"));
				if(allRequestParams.containsKey("comments"))
					tollExpenseData.setComments((String) allRequestParams.get("comments"));
				if(allRequestParams.containsKey("currency"))
					tollExpenseData.setCurrency((String) allRequestParams.get("currency"));
				
				tollExpenseDataRepository.save(tollExpenseData);
				
				allRequestParams.clear();
				
				allRequestParams.put("success", true);
				
				return allRequestParams;
			} catch (Exception e) {
				throw e;
			}
		}
}
