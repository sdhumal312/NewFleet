package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehicleFuelPermissionRepository;
import org.fleetopgroup.persistence.dao.VehicleFuelRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleFuelDto;
import org.fleetopgroup.persistence.model.VehicleFuel;
import org.fleetopgroup.persistence.model.VehicleFuelPermission;
import org.fleetopgroup.persistence.serviceImpl.IVehicleFuelService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehicleFuelService implements IVehicleFuelService {
    @Autowired
    private VehicleFuelRepository vehicleFuelrepository;
    
    @Autowired
    private VehicleFuelPermissionRepository	vehicleFuelPermissionRepository;
    
    @PersistenceContext
	EntityManager entityManager;

    // API

    @Override
    public VehicleFuel registerNewVehicleFuel(final VehicleFuelDto accountDto) throws Exception {
        
        final VehicleFuel VehicleFuel = new VehicleFuel();

        VehicleFuel.setvFuel(accountDto.getvFuel());
       
        return vehicleFuelrepository.save(VehicleFuel);
    }
    
    @Override
    public VehicleFuelPermission addNewVehicleFuel(VehicleFuelPermission fuelPermission) throws Exception {
    	
    	return vehicleFuelPermissionRepository.save(fuelPermission);
    }

    @Override
	public void updateVehicleFuel(String vFuel, long fid) throws Exception {
     
        vehicleFuelrepository.updateVehicleFuel(vFuel, fid);
    }


    @Override
	public List<VehicleFuel> findAll() {
    	CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return vehicleFuelrepository.findAllByPermission(userDetails.getCompany_id());
	}
    
    @Override
    public List<VehicleFuel> findAllVehicleFuel() {
    	
    	return vehicleFuelrepository.findAll();
    }
    
	@Override
	public VehicleFuel getVehicleFuel(String verificationToken) {
		return null;
	}


	@Override
	public void deleteVehicleFuel(long fid) {
		
		vehicleFuelrepository.deleteVehicleFuel(fid);
	}

	@Override
	public void deleteVehicleFuelPermission(long fid, Integer companyId) {

		vehicleFuelPermissionRepository.deleteVehicleFuel(fid, companyId);
	}


	@Override
	public VehicleFuel getVehicleFuelByID(long fid) {
		
		return vehicleFuelrepository.getVehicleFuelByID(fid);
	}

	@Override
	public VehicleFuelPermission getVehicleFuelPermissionByID(long fid, Integer companyId) {
		
		return vehicleFuelPermissionRepository.getVehicleFuelByID(fid, companyId);
	}
	
	@Override
	public long count() {
		
		return vehicleFuelrepository.count();
	}

	@Override
	public VehicleFuel findByVFuel(String vFuel) {
		return vehicleFuelrepository.findByVFuel(vFuel);
	}
	
	@Override
	public List<VehicleFuel> getFuelTypeListDropDown(String term, Integer companyId) throws Exception {
		try {	
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			TypedQuery<VehicleFuel> queryt = entityManager
					.createQuery(" SELECT VF FROM VehicleFuel VF "
					+" INNER JOIN VehicleFuelPermission VFP ON VFP.fid = VF.fid "	
					+" WHERE lower(VF.vFuel) Like ('%"+ term + "%') AND VFP.companyId = "+companyId+" AND "		
					+" VFP.markForDelete = 0 AND VF.markForDelete = 0  ", VehicleFuel.class);
			return queryt.getResultList();
			}else {
				return null;
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getFuelTypeNameWiseList(ValueObject object) throws Exception {
		List<VehicleFuel>      fuelTypesList 				= null;
		List<VehicleFuel>      fuelTypes					= null;
		try {

			fuelTypesList 	= new ArrayList<VehicleFuel>();
			fuelTypes 		= getFuelTypeListDropDown(object.getString("term"), object.getInt("companyId"));
			
			if(fuelTypes != null && !fuelTypes.isEmpty()) {
				for(VehicleFuel load : fuelTypes) {
					fuelTypesList.add(load);
				}
			}

			object.put("fuelTypesList", fuelTypesList);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			fuelTypes 		 = null;
			fuelTypesList 	 = null;
			object  	 	 = null;
		}
	}


}