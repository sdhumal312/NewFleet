package org.fleetopgroup.persistence.bl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.constant.IVPaymentTypeMapper;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.persistence.dto.BankPaymentDto;
import org.fleetopgroup.persistence.model.BankPayment;
import org.fleetopgroup.persistence.model.BankPaymentHistory;
import org.fleetopgroup.persistence.model.BankPaymetDetailsToIv;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Component;

@Component
public class BankPaymentBL {

	public BankPayment prepareBankPayment(BankPaymentDto dto) throws Exception {
		BankPayment payment = new BankPayment();
		payment.setModuleId(dto.getModuleId());
		payment.setModuleIdentifier(dto.getModuleIdentifier());
		payment.setAmount(dto.getAmount());
		payment.setBankAccountId(dto.getBankAccountId());
		payment.setBankPaymentId(dto.getBankPaymentId());
		payment.setBranchId(dto.getBranchId());
		//payment.setCardNo(dto.getCardNo());
		payment.setChequeGivenBy(dto.getChequeGivenBy());
		//payment.setChequeGivenTo(dto.getChequeGivenTo());
		payment.setMobileNumber(dto.getMobileNumber());
		payment.setPayerName(dto.getPayerName());
		if(dto.getBankPaymentType() != null && !dto.getBankPaymentType().equals("")) {
			payment.setPaymentTypeId(Short.valueOf(dto.getBankPaymentType()));
		}else {
			payment.setPaymentTypeId(dto.getBankPaymentTypeId());
		}
		if(dto.getTransactionDateStr() != null && !dto.getTransactionDateStr().equals(""))
		payment.setTransactionDate(DateTimeUtility.getDateFromString(dto.getTransactionDateStr(),DateTimeUtility.DD_MM_YYYY));
		else
		payment.setTransactionDate(dto.getTransactionDate());
		
		if(dto.getChequeTransactionDateStr() != null && !dto.getChequeTransactionDateStr().equals(""))
		payment.setChequeTransactionDate(DateTimeUtility.getDateFromString(dto.getChequeTransactionDateStr(),DateTimeUtility.DD_MM_YYYY));
		
		payment.setTransactionNumber(dto.getTransactionNumber());
		payment.setCompanyId(dto.getCompanyId());
		payment.setUpiId(dto.getUpiId());
		if(dto.getCreateOn() != null)
			payment.setCreateOn(dto.getCreateOn());
		else
			payment.setCreateOn(new Date());

		payment.setCreatedBy(dto.getCreatedBy());
		payment.setPartyAccountNumber(dto.getPartyAccountNumber());
		payment.setPartyBankId(dto.getPartyBankId());
		payment.setModuleNo(dto.getModuleNo());
		
		
		return payment;
	}
	
	public BankPayment prepareBankPaymentFromValueObject(ValueObject object) throws Exception {

		BankPayment payment = new BankPayment();
		payment.setModuleId(object.getLong("moduleId"));
		payment.setModuleIdentifier(object.getShort("moduleIdentifier"));
		payment.setAmount(object.getDouble("amount"));
		payment.setBankAccountId(object.getLong("bankAccountId"));
		payment.setChequeGivenBy(object.getString("chequeGivenBy","")); 
		payment.setMobileNumber(object.getString("mobileNumber",""));
		payment.setPayerName(object.getString("payerName",""));
		payment.setPaymentTypeId(object.getShort("bankPaymentTypeId",object.getShort("currentPaymentTypeId")));
		
		if(object.getString("transactionDate",null) != null) 
		payment.setTransactionDate(DateTimeUtility.getDateFromString(object.getString("transactionDate"),DateTimeUtility.DD_MM_YYYY));
		else
		payment.setTransactionDate(new Date());	
		
		if(object.getString("chequeTransactionDate",null) != null) 
		payment.setChequeTransactionDate(DateTimeUtility.getDateFromString(object.getString("chequeTransactionDate"),DateTimeUtility.DD_MM_YYYY));
		
		payment.setTransactionNumber(object.getString("transactionNumber",""));
		payment.setCompanyId(object.getInt("companyId"));
		payment.setUpiId(object.getString("upiId",""));
		payment.setCreateOn(new Date());
		payment.setCreatedBy(object.getLong("userId"));
		payment.setPartyAccountNumber(object.getString("partyAccountNumber",""));
		payment.setPartyBankId(object.getLong("partyBankId"));
		payment.setModuleNo(object.getLong("moduleNo"));
		
		return payment;
	}
	
