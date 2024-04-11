package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.model.PartCategories;
import org.fleetopgroup.persistence.model.PartCategoriesHistory;



public class PartCategoriesBL {
	public PartCategoriesBL() {
	}

	// save the PartCategories Model
	public PartCategories prepareModel(PartCategories PartCategoriesBean) {
		PartCategories status = new PartCategories();
		status.setPcid(PartCategoriesBean.getPcid());
		status.setPcName(PartCategoriesBean.getPcName());
		status.setPcdescription(PartCategoriesBean.getPcdescription());
		status.setIncPartIssueCateory(PartCategoriesBean.isIncPartIssueCateory());
		return status;
	}

	// show the List Of Vehicle Status
	public List<PartCategories> prepareListofBean(List<PartCategories> PartCategories) {
		List<PartCategories> beans = null;
		if (PartCategories != null && !PartCategories.isEmpty()) {
			beans = new ArrayList<PartCategories>();
			PartCategories bean = null;
			for (PartCategories PartCate : PartCategories) {
				bean = new PartCategories();
				bean.setPcid(PartCate.getPcid());
				bean.setPcName(PartCate.getPcName());
				bean.setPcdescription(PartCate.getPcdescription());
				bean.setIncPartIssueCateory(PartCate.isIncPartIssueCateory());
				
				beans.add(bean);
			}
		}
		return beans;
	}

	// edit Show PartCategoriesBean
	public PartCategories preparePartCategoriesBean(PartCategories status) {
		PartCategories bean = new PartCategories();
		bean.setPcid(status.getPcid());
		bean.setPcName(status.getPcName());
		bean.setPcdescription(status.getPcdescription());
		bean.setIncPartIssueCateory(status.isIncPartIssueCateory());
		return bean;
	}

	public PartCategoriesHistory prepareHistoryModel(PartCategories partCategories) {
		PartCategoriesHistory	categoriesHistory	= new PartCategoriesHistory();
		
		categoriesHistory.setCompanyId(partCategories.getCompanyId());
		categoriesHistory.setLastModifiedById(partCategories.getLastModifiedById());
		categoriesHistory.setLastModifiedOn(partCategories.getLastModifiedOn());
		categoriesHistory.setMarkForDelete(partCategories.isMarkForDelete());
		categoriesHistory.setPcdescription(partCategories.getPcdescription());
		categoriesHistory.setPcid(partCategories.getPcid());
		categoriesHistory.setPcName(partCategories.getPcName());
		
		return categoriesHistory;
	}
}
