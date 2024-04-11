/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.bl.VehicleManufacturerBL;
import org.fleetopgroup.persistence.dao.VehicleManufacturerRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.VehicleManufacturer;
import org.fleetopgroup.persistence.serviceImpl.IVehicleManufacturerService;
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
@Service("VehicleManufacturerService")
@Transactional
public class VehicleManufacturerService implements IVehicleManufacturerService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private VehicleManufacturerRepository 	vehicleManufacturerRepository;
	
	VehicleManufacturerBL	VehicleManufacturerBL 		= new VehicleManufacturerBL();

	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public ValueObject getVehicleManufacturerAutocomplete(String search) throws Exception {
		CustomUserDetails				userDetails						= null;
		List<VehicleManufacturer>		vehicleManufacturerList			= null;
		VehicleManufacturer 			vehicleManufacturer 			= null;
		ValueObject 					valueObject						= null;
		TypedQuery<Object[]> 			query							= null;
		List<Object[]> 					results							= null;	
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject					= new ValueObject();
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			query = entityManager.createQuery(
					"SELECT VM.vehicleManufacturerId, VM.vehicleManufacturerName FROM VehicleManufacturer AS VM "
					+ "WHERE lower(VM.vehicleManufacturerName) Like ('%" + search + "%') AND VM.companyId = "+userDetails.getCompany_id()+" AND VM.markForDelete = 0", Object[].class);
			query.setFirstResult(0);
			query.setMaxResults(20);
			
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				vehicleManufacturerList = new ArrayList<VehicleManufacturer>();
				
				for (Object[] result : results) {
					vehicleManufacturer = new VehicleManufacturer();
					vehicleManufacturer.setVehicleManufacturerId((Long) result[0]);
					vehicleManufacturer.setVehicleManufacturerName((String) result[1]);
					vehicleManufacturerList.add(vehicleManufacturer);
				}
			}
			
			
			valueObject.put("vehicleManufacturerList", vehicleManufacturerList);
			}
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;    
			vehicleManufacturerList			= null;    
			vehicleManufacturer 			= null;    
			valueObject						= null;    
			query							= null;    
			results							= null;	   
		}
	}
	
	@Override
	public ValueObject getAllVehicleManufacturer() throws Exception {
		CustomUserDetails			userDetails						= null;
		List<VehicleManufacturer>	vehicleManufacturerList			= null;
		ValueObject 				valueObject						= null;
		try {
			valueObject					= new ValueObject();
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleManufacturerList 	= vehicleManufacturerRepository.findBycompanyId(userDetails.getCompany_id());
			valueObject.put("allVehicleManufacturer", vehicleManufacturerList);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;     
			vehicleManufacturerList			= null;     
		}
	}
	
	
	@Transactional
	public VehicleManufacturer validateVehicleManufacturer(String vehicleManufacturerName , Integer companyId) throws Exception {
		try {
			return vehicleManufacturerRepository.findByName(vehicleManufacturerName,companyId);
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
		
	}
	
	@Override
	public ValueObject saveVehicleManufacturer(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		VehicleManufacturer			vehicleManufacturer				= null;
		VehicleManufacturer 		validateVehicleManufacturer		= null;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			validateVehicleManufacturer		=	validateVehicleManufacturer(valueObject.getString("vehicleManufacturerName"),userDetails.getCompany_id());
			
			if(validateVehicleManufacturer != null) {
				valueObject.put("alreadyExist", true);
			}else {
				vehicleManufacturer = VehicleManufacturerBL.prepareVehicleManufacturer(valueObject, userDetails);
				vehicleManufacturerRepository.save(vehicleManufacturer);
				valueObject.put("save", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;  
			vehicleManufacturer				= null;  
			validateVehicleManufacturer		= null;  
		}
	}
	
	@Override
	public ValueObject getVehicleManufacturerById(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		VehicleManufacturer			vehicleManufacturer				= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleManufacturer 		= vehicleManufacturerRepository.findByVehicleManufacturerId(valueObject.getLong("vehicleManufacturerId"), userDetails.getCompany_id());
			valueObject.put("vehicleManufacturer", vehicleManufacturer);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;     
			vehicleManufacturer					= null;     
		}
	}
	
	@Override
	@Transactional
	public ValueObject updateVehicleManufacturer(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		VehicleManufacturer 		validateVehicleManufacturer		= null;
		VehicleManufacturer 		vehicleManufacturer				= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleManufacturer			= vehicleManufacturerRepository.findByVehicleManufacturerId(valueObject.getLong("vehicleManufacturerId"),userDetails.getCompany_id());
			
			if(valueObject.getString("vehicleManufacturerName").equalsIgnoreCase(vehicleManufacturer.getVehicleManufacturerName().trim())) {
				vehicleManufacturerRepository.updateVehicleManufacturer(valueObject.getLong("vehicleManufacturerId"),vehicleManufacturer.getVehicleManufacturerName(), valueObject.getString("description"),userDetails.getCompany_id());
			}else {
				validateVehicleManufacturer		= validateVehicleManufacturer(valueObject.getString("vehicleManufacturerName"),userDetails.getCompany_id());
				if(validateVehicleManufacturer != null) {
					valueObject.put("alreadyExist", true);
				}else {
					vehicleManufacturerRepository.updateVehicleManufacturer(valueObject.getLong("vehicleManufacturerId"),valueObject.getString("vehicleManufacturerName"), valueObject.getString("description"),userDetails.getCompany_id());
				}
			}
		
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;   
			validateVehicleManufacturer		= null;   
			vehicleManufacturer				= null;   
		}
	}
	
	@Transactional
	@Override
	public ValueObject deleteVehicleManufacturer(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleManufacturerRepository.deleteVehicleManufacturer(valueObject.getLong("vehicleManufacturerId"),userDetails.getCompany_id());
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;
		}
	}
	
	@Override
	public List<VehicleManufacturer> getAllVehiclManufacturer(Integer companyId) throws Exception {
		return vehicleManufacturerRepository.findBycompanyId(companyId);
	}
	

}
