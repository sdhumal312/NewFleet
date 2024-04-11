package org.fleetopgroup.persistence.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.VehicleBodyMakerRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleBodyMaker;
import org.fleetopgroup.persistence.serviceImpl.IVehicleBodyMakerService;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleBodyMakerService implements IVehicleBodyMakerService {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired VehicleBodyMakerRepository vehicleBodyMakerRepo;
	
	public  List<VehicleBodyMaker>getVehicleBodyMaker(int companyId){
		List<VehicleBodyMaker> 			vehicleBodyMakerList 	= null;
		
		try {
			vehicleBodyMakerList=vehicleBodyMakerRepo.getVehicleBodyMakerByCompanyId(companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vehicleBodyMakerList;

	} 
	
	
	@Override
	public ValueObject validateVehicleBodyMaker(ValueObject object) {
		try {
			String bodyMaker = object.getString("bodyMaker","").trim();
			if(!bodyMaker.equals("")) {
				CustomUserDetails userDetails= Utility.getObject(null);
				VehicleBodyMaker vehicleBodyMaker=getBobyMakerByName(bodyMaker, userDetails.getCompany_id());
				if(vehicleBodyMaker == null) {
					vehicleBodyMaker = new VehicleBodyMaker();
					vehicleBodyMaker.setCompanyId(userDetails.getCompany_id());
					vehicleBodyMaker.setCreatedById(userDetails.getId());
					vehicleBodyMaker.setCreatedOn(new Date());
					vehicleBodyMaker.setVehicleBodyMakerName(bodyMaker);
					vehicleBodyMaker.setLastModifiedOn(new Date());
					vehicleBodyMaker.setLastModifiedById(userDetails.getId());
					vehicleBodyMakerRepo.save(vehicleBodyMaker);
					object.put("saved",true);
				}else {
					object.put("duplicate", true);
					return object;
				}
			}else{
				object.put("invalidName", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return object;
	}
	
	@Transactional(readOnly=true)
	public VehicleBodyMaker getBobyMakerByName(String vehicleBodyMakerName,int companyId) {
		VehicleBodyMaker vehicleBodyMaker  = null;
		try {
			 vehicleBodyMaker = vehicleBodyMakerRepo.validateVehicleBodyMaker(vehicleBodyMakerName, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vehicleBodyMaker;
	}
	
	@Transactional(readOnly=true)
	public VehicleBodyMaker getBobyMakerById(int vehicleBodyMakerId,int companyId) {
		VehicleBodyMaker vehicleBodyMaker  = null;
		try {
			 vehicleBodyMaker = vehicleBodyMakerRepo.getVehicleBodyMakerById(vehicleBodyMakerId, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vehicleBodyMaker;
	}
	
	@Override
	@Transactional
	public ValueObject deleteVehicleBodyMakerById(ValueObject object) {
		try {
			if(validateVehicleHasBodyMaker(object.getInt("id", 0),object.getInt("companyId",0))) {
				object.put("used",true);
				return object;
			}
			int successValue=deleteVehicleBodyMaker(object.getInt("id", 0), object.getInt("companyId",0));
			if(successValue >0) {
				object.put("success",true);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public boolean validateVehicleHasBodyMaker(int vehicleBodyMakerId,int companyId) {
		boolean found = false;
		TypedQuery<Vehicle> query = null;
		List<Vehicle> resultList = null;
		try {
			query=entityManager.createQuery("FROM Vehicle WHERE vehicleBodyMakerId = "+vehicleBodyMakerId+" AND company_Id="+companyId+" AND markForDelete = 0 ", Vehicle.class);
			query.setMaxResults(1);
			resultList=query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(resultList != null && !resultList.isEmpty())
			found =true;
		return found;
	}
	
	@Override
	@Transactional
	public ValueObject updateVehicleBodyMaker(ValueObject object) {
		try {
			CustomUserDetails userDetails = Utility.getObject(null);
			VehicleBodyMaker vehicleBodyMaker = getBobyMakerById(object.getInt("editBodyMakerId", 0), userDetails.getCompany_id());
			if (vehicleBodyMaker != null) {
				int successReturn = vehicleBodyMakerRepo.updateVehicleBodyMaker(
						object.getString("bodyMaker", ""), userDetails.getId(), new Date(),
						object.getInt("editBodyMakerId", 0));
				if (successReturn > 0) {
					object.put("success", true);
				}
			} else {
				object.put("notFound", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	
	@Transactional
	public int deleteVehicleBodyMaker(int vehicleBodyMakerId,int companyId) {
	return vehicleBodyMakerRepo.deleteVehicleBodyMakerById(vehicleBodyMakerId,companyId);
	}
	
	@Override
	public List<VehicleBodyMaker> getVehicleBodyMakerList(String search,int companyId){
		TypedQuery<VehicleBodyMaker> 	query				=	null;
		List<VehicleBodyMaker> 			vehicleBodyMaker 	= 	null;
		try {
			query= entityManager.createQuery("FROM VehicleBodyMaker WHERE vehicleBodyMakerName LIKE '%"+search+"%' AND companyId = "+companyId+"  AND markForDelete = 0 ",VehicleBodyMaker.class);
			vehicleBodyMaker = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(vehicleBodyMaker == null)
			vehicleBodyMaker = new ArrayList<>();
		return vehicleBodyMaker;
	}
}
