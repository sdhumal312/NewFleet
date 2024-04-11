package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fleetopgroup.constant.EmiTenureType;
import org.fleetopgroup.constant.VehicleEmiPaymentStatus;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleEmiDetailDto;
import org.fleetopgroup.persistence.model.VehicleEmiDetails;
import org.fleetopgroup.persistence.model.VehicleEmiPaymentDetails;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.security.core.context.SecurityContextHolder;

public class VehicleEmiBL {
	
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	SimpleDateFormat format 		 = new SimpleDateFormat("MM/dd/yyyy");
	
	public VehicleEmiDetails getVehicleEmmiDetailsDto(ValueObject valueObject) throws Exception{
		VehicleEmiDetails			vehicleEmiDetails		= null;
		CustomUserDetails			userDetails				= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleEmiDetails = new VehicleEmiDetails();
			vehicleEmiDetails.setBankAccountId(valueObject.getLong("bankAccountId", 0));
			vehicleEmiDetails.setLoanAmount(valueObject.getDouble("loanAmount", 0));
			vehicleEmiDetails.setDownPaymentAmount(valueObject.getDouble("downPaymentAmount", 0));
			vehicleEmiDetails.setMonthlyEmiAmount(valueObject.getDouble("monthlyEmiAmount", 0));
			vehicleEmiDetails.setInterestRate(valueObject.getDouble("interestRate", 0));
			vehicleEmiDetails.setTenureType(valueObject.getShort("tenureType", (short) 0));
			vehicleEmiDetails.setTenure(valueObject.getInt("tenure", 0));
			vehicleEmiDetails.setVid(valueObject.getInt("vid", 0));
			vehicleEmiDetails.setRemark(valueObject.getString("remark"));
			vehicleEmiDetails.setCreatedById(userDetails.getId());
			vehicleEmiDetails.setLastModifiedById(userDetails.getId());
			vehicleEmiDetails.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			vehicleEmiDetails.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
			vehicleEmiDetails.setCompanyId(userDetails.getCompany_id());
			vehicleEmiDetails.setLoanStartDate(DateTimeUtility.getTimeStamp(valueObject.getString("loanStartDate")));
			vehicleEmiDetails.setLoanEndDate(DateTimeUtility.getTimeStamp(valueObject.getString("loanEndDate")));
			vehicleEmiDetails.setEmiStatus(VehicleEmiPaymentStatus.EMI_STATUS_IN_PROCESS);
			
			return vehicleEmiDetails;
		} catch (Exception e) {
			throw e;
		}finally {
			
		}
	}
	
	public VehicleEmiDetails getVehicleEmmiDetailsUpdateDto(ValueObject valueObject) throws Exception{
		VehicleEmiDetails			vehicleEmiDetails		= null;
		CustomUserDetails			userDetails				= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleEmiDetails = new VehicleEmiDetails();
			vehicleEmiDetails.setBankAccountId(valueObject.getLong("bankAccountId", 0));
			vehicleEmiDetails.setLoanAmount(valueObject.getDouble("loanAmount", 0));
			vehicleEmiDetails.setDownPaymentAmount(valueObject.getDouble("downPaymentAmount", 0));
			vehicleEmiDetails.setMonthlyEmiAmount(valueObject.getDouble("monthlyEmiAmount", 0));
			vehicleEmiDetails.setInterestRate(valueObject.getDouble("interestRate", 0));
			vehicleEmiDetails.setTenureType(valueObject.getShort("tenureType"));
			vehicleEmiDetails.setTenure(valueObject.getInt("tenure"));
			vehicleEmiDetails.setVid(valueObject.getInt("vid"));
			vehicleEmiDetails.setRemark(valueObject.getString("remark"));
			vehicleEmiDetails.setCreatedById(userDetails.getId());
			vehicleEmiDetails.setLastModifiedById(userDetails.getId());
			vehicleEmiDetails.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			vehicleEmiDetails.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
			vehicleEmiDetails.setCompanyId(userDetails.getCompany_id());
			vehicleEmiDetails.setLoanStartDate(DateTimeUtility.getTimeStamp(valueObject.getString("loanStartDate")));
			vehicleEmiDetails.setLoanEndDate(DateTimeUtility.getTimeStamp(valueObject.getString("loanEndDate")));
			vehicleEmiDetails.setVehicleEmiDetailsId(valueObject.getLong("vehicleEmiDetailsId", 0));
			vehicleEmiDetails.setEmiStatus(VehicleEmiPaymentStatus.EMI_STATUS_IN_PROCESS);
			
			return vehicleEmiDetails;
		} catch (Exception e) {
			throw e;
		}finally {
			
		}
	}
	
	
	public VehicleEmiDetailDto getFinalVehicleEmiDetails(VehicleEmiDetailDto emiDetailDto) throws Exception{
		try {
			if(emiDetailDto != null) {
				
				if(emiDetailDto.getLoanStartDate() != null)
					emiDetailDto.setLoanStartDateStr(DateTimeUtility.getDateFromTimeStamp(emiDetailDto.getLoanStartDate(), DateTimeUtility.DD_MM_YYYY));
				if(emiDetailDto.getLoanEndDate() != null)
					emiDetailDto.setLoanEndDateStr(DateTimeUtility.getDateFromTimeStamp(emiDetailDto.getLoanEndDate(), DateTimeUtility.DD_MM_YYYY));
				emiDetailDto.setCreatedOnStr(CreatedDateTime.format(emiDetailDto.getCreatedOn()));
				emiDetailDto.setLastModifiedOnStr(CreatedDateTime.format(emiDetailDto.getLastModifiedOn()));
				emiDetailDto.setTenureTypeStr(EmiTenureType.getName(emiDetailDto.getTenureType()));
			}
			
			return emiDetailDto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public VehicleEmiPaymentDetails setVehicleEmiPayment(ValueObject object, ValueObject value) throws Exception {
		VehicleEmiPaymentDetails			vehicleEmiPaymentDetails		= null;
		CustomUserDetails					userDetails						= null;
		try {
			
			userDetails				 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleEmiPaymentDetails = new VehicleEmiPaymentDetails();
			
			vehicleEmiPaymentDetails.setVehicleEmiDetailsId(object.getLong("vehicleEmiDetailsId"));
			vehicleEmiPaymentDetails.setMonthlyEmiAmount(object.getDouble("monthlyEmiAmount"));
			vehicleEmiPaymentDetails.setVid(value.getInt("vid"));
			vehicleEmiPaymentDetails.setEmiLoanDate(DateTimeUtility.getTimeStamp(object.getString("emiLoanDate"), DateTimeUtility.DD_MM_YYYY));
			vehicleEmiPaymentDetails.setEmiPaidDate(DateTimeUtility.getTimeStamp(object.getString("txnDate"), DateTimeUtility.DDMMYYYY));
			vehicleEmiPaymentDetails.setEmiPaidAmount(object.getDouble("receiveAmt"));
			vehicleEmiPaymentDetails.setEmiPaidRemark(object.getString("remark"));
			vehicleEmiPaymentDetails.setPaymentTypeId(object.getShort("paymentMode"));
			vehicleEmiPaymentDetails.setCreatedById(userDetails.getId());
			vehicleEmiPaymentDetails.setLastModifiedById(userDetails.getId());
			vehicleEmiPaymentDetails.setCreatedOn(new Date());
			vehicleEmiPaymentDetails.setLastModifiedOn(new Date());
			vehicleEmiPaymentDetails.setCompanyId(userDetails.getCompany_id());
			
			return vehicleEmiPaymentDetails;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public VehicleEmiPaymentDetails setPreEMISettlementPayment(ValueObject object) throws Exception {
		VehicleEmiPaymentDetails			vehicleEmiPaymentDetails		= null;
		CustomUserDetails					userDetails						= null;
		try {
			
			userDetails				 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleEmiPaymentDetails = new VehicleEmiPaymentDetails();
			
			vehicleEmiPaymentDetails.setVehicleEmiDetailsId(object.getLong("vehicleEmiDetailsIdSettlement"));
			vehicleEmiPaymentDetails.setMonthlyEmiAmount(object.getDouble("monthlyEmiAmountSettle"));
			vehicleEmiPaymentDetails.setVid(object.getInt("vehicleId"));
			vehicleEmiPaymentDetails.setEmiPaidDate(DateTimeUtility.getTimeStamp(object.getString("chequeDateSettle"), DateTimeUtility.DDMMYYYY));
			vehicleEmiPaymentDetails.setEmiPaidAmount(object.getDouble("receiveAmtSettle"));
			vehicleEmiPaymentDetails.setEmiPaidRemark(object.getString("remarkSettle"));
			vehicleEmiPaymentDetails.setPaymentTypeId(object.getShort("paymentModeSettle"));
			vehicleEmiPaymentDetails.setCreatedById(userDetails.getId());
			vehicleEmiPaymentDetails.setLastModifiedById(userDetails.getId());
			vehicleEmiPaymentDetails.setCreatedOn(new Date());
			vehicleEmiPaymentDetails.setLastModifiedOn(new Date());
			vehicleEmiPaymentDetails.setCompanyId(userDetails.getCompany_id());
			vehicleEmiPaymentDetails.setEmiLoanDate(DateTimeUtility.getTimeStamp(object.getString("chequeDateSettle"), DateTimeUtility.DDMMYYYY));
			
			return vehicleEmiPaymentDetails;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
}