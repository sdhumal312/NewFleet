/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.MastersConfigurationConstants;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.VehicleModelBL;
import org.fleetopgroup.persistence.dao.VehicleModelRepository;
import org.fleetopgroup.persistence.dao.VehicleRepository;
import org.fleetopgroup.persistence.dao.VehicleTyreLayoutPositionRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleModelDto;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleModel;
import org.fleetopgroup.persistence.model.VehicleTyreLayoutPosition;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleModelService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleModelTyreLayoutService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("VehicleModelService")
@Transactional
public class VehicleModelService implements IVehicleModelService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	private VehicleModelRepository 			vehicleModelRepository;
	@Autowired	private VehicleRepository 							vehicleDao;
	@Autowired	private VehicleTyreLayoutPositionRepository 		vehicleTyreLayoutPositionRepository;
	@Autowired	private IVehicleModelTyreLayoutService 		vehicleModelTyreLayoutService;
	
	@Autowired ICompanyConfigurationService		companyConfigurationService;
	@Autowired	private IVehicleService		vehicleService;
	
	VehicleModelBL	VehicleModelBL 		= new VehicleModelBL();

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public ValueObject getVehicleModelAutocomplete(String search) throws Exception {
		CustomUserDetails			userDetails						= null;
		List<VehicleModel>			vehicleModelList				= null;
		VehicleModel 				vehicleModel 					= null;
		ValueObject 				valueObject						= null;
		TypedQuery<Object[]> 		query							= null;
		List<Object[]> 				results							= null;	
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject					= new ValueObject();
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			query = entityManager.createQuery(
					"SELECT VM.vehicleModelId, VM.vehicleModelName FROM VehicleModel AS VM "
					+ "WHERE lower(VM.vehicleModelName) Like ('%" + search + "%') AND VM.companyId = "+userDetails.getCompany_id()+" AND VM.markForDelete = 0", Object[].class);
			query.setFirstResult(0);
			query.setMaxResults(20);
			
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				vehicleModelList = new ArrayList<VehicleModel>();
				
				for (Object[] result : results) {
					vehicleModel = new VehicleModel();
					vehicleModel.setVehicleModelId((Long) result[0]);
					vehicleModel.setVehicleModelName((String) result[1]);
					vehicleModelList.add(vehicleModel);
				}
			}
			
			
			valueObject.put("vehicleModelList", vehicleModelList);
			}
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;    
			vehicleModelList				= null;    
			vehicleModel 					= null;    
			valueObject						= null;    
			query							= null;    
			results							= null;	   
		}
	}
	
	@Override
	public ValueObject getAllVehicleModel() throws Exception {
		CustomUserDetails			userDetails						= null;
		List<VehicleModelDto>		vehicleModelList				= null;
		ValueObject 				valueObject						= null;
		try {
			valueObject					= new ValueObject();
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleModelList 				= getVehicleModelListByCompanyId(userDetails.getCompany_id());
			valueObject.put("allVehicleModel", vehicleModelList);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;     
			vehicleModelList				= null;     
		}
	}
	
	@Transactional
	public VehicleModel validateVehicleModel(String vehicleModelName , Integer companyId) throws Exception {
		try {
			return vehicleModelRepository.findByName(vehicleModelName,companyId);
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
		
	}
	
	@Override
	public ValueObject saveVehicleModel(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		VehicleModel				VehicleModel					= null;
		VehicleModel 				validateVehicleModel			= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			validateVehicleModel		=	validateVehicleModel(valueObject.getString("vehicleModelName"),userDetails.getCompany_id());
			
			if(validateVehicleModel != null) {
				valueObject.put("alreadyExist", true);
			}else {
				VehicleModel = VehicleModelBL.prepareVehicleModel(valueObject, userDetails);
				vehicleModelRepository.save(VehicleModel);
				valueObject.put("save", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;  
			VehicleModel					= null;  
			validateVehicleModel			= null;  
		}
	}
	
	@Override
	public ValueObject getVehicleModelById(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		VehicleModel				vehicleModel					= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleModel 				= vehicleModelRepository.findByVehicleModelId(valueObject.getLong("vehicleModelId"), userDetails.getCompany_id());
			valueObject.put("vehicleModel", vehicleModel);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;     
			vehicleModel					= null;     
		}
	}
	
	@Override
	@Transactional
	public ValueObject updateVehicleModel(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		VehicleModel 				validateVehicleModel			= null;
		VehicleModel 				vehicleModel					= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleModel				= vehicleModelRepository.findByVehicleModelId(valueObject.getLong("vehicleModelId"),userDetails.getCompany_id());
			
			if(valueObject.getString("vehicleModelName").equalsIgnoreCase(vehicleModel.getVehicleModelName().trim())) {
				vehicleModelRepository.updateVehicleModel(valueObject.getLong("vehicleModelId"),vehicleModel.getVehicleModelName(), 
										valueObject.getString("description"), userDetails.getCompany_id(), valueObject.getLong("manufacturer", 0));
			}else {
				validateVehicleModel		= validateVehicleModel(valueObject.getString("vehicleModelName"),userDetails.getCompany_id());
				if(validateVehicleModel != null) {
					valueObject.put("alreadyExist", true);
				}else {
					vehicleModelRepository.updateVehicleModel(valueObject.getLong("vehicleModelId"),valueObject.getString("vehicleModelName"), 
							valueObject.getString("description"),userDetails.getCompany_id(), valueObject.getLong("manufacturer", 0));
				}
			}
		
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;   
			validateVehicleModel			= null;   
			vehicleModel					= null;   
		}
	}
	
	@Transactional
	@Override
	public ValueObject deleteVehicleModel(ValueObject valueObject) throws Exception {
		CustomUserDetails							userDetails						= null;
		List<Vehicle>								vehicleList						= null;
		List<VehicleTyreLayoutPosition>				vehicleTyreLayoutPositionList	= null;
		Long										assignCount						= null;
		Long										validateVehicleModel						= null;
		HashMap<String, Object>  	configuration			= null;       
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			vehicleList					= new ArrayList<>();
			StringBuffer vids 			= new StringBuffer();
			
			validateVehicleModel = vehicleService.checkVehicleByVehicleModel(valueObject.getLong("vehicleModelId",0),valueObject.getInt("companyId"));
			
			
			if(validateVehicleModel != null && validateVehicleModel > 0 && (boolean)configuration.getOrDefault(MastersConfigurationConstants.VALIDATE_VEHICLE_MODEL,true)) {
				valueObject.put("vehicleExist", true);
				return valueObject;
			}
			
			assignCount = vehicleModelTyreLayoutService.checkAssignTyre(valueObject);
			if(assignCount != null && assignCount > 0) {
				valueObject.put("tyreExist", true);
				return valueObject;
			}
			vehicleList = vehicleDao.getvehicleByModelId(valueObject.getLong("vehicleModelId",0),valueObject.getInt("companyId"));
			if(vehicleList != null && !vehicleList.isEmpty()) {
				for(int i = 0; i < vehicleList.size(); i++) {
					if (i < vehicleList.size() - 1) {
						vids = vids.append(vehicleList.get(i).getVid() + ",");
					} else {
						vids = vids.append(vehicleList.get(i).getVid());
					}

				}
				
			entityManager.createQuery("UPDATE VehicleTyreLayoutPosition SET markForDelete = 1 WHERE VEHICLE_ID IN ("+vids+")  AND COMPANY_ID = "+valueObject.getInt("companyId")+"").executeUpdate();
			}
			vehicleModelRepository.deleteVehicleModel(valueObject.getLong("vehicleModelId"),userDetails.getCompany_id());
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;
		}
	}
	
	@Override
	public List<VehicleModelDto> getVehicleModelListByCompanyId(Integer companyId) throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.vehicleModelId, BA.vehicleModelName, BA.description, "
							+ " PL.vehicleManufacturerName, PL.vehicleManufacturerId, VTL.vehicleModelTyreLayoutId "
							+ " FROM VehicleModel AS BA "
							+ " LEFT JOIN VehicleManufacturer PL ON PL.vehicleManufacturerId = BA.vehicleManufacturerId "
							+ " LEFT JOIN VehicleModelTyreLayout VTL ON VTL.vehicleModelId = BA.vehicleModelId AND VTL.markForDelete = 0 "
							+ " where BA.companyId = "+companyId+" AND  BA.markForDelete = 0 ", Object[].class);
			
			List<Object[]>	results = queryt.getResultList();
			
			List<VehicleModelDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				VehicleModelDto	batteryInvoice = null;
				for (Object[] result : results) {
					batteryInvoice = new VehicleModelDto();
					
					batteryInvoice.setVehicleModelId((Long) result[0]);
					batteryInvoice.setVehicleModelName((String) result[1]);
					batteryInvoice.setDescription((String) result[2]);
					batteryInvoice.setVehicleManufacturer((String) result[3]);
					batteryInvoice.setVehicleManufacturerId((Long) result[4]);
					
					if(batteryInvoice.getVehicleManufacturer() == null) {
						batteryInvoice.setVehicleManufacturer("--");
					}
					if(result[5] != null) {
						batteryInvoice.setVehicleModelTyreLayoutId((Long) result[5]);
					}
					
					list.add(batteryInvoice);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public List<VehicleModelDto> getVehicleModelListByCompanyIdAndManufacturer(Integer companyId,
			String query) throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.vehicleModelId, BA.vehicleModelName, BA.description, "
							+ " PL.vehicleManufacturerName, PL.vehicleManufacturerId"
							+ " FROM VehicleModel AS BA "
							+ " LEFT JOIN VehicleManufacturer PL ON PL.vehicleManufacturerId = BA.vehicleManufacturerId "
							+ " where BA.companyId = "+companyId+"  AND  BA.markForDelete = 0 "+query+" ", Object[].class);
			
			List<Object[]>	results = queryt.getResultList();
			
			List<VehicleModelDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				VehicleModelDto	batteryInvoice = null;
				for (Object[] result : results) {
					batteryInvoice = new VehicleModelDto();
					
					batteryInvoice.setVehicleModelId((Long) result[0]);
					batteryInvoice.setVehicleModelName((String) result[1]);
					batteryInvoice.setDescription((String) result[2]);
					batteryInvoice.setVehicleManufacturer((String) result[3]);
					batteryInvoice.setVehicleManufacturerId((Long) result[4]);
					
					if(batteryInvoice.getVehicleManufacturer() == null) {
						batteryInvoice.setVehicleManufacturer("--");
					}
					
					list.add(batteryInvoice);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
		
	}

}
