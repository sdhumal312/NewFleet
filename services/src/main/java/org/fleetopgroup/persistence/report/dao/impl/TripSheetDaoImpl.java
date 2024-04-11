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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.constant.TripsheetDueAmountPaymentTypeConstant;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripsheetDueAmountDto;
import org.fleetopgroup.persistence.report.dao.TripSheetDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TripSheetDaoImpl implements TripSheetDao {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired	ICompanyConfigurationService		companyConfigurationService;

	SimpleDateFormat 					sqldateTime 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 					dateFormat		 		= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 					CreatedDateTime 		= new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	DecimalFormat 						toFixedTwo 				= new DecimalFormat("#.##");
	
	@Override
	public List<TripSheetDto> getTripSheetList(TripSheetDto tripSheetDto) throws Exception {
		List<TripSheetDto>			tripSheetList			= null;		
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, TS.tripOpenDate,TS.closetripDate, TS.routeName"
							+ "	from TripSheet TS "
							+ " where TS.vid = " + tripSheetDto.getVid() 
							+ " AND (TS.tripOpenDate BETWEEN '"+tripSheetDto.getTripOpenDate()+"' AND '"+tripSheetDto.getClosetripDate()+"'"
							+ " OR TS.closetripDate BETWEEN '"+tripSheetDto.getTripOpenDate() +"' AND '"+tripSheetDto.getClosetripDate()+"')"
							+ " AND TS.companyId = "+tripSheetDto.getCompanyId()+" AND TS.markForDelete  = 0 ORDER BY TS.tripSheetID ASC",
							Object[].class);
			
			List<Object[]> results = query.getResultList();

			tripSheetList	= new ArrayList<TripSheetDto>();
			if (results != null && !results.isEmpty()) {
				TripSheetDto tripSheet = null;
				for (Object[] result : results) {
					tripSheet = new TripSheetDto();
					tripSheet.setTripSheetID((Long) result[0]);
					tripSheet.setTripSheetNumber((Long) result[1]);
					tripSheet.setVid((Integer) result[2]);
					tripSheet.setTripOpenDateOn((java.util.Date) result[3]);
					tripSheet.setClosetripDateOn((java.util.Date) result[4]);
					tripSheet.setRouteName((String) result[5]);

					tripSheetList.add(tripSheet);
				}
			}
			
			return tripSheetList;
		} catch (Exception e) {
			throw e;
		} finally {
			tripSheetList		= null;
		}
	}
	
	
	
	//Create Day Wise Expense Report Start
	@Override
	public List<TripSheetDto> getCreateDayWiseExpenseReportList(String query, Integer companyId)
			throws Exception {
		
		try {
			
			TypedQuery<Object[]> queryt = entityManager
					 .createQuery(" SELECT T.tripSheetID, T.tripSheetNumber, T.tripOpenDate, T.closetripDate,"
					 		+ " V.vid, V.vehicle_registration, TSE.expenseAmount, TE.expenseName, TSE.voucherDate , "
					 		+ " D1.driver_firstname, D1.driver_Lastname , D2.driver_firstname, D2.driver_Lastname  "
					 		+ " FROM TripSheet AS T"
					 		+ " INNER JOIN Vehicle as V ON  V.vid = T.vid "
					 		+ " LEFT JOIN TripSheetExpense AS TSE ON TSE.tripsheet.tripSheetID = T.tripSheetID AND TSE.markForDelete = 0"
					 		+ " INNER JOIN TripExpense AS TE ON TE.expenseID = TSE.expenseId "
					 		+ " INNER JOIN Driver AS D1 ON D1.driver_id = T.tripFristDriverID "
					 		+ " LEFT JOIN Driver AS D2 ON D2.driver_id = T.tripSecDriverID "
							+ " WHERE T.companyId = "+companyId+" AND  T.markForDelete = 0 "+ query + " ", Object[].class);
			
			List<Object[]>	results = queryt.getResultList();
			
			List<TripSheetDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					TripSheetDto	expenseInfo = new TripSheetDto();
					
					expenseInfo.setTripSheetID((Long) result[0]);
					if(result[1] != null)
					expenseInfo.setTripSheetNumberStr("TS-"+(Long) result[1]);
					if(result[2] != null)
					expenseInfo.setTripOpenDate(sqldateTime.format((Timestamp) result[2]));
					if(result[3] != null)
					expenseInfo.setClosetripDate(sqldateTime.format((Timestamp) result[3]));
					expenseInfo.setVid((Integer) result[4]);
					if(result[5] != null)
					expenseInfo.setVehicle_registration((String) result[5]);
					if(result[6] != null)
					expenseInfo.setExpenseAmount((Double) result[6]);
					if(result[7] != null)
					expenseInfo.setExpenseName((String) result[7]);
					if(result[8] != null)
						expenseInfo.setTripEndDateTimeStr(sqldateTime.format((Date) result[8]));		
					if(result[9] != null) {
						expenseInfo.setTripFristDriverName((String) result[9]);
						expenseInfo.setTripFristDriverLastName((String) result[10]);
					}
					if(result[11] != null) {
						expenseInfo.setTripSecDriverName((String) result[11]);
						expenseInfo.setTripSecDriverLastName((String) result[12]);
					}
					list.add(expenseInfo);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
		
		
	}	
	//Create Day Wise Expense Report Stop
	
	@Override
	public List<TripSheetDto> getCreateDayWiseFasttagExpenseReportList(String query, Integer companyId)
			throws Exception {
		
		try {
			
			HashMap<String, Object> 	configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TOLL_API_CONFIG);
			
			int expenseId	= (int) configuration.get("TollExpenseId");
			
			TypedQuery<Object[]> queryt = entityManager
					 .createQuery(" SELECT T.tripSheetID, T.tripSheetNumber, T.tripOpenDate, T.closetripDate,"
					 		+ " V.vid, V.vehicle_registration, sum(TSE.transactionAmount), TE.expenseName "
					 		+ " FROM TripSheet AS T "
					 		+ " INNER JOIN TollExpensesDetails TSE ON TSE.tripSheetId = T.tripSheetID"
					 		+ " INNER JOIN Vehicle as V ON  V.vid = T.vid "
					 		+ " INNER JOIN TripExpense AS TE ON TE.expenseID = "+expenseId+" "
							+ " WHERE T.companyId = "+companyId+" AND  T.markForDelete = 0 "+ query + " "
							+ " AND T.tripStutesId >= "+TripSheetStatus.TRIP_STATUS_CLOSED+" group by T.tripSheetID ", Object[].class);
			
			List<Object[]>	results = queryt.getResultList();
			
			List<TripSheetDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					TripSheetDto	expenseInfo = new TripSheetDto();
					
					expenseInfo.setTripSheetID((Long) result[0]);
					if(result[1] != null)
					expenseInfo.setTripSheetNumberStr("TS-"+(Long) result[1]);
					if(result[2] != null)
					expenseInfo.setTripOpenDate(sqldateTime.format((Timestamp) result[2]));
					if(result[3] != null)
						expenseInfo.setClosetripDate(sqldateTime.format((Timestamp) result[3]));
						
					expenseInfo.setVid((Integer) result[4]);
					expenseInfo.setVehicle_registration((String) result[5]);
					expenseInfo.setExpenseAmount((Double) result[6]);
					expenseInfo.setExpenseName((String) result[7]);
					
					list.add(expenseInfo);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
		
		
	}


	@Override
	public List<TripSheetDto> getVoucherDateWiseExpenseReportList(String query, Integer companyId) throws Exception {
		
		try {
			
			TypedQuery<Object[]> queryt = entityManager
					 .createQuery(" SELECT T.tripSheetID, T.tripSheetNumber, T.tripOpenDate, T.closetripDate,"
					 		+ " V.vid, V.vehicle_registration, TSE.expenseAmount, TE.expenseName, TSE.voucherDate "
					 		+ " FROM TripSheet AS T"
					 		+ " INNER JOIN Vehicle as V ON  V.vid = T.vid "
					 		+ " LEFT JOIN TripSheetExpense AS TSE ON TSE.tripsheet.tripSheetID = T.tripSheetID AND TSE.markForDelete = 0"
					 		+ " INNER JOIN TripExpense AS TE ON TE.expenseID = TSE.expenseId "
					 		+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = T.tallyCompanyId "
							+ " WHERE T.companyId = "+companyId+" AND  T.markForDelete = 0 "+ query + " ", Object[].class);
			
			List<Object[]>	results = queryt.getResultList();
			
			List<TripSheetDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					TripSheetDto	expenseInfo = new TripSheetDto();
					
					expenseInfo.setTripSheetID((Long) result[0]);
					if(result[1] != null)
					expenseInfo.setTripSheetNumberStr("TS-"+(Long) result[1]);
					if(result[2] != null)
					expenseInfo.setTripOpenDate(sqldateTime.format((Timestamp) result[2]));
					if(result[3] != null)
					expenseInfo.setClosetripDate(sqldateTime.format((Timestamp) result[3]));
					expenseInfo.setVid((Integer) result[4]);
					if(result[5] != null)
					expenseInfo.setVehicle_registration((String) result[5]);
					if(result[6] != null)
					expenseInfo.setExpenseAmount((Double) result[6]);
					if(result[7] != null)
					expenseInfo.setExpenseName((String) result[7]);
					if(result[8] != null)
						expenseInfo.setTripEndDateTimeStr(sqldateTime.format((Date) result[8]));				
					list.add(expenseInfo);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<TripsheetDueAmountDto> getDueAmountReportList(String query, Integer companyId) {
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT TD.tripsheetDueAmountId, TD.tripSheetID, TD.driver_id, TD.dueAmount, TD.approximateDate, "
						+ " TD.dueDate, TD.remark, D.driver_firstname, D.driver_Lastname, T.tripSheetNumber, "
						+ " TD.balanceAmount, TD.dueStatus ,D.driver_fathername "
						+ " FROM TripsheetDueAmount AS TD "
						+ " INNER JOIN TripSheet T ON T.tripSheetID = TD.tripSheetID "
						+ " INNER JOIN Driver D ON D.driver_id = TD.driver_id "
						+ " WHERE TD.markForDelete = 0 AND TD.companyId = "+companyId+" "+query+" "
						+ " AND TD.dueStatus IN ("+TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_NOT_PAID+" , "
						+ " "+TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_PARTIALLY_PAID+") "
						+ " AND TD.balanceAmount > 0 "
						+ " ORDER BY TD.tripsheetDueAmountId desc ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripsheetDueAmountDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripsheetDueAmountDto>();
			TripsheetDueAmountDto list = null;
			for (Object[] result : results) {
				list = new TripsheetDueAmountDto();

				list.setTripsheetDueAmountId((long) result[0]);
				list.setTripSheetID((long) result[1]);
				list.setDriver_id((int) result[2]);
				if(result[3] != null)
				list.setDueAmount(Double.parseDouble(toFixedTwo.format(result[3])));
				list.setApproximateDateStr(dateFormat.format((java.sql.Timestamp) result[4]));
				list.setDueDateStr(dateFormat.format((java.sql.Timestamp) result[5]));
				list.setRemark((String) result[6]);
				list.setDriver_firstname((String) result[7]);
				if(result[8] != null) {
					list.setDriver_Lastname((String) result[8]);
				}else {
					list.setDriver_Lastname(" ");
				}
				list.setTripSheetNumber((long) result[9]);
				if(result[10] != null)
				list.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[10])));
				list.setDueStatus((short) result[11]);
				if(result[12] != null) 
				list.setDriver_Lastname(list.getDriver_Lastname()+" - "+ result[12]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	@Override
	public TripsheetDueAmountDto getDueAmountPaymentById(long tripsheetDueAmountId, Integer companyId) {
		Query query = null;
		
		query = entityManager.createQuery(
				"SELECT TD.tripsheetDueAmountId, TD.tripSheetID, TD.driver_id, TD.dueAmount, TD.approximateDate, "
						+ " TD.dueDate, TD.remark, D.driver_firstname, D.driver_Lastname, T.tripSheetNumber, "
						+ " T.dispatchedByTime, V.vehicle_registration, TR.routeName, T.created, VG.vGroup, "
						+ " TD.balanceAmount, TD.dueStatus "
						+ " FROM TripsheetDueAmount AS TD "
						+ " INNER JOIN TripSheet T ON T.tripSheetID = TD.tripSheetID "
						+ " INNER JOIN Driver D ON D.driver_id = TD.driver_id "
						+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
						+ " WHERE TD.markForDelete = 0 AND TD.companyId = "+companyId+" "
						+ " AND TD.tripsheetDueAmountId = "+tripsheetDueAmountId+" ORDER BY TD.tripsheetDueAmountId desc");
		
		Object[] result = (Object[]) query.getSingleResult();

		TripsheetDueAmountDto list = null;
		if (result != null) {
				list = new TripsheetDueAmountDto();

				list.setTripsheetDueAmountId((long) result[0]);
				list.setTripSheetID((long) result[1]);
				list.setDriver_id((int) result[2]);
				if(result[3] != null)
				list.setDueAmount(Double.parseDouble(toFixedTwo.format(result[3])));
				list.setApproximateDateStr(dateFormat.format((java.sql.Timestamp) result[4]));
				list.setDueDateStr(dateFormat.format((java.sql.Timestamp) result[5]));
				list.setRemark((String) result[6]);
				list.setDriver_firstname((String) result[7]);
				list.setDriver_Lastname((String) result[8]);
				list.setTripSheetNumber((long) result[9]);
				list.setDispatchedByTimeStr(CreatedDateTime.format((java.util.Date) result[10]));
				list.setVehicle_registration((String) result[11]);
				list.setRouteName((String) result[12]);
				list.setTripsheetCreatedStr(CreatedDateTime.format((java.util.Date) result[13]));
				list.setVehicleGroup((String) result[14]);
				if(result[15] != null)
				list.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[15])));
				list.setDueStatus((short) result[16]);
			}
		
		return list;
	}
	
	@Override
	public List<TripsheetDueAmountDto> getDueAmountPaymentReportList(String query, Integer companyId) throws Exception {
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT TD.tripsheetDueAmountId, TD.tripSheetID, TD.driver_id, TD.dueAmount, TD.approximateDate, "
						+ " TD.dueDate, TD.remark, D.driver_firstname, D.driver_Lastname, T.tripSheetNumber, "
						+ " TD.balanceAmount, TD.dueStatus, TP.paymentModeId, TP.paymentTypeId, TP.paidAmount, "
						+ " TP.paidDate, TP.reference , D.driver_fathername,TP.transactionMode, TP.transactionNo "
						+ " FROM TripsheetDueAmount AS TD "
						+ " INNER JOIN TripsheetDueAmountPayment TP ON TP.tripsheetDueAmountId = TD.tripsheetDueAmountId "
						+ " INNER JOIN TripSheet T ON T.tripSheetID = TD.tripSheetID "
						+ " INNER JOIN Driver D ON D.driver_id = TD.driver_id "
						+ " WHERE TD.markForDelete = 0 AND TD.companyId = "+companyId+" "+query+" "
						+ " AND TD.dueStatus IN ("+TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_PARTIALLY_PAID+" , "
						+ " "+TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_NEGOTIABLE_PAID+", "
						+ " "+TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_PAID+") "
						+ " ORDER BY TD.tripsheetDueAmountId desc ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripsheetDueAmountDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripsheetDueAmountDto>();
			TripsheetDueAmountDto list = null;
			for (Object[] result : results) {
				list = new TripsheetDueAmountDto();

				list.setTripsheetDueAmountId((long) result[0]);
				list.setTripSheetID((long) result[1]);
				list.setDriver_id((int) result[2]);
				if(result[3] != null)
				list.setDueAmount(Double.parseDouble(toFixedTwo.format(result[3])));
				list.setApproximateDateStr(dateFormat.format((java.sql.Timestamp) result[4]));
				list.setDueDateStr(dateFormat.format((java.sql.Timestamp) result[5]));
				list.setRemark((String) result[6]);
				list.setDriver_firstname((String) result[7]);
				if(result[8] != null) {
					list.setDriver_Lastname((String) result[8]);
				}else {
					list.setDriver_Lastname(" ");
				}
				list.setTripSheetNumber((long) result[9]);
				if(result[10] != null)
				list.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[10])));
				list.setDueStatus((short) result[11]);
				list.setPaymentModeId((short) result[12]);
				list.setPaymentModeName(TripsheetDueAmountPaymentTypeConstant.getPaymentMode(list.getPaymentModeId()));
				list.setPaymentTypeId((short) result[13]);
				list.setPaymentTypeName(TripsheetDueAmountPaymentTypeConstant.getPaymentType(list.getPaymentTypeId()));
				if(result[14] != null)
				list.setPaidAmount(Double.parseDouble(toFixedTwo.format(result[14])));
				list.setPaidDateStr(dateFormat.format((java.sql.Timestamp) result[15]));
				
				if(result[16] != null) {
					list.setReference((String) result[16]);
				} else {
					list.setReference("-");
				}
				if(result[17] != null)
				list.setDriver_Lastname(list.getDriver_Lastname()+" - "+result[17]);
				
				if(result[18] != null) {
					list.setTransactionMode((Short) result[18]);
				}else {
					list.setTransactionMode((short) 0);
				}
				if(list.getTransactionMode() != 0)
				list.setTransactionName(PaymentTypeConstant.getPaymentTypeName(list.getTransactionMode()));
				list.setTransactionNo((String) result[19]);
				
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
}
