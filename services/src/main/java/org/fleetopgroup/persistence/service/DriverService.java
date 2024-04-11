package org.fleetopgroup.persistence.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.aspectj.apache.bcel.classfile.Constant;
import org.fleetopgroup.constant.DriverAdvanceConstant;
import org.fleetopgroup.constant.DriverConfigurationContant;
import org.fleetopgroup.constant.DriverStatus;
import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.TollApiConfiguration;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.DriverDocTypeBL;
import org.fleetopgroup.persistence.bl.DriverReminderBL;
import org.fleetopgroup.persistence.bl.StatusRemarkBL;
import org.fleetopgroup.persistence.dao.DriverCommentRepository;
import org.fleetopgroup.persistence.dao.DriverDocumentHistoryRepository;
import org.fleetopgroup.persistence.dao.DriverDocumentRepository;
import org.fleetopgroup.persistence.dao.DriverHistoryRepository;
import org.fleetopgroup.persistence.dao.DriverPhotoRepository;
import org.fleetopgroup.persistence.dao.DriverReminderHistoryRepository;
import org.fleetopgroup.persistence.dao.DriverReminderRepository;
import org.fleetopgroup.persistence.dao.DriverRenewalReceiptRepository;
import org.fleetopgroup.persistence.dao.DriverRepository;
import org.fleetopgroup.persistence.dao.TripSheetExpenseRepository;
import org.fleetopgroup.persistence.document.DriverPhoto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverCommentDto;
import org.fleetopgroup.persistence.dto.DriverDocumentDto;
import org.fleetopgroup.persistence.dto.DriverDocumentHistoryDto;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.DriverHaltDto;
import org.fleetopgroup.persistence.dto.DriverReminderDto;
import org.fleetopgroup.persistence.dto.DriverReminderHistoryDto;
import org.fleetopgroup.persistence.dto.DriverRenewalRecieptDto;
import org.fleetopgroup.persistence.dto.DriverSalaryAdvanceDto;
import org.fleetopgroup.persistence.dto.DriverSalaryReportDto;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.VehicleAccidentDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverComment;
import org.fleetopgroup.persistence.model.DriverDocument;
import org.fleetopgroup.persistence.model.DriverDocumentHistory;
import org.fleetopgroup.persistence.model.DriverHistory;
import org.fleetopgroup.persistence.model.DriverReminder;
import org.fleetopgroup.persistence.model.DriverReminderHistory;
import org.fleetopgroup.persistence.model.DriverRenewalReceipt;
import org.fleetopgroup.persistence.model.DriverSalaryAdvance;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.UploadFile;
import org.fleetopgroup.persistence.report.dao.DriverCommentDao;
import org.fleetopgroup.persistence.report.dao.DriverReportDao;
import org.fleetopgroup.persistence.report.dao.IFuelReportDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocTypeService;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IDriverHaltService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IStatusChangeRemarkService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAccidentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.ByteConvertor;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import freemarker.template.Configuration;

