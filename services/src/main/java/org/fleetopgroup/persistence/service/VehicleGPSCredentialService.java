package org.fleetopgroup.persistence.service;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dao.VehicleGPSCredentialRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.VehicleGPSCredential;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSCredentialService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleGPSCredentialService implements IVehicleGPSCredentialService {

	@Autowired	VehicleGPSCredentialRepository		vehicleGPSCredentialRepository;
	@Autowired	IVehicleService						vehicleService;
	
	@Override
	public ValueObject getVehicleGPSCredentialList(ValueObject valueObject) throws Exception {
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("credentialList", vehicleGPSCredentialRepository.getVehicleGPSCredentialListByCompanyId(userDetails.getCompany_id()));
			valueObject.put("gpsCount", vehicleService.getVehicleAsinedGPSCount(userDetails.getCompany_id()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
		}
	}
	
	@Override
	public ValueObject saveVehicleGPSCredential(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails				= null;
		VehicleGPSCredential			vehicleTollDetails		= null;
		List<VehicleGPSCredential>	validate				= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			validate		= vehicleGPSCredentialRepository.validateVehicleGPSCredential(valueObject.getString("userName"),userDetails.getCompany_id());
			
			if(validate == null || validate.isEmpty()) {
				vehicleTollDetails	= new VehicleGPSCredential();
				
				vehicleTollDetails.setUserName(valueObject.getString("userName"));
				vehicleTollDetails.setPassword(valueObject.getString("password"));
				vehicleTollDetails.setDescription(valueObject.getString("description"));
				vehicleTollDetails.setCompanyId(userDetails.getCompany_id());
				vehicleTollDetails.setCreatedById(userDetails.getId());
				vehicleTollDetails.setCreatedOn(new Date());
				vehicleTollDetails.setLastModifiedById(userDetails.getId());
				vehicleTollDetails.setLastModifiedOn(new Date());
				
				vehicleGPSCredentialRepository.save(vehicleTollDetails);
			}else {
				valueObject.put("alreadyExist", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails				= null;
			vehicleTollDetails		= null;
			validate				= null;
		}
	}
	
	
	@Override
	public ValueObject getVehicleGPSCredentialById(ValueObject valueObject) throws Exception {
		try {
			valueObject.put("vehicleGPSCredential", vehicleGPSCredentialRepository.getVehicleGPSCredentialById(valueObject.getLong("vehicleGPSCredentialId", 0)));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject editVehicleGPSCredential(ValueObject valueObject) throws Exception {
		CustomUserDetails				userDetails				= null;
		VehicleGPSCredential			vehicleTollDetails		= null;
		List<VehicleGPSCredential>		validate				= null;
		
		try {
		
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleTollDetails			= vehicleGPSCredentialRepository.getVehicleGPSCredentialById(valueObject.getLong("vehicleGPSCredentialId", 0));
			
			if(!valueObject.getString("userName").equalsIgnoreCase(vehicleTollDetails.getUserName())) {
				validate		= vehicleGPSCredentialRepository.validateVehicleGPSCredential(valueObject.getString("userName"),userDetails.getCompany_id());
				if(validate != null && !validate.isEmpty()) {
					valueObject.put("alreadyExist", true);
					return valueObject;
				}
			}
			
			vehicleGPSCredentialRepository.updateVehicleGPSCredential(valueObject.getString("userName"), valueObject.getString("password"), valueObject.getString("description"), userDetails.getId(), new Date(), vehicleTollDetails.getVehicleGPSCredentialId());
		
			return valueObject;
		
		} catch (Exception e){
			throw e;
		}
		finally {
			userDetails				= null;
			vehicleTollDetails		= null;
			validate				= null;
		}
		
	}
	
	@Override
	@Transactional
	public ValueObject deleteVehicleGPSCredential(ValueObject valueObject) throws Exception {
		try {
			vehicleGPSCredentialRepository.deleteVehicleGPSCredential(valueObject.getLong("vehicleGPSCredentialId", 0));
			valueObject.put("deleted", true);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}

}
