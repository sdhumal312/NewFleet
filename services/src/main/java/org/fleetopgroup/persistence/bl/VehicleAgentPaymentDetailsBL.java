package org.fleetopgroup.persistence.bl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.fleetopgroup.persistence.model.VehicleAgentPaymentDetails;
import org.fleetopgroup.web.util.ValueObject;

public class VehicleAgentPaymentDetailsBL {

	public VehicleAgentPaymentDetails getVehicleAgentPaymentDetails(ValueObject	valueObject)throws Exception{
		VehicleAgentPaymentDetails		vehicleAgentPaymentDetails		= null;
		try {
			vehicleAgentPaymentDetails	= new VehicleAgentPaymentDetails();
			
			SimpleDateFormat	format	= new SimpleDateFormat("dd-MM-yyyy");
			
			vehicleAgentPaymentDetails.setVid(valueObject.getInt("vid", 0));
			vehicleAgentPaymentDetails.setTotalAmount(valueObject.getDouble("totalAmount", 0));
			vehicleAgentPaymentDetails.setPaymentMode(valueObject.getShort("paymentMode"));
			vehicleAgentPaymentDetails.setPaymentTypeId(valueObject.getShort("paymentType"));
			vehicleAgentPaymentDetails.setPaidAmount(valueObject.getDouble("paidAmount", 0));
			vehicleAgentPaymentDetails.setPaymentDate(format.parse(valueObject.getString("paymentDate")));
			vehicleAgentPaymentDetails.setRemark(valueObject.getString("remark"));
			vehicleAgentPaymentDetails.setCompanyId(valueObject.getInt("companyId"));
			vehicleAgentPaymentDetails.setCreatedById(valueObject.getLong("userId"));
			vehicleAgentPaymentDetails.setCreatedOn(new Date());
			vehicleAgentPaymentDetails.setLastUpdatedById(valueObject.getLong("userId"));
			vehicleAgentPaymentDetails.setLastUpdatedOn(new Date());
			
			return vehicleAgentPaymentDetails;
			
		} catch (Exception e) {
			throw e;
		}
	}
}
