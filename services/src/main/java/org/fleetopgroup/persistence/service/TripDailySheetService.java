/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.constant.TripRouteFixedType;
import org.fleetopgroup.constant.VehicleFuelType;
import org.fleetopgroup.persistence.dao.TripDailyAllGroupDayCollectionRepository;
import org.fleetopgroup.persistence.dao.TripDailyExpenseRepository;
import org.fleetopgroup.persistence.dao.TripDailyGroupCollectionRepository;
import org.fleetopgroup.persistence.dao.TripDailyIncomeRepository;
import org.fleetopgroup.persistence.dao.TripDailyRouteSheetRepository;
import org.fleetopgroup.persistence.dao.TripDailySheetRepository;
import org.fleetopgroup.persistence.dao.TripDailyTimeIncomeRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripDailyAllGroupDayDto;
import org.fleetopgroup.persistence.dto.TripDailyExpenseDto;
import org.fleetopgroup.persistence.dto.TripDailyGroupCollectionDto;
import org.fleetopgroup.persistence.dto.TripDailyIncomeDto;
import org.fleetopgroup.persistence.dto.TripDailyRouteSheetDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.dto.TripDailyTimeIncomeDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.model.TripDailyAllGroupDay;
import org.fleetopgroup.persistence.model.TripDailyExpense;
import org.fleetopgroup.persistence.model.TripDailyGroupCollection;
import org.fleetopgroup.persistence.model.TripDailyIncome;
import org.fleetopgroup.persistence.model.TripDailyRouteSheet;
import org.fleetopgroup.persistence.model.TripDailySheet;
import org.fleetopgroup.persistence.model.TripDailyTimeIncome;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
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
/**
 * @author fleetop
 *
 */
