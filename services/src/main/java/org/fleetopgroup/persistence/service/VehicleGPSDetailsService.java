package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.GPSConstant;
import org.fleetopgroup.constant.MyTrustManager;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VehicleGPSConfiguratinConstant;
import org.fleetopgroup.persistence.dao.VehicleGPSDataRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleGPSData;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDataService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@Service
public class VehicleGPSDetailsService implements IVehicleGPSDetailsService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	SimpleDateFormat	format	= new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	private static DecimalFormat decimalFormat = new DecimalFormat("#.##");
	
	public static final Integer	GPS_VENDOR_OMNI_TALK	= 1;
	public static final Integer	GPS_VENDOR_INTANGLES	= 2;
	public static final Integer	GPS_VENDOR_IDEAGAMI		= 3;
	public static final Integer	GPS_VENDOR_SMTC			= 4;
	public static final Integer	GPS_VENDOR_ITS			= 5;
	
	@PersistenceContext	public EntityManager entityManager;

	
	@Autowired	private ICompanyConfigurationService		companyConfigurationService;
	@Autowired	private	VehicleGPSDataRepository			vehicleGPSDataRepository;
	@Autowired	private IVehicleService						vehicleService;
	@Autowired	private	IVehicleGPSDataService				vehicleGPSDataService;
	@Autowired	private ITripSheetService					tripSheetService;
	@Autowired	private IFuelService						fuelService;
	@Autowired	private	IVehicleOdometerHistoryService		vehicleOdometerHistoryService;
	
	@Override
	public ValueObject getVehicleGPSDetails(ValueObject valueObject) throws Exception {
		HashMap<String, Object> 	configuration	= null;
		CustomUserDetails			userDetails		= null;
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			return getVehicleGPSDetailsByVehicleId(valueObject.getString("vehicle_Id"), configuration);
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}finally {
			configuration	= null;
			userDetails		= null;
		}
	}

	@Override
	public ValueObject getVehicleGPSDetailsByVehicleId(String gpsId, HashMap<String, Object> configuration)
			throws Exception {
		ValueObject			valueObject		= null;
		try {
		
			disableSSL();
			valueObject	= new ValueObject();
			valueObject.put("isOdometerReading", false);
			JSONObject json = new JSONObject();
    		json.put("User_Id", (String)configuration.get(VehicleGPSConfiguratinConstant.GPS_USER_ID));
    		json.put("Vehicle_Id", gpsId);
			
			RestTemplate restTemplate = new RestTemplate(); 
			
			 HttpEntity<String> entity = new HttpEntity<String>(json.toString(), getHeadersWithClientCredentials(configuration));
	            
	            ResponseEntity<String> loginResponse = restTemplate
	            		  .exchange(VehicleGPSConfiguratinConstant.SAINI_TRAVELS_GPS_URL, HttpMethod.POST, entity, String.class);
	            
			if(loginResponse.getStatusCodeValue() == 200) {
				valueObject	= new ValueObject();
				JSONObject	jsonObject = new JSONObject(loginResponse.getBody());
				
				if(jsonObject.has("VEHICLES")) {
    				if(!jsonObject.getJSONArray("VEHICLES").isNull(0)) {
    					
    					JSONObject	object	=		(JSONObject) jsonObject.getJSONArray("VEHICLES").get(0);
    					//valueObject.put("VEHICLE_ODOMETER", object.get("VEHICLE_ODOMETER"));
    					valueObject.put(GPSConstant.GPS_VEHICLE_ID_NAME, object.getString("VEHICLE_ID"));
    		        	valueObject.put(GPSConstant.GPS_VEHICLE_ALIAS_NAME, object.getString("VEHICLE_RTO_NO"));
    		        	valueObject.put(GPSConstant.GPS_DATE_TIME_NAME, object.getString("VEHICLE_GPS_DATETIME"));
    		        	valueObject.put(GPSConstant.GPS_LOCATION_NAME, object.getString("VEHICLE_LOCATION"));
    		        	valueObject.put(GPSConstant.VEHICLE_LATITUDE_NAME, object.get("VEHICLE_LAT"));
    		        	valueObject.put(GPSConstant.VEHICLE_LONGITUDE_NAME, object.get("VEHICLE_LONG"));
    		        	valueObject.put(GPSConstant.VEHICLE_SPEED_NAME, object.get("VEHICLE_SPEED"));
    		        	valueObject.put(GPSConstant.VEHICLE_DAY_KM_NAME, object.get("VEHICLE_DAYKM"));
    		        	valueObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME, object.get("VEHICLE_ODOMETER"));
    		        	valueObject.put(GPSConstant.VEHICLE_GPS_WORKING, true);
    				}
    			}
			}
			
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			return null;
		}
	}
	
	@Override
	public ValueObject getVehicleGPSDetailsAtSpecifiedTime(ValueObject valueObject) throws Exception {
		HashMap<String, Object> 	configuration	= null;
		//CustomUserDetails			userDetails		= null;
		String						dispatchDate	= null;
		String						dispatchTime	= null;
		try {
			//userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			dispatchDate	= valueObject.getString("dispatchedByTime");
			dispatchTime	= valueObject.getString("dispatchTime");
			
			Timestamp	fromDate	= new Timestamp(DateTimeUtility.getDateTimeFromDateTimeString(dispatchDate, dispatchTime).getTime());
			
			Timestamp	toDate	=	new Timestamp(DateTimeUtility.getDateTimeFromDateTimeString(dispatchDate, dispatchTime).getTime());
			
			toDate.setTime(toDate.getTime() + TimeUnit.MINUTES.toMillis(1));
			
			disableSSL();
			
    		JSONObject json = new JSONObject();
    		json.put("User_Id", (String)configuration.get(VehicleGPSConfiguratinConstant.GPS_USER_ID));
    		json.put("Vehicle_Id", valueObject.getString("vehicleGPSId"));
    		json.put("fromdate", fromDate+"");
    		json.put("todate", toDate+"");
    	
    		HttpResponse<String> response = Unirest.post(VehicleGPSConfiguratinConstant.SAINI_TRAVELS_GPS_URL_FOR_SPECIFIED_TIME)
    				  .header("Content-Type", "application/json")
    				  .header("Authorization", "Basic "+(String)configuration.get(VehicleGPSConfiguratinConstant.SAINI_TRAVELS_AUTHORIZATION_TOKEN))
    				  .body(json)
    				  .asString();
    		JSONObject	jsonObject = null;
    		 //JSONArray jsonArr = new JSONArray(response.getBody());
    		if(response.getStatus() == 200) {
    			jsonObject	= new JSONObject(response.getBody());
    			if(jsonObject.has("VEHICLES")) {
    				if(!jsonObject.getJSONArray("VEHICLES").isNull(0)) {
    					
    					JSONObject	object	=		(JSONObject) jsonObject.getJSONArray("VEHICLES").get(0);
    					valueObject.put("VEHICLE_ODOMETER", object.get("VEHICLE_ODOMETER"));
    					valueObject.put(GPSConstant.VEHICLE_GPS_WORKING, true);
    				}
    			}
    		}
    		valueObject.put("isOdometerReading", false);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
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
	  }}
	 
	 private static HttpHeaders getHeadersWithClientCredentials(HashMap<String, Object> 	configuration){
	    	
	    	HttpHeaders headers = getHeaders();
	    	headers.add("Authorization", "Basic "+(String)configuration.get(VehicleGPSConfiguratinConstant.SAINI_TRAVELS_AUTHORIZATION_TOKEN));
	    	return headers;
	    }  
	    
	 private static HttpHeaders getHeaders(){
	    	HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
	    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    	return headers;
	    }
	 
	 
	 @Override
	public ValueObject getTotalDistanceTraveledBetweenTwoDateTime(ValueObject valueObject) throws Exception {
		try {
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	 
	 @Override
	public Double getVehicleGpsUsageKM(HashMap<String, Object> configuration, ValueObject valueObject)
			throws Exception {
			Timestamp					fromDate			= null;
			Timestamp					toDate				= null;
			Double						VEHICLE_ODOMETER	= 0.0;
			Double						highyestOdoMeter	= 0.0;
			Double						currentOdometer		= 0.0;
			try {
				
				fromDate = DateTimeUtility.getTimeStamp(valueObject.getString("fromDate"), "yyyy-MM-dd HH:mm:ss");
				toDate	= DateTimeUtility.getTimeStamp(valueObject.getString("toDate"), "yyyy-MM-dd HH:mm:ss");
				
				
	    		JSONObject json = new JSONObject();
	    		json.put("User_Id", (String)configuration.get(VehicleGPSConfiguratinConstant.GPS_USER_ID));
	    		json.put("Vehicle_Id", valueObject.getString("vehicleGPSId"));
	    		json.put("fromdate", fromDate+"");
	    		json.put("todate", toDate+"");
	    	
	    		HttpResponse<String> response = Unirest.post(VehicleGPSConfiguratinConstant.SAINI_TRAVELS_GPS_URL_FOR_SPECIFIED_TIME)
	    				  .header("Content-Type", "application/json")
	    				  .header("Authorization", "Basic "+(String)configuration.get(VehicleGPSConfiguratinConstant.SAINI_TRAVELS_AUTHORIZATION_TOKEN))
	    				  .body(json)
	    				  .asString();
	    		
	    		JSONObject	jsonObject = null;
	    		 //JSONArray jsonArr = new JSONArray(response.getBody());
	    		if(response.getStatus() == 200) {
	    			jsonObject	= new JSONObject(response.getBody());
	    			
	    			if(jsonObject.has("VEHICLES")) {
	    				if(!jsonObject.getJSONArray("VEHICLES").isNull(0)) {
	    					JSONArray	jSONArray	= jsonObject.getJSONArray("VEHICLES");
	    					if (jSONArray != null) { 
	    						
	    						JSONObject	object	= null;
	    						
	    						   for (int i=0;i<jSONArray.length();i++){ 
	    							   object	= (JSONObject) jSONArray.get(i);
	    							   VEHICLE_ODOMETER = object.getDouble("VEHICLE_ODOMETER");
	    							   if(highyestOdoMeter < VEHICLE_ODOMETER) {
	    								   highyestOdoMeter	= VEHICLE_ODOMETER;
	    							   }
	    						   } 
	    						   JSONObject	lastObject	= (JSONObject) jSONArray.get(jSONArray.length() - 1);
	    						   currentOdometer = lastObject.getDouble("VEHICLE_ODOMETER");
	    						   
	    						}
	    					
	    				}
	    			}
	    		}
	    		 
	    		 
				return (currentOdometer + highyestOdoMeter) - valueObject.getDouble("tripOpenGPSKM", 0);
			} catch (Exception e) {
				throw e;
			}
		}
	 
	 @Override
	public void saveVehicleGPSDataIntangles(ValueObject valueObject) throws Exception {
		try {
			VehicleGPSData	vehicleGPSData	= new VehicleGPSData();
			
			vehicleGPSData.setVehicleOdometer(valueObject.getInt("Odometer", 0));
			vehicleGPSData.setVehicleNumber(valueObject.getString("vehicle_id"));
			vehicleGPSData.setRfid(valueObject.getString("refid"));
			vehicleGPSData.setDateTime(new Timestamp(valueObject.getLong("time")));
			
			vehicleGPSDataRepository.save(vehicleGPSData);
			
		} catch (Exception e) {
			throw	e;
		}
		
	}
	 
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject saveVehicleGPSDataIdeagami(ValueObject valueObject) throws Exception {
		 ArrayList<Map<String, Object>>  	vehicleGPSDataList		= null;
		 VehicleGPSData						vehicleGPSData			= null; 
		 ArrayList<VehicleGPSData> 			List					= null;
		 try {
			 if(valueObject != null) {
				 
				 vehicleGPSDataList			= new ArrayList<>();
				 List						= new ArrayList<>();
				 
				 vehicleGPSDataList			=  ( ArrayList<Map<String, Object>>) valueObject.get("VehicleGPSDataList");
				 for (Map<String, Object> entry : vehicleGPSDataList) {
					 for (@SuppressWarnings("unused") String key : entry.keySet()) {
						 vehicleGPSData = new VehicleGPSData();
						 vehicleGPSData.setVehicleNumber((String) entry.get("VehicleNumber"));
						 vehicleGPSData.setDateTime(DateTimeUtility.getTimeStampFromStringInYY_MM_DD_HH_MM_SS_Format((String) entry.get("Time")));
						 vehicleGPSData.setAddress((String) entry.get("Address"));
						 vehicleGPSData.setLatitude(String.valueOf((Double)entry.get("latitude")));
						 vehicleGPSData.setLongitude(String.valueOf((Double)entry.get("longitude")));
						 vehicleGPSData.setGpsDeviceId((String) entry.get("Device ID/IMEI"));
					 }
					 List.add(vehicleGPSData);
				 }
				 vehicleGPSDataRepository.saveAll(List);
				
			 }
			 valueObject.put("List", List);
			 return valueObject;
		 }catch(Exception e) {
			 LOGGER.error("err"+e);
		throw e;
	}
	}
	 
	 @Override
	public ValueObject getVehicleGPSData(ValueObject valueObject) throws Exception {
		HashMap<String, Object> 	configuration		= null;
		Vehicle						vehicle				= null;
		 try {
			 configuration	=	companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			 vehicle		= 	vehicleService.getVehicel(valueObject.getInt("vehicleId"), valueObject.getInt("companyId"));
			
			 valueObject.put("configuration", configuration);
			 
			 return getVehicleGPSByVendorProvider(valueObject, vehicle);
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			return null;
		}finally {
			configuration		= null;
			vehicle				= null;
		}
	}
	 
	 @Override
	public ValueObject getVehicleGPSDataAtSpecifiedTime(ValueObject valueObject) throws Exception {
			HashMap<String, Object> 	configuration		= null;
			Vehicle						vehicle				= null;
			 try {
				 configuration	=	companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.VEHICLE_GPS_CONFIG);
				
				 vehicle		= 	vehicleService.getVehicel(valueObject.getInt("vehicleId"), valueObject.getInt("companyId"));
				
				 valueObject.put("configuration", configuration);
				 
				 return getVehicleGPSByVendorProviderAtSpecifiedTime(valueObject, vehicle);
			} catch (Exception e) {
				LOGGER.error("Exception : ", e);
				return null;
			}finally {
				configuration		= null;
				vehicle				= null;
			}
		}
	 
	private ValueObject	getVehicleGPSByVendorProvider(ValueObject	valueObject, Vehicle vehicle) throws Exception{
		ValueObject		valueOutObject		= null;
		try {
			
			
			if(vehicle != null && vehicle.getGpsVendorId() != null && vehicle.getGpsVendorId() > 0) {
				
				switch (vehicle.getGpsVendorId()) {
				case 1:
					valueOutObject	= getVehicleGPSDataOmniTalk(valueObject, vehicle);
					break;
				case 2:
					valueOutObject	= getVehicleGPSDataIntangles(valueObject, vehicle);
					break;
				case 3:
					valueOutObject	= getVehicleGPSDataIDeaGami(valueObject, vehicle);
					break; 
				case 4:
					valueOutObject	= getVehicleGPSDataSMTC(valueObject, vehicle);	
					break;
//				case 8:
//					valueObject.put("vehicleChasis", vehicle.getVehicle_chasis());
//					valueOutObject		=	vehicleGPSDataService.getTataRunKM(valueObject);
//					break;
				default:
					break;
				}
			}
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}
	 }
	public ValueObject	getVehicleGPSByVendorProviderAtSpecifiedTime(ValueObject	valueObject, Vehicle vehicle) throws Exception{
		ValueObject		valueOutObject		= null;
		try {
			valueOutObject	= new ValueObject();
			if(vehicle != null && vehicle.getGpsVendorId() != null && vehicle.getGpsVendorId() > 0) {
				
				switch (vehicle.getGpsVendorId()) {
				case 1:
					valueOutObject	= getVehicleGPSDataOmniTalkAtSpecifiedTime(valueObject, vehicle);
					break;
				case 2:
					valueOutObject	= getVehicleGPSDataIntanglesAtSpecifiedTime(valueObject, vehicle);
					break;
				case 3:
					valueOutObject	= getVehicleGPSDataIdeaGamiAtSpecifiedTime(valueObject, vehicle);
					break;
				case 4:
					valueOutObject	= getVehicleGPSDataSMTCAtSpecifiedTime(valueObject, vehicle);
					break;
				case 8:
					valueOutObject	= getTataRunKM(valueObject,vehicle);
					break;
				default:
					break;
				}
			}
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}
	 }
	
	
	@SuppressWarnings("unchecked")
	public ValueObject getTataRunKM(ValueObject valueObject,Vehicle vehicle) throws Exception {
		Double						gpsUsageKM			= 0.0;
		JSONObject					jsonObject 			= null;
		ValueObject					valueOutObject  	= new ValueObject();
		String 						dispatchDate		= null;
		String 						dispatchTime		= null;
		HashMap<String, Object> 	configuration		= null;
		
		try {
			dispatchDate		= valueObject.getString("dispatchedByTime");
			dispatchTime		= valueObject.getString("dispatchTime");
			configuration	= (HashMap<String, Object>) valueObject.get("configuration");
			boolean fromOpening = false;
			if(valueObject.getBoolean("fromFuel",false))
				fromOpening = true;
			if(valueObject.getBoolean("fromTripOpening",false))
				fromOpening = true;
			Timestamp	date	= new Timestamp(DateTimeUtility.getDateTimeFromDateTimeString(dispatchDate, dispatchTime).getTime());
    		Unirest.setTimeouts(0, 0);
    		HttpResponse<String> response = Unirest.post((String) configuration.get("tataAPISingleDateLink"))
    		  .header("Content-Type", "application/x-www-form-urlencoded")
    		  .field("date", date)
    		  .field("fromOpening", fromOpening)
    		  .field("vid", vehicle.getVid())
    		  .field("companyId", vehicle.getCompany_Id())
    		  .asString();
    		if(response.getStatus() == 200){
    			jsonObject	= new JSONObject(response.getBody());
    			if(jsonObject.has("eventDate")) {
    				gpsUsageKM =Utility.round(jsonObject.getDouble("gpsOdometer"), 2); 
    				valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, true);
    				valueOutObject.put("isOdometerReading", true);
    			}
    		}
    		valueOutObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME,gpsUsageKM);
    		valueOutObject.put("gpsUsageKM", gpsUsageKM);
    		 return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	private ValueObject	getVehicleGPSDataOmniTalk(ValueObject	valueObject, Vehicle vehicle) throws Exception{
		try {
			
			return getVehicleGPSDetailsByVehicleId(vehicle.getVehicleGPSId(), (HashMap<String, Object>)valueObject.get("configuration"));
		} catch (Exception e) {
			throw e;
		}
	}
	
	private ValueObject	getVehicleGPSDataOmniTalkAtSpecifiedTime(ValueObject	valueObject, Vehicle vehicle) throws Exception{
		try {
			
			return getVehicleGPSDetailsAtSpecifiedTime(valueObject);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	private ValueObject	getVehicleGPSDataIntangles(ValueObject	valueObject, Vehicle vehicle) throws Exception{
		ValueObject			valueOutObject		= null;
		VehicleGPSData		vehicleGPSData		= null;
		try {
			
			valueOutObject	= new ValueObject();
			valueOutObject.put("isOdometerReading", true);
			vehicleGPSData	= getVehicleGPSDataByNumber(vehicle.getVehicle_registration().replace("-", ""));
			if(vehicleGPSData != null) {
				
				valueOutObject.put(GPSConstant.GPS_VEHICLE_ID_NAME, vehicleGPSData.getVehicleNumber());
				valueOutObject.put(GPSConstant.GPS_DATE_TIME_NAME, vehicleGPSData.getDateTime());
				valueOutObject.put(GPSConstant.VEHICLE_LATITUDE_NAME, vehicleGPSData.getLatitude());
				valueOutObject.put(GPSConstant.VEHICLE_LONGITUDE_NAME, vehicleGPSData.getLongitude());
				valueOutObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME, vehicleGPSData.getVehicleOdometer());
				valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, true);
			}else {
				valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, false);
			}
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueOutObject		= null;
			vehicleGPSData		= null;
		}
	}
	

	private ValueObject	getVehicleGPSDataIDeaGami(ValueObject	valueObject, Vehicle vehicle) throws Exception{
		ValueObject			valueOutObject		= null;
		boolean				fromTripSheetClose	= false;
		List<VehicleGPSData>	vehicleGpsList	= null;
		Timestamp				fromDate		= null;
		Timestamp				toDate			= null;
		Integer					runKM			= 0;
		Integer					openKM			= 0;
		Double					gpsOpenKM		= 0.0;
		try {
			
			valueOutObject	= new ValueObject();
			
			openKM		= valueObject.getInt("openKM",0);
			gpsOpenKM	= valueObject.getDouble("openGPSKM",0.0);
			
			valueOutObject.put("isOdometerReading", false);
			
			if(vehicle != null) {
				fromTripSheetClose	= valueObject.getBoolean("fromTripSheetClose", false);
				if(fromTripSheetClose) {
					fromDate		= (Timestamp) valueObject.get("fromDate");
					toDate			= (Timestamp) valueObject.get("toDate");
					if(vehicle.getVehicleGPSId() != null)
						vehicleGpsList	=	vehicleGPSDataService.getVehicleGPSDataInDateRange(fromDate, toDate, vehicle.getVehicleGPSId());
					
				}
				if(vehicleGpsList != null && !vehicleGpsList.isEmpty()) {
					runKM	= Utility.calculateRunKM(vehicleGpsList);
					if(gpsOpenKM != null && gpsOpenKM > 0) {
						valueOutObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME, runKM + gpsOpenKM.intValue());
					}else {
						valueOutObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME, runKM + openKM);
					}
					valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, true);
				}else {
					VehicleOdometerHistory	history	=	vehicleOdometerHistoryService.getOdoMeterAtSpecifiedTime(vehicle.getVid(), DateTimeUtility.getCurrentTimeStamp());
					valueOutObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME, history.getVehicle_Odometer());
					valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, false);
				}
				valueOutObject.put(GPSConstant.GPS_VEHICLE_ID_NAME, vehicle.getVehicle_registration());
				
			}
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueOutObject		= null;
			vehicleGpsList	= null;
			fromDate		= null;
			toDate			= null;
			runKM			= 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	private ValueObject	getVehicleGPSDataSMTC(ValueObject	valueObject, Vehicle vehicle) throws Exception{
		ValueObject			valueOutObject		= null;
		boolean				fromTripSheetClose	= false;
		Timestamp				fromDate		= null;
		Timestamp				toDate			= null;
		HashMap<String, Object> configuration	= null;
		JSONObject				jsonObject		= null;
		try {
			
			valueOutObject	= new ValueObject();
			
			valueOutObject.put("isOdometerReading", false);
			if(vehicle != null) {
				configuration	= (HashMap<String, Object>) valueObject.get("configuration");
				
				fromTripSheetClose	= valueObject.getBoolean("fromTripSheetClose", false);
				if(fromTripSheetClose) {
					fromDate	= (Timestamp) valueObject.get("fromDate");
					toDate		= (Timestamp) valueObject.get("toDate");
					jsonObject	=	vehicleGPSDataService.getVehicleGPSDataInDateRange(fromDate, toDate, vehicle, configuration);
					
				}
				
				if(jsonObject != null) {
					Double km = jsonObject.getDouble("length");
					valueOutObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME, vehicle.getVehicle_Odometer() + km.intValue());
					valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, true);
				}else {
					valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, false);
					if(!fromTripSheetClose) {
						valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, true);
						valueOutObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME, vehicle.getVehicle_Odometer());
					}
				}
				valueOutObject.put(GPSConstant.GPS_VEHICLE_ID_NAME, vehicle.getVehicle_registration());
				
			}
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueOutObject		= null;
			fromTripSheetClose	= false;
			fromDate		= null;
			toDate			= null;
			configuration	= null;
			jsonObject		= null;
		}
	}
	
	
	private ValueObject	getVehicleGPSDataIntanglesAtSpecifiedTime(ValueObject	valueObject, Vehicle vehicle) throws Exception{
		ValueObject			valueOutObject		= null;
		VehicleGPSData		vehicleGPSData		= null;
		String				dispatchDate		= null;
		String				dispatchTime		= null;
		try {
			
			valueOutObject	= new ValueObject();
			valueOutObject.put("isOdometerReading", true);
			dispatchDate	= valueObject.getString("dispatchedByTime");
			dispatchTime	= valueObject.getString("dispatchTime");
			
			Timestamp	date	= new Timestamp(DateTimeUtility.getDateTimeFromDateTimeString(dispatchDate, dispatchTime).getTime());
			
			
			vehicleGPSData	= getVehicleGPSDataByNumberAtSprcifiedTime(vehicle.getVehicle_registration().replace("-", ""), date);
			
			
			if(vehicleGPSData != null) {
				
				valueOutObject.put(GPSConstant.GPS_VEHICLE_ID_NAME, vehicleGPSData.getVehicleNumber());
				valueOutObject.put(GPSConstant.GPS_DATE_TIME_NAME, vehicleGPSData.getDateTime());
				valueOutObject.put(GPSConstant.VEHICLE_LATITUDE_NAME, vehicleGPSData.getLatitude());
				valueOutObject.put(GPSConstant.VEHICLE_LONGITUDE_NAME, vehicleGPSData.getLongitude());
				valueOutObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME, vehicleGPSData.getVehicleOdometer());
				valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, true);
			}else {
				valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, false);
			}
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueOutObject		= null;
			vehicleGPSData		= null;
		}
	}
	
	private ValueObject	getVehicleGPSDataIdeaGamiAtSpecifiedTime(ValueObject	valueObject, Vehicle vehicle) throws Exception{
		ValueObject				valueOutObject		= null;
		//VehicleGPSData		vehicleGPSData		= null;
		String					dispatchDate		= null;
		String					dispatchTime		= null;
		List<VehicleGPSData>	vehicleGPSDataList	= null;
		Integer					runKM				= 0;
		Long					tripSheetId			= (long) 0;
		boolean					fromTripSheetClose	= false;
		boolean					fromFuel			= false;
		try {
			
			valueOutObject	= new ValueObject();
			valueOutObject.put("isOdometerReading", false);
			dispatchDate	= valueObject.getString("dispatchedByTime");
			dispatchTime	= valueObject.getString("dispatchTime");
			tripSheetId		= valueObject.getLong("tripSheetId");
			Timestamp	date	= new Timestamp(DateTimeUtility.getDateTimeFromDateTimeString(dispatchDate, dispatchTime).getTime());
			
			fromTripSheetClose	= valueObject.getBoolean("fromTripSheetClose", false);
			fromFuel			= valueObject.getBoolean("fromFuel", false);
			//vehicleGPSData	= getVehicleGPSDataByNumberAtSprcifiedTime(vehicle.getVehicle_registration().replace("-", ""), date);
			
			//VehicleOdometerHistory	odometerHistory	= vehicleOdometerHistoryService.getOdoMeterAtSpecifiedTime(vehicle.getVid(), date);
			if(fromTripSheetClose) {
				TripSheet sheet = tripSheetService.getTripSheetDetails(tripSheetId);
				if(vehicle.getVehicleGPSId() != null)
					vehicleGPSDataList = vehicleGPSDataService.getVehicleGPSDataInDateRange(DateTimeUtility.getTimeStampFromDate(sheet.getDispatchedByTime()), date, vehicle.getVehicleGPSId());
			}
			
			if(fromFuel) {
				Fuel fuel = fuelService.getLastFuelEntrieDetails(vehicle.getVid(), date);
				if(fuel != null) {
					if(vehicle.getVehicleGPSId() != null)
					vehicleGPSDataList = vehicleGPSDataService.getVehicleGPSDataInDateRange(DateTimeUtility.getTimeStampFromDate(fuel.getFuelDateTime()), date, vehicle.getVehicleGPSId());
				}
			}
			
			if(vehicleGPSDataList != null) {
				runKM	= Utility.calculateRunKM(vehicleGPSDataList);
				valueOutObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME, vehicle.getVehicle_Odometer() + runKM);
				valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, true);
			}else {
				VehicleOdometerHistory	history	=	vehicleOdometerHistoryService.getOdoMeterAtSpecifiedTime(vehicle.getVid(), date);
				valueOutObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME, history.getVehicle_Odometer());
				valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, false);
			}
			valueOutObject.put(GPSConstant.GPS_VEHICLE_ID_NAME, vehicle.getVehicle_registration());
				
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueOutObject			= null;
			vehicleGPSDataList		= null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private ValueObject	getVehicleGPSDataSMTCAtSpecifiedTime(ValueObject	valueObject, Vehicle vehicle) throws Exception{
		ValueObject				valueOutObject		= null;
		String					dispatchDate		= null;
		String					dispatchTime		= null;
		Long					tripSheetId			= (long) 0;
		boolean					fromTripSheetClose	= false;
		boolean					fromFuel			= false;
		HashMap<String, Object> configuration	= null;
		JSONObject				jsonObject		= null;
		try {
			
			valueOutObject	= new ValueObject();
			valueOutObject.put("isOdometerReading", false);
			dispatchDate	= valueObject.getString("dispatchedByTime");
			dispatchTime	= valueObject.getString("dispatchTime");
			tripSheetId		= valueObject.getLong("tripSheetId");
			Timestamp	date	= new Timestamp(DateTimeUtility.getDateTimeFromDateTimeString(dispatchDate, dispatchTime).getTime());
			
			fromTripSheetClose	= valueObject.getBoolean("fromTripSheetClose", false);
			fromFuel			= valueObject.getBoolean("fromFuel", false);
			configuration	= (HashMap<String, Object>) valueObject.get("configuration");
			if(fromTripSheetClose) {
				TripSheet sheet = tripSheetService.getTripSheetDetails(tripSheetId);
				jsonObject	=	vehicleGPSDataService.getVehicleGPSDataInDateRange(DateTimeUtility.getTimeStampFromDate(sheet.getDispatchedByTime()), date, vehicle, configuration);
				
			}
			if(fromFuel) {
				Fuel fuel = fuelService.getLastFuelEntrieDetails(vehicle.getVid(), date);
				if(fuel != null) {
					jsonObject = vehicleGPSDataService.getVehicleGPSDataInDateRange(DateTimeUtility.getTimeStampFromDate(fuel.getFuelDateTime()), date, vehicle, configuration);
				}
				
			}
			if(jsonObject != null) {
				Double km = jsonObject.getDouble("length");
				valueOutObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME, vehicle.getVehicle_Odometer() + km.intValue());
				valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, true);
			}else {

				VehicleOdometerHistory	history	=	vehicleOdometerHistoryService.getOdoMeterAtSpecifiedTime(vehicle.getVid(), date);
				if(history != null) {
					valueOutObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME, history.getVehicle_Odometer());	
				}else {
					valueOutObject.put(GPSConstant.VEHICLE_TOTAL_KM_NAME, 1);
				}
				valueOutObject.put(GPSConstant.VEHICLE_GPS_WORKING, false);
			
			}
			valueOutObject.put(GPSConstant.GPS_VEHICLE_ID_NAME, vehicle.getVehicle_registration());
				
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueOutObject			= null;
		}
	}
	
	
	@Override
	public VehicleGPSData getVehicleGPSDataByNumber(String vehicleNumber) throws Exception {

		TypedQuery<VehicleGPSData> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM VehicleGPSData AS T"
						+ " where vehicleNumber = '"+vehicleNumber+"'  ORDER BY vehicleGPSDataId DESC", VehicleGPSData.class);

		VehicleGPSData	dateWiseVehicleExpense = null;
		try {
			query.setMaxResults(1);
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return dateWiseVehicleExpense;
	}
	
	public VehicleGPSData getVehicleGPSDataByNumberAtSprcifiedTime(String vehicleNumber, Timestamp	dateTime) throws Exception {

		TypedQuery<VehicleGPSData> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM VehicleGPSData AS T"
						+ " where T.vehicleNumber = '"+vehicleNumber+"' AND T.dateTime <= '"+dateTime+"' ORDER BY T.vehicleGPSDataId DESC ", VehicleGPSData.class);

		VehicleGPSData	dateWiseVehicleExpense = null;
		try {
			query.setMaxResults(1);
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			
			try {
				TypedQuery<VehicleGPSData> query2 = entityManager.createQuery(
						"SELECT T "
						+ " FROM VehicleGPSData AS T"
								+ " where T.vehicleNumber = '"+vehicleNumber+"' AND T.dateTime >= '"+dateTime+"' ORDER BY T.vehicleGPSDataId ASC", VehicleGPSData.class);
				query2.setMaxResults(1);
				dateWiseVehicleExpense = query2.getSingleResult();
				
			} catch (NoResultException nre2) {
				
			}
		}

		return dateWiseVehicleExpense;
	}

	@Override
	public Double getGPSRunKMBetweenTwoDates(Integer vid, String fromDate, String toDate, Integer companyId) throws Exception {
		
		  ValueObject valueObject = null;
		 
		Double gpsUsageKM			= 0.0;
		HashMap<String, Object>    configuration	= null;
		try {
			
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			VehicleDto	vehicle	=	vehicleService.getVehicel_DropDown_Last_Fuel_ADD_entries(vid, companyId);
			
			if(vehicle != null && vehicle.getGpsVendorId() != null && vehicle.getGpsVendorId() > 0) {
				
				switch (vehicle.getGpsVendorId()) {
				case 1:
					if(vehicle.getFuelDateTime() != null) {
						valueObject	= new ValueObject();
						valueObject.put("fromDate", fromDate);
						valueObject.put("toDate", toDate);
						valueObject.put("vehicleGPSId", vehicle.getVehicleGPSId());
						valueObject.put("tripOpenGPSKM", vehicle.getGpsOdameter());
						gpsUsageKM	= getOmniTalkRunKM(configuration , valueObject);
					}
					break;
				case 2:
						gpsUsageKM	= getIntanglesRunKM(vehicle.getVehicle_registration(), fromDate, toDate);
					break;
				case 3:
						valueObject	= new ValueObject();
						valueObject.put("fromDate", DateTimeUtility.getTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
						valueObject.put("toDate", DateTimeUtility.getTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
						valueObject.put("vehicleGPSId", vehicle.getVehicleGPSId());
						gpsUsageKM	= getIdeagamiRunKM(valueObject);
					break;
				case 4:
						gpsUsageKM	= getSMTCGpsRunKM(DateTimeUtility.getTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), vehicle, configuration);	
					break;
				case 5:
						valueObject	= new ValueObject();
						valueObject.put("fromDate", fromDate);
						valueObject.put("toDate", toDate);
						valueObject.put("vid", vehicle.getVid());
						valueObject.put("companyId", companyId);
						gpsUsageKM	= getITSGatewayRunKM(valueObject);	
					break;	
				case 6:
						valueObject	= new ValueObject();
						valueObject.put("fromDate", fromDate);
						valueObject.put("toDate", toDate);
						valueObject.put("vehicleRegistration", vehicle.getVehicle_registration());
						valueObject.put("companyId", companyId);
						gpsUsageKM	= getTrackNowRunKM(valueObject);
					break;	
				case 7:
						valueObject	= new ValueObject();
						valueObject.put("fromDate", fromDate);
						valueObject.put("toDate", toDate);
						valueObject.put("vehicleRegistration", vehicle.getVehicle_registration());
						valueObject.put("companyId", companyId);
						gpsUsageKM	= getFleetexRunKM(valueObject);
					break;
				case 8:
						valueObject	= new ValueObject();
						valueObject.put("fromDate", fromDate);
						valueObject.put("toDate", toDate);
						valueObject.put("companyId", companyId);
						valueObject.put("vid", vehicle.getVid());
						gpsUsageKM	=  getTataInBetweenRunKM(valueObject,configuration);
					break;
				case 9:
						valueObject	= new ValueObject();
						valueObject.put("fromDate", fromDate);
						valueObject.put("toDate", toDate);
						valueObject.put("vehicleChassisNo", vehicle.getVehicle_chasis());
						valueObject.put("companyId", companyId);
						valueObject.put("vid", vid);
						gpsUsageKM	= getEicherRunKM(valueObject);
					break;
				default:
					break;
				}
			}
			return gpsUsageKM;
		} catch (Exception e) {
			throw e;
		}finally {
			//valueOutObject	= null;
			//valueObject		= null;
			gpsUsageKM		= null;
			configuration	= null;
		}
	}
	
	public Double getTataInBetweenRunKM(ValueObject valueObject,HashMap<String, Object>    configuration) throws Exception {
		Double						gpsUsageKM			= 0.0;
		JSONObject					jsonObject 			= null;
		String 						firstDate			= null;
		String 						secondDate			= null;
		long 						vid 				= 0;
		
		try {
			firstDate		= valueObject.getString("fromDate");
			secondDate		= valueObject.getString("toDate");
			vid 			= valueObject.getLong("vid",0);
    		Unirest.setTimeouts(0, 0);
    		HttpResponse<String> response = Unirest.post((String) configuration.get("tataAPILink"))
    		  .header("Content-Type", "application/x-www-form-urlencoded")
    		  .field("firstDate", firstDate)
    		  .field("secondDate", secondDate)
    		  .field("vid", vid)
    		  .field("companyId", valueObject.getInt("companyId",0) )
    		  .asString();
    		if(response.getStatus() == 200){
    			jsonObject	= new JSONObject(response.getBody());
    			if(jsonObject.has("usageOdometer")) {
    				gpsUsageKM =Utility.round(jsonObject.getDouble("usageOdometer"), 2); 
    			}
    		}
    		 return gpsUsageKM;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	private Double getOmniTalkRunKM(HashMap<String,  Object> configuration, ValueObject	valueObject) throws Exception {
		try {
			return tripSheetService.getGPSUsageKM(configuration, valueObject);
		} catch (Exception e) {
			throw e;
		}
	}
	
	private Double getIntanglesRunKM(String vehicleNumber, String fromDate , String toDate) throws Exception{
		Double gpsRunKM		= 0.0;
		try {
			
			List<VehicleGPSData> dataList	=	getVehicleGPSDataList(vehicleNumber.replace("-", ""), fromDate, toDate);
			if(dataList != null && dataList.size() > 1) {
				Integer runKM	= dataList.get(dataList.size() - 1).getVehicleOdometer() - dataList.get(0).getVehicleOdometer();
				gpsRunKM	= Double.parseDouble(runKM+"");
			}
			
			return gpsRunKM;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private Double getIdeagamiRunKM(ValueObject	valueObject) throws Exception{
		Double 					gpsRunKM		= 0.0;
		List<VehicleGPSData>	vehicleGpsList	= null;
		Timestamp				fromDate		= null;
		Timestamp				toDate			= null;
		Integer					runKM			= 0;
		try {
			fromDate		= (Timestamp) valueObject.get("fromDate");
			toDate			= DateTimeUtility.getTimeStamp(valueObject.getString("toDate"), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			if(valueObject.getString("vehicleGPSId") != null)
			vehicleGpsList	=	vehicleGPSDataService.getVehicleGPSDataInDateRange(fromDate, toDate, valueObject.getString("vehicleGPSId"));
			
			if(vehicleGpsList != null && !vehicleGpsList.isEmpty()) {
				runKM	= Utility.calculateRunKM(vehicleGpsList);
				gpsRunKM	= Double.parseDouble(runKM+"");
			}
			
			return gpsRunKM;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleGPSData> getVehicleGPSDataList(String vid, String fromDate, String toDate) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			try {
				
					query = entityManager.createQuery("SELECT VGD.vehicleOdometer, VGD.vehicleNumber from VehicleGPSData VGD "
							+ " where VGD.vehicleNumber = '"+vid+"' AND VGD.dateTime >= '"+fromDate+"' AND VGD.dateTime <= '"+toDate+"' "
							+ " AND VGD.markForDelete = 0 order by VGD.dateTime asc ", Object[].class);

				
				List<Object[]> results = query.getResultList();

			List<VehicleGPSData> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleGPSData>();
				VehicleGPSData list = null;
				for (Object[] result : results) {
					list = new VehicleGPSData();

					list.setVehicleOdometer((Integer) result[0]);
					list.setVehicleNumber((String) result[1]);

					Dtos.add(list);
				}
			}
			return Dtos;
				// return query.getResultList();
			} catch (Exception e) {
				System.err.println("inside catch " + e);
				throw e;
			} finally {
				query = null;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private Double getSMTCGpsRunKM(Timestamp fromDate, Timestamp toDate, VehicleDto vehicle, HashMap<String, Object> configuration) throws Exception{
		Double gpsRunKM		= 0.0;
		JSONObject				jsonObject		= null;
		try {
			jsonObject	= vehicleGPSDataService.getVehicleGPSDataInDateRange(fromDate, toDate, vehicle, configuration);
			if(jsonObject != null) {
				 gpsRunKM = jsonObject.getDouble("length");
			}
			return gpsRunKM;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private Double getITSGatewayRunKM(ValueObject valueObject) throws Exception{
		Double 					gpsRunKM		= 0.0;
		ValueObject				object			= null;
		try {
			object		=	vehicleGPSDataService.getITSGatewayUsageKM(valueObject);
			
			if(object.getBoolean("gpsUsageKMFound", false)) {
				gpsRunKM = object.getDouble("gpsUsageKM");
			}
			
			return gpsRunKM;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private Double getTrackNowRunKM(ValueObject valueObject) throws Exception{
		Double 					gpsRunKM		= 0.0;
		ValueObject				object			= null;
		try {
			object		=	vehicleGPSDataService.getTrackNowRunKM(valueObject);
			gpsRunKM 	= Double.parseDouble(decimalFormat.format(object.getDouble("gpsUsageKM",0)));
			
			return gpsRunKM;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
//	private Double getTataRunKM(ValueObject valueObject) throws Exception{
//		Double 					gpsRunKM		= 0.0;
//		ValueObject				object			= null;
//		try {
//			object		=	vehicleGPSDataService.getTataRunKM(valueObject);
//			gpsRunKM 	= object.getDouble("gpsUsageKM",0);
//			
//			return gpsRunKM;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//	}
	
	private Double getFleetexRunKM(ValueObject valueObject) throws Exception{
		Double 					gpsRunKM		= 0.0;
		ValueObject				object			= null;
		try {
			object		=	vehicleGPSDataService.getFleetexRunKM(valueObject);
			gpsRunKM 	= Double.parseDouble(decimalFormat.format(object.getDouble("gpsUsageKM",0)));
			
			return gpsRunKM;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private Double getEicherRunKM(ValueObject valueObject) throws Exception{
		Double 					gpsRunKM		= 0.0;
		ValueObject				object			= null;
		try {
			object		=	vehicleGPSDataService.getEicherRunKM(valueObject);
			gpsRunKM 	= Double.parseDouble(decimalFormat.format(object.getDouble("gpsUsageKM",0)));
			
			return gpsRunKM;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
