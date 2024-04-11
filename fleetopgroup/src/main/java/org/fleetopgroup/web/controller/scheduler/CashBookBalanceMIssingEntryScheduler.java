package org.fleetopgroup.web.controller.scheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dao.UserRepository;
import org.fleetopgroup.persistence.dto.CashBookBalanceDto;
import org.fleetopgroup.persistence.model.CashBook;
import org.fleetopgroup.persistence.model.CashBookBalance;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.model.CashBookSequenceCounter;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookVoucherSequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.web.controller.CashBookEntryController;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.fleetopgroup.constant.CashBookStatus;
import org.fleetopgroup.persistence.bl.CashBookBL;
import org.fleetopgroup.persistence.dao.CloseCashBookConfigRepository;
import org.fleetopgroup.persistence.model.CloseCashBookConfiguration;

@Controller
public class CashBookBalanceMIssingEntryScheduler {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	@Autowired ICashBookService							cashBookService;
	@Autowired ICashBookVoucherSequenceCounterService	bookVoucherSequenceCounterService;
	@Autowired CloseCashBookConfigRepository            closeCBRepository;
	@Autowired UserRepository							userRepository;
	@Autowired ICompanyConfigurationService             companyConfigurationService;
	@Autowired CashBookEntryController              	cashbookCon;
	@Autowired ICashBookSequenceService 				cashBookSequenceService;
	@Autowired ICashBookNameService						cashBookNameService;
	
	SimpleDateFormat 			dateFormat 				= new SimpleDateFormat("dd-MM-yyyy");
	CashBookBL					cashbookbl 						= new CashBookBL();

	//@Scheduled(cron = "0 00 02 * * *")
	public void doScheduledForCashBook() {
		try {
			LOGGER.error("Cash Book checker Started..");

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, -1);
			Date currentDate = cal.getTime();
		
			checkBackDateCashBookEntriesForBalanceAmount(currentDate);

			LOGGER.error("Cash Book checker Ended..");
		} catch (Exception e) {
			LOGGER.error("Cash Book checker ..");
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron="0 1 0 1 1/1 *")
	public void resetCashVoucherSequence() throws Exception {
		try {
			bookVoucherSequenceCounterService.resetAllCashVoucherSequnces();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void checkBackDateCashBookEntriesForBalanceAmount(Date currentDate) throws Exception {
		ValueObject			valueObject		= null;
		try {
			valueObject	= new ValueObject();
			String date		=	dateFormat.format(currentDate);
			
			valueObject.put("startDate", DateTimeUtility.getStartOfDayTimeStamp(date));
			valueObject.put("endDate", DateTimeUtility.getEndOfDayTimeStamp(date));
			
			cashBookService.checkCashBookBalanceForMissingEntries(valueObject);
			
		} catch (Exception e) {
			throw e;
		}finally {
			
		}
		
	}
	
	
	//@Scheduled(cron = "0 * * * * *")
	@Scheduled(cron = "0 0 9 * * *")
	public void closeCashBookBalanceEveryDay() throws Exception {
	
		List<CloseCashBookConfiguration>  closeCBConfigList 	= null;
		CloseCashBookConfiguration        closeCashBook 		= null;
		CashBookBalance 	   		 	  cashBookBalObj	 	= null;
		Integer 					      companyId    			= null;
		ValueObject					      object 				= null;
		CashBookName  				      cashbook				= null;
		CashBookBalanceDto  			  cashBookBal			= null;
		CashBookSequenceCounter 		  sequenceCounter 		= null;
		try {
			object = new ValueObject();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, -10);
			Date currentDate = cal.getTime();
			
			closeCBConfigList  = closeCBRepository.findAll();

			for(int i=0;i<closeCBConfigList.size();i++) {
				closeCashBook  = closeCBConfigList.get(i);
				companyId = closeCashBook.getCompanyId();
				User		user 	= userRepository.findUserByUserNameAndCompanyId("ADMIN",companyId);
				
				List<CashBookName> cashBookNamelist = cashBookNameService.list_cashBookByCompanyId(companyId);
				
				Collections.reverse(cashBookNamelist);
				
				for(int k=0;k<cashBookNamelist.size();k++) {
					cashbook = cashBookNamelist.get(k);
					cashBookBalObj = cashBookService.getAllTodayCashBookBalance(currentDate, cashbook.getNAMEID(),companyId,CashBookStatus.CASH_BOOK_STATUS_OPEN); 
					
					
					if(cashBookBalObj !=null) {
							cashBookBal =  cashbookbl.prepare_CashBookBalanceDto(cashBookBalObj);
							cashBookBal.setCASH_CLOSED_BY(user.getFirstName());
							cashBookBal.setCASH_CLOSED_EMAIL(user.getEmail());
							
							object = cashBookService.saveCloseCashbookBalance(cashBookBal, true);
							System.err.println("Object "+ object);
					} //if
					else {
						List<CashBook>   CASHBOOK = cashBookService.checkLastDayCashBookCreatedOrNot(currentDate,cashbook.getNAMEID(),companyId);
						
						if(CASHBOOK.isEmpty() || CASHBOOK == null) {
							CashBook  cashbk = cashbookbl.prepareCashBookDetails(currentDate,cashbook.getNAMEID(),cashbook.getCASHBOOK_NAME(),
									user.getFirstName(),user.getEmail(),companyId);
							sequenceCounter = cashBookSequenceService.findNextSequenceNumber(companyId);
							
							cashbk.setCASH_NUMBER(sequenceCounter.getNextVal());
							cashBookService.registerNewCashBook(cashbk);
						}
						
						cashBookBal = cashbookbl.prepareTodayCashBookBalanceDto(currentDate, cashbook.getNAMEID(),cashbook.getCASHBOOK_NAME(),companyId);
						cashBookBal.setCASH_CLOSED_BY(user.getFirstName());
						cashBookBal.setCASH_CLOSED_EMAIL(user.getEmail());
						
						cashBookService.registerNewCashBookBalance(cashbookbl.prepareCashBookBalanceDetails(cashBookBal));
						
						object = cashBookService.saveCloseCashbookBalance(cashBookBal, true);
						
						System.err.println("object "+ object);
					}
				}//inner for loop
				
			}//outer for loop
			
		}//try
		catch(Exception e) {
			
		}
	}
	
}
