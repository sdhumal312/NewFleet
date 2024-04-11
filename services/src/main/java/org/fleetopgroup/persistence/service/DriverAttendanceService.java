package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.DriverAttandancePointStatus;
import org.fleetopgroup.constant.DriverAttandancePointType;
import org.fleetopgroup.constant.DriverStatus;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.persistence.dao.DriverAttendanceRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.model.Company;
import org.fleetopgroup.persistence.model.DriverAttendance;
import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.IDriverAttendanceService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("DriverAttendanceService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DriverAttendanceService implements IDriverAttendanceService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private DriverAttendanceRepository driverAttendanceDao;

	@Autowired
	private ICompanyConfigurationService companyConfigurationService;
	
	@Autowired
	private ICompanyService CompanyService;
	
	@Autowired	private IVehicleGroupService	vehicleGroupService;

	@Autowired private IUserProfileService		userProfileService;

	@Autowired ITripExpenseService       tripExpenseService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAttendanceService#
	 * addDriverAttendance(org.fleetop.persistence.model.DriverAttendance)
	 */
	@Transactional
	public void addDriverAttendance(DriverAttendance Attendance) {

		driverAttendanceDao.save(Attendance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAttendanceService#
	 * ValidateDriverAttendance(java.util.Date)
	 */
	@Transactional
	public DriverAttendance ValidateDriverAttendance(int driver_id, Date driver_Date) {

		return driverAttendanceDao.ValidateDriverAttendance(driver_id, driver_Date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAttendanceService#
	 * updateDriverAttendance(org.fleetop.persistence.model.DriverAttendance)
	 */
	@Transactional
	public void updateDriverAttendance(DriverAttendance driver) {

		driverAttendanceDao.save(driver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAttendanceService#
	 * listDriverAttendance(int)
	 */
	@Transactional
	public List<DriverAttendance> listDriverAttendance(int driver_id) {

		return driverAttendanceDao.listDriverAttendance(driver_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAttendanceService#
	 * list_Date_DriverAttendance(int, java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<DriverAttendanceDto> list_Date_DriverAttendance(int driver_id, String from, String To, Integer companyId) throws Exception  {

		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		TypedQuery<Object[]> query = null;

		if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {

			query = entityManager.createQuery(
					"SELECT RR.DAID, TR.routeName, RR.ATTENDANCE_STATUS_ID, RR.ATTENDANCE_DATE, RR.TRIPSHEETID, TS.tripSheetNumber, "
					+ " RR.TRIP_ROUTE_ID, TS.tripFristDriverID, TS.tripSecDriverID, TS.tripCleanerID "
					+ " from DriverAttendance RR "
					+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  RR.TRIPSHEETID AND TS.markForDelete = 0"
					+ " LEFT JOIN TripRoute TR ON TR.routeID = RR.TRIP_ROUTE_ID "
					+ " where RR.DRIVERID=" + driver_id + " AND  RR.ATTENDANCE_DATE between '" + from
					+ "' AND '" + To + "' AND RR.COMPANY_ID = " + companyId + " AND RR.markForDelete = 0 ",
					Object[].class);

		} else if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {

			query = entityManager.createQuery(
					"SELECT RR.DAID, TR.routeName, RR.ATTENDANCE_STATUS_ID, RR.ATTENDANCE_DATE, RR.TRIPSHEETID, TS.TRIPCOLLNUMBER, "
					+ " RR.TRIP_ROUTE_ID, TS.TRIP_DRIVER_ID, TS.TRIP_CONDUCTOR_ID, TS.TRIP_CLEANER_ID"
					+ " from DriverAttendance RR "
					+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID =  RR.TRIPSHEETID AND TS.markForDelete = 0"
					+ " LEFT JOIN TripRoute TR ON TR.routeID = RR.TRIP_ROUTE_ID "
					+ " where RR.DRIVERID=" + driver_id + " AND  RR.ATTENDANCE_DATE between '" + from
					+ "' AND '" + To + "' AND RR.COMPANY_ID = " + companyId + " AND RR.markForDelete = 0 ",
					Object[].class);

		} else if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {

			query = entityManager.createQuery(
					"SELECT RR.DAID, TR.routeName, RR.ATTENDANCE_STATUS_ID, RR.ATTENDANCE_DATE, RR.TRIPSHEETID, TS.TRIPDAILYNUMBER,"
					+ " RR.TRIP_ROUTE_ID, TS.TRIP_DRIVER_ID, TS.TRIP_CONDUCTOR_ID, TS.TRIP_CLEANER_ID "
					+ " from DriverAttendance RR "
					+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID =  RR.TRIPSHEETID AND TS.markForDelete = 0"
					+ " LEFT JOIN TripRoute TR ON TR.routeID = RR.TRIP_ROUTE_ID "
					+ " where RR.DRIVERID=" + driver_id + " AND  RR.ATTENDANCE_DATE between '" + from
					+ "' AND '" + To + "' AND RR.COMPANY_ID = " + companyId + " AND RR.markForDelete = 0 ",
					Object[].class);

		}

		List<Object[]> results = query.getResultList();

		List<DriverAttendanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto Atten = null;
			for (Object[] result : results) {
				Atten = new DriverAttendanceDto();

				//Atten.setDAID((Long) result[0]);
				Atten.setTRIP_ROUTE_NAME((String) result[1]);
				Atten.setATTENDANCE_STATUS_ID((short) result[2]);
				Atten.setD_ATTENDANCE_DATE((Date) result[3]);
				Atten.setTRIPSHEETID((Long) result[4]);
				Atten.setDAID((Long) result[5]);
				Atten.setTRIP_ROUTE_ID((Integer) result[6]);
				
				Dtos.add(Atten);
			}
		}
		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAttendanceService#
	 * list_Date_DriverAttendance(int, java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<DriverAttendanceDto> list_OF_Point_Date_DriverAttendance(int driver_id, String from, String To , Integer companyId) throws Exception   {

		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		TypedQuery<Object[]> query = null;

		if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {

			query = entityManager.createQuery(
					"SELECT RR.DAID, TR.routeName, RR.ATTENDANCE_STATUS_ID, RR.ATTENDANCE_DATE,  RR.POINT_STATUS_ID, RR.POINT_TYPE_ID, RR.DRIVER_POINT, RR.TRIPSHEETID, TS.tripSheetNumber "
					+ ", RR.TRIP_ROUTE_ID from DriverAttendance RR "
					+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  RR.TRIPSHEETID AND TS.markForDelete = 0"
					+ " LEFT JOIN TripRoute TR ON TR.routeID = RR.TRIP_ROUTE_ID "
					+ " where RR.DRIVERID=" + driver_id + " AND RR.POINT_STATUS_ID= "+DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT+" AND  RR.ATTENDANCE_DATE between '" + from
					+ "' AND '" + To + "' AND RR.COMPANY_ID = " + companyId + " AND RR.markForDelete = 0 ",
					Object[].class);

		} else if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {

			query = entityManager.createQuery(
					"SELECT RR.DAID, TR.routeName, RR.ATTENDANCE_STATUS_ID, RR.ATTENDANCE_DATE,  RR.POINT_STATUS_ID, RR.POINT_TYPE_ID, RR.DRIVER_POINT, RR.TRIPSHEETID, TS.TRIPCOLLNUMBER "
					+ ", RR.TRIP_ROUTE_ID from DriverAttendance RR "
					+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID =  RR.TRIPSHEETID AND TS.markForDelete = 0"
					+ " LEFT JOIN TripRoute TR ON TR.routeID = RR.TRIP_ROUTE_ID "
					+ " where RR.DRIVERID=" + driver_id + " AND RR.POINT_STATUS_ID="+DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT+" AND  RR.ATTENDANCE_DATE between '" + from
					+ "' AND '" + To + "' AND RR.COMPANY_ID = " + companyId + " AND RR.markForDelete = 0 ",
					Object[].class);

		} else if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {

			query = entityManager.createQuery(
					"SELECT RR.DAID, TR.routeName, RR.ATTENDANCE_STATUS_ID, RR.ATTENDANCE_DATE, RR.POINT_STATUS_ID, RR.POINT_TYPE_ID, RR.DRIVER_POINT, RR.TRIPSHEETID, TS.TRIPDAILYNUMBER "
					+ " , RR.TRIP_ROUTE_ID from DriverAttendance RR "
					+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID =  RR.TRIPSHEETID AND TS.markForDelete = 0"
					+ " LEFT JOIN TripRoute TR ON TR.routeID = RR.TRIP_ROUTE_ID "
					+ " where RR.DRIVERID=" + driver_id + " AND RR.POINT_STATUS_ID="+DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT+" AND  RR.ATTENDANCE_DATE between '" + from
					+ "' AND '" + To + "' AND RR.COMPANY_ID = " + companyId + " AND RR.markForDelete = 0 ",
					Object[].class);

		}

		List<Object[]> results = query.getResultList();

		List<DriverAttendanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto Atten = null;
			for (Object[] result : results) {
				Atten = new DriverAttendanceDto();

				//Atten.setDAID((Long) result[0]);
				Atten.setTRIP_ROUTE_NAME((String) result[1]);
				Atten.setATTENDANCE_STATUS_ID((short) result[2]);
				Atten.setD_ATTENDANCE_DATE((Date) result[3]);
				Atten.setPOINT_STATUS_ID((short) result[4]);
				Atten.setPOINT_TYPE_ID((short) result[5]);
				Atten.setDRIVER_POINT((Double) result[6]);
				Atten.setTRIPSHEETID((Long) result[7]);
				Atten.setDAID((Long) result[8]);
				
				Dtos.add(Atten);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAttendanceService#
	 * getDriverAttendance(java.lang.Long)
	 */
	@Transactional
	public DriverAttendance getDriverAttendance(Long driver_id) {

		return driverAttendanceDao.getDriverAttendance(driver_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAttendanceService#
	 * deleteDriverAttendance(java.lang.Long)
	 */
	@Transactional
	public void deleteDriverAttendance(Long driver_id) {

		driverAttendanceDao.deleteDriverAttendance(driver_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAttendanceService#
	 * Driver_Worked_Over(int, java.lang.String, java.lang.String)
	 */
	@Transactional
	public Long Driver_Worked_Over_Monthly(int driver_id, String from, String To) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Query queryt = entityManager
				.createQuery("SELECT count(DISTINCT RR.ATTENDANCE_DATE) From DriverAttendance RR where RR.DRIVERID="
						+ driver_id + " AND RR.ATTENDANCE_DATE between '" + from + "' AND '" + To
						+ "'  AND RR.markForDelete = 0 AND RR.COMPANY_ID = " + userDetails.getCompany_id() + "");
		Long countWorked = (Long) queryt.getSingleResult();
		return countWorked;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAttendanceService#
	 * Driver_Worked_Over(int, java.lang.String, java.lang.String)
	 */
	@Transactional
	public Double Driver_Point_Over_Monthly(int driver_id, String from, String To, Integer companyId) {

		Query queryt = entityManager
				.createQuery("SELECT SUM(RR.DRIVER_POINT) From DriverAttendance RR where RR.DRIVERID=" + driver_id
						+ " AND RR.POINT_STATUS_ID="+DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT+" AND RR.ATTENDANCE_DATE between '" + from + "' AND '" + To
						+ "' AND RR.COMPANY_ID = " + companyId + "  AND RR.markForDelete = 0");
		Double countWorked = (Double) queryt.getSingleResult();
		return countWorked;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAttendanceService#
	 * validate_DriverAttendance_Halt_details(int, java.util.Date, java.lang.String)
	 */
	@Transactional
	public DriverAttendance validate_DriverAttendance_Halt_details(int driver_id, String driver_Date, short openType, int companyId) {
		
		Query query = entityManager.createQuery("from DriverAttendance RR where RR.DRIVERID=" + driver_id
				+ " AND  RR.ATTENDANCE_DATE = '" + driver_Date + "'  AND RR.POINT_STATUS_ID=" +DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT+""
						+ " AND RR.POINT_TYPE_ID="
				+ openType + " AND RR.markForDelete = 0 AND RR.COMPANY_ID = " + companyId + "");

		Object driveratten = null;
		try {
			driveratten = (Object) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		if (driveratten == null) {
			return null;
		} else {
			return (DriverAttendance) driveratten;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAttendanceService#
	 * Update_DriverAttendance_Halt_point(java.lang.String, java.lang.String,
	 * java.lang.String, double, java.lang.Long)
	 */
	/*@Transactional
	public void Update_DriverAttendance_Halt_point(String ATTENDANCE_STATUS, String POINT_STATUS, String POINT_TYPE,
			double DRIVER_POINT, Long daid) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		driverAttendanceDao.Update_DriverAttendance_Halt_point(ATTENDANCE_STATUS, POINT_STATUS, POINT_TYPE,
				DRIVER_POINT, daid, userDetails.getCompany_id());
	}*/
	
	@Override
	@Transactional
	public void Update_DriverAttendance_Halt_point(short ATTENDANCE_STATUS, short POINT_STATUS, short POINT_TYPE,
			double DRIVER_POINT, Long daid, int companyId) {
		
		driverAttendanceDao.Update_DriverAttendance_Halt_point(ATTENDANCE_STATUS, POINT_STATUS, POINT_TYPE,
				DRIVER_POINT, daid, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverAttendanceService#
	 * DELETE_DRIVERATTENDANCE_TRIPSHEET_ID(java.lang.Long)
	 */
	@Transactional
	public void DELETE_DRIVERATTENDANCE_TRIPSHEET_ID(Long tripSheetID, int companyId) {
		
		driverAttendanceDao.DELETE_DRIVERATTENDANCE_TRIPSHEET_ID(tripSheetID, companyId);
	}

	@Override
	@Transactional
	public void DELETE_DRIVERATTENDANCE_LOCAL_HALTBATA_ID(Long haltBataID) {
		
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		driverAttendanceDao.DELETE_DRIVERATTENDANCE_LOCAL_HALTBATA_ID(haltBataID, userDetails.getCompany_id());
	}

	
	@Override
	public HashMap<String, Object> getAttandanceReport(HashMap<String, Object> inObject) throws Exception {
		String 							HALT_DATE_RANGE						= null;
		Integer							driverJobType						= null;
		long							vehicleGroupId						= 0;
		String[] 						From_TO_DateRange 					= null;
		List<DriverAttendanceDto>	    attandanceList						= null;
		CustomUserDetails				userDetails							= null;
		try {
			String dateRangeFrom = "", dateRangeTo = "";
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Company esi_workingDays_company = CompanyService.GET_USER_ID_TO_COMPANY_DETAILS_ESI_PF_WORKING_DAYS(userDetails.getCompany_id());
			if(esi_workingDays_company != null) {
				
					HALT_DATE_RANGE = (String) inObject.get("ATTENDANCE_DATE");
					driverJobType	=  Integer.parseInt((String) inObject.get("DRIVER_JOBTITLE"));
					vehicleGroupId	= Long.parseLong(inObject.get("DRIVER_GROUP_ID")+"");

						From_TO_DateRange = HALT_DATE_RANGE.split(" to ");

						dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
						dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

						
						attandanceList	= Driver_MONTH_WISE_ATTENDANCE_ReportDate(vehicleGroupId, driverJobType, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
						attandanceList	=	getFinalList(attandanceList, esi_workingDays_company);
						inObject.clear();
						inObject.put("attandanceList", attandanceList);
						inObject.put("SearchDate", HALT_DATE_RANGE.replace(" ", "_"));
						if(vehicleGroupId > 0)
							inObject.put("SearchGroup", vehicleGroupService.getVehicleGroupByID(vehicleGroupId).getvGroup());
						inObject.put("company",
								userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			}
			
			return inObject;
		} catch (Exception e) {
			throw e;
		}finally {
			
		}
	}
	
	@Override
	public List<DriverAttendanceDto> Driver_MONTH_WISE_ATTENDANCE_ReportDate(long dRIVER_GROUP, Integer jobTitle,
			String dateRangeFrom, String dateRangeTo, Integer companyId) {

		TypedQuery<Object[]> query = entityManager
				.createQuery("SELECT R.DAID, R.DRIVERID, D.driver_firstname, D.driver_Lastname, D.driver_empnumber, DDT.driJobType, count(distinct ATTENDANCE_DATE), D.driver_perdaySalary, D.driver_fathername "
						+ " From DriverAttendance AS R "
						+ " INNER JOIN Driver AS D ON R.DRIVERID = D.driver_id "
						+ " LEFT JOIN DriverJobType DDT ON DDT.driJobId = D.driJobId"
						+ " where D.driJobId = "+jobTitle+" AND D.vehicleGroupId = "+dRIVER_GROUP+" AND R.markForDelete=0  AND R.ATTENDANCE_DATE between '" + dateRangeFrom + "' AND '"
						+ dateRangeTo + "' AND R.COMPANY_ID = "+companyId+"  GROUP BY R.DRIVERID ORDER BY D.driver_empnumber ASC ", Object[].class);

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
				
				list.setDAID((Long) result[0]);
				list.setDRIVERID((int) result[1]);
				list.setDRIVER_NAME((String) result[2] +" "+(String) result[3]);
				list.setDriver_empnumber((String) result[4]);
				list.setDriverJobType((String) result[5]);
				list.setTotalWorkingDays((long) result[6]);
				list.setPerDaySalary((Double) result[7]);
				if(result[8]!= null && !((String)result[8]).trim().equals("")) 
					list.setDRIVER_NAME(list.getDRIVER_NAME()+" - "+result[8]);
				
				Dtos.add(list);
			}

		}
		return Dtos;
	}
	
	private List<DriverAttendanceDto> getFinalList(List<DriverAttendanceDto> attendanceList, Company company) throws Exception{
		List<DriverAttendanceDto>	finalList		= null;
		try {
			finalList	= new ArrayList<>();
				if(attendanceList != null) {
					for(DriverAttendanceDto attendanceDto : attendanceList) {
						attendanceDto.setDAID(attendanceDto.getDAID());
						attendanceDto.setDRIVERID(attendanceDto.getDRIVERID());
						attendanceDto.setDRIVER_NAME(attendanceDto.getDRIVER_NAME());
						attendanceDto.setDriver_empnumber(attendanceDto.getDriver_empnumber());
						attendanceDto.setAllWorkingDays(attendanceDto.getTotalWorkingDays());
						if(attendanceDto.getDriverJobType() != null) {
							attendanceDto.setDriverJobType(attendanceDto.getDriverJobType());

						}else {
							attendanceDto.setDriverJobType("--");
						}
						if(company!= null) {
							if(attendanceDto.getTotalWorkingDays() <= company.getCompany_esi_pf_days()) {
								attendanceDto.setTotalWorkingDays(attendanceDto.getTotalWorkingDays());
								attendanceDto.setExtraWorkingDays(0);
							}else {
								attendanceDto.setExtraWorkingDays(attendanceDto.getTotalWorkingDays() - company.getCompany_esi_pf_days());
								attendanceDto.setTotalWorkingDays(company.getCompany_esi_pf_days());
							}
						}
						finalList.add(attendanceDto);
					}
				}
			return finalList;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public TripSheetDto Driver_Trip_Details_Over_Monthly(Integer driver_id, String fromDate, String endDate, HashMap<String, Object> configuration) throws Exception {

		List<TripSheetDto> 				firstDriverTripSheetList		= null;
		List<TripSheetDto> 				secondDriverTripSheetList		= null;
		List<TripSheetDto> 				cleanerDriverTripSheetList		= null;
		TripSheetDto					tripSheet						= null;
		Thread							firstDriverThread				= null;	
		Thread							secondDriverThread				= null;
		Thread							cleanerThread					= null;
		ValueObject						valueObject;
		
		try {
			valueObject		= new ValueObject();
			
			firstDriverThread	= new Thread() {
				public void run() {
					List<TripSheetDto> 				tripSheetList		= null;
					try {
						tripSheetList = getFirstDriverList(driver_id, fromDate, endDate, configuration);
						valueObject.put("firstDriverTripSheetList", tripSheetList);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			firstDriverThread.start();
			
			secondDriverThread = new Thread() {
				public void run() {
					List<TripSheetDto> 				tripSheetList		= null;
					try {
						tripSheetList = getSecondDriverList(driver_id, fromDate, endDate, configuration);
						valueObject.put("secondDriverTripSheetList", tripSheetList);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			secondDriverThread.start();
			
			cleanerThread = new Thread() {
				public void run() {
					List<TripSheetDto> 				tripSheetList		= null;
					try {
						tripSheetList = getCleanerDriverList(driver_id, fromDate, endDate, configuration);
						valueObject.put("cleanerTripSheetList", tripSheetList);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			cleanerThread.start();
			
			firstDriverThread.join();
			secondDriverThread.join();
			cleanerThread.join();
			
			firstDriverTripSheetList		= (List<TripSheetDto>) valueObject.get("firstDriverTripSheetList");
			secondDriverTripSheetList		= (List<TripSheetDto>) valueObject.get("secondDriverTripSheetList");
			cleanerDriverTripSheetList		= (List<TripSheetDto>) valueObject.get("cleanerTripSheetList");
			
			if(firstDriverTripSheetList != null) {
				
				
				for(TripSheetDto  tripSheetDto: firstDriverTripSheetList) {
					if(tripSheet == null) {
						tripSheet	= new TripSheetDto();
						tripSheet.setTripSheetCount(tripSheetDto.getTripSheetCount());
						tripSheet.setTotalKMUsage(tripSheetDto.getTotalKMUsage());
						tripSheet.setExpenseAmount(tripSheetDto.getExpenseAmount());
					} else {
						tripSheet.setTripSheetCount(tripSheet.getTripSheetCount() + tripSheetDto.getTripSheetCount());
						tripSheet.setTotalKMUsage(tripSheet.getTotalKMUsage() + tripSheetDto.getTotalKMUsage());	
						tripSheet.setExpenseAmount(tripSheet.getExpenseAmount() + tripSheetDto.getExpenseAmount());
					}
				}
			}
			
			if(secondDriverTripSheetList != null) {		
				for(TripSheetDto  tripSheetDto: secondDriverTripSheetList) {
					if(tripSheet == null) {
						tripSheet	= new TripSheetDto();
						tripSheet.setTripSheetCount(tripSheetDto.getTripSheetCount());
						tripSheet.setTotalKMUsage(tripSheetDto.getTotalKMUsage());			
						tripSheet.setExpenseAmount(tripSheetDto.getExpenseAmount());
					} else {
						tripSheet.setTripSheetCount(tripSheet.getTripSheetCount() + tripSheetDto.getTripSheetCount());
						tripSheet.setTotalKMUsage(tripSheet.getTotalKMUsage() + tripSheetDto.getTotalKMUsage());		
						tripSheet.setExpenseAmount(tripSheet.getExpenseAmount() + tripSheetDto.getExpenseAmount());
					}
				}
			}
			
			if(cleanerDriverTripSheetList != null) {				
				for(TripSheetDto  tripSheetDto: cleanerDriverTripSheetList) {
					if(tripSheet == null) {
						tripSheet	= new TripSheetDto();
						tripSheet.setTripSheetCount(tripSheetDto.getTripSheetCount());
						tripSheet.setTotalKMUsage(tripSheetDto.getTotalKMUsage());			
						tripSheet.setExpenseAmount(tripSheetDto.getExpenseAmount());
					} else {
						tripSheet.setTripSheetCount(tripSheet.getTripSheetCount() + tripSheetDto.getTripSheetCount());
						tripSheet.setTotalKMUsage(tripSheet.getTotalKMUsage() + tripSheetDto.getTotalKMUsage());		
						tripSheet.setExpenseAmount(tripSheet.getExpenseAmount() + tripSheetDto.getExpenseAmount());
					}
				}
			}
			return tripSheet; 
		} catch (Exception e) {
			throw e;
		} finally {
			firstDriverTripSheetList		= null;
			secondDriverTripSheetList		= null;
			tripSheet						= null;
			firstDriverThread				= null;
			secondDriverThread				= null;
		}
	}
	
	private List<TripSheetDto> getFirstDriverList(Integer driver_id, String fromDate, String endDate, HashMap<String, Object>	configuration) throws Exception {
		
		TypedQuery<Object[]> 			typedQuery			= null;
		List<Object[]> 					results 			= null;
		List<TripSheetDto> 				tripSheetList		= null;
		TripSheetDto 					tripSheet 			= null;
		int								driverExpenseId		= 0;
		
		try {
			driverExpenseId	= (int) configuration.get("firstDriverExpenseId");
			typedQuery = entityManager.createQuery(
					"SELECT count(DISTINCT TS.tripSheetID), sum(TS.tripUsageKM), sum(TE.expenseAmount)"
							+ " FROM TripSheet TS"
							+ " INNER JOIN Driver D ON TS.tripFristDriverID = D.driver_id "
							+ " LEFT JOIN TripSheetExpense TE ON TE.tripsheet.tripSheetID = TS.tripSheetID AND TE.expenseId = "+driverExpenseId+" "
							+ " WHERE  (TS.tripFristDriverID = " + driver_id + ""
							+ " AND (TS.closetripDate  between '" + fromDate + "' AND '" + endDate + "')"
							+ " AND TS.markForDelete = 0) Group By TS.tripFristDriverID",
							Object[].class);
			
			results = typedQuery.getResultList();
			
			if (results != null && !results.isEmpty()) {
				tripSheetList = new ArrayList<>();
				for (Object[] result : results) {
					tripSheet = new TripSheetDto();
					
					tripSheet.setTripSheetCount((Long) result[0]);
					tripSheet.setTotalKMUsage((Long) result[1]);
					tripSheet.setExpenseAmount( (Double) result[2]);
					
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
	
private List<TripSheetDto> getSecondDriverList(Integer driver_id, String fromDate, String endDate, HashMap<String, Object>  configuration) throws Exception {
		
		TypedQuery<Object[]> 			typedQuery			= null;
		List<Object[]> 					results 			= null;
		List<TripSheetDto> 				tripSheetList		= null;
		TripSheetDto 					tripSheet 			= null;
		int								driverExpenseId		= 0;
		
		try {
			
			driverExpenseId	= (int) configuration.get("secondDriverExpenseId");
			
			typedQuery = entityManager.createQuery(
					"SELECT count(DISTINCT TS.tripSheetID), sum(TS.tripUsageKM), sum(TE.expenseAmount)"
							+ " FROM TripSheet TS"
							+ " INNER JOIN Driver D ON TS.tripSecDriverID = D.driver_id "
							+ " LEFT JOIN TripSheetExpense TE ON TE.tripsheet.tripSheetID = TS.tripSheetID AND TE.expenseId = "+driverExpenseId+" "
							+ " WHERE  (TS.tripSecDriverID = " + driver_id + ""
							+ " AND (TS.closetripDate  between '" + fromDate + "' AND '" + endDate + "')"
							+ " AND TS.markForDelete = 0) Group By TS.tripSecDriverID",
							Object[].class);
			
			results = typedQuery.getResultList();
			
			if (results != null && !results.isEmpty()) {
				tripSheetList = new ArrayList<>();
				for (Object[] result : results) {
					tripSheet = new TripSheetDto();
					
					tripSheet.setTripSheetCount((Long) result[0]);
					tripSheet.setTotalKMUsage((Long) result[1]);
					tripSheet.setExpenseAmount( (Double) result[2]);
					
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


private List<TripSheetDto> getCleanerDriverList(Integer driver_id, String fromDate, String endDate, HashMap<String, Object>  configuration) throws Exception {
	
	TypedQuery<Object[]> 			typedQuery			= null;
	List<Object[]> 					results 			= null;
	List<TripSheetDto> 				tripSheetList		= null;
	TripSheetDto 					tripSheet 			= null;
	int								driverExpenseId		= 0;
	
	try {
		
		driverExpenseId	= (int) configuration.get("cleanerExpenseId");
		
		typedQuery = entityManager.createQuery(
				"SELECT count(DISTINCT TS.tripSheetID), sum(TS.tripUsageKM), sum(TE.expenseAmount)"
						+ " FROM TripSheet TS"
						+ " INNER JOIN Driver D ON TS.tripCleanerID = D.driver_id "
						+ " LEFT JOIN TripSheetExpense TE ON TE.tripsheet.tripSheetID = TS.tripSheetID AND TE.expenseId = "+driverExpenseId+" "
						+ " WHERE  (TS.tripCleanerID = " + driver_id + ""
						+ " AND (TS.closetripDate  between '" + fromDate + "' AND '" + endDate + "')"
						+ " AND TS.markForDelete = 0) Group By TS.tripSecDriverID",
						Object[].class);
		
		results = typedQuery.getResultList();
		
		if (results != null && !results.isEmpty()) {
			tripSheetList = new ArrayList<>();
			for (Object[] result : results) {
				tripSheet = new TripSheetDto();
				
				tripSheet.setTripSheetCount((Long) result[0]);
				tripSheet.setTotalKMUsage((Long) result[1]);
				tripSheet.setExpenseAmount( (Double) result[2]);
				
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

@Override
public List<DriverAttendanceDto> getDriverAttandanceListOfTripSheet(Long tripSheetId, Integer companyId)
		throws Exception {
				try {
					TypedQuery<Object[]> query =	null;
					int	flavor	=	companyConfigurationService.getTripSheetFlavor(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
					
					if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
						query = entityManager
								.createQuery("SELECT R.DAID, R.DRIVERID, D.driver_perdaySalary, D.driverSalaryTypeId, t.tripUsageKM, DID.driverSalaryInsertionDetailsId "
										+ " From DriverAttendance AS R "
										+ " INNER JOIN Driver AS D ON R.DRIVERID = D.driver_id "
										+ " INNER JOIN TripSheet t on t.tripSheetID = R.TRIPSHEETID"
										+ " LEFT JOIN DriverSalaryInsertionDetails DID ON DID.driver_id = D.driver_id AND DID.salaryDate ='"+DateTimeUtility.geTimeStampFromDate(new Date())+"'"
												+ " AND DID.markForDelete = 0"
										+ " where R.TRIPSHEETID = "+tripSheetId+" AND  R.COMPANY_ID = "+companyId+" "
										+ " AND R.POINT_TYPE_ID = "+DriverAttandancePointType.ATTANDANCE_POINT_TYPE_TRIP+" AND R.markForDelete = 0 ", Object[].class);
					
					}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
						query = entityManager
								.createQuery("SELECT R.DAID, R.DRIVERID, D.driver_perdaySalary, D.driverSalaryTypeId, t.TRIP_USAGE_KM , DID.driverSalaryInsertionDetailsId"
										+ " From DriverAttendance AS R "
										+ " INNER JOIN Driver AS D ON R.DRIVERID = D.driver_id "
										+ " INNER JOIN TripCollectionSheet t on t.TRIPCOLLID = R.TRIPSHEETID"
										+ " LEFT JOIN DriverSalaryInsertionDetails DID ON DID.driver_id = D.driver_id AND DID.salaryDate ='"+DateTimeUtility.geTimeStampFromDate(new Date())+"'"
												+ " AND DID.markForDelete = 0"
										+ " where R.TRIPSHEETID = "+tripSheetId+" AND  R.COMPANY_ID = "+companyId+" "
												+ " AND R.POINT_TYPE_ID = "+DriverAttandancePointType.ATTANDANCE_POINT_TYPE_TRIP+" AND R.markForDelete = 0 ", Object[].class);
					
					}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
						query = entityManager
								.createQuery("SELECT R.DAID, R.DRIVERID, D.driver_perdaySalary, D.driverSalaryTypeId, t.TRIP_USAGE_KM, DID.driverSalaryInsertionDetailsId "
										+ " From DriverAttendance AS R "
										+ " INNER JOIN Driver AS D ON R.DRIVERID = D.driver_id "
										+ " INNER JOIN TripDailySheet t on t.TRIPDAILYID = R.TRIPSHEETID"
										+ " LEFT JOIN DriverSalaryInsertionDetails DID ON DID.driver_id = D.driver_id AND DID.salaryDate ='"+DateTimeUtility.geTimeStampFromDate(new Date())+"' "
												+ " AND DID.markForDelete = 0"
										+ " where R.TRIPSHEETID = "+tripSheetId+" AND  R.COMPANY_ID = "+companyId+" "
												+ " AND R.POINT_TYPE_ID = "+DriverAttandancePointType.ATTANDANCE_POINT_TYPE_TRIP+"AND R.markForDelete = 0 ", Object[].class);
					
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
							
							list.setDAID((Long) result[0]);
							list.setDRIVERID((int) result[1]);
							list.setPerDaySalary((Double) result[2]);
							list.setDriverSalaryTypeId((short) result[3]);
							list.setTripUsageKM((Integer) result[4]);
							list.setDriverSalaryInsertionDetailsId((Long) result[5]);
							
							if(list.getDriverSalaryInsertionDetailsId() == null) {
								Dtos.add(list);
							}
						}
				
					}
					
					
					return Dtos;
				} catch (Exception e) {
					throw	e;
				}
					
		}

@Override
public List<DriverAttendanceDto> getDriverAttandanceOfMonth(TripSheetIncomeDto incomeDto) throws Exception {
	HashMap<String, DriverAttendanceDto>  hashMap	= null;
	try {
		TypedQuery<Object[]> query =	null;
		int	flavor	=	companyConfigurationService.getTripSheetFlavor(incomeDto.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		
		if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
			query = entityManager
					.createQuery("SELECT R.DAID, R.DRIVERID, D.driver_perdaySalary, D.driverSalaryTypeId, t.tripUsageKM, D.driver_firstname, D.driver_Lastname, D.driver_empnumber,"
							+ " t.tripSheetID, t.tripSheetNumber, R.ATTENDANCE_DATE "
							+ " From DriverAttendance AS R "
							+ " INNER JOIN Driver AS D ON R.DRIVERID = D.driver_id "
							+ " INNER JOIN TripSheet t on t.tripSheetID = R.TRIPSHEETID"
							+ " where t.vid = "+incomeDto.getVid()+" AND t.closetripDate between '"+incomeDto.getFromDate()+"' AND '"+incomeDto.getToDate()+"' "
									+ " AND  R.COMPANY_ID = "+incomeDto.getCompanyId()+" AND R.markForDelete = 0 ", Object[].class);
		
		}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
			query = entityManager
					.createQuery("SELECT R.DAID, R.DRIVERID, D.driver_perdaySalary, D.driverSalaryTypeId, t.TRIP_USAGE_KM, D.driver_firstname, D.driver_Lastname, D.driver_empnumber,"
							+ " t.TRIPCOLLID, t.TRIPCOLLNUMBER, R.ATTENDANCE_DATE "
							+ " From DriverAttendance AS R "
							+ " INNER JOIN Driver AS D ON R.DRIVERID = D.driver_id "
							+ " INNER JOIN TripCollectionSheet t on t.TRIPCOLLID = R.TRIPSHEETID"
							+ " where t.VID = "+incomeDto.getVid()+" AND t.TRIP_OPEN_DATE between '"+incomeDto.getFromDate()+"' AND '"+incomeDto.getToDate()+"' "
									+ " AND  R.COMPANY_ID = "+incomeDto.getCompanyId()+" AND R.markForDelete = 0 ", Object[].class);
		
		}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
			query = entityManager
					.createQuery("SELECT R.DAID, R.DRIVERID, D.driver_perdaySalary, D.driverSalaryTypeId, t.TRIP_USAGE_KM, D.driver_firstname, D.driver_Lastname, D.driver_empnumber,"
							+ " t.TRIPDAILYID, t.TRIPDAILYNUMBER, R.ATTENDANCE_DATE"
							+ " From DriverAttendance AS R "
							+ " INNER JOIN Driver AS D ON R.DRIVERID = D.driver_id "
							+ " INNER JOIN TripDailySheet t on t.TRIPDAILYID = R.TRIPSHEETID"
							+ " where t.VEHICLEID = "+incomeDto.getVid()+" AND t.TRIP_OPEN_DATE between '"+incomeDto.getFromDate()+"' AND '"+incomeDto.getToDate()+"'"
									+ " AND  R.COMPANY_ID = "+incomeDto.getCompanyId()+" AND R.markForDelete = 0 ", Object[].class);
		
		}
	
		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
	
		List<DriverAttendanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			hashMap	= new HashMap<>();
			
			Dtos = new ArrayList<DriverAttendanceDto>();
			DriverAttendanceDto list = null;
			for (Object[] result : results) {
				list = new DriverAttendanceDto();
				
				list.setDAID((Long) result[0]);
				list.setDRIVERID((int) result[1]);
				list.setPerDaySalary((Double) result[2]);
				list.setDriverSalaryTypeId((short) result[3]);
				list.setTripUsageKM((Integer) result[4]);
				list.setDRIVER_NAME((String) result[7]+"_"+(String) result[5]);
				list.setTripSheetId((Long) result[8]);
				list.setTripSheetNumber((Long) result[9]);
				list.setD_ATTENDANCE_DATE((Date) result[10]);
				
				
				Double	salary	= 0.0;
				
				if(list.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_DAY) {
					salary	= list.getPerDaySalary();
				}else if(list.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_TRIP) {
					salary	= list.getPerDaySalary();
				}else if(list.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_KM) {
					salary	= list.getPerDaySalary() * list.getTripUsageKM();
				}else {
					salary	= list.getPerDaySalary();
				}
				
				
				
				if(salary != null && salary > 0) {
					list.setDriverSalary(salary);
					
					DriverAttendanceDto	attendanceDto	=	hashMap.get(list.getD_ATTENDANCE_DATE()+"_"+list.getDRIVERID());
					
					if(attendanceDto == null)
						Dtos.add(list);
					
					hashMap.put(list.getD_ATTENDANCE_DATE()+"_"+list.getDRIVERID(), list);
				}
				
			}
	
		}
		
		
		return Dtos;
	} catch (Exception e) {
		throw	e;
	}
		
}

		@SuppressWarnings("unchecked")
		@Override
		public Double getTripSheetByDriver(Integer driver_id, String driverStart, String driverEnd,
				HashMap<String, Object> configuration, Integer companyId) throws Exception {
			TripExpense							tripExpense                     = null;
			double ExpenseAmount = 0.0;
			TypedQuery<Object[]> query = entityManager
					.createQuery("SELECT TS.tripSheetID , TSE.expenseAmount , TE.expenseID , TSE.tripExpenseID, TSE.driverId "
							+ " FROM TripSheet TS"
							+ " INNER JOIN Driver D ON TS.tripFristDriverID = D.driver_id "
							+ " LEFT JOIN TripSheetExpense TSE ON TSE.tripsheet.tripSheetID = TS.tripSheetID AND TSE.markForDelete = 0 "
							+ " LEFT JOIN TripExpense TE ON TE.expenseID = TSE.expenseId"
							+ " WHERE  ( (TS.tripFristDriverID = " + driver_id + " OR TS.tripSecDriverID = "+ driver_id + " OR TS.tripCleanerID = "+ driver_id +" )"  
							+ " AND (TS.closetripDate  between '" + driverStart + "' AND '" + driverEnd + "')"
							+ " AND TS.markForDelete = 0) AND TS.companyId = "+ companyId + " AND TS.tripStutesId = "+ TripSheetStatus.TRIP_STATUS_CLOSED, 
							Object[].class);
			List<Object[]> results = null;
			try {
				results = query.getResultList();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			List<DriverAttendanceDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				for (Object[] result : results) {
					if(result[2]!=null)
						tripExpense  = tripExpenseService.getTripExpense((Integer)result[2],companyId);
					if(result[4] !=null){
						if((tripExpense.getIncldriverbalance()) && (result[4].equals( driver_id)) )	{
							ExpenseAmount += (double)result[1];
						}
					}
				}
			}
			return ExpenseAmount;
		}
}