@Service("DriverService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DriverService implements IDriverService {
	
	enum type{COMMENT,ACCIDENT,FUEL,ISSUE,BREAKDOWN}

	@PersistenceContext
	EntityManager entityManager;

	@Autowired	private DriverRepository 								driverDao;
	@Autowired	private DriverReminderRepository 						driverReminderRepository;
	@Autowired	private DriverDocumentRepository 						driverDocumentRepository;
	@Autowired	private DriverDocumentHistoryRepository 				driverDocumentHistoryRepository;
	@Autowired	private DriverCommentRepository 						driverCommentRepository;
	@Autowired 	private MongoTemplate									mongoTemplate;
	@Autowired 	private ISequenceCounterService							sequenceCounterService;
	@Autowired	private DriverReminderHistoryRepository 				driverReminderHistoryRepository;
	@Autowired	private ICompanyConfigurationService 					companyConfigurationService;
	@Autowired	private IVehicleGroupService							vehicleGroupService;
	@Autowired  private IUserProfileService								userProfileService;
	@Autowired  private DriverCommentDao								driverCommentDao;
	@Autowired  private DriverPhotoRepository							driverPhotoRepository;
	@Autowired  private IDriverHaltService 								driverHaltService;
	@Autowired  private IFuelService 									fuelService;
	@Autowired  private IIssuesService 									issuesService;
	@Autowired  private DriverReportDao 								DriverReportDaoImpl;
	@Autowired 	private IDriverDocTypeService 							driverDocTypeService;
	@Autowired	private IDriverDocumentService							driverDocumentService;
	@Autowired  private DriverRenewalReceiptRepository					driverRenewalReceiptRepo;
	@Autowired  IStatusChangeRemarkService 								statusRemarkService;
	@Autowired	private DriverHistoryRepository 						driverHistoryDao;
	@Autowired         IFuelReportDao       							fuelReportDao;
	@Autowired         IVehicleAccidentDetailsService    				accidentDetailsService;
	@Autowired private TripSheetExpenseRepository    				    tripSheetExpenseRepo;
		

	// @Autowired
	// private FileUploadDAO fileUploadDao;
	private static final int PAGE_SIZE = 10;
	
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat_SQL = new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 						AttendanceMonth 	= new SimpleDateFormat("MM");
	DriverBL 				DBL					 		= new DriverBL();
	DriverReminderBL 		driverReminderBL		 	= new DriverReminderBL();
	DriverDocTypeBL 		driverDocTypeBL			 	= new DriverDocTypeBL();

	@Transactional
	public List<Driver> ValidateDriver(String driver_DLnumber, String driver_Empnumber, Integer companyId) {
		return driverDao.ValidateDriver(driver_DLnumber, driver_Empnumber, companyId);
	}
	
	
	@Override
	public List<Driver> ValidateEmpDriver(String driver_Empnumber, Integer companyId)
			throws Exception {
		return driverDao.ValidateEmpDriver(driver_Empnumber, companyId);
	}
	
	@Override
	public List<Driver> ValidateDriver(String driver_Empnumber, Integer companyId) throws Exception {
		return driverDao.ValidateDriver(driver_Empnumber, companyId);
		}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Driver addDriver(Driver driver) {
		Driver savedDriver	= driverDao.save(driver);
		return savedDriver;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateDriver(Driver driver) {
		driverDao.save(driver);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addFileDriver(UploadFile upload) {
		// fileUploadDao.save(upload);
	}

	/** This Page get Driver table to get pagination values */
	@SuppressWarnings("deprecation")
	@Transactional
	public Page<Driver> getDeployment_Page_Driver(Integer Job_Type, Integer pageNumber, CustomUserDetails userDetails)
			throws Exception {

		try {
			PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Direction.DESC, "driver_id");
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return driverDao.getDeployment_Page_Driver_JobType(Job_Type, userDetails.getCompany_id(), pageable);
			} else {
				return driverDao.getDeployment_Page_Driver_JobType(Job_Type, userDetails.getCompany_id(),
						userDetails.getId(), pageable);
			}

		} catch (Exception e) {
			throw e;
		}

	}

	/** This List get Driver table to get pagination last 10 entries values */
	@Transactional
	public List<DriverDto> listDriver(Integer Job_Type, Integer pageNumber, CustomUserDetails userDetails)
			throws Exception {
		HashMap<String, Object> configuration = null;
		try {
			configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			TypedQuery<Object[]> queryt = null;
			if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
				if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.tripSheetNumber, R.driver_fathername FROM Driver as R "
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
									+ " LEFT JOIN TripSheet TS ON TS.tripSheetID = R.tripSheetID AND TS.markForDelete = 0"
									+ " WHERE R.driJobId=" + Job_Type + " and R.companyId = "
									+ userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.driver_id desc",
							Object[].class);
				} else if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPCOLLNUMBER, R.driver_fathername FROM Driver as R "
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
									+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID = R.tripSheetID AND TS.markForDelete = 0"
									+ " WHERE R.driJobId=" + Job_Type + " and R.companyId = "
									+ userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.driver_id desc",
							Object[].class);
				} else if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPDAILYNUMBER, R.driver_fathername  FROM Driver as R "
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
									+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID = R.tripSheetID AND TS.markForDelete = 0"
									+ " WHERE R.driJobId=" + Job_Type + " and R.companyId = "
									+ userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.driver_id desc",
							Object[].class);
				}

			} else {
				if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.tripSheetNumber, R.driver_fathername  FROM Driver as R "
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
									+ " LEFT JOIN TripSheet TS ON TS.tripSheetID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
									+ userDetails.getId() + "" + " WHERE R.driJobId=" + Job_Type + " and R.companyId = "
									+ userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.driver_id desc",
							Object[].class);
				} else if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPCOLLNUMBER, R.driver_fathername  FROM Driver as R "
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
									+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
									+ userDetails.getId() + "" + " WHERE R.driJobId=" + Job_Type + " and R.companyId = "
									+ userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.driver_id desc",
							Object[].class);
				} else if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPDAILYNUMBER, R.driver_fathername FROM Driver as R "
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
									+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
									+ userDetails.getId() + "" + " WHERE R.driJobId=" + Job_Type + " and R.companyId = "
									+ userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.driver_id desc",
							Object[].class);
				}

			}
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<DriverDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverDto>();
				DriverDto list = null;
				for (Object[] result : results) {
					list = new DriverDto();

					list.setDriver_id((Integer) result[0]);
					list.setDriver_empnumber((String) result[1]);
					list.setDriver_firstname((String) result[2]);
					list.setDriver_Lastname((String) result[3]);
					list.setDriver_dlnumber((String) result[4]);
					list.setDriver_badgenumber((String) result[5]);
					list.setDriver_mobnumber((String) result[6]);
					list.setDriver_group((String) result[7]);
					list.setDriver_jobtitle((String) result[8]);
					list.setDriverStatusId((short) result[9]);
					list.setDriver_active(DriverStatus.getDriverStatus((short) result[9]));
					list.setTripSheetID((Long) result[10]);
					list.setTripSheetNumber((Long) result[11]);
					list.setDriver_fathername((String) result[12]);
					list.setDriverFullName(list.getDriver_firstname());
					if(list.getDriver_Lastname()!= null && !(list.getDriver_Lastname().trim().equals("")))
						list.setDriverFullName(list.getDriverFullName()+" "+list.getDriver_Lastname());
					
					if(list.getDriver_fathername()!= null && !(list.getDriver_fathername().trim().equals(""))) {
						list.setDriverFullName(list.getDriverFullName()+" - "+list.getDriver_fathername());
					}
					

					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}

	}

	/*@Transactional
	public List<Driver> fildAllDriverList() {

		return driverDao.fildAllDriverList();
	}

	@Transactional
	public List<Driver> fildAllCleanerList() {

		return driverDao.fildAllCleanerList();
	}
*/
	@Transactional
	public Driver getDriver(int driver_id, CustomUserDetails userDetails) throws Exception {
		boolean isVehicleGroupWisePermission = companyConfigurationService.getVehicleGroupWisePermission(
				userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		if (!isVehicleGroupWisePermission) {
			return driverDao.getDriver(driver_id, userDetails.getCompany_id());
		}
		return driverDao.getDriver(driver_id, userDetails.getCompany_id(), userDetails.getId());
	}

	@Override
	public DriverDto getDriverDetails(int driver_id, CustomUserDetails userDetails) throws Exception {
		HashMap<String, Object> configuration = null;
		try {
			configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			TypedQuery<Object[]> queryt = null;
			if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
				if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.tripSheetNumber,"
									+ " R.driver_fathername, R.driver_dateofbirth, R.driver_Qualification, R.driver_bloodgroup, R.driver_languages,"
									+ " R.vehicleGroupId, R.driver_perdaySalary, R.driver_esiamount, R.driver_pfamount, R.driver_email,"
									+ " R.driver_homenumber, R.driver_insuranceno, R.driver_esino, R.driver_pfno, R.driver_address, R.driver_address2,"
									+ " R.driver_city, R.driver_state, R.driver_pincode, R.driver_country, R.driver_trainings, R.driver_startdate,"
									+ " R.driver_leavedate, R.driver_dlclass, R.driver_dlprovince, R.driver_authorised, R.driver_dlOriginal,"
									+ " R.driver_reffristname, R.driver_reflastname, R.driver_refcontect, R.driver_photoid, R.driver_aadharnumber,"
									+ " R.driver_bankname, R.driver_banknumber, R.driver_bankifsc, R.driver_pannumber, U.email, R.created,"
									+ " U2.email, R.lastupdated, R.driJobId, R.driverSalaryTypeId, V.vehicle_registration, V.vid, R.salariedId,R.remark  "
									+ " FROM Driver as R "
									+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId "
									+ " LEFT JOIN TripSheet TS ON TS.tripSheetID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " INNER JOIN User U ON U.id = R.createdById"
									+ " INNER JOIN User U2 ON U2.id = R.lastModifiedById"
									+ " LEFT JOIN Vehicle V on V.vid = R.vid"
									+ " WHERE R.driver_id = " + driver_id + " AND R.companyId = "
									+ userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.driver_id desc",
							Object[].class);
				} else if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPCOLLNUMBER,"
									+ " R.driver_fathername, R.driver_dateofbirth, R.driver_Qualification, R.driver_bloodgroup, R.driver_languages,"
									+ " R.vehicleGroupId, R.driver_perdaySalary, R.driver_esiamount, R.driver_pfamount, R.driver_email,"
									+ " R.driver_homenumber, R.driver_insuranceno, R.driver_esino, R.driver_pfno, R.driver_address, R.driver_address2,"
									+ " R.driver_city, R.driver_state, R.driver_pincode, R.driver_country, R.driver_trainings, R.driver_startdate,"
									+ " R.driver_leavedate, R.driver_dlclass, R.driver_dlprovince, R.driver_authorised, R.driver_dlOriginal,"
									+ " R.driver_reffristname, R.driver_reflastname, R.driver_refcontect, R.driver_photoid, R.driver_aadharnumber,"
									+ " R.driver_bankname, R.driver_banknumber, R.driver_bankifsc, R.driver_pannumber, U.email, R.created,"
									+ " U2.email, R.lastupdated, R.driJobId, R.driverSalaryTypeId, V.vehicle_registration , V.vid, R.salariedId,R.remark "
									+ " FROM Driver as R "
									+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " INNER JOIN User U ON U.id = R.createdById"
									+ " INNER JOIN User U2 ON U2.id = R.lastModifiedById"
									+ " LEFT JOIN Vehicle V on V.vid = R.vid"
									+ " WHERE R.driver_id = " + driver_id + " AND R.companyId = "
									+ userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.driver_id desc",
							Object[].class);
				} else if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPDAILYNUMBER,"
									+ " R.driver_fathername, R.driver_dateofbirth, R.driver_Qualification, R.driver_bloodgroup, R.driver_languages,"
									+ " R.vehicleGroupId, R.driver_perdaySalary, R.driver_esiamount, R.driver_pfamount, R.driver_email,"
									+ " R.driver_homenumber, R.driver_insuranceno, R.driver_esino, R.driver_pfno, R.driver_address, R.driver_address2,"
									+ " R.driver_city, R.driver_state, R.driver_pincode, R.driver_country, R.driver_trainings, R.driver_startdate,"
									+ " R.driver_leavedate, R.driver_dlclass, R.driver_dlprovince, R.driver_authorised, R.driver_dlOriginal,"
									+ " R.driver_reffristname, R.driver_reflastname, R.driver_refcontect, R.driver_photoid, R.driver_aadharnumber,"
									+ " R.driver_bankname, R.driver_banknumber, R.driver_bankifsc, R.driver_pannumber, U.email, R.created,"
									+ " U2.email, R.lastupdated, R.driJobId, R.driverSalaryTypeId , V.vehicle_registration, V.vid, R.salariedId,R.remark "
									+ " FROM Driver as R "
									+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId "
									+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " INNER JOIN User U ON U.id = R.createdById"
									+ " INNER JOIN User U2 ON U2.id = R.lastModifiedById"
									+ " LEFT JOIN Vehicle V on V.vid = R.vid"
									+ " WHERE R.driver_id = " + driver_id + " AND R.companyId = "
									+ userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.driver_id desc",
							Object[].class);
				}

			} else {
				if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.tripSheetNumber,"
									+ " R.driver_fathername, R.driver_dateofbirth, R.driver_Qualification, R.driver_bloodgroup, R.driver_languages,"
									+ " R.vehicleGroupId, R.driver_perdaySalary, R.driver_esiamount, R.driver_pfamount, R.driver_email,"
									+ " R.driver_homenumber, R.driver_insuranceno, R.driver_esino, R.driver_pfno, R.driver_address, R.driver_address2,"
									+ " R.driver_city, R.driver_state, R.driver_pincode, R.driver_country, R.driver_trainings, R.driver_startdate,"
									+ " R.driver_leavedate, R.driver_dlclass, R.driver_dlprovince, R.driver_authorised, R.driver_dlOriginal,"
									+ " R.driver_reffristname, R.driver_reflastname, R.driver_refcontect, R.driver_photoid, R.driver_aadharnumber,"
									+ " R.driver_bankname, R.driver_banknumber, R.driver_bankifsc, R.driver_pannumber, U.email, R.created,"
									+ " U2.email, R.lastupdated, R.driJobId, R.driverSalaryTypeId, V.vehicle_registration, V.vid, R.salariedId,R.remark "
									+ " FROM Driver as R "
									+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId "
									+ " LEFT JOIN TripSheet TS ON TS.tripSheetID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " INNER JOIN User U ON U.id = R.createdById"
									+ " INNER JOIN User U2 ON U2.id = R.lastModifiedById"
									+ " LEFT JOIN Vehicle V on V.vid = R.vid"
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
									+ userDetails.getId() + "" + " WHERE R.driver_id = " + driver_id
									+ " AND R.companyId = " + userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.driver_id desc",
							Object[].class);
				} else if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPCOLLNUMBER,"
									+ " R.driver_fathername, R.driver_dateofbirth, R.driver_Qualification, R.driver_bloodgroup, R.driver_languages,"
									+ " R.vehicleGroupId, R.driver_perdaySalary, R.driver_esiamount, R.driver_pfamount, R.driver_email,"
									+ " R.driver_homenumber, R.driver_insuranceno, R.driver_esino, R.driver_pfno, R.driver_address, R.driver_address2,"
									+ " R.driver_city, R.driver_state, R.driver_pincode, R.driver_country, R.driver_trainings, R.driver_startdate,"
									+ " R.driver_leavedate, R.driver_dlclass, R.driver_dlprovince, R.driver_authorised, R.driver_dlOriginal,"
									+ " R.driver_reffristname, R.driver_reflastname, R.driver_refcontect, R.driver_photoid, R.driver_aadharnumber,"
									+ " R.driver_bankname, R.driver_banknumber, R.driver_bankifsc, R.driver_pannumber, U.email, R.created,"
									+ " U2.email, R.lastupdated, R.driJobId, R.driverSalaryTypeId, V.vehicle_registration, V.vid, R.salariedId,R.remark "
									+ " FROM Driver as R "
									+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId "
									+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " INNER JOIN User U ON U.id = R.createdById"
									+ " INNER JOIN User U2 ON U2.id = R.lastModifiedById"
									+ " LEFT JOIN Vehicle V on V.vid = R.vid"
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
									+ userDetails.getId() + "" + " WHERE R.driver_id = " + driver_id
									+ " AND R.companyId = " + userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.driver_id desc",
							Object[].class);
				} else if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPDAILYNUMBER,"
									+ " R.driver_fathername, R.driver_dateofbirth, R.driver_Qualification, R.driver_bloodgroup, R.driver_languages,"
									+ " R.vehicleGroupId, R.driver_perdaySalary, R.driver_esiamount, R.driver_pfamount, R.driver_email,"
									+ " R.driver_homenumber, R.driver_insuranceno, R.driver_esino, R.driver_pfno, R.driver_address, R.driver_address2,"
									+ " R.driver_city, R.driver_state, R.driver_pincode, R.driver_country, R.driver_trainings, R.driver_startdate,"
									+ " R.driver_leavedate, R.driver_dlclass, R.driver_dlprovince, R.driver_authorised, R.driver_dlOriginal,"
									+ " R.driver_reffristname, R.driver_reflastname, R.driver_refcontect, R.driver_photoid, R.driver_aadharnumber,"
									+ " R.driver_bankname, R.driver_banknumber, R.driver_bankifsc, R.driver_pannumber, U.email, R.created,"
									+ " U2.email, R.lastupdated, R.driJobId, R.driverSalaryTypeId, V.vehicle_registration, V.vid, R.salariedId,R.remark "
									+ " FROM Driver as R "
									+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId "
									+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " INNER JOIN User U ON U.id = R.createdById"
									+ " LEFT JOIN User U2 ON U2.id = R.lastModifiedById"
									+ " LEFT JOIN Vehicle V on V.vid = R.vid"
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
									+ userDetails.getId() + "" + " WHERE R.driver_id = " + driver_id
									+ " AND R.companyId = " + userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.driver_id desc",
							Object[].class);
				}

			}
			List<Object[]> results = queryt.getResultList();

			List<DriverDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverDto>();
				DriverDto list = null;
				for (Object[] result : results) {
					list = new DriverDto();

					list.setDriver_id((Integer) result[0]);
					list.setDriver_empnumber((String) result[1]);
					list.setDriver_firstname((String) result[2]);
					list.setDriver_Lastname((String) result[3]);
					list.setDriver_dlnumber((String) result[4]);
					list.setDriver_badgenumber((String) result[5]);
					list.setDriver_mobnumber((String) result[6]);
					list.setDriver_group((String) result[7]);
					list.setDriver_jobtitle((String) result[8]);
					list.setDriverStatusId((short) result[9]);
					list.setTripSheetID((Long) result[10]);
					list.setTripSheetNumber((Long) result[11]);
					list.setDriver_fathername((String) result[12]);
					list.setDriver_dateofbirth((String) result[13]);
					list.setDriver_Qualification((String) result[14]);
					list.setDriver_bloodgroup((String) result[15]);
					list.setDriver_languages((String) result[16]);
					list.setVehicleGroupId((long) result[17]);
					list.setDriver_perdaySalary((Double) result[18]);
					list.setDriver_esiamount((Double) result[19]);
					list.setDriver_pfamount((Double) result[20]);
					list.setDriver_email((String) result[21]);
					list.setDriver_homenumber((String) result[22]);
					list.setDriver_insuranceno((String) result[23]);
					list.setDriver_esino((String) result[24]);
					list.setDriver_pfno((String) result[25]);
					list.setDriver_address((String) result[26]);
					list.setDriver_address2((String) result[27]);
					list.setDriver_city((String) result[28]);
					list.setDriver_state((String) result[29]);
					list.setDriver_pincode((String) result[30]);
					list.setDriver_country((String) result[31]);
					list.setDriver_trainings((String) result[32]);
					list.setDriver_startdate((String) result[33]);
					list.setDriver_leavedate((String) result[34]);
					list.setDriver_dlclass((String) result[35]);
					list.setDriver_dlprovince((String) result[36]);
					list.setDriver_authorised((String) result[37]);
					list.setDriver_dlOriginal((String) result[38]);
					list.setDriver_reffristname((String) result[39]);
					list.setDriver_reflastname((String) result[40]);
					list.setDriver_refcontect((String) result[41]);
					list.setDriver_photoid((Integer) result[42]);
					list.setDriver_aadharnumber((String) result[43]);
					list.setDriver_bankname((String) result[44]);
					list.setDriver_banknumber((String) result[45]);
					list.setDriver_bankifsc((String) result[46]);
					list.setDriver_pannumber((String) result[47]);
					list.setCreatedBy((String) result[48]);
					list.setCreatedOn((Date) result[49]);
					list.setLastModifiedBy((String) result[50]);
					list.setLastupdatedOn((Date) result[51]);
					list.setDriJobId((Integer) result[52]);
					list.setDriverSalaryTypeId((Short) result[53]);
					if(result[54] != null)
						list.setVehicle_registration((String) result[54]);
					if(result[55] != null)
						list.setVid((Integer) result[55]);
					if(result[56] != null) {
					list.setSalariedId((Short) result[56]);
					}
					if(list.getSalariedId() == 2) {
						list.setSalariedStr("YES");
					}else {
						list.setSalariedStr("NO");
					}
					list.setRemark((String) result[57]);
					Dtos.add(list);
				}
			}
			return Dtos.get(0);
		} catch (Exception e) {
			throw e;
		}

	}

	@Transactional
	public Driver getDriver_Details_Fuel_entries(int driver_id) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Query query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager
					.createQuery("SELECT d.driver_id, d.driver_firstname, d.driver_empnumber from Driver AS d "
							+ " where d.driver_id = :id  and d.companyId = " + userDetails.getCompany_id()
							+ "and d.markForDelete = 0");
		} else {
			query = entityManager
					.createQuery("SELECT d.driver_id, d.driver_firstname, d.driver_empnumber from Driver AS d "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id = "
							+ userDetails.getId() + "" + " where d.driver_id = :id  and d.companyId = "
							+ userDetails.getCompany_id() + "and d.markForDelete = 0");
		}

		query.setParameter("id", driver_id);
		Object[] driver = null;
		try {
			driver = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		Driver select;
		if (driver != null) {
			select = new Driver();
			select.setDriver_id((Integer) driver[0]);
			select.setDriver_firstname((String) driver[1]);
			select.setDriver_empnumber((String) driver[2]);
		} else {
			return null;
		}
		return select;
	}

	@Transactional
	public DriverDto getDriver_Details_Firstnamr_LastName(int driver_id, int companyId) {
		
		Query query = entityManager.createQuery(
				"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, d.driver_mobnumber from Driver AS d where d.driver_id = :id and d.companyId = "
						+ companyId + " and d.markForDelete = 0");

		query.setParameter("id", driver_id);
		Object[] driver = null;
		try {
			driver = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		DriverDto select;
		if (driver != null) {
			select = new DriverDto();

			select.setDriver_id((Integer) driver[0]);
			select.setDriver_firstname((String) driver[1]);
			select.setDriver_Lastname((String) driver[2]);
			select.setDriver_empnumber((String) driver[3]);
			select.setDriver_mobnumber((String) driver[4]);
		} else {
			return null;
		}
		return select;
	}

	@Transactional
	public Driver getDriverReg(String driver_register) {

		return driverDao.getDriverReg(driver_register);
	}

	@Transactional
	public List<Driver> listDriverJOB_LC() {

		return driverDao.listDriverJOB_LC();
		/*
		 * TypedQuery<Driver> query = entityManager.createQuery(Driver.class)
		 * .setProjection( Projections.projectionList().add(Projections.property(
		 * "driver_jobtitle"), "driver_jobtitle")
		 * .add(Projections.property("driver_dlclass"), "driver_dlclass"))
		 * .setResultTransformer(Transformers.aliasToBean(Driver.class)).list();
		 */
	}

	@Transactional
	public List<Driver> listDriverReport() {

		return driverDao.findAll();
	}

	@Transactional
	public List<DriverDto> listDriverReport(String Query, CustomUserDetails userDetails) throws Exception {
		HashMap<String, Object> configuration = null;
		try {
			configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			TypedQuery<Object[]> queryt = null;
			if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
				if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.tripSheetNumber,R.driver_fathername"
									+ ",R.driver_dateofbirth,R.driver_startdate,R.driver_authorised,R.driver_homenumber,R.driver_aadharnumber,R.driver_pannumber"
									+ ",R.driver_bankname,R.driver_banknumber,R.driver_bankifsc,R.driver_address,R.driver_address2,R.driver_city,R.driver_state,R.driver_pincode,"
									+ " R.driverJoinDate, DR.driver_dlto, V.vehicle_registration, R.companyId "
									+ " FROM Driver as R "
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " LEFT JOIN TripSheet TS ON TS.tripSheetID = R.tripSheetID AND TS.markForDelete = 0"
									+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
									+ " LEFT JOIN DriverReminder DR ON DR.driver_id = R.driver_id AND DR.markForDelete = 0"
									+ " LEFT JOIN Vehicle V ON V.vid = R.vid "
									+ " WHERE ( R.markForDelete = 0 AND R.companyId = " + userDetails.getCompany_id()
									+ " ) " + Query + " ORDER BY R.driver_empnumber asc ",
							Object[].class);
				} else if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPCOLLNUMBER,R.driver_fathername "
									+ ",R.driver_dateofbirth,R.driver_startdate,R.driver_authorised,R.driver_homenumber,R.driver_aadharnumber,R.driver_pannumber"
									+ ",R.driver_bankname,R.driver_banknumber,R.driver_bankifsc,R.driver_address,R.driver_address2,R.driver_city,"
									+ " R.driver_state,R.driver_pincode, R.driverJoinDate,DR.driver_dlto , V.vehicle_registration, R.companyId "
									+ "FROM Driver as R "
									+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
									+ " LEFT JOIN DriverReminder DR ON DR.driver_id = R.driver_id AND DR.markForDelete = 0"
									+ " LEFT JOIN Vehicle V ON V.vid = R.vid "
									+ " WHERE ( R.markForDelete = 0 AND R.companyId = " + userDetails.getCompany_id()
									+ " ) " + Query + " ORDER BY R.driver_empnumber asc",
							Object[].class);
				} else if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPDAILYNUMBER,R.driver_fathername "
									+ ",R.driver_dateofbirth,R.driver_startdate,R.driver_authorised,R.driver_homenumber,R.driver_aadharnumber,R.driver_pannumber"
									+ ",R.driver_bankname,R.driver_banknumber,R.driver_bankifsc,R.driver_address,R.driver_address2,R.driver_city,"
									+ " R.driver_state,R.driver_pincode, R.driverJoinDate ,DR.driver_dlto, V.vehicle_registration, R.companyId "
									+ "FROM Driver as R "
									+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
									+ " LEFT JOIN DriverReminder DR ON DR.driver_id = R.driver_id AND DR.markForDelete = 0"
									+ " LEFT JOIN Vehicle V ON V.vid = R.vid "
									+ " WHERE ( R.markForDelete = 0 AND R.companyId = " + userDetails.getCompany_id()
									+ " ) " + Query + " ORDER BY R.driver_empnumber asc",
							Object[].class);
				}

			} else {
				if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.tripSheetNumber,R.driver_fathername"
									+ ",R.driver_dateofbirth,R.driver_startdate,R.driver_authorised,R.driver_homenumber,R.driver_aadharnumber,R.driver_pannumber"
									+ ",R.driver_bankname,R.driver_banknumber,R.driver_bankifsc,R.driver_address,R.driver_address2,R.driver_city"
									+ " ,R.driver_state,R.driver_pincode, R.driverJoinDate ,DR.driver_dlto, V.vehicle_registration, R.companyId "
									+ " FROM Driver as R "
									+ " LEFT JOIN TripSheet TS ON TS.tripSheetID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
									+ " LEFT JOIN DriverReminder DR ON DR.driver_id = R.driver_id AND DR.markForDelete = 0"
									+ " LEFT JOIN Vehicle V ON V.vid = R.vid "
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
									+ userDetails.getId() + "" + " WHERE ( R.markForDelete = 0 AND R.companyId = "
									+ userDetails.getCompany_id() + " ) " + Query + " ORDER BY R.driver_empnumber asc",
							Object[].class);
				} else if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPCOLLNUMBER,R.driver_fathername"
									+ ",R.driver_dateofbirth,R.driver_startdate,R.driver_authorised,R.driver_homenumber,R.driver_aadharnumber,R.driver_pannumber"
									+ ",R.driver_bankname,R.driver_banknumber,R.driver_bankifsc,R.driver_address,R.driver_address2,R.driver_city,"
									+ " R.driver_state,R.driver_pincode, R.driverJoinDate ,DR.driver_dlto, V.vehicle_registration ,R.companyId "
									+ " FROM Driver as R "
									+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
									+ " LEFT JOIN DriverReminder DR ON DR.driver_id = R.driver_id AND DR.markForDelete = 0"
									+ " LEFT JOIN Vehicle V ON V.vid = R.vid "
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
									+ userDetails.getId() + "" + " WHERE ( R.markForDelete = 0 AND R.companyId = "
									+ userDetails.getCompany_id() + " ) " + Query + " ORDER BY R.driver_empnumber asc",
							Object[].class);
				} else if ((int) configuration.get(
						TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
					queryt = entityManager.createQuery(
							"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
									+ " R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPDAILYNUMBER,R.driver_fathername"
									+ ",R.driver_dateofbirth,R.driver_startdate,R.driver_authorised,R.driver_homenumber,R.driver_aadharnumber,R.driver_pannumber"
									+ ",R.driver_bankname,R.driver_banknumber,R.driver_bankifsc,R.driver_address,R.driver_address2,R.driver_city,"
									+ " R.driver_state,R.driver_pincode, "
									+ " R.driverJoinDate, DR.driver_dlto, V.vehicle_registration, R.companyId "
									+ " FROM Driver as R "
									+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID = R.tripSheetID AND TS.markForDelete = 0"
									+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = R.driJobId"
									+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
									+ " LEFT JOIN DriverReminder DR ON DR.driver_id = R.driver_id AND DR.markForDelete = 0"
									+ " LEFT JOIN Vehicle V ON V.vid = R.vid "
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
									+ userDetails.getId() + "" + " WHERE ( R.markForDelete = 0 AND R.companyId = "
									+ userDetails.getCompany_id() + " ) " + Query + " ORDER BY R.driver_empnumber asc",
							Object[].class);
				}

			}
			List<Object[]> results = queryt.getResultList();

			List<DriverDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverDto>();
				DriverDto list = null;
				for (Object[] result : results) {
					list = new DriverDto();

					list.setDriver_id((Integer) result[0]);
					list.setDriver_empnumber((String) result[1]);
					list.setDriver_firstname((String) result[2]);
					list.setDriverFullName(list.getDriver_firstname());
					if(result[3] != null && !((String)result[3]).trim().equals("")) {
						list.setDriver_Lastname((String) result[3]);
						list.setDriverFullName(list.getDriverFullName()+" "+result[3]);
					}
					list.setDriver_dlnumber((String) result[4]);
					list.setDriver_badgenumber((String) result[5]);
					list.setDriver_mobnumber((String) result[6]);
					list.setDriver_group((String) result[7]);
					list.setDriver_jobtitle((String) result[8]);
					list.setDriver_active(DriverStatus.getDriverStatus((short) result[9]));
					list.setTripSheetID((Long) result[10]);
					list.setTripSheetNumber((Long) result[11]);
					if(result[12] != null && !((String)result[12]).trim().equals("")) {
						list.setDriver_fathername("- "+result[12]);
						list.setDriverFullName(list.getDriverFullName()+"- "+result[12]);						
					}
					list.setDriver_dateofbirth((String) result[13]);
					list.setDriver_startdate((String) result[14]);
					list.setDriver_authorised((String)result[15]);
					list.setDriver_homenumber((String)result[16]);
					list.setDriver_aadharnumber((String)result[17]);
					list.setDriver_pannumber((String)result[18]);
					list.setDriver_bankname((String)result[19]);
					list.setDriver_banknumber((String)result[20]);
					list.setDriver_bankifsc((String)result[21]);
					list.setDriver_address((String) result[22]);
					if(result[27]!=null)
						list.setDriverJoinDate(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[27]));
					if(result[28]!=null)
						list.setDlexpiryDate(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[28]));
					if(result[29]!=null)
						list.setVehicle_registration((String) result[29]);
					
					
					list.setCompanyId((Integer)result[30]);
					Dtos.add(list);
							
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}

	}

	@Transactional
	public Long countTotalDriver() throws Exception {

		return driverDao.countTotalDriver();
	}

	@Transactional
	public Long countActiveDriver() throws Exception {

		return driverDao.countActiveDriver();
	}

	@Transactional
	public List<DriverDto> SearchDriver(String Search) throws Exception {
		CustomUserDetails userDetails = null;
		HashMap<String, Object> configuration = null;
		List<DriverDto> Dtos = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			String sql = null;
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			int tripSheetFlavor = (int) configuration.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR);
			if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
				if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					
					sql = "SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, "
							+ "R.driver_dlnumber, R.driver_badgenumber, R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID , TS.tripSheetNumber, R.driver_fathername "
							+ " from Driver as R " 
							+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = R.driJobId "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
							+ " LEFT JOIN TripSheet TS ON TS.tripSheetID = R.tripSheetID AND TS.markForDelete = 0" + " "
							+ " where ( CONCAT(IFNULL(driver_firstname, ''), ' ', IFNULL(driver_lastname, '')) Like ('%" + Search + "%') OR CONCAT(IFNULL(driver_firstname, ''), '', IFNULL(driver_lastname, '')) Like ('%" + Search + "%')  OR lower(R.driver_firstname) Like ('%" + Search + "%') OR lower(R.driver_Lastname) Like ('%" + Search + "%') OR lower(R.driver_empnumber) Like ('%" + Search + "%') "
							+ " OR lower(R.driver_mobnumber) Like ('%" + Search + "%') ) AND R.companyId = "
							+ userDetails.getCompany_id() + "and R.markForDelete = 0 ";

				} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
					sql = "SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, "
							+ "R.driver_dlnumber, R.driver_badgenumber, R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPCOLLNUMBER, R.driver_fathername  "
							+ " from Driver as R " 
							+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = R.driJobId "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
							+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID = R.tripSheetID AND TS.markForDelete = 0"
							+ " where ( CONCAT(IFNULL(driver_firstname, ''), ' ', IFNULL(driver_lastname, '')) Like ('%" + Search + "%') OR CONCAT(IFNULL(driver_firstname, ''), '', IFNULL(driver_lastname, '')) Like ('%" + Search + "%')  OR lower(R.driver_firstname) Like ('%" + Search + "%') OR lower(R.driver_Lastname) Like ('%" + Search + "%') OR lower(R.driver_empnumber) Like ('%" + Search + "%') "
							+ " OR lower(R.driver_mobnumber) Like ('%" + Search + "%'))  AND R.companyId = "
							+ userDetails.getCompany_id() + "and R.markForDelete = 0 ";

				} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {

					sql = "SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, "
							+ "R.driver_dlnumber, R.driver_badgenumber, R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPDAILYNUMBER , R.driver_fathername "
							+ " from Driver as R " 
							+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = R.driJobId "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
							+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID = R.tripSheetID AND TS.markForDelete = 0"
							+ " where ( CONCAT(IFNULL(driver_firstname, ''), ' ', IFNULL(driver_lastname, '')) Like ('%" + Search + "%') OR CONCAT(IFNULL(driver_firstname, ''), '', IFNULL(driver_lastname, '')) Like ('%" + Search + "%')  OR lower(R.driver_firstname) Like ('%" + Search + "%') OR lower(R.driver_Lastname) Like ('%" + Search + "%') OR lower(R.driver_empnumber) Like ('%" + Search + "%') "
							+ " OR lower(R.driver_mobnumber) Like ('%" + Search + "%') ) AND R.companyId = "
							+ userDetails.getCompany_id() + "and R.markForDelete = 0 ";
				}
			}else {
				if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					sql = "SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, "
							+ "R.driver_dlnumber, R.driver_badgenumber, R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID , TS.tripSheetNumber, R.driver_fathername "
							+ " from Driver as R " 
							+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = R.driJobId "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
							+ " LEFT JOIN TripSheet TS ON TS.tripSheetID = R.tripSheetID AND TS.markForDelete = 0"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + "" 
							+ " where ( CONCAT(IFNULL(driver_firstname, ''), ' ', IFNULL(driver_lastname, '')) Like ('%" + Search + "%') OR CONCAT(IFNULL(driver_firstname, ''), '', IFNULL(driver_lastname, '')) Like ('%" + Search + "%')  OR lower(R.driver_firstname) Like ('%" + Search + "%') OR lower(R.driver_Lastname) Like ('%" + Search + "%') OR lower(R.driver_empnumber) Like ('%" + Search + "%') "
							+ " OR lower(R.driver_mobnumber) Like ('%" + Search + "%'))  AND R.companyId = "
							+ userDetails.getCompany_id() + "and R.markForDelete = 0 ";
				} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
					sql = "SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, "
							+ "R.driver_dlnumber, R.driver_badgenumber, R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPCOLLNUMBER, R.driver_fathername  "
							+ " from Driver as R " 
							+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = R.driJobId "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
							+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID = R.tripSheetID AND TS.markForDelete = 0"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + "" 
							+ " where ( CONCAT(IFNULL(driver_firstname, ''), ' ', IFNULL(driver_lastname, '')) Like ('%" + Search + "%') OR CONCAT(IFNULL(driver_firstname, ''), '', IFNULL(driver_lastname, '')) Like ('%" + Search + "%')  OR lower(R.driver_firstname) Like ('%" + Search + "%') OR lower(R.driver_Lastname) Like ('%" + Search + "%') OR lower(R.driver_empnumber) Like ('%" + Search + "%') "
							+ " OR lower(R.driver_mobnumber) Like ('%" + Search + "%'))  AND R.companyId = "
							+ userDetails.getCompany_id() + "and R.markForDelete = 0 ";
				} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {

					sql = "SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, "
							+ "R.driver_dlnumber, R.driver_badgenumber, R.driver_mobnumber, VG.vGroup, DJT.driJobType, R.driverStatusId, R.tripSheetID, TS.TRIPDAILYNUMBER , R.driver_fathername "
							+ " from Driver as R " 
							+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = R.driJobId "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
							+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID = R.tripSheetID AND TS.markForDelete = 0"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + "" 
							+ " where ( CONCAT(IFNULL(driver_firstname, ''), ' ', IFNULL(driver_lastname, '')) Like ('%" + Search + "%') OR CONCAT(IFNULL(driver_firstname, ''), '', IFNULL(driver_lastname, '')) Like ('%" + Search + "%')  OR lower(R.driver_firstname) Like ('%" + Search + "%') OR lower(R.driver_Lastname) Like ('%" + Search + "%') OR lower(R.driver_empnumber) Like ('%" + Search + "%') "
							+ " OR lower(R.driver_mobnumber) Like ('%" + Search + "%'))  AND R.companyId = "
							+ userDetails.getCompany_id() + "and R.markForDelete = 0 ";
				}
			}

			/* this only Select column */
			TypedQuery<Object[]> queryt = entityManager.createQuery(sql, Object[].class);
			queryt.setFirstResult(0);
			queryt.setMaxResults(25);
			List<Object[]> results = queryt.getResultList();

			
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverDto>();
				DriverDto list = null;
				for (Object[] result : results) {
					list = new DriverDto();

					list.setDriver_id((Integer) result[0]);
					list.setDriver_empnumber((String) result[1]);
					list.setDriver_firstname((String) result[2]);
					list.setDriver_Lastname((String) result[3]);
					list.setDriver_dlnumber((String) result[4]);
					list.setDriver_badgenumber((String) result[5]);
					list.setDriver_mobnumber((String) result[6]);
					list.setDriver_group((String) result[7]);
					list.setDriver_jobtitle((String) result[8]);
					list.setDriverStatusId((short) result[9]);
					list.setTripSheetID((Long) result[10]);
					list.setTripSheetNumber((Long) result[11]);
					if(result[12] != null) {
						list.setDriverFullName((String) result[12]);
					}else {
						list.setDriverFullName(" ");
					}

					Dtos.add(list);
				}
			}
			}
			return Dtos;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Transactional
	public List<Driver> Search_ALL_Driver_LIST_AJAX(String Search) throws Exception {
		CustomUserDetails userDetails = null;
		List<Driver> Dtos = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber,d.driver_fathername  FROM Driver AS d "
								+ " where ( lower(d.driver_firstname) Like ('%" + Search + "%') OR  "
								+ " lower(d.driver_empnumber) Like ('%" + Search+ "%') OR lower(d.driver_mobnumber) Like ('%" + Search+ "%') )"
								+ " AND d.driverStatusId = "+DriverStatus.DRIVER_STATUS_ACTIVE+" "
								+ " AND d.companyId = " + userDetails.getCompany_id() + "and d.markForDelete = 0",
						Object[].class);
				
			} else {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber,d.driver_fathername   FROM Driver AS d "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" 
								+ " where ( lower(d.driver_firstname) Like ('%" + Search + "%') OR  "
								+ " lower(d.driver_empnumber) Like ('%" + Search+ "%') OR lower(d.driver_mobnumber) Like ('%" + Search+ "%') )"
								+ " AND d.driverStatusId = "+DriverStatus.DRIVER_STATUS_ACTIVE+" "
								+ " AND d.companyId = " + userDetails.getCompany_id() + "and d.markForDelete = 0",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Driver>();
				Driver dropdown = null;
				for (Object[] result : results) {
					dropdown = new Driver();

					dropdown.setDriver_id((Integer) result[0]);
					dropdown.setDriver_firstname((String) result[1]);
					dropdown.setDriver_Lastname((String) result[2]);
					dropdown.setDriver_empnumber((String) result[3]);
					dropdown.setDriver_fathername((String) result[4]);

					Dtos.add(dropdown);
				}
			}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	@Transactional
	public List<Driver> Search_ALL_Driver_LIST_AJAX(String Search, CustomUserDetails userDetails) throws Exception {
		List<Driver> Dtos = null;
		try {
			TypedQuery<Object[]> queryt = null;
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, d.driver_fathername  FROM Driver AS d "
								+ " where ( lower(d.driver_firstname) Like ('%" + Search
								+ "%')  OR  lower(d.driver_empnumber) Like ('%" + Search + "%') "
								+ " OR  lower(d.driver_mobnumber) Like ('%" + Search + "%') ) AND d.companyId = "
								+ userDetails.getCompany_id() + "and d.markForDelete = 0",
						Object[].class);
			} else {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, d.driver_fathername  FROM Driver AS d "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " where ( lower(d.driver_firstname) Like ('%" + Search
								+ "%')  OR  lower(d.driver_empnumber) Like ('%" + Search + "%') "
								+ " OR  lower(d.driver_mobnumber) Like ('%" + Search + "%') ) AND d.companyId = "
								+ userDetails.getCompany_id() + "and d.markForDelete = 0",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Driver>();
				Driver dropdown = null;
				for (Object[] result : results) {
					dropdown = new Driver();

					dropdown.setDriver_id((Integer) result[0]);
					dropdown.setDriver_firstname((String) result[1]);
					if(result[2] != null) {
						dropdown.setDriver_Lastname((String) result[2]);
					}else {
						dropdown.setDriver_Lastname(" ");
					}
					dropdown.setDriver_empnumber((String) result[3]);
					if(result[4] != null) {
						dropdown.setDriver_fathername((String) result[4]);
					}else {
						dropdown.setDriver_fathername(" ");
					}
					Dtos.add(dropdown);
				}
			}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	@Override
	public List<Driver> getDriverAndConductorListByVehicleGroupId(long vehicleGroupId, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, d.driver_fathername  FROM Driver AS d "
						+ " where d.vehicleGroupId = "+vehicleGroupId+"  AND d.companyId =  "+companyId + "and d.markForDelete = 0",
						Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<Driver> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Driver>();
				Driver dropdown = null;
				for (Object[] result : results) {
					dropdown = new Driver();

					dropdown.setDriver_id((Integer) result[0]);
					dropdown.setDriver_firstname((String) result[1]);
					dropdown.setDriver_Lastname((String) result[2]);
					dropdown.setDriver_empnumber((String) result[3]);
					if(result[4] != null && !((String) result[4]).trim().equals(""))
					dropdown.setDriver_Lastname(dropdown.getDriver_Lastname() +" - "+result[4]);
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
	public List<Driver> getConductorListByVehicleGroupId2(long vehicleGroupId, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber"
						+ "  FROM Driver AS d "
						+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = d.driJobId AND DJT.driJobType = 'CONDUCTOR'"
								+ " where d.vehicleGroupId = "+vehicleGroupId+"  AND d.companyId =  "+companyId + "and d.markForDelete = 0",
						Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<Driver> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Driver>();
				Driver dropdown = null;
				for (Object[] result : results) {
					dropdown = new Driver();

					dropdown.setDriver_id((Integer) result[0]);
					dropdown.setDriver_firstname((String) result[1]);
					dropdown.setDriver_Lastname((String) result[2]);
					dropdown.setDriver_empnumber((String) result[3]);

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
	public List<Driver> getConductorListByVehicleGroupId(String term , long vehicleGroupId, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
			List<Driver> Dtos = null;
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  "
						+ " FROM Driver AS d "
						+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = d.driJobId AND DJT.driJobType ='CONDUCTOR' "
						+ " where ( lower(d.driver_firstname) Like ('%" + term+"%')  OR  lower(d.driver_empnumber) Like ('%" + term + "%')"
						+ " OR  lower(d.driver_mobnumber) Like ('%" + term + "%') ) "
						+ " AND d.vehicleGroupId = "+vehicleGroupId+"  AND d.companyId =  "+companyId + "and d.markForDelete = 0",
						Object[].class);
			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Driver>();
				Driver dropdown = null;
				for (Object[] result : results) {
					dropdown = new Driver();

					dropdown.setDriver_id((Integer) result[0]);
					dropdown.setDriver_firstname((String) result[1]);
					dropdown.setDriver_Lastname((String) result[2]);
					dropdown.setDriver_empnumber((String) result[3]);

					Dtos.add(dropdown);
				}
			}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		} finally {

		}
	}
	
	@Override
	public List<Driver> getDriverListByVehicleGroupId(long vehicleGroupId, Integer companyId) throws Exception {
		try {
			
				TypedQuery<Object[]> queryt = null;
					queryt = entityManager.createQuery(
							"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber ,d.driver_fathername"
							+ "  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = d.driJobId AND DJT.driJobType = 'DRIVER'"
									+ " where d.vehicleGroupId = "+vehicleGroupId+"  AND d.companyId =  "+companyId + "and d.markForDelete = 0",
							Object[].class);
				List<Object[]> results = queryt.getResultList();

				List<Driver> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<Driver>();
					Driver dropdown = null;
					for (Object[] result : results) {
						dropdown = new Driver();

						dropdown.setDriver_id((Integer) result[0]);
						dropdown.setDriver_firstname((String) result[1]);
						dropdown.setDriver_Lastname((String) result[2]);
						dropdown.setDriver_empnumber((String) result[3]);
						if(result[4] !=  null  && !((String)result[4]).trim().equals(""))
						dropdown.setDriver_Lastname(dropdown.getDriver_Lastname() +" - "+ result[4]);

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
	public List<Driver> getConductorListByVehicleGroupId(long vehicleGroupId, Integer companyId) throws Exception {
	try {
	TypedQuery<Object[]> queryt = null;
	queryt = entityManager.createQuery(
	"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber"
	+ "  FROM Driver AS d "
	+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = d.driJobId AND DJT.driJobType = 'CONDUCTOR'"
	+ " where d.vehicleGroupId = "+vehicleGroupId+"  AND d.companyId =  "+companyId + "and d.markForDelete = 0",
	Object[].class);
	List<Object[]> results = queryt.getResultList();

	List<Driver> Dtos = null;
	if (results != null && !results.isEmpty()) {
	Dtos = new ArrayList<Driver>();
	Driver dropdown = null;
	for (Object[] result : results) {
	dropdown = new Driver();

	dropdown.setDriver_id((Integer) result[0]);
	dropdown.setDriver_firstname((String) result[1]);
	dropdown.setDriver_Lastname((String) result[2]);
	dropdown.setDriver_empnumber((String) result[3]);

	Dtos.add(dropdown);
	}
	}
	return Dtos;

	} catch (Exception e) {
	throw e;
	} finally {

	}
	}

	@Transactional
	public List<Driver> SearchOnlyDriverNAME(String Search) throws Exception {
		CustomUserDetails userDetails = null;
		List<Driver> Dtos = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, d.driver_mobnumber, d.driver_fathername  FROM Driver AS d "
								+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='DRIVER' AND DJ.companyId = "
								+ userDetails.getCompany_id() + "" + " where d.companyId = "
								+ userDetails.getCompany_id()
								+ " AND d.markForDelete = 0 AND d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE+" "
								+ " AND ( lower(d.driver_firstname) Like (:Search) OR  lower(d.driver_empnumber)"
								+ " Like (:Search) OR lower(d.driver_mobnumber) Like (:Search)"
								+ " )",
						Object[].class);
			} else {

				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, d.driver_mobnumber, d.driver_fathername  FROM Driver AS d "
								+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='DRIVER' AND DJ.companyId = "
								+ userDetails.getCompany_id() + ""
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id ="
								+ userDetails.getId() + "" + " where d.companyId = "
								+ userDetails.getCompany_id()
								+ " AND d.markForDelete = 0 AND d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE+" "
								+ " AND ( lower(d.driver_firstname) Like (:Search) OR  lower(d.driver_empnumber) Like (:Search) OR "
								+ " lower(d.driver_mobnumber) Like (:Search)"
								+ " )",
						Object[].class);
			}
			
			queryt.setParameter("Search", "%"+Search+"%");
			
			List<Object[]> results = queryt.getResultList();

			
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Driver>();
				Driver dropdown = null;
				for (Object[] result : results) {
					dropdown = new Driver();

					dropdown.setDriver_id((Integer) result[0]);
					dropdown.setDriver_firstname((String) result[1]);
					if(result[2] != null) {
						dropdown.setDriver_Lastname((String) result[2]);
					}else {
						dropdown.setDriver_Lastname(" ");
					}
					dropdown.setDriver_empnumber((String) result[3]);
					dropdown.setDriver_mobnumber((String) result[4]);
					
					if(result[5] != null) {
						dropdown.setDriver_fathername((String) result[5]);
					}else {
						dropdown.setDriver_fathername(" ");
					}
					Dtos.add(dropdown);
				}
			}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}
	
	@Transactional
	@Override
	public List<Driver> searchByDriverName(String Search, int companyId, long userId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
			List<Driver> Dtos = null;
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, d.driver_mobnumber  FROM Driver AS d "
								+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='DRIVER' AND DJ.companyId = "
								+ companyId + "" 
								+ " where d.companyId = "+ companyId+" AND d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE+" "
								+ " AND d.markForDelete = 0 AND ( lower(d.driver_firstname) Like ('%" + Search
								+ "%') OR  lower(d.driver_empnumber) Like ('%" + Search + "%')  "
								+ " OR  lower(d.driver_mobnumber) Like ('%" + Search + "%'))",
						Object[].class);
			} else {

				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, d.driver_mobnumber  FROM Driver AS d "
								+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='DRIVER' AND DJ.companyId = "
								+ companyId + ""
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id ="
								+ userId + "" + " where d.companyId = "+ companyId+" AND d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE+" "
								+ " AND d.markForDelete = 0 AND ( lower(d.driver_firstname) Like ('%" + Search
								+ "%') OR  lower(d.driver_empnumber) Like ('%" + Search + "%')  "
								+ " OR  lower(d.driver_mobnumber) Like ('%" + Search + "%'))",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Driver>();
				Driver dropdown = null;
				for (Object[] result : results) {
					dropdown = new Driver();

					dropdown.setDriver_id((Integer) result[0]);
					dropdown.setDriver_firstname((String) result[1]);
					dropdown.setDriver_Lastname((String) result[2]);
					dropdown.setDriver_empnumber((String) result[3]);
					dropdown.setDriver_mobnumber((String) result[4]);

					Dtos.add(dropdown);
				}
			}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		} 
	}
	
	@Override
	public List<Driver> SearchOnlyAllDriverNAME(String Search) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			List<Driver> Dtos = null;
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, d.driver_mobnumber, d.driver_fathername   FROM Driver AS d "
								+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='DRIVER' AND DJ.companyId = "
								+ userDetails.getCompany_id() + "" + " where d.companyId = "
								+ userDetails.getCompany_id()
								+ " AND d.markForDelete = 0 AND ( lower(d.driver_firstname) Like ('%" + Search
								+ "%')  OR  lower(d.driver_empnumber) Like ('%" + Search + "%') OR  lower(d.driver_mobnumber) Like ('%" + Search + "%') )",
						Object[].class);
			} else {

				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, d.driver_mobnumber, d.driver_fathername   FROM Driver AS d "
								+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='DRIVER' AND DJ.companyId = "
								+ userDetails.getCompany_id() + ""
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id ="
								+ userDetails.getId() + "" + " where d.companyId = "
								+ userDetails.getCompany_id()
								+ " AND d.markForDelete = 0 AND ( lower(d.driver_firstname) Like ('%" + Search
								+ "%')  OR  lower(d.driver_empnumber) Like ('%" + Search + "%') OR  lower(d.driver_mobnumber) Like ('%" + Search + "%') )",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

		
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Driver>();
				Driver dropdown = null;
				for (Object[] result : results) {
					dropdown = new Driver();

					dropdown.setDriver_id((Integer) result[0]);
					dropdown.setDriver_firstname((String) result[1]);
					if(result[2] != null) {
						dropdown.setDriver_Lastname((String) result[2]);
					}else {
						dropdown.setDriver_Lastname("");
					}
					dropdown.setDriver_empnumber((String) result[3]);
					dropdown.setDriver_mobnumber((String) result[4]);
					if(result[5] != null) {
						dropdown.setDriver_fathername((String) result[5]);
					}else {
						dropdown.setDriver_fathername("");
					}
					Dtos.add(dropdown);
				}
			}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}
	
	@Override
	public List<Driver> SearchOnlyAllCleanerNAME(String Search) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			List<Driver> Dtos = null;
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, d.driver_mobnumber  FROM Driver AS d "
								+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CLEANER' AND DJ.companyId = "
								+ userDetails.getCompany_id() + "" + " where d.companyId = "
								+ userDetails.getCompany_id()
								+ " AND d.markForDelete = 0 AND ( lower(d.driver_firstname) Like ('%" + Search
								+ "%')  OR  lower(d.driver_empnumber) Like ('%" + Search + "%') OR lower(d.driver_mobnumber) Like ('%" + Search + "%') )",
						Object[].class);
			} else {

				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, d.driver_mobnumber  FROM Driver AS d "
								+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CLEANER' AND DJ.companyId = "
								+ userDetails.getCompany_id() + ""
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id ="
								+ userDetails.getId() + "" + " where d.companyId = "
								+ userDetails.getCompany_id()
								+ " AND d.markForDelete = 0 AND ( lower(d.driver_firstname) Like ('%" + Search
								+ "%')  OR  lower(d.driver_empnumber) Like ('%" + Search + "%') OR lower(d.driver_mobnumber) Like ('%" + Search + "%') )",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Driver>();
				Driver dropdown = null;
				for (Object[] result : results) {
					dropdown = new Driver();

					dropdown.setDriver_id((Integer) result[0]);
					dropdown.setDriver_firstname((String) result[1]);
					dropdown.setDriver_Lastname((String) result[2]);
					dropdown.setDriver_empnumber((String) result[3]);
					dropdown.setDriver_mobnumber((String) result[4]);

					Dtos.add(dropdown);
				}
			}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Transactional
	public List<Driver> SearchOnly_Techinicion_NAME(String Search, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
						+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='MECHANIC' AND DJ.companyId = "
						+ companyId + "" + "where (lower(d.driver_firstname) Like ('%" + Search
						+ "%')  OR  lower(d.driver_empnumber) Like ('%" + Search + "%')  "
						+ " OR  lower(d.driver_mobnumber) Like ('%" + Search + "%') )"
						+ " AND d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE+" AND d.companyId = " + companyId+" "
						+ " AND d.markForDelete = 0 ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<Driver> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Driver>();
			Driver dropdown = null;
			for (Object[] result : results) {
				dropdown = new Driver();

				dropdown.setDriver_id((Integer) result[0]);
				dropdown.setDriver_firstname((String) result[1]);
				dropdown.setDriver_Lastname((String) result[2]);
				dropdown.setDriver_empnumber((String) result[3]);

				Dtos.add(dropdown);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<Driver> SearchOnlyConductorNAME(String Search) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		TypedQuery<Object[]> queryt = null;
		List<Driver> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CONDUCTOR' AND DJ.companyId = "
							+ userDetails.getCompany_id() + " where (lower(d.driver_firstname) Like ('%" + Search
							+ "%') OR lower(d.driver_empnumber) Like ('%" + Search + "%') OR lower(d.driver_mobnumber) Like ('%" + Search + "%')) "
							+ " AND d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE+" AND d.companyId = " + userDetails.getCompany_id()
							+ " AND d.markForDelete = 0",
					Object[].class);
		} else {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CONDUCTOR' AND DJ.companyId = "
							+ userDetails.getCompany_id() + ""
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id = "
							+ userDetails.getId() + " " + " where (lower(d.driver_firstname) Like ('%" + Search
							+ "%') OR lower(d.driver_empnumber) Like ('%" + Search + "%') OR lower(d.driver_mobnumber) Like ('%" + Search + "%')) "
							+ " AND d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE+" AND d.companyId = " + userDetails.getCompany_id()
							+ " AND d.markForDelete = 0",
					Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Driver>();
			Driver dropdown = null;
			for (Object[] result : results) {
				dropdown = new Driver();

				dropdown.setDriver_id((Integer) result[0]);
				dropdown.setDriver_firstname((String) result[1]);
				dropdown.setDriver_Lastname((String) result[2]);
				dropdown.setDriver_empnumber((String) result[3]);

				Dtos.add(dropdown);
			}
		}
		}
		return Dtos;
	}

	@Override
	public List<Driver> SearchOnlyCheckingInspectorNAME(String Search) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CHECKING INSPECTOR' AND DJ.companyId = "
							+ userDetails.getCompany_id() + " where (lower(d.driver_firstname) Like ('%" + Search
							+ "%') OR lower(d.driver_empnumber) Like ('%" + Search + "%') OR  lower(d.driver_mobnumber) Like ('%" + Search + "%'))"
							+ " AND d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE+" AND d.companyId = " + userDetails.getCompany_id()
							+ " AND d.markForDelete = 0",
					Object[].class);
		} else {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CHECKING INSPECTOR' AND DJ.companyId = "
							+ userDetails.getCompany_id() + ""
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id = "
							+ userDetails.getId() + " " + " where (lower(d.driver_firstname) Like ('%" + Search
							+ "%') OR lower(d.driver_empnumber) Like ('%" + Search + "%') OR  lower(d.driver_mobnumber) Like ('%" + Search + "%'))"
							+ " AND d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE+" AND d.companyId = " + userDetails.getCompany_id()
							+ " AND d.markForDelete = 0",
					Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<Driver> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Driver>();
			Driver dropdown = null;
			for (Object[] result : results) {
				dropdown = new Driver();

				dropdown.setDriver_id((Integer) result[0]);
				dropdown.setDriver_firstname((String) result[1]);
				dropdown.setDriver_Lastname((String) result[2]);
				dropdown.setDriver_empnumber((String) result[3]);

				Dtos.add(dropdown);
			}
		}
		return Dtos;
	}
	
	@Override
	public List<Driver> SearchOnlyCheckingInspectorNAME(Long vehicleGroupId) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CHECKING INSPECTOR' AND DJ.companyId = "
							+ userDetails.getCompany_id() 
							+ " where d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE
							+ " AND d.vehicleGroupId = "+vehicleGroupId+" AND d.companyId = " + userDetails.getCompany_id()
							+ " AND d.markForDelete = 0",
					Object[].class);
		} else {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CHECKING INSPECTOR' AND DJ.companyId = "
							+ userDetails.getCompany_id() + ""
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id = "
							+ userDetails.getId() + " " 
							+ " where d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE
							+ " AND d.vehicleGroupId = "+vehicleGroupId+" AND d.companyId = " + userDetails.getCompany_id()
							+ " AND d.markForDelete = 0",
					Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<Driver> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Driver>();
			Driver dropdown = null;
			for (Object[] result : results) {
				dropdown = new Driver();

				dropdown.setDriver_id((Integer) result[0]);
				dropdown.setDriver_firstname((String) result[1]);
				dropdown.setDriver_Lastname((String) result[2]);
				dropdown.setDriver_empnumber((String) result[3]);

				Dtos.add(dropdown);
			}
		}
		return Dtos;
	}
	
	
	@Override
	public List<Driver> getCheckingInspectorList(Integer companyId, Long id) throws Exception {

		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CHECKING INSPECTOR' AND DJ.companyId = "
							+ companyId 
							+ " where d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE
							+ " AND d.companyId = " + companyId
							+ " AND d.markForDelete = 0",
					Object[].class);
		} else {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CHECKING INSPECTOR' AND DJ.companyId = "
							+ companyId + ""
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id = "
							+ id + " " 
							+ " where d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE
							+ " AND d.companyId = " + companyId
							+ " AND d.markForDelete = 0",
					Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<Driver> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Driver>();
			Driver dropdown = null;
			for (Object[] result : results) {
				dropdown = new Driver();

				dropdown.setDriver_id((Integer) result[0]);
				dropdown.setDriver_firstname((String) result[1]);
				dropdown.setDriver_Lastname((String) result[2]);
				dropdown.setDriver_empnumber((String) result[3]);

				Dtos.add(dropdown);
			}
		}
		return Dtos;
	}
	
	@Override
	public List<Driver> getConductorList(Integer companyId, Long id, String term) throws Exception {

		TypedQuery<Object[]> queryt = null;
		List<Driver> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CONDUCTOR' AND DJ.companyId = "
							+ companyId 
							+ " where d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE
							+ " AND ( lower(d.driver_firstname) Like ('%"+term+"%') OR lower(d.driver_empnumber) Like ('%"+term+"%')"
							+ " OR lower(d.driver_mobnumber) Like ('%"+term+"%')  )  AND d.companyId = " + companyId
							+ " AND d.markForDelete = 0",
					Object[].class);
		} else {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CONDUCTOR' AND DJ.companyId = "
							+ companyId + ""
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id = "
							+ id + " " 
							+ " where d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE
							+ " AND ( lower(d.driver_firstname) Like ('%"+term+"%') OR lower(d.driver_empnumber) Like ('%"+term+"%')"
							+ " OR lower(d.driver_mobnumber) Like ('%"+term+"%')  )  AND d.companyId = " + companyId
							+ " AND d.markForDelete = 0",
					Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Driver>();
			Driver dropdown = null;
			for (Object[] result : results) {
				dropdown = new Driver();

				dropdown.setDriver_id((Integer) result[0]);
				dropdown.setDriver_firstname((String) result[1]);
				dropdown.setDriver_Lastname((String) result[2]);
				dropdown.setDriver_empnumber((String) result[3]);

				Dtos.add(dropdown);
			}
		}
		}
		return Dtos;
	}
	
	@Transactional
	public Driver GetDriverEmpNumberToName(String empnumber) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {
			Query query = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_empnumber from Driver AS d where "
							+ " (d.driver_empnumber ='"+ empnumber + "' OR d.driver_dlnumber = '"+empnumber+"') AND d.companyId=" + userDetails.getCompany_id()
							+ " AND d.markForDelete=0  OR d.driver_firstname = '" + empnumber + "' AND d.companyId="
							+ userDetails.getCompany_id() + " AND d.markForDelete=0 ");
			Object[] driver = null;
			try {
				driver = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			Driver select;
			if (driver != null) {
				select = new Driver();
				select.setDriver_id((Integer) driver[0]);
				select.setDriver_firstname((String) driver[1]);
				select.setDriver_empnumber((String) driver[2]);
			} else {
				return null;
			}
			return select;
		} catch (Exception e) {
			// // e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public DriverDto getDriver_Header_Show_Details(Integer driver_id) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Object[] driver = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_photoid, JT.driJobType, VG.vGroup, d.driver_empnumber,d.driverStatusId ,d.driver_fathername  from Driver AS d"
							+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = d.vehicleGroupId "
							+ " INNER JOIN DriverJobType AS JT ON JT.driJobId = d.driJobId "
							+ " where d.driver_id = :id AND d.companyId = " + userDetails.getCompany_id()
							+ " AND d.markForDelete = 0 ");
			query.setParameter("id", driver_id);
			driver = (Object[]) query.getSingleResult();

			if (driver == null || driver.length == 0) {
				return null;
			}
			DriverDto select = new DriverDto();
			if (driver != null) {
				select.setDriver_id((Integer) driver[0]);
				select.setDriver_firstname((String) driver[1]);
				if( driver[2] != null) {
					select.setDriver_Lastname((String) driver[2]);
				}else {
					select.setDriver_Lastname(" ");
				}
				select.setDriver_photoid((Integer) driver[3]);
				select.setDriver_jobtitle((String) driver[4]);
				select.setDriver_group((String) driver[5]);
				select.setDriver_empnumber((String) driver[6]);
				select.setDriverStatusId((Short) driver[7]);
				if(driver[8] != null)
				select.setDriver_Lastname(select.getDriver_Lastname()+" - "+driver[8]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public DriverDto getDriver_Header_Show_Details_RenewalReminder(Integer driver_id, Integer companyId)
			throws Exception {

		Object[] driver = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_photoid, JT.driJobType, VG.vGroup, d.driver_empnumber, d.driver_dlnumber, d.driver_badgenumber from Driver AS d"
							+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = d.vehicleGroupId "
							+ " INNER JOIN DriverJobType AS JT ON JT.driJobId = d.driJobId "
							+ " where d.driver_id = :id AND d.companyId = " + companyId + " AND d.markForDelete = 0");
			query.setParameter("id", driver_id);
			driver = (Object[]) query.getSingleResult();

			if (driver == null || driver.length == 0) {
				return null;
			}
			DriverDto select = new DriverDto();
			if (driver != null) {
				select.setDriver_id((Integer) driver[0]);
				select.setDriver_firstname((String) driver[1]);
				select.setDriver_Lastname((String) driver[2]);
				select.setDriver_photoid((Integer) driver[3]);
				select.setDriver_jobtitle((String) driver[4]);
				select.setDriver_group((String) driver[5]);
				select.setDriver_empnumber((String) driver[6]);
				select.setDriver_dlnumber((String) driver[7]);
				select.setDriver_badgenumber((String)driver[8]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public DriverDto getDriver_Header_Show_Attandance_perday_esi_Details(Integer driver_id, Integer companyId)
			throws Exception {

		Object[] driver = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_photoid, JT.driJobType, VG.vGroup, d.driver_empnumber,"
					+ " d.driver_perdaySalary, d.driver_esiamount, d.driver_pfamount, d.driverSalaryTypeId "
					+ " from Driver AS d "
					+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = d.vehicleGroupId "
					+ " INNER JOIN DriverJobType AS JT ON JT.driJobId = d.driJobId "
					+ " where d.driver_id = :id AND d.companyId = " + companyId + " AND d.markForDelete = 0");
			query.setParameter("id", driver_id);
			driver = (Object[]) query.getSingleResult();
			if (driver == null || driver.length == 0) {
				return null;
			}
			DriverDto select = new DriverDto();
			if (driver != null) {
				select.setDriver_id((Integer) driver[0]);
				select.setDriver_firstname((String) driver[1]);
				select.setDriver_Lastname((String) driver[2]);
				select.setDriver_photoid((Integer) driver[3]);
				select.setDriver_jobtitle((String) driver[4]);
				select.setDriver_group((String) driver[5]);
				select.setDriver_empnumber((String) driver[6]);
				select.setDriver_perdaySalary((Double) driver[7]);
				select.setDriver_esiamount((Double) driver[8]);
				select.setDriver_pfamount((Double) driver[9]);
				select.setDriverSalaryTypeId((short) driver[10]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public Driver getDriver_Details_Issues_entries(int driver_id) {
		Object[] driver = null;
		try {
			Query query = entityManager
					.createQuery("SELECT d.driver_id, d.driver_firstname from Driver AS d where d.driver_id = :id");
			query.setParameter("id", driver_id);
			driver = (Object[]) query.getSingleResult();
			if (driver == null || driver.length == 0) {
				return null;
			}
			Driver select = new Driver();
			if (driver != null) {
				select.setDriver_id((Integer) driver[0]);
				select.setDriver_firstname((String) driver[1]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public List<Driver> SearchOnlyCleanerNAME(String Search) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		TypedQuery<Object[]> queryt = null;
		List<Driver> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber , d.driver_fathername  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CLEANER' AND DJ.companyId = "
							+ userDetails.getCompany_id() + "" + " where (lower(d.driver_firstname) Like ('%" + Search
							+ "%') OR  lower(d.driver_empnumber) Like ('%" + Search + "%') OR lower(d.driver_mobnumber) Like ('%" + Search + "%')"
							+ " ) AND d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE+" AND d.companyId = " + userDetails.getCompany_id()
							+ "  AND d.markForDelete = 0",
					Object[].class);
		} else {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber , d.driver_fathername  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CLEANER' AND DJ.companyId = "
							+ userDetails.getCompany_id() + ""
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + "" + " where (lower(d.driver_firstname) Like ('%" + Search
							+ "%') OR  lower(d.driver_empnumber) Like ('%" + Search + "%') OR lower(d.driver_mobnumber) Like ('%" + Search + "%')"
							+ " ) AND d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE+" AND d.companyId = " + userDetails.getCompany_id()
							+ "  AND d.markForDelete = 0",
					Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Driver>();
			Driver dropdown = null;
			for (Object[] result : results) {
				dropdown = new Driver();

				dropdown.setDriver_id((Integer) result[0]);
				dropdown.setDriver_firstname((String) result[1]);
				dropdown.setDriver_Lastname((String) result[2]);
				dropdown.setDriver_empnumber((String) result[3]);
				if(result[4] != null) {
					dropdown.setDriver_fathername((String) result[4]);
				}else {
					dropdown.setDriver_fathername(" ");	
				}
				
				Dtos.add(dropdown);
			}
		}
		}
		return Dtos;
	}
	
	@Transactional
	@Override
	public List<Driver> searchByCleanerName(String Search, int companyId, long userId) throws Exception {

		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CLEANER' AND DJ.companyId = "
							+ companyId + "" + " where (lower(d.driver_firstname) Like ('%" + Search
							+ "%') OR  lower(d.driver_empnumber) Like ('%" + Search + "%') "
							+ " OR  lower(d.driver_mobnumber) Like ('%" + Search + "%') ) "
							+ " AND d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE+" AND d.companyId = " + companyId
							+ "  AND d.markForDelete = 0",
					Object[].class);
		} else {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
							+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='CLEANER' AND DJ.companyId = "
							+ companyId + ""
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId AND VGP.user_id = "
							+ userId + "" + " where (lower(d.driver_firstname) Like ('%" + Search
							+ "%') OR  lower(d.driver_empnumber) Like ('%" + Search + "%') "
							+ " OR  lower(d.driver_mobnumber) Like ('%" + Search + "%') ) "
							+ " AND d.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE+" AND d.companyId = " + companyId
							+ "  AND d.markForDelete = 0",
					Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<Driver> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Driver>();
			Driver dropdown = null;
			for (Object[] result : results) {
				dropdown = new Driver();

				dropdown.setDriver_id((Integer) result[0]);
				dropdown.setDriver_firstname((String) result[1]);
				dropdown.setDriver_Lastname((String) result[2]);
				dropdown.setDriver_empnumber((String) result[3]);

				Dtos.add(dropdown);
			}
		}
		return Dtos;
	}

	@Transactional
	public void deleteDriver( Long userId,Timestamp toDate,Integer driverId,Integer companyId) {
		driverDao.deleteDriver(userId,toDate, driverId, companyId );
	}

	/* Driver Service Close */

	/* Driver Reminder */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addDriverReminder(DriverReminder diverreminder) {
		 driverReminderRepository.save(diverreminder);

	}
	
	@Transactional
	public DriverReminder getPreviousDriverReminder(int driverId,Long driverRenewalTypeId,Integer companyId,String query) throws Exception {
		
		DriverReminder driverReminder = null;
		try {
			TypedQuery<DriverReminder> queryt = entityManager.createQuery(
					" select D From DriverReminder as D where D.driver_id = "+driverId+" AND D.driverRenewalTypeId = "+driverRenewalTypeId+"  AND D.companyId = "+companyId+" "+query+" AND D.markForDelete = 0 ORDER BY D.driver_remid DESC ",
							DriverReminder.class);
			try {
				queryt.setMaxResults(1);
				driverReminder =queryt.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
				return driverReminder;
		
	}
	
	
	@Transactional
	public DriverReminder getExistingDriverReminder(int driverId,Long driverRenewalTypeId, Date fromDate ,Date toDate ,Integer companyId ,String query) {
		
		DriverReminder driverReminder = null;
		try {
			TypedQuery<DriverReminder> queryt = entityManager.createQuery(
					"select D From DriverReminder as D where D.driver_id = "+driverId+" AND D.driverRenewalTypeId = "+driverRenewalTypeId+" AND"
							+ " (( '"+fromDate+"' BETWEEN D.driver_dlfrom AND D.driver_dlto ) OR ('"+toDate+"' BETWEEN D.driver_dlfrom AND D.driver_dlto ) OR (D.driver_dlfrom BETWEEN '"+fromDate+"' AND '"+toDate+"' ) OR ( D.driver_dlto BETWEEN '"+fromDate+"' AND '"+toDate+"') OR (D.driver_dlfrom  > '"+fromDate+"') ) "+query+"  AND D.companyId = "+companyId+" AND D.markForDelete = 0",
							DriverReminder.class);
			try {
				queryt.setMaxResults(1);
				driverReminder =queryt.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
				return driverReminder;
		
	}
	
	

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateDriverReminder(DriverReminder diverreminder) {
		driverReminderRepository.save(diverreminder);

	}

	@Transactional
	public void getDriverReminderList(Integer diverreminder) {

		driverReminderRepository.getDriverReminderList(diverreminder);
	}

	@Transactional
	public List<DriverReminderDto> listDriverReminder(int diverreminder, Integer companyId) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.driver_id, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom,"
						+ " R.driver_dlto, R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate, R.driver_content,R.driver_contentType "
						+ " From DriverReminder As R "
						+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId" + " where R.driver_id="
						+ diverreminder + " AND R.companyId = " + companyId + " AND R.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverReminderDto>();
			DriverReminderDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new DriverReminderDto();
				Documentto.setDriver_id((Integer) result[0]);
				Documentto.setDriver_remid((Integer) result[1]);
				Documentto.setDriver_remindertype((String) result[2]);
				Documentto.setDriver_dlnumber((String) result[3]);
				Documentto.setDriver_dlfrom((Date) result[4]);
				Documentto.setDriver_dlto((Date) result[5]);
				Documentto.setDriver_timethreshold((Integer) result[6]);
				Documentto.setDriver_periedthreshold((Integer) result[7]);
				Documentto.setDriver_renewaldate((String) result[8]);
				Documentto.setDriver_content((byte[])result[9]);
				Documentto.setDriver_contentType((String)result[10]);
				Dtos.add(Documentto);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<DriverReminderDto> listLatestDriverDLReminder(int driverId, Integer companyId) {
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.driver_id, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom,"
						+ " R.driver_dlto, R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate "
						+ " From DriverReminder As R "
						+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId" + " where R.driver_id="
						+ driverId + " AND R.companyId = " + companyId + " AND R.markForDelete = 0 order by R.driver_dlto DESC",
						Object[].class);
		List<Object[]> results = queryt.getResultList();
		
		List<DriverReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverReminderDto>();
			DriverReminderDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new DriverReminderDto();
				Documentto.setDriver_id((Integer) result[0]);
				Documentto.setDriver_remid((Integer) result[1]);
				Documentto.setDriver_remindertype((String) result[2]);
				Documentto.setDriver_dlnumber((String) result[3]);
				Documentto.setDriver_dlfrom((Date) result[4]);
				Documentto.setDriver_dlto((Date) result[5]);
				Documentto.setDriver_timethreshold((Integer) result[6]);
				Documentto.setDriver_periedthreshold((Integer) result[7]);
				Documentto.setDriver_renewaldate((String) result[8]);
				Dtos.add(Documentto);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<DriverReminderDto> Showlist_OF_Current_DriverReminder(int diverreminder, String Todate) {

		/*
		 * TypedQuery<DriverReminder> query = entityManager.createQuery(
		 * "from DriverReminder where driver_id=" + diverreminder +
		 * " AND driver_dlto >= '" + Todate + "'", DriverReminder.class); return
		 * query.getResultList();
		 */

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.driver_id, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom,"
						+ " R.driver_dlto, R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate From DriverReminder As R "
						+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId" + " where R.driver_id="
						+ diverreminder + " AND R.driver_dlto >= '" + Todate + "' AND R.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverReminderDto>();
			DriverReminderDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new DriverReminderDto();
				Documentto.setDriver_id((Integer) result[0]);
				Documentto.setDriver_remid((Integer) result[1]);
				Documentto.setDriver_remindertype((String) result[2]);
				Documentto.setDriver_dlnumber((String) result[3]);
				Documentto.setDriver_dlfrom((Date) result[4]);
				Documentto.setDriver_dlto((Date) result[5]);
				Documentto.setDriver_timethreshold((Integer) result[6]);
				Documentto.setDriver_periedthreshold((Integer) result[7]);
				Documentto.setDriver_renewaldate((String) result[8]);
				Dtos.add(Documentto);
			}
		}
		return Dtos;

	}

	@Transactional
	public DriverReminderDto getDriverReminder(int driver_remid, Integer companyId) {

		// return driverReminderRepository.getDriverReminder(driver_remid, companyId);

		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT R.driver_id, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom,"
						+ " R.driver_dlto, R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate"
						+ " , R.driver_contentType, R.driver_content, R.driver_filename, R.driverRenewalTypeId, R.createdById, R.created ,R.newDriverReminder ,R.renewalRecieptId " 
						+ " From DriverReminder As R "
						+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
						+ " where R.driver_remid=" + driver_remid + " AND R.companyId = " + companyId
						+ " AND R.markForDelete = 0", Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverReminderDto>();
			DriverReminderDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new DriverReminderDto();
				Documentto.setDriver_id((Integer) result[0]);
				Documentto.setDriver_remid((Integer) result[1]);
				Documentto.setDriver_remindertype((String) result[2]);
				Documentto.setDriver_dlnumber((String) result[3]);
				Documentto.setDriver_dlfrom((Date) result[4]);
				Documentto.setDriver_dlto((Date) result[5]);
				Documentto.setDriver_timethreshold((Integer) result[6]);
				Documentto.setDriver_periedthreshold((Integer) result[7]);
				Documentto.setDriver_renewaldate((String) result[8]);
				Documentto.setDriver_contentType((String) result[9]);
				Documentto.setDriver_content((byte[]) result[10]);
				Documentto.setDriver_filename((String) result[11]);
				Documentto.setDriverRenewalTypeId((Long) result[12]);
				Documentto.setCreatedById((Long) result[13]);
				Documentto.setCreated((Date) result[14]);
				Documentto.setNewDriverReminder((boolean) result[15]);
				Documentto.setRenewalRecieptId((Long) result[16]);
				Dtos.add(Documentto);
			}
		}
		return Dtos.get(0);

	}

	@Transactional
	public void deleteDriverReminder(Integer diverreminder, Integer companyId) {
		driverReminderRepository.deleteDriverReminder(diverreminder, companyId);

	}
	
	@Transactional
	public void updateOldDriverReminder(Integer diverreminder, Integer companyId) {
		driverReminderRepository.updateOldDriverReminder(diverreminder, companyId);

	}

	@Transactional
	public Long getReminderCount(int driver_remid) {

		// return driverReminderRepository.getReminderCount(driver_remid);

		TypedQuery<Long> query = entityManager.createQuery(
				"SELECT COUNT(d) FROM DriverReminder d where d.driver_id=" + driver_remid + "", Long.class);
		Long countryCount = query.getSingleResult();
		return countryCount;
	}

	@Transactional
	public List<DriverReminder> listDriverReminder() {

		return driverReminderRepository.findAll();
	}

	/* Driver Reminder Close */

	/* Driver Document */
	@Transactional
	public void saveDriverDocument(DriverDocument driverDocument) {

		driverDocumentRepository.save(driverDocument);
	}

	@Transactional
	public void updateDriverDocument(DriverDocument driverDocument) {

		driverDocumentRepository.save(driverDocument);

	}

	@Transactional
	public List<DriverDocumentDto> listDriverDocument(int diverreminder, Integer companyId) {

		// return driverDocumentRepository.listDriverDocument(diverreminder);

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT D.driver_id, D.driver_documentid, D.driver_documentname, D.driver_filename FROM DriverDocument As D where D.driver_id="
						+ diverreminder + " AND D.companyId = " + companyId + " AND D.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverDocumentDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverDocumentDto>();
			DriverDocumentDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new DriverDocumentDto();
				Documentto.setDriver_id((Integer) result[0]);
				Documentto.setDriver_documentid((Integer) result[1]);
				Documentto.setDriver_documentname((String) result[2]);
				Documentto.setDriver_filename((String) result[3]);

				Dtos.add(Documentto);
			}
		}
		return Dtos;
	}

	@Transactional
	public DriverDocumentDto getDriverDocuemnt(int driver_docid, Integer companyId) {

		Query query = entityManager.createQuery(
				" SELECT DD.driver_documentid, DD.driver_documentname, DD.driver_docFromDate, DD.driver_docToDate,"
				+ " DD.uploaddate, DD.revdate, DD.driver_filename, DD.driver_id, DD.driver_content, DD.driver_contentType,"
				+ " U.email, DD.createdById "
				+ " FROM DriverDocument DD "
				+ " LEFT JOIN User U ON U.id = DD.createdById"
				+ " WHERE DD.driver_documentid= "+driver_docid+" AND DD.companyId = "+companyId+" AND DD.markForDelete = 0");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		DriverDocumentDto select;
		if (result != null) {
			select = new DriverDocumentDto();

			select.setDriver_documentid((Integer) result[0]);
			select.setDriver_documentname((String) result[1]);
			select.setDriver_docFromDate((Date) result[2]);
			if(result[2] != null)
				select.setDriver_docFrom(CreatedDateTime.format(result[2]));
			select.setDriver_docToDate((Date) result[3]);
			if(result[3] != null)
				select.setDriver_docTo(CreatedDateTime.format(result[3]));
			select.setUploaddate((Date) result[4]);
			if(result[4] != null)
				select.setDriver_uploaddate(CreatedDateTime.format(result[4]));
			select.setRevdate((Date) result[5]);
			if(result[5] != null)
				select.setDriver_revdate(CreatedDateTime.format(result[5]));
			select.setDriver_filename((String) result[6]);
			select.setDriver_id((Integer) result[7]);
			select.setDriver_content((byte[]) result[8]);
			select.setDriver_contentType((String) result[9]);
			select.setCreatedBy((String) result[10]);
			select.setCreatedById((Long) result[11]);

		} else {
			return null;
		}

		return select;
	
	}

	@Transactional
	public void deleteDriverDocument(int driver_documentid, Integer companyId) {

		driverDocumentRepository.deleteDriverDocument(driver_documentid, companyId);
	}

	@Transactional
	public Long getDocuemntCount(int driver_remid) {

		// return driverDocumentRepository.getDocumentCount(driver_remid);

		TypedQuery<Long> query = entityManager.createQuery(
				"SELECT COUNT(d) FROM DriverDocument d where d.driver_id=" + driver_remid + "", Long.class);
		Long countryCount = query.getSingleResult();
		return countryCount;
	}

	/* Driver Document History */

	@Transactional
	public void saveDriverDocumentHistory(DriverDocumentHistory driverDocumentHistory) {

		driverDocumentHistoryRepository.save(driverDocumentHistory);
	}

	@Transactional
	public void updateDriverDocumentHistory(DriverDocumentHistory driverDocumentHistory) {

		driverDocumentHistoryRepository.save(driverDocumentHistory);
	}

	@Transactional
	public List<DriverDocumentHistoryDto> listDriverDocHis(int diverDocHis_id, Integer companyId) {
		//return driverDocumentHistoryRepository.listDriverDocHis(diverDocHis_id, companyId);

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT DH.driver_doHisid, DH.driver_docHisname, DH.driver_docHisFromDate, DH.driver_docHisToDate, DH.uploaddate,"
				+ " DH.driver_filename, DH.driver_id, DH.driver_content, DH.driver_contentType, U.email, DH.createdById "
				+ " FROM DriverDocumentHistory DH "
				+ " LEFT JOIN User U ON U.id = DH.createdById"
				+ " WHERE DH.driver_id= "+diverDocHis_id+" AND DH.companyId = "+companyId+" AND DH.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverDocumentHistoryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverDocumentHistoryDto>();
			DriverDocumentHistoryDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new DriverDocumentHistoryDto();
				
				Documentto.setDriver_doHisid((Integer) result[0]);
				Documentto.setDriver_docHisname((String) result[1]);
				Documentto.setDriver_docHisFromDate((Date) result[2]);
				if(result[2] != null)
					Documentto.setDriver_docHisFrom(CreatedDateTime.format(result[2]));
				Documentto.setDriver_docHisToDate((Date) result[3]);
				if(result[3] != null)
					Documentto.setDriver_docHisTo(CreatedDateTime.format(result[3]));
				Documentto.setUploaddate((Date) result[4]);
				if(result[4] != null)
					Documentto.setDriver_uploaddate(CreatedDateTime.format(result[4]));
				Documentto.setDriver_filename((String) result[5]);
				Documentto.setDriver_id((Integer) result[6]);
				//Documentto.setDriver_content((Blob) result[7]);
				Documentto.setDriver_contentType((String) result[8]);
				Documentto.setCreatedBy((String) result[9]);
				Documentto.setCreatedById((Long) result[10]);
				
				Dtos.add(Documentto);
			}
		}
		return Dtos;
		
	}

	@Transactional
	public DriverDocumentHistory getDriverDocHis(int diverDocHis_id, Integer companyId) {

		return driverDocumentHistoryRepository.getDriverDocHis(diverDocHis_id, companyId);
	}

	@Transactional
	public void deleteDriverDocHis(int driver_doHistid, Integer companyId) {

		driverDocumentHistoryRepository.deleteDriverDocHis(driver_doHistid, companyId);
	}

	/* Driver Comment History */

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addDriverComment(DriverComment diverComment) {

		driverCommentRepository.save(diverComment);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateDriverComment(DriverComment diverComment) {

		driverCommentRepository.save(diverComment);
	}

	@Transactional
	public List<DriverCommentDto> listDriverComment(int diverComment, Integer companyId) {
		//return driverCommentRepository.listDriverComment(diverComment, companyId);
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT R.driver_commentid, R.driver_title, R.driver_id, R.driver_comment, U.email, R.createdById, R.created "
				+ " FROM DriverComment AS R "
				+ " INNER JOIN Driver D ON D.driver_id = R.driver_id"
				+ " LEFT JOIN User U ON U.id = R.createdById"
				+ " WHERE R.driver_id= "+diverComment+" AND R.companyId = "+companyId+" AND R.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverCommentDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverCommentDto>();
			DriverCommentDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new DriverCommentDto();
				
				Documentto.setDriver_commentid((Long) result[0]);
				Documentto.setDriver_title((String) result[1]);
				Documentto.setDriver_id((Integer) result[2]);
				Documentto.setDriver_comment((String) result[3]);
				Documentto.setCreatedBy((String) result[4]);
				Documentto.setCreatedById((Long) result[5]);
				if(result[6] != null)
					Documentto.setCreationDate(CreatedDateTime.format(result[6]));
				
				Dtos.add(Documentto);
			}
		}
		return Dtos;
	
	}

	@Transactional
	public DriverComment getDriverComment(Long driver_commentid) {

		return driverCommentRepository.getDriverComment(driver_commentid);
	}

	@Transactional
	public void deleteDriverComment(Long driver_commentid, Integer companyId) {
		driverCommentRepository.deleteDriverComment(driver_commentid, companyId);
	}

	@Transactional
	public Long getCommentCount(int driver_remid) {
		TypedQuery<Long> query = entityManager
				.createQuery("SELECT COUNT(d) FROM DriverComment d where d.driver_id=" + driver_remid + "", Long.class);
		Long countryCount = query.getSingleResult();
		return countryCount;
	}

	/* Driver Photo */

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addDriverPhoto(org.fleetopgroup.persistence.document.DriverPhoto diverPhoto) {
		diverPhoto.set_id((int) sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_DRIVER_PHOTO));
		mongoTemplate.save(diverPhoto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateDriverPhoto(org.fleetopgroup.persistence.document.DriverPhoto diverPhoto) {
		mongoTemplate.save(diverPhoto);
	}

	@Transactional
	@PreAuthorize("hasRole('ADDEDIT_DRIVER_PHOTO')")
	public List<DriverPhoto> listDriverPhoto(int diverPhoto, Integer companyId) {
		//return driverPhotoRepository.listDriverPhoto(diverPhoto, companyId);
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("driver_id").is(diverPhoto).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.DriverPhoto.class);
	}
	@Override
	public org.fleetopgroup.persistence.document.DriverPhoto getDriverPhoto(int driver_photoid) throws Exception {
		CustomUserDetails	customUserDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(driver_photoid).and("companyId").is(customUserDetails.getCompany_id()).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.DriverPhoto.class);
	}
	
	@Transactional
	public DriverPhoto getDriverPhoto(int driver_photoid, Integer companyId) {
		//return driverPhotoRepository.getDriverPhoto(driver_photoid, customUserDetails.getCompany_id());
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(driver_photoid).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.DriverPhoto.class);
	}

	@Transactional
	public void deleteDriverPhoto(int driver_Photoid, Integer companyId) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query(Criteria.where("_id").is(driver_Photoid).and("companyId").is(companyId));
		Update update = new Update();
		update.set("markForDelete", true);
		mongoTemplate.updateFirst(query, update, org.fleetopgroup.persistence.document.DriverPhoto.class);
	}

	@Transactional
	public void setprofilePhoto(int Driverphoto_id, int driver_id) {
		driverDao.setprofilePhoto(Driverphoto_id, driver_id);
	}

	@Transactional
	public Long getPhotoCount(int driver_remid) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("driver_id").is(driver_remid).and("markForDelete").is(false));
		return mongoTemplate.count(query, org.fleetopgroup.persistence.document.DriverPhoto.class);
	}

	/* Reminder History */
	@Transactional
	public List<DriverReminderHistoryDto> listDriverReminderHis(int diverRemHis_id, Integer companyId) {
		//return driverReminderHistoryRepository.listDriverReminderHis(diverRemHis_id, companyId);
		
		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT R.driver_rhid, R.driver_id, R.driver_rhtypeId, R.driver_rhnumber,"
						+ " R.driver_rhfromDate, R.driver_rhtoDate, R.driver_filename, DDT.dri_DocType "
						+ " FROM DriverReminderHistory AS R"
						+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driver_rhtypeId"
						+ " where R.driver_id= "+diverRemHis_id+" AND R.companyId = " + companyId
						+ " AND R.markForDelete = 0", Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverReminderHistoryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverReminderHistoryDto>();
			DriverReminderHistoryDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new DriverReminderHistoryDto();
				
				Documentto.setDriver_rhid((int) result[0]);
				Documentto.setDriver_id((Integer) result[1]);
				Documentto.setDriver_rhtypeId((Long) result[2]);
				Documentto.setDriver_rhnumber((String) result[3]);
				if(result[4] != null)
					Documentto.setDriver_rhfrom(CreatedDateTime.format(result[4]));
				if(result[5] != null)
				 Documentto.setDriver_rhto(CreatedDateTime.format(result[5]));
				Documentto.setDriver_filename((String) result[6]);
				Documentto.setDriver_rhtype((String) result[7]);

				Dtos.add(Documentto);
			}
		}
		return Dtos;

	
	}

	@Transactional
	public DriverReminderHistory getDriverReminderHis(int driver_RemHisid, Integer companyId) {

		return driverReminderHistoryRepository.getDriverReminderHis(driver_RemHisid, companyId);
	}

	@Transactional
	public void deleteDriverReminderHis(int driver_RemHistid, Integer companyId) {

		driverReminderHistoryRepository.deleteDriverReminderHis(driver_RemHistid, companyId);
	}

	@Transactional
	public void saveDriverReminderHistory(DriverReminderHistory driverReminderHistory) {

		driverReminderHistoryRepository.save(driverReminderHistory);
	}

	@Transactional
	public void updateDriverReminderHistory(DriverReminderHistory driverReminderHistory) {

		driverReminderHistoryRepository.save(driverReminderHistory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IDriverService#countToDay_DL_Renewal( )
	 */
	@Transactional
	public Long countToDay_DL_Renewal() throws Exception {

		java.util.Date currentDate = new java.util.Date();
		DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");

		TypedQuery<Long> query = entityManager.createQuery(
				"SELECT COUNT(d) FROM DriverReminder d where d.driver_dlto='" + ft.format(currentDate) + "'",
				Long.class);
		Long countryCount = query.getSingleResult();
		return countryCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverService#
	 * listof_ToDay_DL_Renewal()
	 */
	@Transactional
	public List<DriverReminderDto> listof_ToDay_DL_Renewal(String currentdate) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
								+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate FROM  DriverReminder AS R, Driver AS d "
								+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " Where R.driver_dlto ='" + currentdate
								+ "' AND d.driver_id= R.driver_id and d.companyId = " + userDetails.getCompany_id()
								+ " and d.markForDelete = 0 ORDER BY R.driver_remid desc ",
						Object[].class);
			} else {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
								+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate FROM  DriverReminder AS R, Driver AS d "
								+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " INNER JOIN VehicleGroupPermision  VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id = "
								+ userDetails.getId() + "" + "Where R.driver_dlto ='" + currentdate
								+ "' AND d.driver_id= R.driver_id and d.companyId = " + userDetails.getCompany_id()
								+ " and d.markForDelete = 0 ORDER BY R.driver_remid desc ",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			List<DriverReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverReminderDto>();
				DriverReminderDto list = null;
				for (Object[] result : results) {
					list = new DriverReminderDto();

					list.setDriver_id((Integer) result[0]);
					list.setDriver_firstname((String) result[1]);
					list.setDriver_Lastname((String) result[2]);
					list.setDriver_empnumber((String) result[3]);
					list.setDriver_remid((Integer) result[4]);
					list.setDriver_remindertype((String) result[5]);
					list.setDriver_dlnumber((String) result[6]);
					list.setDriver_dlfrom((Date) result[7]);
					list.setDriver_dlto((Date) result[8]);
					list.setDriver_timethreshold((Integer) result[9]);
					list.setDriver_periedthreshold((Integer) result[10]);
					list.setDriver_renewaldate((String) result[11]);
					//list.setDriver_renewaldate((String) result[12]);

					Dtos.add(list);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverService#
	 * countTotalDriver_AND_TodayRenewal()
	 */
	@Transactional
	public Object[] countTotalDriver_AND_TodayRenewal() throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		java.util.Date currentDate = new java.util.Date();
		DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
		/*
		 * .createQuery("SELECT COUNT(co.driver_id),(SELECT COUNT(d) FROM DriverReminder d where d.driver_dlto='"
		 * + ft.format(currentDate) + "' ) " + " FROM Driver As co " +
		 * " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = co.vehicleGroupId AND VGP.vg_per_id = "
		 * +userDetails.getId()+"" + " and co.companyId = "+userDetails.getCompany_id()
		 * +" and co.markForDelete = 0");
		 */
		Query queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery("Select count(D.driver_id), count(DR.driver_id) from DriverReminder DR "
					+ " RIGHT JOIN Driver D ON D.driver_id = DR.driver_id and DR.driver_dlto = '"
					+ ft.format(currentDate) + "'" + " where D.companyId = " + userDetails.getCompany_id()
					+ " and D.markForDelete = 0");
		} else {
			queryt = entityManager.createQuery("Select count(D.driver_id), count(DR.driver_id) from DriverReminder DR "
					+ " RIGHT JOIN Driver D ON D.driver_id = DR.driver_id and DR.driver_dlto = '"
					+ ft.format(currentDate) + "'"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = D.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " where D.companyId = " + userDetails.getCompany_id()
					+ " and D.markForDelete = 0");
		}

		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverService#
	 * countTotal_REMINDER_DOC_COMMENT__AND_PHOTO()
	 */
	@Transactional
	public Object[] countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(Integer Driver_ID) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Query queryt = entityManager.createQuery(
				"SELECT COUNT(co),"
						+ "(SELECT  COUNT(com) FROM DriverComment AS com where  com.driver_id =:id AND com.companyId = :companyId AND com.markForDelete = 0),"
						+ "(SELECT  COUNT(pho) FROM DriverPhoto AS pho where  pho.driver_id =:id AND pho.companyId = :companyId AND pho.markForDelete = 0) "
						+ " FROM DriverReminder As co WHERE co.driver_id=:id AND co.companyId = :companyId AND co.markForDelete = 0 ");
		queryt.setParameter("id", Driver_ID);
		queryt.setParameter("companyId", userDetails.getCompany_id());
		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverService#
	 * countTotal_REMINDER_DOC_COMMENT__AND_PHOTO()
	 */
	@Transactional
	public Object[] countTotal_LAST_COMMENT_ISSUES_FUELMILEAGE_REPORT(Integer Driver_ID, String fromdate, String Todate,
			Integer companyId) throws Exception {
		Query queryt = entityManager.createQuery(
				"SELECT COUNT(co),(SELECT  AVG(f.fuel_kml) FROM Fuel AS f where  f.driver_id =:id AND f.fuel_tank_partial=0 AND f.fuel_date BETWEEN '"
						+ fromdate + "' AND '" + Todate + "' AND f.companyId = " + companyId
						+ " AND f.markForDelete = 0),"
						+ "(SELECT  COUNT(i) FROM Issues AS i where i.ISSUES_DRIVER_ID =:id AND i.ISSUES_REPORTED_DATE BETWEEN '"
						+ fromdate + "' AND '" + Todate + "' AND i.COMPANY_ID = " + companyId
						+ " AND i.markForDelete = 0) "
						+ " FROM DriverComment As co WHERE co.driver_id=:id AND co.created BETWEEN '" + fromdate
						+ " 00:00:00' AND '" + Todate + " 23:59:00' AND co.companyId = " + companyId
						+ " AND co.markForDelete = 0");
		queryt.setParameter("id", Driver_ID);
		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverService#Update_Driver_Status(
	 * java.lang.String, int)
	 */
	@Transactional
	public void Update_Driver_Status(short DriveRStatus_ID, int tripFristDriverID) {
		// Note : Driver ACTIVE to TripSheet
		driverDao.Update_Driver_Status(DriveRStatus_ID, tripFristDriverID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverService#
	 * Update_Driver_Status_TripSheetID(java.lang.String, java.lang.Long, int)
	 */
	@Transactional
	public void Update_Driver_Status_TripSheetID(short DriveRStatus_ID, Long tripSheetID, int tripFristDriverID, int compId) {
		// Note : this Update Status and TripSheet ID
		driverDao.Update_Driver_Status_TripSheetID(DriveRStatus_ID, tripSheetID, tripFristDriverID,
				compId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverService#
	 * Get_Driver_Current_Status(int)
	 */
	@Transactional
	public DriverDto Get_Driver_Current_Status_TripSheetID(int tripFristDriverID, Integer companyId) throws Exception {
		// Note Driver Details
		Object[] driver = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driverStatusId, d.tripSheetID from Driver AS d where d.driver_id = :id "
							+ " and d.companyId = " + companyId + " and d.markForDelete = 0");
			query.setParameter("id", tripFristDriverID);
			driver = (Object[]) query.getSingleResult();
			if (driver == null || driver.length == 0) {
				return null;
			}
			DriverDto select = new DriverDto();
			if (driver != null) {
				select.setDriver_id((Integer) driver[0]);
				select.setDriver_firstname((String) driver[1]);
				select.setDriverStatusId((short) driver[2]);
				select.setTripSheetID((Long) driver[3]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverService#
	 * List_DriverComment_Remarks_Report(java.lang.String)
	 */
	@Transactional
	public List<DriverCommentDto> List_DriverComment_Remarks_Report(String query, Integer companyId) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
	/*			"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_commentid, R.driver_title, R.createdBy, R.created FROM  DriverComment AS R, Driver AS d "
						+ "Where R.markForDelete = 0 AND R.companyId = " + companyId + "" + query
						+ "ORDER BY R.driver_commentid desc ",*/
						
						" SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_commentid, R.driver_title,  R.driver_comment, R.createdBy, R.created ,d.driver_fathername" 
						+" FROM Driver AS d" 
				        +" LEFT JOIN  DriverComment R on R.driver_id=d.driver_id"
						+" Where R.markForDelete = 0 AND R.companyId = " + companyId + "" + query
						+" ORDER BY R.driver_commentid desc",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverCommentDto> dtos = null;
		if (results != null && !results.isEmpty()) {
			dtos = new ArrayList<>();
			DriverCommentDto list = null;
			for (Object[] result : results) {
				list = new DriverCommentDto();

				list.setDriver_id((Integer) result[0]);
				list.setDriver_firstname((String) result[1]);
				list.setDriver_Lastname((String) result[2]);
				list.setDriver_empnumber((String) result[3]);
				list.setDriver_commentid((Long) result[4]);
				list.setDriver_title((String) result[5]);
				list.setDriver_comment((String) result[6]);
				list.setCreatedBy((String) result[7]);
				list.setCreated((Date) result[8]);
			
				if(list.getDriver_firstname() != null && !list.getDriver_firstname().trim().equals(""))
					list.setDriverFullName(list.getDriver_firstname());
				if(list.getDriver_Lastname() != null && !list.getDriver_Lastname().trim().equals(""))
					list.setDriverFullName(list.getDriver_Lastname());
					
				if( result[8] != null &&  !((String)result[8]).trim().equals("")) {
					list.setDriverFullName("- "+result[8]);
					list.setDriverFatherName("- "+result[8]);
				}
				dtos.add(list);
			}
		}
		return dtos;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverService#
	 * DRIVER_RENEWALREMINDER_DL_REPORT(java.lang.String)
	 */
	@Transactional
	public List<DriverReminderDto> DRIVER_RENEWALREMINDER_DL_REPORT(String dateRangeFrom, String dateRangeTo,
			CustomUserDetails userDetails) throws Exception {
		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
							+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate, R.driver_revdate,d.driver_fathername FROM  DriverReminder AS R "
							+ " INNER JOIN Driver AS d ON d.driver_id = R.driver_id "
							+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
							+ " Where R.driver_dlto between '" + dateRangeFrom + "' AND '"
							+ dateRangeTo + "' AND R.companyId = " + userDetails.getCompany_id()
							+ " AND R.markForDelete = 0 " + "  ORDER BY R.driver_remid desc ",
					Object[].class);

		} else {

			queryt = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
							+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate, R.driver_revdate,d.driver_fathername FROM  DriverReminder AS R "
							+ " INNER JOIN Driver AS d ON d.driver_id = R.driver_id "
							+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + " " + " Where R.driver_dlto between '" + dateRangeFrom + "' AND '"
							+ dateRangeTo + "' AND R.companyId = " + userDetails.getCompany_id()
							+ " AND R.markForDelete = 0 " + "  ORDER BY R.driver_remid desc ",
					Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<DriverReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverReminderDto>();
			DriverReminderDto list = null;
			for (Object[] result : results) {
				list = new DriverReminderDto();

				list.setDriver_id((Integer) result[0]);
				list.setDriver_firstname((String) result[1]);
				list.setDriver_Lastname((String) result[2]);
				list.setDriver_empnumber((String) result[3]);
				list.setDriver_remid((Integer) result[4]);
				list.setDriver_remindertype((String) result[5]);
				list.setDriver_dlnumber((String) result[6]);
				list.setDriver_dlfrom((Date) result[7]);
				list.setDriver_dlto((Date) result[8]);
				list.setDriver_timethreshold((Integer) result[9]);
				list.setDriver_periedthreshold((Integer) result[10]);
				list.setDriver_renewaldate((String) result[11]);
				list.setDriver_renewaldate((String) result[12]);
				if(result[13] != null && !((String) result[13]).trim().equals(""))
				list.setDriverFatherName("- "+result[13]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Override
	public List<DriverDto> listDriver(String Job_Type) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			/* this only Select column */
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
								+ " R.driver_mobnumber, VG.vGroup, JT.driJobType, R.driverStatusId, R.tripSheetID FROM Driver as R "
								+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId "
								+ " INNER JOIN DriverJobType AS JT ON JT.driJobId = R.driJobId "
								+ " WHERE R. driver_jobtitle='" + Job_Type + "' and R.companyId = "
								+ userDetails.getCompany_id() + " and R.markForDelete = 0 ORDER BY R.driver_id desc",
						Object[].class);
			} else {
				queryt = entityManager.createQuery(
						"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname, R.driver_dlnumber, R.driver_badgenumber,"
								+ " R.driver_mobnumber, VG.vGroup, JT.driJobType, R.driverStatusId, R.tripSheetID FROM Driver as R "
								+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId "
								+ " INNER JOIN DriverJobType AS JT ON JT.driJobId = R.driJobId "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
								+ userDetails.getId() + "" + " WHERE R. driver_jobtitle='" + Job_Type
								+ "' and R.companyId = " + userDetails.getCompany_id()
								+ " and R.markForDelete = 0 ORDER BY R.driver_id desc",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			List<DriverDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverDto>();
				DriverDto list = null;
				for (Object[] result : results) {
					list = new DriverDto();

					list.setDriver_id((Integer) result[0]);
					list.setDriver_empnumber((String) result[1]);
					list.setDriver_firstname((String) result[2]);
					list.setDriver_Lastname((String) result[3]);
					list.setDriver_dlnumber((String) result[4]);
					list.setDriver_badgenumber((String) result[5]);
					list.setDriver_mobnumber((String) result[6]);
					list.setDriver_group((String) result[7]);
					list.setDriver_jobtitle((String) result[8]);
					list.setDriver_active(DriverStatus.getDriverStatus((short) result[9]));
					list.setTripSheetID((Long) result[10]);

					Dtos.add(list);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverService#
	 * Get_Driver_Current_Status(int)
	 */
	@Transactional
	public DriverDto Get_Driver_Current_Status_TripSheetID(int tripFristDriverID) {
		// Note Driver Details
		Object[] driver = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT d.driver_id, d.driver_firstname, d.driverStatusId, d.tripSheetID from Driver AS d"

							+ " where d.driver_id = :id");
			query.setParameter("id", tripFristDriverID);
			driver = (Object[]) query.getSingleResult();
			if (driver == null || driver.length == 0) {
				return null;
			}
			DriverDto select = new DriverDto();
			if (driver != null) {
				select.setDriver_id((Integer) driver[0]);
				select.setDriver_firstname((String) driver[1]);
				select.setDriver_active(DriverStatus.getDriverStatus((short) driver[2]));
				select.setTripSheetID((Long) driver[3]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Driver> SearchOnlyDriverNAME(String Search, CustomUserDetails userDetails) throws Exception {
		try {
			List<Driver> Dtos = null;
			TypedQuery<Object[]> queryt = null;
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
								+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='DRIVER' AND DJ.companyId = "
								+ userDetails.getCompany_id() + ""

								+ " where d.companyId = " + userDetails.getCompany_id()
								+ " AND d.markForDelete = 0 AND ( lower(d.driver_firstname) Like ('%" + Search
								+ "%')  OR  lower(d.driver_empnumber) Like ('%" + Search + "%') OR  lower(d.driver_mobnumber) Like ('%" + Search + "%') )",
						Object[].class);
			} else {

				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber  FROM Driver AS d "
								+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='DRIVER' AND DJ.companyId = "
								+ userDetails.getCompany_id() + ""

								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id ="
								+ userDetails.getId() + "" + " where d.companyId = " + userDetails.getCompany_id()
								+ " AND d.markForDelete = 0 AND ( lower(d.driver_firstname) Like ('%" + Search
								+ "%')  OR  lower(d.driver_empnumber) Like ('%" + Search + "%') OR  lower(d.driver_mobnumber) Like ('%" + Search + "%') )",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Driver>();
				Driver dropdown = null;
				for (Object[] result : results) {
					dropdown = new Driver();

					dropdown.setDriver_id((Integer) result[0]);
					dropdown.setDriver_firstname((String) result[1]);
					dropdown.setDriver_Lastname((String) result[2]);
					dropdown.setDriver_empnumber((String) result[3]);

					Dtos.add(dropdown);
				}
			}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}
	
	@Override
	public List<Driver> getDriverDetailsNotInSuspend(String Search, CustomUserDetails userDetails) throws Exception {
		try {
			List<Driver> Dtos = null;
			TypedQuery<Object[]> queryt = null;
			List<Object[]> results = null;
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
				if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
						PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
					queryt = entityManager.createQuery(
							"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber,d.driver_fathername  FROM Driver AS d "
									+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='DRIVER' AND DJ.companyId = "
									+ userDetails.getCompany_id() + ""
									+ " where d.companyId = " + userDetails.getCompany_id()
									+ " AND d.driverStatusId <> "+DriverStatus.DRIVER_STATUS_SUSPEND+" AND d.markForDelete = 0 AND ( lower(d.driver_firstname) Like ('%" + Search
									+ "%')  OR  lower(d.driver_empnumber) Like ('%" + Search + "%') OR  lower(d.driver_mobnumber) Like ('%" + Search + "%') )",
									Object[].class);
				} else {

					queryt = entityManager.createQuery(
							"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber,d.driver_fathername  FROM Driver AS d "
									+ " INNER JOIN DriverJobType DJ ON DJ.driJobId = d.driJobId and DJ.driJobType ='DRIVER' AND DJ.companyId = "
									+ userDetails.getCompany_id() + ""
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id ="
									+ userDetails.getId() + "" + " where d.companyId = " + userDetails.getCompany_id()
									+ " AND d.driverStatusId <> "+DriverStatus.DRIVER_STATUS_SUSPEND+" AND d.markForDelete = 0 AND ( lower(d.driver_firstname) Like ('%" + Search
									+ "%')  OR  lower(d.driver_empnumber) Like ('%" + Search + "%') OR  lower(d.driver_mobnumber) Like ('%" + Search + "%') )",
									Object[].class);
				}
				results = queryt.getResultList();
			

			
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Driver>();
				Driver dropdown = null;
				for (Object[] result : results) {
					dropdown = new Driver();

					dropdown.setDriver_id((Integer) result[0]);
					dropdown.setDriver_firstname((String) result[1]);
					if(result[2] != null) {
						dropdown.setDriver_Lastname((String) result[2]);
					}else {
						dropdown.setDriver_Lastname(" ");
					}

					dropdown.setDriver_empnumber((String) result[3]);
					if(result[4] != null) {
						dropdown.setDriver_fathername((String) result[4]);
					}else {
						dropdown.setDriver_fathername(" ");
					}
					Dtos.add(dropdown);
				}
			}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Transactional
	public DriverDto getDriver_Header_Show_Details_ESI_PF(Integer driver_id) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Object[] driver = null;
		try {
			Query query = entityManager
					.createQuery("SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_photoid,"
							+ " JT.driJobType, VG.vGroup, d.driver_empnumber, d.driver_pfno, d.driver_esino from Driver AS d "
							+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = d.vehicleGroupId "
							+ " INNER JOIN DriverJobType AS JT ON JT.driJobId = d.driJobId "
							+ "where d.driver_id = :id AND d.companyId = " + userDetails.getCompany_id()
							+ " AND d.markForDelete = 0 ");
			query.setParameter("id", driver_id);
			driver = (Object[]) query.getSingleResult();

			if (driver == null || driver.length == 0) {
				return null;
			}
			DriverDto select = new DriverDto();
			if (driver != null) {
				select.setDriver_id((Integer) driver[0]);
				select.setDriver_firstname((String) driver[1]);
				select.setDriver_Lastname((String) driver[2]);
				select.setDriver_photoid((Integer) driver[3]);
				select.setDriver_jobtitle((String) driver[4]);
				select.setDriver_group((String) driver[5]);
				select.setDriver_empnumber((String) driver[6]);
				select.setDriver_pfno((String) driver[7]);
				select.setDriver_esino((String) driver[8]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long getCurrentTripSheetNumber(long tripSheetId, Integer companyId) throws Exception {
		Long tripsheetNUmber = null;
		try {
			HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(companyId,
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			Query query = null;
			if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				query = entityManager
						.createQuery("SELECT  TS.tripSheetNumber " + "from TripSheet AS TS where TS.tripSheetID = "
								+ tripSheetId + " AND TS.companyId = " + companyId + " AND TS.markForDelete = 0 ");
			} else if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				query = entityManager.createQuery(
						"SELECT  TS.TRIPCOLLNUMBER " + "from TripCollectionSheet AS TS where TS.TRIPCOLLID = "
								+ tripSheetId + " AND TS.COMPANY_ID = " + companyId + " AND TS.markForDelete = 0 ");

			} else if ((int) configuration.get(
					TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				query = entityManager
						.createQuery("SELECT  TS.TRIPDAILYNUMBER " + "from TripDailySheet AS TS where TS.TRIPDAILYID = "
								+ tripSheetId + " AND TS.COMPANY_ID = " + companyId + " AND TS.markForDelete = 0 ");

			}
			try {
				tripsheetNUmber =  (Long) query.getSingleResult();
			} catch (Exception e) {
				// do nothing
			}
			
			return tripsheetNUmber;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
	}

	@Override
	@Transactional
	public void Update_DriverHalt_Status_To_Active(String UpdateDate) throws Exception {

		entityManager.createNativeQuery(
				" UPDATE Driver As D INNER JOIN DriverHalt AS DH ON DH.DRIVERID = D.driver_id AND DH.HALT_DATE_TO < '"
						+ UpdateDate + "' AND DH.markForDelete = 0  SET D.driverStatusId=" + DriverStatus.DRIVER_STATUS_ACTIVE
						+ " where D.driverStatusId=" + DriverStatus.DRIVER_STATUS_LOCAL_HALT + " AND D.markForDelete = 0")
				.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateDriverSalaryByGroupId(DriverDto driverDto, Integer companyId) throws Exception {
		entityManager.createNativeQuery(
				" UPDATE Driver D SET D.driver_perdaySalary = "+driverDto.getDriver_perdaySalary()+"  where D.vehicleGroupId=" +driverDto.getVehicleGroupId() + ""
						+ " AND D.companyId = "+ companyId +" AND D.driJobId = "+driverDto.getDriJobId()+" AND D.markForDelete = 0")
				.executeUpdate();
		
	}
	
	@Override
	public ValueObject getDriverCommentDetails(ValueObject valueInObeject) throws Exception {
		List<DriverCommentDto>			commentList				= null;
		DriverCommentDto				driverCommentDto		= null;
		CustomUserDetails				userDetails				= null;
		ValueObject						valueObject				= null;
		String 							dateRange				= null;
		
		
		try {
			valueObject	= new ValueObject();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!valueInObeject.containsKey("dateRange") || !valueInObeject.containsKey("vehicleGroupId")) {
				return null;
			}
			dateRange	= valueInObeject.getString("dateRange");
			
			
			String[] From_TO_DateRange = null;

			From_TO_DateRange = dateRange.split(" to ");

			driverCommentDto	= new DriverCommentDto();
			driverCommentDto.setDriver_id(valueInObeject.getInt("driverId", 0));			
			driverCommentDto.setVehicleGroupId(valueInObeject.getLong("vehicleGroupId", 0));			
			driverCommentDto.setCompanyId(userDetails.getCompany_id());
			driverCommentDto.setFromDate(DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			driverCommentDto.setToDate(DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			
			if(driverCommentDto.getDriver_id() > 0) {				
				commentList	= driverCommentDao.getDriverCommentListByDriverId(driverCommentDto);
			}else if(driverCommentDto.getVehicleGroupId() > 0 && driverCommentDto.getDriver_id() <= 0) {				
				commentList	= driverCommentDao.getDriverCommentListByGid(driverCommentDto);
			}else {
				commentList	= driverCommentDao.getDriverCommentListByCompanyId(driverCommentDto);
			}
			
			valueObject.put("commentList", DBL.getDriverCommentList(commentList));
			
			valueObject.put("SearchDate", dateRange.replace(" ", "_"));
			if(driverCommentDto.getVehicleGroupId() > 0)
				valueObject.put("SearchGroup", vehicleGroupService.getVehicleGroupByID(driverCommentDto.getVehicleGroupId()).getvGroup());
			valueObject.put("company",
					userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			commentList				= null;
			driverCommentDto		= null;
			userDetails				= null;
		}
	}

	@Override
	public void transferDriverPhoto(List<org.fleetopgroup.persistence.model.DriverPhoto> list) throws Exception {
		org.fleetopgroup.persistence.document.DriverPhoto				driverPhoto		= null;
		List<org.fleetopgroup.persistence.document.DriverPhoto> 		driverPhotoList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				driverPhotoList	= new ArrayList<>();
				for(org.fleetopgroup.persistence.model.DriverPhoto	document : list) {
					driverPhoto	= new org.fleetopgroup.persistence.document.DriverPhoto();
					
					driverPhoto.set_id(document.getDriver_photoid());
					driverPhoto.setDriver_photoname(document.getDriver_photoname());
					driverPhoto.setUploaddate(document.getUploaddate());
					driverPhoto.setDriver_filename(document.getDriver_filename());
					driverPhoto.setDriver_id(document.getDriver_id());
					driverPhoto.setDriver_content(document.getDriver_content());
					driverPhoto.setDriver_contentType(document.getDriver_contentType());
					driverPhoto.setCompanyId(document.getCompanyId());
					driverPhoto.setCreatedById(document.getCreatedById());
					driverPhoto.setMarkForDelete(document.isMarkForDelete());
					
					driverPhotoList.add(driverPhoto);
				}
				System.err.println("Saving DriverPhoto ....");
				mongoTemplate.insert(driverPhotoList, org.fleetopgroup.persistence.document.DriverPhoto.class);
				System.err.println("Saved Successfully....");
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}
	
	@Override
	public long getDriverPhotoMaxId() throws Exception {
		try {
			return driverPhotoRepository.getDriverPhotoMaxId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public ValueObject getDriverEngagementAndPerformanceReport(ValueObject valueInObject) throws Exception {

		ValueObject				valueOutObject				= null;
		Integer					driverId					= null;
		List<DriverHaltDto> 	driverhalt					= null;
		List<FuelDto> 			fuel						= null;
		List<IssuesDto> 		issues						= null;
		List<Date> 				dateList				= null;
		String					queryString					= "";

		String dateRangeFrom = "", dateRangeTo = "", tempdateRangeTo = "";

		String[] From_DateRange = null;
		String[] TO_DateRange = null;
		try {
			dateList	= new ArrayList<>();
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			driverId	    	= valueInObject.getInt("driverId",0);
			From_DateRange = valueInObject.getString("startDateOfMonth", null).split("/");
			TO_DateRange = valueInObject.getString("startDateOfMonth", null).split("/");

			dateRangeFrom = From_DateRange[1] + "-" + From_DateRange[0] + "-01";
			tempdateRangeTo = TO_DateRange[1] + "-" + TO_DateRange[0] + "-01";

			java.util.Date convertedDate = dateFormatSQL.parse(tempdateRangeTo);

			Calendar c = Calendar.getInstance();
			c.setTime(convertedDate);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

			dateRangeTo = TO_DateRange[1] + "-" + TO_DateRange[0] + "-"
					+ c.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			
		/*	if (driverId > 0) {
				queryString  += "AND T.tripFristDriverID =" + driverId + " ";
			}
			*/
			
			driverhalt = driverHaltService.getBataDetails(driverId,dateRangeFrom,dateRangeTo);
			fuel = fuelService.getFuelConsumed(driverId,dateRangeFrom,dateRangeTo);
			issues = issuesService.getIssuesByDriver(driverId,dateRangeFrom,dateRangeTo);
			
			TypedQuery<Object[]> typedQuery = null;

			typedQuery = entityManager.createQuery(
					"SELECT T.tripFristDriverID, T.tripUsageKM, T.tripOpenDate, T.closetripDate, T.tripSheetID, D.driver_firstname, D.driver_Lastname, "
							+ " D.driver_empnumber, D.driver_city, V.vehicle_ExpectedMileage, D.driver_perdaySalary, VG.vGroup ,D.driver_fathername "
							+ " FROM TripSheet as T "
							+ " INNER JOIN Driver AS D ON D.driver_id = T.tripSecDriverID OR D.driver_id = T.tripFristDriverID"
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = D.vehicleGroupId "
							+ " Where T.markForDelete=0 "+queryString+" "
							+ " AND T.companyId = " + userDetails.getCompany_id() + " AND T.tripOpenDate BETWEEN '" + dateRangeFrom + "' AND  '"
							+ dateRangeTo + "' AND T.tripStutesId IN (3, 4) AND D.driver_id = "+driverId+"  ORDER BY T.created desc ",		
							Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();

			List<TripSheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				
				
				Dtos = new ArrayList<TripSheetDto>();
				TripSheetDto list = null;
				for (Object[] result : results) {
					list = new TripSheetDto();
					list.setTripFristDriverID((Integer) result[0]);
					list.setTripUsageKM((Integer) result[1]);
					
					list.setCreatedOn((Date) result[2]);
					if(list.getCreatedOn() != null) {
					list.setCreated(list.getCreatedOn()+"");
					 }
					
					list.setClosetripDateOn((Date) result[3]);
					if(list.getClosetripDateOn() != null) {
					list.setClosetripDate(list.getClosetripDateOn()+"");
					}
					
					list.setTripSheetID((long) result[4]);
					list.setTripFristDriverName((String) result[5]);
					list.setTripFristDriverLastName((String) result[6]);
					list.setDriverEmpNumber((String) result[7]);
					list.setDriverCity((String) result[8]);
					list.setVehicle_ExpectedMileage((double) result[9]);
					list.setDriverSalary((Double) result[10]);
					list.setVehicle_Group((String) result[11]);
					if(result[12] != null) {
						list.setTripFristDriverFatherName(" - "+ result[12]);
					}else {
						list.setTripFristDriverFatherName(" ");
					}
					
					dateList 	= DateTimeUtility.getDaysBetweenDates(list.getCreatedOn(), list.getClosetripDateOn(), dateList);
					
					Dtos.add(list);
				}
			}
			int										currentMonth			= 0;
			currentMonth	= Integer.parseInt(dateRangeFrom.substring(5, 7));
			int noOftripDays = 0;
			for(Date date : dateList) {
				if(Integer.parseInt(AttendanceMonth.format(date)) == currentMonth) {
					noOftripDays++;
				}
			}
			TypedQuery<Object[]> typedQuery1 = null;

			typedQuery1 = entityManager.createQuery(
					"SELECT DC.driver_commentid, DC.driver_title, DC.driver_comment, DC.driver_id, DC.created "
							+ " FROM DriverComment as DC "
							+ " Where DC.markForDelete=0 AND DC.driver_id = "+driverId+" "
							+ " AND DC.companyId = " + userDetails.getCompany_id() + " AND DC.created BETWEEN '" + dateRangeFrom + "' AND  '"
							+ dateRangeTo + "'  ORDER BY DC.created desc ",		
							Object[].class);
			
			List<Object[]> results1 = typedQuery1.getResultList();

			List<DriverCommentDto> Dtos1 = null;
			if (results1 != null && !results1.isEmpty()) {
				
				Dtos1 = new ArrayList<DriverCommentDto>();
				DriverCommentDto list1 = null;
				for (Object[] result2 : results1) {
					list1 = new DriverCommentDto();
					
					list1.setDriver_commentid((long) result2[0]);
					list1.setDriver_title((String) result2[1]);
					list1.setDriver_comment((String) result2[2]);
					list1.setDriver_id((Integer) result2[3]);
					
					list1.setCreated((Date) result2[4]);
					if(list1.getCreated() != null) {
						list1.setCreationDate(list1.getCreated()+"");
					}
					
					Dtos1.add(list1);
				}
			}
			
			valueOutObject	= new ValueObject();
			valueOutObject.put("Trip", Dtos);
			valueOutObject.put("Driverhalt", driverhalt);
			valueOutObject.put("Fuel", fuel);
			valueOutObject.put("Issues", issues);
			valueOutObject.put("Comment", Dtos1);
			valueOutObject.put("FromDate", dateRangeFrom);
			valueOutObject.put("ToDate", dateRangeTo);
			valueOutObject.put("noOftripDays", noOftripDays);
			

			return	valueOutObject;
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}
	
	@Transactional
	public List<DriverReminderDto> getListDriverReminderMonthWise(String startDateOfMonth , String endDateOfMonth, Integer companyId) throws Exception{
		
		try {
			TypedQuery<Object[]> query =	null;
			
			query = entityManager
					.createQuery(" SELECT R.driver_id, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_renewaldate, R.companyId, D.driver_firstname "
							+ " From DriverReminder As R  "
							+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId "
							+ " INNER JOIN Driver AS D ON  D.driver_id = R.driver_id "
							+ " WHERE R.companyId = "+companyId
							+ " AND R.driver_dlto BETWEEN '"+startDateOfMonth+"' AND '"+endDateOfMonth+"'"
							+ " AND R.markForDelete = 0 AND D.markForDelete = 0 "
							+ " ORDER BY R.driver_renewaldate ASC "
							, Object[].class);
		
		List<Object[]> results = query.getResultList();

		List<DriverReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverReminderDto>();
			DriverReminderDto list = null;
			for (Object[] result : results) {
				list = new DriverReminderDto();
				
				list.setDriver_id((Integer) result[0]);
				
				list.setDriver_remid((Integer) result[1]);
				
				if(result[2] != null)
				list.setDriver_remindertype((String) result[2]);
					
				if(result[3] != null)
				list.setDriver_dlnumber((String) result[3]);
				
				if(result[4] !=null)
				list.setDriver_renewaldate((String) result[4]);
				
				list.setCompanyId((Integer) result[5]);
				
				if(result[6] != null)
				list.setDriver_firstname((String) result[6]);
				
				Dtos.add(list);
			}
		}
		
		return Dtos;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public ValueObject getDriverNameWiseList(ValueObject object) throws Exception {
		List<DriverDto> driverList 				= null;
		List<Driver> 	driver					= null;
		String 			term 					= null;
		int 			companyId 				= 0;
		long			userId					= 0;
		try {

			driverList 	= new ArrayList<DriverDto>();
			term 		= object.getString("term");
			companyId   = object.getInt("companyId");
			userId		= object.getLong("userId");

			driver = searchByDriverName(term, companyId, userId);
			if (driver != null && !driver.isEmpty()) {
				for (Driver add : driver) {
					DriverDto wadd = new DriverDto();
					wadd.setDriver_id(add.getDriver_id());
					wadd.setDriver_empnumber(add.getDriver_empnumber());
					wadd.setDriver_firstname(add.getDriver_firstname());
					wadd.setDriver_Lastname(add.getDriver_Lastname());
					wadd.setDriver_mobnumber(add.getDriver_mobnumber());
					driverList.add(wadd);
				}
			}

			object.put("driverList", driverList);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			driverList 	= null;
			driver  	= null;
			object 		= null;
		}
	}
	
	@Override
	public ValueObject getCleanerNameWiseList(ValueObject object) throws Exception {
		List<DriverDto> cleanerList 			= null;
		List<Driver> 	cleaner					= null;
		String 			term 					= null;
		int 			companyId 				= 0;
		long			userId					= 0;
		try {

			cleanerList = new ArrayList<DriverDto>();
			term 		= object.getString("term");
			companyId   = object.getInt("companyId");
			userId		= object.getLong("userId");

			cleaner = searchByCleanerName(term, companyId, userId);
			if (cleaner != null && !cleaner.isEmpty()) {
				for (Driver add : cleaner) {
					DriverDto wadd = new DriverDto();
					wadd.setDriver_id(add.getDriver_id());
					wadd.setDriver_empnumber(add.getDriver_empnumber());
					wadd.setDriver_firstname(add.getDriver_firstname());
					wadd.setDriver_Lastname(add.getDriver_Lastname());
					cleanerList.add(wadd);
				}
			}

			object.put("cleanerList", cleanerList);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			cleaner 	 = null;
			cleanerList  = null;
			object  	 = null;
		}
	}
	
	@Override
	public ValueObject getDriverTripsheetAdvanceReport(ValueObject valueObject) throws Exception {		
		Integer							driverId							= 0;
		CustomUserDetails				userDetails							= null;
		List<TripSheetDto> 				driverTripsheetAdvance				= null;		
		ValueObject						tableConfig							= null;
		String							dateRange							= null;
		String[] 						From_TO_DateRange 					= null;	
		String 							dateRangeFrom 						= null;
		String 							dateRangeTo 						= null;
		String 							query								= null;
		double							totalDebit							= 0.0;
		double							totalCredit							= 0.0;
		double							balance								= 0.0;
		try {
				userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
				driverId						= valueObject.getInt("driverId", 0);
				dateRange						= valueObject.getString("dateRange");
				From_TO_DateRange				= dateRange.split(" to ");
				
				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			
				String driver = "", date = "";
				
				if(driverId != 0)
				{					
					driver 				= " AND TS.tripFristDriverID = "+ driverId +" ";
				}				
					date 				= " TS.tripOpenDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
				
					query 				= " "+date+" "+driver+" ";				
				
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.DRIVER_TRIPSHEET_ADVANCE_DATA_FILE_PATH);

				tableConfig				= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig				= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);		
				driverTripsheetAdvance 	= DriverReportDaoImpl.getDriverTripsheetAdvanceDetails(query, userDetails.getCompany_id());
				
				for(TripSheetDto trip : driverTripsheetAdvance) {
					totalDebit  += trip.getTripTotalAdvance();
					totalCredit += trip.getCloseTripAmount();
				}
				
				balance			= totalDebit - totalCredit;
				
				valueObject.put("balance", Utility.round(balance,2));
				valueObject.put("driverTripsheetAdvance", driverTripsheetAdvance);
				valueObject.put("tableConfig", tableConfig.getHtData());
				valueObject.put("company",
				userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig				= null;
			driverTripsheetAdvance	= null;
			userDetails				= null;			
		}
	}
	@Transactional
	@Override
	public ValueObject saveDriverDetails(ValueObject valueObject,MultipartFile file) throws Exception {
		CustomUserDetails			userDetails							= null;
		Driver						savedDriver							= null;
		Driver						driveBL								= null;
		DriverDto 					validateDriver						= null; 
		DriverReminder				ReminderBL							= null;
		HashMap<String, Object>		configuration						= null;
		boolean						validateDOB	   						= false;
		boolean						validateGroup	   					= false;
		boolean						validateSalaryType      			= false;
		boolean						validatePerDaySalary      			= false;
		boolean						validateMobileNo     				= false;
		boolean						validateEmpNo     					= false;
		boolean						validateInsuranceNo     			= false;
		boolean						validateStartDate    				= false;
		boolean						validateJobTitle     				= false;
		boolean						validateDLNumber     				= false;
		boolean						validateDriverRenewal     			= false;
		boolean						validateAadhar     					= false;
		boolean						validateVehicle   					= false;
		boolean						validateDLNumberWhenDriver   		= false;
		String 						DLNumber							= "";
		String 						employeeNumber						= "";
		String 						query								= "";
		ValueObject					valueOutObject						= null;
		DriverHistory				driveHistoryBL								= null;
		
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 					= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			validateDOB	   					= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_DOB, false); 
			validateGroup	   				= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_GROUP, false); 
			validateSalaryType	   			= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_SALARY_TYPE, false); 
			validatePerDaySalary	   		= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_PER_DAY_SALARY, false); 
			validateMobileNo   				= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_MOBILE_NO, false); 
			validateEmpNo	   				= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_EMPLOYEE_NO, false); 
			validateInsuranceNo	   			= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_INSURANCE_NO, false); 
			validateJobTitle	   			= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_JOB_TITLE, false); 
			validateStartDate	   			= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_START_DATE, false); 
			validateDLNumber	   			= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_DL_NO, false);
			validateDriverRenewal	   		= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_DRIVER_RENEWAL, false);
			validateAadhar	   				= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_AADHAR, false);
			validateVehicle	   				= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_VEHICLE, false);
			validateDLNumberWhenDriver	   	= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_DL_NUMBER_WHEN_JOBTYPE_IS_DRIVER, false);
			valueOutObject					= new ValueObject();
			
			
			if((valueObject.getString("driverFirstName",null) == null || valueObject.getString("driverFirstName",null).trim().equalsIgnoreCase(""))) {
				valueOutObject.put("driverFirstName", true);
				return valueOutObject;
			}if((validateDOB) && (valueObject.getString("driverDateOfBirth",null) == null || valueObject.getString("driverDateOfBirth",null).trim().equalsIgnoreCase(""))) {
				valueOutObject.put("driverDateOfBirth", true);
				return valueOutObject;
			}if(validateGroup && (valueObject.getLong("vGroup",0) <= 0)) {
				valueOutObject.put("vGroup", true);
				return valueOutObject;
			}if(validateVehicle && (valueObject.getInt("vid",0) <= 0)) {
				valueOutObject.put("vehicle", true);
				return valueOutObject;
			}if((validateAadhar) && (valueObject.getString("aadharNumber",null) == null || valueObject.getString("aadharNumber",null).trim().equalsIgnoreCase(""))) {
				valueOutObject.put("aadhar", true);
				return valueOutObject;
			}else if((validateAadhar) && (valueObject.getString("aadharNumber",null) != null || !valueObject.getString("aadharNumber",null).trim().equalsIgnoreCase(""))) {
				if(valueObject.getString("aadharNumber").length() < 12 ) {
					valueOutObject.put("aadharValidate", true);
					return valueOutObject;
				}
			}if(validateSalaryType && (valueObject.getShort("driverSalaryTypeId",(short)0) <= 0)) {
				valueOutObject.put("driverSalaryTypeId", true);
				return valueOutObject;
			}if(validatePerDaySalary && (valueObject.getDouble("driverPerDaySalary",0) <= 0)) {
				valueOutObject.put("driverPerDaySalary", true);
				return valueOutObject;
			}if(validateMobileNo && (valueObject.getString("mobileNo",null) == null)) {
				valueOutObject.put("mobileNo", true);
				return valueOutObject;
			}if(validateEmpNo && (valueObject.getString("empNumber",null) == null)) {
				valueOutObject.put("empNumber", true);
				return valueOutObject;
			}if(validateInsuranceNo && (valueObject.getString("insuranceNo",null) == null)) {
				valueOutObject.put("insuranceNo", true);
				return valueOutObject;
			}if(validateJobTitle && (valueObject.getInt("driJobId",0) <= 0)) {
				valueOutObject.put("driJobId", true);
				return valueOutObject;
			}if(validateStartDate && (valueObject.getString("joinDate",null) == null)) {
				valueOutObject.put("joinDate", true);
				return valueOutObject;
			}if((validateDLNumber) &&(valueObject.getString("dlNumber",null) == null) && valueObject.getBoolean("jobTypeDriver", false)) {// this validation for normal DL
				valueOutObject.put("dlNumber", true);
				return valueOutObject;
			}if((valueObject.getBoolean("jobTypeDriver") == true) && validateDLNumberWhenDriver &&(valueObject.getString("dlNumber",null) == null)) {// this validation for (when job type is driver)
				valueOutObject.put("dlNumber", true);
				return valueOutObject;
			}if(valueObject.getString("dlNumber") != null ){
				DLNumber	= valueObject.getString("dlNumber");
			}if(valueObject.getString("empNumber") != null ){
				employeeNumber	= valueObject.getString("empNumber");
			}
			
			if((validateDLNumber&& validateDLNumberWhenDriver && valueObject.getBoolean("jobTypeDriver") == true )&& validateEmpNo) { 
				query	= "AND (D.driver_empnumber = '"+employeeNumber+"' OR D.driver_dlnumber = '"+DLNumber+"') ";
			}else if(validateDLNumber&& validateDLNumberWhenDriver && valueObject.getBoolean("jobTypeDriver") == true) {
				query	= " AND D.driver_dlnumber = '"+DLNumber+"'";
			}else if(validateEmpNo) {
				query	= " AND D.driver_empnumber = '"+employeeNumber+"'";
				}
		
			if(query != null && !query.trim().equals(""))
				validateDriver = validateDriverDetails(query); 
			
			if(validateDriver != null) {
				if((DLNumber != "") && (DLNumber.equalsIgnoreCase(validateDriver.getDriver_dlnumber().trim()))) {
					valueOutObject.put("dlNumberExist", true);
					return valueOutObject;
				}else if((employeeNumber != "") && (employeeNumber.equalsIgnoreCase(validateDriver.getDriver_empnumber().trim()))) {
					valueOutObject.put("empNumberExist", true);
					return valueOutObject;
				}else {
					valueOutObject.put("alreadyExist", true);
					return valueOutObject;
				}
			}else {
				
				driveBL 		= DBL.prepareDriverDetails(valueObject,userDetails);
				savedDriver		= addDriver(driveBL);
				
				
				valueObject.put("operationId", 1);
				driveHistoryBL 		= DBL.prepareDriverHistoryDetails(savedDriver,valueObject );
				addDriverHistory(driveHistoryBL);
				
				if(validateDriverRenewal && (valueObject.getShort("driverRenewalTypeId",(short)0) > 0)) {
					ReminderBL		= driverReminderBL.prepareDriverReminderDetails(valueObject,savedDriver,userDetails,file);
					addDriverReminder(ReminderBL);
				}

				valueOutObject.put("driverId", savedDriver.getDriver_id());
				valueOutObject.put("save", true);
			}
			
			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails						= null;       
			configuration					= null;       
			                                              
		}
	}
	
	@Transactional(readOnly = false)
	public DriverDto validateDriverDetails(String finalQuery) throws Exception {
		Object[] 			driver		 	= null;
		CustomUserDetails 	userDetails 	= null;
		DriverDto 			driverDto 		= null;	
		try {
			
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Query query = entityManager.createQuery("SELECT D.driver_id, D.driver_firstname, D.driver_dlnumber, D.driver_empnumber "
						+ " FROM Driver AS D where D.companyId ="+userDetails.getCompany_id()+" "+finalQuery+" AND D.markForDelete = 0");
			query.setMaxResults(1);
			driver = (Object[]) query.getSingleResult();
			if (driver == null || driver.length == 0) {
				return null;
			}
			driverDto 	= new DriverDto();
			if (driver != null) {
				driverDto.setDriver_id((Integer) driver[0]);
				driverDto.setDriver_firstname((String) driver[1]);
				driverDto.setDriver_dlnumber((String) driver[2]);
				driverDto.setDriver_empnumber((String) driver[3]);
			}
			return driverDto;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@Override
	public ValueObject updateDriverDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails							= null;
		Driver						currentDriver						= null;
		Driver						savedDriver							= null;
		Driver						driveBL								= null;
		DriverDto 					validateDriver						= null; 
		HashMap<String, Object>		configuration						= null;
		boolean						validateDOB	   						= false;
		boolean						validateGroup	   					= false;
		boolean						validateSalaryType      			= false;
		boolean						validatePerDaySalary      			= false;
		boolean						validateMobileNo     				= false;
		boolean						validateEmpNo     					= false;
		boolean						validateInsuranceNo     			= false;
		boolean						validateStartDate    				= false;
		boolean						validateJobTitle     				= false;
		boolean						validateDLNumber     				= false;
		boolean						validateAadhar     					= false;
		boolean						validateVehicle   					= false;
		boolean						validateDLNumberWhenDriver 			= false;
		boolean						addStatusChangeRemarkConfig 		= false;
		String 						DLNumber							= "";
		String 						employeeNumber						= "";
		String 						query								= "";
		ValueObject 				valueOutObject						= null;
		DriverHistory				driveHistoryBL								= null;
		try {
			
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 					= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			validateDOB	   					= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_DOB, false); 
			validateGroup	   				= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_GROUP, false); 
			validateSalaryType	   			= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_SALARY_TYPE, false); 
			validatePerDaySalary	   		= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_PER_DAY_SALARY, false); 
			validateMobileNo   				= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_MOBILE_NO, false); 
			validateEmpNo	   				= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_EMPLOYEE_NO, false); 
			validateInsuranceNo	   			= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_INSURANCE_NO, false); 
			validateJobTitle	   			= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_JOB_TITLE, false); 
			validateStartDate	   			= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_START_DATE, false); 
			validateDLNumber	   			= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_DL_NO, false);
			validateAadhar	   				= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_AADHAR, false);
			validateVehicle	   				= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_VEHICLE, false);
			validateDLNumberWhenDriver	   	= (boolean) configuration.getOrDefault(DriverConfigurationContant.VALIDATE_DL_NUMBER_WHEN_JOBTYPE_IS_DRIVER, false);
			addStatusChangeRemarkConfig  	= (boolean) configuration.getOrDefault("addStatusChangeRemark", false);
			valueOutObject					= new ValueObject();
			currentDriver = getDriver(valueObject.getInt("driverId"), userDetails);
			
			if((valueObject.getString("driverFirstName",null) == null || valueObject.getString("driverFirstName",null).trim().equalsIgnoreCase(""))) {
				valueOutObject.put("driverFirstName", true);
				return valueOutObject;
			}if(validateDOB && (valueObject.getString("driverDateOfBirth",null) == null || valueObject.getString("driverDateOfBirth",null).trim().equalsIgnoreCase(""))) {
				valueOutObject.put("driverDateOfBirth", true);
				return valueOutObject;
			}if(validateGroup && (valueObject.getLong("vGroup",0) <= 0)) {
				valueOutObject.put("vGroup", true);
				return valueOutObject;
			}if(validateVehicle && (valueObject.getInt("vid",0) <= 0)) {
				valueOutObject.put("vehicle", true);
				return valueOutObject;
			}if((validateAadhar) && (valueObject.getString("aadharNumber",null) == null || valueObject.getString("aadharNumber",null).trim().equalsIgnoreCase(""))) {
				valueOutObject.put("aadhar", true);
				return valueOutObject;
			}else if((validateAadhar) && (valueObject.getString("aadharNumber",null) != null || !valueObject.getString("aadharNumber",null).trim().equalsIgnoreCase(""))) {
				if(valueObject.getString("aadharNumber").length() < 12 ) {
					valueOutObject.put("aadharValidate", true);
					return valueOutObject;
				}
			}if(validateSalaryType && (valueObject.getShort("driverSalaryTypeId",(short)0) <= 0)) {
				valueOutObject.put("driverSalaryTypeId", true);
				return valueOutObject;
			}if(validatePerDaySalary && (valueObject.getDouble("driverPerDaySalary",0) < 0)) {
				valueOutObject.put("driverPerDaySalary", true);
				return valueOutObject;
			}if(validateMobileNo && (valueObject.getString("mobileNo",null) == null)) {
				valueOutObject.put("mobileNo", true);
				return valueOutObject;
			}if(validateEmpNo && (valueObject.getString("empNumber",null) == null)) {
				valueOutObject.put("empNumber", true);
				return valueOutObject;
			}if(validateInsuranceNo && (valueObject.getString("insuranceNo",null) == null)) {
				valueOutObject.put("insuranceNo", true);
				return valueOutObject;
			}if(validateJobTitle && (valueObject.getInt("driJobId",0) <= 0)) {
				valueOutObject.put("driJobId", true);
				return valueOutObject;
			}if(validateStartDate && (valueObject.getString("joinDate",null) == null)) {
				valueOutObject.put("joinDate", true);
				return valueOutObject;
			}if((validateDLNumber) &&(valueObject.getString("dlNumber",null) == null) && valueObject.getBoolean("jobTypeDriver", false)) {
				valueOutObject.put("dlNumber", true);
				return valueOutObject;
			}if((valueObject.getBoolean("jobTypeDriver") == true)&& validateDLNumberWhenDriver &&(valueObject.getString("dlNumber",null) == null)) {
				valueOutObject.put("dlNumber", true);
				return valueOutObject;
			}
			
			if(valueObject.getString("dlNumber") != null ){
				DLNumber	= valueObject.getString("dlNumber");
			}
			if(valueObject.getString("empNumber") != null ){
				employeeNumber	= valueObject.getString("empNumber");
			}
			
			
			if((!DLNumber.equalsIgnoreCase(currentDriver.getDriver_dlnumber()))&& validateDLNumberWhenDriver && (validateDLNumber && valueObject.getBoolean("jobTypeDriver"))) {
				query	= " AND D.driver_dlnumber = '"+DLNumber+"'";
			}else if((!employeeNumber.equalsIgnoreCase(currentDriver.getDriver_empnumber())) && (validateEmpNo)) {
				query	= " AND D.driver_empnumber = '"+employeeNumber+"'";
			}
			
			if(query != null && !query.trim().equals(""))
				validateDriver = validateDriverDetails(query); 
			
				
				if(validateDriver != null) {
					if((DLNumber != "") && (DLNumber.equalsIgnoreCase(validateDriver.getDriver_dlnumber().trim()))) {
						valueOutObject.put("dlNumberExist", true);
						return valueOutObject;
					}else if((employeeNumber != "") && (employeeNumber.equalsIgnoreCase(validateDriver.getDriver_empnumber().trim()))) {
						valueOutObject.put("empNumberExist", true);
						return valueOutObject;
					}else {
						valueOutObject.put("alreadyExist", true);
						return valueOutObject;
					}
				}else {
					
					driveBL 		= DBL.prepareDriverDetails(valueObject,userDetails);
					savedDriver		= addDriver(driveBL);
					valueObject.put("operationId", 1);
					driveHistoryBL 		= DBL.prepareDriverHistoryDetails(savedDriver,valueObject);
					addDriverHistory(driveHistoryBL);
					

					valueOutObject.put("driverId", savedDriver.getDriver_id());
					valueOutObject.put("save", true);
					
					if(addStatusChangeRemarkConfig && valueObject.getShort("currentStatusId",(short) 0) >0 && valueObject.getShort("changeToStatusId",(short) 0) >0 && valueObject.getShort("currentStatusId",(short) 0) != valueObject.getShort("changeToStatusId",(short) 0)){
						valueObject.put("typeId",StatusRemarkBL.DRIVER_STATUS_CHANGE);
						valueObject.put("transactionId",savedDriver.getDriver_id());
						valueObject.put("companyId",userDetails.getCompany_id());
						valueObject.put("userId",userDetails.getId());
						statusRemarkService.saveVehicleStatusRemark(valueObject);
					}
				}
			
			
			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails						= null;       
			configuration					= null;       
			                                              
		}
	}
	
	@Override
	public ValueObject getDriverRenewalTypeList(ValueObject object) throws Exception {
		int 								companyId 				= 0;
		long 								userId 					= 0;
		CustomUserDetails 					userDetails 			= null;
		DriverDto							driver					= null;
		ArrayList<RenewalReminderDto>		periodThreshold			= null;
		try {
			companyId 					= object.getInt("companyId");
			userId 						= object.getLong("userId");
			
			userDetails = new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			periodThreshold = new ArrayList<RenewalReminderDto>();
			periodThreshold.add( new RenewalReminderDto(0, "Days"));
			periodThreshold.add( new RenewalReminderDto(7, "Weeks"));
			periodThreshold.add( new RenewalReminderDto(28, "Months"));
			
			object.put("periodThreshold", periodThreshold);
			
			driver	= getDriver_Header_Show_Details_RenewalReminder(object.getInt("driverId"), userDetails.getCompany_id());
			
			if(driver != null) {
				object.put("driver", DBL.Show_Driver_Header_Details(driver));
				
				object.put("driverDocType", driverDocTypeBL.DriDocTypeListofDto(driverDocTypeService.findAllByCompanyId(userDetails.getCompany_id())));
			}
			

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			driver 			= null;
			userDetails 	= null;
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	@Transactional
	public ValueObject saveDriverRenewalFromMobile(ValueObject object) throws Exception {
		int 								companyId 				= 0;
		long 								userId 					= 0;
		CustomUserDetails 					userDetails 			= null;
		DriverReminder      				driverReminder			= null;
		String								base64					= null;
		String								imageName				= null;
		String								imageExt				= null;
		try {
			companyId 					= object.getInt("companyId");
			userId 						= object.getLong("userId");
			base64						= object.getString("base64String",null);
			imageName					= object.getString("imageName",null);
			imageExt					= object.getString("imageExt",null);
			
			userDetails = new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			driverReminder = driverReminderBL.saveDriverReminderDetailsFromMobile(object);
			
			if(base64 != null) {
				byte[] bytes = ByteConvertor.base64ToByte(base64);
				driverReminder.setDriver_filename(imageName);
				driverReminder.setDriver_content(bytes);
				driverReminder.setDriver_contentType(imageExt);
			}
			
			addDriverReminder(driverReminder);
			
			if(driverReminder != null) {
				object.put("addDriverReminder", true);
				object.put("driverId", driverReminder.getDriver_id());
			}else {
				object.put("addDriverReminder", false);
			}

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			userDetails 	= null;
		}
	}
	
	@Override
	public ValueObject showDriverRenewalFromMobile(ValueObject object) throws Exception {
		int 								companyId 				= 0;
		long 								userId 					= 0;
		CustomUserDetails 					userDetails 			= null;
		DriverDto							driver					= null;
		List<DriverReminderDto>				reminderDetails			= null;
		try {
			companyId 					= object.getInt("companyId");
			userId 						= object.getLong("userId");
			
			userDetails = new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			driver = getDriver_Header_Show_Details_RenewalReminder(object.getInt("driverId"), companyId);
			
			
			if(driver != null) {
				object.put("driver", DBL.Show_Driver_Header_Details(driver));
				
				reminderDetails = driverReminderBL.getDriverReminder_Showing_Details(listDriverReminder(object.getInt("driverId"), userDetails.getCompany_id()));
				
				for(DriverReminderDto driverReminderDetails : reminderDetails) {
					
					if(driverReminderDetails.getDriver_content() != null) {
						driverReminderDetails.setRenewalBase64Document(ByteConvertor.byteArraytoBase64(driverReminderDetails.getDriver_content()));
						driverReminderDetails.setFileExtension(driverReminderDetails.getDriver_contentType());
					}
				}
				
				object.put("driverReminder", reminderDetails);
			
			}
			
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			userDetails 	= null;
		}
	}
	
	@Override
	public ValueObject searchDriverRenewalByDriverNumber(ValueObject object) throws Exception {
		int 								companyId 				= 0;
		long 								userId 					= 0;
		CustomUserDetails 					userDetails 			= null;
		List<Driver> 						driverDetails 			= null;
		try {
			companyId 					= object.getInt("companyId");
			userId 						= object.getLong("userId");
			
			userDetails = new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			driverDetails = driverDao.ValidateEmpDriver(object.getString("driverEmpNumber"), companyId);
			
			if(driverDetails != null && !driverDetails.isEmpty()) {
				object.put("driverFound", true);
				object.put("driverId", driverDetails.get(0).getDriver_id());
			} else {
				object.put("driverNotFound", true);
			}

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			driverDetails 		= null;
			userDetails 	    = null;
		}
	}
	
	@Override
	public Driver getLimitedDriverDetails(Integer driverId) throws Exception {
		try {
			return driverDao.getLimitedDriverDetails(driverId);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public HashMap<Integer, DriverDto> getDriverStatusHM(String driverIds) throws Exception {
		try {
			TypedQuery<Object[]>	queryt = entityManager.createQuery(
					"SELECT R.driver_id, R.driver_empnumber, R.driver_firstname, R.driver_Lastname"
					+ " FROM Driver R"
					+ " WHERE R.driver_id IN ("+driverIds+") AND driverStatusId <> "+DriverStatus.DRIVER_STATUS_ACTIVE+"  and R.markForDelete = 0 ",
					Object[].class);
			
			List<Object[]> results = queryt.getResultList();

			HashMap<Integer, DriverDto> driverHM = null;
			if (results != null && !results.isEmpty()) {
				driverHM = new HashMap<Integer, DriverDto>();
				DriverDto list = null;
				for (Object[] result : results) {
					list = new DriverDto();

					list.setDriver_id((Integer) result[0]);
					list.setDriver_empnumber((String) result[1]);
					list.setDriver_firstname((String) result[2]);
					list.setDriver_Lastname((String) result[3]);

					driverHM.put(list.getDriver_id(), list);
				}
			}
			return driverHM;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<Driver> getActiveDriverList(Integer companyId,short driverStatusId) throws Exception {
		try {
			return driverDao.getActiveDriverList(companyId, driverStatusId);
		} catch (Exception e) {
			throw e;
		}
		
	}
	@Override
	public ValueObject getDefaultDriverForVehicle(ValueObject object) throws Exception {
		List<DriverDto> 		defaultDriverListForVehicle  	= null;
		CustomUserDetails	userDetails						= null;	
		DriverDto 			driverDto 						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			defaultDriverListForVehicle	= new ArrayList<>();
			TypedQuery<Object[]>	queryt = entityManager.createQuery(
					" SELECT D.driver_id, D.driver_firstname, D.driver_fathername, "
					+ " D.driver_Lastname, D.driver_empnumber, D.vid, V.vehicle_registration"
					+ " FROM Driver D"
					+ " LEFT JOIN Vehicle AS V ON V.vid = D.vid AND V.vStatusId = "+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+" "		
					+ " WHERE D.vid = "+object.getInt("vid")+" AND D.driverStatusId = "+DriverStatus.DRIVER_STATUS_ACTIVE+"  "
					+ " AND D.companyId = "+userDetails.getCompany_id()+" AND D.markForDelete = 0 ",
					Object[].class);
			
			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				
				for (Object[] result : results) {
					driverDto = new DriverDto();

					driverDto.setDriver_id((Integer) result[0]);
					driverDto.setDriver_firstname((String) result[1]);
					driverDto.setDriver_fathername((String) result[2]);
					driverDto.setDriver_Lastname((String) result[3]);
					driverDto.setDriver_empnumber((String) result[4]);
					driverDto.setVid((Integer) result[5]);
					driverDto.setVehicle_registration((String) result[6]);
					defaultDriverListForVehicle.add(driverDto);
				}
			}
			
			if(defaultDriverListForVehicle != null && !defaultDriverListForVehicle.isEmpty()) {
				object.put("defaultDriverListForVehicle", defaultDriverListForVehicle);
				object.put("defaultDriverForVehicle", defaultDriverListForVehicle.get(0));
				
			}
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
@Override
	public ValueObject getCountByDriverJObId(ValueObject	valueObject) throws Exception {
		
		Query 				query 			 				= null; 
		try {
			
			
			if(valueObject.getBoolean("jobTypeWiseAutoGenerateEmpNo",false)) {
				query  = entityManager
						.createQuery("SELECT COUNT(D.driver_id)  "
								+ " FROM DriverJobType DJT "
								+ " LEFT JOIN Driver D ON D.driJobId = DJT.driJobId  AND D.companyId = "+valueObject.getInt("companyId")+" "
								+ " where DJT.driJobId = "+valueObject.getInt("driJobId",0)+" AND D.companyId = "+valueObject.getInt("companyId")+" ");
			}else if(valueObject.getBoolean("autoGenerateEmpNo",false)) {
				query  = entityManager
						.createQuery("SELECT COUNT(D.driver_id) FROM Driver D "
								+ " where D.companyId = "+valueObject.getInt("companyId")+" ");
			}else {
				return valueObject;
			}
			
			
			
			Long result = null;
			
			
			try {
				result = (Long) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			

					if(result != null) {
						valueObject.put("empCode", getEmpCode(valueObject.getString("driJob",""), ((Long) result+1)));
					}
					
			
					else {
						valueObject.put("empCode", getEmpCode(valueObject.getString("driJob",""), (long) 0));
					}
					
				
				
			
		
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private String getEmpCode(String jobType, Long count) {
		
		return jobType.substring(0,4)+"-"+count;
	}
	
	@Override
	public ValueObject getDriverDocuments(ValueObject valueObject) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		valueObject.put("documentList", driverDocumentService.listDriverDocument(valueObject.getInt("driverId",0), userDetails.getCompany_id()));
		
		return valueObject;
	}
	
	@Override
	public ValueObject totalDueSoonCount(Date date, int companyId) throws Exception {
		ValueObject 			valueObject 	= null;
		BigInteger		 		dueSoonCount 	= null;
		Query queryt = null;
		try {
			valueObject 	= new ValueObject();
			
			
			
			queryt = entityManager.createNativeQuery(" SELECT COUNT(R.driver_id)" 
					+ " From driverreminder As R " 
					+ " INNER JOIN Driver DR ON DR.driver_id = R.driver_id " 
					+ " where '" + date +"' between DATE_SUB(R.driver_dlto, INTERVAL R.driver_timethreshold DAY) AND R.driver_dlto  AND R.companyId =" + companyId
					+ " AND DR.driverStatusId NOT IN("+DriverStatus.DRIVER_STATUS_INACTIVE +","+DriverStatus.DRIVER_STATUS_SUSPEND +") AND R.newDriverReminder = 0 AND R.markForDelete = 0 AND DR.markForDelete = 0 ");
			dueSoonCount = (BigInteger) queryt.getSingleResult();

			
			valueObject.put("dueSoonCount", dueSoonCount);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public ValueObject getTotalOverDueRenewalCount(Date date, Integer companyId) throws Exception {
		ValueObject 			valueObject 	= null;
		long 					overDueCount	= 0;
	//	Query queryt = null;
		TypedQuery<Object[]> queryt = null;
		try {
			valueObject 	= new ValueObject();
			queryt = entityManager.createQuery(" SELECT COUNT(R.driver_id), DR.driverStatusId" 
					+ " From DriverReminder As R " 
					+ " INNER JOIN Driver DR ON DR.driver_id = R.driver_id " 
					+ "where R.driver_dlto < '" + date + "' AND R.companyId =" + companyId + " AND "
					+ "DR.driverStatusId NOT IN("+DriverStatus.DRIVER_STATUS_INACTIVE +","+DriverStatus.DRIVER_STATUS_SUSPEND +") AND R.newDriverReminder = 0 AND R.markForDelete = 0 AND DR.markForDelete = 0 ",Object[].class);
			
			List<Object[]> results = queryt.getResultList();

			List<DriverReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverReminderDto>();
				DriverReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new DriverReminderDto();
					if(result[0] != null) {
						renewal.setDriverCount((Long) result[0]);
					}else {
						renewal.setDriverCount(Long.parseLong("0"));
					}
					if(result[1] != null) {
						renewal.setDriverStatusId((short) result[1]);
					}else {
						renewal.setDriverStatusId((short) 0);
					}
					
					overDueCount = renewal.getDriverCount();
					Dtos.add(renewal);
				}
			}
			valueObject.put("overDueCount", overDueCount);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	@Transactional
	public ValueObject getOverDueData(ValueObject valueObject) throws Exception {
		CustomUserDetails 					userDetails 					= null;
		String								driverStatusQuery				= "";
		Integer    							typeId							= null;
		try {
			java.util.Date	todaysStrDate		= DateTimeUtility.getCurrentDate();
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverStatusQuery = "AND d.driverStatusId <> 2 AND d.driverStatusId <> 6 ";
			
			valueObject.put("driverStatusQuery", driverStatusQuery);
			typeId 	   = valueObject.getInt("typeId",0);
			if(typeId == 3) {
				 List<DriverRenewalRecieptDto> listofRenewalReciept = listofRenewalReciept(valueObject, userDetails);
				 valueObject.put("driverReminder", listofRenewalReciept);
				 if(listofRenewalReciept != null && !listofRenewalReciept.isEmpty()) {
					 valueObject.put("renewalReceiptCount", listofRenewalReciept.size());
				 }else {
					 valueObject.put("renewalReceiptCount", 0);
				 }
				 
			}else {
				List<DriverReminderDto> driverReminder = driverReminderBL.prepareListDriverReminderBean(listof_OverDueData(todaysStrDate,valueObject , userDetails));
				valueObject.put("driverReminder", driverReminder);
			}
			
			
			return valueObject;
			
		} catch (Exception e) {
			System.err.println("ERR"+e);
			throw e;
		}finally {
			
		}
	}
	
	@Transactional
	public List<DriverReminderDto> listof_OverDueData(Date currentdate,ValueObject valueObject,CustomUserDetails userDetails) throws Exception {
		Integer 	typeId	= 0;
		try {
			
			typeId 	   = valueObject.getInt("typeId",0);
			
			Query queryt = null;
			if(typeId == 1 ) {
				queryt = entityManager.createNativeQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
								+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate FROM  driverreminder AS R "
								+ " INNER JOIN Driver AS d ON d.driver_id = R.driver_id " +valueObject.getString("driverStatusQuery", "")+""
								+ " INNER JOIN driverdoctype DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " Where R.driver_dlto < '" + currentdate
								+ "' AND d.driver_id= R.driver_id and d.companyId = " + userDetails.getCompany_id()
								+ " AND R.newDriverReminder = 0 and d.markForDelete = 0 AND R.markForDelete = 0 ORDER BY R.driver_id desc ");
			}else if (typeId == 2) {
				queryt = entityManager.createNativeQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
								+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate FROM  driverreminder AS R "
								+ " INNER JOIN Driver AS d ON d.driver_id = R.driver_id " +valueObject.getString("driverStatusQuery", "")+""
								+ " INNER JOIN driverdoctype DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " Where '" + currentdate
								+ "' between DATE_SUB(R.driver_dlto, INTERVAL R.driver_timethreshold DAY) AND R.driver_dlto AND d.driver_id= R.driver_id and d.companyId = " + userDetails.getCompany_id()
								+ " AND R.newDriverReminder = 0 AND d.markForDelete = 0 AND R.markForDelete = 0 ORDER BY R.driver_remid desc ");
			}
			
			List<Object[]> results = queryt.getResultList();
			List<DriverReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverReminderDto>();
				DriverReminderDto list = null;
				for (Object[] result : results) {
					list = new DriverReminderDto();

					list.setDriver_id((Integer) result[0]);
					list.setDriver_firstname((String) result[1]);
					list.setDriver_Lastname((String) result[2]);
					list.setDriver_empnumber((String) result[3]);
					list.setDriver_remid((Integer) result[4]);
					list.setDriver_remindertype((String) result[5]);
					list.setDriver_dlnumber((String) result[6]);
					list.setDriver_dlfrom((Date) result[7]);
					list.setDriver_dlto((Date) result[8]);
					list.setDriver_timethreshold((Integer) result[9]);
					list.setDriver_periedthreshold((Integer) result[10]);
					list.setDriver_renewaldate((String) result[11]);
					Dtos.add(list);
				}
			}
			return Dtos;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	@Transactional
	public List<Driver> SearchALLDriverTypeListAJAX(String Search ,String query, CustomUserDetails userDetails) throws Exception {
		try {
			List<Driver> Dtos = null;
			TypedQuery<Object[]> queryt = null;
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, d.driver_fathername  FROM Driver AS d "
								+ " where ( lower(d.driver_firstname) Like ('%" + Search
								+ "%')  OR  lower(d.driver_empnumber) Like ('%" + Search + "%') "
								+ " OR  lower(d.driver_mobnumber) Like ('%" + Search + "%') ) "+query+" AND d.companyId = "
								+ userDetails.getCompany_id() + "and d.markForDelete = 0",
						Object[].class);
			
			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Driver>();
				Driver dropdown = null;
				for (Object[] result : results) {
					dropdown = new Driver();

					dropdown.setDriver_id((Integer) result[0]);
					dropdown.setDriver_firstname((String) result[1]);
					if(result[2] != null) {
						dropdown.setDriver_Lastname((String) result[2]);
					}else {
						dropdown.setDriver_Lastname(" ");
					}
					dropdown.setDriver_empnumber((String) result[3]);
					if(result[4] != null) {
						dropdown.setDriver_fathername((String) result[4]);
					}else {
						dropdown.setDriver_fathername(" ");
					}
					Dtos.add(dropdown);
				}
			}
			}
			return Dtos;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {

		}
	}
	
	@Override
	@Transactional
	public ValueObject addDriverRenewalReceipt(ValueObject object , MultipartFile file) throws  Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		DriverReminder oldDriverReminder = null;
		DriverRenewalReceipt oldDriverRenewalReceipt = null;
		try {
			String query =" AND  D.newDriverReminder = 0 ";
			
		oldDriverReminder = getPreviousDriverReminder(object.getInt("driverId",0), object.getLong("driverRenewalTypeId",0), userDetails.getCompany_id() ,query);
		
		if(oldDriverReminder != null) {
			
			DriverRenewalReceipt driverRenewalReceipt = new DriverRenewalReceipt();
			driverRenewalReceipt.setApplicationNo(object.getString("applicationNo",""));
			driverRenewalReceipt.setReceiptNo(object.getString("receiptNo",""));
			driverRenewalReceipt.setDriver_id(object.getInt("driverId",0));
			String date =dateFormatSQL.format(dateFormat.parse(object.getString("receiptDate")));
			driverRenewalReceipt.setReceiptDate(dateFormatSQL.parse(date));
			driverRenewalReceipt.setCreatedById(userDetails.getId());
			driverRenewalReceipt.setCreated(DateTimeUtility.getCurrentDate());
			driverRenewalReceipt.setLastupdated(DateTimeUtility.getCurrentDate());
			driverRenewalReceipt.setCompanyId(userDetails.getCompany_id());
			driverRenewalReceipt.setDriverRenewalTypeId(object.getLong("driverRenewalTypeId",0));
			driverRenewalReceipt.setReminderId(oldDriverReminder.getDriver_remid());
			
			oldDriverRenewalReceipt=getPreviousReneWalReceipt(object.getInt("driverId",0), object.getLong("driverRenewalTypeId",0),  userDetails.getCompany_id());
			if(oldDriverRenewalReceipt != null)
			updateDriverRenewalReceiptStatus(oldDriverRenewalReceipt.getDriverRenewalReceiptId(), userDetails.getCompany_id());
			
			DriverRenewalReceipt savedDriverRenewalReceipt= driverRenewalReceiptRepo.save(driverRenewalReceipt);
			
			org.fleetopgroup.persistence.document.DriverDocument document =  new org.fleetopgroup.persistence.document.DriverDocument();
			if(!file.isEmpty()) {
				document.setDriver_content(file.getBytes());
				document.setDriver_contentType(file.getContentType());
				document.setDriver_documentname(file.getOriginalFilename());
				document.setDriver_filename(file.getOriginalFilename());
				document.setCompanyId(userDetails.getCompany_id());
				document.setCreatedById(userDetails.getId());
				document.setUploaddate(DateTimeUtility.getCurrentDate());
				document.setDriverRenewalReceiptId(savedDriverRenewalReceipt.getDriverRenewalReceiptId());
				driverDocumentService.deleteDriverDocumentByReciptId(savedDriverRenewalReceipt.getDriverRenewalReceiptId(),userDetails.getCompany_id());
				driverDocumentService.saveDriverDoc(document);
				
				updateDriverRenewalReceipt(document.getDriver_documentid(), savedDriverRenewalReceipt.getDriverRenewalReceiptId(), userDetails.getCompany_id());
			}
			
				updateRenewalReceiptId(savedDriverRenewalReceipt.getDriverRenewalReceiptId(), oldDriverReminder.getDriver_remid(), userDetails.getCompany_id());
				
				object.put("success", true);
		}else {
			object.put("noReminderFound", true);
			
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return object;
		
	}
	
	public void updateDriverReminderStatus() {
		
	}
	
	@Transactional
	public void updateRenewalReceiptId(Long renewalRecieptId,int driver_remid, Integer companyId) {
		driverReminderRepository.updateRenewalReceiptId(renewalRecieptId,driver_remid, companyId);
		
	}
	
	@Transactional
	public void updateDeletedRenewalReceipt(int driver_remid, Integer companyId) {
		driverReminderRepository.updateDeletedRenewalReceipt(driver_remid, companyId);
		
	}
	
	@Transactional
	public void updateDriverRenewalReceipt(long documentId,long driverRenewalReceiptId, Integer companyId) {
		driverRenewalReceiptRepo.updateDriverRenewalReceipt(documentId,driverRenewalReceiptId, companyId);
		
	}
	
			
	public ValueObject getDriverrenewalReminderCount( Integer companyId) throws Exception {
		ValueObject 			valueObject 	= null;
		long 					DriverrenewalReminder	= 0;
		TypedQuery<Object[]> queryt = null;
		try {
			valueObject 	= new ValueObject();
			queryt = entityManager.createQuery("SELECT COUNT(R.driverRenewalReceiptId), DR.driverStatusId " 
					+ " From DriverRenewalReceipt As R "  
					+" INNER JOIN Driver DR ON DR.driver_id = R.driver_id "  
					+" INNER JOIN DriverReminder DRR ON  DRR.driver_remid = R.reminderId AND DRR.markForDelete = 0 "
					+ " where  R.companyId =" + companyId + " AND " + 
					" DR.driverStatusId NOT IN("+DriverStatus.DRIVER_STATUS_INACTIVE +","+DriverStatus.DRIVER_STATUS_SUSPEND +") AND R.newReceiptStatus = 0 AND R.markForDelete = 0 AND DR.markForDelete = 0 ",Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<DriverReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverReminderDto>();
				DriverReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new DriverReminderDto();
					if(result[0] != null) {
						renewal.setDriverCount((Long) result[0]);
					}else {
						renewal.setDriverCount((long) 0);
					}
					if(result[1] != null) {
						renewal.setDriverStatusId((short) result[1]);
					}else {
						renewal.setDriverStatusId((short) 0);
					}

					DriverrenewalReminder = renewal.getDriverCount();
					Dtos.add(renewal);
				}
			}
			valueObject.put("DriverrenewalReminder", DriverrenewalReminder);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<DriverRenewalRecieptDto> listofRenewalReciept(ValueObject valueObject,CustomUserDetails userDetails) throws Exception {
		try {
			
			Query queryt = null;
			
				queryt = entityManager.createNativeQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.reminderId, DDT.dri_DocType,"
								+ "  R.applicationNo ,R.receiptNo,R.ReceiptDate ,R.documentId ,R.driverRenewalReceiptId FROM  DriverRenewalReceipt AS R "
								+ " INNER JOIN Driver AS d ON d.driver_id = R.driver_id " +valueObject.getString("driverStatusQuery", "")+""
								+ " INNER JOIN driverdoctype DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " INNER JOIN driverreminder DR ON DR.driver_remid = R.reminderId AND DR.markForDelete = 0 "
								+ " Where  d.companyId = " + userDetails.getCompany_id()
								+ " AND R.newReceiptStatus = 0 AND d.markForDelete = 0 AND R.markForDelete = 0 ORDER BY R.driverRenewalReceiptId desc ");
			
			
			List<Object[]> results = queryt.getResultList();
			List<DriverRenewalRecieptDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverRenewalRecieptDto>();
				DriverRenewalRecieptDto list = null;
				for (Object[] result : results) {
					list = new DriverRenewalRecieptDto();

					list.setDriver_id((Integer) result[0]);
					list.setDriver_firstname((String) result[1]);
					list.setDriver_Lastname((String) result[2]);
					list.setDriver_empnumber((String) result[3]);
					if(result[4] != null)
					list.setReminderId((Integer) result[4]);
					list.setDriverRenewalType((String) result[5]); 
					list.setApplicationNo((String) result[6]);
					list.setReceiptNo((String) result[7]);
					list.setReceiptDate((Date) result[8]);
					if(list.getReceiptDate() != null) {
						list.setReceiptDateEtr(dateFormat.format(list.getReceiptDate()));
					}
					list.setDocumentIdBig((BigInteger) result[9]);
					list.setDriverRenewalReceiptIdBig((BigInteger) result[10]);
					Dtos.add(list);
				}
			}
			return Dtos;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public ValueObject deleteRenewalReciept(ValueObject object) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long driverRenewalReceiptId = object.getLong("renewalReceiptId",0);
		int reminderId =object.getInt("reminderId",0);
		try {
			deleteDriverRenewalReceiptById(driverRenewalReceiptId ,userDetails.getCompany_id());
			updateDeletedRenewalReceipt(reminderId,  userDetails.getCompany_id());
			object.put("success", true);
			
			return object;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	@Transactional
	public void deleteDriverRenewalReceiptById(long driverRenewalReceiptId, int companyId ) throws Exception {
		driverRenewalReceiptRepo.deleteDriverRenewalReceipt(driverRenewalReceiptId, companyId);
	}
	
	@Transactional
	public void updateDriverRenewalReceiptStatus(long driverRenewalReceiptId, int companyId ) throws Exception {
		driverRenewalReceiptRepo.updateDriverRenewalReceiptStatus(driverRenewalReceiptId, companyId);
	}
	
	public List<DriverCommentDto> getlistDriverComment(Integer companyId,String query) {
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT R.driver_commentid, R.driver_title, R.driver_id, R.driver_comment, U.email, R.createdById, R.created "
				+ " FROM DriverComment AS R "
				+ " INNER JOIN Driver D ON D.driver_id = R.driver_id"
				+ " LEFT JOIN User U ON U.id = R.createdById"
				+ " WHERE R.companyId = "+companyId+" "+query+" AND R.markForDelete = 0 ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverCommentDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverCommentDto>();
			DriverCommentDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new DriverCommentDto();
				
				Documentto.setDriver_commentid((Long) result[0]);
				Documentto.setDriver_title((String) result[1]);
				Documentto.setDriver_id((Integer) result[2]);
				Documentto.setDriver_comment((String) result[3]);
				Documentto.setCreatedBy((String) result[4]);
				Documentto.setCreatedById((Long) result[5]);
				if(result[6] != null)
					Documentto.setCreationDate(CreatedDateTime.format(result[6]));
				
				Dtos.add(Documentto);
			}
		}
		return Dtos;
	
	}
	
	
	@Transactional
	public DriverRenewalReceipt getPreviousReneWalReceipt(int driverId,Long driverRenewalTypeId,Integer companyId) throws Exception {
		
		DriverRenewalReceipt driverReminder = null;
		try {
			TypedQuery<DriverRenewalReceipt> queryt = entityManager.createQuery(
					" select D From DriverRenewalReceipt as D where D.driver_id = "+driverId+" AND D.driverRenewalTypeId = "+driverRenewalTypeId+" AND D.newReceiptStatus = 0 AND D.companyId = "+companyId+"  AND D.markForDelete = 0 ORDER BY D.driverRenewalReceiptId DESC ",
					DriverRenewalReceipt.class);
			try {
				queryt.setMaxResults(1);
				driverReminder =queryt.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
				return driverReminder;
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addDriverHistory(DriverHistory driverHistory) {
		 driverHistoryDao.save(driverHistory);
	}
	@Override
	public ValueObject getDriverDriverWiseIssueList(ValueObject object) {
		ValueObject outObject = new ValueObject();
		try {
			Calendar aCalendar = Calendar.getInstance();
			Date currentDate =aCalendar.getTime();
			aCalendar.add(Calendar.MONTH, -3);
			aCalendar.set(Calendar.DATE, 1);
			Date fromDate =aCalendar.getTime();
			CustomUserDetails userDetails= Utility.getObject(null);
			object.put("companyId", userDetails.getCompany_id());
			String query= "AND i.ISSUES_REPORTED_DATE BETWEEN '" + dateFormat_SQL.format(fromDate) + "' AND '" + dateFormat_SQL.format(currentDate) + "'";
			object.put("query", query);	
			List<Issues> issuesList= issuesService.getDriverWiseIssueList(object);
			if(issuesList != null && !issuesList.isEmpty())
			issuesList.forEach(e->{e.setISSUESTASK(null);e.setCompanyReference(AESEncryptDecrypt.encryptBase64(""+e.getISSUES_ID()));e.setCUSTOMER_NAME(IssuesTypeConstant.getStatusName(e.getISSUES_STATUS_ID()));});
			outObject.put("issuesList", issuesList);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return outObject;
	}
	
	public ValueObject getDriverDetailsReportList(ValueObject object) {
		short statusId = object.getShort("status",(short)0);
		ExecutorService ex= Executors.newFixedThreadPool(2);
		try {
			CustomUserDetails userDetails= Utility.getObject(null);
//			String fromDate = object.getString("fromDate","");
//			String toDate = object.getString("toDate","");
			ex.execute(()->{
				StringBuilder query = new StringBuilder(" ");
//				if(!fromDate.trim().equals("") && !toDate.trim().equals(""))
//				query.append(" AND R.driverJoinDate BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
				
				if(statusId > 0) 
				query.append(" AND R.driverStatusId ="+statusId+" ");
				
			try {
					List<DriverDto> list =listDriverReport(query.toString(), userDetails);
					if(list != null) {
						List<DriverReminderDto> driverReminderDto				=	listofDriverRenewal(userDetails.getCompany_id()," AND R.newDriverReminder = 0 ");
						List<DriverRenewalRecieptDto> driverRenewalRecieptDto	=   listofRenewalReciept(object, userDetails);
						Map<Integer,DriverReminderDto> driverReminderhash= driverReminderDto.parallelStream().collect(Collectors.toMap(DriverReminderDto::getDriver_id, Function.identity(),(e,e1)->{if(e.getDriver_remindertype().contains("DL"))return e;else return	e1;}));
						Map<Integer,DriverRenewalRecieptDto> driverReceipt= driverRenewalRecieptDto.parallelStream().collect(Collectors.toMap(DriverRenewalRecieptDto::getDriver_id, Function.identity(),(e,e1)->e));
						prepareDriverBl(list, driverReminderhash, driverReceipt);
						int renewalStatus =object.getInt("renwalStatus", 0);
						if (list != null && renewalStatus > 0) {
							Predicate<DriverDto> predicate;
							if (renewalStatus == 1)
								predicate = e -> e.getDlStatus().equalsIgnoreCase("ACTIVE");
							else if (renewalStatus == 2)
								predicate = e -> e.getDlStatus().equalsIgnoreCase("Due Soon");
							else if (renewalStatus == 3)
								predicate = e -> e.getDlStatus().equalsIgnoreCase("OverDue");
							else
								predicate = e -> e.getDlStatus().equalsIgnoreCase("Renewal Receipt");
							list = list.stream().filter(predicate).collect(Collectors.toList());
						}
					}
					object.put("list", list);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			ex.execute(()->{
				ValueObject tableConfig				= new ValueObject();
				try {
					tableConfig.put("companyId", userDetails.getCompany_id());
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.DRIVER_DETAILS_REPORT);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());	
				} catch (Exception e) {
					e.printStackTrace();
				}

			});
		
			ex.shutdown();
			ex.awaitTermination(17, TimeUnit.SECONDS);
		}catch(Exception e) {
			e.printStackTrace();		
		}finally {
			ex.shutdownNow();
		}
		return object;
	}
	
	
	public List<DriverReminderDto> listofDriverRenewal(int companyId,String query) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
								+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate"
								+ ",d.driver_authorised,d.driver_badgenumber,d.driverStatusId "
								+ " ,d.driver_address,d.driver_homenumber,d.driver_mobnumber ,RR.receiptNo,RR.ReceiptDate,RR.documentId,d.driver_fathername,RR.driverRenewalReceiptId"
								+ " FROM  DriverReminder AS R "
								+ " INNER JOIN Driver AS d ON d.driver_id = R.driver_id "
								+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " LEFT JOIN DriverRenewalReceipt RR ON RR.driverRenewalReceiptId = R.renewalRecieptId"
								+ " Where R.companyId = " + companyId
								+ " "+query+" and d.markForDelete = 0 AND R.markForDelete = 0 ORDER BY R.driver_id desc ",Object[].class);
			List<Object[]> results = queryt.getResultList();
			List<DriverReminderDto> dtos = null;
			if (results != null && !results.isEmpty()) {
				dtos = new ArrayList<>();
				DriverReminderDto list = null;
				for (Object[] result : results) {
					list = new DriverReminderDto();
					list.setDriver_id((Integer) result[0]);
					list.setDriver_firstname((String) result[1]);
					list.setFullName(list.getDriver_firstname());
					if(result[2] != null && !((String)result[2]).trim().equals("")) {
						list.setDriver_Lastname((String) result[2]);
						list.setFullName(list.getFullName()+" "+result[2]);
					}
					list.setDriver_empnumber((String) result[3]);
					list.setDriver_remid((Integer) result[4]);
					list.setDriver_remindertype((String) result[5]);
					list.setDriver_dlnumber((String) result[6]);
					list.setDriver_dlfrom((Date) result[7]);
					if(result[7] != null)
					list.setDriver_dlfrom_show(dateFormat.format(result[7]));
					list.setDriver_dlto((Date) result[8]);
					if(result[8] != null)
					list.setDriver_dlto_show(dateFormat.format(result[8]));
					list.setDriver_timethreshold((Integer) result[9]);
					list.setDriver_periedthreshold((Integer) result[10]);
					list.setDriver_renewaldate((String) result[11]);
					list.setDriverAuthorised((String) result[12]);
					list.setDrivedBadgeNo((String) result[13]);
					list.setDriverStatusId((short) result[14]);
					list.setDriverAddress((String) result[15]);
					list.setHomeNumber((String) result[16]);
					list.setMobileNumber((String) result[17]);
					list.setReceiptNumber((String) result[18]);
					if(result[19] != null)
					list.setReceiptDate(dateFormat.format(result[19]));
					list.setReceiptId((Long) result[20]);
					if(result[21] != null && !((String)result[21]).trim().equals("")) 
						list.setFullName(list.getFullName()+"- "+result[21]);
					list.setDriverRenewalReceiptId((Long) result[22]);
					
					dtos.add(list);
				}
			}
			return dtos;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public HashMap<String,String> getFromAndToDateLast3Months(){
		HashMap<String,String> hash = new HashMap<>();
		
		Calendar aCalendar = Calendar.getInstance();
		Date currentDate =aCalendar.getTime();
		aCalendar.add(Calendar.MONTH, -3);
		aCalendar.set(Calendar.DATE, 1);
		Date fromDate =aCalendar.getTime();
		hash.put("fromDate", dateFormat_SQL.format(fromDate));
		hash.put("currentDate", dateFormat_SQL.format(currentDate));
		return hash;
		
	}
	public ValueObject getDriverCommentsList(ValueObject object) {
		ValueObject outObject = new ValueObject();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			HashMap<String,String> dateHash=getFromAndToDateLast3Months();
			String query =" AND R.driver_id= "+object.getInt("driverId",0)+" AND R.created BETWEEN '"+dateHash.get("fromDate")+"' AND '"+dateHash.get("currentDate")+"'";
			List<DriverCommentDto> list=getlistDriverComment(userDetails.getCompany_id(),query);
			outObject.put("list",list);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			object=null;
		}
		return outObject;
	}
	@Override
	public ValueObject getDriverFuelMileage(ValueObject object) {
		CustomUserDetails userDetails = Utility.getObject(null);
		HashMap<String,String> dateHash = null;
		try {
		dateHash=getFromAndToDateLast3Months();
		String query =" AND f.fuel_date BETWEEN '"+dateHash.get("fromDate")+"' AND '"+dateHash.get("currentDate")+"' ";
		List<Fuel> fuelList =fuelService.getDriverWiseFuelList(object.getInt("driverId",0), query, userDetails.getCompany_id());
			object.put("list", fuelList);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dateHash = null;
			userDetails=null;
		}
		return object;
	}
	public void prepareDriverBl(List<DriverDto> dto,Map<Integer,DriverReminderDto> driverReminderhash,Map<Integer,DriverRenewalRecieptDto>renewalRecieptHash) {
		LocalDate localCurrentDate = LocalDate.now();
		try {
		dto.forEach(e->{
			DriverReminderDto driverReminderDto = null;
			DriverRenewalRecieptDto renewalRecieptDto = null;
			if(driverReminderhash != null)
			 driverReminderDto =driverReminderhash.get(e.getDriver_id());
			if(driverReminderhash != null)
			 renewalRecieptDto=renewalRecieptHash.get(e.getDriver_id());

			e.setDriverFullName("<a href=\"showDriver.in?driver_id="+e.getDriver_id()+"\" target=\"_blank\"><span > "+e.getDriverFullName()+"</span></a>");
			if(renewalRecieptDto != null) {
				e.setDlStatus("Renewal Receipt");
				e.setDlImageLink("<a href=\"download/driverDocument/"+renewalRecieptDto.getDocumentIdBig()+".in\" target=\"_blank\"><span class=\"fa fa-download\"> </span></a>");
			}
			else if(driverReminderDto != null) {
				LocalDate dlToDate=driverReminderDto.getDriver_dlto().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate beforeDate=dlToDate.minusDays(driverReminderDto.getDriver_timethreshold());
				if(dlToDate.isBefore(localCurrentDate)){
				e.setDlStatus("OverDue");	
				}else if(!localCurrentDate.isBefore(beforeDate) && !localCurrentDate.isAfter(dlToDate)) {
					e.setDlStatus("Due Soon");	
				}else {
					e.setDlStatus("Active");	
				}
				e.setDlImageLink("<a href=\"download/driverReminder/"+driverReminderDto.getDriver_remid()+".in\" target=\"_blank\"><span class=\"fa fa-download\"> </span></a>");
			}else {
				e.setDlStatus("-");
			}
		});
		} catch (Exception e2) {
		e2.printStackTrace();
		}
		
	}
	
	@Override
	public ValueObject getDriverRenewalReminderReport(ValueObject object) {
		
		ExecutorService ex= Executors.newFixedThreadPool(2);
		try {
			ex.execute(()->prepareRenewalRemidner(object));
			ex.execute(()->{
				ValueObject tableConfig				= new ValueObject();
				try {
					tableConfig.put("companyId", object.getInt("companyId"));
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.DRIVER_RENEWAL_REMINDER_REPORT);
					tableConfig			= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig			= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());	
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		
			ex.shutdown();
			ex.awaitTermination(17, TimeUnit.SECONDS);
		}catch(Exception e) {
			e.printStackTrace();		
		}finally {
			ex.shutdownNow();
		}
		return object;
	}
	
	public void prepareRenewalRemidner(ValueObject object) {
		StringBuilder query = new StringBuilder(" ");
		String fromDate = object.getString("fromDate","");
		String toDate = object.getString("toDate","");
		short statusId = object.getShort("status",(short)0);
		query.append(" AND (R.newDriverReminder = 0 OR R.renewalRecieptId > 0) ");  
		
		if(!fromDate.trim().equals("") && !toDate.trim().equals(""))
		query.append(" AND R.driver_dlto BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		if(statusId > 0) 
		query.append(" AND d.driverStatusId ="+statusId+" ");
		
	try {	
				LocalDate localCurrentDate = LocalDate.now();
				List<DriverReminderDto> driverReminderDto				=	listofDriverRenewal(object.getInt("companyId",0),query.toString());
				
				if(driverReminderDto != null) {
					driverReminderDto.forEach(e->{
						e.setFullName("<a href=\"ShowDriverReminder.in?driver_id="+e.getDriver_id()+"\" target=\"_blank\" style=\"color:blue;\"><span > "+e.getFullName()+"</span></a>");
						e.setDriver_filename("<a href=\"download/driverReminder/"+e.getDriver_remid()+".in\" target=\"_blank\"><span class=\"fa fa-download\"> </span></a>");
						try {
							e.setDriverStatus(DriverStatus.getDriverStatus(e.getDriverStatusId()));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						LocalDate dlToDate		= DateTimeUtility.convertToLocalDate(e.getDriver_dlto()); 
						LocalDate beforeDate	= dlToDate.minusDays(e.getDriver_timethreshold());
						if(e.getDriverRenewalReceiptId() != null && e.getDriverRenewalReceiptId()> 0) {
							e.setDlStatus("Renewal Receipt");
							e.setReceiptDoc("<a href=\"download/driverDocument/"+e.getReceiptId()+".in\" target=\"_blank\"><span class=\"fa fa-download\"> </span></a>");
						}
						else {
						if(dlToDate.isBefore(localCurrentDate))
							e.setDlStatus("OverDue");	
						else if(!localCurrentDate.isBefore(beforeDate) && !localCurrentDate.isAfter(dlToDate)) 
							e.setDlStatus("Due Soon");	
						else 
							e.setDlStatus("Active");	
						}
						
					});
					int renewalStatus =object.getInt("renwalStatus", 0);
					if (renewalStatus > 0) {
						Predicate<DriverReminderDto> predicate;
						if (renewalStatus == 1)
							predicate = e -> e.getDlStatus().equalsIgnoreCase("ACTIVE");
						else if (renewalStatus == 2)
							predicate = e -> e.getDlStatus().equalsIgnoreCase("Due Soon");
						else if (renewalStatus == 3)
							predicate = e -> e.getDlStatus().equalsIgnoreCase("OverDue");
						else
							predicate = e -> e.getDlStatus().equalsIgnoreCase("Renewal Receipt");
							driverReminderDto = driverReminderDto.stream().filter(predicate).collect(Collectors.toList());
					}
					object.put("list",driverReminderDto);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<DriverDto> getDriverBasicDetails(String query, Integer companyId) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT D.driver_id, D.driver_firstname, D.driver_Lastname,D.driver_fathername, D.driver_empnumber, "
						+ "D.driver_badgenumber,D.driver_mobnumber,D.driverStatusId,D.driver_authorised,DJT.driJobType " 
						+" FROM Driver AS D  "
						+ " INNER JOIN DriverJobType DJT  ON DJT.driJobId = D.driJobId" 
						+" Where D.markForDelete = 0 AND D.companyId = " + companyId + "" + query
						+" ORDER BY D.driver_empnumber ASC ",
						Object[].class);
		List<DriverDto> dtos = null;
		try {
			List<Object[]> results = queryt.getResultList();
			if (results != null && !results.isEmpty()) {
				dtos = new ArrayList<>();
				DriverDto list = null;
				for (Object[] result : results) {
					list = new DriverDto();
					list.setDriver_id((Integer) result[0]);
					if(result[1] != null && !((String) result[1]).trim().equals(""))
						list.setDriverFullName((String) result[1]);
					if(result[2] != null && !((String) result[2]).trim().equals(""))
						list.setDriverFullName(list.getDriverFullName()+" "+ result[2]);
					if( result[3] != null &&  !((String)result[3]).trim().equals("")) 
						list.setDriverFullName(list.getDriverFullName()+"- "+result[3]);
					list.setDriver_empnumber((String) result[4]);
					list.setDriver_badgenumber((String) result[5]);
					list.setDriver_mobnumber((String) result[6]);
					list.setDriverStatusId((short) result[7]);
					list.setDriver_active(DriverStatus.getDriverStatus(list.getDriverStatusId()));
					list.setDriver_authorised((String) result[8]);
					list.setDriver_jobtitle((String) result[9]);

					dtos.add(list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtos;
	}

	
	public ValueObject getDriverIncidentReport(ValueObject valueObject){
		CustomUserDetails userDetails=Utility.getObject(null);
		ExecutorService ex = Executors.newFixedThreadPool(5);
		StringBuilder driverStatusqueryD= new StringBuilder();
		StringBuilder driverStatusqueryDD= new StringBuilder();
		if(valueObject.getShort("status",(short) 0) > 0) {
			driverStatusqueryD.append(" AND D.driverStatusId = "+valueObject.getShort("status",(short)0)+"");
			driverStatusqueryDD.append(" AND DD.driverStatusId = "+valueObject.getShort("status",(short)0)+"");
		}
		ex.execute(()->{
			try {
				StringBuilder innerJoin = new StringBuilder(" INNER JOIN Driver DD ON DD.driver_id = R.ISSUES_DRIVER_ID LEFT JOIN User AS U ON U.id = R.CREATEDBYID  ");;
				StringBuilder issueBQuery = new StringBuilder("  R.markForDelete = 0  "); 
				issueBQuery.append(" "+driverStatusqueryDD.toString()+" AND R.ISSUES_REPORTED_DATE BETWEEN '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
				issueBQuery.append(" AND  R.COMPANY_ID ="+userDetails.getCompany_id()+" ");
				List<IssuesDto> issueList=issuesService.getIssuesActivityList(issueBQuery.toString(),innerJoin.toString());
				Map<Integer,Long> issueCountHash =null;
				Map<Integer,Long> breakDownIssueCountHash =null;
				if(issueList != null) {
					breakDownIssueCountHash =issueList.stream().filter(e-> e.getISSUES_TYPE_ID() == IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN).collect(Collectors.groupingBy(IssuesDto::getISSUES_DRIVER_ID,Collectors.counting()));
					issueCountHash =issueList.stream().filter(e-> e.getISSUES_TYPE_ID() != IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN).collect(Collectors.groupingBy(IssuesDto::getISSUES_DRIVER_ID,Collectors.counting()));
				}
				valueObject.put("issueCountHash", issueCountHash);
				valueObject.put("breakDownIssueCountHash", breakDownIssueCountHash);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		ex.execute(()->{
			try {
				StringBuilder fuelQuery = new StringBuilder();
				fuelQuery.append(" "+driverStatusqueryDD.toString()+" AND F.fuel_date between '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
				StringBuilder innerJoinF = new StringBuilder(" INNER JOIN Driver DD ON DD.driver_id = F.driver_id ");
				ValueObject fuelValueObject = new ValueObject();
				fuelValueObject.put("queryStr",fuelQuery.toString());
				fuelValueObject.put("innerJoin",innerJoinF.toString());
				fuelValueObject.put("companyId",userDetails.getCompany_id());
				List<FuelDto> fuelList=fuelReportDao.getFuelConsumptionList(fuelValueObject);
				Map<Integer,Double> milageFuelHash  =null;
				Map<Integer,Double> consumptionFuelHash=null;
				if(fuelList != null) {
					milageFuelHash       =fuelList.stream().collect(Collectors.groupingBy(FuelDto::getDriver_id,Collectors.averagingDouble(FuelDto::getFuel_kml)));
					consumptionFuelHash  =fuelList.stream().collect(Collectors.groupingBy(FuelDto::getDriver_id,Collectors.summingDouble(FuelDto::getFuel_liters)));
				}
				valueObject.put("milageFuelHash", milageFuelHash);
				valueObject.put("consumptionFuelHash", consumptionFuelHash);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		ex.execute(()->{
			try {
				StringBuilder accidentQuery = new StringBuilder(); 
				accidentQuery.append(" "+driverStatusqueryD.toString()+" AND UE.accidentDateTime between '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
				accidentQuery.append(" AND UE.companyId ="+userDetails.getCompany_id()+" ");
				List<VehicleAccidentDetailsDto> accidentList=accidentDetailsService.getVehicleAccidentDetailsDtoList(accidentQuery.toString());	
				Map<Integer,Long>accCountHash=null;
				if(accidentList != null)		
					accCountHash=accidentList.parallelStream().collect(Collectors.groupingBy(VehicleAccidentDetailsDto::getDriverId,Collectors.counting()));
				valueObject.put("accCountHash", accCountHash);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		ex.execute(()->{
			try {
				StringBuilder commentList= new StringBuilder();
				commentList.append(" "+driverStatusqueryD.toString()+" AND R.created between '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
				List<DriverCommentDto> driverCommentDto=getlistDriverComment(userDetails.getCompany_id(), commentList.toString());
				Map<Integer,Long> driverCommentHash=null;
				if(driverCommentDto != null)
					driverCommentHash=driverCommentDto.stream().collect(Collectors.groupingBy(DriverCommentDto::getDriver_id,Collectors.counting()));
				valueObject.put("driverCommentHash", driverCommentHash);
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		ex.execute(()->{	
			try {
				List<DriverDto> driverList=getDriverBasicDetails(""+driverStatusqueryD.toString()+"", userDetails.getCompany_id());
				if(driverList != null)
					driverList=driverList.stream().filter(e->e.getDriver_jobtitle().trim().equalsIgnoreCase("DRIVER")).collect(Collectors.toList());
				valueObject.put("driverList", driverList);
			}catch(Exception e) {
				e.printStackTrace();
			}
		});

		ex.shutdown();

		try {
			ex.awaitTermination(17, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			ex.shutdownNow();
		}
		return getDriverIncidentReportDate(valueObject);
	}

	@SuppressWarnings("unchecked")
	public ValueObject getDriverIncidentReportDate(ValueObject object) {
		ExecutorService ex =Executors.newFixedThreadPool(2);
		ValueObject finalValueObject = new ValueObject();
		ex.execute(()->{
			List<DriverDto> driverList ;
			try {
				Map<Integer, Long> driverCommentHash= (Map<Integer, Long>) object.get("driverCommentHash");
				Map<Integer,Long>accCountHash				=(Map<Integer, Long>) object.get("accCountHash");
				Map<Integer,Double> milageFuelHash  		=(Map<Integer, Double>) object.get("milageFuelHash");
				Map<Integer,Double> consumptionFuelHash		=(Map<Integer, Double>) object.get("consumptionFuelHash");
				Map<Integer,Long> issueCountHash 			=(Map<Integer, Long>) object.get("issueCountHash");
				Map<Integer,Long> breakDownIssueCountHash 	=(Map<Integer, Long>) object.get("breakDownIssueCountHash");

				driverList					= (List<DriverDto>) object.get("driverList");
				if(driverList != null)
					driverList.forEach(e->{
						if(driverCommentHash != null && driverCommentHash.get(e.getDriver_id()) != null)
							e.setCommentTotal("<a style=\"color:blue\" href=\"javascript:void(0)\" onclick=\"getDriverWiseIncidentDetails('COMMENT',"+e.getDriver_id()+",'"+e.getDriverFullName()+"');\" >"+driverCommentHash.get(e.getDriver_id())+"</a>");
						else
							e.setCommentTotal("0");
						if(accCountHash != null && accCountHash.get(e.getDriver_id()) != null)
							e.setAccidentTotal("<a style=\"color:blue\" href=\"javascript:void(0)\" onclick=\"getDriverWiseIncidentDetails('ACCIDENT',"+e.getDriver_id()+",'"+e.getDriverFullName()+"');\" >"+accCountHash.get(e.getDriver_id())+"</a>");
						else
							e.setAccidentTotal("0");
						if(milageFuelHash != null && milageFuelHash.get(e.getDriver_id()) != null)
							e.setMileageFuel("<a style=\"color:blue\" href=\"javascript:void(0)\" onclick=\"getDriverWiseIncidentDetails('FUEL',"+e.getDriver_id()+",'"+e.getDriverFullName()+"');\" >"+milageFuelHash.get(e.getDriver_id())+"</a>");
						else
							e.setMileageFuel("0");
						if(consumptionFuelHash != null && consumptionFuelHash.get(e.getDriver_id()) != null)
							e.setConsumptionFuel("<a style=\"color:blue\" href=\"javascript:void(0)\" onclick=\"getDriverWiseIncidentDetails('FUEL',"+e.getDriver_id()+",'"+e.getDriverFullName()+"');\" >"+consumptionFuelHash.get(e.getDriver_id())+"</a>");
						else
							e.setConsumptionFuel("0");
						if(issueCountHash != null && issueCountHash.get(e.getDriver_id()) != null)
							e.setIssueTotal("<a style=\"color:blue\" href=\"javascript:void(0)\" onclick=\"getDriverWiseIncidentDetails('ISSUE',"+e.getDriver_id()+",'"+e.getDriverFullName()+"');\" >"+issueCountHash.get(e.getDriver_id())+"</a>");
						else
							e.setIssueTotal("0");
						if(breakDownIssueCountHash != null && breakDownIssueCountHash.get(e.getDriver_id()) != null)
							e.setBreakDownTotal("<a style=\"color:blue\" href=\"javascript:void(0)\" onclick=\"getDriverWiseIncidentDetails('BREAKDOWN',"+e.getDriver_id()+",'"+e.getDriverFullName()+"');\" >"+breakDownIssueCountHash.get(e.getDriver_id())+"</a>");
						else
							e.setBreakDownTotal("0");
					});
				finalValueObject.put("list", driverList);
			} catch (Exception e1) {
				e1.printStackTrace();
			}	
		});

		ex.execute(()->{

			ValueObject tableConfig				= new ValueObject();
			try {
				tableConfig.put("companyId", object.getInt("companyId"));
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.DRIVER_INCIDENT_REPORT);
				tableConfig			= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig			= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				finalValueObject.put("tableConfig", tableConfig.getHtData());	
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		ex.shutdown();
		try {
			ex.awaitTermination(17, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			ex.shutdownNow();
		}
		return finalValueObject;
	}

	public ValueObject getDriverWiseIncidentDetails(ValueObject valueObject) {
		ValueObject outObject = new ValueObject();
		StringBuilder driverStatusqueryD= new StringBuilder(" AND D.driver_id = "+valueObject.getInt("driverId",0)+" ");
		StringBuilder driverStatusqueryDD= new StringBuilder(" AND DD.driver_id = "+valueObject.getInt("driverId",0)+" ");
		String incidentType;
		try {
			incidentType = valueObject.getString("incidentType");

			if(type.ACCIDENT.name().equalsIgnoreCase(incidentType)){
				StringBuilder accidentQuery = new StringBuilder(); 
				accidentQuery.append(" "+driverStatusqueryD.toString()+" AND UE.accidentDateTime between '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
				accidentQuery.append(" AND UE.companyId ="+valueObject.getInt("companyId")+" ");
				List<VehicleAccidentDetailsDto> accidentList=accidentDetailsService.getVehicleAccidentDetailsDtoList(accidentQuery.toString());	
				outObject.put("list", accidentList);
			}else if(type.COMMENT.name().equalsIgnoreCase(incidentType)){
				StringBuilder commentList= new StringBuilder();
				commentList.append(" "+driverStatusqueryD.toString()+" AND R.created between '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
				List<DriverCommentDto> driverCommentDto=getlistDriverComment(valueObject.getInt("companyId"), commentList.toString());
				outObject.put("list", driverCommentDto);
			}else if (type.FUEL.name().equalsIgnoreCase(incidentType)){
				StringBuilder fuelQuery = new StringBuilder();
				fuelQuery.append(" "+driverStatusqueryDD.toString()+" AND F.fuel_date between '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
				StringBuilder innerJoinF = new StringBuilder(" INNER JOIN Driver DD ON DD.driver_id = F.driver_id ");
				ValueObject fuelValueObject = new ValueObject();
				fuelValueObject.put("queryStr",fuelQuery.toString());
				fuelValueObject.put("innerJoin",innerJoinF.toString());
				fuelValueObject.put("companyId",valueObject.getInt("companyId"));
				List<FuelDto> fuelList=fuelReportDao.getFuelConsumptionList(fuelValueObject);
				outObject.put("list", fuelList);
			}else if (type.ISSUE.name().equalsIgnoreCase(incidentType) || type.BREAKDOWN.name().equalsIgnoreCase(incidentType) ){
				StringBuilder innerJoin = new StringBuilder(" INNER JOIN Driver DD ON DD.driver_id = R.ISSUES_DRIVER_ID LEFT JOIN User AS U ON U.id = R.CREATEDBYID  ");;
				StringBuilder issueBQuery = new StringBuilder("  R.markForDelete = 0  "); 
				issueBQuery.append(" "+driverStatusqueryDD.toString()+" AND R.ISSUES_REPORTED_DATE BETWEEN '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
				issueBQuery.append(" AND  R.COMPANY_ID ="+valueObject.getInt("companyId")+" ");
				List<IssuesDto> issueList=issuesService.getIssuesActivityList(issueBQuery.toString(),innerJoin.toString());
				if(issueList != null) {
					if(type.ISSUE.name().equalsIgnoreCase(incidentType))
						issueList =issueList.stream().filter(e-> e.getISSUES_TYPE_ID() != IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN).collect(Collectors.toList());
					else
						issueList =issueList.stream().filter(e-> e.getISSUES_TYPE_ID() == IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN).collect(Collectors.toList());
				}
				outObject.put("list", issueList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outObject;
	}
	
	
	
	@Override
	public ValueObject getDriverCurrentStatusForTripsheet(ValueObject object){
		CustomUserDetails	userDetails				= null;
		DriverDto           driver					= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driver = getDriverStatus(object.getInt("driverId"), userDetails.getCompany_id());
			object.put("driver", driver);
			if(driver.getDriverStatusId()>0 && driver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_TRIPROUTE && driver.getTripSheetID() != object.getLong("tripsheetId")) {
				object.put("driverIntripRoute", true);
			}
		}catch(Exception e) {
			
		}
		return object;
	}


	private DriverDto getDriverStatus(int driverId, Integer company_id) throws Exception{
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(" "
				+ "SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_fathername, d.driverStatusId, "
				+ " d.tripSheetID  FROM Driver AS d WHERE d.driver_id = "+driverId + " AND d.companyId = "+ company_id);
		Object[] driver = null;
		try {
			driver = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		DriverDto dto;
		if(driver != null) {
			dto = new DriverDto();
			dto.setDriver_id((Integer)driver[0]);
			dto.setDriver_firstname((String) driver[1]);
			dto.setDriver_Lastname((String) driver[2]);
			dto.setDriver_fathername((String)driver[3]);
			dto.setDriverStatusId((short) driver[4]);
			dto.setDriverStatusStr(DriverStatus.getDriverStatus((short)driver[4]));
			dto.setTripSheetID((Long) driver[5]);
		}else{
			return null;
		}
		return dto;
	}

	@Override
	public ValueObject getAllDriverAndTripsheetDetails(ValueObject object) {
		
		Integer					driverId					    = null;
		List<TripSheetDto> tripSheetDto                         = null;
		List<DriverSalaryAdvanceDto> driverSalaryAdvanceDto     = null;
		HashMap<String, Object>    	configuration				= null;
		CustomUserDetails	userDetails			        	    = null;
		List<DriverSalaryReportDto>  driverSalaryReportDtoList  = null;
		DriverSalaryReportDto driverSalaryReportDto             = null;
		ValueObject tableConfig                                 = null;
		HashMap<String, Object>    	config      				= null;
		String dateRangeFrom = "", dateRangeTo = "";
	        String driverFullName = null;
	        String empNumber = null;
	        boolean initialized = false;
	        Double total_DA    = 0.0;

		try {
			
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration					= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			config                          = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);		
			
			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.DRIVER_SALARY_REPORT_DATA_FILE_PATH);

			tableConfig				= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig				= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
			
			driverId = object.getInt("driverId");
			String[] dateRange  = object.getString("dateRange").split("to"); 
			dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(dateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(dateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			String driver_Id = "";
			String closedDate_Range = "";
			String query = "";
			
			if(driverId > 0) {
				 driver_Id = " AND T.tripFristDriverID = " +driverId+ " ";
			 }

			 closedDate_Range = " ( T.closedByTime BETWEEN '" + dateRangeFrom + "' AND '" + dateRangeTo + "' )";

			 query += driver_Id + " AND ( " + closedDate_Range + " )";
			
			 tripSheetDto = getDriverSalaryReportList(query);
			
			 List<Integer> driverIdList = new ArrayList<Integer>();

				 for(TripSheetDto tripSheetDtoObject : tripSheetDto) {
					 driverIdList.add(tripSheetDtoObject.getTripFristDriverID());	
				 }

			 
			 
			 driverIdList = new ArrayList<>(new HashSet<>(driverIdList));
			 driverSalaryReportDtoList = new ArrayList<DriverSalaryReportDto>();
			
			 for(Integer finaldriverIdList : driverIdList) {
			
				 Integer countSingleTrip    = 0;
				 Integer countRoundTrip     = 0;
				 Integer totalTripCount     = 0;
				 Double singleTripSalary   = 0.0;
				 Double roundTripSalary    = 0.0;
				 Double totalSalary = 0.0;
				 Double netSalary = 0.0;
				 Double da = 0.0;
				 Double advance = 0.0;
				 Double penalty = 0.0;
				 Integer DA_Id  = 0;
				 
				 for(TripSheetDto tripsheet : tripSheetDto) {

					 if(tripsheet.getTripFristDriverID() == finaldriverIdList) {
		
							// for DA from trip sheet expense					 
						
								DA_Id = (Integer) config.get("tripSheetExpenseIdForDA");
								
								da = tripSheetExpenseRepo.getTripExpense_DA_FromTripSheet(tripsheet.getTripSheetID(),DA_Id,userDetails.getCompany_id());
								if (da == null) {
									da = 0.0;
								}
								total_DA = total_DA + da;
						
						 if(!initialized) {
							 driverFullName = tripsheet.getTripFristDriverName()+" "+ tripsheet.getTripFristDriverLastName()+"-"+ tripsheet.getTripFristDriverFatherName();
							 empNumber = tripsheet.getDriverEmpNumber();
							 initialized = true;					 
						 }
						 
						 if(tripsheet.getTripFristDriverRoutePoint().shortValue() == TripSheetStatus.SINGLE_TRIP_ROUTE_POINT) {
								countSingleTrip++;
						 }else if(tripsheet.getTripFristDriverRoutePoint().shortValue() == TripSheetStatus.ROUND_TRIP_ROUTE_POINT) {
								countRoundTrip++;
							}

					 }
				 }
				 
				 initialized = false;
			
			 	countSingleTrip = countSingleTrip + countRoundTrip;
				totalTripCount = countSingleTrip + countRoundTrip;
 				
 				 if(totalTripCount >= (Integer)configuration.get("goldenSlabTripCount")) {
					 singleTripSalary = (double) (countSingleTrip * Integer.parseInt((configuration.get("goldenTripSlabForSingleTrip")+"")));
					 roundTripSalary = (double) (countRoundTrip * Integer.parseInt((configuration.get("goldenTripSlabForRoundTrip")+"")));
					 totalSalary  = singleTripSalary + roundTripSalary ;
				 }else {
					 singleTripSalary = (double) (countSingleTrip * Integer.parseInt((configuration.get("silverTripSlabForSingleTrip")+"")));
					 roundTripSalary = (double) ((countRoundTrip) * Integer.parseInt((configuration.get("silverTripSlabForRoundTrip")+"")));
					 totalSalary  = singleTripSalary + roundTripSalary ;
				 }
				 
				 driverSalaryAdvanceDto = getDriverSalaryAdvanceDetails(dateRange[0],dateRange[1],finaldriverIdList);
				
				 if(driverSalaryAdvanceDto != null) {
					 for(DriverSalaryAdvanceDto driverSalary : driverSalaryAdvanceDto) {		
						 if(driverSalary.getDRIVER_ADVANCE_TYPE_ID() == DriverAdvanceConstant.DRIVER_ADVANCE_AMOUNT) {
							 advance  = advance  + driverSalary.getADVANCE_AMOUNT();
						 }
					 	if(driverSalary.getDRIVER_ADVANCE_TYPE_ID() == DriverAdvanceConstant.DRIVER_PENALTY_AMOUNT) {
							 penalty = penalty + driverSalary.getADVANCE_AMOUNT();
						 }
					 }
				 }
				
				 netSalary = totalSalary - (total_DA + advance + penalty);
			
				 driverSalaryReportDto = new DriverSalaryReportDto();
				 
				 driverSalaryReportDto.setDriverName(driverFullName);
				 driverSalaryReportDto.setDriver_empnumber(empNumber);
				 driverSalaryReportDto.setCountSingleTrip(countSingleTrip);
				 driverSalaryReportDto.setCountRoundTrip(countRoundTrip);
				 driverSalaryReportDto.setTotalTripCount(totalTripCount);
				 driverSalaryReportDto.setSingleTripSalary(singleTripSalary);
				 driverSalaryReportDto.setRoundTripSalary(roundTripSalary);
				 driverSalaryReportDto.setTotalSalary(totalSalary);
				 driverSalaryReportDto.setDa(total_DA);
				 driverSalaryReportDto.setAdvance(advance);
				 driverSalaryReportDto.setPenalty(penalty);
				 driverSalaryReportDto.setNetSalary(netSalary);
	
				 driverSalaryReportDtoList.add(driverSalaryReportDto);
				 
				 total_DA = 0.0;
				 
			 }
			 
			 object.put("driverSalaryReportDtoList", driverSalaryReportDtoList);
			 object.put("tableConfig", tableConfig.getHtData());
			 object.put("company",
						userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			} catch (Exception e) {
				e.printStackTrace();
		}
		
		return object;
	}
	
	@Override
	public List<DriverSalaryAdvanceDto> getDriverSalaryAdvanceDetails(String dateRangeFrom, String dateRangeTo, Integer driverId) {
		
		CustomUserDetails	userDetails			        	= null;
		List<DriverSalaryAdvanceDto> Dtos                   = null;
		TypedQuery<Object[]> typedQuery                     = null;
		
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(dateRangeFrom, DateTimeUtility.YYYY_MM_DD);
			dateRangeTo = DateTimeUtility.getSqlStrDateFromStrDate(dateRangeTo, DateTimeUtility.YYYY_MM_DD);
			
			typedQuery = entityManager.createQuery(
				    "SELECT DS.DRIVER_ID, DS.ADVANCE_AMOUNT, DS.DSAID, DS.DRIVER_ADVANCE_TYPE_ID " 
				        + " FROM DriverSalaryAdvance DS " 
				        + " WHERE DS.DRIVER_ID = " + driverId + " AND  DS.COMPANY_ID = " + userDetails.getCompany_id() + " " 
				        + " AND Date(DS.ADVANCE_DATE) BETWEEN '" + dateRangeFrom + "' AND '" + dateRangeTo + "' AND DS.markForDelete = 0 ",
				    Object[].class);

				List<Object[]> results = typedQuery.getResultList();
				
				if (results != null && !results.isEmpty()) {
					
					Dtos = new ArrayList<DriverSalaryAdvanceDto>();
					
					DriverSalaryAdvanceDto dto = null;
					for (Object[] result : results) {
						dto = new DriverSalaryAdvanceDto();
						dto.setDRIVER_ID((Integer) result[0]);
						dto.setADVANCE_AMOUNT((Double) result[1]);
						dto.setDSAID((Long) result[2]);
						dto.setDRIVER_ADVANCE_TYPE_ID((Short) result[3]);
						
						Dtos.add(dto);
						
					}
				}
			  } catch (Exception e) {
			}
	
			return Dtos;
	}
	
	

	@Override
	public List<TripSheetDto> getDriverSalaryReportList(String query) {
		
		CustomUserDetails	userDetails			        	= null;
		List<TripSheetDto> Dtos = null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			TypedQuery<Object[]> typedQuery = null;
			typedQuery = entityManager.createQuery(
				    "SELECT T.tripFristDriverID, T.tripFristDriverRoutePoint, T.closetripDate, T.tripSheetID, " 
				        + "T.tripStutesId, T.companyId, D.driver_id ,D.driver_firstname, D.driver_empnumber, " 
				    	+ "D.driver_Lastname, D.driver_fathername "	
				        + "FROM TripSheet T " 
				        + "INNER JOIN Driver D ON D.driver_id = T.tripFristDriverID "
				        + "WHERE T.companyId = " + userDetails.getCompany_id() + " " 
				        +  query + " AND T.markForDelete = 0 AND T.tripStutesId = " + TripSheetStatus.TRIP_STATUS_CLOSED + " ",
				    Object[].class);

				List<Object[]> results = typedQuery.getResultList();
				
				if (results != null && !results.isEmpty()) {
					
					Dtos = new ArrayList<TripSheetDto>();
					
					TripSheetDto dto = null;
					for (Object[] result : results) {
						dto = new TripSheetDto();
						dto.setTripFristDriverID((Integer) result[0]);
						dto.setTripFristDriverRoutePoint((Double) result[1]);
						dto.setTripSheetID((Long) result[3]);
						dto.setTripStutesId((Short) result[4]);
						dto.setCompanyId((Integer) result[5]);
						dto.setDriverId((Integer) result[6]);
						dto.setTripFristDriverName((String) result[7]);
						dto.setDriverEmpNumber((String) result[8]);
						dto.setTripFristDriverLastName((String) result[9]);
						dto.setTripFristDriverFatherName((String) result[10]);
					
						Dtos.add(dto);
					}
				}
			} catch (Exception e) {
			
		}
	
		return Dtos;	
	}
		
	@Transactional
	public List<DriverReminderDto> listof_Tomorrow_DL_Renewal(String currentdate) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			 LocalDate tomorrow = LocalDate.parse(currentdate).plusDays(1);
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
								+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate FROM  DriverReminder AS R, Driver AS d "
								+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " Where R.driver_dlto ='" + tomorrow
								+ "' AND d.driver_id= R.driver_id and d.companyId = " + userDetails.getCompany_id()
								+ " and d.markForDelete = 0 ORDER BY R.driver_remid desc ",
						Object[].class);
			} else {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
								+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate FROM  DriverReminder AS R, Driver AS d "
								+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " INNER JOIN VehicleGroupPermision  VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id = "
								+ userDetails.getId() + "" + "Where R.driver_dlto ='" + tomorrow
								+ "' AND d.driver_id= R.driver_id and d.companyId = " + userDetails.getCompany_id()
								+ " and d.markForDelete = 0 ORDER BY R.driver_remid desc ",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			List<DriverReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverReminderDto>();
				DriverReminderDto list = null;
				for (Object[] result : results) {
					list = new DriverReminderDto();

					list.setDriver_id((Integer) result[0]);
					list.setDriver_firstname((String) result[1]);
					list.setDriver_Lastname((String) result[2]);
					list.setDriver_empnumber((String) result[3]);
					list.setDriver_remid((Integer) result[4]);
					list.setDriver_remindertype((String) result[5]);
					list.setDriver_dlnumber((String) result[6]);
					list.setDriver_dlfrom((Date) result[7]);
					list.setDriver_dlto((Date) result[8]);
					list.setDriver_timethreshold((Integer) result[9]);
					list.setDriver_periedthreshold((Integer) result[10]);
					list.setDriver_renewaldate((String) result[11]);
					//list.setDriver_renewaldate((String) result[12]);

					Dtos.add(list);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}
	@Transactional
	public List<DriverReminderDto> listof_nextSevenDay_DL_Renewal(String currentdate) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			LocalDate next2Days = LocalDate.parse(currentdate).plusDays(2);
			LocalDate next9Days = LocalDate.parse(currentdate).plusDays(9);
			
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
								+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate FROM  DriverReminder AS R, Driver AS d "
								+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " WHERE R.driver_dlto BETWEEN '" + next2Days + "' AND '" + next9Days + "'"
								+ "' AND d.driver_id= R.driver_id and d.companyId = " + userDetails.getCompany_id()
								+ " and d.markForDelete = 0 ORDER BY R.driver_remid desc ",
						Object[].class);
			} else {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
								+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate FROM  DriverReminder AS R, Driver AS d "
								+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " INNER JOIN VehicleGroupPermision  VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id = "
								+ userDetails.getId() + ""
								+ " WHERE R.driver_dlto BETWEEN '" + next2Days + "' AND '" + next9Days + "'"
								+ " AND d.driver_id = R.driver_id and d.companyId = " + userDetails.getCompany_id()
								+ " and d.markForDelete = 0 ORDER BY R.driver_remid desc ",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			List<DriverReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverReminderDto>();
				DriverReminderDto list = null;
				for (Object[] result : results) {
					list = new DriverReminderDto();

					list.setDriver_id((Integer) result[0]);
					list.setDriver_firstname((String) result[1]);
					list.setDriver_Lastname((String) result[2]);
					list.setDriver_empnumber((String) result[3]);
					list.setDriver_remid((Integer) result[4]);
					list.setDriver_remindertype((String) result[5]);
					list.setDriver_dlnumber((String) result[6]);
					list.setDriver_dlfrom((Date) result[7]);
					list.setDriver_dlto((Date) result[8]);
					list.setDriver_timethreshold((Integer) result[9]);
					list.setDriver_periedthreshold((Integer) result[10]);
					list.setDriver_renewaldate((String) result[11]);
					//list.setDriver_renewaldate((String) result[12]);

					Dtos.add(list);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	
	@Transactional
	public Integer getDriverByDriverLicense(String driver_DLnumber) {
		return (driverDao.getDriverByDriverLicense(driver_DLnumber) != null)
	            ? driverDao.getDriverByDriverLicense(driver_DLnumber)
	            : 0;
	}
	
	@Transactional
	public List<DriverReminderDto> listof_nextFifteenDay_DL_Renewal(String currentdate) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			LocalDate next10Days = LocalDate.parse(currentdate).plusDays(10);
			LocalDate next25Days = LocalDate.parse(currentdate).plusDays(25);
			
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
								+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate FROM  DriverReminder AS R, Driver AS d "
								+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " WHERE R.driver_dlto BETWEEN '" + next10Days + "' AND '" + next25Days + "'"
								+ "' AND d.driver_id= R.driver_id and d.companyId = " + userDetails.getCompany_id()
								+ " and d.markForDelete = 0 ORDER BY R.driver_remid desc ",
						Object[].class);
			} else {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
								+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate FROM  DriverReminder AS R, Driver AS d "
								+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " INNER JOIN VehicleGroupPermision  VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id = "
								+ userDetails.getId() + ""
								+ " WHERE R.driver_dlto BETWEEN '" + next10Days + "' AND '" + next25Days + "'"
								+ " AND d.driver_id = R.driver_id and d.companyId = " + userDetails.getCompany_id()
								+ " and d.markForDelete = 0 ORDER BY R.driver_remid desc ",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			List<DriverReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverReminderDto>();
				DriverReminderDto list = null;
				for (Object[] result : results) {
					list = new DriverReminderDto();

					list.setDriver_id((Integer) result[0]);
					list.setDriver_firstname((String) result[1]);
					list.setDriver_Lastname((String) result[2]);
					list.setDriver_empnumber((String) result[3]);
					list.setDriver_remid((Integer) result[4]);
					list.setDriver_remindertype((String) result[5]);
					list.setDriver_dlnumber((String) result[6]);
					list.setDriver_dlfrom((Date) result[7]);
					list.setDriver_dlto((Date) result[8]);
					list.setDriver_timethreshold((Integer) result[9]);
					list.setDriver_periedthreshold((Integer) result[10]);
					list.setDriver_renewaldate((String) result[11]);
					//list.setDriver_renewaldate((String) result[12]);

					Dtos.add(list);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public List<DriverReminderDto> listof_nextMonthDay_DL_Renewal(String currentdate) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			LocalDate next26Days = LocalDate.parse(currentdate).plusDays(26);
			LocalDate next56Days = LocalDate.parse(currentdate).plusDays(56);
			
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
								+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate FROM  DriverReminder AS R, Driver AS d "
								+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " WHERE R.driver_dlto BETWEEN '" + next26Days + "' AND '" + next56Days + "'"
								+ "' AND d.driver_id= R.driver_id and d.companyId = " + userDetails.getCompany_id()
								+ " and d.markForDelete = 0 ORDER BY R.driver_remid desc ",
						Object[].class);
			} else {
				queryt = entityManager.createQuery(
						"SELECT d.driver_id, d.driver_firstname, d.driver_Lastname, d.driver_empnumber, R.driver_remid, DDT.dri_DocType, R.driver_dlnumber, R.driver_dlfrom, R.driver_dlto,"
								+ " R.driver_timethreshold, R.driver_periedthreshold, R.driver_renewaldate FROM  DriverReminder AS R, Driver AS d "
								+ " INNER JOIN DriverDocType DDT ON DDT.dri_id = R.driverRenewalTypeId"
								+ " INNER JOIN VehicleGroupPermision  VGP ON VGP.vg_per_id = d.vehicleGroupId and VGP.user_id = "
								+ userDetails.getId() + ""
								+ " WHERE R.driver_dlto BETWEEN '" + next26Days + "' AND '" + next56Days + "'"
								+ " AND d.driver_id = R.driver_id and d.companyId = " + userDetails.getCompany_id()
								+ " and d.markForDelete = 0 ORDER BY R.driver_remid desc ",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			List<DriverReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<DriverReminderDto>();
				DriverReminderDto list = null;
				for (Object[] result : results) {
					list = new DriverReminderDto();

					list.setDriver_id((Integer) result[0]);
					list.setDriver_firstname((String) result[1]);
					list.setDriver_Lastname((String) result[2]);
					list.setDriver_empnumber((String) result[3]);
					list.setDriver_remid((Integer) result[4]);
					list.setDriver_remindertype((String) result[5]);
					list.setDriver_dlnumber((String) result[6]);
					list.setDriver_dlfrom((Date) result[7]);
					list.setDriver_dlto((Date) result[8]);
					list.setDriver_timethreshold((Integer) result[9]);
					list.setDriver_periedthreshold((Integer) result[10]);
					list.setDriver_renewaldate((String) result[11]);
					//list.setDriver_renewaldate((String) result[12]);

					Dtos.add(list);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public Object[] countTotalDriver_AND_TomorrowRenewal() throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	    java.util.Date currentDate = new java.util.Date();
	    DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
		
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(currentDate);
	    calendar.add(Calendar.DAY_OF_YEAR, 1);
	    java.util.Date tomorrowDate = calendar.getTime();
		
		Query queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery("Select count(D.driver_id), count(DR.driver_id) from DriverReminder DR "
					+ " RIGHT JOIN Driver D ON D.driver_id = DR.driver_id and DR.driver_dlto = '" + ft.format(tomorrowDate) + "'"
					+ " where D.companyId = " + userDetails.getCompany_id() + " and D.markForDelete = 0");
		} else {
			queryt = entityManager.createQuery("Select count(D.driver_id), count(DR.driver_id) from DriverReminder DR "
					+ " RIGHT JOIN Driver D ON D.driver_id = DR.driver_id and DR.driver_dlto = '" + ft.format(tomorrowDate) + "'"
	                + " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = D.vehicleGroupId AND VGP.user_id = "
	                + userDetails.getId() + "" + " where D.companyId = " + userDetails.getCompany_id() + " and D.markForDelete = 0");
		}

		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}
	@Transactional
	public Object[] countTotalDriver_AND_nextSevenDayRenewal() throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		java.util.Date currentDate = new java.util.Date();
		DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
		
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentDate);
		currentCalendar.add(Calendar.DAY_OF_YEAR, 2);
		java.util.Date updatedCurrentDate = currentCalendar.getTime();

		// Adding 9 days to updatedCurrentDate
		Calendar currentCalendar2 = Calendar.getInstance();
		currentCalendar2.setTime(currentDate);
		currentCalendar2.add(Calendar.DAY_OF_YEAR, 9);
		java.util.Date updatedNext9DaysDate = currentCalendar2.getTime();
		
		
	    Query queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery("Select count(D.driver_id), count(DR.driver_id) from DriverReminder DR "
					 + " RIGHT JOIN Driver D ON D.driver_id = DR.driver_id and DR.driver_dlto BETWEEN '" + ft.format(updatedCurrentDate) + "' AND '" + ft.format(updatedNext9DaysDate) + "'"
		             + " where D.companyId = " + userDetails.getCompany_id() + " and D.markForDelete = 0");
		} else {
			queryt = entityManager.createQuery("Select count(D.driver_id), count(DR.driver_id) from DriverReminder DR "
					+ " RIGHT JOIN Driver D ON D.driver_id = DR.driver_id and DR.driver_dlto BETWEEN '" + ft.format(updatedCurrentDate) + "' AND '" + ft.format(updatedNext9DaysDate) + "'"
	                + " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = D.vehicleGroupId AND VGP.user_id = "
	                + userDetails.getId() + "" + " where D.companyId = " + userDetails.getCompany_id() + " and D.markForDelete = 0");
		}

		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}
	
	@Transactional
	public Object[] countTotalDriver_AND_nextFifteenDayRenewal() throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		java.util.Date currentDate = new java.util.Date();
		DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
		
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentDate);
		currentCalendar.add(Calendar.DAY_OF_YEAR, 10);
		java.util.Date updatedCurrentDate = currentCalendar.getTime();
		
		// Adding 25 days to updatedCurrentDate
		Calendar currentCalendar2 = Calendar.getInstance();
		currentCalendar2.setTime(currentDate);
		currentCalendar2.add(Calendar.DAY_OF_YEAR, 25);
		java.util.Date updatedNext25DaysDate = currentCalendar2.getTime();
		
		Query queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery("Select count(D.driver_id), count(DR.driver_id) from DriverReminder DR "
					 + " RIGHT JOIN Driver D ON D.driver_id = DR.driver_id and DR.driver_dlto BETWEEN '" + ft.format(updatedCurrentDate) + "' AND '" + ft.format(updatedNext25DaysDate) + "'"
		             + " where D.companyId = " + userDetails.getCompany_id() + " and D.markForDelete = 0");
		} else {
			queryt = entityManager.createQuery("Select count(D.driver_id), count(DR.driver_id) from DriverReminder DR "
					+ " RIGHT JOIN Driver D ON D.driver_id = DR.driver_id and DR.driver_dlto BETWEEN '" + ft.format(updatedCurrentDate) + "' AND '" + ft.format(updatedNext25DaysDate) + "'"
	                + " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = D.vehicleGroupId AND VGP.user_id = "
	                + userDetails.getId() + "" + " where D.companyId = " + userDetails.getCompany_id() + " and D.markForDelete = 0");
		}

		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}
	
		@Transactional
		public Object[] countTotalDriver_AND_nextMonthDayRenewal() throws Exception {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
		    	java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
		    
			Calendar currentCalendar = Calendar.getInstance();
			currentCalendar.setTime(currentDate);
			currentCalendar.add(Calendar.DAY_OF_YEAR, 26);
			java.util.Date updatedCurrentDate = currentCalendar.getTime();
			
			Calendar currentCalendar2 = Calendar.getInstance();
			currentCalendar2.setTime(currentDate);
			currentCalendar2.add(Calendar.DAY_OF_YEAR, 56);
			java.util.Date updatedNext56DaysDate = currentCalendar2.getTime();
			
		    Query queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery("Select count(D.driver_id), count(DR.driver_id) from DriverReminder DR "
						 + " RIGHT JOIN Driver D ON D.driver_id = DR.driver_id and DR.driver_dlto BETWEEN '" + ft.format(updatedCurrentDate) + "' AND '" + ft.format(updatedNext56DaysDate) + "'"
			             + " where D.companyId = " + userDetails.getCompany_id() + " and D.markForDelete = 0");
			} else {
				queryt = entityManager.createQuery("Select count(D.driver_id), count(DR.driver_id) from DriverReminder DR "
						+ " RIGHT JOIN Driver D ON D.driver_id = DR.driver_id and DR.driver_dlto BETWEEN '" + ft.format(updatedCurrentDate) + "' AND '" + ft.format(updatedNext56DaysDate) + "'"
		                + " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = D.vehicleGroupId AND VGP.user_id = "
		                + userDetails.getId() + "" + " where D.companyId = " + userDetails.getCompany_id() + " and D.markForDelete = 0");
			}

			Object[] count = (Object[]) queryt.getSingleResult();

			return count;
		}
}
