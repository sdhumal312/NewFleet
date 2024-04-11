package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.BankPaymentDto;
import org.fleetopgroup.persistence.model.BankPayment;
import org.fleetopgroup.persistence.model.BankPaymetDetailsToIv;
import org.fleetopgroup.web.util.ValueObject;

public interface IBankPaymentService {
	
	
	public BankPayment addBankPaymentDetails(BankPaymentDto bankPaymentDto);
	
	public void updateBankPaymentDetails(ValueObject object, BankPaymentDto bankPaymentDto) throws Exception;
	
	public void updatePaymentDetails(ValueObject object, BankPaymentDto bankPaymentDto) throws Exception;
	
	public void addBankPaymentDetailsAndSendToIv(BankPaymentDto bankPaymentDto,ValueObject object) throws Exception;
	
	public BankPaymentDto getBankPaymentDetailsDto(Long moduleId,short moduleIdentifier);
	
	public void deleteBankPaymentDetailsIfTransactionDeleted(long transactionId,short moduleIdentifier,int companyId,long userId,boolean fromIncome) ;
	
	public void addBankPaymentDetailsFromValueObject(ValueObject object);
	
	public void updatePaymentDetailsFromValuObject(ValueObject object) throws Exception;
	
	public void sendBankStateMentToIv(List<BankPaymetDetailsToIv> ivList);
	
	public void deleteAllStatmentDetailsFromTrip(long tripSheetId,int companyId,long userId);

	public void delete_Bank_PaymentDetails(Long fuel_id, short fuelEntry, Integer company_id, long id) throws Exception;
}

