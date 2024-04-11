package org.fleetopgroup.spring;

import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDailyInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Configuration
@EnableScheduling
@Service
public class EventScheduler {
	
	@Autowired IVehicleDailyInspectionService vehicleDailyInspectionService;
	@Autowired IBankPaymentService			bankPaymentService;
	@Autowired ICashPaymentService 			cashPaymentService;

	@Scheduled(cron="0 10 0 * * *")
	public void insertDailyInspection(){
		try {
			vehicleDailyInspectionService.getDailyInspectionList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Scheduled(fixedDelay = 3600000,initialDelay = 3600000)
	public void sendbankCashStatementData() {
		new Thread(()->{
			try {
				bankPaymentService.sendBankStateMentToIv(null);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			}).start();
		
		new Thread(()->{
			try {
			cashPaymentService.sendCashPaymentListToIV(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}
