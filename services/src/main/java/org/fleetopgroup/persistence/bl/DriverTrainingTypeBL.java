package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.DriverTrainingTypeDto;
import org.fleetopgroup.persistence.model.DriverTrainingType;
import org.fleetopgroup.persistence.model.DriverTrainingTypeHistory;


public class DriverTrainingTypeBL {

	public DriverTrainingTypeBL() {
	}

	// Save the driver Trainingument settings types
	public DriverTrainingType prepareDriTrainingTypeModel(DriverTrainingTypeDto driverTrainingTypeBean) {

		// create obj on driverTrainingtype
		DriverTrainingType TrainingType = new DriverTrainingType();
		TrainingType.setDri_id(driverTrainingTypeBean.getDri_id());
		TrainingType.setDri_TrainingType(driverTrainingTypeBean.getDri_TrainingType());

		return TrainingType;
	}

	// would show the driver List of Trainingument Driver
	public List<DriverTrainingTypeDto> DriTrainingTypeListofBean(List<DriverTrainingType> driverTrainingType) {

		List<DriverTrainingTypeDto> beans = null;
		if (driverTrainingType != null && !driverTrainingType.isEmpty()) {
			beans = new ArrayList<DriverTrainingTypeDto>();

			DriverTrainingTypeDto bean = null;
			for (DriverTrainingType DriverTrainingType : driverTrainingType) {
				bean = new DriverTrainingTypeDto();
				bean.setDri_TrainingType(DriverTrainingType.getDri_TrainingType());
				bean.setDri_id(DriverTrainingType.getDri_id());

				beans.add(bean);
			}
		}
		return beans;
	}

	// edit the Driver Trainingument type
	public DriverTrainingTypeDto prepareDriverTrainingTypeBean(DriverTrainingType TrainingType) {
		DriverTrainingTypeDto bean = new DriverTrainingTypeDto();

		bean.setDri_id(TrainingType.getDri_id());
		bean.setDri_TrainingType(TrainingType.getDri_TrainingType());
		return bean;
	}

	public DriverTrainingTypeHistory prepareHistoryModel(DriverTrainingType driverTrainingType) {
		DriverTrainingTypeHistory		driverTrainingTypeHistory		= null;
		try {
			driverTrainingTypeHistory	= new DriverTrainingTypeHistory();
			
			driverTrainingTypeHistory.setCompanyId(driverTrainingType.getCompanyId());
			driverTrainingTypeHistory.setDri_id(driverTrainingType.getDri_id());
			driverTrainingTypeHistory.setDri_TrainingType(driverTrainingType.getDri_TrainingType());
			driverTrainingTypeHistory.setLastModifiedById(driverTrainingType.getLastModifiedById());
			driverTrainingTypeHistory.setLastModifiedOn(driverTrainingType.getLastModifiedOn());
			driverTrainingTypeHistory.setMarkForDelete(driverTrainingType.isMarkForDelete());
			
			return driverTrainingTypeHistory;
		} catch (Exception e) {
			throw	e;
		} finally {
			driverTrainingTypeHistory	= null;
		}
	}

}
