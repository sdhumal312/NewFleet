package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.MonthWiseVehicleExpense;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.report.dao.MothWiseVehicleIncomeBalanceDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MothWiseVehicleIncomeBalanceDaoImpl implements MothWiseVehicleIncomeBalanceDao {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@PersistenceContext	EntityManager entityManager;
	  
	
	@Override
	@Transactional
	public void updateMonthBalanceAmount(VehicleProfitAndLossDto profitAndLossDto) throws Exception { 
	    try {
		      entityManager.createNativeQuery(
		        "UPDATE MothWiseVehicleIncomeBalance SET totalBalanceIncome = totalBalanceIncome + " + profitAndLossDto.getTxnAmount() + " " + 
		        " where startDateOfMonth > '" + DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getTransactionDateTime()) + "' AND vid=" + profitAndLossDto.getVid() + 
		        " AND companyId = " + profitAndLossDto.getCompanyId() + " AND markForDelete=0")
		        .executeUpdate();
		    } catch (Exception e) {
		      LOGGER.error("Exception " + e);
		    } 
	 }
	
	@Override
	@Transactional
	public void updateMonthWiseVehicleBalanceUpdated(String txnids) throws Exception {
		try {
			 entityManager.createNativeQuery(
			        "UPDATE VehicleProfitAndLossIncomeTxnChecker SET isMonthWiseVehicleBalanceUpdated = 1 where vehicleProfitAndLossTxnCheckerId IN ("+txnids+")")
			        .executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Exception " + e);
		}
		
	}
	
	@Override
	public void deleteMothWiseVehicleIncomeBalance(Integer vid, Timestamp startDate, Double amount) throws Exception { 
	    try {
		      entityManager.createNativeQuery(
		        "UPDATE MothWiseVehicleIncomeBalance SET totalBalanceIncome = totalBalanceIncome - " + amount + ", totalIncome =  totalIncome - "+amount+" " + 
		        " where startDateOfMonth = '" + DateTimeUtility.getFirstDayOfMonth(startDate) + "' AND vid=" + vid + 
		        "  AND markForDelete=0")
		        .executeUpdate();
		    } catch (Exception e) {
		      LOGGER.error("Exception " + e);
		    } 
	 }
	
	@Override
	@Transactional
	public void updateMonthBalanceAmount(Integer vid, Timestamp startDate, Double amount) throws Exception { 
	    try {
		      entityManager.createNativeQuery(
		        "UPDATE MothWiseVehicleIncomeBalance SET totalBalanceIncome = totalBalanceIncome - " + amount + " " + 
		        " where startDateOfMonth > '" + DateTimeUtility.getFirstDayOfMonth(startDate) + "' AND vid=" + vid + 
		        " AND markForDelete=0")
		        .executeUpdate();
		    } catch (Exception e) {
		      LOGGER.error("Exception " , e);
		    } 
	 }
	
	@Override
	public VehicleProfitAndLossTxnChecker getVehicleProfitAndLossTxnChecker(Long vehicleProfitAndLossTxnCheckerId)
			throws Exception {
		

		TypedQuery<VehicleProfitAndLossTxnChecker> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM VehicleProfitAndLossTxnChecker AS T"
						+ " where T.vehicleProfitAndLossTxnCheckerId = "+vehicleProfitAndLossTxnCheckerId+" ", VehicleProfitAndLossTxnChecker.class);

		
		VehicleProfitAndLossTxnChecker	dateWiseVehicleExpense = null;
		try {
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			System.err.println("nre"+nre);
		}

		return dateWiseVehicleExpense;
	}
}
