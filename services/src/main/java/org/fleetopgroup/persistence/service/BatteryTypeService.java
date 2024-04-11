package org.fleetopgroup.persistence.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.BatteryTypeRepository;
import org.fleetopgroup.persistence.dto.BatteryTypeDto;
import org.fleetopgroup.persistence.model.BatteryType;
import org.fleetopgroup.persistence.serviceImpl.IBatteryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

@Service
public class BatteryTypeService implements IBatteryTypeService {

	@PersistenceContext
	public EntityManager entityManager;

	@Autowired private BatteryTypeRepository	batteryTypeRepository;
	
	
	@Override
	public List<BatteryType> findAll() {
		
		return batteryTypeRepository.findAll();
	}

	@Override
	public void save(BatteryType batteryType) throws Exception {

		batteryTypeRepository.save(batteryType);
	}
	
	@Override
	public List<BatteryType> validateType(String type, Long manufactureId, String partNumber, Integer warranty) throws Exception {
		
		return batteryTypeRepository.validateType(type, manufactureId, partNumber, warranty);
	}
	
	@Override
	public HashMap<String, Object> getBatteryTypeConfiguration(Integer companyId, Integer filter) throws Exception{
	String	companyFileName	= null;
	String	defaultFileName	= null;
	HashMap<String, Object>	configuration	= null;
	try {

	companyFileName	= getFullFilePath(companyId, filter);
	defaultFileName	= getFullDefaultFilePath( filter);
	configuration	= getBatteryConfiguration(companyFileName,defaultFileName);

	return configuration;
	} catch (FileNotFoundException e) {
	throw e;
	}catch (IOException e) {
	throw e;
	}catch (Exception e) {
	throw e;
	}finally {
	companyFileName	= null;
	defaultFileName	= null;
	}
	}
	
	
	public String getFullFilePath(Integer companyId, Integer filter) throws Exception{
		String filepath	= null;
		String fileName	= null;
		try {
		filepath = getFilePath(filter);
		fileName = filepath +companyId+PropertyFileConstants.PROPERTIES_EXTENSION;
		return fileName;
		}catch (Exception e) {
		throw e;
		}finally {
		filepath	= null;	
		fileName	= null;
		}
		}

		public String getFullDefaultFilePath(Integer filter) throws Exception{
		String filepath	= null;
		String fileName	= null;
		try {
		filepath = getFilePath(filter);
		fileName = filepath +PropertyFileConstants.DEFAULT+PropertyFileConstants.PROPERTIES_EXTENSION;
		return fileName;
		}catch (Exception e) {
		throw e;
		}finally {
		filepath	= null;	
		fileName	= null;
		}
		}

		public String getFilePath(int filter) throws Exception {
		String path = null;
		switch (filter) {

		case PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG:
		path = PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG_PATH;
		break;
		case PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG:
		path = PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG_PATH;
		break;
		case PropertyFileConstants.FUEL_CONFIGURATION_CONFIG:
		path = PropertyFileConstants.FUEL_CONFIGURATION_CONFIG_PATH;
		break;
		case PropertyFileConstants.PENALTY_CONFIGURATION_CONFIG:
		path = PropertyFileConstants.PENALTY_CONFIGURATION_CONFIG_PATH;
		break;
		case PropertyFileConstants.MASTER_CONFIGURATION_CONFIG:
		path = PropertyFileConstants.MASTER_CONFIGURATION_CONFIG_PATH;
		break;	
		case PropertyFileConstants.CALENDER_BREAK_DOWN_CONFIGURATION_CONFIG:
		path = PropertyFileConstants.CALENDER_BREAK_DOWN_CONFIGURATION_CONFIG_PATH;
		break;
		case PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG:
		path = PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG_PATH;
		break;
		case PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG:
		path = PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG_PATH;
		break;
		case PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG:
		path = PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG_PATH;
		break;
		}
		return path;
		}

		
		public HashMap<String, Object> getBatteryConfiguration(String batteryType, String defaultFileName) throws Exception{
			HashMap<String, Object> configuration	= null;
			HashMap<String, Object> defaultConfiguration	= null;
			HashMap<String, Object> companyConfiguration	= null;
			try {

			defaultConfiguration	= loadConfiguration(defaultFileName);
			companyConfiguration	= loadConfiguration(batteryType);
			if(companyConfiguration != null) { 
			configuration	= getFinalConfiguration(companyConfiguration, defaultConfiguration);
			}else {
			return defaultConfiguration;
			}
			return  configuration;
			} catch (Exception e) {
			throw e;
			}finally {
			defaultConfiguration	= null;
			companyConfiguration	= null;
			configuration	= null;
			}
			}
		
		
		@SuppressWarnings("unchecked")
		public HashMap<String, Object> loadConfiguration(String filename) throws Exception{
		Yaml	yaml	= null;
		HashMap<String, Object> data	= null;
		try {
		yaml	= new Yaml();
		 java.io.InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
		  if(in != null) {
		data = (HashMap<String, Object>) yaml.load(in);
		  }
		return  data;
		} catch (Exception e) {
		throw e;
		}finally {
		yaml	= null;
		data	= null;
		}
		}

