package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.fleetopgroup.persistence.dao.CashBookVoucherSequenceCounterRepository;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.model.CashBookVoucherSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookVoucherSequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

@Service
public class CashBookVoucherSequenceCounterService implements ICashBookVoucherSequenceCounterService {

	@Autowired	CashBookVoucherSequenceCounterRepository		cashBookVoucherSequenceCounterRepository;
	@Autowired	ICashBookNameService							cashBookNameService;
	
	SimpleDateFormat	format	= new SimpleDateFormat("dd-MMM-yyyy");
	
	@Override
	@Transactional
	public  CashBookVoucherSequenceCounter getNextVoucherNumber(Integer cashBookId, Integer companyId) throws Exception {
		CashBookVoucherSequenceCounter		sequenceCounter		= null;
		try {
			sequenceCounter		= cashBookVoucherSequenceCounterRepository.getNextVoucherNumber(cashBookId, companyId);
			
			return sequenceCounter;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public String getCashVoucherNumber(Integer cashBookId, Integer companyId) throws Exception {
		CashBookVoucherSequenceCounter		sequenceCounter		= null;
		String								voucherNumber		= null;
		CashBookName						cashBookName		= null;
		try {
			
			sequenceCounter		= getNextVoucherNumber(cashBookId, companyId);
			cashBookName		= cashBookNameService.get_CashBookName(cashBookId);
			
			if(sequenceCounter != null) {
				Date	date	= new Date();
				String month	= format.format(date).substring(3, 6)+"-"+format.format(date).substring(7, 11);
				if(cashBookName != null && (cashBookName.getCASHBOOK_CODE() != null && !cashBookName.getCASHBOOK_CODE().trim().equals(""))) {
					voucherNumber	= cashBookName.getCASHBOOK_CODE()+"-"+month+"-"+sequenceCounter.getNextVal();
				}else {
					voucherNumber	= cashBookName.getCASHBOOK_NAME().substring(0, 3)+"-"+month+"-"+sequenceCounter.getNextVal();
				}
			}
			
			return voucherNumber;
		} catch (Exception e) {
			throw e;
		}finally {
			sequenceCounter		= null;
			voucherNumber		= null;
			cashBookName		= null;
		}
	}
	
	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED )
	public  String getCashVoucherNumberAndUpdateNext(Integer cashBookId, Integer companyId) throws Exception {
		CashBookVoucherSequenceCounter		sequenceCounter		= null;
		String								voucherNumber		= null;
		CashBookName						cashBookName		= null;
		try {
			
			sequenceCounter		= getNextVoucherNumberAndUpdateNext(cashBookId, companyId);
			cashBookName		= cashBookNameService.get_CashBookName(cashBookId);
			
			if(sequenceCounter != null) {
				Date	date	= new Date();
				String month	= format.format(date).substring(3, 6)+"-"+format.format(date).substring(7, 11);
				if(cashBookName != null && (cashBookName.getCASHBOOK_CODE() != null && !cashBookName.getCASHBOOK_CODE().trim().equals(""))) {
					voucherNumber	= cashBookName.getCASHBOOK_CODE()+"-"+month+"-"+sequenceCounter.getNextVal();
				}else {
					voucherNumber	= cashBookName.getCASHBOOK_NAME().substring(0, 3)+"-"+month+"-"+sequenceCounter.getNextVal();
				}
			}
			
			return voucherNumber;
		} catch (Exception e) {
			throw e;
		}finally {
			sequenceCounter		= null;
			voucherNumber		= null;
			cashBookName		= null;
		}
	}
	
	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED )
	public synchronized CashBookVoucherSequenceCounter getNextVoucherNumberAndUpdateNext(Integer cashBookId, Integer companyId)
			throws Exception {
		CashBookVoucherSequenceCounter		sequenceCounter		= null;
		try {
			
				sequenceCounter		= getNextVoucherNumber(cashBookId, companyId);
				
				cashBookVoucherSequenceCounterRepository.updateNextVal(sequenceCounter.getVoucherSequenceCounterId());
				
				return sequenceCounter;
				
			} catch (Exception e) {
				throw e;
			}finally {
				sequenceCounter		= null;
			}
	}
	
	@Override
	@Transactional
	public void resetAllCashVoucherSequnces() throws Exception {
		
		cashBookVoucherSequenceCounterRepository.resetAllCashVoucherSequnces();
	}
}
