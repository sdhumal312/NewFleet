/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.MastersConfigurationConstants;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.VehicleTyreModelTypeRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.VehicleTyreModelType;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("VehicleTyreModelTypeService")
@Transactional
public class VehicleTyreModelTypeService implements IVehicleTyreModelTypeService {

	@Autowired
	private VehicleTyreModelTypeRepository vehicleTyreModelTypeRepository;
	
	@Autowired ICompanyConfigurationService		companyConfigurationService;

	@PersistenceContext
	EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelTypeService#
	 * registerNew_VehicleTyreModelType(org.fleetop.persistence.model.
	 * VehicleTyreModelType)
	 */
	@Transactional
	public VehicleTyreModelType registerNew_VehicleTyreModelType(VehicleTyreModelType accountDto) throws Exception {

		return vehicleTyreModelTypeRepository.save(accountDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelTypeService#
	 * update_VehicleTyreModelType(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public void update_VehicleTyreModelType(String TYRE_MODEL, String TYRE_MODEL_DESCRITION, Long updateById,
			Date updateDate, Integer TYRE_MT_ID, Integer companyId) throws Exception {

		vehicleTyreModelTypeRepository.update_VehicleTyreModelType(TYRE_MODEL, TYRE_MODEL_DESCRITION, updateById,
				updateDate, TYRE_MT_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IVehicleTyreModelTypeService#findAll(
	 * )
	 */
	@Transactional
	public List<VehicleTyreModelType> findAll() throws Exception {
		HashMap<String, Object>			configuration		= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			
			if(!(boolean) configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL)) {
				return vehicleTyreModelTypeRepository.findAll(userDetails.getCompany_id());

			}else {
				return vehicleTyreModelTypeRepository.findAll();
			}
		} catch (Exception e) {
			throw e;
		}finally {
			configuration	= null;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelTypeService#
	 * get_VehicleTyreModelType(java.lang.String)
	 */
	@Transactional
	public VehicleTyreModelType get_VehicleTyreModelType(String verificationToken, Integer companyId) throws Exception {
		HashMap<String, Object>			configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(!(boolean) configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL)) {
				return vehicleTyreModelTypeRepository.get_VehicleTyreModelType(verificationToken, companyId);
			}else {
				return vehicleTyreModelTypeRepository.get_VehicleTyreModelType(verificationToken);
			}
			
		} catch (Exception e) {
			throw e;
		}finally {
			configuration	= null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelTypeService#
	 * delete_VehicleTyreModelType(java.lang.Integer)
	 */
	@Transactional
	public void delete_VehicleTyreModelType(Integer TYRE_MT_ID, Integer companyId) {

		vehicleTyreModelTypeRepository.delete_VehicleTyreModelType(TYRE_MT_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelTypeService#
	 * getVehicleTyreModelTypeByID(java.lang.Integer)
	 */
	@Transactional
	public VehicleTyreModelType getVehicleTyreModelTypeByID(Integer TYRE_MT_ID, Integer companyId) throws Exception {
		//return vehicleTyreModelTypeRepository.getVehicleTyreModelTypeByID(TYRE_MT_ID, companyId);

		HashMap<String, Object>			configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			
			if(!(boolean) configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL)) {
				return vehicleTyreModelTypeRepository.getVehicleTyreModelTypeByID(TYRE_MT_ID, companyId);
			}else {
				return vehicleTyreModelTypeRepository.getVehicleTyreModelTypeByID(TYRE_MT_ID);
			}
			
		} catch (Exception e) {
			throw e;
		}finally {
			configuration	= null;
		}
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IVehicleTyreModelTypeService#count()
	 */
	@Transactional
	public long count() {

		return vehicleTyreModelTypeRepository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelTypeService#
	 * Search_VehicleTyreModelType_select(java.lang.String)
	 */
	@Transactional
	public List<VehicleTyreModelType> Search_VehicleTyreModelType_select(String search, Integer companyId) throws Exception {
		HashMap<String, Object>			configuration		= null;
		TypedQuery<Object[]> 			query				= null;
		try {
			List<VehicleTyreModelType> Dtos = null;
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			if(!(boolean) configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL)) {
				 query = entityManager.createQuery(
						"SELECT c.TYRE_MT_ID, c.TYRE_MODEL from VehicleTyreModelType AS c where lower(c.TYRE_MODEL) Like ('%"
								+ search + "%') AND c.COMPANY_ID = "+companyId+" AND c.markForDelete = 0 AND c.isOwnTyreModel = 1",
						Object[].class);
			}else {
				query = entityManager.createQuery(
						"SELECT c.TYRE_MT_ID, c.TYRE_MODEL from VehicleTyreModelType AS c where lower(c.TYRE_MODEL) Like ('%"
								+ search + "%') AND c.markForDelete = 0 AND c.isOwnTyreModel = 0",
						Object[].class);
			}
			

			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleTyreModelType>();

				VehicleTyreModelType Documentto = null;
				for (Object[] result : results) {
					Documentto = new VehicleTyreModelType();
					Documentto.setTYRE_MT_ID((Integer) result[0]);
					Documentto.setTYRE_MODEL((String) result[1]);

					Dtos.add(Documentto);
				}
			}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			query				= null;
			configuration		= null;
		}
		
	}

}
