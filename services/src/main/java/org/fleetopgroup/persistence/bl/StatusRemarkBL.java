package org.fleetopgroup.persistence.bl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.fleetopgroup.persistence.model.StatusChangeRemark;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Component;

@Component
public class StatusRemarkBL {

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	 DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	 
	 public static final short DRIVER_STATUS_CHANGE 	=1;
	 public static final short VEHICLE_STATUS_CHANGE 	=2;
	 


	public StatusChangeRemark prepareVehicleStatusRemark(ValueObject valueObject) throws Exception {
		StatusChangeRemark statusRemark = null;
		try {
			statusRemark = new StatusChangeRemark();
			statusRemark.setTransactionId(valueObject.getInt("transactionId",0));
			statusRemark.setStatusRemark(valueObject.getString("changeRemark"));
			statusRemark.setCurrentStatusId(valueObject.getShort("currentStatusId"));
			statusRemark.setChangeToStatusId(valueObject.getShort("changeToStatusId"));
			statusRemark.setCreatedById(valueObject.getLong("userId"));
			statusRemark.setCompanyId(valueObject.getInt("companyId"));
			statusRemark.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			statusRemark.setTypeId(valueObject.getShort("typeId",(short) 0));
			statusRemark.setMobileNumber(valueObject.getString("mobileNo",""));
			statusRemark.setPartyName(valueObject.getString("partyName",""));
			statusRemark.setSoldAmount(valueObject.getDouble("soldAmount",0));
			if(valueObject.getString("soldDate",null) != null)
			statusRemark.setSoldOn(dateFormat.parse(valueObject.getString("soldDate")) );
			statusRemark.setMarkForDelete(false);
		
			return statusRemark;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
}
