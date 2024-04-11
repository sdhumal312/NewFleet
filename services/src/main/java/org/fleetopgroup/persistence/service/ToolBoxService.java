package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.bl.ToolBoxBL;
import org.fleetopgroup.persistence.dao.ToolBoxRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.ToolBox;
import org.fleetopgroup.persistence.serviceImpl.IToolBoxService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ToolBoxService implements IToolBoxService
{
	@PersistenceContext EntityManager 				entityManager;
	ToolBoxBL 			toolBL 						= new ToolBoxBL();
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ToolBoxRepository toolBoxRepository;

	@Override
	public ValueObject saveToolBox(ValueObject valueObject) throws Exception 
		{
		CustomUserDetails			userDetails					= null;
		ToolBox 					validateToolBoxName			= null;
		ToolBox 					tool						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			validateToolBoxName 		= validateToolBoxName(valueObject.getString("toolBoxName"), userDetails.getCompany_id());
			
			if(validateToolBoxName != null) {
				valueObject.put("alreadyExist", true);
			}else {
				tool 		= toolBL.saveToolBoxObject(valueObject,userDetails);
				toolBoxRepository.save(tool);
				valueObject.put("save", true);
			}
			
			return valueObject;
		} catch (Exception e) 
			{
				throw e;
			}
			finally 
			{
				userDetails					= null;
				validateToolBoxName			= null;
				tool						= null;
			}
	
	}

	@Override
	public ToolBox validateToolBoxName(String toolBoxName, Integer companyId) throws Exception
	{
		
		
		return toolBoxRepository.validateToolBoxName(toolBoxName, companyId);
	}

	@Override
	public ValueObject getToolBoxList(ValueObject valueObject) throws Exception 
	{
		CustomUserDetails			userDetails			= null;
		List<ToolBox>				toolBoxList			= null;
		
		try
		{
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			toolBoxList			= toolBoxRepository.getToolBoxListByCompanyId(userDetails.getCompany_id());
			
			valueObject.put("toolBoxList", toolBoxList);
			valueObject.put("count", toolBoxList.size());
			
			return valueObject;
			
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			userDetails			= null;
			toolBoxList			= null;
		}
	}

	@Override
	public ValueObject getToolBoxListById(ValueObject valueObject) throws Exception 
	{	ToolBox toolBox		= null;
		try
		{
			toolBox		=	toolBoxRepository.findById(valueObject.getLong("toolBoxId")).get();
			valueObject.put("ToolBox", toolBox);
			return valueObject;
		}
		catch (Exception e) {
			throw e;
		}
		
		
	}

	@Transactional
	@Override
	public ValueObject editToolBox(ValueObject valueObject) throws Exception
	{
		CustomUserDetails			userDetails			= null;
		ToolBox	 					toolBox				= null;
		ToolBox	 					validateToolBox		= null;
		
		try {
		
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			toolBox						= toolBoxRepository.findByToolBoxId(valueObject.getLong("editToolBoxId"),userDetails.getCompany_id());
			
			if(valueObject.getString("editToolBoxName").equalsIgnoreCase(toolBox.getToolBoxName().trim())) {
				toolBoxRepository.updateToolBoxByExpenseId(valueObject.getLong("editToolBoxId"),toolBox.getToolBoxName(), valueObject.getString("editDescription"),userDetails.getCompany_id());
			}else {
				validateToolBox		= validateToolBoxName(valueObject.getString("editToolBoxName"),userDetails.getCompany_id());
				if(validateToolBox != null) {
					valueObject.put("alreadyExist", true);
				}else {
					toolBoxRepository.updateToolBoxByExpenseId(valueObject.getLong("editToolBoxId"),valueObject.getString("editToolBoxName"), valueObject.getString("editDescription"),userDetails.getCompany_id());
				}
			}
		
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
	public ValueObject deleteToolBox(ValueObject valueObject) throws Exception 
	{
		try
		{
			toolBoxRepository.deleteToolBoxById(valueObject.getLong("toolBoxId"));
			valueObject.put("deleted", true);
			
			return valueObject;
		}
		catch (Exception e) 
		{
			throw e;
		}
		
		
	}
	
	@Override
	public ValueObject getAllToolBoxForAutoComplete(String search) throws Exception {
		CustomUserDetails			userDetails						= null;
		List<ToolBox>				toolBoxList						= null;
		ToolBox 					toolBox 					= null;
		ValueObject 				valueObject						= null;
		TypedQuery<Object[]> 		query							= null;
		List<Object[]> 				results							= null;	
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject					= new ValueObject();
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			query = entityManager.createQuery(
					"SELECT T.toolBoxId, T.toolBoxName FROM ToolBox AS T "
					+ "WHERE lower(T.toolBoxName) Like ('%" + search + "%') AND T.companyId = "+userDetails.getCompany_id()+" AND T.markForDelete = 0", Object[].class);
			query.setFirstResult(0);
			query.setMaxResults(20);
			
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				toolBoxList = new ArrayList<ToolBox>();
				
				for (Object[] result : results) {
					toolBox = new ToolBox();
					toolBox.setToolBoxId((Long) result[0]);
					toolBox.setToolBoxName((String) result[1]);
					toolBoxList.add(toolBox);
				}
			}
			
			
			valueObject.put("toolBoxList", toolBoxList);
			}
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;    
			toolBoxList						= null;    
			toolBox 						= null;    
			valueObject						= null;    
			query							= null;    
			results							= null;	   
		}
	}
	
	
}
