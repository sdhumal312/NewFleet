package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.model.DateWiseVehicleBalance;
import org.fleetopgroup.persistence.report.dao.DateWiseVehicleBalanceDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DateWiseVehicleBalanceDaoImpl
  implements DateWiseVehicleBalanceDao
{
  private final Logger LOGGER = LoggerFactory.getLogger(getClass());
  
  @PersistenceContext
  EntityManager entityManager;
  
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  
  public DateWiseVehicleBalanceDaoImpl() {}
  
  @Transactional
  public void updateNextDateAllDayBalanceAmount(ValueObject valueObject) throws Exception { 
	DateWiseVehicleBalance dateWiseVehicleBalance = null;
    Double amount = Double.valueOf(0.0D);
    
    
    
    try {
      dateWiseVehicleBalance = (DateWiseVehicleBalance)valueObject.get("dateWiseVehicleBalance");
      amount = Double.valueOf(valueObject.getDouble("amount", 0.0D));
      entityManager.createNativeQuery(
        "UPDATE DateWiseVehicleBalance SET AllDayBalanceAmount = AllDayBalanceAmount + " + -amount.doubleValue() + " " + 
        " where transactionDate > '" + DateTimeUtility.getDateFromTimeStamp(dateWiseVehicleBalance.getTransactionDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + dateWiseVehicleBalance.getVid() + 
        " AND companyId = " + dateWiseVehicleBalance.getCompanyId() + " AND markForDelete=0")
        .executeUpdate();
    } catch (Exception e) {
    	
      LOGGER.error("Exception " + e);
    } finally {
      dateWiseVehicleBalance = null;
      amount = Double.valueOf(0.0D);
    }
  }
  
  @Transactional
  public synchronized void subtractAddAmountForPreviousFuelDate(DateWiseVehicleBalance dateWiseVehicleBalance, Double amount, boolean isAlldayAmountUpdateNeeded, Timestamp date) throws Exception
  {		
    try
    {
      if (!isAlldayAmountUpdateNeeded)
      {

        entityManager.createNativeQuery("UPDATE DateWiseVehicleBalance SET dayBalanceAmount = dayBalanceAmount + " + amount + " , totalExpenseAmount = totalExpenseAmount - " + amount + " " + " where transactionDate = '" + DateTimeUtility.getDateFromTimeStamp(date, DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + dateWiseVehicleBalance.getVid() + " AND companyId = " + dateWiseVehicleBalance.getCompanyId() + " AND markForDelete=0").executeUpdate();

      }
      else
      {

        entityManager.createNativeQuery("UPDATE DateWiseVehicleBalance SET dayBalanceAmount = dayBalanceAmount + " + amount + " , totalExpenseAmount = totalExpenseAmount - " + amount + " " + ", AllDayBalanceAmount = AllDayBalanceAmount + " + amount + " where transactionDate = '" + DateTimeUtility.getDateFromTimeStamp(date, DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + dateWiseVehicleBalance.getVid() + " AND companyId = " + dateWiseVehicleBalance.getCompanyId() + " AND markForDelete=0").executeUpdate();
      }
    }
    catch (Exception e) {
      LOGGER.error("Exception " + e);
    }
  }
  
  @Override
  @Transactional
	public void addNextDayAmounts(DateWiseVehicleBalance dateWiseVehicleBalance, Double amount) throws Exception { 
	    try {
	    	
	    	
	      entityManager.createNativeQuery(
	        "UPDATE DateWiseVehicleBalance SET totalExpenseAmount = totalExpenseAmount + " + amount + ", dayBalanceAmount = dayBalanceAmount - "+amount+" " + 
	        " where transactionDate = '" + DateTimeUtility.getDateFromTimeStamp(dateWiseVehicleBalance.getTransactionDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + dateWiseVehicleBalance.getVid() + 
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
	public void updateCurrentDateAllDayBalance(DateWiseVehicleBalance	dateWiseVehicleBalance, Double amount) throws Exception { 
	    try {
	    	
	      entityManager.createNativeQuery(
	        "UPDATE DateWiseVehicleBalance SET AllDayBalanceAmount = AllDayBalanceAmount + " + -amount + " " + 
	        " where transactionDate = '" + DateTimeUtility.getDateFromTimeStamp(dateWiseVehicleBalance.getTransactionDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + dateWiseVehicleBalance.getVid() + 
	        " AND companyId = " + dateWiseVehicleBalance.getCompanyId() + " AND markForDelete=0")
	        .executeUpdate();
	    } catch (Exception e) {
	      LOGGER.error("Exception " + e);
	    } 
	  }
  
  	@Override
  	@Transactional
	public void updateAllDayBalanceForBackDateEdit(DateWiseVehicleBalance	dateWiseVehicleBalance, Double amount, Timestamp previousDate)
			throws Exception { 
	    try {
	    	
		      entityManager.createNativeQuery(
		        "UPDATE DateWiseVehicleBalance SET AllDayBalanceAmount = AllDayBalanceAmount + " + -amount + " " + 
		        " where transactionDate > '" + dateWiseVehicleBalance.getTransactionDate() + "' AND transactionDate < '"+DateTimeUtility.getDateFromTimeStamp(previousDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' AND vid=" + dateWiseVehicleBalance.getVid() + 
		        " AND companyId = " + dateWiseVehicleBalance.getCompanyId() + " AND markForDelete=0")
		        .executeUpdate();
		    } catch (Exception e) {
		      LOGGER.error("Exception " + e);
		    } 
		  }
  	
  	@Override
  	@Transactional
  		public void updateAllDayBalanceForNextDateEdit(DateWiseVehicleBalance dateWiseVehicleBalance, Double amount,
  				Timestamp previousDate, Timestamp fuelDate) throws Exception { 
	    try {
	    	
		      entityManager.createNativeQuery(
		        "UPDATE DateWiseVehicleBalance SET AllDayBalanceAmount = AllDayBalanceAmount + " + amount + " " + 
		        " where transactionDate >= '" + previousDate + "' AND transactionDate <= '"+DateTimeUtility.getDateFromTimeStamp(fuelDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' AND vid=" + dateWiseVehicleBalance.getVid() + 
		        " AND companyId = " + dateWiseVehicleBalance.getCompanyId() + " AND markForDelete=0")
		        .executeUpdate();
		    } catch (Exception e) {
		      LOGGER.error("Exception " + e);
		    } 
		}
  	
  	@Override
  	@Transactional
  		public void updateCurrentNextDateAllDayBalanceAmount(ValueObject valueObject) throws Exception { 
  		DateWiseVehicleBalance dateWiseVehicleBalance = null;
  	    Double amount = Double.valueOf(0.0D);
  	    Timestamp	previousFuelDate	= null;
  	    try {
  	      dateWiseVehicleBalance 	= (DateWiseVehicleBalance)valueObject.get("dateWiseVehicleBalance");
  	      amount 					= Double.valueOf(valueObject.getDouble("amount", 0.0D));
  	      previousFuelDate			= (Timestamp) valueObject.get("previousFuelDate");
  	      
  	      
  	      entityManager.createNativeQuery(
  	        "UPDATE DateWiseVehicleBalance SET AllDayBalanceAmount = AllDayBalanceAmount + " + -amount.doubleValue() + " " + 
  	        " where transactionDate >= '" + DateTimeUtility.getDateFromTimeStamp(previousFuelDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + dateWiseVehicleBalance.getVid() + 
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
  		public void deleteDateWiseVehicleBalance(ValueObject valueObject) throws Exception {
  			
  			
  		}
  		
  		@Override
  		@Transactional
  			public synchronized void addAmountToAllDayAmountForNextDays(DateWiseVehicleBalance dateWiseVehicleBalance,
  					Double amount,Timestamp paramTimestamp) throws Exception {
  				try {
  					
  					 entityManager.createNativeQuery(
  					        "UPDATE DateWiseVehicleBalance SET AllDayBalanceAmount = AllDayBalanceAmount + " + amount.doubleValue() + " " + 
  					        " where transactionDate > '" + DateTimeUtility.getDateFromTimeStamp(paramTimestamp, DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "' AND vid=" + dateWiseVehicleBalance.getVid() + 
  					        " AND companyId = " + dateWiseVehicleBalance.getCompanyId() + " AND markForDelete=0")
  					        .executeUpdate();
				} catch (Exception e) {
					LOGGER.error("Exception " + e);
				}
  				
  			}
  		
  		@Override
  		@Transactional
  			public void updateDateWiseVehicleBalanceMultiple(String txnIds) throws Exception {
				try {
 					 entityManager.createNativeQuery(
 					        "UPDATE VehicleProfitAndLossTxnChecker SET isDateWiseVehicleBalanceUpdated = 1 where vehicleProfitAndLossTxnCheckerId IN ("+txnIds+")")
 					        .executeUpdate();
				} catch (Exception e) {
					LOGGER.error("Exception " + e);
				}
 				
 			}
  		
  			@Override
  			public DateWiseVehicleBalance validateDateWiseVehicleBalance(Integer vid, Integer companyId, String expenseDate)
  					throws Exception {

  				TypedQuery<DateWiseVehicleBalance> query = entityManager.createQuery(
  						"SELECT T "
  						+ " FROM DateWiseVehicleBalance AS T"
  								+ " where vid = "+vid+" AND companyId = "+companyId+" AND transactionDate = '"+expenseDate+"' AND markForDelete = 0", DateWiseVehicleBalance.class);

  				
  				DateWiseVehicleBalance	dateWiseVehicleExpense = null;
  				try {
  					query.setMaxResults(1);
  					dateWiseVehicleExpense = query.getSingleResult();
  					
  				} catch (NoResultException nre) {
  					// Ignore this because as per your logic this is ok!
  				}

  				return dateWiseVehicleExpense;
  			}
  		
  			@Override
  			public DateWiseVehicleBalance getLastDateVehicleBalance(Integer vid, Integer companyId, String expenseDate)
  					throws Exception {

  				TypedQuery<DateWiseVehicleBalance> query = entityManager.createQuery(
  						"SELECT T "
  						+ " FROM DateWiseVehicleBalance AS T "
  								+ " where vid = "+vid+" AND companyId = "+companyId+" AND transactionDate < '"+expenseDate+"' AND markForDelete = 0 ORDER BY dateWiseVehicleBalanceId DESC", DateWiseVehicleBalance.class);

  				
  				DateWiseVehicleBalance	dateWiseVehicleExpense = null;
  				try {
  					query.setMaxResults(1);
  					dateWiseVehicleExpense = query.getSingleResult();
  					
  				} catch (NoResultException nre) {
  					// Ignore this because as per your logic this is ok!
  				}

  				return dateWiseVehicleExpense;
  			}
}