package org.fleetopgroup.persistence.service;

/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dao.DriverJobTypeRepository;
import org.fleetopgroup.persistence.model.DriverJobType;
import org.fleetopgroup.persistence.serviceImpl.IDriverJobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("DriverJobTypeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DriverJobTypeService implements IDriverJobTypeService {
	@Autowired
	private DriverJobTypeRepository DriverJobTypeDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addDriverJobType(DriverJobType JobType) throws Exception {
		DriverJobTypeDao.save(JobType);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateDriverJobType(DriverJobType JobType) throws Exception {
		DriverJobTypeDao.updateDriverJobType(JobType.getDriJobType(), JobType.getDriJobRemarks(),JobType.getLastModifiedById(), JobType.getLastModifiedOn(), JobType.getDriJobId(), JobType.getCompanyId());
	}
	
	@Transactional
	public List<DriverJobType> listDriverJobType() throws Exception {
		return DriverJobTypeDao.findAll();
	}

	@Transactional
	public DriverJobType getDriverJobType(int dtid, Integer companyId) throws Exception {
		return DriverJobTypeDao.getDriverJobType(dtid, companyId);
	}
	
	@Transactional
	public DriverJobType get_EditableDriverJobType(int dtid, Integer companyid) throws Exception {
		return DriverJobTypeDao.get_EditableDriverJobType(dtid,  companyid);
	}

	@Transactional
	public void deleteDriverJobType(Integer JobType, Integer companyId) throws Exception {
		DriverJobTypeDao.deleteDriverJobType(JobType, companyId);
	}

	@Transactional
	@Override
	public DriverJobType validateDriverJobType(String driJobType, Integer companyId) throws Exception {
		return DriverJobTypeDao.validateDriverJobType(driJobType, companyId);
	}

	@Override
	public List<DriverJobType> listDriverJobTypeByCompanyId(Integer companyId) throws Exception {
		try {
			return DriverJobTypeDao.findAllByCompanyId(companyId);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public DriverJobType getTopDriverJobType(Integer companyId) throws Exception {
		
		return DriverJobTypeDao.getTopDriverJobType(companyId);
	}
}