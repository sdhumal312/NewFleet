package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.report.dao.DriverLedgerDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antkorwin.xsync.XSync;

@Service
public class DriverLedgerDaoImpl implements DriverLedgerDao{
	
	@Autowired	private XSync<Long> 		xSync;
	@Autowired	private EntityManager		entityManager;

	@Override
	@Transactional
	public void updateOpeningClosingBalance(Long driverId, Timestamp txnDateTime, Double amount) throws Exception {
		try {
			xSync.execute(driverId, () -> {
				entityManager.createQuery(" UPDATE DriverLedger SET openingBalance = openingBalance + "+amount+", "
						+ " closingBalance = closingBalance + "+amount+" "
						+ " where txnDateTime > '"+DateTimeUtility.getDateFromTimeStamp(txnDateTime, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"'  AND markForDelete = 0 " 
				).executeUpdate();
			});
		} catch (Exception e) {
			throw e;
		}
	}
}
