/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.MastersConfigurationConstants;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TyreModalConstant;
import org.fleetopgroup.persistence.dao.VehicleTyreModelSubTypeRepository;
import org.fleetopgroup.persistence.dto.VehicleTyreModelSubTypeDto;
import org.fleetopgroup.persistence.model.VehicleCostFixing;
import org.fleetopgroup.persistence.model.VehicleTyreModelSubType;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleCostFixingService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelSubTypeService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("VehicleTyreModelSubTypeService")
@Transactional
public class VehicleTyreModelSubTypeService implements IVehicleTyreModelSubTypeService {

	@Autowired
	private VehicleTyreModelSubTypeRepository vehicleTyreModelSubTypeRepository;
	
	@Autowired private ICompanyConfigurationService		companyConfigurationService;
	
	@Autowired	private IVehicleCostFixingService		vehicleCostFixingService;

	@PersistenceContext
	EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelSubTypeService#
	 * registerNew_VehicleTyreModelType(org.fleetop.persistence.model.
	 * VehicleTyreModelSubType)
	 */
	@Transactional
	public VehicleTyreModelSubType registerNew_VehicleTyreModelType(VehicleTyreModelSubType accountDto)
			throws Exception {
		HashMap<String, Object>			configuration		= null;
			try {
				configuration	= companyConfigurationService.getCompanyConfiguration(accountDto.getCOMPANY_ID(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
				if(!(boolean) configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL)) {
					accountDto.setOwnTyreModel(true);
				}
				return vehicleTyreModelSubTypeRepository.save(accountDto);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}finally {
				configuration		= null;
			}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelSubTypeService#
	 * update_VehicleTyreModelType(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public void update_VehicleTyreModelSubType(Integer TYRE_MODEL, String TYRE_MODEL_SUBTYPE,  String TYRE_MODEL_DESCRITION, Long updateById, Date updateDate, Integer	warrantyPeriod, short	warrantyTypeId, String	warrentyterm,  Integer TYRE_MST_ID, Integer companyId) throws Exception {

		vehicleTyreModelSubTypeRepository.update_VehicleTyreModelSubType(TYRE_MODEL, TYRE_MODEL_SUBTYPE,
				TYRE_MODEL_DESCRITION, updateById, updateDate, warrantyPeriod, warrantyTypeId, warrentyterm, TYRE_MST_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelSubTypeService#
	 * findAll()
	 */
	@Transactional
	public List<VehicleTyreModelSubType> findAll() {

		return vehicleTyreModelSubTypeRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelSubTypeService#
	 * get_VehicleTyreModelType(java.lang.String)
	 */
	@Transactional
	@Override
	public VehicleTyreModelSubType get_VehicleTyre_ModelSubType(String verificationToken, Integer companyId) throws Exception {
		HashMap<String, Object>			configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(!(boolean) configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL)) {
				return vehicleTyreModelSubTypeRepository.get_VehicleTyre_ModelSubType(verificationToken, companyId);
			}else {
				return vehicleTyreModelSubTypeRepository.get_VehicleTyre_ModelSubType(verificationToken);
			}
	
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
		
	}

	@Transactional
	@Override
	public VehicleTyreModelSubType get_VehicleTyre_ModelSubTypeById(Integer TYRE_MT_ID, Integer companyId) throws Exception {

		HashMap<String, Object>			configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(!(boolean) configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL)) {
				return vehicleTyreModelSubTypeRepository.get_VehicleTyre_ModelSubTypeById(TYRE_MT_ID, companyId);
			}else {
				return vehicleTyreModelSubTypeRepository.get_VehicleTyre_ModelSubTypeById(TYRE_MT_ID);
			}
	
			
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelSubTypeService#
	 * delete_VehicleTyreModelType(java.lang.Integer)
	 */
	@Transactional
	public void delete_VehicleTyreModel_SubType(Integer TYRE_MT_ID, Integer companyId) {

		vehicleTyreModelSubTypeRepository.delete_VehicleTyreModel_SubType(TYRE_MT_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelSubTypeService#
	 * getVehicleTyreModelTypeByID(java.lang.Integer)
	 */
	@Transactional
	public VehicleTyreModelSubTypeDto getVehicleTyreModel_SubTypeByID(Integer TYRE_MT_ID, Integer companyId) throws Exception {
		HashMap<String, Object>			configuration		= null;
		Query 							query 				= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(!(boolean) configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL)) {
				query = entityManager.createQuery(
						"SELECT T.TYRE_MST_ID, T.TYRE_MODEL_SUBTYPE, T.TYRE_MODEL_DESCRITION, T.TYRE_MT_ID, m.TYRE_MODEL ,T.warrantyPeriod , T.warrantyTypeId, T.warrentyterm, "
						+ " T.tyreModelTypeId, T.gauageMeasurementLine, T.tyreGauge, T.tyreTubeTypeId, T.ply, T.psi, T.tyreModelSizeId, TS.TYRE_SIZE "
						+ " from VehicleTyreModelSubType AS T "
						+ " INNER JOIN VehicleTyreModelType m ON m.TYRE_MT_ID = T.TYRE_MT_ID"
						+ " LEFT JOIN VehicleTyreSize TS ON TS.TS_ID = T.tyreModelSizeId"
						+ " WHERE T.TYRE_MST_ID = "+TYRE_MT_ID+" AND T.COMPANY_ID = "+companyId+" AND T.markForDelete = 0 AND T.isOwnTyreModel = 1 ");

			}else {
				query = entityManager.createQuery(
						"SELECT T.TYRE_MST_ID, T.TYRE_MODEL_SUBTYPE, T.TYRE_MODEL_DESCRITION, T.TYRE_MT_ID, m.TYRE_MODEL, T.warrantyPeriod, T.warrantyTypeId, T.warrentyterm, "
						+ " T.tyreModelTypeId, T.gauageMeasurementLine, T.tyreGauge, T.tyreTubeTypeId, T.ply, T.psi, T.tyreModelSizeId, TS.TYRE_SIZE "
						+ " from VehicleTyreModelSubType AS T "
						+ " INNER JOIN VehicleTyreModelType m ON m.TYRE_MT_ID = T.TYRE_MT_ID"
						+ " LEFT JOIN VehicleTyreSize TS ON TS.TS_ID = T.tyreModelSizeId"
						+ " WHERE T.TYRE_MST_ID = "+TYRE_MT_ID+" AND T.markForDelete = 0 AND T.isOwnTyreModel = 0");
			}
			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			VehicleTyreModelSubTypeDto select;
			if (result != null) {
				select = new VehicleTyreModelSubTypeDto();
				select.setTYRE_MST_ID((Integer) result[0]);
				select.setTYRE_MODEL_SUBTYPE((String) result[1]);
				select.setTYRE_MODEL_DESCRITION((String) result[2]);
				select.setTYRE_MT_ID((Integer) result[3]);
				select.setTYRE_MODEL((String) result[4]);
				select.setWarrantyPeriod((Integer) result[5]);
				select.setWarrantyTypeId((Short) result[6]);
				select.setWarrentyterm((String) result[7] );
				if(result[8]  != null) {
					select.setTyreModelTypeId((Short) result[8]);
					select.setTyreModelType(TyreModalConstant.getTyreModelType(select.getTyreModelTypeId()));
				}
				if(result[9]  != null) {
					select.setGauageMeasurementLine((Integer) result[9]);
				}
				if(result[10]  != null) {
					select.setTyreGauge((Integer) result[10]);
				}
				if(result[11]  != null) {
					select.setTyreTubeTypeId((Short) result[11]);
					select.setTyreTubeType(TyreModalConstant.getTyreTubeType(select.getTyreTubeTypeId()));
				}
				if(result[12]  != null) {
					select.setPly((Integer) result[12]);
				}
				if(result[13]  != null) {
					select.setPsi((Integer) result[13]);
				}
				if(result[14]  != null) {
					select.setTyreModelSizeId((Integer) result[14]);
					select.setTyreModelSize((String) result[15]);
				}

			} else {
				return null;
			}

			return select;
		
			
		} catch (Exception e) {
			throw e;
		}finally {
			configuration	= null;
			query			= null;
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IVehicleTyreModelSubTypeService#count
	 * ()
	 */
	@Transactional
	public long count() {

		return vehicleTyreModelSubTypeRepository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelSubTypeService#
	 * SearchOnly_MpdelSubType(java.lang.String)
	 */
	@Transactional
	public List<VehicleTyreModelSubType> SearchOnly_ModelSubType(Integer Model_name, Integer companyId) throws Exception {
		HashMap<String, Object>			configuration		= null;
		TypedQuery<Object[]> 			query				= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(!(boolean) configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL)) {
				query = entityManager.createQuery(
						"SELECT c.TYRE_MST_ID, c.TYRE_MODEL_SUBTYPE from VehicleTyreModelSubType AS c where c.TYRE_MT_ID= "
								+ Model_name + " and c.COMPANY_ID = "+companyId+" AND c.isOwnTyreModel = 1 AND c.markForDelete = 0",
						Object[].class);
			}else {
				query = entityManager.createQuery(
						"SELECT c.TYRE_MST_ID, c.TYRE_MODEL_SUBTYPE from VehicleTyreModelSubType AS c where c.TYRE_MT_ID= "
								+ Model_name + " AND c.isOwnTyreModel = 0 AND c.markForDelete = 0",
						Object[].class);
			}

			List<Object[]> results = query.getResultList();

			List<VehicleTyreModelSubType> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleTyreModelSubType>();

				VehicleTyreModelSubType SubModel = null;
				for (Object[] result : results) {
					SubModel = new VehicleTyreModelSubType();
					SubModel.setTYRE_MST_ID((Integer) result[0]);
					SubModel.setTYRE_MODEL_SUBTYPE((String) result[1]);

					Dtos.add(SubModel);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			configuration	= null;
			query			= null;
		}
		
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelSubTypeService#Search_VehicleTyreSubModelType_select(java.lang.String)
	 */
	@Transactional
	public List<VehicleTyreModelSubType> Search_VehicleTyreSubModelType_select(String search, Integer companyId) throws Exception {
		
		HashMap<String, Object>			configuration		= null;
		TypedQuery<Object[]> 			query				= null;
		try {
			List<VehicleTyreModelSubType> Dtos = null;
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			if(!(boolean) configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL)) {
				query = entityManager.createQuery(
						"SELECT c.TYRE_MST_ID, c.TYRE_MODEL_SUBTYPE from VehicleTyreModelSubType AS c where  lower(c.TYRE_MODEL_SUBTYPE) Like ('%"
								+ search + "%') and c.COMPANY_ID = "+companyId+" AND c.isOwnTyreModel = 1  AND c.markForDelete = 0",
						Object[].class);

			}else {
				query = entityManager.createQuery(
						"SELECT c.TYRE_MST_ID, c.TYRE_MODEL_SUBTYPE from VehicleTyreModelSubType AS c where  lower(c.TYRE_MODEL_SUBTYPE) Like ('%"
								+ search + "%') AND c.isOwnTyreModel = 0  AND c.markForDelete = 0",
						Object[].class);

			}
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleTyreModelSubType>();

				VehicleTyreModelSubType SubModel = null;
				for (Object[] result : results) {
					SubModel = new VehicleTyreModelSubType();
					SubModel.setTYRE_MST_ID((Integer) result[0]);
					SubModel.setTYRE_MODEL_SUBTYPE((String) result[1]);

					Dtos.add(SubModel);
				}
			}
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public List<VehicleTyreModelSubTypeDto> findAllByCompanyId(Integer companyId) throws Exception{
		HashMap<String, Object>			configuration		= null;
		TypedQuery<Object[]> 			query 				= null;
		try {
			//return vehicleTyreModelSubTypeRepository.findAllByCompanyId(companyId);
			
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(!(boolean) configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL)) {
				query = entityManager.createQuery(
						"SELECT c.TYRE_MST_ID, c.TYRE_MODEL_SUBTYPE, c.TYRE_MODEL_DESCRITION,c.TYRE_MT_ID, m.TYRE_MODEL,c.warrantyPeriod, c.warrentyterm,c.warrantyTypeId,"
						+ " VCX.vehicleCostFixingId, VCX.costPerKM"
						+ " from VehicleTyreModelSubType AS c "
						+ " INNER JOIN VehicleTyreModelType m ON m.TYRE_MT_ID = c.TYRE_MT_ID "
						+ " LEFT JOIN VehicleCostFixing	VCX	ON VCX.tyreSubTypeId = c.TYRE_MST_ID AND VCX.companyId = "+companyId+""
						+ " where  c.COMPANY_ID = "+companyId+" AND c.markForDelete = 0 AND c.isOwnTyreModel = 1",
						Object[].class);
		}else {
				query = entityManager.createQuery(
						"SELECT c.TYRE_MST_ID, c.TYRE_MODEL_SUBTYPE, c.TYRE_MODEL_DESCRITION,c.TYRE_MT_ID, m.TYRE_MODEL, c.warrantyPeriod, c.warrentyterm,c.warrantyTypeId,"
						+ " VCX.vehicleCostFixingId, VCX.costPerKM"
						+ " from VehicleTyreModelSubType AS c "
						+ " INNER JOIN VehicleTyreModelType m ON m.TYRE_MT_ID = c.TYRE_MT_ID"
						+ " LEFT JOIN VehicleCostFixing	 AS VCX	ON VCX.tyreSubTypeId = c.TYRE_MST_ID AND VCX.companyId = "+companyId+""
						+ " where  c.markForDelete = 0 AND c.isOwnTyreModel = 0",
						Object[].class);
			}
			
			
			
			
			List<Object[]> results = query.getResultList();

			List<VehicleTyreModelSubTypeDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleTyreModelSubTypeDto>();

				VehicleTyreModelSubTypeDto SubModel = null;
				for (Object[] result : results) {
					
					
					SubModel = new VehicleTyreModelSubTypeDto();
					SubModel.setTYRE_MST_ID((Integer) result[0]);
					SubModel.setTYRE_MODEL_SUBTYPE((String) result[1]);
					SubModel.setTYRE_MODEL_DESCRITION((String) result[2]);
					SubModel.setTYRE_MT_ID((Integer) result[3]);
					SubModel.setTYRE_MODEL((String) result[4]);
					SubModel.setWarrantyPeriod((Integer) result[5]);
					SubModel.setWarrentyterm((String)result[6]);
				
					if(result[7] != null) {
						SubModel.setWarrantyTypeId((short) result[7]);
						SubModel.setWarrantyType(VehicleTyreModelSubTypeDto.getWarrantyTypeName((short) result[7]));
					}
					SubModel.setVehicleCostFixingId((Long) result[8]);
					SubModel.setCostPerKM((Double) result[9]);
					
					if(SubModel.getVehicleCostFixingId() == null) {
						SubModel.setVehicleCostFixingId((long) 0);
						SubModel.setCostPerKM((double) 0);
					}
					

					Dtos.add(SubModel);
				}
			}
			return Dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			configuration		= null;
			query 				= null;
		}
	}

	@Transactional
	public ValueObject getVehicleTyreModelSubTypeDetails(ValueObject valueObject) throws Exception {
		Query 							query 				= null;
		try {
			query = entityManager.createQuery(
					"SELECT T.TYRE_MST_ID, T.TYRE_MODEL_SUBTYPE, T.TYRE_MODEL_DESCRITION, T.TYRE_MT_ID, m.TYRE_MODEL ,T.warrantyPeriod , T.warrantyTypeId, T.warrentyterm, "
					+ " T.tyreModelTypeId, T.gauageMeasurementLine, T.tyreGauge, T.tyreTubeTypeId, T.ply, T.psi, T.tyreModelSizeId, TS.TYRE_SIZE "
					+ " from VehicleTyreModelSubType AS T "
					+ " INNER JOIN VehicleTyreModelType m ON m.TYRE_MT_ID = T.TYRE_MT_ID"
					+ " LEFT JOIN VehicleTyreSize TS ON TS.TS_ID = T.tyreModelSizeId"
					+ " WHERE T.TYRE_MST_ID = "+valueObject.getLong("TYRE_MST_ID",0)+" AND T.COMPANY_ID = "+valueObject.getInt("companyId")+" AND T.markForDelete = 0 AND T.isOwnTyreModel = 1 ");

			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			VehicleTyreModelSubTypeDto select;
			if (result != null) {
				select = new VehicleTyreModelSubTypeDto();
				select.setTYRE_MST_ID((Integer) result[0]);
				select.setTYRE_MODEL_SUBTYPE((String) result[1]);
				select.setTYRE_MODEL_DESCRITION((String) result[2]);
				select.setTYRE_MT_ID((Integer) result[3]);
				select.setTYRE_MODEL((String) result[4]);
				select.setWarrantyPeriod((Integer) result[5]);
				select.setWarrantyTypeId((Short) result[6]);
				select.setWarrentyterm((String) result[7] );
				if(result[8]  != null) {
					select.setTyreModelTypeId((Short) result[8]);
					select.setTyreModelType(TyreModalConstant.getTyreModelType(select.getTyreModelTypeId()));
				}
				if(result[9]  != null) {
					select.setGauageMeasurementLine((Integer) result[9]);
				}
				if(result[10]  != null) {
					select.setTyreGauge((Integer) result[10]);
				}
				if(result[11]  != null) {
					select.setTyreTubeTypeId((Short) result[11]);
					select.setTyreTubeType(TyreModalConstant.getTyreTubeType(select.getTyreTubeTypeId()));
				}
				if(result[12]  != null) {
					select.setPly((Integer) result[12]);
				}
				if(result[13]  != null) {
					select.setPsi((Integer) result[13]);
				}
				if(result[14]  != null) {
					select.setTyreModelSizeId((Integer) result[14]);
					select.setTyreModelSize((String) result[15]);
				}
				VehicleCostFixing VehicleCostFixing =vehicleCostFixingService.getVehicleCostFixingByTyreSubTypeId(valueObject.getInt("companyId"), select.getTYRE_MST_ID());
				
				if(VehicleCostFixing != null) {
					select.setCostPerKM(VehicleCostFixing.getCostPerKM());
				}
				valueObject.put("tyreModelDetails", select);
			} 
			return valueObject;
		
			
		} catch (Exception e) {
			throw e;
		}finally {
			query			= null;
		}
		
		
	}
	
	@Override
	@Transactional
	public List<VehicleTyreModelSubType> getSearchTyreSubModelByTyreSize(String search, Integer companyId,boolean validateTyreSizeConfig,Integer tyreSize) throws Exception {
		
		HashMap<String, Object>			configuration		= null;
		TypedQuery<Object[]> 			query				= null;
		String							queryStr			= "";
		List<VehicleTyreModelSubType> Dtos = null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			
			if(validateTyreSizeConfig && tyreSize > 0) {
				queryStr = "AND tyreModelSizeId = "+tyreSize+"";
			}
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			if(!(boolean) configuration.get(MastersConfigurationConstants.IS_COMMON_TYRE_MODEL)) {
				query = entityManager.createQuery(
						"SELECT c.TYRE_MST_ID, c.TYRE_MODEL_SUBTYPE from VehicleTyreModelSubType AS c where  lower(c.TYRE_MODEL_SUBTYPE) Like ('%"
								+ search + "%') and c.COMPANY_ID = "+companyId+" AND c.isOwnTyreModel = 1  "+queryStr+" AND c.markForDelete = 0",
						Object[].class);

			}else {
				query = entityManager.createQuery(
						"SELECT c.TYRE_MST_ID, c.TYRE_MODEL_SUBTYPE from VehicleTyreModelSubType AS c where  lower(c.TYRE_MODEL_SUBTYPE) Like ('%"
								+ search + "%') AND c.isOwnTyreModel = 0 "+queryStr+" AND c.markForDelete = 0",
						Object[].class);

			}
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleTyreModelSubType>();

				VehicleTyreModelSubType SubModel = null;
				for (Object[] result : results) {
					SubModel = new VehicleTyreModelSubType();
					SubModel.setTYRE_MST_ID((Integer) result[0]);
					SubModel.setTYRE_MODEL_SUBTYPE((String) result[1]);

					Dtos.add(SubModel);
				}
			}
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public Long validateTyreManufacturer(Integer tyreManufacturerId, Integer companyId) throws Exception {
		try {
			return vehicleTyreModelSubTypeRepository.validateTyreManufacturer(tyreManufacturerId,companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
}
