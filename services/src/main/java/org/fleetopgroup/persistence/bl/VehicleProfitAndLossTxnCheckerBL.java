package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossIncomeTxnChecker;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public class VehicleProfitAndLossTxnCheckerBL {

	
public VehicleProfitAndLossTxnChecker	getVehicleProfitAndLossTxnChecker(ValueObject	valueObject) throws Exception{
	VehicleProfitAndLossTxnChecker			vehicleProfitAndLossTxnChecker		= null;
		try {
			vehicleProfitAndLossTxnChecker	= new VehicleProfitAndLossTxnChecker();
			
			vehicleProfitAndLossTxnChecker.setCompanyId(valueObject.getInt(VehicleProfitAndLossDto.COMPANY_ID));
			vehicleProfitAndLossTxnChecker.setVid(valueObject.getInt(VehicleProfitAndLossDto.TRANSACTION_VID, 0));
			vehicleProfitAndLossTxnChecker.setTxnInsertionDateTime(DateTimeUtility.getCurrentTimeStamp());
			vehicleProfitAndLossTxnChecker.setTransactionId(valueObject.getLong(VehicleProfitAndLossDto.TRANSACTION_ID, 0));
			vehicleProfitAndLossTxnChecker.setTransactionTypeId(valueObject.getShort(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, (short) 0));
			vehicleProfitAndLossTxnChecker.setTransactionSubTypeId(valueObject.getShort(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, (short) 0));
			vehicleProfitAndLossTxnChecker.setExpenseId(valueObject.getLong(VehicleProfitAndLossDto.TXN_EXPENSE_ID, 0));
			vehicleProfitAndLossTxnChecker.setTripExpenseId(valueObject.getLong(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, 0));
			
			return vehicleProfitAndLossTxnChecker;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleProfitAndLossTxnChecker		= null;
		}
	}

public VehicleProfitAndLossIncomeTxnChecker	getVehicleProfitAndLossIncomeTxnChecker(ValueObject	valueObject) throws Exception{
	VehicleProfitAndLossIncomeTxnChecker			vehicleProfitAndLossTxnChecker		= null;
		try {
			vehicleProfitAndLossTxnChecker	= new VehicleProfitAndLossIncomeTxnChecker();
			
			vehicleProfitAndLossTxnChecker.setCompanyId(valueObject.getInt(VehicleProfitAndLossDto.COMPANY_ID));
			vehicleProfitAndLossTxnChecker.setVid(valueObject.getInt(VehicleProfitAndLossDto.TRANSACTION_VID, 0));
			vehicleProfitAndLossTxnChecker.setTxnInsertionDateTime(DateTimeUtility.getCurrentTimeStamp());
			vehicleProfitAndLossTxnChecker.setTransactionId(valueObject.getLong(VehicleProfitAndLossDto.TRANSACTION_ID, 0));
			vehicleProfitAndLossTxnChecker.setTransactionTypeId(valueObject.getShort(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, (short) 0));
			vehicleProfitAndLossTxnChecker.setTransactionSubTypeId(valueObject.getShort(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, (short) 0));
			vehicleProfitAndLossTxnChecker.setIncomeId(valueObject.getLong(VehicleProfitAndLossDto.TXN_INCOME_ID, 0));
			vehicleProfitAndLossTxnChecker.setTripIncomeId(valueObject.getLong(VehicleProfitAndLossDto.TRIP_INCOME_ID, 0));
			
			return vehicleProfitAndLossTxnChecker;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleProfitAndLossTxnChecker		= null;
		}
	}
}
