package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.TripSheetReportDto;
import org.fleetopgroup.persistence.report.dao.TripSheetReportDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripSheetReportDaoImpl implements TripSheetReportDao {

	@PersistenceContext EntityManager entityManager;
	@Autowired ICompanyConfigurationService			companyConfigurationService;
	SimpleDateFormat dateFormat 			= new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public List<TripSheetReportDto> getTripSheetReportDtoList(String filterQuery, Integer companyId, Long userId) throws Exception {
		TypedQuery<Object[]> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"Select T.tripSheetID, T.tripSheetNumber, V.vehicle_registration, D.driver_firstname, D.driver_id,"
							+ " TR.routeName, T.tripTotalAdvance, T.tripTotalincome, T.routeName, T.tripOpenDate, T.closetripDate,"
							+ " T.vid "
							+ " From TripSheet AS T "
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN Driver D ON D.driver_id = T.tripFristDriverID "
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID"
							+ " where "+ filterQuery + " AND T.companyId = " + companyId+" AND T.markForDelete = 0 "
							+ " ORDER BY T.tripSheetID asc",
							Object[].class);
		}else {

			query = entityManager.createQuery(
					"Select T.tripSheetID, T.tripSheetNumber, V.vehicle_registration, D.driver_firstname, D.driver_id,"
							+ " TR.routeName, T.tripTotalAdvance, T.tripTotalincome, T.routeName, T.tripOpenDate, T.closetripDate, "
							+ " T.vid "
							+ " From TripSheet AS T "
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+userId+""
							+ " INNER JOIN Driver D ON D.driver_id = T.tripFristDriverID "
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID"
							+ " where "+ filterQuery + " AND T.companyId = " + companyId+" AND T.markForDelete = 0 "
							+ " ORDER BY T.tripSheetID asc",
							Object[].class);
		
		}
		List<Object[]> results = query.getResultList();

		List<TripSheetReportDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetReportDto>();
			TripSheetReportDto list = null;
			for (Object[] result : results) {
				list = new TripSheetReportDto();

				list.setTripSheetId((Long) result[0]);
				list.setTripSheetNumber("TS-"+(Long) result[1]);
				list.setVehicleRegistration((String) result[2]);
				list.setDriverName((String) result[3]);
				list.setDriverId((Integer) result[4]);
				list.setRouteName((String) result[5]);
				list.setTripAdvanceAmount((Double) result[6]);
				list.setTripIncomeAmount((Double) result[7]);
				if(list.getRouteName() == null) {
					list.setRouteName((String) result[8]);
				}
				list.setTripOpenDate((Date) result[9]);
				list.setTripCloseDate((Date) result[10]);
				list.setVid((Integer) result[11]);
				
				Dtos.add(list);
			}

		}
		return Dtos;

	}

	@Override
	public HashMap<Long, TripSheetReportDto> getTripSheetFuelData(String filterQuery, Integer companyId, Long userId) throws Exception {
		HashMap<Long, TripSheetReportDto>   tripSheetHM		= null;
		TripSheetReportDto					tempTripDto		= null;
		try {
			TypedQuery<Object[]> query = null;
			
			tripSheetHM	= new HashMap<Long, TripSheetReportDto>();
			if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"Select T.tripSheetID, F.fuel_liters, F.fuel_amount "
								+ " From TripSheet AS T "
								+ " INNER JOIN Fuel F ON F.fuel_TripsheetID = T.tripSheetID AND F.markForDelete = 0"
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " where "+ filterQuery + " AND T.companyId = " + companyId+" AND F.companyId = " + companyId+" AND T.markForDelete = 0 AND F.fuel_amount > 0 "
								+ " ORDER BY T.tripSheetID asc",
								Object[].class);
			}else {

				query = entityManager.createQuery(
						"Select T.tripSheetID, F.fuel_liters, F.fuel_amount "
								+ " From TripSheet AS T "
								+ " INNER JOIN Fuel F ON F.fuel_TripsheetID = T.tripSheetID AND F.markForDelete = 0"
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+userId+""
								+ " where "+ filterQuery + " AND T.companyId = " + companyId+" AND F.companyId = " + companyId+" AND T.markForDelete = 0 AND F.fuel_amount > 0 "
								+ " ORDER BY T.tripSheetID asc",
								Object[].class);
			
			}
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				TripSheetReportDto list = null;
				for (Object[] result : results) {
					list = new TripSheetReportDto();

					list.setTripSheetId((Long) result[0]);
					list.setTripFuelQuantity((Double) result[1]);
					list.setTripFuelAmount((Double) result[2]);
					
					tempTripDto	= tripSheetHM.get(list.getTripSheetId());
					
					if(tempTripDto == null) {
						tempTripDto	= list;
					}else {
						tempTripDto.setTripFuelQuantity(tempTripDto.getTripFuelQuantity() + list.getTripFuelQuantity());
						tempTripDto.setTripFuelAmount(tempTripDto.getTripFuelAmount() + list.getTripFuelAmount());
					}
					tripSheetHM.put(list.getTripSheetId(), tempTripDto);
				}

			}
			return tripSheetHM;


		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public HashMap<Long, TripSheetReportDto> getTripExpenseDataHM(String filterQuery, Integer companyId, Long userId)
			throws Exception {
		HashMap<Long, TripSheetReportDto>   tripSheetHM		= null;
		TripSheetReportDto					tempTripDto		= null;
		try {
			TypedQuery<Object[]> query = null;
			
			tripSheetHM	= new HashMap<Long, TripSheetReportDto>();
			if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"Select T.tripSheetID, TE.expenseAmount "
								+ " From TripSheet AS T "
								+ " INNER JOIN TripSheetExpense TE ON TE.tripsheet.tripSheetID = T.tripSheetID AND (TE.fuel_id = null OR TE.fuel_id = 0) AND TE.markForDelete = 0"
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " where "+ filterQuery + " AND T.companyId = " + companyId+" AND TE.companyId = " + companyId+" AND T.markForDelete = 0 "
								+ " AND TE.expenseAmount > 0 AND TE.expenseAmount is not null  "
								+ " ORDER BY T.tripSheetID asc",
								Object[].class);
			}else {

				query = entityManager.createQuery(
						"Select T.tripSheetID, TE.expenseAmount"
								+ " From TripSheet AS T "
								+ " INNER JOIN TripSheetExpense TE ON TE.tripsheet.tripSheetID = T.tripSheetID AND (TE.fuel_id = null OR TE.fuel_id = 0) AND TE.markForDelete = 0"
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+userId+""
								+ " where "+ filterQuery + " AND T.companyId = " + companyId+" AND TE.companyId = " + companyId+" AND T.markForDelete = 0 "
								+ " AND TE.expenseAmount > 0 AND TE.expenseAmount is not null  "
								+ " ORDER BY T.tripSheetID asc",
								Object[].class);
			
			}
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				for (Object[] result : results) {
					
					tempTripDto	= tripSheetHM.get((Long) result[0]);
					
					if(tempTripDto == null) {
						tempTripDto	= new TripSheetReportDto();
						tempTripDto.setTripSheetId((Long) result[0]);
						tempTripDto.setTripExpenseAmount((Double) result[1]);
					}else {
						tempTripDto.setTripExpenseAmount(tempTripDto.getTripExpenseAmount() + (Double) result[1]);
					}
					tripSheetHM.put(tempTripDto.getTripSheetId(), tempTripDto);
				}

			}
			return tripSheetHM;


		} catch (Exception e) {
			throw e;
		}finally {
			tripSheetHM		= null;
			tempTripDto		= null;
		}
	}
	
	@Override
	public HashMap<Long, TripSheetReportDto> getTripTollDataHM(String filterQuery, Integer companyId, Long userId)
			throws Exception {
		HashMap<Long, TripSheetReportDto>   tripSheetHM		= null;
		TripSheetReportDto					tempTripDto		= null;
		try {
			TypedQuery<Object[]> query = null;
			
			tripSheetHM	= new HashMap<Long, TripSheetReportDto>();
			if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"Select T.tripSheetID, TE.transactionAmount "
								+ " From TripSheet AS T "
								+ " INNER JOIN TollExpensesDetails TE ON TE.tripSheetId = T.tripSheetID AND TE.markForDelete = 0"
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " where "+ filterQuery + " AND T.companyId = " + companyId+" AND TE.companyId = " + companyId+" AND T.markForDelete = 0 "
								+ " AND TE.transactionAmount > 0 AND TE.transactionAmount is not null  "
								+ " ORDER BY T.tripSheetID asc",
								Object[].class);
			}else {

				query = entityManager.createQuery(
						"Select T.tripSheetID, TE.transactionAmount"
								+ " From TripSheet AS T "
								+ " INNER JOIN TollExpensesDetails TE ON TE.tripSheetId = T.tripSheetID AND TE.markForDelete = 0"
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+userId+""
								+ " where "+ filterQuery + " AND T.companyId = " + companyId+" AND TE.companyId = " + companyId+" AND T.markForDelete = 0 "
								+ " AND TE.transactionAmount > 0 AND TE.transactionAmount is not null  "
								+ " ORDER BY T.tripSheetID asc",
								Object[].class);
			
			}
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				for (Object[] result : results) {
					
					tempTripDto	= tripSheetHM.get((Long) result[0]);
					
					if(tempTripDto == null) {
						tempTripDto	= new TripSheetReportDto();
						tempTripDto.setTripSheetId((Long) result[0]);
						tempTripDto.setTripFastTagAmt((Double) result[1]);
					}else {
						tempTripDto.setTripFastTagAmt(tempTripDto.getTripFastTagAmt() + (Double) result[1]);
					}
					tripSheetHM.put(tempTripDto.getTripSheetId(), tempTripDto);
				}

			}
			return tripSheetHM;


		} catch (Exception e) {
			throw e;
		}finally {
			tripSheetHM		= null;
			tempTripDto		= null;
		}
	}
	
	@Override
	public HashMap<Long, TripSheetReportDto> getTripDueAmount(String filterQuery, Integer companyId, Long userId)
			throws Exception {
		HashMap<Long, TripSheetReportDto>   tripSheetHM		= null;
		TripSheetReportDto					tempTripDto		= null;
		double								fastTagAmt		= 0;
		try {
			TypedQuery<Object[]> query = null;
			
			tripSheetHM	= new HashMap<Long, TripSheetReportDto>();
			if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"Select T.tripSheetID, TE.balanceAmount "
								+ " From TripSheet AS T "
								+ " INNER JOIN TripsheetDueAmount TE ON TE.tripSheetID = T.tripSheetID AND TE.markForDelete = 0"
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " where "+ filterQuery + " AND T.companyId = " + companyId+" AND TE.companyId = " + companyId+" AND T.markForDelete = 0 "
								+ " AND TE.balanceAmount > 0 AND TE.balanceAmount is not null  "
								+ " ORDER BY T.tripSheetID asc",
								Object[].class);
			}else {

				query = entityManager.createQuery(
						"Select T.tripSheetID, TE.balanceAmount"
								+ " From TripSheet AS T "
								+ " INNER JOIN TripsheetDueAmount TE ON TE.tripSheetID = T.tripSheetID AND TE.markForDelete = 0"
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+userId+""
								+ " where "+ filterQuery + " AND T.companyId = " + companyId+" AND TE.companyId = " + companyId+" AND T.markForDelete = 0 "
								+ " AND TE.balanceAmount > 0 AND TE.balanceAmount is not null  "
								+ " ORDER BY T.tripSheetID asc",
								Object[].class);
			
			}
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				for (Object[] result : results) {
					
					tempTripDto	= tripSheetHM.get((Long) result[0]);
					
					if(tempTripDto == null) {
						tempTripDto	= new TripSheetReportDto();
						tempTripDto.setTripSheetId((Long) result[0]);
						tempTripDto.setTripDueAmount((Double) result[1]);
					}else {
						if(tempTripDto.getTripFastTagAmt() == null) {
							fastTagAmt = 0.0;
						}else {
							fastTagAmt = tempTripDto.getTripFastTagAmt() ;
						}
						tempTripDto.setTripDueAmount(fastTagAmt + (Double) result[1]);
					}
					tripSheetHM.put(tempTripDto.getTripSheetId(), tempTripDto);
				}

			}
			return tripSheetHM;


		} catch (Exception e) {
			throw e;
		}finally {
			tripSheetHM		= null;
			tempTripDto		= null;
		}
	}
	
	@Override
	public List<TripSheetReportDto> getTripincomeDetailsByIncomeId(ValueObject valueObject)
			throws Exception {
		TripSheetReportDto					tempTripDto				= null;
		List<TripSheetReportDto>			TripSheetReportDtoList 	= null;
		HashMap<String, TripSheetReportDto> 	tripSheetHM					= null;
		double								incomeAmt				= 0;
		try {
			
			
			TripSheetReportDtoList = new ArrayList<>();
			TypedQuery<Object[]> query = null;
			tripSheetHM = new HashMap<>();
			
			if (!companyConfigurationService.getVehicleGroupWisePermission(valueObject.getInt("companyId"), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"Select  TI.tripsheet.tripSheetID , TI.incomeId, TI.tripincomeID, V.vid, V.vehicle_registration, T.tripSheetNumber,INC.incomeName, TI.created , TI.incomeAmount "
								+ " From TripSheetIncome AS TI "
								+ " INNER JOIN TripSheet T ON T.tripSheetID = TI.tripsheet.tripSheetID "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN TripIncome INC ON INC.incomeID = TI.incomeId"
								+ " where TI.incomeAmount > 0 AND TI.incomeAmount is not null  "+ valueObject.getString("query") + ""
								+ " AND TI.companyId = " + valueObject.getInt("companyId")+" AND TI.markForDelete = 0 "
								+ " ORDER BY TI.tripincomeID DESC",
								Object[].class);
			}else {
				query = entityManager.createQuery(
						"Select  TI.tripsheet.tripSheetID , TI.incomeId, TI.tripincomeID ,V.vid, V.vehicle_registration, T.tripSheetNumber, INC.incomeName, TI.created , TI.incomeAmount "
								+ " From TripSheetIncome AS TI "
								+ " INNER JOIN TripSheet T ON T.tripSheetID = TI.tripsheet.tripSheetID "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN TripIncome INC ON INC.incomeID = TI.incomeId"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+valueObject.getInt("usesrID")+""
								+ " where TI.incomeAmount > 0 AND TI.incomeAmount is not null  "+ valueObject.getString("query") + ""
								+ " AND TI.companyId = " + valueObject.getInt("companyId")+" AND TI.markForDelete = 0 "
								+ " ORDER BY TI.tripincomeID DESC",
								Object[].class);
			
				
			
			}
			List<Object[]> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				for (Object[] result : results) {
					
					
					tempTripDto	= tripSheetHM.get((Long) result[0]+"_"+(Integer) result[1]);
					if(tempTripDto == null) {
						tempTripDto = new TripSheetReportDto();
						tempTripDto.setTripSheetId((Long) result[0]);
						tempTripDto.setIncomeId((Integer) result[1]);
						tempTripDto.setTripIncomeId((Long) result[2]);
						tempTripDto.setVid((Integer) result[3]);
						tempTripDto.setVehicleRegistration("<a target='_blank' href='showVehicle?vid="+tempTripDto.getVid()+"'>"+(String) result[4]+"</a> ");
						tempTripDto.setTripSheetNumber("<a target='_blank' href='showTripSheet.in?tripSheetID="+tempTripDto.getTripSheetId()+"'>TS-"+(Long) result[5]+"</a> ");
						tempTripDto.setIncomeName((String) result[6]);
						tempTripDto.setIncomeCreated(dateFormat.format((Timestamp) result[7]));
						
						tempTripDto.setTripIncomeAmount((Double) result[8]);
						
					}else {
						if(tempTripDto.getIncomeId() == null) {
							tempTripDto.setTripIncomeAmount((Double) result[8]);
						}else {
							incomeAmt = (Double) result[8] + tempTripDto.getTripIncomeAmount() ;
						}
						tempTripDto.setTripIncomeAmount(incomeAmt);
					
					}
					
					tripSheetHM.put(tempTripDto.getTripSheetId()+"_"+tempTripDto.getIncomeId(), tempTripDto);
				}

			}
			
			TripSheetReportDtoList.addAll(tripSheetHM.values());
			
			return TripSheetReportDtoList;


		} catch (Exception e) {
			throw e;
		}finally {
			tempTripDto		= null;
		}
	}
	
}
