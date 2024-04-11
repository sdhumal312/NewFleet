package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.bl.ToolBoxDetailsBL;
import org.fleetopgroup.persistence.dao.ToolBoxDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.ToolBoxDetailsDto;
import org.fleetopgroup.persistence.model.ToolBoxDetails;
import org.fleetopgroup.persistence.serviceImpl.IToolBoxDetailsService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ToolBoxDetailsService implements IToolBoxDetailsService
{
	@PersistenceContext EntityManager 				entityManager;
	ToolBoxDetailsBL 			toolBoxDetailsBL 	= new ToolBoxDetailsBL();
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ToolBoxDetailsRepository toolBoxDetailsRepository;

	@Override
	public ValueObject saveToolBoxDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails					= null;
		ToolBoxDetails 				toolBoxDetails						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			toolBoxDetails 		= toolBoxDetailsBL.prepareToolBoxDetails(valueObject,userDetails);
			toolBoxDetailsRepository.save(toolBoxDetails);
			valueObject.put("save", true);
			
			return valueObject;
		} 
		catch (Exception e) 
		{
			LOGGER.error("Err"+e);
			throw e;
		}
		finally  
		{
				userDetails			= null;
		}
	}

	@Override
	public ValueObject getToolBoxDetailsList(ValueObject valueObject) throws Exception 
	{
		CustomUserDetails			userDetails					= null;
		List<ToolBoxDetails>		toolBoxDetailsList			= null;
		
		try
		{
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			toolBoxDetailsList	= toolBoxDetailsRepository.getAllToolBoxDetailsList(userDetails.getCompany_id());
			
			valueObject.put("toolBoxDetailsList", toolBoxDetailsList);
			valueObject.put("count", toolBoxDetailsList.size());
			
			return valueObject;
			
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			userDetails			= null;
			toolBoxDetailsList			= null;
		}
	}

	@Override
	public ValueObject getToolBoxDetailsByToolBoxDetailsId(ValueObject valueObject) throws Exception 
	{	
		ToolBoxDetails toolBoxDetails		= null;
		CustomUserDetails			userDetails					= null;
		try
		{	userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			toolBoxDetails		=	toolBoxDetailsRepository.getToolBoxDetailsByToolBoxDetailsId(valueObject.getLong("toolBoxDetailsId"),userDetails.getCompany_id());
			valueObject.put("toolBoxDetails", toolBoxDetails);
			return valueObject;
		}
		catch (Exception e) {
			throw e;
		}
		
		
	}
	@Override
	public ValueObject getToolBoxDetailsByVid(ValueObject valueObject) throws Exception 
	{	
		List<ToolBoxDetailsDto> 			toolBoxDetailsDtoList				= null;
		ToolBoxDetailsDto 					toolBoxDetailsDto				= null;
		CustomUserDetails				userDetails					= null;
		TypedQuery<Object[]> 			query						= null;
		List<Object[]> 					results						= null;
		try
		{
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			query = entityManager.createQuery(
					"SELECT TBD.ToolBoxDetailsId,TBD.quantity, T.toolBoxName,TBD.description "
							+ " FROM ToolBoxDetails AS TBD "
							+ " LEFT JOIN ToolBox AS T ON T.toolBoxId = TBD.toolBoxId "
							+ " WHERE TBD.companyId = "+userDetails.getCompany_id()+" AND TBD.vid = "+valueObject.getInt("vid")+" AND TBD.markForDelete = 0", Object[].class);
			
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				toolBoxDetailsDtoList 	= new ArrayList<ToolBoxDetailsDto>();
				
				for (Object[] result : results) {
					toolBoxDetailsDto 		= new ToolBoxDetailsDto();
					
					toolBoxDetailsDto.setToolBoxDetailsId((Long) result[0]);
					toolBoxDetailsDto.setQuantity((Double) result[1]);
					toolBoxDetailsDto.setToolBoxName((String) result[2]);
					toolBoxDetailsDto.setDescription((String) result[3]);
					
					toolBoxDetailsDtoList.add(toolBoxDetailsDto);
				}
			}
			valueObject.put("toolBoxDetails", toolBoxDetailsDtoList);
			
			return valueObject;
		}
		catch (Exception e) {
			throw e;
		}
		
		
	}

	@Transactional
	@Override
	public ValueObject editToolBoxDetails(ValueObject valueObject) throws Exception
	{
		CustomUserDetails			userDetails			= null;
		
		try {
		
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			toolBoxDetailsRepository.updateToolBoxDetailsByToolBoxDetailsId(valueObject.getLong("editToolBoxId"),valueObject.getDouble("editQuantity"), valueObject.getString("editDescription"),valueObject.getLong("editToolBoxDetailsId"),valueObject.getInt("vid"),userDetails.getCompany_id());
		
			return valueObject;
		
		} catch (Exception e) 
		{
			LOGGER.error("Err"+e);
			throw e;
		}
		finally 
		{
			userDetails			= null;
		}
		
	}

	@Transactional
	@Override
	public ValueObject deleteToolBoxDetails(ValueObject valueObject) throws Exception 
	{
		try
		{
			toolBoxDetailsRepository.deleteToolBoxDetailsByToolBoxDetailsId(valueObject.getLong("toolBoxDetailsId"));
			valueObject.put("deleted", true);
			
			return valueObject;
		}
		catch (Exception e) 
		{
			throw e;
		}
		
		
	}
	
}
