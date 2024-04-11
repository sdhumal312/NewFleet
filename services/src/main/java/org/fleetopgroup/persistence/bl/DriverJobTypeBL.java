package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.model.DriverJobType;
import org.fleetopgroup.persistence.model.DriverJobTypeHistory;


public class DriverJobTypeBL {

	public DriverJobTypeBL() {
	}

	// Save the driver Jobument settings types
	public DriverJobType prepareDriJobTypeModel(DriverJobType driverJobTypeBean) {

		// create obj on driverJobtype
		DriverJobType JobType = new DriverJobType();
		JobType.setDriJobId(driverJobTypeBean.getDriJobId());
		JobType.setDriJobType(driverJobTypeBean.getDriJobType());
		JobType.setDriJobRemarks(driverJobTypeBean.getDriJobRemarks());
		JobType.setDriJob_deleteable(true);
		JobType.setDriJob_editable(true);
		return JobType;
	}

	// would show the driver List of Jobument Driver
	public List<DriverJobType> DriJobTypeListofBean(List<DriverJobType> driverJobType) {

		List<DriverJobType> beans = null;
		if (driverJobType != null && !driverJobType.isEmpty()) {
			beans = new ArrayList<DriverJobType>();

			DriverJobType bean = null;
			for (DriverJobType DriverJobType : driverJobType) {
				bean = new DriverJobType();
				bean.setDriJobType(DriverJobType.getDriJobType());
				bean.setDriJobId(DriverJobType.getDriJobId());
				bean.setDriJobRemarks(DriverJobType.getDriJobRemarks());

				beans.add(bean);
			}
		}
		return beans;
	}

	// edit the Driver Jobument type
	public DriverJobType prepareDriverJobTypeBean(DriverJobType JobType) {
		DriverJobType bean = new DriverJobType();

		bean.setDriJobId(JobType.getDriJobId());
		bean.setDriJobType(JobType.getDriJobType());
		bean.setDriJobRemarks(JobType.getDriJobRemarks());
		return bean;
	}

	public DriverJobTypeHistory prepareHistoryModel(DriverJobType driverJobType) {
		DriverJobTypeHistory		driverJobTypeHistory		= null;
		try {
			driverJobTypeHistory	= new DriverJobTypeHistory();
			
			driverJobTypeHistory.setCompanyId(driverJobType.getCompanyId());
			driverJobTypeHistory.setDriJob_deleteable(driverJobType.isDriJob_deleteable());
			driverJobTypeHistory.setDriJob_editable(driverJobType.isDriJob_editable());
			driverJobTypeHistory.setDriJobId(driverJobType.getDriJobId());
			driverJobTypeHistory.setDriJobRemarks(driverJobType.getDriJobRemarks());
			driverJobTypeHistory.setDriJobType(driverJobType.getDriJobType());
			driverJobTypeHistory.setLastModifiedById(driverJobType.getLastModifiedById());
			driverJobTypeHistory.setLastModifiedOn(driverJobType.getLastModifiedOn());
			driverJobTypeHistory.setMarkForDelete(driverJobType.isMarkForDelete());
			
			return driverJobTypeHistory;
		} catch (Exception e) {
			throw	e;
		}
	}

}
