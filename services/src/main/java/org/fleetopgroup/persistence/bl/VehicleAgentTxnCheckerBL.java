package org.fleetopgroup.persistence.bl;

import java.util.Date;

import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.persistence.model.VehicleAgentIncomeExpenseDetails;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.web.util.ValueObject;

public class VehicleAgentTxnCheckerBL {

	public VehicleAgentTxnChecker	getVehicleAgentTxnChecker(ValueObject	valueObject) throws Exception{
		VehicleAgentTxnChecker			vehicleAgentTxnChecker		= null;
			try {
				vehicleAgentTxnChecker	= new VehicleAgentTxnChecker();
				
				vehicleAgentTxnChecker.setCompanyId(valueObject.getInt(VehicleAgentConstant.COMPANY_ID));
				vehicleAgentTxnChecker.setVid(valueObject.getInt(VehicleAgentConstant.VEHICLE_ID, 0));
				vehicleAgentTxnChecker.setTxnInsertionDateTime((Date)valueObject.get(VehicleAgentConstant.TRANSACTION_DATE));
				vehicleAgentTxnChecker.setSystemDateTime(new Date());
				vehicleAgentTxnChecker.setTransactionId(valueObject.getLong(VehicleAgentConstant.TRANSACTION_ID, 0));
				vehicleAgentTxnChecker.setTxnTypeId(valueObject.getShort(VehicleAgentConstant.TRANSACTION_TYPE));
				vehicleAgentTxnChecker.setIdentifier(valueObject.getShort(VehicleAgentConstant.TXN_IDENTIFIER_FUEL_ENTRY));
				vehicleAgentTxnChecker.setIncomeExpenseAdded(false);
				vehicleAgentTxnChecker.setFinallyEntered(false);
				
				return vehicleAgentTxnChecker;
			} catch (Exception e) {
				throw e;
			}finally {
				vehicleAgentTxnChecker		= null;
			}
		}
	
	public VehicleAgentIncomeExpenseDetails getVehicleAgentIncomeExpenseDetailsDTO(ValueObject	valueObject) throws Exception{
		VehicleAgentIncomeExpenseDetails	vehicleAgentIncomeExpenseDetails	= null;
		try {
			vehicleAgentIncomeExpenseDetails	= new VehicleAgentIncomeExpenseDetails();
			
			vehicleAgentIncomeExpenseDetails.setVid(valueObject.getInt(VehicleAgentConstant.VEHICLE_ID, 0));
			vehicleAgentIncomeExpenseDetails.setTxnTypeId(valueObject.getShort(VehicleAgentConstant.TRANSACTION_TYPE));
			vehicleAgentIncomeExpenseDetails.setDebitAmount(valueObject.getDouble(VehicleAgentConstant.DEBIT_AMOUNT, 0));
			vehicleAgentIncomeExpenseDetails.setCreditAmount(valueObject.getDouble(VehicleAgentConstant.CREDIT_AMOUNT, 0));
			vehicleAgentIncomeExpenseDetails.setIdentifier(valueObject.getShort(VehicleAgentConstant.IDENTIFIER));
			vehicleAgentIncomeExpenseDetails.setTransactionId(valueObject.getLong(VehicleAgentConstant.TRANSACTION_ID, 0));
			vehicleAgentIncomeExpenseDetails.setAccountName(valueObject.getString(VehicleAgentConstant.TRANSACTION_ACCOUNT));
			vehicleAgentIncomeExpenseDetails.setNumberWithtype(valueObject.getString(VehicleAgentConstant.NUMBER_WITH_TYPE));
			vehicleAgentIncomeExpenseDetails.setRemark(valueObject.getString(VehicleAgentConstant.TRANSACTION_REMARK));
			vehicleAgentIncomeExpenseDetails.setTransactionDateTime((Date)valueObject.get(VehicleAgentConstant.TRANSACTION_DATE));
			vehicleAgentIncomeExpenseDetails.setCompanyId(valueObject.getInt(VehicleAgentConstant.COMPANY_ID, 0));
			vehicleAgentIncomeExpenseDetails.setPaymentDone(false);
			vehicleAgentIncomeExpenseDetails.setCreatedOn(new Date());
			vehicleAgentIncomeExpenseDetails.setLastUpdatedOn(new Date());
			vehicleAgentIncomeExpenseDetails.setCreatedById(valueObject.getLong(VehicleAgentConstant.CREATED_BY_ID));
			
			return vehicleAgentIncomeExpenseDetails;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public VehicleAgentIncomeExpenseDetails getClosingBalanceDTO(ValueObject	valueObject) throws Exception{
		VehicleAgentIncomeExpenseDetails	vehicleAgentIncomeExpenseDetails	= null;
		try {
			vehicleAgentIncomeExpenseDetails	= new VehicleAgentIncomeExpenseDetails();
			
			vehicleAgentIncomeExpenseDetails.setVid(valueObject.getInt(VehicleAgentConstant.VEHICLE_ID, 0));
			vehicleAgentIncomeExpenseDetails.setTxnTypeId(VehicleAgentConstant.TXN_TYPE_CLOSING_ENTRY);
			vehicleAgentIncomeExpenseDetails.setDebitAmount(0.0);
			vehicleAgentIncomeExpenseDetails.setCreditAmount(valueObject.getDouble(VehicleAgentConstant.CLOSING_AMOUNT, 0));
			vehicleAgentIncomeExpenseDetails.setIdentifier((short) 0);
			vehicleAgentIncomeExpenseDetails.setAccountName("Closing Entry");
			vehicleAgentIncomeExpenseDetails.setNumberWithtype("--");
			vehicleAgentIncomeExpenseDetails.setRemark("Closing Entry for Date "+(Date)valueObject.get(VehicleAgentConstant.TRANSACTION_DATE));
			vehicleAgentIncomeExpenseDetails.setTransactionDateTime((Date)valueObject.get(VehicleAgentConstant.TRANSACTION_DATE));
			vehicleAgentIncomeExpenseDetails.setCompanyId(valueObject.getInt(VehicleAgentConstant.COMPANY_ID, 0));
			vehicleAgentIncomeExpenseDetails.setPaymentDone(false);
			vehicleAgentIncomeExpenseDetails.setCreatedOn(new Date());
			vehicleAgentIncomeExpenseDetails.setLastUpdatedOn(new Date());
			vehicleAgentIncomeExpenseDetails.setCreatedById(valueObject.getLong(VehicleAgentConstant.CREATED_BY_ID));
			
			return vehicleAgentIncomeExpenseDetails;
		} catch (Exception e) {
			throw e;
		}
	}
}
