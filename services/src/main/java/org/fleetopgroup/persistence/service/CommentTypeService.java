package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.bl.CommentTypeBL;
import org.fleetopgroup.persistence.bl.MasterPartsBL;
import org.fleetopgroup.persistence.dao.CommentTypeRepository;
import org.fleetopgroup.persistence.dto.CommentTypeDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.CommentType;
import org.fleetopgroup.persistence.serviceImpl.ICommentTypeService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentTypeService implements ICommentTypeService {

	@PersistenceContext EntityManager 				entityManager;
	private static final int PAGE_SIZE 				= 10;

	@Autowired	private		CommentTypeRepository				commentTypeRepository;
	@Autowired private		HttpServletRequest					request;

	CommentTypeBL   		commentTypeBL				= new CommentTypeBL();
	MasterPartsBL 			masterPartsBL			 	= new MasterPartsBL();


	@Override
	public ValueObject saveCommentTypeDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails			= null;
		List<CommentType> 			validate			= null;
		ValueObject 				valueObj			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			
			valueObj = commentTypeDetailsValidation(valueObject);
			
			if(valueObj != null && !valueObj.isEmpty())
				return valueObj;

			CommentType	commentType = commentTypeBL.saveCommentType(valueObject);

			validate =  validateCommentTypeName(commentType.getCommentTypeName(), userDetails.getCompany_id());

			if(validate != null && !validate.isEmpty()) {
				valueObject.put("alreadyExist", true); 
				return valueObject; 
			}
			commentTypeRepository.save(commentType);
			valueObject.put("saved", true);

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
		}
	}
	
	@Override 
	public List<CommentType> validateCommentTypeName(String commentTypeName,
		Integer companyId) throws Exception {

	return commentTypeRepository.validateCommentTypeName(commentTypeName,companyId); 
	}

	@Override
	public ValueObject getcommentTypeListByCompanyId(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		try {


			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber			= valueObject.getInt("pageNumber",0);
			Page<CommentType> page = getDeployment_Page_CommentType(pageNumber, userDetails.getCompany_id());

			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());


			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());

			List<CommentTypeDto> pageList = getcommentTypeList(pageNumber, userDetails.getCompany_id());

			valueObject.put("CommentType", pageList);
			valueObject.put("SelectPage", pageNumber);

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails				= null;
		}
	}

	public Page<CommentType> getDeployment_Page_CommentType(Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "commentTypeId");
		return commentTypeRepository.getDeployment_Page_CommentType(companyId, pageable);
	}

	@Override
	public List<CommentTypeDto> getcommentTypeList(Integer pageNumber, Integer companyId) throws Exception {

		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		List<CommentTypeDto> 			commentTypeList 		= null;
		CommentTypeDto 					commentTypeDto			= null;

		try {

			typedQuery = entityManager.createQuery("SELECT CT.commentTypeId, CT.commentTypeName, CT.companyId, CT.description"
					+ " FROM CommentType AS CT"
					+ " WHERE CT.companyId ="+companyId+" AND CT.markForDelete = 0 ORDER BY CT.commentTypeId DESC", Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				commentTypeList = new ArrayList<>();

				for (Object[] result : resultList) {
					commentTypeDto = new CommentTypeDto();

					commentTypeDto.setCommentTypeId((Long) result[0]);
					commentTypeDto.setCommentTypeName((String) result[1]);
					commentTypeDto.setCompanyId((Integer) result[2]);
					if(result[3] != null && result[3] != " ")
						commentTypeDto.setDescription((String) result[3]);
					else
						commentTypeDto.setDescription("--");

					commentTypeList.add(commentTypeDto);
				}
			}

			return commentTypeList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			commentTypeList 		= null;
			commentTypeDto			= null;
		}
	}

	@Override
	public ValueObject getCommentTypeById(ValueObject valueObject) throws Exception {
		CommentType					commentType		= null;
		try {

			commentType = commentTypeRepository.findCommentTypeById(valueObject.getLong("commentTypeId"));

			valueObject.put("commentType", commentType);

			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	@Transactional
	public ValueObject editCommentType(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails			= null;
		List<CommentType> 			validate			= null;
		long 						commentTypeId		= 0;
		String 						commentTypeName		= null;

		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			 if(valueObject.get("editCommentTypeName") == null || valueObject.get("editCommentTypeName") == "") {
				 valueObject.put("editCommentTypeName", true);
				 return valueObject;
			}
			commentTypeId		= valueObject.getLong("editCommentTypeId");
			commentTypeName = valueObject.getString("editCommentTypeName").trim();
			
			
			CommentType	commentType	=	commentTypeRepository.findCommentTypeById(commentTypeId);

			if(!commentTypeName.equalsIgnoreCase(commentType.getCommentTypeName().trim())) {
				validate	=	validateCommentTypeName(commentTypeName, userDetails.getCompany_id());

				if(validate != null && !validate.isEmpty()) {
					valueObject.put("alreadyExist", true);
					return valueObject;
				}
			} 

			valueObject = updateCommentTypeDetails(valueObject,userDetails);

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
		}
	}

	public ValueObject updateCommentTypeDetails(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
		long 						commentTypeId		= 0;
		String 						commentTypeName		= null;
		Timestamp 					lastUpdatedOn		= null;
		long						lastUpdatedBy		= 0;
		String					    description			= null;

		try {

			commentTypeId		= valueObject.getLong("editCommentTypeId");
			commentTypeName = valueObject.getString("editCommentTypeName").trim();
			lastUpdatedOn		= DateTimeUtility.getCurrentTimeStamp();
			lastUpdatedBy		= userDetails.getId();
			description     	= valueObject.getString("editDescription");

			entityManager.createQuery(
					" UPDATE CommentType CT SET CT.commentTypeName = '"+ commentTypeName +"', CT.description = '"+ description +"' , "
							+ " CT.lastModifiedById = '"+lastUpdatedBy+"', CT.lastModifiedOn = '"+ lastUpdatedOn +"' "
							+ " WHERE CT.commentTypeId = " + commentTypeId + " AND CT.companyId = "+userDetails.getCompany_id())
			.executeUpdate();
			valueObject.put("updated", true);

			return valueObject;

		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public ValueObject deleteCommentTypeById(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails			= null;

		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			valueObject = deleteCommentType(valueObject,userDetails);

			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}

	public ValueObject deleteCommentType(ValueObject valueObject , CustomUserDetails userDetails) throws Exception {
		long 						commentTypeId		= 0;
		Timestamp 					lastUpdatedOn		= null;
		long						lastUpdatedBy		= 0;
		try {
			commentTypeId		= valueObject.getLong("commentTypeId");
			lastUpdatedOn		= DateTimeUtility.getCurrentTimeStamp();
			lastUpdatedBy		= userDetails.getId();

			entityManager.createQuery(
					" UPDATE CommentType AS ct SET ct.markForDelete = "+ 1 +", ct.lastModifiedById = "+ lastUpdatedBy+", ct.lastModifiedOn = '"+lastUpdatedOn+"' "
							+ " WHERE ct.commentTypeId = " + commentTypeId + " AND ct.companyId = "+userDetails.getCompany_id())
			.executeUpdate();
			valueObject.put("deleted", true);

			return valueObject;

		} catch (Exception e) {
			throw e;
		}
	}
	public ValueObject commentTypeDetailsValidation(ValueObject valueObject) throws Exception {

		ValueObject  objectIn = null;

		try {
			objectIn = new ValueObject();

		 if(valueObject.get("commentTypeName") == null || valueObject.get("commentTypeName") == "") {
				objectIn.put("commentTypeName", true);
				objectIn.put("accessToken", Utility.getUniqueToken(request));
			}
			
			return objectIn;

		} catch (Exception e) {
			throw e;
		}
	}
}
