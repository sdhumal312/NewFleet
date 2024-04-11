package org.fleetopgroup.web.controller.scheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookVoucherSequenceCounterService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class CashBookBalanceMIssingEntryScheduler {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	@Autowired ICashBookService							cashBookService;
	@Autowired ICashBookVoucherSequenceCounterService	bookVoucherSequenceCounterService;
	
	SimpleDateFormat 			dateFormat 				= new SimpleDateFormat("dd-MM-yyyy");


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
}
