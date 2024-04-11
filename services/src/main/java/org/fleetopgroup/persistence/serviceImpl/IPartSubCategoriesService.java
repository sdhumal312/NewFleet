package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.PartSubCategory;
import org.fleetopgroup.web.util.ValueObject;

public interface IPartSubCategoriesService {

	
	public ValueObject savePartSubCatrgories(ValueObject valueObject) throws Exception;
	
	public List<PartSubCategory> validatePartSubCategoriesName(String subCategoryName, Integer companyId) throws Exception;
	
	public ValueObject getPartSubCatrgoryList(ValueObject valueObject) throws Exception;
	
	public ValueObject getPartSubCategoryById(ValueObject valueObject) throws Exception;
	
	public ValueObject editPartSubCategory(ValueObject valueObject) throws Exception;
	
	public ValueObject deletePartSubCategoryById(ValueObject valueObject) throws Exception;
	
	public List<PartSubCategory> getPartSubCategoryByCompanyId(Integer companyId) throws Exception;
	
}
