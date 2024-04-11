package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dao.DriverTrainingTypeRepository;
import org.fleetopgroup.persistence.model.DriverTrainingType;
import org.fleetopgroup.persistence.serviceImpl.IDriverTrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("DriverTrainingTypeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DriverTrainingTypeService implements IDriverTrainingTypeService {
	@Autowired
	private DriverTrainingTypeRepository DriverTrainingTypeDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addDriverTrainingType(DriverTrainingType TrainingType) throws Exception {
		DriverTrainingTypeDao.save(TrainingType);
	}

	@Transactional
	public List<DriverTrainingType> listDriverTrainingType() throws Exception {
		return DriverTrainingTypeDao.findAll();
	}

	@Transactional
	public DriverTrainingType getDriverTrainingType(int dtid, Integer companyid) throws Exception {
		return DriverTrainingTypeDao.getDriverTrainingType(dtid, companyid);
	}

	public void deleteDriverTrainingType(Integer dri_id, Integer companyId) throws Exception {
		DriverTrainingTypeDao.deleteDriverTrainingType(dri_id, companyId);
	}

	@Transactional
	public void updateDriverTrainingType(String dri_TrainingType, Long modifiedBy, Timestamp modifiedOn, Integer dri_id, Integer companyId) throws Exception {
		DriverTrainingTypeDao.updateDriverTrainingType(dri_TrainingType, modifiedBy, modifiedOn, dri_id, companyId);
	}

	@Transactional
	@Override
	public DriverTrainingType ValidateDriverTrainingType(String dri_TrainingType, Integer companyId) throws Exception {
		
		return DriverTrainingTypeDao.ValidateDriverTrainingType(dri_TrainingType, companyId);
	}

	@Override
	public List<DriverTrainingType> listDriverTrainingTypeByCompanyId(Integer companyId) throws Exception {
		try {
			return DriverTrainingTypeDao.findAllByCompanyId(companyId);
		} catch (Exception e) {
			throw e;
		}
	}
}