package org.fleetopgroup.persistence.service;

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
import javax.transaction.Transactional;

import org.fleetopgroup.constant.DriverAdvanceStatus;
import org.fleetopgroup.constant.DriverAdvanceType;
import org.fleetopgroup.constant.DriverAttandancePointStatus;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.persistence.dao.DriverAdvanceRepository;
import org.fleetopgroup.persistence.dto.DriverAdvanceDto;
import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverAdvance;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverAdvanceService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DriverAdvanceService implements IDriverAdvanceService {
	@Autowired
	private DriverAdvanceRepository driverAdvanceRepository;

	@Autowired
	private ICompanyConfigurationService companyConfigurationService;

	@PersistenceContext
	EntityManager entityManager;
	
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAdvanceService#
	 * register_New_DriverAdvance(org.fleetop.persistence.model.DriverAdvance)
	 */
	@Transactional
	public DriverAdvance register_New_DriverAdvance(DriverAdvance accountDto) throws Exception {

		return driverAdvanceRepository.save(accountDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAdvanceService#
	 * List_Total_OF_DriverAdvance_details(java.lang.Integer)
	 */
	@Transactional
	public List<DriverAdvance> List_Total_OF_DriverAdvance_details(Integer driver_ID) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAdvanceService#
	 * update_DriverAdvance_Status(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public void update_DriverAdvance_Status(short advance_Status, Integer driver_ID, Integer companyId)
			throws Exception {

		driverAdvanceRepository.update_DriverAdvance_Status(advance_Status, driver_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAdvanceService#
	 * DRIVER_Advance_BALANCE_TripCollectiom(java.lang.Integer)
	 */
	@Transactional
	public List<DriverAdvance> DRIVER_Advance_BALANCE_TripCollectiom(Integer dRIVER_ID, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT T.DRIADVID, T.DRIVER_ADVANCE_AMOUNT FROM DriverAdvance AS T WHERE T.TRIP_DRIVER_ID = "
						+ dRIVER_ID + " AND T.ADVANCE_STATUS_ID="+DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN+" AND T.COMPANY_ID = " + companyId
						+ " AND T.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverAdvance> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverAdvance>();
			DriverAdvance list = null;
			for (Object[] result : results) {
				list = new DriverAdvance();

				list.setDRIADVID((Long) result[0]);
				list.setDRIVER_ADVANCE_AMOUNT((Double) result[1]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAdvanceService#
	 * List_DriverAdvance_TOTAL_Report(java.lang.String)
	 */
	@Transactional
	public List<DriverAdvanceDto> List_DriverAdvance_TOTAL_Report(String query, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager
				.createQuery(" SELECT R.DRIADVID, R.TRIP_DRIVER_ID, D.driver_firstname, R.ADVANCE_DATE, R.DRIVER_JAMA_BALANCE,"
						+ " R.DRIVER_ADVANCE_AMOUNT, R.DRIVER_ADVANCE_REMARK, R.ADVANCE_STATUS_ID, R.ADVANCE_PAID_TYPE_ID,"
						+ " R.ADVANCE_PAID_NUMBER, R.CREATED, R.LASTUPDATED, U.firstName, U.email, U2.email "
						+ " FROM DriverAdvance AS R "
						+ " INNER JOIN Driver D ON D.driver_id = R.TRIP_DRIVER_ID"
						+ " LEFT JOIN User U ON U.id = R.CREATED_BY_ID"
						+ " LEFT JOIN User U2 ON U2.id = R.LASTUPDATED_ID"
						+ " Where R.markForDelete= 0" + query + " AND R.COMPANY_ID = "
						+ companyId + " ORDER BY R.DRIADVID desc ", Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverAdvanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverAdvanceDto>();
			DriverAdvanceDto list = null;
			for (Object[] result : results) {
				list = new DriverAdvanceDto();

				list.setDRIADVID((Long) result[0]);
				list.setTRIP_DRIVER_ID((int) result[1]);
				list.setTRIP_DRIVER_NAME((String) result[2]);
				list.setADVANCE_DATE_ON((Date) result[3]);
				list.setDRIVER_JAMA_BALANCE((Double) result[4]);
				list.setDRIVER_ADVANCE_AMOUNT((Double) result[5]);
				list.setDRIVER_ADVANCE_REMARK((String) result[6]);
				list.setADVANCE_STATUS_ID((short) result[7]);
				list.setADVANCE_PAID_TYPE_ID((short) result[8]);
				list.setADVANCE_PAID_NUMBER((String) result[9]);
				list.setCREATED((Date) result[10]);
				list.setLASTUPDATED((Date) result[11]);
				list.setADVANCE_PAID_BY((String) result[12]);
				list.setCREATEDBY((String) result[13]);
				list.setLASTMODIFIEDBY((String) result[14]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAdvanceService#
	 * Report_OF_Driver_Halt_Point(java.lang.Integer, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<DriverAttendanceDto> Report_OF_Driver_Halt_Point(Integer dRIVER_ID, String dateRangeFrom,
			String dateRangeTo, Integer companyId) throws Exception {
		int tripsheetFlavor = companyConfigurationService.getTripSheetFlavor(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		TypedQuery<Object[]> query = null;
		
		if (tripsheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
			 query = entityManager.createQuery(
					"SELECT A.DRIVERID, D.driver_firstname, A.ATTENDANCE_DATE, VG.vGroup, A.TRIPSHEETID, TR.routeName, T.tripOpenDate, T.closetripDate, V.vehicle_registration, "
							+ " A.DRIVER_POINT, A.POINT_TYPE_ID, T.tripSheetNumber From DriverAttendance AS A "
							+ " INNER JOIN Driver D ON D.driver_id = A.DRIVERID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
							+ " LEFT JOIN TripSheet AS T ON T.tripSheetID = A.TRIPSHEETID "
							+ " LEFT JOIN Vehicle V ON V.vid = T.vid "
							+ " LEFT JOIN TripRoute TR ON TR.routeID = A.TRIP_ROUTE_ID"
							+ "  where (A.DRIVERID ="
							+ dRIVER_ID + " AND A.POINT_STATUS_ID= "+DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT+" AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom
							+ "' AND '" + dateRangeTo
							+ "')) ORDER BY A.ATTENDANCE_DATE desc ",
					Object[].class);
		}else if(tripsheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
			 query = entityManager.createQuery(
					"SELECT A.DRIVERID, D.driver_firstname, A.ATTENDANCE_DATE, VG.vGroup, A.TRIPSHEETID, TR.routeName, T.TRIP_OPEN_DATE, T.lASTUPDATED, V.vehicle_registration, "
							+ " A.DRIVER_POINT, A.POINT_TYPE_ID, T.TRIPCOLLNUMBER From DriverAttendance AS A "
							+ " INNER JOIN Driver D ON D.driver_id = A.DRIVERID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
							+ " LEFT JOIN TripCollectionSheet AS T ON T.TRIPCOLLID = A.TRIPSHEETID "
							+ " LEFT JOIN Vehicle V ON V.vid = T.VID "
							+ " LEFT JOIN TripRoute TR ON TR.routeID = A.TRIP_ROUTE_ID"
							+ " where (A.DRIVERID ="
							+ dRIVER_ID + " AND A.POINT_STATUS_ID= "+DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT+" AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom
							+ "' AND '" + dateRangeTo
							+ "'))  ORDER BY A.ATTENDANCE_DATE desc ",
					Object[].class);
		}else if(tripsheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
			
			 query = entityManager.createQuery(
					"SELECT A.DRIVERID, D.driver_firstname, A.ATTENDANCE_DATE, VG.vGroup, A.TRIPSHEETID, TR.routeName, T.TRIP_OPEN_DATE, T.LASTUPDATED, V.vehicle_registration, "
							+ " A.DRIVER_POINT, A.POINT_TYPE_ID, T.TRIPDAILYNUMBER From DriverAttendance AS A"
							+ " INNER JOIN Driver D ON D.driver_id = A.DRIVERID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
							+ " LEFT JOIN TripDailySheet AS T ON T.TRIPDAILYID = A.TRIPSHEETID "
							+ " LEFT JOIN Vehicle V ON V.vid = T.VEHICLEID "
							+ " LEFT JOIN TripRoute TR ON TR.routeID = A.TRIP_ROUTE_ID"
							+ " where (A.DRIVERID ="
							+ dRIVER_ID + " AND A.POINT_STATUS_ID= "+DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT+" AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom
							+ "' AND '" + dateRangeTo
							+ "')) ORDER BY A.ATTENDANCE_DATE desc ",
							Object[].class);
		}else if(tripsheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_FOUR) {
			
			 query = entityManager.createQuery(
					"SELECT A.DRIVERID, D.driver_firstname, A.ATTENDANCE_DATE, VG.vGroup, A.TRIPSHEETID, TR.routeName, T.TRIP_OPEN_DATE, T.LASTUPDATED, V.vehicle_registration, "
							+ " A.DRIVER_POINT, A.POINT_TYPE_ID, T.TRIPDAILYNUMBER From DriverAttendance AS A"
							+ " INNER JOIN Driver D ON D.driver_id = A.DRIVERID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
							+ " LEFT JOIN TripDailySheet AS T ON T.TRIPDAILYID = A.TRIPSHEETID "
							+ " LEFT JOIN Vehicle V ON V.vid = T.VEHICLEID "
							+ " LEFT JOIN TripRoute TR ON TR.routeID = A.TRIP_ROUTE_ID"
							+ " where (A.DRIVERID ="
							+ dRIVER_ID + " AND A.POINT_STATUS_ID= "+DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT+" AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom
							+ "' AND '" + dateRangeTo
							+ "')) ORDER BY A.ATTENDANCE_DATE desc ",
							Object[].class);
		}

		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		List<DriverAttendanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto list = null;
			for (Object[] result : results) {
				list = new DriverAttendanceDto();

				list.setDRIVERID((Integer) result[0]);
				list.setDRIVER_NAME((String) result[1]);
				list.setD_ATTENDANCE_DATE((Date) result[2]);
				// driver Group Service
				list.setCREATEDBY((String) result[3]);

				list.setTRIPSHEETID((Long) result[4]);
				list.setTRIP_ROUTE_NAME((String) result[5]);

				// tripOpenDate is only display propose show CREATED Halt
				list.setCREATED((Date) result[6]);
				// tripCloseDate is only display propose show LASTUPDATED Halt
				list.setLASTUPDATED((Date) result[7]);
				// vehicle_registration is only display propose show
				// POINT_STATUS Halt
				list.setPOINT_STATUS((String) result[8]);

				list.setDRIVER_POINT((Double) result[9]);
				if(result[10] != null)
				list.setPOINT_TYPE_ID((short) result[10]);
				list.setTRIPSHEETNUMBER((Long) result[11]);
				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAdvanceService#
	 * Report_OF_Driver_Halt_Point(java.lang.Integer, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<DriverAttendanceDto> Report_OF_Driver_LocalHalt_Point(Integer dRIVER_ID, String dateRangeFrom,
			String dateRangeTo, Integer companyId) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT A.DRIVERID, D.driver_empnumber, D.driver_firstname, D.driver_Lastname, A.ATTENDANCE_DATE, VG.vGroup, A.TRIPSHEETID, TR.routeName, "
						+ " A.DRIVER_POINT, A.POINT_TYPE_ID From DriverAttendance AS A "
						+ " INNER JOIN Driver AS D ON D.driver_id = A.DRIVERID "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = A.TRIP_ROUTE_ID"
						+ " where (A.DRIVERID ="+ dRIVER_ID
						+ " AND A.POINT_STATUS_ID= "+DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT+" AND A.TRIPSHEETID=0 AND (A.ATTENDANCE_DATE  between '"
						+ dateRangeFrom + "' AND '" + dateRangeTo + "')) AND A.COMPANY_ID = " + companyId
						+ " AND A.markForDelete = 0   ORDER BY A.ATTENDANCE_DATE desc ",
				Object[].class);

		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		List<DriverAttendanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto list = null;
			for (Object[] result : results) {
				list = new DriverAttendanceDto();

				list.setDRIVERID((Integer) result[0]);
				list.setDRIVER_NAME((String) result[1] +" "+(String) result[2] + " "+ (String) result[3]);
				if ((Date) result[4] != null) {
					list.setATTENDANCE_DATE(dateFormat_Name.format((Date) result[4]));
				}
				// driver Group Service
				list.setCREATEDBY((String) result[5]);

				list.setTRIPSHEETID((Long) result[6]);
				list.setTRIP_ROUTE_NAME((String) result[7]);

				list.setDRIVER_POINT((Double) result[8]);
				if(result[9] != null)
				 list.setPOINT_TYPE_ID((short) result[9]);
				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAdvanceService#
	 * Report_OF_Driver_Group_Attendance_Point(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<DriverAttendanceDto> Report_OF_Driver_Group_Attendance_Point(String dRIVER_GROUP, String dateRangeFrom,
			String dateRangeTo, Integer companyId) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT D.DRIVERID, DG.driver_empnumber, DG.driver_firstname, DG.driver_Lastname, VG.vGroup, "
						+ " SUM(D.DRIVER_POINT),DG.driver_fathername From DriverAttendance AS D"
						+ " INNER JOIN Driver AS DG ON DG.driver_id = D.DRIVERID "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = DG.vehicleGroupId"
						+ " where D.DRIVERID IN ( "
						+ dRIVER_GROUP + " ) AND  D.POINT_STATUS_ID="+DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT+" AND (D.ATTENDANCE_DATE  between '"
						+ dateRangeFrom + "' AND '" + dateRangeTo + "') AND D.COMPANY_ID = " + companyId
						+ " AND D.markForDelete = 0  Group By D.DRIVERID  ",
				Object[].class);

		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		List<DriverAttendanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto list = null;
			for (Object[] result : results) {
				list = new DriverAttendanceDto();

				list.setDRIVERID((Integer) result[0]);
				list.setDRIVER_NAME((String) result[1] +" "+(String) result[2] + " "+ (String) result[3]);
				// Point type in Group Service
				list.setPOINT_TYPE((String) result[4]);
				list.setDRIVER_POINT((Double) result[5]);
				if(result[6]  != null && !((String) result[6]).trim().equals(""))
					list.setDRIVER_NAME(list.getDRIVER_NAME()+" - "+(String) result[6] );

				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAdvanceService#
	 * Report_OF_Driver_Attendance(java.lang.Integer, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<DriverAttendanceDto> Report_OF_Driver_Attendance(Integer dRIVER_ID, String dateRangeFrom,
			String dateRangeTo, Integer companyId) throws Exception {

		int tripsheetFlavor = companyConfigurationService.getTripSheetFlavor(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		List<Object[]> results = null;
		List<DriverAttendanceDto> Dtos = new ArrayList<>();
		TypedQuery<Object[]> query = null;
		if (tripsheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
			query = entityManager.createQuery(
					"SELECT A.DRIVERID,  D.driver_empnumber, D.driver_firstname, D.driver_Lastname, A.ATTENDANCE_DATE, VG.vGroup, A.TRIPSHEETID, TR.routeName, "
							+ "A.POINT_TYPE_ID, TS.tripSheetNumber, D.driver_fathername, D.driver_Lastname From DriverAttendance AS A"
							+ " INNER JOIN  Driver AS D ON D.driver_id = A.DRIVERID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = A.TRIP_ROUTE_ID"
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = A.TRIPSHEETID AND TS.companyId ="+companyId+" " + " where (A.DRIVERID ="
							+ dRIVER_ID + " AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom + "' AND '" + dateRangeTo
							+ "')) AND A.COMPANY_ID = " + companyId
							+ "  AND A.markForDelete = 0",
					Object[].class);
			try {
				results = query.getResultList();
				if(results != null && !results.isEmpty())
					Dtos.addAll(getFinalAttandanceList(results, TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE));
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

		} else if (tripsheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
			query = entityManager.createQuery(
					"SELECT A.DRIVERID,  D.driver_empnumber, D.driver_firstname, D.driver_Lastname, A.ATTENDANCE_DATE, VG.vGroup, A.TRIPSHEETID, TR.routeName, "
							+ "A.POINT_TYPE_ID, TS.TRIPCOLLNUMBER, D.driver_fathername From DriverAttendance AS A"
							+ " INNER JOIN  Driver AS D ON D.driver_id = A.DRIVERID"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
							+ " INNER JOIN TripCollectionSheet TS ON TS.TRIPCOLLID = A.TRIPSHEETID AND TS.COMPANY_ID ="+companyId+" "
							+ " LEFT JOIN TripRoute TR ON TR.routeID = A.TRIP_ROUTE_ID"
							+ " where (A.DRIVERID =" + dRIVER_ID + " AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom
							+ "' AND '" + dateRangeTo + "'))  AND A.COMPANY_ID = "
							+ companyId + "  AND A.markForDelete = 0",
					Object[].class);
			try {
				results = query.getResultList();
				if(results != null && !results.isEmpty())
					Dtos.addAll(getFinalAttandanceList(results, TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO));
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

		} else if (tripsheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
			query = entityManager.createQuery(
					"SELECT A.DRIVERID,  D.driver_empnumber, D.driver_firstname, D.driver_Lastname, A.ATTENDANCE_DATE, VG.vGroup, A.TRIPSHEETID, TR.routeName, "
							+ "A.POINT_TYPE_ID, TS.TRIPDAILYNUMBER, D.driver_fathername From DriverAttendance AS A"
							+ " INNER JOIN TripDailySheet TS ON TS.TRIPDAILYID = A.TRIPSHEETID AND TS.COMPANY_ID ="+companyId+" "
							+ " INNER JOIN  Driver AS D ON D.driver_id = A.DRIVERID"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = A.TRIP_ROUTE_ID"
							+ " where (A.DRIVERID =" + dRIVER_ID + " AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom
							+ "' AND '" + dateRangeTo + "'))  AND A.COMPANY_ID = "
							+ companyId + "  AND A.markForDelete = 0",
					Object[].class);
			try {
				results = query.getResultList();
				if(results != null && !results.isEmpty())
					Dtos.addAll(getFinalAttandanceList(results, TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE));
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
		}else if (tripsheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_FOUR) {
			query = entityManager.createQuery(
					"SELECT A.DRIVERID,  D.driver_empnumber, D.driver_firstname, D.driver_Lastname, A.ATTENDANCE_DATE, VG.vGroup, A.TRIPSHEETID, TR.routeName, "
							+ "A.POINT_TYPE_ID, TS.TRIPDAILYNUMBER, D.driver_fathername From DriverAttendance AS A"
							+ " INNER JOIN TripDailySheet TS ON TS.TRIPDAILYID = A.TRIPSHEETID AND TS.COMPANY_ID ="+companyId+" "
							+ " INNER JOIN  Driver AS D ON D.driver_id = A.DRIVERID"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = A.TRIP_ROUTE_ID"
							+ " where (A.DRIVERID =" + dRIVER_ID + " AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom
							+ "' AND '" + dateRangeTo + "'))  AND A.COMPANY_ID = "
							+ companyId + "  AND A.markForDelete = 0",
					Object[].class);
			try {
				results = query.getResultList();
				if(results != null && !results.isEmpty())
					Dtos.addAll(getFinalAttandanceList(results, TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE));
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			query = entityManager.createQuery(
					"SELECT A.DRIVERID,  D.driver_empnumber, D.driver_firstname, D.driver_Lastname, A.ATTENDANCE_DATE, VG.vGroup, A.TRIPSHEETID, TR.routeName, "
							+ "A.POINT_TYPE_ID, TS.tripSheetNumber, D.driver_fathername From DriverAttendance AS A"
							+ " INNER JOIN  Driver AS D ON D.driver_id = A.DRIVERID"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = A.TRIP_ROUTE_ID"
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = A.TRIPSHEETID " + " where (A.DRIVERID ="
							+ dRIVER_ID + " AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom + "' AND '" + dateRangeTo
							+ "')) AND A.COMPANY_ID = " + companyId
							+ "  AND A.markForDelete = 0",
					Object[].class);
			try {
				results = query.getResultList();
				if(results != null && !results.isEmpty())
					Dtos.addAll(getFinalAttandanceList(results, TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE));
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
		
		}
		
		return Dtos;
	}
	
	private List<DriverAttendanceDto> getFinalAttandanceList(List<Object[]>  results, int	tripSheetFlavor){
		
		List<DriverAttendanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto list = null;
			for (Object[] result : results) {
				list = new DriverAttendanceDto();

				list.setDRIVERID((Integer) result[0]);
				list.setDRIVER_NAME((String) result[1] + "-" + (String) result[2] + " " + (String) result[3]);
				list.setD_ATTENDANCE_DATE((Date) result[4]);
				// driver Group Service
				list.setCREATEDBY((String) result[5]);

				list.setTRIPSHEETID((Long) result[6]);
				list.setTRIP_ROUTE_NAME((String) result[7]);
				if(result[8] != null)
					list.setPOINT_TYPE_ID((short) result[8]);
				list.setTRIPSHEETNUMBER((Long) result[9]);
				if(result[10] != null && !((String)result[10]).trim().equals(""))
					list.setDRIVER_NAME(list.getDRIVER_NAME()+" - "+result[10]);	
				list.setTripSheetFlavor(tripSheetFlavor);
				
				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAdvanceService#
	 * Report_Group_Driver_Attendance(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<DriverAttendanceDto> Report_Group_Driver_Attendance(String dRIVER_GROUP, String dateRangeFrom,
			String dateRangeTo, Integer companyId) {
		
		TypedQuery<Object[]> query = entityManager
				.createQuery("SELECT A.DRIVERID, D.driver_empnumber, D.driver_firstname, D.driver_Lastname, VG.vGroup, D.driver_perdaySalary,"
						+ " count(DISTINCT A.ATTENDANCE_DATE),D.driver_fathername From DriverAttendance AS A "
						+ " INNER JOIN Driver AS D ON D.driver_id = A.DRIVERID "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
						+ " where  ( A.DRIVERID IN ( "
						+ dRIVER_GROUP + " )  AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom + "' AND '"
						+ dateRangeTo + "')  ) AND A.COMPANY_ID = " + companyId
						+ " AND A.markForDelete = 0 Group By A.DRIVERID ", Object[].class);

		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			//LOGGER.error("err", nre);
			// Ignore this because as per your logic this is ok!
		}
		List<DriverAttendanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto list = null;
			for (Object[] result : results) {
				list = new DriverAttendanceDto();

				list.setDRIVERID((Integer) result[0]);
				list.setDRIVER_NAME((String) result[1] +" "+(String) result[2] + " "+ (String) result[3] );
				// Point type in Group Service
				list.setPOINT_TYPE((String) result[4]);

				// Per Day Salary To Driver table To Get the Driver ID
				// Here Driver Point IN Driver Per day Salary
				list.setDRIVER_POINT((Double) result[5]);

				// ATTENDANCE_DATE to showing in TRIPSHEETID
				list.setTRIPSHEETID((Long) result[6]);
				if( result[7] != null && !((String) result[7]).trim().equals(""))
				list.setDRIVER_NAME(list.getDRIVER_NAME()+" - "+result[7]);
				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAdvanceService#
	 * Report_Group_Driver_Attendance(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<Driver> Report_Group_Driver_Attendance_Lastmonth_salaryDetails(String DRIVER_GROUP_Query,
			String dateRangeFrom, String dateRangeTo, Integer companyId) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT A.DRIVERID, D.driver_firstname, D.driver_Lastname,  D.vehicleGroupId, D.driver_perdaySalary, D.driver_empnumber,  D.driver_esiamount, D.driver_pfamount,"
						+ " count(DISTINCT A.ATTENDANCE_DATE), D.driverSalaryTypeId From DriverAttendance AS A, Driver AS D"
						+ "  where  ( A.DRIVERID IN ( "
						+ DRIVER_GROUP_Query + " )  AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom + "' AND '"
						+ dateRangeTo + "')  AND A.DRIVERID = D.driver_id AND A.COMPANY_ID = " + companyId
						+ " AND A.markForDelete = 0 ) Group By A.DRIVERID ",
				Object[].class);

		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		List<Driver> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Driver>();
			Driver list = null;
			for (Object[] result : results) {
				list = new Driver();

				list.setDriver_id((Integer) result[0]);
				list.setDriver_firstname((String) result[1]);
				list.setDriver_Lastname((String) result[2]);
				list.setVehicleGroupId((Long) result[3]);
				list.setDriver_perdaySalary((Double) result[4]);
				list.setDriver_empnumber((String) result[5]);
				list.setDriver_esiamount((Double) result[6]);
				list.setDriver_pfamount((Double) result[7]);
				list.setTripSheetID((Long) result[8]);
				list.setDriverSalaryTypeId((Short) result[9]);

				Dtos.add(list);
			}

		}
		return Dtos;
	}
	

	@SuppressWarnings("unchecked")
	@Transactional
	public HashMap<Integer, TripSheetDto> Report_Group_Driver_Trip_And_KMUsage_Lastmonth_salaryDetails(String driverGroupQuery, String dateRangeFrom, String dateRangeTo, Integer company_id) throws Exception {

		List<TripSheetDto> 				firstDriverTripSheetList		= null;
		List<TripSheetDto> 				secondDriverTripSheetList		= null;
		List<TripSheetDto> 				cleanerList						= null;
		HashMap<Integer, TripSheetDto>	driverWiseTripHM				= null;
		Thread							firstDriverThread				= null;	
		Thread							secondDriverThread				= null;
		Thread							cleaner							= null;
		ValueObject						valueObject;
		
		try {
			valueObject		= new ValueObject();
			
			firstDriverThread	= new Thread() {
				public void run() {
					List<TripSheetDto> 				tripSheetList		= null;
					try {
						tripSheetList = getFirstDriverList(driverGroupQuery, dateRangeFrom, dateRangeTo, company_id);
						valueObject.put("firstDriverTripSheetList", tripSheetList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			firstDriverThread.start();
			
			secondDriverThread = new Thread() {
				public void run() {
					List<TripSheetDto> 				tripSheetList		= null;
					try {
						tripSheetList = getSecondDriverList(driverGroupQuery, dateRangeFrom, dateRangeTo, company_id);
						valueObject.put("secondDriverTripSheetList", tripSheetList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			secondDriverThread.start();
			
			
			cleaner = new Thread() {
				public void run() {
					List<TripSheetDto> 				tripSheetList		= null;
					try {
						tripSheetList = getCleanerList(driverGroupQuery, dateRangeFrom, dateRangeTo, company_id);
						valueObject.put("CleanerList", tripSheetList);
					} catch (Exception e) {
						LOGGER.error("err", e);
						e.printStackTrace();
					}
				}
			};
			cleaner.start();
			
			firstDriverThread.join();
			secondDriverThread.join();
			cleaner.join();
			
			firstDriverTripSheetList		= (List<TripSheetDto>) valueObject.get("firstDriverTripSheetList");
			secondDriverTripSheetList		= (List<TripSheetDto>) valueObject.get("secondDriverTripSheetList");
			cleanerList						= (List<TripSheetDto>) valueObject.get("CleanerList");
			
			driverWiseTripHM	= new HashMap<>();
			if(firstDriverTripSheetList != null) {				
				for(TripSheetDto  tripSheetDto: firstDriverTripSheetList) {
					driverWiseTripHM.put(tripSheetDto.getTripFristDriverID(), tripSheetDto);
				}
			}
			
			if(secondDriverTripSheetList != null) {				
				for(TripSheetDto  tripSheetDto: secondDriverTripSheetList) {
					if(driverWiseTripHM.get(tripSheetDto.getTripSecDriverID())== null) {
						driverWiseTripHM.put(tripSheetDto.getTripSecDriverID(), tripSheetDto);						
					} else {
						tripSheetDto.setTripSheetCount(driverWiseTripHM.get(tripSheetDto.getTripSecDriverID()).getTripSheetCount() + tripSheetDto.getTripSheetCount());
						if(tripSheetDto.getTotalKMUsage() != null && driverWiseTripHM.get(tripSheetDto.getTripSecDriverID()).getTotalKMUsage() != null) {
								tripSheetDto.setTotalKMUsage(driverWiseTripHM.get(tripSheetDto.getTripSecDriverID()).getTotalKMUsage() + tripSheetDto.getTotalKMUsage());
							
						}else {
							tripSheetDto.setTotalKMUsage(driverWiseTripHM.get(tripSheetDto.getTripSecDriverID()).getTotalKMUsage());
						}
						
						driverWiseTripHM.put(tripSheetDto.getTripSecDriverID(), tripSheetDto);						
					}
				}
			}
			
			
			if(cleanerList != null) {				
				for(TripSheetDto  tripSheetDto: cleanerList) {
					driverWiseTripHM.put(tripSheetDto.getTripCleanerID(), tripSheetDto);
				}
			}
			
			
			return driverWiseTripHM;
		} catch (Exception e) {
			LOGGER.error("err", e);
			throw e;
		} finally {
			firstDriverTripSheetList		= null;
			secondDriverTripSheetList		= null;
			driverWiseTripHM				= null;
			firstDriverThread				= null;
			secondDriverThread				= null;
		}
	}

	private List<TripSheetDto> getFirstDriverList(String driverGroupQuery, String dateRangeFrom, String dateRangeTo, Integer company_id) throws Exception {
		
		TypedQuery<Object[]> 			typedQuery			= null;
		List<Object[]> 					results 			= null;
		List<TripSheetDto> 				tripSheetList		= null;
		TripSheetDto 					tripSheet 			= null;
		
		try {

			typedQuery = entityManager.createQuery(
					"SELECT TS.tripFristDriverID, count(DISTINCT TS.tripSheetID), sum(TS.tripUsageKM), D.driverSalaryTypeId"
							+ " FROM TripSheet TS"
							+ " INNER JOIN Driver D ON TS.tripFristDriverID = D.driver_id"
							+ " WHERE  ((TS.tripFristDriverID IN ( " + driverGroupQuery + " ))"
							+ " AND (TS.tripOpenDate  between '" + dateRangeFrom + "' AND '" + dateRangeTo + "')"
							+ " AND TS.companyId = " + company_id
							+ " AND TS.markForDelete = 0) Group By TS.tripFristDriverID",
							Object[].class);
			
			results = typedQuery.getResultList();
			if (results != null && !results.isEmpty()) {
				tripSheetList = new ArrayList<>();
				for (Object[] result : results) {
					tripSheet = new TripSheetDto();
					
					tripSheet.setTripFristDriverID((Integer) result[0]);
					tripSheet.setTripSheetCount((Long) result[1]);
					tripSheet.setTotalKMUsage((Long) result[2]);
					tripSheet.setDriverSalaryTypeId((short) result[3]);
					
					tripSheetList.add(tripSheet);
				}
			}
			return tripSheetList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery			= null;
			results 			= null;
			tripSheetList		= null;
			tripSheet 			= null;
		}
	}
	
	private List<TripSheetDto> getSecondDriverList(String driverGroupQuery, String dateRangeFrom,	String dateRangeTo, Integer company_id) throws Exception {
		
		TypedQuery<Object[]> 			typedQuery			= null;
		List<Object[]> 					results 			= null;
		List<TripSheetDto> 				tripSheetList		= null;
		TripSheetDto 					tripSheet 			= null;
		
		try {
			
			typedQuery = entityManager.createQuery(
					"SELECT TS.tripSecDriverID, count(DISTINCT TS.tripSheetID), sum(TS.tripUsageKM), D.driverSalaryTypeId"
							+ " FROM TripSheet TS"
							+ " INNER JOIN Driver D ON TS.tripSecDriverID = D.driver_id"
							+ " WHERE  ((TS.tripSecDriverID IN ( " + driverGroupQuery + " ))"
							+ " AND (TS.tripOpenDate  between '" + dateRangeFrom + "' AND '" + dateRangeTo + "')"
							+ " AND TS.companyId = " + company_id
							+ " AND TS.markForDelete = 0) Group By TS.tripSecDriverID",
							Object[].class);
			
			results = typedQuery.getResultList();
			
			if (results != null && !results.isEmpty()) {
				tripSheetList = new ArrayList<>();
				for (Object[] result : results) {
					tripSheet = new TripSheetDto();
					
					tripSheet.setTripSecDriverID((Integer) result[0]);
					tripSheet.setTripSheetCount((Long) result[1]);
					tripSheet.setTotalKMUsage((Long) result[2]);
					tripSheet.setDriverSalaryTypeId((short) result[3]);
					
					tripSheetList.add(tripSheet);
				}
			}
			return tripSheetList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery			= null;
			results 			= null;
			tripSheetList		= null;
			tripSheet 			= null;
		}
	}
	
private List<TripSheetDto> getCleanerList(String driverGroupQuery, String dateRangeFrom,	String dateRangeTo, Integer company_id) throws Exception {
		
		TypedQuery<Object[]> 			typedQuery			= null;
		List<Object[]> 					results 			= null;
		List<TripSheetDto> 				tripSheetList		= null;
		TripSheetDto 					tripSheet 			= null;
		
		try {
			
			typedQuery = entityManager.createQuery(
					"SELECT TS.tripCleanerID, count(DISTINCT TS.tripSheetID), sum(TS.tripUsageKM), D.driverSalaryTypeId"
							+ " FROM TripSheet TS"
							+ " INNER JOIN Driver D ON TS.tripCleanerID = D.driver_id"
							+ " WHERE  ((TS.tripCleanerID IN ( " + driverGroupQuery + " ))"
							+ " AND (TS.tripOpenDate  between '" + dateRangeFrom + "' AND '" + dateRangeTo + "')"
							+ " AND TS.companyId = " + company_id
							+ " AND TS.markForDelete = 0) Group By TS.tripCleanerID",
							Object[].class);
			
			results = typedQuery.getResultList();
			
			if (results != null && !results.isEmpty()) {
				tripSheetList = new ArrayList<>();
				for (Object[] result : results) {
					tripSheet = new TripSheetDto();
					
					tripSheet.setTripCleanerID((Integer) result[0]);
					tripSheet.setTripSheetCount((Long) result[1]);
					tripSheet.setTotalKMUsage((Long) result[2]);
					tripSheet.setDriverSalaryTypeId((short) result[3]);
					
					tripSheetList.add(tripSheet);
				}
			}
			return tripSheetList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery			= null;
			results 			= null;
			tripSheetList		= null;
			tripSheet 			= null;
		}
	}
	
	@Transactional
	public Object[] SUM_TOTAL_ADVANCE_PENALTY_AMOUNT(Integer DRIVER_ID, String dateRangeFrom, String dateRangeTo,
			Integer companyId) throws Exception {

		Query queryt = entityManager.createQuery("SELECT COALESCE(SUM(co.ADVANCE_BALANCE),0),"
				+ "(SELECT  COALESCE(SUM(RRT.ADVANCE_AMOUNT),0) FROM DriverSalaryAdvance RRT where RRT.DRIVER_ID ="
				+ DRIVER_ID + " AND RRT.ADVANCE_STATUS_ID ="+DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN+" AND RRT.ADVANCE_NAME_ID="+DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY+" "
				+ " AND (RRT.ADVANCE_DATE  between '" + dateRangeFrom + "' AND '" + dateRangeTo + "')"
				+ " AND RRT.COMPANY_ID = " + companyId + " AND  RRT.markForDelete=0 ) "
				+ " FROM DriverSalaryAdvance AS co where co.DRIVER_ID =" + DRIVER_ID
				+ " AND co.ADVANCE_STATUS_ID ="+DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN+" AND co.ADVANCE_NAME_ID="+DriverAdvanceType.DRIVER_ADVANCE_TYPE_ADVANCE+"" + " AND co.COMPANY_ID = " + companyId
				+ "  AND co.markForDelete=0  ");
		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}
	
	@Transactional
	public List<DriverAttendanceDto> Report_OF_Driver_AttendanceForDriverSalary(int driverid, String dateRangeFrom, String dateRangeTo, Integer company_id) throws Exception {
		int tripsheetFlavor = companyConfigurationService.getTripSheetFlavor(company_id, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		List<Object[]> results = null;
		List<DriverAttendanceDto> Dtos = new ArrayList<>();
		TypedQuery<Object[]> query = null;
		
		if (tripsheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
			query = entityManager.createQuery(
					"SELECT A.DRIVERID,  D.driver_empnumber, D.driver_firstname, D.driver_Lastname, A.ATTENDANCE_DATE, VG.vGroup, A.TRIPSHEETID, TR.routeName, "
							+ "A.POINT_TYPE_ID, TS.tripSheetNumber, D.driver_fathername, D.driver_Lastname, TSE.tripExpenseID, TE.expenseID, TSE.expenseAmount, TSE.driverId  From DriverAttendance AS A"
							+ " INNER JOIN  Driver AS D ON D.driver_id = A.DRIVERID "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = A.TRIP_ROUTE_ID"

							+ " INNER JOIN TripSheet T on T.tripSheetID = A.TRIPSHEETID AND T.companyId =" + company_id +  " AND T.markForDelete=0 "
							+ " INNER JOIN TripSheetExpense TSE on TSE.tripsheet.tripSheetID = T.tripSheetID AND TSE.markForDelete = 0"
							+ " INNER JOIN TripExpense TE on TE.expenseID = TSE.expenseId"
							
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = A.TRIPSHEETID AND TS.companyId ="+company_id+" " + " where (A.DRIVERID ="
							+ driverid + " AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom + "' AND '" + dateRangeTo
							+ "')) AND A.COMPANY_ID = " + company_id
							+ "  AND A.markForDelete = 0",
					Object[].class);
			try {
				results = query.getResultList();
				if(results != null && !results.isEmpty())
					Dtos.addAll(getFinalAttandanceListForDriverSalary(results, TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE));
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
		}
		return Dtos;
	}
	
	private List<DriverAttendanceDto> getFinalAttandanceListForDriverSalary(List<Object[]>  results, int	tripSheetFlavor){
		
		List<DriverAttendanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto list = null;
			ArrayList<Long> tripsheet = new ArrayList<>();
			for (Object[] result : results) {
				list = new DriverAttendanceDto();
				
				list.setDRIVERID((Integer) result[0]);
				list.setDRIVER_NAME((String) result[1] + "-" + (String) result[2] + " " + (String) result[3]);
				list.setD_ATTENDANCE_DATE((Date) result[4]);
				list.setCREATEDBY((String) result[5]);
				list.setTRIPSHEETID((Long) result[6]);
				list.setTRIP_ROUTE_NAME((String) result[7]);
				if(result[8] != null)
					list.setPOINT_TYPE_ID((short) result[8]);
				list.setTRIPSHEETNUMBER((Long) result[9]);
				if(result[10] != null && !((String)result[10]).trim().equals(""))
					list.setDRIVER_NAME(list.getDRIVER_NAME()+" - "+result[10]);	
				list.setTripSheetFlavor(tripSheetFlavor);
				list.setExpenseId((Integer) result[13]);
				list.setExpenseAmount((Double) result[14]);
				list.setTripSheetExpenseId((Long) result[12]);
				if(result[15] != null){
					if((!tripsheet.contains(list.getTripSheetExpenseId())) && (result[15].equals(result[0])) ){
						Dtos.add(list);
					}
				}
				tripsheet.add(list.getTripSheetExpenseId());
			}
		}
		return Dtos;
	}


}
