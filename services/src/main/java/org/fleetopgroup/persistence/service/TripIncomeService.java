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
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.bl.TripIncomeBL;
import org.fleetopgroup.persistence.dao.TripIncomeRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TripIncome;
import org.fleetopgroup.persistence.serviceImpl.ITripIncomeService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("TripIncomeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TripIncomeService implements ITripIncomeService {

	private static final int PAGE_SIZE = 25;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private TripIncomeRepository TripIncomeDao;
	
	TripIncomeBL	tripIncomeBL 		= new TripIncomeBL();

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addTripIncome(TripIncome status) throws Exception {
		TripIncomeDao.save(status);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateTripIncome(TripIncome tripIncome) throws Exception {
		TripIncomeDao.updateTripIncome(tripIncome.getIncomeName(), tripIncome.getIncomeRemarks(),
				tripIncome.getLastModifiedBy(), tripIncome.getLastupdated(), tripIncome.getIncomeID(), tripIncome.getCompanyId());
	}

	@Transactional
	public List<TripIncome> listTripIncome() throws Exception {
		return TripIncomeDao.findAll();
	}

	@Transactional
	public TripIncome getTripIncome(int IncomeID, Integer companyId) throws Exception {
		return TripIncomeDao.getTripIncome(IncomeID, companyId);
	}

	@Transactional
	public void deleteTripIncome(Integer status, Integer companyId) throws Exception {
		TripIncomeDao.deleteTripIncome(status, companyId);
	}

	@Transactional
	public List<TripIncome> listOnlyIncomeName(Integer companyId) throws Exception {
		return TripIncomeDao.listTripIncome(companyId);
	}

	@Transactional
	public TripIncome validateTripIncome(String IncomeNAME, Integer companyId) throws Exception {

		return TripIncomeDao.validateTripIncome(IncomeNAME, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripIncomeService#
	 * SearchTripIncome_DropDown(java.lang.String)
	 */
	@Transactional
	public List<TripIncome> SearchTripIncome_DropDown(String IncomeNAME, Integer companyId) throws Exception {
		List<TripIncome> Dtos = null;
		if(IncomeNAME != null && !IncomeNAME.trim().equalsIgnoreCase("") && IncomeNAME.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> query = entityManager.createQuery("SELECT f.incomeID, f.incomeName from TripIncome as f "
				+ "where lower(f.incomeName) Like ('" + IncomeNAME + "%')  AND f.companyId = "+companyId+" AND f.markForDelete = 0", Object[].class);
		List<Object[]> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripIncome>();
			TripIncome user = null;
			for (Object[] result : results) {
				user = new TripIncome();

				user.setIncomeID((Integer) result[0]);
				user.setIncomeName((String) result[1]);

				Dtos.add(user);
			}
		}
		}
		return Dtos;
	}

	@Override
	@Transactional
	public List<TripIncome> listTripIncome(Integer companyId) throws Exception {
		try {
			return TripIncomeDao.listTripIncome(companyId);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public List<TripIncome> listOnlyIncomeName_FixedIncome(Integer companyId) {
		
		return TripIncomeDao.listOnlyIncomeName_FixedIncome(companyId);
	}
	
	@Override
	@Transactional
	public List<TripIncome> listTrip_ROUTE_Income(Integer companyId) throws Exception {

		return TripIncomeDao.listTrip_ROUTE_Income(companyId);
	}

	@Override
	@Transactional
	public List<TripIncome> listTrip_CASHBOOK_Income(Integer companyId) throws Exception {

		return TripIncomeDao.listTrip_CASHBOOK_Income(companyId);
	}

	@Override
	public Page<TripIncome> getDeployment_Page_TripIncome(Integer incomeType, Integer pageNumber, CustomUserDetails userDetails) {
		
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "incomeID");
		return TripIncomeDao.getDeployment_Page_TripIncome(userDetails.getCompany_id(), incomeType, request);
	}

	@Override
	public List<TripIncome> GET_list_Of_TripIncome(Integer incomeType, Integer pageNumber, CustomUserDetails userDetails) {
		
		TypedQuery<TripIncome> query = null;
		query = entityManager.createQuery("FROM TripIncome as v where v.companyId = " + userDetails.getCompany_id()
				+ " AND v.incomeType="+incomeType+" AND v.markForDelete = 0 ORDER BY v.incomeID desc", TripIncome.class);

		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		return query.getResultList();
	}
	
	@Override
	public ValueObject mobileTripIncomeList(ValueObject object) throws Exception {
		List<TripIncome> 	TripIncomeList 			= null;
		int					companyId				= 0;
		try {

			TripIncomeList = new ArrayList<TripIncome>();
			companyId		= object.getInt("companyId");

			for (TripIncome Income : listOnlyIncomeName(companyId)) {

				TripIncome bean = new TripIncome();
				bean.setIncomeName(Income.getIncomeName());
				bean.setIncomeID(Income.getIncomeID());

				TripIncomeList.add(bean);
			}

			object.put("TripIncomeList", TripIncomeList);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			TripIncomeList   = null;
			object  	 	 = null;
		}
	}
	
	@Override
	public ValueObject saveTripIncomeWithRate(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		TripIncome					tripIncome						= null;
		try {
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tripIncome 		= tripIncomeBL.prepareTripIncomeWithRate(valueObject, userDetails);
			
			TripIncomeDao.save(tripIncome);
			
			valueObject.put("save", true);
			return valueObject;
			}
		 catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;  
		}
	}
	
	@Override
	public ValueObject getTripIncomeById(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		TripIncome					tripIncome						= null;
		try {
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			tripIncome 		= TripIncomeDao.getTripIncome(valueObject.getInt("incomeID"), userDetails.getCompany_id());
			valueObject.put("tripIncome", tripIncome);
			return valueObject;
			}
		 catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;  
		}
	}
	@Transactional
	@Override
	public ValueObject updateTripIncomeWithRate(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripIncomeDao.updateTripIncomeWithRate(valueObject.getString("incomeName"),valueObject.getString("discription"),valueObject.getDouble("commision"),valueObject.getDouble("gst"),userDetails.getId(), valueObject.getInt("incomeID"),userDetails.getCompany_id());
			return valueObject;
		}
		catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;  
		}
	}
	
	@Override
	public List<TripIncome> getTripIncomeByName(String vehicle, Integer companyId) throws Exception {
		try {
			List<TripIncome> Dtos = null;
			if(vehicle != null && !vehicle.trim().equalsIgnoreCase("") && vehicle.indexOf('\'') != 0 ) {
			TypedQuery<Object[]> queryt  = entityManager.createQuery("SELECT v.incomeID, v.incomeName "
					+ " FROM TripIncome AS v "
					+ " where  lower(v.incomeName) Like ('%" + vehicle
					+ "%') and v.companyId = " + companyId + " ",
					Object[].class);
			
			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripIncome>();
				TripIncome dropdown = null;
				for (Object[] result : results) {
					dropdown = new TripIncome();

					dropdown.setIncomeID((Integer) result[0]);
					dropdown.setIncomeName((String) result[1]);

					Dtos.add(dropdown);
				}
			}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
}