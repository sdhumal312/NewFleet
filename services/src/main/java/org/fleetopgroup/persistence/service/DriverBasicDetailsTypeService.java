/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.bl.DriverBasicDetailsTypeBL;
import org.fleetopgroup.persistence.dao.DriverBasicDetailsTypeRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.DriverBasicDetails;
import org.fleetopgroup.persistence.model.DriverBasicDetailsType;
import org.fleetopgroup.persistence.serviceImpl.IDriverBasicDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IDriverBasicDetailsTypeService;
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

@Service("DriverBasicDetailsTypeService")
@Transactional
public class DriverBasicDetailsTypeService implements IDriverBasicDetailsTypeService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DriverBasicDetailsTypeRepository 		driverBasicDetailsTypeRepository;
	@Autowired IDriverBasicDetailsService   driverBasicDetailsService;
	SimpleDateFormat dateFormat 	= new SimpleDateFormat("dd-MM-yyyy");
	
	DriverBasicDetailsTypeBL	driverBasicDetailsTypeBL 		= new DriverBasicDetailsTypeBL();

	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public ValueObject getDriverAllBasicDetailsTypeList() throws Exception {
		CustomUserDetails			userDetails						= null;
		List<DriverBasicDetailsType>			DriverBasicDetailsTypeList				= null;
		ValueObject 				valueObject						= null;
		try {
			valueObject					= new ValueObject();
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			DriverBasicDetailsTypeList 				= driverBasicDetailsTypeRepository.findBycompanyId(userDetails.getCompany_id());
			valueObject.put("DriverBasicDetailsTypeList", DriverBasicDetailsTypeList);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;     
			DriverBasicDetailsTypeList				= null;     
		}
	}
	
	
	@Override
	public DriverBasicDetailsType validateDriverBasicDetailsType(String detailType , Integer companyId) throws Exception {
		try {
			return driverBasicDetailsTypeRepository.findByDetailTypeName(detailType,companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public ValueObject saveDriverBasicDetailsType(ValueObject valueObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		DriverBasicDetailsType				validateDriverBasicDetailsType					= null;
		DriverBasicDetailsType 				driverBasicDetailsType			= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			validateDriverBasicDetailsType		=	validateDriverBasicDetailsType(valueObject.getString("driverBasicDetailsTypeName"),userDetails.getCompany_id());
			
			if(validateDriverBasicDetailsType != null) {
				valueObject.put("alreadyExist", true);
			}else {
				driverBasicDetailsType = driverBasicDetailsTypeBL.prepareDriverBasicDetailsType(valueObject, userDetails);
				driverBasicDetailsTypeRepository.save(driverBasicDetailsType);
				valueObject.put("save", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;  
			driverBasicDetailsType					= null;  
			validateDriverBasicDetailsType			= null;  
		}
	}
	
	@Override
	public ValueObject getDriverBasicDetailsType(ValueObject valueInObject) throws Exception {
		DriverBasicDetailsType  driverBasicDetailsType = null;
		CustomUserDetails			userDetails						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverBasicDetailsType 		= driverBasicDetailsTypeRepository.getDriverBasicDetailsTypeById(valueInObject.getLong("driverBasicDetailsTypeId"),userDetails.getCompany_id());
			valueInObject.put("driverBasicDetailsType", driverBasicDetailsType);
			return valueInObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	@Override
	public ValueObject updateDriverBasicDetailsType(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		DriverBasicDetailsType				validateDriverBasicDetailsType					= null;
		DriverBasicDetailsType 				driverBasicDetailsType			= null;
		ValueObject							object								= null;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			object				= getDriverBasicDetailsType(valueObject);
			driverBasicDetailsType = (DriverBasicDetailsType) object.get("driverBasicDetailsType");
			validateDriverBasicDetailsType		=	validateDriverBasicDetailsType(valueObject.getString("driverBasicDetailsTypeName"),userDetails.getCompany_id());
			
			if(validateDriverBasicDetailsType != null && !driverBasicDetailsType.getDriverBasicDetailsTypeName().equalsIgnoreCase(valueObject.getString("driverBasicDetailsTypeName"))) {
				valueObject.put("alreadyExist", true);
			}else {
				driverBasicDetailsType = driverBasicDetailsTypeBL.prepareDriverBasicDetailsType(valueObject, userDetails);
				driverBasicDetailsTypeRepository.save(driverBasicDetailsType);
				valueObject.put("update", true);
			}
		
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;   
		}
	}
	
	@Transactional
	@Override
	public ValueObject deleteDriverBasicDetailsType(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		List<DriverBasicDetails> 	driverBasicDetailsList			= null; 
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverBasicDetailsList = driverBasicDetailsService.getDriverBasicDetailListByTypeId(valueObject);
			if(driverBasicDetailsList != null && !driverBasicDetailsList.isEmpty()) {
				valueObject.put("alreadyExistInDriver", true);
			}else {
				driverBasicDetailsTypeRepository.deleteDriverBasicDetailsType(valueObject.getLong("driverBasicDetailsTypeId"),userDetails.getCompany_id());
			}
			
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails						= null;
		}
	}
	

}
