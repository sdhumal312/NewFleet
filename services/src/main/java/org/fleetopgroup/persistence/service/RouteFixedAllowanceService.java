package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.RouteFixedAllowanceRpository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RouteFixedAllowanceDto;
import org.fleetopgroup.persistence.model.RouteFixedAllowance;
import org.fleetopgroup.persistence.serviceImpl.IRouteFixedAllowanceService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RouteFixedAllowanceService implements IRouteFixedAllowanceService {
	
	@PersistenceContext EntityManager entityManager;
	@Autowired	private	RouteFixedAllowanceRpository		routeFixedAllowanceRpository;

	@Override
	public ValueObject saveAllowanceDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails				userDetails		= null;
		List<RouteFixedAllowance>		validate		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			validate	= routeFixedAllowanceRpository.validateAllowanceForRoute(valueObject.getInt("driJobId",0), valueObject.getInt("routeId",0));
			
			if(validate == null || validate.isEmpty()) {
				
				RouteFixedAllowance		allowance	= new RouteFixedAllowance();
				
				allowance.setRouteId(valueObject.getInt("routeId",0));
				allowance.setDriJobId(valueObject.getInt("driJobId",0));
				allowance.setAmount(valueObject.getDouble("amount",0));
				allowance.setExpenseId(valueObject.getInt("expenseId",0));
				allowance.setCompanyId(userDetails.getCompany_id());
				allowance.setCreated(new Date());
				allowance.setLastUpdatdOn(new Date());
				allowance.setCreatedById(userDetails.getId());
				allowance.setLastUpdatdById(userDetails.getId());
				
				routeFixedAllowanceRpository.save(allowance);
				
				valueObject.put("saved", true);
				
			}else {
				valueObject.put("duplicate", true);
			}
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject removeAllowanceDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			routeFixedAllowanceRpository.removeAllowanceDetails(valueObject.getLong("routeFixedAllowanceId",0), userDetails.getId(), new Date());
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails	= null;
		}
	}
	
	@Override
	public List<RouteFixedAllowance> getRouteFixedAllowanceList(Integer routeId, Integer companyId) throws Exception {
		// 
		return routeFixedAllowanceRpository.getRouteFixedAllowanceList(routeId, companyId);
	}
	
	@Override
	public List<RouteFixedAllowanceDto> getRouteFixedAllowanceList(Integer routeId) throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT RA.routeFixedAllowanceId, RA.expenseId, RA.amount, RA.driJobId, RA.routeId, RA.companyId, RA.created, RA.lastUpdatdOn,"
							+ " RA.createdById, RA.lastUpdatdById, TR.routeName, TE.expenseName, DT.driJobType "
							+ " FROM RouteFixedAllowance AS RA "
							+ " INNER JOIN TripRoute TR ON TR.routeID = RA.routeId"
							+ " INNER JOIN TripExpense TE ON TE.expenseID = RA.expenseId"
							+ " INNER JOIN DriverJobType DT ON DT.driJobId = RA.driJobId "
							+ " where RA.routeId = "+routeId+" AND RA.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<RouteFixedAllowanceDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					RouteFixedAllowanceDto	allowance = new RouteFixedAllowanceDto();
					
					allowance.setRouteFixedAllowanceId((Long) result[0]);
					allowance.setExpenseId((Integer) result[1]);
					allowance.setAmount((Double) result[2]);
					allowance.setDriJobId((Integer) result[3]);
					allowance.setRouteId((Integer) result[4]);
					allowance.setCompanyId((Integer) result[5]);
					allowance.setCreated((Date) result[6]);
					allowance.setLastUpdatdOn((Date) result[7]);
					allowance.setCreatedById((Long) result[8]);
					allowance.setLastUpdatdById((Long) result[9]);
					allowance.setRouteName((String) result[10]);
					allowance.setExpenseType((String) result[11]);
					allowance.setDriverJobType((String) result[12]);
					
					list.add(allowance);
				}
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
}