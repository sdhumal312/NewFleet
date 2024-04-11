package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.MasterPartsConfigurationConstants;
import org.fleetopgroup.constant.MastersConfigurationConstants;
import org.fleetopgroup.constant.PartType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.bl.MasterPartsBL;
import org.fleetopgroup.persistence.dao.ChildPartDetailsRepository;
import org.fleetopgroup.persistence.dao.LowStockInventoryRepository;
import org.fleetopgroup.persistence.dao.MasterPartRateRepository;
import org.fleetopgroup.persistence.dao.MasterPartsExtraDetailsRepository;
import org.fleetopgroup.persistence.dao.MasterPartsLocationRepository;
import org.fleetopgroup.persistence.dao.MasterPartsPhotoRepository;
import org.fleetopgroup.persistence.dao.MasterPartsRepository;
import org.fleetopgroup.persistence.dao.MasterPartsToVehicleMakerReepository;
import org.fleetopgroup.persistence.dao.MasterPartsToVehicleModalRepository;
import org.fleetopgroup.persistence.dao.PartPurchaseVendorRepository;
import org.fleetopgroup.persistence.dao.RepairableVendorDetailsRepository;
import org.fleetopgroup.persistence.dao.SubstitudePartDetailsRepository;
import org.fleetopgroup.persistence.dto.ChildPartDetailsDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.LowStockInventoryDto;
import org.fleetopgroup.persistence.dto.MasterPartsDto;
import org.fleetopgroup.persistence.dto.MasterPartsExtraDetailsDto;
import org.fleetopgroup.persistence.dto.MasterPartsLocationDto;
import org.fleetopgroup.persistence.dto.MasterPartsToVehicleMakerDto;
import org.fleetopgroup.persistence.dto.MasterPartsToVehicleModalDto;
import org.fleetopgroup.persistence.dto.PartPurchaseVendorDto;
import org.fleetopgroup.persistence.dto.RepairableVendorDetailsDto;
import org.fleetopgroup.persistence.dto.SubstitudePartDetailsDto;
import org.fleetopgroup.persistence.model.ChildPartDetails;
import org.fleetopgroup.persistence.model.LowStockInventory;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.MasterPartsExtraDetails;
import org.fleetopgroup.persistence.model.MasterPartsLocation;
import org.fleetopgroup.persistence.model.MasterPartsPhoto;
import org.fleetopgroup.persistence.model.MasterPartsToVehicleMaker;
import org.fleetopgroup.persistence.model.MasterPartsToVehicleModal;
import org.fleetopgroup.persistence.model.PartPurchaseVendor;
import org.fleetopgroup.persistence.model.RepairableVendorDetails;
import org.fleetopgroup.persistence.model.SubstitudePartDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("MasterPartsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MasterPartsService implements IMasterPartsService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private MasterPartsRepository MasterPartsDao;

	@Autowired
	private MasterPartsLocationRepository MasterPartsLocationRepository;

	@Autowired private MongoTemplate					mongoTemplate;
	@Autowired private ISequenceCounterService			sequenceCounterService;
	@Autowired private MasterPartsPhotoRepository		masterPartsPhotoRepository;
	@Autowired private ICompanyConfigurationService		companyConfigurationService;
	@Autowired private LowStockInventoryRepository		 lowStockInventoryRepository;
	@Autowired private SubstitudePartDetailsRepository	 substitudePartDetailsRepository;
	@Autowired private RepairableVendorDetailsRepository repairableVendorDetailsRepository;
	@Autowired private ChildPartDetailsRepository		 childPartDetailsRepository;
	@Autowired private MasterPartsExtraDetailsRepository extraDetailsRepository;
	@Autowired private PartPurchaseVendorRepository			partPurchaseVendorRepository;
	@Autowired private MasterPartRateRepository				masterPartRateRepository;
	@Autowired private MasterPartsToVehicleMakerReepository	makerReepository;
	@Autowired private MasterPartsToVehicleModalRepository	modalRepository;
	
	private static final int PAGE_SIZE = 10;
	
	MasterPartsBL	masterPartsBL	= new MasterPartsBL();

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addMasterParts(MasterParts status) throws Exception {
		HashMap<String, Object>		configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(status.getCompanyId(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(!(boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
				status.setOwnMaterParts(true);
			}else if(status.getPartManufacturerType() == PartType.PART_MANUFACTURER_TYPE_ORIGINAL) {
				status.setCompanyId(0);
			}
			MasterPartsDao.save(status);
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateMasterParts(MasterParts status) throws Exception {
		HashMap<String, Object>		configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(status.getCompanyId(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(!(boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
				status.setOwnMaterParts(true);
			}else if(status.getPartManufacturerType() == PartType.PART_MANUFACTURER_TYPE_ORIGINAL) {
				status.setCompanyId(0);
			}
			MasterPartsDao.save(status);
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addMasterPartsLocation(MasterPartsLocation status) throws Exception {
		MasterPartsLocationRepository.save(status);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateMasterPartsLocation(MasterPartsLocation status) throws Exception {
		MasterPartsLocationRepository.save(status);
	}

	/**
	 * This Page get MasterParts table to get pagination values
	 * 
	 * @throws Exception
	 */
	@Override
	@Transactional
	public Page<MasterParts> getDeployment_Page_MasterParts(Integer pageNumber, Integer companyId) throws Exception {
		HashMap<String, Object>		configuration		= null;
		try {
			@SuppressWarnings("deprecation")
			PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "partid");
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
				return MasterPartsDao.listParts(companyId, request);
			}else {
				return MasterPartsDao.listPartsForOwn(companyId, request);
			}
		} catch (Exception e) {
			throw e;
		}finally {
			configuration	= null;
		}
		
	}

	/**
	 * This List get MasterParts table to get pagination last 10 entries values
	 */
	@Override
	public List<MasterPartsDto> listMasterParts(Integer pageNumber, Integer companyId) throws Exception {
		HashMap<String, Object> 		configuration		= null;
		TypedQuery<Object[]> 			query 				= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, mp.partTypeId,"
						+ " PC.pcName , PM.pmName, PMU.pmuName, mp.description, mp.created, mp.lastupdated,"
						+ " mp.isOwnMaterParts, mp.partManufacturerType, mp.localPartName, mp.partNameOnBill"
						+ " FROM MasterParts as mp "
						+ " INNER JOIN PartCategories PC ON PC.pcid = mp.categoryId"
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " LEFT JOIN PartMeasurementUnit PMU ON PMU.pmuid = mp.unitTypeId"
						+ " WHERE ( mp.companyId = "
						+ companyId + " OR mp.companyId = 0) AND isOwnMaterParts = 0 AND mp.markForDelete = 0 ORDER BY mp.partid desc", Object[].class);
			}else {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, mp.partTypeId,"
						+ " PC.pcName , PM.pmName, PMU.pmuName, mp.description, mp.created, mp.lastupdated,"
						+ " mp.isOwnMaterParts, mp.partManufacturerType, mp.localPartName, mp.partNameOnBill "
						+ " FROM MasterParts as mp "
						+ " INNER JOIN PartCategories PC ON PC.pcid = mp.categoryId"
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " LEFT JOIN PartMeasurementUnit PMU ON PMU.pmuid = mp.unitTypeId"
						+ " WHERE mp.companyId = "
						+ companyId + " AND isOwnMaterParts = 1 AND mp.markForDelete = 0 ORDER BY mp.partid desc", Object[].class);
			}
			
			query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			query.setMaxResults(PAGE_SIZE);

			List<Object[]> results = query.getResultList();

			List<MasterPartsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<MasterPartsDto>();
				MasterPartsDto list = null;
				for (Object[] result : results) {
					list = new MasterPartsDto();
					
					list.setPartid((Long) result[0]);

					list.setPartnumber((String) result[1]);
					list.setPartname((String) result[2]);
					list.setPartTypeId((short) result[3]);
					list.setParttype(PartType.getPartTypeName((short) result[3]));
					list.setCategory((String) result[4]);
					list.setMake((String) result[5]);
					//list.setLowstocklevel((Integer) result[6]);
					//list.setReorderquantity((Integer) result[7]);
					list.setUnittype((String) result[6]);
					list.setDescription((String) result[7]);
					list.setCreatedOn((Date) result[8]);
					list.setLastupdatedOn((Date) result[9]);
					list.setOwnMaterParts((boolean) result[10]);
					list.setPartManufacturerType((short) result[11]);
					
					if(result[12] != null && !(((String) result[12]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[12]);
					}
					if(result[13] != null && !(((String) result[13]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[13]);
					}
					
					Dtos.add(list);
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

	/**
	 * get MasterParts List By Company Id
	 */
	@Override
	public List<MasterPartsDto> listMasterPartsByCompanyId(Integer companyId) throws Exception {
		TypedQuery<Object[]> 			query 				= null;
		HashMap<String, Object>			configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, PM.pmName,"
						+ "  mp.localPartName, mp.partNameOnBill"
						+ " FROM MasterParts as mp "
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " WHERE ( mp.companyId = "+ companyId + " OR mp.companyId = 0) AND isOwnMaterParts = 0"
						+ " AND mp.markForDelete = 0 ORDER BY mp.partnumber", Object[].class);
				
			}else {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, PM.pmName,"
						+ "  mp.localPartName, mp.partNameOnBill"
						+ " FROM MasterParts as mp "
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " WHERE mp.companyId = "+companyId +" AND isOwnMaterParts = 1 AND mp.markForDelete = 0 "
						+ " ORDER BY mp.partnumber", Object[].class);
				
			}
			
			List<Object[]> results = query.getResultList();
			
			List<MasterPartsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<MasterPartsDto>();
				MasterPartsDto list = null;
				for (Object[] result : results) {
					list = new MasterPartsDto();
					
					list.setPartid((Long) result[0]);
					list.setPartnumber((String) result[1]);
					list.setPartname((String) result[2]);
					list.setMake((String) result[3]);
					
					if(result[4] != null && !(((String) result[4]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[4]);
					}
					if(result[5] != null && !(((String) result[5]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[5]);
					}
					
					Dtos.add(list);
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

	@Override
	public List<MasterParts> findAllMasterParts() throws Exception {
		return MasterPartsDao.findAll();
	}

	@Override
	public List<MasterPartsDto> SearchMasterParts(String search, Integer companyId) throws Exception {
		HashMap<String, Object> 		configuration		= null;
		TypedQuery<Object[]> 			query 				= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			List<MasterPartsDto> Dtos = null;
			
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, mp.partTypeId,"
						+ " PC.pcName , PM.pmName, PMU.pmuName, mp.description, mp.created, mp.lastupdated, "
						+ " mp.partManufacturerType, mp.localPartName, mp.partNameOnBill"
						+ " FROM MasterParts as mp "
						+ " INNER JOIN PartCategories PC ON PC.pcid = mp.categoryId"
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " INNER JOIN PartMeasurementUnit PMU ON PMU.pmuid = mp.unitTypeId"
						+ " WHERE ( lower(partnumber) Like (:term) OR lower(mp.partname) Like (:term) OR lower(mp.localPartName) Like (:term)  OR lower(mp.partNameOnBill) Like (:term) ) "
						+ " AND ( mp.companyId = "+companyId + " OR mp.companyId = 0) AND isOwnMaterParts = 0 AND mp.markForDelete = 0 ", Object[].class);
			}else {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, mp.partTypeId,"
						+ " PC.pcName , PM.pmName, PMU.pmuName, mp.description, mp.created, mp.lastupdated, "
						+ " mp.partManufacturerType, mp.localPartName, mp.partNameOnBill "
						+ " FROM MasterParts as mp "
						+ " INNER JOIN PartCategories PC ON PC.pcid = mp.categoryId"
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " INNER JOIN PartMeasurementUnit PMU ON PMU.pmuid = mp.unitTypeId"
						+ " WHERE ( lower(partnumber) Like (:term) OR lower(mp.partname) Like (:term) OR lower(mp.localPartName) Like (:term)  OR lower(mp.partNameOnBill) Like (:term) ) "
						+ " AND mp.companyId = " + companyId + " AND mp.isOwnMaterParts = 1 AND mp.markForDelete = 0 ", Object[].class);
			
			}
			query.setParameter("term", "%"+search+"%");
			
 			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<MasterPartsDto>();
				MasterPartsDto list = null;
				for (Object[] result : results) {
					list = new MasterPartsDto();
					
					list.setPartid((Long) result[0]);

					list.setPartnumber((String) result[1]);
					list.setPartname((String) result[2]);
					list.setPartTypeId((short) result[3]);
					list.setParttype(PartType.getPartTypeName((short) result[3]));
					list.setCategory((String) result[4]);
					list.setMake((String) result[5]);
					list.setUnittype((String) result[6]);
					list.setDescription((String) result[7]);

					list.setCreatedOn((Date) result[8]);
					list.setLastupdatedOn((Date) result[9]);
					list.setPartManufacturerType((short) result[10]);
					
					if(result[11] != null && !(((String) result[11]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[11]);
					}
					if(result[12] != null && !(((String) result[12]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[12]);
					}
					
					Dtos.add(list);
				}
			}
			}
		 return Dtos;
		
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	

	@Override
	public List<MasterPartsDto> SearchInventorySelectParts(String search, Integer companyId) throws Exception {
		HashMap<String, Object> 		configuration		= null;
		TypedQuery<Object[]> 			query 				= null;
		try {
			
			List<MasterPartsDto> Dtos = null;
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, mp.partTypeId,"
						+ " PC.pcName , PM.pmName,PMU.pmuName, mp.description, mp.created, mp.lastupdated"
						+ ", mp.partManufacturerType , mp.localPartName, mp.partNameOnBill"
						+ " FROM MasterParts as mp "
						+ " INNER JOIN PartCategories PC ON PC.pcid = mp.categoryId"
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " INNER JOIN PartMeasurementUnit PMU ON PMU.pmuid = mp.unitTypeId"
						+ " WHERE ( lower(partnumber) Like (:term) OR lower(mp.partname) Like (:term) OR lower(mp.localPartName) Like (:term) OR lower(mp.partNameOnBill) Like (:term) ) "
						+ " AND ( mp.companyId = "+companyId + " OR mp.companyId = 0) AND isOwnMaterParts = 0 AND mp.markForDelete = 0 ", Object[].class);
			}else {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, mp.partTypeId,"
						+ " PC.pcName , PM.pmName, PMU.pmuName, mp.description, mp.created, mp.lastupdated, "
						+ " mp.partManufacturerType , mp.localPartName, mp.partNameOnBill"
						+ " FROM MasterParts as mp "
						+ " INNER JOIN PartCategories PC ON PC.pcid = mp.categoryId"
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " INNER JOIN PartMeasurementUnit PMU ON PMU.pmuid = mp.unitTypeId"
						+ " WHERE ( lower(partnumber) Like (:term) OR lower(mp.partname) Like (:term) OR lower(mp.localPartName) Like (:term) OR lower(mp.partNameOnBill) Like (:term) ) "
						+ " AND mp.companyId = " + companyId + " AND mp.isOwnMaterParts = 1 AND mp.markForDelete = 0 ", Object[].class);
			
			}
			query.setParameter("term", "%"+search+"%");
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<MasterPartsDto>();
				MasterPartsDto list = null;
				for (Object[] result : results) {
					list = new MasterPartsDto();
					
					list.setPartid((Long) result[0]);

					list.setPartnumber((String) result[1]);
					list.setPartname((String) result[2]);
					list.setPartTypeId((short) result[3]);
					list.setParttype(PartType.getPartTypeName((short) result[3]));
					list.setCategory((String) result[4]);
					list.setMake((String) result[5]);
					list.setUnittype((String) result[6]);
					list.setDescription((String) result[7]);
					list.setCreatedOn((Date) result[8]);
					list.setLastupdatedOn((Date) result[9]);
					list.setPartManufacturerType((short) result[10]);
					
					if(result[11] != null && !(((String) result[11]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[11]);
					}
					if(result[12] != null && !(((String) result[12]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[12]);
					}
					
					Dtos.add(list);
				}
			}
			}
		 return Dtos;

		} catch (Exception e) {
			throw e;
		}
			
	}
	
	@Override
	public List<MasterPartsDto> SearchInventorySelectPartsOnType(String search, Integer companyId, short type) throws Exception {
		HashMap<String, Object> 		configuration		= null;
		TypedQuery<Object[]> 			query 				= null;
		List<MasterPartsDto> Dtos = null;
		try {
			
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, mp.partTypeId,"
						+ " PC.pcName , PM.pmName,PMU.pmuName, mp.description, mp.created, mp.lastupdated, "
						+ " mp.partManufacturerType , mp.localPartName, mp.partNameOnBill"
						+ " FROM MasterParts as mp "
						+ " INNER JOIN PartCategories PC ON PC.pcid = mp.categoryId"
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " INNER JOIN PartMeasurementUnit PMU ON PMU.pmuid = mp.unitTypeId"
						+ " WHERE ( lower(partnumber) Like (:term) OR lower(mp.partname) Like (:term) OR lower(mp.localPartName) Like (:term) OR lower(mp.partNameOnBill) Like (:term) ) "
						+ " AND ( mp.companyId = "+companyId + " OR mp.companyId = 0) AND mp.partTypeCategoryId = "+type+" AND isOwnMaterParts = 0 AND mp.markForDelete = 0 ", Object[].class);
			}else {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, mp.partTypeId,"
						+ " PC.pcName , PM.pmName, PMU.pmuName, mp.description, mp.created, mp.lastupdated,"
						+ " mp.partManufacturerType, mp.localPartName, mp.partNameOnBill "
						+ " FROM MasterParts as mp "
						+ " INNER JOIN PartCategories PC ON PC.pcid = mp.categoryId"
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " INNER JOIN PartMeasurementUnit PMU ON PMU.pmuid = mp.unitTypeId"
						+ " WHERE ( lower(partnumber) Like (:term) OR lower(mp.partname) Like (:term) OR lower(mp.localPartName) Like (:term) OR lower(mp.partNameOnBill) Like (:term) ) "
						+ " AND mp.companyId = " + companyId + " AND mp.partTypeCategoryId = "+type+" AND mp.isOwnMaterParts = 1 AND mp.markForDelete = 0 ", Object[].class);
			
			}
			query.setParameter("term", "%"+search+"%");
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<MasterPartsDto>();
				MasterPartsDto list = null;
				for (Object[] result : results) {
					list = new MasterPartsDto();
					
					list.setPartid((Long) result[0]);

					list.setPartnumber((String) result[1]);
					list.setPartname((String) result[2]);
					list.setPartTypeId((short) result[3]);
					list.setParttype(PartType.getPartTypeName((short) result[3]));
					list.setCategory((String) result[4]);
					list.setMake((String) result[5]);
					list.setUnittype((String) result[6]);
					list.setDescription((String) result[7]);
					list.setCreatedOn((Date) result[8]);
					list.setLastupdatedOn((Date) result[9]);
					list.setPartManufacturerType((short) result[10]);
					
					if(result[11] != null && !(((String) result[11]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[11]);
					}
					if(result[12] != null && !(((String) result[12]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[12]);
					}
					
					Dtos.add(list);
				}
			}
			}
		 return Dtos;

		} catch (Exception e) {
			throw e;
		}
			
	}

	@Override
	public MasterParts getMasterParts(Long part_id) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return MasterPartsDao.getMasterParts(part_id);
	}

	@Override
	public MasterPartsDto getMasterParts(Long part_id, Integer companyId) throws Exception {
		HashMap<String, Object> 		configuration		= null;
		Query 							query				= null; 
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
				query = entityManager.createQuery("SELECT MP.partid, MP.partnumber, MP.partname, MP.partTypeId,"
						+ " PC.pcName , PM.pmName, PMU.pmuName, MP.description, MP.created, MP.lastupdated,"
						+ " MP.part_photoid, MP.partTypeId, MP.categoryId, MP.makerId, MP.unitTypeId, U.email, U2.email, MP.partManufacturerType,"
						+ " MP.isRefreshment, MP.subCategory, MP.localPartName, MP.partNameOnBill, MP.isWarrantyApplicable, "
						+ " MP.isCouponAvailable, MP.isRepairable, MP.isScrapAvilable, MP.warrantyInMonths, MP.couponDetails, "
						+ " MP.partTypeCategoryId, MP.assetIdRequired "
						+ " FROM MasterParts MP "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId"
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
						+ " INNER JOIN PartMeasurementUnit PMU ON PMU.pmuid = MP.unitTypeId"
						+ " LEFT JOIN User U ON U.id = MP.createdById"
						+ " LEFT JOIN User U2 ON U2.id = MP.lastModifiedById "
						+ " where MP.partid= "+part_id+" AND (MP.companyId = "+companyId+" OR MP.companyId = 0) AND MP.isOwnMaterParts = 0 AND MP.markForDelete = 0");
			}else {
				query = entityManager.createQuery("SELECT MP.partid, MP.partnumber, MP.partname, MP.partTypeId,"
						+ " PC.pcName , PM.pmName, PMU.pmuName, MP.description, MP.created, MP.lastupdated,"
						+ " MP.part_photoid, MP.partTypeId, MP.categoryId, MP.makerId, MP.unitTypeId, U.email, U2.email, MP.partManufacturerType,"
						+ " MP.isRefreshment, MP.subCategory,"
						+ " MP.localPartName, MP.partNameOnBill, MP.isWarrantyApplicable, MP.isCouponAvailable, MP.isRepairable,"
						+ "	MP.isScrapAvilable, MP.warrantyInMonths, MP.couponDetails, MP.partTypeCategoryId , MP.assetIdRequired"
						+ " FROM MasterParts MP "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId"
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
						+ " INNER JOIN PartMeasurementUnit PMU ON PMU.pmuid = MP.unitTypeId"
						+ " LEFT JOIN User U ON U.id = MP.createdById"
						+ " LEFT JOIN User U2 ON U2.id = MP.lastModifiedById"
						+ " where MP.partid= "+part_id+" AND MP.companyId = "+companyId+" AND MP.isOwnMaterParts = 1 AND MP.markForDelete = 0");
			}

			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			MasterPartsDto list;
			if (result != null) {
				list	= new MasterPartsDto();
				
				list.setPartid((Long) result[0]);
				list.setPartnumber((String) result[1]);
				list.setPartname((String) result[2]);
				list.setPartTypeId((short) result[3]);
				list.setParttype(PartType.getPartTypeName((short) result[3]));
				list.setCategory((String) result[4]);
				list.setMake((String) result[5]);
				list.setUnittype((String) result[6]);
				list.setDescription((String) result[7]);
				list.setCreatedOn((Date) result[8]);
				list.setLastupdatedOn((Date) result[9]);
				list.setPart_photoid((Long) result[10]);
				list.setPartTypeId((short) result[11]);
				list.setCategoryId((long) result[12]);
				list.setMakerId((long) result[13]);
				list.setUnitTypeId((long) result[14]);
				list.setCreatedBy((String) result[15]);
				list.setLastModifiedBy((String) result[16]);
				list.setPartManufacturerType( (short) result[17]);
				list.setRefreshment((boolean) result[18]);
				list.setPartSubCategoryName((String) result[19]);
				list.setLocalPartName((String) result[20]);
				list.setPartNameOnBill((String) result[21]);
				list.setWarrantyApplicable((boolean) result[22]);
				list.setCouponAvailable((boolean) result[23]);
				list.setRepairable((boolean) result[24]);
				list.setScrapAvilable((boolean) result[25]);
				list.setWarrantyInMonths((Double) result[26]);
				list.setCouponDetails((String) result[27]);
				list.setPartTypeCategoryId((short) result[28]);
				list.setAssetIdRequired((boolean) result[29]);
				
			} else {
				return null;
			}

			return list;

		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
			
	}
	
	@Override
	public List<MasterPartsLocationDto> getMasterPartsLocation(Long partid, Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT MPL.partlocationid, MPL.Aisle, MPL.bin, MPL.row, PL.partlocation_name, MPL.locationId, IL.location_quantity, IL.inventory_location_id "
							+ " FROM MasterPartsLocation AS MPL"
							+ " INNER JOIN PartLocations AS PL ON PL.partlocation_id = MPL.locationId"
							+ " LEFT JOIN InventoryLocation AS IL ON IL.partid = MPL.masterparts.partid AND IL.locationId = MPL.locationId AND IL.location_quantity > 0"
							+ " WHERE MPL.masterparts.partid=" + partid + " AND MPL.companyId = " + companyId
							+ " AND MPL.markForDelete = 0 ",
					Object[].class);
			List<Object[]> results = query.getResultList();

			List<MasterPartsLocationDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<MasterPartsLocationDto>();
				MasterPartsLocationDto list = null;
				for (Object[] result : results) {
					list = new MasterPartsLocationDto();

					list.setPartlocationid((Integer) result[0]);
					list.setAisle((String) result[1]);
					list.setBin((String) result[2]);
					list.setRow((String) result[3]);
					list.setLocation((String) result[4]);
					list.setLocationId((Integer) result[5]);
					if(result[6] != null) {
						list.setLocation_quantity((Double) result[6]);
					}else {
						list.setLocation_quantity((double) 0);
					}
					list.setInventory_location_id((Long) result[7]);
					Dtos.add(list);
				}
			}
		 return Dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}

	@Override
	@Transactional
	public void deleteMasterParts(Long part_id, Integer companyId) throws Exception {
		MasterPartsDao.deleteMasterParts(part_id, companyId);
	}

	@Override
	@Transactional
	public void deleteMasterPartsLocation(Long status, Integer companyId) throws Exception {
		MasterPartsLocationRepository.deleteMasterPartsLocation(status, companyId);
	}

	@Override
	@Transactional
	public List<MasterParts> listOnlyStatus() throws Exception {
		// return MasterPartsDao.listOnlyStatus();
		String query = "SELECT NEW MasterParts(i.partnumber, i.partname) FROM MasterParts i ";
		TypedQuery<MasterParts> typedQuery = entityManager.createQuery(query, MasterParts.class);
		List<MasterParts> results = typedQuery.getResultList();

		return results;
	}

	@Override
	@Transactional
	public Long countpart(Integer companyId) throws Exception {
		return MasterPartsDao.countByCompanyId(companyId);
	}

	@Override
	public List<MasterPartsLocationDto> getInventoryMasterPartsLocation(Long partid, Integer Location_ID, Integer companyId)
			throws Exception {
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT MPL.partlocationid, MPL.Aisle, MPL.bin, MPL.row, PL.partlocation_name, MPL.locationId "
						+ " FROM MasterPartsLocation AS MPL"
						+ " INNER JOIN PartLocations AS PL ON PL.partlocation_id = MPL.locationId "
						+ " WHERE MPL.masterparts.partid=" + partid + " AND MPL.locationId=" + Location_ID
						+ " AND MPL.companyId = " + companyId + " AND MPL.markForDelete = 0",
				Object[].class);
		List<Object[]> results = query.getResultList();

		List<MasterPartsLocationDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<MasterPartsLocationDto>();
			MasterPartsLocationDto list = null;
			for (Object[] result : results) {
				list = new MasterPartsLocationDto();

				list.setPartlocationid((Integer) result[0]);
				list.setAisle((String) result[1]);
				list.setBin((String) result[2]);
				list.setRow((String) result[3]);
				list.setLocation((String) result[4]);
				list.setLocationId((Integer) result[5]);

				Dtos.add(list);
			}
		}
	 return Dtos;
	
	}

	@Override
	@Transactional
	public List<org.fleetopgroup.persistence.document.MasterPartsPhoto> listMasterPartsPhoto(Long partid, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("partid").is(partid).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.MasterPartsPhoto.class);

	}

	@Override
	@Transactional
	public void addMasterPartsPhoto(org.fleetopgroup.persistence.document.MasterPartsPhoto masterPartsPhoto) throws Exception {
		masterPartsPhoto.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_MASTER_PARTS_PHOTO));
		mongoTemplate.save(masterPartsPhoto);
	}

	@Override
	public Long getPhotoCount(Long partId, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("partid").is(partId).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.count(query, org.fleetopgroup.persistence.document.MasterPartsPhoto.class);
	}
	
	@Override
	@Transactional
	public org.fleetopgroup.persistence.document.MasterPartsPhoto getMasterPartsPhoto(Long sid) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(sid).and("companyId").is(userDetails.getCompany_id()).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.MasterPartsPhoto.class);
	}

	@Override
	@Transactional
	public void deleteMasterPartsPhoto(Long Part_photoid, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query(Criteria.where("_id").is(Part_photoid).and("companyId").is(companyId));
		Update update = new Update();
		update.set("markForDelete", true);
		mongoTemplate.updateFirst(query, update, org.fleetopgroup.persistence.document.MasterPartsPhoto.class);
	}

	@Override
	@Transactional
	public void setMasterPartProfilePhoto(Long Part_photoid, Long PartId) throws Exception {

		// MasterPartsDao.setMasterPartProfilePhoto(Part_photoid, PartId);

		entityManager
				.createQuery("UPDATE MasterParts SET part_photoid=" + Part_photoid + " WHERE partid =" + PartId + " ")
				.executeUpdate();
		entityManager
				.createQuery("UPDATE Inventory SET part_photoid=" + Part_photoid + " WHERE partid =" + PartId + " ")
				.executeUpdate();

		entityManager
				.createQuery("UPDATE InventoryAll SET part_photoid=" + Part_photoid + " WHERE partid =" + PartId + " ")
				.executeUpdate();

		entityManager
				.createQuery(
						"UPDATE InventoryLocation SET part_photoid=" + Part_photoid + " WHERE partid =" + PartId + " ")
				.executeUpdate();
	}

	@Override
	@Transactional
	public List<MasterPartsDto> ReportMasterParts(String searchReport, Integer companyId) throws Exception {
		HashMap<String, Object> 		configuration		= null;
		TypedQuery<Object[]> 			query 				= null;
		HashMap<Long, MasterPartsDto>   tempHM				= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, mp.partTypeId,"
						+ " PC.pcName , PM.pmName,  PMU.pmuName, mp.description, mp.created, mp.lastupdated, "
						+ " mp.partManufacturerType, mp.localPartName, mp.partNameOnBill "
						+ " FROM MasterParts as mp "
						+ " INNER JOIN PartCategories PC ON PC.pcid = mp.categoryId"
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " INNER JOIN PartMeasurementUnit PMU ON PMU.pmuid = mp.unitTypeId"
						+ " LEFT JOIN MasterPartsExtraDetails ME ON ME.partid = mp.partid AND ME.markForDelete = 0"
						+ " LEFT JOIN MasterPartsToVehicleMaker MM ON MM.partId = mp.partid  AND MM.markForDelete = 0 "
						+ " LEFT JOIN MasterPartsToVehicleModal VM ON VM.partId = mp.partid  AND VM.markForDelete = 0 "
						+"  WHERE mp.markForDelete = 0 " + searchReport + " AND (mp.companyId = "+companyId+" OR mp.companyId = 0) AND mp.isOwnMaterParts = 0", Object[].class);
			}else {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, mp.partTypeId,"
						+ " PC.pcName , PM.pmName, PMU.pmuName, mp.description, mp.created, mp.lastupdated, "
						+ " mp.partManufacturerType, mp.localPartName, mp.partNameOnBill "
						+ " FROM MasterParts as mp "
						+ " INNER JOIN PartCategories PC ON PC.pcid = mp.categoryId"
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " INNER JOIN PartMeasurementUnit PMU ON PMU.pmuid = mp.unitTypeId "
						+ " LEFT JOIN MasterPartsExtraDetails ME ON ME.partid = mp.partid  AND ME.markForDelete = 0"
						+ " LEFT JOIN MasterPartsToVehicleMaker MM ON MM.partId = mp.partid  AND MM.markForDelete = 0 "
						+ " LEFT JOIN MasterPartsToVehicleModal VM ON VM.partId = mp.partid  AND VM.markForDelete = 0 "
						+"  WHERE mp.markForDelete = 0 " + searchReport + " AND mp.companyId = "+companyId+" AND mp.isOwnMaterParts = 1", Object[].class);
			}
			 
			List<Object[]> results = query.getResultList();

			List<MasterPartsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				tempHM	= new HashMap<Long, MasterPartsDto>();
				Dtos = new ArrayList<MasterPartsDto>();
				MasterPartsDto list = null;
				for (Object[] result : results) {
					list = new MasterPartsDto();
					
					list.setPartid((Long) result[0]);

					list.setPartnumber((String) result[1]);
					list.setPartname((String) result[2]);
					list.setPartTypeId((short) result[3]);
					list.setParttype(PartType.getPartTypeName((short) result[3]));
					list.setCategory((String) result[4]);
					list.setMake((String) result[5]);
					list.setUnittype((String) result[6]);
					list.setDescription((String) result[7]);
					list.setCreatedOn((Date) result[8]);
					list.setLastupdatedOn((Date) result[9]);
					list.setPartManufacturerType((short) result[10]);
					
					if(result[11] != null && !(((String) result[11]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[11]);
					}
					if(result[12] != null && !(((String) result[12]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[12]);
					}
					
					tempHM.put(list.getPartid(), list);
					
				}
			}
			if(tempHM != null && !tempHM.isEmpty()) {
				Dtos	= new ArrayList<MasterPartsDto>(tempHM.values());
			}
			
		 return Dtos;
		

		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	@Transactional
	public List<MasterParts> GetMasterPartValidate(String Part_No, String Part_name, Integer companyId)
			throws Exception {
		HashMap<String, Object>  		configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
				return MasterPartsDao.GetMasterPartValidate(Part_No, Part_name, companyId);
			}else {
				return MasterPartsDao.GetMasterPartValidateForOwn(Part_No, Part_name, companyId);
			}
			
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
	
	
		
	}

	

	@Override
	public MasterParts validatePartNumber(String partnumber,String partName, Long makerId, Integer companyId) throws Exception {
		HashMap<String, Object>  		configuration		= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
				return MasterPartsDao.validatePartNumber(partnumber, makerId, companyId);
			}else {
				return MasterPartsDao.validatePartNumberForOwn(partnumber, makerId, companyId);
			}
			
		} catch (Exception e) {
			throw e;
		}finally {
			configuration		= null;
		}
	
	}

	@Override
	public void transferMasterPartsPhoto(List<MasterPartsPhoto> list) throws Exception {
		org.fleetopgroup.persistence.document.MasterPartsPhoto				masterPartsPhoto		= null;
		List<org.fleetopgroup.persistence.document.MasterPartsPhoto> 		masterPartsPhotoList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				masterPartsPhotoList	= new ArrayList<>();
				for(org.fleetopgroup.persistence.model.MasterPartsPhoto	document : list) {
					masterPartsPhoto	= new org.fleetopgroup.persistence.document.MasterPartsPhoto();
					
					masterPartsPhoto.set_id(document.getPart_photoid());
					masterPartsPhoto.setPart_photoname(document.getPart_photoname());
					masterPartsPhoto.setPart_filename(document.getPart_filename());
					masterPartsPhoto.setPartid(document.getPartid());
					masterPartsPhoto.setPart_content(document.getPart_content());
					masterPartsPhoto.setPart_contentType(document.getPart_contentType());
					masterPartsPhoto.setCompanyId(document.getCompanyId());
					masterPartsPhoto.setMarkForDelete(document.isMarkForDelete());
					masterPartsPhoto.setCreatedById(document.getCreatedById());
					masterPartsPhoto.setLastModifiedById(document.getLastModifiedById());
					masterPartsPhoto.setCreated(document.getCreated());
					masterPartsPhoto.setLastupdated(document.getLastupdated());
					
					masterPartsPhotoList.add(masterPartsPhoto);
				}
				System.err.println("Saving MasterPartsPhoto ....");
				mongoTemplate.insert(masterPartsPhotoList, org.fleetopgroup.persistence.document.MasterPartsPhoto.class);
				System.err.println("Saved Successfully....");
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}
	@Override
	public long getMasterPartsPhotoMaxId() throws Exception {
		try {
			return masterPartsPhotoRepository.getMasterPartsPhotoMaxId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public void saveLowStockLevel(LowStockInventory lowStockInventory) throws Exception {
		lowStockInventoryRepository.save(lowStockInventory);
	}
	
	@Override
	public void updateLowStockLevel(LowStockInventory lowStockInventory) throws Exception {
		lowStockInventoryRepository.updatePriceInLowStockInventory(lowStockInventory.getPartid(),lowStockInventory.getUnitCost(),lowStockInventory.getDiscount(),lowStockInventory.getTax());
	}
	
	@Override
	public MasterPartsLocation validateMasterPartLocation(Long partId, Integer locationId, Integer companyId) throws Exception {
		
		return MasterPartsLocationRepository.validateMasterPartLocation(partId,locationId, companyId);
	}
	
	@Override
	public LowStockInventory validateLowStockInventory(Long partId, Integer companyId, Integer locationId) throws Exception {
		
		return lowStockInventoryRepository.validateLowStockInventory(partId, companyId, locationId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject saveNewMasterPartsDetails(ValueObject valueObject) throws Exception {
		MasterParts					masterParts			= null;
		MasterPartsExtraDetails		extraDetails	 	= null;
		ArrayList<ValueObject> 		dataArrayObjColl 	= null;
		MasterParts					masterPartsFromDB	= null;
		HashMap<String, Object> 	configuration		= null;
		try {
			 masterParts	= MasterPartsBL.getMasterPartsDto(valueObject);
			 configuration		= companyConfigurationService.getCompanyConfiguration(masterParts.getCompanyId(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			 masterPartsFromDB	= validatePartNumber(masterParts.getPartnumber(), masterParts.getPartname(), masterParts.getMakerId(), masterParts.getCompanyId());
			 if(masterPartsFromDB == null || valueObject.getBoolean("fromExcelEntry", false)) {
				 if(!((boolean) configuration.getOrDefault(MasterPartsConfigurationConstants.SHOW_PART_MANUFACTURER_TYPE_COL, false)) 
							&& masterParts.getPartManufacturerType() == 0) {
						masterParts.setPartManufacturerType(PartType.PART_MANUFACTURER_TYPE_LOCAL);
				 }
				 
				 
				 if(masterPartsFromDB != null && valueObject.getBoolean("fromExcelEntry", false)) {
					 masterParts.setPartid(masterPartsFromDB.getPartid());
					 
					 repairableVendorDetailsRepository.deleteBypartId(masterParts.getPartid());
					 childPartDetailsRepository.deleteByMainPartId(masterParts.getPartid());
					 substitudePartDetailsRepository.deleteByMainPartId(masterParts.getPartid());
					 partPurchaseVendorRepository.deleteByPartId(masterParts.getPartid());
					 makerReepository.deleteByPartId(masterParts.getPartid());
					 modalRepository.deleteByPartId(masterParts.getPartid());
				 }
				 
				 if((boolean) configuration.get("showExtendedPartSave")) {
					 masterParts.setPartManufacturerType(PartType.PART_MANUFACTURER_TYPE_ORIGINAL);
					 masterParts.setOwnMaterParts(true);
				 }
				 
				 MasterPartsDao.save(masterParts);
				 
				 extraDetails	= MasterPartsBL.getMasterPartsExtraDetailsDto(valueObject);
				 if(extraDetails != null) {
					 extraDetails.setPartid(masterParts.getPartid());
					 extraDetailsRepository.save(extraDetails);
				 }
				 
				 if(valueObject.getString("vehicleMake", null) != null) {
					 
					 saveVehicleMakersDetails(valueObject, masterParts);	
				 }
				 if(valueObject.getString("vehicleModel", null) != null) {
					 saveVehicleModalDetails(valueObject, masterParts);
					 	
				 }
				 
				 if(masterParts.isRepairable() && valueObject.getString("repairingVendor", null) != null) {
					 saveRepairableVendorDetails(valueObject, masterParts);	
				 }
				 
				 if(valueObject.getString("childPartDetails", null) != null && valueObject.getShort("partTypeCatgory") == PartType.PART_TYPE_CATEGORY_PARENT) {
					 saveChildPartDetails(valueObject, masterParts);	
				 }else if(valueObject.getString("childPartDetails", null) != null && valueObject.getShort("partTypeCatgory") == PartType.PART_TYPE_CATEGORY_CHILD){
					 saveParentPartDetails(valueObject, masterParts);
				 }
				 
				 if(valueObject.getString("subtituteParts", null) != null) {
					 saveSubstitudePartDetails(valueObject, masterParts);	
				 }
				 
				 if(valueObject.getString("purchaseVendor", null) != null) {
					 savePurchaseVednors(valueObject, masterParts);	
				 }
				 
				 if(valueObject.containsKey("unitPrice")) {
					 masterPartRateRepository.save(masterPartsBL.getPartPrice(valueObject, masterParts));
				 }
				 
				 dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("locationDetails");
				 
				 if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
					 MasterPartsLocation 		inven 				= null;
					 for (ValueObject object : dataArrayObjColl) {
						 inven	= new MasterPartsLocation();
						 inven.setLocationId(object.getInt("locationId",0));
						 inven.setAisle(object.getString("aisle"));
						 inven.setRow(object.getString("row"));
						 inven.setBin(object.getString("bin"));
						 inven.setMasterparts(masterParts);
						 inven.setCompanyId(masterParts.getCompanyId());
						 
						 addMasterPartsLocation(inven);
					 }
				 }
				 valueObject.put("saved", true);
				 valueObject.put("partId", masterParts.getPartid());
			 }else {
				 valueObject.put("already", true);
			 }
			 
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	private void saveVehicleMakersDetails(ValueObject	valueObject, MasterParts	masterParts) throws Exception{
		String [] vendorArr	= valueObject.getString("vehicleMake").split(",");
		if(vendorArr != null && vendorArr.length > 0) {
			MasterPartsToVehicleMaker	details	= null;
			for (int i = 0; i < vendorArr.length; i++) {
				details	= new MasterPartsToVehicleMaker();
				details.setVehicleManufacturerId(Long.parseLong(vendorArr[i]+""));
				details.setPartId(masterParts.getPartid());
				details.setCompanyId(masterParts.getCompanyId());
				details.setCreatedById(masterParts.getCreatedById());
				details.setCreatedOn(new Date());
				
				makerReepository.save(details);
			}
		}
	}
	
	@Transactional
	private void saveVehicleModalDetails(ValueObject	valueObject, MasterParts	masterParts) throws Exception{
		String [] vendorArr	= valueObject.getString("vehicleModel").split(",");
		if(vendorArr != null && vendorArr.length > 0) {
			MasterPartsToVehicleModal	details	= null;
			for (int i = 0; i < vendorArr.length; i++) {
				details	= new MasterPartsToVehicleModal();
				details.setVehicleModelId(Long.parseLong(vendorArr[i]+""));
				details.setPartId(masterParts.getPartid());
				details.setCompanyId(masterParts.getCompanyId());
				details.setCreatedById(masterParts.getCreatedById());
				details.setCreatedOn(new Date());
				
				modalRepository.save(details);
			}
		}
	}
	
	@Transactional
	private void saveRepairableVendorDetails(ValueObject	valueObject, MasterParts	masterParts) throws Exception{
		String [] vendorArr	= valueObject.getString("repairingVendor").split(",");
		if(vendorArr != null && vendorArr.length > 0) {
			RepairableVendorDetails	details	= null;
			for (int i = 0; i < vendorArr.length; i++) {
				details	= new RepairableVendorDetails();
				details.setVendorId(Integer.parseInt(vendorArr[i]+""));
				details.setPartId(masterParts.getPartid());
				details.setCompanyId(masterParts.getCompanyId());
				repairableVendorDetailsRepository.save(details);
			}
		}
	}
	
	@Transactional
	private void saveChildPartDetails(ValueObject	valueObject, MasterParts	masterParts) throws Exception{
		String [] vendorArr	= valueObject.getString("childPartDetails").split(",");
		if(vendorArr != null && vendorArr.length > 0) {
			ChildPartDetails	details	= null;
			for (int i = 0; i < vendorArr.length; i++) {
				details	= new ChildPartDetails();
				details.setMainPartId(masterParts.getPartid());
				details.setPartId(Long.parseLong(vendorArr[i]+""));
				details.setCompanyId(masterParts.getCompanyId());
				childPartDetailsRepository.save(details);
			}
		}
	}
	
	private void saveParentPartDetails(ValueObject	valueObject, MasterParts	masterParts) throws Exception{

		String [] vendorArr	= valueObject.getString("childPartDetails").split(",");
		if(vendorArr != null && vendorArr.length > 0) {
			ChildPartDetails	details	= null;
			for (int i = 0; i < vendorArr.length; i++) {
				details	= new ChildPartDetails();
				details.setMainPartId(Long.parseLong(vendorArr[i]+""));
				details.setPartId(masterParts.getPartid());
				details.setCompanyId(masterParts.getCompanyId());
				childPartDetailsRepository.save(details);
			}
		}
	
	}
	
	@Transactional
	private void saveSubstitudePartDetails(ValueObject	valueObject, MasterParts	masterParts) throws Exception{
		String [] vendorArr	= valueObject.getString("subtituteParts").split(",");
		if(vendorArr != null && vendorArr.length > 0) {
			SubstitudePartDetails	details	= null;
			for (int i = 0; i < vendorArr.length; i++) {
				details	= new SubstitudePartDetails();
				details.setMainPartId(masterParts.getPartid());
				details.setPartId(Long.parseLong(vendorArr[i]+""));
				details.setCompanyId(masterParts.getCompanyId());
				substitudePartDetailsRepository.save(details);
			}
		}
	}
	
	@Transactional
	private void savePurchaseVednors(ValueObject	valueObject, MasterParts	masterParts) throws Exception{
		String [] vendorArr	= valueObject.getString("purchaseVendor").split(",");
		if(vendorArr != null && vendorArr.length > 0) {
			PartPurchaseVendor	details	= null;
			for (int i = 0; i < vendorArr.length; i++) {
				details	= new PartPurchaseVendor();
				details.setVendorId(Integer.parseInt(vendorArr[i]+""));
				details.setPartId(masterParts.getPartid());
				details.setCompanyId(masterParts.getCompanyId());
				
				partPurchaseVendorRepository.save(details);
			}
		}
	}
	
	
	@Override
	public MasterPartsExtraDetailsDto getMasterPartsExtraDetailsByPartId(Long partId, Integer companyId)
			throws Exception {
		try {

			Query query = entityManager.createQuery("SELECT BA.partsExtraDetailsId, BA.partid, BA.originalBrand, "
							+ " BA.vehicleManufacturerId, BA.vehicleModelId, BA.mainPacking, BA.uomPacking, BA.looseItem,"
							+ " BA.looseUom, BA.barCodeNumber, BA.itemType, PL.vehicleManufacturerName, "
							+ " VM.vehicleModelName,  V.pmName, BA.dimention"
							+ " FROM MasterPartsExtraDetails AS BA "
							+ " LEFT JOIN PartManufacturer V ON V.pmid = BA.originalBrand"
							+ " LEFT JOIN VehicleManufacturer PL ON PL.vehicleManufacturerId = BA.vehicleManufacturerId"
							+ " LEFT JOIN VehicleModel VM ON VM.vehicleModelId = BA.vehicleModelId "
							+ " where BA.partid = "+partId+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0");
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
				MasterPartsExtraDetailsDto	extraDetailsDto = null;
			
				if (result != null) {
					extraDetailsDto = new MasterPartsExtraDetailsDto();
					
					extraDetailsDto.setPartsExtraDetailsId((Long) result[0]);
					extraDetailsDto.setPartid((Long) result[1]);
					extraDetailsDto.setOriginalBrandId((Long) result[2]);
					extraDetailsDto.setVehicleManufacturerId((Long) result[3]);
					extraDetailsDto.setVehicleModelId((Long) result[4]);
					extraDetailsDto.setMainPacking((String) result[5]);
					extraDetailsDto.setUomPacking((String) result[6]);
					extraDetailsDto.setLooseItem((String) result[7]);
					extraDetailsDto.setLooseUom((String) result[8]);
					extraDetailsDto.setBarCodeNumber((String) result[9]);
					extraDetailsDto.setItemTypeId((short) result[10]);
					extraDetailsDto.setVehicleManufacturer((String) result[11]);
					extraDetailsDto.setVehicleModel((String) result[12]);
					extraDetailsDto.setOriginalBrand((String) result[13]);
					extraDetailsDto.setDimention((String) result[14]);
					
					extraDetailsDto.setItemType(PartType.getPartItemType(extraDetailsDto.getItemTypeId()));
					
			}
			
			return extraDetailsDto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<ChildPartDetailsDto> getChildPartDetailsByPartId(Long partId, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.childPartDetailsId, BA.mainPartId, BA.partId, "
							+ " VM.partnumber, VM.partname, VM.localPartName, VM.partNameOnBill "
							+ " FROM ChildPartDetails AS BA "
							+ " INNER JOIN MasterParts VM ON VM.partid = BA.partId "
							+ " where BA.mainPartId = "+partId+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<ChildPartDetailsDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				ChildPartDetailsDto	chilDetailsDto = null;
				for (Object[] result : results) {
					chilDetailsDto = new ChildPartDetailsDto();
					
					chilDetailsDto.setChildPartDetailsId((Long) result[0]);
					chilDetailsDto.setMainPartId((Long) result[1]);
					chilDetailsDto.setPartId((Long) result[2]);
					chilDetailsDto.setChildPartName((String) result[3]+"_"+(String) result[4]+"_"+(String) result[5]+"_"+(String) result[6]);
					
					chilDetailsDto.setChildPartName(chilDetailsDto.getChildPartName().replace("_null", ""));
					
					list.add(chilDetailsDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<ChildPartDetailsDto> getParentPartDetailsByPartId(Long partId, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.childPartDetailsId, BA.mainPartId, BA.partId, "
							+ " VM.partnumber, VM.partname, VM.localPartName, VM.partNameOnBill "
							+ " FROM ChildPartDetails AS BA "
							+ " INNER JOIN MasterParts VM ON VM.partid = BA.mainPartId "
							+ " where BA.partId = "+partId+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<ChildPartDetailsDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				ChildPartDetailsDto	chilDetailsDto = null;
				for (Object[] result : results) {
					chilDetailsDto = new ChildPartDetailsDto();
					
					chilDetailsDto.setChildPartDetailsId((Long) result[0]);
					chilDetailsDto.setMainPartId((Long) result[1]);
					chilDetailsDto.setPartId((Long) result[2]);
					chilDetailsDto.setChildPartName((String) result[3]+"_"+(String) result[4]+"_"+(String) result[5]+"_"+(String) result[6]);
					
					chilDetailsDto.setChildPartName(chilDetailsDto.getChildPartName().replace("_null", ""));
					
					System.err.println("getChildPartName "+chilDetailsDto.getChildPartName());
					System.err.println("chilDetailsDto "+chilDetailsDto.getMainPartId());
					
					list.add(chilDetailsDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<RepairableVendorDetailsDto> getRepairableVendorDetailsByPartId(Long partId, Integer companyId)
			throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.repairableVendorDetailsId, BA.vendorId, "
							+ " VM.vendorName, VM.vendorLocation "
							+ " FROM RepairableVendorDetails AS BA "
							+ " INNER JOIN Vendor VM ON VM.vendorId = BA.vendorId "
							+ " where BA.partId = "+partId+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<RepairableVendorDetailsDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				RepairableVendorDetailsDto	chilDetailsDto = null;
				for (Object[] result : results) {
					chilDetailsDto = new RepairableVendorDetailsDto();
					
					chilDetailsDto.setRepairableVendorDetailsId((Long) result[0]);
					chilDetailsDto.setVendorId((Integer) result[1]);
					chilDetailsDto.setVendorName((String) result[2]+"_"+(String) result[3]);
					
					list.add(chilDetailsDto);
				}
			}
			
			return list;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<PartPurchaseVendorDto> getPurchaseVendorDetailsByPartId(Long partId, Integer companyId)
			throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.partPurchaseVendorId, BA.vendorId, "
							+ " VM.vendorName, VM.vendorLocation "
							+ " FROM PartPurchaseVendor AS BA "
							+ " INNER JOIN Vendor VM ON VM.vendorId = BA.vendorId "
							+ " where BA.partId = "+partId+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<PartPurchaseVendorDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				PartPurchaseVendorDto	chilDetailsDto = null;
				for (Object[] result : results) {
					chilDetailsDto = new PartPurchaseVendorDto();
					
					chilDetailsDto.setPartPurchaseVendorId((Long) result[0]);
					chilDetailsDto.setVendorId((Integer) result[1]);
					chilDetailsDto.setVendorName((String) result[2]+"_"+(String) result[3]);
					
					list.add(chilDetailsDto);
				}
			}
			
			return list;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<SubstitudePartDetailsDto> getSubstitudePartDetailsByPartId(Long partId, Integer companyId) throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.substitudePartDetailsId, BA.mainPartId, "
							+ " VM.partnumber, VM.partname, VM.localPartName, VM.partNameOnBill , BA.partId"
							+ " FROM SubstitudePartDetails AS BA "
							+ " INNER JOIN MasterParts VM ON VM.partid = BA.partId "
							+ " where BA.mainPartId = "+partId+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0",Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<SubstitudePartDetailsDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				SubstitudePartDetailsDto	chilDetailsDto = null;
				for (Object[] result : results) {

					chilDetailsDto = new SubstitudePartDetailsDto();
					
					chilDetailsDto.setSubstitudePartDetailsId(partId);
					chilDetailsDto.setMainPartId((Long) result[1]);
					chilDetailsDto.setChildPartName((String) result[2]+"_"+(String) result[3]+"_"+(String) result[4]+"_"+(String) result[5]);
					chilDetailsDto.setPartId((Long) result[6]);
					chilDetailsDto.setChildPartName(chilDetailsDto.getChildPartName().replace("_null", ""));
				
					list.add(chilDetailsDto);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getMasterPartsDetailsForEdit(ValueObject valueObject) throws Exception {
		try {
			MasterPartsDto		masterParts	=  masterPartsBL.prepareMasterPartsBean(getMasterParts(valueObject.getLong("partId",0), valueObject.getInt("companyId",0)));
			HashMap<String, Object> config	= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId",0), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			valueObject.put("masterParts", masterParts);
		
			if((boolean) config.get("showExtendedPartSave")) {
				valueObject.put("extraDetails", getMasterPartsExtraDetailsByPartId(valueObject.getLong("partId",0), valueObject.getInt("companyId",0)));
				valueObject.put("purchaseVendors", getPurchaseVendorDetailsByPartId(valueObject.getLong("partId",0), valueObject.getInt("companyId",0)));
				valueObject.put("repairableVendors", getRepairableVendorDetailsByPartId(valueObject.getLong("partId",0), valueObject.getInt("companyId",0)));
				if(masterParts.getPartTypeCategoryId() == PartType.PART_TYPE_CATEGORY_CHILD) {
					valueObject.put("childParts", getParentPartDetailsByPartId(valueObject.getLong("partId",0), valueObject.getInt("companyId",0)));
				}else {
					valueObject.put("childParts", getChildPartDetailsByPartId(valueObject.getLong("partId",0), valueObject.getInt("companyId",0)));
				}
				
				valueObject.put("substituDeParts", getSubstitudePartDetailsByPartId(valueObject.getLong("partId",0), valueObject.getInt("companyId",0)));
				valueObject.put("partsLocation", getMasterPartsLocation(valueObject.getLong("partId",0), valueObject.getInt("companyId",0)));
				valueObject.put("vehicleMake", getMasterPartsToVehicleMakerList(valueObject.getLong("partId",0), valueObject.getInt("companyId",0)));
				valueObject.put("vehicleModal", getMasterPartsToVehicleModalList(valueObject.getLong("partId",0), valueObject.getInt("companyId",0)));
				
			}
			
			return valueObject;
		}catch(Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject updateNewMasterPartsDetails(ValueObject valueObject) throws Exception {
		MasterParts					masterParts			= null;
		MasterPartsExtraDetails		extraDetails	 	= null;
		ArrayList<ValueObject> 		dataArrayObjColl 	= null;
		MasterParts					masterPartsFromDB	= null;
		MasterParts					validate			= null;
		HashMap<String, Object> 	configuration		= null;
		try {
			 masterParts	= MasterPartsBL.getMasterPartsDto(valueObject);
			 configuration		= companyConfigurationService.getCompanyConfiguration(masterParts.getCompanyId(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			 
			 masterParts.setPartid(valueObject.getLong("partId",0));
			 
			 masterPartsFromDB	= getMasterParts(valueObject.getLong("partId",0));
			 
			 if(!masterPartsFromDB.getPartnumber().equalsIgnoreCase(masterParts.getPartnumber())
					 || !masterPartsFromDB.getPartname().equalsIgnoreCase(masterParts.getPartname())
					 || masterPartsFromDB.getMakerId() != masterParts.getMakerId() ) {
				 validate	= validatePartNumber(masterParts.getPartnumber(), masterParts.getPartname(), masterParts.getMakerId(), masterParts.getCompanyId());
			 }
			
			 if(validate == null || validate.getPartid().equals(valueObject.getLong("partId",0)) ) {
				 
				 if(!((boolean) configuration.getOrDefault(MasterPartsConfigurationConstants.SHOW_PART_MANUFACTURER_TYPE_COL, false)) 
							&& masterParts.getPartManufacturerType() == 0) {
						masterParts.setPartManufacturerType(PartType.PART_MANUFACTURER_TYPE_LOCAL);
				 }
				 
				 if((boolean) configuration.get("showExtendedPartSave")) {
					 masterParts.setPartManufacturerType(PartType.PART_MANUFACTURER_TYPE_ORIGINAL);
					 masterParts.setOwnMaterParts(true);
				 }
				 
				 MasterPartsDao.save(masterParts);
				 
				 extraDetails	= MasterPartsBL.getMasterPartsExtraDetailsDto(valueObject);
				 if(extraDetails != null) {
					 extraDetails.setPartid(masterParts.getPartid());
					 extraDetails.setPartsExtraDetailsId(valueObject.getLong("partsExtraDetailsId",0));
					 extraDetailsRepository.save(extraDetails);
				 }
				 
				 repairableVendorDetailsRepository.deleteBypartId(masterParts.getPartid());
				 
				 if(masterParts.getPartTypeCategoryId() == PartType.PART_TYPE_CATEGORY_CHILD) {
					 childPartDetailsRepository.deleteByChildPartId(masterParts.getPartid());
				 }else {
					 childPartDetailsRepository.deleteByMainPartId(masterParts.getPartid());
				 }
				 
				 substitudePartDetailsRepository.deleteByMainPartId(masterParts.getPartid());
				 partPurchaseVendorRepository.deleteByPartId(masterParts.getPartid());
				 makerReepository.deleteByPartId(masterParts.getPartid());
				 modalRepository.deleteByPartId(masterParts.getPartid());
				 
				 if(valueObject.getString("vehicleMake", null) != null) {
					 saveVehicleMakersDetails(valueObject, masterParts);	
				 }
				 if(valueObject.getString("vehicleModel", null) != null) {
					 saveVehicleModalDetails(valueObject, masterParts);
				 }
				 
				 if(masterParts.isRepairable() && valueObject.getString("repairingVendor", null) != null) {
					 saveRepairableVendorDetails(valueObject, masterParts);	
				 }
				 
				 if(valueObject.getString("childPartDetails", null) != null && valueObject.getShort("partTypeCatgory") == PartType.PART_TYPE_CATEGORY_PARENT) {
					 saveChildPartDetails(valueObject, masterParts);	
				 }else if(valueObject.getString("childPartDetails", null) != null && valueObject.getShort("partTypeCatgory") == PartType.PART_TYPE_CATEGORY_CHILD){
					 saveParentPartDetails(valueObject, masterParts);
				 }
				 
				 if(valueObject.getString("subtituteParts", null) != null) {
					 saveSubstitudePartDetails(valueObject, masterParts);	
				 }
				 
				 if(valueObject.getString("purchaseVendor", null) != null) {
					 savePurchaseVednors(valueObject, masterParts);	
				 }
				 
				 dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("locationDetails");
				 
				 if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
					 MasterPartsLocationRepository.deleteMasterPartsLocation(masterParts.getPartid(), masterParts.getCompanyId());
					 MasterPartsLocation 		inven 				= null;
					 for (ValueObject object : dataArrayObjColl) {
						 inven	= new MasterPartsLocation();
						 inven.setLocationId(object.getInt("locationId",0));
						 inven.setAisle(object.getString("aisle"));
						 inven.setRow(object.getString("row"));
						 inven.setBin(object.getString("bin"));
						 inven.setMasterparts(masterParts);
						 inven.setCompanyId(masterParts.getCompanyId());
						 
						 addMasterPartsLocation(inven);
					 }
				 }
				 valueObject.put("saved", true);
				 valueObject.put("partId", masterParts.getPartid());
			 }else {
				 valueObject.put("already", true);
			 }
			 
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<LowStockInventoryDto> getLowStockInventoryByPartId(Long partId, Integer companyId) throws Exception {

		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.lowStockInventoryId, BA.partid, BA.lowstocklevel, BA.reorderquantity, "
							+ "  BA.locationId, PL.partlocation_name "
							+ " FROM LowStockInventory AS BA "
							+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = BA.locationId "
							+ " where BA.partid = "+partId+" AND BA.companyId ="+companyId+" AND  BA.markForDelete = 0",Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<LowStockInventoryDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				LowStockInventoryDto	chilDetailsDto = null;
				for (Object[] result : results) {

					chilDetailsDto = new LowStockInventoryDto();
					
					chilDetailsDto.setLowStockInventoryId((Long) result[0]);
					chilDetailsDto.setPartid((Long) result[1]);
					chilDetailsDto.setLowstocklevel((Integer) result[2]);
					chilDetailsDto.setReorderquantity((Integer) result[3]);
					chilDetailsDto.setLocationId((Integer) result[4]);
					chilDetailsDto.setLocationName((String) result[5]);
				
					list.add(chilDetailsDto);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject removeLowStockDetails(ValueObject valueObject) throws Exception {
		try {
			lowStockInventoryRepository.deleteLowStockInventory(valueObject.getLong("lowStockInventoryId",0));
			valueObject.put("removed", true);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public List<MasterPartsDto> searchAllMasterParts(String search, Integer companyId) throws Exception {
		HashMap<String, Object> 		configuration		= null;
		TypedQuery<Object[]> 			query 				= null;
		List<MasterPartsDto> Dtos = null;
		try {
			
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, mp.partTypeId,"
						+ " PC.pcName , PM.pmName,PMU.pmuName, mp.description, mp.created, mp.lastupdated,"
						+ " mp.partManufacturerType, mp.autoMasterPart,mp.isWarrantyApplicable, mp.warrantyInMonths"
						+ ",  mp.localPartName, mp.partNameOnBill "
						+ " FROM MasterParts as mp "
						+ " LEFT JOIN PartCategories PC ON PC.pcid = mp.categoryId"
						+ " LEFT JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " LEFT JOIN PartMeasurementUnit PMU ON PMU.pmuid = mp.unitTypeId"
						+ " WHERE ( lower(partnumber) Like (:term) OR lower(mp.partname) Like (:term) OR lower(mp.localPartName) Like (:term) OR lower(mp.partNameOnBill) Like (:term) ) "
						+ " AND ( mp.companyId = "+companyId + " OR mp.companyId = 0) AND mp.isOwnMaterParts = 0  AND mp.markForDelete = 0  ", Object[].class);
			}else {
				query = entityManager.createQuery("SELECT mp.partid, mp.partnumber, mp.partname, mp.partTypeId,"
						+ " PC.pcName , PM.pmName, PMU.pmuName, mp.description, mp.created, mp.lastupdated, "
						+ " mp.partManufacturerType, mp.autoMasterPart, mp.isWarrantyApplicable, mp.warrantyInMonths"
						+ ",  mp.localPartName, mp.partNameOnBill "
						+ " FROM MasterParts as mp "
						+ " LEFT JOIN PartCategories PC ON PC.pcid = mp.categoryId"
						+ " LEFT JOIN PartManufacturer PM ON PM.pmid = mp.makerId "
						+ " LEFT JOIN PartMeasurementUnit PMU ON PMU.pmuid = mp.unitTypeId"
						+ " WHERE ( lower(partnumber) Like (:term) OR lower(mp.partname) Like (:term) OR lower(mp.localPartName) Like (:term) OR lower(mp.partNameOnBill) Like (:term) ) "
						+ " AND mp.companyId = " + companyId + " AND mp.isOwnMaterParts = 1 AND mp.markForDelete = 0 ", Object[].class);
			
			}
			query.setParameter("term", "%"+search+"%");
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<MasterPartsDto>();
				MasterPartsDto list = null;
				for (Object[] result : results) {
					list = new MasterPartsDto();
					
					list.setPartid((Long) result[0]);
					if(result[1] != null) {
						list.setPartnumber((String) result[1]);
					}else {
						list.setPartnumber("");
						
					}
					list.setPartname((String) result[2]);
					list.setPartTypeId((short) result[3]);
					list.setParttype(PartType.getPartTypeName((short) result[3]));
					if(result[4] != null) {
						list.setCategory((String) result[4]);
					}else {
						list.setCategory("");
					}
					if(result[5] != null) {
						list.setMake((String) result[5]);
					}else {
						list.setMake("");
					}
					list.setUnittype((String) result[6]);
					list.setDescription((String) result[7]);
					list.setCreatedOn((Date) result[8]);
					list.setLastupdatedOn((Date) result[9]);
					if(result[11] != null) {
						list.setAutoMasterPart((boolean) result[11]);
						
					}
					list.setWarrantyApplicable((boolean) result[12]);
					list.setWarrantyInMonths((Double) result[13]);
					
					if(result[14] != null && !(((String) result[14]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[14]);
					}
					if(result[15] != null && !(((String) result[15]).trim().equals("")) ) {
						list.setPartname(list.getPartname()+" / "+(String) result[15]);
					}
					Dtos.add(list);
				}
			}
			}
		 return Dtos;

		} catch (Exception e) {
			throw e;
		}
			
	}
	
	@Override
	public List<MasterPartsToVehicleMakerDto> getMasterPartsToVehicleMakerList(Long partId, Integer companyId)
			throws Exception {

		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.vehicleManufacturerId, PL.vehicleManufacturerName"
							+ " FROM MasterPartsToVehicleMaker AS BA "
							+ " INNER JOIN VehicleManufacturer PL ON PL.vehicleManufacturerId = BA.vehicleManufacturerId "
							+ " where BA.partId = "+partId+" AND BA.companyId ="+companyId+" AND  BA.markForDelete = 0",Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<MasterPartsToVehicleMakerDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				MasterPartsToVehicleMakerDto	chilDetailsDto = null;
				for (Object[] result : results) {

					chilDetailsDto = new MasterPartsToVehicleMakerDto();
					
					chilDetailsDto.setVehicleManufacturerId((Long) result[0]);
					chilDetailsDto.setVehicleManufacturer((String) result[1]);
				
					list.add(chilDetailsDto);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<MasterPartsToVehicleModalDto> getMasterPartsToVehicleModalList(Long partId, Integer companyId)
			throws Exception {

		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.vehicleModelId, PL.vehicleModelName"
							+ " FROM MasterPartsToVehicleModal AS BA "
							+ " INNER JOIN VehicleModel PL ON PL.vehicleModelId = BA.vehicleModelId "
							+ " where BA.partId = "+partId+" AND BA.companyId ="+companyId+" AND  BA.markForDelete = 0",Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<MasterPartsToVehicleModalDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				MasterPartsToVehicleModalDto	chilDetailsDto = null;
				for (Object[] result : results) {

					chilDetailsDto = new MasterPartsToVehicleModalDto();
					
					chilDetailsDto.setVehicleModelId((Long) result[0]);
					chilDetailsDto.setVehicleModel((String) result[1]);
				
					list.add(chilDetailsDto);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
}