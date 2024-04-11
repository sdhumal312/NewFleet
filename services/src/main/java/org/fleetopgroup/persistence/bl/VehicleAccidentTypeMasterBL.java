package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.persistence.model.VehicleAccidentTypeMaster;
import org.fleetopgroup.web.util.ValueObject;

public class VehicleAccidentTypeMasterBL {

	public VehicleAccidentTypeMasterBL() {
		super();
	}
	
	public VehicleAccidentTypeMaster prepareVehicleAccidentTypeMaster(ValueObject valueObject) throws Exception {
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			VehicleAccidentTypeMaster VehicleAccidentTypeMaster = new VehicleAccidentTypeMaster();
			VehicleAccidentTypeMaster.setVehicleAccidentTypeMasterId(valueObject.getLong("vehicleAccidentTypeMasterId",0));
			VehicleAccidentTypeMaster.setVehicleAccidentTypeMasterName(valueObject.getString("vehicleAccidentTypeMasterName"));
			VehicleAccidentTypeMaster.setDescription(valueObject.getString("description"));
			VehicleAccidentTypeMaster.setCreatedById(valueObject.getLong("userId"));
			VehicleAccidentTypeMaster.setLastUpdatedBy(valueObject.getLong("userId"));
			VehicleAccidentTypeMaster.setCreationDate(toDate);
			VehicleAccidentTypeMaster.setLastUpdatedOn(toDate);
			VehicleAccidentTypeMaster.setCompanyId(valueObject.getInt("companyId"));
			VehicleAccidentTypeMaster.setMarkForDelete(false);

			return VehicleAccidentTypeMaster;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			
		}
		

	}

}
