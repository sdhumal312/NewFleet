package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.TripExpenseRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripExpenseDto;
import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("TripExpenseService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TripExpenseService implements ITripExpenseService {

	private static final int PAGE_SIZE = 25;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private TripExpenseRepository TripExpenseDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addTripExpense(TripExpense status) throws Exception {
		TripExpenseDao.save(status);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public TripExpense saveTripExpenses(TripExpense status) throws Exception {
		return TripExpenseDao.save(status);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateTripExpense(TripExpense tripExpense) throws Exception {
		TripExpenseDao.updateTripExpense(tripExpense.getExpenseName(), tripExpense.getExpenseRemarks(),
				tripExpense.getLastModifiedBy(), tripExpense.getLastupdated(), tripExpense.getExpenseID(), tripExpense.getCompanyId(), tripExpense.getIncldriverbalance());
	}

	@Transactional
	public List<TripExpense> listTripExpense() throws Exception {
		return TripExpenseDao.findAll();
	}

	@Transactional
	public TripExpense getTripExpense(int ExpenseID, Integer companyId) throws Exception {
		return TripExpenseDao.getTripExpense(ExpenseID, companyId);
	}

	@Transactional
	public void deleteTripExpense(Integer status) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TripExpenseDao.deleteTripExpense(status, userDetails.getCompany_id());
	}

	@Transactional
	public List<TripExpense> listOnlyExpenseName() throws Exception {
		return TripExpenseDao.findAll();
	}

	@Transactional
	public List<TripExpense> ValidateTripExpense(String ExpenseNAME, Integer companyId) throws Exception {

		return TripExpenseDao.ValidateTripExpense(ExpenseNAME, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripExpenseService#
	 * SearchTripExpense_DropDown(java.lang.String)
	 */
	@Transactional
	public List<TripExpense> SearchTripExpense_DropDown(String ExpenseNAME, Integer companyId) throws Exception {
									
		List<TripExpense> Dtos = null;
		if(ExpenseNAME != null && !ExpenseNAME.trim().equalsIgnoreCase("") && ExpenseNAME.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> query = entityManager
				.createQuery("SELECT f.expenseID, f.expenseName from TripExpense as f "
						+ "where lower(f.expenseName) Like ('" + ExpenseNAME + "%')  AND f.companyId = "+companyId+" AND f.markForDelete = 0", Object[].class);
		List<Object[]> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripExpense>();
			TripExpense user = null;
			for (Object[] result : results) {
				user = new TripExpense();

				user.setExpenseID((Integer) result[0]);
				user.setExpenseName((String) result[1]);

				Dtos.add(user);
			}
		}
		}
		return Dtos;
	}

	@Override
	public List<TripExpense> listTripExpense(Integer companyId) throws Exception {
		try {
			return TripExpenseDao.listTripExpense(companyId);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Page<TripExpense> getDeployment_Page_TripExpense(Integer pageNumber, CustomUserDetails userDetails) {

		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "expenseID");
		return TripExpenseDao.getDeployment_Page_TripExpense(userDetails.getCompany_id(),  request);
	}

	@Override
	public List<TripExpense> GET_list_Of_TripExpense(Integer pageNumber, CustomUserDetails userDetails) {

		TypedQuery<TripExpense> query = null;
		query = entityManager.createQuery("FROM TripExpense as v where v.companyId = " + userDetails.getCompany_id()
				+ "  AND v.markForDelete = 0 ORDER BY v.expenseID desc", TripExpense.class);

		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		return query.getResultList();
	}
	
	@Override
	public List<TripExpenseDto> listTripExpenseForCompare(Integer companyId) throws Exception {
		TypedQuery<Object[]> query = null;

			query = entityManager.createQuery(
					"SELECT distinct te.expenseId, e.expenseName  "
					+ " from TripSheetExpense te "
					+ " inner join TripExpense e on e.expenseID = te.expenseId AND e.companyId = te.companyId"
					+ " where te.companyId = "+companyId+" and te.markForDelete = 0 order by e.expenseName asc",
					Object[].class);
			
			List<Object[]> results = query.getResultList();

			List<TripExpenseDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripExpenseDto>();
				TripExpenseDto select = null;
				for (Object[] vehicle : results) {

					select = new TripExpenseDto();
					select.setExpenseID((Integer) vehicle[0]);
					select.setExpenseName((String) vehicle[1]);

					Dtos.add(select);
				}
			}
			return Dtos;
	}
	
	@Override
	public TripExpense getTripExpenseByName(String name, Integer companyId) throws Exception {
		try {

			
			Query query = entityManager.createQuery(
					"SELECT f.expenseID, f.expenseName from TripExpense AS f "
					+ " where  f.expenseName = '"+name+"' AND f.companyId = "
							+ companyId + " AND f.markForDelete = 0");

			Object[] vehicle = null;
			try {
				vehicle = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			TripExpense select;
			if (vehicle != null) {
				select = new TripExpense();
				
				select.setExpenseID((Integer) vehicle[0]);
				select.setExpenseName((String) vehicle[1]);
			} else {
				return null;
			}
			return select;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	@Override
	public List<TripExpense> Search_ExpenseName_select(String search, Integer companyId) throws Exception {
		List<TripExpense> Dtos = null;
		if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> query = entityManager
				.createQuery("SELECT te.expenseID, te.expenseName from TripExpense AS te where lower(te.expenseName) Like ('%"
						+ search + "%') and te.companyId = "+companyId+" and te.markForDelete = 0", Object[].class);

		List<Object[]> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripExpense>();

			TripExpense TripExpense = null;
			for (Object[] result : results) {
				TripExpense = new TripExpense();
				TripExpense.setExpenseID((Integer) result[0]);
				TripExpense.setExpenseName((String) result[1]);
				
				Dtos.add(TripExpense);
			}
		}
		}
		return Dtos;

	}
	
	@Override
	public ValueObject getTripExpenseList(ValueObject object) throws Exception {
		List<TripExpense> 	TripExpenseList 			= null;
		int					companyId					= 0;
		try {

			TripExpenseList = new ArrayList<TripExpense>();
			companyId		= object.getInt("companyId");

			for (TripExpense Expense : listTripExpense(companyId)) {

				TripExpense bean = new TripExpense();
				bean.setExpenseID(Expense.getExpenseID());
				bean.setExpenseName(Expense.getExpenseName());

				TripExpenseList.add(bean);
			}

			object.put("TripExpenseList", TripExpenseList);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			TripExpenseList  = null;
			object  	 	 = null;
		}
	}
}