	public BankPaymentDto prepareBankPaymentDtoFromValueObject(ValueObject object) throws Exception {

		BankPaymentDto payment = new BankPaymentDto();
		payment.setModuleId(object.getLong("moduleId"));
		payment.setModuleIdentifier(object.getShort("moduleIdentifier"));
		payment.setAmount(object.getDouble("amount"));
		payment.setBankAccountId(object.getLong("bankAccountId",0));
		payment.setChequeGivenBy(object.getString("chequeGivenBy","")); 
		payment.setMobileNumber(object.getString("mobileNumber",""));
		payment.setPayerName(object.getString("payerName",""));
		payment.setBankPaymentTypeId(object.getShort("currentPaymentTypeId",object.getShort("bankPaymentTypeId")));
		payment.setModuleNo(object.getLong("moduleNo",0));
		if(object.getString("transactionDate",null) != null) 
		payment.setTransactionDate(DateTimeUtility.getDateFromString(object.getString("transactionDate"),DateTimeUtility.DD_MM_YYYY));
		else
		payment.setTransactionDate(new Date());	
		
		if(object.getString("chequeTransactionDate",null) != null && object.getString("chequeTransactionDate").trim().equals(""))
		payment.setChequeTransactionDate(DateTimeUtility.getDateFromString(object.getString("chequeTransactionDate"),DateTimeUtility.DD_MM_YYYY));
		payment.setChequeTransactionDateStr(object.getString("chequeTransactionDate"));
		
		payment.setTransactionNumber(object.getString("transactionNumber",""));
		payment.setCompanyId(object.getInt("companyId"));
		payment.setUpiId(object.getString("upiId",""));
		payment.setCreateOn(new Date());
		payment.setCreatedBy(object.getLong("userId"));
		payment.setPartyAccountNumber(object.getString("partyAccountNumber",""));
		payment.setPartyBankId(object.getLong("partyBankId",0));
		
		return payment;
	}
	
	public BankPaymentHistory prepareBankPaymentHistory(BankPaymentDto payment) {
		BankPaymentHistory paymentHistory = new BankPaymentHistory();
		
		paymentHistory.setModuleId(payment.getModuleId());
		paymentHistory.setModuleIdentifier(payment.getModuleIdentifier());
		paymentHistory.setAmount(payment.getAmount());
		paymentHistory.setBankAccountId(payment.getBankAccountId());
		paymentHistory.setBankPaymentId(payment.getBankPaymentId());
		paymentHistory.setBranchId(payment.getBranchId());
		paymentHistory.setCardNo(payment.getCardNo());
		paymentHistory.setChequeGivenBy(payment.getChequeGivenBy());
		paymentHistory.setChequeGivenTo(payment.getChequeGivenTo());
		paymentHistory.setMobileNumber(payment.getMobileNumber());
		paymentHistory.setPayeeName(payment.getPayeeName());
		paymentHistory.setPayerName(payment.getPayerName());
		paymentHistory.setPaymentTypeId(payment.getBankPaymentTypeId());
		paymentHistory.setTransactionDate(payment.getTransactionDate());
		paymentHistory.setTransactionNumber(payment.getTransactionNumber());
		paymentHistory.setCompanyId(payment.getCompanyId());
		paymentHistory.setUpiId(payment.getUpiId());
		paymentHistory.setLastUpdatedBy(payment.getLastUpdatedBy());
		paymentHistory.setLastUpdatedOn(payment.getLastUpdatedOn());
		paymentHistory.setPartyAccountNumber(payment.getPartyAccountNumber());
		paymentHistory.setPartyBankId(payment.getPartyBankId());
		return paymentHistory;
	}
	
