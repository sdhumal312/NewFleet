package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
/**
 * @author fleetop
 *
 */
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.PurchaseOrderType;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.TripSheetConfigurationConstant;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.constant.VehicleEmiPaymentStatus;
import org.fleetopgroup.constant.VehicleFuelType;
import org.fleetopgroup.constant.VehicleGPSVendorConstant;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.bl.StatusRemarkBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleModelTyreLayoutBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.dao.ServiceReminderRepository;
import org.fleetopgroup.persistence.dao.UserProfileRepository;
import org.fleetopgroup.persistence.dao.VehicleCommentRepository;
import org.fleetopgroup.persistence.dao.VehicleDocumentRepository;
import org.fleetopgroup.persistence.dao.VehicleExtraDetailsRepository;
import org.fleetopgroup.persistence.dao.VehicleHistoryRepository;
import org.fleetopgroup.persistence.dao.VehicleInspectionSheetToParameterRepository;
import org.fleetopgroup.persistence.dao.VehiclePhotoRepository;
import org.fleetopgroup.persistence.dao.VehiclePurchaseRepository;
import org.fleetopgroup.persistence.dao.VehicleRepository;
import org.fleetopgroup.persistence.dao.VehicleTypeAssignmentRepository;
import org.fleetopgroup.persistence.dao.VehicleTyreLayoutPositionRepository;
import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DealerServiceEntriesDto;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesAndWorkOrdersDto;
import org.fleetopgroup.persistence.dto.ServiceProgramSchedulesDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.SubCompanyDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.VehicleAccidentDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleCommentDto;
import org.fleetopgroup.persistence.dto.VehicleDocumentDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleEmiDetailDto;
import org.fleetopgroup.persistence.dto.VehicleEmiPaymentDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleExtraDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleHistoryDto;
import org.fleetopgroup.persistence.dto.VehicleInspctionSheetAsingmentDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionCompletionDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleModelTyreLayoutPositionDto;
import org.fleetopgroup.persistence.dto.VehiclePurchaseDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.model.RenewalReminder;
import org.fleetopgroup.persistence.model.ServiceProgramAsignmentDetails;
import org.fleetopgroup.persistence.model.ServiceProgramSchedules;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.ServiceReminderSequenceCounter;
import org.fleetopgroup.persistence.model.UploadFile;
import org.fleetopgroup.persistence.model.UserProfile;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleComment;
import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.persistence.model.VehicleExtraDetails;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.model.VehicleHistory;
import org.fleetopgroup.persistence.model.VehicleInspectionSheetToParameter;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehiclePhoto;
import org.fleetopgroup.persistence.model.VehiclePurchase;
import org.fleetopgroup.persistence.model.VehicleTypeAssignmentDetails;
import org.fleetopgroup.persistence.model.VehicleTyreLayoutPosition;
import org.fleetopgroup.persistence.report.dao.IFuelReportDao;
import org.fleetopgroup.persistence.report.dao.IVehicleDao;
import org.fleetopgroup.persistence.report.dao.VehicleCommentDao;
import org.fleetopgroup.persistence.report.dao.VehicleEmiDetailsDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDealerServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceProgramAsignmentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IServiceProgramService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.IStatusChangeRemarkService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAccidentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleExtraDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspctionSheetAsingmentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionCompletionDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionSheetService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleModelTyreLayoutService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@Service("VehicleService")
@Transactional(readOnly = true)
public class VehicleService implements IVehicleService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@PersistenceContext public 	EntityManager 	entityManager;
	
	@Autowired private MongoTemplate					mongoTemplate;
	@Autowired private VehicleRepository 				vehicleRepository;
	@Autowired private VehiclePurchaseRepository 		vehiclePurchaseRepository;
	@Autowired private VehicleDocumentRepository 		vehicleDocumentRepository;
	@Autowired private VehicleCommentRepository 		vehicleCommentRepository;
	@Autowired private VehicleCommentDao				vehicleCommentDao;
	@Autowired private VehiclePhotoRepository			vehiclePhotoRepository;
	@Autowired private VehicleEmiDetailsDao				vehicleEmi;
	@Autowired private VehicleExtraDetailsRepository	VehicleExtraDetailsRepository;
	@Autowired private ICompanyConfigurationService 	companyConfigurationService;
	@Autowired private ISequenceCounterService			sequenceCounterService;
	@Autowired private IVehicleDao						VehicleDaoImpl;
	@Autowired private IVehicleGroupService				vehicleGroupService;
	@Autowired private IUserProfileService				userProfileService;
	@Autowired private IVehicleExtraDetailsService		vehicleExtraDetailsService;
	@Autowired private IVehicleGPSDetailsService		vehicleGPSDetailsService;	
	@Autowired private ITripSheetService				tripSheetService; 
	@Autowired private IVehicleOdometerHistoryService 	vehicleOdometerHistoryService;
	@Autowired private IServiceReminderService 			ServiceReminderService;
	@Autowired private IWorkOrdersService				workOrdersService;
	@Autowired private IServiceEntriesService			serviceEntriesService;
	@Autowired private IServiceProgramService			serviceProgramService;
	@Autowired private IServiceReminderSequenceService	serviceReminderSequenceService;
	@Autowired private VehicleHistoryRepository 		vehicleHistoryRepository;
	@Autowired private	IVehicleInspctionSheetAsingmentService			asingmentService;
	@Autowired private VehicleInspectionSheetToParameterRepository		VehicleInspectionSheetToParameterRepository;
	@Autowired private IVehicleInspectionSheetService					vehicleInspectionSheetService;
	@Autowired private IServiceProgramAsignmentDetailsService	asignmentDetailsService;
	@Autowired private IVehicleModelTyreLayoutService					vehicleModelTyreLayoutService;
	@Autowired	private VehicleTyreLayoutPositionRepository 		vehicleTyreLayoutPositionRepository;
	@Autowired  IStatusChangeRemarkService 		statusRemarkService;
	@Autowired private VehicleTypeAssignmentRepository vehicleTypeAssignmentRepository;
	@Autowired         IDealerServiceEntriesService    dealerServiceEntriesService;
	@Autowired         IIssuesService 					issueService;
	@Autowired         IVehicleAccidentDetailsService    accidentDetailsService;
	@Autowired			IVehicleInspectionCompletionDetailsService inspectionCompletionDetailsService;
	@Autowired         IFuelReportDao       fuelReportDao;
	@Autowired         UserProfileRepository        userRepository;
	@Autowired ServiceReminderRepository serviceReminderRepository;
	
	
	
	VehicleBL 					VBL 		= new VehicleBL();
	VehicleOdometerHistoryBL 	VOHBL 		= new VehicleOdometerHistoryBL();
	VehicleModelTyreLayoutBL	vModelTyreLayoutBL 		= new VehicleModelTyreLayoutBL();
	SimpleDateFormat CreatedDateTime 		= new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	SimpleDateFormat dateFormat 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatSQL 			= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatSQL 	 			= new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
	DecimalFormat toFixedTwo                = new DecimalFormat("#.##");
	private static final int PAGE_SIZE = 10;

	/*
	 * @Autowired private FileUploadDAO fileUploadDao;
	 */

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Vehicle addVehicle(Vehicle vehicle) throws Exception {
		return vehicleRepository.save(vehicle);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateVehicle(Vehicle vehicle) throws Exception {
		// vehicleRepository.updateVehicle(vehicle);
		vehicleRepository.save(vehicle);
	}

	/*
	 * @Transactional(propagation = Propagation.REQUIRED, readOnly = false) public
	 * void addFileVehicle(UploadFile upload) throws Exception {
	 * fileUploadDao.save(upload); }
	 */

	@Transactional
	public List<Vehicle> listVehicleVaildateOLD(String vehicle_registration, String vehicle_chasis,
			String vehicle_engine) throws Exception {
		return vehicleRepository.listVehicleVaildateOLD(vehicle_registration, vehicle_chasis, vehicle_engine);
	}
	
	@Transactional
	public Long totalCountVehicles(int companyId) throws Exception {
	
		return vehicleRepository.totalCountVehicles(companyId);
	}
	

	/**
	 * This Page get Vehicle table to get pagination values
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Transactional
	public Page<Vehicle> getDeployment_Page_Vehicle(short Status, Integer pageNumber) throws Exception {
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "vid");
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return vehicleRepository.getDeployment_Page_Vehicle_Status(Status, userDetails.getCompany_id(),
						request);
			}
			return vehicleRepository.getDeployment_Page_Vehicle_Status(Status, userDetails.getCompany_id(),
					userDetails.getId(), request);
			

		} catch (Exception e) {
			throw e;
		}
	}

	/** This List get Vehicle table to get pagination last 10 entries values */
	@Transactional
	public List<VehicleDto> listVehicel(short Status, Integer pageNumber, CustomUserDetails userDetails)
			throws Exception {
		TypedQuery<Object[]> queryt = null;
		HashMap<String, Object> configuration = null;
		HashMap<String, Object> configuration1 = null;
		int tripSheetFlavor = 0;
		try {
			configuration1		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			/* this only Select column */
			configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			tripSheetFlavor = (int) configuration.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR);
			if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
				if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					queryt = entityManager.createQuery(
							"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer, "
									+ "R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, T.tripSheetNumber, WO.workorders_Number,R.branchId, B.branch_name FROM Vehicle AS R"
									+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId "
									+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId "
									+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
									+ " LEFT JOIN TripSheet T ON T.tripSheetID = R.tripSheetID AND R.vStatusId = "
									+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
									+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
									+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + " "
									+ " LEFT JOIN Branch AS B ON B.branch_id = R.branchId" 
									+ " WHERE R.vStatusId="
									+ Status + " and R.company_Id = " + userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.vid desc",
							Object[].class);
				} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {

					queryt = entityManager.createQuery(
							"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer, "
									+ "R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, T.TRIPCOLLNUMBER, WO.workorders_Number, R.branchId, B.branch_name FROM Vehicle AS R"
									+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId "
									+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
									+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
									+ " LEFT JOIN TripCollectionSheet T ON T.TRIPCOLLID = R.tripSheetID AND R.vStatusId = "
									+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
									+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
									+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + " "
									+ " LEFT JOIN Branch AS B ON B.branch_id = R.branchId" 
									+ " WHERE R.vStatusId="
									+ Status + " and R.company_Id = " + userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.vid desc",
							Object[].class);

				} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {

					queryt = entityManager.createQuery(
							"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer, "
									+ "R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, T.TRIPDAILYNUMBER, WO.workorders_Number,R.branchId, B.branch_name FROM Vehicle AS R"
									+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId "
									+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
									+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
									+ " LEFT JOIN TripDailySheet T ON T.TRIPDAILYID = R.tripSheetID AND R.vStatusId = "
									+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
									+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
									+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + "" 
									+ " LEFT JOIN Branch AS B ON B.branch_id = R.branchId" 
									+ " WHERE R.vStatusId="
									+ Status + " and R.company_Id = " + userDetails.getCompany_id()
									+ " and R.markForDelete = 0 ORDER BY R.vid desc",
							Object[].class);

				}
			} else {
				if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					queryt = entityManager.createQuery(
							"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer, "
									+ "R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, T.tripSheetNumber, WO.workorders_Number,R.branchId, B.branch_name FROM Vehicle AS R"
									+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId "
									+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
									+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
									+ " LEFT JOIN TripSheet T ON T.tripSheetID = R.tripSheetID AND R.vStatusId = "
									+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
									+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
									+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + ""
									+ " LEFT JOIN Branch AS B ON B.branch_id = R.branchId" 
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
									+ userDetails.getId() + "" + " WHERE R.vStatusId=" + Status + "and R.company_Id = "
									+ userDetails.getCompany_id() + " and R.markForDelete = 0 ORDER BY R.vid desc",
							Object[].class);
				} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
					queryt = entityManager.createQuery(
							"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer, "
									+ "R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, T.TRIPCOLLNUMBER, WO.workorders_Number,R.branchId, B.branch_name FROM Vehicle AS R"
									+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId "
									+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
									+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
									+ " LEFT JOIN TripCollectionSheet T ON T.TRIPCOLLID = R.tripSheetID AND R.vStatusId = "
									+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
									+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
									+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + ""
									+ " LEFT JOIN Branch AS B ON B.branch_id = R.branchId" 
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
									+ userDetails.getId() + "" + " WHERE R.vStatusId=" + Status + "and R.company_Id = "
									+ userDetails.getCompany_id() + " and R.markForDelete = 0 ORDER BY R.vid desc",
							Object[].class);

				} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
					queryt = entityManager.createQuery(
							"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer, "
									+ "R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, T.TRIPDAILYNUMBER, WO.workorders_Number,R.branchId, B.branch_name FROM Vehicle AS R"
									+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId "
									+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
									+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
									+ " LEFT JOIN TripDailySheet T ON T.TRIPDAILYID = R.tripSheetID AND R.vStatusId = "
									+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
									+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
									+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + ""
									+ " LEFT JOIN Branch AS B ON B.branch_id = R.branchId" 
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
									+ userDetails.getId() + "" + " WHERE R.vStatusId=" + Status + "and R.company_Id = "
									+ userDetails.getCompany_id() + " and R.markForDelete = 0 ORDER BY R.vid desc",
							Object[].class);
				}

			}
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<VehicleDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleDto>();
				VehicleDto list = null;
				for (Object[] result : results) {
					list = new VehicleDto();

					list.setVid((Integer) result[0]);
					list.setVehicle_registration((String) result[1]);
					list.setVehicle_Group((String) result[2]);
					list.setVehicle_maker((String) result[3]);
					list.setVehicle_Type((String) result[4]);
					if( result[5] != null) {
					list.setVehicle_Odometer((Integer) result[5]);
					}
					if((boolean)configuration1.get("branchWiseVehicleInspection") && result[12] != null ){
						if((Integer)result[12] > 0) {
							list.setVehicle_Location((String) result[13]);
						}
					}else {
						list.setVehicle_Location((String) result[6]);
					}
					list.setVehicleOwnerShipId((short) result[7]);
					list.setTripSheetID((Long) result[8]);
					list.setvStatusId((short) result[9]);
					list.setTripSheetNumber((Long) result[10]);
					list.setWorkOrder_Number((Long) result[11]);
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		} finally {
			queryt = null;
		}
	}

	@Transactional
	public List<Vehicle> findAllVehiclelist() throws Exception {
		return vehicleRepository.findAll();
	}

	@Transactional
	public Vehicle getVehicel(int vid, Integer companyId) throws Exception {
		return vehicleRepository.getVehicel(vid, companyId);
	}
	
	@Override
	public ValueObject getVehicleIdFromVehicle(ValueObject object) throws Exception {
		Vehicle								vehicle				= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicle = getVehicel(object.getInt("vid"), userDetails.getCompany_id());
			if(vehicle != null) {
				object.put("vehicle", vehicle);
				object.put("vehicleId", vehicle.getVid());
			}
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			vehicle   = null;
		}
	}

	@Transactional
	public Vehicle getVehicel1(Integer vid) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return vehicleRepository.getVehicel1(vid, userDetails.getCompany_id());

	}

	@Transactional
	public VehicleDto getVehicle_Select_TripSheet_Details(Integer vid) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT f.vid, f.vehicle_registration, VG.vGroup, TR.routeName, f.vehicle_Odometer, f.vehicleFuelId, f.vehicleGroupId, TR.routeID, f.vehicle_ExpectedOdameter, f.vehicleGPSId "
				+ " , f.vStatusId, D.driver_firstname, D.driver_Lastname, D.driver_empnumber, D.driver_id, f.gpsVendorId, f.tripSheetID, D.driver_fathername,"
				+ " f.vehicle_ExpectedMileage,f.vehicle_chasis "
				+ " from Vehicle AS f "
				+ " INNER JOIN VehicleGroup VG ON VG.gid = f.vehicleGroupId "
				+ " LEFT JOIN Driver D ON D.vid = f.vid "
				+ " LEFT JOIN DriverJobType DT ON DT.driJobId = D.driJobId AND DT.driJobType = 'Driver'"
				+ " LEFT JOIN TripRoute TR ON TR.routeID = f.routeID"
				+ " where  f.vid = :id AND f.markForDelete = 0");

		query.setParameter("id", vid);
		query.setMaxResults(1);
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		VehicleDto select;
		if (vehicle != null) {
			select = new VehicleDto();
			select.setVid((Integer) vehicle[0]);
			select.setVehicle_registration((String) vehicle[1]);
			select.setVehicle_Group((String) vehicle[2]);
			select.setVehicle_RouteName((String) vehicle[3]);
			select.setVehicle_Odometer((Integer) vehicle[4]);
			select.setVehicleFuelId((String) vehicle[5]);
			select.setVehicleGroupId((long) vehicle[6]);
			select.setRouteID((Integer) vehicle[7]);
			select.setVehicle_ExpectedOdameter((Integer) vehicle[8]);
			select.setVehicleGPSId((String) vehicle[9]);
			select.setvStatusId((short) vehicle[10]);
			select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus((short) vehicle[10]));
			select.setDriverFirstName((String) vehicle[11]);
			select.setDriverLastName((String) vehicle[12]);
			select.setDriverEmpName((String) vehicle[13]);
			select.setPartlocation_id((Integer) vehicle[14]);
			select.setGpsVendorId((Integer) vehicle[15]);
			select.setTripSheetID((Long) vehicle[16]);
			select.setDriverFatherName((String) vehicle[17]);
			select.setVehicle_ExpectedMileage((Double) vehicle[18]);
			select.setVehicle_chasis((String) vehicle[19]);

		} else {
			return null;
		}
		return select;
	}

	@Transactional
	public VehicleDto getVehicel_Details_Fuel_entries(Integer vid) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Query query = entityManager.createQuery(
				"SELECT f.vid, f.vehicle_registration, VG.vGroup, f.vehicleOwnerShipId, f.vehicle_Odometer, f.vehicleGroupId "
						+ " from Vehicle AS f" + " INNER JOIN VehicleGroup AS VG ON VG.gid = f.vehicleGroupId "
						+ " where f.vid = :id AND f.company_Id = :companyId");

		query.setParameter("id", vid);
		query.setParameter("companyId", userDetails.getCompany_id());
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		VehicleDto select;
		if (vehicle != null) {
			select = new VehicleDto();
			select.setVid((Integer) vehicle[0]);
			select.setVehicle_registration((String) vehicle[1]);
			select.setVehicle_Group((String) vehicle[2]);
			select.setVehicleOwnerShipId((short) vehicle[3]);
			select.setVehicle_Odometer((Integer) vehicle[4]);
			select.setVehicleGroupId((long) vehicle[5]);
		} else {
			return null;
		}
		return select;
	}

	@Transactional
	public VehicleDto Get_Vehicle_Header_Fuel_Entries_Details(Integer vid, int companyId) throws Exception {
		Object[] vehicle = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT f.vid, f.vehicle_registration, f.vehicle_photoid, f.vStatusId, f.vehicle_Odometer,  VT.vtype, f.Vehicle_Location, "
							+ " VG.vGroup, TR.routeName, f.vehicle_ExpectedMileage, f.vehicle_ExpectedMileage_to, f.vehicleOwnerShipId, f.vehicle_ExpectedOdameter from Vehicle AS f"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = f.routeID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = f.vehicleTypeId"
							+ " INNER JOIN VehicleGroup AS VG ON VG.gid = f.vehicleGroupId " + " where  f.vid =" + vid
							+ " AND f.company_Id = " + companyId + " AND f.markForDelete = 0 ");
			vehicle = (Object[]) query.getSingleResult();
			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_photoid((Integer) vehicle[2]);
				select.setvStatusId((short) vehicle[3]);
				select.setVehicle_Odometer((Integer) vehicle[4]);
				select.setVehicle_Type((String) vehicle[5]);
				select.setVehicle_Location((String) vehicle[6]);
				select.setVehicle_Group((String) vehicle[7]);
				select.setVehicle_RouteName((String) vehicle[8]);
				select.setVehicle_ExpectedMileage((Double) vehicle[9]);
				select.setVehicle_ExpectedMileage_to((Double) vehicle[10]);
				select.setVehicleOwnerShipId((short) vehicle[11]);
				select.setVehicle_ExpectedOdameter((Integer) vehicle[12]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public VehicleDto Get_Vehicle_Header_Fuel_ADD_Entries_Details(Integer vid, Integer companyId) throws Exception {

		Object[] vehicle = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT f.vid, f.vehicle_registration, f.vehicle_photoid, f.vStatusId, f.vehicle_Odometer,  VT.vtype, f.Vehicle_Location, "
							+ " VG.vGroup, TR.routeName, f.vehicleFuelId, f.vehicleGroupId, f.vehicleOwnerShipId, f.vehicle_ExpectedOdameter, f.vehicleGPSId from Vehicle AS f "
							+ " LEFT JOIN TripRoute TR ON TR.routeID = f.routeID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = f.vehicleTypeId"
							+ " INNER JOIN VehicleGroup AS VG ON VG.gid = f.vehicleGroupId " + " where f.vid =" + vid
							+ " AND f.company_Id = " + companyId + " ");
			vehicle = (Object[]) query.getSingleResult();
			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_photoid((Integer) vehicle[2]);
				select.setvStatusId((short) vehicle[3]);
				select.setVehicle_Odometer((Integer) vehicle[4]);
				select.setVehicle_Type((String) vehicle[5]);
				select.setVehicle_Location((String) vehicle[6]);
				select.setVehicle_Group((String) vehicle[7]);
				select.setVehicle_RouteName((String) vehicle[8]);
				select.setVehicleFuelId((String) vehicle[9]);
				select.setVehicleGroupId((long) vehicle[10]);
				select.setVehicleOwnerShipId((short) vehicle[11]);
				select.setVehicle_ExpectedOdameter((Integer) vehicle[12]);
				select.setVehicleGPSId((String) vehicle[13]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public VehicleDto getVehicel_Details_Fuel_Import(String  vehicleName, Integer companyId) throws Exception {

		Object[] vehicle = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT f.vid, f.vehicle_registration, VG.vGroup, f.vehicleOwnerShipId, f.vehicle_Odometer, f.vehicleGroupId "
							+ " from Vehicle AS f " + " INNER JOIN VehicleGroup AS VG ON VG.gid = f.vehicleGroupId "
							+ " where f.vehicle_registration ='" + vehicleName + "' AND f.company_Id = "+companyId+" AND f.markForDelete = 0");
			vehicle = (Object[]) query.getSingleResult();

			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_Group((String) vehicle[2]);
				select.setVehicleOwnerShipId((short) vehicle[3]);
				select.setVehicle_Odometer((Integer) vehicle[4]);
				select.setVehicleGroupId((long) vehicle[5]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public VehicleDto getVehicel_DropDown_Fuel_ADD_entries(Integer vid, Integer companyId) throws Exception {
		Object[] vehicle = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT f.vid, f.vehicle_registration, VG.vGroup, f.vehicleFuelId, f.vehicle_Odometer, f.vehicleGroupId, "
							+ " f.vehicle_ExpectedOdameter, f.vehicleGPSId, f.vehicle_ExpectedMileage, f.vehicle_ExpectedMileage_to, "
							+ " f.vehicle_FuelTank1 from Vehicle AS f " 
							+ " INNER JOIN VehicleGroup AS VG ON VG.gid = f.vehicleGroupId "
							+ " where f.vid = :id AND f.company_Id = :companyId AND f.markForDelete = 0");
			query.setParameter("id", vid);
			query.setParameter("companyId", companyId);
			vehicle = (Object[]) query.getSingleResult();

			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_Group((String) vehicle[2]);
				select.setVehicleFuelId((String) vehicle[3]);
				select.setVehicle_Fuel(VehicleFuelType.getVehicleFuelTypeName((String) vehicle[3]));
				select.setVehicle_Odometer((Integer) vehicle[4]);
				select.setVehicleGroupId((long) vehicle[5]);
				select.setVehicle_ExpectedOdameter((Integer) vehicle[6]);
				select.setVehicleGPSId((String) vehicle[7]);
				select.setVehicle_ExpectedMileage((double) vehicle[8]);
				select.setVehicle_ExpectedMileage_to((double) vehicle[9]);
				if(vehicle[10] != null) {
					select.setVehicle_FuelTank1((int) vehicle[10]);
				}else {
					select.setVehicle_FuelTank1(0);
				}
			}
			return select;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public VehicleDto getVehicel_DropDown_Last_Fuel_ADD_entries(Integer vid, Integer companyId) throws Exception {
		Object[] vehicle = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT f.vid, f.vehicle_registration, VG.vGroup, f.vehicleFuelId, f.vehicle_Odometer, f.vehicleGroupId, "
							+ "	f.vehicle_ExpectedOdameter, f.vehicleGPSId, fu.fuel_meter, f.vStatusId, f.gpsVendorId, "
							+ " f.vehicleGPSId, fu.fuelDateTime, fu.gpsOdometer, f.vehicle_ExpectedMileage, f.vehicle_ExpectedMileage_to, "
							+ " f.vehicle_FuelTank1,f.vehicle_chasis "
							+ " from Vehicle AS f " 
							+ " INNER JOIN VehicleGroup AS VG ON VG.gid = f.vehicleGroupId "
							+ " LEFT JOIN Fuel fu ON fu.vid = f.vid AND fu.markForDelete = 0 "
							+ " where f.vid = "+vid+" AND f.company_Id = "+companyId+" AND f.markForDelete = 0 order by fu.fuelDateTime desc");
			query.setMaxResults(1);
			vehicle = (Object[]) query.getSingleResult();
			

			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_Group((String) vehicle[2]);
				select.setVehicleFuelId((String) vehicle[3]);
				select.setVehicle_Fuel(VehicleFuelType.getVehicleFuelTypeName((String) vehicle[3]));
				select.setVehicle_Odometer((Integer) vehicle[4]);
				select.setVehicleGroupId((long) vehicle[5]);
				select.setVehicle_ExpectedOdameter((Integer) vehicle[6]);
				select.setVehicleGPSId((String) vehicle[7]);
				select.setLastFuelOdometer((Integer) vehicle[8]);
				select.setvStatusId((short) vehicle[9]);
				select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(select.getvStatusId()));
				select.setGpsVendorId((Integer) vehicle[10]);
				select.setVehicleGPSId((String) vehicle[11]);
				if(vehicle[12] != null)
					select.setFuelDateTime((Date) vehicle[12]);
				if(vehicle[13] != null)
					select.setGpsOdameter((double) vehicle[13]);
				if(vehicle[14] != null) 
					select.setVehicle_ExpectedMileage((double) vehicle[14]);
				if(vehicle[15] != null)
					select.setVehicle_ExpectedMileage_to((double) vehicle[15]);
				if(vehicle[16] != null) {
					select.setVehicle_FuelTank1((int) vehicle[16]);
				}else {
					select.setVehicle_FuelTank1(0);
				}
				select.setVehicle_chasis((String) vehicle[17]);
					
			}
			return select;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Exception : "+e);
			return null;
		}
	
	}
	
	@Override
	public VehicleDto getVehicel_DropDown_Urea_ADD_entries(Integer vid, Integer companyId) throws Exception {
		Object[] vehicle = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT f.vid, f.vehicle_registration, VG.vGroup, f.vehicleFuelId, f.vehicle_Odometer, f.vehicleGroupId, f.vehicle_ExpectedOdameter, f.vehicleGPSId,"
					+ "	fu.ureaOdometer, f.vStatusId "
							+ " from Vehicle AS f " 
							+ " INNER JOIN VehicleGroup AS VG ON VG.gid = f.vehicleGroupId "
							+ " LEFT JOIN UreaEntries fu ON fu.vid = f.vid AND fu.markForDelete = 0"
							+ " where f.vid = :id AND f.company_Id = :companyId AND f.markForDelete = 0 order by fu.ureaEntriesId desc");
			query.setParameter("id", vid);
			query.setParameter("companyId", companyId);
			query.setMaxResults(1);
			vehicle = (Object[]) query.getSingleResult();
			
			

			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_Group((String) vehicle[2]);
				select.setVehicleFuelId((String) vehicle[3]);
				select.setVehicle_Fuel(VehicleFuelType.getVehicleFuelTypeName((String) vehicle[3]));
				select.setVehicle_Odometer((Integer) vehicle[4]);
				select.setVehicleGroupId((long) vehicle[5]);
				select.setVehicle_ExpectedOdameter((Integer) vehicle[6]);
				select.setVehicleGPSId((String) vehicle[7]);
				select.setLastUreaOdometer((Double) vehicle[8]);
				select.setvStatusId((short) vehicle[9]);
				select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(select.getvStatusId()));
				
			}
			return select;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public VehicleDto Get_Vehicle_Header_Details(Integer vehicle_ID) throws Exception {
		Object[] vehicle = null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Query query = entityManager.createQuery(
					"SELECT R.vid, R.vehicle_registration, R.vehicle_photoid, R.vStatusId, R.vehicle_Odometer,  VT.vtype, "
							+ "R.Vehicle_Location, VG.vGroup, TR.routeName, R.vehicleGroupId, R.driverMonthlySalary, VO.VEH_OWNER_NAME,"
							+ " VO.VEH_OWNER_ADDRESS, R.driverMonthlyBhatta"
							+ " from Vehicle AS R "
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.routeID "
							+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId "
							+ " LEFT JOIN VehicleOwner VO ON VO.VEHID = R.vid "
							+ " INNER JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId " + " where R.vid = :id "
							+ " and R.company_Id = " + userDetails.getCompany_id() + " and R.markForDelete = 0 AND R.vStatusId IN ("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE +","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE +","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP +","+VehicleStatusConstant.VEHICLE_STATUS_ACCIDENT+")");
			query.setParameter("id", vehicle_ID);
			query.setMaxResults(1);
			vehicle = (Object[]) query.getSingleResult();
			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_photoid((Integer) vehicle[2]);
				select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus((short) vehicle[3]));
				select.setVehicle_Odometer((Integer) vehicle[4]);
				select.setVehicle_Type((String) vehicle[5]);
				select.setVehicle_Location((String) vehicle[6]);
				select.setVehicle_Group((String) vehicle[7]);
				select.setVehicle_RouteName((String) vehicle[8]);
				select.setVehicleGroupId((long) vehicle[9]);
				if(vehicle[10] != null)
				select.setDriverMonthlySalary((Double) vehicle[10]);
				select.setVehicle_Ownership((String) vehicle[11]);
				select.setCompanyName((String) vehicle[12]);
				
				if(vehicle[13]!=null) {
					select.setDriverMonthlyBhatta(vehicle[13]+"");
				}else {
					select.setDriverMonthlyBhatta("0.0");
				}
			}
			return select;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public Vehicle getVehicle_Details_Renewal_Reminder_Entries(Integer vid) throws Exception {
		Object[] vehicle = null;
		try {
			Query query = entityManager
					.createQuery("SELECT f.vid, f.vehicle_registration from Vehicle AS f where f.vid = :id");
			query.setParameter("id", vid);
			vehicle = (Object[]) query.getSingleResult();
			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			Vehicle select = new Vehicle();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public VehicleDto getVehicle_Details_Service_Entries(Integer vid) throws Exception {

		Object[] vehicle = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT f.vid, f.vehicle_registration, VG.vGroup, f.vehicleGroupId from Vehicle AS f "
							+ " INNER JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId " + " where f.vid = :id");
			query.setParameter("id", vid);
			vehicle = (Object[]) query.getSingleResult();
			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_Group((String) vehicle[2]);
				select.setVehicleGroupId((long) vehicle[3]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public VehicleDto getVehicle_Details_Service_Reminder_Entries(Integer vehicle_ID, Integer companyId)
			throws Exception {
		Object[] vehicle = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT f.vid, f.vehicle_registration, VG.vGroup, f.vehicle_Odometer, f.vehicleGroupId from Vehicle AS f"
							+ " INNER JOIN VehicleGroup AS VG ON VG.gid = f.vehicleGroupId "
							+ " where f.vid = :id AND f.company_Id = :companyId AND f.markForDelete = 0");
			query.setParameter("id", vehicle_ID);
			query.setParameter("companyId", companyId);
			vehicle = (Object[]) query.getSingleResult();
			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_Group((String) vehicle[2]);
				select.setVehicle_Odometer((Integer) vehicle[3]);
				select.setVehicleGroupId((long) vehicle[4]);
			}
			return select;
		} catch (Exception e) {
			System.err.println("inside catch : " + e);
			return null;
		}
	}

	@Transactional
	public VehicleDto getVehicle_Details_TripSheet_Entries(Integer vehicle_ID) throws Exception {
		Object[] vehicle = null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Query query = entityManager
					.createQuery("SELECT f.vid, f.vehicle_registration, VG.vGroup, f.vehicleGroupId from Vehicle AS f"
							+ " INNER JOIN VehicleGroup AS VG ON VG.gid = f.vehicleGroupId "
							+ " where f.vid = :id AND f.company_Id = :company_Id AND f.markForDelete = 0");
			query.setParameter("id", vehicle_ID);
			query.setParameter("company_Id", userDetails.getCompany_id());
			vehicle = (Object[]) query.getSingleResult();
			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_Group((String) vehicle[2]);
				select.setVehicleGroupId((long) vehicle[3]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public Vehicle getVehicle_Details_WorkOrder_Entries(Integer vehicle_ID) throws Exception {
		Object[] vehicle = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT f.vid, f.vehicle_registration, f.vehicle_Odometer, f.vStatusId from Vehicle AS f"
					+ " where f.vid = :id");
			query.setParameter("id", vehicle_ID);
			vehicle = (Object[]) query.getSingleResult();
			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			Vehicle select = new Vehicle();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_Odometer((Integer) vehicle[2]);
				select.setvStatusId((short) vehicle[3]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public Vehicle getVehicle_DropDown_Issues_ADD_entries(Integer vid) throws Exception {
		Object[] vehicle = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT  f.vid, f.vehicle_registration, f.vehicle_Odometer, f.vehicle_ExpectedOdameter, f.vehicleGPSId from Vehicle AS f where f.vid = :id");
			query.setParameter("id", vid);
			vehicle = (Object[]) query.getSingleResult();
			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			Vehicle select = new Vehicle();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_Odometer((Integer) vehicle[2]);
				select.setVehicle_ExpectedOdameter((Integer) vehicle[3]);
				select.setVehicleGPSId((String) vehicle[4]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			System.err.println("Exception "+e);
			return null;
		}
	}

	@Transactional
	public VehicleDto getVehicle_Details_Issues_entries(Integer vid) throws Exception {
		Object[] vehicle = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT f.vid, f.vehicle_registration, VG.vGroup  from Vehicle AS f "
					+ " INNER JOIN VehicleGroup AS VG ON VG.gid = f.vehicleGroupId "
					+ " where f.vid = :id");
			query.setParameter("id", vid);
			vehicle = (Object[]) query.getSingleResult();
			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_Group((String) vehicle[2]);
			}
			return select;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public Vehicle getVehicelReg(String vehicle_register) throws Exception {
		return vehicleRepository.getVehicelReg(vehicle_register);
	}

	@Transactional
	public void deleteVehicle(Integer vehicle_ID,Long userId,Timestamp toDate, Integer companyId) throws Exception {
		vehicleRepository.deleteVehicle(vehicle_ID,userId,toDate, companyId);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addVehicleDocument(VehicleDocument vehicleDocument) throws Exception {
		vehicleDocumentRepository.save(vehicleDocument);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateVehicleDocument(VehicleDocument vehicleDocument) throws Exception {
		vehicleDocumentRepository.updateVehicleDocument(vehicleDocument);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<VehicleDocumentDto> listVehicleDocument(Integer vehicleDocument, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT c.id, VDT.vDocType, c.created, c.filename, c.vehid "
				+ " FROM VehicleDocument AS c "
				+ " INNER JOIN VehicleDocType VDT ON VDT.dtid = c.docTypeId"
				+ " where c.vehid="
						+ vehicleDocument + " AND c.companyId = " + companyId + " AND c.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<VehicleDocumentDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleDocumentDto>();
			VehicleDocumentDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new VehicleDocumentDto();
				Documentto.setId((Integer) result[0]);
				Documentto.setName((String) result[1]);
				if(result[2] != null)
					Documentto.setUploaddate(CreatedDateTime.format(result[2]));
				Documentto.setFilename((String) result[3]);
				Documentto.setVehid((Integer) result[4]);

				Dtos.add(Documentto);
			}
		}
		return Dtos;
	}

	@Transactional
	public VehicleDocumentDto getVehicleDocument(int vehicle_id, Integer companyId) throws Exception {
		//return vehicleDocumentRepository.getVehicleDocument(vehicle_id, companyId);
		Query query = entityManager.createQuery(
				"SELECT T.id , T.docTypeId, VDT.vDocType, T.created, T.filename, T.vehid, T.content, T.contentType,"
				+ " U.email, T.createdById "
				+ " FROM VehicleDocument AS T "
				+ " LEFT JOIN VehicleDocType VDT ON VDT.dtid = T.docTypeId"
				+ " LEFT JOIN User U ON U.id = T.createdById"
				+ " WHERE T.id = "+vehicle_id+" AND T.companyId = "+companyId+" AND T.markForDelete = 0");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		VehicleDocumentDto select;
		if (result != null) {
			select = new VehicleDocumentDto();
			
			select.setId((Integer) result[0]);
			select.setDocTypeId((Long) result[1]);
			select.setName((String) result[2]);
			if(result[2] != null)
				select.setUploaddate(CreatedDateTime.format(result[3]));
			select.setFilename((String) result[4]);
			select.setVehid((Integer) result[5]);
			select.setContent((byte[]) result[6]);
			select.setContentType((String) result[7]);
			select.setCreatedBy((String) result[8]);
			select.setCreatedById((Long) result[9]);

		} else {
			return null;
		}

		return select;
	
	}

	@Transactional
	public void deleteVehicleDocument(int vehicle_id, Integer companyId) throws Exception {
		vehicleDocumentRepository.deleteVehicleDocument(vehicle_id, companyId);
	}

	public VehicleDocumentDto getDownload(int vehicle_id, Integer companyId) throws Exception {
		//return vehicleDocumentRepository.getVehicleDocument(vehicle_id, companyId);
		Query query = entityManager.createQuery(
				"SELECT T.id , T.docTypeId, VDT.vDocType, T.created, T.filename, T.vehid, T.content, T.contentType,"
						+ " U.email, T.createdById "
						+ " FROM VehicleDocument AS T "
						+ " LEFT JOIN VehicleDocType VDT ON VDT.dtid = T.docTypeId"
						+ " LEFT JOIN User U ON U.id = T.createdById"
						+ " WHERE T.id = "+vehicle_id+" AND T.companyId = "+companyId+" AND T.markForDelete = 0");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		VehicleDocumentDto select;
		if (result != null) {
			select = new VehicleDocumentDto();
			
			select.setId((Integer) result[0]);
			select.setDocTypeId((Long) result[1]);
			select.setName((String) result[2]);
			select.setUploaddate(CreatedDateTime.format(result[3]));
			select.setFilename((String) result[4]);
			select.setVehid((Integer) result[5]);
			select.setContent((byte[]) result[6]);
			select.setContentType((String) result[7]);
			select.setCreatedBy((String) result[8]);
			select.setCreatedById((Long) result[9]);

		} else {
			return null;
		}

		return select;
	
	}

	@Transactional
	public List<VehicleDocumentDto> listofSortVehicleDocument(Integer vehicleDocument, Long docTypeId, Integer companyId)
			throws Exception {
		//return vehicleDocumentRepository.listofSortVehicleDocument(vehicleDocument, name, companyId);

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT c.id, VDT.vDocType, c.created, c.filename, c.vehid, c.contentType, U.email "
				+ " FROM VehicleDocument AS c "
				+ " INNER JOIN VehicleDocType VDT ON VDT.dtid = c.docTypeId"
				+ " LEFT JOIN User U ON U.id = c.createdById"
				+ " where c.vehid= "+vehicleDocument+" and c.docTypeId= "+docTypeId+" AND c.companyId = "+companyId+" AND c.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<VehicleDocumentDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleDocumentDto>();
			VehicleDocumentDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new VehicleDocumentDto();
				Documentto.setId((Integer) result[0]);
				Documentto.setName((String) result[1]);
				Documentto.setUploaddate(CreatedDateTime.format(result[2]));
				Documentto.setFilename((String) result[3]);
				Documentto.setVehid((Integer) result[4]);
				Documentto.setContentType((String) result[5]);
				Documentto.setCreatedBy((String) result[6]);

				Dtos.add(Documentto);
			}
		}
		return Dtos;
	
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addVehiclePhoto(org.fleetopgroup.persistence.document.VehiclePhoto vehiclePhoto) throws Exception {
		vehiclePhoto.set_id((int) sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_VEHICLE_PHOTO));
		mongoTemplate.save(vehiclePhoto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateVehiclePhoto(VehicleDocument vehiclePhoto) throws Exception {
		/* vehiclePhotoRepository.updateVehiclePhoto(vehiclePhoto); */
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<org.fleetopgroup.persistence.document.VehiclePhoto> listVehiclePhoto(Integer vehiclePhoto, Integer companyId) throws Exception {
		//return vehiclePhotoRepository.listVehiclePhoto(vehiclePhoto, companyId);
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("vehid").is(vehiclePhoto).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.VehiclePhoto.class);
	
	
	}

	@Transactional
	public List<org.fleetopgroup.persistence.document.VehiclePhoto> listofSortVehiclePhoto(String vehiclePhoto, String name) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("vehid").is(vehiclePhoto).and("name").is(name).and("markForDelete").is(false));
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.VehiclePhoto.class);
	}

	@Transactional
	public org.fleetopgroup.persistence.document.VehiclePhoto getVehiclePhoto(int Photo_id, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(Photo_id).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.VehiclePhoto.class);
	}

	@Transactional
	public org.fleetopgroup.persistence.document.VehiclePhoto getDownloadPhoto(int Photo_id) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(Photo_id));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.VehiclePhoto.class);
		
	}

	@Transactional
	public void deleteVehiclePhoto(int Photo_id, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query(Criteria.where("_id").is(Photo_id).and("companyId").is(companyId));
		Update update = new Update();
		update.set("markForDelete", true);
		mongoTemplate.updateFirst(query, update, org.fleetopgroup.persistence.document.VehiclePhoto.class);
	}

	@Transactional
	public void setprofilePhoto(int vehiclephoto_id, Integer vehicle_id, Integer companyId) throws Exception {
		vehicleRepository.setprofilePhoto(vehiclephoto_id, vehicle_id, companyId);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addVehiclePurchase(VehiclePurchase vehiclePurchase) throws Exception {
		vehiclePurchaseRepository.save(vehiclePurchase);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateVehiclePurchase(VehiclePurchase vehiclePhoto) throws Exception {
		vehiclePurchaseRepository.updateVehiclePurchase(vehiclePhoto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<VehiclePurchaseDto> listVehiclePurchase(Integer vehiclePurchase, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT c.id, VP.vPurchaseInfoType, c.uploaddateOn, c.filename, c.vehid, U.email "
				+ " FROM VehiclePurchase AS c "
				+ " LEFT JOIN VehiclePurchaseInfoType VP ON VP.ptid = c.vPurchaseTypeId"
				+ " LEFT JOIN User U ON U.id = c.createdById"
				+ " where c.vehid="
						+ vehiclePurchase + " AND c.companyId = " + companyId + " AND c.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<VehiclePurchaseDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehiclePurchaseDto>();
			VehiclePurchaseDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new VehiclePurchaseDto();
				Documentto.setId((Integer) result[0]);
				Documentto.setName((String) result[1]);
				Documentto.setUploaddate(CreatedDateTime.format(result[2]));
				Documentto.setFilename((String) result[3]);
				Documentto.setVehid((Integer) result[4]);
				Documentto.setCreatedBy((String) result[5]);

				Dtos.add(Documentto);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<VehiclePurchase> listofSortVehiclePurchase(String vehiclePurchase, String name) throws Exception {
		return vehiclePurchaseRepository.listofSortVehiclePurchase(vehiclePurchase, name);
	}

	@Transactional
	public VehiclePurchaseDto getVehiclePurchase(int doc_id, Integer companyId) throws Exception {
		
		Query query = entityManager.createQuery(
						" SELECT V.id, VPT.vPurchaseInfoType, V.uploaddateOn, V.vPurchaseTypeId, V.filename, V.vehid, V.content,"
						+ " V.contentType, U.email, V.createdById "
						+ " FROM VehiclePurchase AS V "
						+ " LEFT JOIN VehiclePurchaseInfoType VPT ON VPT.ptid = V.vPurchaseTypeId"
						+ " LEFT JOIN User U ON U.id = V.createdById"
						+ " WHERE V.id = "+doc_id+" AND V.companyId = "+companyId+" AND V.markForDelete = 0");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		VehiclePurchaseDto select;
		if (result != null) {
			select = new VehiclePurchaseDto();
			
			select.setId((Integer) result[0]);
			select.setName((String) result[1]);
			select.setUploaddate(CreatedDateTime.format(result[2]));
			select.setvPurchaseTypeId((Long) result[3]);
			select.setFilename((String) result[4]);
			select.setVehid((Integer) result[5]);
			select.setContent((byte[]) result[6]);
			select.setContentType((String) result[7]);
			select.setCreatedBy((String) result[8]);
			select.setCreatedById((Long) result[9]);
			

		} else {
			return null;
		}

		return select;
	
	
	
	}

	@Transactional
	public VehiclePurchase getDownloadPurchase(int doc_id, Integer companyId) throws Exception {
		return vehiclePurchaseRepository.getDownloadPurchase(doc_id, companyId);
	}

	@Transactional
	public void deleteVehiclePurchase(int doc_id, Integer companyId) throws Exception {
		vehiclePurchaseRepository.deleteVehiclePurchase(doc_id, companyId);
	}

	@Transactional
	public void updateCurrentOdoMeter(Integer vehicle_id, int oddmeter_id, Integer companyId) throws Exception {
		vehicleRepository.updateCurrentOdoMeter(vehicle_id, oddmeter_id, companyId);
	}

	public List<Vehicle> listVehicleReport(Vehicle vehicle) throws Exception {
		return vehicleRepository.listVehicleReport(vehicle);
	}

	@Transactional
	public List<VehicleDto> listVehicleReport(String vehicle) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		/* this only Select column */
		TypedQuery<Object[]> queryt = null;
		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(
				userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		int tripSheetFlavor = (int) configuration.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR);
		if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {

			if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
								+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, R.vehicleGroupId, T.tripSheetNumber, WO.workorders_Number"
								+ " ,VBM.vehicleBodyMakerName,R.vehicle_chasis,R.vehicle_engine "
								+ " FROM Vehicle AS R "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
								+ " LEFT JOIN VehicleBodyMaker VBM ON VBM.vehicleBodyMakerId = R.vehicleBodyMakerId "
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN TripSheet T ON T.tripSheetID = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
								+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + "" + " where  R.company_Id = "
								+ userDetails.getCompany_id() + " and R.markForDelete = 0 " + vehicle + "",
						Object[].class);
			} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
								+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, R.vehicleGroupId, T.TRIPCOLLNUMBER, WO.workorders_Number"
								+ " ,VBM.vehicleBodyMakerName,R.vehicle_chasis,R.vehicle_engine "
								+ " FROM Vehicle AS R "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
								+ " LEFT JOIN VehicleBodyMaker VBM ON VBM.vehicleBodyMakerId = R.vehicleBodyMakerId "
								+ " LEFT JOIN TripCollectionSheet T ON T.TRIPCOLLID = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
								+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + "" + " where  R.company_Id = "
								+ userDetails.getCompany_id() + " and R.markForDelete = 0 " + vehicle + "",
						Object[].class);
			} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
								+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, R.vehicleGroupId, T.TRIPDAILYNUMBER, WO.workorders_Number "
								+ " ,VBM.vehicleBodyMakerName,R.vehicle_chasis,R.vehicle_engine "
								+ " FROM Vehicle AS R "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN VehicleBodyMaker VBM ON VBM.vehicleBodyMakerId = R.vehicleBodyMakerId "
								+ " LEFT JOIN TripDailySheet T ON T.TRIPDAILYID = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
								+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + "" + " where  R.company_Id = "
								+ userDetails.getCompany_id() + " and R.markForDelete = 0 " + vehicle + "",
						Object[].class);
			}
		} else {

			if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
								+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, R.vehicleGroupId, T.tripSheetNumber, WO.workorders_Number "
								+ " ,VBM.vehicleBodyMakerName,R.vehicle_chasis,R.vehicle_engine "
								+ " FROM Vehicle AS R "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
								+ " LEFT JOIN VehicleBodyMaker VBM ON VBM.vehicleBodyMakerId = R.vehicleBodyMakerId "
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN TripSheet T ON T.tripSheetID = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
								+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + ""
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
								+ userDetails.getId() + "" + " where  R.company_Id = " + userDetails.getCompany_id()
								+ " and R.markForDelete = 0 " + vehicle + "",
						Object[].class);
			} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
								+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, R.vehicleGroupId, T.TRIPCOLLNUMBER, WO.workorders_Number "
								+ " ,VBM.vehicleBodyMakerName,R.vehicle_chasis,R.vehicle_engine "
								+ " FROM Vehicle AS R "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
								+ " LEFT JOIN VehicleBodyMaker VBM ON VBM.vehicleBodyMakerId = R.vehicleBodyMakerId "
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN TripCollectionSheet T ON T.TRIPCOLLID = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
								+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + ""
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
								+ userDetails.getId() + "" + " where  R.company_Id = " + userDetails.getCompany_id()
								+ " and R.markForDelete = 0 " + vehicle + "",
						Object[].class);
			} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {

				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
								+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, R.vehicleGroupId, T.TRIPDAILYNUMBER, WO.workorders_Number "
								+ " ,VBM.vehicleBodyMakerName,R.vehicle_chasis,R.vehicle_engine "
								+ " FROM Vehicle AS R "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
								+ " LEFT JOIN VehicleBodyMaker VBM ON VBM.vehicleBodyMakerId = R.vehicleBodyMakerId "
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN TripDailySheet T ON T.TRIPDAILYID = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
								+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + ""
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
								+ userDetails.getId() + "" + " where  R.company_Id = " + userDetails.getCompany_id()
								+ " and R.markForDelete = 0 " + vehicle + "",
						Object[].class);
			}
		}
		List<Object[]> results = queryt.getResultList();

		List<VehicleDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<>();
			VehicleDto list = null;
			for (Object[] result : results) {
				list = new VehicleDto();

				list.setVid((Integer) result[0]);
				list.setVehicle_registration((String) result[1]);
				list.setVehicle_Group((String) result[2]);
				list.setVehicle_maker((String) result[3]);
				list.setVehicle_Type((String) result[4]);
				list.setVehicle_Odometer((Integer) result[5]);
				list.setVehicle_Location((String) result[6]);
				list.setVehicleOwnerShipId((short) result[7]);
				list.setTripSheetID((Long) result[8]);
				list.setvStatusId((short) result[9]);
				list.setVehicleGroupId((long) result[10]);
				list.setTripSheetNumber((Long) result[11]);
				list.setWorkOrder_Number((Long) result[12]);
				list.setBodyMakerName((String) result[13]);
				list.setVehicle_chasis((String) result[14]);
				list.setVehicle_engine((String) result[15]);;

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<Vehicle> listVehicleReportByVGPermision(String vehicle) throws Exception {
		CustomUserDetails userDetails = null;
		TypedQuery<Object[]> query = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery("SELECT V.vid, V.vehicle_registration From Vehicle AS V "
						+ " where V.markForDelete = 0 " + vehicle + "", Object[].class);

			} else {
				query = entityManager.createQuery("SELECT V.vid, V.vehicle_registration From Vehicle AS V "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId and VGP.user_id ="
						+ userDetails.getId() + "" + " where V.markForDelete  = 0 " + vehicle + "", Object[].class);
			}
			List<Object[]> results = query.getResultList();

			List<Vehicle> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Vehicle>();
				Vehicle list = null;
				for (Object[] result : results) {
					list = new Vehicle();

					list.setVid((Integer) result[0]);
					list.setVehicle_registration((String) result[1]);

					Dtos.add(list);
				}
			}
			return Dtos;
			// return query.getResultList();
		} catch (Exception e) {
			System.err.println("inside catch " + e);
			throw e;
		} finally {
			userDetails = null;
			query = null;
		}
	}

	@Transactional
	public List<Vehicle> GETImportVehicel(String vehicle) throws Exception {
		return vehicleRepository.GETImportVehicel(vehicle);
	}
	
	@Override
	@Transactional
	public List<Object[]> getVehicleIdByVehicleType(long typeId,Integer companyId,Integer branchId) throws Exception {
		if(branchId > 0) {
			return vehicleRepository.getVehicleId(typeId,companyId, VehicleStatusConstant.VEHICLE_STATUS_INACTIVE,VehicleStatusConstant.VEHICLE_STATUS_SURRENDER,VehicleStatusConstant.VEHICLE_STATUS_SOLD,branchId);
		}else {
			return vehicleRepository.getVehicleId(typeId,companyId, VehicleStatusConstant.VEHICLE_STATUS_INACTIVE,VehicleStatusConstant.VEHICLE_STATUS_SURRENDER,VehicleStatusConstant.VEHICLE_STATUS_SOLD);
		}
	}

	@Transactional
	public List<Vehicle> listVehiclePatternReport(String vehicle) throws Exception {
		return vehicleRepository.listVehiclePatternReport(vehicle);
	}

	@Transactional
	public List<Vehicle> listVehicleImport(String vehicle) throws Exception {
		return vehicleRepository.listVehicleImport(vehicle);
	}

	@Transactional
	public Long countTotalVehicle() throws Exception {

		return vehicleRepository.count();
	}

	/*@Transactional
	public Long countActive() throws Exception {

		return vehicleRepository.countActive();
	}
*/
	/*@Transactional
	public Long countOwnership(String Ownership) throws Exception {

		return vehicleRepository.countOwnership(Ownership);
	}*/

	@Transactional
	public List<VehicleDto> SearchVehicle(String vehicle) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		/* this only Select column */
		TypedQuery<Object[]> queryt = null;
		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(
				userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		int tripSheetFlavor = (int) configuration.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR);
		if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {

			if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
								+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, R.vehicleGroupId, T.tripSheetNumber, WO.workorders_Number FROM Vehicle AS R "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN TripSheet T ON T.tripSheetID = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
								+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + ""
								+ " where  lower(R.vehicle_registration) Like (:vehicle) and R.company_Id = "
								+ userDetails.getCompany_id() + " and R.markForDelete = 0",
						Object[].class);
			} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
								+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, R.vehicleGroupId, T.TRIPCOLLNUMBER, WO.workorders_Number FROM Vehicle AS R "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN TripCollectionSheet T ON T.TRIPCOLLID = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
								+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + ""
								+ " where  lower(R.vehicle_registration) Like (:vehicle) and R.company_Id = "
								+ userDetails.getCompany_id() + " and R.markForDelete = 0",
						Object[].class);
			} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
								+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
								+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, R.vehicleGroupId, T.TRIPDAILYNUMBER, WO.workorders_Number FROM Vehicle AS R "
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN TripDailySheet T ON T.TRIPDAILYID = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
								+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + ""
								+ " where  lower(R.vehicle_registration) Like (:vehicle) and R.company_Id = "
								+ userDetails.getCompany_id() + " and R.markForDelete = 0",
						Object[].class);
			}
		} else {

			if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
								+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, R.vehicleGroupId, T.tripSheetNumber, WO.workorders_Number FROM Vehicle AS R "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN TripSheet T ON T.tripSheetID = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
								+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + ""
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
								+ userDetails.getId() + "" + " where  lower(R.vehicle_registration) Like (:vehicle) "
								+ "and R.company_Id = " + userDetails.getCompany_id() + " and R.markForDelete = 0",
						Object[].class);
			} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
								+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, R.vehicleGroupId, T.TRIPCOLLNUMBER, WO.workorders_Number FROM Vehicle AS R "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN TripCollectionSheet T ON T.TRIPCOLLID = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
								+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + ""
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
								+ userDetails.getId() + "" + " where  lower(R.vehicle_registration) Like (:vehicle) "
										+ "and R.company_Id = " + userDetails.getCompany_id() + " and R.markForDelete = 0",
						Object[].class);
			} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {

				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
								+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.tripSheetID, R.vStatusId, R.vehicleGroupId, T.TRIPDAILYNUMBER, WO.workorders_Number FROM Vehicle AS R "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN TripDailySheet T ON T.TRIPDAILYID = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + ""
								+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.tripSheetID AND R.vStatusId = "
								+ VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + ""
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
								+ userDetails.getId() + "" + " where  lower(R.vehicle_registration) Like (:vehicle) "
										+ "and R.company_Id = " + userDetails.getCompany_id() + " and R.markForDelete = 0",
						Object[].class);
			}
		}
		queryt.setParameter("vehicle","%"+vehicle+"%");
		queryt.setFirstResult(0);
		queryt.setMaxResults(50);
		List<Object[]> results = queryt.getResultList();

		List<VehicleDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleDto>();
			VehicleDto list = null;
			for (Object[] result : results) {
				list = new VehicleDto();

				list.setVid((Integer) result[0]);
				list.setVehicle_registration((String) result[1]);
				list.setVehicle_Group((String) result[2]);
				list.setVehicle_maker((String) result[3]);
				list.setVehicle_Type((String) result[4]);
				list.setVehicle_Odometer((Integer) result[5]);
				list.setVehicle_Location((String) result[6]);
				list.setVehicleOwnerShipId((short) result[7]);
				list.setTripSheetID((Long) result[8]);
				list.setvStatusId((short) result[9]);
				list.setVehicleGroupId((long) result[10]);
				list.setTripSheetNumber((Long) result[11]);
				list.setWorkOrder_Number((Long) result[12]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<VehicleDto> SearchVehicle_Registration_Chassis(String vehicle) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<VehicleDto> Dtos = null; 
		TypedQuery<Object[]> queryt = null;
		if(vehicle != null && !vehicle.trim().equalsIgnoreCase("") && vehicle.indexOf('\'') != 0 ) {  
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
							+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.vStatusId, R.vehicleGroupId FROM Vehicle AS R "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
							+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
							+ " where  (lower(R.vehicle_registration) Like ('%" + vehicle
							+ "%') OR  lower(R.vehicle_chasis) Like ('%" + vehicle + "%')) and R.company_Id ="
							+ userDetails.getCompany_id() + " and R.markForDelete = 0",
					Object[].class);
		}
		else {
			queryt = entityManager.createQuery(
					"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer,"
							+ " R.Vehicle_Location, R.vehicleOwnerShipId, R.vStatusId, R.vehicleGroupId FROM Vehicle AS R "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = R.vehicleGroupId"
							+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
							+ userDetails.getId() + "" + " where  (lower(R.vehicle_registration) Like ('%" + vehicle
							+ "%') OR  lower(R.vehicle_chasis) Like ('%" + vehicle + "%')) and R.company_Id ="
							+ userDetails.getCompany_id() + " and R.markForDelete = 0",
					Object[].class);
		}
		
		queryt.setFirstResult(0);
		queryt.setMaxResults(10);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleDto>();
			VehicleDto list = null;
			for (Object[] result : results) {
				list = new VehicleDto();

				list.setVid((Integer) result[0]);
				list.setVehicle_registration((String) result[1]);
				list.setVehicle_Group((String) result[2]);
				list.setVehicle_maker((String) result[3]);
				list.setVehicle_Type((String) result[4]);
				list.setVehicle_Odometer((Integer) result[5]);
				list.setVehicle_Location((String) result[6]);
				list.setVehicleOwnerShipId((short) result[7]);
				list.setvStatusId((short) result[8]);
				list.setVehicleGroupId((long) result[9]);

				Dtos.add(list);
			}
		    }
		}
		return Dtos;
	}

	@Transactional
	public List<Vehicle> SearchOnlyVehicleNAME(String vehicle) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration, v.vStatusId,v.gpsVendorId FROM Vehicle AS v "
					+ " where  lower(v.vehicle_registration) Like (:vehicle) and v.company_Id = "
					+ userDetails.getCompany_id() + " and v.markForDelete = 0 ", Object[].class);
		} else {
			queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration,v.vStatusId,v.gpsVendorId FROM Vehicle AS v "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId and VGP.user_id ="
					+ userDetails.getId() + "" + " where  lower(v.vehicle_registration) Like (:vehicle) and v.company_Id = " + userDetails.getCompany_id() + " and v.markForDelete = 0 ",
					Object[].class);
		}
		queryt.setParameter("vehicle", "%"+vehicle+"%");
		
		List<Object[]> results = queryt.getResultList();

		List<Vehicle> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vehicle>();
			Vehicle dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vehicle();

				dropdown.setVid((Integer) result[0]);
				dropdown.setVehicle_registration((String) result[1]);
				dropdown.setvStatusId((short)result[2]);
				dropdown.setGpsVendorId((Integer) result[3]);

				Dtos.add(dropdown);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<Vehicle> SearchOnlyVehicleNAME_ALL_STATUS(String vehicle) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration FROM Vehicle AS v "
						+ " where  lower(v.vehicle_registration) Like (:vehicle) and v.company_Id = "
						+ userDetails.getCompany_id() + " and v.markForDelete = 0 ", Object[].class);

			} else {
				queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration FROM Vehicle AS v "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId and VGP.user_id ="
						+ userDetails.getId() + "" + " where  lower(v.vehicle_registration) Like (:vehicle) "
						+ "and v.company_Id = " + userDetails.getCompany_id() + " and v.markForDelete = 0 ",
						Object[].class);
			}
			queryt.setParameter("vehicle","%"+vehicle+"%");
			List<Object[]> results = queryt.getResultList();

			List<Vehicle> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Vehicle>();
				Vehicle dropdown = null;
				for (Object[] result : results) {
					dropdown = new Vehicle();

					dropdown.setVid((Integer) result[0]);
					dropdown.setVehicle_registration((String) result[1]);

					Dtos.add(dropdown);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<Vehicle> SearchOnlyVehicleNAME_ALL_STATUS(String vehicle, Long vehicleGroupId) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration FROM Vehicle AS v "
						+ " where  lower(v.vehicle_registration) Like (:vehicle) AND v.vehicleGroupId = "+vehicleGroupId+" and v.company_Id = "
						+ userDetails.getCompany_id() + " and v.markForDelete = 0 ", Object[].class);

			} else {
				queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration FROM Vehicle AS v "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId and VGP.user_id ="
						+ userDetails.getId() + "" + " where  lower(v.vehicle_registration) Like (:vehicle) "
								+ "AND v.vehicleGroupId = "+vehicleGroupId+" and v.company_Id = " + userDetails.getCompany_id() + " and v.markForDelete = 0 ",
						Object[].class);
			}
			queryt.setParameter("vehicle", "%"+vehicle+"%");
			List<Object[]> results = queryt.getResultList();

			List<Vehicle> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Vehicle>();
				Vehicle dropdown = null;
				for (Object[] result : results) {
					dropdown = new Vehicle();

					dropdown.setVid((Integer) result[0]);
					dropdown.setVehicle_registration((String) result[1]);

					Dtos.add(dropdown);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public List<Vehicle> Search_Service_entries_Vehicle_Name(String vehicle) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration FROM Vehicle AS v "
						+ " where  lower(v.vehicle_registration) Like (:vehicle) and v.company_Id = "
						+ userDetails.getCompany_id() + " and v.markForDelete = 0", Object[].class);

			} else {
				queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration FROM Vehicle AS v "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId and VGP.user_id ="
						+ userDetails.getId() + " " + " where  lower(v.vehicle_registration) Like (:vehicle) and v.company_Id = " + userDetails.getCompany_id() + " and v.markForDelete = 0",
						Object[].class);
			}
			
			queryt.setParameter("vehicle","%"+vehicle+"%");
			List<Object[]> results = queryt.getResultList();

			List<Vehicle> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Vehicle>();
				Vehicle dropdown = null;
				for (Object[] result : results) {
					dropdown = new Vehicle();

					dropdown.setVid((Integer) result[0]);
					dropdown.setVehicle_registration((String) result[1]);

					Dtos.add(dropdown);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleDto> Search_DealerService_Vehicle_Name(String vehicle) throws Exception {
		List<VehicleDto> Dtos = null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			if(vehicle != null && !vehicle.trim().equalsIgnoreCase("") && vehicle.indexOf('\'') != 0 ) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration, D.driver_empnumber, D.driver_firstname, D.driver_Lastname,"
						+ " D.driver_id"
						+ " FROM Vehicle AS v "
						+ " LEFT JOIN Driver D ON D.vid = v.vid" 
						+ " where  lower(v.vehicle_registration) Like ('%" + vehicle + "%') and v.company_Id = "
						+ userDetails.getCompany_id() + " and v.markForDelete = 0", Object[].class);

			} else {
				queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration, D.driver_empnumber, D.driver_firstname, D.driver_Lastname,"
						+ " D.driver_id "
						+ " FROM Vehicle AS v "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId and VGP.user_id ="
						+ userDetails.getId() + " "
						+ " LEFT JOIN Driver D ON D.vid = v.vid" 
						+ " where  lower(v.vehicle_registration) Like ('%" + vehicle
						+ "%') and v.company_Id = " + userDetails.getCompany_id() + " and v.markForDelete = 0",
						Object[].class);
			}

			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleDto>();
				VehicleDto dropdown = null;
				for (Object[] result : results) {
					dropdown = new VehicleDto();

					dropdown.setVid((Integer) result[0]);
					dropdown.setVehicle_registration((String) result[1]);
					if(result[2] != null && result[3] != null && result[4] != null)
						dropdown.setDriverFirstName((String) result[2]+"_"+(String) result[3]+"_"+(String) result[4]);
					if(result[2] == null && result[3] != null && result[4] != null)
						dropdown.setDriverFirstName((String) result[3]+"_"+(String) result[4]);
					if(result[2] == null && result[3] != null && result[4] == null)
						dropdown.setDriverFirstName((String) result[3]);
					if(result[2] != null && result[3] != null && result[4] == null)
						dropdown.setDriverFirstName((String) result[2]+"_"+(String) result[3]);
					
					
					dropdown.setDriverId((Integer) result[5]);

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
	public List<Vehicle> searchVehicleListCompare(String vehicle, Integer vid) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration FROM Vehicle AS v "
						+ " where  lower(v.vehicle_registration) Like (:vehicle) and v.company_Id = "
						+ userDetails.getCompany_id() + " and v.markForDelete = 0 AND v.vid <> "+vid+" ", Object[].class);

			} else {
				queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration FROM Vehicle AS v "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId and VGP.user_id ="
						+ userDetails.getId() + " " + " where  lower(v.vehicle_registration) Like (:vehicle) "
								+ "nd v.company_Id = " + userDetails.getCompany_id() + " and v.markForDelete = 0 AND v.vid <> "+vid+" ",
						Object[].class);
			}
			
			queryt.setParameter("vehicle", "%"+vehicle+"%");
			List<Object[]> results = queryt.getResultList();

			List<Vehicle> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Vehicle>();
				Vehicle dropdown = null;
				for (Object[] result : results) {
					dropdown = new Vehicle();

					dropdown.setVid((Integer) result[0]);
					dropdown.setVehicle_registration((String) result[1]);

					Dtos.add(dropdown);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public List<Vehicle> findDropdownList(String vehicle) throws Exception {

		return vehicleRepository.findDropdownList(vehicle);
	}

	@Transactional
	public Integer updateCurrentOdoMeterGETVehicleToCurrentOdometer(int vid) throws Exception {
		try{
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			return vehicleRepository.updateCurrentOdoMeterGETVehicleToCurrentOdometer(vid, userDetails.getCompany_id());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public Integer updateCurrentOdoMeterGetVehicleCurrentOdometer(int vid, int companyId) throws Exception {
		
		return vehicleRepository.updateCurrentOdoMeterGETVehicleToCurrentOdometer(vid, companyId);
	}
	

	@Transactional
	public void addFileVehicle(UploadFile upload) throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleService#
	 * vehicle_wise_select_multiple_vehicle(java.lang.String)
	 */
	@Transactional
	public List<Vehicle> vehicle_wise_select_multiple_vehicle(String Multiplevehicle, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> query = entityManager
				.createQuery("SELECT c.vid, c.vehicle_registration, c.Vehicle_Location from Vehicle AS c where c.vid IN (" + Multiplevehicle
						+ ") AND c.company_Id = " + companyId + " AND c.markForDelete = 0", Object[].class);

		/*
		 * List<Long> names = Arrays.asList(8L,9L);
		 * 
		 * query.setParameter("ids", names);
		 */
		/* return query.getResultList(); */
		List<Object[]> results = query.getResultList();

		List<Vehicle> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vehicle>();
			Vehicle Documentto = null;
			for (Object[] result : results) {
				Documentto = new Vehicle();
				Documentto.setVid((Integer) result[0]);
				Documentto.setVehicle_registration((String) result[1]);
				Documentto.setVehicle_Location((String) result[2]);

				Dtos.add(Documentto);
			}
		}
		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleService#
	 * vehicle_wise_Fuel_mileage_multiple_vehicle(java.lang.String)
	 */
	@Transactional
	public List<Vehicle> vehicle_wise_Fuel_mileage_multiple_vehicle(String Multiplevehicle) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT c.vid, c.vehicle_registration, c.vehicle_ExpectedMileage, c.vehicle_ExpectedMileage_to from Vehicle AS c where c.vid IN ("
						+ Multiplevehicle + ") AND c.company_Id = " + userDetails.getCompany_id()
						+ " AND c.markForDelete = 0",
				Object[].class);

		/*
		 * List<Long> names = Arrays.asList(8L,9L);
		 * 
		 * query.setParameter("ids", names);
		 */
		/* return query.getResultList(); */
		List<Object[]> results = query.getResultList();

		List<Vehicle> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vehicle>();
			Vehicle Documentto = null;
			for (Object[] result : results) {
				Documentto = new Vehicle();
				Documentto.setVid((Integer) result[0]);
				Documentto.setVehicle_registration((String) result[1]);
				Documentto.setVehicle_ExpectedMileage((Double) result[2]);
				Documentto.setVehicle_ExpectedMileage_to((Double) result[3]);

				Dtos.add(Documentto);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleService#
	 * vehicle_wise_GroupFuelRange_Group(java.lang.String)
	 */
	@Transactional
	public List<Vehicle> vehicle_wise_GroupFuelRange_Group(long vehicleGroupId, Integer companyId) throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT c.vid, c.vehicle_registration, c.vehicle_ExpectedMileage, c.vehicle_ExpectedMileage_to from Vehicle AS c where c.vehicleGroupId = "
						+ vehicleGroupId + " AND c.company_Id = " + companyId + " AND c.markForDelete = 0 ",
				Object[].class);

		/*
		 * List<Long> names = Arrays.asList(8L,9L);
		 * 
		 * query.setParameter("ids", names);
		 */
		/* return query.getResultList(); */
		List<Object[]> results = query.getResultList();

		List<Vehicle> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vehicle>();
			Vehicle Documentto = null;
			for (Object[] result : results) {
				Documentto = new Vehicle();
				Documentto.setVid((Integer) result[0]);
				Documentto.setVehicle_registration((String) result[1]);
				Documentto.setVehicle_ExpectedMileage((Double) result[2]);
				Documentto.setVehicle_ExpectedMileage_to((Double) result[3]);
				
				Dtos.add(Documentto);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleService#
	 * countTotalVehicle_AND_Ownership()
	 */
	@Transactional
	public Object[] countTotalVehicle_AND_Ownership() throws Exception {

		Query queryt = entityManager.createQuery(
				"SELECT COUNT(co),(SELECT  COUNT(P) FROM Vehicle AS P where  P.vehicle_Ownership ='Owned' ) "
						+ " FROM Vehicle As co");

		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleService#
	 * countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH(java.lang.
	 * Integer)
	 */
	@Transactional
	public Object[] countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(Integer Vehicle_ID)
			throws Exception {
		CustomUserDetails userDetails = null;
		try {
			Timestamp currentDate = DateTimeUtility.getCurrentTimeStamp();
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Query queryt = entityManager.createQuery(
					"SELECT COUNT(co), "
							+ " (SELECT  COUNT(Pur) FROM VehiclePurchase AS Pur where  Pur.vehid =:id AND Pur.markForDelete = 0 AND Pur.companyId = "
							+ userDetails.getCompany_id() + "),"
							+ " (SELECT  COUNT(RR) FROM RenewalReminder AS RR where  RR.vid =:id  and RR.companyId = "
							+ userDetails.getCompany_id() + " AND RR.renewal_to >= '"+currentDate+"' AND RR.markForDelete = 0),"
							+ " (SELECT  COUNT(FE) FROM Fuel AS FE where  FE.vid =:id AND FE.companyId = "
							+ userDetails.getCompany_id() + " AND FE.markForDelete = 0 ),"
							+ " (SELECT  COUNT(SE) FROM ServiceEntries AS SE where  SE.vid =:id and SE.companyId = "
							+ userDetails.getCompany_id() + " and SE.markForDelete = 0),"
							+ " (SELECT  COUNT(SR) FROM ServiceReminder AS SR INNER JOIN Vehicle AS VH ON VH.vid = SR.vid  where  SR.vid =:id and SR.companyId = "
							+ userDetails.getCompany_id() + " and SR.markForDelete = 0 and VH.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+","+VehicleStatusConstant.VEHICLE_STATUS_ACCIDENT+")),"
							+ " (SELECT  COUNT(TE) FROM TripSheet AS TE where  TE.vid =:id AND TE.companyId = "
							+ userDetails.getCompany_id() + " AND TE.markForDelete = 0 ),"
							+ " (SELECT  COUNT(WOR) FROM WorkOrders AS WOR where  WOR.vehicle_vid =:id and WOR.companyId = "
							+ userDetails.getCompany_id() + " and WOR.markForDelete = 0 ),"
							+ " (SELECT  COUNT(ISSU) FROM Issues AS ISSU where  ISSU.ISSUES_VID =:id AND ISSU.COMPANY_ID = "
							+ userDetails.getCompany_id() + " AND ISSU.markForDelete = 0 ),"
							+ " (SELECT  COUNT(ODOH) FROM VehicleOdometerHistory AS ODOH where  ODOH.vid =:id and ODOH.companyId = "
							+ userDetails.getCompany_id() + " and ODOH.markForDelete = 0),"
							+ " (SELECT  COUNT(COM) FROM VehicleComment AS COM where  COM.VEHICLE_ID =:id AND COM.markForDelete = 0 AND COM.COMPANY_ID = "
							+ userDetails.getCompany_id() + "),"
							+ " (SELECT  COUNT(VIC) FROM VehicleInspectionCompletionDetails AS VIC where  VIC.vid =:id AND VIC.markForDelete = 0 AND VIC.companyId = "
							+ userDetails.getCompany_id() + " AND VIC.inspectionStatusId = 2),"
							+ "(SELECT COUNT(DSE) FROM DealerServiceEntries AS DSE WHERE DSE.vid =:id AND DSE.markForDelete=0 AND DSE.companyId="+userDetails.getCompany_id()+" )"
							+ " FROM VehiclePhoto As co Where co.vehid=:id AND co.markForDelete = 0 AND co.companyId = "
							+ userDetails.getCompany_id() + "");
			queryt.setParameter("id", Vehicle_ID);
			Object[] count = (Object[]) queryt.getSingleResult();

			return count;
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleService#
	 * add_Issues_Comment_Details(org.fleetop.persistence.model.VehicleComment)
	 */
	@Transactional
	public void add_Vehicle_Comment_Details(VehicleComment vehicleComment) throws Exception {

		vehicleCommentRepository.save(vehicleComment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleService#
	 * Get_Issues_ID_Comment_Details(java.lang.Long)
	 */
	@Transactional
	public List<VehicleCommentDto> Get_Vehicle_ID_Comment_Details(Integer vehicle_ID, Integer companyId) throws Exception {
		//return vehicleCommentRepository.Get_Vehicle_ID_Comment_Details(vehicle_ID, companyId);

		TypedQuery<Object[]> query = entityManager.createQuery(
				" SELECT C.VEHICLE_COMMENTID, C.VEHICLE_TITLE, C.VEHICLE_ID, C.VEHICLE_COMMENT, U.firstName, U.email, C.CREATEDBYID, C.CREATED_DATE "
				+ " FROM VehicleComment AS C  "
				+ " LEFT JOIN User U ON U.id = C.CREATEDBYID"
				+ " WHERE C.VEHICLE_ID = "+vehicle_ID+" AND C.COMPANY_ID = "+companyId+" AND C.markForDelete = 0",
				Object[].class);

		
		List<Object[]> results = query.getResultList();

		List<VehicleCommentDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleCommentDto>();
			VehicleCommentDto select = null;
			for (Object[] result : results) {
				
				select = new VehicleCommentDto();
				
				select.setVEHICLE_COMMENTID((Long) result[0]);
				select.setVEHICLE_TITLE((String) result[1]);
				select.setVEHICLE_ID((Integer) result[2]);
				select.setVEHICLE_COMMENT((String) result[3]);
				select.setCREATEDBY((String) result[4]);
				select.setCREATED_EMAIL((String) result[5]);
				select.setCREATEDBYID((Long) result[6]);
				select.setCREATED_DATE_ON((Date) result[7]);
				
				Dtos.add(select);
			}
		}
		return Dtos;
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleService#
	 * Delete_Issues_Comment_Details(java.lang.Long)
	 */
	@Transactional
	public void Delete_Vehicle_Comment_Details(Long vehicleComment_ID, Integer companyId) throws Exception {

		vehicleCommentRepository.Delete_Vehicle_Comment_Details(vehicleComment_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleService#
	 * Get_Recent_Comment_Details()
	 */
	@Transactional
	public List<VehicleCommentDto> Get_Recent_Comment_Details(Integer companyId) throws Exception {
		//return vehicleCommentRepository.Get_Vehicle_ID_Comment_Details(vehicle_ID, companyId);

		TypedQuery<Object[]> query = entityManager.createQuery(
				" SELECT C.VEHICLE_COMMENTID, C.VEHICLE_TITLE, C.VEHICLE_ID, C.VEHICLE_COMMENT, U.firstName, U.email, C.CREATEDBYID, C.CREATED_DATE "
				+ " FROM VehicleComment AS C  "
				+ " LEFT JOIN User U ON U.id = C.CREATEDBYID"
				+ " WHERE  C.COMPANY_ID = "+companyId+" AND C.markForDelete = 0 ORDER BY C.VEHICLE_COMMENTID DESC",
				Object[].class);

		
		List<Object[]> results = query.getResultList();

		List<VehicleCommentDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleCommentDto>();
			VehicleCommentDto select = null;
			for (Object[] result : results) {
				
				select = new VehicleCommentDto();
				
				select.setVEHICLE_COMMENTID((Long) result[0]);
				select.setVEHICLE_TITLE((String) result[1]);
				select.setVEHICLE_ID((Integer) result[2]);
				select.setVEHICLE_COMMENT((String) result[3]);
				select.setCREATEDBY((String) result[4]);
				select.setCREATED_EMAIL((String) result[5]);
				select.setCREATEDBYID((Long) result[6]);
				select.setCREATED_DATE_ON((Date) result[7]);
				
				Dtos.add(select);
			}
		}
		return Dtos;
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleService#
	 * countTotal_Dashboard_IOpen_IOverdue_SROverdue_SRDuesoon_RRToday_DLRToday_PUReq_PUOrdered
	 * ()
	 */
	@Transactional
	public Object[] countTotal_Dashboard_IOpen_IOverdue_SROverdue_SRDuesoon_RRToday_DLRToday_PUReq_PUOrdered(String CurrentDate, Integer companyId) throws Exception {

		Query queryt = entityManager.createQuery("SELECT COUNT(co),"
				+ "(SELECT COUNT(IOV) FROM Issues AS IOV  Where IOV.ISSUES_REPORTED_DATE <= '" + CurrentDate
				+ "' AND IOV.ISSUES_STATUS_ID = "+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND IOV.COMPANY_ID = "+companyId+" AND IOV.markForDelete = 0), "

				+ " (SELECT  COUNT(SRO) FROM ServiceReminder SRO where ( SRO.time_servicedate <= '" + CurrentDate
				+ "'  OR SRO.meter_serviceodometer <= SRO.vehicle_currentOdometer) AND SRO.companyId = "+companyId+" AND SRO.markForDelete = 0 ),"
				+ " (SELECT  COUNT(SRD) FROM ServiceReminder SRD where  ( SRD.time_servicethreshold_date <= '"
				+ CurrentDate + "' AND  SRD.time_servicedate >= '" + CurrentDate + "'  OR "
				+ "SRD.meter_servicethreshold_odometer <= SRD.vehicle_currentOdometer AND SRD.meter_serviceodometer >= SRD.vehicle_currentOdometer ) AND SRD.companyId = "+companyId+" AND SRD.markForDelete = 0 ),"

				+ " (SELECT  COUNT(RRT) FROM RenewalReminder RRT where RRT.renewal_to between '" + CurrentDate
				+ "' AND '" + CurrentDate + "' AND RRT.companyId = "+companyId+" AND RRT.markForDelete = 0 ),"

				+ " (SELECT  COUNT(DLR) FROM DriverReminder DLR where DLR.driver_dlto='" + CurrentDate + "' AND DLR.companyId = "+companyId+" AND DLR.markForDelete = 0 ),"

				+ " (SELECT  COUNT(PUR) FROM PurchaseOrders PUR Where PUR.purchaseorder_statusId="+PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION+" AND PUR.companyId = "+companyId+" AND PUR.markForDelete = 0 ),"
				+ " (SELECT  COUNT(PUO) FROM PurchaseOrders PUO Where PUO.purchaseorder_statusId="+PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED+" AND PUO.companyId = "+companyId+" AND PUO.markForDelete = 0 )"
				+ " FROM Issues AS co where  co.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_OPEN+"  AND co.COMPANY_ID = "+companyId+" AND co.markForDelete = 0");
		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IVehicleService#countTotal_Grap_Cost(
	 * java.lang.String, java.lang.String)
	 */
	@Transactional
	public Object[] countTotal_Grap_Cost_Fuel_RenewalRe_SE_TS_PO_WO_Amount(String FromDate, String ToDate,
			Integer companyId) throws Exception {

		Query queryt = entityManager.createQuery("SELECT SUM(co.fuel_amount),"
				+ "(SELECT  SUM(RRT.renewal_Amount) FROM RenewalReminder RRT where RRT.renewal_to between '" + FromDate
				+ "' AND '" + ToDate + "' AND RRT.companyId = " + companyId + " AND RRT.markForDelete = 0 ), "
				+ "(SELECT  SUM(SE.totalserviceROUND_cost) FROM ServiceEntries SE where SE.invoiceDate between '"
				+ FromDate + "' AND '" + ToDate + "' AND SE.companyId = " + companyId + " AND SE.markForDelete = 0), "
				+ "(SELECT  SUM(TS.tripTotalexpense) FROM TripSheet TS where TS.tripOpenDate between '" + FromDate
				+ "' AND '" + ToDate + "' AND TS.companyId = " + companyId + " AND TS.markForDelete = 0 ), "
				+ "(SELECT  SUM(PO.purchaseorder_totalcost) FROM PurchaseOrders PO where PO.purchaseorder_orderddate between '"
				+ FromDate + "' AND '" + ToDate + "' AND PO.companyId = " + companyId + " AND PO.markForDelete = 0 ), "
				+ "(SELECT  SUM(WO.totalworkorder_cost) FROM WorkOrders WO where WO.start_date between '" + FromDate
				+ "' AND '" + ToDate + "' AND WO.companyId = " + companyId + " AND WO.markForDelete = 0 ), "
				+ "(SELECT  SUM(U.ureaAmount) FROM UreaEntries U where U.ureaDate between '" + FromDate
				+ "' AND '" + ToDate + "' AND U.companyId = " + companyId + " AND U.markForDelete = 0 ) "
				+ " FROM Fuel AS co where co.fuel_date between '" + FromDate + "' AND '" + ToDate
				+ "' AND co.companyId = " + companyId + " AND co.markForDelete = 0");
		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IVehicleService#Update_Vehicle_Status
	 * (java.lang.String, java.lang.Integer)
	 */
	/*@Transactional
	public void Update_Vehicle_Status(String vehicleStatus, Integer vehicle_vid) {

		vehicleRepository.Update_Vehicle_Status(vehicleStatus, vehicle_vid);
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleService#
	 * Update_Vehicle_Status_TripSheetID(java.lang.String, java.lang.Long,
	 * java.lang.Integer)
	 */
	@Transactional
	public void Update_Vehicle_Status_TripSheetID(Long tripSheetID, Integer vid, Integer compId, short vStatusId) {
		
		vehicleRepository.Update_Vehicle_Status_TripSheetID(tripSheetID, vid, compId, vStatusId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleService#
	 * Get_Vehicle_Current_Status(java.lang.Integer)
	 */
	@Transactional
	public VehicleDto Get_Vehicle_Current_Status_TripSheetID(Integer vid) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Query query = entityManager.createQuery(
				 " SELECT f.vid, f.vehicle_registration, f.vStatusId,  f.tripSheetID, f.vehicle_Odometer, f.vehicle_ExpectedOdameter,"
				 + " f.vehicleGroupId, tr.routeApproximateKM, f.vehicleGPSId, f.gpsVendorId, f.vehicleOwnerShipId, f.vehicle_ExpectedMileage "
			   + " FROM Vehicle AS f "
			   + " LEFT JOIN TripRoute tr ON tr.routeID = f.routeID "			 
			   + " where f.vid = :id AND f.company_Id = "+ userDetails.getCompany_id() + " AND f.markForDelete = 0");

		query.setParameter("id", vid);
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		VehicleDto select;
		if (vehicle != null) {
			select = new VehicleDto();
			select.setVid((Integer) vehicle[0]);
			select.setVehicle_registration((String) vehicle[1]);
			select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus((short) vehicle[2]));
			select.setTripSheetID((Long) vehicle[3]);
			Integer currentOdometer = 0, expectOdometer = 0, totalexpectOdometer = 0, routeApproximateKM =0;
			if ((Integer) vehicle[4] != null) {
				currentOdometer = (Integer) vehicle[4];
			}
			if ((Integer) vehicle[5] != null && (Integer) vehicle[5] != 0) {
				expectOdometer = (Integer) vehicle[5];
			}else {
				expectOdometer = 3500;
			} 
			if ((Integer) vehicle[7] != null && (Integer) vehicle[7] != 0) {
				routeApproximateKM = (Integer) vehicle[7];
			}
			
			totalexpectOdometer = currentOdometer + expectOdometer;
			select.setVehicle_Odometer(currentOdometer);
			select.setVehicle_ExpectedOdameter(totalexpectOdometer);
			select.setvStatusId((short) vehicle[2]);
			select.setVehicleGroupId((Long) vehicle[6]);
			
			if(routeApproximateKM > expectOdometer) {
			select.setVehicleExpectedKm(routeApproximateKM);
			} else {
			select.setVehicleExpectedKm(expectOdometer);	
			}
			
			select.setVehicleGPSId((String) vehicle[8]);
			if( vehicle[9] != null)
				select.setGpsVendorId((Integer) vehicle[9]);
			
			select.setVehicleOwnerShipId((short) vehicle[10]);
			select.setVehicle_ExpectedMileage((Double) vehicle[11]);
			
		} else {
			return null;
		}
		return select;
	}

	@Override
	public VehicleDto Get_Vehicle_Current_Status_TripSheetID(Integer vid, Integer companyId) throws Exception {
		
		Query query = entityManager.createQuery(
				 " SELECT f.vid, f.vehicle_registration, f.vStatusId,  f.tripSheetID, f.vehicle_Odometer, f.vehicle_ExpectedOdameter,"
				 + " f.vehicleGroupId, tr.routeApproximateKM, f.vehicleGPSId, f.gpsVendorId, f.vehicleOwnerShipId, f.vehicle_ExpectedMileage,"
				 + " f.vehicleTollId "
			   + " FROM Vehicle AS f "
			   + " LEFT JOIN TripRoute tr ON tr.routeID = f.routeID "			 
			   + " where f.vid = :id AND f.company_Id = "+ companyId + " AND f.markForDelete = 0");

		query.setParameter("id", vid);
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		VehicleDto select;
		if (vehicle != null) {
			select = new VehicleDto();
			select.setVid((Integer) vehicle[0]);
			select.setVehicle_registration((String) vehicle[1]);
			select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus((short) vehicle[2]));
			select.setTripSheetID((Long) vehicle[3]);
			Integer currentOdometer = 0, expectOdometer = 0, totalexpectOdometer = 0, routeApproximateKM =0;
			if ((Integer) vehicle[4] != null) {
				currentOdometer = (Integer) vehicle[4];
			}
			if ((Integer) vehicle[5] != null && (Integer) vehicle[5] != 0) {
				expectOdometer = (Integer) vehicle[5];
			}else {
				expectOdometer = 3500;
			} 
			if ((Integer) vehicle[7] != null && (Integer) vehicle[7] != 0) {
				routeApproximateKM = (Integer) vehicle[7];
			}
			
			totalexpectOdometer = currentOdometer + expectOdometer;
			select.setVehicle_Odometer(currentOdometer);
			select.setVehicle_ExpectedOdameter(totalexpectOdometer);
			select.setvStatusId((short) vehicle[2]);
			select.setVehicleGroupId((Long) vehicle[6]);
			
			if(routeApproximateKM > expectOdometer) {
			select.setVehicleExpectedKm(routeApproximateKM);
			} else {
			select.setVehicleExpectedKm(expectOdometer);	
			}
			
			select.setVehicleGPSId((String) vehicle[8]);
			if( vehicle[9] != null)
				select.setGpsVendorId((Integer) vehicle[9]);
			
			select.setVehicleOwnerShipId((short) vehicle[10]);
			select.setVehicle_ExpectedMileage((Double) vehicle[11]);
			if(vehicle[12] != null)
				select.setVehicleTollId((String) vehicle[12]);
		} else {
			return null;
		}
		return select;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleService#
	 * countTotal_LAST_MONTH_RR_FE_SE_TS_WO_REPORT(java.lang.Integer,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Object[] countTotal_LAST_MONTH_RR_FE_SE_TS_or_TC_or_TD_WO_REPORT(Integer vEHICLE_VID, String fromDate,
			String toDate) {
		// lAST MONTH EXPENSE DETAILS
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Query queryt = entityManager.createQuery(
				"SELECT SUM(RR.renewal_Amount),(SELECT  SUM(FE.fuel_amount) FROM Fuel AS FE where  FE.vid =:id AND FE.fuel_date BETWEEN '"
						+ fromDate + "' AND '" + toDate + "' AND FE.companyId = " + userDetails.getCompany_id()
						+ " AND FE.markForDelete = 0),"
						+ " (SELECT  SUM(SE.totalserviceROUND_cost) FROM ServiceEntries AS SE where  SE.vid =:id AND SE.invoiceDate BETWEEN '"
						+ fromDate + "' AND '" + toDate + "' ),"
						+ " (SELECT  SUM(TE.tripTotalexpense) FROM TripSheet AS TE where  TE.vid =:id AND TE.closetripDate BETWEEN '"
						+ fromDate + "' AND '" + toDate + "' AND TE.companyId = " + userDetails.getCompany_id()
						+ " AND TE.markForDelete = 0),"
						+ " (SELECT  SUM(TC.TOTAL_EXPENSE) FROM TripCollectionSheet AS TC where  TC.VID =:id AND TC.TRIP_OPEN_DATE BETWEEN '"
						+ fromDate + "' AND '" + toDate + "' AND TC.COMPANY_ID = " + userDetails.getCompany_id()
						+ " AND TC.markForDelete = 0),"
						+ " (SELECT  SUM(TD.TOTAL_EXPENSE) FROM TripDailySheet AS TD where  TD.VEHICLEID =:id AND TD.TRIP_OPEN_DATE BETWEEN '"
						+ fromDate + "' AND '" + toDate + "' AND TD.COMPANY_ID = " + userDetails.getCompany_id()
						+ " AND TD.markForDelete = 0),"
						+ " (SELECT  SUM(WOR.totalworkorder_cost) FROM WorkOrders AS WOR where  WOR.vehicle_vid =:id  AND WOR.start_date BETWEEN '"
						+ fromDate + "' AND '" + toDate + "' AND WOR.companyId = " + userDetails.getCompany_id()
						+ " AND WOR.markForDelete = 0),"
						+ " (SELECT SUM(DSE.totalInvoiceCost) FROM DealerServiceEntries AS DSE WHERE DSE.vid = :id AND DSE.invoiceDate BETWEEN '"+fromDate+"' AND '"+toDate+"' AND DSE.companyId="+userDetails.getCompany_id()+" AND DSE.markForDelete=0) "
						+ " FROM RenewalReminder As RR Where RR.vid=:id AND RR.renewal_from BETWEEN '" + fromDate
						+ "' AND '" + toDate + "' AND RR.companyId = " + userDetails.getCompany_id()
						+ " AND RR.markForDelete = 0");
		queryt.setParameter("id", vEHICLE_VID);
		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	@Override
	public List<VehicleDto> listVehicel(short Status, Integer companyId) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, R.vehicle_Type, R.vehicle_Odometer, "
								+ "R.Vehicle_Location, R.vehicle_Ownership, R.vehicle_Status, R.tripSheetID FROM Vehicle AS R "
								+ " INNER JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId "
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " WHERE R.vStatusId=" + Status + "and R.company_Id = " + companyId
								+ " and R.markForDelete = 0",
						Object[].class);

			} else {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, R.vehicle_Type, R.vehicle_Odometer, "
								+ "R.Vehicle_Location, R.vehicle_Ownership, R.vehicle_Status, R.tripSheetID FROM Vehicle AS R"
								+ " INNER JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.vehicleGroupId and VGP.user_id ="
								+ userDetails.getId() + "" 
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " WHERE R.vStatusId=" + Status + "and R.company_Id = "
								+ companyId + " and R.markForDelete = 0",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			List<VehicleDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleDto>();
				VehicleDto list = null;
				for (Object[] result : results) {
					list = new VehicleDto();

					list.setVid((Integer) result[0]);
					list.setVehicle_registration((String) result[1]);
					list.setVehicle_Group((String) result[2]);
					list.setVehicle_maker((String) result[3]);
					list.setVehicle_Type((String) result[4]);
					list.setVehicle_Odometer((Integer) result[5]);
					list.setVehicle_Location((String) result[6]);
					list.setVehicle_Ownership((String) result[7]);
					list.setVehicle_Status((String) result[8]);
					list.setTripSheetID((Long) result[9]);
					Dtos.add(list);
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
	public VehicleDto getVehicelDetails(Integer vid, CustomUserDetails userDetails) throws Exception {
			HashMap<String, Object> configuration1 = null;
		try {
			Query		 queryt = null;
			VehicleDto	 list 	= null;
			configuration1		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer, "
								+ "R.Vehicle_Location, R.vehicleOwnerShipId, R.vStatusId, R.tripSheetID, R.vehicle_chasis,"
								+ " R.vehicle_engine, R.vehicle_year, VM.vehicleModelName, R.vehicle_registrationState, R.vehicle_RegisterDate,"
								+ " R.vehicle_Registeredupto, TR.routeName, R.vehicle_Color, R.vehicle_Class, R.vehicle_body, R.acTypeId,"
								+ " R.vehicle_Cylinders, R.vehicle_CubicCapacity, R.vehicle_Power, R.vehicle_wheelBase, R.vehicle_SeatCapacity,"
								+ " R.vehicle_UnladenWeight, R.vehicle_LadenWeight, R.vehicleFuelId, R.vehicle_FuelTank1, R.vehicle_Oil, R.vehicleMeterUnitId,"
								+ " R.vehicle_ExpectedMileage, R.vehicle_ExpectedMileage_to, R.vehicleFuelUnitId, R.vStatusId, R.vehicleGroupId,"
								+ " U.email, R.created, U2.email, R.lastupdated, R.vehicle_photoid, R.vehicleTypeId,"
								+ " R.routeID, R.vehicle_ExpectedOdameter, R.vehicleGPSId, R.driverMonthlySalary, R.mobileNumber, R.ledgerName, R.gpsVendorId, R.serviceProgramId,"
								+ "	R.createdById,R.lastModifiedById,R.branchId, R.vehicleModalId, R.vehicleMakerId, B.branch_name,"
								+ " VBM.vehicleBodyMakerId,VBM.vehicleBodyMakerName, R.vehicleTollId, SB.subCompanyName, SB.subCompanyId "
								+ " FROM Vehicle AS R"
								+ " INNER JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId "
								+ " LEFT JOIN TripRoute TR ON TR.routeID = R.routeID"
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId"
								+ " LEFT JOIN User U ON U.id = R.createdById"
								+ " LEFT JOIN User U2 ON U2.id = R.lastModifiedById"
								+ " LEFT JOIN VehicleModel VM ON VM.vehicleModelId = R.vehicleModalId"
								+ " LEFT JOIN Branch AS B ON B.branch_id = R.branchId"
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN VehicleBodyMaker VBM ON VBM.vehicleBodyMakerId = R.vehicleBodyMakerId"
								+ " LEFT JOIN SubCompany  SB ON SB.subCompanyId = R.subCompanyId "
								+ " WHERE R.vid = " + vid
								+ " AND R.company_Id = " + userDetails.getCompany_id() + " and R.markForDelete = 0");

			} else {
				queryt = entityManager.createQuery(
						"SELECT R.vid, R.vehicle_registration, VG.vGroup, M.vehicleManufacturerName, VT.vtype, R.vehicle_Odometer, "
								+ "R.Vehicle_Location, R.vehicleOwnerShipId, R.vStatusId, R.tripSheetID, R.vehicle_chasis,"
								+ " R.vehicle_engine, R.vehicle_year, VM.vehicleModelName, R.vehicle_registrationState, R.vehicle_RegisterDate,"
								+ " R.vehicle_Registeredupto, TR.routeName, R.vehicle_Color, R.vehicle_Class, R.vehicle_body, R.acTypeId,"
								+ " R.vehicle_Cylinders, R.vehicle_CubicCapacity, R.vehicle_Power, R.vehicle_wheelBase, R.vehicle_SeatCapacity,"
								+ " R.vehicle_UnladenWeight, R.vehicle_LadenWeight, R.vehicleFuelId, R.vehicle_FuelTank1, R.vehicle_Oil, R.vehicleMeterUnitId,"
								+ " R.vehicle_ExpectedMileage, R.vehicle_ExpectedMileage_to, R.vehicleFuelUnitId, R.vStatusId, R.vehicleGroupId,"
								+ " U.email, R.created, U2.email, R.lastupdated, R.vehicle_photoid,  R.vehicleTypeId,"
								+ " R.routeID, R.vehicle_ExpectedOdameter, R.vehicleGPSId, R.driverMonthlySalary, R.mobileNumber, R.ledgerName, R.gpsVendorId, R.serviceProgramId,"
								+ "	R.createdById,R.lastModifiedById,R.branchId, R.vehicleModalId, R.vehicleMakerId, B.branch_name,"
								+ " VBM.vehicleBodyMakerId,VBM.vehicleBodyMakerName, R.vehicleTollId, SB.subCompanyName, SB.subCompanyId "
								+ " FROM Vehicle AS R"
								+ " INNER JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId "
								+ " LEFT JOIN TripRoute TR ON TR.routeID = R.routeID"
								+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId" 
								+ " LEFT JOIN User U ON U.id = R.createdById"
								+ " LEFT JOIN User U2 ON U2.id = R.lastModifiedById"
								+ " LEFT JOIN VehicleModel VM ON VM.vehicleModelId = R.vehicleModalId"
								+ " LEFT JOIN Branch AS B ON B.branch_id = R.branchId"
								+ " LEFT JOIN VehicleManufacturer M ON M.vehicleManufacturerId = R.vehicleMakerId"
								+ " LEFT JOIN VehicleBodyMaker VBM ON VBM.vehicleBodyMakerId = R.vehicleBodyMakerId"
								+ " LEFT JOIN SubCompany  SB ON SB.subCompanyId = R.subCompanyId "
								+ " WHERE R.vid = " + vid
								+ " AND R.company_Id = " + userDetails.getCompany_id() + " and R.markForDelete = 0");
			}

			Object[] result = null;
			try {
				result = (Object[]) queryt.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			if (result != null) {
				
					list = new VehicleDto();

					list.setVid((Integer) result[0]);
					list.setVehicle_registration((String) result[1]);
					list.setVehicle_Group((String) result[2]);
					list.setVehicle_maker((String) result[3]);
					list.setVehicle_Type((String) result[4]);
					list.setVehicle_Odometer((Integer) result[5]);
					if((boolean)configuration1.get("branchWiseVehicleInspection") && result[54] != null ){
						if((Integer)result[54] > 0) {
							list.setVehicle_Location((String) result[57]);
						}else {
							list.setVehicle_Location((String) result[6]);
						}
					}else {
						list.setVehicle_Location((String) result[6]);
					}
					list.setVehicleOwnerShipId((short) result[7]);
					list.setvStatusId((short) result[8]);
					list.setTripSheetID((Long) result[9]);
					list.setVehicle_chasis((String) result[10]);
					list.setVehicle_engine((String) result[11]);
					list.setVehicle_year((Integer) result[12]);
					list.setVehicle_Model((String) result[13]);
					list.setVehicle_registrationState((String) result[14]);
					list.setVehicle_RegisterDate((String) result[15]);
					list.setVehicle_Registeredupto((String) result[16]);
					list.setVehicle_RouteName((String) result[17]);
					list.setVehicle_Color((String) result[18]);
					list.setVehicle_Class((String) result[19]);
					list.setVehicle_body((String) result[20]);
					list.setAcTypeId((short) result[21]);
					list.setVehicle_Cylinders((String) result[22]);
					list.setVehicle_CubicCapacity((String) result[23]);
					list.setVehicle_Power((String) result[24]);
					list.setVehicle_wheelBase((String) result[25]);
					list.setVehicle_SeatCapacity((String) result[26]);
					list.setVehicle_UnladenWeight((String) result[27]);
					list.setVehicle_LadenWeight((String) result[28]);
					list.setVehicleFuelId((String) result[29]);
					list.setVehicle_FuelTank1((Integer) result[30]);
					list.setVehicle_Oil((Integer) result[31]);
					list.setVehicleMeterUnitId((short) result[32]);
					list.setVehicle_ExpectedMileage((Double) result[33]);
					list.setVehicle_ExpectedMileage_to((Double) result[34]);
					list.setVehicleFuelUnitId((short) result[35]);
					list.setvStatusId((short) result[36]);
					list.setVehicleGroupId((long) result[37]);
					list.setCreatedBy((String) result[38]);
					list.setCreatedOn((Date) result[39]);
					list.setLastModifiedBy((String) result[40]);
					list.setLastupdatedOn((Date) result[41]);
					list.setVehicle_photoid((Integer) result[42]);
					list.setVehicleTypeId((long) result[43]);
					list.setRouteID((Integer) result[44]);
					list.setVehicle_ExpectedOdameter((Integer) result[45]);
					list.setVehicleGPSId((String) result[46]);
					if((Double) result[47] != null) {
						list.setDriverMonthlySalary((Double) result[47]);
					}
					list.setMobileNumber((String) result[48]);
					list.setLedgerName((String) result[49]);
					if(result[50] != null) {
						list.setGpsVendorId((Integer) result[50]);
						list.setGpsVendorName(VehicleGPSVendorConstant.getGPSVendorName(list.getGpsVendorId()));
					}
					if(result[51] != null)
						list.setServiceProgramId((Long) result[51]);
					list.setCreatedById((long) result[52]);
					list.setModifiedById((long) result[53]);
					if(result[54] != null) {
						list.setBranchId((Integer) result[54]);
					}else {
						list.setBranchId(0);
					}
					if(result[55] != null)
						list.setVehicleModelId((Long) result[55]);
					if(result[56] != null)
						list.setVehicleMakerId((Long) result[56]);
					list.setVehicleBodyMakerId((Integer) result[58]);
					list.setBodyMakerName((String) result[59]);
					list.setVehicleTollId((String) result[60]);
					list.setSubCompanyName((String) result[61]);
					list.setSubCompanyId((Long) result[62]);
			}
			return list;
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}
	
	@Override
	@Transactional
	public void updateVehicleStatus(short status, Integer vid, Integer companyId) throws Exception {
		
		vehicleRepository.update_Vehicle_Status(status, vid, companyId);
	}
	
	@Override
	public ValueObject getVehicleCommentList(ValueObject valueInObject) throws Exception {
		List<VehicleCommentDto>			commentList				= null;
		VehicleCommentDto				vehicleCommentDto		= null;
		CustomUserDetails				userDetails				= null;
		ValueObject						valueObject				= null;
		String 							dateRange				= null;
		try {
			valueObject	= new ValueObject();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!valueInObject.containsKey("dateRange") || !valueInObject.containsKey("vehicleGroupId")) {
				return null;
			}
			dateRange	= valueInObject.getString("dateRange");
			
			String[] From_TO_DateRange = null;

			From_TO_DateRange = dateRange.split(" to ");

			vehicleCommentDto = new VehicleCommentDto();
			vehicleCommentDto.setVEHICLE_ID(valueInObject.getInt("vid", 0));
			vehicleCommentDto.setVEHICLE_GROUP_ID(valueInObject.getInt("vehicleGroupId", 0));
			vehicleCommentDto.setCOMPANY_ID(userDetails.getCompany_id());
			vehicleCommentDto.setFromDate(DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			vehicleCommentDto.setToDate(DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			
			if(vehicleCommentDto.getVEHICLE_ID() > 0) {
				commentList	= vehicleCommentDao.getVehicleCommentListByVid(vehicleCommentDto);
			}else if(vehicleCommentDto.getVEHICLE_GROUP_ID() > 0 && vehicleCommentDto.getVEHICLE_ID() <= 0) {
				commentList	= vehicleCommentDao.getVehicleCommentListByGid(vehicleCommentDto);
			}else {
				commentList	= vehicleCommentDao.getVehicleCommentListByCompanyId(vehicleCommentDto);
			}
			
			valueObject.put("commentList", VBL.getVehicleCommentList(commentList));
			
			valueObject.put("SearchDate", dateRange.replace(" ", "_"));
			if(vehicleCommentDto.getVEHICLE_GROUP_ID() > 0)
				valueObject.put("SearchGroup", vehicleGroupService.getVehicleGroupByID(vehicleCommentDto.getVEHICLE_GROUP_ID()).getvGroup());
			valueObject.put("company",
					userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			commentList				= null;
			vehicleCommentDto		= null;
			userDetails				= null;
		}
	}
	
	@Override
	public List<Vehicle> getVehicleListByVehicleGroupId(long vehicleGroupId, Integer companyId) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
				queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration"
						+ " FROM Vehicle AS v "
						+ " where v.vehicleGroupId = "+vehicleGroupId+" and v.company_Id = " + userDetails.getCompany_id() + " and v.markForDelete = 0 ",
						Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<Vehicle> Dtos = null;
			if (results != null && !results.isEmpty()) {
				
				Dtos = new ArrayList<Vehicle>();
				Vehicle dropdown = null;
				for (Object[] result : results) {
					dropdown = new Vehicle();

					dropdown.setVid((Integer) result[0]);
					dropdown.setVehicle_registration((String) result[1]);

					Dtos.add(dropdown);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public List<Vehicle> getVehicleListByCompanyId(Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
				queryt = entityManager.createQuery(
						"SELECT V.vid, V.vehicle_registration, V.vehicle_Odometer, V.vehicle_ExpectedOdameter "
						+ " FROM Vehicle AS V "
						+ " where V.company_Id = " + companyId + " and V.markForDelete = 0 ",
						Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<Vehicle> Dtos = null;
			if (results != null && !results.isEmpty()) {
				
				Dtos = new ArrayList<Vehicle>();
				Vehicle dropdown = null;
				for (Object[] result : results) {
					dropdown = new Vehicle();

					dropdown.setVid((Integer) result[0]);
					dropdown.setVehicle_registration((String) result[1]);
					dropdown.setVehicle_Odometer((Integer) result[2]);
					dropdown.setVehicle_ExpectedOdameter((Integer) result[3]);

					Dtos.add(dropdown);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void transferVehiclePhoto(List<VehiclePhoto> list) throws Exception {
		org.fleetopgroup.persistence.document.VehiclePhoto				vehiclePhoto		= null;
		List<org.fleetopgroup.persistence.document.VehiclePhoto> 		vehiclePhotoList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				vehiclePhotoList	= new ArrayList<>();
				for(org.fleetopgroup.persistence.model.VehiclePhoto	document : list) {
					vehiclePhoto	= new org.fleetopgroup.persistence.document.VehiclePhoto();
					
					vehiclePhoto.set_id(document.getId());
					vehiclePhoto.setUploaddateOn(document.getUploaddateOn());
					vehiclePhoto.setPhotoTypeId(document.getPhotoTypeId());
					vehiclePhoto.setFilename(document.getFilename());
					vehiclePhoto.setVehid(document.getVehid());
					vehiclePhoto.setContent(document.getContent());
					vehiclePhoto.setContentType(document.getContentType());
					vehiclePhoto.setCompanyId(document.getCompanyId());
					vehiclePhoto.setCreatedById(document.getCreatedById());
					vehiclePhoto.setMarkForDelete(document.isMarkForDelete());
					
					vehiclePhotoList.add(vehiclePhoto);
				}
				System.err.println("Saving VehiclePhoto ....");
				mongoTemplate.insert(vehiclePhotoList, org.fleetopgroup.persistence.document.VehiclePhoto.class);
				System.err.println("Saved Successfully....");
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}
	
	@Override
	public long getVehiclePhotoMaxId() throws Exception {
		try {
			return vehiclePhotoRepository.getVehiclePhotoMaxId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	@Transactional
	public void UpdateOdameterUpdateDate(Integer vid, Timestamp date) throws Exception {
		
		vehicleRepository.UpdateOdameterUpdateDate(vid, date);
	}
	
	@Override
	public VehicleDto getVehicleByNumber(String vehicleNumber, Integer companyId) throws Exception {
		
		Query query = entityManager.createQuery(
				"SELECT f.vid, f.vehicle_registration, f.vStatusId,  f.tripSheetID, f.vehicle_Odometer, f.vehicle_ExpectedOdameter,  f.vehicleGroupId from Vehicle AS f where  f.vehicle_registration = :vehicleNumber AND f.company_Id = "
						+ companyId + " AND f.markForDelete = 0");

		query.setParameter("vehicleNumber", vehicleNumber);
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		VehicleDto select;
		if (vehicle != null) {
			select = new VehicleDto();
			select.setVid((Integer) vehicle[0]);
			select.setVehicle_registration((String) vehicle[1]);
			select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus((short) vehicle[2]));
			select.setTripSheetID((Long) vehicle[3]);
			Integer currentOdometer = 0, expectOdometer = 0, totalexpectOdometer = 0;
			if ((Integer) vehicle[4] != null) {
				currentOdometer = (Integer) vehicle[4];
			}
			if ((Integer) vehicle[5] != null && (Integer) vehicle[5] != 0) {
				expectOdometer = (Integer) vehicle[5];
			} else {
				expectOdometer = 0;
			}
			totalexpectOdometer = currentOdometer + expectOdometer;
			select.setVehicle_Odometer(currentOdometer);
			select.setVehicle_ExpectedOdameter(totalexpectOdometer);
			select.setvStatusId((short) vehicle[2]);
			select.setVehicleGroupId((Long) vehicle[6]);
			select.setVehicleExpectedKm((Integer) vehicle[5]);

		} else {
			return null;
		}
		return select;
	}
	
	@Override
	@Transactional
	public void updateLastLhpvSyncDateTime(Integer vid, Timestamp lastSyncDateTime) throws Exception {
		
		vehicleRepository.updateLastLhpvSyncDateTime(vid, lastSyncDateTime);
	}
	
	
	/*Dev Y Code Start for Vehicle Creation Report Part 1 */
	@Override
	public ValueObject getVehicleCreationReport(ValueObject valueInObject) throws Exception {
		
		ValueObject										valueOutObject;	
		Timestamp										startDateOfMonth		= null;
		Thread 											active					= null;
		Thread 											inactive				= null;
		Thread 											sold					= null;
		
		
		
		try {
			
			final String month				       		 = 	valueInObject.getString("monthRangeSelector", null);
			startDateOfMonth							= 	DateTimeUtility.getTimeStamp(month, DateTimeUtility.DD_MM_YY);
			final Timestamp fromDate					=	DateTimeUtility.getFirstDayOfMonth(startDateOfMonth); 
			final Timestamp toDate						=	DateTimeUtility.atEndOfDay(DateTimeUtility.getLastDayOfMonth(startDateOfMonth));
			
			valueOutObject	= new ValueObject();
			
			active = new Thread() {			
				public void run() {
					try{
						TypedQuery<Object[]> typedQuery = null;
						typedQuery = entityManager.createQuery(
								"SELECT Count(V.vid),C.company_id, C.company_name,V.vStatusId From Vehicle AS V "
										+ " INNER JOIN Company AS C on C.company_id = V.company_Id  "
										+ " where  V.vStatusId IN ("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE +","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE +","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP +")"
										+ " AND V.markForDelete = 0 "
										+ " AND V.created <= '"+ toDate +" ' OR (  V.created <= '"+ toDate +" ' AND V.vStatusId IN ("+VehicleStatusConstant.VEHICLE_STATUS_INACTIVE +","+VehicleStatusConstant.VEHICLE_STATUS_SURRENDER +","+VehicleStatusConstant.VEHICLE_STATUS_SOLD +") AND V.lastupdated > '"+toDate+"')    "
										+ " group by V.company_Id",
										Object[].class);
						
						List<Object[]> results = typedQuery.getResultList();

						List<VehicleDto> DtosActive = null;
						if (results != null && !results.isEmpty()) {
							
							
							DtosActive = new ArrayList<VehicleDto>();
							VehicleDto list = null;
							for (Object[] result : results) {
								
								list = new VehicleDto();
								
								list.setVehicleInActiveCount((long) 0);
								list.setVehicleSoldCount((long) 0);
								list.setVehicleActiveCount((long) result[0]);
								list.setVehicleCompanyId((int) result[1]);
								list.setCompanyName((String) result[2]);
								list.setvStatusId((short) result[3]);
								list.setCreated(month);
								
								
								DtosActive.add(list);
							}
							
						}
						valueOutObject.put("Active", DtosActive);
					}
					catch(Exception e){
						e.printStackTrace();						
					}				
				}
			};				
			active.start();
			
			
			inactive = new Thread() {				
				public void run() {
					try{
						TypedQuery<Object[]> typedQuery = null;

											
						typedQuery = entityManager.createQuery(
								"SELECT Count(V.vid), V.company_Id From Vehicle AS V "
										+ " where V.vStatusId = "+VehicleStatusConstant.VEHICLE_STATUS_INACTIVE+"   "
										+ " And V.lastupdated between '" + fromDate + "' AND '" + toDate +"' AND V.markForDelete = 0 "
										+ " group by V.company_Id",
										Object[].class);
						
						
						List<Object[]> results = typedQuery.getResultList();

						List<VehicleDto> DtosInactive = null;
						if (results != null && !results.isEmpty()) {
							
							
							DtosInactive = new ArrayList<VehicleDto>();
							VehicleDto list1 = null;
							for (Object[] result : results) {
								list1 = new VehicleDto();
							if(result !=null) {
								
								list1.setVehicleSoldCount((long) 0);
								list1.setVehicleActiveCount((long) 0);
								list1.setVehicleInActiveCount((long)result[0]);
								list1.setVehicleCompanyId((Integer) result[1]);
								list1.setCreated(month);
							
								}
								DtosInactive.add(list1);
							}
						}
						valueOutObject.put("Inactive", DtosInactive);
						
					}
					catch(Exception e){
						e.printStackTrace();						
					}				
				}				
			};
			inactive.start();
			
			
			sold=new Thread() {
				public void run() {
					try {
						TypedQuery<Object[]> typedQuery = null;

						typedQuery = entityManager.createQuery(
								" SELECT Count(V.vid), V.company_Id From Vehicle AS V "
										+ " where V.vStatusId = "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+"   "
										+ " And V.lastupdated between '" + fromDate + "' AND '" + toDate +"' AND V.markForDelete = 0 "
										+ " group by V.company_Id",
										Object[].class);
						
						List<Object[]> results = typedQuery.getResultList();

						List<VehicleDto> DtosSold = null;
						if (results != null && !results.isEmpty()) {
							
							DtosSold = new ArrayList<VehicleDto>();
							VehicleDto list2 = null;
							for (Object[] result : results) {							
								list2 = new VehicleDto();
								if(result != null) {

									list2.setVehicleActiveCount((long) 0);
									list2.setVehicleInActiveCount((long) 0);
									list2.setVehicleSoldCount((long) result[0]);
									list2.setVehicleCompanyId((Integer) result[1]);
									list2.setCreated(month);
									
								}
								DtosSold.add(list2);
							}
						}
						valueOutObject.put("Sold", DtosSold);
					}
					catch(Exception e) {
						e.printStackTrace();							
					}				
				}				
			};
			sold.start();
			
			active.join();
			inactive.join();
			sold.join();
			
			return	valueOutObject;
		} catch (Exception e) {
			LOGGER.error("Error : ", e);
			throw e;
		} finally {

		}
	} 
	/* Dev Y Code End for Vehicle Creation Report Part 1 */
	
	
	/*Dev Y Code Start for Vehicle Creation Report Details Part 2 */
	@Override
	public List<VehicleDto> getVehicleCreationReportDetails(long cId, long vStatusId, String month) throws Exception {
		
		Timestamp			startDateOfMonth		= null;
		SimpleDateFormat simpleDateFormat 			= new SimpleDateFormat("dd-MM-yyyy");
		try {
			
			startDateOfMonth			= 	DateTimeUtility.getTimeStamp(month, DateTimeUtility.DD_MM_YY);
			final Timestamp fromDate	=	DateTimeUtility.getFirstDayOfMonth(startDateOfMonth); 
			final Timestamp toDate		=	DateTimeUtility.atEndOfDay(DateTimeUtility.getLastDayOfMonth(startDateOfMonth));
			
			
			TypedQuery<Object[]> typedQuery = null;
			if(VehicleStatusConstant. VEHICLE_STATUS_ACTIVE	== vStatusId) {
				typedQuery = entityManager.createQuery(
					" SELECT vid, vehicle_registration, lastupdated, created, vStatusId FROM Vehicle V "
							+ " where vStatusId IN ("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE +","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE +","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP +")  "
							+ " AND created <= '" + toDate + " ' And company_Id ="+cId+" OR (created <= '" + toDate + " ' And company_Id ="+cId+"  AND V.vStatusId IN ("+VehicleStatusConstant.VEHICLE_STATUS_INACTIVE +","+VehicleStatusConstant.VEHICLE_STATUS_SURRENDER +","+VehicleStatusConstant.VEHICLE_STATUS_SOLD +") AND V.lastupdated > '"+toDate+"') "
							+ " ORDER BY lastupdated desc ",							
							Object[].class);	
			} else {
				typedQuery = entityManager.createQuery(
					" SELECT vid, vehicle_registration, lastupdated, created, vStatusId FROM Vehicle "
							+ " where vStatusId = "+vStatusId+"   "
							+ " AND lastupdated between  '" + fromDate + "' AND '" + toDate +"' AND markForDelete = 0 " 
							+ " And company_Id ="+cId+" ORDER BY lastupdated desc ",							
							Object[].class);
			}
			
					List<Object[]> results = typedQuery.getResultList();
		
					List<VehicleDto> Dtos = null;
					if (results != null && !results.isEmpty()) {
						
						Dtos = new ArrayList<VehicleDto>();
						VehicleDto list = null;
						for (Object[] result : results) {							
							list = new VehicleDto();
							if(result != null) {
								list.setVid((Integer) result[0]);
								list.setVehicle_registration((String) result[1]);
								list.setLastupdatedOn((Date) result[2]);
								list.setCreatedOn((Date) result[3]);
								list.setCreated(simpleDateFormat.format(list.getCreatedOn()));								
								list.setLastupdated(simpleDateFormat.format(list.getLastupdatedOn()));
								list.setvStatusId((short) result[4]);
								list.setVehicle_Status(VehicleStatusConstant.getVehicleStatus((short) result[4]));
							}
							Dtos.add(list);
						}
					}
			
			
			return	Dtos;
		} catch (Exception e) {
			LOGGER.error("Error : ", e);
			throw e;
		} finally {

		}
	} 

	/*Dev Y Code End for Vehicle Creation Report Details Part 2 */
	
	@Override
	public ValueObject getVehicleWiseBatteryReport(ValueObject valueObject) throws Exception {		
		ValueObject											valueOutObject;
		Integer												vid						= 0;
		ValueObject						                  	tableConfig				= null;
		List<BatteryDto> 									currentBatteryList		= null;	
		List<BatteryDto>									finalBatteryHistoryList = null;							
		List<BatteryDto>									listToMap				= null;
		HashMap<String, BatteryDto>  						dtosMap 				= null;
		List<BatteryDto>									finalBatteryList		= null;
		
		try {
			CustomUserDetails userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
				vid							= valueObject.getInt("vehicleId", 0);
			
				String vehicleId = "";
				
				if(vid != 0 )
				{					
					vehicleId = " AND BT.vid = "+ vid +" ";
				}				
				
				String query = " " + vehicleId + "  ";				
				
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VEHICLE_WISE_BATTERY_TABLE_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);	
				
				valueOutObject = new ValueObject();
				valueOutObject.put("tableConfig", tableConfig.getHtData());
				valueOutObject.put("company",
						userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
				
				
				currentBatteryList = VehicleDaoImpl.getVehicleWiseBatteryReportList(query, userDetails.getCompany_id());
				valueOutObject.put("CurrentBatteryList", currentBatteryList);
				
				if(currentBatteryList != null && !currentBatteryList.isEmpty())
				{
					for(BatteryDto battery : currentBatteryList) {
						
						BatteryDto batteryHistory = VehicleDaoImpl.getBatteryHistoryList(battery.getVid(), 
								battery.getLayoutPosition(), battery.getBatteryId(), userDetails.getCompany_id());
						
						if(batteryHistory != null) {
							finalBatteryHistoryList	= new ArrayList<BatteryDto>();
							finalBatteryHistoryList.add(batteryHistory);
						}
					}
				
					listToMap = new ArrayList<>();
					if(currentBatteryList != null)
						listToMap.addAll(currentBatteryList);
					if(finalBatteryHistoryList != null)
						listToMap.addAll(finalBatteryHistoryList);
					
					dtosMap = getVehicleWiseBatteryMap(listToMap);
					
					if(dtosMap != null) {
						finalBatteryList = new ArrayList<BatteryDto>();
						finalBatteryList.addAll(dtosMap.values());
					}
					valueOutObject.put("VehicleBattery", finalBatteryList);
			}	
				
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
		}
	}
	
	@Override
	public HashMap<String ,BatteryDto> getVehicleWiseBatteryMap(List<BatteryDto> Dtos) throws Exception {
		try {

			HashMap<String ,BatteryDto> dtosMap = null;
			if (Dtos != null && !Dtos.isEmpty()) {
				dtosMap = new HashMap<String ,BatteryDto>();
				BatteryDto list = null;
				for (BatteryDto batteryDto : Dtos) {
					
					if(dtosMap.containsKey(batteryDto.getVid()+"_"+batteryDto.getLayoutPosition())) {
						list 			= dtosMap.get(batteryDto.getVid()+"_"+batteryDto.getLayoutPosition());
						
						list.setPreviousBatteryManufacturer(batteryDto.getPreviousBatteryManufacturer());
						list.setPreviousSerialNumber(batteryDto.getPreviousSerialNumber());
						list.setPreviousAssignDate(batteryDto.getPreviousAssignDate());
						list.setPreviousUsesOdometer(batteryDto.getPreviousUsesOdometer());
					} else {
						
						dtosMap.put(batteryDto.getVid()+"_"+batteryDto.getLayoutPosition(),batteryDto);
					}
				}
			}
			return dtosMap;

		}catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleWiseTyreReport(ValueObject valueObject) throws Exception {		
		ValueObject											valueOutObject;
		Integer												vid						= 0;
		ValueObject						                  	tableConfig				= null;
		List<InventoryTyreDto> 								currentTyreList			= null;	
		List<InventoryTyreDto>								finalTyreHistoryList 	= null;							
		List<InventoryTyreDto>								listToMap				= null;
		HashMap<String, InventoryTyreDto>  					dtosMap 				= null;
		List<InventoryTyreDto>								finalTyreList			= null;
		String												dateRange				= "";
		String												startDate				= "";
		String												endDate					= "";
		try {
			CustomUserDetails userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
				vid							= valueObject.getInt("vehicleId", 0);
				if(!valueObject.getString("startDate", "").equalsIgnoreCase("")) {
					startDate 					= DateTimeUtility.getSqlStrDateFromStrDate(valueObject.getString("startDate", ""), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					endDate						= DateTimeUtility.getSqlStrDateFromStrDate(valueObject.getString("endDate", ""), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				}
				String vehicleId = "";
				
				if(vid != 0 )
				{					
					vehicleId = " AND IT.VEHICLE_ID = "+ vid +" ";
				}				
				
				if(startDate != "")
				{		
					dateRange = " AND IT.TYRE_ASSIGN_DATE BETWEEN '"+ startDate +"' AND '"+endDate+"' ";
				}
				String query = " " + vehicleId + " "+dateRange+" ";				
				
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VEHICLE_WISE_TYRE_TABLE_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);	
				
				valueOutObject = new ValueObject();
				valueOutObject.put("tableConfig", tableConfig.getHtData());
				valueOutObject.put("company",
						userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
				
				currentTyreList = VehicleDaoImpl.getVehicleWiseTyreReportList(query, userDetails.getCompany_id());
				valueOutObject.put("CurrentBatteryList", currentTyreList);
				
				if(currentTyreList != null && !currentTyreList.isEmpty()) {
				
					for(InventoryTyreDto tyre : currentTyreList) {
						
						String position = tyre.getPOSITION().split("-")[0];
						
						InventoryTyreDto tyreHistory = VehicleDaoImpl.getTyreHistoryList(tyre.getVEHICLE_ID(), 
								position, tyre.getTYRE_ID(), tyre.getAXLE()+"", userDetails.getCompany_id());
						
						if(tyreHistory != null) {
							finalTyreHistoryList = new ArrayList<InventoryTyreDto>();
							finalTyreHistoryList.add(tyreHistory);
						}
					}
					
					listToMap = new ArrayList<>();
					if(currentTyreList != null)
						listToMap.addAll(currentTyreList);
					if(finalTyreHistoryList != null)
						listToMap.addAll(finalTyreHistoryList);
					
					dtosMap = getVehicleWiseTyreMap(listToMap);
					
					if(dtosMap != null) {
						finalTyreList = new ArrayList<InventoryTyreDto>();
						finalTyreList.addAll(dtosMap.values());
					}
					
					valueOutObject.put("VehicleTyre", finalTyreList);
			}
				
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
		}
	}
	
	@Override
	public HashMap<String ,InventoryTyreDto> getVehicleWiseTyreMap(List<InventoryTyreDto> Dtos) throws Exception {
		try {

			HashMap<String ,InventoryTyreDto> dtosMap = null;
			if (Dtos != null && !Dtos.isEmpty()) {
				dtosMap = new HashMap<String ,InventoryTyreDto>();
				InventoryTyreDto list = null;
				for (InventoryTyreDto tyreDto : Dtos) {
					if(dtosMap.containsKey(tyreDto.getVEHICLE_ID()+"_"+tyreDto.getPOSITION()+"_"+tyreDto.getAXLE())) {
						list 			= dtosMap.get(tyreDto.getVEHICLE_ID()+"_"+tyreDto.getPOSITION()+"_"+tyreDto.getAXLE());
						
						list.setPreviousTyreManufacturer(tyreDto.getPreviousTyreManufacturer());
						list.setPreviousSerialNumber(tyreDto.getPreviousSerialNumber());
						list.setPreviousAssignedDate(tyreDto.getPreviousAssignedDate());
						list.setPreviousTyreOpenOdo(tyreDto.getPreviousTyreOpenOdo());
						if(tyreDto.getPreviousTyreUsage() > 0) {
							list.setPreviousTyreUsage(list.getOPEN_ODOMETER() - tyreDto.getPreviousTyreUsage());
						} else {
							list.setPreviousTyreUsage(0);
						}
						
						
					} else {
						
						dtosMap.put(tyreDto.getVEHICLE_ID()+"_"+tyreDto.getPOSITION()+"_"+tyreDto.getAXLE(),tyreDto);
					}
				}
			}
			return dtosMap;

		}catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public HashMap<Integer, Long> getActiveVehicleCountMap() throws Exception {
		try{
			TypedQuery<Object[]> typedQuery 	= null;
			HashMap<Integer, Long>	map		    = null;
			typedQuery = entityManager.createQuery(
					"SELECT Count(V.vid), V.company_Id "
							+ " From Vehicle AS V "
							+ " where  V.vStatusId IN ("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE +","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE +","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP +")"
							+ " AND V.markForDelete = 0 "
							+ " group by V.company_Id",
							Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();

			if (results != null && !results.isEmpty()) {
				
				map	= new HashMap<>();
				
				for (Object[] result : results) {
					
					map.put((Integer)result[1], (Long)result[0]);
					
				}
			}
			
			return map;
			
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}				
	}
	
	
	@Override
	@Transactional
	public ValueObject saveDriverMonthlySalary(ValueObject	valueObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		Timestamp 						updatedDateTime 				= null;
		int								vehicleId						= 0;
		ValueObject						valueOutObject					= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			updatedDateTime 	= new java.sql.Timestamp(new Date().getTime());
			valueOutObject		= new ValueObject();
			vehicleId			= valueObject.getInt("vehicleId");
			
			entityManager.createQuery(" UPDATE Vehicle SET lastModifiedById = "+ userDetails.getId()
						+ " , lastupdated = '"+ updatedDateTime +"' "
						+ ", driverMonthlySalary = "+valueObject.getDouble("amountForDriver")
						+ " WHERE vid = " + vehicleId +" AND company_Id = "+userDetails.getCompany_id()+ " AND markForDelete = 0 " 
					)
				.executeUpdate();
			valueOutObject.put("SalarySaved", true);
			
			return valueOutObject;
			
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails						= null;
			updatedDateTime 				= null;
		}
	}
	
	@Override
	public long getActiveVehicleCount(int companyId) throws Exception {
		
			Query queryt = 	null;
			queryt = entityManager.createQuery(
					"SELECT Count(V.vid) "
							+ " From Vehicle AS V "
							+ " where  V.vStatusId IN ("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE +","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE +","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP +")"
							+ " AND V.markForDelete = 0 AND V.company_Id = "+companyId+" ",
							Object[].class);
			
			Long count = (long) 0;
			try {
				
				if((Long) queryt.getSingleResult() != null) {
					count = (Long) queryt.getSingleResult();
				}
				
			} catch (NoResultException nre) {
				
			}
			
			return count;
						
	}
	
	@Override
	public List<VehicleDto> getActiveVehicleList(int companyId) throws Exception {
		TypedQuery<Object[]> query = null;
		
		query = entityManager.createQuery(
				"SELECT V.vid, V.vehicle_registration, V.vehicle_Odometer, V.created, V.vStatusId, V.vehicle_ExpectedOdameter "
						+ " From Vehicle AS V "
						+ " where  V.vStatusId IN ("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE +","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE +","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP +")"
						+ " AND V.markForDelete = 0 AND V.company_Id = "+companyId+" ",
						Object[].class);
		List<Object[]> results = query.getResultList();

		List<VehicleDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleDto>();
			VehicleDto select = null;
			for (Object[] vehicle : results) {

				select = new VehicleDto();
				select.setVid((int) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_Odometer((int) vehicle[2]);
				select.setCreated(dateFormat.format((Timestamp) vehicle[3]));
				select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus((short) vehicle[4]));
				if(vehicle[5] != null) {
					select.setVehicle_ExpectedOdameter((Integer) vehicle[5]);
				}else {
					select.setVehicle_ExpectedOdameter(0);
					
				}
				Dtos.add(select);
			}
		}
		return Dtos;

	}
	
	@Transactional
	public List<Vehicle> searchOnlyVehicleNameOnMobile(String vehicle,int companyId , long userId) throws Exception {

		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration, v.vStatusId, v.vehicleGroupId FROM Vehicle AS v "
					+ " where  lower(v.vehicle_registration) Like (:vehicle) and v.company_Id = "
					+ companyId + " and v.markForDelete = 0 ", Object[].class);
		} else {
			queryt = entityManager.createQuery("SELECT v.vid, v.vehicle_registration,v.vStatusId, v.vehicleGroupId FROM Vehicle AS v "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId and VGP.user_id ="
					+ userId + "" + " where  lower(v.vehicle_registration) Like (:vehicle) "
							+ "and v.company_Id = " + companyId + " and v.markForDelete = 0 ",
					Object[].class);
		}
		queryt.setParameter("vehicle","%"+vehicle+"%");
		List<Object[]> results = queryt.getResultList();

		List<Vehicle> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vehicle>();
			Vehicle dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vehicle();

				dropdown.setVid((Integer) result[0]);
				dropdown.setVehicle_registration((String) result[1]);
				dropdown.setvStatusId((short)result[2]);
				dropdown.setVehicleGroupId((long) result[3]);

				Dtos.add(dropdown);
			}
		}
		return Dtos;
	}

	
	@Override
	public ValueObject getBankWiseVehicleEmiDetailsReport(ValueObject valueObject) throws Exception {		
		ValueObject											valueOutObject;
		Integer												bankId					= 0;
		ValueObject						                  	tableConfig				= null;
		List<VehicleEmiDetailDto> 							vehicleEmiDetailsList	= null;	
		String 												query 					= "" ;
		
		try {
			CustomUserDetails userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			bankId							= valueObject.getInt("bankId", 0);
				
				if(bankId > 0) {
					query	=" AND BA.bankId  = "+bankId+"";
				}
				vehicleEmiDetailsList = vehicleEmi.getBankWiseVehicleEmiDetailsReport(query);
				
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.BANK_WISE_VEHICLE_EMI_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);	
				
				valueOutObject = new ValueObject();
				valueOutObject.put("tableConfig", tableConfig.getHtData());
				valueOutObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
				
				valueOutObject.put("vehicleEmiDetailsList", vehicleEmiDetailsList);
				
				
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
		}
	}
	@Override
	public ValueObject getVehicleWiseEmiDetailsReport(ValueObject valueObject) throws Exception {		
		ValueObject							valueOutObject					= null;
		CustomUserDetails 					userDetails						= null;
		Integer								vehicleId						= 0;
		ValueObject						    tableConfig						= null;
		List<VehicleEmiDetailDto>			allVehiclesEmiDetails			= null;
		VehicleEmiDetailDto					vehicleEmiDetail				= null;
		List<VehicleEmiDetailDto>			finalList						= null;
		List<VehicleEmiDetailDto>			emiLoanDateList					= null;
		List<VehicleEmiPaymentDetailsDto>	paymentList						= null;
		List<String>						paymentFinalList				= null;
		List<VehicleEmiPaymentDetailsDto>	vehicleEmiPaymentDetailList		= null;
		List<VehicleEmiPaymentDetailsDto>	paidFinalList					= null;
		List <Object> 						finalObjectList 				= null;
		HashMap<Long,Integer> 				vehicleEmiPaymentDetailsHM 		= null ;
		
		try {
			valueOutObject 		= new ValueObject();
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			vehicleId			= valueObject.getInt("vehicleId", 0);
			
			allVehiclesEmiDetails 	= vehicleEmi.getVehicleEmiDetailsList(vehicleId,userDetails.getCompany_id());
			finalList 		= new ArrayList<VehicleEmiDetailDto>();
			paidFinalList 	=  new ArrayList<VehicleEmiPaymentDetailsDto>();
			finalObjectList	= new ArrayList<Object>();
			vehicleEmiPaymentDetailsHM= new HashMap<>();
			if(allVehiclesEmiDetails != null) {
			for(VehicleEmiDetailDto dto : allVehiclesEmiDetails) {
				if(dto != null) {
					vehicleEmiDetail 	= vehicleEmi.getVehicleEmiDetailsById(dto.getVehicleEmiDetailsId(), userDetails.getCompany_id());
					emiLoanDateList 	= new ArrayList<VehicleEmiDetailDto>();
					
					for(int j = 0; j< vehicleEmiDetail.getTenure(); j++) {

						VehicleEmiDetailDto vehicleEmiDate = (VehicleEmiDetailDto) vehicleEmiDetail.clone();
						vehicleEmiDate.setLoanEmiDate(DateTimeUtility.getNextDateByMonth(vehicleEmiDate.getLoanStartDate(), j));

						emiLoanDateList.add(vehicleEmiDate);
					}
				
					if(vehicleEmiDetail.getEmiStatus() == VehicleEmiPaymentStatus.EMI_STATUS_IN_PROCESS) {
						
						paymentList = vehicleEmi.getVehicleEmiPaymentPaidList(vehicleEmiDetail.getVehicleEmiDetailsId(),userDetails.getCompany_id());

						paymentFinalList = new ArrayList<String>();
						
						if(paymentList != null) {
							for(VehicleEmiPaymentDetailsDto paid : paymentList) {
								paymentFinalList.add(paid.getEmiLoanDateStr());
							}
						}
						
						for(VehicleEmiDetailDto emiDetailsList : emiLoanDateList) {

							if(paymentList == null) {
								finalList.add(emiDetailsList);
							} else if (!paymentFinalList.contains(emiDetailsList.getLoanEmiDate())) {
								emiDetailsList.setPaymentStatus("NOT PAID");
								finalList.add(emiDetailsList);
							}

						}

					}
					vehicleEmiPaymentDetailList = vehicleEmi.getVehicleEmiPaymentPaidList(dto.getVehicleEmiDetailsId(), userDetails.getCompany_id());
					if(vehicleEmiPaymentDetailList != null) {
						for(VehicleEmiPaymentDetailsDto paid : vehicleEmiPaymentDetailList) {
							vehicleEmiPaymentDetailsHM.put(paid.getVehicleEmiDetailsId(), vehicleEmiPaymentDetailList.size());
						}
						paidFinalList.addAll(vehicleEmiPaymentDetailList);
					}
					

				}
			}
			}if(allVehiclesEmiDetails != null) {
			for(VehicleEmiDetailDto dto : allVehiclesEmiDetails) {
				if(vehicleEmiPaymentDetailsHM != null) {
				for(Long key : vehicleEmiPaymentDetailsHM.keySet()) {
					if(vehicleEmiPaymentDetailsHM.containsKey(dto.getVehicleEmiDetailsId())) {
						if(key != null) {
							dto.setPaidEmi(vehicleEmiPaymentDetailsHM.get(key));
							if(dto.getEmiStatus() == VehicleEmiPaymentStatus.EMI_STATUS_SETTLEMENT ) {
								dto.setPaymentStatus(VehicleEmiPaymentStatus.EMI_STATUS_SETTLEMENT_NAME);
							}
						}
					}
				}
				}
			}
			}
			finalObjectList.addAll(paidFinalList);
			finalObjectList.addAll(finalList);
			valueOutObject.put("allVehiclesEmiDetails", allVehiclesEmiDetails);
			valueOutObject.put("finalObjectList", finalObjectList);
			
			
			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VEHICLE_WISE_EMI_DATA_FILE_PATH);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);	
			
			valueOutObject.put("tableConfig", tableConfig.getHtData());
			valueOutObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueOutObject					= null;
			userDetails						= null;
			vehicleId						= 0;
			tableConfig						= null;
			allVehiclesEmiDetails			= null;
			vehicleEmiDetail				= null;
			finalList						= null;
			emiLoanDateList					= null;
			paymentList						= null;
			paymentFinalList				= null;
			vehicleEmiPaymentDetailList		= null;
			paidFinalList					= null;
			finalObjectList 				= null;
			vehicleEmiPaymentDetailsHM 		= null ;
		}
	}
	

	@Override
	public ValueObject getVehicleList(ValueObject object) throws Exception {
		List<VehicleDto> 		vehicleList				= null;
		List<Vehicle> 			vehicle					= null;
		ValueObject				valueOutObj				= null;
		String					text					= null;
		int						companyId				= 0;
		long					userId					= 0;
		try {
			
			vehicleList 	= new ArrayList<VehicleDto>();
			valueOutObj 	= new ValueObject();
			text			= object.getString("term");
			companyId		= object.getInt("companyId");
			userId			= object.getLong("userId");
			
			vehicle 		= searchOnlyVehicleNameOnMobile(text,companyId,userId);
			
			if (vehicle != null && !vehicle.isEmpty()) {
				for (Vehicle add : vehicle) {
					VehicleDto wadd = new VehicleDto();
					wadd.setVid(add.getVid());
					wadd.setVehicle_registration(add.getVehicle_registration());
					wadd.setVehicleGroupId(add.getVehicleGroupId());
					vehicleList.add(wadd);
				}
			}
			
			valueOutObj.put("vehicleList", vehicleList);
			
			return valueOutObj;
		}catch(Exception e) {
			throw e;
		} finally {
			vehicleList				= null;
			vehicle					= null;
			valueOutObj				= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveVehicleKmplDetails(ValueObject object) throws Exception {
		int 			vehicleKmplId				= 0;
		int 			companyId					= 0;
		double 			expectedMileageFrom			= 0.0;
		double 			expectedMileageTo			= 0.0;
		try {
			
			vehicleKmplId					= object.getInt("vehicleKmplId");
			companyId						= object.getInt("companyId");
			expectedMileageFrom  			= object.getDouble("expectedMileageFrom");
			expectedMileageTo  				= object.getDouble("expectedMileageTo");
			
			vehicleRepository.updateVehicleKmplDetails(vehicleKmplId, expectedMileageFrom, 
					expectedMileageTo, companyId);
			object.put("kmplUpdated", true);
			
			Vehicle vehicle = vehicleRepository.getVehicel(vehicleKmplId, companyId);
			object.put("vehicle_ExpectedMileage", vehicle.getVehicle_ExpectedMileage());
			object.put("vehicle_ExpectedMileage_to", vehicle.getVehicle_ExpectedMileage_to());
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public ValueObject saveVehicleDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails							= null;
		Vehicle						vehicle								= null;
		VehicleDto 					validate							= null; 
		VehicleOdometerHistory 		vehicleUpdateHistory 				= null;
		HashMap<String, Object>		configuration						= null;
		HashMap<String, Object>		configurationInspection				= null;
		boolean						validateChasisNo	   				= false;
		boolean						validateVGroup	   					= false;
		boolean						validateEnginNo      				= false;
		boolean						validateVehicleType      			= false;
		boolean						validateVehicleStatus     			= false;
		boolean						validateVehicleFuelType     		= false;
		boolean						validateVehicleOdometer    			= false;
		boolean						validateVehicleExpectedOdometer     = false;
		boolean						validateVehicleExpectedMileage     	= false;
		Vehicle 					savedVehicel						= null;
		String 						vehicleRegistration					= "";
		String 						vehicleChassisNumber				= "";
		String 						vehicleEngineNumber					= "";
		String 						query								= "";
		HashMap<String, Object>		SPconfiguration						= null;
		List<ServiceProgramAsignmentDetails>	 asignmentDetails		= null;
		VehicleTyreLayoutPosition       			vehicleTyreLayoutPosition					= null;
		List<VehicleModelTyreLayoutPositionDto> vehicleModelTyreLayoutPositionList = null;
		List<VehicleTyreLayoutPosition>       		vehicleTyreLayoutPositionList				= null;
		
		
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 					= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			SPconfiguration 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_PROGRAM_CONFIGURATION_CONFIG);
			configurationInspection			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			validateVGroup	   				= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VGROUP, false); 
			validateChasisNo	   			= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_CHASIS_NO, false); 
			validateEnginNo      			= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_ENGINE_NO, false); 
			validateVehicleType      		= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VEHICLE_TYPE, false); 
			validateVehicleStatus      		= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VEHICLE_TYPE, false); 
			validateVehicleFuelType    	 	= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VEHICLE_FUEL_TYPE, false); 
			validateVehicleOdometer     	= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VEHICLE_ODOMETER, false); 
			validateVehicleExpectedOdometer = (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VEHICLE_EXPECTED_ODOMETER, false); 
			validateVehicleExpectedMileage  = (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VEHICLE_EXPECTED_MILEAGE, false); 
			
			
			if((valueObject.getString("vehicleRegistrationNumber",null) == null || valueObject.getString("vehicleRegistrationNumber",null).trim().equalsIgnoreCase(""))) {
				valueObject.put("vehicleNumberNotFound", true);
				return valueObject;
			}if(validateChasisNo && (valueObject.getString("vehicleChasisNumber",null) == null || valueObject.getString("vehicleChasisNumber",null).trim().equalsIgnoreCase(""))) {
				valueObject.put("ChasisNoNotFound", true);
				return valueObject;
			}if(validateEnginNo && (valueObject.getString("vehicleEngineNumber", null) == null || valueObject.getString("vehicleEngineNumber", null).trim().equalsIgnoreCase(""))) {
				valueObject.put("EngineNoNotFound", true);
				return valueObject;
			}if(validateVehicleType && valueObject.get("VehicleTypeId") == "" ) {
				valueObject.put("VehicleTypeNoNotFound", true);
				return valueObject;
			}if(validateVGroup && valueObject.get("vehicleGroupId")  == "" ) {
				valueObject.put("VGroupNotFound", true);
				return valueObject;
			}if(validateVehicleStatus && valueObject.get("vehicleStatusId") == null) {
				valueObject.put("VehicleStatusNotFound", true);
				return valueObject;
			}if(validateVehicleFuelType && valueObject.get("vehicleFuel") == null) {
				valueObject.put("VehicleFuelTypeNotFound", true);
				return valueObject;
			}if(validateVehicleOdometer && valueObject.get("vehicleOdometer") == null) {
				valueObject.put("VehicleOdometerNotFound", true);
				return valueObject;
			}if(validateVehicleExpectedOdometer && valueObject.get("maximumOdometer") == null) {
				valueObject.put("VehicleExpectedOdometerNotFound", true);
				return valueObject;
			}if(validateVehicleExpectedMileage &&(valueObject.get("expectedMileageFrom") == null || valueObject.get("expectedMileageTo") == null)) {
				valueObject.put("VehicleExpectedMileageNotFound", true);
				return valueObject;
			}if(valueObject.getInt("maximumOdometer", 0)>Integer.parseInt(configuration.getOrDefault("allowMaximumOdometer", "5000")+"")){
				valueObject.put("aboveMaxOdometer", true);
				valueObject.put("maxAllowedOdo", configuration.getOrDefault("allowMaximumOdometer", 5000));
				return valueObject;
			}
			
			if(valueObject.getString("vehicleRegistrationNumber") != null ){
				vehicleRegistration	= valueObject.getString("vehicleRegistrationNumber");
			}
			if(valueObject.getString("vehicleChasisNumber") != null ){
				vehicleChassisNumber	= valueObject.getString("vehicleChasisNumber");
			}
			if(valueObject.getString("vehicleEngineNumber") != null ){
				vehicleEngineNumber	= valueObject.getString("vehicleEngineNumber");
			}
			
			if(validateChasisNo && validateEnginNo) { 
				query	= "(V.vehicle_registration = '"+vehicleRegistration+"' OR V.vehicle_chasis = '"+vehicleChassisNumber+"' OR V.vehicle_engine = '"+vehicleEngineNumber+"') ";
			}else if(validateChasisNo) {
				query	= "(V.vehicle_registration =  '"+vehicleRegistration+"' OR V.vehicle_chasis = '"+vehicleChassisNumber+"')";
			}else if(validateEnginNo) {
				query	= "(V.vehicle_registration = '"+vehicleRegistration+"' OR V.vehicle_engine = '"+vehicleEngineNumber+"')";
			}else {
				query	= "(V.vehicle_registration = '"+vehicleRegistration+"')";
			}
			
			validate = validateVehicle(query); // checking duplicate Entries
			
			if(validate != null) {// If duplicate Entry Found This will return Message respective to the duplicate fie;ld
				if(valueObject.getString("vehicleRegistrationNumber").equalsIgnoreCase(validate.getVehicle_registration().trim())) {
					valueObject.put("VehicleRegExist", true);
				}else if(valueObject.getString("vehicleChasisNumber").equalsIgnoreCase(validate.getVehicle_chasis().trim())) {
					valueObject.put("VehicleChasisExist", true);
				}else if(valueObject.getString("vehicleEngineNumber").equalsIgnoreCase(validate.getVehicle_engine().trim())) {
					valueObject.put("VehicleEngineExist", true);
				}else {
					valueObject.put("alreadyExist", true);
				}
			}else {
				
					vehicle 				= VBL.prepareVehicleDetails(valueObject, userDetails);
					savedVehicel			= addVehicle(vehicle);
					
					if(vehicle.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_SOLD) {
						if(!(boolean) configuration.get("selectServiceProgramOnModal")) {
							if(valueObject.getLong("serviceProgramId",0) > 0) {
								List<ServiceProgramSchedulesDto> 	serviceScheduleList		= serviceProgramService.getServiceProgramSchedulesBtId(valueObject.getLong("serviceProgramId",0));
								if(serviceScheduleList != null && !serviceScheduleList.isEmpty()) {
									for(ServiceProgramSchedulesDto schedulesDto : serviceScheduleList) {
										getServiceReminderListToSave(schedulesDto, savedVehicel);
									}
								}
							}
						}else {
							if(vehicle.getVehicleModalId() != null && vehicle.getVehicleModalId() > 0) {
								if((boolean) SPconfiguration.getOrDefault("vehicleBranchWiseProgram", false)) {
									
									asignmentDetails	=		asignmentDetailsService.getServiceProgramListByVehicleTypeModalAndBranch(
											vehicle.getVehicleTypeId(), vehicle.getVehicleModalId(),vehicle.getBranchId(), userDetails.getCompany_id());
								}else {
									asignmentDetails	=		asignmentDetailsService.getServiceProgramListByVehicleTypeAndModal(
											vehicle.getVehicleTypeId(), vehicle.getVehicleModalId(), userDetails.getCompany_id());
									
								}
								if(asignmentDetails != null && !asignmentDetails.isEmpty()) {
									for (ServiceProgramAsignmentDetails serviceProgramAsignmentDetails : asignmentDetails) {
										List<ServiceProgramSchedulesDto> 	serviceScheduleList		= serviceProgramService.getServiceProgramSchedulesBtId(serviceProgramAsignmentDetails.getServiceProgramId());
										if(serviceScheduleList != null && !serviceScheduleList.isEmpty()) {
											for(ServiceProgramSchedulesDto schedulesDto : serviceScheduleList) {
												getServiceReminderListToSave(schedulesDto, savedVehicel);
											}
										}
									}
								}
							}
						}
					}
					
					vehicleUpdateHistory 	= VOHBL.prepareOdometerGetHistoryToVehicle(savedVehicel);
					vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
					vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					valueObject.put("BranchId", savedVehicel.getBranchId());
					if((boolean) configurationInspection.getOrDefault("sheetToVehicleType",false) && (savedVehicel.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_INACTIVE) && (savedVehicel.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_SOLD) && (savedVehicel.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_SURRENDER))
					prepareForInspectionSheetAssignForNewVid(savedVehicel.getVid(),savedVehicel.getVehicleTypeId(),valueObject.getInt("BranchId",0));
					valueObject.put("vid", savedVehicel.getVid());
					valueObject.put("save", true);
					
					vehicleModelTyreLayoutPositionList 		= new ArrayList<>();
					vehicleTyreLayoutPositionList 			= new ArrayList<>();
					
					valueObject.put("vehicleModelId", 	valueObject.getLong("vehicleModel",0));
					valueObject.put("companyId", userDetails.getCompany_id());
					valueObject.put("userId", userDetails.getId());
					
					valueObject = vehicleModelTyreLayoutService.getVehicleModelTyreLayoutPositionByVehicleModelId(valueObject);
					vehicleModelTyreLayoutPositionList =	(List<VehicleModelTyreLayoutPositionDto>) valueObject.get("vehicleModelTyreLayoutPositionList");
					
					if(vehicleModelTyreLayoutPositionList != null && !vehicleModelTyreLayoutPositionList.isEmpty()) {
						for( VehicleModelTyreLayoutPositionDto dto : vehicleModelTyreLayoutPositionList) {
							vehicleTyreLayoutPosition = vModelTyreLayoutBL.prepareMultipleVehicleTyreLayoutPosition(dto.getPosition(),dto.getTyreModelId(),valueObject);
							vehicleTyreLayoutPositionList.add(vehicleTyreLayoutPosition);
						}
						
						vehicleTyreLayoutPositionRepository.saveAll(vehicleTyreLayoutPositionList);
					}
			}
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;       
			validate 						= null;       
			vehicle							= null;       
			vehicleUpdateHistory 			= null;       
			configuration					= null;       
			                                              
		}
	}
	@Transactional
	public  void getServiceReminderListToSave(ServiceProgramSchedulesDto schedulesDto, Vehicle vehicle) throws Exception{
		ServiceReminder						serviceReminder			= null;
		ServiceReminderSequenceCounter		counter					= null;
		ServiceProgramSchedules serviceProgramSchedules             = null;
		UserProfile userProfile                                     = null;
		try {
					if(vehicle.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_SOLD) {
						
						serviceReminder	= new ServiceReminder();
						counter = serviceReminderSequenceService.findNextServiceReminderNumber(vehicle.getCompany_Id());
						serviceReminder.setService_Number(counter.getNextVal());
						serviceReminder.setVid(vehicle.getVid());
						serviceReminder.setServiceTypeId(schedulesDto.getJobTypeId());
						serviceReminder.setServiceSubTypeId(schedulesDto.getJobSubTypeId());
						serviceReminder.setMeter_interval(schedulesDto.getMeterInterval());
						serviceReminder.setVehicle_currentOdometer(vehicle.getVehicle_Odometer());
						serviceReminder.setMeter_serviceodometer(vehicle.getVehicle_Odometer() + schedulesDto.getMeterInterval());
						serviceReminder.setMeter_threshold(schedulesDto.getMeterThreshold());
						serviceReminder.setMeter_servicethreshold_odometer(serviceReminder.getMeter_serviceodometer() - schedulesDto.getMeterThreshold() );
						serviceReminder.setTime_interval(schedulesDto.getTimeInterval());
						serviceReminder.setTime_intervalperiodId(schedulesDto.getTimeIntervalType());
						serviceReminder.setTime_interval_currentdate(new Date());
						serviceReminder.setTime_threshold(schedulesDto.getTimeThreshold());
						serviceReminder.setTime_thresholdperiodId(schedulesDto.getTimeThresholdType());
						serviceReminder.setService_remider_howtimes(1);
						serviceReminder.setServiceStatusId(ServiceReminderDto.SERVICE_REMINDER_STATUS_ACTIVE);
						serviceReminder.setCompanyId(vehicle.getCompany_Id());
						serviceReminder.setVehicleGroupId(vehicle.getVehicleGroupId());
						serviceReminder.setCreatedById(vehicle.getCreatedById());
						serviceReminder.setLastModifiedById(vehicle.getLastModifiedById());
						serviceReminder.setCreated(new Date());
						serviceReminder.setLastupdated(new Date());
						// time interval not equal to null only enter the value
						if (serviceReminder.getTime_interval() != null) {
							
							// get time interval calculation to service to get
							// service time interval days
							Integer time_Intervalperiod = 0;
							if (serviceReminder.getTime_intervalperiodId() > 0) {
								time_Intervalperiod = serviceReminder.getTime_interval();
							}
							Integer timeserviceDaysPeriod = 0;
							switch (serviceReminder.getTime_intervalperiodId()) {
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
								timeserviceDaysPeriod = time_Intervalperiod;
								break;
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
								timeserviceDaysPeriod = time_Intervalperiod * 7;
								break;
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
								timeserviceDaysPeriod = time_Intervalperiod * 30;
								break;
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
								timeserviceDaysPeriod = time_Intervalperiod * 365;
								break;
								
							default:
								timeserviceDaysPeriod = time_Intervalperiod;
								break;
							}
							
							final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							
							final Calendar calendar = Calendar.getInstance();
							calendar.setTime(new Date());
							calendar.add(Calendar.DAY_OF_YEAR, timeserviceDaysPeriod);
							// System.out.println("Service time_date =" +
							// format.format(calendar.getTime()));
							
							// fuel date converted String to date Format
							java.util.Date servicedate = format.parse(format.format(calendar.getTime()));
							java.sql.Date Time_servicedate = new java.sql.Date(servicedate.getTime());
							
							// save Service Time_interval Reminder
							serviceReminder.setTime_servicedate(Time_servicedate);
							
							if (serviceReminder.getTime_threshold() != null) {
								
								Integer Time_threshold = 0;
								switch (serviceReminder.getTime_thresholdperiodId()) {
								case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
									Time_threshold = serviceReminder.getTime_threshold();
									break;
								case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
									Time_threshold = serviceReminder.getTime_threshold() * 7;
									break;
								case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
									Time_threshold = serviceReminder.getTime_threshold() * 30;
									break;
								case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
									Time_threshold = serviceReminder.getTime_threshold() * 365;
									break;
									
								default:
									Time_threshold = serviceReminder.getTime_threshold();
									break;
								}
								final Calendar calendar_advanceThreshold = Calendar.getInstance();
								// System.out.println("Service =" +
								// Time_servicedate);
								// this Time_servicedate is service time Day so that
								// day to minus advance
								// time threshold time
								calendar_advanceThreshold.setTime(Time_servicedate);
								calendar_advanceThreshold.add(Calendar.DAY_OF_YEAR, -Time_threshold);
								/*
								 * System.out.println( "Service time_advancedate ="
								 * +
								 * format.format(calendar_advanceThreshold.getTime()
								 * ));
								 */
								// fuel date converted String to date Format
								java.util.Date serviceThreshold = format
										.parse(format.format(calendar_advanceThreshold.getTime()));
								java.sql.Date Time_serviceAdvacedate = new java.sql.Date(serviceThreshold.getTime());
								
								// save Service Time_interval Advance Threshold
								// dateReminder
								serviceReminder.setTime_servicethreshold_date(Time_serviceAdvacedate);
								serviceReminder.setServiceProgramId(schedulesDto.getVehicleServiceProgramId());
								serviceReminder.setServiceScheduleId(schedulesDto.getServiceScheduleId());
								serviceReminder.setServiceType(schedulesDto.getServiceTypeId());
								
								if(schedulesDto.getService_subScribedUserId()!=null || schedulesDto.getService_subScribedUserId()=="") {
									userProfile  =userRepository.getUserProfileByUser_id( Long.parseLong(schedulesDto.getService_subScribedUserId()) , vehicle.getCompany_Id());
									serviceReminder.setService_subscribeduser_name(userProfile.getFirstName());	
								}
								serviceReminder.setService_subScribedUserId(schedulesDto.getService_subScribedUserId());
															
								
							}
						}
						ServiceReminder s1 =  serviceReminderRepository.getCheckDuplicateServiceReminder(schedulesDto.getJobTypeId(), schedulesDto.getJobSubTypeId(), vehicle.getVid());
						//if(s1 == null) {
							ServiceReminderService.addServiceReminder(serviceReminder);
						//}
//						  ServiceReminderService.addServiceReminder(serviceReminder);
					}
		} catch (Exception e) {
			throw e;
		}finally {
			serviceReminder	= null;
			counter			= null;
		}
	}
	
	@Override
	@Transactional
	public void getServiceReminderListToSave(ServiceProgramSchedules schedulesDto, Vehicle vehicle) throws Exception {

		ServiceReminder						serviceReminder			= null;
		ServiceReminderSequenceCounter		counter					= null;
		try {
					serviceReminder	= new ServiceReminder();
					counter = serviceReminderSequenceService.findNextServiceReminderNumber(vehicle.getCompany_Id());
					serviceReminder.setService_Number(counter.getNextVal());
					serviceReminder.setVid(vehicle.getVid());
					serviceReminder.setServiceTypeId(schedulesDto.getJobTypeId());
					serviceReminder.setServiceSubTypeId(schedulesDto.getJobSubTypeId());
					serviceReminder.setMeter_interval(schedulesDto.getMeterInterval());
					serviceReminder.setVehicle_currentOdometer(vehicle.getVehicle_Odometer());
					serviceReminder.setMeter_serviceodometer(vehicle.getVehicle_Odometer() + schedulesDto.getMeterInterval());
					serviceReminder.setMeter_threshold(schedulesDto.getMeterThreshold());
					serviceReminder.setMeter_servicethreshold_odometer(serviceReminder.getMeter_serviceodometer() - schedulesDto.getMeterThreshold() );
					serviceReminder.setTime_interval(schedulesDto.getTimeInterval());
					serviceReminder.setTime_intervalperiodId(schedulesDto.getTimeIntervalType());
					serviceReminder.setTime_interval_currentdate(new Date());
					serviceReminder.setTime_threshold(schedulesDto.getTimeThreshold());
					serviceReminder.setTime_thresholdperiodId(schedulesDto.getTimeThresholdType());
					serviceReminder.setService_remider_howtimes(1);
					serviceReminder.setServiceStatusId(ServiceReminderDto.SERVICE_REMINDER_STATUS_ACTIVE);
					serviceReminder.setCompanyId(vehicle.getCompany_Id());
					serviceReminder.setVehicleGroupId(vehicle.getVehicleGroupId());
					serviceReminder.setCreatedById(vehicle.getCreatedById());
					serviceReminder.setLastModifiedById(vehicle.getLastModifiedById());
					serviceReminder.setCreated(new Date());
					serviceReminder.setLastupdated(new Date());
					serviceReminder.setServiceProgramId(schedulesDto.getVehicleServiceProgramId());
					serviceReminder.setServiceScheduleId(schedulesDto.getServiceScheduleId());
					// time interval not equal to null only enter the value
					if (serviceReminder.getTime_interval() != null) {

						// get time interval calculation to service to get
						// service time interval days
						Integer time_Intervalperiod = 0;
						if (serviceReminder.getTime_intervalperiodId() > 0) {
							time_Intervalperiod = serviceReminder.getTime_interval();
						}
						Integer timeserviceDaysPeriod = 0;
						switch (serviceReminder.getTime_intervalperiodId()) {
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
							timeserviceDaysPeriod = time_Intervalperiod;
							break;
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
							timeserviceDaysPeriod = time_Intervalperiod * 7;
							break;
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
							timeserviceDaysPeriod = time_Intervalperiod * 30;
							break;
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
							timeserviceDaysPeriod = time_Intervalperiod * 365;
							break;

						default:
							timeserviceDaysPeriod = time_Intervalperiod;
							break;
						}

						final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

						final Calendar calendar = Calendar.getInstance();
						calendar.setTime(new Date());
						calendar.add(Calendar.DAY_OF_YEAR, timeserviceDaysPeriod);
						// System.out.println("Service time_date =" +
						// format.format(calendar.getTime()));

						// fuel date converted String to date Format
						java.util.Date servicedate = format.parse(format.format(calendar.getTime()));
						java.sql.Date Time_servicedate = new java.sql.Date(servicedate.getTime());

						// save Service Time_interval Reminder
						serviceReminder.setTime_servicedate(Time_servicedate);

						if (serviceReminder.getTime_threshold() != null) {

							Integer Time_threshold = 0;
							switch (serviceReminder.getTime_thresholdperiodId()) {
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
								Time_threshold = serviceReminder.getTime_threshold();
								break;
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
								Time_threshold = serviceReminder.getTime_threshold() * 7;
								break;
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
								Time_threshold = serviceReminder.getTime_threshold() * 30;
								break;
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
								Time_threshold = serviceReminder.getTime_threshold() * 365;
								break;

							default:
								Time_threshold = serviceReminder.getTime_threshold();
								break;
							}
							final Calendar calendar_advanceThreshold = Calendar.getInstance();
							// System.out.println("Service =" +
							// Time_servicedate);
							// this Time_servicedate is service time Day so that
							// day to minus advance
							// time threshold time
							calendar_advanceThreshold.setTime(Time_servicedate);
							calendar_advanceThreshold.add(Calendar.DAY_OF_YEAR, -Time_threshold);
							/*
							 * System.out.println( "Service time_advancedate ="
							 * +
							 * format.format(calendar_advanceThreshold.getTime()
							 * ));
							 */
							// fuel date converted String to date Format
							java.util.Date serviceThreshold = format
									.parse(format.format(calendar_advanceThreshold.getTime()));
							java.sql.Date Time_serviceAdvacedate = new java.sql.Date(serviceThreshold.getTime());

							// save Service Time_interval Advance Threshold
							// dateReminder
							serviceReminder.setTime_servicethreshold_date(Time_serviceAdvacedate);

						}
			}
					ServiceReminderService.addServiceReminder(serviceReminder);
		} catch (Exception e) {
			throw e;
		}finally {
			serviceReminder	= null;
			counter			= null;
		}
	
		
	}
	
	@Override
	public ValueObject getBusDetailsForFalconITSApi(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails			= null;
		try {
			userDetails	 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			
			VehicleExtraDetails busDetails = VehicleExtraDetailsRepository.getBusId(valueObject.getInt("vid"), userDetails.getCompany_id());
			
			if(busDetails != null) {
				valueObject.put("busDetails", busDetails);
				valueObject.put("busDetailsFound", true);
			} else {
				valueObject.put("busDetailsFound", false);
			}
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public ValueObject saveBusDetailsForFalconITSApi(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails			= null;
		VehicleExtraDetails			extraDetails		= null;
		VehicleExtraDetails			updateBusDetails	= null;
		try {
			extraDetails 	 = new VehicleExtraDetails();
			updateBusDetails = new VehicleExtraDetails();
			userDetails	 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			
			VehicleExtraDetails validate = VehicleExtraDetailsRepository.validateBusDetails(valueObject.getInt("vid"), 
					valueObject.getLong("busId"), valueObject.getLong("deviceId"), userDetails.getCompany_id());
			
			
			if(validate == null) {
				
				VehicleExtraDetails addBusDetails = VehicleExtraDetailsRepository.getBusId(valueObject.getInt("vid"), 
						userDetails.getCompany_id());
				
				
				VehicleExtraDetails validateBusId = VehicleExtraDetailsRepository.validateBusId(valueObject.getLong("busId"), 
						userDetails.getCompany_id());
				
				VehicleExtraDetails validateDeviceId = VehicleExtraDetailsRepository.validateDeviceId(valueObject.getLong("deviceId"), 
						userDetails.getCompany_id());
				
				
				if(addBusDetails == null && validateBusId == null && validateDeviceId == null) {
					
					extraDetails.setVid(valueObject.getInt("vid"));
					extraDetails.setBusId(valueObject.getLong("busId"));
					extraDetails.setDeviceId(valueObject.getLong("deviceId"));
					extraDetails.setCompanyId(userDetails.getCompany_id());
					extraDetails.setMarkForDelete(false);
					
					VehicleExtraDetailsRepository.save(extraDetails);
					valueObject.put("busDetailsAdded", true);
					
				} else if (addBusDetails != null) {
					
					if( (validateBusId != null && (addBusDetails.getVid() == validateBusId.getVid()) && validateDeviceId == null) 
							|| (validateDeviceId != null && (addBusDetails.getVid() == validateDeviceId.getVid()) && validateBusId == null) 
							|| (validateBusId == null && validateDeviceId == null)) {
						
						updateBusDetails.setVehicleExtraDetailsId(addBusDetails.getVehicleExtraDetailsId());
						updateBusDetails.setVid(valueObject.getInt("vid"));
						updateBusDetails.setBusId(valueObject.getLong("busId"));
						updateBusDetails.setDeviceId(valueObject.getLong("deviceId"));
						updateBusDetails.setCompanyId(userDetails.getCompany_id());
						updateBusDetails.setMarkForDelete(false);
						updateBusDetails.setVehicleTollDetailsId(addBusDetails.getVehicleTollDetailsId());
						updateBusDetails.setVehicleGPSCredentialId(addBusDetails.getVehicleGPSCredentialId());
						
						VehicleExtraDetailsRepository.save(updateBusDetails);
						valueObject.put("busDetailsUpdated", true);
						
					} else {
						
						valueObject.put("dupliacteBusDetailsFound", true);
						
						if(validateBusId != null) {
							Vehicle busVehicel = vehicleRepository.getVehicel(validateBusId.getVid(), validateBusId.getCompanyId());
							valueObject.put("dupliacteBusIdFound", busVehicel.getVehicle_registration());
						}
						
						if(validateDeviceId != null) {
							Vehicle deviceVehicel = vehicleRepository.getVehicel(validateDeviceId.getVid(), validateDeviceId.getCompanyId());
							valueObject.put("dupliacteDeviceIdFound", deviceVehicel.getVehicle_registration());
						}
					}
				
				} else {
					
					valueObject.put("dupliacteBusDetailsFound", true);
					
					if(validateBusId != null) {
						Vehicle busVehicel = vehicleRepository.getVehicel(validateBusId.getVid(), validateBusId.getCompanyId());
						valueObject.put("dupliacteBusIdFound", busVehicel.getVehicle_registration());
					}
					
					if(validateDeviceId != null) {
						Vehicle deviceVehicel = vehicleRepository.getVehicel(validateDeviceId.getVid(), validateDeviceId.getCompanyId());
						valueObject.put("dupliacteDeviceIdFound", deviceVehicel.getVehicle_registration());
					}
					
				}	
				
			} else {
				valueObject.put("busDetailsAlreadyAdded", true);
			}
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	@Override
	public ValueObject updateVehicleDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		VehicleDto 					validate 						= null;
		Vehicle						vehicle							= null;
		VehicleOdometerHistory 		vehicleUpdateHistory 			= null;
		HashMap<String, Object>		configuration					= null;
		List<ServiceReminderDto>	serviceList						= null;
		VehicleDto					vehicleDto						= null;
		boolean						validateChasisNo	   			= false;
		boolean						validateVGroup	   				= false;
		boolean						validateEnginNo      			= false;
		boolean						validateVehicleType      		= false;
		boolean						validateVehicleStatus     		= false;
		boolean						validateVehicleFuelType     	= false;
		boolean						validateVehicleOdometer    		= false;
		boolean						validateVehicleExpectedOdometer = false;
		boolean						validateVehicleExpectedMileage  = false;
		Vehicle 					savedVehicle 					= null;	
		String 						vehicleRegistration				= "";
		String 						vehicleChassisNumber			= "";
		String 						vehicleEngineNumber				= "";
		String 						query							= "";
		List<ServiceReminder>       serviceReminerValidate			= null;
		int							duplicateCount					= 0;
		String						duplicateSr						= "";
		VehicleHistory				vehicleHistory					=null;
		Vehicle 					beforeSaveVehicle 				= null;
		HashMap<String, Object> 		configurationInspection		= null;
		HashMap<String, Object>		SPconfiguration					= null;
		List<ServiceProgramAsignmentDetails> asignmentDetails		= null;		
		List<VehicleTyreLayoutPosition>	exitingVehicleTyreLayoutPositionList	= null;
		VehicleTyreLayoutPosition       			vehicleTyreLayoutPosition					= null;
		List<VehicleModelTyreLayoutPositionDto> vehicleModelTyreLayoutPositionList = null;
		List<VehicleTyreLayoutPosition>       		vehicleTyreLayoutPositionList				= null;
		boolean						addVehicleStatusRemarkConfig  = false;
		
		try {
			System.err.println("inside update service ");
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 					= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			SPconfiguration 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_PROGRAM_CONFIGURATION_CONFIG);
			configurationInspection			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			validateVGroup	   				= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VGROUP, false); 
			validateChasisNo	   			= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_CHASIS_NO, false); 
			validateEnginNo      			= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_ENGINE_NO, false);
			validateVehicleType      		= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VEHICLE_TYPE, false); 
			validateVehicleStatus     	 	= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VEHICLE_TYPE, false); 
			validateVehicleFuelType     	= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VEHICLE_FUEL_TYPE, false);
			validateVehicleOdometer     	= (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VEHICLE_ODOMETER, false); 
			validateVehicleExpectedOdometer = (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VEHICLE_EXPECTED_ODOMETER, false); 
			validateVehicleExpectedMileage  = (boolean) configuration.getOrDefault(VehicleConfigurationContant.VALIDATE_VEHICLE_EXPECTED_MILEAGE, false);
			addVehicleStatusRemarkConfig  = (boolean) configuration.getOrDefault(VehicleConfigurationContant.ADD_VEHICLE_STATUS_REMARK, false);
			vehicleDto 						= getVehicelDetails(valueObject.getInt("vid",0),userDetails);
			Long oldModalId		= vehicleDto.getVehicleModelId();
			long oldTypeId		= vehicleDto.getVehicleTypeId();
			long oldBranchId	= vehicleDto.getBranchId();
			
			if((valueObject.getString("vehicleRegistrationNumber",null) == null || valueObject.getString("vehicleRegistrationNumber",null).trim().equalsIgnoreCase(""))) {
				valueObject.put("vehicleNumberNotFound", true);
				return valueObject;
			}if(validateChasisNo && (valueObject.getString("vehicleChasisNumber",null) == null || valueObject.getString("vehicleChasisNumber",null).trim().equalsIgnoreCase(""))) {
				valueObject.put("ChasisNoNotFound", true);
				return valueObject;
			}if(validateEnginNo && (valueObject.getString("vehicleEngineNumber", null) == null || valueObject.getString("vehicleEngineNumber", null).trim().equalsIgnoreCase(""))) {
				valueObject.put("EngineNoNotFound", true);
				return valueObject;
			}if(validateVehicleType && valueObject.get("VehicleTypeId") == "" ) {
				valueObject.put("VehicleTypeNoNotFound", true);
				return valueObject;
			}if(validateVGroup && valueObject.get("vehicleGroupId")  == "" ) {
				valueObject.put("VGroupNotFound", true);
				return valueObject;
			}if(validateVehicleStatus && valueObject.get("vehicleStatusId") == null) {
				valueObject.put("VehicleStatusNotFound", true);
				return valueObject;
			}if(validateVehicleFuelType && valueObject.get("vehicleFuel") == null) {
				valueObject.put("VehicleFuelTypeNotFound", true);
				return valueObject;
			}if(validateVehicleOdometer && valueObject.get("vehicleOdometer") == null) {
				valueObject.put("VehicleOdometerNotFound", true);
				return valueObject;
			}if(validateVehicleExpectedOdometer && valueObject.get("maximumOdometer") == null) {
				valueObject.put("VehicleExpectedOdometerNotFound", true);
				return valueObject;
			}if(validateVehicleExpectedMileage &&(valueObject.get("expectedMileageFrom") == null || valueObject.get("expectedMileageTo") == null)) {
				valueObject.put("VehicleExpectedMileageNotFound", true);
				return valueObject;
			}if(valueObject.getInt("maximumOdometer", 0) > Integer.parseInt(configuration.getOrDefault("allowMaximumOdometer", "5000")+"")) {
				valueObject.put("aboveMaxOdometer", true);
				valueObject.put("maxAllowedOdo",  configuration.getOrDefault("allowMaximumOdometer", 5000));
				return valueObject;
			}
			
			if(!valueObject.getString("vehicleRegistrationNumber").equalsIgnoreCase(vehicleDto.getVehicle_registration().trim())) {
				
				if(valueObject.getString("vehicleRegistrationNumber") != null ){
					vehicleRegistration	= valueObject.getString("vehicleRegistrationNumber");
				}
				if(valueObject.getString("vehicleChasisNumber") != null ){
					vehicleChassisNumber	= valueObject.getString("vehicleChasisNumber");
				}
				if(valueObject.getString("vehicleEngineNumber") != null ){
					vehicleEngineNumber	= valueObject.getString("vehicleEngineNumber");
				}
				
				if(validateChasisNo && validateEnginNo) { 
					query	= "(V.vehicle_registration = '"+vehicleRegistration+"' OR V.vehicle_chasis = '"+vehicleChassisNumber+"' OR V.vehicle_engine = '"+vehicleEngineNumber+"') ";
				}else if(validateChasisNo) {
					query	= "(V.vehicle_registration =  '"+vehicleRegistration+"' OR V.vehicle_chasis = '"+vehicleChassisNumber+"')";
				}else if(validateEnginNo) {
					query	= "(V.vehicle_registration = '"+vehicleRegistration+"' OR V.vehicle_engine = '"+vehicleEngineNumber+"')";
				}else {
					query	= "(V.vehicle_registration = '"+vehicleRegistration+"')";
				}
				
				validate = validateVehicle(query); 
				
				if(validate != null) {// If duplicate Entry Found ,This will return Message respective to duplicate fie;ld
					if(valueObject.getString("vehicleRegistrationNumber").equalsIgnoreCase(validate.getVehicle_registration().trim())) {
						valueObject.put("VehicleRegExist", true);
					}else if(valueObject.getString("vehicleChasisNumber").equalsIgnoreCase(validate.getVehicle_chasis().trim())) {
						valueObject.put("VehicleChasisExist", true);
					}else if(valueObject.getString("vehicleEngineNumber").equalsIgnoreCase(validate.getVehicle_engine().trim())) {
						valueObject.put("VehicleEngineExist", true);
					}else {
						valueObject.put("alreadyExist", true);
					}
				}else {
					System.err.println("inside else ");
					if(vehicleDto.getRouteID() != null && vehicleDto.getRouteID() > 0) {
						if(vehicleDto.getRouteID() != valueObject.getInt("vehicleRouteId",0)) {
							valueObject.put("oldvehicleRouteId", vehicleDto.getRouteID());
							valueObject.put("routeChanged", true);
						}
					}else if(valueObject.getInt("vehicleRouteId",0) > 0) {
						valueObject.put("routeChanged", true);
					}   
						beforeSaveVehicle = vehicleRepository.getVehicel(valueObject.getInt("vid",0), userDetails.getCompany_id());
						
						vehicle 					= VBL.prepareVehicleDetails(valueObject, userDetails);
						savedVehicle 				= vehicleRepository.save(vehicle);
						vehicleHistory       		= VBL.prepareVehicleHistory(vehicleDto,valueObject, userDetails);
						vehicleHistoryRepository.save(vehicleHistory);
						
						
						if((boolean)configurationInspection.getOrDefault("sheetToVehicleType", false) && beforeSaveVehicle!= null && (beforeSaveVehicle.getVehicleTypeId() != savedVehicle.getVehicleTypeId() || savedVehicle.getBranchId() != null)) {
							int savedBranchId=0;
							
							if(savedVehicle.getBranchId() != null) {
								savedBranchId = savedVehicle.getBranchId();
							}
							asingmentService.editVehicleSheetAssignment(beforeSaveVehicle.getVehicleTypeId(), savedVehicle.getVehicleTypeId(), valueObject.getInt("vid"),savedBranchId );
						}
						vehicleUpdateHistory 		= VOHBL.prepareOdometerGetHistoryToVehicle(vehicle);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						ServiceReminderService.updateCurrentOdometerToServiceReminder(vehicle.getVid(), vehicle.getVehicle_Odometer(), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(vehicle.getVid(), userDetails.getCompany_id(), userDetails.getId());
					
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
								}
							}
						}
						
						valueObject.put("vid", savedVehicle.getVid());
						valueObject.put("update", true);
					
					}
				
			}else {
				if(validateChasisNo && validateEnginNo) { 
					query	= "(V.vehicle_chasis = '"+valueObject.getString("vehicleChasisNumber")+"' OR V.vehicle_engine = '"+valueObject.getString("vehicleEngineNumber")+"') ";
				}else if(validateChasisNo) {
					query	= "(V.vehicle_chasis = '"+valueObject.getString("vehicleChasisNumber")+"')";
				}else if(validateEnginNo) {
					query	= "( V.vehicle_engine = '"+valueObject.getString("vehicleEngineNumber")+"')";
				}else {
					query	= "(V.vehicle_chasis = '"+valueObject.getString("vehicleChasisNumber")+"' OR V.vehicle_engine = '"+valueObject.getString("vehicleEngineNumber")+"') ";
				}
				
				validate = validateVehicle(query); 
				
				if(validate != null) {
					if(!validate.getVehicle_chasis().equalsIgnoreCase(vehicleDto.getVehicle_chasis())) {
						if(valueObject.getString("vehicleChasisNumber").equalsIgnoreCase(validate.getVehicle_chasis().trim())) {
							valueObject.put("VehicleChasisExist", true);
							return valueObject;
						}
					}
					if(!validate.getVehicle_engine().equalsIgnoreCase(vehicleDto.getVehicle_engine())) {
						 if(valueObject.getString("vehicleEngineNumber").equalsIgnoreCase(validate.getVehicle_engine().trim())) {
							valueObject.put("VehicleEngineExist", true);
							return valueObject;
						}
					}
				}
				
				if(vehicleDto.getRouteID() != null && vehicleDto.getRouteID() > 0) {

					if(vehicleDto.getRouteID() != valueObject.getInt("vehicleRouteId",0)) {
						valueObject.put("oldvehicleRouteId", vehicleDto.getRouteID());
						valueObject.put("routeChanged", true);
					}
				}else if(valueObject.getInt("vehicleRouteId",0) > 0) {
					valueObject.put("routeChanged", true);
				}
				System.err.println("near update vehicle ");
				vehicle 					= VBL.prepareVehicleDetails(valueObject, userDetails);
				savedVehicle	=	vehicleRepository.save(vehicle);
				valueObject.put("vid", savedVehicle.getVid());
				vehicleHistory       		= VBL.prepareVehicleHistory(vehicleDto,valueObject, userDetails);
				vehicleHistoryRepository.save(vehicleHistory);
				
				vehicleUpdateHistory 		= VOHBL.prepareOdometerGetHistoryToVehicle(vehicle);
				vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
				
				vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
				ServiceReminderService.updateCurrentOdometerToServiceReminder(vehicle.getVid(), vehicle.getVehicle_Odometer(), userDetails.getCompany_id());
				
				serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(vehicle.getVid(), userDetails.getCompany_id(), userDetails.getId());
			
				if(serviceList != null) {
					for(ServiceReminderDto list : serviceList) {
						
						if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
							
							ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
						}
					}
				}
				
				/*
				 * if(vehicle.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_SOLD) {
				 * 
				 * if(valueObject.getLong("serviceProgramId",0) > 0 &&
				 * (vehicleDto.getServiceProgramId() == null || vehicleDto.getServiceProgramId()
				 * == 0)) { List<ServiceProgramSchedulesDto> serviceScheduleList =
				 * serviceProgramService.getServiceProgramSchedulesBtId(valueObject.getLong(
				 * "serviceProgramId",0)); if(serviceScheduleList != null &&
				 * !serviceScheduleList.isEmpty()) { for(ServiceProgramSchedulesDto schedulesDto
				 * : serviceScheduleList) { serviceReminerValidate =
				 * ServiceReminderService.validateServiceReminder(vehicle.getVid(),
				 * schedulesDto.getJobTypeId(), schedulesDto.getJobSubTypeId());
				 * if(serviceReminerValidate != null && !serviceReminerValidate.isEmpty()) {
				 * duplicateCount ++; duplicateSr +=
				 * serviceReminerValidate.get(0).getService_Number()+",";
				 * valueObject.put("duplicateSr", duplicateSr);
				 * valueObject.put("duplicateCount", duplicateCount); continue; }
				 * getServiceReminderListToSave(schedulesDto, savedVehicle); } } } }
				 */			
				if(vehicle.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_SOLD) {
					
					if(!(boolean) configuration.get("selectServiceProgramOnModal")) {
						if(valueObject.getLong("serviceProgramId",0) > 0 && (vehicleDto.getServiceProgramId() == null || vehicleDto.getServiceProgramId() == 0)) {
							List<ServiceProgramSchedulesDto> 	serviceScheduleList		= serviceProgramService.getServiceProgramSchedulesBtId(valueObject.getLong("serviceProgramId",0));
							if(serviceScheduleList != null && !serviceScheduleList.isEmpty()) {
								for(ServiceProgramSchedulesDto schedulesDto : serviceScheduleList) {
									serviceReminerValidate	=	ServiceReminderService.validateServiceReminder(vehicle.getVid(), schedulesDto.getJobTypeId(), schedulesDto.getJobSubTypeId());
									if(serviceReminerValidate != null && !serviceReminerValidate.isEmpty()) {
										duplicateCount ++;
										duplicateSr 	+= serviceReminerValidate.get(0).getService_Number()+",";
										valueObject.put("duplicateSr", duplicateSr);
										valueObject.put("duplicateCount", duplicateCount);
										continue;
									}
									getServiceReminderListToSave(schedulesDto, savedVehicle);
								}
							}
						}
					}else {
						
						ServiceReminderService.deleteServiceReminderByVid(vehicle.getVid(), userDetails.getCompany_id(), valueObject.getLong("serviceProgramId",0));
						
						if(vehicle.getVehicleTypeId() > 0 && vehicle.getVehicleModalId() != null && vehicle.getVehicleModalId() > 0 && vehicle.getBranchId() > 0) {
							
							if(!vehicle.getVehicleModalId().equals(oldModalId) || oldTypeId != vehicle.getVehicleTypeId() || oldBranchId != vehicle.getBranchId()) {
								if((boolean) SPconfiguration.getOrDefault("vehicleBranchWiseProgram", false)) {
									
									asignmentDetails	=		asignmentDetailsService.getServiceProgramListByVehicleTypeModalAndBranch(
											vehicle.getVehicleTypeId(), vehicle.getVehicleModalId(),vehicle.getBranchId(), userDetails.getCompany_id());
								}else {
									asignmentDetails	=		asignmentDetailsService.getServiceProgramListByVehicleTypeAndModal(
											vehicle.getVehicleTypeId(), vehicle.getVehicleModalId(), userDetails.getCompany_id());
									
								}
								if(asignmentDetails != null && !asignmentDetails.isEmpty()) {
									for (ServiceProgramAsignmentDetails serviceProgramAsignmentDetails : asignmentDetails) {
										List<ServiceProgramSchedulesDto> 	serviceScheduleList		= serviceProgramService.getServiceProgramSchedulesBtId(serviceProgramAsignmentDetails.getServiceProgramId());
										if(serviceScheduleList != null && !serviceScheduleList.isEmpty()) {
											for(ServiceProgramSchedulesDto schedulesDto : serviceScheduleList) {
												serviceReminerValidate	=	ServiceReminderService.validateServiceReminder(vehicle.getVid(), schedulesDto.getJobTypeId(), schedulesDto.getJobSubTypeId());
												if(serviceReminerValidate == null || serviceReminerValidate.isEmpty()) {
													getServiceReminderListToSave(schedulesDto, savedVehicle);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			
				if(valueObject.getLong("vehicleModel",0) !=  vehicleDto.getVehicleModelId()) {
					exitingVehicleTyreLayoutPositionList =	vehicleTyreLayoutPositionRepository.Get_Vehicle_Type_Details(valueObject.getInt("vid"), userDetails.getCompany_id());
					if(exitingVehicleTyreLayoutPositionList != null && !exitingVehicleTyreLayoutPositionList.isEmpty()){
						vehicleTyreLayoutPositionRepository.Delete_VehicleTyreLayoutPosition(valueObject.getInt("vid"), userDetails.getCompany_id());
					}
					vehicleModelTyreLayoutPositionList 		= new ArrayList<>();
					vehicleTyreLayoutPositionList 			= new ArrayList<>();
					
					valueObject.put("vehicleModelId", 	valueObject.getLong("vehicleModel",0));
					valueObject.put("companyId", userDetails.getCompany_id());
					valueObject.put("userId", userDetails.getId());
					
					valueObject = vehicleModelTyreLayoutService.getVehicleModelTyreLayoutPositionByVehicleModelId(valueObject);
					vehicleModelTyreLayoutPositionList =	(List<VehicleModelTyreLayoutPositionDto>) valueObject.get("vehicleModelTyreLayoutPositionList");
					
					if(vehicleModelTyreLayoutPositionList != null && !vehicleModelTyreLayoutPositionList.isEmpty()) {
						for( VehicleModelTyreLayoutPositionDto dto : vehicleModelTyreLayoutPositionList) {
							vehicleTyreLayoutPosition = vModelTyreLayoutBL.prepareMultipleVehicleTyreLayoutPosition(dto.getPosition(),dto.getTyreModelId(),valueObject);
							vehicleTyreLayoutPositionList.add(vehicleTyreLayoutPosition);
						}
						
						vehicleTyreLayoutPositionRepository.saveAll(vehicleTyreLayoutPositionList);
					}
				}
				
				valueObject.put("save", true);
				}
				
			if(addVehicleStatusRemarkConfig && valueObject.getShort("currentStatusId",(short) 0) >0 && valueObject.getShort("changeToStatusId",(short) 0) >0 && valueObject.getShort("currentStatusId",(short) 0) != valueObject.getShort("changeToStatusId",(short) 0)) {
				valueObject.put("typeId",StatusRemarkBL.VEHICLE_STATUS_CHANGE);
				valueObject.put("transactionId",valueObject.getInt("vid",0));
				statusRemarkService.saveVehicleStatusRemark(valueObject);
			}
					
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;       
			validate 						= null;       
			vehicle							= null;       
			vehicleUpdateHistory 			= null;       
			configuration					= null;       
			                                              
		}
	}
	@Transactional(readOnly = false)
	public VehicleDto validateVehicle(String finalQuery) throws Exception {
		Object[] 			vehicle		 	= null;
		CustomUserDetails 	userDetails 	= null;
		VehicleDto 			vehicleDto 		= null;	
		try {
			
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Query query = entityManager.createQuery("SELECT V.vid, V.vehicle_registration, V.vehicle_chasis, V.vehicle_engine "
						+ " FROM Vehicle AS V where "+finalQuery+" AND V.company_Id ="+userDetails.getCompany_id()+" AND V.markForDelete = 0");
			query.setMaxResults(1);
			try {
				vehicle = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				
			}
			
			
			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			vehicleDto 	= new VehicleDto();
			if (vehicle != null) {
				vehicleDto.setVid((Integer) vehicle[0]);
				vehicleDto.setVehicle_registration((String) vehicle[1]);
				vehicleDto.setVehicle_chasis((String) vehicle[2]);
				vehicleDto.setVehicle_engine((String) vehicle[3]);
				
			}
			return vehicleDto;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public ValueObject saveVehicleTollDetails(ValueObject valueInObject) throws Exception {
		VehicleExtraDetails			vehicleExtraDetails		= null;
		CustomUserDetails			userDetails				= null;
		try {
			
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehicleExtraDetailsDto validate 	=	vehicleExtraDetailsService.getVehicleExtraDetailsByVid(valueInObject.getInt("vid"));
			if(validate == null) {

				vehicleExtraDetails	= new VehicleExtraDetails();
				
				vehicleExtraDetails.setVid(valueInObject.getInt("vid"));
				vehicleExtraDetails.setVehicleTollDetailsId(valueInObject.getLong("vehicleTollDetailsId",0));
				vehicleExtraDetails.setCompanyId(userDetails.getCompany_id());
				
				VehicleExtraDetailsRepository.save(vehicleExtraDetails);
			}else {
				vehicleExtraDetails	= new VehicleExtraDetails();
				
				vehicleExtraDetails.setBusId(validate.getBusId());
				vehicleExtraDetails.setDeviceId(validate.getDeviceId());
				vehicleExtraDetails.setVehicleExtraDetailsId(validate.getVehicleExtraDetailsId());
				
				vehicleExtraDetails.setVehicleTollDetailsId(valueInObject.getLong("vehicleTollDetailsId", 0));
				vehicleExtraDetails.setVid(validate.getVid());
				vehicleExtraDetails.setCompanyId(userDetails.getCompany_id());
				
				VehicleExtraDetailsRepository.save(vehicleExtraDetails);
			}
			
			
			return valueInObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public HashMap<Long, Long> getVehicleAsinedTollCount(Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> 	typedQuery = null;
			HashMap<Long, Long>		map		   = null;
			
			typedQuery = entityManager.createQuery(
					"SELECT COUNT(WO.vehicleExtraDetailsId), WO.vehicleTollDetailsId "
							+ " From VehicleExtraDetails as WO "
							+ " WHERE WO.companyId = "+companyId+" AND WO.markForDelete = 0 AND WO.vehicleTollDetailsId is not null "
							+ " AND WO.vehicleTollDetailsId > 0 GROUP BY WO.vehicleTollDetailsId ",
							Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();

			if (results != null && !results.isEmpty()) {
				
				map	= new HashMap<>();
				
				for (Object[] result : results) {
					
					map.put((Long)result[1], (Long)result[0]);
					
				}
			}
			
			return map;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public HashMap<Long, Long> getVehicleAsinedGPSCount(Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> 	typedQuery = null;
			HashMap<Long, Long>		map		   = null;
			
			typedQuery = entityManager.createQuery(
					"SELECT COUNT(WO.vehicleExtraDetailsId), WO.vehicleGPSCredentialId "
							+ " From VehicleExtraDetails as WO "
							+ " WHERE WO.companyId = "+companyId+" AND WO.markForDelete = 0 AND WO.vehicleGPSCredentialId is not null "
							+ " AND WO.vehicleGPSCredentialId > 0 GROUP BY WO.vehicleGPSCredentialId ",
							Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();

			if (results != null && !results.isEmpty()) {
				
				map	= new HashMap<>();
				
				for (Object[] result : results) {
					
					map.put((Long)result[1], (Long)result[0]);
					
				}
			}
			
			return map;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveVehicleGPSAccount(ValueObject valueInObject) throws Exception {
		VehicleExtraDetails			vehicleExtraDetails		= null;
		CustomUserDetails			userDetails				= null;
		try {
			
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehicleExtraDetailsDto validate 	=	vehicleExtraDetailsService.getVehicleExtraDetailsByVid(valueInObject.getInt("vid"));
			if(validate == null) {

				vehicleExtraDetails	= new VehicleExtraDetails();
				
				vehicleExtraDetails.setVid(valueInObject.getInt("vid"));
				vehicleExtraDetails.setVehicleGPSCredentialId(valueInObject.getLong("vehicleGPSCredentialId",0));
				vehicleExtraDetails.setCompanyId(userDetails.getCompany_id());
				
				VehicleExtraDetailsRepository.save(vehicleExtraDetails);
			}else {
				vehicleExtraDetails	= new VehicleExtraDetails();
				
				vehicleExtraDetails.setBusId(validate.getBusId());
				vehicleExtraDetails.setDeviceId(validate.getDeviceId());
				vehicleExtraDetails.setVehicleExtraDetailsId(validate.getVehicleExtraDetailsId());
				
				vehicleExtraDetails.setVehicleTollDetailsId(validate.getVehicleTollDetailsId());
				vehicleExtraDetails.setVehicleGPSCredentialId(valueInObject.getLong("vehicleGPSCredentialId",0));
				vehicleExtraDetails.setVid(validate.getVid());
				vehicleExtraDetails.setCompanyId(userDetails.getCompany_id());
				
				VehicleExtraDetailsRepository.save(vehicleExtraDetails);
			}
			
			
			return valueInObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleDetailsOnTime(ValueObject valueObject) throws Exception {
		HashMap<String, Object> 	configuration		= null;
		Vehicle						vehicle				= null;
		ValueObject					valueOutObject		= null;
		String						dispatchDate		= null;
		String						dispatchTime		= null;
		TripSheetDto				tripSheetDto		= null;
		HashMap<String, Object> 	tripConfig			= null;
		TripSheetDto				savedTripSheet		= null;
			try {
					valueOutObject	= new ValueObject();
					configuration	= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.VEHICLE_GPS_CONFIG);
					tripConfig		= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
					dispatchDate	= valueObject.getString("dispatchedByTime");
					dispatchTime	= valueObject.getString("dispatchTime");
					
					Timestamp	fromDate	= new Timestamp(DateTimeUtility.getDateTimeFromDateTimeString(dispatchDate, dispatchTime).getTime());
					VehicleDto oddmeter		= getVehicle_Select_TripSheet_Details(valueObject.getInt("vehicleId"));
					
					valueOutObject.put("vehicle", oddmeter);
					
						
						if(oddmeter != null) {
							
							vehicle				= new Vehicle();
							vehicle.setVid(oddmeter.getVid());
							vehicle.setVehicle_registration(oddmeter.getVehicle_registration());
							vehicle.setVehicle_Odometer(oddmeter.getVehicle_Odometer());
							vehicle.setGpsVendorId(oddmeter.getGpsVendorId());
							vehicle.setVehicleGPSId(oddmeter.getVehicleGPSId());
							vehicle.setVehicle_chasis(oddmeter.getVehicle_chasis());
							
							valueOutObject.put("vehicle", oddmeter);
							if(oddmeter != null && oddmeter.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
								valueOutObject.put("tripStatus",workOrdersService.getWorkOrdersLimitedDetails(oddmeter.getTripSheetID(), valueObject.getInt("companyId")));
								return valueOutObject;
							}else if(oddmeter != null && oddmeter.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
								valueOutObject.put("tripStatus",tripSheetService.getTripSheetLimitedDetails(oddmeter.getTripSheetID()));
								return valueOutObject;
							}
							
							savedTripSheet	= tripSheetService.getSavedTripSheetByVid(valueObject.getInt("vehicleId"));
							if(savedTripSheet != null) {
								valueOutObject.put("savedTripSheet", savedTripSheet);
								return valueOutObject;
							}
							
							if((boolean) configuration.get("allowGPSIntegration")) {
								valueObject.put("configuration", configuration);
								ValueObject  gpsObject	= vehicleGPSDetailsService.getVehicleGPSByVendorProviderAtSpecifiedTime(valueObject, vehicle);
								if(gpsObject != null) {
									valueOutObject.put("gpsObject", gpsObject);
								}
							}
							tripSheetDto	=	 tripSheetService.getLastClosedTripSheetByDateTime(valueObject.getInt("vehicleId"), DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
							if(tripSheetDto != null) {
								valueOutObject.put("tripSheet", tripSheetDto);
								valueOutObject.put("tripStartDiesel", tripSheetDto.getTripStartDiesel());
								valueOutObject.put("tripEndDiesel", tripSheetDto.getTripEndDiesel());
							}
							
							if((boolean) tripConfig.get(TripSheetConfigurationConstant.VALIDATE_SERVICE_REMINDER)) {
								ValueObject valueObject2	= ServiceReminderService.getReminderStatusForOverdue(valueObject.getInt("vehicleId"));
								if(valueObject2 != null) {
									valueOutObject.put("serviceOverDue", true);
									valueOutObject.put("overDueReminder", valueObject.getString("overDueReminder"));
								}
							}
						}
					
					 
			 return valueOutObject;
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			return null;
		}finally {
			configuration		= null;
			vehicle				= null;
			valueOutObject		= null;
			dispatchDate		= null;
			dispatchTime		= null;
			tripSheetDto		= null;
		}
	}
	//Get_Vehicle_Header_Details
	@Transactional
	public List<VehicleDto> Get_Vehicle_Header_DetailsByGid(long vehicleGroupId) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT R.vid, R.vehicle_registration, R.vehicle_photoid, R.vStatusId, R.vehicle_Odometer,  VT.vtype, "
							+ "R.Vehicle_Location, VG.vGroup, TR.routeName, R.vehicleGroupId, R.driverMonthlySalary, VO.VEH_OWNER_NAME,"
							+ " VO.VEH_OWNER_ADDRESS "
							+ " from Vehicle AS R "
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.routeID "
							+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId "
							+ " LEFT JOIN VehicleOwner VO ON VO.VEHID = R.vid "
							+ " INNER JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId " + " where R.vehicleGroupId = "+vehicleGroupId+" "
							+ " and R.company_Id = " + userDetails.getCompany_id() + " and R.markForDelete = 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			List<VehicleDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] vehicle : results) {
					VehicleDto select = new VehicleDto();

					select.setVid((Integer) vehicle[0]);
					select.setVehicle_registration((String) vehicle[1]);
					select.setVehicle_photoid((Integer) vehicle[2]);
					select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus((short) vehicle[3]));
					select.setVehicle_Odometer((Integer) vehicle[4]);
					select.setVehicle_Type((String) vehicle[5]);
					select.setVehicle_Location((String) vehicle[6]);
					select.setVehicle_Group((String) vehicle[7]);
					select.setVehicle_RouteName((String) vehicle[8]);
					select.setVehicleGroupId((long) vehicle[9]);
					if(vehicle[10] != null)
					select.setDriverMonthlySalary((Double) vehicle[10]);
					select.setVehicle_Ownership((String) vehicle[11]);
					select.setCompanyName((String) vehicle[12]);
					
					list.add(select);
				
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleRepairAndPartConsumptionDetails(ValueObject valueObject) throws Exception {		
		ValueObject								valueOutObject							= null;
		CustomUserDetails 						userDetails								= null;
		ValueObject						   	 	tableConfig								= null;
		List<ServiceEntriesAndWorkOrdersDto>	workOrdersList							= null;	
		List<ServiceEntriesAndWorkOrdersDto>	serviceEntriesList						= null;
		List<ServiceEntriesAndWorkOrdersDto>	vehicleRepairAndPartConsumptionList		= null;
		short									vehicleServiceType						= 0;
		
		try {
			valueOutObject 							= new ValueObject();
			tableConfig								= new ValueObject();
			userDetails								= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleServiceType 						= valueObject.getShort("vehicleServiceType", (short)0);
			vehicleRepairAndPartConsumptionList		= new ArrayList<ServiceEntriesAndWorkOrdersDto>();
			
			switch (vehicleServiceType) {
			case 1://ALL
				workOrdersList 		= workOrdersService.getVehicleRepairAndPartConsumptionDetailsInWO(valueObject);
				serviceEntriesList 	= serviceEntriesService.getVehicleRepairAndPartConsumptionDetailsInSE(valueObject);
				vehicleRepairAndPartConsumptionList.addAll(workOrdersList);
				vehicleRepairAndPartConsumptionList.addAll(serviceEntriesList);
				
				break;
			case 2://SE
				vehicleRepairAndPartConsumptionList = serviceEntriesService.getVehicleRepairAndPartConsumptionDetailsInSE(valueObject);
				
				break;
			case 3://WO
				vehicleRepairAndPartConsumptionList = workOrdersService.getVehicleRepairAndPartConsumptionDetailsInWO(valueObject);
				break;	

			default:
				break;
			}
			
			valueOutObject.put("vehicleRepairAndPartConsumptionList", vehicleRepairAndPartConsumptionList);
			
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VEHICLE_REPAIR_AND_PART_CONSUMPTION_DATA_FILE_PATH);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);	
			
			valueOutObject.put("tableConfig", tableConfig.getHtData());
			valueOutObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueOutObject					= null;
			userDetails						= null;
			tableConfig						= null;
		}
	}
	
	@Override
	public ValueObject getVehicleAutoCompleteByVehicleGroup(String search,String vGroup) throws Exception {
		CustomUserDetails			userDetails						= null;
		List<Vehicle>				vehicleList						= null;
		Vehicle 					vehicle 						= null;
		ValueObject 				valueObject						= null;
		TypedQuery<Object[]> 		query							= null;
		List<Object[]> 				results							= null;	
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject					= new ValueObject();
			
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"SELECT V.vid, V.vehicle_registration FROM Vehicle AS V "
					+ " WHERE lower(V.vehicle_registration) Like (:search) AND V.vehicleGroupId IN("+vGroup+") "
					+ " AND V.company_Id = "+userDetails.getCompany_id()+" AND V.markForDelete = 0", Object[].class);
			}else {
				query = entityManager.createQuery(
						"SELECT V.vid, V.vehicle_registration FROM Vehicle AS V "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
						+ " WHERE lower(V.vehicle_registration) Like (:search) AND V.vehicleGroupId IN("+vGroup+") "
						+ " AND V.company_Id = "+userDetails.getCompany_id()+" AND V.markForDelete = 0", Object[].class);
			}
			query.setParameter("search", "%"+search+"%");
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				vehicleList = new ArrayList<Vehicle>();
				
				for (Object[] result : results) {
					vehicle = new Vehicle();
					vehicle.setVid((Integer) result[0]);
					vehicle.setVehicle_registration((String) result[1]);
					vehicleList.add(vehicle);
				}
			}
			
			valueObject.put("vehicleList", vehicleList);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;    
			vehicleList						= null;    
			vehicle 						= null;    
			valueObject						= null;    
			query							= null;    
			results							= null;	   
		}
	}
	
	@Override
	public ValueObject getVehicleDetailsFromVehicleGroup(ValueObject valueObject) throws Exception {	
		CustomUserDetails			userDetails			= null;
		List<Vehicle>				vehicleList			= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				vehicleList 				= vehicleRepository.getVehicleDetailsFromVGroup(valueObject.getLong("vehicleGroup"),userDetails.getCompany_id());
			}else {
				vehicleList 				= vehicleRepository.getVehicleDetailsFromVGroup(valueObject.getLong("vehicleGroup"), userDetails.getCompany_id(),userDetails.getId());
			}
			
			valueObject.put("vehicleList", vehicleList);
			
			return valueObject;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	
	
	
	@Override
	public List<VehicleDto> getListOfNumberOfSRNotCreatedOnVehicles(String vids, Integer companyID) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;

			queryt = entityManager.createQuery(
					" SELECT V.vid, V.vehicle_registration, VS.vStatus "
							+ " FROM Vehicle AS V "
							+ " INNER JOIN VehicleStatus AS VS ON VS.sid = V.vStatusId "
							+ " WHERE V.vid NOT IN ("+vids+") AND V.vStatusId <> 4 "
							+ " AND V.company_Id= "+companyID+" and V.markForDelete =0 ",
							Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<VehicleDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleDto>();
				VehicleDto service = null;
				
					for (Object[] result : results) {
						service = new VehicleDto();
						
						service.setVid((Integer) result[0]);
						service.setVehicle_registration((String) result[1]);
						service.setVehicle_Status((String) result[2]);
				
						Dtos.add(service);
					}
			}
				return Dtos;

		} catch (Exception e) {
		throw e;
		}

	}
	
	@Override
	public List<VehicleDto> getListOfNumberOfRRNotCreatedOnVehicles(String vids, Integer companyID) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;

			queryt = entityManager.createQuery(
					" SELECT V.vid, V.vehicle_registration, VS.vStatus "
							+ " FROM Vehicle AS V "
							+ " INNER JOIN VehicleStatus AS VS ON VS.sid = V.vStatusId "
							+ " WHERE V.vid NOT IN ("+vids+") AND V.vStatusId <> 4 "
							+ " AND V.company_Id= "+companyID+" and V.markForDelete =0 ",
							Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<VehicleDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleDto>();
				VehicleDto service = null;
				
					for (Object[] result : results) {
						service = new VehicleDto();
						
						service.setVid((Integer) result[0]);
						service.setVehicle_registration((String) result[1]);
						service.setVehicle_Status((String) result[2]);
				
						Dtos.add(service);
					}
			}
				return Dtos;

		} catch (Exception e) {
		throw e;
		}

	}
	
	@Override
	public List<VehicleDto> getListOfNumberOfFENotCreatedOnVehicles(String vids, Integer companyID) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;

			queryt = entityManager.createQuery(
					" SELECT V.vid, V.vehicle_registration, VS.vStatus, V.vehicle_Odometer, V.vehicle_ExpectedOdameter "
							+ " FROM Vehicle AS V "
							+ " INNER JOIN VehicleStatus AS VS ON VS.sid = V.vStatusId "
							+ " WHERE V.vid NOT IN ("+vids+") "
							+ " and V.company_Id= "+companyID+" and V.markForDelete = 0 AND V.vStatusId <> 4 ",
							Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<VehicleDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleDto>();
				VehicleDto service = null;
				
					for (Object[] result : results) {
						service = new VehicleDto();
						
						service.setVid((Integer) result[0]);
						service.setVehicle_registration((String) result[1]);
						service.setVehicle_Status((String) result[2]);
						service.setVehicle_Odometer((Integer) result[3]);
						service.setVehicle_ExpectedOdameter((Integer) result[4]);
				
						Dtos.add(service);
					}
			}
				return Dtos;

		} catch (Exception e) {
		throw e;
		}

	}
	
	@Override
	public List<VehicleDto> listOfVehiclesWithoutMileage(Integer companyID) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;

			queryt = entityManager.createQuery(
					" SELECT V.vid, V.vehicle_registration, VS.vStatus, V.vehicle_ExpectedMileage, V.vehicle_ExpectedMileage_to "
							+ " FROM Vehicle AS V "
							+ " INNER JOIN VehicleStatus AS VS ON VS.sid = V.vStatusId "
							+ " WHERE V.company_Id= "+companyID+" and V.markForDelete = 0 AND V.vStatusId <> 4 "
							+ " AND V.vehicle_ExpectedMileage = 0 AND V.vehicle_ExpectedMileage_to = 0 ",
							Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<VehicleDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleDto>();
				VehicleDto service = null;
				
					for (Object[] result : results) {
						service = new VehicleDto();
						
						service.setVid((Integer) result[0]);
						service.setVehicle_registration((String) result[1]);
						service.setVehicle_Status((String) result[2]);
						if(result[3] != null)
						service.setVehicle_ExpectedMileage(Double.parseDouble(toFixedTwo.format(result[3])));
						if(result[4] != null)
						service.setVehicle_ExpectedMileage_to(Double.parseDouble(toFixedTwo.format(result[4])));
						Dtos.add(service);
					}
			}
				return Dtos;

		} catch (Exception e) {
		throw e;
		}

	}
	
	@Override
	public List<VehicleDto> getActiveVehicleInDateRange(String fromDate, String toDate, Integer companyId, Long userId)
			throws Exception {
		TypedQuery<Object[]> query = null;
		boolean		vehicleGroupWisePermission	= false;
		try {
			vehicleGroupWisePermission	= companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if(!vehicleGroupWisePermission) {
				query = entityManager.createQuery(
						"SELECT DISTINCT V.vid, V.vehicle_registration, V.vehicleTypeId "
								+ " From Vehicle AS V "
								+ " INNER JOIN TripSheet T ON T.vid = V.vid AND T.closetripDate between '"+fromDate+"' AND '"+toDate+"' "
								+ " where T.markForDelete = 0 AND V.company_Id = "+companyId+" ",
								Object[].class);
			}else {
				query = entityManager.createQuery(
						"SELECT DISTINCT V.vid, V.vehicle_registration, V.vehicleTypeId "
								+ " From Vehicle AS V "
								+ " INNER JOIN TripSheet T ON T.vid = V.vid AND T.closetripDate between '"+fromDate+"' AND '"+toDate+"' "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId and VGP.user_id = "+userId+""
								+ " where T.markForDelete = 0 AND V.company_Id = "+companyId+" ",
								Object[].class);
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		
		List<Object[]> results = query.getResultList();

		List<VehicleDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleDto>();
			VehicleDto select = null;
			for (Object[] vehicle : results) {

				select = new VehicleDto();
				select.setVid((int) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicleTypeId((long) vehicle[2]);

				Dtos.add(select);
			}
		}
		return Dtos;

	}
	
	@Override
	public List<VehicleDto> getActiveVehicleInDateRange(String selectQuery, Integer companyId, Long userId) throws Exception {
		TypedQuery<Object[]> query = null;
		boolean		vehicleGroupWisePermission	= false;
		try {
			vehicleGroupWisePermission	= companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if(!vehicleGroupWisePermission) {
				query = entityManager.createQuery(
						"SELECT DISTINCT V.vid, V.vehicle_registration, V.vehicleTypeId "
								+ " From Vehicle AS V "
								+ " INNER JOIN TripSheet T ON T.vid = V.vid  "
								+ " where T.markForDelete = 0  "+selectQuery+" AND V.company_Id = "+companyId+" ",
								Object[].class);
			}else {
				query = entityManager.createQuery(
						"SELECT DISTINCT V.vid, V.vehicle_registration, V.vehicleTypeId "
								+ " From Vehicle AS V "
								+ " INNER JOIN TripSheet T ON T.vid = V.vid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId and VGP.user_id = "+userId+""
								+ " where T.markForDelete = 0  "+selectQuery+" AND V.company_Id = "+companyId+" ",
								Object[].class);
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		
		List<Object[]> results = query.getResultList();

		List<VehicleDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleDto>();
			VehicleDto select = null;
			for (Object[] vehicle : results) {

				select = new VehicleDto();
				select.setVid((int) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicleTypeId((long) vehicle[2]);

				Dtos.add(select);
			}
		}
		return Dtos;

	}
	
	@Override
	public ValueObject getActiveVehicleListInRange(ValueObject valueObject) throws Exception {
		String				query		= "";
		String				fromDate				= null;
		String				toDate					= null;
		ValueObject			dateRangeObj			= null;
		try {
			dateRangeObj	= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"), DateTimeUtility.DD_MM_YYYY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			fromDate		= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate			= dateRangeObj.getString(DateTimeUtility.TO_DATE);
			
			query += "AND T.closetripDate between '"+fromDate+"' AND '"+toDate+"' ";
			if(valueObject.getInt("routeId",0) > 0) {
				query += "AND T.routeID = "+valueObject.getInt("routeId",0)+" ";
			}
			if(valueObject.getLong("vehicleTypeId",0) > 0) {
				query += "AND V.vehicleTypeId = "+valueObject.getLong("vehicleTypeId",0)+" ";
			}
			valueObject.put("vehicleList", getActiveVehicleInDateRange(query, valueObject.getInt("companyId", 0), valueObject.getLong("userId", 0)));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			query			= null;
			fromDate		= null;
			toDate			= null;
			dateRangeObj	= null;

		}
	}
	
	@Override
	public ValueObject getActiveVehicleDetailsOfTheMonth(ValueObject valueObject) throws Exception {
		Thread 											active					= null;
		Thread 											inactive				= null;
		Thread 											sold					= null;
		try {
			active = new Thread() {			
				public void run() {
					try{
						TypedQuery<Object[]> typedQuery = null;
						typedQuery = entityManager.createQuery(
								"SELECT Count(V.vid), V.company_Id, C.company_name From Vehicle AS V "
										+ " INNER JOIN Company AS C ON C.company_id = V.company_Id "
										+ " where V.vStatusId IN ("+valueObject.getString("vehicleActiveStatus")+") "
										+ " AND V.created <= '"+ valueObject.getString("finalEndDate") +" '  AND V.markForDelete = 0 AND  V.company_Id IN("+valueObject.getString("companyId")+") "
										+ " OR (V.created <= '"+ valueObject.getString("finalEndDate") +"' AND V.vStatusId IN ("+valueObject.getString("vehicleInActiveStatus")+") AND V.lastupdated > '"+valueObject.getString("finalEndDate") +"')  "
										+ " group by V.company_Id ",Object[].class);
						
						List<Object[]> results = typedQuery.getResultList();

						List<CompanyDto> DtosActive = null;
						if (results != null && !results.isEmpty()) {
							
							DtosActive = new ArrayList<CompanyDto>();
							CompanyDto list = null;
							for (Object[] result : results) {
								list = new CompanyDto();
								list.setTotalActiveVehicleCount((long) result[0]);
								list.setCompany_id((int) result[1]);
								list.setCompany_name((String) result[2]);
								
								DtosActive.add(list);
							}
							
						}
						valueObject.put("Active", DtosActive);
					}
					catch(Exception e){
						e.printStackTrace();						
					}				
				}
			};				
			active.start();
			
			inactive = new Thread() {				
				public void run() {
					try{
						TypedQuery<Object[]> typedQuery = null;
						typedQuery = entityManager.createQuery(
								"SELECT Count(V.vid), V.company_Id From Vehicle AS V "
										+ " where V.vStatusId = "+VehicleStatusConstant.VEHICLE_STATUS_INACTIVE+"  AND  V.company_Id IN("+valueObject.getString("companyId")+") " 
										+ " And V.lastupdated between '" + valueObject.getString("finalStartDate") + "' AND '" + valueObject.getString("finalEndDate") +"' AND V.markForDelete = 0 "
										+ " group by V.company_Id",
										Object[].class);
						
						List<Object[]> results = typedQuery.getResultList();
						List<CompanyDto> DtosInactive = null;
						if (results != null && !results.isEmpty()) {
							DtosInactive = new ArrayList<CompanyDto>();
							CompanyDto list1 = null;
							for (Object[] result : results) {
								list1 = new CompanyDto();
							if(result !=null) {
								list1.setTotalInActiveVehicleCount((long) result[0]);
								list1.setCompany_id((int) result[1]);
								}
								DtosInactive.add(list1);
							}
						}
						valueObject.put("Inactive", DtosInactive);
						
					}
					catch(Exception e){
						e.printStackTrace();						
					}				
				}				
			};
			inactive.start();
			
			sold=new Thread() {
				public void run() {
					try {
						TypedQuery<Object[]> typedQuery = null;
						typedQuery = entityManager.createQuery(
								" SELECT Count(V.vid), V.company_Id From Vehicle AS V "
										+ " where V.vStatusId = "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+"  AND  V.company_Id IN("+valueObject.getString("companyId")+") " 
										+ " And V.lastupdated between '" + valueObject.getString("finalStartDate") + "' AND '" + valueObject.getString("finalEndDate") +"' AND V.markForDelete = 0 "
										+ " group by V.company_Id",
										Object[].class);
						List<Object[]> results = typedQuery.getResultList();
						List<CompanyDto> DtosSold = null;
						if (results != null && !results.isEmpty()) {
							DtosSold = new ArrayList<CompanyDto>();
							CompanyDto list2 = null;
							for (Object[] result : results) {							
								list2 = new CompanyDto();
								if(result != null) {
									list2.setTotalSoldVehicleCount((long) result[0]);
									list2.setCompany_id((int) result[1]);
								}
								DtosSold.add(list2);
							}
						}
						valueObject.put("Sold", DtosSold);
					}
					catch(Exception e) {
						e.printStackTrace();							
					}				
				}				
			};
			sold.start();
			
			active.join();
			inactive.join();
			sold.join();
			
			return	valueObject;
		} catch (Exception e) {
			LOGGER.error("Error : ", e);
			throw e;
		} finally {

		}
	}
	@Override
	public Vehicle getLimitedVehicleDetails(Integer vid) throws Exception {
		
		return vehicleRepository.getLimitedVehicleDetails(vid);
	}
	
	@Transactional
	public Integer updateCurrentOdoMeterGETVehicleToCurrentOdometer(int vid,int companyId) throws Exception {
		try{
			return vehicleRepository.updateCurrentOdoMeterGETVehicleToCurrentOdometer(vid, companyId);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	@Transactional
	public VehicleDto Get_Vehicle_Header_Details(Integer vehicle_ID,Integer companyId) throws Exception {
		Object[] vehicle = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT R.vid, R.vehicle_registration, R.vehicle_photoid, R.vStatusId, R.vehicle_Odometer,  VT.vtype, "
							+ "R.Vehicle_Location, VG.vGroup, TR.routeName, R.vehicleGroupId, R.driverMonthlySalary, VO.VEH_OWNER_NAME,"
							+ " VO.VEH_OWNER_ADDRESS "
							+ " from Vehicle AS R "
							+ " LEFT JOIN TripRoute TR ON TR.routeID = R.routeID "
							+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId "
							+ " LEFT JOIN VehicleOwner VO ON VO.VEHID = R.vid "
							+ " INNER JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId " + " where R.vid = :id "
							+ " and R.company_Id = " + companyId + " and R.markForDelete = 0");
			query.setParameter("id", vehicle_ID);
			query.setMaxResults(1);
			vehicle = (Object[]) query.getSingleResult();
			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_photoid((Integer) vehicle[2]);
				select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus((short) vehicle[3]));
				select.setVehicle_Odometer((Integer) vehicle[4]);
				select.setVehicle_Type((String) vehicle[5]);
				select.setVehicle_Location((String) vehicle[6]);
				select.setVehicle_Group((String) vehicle[7]);
				select.setVehicle_RouteName((String) vehicle[8]);
				select.setVehicleGroupId((long) vehicle[9]);
				if(vehicle[10] != null)
				select.setDriverMonthlySalary((Double) vehicle[10]);
				select.setVehicle_Ownership((String) vehicle[11]);
				select.setCompanyName((String) vehicle[12]);
			}
			return select;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleRouteChangeReport(ValueObject valueObject) throws Exception {


		Integer						vehicleId				= 0;
		String						dateRange				= "";
		CustomUserDetails			userDetails				= null;
		List<VehicleHistoryDto> 	vehicleHistoryList	   	= null;	
		String 						query					= "";
		ValueObject					tableConfig				= null;
		
		
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			vehicleId						= valueObject.getInt("vehicleId", 0);
			dateRange						= valueObject.getString("date");
		

			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");
				
				dateRangeFrom =dateFormatSQL.format(dateFormat.parse(From_TO_DateRange[0]))+ DateTimeUtility.DAY_START_TIME;
				
				dateRangeTo =dateFormatSQL.format(dateFormat.parse(From_TO_DateRange[1]))+ DateTimeUtility.DAY_END_TIME;
				
				String  Vehicle = "",Date = "";

			
				if(vehicleId > 0 )
				{
					Vehicle = " AND  V.vid = "+ vehicleId +" ";
				}

				Date		=	" AND V.vHistorycreated between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;

				query       = " " + Date +" "+ Vehicle +" ";
			}	

			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VEHICLE_ROUTE_CHANGE_REPORT);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

			vehicleHistoryList		= allVehicleRouteChangeReport(userDetails.getCompany_id(),query);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("VehicleHistoryList", vehicleHistoryList);
			
			valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;			
		}
	}
	
	
	@Transactional
	public List<VehicleHistoryDto> allVehicleRouteChangeReport(int companyId,String queryIn) throws Exception {

		TypedQuery<Object[]> query = null;
		try {
			
		
		query = entityManager
				.createQuery("SELECT V.vHistoryId, V.vid,V.vehicle_registration,V.oldRouteId,V.newRouteId,V.vHistorycreatedById,V.vHistorycreated, "
						+ "TR.routeName,T.routeName ,U.firstName , U.lastName ,VE.markForDelete FROM VehicleHistory AS V"
						+ " INNER JOIN Vehicle VE ON VE.vid = V.vid "
						+ "LEFT JOIN TripRoute T ON T.routeID = V.newRouteId  "
						+ " LEFT JOIN TripRoute TR ON TR.routeID = V.oldRouteId "
						+ " INNER JOIN User U ON U.id  = V.vHistorycreatedById "
						+ " WHERE V.company_Id = "+companyId+" AND V.isRouteChanged = 1 "+queryIn+" ", Object[].class);

		List<Object[]> results = query.getResultList();

		List<VehicleHistoryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleHistoryDto>();
			VehicleHistoryDto vehicleHistory = null;
			for (Object[] result : results) {
				vehicleHistory = new VehicleHistoryDto();
				 
				vehicleHistory.setvHistoryId((Integer)result[0]);
				vehicleHistory.setVid((Integer)result[1]);
				vehicleHistory.setOldRouteId((Integer) result[3]);
				vehicleHistory.setNewRouteId( (Integer) result[4]);
				vehicleHistory.setLastModifiedById((Long) result[5]);
				if(result[6] != null)
				vehicleHistory.setLastupdatedStr(dateFormat.format(result[6]));
				if(result[7] != null)
				vehicleHistory.setOldRouteName((String) result[7]);
				if(result[8] != null)
				vehicleHistory.setNewRouteName((String) result[8]);
				vehicleHistory.setUserName(result[9]+" "+result[10]);
				vehicleHistory.setMarkForDelete((boolean) result[11]);
				if(vehicleHistory.isMarkForDelete()) {
					vehicleHistory.setVehicle_registration( "<a style=\"color: red; background: #ffc;\" href=\"#\">"+result[2]+"</a>" );
				}else {
					vehicleHistory.setVehicle_registration( "<a target=_blank style=\"color: blue; background: #ffc;\" href=\"showVehicle?vid="+vehicleHistory.getVid()+"\">"+result[2]+"</a>" );
				}
				Dtos.add(vehicleHistory);
			}
		}
		return Dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Transactional
	public List<Vehicle> getVehicleListByGpsVenordId(Integer  gpsVendorId) throws Exception {
		try {TypedQuery<Object[]> 	queryt 			= null;
		List<Vehicle> 			vehicleList 	= null;
		queryt = entityManager.createQuery("SELECT V.vid, V.vehicle_registration,V.company_Id "
				+ " from Vehicle AS V  where V.gpsVendorId =" + gpsVendorId + " AND V.company_Id = 40 AND V.markForDelete = 0",
				Object[].class);

		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			vehicleList = new ArrayList<Vehicle>();
			Vehicle vehicle = null;
			for (Object[] result : results) {
				vehicle = new Vehicle();
				vehicle.setVid((Integer) result[0]);
				vehicle.setVehicle_registration((String) result[1]);
				vehicle.setCompany_Id((Integer) result[2]);
				vehicleList.add(vehicle);
			}
		}
		return vehicleList;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}
	
	public JSONArray getIntangleVehicleList(HashMap<String, Object> configuration) throws Exception {
		HttpResponse<String> 			intangleVehicleListResponse		= null;
		JSONObject						intangleVehicleListJsonObject 	= null;
		JSONObject						intangleVehicleListResult 		= null;
		JSONArray						intangleVehicleListJsonArray	= null;
		String 							intangleVehicleListURL 			= "";
		String 							intangleFuelApiAsscessToken 	= "";
		try {
			intangleVehicleListURL 			= (String) configuration.getOrDefault("intangleVehicleListURL", "");
			intangleFuelApiAsscessToken 	= (String) configuration.getOrDefault("intangleFuelAPI_AsscessToken", "");
			intangleVehicleListJsonArray	= new JSONArray();
			Unirest.setTimeouts(0, 0);
			intangleVehicleListResponse 		= Unirest.get(""+intangleVehicleListURL+"").header("Api-access-token", ""+intangleFuelApiAsscessToken+"").asString();
			
			if(intangleVehicleListResponse.getStatus() == 200) {
				intangleVehicleListJsonObject	= new JSONObject(intangleVehicleListResponse.getBody());
				if(intangleVehicleListJsonObject.has("result")) {
					intangleVehicleListResult 		= (JSONObject) intangleVehicleListJsonObject.get("result");
					intangleVehicleListJsonArray	= intangleVehicleListResult.getJSONArray("vehicles");
				}
			}
			return intangleVehicleListJsonArray;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public JSONArray getIntangleByVehicleNumber(HashMap<String, Object> configuration,ValueObject valueObject) throws Exception {
		HttpResponse<String> 			intangleVehicleListResponse		= null;
		JSONObject						intangleVehicleListJsonObject 	= null;
		JSONObject						intangleVehicleListResult 		= null;
		JSONArray						intangleVehicleListJsonArray	= null;
		String 							intangleVehicleListURL 			= "";
		String 							intangleFuelApiAsscessToken 	= "";
		String 							vehicleNumber				 	= "";
		try {
			vehicleNumber 					= valueObject.getString("vehicleNumber");
			intangleVehicleListURL 			= (String) configuration.getOrDefault("intangleVehicleListURL", "");
			intangleFuelApiAsscessToken 	= (String) configuration.getOrDefault("intangleFuelAPI_AsscessToken", "");
			intangleVehicleListJsonArray	= new JSONArray();
			Unirest.setTimeouts(0, 0);
			intangleVehicleListResponse 	= Unirest.get(""+intangleVehicleListURL+"&query="+vehicleNumber).header("Api-access-token", ""+intangleFuelApiAsscessToken+"").asString();
			
			if(intangleVehicleListResponse != null && intangleVehicleListResponse.getStatus() == 200) {
				intangleVehicleListJsonObject		= new JSONObject(intangleVehicleListResponse.getBody());
				if(intangleVehicleListJsonObject.has("result")) {
					intangleVehicleListResult 		= (JSONObject) intangleVehicleListJsonObject.get("result");
					intangleVehicleListJsonArray	= intangleVehicleListResult.getJSONArray("vehicles");
				}
			}
			return intangleVehicleListJsonArray;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public List<Vehicle> getVehicleByTypeAndModal(Long vehicleType, Long vehicleModelId, Integer companyId)
			throws Exception {
		return vehicleRepository.getVehicleByTypeAndModal(vehicleType, vehicleModelId, companyId);
	}
	
	@Override
	@Transactional
	public void updateServiceProgramId(Long serviceProgramId, Integer vid) throws Exception {
		entityManager.createQuery(" UPDATE Vehicle SET serviceProgramId = "+serviceProgramId
		+ " WHERE vid = " + vid +"  " ).executeUpdate();
		
	}
	
	@Override
	@Transactional
	public void updateVehicleByServiceProgramId(Long oldServiceProgramId, Long newServiceProgramId, Integer companyId) throws Exception {
		entityManager.createQuery(" UPDATE Vehicle SET serviceProgramId = "+newServiceProgramId
		+ " WHERE serviceProgramId = " + oldServiceProgramId +" AND company_Id = "+companyId+"  " ).executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVehicleServiceProgramStatus(Long vehicleTypeId, Long modal, Long serviceProgramId,
			Integer companyId) throws Exception {
		
		entityManager.createQuery(" UPDATE Vehicle SET serviceProgramId = 0 "
				+ " WHERE vehicleTypeId = "+vehicleTypeId+" AND vehicleModalId = "+modal+""
				+ " AND  serviceProgramId = " + serviceProgramId +" AND company_Id = "+companyId+" " ).executeUpdate();
		
	}
	
	@Override
	public void prepareForInspectionSheetAssignForNewVid(int vid,Long vehicleTypeId,Integer branchId) throws Exception {
		List<VehicleInspectionSheetToParameter> emptySheet			=   null;
		CustomUserDetails     userDetails = null;
		VehicleTypeAssignmentDetails vehicleTypeAssignmentDetailsDto = null;
		VehicleInspctionSheetAsingmentDto vehicleInspctionSheetAsingmentDto = null;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(branchId > 0) {
				vehicleTypeAssignmentDetailsDto=vehicleTypeAssignmentRepository.getVehicleTypeAssignmentByTypeAndBranch(branchId,vehicleTypeId,userDetails.getCompany_id());
				if(vehicleTypeAssignmentDetailsDto!= null) {
					emptySheet = VehicleInspectionSheetToParameterRepository.getAllParametersInVehicleInspectionSheets(vehicleTypeAssignmentDetailsDto.getInspectionSheetId(), userDetails.getCompany_id());
					if(!emptySheet.isEmpty()) {
						vehicleInspctionSheetAsingmentDto= new VehicleInspctionSheetAsingmentDto();
						vehicleInspctionSheetAsingmentDto.setInspectionSheetId(vehicleTypeAssignmentDetailsDto.getInspectionSheetId());
						vehicleInspctionSheetAsingmentDto.setVid(vid);
						vehicleInspctionSheetAsingmentDto.setVehicleTypeId(vehicleTypeId);
						vehicleInspctionSheetAsingmentDto.setInspectionStartDateStr(DateTimeUtility.getCurrentDateInStringViewFormat());
						vehicleInspctionSheetAsingmentDto.setBranchId(branchId);
						vehicleInspectionSheetService.addInspectionSheetToVehicle(vehicleInspctionSheetAsingmentDto);
					}
				}	}
		} catch (Exception e) {
			throw e;
		}
		
		
	}
	
	@Override
	public List<Vehicle> getVehicleByTypeModalAndBranch(Long vehicleType, Long vehicleModelId, Integer branchId, Integer companyId)
			throws Exception {
		return vehicleRepository.getVehicleByTypeModalAndBranch(vehicleType, vehicleModelId,branchId, companyId);
	}
	
	@Override
	@Transactional
	public void updateBranchWiseVehicleServiceProgramStatus(Long vehicleTypeId, Long modal,Integer branchId, Long serviceProgramId,
			Integer companyId) throws Exception {
		
		entityManager.createQuery(" UPDATE Vehicle SET serviceProgramId = 0 "
				+ " WHERE vehicleTypeId = "+vehicleTypeId+" AND vehicleModalId = "+modal+" AND branchId = "+branchId+""
				+ " AND  serviceProgramId = " + serviceProgramId +" AND company_Id = "+companyId+" " ).executeUpdate();
		
	}
	
	@Transactional
	public ValueObject getVehicleListForCreateServiceProgram(ValueObject object) throws Exception {
		TypedQuery<Object[]> 	query 			= null;
		List<VehicleDto> 		vehicleList 	= null;
		VehicleDto 				vehicleDto 		= null;
		String					branchQuery		= "";
		try {
			if(object.getBoolean("vehicleBranchWiseProgramConfig")) {
				branchQuery = " AND V. branchId ="+object.getInt("vehicleBranchId") +" ";
			}
			query = entityManager.createQuery(
					" SELECT V.vid, V.vehicle_registration FROM Vehicle AS V where V.vehicleModalId ="+object.getLong("vehicleModalId")+" " 
					+" AND V.vehicleTypeId = "+object.getLong("vehicleTypeId")+" "+branchQuery+" AND V.markForDelete = 0",
					Object[].class);
			List<Object[]> results = query.getResultList();

			
			if (results != null && !results.isEmpty()) {
				vehicleList = new ArrayList<VehicleDto>();
				for (Object[] result : results) {
					vehicleDto = new VehicleDto();
					vehicleDto.setVid((Integer)result[0]);
					vehicleDto.setVehicle_registration((String)result[1]);
					vehicleList.add(vehicleDto);
				}
				object.put("vehicleList",vehicleList);
			}
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public List<Vehicle> getVehicleListForSR(String vids, Integer companyID) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
			vids	= Utility.removeLastComma(vids);
			queryt = entityManager.createQuery(
					" SELECT V.vid, V.vehicle_registration, V.vStatusId, V.vehicle_Odometer, V.vehicleGroupId, V.createdById, V.lastModifiedById, V.company_Id FROM Vehicle AS V "
							+ " WHERE V.vid IN ("+vids+") AND V.vStatusId <> 4 "
							+ " AND V.company_Id= "+companyID+" and V.markForDelete = 0 ",
							Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<Vehicle> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<Vehicle>();
				Vehicle service = null;
				
					for (Object[] result : results) {
						service = new Vehicle();
						
						service.setVid((Integer) result[0]);
						service.setVehicle_registration((String) result[1]);
						service.setvStatusId((short) result[2]);
						service.setVehicle_Odometer((Integer) result[3]);
						service.setVehicleGroupId((Long) result[4]);
						service.setCreatedById((Long) result[5]);
						service.setLastModifiedById((Long) result[6]);
						service.setCompany_Id((Integer) result[7]);
						Dtos.add(service);
					}
			}
				return Dtos;

		} catch (Exception e) {
		throw e;
		}

	}
	
	@Override
	@Transactional
	public ValueObject checkVehicleStatusByVid(ValueObject valueObject) throws Exception {
		VehicleDto vehicle = Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("vid",0),valueObject.getInt("companyId",0));
		if(vehicle != null) {
			valueObject.put("vehicle", vehicle);
		}
		return valueObject;
	}
	public ValueObject getIntangleByVehicleNumberFromMob(ValueObject valueObjectIn) throws Exception {
		ValueObject 					model 						 = null;
		JSONArray						intangleVehicleListJsonArray = null;
		JSONObject						object						 = null;
		JSONObject						healthInfoObj			     = null;
		HashMap<String, Object> 		vehicleConfiguration	 	 = null;
		String 							healthStatus 				= null;

		try {
			vehicleConfiguration		 = companyConfigurationService.getCompanyConfiguration(0, PropertyFileConstants.VEHICLE_GPS_CONFIG);
			intangleVehicleListJsonArray = getIntangleByVehicleNumber(vehicleConfiguration,valueObjectIn);
			if(intangleVehicleListJsonArray != null && intangleVehicleListJsonArray.length() > 0) {
				for(int i=0; i<intangleVehicleListJsonArray.length(); i++) {
					object				= (JSONObject) intangleVehicleListJsonArray.get(i);
					healthInfoObj 		= (JSONObject) object.get("health_info");
					if(healthInfoObj.getString("health") != null) {
						healthStatus = (healthInfoObj.getString("health"));
					}
				}
			}
			model = new ValueObject();
			model.put("healthStatus", healthStatus);
			return model;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	@Override
	@Transactional
	public List<Vehicle> searchActiveVehicle(String term) throws Exception {
		CustomUserDetails 		userDetails = null;
		TypedQuery<Object[]>	query 		= null;
		List<Vehicle> 			vehicleList = null;
		Vehicle 				vehicle 	= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
				if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
						PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
					query = entityManager.createQuery("SELECT v.vid, v.vehicle_registration, v.vehicleModalId, v.vehicle_Odometer FROM Vehicle AS v "
							+ " where lower(v.vehicle_registration) Like ('%" + term + "%') AND v.company_Id = " + userDetails.getCompany_id() + " "
							+ " AND v.vStatusId NOT IN("+VehicleStatusConstant.VEHICLE_STATUS_INACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_SURRENDER+","+VehicleStatusConstant.VEHICLE_STATUS_SOLD+") AND v.markForDelete = 0", Object[].class);
				} else {
					query = entityManager.createQuery("SELECT v.vid, v.vehicle_registration, v.vehicleModalId, v.vehicle_Odometer FROM Vehicle AS v "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId and VGP.user_id ="
							+ userDetails.getId() + " " 
							+ " where lower(v.vehicle_registration) Like ('%" + term + "%') AND v.company_Id = " + userDetails.getCompany_id() + " "
							+ " AND v.vStatusId NOT IN("+VehicleStatusConstant.VEHICLE_STATUS_INACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_SURRENDER+","+VehicleStatusConstant.VEHICLE_STATUS_SOLD+") AND v.markForDelete = 0", Object[].class);
				}
			}
			List<Object[]> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				vehicleList = new ArrayList<Vehicle>();
				for (Object[] result : results) {
					vehicle = new Vehicle();
					vehicle.setVid((Integer) result[0]);
					vehicle.setVehicle_registration((String) result[1]);
					if(result[2] != null) {
						vehicle.setVehicleModalId((Long) result[2]);;
					}
					vehicleList.add(vehicle);
				}
			}
			return vehicleList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public List<Vehicle> searchActiveVehicleByVehicleModel(String term, Long vehicleModelId) throws Exception {
		CustomUserDetails 		userDetails = null;
		TypedQuery<Object[]>	query 		= null;
		List<Vehicle> 			vehicleList = null;
		Vehicle 				vehicle 	= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery("SELECT v.vid, v.vehicle_registration, v.vehicleModalId, v.vehicle_Odometer FROM Vehicle AS v "
						+ " where lower(v.vehicle_registration) Like ('%" + term + "%') AND v.company_Id = " + userDetails.getCompany_id() + " AND v.vehicleModalId = "+vehicleModelId+""
						+ " AND v.vStatusId NOT IN("+VehicleStatusConstant.VEHICLE_STATUS_INACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_SURRENDER+","+VehicleStatusConstant.VEHICLE_STATUS_SOLD+") AND v.markForDelete = 0", Object[].class);
			} else {
				query = entityManager.createQuery("SELECT v.vid, v.vehicle_registration, v.vehicleModalId, v.vehicle_Odometer FROM Vehicle AS v "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId and VGP.user_id ="
						+ userDetails.getId() + " " 
						+ " where lower(v.vehicle_registration) Like ('%" + term + "%') AND v.company_Id = " + userDetails.getCompany_id() + " AND v.vehicleModalId = "+vehicleModelId+"" 
						+ " AND v.vStatusId NOT IN("+VehicleStatusConstant.VEHICLE_STATUS_INACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_SURRENDER+","+VehicleStatusConstant.VEHICLE_STATUS_SOLD+") AND v.markForDelete = 0", Object[].class);
			}

			List<Object[]> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				vehicleList = new ArrayList<Vehicle>();
				for (Object[] result : results) {
					vehicle = new Vehicle();
					vehicle.setVid((Integer) result[0]);
					vehicle.setVehicle_registration((String) result[1]);
					if(result[2] != null) {
						vehicle.setVehicleModalId((Long) result[2]);;
					}
					vehicleList.add(vehicle);
				}
			}
			return vehicleList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Long checkVehicleByVehicleModel(Long vehicleModelId, Integer companyId) throws Exception {
		try {
			return vehicleRepository.checkVehicleByVehicleModel(vehicleModelId,companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject getVehicleWiseTyreAssignReport(ValueObject valueObject) throws Exception {		
		ValueObject											valueOutObject;
		int												vid						= 0;
		ValueObject						                  	tableConfig				= null;
		List<InventoryTyreDto> 								currentTyreList			= null;	
		
		try {
			CustomUserDetails userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
				vid							= valueObject.getInt("vehicleId", 0);
				int vehicleGroup			= valueObject.getInt("vehicleGroup", 0);
				String vehicleId = "",vehicleGroupQ="";
				
				if(vid != 0)
					vehicleId = " AND IT.VEHICLE_ID = "+ vid +" ";
				if(vehicleGroup > 0)
					vehicleGroupQ=" AND V.vehicleGroupId = "+vehicleGroup+" ";
				
				String query = " " + vehicleId + " "+vehicleGroupQ+" ";				
				
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VEHICLE_WISE_TYRE_ASSIGN_TABLE_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);	
				
				valueOutObject = new ValueObject();
				valueOutObject.put("tableConfig", tableConfig.getHtData());
				currentTyreList = VehicleDaoImpl.getVehicleWiseTyreReportList(query, userDetails.getCompany_id());
				valueOutObject.put("currentTyreList", currentTyreList);
				valueOutObject.put("vGroupSelected", valueObject.getInt("vehicleGroup", 0));
				
			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			tableConfig			= null;
		}
	}
	
	@Override
	public ValueObject getVehicleExpenseDetails(ValueObject object) {
		
		String list = "list";
		try {
		short typeId =object.getShort("typeId",(short)0);
		int companyId =object.getInt("companyId",0);
		
		Calendar aCalander = Calendar.getInstance();
		
		Date toDate  = aCalander.getTime();
		aCalander.add(Calendar.MONTH, -3);
		aCalander.set(Calendar.DATE,1);
		Date fromDate =aCalander.getTime();
		
		if(typeId == 1) 
			object.put(list, getRRList(formatSQL.format(fromDate),formatSQL.format(toDate), companyId, object.getInt("vid",0)));
		else if(typeId == 2)	
			object.put(list, getFuleList(formatSQL.format(fromDate),formatSQL.format(toDate), companyId, object.getInt("vid",0)));
		else if(typeId ==3) 
			object.put(list,getWOList(formatSQL.format(fromDate),formatSQL.format(toDate), companyId, object.getInt("vid",0)));
		else if (typeId == 4)
			object.put(list,getDealerServiceList(formatSQL.format(fromDate),formatSQL.format(toDate),object.getInt("vid",0),companyId));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
		
	}
	
	public List<FuelDto> getFuleList(String currentDate,String toDate,int companyId,int vid){
		List<FuelDto> finalList = null;
		TypedQuery<Object[]> query = entityManager.createQuery("select fuel_id,fuel_Number,"
				+ "fuel_date,fuel_amount FROM Fuel  where  vid =:id AND  companyId = " + companyId
				+ " AND fuel_date BETWEEN '"+ currentDate + "' AND '" + toDate + "' AND markForDelete = 0", Object[].class);
		query.setParameter("id", vid);
		
		List<Object[]> list =query.getResultList();
		if(list != null && !list.isEmpty()) {
			finalList = new ArrayList<>();
			FuelDto dto = null;
			for(Object[] result:list) {
				dto = new FuelDto();
				dto.setFuel_id((Long) result[0]);
				dto.setFuel_Number((Long) result[1]);
				dto.setFuel_D_date((Date) result[2]);
				dto.setFuel_amount((Double) result[3]);
				finalList.add(dto);
			}
		}
		return finalList;
	} 
	
	public List<WorkOrdersDto> getWOList(String currentDate,String toDate,int companyId,int vid){
		WorkOrdersDto         dto 			= null;
		List<WorkOrdersDto>   list 			=null;

		try {
			TypedQuery<Object[]> query = entityManager.createQuery( "SELECT workorders_id,workorders_Number,totalworkorder_cost FROM WorkOrders AS WOR where  WOR.vehicle_vid =:id  AND WOR.start_date BETWEEN '"
					+ currentDate + "' AND '" + toDate + "' AND WOR.companyId = " + companyId
					+ " AND WOR.markForDelete = 0",Object[].class);

			query.setParameter("id", vid);
			List<Object[]> resultList =query.getResultList();

			if(resultList != null && !resultList.isEmpty()) {
				list = new ArrayList<>();
				for(Object[] result:resultList) {
					dto = new WorkOrdersDto();
					dto.setWorkorders_id((Long) result[0]);
					dto.setWorkorders_Number((Long) result[1]);
					dto.setTotalworkorder_cost((Double) result[2]);
					list.add(dto);				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return list;
	} 
	
	public List<RenewalReminder> getRRList(String currentDate,String toDate,int companyId,int vid){
		TypedQuery<RenewalReminder> query =entityManager.createQuery("FROM RenewalReminder As RR Where RR.vid=:id AND RR.renewal_from BETWEEN '" + currentDate
				+ "' AND '" + toDate + "' AND RR.companyId = " + companyId
				+ " AND RR.markForDelete = 0", RenewalReminder.class);
		query.setParameter("id",vid);
		List<RenewalReminder> list=query.getResultList();
		return list;
	}
	
	public List<DealerServiceEntriesDto> getDealerServiceList(String fromDate, String toDate,int vid,int companyId){
		 List<DealerServiceEntriesDto> list = null;
		 ValueObject object = new ValueObject();
		try {
			object.put("companyId", companyId);
			String query ="AND DSE.vid ="+vid+" AND DSE.invoiceDate BETWEEN '"+fromDate+"' AND '"+toDate+"' AND DSE.markForDelete=0";
			object.put("companyId", companyId);
			object.put("condition", query);
			list=dealerServiceEntriesService.getDSEReportList(object);
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			object= null;
		}
		return list;
	}
	
	@Override
	public ValueObject getVehicleIncidentReport(ValueObject valueObject) {
		CustomUserDetails userDetails = Utility.getObject(null);
		int vTypeId = valueObject.getInt("vTypeId",0);
		int vId = valueObject.getInt("vId",0);
		int branchId = valueObject.getInt("vLocation",0);
		StringBuilder query = new StringBuilder();
		StringBuilder issueBQuery = new StringBuilder("  R.markForDelete = 0  ");
		StringBuilder woQuery= new StringBuilder(" where w.markForDelete=0 ");
		StringBuilder dSEQuery= new StringBuilder();
		ExecutorService ex = Executors.newFixedThreadPool(6);
		
		if(vId > 0) {
			query.append(" AND R.vid = "+vId+" ");
			issueBQuery.append(" AND VV.vid = "+vId+" ");
			woQuery.append(" AND v.vid = "+vId+" ");
			dSEQuery.append(" AND V.vid = "+vId+" ");
		}
		if(vTypeId > 0) {
			query.append(" AND R.vehicleTypeId = "+vTypeId+" ");
			issueBQuery.append(" AND VV.vehicleTypeId = "+vTypeId+" ");
			woQuery.append(" AND v.vehicleTypeId = "+vTypeId+" ");
			dSEQuery.append(" AND V.vehicleTypeId = "+vTypeId+" ");
		}
		if(branchId >0) {
			query.append(" AND R.branchId = "+branchId+" ");
			issueBQuery.append(" AND VV.branchId = "+branchId+" ");
			woQuery.append(" AND v.branchId = "+branchId+" ");
			dSEQuery.append(" AND V.branchId = "+branchId+" ");
		}
		StringBuilder accidentQuery =new StringBuilder(dSEQuery.toString()) ;
		StringBuilder inpectionQuery=new StringBuilder(dSEQuery.toString());
		StringBuilder  fuelQuery=new StringBuilder(dSEQuery.toString());
		try {
			valueObject.put("companyId",userDetails.getCompany_id());
			
		ex.execute(()->{
			try {
			StringBuilder innerJoin = new StringBuilder(" INNER JOIN Vehicle VV ON VV.vid = R.ISSUES_VID LEFT JOIN User AS U ON U.id = R.CREATEDBYID  ");;
			  
			issueBQuery.append(" AND R.ISSUES_REPORTED_DATE BETWEEN '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
			issueBQuery.append(" AND  R.ISSUE_TYPE_ID IN("+IssuesTypeConstant.ISSUE_TYPE_VEHICLE+","+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN+") AND  R.COMPANY_ID ="+userDetails.getCompany_id()+" ");
			List<IssuesDto> issueList=issueService.getIssuesActivityList(issueBQuery.toString(),innerJoin.toString());
			Map<Short,Map<Integer,Long>> issueHash= null;
			Map<Integer,Long> vehicleIssueCountHash =null;
			Map<Integer,Long> breakDownIssueCountHash =null;
			if(issueList != null) {
				issueHash =issueList.stream().collect(Collectors.groupingBy(IssuesDto::getISSUES_TYPE_ID,Collectors.groupingBy(IssuesDto::getISSUES_VID,Collectors.counting())));
				vehicleIssueCountHash=issueHash.get(IssuesTypeConstant.ISSUE_TYPE_VEHICLE);
				breakDownIssueCountHash=issueHash.get(IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN);
			}
			valueObject.put("vehicleIssueCountHash", vehicleIssueCountHash);
			valueObject.put("breakDownIssueCountHash", breakDownIssueCountHash);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		
		ex.execute(()->{
			try {
			woQuery.append(" AND w.start_date between '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
			woQuery.append(" AND w.companyId ="+userDetails.getCompany_id()+" ");
			List<WorkOrdersDto> workOrderList=workOrdersService.getUserWiseWOActivityList(woQuery.toString(), " left JOIN User AS U ON U.id = w.createdById ");
			Map<Integer,Long> woCountHash=null;
			if(workOrderList != null)
			woCountHash=workOrderList.stream().collect(Collectors.groupingBy(WorkOrdersDto::getVehicle_vid,Collectors.counting()));
			valueObject.put("woCountHash", woCountHash);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		ex.execute(()->{
			
			ValueObject dseValueObject = new ValueObject();
			try {
				dSEQuery.append(" AND DSE.invoiceDate between '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
				dseValueObject.put("condition", dSEQuery.toString());
				dseValueObject.put("companyId", userDetails.getCompany_id());
				List<DealerServiceEntriesDto> dseList=dealerServiceEntriesService.getDSEReportList(dseValueObject);
				Map<Integer,Long>dseCountHash= null;
				if(dseList != null)
				dseCountHash=dseList.stream().collect(Collectors.groupingBy(DealerServiceEntriesDto::getVid,Collectors.counting()));
				valueObject.put("dseCountHash", dseCountHash);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		ex.execute(()->{
			try {
				accidentQuery.append(" AND UE.accidentDateTime between '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
				accidentQuery.append(" AND UE.companyId ="+userDetails.getCompany_id()+" ");
				List<VehicleAccidentDetailsDto> accidentList=accidentDetailsService.getVehicleAccidentDetailsDtoList(accidentQuery.toString());	
				Map<Integer,Long>accCountHash=null;
				if(accidentList != null)		
				accCountHash=accidentList.parallelStream().collect(Collectors.groupingBy(VehicleAccidentDetailsDto::getVid,Collectors.counting()));
				valueObject.put("accCountHash", accCountHash);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	
		ex.execute(()->{
		try {
			inpectionQuery.append(" AND  ICP.companyId = "+userDetails.getCompany_id()+"  AND  ICP.completionDateTime between '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' AND ICP.totalPenalty > 0  ");
			List<VehicleInspectionCompletionDetailsDto>inspenctionPenaltyList= inspectionCompletionDetailsService.getInspectionComplitionDetails(inpectionQuery.toString());
			Map<Integer,Long> penaltyCountHash= null;
			if(inspenctionPenaltyList != null)
			penaltyCountHash = inspenctionPenaltyList.parallelStream().collect(Collectors.groupingBy(VehicleInspectionCompletionDetailsDto::getVid,Collectors.counting()));
		
			valueObject.put("penaltyCountHash", penaltyCountHash);
		} catch (Exception e) {
			e.printStackTrace(); 
		}	
		});
		
		ex.execute(()->{
			try {
			fuelQuery.append(" AND F.fuel_date between '"+valueObject.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+valueObject.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
			valueObject.put("queryStr",fuelQuery.toString());
			List<FuelDto> fuelList=fuelReportDao.getFuelConsumptionList(valueObject);
			Map<Integer,Double> milageFuelHash  =null;
			Map<Integer,Double> consumptionFuelHash=null;
			if(fuelList != null) {
				milageFuelHash       =fuelList.stream().collect(Collectors.groupingBy(FuelDto::getVid,Collectors.averagingDouble(FuelDto::getFuel_kml)));
				consumptionFuelHash  =fuelList.stream().collect(Collectors.groupingBy(FuelDto::getVid,Collectors.summingDouble(FuelDto::getFuel_liters)));
			}
			valueObject.put("milageFuelHash", milageFuelHash);
			valueObject.put("consumptionFuelHash", consumptionFuelHash);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		} catch (Exception e) {
			e.printStackTrace();
		}
		ex.shutdown();
		
		try {
			ex.awaitTermination(20, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			ex.shutdownNow();
		}
			return setVehicleWiseIncidentData(valueObject, query);
	
	}
	
	@SuppressWarnings("unchecked")
	public ValueObject setVehicleWiseIncidentData(ValueObject obejct,StringBuilder query) {
		ExecutorService ex=Executors.newFixedThreadPool(2);
		ValueObject finalObject = new ValueObject();
		try {
		Map<Integer, Long> penaltyCountHash 		= (Map<Integer, Long>) obejct.get("penaltyCountHash");
		Map<Integer,Long> accCountHash				= (Map<Integer, Long>) obejct.get("accCountHash");
		Map<Integer,Long> dseCountHash				= (Map<Integer, Long>) obejct.get("dseCountHash");
		Map<Integer,Long> woCountHash				= (Map<Integer, Long>) obejct.get("woCountHash");
		Map<Integer,Long> vehicleIssueCountHash 	= (Map<Integer, Long>) obejct.get("vehicleIssueCountHash");
		Map<Integer,Long> breakDownIssueCountHash 	= (Map<Integer, Long>) obejct.get("breakDownIssueCountHash");
		Map<Integer,Double> milageFuelHash 			= (Map<Integer, Double>) obejct.get("milageFuelHash");
		Map<Integer,Double> consumptionFuelHash 	= (Map<Integer, Double>) obejct.get("consumptionFuelHash");
		
			
		
		ex.execute(()->{
			try {
				query.append(" AND R.company_Id = "+obejct.getInt("companyId")+" ");
				List<VehicleDto>vehicle =	getBasicVehicleDetails(query.toString());
				vehicle.forEach(e -> {
					double dseAndWoCount =0;
					if (penaltyCountHash != null &&penaltyCountHash.get(e.getVid()) != null)
						e.setPenaltyCount("<a style=\"color:blue\" href=\"javascript:void(0)\" onclick=\"getVehicleWiseIncidentDetails(1,"+e.getVid()+",'"+e.getVehicle_registration()+"');\" >" + penaltyCountHash.get(e.getVid())+"</a>");
					else
						e.setPenaltyCount("0");
					if (accCountHash != null && accCountHash.get(e.getVid()) != null)
						e.setAccCount("<a style=\"color:blue\" href=\"javascript:void(0)\" onclick=\"getVehicleWiseIncidentDetails(2,"+e.getVid()+",'"+e.getVehicle_registration()+"');\" >" + accCountHash.get(e.getVid())+"</a>");
					else
						e.setAccCount("0");
					if (vehicleIssueCountHash != null && vehicleIssueCountHash.get(e.getVid()) != null)
						e.setVehicleIssueCount("<a style=\"color:blue\" href=\"javascript:void(0)\" onclick=\"getVehicleWiseIncidentDetails(4,"+e.getVid()+",'"+e.getVehicle_registration()+"');\" >" + vehicleIssueCountHash.get(e.getVid())+"</a>");
					else
						e.setVehicleIssueCount("0");
					if (breakDownIssueCountHash != null && breakDownIssueCountHash.get(e.getVid()) != null)
						e.setBreakDownIssueCount("<a style=\"color:blue\" href=\"javascript:void(0)\" onclick=\"getVehicleWiseIncidentDetails(5,"+e.getVid()+",'"+e.getVehicle_registration()+"');\" >" + breakDownIssueCountHash.get(e.getVid())+"</a>");
					else
						e.setBreakDownIssueCount("0");
					if (milageFuelHash != null && milageFuelHash.get(e.getVid()) != null)
						e.setMilageFuel("<a style=\"color:blue\" href=\"javascript:void(0)\" onclick=\"getVehicleWiseIncidentDetails(6,"+e.getVid()+",'"+e.getVehicle_registration()+"');\" >" + Utility.round(milageFuelHash.get(e.getVid()),2)+"</a>");
					else
						e.setMilageFuel("0");
					if (consumptionFuelHash != null && consumptionFuelHash.get(e.getVid()) != null)
						e.setConsumptionFuel("<a style=\"color:blue\" href=\"javascript:void(0)\" onclick=\"getVehicleWiseIncidentDetails(7,"+e.getVid()+",'"+e.getVehicle_registration()+"');\" >" + Utility.round(consumptionFuelHash.get(e.getVid()),2)+"</a>");
					else
						e.setConsumptionFuel("0");
					if (dseCountHash != null && dseCountHash.get(e.getVid()) != null)
						dseAndWoCount+=dseCountHash.get(e.getVid());
					
					if (woCountHash != null && woCountHash.get(e.getVid()) != null)
						dseAndWoCount+=woCountHash.get(e.getVid());
					
					if(dseAndWoCount > 0)
						e.setWoCount("<a style=\"color:blue\" href=\"javascript:void(0)\" onclick=\"getVehicleWiseIncidentDetails(3,"+e.getVid()+",'"+e.getVehicle_registration()+"');\" >" + dseAndWoCount+"</a>");
					else
						e.setWoCount("0");
				});
				finalObject.put("list", vehicle);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		});
		
		ex.execute(()->{
			try {
				ValueObject tableConfig				= new ValueObject();
				tableConfig.put("companyId",obejct.getInt("companyId",0));
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VEHICLE_INCIDENT_REPORT);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				finalObject.put("tableConfig", tableConfig.getHtData());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		ex.shutdown();
		
		ex.awaitTermination(17,TimeUnit.SECONDS);
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally {
			ex.shutdownNow();
		}
		
		return finalObject;
	}
	public List<VehicleDto> getBasicVehicleDetails(String query) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT R.vid, R.vehicle_registration, R.vehicle_photoid, R.vStatusId, R.vehicle_Odometer,  VT.vtype, "
							+ "R.Vehicle_Location, VG.vGroup, R.vehicleGroupId, R.driverMonthlySalary,B.branch_name"
							+ " from Vehicle AS R "
							+ " LEFT JOIN VehicleType VT ON VT.tid = R.vehicleTypeId "
							+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = R.vehicleGroupId " 
							+ " LEFT JOIN Branch AS B ON B.branch_id = R.branchId " 
							+ " where R.markForDelete = 0 "+query+" ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			List<VehicleDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] vehicle : results) {
					VehicleDto select = new VehicleDto();

					select.setVid((Integer) vehicle[0]);
					select.setVehicle_registration((String) vehicle[1]);
					select.setVehicle_photoid((Integer) vehicle[2]);
					select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus((short) vehicle[3]));
					select.setVehicle_Odometer((Integer) vehicle[4]);
					select.setVehicle_Type((String) vehicle[5]);
					select.setVehicle_Group((String) vehicle[7]);
					select.setVehicleGroupId((long) vehicle[8]);
					if(vehicle[9] != null)
					select.setDriverMonthlySalary((Double) vehicle[9]);
					select.setVehicle_Location((String) vehicle[10]);
					
					list.add(select);
				
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public ValueObject getVehicleWiseIncidentDetails(ValueObject object) {
		ValueObject outObject = new ValueObject();
		int inspectionPenalty = 1;
		int accident = 2;
		int woDse = 3;
		int vehicleIssue = 4;
		int breakDown = 5;
		int fuelMilage = 6;
		int fuelConsumption = 7;
		try {
		int incidentType = object.getInt("incidentType",0);
		int vId = object.getInt("vid",0);
		StringBuilder query = new StringBuilder();
		String queryV = " AND V.vid = "+vId+" ";
		String queryv = " AND v.vid = "+vId+" ";
		String queryVV = " AND VV.vid = "+vId+" ";
		String list ="list";
		if(incidentType == inspectionPenalty) {
			query.append(queryV+" AND  ICP.companyId = "+object.getInt("companyId",0)+"  AND  ICP.completionDateTime between '"+object.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' AND ICP.totalPenalty > 0  ");
			List<VehicleInspectionCompletionDetailsDto>inspenctionPenaltyList= inspectionCompletionDetailsService.getInspectionComplitionDetails(query.toString());
			outObject.put(list, inspenctionPenaltyList);
		}else if(incidentType == accident) {
			query.append(queryV+" AND UE.accidentDateTime between '"+object.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
			query.append(" AND UE.companyId ="+object.getInt("companyId",0)+" ");
			List<VehicleAccidentDetailsDto> accidentList=accidentDetailsService.getVehicleAccidentDetailsDtoList(query.toString());
			outObject.put(list, accidentList);
		}else if (incidentType == woDse) {
			query.append(" AND DSE.invoiceDate between '"+object.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
			object.put("condition", query.toString());
			object.put("companyId", object.getInt("companyId",0));
			List<DealerServiceEntriesDto> dseList=dealerServiceEntriesService.getDSEReportList(object);
			
			query= new StringBuilder();
			query.append(" where w.markForDelete=0 "+queryv+" AND w.start_date between '"+object.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
			query.append(queryv+" AND w.companyId ="+object.getInt("companyId",0)+" ");
			List<WorkOrdersDto> workOrderList=workOrdersService.getUserWiseWOActivityList(query.toString(), " left JOIN User AS U ON U.id = w.createdById ");
			outObject.put(list, dseList);
			outObject.put("workOrderList", workOrderList);
		}else if (incidentType == breakDown || incidentType ==vehicleIssue) {
			StringBuilder innerJoin = new StringBuilder(" INNER JOIN Vehicle VV ON VV.vid = R.ISSUES_VID LEFT JOIN User AS U ON U.id = R.CREATEDBYID  ");;
			query.append(" R.markForDelete = 0 "+queryVV+" AND  R.COMPANY_ID ="+object.getInt("companyId",0)+"   AND R.ISSUES_REPORTED_DATE BETWEEN '"+object.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
			if(incidentType == breakDown)query.append(" AND  R.ISSUE_TYPE_ID ="+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN+") ");else query.append(" AND  R.ISSUE_TYPE_ID ="+IssuesTypeConstant.ISSUE_TYPE_VEHICLE+" ");
			List<IssuesDto> issueList=issueService.getIssuesActivityList(query.toString(),innerJoin.toString());
			outObject.put(list, issueList);
		}else if (incidentType  == fuelConsumption || incidentType==fuelMilage) {
			query.append(queryV+" AND F.fuel_date between '"+object.getString("fromDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"' ");
			object.put("queryStr",query.toString());
			List<FuelDto> fuelList=fuelReportDao.getFuelConsumptionList(object);
			outObject.put(list, fuelList);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return outObject;
	}
	
	@Override
	public List<SubCompanyDto> getSubCompany(Integer company_Id) throws Exception {
		try {
			TypedQuery<Object[]> queryt =	null;
			queryt = entityManager.createQuery("SELECT  subCompanyId, subCompanyName FROM SubCompany "
					+ " WHERE  companyId = " + company_Id +  " AND  markForDelete = 0 " , Object[].class);
				
			List<Object[]> results = queryt.getResultList();
			List<SubCompanyDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<SubCompanyDto>();
				SubCompanyDto list = null;
				for (Object[] result : results) {
					list = new SubCompanyDto();
					list.setSubCompanyId((Long) result[0]);
					list.setSubCompanyName((String) result[1]);
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	@Override
	public void updateServiceReminder(ServiceProgramSchedulesDto schedulesDto, Vehicle vehicle , ServiceReminder sr) {
		
		//ServiceReminder						serviceReminder			= null;
		Integer meter_serviceodometer  = vehicle.getVehicle_Odometer() + schedulesDto.getMeterInterval();
		Integer meter_servicethreshold_odometer = meter_serviceodometer - schedulesDto.getMeterThreshold();
		UserProfile userProfile                                     = null;
		java.sql.Date Time_serviceAdvacedate   =         null;
		java.sql.Date Time_servicedate				= null;
		
		if (schedulesDto.getTimeInterval() != null) {
			
			// get time interval calculation to service to get
			// service time interval days
			Integer time_Intervalperiod = 0;
			if ( schedulesDto.getTimeIntervalType() > 0) {
				time_Intervalperiod = schedulesDto.getTimeInterval();
			}
			Integer timeserviceDaysPeriod = 0;
			switch (schedulesDto.getTimeIntervalType()) {
			case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
				timeserviceDaysPeriod = time_Intervalperiod;
				break;
			case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
				timeserviceDaysPeriod = time_Intervalperiod * 7;
				break;
			case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
				timeserviceDaysPeriod = time_Intervalperiod * 30;
				break;
			case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
				timeserviceDaysPeriod = time_Intervalperiod * 365;
				break;
				
			default:
				timeserviceDaysPeriod = time_Intervalperiod;
				break;
			}
			
			final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, timeserviceDaysPeriod);

			java.util.Date servicedate = null ;
			try {
				servicedate = format.parse(format.format(calendar.getTime()));
				Time_servicedate = new java.sql.Date(servicedate.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			if (schedulesDto.getTimeThreshold() != null) {
				
				Integer Time_threshold = 0;
				switch (schedulesDto.getTimeThresholdType()) {
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
					Time_threshold = schedulesDto.getTimeThreshold();
					break;
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
					Time_threshold = schedulesDto.getTimeThreshold() * 7;
					break;
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
					Time_threshold = schedulesDto.getTimeThreshold() * 30;
					break;
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
					Time_threshold = schedulesDto.getTimeThreshold() * 365;
					break;
					
				default:
					Time_threshold = schedulesDto.getTimeThreshold();
					break;
				}
				final Calendar calendar_advanceThreshold = Calendar.getInstance();

				calendar_advanceThreshold.setTime(Time_servicedate);
				calendar_advanceThreshold.add(Calendar.DAY_OF_YEAR, -Time_threshold);
				
				
				// fuel date converted String to date Format
				java.util.Date serviceThreshold = null;
				try {
					serviceThreshold = format
							.parse(format.format(calendar_advanceThreshold.getTime()));
					Time_serviceAdvacedate = new java.sql.Date(serviceThreshold.getTime());
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
				
				if(schedulesDto.getService_subScribedUserId()!=null || schedulesDto.getService_subScribedUserId()=="") {
					userProfile  =userRepository.getUserProfileByUser_id( Long.parseLong(schedulesDto.getService_subScribedUserId()) , vehicle.getCompany_Id());
	
				}
			}
		}
		
		
		serviceReminderRepository.updateServiceReminder(sr.getService_id(),schedulesDto.getMeterInterval(),
				schedulesDto.getMeterThreshold(),meter_serviceodometer,meter_servicethreshold_odometer,
				schedulesDto.getMeterInterval(),new Date(),Time_servicedate, schedulesDto.getTimeThreshold(),
				Time_serviceAdvacedate, userProfile.getFirstName());
	
	}

	@Transactional
	public Vehicle GetVehicleByRegNo(String vehicle_register) throws Exception {
		return vehicleRepository.getVehicleByRegNo(vehicle_register);
	}

	@Override
	@Transactional
	public ValueObject saveDriverMonthlyBhatta(ValueObject	valueObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		Timestamp 						updatedDateTime 				= null;
		int								vehicleId						= 0;
		ValueObject						valueOutObject					= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			updatedDateTime 	= new java.sql.Timestamp(new Date().getTime());
			valueOutObject		= new ValueObject();
			vehicleId			= valueObject.getInt("vehicleId");
			
			entityManager.createQuery(" UPDATE Vehicle SET lastModifiedById = "+ userDetails.getId()
						+ " , lastupdated = '"+ updatedDateTime +"' "
						+ ", driverMonthlyBhatta = "+valueObject.getDouble("bhattaForDriver")
						+ " WHERE vid = " + vehicleId +" AND company_Id = "+userDetails.getCompany_id()+ " AND markForDelete = 0 " 
					)
				.executeUpdate();
			valueOutObject.put("SalarySaved", true);
			
			return valueOutObject;
			
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails						= null;
			updatedDateTime 				= null;
		}
	}
	
}
