package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.model.MonthWiseVehicleBalance;
import org.fleetopgroup.persistence.report.dao.MonthWiseVehicleBalanceDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MonthWiseVehicleBalanceDaoImpl implements MonthWiseVehicleBalanceDao{
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	@PersistenceContext	EntityManager entityManager;
	

	@Override
	@Transactional
	public void updateMonthBalance(ValueObject valueObject) throws Exception {
		MonthWiseVehicleBalance		monthWiseVehicleBalance		= null;
		Double						amount						= 0.0;
		try {
			
			monthWiseVehicleBalance	= (MonthWiseVehicleBalance) valueObject.get("monthWiseVehicleBalance");
			amount					= valueObject.getDouble("amount", 0);
			entityManager.createNativeQuery(
					"UPDATE MonthWiseVehicleBalance SET monthBalance = monthBalance + "+ - amount+" "
							+ " where monthStartDate > '" +DateTimeUtility.getDateFromTimeStamp(monthWiseVehicleBalance.getMonthStartDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + monthWiseVehicleBalance.getVid()
							+ " AND companyId = " + monthWiseVehicleBalance.getCompanyId() + " AND markForDelete=0")
			.executeUpdate();
			
		} catch (Exception e) {
			LOGGER.error("exception ", e);
		}
		
	}
	
	@Override
	@Transactional
	public synchronized void subtractAddAmountForPreviousFuelDate(MonthWiseVehicleBalance monthWiseVehicleBalanceDao,
			Double amount, boolean isAlldayAmountUpdateNeeded, Timestamp date) throws Exception {
	    try
	    {
	      if (!isAlldayAmountUpdateNeeded)
	      {

	        entityManager.createNativeQuery("UPDATE MonthWiseVehicleBalance SET balanceAmount = balanceAmount + " + amount + " , totalExpenseAmount = totalExpenseAmount - " + amount + " " + " where monthStartDate = '" + DateTimeUtility.getDateFromTimeStamp(date, DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + monthWiseVehicleBalanceDao.getVid() + " AND companyId = " + monthWiseVehicleBalanceDao.getCompanyId() + " AND markForDelete=0").executeUpdate();

	      }
	      else
	      {

	        entityManager.createNativeQuery("UPDATE MonthWiseVehicleBalance SET balanceAmount = balanceAmount + " + amount + " , totalExpenseAmount = totalExpenseAmount - " + amount + " " + ", monthBalance = monthBalance + " + amount + " where monthStartDate = '" + DateTimeUtility.getDateFromTimeStamp(date, DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + monthWiseVehicleBalanceDao.getVid() + " AND companyId = " + monthWiseVehicleBalanceDao.getCompanyId() + " AND markForDelete=0").executeUpdate();
	      }
	    }
	    catch (Exception e) {
	      LOGGER.error("Exception " ,  e);
	    }
	  }
	
	@Override
	@Transactional
	public void updateAllDayBalanceForBackDateEdit(MonthWiseVehicleBalance monthWiseVehicleBalance, Double amount,
			Timestamp previousDate) throws Exception { 
	    try {
	    	
		      entityManager.createNativeQuery(
		        "UPDATE MonthWiseVehicleBalance SET monthBalance = monthBalance + " + -amount + " " + 
		        " where monthStartDate > '" + monthWiseVehicleBalance.getMonthStartDate() + "' AND monthStartDate < '"+DateTimeUtility.getDateFromTimeStamp(previousDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' AND vid=" + monthWiseVehicleBalance.getVid() + 
		        " AND companyId = " + monthWiseVehicleBalance.getCompanyId() + " AND markForDelete=0")
		        .executeUpdate();
		    } catch (Exception e) {
		      LOGGER.error("Exception " , e);
		    } 
	}
	
	@Override
	@Transactional
	public void updateCurrentNextDateAllDayBalanceAmount(ValueObject valueObject) throws Exception { 
		MonthWiseVehicleBalance monthWiseVehicleBalance = null;
  	    Double amount = Double.valueOf(0.0D);
  	    Timestamp	previousFuelDate	= null;
  	    try {
  	    	
  	      monthWiseVehicleBalance 	= (MonthWiseVehicleBalance)valueObject.get("monthWiseVehicleBalance");
  	      amount 					= Double.valueOf(valueObject.getDouble("amount", 0.0D));
  	      previousFuelDate			= (Timestamp) valueObject.get("previousFuelDate");
  	      entityManager.createNativeQuery(
  	        "UPDATE MonthWiseVehicleBalance SET monthBalance = monthBalance + " + -amount.doubleValue() + " " + 
  	        " where monthStartDate >= '" + DateTimeUtility.getDateFromTimeStamp(previousFuelDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + monthWiseVehicleBalance.getVid() + 
  	        " AND companyId = " + monthWiseVehicleBalance.getCompanyId() + " AND markForDelete=0")
  	        .executeUpdate();
  	    } catch (Exception e) {
  	      LOGGER.error("Exception " , e);
  	    } finally {
  	    	monthWiseVehicleBalance = null;
  	      amount = Double.valueOf(0.0D);
  	    }
  	  }

	
	@Override
	@Transactional
	public void updateAllDayBalanceForNextDateEdit(MonthWiseVehicleBalance dateWiseVehicleBalance, Double amount,
			Timestamp previousDate, Timestamp fuelDate) throws Exception { 
	    try {
	    	
		      entityManager.createNativeQuery(
		        "UPDATE MonthWiseVehicleBalance SET monthBalance = monthBalance + " + amount + " " + 
		        " where monthStartDate >= '" + DateTimeUtility.getDateFromTimeStamp(previousDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND monthStartDate <= '"+DateTimeUtility.getDateFromTimeStamp(fuelDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' AND vid=" + dateWiseVehicleBalance.getVid() + 
		        " AND companyId = " + dateWiseVehicleBalance.getCompanyId() + " AND markForDelete=0")
		        .executeUpdate();
		    } catch (Exception e) {
		      LOGGER.error("Exception " , e);
		    } 
		}
	
	@Override
	@Transactional
	public void updateNextDateAllDayBalanceAmount(ValueObject valueObject) throws Exception { 
		MonthWiseVehicleBalance dateWiseVehicleBalance = null;
	    Double amount = Double.valueOf(0.0D);
	    try {
	      dateWiseVehicleBalance = (MonthWiseVehicleBalance)valueObject.get("monthWiseVehicleBalance");
	      
	      amount = Double.valueOf(valueObject.getDouble("amount", 0.0D));
	      entityManager.createNativeQuery(
	        "UPDATE MonthWiseVehicleBalance SET monthBalance = monthBalance + " + -amount.doubleValue() + " " + 
	        " where monthStartDate > '" + DateTimeUtility.getDateFromTimeStamp(dateWiseVehicleBalance.getMonthStartDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + dateWiseVehicleBalance.getVid() + 
	        " AND companyId = " + dateWiseVehicleBalance.getCompanyId() + " AND markForDelete=0")
	        .executeUpdate();
	    } catch (Exception e) {
	      LOGGER.error("Exception " + e);
	    } finally {
	      dateWiseVehicleBalance = null;
	      amount = Double.valueOf(0.0D);
	    }
	  }
	
	@Override
	@Transactional
	public synchronized void addNextDateAllDayBalanceAmount(ValueObject valueObject) throws Exception { 
		MonthWiseVehicleBalance dateWiseVehicleBalance = null;
	    Double amount = Double.valueOf(0.0D);
	    try {
	      dateWiseVehicleBalance = (MonthWiseVehicleBalance)valueObject.get("monthWiseVehicleBalance");
	      
	      amount = Double.valueOf(valueObject.getDouble("amount", 0.0D));
	      entityManager.createNativeQuery(
	        "UPDATE MonthWiseVehicleBalance SET monthBalance = monthBalance + " + amount.doubleValue() + " " + 
	        " where monthStartDate > '" + DateTimeUtility.getDateFromTimeStamp(dateWiseVehicleBalance.getMonthStartDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + dateWiseVehicleBalance.getVid() + 
	        " AND companyId = " + dateWiseVehicleBalance.getCompanyId() + " AND markForDelete=0")
	        .executeUpdate();
	    } catch (Exception e) {
	      LOGGER.error("Exception " + e);
	    } finally {
	      dateWiseVehicleBalance = null;
	      amount = Double.valueOf(0.0D);
	    }
	  }
	
	@Override
	@Transactional
	public void updateMonthWiseVehicleBalanceUpdated(String txnids) throws Exception {
		try {
			 entityManager.createNativeQuery(
			        "UPDATE VehicleProfitAndLossTxnChecker SET isMonthWiseVehicleBalanceUpdated = 1 where vehicleProfitAndLossTxnCheckerId IN ("+txnids+")")
			        .executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Exception " + e);
		}
		
	}
	
	@Override
	public MonthWiseVehicleBalance validateMonthWiseVehicleBalance(Integer vid, String firstDate, Integer companyId)
			throws Exception {

			TypedQuery<MonthWiseVehicleBalance> query = entityManager.createQuery(
					"SELECT T "
					+ " FROM MonthWiseVehicleBalance AS T"
							+ " where vid = "+vid+" AND monthStartDate = '"+firstDate+"' AND companyId = "+companyId+" AND markForDelete = 0", MonthWiseVehicleBalance.class);

			
			MonthWiseVehicleBalance	dateWiseVehicleExpense = null;
			try {
				query.setMaxResults(1);
				dateWiseVehicleExpense = query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			return dateWiseVehicleExpense;
		}
	
	@Override
	public MonthWiseVehicleBalance getLastMonthWiseVehicleBalance(Integer vid, String firstDate, Integer companyId)
			throws Exception {

		TypedQuery<MonthWiseVehicleBalance> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM MonthWiseVehicleBalance AS T"
						+ " where vid = "+vid+" AND monthStartDate < '"+firstDate+"'  AND companyId = "+companyId+" AND markForDelete = 0 ORDER BY monthWiseVehicleBalanceId DESC", MonthWiseVehicleBalance.class);

		MonthWiseVehicleBalance	dateWiseVehicleExpense = null;
		try {
			query.setMaxResults(1);
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return dateWiseVehicleExpense;
	}
}
