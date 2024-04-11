/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.bl.TyreExpensesBL;
import org.fleetopgroup.persistence.dao.TyreExpensesRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TyreExpenses;
import org.fleetopgroup.persistence.serviceImpl.ITyreExpensesService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("TyreExpensesService")
@Transactional
public class TyreExpensesService implements ITyreExpensesService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TyreExpensesRepository tyreExpensesRepository;
	
	TyreExpensesBL	tyreExpensesBL 		= new TyreExpensesBL();

	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public ValueObject getAllTyreExpenses() throws Exception {
		CustomUserDetails			userDetails						= null;
		List<TyreExpenses>			tyreExpensesList				= null;
		ValueObject 				valueObject						= null;
		try {
			valueObject					= new ValueObject();
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreExpensesList 			= tyreExpensesRepository.findAllByCompanyId(userDetails.getCompany_id());
			
			valueObject.put("tyreExpensesList", tyreExpensesList);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;     
			tyreExpensesList				= null;     
			valueObject						= null;     
		}
	}
	@Override
	public ValueObject getAllTyreExpenses2(String search) throws Exception {
		CustomUserDetails			userDetails						= null;
		List<TyreExpenses>			tyreExpensesList				= null;
		TyreExpenses 				tyreExpenses 					= null;
		ValueObject 				valueObject						= null;
		TypedQuery<Object[]> 		query							= null;
		List<Object[]> 				results							= null;	
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject					= new ValueObject();
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			query = entityManager.createQuery(
					"SELECT TE.tyreExpenseId, TE.tyreExpenseName FROM TyreExpenses AS TE "
					+ "WHERE lower(TE.tyreExpenseName) Like ('%" + search + "%') AND TE.companyId = "+userDetails.getCompany_id()+" AND TE.markForDelete = 0", Object[].class);
			query.setFirstResult(0);
			query.setMaxResults(20);
			
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				tyreExpensesList = new ArrayList<TyreExpenses>();
				
				for (Object[] result : results) {
					tyreExpenses = new TyreExpenses();
					tyreExpenses.setTyreExpenseId((Integer) result[0]);
					tyreExpenses.setTyreExpenseName((String) result[1]);
					tyreExpensesList.add(tyreExpenses);
				}
			}
			
			
			valueObject.put("tyreExpensesList", tyreExpensesList);
			}
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;    
			tyreExpensesList				= null;    
			tyreExpenses 					= null;    
			valueObject						= null;    
			query							= null;    
			results							= null;	   
		}
	}
	
	@Transactional
	public TyreExpenses validateTyreExpense(String tyreExpenseName , Integer companyId) throws Exception {
		try {
			return tyreExpensesRepository.findByName(tyreExpenseName,companyId);
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
		
	}
	
	@Override
	public ValueObject saveTyreExpense(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		TyreExpenses				tyreExpenses					= null;
		TyreExpenses 				validateTyreExpenses			= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			validateTyreExpenses		=	validateTyreExpense(valueObject.getString("addTyreExpenseName"),userDetails.getCompany_id());
			
			if(validateTyreExpenses != null) {
				valueObject.put("alreadyExist", true);
			}else {
				tyreExpenses = tyreExpensesBL.prepareTyreExpense(valueObject, userDetails);
				tyreExpensesRepository.save(tyreExpenses);
				valueObject.put("save", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;  
			tyreExpenses					= null;  
			validateTyreExpenses			= null;  
		}
	}
	
	@Override
	public ValueObject getTyreExpenseByTyreExpenseId(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		TyreExpenses				tyreExpenses					= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreExpenses 			= tyreExpensesRepository.findByTyreExpenseId(valueObject.getInt("tyreExpenseId"), userDetails.getCompany_id());
			valueObject.put("tyreExpensesDto", tyreExpenses);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;     
			tyreExpenses					= null;     
		}
	}
	
	@Override
	@Transactional
	public ValueObject updateTyreExpense(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		TyreExpenses 				validateTyreExpenses			= null;
		TyreExpenses 				tyreExpense						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreExpense					= tyreExpensesRepository.findByTyreExpenseId(valueObject.getInt("editTyreExpenseId"),userDetails.getCompany_id());
			
			if(valueObject.getString("editTyreExpenseName").equalsIgnoreCase(tyreExpense.getTyreExpenseName().trim())) {
				tyreExpensesRepository.updateTyreExpenseByExpenseId(valueObject.getInt("editTyreExpenseId"),tyreExpense.getTyreExpenseName(), valueObject.getString("editDescription"),userDetails.getCompany_id());
			}else {
				validateTyreExpenses		= validateTyreExpense(valueObject.getString("editTyreExpenseName"),userDetails.getCompany_id());
				if(validateTyreExpenses != null) {
					valueObject.put("alreadyExist", true);
				}else {
					tyreExpensesRepository.updateTyreExpenseByExpenseId(valueObject.getInt("editTyreExpenseId"),valueObject.getString("editTyreExpenseName"), valueObject.getString("editDescription"),userDetails.getCompany_id());
				}
			}
		
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;   
			validateTyreExpenses			= null;   
			tyreExpense						= null;   
		}
	}
	
	@Transactional
	@Override
	public ValueObject deleteTyreExpense(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreExpensesRepository.deleteTyreExpenseByExpenseId(valueObject.getInt("deleteTyreExpenseId"),userDetails.getCompany_id());
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;
		}
	}

}
