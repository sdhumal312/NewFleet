package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.DateFormat;
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
 *
 *
 */
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.fleetopgroup.constant.ApprovalType;
import org.fleetopgroup.constant.DriverLedgerTypeConstant;
import org.fleetopgroup.constant.FuelConfigurationConstants;
import org.fleetopgroup.constant.FuelInvoiceConstant;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.GPSConstant;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.ServiceEntriesType;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.constant.TripRouteFixedType;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleFuelType;
import org.fleetopgroup.constant.VehicleGPSConfiguratinConstant;
import org.fleetopgroup.constant.VehicleGPSVendorConstant;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.constant.VendorTypeConfigurationConstants;
import org.fleetopgroup.persistence.bl.FuelBL;
import org.fleetopgroup.persistence.bl.FuelInvoiceBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.bl.ReportBL;
import org.fleetopgroup.persistence.bl.TripSheetMobileBL;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.dao.FuelDocumentRepository;
import org.fleetopgroup.persistence.dao.FuelInvoiceHistoryRepository;
import org.fleetopgroup.persistence.dao.FuelInvoiceRepository;
import org.fleetopgroup.persistence.dao.FuelRepository;
import org.fleetopgroup.persistence.dao.IntangleFuelEntryDetailsRepository;
import org.fleetopgroup.persistence.dao.TripSheetExpenseRepository;
import org.fleetopgroup.persistence.dao.UserProfileRepository;
import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.FuelDocument;
import org.fleetopgroup.persistence.model.FuelInvoice;
import org.fleetopgroup.persistence.model.FuelInvoiceHistory;
import org.fleetopgroup.persistence.model.FuelSequenceCounter;
import org.fleetopgroup.persistence.model.FuelStockDetails;
import org.fleetopgroup.persistence.model.IntangleFuelEntryDetails;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.TripSheetHistory;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.VendorSequenceCounter;
import org.fleetopgroup.persistence.model.VendorType;
import org.fleetopgroup.persistence.report.dao.IFuelReportDao;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverLedgerService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IFuelInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IFuelSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.ITyreUsageHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVendorSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.persistence.serviceImpl.IVendorTypeService;
import org.fleetopgroup.web.util.ByteConvertor;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.antkorwin.xsync.XMutexFactoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@Service("FuelService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FuelService implements IFuelService {

	private final Logger LOGGER	= LoggerFactory.getLogger(getClass());

	@PersistenceContext
	EntityManager entityManager;

	@Autowired	private FuelRepository 										fuelDao;
	@Autowired	private	IFuelSequenceService 								fuelSequenceService;
	@Autowired private	IVehicleService 									vehicleService;
	@Autowired private	IVehicleOdometerHistoryService 						vehicleOdometerHistoryService;
	@Autowired private  ICompanyConfigurationService 						companyConfigurationService;
	@Autowired private  IUserProfileService									userProfileService;
	@Autowired private  MongoTemplate										mongoTemplate;
	@Autowired private  ISequenceCounterService								sequenceCounterService;
	@Autowired private  FuelDocumentRepository								fuelDocumentRepository;
	@Autowired private  IFuelReportDao										FuelReportDaoImpl;
	@Autowired private  VehicleProfitAndLossTxnCheckerService				vehicleProfitAndLossTxnCheckerService;
	@Autowired private  IServiceReminderService 							ServiceReminderService;
	@Autowired private  IVehicleProfitAndLossService						vehicleProfitAndLossService;
	@Autowired private  IVendorService 										vendorService;
	@Autowired private  IVendorTypeService 									VendorTypeService;
	@Autowired private  IVendorSequenceService 								vendorSequenceService;
	@Autowired private	ITripSheetService									tripSheetService;
	@Autowired private	UserProfileRepository								UserProfileRepository;
	@Autowired private	IVehicleAgentTxnCheckerService						vehicleAgentTxnCheckerService;
	@Autowired private	IVehicleAgentIncomeExpenseDetailsService			vehicleAgentIncomeExpenseDetailsService;
	@Autowired private	IVehicleGPSDetailsService							vehicleGPSDetailsService;
	@Autowired private	IPendingVendorPaymentService						pendingVendorPaymentService;
	@Autowired private	IFuelInvoiceService									fuelInvoiceService;
	@Autowired private	FuelInvoiceHistoryRepository						fuelInvoiceHistoryRepository;
	@Autowired private	IntangleFuelEntryDetailsRepository					intangleFuelEntryDetailsRepository;
	@Autowired private	FuelInvoiceRepository								fuelInvoiceRepository;
	@Autowired private	ITyreUsageHistoryService							tyreUsageHistoryService;
	@Autowired private 	ICashPaymentService									cashPaymentService;
	@Autowired private 	IBankPaymentService									bankPaymentService;
	@Autowired private IDriverLedgerService									driverLedgerService;
	@Autowired private ITripSheetHistoryService 							tripSheetHistoryService; 
	@Autowired private TripSheetExpenseRepository							tripSheetExpenseRepository;
	@Autowired private  IVendorService										ivendorService;
	@Autowired private IFuelService 										fuelService;
	@Autowired private  IDriverService										driverService;
	
	
	TripSheetMobileBL	 tripSheetMobileBL			= new TripSheetMobileBL();
	
	XMutexFactoryImpl<Integer> xMutexFactory = new XMutexFactoryImpl<Integer>();
	
	
	@Autowired private HttpServletRequest		request;


	VehicleOdometerHistoryBL 			VOHBL 		 = new VehicleOdometerHistoryBL();
	VehicleProfitAndLossTxnCheckerBL	txnCheckerBL = new VehicleProfitAndLossTxnCheckerBL();
	VendorBL 							VenBL 		 = new VendorBL();
	FuelBL 								FuBL 		 = new FuelBL();
	SimpleDateFormat dateFormatSQL 	 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat 	 = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat formatSQL 	 	 = new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
	SimpleDateFormat tallyFormat	 = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat dateFormat2 = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY_HH_MM_AA);
	DecimalFormat toFixedTwo= new DecimalFormat("#.##");
	FuelInvoiceBL 			fuelInvoiceBL 		 = new FuelInvoiceBL();
	
	VehicleAgentTxnCheckerBL	agentTxnCheckerBL			= new VehicleAgentTxnCheckerBL();
	ReportBL RBL = new ReportBL();
	

	private static final int PAGE_SIZE = 10;

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addFuel(Fuel fuel) {

		fuelDao.save(fuel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateFuel(Fuel fuel) {

		fuelDao.save(fuel);
	}

	@Transactional
	public Fuel getFuelList(Integer fuel_id) {

		return fuelDao.getFuelList(fuel_id);
	}
	/**
	 * This Page get Vehicle table to get pagination values
	 * 
	 * @throws Exception
	 */
	@Transactional
	public Page<Fuel> getDeployment_Page_Fuel(Integer pageNumber, CustomUserDetails userDetails) throws Exception {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "vid");
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return fuelDao.getDeployment_Page_Fuel(userDetails.getCompany_id(), request);
		}
		return fuelDao.getDeployment_Page_Fuel(userDetails.getCompany_id(), userDetails.getId(), request);
	}

	/**
	 * This List get Fuel table to get pagination last 10 entries values
	 * 
	 * @throws Exception
	 */
	@Transactional
	public List<Fuel> listFuel(Integer pageNumber, CustomUserDetails userDetails) throws Exception {

		TypedQuery<Fuel> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery("SELECT F FROM Fuel as F " + " where F.companyId = "
					+ userDetails.getCompany_id() + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc", Fuel.class);
		} else {
			query = entityManager.createQuery("SELECT F FROM Fuel as F " + " INNER JOIN Vehicle V ON V.vid = F.vid"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " where F.companyId = " + userDetails.getCompany_id()
					+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc", Fuel.class);
		}
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		return query.getResultList();
	}

	@Override
	public List<FuelDto> listFuelEntries(Integer pageNumber, CustomUserDetails userDetails) throws Exception {
		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(
				userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		TypedQuery<Object[]> query = null;
		if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
			if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.tripSheetNumber, F.fuel_vendor_paymentdate,D.driver_Lastname,D.driver_fathername " 
								+ " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			} else if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPCOLLNUMBER, F.fuel_vendor_paymentdate,D.driver_Lastname,D.driver_fathername " 
								+ " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			} else if ((int) configuration.get(
					TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPDAILYNUMBER, F.fuel_vendor_paymentdate,D.driver_Lastname,D.driver_fathername " 
								+ " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			}
		} else {

			if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.tripSheetNumber, F.fuel_vendor_paymentdate,D.driver_Lastname,D.driver_fathername " 
								+ " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			} else if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPCOLLNUMBER, F.fuel_vendor_paymentdate,D.driver_Lastname,D.driver_fathername " 
								+ " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			} else if ((int) configuration.get(
					TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPDAILYNUMBER, F.fuel_vendor_paymentdate,D.driver_Lastname,D.driver_fathername " 
								+ " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			}

		}
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				fuel.setFuel_TripsheetNumber((Long) result[33]);
				if(result[34]!= null) {
					fuel.setFuel_vendor_paymentdate((Timestamp) result[34]);
				}
				if(result[35]!= null &&!((String)result[35]).trim().equals(""))
					fuel.setDriver_name(fuel.getDriver_name()+" "+ result[35]);	
				if(result[36]!= null &&!((String)result[36]).trim().equals(""))
					fuel.setDriver_name(fuel.getDriver_name()+"-"+ result[36]);	

				Dtos.add(fuel);
			}
		}
		return Dtos;
	}

	@Override
	public List<FuelDto> listFuelEntriesBySP(Integer pageNumber, CustomUserDetails userDetails) throws Exception {
		StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getFuelListByCompanyId"); 

		storedProcedure.registerStoredProcedureParameter("companyId", Integer.class, ParameterMode.IN);
		//storedProcedure.registerStoredProcedureParameter("userId", Long.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("startLimit", Integer.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("endLimit", Integer.class, ParameterMode.IN);

		storedProcedure.setParameter("companyId", userDetails.getCompany_id());
		//storedProcedure.setParameter("userId", userDetails.getId());
		storedProcedure.setParameter("startLimit", (pageNumber - 1) * PAGE_SIZE);
		storedProcedure.setParameter("endLimit", PAGE_SIZE);

		storedProcedure.setFirstResult((pageNumber - 1) * PAGE_SIZE);

		storedProcedure.setMaxResults(PAGE_SIZE);

		// storedProcedure.execute();

		@SuppressWarnings("unchecked")
		List<Object[]> results = storedProcedure.getResultList();
		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();
				fuel.setFuel_id(Long.parseLong(result[0]+""));
				fuel.setFuel_Number(Long.parseLong(result[1]+""));
				if(result[2] != null)
					fuel.setFuel_TripsheetID(Long.parseLong(result[2]+""));
				fuel.setVid(Integer.parseInt(result[3]+""));
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id(Long.parseLong(result[32]+""));
				if(result[33]!= null)
					fuel.setFuel_TripsheetNumber((Long) result[33]);


				Dtos.add(fuel);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<Fuel> listUPYesterdayFuel(Date yesterday) throws Exception {

		return fuelDao.listUPYesterdayFuel(yesterday);
	}

	/** The Value of Fuel Entries Get Fuel ID To details */
	@Transactional
	public Fuel getFuel(Long fuel_id, Integer companyId) {

		return fuelDao.getFuel(fuel_id, companyId);
	}

	@Override
	public FuelDto getFuelDetails(Long fuel_id, Integer companyId) throws Exception {
		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		TypedQuery<Object[]> query = null;

		if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
			query = entityManager.createQuery(
					"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id, TS.tripSheetNumber, F.fuel_vendor_paymentdate, U.email, U2.email,"
							+ " F.created, F.lastupdated, F.fuel_vendor_approvalID, F.createdById, VN.autoVendor, F.gpsOdometer,"
							+ " F.secDriverID, F.cleanerID, F.routeID, D2.driver_firstname, D3.driver_firstname, TR.routeName, F.fuelDateTime,"
							+ " F.tallyCompanyId, TC.companyName, F.gpsUsageKM, V.vehicle_ExpectedOdameter, VN.ownPetrolPump, D.driver_fathername, D.driver_Lastname, D2.driver_fathername, D2.driver_Lastname, F.fuelInvoiceId, " 
							+ " B.branch_name, F.paidByBranchId, TS.tripStutesId, TS.tripClosingKM "
							+ " FROM Fuel as F "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
							+ " LEFT JOIN Driver AS D2 ON D2.driver_id = F.secDriverID "
							+ " LEFT JOIN Driver AS D3 ON D3.driver_id = F.cleanerID "
							+ " LEFT JOIN TripRoute AS TR ON TR.routeID = F.routeID "
							+ " LEFT JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
							+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = F.tallyCompanyId"
							+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
							+ " LEFT JOIN User U ON U.id = F.createdById"
							+ " LEFT JOIN Branch B ON B.branch_id = F.paidByBranchId"
							+ " LEFT JOIN User U2 ON U2.id = F.lastModifiedById" + " where F.fuel_id = " + fuel_id
							+ " AND F.companyId = " + companyId + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
							Object[].class);

		} else if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
			query = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id, TS.TRIPCOLLNUMBER, F.fuel_vendor_paymentdate, U.email, U2.email,"
							+ " F.created, F.lastupdated, F.fuel_vendor_approvalID, F.createdById, VN.autoVendor, F.gpsOdometer,"
							+ " F.secDriverID,F.cleanerID,F.routeID, D2.driver_firstname, D3.driver_firstname, TR.routeName, F.fuelDateTime  " 
							+ " F.tallyCompanyId, TC.companyName, F.gpsUsageKM, V.vehicle_ExpectedOdameter , VN.ownPetrolPump, D.driver_fathername, D.driver_Lastname, D2.driver_fathername, D2.driver_Lastname, F.fuelInvoiceId, " 
							+ " B.branch_name, F.paidByBranchId, TS.tripStutesId, TS.tripClosingKM "
							+ " FROM Fuel as F "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
							+ " LEFT JOIN Driver AS D2 ON D2.driver_id = F.secDriverID "
							+ " LEFT JOIN Driver AS D3 ON D3.driver_id = F.cleanerID "
							+ " LEFT JOIN TripRoute AS TR ON TR.routeID = F.routeID "
							+ " LEFT JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
							+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = F.tallyCompanyId"
							+ " LEFT JOIN User U ON U.id = F.createdById"
							+ " LEFT JOIN User U2 ON U2.id = F.lastModifiedById"
							+ " LEFT JOIN Branch B ON B.branch_id = F.paidByBranchId"
							+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
							+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
							+ " where F.fuel_id = " + fuel_id + " AND F.companyId = " + companyId
							+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc", Object[].class);

		} else if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
			query = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id, TS.TRIPDAILYNUMBER, F.fuel_vendor_paymentdate, U.email, U2.email,"
							+ " F.created, F.lastupdated, F.fuel_vendor_approvalID, F.createdById, VN.autoVendor, F.gpsOdometer,"
							+ " F.secDriverID,F.cleanerID,F.routeID, D2.driver_firstname, D3.driver_firstname, TR.routeName, F.fuelDateTime " 
							+ " F.tallyCompanyId, TC.companyName, F.gpsUsageKM, V.vehicle_ExpectedOdameter, VN.ownPetrolPump , D.driver_fathername, D.driver_Lastname, D2.driver_fathername, D2.driver_Lastname, F.fuelInvoiceId, " 
							+ " B.branch_name, F.paidByBranchId, TS.tripStutesId, TS.tripClosingKM "
							+ " FROM Fuel as F "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
							+ " LEFT JOIN Driver AS D2 ON D2.driver_id = F.secDriverID "
							+ " LEFT JOIN Driver AS D3 ON D3.driver_id = F.cleanerID "
							+ " LEFT JOIN TripRoute AS TR ON TR.routeID = F.routeID "
							+ " LEFT JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
							+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = F.tallyCompanyId"
							+ " LEFT JOIN User U ON  U.id = F.createdById"
							+ " LEFT JOIN User U2 ON U2.id = F.lastModifiedById"
							+ " LEFT JOIN Branch B ON B.branch_id = F.paidByBranchId"
							+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
							+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
							+ " where F.fuel_id = " + fuel_id + " AND F.companyId = " + companyId
							+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc", Object[].class);

		}
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();
				// fuel_vendor_paymentdate
				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setVehicle_OwnershipId((short) result[5]);
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuelTypeId((short) result[10]);
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setPaymentTypeId((short) result[20]);
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_vendor_paymodeId((short) result[30]);
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				fuel.setFuel_TripsheetNumber((Long) result[33]);
				fuel.setFuel_vendor_paymentdate((Date) result[34]);
				if(fuel.getFuel_vendor_paymentdate() != null)
				fuel.setFuelVendorPaymentDate(dateFormatTime.format(fuel.getFuel_vendor_paymentdate()));
				fuel.setCreatedBy((String) result[35]);
				fuel.setLastModifiedBy((String) result[36]);
				fuel.setCreatedOn((Date) result[37]);
				fuel.setLastupdatedOn((Date) result[38]);
				fuel.setFuel_vendor_approvalID((Long) result[39]);
				fuel.setCreatedById((Long) result[40]);
				if(result[41] != null)
					fuel.setAutoVendor((boolean) result[41]);
				if(result[42] != null)
					fuel.setGpsOdometer((Double) result[42]);
				fuel.setSecDriverID((Integer) result[43]);
				fuel.setCleanerID((Integer) result[44]);
				fuel.setRouteID((Integer) result[45]);
				if(result[46] != null)
					fuel.setFuelSecDriverName((String)result[46]);
				if(result[47] != null)
					fuel.setFuelCleanerName((String)result[47]);
				if(result[48] != null)
					fuel.setFuelRouteName((String)result[48]);
				if(result[49] != null)
					fuel.setFuelDateTime((Date) result[49]);
				if(result[50] != null)
					fuel.setTallyCompanyId((Long) result[50]);
				if(result[51] != null)
					fuel.setTallyCompanyName((String)result[51]);
				if(result[52] != null)
					fuel.setGpsUsageKM((Double) result[52]);
				if(result[53] != null) {
					fuel.setVehicle_ExpectedOdameter((Integer) result[53]);
				}
  				fuel.setOwnPetrolPump((short) result[54]);
				if(result[55] != null) {
					fuel.setFirstDriverFatherName((String) result[55]);
					fuel.setFirstDriverLastName((String) result[56]);
				}
				//
				if(result[57] != null) {
					fuel.setSecDriverFatherName((String) result[57]);
					fuel.setSecDriverLastName((String) result[58]);
				}
				
				if(result[59] != null) {
					fuel.setFuelInvoiceId((Long)result[59]);
				}
				if(result[60] != null) {
					fuel.setPaidByBranchStr((String) result[60]);
				}else {
					fuel.setPaidByBranchStr("");
				}
				fuel.setPaidByBranchId((Long) result[61]);
				
				if(result[62]!=null)
					fuel.setTripStutesId((short) result[62]);
				if(result[63]!=null)
					fuel.setTripClosingKM((Integer) result[63]);
				
				fuel.setModuleIdentifire(ModuleConstant.FUEL_ENTRY);
				Dtos.add(fuel);
			}
			return Dtos.get(0);
		}
		return null;
	}

	@Transactional
	public void deleteFuel(Long fuel,Timestamp toDate, long userId, Integer companyId) {

		fuelDao.deleteFuel(fuel,toDate,userId, companyId);
	}

	@Transactional
	public List<Fuel> listOfGetvehicelpartialFuel(Integer fuel_vehicleid, Integer fuel_tank) {

		return fuelDao.listOfGetvehicelpartialFuel(fuel_vehicleid, fuel_tank);
	}

	@Transactional
	public void updatepartialFuel(Integer fuel_vehicleid, Integer fuel_tank, Integer companyId) {
		fuelDao.updatepartialFuel(fuel_vehicleid, fuel_tank, companyId);
	}

	@Transactional
	public List<FuelDto> listFuelReport(String query,ValueObject object) throws Exception {
		 CustomUserDetails userDetails = null;
		 userDetails = Utility.getObject(object);
		
		TypedQuery<Object[]> queryFuel = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

			queryFuel = entityManager.createQuery(
					"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id , D.driver_Lastname,D.driver_fathername" + " FROM Fuel as F "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
							+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id " + " where F.markForDelete = 0  "
							+ query + " AND F.companyId = " + userDetails.getCompany_id() + " ORDER BY fuel_date desc",
							Object[].class);
		} else {
			queryFuel = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id, D.driver_Lastname,D.driver_fathername " + " FROM Fuel as F "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
							+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "
							+ userDetails.getId() + "" + " where F.markForDelete = 0  " + query + " AND F.companyId = "
							+ userDetails.getCompany_id() + " ORDER BY fuel_date desc", Object[].class);

		}

		List<Object[]> results = queryFuel.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				if(result[33]!= null &&!((String)result[33]).trim().equals(""))
					fuel.setDriver_name(fuel.getDriver_name()+" "+ result[33]);	
				if(result[34]!= null &&!((String)result[34]).trim().equals(""))
					fuel.setDriver_name(fuel.getDriver_name()+"-"+ result[34]);	
				Dtos.add(fuel);
			}
		}
		return Dtos;

	}

	@Transactional
	public List<Double> ReportFuelTotalCount(String query) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<Double> queryFuel = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryFuel = entityManager.createQuery("select sum(F.fuel_amount) from Fuel F "
					+ " where F.markForDelete = 0 " + query + " AND F.companyId = " + userDetails.getCompany_id() + " ",
					Double.class);
		} else {
			queryFuel = entityManager.createQuery("select sum(F.fuel_amount) from Fuel F "
					+ " INNER JOIN Vehicle V ON V.vid = F.vid"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " where F.markForDelete = 0 " + query + " AND F.companyId = "
					+ userDetails.getCompany_id() + " ", Double.class);
		}
		return queryFuel.getResultList();
	}

	@Transactional
	public List<Fuel> listVehicleFuelReport(Integer vid) {

		TypedQuery<Fuel> query = entityManager.createQuery(
				"from Fuel where vid=" + vid + " AND markForDelete = 0 ORDER BY fuel_date DESC , fuel_meter DESC",
				Fuel.class);
		query.setFirstResult(0);
		query.setMaxResults(100);
		return query.getResultList();
	}

	/** This Page get Vehicle table to get pagination values */
	@Transactional
	public Page<Fuel> getlistVehicleFuelReport_Page_Fuel(Integer vid, Integer pageNumber, Integer companyId) {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return fuelDao.findByVid(vid, companyId, pageable);
	}

	/**
	 * This List get Fuel table to get pagination last 10 entries values
	 * 
	 * @throws Exception
	 */
	@Transactional
	public List<FuelDto> listVehicleFuelReport(Integer vid, Integer pageNumber, Integer companyId) throws Exception {

		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		TypedQuery<Object[]> query = null;

		if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
			query = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id, TS.tripSheetNumber, F.fuel_vendor_paymentdate,"
							+ " F.created, F.lastupdated, F.fuel_vendor_approvalID, F.createdById" + " FROM Fuel as F "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
							+ " LEFT JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
							+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
							+ " where F.vid=" + vid + " AND F.companyId = " + companyId
							+ " AND F.markForDelete = 0 ORDER BY F.fuel_date DESC , F.fuel_id DESC", Object[].class);

		} else if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
			query = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id, TS.TRIPCOLLNUMBER, F.fuel_vendor_paymentdate,"
							+ " F.created, F.lastupdated, F.fuel_vendor_approvalID, F.createdById" + " FROM Fuel as F "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
							+ " LEFT JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
							+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
							+ " where F.vid=" + vid + " AND F.companyId = " + companyId
							+ " AND F.markForDelete = 0 ORDER BY F.fuel_date DESC , F.fuel_id DESC", Object[].class);

		} else if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
			query = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id, TS.TRIPDAILYNUMBER, F.fuel_vendor_paymentdate,"
							+ " F.created, F.lastupdated, F.fuel_vendor_approvalID, F.createdById" + " FROM Fuel as F "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
							+ " LEFT JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
							+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
							+ " where F.vid=" + vid + " AND F.companyId = " + companyId
							+ " AND F.markForDelete = 0 ORDER BY F.fuel_date DESC , F.fuel_id DESC", Object[].class);

		}
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();
				// fuel_vendor_paymentdate
				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setVehicle_OwnershipId((short) result[5]);
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuelTypeId((short) result[10]);
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setPaymentTypeId((short) result[20]);
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_vendor_paymodeId((short) result[30]);
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				fuel.setFuel_TripsheetNumber((Long) result[33]);
				fuel.setFuel_vendor_paymentdate((Date) result[34]);
				fuel.setCreatedOn((Date) result[35]);
				fuel.setLastupdatedOn((Date) result[36]);
				fuel.setFuel_vendor_approvalID((Long) result[37]);
				fuel.setCreatedById((Long) result[38]);
				Dtos.add(fuel);
			}
		}
		return Dtos;

	}

	@Transactional
	public Long countFuel(Integer companyId) throws Exception {

		return fuelDao.countFuelById(companyId);
	}

	@Transactional
	public Long countFuelTodayEntries(Date todaydate, Integer companyId) throws Exception {

		return fuelDao.countFuelTodayEntries(todaydate, companyId);
	}

	@Transactional
	public List<Fuel> findMissingOddmeterFirstDESC_Vaule_Liter_Amount_DELETE(Integer fuel_vehicleid,
			Integer fuel_Odd_Meter, Date fuel_date) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		// return
		// fuelDao.findMissingOddmeterFirstDESC_Vaule_Liter_Amount_DELETE(fuel_vehicleid,
		// fuel_Odd_Meter, fuel_date);

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT f.fuel_id, f.fuel_tank_partial, f.fuel_meter_old, f.fuel_meter, f.fuel_usage, f.fuel_liters,f.fuel_amount from Fuel AS f where f.vid ='"
						+ fuel_vehicleid + "' AND f.fuel_meter <" + fuel_Odd_Meter + "  AND f.fuel_date < '" + fuel_date
						+ "' AND f.companyId = " + userDetails.getCompany_id()
						+ " AND f.markForDelete = 0  ORDER BY  f.fuel_date desc",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<Fuel> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Fuel>();
			Fuel fuel = null;
			for (Object[] result : results) {
				fuel = new Fuel();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_tank_partial((Integer) result[1]);
				fuel.setFuel_meter_old((Integer) result[2]);
				fuel.setFuel_meter((Integer) result[3]);
				fuel.setFuel_usage((Integer) result[4]);
				fuel.setFuel_liters((Double) result[5]);
				fuel.setFuel_amount((Double) result[6]);

				Dtos.add(fuel);
			}
		}
		return Dtos;
	}

	/** missing oddmeter value */
	/**
	 * the value Fuel calculation using Odometer Descending Order fuel_date value in
	 * search v_id, current_odometer & fuel_date also
	 */
	@Transactional
	public List<Fuel> findMissingOddmeterFirstDESC_Vaule_Liter_Amount(Integer fuel_vehicleid, Integer fuel_Odd_Meter,
			Date fuel_date) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT f.fuel_id, f.fuel_tank_partial, f.fuel_meter_old, f.fuel_meter, f.fuel_usage, f.fuel_liters,f.fuel_amount, f.fuel_Number from Fuel AS f where f.vid ='"
						+ fuel_vehicleid + "' AND f.fuel_meter <=" + fuel_Odd_Meter + "  AND f.fuel_date <= '"
						+ fuel_date + "'  AND f.markForDelete = 0  ORDER BY  f.fuel_date DESC , f.fuel_meter DESC",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<Fuel> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Fuel>();
			Fuel fuel = null;
			for (Object[] result : results) {
				fuel = new Fuel();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_tank_partial((Integer) result[1]);
				fuel.setFuel_meter_old((Integer) result[2]);
				fuel.setFuel_meter((Integer) result[3]);
				fuel.setFuel_usage((Integer) result[4]);
				fuel.setFuel_liters((Double) result[5]);
				fuel.setFuel_amount((Double) result[6]);
				fuel.setFuel_Number((Long) result[7]);

				Dtos.add(fuel);
			}
		}
		return Dtos;
	}

	/**
	 * the value Fuel calculation using Odometer Ascending Order fuel_date value in
	 * search v_id, current_odometer & fuel_date also
	 */
	@Transactional
	public List<Fuel> findMissingOddmeterSecondASC_Vaule_Liter_Amount(Integer fuel_vehicleid, Integer fuel_Odd_Meter,
			Date fuel_date) {


		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT f.fuel_id, f.fuel_tank_partial, f.fuel_meter_old, f.fuel_meter, f.fuel_usage, f.fuel_liters,f.fuel_amount, f.companyId from Fuel AS f where f.vid ='"
						+ fuel_vehicleid + "' AND f.fuel_meter >=" + fuel_Odd_Meter + "  AND f.fuel_date >= '"
						+ fuel_date + "' AND f.markForDelete = 0 ORDER BY  f.fuel_date ASC , f.fuel_meter ASC",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<Fuel> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Fuel>();
			Fuel fuel = null;
			for (Object[] result : results) {
				fuel = new Fuel();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_tank_partial((Integer) result[1]);
				fuel.setFuel_meter_old((Integer) result[2]);
				fuel.setFuel_meter((Integer) result[3]);
				fuel.setFuel_usage((Integer) result[4]);
				fuel.setFuel_liters((Double) result[5]);
				fuel.setFuel_amount((Double) result[6]);
				fuel.setCompanyId((Integer) result[7]);

				Dtos.add(fuel);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<Fuel> findMissingOddmeterSecondASC_Vaule_Liter_Amount_DELETE(Integer fuel_vehicleid,
			Integer fuel_Odd_Meter, Date fuel_date) {

		// return
		// fuelDao.findMissingOddmeterSecondASC_Vaule_Liter_Amount_DELETE(fuel_vehicleid,
		// fuel_Odd_Meter, fuel_date);

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT f.fuel_id, f.fuel_tank_partial, f.fuel_meter_old, f.fuel_meter, f.fuel_usage, f.fuel_liters,f.fuel_amount, f.companyId from Fuel AS f where f.vid ='"
						+ fuel_vehicleid + "' AND f.fuel_meter >=" + fuel_Odd_Meter + "  AND f.fuel_date >= '"
						+ fuel_date + "'  ORDER BY  f.fuel_date asc",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<Fuel> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Fuel>();
			Fuel fuel = null;
			for (Object[] result : results) {
				fuel = new Fuel();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_tank_partial((Integer) result[1]);
				fuel.setFuel_meter_old((Integer) result[2]);
				fuel.setFuel_meter((Integer) result[3]);
				fuel.setFuel_usage((Integer) result[4]);
				fuel.setFuel_liters((Double) result[5]);
				fuel.setFuel_amount((Double) result[6]);
				fuel.setCompanyId((Integer) result[7]);

				Dtos.add(fuel);
			}
		}
		return Dtos;
	}

	@Transactional
	public void update_Missing_OddMeter_KM_cost(Integer OldFullTank_OpringKM, Integer OldFullTank_OpringKM_Usage,
			Long FuellFullTank_ID, Double km, Double cost) {

		fuelDao.update_Missing_OddMeter_KM_cost(OldFullTank_OpringKM, OldFullTank_OpringKM_Usage, FuellFullTank_ID, km,
				cost);
	}

	@Transactional
	public void update_Missing_OddMeter_Usage_Partial(Integer OldFullTank_OpringKM, Integer OldFullTank_OpringKM_Usage,
			Long FuellFullTank_ID, Integer companyId) {
		fuelDao.update_Missing_OddMeter_Usage_Partial(OldFullTank_OpringKM, OldFullTank_OpringKM_Usage,
				FuellFullTank_ID, companyId);
	}

	@Transactional
	public void update_Missing_FuelNextFull_KM_cost(Long FuellFullTank_ID, Double km, Double cost, Integer companyId) {
		fuelDao.update_Missing_FuelNextFull_KM_cost(FuellFullTank_ID, km, cost, companyId);
	}

	/*
	 * @Transactional public List<Double> listOFCountTotal_Cost_Paid(String
	 * vendor_name) throws Exception {
	 * 
	 * return fuelDao.listOFCountTotal_Cost_Paid(vendor_name); }
	 */

	@Transactional
	public List<Double> listOFCountTotal_Cost_NotPaid(Integer vendorId, Integer companyId) throws Exception {

		return fuelDao.listOFCountTotal_Cost_NotPaid(vendorId, companyId);
	}

	@Transactional
	public List<Fuel> listFuel_vendor_History(String vendor_name, String fuel_payment) throws Exception {

		return fuelDao.listFuel_vendor_History(vendor_name, fuel_payment);
	}

	@Transactional
	public List<Fuel> listFuelValidate(Integer Vid, Integer odameter, Double liter, Double price, Integer vendor_id,
			String vendor_name, String refernceNumber) {

		// return fuelDao.listFuelValidate(Vid, odameter, liter, price,
		// vendor_id, vendor_name, refernceNumber);
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<Fuel> query = entityManager.createQuery(" SELECT F from Fuel AS F "
				+ " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id" + " where  (F.vid=" + Vid + " AND  F.fuel_meter="
				+ odameter + " AND  F.fuel_liters=" + liter + " AND  F.fuel_price=" + price + "  OR  F.vid=" + Vid
				+ " AND F.vendor_id= " + vendor_id + " AND F.fuel_reference='" + refernceNumber + "' OR  F.vid=" + Vid
				+ " AND VN.vendorName='" + vendor_name + "' AND F.fuel_reference='" + refernceNumber
				+ "') AND F.companyId = " + userDetails.getCompany_id() + " AND F.markForDelete = 0", Fuel.class);
		return query.getResultList();
	}

	// without vendor_name
	@Transactional
	public List<Fuel> listFuelValidate(Integer Vid, Integer odameter, Double liter, Double price,
			String vendor_nameAndId, String refernceNumber, Integer companyId) throws Exception{


		TypedQuery<Fuel> query = null;

		HashMap<String, Object> configuration	=	companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);

		if((boolean) configuration.get(FuelConfigurationConstants.SHOW_REFERENCE_COL)) {
			if (FuelDto.getParseVendorID_STRING_TO_ID(vendor_nameAndId) != 0) {
				query = entityManager
						.createQuery(" SELECT F from Fuel AS F" + " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id"
								+ " where  (F.vid=" + Vid + " AND  F.fuel_meter=" + odameter + " AND  F.fuel_liters="
								+ liter + " AND  F.fuel_price=" + price + "  OR  F.vid=" + Vid + " AND F.vendor_id= "
								+ vendor_nameAndId + " AND F.fuel_reference='" + refernceNumber + "' ) AND F.companyId = "
								+ companyId + " AND F.markForDelete = 0", Fuel.class);
			} else {
				query = entityManager
						.createQuery(" SELECT F from Fuel AS F" + " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id"
								+ " where  (F.vid=" + Vid + " AND  F.fuel_meter=" + odameter + " AND  F.fuel_liters="
								+ liter + " AND  F.fuel_price=" + price + "  OR  F.vid=" + Vid + " AND VN.vendorName='"
								+ vendor_nameAndId + "' AND F.fuel_reference='" + refernceNumber + "') AND F.companyId = "
								+ companyId + " AND F.markForDelete = 0", Fuel.class);
			}
		}else {
			if (FuelDto.getParseVendorID_STRING_TO_ID(vendor_nameAndId) != 0) {
				query = entityManager
						.createQuery(" SELECT F from Fuel AS F" + " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id"
								+ " where  (F.vid=" + Vid + " AND  F.fuel_meter=" + odameter + " AND  F.fuel_liters="
								+ liter + " AND  F.fuel_price=" + price + " ) AND F.companyId = "
								+ companyId + " AND F.markForDelete = 0", Fuel.class);
			} else {
				query = entityManager
						.createQuery(" SELECT F from Fuel AS F" + " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id"
								+ " where  (F.vid=" + Vid + " AND  F.fuel_meter=" + odameter + " AND  F.fuel_liters="
								+ liter + " AND  F.fuel_price=" + price + " ) AND F.companyId = "
								+ companyId + " AND F.markForDelete = 0", Fuel.class);
			}
		}


		return query.getResultList();
	}

	@Transactional
	public List<Fuel> listFuelValidate(String refernceNumber) {

		return fuelDao.listFuelValidate(refernceNumber);
	}

	@Transactional
	public List<FuelDto> SearchFuel(String query, CustomUserDetails userDetails) throws Exception {
		TypedQuery<Object[]> querySearch = null;
		List<FuelDto> Dtos = null;
		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(
				userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		if(query != null && !query.trim().equalsIgnoreCase("") && query.indexOf('\'') != 0 ) {
		if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
			if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				querySearch = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ "  VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.tripSheetNumber ,D.driver_Lastname ,D.driver_fathername " + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where (lower(V.vehicle_registration) Like ('%" + query
								+ "%') OR lower(F.fuel_reference) Like ('%" + query
								+ "%') OR lower(F.fuel_date) Like ('%" + query + "%') OR lower(F.fuel_Number) Like ('%"
								+ query + "%')) AND F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 order by F.fuelDateTime desc",
								Object[].class);

			} else if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				querySearch = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ "  VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPCOLLNUMBER ,D.driver_Lastname ,D.driver_fathername " + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where (lower(V.vehicle_registration) Like ('%" + query
								+ "%') OR lower(F.fuel_reference) Like ('%" + query
								+ "%') OR lower(F.fuel_date) Like ('%" + query + "%') OR lower(F.fuel_Number) Like ('%"
								+ query + "%')) AND F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 order by F.fuelDateTime desc",
								Object[].class);

			} else if ((int) configuration.get(
					TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				querySearch = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ "  VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPDAILYNUMBER ,D.driver_Lastname ,D.driver_fathername  " + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where (lower(V.vehicle_registration) Like ('%" + query
								+ "%') OR lower(F.fuel_reference) Like ('%" + query
								+ "%') OR lower(F.fuel_date) Like ('%" + query + "%') OR lower(F.fuel_Number) Like ('%"
								+ query + "%')) AND F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 order by F.fuelDateTime desc",
								Object[].class);

			}
		} else {
			if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				querySearch = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ "  VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.tripSheetNumber ,D.driver_Lastname ,D.driver_fathername  " + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where (lower(V.vehicle_registration) Like ('%" + query
								+ "%') OR lower(F.fuel_reference) Like ('%" + query
								+ "%') OR lower(F.fuel_date) Like ('%" + query + "%') OR lower(F.fuel_Number) Like ('%"
								+ query + "%')) AND F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 order by F.fuelDateTime desc",
								Object[].class);

			} else if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				querySearch = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ "  VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPCOLLNUMBER ,D.driver_Lastname ,D.driver_fathername  " + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where (lower(V.vehicle_registration) Like ('%" + query
								+ "%') OR lower(F.fuel_reference) Like ('%" + query
								+ "%') OR lower(F.fuel_date) Like ('%" + query + "%') OR lower(F.fuel_Number) Like ('%"
								+ query + "%')) AND F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 order by F.fuelDateTime desc",
								Object[].class);

			} else if ((int) configuration.get(
					TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				querySearch = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ "  VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPDAILYNUMBER ,D.driver_Lastname ,D.driver_fathername  " + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where (lower(V.vehicle_registration) Like ('%" + query
								+ "%') OR lower(F.fuel_reference) Like ('%" + query
								+ "%') OR lower(F.fuel_date) Like ('%" + query + "%') OR lower(F.fuel_Number) Like ('%"
								+ query + "%')) AND F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 order by F.fuelDateTime desc",
								Object[].class);

			}

		}
		List<Object[]> results = querySearch.getResultList();
		querySearch.setMaxResults(50);
		
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				fuel.setFuel_TripsheetNumber((Long) result[33]);
				if(result[34] != null)
				fuel.setDriver_name(fuel.getDriver_name() +" "+result[34]);
				
				if(result[35] != null)
					fuel.setDriver_name(fuel.getDriver_name() +" - "+result[35]);

				Dtos.add(fuel);
			}
		}
		}
		return Dtos;

	}

	@Transactional
	public void update_Vendor_ApprovalTO_Status(String ApprovalFuel_ID, Long Approval_ID, String approval_Status)
			throws Exception {

		entityManager.createQuery("update Fuel set fuel_vendor_approvalID=" + Approval_ID + ", fuel_vendor_paymode='"
				+ approval_Status + "' where fuel_id=" + ApprovalFuel_ID + "").executeUpdate();
	}

	@Transactional
	public void update_Vendor_ApprovalTO_Status_MULTIP_FUEL_ID(String ApprovalFuel_ID, Long Approval_ID,
			short approval_Status, Integer companyId) throws Exception {

		entityManager.createQuery("update Fuel set fuel_vendor_approvalID=" + Approval_ID + ", fuel_vendor_paymodeId="
				+ approval_Status + " where fuel_id IN (" + ApprovalFuel_ID + ") AND companyId = " + companyId + " ")
		.executeUpdate();
	}

	@Transactional
	public void update_Vendor_ApprovalTO_Status(Long ApprovalFuel_ID, Long Approval_ID, short approval_Status,
			Integer companyId) throws Exception {

		entityManager
		.createQuery("update Fuel set fuel_vendor_approvalID=" + Approval_ID + ", fuel_vendor_paymodeId="
				+ approval_Status + " where fuel_id=" + ApprovalFuel_ID + " AND companyId = " + companyId + " ")
		.executeUpdate();

	}

	@Transactional
	public void update_Vendor_ApprovalTO_Status_MUTIP_FUEL_ID(String ApprovalFuel_ID, Long Approval_ID,
			String approval_Status) throws Exception {

		entityManager.createQuery("update Fuel set fuel_vendor_approvalID=" + Approval_ID + ", fuel_vendor_paymode='"
				+ approval_Status + "' where fuel_id IN (" + ApprovalFuel_ID + ") ").executeUpdate();

	}

	/*
	 * @Transactional public void update_Vendor_ApprovalTO_Status_PayDate(Long
	 * ApprovalFuel_ID, Date paymentDate, Long Approval_ID, String approval_Status)
	 * throws Exception {
	 * fuelDao.update_Vendor_ApprovalTO_Status_PayDate(ApprovalFuel_ID, paymentDate,
	 * Approval_ID, approval_Status); }
	 */

	@Transactional
	public void update_Vendor_ApprovalTO_Status_PayDate_Multiple_fuel_ID(String ApprovalFuel_ID, Date paymentDate,
			Long Approval_ID, short approval_Status, Integer companyId,double paidAmount, double discountAmount) throws Exception {
		double balanceAmount = 0;
		entityManager.createQuery("update Fuel set fuel_vendor_paymentdate='" + paymentDate
				+ "' , fuel_vendor_approvalID=" + Approval_ID + ", fuel_vendor_paymodeId=' " + approval_Status
				+ "', paidAmount = '"+paidAmount+"', discountAmount = '"+discountAmount+"' , balanceAmount = '"+balanceAmount+"' "
				+ " where fuel_id IN (" + ApprovalFuel_ID + ") AND companyId = " + companyId + " ").executeUpdate();
	}

	@Transactional
	public List<Fuel> getVendorApproval_IN_FuelTable_List(Long VendorApproval_Id) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return fuelDao.getVendorApproval_IN_FuelTable_List(VendorApproval_Id, userDetails.getCompany_id());
	}

	@Override
	public List<FuelDto> getVendorApproval_IN_FuelTable_List(Long VendorApproval_Id, CustomUserDetails userDetails)
			throws Exception {
		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(
				userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		TypedQuery<Object[]> query = null;
		if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
			query = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id, F.balanceAmount,F.paidAmount,VS.subApprovalpaidAmount " 
							+ " FROM Fuel as F "
							+ " INNER JOIN Vehicle V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id"
							+ " LEFT JOIN Driver D ON D.driver_id = F.driver_id "
							+ " LEFT JOIN VendorSubApprovalDetails VS ON VS.invoiceId = F.fuel_id AND VS.approvalPlaceId ="+ApprovalType.APPROVAL_TYPE_FUEL_ENTRIES+" AND VS.approvalId= "+VendorApproval_Id+" AND VS.markForDelete = 0 "
							+ " where F.fuel_vendor_approvalID = "
							+ VendorApproval_Id + " AND F.companyId = " + userDetails.getCompany_id()
							+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc", Object[].class);

		} else {

			query = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id, F.balanceAmount,F.paidAmount,VS.subApprovalpaidAmount " 
							+ " FROM Fuel as F "
							+ " INNER JOIN Vehicle V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id"
							+ " LEFT JOIN Driver D ON D.driver_id = F.driver_id"
							+ " LEFT JOIN VendorSubApprovalDetails VS ON VS.invoiceId = F.fuel_id AND VS.approvalPlaceId ="+ApprovalType.APPROVAL_TYPE_FUEL_ENTRIES+" AND VS.approvalId= "+VendorApproval_Id+" AND VS.markForDelete = 0 "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "
							+ userDetails.getId() + "" + " where F.fuel_vendor_approvalID = " + VendorApproval_Id
							+ " AND F.companyId = " + userDetails.getCompany_id()
							+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc", Object[].class);

		}
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				fuel.setBalanceAmount((Double) result[33]);
				if((Double) result[34] != null)
				fuel.setPaidAmount((Double) result[34]);
				if(result[35] != null) {
					fuel.setVendorApprovedAmount((Double) result[35]);
				}else {
					fuel.setVendorApprovedAmount(0.0);
				}
				
				Dtos.add(fuel);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<FuelDto> Get_TripSheet_IN_FuelTable_List(Long VendorApproval_Id, int compId, long userId) throws Exception {
		Double 			expectedKM 			= null;
		Integer 		usageKM 			= null;
		double 			expectedKMCost 		= 0;
		double 			actualKMCost 		= 0;
		double 			changeKMCost 		= 0;
		double 			changePrice 		= 0;
		
		try {
			HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(
					compId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			TypedQuery<Object[]> query = null;
			if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
				query = entityManager
						.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, V.vehicle_ExpectedMileage, V.vehicle_ExpectedMileage_to, F.paidByBranchId " 
								+ " FROM Fuel as F "
								+ " INNER JOIN  Vehicle V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Vendor VN ON VN.vendorId = F.vendor_id"
								+ " LEFT JOIN Driver D ON D.driver_id = F.driver_id" + " where F.fuel_TripsheetID = "
								+ VendorApproval_Id + " AND F.companyId = " + compId
								+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc", Object[].class);

			} else {

				query = entityManager
						.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, V.vehicle_ExpectedMileage, V.vehicle_ExpectedMileage_to, F.paidByBranchId" 
								+ " FROM Fuel as F "
								+ " INNER JOIN  Vehicle V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Vendor VN ON VN.vendorId = F.vendor_id"
								+ " LEFT JOIN Driver D ON D.driver_id = F.driver_id"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userId + "" + " where F.fuel_TripsheetID = " + VendorApproval_Id
								+ " AND F.companyId = " + compId
								+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc", Object[].class);

			}
			List<Object[]> results = query.getResultList();

			List<FuelDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<FuelDto>();
				FuelDto fuel = null;
				for (Object[] result : results) {
					fuel = new FuelDto();

					fuel.setFuel_id((Long) result[0]);
					fuel.setFuel_Number((Long) result[1]);
					fuel.setFuel_TripsheetID((Long) result[2]);
					fuel.setVid((Integer) result[3]);
					fuel.setVehicle_registration((String) result[4]);
					fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
					fuel.setFuel_D_date((Date) result[6]);
					fuel.setFuel_meter((Integer) result[7]);
					fuel.setFuel_meter_old((Integer) result[8]);
					fuel.setFuel_meter_attributes((Integer) result[9]);
					fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
					fuel.setFuel_liters((Double) result[11]);
					fuel.setFuel_price((Double) result[12]);// result[2]
					fuel.setVehicle_group((String) result[13]);
					fuel.setDriver_id((Integer) result[14]);
					fuel.setDriver_name((String) result[15]);
					fuel.setDriver_empnumber((String) result[16]);
					fuel.setVendor_id((Integer) result[17]);
					fuel.setVendor_name((String) result[18]);
					fuel.setVendor_location((String) result[19]);
					fuel.setPaymentTypeId((short) result[20]);
					fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
					fuel.setFuel_reference((String) result[21]);
					fuel.setFuel_personal((Integer) result[22]);
					fuel.setFuel_tank((Integer) result[23]);
					fuel.setFuel_tank_partial((Integer) result[24]);
					fuel.setFuel_comments((String) result[25]);
					fuel.setFuel_usage((Integer) result[26]);
					fuel.setFuel_amount((Double) result[27]);
					fuel.setFuel_kml((Double) result[28]);
					fuel.setFuel_cost((Double) result[29]);
					fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
					fuel.setFuel_document((boolean) result[31]);
					fuel.setFuel_document_id((Long) result[32]);
					fuel.setVehicle_ExpectedMileage((Double) result[33]);
					fuel.setVehicle_ExpectedMileage_to((Double) result[34]);
					Double expectedMileage = fuel.getVehicle_ExpectedMileage_to();
					
					if(expectedMileage != null && fuel.getFuel_kml() != null && fuel.getFuel_usage() > 0 && fuel.getFuel_amount() != null && fuel.getFuel_amount() > 0) {
						expectedKM  	= fuel.getFuel_liters() * expectedMileage;
						usageKM 		= fuel.getFuel_usage();
						if(usageKM != null && usageKM > 0 && expectedMileage > 0) {
							
							expectedKMCost	= fuel.getFuel_amount() / expectedKM;
							actualKMCost	= fuel.getFuel_amount() / usageKM;
							
							changeKMCost	= expectedKMCost - actualKMCost;
							changePrice		= changeKMCost * usageKM;
						}
					}
					
					
					if(fuel.getFuel_kml() == null) {
						fuel.setFuelPriceDiff(0.0);
					}else {
						fuel.setFuelPriceDiff(changePrice);
					}
					if(result[35]!=null)
						fuel.setPaidByBranchId((Long)result[35] );
					else
						fuel.setPaidByBranchId(0L);

					Dtos.add(fuel);
				}
			}
			return Dtos;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}

	// Fuel Payment in approval Details

	/*
	 * @Transactional public List<Fuel> listFuel_vendor_APPROVALLIST(String
	 * vendor_name) throws Exception {
	 * 
	 * return fuelDao.listFuel_vendor_APPROVALLIST(vendor_name); }
	 */

	@Transactional
	public List<FuelDto> listFuel_vendor_APPROVALLISTFilter(Integer vendor_id, String ApprovalQuery, Integer companyId)
			throws Exception {
		List<FuelDto> 					Dtos 					= null;
		CustomUserDetails 				userDetails 			= null;
		HashMap<String, Object> 		configuration 			= null;
		TypedQuery<Object[]> 			query 					= null;
		short 							paymentTypeCreadit 		= 0;
		short 							notPaid 				= 0;
		short 							approved 				= 0;
		short 							partiallyPaid 			= 0;
		try {
			paymentTypeCreadit 	= PaymentTypeConstant.PAYMENT_TYPE_CREDIT;
			notPaid 			= FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID;
			approved			= FuelVendorPaymentMode.PAYMENT_MODE_APPROVED;
			partiallyPaid		= FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID;


			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);

			if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, F.paidAmount, F.balanceAmount "
								+ " FROM Fuel as F "
								+ " INNER JOIN Vehicle V on V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id"
								+ " LEFT JOIN Driver D ON D.driver_id = F.driver_id" 
								+ " where (( F.vendor_id='" + vendor_id+"' AND F.paymentTypeId=" + paymentTypeCreadit
								+ " AND F.fuel_vendor_paymodeId IN('" + notPaid + "','" + approved + "','" + partiallyPaid + "')) AND " + ApprovalQuery+") "
								+ " AND F.companyId = " + companyId + " AND F.markForDelete = 0 ORDER BY F.fuel_date desc",
								Object[].class);
			} else {

				query = entityManager
						.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id ,F.paidAmount, F.balanceAmount "
								+ " FROM Fuel as F "
								+ " INNER JOIN Vehicle V on V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "
								+ userDetails.getId() + "" + " LEFT JOIN Driver D ON D.driver_id = F.driver_id "
								+ " where (( F.vendor_id='" + vendor_id+"' AND F.paymentTypeId=" + paymentTypeCreadit
								+ " AND F.fuel_vendor_paymodeId IN('" + notPaid + "','" + approved + "','" + partiallyPaid + "')) AND " + ApprovalQuery+ ") "
								+ " AND F.companyId = " + companyId + " AND F.markForDelete = 0 ORDER BY F.fuel_date desc",
								Object[].class);

			}
			List<Object[]> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<FuelDto>();
				FuelDto fuel = null;
				for (Object[] result : results) {
					fuel = new FuelDto();

					fuel.setFuel_id((Long) result[0]);
					fuel.setFuel_Number((Long) result[1]);
					fuel.setFuel_TripsheetID((Long) result[2]);
					fuel.setVid((Integer) result[3]);
					fuel.setVehicle_registration((String) result[4]);
					fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
					fuel.setFuel_D_date((Date) result[6]);
					fuel.setFuel_meter((Integer) result[7]);
					fuel.setFuel_meter_old((Integer) result[8]);
					fuel.setFuel_meter_attributes((Integer) result[9]);
					fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
					fuel.setFuel_liters((Double) result[11]);
					if(result[12] != null)
					fuel.setFuel_price(Double.parseDouble(toFixedTwo.format(result[12])));// result[2]
					fuel.setVehicle_group((String) result[13]);
					fuel.setDriver_id((Integer) result[14]);
					fuel.setDriver_name((String) result[15]);
					fuel.setDriver_empnumber((String) result[16]);
					fuel.setVendor_id((Integer) result[17]);
					fuel.setVendor_name((String) result[18]);
					fuel.setVendor_location((String) result[19]);
					fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
					fuel.setFuel_reference((String) result[21]);
					fuel.setFuel_personal((Integer) result[22]);
					fuel.setFuel_tank((Integer) result[23]);
					fuel.setFuel_tank_partial((Integer) result[24]);
					fuel.setFuel_comments((String) result[25]);
					fuel.setFuel_usage((Integer) result[26]);
					if(result[27] != null)
					fuel.setFuel_amount(Double.parseDouble(toFixedTwo.format(result[27])));
					fuel.setFuel_kml((Double) result[28]);
					if(result[29] != null)
					fuel.setFuel_cost(Double.parseDouble(toFixedTwo.format(result[29])));
					fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
					fuel.setFuel_document((boolean) result[31]);
					fuel.setFuel_document_id((Long) result[32]);

					if(result[33]!=null) {
						fuel.setPaidAmount(Double.parseDouble(toFixedTwo.format(result[33])));
					}
					if(result[34]!=null) {
						fuel.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[34])));
					}

					Dtos.add(fuel);
				}
			}

		}catch(Exception e) {
			System.err.println("e"+e);
		}
		return Dtos;
	}

	@Transactional
	public List<FuelDto> listFuel_vendor_Credit_To_NotPaid(Integer vendor_id, Integer companyId) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(
				userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		TypedQuery<Object[]> query = null;
		if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
			query = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id" + " FROM Fuel as F "
							+ " INNER JOIN Vehicle V on V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id"
							+ " LEFT JOIN Driver D ON D.driver_id = F.driver_id" + " where  ( F.vendor_id=" + vendor_id
							+ " and F.paymentTypeId=" + PaymentTypeConstant.PAYMENT_TYPE_CREDIT
							+ " and F.fuel_vendor_paymodeId =" + FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID
							+ " OR F.vendor_id=" + vendor_id + " and F.paymentTypeId="
							+ PaymentTypeConstant.PAYMENT_TYPE_CREDIT + " and F.fuel_vendor_paymodeId ="
							+ FuelVendorPaymentMode.PAYMENT_MODE_APPROVED + " ) AND F.companyId = " + companyId
							+ " AND F.markForDelete = 0 ORDER BY fuel_id desc", Object[].class);

		} else {

			query = entityManager.createQuery(
					"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id" + " FROM Fuel as F "
							+ " INNER JOIN Vehicle V on V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id"
							+ " LEFT JOIN Driver D ON D.driver_id = F.driver_id"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "
							+ userDetails.getId() + "" + " where  ( F.vendor_id=" + vendor_id + " and F.paymentTypeId="
							+ PaymentTypeConstant.PAYMENT_TYPE_CREDIT + " and F.fuel_vendor_paymodeId ="
							+ FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID + " OR F.vendor_id=" + vendor_id
							+ " and F.paymentTypeId=" + PaymentTypeConstant.PAYMENT_TYPE_CREDIT
							+ " and F.fuel_vendor_paymodeId =" + FuelVendorPaymentMode.PAYMENT_MODE_APPROVED
							+ " ) AND F.companyId = " + companyId + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
							Object[].class);

		}
		query.setFirstResult(0);
		query.setMaxResults(5);
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);

				Dtos.add(fuel);
			}
		}
		return Dtos;

	}

	@Transactional
	public List<FuelDto> listFuel_vendor_Cash_To_Paid(Integer vendor_id, Integer companyId) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(
				userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		TypedQuery<Object[]> query = null;
		if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
			query = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id" + " FROM Fuel as F "
							+ " INNER JOIN Vehicle V ON V.vid = F.vid"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id"
							+ " LEFT JOIN Driver D ON D.driver_id = F.driver_id" + " where  ( F.vendor_id=" + vendor_id
							+ " and F.paymentTypeId=" + PaymentTypeConstant.PAYMENT_TYPE_CASH
							+ " and F.fuel_vendor_paymodeId = " + FuelVendorPaymentMode.PAYMENT_MODE_PAID
							+ " OR (F.vendor_id=" + vendor_id + " and F.paymentTypeId="
							+ PaymentTypeConstant.PAYMENT_TYPE_CREDIT + " and F.fuel_vendor_paymodeId ="
							+ FuelVendorPaymentMode.PAYMENT_MODE_PAID + ")) AND F.companyId = " + companyId
							+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc", Object[].class);

		} else {

			query = entityManager.createQuery(
					"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id" + " FROM Fuel as F "
							+ " INNER JOIN Vehicle V ON V.vid = F.vid"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id"
							+ " LEFT JOIN Driver D ON D.driver_id = F.driver_id"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "
							+ userDetails.getId() + "" + " where  ( F.vendor_id=" + vendor_id + " and F.paymentTypeId="
							+ PaymentTypeConstant.PAYMENT_TYPE_CASH + " and F.fuel_vendor_paymodeId = "
							+ FuelVendorPaymentMode.PAYMENT_MODE_PAID + " OR (F.vendor_id=" + vendor_id
							+ " and F.paymentTypeId=" + PaymentTypeConstant.PAYMENT_TYPE_CREDIT
							+ " and F.fuel_vendor_paymodeId =" + FuelVendorPaymentMode.PAYMENT_MODE_PAID
							+ ")) AND F.companyId = " + companyId + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
							Object[].class);

		}
		query.setFirstResult(0);
		query.setMaxResults(5);
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);

				Dtos.add(fuel);
			}
		}
		return Dtos;

	}

	/*
	 * @Transactional public List<Fuel> listFuel_vendor_To_Approval(String
	 * vendor_name) throws Exception {
	 * 
	 * return fuelDao.listFuel_vendor_To_Approval(vendor_name); }
	 */

	@Transactional
	public List<Fuel> getFuelValidate_APPROVAL_Status(Long Fuel_ID, int companyId) {
		
		return fuelDao.getFuelValidate_APPROVAL_Status(Fuel_ID, companyId);
	}

	@Transactional
	public List<Fuel> GraphVehicleFuelReport(Integer vid) {

		TypedQuery<Fuel> query = entityManager.createQuery("from Fuel where vid=" + vid + " ORDER BY fuel_date",
				Fuel.class);
		query.setFirstResult(0);
		query.setMaxResults(10);
		return query.getResultList();
	}

	@Transactional
	public Double getVehicleFuelMonthlyReport(Date sql) {

		TypedQuery<Double> query = entityManager
				.createQuery("select sum(job.fuel_amount) from Fuel job" + " where year(job.fuel_date) = year('" + sql
						+ "')" + " and month(job.fuel_date) = month('" + sql + "')", Double.class);
		return query.getSingleResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IFuelService#
	 * Vehicle_wise_Fuel_Mileage_Report(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<FuelDto> Vehicle_wise_Fuel_Mileage_Report(String DateRangeFrom, String DateRangeTo, String Multi_vid,
			Integer companyId) throws Exception {

		TypedQuery<Object[]> query = null;
		query = entityManager
				.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
						+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
						+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
						+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
						+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
						+ " F.fuel_document, F.fuel_document_id, F.fuel_reference, V.vehicle_ExpectedMileage, V.vehicle_ExpectedMileage_to,D.driver_fathername,D.driver_Lastname,F.gpsUsageKM " + " FROM Fuel as F "
						+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
						+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id " + " where F.fuel_date between '"
						+ DateRangeFrom + "' AND '" + DateRangeTo + "' AND  F.vid IN (" + Multi_vid
						+ ") AND F.companyId = " + companyId
						+ " AND F.markForDelete = 0  ORDER BY F.fuel_date DESC , F.fuel_meter DESC", Object[].class);

		/*
		 * List<Integer> names = Arrays.asList(3243, 13);
		 * 
		 * query.setParameter("ids", names);
		 */
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				if(result[33] != null) {	//latest
					fuel.setFuel_reference((String) result[33]);
				}else {
					fuel.setFuel_reference("NA");
				}
				if(result[34] != null)
					fuel.setVehicle_ExpectedMileage((Double) result[34]);
				if(result[35] != null)
					fuel.setVehicle_ExpectedMileage_to((Double) result[35]);
				
				if(result[36] != null && !((String) result[36]).trim().equals("")) {
					fuel.setFirstDriverFatherName(" - "+ result[36]);
				}else {
					fuel.setFirstDriverFatherName(" ");
				}
				fuel.setFirstDriverLastName((String) result[37]);
				if(result[38]!= null) {
					fuel.setGpsUsageKM((Double) result[38]);
				}else {
					fuel.setGpsUsageKM(0.0);
				}
				
				Double expectedKM	= 0.0;
				Double expectedMileage = fuel.getVehicle_ExpectedMileage_to();
				Integer usageKM	= 0;
				Double	expectedKMCost	= 0.0;
				Double	actualKMCost	= 0.0;
				Double	changeKMCost	= 0.0;
				Double	changePrice		= 0.0;
				if(expectedMileage != null && fuel.getFuel_kml() != null && fuel.getFuel_usage() > 0 && fuel.getFuel_amount() != null && fuel.getFuel_amount() > 0) {
					expectedKM  	= fuel.getFuel_liters() * expectedMileage;
					usageKM 		= fuel.getFuel_usage();
					if(usageKM != null && usageKM > 0 && expectedMileage > 0) {
						
						expectedKMCost	= fuel.getFuel_amount() / expectedKM;
						actualKMCost	= fuel.getFuel_amount() / usageKM;
						
						changeKMCost	= expectedKMCost - actualKMCost;
						changePrice		= changeKMCost * usageKM;
					}
				}
				fuel.setFuelPriceDiff(changePrice);
				
				Dtos.add(fuel);
			}
		}
		return Dtos;
	}


	@Override
	public ValueObject Group_wise_Fuel_Mileage_Report(String DateRangeFrom, String DateRangeTo, Long groupId, Integer companyId) throws Exception {
		ValueObject		valueObject	= null;
		TypedQuery<Object[]> query = null;
		query = entityManager
				.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
						+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
						+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
						+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
						+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
						+ " F.fuel_document, F.fuel_document_id,F.fuel_reference, V.vehicle_ExpectedMileage, V.vehicle_ExpectedMileage_to,D.driver_fathername,D.driver_Lastname,F.gpsUsageKM " + " FROM Fuel as F "
						+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
						+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id " + " where F.fuel_date between '"
						+ DateRangeFrom + "' AND '" + DateRangeTo + "' AND VG.gid = "+groupId+"   AND F.companyId = " + companyId
						+ " AND F.markForDelete = 0  ORDER BY F.fuel_date DESC , F.fuel_meter DESC", Object[].class);

		/*
		 * List<Integer> names = Arrays.asList(3243, 13);
		 * 
		 * query.setParameter("ids", names);
		 */
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			valueObject	= new ValueObject();
			String vehicleIds	= "";
			int count	= 0;
			for (Object[] result : results) {
				fuel = new FuelDto();
				count ++;
				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				if(result[33] != null) {	//latest
					fuel.setFuel_reference((String) result[33]);  
				}else {
					fuel.setFuel_reference("NA");
				}
				if(result[34] != null)
					fuel.setVehicle_ExpectedMileage((Double) result[34]);
				if(result[35] != null)
					fuel.setVehicle_ExpectedMileage_to((Double) result[35]);
				
				
				if(result[36] != null && !((String) result[36]).trim().equals("")) {
					fuel.setFirstDriverFatherName(" - "+ result[36]);
				}else {
					fuel.setFirstDriverFatherName(" ");
				}
				fuel.setFirstDriverLastName((String) result[37]);
				if(result[38]!= null) {
					fuel.setGpsUsageKM((Double) result[38]);
				}else {
					fuel.setGpsUsageKM(0.0);
				}
				if(results.size() > count) {
					vehicleIds += fuel.getVid() +",";
				}else {
					vehicleIds += fuel.getVid();
				}
				
				
				Double expectedKM	= 0.0;
				Double expectedMileage = fuel.getVehicle_ExpectedMileage_to();
				Integer usageKM	= 0;
				Double	expectedKMCost	= 0.0;
				Double	actualKMCost	= 0.0;
				Double	changeKMCost	= 0.0;
				Double	changePrice		= 0.0;
				if(expectedMileage != null && fuel.getFuel_kml() != null && fuel.getFuel_usage() > 0 && fuel.getFuel_amount() != null && fuel.getFuel_amount() > 0) {
					expectedKM  	= fuel.getFuel_liters() * expectedMileage;
					usageKM 		= fuel.getFuel_usage();
					if(usageKM != null && usageKM > 0 && expectedMileage > 0) {
						
						expectedKMCost	= fuel.getFuel_amount() / expectedKM;
						actualKMCost	= fuel.getFuel_amount() / usageKM;
						
						changeKMCost	= expectedKMCost - actualKMCost;
						changePrice		= changeKMCost * usageKM;
					}
				}
				
					fuel.setFuelPriceDiff(changePrice);
					

				Dtos.add(fuel);
			}
			valueObject.put("fuelList", Dtos);
			valueObject.put("vehicleIds", vehicleIds);
		}



		return valueObject;
	}
	
	
	@Transactional
	public List<FuelDto> allVehicleWiseMileage_Report(int companyId,String queryIn) throws Exception {

		TypedQuery<Object[]> query = null;
		query = entityManager
				.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
						+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
						+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
						+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
						+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
						+ " F.fuel_document, F.fuel_document_id, F.fuel_reference, V.vehicle_ExpectedMileage, V.vehicle_ExpectedMileage_to " + " FROM Fuel as F "
						+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
						+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id " + " where " + queryIn
						+ " AND F.companyId = " + companyId
						+ " AND F.markForDelete = 0  ORDER BY F.fuel_date DESC , F.fuel_meter DESC", Object[].class);


		/*
		 * List<Integer> names = Arrays.asList(3243, 13);
		 * 
		 * query.setParameter("ids", names);
		 */
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				
				
				fuel.setFuelNumber("<a target=\"_blank\" style=\"color: blue; background: #ffc;\"  href=\"showFuel.in?FID="+fuel.getFuel_id()+" \"> F-"+(Long )result[1]+" </a>"); 
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration("<a target=\"_blank\" style=\"color: blue; background: #ffc;\" href=\"showVehicle?vid="+fuel.getVid()+" \"> "+(String )result[4]+" </a>");
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				if(result[6] != null)
				fuel.setFuel_date(dateFormat.format(result[6]));
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				if(result[11] != null)
				fuel.setFuel_liters(Double.parseDouble(toFixedTwo.format(result[11])));
				if(result[12] != null)
				fuel.setFuel_price(Double.parseDouble(toFixedTwo.format(result[12])));// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				if(result[27] != null)
				fuel.setFuel_amount(Double.parseDouble(toFixedTwo.format(result[27])));
				if(result[28] != null)
				fuel.setFuel_kml(Double.parseDouble(toFixedTwo.format(result[28])));
				if(result[29] != null)
				fuel.setFuel_cost(Double.parseDouble(toFixedTwo.format(result[29])));
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				if(result[33] != null) {	//latest
					fuel.setFuel_reference((String) result[33]);
				}else {
					fuel.setFuel_reference("NA");
				}
				if(result[34] != null)
					fuel.setVehicle_ExpectedMileage(Double.parseDouble(toFixedTwo.format(result[34])));
				if(result[35] != null)
					fuel.setVehicle_ExpectedMileage_to(Double.parseDouble(toFixedTwo.format(result[35])));
				
				
				
				Dtos.add(fuel);
			}
		}
		return Dtos;
	}
	
	
	
	
	
	
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IFuelService#
	 * Fuel_Range_with_Vehicle_wise__Report(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<FuelDto> Fuel_Range_with_Vehicle_wise__Report(String DateRangeFrom, String DateRangeTo,
			String Multi_vid, Integer companyId) throws Exception {

		TypedQuery<Object[]> query = null;
		query = entityManager
				.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
						+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
						+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
						+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
						+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
						+ " F.fuel_document, F.fuel_document_id ,D.driver_fathername,D.driver_Lastname" + " FROM Fuel as F "
						+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
						+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id " + " where F.fuel_date between '"
						+ DateRangeFrom + "' AND '" + DateRangeTo + "' AND F.fuel_tank_partial = 0 AND F.vid IN ("
						+ Multi_vid + ") AND F.companyId = " + companyId
						+ " AND F.markForDelete = 0 ORDER BY F.fuel_date DESC , F.fuel_meter DESC ", Object[].class);

		/*
		 * List<Integer> names = Arrays.asList(3243, 13);
		 * 
		 * query.setParameter("ids", names);
		 */
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				if(result[33] != null && !((String)result[33]).trim().equals("")) {
					fuel.setFirstDriverFatherName(" - "+ result[33]);
				}else {
					fuel.setFirstDriverFatherName("");
				}
				fuel.setFirstDriverLastName((String)result[34]);
				Dtos.add(fuel);
			}
		}
		return Dtos;
	}

	
	@Override
	public List<FuelDto> Fuel_Range_with_Vehicle_wise__ReportWithPartial(String DateRangeFrom, String DateRangeTo,
			String Multi_vid, Integer companyId) throws Exception {

		TypedQuery<Object[]> query = null;
		query = entityManager
				.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
						+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
						+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
						+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
						+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
						+ " F.fuel_document, F.fuel_document_id ,D.driver_fathername,D.driver_Lastname" + " FROM Fuel as F "
						+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
						+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id " + " where F.fuel_date between '"
						+ DateRangeFrom + "' AND '" + DateRangeTo + "' AND F.vid IN ("
						+ Multi_vid + ") AND F.companyId = " + companyId
						+ " AND F.markForDelete = 0 ORDER BY F.fuel_date DESC , F.fuel_meter DESC ", Object[].class);

		/*
		 * List<Integer> names = Arrays.asList(3243, 13);
		 * 
		 * query.setParameter("ids", names);
		 */
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				if(result[33] != null && !((String)result[33]).trim().equals("")) {
					fuel.setFirstDriverFatherName(" - "+ result[33]);
				}else {
					fuel.setFirstDriverFatherName("");
				}
				fuel.setFirstDriverLastName((String)result[34]);

				Dtos.add(fuel);
			}
		}
		return Dtos;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IFuelService#
	 * Vehicle_wise_Group_FuelRange_Report(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<FuelDto> Vehicle_wise_Group_FuelRange_Report(String DateRangeFrom, String DateRangeTo,
			long VehicleGroupId, Integer companyId) throws Exception {

		TypedQuery<Object[]> query = null;
		query = entityManager
				.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
						+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
						+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
						+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
						+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
						+ " F.fuel_document, F.fuel_document_id,D.driver_fathername ,D.driver_Lastname " + " FROM Fuel as F "
						+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
						+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id " + " where F.fuel_date between '"
						+ DateRangeFrom + "' AND '" + DateRangeTo
						+ "' AND F.fuel_tank_partial = 0  AND  V.vehicleGroupId =" + VehicleGroupId
						+ " AND F.companyId = " + companyId
						+ "  AND F.markForDelete = 0 ORDER BY F.fuel_date DESC , F.fuel_meter DESC", Object[].class);
		
		
				

		/*
		 * List<Integer> names = Arrays.asList(3243, 13);
		 * 
		 * query.setParameter("ids", names);
		 */
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				if(result[33] != null && !((String)result[33]).trim().equals("")) {
					fuel.setFirstDriverFatherName(" - "+ result[33]);
				}else {
					fuel.setFirstDriverFatherName("");
				}
				fuel.setFirstDriverLastName((String)result[34]);
				Dtos.add(fuel);
			}
		}
		return Dtos;

	}
	
	@Override
	public List<FuelDto> Vehicle_wise_Group_FuelRange_ReportWithPartial(String DateRangeFrom, String DateRangeTo,
			long VehicleGroupId, Integer companyId) throws Exception {

		TypedQuery<Object[]> query = null;
		query = entityManager
				.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
						+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
						+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
						+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
						+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
						+ " F.fuel_document, F.fuel_document_id ,D.driver_fathername,D.driver_Lastname" + " FROM Fuel as F "
						+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
						+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id " + " where F.fuel_date between '"
						+ DateRangeFrom + "' AND '" + DateRangeTo
						+ "' AND  V.vehicleGroupId =" + VehicleGroupId
						+ " AND F.companyId = " + companyId
						+ "  AND F.markForDelete = 0 ORDER BY F.fuel_date DESC , F.fuel_meter DESC", Object[].class);
		
		
				

		/*
		 * List<Integer> names = Arrays.asList(3243, 13);
		 * 
		 * query.setParameter("ids", names);
		 */
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				if(result[33] != null && !((String)result[33]).trim().equals("")) {
					fuel.setFirstDriverFatherName(" - "+ result[33]);
				}else {
					fuel.setFirstDriverFatherName("");
				}
				fuel.setFirstDriverLastName((String)result[34]);

				Dtos.add(fuel);
			}
		}
		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IFuelService#
	 * Vendor_delete_ValidateIn_Fuel(java.lang.Integer)
	 */
	@Transactional
	public List<Fuel> Vendor_delete_ValidateIn_Fuel(Integer vendor_Id, Integer companyId) throws Exception {

		return fuelDao.Vendor_delete_ValidateIn_Fuel(vendor_Id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IFuelService#
	 * update_Vehicle_Group_AND_Vehicle_Ownership(java.lang.String,
	 * java.lang.String, java.lang.Integer)
	 * 
	 * @Transactional public void update_Vehicle_Group_AND_Vehicle_Ownership(String
	 * Vehicle_Group, short Vehicle_Ownership, Integer vehicle_id, long
	 * vehicleGroupId) {
	 * 
	 * fuelDao.update_Vehicle_Group_AND_Vehicle_Ownership(Vehicle_Group,
	 * Vehicle_Ownership, vehicle_id, vehicleGroupId); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IFuelService#
	 * Vehicel_Fuel_Chart_InDate(java.lang.String, java.lang.String,
	 * java.lang.Integer)
	 */
	@Transactional
	public List<Fuel> Vehicel_Fuel_Chart_InDate(String dateFrom, String dateTo, Integer vehicle_id) {

		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT f.fuel_id, f.fuel_kml, date(f.fuel_date) from Fuel AS f where f.vid ='"
						+ vehicle_id + "' AND f.fuel_date between '" + dateFrom + "' AND  '" + dateTo
						+ "'  ORDER BY f.fuel_date DESC , f.fuel_meter DESC", Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<Fuel> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Fuel>();
			Fuel fuel = null;
			for (Object[] result : results) {
				fuel = new Fuel();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_kml((Double) result[1]);
				fuel.setFuel_date((Date) result[2]);

				Dtos.add(fuel);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IFuelService#
	 * getDeployment_Page_FuelVendor_Page(java.lang.Integer, java.lang.Integer)
	 */
	@Transactional
	public Page<Fuel> getDeployment_Page_FuelVendor_Page(Integer pageNumber, Integer VendorId) {

		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return fuelDao.getDeployment_Page_FuelVendor_Page(VendorId, pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IFuelService#
	 * list_FuelVendor_ListOf_FuelEntries(java.lang.Integer, java.lang.Integer)
	 */
	@Transactional
	public List<FuelDto> list_FuelVendor_ListOf_FuelEntries(Integer pageNumber, Integer VendorId) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(
				userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		TypedQuery<Object[]> query = null;
		if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
			if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.tripSheetNumber" + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.companyId = " + userDetails.getCompany_id() + " AND  F.vendor_id="
								+ VendorId + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			} else if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPCOLLNUMBER" + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.companyId = " + userDetails.getCompany_id() + " AND AND  F.vendor_id="
								+ VendorId + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			} else if ((int) configuration.get(
					TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPDAILYNUMBER" + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.companyId = " + userDetails.getCompany_id() + " AND  F.vendor_id="
								+ VendorId + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			}
		} else {
			if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.tripSheetNumber" + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.companyId = " + userDetails.getCompany_id() + " AND  F.vendor_id="
								+ VendorId + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			} else if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPCOLLNUMBER" + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.companyId = " + userDetails.getCompany_id() + " AND  F.vendor_id="
								+ VendorId + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			} else if ((int) configuration.get(
					TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPDAILYNUMBER" + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.companyId = " + userDetails.getCompany_id() + " AND  F.vendor_id="
								+ VendorId + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			}

		}
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				fuel.setFuel_TripsheetNumber((Long) result[33]);

				Dtos.add(fuel);
			}
		}
		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IFuelService#add_Fuel_To_FuelDocument
	 * (org.fleetop.persistence.model.FuelDocument)
	 */
	@Transactional
	public void add_Fuel_To_FuelDocument(org.fleetopgroup.persistence.document.FuelDocument fuelDoc) {
		fuelDoc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_FUEL_DOCUMENT));
		mongoTemplate.save(fuelDoc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IFuelService#
	 * get_Fuel_Document_Details(java.lang.Long)
	 */
	@Transactional
	public org.fleetopgroup.persistence.document.FuelDocument get_Fuel_Document_Details(Long fuel_id, Integer companyId) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(fuel_id).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.FuelDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IFuelService#
	 * Update_FuelDocument_ID_to_Fuel(java.lang.Long, boolean, java.lang.Long)
	 */
	@Transactional
	public void Update_FuelDocument_ID_to_Fuel(Long fueldocid, boolean b, Long fuel_id, Integer companyId) {
		// Note: Update to Fuel Document id to Fuel

		fuelDao.Update_FuelDocument_ID_to_Fuel(fueldocid, b, fuel_id, companyId);

	}

	@Override
	public FuelDto getFuelByNumber(Long fuel_Number, CustomUserDetails userDetails) throws Exception {
		TypedQuery<Object[]> query = null;
		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(
				userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
			if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.tripSheetNumber, F.fuel_vendor_paymentdate, U.email, U2.email,"
								+ " F.created, F.lastupdated, F.fuel_vendor_approvalID" + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " LEFT JOIN User U ON U.id = F.createdById"
								+ " LEFT JOIN User U2 ON U2.id = F.lastModifiedById" + " where F.fuel_Number = "
								+ fuel_Number + " AND F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			} else if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPCOLLNUMBER, F.fuel_vendor_paymentdate, U.email, U2.email,"
								+ " F.created, F.lastupdated, F.fuel_vendor_approvalID" + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " LEFT JOIN User U ON U.id = F.createdById"
								+ " LEFT JOIN User U2 ON U2.id = F.lastModifiedById"
								+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.fuel_Number = " + fuel_Number + " AND F.companyId = "
								+ userDetails.getCompany_id() + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			} else if ((int) configuration.get(
					TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPDAILYNUMBER, F.fuel_vendor_paymentdate, U.email, U2.email,"
								+ " F.created, F.lastupdated, F.fuel_vendor_approvalID" + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " LEFT JOIN User U ON  U.id = F.createdById"
								+ " LEFT JOIN User U2 ON U2.id = F.lastModifiedById"
								+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.fuel_Number = " + fuel_Number + " AND F.companyId = "
								+ userDetails.getCompany_id() + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);
			}

		} else {
			if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.tripSheetNumber, F.fuel_vendor_paymentdate, U.email, U2.email,"
								+ " F.created, F.lastupdated, F.fuel_vendor_approvalID" + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " LEFT JOIN User U ON U.id = F.createdById"
								+ " LEFT JOIN User U2 ON U2.id = F.lastModifiedById" + " where F.fuel_Number = "
								+ fuel_Number + " AND F.companyId = " + userDetails.getCompany_id()
								+ " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			} else if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPCOLLNUMBER, F.fuel_vendor_paymentdate, U.email, U2.email,"
								+ " F.created, F.lastupdated, F.fuel_vendor_approvalID" + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " LEFT JOIN User U ON U.id = F.createdById"
								+ " LEFT JOIN User U2 ON U2.id = F.lastModifiedById"
								+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.fuel_Number = " + fuel_Number + " AND F.companyId = "
								+ userDetails.getCompany_id() + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			} else if ((int) configuration.get(
					TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
								+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
								+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
								+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
								+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
								+ " F.fuel_document, F.fuel_document_id, TS.TRIPDAILYNUMBER, F.fuel_vendor_paymentdate, U.email, U2.email,"
								+ " F.created, F.lastupdated, F.fuel_vendor_approvalID" + " FROM Fuel as F "
								+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
								+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " LEFT JOIN User U ON  U.id = F.createdById"
								+ " LEFT JOIN User U2 ON U2.id = F.lastModifiedById"
								+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
								+ " where F.fuel_Number = " + fuel_Number + " AND F.companyId = "
								+ userDetails.getCompany_id() + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);
			}

		}
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();
				// fuel_vendor_paymentdate
				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setVehicle_OwnershipId((short) result[5]);
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuelTypeId((short) result[10]);
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setPaymentTypeId((short) result[20]);
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_vendor_paymodeId((short) result[30]);
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				fuel.setFuel_TripsheetNumber((Long) result[33]);
				fuel.setFuel_vendor_paymentdate((Date) result[34]);
				fuel.setCreatedBy((String) result[35]);
				fuel.setLastModifiedBy((String) result[36]);
				fuel.setCreatedOn((Date) result[37]);
				fuel.setLastupdatedOn((Date) result[38]);
				fuel.setFuel_vendor_approvalID((Long) result[39]);
				Dtos.add(fuel);
			}
		}
		return Dtos.get(0);

	}

	@Override
	public Long getTripSheetIdByNumber(long tripSheetId) throws Exception {

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(
					userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			Query query = null;
			if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				query = entityManager.createQuery("SELECT  TS.tripSheetID, TS.tripSheetNumber "
						+ "from TripSheet AS TS where TS.tripSheetNumber = " + tripSheetId + " AND TS.companyId = "
						+ userDetails.getCompany_id() + " AND TS.markForDelete = 0 order by TS.tripSheetID DESC");
			} else if ((int) configuration
					.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				query = entityManager.createQuery("SELECT  TS.TRIPCOLLID, TS.TRIPCOLLNUMBER "
						+ "from TripCollectionSheet AS TS where TS.TRIPCOLLNUMBER = " + tripSheetId
						+ " AND TS.COMPANY_ID = " + userDetails.getCompany_id() + " AND TS.markForDelete = 0 ORDER BY TS.TRIPCOLLID DESC");

			} else if ((int) configuration.get(
					TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				query = entityManager.createQuery("SELECT  TS.TRIPDAILYID, TS.TRIPDAILYNUMBER "
						+ "from TripDailySheet AS TS where TS.TRIPDAILYNUMBER = " + tripSheetId
						+ " AND TS.COMPANY_ID = " + userDetails.getCompany_id() + " AND TS.markForDelete = 0 ORDER BY TS.TRIPDAILYID DESC");

			}

			Object[] tripPriID = null;
			try {
				query.setMaxResults(1);
				tripPriID = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			if (tripPriID != null) {

				return (Long) tripPriID[0];
			} else {

				return (long) 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
	}

	@Override
	public void transferFuelDocument(List<FuelDocument> list) throws Exception {
		org.fleetopgroup.persistence.document.FuelDocument				fuelDocument		= null;
		List<org.fleetopgroup.persistence.document.FuelDocument> 		fuelDocumentList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				fuelDocumentList	= new ArrayList<>();
				for(FuelDocument	document : list) {
					fuelDocument	= new org.fleetopgroup.persistence.document.FuelDocument();

					fuelDocument.set_id(document.getFueldocid());
					fuelDocument.setFuel_id(document.getFuel_id());
					fuelDocument.setFuel_content(document.getFuel_content());
					fuelDocument.setFuel_contentType(document.getFuel_contentType());
					fuelDocument.setFuel_filename(document.getFuel_filename());
					fuelDocument.setCompanyId(document.getCompanyId());
					fuelDocument.setCreatedById(document.getCreatedById());
					fuelDocument.setLastModifiedById(document.getLastModifiedById());
					fuelDocument.setMarkForDelete(document.isMarkForDelete());
					fuelDocument.setCreated(document.getCreated());
					fuelDocument.setLastupdated(document.getLastupdated());

					fuelDocumentList.add(fuelDocument);
				}
				mongoTemplate.insert(fuelDocumentList, org.fleetopgroup.persistence.document.FuelDocument.class);
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}

	@Override
	public long getFuelDocumentMaxId() throws Exception {
		try {
			return fuelDocumentRepository.getFuelDocumentMaxId();
		} catch (Exception e) {
			return 0;
		}
	}


	@Transactional
	public List<FuelDto> Vendor_wise_Fuel_Mileage_Report(String Query, String Multi_vendor_id,
			Integer companyId) throws Exception {

		TypedQuery<Object[]> query = null;

		query = entityManager
				.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
						+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
						+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
						+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
						+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
						+ " F.fuel_document, F.fuel_document_id, F.gpsUsageKM ,D.driver_Lastname ,D.driver_fathername , F.created"
						+ " FROM Fuel as F "
						+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
						+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
						+ " WHERE F.companyId  = " + companyId + " AND F.markForDelete = 0  "
						+ " AND F.vendor_id IN ( " + Multi_vendor_id + ") "
						+ " "+Query+" ORDER BY F.fuel_date DESC , F.fuel_meter DESC ",Object[].class);

		
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				fuel.setGpsUsageKM((Double) result[33]);
				if(result[34] != null)
				fuel.setDriver_name(fuel.getDriver_name() +" "+ result[34]);
				if(result[35] != null)
					fuel.setDriver_name(fuel.getDriver_name() +" -"+ result[35]);
				
				fuel.setCreated((dateFormat_Name.format(result[36])));
				
				Dtos.add(fuel);
			}
		}
		return Dtos;
	}



	@Override
	public List<FuelDto> getFuelConsumed(Integer did, String dateRangeFrom,
			String dateRangeTo) throws Exception {

		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT DISTINCT F.fuel_id, F.fuel_price, F.driver_id, F.fuel_TripsheetID, F.fuel_liters, F.fuel_amount, F.fuel_usage"
							+ " FROM Fuel F "
							+ " INNER JOIN TripSheet TS ON TS.vid = F.vid"
							+ " where (TS.tripFristDriverID = "+did+" OR TS.tripSecDriverID = "+did+" OR TS.tripCleanerID = "+did+") "
							+ " AND F.fuel_date BETWEEN '" + dateRangeFrom + "' AND  '"
							+ dateRangeTo + "' AND TS.tripOpenDate between '" + dateRangeFrom + "' AND  '"+dateRangeTo + "' "
							+ " AND F.markForDelete = 0 "
							+ " ORDER BY F.fuel_date desc ",	
							Object[].class);
			List<Object[]>	results = queryt.getResultList();

			List<FuelDto>	list	= null;

			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					FuelDto	fuel = new FuelDto();

					fuel.setFuel_id((long) result[0]);
					fuel.setFuel_price((double) result[1]);
					fuel.setDriver_id((Integer) result[2]);
					if(result[3] != null)
						fuel.setFuel_TripsheetID((long) result[3]);
					fuel.setFuel_liters((double) result[4]);
					fuel.setFuel_amount((double) result[5]);
					fuel.setFuel_usage((Integer) result[6]);

					list.add(fuel);
				}
			}

			return list;

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ValueObject getMonthlyVehicleWiseFuelReport(ValueObject valueInObject) throws Exception {
		ValueObject				valueOutObject				= null;
		String[] 				From_DateRange 				= null;
		String[] 				TO_DateRange 				= null;
		String dateRangeFrom = "", dateRangeTo = "", tempdateRangeTo = "";
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			From_DateRange = valueInObject.getString("startDateOfMonth", null).split("/");
			TO_DateRange = valueInObject.getString("startDateOfMonth", null).split("/");

			dateRangeFrom = From_DateRange[1]+ "-" + From_DateRange[0] + "-01";
			tempdateRangeTo = TO_DateRange[1] + "-" + TO_DateRange[0] + "-01";

			java.util.Date convertedDate = dateFormatSQL.parse(tempdateRangeTo);

			Calendar c = Calendar.getInstance();
			c.setTime(convertedDate);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

			dateRangeTo = TO_DateRange[1]+ "-"+ TO_DateRange[0] + "-"+ c.getActualMaximum(Calendar.DAY_OF_MONTH);

			TypedQuery<Object[]> typedQuery = null;
			typedQuery = entityManager.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
					+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId, "
					+ " F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id, "
					+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial, "
					+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId, "
					+ " F.fuel_document, F.fuel_document_id  FROM Fuel as F "
					+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId "
					+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
					+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id where F.fuel_date between '" + dateRangeFrom + "' AND '" + dateRangeTo +"'"
					+ " AND F.companyId = " + userDetails.getCompany_id()
					+ " AND F.markForDelete = 0  ORDER BY F.fuel_date DESC , F.fuel_meter DESC", Object[].class);

			List<Object[]> results = typedQuery.getResultList();
			List<FuelDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<FuelDto>();
				FuelDto fuel = null;
				for (Object[] result : results) {
					fuel = new FuelDto();
					fuel.setFuel_id((Long) result[0]);
					fuel.setFuel_Number((Long) result[1]);
					fuel.setFuel_TripsheetID((Long) result[2]);
					fuel.setVid((Integer) result[3]);
					fuel.setVehicle_registration((String) result[4]);
					fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
					fuel.setFuel_date(dateFormat_Name.format(result[6]));
					fuel.setFuel_D_date((Date)result[6] );
					fuel.setFuel_meter((Integer) result[7]);
					fuel.setFuel_meter_old((Integer) result[8]);
					fuel.setFuel_meter_attributes((Integer) result[9]);
					fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
					fuel.setFuel_liters((Double) result[11]);
					fuel.setFuel_price((Double) result[12]);// result[2]
					fuel.setVehicle_group((String) result[13]);
					fuel.setDriver_id((Integer) result[14]);
					fuel.setDriver_name((String) result[15]);
					fuel.setDriver_empnumber((String) result[16]);
					fuel.setVendor_id((Integer) result[17]);
					fuel.setVendor_name((String) result[18]);
					fuel.setVendor_location((String) result[19]);
					fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
					fuel.setFuel_reference((String) result[21]);
					fuel.setFuel_personal((Integer) result[22]);
					fuel.setFuel_tank((Integer) result[23]);
					fuel.setFuel_tank_partial((Integer) result[24]);
					fuel.setFuel_comments((String) result[25]);
					fuel.setFuel_usage((Integer) result[26]);
					fuel.setFuel_amount((Double) result[27]);
					
					if((Double) result[28] != null) {
						fuel.setFuel_kml(Double.parseDouble(toFixedTwo.format(result[28])));
					}else {
						fuel.setFuel_kml(0.0);
					}
					fuel.setFuel_cost((Double) result[29]);
					fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
					fuel.setFuel_document((boolean) result[31]);
					fuel.setFuel_document_id((Long) result[32]);

					Dtos.add(fuel);
				}
			}

			valueOutObject	= new ValueObject();
			valueOutObject.put("Trip", Dtos);

			return	valueOutObject;
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	@Override
	public List<FuelDto> getFuelListByVidAndDateRange(TripSheetIncomeDto incomeDto) throws Exception {
		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT F.fuel_id, F.fuel_Number, F.vid, F.fuel_date, F.fuel_amount, F.fuel_reference, F.fuel_liters "
						+ " FROM Fuel F "
						+ " where F.vid = "+incomeDto.getVid()+" AND F.fuel_date between '" + incomeDto.getFromDate() + "' AND  '"+incomeDto.getToDate() + "' "
						+ " AND F.markForDelete = 0 ",	
						Object[].class);
		List<Object[]>	results = queryt.getResultList();

		List<FuelDto>	list	= null;

		if(results != null && !results.isEmpty()) {
			list	=	new ArrayList<>();
			for (Object[] result : results) {
				FuelDto	fuel = new FuelDto();

				fuel.setFuel_id((long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setVid((Integer) result[2]);
				if(result[3] != null)
					fuel.setFuel_date((dateFormat_Name.format(result[3])));
				fuel.setFuel_amount((double) result[4]);
				if(result[5] != null) {	//latest
					fuel.setFuel_reference((String) result[5]);    
				}
				else {
					fuel.setFuel_reference("NA");
				}	
				fuel.setFuel_liters((Double) result[6]);
				list.add(fuel);
			}
		}

		return list;

	}


	//Dev Y Code Start  For Fuel Date Range Cash Or Credit Wise Report
	@Override
	public ValueObject getFuelRangeCashCreditWiseReport(ValueObject valueInObject) throws Exception {
		ValueObject				valueOutObject				= null;
		String					fromDate					= null;
		String					toDate						= null;

		Integer                 vehicleId					= null;
		Integer 				vendorId					= null;
		Short					paymentId					= null;	

		ValueObject				dateRangeObj				= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRangeObj						= DateTimeUtility.getStartAndEndDateStr(valueInObject.getString("rangeFuelMileage"));
			fromDate							= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate								= dateRangeObj.getString(DateTimeUtility.TO_DATE);
			
			fromDate = DateTimeUtility.getStrDateFromStrDate(fromDate, DateTimeUtility.DD_MM_YYYY);
			toDate   = DateTimeUtility.getStrDateFromStrDate(toDate + DateTimeUtility.DAY_END_TIME, DateTimeUtility.DD_MM_YY_HH_MM_SS);


			vehicleId = valueInObject.getInt("ReportSelectVehicle",0);
			vendorId = valueInObject.getInt("selectVendor",0);
			paymentId = valueInObject.getShort("fuel_vendor_paymode",(short)0);			

			String Vehicle_Name = "", Vendor_name = "",Fuel_vendor_paymode = "";

			if(vehicleId != 0  )
			{
				Vehicle_Name = " AND F.vid=" + valueInObject.getInt("ReportSelectVehicle") + " ";
				vehicleId = valueInObject.getInt("ReportSelectVehicle");
			}
			if(vendorId != 0)
			{
				Vendor_name = " AND F.vendor_id=" + valueInObject.getInt("selectVendor") + " ";
				vendorId  = valueInObject.getInt("selectVendor");

			}			
			if(paymentId > 0)
			{
				Fuel_vendor_paymode	= " AND F.fuel_vendor_paymodeId=" + paymentId + " ";

			}

			String query = " " + Vehicle_Name + " " + Vendor_name + " " + Fuel_vendor_paymode + " ";			

			TypedQuery<Object[]> typedQuery = null;
			typedQuery = entityManager.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_date, F.fuel_meter_old,  "
					+ " F.fuel_meter, F.fuel_usage, F.fuel_liters, F.fuel_amount, F.fuel_kml, F.fuel_cost, V.vehicle_registration, VN.vendorName,F.paymentTypeId  FROM Fuel as F  "
					+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "											          
					+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id WHERE F.fuel_date between '" + fromDate + "' AND '" + toDate +"'"
					+ " AND F.companyId = " + userDetails.getCompany_id()
					+ query + " AND F.markForDelete = 0  ORDER BY F.fuel_date DESC , F.fuel_meter DESC", Object[].class);

			List<Object[]> results = typedQuery.getResultList();
			List<FuelDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<FuelDto>();
				FuelDto fuel = null;				

				for (Object[] result : results) {
					fuel = new FuelDto();
					fuel.setFuel_id((Long) result[0]);					
					fuel.setFuel_Number((Long) result[1]);
					fuel.setFuel_date(dateFormat_Name.format(result[2]));
					fuel.setFuel_D_date((Date)result[2] );

					fuel.setFuel_meter_old((Integer) result[3]);
					fuel.setFuel_meter((Integer) result[4]);
					fuel.setFuel_usage((Integer) result[5]);
					if(result[6] != null)
					fuel.setFuel_liters(Double.parseDouble(toFixedTwo.format(result[6])));
					if(result[7] != null)
					fuel.setFuel_amount(Double.parseDouble(toFixedTwo.format(result[7])));
					if(result[8] != null)
					fuel.setFuel_kml(Double.parseDouble(toFixedTwo.format(result[8])));
					if(result[9] != null)
					fuel.setFuel_cost(Double.parseDouble(toFixedTwo.format(result[9])));	

					fuel.setVehicle_registration((String) result[10]);
					fuel.setVendor_name((String) result[11]);
					fuel.setPaymentTypeId((Short) result[12]);
					fuel.setFuel_vendor_paymode(PaymentTypeConstant.getPaymentTypeName(fuel.getPaymentTypeId()));

					Dtos.add(fuel);
				}
			}

			valueOutObject	= new ValueObject();
			valueOutObject.put("FuelReportInfo", Dtos); 

			return	valueOutObject;
		} catch (Exception e) {
			throw e;
		} finally {

		}
	} 


	@SuppressWarnings("unchecked")
	@Override
	public ValueObject getFuelEfficiencyDataReport(ValueObject valueInObject) throws Exception {
		ValueObject						    valueOutObject;
		String								fromDate					= null;
		String								toDate						= null;
		HashMap<String, TripDailySheetDto>  dtosMap 					= null;
		Integer                 			vehicleId					= null;
		Integer 							tripRouteId					= null;
		ValueObject							dateRangeObj				= null;
		Thread								fuelThread					= null;	
		Thread								dtosThread					= null;	
		List<TripDailySheetDto> 			fuel 						= null;
		List<TripDailySheetDto> 			dtos 						= null;
		HashMap<Long, TripDailySheetDto> 	finalFuelMap		 		= null;
		List<TripDailySheetDto> 			finalFuelList 				= null;

		try {
			valueOutObject	= new ValueObject();
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRangeObj						= DateTimeUtility.getStartAndEndDateStr(valueInObject.getString("rangeFuelMileage"));
			fromDate							= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate								= dateRangeObj.getString(DateTimeUtility.TO_DATE);			

			vehicleId 							= valueInObject.getInt("ReportSelectVehicle",0);
			tripRouteId 						= valueInObject.getInt("TripRouteList",0);

			String Vehicle_Name = "", Route_name = "", Date = "";

			if(vehicleId != 0  )
			{
				Vehicle_Name = " AND t.VEHICLEID= "+ vehicleId +" ";
			}

			if(tripRouteId != 0)
			{
				Route_name = " AND t.TRIP_ROUTE_ID= "+ tripRouteId +" ";

			}

			Date =  "AND t.TRIP_OPEN_DATE between '" + fromDate + "' AND '" + toDate +"' " ;
			String query = " " + Vehicle_Name + " " + Route_name + " " + Date + " ";


			fuelThread = new Thread() {
				public void run() {
					List<TripDailySheetDto> fuelList = null;
					try {
						fuelList = FuelReportDaoImpl.getFuelTripDailyList(query,userDetails.getCompany_id());
						valueOutObject.put("FuelList", fuelList);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			};
			fuelThread.start();


			dtosThread = new Thread() {
				public void run() {
					List<TripDailySheetDto> dtosList = null;
					try {
						dtosList = FuelReportDaoImpl.getFuelEfficiencyDtosReport(query, userDetails.getCompany_id());
						valueOutObject.put("DtosList", dtosList);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			};
			dtosThread.start();


			fuelThread.join();
			dtosThread.join();

			fuel = (List<TripDailySheetDto>) valueOutObject.get("FuelList");
			dtos = (List<TripDailySheetDto>) valueOutObject.get("DtosList");

			finalFuelMap = new HashMap<>();
			if(dtos != null)
				for (TripDailySheetDto i : dtos) finalFuelMap.put(i.getTRIPDAILYID(),i);

			if(fuel != null)
				FuelReportDaoImpl.getFinalMapOfFuelEfficiencyDataReport(fuel,finalFuelMap);

			if(finalFuelMap != null) {
				finalFuelList = new ArrayList<TripDailySheetDto>();
				finalFuelList.addAll(finalFuelMap.values());
			}

			if(finalFuelList != null) {
				dtosMap = getNumberOfTripsByDate(finalFuelList);
				valueOutObject.put("FuelEfficiencyDataReport", dtosMap);
			}

			return	valueOutObject;
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}


	@Override
	public HashMap<String ,TripDailySheetDto> getNumberOfTripsByDate(List<TripDailySheetDto> Dtos) throws Exception {
		try {

			HashMap<String ,TripDailySheetDto> dtosMap = null;
			if (Dtos != null && !Dtos.isEmpty()) {
				dtosMap = new HashMap<String ,TripDailySheetDto>();
				TripDailySheetDto list = null;
				for (TripDailySheetDto TripSheetDto : Dtos) {
					if(dtosMap.containsKey(TripSheetDto.getTRIP_OPEN_DATE()+"_"+TripSheetDto.getVEHICLEID())) {
						list 			= dtosMap.get(TripSheetDto.getTRIP_OPEN_DATE()+"_"+TripSheetDto.getVEHICLEID());

						if(list.getTRIP_DIESELKMPL() == null) {
							list.setTRIP_DIESELKMPL(0.0);
						}

						if(list.getTRIP_DIESEL() == null) {
							list.setTRIP_DIESEL(0.0);
						}

						if(list.getTRIP_USAGE_KM() == null) {
							list.setTRIP_USAGE_KM(0);
						}
						if(list.getFuel_amount() == null) {
							list.setFuel_amount(0.0);
						}

						list.setTRIP_SEC_DRIVER_ID(TripSheetDto.getTRIP_DRIVER_ID());
						list.setTRIP_SEC_DRIVER_NAME(TripSheetDto.getTRIP_DRIVER_NAME());
						if(TripSheetDto.getTRIP_DIESEL() != null) {
							list.setTRIP_DIESEL(list.getTRIP_DIESEL() + TripSheetDto.getTRIP_DIESEL());
						}
						if(TripSheetDto.getTRIP_USAGE_KM() != null) {
							list.setTRIP_USAGE_KM(list.getTRIP_USAGE_KM() + TripSheetDto.getTRIP_USAGE_KM());
						}
						if(TripSheetDto.getFuel_amount() != null) {
							list.setFuel_amount(list.getFuel_amount() + TripSheetDto.getFuel_amount());
						}

						if(TripSheetDto.getTRIP_DIESELKMPL() != null) {
							list.setTRIP_DIESELKMPL(list.getTRIP_DIESELKMPL() + TripSheetDto.getTRIP_DIESELKMPL());
						}
						list.setMultipleTrip(true);
					} else {

						TripSheetDto.setMultipleTrip(false);

						dtosMap.put(TripSheetDto.getTRIP_OPEN_DATE()+"_"+TripSheetDto.getVEHICLEID(),TripSheetDto);
					}
				}
			}
			return dtosMap;

		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Long getALLEmailFuelEntriesDailyWorkStatus(String startDate, String endDate,Integer companyId)
			throws Exception {

		Query queryt = entityManager.createQuery(
				"SELECT COUNT(F.fuel_id) "
						+ " From Fuel as F "
						+ " WHERE F.created between '"+startDate+"' AND '"+endDate+"' AND F.companyId = "+companyId+" AND F.markForDelete = 0 ");

		Long count = (long) 0;
		try {

			if((Long) queryt.getSingleResult() != null) {
				count = (Long) queryt.getSingleResult();
			}

		} catch (NoResultException nre) {
		}

		return count;
	}	

	//Email System for Manager Start Fuel Entries
	@Override
	public List<CompanyDto> getALLEmailFuelEntriesDailyWorkForManagers(String startDate, String endDate)
			throws Exception {

		try{
			TypedQuery<Object[]> typedQuery = null;


			typedQuery = entityManager.createQuery(
					" SELECT COUNT(F.fuel_id), C.company_id, C.company_name "
							+ " From Fuel as F "
							+ " INNER JOIN Company AS C on C.company_id = F.companyId  "
							+ " WHERE F.created between '"+startDate+"' AND '"+endDate+"'  AND F.markForDelete = 0 "
							+ " Group by F.companyId ",
							Object[].class);


			List<Object[]> results = typedQuery.getResultList();

			List<CompanyDto> fuelEntries = null;
			if (results != null && !results.isEmpty()) {

				fuelEntries = new ArrayList<CompanyDto>();
				CompanyDto list = null;
				for (Object[] result : results) {
					list = new CompanyDto();
					if(result !=null) {

						list.setTripSheetCount((long) 0);
						list.setRenewalReminderCount((long) 0);
						list.setServiceReminderCount((long) 0);													
						list.setWorkOrderCount((long) 0);
						list.setServiceEntriesCount((long) 0);

						if(result[0] != null) 							
							list.setFuelEntriesCount((long) result[0]);								
						list.setCompany_id((int) result[1]);
						list.setCompany_name((String) result[2]);
					}
					fuelEntries.add(list);
				}
			}
			return fuelEntries;
		}
		catch(Exception e){
			throw e;						
		}			
	}	
	//Email System for Manager Stop Fuel Entries+


	@Override
	public ValueObject getdateWiseFuelEntryDetailsReport(ValueObject valueObject) throws Exception {

		Integer						vehicleId				= 0;
		String						dateRange				= "";
		CustomUserDetails			userDetails				= null;
		ValueObject					tableConfig				= null;
		List<FuelDto> 				fuelDtoList			   	= null;	
		String 						query					= "";
		int 						driverId				= 0 ;
		int 						secDriverId				= 0 ;				
		int 						routeId					= 0 ;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			vehicleId						= valueObject.getInt("vehicleId", 0);
			dateRange						= valueObject.getString("date");
			driverId						= valueObject.getInt("driverId", 0);
			secDriverId						= valueObject.getInt("secDriverId", 0);
			routeId							= valueObject.getInt("routeId", 0);

			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				String Driver_Name = "", Route = "" , Vehicle = "",Date = "", SEC_DRIVER ="";

				if(driverId > 0 )
				{
					Driver_Name = " AND D.driver_id = "+ driverId +" ";
				}
				if(routeId > 0 )
				{
					Route = " AND  T.routeID = "+ routeId +" ";
				}
				if(vehicleId > 0 )
				{
					Vehicle = " AND  F.vid = "+ vehicleId +" ";
				}
				if(secDriverId > 0 )
				{
					SEC_DRIVER = " AND  D1.driver_id = "+ secDriverId +" ";
				}

				Date		=	" AND F.fuel_date between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;

				query       = " " + Driver_Name + " " + SEC_DRIVER+ " " + Route + " "+ Vehicle +" "+ Date+" ";
			}	

			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.DATE_WISE_FUEL_ENTRY_DATA_FILE_PATH);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig); 
			fuelDtoList		= FuelReportDaoImpl.getFuelEntryDetails_Report(query,userDetails.getCompany_id());

			if(driverId != 0 ) {
				if(fuelDtoList != null)
					valueObject.put("DriverKey", fuelDtoList.get(0).getDriver_name());
			} else {
				valueObject.put("DriverKey", "All");
			}

			if(secDriverId != 0 ) {
				if(fuelDtoList != null)
					valueObject.put("SecDriverKey", fuelDtoList.get(0).getFuelSecDriverName());
			} else {
				valueObject.put("SecDriverKey", "All");
			}

			if(routeId != 0 ) {
				if(fuelDtoList != null)
					valueObject.put("RouteKey", fuelDtoList.get(0).getFuelRouteName());
			} else {
				valueObject.put("RouteKey", "All");
			}

			if(vehicleId > 0) {
				if(fuelDtoList != null)
					valueObject.put("VehicleKey", fuelDtoList.get(0).getVehicle_registration());
			} else {
				valueObject.put("VehicleKey", "All");
			}

			valueObject.put("fuelEntryDetails", fuelDtoList);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;			
		}
	}	
	
	@Override
	public ValueObject getallVehiclesMileageReport(ValueObject valueObject) throws Exception {


		Integer						vehicleId				= 0;
		String						dateRange				= "";
		CustomUserDetails			userDetails				= null;
		List<FuelDto> 				fuelDtoList			   	= null;	
		String 						query					= "";
		int 						groupId					= 0;
		ValueObject					tableConfig				= null;
		
		
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			vehicleId						= valueObject.getInt("vehicleId", 0);
			groupId							= valueObject.getInt("group", 0);
			dateRange						= valueObject.getString("date");
		

			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");
				
				dateRangeFrom =dateFormatSQL.format(dateFormat.parse(From_TO_DateRange[0]));
				
				dateRangeTo =dateFormatSQL.format(dateFormat.parse(From_TO_DateRange[1]))+ DateTimeUtility.DAY_END_TIME;
				
				String  Vehicle = "",Date = "",Group = "";

			
				if(vehicleId > 0 )
				{
					Vehicle = " AND  F.vid = "+ vehicleId +" ";
				}
				if(groupId > 0) {
					
					Group = "AND VG.gid ="+ groupId +" ";
				}
				

				Date		=	" F.fuel_date between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;

				query       = " " + Date +" "+ Vehicle +" " + Group +" ";
			}	

			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_VEHICLE_MILEAGE_REPORT);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

			fuelDtoList		= allVehicleWiseMileage_Report(userDetails.getCompany_id(),query);
	
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("fuelEntryDetails", fuelDtoList);
			
			valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
		

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;			
		}
	}	


	@Override
	public ValueObject creationDateWiseFuelEntryReport(ValueObject valueObject) throws Exception {		
		Integer						vehicleId				= 0;
		String						dateRange					= "";
		CustomUserDetails			userDetails				= null;
		ValueObject					tableConfig				= null;
		List<FuelDto> 				fuelDtoList			   	= null;	
		String 						query					= "";
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			vehicleId						= valueObject.getInt("vehicleId", 0);
			dateRange							= valueObject.getString("date");

			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				if( vehicleId <= 0) {
					query 		= " AND F.created between '"+dateRangeFrom+"' AND '"+dateRangeTo+"' ";
				}else {
					query 		= " AND F.created between '"+dateRangeFrom+"'AND '"+dateRangeTo+"' AND F.vid = '"+vehicleId+"' ";
				}
			}	

			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.CREATION_DATE_WISE_FUEL_ENTRY_DATA_FILE_PATH);

			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);		

			fuelDtoList		= FuelReportDaoImpl.getFuelEntryDetails_Report(query,userDetails.getCompany_id());
			valueObject.put("fuelEntryDetails", fuelDtoList);

			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;			
		}
	}

	@Override
	public ValueObject getUserWiseFuelEntryDetailsReport(ValueObject valueObject) throws Exception {		
		Integer						userId					= 0;
		String						dateRange				= "";
		CustomUserDetails			userDetails				= null;
		ValueObject					tableConfig				= null;
		List<FuelDto> 				fuelDtoList			   	= null;	
		String 						query					= "";
		String 						dateRangeFrom 			= "";
		String						dateRangeTo 			= "";
		String[] 					dateRangeArr	 		= null;
		HashMap<String, Object> 		companyConfiguration		= null;
		String 						vehicleWiseGrpData     = "";
		
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			companyConfiguration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			userId							= valueObject.getInt("userId", 0);
			dateRange						= valueObject.getString("date");

			dateRangeArr = dateRange.split(" to ");
			if(dateRangeArr != null) {
				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(dateRangeArr[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(dateRangeArr[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			}
			query 		= " F.fuel_date between '"+dateRangeFrom+"'AND '"+dateRangeTo+"'  ";
			
			if(userId > 0) {
				query += "	AND F.createdById = "+userId+" ";
			}
			if(valueObject.getInt("branchId") > 0) {
				query += "	AND U.branch_id = "+valueObject.getInt("branchId")+" ";
			}

			if((boolean) companyConfiguration.get("vehicleGroupWisePermission")) {
				vehicleWiseGrpData  = " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = U.user_id ";
			}
			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.USER_WISE_FUEL_ENTRY_DATA_FILE_PATH);

			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);		

			fuelDtoList		= FuelReportDaoImpl.getUserWiseFuelEntryDetails_Report(query,userDetails.getCompany_id(),vehicleWiseGrpData);
			valueObject.put("fuelEntryDetails", fuelDtoList);

			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;			
		}
	}

	@Override
	@Transactional
	public ValueObject saveFuelEntryDetails(ValueObject valueObject, MultipartFile file) throws Exception {
			
			CustomUserDetails				userDetails					= null;
			HashMap<String, Object> 		gpsConfiguration			= null;
			ValueObject						valObjProperty				= null;
			VehicleDto 						CheckVehicleStatus			= null;
			FuelSequenceCounter 			sequenceCounter 			= null;
			List<ServiceReminderDto>		serviceList					= null;
			boolean 						FuelValidateStatus 			= false;
			int								companyId					= 0;
			long							userId						= 0;
			String							base64						= null;
			String							imageName					= null;
			String							imageExt					= null;
			Double							gpsUsageKM					= 0.0;
			ValueObject						ownerShipObject				= null;
			boolean							creatingBackDateFuel		= false;
			int								fuelUsage					= 0;
			HashMap<String, Object> 		configuration				= null;
			FuelDto							fuelDto						= null;
			FuelStockDetails				fuelStockDetails			= null;
			ValueObject						fuelInvoiceObject			= null;
			FuelInvoice						fuelInvoice					= null;
			FuelInvoiceHistory				fuelInvoiceHistory			= null;
			HashMap<String, Object> 		companyConfiguration		= null;
			HashMap<String, Object> 		tripConfiguration			= null;
			try {
				
				companyId					= valueObject.getInt("companyId",0);
				userId						= valueObject.getLong("userId",0);
				base64						= valueObject.getString("base64String",null);
				imageName					= valueObject.getString("imageName",null);
				imageExt					= valueObject.getString("imageExt",null);
				creatingBackDateFuel		= valueObject.getBoolean("creatingBackDateFuel",false);

				gpsConfiguration			= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.VEHICLE_GPS_CONFIG);
				configuration				= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
				companyConfiguration		= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				tripConfiguration			= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			
				userDetails  =  new CustomUserDetails(companyId, userId);
				
				valObjProperty = new ValueObject();
				valObjProperty = getFuelEntriesResetProperties(companyId);

				valueObject.put("ResetAllFuelEntries", valObjProperty.get("ResetAllFuelEntries"));
				valueObject.put("ResetFuelVehicle", valObjProperty.get("ResetFuelVehicle"));
				valueObject.put("ResetFuelDate", valObjProperty.get("ResetFuelDate"));
				valueObject.put("ResetFuelOdometer", valObjProperty.get("ResetFuelOdometer"));
				valueObject.put("ResetFuelType", valObjProperty.get("ResetFuelType"));
				valueObject.put("ResetFuelVendor", valObjProperty.get("ResetFuelVendor"));
				valueObject.put("ResetFuelLiter", valObjProperty.get("ResetFuelLiter"));
				valueObject.put("ResetFuelPrice", valObjProperty.get("ResetFuelPrice"));
				valueObject.put("ResetFuelReference", valObjProperty.get("ResetFuelReference"));
				valueObject.put("ResetFuelDriver", valObjProperty.get("ResetFuelDriver"));
				valueObject.put("ResetFuelDriverTwo", valObjProperty.get("ResetFuelDriverTwo"));
				valueObject.put("ResetFuelCleaner", valObjProperty.get("ResetFuelCleaner"));
				valueObject.put("ResetFuelRoute", valObjProperty.get("ResetFuelRoute"));
				valueObject.put("ResetFuelImage", valObjProperty.get("ResetFuelImage"));
				valueObject.put("ResetFuelComment", valObjProperty.get("ResetFuelComment"));
				valueObject.put("userDetails", userDetails);
				valueObject.put("configuration", configuration);
							
				
				if(!(boolean) configuration.getOrDefault("getStockFromInventory", false) &&valueObject.getShort("ownPetrolPump") == 1 && valueObject.getInt("selectVendor",0) > 0) {
					valueObject =	getFuelStockDetailsForFuelEntry(valueObject,companyId);
				}

				if((boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
					FuelValidateStatus	= true;
				}

				CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("FuelSelectVehicle"), companyId);
				if(CheckVehicleStatus!=null){
					if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE || CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE || CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
						FuelValidateStatus = true;
					}
				}
				if (FuelValidateStatus) {
					List<Fuel> validate = null;
					if(valueObject.getString("fuel_reference", null) != null && (Boolean)configuration.get("validateReference")) {
						validate = listFuelValidate(valueObject.getInt("FuelSelectVehicle", 0), valueObject.getInt("fuel_meter", 0),
								valueObject.getDouble("fuel_liters", 0), valueObject.getDouble("fuel_price", 0), valueObject.getString("selectVendor", null),
								valueObject.getString("fuel_reference", null), companyId);
					}

					if (validate != null && !validate.isEmpty()) {
						valueObject.put("alreadyExist", true);
						valueObject.put("accessToken", Utility.getUniqueToken(request));
						return valueObject;

					} else {
						fuelDto				 = FuBL.getUpdateFuelDto(valueObject);
						valueObject	=	getFuelDto(fuelDto, valueObject);
						Fuel 	fuel = (Fuel) valueObject.get("fuel");
						
						if(fuel.getFuel_TripsheetID() != null && fuel.getFuel_TripsheetID() > 0) {
							TripSheetDto trip = tripSheetService.getTripSheetLimitedDetails(fuel.getFuel_TripsheetID());
							if(trip == null || !trip.getVid().equals(fuel.getVid())) {
								fuel.setFuel_TripsheetID((long) 0);
							}
						}
						
						if ((boolean) configuration.get("validateUniqueReference") && fuel.getVendor_id() > 0) {
							String fromDate = DateTimeUtility.getFirstDayOfFiscalYear(fuel.getFuel_date(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
							String endDate	= DateTimeUtility.getLastDayOfFiscalYear(fuel.getFuel_date(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
							
							List<FuelDto>	refValidate = validateFuelReferenceInFinancailYear(fromDate, endDate, fuel.getFuel_reference(), fuel.getVendor_id());
							
							if (refValidate != null && !refValidate.isEmpty()) {
								valueObject.put("refAlreadyExist", true);
								valueObject.put("fuelNumber", refValidate.get(0).getFuel_Number());
								valueObject.put("accessToken", Utility.getUniqueToken(request));
								return valueObject;
							}
						}
						if(!(boolean) configuration.get("considerFirstEntry")) {
							Long	count = checkVehicleFirstFuelEntry(fuel.getVid());
							if(count == 0) {
								fuel.setFuel_meter_old(fuel.getFuel_meter());
								fuel.setFuel_usage(0);
							}
						}
						
						if((boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION) && CheckVehicleStatus.getGpsVendorId() != null && CheckVehicleStatus.getGpsVendorId() > 0) {
								try {
									FuelValidateStatus	= true;
									String fuelDate = formatSQL.format(fuel.getFuelDateTime());
									FuelDto preFuel = getVehicleLastFuelDetailsByVidDate(fuelDate, fuel.getVid());
									if(preFuel != null) {
										try {
											gpsUsageKM	=	vehicleGPSDetailsService.getGPSRunKMBetweenTwoDates(fuel.getVid(),preFuel.getFuelDateTimeStr(), fuelDate , companyId);
										} catch (Exception e) {
											System.err.println("Exception while getting gps run km fuel "+e);
										}
										
										fuel.setGpsUsageKM(gpsUsageKM);
									}
								} catch (Exception e) {
									
								}
								
						}
						sequenceCounter = fuelSequenceService.findNextFuelNumber(companyId);
						if (sequenceCounter == null) {
							valueObject.put("contactAdmin", true);
							valueObject.put("accessToken", Utility.getUniqueToken(request));
							return valueObject;
						}
						fuel.setFuel_Number(sequenceCounter.getNextVal());

						if(file != null) {
							if (!file.isEmpty()) {
								fuel.setFuel_document(true);
							} else {
								fuel.setFuel_document(false);
							}
						} else {
							if (base64 != null) {
								fuel.setFuel_document(true);
							} else {
								fuel.setFuel_document(false);
							}
						}

						addFuel(fuel); // Note: Save Fuel Entries Details
						
						try {
							
							if(fuel.getFuel_TripsheetID() != null && fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH && fuel.getFuel_TripsheetID() > 0 && fuel.getFuel_amount() >0)
							{
								
									TripSheet trip = tripSheetService.getTripSheetData(fuel.getFuel_TripsheetID(), fuel.getCompanyId());
									
									Double driverBalance=null;
									if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED && (boolean)tripConfiguration.get("addInDriverbalanceAfterTripClose")) {
										driverBalance = trip.getDriverBalance();
										if(driverBalance <0) {
											driverBalance = driverBalance - fuel.getFuel_amount();
										}
										if(driverBalance>0) {
											driverBalance = driverBalance - fuel.getFuel_amount();
										}
										tripSheetService.updateDriverBalance(trip.getTripSheetID(),driverBalance,companyId);
									}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if ((boolean) tripConfiguration.getOrDefault("saveDriverLedgerDetails", false) && fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
							ValueObject driveLedgerObj = new ValueObject();

							driveLedgerObj.put("companyId", companyId);
							driveLedgerObj.put("userId", userId);
							driveLedgerObj.put("driverId", fuel.getDriver_id());
							driveLedgerObj.put("amount", fuel.getFuel_amount());
							driveLedgerObj.put("transactionId", fuel.getFuel_TripsheetID());
							driveLedgerObj.put("txnType", DriverLedgerTypeConstant.FUEL_ENTRY_EDIT);
							driveLedgerObj.put("subTransactionId", fuel.getFuel_id());
							driveLedgerObj.put("description", "Fuel Entry Done On Vehicle : " + CheckVehicleStatus.getVehicle_registration()
									+ " For Fuel Number : " + fuel.getFuel_Number());

							driverLedgerService.addDriverLedgerDetails(driveLedgerObj);
						}
						
						if(valueObject.getShort("ownPetrolPump") == 1 && valueObject.getInt("selectVendor",0) > 0) {
							if((boolean) configuration.getOrDefault("getStockFromInventory", false)) {
								fuelStockDetails	=	fuelInvoiceService.getFuelStockDetailsByPetrolPumpId(valueObject.getInt("selectVendor",0), companyId);
							}else {
								fuelStockDetails = (FuelStockDetails) valueObject.get("fuelStockDetails");
							}
							
							if(valueObject.getBoolean("getStockFromInventoryConfig",false)) {
								fuelInvoiceObject = new ValueObject();
								
								fuelInvoice					= fuelInvoiceService.getFuelInvoiceDetailsByFuelInvoiceId(valueObject.getLong("fuelInvoiceId"), valueObject.getInt("companyId"));
								fuelInvoiceHistory			= FuelInvoiceBL.prepareFuelInvoiceHistory(fuelInvoice);
								fuelInvoiceHistoryRepository.save(fuelInvoiceHistory);
								
								fuelInvoiceObject.put("stockTypeId", FuelInvoiceConstant.STOCK_TYPE_FE_CREATE);
								fuelInvoiceObject.put("updatedStock", fuel.getFuel_liters());
								fuelInvoiceObject.put("fuelInvoiceId", valueObject.getLong("fuelInvoiceId"));
								fuelInvoiceObject.put("remark", FuelInvoiceConstant.getStockType(FuelInvoiceConstant.STOCK_TYPE_FE_CREATE));
								
								fuelInvoiceService.updateFuelStockDetailsInFuelInvoice(fuelInvoiceObject);	
							}
							if(fuelStockDetails != null && fuel != null) {
								fuelInvoiceService.updateFuelStockDetails(valueObject.getInt("selectVendor",0), fuelStockDetails.getAvgFuelRate(), - fuel.getFuel_liters(), - (fuelStockDetails.getAvgFuelRate()*fuel.getFuel_liters()));
							}

						}
						if(fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && fuel.getFuel_amount() > 0) {
							PendingVendorPayment	payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForFuel(fuel);
							pendingVendorPaymentService.savePendingVendorPayment(payment);
						}
						if(!valueObject.getBoolean("paidByBranch"))
						{
							
							if ((boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails",false)) {
								
									ValueObject bankPaymentValueObject= JsonConvertor
											.toValueObjectFormSimpleJsonString(valueObject.getString("bankPaymentDetails"));;
									
									bankPaymentValueObject.put("currentPaymentTypeId",fuel.getPaymentTypeId() );
									bankPaymentValueObject.put("userId", userDetails.getId());
									bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
									bankPaymentValueObject.put("moduleId",fuel.getFuel_id());
									bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.FUEL_ENTRY);
									bankPaymentValueObject.put("moduleNo", fuel.getFuel_Number());
									bankPaymentValueObject.put("amount",fuel.getFuel_amount());
									bankPaymentValueObject.put("paidDate", fuel.getFuel_date());
									bankPaymentValueObject.put("remark", "Fuel Entry Done On Vehicle "+CheckVehicleStatus.getVehicle_registration());
									
									if ((fuel.getFuel_TripsheetID() == null || fuel.getFuel_TripsheetID() <= 0)) {
										
										if(fuel.getPaymentTypeId()  == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
											cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
										}
									}
									if(fuel.getPaymentTypeId()  != PaymentTypeConstant.PAYMENT_TYPE_CASH && fuel.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT){
										bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
									}
							}
							
						}
						else
						{	
							if ((boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails",false)) {
								

									ValueObject bankPaymentValueObject=new ValueObject();
									bankPaymentValueObject.put("currentPaymentTypeId",fuel.getPaymentTypeId() );
									bankPaymentValueObject.put("userId", userDetails.getId());
									bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
									bankPaymentValueObject.put("moduleId",fuel.getFuel_id());
									bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.FUEL_ENTRY);
									bankPaymentValueObject.put("moduleNo", fuel.getFuel_Number());
									bankPaymentValueObject.put("amount",fuel.getFuel_amount());
									bankPaymentValueObject.put("paidDate", fuel.getFuel_date());
									bankPaymentValueObject.put("remark", "Fuel Entry Done On Vehicle "+CheckVehicleStatus.getVehicle_registration());
									bankPaymentValueObject.put("paidByBranchId", fuel.getPaidByBranchId());
									bankPaymentValueObject.put("paidByBranch", valueObject.getBoolean("paidByBranch"));
									
									if(fuel.getPaymentTypeId()  == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
										cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
									}
								
									if(fuel.getPaymentTypeId()  != PaymentTypeConstant.PAYMENT_TYPE_CASH && fuel.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT){
										bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
									}
								}
							
						}
						
						ValueObject	object	= new ValueObject();
						object.put("fuel", fuel);
						valueObject.put("fuel", fuel);

						valueObject.put("SuccessNum", fuel.getFuel_Number());
						valueObject.put("SuccessId", fuel.getFuel_id());
						
						tyreUsageHistoryService.saveFuelTyreUsageHistory(valueObject);
						
						if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED && fuel.getFuel_amount() > 0){
							ownerShipObject	= new ValueObject();
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, fuel.getFuel_id());
							ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, fuel.getVid());
							ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, fuel.getFuel_amount());
							ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
							ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_FUEL_ENTRY);
							ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, fuel.getCompanyId());
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, fuel.getFuel_date());
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Fuel Entry");
							ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Fuel Number : "+fuel.getFuel_Number());
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, fuel.getFuel_comments());
							ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, fuel.getCreatedById());
							ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -fuel.getFuel_amount());
							
							VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
							vehicleAgentTxnCheckerService.save(agentTxnChecker);
							
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
							
						}
					ValueObject	plObj	=	saveVehicleProfitAndLossTxnChecker(object);
						if(file != null) {
							if (!file.isEmpty()) {
								saveFuelDocument(fuel, file);
							}
						} else {
							if (base64 != null) {
								byte[] bytes = ByteConvertor.base64ToByte(base64);
								saveFuelDocumentFromMob(fuel, bytes ,imageName , imageExt);
							}
						}
						Vehicle	vehicle	= vehicleService.getVehicel(fuel.getVid(), companyId);

						if (vehicle.getVehicle_Odometer() < valueObject.getInt("fuel_meter")) {
							
							if(!creatingBackDateFuel) {
								try {
									vehicleService.updateCurrentOdoMeter(fuel.getVid(), fuel.getFuel_meter(), companyId);
									ServiceReminderService.updateCurrentOdometerToServiceReminder(fuel.getVid(),fuel.getFuel_meter(), companyId);

									serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(fuel.getVid(), companyId, userId);
									if(serviceList != null) {
										for(ServiceReminderDto list : serviceList) {

											if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {

												ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
											}
										}
									}
									
								} catch (Exception e) {
									System.err.println("Exception SR "+e);
								}
								
							}
							VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToFuel(fuel);
							vehicleUpdateHistory.setCompanyId(companyId);
							vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						}else if(vehicle.getVehicle_Odometer() == valueObject.getInt("fuel_meter")) {
							VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToFuel(fuel);
							vehicleUpdateHistory.setCompanyId(companyId);
							vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						}

						
						if(fuel.getFuel_TripsheetID() != null && fuel.getFuel_TripsheetID() > 0) {
							
							TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(valueObject.getLong("tripSheetId",0));
							TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
							
							TripSheet tsheet = new TripSheet();
							tsheet.setTripSheetID(fuel.getFuel_TripsheetID());

							if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH && !valueObject.getBoolean("paidByBranch")) {

								UserProfileDto Orderingname = userProfileService
										.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);

								TripSheetExpense TripExpense = new TripSheetExpense();

								TripExpense.setExpenseId((int) fuel.getFuelTypeId());
								TripExpense.setExpenseAmount(fuel.getFuel_amount());
								TripExpense.setExpensePlaceId(Orderingname.getBranch_id());
								TripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_FUEL);
								TripExpense.setCreatedById(userId);
								TripExpense.setTripsheet(tsheet);
								TripExpense.setFuel_id(fuel.getFuel_id());
								java.util.Date currentDateUpdate = new java.util.Date();
								Timestamp toDateTime = new java.sql.Timestamp(currentDateUpdate.getTime());

								TripExpense.setCreated(toDateTime);
								TripExpense.setCompanyId(companyId);
								TripExpense.setBalanceAmount(TripExpense.getExpenseAmount());

								tripSheetService.addTripSheetExpense(TripExpense);

								tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(fuel.getFuel_TripsheetID(), valueObject.getInt("companyId"));

								tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
								
							}
						}
						
						if(valueObject.getBoolean("nextFuelAvailable",false)  && fuel.getFuel_meter_attributes() != 1) {
							Fuel nextFuel 		= (Fuel) valueObject.get("nextFuel");
							if(nextFuel != null) {
									fuelUsage			= nextFuel.getFuel_meter() - fuel.getFuel_meter();
									java.util.Date currentDateUpdate 	= new java.util.Date();
									Timestamp 	   updatedDate 			= new java.sql.Timestamp(currentDateUpdate.getTime());
									
									if(fuel.getFuel_tank() == 0) {
										Double nextKMPL = fuelUsage/nextFuel.getFuel_liters();
										Double nextCost = nextFuel.getFuel_amount() / fuelUsage;
										updateNextFuelCostKmpl(nextFuel.getFuel_id(), nextCost ,nextKMPL,
												fuelUsage, userDetails.getId(), updatedDate, fuel.getFuel_meter());
							
									
									}else {
										updateNextFuelCostKmpl(nextFuel.getFuel_id(), valueObject.getDouble("nextCost",0), valueObject.getDouble("nextKmpl",0),
												fuelUsage, userDetails.getId(), updatedDate, fuel.getFuel_meter());
									
									}
									
						}
					}
						if(plObj != null && plObj.containsKey(VehicleProfitAndLossDto.TXN_CHECKER_ID)) {
							new Thread() {
								public void run() {
									try {
										vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(plObj);
										vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(plObj);
										vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(plObj);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}		
							}.start();
						}
						
						if(ownerShipObject != null){
							vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
						}
					}
				} else {
					String Link = "";
					if (valueObject.getInt("fuel_meter") > CheckVehicleStatus.getVehicle_ExpectedOdameter()) {

						Link += " Maxminum Allow Odometer " + CheckVehicleStatus.getVehicle_ExpectedOdameter()
						+ " kindly Check Odometer ";
						valueObject.put("odoMeter", Link);
						valueObject.put("accessToken", Utility.getUniqueToken(request));
					}

					String TIDMandatory = "";
					TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
							+ CheckVehicleStatus.getVehicle_Status() + " Status you can't create Fuel Entires " + " " + Link
							+ "" + " </span> ,";
					valueObject.put("vehicleStatus", TIDMandatory);
					valueObject.put("accessToken", Utility.getUniqueToken(request));
				}
				
				valueObject.put("configuration", configuration);
				

				return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
				gpsConfiguration			= null;
				valObjProperty				= null;
				CheckVehicleStatus			= null;
				sequenceCounter 			= null;
				serviceList					= null;
				FuelValidateStatus 			= false;
				companyId					= 0;
				userId						= 0;
				base64						= null;
				imageName					= null;
				imageExt					= null;
				gpsUsageKM					= 0.0;
				ownerShipObject				= null;
				creatingBackDateFuel		= false;
				fuelUsage					= 0;
			}
			

		
	}

	@Override
	public ValueObject saveVehicleProfitAndLossTxnChecker(ValueObject valueObject) throws Exception {
		Fuel							fuel						= null;
		try {
			fuel					= (Fuel) valueObject.get("fuel");
			ValueObject		object	= new ValueObject();

			object.put(VehicleProfitAndLossDto.COMPANY_ID, fuel.getCompanyId());
			object.put(VehicleProfitAndLossDto.TRANSACTION_ID, fuel.getFuel_id());
			object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
			object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
			object.put(VehicleProfitAndLossDto.COMPANY_ID, fuel.getCompanyId());
			object.put(VehicleProfitAndLossDto.TRANSACTION_VID, fuel.getVid());
			object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, fuel.getFuel_id());
			VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);

			vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);

			ValueObject		valObj	= new ValueObject();
			CustomUserDetails	userDetails = new CustomUserDetails();
			userDetails.setCompany_id(fuel.getCompanyId());
			valObj.put("fuel", fuel);
			valObj.put("userDetails", userDetails);
			valObj.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
			valObj.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
			valObj.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
			
			return valObj;

		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void saveFuelDocument(Fuel fuel, MultipartFile file) throws Exception {
		try {

			org.fleetopgroup.persistence.document.FuelDocument fuelDoc = new org.fleetopgroup.persistence.document.FuelDocument();
			fuelDoc.setFuel_id(fuel.getFuel_id());
			byte[] bytes = file.getBytes();
			fuelDoc.setFuel_filename(file.getOriginalFilename());
			fuelDoc.setFuel_content(bytes);
			fuelDoc.setFuel_contentType(file.getContentType());
			fuelDoc.setMarkForDelete(false);
			fuelDoc.setCreatedById(fuel.getCreatedById());
			fuelDoc.setLastModifiedById(fuel.getCreatedById());

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			fuelDoc.setCreated(toDate);
			fuelDoc.setLastupdated(toDate);
			fuelDoc.setCompanyId(fuel.getCompanyId());

			// Note: Save FuelDocument Details
			add_Fuel_To_FuelDocument(fuelDoc);

			// Note: FuelDocument to Save id to Fuel save
			Update_FuelDocument_ID_to_Fuel(fuelDoc.get_id(), true, fuel.getFuel_id(),
					fuel.getCompanyId());

		} catch (Exception e) {
			throw e;
		}
	}

	public void saveFuelDocumentFromMob(Fuel fuel, byte[] bytes , String imageName , String imageExt) throws Exception {
		try {

			org.fleetopgroup.persistence.document.FuelDocument fuelDoc = new org.fleetopgroup.persistence.document.FuelDocument();
			fuelDoc.setFuel_id(fuel.getFuel_id());
			fuelDoc.setFuel_filename(imageName);
			fuelDoc.setFuel_content(bytes);
			fuelDoc.setFuel_contentType(imageExt);
			fuelDoc.setMarkForDelete(false);
			fuelDoc.setCreatedById(fuel.getCreatedById());
			fuelDoc.setLastModifiedById(fuel.getCreatedById());

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			fuelDoc.setCreated(toDate);
			fuelDoc.setLastupdated(toDate);
			fuelDoc.setCompanyId(fuel.getCompanyId());

			// Note: Save FuelDocument Details
			add_Fuel_To_FuelDocument(fuelDoc);

			// Note: FuelDocument to Save id to Fuel save
			Update_FuelDocument_ID_to_Fuel(fuelDoc.get_id(), true, fuel.getFuel_id(),
					fuel.getCompanyId());

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Fuel saveFuel(ValueObject valueObject) throws Exception {

		int 	vid 					= valueObject.getInt("FuelSelectVehicle",0);
		short 	fuelTypeId 				= valueObject.getShort("fuel_type", (short) 1);
		short 	paymentTypeId 			= valueObject.getShort("paymentTypeId");
		int 	fuelMeter 				= valueObject.getInt("fuel_meter", 0);
		double	gpsOdometer 			= valueObject.getDouble("gpsOdometer", 0);
		int 	fuelMeterOld 			= valueObject.getInt("fuel_meter_old", 0);
		int		fuelTank				= valueObject.getInt("fuel_tank", 0);
		int 	firstDriver 			= valueObject.getInt("VehicleTODriverFuel",0);
		int 	secondDriver			= valueObject.getInt("VehicleTODriver2Fuel",0);
		int 	cleaner 				= valueObject.getInt("VehicleTOCleanerFuel",0);
		int 	routeId 				= valueObject.getInt("TripRouteList",0);
		Long	tripSheetNumber			= valueObject.getLong("fuel_TripsheetNumber", 0);
		Long	tripSheetId				= valueObject.getLong("tripSheetId", 0);

		Fuel fuel = new Fuel();
		fuel.setVid(vid);
		fuel.setFuelTypeId(fuelTypeId);
		fuel.setFuel_meter(fuelMeter);
		fuel.setGpsOdometer(gpsOdometer);
		fuel.setFuel_document_id((long) 0);
		fuel.setTallyCompanyId(valueObject.getLong("tallyCompanyId", 0));

		if(tripSheetId != null && tripSheetId > 0) {
			fuel.setFuel_TripsheetID(tripSheetId);
		}else {
			if (tripSheetNumber != null && tripSheetNumber > 0) {
				fuel.setFuel_TripsheetID(getTripSheetIdByNumber(tripSheetNumber));
			} else {
				fuel.setFuel_TripsheetID((long) 0);
			}
		}
		if(valueObject.getString("fuelTime", null) != null && !valueObject.getString("fuelTime", null).trim().equalsIgnoreCase("")) {
			String start_time = "00:00";
			if(valueObject.getString("fuelTime", null) != null) {
				start_time	= valueObject.getString("fuelTime");
				if(start_time.length() < 5) {
					start_time = "0"+start_time;
				}
			}
			
			java.util.Date fuelDateTime = DateTimeUtility.getDateTimeFromDateTimeString(valueObject.getString("fuelDate"), start_time);
			fuel.setFuelDateTime(fuelDateTime);
		}
		java.util.Date date = dateFormat.parse(valueObject.getString("fuelDate"));
		java.sql.Date fuelDate = new java.sql.Date(date.getTime());
		fuel.setFuel_date(fuelDate);


		fuel.setPaymentTypeId(paymentTypeId);
		if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
			fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
			java.util.Date currentDate  = new java.util.Date();
			Timestamp 	   toDate 		= new java.sql.Timestamp(currentDate.getTime());
			fuel.setFuel_vendor_paymentdate(toDate);
		} else {
			fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
		}

		if (valueObject.getString("selectVendor") != null
				&& FuelDto.getParseVendorID_STRING_TO_ID(valueObject.getString("selectVendor")) != 0) {
			fuel.setVendor_id(FuelDto.getParseVendorID_STRING_TO_ID(valueObject.getString("selectVendor")));
		} else {

			fuel.setVendor_id(saveFuelEntriesToNewVendorCreateAuto(valueObject.getString("selectVendor"), valueObject.getInt("companyId"),valueObject.getLong("userId")));
		}

		if (fuelMeterOld < fuelMeter) {
			fuel = fuelMeterGreaterThanFuelMeterOld(valueObject, fuel);
		} else {
			fuel = fuelMeterLessThanFuelMeterOld(valueObject, fuel);
		}

		fuel.setFuel_tank_partial(fuelTank);
		fuel.setFuel_reference(valueObject.getString("fuel_reference"));

		double fuelAmt = fuel.getFuel_amount();
		fuel.setBalanceAmount(fuelAmt);

		if (firstDriver > 0) { 
			fuel.setDriver_id(firstDriver);
		} else {
			fuel.setDriver_id(0);
		}

		if(secondDriver > 0) {
			fuel.setSecDriverID(secondDriver);
		}else {
			fuel.setSecDriverID(0);
		}

		if(cleaner> 0) {
			fuel.setCleanerID(cleaner);
		}else {
			fuel.setCleanerID(0);
		}

		if(routeId > 0) {
			fuel.setRouteID(routeId);
		}else {
			fuel.setRouteID(0);
		}

		fuel.setFuel_personal(valueObject.getInt("fuel_personal", 0));
		fuel.setFuel_comments(valueObject.getString("fuel_comments"));
		fuel.setLatitude(valueObject.getString("latitude",null));
		fuel.setLongitude(valueObject.getString("longitude",null));

		return fuel;

	}

	@Override
	public Fuel fuelMeterGreaterThanFuelMeterOld(ValueObject valueObject, Fuel fuel) throws Exception {

		int 		vid 				= 	valueObject.getInt("FuelSelectVehicle");
		int 		fuelMeter 			= 	valueObject.getInt("fuel_meter");
		int 		fuelMeterOld	 	= 	valueObject.getInt("fuel_meter_old");
		double 		fuelLiters 			= 	valueObject.getDouble("fuel_liters");
		double 		fuelPrice 			= 	valueObject.getDouble("fuel_price");
		int			fuelTank			= 	valueObject.getInt("fuel_tank");
		int 		fuelMeterAttributes =	valueObject.getInt("fuel_meter_attributes");


		fuel.setFuel_meter_old(fuelMeterOld);
		Integer fuelmeterNow = fuelMeter;
		Integer fuelmeterOld = fuelMeterOld;
		Integer usagefuel = (fuelmeterNow - fuelmeterOld);
		fuel.setFuel_usage(usagefuel);

		Double fuelliter = fuelLiters;
		Double fuelprice = fuelPrice;
		Double amountfuel = fuelliter * fuelprice;
		fuel.setFuel_amount(round(amountfuel, 2));

		Integer tank = fuelTank;
		/** below checking fuel tank full or partial this true */
		if (tank == 0) {
			// get Vehicle id to Vehicle name
			// Integer tank1 = 1;

			Integer usagePartial = 0;
			Double litersPartial = 0.0;
			Double AmountPartial = 0.0;

			try {

				// search time full tank stop the loop
				List<Fuel> fuelkmDESC = findMissingOddmeterFirstDESC_Vaule_Liter_Amount(
						vid, fuelmeterNow, fuel.getFuel_date());
				if (fuelkmDESC != null && !fuelkmDESC.isEmpty()) {
					for (Fuel fuelDtoDESC : fuelkmDESC) {
						// get the Fuel_tank_partial value and equal to one or not
						if (fuelDtoDESC.getFuel_tank_partial() == 1) {

							usagePartial += fuelDtoDESC.getFuel_usage();
							litersPartial += fuelDtoDESC.getFuel_liters();
							AmountPartial += fuelDtoDESC.getFuel_amount();

						} else {
							// FirstDESC_Vaule_Liter_Amount");
							break; // stop the loop when Tank_partial value ==0
						}
					}
				}
				Integer usagefuelTotal = usagePartial + usagefuel;
				Double literTotal = litersPartial + (fuelLiters);

				Double km = (usagefuelTotal / literTotal);
				fuel.setFuel_kml(round(km, 2));

				Double amountfuelTotal = AmountPartial + amountfuel;
				Double cost = (amountfuelTotal / usagefuelTotal);

				if (km == 0.0) {
					cost = 0.0;
				}
				fuel.setFuel_cost(round(cost, 2));

			} catch (Exception e) {

				Integer usagefuelTotal = usagePartial + usagefuel;
				Double literTotal = litersPartial + (fuelLiters);

				Double km = (usagefuelTotal / literTotal);
				fuel.setFuel_kml(round(km, 2));

				Double amountfuelTotal = AmountPartial + amountfuel;
				Double cost = (amountfuelTotal / usagefuelTotal);

				if (km == 0.0) {
					cost = 0.0;
				}
				fuel.setFuel_cost(round(cost, 2));
			}

		}

		fuel.setFuel_meter_attributes(fuelMeterAttributes);
		fuel.setFuel_liters(fuelLiters);
		fuel.setFuel_price(fuelPrice);
		fuel.setFuel_tank(fuelTank);

		return fuel;
	}


	@Override
	@Transactional
	public Fuel fuelMeterLessThanFuelMeterOld(ValueObject valueObject, Fuel fuel) throws Exception {

		int 	vid 				= valueObject.getInt("FuelSelectVehicle");
		int 	fuelMeter 			= valueObject.getInt("fuel_meter");
		double 	fuelLiters 			= valueObject.getDouble("fuel_liters");
		double 	fuelPrice 			= valueObject.getDouble("fuel_price");
		int		fuelTank			= valueObject.getInt("fuel_tank");
		int 	fuelMeterAttributes = valueObject.getInt("fuel_meter_attributes");

		/***************************************************************************
		 * save Fuel Missing Entries
		 ****************************************************************************/
		Integer fuelmeterNow = fuelMeter;
		Double fuelliter = fuelLiters;
		Double fuelprice = fuelPrice;
		Double amountfuel = fuelliter * fuelprice;
		fuel.setFuel_amount(round(amountfuel, 2));
		Integer tank = fuelTank;

		Integer usagePartialMISSING = 0;
		Double litersPartialMISSING = 0.0;
		Double AmountPartialMISSING = 0.0;

		Long 	FuellFullTank_ID 		 = null;
		Integer OldFullTank_OpringKM 	 = null;
		Long 	PartialFuel_Tank_ID		 = null;
		Integer OldPartialTank_closingKM = null;
		List<Fuel> fuelkmDESC				= null;

		try {
			/**
			 * Search descending Order in vid, odometer, date less then time full tank stop
			 * the for loop in fuel entries in database
			 */
			fuelkmDESC = findMissingOddmeterFirstDESC_Vaule_Liter_Amount(vid,fuelmeterNow, fuel.getFuel_date());

			if (fuelkmDESC != null && !fuelkmDESC.isEmpty()) {
				for (Fuel fuelDtoDESC : fuelkmDESC) {
					// get the Fuel_tank_partial value and equal to one or not
					/** Fuel tank is = 0 , partial is = 1 */
					if (fuelDtoDESC.getFuel_tank_partial() == 1) {

						usagePartialMISSING += fuelDtoDESC.getFuel_usage();
						litersPartialMISSING += fuelDtoDESC.getFuel_liters();
						AmountPartialMISSING += fuelDtoDESC.getFuel_amount();

					} else {
						break; // stop the loop when Tank_partial value == 0
					}
				}
			}


			List<Fuel> fuelkmASC_Update = findMissingOddmeterSecondASC_Vaule_Liter_Amount(
					vid, fuelmeterNow, fuel.getFuel_date());

			if (fuelkmASC_Update != null && !fuelkmASC_Update.isEmpty()) {
				for (Fuel fuel2 : fuelkmASC_Update) {
					// get the this update before vehicle id and meter value
					if (fuel2.getFuel_tank_partial() == 1) {

						OldFullTank_OpringKM = fuel2.getFuel_meter();

						PartialFuel_Tank_ID = fuel2.getFuel_id();
						OldPartialTank_closingKM = fuel2.getFuel_meter();
						// before enter update usage and opening oddmeter

						update_Missing_OddMeter_Usage_Partial(fuelMeter,
								OldPartialTank_closingKM - fuel.getFuel_meter(), PartialFuel_Tank_ID, valueObject.getInt("companyId"));

						break;

					} else {

						OldFullTank_OpringKM = fuel2.getFuel_meter();

						PartialFuel_Tank_ID = fuel2.getFuel_id();
						OldPartialTank_closingKM = fuel2.getFuel_meter();
						// before enter update usage and opening oddmeter

						update_Missing_OddMeter_Usage_Partial(fuelMeter,
								OldPartialTank_closingKM - fuel.getFuel_meter(), PartialFuel_Tank_ID, valueObject.getInt("companyId"));

						break;
					}
				}
			}


			// search asc order in vid and odd meter value in asc less than
			// search time full tank stop the loop but get usage and liter
			// and get full tank id and meter value also
			List<Fuel> fuelkmASC = findMissingOddmeterSecondASC_Vaule_Liter_Amount(vid,
					fuelmeterNow, fuel.getFuel_date());
			if (fuelkmASC != null && !fuelkmASC.isEmpty()) {
				for (Fuel fuelDtoASC : fuelkmASC) {
					/*
					 * if enter the missing fuel tank is full not calution before enter don't cal
					 */
					if (fuelTank != 0) {

						// get the Fuel_tank_partial value and equal to one or not
						if (fuelDtoASC.getFuel_tank_partial() == 1) {

							usagePartialMISSING += fuelDtoASC.getFuel_usage();
							litersPartialMISSING += fuelDtoASC.getFuel_liters();
							AmountPartialMISSING += fuelDtoASC.getFuel_amount();

						} else {
							OldFullTank_OpringKM = fuelDtoASC.getFuel_meter();
							usagePartialMISSING += fuelDtoASC.getFuel_usage();
							litersPartialMISSING += fuelDtoASC.getFuel_liters();
							AmountPartialMISSING += fuelDtoASC.getFuel_amount();

							FuellFullTank_ID = fuelDtoASC.getFuel_id();
							break; // stop the loop when Tank_partial value ==0
						}

					} else {

						FuellFullTank_ID = fuelDtoASC.getFuel_id();
						OldFullTank_OpringKM = fuelDtoASC.getFuel_meter();
						FuellFullTank_ID = fuelDtoASC.getFuel_id();
						break; // stop the loop when Tank_partial value == 0
					}
				}
			}


			// get old meter
			Integer fuelmeterOld = 0;
			// get opening oddmeter in enter oddmeter value
			List<Fuel> fuelkmDESC_OPEN_ODDMeter = findMissingOddmeterFirstDESC_Vaule_Liter_Amount(
					vid, fuelmeterNow, fuel.getFuel_date());
			if (fuelkmDESC_OPEN_ODDMeter != null && !fuelkmDESC_OPEN_ODDMeter.isEmpty()) {
				for (Fuel fuelDtoDESC_OPEN : fuelkmDESC_OPEN_ODDMeter) {

					fuelmeterOld = fuelDtoDESC_OPEN.getFuel_meter();
					break; // stop the loop when Tank_partial value == 0

				}
			}


			if (OldFullTank_OpringKM == null) {
				OldFullTank_OpringKM = 0;
			}
			Integer OldFullTank_OpringKM_Usage = (OldFullTank_OpringKM - fuel.getFuel_meter());
			// after missing oddmter after get the value of that fuel
			// enterties values
			@SuppressWarnings("unused")
			Integer OldFull_AfterMissingMeterOpring = (OldFullTank_OpringKM) - (OldFullTank_OpringKM_Usage);

			Integer usagefuel = (fuelmeterNow - fuelmeterOld);
			fuel.setFuel_usage(usagefuel);
			fuel.setFuel_meter_old(fuelmeterOld);

			// tank full mean update that full value row not full search for
			// full tank entries in full entries
			if (tank == 0) {

				// Missing Tank Full update the oddmeter in same full id
				// that calculation

				Integer usagefuelTotal = usagePartialMISSING + usagefuel;
				Double literTotal = litersPartialMISSING + (fuelLiters);

				Double km = (usagefuelTotal / literTotal);
				fuel.setFuel_kml(round(km, 2));

				Double amountfuelTotal = AmountPartialMISSING + amountfuel;
				Double cost = (amountfuelTotal / usagefuelTotal);

				if (km == 0.0) {
					cost = 0.0;
				}
				fuel.setFuel_cost(round(cost, 2));

				update_Missing_OddMeter_Usage_Partial(fuel.getFuel_meter(),
						OldFullTank_OpringKM - fuel.getFuel_meter(), FuellFullTank_ID, valueObject.getInt("companyId"));

				/******************************************************************/
				// this code check get Old Full tank value of the cost and
				// km/l cost update
				Long OldFUELL_id = (long) 0;
				Integer usagePartialOldFUELL = 0;
				Double litersPartialOldFUELL = 0.0;
				Double AmountPartialOldFUELL = 0.0;

				List<Fuel> fuelkmASC_updateOldFuel_cost = findMissingOddmeterSecondASC_Vaule_Liter_Amount(vid, fuelmeterNow,
						fuel.getFuel_date());
				if (fuelkmASC_updateOldFuel_cost != null && !fuelkmASC_updateOldFuel_cost.isEmpty()) {
					for (Fuel fuelDtoASC_oldfull : fuelkmASC_updateOldFuel_cost) {

						// get the Fuel_tank_partial value and equal to one or not
						if (fuelDtoASC_oldfull.getFuel_tank_partial() == 1) {

							/*
							 * OldFullTank_OpringKM = fuelDtoASC.getFuel_meter();
							 * System.out.println(OldFullTank_OpringKM);
							 */
							usagePartialOldFUELL += fuelDtoASC_oldfull.getFuel_usage();
							litersPartialOldFUELL += fuelDtoASC_oldfull.getFuel_liters();
							AmountPartialOldFUELL += fuelDtoASC_oldfull.getFuel_amount();

						} else {

							usagePartialOldFUELL += fuelDtoASC_oldfull.getFuel_usage();
							litersPartialOldFUELL += fuelDtoASC_oldfull.getFuel_liters();
							AmountPartialOldFUELL += fuelDtoASC_oldfull.getFuel_amount();

							OldFUELL_id = fuelDtoASC_oldfull.getFuel_id();
							break; // stop the loop when Tank_partial value == 0
						}

					} // for loop close

				} // close if
				// this update Missing oddmeter to add in Next Fuel entries
				// Full calculation cost and km/L

				Double KM_OLdNextFULL = (usagePartialOldFUELL / litersPartialOldFUELL);
				Double cost_OLdNextFULL = (AmountPartialOldFUELL / usagePartialOldFUELL);

				if (KM_OLdNextFULL == 0.0) {
					cost_OLdNextFULL = 0.0;
				}

				update_Missing_FuelNextFull_KM_cost(OldFUELL_id, round(KM_OLdNextFULL, 2),
						round(cost_OLdNextFULL, 2), valueObject.getInt("companyId"));

			} else {
				/*********************************
				 * ADD in UI Tank partial missing oddmeter
				 **********************************/

				/* usagePartialMISSING += OldFullTank_OpringKM_Usage; */

				// this update Missing oddmeter calculation that id
				Integer usagefuelTotal = usagePartialMISSING + usagefuel;
				Double literTotal = litersPartialMISSING + (fuelLiters);

				/// *********************************/
				/* Tank partial missing oddmeter */

				/* usagePartialMISSING += OldFullTank_OpringKM_Usage; */

				Double km = (usagefuelTotal / literTotal);
				Double amountfuelTotal = AmountPartialMISSING + amountfuel;

				Double cost = (amountfuelTotal / usagefuelTotal);
				if (km == 0.0) {
					cost = 0.0;
				}

				update_Missing_FuelNextFull_KM_cost(FuellFullTank_ID, round(km, 2), round(cost, 2), valueObject.getInt("companyId"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		fuel.setFuel_meter_attributes(fuelMeterAttributes);
		fuel.setFuel_liters(fuelLiters);
		fuel.setFuel_price(fuelPrice);

		// Why tank value is Zero mean this missing odd meter
		// Next time greater then value add partial this not calculation the
		// that full
		fuel.setFuel_tank(0);

		return fuel;
	}


	@Override
	public Integer saveFuelEntriesToNewVendorCreateAuto(String FuelVendor, Integer companyId , Long userId) throws Exception {

		VendorSequenceCounter 			sequenceCounter 			= null;
		HashMap<String, Object> 		configuration				= null;
		String 							VendorName 					= "";
		String 							VendorLocation 				= "";
		Long 							VendorTypeId 				= (long) 0;
		Integer 						sevedVendorID 				= 0;
		Integer							company					= 0;
		try {

			configuration 		= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);

			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				company			= 0;
			} else {
				company			= companyId;
			}

			if(FuelVendor.contains("-")) {
				String[] VeNLocation = FuelVendor.split("-");
				VendorName = VeNLocation[0];
				try {
					if (VeNLocation[1] != null) {
						VendorLocation = VeNLocation[1];
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				VendorName = FuelVendor;
			}

			List<Vendor> validate = vendorService.getVendorNametoall(VendorName, companyId);
			if (validate != null && !validate.isEmpty()) {
				for (Vendor already : validate) {
					sevedVendorID = already.getVendorId();
					break;
				}
			} else {

				Vendor vendor = VenBL.prepareFuelEntriesToCreateNewVendor(VendorName, VendorLocation,companyId,userId);

				VendorType VenType = VendorTypeService.getVendorType("FUEL-VENDOR", company);
				if (VenType != null) {
					VendorTypeId = VenType.getVendor_Typeid();
				}
				vendor.setVendorTypeId(VendorTypeId);

				sequenceCounter = vendorSequenceService.findNextVendorNumber(companyId);
				vendor.setVendorNumber((int) sequenceCounter.getNextVal());

				vendorService.addVendor(vendor);

				sevedVendorID = vendor.getVendorId();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return 0;
		}

		return sevedVendorID;
	}

	@Override
	public ValueObject getFuelEntriesResetProperties(Integer companyId) throws Exception {
		ValueObject   					valObj						= null;
		HashMap<String, Object> 		configuration				= null;
		//	CustomUserDetails				userDetails					= null;
		boolean 						resetAllFuelEntries 		= false;
		boolean 						resetFuelVehicle 			= false;
		boolean 						resetFuelDate 				= false;
		boolean 						resetFuelOdometer 			= false;
		boolean 						resetFuelType 				= false;
		boolean 						resetFuelVendor 			= false;
		boolean 						resetFuelLiter 				= false;
		boolean 						resetFuelPrice 				= false;
		boolean 						resetFuelReference 			= false;
		boolean 						resetFuelDriver 			= false;
		boolean 						resetFuelDriverTwo 			= false;
		boolean 						resetFuelCleaner 			= false;
		boolean 						resetFuelRoute 				= false;
		boolean 						resetFuelImage 				= false;
		boolean 						resetFuelComment 			= false;

		try {
			valObj = new ValueObject();
			configuration				= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			//userDetails 				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration				= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);

			resetAllFuelEntries			= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_ALL_FUEL_ENTRIES, false);
			resetFuelVehicle			= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_VEHICLE, false);
			resetFuelDate				= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_DATE, false);
			resetFuelOdometer			= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_ODOMETER, false);
			resetFuelType				= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_TYPE, false);
			resetFuelVendor				= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_VENDOR, false);
			resetFuelLiter				= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_LITER, false);
			resetFuelPrice				= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_PRICE, false);
			resetFuelReference			= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_REFERENCE, false);
			resetFuelDriver				= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_DRIVER, false);
			resetFuelDriverTwo			= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_DRIVER_TWO, false);
			resetFuelCleaner			= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_CLEANER, false);
			resetFuelRoute				= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_ROUTE, false);
			resetFuelImage				= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_IMAGE, false);
			resetFuelComment			= (boolean) configuration.getOrDefault(FuelConfigurationConstants.RESET_FUEL_COMMENT, false);

			valObj.put("ResetAllFuelEntries", resetAllFuelEntries);
			valObj.put("ResetFuelVehicle", resetFuelVehicle);
			valObj.put("ResetFuelDate", resetFuelDate);
			valObj.put("ResetFuelOdometer", resetFuelOdometer);
			valObj.put("ResetFuelType", resetFuelType);
			valObj.put("ResetFuelVendor", resetFuelVendor);
			valObj.put("ResetFuelLiter", resetFuelLiter);
			valObj.put("ResetFuelPrice", resetFuelPrice);
			valObj.put("ResetFuelReference", resetFuelReference);
			valObj.put("ResetFuelDriver", resetFuelDriver);
			valObj.put("ResetFuelDriverTwo", resetFuelDriverTwo);
			valObj.put("ResetFuelCleaner", resetFuelCleaner);
			valObj.put("ResetFuelRoute", resetFuelRoute);
			valObj.put("ResetFuelImage", resetFuelImage);
			valObj.put("ResetFuelComment", resetFuelComment);

			return valObj;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deletePreviousVehicleFuelEntries(Fuel fuel) throws Exception{
		ValueObject		valObj	= new ValueObject();
		valObj.put("fuel", fuel);
		valObj.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
		valObj.put("txnAmount", fuel.getFuel_amount());
		valObj.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(fuel.getFuel_date()));
		valObj.put("txnTypeId", fuel.getFuel_id());
		valObj.put("vid", fuel.getVid());
		valObj.put("companyId", fuel.getCompanyId());

		new Thread() {
			public void run() {
				try {
					vehicleProfitAndLossService.runThreadForDeleteVehicleExpenseDetails(valObj);
					vehicleProfitAndLossService.runThreadForDeleteDateWiseVehicleExpenseDetails(valObj);
					vehicleProfitAndLossService.runThreadForDeleteMonthWiseVehicleExpenseDetails(valObj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
		}.start();
	}

	@Override
	@Transactional
	public void updateVidOfFuelEntries(long tripSheetId, int vid, int companyId) throws Exception {
		CustomUserDetails		 userDetails					= null;
		Date					 updatedDate					;
		try {
			userDetails					 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			updatedDate					 = new Date();
			java.sql.Timestamp sqlDate   = new java.sql.Timestamp(updatedDate.getTime());

			entityManager.createQuery(
					"Update Fuel SET vid =  "+vid+", lastupdated = '"+sqlDate+"', "
							+ " lastModifiedById = "+userDetails.getId()+" "
							+ " where fuel_TripsheetID = "+tripSheetId+" "
							+ " AND companyId = "+companyId+" ")
			.executeUpdate();

		} catch (Exception e) {
			throw e;
		}
	}


	@Override
	public HashMap<Integer, Long> getFuelEntriesCountHM(String startDate, String endDate) throws Exception {
		try {
			TypedQuery<Object[]> 	typedQuery = null;
			HashMap<Integer, Long>	map		   = null;

			typedQuery = entityManager.createQuery(
					"SELECT COUNT(F.fuel_id), F.companyId "
							+ " From Fuel as F "
							+ " WHERE F.fuel_date between '"+startDate+"' AND '"+endDate+"' AND "
							+ " F.markForDelete = 0 GROUP BY F.companyId ",
							Object[].class);

			List<Object[]> results = typedQuery.getResultList();

			if (results != null && !results.isEmpty()) {

				map	= new HashMap<>();

				for (Object[] result : results) {

					map.put((Integer)result[1], (Long)result[0]);

				}
			}

			return map;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public HashMap<Integer, Long> getFuelEntriesCountHMOnCreated(String startDate, String endDate) throws Exception {
		try {
			TypedQuery<Object[]> 	typedQuery = null;
			HashMap<Integer, Long>	map		   = null;

			typedQuery = entityManager.createQuery(
					"SELECT COUNT(F.fuel_id), F.companyId "
							+ " From Fuel as F "
							+ " WHERE F.created between '"+startDate+"' AND '"+endDate+"' AND "
							+ " F.markForDelete = 0 GROUP BY F.companyId ",
							Object[].class);

			List<Object[]> results = typedQuery.getResultList();

			if (results != null && !results.isEmpty()) {

				map	= new HashMap<>();

				for (Object[] result : results) {

					map.put((Integer)result[1], (Long)result[0]);

				}
			}

			return map;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<FuelDto> getTripsheetfuelAmount(int vid, String fromDate, String toDate,
			CustomUserDetails userDetails,int flavor)throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE)	{
				query = entityManager.createQuery(
						" SELECT F.fuel_id, F.fuel_amount,F.fuel_TripsheetID "
								+ " FROM Fuel AS F "
								+ " LEFT JOIN TripSheet T ON T.tripSheetID = F.fuel_TripsheetID "
								+ " where  F.companyId = "+ userDetails.getCompany_id() + " AND F.vid = "+vid+" AND T.closetripDate between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.tripStutesId IN (3, 4) AND F.markForDelete = 0 "
								+ " ORDER BY F.fuel_id ASC",
								Object[].class);
			}else {
				query = entityManager.createQuery(
						" SELECT F.fuel_id, F.fuel_amount,F.fuel_TripsheetID "
								+ " FROM Fuel AS F "
								+ " LEFT JOIN TripDailySheet T ON T.TRIPDAILYID = F.fuel_TripsheetID "
								+ " where  F.companyId = "+ userDetails.getCompany_id() + " AND F.vid = "+vid+" AND T.TRIP_OPEN_DATE between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND F.markForDelete = 0 "
								+ " ORDER BY F.fuel_id ASC",
								Object[].class);
			}

			List<Object[]> results = query.getResultList();

			List<FuelDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<FuelDto>();
				FuelDto select = null;
				for (Object[] result : results) {

					select = new FuelDto();
					select.setFuel_id((Long) result[0]);
					select.setFuel_amount((double) result[1]);
					select.setFuel_TripsheetID((Long)result[2]);


					Dtos.add(select);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<FuelDto> getTripsheetfuelAmountByGroupId(Long vid, String fromDate, String toDate,
			CustomUserDetails userDetails,int flavor) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE)	{
				query = entityManager.createQuery(
						" SELECT F.fuel_id, F.fuel_amount,F.fuel_TripsheetID "
								+ " FROM Fuel AS F "
								+ " INNER JOIN Vehicle V ON V.vid = F.vid"
								+ " LEFT JOIN TripSheet T ON T.tripSheetID = F.fuel_TripsheetID "
								+ " where  F.companyId = "+ userDetails.getCompany_id() + " AND V.vehicleGroupId = "+vid+" AND T.closetripDate between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.tripStutesId IN (3, 4) AND F.markForDelete = 0 "
								+ " ORDER BY F.fuel_id ASC",
								Object[].class);
			}else {
				query = entityManager.createQuery(
						" SELECT F.fuel_id, F.fuel_amount,F.fuel_TripsheetID "
								+ " FROM Fuel AS F "
								+ " INNER JOIN Vehicle V ON V.vid = F.vid"
								+ " LEFT JOIN TripDailySheet T ON T.TRIPDAILYID = F.fuel_TripsheetID "
								+ " where  F.companyId = "+ userDetails.getCompany_id() + " AND V.vehicleGroupId = "+vid+" AND T.TRIP_OPEN_DATE between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND F.markForDelete = 0 "
								+ " ORDER BY F.fuel_id ASC",
								Object[].class);
			}

			List<Object[]> results = query.getResultList();

			List<FuelDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<FuelDto>();
				FuelDto select = null;
				for (Object[] result : results) {

					select = new FuelDto();
					select.setFuel_id((Long) result[0]);
					if(result[1] != null)
					select.setFuel_amount(Double.parseDouble(toFixedTwo.format(result[1])));
					select.setFuel_TripsheetID((Long)result[2]);


					Dtos.add(select);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<FuelDto> getRouteWiseTripsheetfuelAmount(int routeId, String fromDate, String toDate,
			CustomUserDetails userDetails, int tripFlavor)throws Exception {
		try {
			TypedQuery<Object[]> query = null;

			if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE ) {
				query = entityManager.createQuery(
						" SELECT F.fuel_id, F.fuel_amount,F.fuel_TripsheetID,T.routeID "
								+ " FROM Fuel AS F "
								+ " LEFT JOIN TripSheet T ON T.tripSheetID = F.fuel_TripsheetID  "
								+ " where  F.companyId = "+ userDetails.getCompany_id() + " AND T.routeID = "+routeId+" AND T.closetripDate between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.tripStutesId IN (3, 4) AND F.markForDelete = 0 "
								+ " ORDER BY F.fuel_id ASC",
								Object[].class);
			}else {
				query = entityManager.createQuery(
						" SELECT F.fuel_id, F.fuel_amount,F.fuel_TripsheetID,T.routeID "
								+ " FROM Fuel AS F "
								+ " LEFT JOIN TripDailySheet T ON T.TRIPDAILYID = F.fuel_TripsheetID "
								+ " where  F.companyId = "+ userDetails.getCompany_id() + " AND T.routeID = "+routeId+" AND T.TRIP_OPEN_DATE between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND T.markForDelete = 0 "
								+ " ORDER BY F.fuel_id ASC",
								Object[].class);
			}


			List<Object[]> results = query.getResultList();

			List<FuelDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<FuelDto>();
				FuelDto select = null;
				for (Object[] result : results) {

					select = new FuelDto();
					select.setFuel_id((Long) result[0]);
					select.setFuel_amount((double) result[1]);
					select.setFuel_TripsheetID((Long)result[2]);
					select.setRouteID((Integer)result[3]);

					Dtos.add(select);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}








	@Override
	public ValueObject getTripSheetWiseFuelEntryDetailsReport(ValueObject valueObject) throws Exception {

		Integer						vehicleId				= 0;
		String						dateRange				= "";
		CustomUserDetails			userDetails				= null;
		ValueObject					tableConfig				= null;
		List<FuelDto> 				fuelDtoList			   	= null;	
		String 						query					= "";
		int 						driverId				= 0 ;
		int 						secDriverId				= 0 ;				
		int 						routeId					= 0 ;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			vehicleId						= valueObject.getInt("vehicleId", 0);
			dateRange						= valueObject.getString("date");
			driverId						= valueObject.getInt("driverId", 0);
			secDriverId						= valueObject.getInt("secDriverId", 0);
			routeId							= valueObject.getInt("routeId", 0);

			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				String Driver_Name = "", Route = "" , Vehicle = "",Date = "", SEC_DRIVER ="";

				if(driverId > 0 )
				{
					Driver_Name = " AND D.driver_id = "+ driverId +" ";
				}
				if(routeId > 0 )
				{
					Route = " AND  T.routeID = "+ routeId +" ";
				}
				if(vehicleId > 0 )
				{
					Vehicle = " AND  F.vid = "+ vehicleId +" ";
				}
				if(secDriverId > 0 )
				{
					SEC_DRIVER = " AND  D1.driver_id = "+ secDriverId +" ";
				}

				Date		=	" AND F.fuel_date between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;

				query       = " " + Driver_Name + " " + SEC_DRIVER+ " " + Route + " "+ Vehicle +" "+ Date+" ";
			}	

			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.TRIPSHEET_WISE_FUEL_ENTRY_DATA_FILE_PATH);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

			fuelDtoList		= FuelReportDaoImpl.getFuelEntryDetails_Report(query,userDetails.getCompany_id());

			if(driverId != 0 ) {
				if(fuelDtoList != null)
					valueObject.put("DriverKey", fuelDtoList.get(0).getDriver_name());
			} else {
				valueObject.put("DriverKey", "All");
			}

			if(secDriverId != 0 ) {
				if(fuelDtoList != null)
					valueObject.put("SecDriverKey", fuelDtoList.get(0).getFuelSecDriverName());
			} else {
				valueObject.put("SecDriverKey", "All");
			}


			if(routeId != 0 ) {
				if(fuelDtoList != null)
					valueObject.put("RouteKey", fuelDtoList.get(0).getFuelRouteName());
			} else {
				valueObject.put("RouteKey", "All");
			}

			if(vehicleId > 0) {
				if(fuelDtoList != null)
					valueObject.put("VehicleKey", fuelDtoList.get(0).getVehicle_registration());
			} else {
				valueObject.put("VehicleKey", "All");
			}

			valueObject.put("fuelEntryDetails", fuelDtoList);

			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;			
		}
	}

	@Override
	public long todaysFuelEntriesCount (String startDate, String endDate, int companyId) throws Exception {

		Query queryt = 	null;
		queryt = entityManager.createQuery(
				"SELECT COUNT(F.fuel_id) "
						+ " From Fuel as F "
						+ " WHERE F.fuel_date between '"+startDate+"' AND '"+endDate+"' "
						+ " AND F.companyId = "+companyId+" AND F.markForDelete = 0 ");

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
	public double todaysTotalFuelCost (String startDate, String endDate, int companyId) throws Exception {

		Query queryt = 	null;
		queryt = entityManager.createQuery(
				"SELECT SUM(F.fuel_amount) "
						+ " From Fuel as F "
						+ " WHERE F.fuel_date between '"+startDate+"' AND '"+endDate+"' "
						+ " AND F.companyId = "+companyId+" AND F.markForDelete = 0 ");

		Double count = (double) 0;
		try {

			if((Double) queryt.getSingleResult() != null) {
				count = (Double) queryt.getSingleResult();
			}

		} catch (NoResultException nre) {

		}

		return count;
	}

	@Override
	public double todaysTotalFuelLiter (String startDate, String endDate, int companyId) throws Exception {

		Query queryt = 	null;
		queryt = entityManager.createQuery(
				"SELECT SUM(F.fuel_liters) "
						+ " From Fuel as F "
						+ " WHERE F.fuel_date between '"+startDate+"' AND '"+endDate+"' "
						+ " AND F.companyId = "+companyId+" AND F.markForDelete = 0 ");

		Double count = (double) 0;
		try {

			if((Double) queryt.getSingleResult() != null) {
				count = (Double) queryt.getSingleResult();
			}

		} catch (NoResultException nre) {

		}

		return count;
	}

	@Override
	public double todaysAverageFuelPrice (String startDate, String endDate, int companyId) throws Exception {

		Query queryt = 	null;
		queryt = entityManager.createQuery(
				"SELECT AVG(F.fuel_price) "
						+ " From Fuel as F "
						+ " WHERE F.fuel_date between '"+startDate+"' AND '"+endDate+"' "
						+ " AND F.companyId = "+companyId+" AND F.markForDelete = 0 ");

		Double count = (double) 0;
		try {

			if((Double) queryt.getSingleResult() != null) {
				count = (Double) queryt.getSingleResult();
			}

		} catch (NoResultException nre) {

		}

		return count;
	}

	@Override
	public List<FuelDto> getTotalFuelCreatedDetailsBetweenDates(String startDate, String endDate, Integer companyId) throws Exception {
		TypedQuery<Object[]> query = null;

		query = entityManager.createQuery(
				" SELECT FL.fuel_id, FL.fuel_Number, FL.fuel_price, FL.fuel_liters, FL.fuel_amount, FL.created, V.vehicle_registration,"
						+ " V.vehicle_ExpectedMileage, V.vehicle_ExpectedMileage_to, FL.fuel_kml, V.vid, FL.fuel_date "
						+ " from Fuel FL " 
						+ " INNER JOIN Vehicle V ON V.vid = FL.vid "
						+ " WHERE FL.fuel_date between '"+startDate+"' AND '"+endDate+"' "
						+ " AND FL.companyId = "+companyId+" AND FL.markForDelete = 0 ",
						Object[].class);
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto select = null;
			for (Object[] vehicle : results) {

				select = new FuelDto();
				select.setFuel_id((long) vehicle[0]);
				select.setFuel_Number((long) vehicle[1]);
				if(vehicle[2] != null)
				select.setFuel_price(Double.parseDouble(toFixedTwo.format(vehicle[2])));
				if(vehicle[3] != null)
				select.setFuel_liters(Double.parseDouble(toFixedTwo.format(vehicle[3])));
				if(vehicle[4] != null)
				select.setFuel_amount(Double.parseDouble(toFixedTwo.format(vehicle[4])));
				select.setCreated(dateFormat.format((Timestamp) vehicle[5]));
				select.setVehicle_registration((String) vehicle[6]);
				if(vehicle[7] != null)
				select.setVehicle_ExpectedMileage(Double.parseDouble(toFixedTwo.format(vehicle[7])));
				if(vehicle[8] != null)
				select.setVehicle_ExpectedMileage_to(Double.parseDouble(toFixedTwo.format(vehicle[8])));
				if(vehicle[9] != null) {
					select.setFuel_kml(Double.parseDouble(toFixedTwo.format(vehicle[9])));
				}else {
					select.setFuel_kml((double) 0);
				}
				select.setVid((int) vehicle[10]);
				select.setFuel_date(dateFormat.format((Timestamp) vehicle[11]));

				Dtos.add(select);
			}
		}
		return Dtos;

	}

	@Override
	public List<FuelDto> getTotalTripSheetFuelEntriesCreatedDetailsBetweenDates(String startDate, String endDate, Integer companyId)throws Exception {
		TypedQuery<Object[]> query = null;

		query = entityManager.createQuery(
				" SELECT FL.fuel_id, FL.fuel_Number, FL.fuel_price, FL.fuel_liters, FL.fuel_amount, FL.created, V.vehicle_registration"
						+ " from Fuel FL " 
						+ " INNER JOIN Vehicle V ON V.vid = FL.vid "
						+ " INNER JOIN TripSheet T ON T.tripSheetID = FL.fuel_TripsheetID "
						+ " WHERE FL.created between '"+startDate+"' AND '"+endDate+"' "
						+ " AND FL.companyId = "+companyId+" AND FL.markForDelete = 0 ",
						Object[].class);
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto select = null;
			for (Object[] vehicle : results) {

				select = new FuelDto();
				select.setFuel_id((long) vehicle[0]);
				select.setFuel_Number((long) vehicle[1]);
				select.setFuel_price((double) vehicle[2]);
				select.setFuel_liters((double) vehicle[3]);
				select.setFuel_amount((double) vehicle[4]);
				select.setCreated(dateFormat.format((Timestamp) vehicle[5]));
				select.setVehicle_registration((String) vehicle[6]);

				Dtos.add(select);
			}
		}
		return Dtos;

	}

	@Override
	public ValueObject getFuelVehicleOdoMerete(ValueObject valueObjectIn) throws Exception {		

		int 					vehicleID				= 0;
		int 					companyId				= 0;
		long					userId					= 0;
		String					vendorName				= null;
		ValueObject 			valueObjectOut			= null;
		VehicleDto 				oddmeter 				= null;
		VehicleDto 				oddMeterDetails			= null;
		HashMap<String, Object> configuration			= null;

		try {
			valueObjectOut	= new ValueObject();
			oddMeterDetails	= new VehicleDto();
			vehicleID		= valueObjectIn.getInt("vehicleId");
			companyId		= valueObjectIn.getInt("companyId");
			userId			= valueObjectIn.getLong("userId");

			if(vehicleID > 0){
				
				vendorName = UserProfileRepository.selectVendor(userId, companyId);
				if(vendorName != null) {
					valueObjectOut.put("vendorName",vendorName);
				}

				configuration	= companyConfigurationService.getCompanyConfiguration(companyId,PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
				if(!(boolean) configuration.get(FuelConfigurationConstants.LAST_FUEL_AS_OPEN)) {
					oddmeter = vehicleService.getVehicel_DropDown_Fuel_ADD_entries(vehicleID,companyId);
				}else {
					oddmeter = vehicleService.getVehicel_DropDown_Last_Fuel_ADD_entries(vehicleID,companyId);
				}
				
				if (oddmeter != null) {
					HashMap<String, Object>	gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.VEHICLE_GPS_CONFIG);
					
					if((boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
						ValueObject	object	= new ValueObject();
						
						object.put("companyId", companyId);
						object.put("vehicleId", vehicleID);
						ValueObject	gpsobject	=	vehicleGPSDetailsService.getVehicleGPSData(object);
						
						if(gpsobject !=null && gpsobject.containsKey(GPSConstant.VEHICLE_TOTAL_KM_NAME)) {
							oddMeterDetails.setGpsWorking(true);
							
							if((int)gpsConfiguration.get(VehicleGPSConfiguratinConstant.GPS_FLAVOR) == 2) {
								oddMeterDetails.setGpsOdameter(gpsobject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME));
							}else if((int)gpsConfiguration.get(VehicleGPSConfiguratinConstant.GPS_FLAVOR) == 3) {
								oddMeterDetails.setGpsOdameter(gpsobject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME));
							}
							oddMeterDetails.setVehicle_Location(gpsobject.getString(GPSConstant.GPS_LOCATION_NAME));
							
						}else {
							oddMeterDetails.setGpsWorking(false);
						}
					} else {
						oddMeterDetails.setGpsWorking(true);
					}
					
					oddMeterDetails.setVehicle_Odometer(oddmeter.getVehicle_Odometer());
					oddMeterDetails.setVehicle_Fuel(methodRemoveLastComma(VehicleFuelType.getVehicleFuelTypeName(oddmeter.getVehicleFuelId())));
					oddMeterDetails.setVehicle_Group(oddmeter.getVehicle_Group());
					oddMeterDetails.setVehicleGroupId(oddmeter.getVehicleGroupId());
					oddMeterDetails.setVehicleFuelId(oddmeter.getVehicleFuelId());
					oddMeterDetails.setVehicle_Fuel(VehicleFuelType.getVehicleFuelTypeName(oddmeter.getVehicleFuelId()));
					oddMeterDetails.setVehicle_ExpectedOdameter(oddmeter.getVehicle_ExpectedOdameter());
					oddMeterDetails.setLastFuelOdometer(oddmeter.getLastFuelOdometer());
					oddMeterDetails.setVehicle_registration(oddmeter.getVehicle_registration());
					oddMeterDetails.setVehicle_ExpectedMileage(oddmeter.getVehicle_ExpectedMileage());
					oddMeterDetails.setVehicle_ExpectedMileage_to(oddmeter.getVehicle_ExpectedMileage_to());
					oddMeterDetails.setVehicle_FuelTank1(oddmeter.getVehicle_FuelTank1());
				}
			}
			
			valueObjectOut.put("oddMeterDetails",oddMeterDetails);

			return valueObjectOut;
		} catch (Exception e) {
			LOGGER.error("getFuelVehicleOdoMerete:"+ e);
			throw e;
		} finally {
			vehicleID				= 0;    
			companyId				= 0;    
			valueObjectOut			= null; 
			oddmeter 				= null; 
			oddMeterDetails			= null; 
			configuration			= null; 
		}
	}

	public String methodRemoveLastComma(String str) {
		if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	@Override
	public ValueObject getFuelVendorList(ValueObject object) throws Exception {

		List<Vendor> 			vendorList				= null;
		List<Vendor> 			vendor					= null;
		ValueObject				valueOutObj				= null;
		String					text					= null;
		int						companyId				= 0;

		try {
			valueOutObj = new ValueObject();
			vendorList 	= new ArrayList<Vendor>();

			text		= object.getString("term");
			companyId	= object.getInt("companyId");

			vendor 		= vendorService.SearchOnlyFuelVendorName(text, companyId);
			if (vendor != null && !vendor.isEmpty()) {
				for (Vendor add : vendor) {
					Vendor wadd = new Vendor();
					wadd.setVendorId(add.getVendorId());
					wadd.setVendorName(add.getVendorName());
					wadd.setVendorLocation(add.getVendorLocation());
					wadd.setOwnPetrolPump(add.getOwnPetrolPump());
					vendorList.add(wadd);
				}
			}

			valueOutObj.put("vendorList", vendorList);

			return valueOutObj;
		} catch(Exception e) {
			LOGGER.error("getFuelVendorList:"+ e);
			throw e;
		} finally {
			vendorList				= null;
			vendor					= null;
			valueOutObj				= null;
			text					= null;
			companyId				= 0;   
		}
	}

	@Override
	public ValueObject initializeFuelEntry(ValueObject valueInObj) throws Exception {
		Map<String, Object> 					model 					= null;
		HashMap<String, Object> 				configuration			= null;
		HashMap<String, Object> 				gpsConfiguration 		= null;
		HashMap<String, Object> 				fuelPriceConfig 		= null;
		int										companyId				= 0;
		ObjectMapper 							objectMapper			= null;
		String 									defaultFuelPrice 		= null;
		HashMap<String, Object> 				vendorConfiguration	    = null;
		boolean                                 allowOwnPetrolPump      = false;
		int                                     maxFuelCapacity         = 0;
		try {
			model 				= new HashMap<String, Object>();
			objectMapper 		= new ObjectMapper();
			companyId			= valueInObj.getInt("companyId", 0);

			if(companyId == 0) {
				valueInObj.put("message", "Unauthorised Access !");
				return valueInObj;
			}

			configuration 	 	= companyConfigurationService.getCompanyConfiguration(companyId,PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			gpsConfiguration 	= companyConfigurationService.getCompanyConfiguration(companyId,PropertyFileConstants.VEHICLE_GPS_CONFIG);
			fuelPriceConfig 	= companyConfigurationService.getCompanyConfiguration(companyId,PropertyFileConstants.FUEL_PRICE_CONFIG);
			vendorConfiguration	= companyConfigurationService.getCompanyConfiguration(companyId,PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG);
			defaultFuelPrice	=  fuelPriceConfig.getOrDefault(FuelConfigurationConstants.FUEL_PRICE, 0) + "";
			maxFuelCapacity     = (int) fuelPriceConfig.getOrDefault(FuelConfigurationConstants.MAX_FUEL_CAPACITY, 0);
			allowOwnPetrolPump  = (boolean) vendorConfiguration.getOrDefault(VendorTypeConfigurationConstants.ALLOW_OWN_PETROL_PUMP, false);
			model.put("defaultFuelPrice", Double.parseDouble(defaultFuelPrice));

			model.put(FuelConfigurationConstants.MAX_FUEL_CAPACITY, maxFuelCapacity);
			model.put("gpsConfiguration", objectMapper.writeValueAsString(gpsConfiguration));
			model.put("configuration",configuration);
			model.put("allowGPSIntegration",(boolean)gpsConfiguration.get("allowGPSIntegration"));

			model.put(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_REFERENCE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_REFERENCE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, configuration.getOrDefault(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, true));
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, (boolean)companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, false));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL,companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL, false));
			model.put(FuelConfigurationConstants.VALIDATE_DRIVER1,configuration.getOrDefault(FuelConfigurationConstants.VALIDATE_DRIVER1, false));
			model.put(FuelConfigurationConstants.SHOW_MARK_AS_VOID_AUTO_SELECTED,configuration.getOrDefault(FuelConfigurationConstants.SHOW_MARK_AS_VOID_AUTO_SELECTED, false));
			model.put(VendorTypeConfigurationConstants.ALLOW_OWN_PETROL_PUMP,allowOwnPetrolPump);
			model.put(FuelConfigurationConstants.BLOCK_GALLERY_IMAGE_SELECTION,configuration.getOrDefault(FuelConfigurationConstants.BLOCK_GALLERY_IMAGE_SELECTION, false));
			model.put(FuelConfigurationConstants.SET_FUEL_ENTRY_DEFAULT_DATE_TIME,configuration.getOrDefault(FuelConfigurationConstants.SET_FUEL_ENTRY_DEFAULT_DATE_TIME, false));
			
			String minBackDate = DateTimeUtility.getDateBeforeNoOfDays((int)configuration.get("noOfDaysForBackDate"));
			model.put("minBackDate",minBackDate);
			
			valueInObj.put("initializeObj", model);

			return valueInObj;
		}  catch (Exception e) {
			LOGGER.error("initializeFuelEntry:", e);
			throw e;
		} finally {
			model 				= null;
			configuration		= null;
			gpsConfiguration 	= null;
			companyId			= 0;   
			objectMapper		= null;
		}
	}

	@Override
	public ValueObject getFuelDetailsForMobile(ValueObject valueInObj) throws Exception {

		int				companyId			= 0;
		FuelDto			fuelDetails			= null;
		Fuel			fuelObj				= null;
		long			fuelId				= 0;

		try {
			companyId			= valueInObj.getInt("companyId", 0);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat timeFormat	 = new SimpleDateFormat("hh:mm");

			if(valueInObj.containsKey("fuelNumber")) {
				fuelObj = fuelDao.getFuelByNumber(valueInObj.getLong("fuelNumber"),companyId);
				if(fuelObj == null) {
					valueInObj.put("message", "No Records Found !");
					return valueInObj;
				}
				fuelId		= fuelObj.getFuel_id();
			} else {
				fuelId		= valueInObj.getLong("fuelId",0);
				if(fuelId == 0) {
					valueInObj.put("message", "Please Enter a Valid data   !");
					return valueInObj;
				}
			}

			fuelDetails = getFuelDetails(fuelId,companyId);
			if(fuelDetails != null) {
				org.fleetopgroup.persistence.document.FuelDocument file = get_Fuel_Document_Details(fuelDetails.getFuel_document_id(), companyId);
				if(file != null) {
					fuelDetails.setFuelBase64Document(ByteConvertor.byteArraytoBase64(file.getFuel_content()));
					fuelDetails.setFileExtension(file.getFuel_contentType());
				}
				if(fuelDetails.getFuelDateTime() != null) {
					fuelDetails.setFuel_date(dateFormat.format(fuelDetails.getFuelDateTime()));
					fuelDetails.setFuelTime(timeFormat.format(fuelDetails.getFuelDateTime()));
					fuelDetails.setFuelDateTimeStr(dateFormat2.format(fuelDetails.getFuelDateTime()));
				}
			}

			valueInObj.put("fuelDetails", fuelDetails);

			return valueInObj;
		} catch(Exception e) {
			LOGGER.error("GetFuelDetails:"+ e);
			throw e;
		} finally {
			companyId			= 0;   
			fuelDetails			= null;
			fuelObj				= null;
			fuelId				= 0;   
		}
	}
	
	@Override
	public List<FuelDto> getRouteWiseTripsheetfuelExpense(String routeId, String fromDate, String toDate,CustomUserDetails userDetails, int tripFlavor)throws Exception {
		TypedQuery<Object[]> 		query 			= null;
		List<FuelDto> 				fuelDtoList 	= null;
		FuelDto 					fuelDto 		= null;
		
		try {
			if(tripFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE ) {
			query = entityManager.createQuery(
					" SELECT F.fuel_id, SUM(F.fuel_amount), F.fuel_TripsheetID , T.routeID, SUM(F.fuel_liters) "
							+ " FROM Fuel AS F "
							+ " LEFT JOIN TripSheet T ON T.tripSheetID = F.fuel_TripsheetID "
							+ " where  F.companyId = "+ userDetails.getCompany_id() + " "+routeId+" AND T.closetripDate between "
							+ " '"+fromDate+"' AND '"+toDate+"' AND T.tripStutesId IN ("+TripSheetStatus.TRIP_STATUS_CLOSED+", "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+") AND F.markForDelete = 0 "
							+ " GROUP BY T.tripSheetID  ORDER BY F.fuel_id ASC",
							Object[].class );
			}else {
				query = entityManager.createQuery(
						" SELECT F.fuel_id, SUM(F.fuel_amount),F.fuel_TripsheetID ,T.TRIP_ROUTE_ID, SUM(F.fuel_liters) "
								+ " FROM Fuel AS F "
								+ " LEFT JOIN TripDailySheet T ON T.TRIPDAILYID = F.fuel_TripsheetID "
								+ " where  F.companyId = "+ userDetails.getCompany_id() + " "+routeId+" AND T.TRIP_OPEN_DATE between "
								+ " '"+fromDate+"' AND '"+toDate+"' AND T.TRIP_STATUS_ID = "+TripDailySheetStatus.TRIP_STATUS_CLOSED+" AND T.markForDelete = 0 "
								+ " GROUP BY T.TRIPDAILYID  ORDER BY F.fuel_id ASC",
								Object[].class);
			}
			
			
			List<Object[]> results = query.getResultList();
			
			
			if (results != null && !results.isEmpty()) {
				fuelDtoList = new ArrayList<FuelDto>();
				
				for (Object[] result : results) {
					fuelDto = new FuelDto();
					
					fuelDto.setFuel_id((Long) result[0]);
					if((Double) result[1] != null) {
					fuelDto.setFuel_amount((Double) result[1]);
					}else {
					fuelDto.setFuel_amount(0.0);
					}
					fuelDto.setFuel_TripsheetID((Long)result[2]);
					fuelDto.setRouteID((Integer)result[3]);
					if((Double) result[4] != null) {
					fuelDto.setFuel_liters((Double) result[4]);
					}else {
					fuelDto.setFuel_liters(0.0);
					}
					fuelDtoList.add(fuelDto);
				}
			}
			return fuelDtoList;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getDriverWiseFuelEntryReport(ValueObject valueObject) throws Exception {

		Integer						vehicleId				= 0;
		String						dateRange				= "";
		CustomUserDetails			userDetails				= null;
		ValueObject					tableConfig				= null;
		List<FuelDto> 				fuelDtoList			   	= null;	
		String 						query					= "";
		int 						driverId				= 0 ;
		int 						secDriverId				= 0 ;				
		int 						routeId					= 0 ;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			vehicleId						= valueObject.getInt("vehicleId", 0);
			dateRange						= valueObject.getString("date");
			driverId						= valueObject.getInt("driverId", 0);
			secDriverId						= valueObject.getInt("secDriverId", 0);
			routeId							= valueObject.getInt("routeId", 0);

			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				String Driver_Name = "", Route = "" , Vehicle = "",Date = "", SEC_DRIVER ="";

				if(driverId > 0 )
				{
					Driver_Name = " AND D.driver_id = "+ driverId +" ";
				}
				if(routeId > 0 )
				{
					Route = " AND  T.routeID = "+ routeId +" ";
				}
				if(vehicleId > 0 )
				{
					Vehicle = " AND  F.vid = "+ vehicleId +" ";
				}
				if(secDriverId > 0 )
				{
					SEC_DRIVER = " AND  D1.driver_id = "+ secDriverId +" ";
				}

				Date		=	" AND F.fuel_date between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;

				query       = " " + Driver_Name + " " + SEC_DRIVER+ " " + Route + " "+ Vehicle +" "+ Date+" ";
			}	

			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.DRIVER_WISE_FUEL_ENTRY_DATA_FILE_PATH);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

			fuelDtoList		= FuelReportDaoImpl.getFuelEntryDetails_Report(query,userDetails.getCompany_id());

			if(driverId != 0 ) {
				if(fuelDtoList != null)
					valueObject.put("DriverKey", fuelDtoList.get(0).getDriver_name());
			} else {
				valueObject.put("DriverKey", "All");
			}

			if(secDriverId != 0 ) {
				if(fuelDtoList != null)
					valueObject.put("SecDriverKey", fuelDtoList.get(0).getFuelSecDriverName());
			} else {
				valueObject.put("SecDriverKey", "All");
			}

			if(routeId != 0 ) {
				if(fuelDtoList != null)
					valueObject.put("RouteKey", fuelDtoList.get(0).getFuelRouteName());
			} else {
				valueObject.put("RouteKey", "All");
			}

			if(vehicleId > 0) {
				if(fuelDtoList != null)
					valueObject.put("VehicleKey", fuelDtoList.get(0).getVehicle_registration());
			} else {
				valueObject.put("VehicleKey", "All");
			}

			valueObject.put("fuelEntryDetails", fuelDtoList);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;			
		}
	}	
	
	@Override
	public Fuel getLastFuelEntrieDetails(Integer vid, Timestamp fromDate) throws Exception {
		try {
			TypedQuery<Fuel> query2 = entityManager.createQuery(
					"SELECT T "
					+ " FROM Fuel AS T"
							+ " where T.vid = "+vid+" AND T.fuelDateTime <= '"+fromDate+"' ORDER BY T.fuel_id DESC", Fuel.class);
			query2.setMaxResults(1);
			return query2.getSingleResult();
		}catch (Exception e) {
			return null;
		}
	}
	
	
	@Transactional
	public void updatePaymentApprovedFuelDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId) throws Exception {
		entityManager.createQuery("UPDATE Fuel SET fuel_vendor_approvalID=" + Approval_ID
				+ ",expectedPaymentDate='" + expectedPaymentDate + "', fuel_vendor_paymodeId='" + approval_Status + "'"
				+ " WHERE fuel_id IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();

	}
	
	@Transactional
	public void updatePaymentPaidFuelDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId , short fuel_vendor_paymodeId) throws Exception {
		
		entityManager.createQuery("  UPDATE Fuel SET fuel_vendor_paymentdate='" + approval_date+"' , fuel_vendor_paymodeId="+fuel_vendor_paymodeId+" WHERE fuel_id IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getFuelListforTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany, String ledgerName) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT F.fuel_id, F.fuel_Number, F.fuel_date, F.fuel_comments, TS.tripSheetNumber, V.vendorName,"
					+ " VH.vehicle_registration, VH.ledgerName, TC.companyName, F.created, F.paymentTypeId, TS.tripSheetID, F.vid,"
					+ " F.fuel_amount, F.isPendingForTally "
					+ " FROM Fuel F "
					+ " INNER JOIN Vehicle VH ON VH.vid = F.vid "
					+ " INNER JOIN TripSheet TS ON TS.tripSheetID = F.fuel_TripsheetID AND TS.markForDelete = 0 AND TS.voucherDate is not null "
					+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = F.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
					+ " LEFT JOIN Vendor AS V ON V.vendorId = F.vendor_id"
					+ " WHERE TS.voucherDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND F.companyId = "+companyId+" AND TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+" AND F.markForDelete = 0");
				
			
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
						select.setTripExpenseID((Long) vehicle[0]);
						select.setFuel_id((Long) vehicle[1]);
						select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[2]));
						select.setRemark((String) vehicle[3]);
						select.setTripSheetNumber((Long) vehicle[1]); //(Long) vehicle[4] not needed
						select.setVendorName((String) vehicle[5]);
						select.setVehicle_registration((String) vehicle[6]);
						select.setLedgerName((String) vehicle[7]);
						select.setTallyCompanyName((String) vehicle[8]);
						select.setCreated((Date) vehicle[9]);
						select.setExpenseFixedId((short) vehicle[10]);
						select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
						select.setTripSheetId((Long) vehicle[11]);
						select.setVid((Integer) vehicle[12]);
						select.setExpenseAmount((Double) vehicle[13]);
						select.setPendingForTally((boolean) vehicle[14]);
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_FUEL);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						select.setExpenseName(ledgerName);
						
						select.setTripSheetNumberStr("F-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
						
						if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
							select.setCredit(true);
						}else {
							select.setCredit(false);
						}
						
						if(select.getVendorName() == null ) {
							select.setVendorName("--");
						}
						if(select.getCreated() != null ) {
							select.setCreatedStr(dateFormat.format((java.util.Date)vehicle[2]));
						}
						
						 select.setRemark("Fuel Entry On Vehicle "+select.getVehicle_registration()+" Date: "+dateFormat.format((java.util.Date)vehicle[2])+" TripSheet No :  "+(Long) vehicle[4]+" Fuel No : "+select.getFuel_id()+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						
						
						Dtos.add(select);
					}
				}
				
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Long checkVehicleFirstFuelEntry(Integer vid) throws Exception {
		Long count	= (long) 0;
	Query	query = entityManager.createQuery("SELECT count(F) "
			+ " FROM Fuel F where vid = "+vid+"  AND F.markForDelete = 0");

	try {
		query.setMaxResults(1);
		count =  (Long) query.getSingleResult();
		
	} catch (NoResultException nre) {
		// Ignore this because as per your logic this is ok!
	}
	
	return count;
	
	}	
	@Override
	public ValueObject getActiveTripsheetDataAtTime(ValueObject object) throws Exception {
		int 			vid								= 0;
		int 			companyId						= 0;
		Long			tripSheetId						= (long) 0;
		String			preQuery						= "";
		String			nextQuery						= "";
		try {
			if(object.containsKey("fromMob") && object.getBoolean("fromMob",false)){
				ValueObject objectOut   = checkFuelEntryExistOnSameDateAndTime(object);
				if(objectOut!=null && !objectOut.isEmpty()){
					if(objectOut.containsKey("isAlreadyExist") && objectOut.getBoolean("isAlreadyExist",false)){
						return objectOut;
					}
				}
			}
			vid				= object.getInt("vid");
			companyId		= object.getInt("companyId");
			
			String start_time = "00:00";
			if(object.getString("fuelTime") != null && object.getString("fuelTime") != "") {
				start_time	= object.getString("fuelTime");
			}
			
			
			java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(object.getString("fuelDate"), start_time);
			String tripActiveDateAndTime = formatSQL.format(date);
			
			
			TripSheetDto tripActive = getTripsheetDataAtTime(tripActiveDateAndTime, vid, companyId);
			if(tripActive != null) {
				if(object.getBoolean("bindMinMaxOdometerOnTripSheet",false)) {
					tripSheetId = tripActive.getTripSheetID();
				}
				object.put("tripActive", tripActive);
			}
			
			preQuery += " f.vid = "+vid+" AND f.company_Id = "+companyId+" AND f.markForDelete = 0 AND fu.fuelDateTime < '"+tripActiveDateAndTime+"' ";
			
			if(tripSheetId > 0) {
				preQuery += " AND fu.fuel_TripsheetID  = "+tripSheetId+" ";
			}
			if(object.getLong("fuel_id",0) > 0) {
				preQuery += " AND fu.fuel_id <> "+object.getLong("fuel_id",0)+" ";
			}
			
			
			nextQuery += " f.vid = "+vid+" AND f.company_Id = "+companyId+" AND f.markForDelete = 0 AND fu.fuelDateTime > '"+tripActiveDateAndTime+"' ";
			
			if(tripSheetId > 0) {
				nextQuery += " AND fu.fuel_TripsheetID  = "+tripSheetId+" ";
			}
			
			if(object.getLong("fuel_id",0) > 0) {
				nextQuery += " AND fu.fuel_id <> "+object.getLong("fuel_id",0)+" ";
			}
			
			VehicleDto previousFuelDeatils = getPreviousFuelEntryDetails(preQuery);
			VehicleDto nextFuelDeatils 	   = getNextFuelEntryDetailsInCaseOfSelectedBackDate(nextQuery);
			object.put("previousFuelDeatils", previousFuelDeatils);
			object.put("nextFuelDeatils", nextFuelDeatils);
			
			if(nextFuelDeatils != null && previousFuelDeatils != null) {
				object.put("minOdometer", previousFuelDeatils.getLastFuelOdometer() + 1 );
				object.put("maxOdometer", nextFuelDeatils.getFuelMeter() - 1);
			}else if(nextFuelDeatils == null && previousFuelDeatils != null) {
				object.put("minOdometer", previousFuelDeatils.getLastFuelOdometer() + 1 );
				object.put("maxOdometer", previousFuelDeatils.getLastFuelOdometer() + object.getDouble("vehicle_ExpectedOdameter",2500));
				if(tripSheetId > 0 && tripActive.getTripClosingKM() != null && tripActive.getTripClosingKM() > 0) {
					object.put("maxOdometer", tripActive.getTripClosingKM());
				}
			}else if(nextFuelDeatils != null && previousFuelDeatils == null) {
				object.put("minOdometer", nextFuelDeatils.getLastFuelOdometer() + 1 );
				object.put("maxOdometer", nextFuelDeatils.getFuelMeter() - object.getDouble("vehicle_ExpectedOdameter",2500));
				if(tripSheetId > 0) {
					object.put("minOdometer", tripActive.getTripOpeningKM());
				}
			}
			
			if(previousFuelDeatils != null) {
				object.put("minOdometer", previousFuelDeatils.getLastFuelOdometer()  );
			}else {
				if(tripSheetId > 0) {
					object.put("minOdometer", tripActive.getTripOpeningKM());
				}
			}
			
			if(nextFuelDeatils != null) {
				object.put("maxOdometer", nextFuelDeatils.getFuelMeter() - 1);
			}else {
				if(tripSheetId > 0 && tripActive.getTripClosingKM() != null && tripActive.getTripClosingKM() > 0) {
					object.put("maxOdometer", tripActive.getTripClosingKM());
				}else {
					if(tripActive  !=  null)
					object.put("maxOdometer", object.getDouble("vehicle_ExpectedOdameter",2500) + tripActive.getTripOpeningKM());
				}
			}
			
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public TripSheetDto getTripsheetDataAtTime (String tripActiveDateAndTime, int vid, Integer companyId) throws Exception {
		Query queryt = 	null;
		
			
		
		Object[] vehicle = null;
		try {
			queryt = entityManager.createQuery(
					"SELECT  T.tripSheetID, T.tripSheetNumber, T.tripOpenDate, T.closetripDate, T.tripFristDriverID, "
							+ " D.driver_firstname, T.routeID, TR.routeName, T.tripOpeningKM, T.tripClosingKM, D.driver_fathername, D.driver_Lastname "
							+ " FROM TripSheet as T "
							+ " INNER JOIN Driver as D ON D.driver_id = T.tripFristDriverID "
							+ " LEFT JOIN TripRoute as TR ON TR.routeID = T.routeID "
							+ " Where T.markForDelete=0 AND T.vid = "+vid+" AND T.companyId = "+companyId+" "
							+ " AND '"+tripActiveDateAndTime+"' BETWEEN T.dispatchedByTime AND T.closedByTime "
							+ " AND tripStutesId >= "+TripSheetStatus.TRIP_STATUS_CLOSED+"  "
							+ " ORDER BY T.tripSheetID desc");
			
			queryt.setMaxResults(1);
			vehicle = (Object[]) queryt.getSingleResult();
			
		} catch (NoResultException nre) {
			
			queryt = entityManager.createQuery(
					"SELECT  T.tripSheetID, T.tripSheetNumber, T.tripOpenDate, T.closetripDate, T.tripFristDriverID, "
							+ " D.driver_firstname, T.routeID, TR.routeName, T.tripOpeningKM, T.tripClosingKM, D.driver_fathername, D.driver_Lastname "
							+ " FROM TripSheet as T "
							+ " INNER JOIN Driver as D ON D.driver_id = T.tripFristDriverID "
							+ " LEFT JOIN TripRoute as TR ON TR.routeID = T.routeID "
							+ " Where T.markForDelete=0 AND T.vid = "+vid+" AND T.companyId = "+companyId+" "
							+ " AND T.dispatchedByTime <= '"+tripActiveDateAndTime+"' "
							+ " AND tripStutesId = "+TripSheetStatus.TRIP_STATUS_DISPATCHED+"  "
							+ " ORDER BY T.tripSheetID desc");
			
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre2) {
					
			}
		}
	
		TripSheetDto select;
		if (vehicle != null) {
			select = new TripSheetDto();
			
			select.setTripSheetID((Long) vehicle[0]);
			select.setTripSheetNumber((long) vehicle[1]);
			select.setTripOpenDateOn((java.util.Date) vehicle[2]);
			if(vehicle[3] != null)
			select.setClosetripDateOn((java.util.Date) vehicle[3]);
			
			if(select.getTripOpenDateOn() != null) {
				select.setTripOpenDate(dateFormat_Name.format(select.getTripOpenDateOn()));
			}
			
			if(select.getClosetripDateOn() != null) {
				select.setClosetripDate(dateFormat_Name.format(select.getClosetripDateOn()));
			}
			
			select.setTripFristDriverID((int) vehicle[4]);
			select.setTripFristDriverName((String) vehicle[5]);
			select.setRouteID((int) vehicle[6]);
			select.setRouteName((String) vehicle[7]);
			select.setTripOpeningKM((Integer) vehicle[8]);
			if(vehicle[9] != null)
			select.setTripClosingKM((Integer) vehicle[9]);
			select.setTripFristDriverFatherName((String) vehicle[10]);
			select.setTripFristDriverLastName((String) vehicle[11]);
			
			
		} else {
			return null;
		}
	
		return select;
	}
	
	@Override
	public VehicleDto getPreviousFuelEntryDetails(String prQuery) throws Exception {
		Object[] vehicle = null;
		try {
			Query query = null;
				query = entityManager.createQuery(
						"SELECT f.vid, f.vehicle_registration, f.vehicleFuelId, f.vehicle_Odometer,  "
								+ " f.vehicleGroupId, f.vehicle_ExpectedOdameter, f.vehicleGPSId, fu.fuel_meter, "
								+ "	f.vStatusId, f.gpsVendorId, fu.fuelDateTime, fu.gpsOdometer, "
								+ " fu.fuel_id, fu.fuel_Number, fu.fuel_liters, fu.fuel_meter_old FROM Vehicle AS f " 
								+ " INNER JOIN Fuel fu ON fu.vid = f.vid AND fu.markForDelete = 0 "
								+ " WHERE "+prQuery+" "
								+ " ORDER BY fu.fuelDateTime desc");
			
			try {
				query.setMaxResults(1);
				vehicle = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicleFuelId((String) vehicle[2]);
				select.setVehicle_Fuel(VehicleFuelType.getVehicleFuelTypeName((String) vehicle[2]));
				select.setVehicle_Odometer((Integer) vehicle[3]);
				select.setVehicleGroupId((long) vehicle[4]);
				select.setVehicle_ExpectedOdameter((Integer) vehicle[5]);
				select.setVehicleGPSId((String) vehicle[6]);
				select.setLastFuelOdometer((Integer) vehicle[7]);
				select.setvStatusId((short) vehicle[8]);
				select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(select.getvStatusId()));
				select.setGpsVendorId((Integer) vehicle[9]);
				if(vehicle[10] != null)
					select.setFuelDateTime((Date) vehicle[10]);
				if(vehicle[11] != null)
					select.setGpsOdameter((double) vehicle[11]);
				if(vehicle[12] != null)
					select.setFuel_id((long) vehicle[12]);
				select.setFuel_Number(((long) vehicle[13]));
				select.setFuel_liters((double) vehicle[14]);
				select.setFuelMeter((Integer) vehicle[15]);
			}
			return select;
		} catch (Exception e) {
			System.err.println("Exception Previous Fuel Details : "+e);
			return null;
		}
	
	}
	
	@Override
	public VehicleDto getNextFuelEntryDetailsInCaseOfSelectedBackDate(String nextQuery) throws Exception {
		Object[] vehicle = null;
		try {
			Query query =  null;
				query = entityManager.createQuery(
						"SELECT f.vid, f.vehicle_registration, f.vehicleFuelId, f.vehicle_Odometer,  "
								+ " f.vehicleGroupId, f.vehicle_ExpectedOdameter, f.vehicleGPSId, fu.fuel_meter, "
								+ "	f.vStatusId, f.gpsVendorId, fu.fuelDateTime, fu.gpsOdometer, "
								+ " fu.fuel_id, fu.fuel_meter_old, fu.fuel_Number "
								+ " FROM Vehicle AS f " 
								+ " INNER JOIN Fuel fu ON fu.vid = f.vid AND fu.markForDelete = 0 "
								+ " WHERE "+nextQuery+" "
								+ " ORDER BY fu.fuelDateTime ASC");
			
			
			try {
				query.setMaxResults(1);
				vehicle = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicleFuelId((String) vehicle[2]);
				select.setVehicle_Fuel(VehicleFuelType.getVehicleFuelTypeName((String) vehicle[2]));
				select.setVehicle_Odometer((Integer) vehicle[3]);
				select.setVehicleGroupId((long) vehicle[4]);
				select.setVehicle_ExpectedOdameter((Integer) vehicle[5]);
				select.setVehicleGPSId((String) vehicle[6]);
				select.setFuelMeter((Integer) vehicle[7]);
				select.setvStatusId((short) vehicle[8]);
				select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(select.getvStatusId()));
				select.setGpsVendorId((Integer) vehicle[9]);
				if(vehicle[10] != null)
					select.setFuelDateTime((Date) vehicle[10]);
				if(vehicle[11] != null)
					select.setGpsOdameter((double) vehicle[11]);
				if(vehicle[12] != null)
					select.setFuel_id((long) vehicle[12]);
				select.setLastFuelOdometer((Integer) vehicle[13]);
				select.setFuel_Number(((long) vehicle[14 ]));
				
			}
			return select;
		} catch (Exception e) {
			System.err.println("Exception Next Fuel Details : "+e);
			e.printStackTrace();
			return null;
		}
	
	}
	
	
	@Override
	public FuelDto getVehicleLastFuelDetailsByVidDate(String fuelDate, Integer vid) throws Exception {
		Query query = entityManager.createQuery(
				"SELECT F.fuel_id, F.fuel_Number, F.fuel_date, F.fuelDateTime, F.fuel_meter "
				+ " FROM Fuel F "
				+ " INNER JOIN Vehicle VH ON VH.vid = F.vid "
				+ " WHERE F.fuel_date < '"+fuelDate+"' AND F.vid = "+vid+" AND F.markForDelete = 0 order by F.fuel_date desc");
		
		
		query.setMaxResults(1);
		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		
		FuelDto select = null;
		if (result != null) {
			select = new FuelDto();
			select.setFuel_id((Long) result[0]);
			select.setFuel_Number((Long) result[1]);
			select.setFuel_D_date((Date) result[2]);
			if(result[3] != null) {
				select.setFuelDateTime((Date) result[3]);
			}else {
				select.setFuelDateTime(select.getFuel_D_date());
			}
			select.setFuel_meter((Integer) result[4]);
			
			if(select.getFuelDateTime() != null) {
				select.setFuelDateTimeStr(formatSQL.format(select.getFuelDateTime()));
			}
		}
		
		return	select;
			
	}
	
	@Override
	public List<FuelDto> validateFuelReferenceInFinancailYear(String fromDate, String toDate, String reference, Integer vendorId)
			throws Exception {
		try {

			TypedQuery<Object[]> query = null;

			query = entityManager.createQuery(
					" SELECT FL.fuel_id, FL.fuel_Number"
							+ " from Fuel FL " 
							+ " WHERE FL.fuelDateTime between '"+fromDate+"' AND '"+toDate+"' "
							+ " AND FL.vendor_id = "+vendorId+" AND FL.fuel_reference = '"+reference+"'  AND FL.markForDelete = 0 ",
							Object[].class);
			List<Object[]> results = query.getResultList();

			List<FuelDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<FuelDto>();
				FuelDto select = null;
				for (Object[] vehicle : results) {

					select = new FuelDto();
					select.setFuel_id((long) vehicle[0]);
					select.setFuel_Number((long) vehicle[1]);

					Dtos.add(select);
				}
			}
			return Dtos;

		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<FuelDto> validateFuelReferenceInFinancailYear(String fromDate, String toDate, String reference,
			Integer vendorId, Long fuelId) throws Exception {
		try {

			TypedQuery<Object[]> query = null;

			query = entityManager.createQuery(
					" SELECT FL.fuel_id, FL.fuel_Number"
							+ " from Fuel FL " 
							+ " WHERE FL.fuelDateTime between '"+fromDate+"' AND '"+toDate+"' AND FL.fuel_id <> "+fuelId+" "
							+ " AND FL.vendor_id = "+vendorId+" AND FL.fuel_reference = '"+reference+"'  AND FL.markForDelete = 0 ",
							Object[].class);
			List<Object[]> results = query.getResultList();

			List<FuelDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<FuelDto>();
				FuelDto select = null;
				for (Object[] vehicle : results) {

					select = new FuelDto();
					select.setFuel_id((long) vehicle[0]);
					select.setFuel_Number((long) vehicle[1]);

					Dtos.add(select);
				}
			}
			return Dtos;

		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getFuelDetailsById(ValueObject valueObject) throws Exception {
		FuelDto 						fuel 				= null;
		CustomUserDetails 				userDetails 		= null;
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			fuel 			= getFuelDetails(valueObject.getLong("fuel_id",0), userDetails.getCompany_id());
			
			/* if(valueObject.getBoolean("getStockFromInventoryConfig")) {
				if(fuel.getFuelInvoiceId() == null || fuel.getFuelInvoiceId() == 0) {
					valueObject.put("fuel", FuBL.getFuelId(fuel));
					return valueObject;
				}else {
					fuelDetails 	= getfueldetailsbyfuelInvoiceIdAndFuelId(fuel.getFuel_id(),fuel.getFuelInvoiceId(),userDetails.getCompany_id());
					
					if(fuelDetails != null) {
						valueObject.put("alreadyExist",true);
						return valueObject;
					}
				}
			} */
			valueObject.put("fuel", FuBL.getFuelId(fuel));
			return valueObject;
		} catch (Exception e) {
			throw e ;    
		}
	}
	
	@Override
	@Transactional
	public ValueObject updateFuelEntries(ValueObject object) throws Exception {
		FuelDto 						fuelDto 			= null;
		CustomUserDetails 				userDetails 		= null;
		HashMap<String, Object> 		configuration 		= null;
		PendingVendorPayment			payment 			= null;
		FuelStockDetails				fuelStockDetails	= null;
		FuelStockDetails				oldfuelStockDetails	= null;
		boolean	 						isFuelChanged		= false;
		Fuel 							oldFuel		 		= null;
		Fuel 							OldFuelDetails		= null;
		HashMap<String, Object>  		gpsConfiguration	= null;
		HashMap<String, Object>  		companyConfiguration	= null;
		VehicleDto				 		CheckVehicleStatus 	= null;
		Double 							gpsUsageKM 			= 0.0;	
		HashMap<String, Object> 		tripConfiguration 		= null;
		Long							oldPaidByBranchId       = null;
		try {    		 

			userDetails			= new CustomUserDetails(object.getInt("companyId",0), object.getLong("userId",0));
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			companyConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			tripConfiguration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			java.util.Date 		currentDate 		= new java.util.Date();
			DateFormat 			ft 					= new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
			java.util.Date 		dateTo 				= dateFormatTime.parse(ft.format(currentDate));
			java.sql.Date 		toDate 				= new java.sql.Date(dateTo.getTime());
			
			CheckVehicleStatus 	= vehicleService.Get_Vehicle_Current_Status_TripSheetID(object.getInt("FuelSelectVehicle"), userDetails.getCompany_id());
			fuelDto				= FuBL.getUpdateFuelDto(object);   
			oldFuel		 		= getFuel(fuelDto.getFuel_id(), userDetails.getCompany_id());
			OldFuelDetails      = getFuel(fuelDto.getFuel_id(), userDetails.getCompany_id());
			oldPaidByBranchId  = oldFuel.getPaidByBranchId();
			

			final Double	previousFuelAmount	 = fuelDto.getPreviousFuelAmount();
			final String 	previousFuelDate	 = fuelDto.getPreviousFuelDate();
			boolean			creatingBackDateFuel = fuelDto.isCreatingBackDateFuel();
			boolean			nextFuelEntryFound   = fuelDto.isNextFuelEntryFound();
			short			oldPaymentTypeId	 = oldFuel.getPaymentTypeId();
			Double			oldFuelLiters		 = oldFuel.getFuel_liters();

			if(oldPaymentTypeId == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && oldFuel.getFuel_vendor_paymodeId() != ServiceEntriesType.PAYMENT_MODE_NOT_PAID) {
				object.put("paymentDone", true);
				object.put("accessToken", Utility.getUniqueToken(request));
				return object;
			}else if(fuelDto.getFuel_TripsheetID() != null && fuelDto.getFuel_TripsheetID() > 0 && tripSheetService.getLimitedTripSheetDetails(fuelDto.getFuel_TripsheetID()).getTripStutesId() == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED) {
				object.put("tripAcClosed", true);
				object.put("accessToken", Utility.getUniqueToken(request));
				return object;
			}
			if(object.getShort("ownPetrolPump") == 1)
			fuelStockDetails	=	fuelInvoiceService.getFuelStockDetailsByPetrolPumpId(object.getInt("selectVendor",0), userDetails.getCompany_id());
			if(oldFuel.getVendor_id() != object.getInt("selectVendor",0) && object.getShort("ownPetrolPump") == 1 && !(boolean) configuration.getOrDefault("getStockFromInventory", false)) {
				if(fuelStockDetails == null) {
					object.put("noStock", true);
					object.put("accessToken", Utility.getUniqueToken(request));
					return object;
				}else if(object.getDouble("fuel_liters",0) > fuelStockDetails.getStockQuantity()) {
					object.put("insufficientStock", true);
					object.put("accessToken", Utility.getUniqueToken(request));
					return object;
				}else {
					object.put("fuel_price", fuelStockDetails.getAvgFuelRate());
				}

			}else if(oldFuel.getVendor_id() == object.getInt("selectVendor",0) && object.getShort("ownPetrolPump") == 1 && !(boolean) configuration.getOrDefault("getStockFromInventory", false)) {
				if(!oldFuel.getFuel_liters().equals(fuelDto.getFuel_liters()) && ((oldFuel.getFuel_liters() - fuelDto.getFuel_liters()) < 0 )) {
					if(fuelStockDetails == null) {
						object.put("noStock", true);
						return object;
					}else if(fuelDto.getFuel_liters()- oldFuel.getFuel_liters() > fuelStockDetails.getStockQuantity()) {
						object.put("insufficientStock", true);
						return object;
					}else {
						object.put("fuel_price", fuelStockDetails.getAvgFuelRate());
					}
				}
			}

			object.put("userDetails", userDetails);
			object.put("configuration", configuration);

			object 	=	  getFuelDto(fuelDto, object);

			Fuel fuel  = (Fuel) object.get("fuel");
			fuel.setFuel_document(oldFuel.isFuel_document());
			fuel.setFuel_document_id(oldFuel.getFuel_document_id());

			if(!fuel.equals(oldFuel)) {
				isFuelChanged = true;
			}

			if((boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION) && CheckVehicleStatus.getGpsVendorId() != null && CheckVehicleStatus.getGpsVendorId() > 0) {
				try {
					String fuelDate = formatSQL.format(fuel.getFuelDateTime());
					FuelDto preFuel = getVehicleLastFuelDetailsByVidDate(fuelDate, fuel.getVid());
					if(preFuel != null) {
						try {
							gpsUsageKM	=	vehicleGPSDetailsService.getGPSRunKMBetweenTwoDates(fuel.getVid(),preFuel.getFuelDateTimeStr(), fuelDate , userDetails.getCompany_id());
						} catch (Exception e) {
							System.err.println("Exception while getting gps run km fuel "+e);
						}

						fuel.setGpsUsageKM(gpsUsageKM);
					}
				} catch (Exception e) {

				}
			}
			
			try {
				if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH || fuel.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
					try {
						fuel.setFuel_vendor_paymentdate(toDate);

						if(fuel.getFuel_TripsheetID() != null && fuel.getFuel_TripsheetID() > 0) {

							UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId());
							TripSheet tsheet = new TripSheet();
							tsheet.setTripSheetID(fuel.getFuel_TripsheetID());
							TripSheetExpense TripExpense = new TripSheetExpense();
							TripExpense.setExpenseId((int) fuel.getFuelTypeId());
							TripExpense.setExpenseAmount(fuel.getFuel_amount());
							TripExpense.setExpensePlaceId(Orderingname.getBranch_id());
							TripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
							TripExpense.setCreatedById(userDetails.getId());
							TripExpense.setTripsheet(tsheet);
							TripExpense.setFuel_id(fuel.getFuel_id());
							TripExpense.setCreated(toDate);
							TripExpense.setCompanyId(userDetails.getCompany_id());
							TripExpense.setBalanceAmount(TripExpense.getExpenseAmount());

							if(oldFuel.getPaymentTypeId() != fuel.getPaymentTypeId() || oldPaidByBranchId >0 && fuel.getPaidByBranchId() <=0) {
								tripSheetService.addTripSheetExpense(TripExpense);
								tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(fuel.getFuel_TripsheetID(), userDetails.getCompany_id());
							}
							
							  TripSheet trip = tripSheetService.getTripSheetData(fuel.getFuel_TripsheetID(), fuel.getCompanyId());
							  Double driverBalance=null;
							  if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED && (boolean)tripConfiguration.get("addInDriverbalanceAfterTripClose")) {
									driverBalance = trip.getDriverBalance();
									if(driverBalance != null) 
										driverBalance = driverBalance - fuel.getFuel_amount();
									else
										driverBalance =  - fuel.getFuel_amount();
									
									tripSheetService.updateDriverBalance(trip.getTripSheetID(), driverBalance, userDetails.getCompany_id());
								}
						  
						}else {
							if(!fuel.getFuel_amount().equals(previousFuelAmount)) {
								TripSheetExpense TripExpense = tripSheetService.getTripSheetExpenseByFuelId(fuel.getFuel_id());
								if(TripExpense != null) {
									TripExpense.setExpenseAmount(fuel.getFuel_amount());
									tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(fuel.getFuel_TripsheetID(), userDetails.getCompany_id());
								}
							}
						}

					} catch (ParseException e) {

						e.printStackTrace();
					}

				} else {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
					
					tripSheetService.removeTripSheetFuel(fuel.getFuel_id());
					tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(oldFuel.getFuel_TripsheetID(), userDetails.getCompany_id());
				}

				
				if(!(boolean) configuration.get("allowToEditFuelRef")) {
					fuel.setFuel_reference(oldFuel.getFuel_reference());
				}
				fuel.setMarkForDelete(false);
				
				if ((boolean) configuration.get("validateUniqueReference") && fuel.getVendor_id() > 0) {
					String fromDate = DateTimeUtility.getFirstDayOfFiscalYear(fuel.getFuel_date(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					String endDate	= DateTimeUtility.getLastDayOfFiscalYear(fuel.getFuel_date(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					
					List<FuelDto>	refValidate = validateFuelReferenceInFinancailYear(fromDate, endDate, fuel.getFuel_reference(), fuel.getVendor_id(), oldFuel.getFuel_id());
					
					if (refValidate != null && !refValidate.isEmpty()) {
						object.put("refExists", true);
						return  object;
					}
				}
				updateFuel(fuel);

				if(object.getBoolean("nextFuelAvailable",false)  && fuel.getFuel_meter_attributes() != 1) {
					Fuel nextFuel 		= (Fuel) object.get("nextFuel");
					if(nextFuel != null) {
						Integer			fuelUsage			= nextFuel.getFuel_meter() - fuel.getFuel_meter();
						java.util.Date 	currentDateUpdate 	= new java.util.Date();
						Timestamp 	   	updatedDate 		= new java.sql.Timestamp(currentDateUpdate.getTime());

						if(nextFuel.getFuel_tank() == 0) {
							Double nextKMPL = fuelUsage/nextFuel.getFuel_liters();
							Double nextCost = nextFuel.getFuel_amount() / fuelUsage;
							
							updateNextFuelCostKmpl(nextFuel.getFuel_id(), nextCost ,nextKMPL, fuelUsage, userDetails.getId(), updatedDate, fuel.getFuel_meter());

						}else {
							updateNextFuelCostKmpl(nextFuel.getFuel_id(), object.getDouble("nextCost",0), object.getDouble("nextKmpl",0),
									fuelUsage, userDetails.getId(), updatedDate, fuel.getFuel_meter());
						}
					}
				}
				
				
				if ((boolean) tripConfiguration.getOrDefault("saveDriverLedgerDetails", false) 
						&& fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH
						&& (!fuel.getFuel_amount().equals(previousFuelAmount) || fuel.getPaymentTypeId() != oldPaymentTypeId)  ) {
					ValueObject driveLedgerObj = new ValueObject();

					driveLedgerObj.put("companyId", userDetails.getCompany_id());
					driveLedgerObj.put("userId", userDetails.getId());
					driveLedgerObj.put("driverId", fuel.getDriver_id());
					if(fuel.getPaymentTypeId() != oldPaymentTypeId)
						driveLedgerObj.put("amount", fuel.getFuel_amount());
					else
						driveLedgerObj.put("amount", (fuel.getFuel_amount() - previousFuelAmount));
					driveLedgerObj.put("transactionId", fuel.getFuel_TripsheetID());
					driveLedgerObj.put("txnType", DriverLedgerTypeConstant.FUEL_ENTRY_EDIT);
					driveLedgerObj.put("subTransactionId", fuel.getFuel_id());
					driveLedgerObj.put("description", "Fuel Entry Edit On Vehicle : " + CheckVehicleStatus.getVehicle_registration()
							+ " For Fuel Number : " + fuel.getFuel_Number());

					driverLedgerService.addDriverLedgerDetails(driveLedgerObj);
				}else if(fuel.getPaymentTypeId() != oldPaymentTypeId && fuel.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CASH) {
					ValueObject driveLedgerObj = new ValueObject();

					driveLedgerObj.put("companyId", userDetails.getCompany_id());
					driveLedgerObj.put("userId", userDetails.getId());
					driveLedgerObj.put("driverId", fuel.getDriver_id());
					driveLedgerObj.put("amount", - previousFuelAmount);
					driveLedgerObj.put("transactionId", fuel.getFuel_TripsheetID());
					driveLedgerObj.put("txnType", DriverLedgerTypeConstant.FUEL_ENTRY_EDIT);
					driveLedgerObj.put("subTransactionId", fuel.getFuel_id());
					driveLedgerObj.put("description", "Fuel Entry Edit On Vehicle : " + CheckVehicleStatus.getVehicle_registration()
							+ " For Fuel Number : " + fuel.getFuel_Number());

					driverLedgerService.addDriverLedgerDetails(driveLedgerObj);
				}


				if(oldPaymentTypeId != fuel.getPaymentTypeId() && fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForFuel(fuel);
					pendingVendorPaymentService.savePendingVendorPayment(payment);
					
				}else if(oldPaymentTypeId == fuel.getPaymentTypeId() && fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && isFuelChanged) {
					payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForFuel(fuel);
					pendingVendorPaymentService.updatePendingVendorPayment(payment);
				}
				if((boolean)configuration.get("fuleEntryPaymentOptions"))
				{
					if(oldPaymentTypeId == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && fuel.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT)
					{
						pendingVendorPaymentService.deletePendingVendorPayment(fuel.getFuel_id(), PendingPaymentType.PAYMENT_TYPE_FUEL_ENTRIES);
					}
				}
				else
				{
					if(oldPaymentTypeId != fuel.getPaymentTypeId() && fuel.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
						pendingVendorPaymentService.deletePendingVendorPayment(fuel.getFuel_id(), PendingPaymentType.PAYMENT_TYPE_FUEL_ENTRIES);
					}
				}
				
				
				
				
					if((boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails", false)) {
					
					ValueObject bankPaymentValueObject = JsonConvertor
							.toValueObjectFormSimpleJsonString(object.getString("bankPaymentDetails"));
					
					bankPaymentValueObject.put("oldPaymentTypeId", oldPaymentTypeId);
					bankPaymentValueObject.put("currentPaymentTypeId",fuel.getPaymentTypeId());
					bankPaymentValueObject.put("userId", userDetails.getId());
					bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
					bankPaymentValueObject.put("moduleId", fuel.getFuel_id());
					bankPaymentValueObject.put("moduleNo", fuel.getFuel_Number());
					bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.FUEL_ENTRY);
					bankPaymentValueObject.put("amount",fuel.getFuel_amount());
					bankPaymentValueObject.put("paidDate", fuel.getFuel_date());
					
					Vendor	vendor	=  vendorService.getVendor(fuel.getVendor_id());
					bankPaymentValueObject.put("remark", "Update Fuel Entry F-"+fuel.getFuel_Number()+" vendor : "+vendor.getVendorName());
					
					bankPaymentValueObject.put("paidByBranchId", fuel.getPaidByBranchId());
					bankPaymentValueObject.put("paidByBranch", object.getBoolean("paidByBranch"));
					bankPaymentValueObject.put("oldpaidByBranchId", oldPaidByBranchId);
					
					bankPaymentService.updatePaymentDetailsFromValuObject(bankPaymentValueObject);
					}
				
					
					if(oldPaidByBranchId <=0 && fuel.getPaidByBranchId()>0) {
						tripSheetExpenseRepository.updateTripsheetExpenseByFuelId(fuel.getFuel_id(), userDetails.getCompany_id(),true);
					}
					if(oldPaidByBranchId >0 && fuel.getPaidByBranchId() <=0) {
						System.err.println("inside make expense false");
						tripSheetExpenseRepository.updateTripsheetExpenseByFuelId(fuel.getFuel_id(), userDetails.getCompany_id(),false);
					}
				// fuel stock updation 

				oldfuelStockDetails	=	fuelInvoiceService.getFuelStockDetailsByPetrolPumpId(object.getInt("oldVendorId"), userDetails.getCompany_id());
				if(object.getInt("oldVendorId") != object.getInt("selectVendor",0) && object.getShort("ownPetrolPump") == 1) {
					fuelInvoiceService.updateFuelStockDetails(object.getInt("selectVendor",0), fuelStockDetails.getAvgFuelRate(), - fuel.getFuel_liters(), - (fuelStockDetails.getAvgFuelRate() *  fuel.getFuel_liters()));
					if(object.getShort("preOwnPetrolPump") == 1) {
						fuelInvoiceService.updateFuelStockDetails(object.getInt("oldVendorId"), oldfuelStockDetails.getAvgFuelRate(), oldFuelLiters,  (oldfuelStockDetails.getAvgFuelRate() *  oldFuelLiters));
					}
				}else if(object.getInt("oldVendorId") == object.getInt("selectVendor",0) && object.getShort("ownPetrolPump") == 1 && !oldFuelLiters.equals(fuel.getFuel_liters())) {
					fuelStockDetails	=	fuelInvoiceService.getFuelStockDetailsByPetrolPumpId(object.getInt("selectVendor",0), userDetails.getCompany_id());
					fuelInvoiceService.updateFuelStockDetails(fuel.getVendor_id(), fuelStockDetails.getAvgFuelRate(), oldFuelLiters - fuel.getFuel_liters(),  (oldFuelLiters - fuel.getFuel_liters()) * fuelStockDetails.getAvgFuelRate());
				}else if(object.getInt("oldVendorId") != object.getInt("selectVendor",0) && object.getShort("ownPetrolPump") == 0 && object.getShort("preOwnPetrolPump") == 1) {
					fuelStockDetails	=	fuelInvoiceService.getFuelStockDetailsByPetrolPumpId(object.getInt("oldVendorId"), userDetails.getCompany_id());
					fuelInvoiceService.updateFuelStockDetails(object.getInt("oldVendorId"), oldfuelStockDetails.getAvgFuelRate(), oldFuelLiters,  (oldfuelStockDetails.getAvgFuelRate()*oldFuelLiters));
				}
				
				

				if((boolean) configuration.getOrDefault("getStockFromInventory", false)) {
					
				FuelInvoice			fuelInvoice					= fuelInvoiceService.getFuelInvoiceDetailsByFuelInvoiceId(object.getLong("oldFuelInvoiceId"), userDetails.getCompany_id());
				if(fuelInvoice != null) {
				FuelInvoiceHistory	fuelInvoiceHistory			= FuelInvoiceBL.prepareFuelInvoiceHistory(fuelInvoice);
					fuelInvoiceHistoryRepository.save(fuelInvoiceHistory);
				}	
					object.put("oldFuelLiters", oldFuelLiters);
					object.put("currentFuelLiters", fuel.getFuel_liters());
					
					updateStockDetailsFromFuelInvoice(object,userDetails.getCompany_id());
				}


				VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= null;
				if(!(DateTimeUtility.getDateTimeFromStr(previousFuelDate, DateTimeUtility.DD_MM_YYYY).equals(new Timestamp(fuel.getFuel_date().getTime()))) || (fuel.getFuel_amount() - previousFuelAmount) != 0) {
					
					ValueObject		txnCheckerObj	= new ValueObject();
					
					txnCheckerObj.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					txnCheckerObj.put(VehicleProfitAndLossDto.TRANSACTION_ID, fuel.getFuel_id());
					txnCheckerObj.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					txnCheckerObj.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					txnCheckerObj.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					txnCheckerObj.put(VehicleProfitAndLossDto.TRANSACTION_VID, fuel.getVid());
					txnCheckerObj.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, fuel.getFuel_id());
					
					profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(txnCheckerObj);
					
					vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
					
				}		

				// update the Current Odometer vehicle Databases tables
				if(creatingBackDateFuel == false && nextFuelEntryFound == false) {
					
					Integer CurrentOdometer = vehicleService
							.updateCurrentOdoMeterGETVehicleToCurrentOdometer(fuelDto.getVid());
					if (CurrentOdometer < fuelDto.getFuel_meter()) {
						vehicleService.updateCurrentOdoMeter(fuel.getVid(), fuel.getFuel_meter(), userDetails.getCompany_id());

						// update current Odometer update Service Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(fuel.getVid(),
								fuel.getFuel_meter(), userDetails.getCompany_id());

						vehicleOdometerHistoryService.updateVehicleOdometerHistory(fuel.getFuel_meter(),
								fuel.getFuel_id());
					} else {
						vehicleOdometerHistoryService.updateVehicleOdometerHistory(fuel.getFuel_meter(),
								fuel.getFuel_id());
					}

				}
				ValueObject	ownerShipObject	= null;	
				if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED){
					if((fuel.getFuel_amount() - previousFuelAmount) != 0 || !(DateTimeUtility.getDateTimeFromStr(previousFuelDate, DateTimeUtility.DD_MM_YYYY).equals(new Timestamp(fuel.getFuel_date().getTime())))) {

						ownerShipObject	= new ValueObject();
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, fuel.getFuel_id());
						ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, fuel.getVid());
						ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, fuel.getFuel_amount());
						ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
						ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_FUEL_ENTRY);
						ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, fuel.getCompanyId());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, fuel.getFuel_date());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Fuel Entry");
						ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Fuel Number : "+fuel.getFuel_Number());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, fuel.getFuel_comments());
						ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, fuel.getCreatedById());
						if(DateTimeUtility.getDateTimeFromStr(previousFuelDate, DateTimeUtility.DD_MM_YYYY).equals(new Timestamp(fuel.getFuel_date().getTime()))) {
							ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, - (fuel.getFuel_amount() - previousFuelAmount));
							ownerShipObject.put("isDateChanged", false);
						}else {
							ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, - fuel.getFuel_amount());
							ownerShipObject.put("isDateChanged", true);

						}
						ownerShipObject.put("previousAmount", -previousFuelAmount);
						ownerShipObject.put("previousDate", DateTimeUtility.getDateFromString(previousFuelDate, DateTimeUtility.DD_MM_YYYY));


						VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
						vehicleAgentTxnCheckerService.save(agentTxnChecker);

						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
					}

				}


				if(!(DateTimeUtility.getDateTimeFromStr(previousFuelDate, DateTimeUtility.DD_MM_YYYY).equals(new Timestamp(fuel.getFuel_date().getTime()))) || (fuel.getFuel_amount() - previousFuelAmount) != 0) {
					ValueObject		valueObject	= new ValueObject();
					valueObject.put("fuel", fuel);
					valueObject.put("userDetails", userDetails);
					valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					valueObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					
					valueObject.put("isFuelEdit", true);
					valueObject.put("previousFuelAmount", previousFuelAmount);
					valueObject.put("fuelAmountToBeAdded", (fuel.getFuel_amount() - previousFuelAmount));
					
					if(DateTimeUtility.getDateTimeFromStr(previousFuelDate, DateTimeUtility.DD_MM_YYYY).equals(new Timestamp(fuel.getFuel_date().getTime()))) {
						valueObject.put("isFuelDateChanged", false);
					}else {
						valueObject.put("isFuelDateChanged", true);
					}
					valueObject.put("previousFuelDate", DateTimeUtility.getDateTimeFromStr(previousFuelDate, DateTimeUtility.DD_MM_YYYY));
					new Thread() {
						
						public void run() {
							try {
								vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(valueObject);
								vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(valueObject);
								vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(valueObject);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}		
					}.start();
				}
				if(ownerShipObject != null) {
					vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
				}
				
				
			} catch (Exception e) {
				LOGGER.error("Fuel Page:", e);
				e.printStackTrace();

			}
		
			
			return object;
		} catch (Exception e) {
			throw e ;    
		}finally {
			fuelDto 			= null;
			userDetails 		= null;
			payment 			= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public  ValueObject getFuelDto(FuelDto	fuelDto, ValueObject  object)  throws Exception{
		Fuel   fuel		=  null;
		CustomUserDetails	userDetails		= null;
		HashMap<String, Object> configuration	= null;
		try {
			
			userDetails	= (CustomUserDetails) object.get("userDetails");
			configuration	= (HashMap<String, Object>) object.get("configuration");
			
			fuel  =  new  Fuel();
			if(object.getBoolean("fromFuelEdit", false)) {
				fuel.setFuel_id(object.getLong("fuel_id"));
				fuel.setFuel_Number(object.getLong("fuelNumber"));
			}
			if(object.getLong("fuelId",0) > 0)
				fuel.setFuel_id(object.getLong("fuelId",0));
			fuel.setVid(object.getInt("FuelSelectVehicle",0));
			fuel.setGpsOdometer(object.getDouble("gpsOdometer",0));
			fuel.setTallyCompanyId(object.getLong("tallyCompanyId",0));
			fuel.setFuel_liters(object.getDouble("fuel_liters",0));
			fuel.setFuel_meter_old(object.getInt("fuel_meter_old",0));
			fuel.setFuel_meter(object.getInt("fuel_meter",0));
			fuel.setFuelTypeId(object.getShort("fuel_type"));
			fuel.setFuel_price(object.getDouble("fuel_price",0));
			fuel.setFuel_reference(object.getString("fuel_reference"));
			fuel.setDriver_id(object.getInt("VehicleTODriverFuel",0));
			fuel.setSecDriverID(object.getInt("VehicleTODriver2Fuel",0));
			fuel.setCleanerID(object.getInt("VehicleTOCleanerFuel",0));
			fuel.setRouteID(object.getInt("TripRouteList",0));
			fuel.setFuel_comments(object.getString("fuel_comments"));
			if(object.getLong("tripSheetId",0) >  0)
				fuel.setFuel_TripsheetID(object.getLong("tripSheetId",0));
			fuel.setPaymentTypeId(object.getShort("paymentTypeId"));
			fuel.setFuel_tank(object.getInt("fuel_tank",0));
			fuel.setFuel_tank_partial(object.getInt("fuel_tank",0));
			fuel.setFuel_meter_attributes(object.getInt("fuel_meter_attributes",0));
			fuel.setCreatedById(userDetails.getId());
			fuel.setLastModifiedById(userDetails.getId());
			fuel.setCompanyId(userDetails.getCompany_id());
			fuel.setCreated(new  Date());
			fuel.setLastupdated(new  Date());
			if((boolean) configuration.get("allowToAddFuelAmount")) {
				if(object.getDouble("fuelAmount", 0) > 0) {
					fuel.setFuel_amount(round(object.getDouble("fuelAmount", 0), 2));
				}
			}else {
				fuel.setFuel_amount(round(fuel.getFuel_liters() * fuel.getFuel_price(), 2));
			}
			fuel.setFuel_personal(object.getInt("fuel_personal", 0));
			fuel.setLatitude(object.getString("latitude",null));
			fuel.setLongitude(object.getString("longitude",null));
			fuel.setFuelInvoiceId(object.getLong("currentFuelInvoiceId",0));
			java.util.Date fuelDateTime =  null;
			if(fuelDto.getFuelTime() != null && !fuelDto.getFuelTime().trim().equalsIgnoreCase("")) {
				String start_time = "00:00";
				start_time	= fuelDto.getFuelTime();
				
				fuelDateTime = DateTimeUtility.getDateTimeFromDateTimeString(fuelDto.getFuel_date(), start_time);
				fuel.setFuelDateTime(fuelDateTime);
			}
			fuel.setPaidByBranchId(object.getLong("paidByBranchId"));
			if(object.getShort("paidById") >0) {
			fuel.setPaidById(object.getShort("paidById"));
			}
			
			java.util.Date date = dateFormat.parse(fuelDto.getFuel_date());
			Date FuelDate = new Date(date.getTime());
			fuel.setFuel_date(FuelDate);
			if(fuel.getFuel_meter_old() < fuel.getFuel_meter() && object.getInt("fuel_meter_attributes",0) == 0) {
				fuel.setFuel_usage(fuel.getFuel_meter() -  fuel.getFuel_meter_old());
			}else {
			 Fuel fuelMissing =	findMissingOddmeterFirstDESC_Vaule_Liter_AmountFuel(fuel.getVid(), fuel.getFuel_meter(), formatSQL.format(fuelDateTime));
			 if(fuelMissing != null) {
				 fuel.setFuel_meter_old(fuelMissing.getFuel_meter());
				 fuel.setFuel_usage(fuel.getFuel_meter() - fuelMissing.getFuel_meter());
			 }else {
				 fuel.setFuel_usage(0);
			 }
			}
			
			if((boolean)configuration.get("fuleEntryPaymentOptions"))
			{
				
				if(fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH || fuel.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
					java.util.Date currentDate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

					fuel.setFuel_vendor_paymentdate(toDate);
					fuel.setBalanceAmount(0.0);
					}
					else {
						fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
						fuel.setBalanceAmount(fuel.getFuel_amount());
					}
			}
			else
			{
				if(fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
				fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
	
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
	
				fuel.setFuel_vendor_paymentdate(toDate);
				fuel.setBalanceAmount(0.0);
				}
				else {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
					fuel.setBalanceAmount(fuel.getFuel_amount());
				}
			}
			
			
			if (object.getString("selectVendor") != null
					&& FuelDto.getParseVendorID_STRING_TO_ID(object.getString("selectVendor")) != 0) {
				fuel.setVendor_id(FuelDto.getParseVendorID_STRING_TO_ID(object.getString("selectVendor")));
			} else {
				fuel.setVendor_id(saveFuelEntriesToNewVendorCreateAuto(object.getString("selectVendor"), object.getInt("companyId"),object.getLong("userId")));
			}
			
			
			if((object.getInt("fuel_meter_attributes",0) == 0)) {
				/*
				 * checking for next fuel entry available and if next full fuel entry found  
				 */
				Fuel nextFuel  =	getnextFuelEntryOfType(fuel.getVid(), formatSQL.format(fuel.getFuelDateTime()), 0, fuel.getFuel_id());
				if(nextFuel != null) {
					object.put("nextFuel", nextFuel);
					object.put("nextFuelAvailable", true);
				}
				
				
				if(fuel.getFuel_tank() == 0) {
					
					Fuel lastFuel  =	getLastFullEntryDetail(fuel.getVid(), formatSQL.format(fuel.getFuelDateTime()));
					Integer usagePartial = 0;
					Double litersPartial = 0.0;
					Double AmountPartial = 0.0;
					
					/*
					 *  checking  for partial fuel entry available between current  entry  and last full entry	
					 *  all partial fuel entry details will  be included in this  current entry	
					 */
					if(lastFuel  !=  null) {
						object.put("preFuel", lastFuel);
						object.put("preFuelAvailable", true);
						try {
							List<FuelDto> fuelkm = prepareOfGetvehicelpartialFuel(
									listOfGetvehicelpartialFuelBetween(fuel.getVid(), formatSQL.format(lastFuel.getFuelDateTime()), formatSQL.format(fuel.getFuelDateTime()), fuel.getFuel_id()));
							if (fuelkm != null && !fuelkm.isEmpty()) {
								for (FuelDto fuelDto2 : fuelkm) {
									/** Edit Fuel Entries edit id not take the values */
									if (!(fuelDto2.getFuel_id().equals(fuelDto.getFuel_id()))) {
										usagePartial += fuelDto2.getFuel_usage();
										litersPartial += fuelDto2.getFuel_liters();
										if(fuelDto2.getFuel_amount() != null)
											AmountPartial += fuelDto2.getFuel_amount();
									}
									
								}
								fuel.setFuel_comments(fuel.getFuel_comments()  + "_ previous partial  fuel details included in kmpl cost/km ");
								
							}
							
							Integer usagefuelTotal  = usagePartial  + fuel.getFuel_usage();
							Double literTotal 		= litersPartial + fuel.getFuel_liters();
							Double amountfuelTotal  = AmountPartial + fuel.getFuel_amount();
							
							if(usagefuelTotal > 0) {
								fuel.setFuel_kml(round(usagefuelTotal / literTotal, 2));
								fuel.setFuel_cost(round(amountfuelTotal / usagefuelTotal, 2));
							}else {
								fuel.setFuel_kml(0.0);
								fuel.setFuel_cost(0.0);
							}
							
						} catch (Exception e) {
							
							e.printStackTrace();
						}
						
					}else {
						/*
						 *  checking  for  all partial fuel entry available before current  entry	
						 *  all partial fuel entry details will  be included in this  current entry	
						 */
						try {
							List<FuelDto> fuelkm = prepareOfGetvehicelpartialFuel(
									listOfGetvehicelpartialFuel(fuel.getVid(), 1));
							if (fuelkm != null && !fuelkm.isEmpty()) {
								for (FuelDto fuelDto2 : fuelkm) {
									/** Edit Fuel Entries edit id not take the values */
									if (!(fuelDto2.getFuel_id().equals(fuelDto.getFuel_id()))) {
										usagePartial += fuelDto2.getFuel_usage();
										litersPartial += fuelDto2.getFuel_liters();
										if(fuelDto2.getFuel_amount() != null)
											AmountPartial += fuelDto2.getFuel_amount();
									}
									fuel.setFuel_comments(fuel.getFuel_comments()  + "_ previous partial  fuel details included in kmpl cost/km ");
								}
							}
							
							Integer usagefuelTotal  = usagePartial  + fuel.getFuel_usage();
							Double literTotal 		= litersPartial + fuelDto.getFuel_liters();
							Double amountfuelTotal  = AmountPartial + fuel.getFuel_amount();
							
							if(usagefuelTotal > 0) {
								fuel.setFuel_kml(round(usagefuelTotal / literTotal, 2));
								fuel.setFuel_cost(round(amountfuelTotal / usagefuelTotal, 2));
							}
							
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					}
					
					
					/*
					 * checking for next fuel entry available and if next full fuel entry found  
					 * than  we have to remove above partial fuel details from next fuel entry
					 */
					if(nextFuel != null) {
						Double   nextAmount = 0.0;
						Integer  nextUsage  = 0;
						Double   nextLiters = 0.0;
						
						List<FuelDto> nextPartialFuel = prepareOfGetvehicelpartialFuel(
								listOfGetvehicelpartialFuelBetween(fuel.getVid(), formatSQL.format(fuel.getFuelDateTime()), formatSQL.format(nextFuel.getFuelDateTime()),  fuel.getFuel_id()));
						
						if (nextPartialFuel != null && !nextPartialFuel.isEmpty()) {
							for (FuelDto fuelDto2 : nextPartialFuel) {
								/** Edit Fuel Entries edit id not take the values */
								if (fuelDto.getFuel_id() != null && !(fuelDto2.getFuel_id().equals(fuelDto.getFuel_id()))) {
									if(fuelDto2.getFuel_usage() != null)
										nextUsage   += fuelDto2.getFuel_usage();
									if(fuelDto2.getFuel_liters() != null)
										nextLiters  += fuelDto2.getFuel_liters();
									if(fuelDto2.getFuel_amount() != null)
										nextAmount += fuelDto2.getFuel_amount();
								}
								if(nextFuel.getFuel_usage() != null)
									nextUsage += nextFuel.getFuel_usage();
								if(nextFuel.getFuel_liters() != null)
									nextLiters += nextFuel.getFuel_liters();
								if(nextFuel.getFuel_amount() != null)
									nextAmount += nextFuel.getFuel_amount();
								
							}
							if(nextLiters > 0 )
								object.put("nextKmpl", round(nextUsage / nextLiters, 2));
							if(nextUsage > 0 )
								object.put("nextCost", round(nextAmount / nextUsage, 2));
						}	
						
					}
					
				}else {
					
					
					/*
					 * checking for next fuel entry available and if next full fuel entry found  
					 * than  we have to add this partial fuel details to next full fuel  entry
					 */
					if(nextFuel != null) {
						Double    totalFuel 	 = 0.0;
						Integer   totalUsageKM = 0;
						Double	totalCost	 =  0.0;
						
						if(nextFuel.getFuel_liters() != null)
							totalFuel = nextFuel.getFuel_liters()  + fuel.getFuel_liters();
						if(nextFuel.getFuel_usage() != null) {
							totalUsageKM = nextFuel.getFuel_usage()  +  fuel.getFuel_usage();
							if(fuel.getFuel_meter()  > nextFuel.getFuel_meter_old()) {
								totalUsageKM = totalUsageKM -(  fuel.getFuel_meter()  - nextFuel.getFuel_meter_old());
							}
						}
						if(nextFuel.getFuel_amount() != null)
							totalCost = nextFuel.getFuel_amount()  +  fuel.getFuel_amount();
						
						
						object.put("nextKmpl", round(totalUsageKM/totalFuel, 2));
						object.put("nextCost", round(totalCost/ totalUsageKM, 2));
						
					}
				}
			}
			
			if((boolean) configuration.get("showKmplOnPartialEntry")) {
				fuel.setFuel_kml(round(fuel.getFuel_usage()/fuel.getFuel_liters(), 2));
				fuel.setFuel_cost(round(fuel.getFuel_amount()/fuel.getFuel_usage(), 2));
			}
			
			object.put("fuel", fuel);
			return object;
		} catch (Exception e) {
			throw e;
		}finally {
			configuration = null;
			userDetails		= null;
		}
		
	}
	
	public List<FuelDto> prepareOfGetvehicelpartialFuel(List<Fuel> fuelList) {

		List<FuelDto> Dtos = null;

		if (fuelList != null && !fuelList.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;

			for (Fuel fuelDto : fuelList) {

				fuel = new FuelDto();
				fuel.setFuel_id(fuelDto.getFuel_id());
				fuel.setFuel_liters(fuelDto.getFuel_liters());
				fuel.setFuel_usage(fuelDto.getFuel_usage());
				fuel.setFuel_amount(fuelDto.getFuel_amount());

				Dtos.add(fuel);
			}
		}
		return Dtos;
	}
	
	@Override
	public Fuel getLastFullEntryDetail(Integer vid, String dateTime) throws Exception {

		TypedQuery<Fuel> query = entityManager.createQuery(
				"SELECT f "
				+ " FROM Fuel AS f "
						+ " where f.vid = "+vid+" AND f.fuelDateTime < '"+dateTime+"' and f.fuel_tank = 0 AND f.markForDelete = 0  order  by f.fuelDateTime desc", Fuel.class);

		Fuel	fuel = null;
		try {
				query.setMaxResults(1);
				fuel = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return fuel;
	}
	
	@Override
	public List<Fuel> listOfGetvehicelpartialFuelBetween(Integer vid,  String fromDate, String toDate, Long fuelId) {

		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT f.fuel_id, f.fuel_liters, f.fuel_usage, f.fuel_amount from Fuel AS f"
						+ " where f.vid = "+vid+" AND f.fuelDateTime between '" + fromDate + "' AND  '" + toDate
						+ "'  AND f.fuel_tank = 1 AND f.fuel_id <> "+fuelId+" AND f.markForDelete = 0", Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<Fuel> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Fuel>();
			Fuel fuel = null;
			for (Object[] result : results) {
				fuel = new Fuel();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_liters((Double) result[1]);
				fuel.setFuel_usage((Integer) result[2]);
				fuel.setFuel_amount((Double) result[3]);

				Dtos.add(fuel);
			}
		}
		return Dtos;
	}
	
	@Override
	public Fuel getnextFuelEntryOfType(Integer vid, String dateTime, Integer fuel_tank, Long fuelId) throws Exception {
		TypedQuery<Fuel> query = entityManager.createQuery(
				"SELECT f "
				+ " FROM Fuel AS f "
						+ " where f.vid = "+vid+" AND f.fuelDateTime > '"+dateTime+"'  AND f.fuel_tank = "+fuel_tank+" AND fuel_id <> "+fuelId+" "
						+ " AND f.markForDelete = 0  order  by f.fuelDateTime asc", Fuel.class);

		Fuel	fuel = null;
		try {
				query.setMaxResults(1);
				fuel = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return fuel;
	}
	
	@Override
	@Transactional
	public void updateNextFuelCostKmpl(Long fuel_id, Double cost , Double kmpl , Integer usageKM, Long id, Timestamp dateTime,  Integer oldMeter) throws Exception {
		try {
			entityManager.createQuery(
					"Update Fuel SET fuel_meter_old =  "+oldMeter+", fuel_usage = "+usageKM+" , "
							+ " fuel_kml = "+kmpl+", fuel_cost = "+cost+", "
							+ " lastModifiedById = "+id+", lastupdated = '"+dateTime+"' "
							+ " WHERE fuel_id = "+fuel_id+"  ")
			.executeUpdate();
		} catch (Exception e) { 
			
		}
	}
	
	@Override
	@Transactional
	public void updateFuelUsageKm(Long fuel_id, Integer usageKM, Long  id, Timestamp updattime) throws Exception {
		try {
			entityManager.createQuery(
					"Update Fuel SET fuel_usage = "+usageKM+",  lastModifiedById = "+id+", lastupdated = '"+updattime+"' "
							+ " WHERE fuel_id = "+fuel_id+" ")
			.executeUpdate();
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public Fuel findMissingOddmeterFirstDESC_Vaule_Liter_AmountFuel(Integer fuel_vehicleid, Integer fuel_Odd_Meter,
			String fuel_date) throws Exception {

		TypedQuery<Fuel> query = entityManager.createQuery(
				"SELECT f "
				+ " FROM Fuel AS f "
						+ " where f.vid = "+fuel_vehicleid+" AND f.fuelDateTime < '"+fuel_date+"'  AND f.fuel_meter < "+fuel_Odd_Meter+" "
						+ " AND f.markForDelete = 0  order  by f.fuelDateTime desc", Fuel.class);

		Fuel	fuel = null;
		try {
				query.setMaxResults(1);
				fuel = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return fuel;
	}
	
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseFuelExpenseDtoByVid(Integer vid, String fromDate, String toDate,
			Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.fuel_amount), MVE.companyId  "
							+ " FROM Fuel MVE "
							+ " where MVE.vid = "+vid+" AND MVE.fuel_date between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.markForDelete = 0 AND MVE.fuel_amount > 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseType((short)1);
					mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<MonthWiseVehicleExpenseDto> getMonthWiseFuelExpenseDtoByVid(Integer vid, String fromDate, String toDate,
			Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.fuel_amount), MVE.companyId  "
							+ " FROM Fuel MVE "
							+ " where MVE.vid = "+vid+" AND MVE.fuel_date between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.markForDelete = 0 AND MVE.fuel_amount > 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<MonthWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					MonthWiseVehicleExpenseDto	mExpenseDto = new MonthWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseType((short)1);
					mExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<FuelDto> getListOfNumberOfFECreatedOnVehicles(Integer companyID) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;

			queryt = entityManager.createQuery(
					" SELECT COUNT(F.vid), F.vid, V.vehicle_registration "
							+ " FROM Fuel as F "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid AND V.vStatusId <> 4 "
							+ " WHERE F.companyId = "+companyID+" and F.markForDelete = 0 "
							+ " GROUP BY F.vid ",
							Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<FuelDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<FuelDto>();
				FuelDto service = null;
				
					for (Object[] result : results) {
						service = new FuelDto();
						
						service.setCountOfFEOnEachVehicle((Long) result[0]);
						service.setVid((Integer)  result[1]);
						service.setVehicle_registration((String) result[2]);
				
						Dtos.add(service);
					}
			}
				return Dtos;

		} catch (Exception e) {
		throw e;
		}

	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseFuelExpenseDtoByRouteId(Integer vid, String fromDate, String toDate, Integer companyId, Integer routeId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.fuel_amount), MVE.companyId  "
							+ " FROM Fuel MVE "
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = MVE.fuel_TripsheetID "
							+ " where MVE.vid = "+vid+" AND MVE.fuel_date between '"+ fromDate +"' AND '"+toDate+"'"
									+ " AND MVE.companyId = "+companyId+" AND TS.routeID = "+routeId+" "
									+ " AND MVE.markForDelete = 0 AND MVE.fuel_amount > 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseType((short)1);
					mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseFuelExpenseDtoByVehicleTypeId(Integer vid, String fromDate,
			String toDate, Integer companyId, Long vehicleTypeId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.fuel_amount), MVE.companyId  "
							+ " FROM Fuel MVE "
							+ " INNER JOIN Vehicle TS ON TS.vid = MVE.vid "
							+ " where MVE.vid = "+vid+" AND MVE.fuel_date between '"+ fromDate +"' AND '"+toDate+"'"
							+ " AND MVE.companyId = "+companyId+" AND TS.vehicleTypeId = "+vehicleTypeId+" "
							+ " AND MVE.markForDelete = 0 AND MVE.fuel_amount > 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseType((short)1);
					mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseFuelExpenseDtoByVTRouteId(Integer vid, String fromDate, String toDate, Integer companyId, Long vehicleTypeId, Integer routeId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.fuel_amount), MVE.companyId  "
							+ " FROM Fuel MVE "
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = MVE.fuel_TripsheetID "
							+ " INNER JOIN Vehicle V ON V.vid = MVE.vid "
							+ " where MVE.vid = "+vid+" AND MVE.fuel_date between '"+ fromDate +"' AND '"+toDate+"'"
							+ " AND MVE.companyId = "+companyId+" AND TS.routeID = "+routeId+" AND V.vehicleTypeId = "+vehicleTypeId+" "
							+ " AND MVE.markForDelete = 0 AND MVE.fuel_amount > 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseType((short)1);
					mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	@Override
	public void deleteFuelByTripSheetId(Long tripSheetId) throws Exception {
		fuelDao.deleteFuelByTripSheetId(tripSheetId);
	}
	
	@Override
	public List<FuelDto> listFuelEntriesByTripSheetId(Long tripSheetId) throws Exception {
		
		TypedQuery<Object[]> query = null;
			query = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id" + " FROM Fuel as F "
							+ " INNER JOIN  Vehicle V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Vendor VN ON VN.vendorId = F.vendor_id"
							+ " LEFT JOIN Driver D ON D.driver_id = F.driver_id" + " where F.fuel_TripsheetID = "
							+ tripSheetId + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc", Object[].class);



		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();

				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setPaymentTypeId((short) result[20]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				fuel.setFuel_cost((Double) result[29]);
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);

				Dtos.add(fuel);
			}
		}
		return Dtos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getFuelListOutTripForTally(String fromDate, String toDate, Integer companyId,
			String tallyCompany, String ledgerName) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT F.fuel_id, F.fuel_Number, VSD.expectedPaymentDate, F.fuel_comments, V.vendorName,"
					+ " VH.vehicle_registration, VH.ledgerName, TC.companyName, F.created, F.paymentTypeId,F.vid, "
					+ " VSD.subApprovalpaidAmount, F.isPendingForTally, F.fuel_date "
					+ " FROM Fuel F "
					+ " INNER JOIN Vehicle VH ON VH.vid = F.vid "
					+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = F.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
					+ " INNER JOIN VendorApproval VA on VA.approvalId = F.fuel_vendor_approvalID AND VA.markForDelete = 0"
					+ " INNER JOIN VendorSubApprovalDetails VSD ON VSD.approvalId = VA.approvalId AND VSD.invoiceId = F.fuel_id AND VSD.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_FUEL_ENTRIES+""
					+ " LEFT JOIN Vendor AS V ON V.vendorId = F.vendor_id"
					+ " WHERE VA.approvalCreateDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND F.companyId = "+companyId+" AND F.fuel_TripsheetID is null AND F.markForDelete = 0 AND VSD.subApprovalpaidAmount > 0");
				
			
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
						select.setTripExpenseID((Long) vehicle[0]);
						select.setTripSheetNumber((Long) vehicle[1]);
						if(vehicle[2] != null)
							select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[2]));
						select.setRemark((String) vehicle[3]);
						select.setVendorName((String) vehicle[4]);
						select.setVehicle_registration((String) vehicle[5]);
						select.setLedgerName((String) vehicle[6]);
						select.setTallyCompanyName((String) vehicle[7]);
						select.setCreated((Date) vehicle[8]);
						select.setExpenseFixedId((short) vehicle[9]);
						select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
						select.setTripSheetId((Long) vehicle[0]);
						select.setVid((Integer) vehicle[10]);
						select.setExpenseAmount((Double) vehicle[11]);
						select.setPendingForTally((boolean) vehicle[12]);
						
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_FUEL);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						select.setExpenseName(ledgerName);
						
						
						
						select.setTripSheetNumberStr("F-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
						
						if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
							select.setCredit(true);
						}else {
							select.setCredit(false);
						}
						
						if(select.getVendorName() == null ) {
							select.setVendorName("--");
						}
						if(select.getCreated() != null ) {
							select.setCreatedStr(dateFormat.format((java.util.Date)vehicle[2]));
						}
						
						 select.setRemark("Fuel Entry On Vehicle "+select.getVehicle_registration()+" Date: "+dateFormat.format((java.util.Date)vehicle[13])+" Fuel No : "+select.getTripSheetNumberStr()+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						
						if(vehicle[2] != null)
							Dtos.add(select);
					}
				}
				
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<FuelDto> vehicleWiseFuelEntriesList(Integer vid, Integer companyId) throws Exception {

		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		TypedQuery<Object[]> query = null;

		if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
			query = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id, TS.tripSheetNumber, F.fuel_vendor_paymentdate,"
							+ " F.created, F.lastupdated, F.fuel_vendor_approvalID, F.createdById" + " FROM Fuel as F "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
							+ " LEFT JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
							+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
							+ " where F.vid=" + vid + " AND F.companyId = " + companyId
							+ " AND F.markForDelete = 0 ORDER BY F.fuel_date DESC , F.fuel_id DESC", Object[].class);

		} else if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
			query = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id, TS.TRIPCOLLNUMBER, F.fuel_vendor_paymentdate,"
							+ " F.created, F.lastupdated, F.fuel_vendor_approvalID, F.createdById" + " FROM Fuel as F "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
							+ " LEFT JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
							+ " LEFT JOIN TripCollectionSheet TS ON TS.TRIPCOLLID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
							+ " where F.vid=" + vid + " AND F.companyId = " + companyId
							+ " AND F.markForDelete = 0 ORDER BY F.fuel_date DESC , F.fuel_id DESC", Object[].class);

		} else if ((int) configuration
				.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR) == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
			query = entityManager
					.createQuery("SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id, TS.TRIPDAILYNUMBER, F.fuel_vendor_paymentdate,"
							+ " F.created, F.lastupdated, F.fuel_vendor_approvalID, F.createdById" + " FROM Fuel as F "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
							+ " LEFT JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
							+ " LEFT JOIN TripDailySheet TS ON TS.TRIPDAILYID =  F.fuel_TripsheetID AND TS.markForDelete = 0"
							+ " where F.vid=" + vid + " AND F.companyId = " + companyId
							+ " AND F.markForDelete = 0 ORDER BY F.fuel_date DESC , F.fuel_id DESC", Object[].class);

		}
		query.setMaxResults(25);
		List<Object[]> results = query.getResultList();

		List<FuelDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;
			for (Object[] result : results) {
				fuel = new FuelDto();
				// fuel_vendor_paymentdate
				fuel.setFuel_id((Long) result[0]);
				fuel.setFuel_Number((Long) result[1]);
				fuel.setFuel_TripsheetID((Long) result[2]);
				fuel.setVid((Integer) result[3]);
				fuel.setVehicle_registration((String) result[4]);
				fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
				fuel.setVehicle_OwnershipId((short) result[5]);
				fuel.setFuel_D_date((Date) result[6]);
				fuel.setFuel_meter((Integer) result[7]);
				fuel.setFuel_meter_old((Integer) result[8]);
				fuel.setFuel_meter_attributes((Integer) result[9]);
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
				fuel.setFuelTypeId((short) result[10]);
				fuel.setFuel_liters((Double) result[11]);
				fuel.setFuel_price((Double) result[12]);// result[2]
				fuel.setVehicle_group((String) result[13]);
				fuel.setDriver_id((Integer) result[14]);
				fuel.setDriver_name((String) result[15]);
				fuel.setDriver_empnumber((String) result[16]);
				fuel.setVendor_id((Integer) result[17]);
				fuel.setVendor_name((String) result[18]);
				fuel.setVendor_location((String) result[19]);
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
				fuel.setPaymentTypeId((short) result[20]);
				fuel.setFuel_reference((String) result[21]);
				fuel.setFuel_personal((Integer) result[22]);
				fuel.setFuel_tank((Integer) result[23]);
				fuel.setFuel_tank_partial((Integer) result[24]);
				fuel.setFuel_comments((String) result[25]);
				fuel.setFuel_usage((Integer) result[26]);
				fuel.setFuel_amount((Double) result[27]);
				fuel.setFuel_kml((Double) result[28]);
				if( result[29] != null) {
					fuel.setFuel_cost((Double) result[29]);
				}else {
					fuel.setFuel_cost(0.0);
				}
				fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
				fuel.setFuel_vendor_paymodeId((short) result[30]);
				fuel.setFuel_document((boolean) result[31]);
				fuel.setFuel_document_id((Long) result[32]);
				fuel.setFuel_TripsheetNumber((Long) result[33]);
				fuel.setFuel_vendor_paymentdate((Date) result[34]);
				fuel.setCreatedOn((Date) result[35]);
				fuel.setLastupdatedOn((Date) result[36]);
				fuel.setFuel_vendor_approvalID((Long) result[37]);
				fuel.setCreatedById((Long) result[38]);
				
				org.fleetopgroup.persistence.document.FuelDocument file = get_Fuel_Document_Details(fuel.getFuel_document_id(), companyId);
				if(file != null) {
					fuel.setFuelBase64Document(ByteConvertor.byteArraytoBase64(file.getFuel_content()));
				}
				
				Dtos.add(fuel);
			}
		}
		return Dtos;

	}
	
	@Override
	public ValueObject searchFuelEntriesByVehicle(ValueObject object) throws Exception {
		int 								companyId 				= 0;
		long 								userId 					= 0;
		List<FuelDto> 						fuelList 				= null;
		CustomUserDetails 					userDetails 			= null;
		try {
			companyId 					= object.getInt("companyId");
			userId 						= object.getLong("userId");
			
			userDetails 				= new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			fuelList = FuBL.prepareListofFuel(vehicleWiseFuelEntriesList(object.getInt("vid"), companyId));
			
			if(fuelList != null) {
				object.put("fuelFound", true);
				object.put("fuelList", fuelList);
			} else {
				object.put("fuelNotFound", true);
			}

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			fuelList 		= null;
			userDetails 	= null;
		}
	}
	
	
	@Override
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidFuelList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception {
		List<VendorPaymentNotPaidDto> 	Dtos 					= null;
		CustomUserDetails 				userDetails 			= null;
		HashMap<String, Object> 		configuration 			= null;
		TypedQuery<Object[]> 			query 					= null;
		short 							paymentTypeCreadit 		= 0;
		short 							notPaid 				= 0;
		short 							approved 				= 0;
		short 							partiallyPaid 			= 0;
		try {
			paymentTypeCreadit 	= PaymentTypeConstant.PAYMENT_TYPE_CREDIT;
			notPaid 			= FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID;
			approved			= FuelVendorPaymentMode.PAYMENT_MODE_APPROVED;
			partiallyPaid		= FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID;

			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);

			if (!(boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION)) {
				query = entityManager.createQuery(
						"SELECT F.fuel_id, F.fuel_Number, F.vid, V.vehicle_registration, VN.vendorName, "
								+" F.fuel_date, F.paymentTypeId, F.fuel_amount, F.balanceAmount "
								+ " FROM Fuel as F "
								+ " INNER JOIN Vehicle V on V.vid = F.vid "
								+ " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id"
								+ " where (( F.vendor_id='" + vendor_id+"' AND F.paymentTypeId=" + paymentTypeCreadit
								+ " AND F.fuel_vendor_paymodeId IN('" + notPaid + "','" + approved + "','" + partiallyPaid + "')) "
								+ " AND F.fuel_date between '" + dateFrom + "' AND '" + dateTo + "' ) "
								+ " AND F.companyId = " + companyId + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);
			} else {

				query = entityManager.createQuery(
							"SELECT F.fuel_id, F.fuel_Number, F.vid, V.vehicle_registration, VN.vendorName, "
								+ " F.fuel_date, F.paymentTypeId, F.fuel_amount, F.balanceAmount "
								+ " FROM Fuel as F "
								+ " INNER JOIN Vehicle V on V.vid = F.vid "
								+ " INNER JOIN Vendor VN ON VN.vendorId = F.vendor_id "
								+ " where (( F.vendor_id='" + vendor_id+"' AND F.paymentTypeId=" + paymentTypeCreadit
								+ " AND F.fuel_vendor_paymodeId IN('" + notPaid + "','" + approved + "','" + partiallyPaid + "')) "
								+ " AND F.fuel_date between '" + dateFrom + "' AND '" + dateTo + "' ) "
								+ " AND F.companyId = " + companyId + " AND F.markForDelete = 0 ORDER BY F.fuel_id desc",
								Object[].class);

			}
			List<Object[]> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VendorPaymentNotPaidDto>();
				VendorPaymentNotPaidDto list = null;
				for (Object[] result : results) {
					list = new VendorPaymentNotPaidDto();
					
					list.setSerialNumberId((Long) result[0]);
					list.setSerialNumber((Long) result[1]);
					list.setSerialNumberStr("FE-"+list.getSerialNumber());
					list.setVid((Integer) result[2]);
					list.setVehicleRegistration((String) result[3]);
					list.setVendorName((String) result[4]);
					
					if(result[1] != null) {
						list.setInvoiceNumber((long) result[1]+"");
					} else {
						list.setInvoiceNumber("-");
					}
					
					list.setInvoiceDateStr(dateFormat.format((java.util.Date) result[5]));
					list.setPaymentType(PaymentTypeConstant.getPaymentTypeName((short) result[6]));
					
					if(result[7] != null) {
						list.setInvoiceAmount((Double) result[7]);
					} else {
						list.setInvoiceAmount(0.0);
					}
					
					if(result[8] != null) {
						list.setBalanceAmount((Double) result[8]);
					} else {
						list.setBalanceAmount(0.0);
					}

					Dtos.add(list);
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("e"+e);
		}
		return Dtos;
	}
	
	@Override
	public Fuel getFuelCreatedBetweenDates(String startDate, String endDate,Integer companyId)
			throws Exception {
		TypedQuery<Fuel> query = entityManager.createQuery(
				"SELECT F "
						+ " From Fuel as F "
						+ " WHERE F.created between '"+startDate+"' AND '"+endDate+"' AND F.companyId = "+companyId+" AND F.markForDelete = 0 ", Fuel.class);

		Fuel	fuel = null;
		try {
				query.setMaxResults(1);
				fuel = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return fuel;
	}
	
	@Override
	@Transactional
	public ValueObject updateFuelGPSUsage(ValueObject object) throws Exception {
		Fuel		currentFuel			= null;
		Fuel		previousFuel		= null;
		Double		gpsUSageKM			= null;
		try {
			SimpleDateFormat format	= new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			currentFuel		= fuelDao.getCurrentFuelDetails(object.getLong("fuelId",0));
			previousFuel	= fuelDao.getPreviousFuelDetails(currentFuel.getVid(), currentFuel.getFuelDateTime());
			if(currentFuel != null && previousFuel != null) {
				gpsUSageKM	=	vehicleGPSDetailsService.getGPSRunKMBetweenTwoDates(currentFuel.getVid(), format.format(previousFuel.getFuelDateTime()), format.format(currentFuel.getFuelDateTime()), currentFuel.getCompanyId());			
				fuelDao.updateGPSUsageKM(currentFuel.getFuel_id(), gpsUSageKM);
			}
			return object;
		} catch (Exception e) {
			throw e;
		}finally {
			currentFuel			= null;
			previousFuel		= null;
		}
	}
	
	@Override
	public ValueObject getMissingFuelEntryAlertByCompanyId(Integer companyId) throws Exception {
		List<VehicleDto>					vehicleList					= null;
		List<VehicleDto> 					vehicleFinalList			= null;
		List<VehicleDto> 					noFuelList					= null;
		ValueObject							valueObject 				= null;
		HashMap<Integer, VehicleDto>		finalFuelHM					= null;
		VehicleDto							vehicleDto					= null;
		List<Fuel> 							FuelList 					= null;
		HashMap<Integer, Integer>			noFuelHM					= null;
		try { 
			valueObject				= new ValueObject();
			noFuelList				= new ArrayList<>();
			vehicleFinalList		= new ArrayList<>();
			vehicleList				= vehicleService.getActiveVehicleList(companyId);
			noFuelHM				= new HashMap<>();
			finalFuelHM				= new HashMap<>();
			
			FuelList 				= fuelDao.listFuelEmtriesCreatedOnVehicles(companyId);
			
			if(!FuelList.isEmpty()) {
				for(int i =0; i <FuelList.size() ; i++) {
					noFuelHM.put(Integer.parseInt(""+FuelList.get(i)), Integer.parseInt(""+FuelList.get(i)));
				}
			}
			
			if(vehicleList != null && !vehicleList.isEmpty()) {
				finalFuelHM 	= getLastFuelEntryDetailList(companyId);
				
				for(VehicleDto dto : vehicleList){
					if(!noFuelHM.containsKey(dto.getVid())) {
						noFuelList.add(dto);
					}
					
					vehicleDto	= new VehicleDto();
					if((finalFuelHM != null && !finalFuelHM.isEmpty()) && (finalFuelHM.containsKey(dto.getVid())) && (dto.getVehicle_Odometer() > (dto.getVehicle_ExpectedOdameter() + finalFuelHM.get(dto.getVid()).getFuelMeter()))) {
						vehicleDto.setVid(dto.getVid());
						vehicleDto.setFuel_id(finalFuelHM.get(dto.getVid()).getFuel_id());
						vehicleDto.setVehicle_registration(dto.getVehicle_registration());
						vehicleDto.setFuel_Number(finalFuelHM.get(dto.getVid()).getFuel_Number());
						vehicleDto.setFuelDateStr(finalFuelHM.get(dto.getVid()).getFuelDateStr());
						vehicleDto.setFuelMeter(finalFuelHM.get(dto.getVid()).getFuelMeter());
						vehicleDto.setVehicle_Odometer(dto.getVehicle_Odometer());
						vehicleDto.setVehicle_ExpectedOdameter(dto.getVehicle_ExpectedOdameter());
						vehicleFinalList.add(vehicleDto);
					}
				}
			}
			
			valueObject.put("noFuelList", noFuelList);
			valueObject.put("vehicleFinalList", vehicleFinalList);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			vehicleFinalList = null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<Integer, VehicleDto> getLastFuelEntryDetailList(Integer companyId) throws Exception {
		Query 			query 		= null;
		HashMap<Integer, VehicleDto> 	fuelHM 		= null;
		try {
			fuelHM 	= new HashMap<>();
			query = entityManager.createNativeQuery(
					"SELECT F.fuel_id, F.fuel_Number, F.vid, F.fuel_meter, F.fuelDateTime "
							+ " FROM Fuel AS F "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid AND V.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE +","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE +","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP +") "
							+ " INNER JOIN ( SELECT fuel_id, fuel_Number, vid, fuel_meter , MAX(fuelDateTime) AS MaxFuelDateTime FROM Fuel GROUP BY vid ) MFD ON MFD.vid = F.vid AND MFD.MaxFuelDateTime = F.fuelDateTime "
							+ " AND F.markForDelete = 0 AND F.companyId = "+companyId+" "
							);
			
			
			List<Object[]> results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				
				VehicleDto select = null;
				for (Object[] vehicle : results) {
	
					select = new VehicleDto();
					select.setFuel_id(Long.parseLong(vehicle[0]+""));
					select.setFuel_Number(Long.parseLong(vehicle[1]+""));
					select.setVid((int) vehicle[2]);
					select.setFuelMeter((int) vehicle[3]);
					select.setFuelDateStr(formatSQL.format((Timestamp) vehicle[4]));
					fuelHM.put(select.getVid(), select);
				}
				
			}
			
			return fuelHM;
		
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public FuelDto getTripsheetFuelMileage(Long	tripSheetId, Integer companyId) throws Exception {
		Query query = entityManager.createQuery(
				"SELECT F.fuel_id, F.fuel_liters, F.fuel_usage, T.tripSheetNumber, V.vehicle_ExpectedMileage "
				+ " FROM Fuel F "
				+ " INNER JOIN TripSheet T ON T.tripSheetID = F.fuel_TripsheetID "
				+ " INNER JOIN Vehicle V ON V.vid = T.vid "
				+ " WHERE F.fuel_TripsheetID = "+tripSheetId+" AND F.companyId = "+companyId+" AND F.markForDelete = 0 order by F.fuel_date desc");
		List<Object[]> results = query.getResultList();
		FuelDto mielage = new FuelDto(); 
		if (results != null && !results.isEmpty()) {
			FuelDto select = null;
			mielage.setFuel_liters(0.0);
			mielage.setFuel_usage(0);
			for (Object[] result : results) {
				select = new FuelDto();
				select.setFuel_id((Long) result[0]);
				select.setFuel_liters((Double) result[1]);
				select.setFuel_usage((Integer) result[2]);
				mielage.setTripSheetNumber("TS-"+ result[3]+"");
				mielage.setFuel_cost((Double) result[4]);
				mielage.setFuel_liters(mielage.getFuel_liters() + select.getFuel_liters());
				mielage.setFuel_usage(mielage.getFuel_usage() + select.getFuel_usage());
			}
			mielage.setFuel_TripsheetID(tripSheetId);
			mielage.setFuel_kml(mielage.getFuel_usage()/mielage.getFuel_liters());
		}
		return	mielage;
	}

	public ValueObject checkFuelEntryExistOnSameDateAndTime(ValueObject object) throws Exception {
		boolean                            isAlreadyExist   = false;
		Integer                            vid              = 0;
		String                             fuel_Date        = "";
		String                             fuel_Time        = "";
		Date                               fuelDateTime     = null;
		String                             start_time       = "00:00";
		try{
			vid            = object.getInt("vid",0);
			fuel_Date      = object.getString("fuelDate","");
			fuel_Time      = object.getString("fuelTime",start_time);
			start_time     = fuel_Time;
			fuelDateTime = DateTimeUtility.getDateTimeFromDateTimeString(fuel_Date, start_time);
			String tripActiveDateAndTime = formatSQL.format(fuelDateTime);

			Query  query = entityManager.createQuery(
					"SELECT F.fuel_id FROM Fuel F WHERE F.vid = "+vid+" AND F.fuelDateTime = '"+tripActiveDateAndTime+"' AND F.markForDelete = 0 ");
			try {
				Long resulValue =  (Long) query.getSingleResult();
				if (resulValue != null && resulValue > 0) {
					isAlreadyExist = true;
					object.put("fuelId", resulValue);
				}
				object.put("isAlreadyExist", isAlreadyExist);
			} catch (NoResultException nre) {
				object.put("isAlreadyExist", isAlreadyExist);
				return object;
				// Ignore this because as per your logic this is ok!
			}
			return object;
		}catch(Exception  e){
			e.printStackTrace();
			throw e;
		}
	}

	public ValueObject searchFuelEntriesByVehicleNumberAndDateRange(ValueObject object) throws Exception {
		int                                 vehicleid               = 0;
		String 								dateRangeFrom 			= "";
		String 								dateRangeTo 			= "";
		ValueObject							dateRange				= null;
		String                              query                   = "";
		
		try{
			vehicleid			    = object.getInt("vid", 0);
			dateRange			    = DateTimeUtility.getStartAndEndDateStr(object.getString("dateRange",""));
			if(dateRange!=null){
				dateRangeFrom 		= dateRange.getString(DateTimeUtility.FROM_DATE,"");
				dateRangeTo 		= dateRange.getString(DateTimeUtility.TO_DATE,"");
			}
			String Vid          = "";
			String Fuel_from_AND_To = "";
			if (vehicleid > 0) {
				Vid = " AND F.vid=" + vehicleid + " ";
			}

			if (dateRangeFrom != "" && dateRangeTo != "") {
				Fuel_from_AND_To = " AND   F.fuel_date between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' ";
				query = " " + Vid + " "  + Fuel_from_AND_To + " ";
			} else {
				query = " " + Vid + " ";
				Fuel_from_AND_To = " AND   F.fuel_date between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' ";
			}
			object.put("fuel", FuBL.prepareListofFuel(listFuelReport(query,object)));
			return object;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	public ValueObject getFuelStockDetailsForFuelEntry(ValueObject valueObject, Integer companyId ) throws Exception {
		FuelStockDetails				fuelStockDetails			=	null;
		try {
			
			synchronized (xMutexFactory.getMutex(valueObject.getInt("selectVendor",0))) {
				
				HashMap<String, Object> 		configuration				= (HashMap<String, Object>) valueObject.get("configuration");
				fuelStockDetails	=	fuelInvoiceService.getFuelStockDetailsByPetrolPumpId(valueObject.getInt("selectVendor",0), companyId);
				valueObject.put("fuelStockDetails", fuelStockDetails);
				if(fuelStockDetails == null) {
					valueObject.put("fuelStockDetails", fuelStockDetails);
					valueObject.put("noStock", true);
					valueObject.put("accessToken", Utility.getUniqueToken(request));
					return valueObject;
				}else if(valueObject.getDouble("fuel_liters",0) > fuelStockDetails.getStockQuantity()) {
					valueObject.put("insufficientStock", true);
					valueObject.put("accessToken", Utility.getUniqueToken(request));
					return valueObject;
				}else {
					if(!(boolean) configuration.getOrDefault("getStockFromInventory", false)) {
						valueObject.put("fuel_price", fuelStockDetails.getAvgFuelRate());
					}
				}
		}
			
			
		return valueObject;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	public void updateStockDetailsFromFuelInvoice(ValueObject valueObject, Integer companyId ) throws Exception {
		try {
			valueObject.put("stockTypeId",  FuelInvoiceConstant.STOCK_TYPE_FE_EDIT );
			
			if(valueObject.getInt("oldVendorId",0) == valueObject.getInt("selectVendor",0) && valueObject.getShort("ownPetrolPump") == 1) {
				
				
				valueObject.put("updatedStock", (valueObject.getDouble("oldFuelLiters") - valueObject.getDouble("currentFuelLiters")));
				valueObject.put("fuelInvoiceId", valueObject.getLong("currentFuelInvoiceId"));
				valueObject.put("remark", FuelInvoiceConstant.getStockType(FuelInvoiceConstant.STOCK_TYPE_FE_EDIT ));
				fuelInvoiceService.updateFuelStockDetailsInFuelInvoice(valueObject);
			}else if(valueObject.getInt("oldVendorId",0) != valueObject.getInt("selectVendor",0)) {
				if(valueObject.getShort("ownPetrolPump") == 1 && valueObject.getShort("preOwnPetrolPump") == 0) {
					valueObject.put("updatedStock", (-valueObject.getDouble("currentFuelLiters")));
					valueObject.put("fuelInvoiceId", valueObject.getLong("currentFuelInvoiceId"));
					valueObject.put("remark", "vendor has Changed from No OwnPetrolPump to ownPetrolPump");
					fuelInvoiceService.updateFuelStockDetailsInFuelInvoice(valueObject);
					
				}else if(valueObject.getShort("ownPetrolPump") == 0 && valueObject.getShort("preOwnPetrolPump") == 1) {
					valueObject.put("updatedStock", valueObject.getDouble("oldFuelLiters"));
					valueObject.put("fuelInvoiceId", valueObject.getLong("oldFuelInvoiceId"));
					valueObject.put("oldVendorOwnPump", true);
					valueObject.put("remark", "vendor has Changed from ownPetrolPump to No OwnPetrolPump");
					fuelInvoiceService.updateFuelStockDetailsInFuelInvoice(valueObject);
					
				}else if(valueObject.getShort("ownPetrolPump") == 1 && valueObject.getShort("preOwnPetrolPump") == 1) {
					
					valueObject.put("updatedStock", (-valueObject.getDouble("currentFuelLiters")));
					valueObject.put("fuelInvoiceId", valueObject.getLong("currentFuelInvoiceId"));
					valueObject.put("remark", "vendor has Changed ");
					
					fuelInvoiceService.updateFuelStockDetailsInFuelInvoice(valueObject);
					
					valueObject.put("updatedStock", valueObject.getDouble("oldFuelLiters"));
					valueObject.put("fuelInvoiceId", valueObject.getLong("oldFuelInvoiceId"));
					valueObject.put("oldVendorOwnPump", true);
					valueObject.put("remark", "vendor has Changed ");
					fuelInvoiceService.updateFuelStockDetailsInFuelInvoice(valueObject);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public Fuel getfueldetailsbyfuelInvoiceId(Long fuelInvoiceId, Integer companyId) throws Exception {
		try {
			return fuelDao.getfueldetailsbyfuelInvoiceId(fuelInvoiceId, companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Fuel getfueldetailsbyfuelInvoiceIdAndFuelId(Long fuelId, Long fuelInvoiceId, Integer companyId) throws Exception {
		try {
			return fuelDao.getfueldetailsbyfuelInvoiceIdAndFuelId(fuelId,fuelInvoiceId, companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception {
		entityManager.createQuery("  UPDATE Fuel SET fuel_vendor_paymentdate='" + paymentDate+"' , "
				+ " fuel_vendor_paymodeId="+paymentStatus+""
				+ " WHERE fuel_vendor_approvalID = "+approvalId+" ").executeUpdate();
		
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception {
		
		entityManager.createQuery("  UPDATE Fuel SET fuel_vendor_paymentdate= " + paymentDate+" , "
				+ " fuel_vendor_paymodeId="+paymentStatus+", fuel_vendor_approvalID = "+approvalId+" "
				+ " WHERE fuel_id = "+invoiceId+" ").executeUpdate();
		
	}
	
	
	
	
	@Override
	public List<FuelDto> getUserWiseFEActivityList( String queryStr ,String innerQuery) throws Exception {
		TypedQuery<Object[]> query = null;
		try {
			query = entityManager.createQuery(
					"SELECT F.fuel_id, F.fuel_Number, F.fuel_TripsheetID, F.vid, V.vehicle_registration, "
							+ " V.vehicleOwnerShipId, F.fuel_date, F.fuel_meter, F.fuel_meter_old, F.fuel_meter_attributes, F.fuelTypeId"
							+ " ,F.fuel_liters, F.fuel_price, VG.vGroup, F.driver_id, D.driver_firstname, D.driver_empnumber, F.vendor_id,"
							+ " VN.vendorName, VN.vendorLocation, F.paymentTypeId, F.fuel_reference, F.fuel_personal, F.fuel_tank, F.fuel_tank_partial,"
							+ " F.fuel_comments, F.fuel_usage, F.fuel_amount, F.fuel_kml, F.fuel_cost, F.fuel_vendor_paymodeId,"
							+ " F.fuel_document, F.fuel_document_id, TS.tripSheetNumber, F.fuel_vendor_paymentdate ,F.created ,F.lastupdated,"
							+ " F.markForDelete,F.createdById,F.lastModifiedById,U.firstName, U.lastName ,U.id" 
							+ " FROM Fuel as F "
							+ " "+innerQuery+" "
							+ " INNER JOIN Vehicle AS V ON V.vid = F.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN Vendor AS VN ON VN.vendorId = F.vendor_id "
							+ " LEFT JOIN Driver AS D ON D.driver_id = F.driver_id "
							+ " LEFT JOIN TripSheet TS ON TS.tripSheetID =  F.fuel_TripsheetID "
							+ " WHERE  "+ queryStr +" ", 
							Object[].class);
	List<Object[]> results = query.getResultList();

	List<FuelDto> Dtos = null;
	if (results != null && !results.isEmpty()) {
		Dtos = new ArrayList<>();
		FuelDto fuel = null;
		for (Object[] result : results) {
			fuel = new FuelDto();

			fuel.setFuel_id((Long) result[0]);
			fuel.setFuel_Number((Long) result[1]);
			fuel.setFuel_TripsheetID((Long) result[2]);
			fuel.setVid((Integer) result[3]);
			fuel.setVehicle_registration((String) result[4]);
			fuel.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName((short) result[5]));
			fuel.setFuel_D_date((Date) result[6]);
			if(result[6] != null)
			fuel.setFuel_date(dateFormat.format(result[6]));
			fuel.setFuel_meter((Integer) result[7]);
			fuel.setFuel_meter_old((Integer) result[8]);
			fuel.setFuel_meter_attributes((Integer) result[9]);
			fuel.setFuel_type(VehicleFuelType.getVehicleFuelName((short) result[10]));
			if(result[11] != null) {
				fuel.setFuel_liters(Double.parseDouble(toFixedTwo.format(result[11])));
			}else {
				fuel.setFuel_liters(0.0);
			}
			fuel.setFuel_price((Double) result[12]);// result[2]
			fuel.setVehicle_group((String) result[13]);
			fuel.setDriver_id((Integer) result[14]);
			fuel.setDriver_name((String) result[15]);
			fuel.setDriver_empnumber((String) result[16]);
			fuel.setVendor_id((Integer) result[17]);
			fuel.setVendor_name((String) result[18]);
			fuel.setVendor_location((String) result[19]);
			fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName((short) result[20]));
			fuel.setFuel_reference((String) result[21]);
			fuel.setFuel_personal((Integer) result[22]);
			fuel.setFuel_tank((Integer) result[23]);
			fuel.setFuel_tank_partial((Integer) result[24]);
			fuel.setFuel_comments((String) result[25]);
			fuel.setFuel_usage((Integer) result[26]);
			if(result[27] != null) {
				fuel.setFuel_amount(Double.parseDouble(toFixedTwo.format(result[27])));
			}else {
				fuel.setFuel_amount(0.0);
			}
			fuel.setFuel_kml((Double) result[28]);
			fuel.setFuel_cost((Double) result[29]);
			fuel.setFuel_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[30]));
			fuel.setFuel_document((boolean) result[31]);
			fuel.setFuel_document_id((Long) result[32]);
			fuel.setFuel_TripsheetNumber((Long) result[33]);
			if(result[34]!= null) {
				fuel.setFuel_vendor_paymentdate((Timestamp) result[34]);
			}
			if(result[35]!= null)
			fuel.setCreated(dateFormat.format(result[35]));
			if(result[36]!= null)
			fuel.setLastupdated(dateFormat.format(result[36]));
			fuel.setMarkForDelete((boolean)result[37]);
			if(!fuel.isMarkForDelete()) {
				fuel.setFuelNumber("<a target=\"_blank\" style=\"color: blue; background: #ffc;\"  href=\"showFuel.in?FID="+fuel.getFuel_id()+" \"> F-"+fuel.getFuel_Number()+" </a>");
			}else {
				fuel.setFuelNumber("<a style=\"color: red; background: #ffc;\"  href=\"# \" data-toggle=\"tip\" data-original-title=\"Deleted Data\" > F-"+fuel.getFuel_Number()+" </a>");
			}
			fuel.setCreatedById((Long)result[38]);
			fuel.setLastModifiedById((Long)result[39]);
			fuel.setUserName(result[40]+" "+result[41]);
			fuel.setUserId((Long)result[42]);
			Dtos.add(fuel);
		}
	}
	return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
			
	}
	
	
	@Override
	@Transactional
	public void saveIntangleFuelPullAPI() throws Exception {
		List<Vehicle> 					vehicleList 					= null;
		long							fromDate						= 0;//unix timestamp will return long
		long							toDate							= 0;//unix timestamp will return long
		Integer							vehicelId						= null;
		String							vehicelNumber					= "";
		JSONObject						jsonObject 						= null;
		JSONObject						result 							= null;
		IntangleFuelEntryDetails		intangleFuelEntryDetailsBL 		= null;
		List<IntangleFuelEntryDetails> 	intangleFuelEntryDetailsList 	= null;
		HashMap<String, Object> 		configuration	          		= null;
		String							intangleFuelPreURL				= "";
		String							intangleFuelPostURL				= "";
		String							intangleFuelApiAsscessToken		= "";
		int								noOfBackDate					= 1;
		JSONArray	jSONArray	=  null;
		IntangleFuelEntryDetails 		validateIntangleFuelEntryDetails = null; 
		try {
			 intangleFuelEntryDetailsList	= new ArrayList<>();
			 vehicleList		 			= vehicleService.getVehicleListByGpsVenordId(VehicleGPSVendorConstant.GPS_VENDOR_INTANGLES);
			 configuration					= companyConfigurationService.getCompanyConfiguration(0, PropertyFileConstants.VEHICLE_GPS_CONFIG);
			 intangleFuelPreURL 			= (String) configuration.getOrDefault("intangleFuelPreURL", "");
			 intangleFuelPostURL 			= (String) configuration.getOrDefault("intangleFuelPostURL", "");
			 intangleFuelApiAsscessToken 	= (String) configuration.getOrDefault("intangleFuelAPI_AsscessToken", "");
			 fromDate						= DateTimeUtility.getUnixEpochStartTimeTimestampInMilliseconds(noOfBackDate);
			 toDate							= DateTimeUtility.getUnixEpochEndTimeTimestampInMilliseconds(noOfBackDate);
			 
			 for(Vehicle vehicle : vehicleList) {

					vehicelNumber	= vehicle.getVehicle_registration().replace("-", "");
					vehicelId  		= vehicle.getVid();

					Unirest.setTimeouts(0, 0);
					HttpResponse<String> response = Unirest.get(""+intangleFuelPreURL+""+vehicelNumber+"/"+fromDate+"/"+toDate+""+intangleFuelPostURL+"")
					  .header("Api-access-token", ""+intangleFuelApiAsscessToken+"")
					  .asString();

					if(response.getStatus() == 200) {
						result		= new JSONObject();
						jsonObject	= new JSONObject(response.getBody());
						
						if(jsonObject.has("result")) {
							result 		= (JSONObject) jsonObject.get("result");
							jSONArray	= result.getJSONArray("logs");
							
							if (jSONArray != null && jSONArray.length() > 0 ) { 
								JSONObject	object	= null;
								for (int i=0; i<jSONArray.length(); i++){ 
									object	= (JSONObject) jSONArray.get(i);
									object.put("vid",vehicelId);
									object.put("companyId",vehicle.getCompany_Id());
									Date date = new Date(Long.parseLong(object.getString("timestamp")));
									Timestamp fuelDate = DateTimeUtility.getTimeStampFromDate(date);
									object.put("fuelDate",fuelDate);
									
									validateIntangleFuelEntryDetails = intangleFuelEntryDetailsRepository.validateIntangleFuelEntryDetails(object.getString("id"),vehicelId,fuelDate,object.getLong("odo"),object.getDouble("amount"),vehicle.getCompany_Id());
									if(validateIntangleFuelEntryDetails != null) {
										continue;
									}
									
									intangleFuelEntryDetailsBL =  FuBL.prepareIntangleFuelEntryDetails(object);
									intangleFuelEntryDetailsList.add(intangleFuelEntryDetailsBL);
								}
								intangleFuelEntryDetailsRepository.saveAll(intangleFuelEntryDetailsList);
							}
						}
					}
				}
			 

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	@Override
	@Transactional
	public ValueObject prepareInvoiceWiseFuelEntry(ValueObject valueObject,MultipartFile uploadfile,CustomUserDetails userDetails,HashMap<String,Object> configuration) throws Exception {
		List<FuelInvoice> fuelInvoiceList = null;
		double quantity =0.0;
		try {
			valueObject.put("configuration", configuration);
			if(valueObject.getShort("ownPetrolPump") == 1 && valueObject.getInt("selectVendor",0) > 0) {
				
				
				Double stockBalanceSum =fuelInvoiceService.getBalanceStockFromInvoice(valueObject.getInt("selectVendor",0), valueObject.getInt("companyId"));
				if(stockBalanceSum == null) {
					valueObject.put("noStock", true);
					valueObject.put("accessToken", Utility.getUniqueToken(request));
					return valueObject;
				} else if(stockBalanceSum < valueObject.getDouble("fuel_liters", 0.0) || stockBalanceSum <= 0) {
					valueObject.put("insufficientStock", true);
					valueObject.put("accessToken", Utility.getUniqueToken(request));
					return valueObject;
					}
					
					fuelInvoiceList= fuelInvoiceRepository.getFuelInvoiceListByBalanceStock(valueObject.getInt("selectVendor",0),valueObject.getInt("companyId"));
			 		if(fuelInvoiceList != null && !fuelInvoiceList.isEmpty()) {
			 			quantity =valueObject.getDouble("fuel_liters", 0.0);
			 			for(FuelInvoice fuelInvoice : fuelInvoiceList) {
			 				if(quantity <= 0)
			 					break;
			 				
			 				if(fuelInvoice.getBalanceStock() >= quantity) {
			 					valueObject.put("fuel_liters", quantity);
			 				}else {
			 					valueObject.put("fuel_liters", fuelInvoice.getBalanceStock());
			 				}
			 				
			 				valueObject.put("fuel_price", eachPriceForFuleInvoice(fuelInvoice));	
			 				valueObject.put("fuelInvoiceId", fuelInvoice.getFuelInvoiceId());
			 				valueObject.put("currentFuelInvoiceId", fuelInvoice.getFuelInvoiceId());
			 				valueObject.put("fuel_reference", valueObject.getString("fuel_reference", "")+"_"+fuelInvoice.getFuelInvoiceId());
			 				quantity -= valueObject.getDouble("fuel_liters", 0.0);
			 				valueObject=saveFuelEntryDetails(valueObject, uploadfile);
			 			}
			 		}
				
				}else{
				return saveFuelEntryDetails(valueObject, uploadfile);
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public double eachPriceForFuleInvoice(FuelInvoice fuelInvoice) throws Exception {
		double eachPrice  = 0.0;
		try {
			eachPrice= fuelInvoice.getTotalAmount()/fuelInvoice.getQuantity();
			return eachPrice ;
		} catch (Exception e) {
			return eachPrice ;
		}
	}
	
	@Override
	public List<Fuel> getDriverWiseFuelList(int driverId,String queryStr ,int companyId){

		List<Fuel> fuelList = null;
		TypedQuery<Fuel> query = entityManager.createQuery( "FROM Fuel AS f where  f.driver_id =:id AND f.fuel_tank_partial=0 "+queryStr+" AND f.companyId = " + companyId
				+ " AND f.markForDelete = 0",Fuel.class);
		query.setParameter("id",driverId);
		try {
			fuelList= query.getResultList();
		}catch (Exception e){
			//ignore
		}
		return fuelList;
	}
	
@SuppressWarnings("unchecked")
@Override
	public List<TripSheetExpenseDto> getFuelListWithoutApprovalForTally(String fromDate, String toDate,
			Integer companyId, String tallyCompany, String ledgerName) throws Exception {
	try {
		Query query = entityManager.createQuery(
				"SELECT F.fuel_id, F.fuel_Number, F.fuel_date, F.fuel_comments, V.vendorName,"
				+ " VH.vehicle_registration, VH.ledgerName, TC.companyName, F.created, F.paymentTypeId,F.vid, "
				+ " F.fuel_amount, F.isPendingForTally, F.fuel_date, F.fuel_reference "
				+ " FROM Fuel F "
				+ " INNER JOIN Vehicle VH ON VH.vid = F.vid "
				+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = F.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
				+ " LEFT JOIN Vendor AS V ON V.vendorId = F.vendor_id"
				+ " WHERE F.fuel_date between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
				+ " AND F.companyId = "+companyId+" AND F.markForDelete = 0 AND F.fuel_amount > 0");
			
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
					select.setTripExpenseID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					if(vehicle[2] != null)
						select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[2]));
					select.setRemark((String) vehicle[3]);
					select.setVendorName((String) vehicle[4]);
					select.setVehicle_registration((String) vehicle[5]);
					select.setLedgerName((String) vehicle[6]);
					select.setTallyCompanyName((String) vehicle[7]);
					select.setCreated((Date) vehicle[8]);
					select.setExpenseFixedId((short) vehicle[9]);
					select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
					select.setTripSheetId((Long) vehicle[0]);
					select.setVid((Integer) vehicle[10]);
					select.setExpenseAmount((Double) vehicle[11]);
					select.setPendingForTally((boolean) vehicle[12]);
					
					select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_FUEL);
					select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
					select.setExpenseName(ledgerName);
					
					select.setTripSheetNumberStr("F-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
					
					if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
						select.setCredit(true);
					}else {
						select.setCredit(false);
					}
					
					if(select.getVendorName() == null ) {
						select.setVendorName("--");
					}
					if(select.getCreated() != null ) {
						select.setCreatedStr(dateFormat.format((java.util.Date)vehicle[2]));
					}
					
					 select.setRemark("Fuel Entry On Vehicle "+select.getVehicle_registration()+" Date: "+dateFormat.format((java.util.Date)vehicle[13])+" Fuel No : "+select.getTripSheetNumberStr()+" From: "+select.getVendorName()+" Fuel Reference : "+vehicle[14]);
					
					if(select.getTallyCompanyName() == null) {
						select.setTallyCompanyName("--");
					}
					if(select.getLedgerName() == null) {
						select.setLedgerName("--");
					}
					
					if(vehicle[2] != null)
						Dtos.add(select);
				}
			}
			
		}
		return Dtos;
		
	} catch (Exception e) {
		throw e;
	}
}

@Override
public ValueObject saveTripsheetFuelDetailsFromClosTripApi(ArrayList<ValueObject> fulearray, ValueObject object, Vehicle vehicle)
		throws Exception {
	// TODO Auto-generated method stub
	Integer 						driverId				= null;	
	Vendor							vendor					= null;
	MultipartFile					uploadfile 				= null;
	ValueObject						fuelObject			    = null;
	Fuel							fuel					= null;
	List<Fuel>						fuelList				= null;
	TripSheet						trip 					= null;
	
	if (fulearray != null) {
		StringBuilder already = new StringBuilder();
		StringBuilder created = new StringBuilder();
		
		trip = tripSheetService.getTripsheetByLhpvId(object.getInt("lhpvId"));

		
		for(ValueObject fuelvalueObj: fulearray) {
	        try {
	        	driverId=(object.getString("driverLicence") !="" && StringUtils.isNotEmpty(object.getString("driverLicence"))) ? 
	        			  driverService.getDriverByDriverLicense(object.getString("driverLicence")) : 0;

	        	vendor = ivendorService.getVendorByName(fuelvalueObj.getString("selectVendor"), object.getInt("companyId"));
	        	
	        	if(vendor != null)
	        		fuelvalueObj.put("selectVendor", vendor.getVendorId());
	        	
	        	fuelList 	= fuelDao.getFuelByVId(vehicle.getVid(),object.getInt("companyId"));
	        	
	        	if(!fuelList.isEmpty()) {
	        		System.err.println("inside if ");
	        		
	        		fuel 	= fuelService.getFuelMeterByDateTime(vehicle.getVid(),  object.getInt("companyId"));
	        		System.err.println("fuelmeter old "+ fuel.getFuel_meter());
	        		fuelvalueObj.put("fuel_meter_old", fuel.getFuel_meter());
	        
	        	}
				
	        	
				
	        	fuelvalueObj.put("FuelSelectVehicle", vehicle.getVid());
	        	fuelvalueObj.put("companyId", object.getInt("companyId"));
	        	fuelvalueObj.put("vehicle_group", vehicle.getVehicleGroupId());
	        	fuelvalueObj.put("tripSheetId", object.getString("tripsheetId"));
	        	fuelvalueObj.put("paidById", driverId);
	        	fuelvalueObj.put("userId", object.getInt("userId"));
	        	fuelvalueObj.put("fuel_type", 1);
	        	fuelObject = fuelService.saveFuelEntryDetails(fuelvalueObj,uploadfile);

	        	if(fuelObject.getBoolean("alreadyExist")) {
	        		already.append(", "+fuelvalueObj.getDouble("fuelAmount"));
	        	}else {
	        		created.append(", "+fuelvalueObj.getDouble("fuelAmount"));
	        	}
	        	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		fuelObject.put("alreadyFuel", already);
		fuelObject.put("createdFuel", created);
	} 
	
	return fuelObject;
}

		@Override
		public Fuel getFuelMeterByTripsheetIdByDateTime(Long Tripsheet_Id, Integer companyId) throws Exception {
			// TODO Auto-generated method stub
			//return  fuelDao.getFuelClosingKmByTripsheetIdAndDataTime(Tripsheet_Id, companyId);
			
			 Query query = entityManager.createQuery("FROM Fuel AS F WHERE F.fuel_TripsheetID = ?1 AND F.companyId = ?2 AND F.markForDelete = 0 " +
                     "ORDER BY F.fuelDateTime DESC");
			 
		    query.setParameter(1, Tripsheet_Id);
		    query.setParameter(2, companyId);
		    query.setMaxResults(1);

		    System.err.println("result -- "+ query.getSingleResult() );
		    return (Fuel) query.getSingleResult();
		    
		}

		@Override
		public Fuel getFuelMeterByDateTime(Integer vid, Integer companyId) throws Exception {
			// TODO Auto-generated method stub
			 Query query = entityManager.createQuery("FROM Fuel AS F WHERE  F.companyId = ?1 AND F.markForDelete = 0 " +
                     "ORDER BY F.fuelDateTime DESC");
		    query.setParameter(1, companyId);
		    query.setMaxResults(1);

		    System.err.println("result -- "+ query.getSingleResult() );
		    return (Fuel) query.getSingleResult();
		}
		
}
