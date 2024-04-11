package org.fleetopgroup.persistence.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VehicleGPSConfiguratinConstant;
import org.fleetopgroup.persistence.dao.VehicleExtraDetailsRepository;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleExtraDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleGpsDto;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleExtraDetails;
import org.fleetopgroup.persistence.model.VehicleGPSData;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleExtraDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDataService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@Service
public class VehicleGPSDataService implements IVehicleGPSDataService {

	@PersistenceContext public EntityManager entityManager;
	
	@Autowired	private ICompanyConfigurationService		companyConfigurationService;
	@Autowired  private	VehicleExtraDetailsRepository 		VehicleExtraDetailsRepository;
	@Autowired  private IVehicleExtraDetailsService			vehicleExtraDetailsService;
	@Autowired 	IVehicleService								vehicleService;
	
	SimpleDateFormat dateTimeFormat 	= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateTimeFormatter inputFormatter 	= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	DateTimeFormatter newDateFormatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	SimpleDateFormat  dateFormat		= new SimpleDateFormat("yyyyMMddHHmmss");
	SimpleDateFormat  date_Format 		= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	@Override
	public List<VehicleGPSData> getVehicleGPSDataInDateRange(Timestamp fromDate, Timestamp toDate, String gpsDeviceId) throws Exception {
		try {
			
			TypedQuery<VehicleGPSData> query = entityManager.createQuery(
					"SELECT T "
					+ " FROM VehicleGPSData AS T"
							+ " where T.gpsDeviceId = "+gpsDeviceId+" AND  T.dateTime between '"+fromDate+"' AND '"+toDate+"' ORDER BY T.vehicleGPSDataId ASC ", VehicleGPSData.class);
			
			return	query.getResultList();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public JSONObject getVehicleGPSDataInDateRange(Timestamp fromDate, Timestamp toDate, Vehicle vehicle, HashMap<String, Object> configuration)
			throws Exception {
		Long	trackerId	= (long) 0;	
		JSONObject	object  =	null;
		try {
				VehicleExtraDetailsDto validate 	=	vehicleExtraDetailsService.getVehicleExtraDetailsByVid(vehicle.getVid());
				String hash			= getHashByUserNamePassword(configuration, validate);
				if(hash != null) {
					trackerId			= getVehicleTrackerList(configuration, hash, vehicle.getVehicle_registration());
				}
				if(trackerId > 0) {
					object  = getVehicleGPSData(trackerId, fromDate, toDate, hash, configuration);
				}
				
				return object;
			} catch (Exception e) {
				throw e;
			}finally {
				object		= null;
				trackerId	= null;
			}
	}
	
	@Override
	public JSONObject getVehicleGPSDataInDateRange(Timestamp fromDate, Timestamp toDate, VehicleDto vehicle,
			HashMap<String, Object> configuration) throws Exception {
		Long				trackerId		= (long) 0;	
		JSONObject			object  		=	null;
		try {
				
			VehicleExtraDetailsDto validate 	=	vehicleExtraDetailsService.getVehicleExtraDetailsByVid(vehicle.getVid());			
			String hash			= getHashByUserNamePassword(configuration, validate);
			
				if(hash != null) {
					trackerId			= getVehicleTrackerList(configuration, hash, vehicle.getVehicle_registration());
					if(trackerId > 0) {
						object  = getVehicleGPSData(trackerId, fromDate, toDate, hash, configuration);
					}
				}
				
				return object;
			} catch (Exception e) {
				throw e;
			}finally {
				object		= null;
				trackerId	= null;
			}
	}
	
	private String getHashByUserNamePassword(HashMap<String, Object> configuration, VehicleExtraDetailsDto	extraDetails) throws Exception {
		HttpResponse<String> 		response 				= null;
		String	username 	= null;
		String	password	= null;
		String	hash		= null;
		try {
			if(extraDetails != null) {
				username 	= extraDetails.getUserName();
				password	= extraDetails.getPassword();
				response =		Unirest.get((String)configuration.get("smtcHashUrl")).queryString("login", username)
						.queryString("password", password).asString();
				JSONObject resObj	= new JSONObject(response.getBody());
				if(resObj.has("hash")) {
					hash = resObj.getString("hash");
				}
			}
			return hash;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			response 	= null;
			username 	= null;
			password	= null;
			hash		= null;
		}
		
	}
	
	private Long getVehicleTrackerList(HashMap<String, Object> configuration, String hash, String vehicle) throws Exception{
		HttpResponse<String> 		response 				= null;
		Long						trackerId				= (long) 0;
		try {
			response =		Unirest.get((String)configuration.get("smtcTrackerUrl"))
					.queryString("hash", hash).asString();
			
			JSONObject resObj		= new JSONObject(response.getBody());
			
			if(resObj.has("trackers")) {
				JSONArray	jsonArray	=	resObj.getJSONArray("trackers");
				
				if (jsonArray != null) { 
					for (int i=0;i<jsonArray.length();i++){ 
						JSONObject	object = jsonArray.getJSONObject(i);
						if(object.getString("label").replace("-", "").equalsIgnoreCase(vehicle.replace("-", ""))) {
							trackerId = object.getLong("id");
							break;
						}
					} 
				} 
			}
			return trackerId;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			response 	= null;
			trackerId	= null;
		}
	}
	
	public JSONObject getVehicleGPSData(Long trackerId, Timestamp fromDate, Timestamp toDate, String hash, HashMap<String, Object> configuration) throws Exception{
		HttpResponse<String> 		response 				= null;
		JSONArray					jsonArray				= null;
		JSONObject					jsonObject				= null;
		Double						usageKM					= 0.0;
		try {
			
			response =		Unirest.get((String)configuration.get("smtcDataUrl"))
					.queryString("tracker_id", trackerId)
					.queryString("from", DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS))
					.queryString("to", DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS))
					.queryString("split", true)
					.queryString("hash", hash).asString();
			
			JSONObject 	resObj		= new JSONObject(response.getBody());
			if(resObj.has("list")) {
				jsonArray	=	resObj.getJSONArray("list");
				if(jsonArray != null && jsonArray.length() > 0) {
					for (int i = 0; i < jsonArray.length(); i++) {
						jsonObject  = 	jsonArray.getJSONObject(i);
						if(jsonObject.has("length"))
							usageKM += jsonObject.getDouble("length");
					}
				}
			}
			if(jsonObject == null) {
				jsonObject = new JSONObject();
			}
			jsonObject.put("length", usageKM);
			return jsonObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			response 				= null;
			jsonArray				= null;
			jsonObject				= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject getITSGatewayUsageKM(ValueObject valueObject) throws Exception {
		HashMap<String, Object> 					gpsConfig							= null;
		String 										verifycall 							= null;
		String										apiUrl								= null;
		VehicleExtraDetails 						busDeatils							= null;
		double										gpsUsageKm							= 0.0;
		ValueObject 								gpsDetails							= null;
		LinkedHashMap<Object, Object> 				gpsValue							= null;
		ArrayList<LinkedHashMap<Object, Object>> 	gps									= null;
		try {
			gpsConfig  	 		= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			verifycall 	 		= (String) gpsConfig.get("itsGatewayVerifyCall");
			apiUrl 	 			= (String) gpsConfig.get("itsGatewayGpsUsageUrl");
			
			busDeatils 			= VehicleExtraDetailsRepository.getBusId(valueObject.getInt("vid"), valueObject.getInt("companyId"));
			
			if(busDeatils != null) {
				
	    		JSONObject json = new JSONObject();
	    		json.put("FromDate", (Object)dateTimeFormat.format((Date)dateTimeFormat.parse(valueObject.getString("fromDate"))));
	            json.put("ToDate", (Object)dateTimeFormat.format((Date)dateTimeFormat.parse(valueObject.getString("toDate"))));
	            json.put("VerifyCall", (Object)verifycall);
	            json.put("BusID", busDeatils.getBusId());
	            json.put("DeviceID", busDeatils.getDeviceId());
	    	
	            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post(apiUrl)
	            		.header("Content-Type", "application/json")
	            		.body(json).asString();
	            
	    		if(response.getStatus() == 200) {

	    			gpsDetails = JsonConvertor.toValueObjectFormSimpleJsonString((String)response.getBody());
					
					if(gpsDetails.get("data") != null) {
						gpsValue = (LinkedHashMap<Object, Object>) gpsDetails.get("data");
	                	
	                	if(!gpsValue.isEmpty() && gpsValue.get("Data") != null) {
	                		gps	= (ArrayList<LinkedHashMap<Object, Object>>) gpsValue.get("Data");
	                		
	                		for (Map<Object, Object> entry : gps) {
	                			
	                			if(entry.get("Total_KM") != null) {
	                				gpsUsageKm = (double) entry.get("Total_KM");
	                				valueObject.put("gpsUsageKM", gpsUsageKm);
	    	                		valueObject.put("gpsUsageKMFound", true);
	                			} else {
	                				valueObject.put("gpsUsageKMFound", false);
	                			}
	                			
	                		}
	                		
	                	} else {
	                		valueObject.put("noRecordFound", true);
	                	}
	                }
				
	    		} else {
					valueObject.put("noRecordFound", true);
				}
				
			} else {
				valueObject.put("busDeatilsNotFound", true);
			}
    		
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	 @Override
		public ValueObject getTrackNowRunKM(ValueObject valueObject) throws Exception {
				Timestamp					fromDate			= null;
				Timestamp					toDate				= null;
				Double						distance			= 0.0;
				Double						gpsUsageKM			= 0.0;
				String						veicleRegistration  = "";
				HashMap<String, Object> 	gpsConfig			= null;
				JSONObject					jsonObject 			= null;
				JSONObject					object				= null;
				try {
					JSONObject json = new JSONObject();
					
					gpsConfig  	 		= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.VEHICLE_GPS_CONFIG);
					fromDate 			= DateTimeUtility.getTimeStamp(valueObject.getString("fromDate"), "yyyy-MM-dd HH:mm:ss");
					toDate				= DateTimeUtility.getTimeStamp(valueObject.getString("toDate"), "yyyy-MM-dd HH:mm:ss");
					veicleRegistration 	= valueObject.getString("vehicleRegistration","");
					
		    		
		    		json.put("fromdate", fromDate+"");
		    		json.put("todate", toDate+"");
		    		Unirest.setTimeouts(0, 0);
		    		HttpResponse<String> response = Unirest.get(""+gpsConfig.get("trackNowUrl")+""+veicleRegistration+"/"+fromDate+"/"+toDate+"")
		    		  .header("Content-Type", "application/json")
		    		  .header("Authorization","Basic "+(String)gpsConfig.get(VehicleGPSConfiguratinConstant.TRACK_NOW_AUTHORIZATION))
		    		  .asString();
		    		
		    		if(response.getStatus() == 200) {
		    			jsonObject	= new JSONObject(response.getBody());
		    			if(jsonObject.has("data") && !jsonObject.getJSONArray("data").isNull(0)) {
	    					JSONArray	jSONArray	= jsonObject.getJSONArray("data");
	    					if (jSONArray != null) { 
	    						for (int i=0; i<jSONArray.length(); i++){ 
	    							object	= (JSONObject) jSONArray.get(i);
	    							distance = object.getDouble("distance")/1000;
	    							gpsUsageKM += distance;
	    						} 

	    					}
		    			}
		    		}
		    		
		    		valueObject.put("gpsUsageKM", gpsUsageKM);
		    		 return valueObject;
		    		 
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
	 
	 @Override
	public ValueObject getFleetexRunKM(ValueObject valueObject) throws Exception {
		 	String					fromDate			= null;
		 	String					toDate				= null;
			Double						gpsUsageKM			= 0.0;
			String						veicleRegistration  = "";
			HashMap<String, Object> 	gpsConfig			= null;
			JSONObject					jsonObject 			= null;
			try {
				
				gpsConfig  	 		= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.VEHICLE_GPS_CONFIG);
				fromDate 			=	URLEncoder.encode(valueObject.getString("fromDate").replace(".0", ""), "UTF-8");
				toDate				=	URLEncoder.encode(valueObject.getString("toDate").replace(".0", ""), "UTF-8");
				veicleRegistration 	= valueObject.getString("vehicleRegistration","").replace("-", "");
				
				
	    		Unirest.setTimeouts(0, 0);
	    		StringBuilder url =   new StringBuilder((String)gpsConfig.getOrDefault("fleetexURL",""));
	    		if(url.length() > 0) {
	    			url.append("from="+fromDate+"&to="+toDate+"&vehicleNumber="+veicleRegistration+"");
	    			HttpResponse<String> response = Unirest.get(url.toString())
	    		    		  .header("Authorization", "bearer "+gpsConfig.get("fleetexCookie")+"")
	    		    		  .asString();
	    		    		if(response.getStatus() == 200) {
	    		    			JSONArray jsonArray = new JSONArray(response.getBody());
	    		    			if(!jsonArray.isNull(0)) {
	    		    				jsonObject	=	jsonArray.getJSONObject(0);
	    		    				if(jsonObject.has("distance")) {
	    		    					gpsUsageKM=jsonObject.getDouble("distance");
	    		    				}
	    		    			}
	    		    		}
	    		}
	    		valueObject.put("gpsUsageKM", gpsUsageKM);
	    		 return valueObject;
	    		 
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	 
	 @Override
	 public ValueObject getEicherRunKM(ValueObject valueObject) throws Exception {
			Double						gpsUsageKM				= 0.0;
			String						veicleChassisNo  		= "";
			HashMap<String, Object> 	gpsConfig				= null;
			JSONObject					jsonObject 				= null;
			String						XIBMClientI     		= null;
			String						XIBMClientSecret		= null;
			JSONObject 					JSON 					= null;
			VehicleGpsDto 				gpsDto 					= null;
			List<VehicleGpsDto> 		gpsDtoList				= null;
			String 						parsedDate				= null;		
			
			try {
				gpsConfig  	 		= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.VEHICLE_GPS_CONFIG);
				veicleChassisNo 	= valueObject.getString("vehicleChassisNo","");
				String[] chessisNo  = new String[1];
				chessisNo[0] = veicleChassisNo;
				
				JSONObject json = new JSONObject();
	    		json.put("startDateTime",Long.parseLong(LocalDateTime.parse(valueObject.getString("fromDate"), inputFormatter).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	    		json.put("chassisno", chessisNo);
	    		json.put("endDateTime", Long.parseLong(LocalDateTime.parse(valueObject.getString("toDate"), inputFormatter).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	            json.put("latestOnly", false);
	            
	    		Unirest.setTimeouts(0, 0);
	    		StringBuilder url =   new StringBuilder((String)gpsConfig.getOrDefault("eicherURL",""));
	    		XIBMClientI       =  (String) gpsConfig.getOrDefault("X-IBM-Client-Id","");
	    		XIBMClientSecret  =  (String) gpsConfig.getOrDefault("X-IBM-Client-Secret","");
	    		
	    		if(url.length() > 0) {
	    			
	    			Unirest.setTimeouts(0, 0);
	    			HttpResponse<String> response = Unirest.post(url.toString())
	    			  .header("X-IBM-Client-Id", XIBMClientI)
	    			  .header("X-IBM-Client-Secret", XIBMClientSecret)
	    			  .header("Content-Type", "application/json")
	    			  .body(json).asString();
	    				
    		    		if(response.getStatus() == 200) {
    		    			JSONArray jsonArray = new JSONArray(response.getBody());
    		    			if(!jsonArray.isNull(0)) {
    		    				jsonObject	=	jsonArray.getJSONObject(0);
    		    				
    		    				if(jsonObject.has("positiondata")) {
    		    					gpsDtoList = new ArrayList<>();
	    		    				JSONArray positionDataArray = jsonObject.getJSONArray("positiondata");

	    		    				for(Object jsonObj : positionDataArray) {
	    		    					
	    		    					JSON 		    = (JSONObject) jsonObj;
	    		    					gpsDto 			= new VehicleGpsDto();
	    		    					
	    		    					gpsDto.setVid(valueObject.getInt("vid"));
	    		    					gpsDto.setCompany_Id(valueObject.getInt("companyId"));
	    		    					gpsDto.setVehicleChassisNo(jsonObject.getString("chassisNo"));
	    		    					try {
    		    						    parsedDate = date_Format.format(dateFormat.parse(String.valueOf(JSON.getLong("positionDateTime"))));
    		    						    gpsDto.setPosition_DateTime(date_Format.parse(parsedDate));
		    		    					gpsDto.setPositionDateTime(JSON.getLong("positionDateTime"));
		    		    					gpsDto.setLattitude(JSON.getDouble("lattitude"));
		    		    					gpsDto.setLongitude(JSON.getDouble("longitude"));
		    		    					gpsDto.setOdometer(JSON.getDouble("odometer"));
		    		    					gpsDto.setVehicleRegistrationNumber(JSON.getString("vehicleRegistrationNumber"));
		    		    					gpsDto.setSpeed(JSON.getDouble("speed"));
		    		    					gpsDto.setTotalEngineHours(JSON.get("totalEngineHours"));
		    		    					gpsDto.setTotalFuelConsumption(JSON.get("totalFuelConsumption"));
		    		    					gpsDto.setIdleTime(JSON.get("idleTime"));
		    		    					gpsDto.setFuelLevel(JSON.get("fuelLevel"));
		    		    					gpsDto.setAltitude(JSON.getInt("altitude"));
		    		    					gpsDto.setHeading(JSON.getInt("heading"));
		    		    					gpsDto.setIgnitionStatus(JSON.getInt("ignitionStatus"));
		    		    					
		    		    					gpsDtoList.add(gpsDto);
	    		    					} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
	    		    				};
	    		    			     Collections.sort(gpsDtoList, Comparator.comparing(VehicleGpsDto::getPosition_DateTime));
	    		    				 gpsUsageKM= gpsDtoList.get(gpsDtoList.size()-1).getOdometer()  - gpsDtoList.get(0).getOdometer();
    		    				}
    		    			}
	    		    	}
	    		}
	    		valueObject.put("gpsUsageKM", gpsUsageKM);
	    		 return valueObject;
	    		 
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
}
