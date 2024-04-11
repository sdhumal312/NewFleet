package org.fleetopgroup.persistence.report.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.persistence.dto.VehicleAgentIncomeExpenseDetailsDto;
import org.fleetopgroup.persistence.report.dao.VehicleAgentIncomeExpenseDetailsDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class VehicleAgentIncomeExpenseDetailsDaoImpl implements VehicleAgentIncomeExpenseDetailsDao {

	@PersistenceContext
	public EntityManager entityManager;

	
	@Override
	@Transactional
	public void updateClosingBalanceAmountForNextDates(Double amount, String txnDate, Integer vid) throws Exception {
		entityManager.createQuery(
		        "UPDATE VehicleAgentIncomeExpenseDetails SET creditAmount = creditAmount + "+amount+" " 
		        +" where transactionDateTime > '" + txnDate + "' AND vid=" + vid +" AND txnTypeId = "+VehicleAgentConstant.TXN_TYPE_CLOSING_ENTRY+" AND markForDelete = 0")
		        .executeUpdate();
}

	@Override
	@Transactional
	public void updateClosingBalanceAmount(Double amount, String txnDate, Integer vid) throws Exception {
		  entityManager.createQuery(
			        "UPDATE VehicleAgentIncomeExpenseDetails SET creditAmount = "+amount+" " 
			        +" where transactionDateTime = '" + txnDate + "' AND vid=" + vid +" AND txnTypeId = "+VehicleAgentConstant.TXN_TYPE_CLOSING_ENTRY+" AND markForDelete=0")
			        .executeUpdate();
	}
	
	@Override
	public List<VehicleAgentIncomeExpenseDetailsDto> getVehicleAgentPendingPayment(Integer vid, String fromDate, String toDate) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			try {
					query = entityManager.createQuery("SELECT VGD.agentIncomeExpenseDetailsId, VGD.vid, VGD.txnTypeId, VGD.creditAmount,"
							+ " VGD.debitAmount, VGD.identifier, VGD.transactionId, VGD.accountName, VGD.numberWithtype, VGD.remark,"
							+ " VGD.transactionDateTime "
							+ " from VehicleAgentIncomeExpenseDetails VGD "
							+ " where VGD.vid = '"+vid+"' AND transactionDateTime >= '"+fromDate+"' AND transactionDateTime <= '"+toDate+"'  "
							+ " AND VGD.isPaymentDone = 0 AND VGD.markForDelete = 0 "
							+ " AND VGD.markForDelete = 0 ORDER BY VGD.transactionDateTime ASC ", Object[].class);
				
				List<Object[]> results = query.getResultList();

			List<VehicleAgentIncomeExpenseDetailsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleAgentIncomeExpenseDetailsDto>();
				VehicleAgentIncomeExpenseDetailsDto list = null;
				for (Object[] result : results) {
					list = new VehicleAgentIncomeExpenseDetailsDto();

					list.setAgentIncomeExpenseDetailsId((Long) result[0]);
					list.setVid((Integer) result[1]);
					list.setTxnTypeId((short) result[2]);
					list.setCreditAmount((Double) result[3]);
					list.setDebitAmount((Double) result[4]);
					list.setIdentifier((short) result[5]);
					list.setTransactionId((Long) result[6]);
					list.setAccountName((String) result[7]);
					list.setNumberWithtype((String) result[8]);
					list.setRemark((String) result[9]);
					list.setTransactionDateTime((Date) result[10]);

					Dtos.add(list);
				}
			}
				return Dtos;
			
			} catch (Exception e) {
				System.err.println("inside catch " + e);
				throw e;
			} finally {
				query = null;
			}
		} catch (Exception e) {
			throw e;
		}finally {
			
		}
	}
	
	@Override
	public Double getVehicleAgentOpeningClosingBalanceByDate(Integer vid, String txnDate) throws Exception {
		double balanceAmount	= 0.0;
	Query	query = entityManager.createQuery("SELECT creditAmount "
			+ " FROM VehicleAgentIncomeExpenseDetails where vid = "+vid+" AND transactionDateTime <= '"+txnDate+"' "
					+ " AND txnTypeId = "+VehicleAgentConstant.TXN_TYPE_CLOSING_ENTRY+" AND isPaymentDone = 0 AND markForDelete = 0 order by transactionDateTime desc");

	try {
		query.setMaxResults(1);
		balanceAmount = (double) query.getSingleResult();
		
	} catch (NoResultException nre) {
		// Ignore this because as per your logic this is ok!
	}
	
	return balanceAmount;
}
	
	@Override
	@Transactional
	public void updatePaymentDone(Integer vid, String date) throws Exception {
		  entityManager.createQuery(
			        "UPDATE VehicleAgentIncomeExpenseDetails SET isPaymentDone = 1 " 
			        +" where transactionDateTime <= '" + date + "' AND vid=" + vid +" AND markForDelete=0")
			        .executeUpdate();
	
	}
	
}
