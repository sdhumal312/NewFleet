package org.fleetopgroup.persistence.report.dao;

import java.sql.Timestamp;

public interface DriverLedgerDao {

	public void updateOpeningClosingBalance(Long driverId, Timestamp txnDateTime, Double amount) throws Exception;
}
