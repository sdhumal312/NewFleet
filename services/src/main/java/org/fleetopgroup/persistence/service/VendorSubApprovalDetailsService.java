package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.ApprovalType;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.persistence.dao.VendorSubApprovalDetailsRepository;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.VendorSubApprovalDetailsDto;
import org.fleetopgroup.persistence.model.VendorSubApprovalDetails;
import org.fleetopgroup.persistence.serviceImpl.IVendorSubApprovalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("VendorSubApprovalDetailsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VendorSubApprovalDetailsService implements IVendorSubApprovalDetailsService {
	@Autowired
	private VendorSubApprovalDetailsRepository VendorApprovalDao;
	
	@PersistenceContext
	EntityManager entityManager;
	
	SimpleDateFormat 	dateTimeFormat 	= new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveSubApproval(VendorSubApprovalDetails VendorApproval) throws Exception {

		VendorApprovalDao.save(VendorApproval);
	}
	
	@Transactional
	public void updateSubApproved_Details(Long invoiceId, short approvedPaymentStatus,Long approvalId, Timestamp expectedPaymentDate,double subApprovalpaidAmount, Integer company_id) throws Exception {
		entityManager.createQuery("update VendorSubApprovalDetails set approvedPaymentStatusId=" + approvedPaymentStatus+ " , expectedPaymentDate = '"+expectedPaymentDate+"', subApprovalpaidAmount="+subApprovalpaidAmount+", approvalStatusId="+FuelVendorPaymentMode.PAYMENT_MODE_APPROVED+" where invoiceId = "+invoiceId+" AND approvalId ="+approvalId+" AND companyId = " + company_id + " ").executeUpdate();
	}
	
	@Transactional
	public void updateSubApprovedPayment_Details( short approvalStatus,short paymentType,String payNumber, Timestamp PaymentDate,long paidById ,String InvoiceIds , Long ApprovalID, Integer companyId) throws Exception {
		entityManager.createQuery("update VendorSubApprovalDetails set approvalStatusId=" + approvalStatus+ ",approvalPaymentTypeId="+paymentType+",approvalPayNumber='"+payNumber+"',approvalDateofpayment='"+PaymentDate+"',approvalpaidbyId="+paidById+" where invoiceId IN (" + InvoiceIds + ") AND approvalId ="+ApprovalID+" AND companyId = " + companyId + " ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateSubApprovalPaymentStatus(Long subApprovalId, short paymentStatus, short paymentType)
			throws Exception {
		entityManager.createQuery("update VendorSubApprovalDetails set approvedPaymentStatusId=" + paymentStatus+ ","
				+ "approvalPaymentTypeId="+paymentType+" where subApprovalId = "+subApprovalId+"  ").executeUpdate();
		
	}
	
	@Transactional
	public void deleteSubVendorApproval(Long VendorApproval,Integer companyID) throws Exception {
		VendorApprovalDao.deleteSubVendorApproval(VendorApproval, companyID);
	}
	
	@Override
	public List<VendorSubApprovalDetailsDto> getVendorSubApprovalDetails(Long VendorApproval_id, Integer companyId) throws Exception {
		
		TypedQuery<Object[]> query = entityManager.createQuery(
				" SELECT VSA.subApprovalId, VSA.approvalId, VSA.created, VSA.approvedPaymentStatusId, "
				+ " VSA.approvalPlaceId, VSA.approvalStatusId, VSA.expectedPaymentDate, VSA.subApprovalTotal, "
				+ " VSA.subApprovalpaidAmount, VSA.invoiceId, V.vendorName, PI.partInvoiceNumber, BI.batteryInvoiceNumber, "
				+ " TI.ITYRE_NUMBER, RT.TRNUMBER, CI.clothInvoiceNumber, SE.serviceEntries_Number, PO.purchaseorder_Number, VH.vehicle_registration, "
				+ " PI.invoiceNumber, BI.invoiceNumber, TI.INVOICE_NUMBER, RT.TR_INVOICE_NUMBER, CI.invoiceNumber, SE.invoiceNumber, PO.purchaseorder_invoiceno, "
				+ " PI.invoiceDate, BI.invoiceDate, TI.INVOICE_DATE, RT.TR_INVOICE_DATE, CI.invoiceDate, SE.invoiceDate, PO.purchaseorder_invoice_date , SE.jobNumber,"
				+ " LI.laundryInvoiceId, LI.laundryInvoiceNumber, LI.sentDate, LI.paymentNumber "
				+ " FROM VendorSubApprovalDetails VSA "
				+ " INNER JOIN Vendor V ON V.vendorId = VSA.approvalvendorID"
				+ " LEFT JOIN PartInvoice PI ON PI.partInvoiceId = VSA.invoiceId AND VSA.approvalPlaceId ="+ApprovalType.APPROVAL_TYPE_INVENTORY_PART_INVOICE+" "
				+ " LEFT JOIN BatteryInvoice BI ON BI.batteryInvoiceId = VSA.invoiceId AND VSA.approvalPlaceId ="+ApprovalType.APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE+" "
				+ " LEFT JOIN InventoryTyreInvoice TI ON TI.ITYRE_ID = VSA.invoiceId AND VSA.approvalPlaceId ="+ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_INVOICE+" "
				+ " LEFT JOIN InventoryTyreRetread RT ON RT.TRID = VSA.invoiceId AND VSA.approvalPlaceId ="+ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_RETREAD+" "
				+ " LEFT JOIN ClothInvoice CI ON CI.clothInvoiceId = VSA.invoiceId AND VSA.approvalPlaceId ="+ApprovalType.APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE+" "
				+ " LEFT JOIN UpholsterySendLaundryInvoice LI ON LI.laundryInvoiceId = VSA.invoiceId AND VSA.approvalPlaceId ="+ApprovalType.APPROVAL_TYPE_INVENTORY_LAUNDRY_INVOICE+" "
				+ " LEFT JOIN ServiceEntries SE ON SE.serviceEntries_id = VSA.invoiceId AND VSA.approvalPlaceId ="+ApprovalType.APPROVAL_TYPE_SERVICE_ENTRIES+" "
				+ " LEFT JOIN Vehicle VH ON VH.vid = SE.vid "
				+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = VSA.invoiceId AND VSA.approvalPlaceId ="+ApprovalType.APPROVAL_TYPE_PURCHASE_ORDER+" "
				+ " where VSA.approvalId =" + VendorApproval_id + " AND VSA.approvalPlaceId <> "+ApprovalType.APPROVAL_TYPE_FUEL_ENTRIES+" AND VSA.companyId = "+companyId+" AND VSA.markForDelete = 0 ORDER BY VSA.subApprovalId desc",
				Object[].class);
		
		List<Object[]> results = query.getResultList();
		
		List<VendorSubApprovalDetailsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VendorSubApprovalDetailsDto>();
			VendorSubApprovalDetailsDto vendorApproval = null;
			for (Object[] result : results) {
				vendorApproval = new VendorSubApprovalDetailsDto();
				vendorApproval.setSubApprovalId((Long) result[0]);
				vendorApproval.setApprovalId((Long) result[1]);
				vendorApproval.setCreatedStr(dateTimeFormat.format((java.util.Date) result[2]));
				vendorApproval.setApprovedPaymentStatus(InventoryTyreInvoiceDto.getPaymentModeName((short) result[3]));
				vendorApproval.setApprovalPlaceId((short) result[4]);
				vendorApproval.setApprovalPlace(ApprovalType.getApprovalType((short) result[4]));
				vendorApproval.setApprovalStatus(InventoryTyreInvoiceDto.getPaymentModeName((short) result[5]));
				if(result[6] != null) {
					vendorApproval.setExpectedPaymentDateStr(dateTimeFormat.format((java.util.Date) result[6]));
				}
				vendorApproval.setSubApprovalTotal(Double.parseDouble(toFixedTwo.format(result[7])));// Actual Amount (Invoice Amount)
				if(result[8] != null) {
					vendorApproval.setSubApprovalPaidAmount(Double.parseDouble(toFixedTwo.format(result[8])) );// After Approval this is the final Amount
					
				}else {
					vendorApproval.setSubApprovalPaidAmount(0.0);
				}
				vendorApproval.setInvoiceId((Long) result[9]);
				if(result[10] != null) {
					vendorApproval.setVendorName((String) result[10]);
				}
				if(result[11] != null) {
					vendorApproval.setPartInvoiceNumber((Long) result[11]);
				}
				if(result[12] != null) {
					vendorApproval.setBatteryInvoiceNumber((Long) result[12]);
				}
				if(result[13] != null) {
					vendorApproval.setTyreInvoiceNumber((Long) result[13]);
				}
				if(result[14] != null) {
					vendorApproval.setTyreRetreadInvoiceNumber((Long) result[14]);
				}
				if(result[15] != null) {
					vendorApproval.setClothInvoiceNumber((Long) result[15]);
				}
				if(result[16] != null) {
					vendorApproval.setServiceEntriesNumber((Long) result[16]);
				}
				if(result[17] != null) {
					vendorApproval.setPurchaseOrderNumber((Long) result[17]);
				}
				if(result[18] != null) {
					vendorApproval.setVehicle((String) result[18]);
				}
				if(result[19] != null) {
					vendorApproval.setInvoicePartNumber((String) result[19]);
				}
				if(result[20] != null) {
					vendorApproval.setInvoiceBatteryNumber((String) result[20]);
				}
				if(result[21] != null) {
					vendorApproval.setInvoiceTyreNumber((String) result[21]);
				}
				if(result[22] != null) {
					vendorApproval.setInvoiceTyreRetreadNumber((String) result[22]);
				}
				if(result[23] != null) {
					vendorApproval.setInvoiceClothNumber((String) result[23]);
				}
				if(result[24] != null) {
					vendorApproval.setInvoiceSENumber((String) result[24]);
				}
				if(result[25] != null) {
					vendorApproval.setInvoicePONumber((String) result[25]);
				}
				if(result[26] != null) {
					vendorApproval.setPartInvoiceDate(dateTimeFormat.format((java.util.Date) result[26]));
				}
				if(result[27] != null) {
					vendorApproval.setBatteryInvoiceDate(dateTimeFormat.format((java.util.Date) result[27]));
				}
				if(result[28] != null) {
					vendorApproval.setTyreInvoiceDate(dateTimeFormat.format((java.util.Date) result[28]));
				}
				if(result[29] != null) {
					vendorApproval.setTyreRetreadInvoiceDate(dateTimeFormat.format((java.util.Date) result[29]));
				}
				if(result[30] != null) {
					vendorApproval.setClothInvoiceDate(dateTimeFormat.format((java.util.Date) result[30]));
				}
				if(result[31] != null) {
					vendorApproval.setServiceEntriesDate(dateTimeFormat.format((java.util.Date) result[31]));
				}
				if(result[32] != null) {
					vendorApproval.setPurchaseOrderDate(dateTimeFormat.format((java.util.Date) result[32]));
				}
				if(result[33] != null) {
					vendorApproval.setJobNumber((String) result[33]);
				}
				vendorApproval.setLaundryInvoiceId((Long) result[34]);
				vendorApproval.setLaundryInvoiceNumber((Long) result[35]);
				
				if(result[36] != null) {
					vendorApproval.setSentDateStr(dateTimeFormat.format((java.util.Date) result[36]));
				}
				vendorApproval.setInvoiceLINumber((String) result[37]);
				
				Dtos.add(vendorApproval);
			}
		}
		return Dtos;
	}
	
	@Override
	@Transactional
	public void deleteSubVendorApprovalById(Long subApprovalId, Integer companyId) throws Exception {
		VendorApprovalDao.deleteSubVendorApprovalById(subApprovalId, companyId);
	}

	@Override
	public VendorSubApprovalDetails getVendorSubApprovalDetailsById(Long subApprovalId) throws Exception {
		return VendorApprovalDao.findById(subApprovalId).get();
	}
	
	@Override
	public List<VendorSubApprovalDetails> getSubApprovalListByApprovalId(Long approvalId, Integer companyId) throws Exception {
		return VendorApprovalDao.getVendorSubApprovalDetails(approvalId, companyId);
	}
}
