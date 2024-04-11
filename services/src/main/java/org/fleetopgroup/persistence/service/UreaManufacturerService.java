package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.fleetopgroup.persistence.bl.UreaInventoryBL;
import org.fleetopgroup.persistence.dao.UreaManufacturerRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.UreaManufacturer;
import org.fleetopgroup.persistence.serviceImpl.IUreaManufacturerService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UreaManufacturerService implements IUreaManufacturerService {

	@PersistenceContext EntityManager entityManager;
	
	UreaInventoryBL ureaTypeMasterBL = new UreaInventoryBL();
	
	@Autowired UreaManufacturerRepository ureaManufacturerRepository;
	
	@Override
	public List<UreaManufacturer> getUreaManufacturerList(String term, Integer companyId) throws Exception {
		try {
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			TypedQuery<UreaManufacturer> queryt = entityManager
					.createQuery("select BM from UreaManufacturer AS BM where lower(BM.manufacturerName) Like ('%"+ term + "%')"
					+ " AND BM.companyId = "+companyId+" AND  BM.markForDelete = 0 ", UreaManufacturer.class);
			return queryt.getResultList();
			}else {
				return null;
			}
			
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ValueObject saveUreaTypes(ValueObject valueObject) throws Exception {
		
		CustomUserDetails			userDetails		= null;		
		List<UreaManufacturer> 		ureaTypeList	= null;
		List<UreaManufacturer> 		validate		= null;
		
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valueObject.put("userDetails", userDetails);			
			
			UreaManufacturer  ureaTypes  = ureaTypeMasterBL.getUreaTypesModel(valueObject);
			
			validate				     = validateUreaTypes(ureaTypes.getManufacturerName(),userDetails.getCompany_id());			
			
			
			if(validate != null && !validate.isEmpty()) {
				valueObject.put("alreadyExist", true);
				return valueObject;
			}		
			
			ureaManufacturerRepository.save(ureaTypes);
			
			valueObject.put("saved", true);
			int count	= 0;	
			
			ureaTypeList	= ureaManufacturerRepository.getUreaTypesListByCompanyId(userDetails.getCompany_id());
			
					
			if(ureaTypeList != null && !ureaTypeList.isEmpty())
				count	= ureaTypeList.size();
			valueObject.put("ureaTypeList", ureaTypeList);
			
			valueObject.put("count", count);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;			
			ureaTypeList	= null;
		}
	}

	@Override
	public List<UreaManufacturer> validateUreaTypes(String ureaTypeName, Integer companyId) throws Exception {
		
		return ureaManufacturerRepository.validateUreaTypes(ureaTypeName, companyId);
		
	}
	
	@Override
	public ValueObject getUreaTypesList(ValueObject valueObject) throws Exception {
		
		CustomUserDetails			userDetails			= null;		
		List<UreaManufacturer> 		ureaTypeList		= null;
		
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			int count	= 0;
			
			ureaTypeList	=	ureaManufacturerRepository.getUreaTypesListByCompanyId(userDetails.getCompany_id());
			if(ureaTypeList != null && !ureaTypeList.isEmpty())
				count	= ureaTypeList.size();
			valueObject.put("ureaTypeList", ureaTypeList);
			
			valueObject.put("count", count);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
			ureaTypeList	= null;
		}
	}
	
	@Override
	public ValueObject getUreaTypesById(ValueObject valueObject) throws Exception {
		try {
			UreaManufacturer	preUreaTypes	=	ureaManufacturerRepository.findById(valueObject.getLong("ureaManufacturerId")).get();
			valueObject.put("ureaTypes", preUreaTypes);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	@Override
	public ValueObject editUreaTypes(ValueObject valueObject) throws Exception {
		CustomUserDetails			    userDetails		= null;
		List<UreaManufacturer> 			ureaTypeList	= null;
		List<UreaManufacturer> 			validate		= null;
		
		long ureaManufacturerId							= 0;
		String manufacturerName							= null;
		Timestamp	lastUpdatedDate						= null;
		long lastModifiedId								= 0;
		String description								= null;
		
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			valueObject.put("userDetails", userDetails);		
			
			ureaManufacturerId = valueObject.getLong("ureaManufacturerId");
			manufacturerName   = valueObject.getString("ureaTypeName").trim();
			lastUpdatedDate    = DateTimeUtility.getCurrentTimeStamp();
			lastModifiedId 	   = userDetails.getId();
			description        = valueObject.getString("description");
			
			entityManager.createQuery(
					" UPDATE UreaManufacturer AS U SET U.manufacturerName = '"+manufacturerName+"', U.description = '"+ description +"' , "
					+ " U.lastModifiedById = '"+lastModifiedId+"', U.lastModified = '"+lastUpdatedDate+"' "
					+ " WHERE U.ureaManufacturerId = " + ureaManufacturerId + " AND U.companyId = "+userDetails.getCompany_id()
					+ " AND U.markForDelete=0  ")
					.executeUpdate();
			
				
			UreaManufacturer	preUreaTypes	=	ureaManufacturerRepository.findById(ureaManufacturerId).get();
			
			if(!manufacturerName.equalsIgnoreCase(preUreaTypes.getManufacturerName().trim())) {
			validate	=	validateUreaTypes(manufacturerName, userDetails.getCompany_id());
			}	
			
			if(validate != null && !validate.isEmpty()) {
				valueObject.put("alreadyExist", true);
				return valueObject;
			}			
			
			
			valueObject.put("saved", true);
			int count		= 0;		
			
			ureaTypeList	=	ureaManufacturerRepository.getUreaTypesListByCompanyId(userDetails.getCompany_id());	
			
			if(ureaTypeList != null && !ureaTypeList.isEmpty())
				count	= ureaTypeList.size();
			
			valueObject.put("ureaTypeList", ureaTypeList);			
			valueObject.put("count", count);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
			ureaTypeList	= null;
		}
	}	

	@Override
	@Transactional
	public ValueObject deleteUreaType(ValueObject valueObject) throws Exception {
		try {
			ureaManufacturerRepository.deleteUreaTypeById(valueObject.getLong("ureaManufacturerId"));
			valueObject.put("deleted", true);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}



}
