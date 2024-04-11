
package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.bl.LabourBL;
import org.fleetopgroup.persistence.dao.LabourRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.Labour;
import org.fleetopgroup.persistence.model.TyreExpenses;
import org.fleetopgroup.persistence.model.VehicleModel;
import org.fleetopgroup.persistence.serviceImpl.ILabourService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Service
public class LabourService implements ILabourService{
    
	@Autowired private LabourRepository labourRepository;
	LabourBL  labourBL = new LabourBL();
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public ValueObject getAllLabourMaster(ValueObject valueObject) throws Exception{
		List<Labour>	labourList		= null;
		try {
			labourList = labourRepository.getAllLabourMaster(valueObject.getInt("companyId"));
			valueObject.put("labourList", labourList);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject saveLabour(ValueObject valueObject) throws Exception{
		Labour	validateLabour		= null;
		Labour	labour		= null;
		try {
			validateLabour		=	validateLabour(valueObject.getString("labourName"),valueObject.getInt("companyId"));
			if(validateLabour != null) {
				valueObject.put("alreadyExist", true);
			}else {
				labour = labourBL.prepareLabour(valueObject);
				labourRepository.save(labour);
				valueObject.put("save", true);
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Labour validateLabour(String labourName , int companyId) throws Exception{
		try {
			return labourRepository.findByName(labourName, companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getLabourMaster(ValueObject valueObject) throws Exception{
		try {
		Labour labour =	 labourRepository.getLabourMaster(valueObject.getInt("labourId"), valueObject.getInt("companyId"));
		valueObject.put("labour", labour);	
		return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject updateLabour(ValueObject valueObject) throws Exception {
		Labour 				validateLabour			= null;
		Labour 						labour							= null;
		try {
			labour 				=	 labourRepository.getLabourMaster(valueObject.getInt("labourId"), valueObject.getInt("companyId"));
			
			if(valueObject.getString("labourName").equalsIgnoreCase(labour.getLabourName().trim())) {
				labourRepository.updateLabour(valueObject.getInt("labourId"),labour.getLabourName(), valueObject.getString("description"),valueObject.getInt("companyId"));
			}else {
				validateLabour		= validateLabour(valueObject.getString("labourName"),valueObject.getInt("companyId"));
				if(validateLabour != null) {
					valueObject.put("alreadyExist", true);
				}else {
					labourRepository.updateLabour(valueObject.getInt("labourId"),valueObject.getString("labourName"), valueObject.getString("description"),valueObject.getInt("companyId"));
				}
			}
		
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}	
	
	@Override
	@Transactional
	public ValueObject deleteLabour(ValueObject valueObject) throws Exception {
		try {
			labourRepository.deleteLabourMaster(valueObject.getInt("labourId"), valueObject.getInt("companyId"));
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}	
	
	@Override
	public List<Labour>  getAllLabourMasterByTerm(String search, Integer companyId) throws Exception {
		List<Labour>				labourList				= null;
		Labour 						labour 					= null;
		TypedQuery<Object[]> 		query					= null;
		List<Object[]> 				results					= null;	
		try {
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			query = entityManager.createQuery(
					"SELECT L.labourId, L.labourName FROM Labour AS L "
					+ "WHERE lower(L.labourName) Like ('%" + search + "%') AND L.companyId = "+companyId+" AND L.markForDelete = 0", Object[].class);
			query.setFirstResult(0);
			query.setMaxResults(20);
			
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				labourList = new ArrayList<Labour>();
				
				for (Object[] result : results) {
					labour = new Labour();
					labour.setLabourId((Integer) result[0]);
					labour.setLabourName((String) result[1]);
					labourList.add(labour);
				}
			}
			}
			return labourList;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
}
