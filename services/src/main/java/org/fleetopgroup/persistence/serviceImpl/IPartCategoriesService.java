package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.PartCategories;
import org.fleetopgroup.web.util.ValueObject;


public interface IPartCategoriesService {

	public void addPartCategories(PartCategories status) throws Exception;

	public void updatePartCategories(PartCategories status) throws Exception;

	public List<PartCategories> listPartCategories() throws Exception;

	public PartCategories getPartCategories(int sid, Integer companyid) throws Exception;

	public void deletePartCategories(Integer partid, Integer companyId) throws Exception;

	public List<PartCategories> listOnlyStatus() throws Exception;
	
	public List<PartCategories> ValidateCategoriesName(String Categories, Integer companyId) throws Exception;
	
	public PartCategories ValidatePCName(String Categories, Integer companyId) throws Exception;
	
	public List<PartCategories> listPartCategoriesByCompayId(Integer companyId) throws Exception;

	public ValueObject getMostPartConsumedReport(ValueObject object)throws Exception;
	
	public List<PartCategories> searchPartCategories(String term) throws Exception;

	public List<PartCategories> listPartCategoriesByCompayIdAncIncIssue(Integer company_id) throws Exception;
	
	public List<PartCategories> getPartCategoryList(Integer companyId) throws Exception;
	
}