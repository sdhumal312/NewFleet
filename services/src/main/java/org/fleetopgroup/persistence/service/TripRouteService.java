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

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripRouteFixedType;
import org.fleetopgroup.persistence.dao.TripRouteFixedExpenseRepository;
import org.fleetopgroup.persistence.dao.TripRouteFixedIncomeRepository;
import org.fleetopgroup.persistence.dao.TripRouteRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripRouteDto;
import org.fleetopgroup.persistence.dto.TripRoutefixedExpenseDto;
import org.fleetopgroup.persistence.dto.TripRoutefixedIncomeDto;
import org.fleetopgroup.persistence.model.TripRoute;
import org.fleetopgroup.persistence.model.TripRoutefixedExpense;
import org.fleetopgroup.persistence.model.TripRoutefixedIncome;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("TripRouteService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TripRouteService implements ITripRouteService {

	@PersistenceContext
	EntityManager entityManager;

	private static final int PAGE_SIZE = 10;

	@Autowired
	private TripRouteRepository TripRouteDao;

	@Autowired
	private TripRouteFixedExpenseRepository TripRouteFixedExpenseDao;

	@Autowired
	private TripRouteFixedIncomeRepository TripRouteFixedIncomeDao;

	@Autowired
	private ICompanyConfigurationService companyConfigurationService;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public TripRoute addTripRoute(TripRoute status) throws Exception {
		TripRoute tripRoute = null;
		try {
			tripRoute = 	TripRouteDao.save(status);
			return tripRoute;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateTripRoute(TripRoute status) throws Exception {
		TripRouteDao.save(status);
	}

	@Transactional
	public TripRoute getTripRoute(int RouteID) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return TripRouteDao.getTripRoute(RouteID, userDetails.getCompany_id());
	}
	
	@Override
	public TripRouteDto getTripRouteDetails(int RouteID, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT T.routeID, T.routeType, T.routeNumber, T.routeName, T.routeNo, T.routeApproximateKM, T.routeTimeFrom,"
				+ " T.routeTimeTo, T.routeTotalHour, T.routeTotalLiter, T.routrAttendance, T.vehicleGroupId, T.routeRemarks, TR.routeName,"
				+ " T.mainRouteId "
				+ " FROM TripRoute T "
				+ " LEFT JOIN TripRoute TR ON TR.routeID = T.mainRouteId" 
				+ " WHERE T.routeID =:id AND T.companyId =:companyId AND T.markForDelete = 0");

		query.setParameter("id", RouteID);
		query.setParameter("companyId", companyId);
		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		TripRouteDto select;
		if (result != null) {
			select = new TripRouteDto();
			select.setRouteID((Integer) result[0]);
			select.setRouteType((Integer) result[1]);
			select.setRouteNumber((Integer) result[2]);
			select.setRouteName((String) result[3]);
			select.setRouteNo((String) result[4]);
			select.setRouteApproximateKM((Integer) result[5]);
			select.setRouteTimeFrom((String) result[6]);
			select.setRouteTimeTo((String) result[7]);
			select.setRouteTotalHour((Double) result[8]);
			select.setRouteTotalLiter((Double) result[9]);
			select.setRoutrAttendance((Double) result[10]);
			select.setVehicleGroupId((long) result[11]);
			select.setRouteRemarks((String) result[12]);
			select.setMainRoute((String) result[13]);
			select.setMainRouteId((Integer) result[14]);
			if(select.getMainRouteId() == null) {
				select.setMainRouteId(0);
			}


		} else {
			return null;
		}

		return select;
	}

	@Transactional
	public void deleteTripRoute(TripRoute status) throws Exception {
		TripRouteDao.deleteTripRoute(status.getRouteID(), status.getCompanyId());
	}

	@Transactional
	public List<TripRoute> SearchOnlyRouteNAME(String Search, CustomUserDetails userDetails) throws Exception {
		TypedQuery<TripRoute> query = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		if (!companyConfigurationService.getVehicleGroupWiseRoutePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"from TripRoute WHERE (lower(routeName) Like (:Search) OR  lower(routeNo) Like (:Search))"
					+ " and companyId = " + userDetails.getCompany_id() + " and markForDelete = 0",
					TripRoute.class);
		} else {
			query = entityManager.createQuery("SELECT TR from TripRoute TR"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = TR.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" 
					+ " WHERE (lower(TR.routeName) Like (:Search) OR  lower(TR.routeNo) Like (:Search)) and TR.companyId = "
					+ userDetails.getCompany_id() + " and TR.markForDelete = 0", TripRoute.class);
		}
		query.setParameter("Search", "%"+Search+"%");
		return query.getResultList();
		}else {
			return null;
		}
	}

	@Transactional
	public void addTripRouteFixedExpene(TripRoutefixedExpense fixedExpense) throws Exception {

		TripRouteFixedExpenseDao.save(fixedExpense);

	}

	@Transactional
	public List<TripRoutefixedExpense> listTripRouteFixedExpenes(Integer routeID, Integer compId) throws Exception {

		
		// return TripRouteFixedExpenseDao.listTripRouteFixedExpene(routeID);
		TypedQuery<TripRoutefixedExpense> query = entityManager
				.createQuery(
						"FROM TripRoutefixedExpense WHERE routeID =" + routeID
								+ " and markForDelete = 0 AND companyId = " + compId + "",
						TripRoutefixedExpense.class);
		return query.getResultList();
	}
	/*get the list of Fixed Route Income List*/
	@Transactional
	public List<TripRoutefixedIncome> listTripRouteFixedIncomeList(Integer routeID, int companyId) throws Exception {

		TypedQuery<TripRoutefixedIncome> query = entityManager
				.createQuery(
						"FROM TripRoutefixedIncome WHERE routeID =" + routeID
						+ " and markForDelete = 0 AND companyId = " + companyId + "",
						TripRoutefixedIncome.class);
		return query.getResultList();
	}
	

	@Override
	public List<TripRoutefixedExpenseDto> listTripRouteFixedExpene(Integer routeID, Integer companyId) throws Exception {
		TypedQuery<Object[]> query = entityManager
				.createQuery(
						"SELECT TFX.routefixedID, TE.expenseName, TFX.fixedTypeId, B.branch_name, TFX.expenseRefence"
						+ ", TFX.expenseAmount, TFX.expenseId  FROM TripRoutefixedExpense TFX "
						+ " INNER JOIN TripExpense TE ON TE.expenseID = TFX.expenseId "
						+ " INNER JOIN Branch B ON B.branch_id = TFX.expensePlaceId"
						+ " WHERE routeID =" + routeID
								+ " and TFX.markForDelete = 0 AND TFX.companyId = " + companyId + "",
								Object[].class);
		List<Object[]> results = query.getResultList();
		
		List<TripRoutefixedExpenseDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripRoutefixedExpenseDto>();
			TripRoutefixedExpenseDto list = null;
			for (Object[] result : results) {
				list = new TripRoutefixedExpenseDto();

				list.setRoutefixedID((Integer) result[0]);
				list.setExpenseName((String) result[1]);
				list.setExpenseFixed(TripRouteFixedType.getFixedTypeName((short) result[2]));
				list.setFixedTypeId((short) result[2]);
				list.setExpensePlace((String) result[3]);
				list.setExpenseRefence((String) result[4]);
				list.setExpenseAmount((Double) result[5]);
				list.setExpenseId((Integer) result[6]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	@Override
	public List<TripRoutefixedExpenseDto> listTripRouteFixedExpeneList(Integer routeID, Long tripSheetId, Integer companyId)
			throws Exception {
		TypedQuery<Object[]> query = entityManager
				.createQuery(
						"SELECT TFX.routefixedID, TE.expenseName, TFX.fixedTypeId, B.branch_name, TFX.expenseRefence"
						+ ", TFX.expenseAmount, TFX.expenseId, TDE.expenseAmount, TDE.tripExpenseID  FROM TripRoutefixedExpense TFX "
						+ " INNER JOIN TripExpense TE ON TE.expenseID = TFX.expenseId "
						+ " INNER JOIN Branch B ON B.branch_id = TFX.expensePlaceId "
						+ " LEFT JOIN TripDailyExpense TDE ON TDE.expenseId = TFX.expenseId AND TDE.tripDailysheet.TRIPDAILYID = "+tripSheetId+""
						+ " WHERE routeID =" + routeID
								+ " and TFX.markForDelete = 0 AND TFX.companyId = " + companyId + "",
								Object[].class);
		List<Object[]> results = query.getResultList();
		
		List<TripRoutefixedExpenseDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripRoutefixedExpenseDto>();
			TripRoutefixedExpenseDto list = null;
			for (Object[] result : results) {
				list = new TripRoutefixedExpenseDto();

				list.setRoutefixedID((Integer) result[0]);
				list.setExpenseName((String) result[1]);
				list.setExpenseFixed(TripRouteFixedType.getFixedTypeName((short) result[2]));
				list.setFixedTypeId((short) result[2]);
				list.setExpensePlace((String) result[3]);
				list.setExpenseRefence((String) result[4]);
				list.setExpenseAmount((Double) result[5]);
				list.setExpenseId((Integer) result[6]);
				
				if(result[7] != null) {
					list.setExpenseAmount((Double) result[7]);
					list.setAlreadyExpense(true);
				}
				list.setTripExpenseID((Long) result[8]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	@Transactional
	public List<TripRoutefixedExpense> ValidateTripRouteFixedExpene(Integer expenseId, Integer routeID,
			Integer companyId) throws Exception {

		TypedQuery<TripRoutefixedExpense> query = entityManager
				.createQuery(
						"FROM TripRoutefixedExpense WHERE routeID =" + routeID + " AND expenseId=" + expenseId
								+ " AND companyId = " + companyId + " AND markForDelete = 0",
						TripRoutefixedExpense.class);
		return query.getResultList();
	}

	@Transactional
	public TripRoutefixedExpense getTripRoutefixedExpense(Integer RoutefixedID, Integer companyId) throws Exception {

		return TripRouteFixedExpenseDao.getTripRoutefixedExpense(RoutefixedID, companyId);
	}

	@Transactional
	public void deleteTripRoutefixedExpense(Integer fixedExpense, Integer companyId) throws Exception {

		TripRouteFixedExpenseDao.deleteTripRoutefixedExpense(fixedExpense, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripRouteService#
	 * getDeployment_Page_TripRoute(java.lang.Integer)
	 */
	@Transactional
	public Page<TripRoute> getDeployment_Page_TripRoute(Integer pageNumber, CustomUserDetails userDetails)
			throws Exception {

		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "routeID");
		if (!companyConfigurationService.getVehicleGroupWiseRoutePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return TripRouteDao.findAllByCompanyId(userDetails.getCompany_id(), request);
		} else {
			return TripRouteDao.findAllByCompanyId(userDetails.getCompany_id(), userDetails.getId(), request);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripRouteService#
	 * GET_list_Of_TripRoute(java.lang.Integer)
	 */
	@Transactional
	public List<TripRoute> GET_list_Of_TripRoute(Integer pageNumber, CustomUserDetails userDetails) throws Exception {

		TypedQuery<TripRoute> query = null;
		if (!companyConfigurationService.getVehicleGroupWiseRoutePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery("FROM TripRoute as v where v.companyId = " + userDetails.getCompany_id()
					+ "and v.markForDelete = 0 ORDER BY v.routeID desc", TripRoute.class);
		} else {
			query = entityManager.createQuery("SELECT v FROM TripRoute as v "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " where v.companyId = " + userDetails.getCompany_id()
					+ "and v.markForDelete = 0 ORDER BY v.routeID desc", TripRoute.class);

		}
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripRouteService#
	 * Get_TripSheet_Id_to_get_TripRoute(java.lang.Long)
	 */

	@Override
	public TripRoute validateTripRoute(String name, Integer companyId) throws Exception {
		try {
			return TripRouteDao.validateTripRoute(name, companyId);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<TripRoute> listTripRoute(CustomUserDetails userDetails) throws Exception {
		try {
			if (!companyConfigurationService.getVehicleGroupWiseRoutePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return TripRouteDao.listTripRoute(userDetails.getCompany_id());
			} else {
				return TripRouteDao.listTripRoute(userDetails.getCompany_id(), userDetails.getId());
			}

		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public List<TripRoute> SearchOnlyRoute_MAIN_ROUTE_NAME(String Search, CustomUserDetails userDetails)
			throws Exception {

		TypedQuery<TripRoute> query = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			if (!companyConfigurationService.getVehicleGroupWiseRoutePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"FROM TripRoute TR  WHERE  lower(TR.routeName) Like (:Search) AND TR.routeType=1 AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0 " 
								+ "  OR  lower(TR.routeNo) Like (:Search) AND TR.routeType=1 "
								+ "  AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0 ",
								TripRoute.class);
			} else {
				query = entityManager.createQuery("SELECT TR from TripRoute TR "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = TR.vehicleGroupId AND VGP.user_id = "
						+ userDetails.getId() + "" + " WHERE  lower(TR.routeName) Like (:Search) AND TR.routeType=1 AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0 "
						+ " OR  lower(TR.routeNo) Like (:Search) AND TR.routeType=1"
						+ "  AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0",
						TripRoute.class);
			}
			query.setParameter("Search", "%"+Search+"%");
			
			return query.getResultList();
		}else {
			return null;
		}

	}
	
	@Override
	public List<TripRoute> SearchOnlyRoute_MAIN_ROUTE_BY_DEPOTID(Long Search, CustomUserDetails userDetails)
			throws Exception {
		TypedQuery<TripRoute> query = null;
		if (!companyConfigurationService.getVehicleGroupWiseRoutePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"FROM TripRoute TR  WHERE  TR.vehicleGroupId = "+Search+" AND TR.routeType=1 "
							+ "  AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0 ",
					TripRoute.class);
		} else {
			query = entityManager.createQuery("SELECT TR from TripRoute TR "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = TR.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " WHERE TR.vehicleGroupId = "+Search+"  AND TR.routeType=1"
					+ "  AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0",
					TripRoute.class);
		}
		return query.getResultList();
	}
	
	@Override
	public List<TripRoute> SearchOnlyRoute_SUB_ROUTE_BY_RouteId(Integer Search, CustomUserDetails userDetails)
			throws Exception {
		TypedQuery<TripRoute> query = null;
		if (!companyConfigurationService.getVehicleGroupWiseRoutePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"FROM TripRoute TR  WHERE  TR.mainRouteId = "+Search+" AND TR.routeType=2 "
							+ "  AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0 ",
					TripRoute.class);
		} else {
			query = entityManager.createQuery("SELECT TR from TripRoute TR "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = TR.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " WHERE TR.mainRouteId = "+Search+"  AND TR.routeType=2"
					+ "  AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0",
					TripRoute.class);
		}
		return query.getResultList();
	}

	@Transactional
	public List<TripRoutefixedIncome> ValidateTripRouteFixedIncome(Integer incomeId, Integer routeID,
			Integer companyId) {

		TypedQuery<TripRoutefixedIncome> query = entityManager
				.createQuery(
						"FROM TripRoutefixedIncome WHERE routeID =" + routeID + " AND incomeId='" + incomeId
								+ "'  AND companyId = " + companyId + " AND markForDelete=0 ",
						TripRoutefixedIncome.class);
		return query.getResultList();
	}

	@Override
	@Transactional
	public void addTripRouteFixedIncome(TripRoutefixedIncome tripIncome) {

		TripRouteFixedIncomeDao.save(tripIncome);
	}

	@Override
	@Transactional
	public List<TripRoutefixedIncome> listTripRouteFixedIncome(Integer routeID) {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		TypedQuery<TripRoutefixedIncome> query = entityManager.createQuery("FROM TripRoutefixedIncome WHERE routeID ="
				+ routeID + " AND companyId = " + userDetails.getCompany_id() + " AND markForDelete=0 ",
				TripRoutefixedIncome.class);
		return query.getResultList();
	}
	
	@Override
	public List<TripRoute> getRouteListByVehicleGroupId(Long vehicleGroupId, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
				queryt = entityManager.createQuery(
						"SELECT t.routeName, t.vehicleGroupId, t.routeID "
						+ "FROM TripRoute AS t "
						+ " where t.vehicleGroupId = "+vehicleGroupId+"  AND t.companyId =  "+companyId + "and t.markForDelete = 0",
						Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<TripRoute> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripRoute>();
				TripRoute dropdown = null;
				for (Object[] result : results) {
					dropdown = new TripRoute();

					dropdown.setRouteName((String) result[0]);
					dropdown.setVehicleGroupId((Long) result[1]);
					dropdown.setRouteID((Integer) result[2]);
					

					Dtos.add(dropdown);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		} finally {

		}
	}
	
	@Override
	public List<TripRoutefixedIncomeDto> listTripRouteFixedIncome(Integer routeID, Integer companyId) throws Exception {
		TypedQuery<Object[]> query = entityManager
				.createQuery(
						"SELECT TFX.routefixedID, TE.incomeName, TFX.incomeFixedId, B.branch_name, TFX.incomeRefence"
						+ ", TFX.incomeAmount, TFX.incomeId  FROM TripRoutefixedIncome TFX "
						+ " INNER JOIN TripIncome TE ON TE.incomeID = TFX.incomeId "
						+ " INNER JOIN Branch B ON B.branch_id = TFX.incomePlaceId"
						+ " WHERE routeID =" + routeID
								+ " and TFX.markForDelete = 0 AND TFX.companyId = " + companyId + "",
								Object[].class);
		List<Object[]> results = query.getResultList();
		
		List<TripRoutefixedIncomeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripRoutefixedIncomeDto>();
			TripRoutefixedIncomeDto list = null;
			for (Object[] result : results) {
				list = new TripRoutefixedIncomeDto();

				list.setRoutefixedID((Integer) result[0]);
				list.setIncomeName((String) result[1]);
				list.setIncomeFixed(TripRouteFixedType.getFixedTypeName((short) result[2]));
				list.setIncomeFixedId((short) result[2]);
				list.setIncomePlace((String) result[3]);
				list.setIncomeRefence((String) result[4]);
				list.setIncomeAmount((Double) result[5]);
				list.setIncomeId((Integer) result[6]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Override
	@Transactional
	public TripRoutefixedIncome getTripRoutefixedIncome(Integer routefixedID, Integer companyId) throws Exception {

		return TripRouteFixedIncomeDao.getTripRoutefixedIncome(routefixedID, companyId);

	}

	@Override
	@Transactional
	public void deleteTripRoutefixedIncome(Integer routefixedID, Integer companyId) throws Exception {

		try {
			TripRouteFixedIncomeDao.deleteTripRoutefixedIncome(routefixedID, companyId);
		} catch (Exception e) {

		}

	}

	@Override
	@Transactional
	public List<TripRoute> Validate_TRIP_ROUTE_NAME(String routeName, Integer routeType) {

		return TripRouteDao.Validate_TRIP_ROUTE_NAME(routeName, routeType);
	}

	@Override
	@Transactional
	public List<TripRoute> SearchOnlyRoute_SUB_ROUTE_NAME(String Search, CustomUserDetails userDetails)
			throws Exception {
		TypedQuery<TripRoute> query = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		if (!companyConfigurationService.getVehicleGroupWiseRoutePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
						"FROM TripRoute TR  WHERE  lower(TR.routeName) Like (:Search) "
							+ " AND TR.routeType=2 AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0 " 
							+ "  OR  lower(TR.routeNo) Like (:Search) AND TR.routeType=2 "
							+ "  AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0 ",
					TripRoute.class);
		} else {
			query = entityManager.createQuery("SELECT TR from TripRoute TR "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = TR.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " WHERE  lower(TR.routeName) Like (:Search) AND TR.routeType=2 "
					+ " AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0 "
					+ " OR  lower(TR.routeNo) Like (:Search) AND TR.routeType=2"
					+ "  AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0",
					TripRoute.class);
		}
		
		query.setParameter("Search", "%"+Search+"%");
		
		return query.getResultList();
		}else {
			return null;
		}
	}
	
	
	@Override
	public List<TripRoute> getTripRouteSerachList(String Search, CustomUserDetails userDetails) throws Exception {
		TypedQuery<TripRoute> query = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		if (!companyConfigurationService.getVehicleGroupWiseRoutePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"FROM TripRoute TR  WHERE  lower(TR.routeName) Like (:Search) AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0 " 
							+ "  OR  lower(TR.routeNo) Like (:Search) "
							+ "  AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0 ",
					TripRoute.class);
		} else {
			query = entityManager.createQuery("SELECT TR from TripRoute TR "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = TR.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " WHERE  lower(TR.routeName) Like (:Search) AND TR.companyId = " + userDetails.getCompany_id() 
					+ " AND TR.markForDelete = 0 "
					+ " OR  lower(TR.routeNo) Like (:Search) "
					+ "  AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0",
					TripRoute.class);
		}
		query.setParameter("Search", "%"+Search+"%");
		return query.getResultList();
		}else {
			return null;
		}
	}
	
	
		@Override
		public List<TripRoute> getTripRouteListByType(Integer routeType, CustomUserDetails	userDetails) throws Exception {
			TypedQuery<TripRoute> query = null;
			if (!companyConfigurationService.getVehicleGroupWiseRoutePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"FROM TripRoute TR  WHERE TR.routeType= "+routeType+" "
								+ "  AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0 ",
						TripRoute.class);
			} else {
				query = entityManager.createQuery("SELECT TR from TripRoute TR "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = TR.vehicleGroupId AND VGP.user_id = "
						+ userDetails.getId() + "" + " WHERE  TR.routeType= "+routeType+" "
						+ "  AND TR.companyId = " + userDetails.getCompany_id() + " AND TR.markForDelete = 0",
						TripRoute.class);
			}
			return query.getResultList();
		}
	
		@Override
		public Integer getTripRouteApproxKm(Integer routeId) throws Exception {
			return TripRouteDao.getTripRouteApproxKm(routeId);
		}
		
		@Override
		public TripRoute getTriproutePoint(Integer routeID, int companyId) throws Exception {
			/*CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();*/
			
			return TripRouteDao.getTripRoute(routeID, companyId);
		}
		
		@Override
		public ValueObject getTripRouteNameWiseList(ValueObject object) throws Exception {
			List<TripRoute> 	TripRouteList 				= null;
			List<TripRoute> 	TripRoute					= null;
			CustomUserDetails	userDetails					= null;
			String 				term 						= null;
			try {

				TripRouteList 	= new ArrayList<TripRoute>();
				userDetails		= new CustomUserDetails();
				term 		    = object.getString("term");
				
				userDetails.setCompany_id(object.getInt("companyId"));
				userDetails.setId(object.getLong("userId"));

				TripRoute = SearchOnlyRoute_MAIN_ROUTE_NAME(term, userDetails);
				if (TripRoute != null && !TripRoute.isEmpty()) {
					for (TripRoute Route : TripRoute) {

						TripRoute Dto = new TripRoute();
						Dto.setRouteID(Route.getRouteID());
						Dto.setRouteName(Route.getRouteName());
						Dto.setRouteNo(Route.getRouteNo());

						TripRouteList.add(Dto);
					}
				}

				object.put("TripRouteList", TripRouteList);

				return object;
				
			}catch(Exception e) {
				throw e;
			} finally {
				TripRoute 		 = null;
				TripRouteList 	 = null;
				object  	 	 = null;
			}
		}
		
		/*@Transactional
		public ValueObject getTripRouteFixedExpeneByExpenseId(ValueObject object) throws Exception {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			TripRoutefixedExpense tripRoutefixedExpense =  TripRouteFixedExpenseDao.getTripRouteFixedExpeneByExpenseId(object.getInt("routeId"), object.getInt("expenseId"), userDetails.getCompany_id());
			 object.put("tripRoutefixedExpense", tripRoutefixedExpense);
			 return object;
		}*/
		
		@Override
		public ValueObject getTripRouteFixedExpeneByExpenseId(ValueObject object) throws Exception {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> query = entityManager
					.createQuery(
							"SELECT TFX.routefixedID, TE.expenseName, TFX.fixedTypeId, B.branch_name, TFX.expenseRefence"
							+ ", TFX.expenseAmount, TFX.expenseId  FROM TripRoutefixedExpense TFX "
							+ " INNER JOIN TripExpense TE ON TE.expenseID = TFX.expenseId "
							+ " INNER JOIN Branch B ON B.branch_id = TFX.expensePlaceId"
							+ " WHERE routeID =" + object.getInt("routeId")+" AND TFX.expenseId = "+object.getInt("expenseId")+""
									+ " and TFX.markForDelete = 0 AND TFX.companyId = " + userDetails.getCompany_id() + "",
									Object[].class);
			Object[] result = query.getSingleResult();
			
			TripRoutefixedExpenseDto list = null;
			list = new TripRoutefixedExpenseDto();
			if (result != null ) {
					

					list.setRoutefixedID((Integer) result[0]);
					list.setExpenseName((String) result[1]);
					list.setExpenseFixed(TripRouteFixedType.getFixedTypeName((short) result[2]));
					list.setFixedTypeId((short) result[2]);
					list.setExpensePlace((String) result[3]);
					list.setExpenseRefence((String) result[4]);
					list.setExpenseAmount((Double) result[5]);
					list.setExpenseId((Integer) result[6]);
					
			}
			object.put("list", list);
			return object;
		}
		
		
		
		
}