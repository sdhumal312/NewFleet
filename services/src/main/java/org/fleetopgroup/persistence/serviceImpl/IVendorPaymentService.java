package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;



public interface IVendorPaymentService {
	/*
	 * Add Vendor Payment Details
	 */
	public ValueObject addVendorPayment(ValueObject valueObject) throws Exception;
	/*
	 * Get Vendor's Opening Balance
	 */
	public ValueObject getVendorOpeningBalance(ValueObject valueObject) throws Exception;
	/*
	 * Get Vendor Payment Report
	 */
	public ValueObject getVendorPaymentReport(ValueObject valueObject) throws Exception;
	/*
	 * Get Vendor Payment Details By Id
	 */
	public ValueObject getVendorPaymentDetailsById(ValueObject valueObject) throws Exception;
	/*
	 * Get Page Wise Vendor Payment Details
	 */
	public ValueObject getPageWiseVendorPaymentDetails(ValueObject valueObject) throws Exception;
	/*
	 * Delete Vendor Payment By VendorPaymentId
	 */
	public ValueObject deleteVendorPayment(ValueObject valueObject) throws Exception;
	/*
	 * update Vendor Payment By VendorPaymentId
	 */
	public ValueObject updateVendorPayment(ValueObject valueObject) throws Exception;
	
	public ValueObject getVendorGstReport(ValueObject object) throws Exception;
	public ValueObject VendorPaymentApproval(ValueObject valueObject) throws Exception;
	public ValueObject VendorFuelPaymentApproval(ValueObject valueOutObject) throws Exception;
	public ValueObject getPartiallyPaidApprovalDetails(ValueObject valueObject) throws Exception;
	public ValueObject getVendorPaymentHistoryReport(ValueObject object) throws Exception;
	public ValueObject getVendorWisePaymentStatusReport(ValueObject valueObject) throws Exception;
	public ValueObject getPendingVendorPaymentList(ValueObject valueObject) throws Exception;
	public ValueObject getVendorApprovalForPayment(ValueObject valueObject) throws Exception;
}