@Service("TripDailySheetService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TripDailySheetService implements ITripDailySheetService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private TripDailySheetRepository TripDailySheet;

	@Autowired
	private TripDailyExpenseRepository tripDailyExpense;

	@Autowired
	private TripDailyIncomeRepository tripDailyIncome;

	@Autowired
	private TripDailyTimeIncomeRepository tripDailyTimeIncome;

	@Autowired
	private TripDailyRouteSheetRepository tripDailyRouteSheet;

	@Autowired
	private TripDailyGroupCollectionRepository DailyGroupCollectionRepository;

	@Autowired
	private TripDailyAllGroupDayCollectionRepository AllGroupDayCollectionRepository;

	@Autowired
	private ICompanyConfigurationService companyConfigurationService;

	private static final int PAGE_SIZE = 10;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * add_TripDailySheet(org.fleetop.persistence.model.TripDailySheet)
	 */
	@Transactional
	public void add_TripDailySheet(TripDailySheet DailySheet) throws Exception {

		TripDailySheet.save(DailySheet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * add_TripDailyRoute_Collection(org.fleetop.persistence.model.
	 * TripDailyRouteSheet)
	 */
	@Transactional
	public void add_TripDailyRoute_Collection(TripDailyRouteSheet RouteSheet) throws Exception {

		tripDailyRouteSheet.save(RouteSheet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * add_TripDailyExpense(org.fleetop.persistence.model.TripDailyExpense)
	 */
	@Transactional
	public void add_TripDailyExpense(TripDailyExpense DailyExpense) throws Exception {

		tripDailyExpense.save(DailyExpense);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * add_TripDailyIncome(org.fleetop.persistence.model.TripDailyIncome)
	 */
	@Transactional
	public void add_TripDailyIncome(TripDailyIncome DailyIncome) throws Exception {

		tripDailyIncome.save(DailyIncome);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * list_Today_TripDailySheet(java.lang.String)
	 */
	@Transactional
	public List<TripDailySheetDto> list_Today_TripDailySheet(String date, CustomUserDetails userDetails)
			throws Exception {

		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.TRIPDAILYID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_TOTALPASSNGER, "
							+ "R.TRIP_OVERTIME, R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_STATUS_ID, R.TRIPDAILYNUMBER, R.CHALO_KM , R.CHALO_AMOUNT "
							+ " FROM TripDailySheet AS R "
							+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " Where R.markForDelete=0 AND  R.TRIP_OPEN_DATE='" + date + "' AND R.COMPANY_ID = "
							+ userDetails.getCompany_id() + " ORDER BY R.TRIPDAILYID desc ",
					Object[].class);
		} else {
			queryt = entityManager.createQuery(
					"SELECT R.TRIPDAILYID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_TOTALPASSNGER, "
							+ "R.TRIP_OVERTIME, R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_STATUS_ID, R.TRIPDAILYNUMBER, R.CHALO_KM , R.CHALO_AMOUNT "
							+ " FROM TripDailySheet AS R "
							+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "
							+ userDetails.getId() + "" + " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " Where R.markForDelete=0 AND  R.TRIP_OPEN_DATE='" + date + "' AND R.COMPANY_ID = "
							+ userDetails.getCompany_id() + " ORDER BY R.TRIPDAILYID desc ",
					Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<TripDailySheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto list = null;
			for (Object[] result : results) {
				list = new TripDailySheetDto();

				list.setTRIPDAILYID((Long) result[0]);
				list.setVEHICLE_REGISTRATION((String) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_ROUTE_NAME((String) result[3]);
				list.setTRIP_OPEN_DATE_D((Date) result[4]);
				list.setTRIP_TOTALPASSNGER((Integer) result[5]);
				list.setTOTAL_EXPENSE((Double) result[6]);
				list.setTRIP_OVERTIME((Double) result[7]);
				list.setTOTAL_INCOME((Double) result[8]);
				list.setTOTAL_BALANCE((Double) result[9]);
				list.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[10]));
				list.setTRIP_STATUS_ID((short) result[10]);
				list.setTRIPDAILYNUMBER((Long) result[11]);
				list.setCHALO_KM((Integer) result[12]);
				list.setCHALO_AMOUNT((Double) result[13]);
				

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<TripDailySheet> list_Today_TripDailySheet(String date, String Status, String VEHICLE_GROUP) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TRIPDAILYID, R.VEHICLE_REGISTRATION, R.VEHICLE_GROUP, R.TRIP_ROUTE_NAME, R.TRIP_OPEN_DATE, R.TRIP_TOTALPASSNGER, "
						+ "R.TRIP_OVERTIME, R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_CLOSE_STATUS FROM TripDailySheet AS R "
						+ "Where  R.markForDelete=0 AND  R.TRIP_OPEN_DATE='" + date + "' AND R.TRIP_CLOSE_STATUS='"
						+ Status + "' AND R.VEHICLE_GROUP='" + VEHICLE_GROUP + "' ORDER BY R.TRIPDAILYID desc ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripDailySheet> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailySheet>();
			TripDailySheet list = null;
			for (Object[] result : results) {
				list = new TripDailySheet();

				list.setTRIPDAILYID((Long) result[0]);
				//list.setVEHICLE_REGISTRATION((String) result[1]);
				//list.setVEHICLE_GROUP((String) result[2]);
				//list.setTRIP_ROUTE_NAME((String) result[3]);
				list.setTRIP_OPEN_DATE((Date) result[4]);
				list.setTRIP_TOTALPASSNGER((Integer) result[5]);
				list.setTOTAL_EXPENSE((Double) result[6]);
				list.setTRIP_OVERTIME((Double) result[7]);
				list.setTOTAL_INCOME((Double) result[8]);
				list.setTOTAL_BALANCE((Double) result[9]);
			//	list.setTRIP_CLOSE_STATUS((String) result[10]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * count_TripCollectionSheet()
	 */
	@Override
	public Object count_TripCollectionSheet() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * List_TripDailySheetService_closeDate(java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<TripDailyRouteSheet> List_TripDailySheetService_closeDate(String VEHICLE_GROUP, String trip_OPEN_DATE,
			String TRIP_CLOSE_STATUS) {

		TypedQuery<TripDailyRouteSheet> query = entityManager.createQuery(
				"FROM TripDailyRouteSheet as v Where  v.VEHICLE_GROUP='" + VEHICLE_GROUP + "' AND v.TRIP_OPEN_DATE='"
						+ trip_OPEN_DATE + "' AND TRIP_CLOSE_STATUS='" + TRIP_CLOSE_STATUS + "'",
				TripDailyRouteSheet.class);
		return query.getResultList();
	}

	@Override
	public List<TripDailyRouteSheet> List_TripDailySheetService_closeDate(long VEHICLE_GROUP_ID, String trip_OPEN_DATE,
			short TRIP_CLOSE_STATUS, Integer companyId) {

		TypedQuery<TripDailyRouteSheet> query = entityManager
				.createQuery(
						"FROM TripDailyRouteSheet as v Where  v.VEHICLE_GROUP_ID=" + VEHICLE_GROUP_ID
								+ " AND v.TRIP_OPEN_DATE='" + trip_OPEN_DATE + "' AND TRIP_STATUS_ID="
								+ TRIP_CLOSE_STATUS + " AND COMPANY_ID = " + companyId + " AND markForDelete = 0",
						TripDailyRouteSheet.class);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * update_TripDailySheet_TotalExpense(java.lang.Double, java.lang.Long)
	 */
	/*
	 * @Transactional public void update_TripDailySheet_TotalExpense(Double
	 * tripTotalExpense, Long tripdailyid) {
	 * 
	 * TripDailySheet.update_TripDailySheet_TotalExpense(tripTotalExpense,
	 * tripdailyid); }
	 */

	@Transactional
	public void update_TripDailySheet_TotalExpense(Long tripdailyid) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		entityManager.createQuery(
				"UPDATE TripDailySheet AS T SET T.TOTAL_EXPENSE = (SELECT SUM(expenseAmount) FROM TripDailyExpense WHERE  markForDelete=0 AND  TRIPDAILYID ="
						+ tripdailyid + " AND companyId = " + userDetails.getCompany_id()
						+ ")  WHERE  T.markForDelete=0 AND  T.TRIPDAILYID=" + tripdailyid + " AND T.COMPANY_ID = "
						+ userDetails.getCompany_id() + "")
				.executeUpdate();
	}
	
	@Transactional
	public int update_Chalo_Details(Long tripdailyid, Integer chaloKm, Double chaloAmount) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int updatedrows = entityManager.createQuery("UPDATE TripDailySheet SET CHALO_KM = "+chaloKm+", CHALO_AMOUNT="+chaloAmount+" "
					+ " WHERE TRIPDAILYID=" + tripdailyid + " AND markForDelete= 0 AND COMPANY_ID = "+userDetails.getCompany_id()+" ")
				.executeUpdate();
		return updatedrows;
	}
	
	@Transactional
	public void delete_Chalo_Details(Long tripdailyid, Integer companyId) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		entityManager.createQuery(
				"UPDATE TripDailySheet SET CHALO_KM = 0, CHALO_AMOUNT = 0 "
					+ " WHERE TRIPDAILYID = "+tripdailyid+" AND COMPANY_ID = "+userDetails.getCompany_id()+" AND markForDelete = 0 ")
				.executeUpdate();
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Get_Showing_TripDaily_Sheet(java.lang.Long)
	 */
	@Transactional
	public TripDailySheet Get_Showing_TripDaily_Sheet(Long tripdailyid) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return TripDailySheet.Get_Showing_TripDaily_Sheet(tripdailyid, userDetails.getCompany_id());
		}
		return TripDailySheet.Get_Showing_TripDaily_Sheet(tripdailyid, userDetails.getId(),
				userDetails.getCompany_id());
	}

	@Override
	public TripDailySheetDto Get_Showing_TripDaily_Sheet(Long tripdailyid, CustomUserDetails userDetails)
			throws Exception {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT R.TRIPDAILYID, R.TRIPDAILYNUMBER, R.VEHICLEID, V.vehicle_registration, VG.vGroup, VG.gid,"
						+ " R.TRIP_DRIVER_ID, D.driver_firstname, R.TRIP_CONDUCTOR_ID, D2.driver_firstname, R.TRIP_CLEANER_ID, D3.driver_firstname,"
						+ " R.TRIP_OPEN_DATE, TR.routeName, R.TRIP_ROUTE_ID, R.TRIP_DIESEL, R.TRIP_OPEN_KM, R.TRIP_CLOSE_KM, R.TRIP_USAGE_KM,  "
						+ " R.TRIP_TOTALPASSNGER, R.TRIP_PASS_PASSNGER, R.TRIP_RFIDPASS, R.TRIP_RFID_AMOUNT, R.TRIP_OVERTIME,"
						+ " R.TRIP_DIESELKMPL, R.TOTAL_BALANCE, R.TOTAL_EXPENSE, R.TOTAL_INCOME_COLLECTION, R.TOTAL_INCOME, R.TOTAL_NET_INCOME,"
						+ " R.TOTAL_WT, R.TRIP_STATUS_ID, R.TRIP_REMARKS, R.CREATED, R.LASTUPDATED, U.email, co.email, R.CREATED_BY_ID, R.LASTMODIFIED_BY_ID,"
						+ " R.noOfRoundTrip, R.CHALO_KM, R.CHALO_AMOUNT"
						+ " FROM TripDailySheet as R " + " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID"
						+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Driver D ON D.driver_id = R.TRIP_DRIVER_ID"
						+ " LEFT JOIN Driver D2 ON D2.driver_id = R.TRIP_CONDUCTOR_ID"
						+ " LEFT JOIN Driver D3 ON D3.driver_id = R.TRIP_CLEANER_ID"
						+ " LEFT JOIN User U ON U.id = R.CREATED_BY_ID "
						+ " LEFT JOIN User co ON co.id = R.LASTMODIFIED_BY_ID "
						+ " Where R.markForDelete=0 AND R.TRIPDAILYID =" + tripdailyid + " AND R.COMPANY_ID = "
						+ userDetails.getCompany_id() + " ORDER BY R.TRIPDAILYID desc ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();
		List<TripDailySheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto list = null;
			for (Object[] result : results) {
				list = new TripDailySheetDto();
				list.setTRIPDAILYID((Long) result[0]);
				list.setTRIPDAILYNUMBER((Long) result[1]);
				list.setVEHICLEID((Integer) result[2]);
				list.setVEHICLE_REGISTRATION((String) result[3]);
				list.setVEHICLE_GROUP((String) result[4]);
				list.setVEHICLE_GROUP_ID((long) result[5]);
				list.setTRIP_DRIVER_ID((int) result[6]);
				list.setTRIP_DRIVER_NAME((String) result[7]);
				list.setTRIP_CONDUCTOR_ID((int) result[8]);
				list.setTRIP_CONDUCTOR_NAME((String) result[9]);
				list.setTRIP_CLEANER_ID((int) result[10]);
				list.setTRIP_CLEANER_NAME((String) result[11]);
				list.setTRIP_OPEN_DATE_D((Date) result[12]);
				list.setTRIP_ROUTE_NAME((String) result[13]);
				list.setTRIP_ROUTE_ID((Integer) result[14]);
				list.setTRIP_DIESEL((Double) result[15]);
				list.setTRIP_OPEN_KM((Integer) result[16]);
				list.setTRIP_CLOSE_KM((Integer) result[17]);
				list.setTRIP_USAGE_KM((Integer) result[18]);
				list.setTRIP_TOTALPASSNGER((Integer) result[19]);
				list.setTRIP_PASS_PASSNGER((Integer) result[20]);
				list.setTRIP_RFIDPASS((Integer) result[21]);
				list.setTRIP_RFID_AMOUNT((Double) result[22]);
				list.setTRIP_OVERTIME((Double) result[23]);
				list.setTRIP_DIESELKMPL((Double) result[24]);
				list.setTOTAL_BALANCE((Double) result[25]);
				list.setTOTAL_EXPENSE((Double) result[26]);
				list.setTOTAL_INCOME_COLLECTION((Double) result[27]);
				list.setTOTAL_INCOME((Double) result[28]);
				list.setTOTAL_NET_INCOME((Double) result[29]);
				list.setTOTAL_WT((Double) result[30]);
				list.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[31]));
				list.setTRIP_STATUS_ID((short) result[31]);
				list.setTRIP_REMARKS((String) result[32]);
				list.setCREATEDON((Date) result[33]);
				list.setLASTUPDATEDON((Date) result[34]);
				list.setCREATEDBY((String) result[35]);
				list.setLASTMODIFIEDBY((String) result[36]);
				list.setCREATED_BY_ID((Long) result[37]);
				list.setLASTMODIFIED_BY_ID((Long) result[38]);
				if(result[39] != null) {
					list.setNoOfRoundTrip((float) result[39]);
				}
				if(result[40]!=null) {
				list.setCHALO_KM((int) result[40]);
				list.setCHALO_AMOUNT((double) result[41]);
				}
			

				Dtos.add(list);
			}
		}
		if(Dtos != null && !Dtos.isEmpty()) {
			return Dtos.get(0);
		}else {
			return null;
		}
			
	}

	@Override
	public TripDailySheetDto Get_Showing_TripDaily_SheetByNumber(Long tripdailyid, CustomUserDetails userDetails)
			throws Exception {
		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					" SELECT R.TRIPDAILYID, R.TRIPDAILYNUMBER, R.VEHICLEID, V.vehicle_registration, VG.vGroup, VG.gid,"
							+ " R.TRIP_DRIVER_ID,D.driver_firstname, R.TRIP_CONDUCTOR_ID, D2.driver_firstname, R.TRIP_CLEANER_ID, D3.driver_firstname,"
							+ " R.TRIP_OPEN_DATE, TR.routeName, R.TRIP_ROUTE_ID, R.TRIP_DIESEL, R.TRIP_OPEN_KM, R.TRIP_CLOSE_KM, R.TRIP_USAGE_KM,  "
							+ " R.TRIP_TOTALPASSNGER, R.TRIP_PASS_PASSNGER, R.TRIP_RFIDPASS, R.TRIP_RFID_AMOUNT, R.TRIP_OVERTIME,"
							+ " R.TRIP_DIESELKMPL, R.TOTAL_BALANCE, R.TOTAL_EXPENSE, R.TOTAL_INCOME_COLLECTION, R.TOTAL_INCOME, R.TOTAL_NET_INCOME,"
							+ " R.TOTAL_WT, R.TRIP_STATUS_ID, R.TRIP_REMARKS, R.CREATED, R.LASTUPDATED, U.email, co.email, R.CREATED_BY_ID, R.LASTMODIFIED_BY_ID "
							+ " FROM TripDailySheet as R " + " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.TRIP_DRIVER_ID"
							+ " LEFT JOIN Driver D2 ON D2.driver_id = R.TRIP_CONDUCTOR_ID"
							+ " LEFT JOIN Driver D3 ON D3.driver_id = R.TRIP_CLEANER_ID"
							+ " LEFT JOIN User U ON U.id = R.CREATED_BY_ID "
							+ " LEFT JOIN User co ON co.id = R.LASTMODIFIED_BY_ID "
							+ " Where R.markForDelete=0 AND R.TRIPDAILYNUMBER =" + tripdailyid + " AND R.COMPANY_ID = "
							+ userDetails.getCompany_id() + " ORDER BY R.TRIPDAILYID desc ",
					Object[].class);
		} else {
			queryt = entityManager.createQuery(
					" SELECT R.TRIPDAILYID, R.TRIPDAILYNUMBER, R.VEHICLEID, V.vehicle_registration, VG.vGroup, VG.gid,"
							+ " R.TRIP_DRIVER_ID, D.driver_firstname, R.TRIP_CONDUCTOR_ID, D2.driver_firstname, R.TRIP_CLEANER_ID, D3.driver_firstname,"
							+ " R.TRIP_OPEN_DATE, TR.routeName, R.TRIP_ROUTE_ID, R.TRIP_DIESEL, R.TRIP_OPEN_KM, R.TRIP_CLOSE_KM, R.TRIP_USAGE_KM,  "
							+ " R.TRIP_TOTALPASSNGER, R.TRIP_PASS_PASSNGER, R.TRIP_RFIDPASS, R.TRIP_RFID_AMOUNT, R.TRIP_OVERTIME,"
							+ " R.TRIP_DIESELKMPL, R.TOTAL_BALANCE, R.TOTAL_EXPENSE, R.TOTAL_INCOME_COLLECTION, R.TOTAL_INCOME, R.TOTAL_NET_INCOME,"
							+ " R.TOTAL_WT, R.TRIP_STATUS_ID, R.TRIP_REMARKS, R.CREATED, R.LASTUPDATED, U.email, co.email, R.CREATED_BY_ID, R.LASTMODIFIED_BY_ID "
							+ " FROM TripDailySheet as R "
							+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "
							+ userDetails.getId() + "" + " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID"
							+ " LEFT JOIN Driver D ON D.driver_id = R.TRIP_DRIVER_ID"
							+ " LEFT JOIN Driver D2 ON D2.driver_id = R.TRIP_CONDUCTOR_ID"
							+ " LEFT JOIN Driver D3 ON D3.driver_id = R.TRIP_CLEANER_ID"
							+ " LEFT JOIN User U ON U.id = R.CREATED_BY_ID "
							+ " LEFT JOIN User co ON co.id = R.LASTMODIFIED_BY_ID "
							+ " Where R.markForDelete=0 AND R.TRIPDAILYNUMBER =" + tripdailyid + " AND R.COMPANY_ID = "
							+ userDetails.getCompany_id() + " ORDER BY R.TRIPDAILYID desc ",
					Object[].class);
		}

		List<Object[]> results = queryt.getResultList();
		List<TripDailySheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto list = null;
			for (Object[] result : results) {
				list = new TripDailySheetDto();
				list.setTRIPDAILYID((Long) result[0]);
				list.setTRIPDAILYNUMBER((Long) result[1]);
				list.setVEHICLEID((Integer) result[2]);
				list.setVEHICLE_REGISTRATION((String) result[3]);
				list.setVEHICLE_GROUP((String) result[4]);
				list.setVEHICLE_GROUP_ID((long) result[5]);
				list.setTRIP_DRIVER_ID((int) result[6]);
				list.setTRIP_DRIVER_NAME((String) result[7]);
				list.setTRIP_CONDUCTOR_ID((int) result[8]);
				list.setTRIP_CONDUCTOR_NAME((String) result[9]);
				list.setTRIP_CLEANER_ID((int) result[10]);
				list.setTRIP_CLEANER_NAME((String) result[11]);
				list.setTRIP_OPEN_DATE_D((Date) result[12]);
				list.setTRIP_ROUTE_NAME((String) result[13]);
				list.setTRIP_ROUTE_ID((Integer) result[14]);
				list.setTRIP_DIESEL((Double) result[15]);
				list.setTRIP_OPEN_KM((Integer) result[16]);
				list.setTRIP_CLOSE_KM((Integer) result[17]);
				list.setTRIP_USAGE_KM((Integer) result[18]);
				list.setTRIP_TOTALPASSNGER((Integer) result[19]);
				list.setTRIP_PASS_PASSNGER((Integer) result[20]);
				list.setTRIP_RFIDPASS((Integer) result[21]);
				list.setTRIP_RFID_AMOUNT((Double) result[22]);
				list.setTRIP_OVERTIME((Double) result[23]);
				list.setTRIP_DIESELKMPL((Double) result[24]);
				list.setTOTAL_BALANCE((Double) result[25]);
				list.setTOTAL_EXPENSE((Double) result[26]);
				list.setTOTAL_INCOME_COLLECTION((Double) result[27]);
				list.setTOTAL_INCOME((Double) result[28]);
				list.setTOTAL_NET_INCOME((Double) result[29]);
				list.setTOTAL_WT((Double) result[30]);
				list.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[31]));
				list.setTRIP_STATUS_ID((short) result[31]);
				list.setTRIP_REMARKS((String) result[32]);
				list.setCREATEDON((Date) result[33]);
				list.setLASTUPDATEDON((Date) result[34]);
				list.setCREATEDBY((String) result[35]);
				list.setLASTMODIFIEDBY((String) result[36]);
				list.setCREATED_BY_ID((Long) result[37]);
				list.setLASTMODIFIED_BY_ID((Long) result[38]);

				Dtos.add(list);
			}
		}
		return Dtos.get(0);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * findAll_TripDailySheet_ID_Expense(java.lang.Long)
	 */
	/*@Transactional
	public List<TripDailyExpense> findAll_TripDailySheet_ID_Expense(Long tripdailyid) {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.tripExpenseID, R.expenseName, R.expenseAmount, R.expenseRefence, R.expenseFixed, "
						+ "R.createdBy, R.created FROM TripDailyExpense as R"
						+ " Where R.markForDelete=0 AND R.tripDailysheet.TRIPDAILYID =" + tripdailyid
						+ " AND R.companyId = " + userDetails.getCompany_id() + " ORDER BY R.tripExpenseID desc ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripDailyExpense> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyExpense>();
			TripDailyExpense list = null;
			for (Object[] result : results) {
				list = new TripDailyExpense();

				list.setTripExpenseID((Long) result[0]);
				list.setExpenseName((String) result[1]);
				list.setExpenseAmount((Double) result[2]);
				list.setExpenseRefence((String) result[3]);
				list.setExpenseFixed((String) result[4]);
				list.setCreatedBy((String) result[5]);
				list.setCreated((Date) result[6]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}
*/
	@Override
	public List<TripDailyExpenseDto> findAll_TripDailySheet_ID_Expense(Long tripdailyid, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.tripExpenseID, TE.expenseName, R.expenseAmount, R.expenseRefence, R.fixedTypeId, "
						+ "U.email, R.created, R.expenseId, R.fuel_id FROM TripDailyExpense as R"
						+ " LEFT JOIN TripExpense TE ON TE.expenseID = R.expenseId AND (R.fuel_id IS NULL OR R.fuel_id = 0)"
						+ " LEFT JOIN User U ON U.id = R.createdById"
						+ " Where R.markForDelete=0 AND R.tripDailysheet.TRIPDAILYID =" + tripdailyid
						+ " AND R.companyId = " + companyId + " ORDER BY R.tripExpenseID desc ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripDailyExpenseDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyExpenseDto>();
			TripDailyExpenseDto list = null;
			for (Object[] result : results) {
				list = new TripDailyExpenseDto();

				list.setTripExpenseID((Long) result[0]);
				list.setExpenseName((String) result[1]);
				list.setExpenseAmount((Double) result[2]);
				list.setExpenseRefence((String) result[3]);
				list.setExpenseFixed(TripRouteFixedType.getFixedTypeName((short) result[4]));
				list.setFixedTypeId((short) result[4]);
				list.setCreatedBy((String) result[5]);
				list.setCreated((Date) result[6]);
				list.setExpenseId((Integer) result[7]);
				if (list.getExpenseName() == null || list.getExpenseName() == "") {
					list.setExpenseName(VehicleFuelType.getVehicleFuelName(Short.parseShort((Integer) result[7] + "")));
				}
				list.setFuel_id((Long) result[8]);
				Dtos.add(list);
			}
		}
		return Dtos;

	}

	/*Chalo Info Start*/	
	@Override
	public List<TripDailySheetDto> findChaloInformation(Long tripdailyid, Integer companyId)
			throws Exception {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT TD.CHALO_KM, TD.CHALO_AMOUNT FROM TripDailySheet AS TD "					
						+ " WHERE TD.markForDelete=0 AND TD.TRIPDAILYID =" + tripdailyid
						+ " AND TD.COMPANY_ID = " + companyId + " ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();
		
		List<TripDailySheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto list = null;
			for (Object[] result : results) {
				list = new TripDailySheetDto();					
				list.setCHALO_KM((Integer) result[0]);				
				list.setCHALO_AMOUNT((Double) result[1]);				
				Dtos.add(list);				
			}
			
		}
		return Dtos;		
	}
	/*Chalo Info Stop*/
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * findAll_TripDailySheet_ID_Income(java.lang.Long)
	 */
	/*@Transactional
	public List<TripDailyIncome> findAll_TripDailySheet_ID_Income(Long tripdailyid) {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.tripincomeID, R.incomeName, R.incomeAmount, R.incomeRefence, R.incomeFixed, R.incomeCollectedBy, "
						+ "R.createdBy, R.created FROM TripDailyIncome as R"
						+ " Where R.markForDelete=0 AND R.tripDailysheet.TRIPDAILYID =" + tripdailyid
						+ " AND R.companyId = " + userDetails.getCompany_id() + " ORDER BY R.tripincomeID desc ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripDailyIncome> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyIncome>();
			TripDailyIncome list = null;
			for (Object[] result : results) {
				list = new TripDailyIncome();

				list.setTripincomeID((Long) result[0]);
				list.setIncomeName((String) result[1]);
				list.setIncomeAmount((Double) result[2]);
				list.setIncomeRefence((String) result[3]);
				list.setIncomeFixed((String) result[4]);
				list.setIncomeCollectedBy((String) result[5]);
				list.setCreatedBy((String) result[6]);
				list.setCreated((Date) result[7]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}
*/
	@Override
	public List<TripDailyIncomeDto> findAll_TripDailySheet_ID_Income(Long tripdailyid, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.tripincomeID, TI.incomeName, R.incomeAmount, R.incomeRefence, R.incomeTypeId, U.firstName, U.lastName, "
						+ "U.email, R.created, R.incomeId FROM TripDailyIncome as R "
						+ " LEFT JOIN TripIncome TI ON TI.incomeID = R.incomeId "
						+ " LEFT JOIN User U ON U.id = R.incomeCollectedById"
						+ " Where R.markForDelete=0 AND R.tripDailysheet.TRIPDAILYID =" + tripdailyid
						+ " AND R.companyId = " + companyId + " ORDER BY R.tripincomeID desc ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripDailyIncomeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyIncomeDto>();
			TripDailyIncomeDto list = null;
			for (Object[] result : results) {
				list = new TripDailyIncomeDto();

				list.setTripincomeID((Long) result[0]);
				list.setIncomeName((String) result[1]);
				list.setIncomeAmount((Double) result[2]);
				list.setIncomeRefence((String) result[3]);
				list.setIncomeFixed(TripRouteFixedType.getFixedTypeName((short) result[4]));
				list.setIncomeTypeId((short) result[4]);
				list.setIncomeCollectedBy((String) result[5] + " " + (String) result[6]);
				list.setCreatedBy((String) result[7]);
				list.setCreated((Date) result[8]);
				list.setIncomeId((Integer) result[9]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Validate_TripDaily_Expense(java.lang.String, java.lang.Long)
	 */
	@Transactional
	public List<TripDailyExpense> Validate_TripDaily_Expense(Integer string, Long tripdailyid, Integer companyId) {

		return tripDailyExpense.Validate_TripDaily_Expense(string, tripdailyid, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * findAll_TripDaily_Expense(java.lang.Long)
	 */
	@Transactional
	public List<TripDailyExpense> findAll_TripDaily_Expense(Long tripdailyid) {

		return tripDailyExpense.findAll_TripDailySheet_ID_Expense(tripdailyid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Get_TripDaily_Expense(java.lang.Long)
	 */
	@Transactional
	public TripDailyExpense Get_TripDaily_Expense(Long tripExpenseID, Integer companyId) {

		return tripDailyExpense.Get_TripDaily_Expense(tripExpenseID, companyId);
	}

	
	@Override
	public TripDailyExpense Get_TripDaily_Expense(Integer tripExpenseID, Integer companyId) {
		
		return tripDailyExpense.Get_TripDaily_Expense(tripExpenseID, companyId);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * delete_TripDailyExpense(java.lang.Long)
	 */
	@Transactional
	public void delete_TripDailyExpense(Long tripExpenseID, Integer companyId) {

		tripDailyExpense.delete_TripDailyExpense(tripExpenseID, companyId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * findAll_TripDaily_Income(java.lang.Long)
	 */
	@Transactional
	public List<TripDailyIncome> findAll_TripDaily_Income(Long tripdailyid) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return tripDailyIncome.findAll_TripDaily_Income(tripdailyid, userDetails.getCompany_id());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Validate_TripDaily_Income(java.lang.String, java.lang.Long)
	 */
	@Transactional
	public List<TripDailyIncome> Validate_TripDaily_Income(Integer string, Long tripdailyid, Integer companyId) {

		return tripDailyIncome.Validate_TripDaily_Income(string, tripdailyid, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * update_TripDaily_TotalIncome(java.lang.Double, java.lang.Long)
	 */
	/*
	 * @Transactional public void update_TripDaily_TotalIncome(Double
	 * tripTotalIncome, Long tripdailyid) {
	 * 
	 * TripDailySheet.update_TripDaily_TotalIncome(tripTotalIncome, tripdailyid);
	 * 
	 * }
	 */

	@Transactional
	public void update_TripDaily_TotalIncome(Long tripdailyid) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		entityManager.createQuery(
				"UPDATE TripDailySheet AS T SET T.TOTAL_INCOME = (SELECT SUM(incomeAmount) FROM TripDailyIncome  WHERE  markForDelete=0 AND  TRIPDAILYID ="
						+ tripdailyid + " AND companyId = " + userDetails.getCompany_id()
						+ ")  WHERE  T.markForDelete=0 AND  T.TRIPDAILYID=" + tripdailyid + " AND T.COMPANY_ID = "
						+ userDetails.getCompany_id() + "")
				.executeUpdate();
	}
	
	@Override
	@Transactional
	public void update_TripDaily_TotalIncome(Long tripdailyid, Integer companyId) {
		
		entityManager.createQuery(
				"UPDATE TripDailySheet AS T SET T.TOTAL_INCOME = (SELECT SUM(incomeAmount) FROM TripDailyIncome  WHERE  markForDelete=0 AND  TRIPDAILYID ="
						+ tripdailyid + " AND companyId = " + companyId
						+ ")  WHERE  T.markForDelete=0 AND  T.TRIPDAILYID=" + tripdailyid + " AND T.COMPANY_ID = "
						+ companyId + "")
				.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Get_TripDaily_Income(java.lang.Long)
	 */
	@Transactional
	public TripDailyIncome Get_TripDaily_Income(Long tripIncomeID, Integer companyId) {

		return tripDailyIncome.Get_TripDaily_Income(tripIncomeID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * delete_TripDaily_Income(java.lang.Long)
	 */
	@Transactional
	public void delete_TripDaily_Income(Long tripincomeID, Integer companyId) {

		tripDailyIncome.delete_TripDaily_Income(tripincomeID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Validate_TripCollectionSheet_Date_VehicleName(java.sql.Date,
	 * java.lang.Integer)
	 */
	@Transactional
	public List<TripDailySheet> Validate_TripCollectionSheet_Date_VehicleName(java.sql.Date fromDate,
			Integer vehicleid) {

		return TripDailySheet.Validate_TripCollectionSheet_Date_VehicleName(fromDate, vehicleid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Update_TripDailysheet_Deisel_and_KMPL(java.lang.Double, java.lang.Double,
	 * java.lang.Long)
	 */
	@Transactional
	public void Update_TripDailysheet_Deisel_and_KMPL(Long tRIPSHEET_ID, Integer companyId) {

		entityManager.createQuery(
				"UPDATE TripDailySheet  SET TRIP_DIESEL = (SELECT ROUND(SUM(L.fuel_liters), 2) FROM Fuel AS L  WHERE L. markForDelete=0 AND  L.fuel_TripsheetID ="
						+ tRIPSHEET_ID + " AND L.companyId = " + companyId
						+ "), TRIP_DIESELKMPL = (SELECT ROUND(AVG(K.fuel_kml), 2) FROM Fuel AS K WHERE  K.markForDelete=0 AND  K.fuel_TripsheetID ="
						+ tRIPSHEET_ID + " AND K.companyId = " + companyId + ") where  markForDelete=0 AND TRIPDAILYID="
						+ tRIPSHEET_ID + " AND COMPANY_ID = " + companyId + "")
				.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Update_Close_TripDailySheet(java.lang.Integer, java.lang.Integer,
	 * java.lang.String, java.lang.Double, java.lang.String, java.sql.Date,
	 * java.lang.Long)
	 */
	@Transactional
	public void Update_Close_TripDailySheet(Integer closingKM, Integer usageKM, short status, Double TOTAL_NET_INCOME,
			Double balance, Timestamp lASTUPDATED, Long tripdailyid, Integer companyId,
			Long lastUpdatedById, float noOfRoundTrip) {

		TripDailySheet.Update_Close_TripDailySheet(closingKM, usageKM, status, TOTAL_NET_INCOME, balance,
				lASTUPDATED, tripdailyid, companyId, lastUpdatedById, noOfRoundTrip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Validate_TripDailyRoute_SheetIs_or_not(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<TripDailyRouteSheet> Validate_TripDailyRoute_SheetIs_or_not(String trip_OPEN_DATE,
			Integer trip_ROUTE_ID, long vehicle_GROUP_ID, Integer companyId) {

		TypedQuery<TripDailyRouteSheet> query = entityManager
				.createQuery(
						"FROM TripDailyRouteSheet as v Where v.TRIP_OPEN_DATE='" + trip_OPEN_DATE
								+ "' AND v.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND v.VEHICLE_GROUP_ID="
								+ vehicle_GROUP_ID + " AND v.COMPANY_ID = " + companyId + "",
						TripDailyRouteSheet.class);
		return query.getResultList();
	}

	@Transactional
	public void update_TripDailyRoute_already(String trip_OPEN_DATE, Integer trip_ROUTE_ID, long vehicle_GROUP,
			Long triprouteid, Integer companyId) {

		entityManager.createQuery(
				"UPDATE TripDailyRouteSheet  SET TOTAL_COLLECTION = (SELECT SUM(T.TOTAL_INCOME) FROM TripDailySheet AS T "
				+ " INNER JOIN Vehicle V ON V.vid = T.VEHICLEID "
				+ " WHERE T.markForDelete=0 AND  T.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  T.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND T.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V.vehicleGroupId=" + vehicle_GROUP
						+ " AND T.COMPANY_ID = " + companyId + "), "
						+ " TOTAL_NET_COLLECTION = (SELECT SUM(T.TOTAL_NET_INCOME) FROM TripDailySheet AS T "
						+ " INNER JOIN Vehicle V1 ON V1.vid = T.VEHICLEID "
						+ " WHERE T.markForDelete=0 AND  T.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  T.TRIP_OPEN_DATE='" + trip_OPEN_DATE + "'"
						+ " AND T.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V1.vehicleGroupId=" + vehicle_GROUP
						+ " AND T.COMPANY_ID = " + companyId + "), "
						+ " TOTAL_WT = (SELECT SUM(T.TOTAL_WT) FROM TripDailySheet AS T "
						+ " INNER JOIN Vehicle V2 ON V2.vid = T.VEHICLEID "
						+ " WHERE T.markForDelete=0 AND  T.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  T.TRIP_OPEN_DATE='" + trip_OPEN_DATE + "'"
						+ " AND T.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V2.vehicleGroupId=" + vehicle_GROUP
						+ " AND T.COMPANY_ID = " + companyId + "), "
						+ " TOTAL_EXPENSE = (SELECT SUM(E.TOTAL_EXPENSE) FROM TripDailySheet AS E "
						+ " INNER JOIN Vehicle V3 ON V3.vid = E.VEHICLEID "
						+ " WHERE E.markForDelete=0 AND E.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  E.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND E.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V3.vehicleGroupId=" + vehicle_GROUP
						+ " AND E.COMPANY_ID = " + companyId + "), "
						+ "TOTAL_BALANCE = (SELECT SUM(B.TOTAL_BALANCE) FROM TripDailySheet AS B "
						+ " INNER JOIN Vehicle V4 ON V4.vid = B.VEHICLEID "
						+ " WHERE B.markForDelete=0 AND  B.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  B.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND B.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V4.vehicleGroupId=" + vehicle_GROUP
						+ " AND B.COMPANY_ID = " + companyId + ") ,"
						+ " TOTAL_OVERTIME = (SELECT SUM(O.TRIP_OVERTIME) FROM TripDailySheet AS O"
						+ " INNER JOIN Vehicle V5 ON V5.vid = O.VEHICLEID "
						+ "  WHERE O.markForDelete=0 AND O.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  O.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND O.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V5.vehicleGroupId=" + vehicle_GROUP
						+ " AND O.COMPANY_ID = " + companyId + "),"
						+ " TOTAL_RFID_AMOUNT = (SELECT SUM(T.TRIP_RFID_AMOUNT) FROM TripDailySheet AS T "
						+ " INNER JOIN Vehicle V6 ON V6.vid = T.VEHICLEID "
						+ " WHERE T.markForDelete=0 AND  T.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  T.TRIP_OPEN_DATE='" + trip_OPEN_DATE + "'"
						+ " AND T.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V6.vehicleGroupId=" + vehicle_GROUP
						+ " AND T.COMPANY_ID = " + companyId + "), "
						+ " TOTAL_RFIDPASS = (SELECT SUM(R.TRIP_RFIDPASS) FROM TripDailySheet AS R"
						+ " INNER JOIN Vehicle V7 ON V7.vid = R.VEHICLEID "
						+ " WHERE R.markForDelete=0 AND R.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  R.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND R.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V7.vehicleGroupId=" + vehicle_GROUP
						+ " AND R.COMPANY_ID = " + companyId + "), "
						+ " TOTAL_PASS_PASSNGER = (SELECT SUM(T.TRIP_PASS_PASSNGER) FROM TripDailySheet AS T "
						+ " INNER JOIN Vehicle V8 ON V8.vid = T.VEHICLEID "
						+ " WHERE T.markForDelete=0 AND  T.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  T.TRIP_OPEN_DATE='" + trip_OPEN_DATE + "'"
						+ " AND T.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V8.vehicleGroupId=" + vehicle_GROUP
						+ " AND T.COMPANY_ID = " + companyId + "), "
						+ "TOTAL_TOTALPASSNGER = (SELECT SUM(P.TRIP_TOTALPASSNGER) FROM TripDailySheet AS P "
						+ " INNER JOIN Vehicle V9 ON V9.vid = P.VEHICLEID "
						+ " WHERE P.markForDelete=0 AND  P.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  P.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND P.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V9.vehicleGroupId=" + vehicle_GROUP
						+ " AND P.COMPANY_ID = " + companyId
						+ "), TOTAL_DIESEL = (SELECT SUM(D.TRIP_DIESEL) FROM TripDailySheet AS D "
						+ " INNER JOIN Vehicle V10 ON V10.vid = D.VEHICLEID "
						+ " WHERE D.markForDelete=0 AND D.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND   D.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND D.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V10.vehicleGroupId=" + vehicle_GROUP
						+ " AND D.COMPANY_ID = " + companyId
						+ ") , TOTAL_USAGE_KM = (SELECT SUM(U.TRIP_USAGE_KM) FROM TripDailySheet AS U"
						+ " INNER JOIN Vehicle V11 ON V11.vid = U.VEHICLEID "
						+ " WHERE U.markForDelete=0 AND U.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  U.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND U.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V11.vehicleGroupId=" + vehicle_GROUP
						+ " AND U.COMPANY_ID = " + companyId + " ) where TRIPROUTEID=" + triprouteid
						+ " AND COMPANY_ID = " + companyId + "")
				.executeUpdate();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * ReOpen_TripDaily_Sheet_ClosedOnly(java.lang.Long)
	 */
	@Transactional
	public TripDailySheetDto ReOpen_TripDaily_Sheet_ClosedOnly(Long tripCollectionID, Integer companyId) {

		//return TripDailySheet.ReOpen_TripDaily_Sheet_ClosedOnly(tripCollectionID, companyId);
		Query query = entityManager.createQuery(
					" SELECT R.TRIPDAILYID, R.TRIPDAILYNUMBER, R.VEHICLEID, V.vehicle_registration, VG.vGroup, VG.gid,"
							+ " R.TRIP_DRIVER_ID,D.driver_firstname, R.TRIP_CONDUCTOR_ID, D2.driver_firstname, R.TRIP_CLEANER_ID, D3.driver_firstname,"
							+ " R.TRIP_OPEN_DATE, TR.routeName, R.TRIP_ROUTE_ID, R.TRIP_DIESEL, R.TRIP_OPEN_KM, R.TRIP_CLOSE_KM, R.TRIP_USAGE_KM,  "
							+ " R.TRIP_TOTALPASSNGER, R.TRIP_PASS_PASSNGER, R.TRIP_RFIDPASS, R.TRIP_RFID_AMOUNT, R.TRIP_OVERTIME,"
							+ " R.TRIP_DIESELKMPL, R.TOTAL_BALANCE, R.TOTAL_EXPENSE, R.TOTAL_INCOME_COLLECTION, R.TOTAL_INCOME, R.TOTAL_NET_INCOME,"
							+ " R.TOTAL_WT, R.TRIP_STATUS_ID, R.TRIP_REMARKS, R.CREATED, R.LASTUPDATED, U.email, co.email, R.CREATED_BY_ID, R.LASTMODIFIED_BY_ID "
							+ " FROM TripDailySheet as R " + " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.TRIP_DRIVER_ID"
							+ " LEFT JOIN Driver D2 ON D2.driver_id = R.TRIP_CONDUCTOR_ID"
							+ " LEFT JOIN Driver D3 ON D3.driver_id = R.TRIP_CLEANER_ID"
							+ " LEFT JOIN User U ON U.id = R.CREATED_BY_ID "
							+ " LEFT JOIN User co ON co.id = R.LASTMODIFIED_BY_ID "
							+ " Where R.markForDelete=0 AND R.TRIPDAILYID =" + tripCollectionID + " AND R.COMPANY_ID = "
							+ companyId + " AND R.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" ORDER BY R.TRIPDAILYID desc");
		
		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		TripDailySheetDto list;
		if (result != null) {
				list = new TripDailySheetDto();
				list.setTRIPDAILYID((Long) result[0]);
				list.setTRIPDAILYNUMBER((Long) result[1]);
				list.setVEHICLEID((Integer) result[2]);
				list.setVEHICLE_REGISTRATION((String) result[3]);
				list.setVEHICLE_GROUP((String) result[4]);
				list.setVEHICLE_GROUP_ID((long) result[5]);
				list.setTRIP_DRIVER_ID((int) result[6]);
				list.setTRIP_DRIVER_NAME((String) result[7]);
				list.setTRIP_CONDUCTOR_ID((int) result[8]);
				list.setTRIP_CONDUCTOR_NAME((String) result[9]);
				list.setTRIP_CLEANER_ID((int) result[10]);
				list.setTRIP_CLEANER_NAME((String) result[11]);
				list.setTRIP_OPEN_DATE_D((Date) result[12]);
				list.setTRIP_ROUTE_NAME((String) result[13]);
				list.setTRIP_ROUTE_ID((Integer) result[14]);
				list.setTRIP_DIESEL((Double) result[15]);
				list.setTRIP_OPEN_KM((Integer) result[16]);
				list.setTRIP_CLOSE_KM((Integer) result[17]);
				list.setTRIP_USAGE_KM((Integer) result[18]);
				list.setTRIP_TOTALPASSNGER((Integer) result[19]);
				list.setTRIP_PASS_PASSNGER((Integer) result[20]);
				list.setTRIP_RFIDPASS((Integer) result[21]);
				list.setTRIP_RFID_AMOUNT((Double) result[22]);
				list.setTRIP_OVERTIME((Double) result[23]);
				list.setTRIP_DIESELKMPL((Double) result[24]);
				list.setTOTAL_BALANCE((Double) result[25]);
				list.setTOTAL_EXPENSE((Double) result[26]);
				list.setTOTAL_INCOME_COLLECTION((Double) result[27]);
				list.setTOTAL_INCOME((Double) result[28]);
				list.setTOTAL_NET_INCOME((Double) result[29]);
				list.setTOTAL_WT((Double) result[30]);
				list.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[31]));
				list.setTRIP_STATUS_ID((short) result[31]);
				list.setTRIP_REMARKS((String) result[32]);
				list.setCREATEDON((Date) result[33]);
				list.setLASTUPDATEDON((Date) result[34]);
				list.setCREATEDBY((String) result[35]);
				list.setLASTMODIFIEDBY((String) result[36]);
				list.setCREATED_BY_ID((Long) result[37]);
				list.setLASTMODIFIED_BY_ID((Long) result[38]);

		} else {
			return null;
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Update_ReOpen_Status_TripDailySheet(java.lang.Long, java.lang.String)
	 */
	@Transactional
	public void Update_ReOpen_Status_TripDailySheet(Long tripdailyid, short status, Integer companyId) {

		TripDailySheet.Update_ReOpen_Status_TripDailySheet(tripdailyid, status, companyId);
	}

	@Transactional
	public void Update_ReOpen_To_Subtrack_TripDailyRouteSheet_to_tripDaily(String trip_OPEN_DATE, long vehicle_GROUP,
			Integer trip_ROUTE_ID, Integer companyId) {

		entityManager.createQuery(
				"UPDATE TripDailyRouteSheet  SET TOTAL_COLLECTION = (SELECT COALESCE(SUM(T.TOTAL_INCOME), 0) FROM TripDailySheet AS T"
						+ " INNER JOIN Vehicle V ON V.vid = T.VEHICLEID "
						+ " WHERE T.markForDelete=0 AND  T.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  T.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND T.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V.vehicleGroupId=" + vehicle_GROUP
						+ " AND T.COMPANY_ID = " + companyId + "), "
						+ " TOTAL_NET_COLLECTION = (SELECT COALESCE(SUM(T.TOTAL_NET_INCOME), 0) FROM TripDailySheet AS T"
						+ " INNER JOIN Vehicle V1 ON V1.vid = T.VEHICLEID "
						+ " WHERE T.markForDelete=0 AND  T.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  T.TRIP_OPEN_DATE='" + trip_OPEN_DATE + "'"
						+ " AND T.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V1.vehicleGroupId=" + vehicle_GROUP
						+ " AND T.COMPANY_ID = " + companyId + "), "
						+ " TOTAL_WT = (SELECT COALESCE(SUM(T.TOTAL_WT), 0) FROM TripDailySheet AS T"
						+ " INNER JOIN Vehicle V2 ON V2.vid = T.VEHICLEID "
						+ " WHERE T.markForDelete=0 AND  T.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  T.TRIP_OPEN_DATE='" + trip_OPEN_DATE + "'"
						+ " AND T.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V2.vehicleGroupId=" + vehicle_GROUP
						+ " AND T.COMPANY_ID = " + companyId + "), "
						+ " TOTAL_EXPENSE = (SELECT COALESCE(SUM(E.TOTAL_EXPENSE), 0) FROM TripDailySheet AS E"
						+ " INNER JOIN Vehicle V3 ON V3.vid = E.VEHICLEID "
						+ " WHERE E.markForDelete=0 AND E.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  E.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND E.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V3.vehicleGroupId=" + vehicle_GROUP
						+ " AND E.COMPANY_ID = " + companyId + "), "
						+ "TOTAL_BALANCE = (SELECT COALESCE(SUM(B.TOTAL_BALANCE), 0) FROM TripDailySheet AS B "
						+ " INNER JOIN Vehicle V4 ON V4.vid = B.VEHICLEID "
						+ " WHERE B.markForDelete=0 AND  B.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  B.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND B.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V4.vehicleGroupId=" + vehicle_GROUP
						+ " AND B.COMPANY_ID = " + companyId + ") ,"
						+ " TOTAL_OVERTIME = (SELECT COALESCE(SUM(O.TRIP_OVERTIME), 0) FROM TripDailySheet AS O "
						+ " INNER JOIN Vehicle V5 ON V5.vid = O.VEHICLEID "
						+ " WHERE O.markForDelete=0 AND O.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  O.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND O.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V5.vehicleGroupId=" + vehicle_GROUP
						+ " AND O.COMPANY_ID = " + companyId + "),"
						+ " TOTAL_RFID_AMOUNT = (SELECT COALESCE(SUM(T.TRIP_RFID_AMOUNT), 0) FROM TripDailySheet AS T"
						+ " INNER JOIN Vehicle V6 ON V6.vid = T.VEHICLEID "
						+ " WHERE T.markForDelete=0 AND  T.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  T.TRIP_OPEN_DATE='" + trip_OPEN_DATE + "'"
						+ " AND T.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V6.vehicleGroupId=" + vehicle_GROUP
						+ " AND T.COMPANY_ID = " + companyId + "), "
						+ " TOTAL_RFIDPASS = (SELECT COALESCE(SUM(R.TRIP_RFIDPASS), 0) FROM TripDailySheet AS R"
						+ " INNER JOIN Vehicle V7 ON V7.vid = R.VEHICLEID "
						+ " WHERE R.markForDelete=0 AND R.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  R.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND R.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V7.vehicleGroupId=" + vehicle_GROUP
						+ " AND R.COMPANY_ID = " + companyId + "), "
						+ " TOTAL_PASS_PASSNGER = (SELECT COALESCE(SUM(T.TRIP_PASS_PASSNGER), 0) FROM TripDailySheet AS T "
						+ " INNER JOIN Vehicle V8 ON V8.vid = T.VEHICLEID "
						+ " WHERE T.markForDelete=0 AND  T.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  T.TRIP_OPEN_DATE='" + trip_OPEN_DATE + "'"
						+ " AND T.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V8.vehicleGroupId=" + vehicle_GROUP
						+ " AND T.COMPANY_ID = " + companyId + "), "
						+ "TOTAL_TOTALPASSNGER = (SELECT COALESCE(SUM(P.TRIP_TOTALPASSNGER),0) FROM TripDailySheet AS P"
						+ " INNER JOIN Vehicle V9 ON V9.vid = P.VEHICLEID "
						+ " WHERE P.markForDelete=0 AND  P.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  P.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND P.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V9.vehicleGroupId=" + vehicle_GROUP
						+ " AND P.COMPANY_ID = " + companyId
						+ "), TOTAL_DIESEL = (SELECT COALESCE(SUM(D.TRIP_DIESEL),0) FROM TripDailySheet AS D "
						+ " INNER JOIN Vehicle V10 ON V10.vid = D.VEHICLEID "
						+ " WHERE D.markForDelete=0 AND D.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND   D.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND D.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V10.vehicleGroupId=" + vehicle_GROUP
						+ " AND D.COMPANY_ID = " + companyId
						+ ") , TOTAL_USAGE_KM = (SELECT COALESCE(SUM(U.TRIP_USAGE_KM),0) FROM TripDailySheet AS U "
						+ " INNER JOIN Vehicle V11 ON V11.vid = U.VEHICLEID "
						+ " WHERE U.markForDelete=0 AND U.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND  U.TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND U.TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND V11.vehicleGroupId=" + vehicle_GROUP
						+ " AND U.COMPANY_ID = " + companyId + ") where TRIP_OPEN_DATE='" + trip_OPEN_DATE
						+ "' AND TRIP_ROUTE_ID =" + trip_ROUTE_ID + " AND VEHICLE_GROUP_ID=" + vehicle_GROUP
						+ " AND COMPANY_ID = " + companyId + " ")
				.executeUpdate();

	}

	
	/*@Transactional
	public List<TripDailySheet> List_TripDailySheet_closeDate_with_Group(String trip_OPEN_DATE, long vEHICLEGROUP,
			short TRIP_CLOSE_STATUS) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		TypedQuery<Object[]> query = null;
		try {
			query = entityManager.createQuery(
					"SELECT v.TRIPDAILYID, v.TRIP_ROUTE_NAME, v.TRIP_DRIVER_NAME, v.TRIP_CONDUCTOR_NAME, v.VEHICLE_REGISTRATION,"
							+ " v.TOTAL_INCOME, v.TOTAL_WT, v.TOTAL_NET_INCOME, v.TOTAL_INCOME, v.TRIP_USAGE_KM, v.TOTAL_EXPENSE, v.TOTAL_BALANCE,"
							+ " v.TRIP_OVERTIME, v.TRIP_RFIDPASS, v.TRIP_RFID_AMOUNT,  v.TRIP_TOTALPASSNGER, v.TRIP_PASS_PASSNGER,"
							+ " v.TRIP_USAGE_KM, v.TRIP_DIESEL, v.TRIP_DIESELKMPL, v.TRIP_ROUTE_ID FROM TripDailySheet as v Where v.markForDelete=0 AND v.TRIP_OPEN_DATE='"
							+ trip_OPEN_DATE + "' AND v.VEHICLE_GROUP_ID=" + vEHICLEGROUP + " AND v.TRIP_STATUS_ID="
							+ TRIP_CLOSE_STATUS + " AND v.COMPANY_ID = " + userDetails.getCompany_id()
							+ " AND v.markForDelete = 0",
					Object[].class);

			List<Object[]> results = query.getResultList();

			List<TripDailySheet> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripDailySheet>();
				TripDailySheet tripSheet = null;
				for (Object[] result : results) {
					tripSheet = new TripDailySheet();

					tripSheet.setTRIPDAILYID((Long) result[0]);
					tripSheet.setTRIP_ROUTE_NAME((String) result[1]);

					tripSheet.setTRIP_DRIVER_NAME((String) result[2]);
					tripSheet.setTRIP_CONDUCTOR_NAME((String) result[3]);

					tripSheet.setVEHICLE_REGISTRATION((String) result[4]);
					tripSheet.setTOTAL_INCOME((Double) result[5]);

					tripSheet.setTOTAL_WT((Double) result[6]);
					tripSheet.setTOTAL_NET_INCOME((Double) result[7]);

					tripSheet.setTOTAL_INCOME((Double) result[8]);
					tripSheet.setTRIP_USAGE_KM((Integer) result[9]);
					tripSheet.setTOTAL_EXPENSE((Double) result[10]);
					tripSheet.setTOTAL_BALANCE((Double) result[11]);
					tripSheet.setTRIP_OVERTIME((Double) result[12]);

					tripSheet.setTRIP_RFIDPASS((Integer) result[13]);
					tripSheet.setTRIP_RFID_AMOUNT((Double) result[14]);

					tripSheet.setTRIP_TOTALPASSNGER((Integer) result[15]);
					tripSheet.setTRIP_PASS_PASSNGER((Integer) result[16]);

					tripSheet.setTRIP_USAGE_KM((Integer) result[17]);
					tripSheet.setTRIP_DIESEL((Double) result[18]);
					tripSheet.setTRIP_DIESELKMPL((Double) result[19]);
					tripSheet.setTRIP_ROUTE_ID((Integer) result[20]);
					Dtos.add(tripSheet);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		} finally {
			query = null;
		}
		// TypedQuery<TripDailySheet> query = entityManager
		// .createQuery(
		// "FROM TripDailySheet as v Where v.markForDelete=0 AND v.TRIP_OPEN_DATE='" +
		// trip_OPEN_DATE
		// + "' AND v.VEHICLE_GROUP='" + vEHICLEGROUP + "' AND v.TRIP_CLOSE_STATUS='"
		// + TRIP_CLOSE_STATUS + "' AND v.COMPANY_ID = " + userDetails.getCompany_id() +
		// "",
		// TripDailySheet.class);
		// return query.getResultList();
	}
*/
	@Override
	public List<TripDailySheetDto> List_TripDailySheet_closeDate_with_Group(String trip_OPEN_DATE, long vEHICLEGROUP,
			short TRIP_CLOSE_STATUS, Integer companyId) {

		TypedQuery<Object[]> query = null;
		try {
			query = entityManager.createQuery(
					"SELECT v.TRIPDAILYID, TR.routeName, D.driver_firstname, D2.driver_firstname, R.vehicle_registration,"
							+ " v.TOTAL_INCOME, v.TOTAL_WT, v.TOTAL_NET_INCOME, v.TOTAL_INCOME, v.TRIP_USAGE_KM, v.TOTAL_EXPENSE, v.TOTAL_BALANCE,"
							+ " v.TRIP_OVERTIME, v.TRIP_RFIDPASS, v.TRIP_RFID_AMOUNT,  v.TRIP_TOTALPASSNGER, v.TRIP_PASS_PASSNGER,"
							+ " v.TRIP_USAGE_KM, v.TRIP_DIESEL, v.TRIP_DIESELKMPL, v.TRIPDAILYNUMBER , v.TRIP_ROUTE_ID, v.CHALO_KM, v.CHALO_AMOUNT "
							+ " FROM TripDailySheet as v "
							+ " INNER JOIN Vehicle R ON R.vid = v.VEHICLEID"
							+ " LEFT JOIN Driver AS D ON D.driver_id = v.TRIP_DRIVER_ID"
							+ " LEFT JOIN Driver AS D2 ON D2.driver_id = v.TRIP_CONDUCTOR_ID"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = v.TRIP_ROUTE_ID"
							+ " Where v.TRIP_OPEN_DATE='"
							+ trip_OPEN_DATE + "' AND R.vehicleGroupId=" + vEHICLEGROUP + " AND v.TRIP_STATUS_ID ="
							+ TRIP_CLOSE_STATUS + " AND v.COMPANY_ID = " + companyId + " AND v.markForDelete = 0",
					Object[].class);

			List<Object[]> results = query.getResultList();

			List<TripDailySheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripDailySheetDto>();
				TripDailySheetDto tripSheet = null;
				for (Object[] result : results) {
					tripSheet = new TripDailySheetDto();

					tripSheet.setTRIPDAILYID((Long) result[0]);
					tripSheet.setTRIP_ROUTE_NAME((String) result[1]);

					tripSheet.setTRIP_DRIVER_NAME((String) result[2]);
					tripSheet.setTRIP_CONDUCTOR_NAME((String) result[3]);

					tripSheet.setVEHICLE_REGISTRATION((String) result[4]);
					tripSheet.setTOTAL_INCOME((Double) result[5]);

					tripSheet.setTOTAL_WT((Double) result[6]);
					tripSheet.setTOTAL_NET_INCOME((Double) result[7]);

					tripSheet.setTOTAL_INCOME((Double) result[8]);
					tripSheet.setTRIP_USAGE_KM((Integer) result[9]);
					if(result[10] != null) {
					tripSheet.setTOTAL_EXPENSE((Double) result[10]);
					}
					tripSheet.setTOTAL_BALANCE((Double) result[11]);
					tripSheet.setTRIP_OVERTIME((Double) result[12]);

					tripSheet.setTRIP_RFIDPASS((Integer) result[13]);
					tripSheet.setTRIP_RFID_AMOUNT((Double) result[14]);

					tripSheet.setTRIP_TOTALPASSNGER((Integer) result[15]);
					tripSheet.setTRIP_PASS_PASSNGER((Integer) result[16]);

					tripSheet.setTRIP_USAGE_KM((Integer) result[17]);
					tripSheet.setTRIP_DIESEL((Double) result[18]);
					tripSheet.setTRIP_DIESELKMPL((Double) result[19]);

					tripSheet.setTRIPDAILYNUMBER((Long) result[20]);
					tripSheet.setTRIP_ROUTE_ID((Integer) result[21]);
					
					if(result[22] != null) {
					tripSheet.setCHALO_KM((int) result[22]);
					}
					if(result[23] != null) {
					tripSheet.setCHALO_AMOUNT((double) result[23]);
					}
					
					Dtos.add(tripSheet);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		} finally {
			query = null;
		}

	}
	
	@Override
	public HashMap<String, TripDailySheetDto> getFuelAmountOfTripSheet(String trip_OPEN_DATE, long vEHICLEGROUP,
			short TRIP_CLOSE_STATUS, Integer companyId) throws Exception {

		TypedQuery<Object[]> query = null;
		try {
			query = entityManager.createQuery(
					"SELECT v.TRIPDAILYID, F.fuel_amount "
							+ " FROM TripDailySheet as v "
							+ " INNER JOIN Vehicle R ON R.vid = v.VEHICLEID "
							+ " INNER JOIN Fuel AS F ON F.fuel_TripsheetID = v.TRIPDAILYID AND F.companyId = "+companyId+" AND F.markForDelete = 0"
							+ " Where v.TRIP_OPEN_DATE='"
							+ trip_OPEN_DATE + "' AND R.vehicleGroupId=" + vEHICLEGROUP + " AND v.TRIP_STATUS_ID ="
							+ TRIP_CLOSE_STATUS + " AND v.COMPANY_ID = " + companyId + " AND v.markForDelete = 0",
					Object[].class);

			List<Object[]> results = query.getResultList();

			HashMap<String, TripDailySheetDto>  hashMap	= new HashMap<>();
			if (results != null && !results.isEmpty()) {
				TripDailySheetDto	dailySheetDto 		= null;
				TripDailySheetDto	tripDailySheetDto	= null;
				for (Object[] result : results) {
					dailySheetDto	= new TripDailySheetDto();
					
					dailySheetDto.setTRIPDAILYID((Long) result[0]);
					dailySheetDto.setTRIP_DIESEL_AMOUNT((Double) result[1]);
					tripDailySheetDto	=	hashMap.get(dailySheetDto.getTRIPDAILYID()+"");
					
					if(tripDailySheetDto == null) {
						tripDailySheetDto = new TripDailySheetDto();
						tripDailySheetDto = dailySheetDto;
					}else {
						tripDailySheetDto.setTRIP_DIESEL_AMOUNT(tripDailySheetDto.getTRIP_DIESEL_AMOUNT() + dailySheetDto.getTRIP_DIESEL_AMOUNT());
					}
					
					hashMap.put(dailySheetDto.getTRIPDAILYID()+"", tripDailySheetDto);
				}
			}
			return hashMap;
		} catch (Exception e) {
			throw e;
		} finally {
			query = null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * List_TripDailyRouteSheet_closeDate_to_get_details(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<TripDailyRouteSheet> List_TripDailyRouteSheet_closeDate_to_get_details(String trip_OPEN_DATE,
			long vEHICLEGROUP_ID, short TRIP_CLOSE_STATUS) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<TripDailyRouteSheet> query = entityManager
				.createQuery(
						"FROM TripDailyRouteSheet as v Where  v.TRIP_OPEN_DATE='" + trip_OPEN_DATE
								+ "' AND v.VEHICLE_GROUP_ID=" + vEHICLEGROUP_ID + " AND v.TRIP_STATUS_ID="
								+ TRIP_CLOSE_STATUS + " AND v.COMPANY_ID = " + userDetails.getCompany_id() + "",
						TripDailyRouteSheet.class);
		return query.getResultList();
	}

	@Override
	public List<TripDailyRouteSheetDto> List_TripDailyRouteSheet_closeDate_to_get_details(String trip_OPEN_DATE,
			long vEHICLEGROUP, short TRIP_CLOSE_STATUS, Integer companyId) {
		TypedQuery<Object[]> query = entityManager
				.createQuery(
						" SELECT v.TRIPROUTEID, v.TRIP_ROUTE_ID, TR.routeName, v.TRIP_OPEN_DATE, v.TOTAL_USAGE_KM, v.TOTAL_DIESEL,"
						+ " v.TOTAL_TOTALPASSNGER, v.TOTAL_PASS_PASSNGER, v.TOTAL_RFIDPASS, v.TOTAL_RFID_AMOUNT, v.TOTAL_COLLECTION, v.TOTAL_NET_COLLECTION,"
						+ " v.TOTAL_WT, v.TOTAL_EXPENSE, v.TOTAL_OVERTIME, v.TOTAL_BALANCE, v.TOTAL_BUS, v.TRIP_STATUS_ID, v.COMPANY_ID, v.VEHICLE_GROUP_ID"
						+ " FROM TripDailyRouteSheet as v "
						+ " LEFT JOIN TripRoute TR ON TR.routeID = v.TRIP_ROUTE_ID "
						+ " Where  v.TRIP_OPEN_DATE='" + trip_OPEN_DATE
								+ "' AND v.VEHICLE_GROUP_ID=" + vEHICLEGROUP + " AND v.TRIP_STATUS_ID="
								+ TRIP_CLOSE_STATUS + " AND v.COMPANY_ID = " + companyId + " AND v.markForDelete = 0",
								Object[].class);
		List<Object[]> results = query.getResultList();

		List<TripDailyRouteSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyRouteSheetDto>();
			TripDailyRouteSheetDto tripSheet = null;
			for (Object[] result : results) {
				tripSheet = new TripDailyRouteSheetDto();
				tripSheet.setTRIPROUTEID((Long) result[0]);
				tripSheet.setTRIP_ROUTE_ID((Integer) result[1]);
				tripSheet.setTRIP_ROUTE_NAME((String) result[2]);
				tripSheet.setTRIP_OPEN_DATE_ON((Date) result[3]);
				tripSheet.setTOTAL_USAGE_KM((Integer) result[4]);
				tripSheet.setTOTAL_DIESEL((Double) result[5]);
				tripSheet.setTOTAL_TOTALPASSNGER((Integer) result[6]);
				tripSheet.setTOTAL_PASS_PASSNGER((Integer) result[7]);
				tripSheet.setTOTAL_RFIDPASS((Integer) result[8]);
				tripSheet.setTOTAL_RFID_AMOUNT((Double) result[9]);
				tripSheet.setTOTAL_COLLECTION((Double) result[10]);
				tripSheet.setTOTAL_NET_COLLECTION((Double) result[11]);
				tripSheet.setTOTAL_WT((Double) result[12]);
				if(result[13] != null) {
				tripSheet.setTOTAL_EXPENSE((Double) result[13]);
				}
				tripSheet.setTOTAL_OVERTIME((Double) result[14]);
				tripSheet.setTOTAL_BALANCE((Double) result[15]);
				tripSheet.setTOTAL_BUS((Integer) result[16]);
				tripSheet.setTRIP_STATUS_ID((short) result[17]);
				tripSheet.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[17]));
				tripSheet.setCOMPANY_ID((Integer) result[18]);
				tripSheet.setVEHICLE_GROUP_ID((long) result[19]);
				
				Dtos.add(tripSheet);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * add_TripDailyGroup_Collection(org.fleetop.persistence.model.
	 * TripDailyGroupCollection)
	 */
	@Transactional
	public void add_TripDailyGroup_Collection(TripDailyGroupCollection RouteSheet) throws Exception {

		DailyGroupCollectionRepository.save(RouteSheet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Update_TripDailyRoute_Closed_Status(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public void Update_TripDailyRoute_Closed_Status(short TRIP_CLOSE_STATUS, String tripCollectionDate,
			long vEHICLEGROUPID, Integer companyId) {

		entityManager.createQuery("UPDATE TripDailyRouteSheet  SET TRIP_STATUS_ID = " + TRIP_CLOSE_STATUS
				+ " where  TRIP_OPEN_DATE='" + tripCollectionDate + "'  AND VEHICLE_GROUP_ID='" + vEHICLEGROUPID
				+ "' AND COMPANY_ID = " + companyId + "").executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * GET_TripDailyGroupCollection_CloseDate(java.sql.Date, java.lang.String,
	 * java.lang.String)
	 */
	/*@Transactional
	public TripDailyGroupCollection GET_TripDailyGroupCollection_CloseDate(java.sql.Date fromDate, long vEHICLEGROUP,
			String string) {

		return DailyGroupCollectionRepository.GET_TripDailyGroupCollection_CloseDate(fromDate, vEHICLEGROUP, string);
	}*/

	@Override
	public TripDailyGroupCollectionDto GET_TripDailyGroupCollection_CloseDate(java.sql.Date fromDate, long vEHICLEGROUP,
			short string, Integer companyId) {

		Query queryt = entityManager.createQuery(
				" SELECT T.TRIPGROUPID, T.TRIPGROUPNUMBER, T.TOTAL_USAGE_KM, T.TOTAL_DIESEL, T.TOTAL_DIESEL_MILAGE, T.TOTAL_TOTALPASSNGER,"
				+ "T.TOTAL_PASS_PASSNGER, T.TOTAL_RFIDPASS, T.TOTAL_RFID_AMOUNT, T.TOTAL_COLLECTION, T.TOTAL_WT, T.TOTAL_NET_COLLECTION, "
				+ " T.TOTAL_EXPENSE, T.TOTAL_OVERTIME, T.TOTAL_BALANCE, T.TRIP_CLOSE_REMARKS, U.email, T.CREATED"
				+ " FROM TripDailyGroupCollection T "
				+ " INNER JOIN User U ON U.id = T.CREATED_BY_ID"
				+ " WHERE T.TRIP_OPEN_DATE = '"+fromDate+"' AND T.VEHICLE_GROUP_ID= "+vEHICLEGROUP+" AND T.TRIP_STATUS_ID= "+string+" AND T.COMPANY_ID = "+companyId+" AND T.markForDelete = 0");
		Object[] result = null;
		try {
			result = (Object[]) queryt.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		
		TripDailyGroupCollectionDto list = null;
			if (result != null) {
				list = new TripDailyGroupCollectionDto();
				
				list.setTRIPGROUPID((Long) result[0]);
				list.setTRIPGROUPNUMBER((Long) result[1]);
				list.setTOTAL_USAGE_KM((Integer) result[2]);
				list.setTOTAL_DIESEL((Double) result[3]);
				list.setTOTAL_DIESEL_MILAGE((Double) result[4]);
				list.setTOTAL_TOTALPASSNGER((Integer) result[5]);
				list.setTOTAL_PASS_PASSNGER((Integer) result[6]);
				list.setTOTAL_RFIDPASS((Integer) result[7]);
				list.setTOTAL_RFID_AMOUNT((Double) result[8]);
				list.setTOTAL_COLLECTION((Double) result[9]);
				list.setTOTAL_WT((Double) result[10]);
				list.setTOTAL_NET_COLLECTION((Double) result[11]);
				//list.setTOTAL_EPK((Double) result[12]);
				list.setTOTAL_EXPENSE((Double) result[12]);
				list.setTOTAL_OVERTIME((Double) result[13]);
				list.setTOTAL_BALANCE((Double) result[14]);
				list.setTRIP_CLOSE_REMARKS((String) result[15]);
				list.setCREATEDBY((String) result[16]);
				list.setCREATED_ON((Date) result[17]);
				
			}
		return list;
	
	}
	
	@Override
	public TripDailyGroupCollection GET_TripDailyGroupCollectionOnCloseDate(java.sql.Date fromDate, long vEHICLEGROUP,
			short string, Integer companyId) {
		return DailyGroupCollectionRepository.GET_TripDailyGroupCollection_CloseDate(fromDate, vEHICLEGROUP, string, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * List_TripDailySheet_Report(java.lang.String)
	 */
	@Transactional
	public List<TripDailySheet> List_TripDailySheet_Report(String Search) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<TripDailySheet> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R FROM TripDailySheet AS R " + " Where ( R.markForDelete=0 " + Search
							+ " ) AND R.COMPANY_ID = " + userDetails.getCompany_id() + " ORDER BY R.TRIPDAILYID desc ",
					TripDailySheet.class);
		} else {
			queryt = entityManager.createQuery("SELECT R FROM TripDailySheet AS R "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "
					+ userDetails.getId() + "" + " Where ( R.markForDelete=0 " + Search + " ) AND R.COMPANY_ID = "
					+ userDetails.getCompany_id() + " ORDER BY R.TRIPDAILYID desc ", TripDailySheet.class);
		}
		List<TripDailySheet> results = queryt.getResultList();
		return results;
	}

	@Override
	public List<TripDailySheetDto> List_TripDailySheet_Report(String Search, CustomUserDetails userDetails)
			throws Exception {
		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					" SELECT R.TRIPDAILYID, R.TRIPDAILYNUMBER, R.VEHICLEID, V.vehicle_registration, VG.vGroup, V.vehicleGroupId,"
							+ " R.TRIP_DRIVER_ID,D.driver_firstname, R.TRIP_CONDUCTOR_ID, D2.driver_firstname, R.TRIP_CLEANER_ID, D3.driver_firstname,"
							+ " R.TRIP_OPEN_DATE, TR.routeName, R.TRIP_ROUTE_ID, R.TRIP_DIESEL, R.TRIP_OPEN_KM, R.TRIP_CLOSE_KM, R.TRIP_USAGE_KM,  "
							+ " R.TRIP_TOTALPASSNGER, R.TRIP_PASS_PASSNGER, R.TRIP_RFIDPASS, R.TRIP_RFID_AMOUNT, R.TRIP_OVERTIME,"
							+ " R.TRIP_DIESELKMPL, R.TOTAL_BALANCE, R.TOTAL_EXPENSE, R.TOTAL_INCOME_COLLECTION, R.TOTAL_INCOME, R.TOTAL_NET_INCOME,"
							+ " R.TOTAL_WT, R.TRIP_STATUS_ID, R.TRIP_REMARKS, R.CREATED, R.LASTUPDATED, U.email, co.email, R.CREATED_BY_ID, "
							+ " R.LASTMODIFIED_BY_ID, V.vehicle_ExpectedMileage "
							+ " FROM TripDailySheet as R "
							+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.TRIP_DRIVER_ID"
							+ " LEFT JOIN Driver D2 ON D2.driver_id = R.TRIP_CONDUCTOR_ID"
							+ " LEFT JOIN Driver D3 ON D3.driver_id = R.TRIP_CLEANER_ID"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " LEFT JOIN User U ON U.id = R.CREATED_BY_ID "
							+ " LEFT JOIN User co ON co.id = R.LASTMODIFIED_BY_ID " + " Where ( R.markForDelete=0 "
							+ Search + "" + " ) AND R.COMPANY_ID = " + userDetails.getCompany_id()
							+ " ORDER BY R.TRIPDAILYID desc ",
					Object[].class);
		} else {
			queryt = entityManager.createQuery(
					" SELECT R.TRIPDAILYID, R.TRIPDAILYNUMBER, R.VEHICLEID, V.vehicle_registration, VG.vGroup, V.vehicleGroupId,"
							+ " R.TRIP_DRIVER_ID,D.driver_firstname, R.TRIP_CONDUCTOR_ID, D2.driver_firstname, R.TRIP_CLEANER_ID, D3.driver_firstname,"
							+ " R.TRIP_OPEN_DATE, TR.routeName, R.TRIP_ROUTE_ID, R.TRIP_DIESEL, R.TRIP_OPEN_KM, R.TRIP_CLOSE_KM, R.TRIP_USAGE_KM,  "
							+ " R.TRIP_TOTALPASSNGER, R.TRIP_PASS_PASSNGER, R.TRIP_RFIDPASS, R.TRIP_RFID_AMOUNT, R.TRIP_OVERTIME,"
							+ " R.TRIP_DIESELKMPL, R.TOTAL_BALANCE, R.TOTAL_EXPENSE, R.TOTAL_INCOME_COLLECTION, R.TOTAL_INCOME, R.TOTAL_NET_INCOME,"
							+ " R.TOTAL_WT, R.TRIP_STATUS_ID, R.TRIP_REMARKS, R.CREATED, R.LASTUPDATED, U.email, co.email, R.CREATED_BY_ID,"
							+ " R.LASTMODIFIED_BY_ID, V.vehicle_ExpectedMileage "
							+ " FROM TripDailySheet as R "
							+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "
							+ userDetails.getId() + ""
							+ " LEFT JOIN Driver D ON D.driver_id = R.TRIP_DRIVER_ID"
							+ " LEFT JOIN Driver D2 ON D2.driver_id = R.TRIP_CONDUCTOR_ID"
							+ " LEFT JOIN Driver D3 ON D3.driver_id = R.TRIP_CLEANER_ID"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " LEFT JOIN User U ON U.id = R.CREATED_BY_ID "
							+ " LEFT JOIN User co ON co.id = R.LASTMODIFIED_BY_ID " + " Where ( R.markForDelete=0 "
							+ Search + "" + " ) AND R.COMPANY_ID = " + userDetails.getCompany_id()
							+ " ORDER BY R.TRIPDAILYID desc ",
					Object[].class);
		}

		List<Object[]> results = queryt.getResultList();
		List<TripDailySheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto list = null;
			for (Object[] result : results) {
				list = new TripDailySheetDto();
				list.setTRIPDAILYID((Long) result[0]);
				list.setTRIPDAILYNUMBER((Long) result[1]);
				list.setVEHICLEID((Integer) result[2]);
				list.setVEHICLE_REGISTRATION((String) result[3]);
				list.setVEHICLE_GROUP((String) result[4]);
				list.setVEHICLE_GROUP_ID((long) result[5]);
				list.setTRIP_DRIVER_ID((int) result[6]);
				list.setTRIP_DRIVER_NAME((String) result[7]);
				list.setTRIP_CONDUCTOR_ID((int) result[8]);
				list.setTRIP_CONDUCTOR_NAME((String) result[9]);
				list.setTRIP_CLEANER_ID((int) result[10]);
				list.setTRIP_CLEANER_NAME((String) result[11]);
				
				list.setTRIP_OPEN_DATE_D((Date) result[12]);
				list.setTRIP_ROUTE_NAME((String) result[13]);
				list.setTRIP_ROUTE_ID((Integer) result[14]);
				list.setTRIP_DIESEL((Double) result[15]);
				list.setTRIP_OPEN_KM((Integer) result[16]);
				list.setTRIP_CLOSE_KM((Integer) result[17]);
				list.setTRIP_USAGE_KM((Integer) result[18]);
				list.setTRIP_TOTALPASSNGER((Integer) result[19]);
				list.setTRIP_PASS_PASSNGER((Integer) result[20]);
				list.setTRIP_RFIDPASS((Integer) result[21]);
				list.setTRIP_RFID_AMOUNT((Double) result[22]);
				list.setTRIP_OVERTIME((Double) result[23]);
				list.setTRIP_DIESELKMPL((Double) result[24]);
				list.setTOTAL_BALANCE((Double) result[25]);
				list.setTOTAL_EXPENSE((Double) result[26]);
				list.setTOTAL_INCOME_COLLECTION((Double) result[27]);
				list.setTOTAL_INCOME((Double) result[28]);
				list.setTOTAL_NET_INCOME((Double) result[29]);
				list.setTOTAL_WT((Double) result[30]);
				list.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[31]));
				list.setTRIP_STATUS_ID((short) result[31]);
				list.setTRIP_REMARKS((String) result[32]);
				list.setCREATEDON((Date) result[33]);
				list.setLASTUPDATEDON((Date) result[34]);
				list.setCREATEDBY((String) result[35]);
				list.setLASTMODIFIEDBY((String) result[36]);
				list.setCREATED_BY_ID((Long) result[37]);
				list.setLASTMODIFIED_BY_ID((Long) result[38]);
				list.setVehicle_ExpectedMileage((Double) result[39]);
				
				

				Dtos.add(list);
			}
		}
		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * getDeployment_Page_TripDailySheet(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public Page<TripDailySheet> getDeployment_Page_TripDailySheet(short status, Integer pageNumber,
			CustomUserDetails userDetails) throws Exception {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return TripDailySheet.getDeployment_Page_TripDailySheet(status, userDetails.getCompany_id(), pageable);
		}
		return TripDailySheet.getDeployment_Page_TripDailySheet(status, userDetails.getId(),
				userDetails.getCompany_id(), pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * list_TripDailySheet_Page_Status(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public List<TripDailySheetDto> list_TripDailySheet_Page_Status(short status, Integer pageNumber,
			CustomUserDetails userDetails) throws Exception {

		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.TRIPDAILYID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_TOTALPASSNGER, "
							+ "R.TRIP_OVERTIME, R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_STATUS_ID, R.TRIPDAILYNUMBER "
							+ " FROM TripDailySheet AS R "
							+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " Where R.markForDelete=0 AND  R.TRIP_STATUS_ID=" + status + " AND R.COMPANY_ID = "
							+ userDetails.getCompany_id() + " ORDER BY R.TRIPDAILYID desc ",
					Object[].class);
		} else {
			queryt = entityManager.createQuery(
					"SELECT R.TRIPDAILYID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_TOTALPASSNGER, "
							+ "R.TRIP_OVERTIME, R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_STATUS_ID, R.TRIPDAILYNUMBER "
							+ " FROM TripDailySheet AS R "
							+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "
							+ userDetails.getId() + "" + "" + " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
							+ " Where R.markForDelete=0 AND  R.TRIP_STATUS_ID=" + status + " AND R.COMPANY_ID = "
							+ userDetails.getCompany_id() + " ORDER BY R.TRIPDAILYID desc ",
					Object[].class);
		}
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<TripDailySheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto list = null;
			for (Object[] result : results) {
				list = new TripDailySheetDto();

				list.setTRIPDAILYID((Long) result[0]);
				list.setVEHICLE_REGISTRATION((String) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_ROUTE_NAME((String) result[3]);
				list.setTRIP_OPEN_DATE_D((Date) result[4]);
				list.setTRIP_TOTALPASSNGER((Integer) result[5]);
				list.setTOTAL_EXPENSE((Double) result[6]);
				list.setTRIP_OVERTIME((Double) result[7]);
				list.setTOTAL_INCOME((Double) result[8]);
				list.setTOTAL_BALANCE((Double) result[9]);
				list.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[10]));
				list.setTRIP_STATUS_ID((short) result[10]);
				list.setTRIPDAILYNUMBER((Long) result[11]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * delete_TripDailyExpense_TRIPDAILYID(java.lang.Long)
	 */
	@Transactional
	public void delete_TripDailyExpense_TRIPDAILYID(Long tripCollectionID, Integer companyId) {

		tripDailyExpense.delete_TripDailyExpense_TRIPDAILYID(tripCollectionID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * delete_TripDailyIncome_TRIPDAILYID(java.lang.Long)
	 */
	@Transactional
	public void delete_TripDailyIncome_TRIPDAILYID(Long tripCollectionID, Integer companyId) {

		tripDailyIncome.delete_TripDailyIncome_TRIPDAILYID(tripCollectionID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * delete_TripDailySheet_TRIPDAILYID(java.lang.Long)
	 */
	@Transactional
	public void delete_TripDailySheet_TRIPDAILYID(Long DeletedBy, Timestamp deletedtime, Long tripCollectionID,
			Integer companyId) {

		try {
			TripDailySheet.delete_TripDailySheet_TRIPDAILYID(DeletedBy, deletedtime, tripCollectionID, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * List_TripDailyRouteSheet_Report(java.lang.String)
	 */
	@Transactional
	public List<TripDailyRouteSheetDto> List_TripDailyRouteSheet_Report(String query, CustomUserDetails userDetails)
			throws Exception {
		TypedQuery<Object[]> queryt = null;
		
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager
					.createQuery(
							" SELECT R.TRIPROUTEID, R.TRIP_ROUTE_ID, TR.routeName, R.TRIP_OPEN_DATE, R.TOTAL_USAGE_KM, R.TOTAL_DIESEL,"
									+ " R.TOTAL_TOTALPASSNGER, R.TOTAL_PASS_PASSNGER, R.TOTAL_RFIDPASS, R.TOTAL_RFID_AMOUNT, R.TOTAL_COLLECTION, R.TOTAL_NET_COLLECTION,"
									+ " R.TOTAL_WT, R.TOTAL_EXPENSE, R.TOTAL_OVERTIME, R.TOTAL_BALANCE, R.TOTAL_BUS, R.TRIP_STATUS_ID, R.COMPANY_ID, R.VEHICLE_GROUP_ID"
									+ " , VG.vGroup FROM TripDailyRouteSheet as R"
									+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID"
									+ " INNER JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
									+ " Where  ( R.TRIP_STATUS_ID="
									+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " " + query + " ) AND R.COMPANY_ID = "
									+ userDetails.getCompany_id() + " AND R.markForDelete = 0  ORDER BY R.TRIPROUTEID desc ",
									Object[].class);

		}else {
			queryt = entityManager
					.createQuery(
							" SELECT R.TRIPROUTEID, R.TRIP_ROUTE_ID, TR.routeName, R.TRIP_OPEN_DATE, R.TOTAL_USAGE_KM, R.TOTAL_DIESEL,"
									+ " R.TOTAL_TOTALPASSNGER, R.TOTAL_PASS_PASSNGER, R.TOTAL_RFIDPASS, R.TOTAL_RFID_AMOUNT, R.TOTAL_COLLECTION, R.TOTAL_NET_COLLECTION,"
									+ " R.TOTAL_WT, R.TOTAL_EXPENSE, R.TOTAL_OVERTIME, R.TOTAL_BALANCE, R.TOTAL_BUS, R.TRIP_STATUS_ID, R.COMPANY_ID, R.VEHICLE_GROUP_ID"
									+ " , VG.vGroup FROM TripDailyRouteSheet as R"
									+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID"
									+ " INNER JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+""
									+ " Where  ( R.TRIP_STATUS_ID="
									+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " " + query + " ) AND R.COMPANY_ID = "
									+ userDetails.getCompany_id() + " AND R.markForDelete = 0  ORDER BY R.TRIPROUTEID desc ",
									Object[].class);
		}

		List<Object[]> results = queryt.getResultList();

		List<TripDailyRouteSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyRouteSheetDto>();
			TripDailyRouteSheetDto tripSheet = null;
			for (Object[] result : results) {
				tripSheet = new TripDailyRouteSheetDto();
				tripSheet.setTRIPROUTEID((Long) result[0]);
				tripSheet.setTRIP_ROUTE_ID((Integer) result[1]);
				tripSheet.setTRIP_ROUTE_NAME((String) result[2]);
				tripSheet.setTRIP_OPEN_DATE_ON((Date) result[3]);
				tripSheet.setTOTAL_USAGE_KM((Integer) result[4]);
				tripSheet.setTOTAL_DIESEL((Double) result[5]);
				tripSheet.setTOTAL_TOTALPASSNGER((Integer) result[6]);
				tripSheet.setTOTAL_PASS_PASSNGER((Integer) result[7]);
				tripSheet.setTOTAL_RFIDPASS((Integer) result[8]);
				tripSheet.setTOTAL_RFID_AMOUNT((Double) result[9]);
				tripSheet.setTOTAL_COLLECTION((Double) result[10]);
				tripSheet.setTOTAL_NET_COLLECTION((Double) result[11]);
				tripSheet.setTOTAL_WT((Double) result[12]);
				tripSheet.setTOTAL_EXPENSE((Double) result[13]);
				tripSheet.setTOTAL_OVERTIME((Double) result[14]);
				tripSheet.setTOTAL_BALANCE((Double) result[15]);
				tripSheet.setTOTAL_BUS((Integer) result[16]);
				tripSheet.setTRIP_STATUS_ID((short) result[17]);
				tripSheet.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[17]));
				tripSheet.setCOMPANY_ID((Integer) result[18]);
				tripSheet.setVEHICLE_GROUP_ID((long) result[19]);
				tripSheet.setVEHICLE_GROUP((String) result[20]);
				Dtos.add(tripSheet);
				
				
			}
		}
		return Dtos;
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Validate_All_GroupDepartment_CloseOrNot_list(java.lang.String,
	 * java.util.Date, java.lang.String)
	 */
	@Transactional
	public List<TripDailyAllGroupDay> Validate_All_GroupDepartment_CloseOrNot_list(Date trip_OPEN_DATE, short Status,
			Integer companyId) {

		return AllGroupDayCollectionRepository.Validate_All_GroupDepartment_CloseOrNot_list(trip_OPEN_DATE, Status,
				companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * add_TripDailyAllGroupDay_Collection(org.fleetop.persistence.model.
	 * TripDailyAllGroupDayCollection)
	 */
	@Transactional
	public void add_TripDailyAllGroupDay_Collection(TripDailyAllGroupDay DailyAllGroupDay) throws Exception {

		AllGroupDayCollectionRepository.save(DailyAllGroupDay);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Update_TripDailyAllGroupDay_Collection_To_GroupCollection(org.fleetop.
	 * persistence.model.TripDailyGroupCollection)
	 */
	@Transactional
	public void Update_TripDailyAllGroupDay_Collection_To_GroupCollection(TripDailyGroupCollection totalGroupSheet,
			Long TRIPALLGROUPID) {
		entityManager.createQuery("UPDATE TripDailyAllGroupDay  SET BUSCOLLECTION = BUSCOLLECTION + "
				+ totalGroupSheet.getTOTAL_BALANCE() + ", TOTAL_DIESEL =  TOTAL_DIESEL + "
				+ totalGroupSheet.getTOTAL_DIESEL() + ", " + " TOTAL_OVERTIME =  TOTAL_OVERTIME + "
				+ totalGroupSheet.getTOTAL_OVERTIME() + ", " + " TOTAL_TOTALPASSNGER =  TOTAL_TOTALPASSNGER + "
				+ totalGroupSheet.getTOTAL_TOTALPASSNGER() + ", " + " TOTAL_USAGE_KM =  TOTAL_USAGE_KM + "
				+ totalGroupSheet.getTOTAL_USAGE_KM() + ", " + " TOTAL_DIESEL =  TOTAL_DIESEL + "
				+ totalGroupSheet.getTOTAL_DIESEL() + " " + " where TRIPALLGROUPID=" + TRIPALLGROUPID
				+ " AND COMPANY_ID = " + totalGroupSheet.getCOMPANY_ID() + "").executeUpdate();

	}
	
	@Override
	@Transactional
	public void Subtract_TripDailyAllGroupDay_Collection_To_GroupCollection(TripDailyGroupCollection totalGroupSheet,
			Long TRIPALLGROUPID) {
		entityManager.createQuery("UPDATE TripDailyAllGroupDay  SET BUSCOLLECTION = BUSCOLLECTION - "
				+ totalGroupSheet.getTOTAL_BALANCE() + ", TOTAL_DIESEL =  TOTAL_DIESEL - "
				+ totalGroupSheet.getTOTAL_DIESEL() + ", " + " TOTAL_OVERTIME =  TOTAL_OVERTIME - "
				+ totalGroupSheet.getTOTAL_OVERTIME() + ", " + " TOTAL_TOTALPASSNGER =  TOTAL_TOTALPASSNGER - "
				+ totalGroupSheet.getTOTAL_TOTALPASSNGER() + ", " + " TOTAL_USAGE_KM =  TOTAL_USAGE_KM - "
				+ totalGroupSheet.getTOTAL_USAGE_KM() + ", " + " TOTAL_DIESEL =  TOTAL_DIESEL - "
				+ totalGroupSheet.getTOTAL_DIESEL() + " " + " where TRIPALLGROUPID=" + TRIPALLGROUPID
				+ " AND COMPANY_ID = " + totalGroupSheet.getCOMPANY_ID() + "").executeUpdate();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * getDeployment_Page_TripDailyAllGroupDay(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public Page<TripDailyAllGroupDay> getDeployment_Page_TripDailyAllGroupDay(short status, Integer pageNumber,
			CustomUserDetails userDetails) {

		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return AllGroupDayCollectionRepository.getDeployment_Page_TripDailyAllGroupDay(status,
				userDetails.getCompany_id(), pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * list_TripDailyAllGroupDay_Page_StatusAllGroup(java.lang.String,
	 * java.lang.Integer)
	 */
	@Transactional
	public List<TripDailyAllGroupDayDto> list_TripDailyAllGroupDay_Page_StatusAllGroup(short status, Integer pageNumber,
			Integer companyId) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TRIPALLGROUPID, R.TRIP_OPEN_DATE, R.TOTAL_DIESEL, R.BUSCOLLECTION, R.RFIDCARD, R.TOTAL_USAGE_KM, "
						+ "R.TOTAL_OVERTIME, R.TOTAL_TOTALPASSNGER, R.TRIP_STATUS_ID, R.TRIPALLGROUPNUMBER FROM TripDailyAllGroupDay as R"
						+ " Where R.TRIP_STATUS_ID=" + status + " AND R.COMPANY_ID = " + companyId
						+ " AND R.markForDelete = 0 ORDER BY R.TRIPALLGROUPID desc ",
				Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<TripDailyAllGroupDayDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyAllGroupDayDto>();
			TripDailyAllGroupDayDto list = null;
			for (Object[] result : results) {
				list = new TripDailyAllGroupDayDto();

				list.setTRIPALLGROUPID((Long) result[0]);
				list.setTRIP_OPEN_DATE_ON((Date) result[1]);
				list.setTOTAL_DIESEL((Double) result[2]);
				list.setBUSCOLLECTION((Double) result[3]);
				list.setRFIDCARD((Double) result[4]);
				list.setTOTAL_USAGE_KM((Integer) result[5]);
				list.setTOTAL_OVERTIME((Double) result[6]);
				list.setTOTAL_TOTALPASSNGER((Integer) result[7]);
				list.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[8]));
				list.setTRIP_STATUS_ID((short) result[8]);
				list.setTRIPALLGROUPNUMBER((Long) result[9]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Validate_TripDailyRoute_All_Group_CLOSED_OR_NOT(java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<TripDailyRouteSheetDto> Validate_TripDailyRoute_All_Group_CLOSED_OR_NOT(String trip_OPEN_DATE,
			short TRIP_CLOSE_STATUS, Integer companyId) {

		TypedQuery<Object[]> query = entityManager
				.createQuery(
						" SELECT v.TRIPROUTEID, v.TRIP_ROUTE_ID, TR.routeName, v.TRIP_OPEN_DATE, v.TOTAL_USAGE_KM, v.TOTAL_DIESEL,"
						+ " v.TOTAL_TOTALPASSNGER, v.TOTAL_PASS_PASSNGER, v.TOTAL_RFIDPASS, v.TOTAL_RFID_AMOUNT, v.TOTAL_COLLECTION, v.TOTAL_NET_COLLECTION,"
						+ " v.TOTAL_WT, v.TOTAL_EXPENSE, v.TOTAL_OVERTIME, v.TOTAL_BALANCE, v.TOTAL_BUS, v.TRIP_STATUS_ID, v.COMPANY_ID, v.VEHICLE_GROUP_ID"
						+ " , VG.vGroup FROM TripDailyRouteSheet as v"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = v.TRIP_ROUTE_ID"
						+ " INNER JOIN VehicleGroup VG ON VG.gid = v.VEHICLE_GROUP_ID"
						+ "  Where v.TRIP_OPEN_DATE='" + trip_OPEN_DATE + "' AND v.TRIP_STATUS_ID="
						+ TRIP_CLOSE_STATUS + " AND v.COMPANY_ID =" + companyId + " AND v.markForDelete = 0",
								Object[].class);
		List<Object[]> results = query.getResultList();

		List<TripDailyRouteSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyRouteSheetDto>();
			TripDailyRouteSheetDto tripSheet = null;
			for (Object[] result : results) {
				tripSheet = new TripDailyRouteSheetDto();
				tripSheet.setTRIPROUTEID((Long) result[0]);
				tripSheet.setTRIP_ROUTE_ID((Integer) result[1]);
				tripSheet.setTRIP_ROUTE_NAME((String) result[2]);
				tripSheet.setTRIP_OPEN_DATE_ON((Date) result[3]);
				tripSheet.setTOTAL_USAGE_KM((Integer) result[4]);
				tripSheet.setTOTAL_DIESEL((Double) result[5]);
				tripSheet.setTOTAL_TOTALPASSNGER((Integer) result[6]);
				tripSheet.setTOTAL_PASS_PASSNGER((Integer) result[7]);
				tripSheet.setTOTAL_RFIDPASS((Integer) result[8]);
				tripSheet.setTOTAL_RFID_AMOUNT((Double) result[9]);
				tripSheet.setTOTAL_COLLECTION((Double) result[10]);
				tripSheet.setTOTAL_NET_COLLECTION((Double) result[11]);
				tripSheet.setTOTAL_WT((Double) result[12]);
				tripSheet.setTOTAL_EXPENSE((Double) result[13]);
				tripSheet.setTOTAL_OVERTIME((Double) result[14]);
				tripSheet.setTOTAL_BALANCE((Double) result[15]);
				tripSheet.setTOTAL_BUS((Integer) result[16]);
				tripSheet.setTRIP_STATUS_ID((short) result[17]);
				tripSheet.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[17]));
				tripSheet.setCOMPANY_ID((Integer) result[18]);
				tripSheet.setVEHICLE_GROUP_ID((long) result[19]);
				tripSheet.setVEHICLE_GROUP((String) result[20]);
				
				Dtos.add(tripSheet);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * GET_DETAILS_TO_TRIPSHEET_ALLGROUPDAY_COLLECTION_DETILS(java.sql.Date)
	 */
	@Transactional
	public TripDailyAllGroupDayDto GET_DETAILS_TO_TRIPSHEET_ALLGROUPDAY_COLLECTION_DETILS(java.sql.Date fromDate,
			Integer companyId) {


		Query query = entityManager.createQuery(
				" SELECT T.TRIPALLGROUPID, T.TRIPALLGROUPNUMBER, T.TRIP_OPEN_DATE, T.BUSCOLLECTION, T.RFIDRCG, T.RFIDCARD, T.RFIDUSAGE,"
				+ " T.BOOKING, T.TOTAL_DIESEL, T.TOTAL_USAGE_KM, T.TOTAL_DIESEL_MILAGE, T.TOTAL_TOTALPASSNGER, T.TOTAL_PASS_PASSNGER, T.TOTAL_RFIDPASS,"
				+ "T.TOTAL_RFID_AMOUNT, T.TOTAL_DIESELEXPENSE, T.TOTAL_OVERTIME, T.ADVANCE, T.COLLECTION_BALANCE, T.EXPENSE_DAY, T.TOTAL_BALANCE,"
				+ " T.TOTAL_WT, T.TRIP_REMARKS, T.TRIP_STATUS_ID, U.email, U2.email, T.CREATED, T.LASTUPDATED"
				+ " FROM TripDailyAllGroupDay AS T "
				+ " LEFT JOIN User U ON U.id = T.CREATED_BY_ID"
				+ " LEFT JOIN User U2 ON U2.id = T.LASTMODIFIED_BY_ID"
				+ " WHERE T.TRIP_OPEN_DATE= '"+fromDate+"' AND T.COMPANY_ID = "+companyId+" AND T.markForDelete = 0");

		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		TripDailyAllGroupDayDto select;
		if (vehicle != null) {
			select = new TripDailyAllGroupDayDto();
			
			select.setTRIPALLGROUPID((Long) vehicle[0]);
			select.setTRIPALLGROUPNUMBER((Long) vehicle[1]);
			select.setTRIP_OPEN_DATE_ON((Date) vehicle[2]);
			select.setBUSCOLLECTION((Double) vehicle[3]);
			select.setRFIDRCG((Double) vehicle[4]);
			select.setRFIDCARD((Double) vehicle[5]);
			select.setRFIDUSAGE((Double) vehicle[6]);
			select.setBOOKING((Double) vehicle[7]);
			select.setTOTAL_DIESEL((Double) vehicle[8]);
			select.setTOTAL_USAGE_KM((Integer) vehicle[9]);
			select.setTOTAL_DIESEL_MILAGE((Double) vehicle[10]);
			select.setTOTAL_TOTALPASSNGER((Integer) vehicle[11]);
			select.setTOTAL_PASS_PASSNGER((Integer) vehicle[12]);
			select.setTOTAL_RFIDPASS((Integer) vehicle[13]);
			select.setTOTAL_RFID_AMOUNT((Double) vehicle[14]);
			select.setTOTAL_DIESELEXPENSE((Double) vehicle[15]);
			select.setTOTAL_OVERTIME((Double) vehicle[16]);
			select.setADVANCE((Integer) vehicle[17]);
			select.setCOLLECTION_BALANCE((Double) vehicle[18]);
			select.setEXPENSE_DAY((Double) vehicle[19]);
			select.setTOTAL_BALANCE((Double) vehicle[20]);
			select.setTOTAL_WT((String) vehicle[21]);
			select.setTRIP_REMARKS((String) vehicle[22]);
			select.setTRIP_STATUS_ID((short) vehicle[23]);
			select.setCREATEDBY((String) vehicle[24]);
			select.setLASTMODIFIEDBY((String) vehicle[25]);
			select.setCREATED_ON((Date) vehicle[26]);
			select.setLASTUPDATED_ON((Date) vehicle[27]);
			
						
		} else {
			return null;
		}

		return select;
	
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * List_TripDailyGroupCollection_Get_all_details(java.sql.Date,
	 * java.lang.String)
	 */
	@Transactional
	public List<TripDailyGroupCollectionDto> List_TripDailyGroupCollection_Get_all_details(java.sql.Date fromDate,
			short TRIP_CLOSE_STATUS, Integer companyId) {

		TypedQuery<Object[]> queryt = null;
			queryt = entityManager.createQuery(
					" SELECT T.TRIPGROUPID, T.TRIPGROUPNUMBER, VG.vGroup, T.TRIP_OPEN_DATE, T.TOTAL_USAGE_KM, T.TOTAL_DIESEL,"
					+ "T.TOTAL_DIESEL_MILAGE, T.TOTAL_TOTALPASSNGER, T.TOTAL_PASS_PASSNGER, T.TOTAL_RFIDPASS, T.TOTAL_RFID_AMOUNT, T.TOTAL_COLLECTION,"
					+ "T.TOTAL_NET_COLLECTION, T.TOTAL_WT, T.TOTAL_EXPENSE, T.TOTAL_OVERTIME, T.TOTAL_BALANCE, T.TOTAL_BUS, T.TRIP_STATUS_ID,"
					+ "T.TRIP_CLOSE_REMARKS, T.VEHICLE_GROUP_ID, U.email, T.CREATED "
					+ " From TripDailyGroupCollection T "
					+ " LEFT JOIN User U ON U.id = T.CREATED_BY_ID"
					+ " INNER JOIN VehicleGroup VG ON VG.gid = T.VEHICLE_GROUP_ID"
					+ " WHERE T.TRIP_OPEN_DATE='"+fromDate+"' AND T.TRIP_STATUS_ID="+TRIP_CLOSE_STATUS+" AND T.COMPANY_ID = "+companyId+" AND T.markForDelete = 0 ",
					Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripDailyGroupCollectionDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyGroupCollectionDto>();
			TripDailyGroupCollectionDto list = null;
			for (Object[] result : results) {
				list = new TripDailyGroupCollectionDto();
				
				list.setTRIPGROUPID((Long) result[0]);
				list.setTRIPGROUPNUMBER((Long) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_OPEN_DATE_ON((Date) result[3]);
				list.setTOTAL_USAGE_KM((Integer) result[4]);
				list.setTOTAL_DIESEL((Double) result[5]);
				list.setTOTAL_DIESEL_MILAGE((Double) result[6]);
				list.setTOTAL_TOTALPASSNGER((Integer) result[7]);
				list.setTOTAL_PASS_PASSNGER((Integer) result[8]);
				list.setTOTAL_RFIDPASS((Integer) result[9]);
				list.setTOTAL_RFID_AMOUNT((Double) result[10]);
				list.setTOTAL_COLLECTION((Double) result[11]);
				list.setTOTAL_NET_COLLECTION((Double) result[12]);
				list.setTOTAL_WT((Double) result[13]);
				list.setTOTAL_EXPENSE((Double) result[14]);
				list.setTOTAL_OVERTIME((Double) result[15]);
				list.setTOTAL_BALANCE((Double) result[16]);
				list.setTOTAL_BUS((Integer) result[17]);
				list.setTRIP_STATUS_ID((short) result[18]);
				list.setTRIP_CLOSE_REMARKS((String) result[19]);
				list.setVEHICLE_GROUP_ID((long) result[20]);
				list.setCREATEDBY((String) result[21]);
				list.setCREATED_ON((Date) result[22]);

				Dtos.add(list);
			}
		}
		return Dtos;
	
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Update_AllGroup_Day_Collection_Closed_Details(org.fleetop.persistence.dto
	 * .TripDailyAllGroupDayDto)
	 */
	@Transactional
	public void Update_AllGroup_Day_Collection_Closed_Details(TripDailyAllGroupDay dailyAllGroupDayDto) {

		AllGroupDayCollectionRepository.Update_AllGroup_Day_Collection_Closed_Details(dailyAllGroupDayDto.getRFIDRCG(),
				dailyAllGroupDayDto.getRFIDCARD(), dailyAllGroupDayDto.getRFIDUSAGE(), dailyAllGroupDayDto.getBOOKING(),
				dailyAllGroupDayDto.getTOTAL_DIESELEXPENSE(), dailyAllGroupDayDto.getADVANCE(),
				dailyAllGroupDayDto.getTOTAL_WT(), dailyAllGroupDayDto.getCOLLECTION_BALANCE(),
				dailyAllGroupDayDto.getEXPENSE_DAY(), dailyAllGroupDayDto.getTOTAL_BALANCE(),
				dailyAllGroupDayDto.getTRIP_REMARKS(), dailyAllGroupDayDto.getLASTMODIFIED_BY_ID(),
				dailyAllGroupDayDto.getLASTUPDATED(), dailyAllGroupDayDto.getTRIPALLGROUPID(),
				dailyAllGroupDayDto.getCOMPANY_ID());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * GET_DETAILS_TO_TRIPSHEET_ALLGROUPDAY_ID(java.lang.Long)
	 */
	@Transactional
	public TripDailyAllGroupDayDto GET_DETAILS_TO_TRIPSHEET_ALLGROUPDAY_ID(Long tRIPALLGROUPID, Integer companyId) {
		//return AllGroupDayCollectionRepository.GET_DETAILS_TO_TRIPSHEET_ALLGROUPDAY_ID(tRIPALLGROUPID, companyId);


		Query query = entityManager.createQuery(
				" SELECT T.TRIPALLGROUPID, T.TRIPALLGROUPNUMBER, T.TRIP_OPEN_DATE, T.BUSCOLLECTION, T.RFIDRCG, T.RFIDCARD, T.RFIDUSAGE,"
				+ " T.BOOKING, T.TOTAL_DIESEL, T.TOTAL_USAGE_KM, T.TOTAL_DIESEL_MILAGE, T.TOTAL_TOTALPASSNGER, T.TOTAL_PASS_PASSNGER, T.TOTAL_RFIDPASS,"
				+ "T.TOTAL_RFID_AMOUNT, T.TOTAL_DIESELEXPENSE, T.TOTAL_OVERTIME, T.ADVANCE, T.COLLECTION_BALANCE, T.EXPENSE_DAY, T.TOTAL_BALANCE,"
				+ " T.TOTAL_WT, T.TRIP_REMARKS, T.TRIP_STATUS_ID, U.email, U2.email, T.CREATED, T.LASTUPDATED"
				+ " FROM TripDailyAllGroupDay AS T "
				+ " LEFT JOIN User U ON U.id = T.CREATED_BY_ID"
				+ " LEFT JOIN User U2 ON U2.id = T.LASTMODIFIED_BY_ID"
				+ " WHERE T.TRIPALLGROUPID= "+tRIPALLGROUPID+" AND T.COMPANY_ID = "+companyId+" AND T.markForDelete = 0  ");

		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		TripDailyAllGroupDayDto select;
		if (vehicle != null) {
			select = new TripDailyAllGroupDayDto();
			
			select.setTRIPALLGROUPID((Long) vehicle[0]);
			select.setTRIPALLGROUPNUMBER((Long) vehicle[1]);
			select.setTRIP_OPEN_DATE_ON((Date) vehicle[2]);
			select.setBUSCOLLECTION((Double) vehicle[3]);
			select.setRFIDRCG((Double) vehicle[4]);
			select.setRFIDCARD((Double) vehicle[5]);
			select.setRFIDUSAGE((Double) vehicle[6]);
			select.setBOOKING((Double) vehicle[7]);
			select.setTOTAL_DIESEL((Double) vehicle[8]);
			select.setTOTAL_USAGE_KM((Integer) vehicle[9]);
			select.setTOTAL_DIESEL_MILAGE((Double) vehicle[10]);
			select.setTOTAL_TOTALPASSNGER((Integer) vehicle[11]);
			select.setTOTAL_PASS_PASSNGER((Integer) vehicle[12]);
			select.setTOTAL_RFIDPASS((Integer) vehicle[13]);
			select.setTOTAL_RFID_AMOUNT((Double) vehicle[14]);
			select.setTOTAL_DIESELEXPENSE((Double) vehicle[15]);
			select.setTOTAL_OVERTIME((Double) vehicle[16]);
			select.setADVANCE((Integer) vehicle[17]);
			select.setCOLLECTION_BALANCE((Double) vehicle[18]);
			select.setEXPENSE_DAY((Double) vehicle[19]);
			select.setTOTAL_BALANCE((Double) vehicle[20]);
			select.setTOTAL_WT((String) vehicle[21]);
			select.setTRIP_REMARKS((String) vehicle[22]);
			select.setTRIP_STATUS_ID((short) vehicle[23]);
			select.setCREATEDBY((String) vehicle[24]);
			select.setLASTMODIFIEDBY((String) vehicle[25]);
			select.setCREATED_ON((Date) vehicle[26]);
			select.setLASTUPDATED_ON((Date) vehicle[27]);
			
						
		} else {
			return null;
		}

		return select;
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * List_TripDailyGroupCollection_Get_all_details(java.util.Date,
	 * java.lang.String)
	 */
	@Transactional
	public List<TripDailyGroupCollectionDto> List_TripDailyGroupCollection_Get_all_details_Date(Date fromDate,
			short TRIP_CLOSE_STATUS, Integer companyId) {
		/*return DailyGroupCollectionRepository.List_TripDailyGroupCollection_Get_all_details_Date(fromDate,
				TRIP_CLOSE_STATUS, companyId);*/


		TypedQuery<Object[]> queryt = null;
			queryt = entityManager.createQuery(
					" SELECT T.TRIPGROUPID, T.TRIPGROUPNUMBER, VG.vGroup, T.TRIP_OPEN_DATE, T.TOTAL_USAGE_KM, T.TOTAL_DIESEL,"
					+ "T.TOTAL_DIESEL_MILAGE, T.TOTAL_TOTALPASSNGER, T.TOTAL_PASS_PASSNGER, T.TOTAL_RFIDPASS, T.TOTAL_RFID_AMOUNT, T.TOTAL_COLLECTION,"
					+ "T.TOTAL_NET_COLLECTION, T.TOTAL_WT, T.TOTAL_EXPENSE, T.TOTAL_OVERTIME, T.TOTAL_BALANCE, T.TOTAL_BUS, T.TRIP_STATUS_ID,"
					+ "T.TRIP_CLOSE_REMARKS, T.VEHICLE_GROUP_ID, U.email, T.CREATED "
					+ " From TripDailyGroupCollection T "
					+ " LEFT JOIN User U ON U.id = T.CREATED_BY_ID"
					+ " INNER JOIN VehicleGroup VG ON VG.gid = T.VEHICLE_GROUP_ID"
					+ " WHERE T.TRIP_OPEN_DATE= '"+fromDate+"' AND T.TRIP_STATUS_ID= "+TRIP_CLOSE_STATUS+"  AND T.COMPANY_ID = "+companyId+" AND T.markForDelete = 0  ",
					Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripDailyGroupCollectionDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyGroupCollectionDto>();
			TripDailyGroupCollectionDto list = null;
			for (Object[] result : results) {
				list = new TripDailyGroupCollectionDto();
				
				list.setTRIPGROUPID((Long) result[0]);
				list.setTRIPGROUPNUMBER((Long) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_OPEN_DATE_ON((Date) result[3]);
				list.setTOTAL_USAGE_KM((Integer) result[4]);
				list.setTOTAL_DIESEL((Double) result[5]);
				list.setTOTAL_DIESEL_MILAGE((Double) result[6]);
				list.setTOTAL_TOTALPASSNGER((Integer) result[7]);
				list.setTOTAL_PASS_PASSNGER((Integer) result[8]);
				list.setTOTAL_RFIDPASS((Integer) result[9]);
				list.setTOTAL_RFID_AMOUNT((Double) result[10]);
				list.setTOTAL_COLLECTION((Double) result[11]);
				list.setTOTAL_NET_COLLECTION((Double) result[12]);
				list.setTOTAL_WT((Double) result[13]);
				list.setTOTAL_EXPENSE((Double) result[14]);
				list.setTOTAL_OVERTIME((Double) result[15]);
				list.setTOTAL_BALANCE((Double) result[16]);
				list.setTOTAL_BUS((Integer) result[17]);
				list.setTRIP_STATUS_ID((short) result[18]);
				list.setTRIP_CLOSE_REMARKS((String) result[19]);
				list.setVEHICLE_GROUP_ID((long) result[20]);
				list.setCREATEDBY((String) result[21]);
				list.setCREATED_ON((Date) result[22]);

				Dtos.add(list);
			}
		}
		return Dtos;
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * List_TripDailyGroupCollection_Report(java.lang.String)
	 */
	@Transactional
	public List<TripDailyGroupCollectionDto> List_TripDailyGroupCollection_Report(String query,
			CustomUserDetails userDetails) throws Exception {
		
		
		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					" SELECT R.TRIPGROUPID, R.TRIPGROUPNUMBER, VG.vGroup, R.TRIP_OPEN_DATE, R.TOTAL_USAGE_KM, R.TOTAL_DIESEL,"
					+ "R.TOTAL_DIESEL_MILAGE, R.TOTAL_TOTALPASSNGER, R.TOTAL_PASS_PASSNGER, R.TOTAL_RFIDPASS, R.TOTAL_RFID_AMOUNT, R.TOTAL_COLLECTION,"
					+ "R.TOTAL_NET_COLLECTION, R.TOTAL_WT, R.TOTAL_EXPENSE, R.TOTAL_OVERTIME, R.TOTAL_BALANCE, R.TOTAL_BUS, R.TRIP_STATUS_ID,"
					+ "R.TRIP_CLOSE_REMARKS, R.VEHICLE_GROUP_ID, U.email, R.CREATED "
					+ " From TripDailyGroupCollection R "
					+ " LEFT JOIN User U ON U.id = R.CREATED_BY_ID"
					+ " INNER JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
					+ " Where  R.markForDelete=0 AND R.TRIP_STATUS_ID="
					+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " " + query + " AND R.COMPANY_ID = "
					+ userDetails.getCompany_id() + " ORDER BY R.TRIP_OPEN_DATE ASC ",
					Object[].class);
		}else {
			queryt = entityManager.createQuery(
					" SELECT R.TRIPGROUPID, R.TRIPGROUPNUMBER, VG.vGroup, R.TRIP_OPEN_DATE, R.TOTAL_USAGE_KM, R.TOTAL_DIESEL,"
					+ "R.TOTAL_DIESEL_MILAGE, R.TOTAL_TOTALPASSNGER, R.TOTAL_PASS_PASSNGER, R.TOTAL_RFIDPASS, R.TOTAL_RFID_AMOUNT, R.TOTAL_COLLECTION,"
					+ "R.TOTAL_NET_COLLECTION, R.TOTAL_WT, R.TOTAL_EXPENSE, R.TOTAL_OVERTIME, R.TOTAL_BALANCE, R.TOTAL_BUS, R.TRIP_STATUS_ID,"
					+ "R.TRIP_CLOSE_REMARKS, R.VEHICLE_GROUP_ID, U.email, R.CREATED "
					+ " From TripDailyGroupCollection R "
					+ " LEFT JOIN User U ON U.id = R.CREATED_BY_ID"
					+ " INNER JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "
					+ userDetails.getId() + ""
					+ " Where  R.markForDelete=0 AND R.TRIP_STATUS_ID="
					+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " " + query + " AND R.COMPANY_ID = "
					+ userDetails.getCompany_id() + " ORDER BY R.TRIP_OPEN_DATE ASC ",
					Object[].class);
		}
			
		List<Object[]> results = queryt.getResultList();

		List<TripDailyGroupCollectionDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyGroupCollectionDto>();
			TripDailyGroupCollectionDto list = null;
			for (Object[] result : results) {
				list = new TripDailyGroupCollectionDto();
				
				list.setTRIPGROUPID((Long) result[0]);
				list.setTRIPGROUPNUMBER((Long) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_OPEN_DATE_ON((Date) result[3]);
				list.setTOTAL_USAGE_KM((Integer) result[4]);
				list.setTOTAL_DIESEL((Double) result[5]);
				list.setTOTAL_DIESEL_MILAGE((Double) result[6]);
				list.setTOTAL_TOTALPASSNGER((Integer) result[7]);
				list.setTOTAL_PASS_PASSNGER((Integer) result[8]);
				list.setTOTAL_RFIDPASS((Integer) result[9]);
				list.setTOTAL_RFID_AMOUNT((Double) result[10]);
				list.setTOTAL_COLLECTION((Double) result[11]);
				list.setTOTAL_NET_COLLECTION((Double) result[12]);
				list.setTOTAL_WT((Double) result[13]);
				list.setTOTAL_EXPENSE((Double) result[14]);
				list.setTOTAL_OVERTIME((Double) result[15]);
				list.setTOTAL_BALANCE((Double) result[16]);
				list.setTOTAL_BUS((Integer) result[17]);
				list.setTRIP_STATUS_ID((short) result[18]);
				list.setTRIP_CLOSE_REMARKS((String) result[19]);
				list.setVEHICLE_GROUP_ID((long) result[20]);
				list.setCREATEDBY((String) result[21]);
				list.setCREATED_ON((Date) result[22]);
				//list.setTRIP_ROUTE_ID((Integer)result[23]);
				Dtos.add(list);
			}
		}
		return Dtos;
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * List_TripDailyAllGroupDay_Report(java.lang.String)
	 */
	@Transactional
	public List<TripDailyAllGroupDay> List_TripDailyAllGroupDay_Report(String query, Integer companyId) {

		TypedQuery<TripDailyAllGroupDay> queryt = entityManager
				.createQuery(" FROM TripDailyAllGroupDay AS R Where  R.markForDelete=0 AND R.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " " + query + " AND R.COMPANY_ID = " + companyId
						+ " ORDER BY R.TRIP_OPEN_DATE desc ", TripDailyAllGroupDay.class);

		List<TripDailyAllGroupDay> results = queryt.getResultList();
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Search_TripDailySheet(java.lang.String)
	 */
	@Transactional
	public List<TripDailySheetDto> Search_TripDailySheet(String Search, CustomUserDetails userDetails) throws Exception {

		TypedQuery<Object[]> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery("SELECT R.TRIPDAILYID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_TOTALPASSNGER, " 
					+ " R.TRIP_OVERTIME, R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_STATUS_ID, R.TRIPDAILYNUMBER "
					+ " FROM TripDailySheet AS R "
					+ " INNER JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
					+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
					+ " where ( R.markForDelete=0 AND lower(R.TRIPDAILYNUMBER) Like ('%" + Search
					+ "%') OR R.markForDelete=0 AND lower(V.vehicle_registration) Like ('%" + Search + "%') "
					+ " ) AND R.COMPANY_ID = " + userDetails.getCompany_id()
					+ " ORDER BY R.TRIPDAILYID DESC ", Object[].class);
		} else {

			query = entityManager.createQuery("SELECT R.TRIPDAILYID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_TOTALPASSNGER, " 
					+ " R.TRIP_OVERTIME, R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_STATUS_ID, R.TRIPDAILYNUMBER "
					+ " FROM TripDailySheet AS R "
					+ " INNER JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
					+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "
					+ userDetails.getId() + "" + " where ( R.markForDelete=0 AND lower(R.TRIPDAILYNUMBER) Like ('%"
					+ Search + "%') OR R.markForDelete=0 AND lower(V.vehicle_registration) Like ('%" + Search + "%') "
					+ " ) AND R.COMPANY_ID = " + userDetails.getCompany_id()
					+ " ORDER BY R.TRIPDAILYID DESC ", Object[].class);
		}
		query.setMaxResults(PAGE_SIZE);
		List<Object[]> results = query.getResultList();

		List<TripDailySheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto list = null;
			for (Object[] result : results) {
				list = new TripDailySheetDto();

				list.setTRIPDAILYID((Long) result[0]);
				list.setVEHICLE_REGISTRATION((String) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_ROUTE_NAME((String) result[3]);
				list.setTRIP_OPEN_DATE_D((Date) result[4]);
				list.setTRIP_TOTALPASSNGER((Integer) result[5]);
				list.setTOTAL_EXPENSE((Double) result[6]);
				list.setTRIP_OVERTIME((Double) result[7]);
				list.setTOTAL_INCOME((Double) result[8]);
				list.setTOTAL_BALANCE((Double) result[9]);
				list.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[10]));
				list.setTRIP_STATUS_ID((short) result[10]);
				list.setTRIPDAILYNUMBER((Long) result[11]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripDailySheetService#
	 * Search_STATUS_TripDailySheet(java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<TripDailySheetDto> Search_STATUS_TripDailySheet(String Search, short tripStutes) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<Object[]> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery("SELECT R.TRIPDAILYID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_TOTALPASSNGER, " 
					+ " R.TRIP_OVERTIME, R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_STATUS_ID, R.TRIPDAILYNUMBER FROM TripDailySheet AS R "
					+ " INNER JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
					+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
					+ " where ( R.markForDelete=0 AND lower(R.TRIPDAILYNUMBER) Like ('%" + Search
					+ "%') AND R.TRIP_STATUS_ID=" + tripStutes
					+ " OR R.markForDelete=0 AND lower(V.vehicle_registration) Like ('%" + Search + "%') "
					+ " AND R.TRIP_STATUS_ID=" + tripStutes + " ) AND R.COMPANY_ID = " + userDetails.getCompany_id()
					+ " ORDER BY R.TRIPDAILYID DESC ", Object[].class);
		} else {

			query = entityManager.createQuery("SELECT R.TRIPDAILYID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_TOTALPASSNGER, " 
					+ " R.TRIP_OVERTIME, R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_STATUS_ID, R.TRIPDAILYNUMBER FROM TripDailySheet AS R "
					+ " INNER JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
					+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "
					+ userDetails.getId() + "" + " where ( R.markForDelete=0 AND lower(R.TRIPDAILYNUMBER) Like ('%"
					+ Search + "%') AND R.TRIP_STATUS_ID=" + tripStutes
					+ " OR R.markForDelete=0 AND lower(V.vehicle_registration) Like ('%" + Search + "%') "
					+ " AND R.TRIP_STATUS_ID=" + tripStutes + " ) AND R.COMPANY_ID = " + userDetails.getCompany_id()
					+ " ORDER BY R.TRIPDAILYID DESC ", Object[].class);
		}
		query.setMaxResults(PAGE_SIZE);
		List<Object[]> results = query.getResultList();

		List<TripDailySheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto list = null;
			for (Object[] result : results) {
				list = new TripDailySheetDto();

				list.setTRIPDAILYID((Long) result[0]);
				list.setVEHICLE_REGISTRATION((String) result[1]);
				list.setVEHICLE_GROUP((String) result[2]);
				list.setTRIP_ROUTE_NAME((String) result[3]);
				list.setTRIP_OPEN_DATE_D((Date) result[4]);
				list.setTRIP_TOTALPASSNGER((Integer) result[5]);
				list.setTOTAL_EXPENSE((Double) result[6]);
				list.setTRIP_OVERTIME((Double) result[7]);
				list.setTOTAL_INCOME((Double) result[8]);
				list.setTOTAL_BALANCE((Double) result[9]);
				list.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[10]));
				list.setTRIP_STATUS_ID((short) result[10]);
				list.setTRIPDAILYNUMBER((Long) result[11]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public void UpDate_TRIP_PENALTY_AMOUNT_IN_WT(Long tripdailyid, short ADVANCE_NAME_ID) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		entityManager.createQuery(
				"UPDATE TripDailySheet AS T SET T.TOTAL_WT = (SELECT COALESCE(SUM(A.ADVANCE_AMOUNT),0) FROM DriverSalaryAdvance AS A "
						+ "WHERE A.markForDelete=0  AND A.ADVANCE_NAME_ID=" + ADVANCE_NAME_ID + " AND A.TRIPDAILYID="
						+ tripdailyid + " AND A.COMPANY_ID = " + userDetails.getCompany_id() + ") where T.TRIPDAILYID="
						+ tripdailyid + " AND T.COMPANY_ID = " + userDetails.getCompany_id() + "")
				.executeUpdate();
	}

	@Override
	@Transactional
	public void Update_TripDailyIncome_Amount_By_tripincomeID(Double incomeAmount, Long incomeCollectedBy,
			Long tripincomeID, Integer companyId) {

		tripDailyIncome.Update_TripDailyIncome_Amount_By_tripincomeID(incomeAmount, incomeCollectedBy, tripincomeID,
				companyId);
	}

	@Override
	@Transactional
	public List<TripDailyTimeIncomeDto> List_OF_TripDailySheet_get_Fixed_TIME_Income_Name(Integer vEHICLE_ID,
			String dateRangeFrom, String dateRangeTo, Integer companyId) {

		TypedQuery<String> queryt = entityManager.createQuery(
				"SELECT distinct concat(TI.incomeName) AS incomeName  FROM TripDailyTimeIncome AS T"
						+ " INNER JOIN TripDailySheet AS D ON D.TRIPDAILYID = T.tripDailysheet.TRIPDAILYID"
						+ " LEFT  JOIN TripIncome TI ON TI.incomeID = T.incomeId "
						+ "  WHERE D.markForDelete=0 AND D.VEHICLEID=" + vEHICLE_ID + " AND D.TRIP_OPEN_DATE BETWEEN '"
						+ dateRangeFrom + "' AND '" + dateRangeTo + "'  AND T.companyId = " + companyId + " ",
				String.class);
		List<String> results = queryt.getResultList();

		List<TripDailyTimeIncomeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyTimeIncomeDto>();
			TripDailyTimeIncomeDto list = null;
			short CountColumn = 1;
			for (String result : results) {
				list = new TripDailyTimeIncomeDto();
				list.setCreatedBy("COLUMN_" + CountColumn);
				list.setIncomeName(result);

				Dtos.add(list);
				CountColumn++;
			}
		}
		return Dtos;
	}

	@Override
	@Transactional
	public List<Object[]> List_OF_Time_WISE_Report(Integer vEHICLE_ID, String dateRangeFrom, String dateRangeTo,
			List<TripDailyTimeIncomeDto> getFixedTypeIncomeName, Integer companyId) {

		String IncomeName = " ";
		if (getFixedTypeIncomeName != null && !getFixedTypeIncomeName.isEmpty()) {
			for (TripDailyTimeIncomeDto tripDailyIncome : getFixedTypeIncomeName) {

				IncomeName += " , SUM(CASE WHEN TI.incomeName = '" + tripDailyIncome.getIncomeName()
						+ "' AND p.markForDelete=0 AND p.companyId = " + companyId
						+ " THEN p.incomeAmount ELSE 0 END) AS " + tripDailyIncome.getCreatedBy() + " ";
			}

		}

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TRIPDAILYID, V.vehicle_registration,  R.TRIP_OPEN_DATE, R.TRIP_USAGE_KM, R.TRIP_DIESEL, R.TRIP_DIESELKMPL,  "
						+ " R.TOTAL_INCOME,  R.TOTAL_EXPENSE, R.TOTAL_BALANCE, R.TRIP_STATUS_ID" + IncomeName
						+ "  FROM TripDailySheet AS R INNER JOIN TripDailyTimeIncome AS p"
						+ "  ON R.TRIPDAILYID = p.tripDailysheet.TRIPDAILYID "
						+ " LEFT JOIN TripIncome TI ON TI.incomeID = p.incomeId "
						+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
						+ " WHERE R.markForDelete=0 AND R.VEHICLEID=" + vEHICLE_ID + " AND R.TRIP_OPEN_DATE BETWEEN '"
						+ dateRangeFrom + "' AND '" + dateRangeTo + "'  " + "  AND R.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND R.COMPANY_ID = " + companyId
						+ "  GROUP BY R.TRIPDAILYID ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		return results;
	}

	@Override
	@Transactional
	public List<TripDailyTimeIncomeDto> List_OF_TripDailySheet_get_Fixed_TIME_Income_ROUTE_NAME(Integer ROUTE_ID,
			String dateRangeFrom, String dateRangeTo, Integer companyId) {

		TypedQuery<String> queryt = entityManager
				.createQuery("SELECT distinct concat(TI.incomeName) AS incomeName  FROM TripDailyTimeIncome AS T"
						+ " INNER JOIN TripDailySheet AS D ON D.TRIPDAILYID = T.tripDailysheet.TRIPDAILYID"
						+ " INNER JOIN TripIncome TI ON TI.incomeID = T.incomeId"
						+ "  WHERE D.markForDelete=0 AND D.TRIP_SUBROUTE_ID=" + ROUTE_ID
						+ " AND D.TRIP_OPEN_DATE BETWEEN '" + dateRangeFrom + "' AND '" + dateRangeTo
						+ "' AND T.companyId = " + companyId + " AND T.markForDelete = 0 ORDER BY TI.incomeID ASC ", String.class);
		List<String> results = queryt.getResultList();

		List<TripDailyTimeIncomeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyTimeIncomeDto>();
			TripDailyTimeIncomeDto list = null;
			short CountColumn = 1;
			for (String result : results) {
				list = new TripDailyTimeIncomeDto();
				list.setCreatedBy("COLUMN_" + CountColumn);
				list.setIncomeName(result);

				Dtos.add(list);
				CountColumn++;
			}
		}
		return Dtos;
	}

	@Override
	@Transactional
	public List<Object[]> List_OF_Time_WISE_Report_TIME_INCOME_ROUTE_NAME(Integer ROUTE_ID, String dateRangeFrom,
			String dateRangeTo, List<TripDailyTimeIncomeDto> getFixedTypeIncomeName, Integer companyId) {

		String IncomeName = " ";
		if (getFixedTypeIncomeName != null && !getFixedTypeIncomeName.isEmpty()) {
			for (TripDailyTimeIncomeDto tripDailyIncome : getFixedTypeIncomeName) {

				IncomeName += " , SUM(CASE WHEN TI.incomeName = '" + tripDailyIncome.getIncomeName()
						+ "' AND p.markForDelete=0 AND p.companyId = " + companyId
						+ " THEN p.incomeAmount ELSE 0 END) AS " + tripDailyIncome.getCreatedBy() + " ";
			}

		}

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TRIPDAILYID, V.vehicle_registration, TR.routeName,  R.TRIP_OPEN_DATE, R.TRIP_USAGE_KM, R.TRIP_DIESEL, R.TRIP_DIESELKMPL,  "
						+ " R.TRIP_RFIDPASS, R.TRIP_RFID_AMOUNT, R.TOTAL_INCOME,  R.TOTAL_EXPENSE, R.TOTAL_BALANCE, R.TRIP_STATUS_ID" + IncomeName
						+ " FROM TripDailySheet AS R INNER JOIN TripDailyTimeIncome AS p "
						+ "  ON R.TRIPDAILYID = p.tripDailysheet.TRIPDAILYID "
						+ " INNER JOIN TripIncome TI ON TI.incomeID = p.incomeId "
						+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID"
						+ " INNER JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID"						
						+ " WHERE R.markForDelete=0 AND R.TRIP_SUBROUTE_ID=" + ROUTE_ID
						+ " AND R.TRIP_OPEN_DATE BETWEEN '" + dateRangeFrom + "' AND '" + dateRangeTo + "'  "
						+ "  AND R.TRIP_STATUS_ID=" + TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND R.COMPANY_ID = "
						+ companyId + "  GROUP BY R.TRIPDAILYID ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();
		
		
		return results;
	 
		
}

	@Override
	@Transactional
	public List<TripDailyTimeIncomeDto> findAll_TripDaily_TIME_Income(Long tripdailyid) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT T.TDTIMEID, T.incomeId, TI.incomeName, T.incomeAmount, T.incomeRefence, T.incomeFixedId,"
						+ " T.createdById, U.firstName, U.lastName FROM TripDailyTimeIncome AS T"
						+ " INNER JOIN TripDailySheet AS TD ON TD.TRIPDAILYID = T.tripDailysheet.TRIPDAILYID "
						+ " LEFT JOIN TripIncome AS TI ON TI.incomeID = T.incomeId "
						+ " LEFT JOIN User AS U ON U.id = T.createdById"
						+ "  WHERE T.markForDelete=0  AND TD.TRIPDAILYID=" + tripdailyid + "  AND T.companyId = "
						+ userDetails.getCompany_id() + " ",
				Object[].class);
		
		List<Object[]> results = queryt.getResultList();

		List<TripDailyTimeIncomeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyTimeIncomeDto>();
			TripDailyTimeIncomeDto list = null;
			for (Object[] result : results) {
				list = new TripDailyTimeIncomeDto();
				list.setTDTIMEID((Long) result[0]);
				list.setIncomeId((Integer) result[1]);
				list.setIncomeName((String) result[2]);
				list.setIncomeAmount((Double) result[3]);
				list.setIncomeRefence((String) result[4]);
				list.setIncomeFixedId((short) result[5]);
				list.setIncomeFixed(TripRouteFixedType.getFixedTypeName((short) result[5]));
				list.setCreatedById((Long) result[6]);
				list.setIncomeCollectedBy((String) result[7] + " " + (String) result[8]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Override
	@Transactional
	public void add_TripDailyTimeIncome(TripDailyTimeIncome tripTimeIncome) {

		tripDailyTimeIncome.save(tripTimeIncome);
	}

	@Override
	@Transactional
	public void update_TripDaily_TotalTimeIncome_INCOME_COLLECTION(Long tripdailyid) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		entityManager.createQuery(
				"UPDATE TripDailySheet AS T SET T.TOTAL_INCOME_COLLECTION = (SELECT SUM(incomeAmount) FROM TripDailyTimeIncome  WHERE  markForDelete=0 AND  TRIPDAILYID ="
						+ tripdailyid + " AND companyId = " + userDetails.getCompany_id()
						+ ")  WHERE  T.markForDelete=0 AND  T.TRIPDAILYID=" + tripdailyid + " AND T.COMPANY_ID = "
						+ userDetails.getCompany_id() + "")
				.executeUpdate();
	}

	@Override
	@Transactional
	public void update_TripDaily_TotalTimeIncome_INCOME_COLLECTION_TRIP_SUBROUTE_ID(Integer Trip_SUBRouteID,
			Long tripdailyid, Integer companyId) {

		entityManager.createQuery(
				"UPDATE TripDailySheet AS T SET T.TOTAL_INCOME_COLLECTION = (SELECT SUM(incomeAmount) FROM TripDailyTimeIncome  WHERE  markForDelete=0 AND  TRIPDAILYID ="
						+ tripdailyid + " AND companyId = " + companyId + "), T.TRIP_SUBROUTE_ID=" + Trip_SUBRouteID
						+ "  WHERE  T.markForDelete=0 AND  T.TRIPDAILYID=" + tripdailyid + " AND T.COMPANY_ID = "
						+ companyId + "")
				.executeUpdate();
	}

	@Override
	@Transactional
	public List<TripDailyTimeIncome> Validate_TripDaily_TIME_Income(Integer string, Long tripdailyid,
			Integer companyId) {

		return tripDailyTimeIncome.Validate_TripDaily_TIME_Income(string, tripdailyid, companyId);
	}

	@Override
	@Transactional
	public void Update_TripDailyTimeIncome_Amount_By_TDTIMEID(Double incomeAmount, Long incomeCollectedBy,
			Long tripincomeID) {

		tripDailyTimeIncome.Update_TripDailyTimeIncome_Amount_By_TDTIMEID(incomeAmount, incomeCollectedBy,
				tripincomeID);
	}

	@Override
	@Transactional
	public TripDailyTimeIncome Get_TripDaily_TIME_Income(Long tripIncomeID, Integer companyId) {

		return tripDailyTimeIncome.Get_TripDaily_TIME_Income(tripIncomeID, companyId);
	}

	@Override
	@Transactional
	public void delete_TripDaily_TIME_Income(Long tdtimeid, Integer companyId) {

		tripDailyTimeIncome.delete_TripDaily_TIME_Income(tdtimeid, companyId);
	}

//	@Override
//	@Transactional
//	public List<TripDailyTimeIncome> findAll_TripDailySheet_ID_TIME_Income(Long tripdailyid) {
//
//		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
//				.getPrincipal();
//		TypedQuery<Object[]> queryt = entityManager.createQuery(
//				"SELECT R.TDTIMEID, R.incomeName, R.incomeAmount, R.incomeRefence, R.incomeFixed, R.incomeCollectedBy, "
//						+ "R.createdBy, R.created FROM TripDailyTimeIncome as R"
//						+ " Where R.markForDelete=0 AND R.tripDailysheet.TRIPDAILYID =" + tripdailyid
//						+ " AND companyId = " + userDetails.getCompany_id() + " ORDER BY R.TDTIMEID desc ",
//				Object[].class);
//		List<Object[]> results = queryt.getResultList();
//
//		List<TripDailyTimeIncome> Dtos = null;
//		if (results != null && !results.isEmpty()) {
//			Dtos = new ArrayList<TripDailyTimeIncome>();
//			TripDailyTimeIncome list = null;
//			for (Object[] result : results) {
//				list = new TripDailyTimeIncome();
//
//				list.setTDTIMEID((Long) result[0]);
//				list.setIncomeName((String) result[1]);
//				list.setIncomeAmount((Double) result[2]);
//				list.setIncomeRefence((String) result[3]);
//				list.setIncomeFixed((String) result[4]);
//				list.setIncomeCollectedBy((String) result[5]);
//				list.setCreatedBy((String) result[6]);
//				list.setCreated((Date) result[7]);
//
//				Dtos.add(list);
//			}
//		}
//		return Dtos;
//	}

	@Override
	public List<TripDailyTimeIncomeDto> findAll_TripDailySheet_ID_TIME_Income(Long tripdailyid, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT R.TDTIMEID, TI.incomeName, R.incomeAmount, R.incomeRefence, R.incomeFixedId, "
						+ " U.email, R.created, U.firstName, U.lastName, R.incomeId  FROM TripDailyTimeIncome as R "
						+ " LEFT JOIN TripIncome TI ON TI.incomeID = R.incomeId "
						+ " LEFT JOIN User U ON U.id = R.createdById"
						+ " Where R.markForDelete=0 AND R.tripDailysheet.TRIPDAILYID =" + tripdailyid
						+ " AND R.companyId = " + companyId + " ORDER BY R.TDTIMEID desc ", Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<TripDailyTimeIncomeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripDailyTimeIncomeDto>();
			TripDailyTimeIncomeDto list = null;
			for (Object[] result : results) {
				list = new TripDailyTimeIncomeDto();

				list.setTDTIMEID((Long) result[0]);
				list.setIncomeName((String) result[1]);
				list.setIncomeAmount((Double) result[2]);
				list.setIncomeRefence((String) result[3]);
				list.setIncomeFixed(TripRouteFixedType.getFixedTypeName((short) result[4]));
				list.setIncomeFixedId((short) result[4]);
				list.setCreatedBy((String) result[5]);
				list.setCreated((Date) result[6]);
				list.setIncomeCollectedBy((String) result[7] + " " + (String) result[8]);
				list.setIncomeId((Integer) result[9]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Override
	@Transactional
	public void Delete_Trip_Daily_Expense_Fuel_ID_Value_Amount(Long fuel_id, Integer companyId) {

		tripDailyExpense.Delete_Trip_Daily_Expense_Fuel_ID_Value_Amount(fuel_id, companyId);
	}

	@Override
	@Transactional
	public List<Object[]> List_OF_Time_WISE_COLUMN_Report(Integer vEHICLE_ID, String dateRangeFrom, String dateRangeTo,
			List<TripDailyTimeIncomeDto> getFixedTypeIncomeName, Integer companyId) {

		String IncomeName = " ";
		if (getFixedTypeIncomeName != null && !getFixedTypeIncomeName.isEmpty()) {
			for (TripDailyTimeIncomeDto tripDailyIncome : getFixedTypeIncomeName) {

				IncomeName += " , SUM(CASE WHEN TI.incomeName = '" + tripDailyIncome.getIncomeName()
						+ "' AND p.markForDelete=0 AND p.companyId = " + companyId
						+ " THEN p.incomeAmount ELSE 0 END) AS " + tripDailyIncome.getCreatedBy() + " ";
			}

		}

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.markForDelete=0 " + IncomeName
						+ " FROM TripDailySheet AS R INNER JOIN TripDailyTimeIncome AS p "
						+ "  ON R.TRIPDAILYID = p.tripDailysheet.TRIPDAILYID" + " "
						+ " LEFT JOIN TripIncome TI ON TI.incomeID = p.incomeId"
						+ " WHERE R.markForDelete=0 AND R.VEHICLEID=" + vEHICLE_ID + " AND R.TRIP_OPEN_DATE BETWEEN '"
						+ dateRangeFrom + "' AND '" + dateRangeTo + "'  " + "  AND R.TRIP_STATUS_ID="
						+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND R.COMPANY_ID = " + companyId + " ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		return results;
	}

	@Override
	@Transactional
	public List<Object[]> List_OF_Time_WISE_ROUTE_COLUMN_Report(Integer ROUTE_ID, String dateRangeFrom,
			String dateRangeTo, List<TripDailyTimeIncomeDto> getFixedTypeIncomeName, Integer companyId) {

		String IncomeName = " ";
		if (getFixedTypeIncomeName != null && !getFixedTypeIncomeName.isEmpty()) {
			for (TripDailyTimeIncomeDto tripDailyIncome : getFixedTypeIncomeName) {

				IncomeName += " , SUM(CASE WHEN TI.incomeName = '" + tripDailyIncome.getIncomeName()
						+ "' AND p.markForDelete=0 THEN p.incomeAmount ELSE 0 END) AS " + tripDailyIncome.getCreatedBy()
						+ " ";
			}

		}

		TypedQuery<Object[]> queryt = entityManager.createQuery("SELECT R.markForDelete=0 " + IncomeName
				+ " FROM TripDailySheet AS R INNER JOIN TripDailyTimeIncome AS p "
				+ "  ON R.TRIPDAILYID = p.tripDailysheet.TRIPDAILYID "
				+ " LEFT JOIN TripIncome TI ON TI.incomeID = p.incomeId"
				+ " WHERE R.markForDelete=0 AND  R.TRIP_SUBROUTE_ID=" + ROUTE_ID + "  AND R.TRIP_OPEN_DATE BETWEEN '"
				+ dateRangeFrom + "' AND '" + dateRangeTo + "'  " + "  AND R.TRIP_STATUS_ID="
				+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " ", Object[].class);
		List<Object[]> results = queryt.getResultList();

		return results;
	}

	@Override
	public TripDailySheetDto Get_FuelVendor_SearchTo_TripSheetDetailsIN(Long tripSheetNumber, Integer companyId) {

		Query query = entityManager.createQuery(
				"SELECT f.TRIPDAILYID, f.VEHICLEID, v.vehicle_registration, VG.vGroup, f.TRIP_DRIVER_ID, D.driver_firstname,"
						+ " f.TRIP_CONDUCTOR_ID, D2.driver_firstname, f.TRIP_CLEANER_ID, D3.driver_firstname, v.vehicleGroupId, f.TRIPDAILYNUMBER "
						+ " , v.vehicle_Odometer, v.vehicleFuelId from TripDailySheet AS f"
						+ " INNER JOIN Vehicle AS v ON f.VEHICLEID= v.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
						+ " LEFT JOIN Driver D ON D.driver_id = f.TRIP_DRIVER_ID"
						+ " LEFT JOIN Driver D2 ON D2.driver_id = f.TRIP_CONDUCTOR_ID"
						+ " LEFT JOIN Driver D3 ON D3.driver_id = f.TRIP_CLEANER_ID"
						+ " WHERE f.TRIPDAILYNUMBER="
						+ tripSheetNumber + " AND f.markForDelete = 0 AND f.COMPANY_ID = " + companyId + "");
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		TripDailySheetDto select = new TripDailySheetDto();
		if (vehicle != null) {
			select.setTRIPDAILYID((Long) vehicle[0]);
			select.setVEHICLEID((Integer) vehicle[1]);
			select.setVEHICLE_REGISTRATION((String) vehicle[2]);
			select.setVEHICLE_GROUP((String) vehicle[3]);
			select.setTRIP_DRIVER_ID((Integer) vehicle[4]);
			select.setTRIP_DRIVER_NAME((String) vehicle[5]);
			select.setTRIP_CONDUCTOR_ID((Integer) vehicle[6]);
			select.setTRIP_CONDUCTOR_NAME((String) vehicle[7]);
			select.setTRIP_CLEANER_ID((Integer) vehicle[8]);
			select.setTRIP_CLEANER_NAME((String) vehicle[9]);
			select.setVEHICLE_GROUP_ID((long) vehicle[10]);
			select.setTRIPDAILYNUMBER((Long) vehicle[11]);
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
	public void updatePassengerDetails(TripDailySheetDto dailySheet) throws Exception {
		entityManager.createQuery(
				"UPDATE TripDailySheet AS T SET T.TRIP_TOTALPASSNGER = "+dailySheet.getTRIP_TOTALPASSNGER()+" , T.TRIP_PASS_PASSNGER = "+dailySheet.getTRIP_PASS_PASSNGER()+" "
						+ " ,T.TRIP_RFIDPASS = "+dailySheet.getTRIP_RFIDPASS()+", T.TRIP_RFID_AMOUNT = "+dailySheet.getTRIP_RFID_AMOUNT()+""
						+ ", T.TRIP_OVERTIME = "+dailySheet.getTRIP_OVERTIME()+", T.LASTMODIFIED_BY_ID = "+dailySheet.getLASTMODIFIED_BY_ID()+","
								+ " T.LASTUPDATED = '"+dailySheet.getLASTUPDATEDON()+"', T.noOfRoundTrip = "+dailySheet.getNoOfRoundTrip()+" "
						+ " WHERE   T.TRIPDAILYID=" + dailySheet.getTRIPDAILYID() + " AND T.COMPANY_ID = "
						+ dailySheet.getCOMPANY_ID() + "")
				.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateNoOfRoundTrip(TripDailySheetDto dailySheet) throws Exception {
		entityManager.createQuery(
				"UPDATE TripDailySheet AS T SET T.LASTMODIFIED_BY_ID = "+dailySheet.getLASTMODIFIED_BY_ID()+","
								+ " T.LASTUPDATED = '"+dailySheet.getLASTUPDATEDON()+"', T.noOfRoundTrip = "+dailySheet.getNoOfRoundTrip()+" "
						+ " WHERE   T.TRIPDAILYID=" + dailySheet.getTRIPDAILYID() + " AND T.COMPANY_ID = "
						+ dailySheet.getCOMPANY_ID() + "")
				.executeUpdate();
	}
	
	@Override
	@Transactional
	public void deleteTripDailyGroupCollectionById(String opendate, long vehicleGroupId, Integer companyId) throws Exception {
		try {
			entityManager.createQuery(
					"UPDATE TripDailyGroupCollection TGC SET TGC.markForDelete = 1 where TGC.VEHICLE_GROUP_ID = "+vehicleGroupId+""
							+ " AND TRIP_OPEN_DATE = '"+opendate+"' AND TGC.COMPANY_ID = "+companyId+"")
					.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public ArrayList<TripDailySheetDto> List_TripDailySheet(String query, Integer companyId) {
		
		TypedQuery<Object[]> typedQuery = null;
		try {
			typedQuery = entityManager.createQuery(
					"SELECT v.TRIPDAILYID,v.TRIPDAILYNUMBER,v.TRIP_OPEN_DATE,R.vid,R.vehicle_registration,D.driver_firstname,D2.driver_firstname,"
							+ " VG.vGroup,v.TRIP_ROUTE_ID,TR.routeName,v.TOTAL_WT,v.TRIP_OVERTIME,v.TRIP_DIESEL,v.TRIP_STATUS_ID"
							+ " FROM TripDailySheet as v "
							+ " INNER JOIN Vehicle R ON R.vid = v.VEHICLEID"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
							+ " LEFT JOIN Driver AS D ON D.driver_id = v.TRIP_DRIVER_ID"
							+ " LEFT JOIN Driver AS D2 ON D2.driver_id = v.TRIP_CONDUCTOR_ID"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = v.TRIP_ROUTE_ID"
							+ " Where " + query + " AND v.COMPANY_ID = " + companyId+" AND v.markForDelete = 0",
							Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();
			
			ArrayList<TripDailySheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripDailySheetDto>();
				TripDailySheetDto tripSheet = null;
				for (Object[] result : results) {
					tripSheet = new TripDailySheetDto();
					
					tripSheet.setTRIPDAILYID((Long) result[0]);
					tripSheet.setTRIPDAILYNUMBER((Long) result[1]);
					tripSheet.setTRIP_OPEN_DATE_D((Date) result[2]);						
					tripSheet.setVEHICLEID((Integer) result[3]);
					tripSheet.setVEHICLE_REGISTRATION((String) result[4]);
					tripSheet.setTRIP_DRIVER_NAME((String) result[5]);
					tripSheet.setTRIP_CONDUCTOR_NAME((String) result[6]);
					tripSheet.setVEHICLE_GROUP((String) result[7]);
					tripSheet.setTRIP_ROUTE_ID((Integer) result[8]);
					tripSheet.setTRIP_ROUTE_NAME((String) result[9]);
					tripSheet.setTOTAL_WT((Double) result[10]);
					tripSheet.setTRIP_OVERTIME((Double) result[11]);
					tripSheet.setTRIP_DIESEL((Double) result[12]);
					tripSheet.setTRIP_STATUS_ID((short) result[13]);
					Dtos.add(tripSheet);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		} finally {
			query = null;
		}
		
	}
	@Override
	public HashMap<Long, Double> getTripSheetWiseTotalIncome(String query, Integer companyId) {
		
		TypedQuery<Object[]> typedQuery = null;
		HashMap<Long, Double>	tripSheetIncomeHM		= null;

		try {
			typedQuery = entityManager.createQuery(
					"SELECT v.TRIPDAILYID,TDI.incomeAmount"
							+ " FROM TripDailySheet as v "
							+ " INNER JOIN Vehicle R ON R.vid = v.VEHICLEID"
							+ " LEFT JOIN TripDailyIncome TDI ON TDI.tripDailysheet = v.TRIPDAILYID"
							+ " Where " + query + " AND v.COMPANY_ID = " + companyId+" AND v.markForDelete = 0 AND TDI.markForDelete = 0",
							Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();
			
			List<TripDailyIncomeDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<>();
				TripDailyIncomeDto tripDailyIncome = null;
				for (Object[] result : results) {
					tripDailyIncome = new TripDailyIncomeDto();
					
					tripDailyIncome.setTripDailysheetId((Long) result[0]);
					tripDailyIncome.setIncomeAmount((Double) result[1]);
					
					Dtos.add(tripDailyIncome);
				}
			}
			
			if(Dtos != null) {
				tripSheetIncomeHM	= new HashMap<>();
				
				for(TripDailyIncomeDto incomeDto : Dtos) {
					if(tripSheetIncomeHM.get(incomeDto.getTripDailysheetId()) == null) {
						tripSheetIncomeHM.put(incomeDto.getTripDailysheetId(), incomeDto.getIncomeAmount());
					} else {
						incomeDto.setIncomeAmount(tripSheetIncomeHM.get(incomeDto.getTripDailysheetId()) + incomeDto.getIncomeAmount());
						tripSheetIncomeHM.put(incomeDto.getTripDailysheetId(), incomeDto.getIncomeAmount());					
					}
				}				
			}
			
			return tripSheetIncomeHM;
		} catch (Exception e) {
			throw e;
		} finally {
			query = null;
		}
		
	}
	@Override
	public HashMap<Long, Double> getTripSheetWiseTotalExpense(String query, Integer companyId) {
		
		TypedQuery<Object[]> typedQuery = null;
		HashMap<Long, Double>	tripSheetExpenseHM		= null;
		try {
			typedQuery = entityManager.createQuery(
					"SELECT v.TRIPDAILYID,TDE.expenseAmount"
							+ " FROM TripDailySheet as v "
							+ " INNER JOIN Vehicle R ON R.vid = v.VEHICLEID"
							+ " LEFT JOIN TripDailyExpense TDE ON TDE.tripDailysheet = v.TRIPDAILYID"
							+ " Where " + query + " AND v.COMPANY_ID = " + companyId+" AND v.markForDelete = 0 AND TDE.markForDelete = 0",
							Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();
			
			List<TripDailyExpenseDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<>();
				TripDailyExpenseDto tripDailyExpense = null;
				for (Object[] result : results) {
					tripDailyExpense = new TripDailyExpenseDto();
					
					tripDailyExpense.setTripDailysheetID((Long) result[0]);
					tripDailyExpense.setExpenseAmount((Double) result[1]);
					
					Dtos.add(tripDailyExpense);
				}
			}
			
			tripSheetExpenseHM	= new HashMap<>();
			
			if(Dtos != null) {
				for(TripDailyExpenseDto expenseDto : Dtos) {
					if(tripSheetExpenseHM.get(expenseDto.getTripDailysheetID()) == null) {
						tripSheetExpenseHM.put(expenseDto.getTripDailysheetID(), expenseDto.getExpenseAmount());
					} else {
						expenseDto.setExpenseAmount(tripSheetExpenseHM.get(expenseDto.getTripDailysheetID()) + expenseDto.getExpenseAmount());
						tripSheetExpenseHM.put(expenseDto.getTripDailysheetID(), expenseDto.getExpenseAmount());					
					}
				}
			}
			
			return tripSheetExpenseHM;
		} catch (Exception e) {
			throw e;
		} finally {
			query = null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject updateFixedExpenses(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject> 			dataArrayObjColl 			= null;
		CustomUserDetails				userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("expensesDetails");
			
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				
				
				TripDailyExpense	validate	= null;
				for (ValueObject object : dataArrayObjColl) {
					if(object.getInt("expenseId") > 0) {
						TripDailySheet tsheet = new TripDailySheet();
						tsheet.setTRIPDAILYID(valueObject.getLong("tripSheetId"));

						TripDailyExpense TripExpense = new TripDailyExpense();
						TripExpense.setExpenseId(object.getInt("expenseId", 0));
						TripExpense.setTripDailysheet(tsheet);

						TripExpense.setCreatedById(userDetails.getId());
						TripExpense.setExpenseAmount(object.getDouble("expenses", 0));

						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

						TripExpense.setCreated(toDate);
						TripExpense.setCompanyId(userDetails.getCompany_id());
						TripExpense.setFixedTypeId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
						
						
						validate	= Get_TripDaily_Expense(object.getLong("tripExpenseID", 0), userDetails.getCompany_id());
						if(validate != null) {
							TripExpense.setTripExpenseID(validate.getTripExpenseID());
						}
						add_TripDailyExpense(TripExpense);
						
					}
				}
				update_TripDailySheet_TotalExpense(valueObject.getLong("tripSheetId"));
			}
			
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Double getFuelExpensesOfTrip(Long tripSheetId, Integer companyId) throws Exception {
		TypedQuery<Object[]> typedQuery = null;
		try {
			typedQuery = entityManager.createQuery(
							"SELECT v.TRIPDAILYID, F.fuel_amount"
							+ " FROM TripDailySheet as v "
							+ " INNER JOIN Fuel F ON F.fuel_TripsheetID = v.TRIPDAILYID AND F.companyId = "+companyId+"  "
							+ " Where v.TRIPDAILYID = " + tripSheetId + " AND v.COMPANY_ID = " + companyId+" AND v.markForDelete = 0 AND F.markForDelete = 0",
							Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();
			Double fuelAmount = 0.0;
			if (results != null && !results.isEmpty()) {
				for (Object[] result : results) {
					fuelAmount = fuelAmount + (Double) result[1];
				}
			}
			
			return fuelAmount;
		} catch (Exception e) {
			throw e;
		}
	}


	@Override
	public HashMap<String, TripDailySheetDto> getFuelAmountOfTripSheetReport(Integer ROUTE_ID,
			String dateRangeFrom, String dateRangeTo, Integer companyId) throws Exception
	{
		
		TypedQuery<Object[]> query = null;
		try {
			query = entityManager.createQuery(
					"SELECT v.TRIPDAILYID, F.fuel_amount "
							+ " FROM TripDailySheet as v "
							+ " INNER JOIN Vehicle R ON R.vid = v.VEHICLEID "
							+ " INNER JOIN Fuel AS F ON F.fuel_TripsheetID = v.TRIPDAILYID "
							+ " INNER JOIN TripRoute TR ON TR.routeID = v.TRIP_ROUTE_ID "
							+ " WHERE v.markForDelete=0 AND v.TRIP_SUBROUTE_ID=" + ROUTE_ID 
							+ " AND v.TRIP_OPEN_DATE BETWEEN '" + dateRangeFrom + "' AND '" + dateRangeTo + "'  "
							+ " AND v.TRIP_STATUS_ID=" + TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND v.COMPANY_ID = "
							+  companyId + "  GROUP BY v.TRIPDAILYID ",
					Object[].class);

			List<Object[]> results = query.getResultList();

			HashMap<String, TripDailySheetDto>  hashMap	= new HashMap<>();
			if (results != null && !results.isEmpty()) {
				TripDailySheetDto	dailySheetDto 		= null;
				TripDailySheetDto	tripDailySheetDto	= null;
				for (Object[] result : results) {
					dailySheetDto	= new TripDailySheetDto();
					
					dailySheetDto.setTRIPDAILYID((Long) result[0]);
					dailySheetDto.setTRIP_DIESEL_AMOUNT((Double) result[1]);
					tripDailySheetDto	=	hashMap.get(dailySheetDto.getTRIPDAILYID()+"");
					
					if(tripDailySheetDto == null) {
						tripDailySheetDto = new TripDailySheetDto();
						tripDailySheetDto = dailySheetDto;
					}else {
						tripDailySheetDto.setTRIP_DIESEL_AMOUNT(tripDailySheetDto.getTRIP_DIESEL_AMOUNT() + dailySheetDto.getTRIP_DIESEL_AMOUNT());
					}
					
					hashMap.put(dailySheetDto.getTRIPDAILYID()+"", tripDailySheetDto);
				}
			}
			
			return hashMap;
		} catch (Exception e) {
			throw e;
		} finally {
			query = null;
		}
	}

	@Override
	public HashMap<String, TripDailySheetDto> getDieselAmountOfTripSheetReport(Integer VEHICLE_ID,
			String dateRangeFrom, String dateRangeTo, Integer companyId) throws Exception
	{
		
		
		TypedQuery<Object[]> query = null;
		try {
			query = entityManager.createQuery(
					"SELECT v.TRIPDAILYID, F.fuel_amount "
							+ " FROM TripDailySheet as v "
							+ " INNER JOIN Vehicle R ON R.vid = v.VEHICLEID "
							+ " INNER JOIN Fuel AS F ON F.fuel_TripsheetID = v.TRIPDAILYID "
							+ " INNER JOIN TripRoute TR ON TR.routeID = v.TRIP_ROUTE_ID "
							+ " WHERE v.markForDelete=0 AND v.VEHICLEID=" + VEHICLE_ID 
							+ " AND v.TRIP_OPEN_DATE BETWEEN '" + dateRangeFrom + "' AND '" + dateRangeTo + "'  "
							+ " AND v.TRIP_STATUS_ID=" + TripDailySheetStatus.TRIP_STATUS_CLOSED + " AND v.COMPANY_ID = "
							+  companyId + "  GROUP BY v.TRIPDAILYID ",
					Object[].class);

			List<Object[]> results = query.getResultList();

			HashMap<String, TripDailySheetDto>  hashMap	= new HashMap<>();
			if (results != null && !results.isEmpty()) {
				TripDailySheetDto	dailySheetDto 		= null;
				TripDailySheetDto	tripDailySheetDto	= null;
				for (Object[] result : results) {
					dailySheetDto	= new TripDailySheetDto();
					
					dailySheetDto.setTRIPDAILYID((Long) result[0]);
					dailySheetDto.setTRIP_DIESEL_AMOUNT((Double) result[1]);
					tripDailySheetDto	=	hashMap.get(dailySheetDto.getTRIPDAILYID()+"");
					if(tripDailySheetDto == null) {
						tripDailySheetDto = new TripDailySheetDto();
						tripDailySheetDto = dailySheetDto;
					}else {
						tripDailySheetDto.setTRIP_DIESEL_AMOUNT(tripDailySheetDto.getTRIP_DIESEL_AMOUNT() + dailySheetDto.getTRIP_DIESEL_AMOUNT());
					}
					
					hashMap.put(dailySheetDto.getTRIPDAILYID()+"", tripDailySheetDto);
				}
			}
			
			return hashMap;
		} catch (Exception e) {
			throw e;
		} finally {
			query = null;
		}
	}
	@Override
	public ValueObject getExpensesDetailsforModel(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valueObject.put("tripExpenses", findAll_TripDailySheet_ID_Expense(valueObject.getLong("tripSheetId"), userDetails.getCompany_id()));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails	= null;
		}
	}

	
	@Override
	public TripDailyExpense getExpenseByTripSheetIdAndExpenseId(Long tripId, Long expenseId) throws Exception {
		
		//return tripDailyExpense.getExpenseByTripSheetIdAndExpenseId(tripId, expenseId);

		TypedQuery<Object[]>	query = entityManager.createQuery(
					"SELECT expenseAmount, expenseId from TripDailyExpense where tripDailysheet.TRIPDAILYID = "+tripId+" AND tripExpenseID = "+expenseId+" "
							+ " AND markForDelete = 0",
					Object[].class);

		List<Object[]> results = query.getResultList();
		TripDailyExpense select = new TripDailyExpense();
		if (results != null && !results.isEmpty()) {
			
			for (Object[] vehicle : results) {
				select.setExpenseAmount((Double) vehicle[0]);
				select.setExpenseId((Integer) vehicle[1]);
			}
		}		
		
		return select;
		//return TripSheetIncomeRepository.getTripSheetIncomeByTripIdAndIncomeId(tripSheetID, incomeId);
	
	
	}
	
	@Override
	public TripDailyIncome getIncomeByTripSheetIdAndIncomeId(Long tripId, Integer incomeId) throws Exception {
		
		TypedQuery<Object[]>	query = entityManager.createQuery(
					"SELECT SUM(incomeAmount), incomeId from TripDailyIncome where TRIPDAILYID = "+tripId+" AND incomeId = "+incomeId+" AND markForDelete = 0",
					Object[].class);

		List<Object[]> results = query.getResultList();
		TripDailyIncome select = new TripDailyIncome();
		if (results != null && !results.isEmpty()) {
			
			for (Object[] vehicle : results) {
				if(select.getIncomeAmount() == null) {
					select.setIncomeAmount((Double) vehicle[0]);
				}else {
					select.setIncomeAmount((Double) vehicle[0]+ select.getIncomeAmount());
				}
				select.setIncomeId((Integer) vehicle[1]);
			}
		}		
		
		return select;
	}
	
	@Override
	public List<TripSheetDto> getTripSheetDetailsInMonthByVehicleGroupId(long vehicleGroupId, Timestamp fromDate,
			Timestamp toDate) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.TRIPDAILYID, TS.TRIPDAILYNUMBER, TS.VEHICLEID, TS.TRIP_OPEN_DATE, TS.TOTAL_INCOME,"
							+ " TS.TRIP_USAGE_KM, TS.TRIP_STATUS_ID, TS.TOTAL_EXPENSE,"
							+ " TS.TRIP_DIESEL "
							+ " from TripDailySheet TS "
							+ " INNER JOIN Vehicle V ON V.vid = TS.VEHICLEID"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId" 
							+ " where VG.gid = "+vehicleGroupId+" AND TS.TRIP_OPEN_DATE between '"+fromDate+"' AND '"+toDate+"' "
							+ " AND TS.markForDelete = 0 AND TS.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+"",
							Object[].class);
			List<Object[]> results = query.getResultList();

			List<TripSheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetDto>();
				TripSheetDto select = null;
				for (Object[] vehicle : results) {

					select = new TripSheetDto();
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setTripOpenDateOn((java.util.Date) vehicle[3]);
					select.setTripTotalincome((Double) vehicle[4]);
					select.setTripTotalexpense((Double) vehicle[5]);
					select.setTripDiesel((Double) vehicle[6]);
					
					Dtos.add(select);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<TripSheetDto> getTripSheetDetailsInMonthByVId(Integer vid, Timestamp fromDate, Timestamp toDate)
			throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.TRIPDAILYID, TS.TRIPDAILYNUMBER, TS.VEHICLEID, TS.TRIP_OPEN_DATE, TS.TOTAL_INCOME,"
							+ " TS.TRIP_USAGE_KM, TS.TOTAL_EXPENSE,"
							+ " TS.TRIP_DIESEL "
							+ " from TripDailySheet TS "
							+ " INNER JOIN Vehicle V ON V.vid = TS.VEHICLEID"
							+ " where V.vid = "+vid+" AND TS.TRIP_OPEN_DATE between '"+fromDate+"' AND '"+toDate+"'"
							+ " AND TS.markForDelete = 0 AND TS.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+"",
							Object[].class);
			List<Object[]> results = query.getResultList();

			List<TripSheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetDto>();
				TripSheetDto select = null;
				for (Object[] vehicle : results) {

					select = new TripSheetDto();
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setTripOpenDateOn((java.util.Date) vehicle[3]);
					select.setTripTotalincome((Double) vehicle[4]);
					select.setTripUsageKM((Integer) vehicle[5]);
					select.setTripTotalexpense((Double) vehicle[6]);
					select.setTripDiesel((Double) vehicle[7]);
					
					Dtos.add(select);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public HashMap<Integer, Long> getVehicleRunKMByGroupId(Long groupId, Timestamp fromDate, Timestamp toDate)
			throws Exception {
		HashMap<Integer, Long>		hashMap		= null;
		try {
				TypedQuery<Object[]> query = null;
				hashMap	= new HashMap<>();
				query = entityManager.createQuery(
						" SELECT TS.vid, SUM(TS.TRIP_USAGE_KM)"
								+ "FROM TripDailySheet TS "
								+ "INNER JOIN Vehicle V ON V.vid = TS.VEHICLEID"
								+ " where V.vehicleGroupId = "+groupId+" AND TS.TRIP_OPEN_DATE between '"+fromDate+"' AND '"+toDate+"' AND TS.markForDelete = 0 AND TS.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+""
								+ " GROUP BY TS.vid",
								Object[].class);
				List<Object[]> results = query.getResultList();
	
				if (results != null && !results.isEmpty()) {
					for (Object[] vehicle : results) {
						hashMap.put((Integer)vehicle[0], (Long)vehicle[1]);
					}
				}
				return hashMap;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Integer getVehicleRunKMByVid(Integer vid, Timestamp fromDate, Timestamp toDate) throws Exception {
		
		return TripDailySheet.getVehicleRunKMByVid(vid, fromDate, toDate);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetIncomeDto> getVehicleIncomeDetailsOfMonthByIncomeId(TripSheetIncomeDto incomeDto)
			throws Exception {

		Query query = entityManager.createQuery(
			"SELECT T.tripincomeID, TI.incomeName, T.incomeAmount, TS.TRIP_OPEN_DATE, TS.TRIP_OPEN_DATE, TS.TRIPDAILYID, TS.TRIPDAILYNUMBER"
						+ "  FROM TripDailyIncome T " 
						+ " INNER JOIN TripIncome TI ON TI.incomeID = T.incomeId "
						+ " INNER JOIN TripDailySheet  TS ON TS.TRIPDAILYID = T.tripDailysheet.TRIPDAILYID "
						+ " WHERE T.incomeId = "+incomeDto.getIncomeId()+" AND TS.VEHICLEID = "+incomeDto.getVid()+" AND TS.TRIP_OPEN_DATE between '"+incomeDto.getFromDate()+"'"
								+ " AND '"+incomeDto.getToDate()+"' AND T.companyId = "+incomeDto.getCompanyId()+" AND T.markForDelete = 0 AND TS.markForDelete = 0");

		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetIncomeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetIncomeDto>();
			TripSheetIncomeDto select;
			for (Object[] vehicle : results) {
				
				if (vehicle != null) {
					
					select = new TripSheetIncomeDto();
					
					if(select != null) {
					select.setTripincomeID((Long) vehicle[0]);
					select.setIncomeName((String) vehicle[1]);
					select.setIncomeAmount((Double) vehicle[2]);
					if(vehicle[3] != null)
						select.setFromDate(dateFormat.format(vehicle[3]));
					if(vehicle[4] != null)
						select.setToDate(dateFormat.format(vehicle[4]));
					
					select.setTripSheetId((Long) vehicle[5]);
					select.setTripSheetNumber((Long) vehicle[6]);
					

					Dtos.add(select);
					}
				}
			}
		}

		return Dtos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getVehicleExpenseDetailsOfMonthByExpenseId(TripSheetIncomeDto incomeDto)
			throws Exception {
		Query query = entityManager.createQuery(
			"SELECT T.tripExpenseID, TI.expenseName, T.expenseAmount, TS.TRIP_OPEN_DATE, TS.TRIP_OPEN_DATE, TS.TRIPDAILYID, TS.TRIPDAILYNUMBER"
						+ "  FROM TripDailyExpense T " 
						+ " INNER JOIN TripExpense TI ON TI.expenseID = T.expenseId AND (T.fuel_id IS NULL OR T.fuel_id = 0)"
						+ " INNER JOIN TripDailySheet  TS ON TS.TRIPDAILYID = T.tripDailysheet.TRIPDAILYID "
						+ " WHERE T.expenseId = "+incomeDto.getIncomeId()+" AND TS.VEHICLEID = "+incomeDto.getVid()+" AND TS.TRIP_OPEN_DATE between '"+incomeDto.getFromDate()+"'"
								+ " AND '"+incomeDto.getToDate()+"' AND T.companyId = "+incomeDto.getCompanyId()+" AND T.markForDelete = 0 AND TS.markForDelete = 0");

		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetExpenseDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetExpenseDto>();
			TripSheetExpenseDto select;
			for (Object[] vehicle : results) {
				
				if (vehicle != null) {
					
					select = new TripSheetExpenseDto();
					
					if(select != null) {
					select.setTripExpenseID((Long) vehicle[0]);
					select.setExpenseName((String) vehicle[1]);
					select.setExpenseAmount((Double) vehicle[2]);
					if(vehicle[3] != null)
						select.setFromDate(dateFormat.format(vehicle[3]));
					if(vehicle[4] != null)
						select.setToDate(dateFormat.format(vehicle[4]));
					
					select.setTripSheetId((Long) vehicle[5]);
					select.setTripSheetNumber((Long) vehicle[6]);
					
					Dtos.add(select);
					}
				}
			}
		}

		return Dtos;
	}
	
	
	@Override
	public List<TripDailySheetDto> getFuelAmountOfTripDailySheetReport(String dateRangeFrom, String dateRangeTo,String query1, Integer companyId) throws Exception
	{
		
		TypedQuery<Object[]> query = null;
		try {
			query = entityManager.createQuery(
					"SELECT v.TRIPDAILYID, F.fuel_amount, v.TRIP_ROUTE_ID "
							+ " FROM TripDailySheet as v "
							+ " INNER JOIN Vehicle R ON R.vid = v.VEHICLEID "
							+ " INNER JOIN Fuel AS F ON F.fuel_TripsheetID = v.TRIPDAILYID AND F.companyId = "+companyId+" AND F.markForDelete = 0 "
							+ " INNER JOIN TripRoute TR ON TR.routeID = v.TRIP_ROUTE_ID "
							+ " WHERE v.markForDelete= 0 "
							+ " AND v.TRIP_OPEN_DATE BETWEEN '" + dateRangeFrom + "' AND '" + dateRangeTo + "'" + query1 
							+ " AND v.COMPANY_ID ="+companyId+" ",
					Object[].class);

			List<Object[]> results = query.getResultList();

			List<TripDailySheetDto>  Dtos	= null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripDailySheetDto>();
				TripDailySheetDto select = null;
				for (Object[] vehicle : results) {

					select = new TripDailySheetDto();
					select.setTRIPDAILYID((Long)vehicle[0]);
					select.setFuel_amount((double)vehicle[1]);
					select.setTRIP_ROUTE_ID((Integer)vehicle[2]);
					Dtos.add(select);
				}
			}
			
			return Dtos;
		} catch (Exception e) {
			LOGGER.error("Error", e);
			throw e;
		} finally {
			query = null;
		}
	}


			@Override
			public HashMap<String, TripDailySheetDto> getFuelAmountOfTripSheetReportDateWise(String dateRangeFrom, String dateRangeTo,
					String query1, Integer companyId) throws Exception {

				TypedQuery<Object[]> query = null;
				try {
					query = entityManager.createQuery(
							"SELECT v.TRIPDAILYID, F.fuel_amount , v.TRIP_OPEN_DATE"
									+ " FROM TripDailySheet as v "
									+ " INNER JOIN Vehicle R ON R.vid = v.VEHICLEID "
									+ " INNER JOIN Fuel AS F ON F.fuel_TripsheetID = v.TRIPDAILYID AND F.companyId = "+companyId+" AND F.markForDelete = 0"
									+ " Where  ( v.TRIP_STATUS_ID="
									+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " " + query1 + " ) AND v.COMPANY_ID = "
									+ companyId + " AND v.markForDelete = 0  ORDER BY v.TRIP_ROUTE_ID desc ",
									Object[].class);

					List<Object[]> results = query.getResultList();

					HashMap<String, TripDailySheetDto>  hashMap	= new HashMap<>();
					if (results != null && !results.isEmpty()) {
						TripDailySheetDto	dailySheetDto 		= null;
						TripDailySheetDto	tripDailySheetDto	= null;
						for (Object[] result : results) {
							dailySheetDto	= new TripDailySheetDto();
							
							dailySheetDto.setTRIPDAILYID((Long) result[0]);
							dailySheetDto.setTRIP_DIESEL_AMOUNT((Double) result[1]);
							dailySheetDto.setTRIP_OPEN_DATE_D((Date) result[2]);
							tripDailySheetDto	=	hashMap.get(dailySheetDto.getTRIP_OPEN_DATE()+"");
							
							if(tripDailySheetDto == null) {
								tripDailySheetDto = new TripDailySheetDto();
								tripDailySheetDto = dailySheetDto;
							}else {
								tripDailySheetDto.setTRIP_DIESEL_AMOUNT(tripDailySheetDto.getTRIP_DIESEL_AMOUNT() + dailySheetDto.getTRIP_DIESEL_AMOUNT());
							}
							
							hashMap.put(dailySheetDto.getTRIP_OPEN_DATE_D()+"", tripDailySheetDto);
						}
					}
					return hashMap;
				} catch (Exception e) {
					throw e;
				} finally {
					query = null;
				}

			}
			
			
			@Override
			public HashMap<String, TripDailySheetDto> getDepotWiseFuelAmountOfTripSheet(String dateRangeFrom, String dateRangeTo,
					String query1,CustomUserDetails userDetails) throws Exception {

				TypedQuery<Object[]> query = null;
				try {
					query = entityManager.createQuery(
							"SELECT v.TRIPDAILYID, F.fuel_amount, v.TRIP_OPEN_DATE "
									+ " FROM TripDailySheet as v "
									+ " INNER JOIN Vehicle R ON R.vid = v.VEHICLEID "
									+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "+userDetails.getId()+ " "
									+ " INNER JOIN Fuel AS F ON F.fuel_TripsheetID = v.TRIPDAILYID AND F.companyId = "+userDetails.getCompany_id()+" AND F.markForDelete = 0"
									+ " Where  v.markForDelete=0 AND v.TRIP_STATUS_ID="
									+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " " + query1 + " AND v.COMPANY_ID = "
									+ userDetails.getCompany_id() + " ORDER BY v.TRIP_OPEN_DATE ASC ",
									Object[].class);

					List<Object[]> results = query.getResultList();

					HashMap<String, TripDailySheetDto>  hashMap	= new HashMap<>();
					if (results != null && !results.isEmpty()) {
						TripDailySheetDto	dailySheetDto 		= null;
						TripDailySheetDto	tripDailySheetDto	= null;
						for (Object[] result : results) {
							dailySheetDto	= new TripDailySheetDto();
							
							dailySheetDto.setTRIPDAILYID((Long) result[0]);
							dailySheetDto.setTRIP_DIESEL_AMOUNT((Double) result[1]);
							dailySheetDto.setTRIP_OPEN_DATE_D((Date)result[2]);
							tripDailySheetDto	=	hashMap.get(dailySheetDto.getTRIP_OPEN_DATE_D()+"");
							
							if(tripDailySheetDto == null) {
								tripDailySheetDto = new TripDailySheetDto();
								tripDailySheetDto = dailySheetDto;
							}else {
								tripDailySheetDto.setTRIP_DIESEL_AMOUNT(tripDailySheetDto.getTRIP_DIESEL_AMOUNT() + dailySheetDto.getTRIP_DIESEL_AMOUNT());
							}
							
							hashMap.put(dailySheetDto.getTRIP_OPEN_DATE_D()+"", tripDailySheetDto);
						}
					}
					return hashMap;
				} catch (Exception e) {
					throw e;
				} finally {
					query = null;
				}

			}
			@Override
			public TripDailySheetDto getvehicleStatusByVid(Integer vehicleID) throws Exception {
				Query query = entityManager.createQuery("Select v.vStatusId,v.vid From Vehicle AS v" 
						+ " where v.vid=" + vehicleID
						);
				Object[] vehicle = null;
				try {
					vehicle = (Object[]) query.getSingleResult();

				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}

				TripDailySheetDto select =null;
				if (vehicle != null) {
					select = new TripDailySheetDto();
					select.setvStatusId((short) vehicle[0]);
					select.setVEHICLEID((Integer)vehicle[1]);
				}

				return select;
		}
			
			
			@Transactional
			public List<TripDailySheetDto> list_Close_TripDailySheet(String date, CustomUserDetails userDetails)
					throws Exception {

				TypedQuery<Object[]> queryt = null;
				if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
						PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
					queryt = entityManager.createQuery(
							"SELECT R.TRIPDAILYID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_TOTALPASSNGER, "
									+ "R.TRIP_OVERTIME, R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_STATUS_ID, R.TRIPDAILYNUMBER, R.CHALO_KM , R.CHALO_AMOUNT "
									+ " FROM TripDailySheet AS R "
									+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID "
									+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
									+ " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
									+ " Where R.markForDelete=0 AND R.TRIP_STATUS_ID=" + TripDailySheetStatus.TRIP_STATUS_CLOSED
									+ " AND R.TRIP_OPEN_DATE='" + date + "' AND R.COMPANY_ID = "
									+ userDetails.getCompany_id() + " ORDER BY R.TRIPDAILYID desc ",
							Object[].class);
				} else {
					queryt = entityManager.createQuery(
							"SELECT R.TRIPDAILYID, V.vehicle_registration, VG.vGroup, TR.routeName, R.TRIP_OPEN_DATE, R.TRIP_TOTALPASSNGER, "
									+ "R.TRIP_OVERTIME, R.TOTAL_EXPENSE, R.TOTAL_INCOME, R.TOTAL_BALANCE, R.TRIP_STATUS_ID, R.TRIPDAILYNUMBER, R.CHALO_KM , R.CHALO_AMOUNT "
									+ " FROM TripDailySheet AS R "
									+ " INNER JOIN Vehicle V ON V.vid = R.VEHICLEID "
									+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "
									+ userDetails.getId() + "" + " LEFT JOIN TripRoute TR ON TR.routeID = R.TRIP_ROUTE_ID "
									+ " Where R.markForDelete=0 AND R.TRIP_STATUS_ID=" + TripDailySheetStatus.TRIP_STATUS_CLOSED
									+ " AND  R.TRIP_OPEN_DATE='" + date + "' AND R.COMPANY_ID = "
									+ userDetails.getCompany_id() + " ORDER BY R.TRIPDAILYID desc ",
							Object[].class);
				}
				List<Object[]> results = queryt.getResultList();

				List<TripDailySheetDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<TripDailySheetDto>();
					TripDailySheetDto list = null;
					for (Object[] result : results) {
						list = new TripDailySheetDto();

						list.setTRIPDAILYID((Long) result[0]);
						list.setVEHICLE_REGISTRATION((String) result[1]);
						list.setVEHICLE_GROUP((String) result[2]);
						list.setTRIP_ROUTE_NAME((String) result[3]);
						list.setTRIP_OPEN_DATE_D((Date) result[4]);
						list.setTRIP_TOTALPASSNGER((Integer) result[5]);
						list.setTOTAL_EXPENSE((Double) result[6]);
						list.setTRIP_OVERTIME((Double) result[7]);
						list.setTOTAL_INCOME((Double) result[8]);
						list.setTOTAL_BALANCE((Double) result[9]);
						list.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[10]));
						list.setTRIP_STATUS_ID((short) result[10]);
						list.setTRIPDAILYNUMBER((Long) result[11]);
						if(result[12] != null) {
						list.setCHALO_KM((Integer) result[12]);
						}
						if(result[13] != null) {
						list.setCHALO_AMOUNT((Double) result[13]);
						}
						

						Dtos.add(list);
					}
				}
				return Dtos;
			}

			
			@Override
			public HashMap<String, TripDailySheetDto> getDepotWiseChaloDetailsOfTripSheet(String dateRangeFrom, String dateRangeTo,
			String query1,CustomUserDetails userDetails) throws Exception {

			TypedQuery<Object[]> query = null;
			try {
				query = entityManager.createQuery(
						"SELECT v.TRIPDAILYID, v.CHALO_KM, v.CHALO_AMOUNT, v.TRIP_OPEN_DATE "
								+ " FROM TripDailySheet as v "
								+ " INNER JOIN Vehicle R ON R.vid = v.VEHICLEID "		
								+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"		
								+ " Where  v.markForDelete=0 AND v.TRIP_STATUS_ID="
								+ TripDailySheetStatus.TRIP_STATUS_CLOSED + " " + query1 + " AND v.COMPANY_ID = "
								+ userDetails.getCompany_id() + " ORDER BY v.TRIP_OPEN_DATE ASC ",
								Object[].class);
				
				List<Object[]> results = query.getResultList();
				
				HashMap<String, TripDailySheetDto>  hashMap	= new HashMap<>();
				if (results != null && !results.isEmpty()) {
					TripDailySheetDto	dailySheetDto 		= null;
					TripDailySheetDto	tripDailySheetDto	= null;
					for (Object[] result : results) {
						dailySheetDto	= new TripDailySheetDto();
						
						dailySheetDto.setTRIPDAILYID((Long) result[0]);
						if(result[1] != null) {
							dailySheetDto.setCHALO_KM((int) result[1]);
						} else {
							dailySheetDto.setCHALO_KM(0);	
						}
						if(result[2] != null) {
							dailySheetDto.setCHALO_AMOUNT((Double) result[2]);
						}else {
							dailySheetDto.setCHALO_AMOUNT(0.0);	
						}
						dailySheetDto.setTRIP_OPEN_DATE_D((Date)result[3]);
						tripDailySheetDto	=	hashMap.get(dailySheetDto.getTRIP_OPEN_DATE_D()+"");
						
						if(tripDailySheetDto == null) {
							tripDailySheetDto = new TripDailySheetDto();
							tripDailySheetDto = dailySheetDto;
						}else {
							tripDailySheetDto.setCHALO_KM(tripDailySheetDto.getCHALO_KM() + dailySheetDto.getCHALO_KM());
							tripDailySheetDto.setCHALO_AMOUNT(tripDailySheetDto.getCHALO_AMOUNT() + dailySheetDto.getCHALO_AMOUNT());
							//tripDailySheetDto.setTRIP_DIESEL_AMOUNT(tripDailySheetDto.getTRIP_DIESEL_AMOUNT() + dailySheetDto.getTRIP_DIESEL_AMOUNT());
						}
						
						hashMap.put(dailySheetDto.getTRIP_OPEN_DATE_D()+"", tripDailySheetDto);
					}
				}
				return hashMap;
				

			} catch (Exception e) {
					throw e;
			} finally {
					query = null;
			}

		}
			
			@Override
			public List<TripDailyRouteSheet> validateForDepotTripDayCollectionClosed(String trip_OPEN_DATE, long vEHICLEGROUP,
					short TRIP_CLOSE_STATUS, Integer companyId) throws Exception {
				try {
					TypedQuery<TripDailyRouteSheet> query = entityManager
						.createQuery("from TripDailyRouteSheet v "
								+ " Where  v.TRIP_OPEN_DATE='" + trip_OPEN_DATE
								+ "' AND v.VEHICLE_GROUP_ID=" + vEHICLEGROUP + " AND v.TRIP_STATUS_ID="
								+ TRIP_CLOSE_STATUS + " AND v.COMPANY_ID = " + companyId + " AND v.markForDelete = 0", TripDailyRouteSheet.class);
				return query.getResultList();} catch (Exception e) {
					throw e;
				}
			}
			
			@Override
			public org.fleetopgroup.persistence.model.TripDailySheet Get_Showing_TripDaily_Sheet(Long tripdailyid,
					Integer companyId) throws Exception {
				
				return TripDailySheet.Get_Showing_TripDaily_Sheet(tripdailyid, companyId);
			}
}
	

