package org.fleetopgroup.persistence.bl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.constant.ApprovalType;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VendorApprovalDto;
import org.fleetopgroup.persistence.dto.VendorDto;
import org.fleetopgroup.persistence.dto.VendorSubApprovalDetailsDto;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.VendorApproval;
import org.fleetopgroup.persistence.model.VendorSubApprovalDetails;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class VendorApprovalBL {

	public VendorApprovalBL() {
	}

	VehicleBL VBL = new VehicleBL();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	DecimalFormat  toFixedTwo = new DecimalFormat("#.##");

	// get Save VendorApproval
	public VendorApproval prepareVendorApproval(VendorDto Vendor) {

		VendorApproval VendorApproval = new VendorApproval();

		VendorApproval.setApprovalId(VendorApproval.getApprovalId());
		VendorApproval.setApprovalvendorID(Vendor.getVendorId());
		//VendorApproval.setApprovalvendorName(Vendor.getVendorName());
		//VendorApproval.setApprovalvendorType(Vendor.getVendorType());
	//	VendorApproval.setApprovalvendorTypeId(Vendor.getVendorTypeId());
	//	VendorApproval.setApprovalvendorLocation(Vendor.getVendorLocation());

		/** who Create this vehicle get email id to user profile details */
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		//VendorApproval.setCreatedBy(userDetails.getEmail());
		//VendorApproval.setLastModifiedBy(userDetails.getEmail());
		VendorApproval.setCompanyId(userDetails.getCompany_id());
		VendorApproval.setCreatedById(userDetails.getId());
		VendorApproval.setLastModifiedById(userDetails.getId());
		VendorApproval.setMarkForDelete(false);
		try {
			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			java.util.Date dateTo = dateFormatTime.parse(ft.format(currentDate));

			java.sql.Date toDate = new java.sql.Date(dateTo.getTime());
			VendorApproval.setCreated(toDate);
			VendorApproval.setLastupdated(toDate);
			VendorApproval.setApprovalCreateDate(toDate);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return VendorApproval;
	}

	public List<VendorApprovalDto> prepareListofVendorApproval(List<VendorApprovalDto> ApprovalList) throws Exception {
		List<VendorApprovalDto> s = null;
		if (ApprovalList != null && !ApprovalList.isEmpty()) {
			s = new ArrayList<VendorApprovalDto>();
			VendorApprovalDto VendorApproval = null;
			for (VendorApprovalDto Approval : ApprovalList) {
				VendorApproval = new VendorApprovalDto();

				VendorApproval.setApprovalId(Approval.getApprovalId());
				VendorApproval.setApprovalNumber(Approval.getApprovalNumber());
				VendorApproval.setApprovalvendorID(Approval.getApprovalvendorID());
				VendorApproval.setApprovalvendorName(Approval.getApprovalvendorName());
				VendorApproval.setApprovalvendorType(Approval.getApprovalvendorType());
				VendorApproval.setApprovalvendorLocation(Approval.getApprovalvendorLocation());
				VendorApproval.setApprovalStatusId(Approval.getApprovalStatusId());
				VendorApproval.setApprovalStatus(FuelVendorPaymentMode.getPaymentMode(Approval.getApprovalStatusId()));

				VendorApproval.setApprovalPlaceId(Approval.getApprovalPlaceId());
				VendorApproval.setApprovalPlace(ApprovalType.getApprovalType(Approval.getApprovalPlaceId()));

				VendorApproval.setApprovalTotal(Approval.getApprovalTotal());
				VendorApproval.setApprovalCreateDate(Approval.getApprovalCreateDate());
				VendorApproval.setApprovalCreateBy(Approval.getApprovalCreateBy());

				VendorApproval.setCreatedOn(Approval.getCreatedOn());
				VendorApproval.setLastupdatedOn(Approval.getLastupdatedOn());

				s.add(VendorApproval);
			}
		}
		return s;
	}
	
	public VendorApprovalDto prepareVendorApprovalList(VendorApprovalDto ApprovalList, List<VendorSubApprovalDetails> VendorSubApprovalDetails) throws Exception {
		VendorApprovalDto 	vendorApproval = null;
		double							approvalPaidAmount	= 0;
			
			vendorApproval = new VendorApprovalDto();

		vendorApproval.setApprovalId(ApprovalList.getApprovalId());
		vendorApproval.setApprovalNumber(ApprovalList.getApprovalNumber());
			vendorApproval.setApprovalvendorID(ApprovalList.getApprovalvendorID());
			vendorApproval.setApprovalvendorName(ApprovalList.getApprovalvendorName());
			vendorApproval.setApprovalvendorType(ApprovalList.getApprovalvendorType());
			vendorApproval.setApprovalvendorLocation(ApprovalList.getApprovalvendorLocation());
			vendorApproval.setApprovalStatusId(ApprovalList.getApprovalStatusId());//1. Its Approval StatusID (Approved, NotAprroved Or Paid)
			vendorApproval.setApprovalStatus(FuelVendorPaymentMode.getPaymentMode(ApprovalList.getApprovalStatusId()));
			if(vendorApproval.getApprovedPaymentStatusId() > 0) {
				vendorApproval.setApprovedPaymentStatusId(ApprovalList.getApprovedPaymentStatusId());
				vendorApproval.setApprovedPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode(ApprovalList.getApprovedPaymentStatusId()));// 2. Its Approvd Payment Status ID (clear Negotiate partial)
			}
			vendorApproval.setApprovalPlaceId(ApprovalList.getApprovalPlaceId());
			vendorApproval.setApprovalPlace(ApprovalType.getApprovalType(ApprovalList.getApprovalPlaceId()));
			vendorApproval.setApprovalTotal(ApprovalList.getApprovalTotal());
			vendorApproval.setApprovalCreateDate(ApprovalList.getApprovalCreateDate());
			vendorApproval.setApprovalCreateBy(ApprovalList.getApprovalCreateBy());
			vendorApproval.setCreatedOn(ApprovalList.getCreatedOn());
			vendorApproval.setCreated(dateFormatTime.format(ApprovalList.getCreatedOn()));
			vendorApproval.setLastupdatedOn(ApprovalList.getLastupdatedOn());
			vendorApproval.setPaidAmount(ApprovalList.getPaidAmount());
			vendorApproval.setApprovalPaidTotal(ApprovalList.getApprovalPaidTotal());
			vendorApproval.setTypeOfPaymentStatus(FuelVendorPaymentMode.getPaymentMode(ApprovalList.getApprovalStatusId()));
			
			if (VendorSubApprovalDetails != null && !VendorSubApprovalDetails.isEmpty()) {
				for (VendorSubApprovalDetails Approval : VendorSubApprovalDetails) {
					if(Approval.getSubApprovalpaidAmount() != null)
					approvalPaidAmount += Approval.getSubApprovalpaidAmount();
				}
				
				if(approvalPaidAmount > 0) {
					vendorApproval.setTotalApprovalPaidAmount(approvalPaidAmount);
				}
				
		}
		return vendorApproval;
	}

/*	// get the VendorApproval Details
	public VendorApproval getVendorApproval(VendorApproval Approval) {
		VendorApproval VendorApproval = new VendorApproval();

		VendorApproval.setApprovalId(Approval.getApprovalId());
		VendorApproval.setApprovalvendorID(Approval.getApprovalvendorID());
		//VendorApproval.setApprovalvendorName(Approval.getApprovalvendorName());
		//VendorApproval.setApprovalvendorLocation(Approval.getApprovalvendorLocation());

		VendorApproval.setApprovalTotal(Approval.getApprovalTotal());
		VendorApproval.setApprovalCreateDate(Approval.getApprovalCreateDate());
		VendorApproval.setApprovalCreateBy(Approval.getApprovalCreateBy());

		VendorApproval.setCreated(Approval.getCreated());
		VendorApproval.setLastupdated(Approval.getLastupdated());

		return VendorApproval;
	}
*/
	/*// get the VendorApproval Details
	public VendorApprovalDto Show_VendorApproval(VendorApproval Approval) {
		VendorApprovalDto VendorApproval = new VendorApprovalDto();

		VendorApproval.setApprovalId(Approval.getApprovalId());
		VendorApproval.setApprovalNumber(Approval.getApprovalNumber());
		VendorApproval.setApprovalvendorID(Approval.getApprovalvendorID());
		VendorApproval.setApprovalvendorName(Approval.getApprovalvendorName());
		VendorApproval.setApprovalvendorLocation(Approval.getApprovalvendorLocation());

		VendorApproval.setApprovalTotal(Approval.getApprovalTotal());
		VendorApproval.setApprovalCreateDate(Approval.getApprovalCreateDate());
		VendorApproval.setApprovalCreateBy(Approval.getApprovalCreateBy());

		VendorApproval.setApprovalStatus(Approval.getApprovalStatus());
		VendorApproval.setApprovalPlace(Approval.getApprovalPlace());
		VendorApproval.setApprovalPaymentType(Approval.getApprovalPaymentType());
		VendorApproval.setApprovalPayNumber(Approval.getApprovalPayNumber());
		VendorApproval.setApprovalpaidby(Approval.getApprovalpaidby());

		if (Approval.getApprovalDateofpayment() != null) {
			VendorApproval.setApprovalDateofpayment(dateFormat_Name.format(Approval.getApprovalDateofpayment()));
		}
		// Create and Last updated Display
		VendorApproval.setCreatedBy(Approval.getCreatedBy());
		if (Approval.getCreated() != null) {
			VendorApproval.setCreated(CreatedDateTime.format(Approval.getCreated()));
		}
		VendorApproval.setLastModifiedBy(Approval.getLastModifiedBy());
		if (Approval.getLastupdated() != null) {
			VendorApproval.setLastupdated(CreatedDateTime.format(Approval.getLastupdated()));
		}

		return VendorApproval;
	}
*/	
	public VendorApprovalDto Show_VendorApprovalDetails(VendorApprovalDto Approval) throws Exception {
		VendorApprovalDto VendorApproval = new VendorApprovalDto();

		VendorApproval.setApprovalId(Approval.getApprovalId());
		VendorApproval.setApprovalNumber(Approval.getApprovalNumber());
		VendorApproval.setApprovalvendorID(Approval.getApprovalvendorID());
		VendorApproval.setApprovalvendorName(Approval.getApprovalvendorName());
		VendorApproval.setApprovalvendorLocation(Approval.getApprovalvendorLocation());

		VendorApproval.setApprovalTotal(Double.parseDouble(toFixedTwo.format(Approval.getApprovalTotal())));
		VendorApproval.setApprovalCreateDate(Approval.getApprovalCreateDate());
		VendorApproval.setApprovalCreateBy(Approval.getApprovalCreateBy());

		VendorApproval.setApprovalStatusId(Approval.getApprovalStatusId());
		VendorApproval.setApprovalStatus(FuelVendorPaymentMode.getPaymentMode(Approval.getApprovalStatusId()));
		VendorApproval.setApprovalPlaceId(Approval.getApprovalPlaceId());
		VendorApproval.setApprovalPlace(ApprovalType.getApprovalType(Approval.getApprovalPlaceId()));
		VendorApproval.setApprovalPaymentTypeId(Approval.getApprovalPaymentTypeId());
		VendorApproval.setApprovalPaymentType(PaymentTypeConstant.getPaymentTypeName(Approval.getApprovalPaymentTypeId()));
		VendorApproval.setApprovalPayNumber(Approval.getApprovalPayNumber());
		VendorApproval.setApprovalpaidby(Approval.getApprovalpaidby());
		VendorApproval.setApprovalPaidTotal(Approval.getApprovalPaidTotal());

		if (Approval.getApprovalDateofpaymentOn() != null) {
			VendorApproval.setApprovalDateofpayment(dateFormat_Name.format(Approval.getApprovalDateofpaymentOn()));
		}
		// Create and Last updated Display
		VendorApproval.setCreatedBy(Approval.getCreatedBy());
		if (Approval.getCreatedOn() != null) {
			VendorApproval.setCreated(CreatedDateTime.format(Approval.getCreatedOn()));
		}
		VendorApproval.setLastModifiedBy(Approval.getLastModifiedBy());
		if (Approval.getLastupdatedOn() != null) {
			VendorApproval.setLastupdated(CreatedDateTime.format(Approval.getCreatedOn()));
		}
		VendorApproval.setApprovalCreateDateStr(Approval.getApprovalCreateDateStr());
		VendorApproval.setVendorTDSPercent(Approval.getVendorTDSPercent());
		VendorApproval.setTDSAmount(Approval.getTDSAmount());
		return VendorApproval;
	}
	
	
	public VendorSubApprovalDetails prepareVendorSubApprovalDetails(VendorApproval approval, PendingVendorPayment	pendingVendorPayment) {

		VendorSubApprovalDetails vendorSubApprovalDetails = new VendorSubApprovalDetails();

		vendorSubApprovalDetails.setApprovalId(approval.getApprovalId());
		vendorSubApprovalDetails.setInvoiceId(pendingVendorPayment.getTransactionId());
		vendorSubApprovalDetails.setSubApprovalTotal(pendingVendorPayment.getBalanceAmount());
		vendorSubApprovalDetails.setTransactionNumber(pendingVendorPayment.getTransactionNumber());
		vendorSubApprovalDetails.setApprovalPlaceId(pendingVendorPayment.getTxnTypeId());
		vendorSubApprovalDetails.setCompanyId(approval.getCompanyId());
		vendorSubApprovalDetails.setMarkForDelete(false);
		vendorSubApprovalDetails.setInvoiceNumber(pendingVendorPayment.getInvoiceNumber());
		vendorSubApprovalDetails.setInvoiceDate(pendingVendorPayment.getTransactionDate());
		vendorSubApprovalDetails.setApprovedPaymentStatusId(PendingPaymentType.PAYMENT_STATUS_APPROVAL_CREATED);
		vendorSubApprovalDetails.setVid(pendingVendorPayment.getVid());
		
		return vendorSubApprovalDetails;
	}
	
	public static VendorApproval getVendorApprovalDTO(Vendor		vendor, CustomUserDetails	userDetails) {
		SimpleDateFormat formatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
		VendorApproval vendorApproval = new VendorApproval();

		vendorApproval.setApprovalvendorID(vendor.getVendorId());

		vendorApproval.setCompanyId(userDetails.getCompany_id());
		vendorApproval.setCreatedById(userDetails.getId());
		vendorApproval.setLastModifiedById(userDetails.getId());
		vendorApproval.setMarkForDelete(false);
		vendorApproval.setApprovalCreateById(userDetails.getId());
		vendorApproval.setApprovalStatusId(FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL);
		try {
			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			java.util.Date dateTo = formatTime.parse(ft.format(currentDate));

			java.sql.Date toDate = new java.sql.Date(dateTo.getTime());
			vendorApproval.setCreated(toDate);
			vendorApproval.setLastupdated(toDate);
			vendorApproval.setApprovalCreateDate(toDate);
			
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return vendorApproval;
	
	}

	public List<VendorSubApprovalDetailsDto>  getFinalVendorSubApprovalList(List<VendorSubApprovalDetails>  subApprovalList) throws Exception{
		try {
			List<VendorSubApprovalDetailsDto> 	detailsDtos	= null;
			VendorSubApprovalDetailsDto		approvalDetailsDto		= null;
			if(subApprovalList != null && !subApprovalList.isEmpty()) {
				detailsDtos	= new ArrayList<VendorSubApprovalDetailsDto>();
				for (VendorSubApprovalDetails vendorSubApprovalDetails : subApprovalList) {
					approvalDetailsDto	= new VendorSubApprovalDetailsDto();
					
					approvalDetailsDto.setTransactionNumber(vendorSubApprovalDetails.getTransactionNumber());
					approvalDetailsDto.setInvoiceId(vendorSubApprovalDetails.getInvoiceId());
					approvalDetailsDto.setApprovalPlaceId(vendorSubApprovalDetails.getApprovalPlaceId());
					approvalDetailsDto.setInvoiceNumber(vendorSubApprovalDetails.getInvoiceNumber());
					approvalDetailsDto.setInvoiceDateStr(dateFormat.format(vendorSubApprovalDetails.getInvoiceDate()));
					approvalDetailsDto.setSubApprovalTotal(vendorSubApprovalDetails.getSubApprovalTotal());
					approvalDetailsDto.setSubApprovalPaidAmount(vendorSubApprovalDetails.getSubApprovalpaidAmount());
					approvalDetailsDto.setApprovalPaymentTypeId(vendorSubApprovalDetails.getApprovalPaymentTypeId());
					approvalDetailsDto.setExpectedPaymentDateStr(dateFormat.format(vendorSubApprovalDetails.getExpectedPaymentDate()));
					approvalDetailsDto.setSubApprovalId(vendorSubApprovalDetails.getSubApprovalId());
					approvalDetailsDto.setApprovalId(vendorSubApprovalDetails.getApprovalId());
					
				}
			}
			return detailsDtos;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public VendorApproval  getVendorApprovalDTOForPayment(ValueObject		valueObject, VendorApproval	approval) throws Exception {
		try {
			approval.setApprovalPaymentTypeId(valueObject.getShort("approvalPaymentTypeId"));
			approval.setApprovalPayNumber(valueObject.getString("approvalPayNumber"));
			approval.setApprovalDateofpayment(dateFormat.parse(valueObject.getString("paymentDate")));
			approval.setApprovalpaidbyId(valueObject.getLong("approvalpaidbyId"));
			approval.setLastModifiedById(valueObject.getLong("approvalpaidbyId"));
			approval.setLastupdated(new Date());
			approval.setApprovalStatusId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
			approval.setTallyCompanyId(valueObject.getLong("tallyCompanyId",0));
			approval.setApprovalPaidTotal(valueObject.getDouble("approvedAmount",0));
			approval.setApprovalTDSAmount(valueObject.getDouble("approvalTDSAmount",0));
		} catch (Exception e) {
			System.err.println("exception : "+ e);
		}
		
		return approval;
	}
 }
