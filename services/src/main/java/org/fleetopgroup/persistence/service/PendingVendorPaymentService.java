package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.persistence.dao.PendingVendorPaymentRepository;
import org.fleetopgroup.persistence.dto.PendingVendorPaymentDto;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PendingVendorPaymentService implements IPendingVendorPaymentService {
	
	@PersistenceContext EntityManager entityManager;

	@Autowired	PendingVendorPaymentRepository		pendingVendorPaymentRepository;
	
	SimpleDateFormat	format	= new SimpleDateFormat("dd-MM-yyyy");	
	
	@Override
	public void savePendingVendorPayment(PendingVendorPayment	payment) {
		
		pendingVendorPaymentRepository.save(payment);
	}

	@Override
	@Transactional
	public void deletePendingVendorPayment(Long transactionId, short type) throws Exception {
		pendingVendorPaymentRepository.deletePendingVendorPayment(transactionId, type);
		
	}
	
	@Override
	@Transactional
	public void updatePendingVendorPayment(PendingVendorPayment payment) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		entityManager.createQuery(
				"Update PendingVendorPayment SET vendorId =  "+payment.getVendorId()+", transactionDate = '"+dateFormat.format(payment.getTransactionDate())+"' , "
						+ " transactionAmount = "+payment.getTransactionAmount()+", balanceAmount = "+payment.getTransactionAmount()+" "
						+ " WHERE transactionId = "+payment.getTransactionId()+" AND txnTypeId = "+payment.getTxnTypeId()+"  ")
		.executeUpdate();	
	}
	
	@Override
	@Transactional
	public void updatePendingVendorPaymentAmt(Long transactionId, short type, Double amount) throws Exception {
		entityManager.createQuery(
				"Update PendingVendorPayment SET transactionAmount = "+amount+", balanceAmount = "+amount+" "
						+ " WHERE transactionId = "+transactionId+" AND txnTypeId = "+type+"  ")
		.executeUpdate();	
	}
	
	@Override
	@Transactional
	public void deletePendingVendorPaymentAmt(Long transactionId, short type, Double amount) throws Exception {
		entityManager.createQuery(
				"Update PendingVendorPayment SET transactionAmount = transactionAmount - "+amount+", balanceAmount = balanceAmount - "+amount+" "
						+ " WHERE transactionId = "+transactionId+" AND txnTypeId = "+type+"  ")
		.executeUpdate();	
	}
	
	@Override
	public List<PendingVendorPaymentDto> getPendingVendorPayment(String query, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery(" SELECT BH.pendingPaymentId, BH.vendorId, BH.transactionId, BH.transactionNumber, BH.txnTypeId,"
							+ " BH.transactionDate, BH.transactionAmount, BH.balanceAmount, BH.paymentStatusId, BH.invoiceNumber, BH.vid,"
							+ " V.vehicle_registration, BH.documentId "
							+ " FROM PendingVendorPayment AS BH "
							+ " LEFT JOIN Vehicle V ON V.vid = BH.vid"
							+ " where "+query+" AND BH.companyId = "+companyId+" AND "
							+ "BH.paymentStatusId IN ("+PendingPaymentType.PAYMENT_STATUS_PENDING+", "+PendingPaymentType.PAYMENT_STATUS_PARTIAL+" ) "
							+ " AND BH.markForDelete = 0 order by BH.transactionDate desc ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<PendingVendorPaymentDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					PendingVendorPaymentDto	paymentList = new PendingVendorPaymentDto();
					
					paymentList.setPendingPaymentId((Long) result[0]);
					paymentList.setVendorId((Integer) result[1]);
					paymentList.setTransactionId((Long) result[2]);
					paymentList.setTransactionNumber((String) result[3]);
					paymentList.setTxnTypeId((short) result[4]);
					paymentList.setTransactionDate((Date) result[5]);
					paymentList.setTransactionAmount((Double) result[6]);
					paymentList.setBalanceAmount((Double) result[7]);
					paymentList.setPaymentStatusId((short) result[8]);
					paymentList.setInvoiceNumber((String) result[9]);
					paymentList.setVid((Integer) result[10]);
					paymentList.setVehicleNumber((String) result[11]);
					paymentList.setDocumentId((Long) result[12]);
					
					paymentList.setTransactionDateStr(format.format(paymentList.getTransactionDate()));
					paymentList.setPaymentStatus(PendingPaymentType.getPaymentMode(paymentList.getPaymentStatusId()));
					
					if(paymentList.getVehicleNumber() == null) {
						paymentList.setVehicleNumber("--");
					}
					
					list.add(paymentList);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public PendingVendorPayment getPendingVendorPaymentById(Long transactionId, short type) throws Exception {
		
		return pendingVendorPaymentRepository.getPendingVendorPaymentById(transactionId, type);
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentStatus(Long transactionId, short type, short status) throws Exception {
		entityManager.createQuery(
				"Update PendingVendorPayment SET paymentStatusId = "+status+" "
						+ " WHERE transactionId = "+transactionId+" AND txnTypeId = "+type+" ")
		.executeUpdate();	
	}
	
	@Override
	@Transactional
	public PendingVendorPayment updateAfterVendorPaymentStatus(Integer vid, Long invoiceId, Integer companyId) throws Exception{
	
		return pendingVendorPaymentRepository.getPendingVendorPaymentByTransactionId(vid, invoiceId, companyId);
		
	}
	
	@Override
	public void updateVendorPaymentStatusAndAmt(Long transactionId, short type, short status, Double amount)
			throws Exception {
		entityManager.createQuery(
				"Update PendingVendorPayment SET paymentStatusId = "+status+" , balanceAmount = balanceAmount - "+amount+" "
						+ " WHERE transactionId = "+transactionId+" AND txnTypeId = "+type+" ")
		.executeUpdate();	
	}
	
	@Override
	public Double getVendorPendingPaymentDetails(Integer vendorId, Integer companyId) throws Exception {

		Query queryt = 	null;
		queryt = entityManager.createQuery(
				"SELECT SUM(F.balanceAmount) "
						+ " From PendingVendorPayment as F "
						+ " WHERE F.vendorId = "+vendorId+" AND F.companyId = "+companyId+" AND F.markForDelete = 0 ");

		Double count = 0.0;
		try {

				count = (Double) queryt.getSingleResult();

		} catch (NoResultException nre) {

		}

		return count;
	}

	@Override
	@Transactional
	public void updatePendingVendorPayment(Long pendingPaymentId, Double transactionAmount)
			throws Exception {
		// TODO Auto-generated method stub
		
		pendingVendorPaymentRepository.updatependingVendorBalanceAmunt(pendingPaymentId,transactionAmount);
	}
}
