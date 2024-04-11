/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.bl.TripRouteExpenseRangeBL;
import org.fleetopgroup.persistence.dao.TripExpenseRepository;
import org.fleetopgroup.persistence.dao.TripRouteExpenseRangeRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripRouteExpenseRangeDto;
import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.persistence.model.TripRouteExpenseRange;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteExpenseRangeService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
/**
 * @author fleetop
 *
 */
@Service("TripRouteExpenseRangeService")
@Transactional
public class TripRouteExpenseRangeService implements ITripRouteExpenseRangeService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext EntityManager 							entityManager;
	
	@Autowired private 	TripRouteExpenseRangeRepository 		tripRouteExpenseRangeRepository;
	
	@Autowired private 	TripExpenseRepository 					tripExpenseRepository;
	
	
	
	TripRouteExpenseRangeBL	tripRouteExpenseRangeBL 		= new TripRouteExpenseRangeBL();
	
	SimpleDateFormat		SQLdateFormat 				= new SimpleDateFormat("dd-MM-yyyy");
	private static final int PAGE_SIZE 					= 100;
	
	
	public Page<TripRouteExpenseRange> getDeployment_Page_TripRouteExpenseRange(Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "tripRouteExpenseRangeId");
		return tripRouteExpenseRangeRepository.getDeployment_Page_TripRouteExpenseRange(companyId, pageable);
	}
	
	public Page<TripExpense> getDeployment_Page_TripExpense(Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "expenseID");
		return tripExpenseRepository.getDeployment_Page_TripExpense(companyId, pageable);
	}
	
	@Override
	public ValueObject getTripExpenseListToSetRouteWiseExpenseRange(ValueObject valueInObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		List<TripRouteExpenseRangeDto>	tripRouteExpenseRangeList		= null;
		TripRouteExpenseRangeDto		tripRouteExpenseRangeDto		= null;
		ValueObject 					valueObject						= null;
		TypedQuery<Object[]> 			query							= null;
		List<Object[]> 					results							= null;	
		Integer				  			pageNumber						= null;
		try {
			
			valueObject					= new ValueObject();
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber					= valueInObject.getInt("pageNumber",0);
			Page<TripExpense> page = getDeployment_Page_TripExpense(pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			valueObject.put("SelectPage", pageNumber);
			
			
			query = entityManager.createQuery(
					"SELECT TE.expenseID, TE.expenseName,TRER.expenseMaxLimt, TRER.tripRouteExpenseRangeId"
							+ " FROM TripExpense AS TE "
							+ " LEFT JOIN TripRouteExpenseRange AS TRER ON TRER.expenseId = TE.expenseID AND TRER.routeId = "+valueInObject.getInt("routeId")+" "
							+ " WHERE TE.companyId = "+userDetails.getCompany_id()+"  AND TE.markForDelete = 0", Object[].class);
			
			query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			query.setMaxResults(PAGE_SIZE);
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				tripRouteExpenseRangeList 	= new ArrayList<TripRouteExpenseRangeDto>();
				
				for (Object[] result : results) {
					tripRouteExpenseRangeDto 		= new TripRouteExpenseRangeDto();
					tripRouteExpenseRangeDto.setTripExpenseId((Integer)result[0]);
					tripRouteExpenseRangeDto.setExpenseName((String)result[1]);
						
					if(result[2] != null) {
						tripRouteExpenseRangeDto.setExpenseMaxLimt((Double)result[2]);
					}else {
						tripRouteExpenseRangeDto.setExpenseMaxLimt(0.0);
					}
					
					if(result[3] != null) {
						tripRouteExpenseRangeDto.setTripRouteExpenseRangeId((Long)result[3]);
					}
					
					tripRouteExpenseRangeList.add(tripRouteExpenseRangeDto);
				}
			}
			
			valueObject.put("tripRouteExpenseRangeList", tripRouteExpenseRangeList);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject saveTripRouteExpenseRange(ValueObject valueObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		List<TripRouteExpenseRange>		tripRouteExpenseRangeList		= null;
		ArrayList<ValueObject>			dataArrayObjColl				= null;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dataArrayObjColl 				= new ArrayList<ValueObject>();
			tripRouteExpenseRangeList 		= new ArrayList<TripRouteExpenseRange>();
			
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("finalList");
			
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				for (ValueObject object : dataArrayObjColl) {
					tripRouteExpenseRangeList.add(tripRouteExpenseRangeBL.prepareTripRouteExpenseRange(object, userDetails));
				}
				
				tripRouteExpenseRangeRepository.saveAll(tripRouteExpenseRangeList);
			
			valueObject.put("save", true);
			}
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;  
			tripRouteExpenseRangeList		= null; 
			dataArrayObjColl				= null; 
		}
	}
	
	
	
	@Transactional
	public ValueObject getExpenseRangeByRouteIdAndExpenseId(ValueObject valueObject) throws Exception {
		TripRouteExpenseRange			tripRouteExpenseRange 			= null;
		CustomUserDetails				userDetails						= null;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tripRouteExpenseRange = tripRouteExpenseRangeRepository.getExpenseRangeByRouteIdAndExpenseId(valueObject.getInt("routeId",0),valueObject.getInt("expenseId",0),userDetails.getCompany_id());
			 valueObject.put("tripRouteExpenseRange", tripRouteExpenseRange);
			 if(tripRouteExpenseRange != null) {
				 valueObject.put("maxAmount", tripRouteExpenseRange.getExpenseMaxLimt());
			 }else {
				 valueObject.put("maxAmount", 0.0);
			 }
			 
			 return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
		
	}
	
	@Override
	public HashMap<Integer, Double> getExpenseWiseMaxLimit(Integer routeId) throws Exception {
		List<TripRouteExpenseRange> 	rangeList	= null;
		HashMap<Integer, Double>		expenseHM	= null;
		try {
			rangeList	= tripRouteExpenseRangeRepository.findByRouteId(routeId);
			if(rangeList != null && !rangeList.isEmpty()) {
				expenseHM	= new HashMap<>();
				for(TripRouteExpenseRange expenseRange : rangeList) {
					expenseHM.put(expenseRange.getExpenseId(), expenseRange.getExpenseMaxLimt());
				}
			}
			
			return expenseHM;
		} catch (Exception e) {
			throw e;
		}
	}
	

}
