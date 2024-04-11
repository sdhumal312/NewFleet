package org.fleetopgroup.persistence.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehicleStatusPermissionRepository;
import org.fleetopgroup.persistence.dao.VehicleStatusRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.VehicleStatus;
import org.fleetopgroup.persistence.model.VehicleStatusPermission;
import org.fleetopgroup.persistence.serviceImpl.IVehicleStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehicleStatusService implements IVehicleStatusService {
    @Autowired
    private VehicleStatusRepository vehicleStatusrepository;
    
    @Autowired 
    private VehicleStatusPermissionRepository vehicleStatusPermissionRepository;


    // API

    @Override
    public VehicleStatus registerNewVehicleStatus(final VehicleStatus accountDto) throws Exception {
           
        return vehicleStatusrepository.save(accountDto);
    }
    
    @Override
    public VehicleStatusPermission addNewVehicleStatusPermission(VehicleStatusPermission permission) throws Exception {
    	
    	return vehicleStatusPermissionRepository.save(permission);
    }

    @Override
	public void updateVehicleStatus(String vStatus, String updateBy, Date updateDate, long sid) throws Exception {
     
        vehicleStatusrepository.updateVehicleStatus(vStatus, updateBy,updateDate,  sid);
    }

   
    @Override
	public List<VehicleStatus> findAll() {
    	CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return vehicleStatusrepository.findAll(userDetails.getCompany_id());
	}
    
    
    @Override
    public List<VehicleStatus> findAllVehicleStatus() {
    	
    	return vehicleStatusrepository.findAll();
    }
    
	@Override
	public VehicleStatus getVehicleStatus(String verificationToken) {
		return null;
	}


	@Override
	public void saveRegisteredVehicleStatus(VehicleStatus VehicleStatus) {
		
	}


	@Override
	public void deleteVehicleStatus(long sid) {
		
		vehicleStatusrepository.deleteVehicleStatus(sid);
	}

	@Override
	public void deleteVehicleStatusPermission(long sid, Integer companyId) {
		 vehicleStatusPermissionRepository.deleteVehicleStatus(sid, companyId);
		
	}


	@Override
	public VehicleStatus getVehicleStatusByID(long sid) {
		
		return vehicleStatusrepository.getVehicleStatusByID(sid);
	}
	
	@Override
	public VehicleStatusPermission getVehicleStatusPermissionByID(long sid, Integer companyId) {
		return vehicleStatusPermissionRepository.getVehicleStatusByID(sid, companyId);
	}

	@Override
	public long count() {
		
		return vehicleStatusrepository.count();
	}

	@Override
	public VehicleStatus findByVStatus(String vStatus) throws Exception {
		
		return vehicleStatusrepository.findByVStatus(vStatus);
	}

	


}