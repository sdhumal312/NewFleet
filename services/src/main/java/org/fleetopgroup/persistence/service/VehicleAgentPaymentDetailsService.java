package org.fleetopgroup.persistence.service;

import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.persistence.bl.VehicleAgentPaymentDetailsBL;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.VehicleAgentPaymentDetails;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.report.dao.VehicleAgentPaymentDetailsDao;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentPaymentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleAgentPaymentDetailsService implements IVehicleAgentPaymentDetailsService {

	@Autowired	private	VehicleAgentPaymentDetailsDao				vehicleAgentPaymentDetailsDao;
	@Autowired	private IVehicleService								vehicleService;
	@Autowired 	private	IVehicleAgentTxnCheckerService				vehicleAgentTxnCheckerService;
	@Autowired 	private	IVehicleAgentIncomeExpenseDetailsService	vehicleAgentIncomeExpenseDetailsService;

	
	VehicleAgentPaymentDetailsBL	paymentDetailsBL 	= new VehicleAgentPaymentDetailsBL();	
	VehicleAgentTxnCheckerBL		agentTxnCheckerBL	= new VehicleAgentTxnCheckerBL();
	
	@Override
	@Transactional
	public ValueObject saveVehicleAgentPayment(ValueObject valueObject) throws Exception {
		VehicleAgentPaymentDetails				vehicleAgentPaymentDetails		= null;
		VehicleDto								vehicleStatus					= null;
		ValueObject								ownerShipObject					= null;
		try {
			
			vehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("vid", 0));
			
			vehicleAgentPaymentDetails	=	paymentDetailsBL.getVehicleAgentPaymentDetails(valueObject);
			vehicleAgentPaymentDetailsDao.saveVehicleAgentPayment(vehicleAgentPaymentDetails);
			
			//vehicleAgentIncomeExpenseDetailsService.updatePaymentDone(valueObject.getInt("vid", 0), valueObject.getString("toDate"));
			
			
			if(vehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED){
				ownerShipObject	= new ValueObject();
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_AGENT_PAYMENT);
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, vehicleAgentPaymentDetails.getAgentPaymentDetailsId());
				ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, vehicleAgentPaymentDetails.getVid());
				ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, vehicleAgentPaymentDetails.getPaidAmount());
				ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
				ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, 0);
				ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, vehicleAgentPaymentDetails.getCompanyId());
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, vehicleAgentPaymentDetails.getPaymentDate());
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Payment Entry");
				ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Payment Entry");
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "Payment Entry done on : "+vehicleAgentPaymentDetails.getPaymentDate());
				ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, vehicleAgentPaymentDetails.getCreatedById());
				ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -vehicleAgentPaymentDetails.getPaidAmount());
				
				VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
				vehicleAgentTxnCheckerService.save(agentTxnChecker);
				
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
				
			}
			if(ownerShipObject != null) {
				vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleAgentPaymentDetails	= null;
		}
	}

	@Override
	public VehicleAgentPaymentDetails validatePaymentAfterDate(String date, Integer vid) throws Exception {
		return vehicleAgentPaymentDetailsDao.validatePaymentAfterDate(date, vid);
	}
}
