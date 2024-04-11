package org.fleetopgroup.persistence.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.CashBookStatus;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripDailySheetCollectionConfiguration;
import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.constant.VehicleFuelType;
import org.fleetopgroup.persistence.bl.CashBookBL;
import org.fleetopgroup.persistence.bl.TripDailyBL;
import org.fleetopgroup.persistence.dao.TripCollectionExpenseRepository;
import org.fleetopgroup.persistence.dao.TripCollectionIncomeRepository;
import org.fleetopgroup.persistence.dao.TripCollectionSheetRepository;
import org.fleetopgroup.persistence.dao.TripDayCollectionRepository;
import org.fleetopgroup.persistence.dao.TripGroupCollectionRepository;
import org.fleetopgroup.persistence.dto.CashBookDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTransferDto;
import org.fleetopgroup.persistence.dto.TripCollectionExpenseDto;
import org.fleetopgroup.persistence.dto.TripCollectionIncomeDto;
import org.fleetopgroup.persistence.dto.TripCollectionSheetDto;
import org.fleetopgroup.persistence.dto.TripDailyExpenseDto;
import org.fleetopgroup.persistence.dto.TripDailyGroupCollectionDto;
import org.fleetopgroup.persistence.dto.TripDailyRouteSheetDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.dto.TripDailyTimeIncomeDto;
import org.fleetopgroup.persistence.dto.TripGroupCollectionDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.model.CashBookBalance;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.model.TripCollectionExpense;
import org.fleetopgroup.persistence.model.TripCollectionIncome;
import org.fleetopgroup.persistence.model.TripCollectionSheet;
import org.fleetopgroup.persistence.model.TripDayCollection;
import org.fleetopgroup.persistence.model.TripGroupCollection;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ITripCollectionService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.web.util.ConvertIntegerToWord;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("TripCollectionService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TripCollectionService implements ITripCollectionService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private TripCollectionSheetRepository TripCollectionSheet;

	@Autowired
	private TripCollectionExpenseRepository TripCollectionExpense;

	@Autowired
	private TripCollectionIncomeRepository TripCollectionIncome;

	@Autowired
	private TripGroupCollectionRepository TripGroupCollectionSheet;

	@Autowired
	private TripDayCollectionRepository TripDayCollectionSheet;

	@Autowired
	private ICompanyConfigurationService	companyConfigurationService;

	@Autowired
	private ITripDailySheetService tripDailySheetService;
	@Autowired
	private IUserProfileService userProfileService;
	@Autowired
	private ICashBookService CashBookService;

	@Autowired	
	private ICashBookNameService	cashBookNameService;

	@Autowired	
	private IVehicleGroupService	vehicleGroupService;	

	CashBookBL CBBL = new CashBookBL();

	TripDailyBL TDBL = new TripDailyBL();
	DecimalFormat AMOUNTFORMAT 			= new DecimalFormat("##,##,##0");
	SimpleDateFormat dateFormatonlyDate = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormatSQL		= new SimpleDateFormat("yyyy-MM-dd");

	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");

	private static final int PAGE_SIZE = 10;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void add_TripCollectionSheet(TripCollectionSheet CollectionSheet) throws Exception {

		TripCollectionSheet.save(CollectionSheet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * add_TripDayCollection(org.fleetop.persistence.model.TripDayCollection)
	 */
	@Transactional
	public void add_TripDayCollection(TripDayCollection DayCollection) throws Exception {

		TripDayCollectionSheet.save(DayCollection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * add_TripGroupCollection(org.fleetop.persistence.model.
	 * TripGroupCollection)
	 */
	@Transactional
	public void add_TripGroupCollection(TripGroupCollection GroupCollection) throws Exception {

		TripGroupCollectionSheet.save(GroupCollection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * add_TripCollectionExpense(org.fleetop.persistence.model.
	 * TripCollectionExpense)
	 */
	@Transactional
	public void add_TripCollectionExpense(TripCollectionExpense CollectionExpense) throws Exception {

		TripCollectionExpense.save(CollectionExpense);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * add_TripCollectionIncome(org.fleetop.persistence.model.
	 * TripCollectionIncome)
	 */
	@Transactional
	public void add_TripCollectionIncome(TripCollectionIncome CollectionIncome) throws Exception {

		TripCollectionIncome.save(CollectionIncome);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * Search_TripCollectionSheet(java.lang.String)
	 */
	@Transactional
	public List<TripCollectionSheetDto> Search_TripCollectionSheet(String Search, CustomUserDetails userDetails) throws Exception {

		TypedQuery<Object[]> queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.TRIPCOLLID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_SINGL, "
							+ "R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS_ID, R.TRIPCOLLNUMBER "
							+ " FROM TripCollectionSheet AS R "
							+ " INNER JOIN Vehicle AS V ON V.vid = R.VID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " where (lower(R.TRIPCOLLNUMBER) Like ('%" + Search
							+ "%') OR lower(V.vehicle_registration) Like ('%" + Search + "%') "
							+ " OR lower(TR.routeName) Like ('%" + Search + "%') ) AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0",
							Object[].class);
		}else {
			queryt = entityManager.createQuery(
					"SELECT R.TRIPCOLLID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_SINGL, "
							+ "R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS_ID, R.TRIPCOLLNUMBER FROM TripCollectionSheet AS R "
							+ " INNER JOIN Vehicle AS V ON V.vid = R.VID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "+userDetails.getId()+""
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " where (lower(R.TRIPCOLLNUMBER) Like ('%" + Search
							+ "%') OR lower(V.vehicle_registration) Like ('%" + Search + "%') "
							+ " OR lower(TR.routeName) Like ('%" + Search + "%') ) AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0",
							Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<TripCollectionSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripCollectionSheetDto>();
			TripCollectionSheetDto list = null;
			for (Object[] result : results) {
				list = new TripCollectionSheetDto();

				list.setTRIPCOLLID((Long) result[0]);
				list.setVEHICLE_REGISTRATION((String) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_ROUTE_NAME((String) result[3]);
				list.setTRIP_OPEN_DATE_ON((Date) result[4]);
				list.setTRIP_SINGL((Integer) result[5]);
				list.setTOTAL_EXPENSE((Double) result[6]);
				list.setTOTAL_INCOME((Double) result[7]);
				list.setTOTAL_BALANCE((Double) result[8]);
				list.setTRIP_CLOSE_STATUS_ID((short) result[9]);
				list.setTRIPCOLLNUMBER((Long) result[10]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * Search_TripCollectionSheet(java.lang.String)
	 */
	@Transactional
	public List<TripCollectionSheetDto> Search_TripCollectionSheet_Statues(short Statues, String Search)
			throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		/*TypedQuery<TripCollectionSheet> query =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager
					.createQuery("SELECT T from TripCollectionSheet T"
							+ " where ( lower(TRIPCOLLNUMBER) Like ('%" + Search
							+ "%') AND TRIP_CLOSE_STATUS='" + Statues + "' OR lower(VEHICLE_REGISTRATION) Like ('%" + Search
							+ "%') " + " AND TRIP_CLOSE_STATUS='" + Statues + "' OR lower(TRIP_ROUTE_NAME) Like ('%"
							+ Search + "%')  AND TRIP_CLOSE_STATUS='" + Statues + "' ) AND T.COMPANY_ID = "+userDetails.getCompany_id()+" AND T.markForDelete = 0", TripCollectionSheet.class);
		}else {

			query = entityManager
					.createQuery("SELECT T from TripCollectionSheet T"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = T.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+""
							+ " where ( lower(TRIPCOLLNUMBER) Like ('%" + Search
							+ "%') AND TRIP_CLOSE_STATUS='" + Statues + "' OR lower(VEHICLE_REGISTRATION) Like ('%" + Search
							+ "%') " + " AND TRIP_CLOSE_STATUS='" + Statues + "' OR lower(TRIP_ROUTE_NAME) Like ('%"
							+ Search + "%')  AND TRIP_CLOSE_STATUS='" + Statues + "' ) AND T.COMPANY_ID = "+userDetails.getCompany_id()+" AND T.markForDelete = 0", TripCollectionSheet.class);
		}
		return query.getResultList();*/



		TypedQuery<Object[]> queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.TRIPCOLLID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_SINGL, "
							+ "R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS_ID, R.TRIPCOLLNUMBER "
							+ " FROM TripCollectionSheet AS R "
							+ " INNER JOIN Vehicle AS V ON V.vid = R.VID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " where ( lower(R.TRIPCOLLNUMBER) Like ('%" + Search
							+ "%') AND R.TRIP_CLOSE_STATUS_ID=" + Statues + " OR lower(V.vehicle_registration) Like ('%" + Search
							+ "%') " + " AND R.TRIP_CLOSE_STATUS_ID=" + Statues + " OR lower(TR.routeName) Like ('%"
							+ Search + "%')  AND R.TRIP_CLOSE_STATUS_ID=" + Statues + " ) AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0",
							Object[].class);
		}else {
			queryt = entityManager.createQuery(
					"SELECT R.TRIPCOLLID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_SINGL, "
							+ "R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS_ID, R.TRIPCOLLNUMBER FROM TripCollectionSheet AS R "
							+ " INNER JOIN Vehicle AS V ON V.vid = R.VID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "+userDetails.getId()+""
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " where ( lower(R.TRIPCOLLNUMBER) Like ('%" + Search
							+ "%') AND R.TRIP_CLOSE_STATUS_ID=" + Statues + " OR lower(V.vehicle_registration) Like ('%" + Search
							+ "%') " + " AND R.TRIP_CLOSE_STATUS_ID=" + Statues + " OR lower(TR.routeName) Like ('%"
							+ Search + "%')  AND R.TRIP_CLOSE_STATUS_ID=" + Statues + " ) AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0",
							Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<TripCollectionSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripCollectionSheetDto>();
			TripCollectionSheetDto list = null;
			for (Object[] result : results) {
				list = new TripCollectionSheetDto();

				list.setTRIPCOLLID((Long) result[0]);
				list.setVEHICLE_REGISTRATION((String) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_ROUTE_NAME((String) result[3]);
				list.setTRIP_OPEN_DATE_ON((Date) result[4]);
				list.setTRIP_SINGL((Integer) result[5]);
				list.setTOTAL_EXPENSE((Double) result[6]);
				list.setTOTAL_INCOME((Double) result[7]);
				list.setTOTAL_BALANCE((Double) result[8]);
				list.setTRIP_CLOSE_STATUS_ID((short) result[9]);
				list.setTRIPCOLLNUMBER((Long) result[10]);

				Dtos.add(list);
			}
		}
		return Dtos;


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * count_TripCollectionSheet()
	 */
	@Transactional
	public Long count_TripCollectionSheet(CustomUserDetails	userDetails) throws Exception {
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return TripCollectionSheet.getCountOfCompanyByVehicleGroupPermision(userDetails.getCompany_id());	
		}
		return TripCollectionSheet.getCountOfCompanyByVehicleGroupPermision(userDetails.getId(), userDetails.getCompany_id());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * count_TripCollectionSheet_Today(java.lang.String)
	 */
	@Transactional
	public Long count_TripCollectionSheet_Today(String Today) throws Exception {

		TypedQuery<Long> query = entityManager.createQuery(
				"select count(*) from TripCollectionSheet where TRIP_OPEN_DATE='" + Today + "' ", Long.class);
		return query.getSingleResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * list_Today_TripCollectionSheet(java.lang.String)
	 */
	@Transactional
	public List<TripCollectionSheetDto> list_Today_TripCollectionSheet(String date, CustomUserDetails	userDetails) throws Exception {

		TypedQuery<Object[]> queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.TRIPCOLLID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_SINGL, "
							+ "R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS_ID, R.TRIPCOLLNUMBER "
							+ " FROM TripCollectionSheet AS R "
							+ " INNER JOIN Vehicle AS V ON V.vid = R.VID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " Where R.TRIP_OPEN_DATE='" + date + "' AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ORDER BY R.TRIPCOLLID desc ",
							Object[].class);
		}else {
			queryt = entityManager.createQuery(
					"SELECT R.TRIPCOLLID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_SINGL, "
							+ "R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS_ID, R.TRIPCOLLNUMBER FROM TripCollectionSheet AS R "
							+ " INNER JOIN Vehicle AS V ON V.vid = R.VID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "+userDetails.getId()+""
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " Where R.TRIP_OPEN_DATE='" + date + "' AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ORDER BY R.TRIPCOLLID desc ",
							Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<TripCollectionSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripCollectionSheetDto>();
			TripCollectionSheetDto list = null;
			for (Object[] result : results) {
				list = new TripCollectionSheetDto();

				list.setTRIPCOLLID((Long) result[0]);
				list.setVEHICLE_REGISTRATION((String) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_ROUTE_NAME((String) result[3]);
				list.setTRIP_OPEN_DATE_ON((Date) result[4]);
				list.setTRIP_SINGL((Integer) result[5]);
				list.setTOTAL_EXPENSE((Double) result[6]);
				list.setTOTAL_INCOME((Double) result[7]);
				list.setTOTAL_BALANCE((Double) result[8]);
				list.setTRIP_CLOSE_STATUS_ID((short) result[9]);
				list.setTRIPCOLLNUMBER((Long) result[10]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * update_TripCollection_TotalExpense(java.lang.Double, java.lang.Long)
	 */
	@Transactional
	public void update_TripCollection_TotalExpense(Double tripTotalExpense, Long TRIPCOLLID, Integer companyId) throws Exception {

		TripCollectionSheet.update_TripCollection_TotalExpense(tripTotalExpense, TRIPCOLLID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * Get_Showing_TripCollection_Sheet(java.lang.Long)
	 */
	/*@Transactional
	public TripCollectionSheet Get_Showing_TripCollection_Sheet(Long TRIPCOLLID) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return TripCollectionSheet.Get_Showing_TripCollection_Sheet(TRIPCOLLID, userDetails.getCompany_id());	
			}
			return TripCollectionSheet.Get_Showing_TripCollection_Sheet(TRIPCOLLID, userDetails.getId(), userDetails.getCompany_id());
		} catch (Exception e) {
			throw e; 
		}
	}*/

	@Override
	public TripCollectionSheetDto Get_Showing_TripCollection_Sheet(Long TRIPCOLLID, CustomUserDetails userDetails)
			throws Exception {
		Query query = null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"  SELECT T.TRIPCOLLID, T.TRIPCOLLNUMBER, T.VID, V.vehicle_registration, VG.vGroup, VG.gid, T.TRIP_OPEN_KM,"
							+ " T.TRIP_CLOSE_KM, T.TRIP_USAGE_KM, T.TRIP_OPEN_DATE, T.TRIP_ROUTE_ID, TR.routeName, T.TRIP_DRIVER_ID, D.driver_firstname,"
							+ " T.TRIP_CONDUCTOR_ID, D2.driver_firstname, T.TRIP_CLEANER_ID, D3.driver_firstname, T.TRIP_DIESEL_LITER, T.TRIP_SINGL, T.TOTAL_BALANCE,"
							+ " T.TOTAL_EXPENSE, T.TOTAL_INCOME, T.TRIP_DRIVER_JAMA, T.TRIP_CONDUCTOR_JAMA, T.DRIVER_ADVANCE_STATUS_ID, T.CONDUCTOR_ADVANCE_STATUS_ID,"
							+ " T.TRIP_CLOSE_STATUS_ID, T.TRIP_REMARKS, U.email, U2.email, T.CREATED, T.lASTUPDATED, T.CREATEDBYID, T.LASTMODIFIEDBYID "
							+ " From TripCollectionSheet AS T "
							+ " INNER JOIN Vehicle AS V ON V.vid = T.VID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID "
							+ " INNER JOIN Driver D ON D.driver_id = T.TRIP_DRIVER_ID"
							+ " INNER JOIN Driver D2 ON D2.driver_id = T.TRIP_CONDUCTOR_ID"
							+ " LEFT JOIN Driver D3 ON D3.driver_id = T.TRIP_CLEANER_ID"
							+ " LEFT JOIN User U ON U.id = T.CREATEDBYID"
							+ " LEFT JOIN User U2 ON U2.id = T.LASTMODIFIEDBYID"
							+ " WHERE T.TRIPCOLLID = "+TRIPCOLLID+" AND T.COMPANY_ID = "+userDetails.getCompany_id()+" AND T.markForDelete = 0");

		}else {
			query = entityManager.createQuery(
					"  SELECT T.TRIPCOLLID, T.TRIPCOLLNUMBER, T.VID, V.vehicle_registration, VG.vGroup, VG.gid, T.TRIP_OPEN_KM,"
							+ " T.TRIP_CLOSE_KM, T.TRIP_USAGE_KM, T.TRIP_OPEN_DATE, T.TRIP_ROUTE_ID, TR.routeName, T.TRIP_DRIVER_ID, D.driver_firstname,"
							+ " T.TRIP_CONDUCTOR_ID, D2.driver_firstname, T.TRIP_CLEANER_ID, D3.driver_firstname, T.TRIP_DIESEL_LITER, T.TRIP_SINGL, T.TOTAL_BALANCE,"
							+ " T.TOTAL_EXPENSE, T.TOTAL_INCOME, T.TRIP_DRIVER_JAMA, T.TRIP_CONDUCTOR_JAMA, T.DRIVER_ADVANCE_STATUS_ID, T.CONDUCTOR_ADVANCE_STATUS_ID,"
							+ " T.TRIP_CLOSE_STATUS_ID, T.TRIP_REMARKS, U.email, U2.email, T.CREATED, T.lASTUPDATED, T.CREATEDBYID, T.LASTMODIFIEDBYID "
							+ " From TripCollectionSheet AS T "
							+ " INNER JOIN Vehicle AS V ON V.vid = T.VID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "+userDetails.getId()+""
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID "
							+ " INNER JOIN Driver D ON D.driver_id = T.TRIP_DRIVER_ID"
							+ " INNER JOIN Driver D2 ON D2.driver_id = T.TRIP_CONDUCTOR_ID"
							+ " LEFT JOIN Driver D3 ON D3.driver_id = T.TRIP_CLEANER_ID"
							+ " LEFT JOIN User U ON U.id = T.CREATEDBYID"
							+ " LEFT JOIN User U2 ON U2.id = T.LASTMODIFIEDBYID"
							+ " WHERE T.TRIPCOLLID = "+TRIPCOLLID+" AND T.COMPANY_ID = "+userDetails.getCompany_id()+" AND T.markForDelete = 0");

		}


		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		TripCollectionSheetDto status;
		if (vehicle != null) {
			status	= new TripCollectionSheetDto();

			status.setTRIPCOLLID((Long) vehicle[0]);
			status.setTRIPCOLLNUMBER((Long) vehicle[1]);
			status.setVID((Integer) vehicle[2]);
			status.setVEHICLE_REGISTRATION((String) vehicle[3]);
			status.setVEHICLE_GROUP((String) vehicle[4]);
			status.setVEHICLE_GROUP_ID((long) vehicle[5]);
			status.setTRIP_OPEN_KM((Integer) vehicle[6]);
			status.setTRIP_CLOSE_KM((Integer) vehicle[7]);
			status.setTRIP_USAGE_KM((Integer) vehicle[8]);
			if (vehicle[9] != null) {
				status.setTRIP_OPEN_DATE(dateFormat_Name.format(vehicle[9]));
			}
			status.setTRIP_OPEN_DATE_ON((Date) vehicle[9]);
			status.setTRIP_ROUTE_ID((Integer) vehicle[10]);
			status.setTRIP_ROUTE_NAME((String) vehicle[11]);
			status.setTRIP_DRIVER_ID((int) vehicle[12]);
			status.setTRIP_DRIVER_NAME((String) vehicle[13]);
			status.setTRIP_CONDUCTOR_ID((int) vehicle[14]);
			status.setTRIP_CONDUCTOR_NAME((String) vehicle[15]);
			status.setTRIP_CLEANER_ID((int) vehicle[16]);
			status.setTRIP_CLEANER_NAME((String) vehicle[17]);
			status.setTRIP_DIESEL_LITER((Integer) vehicle[18]);
			status.setTRIP_SINGL((Integer) vehicle[19]);
			status.setTOTAL_BALANCE((Double) vehicle[20]);
			status.setTOTAL_EXPENSE((Double) vehicle[21]);
			status.setTOTAL_INCOME((Double) vehicle[22]);
			status.setTRIP_DRIVER_JAMA((Double) vehicle[23]);
			status.setTRIP_CONDUCTOR_JAMA((Double) vehicle[24]);
			status.setDRIVER_ADVANCE_STATUS_ID((short) vehicle[25]);
			status.setDRIVER_ADVANCE_STATUS(FuelVendorPaymentMode.getPaymentMode((short) vehicle[25]));
			status.setCONDUCTOR_ADVANCE_STATUS_ID((short) vehicle[26]);
			status.setCONDUCTOR_ADVANCE_STATUS(FuelVendorPaymentMode.getPaymentMode((short) vehicle[26]));
			status.setTRIP_CLOSE_STATUS_ID((short) vehicle[27]);
			status.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) vehicle[27]));
			status.setTRIP_REMARKS((String) vehicle[28]);
			status.setCREATEDBY((String) vehicle[29]);
			status.setLASTMODIFIEDBY((String) vehicle[30]);
			/** Set Created Current Date */
			if (vehicle[31] != null) {
				/** Set Created Current Date */
				status.setCREATED(CreatedDateTime.format(vehicle[31]));
			}
			if (vehicle[32] != null) {
				status.setlASTUPDATED(CreatedDateTime.format(vehicle[32]));
			}
			return status;

		} else {
			return null;
		}

	}

	@Override
	public TripCollectionSheetDto Get_Showing_TripCollection_Sheetby_Number(Long TRIPCOLLID, CustomUserDetails userDetails)
			throws Exception {
		Query query = null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"  SELECT T.TRIPCOLLID, T.TRIPCOLLNUMBER, T.VID, V.vehicle_registration, VG.vGroup, VG.gid, T.TRIP_OPEN_KM,"
							+ " T.TRIP_CLOSE_KM, T.TRIP_USAGE_KM, T.TRIP_OPEN_DATE, T.TRIP_ROUTE_ID, TR.routeName, T.TRIP_DRIVER_ID, D.driver_firstname,"
							+ " T.TRIP_CONDUCTOR_ID, D2.driver_firstname, T.TRIP_CLEANER_ID, D3.driver_firstname, T.TRIP_DIESEL_LITER, T.TRIP_SINGL, T.TOTAL_BALANCE,"
							+ " T.TOTAL_EXPENSE, T.TOTAL_INCOME, T.TRIP_DRIVER_JAMA, T.TRIP_CONDUCTOR_JAMA, T.DRIVER_ADVANCE_STATUS_ID, T.CONDUCTOR_ADVANCE_STATUS_ID,"
							+ " T.TRIP_CLOSE_STATUS_ID, T.TRIP_REMARKS, U.email, U2.email, T.CREATED, T.lASTUPDATED, T.CREATEDBYID, T.LASTMODIFIEDBYID "
							+ " From TripCollectionSheet AS T "
							+ " INNER JOIN Vehicle AS V ON V.vid = T.VID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID "
							+ " INNER JOIN Driver D ON D.driver_id = T.TRIP_DRIVER_ID"
							+ " INNER JOIN Driver D2 ON D2.driver_id = T.TRIP_CONDUCTOR_ID"
							+ " LEFT JOIN Driver D3 ON D3.driver_id = T.TRIP_CLEANER_ID"
							+ " LEFT JOIN User U ON U.id = T.CREATEDBYID"
							+ " LEFT JOIN User U2 ON U2.id = T.LASTMODIFIEDBYID"
							+ " WHERE T.TRIPCOLLNUMBER = "+TRIPCOLLID+" AND T.COMPANY_ID = "+userDetails.getCompany_id()+" AND T.markForDelete = 0");

		}else {
			query = entityManager.createQuery(
					"  SELECT T.TRIPCOLLID, T.TRIPCOLLNUMBER, T.VID, V.vehicle_registration, VG.vGroup, VG.gid, T.TRIP_OPEN_KM,"
							+ " T.TRIP_CLOSE_KM, T.TRIP_USAGE_KM, T.TRIP_OPEN_DATE, T.TRIP_ROUTE_ID, TR.routeName, T.TRIP_DRIVER_ID, D.driver_firstname,"
							+ " T.TRIP_CONDUCTOR_ID, D2.driver_firstname, T.TRIP_CLEANER_ID, D3.driver_firstname, T.TRIP_DIESEL_LITER, T.TRIP_SINGL, T.TOTAL_BALANCE,"
							+ " T.TOTAL_EXPENSE, T.TOTAL_INCOME, T.TRIP_DRIVER_JAMA, T.TRIP_CONDUCTOR_JAMA, T.DRIVER_ADVANCE_STATUS_ID, T.CONDUCTOR_ADVANCE_STATUS_ID,"
							+ " T.TRIP_CLOSE_STATUS_ID, T.TRIP_REMARKS, U.email, U2.email, T.CREATED, T.lASTUPDATED, T.CREATEDBYID, T.LASTMODIFIEDBYID "
							+ " From TripCollectionSheet AS T "
							+ " INNER JOIN Vehicle AS V ON V.vid = T.VID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "+userDetails.getId()+""
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID "
							+ " INNER JOIN Driver D ON D.driver_id = T.TRIP_DRIVER_ID"
							+ " INNER JOIN Driver D2 ON D2.driver_id = T.TRIP_CONDUCTOR_ID"
							+ " LEFT JOIN Driver D3 ON D3.driver_id = T.TRIP_CLEANER_ID"
							+ " LEFT JOIN User U ON U.id = T.CREATEDBYID"
							+ " LEFT JOIN User U2 ON U2.id = T.LASTMODIFIEDBYID"
							+ " WHERE T.TRIPCOLLNUMBER = "+TRIPCOLLID+" AND T.COMPANY_ID = "+userDetails.getCompany_id()+" AND T.markForDelete = 0");

		}


		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		TripCollectionSheetDto status;
		if (vehicle != null) {
			status	= new TripCollectionSheetDto();

			status.setTRIPCOLLID((Long) vehicle[0]);
			status.setTRIPCOLLNUMBER((Long) vehicle[1]);
			status.setVID((Integer) vehicle[2]);
			status.setVEHICLE_REGISTRATION((String) vehicle[3]);
			status.setVEHICLE_GROUP((String) vehicle[4]);
			status.setVEHICLE_GROUP_ID((long) vehicle[5]);
			status.setTRIP_OPEN_KM((Integer) vehicle[6]);
			status.setTRIP_CLOSE_KM((Integer) vehicle[7]);
			status.setTRIP_USAGE_KM((Integer) vehicle[8]);
			if (vehicle[9] != null) {
				status.setTRIP_OPEN_DATE(dateFormat_Name.format(vehicle[9]));
			}
			status.setTRIP_ROUTE_ID((Integer) vehicle[10]);
			status.setTRIP_ROUTE_NAME((String) vehicle[11]);
			status.setTRIP_DRIVER_ID((int) vehicle[12]);
			status.setTRIP_DRIVER_NAME((String) vehicle[13]);
			status.setTRIP_CONDUCTOR_ID((int) vehicle[14]);
			status.setTRIP_CONDUCTOR_NAME((String) vehicle[15]);
			status.setTRIP_CLEANER_ID((int) vehicle[16]);
			status.setTRIP_CLEANER_NAME((String) vehicle[17]);
			status.setTRIP_DIESEL_LITER((Integer) vehicle[18]);
			status.setTRIP_SINGL((Integer) vehicle[19]);
			status.setTOTAL_BALANCE((Double) vehicle[20]);
			status.setTOTAL_EXPENSE((Double) vehicle[21]);
			status.setTOTAL_INCOME((Double) vehicle[22]);
			status.setTRIP_DRIVER_JAMA((Double) vehicle[23]);
			status.setTRIP_CONDUCTOR_JAMA((Double) vehicle[24]);
			status.setDRIVER_ADVANCE_STATUS_ID((short) vehicle[25]);
			status.setDRIVER_ADVANCE_STATUS(FuelVendorPaymentMode.getPaymentMode((short) vehicle[25]));
			status.setCONDUCTOR_ADVANCE_STATUS_ID((short) vehicle[26]);
			status.setCONDUCTOR_ADVANCE_STATUS(FuelVendorPaymentMode.getPaymentMode((short) vehicle[26]));
			status.setTRIP_CLOSE_STATUS_ID((short) vehicle[27]);
			status.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) vehicle[27]));
			status.setTRIP_REMARKS((String) vehicle[28]);
			status.setCREATEDBY((String) vehicle[29]);
			status.setLASTMODIFIEDBY((String) vehicle[30]);
			/** Set Created Current Date */
			if (vehicle[31] != null) {
				/** Set Created Current Date */
				status.setCREATED(CreatedDateTime.format(vehicle[31]));
			}
			if (vehicle[32] != null) {
				status.setlASTUPDATED(CreatedDateTime.format(vehicle[32]));
			}
			return status;

		} else {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * findAll_TripCollection_Expense(java.lang.Long)
	 */
	@Transactional
	public List<TripCollectionExpense> findAll_TripCollection_Expense(Long TRIPCOLLID) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return TripCollectionExpense.findAll_TripCollection_Expense(TRIPCOLLID, userDetails.getCompany_id());
	}

	@Override
	public List<TripCollectionExpenseDto> findAll_TripCollection_Expense(Long TRIPCOLLID, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt =	null;

		queryt = entityManager.createQuery(
				" SELECT T.tripExpenseID , TE.expenseName, T.expenseId, T.expenseAmount, T.expenseRefence, U.email, T.created "
						+ " From TripCollectionExpense T "
						+ " LEFT JOIN TripExpense TE ON TE.expenseID = T.expenseId"
						+ " LEFT JOIN User U ON U.id = T.createdById"
						+ " where T.tripCollectionsheet.TRIPCOLLID = "+TRIPCOLLID+" AND T.companyId = "+companyId+" AND T.markForDelete = 0 ",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripCollectionExpenseDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripCollectionExpenseDto>();
			TripCollectionExpenseDto list = null;
			for (Object[] result : results) {
				list = new TripCollectionExpenseDto();

				list.setTripExpenseID((Long) result[0]);
				list.setExpenseName((String) result[1]);
				list.setExpenseId((Integer) result[2]);
				list.setExpenseAmount((Double) result[3]);
				list.setExpenseRefence((String) result[4]);
				list.setCreatedBy((String) result[5]);
				list.setCreated((Date) result[6]);

				if (list.getExpenseName() == null || list.getExpenseName() == "") {
					list.setExpenseName(VehicleFuelType.getVehicleFuelName(Short.parseShort((Integer) result[2] + "")));
				}

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * findAll_TripCollection_Income(java.lang.Long)
	 */
	@Transactional
	public List<TripCollectionIncome> findAll_TripCollection_Income(Long TRIPCOLLID) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return TripCollectionIncome.findAll_TripCollection_Income(TRIPCOLLID, userDetails.getCompany_id());
	}

	@Override
	public List<TripCollectionIncomeDto> findAll_TripCollection_Income(Long TRIPCOLLID, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt =	null;

		queryt = entityManager.createQuery(
				" SELECT T.tripincomeID , TI.incomeName, T.incomeId, T.incomeAmount, T.incomeRefence, U.firstName, U.lastName, T.created "
						+ " From TripCollectionIncome T "
						+ " LEFT JOIN TripIncome TI ON TI.incomeID = T.incomeId"
						+ " LEFT JOIN User U ON U.id = T.incomeCollectedById"
						+ " WHERE T.tripCollectionsheet.TRIPCOLLID = "+TRIPCOLLID+" AND T.companyId = "+companyId+" AND T.markForDelete = 0 ",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripCollectionIncomeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripCollectionIncomeDto>();
			TripCollectionIncomeDto list = null;
			for (Object[] result : results) {
				list = new TripCollectionIncomeDto();

				list.setTripincomeID((Long) result[0]);
				list.setIncomeName((String) result[1]);
				list.setIncomeId((Integer) result[2]);
				list.setIncomeAmount((Double) result[3]);
				list.setIncomeRefence((String) result[4]);
				list.setIncomeCollectedBy((String) result[5] +"_"+(String) result[6]);
				list.setCreated((Date) result[7]);


				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * Validate_TripCollection_Expense(java.lang.String, java.lang.Long)
	 */
	@Transactional
	public List<TripCollectionExpense> Validate_TripCollection_Expense(Integer expenceName, Long TRIPCOLLID, Integer companyId)
			throws Exception {

		return TripCollectionExpense.Validate_TripCollection_Expense(expenceName, TRIPCOLLID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * Get_TripCollection_Expense(java.lang.Long)
	 */
	@Transactional
	public TripCollectionExpense Get_TripCollection_Expense(Long TripSheet_Expenseid, Integer companyId) throws Exception {

		return TripCollectionExpense.Get_TripCollection_Expense(TripSheet_Expenseid, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * delete_TripCollectionExpense(java.lang.Long)
	 */
	@Transactional
	public void delete_TripCollectionExpense(Long TripExpenseid, Integer companyId) throws Exception {

		TripCollectionExpense.delete_TripCollectionExpense(TripExpenseid, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * Validate_TripCollection_Income(java.lang.String, java.lang.Long)
	 */
	@Transactional
	public List<TripCollectionIncome> Validate_TripCollection_Income(Integer IncomeName, Long TRIPCOLLID, Integer companyId)
			throws Exception {

		return TripCollectionIncome.Validate_TripCollection_Income(IncomeName, TRIPCOLLID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * Get_TripCollection_Income(java.lang.Long)
	 */
	@Transactional
	public TripCollectionIncome Get_TripCollection_Income(Long TripSheet_Incomeid, Integer companyId) throws Exception {

		return TripCollectionIncome.Get_TripCollection_Income(TripSheet_Incomeid, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * delete_TripCollectionIncome(java.lang.Long)
	 */
	@Transactional
	public void delete_TripCollectionIncome(Long TripIncomeid, Integer companyId) throws Exception {

		TripCollectionIncome.delete_TripCollectionIncome(TripIncomeid, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * update_TripCollection_TotalIncome(java.lang.Double, java.lang.Long)
	 */
	@Transactional
	public void update_TripCollection_TotalIncome(Double tripTotalIncome, Long TRIPCOLLID, Integer companyId) throws Exception {

		TripCollectionSheet.update_TripCollection_TotalIncome(tripTotalIncome, TRIPCOLLID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * EDIT_TripCollection_Sheet(java.lang.Long)
	 */
	@Transactional
	public TripCollectionSheetDto EDIT_TripCollection_Sheet(Long TRIPCOLLID, Integer companyId) throws Exception {

		Query query = null;
		query = entityManager.createQuery(
				"  SELECT T.TRIPCOLLID, T.TRIPCOLLNUMBER, T.VID, V.vehicle_registration, VG.vGroup, VG.gid, T.TRIP_OPEN_KM,"
						+ " T.TRIP_CLOSE_KM, T.TRIP_USAGE_KM, T.TRIP_OPEN_DATE, T.TRIP_ROUTE_ID, TR.routeName, T.TRIP_DRIVER_ID, D.driver_firstname,"
						+ " T.TRIP_CONDUCTOR_ID, D2.driver_firstname, T.TRIP_CLEANER_ID, D3.driver_firstname, T.TRIP_DIESEL_LITER, T.TRIP_SINGL, T.TOTAL_BALANCE,"
						+ " T.TOTAL_EXPENSE, T.TOTAL_INCOME, T.TRIP_DRIVER_JAMA, T.TRIP_CONDUCTOR_JAMA, T.DRIVER_ADVANCE_STATUS_ID, T.CONDUCTOR_ADVANCE_STATUS_ID,"
						+ " T.TRIP_CLOSE_STATUS_ID, T.TRIP_REMARKS, U.email, U2.email, T.CREATED, T.lASTUPDATED, T.CREATEDBYID, T.LASTMODIFIEDBYID "
						+ " From TripCollectionSheet AS T "
						+ " INNER JOIN Vehicle AS V ON V.vid = T.VID "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID "
						+ " INNER JOIN Driver D ON D.driver_id = T.TRIP_DRIVER_ID"
						+ " INNER JOIN Driver D2 ON D2.driver_id = T.TRIP_CONDUCTOR_ID"
						+ " LEFT JOIN Driver D3 ON D3.driver_id = T.TRIP_CLEANER_ID"
						+ " LEFT JOIN User U ON U.id = T.CREATEDBYID"
						+ " LEFT JOIN User U2 ON U2.id = T.LASTMODIFIEDBYID"
						+ " WHERE T.TRIPCOLLID = "+TRIPCOLLID+" AND T.COMPANY_ID = "+companyId+" AND T.markForDelete = 0");


		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		TripCollectionSheetDto status;
		if (vehicle != null) {
			status	= new TripCollectionSheetDto();

			status.setTRIPCOLLID((Long) vehicle[0]);
			status.setTRIPCOLLNUMBER((Long) vehicle[1]);
			status.setVID((Integer) vehicle[2]);
			status.setVEHICLE_REGISTRATION((String) vehicle[3]);
			status.setVEHICLE_GROUP((String) vehicle[4]);
			status.setVEHICLE_GROUP_ID((long) vehicle[5]);
			status.setTRIP_OPEN_KM((Integer) vehicle[6]);
			status.setTRIP_CLOSE_KM((Integer) vehicle[7]);
			status.setTRIP_USAGE_KM((Integer) vehicle[8]);
			if (vehicle[9] != null) {
				status.setTRIP_OPEN_DATE(dateFormat.format(vehicle[9]));
			}
			status.setTRIP_OPEN_DATE_ON((Date) vehicle[9]);
			status.setTRIP_ROUTE_ID((Integer) vehicle[10]);
			status.setTRIP_ROUTE_NAME((String) vehicle[11]);
			status.setTRIP_DRIVER_ID((int) vehicle[12]);
			status.setTRIP_DRIVER_NAME((String) vehicle[13]);
			status.setTRIP_CONDUCTOR_ID((int) vehicle[14]);
			status.setTRIP_CONDUCTOR_NAME((String) vehicle[15]);
			status.setTRIP_CLEANER_ID((int) vehicle[16]);
			status.setTRIP_CLEANER_NAME((String) vehicle[17]);
			status.setTRIP_DIESEL_LITER((Integer) vehicle[18]);
			status.setTRIP_SINGL((Integer) vehicle[19]);
			status.setTOTAL_BALANCE((Double) vehicle[20]);
			status.setTOTAL_EXPENSE((Double) vehicle[21]);
			status.setTOTAL_INCOME((Double) vehicle[22]);
			status.setTRIP_DRIVER_JAMA((Double) vehicle[23]);
			status.setTRIP_CONDUCTOR_JAMA((Double) vehicle[24]);
			status.setDRIVER_ADVANCE_STATUS_ID((short) vehicle[25]);
			status.setDRIVER_ADVANCE_STATUS(FuelVendorPaymentMode.getPaymentMode((short) vehicle[25]));
			status.setCONDUCTOR_ADVANCE_STATUS_ID((short) vehicle[26]);
			status.setCONDUCTOR_ADVANCE_STATUS(FuelVendorPaymentMode.getPaymentMode((short) vehicle[26]));
			status.setTRIP_CLOSE_STATUS_ID((short) vehicle[27]);
			status.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) vehicle[27]));
			status.setTRIP_REMARKS((String) vehicle[28]);
			status.setCREATEDBY((String) vehicle[29]);
			status.setLASTMODIFIEDBY((String) vehicle[30]);
			/** Set Created Current Date */
			if (vehicle[31] != null) {
				/** Set Created Current Date */
				status.setCREATED(CreatedDateTime.format(vehicle[31]));
			}
			status.setCREATED_ON((Date) vehicle[31]);
			if (vehicle[32] != null) {
				status.setlASTUPDATED(CreatedDateTime.format(vehicle[32]));
			}
			status.setlASTUPDATED_ON((Date) vehicle[32]);
			return status;

		} else {
			return null;
		}


	}

	@Transactional
	public TripCollectionSheetDto ReOpen_TripCollection_Sheet(Long TRIPCOLLID, Integer companyId) throws Exception {
		//return TripCollectionSheet.ReOpen_TripCollection_Sheet(TRIPCOLLID, companyId);
		Query query = null;
		query = entityManager.createQuery(
				"  SELECT T.TRIPCOLLID, T.TRIPCOLLNUMBER, T.VID, VG.gid, T.TRIP_OPEN_KM,"
						+ " T.TRIP_CLOSE_KM, T.TRIP_USAGE_KM, T.TRIP_OPEN_DATE, T.TRIP_ROUTE_ID, T.TRIP_DRIVER_ID,"
						+ " T.TRIP_CONDUCTOR_ID, T.TRIP_CLEANER_ID, T.TRIP_DIESEL_LITER, T.TRIP_SINGL, T.TOTAL_BALANCE,"
						+ " T.TOTAL_EXPENSE, T.TOTAL_INCOME, T.TRIP_DRIVER_JAMA, T.TRIP_CONDUCTOR_JAMA, T.DRIVER_ADVANCE_STATUS_ID, T.CONDUCTOR_ADVANCE_STATUS_ID,"
						+ " T.TRIP_CLOSE_STATUS_ID"
						+ " From TripCollectionSheet AS T "
						+ " INNER JOIN Vehicle AS V ON V.vid = T.VID "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " WHERE T.TRIPCOLLID = "+TRIPCOLLID+" AND T.TRIP_CLOSE_STATUS_ID="+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND T.COMPANY_ID = "+companyId+" AND T.markForDelete = 0");


		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		TripCollectionSheetDto status;
		if (vehicle != null) {
			status	= new TripCollectionSheetDto();

			status.setTRIPCOLLID((Long) vehicle[0]);
			status.setTRIPCOLLNUMBER((Long) vehicle[1]);
			status.setVID((Integer) vehicle[2]);
			status.setVEHICLE_GROUP_ID((long) vehicle[3]);
			status.setTRIP_OPEN_KM((Integer) vehicle[4]);
			status.setTRIP_CLOSE_KM((Integer) vehicle[5]);
			status.setTRIP_USAGE_KM((Integer) vehicle[6]);
			if (vehicle[7] != null) {
				status.setTRIP_OPEN_DATE(dateFormat.format(vehicle[7]));
			}
			status.setTRIP_OPEN_DATE_ON((Date) vehicle[7]);
			status.setTRIP_ROUTE_ID((Integer) vehicle[8]);
			status.setTRIP_DRIVER_ID((int) vehicle[9]);
			status.setTRIP_CONDUCTOR_ID((int) vehicle[10]);
			status.setTRIP_CLEANER_ID((int) vehicle[11]);
			status.setTRIP_DIESEL_LITER((Integer) vehicle[12]);
			status.setTRIP_SINGL((Integer) vehicle[13]);
			status.setTOTAL_BALANCE((Double) vehicle[14]);
			status.setTOTAL_EXPENSE((Double) vehicle[15]);
			status.setTOTAL_INCOME((Double) vehicle[16]);
			status.setTRIP_DRIVER_JAMA((Double) vehicle[17]);
			status.setTRIP_CONDUCTOR_JAMA((Double) vehicle[18]);
			status.setDRIVER_ADVANCE_STATUS_ID((short) vehicle[19]);
			status.setDRIVER_ADVANCE_STATUS(FuelVendorPaymentMode.getPaymentMode((short) vehicle[19]));
			status.setCONDUCTOR_ADVANCE_STATUS_ID((short) vehicle[20]);
			status.setCONDUCTOR_ADVANCE_STATUS(FuelVendorPaymentMode.getPaymentMode((short) vehicle[20]));
			status.setTRIP_CLOSE_STATUS_ID((short) vehicle[21]);
			status.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) vehicle[21]));

			return status;

		} else {
			return null;
		}



	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * getDeployment_Page_TripCollectionSheet(java.lang.String,
	 * java.lang.Integer)
	 */
	@Transactional
	public Page<org.fleetopgroup.persistence.model.TripCollectionSheet> getDeployment_Page_TripCollectionSheet(short status, Integer pageNumber, CustomUserDetails	userDetails) throws Exception {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return TripCollectionSheet.getDeployment_Page_TripCollectionSheet(status, userDetails.getCompany_id(), pageable);
		}
		return TripCollectionSheet.getDeployment_Page_TripCollectionSheet(status, userDetails.getId(), userDetails.getCompany_id(), pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * list_TripCollectionSheet_Page_Status(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public List<TripCollectionSheetDto> list_TripCollectionSheet_Page_Status(short status, Integer pageNumber, CustomUserDetails	userDetails) throws Exception {

		TypedQuery<Object[]> queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager
					.createQuery(
							"SELECT R.TRIPCOLLID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_SINGL, "
									+ "R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS_ID, R.TRIPCOLLNUMBER "
									+ " FROM TripCollectionSheet AS R "
									+ " INNER JOIN Vehicle AS V ON V.vid = R.VID "
									+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
									+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
									+ " Where R.TRIP_CLOSE_STATUS_ID=" + status + " AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ORDER BY R.TRIPCOLLID desc ",
									Object[].class);
		}else {
			queryt = entityManager
					.createQuery(
							"SELECT R.TRIPCOLLID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_SINGL, "
									+ "R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS_ID, R.TRIPCOLLNUMBER "
									+ " FROM TripCollectionSheet AS R "
									+ " INNER JOIN Vehicle AS V ON V.vid = R.VID "
									+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
									+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "+userDetails.getId()+""
									+ " Where R.TRIP_CLOSE_STATUS_ID=" + status + " AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ORDER BY R.TRIPCOLLID desc ",
									Object[].class);
		}
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<TripCollectionSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripCollectionSheetDto>();
			TripCollectionSheetDto list = null;
			for (Object[] result : results) {
				list = new TripCollectionSheetDto();

				list.setTRIPCOLLID((Long) result[0]);
				list.setVEHICLE_REGISTRATION((String) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_ROUTE_NAME((String) result[3]);
				list.setTRIP_OPEN_DATE_ON((Date) result[4]);
				list.setTRIP_SINGL((Integer) result[5]);
				list.setTOTAL_EXPENSE((Double) result[6]);
				list.setTOTAL_INCOME((Double) result[7]);
				list.setTOTAL_BALANCE((Double) result[8]);
				list.setTRIP_CLOSE_STATUS_ID((short) result[9]);
				list.setTRIPCOLLNUMBER((Long) result[10]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * update_TripCollection_Driver_JAM(java.lang.Double, java.lang.Long)
	 */
	@Transactional
	public void update_TripCollection_Driver_JAM(Double driverJAM, Long TRIPCOLLID, Integer companyId) throws Exception {

		TripCollectionSheet.update_TripCollection_Driver_JAM(driverJAM, TRIPCOLLID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * update_TripCollection_Conductor_JAM(java.lang.Double, java.lang.Long)
	 */
	@Transactional
	public void update_TripCollection_Conductor_JAM(Double ConductorJAM, Long TRIPCOLLID, Integer companyId) throws Exception {

		TripCollectionSheet.update_TripCollection_Conductor_JAM(ConductorJAM, TRIPCOLLID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * update_Close_TripCollection(java.lang.Integer, java.lang.Integer,
	 * java.lang.String, java.lang.Double, java.lang.String, java.lang.Long)
	 */
	@Transactional
	public void update_Close_TripCollection(Integer closingKM, Integer usageKM, short Status, Double balance,
			Long lastmodifiedby, Long tripcollid, Integer companyId) throws Exception {

		TripCollectionSheet.update_Close_TripCollection(closingKM, usageKM, Status, balance, lastmodifiedby,
				tripcollid, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * Validate_TripGroupCollection(java.util.Date, java.lang.String)
	 */
	@Transactional
	public List<TripGroupCollection> Validate_TripGroupCollection(String trip_OPEN_DATE, Long vehicle_GROUP, Integer companyId)
			throws Exception {

		TypedQuery<TripGroupCollection> query = entityManager
				.createQuery("FROM TripGroupCollection as v Where v.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND VEHICLE_GROUP_ID=" + vehicle_GROUP + " AND COMPANY_ID = "+companyId+" AND markForDelete = 0", TripGroupCollection.class);
		return query.getResultList();

		// return
		// TripGroupCollectionSheet.Validate_TripGroupCollection(trip_OPEN_DATE,
		// vehicle_GROUP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * update_TripGroupCollection(java.lang.Double, java.lang.Double,
	 * java.lang.Double, java.lang.Integer, java.lang.Integer,
	 * java.lang.Integer, java.lang.Long)
	 */
	@Transactional
	public void update_TripGroupCollection(Double total_INCOME, Double total_EXPENSE, Double total_BALANCE,
			Integer total_BUS, Integer total_DIESEL, Integer total_SINGL, Long TRIPGROUPID, Integer companyId) throws Exception {

		entityManager.createQuery("UPDATE TripGroupCollection  SET TOTAL_COLLECTION = TOTAL_COLLECTION + "
				+ total_INCOME + ", TOTAL_EXPENSE = TOTAL_EXPENSE + " + total_EXPENSE
				+ ", TOTAL_BALANCE = TOTAL_BALANCE + " + total_BALANCE + " , TOTAL_BUS = TOTAL_BUS + " + total_BUS
				+ " , TOTAL_DIESEL = TOTAL_DIESEL + " + total_DIESEL + " , TOTAL_SINGL = TOTAL_SINGL + " + total_SINGL
				+ " where TRIPGROUPID=" + TRIPGROUPID+" AND COMPANY_ID = "+companyId+" ").executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * List_TripGroupCollection_close(java.lang.String)
	 */
	@Transactional
	public List<TripGroupCollectionDto> List_TripGroupCollection_closeDate(String trip_OPEN_DATE, short TRIP_CLOSE_STATUS, Integer companyId)
			throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager.createQuery(" SELECT R.TRIPGROUPID, VG.vGroup, R.TOTAL_BALANCE,"
					+ " R.TOTAL_BUS, R.TOTAL_COLLECTION, R.TOTAL_DIESEL, R.TOTAL_EXPENSE, R.TOTAL_GROUP_COLLECTION, R.TOTAL_SINGL, R.TRIP_OPEN_DATE,"
					+ " R.TRIP_CLOSE_STATUS_ID, R.TOTAL_BUS, R.VEHICLE_GROUP_ID "
					+ " FROM TripGroupCollection AS R "
					+ " INNER JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
					+ " Where R.TRIP_OPEN_DATE='" + trip_OPEN_DATE
					+ "' AND R.TRIP_CLOSE_STATUS_ID=" + TRIP_CLOSE_STATUS + " AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0",
					Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<TripGroupCollectionDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripGroupCollectionDto>();
				TripGroupCollectionDto list = null;
				for (Object[] result : results) {
					list = new TripGroupCollectionDto();

					list.setTRIPGROUPID((Long) result[0]);
					list.setVEHICLE_GROUP((String) result[1]);
					list.setTOTAL_BALANCE((Double) result[2]);
					list.setTOTAL_BUS((Integer) result[3]);
					list.setTOTAL_COLLECTION((Double) result[4]);
					list.setTOTAL_DIESEL((Integer) result[5]);
					list.setTOTAL_EXPENSE((Double) result[6]);
					list.setTOTAL_GROUP_COLLECTION((Double) result[7]);
					list.setTOTAL_SINGL((Integer) result[8]);
					list.setTRIP_OPEN_DATE_ON((Date) result[9]);
					list.setTRIP_CLOSE_STATUS_ID((short) result[10]);
					list.setTOTAL_BUS( (Integer) result[11]);
					list.setVEHICLE_GROUP_ID((long) result[12]);
					Dtos.add(list);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<TripCollectionSheetDto> List_TripCollectionSheet_closeDate(
			String trip_OPEN_DATE, short TRIP_CLOSE_STATUS, CustomUserDetails userDetails) throws Exception {

		TypedQuery<Object[]> queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.TRIPCOLLID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_SINGL, "
							+ "R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS_ID, R.TRIPCOLLNUMBER,"
							+ " D.driver_firstname, D2.driver_firstname, R.TRIP_DIESEL_LITER, R.TRIP_SINGL, VG.gid "
							+ " FROM TripCollectionSheet AS R "
							+ " INNER JOIN Vehicle AS V ON V.vid = R.VID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID"
							+ " LEFT JOIN Driver D ON D.driver_id = R.TRIP_DRIVER_ID"
							+ " LEFT JOIN Driver D2 ON D2.driver_id = R.TRIP_DRIVER_ID"
							+ " Where R.TRIP_OPEN_DATE='" + trip_OPEN_DATE
							+ "' AND R.TRIP_CLOSE_STATUS_ID=" + TRIP_CLOSE_STATUS + " AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0",
							Object[].class);
		}else {
			queryt = entityManager.createQuery(
					"SELECT R.TRIPCOLLID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_SINGL, "
							+ "R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS_ID, R.TRIPCOLLNUMBER,"
							+ " D.driver_firstname, D2.driver_firstname, R.TRIP_DIESEL_LITER, R.TRIP_SINGL, VG.gid "
							+ " FROM TripCollectionSheet AS R "
							+ " INNER JOIN Vehicle AS V ON V.vid = R.VID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "+userDetails.getId()+""
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " LEFT JOIN Driver D ON D.driver_id = R.TRIP_DRIVER_ID"
							+ " LEFT JOIN Driver D2 ON D2.driver_id = R.TRIP_DRIVER_ID"
							+ " Where R.TRIP_OPEN_DATE='" + trip_OPEN_DATE
							+ "' AND R.TRIP_CLOSE_STATUS_ID=" + TRIP_CLOSE_STATUS + " AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0",
							Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<TripCollectionSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripCollectionSheetDto>();
			TripCollectionSheetDto list = null;
			for (Object[] result : results) {
				list = new TripCollectionSheetDto();

				list.setTRIPCOLLID((Long) result[0]);
				list.setVEHICLE_REGISTRATION((String) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_ROUTE_NAME((String) result[3]);
				list.setTRIP_OPEN_DATE_ON((Date) result[4]);
				list.setTRIP_SINGL((Integer) result[5]);
				list.setTOTAL_EXPENSE((Double) result[6]);
				list.setTOTAL_INCOME((Double) result[7]);
				list.setTOTAL_BALANCE((Double) result[8]);
				list.setTRIP_CLOSE_STATUS_ID((short) result[9]);
				list.setTRIPCOLLNUMBER((Long) result[10]);
				list.setTRIP_DRIVER_NAME((String) result[11]);
				list.setTRIP_CONDUCTOR_NAME((String) result[12]);
				list.setTRIP_DIESEL_LITER((Integer) result[13]);
				list.setTRIP_SINGL((Integer) result[14]);
				list.setVEHICLE_GROUP_ID((long) result[15]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}

	@Override
	public List<TripGroupCollection> List_TripGroupCollection_closeDate(String trip_OPEN_DATE, String TRIP_CLOSE_STATUS,
			CustomUserDetails userDetails) throws Exception {
		try {
			TypedQuery<TripGroupCollection> query = null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager
						.createQuery("SELECT v FROM TripGroupCollection as v Where v.TRIP_OPEN_DATE='" + trip_OPEN_DATE
								+ "' AND TRIP_CLOSE_STATUS='" + TRIP_CLOSE_STATUS + "' AND v.COMPANY_ID = "+userDetails.getCompany_id()+" AND v.markForDelete = 0", TripGroupCollection.class);
			}else {
				query = entityManager
						.createQuery("SELECT v FROM TripGroupCollection as v "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+""
								+ " Where v.TRIP_OPEN_DATE='" + trip_OPEN_DATE
								+ "' AND TRIP_CLOSE_STATUS='" + TRIP_CLOSE_STATUS + "' AND v.COMPANY_ID = "+userDetails.getCompany_id()+" AND v.markForDelete = 0", TripGroupCollection.class);
			}
			return query.getResultList();
		} catch (Exception e) {
			throw e;
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * List_TripGroupCollection_close(java.lang.String)
	 */
	@Transactional
	public List<TripCollectionSheet> List_TripCollectionSheet_closeDate(String trip_OPEN_DATE, String TRIP_CLOSE_STATUS)
			throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TypedQuery<TripCollectionSheet> query = entityManager
				.createQuery("FROM TripCollectionSheet as v Where v.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND TRIP_CLOSE_STATUS='" + TRIP_CLOSE_STATUS + "' AND v.COMPANY_ID = "+userDetails.getCompany_id()+" AND v.markForDelete = 0", TripCollectionSheet.class);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * update_TripGroup_Ac_collectionTotal_Amount(java.lang.Double,
	 * java.lang.String)
	 */
	@Transactional
	public void update_TripGroup_Ac_collectionTotal_Amount(Double per_SINGL_COLLECTION, String tripCollectionDate, Integer companyId)
			throws Exception {

		entityManager.createQuery("UPDATE TripGroupCollection  SET TOTAL_GROUP_COLLECTION = (TOTAL_SINGL * "
				+ per_SINGL_COLLECTION + "), TRIP_CLOSE_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" where TRIP_OPEN_DATE='" + tripCollectionDate
				+ "' AND TRIP_CLOSE_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_OPEN+" AND COMPANY_ID = "+companyId+"").executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * List_TripDayCollection_CloseDate(java.lang.String, java.lang.String)
	 */
	@Transactional
	public TripDayCollection List_TripDayCollection_CloseDate(String tripCollectionDate, short CloseSatus)
			throws Exception {
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TypedQuery<TripDayCollection> query = entityManager
				.createQuery("FROM TripDayCollection as v Where v.TRIP_OPEN_DATE='" + tripCollectionDate
						+ "' AND TRIP_CLOSE_STATUS_ID=" + CloseSatus + " AND COMPANY_ID = "+userDetails.getCompany_id()+" AND markForDelete = 0", TripDayCollection.class);
		return query.getSingleResult();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * delete_TripCollectionSheet(java.lang.Long)
	 */
	@Transactional
	public void delete_TripCollectionSheet(Long tripCollectionID, Integer companyId) throws Exception {

		TripCollectionSheet.delete_TripCollectionSheet(tripCollectionID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * delete_TripCollectionExpense_collectionID(java.lang.Long)
	 */
	@Transactional
	public void delete_TripCollectionExpense_collectionID(Long tripCollectionID, Integer companyId) throws Exception {

		TripCollectionExpense.delete_TripCollectionExpense_collectionID(tripCollectionID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * delete_TripCollectionIncome_collectionId(java.lang.Long)
	 */
	@Transactional
	public void delete_TripCollectionIncome_collectionId(Long tripCollectionID, Integer companyId) throws Exception {

		TripCollectionIncome.delete_TripCollectionIncome_collectionId(tripCollectionID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * List_TripCollectionSheet_Report(java.lang.String)
	 */
	@Transactional
	public List<TripCollectionSheetDto> List_TripCollectionSheet_Report(String Search) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		TypedQuery<Object[]> queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager
					.createQuery(
							"SELECT R.TRIPCOLLID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_SINGL, "
									+ "R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS_ID, R.TRIPCOLLNUMBER "
									+ " FROM TripCollectionSheet AS R "
									+ " INNER JOIN Vehicle AS V ON V.vid = R.VID "
									+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
									+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
									+ " Where ( R.markForDelete= 0" + Search + " ) AND R.COMPANY_ID = "+userDetails.getCompany_id()+" ORDER BY R.TRIPCOLLID desc ",
									Object[].class);
		}else {
			queryt = entityManager
					.createQuery(
							"SELECT R.TRIPCOLLID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_SINGL, "
									+ "R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS_ID, R.TRIPCOLLNUMBER "
									+ " FROM TripCollectionSheet AS R "
									+ " INNER JOIN Vehicle AS V ON V.vid = R.VID "
									+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
									+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "+userDetails.getId()+""
									+ " Where ( R.markForDelete= 0" + Search + " ) AND R.COMPANY_ID = "+userDetails.getCompany_id()+" ORDER BY R.TRIPCOLLID desc ",
									Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<TripCollectionSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripCollectionSheetDto>();
			TripCollectionSheetDto list = null;
			for (Object[] result : results) {
				list = new TripCollectionSheetDto();

				list.setTRIPCOLLID((Long) result[0]);
				list.setVEHICLE_REGISTRATION((String) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_ROUTE_NAME((String) result[3]);
				list.setTRIP_OPEN_DATE_ON((Date) result[4]);
				list.setTRIP_SINGL((Integer) result[5]);
				list.setTOTAL_EXPENSE((Double) result[6]);
				list.setTOTAL_INCOME((Double) result[7]);
				list.setTOTAL_BALANCE((Double) result[8]);
				list.setTRIP_CLOSE_STATUS_ID((short) result[9]);
				list.setTRIPCOLLNUMBER((Long) result[10]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<TripCollectionSheetDto> List_DriverJAMA_TripCollectionSheet_Report(String Search, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TRIPCOLLID,  V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_SINGL, "
						+ "R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS_ID, D.driver_firstname, R.TRIP_DRIVER_JAMA, D2.driver_firstname, R.TRIP_CONDUCTOR_JAMA, R.TRIP_DIESEL_LITER, R.TRIPCOLLNUMBER "
						+ " FROM TripCollectionSheet AS R "
						+ " INNER JOIN Vehicle AS V ON V.vid = R.VID "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID"
						+ " LEFT JOIN Driver D ON D.driver_id = R.TRIP_DRIVER_ID"
						+ " LEFT JOIN Driver D2 ON D2.driver_id = R.TRIP_DRIVER_ID"
						+ " Where R.markForDelete = 0 " + Search + " AND R.COMPANY_ID = "+companyId+" ORDER BY R.TRIPCOLLID desc ",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripCollectionSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripCollectionSheetDto>();
			TripCollectionSheetDto list = null;
			for (Object[] result : results) {
				list = new TripCollectionSheetDto();

				list.setTRIPCOLLID((Long) result[0]);
				list.setVEHICLE_REGISTRATION((String) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_ROUTE_NAME((String) result[3]);
				list.setTRIP_OPEN_DATE_ON((Date) result[4]);
				list.setTRIP_SINGL((Integer) result[5]);
				list.setTOTAL_EXPENSE((Double) result[6]);
				list.setTOTAL_INCOME((Double) result[7]);
				list.setTOTAL_BALANCE((Double) result[8]);
				list.setTRIP_CLOSE_STATUS_ID((short) result[9]);
				list.setTRIP_DRIVER_NAME((String) result[10]);
				list.setTRIP_DRIVER_JAMA((Double) result[11]);
				list.setTRIP_CONDUCTOR_NAME((String) result[12]);
				list.setTRIP_CONDUCTOR_JAMA((Double) result[13]);
				list.setTRIP_DIESEL_LITER((Integer) result[14]);
				list.setTRIPCOLLNUMBER((Long) result[15]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * List_TripGroupCollection_Report(java.lang.String)
	 */
	@Transactional
	public List<TripGroupCollectionDto> List_TripGroupCollection_Report(String Search, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(" SELECT R.TRIPGROUPID, VG.vGroup, R.TOTAL_BALANCE,"
				+ " R.TOTAL_BUS, R.TOTAL_COLLECTION, R.TOTAL_DIESEL, R.TOTAL_EXPENSE, R.TOTAL_GROUP_COLLECTION, R.TOTAL_SINGL, R.TRIP_OPEN_DATE,"
				+ " R.TRIP_CLOSE_STATUS_ID "
				+ " FROM TripGroupCollection AS R "
				+ " INNER JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
				+ " Where R.TRIP_CLOSE_STATUS_ID="+TripDailySheetStatus.TRIP_STATUS_CLOSED+" " + Search + " AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0  ORDER BY R.TRIP_OPEN_DATE desc ",
				Object[].class);

		List<Object[]> results = queryt.getResultList();

		List<TripGroupCollectionDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripGroupCollectionDto>();
			TripGroupCollectionDto list = null;
			for (Object[] result : results) {
				list = new TripGroupCollectionDto();

				list.setTRIPGROUPID((Long) result[0]);
				list.setVEHICLE_GROUP((String) result[1]);
				list.setTOTAL_BALANCE((Double) result[2]);
				list.setTOTAL_BUS((Integer) result[3]);
				list.setTOTAL_COLLECTION((Double) result[4]);
				list.setTOTAL_DIESEL((Integer) result[5]);
				list.setTOTAL_EXPENSE((Double) result[6]);
				list.setTOTAL_GROUP_COLLECTION((Double) result[7]);
				list.setTOTAL_SINGL((Integer) result[8]);
				list.setTRIP_OPEN_DATE_ON((Date) result[9]);
				list.setTRIP_CLOSE_STATUS_ID((short) result[10]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * List_TripDayCollection_Report(java.lang.String)
	 */
	@Transactional
	public List<TripDayCollection> List_TripDayCollection_Report(String Search, CustomUserDetails	userDetails) throws Exception {

		TypedQuery<TripDayCollection> queryt = entityManager.createQuery(" FROM TripDayCollection AS R "
				+ " Where R.TRIP_CLOSE_STATUS_ID="+TripDailySheetStatus.TRIP_STATUS_CLOSED+" " + Search + " AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ORDER BY R.TRIP_OPEN_DATE desc ",
				TripDayCollection.class);
		return queryt.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * DRIVER_JAMA_BALANCE_TripCollectio(java.lang.Integer)
	 */
	@Transactional
	public List<TripCollectionSheet> DRIVER_JAMA_BALANCE_TripCollectio(Integer dRIVER_ID) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT T.TRIPCOLLID, T.TRIP_DRIVER_JAMA FROM TripCollectionSheet AS T WHERE T.TRIP_DRIVER_ID = "
						+ dRIVER_ID + " AND T.DRIVER_ADVANCE_STATUS='NOTPAID'",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripCollectionSheet> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripCollectionSheet>();
			TripCollectionSheet list = null;
			for (Object[] result : results) {
				list = new TripCollectionSheet();

				list.setTRIPCOLLID((Long) result[0]);
				list.setTRIP_DRIVER_JAMA((Double) result[1]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * CONDUCTOR_JAMA_BALANCE_TripCollectio(java.lang.Integer)
	 */
	@Transactional
	public List<TripCollectionSheet> CONDUCTOR_JAMA_BALANCE_TripCollectio(Integer dRIVER_ID) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT T.TRIPCOLLID, T.TRIP_CONDUCTOR_JAMA FROM TripCollectionSheet AS T WHERE T.TRIP_CONDUCTOR_ID = "
						+ dRIVER_ID + " AND T.CONDUCTOR_ADVANCE_STATUS='NOTPAID'",
						Object[].class);

		List<Object[]> results = queryt.getResultList();
		List<TripCollectionSheet> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripCollectionSheet>();
			TripCollectionSheet list = null;
			for (Object[] result : results) {
				list = new TripCollectionSheet();

				list.setTRIPCOLLID((Long) result[0]);
				list.setTRIP_CONDUCTOR_JAMA((Double) result[1]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * update_Driver_JAMA_Status(java.lang.String, int)
	 */
	@Transactional
	public void update_Driver_JAMA_Status(short string, int trip_DRIVER_ID, Integer companyId) {

		TripCollectionSheet.update_Driver_JAMA_Status(string, trip_DRIVER_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * update_Conductor_JAMA_Status(java.lang.String, int)
	 */
	@Transactional
	public void update_Conductor_JAMA_Status(short string, int trip_DRIVER_ID, Integer companyId) {

		TripCollectionSheet.update_Conductor_JAMA_Status(string, trip_DRIVER_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * Update_ReOpen_Status_TripCollectionSheet(java.lang.Long,
	 * java.lang.String)
	 */
	@Transactional
	public void Update_ReOpen_Status_TripCollectionSheet(Long tripcollid, short trip_CLOSE_STATUS, Integer companyId) {

		TripCollectionSheet.Update_ReOpen_Status_TripCollectionSheet(tripcollid, trip_CLOSE_STATUS, companyId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * Update_ReOpen_To_Subtrack_TripGroupCollection(java.lang.Double,
	 * java.lang.Double, java.lang.Double, java.lang.Integer, java.lang.Integer,
	 * java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Transactional
	public void Update_ReOpen_To_Subtrack_TripGroupCollection(Double total_INCOME, Double total_EXPENSE,
			Double total_BALANCE, Integer trip_DIESEL_LITER, Integer trip_SINGL, Integer BUS_ROUTE_ID,
			String trip_OPEN_DATE, long vehicle_GROUP, Integer companyId) {

		entityManager.createQuery("UPDATE TripGroupCollection  SET TOTAL_COLLECTION = TOTAL_COLLECTION - "
				+ total_INCOME + ", TOTAL_EXPENSE = TOTAL_EXPENSE - " + total_EXPENSE
				+ ", TOTAL_BALANCE = TOTAL_BALANCE - " + total_BALANCE + "" + ", TOTAL_DIESEL = TOTAL_DIESEL - "
				+ trip_DIESEL_LITER + ",  TOTAL_SINGL = TOTAL_SINGL - " + trip_SINGL + ",  TOTAL_BUS = TOTAL_BUS - "
				+ BUS_ROUTE_ID + " WHERE TRIP_OPEN_DATE='" + trip_OPEN_DATE + "' AND VEHICLE_GROUP_ID = "
				+ vehicle_GROUP + " AND COMPANY_ID = "+companyId+"").executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCollectionService#
	 * Validate_TripCollectionSheet_Date_VehicleName(java.sql.Date,
	 * java.lang.Integer)
	 */
	@Transactional
	public List<TripCollectionSheet> Validate_TripCollectionSheet_Date_VehicleName(java.sql.Date fromDate, Integer vid, Integer companyId) {

		return TripCollectionSheet.Validate_TripCollectionSheet_Date_VehicleName(fromDate, vid, companyId);
	}

	@Override
	public TripCollectionSheetDto Get_FuelVendor_SearchTo_TripSheetDetailsIN(Long tripSheetNumber, Integer companyId) {

		Query query = entityManager.createQuery(
				"SELECT f.TRIPCOLLID, f.VID, f.VEHICLE_REGISTRATION, VG.vGroup, f.TRIP_DRIVER_ID, D.driver_firstname,"
						+ " f.TRIP_CONDUCTOR_ID, D2.driver_firstname, f.TRIP_CLEANER_ID, D3.driver_firstname, VG.gid, f.TRIPCOLLNUMBER "
						+ " , v.vehicle_Odometer, v.vehicleFuelId "
						+ " from TripCollectionSheet AS f "
						+ " INNER JOIN Vehicle v on v.vid = f.VID "
						+ " INNER JOIN VehicleGroup VG = VG.gid = v.vehicleGroupId "
						+ " LEFT JOIN Driver D ON D.driver_id = f.TRIP_DRIVER_ID"
						+ " LEFT JOIN Driver D2 ON D2.driver_id = f.TRIP_CONDUCTOR_ID"
						+ " LEFT JOIN Driver D3 ON D3.driver_id = f.TRIP_CLEANER_ID"
						+ " where  f.TRIPCOLLNUMBER="
						+ tripSheetNumber + " AND f.markForDelete = 0 AND f.COMPANY_ID = " + companyId + "");
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		TripCollectionSheetDto select = new TripCollectionSheetDto();
		if (vehicle != null) {
			select.setTRIPCOLLID((Long) vehicle[0]);
			select.setVID((Integer) vehicle[1]);
			select.setVEHICLE_REGISTRATION((String) vehicle[2]);
			select.setVEHICLE_GROUP((String) vehicle[3]);
			select.setTRIP_DRIVER_ID((Integer) vehicle[4]);
			select.setTRIP_DRIVER_NAME((String) vehicle[5]);
			select.setTRIP_CONDUCTOR_ID((Integer) vehicle[6]);
			select.setTRIP_CONDUCTOR_NAME((String) vehicle[7]);
			select.setTRIP_CLEANER_ID((Integer) vehicle[8]);
			select.setTRIP_CLEANER_NAME((String) vehicle[9]);
			select.setVEHICLE_GROUP_ID((long) vehicle[10]);
			select.setTRIPCOLLNUMBER((Long) vehicle[11]);
			select.setTRIP_OPEN_KM((Integer) vehicle[12]);
			select.setTRIP_ROUTE_NAME((String) vehicle[13]);
			select.setCREATEDBY(VehicleFuelType.getVehicleFuelTypeName((String) vehicle[13]));

		} else {
			return null;
		}
		return select;
	}

	@Override
	@Transactional
	public Object[] getDriverAdvanceAndBataExpenseId(Integer companyId) throws Exception {
		try {
			Query queryt = entityManager
					.createQuery("SELECT co.expenseID , (SELECT Pho.expenseID FROM TripExpense AS Pho where  Pho.expenseName ='DRIVER_JAMA' AND Pho.markForDelete = 0 AND Pho.companyId = "+companyId+") "
							+ " FROM TripExpense As co Where co.expenseName = 'CONDUCTOR_JAMA' AND co.markForDelete = 0 AND co.companyId = "+companyId+"");
			Object[] count = null;
			try {
				count = (Object[]) queryt.getSingleResult();
			} catch (NoResultException nre) {
				return null;
			}
			return count;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public ValueObject getDailyMonthlyYearlyTripCollectionReport(ValueObject valueObject) throws Exception {

		ValueObject				valOutObject 		= null;
		CustomUserDetails		userDetails			= null;
		String					dateRange			= null;
		long					depotId				= 0;
		HashMap<String, Object>		configuration			= null;
		//HashMap<String, Object>		config					= null;
		HashMap<String, TripDailySheetDto>  fuelAmountHm	= null;
		HashMap<String, TripDailySheetDto>  chaloHm			= null;
		try {

			if(!valueObject.containsKey("dateRange") || !valueObject.containsKey("depotId")) {
				return null;
			}

			valOutObject	= new ValueObject();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			dateRange	= valueObject.getString("dateRange");
			depotId		= valueObject.getLong("depotId");

			if (dateRange != "" && dateRange != null && depotId != 0) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = From_TO_DateRange[0];
				dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				try {

					try {
						configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIP_COLLECTION_CONFIG);
						//config			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIP_COLLECTION_CONFIG);
						String Trip_depotName = "", TripOpenDate_Close = "";

						if (depotId != 0) {
							Trip_depotName = "  AND R.VEHICLE_GROUP_ID = " + depotId + " ";
						}

						if (dateRangeFrom != "" && dateRangeTo != "") {
							TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
									+ dateRangeTo + "' ";
						}

						String query = " " + Trip_depotName + " " + TripOpenDate_Close + " ";
						
						
						String Trip_DepotId = "  AND R.vehicleGroupId = " + depotId + " ";
						String Trip_Date = "  AND v.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
								+ dateRangeTo + "' ";
						
						String query1 = " " + Trip_DepotId + " " + Trip_Date + " ";
						
						List<TripDailyGroupCollectionDto> TDGroupCol = tripDailySheetService
								.List_TripDailyGroupCollection_Report(query, userDetails);
						
							
					if((boolean) configuration.get(TripDailySheetCollectionConfiguration.SHOW_DIESEL_AMOUNT) || (boolean) configuration.get(TripDailySheetCollectionConfiguration.SHOW_CHALO_DETAILS)) {
							
						//For Chalo details
							if((boolean) configuration.get(TripDailySheetCollectionConfiguration.SHOW_CHALO_DETAILS)) {
								chaloHm	=	tripDailySheetService.getDepotWiseChaloDetailsOfTripSheet( dateRangeFrom,  dateRangeTo,
										query1, userDetails);
							} 
								
					if((boolean) configuration.get(TripDailySheetCollectionConfiguration.SHOW_DIESEL_AMOUNT)) {
								fuelAmountHm=	tripDailySheetService.getDepotWiseFuelAmountOfTripSheet( dateRangeFrom,  dateRangeTo,
										query1, userDetails);
							if(fuelAmountHm != null && fuelAmountHm.size() > 0) {
								Double totalDieselAmount = 0.0;
								 for (Map.Entry<String, TripDailySheetDto> entry : fuelAmountHm.entrySet()) {
								        totalDieselAmount += entry.getValue().getTRIP_DIESEL_AMOUNT();
								 }
								 valOutObject.put("totalDieselAmount", round(totalDieselAmount, 2));
							   }
						  }		
								
							valOutObject.put("TDGroupCol", TDBL.prepare_LIST_TripDailyGroupCollectionDto(TDGroupCol,fuelAmountHm,chaloHm)); 
					}else {
						
							valOutObject.put("TDGroupCol", TDBL.prepare_LIST_TripDailyGroupCollectionDto(TDGroupCol));
						}

					
						valOutObject.put("CurrentMonth_FromDATE", dateRangeFrom);
						valOutObject.put("CurrentMonth_ToDATE", dateRangeTo);

						Double[] totalGroupSheet = TDBL.Tota_TripDailyGroupCollection_Sheet_total(TDGroupCol);
						if (totalGroupSheet != null) {
							String TotalUsageKM = AMOUNTFORMAT.format(totalGroupSheet[0]);
							valOutObject.put("TotalUsageKM", TotalUsageKM);
							String TotalDiesel = AMOUNTFORMAT.format(totalGroupSheet[1]);
							valOutObject.put("TotalDiesel", TotalDiesel);
							String TotalDieselKMPL = AMOUNTFORMAT.format(totalGroupSheet[2]);
							valOutObject.put("TotalDieselKMPL", TotalDieselKMPL);
							String TotalPassenger = AMOUNTFORMAT.format(totalGroupSheet[3]);
							valOutObject.put("TotalPassenger", TotalPassenger);
							String TotalRFID = AMOUNTFORMAT.format(totalGroupSheet[4]);
							valOutObject.put("TotalRFID", TotalRFID);

							String TotalRFIDAmount = AMOUNTFORMAT.format(totalGroupSheet[5]);
							valOutObject.put("TotalRFIDAmount", TotalRFIDAmount);

							String TotalCollection = AMOUNTFORMAT.format(totalGroupSheet[6]);
							valOutObject.put("TotalCollection", TotalCollection);

							String TotalWT = AMOUNTFORMAT.format(totalGroupSheet[7]);
							valOutObject.put("TotalWT", TotalWT);

							String TotalNetCollection = AMOUNTFORMAT.format(totalGroupSheet[8]);
							valOutObject.put("TotalNetCollection", TotalNetCollection);

							String TotalEPK = AMOUNTFORMAT.format(totalGroupSheet[9]);
							valOutObject.put("TotalEPK", TotalEPK);

							String TotalExpense = AMOUNTFORMAT.format(totalGroupSheet[10]);
							valOutObject.put("TotalExpense", TotalExpense);
							String TotalOT = AMOUNTFORMAT.format(totalGroupSheet[11]);
							valOutObject.put("TotalOT", TotalOT);
							String TotalBalance = AMOUNTFORMAT.format(totalGroupSheet[12]);
							valOutObject.put("TotalBalance", TotalBalance);
						}
						VehicleGroup	group	= vehicleGroupService.getVehicleGroupByID(depotId);
						CashBookName cashBookName =	cashBookNameService.getCashBookByVehicleGroupId(depotId, userDetails.getCompany_id());
						// When Cash Book Approval the Date only show the
						// Details

						if (dateRangeFrom != null && dateRangeTo != null) {
							java.sql.Date dateRangeFrom_CB = null, dateRangeTo_CB = null;

							try {

								java.util.Date date = dateFormatSQL.parse(dateRangeFrom);
								dateRangeFrom_CB = new java.sql.Date(date.getTime());

								java.util.Date dateTo = dateFormatSQL.parse(dateRangeTo);
								dateRangeTo_CB = new java.sql.Date(dateTo.getTime());
							} catch (Exception e) {

							}
							List<String> dateSearch = new ArrayList<String>();
							Calendar calender = new GregorianCalendar();
							calender.setTime(dateRangeFrom_CB);

							while (calender.getTime().getTime() <= dateRangeTo_CB.getTime()) {

								java.util.Date result = calender.getTime();

								dateSearch.add(dateFormatonlyDate.format(result));

								calender.add(Calendar.DATE, 1);

							}

							valOutObject.put("DATESEARCH", dateSearch);
						}

						List<CashBookDto> Credit_Debit = CashBookService
								.Report_CashBook_Month_wise_Expense_Group(cashBookName.getNAMEID(), dateRangeFrom, dateRangeTo, userDetails.getCompany_id());

						
						valOutObject.put("CASHBOOK_NAME", group.getvGroup());
						valOutObject.put("CASH_BOOK_ID", cashBookName.getNAMEID());
						valOutObject.put("GROUP_NAME", group.getvGroup());
						// model.put("CASHBOOK_DATE",
						// dateFormatonlyDate.format(date));
						HashMap<String, Object>  hashMap	= CBBL.getCashPaymentNatureWiseHashMap(Credit_Debit);
						//valOutObject.put("cashbook", CBBL.prepare_CashBookDto_ListofDto(Credit_Debit));
						valOutObject.put("cashbookMap", hashMap.get("cashBookMap"));
						valOutObject.put("paymentTypeWise", hashMap.get("paymentTypeWise"));

						// Assign Opening Balance Total

						Double[] DebitTotal = TDBL.Total_DebitTotal_CreditTotal_CashBook(Credit_Debit);

						String TotalShowDebit = AMOUNTFORMAT.format(round(DebitTotal[0], 2));
						valOutObject.put("DebitTotal", TotalShowDebit);

						String TotalShowCredit = AMOUNTFORMAT.format(round(DebitTotal[1], 2));
						valOutObject.put("CreditTotal", TotalShowCredit);

						String TotalCloseBalance = AMOUNTFORMAT.format(round(DebitTotal[2], 2));
						valOutObject.put("Balance", TotalCloseBalance);

						ConvertIntegerToWord amount = new ConvertIntegerToWord();
						Double AmountWorld = round(DebitTotal[2], 2);
						int value = Integer.valueOf(AmountWorld.intValue());
						String AmountWORLD = amount.convertNumberToWords(value);
						valOutObject.put("TotalBalanceWorld", AmountWORLD);

					} catch (Exception e) {
						LOGGER.error("Err1", e);
						e.printStackTrace();
					}

					valOutObject.put("SEARCHDATE", dateRange);

					valOutObject.put("VEHICLEGROUPID", depotId);


				} catch (Exception e) {
					LOGGER.error("Err2", e);
					e.printStackTrace();
				}

				valOutObject.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			}

			return valOutObject;
		} catch (Exception e) {
			LOGGER.error("Error ",e);
			throw e;			
		} finally {

		}
	}
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@Override
	public ValueObject getDailyTripDailyCashBookReport(ValueObject valueObject) throws Exception {

		ValueObject					valOutObject			= null;
		CustomUserDetails			userDetails				= null; 
		VehicleGroup				group					= null;
		String						dateRange				= null;
		long						depotId					= 0;
		short						status					= 0;
		HashMap<String, Object>		configuration			= null;
		HashMap<String, TripDailySheetDto>  fuelAmountHm	= null;
		try {

			if(!valueObject.containsKey("dateRange") || !valueObject.containsKey("depotId")) {
				return null;
			}

			valOutObject	= new ValueObject();
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIP_COLLECTION_CONFIG);

			dateRange		= valueObject.getString("dateRange");
			depotId			= valueObject.getLong("depotId");
			status			= valueObject.getShort("status");
			
			

			if (dateRange != "" && dateRange != null && depotId != 0) {
				
				java.util.Date date = dateFormatSQL.parse(dateRange);
				java.sql.Date fromDate = new java.sql.Date(date.getTime());
				try {
					List<TripDailyRouteSheetDto> GroupSheet = tripDailySheetService
							.List_TripDailyRouteSheet_closeDate_to_get_details(dateRange, depotId, status, userDetails.getCompany_id());
				
					
					if (GroupSheet != null && !GroupSheet.isEmpty()) {
						List<TripDailySheetDto> CollectionSheet = tripDailySheetService
								.List_TripDailySheet_closeDate_with_Group(dateRange, depotId, status, userDetails.getCompany_id());
						
						if((boolean) configuration.get(TripDailySheetCollectionConfiguration.SHOW_DIESEL_AMOUNT)) {
							fuelAmountHm	= tripDailySheetService.getFuelAmountOfTripSheet(dateRange, depotId, status, userDetails.getCompany_id());
							if(fuelAmountHm != null && fuelAmountHm.size() > 0) {
								Double totalDieselAmount = 0.0;
								 for (Map.Entry<String, TripDailySheetDto> entry : fuelAmountHm.entrySet()) {
								        totalDieselAmount += entry.getValue().getTRIP_DIESEL_AMOUNT();
								 }
								 valOutObject.put("totalDieselAmount", round(totalDieselAmount, 2));
							}
							valOutObject.put("TDRoute", TDBL.CloseDaily_tripDailyRoute_Sheet(GroupSheet, CollectionSheet, fuelAmountHm));
						}else {
							valOutObject.put("TDRoute", TDBL.CloseDaily_tripDailyRoute_Sheet(GroupSheet, CollectionSheet));
						}
							/*This Is For Chalo Details*/
						List<TripDailySheetDto> TripCollection = tripDailySheetService.list_Close_TripDailySheet(dateRange, userDetails);
						
						try {
							valOutObject.put("TDGroupCol", TDBL.CLOSED_DAILY_GROUP_SHEET(tripDailySheetService
									.GET_TripDailyGroupCollection_CloseDate(fromDate, depotId, status, userDetails.getCompany_id()),TripCollection));
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					valOutObject.put("GROUP_NAME_ID", depotId);
					group	= vehicleGroupService.getVehicleGroupByID(depotId);
					valOutObject.put("SEARCHDATE", dateRange);
					valOutObject.put("GROUP_NAME", group.getvGroup());
					valOutObject.put("configuration", configuration);

					valOutObject.put("TRIPCOL_DATE", dateFormatonlyDate.format(date));

					Date yesterday = new Date(date.getTime() - 1 * 24 * 3600 * 1000);
					valOutObject.put("YESTERDAY", dateFormatSQL.format(yesterday));
					Date tomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
					valOutObject.put("TOMORROW", dateFormatSQL.format(tomorrow));

				} catch (Exception e) {
					e.printStackTrace();
				}

				valOutObject.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

				// fuel date converted String to date Format
				CashBookName	cashBookName =	null;
				if (dateRange != null) {
					java.util.Date date3 = dateFormatSQL.parse(dateRange);
					java.sql.Date cashbookdate = new java.sql.Date(date3.getTime());

					Double OpeningBalanceTotal = 0.0, CloseingBalanceTotal = 0.0;
					int n = 1;
					/*
					 * if (CASH_BOOK_NO.equalsIgnoreCase("MAIN-CASH-BOOK")) {
					 */
					while (n < 3) {
						// this check Before Begin Opening Balances
						Date openingBalanceDate = new Date(date.getTime() - n * 24 * 3600 * 1000);
						// System.out.println("datBefore = " + n + " " +
						// openingBalanceDate);
						
						cashBookName =	cashBookNameService.getCashBookByVehicleGroupId(group.getGid(), userDetails.getCompany_id());
						if(cashBookName != null) {
							CashBookBalance OpeningBalance = CashBookService
									.Validate_Last_DayCashBook_CloseOrNot(openingBalanceDate, cashBookName.getNAMEID(), CashBookStatus.CASH_BOOK_STATUS_CLOSED, userDetails.getCompany_id());
							if (OpeningBalance != null) {
								// Assign Opening Balance Total
								OpeningBalanceTotal = OpeningBalance.getCASH_ALL_BALANCE();

								String TotalOpenBalance = AMOUNTFORMAT.format(round(OpeningBalanceTotal, 2));
								valOutObject.put("OpenBalance", TotalOpenBalance);

								// System.out.println(TotalOpenBalance);
								break;
							}
						}

						n++;
					}
					/* } */
					// When Cash Book Approval the Date only show the
					// Details
					List<CashBookDto> Credit_Debit = CashBookService.Report_CashBook_All(cashBookName.getNAMEID(), cashbookdate, userDetails.getCompany_id());

					valOutObject.put("CASHBOOK_NAME", group.getvGroup());
					valOutObject.put("CASHBOOK_DATE", dateFormatonlyDate.format(date));

					valOutObject.put("cashbook", CBBL.prepare_CashBookDto_ListDto(Credit_Debit));

					CashBookBalance CloseBalance = CashBookService.Validate_CashBookBalance_value(cashbookdate,
							cashBookName.getNAMEID(), userDetails.getCompany_id());
					if (CloseBalance != null) {
						if (CloseBalance.getCASH_STATUS_ID() == CashBookStatus.CASH_BOOK_STATUS_CLOSED) {

							// Assign Opening Balance Total
							CloseingBalanceTotal = CloseBalance.getCASH_ALL_BALANCE();

							String TotalCloseBalance = AMOUNTFORMAT.format(round(CloseingBalanceTotal, 2));
							valOutObject.put("Balance", TotalCloseBalance);

							valOutObject.put("CashBookStatus", CashBookStatus.getCashBookStatusName(CloseBalance.getCASH_STATUS_ID()));

							ConvertIntegerToWord amount = new ConvertIntegerToWord();
							Double AmountWorld = round(CloseingBalanceTotal, 2);
							int value = Integer.valueOf(AmountWorld.intValue());

							String AmountWORLD = amount.convertNumberToWords(value);
							valOutObject.put("BalanceWorld", AmountWORLD);
						} else {

							// Assign Opening Balance Total
							CloseingBalanceTotal = OpeningBalanceTotal + CloseBalance.getCASH_DAY_BALANCE();

							String TotalCloseBalance = AMOUNTFORMAT.format(round(CloseingBalanceTotal, 2));
							valOutObject.put("Balance", TotalCloseBalance);

							valOutObject.put("CashBookStatus", CashBookStatus.getCashBookStatusName(CloseBalance.getCASH_STATUS_ID()));

							ConvertIntegerToWord amount = new ConvertIntegerToWord();
							Double AmountWorld = round(CloseingBalanceTotal, 2);
							int value = Integer.valueOf(AmountWorld.intValue());

							String AmountWORLD = amount.convertNumberToWords(value);
							valOutObject.put("BalanceWorld", AmountWORLD);
						}

						String TotalShowDebit = AMOUNTFORMAT.format(round(CloseBalance.getCASH_DEBIT(), 2));
						valOutObject.put("DebitTotal", TotalShowDebit);

						Double CASH_CREDIT = OpeningBalanceTotal + CloseBalance.getCASH_CREDIT();
						String TotalShowCredit = AMOUNTFORMAT.format(round(CASH_CREDIT, 2));
						valOutObject.put("CreditTotal", TotalShowCredit);
					}

				}

			} 

			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			valOutObject			= null;
		}
	}
	
	@Override
	public ValueObject getDepotWiseTripSheetStatusReport(ValueObject valueObject) throws Exception {
		
		ValueObject						valOutObject			= null;
		CustomUserDetails				userDetails				= null; 
		String							dateRange				= null;
		HashMap<Long, Double>			tripSheetIncomeHM		= null;
		HashMap<Long, Double>			tripSheetExpenseHM		= null;
		ArrayList<TripDailySheetDto> 	tripDailySheetList 		= null;
		long							depotId					= 0;
		short 							status					= 0;
		String 							dateQuery				= "";
		String 							depotQuery				= "";
		String 							statusQuery				= "";
		String 							finalQuery				= "";
		
		
		try {
			
			if(!valueObject.containsKey("dateRange") || !valueObject.containsKey("depotId")) {
				return null;
			}

			valOutObject	= new ValueObject();
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			dateRange		= valueObject.getString("dateRange");
			depotId			= valueObject.getLong("depotId");
			status			= valueObject.getShort("status");
			
			if(dateRange != null) {
				dateQuery	= " v.TRIP_OPEN_DATE='"+dateRange+"'";
				finalQuery	+= dateQuery;
			}
			
			if(depotId > 0) {
				depotQuery	= " AND R.vehicleGroupId=" + depotId;
				finalQuery	+= depotQuery;
			}
			
			if(status > 0) {
				statusQuery = " AND v.TRIP_STATUS_ID =" + status;
				finalQuery	+= statusQuery;
			}
			
			tripSheetIncomeHM	= tripDailySheetService.getTripSheetWiseTotalIncome(finalQuery, userDetails.getCompany_id());
			tripSheetExpenseHM	= tripDailySheetService.getTripSheetWiseTotalExpense(finalQuery, userDetails.getCompany_id());
			tripDailySheetList 	= tripDailySheetService.List_TripDailySheet(finalQuery, userDetails.getCompany_id());

			tripDailySheetList	= finalizeTripDailySheet(tripDailySheetList, tripSheetIncomeHM, tripSheetExpenseHM);
			
			valOutObject.put("TDRoute", tripDailySheetList);
			valOutObject.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
			
			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			valOutObject			= null;
		}
	}

	private ArrayList<TripDailySheetDto> finalizeTripDailySheet(ArrayList<TripDailySheetDto> tripDailySheetList, HashMap<Long, Double> tripSheetIncomeHM, HashMap<Long, Double> tripSheetExpenseHM) throws Exception {
		
		try {
			
			for(TripDailySheetDto  tripDailySheet: tripDailySheetList) {
				tripDailySheet.setTRIP_OPEN_DATE(dateFormat.format(tripDailySheet.getTRIP_OPEN_DATE_D()));
				tripDailySheet.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName(tripDailySheet.getTRIP_STATUS_ID()));
				if(tripSheetIncomeHM.get(tripDailySheet.getTRIPDAILYID()) != null) {
					tripDailySheet.setTOTAL_INCOME(Double.parseDouble(new DecimalFormat("##.##").format(tripSheetIncomeHM.get(tripDailySheet.getTRIPDAILYID()))));					
				} else {
					tripDailySheet.setTOTAL_INCOME(0.0);										
				}
				if(tripSheetExpenseHM.get(tripDailySheet.getTRIPDAILYID()) != null) {
					tripDailySheet.setTOTAL_EXPENSE(Double.parseDouble(new DecimalFormat("##.##").format(tripSheetExpenseHM.get(tripDailySheet.getTRIPDAILYID()))));					
				} else {
					tripDailySheet.setTOTAL_EXPENSE(0.0);										
				}
			}
			
			return tripDailySheetList;
		} catch (Exception e) {
			throw e;
		}
	}

	public List<TripDailySheetDto> getTreepCollectionInfo(Integer ROUTE_ID, String dateRangeFrom,
			String dateRangeTo, List<TripDailyTimeIncomeDto> getFixedTypeIncomeName, Integer companyId) throws Exception {
		String IncomeName = " ";
		if (getFixedTypeIncomeName != null && !getFixedTypeIncomeName.isEmpty()) {
			for (TripDailyTimeIncomeDto tripDailyIncome : getFixedTypeIncomeName) {

				IncomeName += " , SUM(CASE WHEN TI.incomeName = '" + tripDailyIncome.getIncomeName()
						+ "' AND p.markForDelete=0 AND p.companyId = " + companyId
						+ " THEN p.incomeAmount ELSE 0 END) AS " + tripDailyIncome.getCreatedBy() + " ";
			}

		}
		javax.persistence.Query query = entityManager.createQuery("SELECT R.TRIPDAILYID, V.vehicle_registration, TR.routeName,  R.TRIP_OPEN_DATE, R.TRIP_USAGE_KM, R.TRIP_DIESEL, R.TRIP_DIESELKMPL,  "
						+ " R.TRIP_RFIDPASS, R.TRIP_RFID_AMOUNT, R.TOTAL_INCOME,  R.TOTAL_EXPENSE, R.TOTAL_BALANCE, R.TRIP_STATUS_ID" + IncomeName
						+ " FROM TripDailySheet AS R INNER JOIN TripDailyTimeIncome AS p "
						+ "  ON R.TRIPDAILYID = p.tripDailysheet.TRIPDAILYID "
						+ " INNER JOIN TripIncome TI ON TI.incomeID = p.incomeId "
						+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
						+ " INNER JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID"						
						+ " WHERE R.markForDelete=0 AND R.TRIP_SUBROUTE_ID=" + ROUTE_ID
						+ " AND R.TRIP_OPEN_DATE BETWEEN '" + dateRangeFrom + "' AND '" + dateRangeTo + "'  "
						+ "  AND R.TRIP_STATUS_ID=" + TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND R.COMPANY_ID = ");
			
			@SuppressWarnings("unchecked")
			List<Object[]>	results = query.getResultList();
			
			List<TripDailySheetDto>	list	= null;
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					TripDailySheetDto TDSD =new TripDailySheetDto();

					TDSD.setTRIPDAILYID((Long) result[0]);
					TDSD.setVEHICLE_REGISTRATION((String) result[1]);

					TDSD.setTRIP_ROUTE_NAME((String) result[2]);

					if (result[3] != null) {
						TDSD.setTRIP_OPEN_DATE(dateFormatonlyDate.format((java.util.Date) result[3]));
					}

					TDSD.setTRIP_USAGE_KM((Integer) result[4]);
					TDSD.setTRIP_DIESEL((Double) result[5]);
					/*list.setFuel_amount((Double) result[6]);*/
					TDSD.setTRIP_DIESELKMPL((Double) result[6]);
					TDSD.setTRIP_RFIDPASS((Integer) result[7]);
					TDSD.setTRIP_RFID_AMOUNT((Double) result[8]);
					TDSD.setTOTAL_INCOME((Double) result[9]);
					TDSD.setTOTAL_EXPENSE((Double) result[10]);
					TDSD.setTOTAL_BALANCE((Double) result[11]);
					TDSD.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[12]));
					TDSD.setTOTAL_INCOME_COLLECTION((Double) (TDSD.getTRIP_RFID_AMOUNT()+ TDSD.getTOTAL_INCOME()));
					TDSD.setTOTAL_NET_BALANCE((Double) (TDSD.getTOTAL_INCOME_COLLECTION() - TDSD.getTOTAL_EXPENSE()));
					
					list.add(TDSD);
				}
			
			}
				
			return list;


} 

	@Override
	public ValueObject getTreepCollectionExpense(ValueObject object) throws Exception {
		ValueObject    					valOutObject			= null;
		String							dateRange				= null;
		Integer 						ROUTE_ID				= 0;
		long							depotId					= 0;
		String 							queryString				= "";
		short							loadTypeId				= 0;
	try {
	

			CustomUserDetails usrDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			dateRange		= object.getString("dateRange");
			depotId			= object.getInt("vehicleGroupId",0);
			ROUTE_ID		= object.getInt("routeId",0);
			if(object.containsKey("loadTypeId")) {
				loadTypeId	= object.getShort("loadTypeId", (short) 0);
			}
				

			if (ROUTE_ID > 0) {
				queryString  += "AND R.routeID =" + ROUTE_ID + " ";
			}
			if(depotId>0) {
				queryString  += "AND V.vehicleGroupId =" + depotId + " ";	
			}
			if(loadTypeId > 0) {
				queryString  += "AND R.loadTypeId =" + loadTypeId + " ";
			}
			
			
			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;

			From_TO_DateRange = dateRange.split(" to ");

			dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			TypedQuery<Object[]> typedQuery = null;

			typedQuery = entityManager.createQuery(
					"SELECT R.tripSheetID, V.vehicle_registration, D1.driver_firstname, D2.driver_firstname, "  
					+ " R.tripTotalincome, R.tripTotalexpense, R.tripOpenDate, R.vid, TP.routeName " 
					+ " FROM TripSheet AS R "
					+ " LEFT JOIN Driver D1 ON D1.driver_id = R.tripFristDriverID "
					+ " LEFT JOIN TripRoute TP ON TP.routeID= R.routeID "
					+ " LEFT JOIN Driver D2 ON D2.driver_id = R.tripSecDriverID "
					+ " INNER JOIN Vehicle V ON V.vid= R.vid "
					+ " Where R.markForDelete=0 and R.companyId = "+usrDetails.getCompany_id()+" "
					+ " AND R.closetripDate BETWEEN '" + dateRangeFrom + "' AND  '" 
					+ dateRangeTo + "' "+queryString+" ",	
							Object[].class);

			
			List<Object[]> results = typedQuery.getResultList();
			
			List<TripSheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetDto>();
				TripSheetDto list = null;
				for (Object[] result : results) {
					list = new TripSheetDto();
					
					list.setTripSheetID((Long) result[0]);
					list.setVehicle_registration((String) result[1]);
					list.setTripFristDriverName((String) result[2]);
					list.setTripSecDriverName((String) result[3]);
					if( result[4] != null)
						list.setTotalIncomeCollection((double) result[4]);
					if( result[5] != null)
						list.setTripTotalexpense((double) result[5]);
					if( result[6] != null)
						list.setTripOpenDate(dateFormat.format((Date) result[6]));
					list.setVid((Integer) result[7]);
					list.setTRIP_ROUTE_NAME((String) result[8]);
					
					Dtos.add(list);
					
				}
			}
			valOutObject	= new ValueObject();
		
			valOutObject.put("TripCollectionExpenseList", Dtos);
			
			
		
			return	valOutObject;
			
		}
		 catch (Exception e) {
			throw e;
		} finally {
			valOutObject			= null;
		}


	}

	


	
	

	
	
	@Override
	public ValueObject getTripCollectionExpenseName(ValueObject valueInObject) throws Exception {

		ValueObject				valueOutObject				= null;
		String					dateRange					= null;
		Integer					ExpenseId					= null;
		String					queryString					= "";

		try {

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			dateRange	= valueInObject.getString("dateRange");
			ExpenseId	    = valueInObject.getInt("ExpenseId",0);
		

			if (ExpenseId > 0) {
				queryString  += "AND R.expenseId =" + ExpenseId + " ";
			}
			
			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;

			From_TO_DateRange = dateRange.split(" to ");

			dateRangeFrom = From_TO_DateRange[0];
			dateRangeTo = From_TO_DateRange[1] + InventoryTransferDto.DAY_END_TIME;


			TypedQuery<Object[]> typedQuery = null;
			
			
			typedQuery = entityManager.createQuery(
					"SELECT TS.TRIPDAILYNUMBER, TE.expenseName, R.expenseAmount, "
							+ " TS.TRIP_OPEN_DATE, V.vehicle_registration, V.vid, TS.TRIPDAILYID FROM TripDailyExpense as R "
							+ " INNER JOIN TripExpense TE ON TE.expenseID = R.expenseId "
							+ " INNER JOIN TripDailySheet TS ON TS.TRIPDAILYID = R.tripDailysheet.TRIPDAILYID "
							+ " INNER JOIN Vehicle V ON V.vid= TS.VEHICLEID "
							+ " Where R.markForDelete=0 "+queryString+" "
							+ " AND R.companyId = " + userDetails.getCompany_id() + " AND TS.TRIP_OPEN_DATE BETWEEN '" + dateRangeFrom + "' AND  '"
							+ dateRangeTo + "'  ORDER BY TS.TRIP_OPEN_DATE desc ",		
							Object[].class);
			

			
			List<Object[]> results = typedQuery.getResultList();
			

			List<TripDailyExpenseDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				
				Dtos = new ArrayList<TripDailyExpenseDto>();
				TripDailyExpenseDto list = null;
				for (Object[] result : results) {
					list = new TripDailyExpenseDto();
					
					list.setTRIPDAILYNUMBER((Long) result[0]);
					list.setExpenseName((String) result[1]);
					list.setExpenseAmount((Double) result[2]);
					list.setCreatedDate(dateFormatonlyDate.format((Date) result[3]));
					list.setVehicle_registration((String) result[4]);
					list.setVid((Integer) result[5]);
					list.setTripDailysheetID((Long) result[6]);
					
					Dtos.add(list);
				}
			}
			valueOutObject	= new ValueObject();
		
			valueOutObject.put("TripCollectionExpenseName", Dtos);
			

		
			return	valueOutObject;
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

}
