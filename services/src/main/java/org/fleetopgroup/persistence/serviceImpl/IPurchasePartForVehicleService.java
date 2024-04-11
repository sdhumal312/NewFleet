package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;

//import com.fleetop.dto.UploadFile;

public interface IPurchasePartForVehicleService {

	public ValueObject savePurchasePartForVehicle(ValueObject valueObject) throws Exception;

	public ValueObject getAllPurchasePartForVehicle(ValueObject valueInObject)throws Exception;

	public ValueObject deletePurchasePartForVehicle(ValueObject valueInObject)throws Exception;

	public void deletePurchasePartForVehicleByPurchaseOrderToPartId(Long purchaseorderto_partid, Long purchaseorder_id)throws Exception;
	
	
	
}