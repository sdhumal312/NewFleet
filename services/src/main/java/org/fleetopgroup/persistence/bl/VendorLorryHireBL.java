package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.constant.LorryHirePaymentStatus;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.LorryHirePaymentApprovalToHireDetails;
import org.fleetopgroup.persistence.model.VendorLorryHirePaymentApproval;
import org.fleetopgroup.persistence.model.VendorMarketHireToCharges;
import org.fleetopgroup.persistence.model.VendorMarketLorryHireDetails;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.security.core.context.SecurityContextHolder;

public class VendorLorryHireBL {
	
	
	public VendorMarketLorryHireDetails	getVendorMarketLorryHireDetails(ValueObject	valueObject) throws Exception{
		VendorMarketLorryHireDetails		hireDetails		= null;
		CustomUserDetails					userDetails		= null;
		double								otherCharges	= 0.0;
		try {

			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			
			hireDetails	= new VendorMarketLorryHireDetails();
			
			hireDetails.setVendorId(valueObject.getInt("vendorId"));
			hireDetails.setVid(valueObject.getInt("vid"));
			hireDetails.setLorryHire(valueObject.getDouble("lorryHire"));
			hireDetails.setAdvanceAmount(valueObject.getDouble("advanceAmount"));
			hireDetails.setPaidAmount(valueObject.getDouble("advanceAmount"));
			hireDetails.setBalanceAmount(hireDetails.getLorryHire() - hireDetails.getAdvanceAmount());
			if(hireDetails.getLorryHire() > hireDetails.getAdvanceAmount())
				hireDetails.setPaymentStatusId(LorryHirePaymentStatus.PAYMENT_MODE_NOT_PAID);
			else
				hireDetails.setPaymentStatusId(LorryHirePaymentStatus.PAYMENT_MODE_PAID);
			hireDetails.setHireDate(DateTimeUtility.getDateTimeFromStr(valueObject.getString("date"), DateTimeUtility.DD_MM_YYYY));
			hireDetails.setCreatedById(userDetails.getId());
			hireDetails.setLastModifiedById(userDetails.getId());
			hireDetails.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			hireDetails.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
			hireDetails.setCompanyId(userDetails.getCompany_id());
			hireDetails.setOtherCharges(otherCharges);
			hireDetails.setDriverId(valueObject.getLong("driverId",0));
			hireDetails.setTripSheetId(valueObject.getLong("tripSheetId",0));
			hireDetails.setRemark(valueObject.getString("remark"));
			hireDetails.setIncomeId(valueObject.getInt("incomeId"));
			
			return hireDetails;
		} catch (Exception e) {
			throw e;
		}finally {
			hireDetails					= null;
			userDetails					= null;
		}
	}
	
	public VendorMarketHireToCharges	getVendorMarketHireToCharges(ValueObject	valueObject, ValueObject	charges) throws Exception{
		VendorMarketHireToCharges	vendorMarketHireToCharges	= null;
		CustomUserDetails					userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			
			if(charges.get("amount") != null && charges.get("amount") != "") {
				
				vendorMarketHireToCharges	= new VendorMarketHireToCharges();
				
				vendorMarketHireToCharges.setChargeMasterId(charges.getInt("expenseId"));
				vendorMarketHireToCharges.setAmount(charges.getDouble("amount"));
				vendorMarketHireToCharges.setIdentifier((short) 1);
				vendorMarketHireToCharges.setCreatedById(userDetails.getId());
				vendorMarketHireToCharges.setLastModifiedById(userDetails.getId());
				vendorMarketHireToCharges.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
				vendorMarketHireToCharges.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
				vendorMarketHireToCharges.setCompanyId(userDetails.getCompany_id());
			}
			
			
			
			return vendorMarketHireToCharges;
		} catch (Exception e) {
			throw e;
		}finally {
			vendorMarketHireToCharges	= null;
			userDetails					= null;
		}
	}
	
	public VendorLorryHirePaymentApproval	getVendorLorryHirePaymentApproval(ValueObject	valueObject) throws Exception{
		VendorLorryHirePaymentApproval		hireDetails		= null;
		CustomUserDetails					userDetails		= null;
		try {
			
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			
			hireDetails	= new VendorLorryHirePaymentApproval();
			
			hireDetails.setVendorId(valueObject.getInt("vendorId"));
			hireDetails.setTotalAmount(valueObject.getDouble("totalAmount"));
			hireDetails.setPaymentReceivedById(userDetails.getId());
			hireDetails.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			hireDetails.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
			hireDetails.setCompanyId(userDetails.getCompany_id());
			
			return hireDetails;
		} catch (Exception e) {
			throw e;
		}finally {
			hireDetails					= null;
			userDetails					= null;
		}
	}
	
	public LorryHirePaymentApprovalToHireDetails	getLorryHirePaymentApprovalToHireDetails(ValueObject	valueObject, ValueObject	charges) throws Exception{
		LorryHirePaymentApprovalToHireDetails	approvalToHireDetails		= null;
		CustomUserDetails						userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			
				
			approvalToHireDetails	= new LorryHirePaymentApprovalToHireDetails();
			
			approvalToHireDetails.setLorryHirePaymentId(valueObject.getLong("lorryHirePaymentId"));
			approvalToHireDetails.setLorryHireDetailsId(charges.getLong("lorryHireDetailsId"));
			approvalToHireDetails.setReceiveAmount(charges.getDouble("receiveAmt"));
			approvalToHireDetails.setPaymentTypeId(charges.getShort("paymentMode"));
			approvalToHireDetails.setPaymentStatusId(charges.getShort("paymentType"));
			approvalToHireDetails.setTransactionNumber(charges.getString("txnNo"));
			approvalToHireDetails.setTransactionDate(DateTimeUtility.getTimeStamp(charges.getString("txnDate"), DateTimeUtility.DDMMYYYY));
			approvalToHireDetails.setRemark(charges.getString("remark"));
			approvalToHireDetails.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			approvalToHireDetails.setCompanyId(userDetails.getCompany_id());
			
			
			return approvalToHireDetails;
		} catch (Exception e) {
			throw e;
		}finally {
			approvalToHireDetails		= null;
			userDetails					= null;
		}
	}
	
	//newy start
	public VendorMarketHireToCharges saveExpenseDetailsInfo(ValueObject object, ValueObject valueObject) throws Exception{
		
		VendorMarketHireToCharges		chargeTransfer				= null;
		CustomUserDetails				userDetails					= null;
		long 							lorryHireDetailsId 			= 0;
		double							amount						= 0;
		int								chargeMasterId				= 0;
		
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			lorryHireDetailsId =  valueObject.getLong("lorryHireDetailsId");
			amount			   = object.getDouble("routeAmount");
			chargeMasterId	   = object.getInt("Expense");
			
			chargeTransfer	= new VendorMarketHireToCharges();
								
			chargeTransfer.setAmount(amount);								
			chargeTransfer.setChargeMasterId(chargeMasterId);				
			chargeTransfer.setCompanyId(userDetails.getCompany_id());	
			chargeTransfer.setCreatedById(userDetails.getId());          	
			chargeTransfer.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());	
			chargeTransfer.setIdentifier((short) 1);
			chargeTransfer.setLastModifiedById(userDetails.getId());		
			chargeTransfer.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());		
			chargeTransfer.setLorryHireDetailsId(lorryHireDetailsId);		
			chargeTransfer.setMarkForDelete(false);						   
			
			return chargeTransfer;
			
		} catch (Exception e) {
			throw e;
		}finally {
			chargeTransfer				= null;
			userDetails					= null;
		}
	}
	
	
	//newy stop
	
}
