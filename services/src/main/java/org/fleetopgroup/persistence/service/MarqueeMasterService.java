package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.MarqueeMasterRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.MarqueeMasterDto;
import org.fleetopgroup.persistence.model.MarqueeMaster;
import org.fleetopgroup.persistence.serviceImpl.IMarqueeMasterService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("MarqueeMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MarqueeMasterService implements IMarqueeMasterService {

	@PersistenceContext	public EntityManager entityManager;
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private @Autowired	MarqueeMasterRepository		IMarqueeMasterRepository;
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	
	@Override
	public ValueObject saveMarqueeMessage(ValueObject object) throws Exception {
		CustomUserDetails		userDetails		= null;
		Integer					companyId 		= null;
		String					message 		= null;
		String					colorId 		= null;
		MarqueeMaster			marqueeMaster	= null;	
		try {

			java.util.Date currentDate 	= new java.util.Date();
			
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			companyId					= object.getInt("company");
			message						= object.getString("message");
			colorId						= object.getString("colorId");
			
			marqueeMaster 				= new MarqueeMaster();
			marqueeMaster.setCompanyId(companyId);
			marqueeMaster.setCreatedById(userDetails.getId());
			marqueeMaster.setCreatedDate(currentDate);
			marqueeMaster.setLastModifiedById(userDetails.getId());
			marqueeMaster.setLastupdated(currentDate);
			marqueeMaster.setMarquee_message(message+"`"+colorId);
			marqueeMaster.setMarkForDelete(false);
			
			IMarqueeMasterRepository.save(marqueeMaster);

		}catch(Exception e) {
			LOGGER.error("Exception " + e);
		}

		return object;

	}
	
	@Override 
	public ValueObject getMessageByCompanyId(ValueObject object) throws Exception {// this method used to get Marquee message to show in master  
		Integer						companyId 			= null;
		TypedQuery<Object[]> 		queryt				= null;
		List<Object[]> 				results				= null;
		List<MarqueeMasterDto> 		Dtos 				= null;
		try {
			companyId	= object.getInt("company");
			
			queryt 		=  entityManager.createQuery(
					"SELECT companyId , marquee_message, marquee_id FROM MarqueeMaster"
							+ " WHERE companyId=" + companyId+ "  "
							+ " and markForDelete = 0 ",Object[].class);
			results 	= queryt.getResultList();
			
			if (results != null) {
				Dtos = new ArrayList<MarqueeMasterDto>();
				MarqueeMasterDto marqueeMasterDto = null;
					for (Object[] result : results) {
						marqueeMasterDto = new MarqueeMasterDto();
							if(result != null) {
								marqueeMasterDto.setCompanyId((Integer) result[0]);				
								marqueeMasterDto.setMarquee_message(((String) result[1]).split("`")[0]);	
								marqueeMasterDto.setMarquee_id((Long) result[2]);	
								Dtos.add(marqueeMasterDto);
						}
					}
				object.put("messageList", Dtos);
				}
			}
		catch(Exception e) {
			LOGGER.error("Exception " + e);
		}
		return object;
		
	}
	
	@Override
	public List<MarqueeMasterDto> getCompayWiseMarqueeMessage() throws Exception {//header Marquee Message
		CustomUserDetails 			userDetails			= null;
		List<MarqueeMasterDto> 		Dtos 				= null;
		Integer 					companyId			= 0;
		Integer 					companyAllId		= 0;
		TypedQuery<Object[]> 		query				= null;
		List<Object[]> 				results				= null;
		MarqueeMasterDto 			marqueeMasterDto 	= null;
		
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			companyId	= userDetails.getCompany_id();
			
			query = entityManager.createQuery(
					"SELECT companyId ,marquee_message,marquee_id FROM MarqueeMaster"
							+ " WHERE companyId IN(" +companyId+ ","+ companyAllId +" )  "
							+ " and markForDelete = 0 " ,Object[].class);
				
			results = query.getResultList();
			
			if (results != null) {
			Dtos = new ArrayList<MarqueeMasterDto>();
			
				for (Object[] result : results) {
					marqueeMasterDto = new MarqueeMasterDto();
					if(result!= null) {
						marqueeMasterDto.setCompanyId((Integer) result[0]);	
						marqueeMasterDto.setMarquee_message(((String) result[1]).split("`")[0]);	
						marqueeMasterDto.setColor_Id(((String) result[1]).split("`")[1]);	
						marqueeMasterDto.setMarquee_id((Long) result[2]);	
						Dtos.add(marqueeMasterDto);
					}
				}
			}
		}
		catch(Exception e) {
			LOGGER.error("Exception " + e);
		}
		return Dtos;
		
	}
	
	@Override
	public List<MarqueeMasterDto> getAllMarqueeMessagesList() throws Exception {//View Page(jsp) to Show All Marquee Message
		List<MarqueeMasterDto> 		Dtos 				= null;
		TypedQuery<Object[]> 		query				= null;
		List<Object[]> 				results				= null; 
		MarqueeMasterDto 			marqueeMasterDto 	= null;
		
		try {
			 query 	= entityManager.createQuery(
					"SELECT m.companyId ,m.marquee_message,m.marquee_id, c.company_name FROM MarqueeMaster As m "
							+ " LEFT JOIN Company As c ON c.company_id = m.companyId "
							+ " where m.markForDelete = 0 " ,Object[].class);
				
			 results = query.getResultList();
			
			if (results != null) {
			Dtos = new ArrayList<MarqueeMasterDto>();
			
				for (Object[] result : results) {

					marqueeMasterDto = new MarqueeMasterDto();
					marqueeMasterDto.setCompanyId((Integer) result[0]);				
					marqueeMasterDto.setMarquee_message((String) result[1]);	
					marqueeMasterDto.setMarquee_id((Long) result[2]);
					if(result[3] != null) {
					marqueeMasterDto.setCompanyName((String) result[3]);	
					}else {
						marqueeMasterDto.setCompanyName("All");
					}
					Dtos.add(marqueeMasterDto);
				}
			}
		}
		catch(Exception e) {
			LOGGER.error("Exception " + e);
		}
		return Dtos;
		
	}
	
}