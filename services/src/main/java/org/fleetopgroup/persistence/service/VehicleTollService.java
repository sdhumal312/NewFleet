package org.fleetopgroup.persistence.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.VehicleTollDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.VehicleTollDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTollService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleTollService implements IVehicleTollService {

	@Autowired	VehicleTollDetailsRepository	vehicleTollDetailsRepository;
	@Autowired	ICompanyConfigurationService	companyConfigurationService;
	@Autowired	IVehicleService					vehicleService;
	
	@Override
	public ValueObject getVehicleTollDetailsList(ValueObject valueObject) throws Exception {
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("tollList", vehicleTollDetailsRepository.getVehicleTollDetails(userDetails.getCompany_id()));
			valueObject.put("countList", vehicleService.getVehicleAsinedTollCount(userDetails.getCompany_id()));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
		}
	}

	@Override
	public ValueObject saveVehicleToll(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails				= null;
		VehicleTollDetails			vehicleTollDetails		= null;
		List<VehicleTollDetails>	validate				= null;
		HashMap<String, Object> 	configuration			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			validate		= vehicleTollDetailsRepository.validateVehicleTollDetails(userDetails.getCompany_id(), valueObject.getString("customerId"));
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TOLL_API_CONFIG);
			
			if(validate == null || validate.isEmpty()) {
				vehicleTollDetails	= new VehicleTollDetails();
				
				vehicleTollDetails.setCustomerId(valueObject.getString("customerId"));
				vehicleTollDetails.setWalletId(valueObject.getString("walletId"));
				vehicleTollDetails.setDescription(valueObject.getString("description"));
				vehicleTollDetails.setCompanyId(userDetails.getCompany_id());
				vehicleTollDetails.setApiClientId(Long.parseLong(configuration.get("APIClient_ID")+""));
				vehicleTollDetails.setApiKey((String)configuration.get("API_KEY"));
				vehicleTollDetails.setCreatedById(userDetails.getId());
				vehicleTollDetails.setCreatedOn(new Date());
				vehicleTollDetails.setLastUpdatedById(userDetails.getId());
				vehicleTollDetails.setLastUpdatedOn(new Date());
				
				vehicleTollDetailsRepository.save(vehicleTollDetails);
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
			configuration			= null;
		}
	}
	
	@Override
	public ValueObject getVehicleTollById(ValueObject valueObject) throws Exception {
		try {
			valueObject.put("vehicleToll", vehicleTollDetailsRepository.getVehicleTollDetails(valueObject.getLong("vehicleTollDetailsId", 0)));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public ValueObject editVehicleToll(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails				= null;
		VehicleTollDetails			vehicleTollDetails		= null;
		List<VehicleTollDetails>	validate				= null;
		
		try {
		
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleTollDetails			= vehicleTollDetailsRepository.getVehicleTollDetails(valueObject.getLong("vehicleTollDetailsId", 0));
			
			if(!valueObject.getString("customerId").equalsIgnoreCase(vehicleTollDetails.getCustomerId())) {
				validate		= vehicleTollDetailsRepository.validateVehicleTollDetails(userDetails.getCompany_id(), valueObject.getString("customerId"));
				if(validate != null && !validate.isEmpty()) {
					valueObject.put("alreadyExist", true);
					return valueObject;
				}
			}
			vehicleTollDetailsRepository.updateVehicleTollDetails(valueObject.getString("customerId"), valueObject.getString("walletId"), valueObject.getString("description"), userDetails.getId(), new Date(), vehicleTollDetails.getVehicleTollDetailsId());
		
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
	public VehicleTollDetails getVehicleTollByVid(Integer vid) throws Exception {
		
		return vehicleTollDetailsRepository.getVehicleTollDetailsByVid(vid);
	}
	
	@Override
	@Transactional
	public ValueObject getDeleteVehicleToll(ValueObject valueObject) throws Exception {
		try {
			vehicleTollDetailsRepository.deleteVehicleToll(valueObject.getLong("vehicleTollDetailsId"));
			valueObject.put("deleted", true);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
}
