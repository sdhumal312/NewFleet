package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.bl.LoadTypeMasterBL;
import org.fleetopgroup.persistence.dao.LoadTypesRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.LoadTypeMaster;
import org.fleetopgroup.persistence.serviceImpl.ILoadTypeService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoadTypeService implements ILoadTypeService{
	
	LoadTypeMasterBL loadTypeMasterBL = new LoadTypeMasterBL();
	
	@Autowired LoadTypesRepository loadTypesRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public ValueObject saveLoadTypes(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails		= null;		
		List<LoadTypeMaster> 		loadTypeList	= null;
		List<LoadTypeMaster> 		validate		= null;
		
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valueObject.put("userDetails", userDetails);			
			
			LoadTypeMaster  loadTypes  = loadTypeMasterBL.getLoadTypesModel(valueObject);
			
			validate				   = validateLoadTypes(loadTypes.getLoadTypeName(), userDetails.getCompany_id());			
			
			if(validate != null && !validate.isEmpty()) {
				valueObject.put("alreadyExist", true);
				return valueObject;
			}		
			
			loadTypesRepository.save(loadTypes);
			
			valueObject.put("saved", true);
			int count	= 0;	
			
			loadTypeList	= loadTypesRepository.getLoadTypesListByCompanyId(userDetails.getCompany_id());
					
			if(loadTypeList != null && !loadTypeList.isEmpty())
				count	= loadTypeList.size();
			valueObject.put("loadTypeList", loadTypeList);
			
			valueObject.put("count", count);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;			
			loadTypeList	= null;
		}
	}
	
	@Override
	public List<LoadTypeMaster> validateLoadTypes(String loadType, Integer companyId) throws Exception {
		
		return loadTypesRepository.validateLoadTypes(loadType, companyId);
		
	}
	
	@Override
	public ValueObject getLoadTypesList(ValueObject valueObject) throws Exception {
		
		CustomUserDetails			userDetails			= null;		
		List<LoadTypeMaster> 		loadTypeList		= null;
		
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			int count	= 0;
			
			loadTypeList	=	loadTypesRepository.getLoadTypesListByCompanyId(userDetails.getCompany_id());
			if(loadTypeList != null && !loadTypeList.isEmpty())
				count	= loadTypeList.size();
			valueObject.put("loadTypeList", loadTypeList);
			
			valueObject.put("count", count);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
			loadTypeList	= null;
		}
	}
	
	@Override
	public ValueObject getLoadTypesById(ValueObject valueObject) throws Exception {
		try {
			LoadTypeMaster	preLoadTypes	=	loadTypesRepository.findById(valueObject.getLong("loadTypesId")).get();
			valueObject.put("loadTypes", preLoadTypes);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	@Override
	public ValueObject editLoadTypes(ValueObject valueObject) throws Exception {
		CustomUserDetails			    userDetails		= null;
		List<LoadTypeMaster> 			loadTypeList	= null;
		List<LoadTypeMaster> 			validate		= null;
		long							loadTypesId		= 0;				
		String 							loadTypeName	= null;				
		Timestamp						lastUpdatedOn	= null;				
		long							lastUpdatedBy	= 0;				
		String 							description		= null;				
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			valueObject.put("userDetails", userDetails);		
			
			loadTypesId 					= valueObject.getLong("loadTypesId");	
			loadTypeName 					= valueObject.getString("loadTypeName").trim();	
			lastUpdatedOn					= DateTimeUtility.getCurrentTimeStamp(); 
			lastUpdatedBy					= userDetails.getId();	
			description						= valueObject.getString("description"); 
			
			entityManager.createQuery(
					" UPDATE LoadTypeMaster AS L SET L.loadTypeName = '"+loadTypeName+"', L.description = '"+ description +"' , "
					+ " L.lastUpdatedBy = '"+lastUpdatedBy+"', L.lastUpdatedOn = '"+lastUpdatedOn+"' "
					+ " WHERE L.loadTypesId = " + loadTypesId + " AND L.companyId = "+userDetails.getCompany_id()
					+ " AND L.markForDelete=0  ")
					.executeUpdate();
			
			LoadTypeMaster	preLoadTypes	=	loadTypesRepository.findById(loadTypesId).get();
				
			if(!loadTypeName.equalsIgnoreCase(preLoadTypes.getLoadTypeName().trim())) {
				validate	=	validateLoadTypes(loadTypeName, userDetails.getCompany_id());
			}
			
			if(validate != null && !validate.isEmpty()) {
				valueObject.put("alreadyExist", true);
				return valueObject;
			}			
			
			
			valueObject.put("saved", true);
			int count		= 0;			
			loadTypeList	=	loadTypesRepository.getLoadTypesListByCompanyId(userDetails.getCompany_id());	
			
			if(loadTypeList != null && !loadTypeList.isEmpty())
				count	= loadTypeList.size();
			
			valueObject.put("loadTypeList", loadTypeList);			
			valueObject.put("count", count);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
			loadTypeList	= null;
		}
	}	
	
	@Override
	@Transactional
	public ValueObject deleteLoadType(ValueObject valueObject) throws Exception {
		try {
			loadTypesRepository.deleteLoadTypeById(valueObject.getLong("loadTypesId"));		
			valueObject.put("deleted", true);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}	
	
	@Override
	public List<LoadTypeMaster> getLoadTypesListDropDown(String term, Integer companyId) throws Exception {
		try {	
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			TypedQuery<LoadTypeMaster> queryt = entityManager
					.createQuery("select L from LoadTypeMaster AS L where lower(L.loadTypeName) Like ('%"+ term + "%')"
					+ " AND L.companyId = "+companyId+" AND  L.markForDelete = 0 ", LoadTypeMaster.class);
			return queryt.getResultList();
			}else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getLoadTypeNameWiseList(ValueObject object) throws Exception {
		List<LoadTypeMaster>   loadTypesList 				= null;
		List<LoadTypeMaster>   loadTypes					= null;
		try {

			loadTypesList 	= new ArrayList<LoadTypeMaster>();
			loadTypes 		= getLoadTypesListDropDown(object.getString("term"), object.getInt("companyId"));
			
			if(loadTypes != null && !loadTypes.isEmpty()) {
				for(LoadTypeMaster load : loadTypes) {
					loadTypesList.add(load);
				}
			}

			object.put("LoadTypesList", loadTypesList);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			loadTypes 		 = null;
			loadTypesList 	 = null;
			object  	 	 = null;
		}
	}
	
}
