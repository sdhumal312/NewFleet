package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.report.dao.IFuelReportDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FuelReportDaoImpl implements IFuelReportDao {
	
	SimpleDateFormat simpleFormat = new SimpleDateFormat("dd-MM-yyyy");
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	
	
	@PersistenceContext	EntityManager entityManager;
	private static final int PAGE_SIZE 					= 10;
	@Transactional
	public List<TripDailySheetDto> getFuelTripDailyList(String Query, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT t.TRIPDAILYID, f.fuel_liters, f.fuel_amount, f.fuel_kml "
						+ " from Fuel as f "
						+ " LEFT join TripDailySheet t on  t.TRIPDAILYID = f.fuel_TripsheetID  AND f.markForDelete = 0  "
						+ " where t.COMPANY_ID = "+companyId+" "+ Query + " AND t.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+""
						+ " AND t.markForDelete = 0 ORDER BY t.TRIP_OPEN_DATE ASC",
							Object[].class);
			List<Object[]> resultsThread = queryt.getResultList();
			
			List<TripDailySheetDto> list = null;
			if (resultsThread != null && !resultsThread.isEmpty()) {
				list = new ArrayList<TripDailySheetDto>();
				TripDailySheetDto listThread = null;
				for (Object[] result : resultsThread) {
					listThread = new TripDailySheetDto();
					
					listThread.setTRIPDAILYID((long) result[0]);
					if(result[1] != null) {
						listThread.setTRIP_DIESEL((double) result[1]);
					}
					if(result[2] != null) {
						listThread.setFuel_amount((double) result[2]);
					}
					if(result[3] != null) {
						listThread.setTRIP_DIESELKMPL((double) result[3]);
					}
					list.add(listThread);
				}
			}
			return list;
		} catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		} finally {
		}
	}
	
	
	@Transactional
	public List<TripDailySheetDto> getFuelEfficiencyDtosReport(String Query, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT t.TRIPDAILYID, t.TRIP_ROUTE_ID, t.TRIP_DRIVER_ID, t.TRIP_OPEN_KM, t.TRIP_CLOSE_KM, t.VEHICLEID,"
						+ " t.TRIPDAILYNUMBER, t.TRIP_USAGE_KM, tr.routeName, tr.routeNo, d.driver_firstname, "
						+ " v.vehicle_registration, t.TRIP_OPEN_DATE "
						+ " from TripDailySheet as t "
						+ " inner join TripRoute tr on tr.routeID = t.TRIP_ROUTE_ID "
						+ " inner join Driver d on d.driver_id = t.TRIP_DRIVER_ID "
						+ " inner join Vehicle v on v.vid = t.VEHICLEID "
						+ " where t.COMPANY_ID = "+companyId+" "+ Query + " AND t.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+""
						+ " AND t.markForDelete = 0 ORDER BY t.TRIP_OPEN_DATE ASC",
							Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<TripDailySheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripDailySheetDto>();
				TripDailySheetDto list = null;
				for (Object[] result : results) {
					list = new TripDailySheetDto();
					
					list.setTRIPDAILYID((long) result[0]);
					list.setTRIP_ROUTE_ID((int) result[1]);
					list.setTRIP_DRIVER_ID((int) result[2]);
					list.setTRIP_OPEN_KM((int) result[3]);
					if(result[4] != null) {
					list.setTRIP_CLOSE_KM((int) result[4]);
					}
					list.setVEHICLEID((int) result[5]);
					list.setTRIPDAILYNUMBER((long) result[6]);
					if(result[7] != null) {
					list.setTRIP_USAGE_KM((int) result[7]);
					}
					list.setTRIP_ROUTE_NAME((String) result[8]);
					list.setRouteNo((String) result[9]);
					list.setTRIP_DRIVER_NAME((String) result[10]);
					list.setVEHICLE_REGISTRATION((String) result[11]);
					list.setTRIP_OPEN_DATE_D((Date) result[12]);
					list.setTRIP_OPEN_DATE(simpleFormat.format(list.getTRIP_OPEN_DATE_D()));
					list.setMultipleTrip(false);

					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		} finally {
		}
	}
	
	public void getFinalMapOfFuelEfficiencyDataReport(List<TripDailySheetDto> fuelList, 
			HashMap<Long, TripDailySheetDto> finalFuelMap) throws Exception {
		TripDailySheetDto					tripDailySheetDto	= null;
		try {
			//finalMapOfFuel	= new HashMap<Long, TripDailySheetDto>();
			for(int i = 0; i < fuelList.size(); i++) {
				
				if(finalFuelMap.containsKey(fuelList.get(i).getTRIPDAILYID())) {
					tripDailySheetDto	= finalFuelMap.get(fuelList.get(i).getTRIPDAILYID());
					
					
					tripDailySheetDto.setTRIP_DIESEL(fuelList.get(i).getTRIP_DIESEL());
					tripDailySheetDto.setFuel_amount(fuelList.get(i).getFuel_amount());
					tripDailySheetDto.setTRIP_DIESELKMPL(fuelList.get(i).getTRIP_DIESELKMPL());
					
				} else {
					finalFuelMap.put(fuelList.get(i).getTRIPDAILYID(), fuelList.get(i));
						
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Transactional
	public List<FuelDto> getFuelEntryDetails_Report(String query,Integer companyId) throws Exception {
		FuelDto 					fuelDto			    = null;	
		TypedQuery<Object[]> 		typedQuery 			= null;
		List<Object[]> 				results				= null;
		try {
			
			typedQuery = entityManager.createQuery(
				" SELECT F.fuel_id, F.vid, F.fuel_Number, F.fuel_date, F.fuel_kml, F.fuel_usage, F.fuel_amount,"
						+ " F.fuel_liters, F.fuel_tank_partial, F.fuel_meter_old, F.fuel_meter, "
						+ " V.vehicle_registration, D.driver_id, D.driver_firstname, VD.vendorId, VD.vendorName, F.secDriverID, D1.driver_firstname , "
						+ " F.cleanerID, D2.driver_firstname,T.routeName, F.created, TS.tripSheetNumber, TS.tripSheetID, V.vehicle_ExpectedMileage, "
						+ " V.vehicle_ExpectedMileage_to, F.gpsUsageKM,D.driver_Lastname,D.driver_fathername "
						+ " From Fuel as F "
						+ " INNER JOIN Vehicle as V ON V.vid = F.vid "
						+ " LEFT JOIN Vendor as VD ON VD.vendorId = F.vendor_id "
						+ " LEFT JOIN Driver as D ON D.driver_id = F.driver_id "
						+ " LEFT JOIN Driver as D1 ON D1.driver_id = F.secDriverID "
						+ " LEFT JOIN Driver as D2 ON D2.driver_id = F.cleanerID "
						+ " LEFT JOIN TripRoute as T ON T.routeID = F.routeID "
						+ " LEFT JOIN TripSheet as TS ON TS.tripSheetID = F.fuel_TripsheetID "
						+ " WHERE F.companyId = "+companyId+" AND  F.markForDelete = 0 "+ query + " "
						+ " ORDER BY F.fuel_id DESC ", Object[].class);
			
		results = typedQuery.getResultList();
		List<FuelDto> dtoList = null;
		if (results != null && !results.isEmpty()) {
			dtoList = new ArrayList<FuelDto>();
			
			
			
			for (Object[] result : results) {
				fuelDto = new FuelDto();
				
				if(result[0] != null) 							
				fuelDto.setFuel_id((Long) result[0]);								
				fuelDto.setVid((Integer) result[1]);								
				fuelDto.setFuelNumber("FT-"+(Long) result[2]);
				Timestamp td =(Timestamp)result[3];
				fuelDto.setFuel_date(DateTimeUtility.getDateFromTimeStamp(td,DateTimeUtility.DD_MM_YYYY));
				if(result[4] != null)
				fuelDto.setFuel_kml(Double.parseDouble(toFixedTwo.format(result[4])));
				fuelDto.setFuel_usage((Integer) result[5]);
				if(result[6] != null)
				fuelDto.setFuel_amount(Double.parseDouble(toFixedTwo.format(result[6])));
				fuelDto.setFuel_liters((Double) result[7]);
				fuelDto.setFuel_tank_partial((Integer) result[8]);
				fuelDto.setFuel_meter_old((Integer) result[9]);
				fuelDto.setFuel_meter((Integer)result[10]);
				fuelDto.setVehicle_registration((String) result[11]);
				if(result[12] != null) {
				fuelDto.setDriver_id((Integer) result[12]);
				}
				if(result[13] != null)
				fuelDto.setDriver_name((String) result[13]);
				else {
					fuelDto.setDriver_name("-");
				}
				fuelDto.setVendor_id((Integer) result[14]);
				
				fuelDto.setVendor_name((String) result[15]);
				
				if(result[16] != null) {
				fuelDto.setSecDriverID((Integer) result[16]);
				}
				if(result[17] != null) {
					fuelDto.setFuelSecDriverName((String) result[17]);
				}else {
					fuelDto.setFuelSecDriverName("-");
				}
				if(result[18] != null) {
				fuelDto.setCleanerID((Integer) result[18]);
				}
				if(result[19] != null) {
					fuelDto.setFuelCleanerName((String) result[19]);
				}else {
					fuelDto.setFuelCleanerName("-");
				}
				if(result[20] != null) {
					fuelDto.setFuelRouteName((String) result[20]);
				}else {
					fuelDto.setFuelRouteName("-");
				}
				fuelDto.setCreatedOn((Date) result[21]);
				if(fuelDto.getCreatedOn() != null)
					fuelDto.setCreated(simpleFormat.format(fuelDto.getCreatedOn()));
				
				if(fuelDto.getVendor_name() == null) {
					fuelDto.setVendor_name("--");
				}
				
				if(result[22] != null) {
					fuelDto.setFuel_TripsheetNumber((long) result[22]);	
					fuelDto.setTripSheetNumber("TS-"+fuelDto.getFuel_TripsheetNumber());	
				} else {
					fuelDto.setTripSheetNumber("-");
				}
				
				if(result[23] != null)
					fuelDto.setFuel_TripsheetID((long) result[23]);
				if(result[24] != null)
					fuelDto.setVehicle_ExpectedMileage((Double) result[24]);
				if(result[25] != null)
					fuelDto.setVehicle_ExpectedMileage_to((Double) result[25]);
				if(result[26] != null)
					fuelDto.setGpsUsageKM((Double) result[26]);
				if(result[27] != null && !((String)result[27]).trim().equals("")) {
					fuelDto.setDriver_name(fuelDto.getDriver_name() +" "+result[27]);
				}
				if(result[28] != null && !((String)result[28]).trim().equals("")) {
					fuelDto.setDriver_name(fuelDto.getDriver_name() +" - "+result[28]);
				}
				
				Double expectedKM	= 0.0;
				Double expectedMileage = fuelDto.getVehicle_ExpectedMileage_to();
				Integer usageKM	= 0;
				Double	expectedKMCost	= 0.0;
				Double	actualKMCost	= 0.0;
				Double	changeKMCost	= 0.0;
				Double	changePrice		= 0.0;
				if(expectedMileage != null && fuelDto.getFuel_kml() != null && fuelDto.getFuel_usage() > 0) {
					expectedKM  	= fuelDto.getFuel_liters() * expectedMileage;
					usageKM 		= fuelDto.getFuel_usage();
					expectedKMCost	= fuelDto.getFuel_amount() / expectedKM;
					actualKMCost	= fuelDto.getFuel_amount() / usageKM;
					
					changeKMCost	= expectedKMCost - actualKMCost;
					changePrice		= changeKMCost * usageKM;
				}
				fuelDto.setFuelPriceDiff(changePrice);
				
				
				if(fuelDto.getGpsUsageKM() != null && fuelDto.getGpsUsageKM() > 0) {
					fuelDto.setGpKMPL(fuelDto.getGpsUsageKM()/ fuelDto.getFuel_liters());
				}
				
				dtoList.add(fuelDto);
			}
		} 
		return dtoList;
		
		}catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		} finally {
		}
	}
	
	
	@Transactional
	public List<FuelDto> getUserWiseFuelEntryDetails_Report(String query,Integer companyId, String vehicleWiseGrpData) throws Exception {
		FuelDto 					fuelDto			    = null;	
		TypedQuery<Object[]> 		typedQuery 			= null;
		List<Object[]> 				results				= null;
		try {
			
			typedQuery = entityManager.createQuery(
				" SELECT F.fuel_id, F.vid, F.fuel_Number, F.fuel_date, F.fuel_kml, F.fuel_usage, F.fuel_amount,"
						+ " F.fuel_liters, F.fuel_meter_old, F.fuel_meter, "
						+ " V.vehicle_registration, VD.vendorId, VD.vendorName, F.fuel_cost, VG.vGroup "
						+ " From Fuel as F "
						+ " INNER JOIN Vehicle as V ON V.vid = F.vid "
						+ " INNER JOIN UserProfile U ON U.user_id = F.createdById "
						+ " LEFT JOIN VehicleGroup as VG ON VG.gid = V.vehicleGroupId "
						+ " LEFT JOIN Vendor as VD ON VD.vendorId = F.vendor_id "
						+   vehicleWiseGrpData 
						+ " WHERE "+ query +" AND F.companyId =" + companyId + " AND F.markForDelete = 0 ORDER BY F.fuel_date " ,
						Object[].class);
		
			results = typedQuery.getResultList();
		List<FuelDto> dtoList = null;
		if (results != null && !results.isEmpty()) {
			
			dtoList = new ArrayList<FuelDto>();
			for (Object[] result : results) {
				fuelDto = new FuelDto();
				if(result != null) {
					fuelDto.setFuel_id((Long)result[0]);
					fuelDto.setVid((Integer)result[1]);
					fuelDto.setFuelNumber("FT-"+(Long) result[2]);
					Timestamp td =(Timestamp)result[3];
					fuelDto.setFuel_date(DateTimeUtility.getDateFromTimeStamp(td,DateTimeUtility.DD_MM_YYYY));
					if(result[4] != null) {
					fuelDto.setFuel_kml((Double)result[4]);
					}
					fuelDto.setFuel_usage((Integer) result[5]);
					fuelDto.setFuel_amount((Double) result[6]);
					fuelDto.setFuel_liters((Double) result[7]);
					fuelDto.setFuel_meter_old((Integer) result[8]);
					fuelDto.setFuel_meter((Integer)result[9]);
					fuelDto.setVehicle_registration((String) result[10]);
					if(result[11]!= null) {
					fuelDto.setVendor_id((Integer) result[11]);
					}
					if (result[12] != null) {
						fuelDto.setVendor_name((String) result[12]);
					}else {
						fuelDto.setVendor_name("-");
					}
					if(result[13] != null) {
					fuelDto.setFuelCost(toFixedTwo.format((Double) result[13])+"/Km");
					}if(result[14] != null) {
					fuelDto.setVehicle_group((String)result[14]);
					}
					dtoList.add(fuelDto);
				}
			}
		} 
		return dtoList;
		}catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		} finally {
		}
	}
	
	
	@Override
	@Transactional
	public List<FuelDto> getFuelConsumptionList(ValueObject object) throws Exception {
		FuelDto 					fuelDto			    = null;	
		TypedQuery<Object[]> 		typedQuery 			= null;
		List<Object[]> 				results				= null;
		try {
			typedQuery = entityManager.createQuery(
				" SELECT F.fuel_id, F.fuel_Number, F.created, F.fuel_date, F.vid, V.vehicle_registration, "
					+ " F.fuel_liters, F.fuel_price, F.fuel_amount, F.fuel_kml,F.driver_id "
					+ " From Fuel as F "
					+ " "+object.getString("innerJoin","")+" "
					+ " INNER JOIN Vehicle as V ON V.vid = F.vid "
					+ " WHERE F.companyId = "+object.getInt("companyId")+" AND  F.markForDelete = 0 "+ object.getString("queryStr") + " "
					+ " ORDER BY F.fuel_date DESC ", Object[].class);
		results = typedQuery.getResultList();
		List<FuelDto> dtoList = null;
		if (results != null && !results.isEmpty()) {
			dtoList = new ArrayList<>();
			
			for (Object[] result : results) {
				fuelDto = new FuelDto();
				
				fuelDto.setFuel_id((Long) result[0]);								
				fuelDto.setFuelNumber("FT-"+result[1]);
				fuelDto.setCreated(DateTimeUtility.getDateFromTimeStamp((Timestamp)result[2], DateTimeUtility.DD_MM_YYYY));
				fuelDto.setFuel_date(DateTimeUtility.getDateFromTimeStamp((Timestamp)result[3], DateTimeUtility.DD_MM_YYYY));
				fuelDto.setVid((Integer) result[4]);								
				fuelDto.setVehicle_registration((String) result[5]);
				fuelDto.setFuel_liters((Double) result[6]);
				fuelDto.setFuel_price((Double) result[7]);
				fuelDto.setFuel_amount((Double)result[8]);
				fuelDto.setFuel_kml((Double)result[9]);
				fuelDto.setDriver_id((Integer) result[10]);
				dtoList.add(fuelDto);
			}
		} 
		return dtoList;
		
		}catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		} finally {
		}
	}
	
	
	
}	