	public BankPaymentDto preapreBankPaymentDto(BankPayment payment) {
		
		BankPaymentDto dto = new BankPaymentDto();
		dto.setModuleId(payment.getModuleId());
		dto.setModuleIdentifier(payment.getModuleIdentifier());
		dto.setAmount(payment.getAmount());
		dto.setBankAccountId(payment.getBankAccountId());
		dto.setBankPaymentId(payment.getBankPaymentId());
		dto.setBranchId(payment.getBranchId());
		//dto.setCardNo(payment.getCardNo());
		dto.setChequeGivenBy(payment.getChequeGivenBy());
		//dto.setChequeGivenTo(payment.getChequeGivenTo());
		dto.setMobileNumber(payment.getMobileNumber());
		dto.setPayerName(payment.getPayerName());
		dto.setBankPaymentTypeId(payment.getPaymentTypeId());
		dto.setTransactionDate(payment.getTransactionDate());
		dto.setTransactionNumber(payment.getTransactionNumber());
		dto.setCompanyId(payment.getCompanyId());
		dto.setUpiId(payment.getUpiId());
		dto.setCreateOn(payment.getCreateOn());
		dto.setCreatedBy(payment.getCreatedBy());
		dto.setPartyAccountNumber(payment.getPartyAccountNumber());
		return dto;
	}	
	public List<BankPaymetDetailsToIv> prepareBankPaymetDetailsToIvList(List<BankPaymentDto> bankPaymentDtoList) {
		List<BankPaymetDetailsToIv> bankPaymetDetailsToIv = new ArrayList<>();	
		BankPaymetDetailsToIv paymetDetails = null; 
		for(BankPaymentDto bankPayment:bankPaymentDtoList) {
			paymetDetails = new BankPaymetDetailsToIv();
			//paymetDetails.setBankAccount(bankPayment.getBankAccount());
			paymetDetails.setBankAccountNumber(bankPayment.getBankAccountNumber());
			paymetDetails.setBankPaymentTypeId(IVPaymentTypeMapper.getIVPaymentTypeId(bankPayment.getBankPaymentTypeId()));
			paymetDetails.setModuleIdentifier(bankPayment.getModuleIdentifier());
			paymetDetails.setModuleId(bankPayment.getModuleId());
			paymetDetails.setIncomeAmount(bankPayment.getIncomeAmount());
			paymetDetails.setExpenseAmount(bankPayment.getExpenseAmount());
			paymetDetails.setCardNo(bankPayment.getCardNo());
			paymetDetails.setChequeGivenBy(bankPayment.getChequeGivenBy());
			paymetDetails.setChequeGivenTo(bankPayment.getChequeGivenTo());
			paymetDetails.setCompanyId(bankPayment.getIvGroupId());
			paymetDetails.setMobileNumber(bankPayment.getMobileNumber());
			paymetDetails.setPartyAccountNumber(bankPayment.getPartyAccountNumber());
			paymetDetails.setPayeeName(bankPayment.getPayeeName());
			paymetDetails.setPayerName(bankPayment.getPayerName());
			paymetDetails.setTransactionDateStr(bankPayment.getTransactionDateStr());
			//paymetDetails.setTransactionDate(bankPayment.getTransactionDate());
			paymetDetails.setTransactionNumber(bankPayment.getTransactionNumber());
			paymetDetails.setUpiId(bankPayment.getUpiId());
			paymetDetails.setPartyBankId(bankPayment.getPartyBankId());
			if(bankPayment.getIvBranchId() != null)
			paymetDetails.setBranchId((int)(long)bankPayment.getIvBranchId());
			paymetDetails.setModuleNo(ModuleConstant.getNumberPrefixByModuleIdentfier(bankPayment.getModuleIdentifier())+"-"+bankPayment.getModuleNo());
			paymetDetails.setDetails(ModuleConstant.getModuleNameByModuleIdentifier(bankPayment.getModuleIdentifier()));
			if(bankPayment.getRemark() != null)
			paymetDetails.setRemark(bankPayment.getRemark()+paymetDetails.getModuleNo());
			paymetDetails.setBankAccount(bankPayment.getBankAccount());
			if(paymetDetails.getIncomeAmount() > 0 || paymetDetails.getExpenseAmount() > 0) {
				bankPaymetDetailsToIv.add(paymetDetails);
				
			}
		}
		return bankPaymetDetailsToIv;
	}
}