		public HashMap<String, Object> getFinalConfiguration(HashMap<String, Object> companyConfiguration, HashMap<String, Object> defaultConfiguration) throws Exception{

		try {
		HashMap<String, Object> configuration	= new HashMap<>();

		defaultConfiguration.forEach((key, value) -> {
		 if(companyConfiguration.get(key) == null) {
		   	configuration.put(key, value);
		 }else {
		 configuration.put(key, companyConfiguration.get(key));
		 }
		   
		});
		return  configuration;
		} catch (Exception e) {
		throw e;
		}finally {

		}

		}

	@Override
	public List<BatteryTypeDto> findAllBatteryType(Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT BT.batteryTypeId, BT.batteryType, BT.description, BM.manufacturerName, BT.partNumber, BT.warrantyPeriod, BT.warrantyTypeId, BT.warrentyterm,"
					+ " VCF.vehicleCostFixingId, VCF.costPerDay "
						+ " FROM BatteryType AS BT "
						+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BT.batteryManufacturerId "
						+ " LEFT JOIN VehicleCostFixing VCF ON VCF.batteryTypeId = BT.batteryTypeId AND VCF.companyId = "+companyId+" "
						+ " where BT.markForDelete = 0 order by BT.batteryTypeId desc ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<BatteryTypeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<BatteryTypeDto>();
			BatteryTypeDto batteryType = null;
			for (Object[] result : results) {
				batteryType = new BatteryTypeDto();
				
				batteryType.setBatteryTypeId((Long) result[0]);
				batteryType.setBatteryType((String) result[1]);
				batteryType.setDescription((String) result[2]);
				batteryType.setManufacturerName((String) result[3]);
				batteryType.setPartNumber((String) result[4]);
				batteryType.setWarrantyPeriod((Integer) result[5]);
				batteryType.setWarrantyTypeId((short) result[6]);
				batteryType.setWarrentyterm((String) result[7]);
				
				batteryType.setWarrantyType(BatteryTypeDto.getWarrantyTypeName((short) result[6]));
				
				batteryType.setVehicleCostFixingId((Long) result[8]);
				batteryType.setCostPerDay((Double) result[9]);
				
				if(batteryType.getVehicleCostFixingId() == null) {
					batteryType.setVehicleCostFixingId((long) 0);
					batteryType.setCostPerDay((double) 0);
						
				}
				
				Dtos.add(batteryType);
			}
		}
		return Dtos;
	}
	
	@Override
	public BatteryType getBatteryTypeByID(Long batteryTypeId) {

		return batteryTypeRepository.getbatteryTypeByID(batteryTypeId);
	}
@Override
public BatteryTypeDto getBatteryTypeDto(Long batteryTypeId) throws Exception{
	try {

		javax.persistence.Query query = entityManager.createQuery("SELECT BT.batteryTypeId, BT.batteryType, BT.description, BM.manufacturerName, BT.partNumber, BT.warrantyPeriod, BT.warrantyTypeId,"
				+ " BT.batteryManufacturerId, BT.warrentyterm "
				+ " FROM BatteryType AS BT "
				+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BT.batteryManufacturerId"
				+ " where BT.batteryTypeId = "+batteryTypeId+" AND BT.markForDelete = 0 ");
		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		BatteryTypeDto	batteryType = null;
		
			if (result != null) {
				batteryType = new BatteryTypeDto();
				
				batteryType.setBatteryTypeId((Long) result[0]);
				batteryType.setBatteryType((String) result[1]);
				batteryType.setDescription((String) result[2]);
				batteryType.setManufacturerName((String) result[3]);
				batteryType.setPartNumber((String) result[4]);
				batteryType.setWarrantyPeriod((Integer) result[5]);
				batteryType.setWarrantyTypeId((short) result[6]);
				
				batteryType.setWarrantyType(BatteryTypeDto.getWarrantyTypeName((short) result[6]));
				batteryType.setBatteryManufacturerId((long)result[7]);
				batteryType.setWarrentyterm((String)result[8]);;
				
			}
		
		return batteryType;
	} catch (Exception e) {
		throw e;
	}
}
	@Override
	public List<BatteryType> getBatteryType(Long manufactureId) throws Exception {
		try {
			TypedQuery<BatteryType> queryt = entityManager.createQuery(
						"SELECT BT FROM BatteryType AS BT where BT.batteryManufacturerId = "+manufactureId+" AND BT.markForDelete = 0 ",
						BatteryType.class);
			
			return queryt.getResultList();
			
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<BatteryType> validateBatteryType(String batteryType) throws Exception {

		return batteryTypeRepository.validateBatteryType(batteryType);
	}
}
