package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.ClothTypesDto;
import org.fleetopgroup.persistence.model.ClothTypes;
import org.fleetopgroup.web.util.ValueObject;

public interface IClothTypeService {

	ValueObject		getClothTypesList(ValueObject	valueObject) throws Exception;
	
	ValueObject		saveClothTypes(ValueObject	valueObject) throws Exception;
	
	ValueObject		editClothTypes(ValueObject	valueObject) throws Exception;
	
	ValueObject		getClothTypesById(ValueObject	valueObject) throws Exception;
	
	ValueObject		deleteClothType(ValueObject	valueObject) throws Exception;
	
	List<ClothTypes>  validateClotypes(String	clothType, Integer companyId)throws Exception;
	
	List<ClothTypes>  getClothTypesList(String	clothType, Integer companyId)throws Exception;
	
	List<ClothTypes>  getClothTypesListNotSelected(String	term, String clothTypes, Integer companyId)throws Exception;
	
	List<ClothTypesDto>  getClothTypesListByClothTypesId(String	clothType, Integer locationId, Integer companyId)throws Exception;
	
	List<ClothTypesDto>  getClothTypesListByClothTypesIdWithRate(String	clothType, Integer locationId, Integer vendorId)throws Exception;

}
