package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.model.PartManufacturer;
import org.fleetopgroup.persistence.model.PartManufacturerHistory;



public class PartManufacturerBL {
	public PartManufacturerBL() {
	}

	// save the PartManufacturer Model
	public PartManufacturer prepareModel(PartManufacturer PartManufacturerBean) {
		PartManufacturer status = new PartManufacturer();
		status.setPmid(PartManufacturerBean.getPmid());
		status.setPmName(PartManufacturerBean.getPmName());
		status.setPmdescription(PartManufacturerBean.getPmdescription());

		return status;
	}

	// show the List Of Vehicle Status
	public List<PartManufacturer> prepareListofBean(List<PartManufacturer> PartManufacturer) {
		List<PartManufacturer> beans = null;
		if (PartManufacturer != null && !PartManufacturer.isEmpty()) {
			beans = new ArrayList<PartManufacturer>();
			PartManufacturer bean = null;
			for (PartManufacturer PartCate : PartManufacturer) {
				bean = new PartManufacturer();
				bean.setPmid(PartCate.getPmid());
				bean.setPmName(PartCate.getPmName());
				bean.setPmdescription(PartCate.getPmdescription());
				bean.setCompanyId(PartCate.getCompanyId());
				bean.setCreatedBy(PartCate.getCreatedBy());
				bean.setCreatedOn(PartCate.getCreatedOn());
				beans.add(bean);
			}
		}
		return beans;
	}

	// edit Show PartManufacturerBean
	public PartManufacturer preparePartManufacturerBean(PartManufacturer status) {
		PartManufacturer bean = new PartManufacturer();
		bean.setPmid(status.getPmid());
		bean.setPmName(status.getPmName());
		bean.setPmdescription(status.getPmdescription());
		return bean;
	}

	public PartManufacturerHistory prepareHistoryModel(PartManufacturer manufacturer) {
		PartManufacturerHistory		partManufacturerHistory	= new PartManufacturerHistory();
		
		partManufacturerHistory.setCompanyId(manufacturer.getCompanyId());
		partManufacturerHistory.setLastModifiedById(manufacturer.getLastModifiedById());
		partManufacturerHistory.setLastModifiedOn(manufacturer.getLastModifiedOn());
		partManufacturerHistory.setMarkForDelete(manufacturer.isMarkForDelete());
		partManufacturerHistory.setPmdescription(manufacturer.getPmdescription());
		partManufacturerHistory.setPmid(manufacturer.getPmid());
		partManufacturerHistory.setPmName(manufacturer.getPmName());
		
		return partManufacturerHistory;
	}
}
