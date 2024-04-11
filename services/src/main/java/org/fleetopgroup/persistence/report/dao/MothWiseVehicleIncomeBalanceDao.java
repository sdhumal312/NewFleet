package org.fleetopgroup.persistence.report.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;

public interface MothWiseVehicleIncomeBalanceDao {

	public void updateMonthBalanceAmount(VehicleProfitAndLossDto	profitAndLossDto) throws Exception;
	
	public void updateMonthWiseVehicleBalanceUpdated(String txnids) throws Exception;
	
	public void deleteMothWiseVehicleIncomeBalance(Integer	vid, Timestamp	startDate, Double	amount) throws Exception;
	
	public void updateMonthBalanceAmount(Integer vid, Timestamp startDate, Double amount) throws Exception;
	
	public VehicleProfitAndLossTxnChecker getVehicleProfitAndLossTxnChecker(Long vehicleProfitAndLossTxnCheckerId) throws Exception;
}
