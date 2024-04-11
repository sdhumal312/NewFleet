package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.CashBookVoucherSequenceCounter;
import org.springframework.stereotype.Service;

@Service
public interface ICashBookVoucherSequenceCounterService {

	public CashBookVoucherSequenceCounter getNextVoucherNumber(Integer cashBookId, Integer companyId) throws Exception;
	
	public String	getCashVoucherNumber(Integer cashBookId, Integer companyId) throws Exception;
	
	public String	getCashVoucherNumberAndUpdateNext(Integer cashBookId, Integer companyId) throws Exception;
	
	public CashBookVoucherSequenceCounter getNextVoucherNumberAndUpdateNext(Integer cashBookId, Integer companyId) throws Exception;
	
	public void resetAllCashVoucherSequnces() throws Exception;
}
