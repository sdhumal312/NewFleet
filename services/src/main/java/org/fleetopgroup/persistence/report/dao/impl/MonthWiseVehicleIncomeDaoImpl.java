package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dto.VehicleProfitAndLossReportDto;
import org.fleetopgroup.persistence.model.MonthWiseVehicleIncome;
import org.fleetopgroup.persistence.report.dao.MonthWiseVehicleIncomeDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonthWiseVehicleIncomeDaoImpl implements MonthWiseVehicleIncomeDao {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@PersistenceContext	EntityManager entityManager;
	

	@Override
	@Transactional
	public void deleteMonthWiseVehicleIncome(Integer vid, Timestamp startDate, Integer companyId, Integer incomeId,
			Double incomeAmount) throws Exception {
		 
	    try {
		      entityManager.createNativeQuery(
		        "UPDATE MonthWiseVehicleIncome SET incomeAmount = incomeAmount - " + incomeAmount + " " + 
		        " where startDateOfMonth = '" + DateTimeUtility.getFirstDayOfMonth(startDate) + "' AND vid=" + vid + 
		        " AND incomeId = "+incomeId+" AND companyId = " +companyId+ " AND markForDelete=0")
		        .executeUpdate();
		    } catch (Exception e) {
		      LOGGER.error("Exception " + e);
		    } 
		  
	}
	
	@Override
	public List<VehicleProfitAndLossReportDto> getMonthIncomeDetailsByVid(Integer vid, Timestamp startDateOfMonth,
			Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT MVI.vid, MVI.incomeAmount, MVI.incomeType, MVI.incomeId, MVI.startDateOfMonth, V.vehicle_registration,"
							+ " TI.incomeName "
							+ " FROM MonthWiseVehicleIncome MVI "
							+ " INNER JOIN Vehicle V ON V.vid = MVI.vid "
							+ " LEFT JOIN TripIncome TI ON TI.incomeID = MVI.incomeId "
							+ " where MVI.vid = "+vid+" AND MVI.startDateOfMonth = '"+startDateOfMonth+"' AND MVI.companyId = "+companyId+" AND MVI.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<VehicleProfitAndLossReportDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					VehicleProfitAndLossReportDto	incomeDto = new VehicleProfitAndLossReportDto();
					
					incomeDto.setVid((Integer) result[0]);
					incomeDto.setIncomeAmount((Double) result[1]);
					incomeDto.setIncomeType((short) result[2]);
					incomeDto.setIncomeId((Integer) result[3]);
					incomeDto.setStartDateOfMonth((Timestamp) result[4]);
					incomeDto.setVehicle_registration((String) result[5]);
					incomeDto.setIncomeName((String) result[6]);
					
					list.add(incomeDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleProfitAndLossReportDto> getMonthIncomeDetailsByVehicleGroupId(long vehicleGroupId,
			Timestamp startDateOfMonth, Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT MVI.vid, MVI.incomeAmount, MVI.incomeType, MVI.incomeId, MVI.startDateOfMonth, V.vehicle_registration,"
							+ " TI.incomeName "
							+ " FROM MonthWiseVehicleIncome MVI "
							+ " INNER JOIN Vehicle V ON V.vid = MVI.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId "
							+ " LEFT JOIN TripIncome TI ON TI.incomeID = MVI.incomeId "
							+ " where VG.gid = "+vehicleGroupId+" AND MVI.startDateOfMonth = '"+startDateOfMonth+"' AND MVI.companyId = "+companyId+" AND MVI.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<VehicleProfitAndLossReportDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					VehicleProfitAndLossReportDto	incomeDto = new VehicleProfitAndLossReportDto();
					
					incomeDto.setVid((Integer) result[0]);
					incomeDto.setIncomeAmount((Double) result[1]);
					incomeDto.setIncomeType((short) result[2]);
					incomeDto.setIncomeId((Integer) result[3]);
					incomeDto.setStartDateOfMonth((Timestamp) result[4]);
					incomeDto.setVehicle_registration((String) result[5]);
					incomeDto.setIncomeName((String) result[6]);
					
					list.add(incomeDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public MonthWiseVehicleIncome validateMonthWiseVehicleIncome(Integer vid, String	startDate, Integer companyId,
			short incomeType) throws Exception {

		TypedQuery<MonthWiseVehicleIncome> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM MonthWiseVehicleIncome AS T where vid = "+vid+" AND startDateOfMonth = '"+startDate+"' AND companyId = "+companyId+" AND incomeType = "+incomeType+" AND markForDelete = 0", MonthWiseVehicleIncome.class);

		
		MonthWiseVehicleIncome	dateWiseVehicleExpense = null;
		try {
			query.setMaxResults(1);
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return dateWiseVehicleExpense;
	}

	@Override
	public MonthWiseVehicleIncome validateMonthWiseVehicleIncome(Integer vid, String	startDate, Integer companyId,
			Integer incomeId, short type) throws Exception {
		
		TypedQuery<MonthWiseVehicleIncome> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM MonthWiseVehicleIncome AS T where vid = "+vid+" AND startDateOfMonth = '"+startDate+"' AND companyId = "+companyId+" AND incomeId = "+incomeId+" AND incomeType = "+type+" AND markForDelete = 0", MonthWiseVehicleIncome.class);

		
		MonthWiseVehicleIncome	dateWiseVehicleExpense = null;
		try {
			query.setMaxResults(1);
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return dateWiseVehicleExpense;
	}
}
