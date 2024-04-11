package org.fleetopgroup.persistence.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dto.DriverLedgerDto;
import org.fleetopgroup.persistence.report.dao.DriverLedgerAccountDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
@Repository
public class DriverLedgerAccountImpl implements DriverLedgerAccountDao {
	
	SimpleDateFormat sqldateTime = new SimpleDateFormat("dd-MM-yyyy");
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext	EntityManager entityManager;
	public List<DriverLedgerDto> getDriverLedgerAccountReportList(String Query, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = null;
		List<DriverLedgerDto> driverLedgerDtoList = null;
		try {
			queryt = entityManager.createQuery(
					" SELECT T.tripSheetNumber, DT.txnDateTime, DT.driverId, DT.transactionAmount, DT.closingBalance, "
							+ " DT.openingBalance, DT.transactionId,DT.driverLedgerId"
							+ " FROM DriverLedger DT "
							+ " INNER JOIN TripSheet AS T ON T.tripSheetID = DT.transactionId"
							+ " WHERE DT.companyId = "+companyId 
							+ " AND DT.markForDelete = 0 " + Query + " ",
					Object[].class);
			List<Object[]> results = queryt.getResultList();
			
			driverLedgerDtoList = new ArrayList<>();
			
			if (results != null && !results.isEmpty()) {
				for (Object[] result : results) {
					DriverLedgerDto   driverLedgerDto = new DriverLedgerDto();
					
					driverLedgerDto.setTripSheetNumber("<a style=\"color:blue\" target=\"_blank\" href=\"showTripSheet.in?tripSheetID="+result[6]+"\">T-"+ result[0]+"</a>");
					driverLedgerDto.setTxnDateTimeStr(sqldateTime.format((Date) result[1]));
					driverLedgerDto.setDriverId((long) result[2]);
					driverLedgerDto.setTransactionAmount((Double) result[3]);
					driverLedgerDto.setClosingBalance((Double) result[4]);
					driverLedgerDto.setOpeningBalance((Double) result[5]);
					driverLedgerDto.setTransactionId((long) result[6]);
					
					
					driverLedgerDtoList.add(driverLedgerDto);
					
					
			}
			}
			

		} catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		}
		return driverLedgerDtoList;
	}

}
