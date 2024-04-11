package org.fleetopgroup.persistence.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.DriverAdvanceStatus;
import org.fleetopgroup.constant.DriverAdvanceType;
import org.fleetopgroup.persistence.dao.DriverSalaryAdvanceRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.DriverSalaryAdvanceDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverSalaryAdvance;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryAdvanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DriverSalaryAdvanceService implements IDriverSalaryAdvanceService {
	@Autowired
	private DriverSalaryAdvanceRepository DriverSalaryAdvanceRepository;

	@PersistenceContext
	EntityManager entityManager;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverSalaryAdvanceService#
	 * register_New_DriverSalaryAdvance(org.fleetop.persistence.model.
	 * DriverSalaryAdvance)
	 */
	@Transactional
	public DriverSalaryAdvance register_New_DriverSalaryAdvance(DriverSalaryAdvance accountDto) throws Exception {

		return DriverSalaryAdvanceRepository.save(accountDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverSalaryAdvanceService#
	 * List_Total_OF_DriverSalaryAdvance_details(java.lang.Integer)
	 */
	@Transactional
	public List<DriverSalaryAdvance> List_Total_OF_DriverSalaryAdvance_details(Integer driver_ID, Integer companyId) {

		return DriverSalaryAdvanceRepository.List_Total_OF_DriverSalaryAdvance_details(driver_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverSalaryAdvanceService#
	 * update_DriverSalaryAdvance_Status(java.lang.String, java.lang.Integer)
	 */
	/*@Transactional
	public void update_DriverSalaryAdvance_Status(String advance_Status, Integer driver_ID) throws Exception {

		DriverSalaryAdvanceRepository.update_DriverSalaryAdvance_Status(advance_Status, driver_ID);
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverSalaryAdvanceService#
	 * DRIVER_Advance_BALANCE_TripCollectiom(java.lang.Integer)
	 */
	@Transactional
	public Double DRIVER_Advance_BALANCE_TripCollectiom(Integer dRIVER_ID) throws Exception {

		Query query = entityManager.createQuery(
				" SELECT ROUND(SUM(T.ADVANCE_BALANCE),2) FROM DriverSalaryAdvance AS T WHERE T.markForDelete=0 AND T.DRIVER_ID = " + dRIVER_ID
						+ " AND T.ADVANCE_STATUS_ID="+DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN+" ");

		Double vehicle = null;
		try {
			vehicle = (Double) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		Double Dtos = 0.0;
		if (vehicle != null) {
			Dtos =  vehicle;
		} else {
			return Dtos;
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverSalaryAdvanceService#
	 * List_DriverSalaryAdvance_TOTAL_Report(java.lang.String)
	 */
	@Transactional
	public List<DriverSalaryAdvance> List_DriverSalaryAdvance_TOTAL_Report(String query) throws Exception {

		TypedQuery<DriverSalaryAdvance> queryt = entityManager.createQuery(
				" FROM DriverSalaryAdvance AS R " + "Where R.markForDelete=0 " + query + " ORDER BY R.DSAID desc ",
				DriverSalaryAdvance.class);
		return queryt.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverSalaryAdvanceService#
	 * Report_OF_Driver_Halt_Point(java.lang.Integer, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<DriverAttendanceDto> Report_OF_Driver_Halt_Point(Integer dRIVER_ID, String dateRangeFrom,
			String dateRangeTo) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT A.DRIVERID, D.driver_empnumber, D.driver_firstname,  D.driver_Lastname, A.ATTENDANCE_DATE, D.driver_group, A.TRIPSHEETID, A.TRIP_ROUTE_NAME, T.tripOpenDate, T.closetripDate, T.vehicle_registration, "
						+ " A.DRIVER_POINT, A.POINT_TYPE From DriverAttendance AS A"
						+ " INNER JOIN Driver AS D ON D.driver_id = A.DRIVERID"
						+ " INNER JOIN TripSheet AS T ON T.tripSheetID = A.TRIPSHEETID where (A.DRIVERID ="
						+ dRIVER_ID + " AND A.POINT_STATUS='POINT' AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom
						+ "' AND '" + dateRangeTo
						+ "')) ORDER BY A.ATTENDANCE_DATE desc ",
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
				list.setDRIVER_NAME((String) result[1]+" "+(String) result[2]+" "+(String) result[3]);
				if ((Date) result[4] != null) {
					list.setATTENDANCE_DATE(dateFormat_Name.format((Date) result[4]));
				}
				// driver Group Service
				list.setCREATEDBY((String) result[5]);

				list.setTRIPSHEETID((Long) result[6]);
				list.setTRIP_ROUTE_NAME((String) result[7]);

				// tripOpenDate is only display propose show CREATED Halt
				list.setCREATED((Date) result[8]);
				// tripCloseDate is only display propose show LASTUPDATED Halt
				list.setLASTUPDATED((Date) result[9]);
				// vehicle_registration is only display propose show
				// POINT_STATUS Halt
				list.setPOINT_STATUS((String) result[10]);

				list.setDRIVER_POINT((Double) result[11]);
				list.setPOINT_TYPE((String) result[12]);
				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverSalaryAdvanceService#
	 * Report_OF_Driver_Halt_Point(java.lang.Integer, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<DriverAttendanceDto> Report_OF_Driver_LocalHalt_Point(Integer dRIVER_ID, String dateRangeFrom,
			String dateRangeTo) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT A.DRIVERID, D.driver_empnumber, D.driver_firstname,  D.driver_Lastname , A.ATTENDANCE_DATE, D.driver_group, A.TRIPSHEETID, A.TRIP_ROUTE_NAME, "
						+ " A.DRIVER_POINT, A.POINT_TYPE From DriverAttendance AS A"
						+ " INNER JOIN Driver AS D ON D.driver_id = A.DRIVERID where (A.DRIVERID ="
						+ dRIVER_ID
						+ " AND A.POINT_STATUS='POINT' AND A.TRIPSHEETID=0 AND (A.ATTENDANCE_DATE  between '"
						+ dateRangeFrom + "' AND '" + dateRangeTo
						+ "'))  ORDER BY A.ATTENDANCE_DATE desc ",
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
				list.setDRIVER_NAME((String) result[1]+" "+(String) result[2]+" "+(String) result[3]);
				list.setATTENDANCE_DATE(dateFormat_Name.format((Date) result[4]));
				// driver Group Service
				list.setCREATEDBY((String) result[5]);

				list.setTRIPSHEETID((Long) result[6]);
				list.setTRIP_ROUTE_NAME((String) result[7]);

				list.setDRIVER_POINT((Double) result[8]);
				list.setPOINT_TYPE((String) result[9]);
				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverSalaryAdvanceService#
	 * Report_OF_Driver_Group_Attendance_Point(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<DriverAttendanceDto> Report_OF_Driver_Group_Attendance_Point(String dRIVER_GROUP, String dateRangeFrom,
			String dateRangeTo) {

		TypedQuery<Object[]> query = entityManager.createQuery("SELECT D.DRIVERID, DG.driver_empnumber, DG.driver_firstname,  DG.driver_Lastname, DG.driver_group, "
				+ " SUM(D.DRIVER_POINT) From DriverAttendance AS D"
				+ " INNER JOIN Driver AS DG ON DG.driver_id = D.DRIVERID where D.DRIVERID IN ( " + dRIVER_GROUP
				+ " ) AND  D.POINT_STATUS='POINT' AND (D.ATTENDANCE_DATE  between '" + dateRangeFrom + "' AND '"
				+ dateRangeTo + "') Group By D.DRIVERID  ", Object[].class);

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
				list.setDRIVER_NAME((String) result[1]+" "+(String) result[2]+" "+(String) result[3]);
				// Point type in Group Service
				list.setPOINT_TYPE((String) result[4]);

				list.setDRIVER_POINT((Double) result[5]);

				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverSalaryAdvanceService#
	 * Report_OF_Driver_Attendance(java.lang.Integer, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<DriverAttendanceDto> Report_OF_Driver_Attendance(Integer dRIVER_ID, String dateRangeFrom,
			String dateRangeTo) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT A.DRIVERID, D.driver_empnumber, D.driver_firstname,  D.driver_Lastname, A.ATTENDANCE_DATE, D.driver_group, A.TRIPSHEETID, A.TRIP_ROUTE_NAME, "
						+ "A.POINT_TYPE From DriverAttendance AS A"
						+ " INNER JOIN Driver AS D ON D.driver_id = A.DRIVERID where (A.DRIVERID =" + dRIVER_ID
						+ " AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom + "' AND '" + dateRangeTo
						+ "'))  GROUP BY A.ATTENDANCE_DATE ",
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
				list.setDRIVER_NAME((String) result[1]+" "+(String) result[2]+" "+(String) result[3]);
				if ((Date) result[4] != null) {
					list.setATTENDANCE_DATE(dateFormat_Name.format((Date) result[4]));
				}
				// driver Group Service
				list.setCREATEDBY((String) result[5]);

				list.setTRIPSHEETID((Long) result[6]);
				list.setTRIP_ROUTE_NAME((String) result[7]);
				list.setPOINT_TYPE((String) result[8]);
				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverSalaryAdvanceService#
	 * Report_Group_Driver_Attendance(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<DriverAttendanceDto> Report_Group_Driver_Attendance(String dRIVER_GROUP, String dateRangeFrom,
			String dateRangeTo) {

		TypedQuery<Object[]> query = entityManager
				.createQuery("SELECT A.DRIVERID, D.driver_empnumber, D.driver_firstname,  D.driver_Lastname, D.driver_group, D.driver_perdaySalary,"
						+ " count(DISTINCT A.ATTENDANCE_DATE) From DriverAttendance AS A"
						+ " INNER JOIN Driver AS D ON D.driver_id = A.DRIVERID  where  ( A.DRIVERID IN ( "
						+ dRIVER_GROUP + " )  AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom + "' AND '"
						+ dateRangeTo + "')  ) Group By A.DRIVERID ", Object[].class);

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
				list.setDRIVER_NAME((String) result[1]+" "+(String) result[2]+" "+(String) result[3]);
				// Point type in Group Service
				list.setPOINT_TYPE((String) result[4]);

				// Per Day Salary To Driver table To Get the Driver ID
				// Here Driver Point IN Driver Per day Salary
				list.setDRIVER_POINT((Double) result[5]);

				// ATTENDANCE_DATE to showing in TRIPSHEETID
				list.setTRIPSHEETID((Long) result[6]);

				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverSalaryAdvanceService#
	 * Report_Group_Driver_Attendance(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<Driver> Report_Group_Driver_Attendance_Lastmonth_salaryDetails(String dRIVER_GROUP,
			String dateRangeFrom, String dateRangeTo) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT A.DRIVERID, D.driver_firstname, D.driver_Lastname, D.vehicleGroupId, D.driver_perdaySalary, D.driver_empnumber,  D.driver_esiamount, D.driver_pfamount,"
						+ " count(DISTINCT A.ATTENDANCE_DATE) From DriverAttendance AS A, Driver AS D "
						+ " where  ( A.DRIVERID IN ( "
						+ dRIVER_GROUP + " )  AND (A.ATTENDANCE_DATE  between '" + dateRangeFrom + "' AND '"
						+ dateRangeTo + "')  AND A.DRIVERID = D.driver_id ) Group By A.DRIVERID ",
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
				// Point type in Group Service
				list.setVehicleGroupId((Long) result[3]);

				// Per Day Salary To Driver table To Get the Driver ID
				// Here Driver Point IN Driver Per day Salary
				list.setDriver_perdaySalary((Double) result[4]);

				list.setDriver_empnumber((String) result[5]);

				list.setDriver_esiamount((Double) result[6]);

				list.setDriver_pfamount((Double) result[7]);

				// ATTENDANCE_DATE to showing in TRIPSHEETID
				list.setTripSheetID((Long) result[8]);

				Dtos.add(list);
			}

		}
		return Dtos;
	}

	@Transactional
	public List<DriverSalaryAdvance> GET_DriverSalaryAdvance_details_DRIVER_ID(Integer dRIVER_ID,
			short ADVANCE_STATUS,String salary_FROM_DATE, String salary_TO_DATE) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//return DriverSalaryAdvanceRepository.GET_DriverSalaryAdvance_details_DRIVER_ID(dRIVER_ID, ADVANCE_STATUS, userDetails.getCompany_id());
		
		TypedQuery<DriverSalaryAdvance> query = entityManager.createQuery(
				"FROM DriverSalaryAdvance AS DDT where DDT.DRIVER_ID =" + dRIVER_ID + " AND DDT.ADVANCE_STATUS_ID ="
						+ ADVANCE_STATUS + "" + " AND DDT.ADVANCE_NAME_ID="+DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY+" AND (DDT.ADVANCE_DATE BETWEEN '"
						+ salary_FROM_DATE + "'AND '" + salary_TO_DATE + "') AND DDT.markForDelete=0 AND DDT.COMPANY_ID ="+userDetails.getCompany_id()+""
						+ " OR DDT.DRIVER_ID =" + dRIVER_ID + " AND DDT.ADVANCE_STATUS_ID =" + ADVANCE_STATUS + ""
						+ " AND DDT.ADVANCE_NAME_ID="+DriverAdvanceType.DRIVER_ADVANCE_TYPE_ADVANCE+" AND DDT.markForDelete=0 AND DDT.COMPANY_ID ="+userDetails.getCompany_id()+"",
				DriverSalaryAdvance.class);

		return query.getResultList();
	}

	@Transactional
	public void update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK(Double paidAmountAdvacne, Long dsaid) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		DriverSalaryAdvanceRepository.update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK(paidAmountAdvacne, dsaid, userDetails.getCompany_id());

	}

	@Transactional
	public void update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK_UPDATE_STATUS(Double paidAmountAdvacne,
			short ADVANCE_STATUS, Long dsaid) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		DriverSalaryAdvanceRepository.update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK_UPDATE_STATUS(
				paidAmountAdvacne, ADVANCE_STATUS, dsaid, userDetails.getCompany_id());

	}

	@Transactional
	public DriverSalaryAdvance GET_DRIVER_SALARY_ADVANCE_ID(Long dRIVER_SALARYID, Integer companyId) {

		return DriverSalaryAdvanceRepository.GET_DRIVER_SALARY_ADVANCE_ID(dRIVER_SALARYID, companyId);
	}

	@Transactional
	public void DELETE_DRIVER_SALARY_ADVANCE_ID(Long dRIVER_SALARYID, Integer companyId) {

		DriverSalaryAdvanceRepository.DELETE_DRIVER_SALARY_ADVANCE_ID(dRIVER_SALARYID, companyId);
	}

	@Transactional
	public List<DriverSalaryAdvanceDto> List_OF_TRIPDAILYSHEET_ID_PENALTY_DETAILS(Long tripdailyid, short AdvanceName, int companyId) {
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.DSAID, R.DRIVER_ID, D.driver_firstname, D.driver_Lastname , D.driver_empnumber, R.ADVANCE_AMOUNT, U.firstName, "
						+ " R.ADVANCE_DATE, R.TRIPDAILYID, TR.routeName, VG.vGroup,D.driver_fathername FROM DriverSalaryAdvance AS R "
						+ " LEFT JOIN Driver AS D ON R.DRIVER_ID=D.driver_id "
						+ " LEFT JOIN TripDailySheet AS T ON T.TRIPDAILYID = R.TRIPDAILYID"
						+ " INNER JOIN Vehicle V ON V.vid = T.VEHICLEID"
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId "
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID"
						+ " LEFT JOIN User U ON U.id = R.CREATED_BY_ID"
						+ " WHERE R.markForDelete=0 AND R.TRIPDAILYID=" + tripdailyid + " AND R.ADVANCE_NAME_ID="
						+ AdvanceName + " AND R.COMPANY_ID = "+companyId+" ORDER BY R.DSAID desc",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverSalaryAdvanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverSalaryAdvanceDto>();
			DriverSalaryAdvanceDto list = null;
			for (Object[] result : results) {
				list = new DriverSalaryAdvanceDto();

				list.setDSAID((Long) result[0]);
				list.setDRIVER_ID((Integer) result[1]);
				list.setDriver_firstname((String) result[2]);
				if(result[3] != null) {
					list.setDriver_Lastname((String) result[3]);
				}else {
					list.setDriver_Lastname(" ");
				}
				list.setDriver_empnumber((String) result[4]);
				if(result[5] != null)
				list.setADVANCE_AMOUNT(Double.parseDouble(toFixedTwo.format(result[5])));
				list.setADVANCE_PAID_BY((String) result[6]);
				if (result[7] != null) {
					list.setADVANCE_DATE(dateFormat.format((Date) result[7]));
				}
				list.setTRIPDAILYID((Long) result[8]);
				list.setTRIP_ROUTE_NAME((String) result[9]);
				list.setVEHICLE_GROUP((String) result[10]);
				if(result[11] != null) {
					list.setDriverFatherName((String) result[11]);
				}else {
					list.setDriverFatherName(" ");
				}
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public void DELETE_PENALTY_IN_TRIPSHEET_AID(Long advance_ID) {

		DriverSalaryAdvanceRepository.DELETE_PENALTY_IN_TRIPSHEET_AID(advance_ID);
	}

	@Transactional
	public void DELETE_PENALTY_IN_TRIPDailySheet_Delete_ID(Long tripCollectionID) {

		DriverSalaryAdvanceRepository.DELETE_PENALTY_IN_TRIPDailySheet_Delete_ID(tripCollectionID);
	}
	
	
	@Transactional
	public List<DriverSalaryAdvanceDto> Driver_Advance_Penalty_ReportDate(String query, String dateRangeFrom,
			String dateRangeTo, Integer companyId) {

		/* this only Select column */
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.DSAID, R.DRIVER_ID, D.driver_firstname, D.driver_Lastname , D.driver_empnumber, R.ADVANCE_AMOUNT, U.firstName, R.ADVANCE_NAME_ID, R.ADVANCE_BALANCE, "
						+ " R.ADVANCE_STATUS_ID, R.ADVANCE_DATE, R.TRIPDAILYID, TR.routeName, VG.vGroup ,D.driver_fathername "
						+ " FROM DriverSalaryAdvance AS R "
						+ " LEFT JOIN Driver AS D ON R.DRIVER_ID=D.driver_id "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
						+ " LEFT JOIN TripDailySheet AS T ON R.TRIPDAILYID=T.TRIPDAILYID "
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID"
						+ " LEFT JOIN User U ON U.id = R.CREATED_BY_ID"
						+ " WHERE R.markForDelete=0 AND R.ADVANCE_DATE BETWEEN '" + dateRangeFrom + "' AND '"
						+ dateRangeTo + "' " + query + " AND R.COMPANY_ID = "+companyId+" ORDER BY R.DSAID desc",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverSalaryAdvanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverSalaryAdvanceDto>();
			DriverSalaryAdvanceDto list = null;
			for (Object[] result : results) {
				list = new DriverSalaryAdvanceDto();

				list.setDSAID((Long) result[0]);
				list.setDRIVER_ID((Integer) result[1]);
				list.setDriver_firstname((String) result[2]);
				list.setDriver_Lastname((String) result[3]);
				list.setDriver_empnumber((String) result[4]);
				list.setADVANCE_AMOUNT((Double) result[5]);
				list.setADVANCE_PAID_BY((String) result[6]);

				list.setADVANCE_NAME(DriverAdvanceType.getAdvanceTypeName((short) result[7]));

				list.setADVANCE_BALANCE((Double) result[8]);
				list.setADVANCE_STATUS(DriverAdvanceStatus.getStausName((short) result[9]));

				if (result[10] != null) {
					list.setADVANCE_DATE(dateFormat.format((Date) result[10]));
				}

				list.setTRIPDAILYID((Long) result[11]);
				list.setTRIP_ROUTE_NAME((String) result[12]);
				list.setVEHICLE_GROUP((String) result[13]);
				if(result[14] != null && !((String)result[14]).trim().equals(""))
				list.setDriver_Lastname(list.getDriver_Lastname()+" - "+result[14]);
				Dtos.add(list);
			}
		}
		return Dtos;

	}

	@Override
	@Transactional
	public List<Object[]> List_OF_DEPOT_WISE_PENALTY_DATE_REPORT(long dRIVER_GROUP, List<String> dateSearch, Integer companyId) {

		String IncomeName = " ";
		if (dateSearch != null && !dateSearch.isEmpty()) {
			short CountColumn = 1;
			for (String tripDailyIncome : dateSearch) {

				IncomeName += " , SUM(CASE WHEN R.ADVANCE_DATE = '" + tripDailyIncome
						+ "' THEN R.ADVANCE_AMOUNT ELSE 0 END) AS COLUMN_" + CountColumn + " ";
				CountColumn++;
			}

		}
		System.err.println("*********** "+IncomeName);
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.DRIVER_ID, p.driver_empnumber, p.driver_firstname,p.driver_fathername,  p.driver_Lastname  " + IncomeName
						+ " FROM DriverSalaryAdvance AS R INNER JOIN Driver AS p  ON R.DRIVER_ID = p.driver_id"
						+ " WHERE R.markForDelete=0 AND R.ADVANCE_NAME_ID="+DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY+" AND p.vehicleGroupId=" + dRIVER_GROUP
						+ " AND p.companyId = "+companyId+"  GROUP BY R.DRIVER_ID ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		return results;
	}
	
	
	@Override
	@Transactional
	public List<Object[]> List_OF_DEPOT_WISE_PENALTY_COLUMN_Report(long dRIVER_GROUP, List<String> dateSearch, Integer companyId) {
		
		String IncomeName = " ";
		if (dateSearch != null && !dateSearch.isEmpty()) {
			short CountColumn = 1;
			for (String tripDailyIncome : dateSearch) {

				IncomeName += " , SUM(CASE WHEN R.ADVANCE_DATE = '" + tripDailyIncome
						+ "' THEN R.ADVANCE_AMOUNT ELSE 0 END) AS COLUMN_" + CountColumn + " ";
				CountColumn++;
			}

		}

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.markForDelete " + IncomeName
						+ " FROM DriverSalaryAdvance AS R INNER JOIN Driver AS p  ON R.DRIVER_ID = p.driver_id"
						+ " WHERE R.markForDelete=0 AND R.ADVANCE_NAME_ID="+DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY+" AND p.vehicleGroupId=" + dRIVER_GROUP
						+ " AND  p.companyId = "+companyId+"  ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		return results;
	}
	
	
	@Override
	@Transactional
	public List<Object[]> LIST_OF_INDIDUAL_WISE_PENALTY_DATE_REPORT(Integer dRIVER_ID, String dateRangeFrom,
			String dateRangeTo, Integer companyId) {
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.DRIVER_ID, p.driver_empnumber, p.driver_firstname,  p.driver_Lastname, R.TRIPDAILYID, TR.routeName, R.ADVANCE_DATE, R.ADVANCE_AMOUNT, R.ADVANCE_REMARK,p.driver_fathername "
						+ " FROM DriverSalaryAdvance AS R "
						+ " LEFT JOIN Driver AS p  ON R.DRIVER_ID = p.driver_id "
						+ " LEFT JOIN TripDailySheet AS T  ON R.TRIPDAILYID = T.TRIPDAILYID "
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID"
						+ " WHERE R.markForDelete=0 AND R.ADVANCE_NAME_ID="+DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY+" AND R.DRIVER_ID=" + dRIVER_ID
						+ " AND R.ADVANCE_DATE BETWEEN '"+dateRangeFrom+"' AND  '"+dateRangeTo+"' AND R.COMPANY_ID = "+companyId+" ORDER BY R.ADVANCE_DATE asc ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		return results;
	}
	
	
	@Override
	@Transactional
	public List<Object[]> List_OF_DEPOT_WISE_ADVANCE_DATE_REPORT(long dRIVER_GROUP, List<String> dateSearch, Integer companyId) {

		String IncomeName = " ";
		if (dateSearch != null && !dateSearch.isEmpty()) {
			short CountColumn = 1;
			for (String tripDailyIncome : dateSearch) {

				IncomeName += " , SUM(CASE WHEN R.ADVANCE_DATE = '" + tripDailyIncome
						+ "' THEN R.ADVANCE_AMOUNT ELSE 0 END) AS COLUMN_" + CountColumn + " ";
				CountColumn++;
			}

		}

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.DRIVER_ID, p.driver_empnumber, p.driver_firstname,  p.driver_Lastname ,p.driver_fathername " + IncomeName
						+ " FROM DriverSalaryAdvance AS R INNER JOIN Driver AS p  ON R.DRIVER_ID = p.driver_id"
						+ " WHERE R.markForDelete=0 AND R.ADVANCE_NAME_ID="+DriverAdvanceType.DRIVER_ADVANCE_TYPE_ADVANCE+" AND p.vehicleGroupId=" + dRIVER_GROUP
						+ "  AND R.COMPANY_ID = "+companyId+" GROUP BY R.DRIVER_ID ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		return results;
	}

	@Override
	@Transactional
	public List<Object[]> List_OF_DEPOT_WISE_ADVANCE_COLUMN_Report(long dRIVER_GROUP, List<String> dateSearch, Integer companyId) {
		
		String IncomeName = " ";
		if (dateSearch != null && !dateSearch.isEmpty()) {
			short CountColumn = 1;
			for (String tripDailyIncome : dateSearch) {

				IncomeName += " , SUM(CASE WHEN R.ADVANCE_DATE = '" + tripDailyIncome
						+ "' THEN R.ADVANCE_AMOUNT ELSE 0 END) AS COLUMN_" + CountColumn + " ";
				CountColumn++;
			}

		}

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.markForDelete " + IncomeName
						+ " FROM DriverSalaryAdvance AS R INNER JOIN Driver AS p  ON R.DRIVER_ID = p.driver_id"
						+ " WHERE R.markForDelete=0 AND R.ADVANCE_NAME_ID="+DriverAdvanceType.DRIVER_ADVANCE_TYPE_ADVANCE+" AND p.vehicleGroupId=" + dRIVER_GROUP
						+ " AND R.COMPANY_ID = "+companyId+"  ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		return results;
	}

	@Override
	@Transactional
	public List<Object[]> LIST_OF_INDIDUAL_WISE_ADVANCE_DATE_REPORT(String query, String dateRangeFrom,
			String dateRangeTo, Integer companyId) {
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.DRIVER_ID, p.driver_empnumber, p.driver_firstname,  p.driver_Lastname, R.TRIPDAILYID, TR.routeName, R.ADVANCE_DATE, R.ADVANCE_AMOUNT, R.ADVANCE_REMARK, T.TRIPDAILYNUMBER,p.driver_fathername "
						+ " FROM DriverSalaryAdvance AS R "
						+ " LEFT JOIN Driver AS p  ON R.DRIVER_ID = p.driver_id "
						+ " LEFT JOIN TripDailySheet AS T  ON R.TRIPDAILYID = T.TRIPDAILYID"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.TRIP_ROUTE_ID"
						+ " WHERE R.markForDelete=0 AND R.ADVANCE_NAME_ID="+DriverAdvanceType.DRIVER_ADVANCE_TYPE_ADVANCE+" " 
						+ " AND R.ADVANCE_DATE BETWEEN '"+dateRangeFrom+"' AND  '"+dateRangeTo+"' " + query + " AND R.COMPANY_ID = "+companyId+" ORDER BY R.ADVANCE_DATE asc ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		return results;
	}
	
	
	@Override
	@Transactional
	public void update_DriverSalaryAdvance_OTHER_PENALTY_UPDATE_STATUS_ZERO(double d, short status, Integer dRIVER_ID,
			String dateRangeFrom, String dateRangeTo, Integer companyId) {

		
		entityManager.createQuery(
				"UPDATE DriverSalaryAdvance DDT set DDT.ADVANCE_BALANCE =" + d + " , DDT.ADVANCE_STATUS_ID =" + status
						+ " WHERE DDT.DRIVER_ID =" + dRIVER_ID
						+ " AND ADVANCE_NAME_ID="+DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY+" AND DDT.ADVANCE_STATUS_ID ="+DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN+" AND (DDT.ADVANCE_DATE BETWEEN '"
						+ dateRangeFrom + "' AND '" + dateRangeTo + "') AND DDT.markForDelete=0 AND DDT.COMPANY_ID = "+companyId+" ");
	}

	@Override
	public List<DriverSalaryAdvance> getDriverSalaryAdvanceByTripSheetId(Long tripSheetId, Integer companyId)
			throws Exception {
		
		return DriverSalaryAdvanceRepository.getDriverSalaryAdvanceByTripSheetId(tripSheetId, companyId);
	}
}