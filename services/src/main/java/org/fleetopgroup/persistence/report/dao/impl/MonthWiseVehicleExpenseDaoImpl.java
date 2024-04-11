package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.model.MonthWiseVehicleExpense;
import org.fleetopgroup.persistence.report.dao.MonthWiseVehicleExpenseDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonthWiseVehicleExpenseDaoImpl implements MonthWiseVehicleExpenseDao {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	@Transactional
	public synchronized void subtractPreviousMonthAmount(MonthWiseVehicleExpense		monthWiseVehicleExpense, Timestamp	timestamp, Double amount) throws Exception {
		try {
			
			entityManager.createNativeQuery(
					"UPDATE MonthWiseVehicleExpense SET expenseAmount = expenseAmount - "+ amount+" "
							+ " where startDateOfMonth = '" + DateTimeUtility.getDateFromTimeStamp(timestamp, DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + monthWiseVehicleExpense.getVid()
							+ " AND companyId = " + monthWiseVehicleExpense.getCompanyId() + " AND expenseType = "+monthWiseVehicleExpense.getExpenseType()+" AND markForDelete=0")
			.executeUpdate();
			} catch (Exception e) {
			LOGGER.error("Exception "+e);
		}
		
	}
	
	@Override
	@Transactional
	public synchronized void subtractPreviousMonthAmount(MonthWiseVehicleExpense monthWiseVehicleExpense) throws Exception {
		try {
			
			entityManager.createNativeQuery(
					"UPDATE MonthWiseVehicleExpense SET expenseAmount = expenseAmount - "+ monthWiseVehicleExpense.getExpenseAmount()+" "
							+ " where startDateOfMonth = '" + DateTimeUtility.getDateFromTimeStamp(monthWiseVehicleExpense.getStartDateOfMonth(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + monthWiseVehicleExpense.getVid()
							+ " AND expenseId = "+monthWiseVehicleExpense.getExpenseId()+" AND companyId = " + monthWiseVehicleExpense.getCompanyId() + " AND expenseType = "+monthWiseVehicleExpense.getExpenseType()+" AND markForDelete=0")
			.executeUpdate();
			} catch (Exception e) {
			LOGGER.error("Exception "+e);
		}
		
	}
	
	@Override
	public List<MonthWiseVehicleExpenseDto> getMonthWiseVehicleExpenseForReport(Integer vid, Timestamp startDateOfMonth,
			Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT MVE.monthWiseVehicleExpenseId, MVE.vid, MVE.expenseAmount, MVE.expenseType, MVE.startDateOfMonth,V.vehicle_registration,"
							+ " TE.expenseName, MVE.expenseId  "
							+ " FROM MonthWiseVehicleExpense MVE "
							+ " INNER JOIN Vehicle V ON V.vid = MVE.vid "
							+ " LEFT JOIN TripExpense TE ON TE.expenseID = MVE.expenseId AND MVE.expenseType = "+VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET+""
							+ " where MVE.vid = "+vid+" AND MVE.startDateOfMonth = '"+ DateTimeUtility.getDateFromTimeStamp(startDateOfMonth, DateTimeUtility.YYYY_MM_DD_HH_MM_SS) +"' AND MVE.companyId = "+companyId+" AND MVE.expenseType <> "+VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI+"  AND MVE.markForDelete= 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<MonthWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					MonthWiseVehicleExpenseDto	mExpenseDto = new MonthWiseVehicleExpenseDto();
					
					mExpenseDto.setMonthWiseVehicleExpenseId((Long) result[0]);
					mExpenseDto.setVid((Integer) result[1]);
					mExpenseDto.setExpenseAmount((Double) result[2]);
					mExpenseDto.setExpenseType((short) result[3]);
					mExpenseDto.setStartDateOfMonth((Timestamp) result[4]);
					mExpenseDto.setVehicle_registration((String) result[5]);
					mExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.getExpenseTypeName((short) result[3]));
					mExpenseDto.setTripExpenseName((String) result[6]);
					mExpenseDto.setExpenseId((Integer) result[7]);
					
					if(mExpenseDto.getTripExpenseName() == null) {
						mExpenseDto.setTripExpenseName(VehicleExpenseTypeConstant.getExpenseTypeName((short) result[3]));
					}
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public List<MonthWiseVehicleExpenseDto> getMonthWiseGroupExpenseForReport(long vehicleGroupId, Timestamp startDateOfMonth, Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT MVE.monthWiseVehicleExpenseId, MVE.vid, MVE.expenseAmount, MVE.expenseType, MVE.startDateOfMonth,V.vehicle_registration,"
							+ " TE.expenseName, MVE.expenseId, V.driverMonthlySalary   "
							+ " FROM MonthWiseVehicleExpense MVE "
							+ " INNER JOIN Vehicle V ON V.vid = MVE.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripExpense TE ON TE.expenseID = MVE.expenseId AND MVE.expenseType = "+VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET+""
							+ " where VG.gid = "+vehicleGroupId+" AND MVE.startDateOfMonth = '"+DateTimeUtility.getDateFromTimeStamp(startDateOfMonth, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' AND MVE.expenseType <> "+VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI+" AND MVE.companyId = "+companyId+" AND MVE.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<MonthWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					MonthWiseVehicleExpenseDto	mExpenseDto = new MonthWiseVehicleExpenseDto();
					
					mExpenseDto.setMonthWiseVehicleExpenseId((Long) result[0]);
					mExpenseDto.setVid((Integer) result[1]);
					mExpenseDto.setExpenseAmount((Double) result[2]);
					mExpenseDto.setExpenseType((short) result[3]);
					mExpenseDto.setStartDateOfMonth((Timestamp) result[4]);
					mExpenseDto.setVehicle_registration((String) result[5]);
					mExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.getExpenseTypeName((short) result[3]));
					mExpenseDto.setTripExpenseName((String) result[6]);
					mExpenseDto.setExpenseId((Integer) result[7]);
					if((Double) result[8] != null) {
						mExpenseDto.setDriverMonthlySalary((Double) result[8]);
					}
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public MonthWiseVehicleExpense validateMonthWiseVehicleExpense(Integer vid, String startDate, Integer companyId,
			short expenseType) throws Exception {

		TypedQuery<MonthWiseVehicleExpense> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM MonthWiseVehicleExpense AS T"
						+ " where startDateOfMonth = '"+startDate+"' AND expenseType = "+expenseType+" AND companyId = "+companyId+" AND vid = "+vid+" AND markForDelete = 0", MonthWiseVehicleExpense.class);

		
		MonthWiseVehicleExpense	dateWiseVehicleExpense = null;
		try {
			query.setMaxResults(1);
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return dateWiseVehicleExpense;
	}
	
	@Override
	public MonthWiseVehicleExpense validateMonthWiseVehicleExpense(Integer vid, String startDate, Integer companyId,
			Integer expenseId, short expenseType) throws Exception {

		TypedQuery<MonthWiseVehicleExpense> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM MonthWiseVehicleExpense AS T"
						+ " where startDateOfMonth = '"+startDate+"' AND expenseId = "+expenseId+" AND companyId = "+companyId+" AND vid = "+vid+" AND expenseType = "+expenseType+" AND markForDelete = 0", MonthWiseVehicleExpense.class);

		
		MonthWiseVehicleExpense	dateWiseVehicleExpense = null;
		try {
			query.setMaxResults(1);
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return dateWiseVehicleExpense;
	}
	
	@Override
	public MonthWiseVehicleExpense getMonthWiseVehicleExpense(Integer vid, String startDate, Integer companyId,
			Integer expenseId, short expenseType) throws Exception {

		TypedQuery<MonthWiseVehicleExpense> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM MonthWiseVehicleExpense AS T"
						+ " where vid = "+vid+" AND startDateOfMonth = '"+startDate+"' AND companyId = "+companyId+" AND expenseId = "+expenseId+"  AND expenseType = "+expenseType+" AND markForDelete = 0", MonthWiseVehicleExpense.class);

		
		MonthWiseVehicleExpense	dateWiseVehicleExpense = null;
		try {
			query.setMaxResults(1);
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return dateWiseVehicleExpense;
	}
	
	@Override
	public MonthWiseVehicleExpense getMonthWiseVehicleExpense(Integer vid, String startDate, Integer companyId,
			short expenseType) throws Exception {

		TypedQuery<MonthWiseVehicleExpense> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM MonthWiseVehicleExpense AS T"
						+ " where vid = "+vid+" AND startDateOfMonth = '"+startDate+"' AND companyId = "+companyId+" AND expenseType = "+expenseType+" AND markForDelete = 0", MonthWiseVehicleExpense.class);

		
		MonthWiseVehicleExpense	dateWiseVehicleExpense = null;
		try {
			query.setMaxResults(1);
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return dateWiseVehicleExpense;
	}
}
