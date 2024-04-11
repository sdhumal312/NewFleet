package com.fleetop.gpsservice.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fleetop.gpsservice.constant.GpsApiConstant;
import com.fleetop.gpsservice.constant.PropertyFileConstants;
import com.fleetop.gpsservice.model.Vehicle;
import com.fleetop.gpsservice.model.VehicleGpsData;
import com.fleetop.gpsservice.repository.VehicleGpsDataRepository;
import com.fleetop.gpsservice.repository.VehicleRepository;
import com.fleetop.gpsservice.serviceImpl.ICompanyConfigurationService;
import com.fleetop.gpsservice.util.ValueObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;


@Configuration
@EnableScheduling
@Service
public class GpsApiService {

	@Autowired 					VehicleRepository 				vehicleRepo;
	@Autowired 					VehicleGpsDataRepository     	vehicleGpsDataRepository;
	@Autowired					ICompanyConfigurationService	companyConfigurationService;
	@PersistenceContext 		EntityManager 					entityManager;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	@Scheduled(fixedRate=300000)
	public void getTatagpsDetails() {
		try {
			List<Vehicle>vehicleList =vehicleRepo.getVehicleListByGpsVendor(GpsApiConstant.TATAAPI,GpsApiConstant.COMPANY_CODE_STA);
			Map<String, Object> configuration =companyConfigurationService.getCompanyConfiguration(GpsApiConstant.COMPANY_CODE_STA, PropertyFileConstants.VEHICLE_GPS_CONFIG);
			if(vehicleList != null && !vehicleList.isEmpty() && configuration != null) {
				for(Vehicle vehicle:vehicleList) {
					int vid = vehicle.getVid();
					String vehicleChasis =vehicle.getVehicle_chasis();
					getTataRunKM(vid,vehicleChasis,GpsApiConstant.COMPANY_CODE_STA,configuration);
					Thread.sleep(5000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getTataRunKM(int vid,String chasis,int companyId,Map<String, Object> configuration) throws Exception {
		Double						gpsOdometerKM			= 0.0;
		JSONObject				jsonObject	= null;
		try {
			String tocken=getTataTocken(configuration);
			HttpResponse<String> response = Unirest.get(configuration.getOrDefault("tataUnirestLink", " ")+chasis+"/snapshot")
					.header("Authorization", "Bearer "+tocken+"").asString();
			
			if(response.getStatus() == 200) {
				jsonObject	= new JSONObject(response.getBody());
				if(jsonObject.has("eventDateTime")) {
					VehicleGpsData dataList = getLeatesGpsEntryByVid(vid, companyId);
					if(dataList == null || (dataList.getGpsOdometer() != null && jsonObject.getDouble("odometer") != dataList.getGpsOdometer())) {
						VehicleGpsData gpsData= new VehicleGpsData(); 
						String eventDateTime= jsonObject.getString("eventDateTime");
						Date edateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(eventDateTime);
						gpsData.setEventDateTime(edateTime);
						gpsData.setCompanyId(companyId);
						gpsOdometerKM =jsonObject.getDouble("odometer"); 
						gpsData.setGpsOdometer(gpsOdometerKM);
						gpsData.setVid(vid);
						gpsData.setCreated(new Date());
						vehicleGpsDataRepository.save(gpsData);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public String getTataTocken(Map<String, Object> configuration) {
		JSONObject					jsonObject 			= null;
		String 						token 				= "";
		try {
			String  	tataGrantType  		= "client_credentials";
			HttpResponse<String> response = Unirest.post((String) configuration.getOrDefault("tataTokenURL", ""))
					.header("Authorization", "Basic c2hyaW5hdGhjYjpzaHJpbmF0aGNiQDk1Nw==")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("Cookie",(String) configuration.get("tataCookies")) 
					.field("client_id",  configuration.get("tataClientId"))
					.field("client_secret",configuration.get("tataClientSecret"))
					.field("grant_type",tataGrantType)
					.asString();
			if(response.getStatus() == 200) {
				jsonObject	= new JSONObject(response.getBody());
				if(jsonObject.has("access_token")) {
					token =jsonObject.getString("access_token");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	public Map<Object,Object> getInbetweenGpsUsage(Map<Object,Object> hasHObject){
		String firstDateStr 	= (String) hasHObject.getOrDefault("firstDate",null);
		String secondDateStr 	= (String) hasHObject.getOrDefault("secondDate",null);
		int vid 		= Integer.parseInt((String) hasHObject.getOrDefault("vid", "0"));
		int companyId 	= Integer.parseInt((String)hasHObject.getOrDefault("companyId", "0"));
		Map<Object,Object> outObject = new HashMap<>();
		try {
			if(firstDateStr != null &&secondDateStr != null && vid>0 && companyId >0 ) {
				List<VehicleGpsData> gpsDataList=getInBetweenGpsData(vid, companyId, firstDateStr, secondDateStr);
				if(gpsDataList != null && !gpsDataList.isEmpty() && gpsDataList.size()>1) {	
					VehicleGpsData fisrstEntry 	= gpsDataList.get(0);
					VehicleGpsData lastEntry	= gpsDataList.get(gpsDataList.size()-1);
					double startOdo				= fisrstEntry.getGpsOdometer(),endOdo=lastEntry.getGpsOdometer();
					if(startOdo<endOdo) {
						outObject.put("startOdo", startOdo);
						outObject.put("endOdo", endOdo);
						outObject.put("usageOdometer", endOdo-startOdo);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outObject;
	}

	public Map<Object,Object> getOpeningOrClosingGpsUsage(Map<Object,Object> hasHObject){
		String dateStr 	= (String) hasHObject.getOrDefault("date",null);
		boolean fromOpening 	= Boolean.parseBoolean((String) hasHObject.getOrDefault("fromOpening","false")) ;
		int vid 				= Integer.parseInt((String) hasHObject.getOrDefault("vid","0"));
		int companyId 			= Integer.parseInt((String)hasHObject.getOrDefault("companyId","0"));
		Map<Object,Object> outObject 	= new HashMap<>();
		double gpsOdometer				= 0.0;
		VehicleGpsData vehicleGpsData 	= null;
		try {
			if(dateStr != null && vid >0 && companyId > 0) {
				if(fromOpening) {
					vehicleGpsData =getOpeningGpsData(vid, companyId, dateStr);
				}else{
					vehicleGpsData =getClosingGpsData(vid, companyId, dateStr);
				}
				if(vehicleGpsData!= null) {
					gpsOdometer=vehicleGpsData.getGpsOdometer();
					outObject.put("eventDate",vehicleGpsData.getEventDateTime());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		outObject.put("gpsOdometer",gpsOdometer);
		return outObject;
	}

	public List<VehicleGpsData> getInBetweenGpsData(int vid,int companyId,String startDate,String endDate) throws Exception{
		TypedQuery<VehicleGpsData> query =null;
		List<VehicleGpsData> list = null;
		try {
		query=entityManager.createQuery(" FROM VehicleGpsData "
				+ "WHERE vid ="+vid+" AND company_Id ="+companyId+" AND mark_For_Delete = 0 "
				+ "AND event_date_time >= '"+startDate+"' AND event_date_time <= '"+endDate+"' "
				+ "order by event_date_time ASC ", VehicleGpsData.class);
		 list=query.getResultList();
		} catch (NoResultException e) {
			// TODO: handle exception
		}
		return list;
	}

	public VehicleGpsData getOpeningGpsData(int vid,int companyId,String startDate) throws Exception{
		Query query =null;
		VehicleGpsData vehicleGpsData = null;

		query=entityManager.createQuery(" FROM VehicleGpsData "
				+ "WHERE vid ="+vid+" AND company_Id ="+companyId+" AND mark_For_Delete = 0 "
				+ "AND event_date_time >= '"+startDate+"' "
				+ "order by event_date_time ASC ", VehicleGpsData.class);
		query.setMaxResults(1);
		try {
			vehicleGpsData=(VehicleGpsData) query.getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
		}
		return vehicleGpsData ;
	}

	public VehicleGpsData getClosingGpsData(int vid,int companyId,String endDate) throws Exception{
		Query query =null;
		VehicleGpsData vehicleGpsData = null;
		query=entityManager.createQuery(" FROM VehicleGpsData "
				+ "WHERE vid ="+vid+" AND company_Id ="+companyId+" AND mark_For_Delete = 0 "
				+ "AND event_date_time <= '"+endDate+"' "
				+ "order by event_date_time DESC ", VehicleGpsData.class);

		query.setMaxResults(1);
		try {
			vehicleGpsData=(VehicleGpsData) query.getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
		}
		return vehicleGpsData ;
	}
	public VehicleGpsData getLeatesGpsEntryByVid(int vid,int companyId) throws Exception{
		Query query =null;
		VehicleGpsData vehicleGpsData = null;
		query=entityManager.createQuery(" FROM VehicleGpsData "
				+ "WHERE vid ="+vid+" AND company_Id ="+companyId+" AND mark_For_Delete = 0 "
				+ " order by created desc", VehicleGpsData.class);
		query.setMaxResults(1);
		try {
			vehicleGpsData=(VehicleGpsData) query.getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
		}
		return vehicleGpsData ;
	}

	public void saveVehicleGPSDataIntangles(ValueObject valueObject) {
		try {
			VehicleGpsData	vehicleGPSData	= new VehicleGpsData();
			vehicleGPSData.setGpsOdometer(valueObject.getDouble("Odometer", 0));
			vehicleGPSData.setVehicleNumber(valueObject.getString("vehicle_id"));
			vehicleGPSData.setRfid(valueObject.getString("refid"));
			vehicleGPSData.setEventDateTime(new Date(valueObject.getLong("time")));
			vehicleGPSData.setCompanyId(GpsApiConstant.COMPANY_CODE_STA);
			vehicleGPSData.setCreated(new Date());
			vehicleGpsDataRepository.save(vehicleGPSData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
