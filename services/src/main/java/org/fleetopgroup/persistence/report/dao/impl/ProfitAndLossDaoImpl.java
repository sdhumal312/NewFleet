package org.fleetopgroup.persistence.report.dao.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.report.dao.ProfitAndLossDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProfitAndLossDaoImpl implements ProfitAndLossDao {
	
	@PersistenceContext	EntityManager entityManager;
	@Autowired	private ICompanyConfigurationService	companyConfigurationService;
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	
	@Override
	public List<TripSheetDto> getVehicleUsageTripSheet(Integer vid, String fromDate, String toDate,
			CustomUserDetails userDetails) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount , D1.driver_firstname, D2.driver_firstname,"
								+ " D3.driver_firstname, U.email, B.branch_name,T.dispatchedByTime, T.routeName, TE.expenseAmount, T.gpsTripUsageKM "
								+ " FROM TripSheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
								+ " LEFT JOIN User U ON U.id = T.dispatchedById"
								+ " LEFT JOIN Branch AS B ON B.branch_id = T.dispatchedLocationId "
								+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
								+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
								+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
								+ " LEFT JOIN TripSheetExpense TE ON TE.tripsheet.tripSheetID = T.tripSheetID AND (TE.fuel_id IS NULL OR TE.fuel_id = 0) AND TE.markForDelete = 0 "
								+ " where  T.companyId = "+ userDetails.getCompany_id() + " AND T.vid = "+vid+" AND T.closetripDate between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.tripStutesId IN (3, 4) AND T.markForDelete = 0 "
								+ " ORDER BY T.tripSheetID ASC",
						Object[].class);

			} else {
				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount , D1.driver_firstname, D2.driver_firstname,"
								+ " D3.driver_firstname, U.email, B.branch_name, T.dispatchedByTime, T.routeName , TE.expenseAmount, T.gpsTripUsageKM  "
								+ " FROM TripSheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
								+ " LEFT JOIN User U ON U.id = T.dispatchedById"
								+ " LEFT JOIN Branch AS B ON B.branch_id = T.dispatchedLocationId "
								+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
								+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
								+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+ userDetails.getId() + " "
								+ " LEFT JOIN TripSheetExpense TE ON TE.tripsheet.tripSheetID = T.tripSheetID AND (TE.fuel_id IS NULL OR TE.fuel_id = 0) AND TE.markForDelete = 0 "
								+ " where  T.companyId = "+ userDetails.getCompany_id() + " AND T.vid = "+vid+" AND T.closetripDate between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.tripStutesId IN (3, 4) AND T.markForDelete = 0 "
								+ " ORDER BY T.tripSheetID ASC",
						Object[].class);
			}
			
			List<Object[]> results = query.getResultList();

			List<TripSheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetDto>();
				TripSheetDto select = null;
				for (Object[] vehicle : results) {

					select = new TripSheetDto();
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setVehicle_registration((String) vehicle[3]);
					select.setVehicle_Group((String) vehicle[4]);
					select.setVehicleGroupId((long) vehicle[5]);
					select.setRouteID((Integer) vehicle[6]);
					if(vehicle[7] != null) {
						select.setRouteName((String) vehicle[7]);
					}else {
						select.setRouteName((String) vehicle[26]);
					}
					select.setTripOpenDateOn((java.util.Date) vehicle[8]);
					select.setClosetripDateOn((java.util.Date) vehicle[9]);
					select.setTripBookref((String) vehicle[10]);
					select.setTripOpeningKM((Integer) vehicle[11]);
					select.setTripClosingKM((Integer) vehicle[12]);
					select.setTripUsageKM((Integer) vehicle[13]);
					select.setTripStutesId((short) vehicle[14]);
					select.setTripTotalAdvance((Double) vehicle[15]);
					select.setTripTotalexpense((Double)vehicle[16]);
					select.setTripTotalincome(Double.parseDouble(toFixedTwo.format(vehicle[17])));
					select.setCloseTripAmount((Double) vehicle[18]);
					select.setCloseACCTripAmount((Double) vehicle[19]);
					select.setTripFristDriverName((String) vehicle[20]);
					select.setTripSecDriverName((String) vehicle[21]);
					select.setTripCleanerName((String) vehicle[22]);
					select.setDispatchedBy((String) vehicle[23]);
					select.setDispatchedLocation((String) vehicle[24]);
					select.setDispatchedByTimeOn((java.util.Date) vehicle[25]);
					
					if(select.getRouteName() == null || select.getRouteName() == "") {
						select.setRouteName((String) vehicle[26]);
					}
					
					if(select.getTripOpenDateOn() != null) {
						select.setTripOpenDate(dateFormat.format(select.getTripOpenDateOn()));
					}
					if(select.getClosetripDateOn() != null) {
						select.setClosetripDate(dateFormat.format(select.getClosetripDateOn()));
					}
					if(vehicle[27] != null) {
						select.setExpenseAmount(Double.parseDouble(toFixedTwo.format(vehicle[27])));
					}else {
						select.setExpenseAmount(0.0);
					}
					select.setGpsTripUsageKM(Double.parseDouble(toFixedTwo.format(vehicle[28])));
					
					
					Dtos.add(select);
				}
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<TripSheetDto> getGroupUsageTripSheet(Long vid, String fromDate, String toDate,
			CustomUserDetails userDetails) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount , D1.driver_firstname, D2.driver_firstname,"
								+ " D3.driver_firstname, U.email, B.branch_name,T.dispatchedByTime, T.routeName, TE.expenseAmount, T.gpsTripUsageKM "
								+ " FROM TripSheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
								+ " LEFT JOIN User U ON U.id = T.dispatchedById"
								+ " LEFT JOIN Branch AS B ON B.branch_id = T.dispatchedLocationId "
								+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
								+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
								+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
								+ " LEFT JOIN TripSheetExpense TE ON TE.tripsheet.tripSheetID = T.tripSheetID AND (TE.fuel_id IS NULL OR TE.fuel_id = 0) AND TE.markForDelete = 0 "
								+ " where  T.companyId = "+ userDetails.getCompany_id() + " AND VG.gid = "+vid+" AND T.closetripDate between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.tripStutesId IN (3, 4) AND T.markForDelete = 0 "
								+ " ORDER BY T.vid ASC",
						Object[].class);

			} else {
				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount , D1.driver_firstname, D2.driver_firstname,"
								+ " D3.driver_firstname, U.email, B.branch_name, T.dispatchedByTime, T.routeName , TE.expenseAmount, T.gpsTripUsageKM "
								+ " FROM TripSheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
								+ " LEFT JOIN User U ON U.id = T.dispatchedById"
								+ " LEFT JOIN Branch AS B ON B.branch_id = T.dispatchedLocationId "
								+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
								+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
								+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+ userDetails.getId() + " "
								+ " LEFT JOIN TripSheetExpense TE ON TE.tripsheet.tripSheetID = T.tripSheetID AND (TE.fuel_id IS NULL OR TE.fuel_id = 0) AND TE.markForDelete = 0 "
								+ " where  T.companyId = "+ userDetails.getCompany_id() + " AND VG.gid = "+vid+" AND T.closetripDate between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.tripStutesId IN (3, 4) AND T.markForDelete = 0 "
								+ " ORDER BY T.vid ASC",
						Object[].class);
			}
			
			List<Object[]> results = query.getResultList();

			List<TripSheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetDto>();
				TripSheetDto select = null;
				for (Object[] vehicle : results) {

					select = new TripSheetDto();
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setVehicle_registration((String) vehicle[3]);
					select.setVehicle_Group((String) vehicle[4]);
					select.setVehicleGroupId((long) vehicle[5]);
					select.setRouteID((Integer) vehicle[6]);
					if(vehicle[7]!=null) {
						select.setRouteName((String) vehicle[7]);
					}else{
						select.setRouteName((String) vehicle[26]);
					}
					select.setTripOpenDateOn((java.util.Date) vehicle[8]);
					select.setClosetripDateOn((java.util.Date) vehicle[9]);
					select.setTripBookref((String) vehicle[10]);
					select.setTripOpeningKM((Integer) vehicle[11]);
					select.setTripClosingKM((Integer) vehicle[12]);
					select.setTripUsageKM((Integer) vehicle[13]);
					select.setTripStutesId((short) vehicle[14]);
					if(vehicle[15] != null)
					select.setTripTotalAdvance(Double.parseDouble(toFixedTwo.format(vehicle[15])));
					if(vehicle[16] != null)
					select.setTripTotalexpense(Double.parseDouble(toFixedTwo.format(vehicle[16])));
					if(vehicle[17] != null)
					select.setTripTotalincome(Double.parseDouble(toFixedTwo.format(vehicle[17])));
					if(vehicle[18] != null)
					select.setCloseTripAmount(Double.parseDouble(toFixedTwo.format(vehicle[18])));
					if(vehicle[19] != null)
					select.setCloseACCTripAmount(Double.parseDouble(toFixedTwo.format(vehicle[19])));
					select.setTripFristDriverName((String) vehicle[20]);
					select.setTripSecDriverName((String) vehicle[21]);
					select.setTripCleanerName((String) vehicle[22]);
					select.setDispatchedBy((String) vehicle[23]);
					select.setDispatchedLocation((String) vehicle[24]);
					select.setDispatchedByTimeOn((java.util.Date) vehicle[25]);
					
					if(select.getRouteName() == null || select.getRouteName() == "") {
						select.setRouteName((String) vehicle[26]);
					}
					
					if(select.getTripOpenDateOn() != null) {
						select.setTripOpenDate(dateFormat.format(select.getTripOpenDateOn()));
					}
					if(select.getClosetripDateOn() != null) {
						select.setClosetripDate(dateFormat.format(select.getClosetripDateOn()));
					}
					if(vehicle[27] != null) {
						select.setExpenseAmount(Double.parseDouble(toFixedTwo.format(vehicle[27])));
					}else {
						select.setExpenseAmount(0.0);
					}
					if(vehicle[28] != null) {
						select.setTripGpsUsageKM(Double.parseDouble(toFixedTwo.format(vehicle[28])));
					}else {
						select.setTripGpsUsageKM(0.0);
					}
					
					Dtos.add(select);
				}
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<TripSheetDto> getVehicleUsageTripDailySheet(Integer vid, String fromDate, String toDate,
			CustomUserDetails userDetails) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

				query = entityManager.createQuery(
						"SELECT T.TRIPDAILYID, T.TRIPDAILYNUMBER, T.VEHICLEID, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.TRIP_OPEN_DATE, T.LASTUPDATED,"
								+ " T.TOTAL_EXPENSE, T.TOTAL_INCOME, T.TOTAL_NET_INCOME, T.TOTAL_BALANCE, T.TRIP_STATUS_ID,"
								+ " T.TOTAL_INCOME_COLLECTION, T.TRIP_USAGE_KM, TD.expenseAmount "
								+ " FROM TripDailySheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.VEHICLEID "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID "
								+ " LEFT JOIN TripDailyExpense TD ON TD.tripDailysheet.TRIPDAILYID = T.TRIPDAILYID AND (TD.fuel_id IS NULL OR TD.fuel_id = 0) AND TD.markForDelete = 0 "
								+ " where  T.COMPANY_ID = "+ userDetails.getCompany_id() + " AND T.VEHICLEID = "+vid+" AND T.TRIP_OPEN_DATE between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND T.markForDelete = 0 "
								+ " ORDER BY T.TRIPDAILYID ASC",
						Object[].class);

			} else {
				query = entityManager.createQuery(
						"SELECT T.TRIPDAILYID, T.TRIPDAILYNUMBER, T.VEHICLEID, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.TRIP_OPEN_DATE, T.LASTUPDATED,"
								+ " T.TOTAL_EXPENSE, T.TOTAL_INCOME, T.TOTAL_NET_INCOME, T.TOTAL_BALANCE, T.TRIP_STATUS_ID,"
								+ " T.TOTAL_INCOME_COLLECTION, T.TRIP_USAGE_KM, TD.expenseAmount "
								+ " FROM TripDailySheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.VEHICLEID "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID "
								+ " LEFT JOIN TripDailyExpense TD ON TD.tripDailysheet.TRIPDAILYID = T.TRIPDAILYID AND (TD.fuel_id IS NULL OR TD.fuel_id = 0) AND TD.markForDelete = 0 "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" 
								+ " where  T.COMPANY_ID = "+ userDetails.getCompany_id() + " AND T.VEHICLEID = "+vid+" AND T.TRIP_OPEN_DATE between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND T.markForDelete = 0 "
								+ " ORDER BY T.TRIPDAILYID ASC",
						Object[].class);
			}
			
			List<Object[]> results = query.getResultList();

			List<TripSheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetDto>();
				TripSheetDto select = null;
				for (Object[] vehicle : results) {

					select = new TripSheetDto();
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setVehicle_registration((String) vehicle[3]);
					select.setVehicle_Group((String) vehicle[4]);
					select.setVehicleGroupId((long) vehicle[5]);
					select.setRouteID((Integer) vehicle[6]);
					if(vehicle[7] != null) {
						select.setRouteName((String) vehicle[7]);
					}else {
						select.setRouteName((String) vehicle[26]);
					}
					select.setTripOpenDateOn((java.util.Date) vehicle[8]);
					select.setClosetripDateOn((java.util.Date) vehicle[9]);
					select.setTripTotalexpense((Double) vehicle[10]);
					select.setTripTotalincome(Double.parseDouble(toFixedTwo.format(vehicle[11])));
					select.setTotalNetIncome((Double) vehicle[12]);
					select.setTotalBalance((Double) vehicle[13]);
					select.setTripStutesId((short) vehicle[14]);
					select.setTotalIncomeCollection((Double) vehicle[15]);
					select.setTripUsageKM((Integer) vehicle[16]);
					
					
					if(select.getTripOpenDateOn() != null) {
						select.setTripOpenDate(dateFormat.format(select.getTripOpenDateOn()));
					}
					if(select.getClosetripDateOn() != null) {
						if(select.getTripStutesId() == TripDailySheetStatus.TRIP_STATUS_CLOSED)
							select.setClosetripDate(dateFormat.format(select.getClosetripDateOn()));
					}
					if(vehicle[17] !=  null) {
						select.setExpenseAmount(Double.parseDouble(toFixedTwo.format(vehicle[17])));
					}else {
						select.setExpenseAmount(0.0);
					}
					Dtos.add(select);
				}
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<TripSheetDto> getGroupUsageTripDailySheet(Long vid, String fromDate, String toDate,
			CustomUserDetails userDetails) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

				query = entityManager.createQuery(
						"SELECT T.TRIPDAILYID, T.TRIPDAILYNUMBER, T.VEHICLEID, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.TRIP_OPEN_DATE, T.LASTUPDATED,"
								+ " T.TOTAL_EXPENSE, T.TOTAL_INCOME, T.TOTAL_NET_INCOME, T.TOTAL_BALANCE, T.TRIP_STATUS_ID,"
								+ " T.TOTAL_INCOME_COLLECTION, T.TRIP_USAGE_KM, TD.expenseAmount "
								+ " FROM TripDailySheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.VEHICLEID "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID "
								+ " LEFT JOIN TripDailyExpense TD ON TD.tripDailysheet.TRIPDAILYID = T.TRIPDAILYID AND (TD.fuel_id IS NULL OR TD.fuel_id = 0) AND TD.markForDelete = 0 "
								+ " where  T.COMPANY_ID = "+ userDetails.getCompany_id() + " AND VG.gid = "+vid+" AND T.TRIP_OPEN_DATE between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND T.markForDelete = 0 "
								+ " ORDER BY T.TRIPDAILYID ASC",
						Object[].class);

			} else {
				query = entityManager.createQuery(
						"SELECT T.TRIPDAILYID, T.TRIPDAILYNUMBER, T.VEHICLEID, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.TRIP_OPEN_DATE, T.LASTUPDATED,"
								+ " T.TOTAL_EXPENSE, T.TOTAL_INCOME, T.TOTAL_NET_INCOME, T.TOTAL_BALANCE, T.TRIP_STATUS_ID,"
								+ " T.TOTAL_INCOME_COLLECTION, T.TRIP_USAGE_KM, TD.expenseAmount "
								+ " FROM TripDailySheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.VEHICLEID "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID "
								+ " LEFT JOIN TripDailyExpense TD ON TD.tripDailysheet.TRIPDAILYID = T.TRIPDAILYID AND (TD.fuel_id IS NULL OR TD.fuel_id = 0) AND TD.markForDelete = 0 "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" 
								+ " where  T.COMPANY_ID = "+ userDetails.getCompany_id() + " AND VG.gid = "+vid+" AND T.TRIP_OPEN_DATE between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND T.markForDelete = 0 "
								+ " ORDER BY T.TRIPDAILYID ASC",
						Object[].class);
			}
			
			List<Object[]> results = query.getResultList();

			List<TripSheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetDto>();
				TripSheetDto select = null;
				for (Object[] vehicle : results) {

					select = new TripSheetDto();
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setVehicle_registration((String) vehicle[3]);
					select.setVehicle_Group((String) vehicle[4]);
					select.setVehicleGroupId((long) vehicle[5]);
					select.setRouteID((Integer) vehicle[6]);
					select.setRouteName((String) vehicle[7]);
					select.setTripOpenDateOn((java.util.Date) vehicle[8]);
					select.setClosetripDateOn((java.util.Date) vehicle[9]);
					select.setTripTotalexpense((Double) vehicle[10]);
					select.setTripTotalincome((Double) vehicle[11]);
					select.setTotalNetIncome((Double) vehicle[12]);
					select.setTotalBalance((Double) vehicle[13]);
					select.setTripStutesId((short) vehicle[14]);
					select.setTotalIncomeCollection((Double) vehicle[15]);
					select.setTripUsageKM((Integer) vehicle[16]);
					
					
					if(select.getTripOpenDateOn() != null) {
						select.setTripOpenDate(dateFormat.format(select.getTripOpenDateOn()));
					}
					if(select.getClosetripDateOn() != null) {
						if(select.getTripStutesId() == TripDailySheetStatus.TRIP_STATUS_CLOSED)
							select.setClosetripDate(dateFormat.format(select.getClosetripDateOn()));
					}
					if(vehicle[17] !=  null) {
						select.setExpenseAmount((double) vehicle[17]);
					}else {
						select.setExpenseAmount(0.0);
					}
					Dtos.add(select);
				}
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<TripSheetDto> getRouteUsageTripSheet(Integer routeID,String vehicleStr, String fromDate, String toDate,
			CustomUserDetails userDetails) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount , D1.driver_firstname, D2.driver_firstname,"
								+ " D3.driver_firstname, U.email, B.branch_name,T.dispatchedByTime, T.routeName, TE.expenseAmount "
								+ " FROM TripSheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
								+ " LEFT JOIN User U ON U.id = T.dispatchedById"
								+ " LEFT JOIN Branch AS B ON B.branch_id = T.dispatchedLocationId "
								+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
								+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
								+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
								+ " LEFT JOIN TripSheetExpense TE ON TE.tripsheet.tripSheetID = T.tripSheetID AND (TE.fuel_id IS NULL OR TE.fuel_id = 0) AND TE.markForDelete = 0 "
								+ " where  T.companyId = "+ userDetails.getCompany_id() + " AND T.routeID = "+routeID+" AND T.closetripDate between "
								+ " '"+fromDate+"' AND '"+toDate+"' "+vehicleStr+" AND T.tripStutesId IN (3, 4) AND T.markForDelete = 0 "
								+ " ORDER BY T.tripSheetID ASC",
						Object[].class);

			} else {
				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount , D1.driver_firstname, D2.driver_firstname,"
								+ " D3.driver_firstname, U.email, B.branch_name, T.dispatchedByTime, T.routeName, TE.expenseAmount "
								+ " FROM TripSheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
								+ " LEFT JOIN User U ON U.id = T.dispatchedById"
								+ " LEFT JOIN Branch AS B ON B.branch_id = T.dispatchedLocationId "
								+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
								+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
								+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
								+ " LEFT JOIN TripSheetExpense TE ON TE.tripsheet.tripSheetID = T.tripSheetID AND (TE.fuel_id IS NULL OR TE.fuel_id = 0) AND TE.markForDelete = 0 "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" 
								+ " where  T.companyId = "+ userDetails.getCompany_id() + " AND T.routeID = "+routeID+" AND T.closetripDate between "
								+ " '"+fromDate+"' AND '"+toDate+"' "+vehicleStr+" AND T.tripStutesId IN (3, 4) AND T.markForDelete = 0 "
								+ " ORDER BY T.tripSheetID ASC",
						Object[].class);
			}
			
			List<Object[]> results = query.getResultList();

			List<TripSheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetDto>();
				TripSheetDto select = null;
				for (Object[] vehicle : results) {

					select = new TripSheetDto();
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setVehicle_registration((String) vehicle[3]);
					select.setVehicle_Group((String) vehicle[4]);
					select.setVehicleGroupId((long) vehicle[5]);
					select.setRouteID((Integer) vehicle[6]);
					if(vehicle[7] != null) {
						select.setRouteName((String) vehicle[7]);
					}else {
						select.setRouteName((String) vehicle[26]);
					}
					select.setTripOpenDateOn((java.util.Date) vehicle[8]);
					select.setClosetripDateOn((java.util.Date) vehicle[9]);
					select.setTripBookref((String) vehicle[10]);
					select.setTripOpeningKM((Integer) vehicle[11]);
					select.setTripClosingKM((Integer) vehicle[12]);
					select.setTripUsageKM((Integer) vehicle[13]);
					select.setTripStutesId((short) vehicle[14]);
					select.setTripTotalAdvance((Double) vehicle[15]);
					select.setTripTotalexpense((Double) vehicle[16]);
					select.setTripTotalincome(Double.parseDouble(toFixedTwo.format(vehicle[17])));
					select.setCloseTripAmount((Double) vehicle[18]);
					select.setCloseACCTripAmount((Double) vehicle[19]);
					select.setTripFristDriverName((String) vehicle[20]);
					select.setTripSecDriverName((String) vehicle[21]);
					select.setTripCleanerName((String) vehicle[22]);
					select.setDispatchedBy((String) vehicle[23]);
					select.setDispatchedLocation((String) vehicle[24]);
					select.setDispatchedByTimeOn((java.util.Date) vehicle[25]);
					
					if(select.getRouteName() == null || select.getRouteName() == "") {
						select.setRouteName((String) vehicle[26]);
					}
					
					if(select.getTripOpenDateOn() != null) {
						select.setTripOpenDate(dateFormat.format(select.getTripOpenDateOn()));
					}
					if(select.getClosetripDateOn() != null) {
						select.setClosetripDate(dateFormat.format(select.getClosetripDateOn()));
					}if(vehicle[27] != null) {
						select.setExpenseAmount(Double.parseDouble(toFixedTwo.format(vehicle[27])));
					}else {
						select.setExpenseAmount(0.0);
					}
					Dtos.add(select);
				}
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<TripSheetDto> getRouteUsageTripDailySheet(Integer routeID, String vehicleStr, String fromDate, String toDate,
			CustomUserDetails userDetails) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

				query = entityManager.createQuery(
						"SELECT T.TRIPDAILYID, T.TRIPDAILYNUMBER, T.VEHICLEID, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.TRIP_OPEN_DATE, T.LASTUPDATED,"
								+ " T.TOTAL_EXPENSE, T.TOTAL_INCOME, T.TOTAL_NET_INCOME, T.TOTAL_BALANCE, T.TRIP_STATUS_ID,"
								+ " T.TOTAL_INCOME_COLLECTION, T.TRIP_USAGE_KM, TD.expenseAmount "
								+ " FROM TripDailySheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.VEHICLEID "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID "
								+ " LEFT JOIN TripDailyExpense TD ON TD.tripDailysheet.TRIPDAILYID = T.TRIPDAILYID AND (TD.fuel_id IS NULL OR TD.fuel_id = 0) AND TD.markForDelete = 0 "
								+ " where  T.COMPANY_ID = "+ userDetails.getCompany_id() + " AND T.TRIP_ROUTE_ID = "+routeID+" AND T.TRIP_OPEN_DATE between "
								+ " '"+fromDate+"' AND '"+toDate+"' "+vehicleStr+" AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND T.markForDelete = 0 "
								+ " ORDER BY T.TRIPDAILYID ASC",
						Object[].class);

			} else {
				query = entityManager.createQuery(
						"SELECT T.TRIPDAILYID, T.TRIPDAILYNUMBER, T.VEHICLEID, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.TRIP_OPEN_DATE, T.LASTUPDATED,"
								+ " T.TOTAL_EXPENSE, T.TOTAL_INCOME, T.TOTAL_NET_INCOME, T.TOTAL_BALANCE, T.TRIP_STATUS_ID,"
								+ " T.TOTAL_INCOME_COLLECTION, T.TRIP_USAGE_KM, TD.expenseAmount "
								+ " FROM TripDailySheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.VEHICLEID "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID "
								+ " LEFT JOIN TripDailyExpense TD ON TD.tripDailysheet.TRIPDAILYID = T.TRIPDAILYID AND (TD.fuel_id IS NULL OR TD.fuel_id = 0) AND TD.markForDelete = 0 "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" 
								+ " where  T.COMPANY_ID = "+ userDetails.getCompany_id() + " AND T.TRIP_ROUTE_ID = "+routeID+" AND T.TRIP_OPEN_DATE between "
								+ " '"+fromDate+"' AND '"+toDate+"' "+vehicleStr+" AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+"  AND T.markForDelete = 0 "
								+ " ORDER BY T.TRIPDAILYID ASC",
						Object[].class);
			}
			
			List<Object[]> results = query.getResultList();

			List<TripSheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetDto>();
				TripSheetDto select = null;
				for (Object[] vehicle : results) {

					select = new TripSheetDto();
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setVehicle_registration((String) vehicle[3]);
					select.setVehicle_Group((String) vehicle[4]);
					select.setVehicleGroupId((long) vehicle[5]);
					select.setRouteID((Integer) vehicle[6]);
					select.setRouteName((String) vehicle[7]);
					select.setTripOpenDateOn((java.util.Date) vehicle[8]);
					select.setClosetripDateOn((java.util.Date) vehicle[9]);
					select.setTripTotalexpense((Double) vehicle[10]);
					select.setTripTotalincome((Double) vehicle[11]);
					select.setTotalNetIncome((Double) vehicle[12]);
					select.setTotalBalance((Double) vehicle[13]);
					select.setTripStutesId((short) vehicle[14]);
					select.setTotalIncomeCollection((Double) vehicle[15]);
					select.setTripUsageKM((Integer) vehicle[16]);
					
					
					if(select.getTripOpenDateOn() != null) {
						select.setTripOpenDate(dateFormat.format(select.getTripOpenDateOn()));
					}
					if(select.getClosetripDateOn() != null) {
						if(select.getTripStutesId() == TripDailySheetStatus.TRIP_STATUS_CLOSED)
							select.setClosetripDate(dateFormat.format(select.getClosetripDateOn()));
					}
					if(vehicle[17] != null) {
						select.setExpenseAmount((double) vehicle[17]);
					}else {
						select.setExpenseAmount(0.0);
					}
					
					Dtos.add(select);
				}
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<TripSheetDto> getRouteWiseTripsheetExpense(String routeID, String fromDate, String toDate, CustomUserDetails userDetails) throws Exception {
		TypedQuery<Object[]> 		query 					= null;
		TripSheetDto 				tripSheetDto 			= null;
		List<TripSheetDto> 			tripsheetDtoList 		= null;
		try {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						" SELECT count(DISTINCT T.tripSheetID),SUM(TE.expenseAmount),T.tripTotalAdvance,T.tripTotalexpense ,T.tripTotalincome,TR.routeID, T.tripUsageKM,TR.routeName "
						+ " FROM TripSheet T "
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
						+ " LEFT JOIN TripSheetExpense TE ON TE.tripsheet.tripSheetID = T.tripSheetID AND (TE.fuel_id IS NULL OR TE.fuel_id = 0) "
						+ " where  T.companyId = "+ userDetails.getCompany_id() + " "+routeID+" AND T.closetripDate between "
						+ " '"+fromDate+"' AND '"+toDate+"' AND T.tripStutesId IN ("+TripSheetStatus.TRIP_STATUS_CLOSED+", "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+") AND T.markForDelete = 0 "
						+ " GROUP BY T.tripSheetID ORDER BY T.tripSheetID ASC",
				Object[].class);

			} else {
				query = entityManager.createQuery(
						" SELECT count(DISTINCT T.tripSheetID),SUM(TE.expenseAmount),T.tripTotalAdvance,T.tripTotalexpense ,T.tripTotalincome,TR.routeID, T.tripUsageKM ,TR.routeName  "
						+ " FROM TripSheet T "
						+ " INNER JOIN Vehicle V ON V.vid = T.vid"
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
						+ userDetails.getId() + ""
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
						+ " LEFT JOIN TripSheetExpense TE ON TE.tripsheet.tripSheetID = T.tripSheetID AND (TE.fuel_id IS NULL OR TE.fuel_id = 0) "
						+ " where  T.companyId = "+ userDetails.getCompany_id() + " "+routeID+" AND T.closetripDate between "
						+ " '"+fromDate+"' AND '"+toDate+"' AND T.tripStutesId IN ("+TripSheetStatus.TRIP_STATUS_CLOSED+", "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+") AND T.markForDelete = 0 "
						+ " GROUP BY T.tripSheetID ORDER BY T.tripSheetID ASC",
				Object[].class);
			}
			
			List<Object[]> results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				
				tripsheetDtoList = new ArrayList<TripSheetDto>();
				
				for (Object[] vehicle : results) {
					tripSheetDto = new TripSheetDto();
					
					tripSheetDto.setTripSheetCount((Long) vehicle[0]);
					if((Double) vehicle[1] != null) {
					tripSheetDto.setExpenseAmount((Double) vehicle[1]);
					}else {
					tripSheetDto.setExpenseAmount(0.0);
					}
					tripSheetDto.setTripTotalAdvance((Double) vehicle[2]);
					if((Double) vehicle[3] != null) {
					tripSheetDto.setTripTotalexpense((Double) vehicle[3]);
					}
					if((Double) vehicle[4] != null) {
					tripSheetDto.setTripTotalincome((Double) vehicle[4]);
					}else {
					tripSheetDto.setTripTotalincome(0.0);
					}
					tripSheetDto.setRouteID((Integer) vehicle[5]);
					if(vehicle[6] != null) {
					tripSheetDto.setTripTotalUsageKM(Long.parseLong(vehicle[6]+""));
					}
					tripSheetDto.setRouteName((String) vehicle[7]);
					
					tripsheetDtoList.add(tripSheetDto);
					
				}
			}
			return tripsheetDtoList;
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;
		}finally {
			query 					= null;  
			tripSheetDto 			= null;  
			tripsheetDtoList 		= null;  
		}
	}
	@Override
	public List<TripSheetDto> getRouteWiseTripsDailyExpense(String routeID, String fromDate, String toDate, CustomUserDetails userDetails) throws Exception {
		TypedQuery<Object[]> 		query 					= null;
		TripSheetDto 				tripSheetDto 			= null;
		List<TripSheetDto> 			tripsheetDtoList 		= null;
		try {

			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				
				query = entityManager.createQuery(
						" SELECT count(DISTINCT T.TRIPDAILYID),SUM(TD.expenseAmount),T.TOTAL_EXPENSE ,T.TOTAL_INCOME,TR.routeID, SUM(T.TRIP_USAGE_KM) "
						+ " FROM TripDailySheet T "
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
						+ " LEFT JOIN TripDailyExpense TD ON TD.tripDailysheet.TRIPDAILYID = T.TRIPDAILYID AND (TD.fuel_id IS NULL OR TD.fuel_id = 0) "
						+ " where  T.COMPANY_ID = "+ userDetails.getCompany_id() + " "+routeID+" AND T.TRIP_OPEN_DATE between "
						+ " '"+fromDate+"' AND '"+toDate+"' AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND T.markForDelete = 0 "
						+ " GROUP BY T.TRIPDAILYID ORDER BY T.TRIPDAILYID ASC",
				Object[].class);
				
			} else {
				query = entityManager.createQuery(
						" SELECT count(DISTINCT T.TRIPDAILYID),SUM(TD.expenseAmount),T.TOTAL_EXPENSE ,T.TOTAL_INCOME,TR.routeID, SUM(T.TRIP_USAGE_KM) "
						+ " FROM TripDailySheet T "
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
						+ " LEFT JOIN TripDailyExpense TD ON TD.tripDailysheet.TRIPDAILYID = T.TRIPDAILYID AND (TD.fuel_id IS NULL OR TD.fuel_id = 0) "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
						+ userDetails.getId() + ""
						+ " where  T.COMPANY_ID = "+ userDetails.getCompany_id() + " "+routeID+" AND T.TRIP_OPEN_DATE between "
						+ " '"+fromDate+"' AND '"+toDate+"' AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND T.markForDelete = 0 "
						+ " GROUP BY T.TRIPDAILYID ORDER BY T.TRIPDAILYID ASC",
				Object[].class);
			}
			
			List<Object[]> results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
			
			tripsheetDtoList = new ArrayList<TripSheetDto>();
			
			for (Object[] vehicle : results) {
				tripSheetDto = new TripSheetDto();
				
				tripSheetDto.setTripSheetCount((Long) vehicle[0]);
				if((Double) vehicle[1] != null) {
				tripSheetDto.setExpenseAmount(Double.parseDouble(toFixedTwo.format(vehicle[1])));
				}else {
				tripSheetDto.setExpenseAmount(0.0);
				}
				if((Double) vehicle[2] != null) {
				tripSheetDto.setTripTotalexpense((Double) vehicle[2]);
				}
				if((Double) vehicle[3] != null) {
				tripSheetDto.setTripTotalincome(Double.parseDouble(toFixedTwo.format(vehicle[3])));
				}else {
				tripSheetDto.setTripTotalincome(0.0);
				}
				tripSheetDto.setRouteID((Integer) vehicle[4]);
				if((Long) vehicle[5] != null) {
				tripSheetDto.setTripTotalUsageKM((Long) vehicle[5]);
				}
				
				tripsheetDtoList.add(tripSheetDto);
			}
		}
			return tripsheetDtoList;
		}
		catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;
		}finally {
			query 					= null;  
			tripSheetDto 			= null;  
			tripsheetDtoList 		= null;  
		}
	}
}
