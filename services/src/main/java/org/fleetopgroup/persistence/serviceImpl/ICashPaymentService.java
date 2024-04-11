package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.BankPaymetDetailsToIv;
import org.fleetopgroup.persistence.model.CashPaymentStatement;
import org.fleetopgroup.web.util.ValueObject;

public interface ICashPaymentService {
	
	public void deleteCashStateMentEntry(long moduleId,short moduleIdentifier,int companyId,long userId,boolean fromIncome);
	
	public void updateCashPaymentStatement(ValueObject object) throws Exception;
	
	public void saveCashPaymentSatement(ValueObject object) throws Exception;
	
	public void sendCashPaymentListToIV(List<CashPaymentStatement> cashPaymentStatementList);
	
	public void sendCashlessEntryForOnlinesTransactions(List<BankPaymetDetailsToIv> bankPaymetDetailsToIv) ;
	

}
