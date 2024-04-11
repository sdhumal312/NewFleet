package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.bl.ClothTypesBL;
import org.fleetopgroup.persistence.dao.ClothTypesRepository;
import org.fleetopgroup.persistence.dto.ClothTypesDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.ClothTypes;
import org.fleetopgroup.persistence.serviceImpl.IClothTypeService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClothTypeService implements IClothTypeService {
	
	@Autowired	ClothTypesRepository		clothTypesRepository;
	
	@PersistenceContext EntityManager entityManager;

	
	ClothTypesBL	clothTypesBL	= new ClothTypesBL();

	@Override
	public ValueObject getClothTypesList(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails		= null;
		List<ClothTypes> 			clothTypeList	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			int count	= 0;
			
			clothTypeList	=	clothTypesRepository.getClothTypesListByCompanyId(userDetails.getCompany_id());
			if(clothTypeList != null && !clothTypeList.isEmpty())
				count	= clothTypeList.size();
			valueObject.put("clothTypeList", clothTypeList);
			
			valueObject.put("count", count);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
			clothTypeList	= null;
		}
	}

	@Override
	public ValueObject saveClothTypes(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails		= null;
		List<ClothTypes> 			clothTypeList	= null;
		List<ClothTypes> 			validate		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valueObject.put("userDetails", userDetails);
			
			ClothTypes	clothTypes = clothTypesBL.getClothTypesModel(valueObject);
			
			validate	=	validateClotypes(clothTypes.getClothTypeName(), userDetails.getCompany_id());
			
			if(validate != null && !validate.isEmpty()) {
				valueObject.put("alreadyExist", true);
				return valueObject;
			}
			
			
			clothTypesRepository.save(clothTypes);
			valueObject.put("saved", true);
			int count	= 0;
			
			clothTypeList	=	clothTypesRepository.getClothTypesListByCompanyId(userDetails.getCompany_id());
			if(clothTypeList != null && !clothTypeList.isEmpty())
				count	= clothTypeList.size();
			valueObject.put("clothTypeList", clothTypeList);
			
			valueObject.put("count", count);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
			clothTypeList	= null;
		}
	}
	
	@Transactional
	@Override
	public ValueObject editClothTypes(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails		= null;
		List<ClothTypes> 			clothTypeList	= null;
		List<ClothTypes> 			validate		= null;
		
		long 						clothTypesId	= 0;
		String 						clothTypeName	= null;
		Timestamp 					lastUpdatedOn	= null;
		long						lastUpdatedBy	= 0;
		String					    description		= null;
		
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valueObject.put("userDetails", userDetails);
			
			clothTypesId	= valueObject.getLong("clothTypesId");
			clothTypeName   = valueObject.getString("clothTypeName").trim();
			lastUpdatedOn	= DateTimeUtility.getCurrentTimeStamp();
			lastUpdatedBy	= userDetails.getId();
			description     = valueObject.getString("description");
			
			entityManager.createQuery(
					" UPDATE ClothTypes AS C SET C.clothTypeName = '"+clothTypeName+"', C.description = '"+ description +"' , "
					+ " C.lastUpdatedBy = '"+lastUpdatedBy+"', C.lastUpdatedOn = '"+lastUpdatedOn+"' "
					+ " WHERE C.clothTypesId = " + clothTypesId + " AND C.companyId = "+userDetails.getCompany_id()
					+ " AND C.markForDelete = 0  ")
					.executeUpdate();
			
			ClothTypes	preClothTypes	=	clothTypesRepository.findById(clothTypesId).get();
		
			if(!clothTypeName.equalsIgnoreCase(preClothTypes.getClothTypeName().trim())) {
				validate	=	validateClotypes(clothTypeName, userDetails.getCompany_id());
			}
			
			if(validate != null && !validate.isEmpty()) {
				valueObject.put("alreadyExist", true);
				return valueObject;
			}
			
			valueObject.put("saved", true);
			int count	= 0;
			
			clothTypeList	=	clothTypesRepository.getClothTypesListByCompanyId(userDetails.getCompany_id());
			if(clothTypeList != null && !clothTypeList.isEmpty())
				count	= clothTypeList.size();
			valueObject.put("clothTypeList", clothTypeList);
			
			valueObject.put("count", count);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
			clothTypeList	= null;
		}
	}
	
	@Override
	public ValueObject getClothTypesById(ValueObject valueObject) throws Exception {
		try {
			ClothTypes	preClothTypes	=	clothTypesRepository.findById(valueObject.getLong("clothTypesId")).get();
			valueObject.put("clothTypes", preClothTypes);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteClothType(ValueObject valueObject) throws Exception {
		try {
			clothTypesRepository.deleteClothTypeById(valueObject.getLong("clothTypesId"));
			valueObject.put("deleted", true);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<ClothTypes> validateClotypes(String clothType, Integer companyId) throws Exception {
		
		return clothTypesRepository.validateClotypes(clothType, companyId);
	}
	
	@Override
	public List<ClothTypes> getClothTypesList(String clothType, Integer companyId) throws Exception {
		try {
			if(clothType != null && !clothType.trim().equalsIgnoreCase("") && clothType.indexOf('\'') != 0 ) {
			TypedQuery<ClothTypes> queryt = entityManager
					.createQuery("select BM from ClothTypes AS BM where lower(BM.clothTypeName) Like ('%"+ clothType + "%')"
					+ " AND BM.companyId = "+companyId+" AND  BM.markForDelete = 0 ", ClothTypes.class);
			return queryt.getResultList();
			}else {
				return null;
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<ClothTypes> getClothTypesListNotSelected(String term, String clothTypes, Integer companyId)
			throws Exception {
		try {
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			TypedQuery<ClothTypes> queryt = entityManager
					.createQuery("select BM from ClothTypes AS BM where lower(BM.clothTypeName) Like ('%"+ term + "%')"
					+ " AND BM.companyId = "+companyId+" AND clothTypesId NOT IN ("+clothTypes+") AND  BM.markForDelete = 0 ", ClothTypes.class);
			return queryt.getResultList();
			}else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<ClothTypesDto> getClothTypesListByClothTypesId(String	clothType, Integer locationId, Integer companyId) throws Exception {
		List<Object[]> 					resultList 				= null; 
		List<ClothTypesDto> 			clothInvoiceList 		= null;
		ClothTypesDto 					clothInvoiceDto			= null;
		try {
			if(clothType != null && !clothType.trim().equalsIgnoreCase("") && clothType.indexOf('\'') != 0 ) {
			TypedQuery<Object[]> typedQuery = entityManager
					.createQuery("select BM.clothTypesId, BM.clothTypeName, ST.usedStockQuantity, ST.newStockQuantity, ST.inServiceQuantity"
							+ " from ClothTypes AS BM "
							+ " INNER JOIN ClothInventoryStockTypeDetails ST ON ST.clothTypesId = BM.clothTypesId AND wareHouseLocationId ="+locationId+" "
							+ " where lower(BM.clothTypeName) Like ('%"+ clothType + "%')"
					+ " AND BM.companyId = "+companyId+" AND  BM.markForDelete = 0 ", Object[].class);
			
			
			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new ClothTypesDto();
					
					clothInvoiceDto.setClothTypesId((Long) result[0]);
					clothInvoiceDto.setClothTypeName((String) result[1]);
					clothInvoiceDto.setUsedStockQuantity((Double) result[2]);
					clothInvoiceDto.setNewStockQuantity((Double) result[3]);
					clothInvoiceDto.setInServiceQuantity((Double) result[4]);
				
					clothInvoiceList.add(clothInvoiceDto);
				}
			}
			}
			return clothInvoiceList;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<ClothTypesDto> getClothTypesListByClothTypesIdWithRate(String	clothType, Integer locationId, Integer vendorId) throws Exception {
		List<Object[]> 					resultList 				= null; 
		List<ClothTypesDto> 			clothInvoiceList 		= null;
		ClothTypesDto 					clothInvoiceDto			= null;
		try {
			if(clothType != null && !clothType.trim().equalsIgnoreCase("") && clothType.indexOf('\'') != 0 ) {
			TypedQuery<Object[]> typedQuery = entityManager
					.createQuery("select BM.clothTypesId, BM.clothTypeName, ST.usedStockQuantity, ST.newStockQuantity, ST.inServiceQuantity"
							+ " from ClothTypes AS BM "
							+ " INNER JOIN ClothInventoryStockTypeDetails ST ON ST.clothTypesId = BM.clothTypesId AND wareHouseLocationId ="+locationId+"  AND ST.markForDelete = 0 "
							+ " LEFT JOIN VendorFixedLaundryRate  VFR ON VFR.clothTypesId = BM.clothTypesId AND VFR.vendorId = "+vendorId+" AND VFR.markForDelete = 0 "
							+ " where lower(BM.clothTypeName) Like ('%"+ clothType + "%')"
							+ " AND  BM.markForDelete = 0 ", Object[].class);
			
			
			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new ClothTypesDto();
					
					clothInvoiceDto.setClothTypesId((Long) result[0]);
					clothInvoiceDto.setClothTypeName((String) result[1]);
					clothInvoiceDto.setUsedStockQuantity((Double) result[2]);
					clothInvoiceDto.setNewStockQuantity((Double) result[3]);
					clothInvoiceDto.setInServiceQuantity((Double) result[4]);
				
					clothInvoiceList.add(clothInvoiceDto);
				}
			}
			}
			return clothInvoiceList;
			
		} catch (Exception e) {
			throw e;
		}
	}
}
