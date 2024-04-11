package org.fleetopgroup.persistence.service;

import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.constant.MastersConfigurationConstants;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.PartManufacturerRepository;
import org.fleetopgroup.persistence.model.PartManufacturer;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPartManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("IPartManufacturerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PartManufacturerService implements IPartManufacturerService {

	@Autowired
	private PartManufacturerRepository PartManufacturerDao;
	
	@Autowired private ICompanyConfigurationService		companyConfigurationService;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPartManufacturer(PartManufacturer status) throws Exception {

		HashMap<String, Object>   configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(status.getCompanyId(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(!(boolean) configuration.get(MastersConfigurationConstants.COMMON_MANUFACTURER)) {
				status.setOwnPartManufacturer(true);
			}
			PartManufacturerDao.save(status);
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updatePartManufacturer(PartManufacturer status) throws Exception {
		PartManufacturerDao.updatePartManufacturer(status.getPmName(), status.getPmdescription(),status.getLastModifiedById(),status.getLastModifiedOn(), status.getPmid(), status.getCompanyId());
	}

	@Transactional
	public List<PartManufacturer> listPartManufacturer() throws Exception {
		return PartManufacturerDao.findAll();
	}

	@Transactional
	public PartManufacturer getPartManufacturer(int sid, Integer companyId) throws Exception {
		HashMap<String, Object>   configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MANUFACTURER)) {
				return PartManufacturerDao.getPartManufacturer(sid);
			}else {
				return PartManufacturerDao.getPartManufacturer(sid, companyId);
			}
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
	
	}

	@Transactional
	public void deletePartManufacturer(Integer status, Integer companyId) throws Exception {
		PartManufacturerDao.deletePartManufacturer(status, companyId);
	}

	@Transactional
	public List<PartManufacturer> listOnlyStatus() throws Exception {
		return PartManufacturerDao.findAll();
	}

	@Transactional
	public List<PartManufacturer> ValidatePartManufacturerName(String Manufacturer , Integer companyId) throws Exception {

		HashMap<String, Object>   configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MANUFACTURER)) {
				return PartManufacturerDao.ValidatePartManufacturerName(Manufacturer);
			}else {
				return PartManufacturerDao.ValidatePartManufacturerName(Manufacturer, companyId);
			}
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
	}

	@Transactional
	@Override
	public PartManufacturer ValidateManufacturerName(String Manufacturer , Integer companyId) throws Exception {

		HashMap<String, Object>   configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MANUFACTURER)) {
				return PartManufacturerDao.ValidateManufacturerName(Manufacturer);
			}else {
				return PartManufacturerDao.ValidateManufacturerName(Manufacturer, companyId);
			}
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
		
	}

	@Override
	public List<PartManufacturer> listPartManufacturerByCompanyId(Integer companyId) throws Exception {
		HashMap<String, Object>   configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MANUFACTURER)) {
				return PartManufacturerDao.listPartManufacturerByCompanyId();
			}else {
				return PartManufacturerDao.listPartManufacturerByCompanyId(companyId);
			}
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
	}
}