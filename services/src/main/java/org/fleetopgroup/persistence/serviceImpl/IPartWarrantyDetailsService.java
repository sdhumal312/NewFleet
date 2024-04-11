package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.PartWarrantyDetailsDto;
import org.fleetopgroup.persistence.model.PartWarrantyDetails;
import org.fleetopgroup.web.util.ValueObject;

public interface IPartWarrantyDetailsService {

	ValueObject	getPartWarrantyDetails (ValueObject		valueObject) throws Exception;
	
	ValueObject	saveWarrantySerialNumber (ValueObject		valueObject) throws Exception;
	
	ValueObject	getWarrantyDetailsList (ValueObject		valueObject) throws Exception;

//	ValueObject getPartWarrantyDetailByVidAndPartId(ValueObject valueObject) throws Exception;

	ValueObject updatePartWarrantyStatus(ValueObject object)throws Exception;

	ValueObject getWarrantyPartDetailsByDseId(ValueObject object)throws Exception;

	List<PartWarrantyDetailsDto> getWarrantyPartDetailsLsit(ValueObject valueObject) throws Exception;

	ValueObject underWarrantyPartDetailsList(String term, ValueObject valueObject)throws Exception;

	ValueObject showAllWarrantyPart(ValueObject object)throws Exception;

	ValueObject removeAssignPartWarrantyDetails(ValueObject object)throws Exception;

	PartWarrantyDetailsDto validatePartWarrantyDetils(ValueObject object, ValueObject valueObject) throws Exception;
	
	List<PartWarrantyDetailsDto> getAlreadyAsignedPartsByVid(String term, ValueObject valueObject)throws Exception;
	
	ValueObject savePartWithWarrantyDetails(ValueObject object)throws Exception;
	
	List<PartWarrantyDetails> getPartWarrantyDetailsByServiceId(long servicePartId, short type);
	
	public void deleteWarrantyPartByInventoryId(Long inventoryId) throws Exception;

	List<PartWarrantyDetailsDto> getAssetNumberByPartIdAndInventoryId(long partId, long inventoryId, int companayId)
			throws Exception;

	public void updateRepairStatus(Long assetId, short statusId, Long repairToStockDetailsId);

	public void transferAssetNumber(long repairStockId, int locationId);

	public void updateRepairStatusByrepairToStockDetailsId(short statusId, Long repairToStockDetailsId) throws Exception;

	ValueObject getWarrantyPartDataDetails(ValueObject object)throws Exception;
	
	
	public ValueObject getWarrantyDetailsListDetails(ValueObject valueObject);
	

}
