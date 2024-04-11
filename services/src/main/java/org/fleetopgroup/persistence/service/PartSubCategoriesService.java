package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.bl.MasterPartsBL;
import org.fleetopgroup.persistence.bl.PartSubCategoriesBL;
import org.fleetopgroup.persistence.dao.PartSubCategoriesRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.PartSubCategoryDto;
import org.fleetopgroup.persistence.model.PartSubCategory;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IPartSubCategoriesService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartSubCategoriesService implements IPartSubCategoriesService {
	
	@PersistenceContext EntityManager 				entityManager;

	@Autowired			PartSubCategoriesRepository			partSubCategoriesRepository;
	@Autowired			IMasterPartsService 				masterPartsService;
	
	PartSubCategoriesBL   	partSubCategoriesBL			= new PartSubCategoriesBL();
	MasterPartsBL 			masterPartsBL			 	= new MasterPartsBL();
	
	
	@Override
	@SuppressWarnings("unchecked")
	public ValueObject savePartSubCatrgories(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails			= null;
		List<PartSubCategory> 		partSubCategoryList	= null;
		List<PartSubCategory> 		validate			= null;
		ValueObject			 		ValueObjectIn		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			
			PartSubCategory	partSubCategory = partSubCategoriesBL.savePartSubCatrgories(valueObject);
			
			
			  validate =  validatePartSubCategoriesName(partSubCategory.getSubCategoryName(), userDetails.getCompany_id());
			  
			  if(validate != null && !validate.isEmpty()) {
				  valueObject.put("alreadyExist", true); 
				  return valueObject; 
			  }
			 
			
			partSubCategoriesRepository.save(partSubCategory);
			valueObject.put("saved", true);
			
			int count			= 0;
			
			ValueObjectIn	= getPartSubCatrgoryList(valueObject);
			partSubCategoryList = (List<PartSubCategory>) ValueObjectIn.get("partSubCategoryList");
		
			if(partSubCategoryList != null && !partSubCategoryList.isEmpty())
				count	= partSubCategoryList.size();
			
			valueObject.put("partSubCategoryList", partSubCategoryList);
			valueObject.put("count", count);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
			partSubCategoryList	= null;
		}
	}
	
	@Override
	public List<PartSubCategory> validatePartSubCategoriesName(String subCategoryName, Integer companyId) throws Exception {
		
		return partSubCategoriesRepository.validatePartSubCategoriesName(subCategoryName, companyId);
	}
	
	@Override
	public ValueObject getPartSubCatrgoryList(ValueObject valueObject) throws Exception {
		CustomUserDetails				userDetails			= null;
		List<PartSubCategoryDto> 		partSubCategoryList	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int count	= 0;
			
			
			TypedQuery<Object[]> queryt =  entityManager.createQuery(
					"SELECT psc.subCategoryName, psc.description, psc.subCategoryId, pc.pcName" + 
					" FROM PartSubCategory as psc" + 
					" INNER JOIN PartCategories as pc on psc.categoryId = pc.pcid" + 
					" WHERE psc.companyId="+userDetails.getCompany_id()+" and psc.markForDelete=0", Object[].class);
			
			
			List<Object[]> results 	=	queryt.getResultList();
			if (results != null && !results.isEmpty()) {
				partSubCategoryList = new ArrayList<PartSubCategoryDto>();
				PartSubCategoryDto list = null;
				
				for (Object[] result : results) {
					list = new PartSubCategoryDto();
					
					list.setSubCategoryName((String) result[0]);
					list.setDescription((String) result[1]);
					list.setSubCategoryId((Integer) result[2]);
					list.setPartCategoryName((String) result[3]);

					partSubCategoryList.add(list);
				}
			}
			
			if(partSubCategoryList != null && !partSubCategoryList.isEmpty())
				count	= partSubCategoryList.size();
			
			valueObject.put("partSubCategoryList", partSubCategoryList);
			valueObject.put("count", count);
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
			partSubCategoryList	= null;
		}
	}
	
	@Override
	public ValueObject getPartSubCategoryById(ValueObject valueObject) throws Exception {
		PartSubCategory					partSubCategory		= null;
		try {
			
			partSubCategory = partSubCategoriesRepository.findPartSubCategoryById(valueObject.getInt("subCategoryId"));
			
			valueObject.put("partSubCategory", partSubCategory);
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject editPartSubCategory(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails			= null;
		List<PartSubCategory> 		validate			= null;
		Integer 					subCategoryId		= 0;
		String 						partSubCategoryName	= null;

		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);

			subCategoryId		= valueObject.getInt("editSubCategoryId");
			partSubCategoryName = valueObject.getString("editPartSubCategory").trim();

			PartSubCategory	partSubCategory	=	partSubCategoriesRepository.findPartSubCategoryById(subCategoryId);

			if(!partSubCategoryName.equalsIgnoreCase(partSubCategory.getSubCategoryName().trim())) {
				validate	=	validatePartSubCategoriesName(partSubCategoryName, userDetails.getCompany_id());

				if(validate != null && !validate.isEmpty()) {
					valueObject.put("alreadyExist", true);
					return valueObject;
				}
			} 

			valueObject = updatePartSubCategory(valueObject,userDetails);

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
		}
	}
	public ValueObject updatePartSubCategory(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
		Integer 					subCategoryId		= 0;
		Integer 					PartCategoryId		= 0;
		String 						partSubCategoryName	= null;
		Timestamp 					lastUpdatedOn		= null;
		long						lastUpdatedBy		= 0;
		String					    description			= null;
		
		try {
		
			subCategoryId		= valueObject.getInt("editSubCategoryId");
			PartCategoryId		= valueObject.getInt("editPartCategory");
			partSubCategoryName = valueObject.getString("editPartSubCategory").trim();
			lastUpdatedOn		= DateTimeUtility.getCurrentTimeStamp();
			lastUpdatedBy		= userDetails.getId();
			description     	= valueObject.getString("editDescription");
			
			entityManager.createQuery(
					" UPDATE PartSubCategory psc SET psc.subCategoryName = '"+partSubCategoryName+"', psc.description = '"+ description +"' , "
					+ " psc.lastModifiedById = '"+lastUpdatedBy+"', psc.lastModifiedOn = '"+lastUpdatedOn+"' ,"
					+"psc.categoryId = "+PartCategoryId+""
					+ " WHERE psc.subCategoryId = " + subCategoryId + " AND psc.companyId = "+userDetails.getCompany_id()
					+ " AND psc.markForDelete = 0  ")
					.executeUpdate();
			valueObject.put("saved", true);
			
			return valueObject;
			
		}catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deletePartSubCategoryById(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails			= null;

		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			valueObject = deletePartSubCategory(valueObject,userDetails);

			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public ValueObject deletePartSubCategory(ValueObject valueObject , CustomUserDetails userDetails) throws Exception {
		Integer 					subCategoryId		= 0;
		Timestamp 					lastUpdatedOn		= null;
		long						lastUpdatedBy		= 0;
		try {
			subCategoryId		= valueObject.getInt("subCategoryId");
			lastUpdatedOn		= DateTimeUtility.getCurrentTimeStamp();
			lastUpdatedBy		= userDetails.getId();
			
			entityManager.createQuery(
					" UPDATE PartSubCategory AS psc SET psc.markForDelete = "+1+", psc.lastModifiedById = "+ lastUpdatedBy+", psc.lastModifiedOn = '"+lastUpdatedOn+"' "
					+ " WHERE psc.subCategoryId = " + subCategoryId + " AND psc.companyId = "+userDetails.getCompany_id())
					.executeUpdate();
			valueObject.put("deleted", true);
		
			return valueObject;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<PartSubCategory> getPartSubCategoryByCompanyId(Integer companyId) throws Exception {
		return partSubCategoriesRepository.getPartSubCategoryListByCompanyId(companyId);
	}
}
