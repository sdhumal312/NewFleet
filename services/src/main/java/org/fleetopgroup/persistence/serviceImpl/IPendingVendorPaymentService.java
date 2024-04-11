package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.PendingVendorPaymentDto;
import org.fleetopgroup.persistence.model.PendingVendorPayment;

public interface IPendingVendorPaymentService {
	
	public void savePendingVendorPayment(PendingVendorPayment	payment);
	
	public void deletePendingVendorPayment(Long transactionId, short type) throws Exception;
	
	public void updatePendingVendorPayment(PendingVendorPayment	payment) throws Exception;
	
	public void updatePendingVendorPaymentAmt(Long transactionId, short type, Double amount) throws Exception;
	
	public void deletePendingVendorPaymentAmt(Long transactionId, short type, Double amount) throws Exception;
	
	List<PendingVendorPaymentDto>  getPendingVendorPayment(String query, Integer companyId) throws Exception;
	
	public PendingVendorPayment	getPendingVendorPaymentById(Long transactionId, short type) throws Exception;
	
	public void updateVendorPaymentStatus(Long transactionId, short type, short status) throws Exception;
	
	public Double getVendorPendingPaymentDetails(Integer	vendorId, Integer companyId) throws Exception;
	
	public void updateVendorPaymentStatusAndAmt(Long transactionId, short type, short status, Double amount) throws Exception;
	
	public PendingVendorPayment updateAfterVendorPaymentStatus(Integer vid, Long invoiceId, Integer companyId) throws Exception;

	public void updatePendingVendorPayment(Long pendingPaymentId, Double transactionAmount) throws Exception;
}
