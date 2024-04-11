package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.persistence.model.DateWiseVehicleExpense;
import org.fleetopgroup.persistence.report.dao.DateWiseVehicleExpenseDetailsDao;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DateWiseVehicleExpenseDetailsDaoImpl implements DateWiseVehicleExpenseDetailsDao {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext	EntityManager entityManager;
	
	@Override
	@Transactional
	public void subtractVehicleExpenseFromPreviousDate(DateWiseVehicleExpense	dateWiseVehicleExpense, Double amount) throws Exception {
		
		try {
			entityManager.createNativeQuery(
					"UPDATE DateWiseVehicleExpense SET expenseAmount = expenseAmount - "+ amount+" "
							+ " where expenseDate = '" + dateWiseVehicleExpense.getExpenseDate() + "' AND vid=" + dateWiseVehicleExpense.getVid()
							+ " AND companyId = " + dateWiseVehicleExpense.getCompanyId() + " AND markForDelete=0")
			.executeUpdate();
			} catch (Exception e) {
			LOGGER.error("Exception ", e);
		}
	}
	
	@Override
	@Transactional
	public void deleteDateWiseVehicleExpense(ValueObject valueObject) throws Exception {
		Integer		vid						= 0;
		Timestamp	transactionDateTime		= null;
		Double		txnAmount				= 0.0;
		Integer		companyId				= 0;
		Short		txnType					= 0;
		try {
			vid					= valueObject.getInt("vid", 0);
			transactionDateTime	= (Timestamp) valueObject.get("transactionDateTime");
			txnAmount			= valueObject.getDouble("txnAmount", 0);
			companyId			= valueObject.getInt("companyId");
			txnType				= valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE);
			if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) != VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
				entityManager.createNativeQuery(
						"UPDATE DateWiseVehicleExpense SET expenseAmount = expenseAmount - "+ txnAmount+" "
								+ " where expenseDate = '" + transactionDateTime + "' AND vid=" + vid
								+ " AND companyId = " + companyId + " AND expenseType = "+txnType+" AND markForDelete=0")
				.executeUpdate();
			}else {
				entityManager.createNativeQuery(
						"UPDATE DateWiseVehicleExpense SET expenseAmount = expenseAmount - "+ txnAmount+" "
								+ " where expenseDate = '" + transactionDateTime + "' AND vid=" + vid
								+ " AND expenseId = "+valueObject.getInt("expenseId", 0)+" AND companyId = " + companyId + " AND expenseType = "+txnType+" AND markForDelete=0")
				.executeUpdate();
			}
			
			} catch (Exception e) {
			LOGGER.error("Exception " , e);
		}
	}

	@Override
	public DateWiseVehicleExpense validateDateWiseVehicleExpense(Integer expenseId, Integer companyId, Integer vid,
			String expenseDate, short expenseType) throws Exception {

		TypedQuery<DateWiseVehicleExpense> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM DateWiseVehicleExpense AS T"
						+ " where expenseDate = '"+expenseDate+"' AND expenseId = "+expenseId+" AND companyId = "+companyId+" AND vid = "+vid+" AND expenseType = "+expenseType+" AND markForDelete = 0", DateWiseVehicleExpense.class);

		
		DateWiseVehicleExpense	dateWiseVehicleExpense = null;
		try {
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return dateWiseVehicleExpense;
	}
	
	@Override
	public DateWiseVehicleExpense validateDateWiseVehicleExpense(short expenseType, Integer companyId, Integer vid,
			String expenseDate) throws Exception {

		TypedQuery<DateWiseVehicleExpense> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM DateWiseVehicleExpense AS T"
						+ " where T.expenseDate = '"+expenseDate+"' AND T.expenseType = "+expenseType+" AND T.companyId = "+companyId+" AND T.vid = "+vid+" AND T.markForDelete = 0", DateWiseVehicleExpense.class);

		
		DateWiseVehicleExpense	dateWiseVehicleExpense = null;
		try {
			query.setMaxResults(1);
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return dateWiseVehicleExpense;
	}
}
